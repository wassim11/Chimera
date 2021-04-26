<?php

namespace App\Controller;
use App\Form\CoachType;
use App\Form\LoginType;
use App\Form\ModifType;
use App\Form\PhotoType;
use App\Form\ChangeType;
use App\Entity\Utilisateur;
use App\Form\UtilisateurType;
use App\Form\RecuperermotdepasseType;
use Doctrine\Persistence\ObjectManager;
use Doctrine\ORM\EntityManagerInterface;
use App\Repository\UtilisateurRepository;
use Symfony\Component\HttpFoundation\Request;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Form\Extension\HttpFoundation\HttpFoundationRequestHandler;

class SecurityController extends Controller
{
 
    /**
     * @Route("/login", name="security_log")
     */
    public function login(UtilisateurRepository $utilisateurRepository , SessionInterface $session,Request $request,UserPasswordEncoderInterface $encoder)
    {
        $utilisateurlogin = new Utilisateur();
        $connexionform=$this->createForm(LoginType::class, $utilisateurlogin);      
        $connexionform->add('Login',SubmitType::class);
        $connexionform->handleRequest($request);
        if ($connexionform->isSubmitted() && $connexionform->isValid()){
            $em=$this->getDoctrine()->getManager();      
            $Utilisateur = $em->getRepository(Utilisateur::class)->findOneBy(array('email'=>$utilisateurlogin->getEmail())); 
            $mdp = $connexionform->get('mdp')->getData();
            $hashBd=$Utilisateur->getMdp();
          
            
            if(!password_verify($mdp,$hashBd)){
                return $this->redirectToRoute('security_log', [
                    'form' => $connexionform->createView(),
                    
                ]);          
            }
            else{
                $type=$Utilisateur->getType();
                if($type==1){
                    $session->set('Utilisateur',$Utilisateur);
                    return $this->redirectToRoute('index', [
                       'session'=>$session,
                     ]);
                }
                else if ($type==2|| $type==3){
                    $session->set('Utilisateur',$Utilisateur);
                    return $this->redirectToRoute('indexfront', [
                     'session'=>$session,
                     ]);
                }
            }     
        }
        else{
            return $this->render('utilisateur/log.html.twig', [
                'form' => $connexionform->createView(),
                
            ]);
        }
    }
        


         /**
     * @Route("/recover", name="security_recover")
     */
    public function recup(UtilisateurRepository $utilisateurRepository , SessionInterface $session,UserPasswordEncoderInterface $encoder,Request $request, \Swift_Mailer $mailer): Response
    {
        $utilisateurrec = new Utilisateur();
        $recupererform=$this->createForm(RecuperermotdepasseType::class, $utilisateurrec);
        $recupererform->add('Reset',SubmitType::class);
        $recupererform->handleRequest($request);

        if ($recupererform->isSubmitted() && $recupererform->isValid()){
            $em=$this->getDoctrine()->getManager();
            $Utilisateur = $em->getRepository(Utilisateur::class)->findOneBy(array('email'=>$utilisateurrec->getEmail()));
            if(is_null($Utilisateur)){
                return $this->redirectToRoute('security_recover', [
                    'form' => $recupererform->createView(),
                ]);
            }
            else{
                $alphabet = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
                $pass = array();
                $alphaLength = strlen($alphabet) - 1;
                for ($i = 0; $i < 8; $i++) {
                    $n = rand(0, $alphaLength);
                    $pass[] = $alphabet[$n];
                }
                $generatedpass=implode($pass);
                $nom=$Utilisateur->getNom();
                $message = (new \Swift_Message('Utilisateur'))
                ->setFrom('slowlife.testpi@gmail.com')
                ->setTo($Utilisateur->getEmail())
                ->setBody($this->renderView('Emails/resetPwd.html.twig',['generatedpass'=> $generatedpass, 'nom'=> $nom]) , 'text/html' );

                $mailer->send($message) ;
               

                $hash = $encoder->encodePassword($Utilisateur, $generatedpass);
                $Utilisateur->setMdp($hash);
                $em->flush();
                
                return $this->redirectToRoute('security_log', [   
                ]);
            }
        }
        else{
        return $this->render('utilisateur/recup.html.twig', [
            'form' => $recupererform->createView(),
        ]); }
    }

    /**
     * @Route("/changepwd/{id}", name="change")
     */
    public function changerpwd(Request $request, $id, FlashyNotifier $flashy, SessionInterface $session, UserPasswordEncoderInterface $encoder)
    {
        $em=$this->getDoctrine()->getManager();
        $Utilisateur = $em->getRepository(Utilisateur::class)->find($id);
        $hashbd = $Utilisateur->getMdp();     
        $form = $this->createForm(ChangeType::class, $Utilisateur);    
        $form->add('Modifier',SubmitType::class);
        $form->handleRequest($request);
        $ancien = $form->get('ancien')->getData();        
        $mdp = $form->get('mdp')->getData(); 
        
        if ((password_verify($ancien,$hashbd)) && ($form->isSubmitted()) ) {  
            if(strcmp($ancien,$mdp)==0){
                $flashy->warning('Vous avez taper de nouveau un ancien MDP veuillez le remplacer pour plus de securité! ');
                if($Utilisateur->getType()==3||$Utilisateur->getType()==2) {
                    return $this->render('utilisateur/passwd.html.twig', [
                        'form' => $form->createView(),
                        'session'=>$session,
                    ]);
                    
                }
                else if($Utilisateur->getType()==1) {
                    return $this->render('utilisateur/passwdA.html.twig', [
                        'form' => $form->createView(),
                        'session'=>$session,
                    ]);
                } 
            }  
            else{       
            $hash = $encoder->encodePassword($Utilisateur,$mdp);
            $Utilisateur->setMdp($hash);
            $em->flush(); 
            $flashy->success('Mot de Passe changé avec succès');    
            if($Utilisateur->getType()==3||$Utilisateur->getType()==2) {
                return $this->render('utilisateur/indexfront.html.twig', [  
                    'session'=>$session,
                    
                ]);
            }
            else if($Utilisateur->getType()==1) {
                return $this->render('utilisateur/index.html.twig', [ 
                    'session'=>$session,
                    
                ]);
            }
           }
        
        }
        else
        {
            $flashy->error('Verifiez votre ancien MDP');    
            if($Utilisateur->getType()==3||$Utilisateur->getType()==2) {
                return $this->render('utilisateur/passwd.html.twig', [
                    'form' => $form->createView(),
                    'session'=>$session,
                ]);

            }
            else if($Utilisateur->getType()==1) {
                return $this->render('utilisateur/passwdA.html.twig', [
                    'form' => $form->createView(),
                    'session'=>$session,
                ]);
            }  
        }
         
    }
    
    
     /**
     * @Route("/new", name="newcompte")
     */
    public function inscrit(Request $request,\Swift_Mailer $mailer,UserPasswordEncoderInterface $encoder)
    {
        $Utilisateur=new Utilisateur();
        $form=$this->createForm(UtilisateurType::Class,$Utilisateur);
        $form->add('Inscrit', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
        $Utilisateur = $form->getData();
        $file=$Utilisateur->getPhoto();
        $fileName=md5(uniqid()).'.'.$file->guessExtension();
        $file->move($this->getParameter('upload_directory'), $fileName);
        $Utilisateur->setPhoto($fileName);
        $em=$this->getDoctrine()->getManager();
        $hash = $encoder->encodePassword($Utilisateur, $Utilisateur->getMdp());
        $Utilisateur->setMdp($hash);
        $em->persist($Utilisateur);
        $em->flush();
        $message = (new \Swift_Message('Utilisateur'))
        ->setFrom('slowlife.testpi@gmail.com')
        ->setTo($Utilisateur->getEmail())
        ->setBody($this->renderView('Emails/confirmer.html.twig', ['nom'=> $$Utilisateur->getNom()]) , 'text/html' );
        $mailer->send($message) ;

        return $this->redirectToRoute('security_log');
        }
        return $this->render('utilisateur/inscription.html.twig', [
            'form1' => $form->createView(),
        ]);
    }

    /**
     * @Route("/up/{id}", name="upd")
     */
    public function up(SessionInterface $session,$id,FlashyNotifier $flashy,Request $request): Response
    {      
        $em=$this->getDoctrine()->getManager();
        $Utilisateur = $em->getRepository(Utilisateur::class)->find($id);
        if($Utilisateur->getType()==1 || $Utilisateur->getType()==2 ){
        $form = $this->createForm(ModifType::class, $Utilisateur);
        $form->add('Modifier',SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted()) {
            $em->flush(); 
            $flashy->success('Profil modifé avec succès');
            if($Utilisateur->getType()==1){     
            return $this->render('utilisateur/updateAdmin.html.twig', [
                'form' => $form->createView(),
                "session"=>$session,
            ]);
            }
            else {
                return $this->render('utilisateur/updateUser.html.twig', [
                    'form' => $form->createView(),
                    "session"=>$session,
                ]);
            }
        }
        else{
            $flashy->success('Vérifiez données');
            if($Utilisateur->getType()==1){     
                return $this->render('utilisateur/updateAdmin.html.twig', [
                    'form' => $form->createView(),
                    "session"=>$session,
                ]);
                }
                else {
                    return $this->render('utilisateur/updateUser.html.twig', [
                        'form' => $form->createView(),
                        "session"=>$session,
                    ]);
                }

        }
    }
        else if($Utilisateur->getType()==3){
            $form = $this->createForm(CoachType::class, $Utilisateur);
            $form->add('Modifier',SubmitType::class);
            $form->handleRequest($request);
            if ($form->isSubmitted()) {
                $em->flush();   
                $flashy->success('Profil modifé avec succès');    
                return $this->render('utilisateur/update.html.twig', [
                   'form' => $form->createView(),
                    "session"=>$session,
                ]);
            }
            else{
                $flashy->success('Vérifiez données');
            return $this->render('utilisateur/update.html.twig', [
                        'form' => $form->createView(),
                        "session"=>$session,
            ]);
            }
        }
        
    

}
     /**
     * @Route("/changeph/{id}", name="changeph")
     */
    public function photo(SessionInterface $session,$id,Request $request): Response
    {      
        $em=$this->getDoctrine()->getManager();
        $Utilisateur = $em->getRepository(Utilisateur::class)->find($id);    
        $form = $this->createForm(PhotoType::class, $Utilisateur);
        $form->add('Modifier',SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted()) { 
            $em->flush();
            if($Utilisateur->getType()==1){
                return $this->render('utilisateur/updateAdmin.html.twig', [
                    'form' => $form->createView(),
                    "session"=>$session,    
                ]);

            }
            else if ($Utilisateur->getType()==2){
                return $this->render('utilisateur/updateUser.html.twig', [
                    'form' => $form->createView(),
                    "session"=>$session,    
                ]);

            }
            else if ($Utilisateur->getType()==3){
                return $this->render('utilisateur/update.html.twig', [
                    'form' => $form->createView(),
                    "session"=>$session,    
                ]);

            }
        }
        else{
            if($Utilisateur->getType()==1){
                return $this->render('utilisateur/updatePhotoA.html.twig', [
                    'form' => $form->createView(),
                    "session"=>$session,
                 ]);

            }
            else if($Utilisateur->getType()==2||$Utilisateur->getType()==3){
                return $this->render('utilisateur/updatePhoto.html.twig', [
                    'form' => $form->createView(),
                    "session"=>$session,
                 ]);
            }   
    }}}
        




