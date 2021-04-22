<?php

namespace App\Form;

use App\Entity\Utilisateur;
use App\Repository\UtilisateurRepository;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Doctrine\Common\Collections\ArrayCollection;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
class UtilisateurType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom',TextType::class)
            ->add('prenom',TextType::class)
            ->add('email',EmailType::class)
            ->add('mdp',PasswordType::class)
            ->add('confirm',PasswordType::class)
            ->add('ancien',PasswordType::class)
            ->add('type',ChoiceType::class,[
                'choices' =>[
                    '' =>[
                        'Client' =>2,
                        'Coach' =>3,
                    ],
                ],
            ])
            ->add('bday',DateType::class)
            ->add('genre',ChoiceType::class,[
                'choices' =>[
                    '' =>[
                        'Homme' =>'Homme',
                        'Femme' =>'Femme',
                    ],
                ],
            ])
            ->add('domaine',TextType::class)
            ->add('photo',FileType::class, array('data_class'=>null,'required'=>false))
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Utilisateur::class,
        ]);
    }
}
