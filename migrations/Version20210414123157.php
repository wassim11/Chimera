<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210414123157 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE activite_evenement DROP FOREIGN KEY fk_activite');
        $this->addSql('ALTER TABLE centre_service DROP FOREIGN KEY centre_service_ibfk_1');
        $this->addSql('ALTER TABLE rendezvous DROP FOREIGN KEY fk_centre');
        $this->addSql('ALTER TABLE activite_evenement DROP FOREIGN KEY fk_evenement');
        $this->addSql('ALTER TABLE ingredient_recette DROP FOREIGN KEY fk_ingr');
        $this->addSql('ALTER TABLE commentaire DROP FOREIGN KEY fk_pub_comm');
        $this->addSql('ALTER TABLE publication_physique DROP FOREIGN KEY fk_pub_phys');
        $this->addSql('ALTER TABLE publication_psy DROP FOREIGN KEY fk_pub_psy');
        $this->addSql('ALTER TABLE ingredient_recette DROP FOREIGN KEY fk_recette');
        $this->addSql('ALTER TABLE centre_service DROP FOREIGN KEY centre_service_ibfk_2');
        $this->addSql('ALTER TABLE rendezvous DROP FOREIGN KEY rendezvous_ibfk_1');
        $this->addSql('DROP TABLE activite');
        $this->addSql('DROP TABLE activite_evenement');
        $this->addSql('DROP TABLE centre');
        $this->addSql('DROP TABLE centre_service');
        $this->addSql('DROP TABLE commentaire');
        $this->addSql('DROP TABLE evenement');
        $this->addSql('DROP TABLE feedback');
        $this->addSql('DROP TABLE ingredient');
        $this->addSql('DROP TABLE ingredient_recette');
        $this->addSql('DROP TABLE logged');
        $this->addSql('DROP TABLE publication');
        $this->addSql('DROP TABLE publication_physique');
        $this->addSql('DROP TABLE publication_psy');
        $this->addSql('DROP TABLE recette');
        $this->addSql('DROP TABLE reclamation');
        $this->addSql('DROP TABLE rendezvous');
        $this->addSql('DROP TABLE service');
        $this->addSql('DROP TABLE suivi');
        $this->addSql('DROP INDEX email ON utilisateur');
        $this->addSql('ALTER TABLE utilisateur CHANGE mdp mdp INT NOT NULL, CHANGE bday bday DATE NOT NULL, CHANGE photo photo VARCHAR(255) DEFAULT NULL');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE activite (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(70) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, type VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, Description VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, duree INT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE activite_evenement (id_activite INT NOT NULL, id_evenement INT NOT NULL, INDEX fk_activite (id_activite), INDEX fk_evenement (id_evenement)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE centre (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(70) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, lieu VARCHAR(120) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, Description VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, image VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, type VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE centre_service (id_centre INT NOT NULL, id_service INT NOT NULL, prix DOUBLE PRECISION NOT NULL, INDEX id_centre (id_centre), INDEX id_service (id_service)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE commentaire (id INT AUTO_INCREMENT NOT NULL, id_utilisateur INT NOT NULL, id_publication INT NOT NULL, date INT NOT NULL, heure INT NOT NULL, contenu VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, INDEX fk_pub_utilisateur (id_utilisateur), INDEX fk_pub_comm (id_publication), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE evenement (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(70) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, lieu VARCHAR(150) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, type VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, Description VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, date_debut DATE NOT NULL, date_fin DATE NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE feedback (id INT AUTO_INCREMENT NOT NULL, id_utilisateur INT NOT NULL, sujet VARCHAR(100) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, description VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, nbr_etoiles INT NOT NULL, INDEX fk_utilisateur_feedback (id_utilisateur), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE ingredient (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(70) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, type VARCHAR(70) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE ingredient_recette (id_recette INT NOT NULL, id_ingredient INT NOT NULL, INDEX fk_ingr (id_ingredient), INDEX fk_recette (id_recette)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE logged (id INT NOT NULL, nom VARCHAR(50) CHARACTER SET latin1 NOT NULL COLLATE `latin1_swedish_ci`, prenom VARCHAR(50) CHARACTER SET latin1 NOT NULL COLLATE `latin1_swedish_ci`, email VARCHAR(100) CHARACTER SET latin1 NOT NULL COLLATE `latin1_swedish_ci`, mdp VARCHAR(255) CHARACTER SET latin1 NOT NULL COLLATE `latin1_swedish_ci`, domain VARCHAR(50) CHARACTER SET latin1 DEFAULT NULL COLLATE `latin1_swedish_ci`, photo TEXT CHARACTER SET latin1 DEFAULT NULL COLLATE `latin1_swedish_ci`, type INT NOT NULL, genre VARCHAR(8) CHARACTER SET latin1 NOT NULL COLLATE `latin1_swedish_ci`, bday DATE NOT NULL, UNIQUE INDEX id (id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = MyISAM COMMENT = \'\' ');
        $this->addSql('CREATE TABLE publication (id INT AUTO_INCREMENT NOT NULL, id_coach INT NOT NULL, type VARCHAR(100) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, url VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, Description VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, INDEX fk_coach_pub (id_coach), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE publication_physique (id INT NOT NULL, nom VARCHAR(70) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, duree DOUBLE PRECISION NOT NULL, repetition INT NOT NULL, INDEX fk_pub_phys (id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE publication_psy (id INT NOT NULL, sujet VARCHAR(100) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, INDEX fk_pub_psy (id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE recette (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(70) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, nombre_calories DOUBLE PRECISION NOT NULL, Description VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE reclamation (id INT AUTO_INCREMENT NOT NULL, id_utilisateur INT NOT NULL, sujet VARCHAR(90) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, description VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, date DATE NOT NULL, etat TINYINT(1) NOT NULL, INDEX fk_utilisateur_reclamation (id_utilisateur), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE rendezvous (id INT AUTO_INCREMENT NOT NULL, id_centre INT NOT NULL, id_utilisateur INT NOT NULL, id_service INT NOT NULL, date DATE NOT NULL, temps TIME NOT NULL, INDEX fk_utilisateur (id_utilisateur), INDEX fk_centre (id_centre), INDEX id_service (id_service), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE service (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(70) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, Description VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, type VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE suivi (id INT AUTO_INCREMENT NOT NULL, id_utilisateur INT NOT NULL, date DATE NOT NULL, poids DOUBLE PRECISION NOT NULL, taille DOUBLE PRECISION NOT NULL, age INT NOT NULL, sexe VARCHAR(10) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, heure_activite DOUBLE PRECISION NOT NULL, conso_eau DOUBLE PRECISION NOT NULL, INDEX fk_utilisateur_suivi (id_utilisateur), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('ALTER TABLE activite_evenement ADD CONSTRAINT fk_activite FOREIGN KEY (id_activite) REFERENCES activite (id)');
        $this->addSql('ALTER TABLE activite_evenement ADD CONSTRAINT fk_evenement FOREIGN KEY (id_evenement) REFERENCES evenement (id)');
        $this->addSql('ALTER TABLE centre_service ADD CONSTRAINT centre_service_ibfk_1 FOREIGN KEY (id_centre) REFERENCES centre (id)');
        $this->addSql('ALTER TABLE centre_service ADD CONSTRAINT centre_service_ibfk_2 FOREIGN KEY (id_service) REFERENCES service (id)');
        $this->addSql('ALTER TABLE commentaire ADD CONSTRAINT fk_pub_comm FOREIGN KEY (id_publication) REFERENCES publication (id)');
        $this->addSql('ALTER TABLE commentaire ADD CONSTRAINT fk_pub_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id)');
        $this->addSql('ALTER TABLE feedback ADD CONSTRAINT fk_utilisateur_feedback FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id)');
        $this->addSql('ALTER TABLE ingredient_recette ADD CONSTRAINT fk_ingr FOREIGN KEY (id_ingredient) REFERENCES ingredient (id)');
        $this->addSql('ALTER TABLE ingredient_recette ADD CONSTRAINT fk_recette FOREIGN KEY (id_recette) REFERENCES recette (id)');
        $this->addSql('ALTER TABLE publication ADD CONSTRAINT fk_coach_pub FOREIGN KEY (id_coach) REFERENCES utilisateur (id)');
        $this->addSql('ALTER TABLE publication_physique ADD CONSTRAINT fk_pub_phys FOREIGN KEY (id) REFERENCES publication (id)');
        $this->addSql('ALTER TABLE publication_psy ADD CONSTRAINT fk_pub_psy FOREIGN KEY (id) REFERENCES publication (id)');
        $this->addSql('ALTER TABLE reclamation ADD CONSTRAINT fk_utilisateur_reclamation FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id)');
        $this->addSql('ALTER TABLE rendezvous ADD CONSTRAINT fk_centre FOREIGN KEY (id_centre) REFERENCES centre (id)');
        $this->addSql('ALTER TABLE rendezvous ADD CONSTRAINT fk_utilisateur FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id)');
        $this->addSql('ALTER TABLE rendezvous ADD CONSTRAINT rendezvous_ibfk_1 FOREIGN KEY (id_service) REFERENCES service (id)');
        $this->addSql('ALTER TABLE suivi ADD CONSTRAINT fk_utilisateur_suivi FOREIGN KEY (id_utilisateur) REFERENCES utilisateur (id)');
        $this->addSql('ALTER TABLE `utilisateur` CHANGE mdp mdp VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, CHANGE bday bday DATE DEFAULT NULL, CHANGE photo photo TEXT CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`');
        $this->addSql('CREATE UNIQUE INDEX email ON `utilisateur` (email)');
    }
}
