/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Suivi;

import entities.RecetteSuivi.SuiviRegime;
import entities.RecetteSuivi.User;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import slowlifejava.services.RecetteSuivi.SuiviService;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class SuiviRegimeController implements Initializable {
    
//declaration table
    private TableColumn<SuiviRegime, Float> colPoids;
    private TableColumn<SuiviRegime, Integer> colTaille;
    private TableColumn<SuiviRegime, Integer> colActivite;
    private TableColumn<SuiviRegime, Integer> colConso;
    private TableColumn<SuiviRegime, Date> colDate;
    private TableView<SuiviRegime> tvSuivi;
    
    //my varaibles
    private int tri;
    private int Recherche;
    //buttons
    @FXML
    private ImageView btnInsert;
    @FXML
    private ImageView btnUpdate;
    @FXML
    private ImageView btnDelete;
    @FXML
    private JFXCheckBox TriDateAsc;
    @FXML
    private JFXCheckBox TriDateDesc;
    @FXML
    private JFXDatePicker DateD;
    @FXML
    private JFXDatePicker DateF;
    @FXML
    private ScrollPane sp;
    private int id;
    @FXML
    private JFXSlider SlTaille;
    @FXML
    private JFXSlider SlPoids;
    @FXML
    private JFXSlider SlActivite;
    @FXML
    private JFXSlider Slconso;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id=-1;
        try {
            //showSuivi(trie(tri));
            showSuivi(trie(tri));
        } catch (SQLException ex) {
            Logger.getLogger(SuiviRegimeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    /*  public void showSuivi(List<SuiviRegime> list) throws SQLException
    {
        SuiviService SS= new SuiviService();
        ObservableList<SuiviRegime> listSuivi = FXCollections.observableArrayList(list);
        colPoids.setCellValueFactory(new PropertyValueFactory<>("poid"));
        colTaille.setCellValueFactory(new PropertyValueFactory<>("taille"));
        colActivite.setCellValueFactory(new PropertyValueFactory<>("heursActivite"));
        colConso.setCellValueFactory(new PropertyValueFactory<>("consommationEau"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateSuivi"));
        tvSuivi.setItems(listSuivi);
    }
      */
    @FXML
    private void InsertSuivi() throws SQLException {
   //System.out.println("date="+new Date()+"poids="+ToFloat(tfPoids)+"taille="+tfTaille.getValue()+"activité="+ ToInt(tfActivite)+"conso="+ToInt(tfconso));
   SuiviService SS= new SuiviService();
   java.sql.Date date = new java.sql.Date(new Date().getTime());
   if(!SS.readoneByDate(date)){
   float taille = (float)((int) SlTaille.getValue())/100;
   SuiviRegime SR = new SuiviRegime(new Date(),(int)SlPoids.getValue(),taille,(int)SlActivite.getValue(),(int)Slconso.getValue(),new User(1));
   SS.ajouter(SR);
   showSuivi(trie(tri));
   }
   else
   {
       Alert AjoutImpossible = new Alert(Alert.AlertType.WARNING);
       AjoutImpossible.setTitle("Ajout Impossible");
       AjoutImpossible.setHeaderText("Vous ne pouvez ajouter un suivi qu'une fois par jour .");
       AjoutImpossible.setContentText("Si vous pensez vous avoir tromper dans vos parametres,vous pouvez toujours les modifiers dans le delais impartie");
       AjoutImpossible.showAndWait();
   }
   
   }
  

    @FXML
    private void UpdateSuivi() throws SQLException {
       SuiviService SS= new SuiviService();
           if(id!=-1){
           float taille = (float)((int) SlTaille.getValue())/100;
           SuiviRegime SR=new SuiviRegime(id,new Date(),(int)SlPoids.getValue(),taille,(int)SlActivite.getValue(),(int)Slconso.getValue(),new User(1));
           SS.update(SR);
           showSuivi(trie(tri));
        }
        else
        {
            Alert SelectionItem = new Alert(Alert.AlertType.ERROR);
            SelectionItem.setTitle("Aucun Suivi n'a été selectionner");
            SelectionItem.setHeaderText("Veuillez selectionner le suivi que vous voulez modifier");
            SelectionItem.setContentText("Rappelez vous de ces information \n "
                    + "1-Essayez d'être le plus que possible précis en entrant les valeurs afin de nous aider a vous conseiller"
                    + "2-Vous pouvez changer un suivi dans un délais de 7jours"
                    + "");
        }
    }

    @FXML
    private void DeleteSuivi() throws SQLException {
        System.out.println("delete clicked");
        SuiviService SS= new SuiviService();
        if(id!=-1){
        SS.delete(new SuiviRegime(id));
        showSuivi(trie(tri));
        id=-1;
        }
        else
        {
            System.out.println("error");
        }
    }
/*
    private void LireTab(MouseEvent event) throws SQLException {
        SuiviRegime rgm = tvSuivi.getSelectionModel().getSelectedItem();
        SuiviService IS = new SuiviService();
        System.out.println(rgm);
        tfPoids.setValue(rgm.getPoid());
        tfTaille.setValue(rgm.getTaille());
        tfconso.setValue(rgm.getConsommationEau());
        tfActivite.setValue(rgm.getHeursActivite());
    }*/
    public List<SuiviRegime> trie(int n) throws SQLException
{
    // 1 pour Nom
    // 2 pour Type
    // 4 pour Description
    String requete = null;
    if(n==0)
    {
      requete="select *,(poids/POW(taille,2)) as IMC from `suivi` order by id";
    }
    if(n==1)
    {
      requete="select *,(poids/POW(taille,2)) as IMC from suivi order by date Asc";
    }
    if (n==2)
    {
      requete="select *,(poids/POW(taille,2)) as IMC from suivi order by date Desc";
    }
    SuiviService SS=new SuiviService();
    return SS.Tri(requete);
}
    private String CompareIMC(float IMC)
    {
        if(IMC<18.5)
        {
            return "Insuffissance";
        }
        if(IMC>18.5 && IMC<24.9)
        {
            return "Poids Ideale";
        }
        if(IMC>25 && IMC<29.9)
        {
            return "Surpoids";
        }
        else
        {
            return "Obésité";
        }
    }

    @FXML
    private void DateAsc(MouseEvent event) throws SQLException {
        if(TriDateAsc.isSelected()){
        TriDateDesc.setSelected(false);
        tri=1;
        showSuivi(trie(tri));
        
        }
        else
        {
            tri = 0;
            showSuivi(trie(tri));
        }
        System.out.println("tri="+tri);
    }

    @FXML
    private void DateDesc(MouseEvent event) throws SQLException {
        if(TriDateDesc.isSelected()){
            TriDateAsc.setSelected(false);
        tri=2;
        showSuivi(trie(tri));
        }
        else
        {
            tri = 0;
            showSuivi(trie(tri));
        }
        System.out.println("tri="+tri);
    }

    @FXML
    private void Datedebut() throws SQLException {Recherche(DateD.getValue(),DateF.getValue());}

    @FXML
    private void DateFin() throws SQLException {Recherche(DateD.getValue(),DateF.getValue());}
    
    private void Recherche(LocalDate dateD,LocalDate dateF) throws SQLException
    {
        SuiviService SS= new SuiviService();
        if(dateD==null && dateF==null)
       {
           showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC  from Suivi order by date"));
       }
       
       if(dateD==null && dateF!=null)
       {        
           Date f= new Date(dateF.toEpochDay()*24*3600*1000);
           java.sql.Date sqldatef = new java.sql.Date(f.getTime());
          showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC from Suivi WHERE date<='"+sqldatef+"'"));
       }
       if(dateF==null && dateD!=null)
       {
            Date d= new Date(dateD.toEpochDay()*24*3600*1000);
            java.sql.Date sqldated = new java.sql.Date(d.getTime());
            showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC from Suivi WHERE date>='"+sqldated+"'"));
       }
       if(dateD!=null && dateF!=null)
       {
           if(dateD.compareTo(dateF)>0){
           Alert DateSupp = new Alert(Alert.AlertType.ERROR);
           DateSupp.setTitle("Date");
           DateSupp.setContentText("Veuillez bien saisir les valeurs de recherche");
           DateSupp.show();
           DateD.setValue(null);
           DateF.setValue(null); 
           showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC from Suivi order by date"));
           }
           else
        {
           Date d= new Date(dateD.toEpochDay()*24*3600*1000);
           java.sql.Date sqldated = new java.sql.Date(d.getTime());
           Date f= new Date(dateF.toEpochDay()*24*3600*1000);
           java.sql.Date sqldatef = new java.sql.Date(f.getTime());
           showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC  from Suivi WHERE date>= '"+sqldated+"' AND date<='"+sqldatef+"'"));
        }  
       }
    }
    private ImageView initializeImages(String URL,int longueur,int largeur){
      Image Poids = new Image(URL);
      ImageView imageview= new ImageView();
      imageview.setImage(Poids);
      imageview.setFitHeight(longueur);
      imageview.setFitWidth(largeur);
      return imageview;
    }
    private Label initializeLabel(String Texte,int longueur,int largeur)
    {
        Label label = new Label(Texte);
        label.setPrefSize(largeur,longueur);
        label.setAlignment(Pos.CENTER);
        return label;
    }
    public static String diff(Date date){
        Date aujourdhui=new Date();
        String Dif="";        
        long difference =(aujourdhui.getTime()-date.getTime())/(1000*60*60*24);
        if(difference<30)
        {
            if(difference==0)
            {
                Dif="Aujourd'hui";
                return Dif;
            }
            if(difference==1)
            {
                Dif="Il y a "+difference+" Jour";
                return Dif;
            }
            else
            {
                 Dif="Il y a "+difference+" Jours";
                 return Dif;
            }
        }
        else
        {
            
            if((int)(difference%30)<12)
            {
            int mois;
            mois=(int)(difference%30);
            Dif="Il y a "+mois+" Mois";
            return Dif;
            }
            else
            {
            int Annee;
            Annee=(int)(difference%(30*12));
            Dif="Il y a "+Annee+" Année";
            return Dif;
            }
                    
        }
    
}
    private void showSuivi(List<SuiviRegime> liste) throws SQLException
    {     
      VBox Liste = new VBox(); 
      System.out.println("here");
      for(int i=0;i<liste.size();i++)
      {
      ImageView imagevPoids=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/poids1.png",64,64);
      Label vPoids = initializeLabel(String.valueOf(liste.get(i).getPoid())+"Kg",64,64);
      VBox Poids = new VBox(imagevPoids,vPoids);
      
      
      ImageView imagevTaille=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/hauteur1.png",64,64);
      Label vTaille = initializeLabel(String.valueOf(liste.get(i).getTaille())+"m",64,64);
      VBox Taille = new VBox(imagevTaille,vTaille);
              
      ImageView imagevEau=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/eau1.png",64,64);
      Label vEau = initializeLabel(String.valueOf(liste.get(i).getConsommationEau())+"L",10,64);
      VBox Eau = new VBox(imagevEau,vEau);
      
      ImageView imagevActivite=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/activite1.png",64,64);
      Label vActivite = initializeLabel(String.valueOf(liste.get(i).getHeursActivite())+"H",10,64);
      VBox Activite = new VBox(imagevActivite,vActivite);
       
      ImageView imagevDate=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/date1.png",80,80);
      Label vDate = initializeLabel(diff(liste.get(i).getDateSuivi()),10,80);
      VBox Date = new VBox(imagevDate,vDate);
      
      ImageView imagevIMC=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/date1.png",80,80);
      Label vIMC = initializeLabel(String.valueOf(liste.get(i).getIMC()),10,80);
      VBox IMC = new VBox(imagevIMC,vIMC);
      
      HBox Ligne = new HBox(Date,Poids,Taille,Eau,Activite,IMC);
      Ligne.setPrefSize(420, 80);
          Ligne.setBackground(new Background(
        new BackgroundFill(
            Color.color(
                Color.YELLOW.getRed(), 
                Color.YELLOW.getGreen(), 
                Color.YELLOW.getBlue(), 0.4d),
            new CornerRadii(5), 
            null)));
      
      Liste.setOnMouseClicked((MouseEvent event)->{
        int numberY= ((int)(event.getY()-10)/98);
        SuiviRegime SR=liste.get(numberY);
        SlPoids.setValue(SR.getPoid());
        SlTaille.setValue(SR.getTaille()*100);
        Slconso.setValue(SR.getConsommationEau());
        SlActivite.setValue(SR.getHeursActivite());
        id=SR.getIdSuivi();
      });
      
      Ligne.setSpacing(20);
      Liste.getChildren().add(Ligne);
      Liste.setPadding(new Insets(10));
      }
      sp.setContent(Liste);
      
      
      
    }


}

