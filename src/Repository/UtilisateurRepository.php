<?php

namespace App\Repository;

use App\Entity\Utilisateur;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Utilisateur|null find($id, $lockMode = null, $lockVersion = null)
 * @method Utilisateur|null findOneBy(array $criteria, array $orderBy = null)
 * @method Utilisateur[]    findAll()
 * @method Utilisateur[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class UtilisateurRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Utilisateur::class);
    }

    // /**
    //  * @return Utilisateur[] Returns an array of Utilisateur objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('u')
            ->andWhere('u.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('u.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Utilisateur
    {
        return $this->createQueryBuilder('u')
            ->andWhere('u.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
    public function findByDate()
    {
        return $this->createQueryBuilder('Utilisateur')
            ->orderBy('Utilisateur.bday','DESC')
            ->getQuery()
            ->getResult()
            ;
    }
    public function findByDate2()
    {
        return $this->createQueryBuilder('Utilisateur')
            ->orderBy('Utilisateur.bday','ASC')
            ->getQuery()
            ->getResult()
            ;
    }
    public function findMale()
    {
        return $this->createQueryBuilder('Utilisateur')
            ->where('Utilisateur.genre Like :genre')
            ->setParameter('genre', '%Homme%')
            ->getQuery()
            ->getResult()
            ;
    }
    public function findFemale()
    {
        return $this->createQueryBuilder('Utilisateur')
            ->where('Utilisateur.genre Like :genre')
            ->setParameter('genre', '%Femme%')
            ->getQuery()
            ->getResult()
            ;
    }
    public function findCoach()
    {
        return $this->createQueryBuilder('Utilisateur')
            ->where('Utilisateur.type Like :type')
            ->setParameter('type', 3)
            ->getQuery()
            ->getResult()
            ;
    }
    public function findClient()
    {
        return $this->createQueryBuilder('Utilisateur')
            ->where('Utilisateur.type Like :type')
            ->setParameter('type', 2)
            ->getQuery()
            ->getResult()
            ;
    }
}
