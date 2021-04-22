<?php

namespace App\Controller;
use App\Form\LoginType;
use App\Form\ChangeType;

use App\Entity\Utilisateur;
use App\Form\UtilisateurType;
use App\Form\RecuperermotdepasseType;
use Doctrine\Persistence\ObjectManager;
use Doctrine\ORM\EntityManagerInterface;
use App\Repository\UtilisateurRepository;
use Symfony\Component\HttpFoundation\Request;
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
     * @Route("/inscription", name="security_register")
     */
    public function registration(Request $request, EntityManagerInterface $manager)
    {
        $utilisateur = new Utilisateur();
        $form = $this->createForm(UtilisateurType::class,$utilisateur);
        $form->add('Ajouter', SubmitType::class);
        $form->handleRequest($request);
        

        if($form->isSubmitted() && $form->isValid()){
            $utilisateur->setMdp($utilisateur, $utilisateur->getMdp());
            $manager->persist($utilisateur);
            $manager->flush();
            return $this->redirectToRoute('UtilisateurList');
        }
        //return $this->render('security/registration.html.twig',[
            return $this->render('utilisateur/inscription.html.twig',[
            'form'=> $form->createView()
        ]);
    }

    //______________________________Test Cryptage_______________________________________________________________________________

     /**
     * @Route("/inscription1", name="security_inscri")
     */
    public function registration1(Request $request, EntityManagerInterface $manager, UserPasswordEncoderInterface $encoder)
    {
        $utilisateur = new Utilisateur();
        $form = $this->createForm(UtilisateurType::class,$utilisateur);
        $form->add('Ajouter', SubmitType::class);
        $form->handleRequest($request);
        

        if($form->isSubmitted() && $form->isValid()){

            $hash = $encoder->encodePassword($utilisateur, $utilisateur->getMdp());
            $utilisateur->setMdp($utilisateur, $utilisateur->getMdp());
            $manager->persist($utilisateur);
            $manager->flush();
            return $this->redirectToRoute('security_con');
        }
        return $this->render('security/registration.html.twig',[
            'form'=> $form->createView()
        ]);
    }

    /**
     * @Route("/connexion", name="security_con")
     */
    public function login1(UtilisateurRepository $utilisateurRepository , SessionInterface $session,Request $request,UserPasswordEncoderInterface $encoder)
    {
 
        return $this->render('security/login.html.twig');
        $hash = $encoder->encodePassword($_POST['_password']);
        //if($utilisateurlogin->getMdp()==$hash){
        $utilisateurlogin = new Utilisateur();
        $em=$this->getDoctrine()->getManager();
        $Utilisateur = $em->getRepository(Utilisateur::class)->findOneBy(array('email'=>$utilisateurlogin->getEmail(),'mdp'=>$utilisateurlogin->getMdp()));
        if(is_null($Utilisateur)){
            return $this->redirectToRoute('login');
        }
        else{
            $session->set('Utilisateur',$Utilisateur);
            return $this->redirectToRoute('UtilisateurList', [
              'session'=>$session,
            ]);
        }
        /*}
        else{
            $session->set('Utilisateur',$Utilisateur);
            return $this->redirectToRoute('security_con', [
              'session'=>$session,
            ]);
        }*/

    }



 



    /**
     * @Route("/login", name="security_log")
     */
    public function login(UtilisateurRepository $utilisateurRepository , SessionInterface $session,Request $request)
    {
        $utilisateurlogin = new Utilisateur();
        $connexionform=$this->createForm(LoginType::class, $utilisateurlogin);

        
        $connexionform->add('Login',SubmitType::class);
        $connexionform->handleRequest($request);

        if ($connexionform->isSubmitted() && $connexionform->isValid()){
            $em=$this->getDoctrine()->getManager();
            $Utilisateur = $em->getRepository(Utilisateur::class)->findOneBy(array('email'=>$utilisateurlogin->getEmail(),'mdp'=>$utilisateurlogin->getMdp()));
            //$user=$userRepository->findOneBy(array('email'=>$userlogin->getEmail(),'password'=>$utilisateurlogin->getPassword()));
            if(is_null($Utilisateur)){
                return $this->redirectToRoute('security_log', [
                    'form' => $connexionform->createView(),
                    
                ]);
            }
            else{
                $session->set('Utilisateur',$Utilisateur);
                return $this->redirectToRoute('index', [
                  'session'=>$session,
                ]);
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
    public function recup(UtilisateurRepository $utilisateurRepository , SessionInterface $session,Request $request, \Swift_Mailer $mailer): Response
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
                $message = (new \Swift_Message('Utilisateur'))
                ->setFrom('slowlife.testpi@gmail.com')
                ->setTo($Utilisateur->getEmail())
                ->setBody("votre mot de passe :".$Utilisateur->getMdp());
                    $mailer->send($message) ;

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
    public function changerpwd(Request $request, $id)
    {
        $em=$this->getDoctrine()->getManager();
        $Utilisateur = $em->getRepository(Utilisateur::class)->find($id);
        $form = $this->createForm(ChangeType::class, $Utilisateur);    
        $form->add('Modifier',SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted()) {
            $em->flush();         
            return $this->redirectToRoute('UtilisateurList');
        }
        return $this->render('utilisateur/changepwd.html.twig', [
                        'form' => $form->createView(),
        ]);
    }
     /**
     * @Route("/changepwd1/{id}", name="change1")
     */
    public function changerpwd1(Request $request, $id)
    {
        $em=$this->getDoctrine()->getManager();
        $Utilisateur = $em->getRepository(Utilisateur::class)->find($id);
        $form = $this->createForm(LoginType::class, $Utilisateur);    
        $form->add('Changer',SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted()) {
            $em->flush();         
            return $this->redirectToRoute('change');
        }
        return $this->render('utilisateur/changepwd1.html.twig', [
                        'form' => $form->createView(),
        ]);
    }
     /**
     * @Route("/new", name="newcompte")
     */
    public function inscrit(Request $request,\Swift_Mailer $mailer)
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
        $em->persist($Utilisateur);
        $em->flush();
        $message = (new \Swift_Message('Utilisateur'))
        ->setFrom('slowlife.testpi@gmail.com')
        ->setTo($Utilisateur->getEmail())
        ->setBody("Bienvenu Mme/Mr ".$Utilisateur->getNom()."à Slowlife By Chimera");
            $mailer->send($message) ;

        return $this->redirectToRoute('security_log');
        }
        return $this->render('utilisateur/inscription.html.twig', [
            'form1' => $form->createView(),
        ]);
    }

}
