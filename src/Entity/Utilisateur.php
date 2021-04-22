<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\UtilisateurRepository;
use Doctrine\Common\Collections\Collection;
use Doctrine\Common\Collections\ArrayCollection;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;


/**
 * @ORM\Entity(repositoryClass=UtilisateurRepository::class)
 * @ORM\Table(name="`utilisateur`")
 * @UniqueEntity(
 * fields = {"email"},
 * message ="Email déja utilisé !"
 * )
 */
class Utilisateur implements UserInterface
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=50)
     * @Assert\NotBlank(message="nom is required")
     */
    private $nom;

    /**
     * @ORM\Column(type="string", length=50)
     * @Assert\NotBlank(message="prenom is required")
     */
    private $prenom;

    /**
     * @ORM\Column(type="string", length=100)
     * @Assert\NotBlank(message="email is required")
     * @Assert\Email(message = "The email '{{ value }}' is not a valid email")
     */
    private $email;

    /**
     * @ORM\Column(type="string")
     * @Assert\NotBlank(message="mdp is required")
     * @Assert\Length(max="15",maxMessage="Votre mot de passe doit contenir au maximum 15 caractères")
     * @Assert\Length(min="6",minMessage="Votre mot de passe doit contenir au minimum 6 caractères")
     */
    private $mdp;
/**
     *@Assert\NotBlank(message="confirm is required")
     *@Assert\EqualTo(propertyPath="mdp",message="Vérifier votre password")
     */
    private $confirm;

    private $ancien;
    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="type is required")
     */
    private $type;

    /**
     * @ORM\Column(type="date")
     * @Assert\NotBlank(message="bday is required")
     * @Assert\LessThan("today")
     */
    private $bday;

    /**
     * @ORM\Column(type="string", length=8)
     * @Assert\NotBlank(message="genre is required")
     */
    private $genre;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    private $domaine;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Assert\NotBlank(message="photo is required")
     */
    private $photo;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getMdp(): ?string
    {
        return $this->mdp;
    }

    public function setMdp(string $mdp): self
    {
        $this->mdp = $mdp;

        return $this;
    }
    public function getConfirm(): ?string
    {
        return $this->confirm;
    }

    public function setConfirm(string $confirm): self
    {
        $this->confirm = $confirm;

        return $this;
    }
    public function getAncien(): ?string
    {
        return $this->ancien;
    }

    public function setAncien(string $ancien): self
    {
        $this->ancien = $ancien;

        return $this;
    }
    public function getType(): ?int
    {
        return $this->type;
    }

    public function setType(int $type): self
    {
        $this->type = $type;

        return $this;
    }

    public function getBday(): ?\DateTimeInterface
    {
        return $this->bday;
    }

    public function setBday(?\DateTimeInterface $bday): self
    {
        $this->bday = $bday;

        return $this;
    }

    public function getGenre(): ?string
    {
        return $this->genre;
    }

    public function setGenre(string $genre): self
    {
        $this->genre = $genre;

        return $this;
    }

    public function getDomaine(): ?string
    {
        return $this->domaine;
    }

    public function setDomaine(?string $domaine): self
    {
        $this->domaine = $domaine;

        return $this;
    }

    public function getPhoto()
    {
        return $this->photo;
    }

    public function setPhoto($photo)
    {
        $this->photo = $photo;

        return $this;
    }
    public function eraseCredentials(){}
    public function getPassword(): ?string
    {
        return $this->mdp;
    }
    public function getUsername(): ?string
    {
        return $this->email;
    }
    public function getSalt(){}
    public function getRoles(){
        return['ROLE_USER'];
    }
   
}
