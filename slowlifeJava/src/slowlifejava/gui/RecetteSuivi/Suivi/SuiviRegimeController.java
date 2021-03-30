/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Suivi;

import slowlifejava.entities.RecetteSuivi.SuiviRegime;
//import slowlifejava.entities.RecetteSuivi.User;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import entities.users.Utilisateur;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import slowlifejava.services.users.UserService;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class SuiviRegimeController implements Initializable {
    
    
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
    private JFXSlider SlActivite;
    @FXML
    private JFXSlider Slconso;
    @FXML
    private JFXTextField tfTaille;
    @FXML
    private JFXTextField tfPoids;
    private Utilisateur client;
    @FXML
    private Label lbTaille;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService us = new UserService();
        try {
            client = us.getUserlogged();
        } catch (SQLException ex) {
            Logger.getLogger(SuiviRegimeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SuiviService SS;
        try {
            SS = new SuiviService();
            if(SS.readoneByUser(client)){
            tfTaille.setVisible(false);
            lbTaille.setVisible(false);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SuiviRegimeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        id=-1;
        try {
            //showSuivi(trie(tri));
            showSuivi(trie(tri));
        } catch (SQLException ex) {
            System.out.println("error affichage");
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
   if(!SS.readoneByDate(date,client)){
   if(SS.readoneByUser(client)){
   SuiviRegime SR = new SuiviRegime(new Date(),Integer.parseInt(tfPoids.getText()),SS.readoneLastSuivi(client).getTaille()/100,(int)SlActivite.getValue(),(int)Slconso.getValue(),client);
   SS.ajouter(SR);
   showSuivi(trie(tri));
    
       }
       else
       {
        SuiviRegime SR = new SuiviRegime(new Date(),Integer.parseInt(tfPoids.getText()),Float.parseFloat(tfTaille.getText()),(int)SlActivite.getValue(),(int)Slconso.getValue(),client);
   SS.ajouter(SR);
   showSuivi(trie(tri));    
       tfTaille.setVisible(false);
       lbTaille.setVisible(false);
       }
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
           System.out.println("id=**"+id);
           if(id!=-1){
           SuiviRegime SR=new SuiviRegime(id,new Date(),Integer.parseInt(tfPoids.getText()),Float.parseFloat(tfTaille.getText()),(int)SlActivite.getValue(),(int)Slconso.getValue(),client);
           SS.update(SR);
           showSuivi(trie(tri));
           id=-1;
        }
        else
        {
            Alert SelectionItem = new Alert(Alert.AlertType.ERROR);
            SelectionItem.setTitle("Aucun Suivi n'a été selectionner");
            SelectionItem.setHeaderText("Veuillez selectionner le suivi que vous voulez modifier");
            SelectionItem.setContentText("Rappelez vous de ces information \n "
                    + "1-Essayez d'être le plus que possible précis en entrant les valeurs afin de nous aider a vous conseiller"
                    + "2-Vous pouvez changer un suivi dans un délais de 7jours");
        }
    }

    @FXML
    private void DeleteSuivi() throws SQLException {
        System.out.println("delete clicked");
        SuiviService SS= new SuiviService();
        if(id!=-1){
        System.out.println(id);
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
      requete="select *,(poids/POW(taille,2)) as IMC from `suivi` where idUtilisateur="+client.getId();
    }
    if(n==1)
    {
      requete="select *,(poids/POW(taille,2)) as IMC from suivi  where idUtilisateur="+client.getId()+ " order by date Asc";
    }
    if (n==2)
    {
      requete="select *,(poids/POW(taille,2)) as IMC from suivi  where idUtilisateur="+client.getId()+ " order by date Desc";
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
           showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC  from Suivi where idUtilisateur='"+client.getId()+ "' order by date"));
       }
       
       if(dateD==null && dateF!=null)
       {        
           Date f= new Date(dateF.toEpochDay()*24*3600*1000);
           java.sql.Date sqldatef = new java.sql.Date(f.getTime());
          showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC from Suivi WHERE date<='"+sqldatef+"' AND idUtilisateur='"+client.getId()+"'"));
       }
       if(dateF==null && dateD!=null)
       {
            Date d= new Date(dateD.toEpochDay()*24*3600*1000);
            java.sql.Date sqldated = new java.sql.Date(d.getTime());
            showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC from Suivi WHERE date>='"+sqldated+"' AND idUtilisateur='"+client.getId()+"'"));
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
           showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC from Suivi where idUtilisateur='"+client.getId()+"' order by date"));
           }
           else
        {
           Date d= new Date(dateD.toEpochDay()*24*3600*1000);
           java.sql.Date sqldated = new java.sql.Date(d.getTime());
           Date f= new Date(dateF.toEpochDay()*24*3600*1000);
           java.sql.Date sqldatef = new java.sql.Date(f.getTime());
           showSuivi(SS.Tri("Select *,(poids/POW(taille,2)) as IMC  from Suivi WHERE date>= '"+sqldated+"' AND date<='"+sqldatef+"' AND idUtilisateur='"+client.getId()+"'"));
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
      ImageView imagevPoids=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/poids1.png",64,80);
      Label vPoids = initializeLabel(String.valueOf(liste.get(i).getPoid())+"Kg",64,80);
      VBox Poids = new VBox(imagevPoids,vPoids);
      
      
      ImageView imagevTaille=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/hauteur1.png",64,80);
      Label vTaille = initializeLabel(String.valueOf(liste.get(i).getTaille())+"m",64,80);
      VBox Taille = new VBox(imagevTaille,vTaille);
              
      ImageView imagevEau=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/eau1.png",64,80);
      Label vEau = initializeLabel(String.valueOf(liste.get(i).getConsommationEau())+"L",10,80);
      VBox Eau = new VBox(imagevEau,vEau);
      
      ImageView imagevActivite=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/activite1.png",64,80);
      Label vActivite = initializeLabel(String.valueOf(liste.get(i).getHeursActivite())+"H",10,80);
      VBox Activite = new VBox(imagevActivite,vActivite);
       
      ImageView imagevDate=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/date1.png",64,80);
      Label vDate = initializeLabel(diff(liste.get(i).getDateSuivi()),10,80);
      VBox Date = new VBox(imagevDate,vDate);
      
      ImageView imagevIMC=initializeImages("Gui/RecetteSuivi/Suivi/ImagesSuivi/bmi.png",64,80);
      Label vIMC = initializeLabel(String.valueOf((double)Math.round(liste.get(i).getIMC() * 10)/ 10),10,80);
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
          System.out.println(numberY);
        SuiviRegime SR=liste.get(numberY);
        tfPoids.setText(String.valueOf(SR.getPoid()));
        tfTaille.setText(String.valueOf(SR.getTaille()));
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

    @FXML
    private void Suivi(MouseEvent event) throws SQLException {
        showSuivi(trie(tri));   
    }

    @FXML
    private void Stat(MouseEvent event) throws SQLException {
        System.out.println(calculEnergie());
        CategoryAxis xAxis    = new CategoryAxis();
        xAxis.setLabel("Indices");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Valeurs en kCal");
        BarChart barChart = new BarChart(xAxis, yAxis);
        XYChart.Series dataSeries1 = new XYChart.Series();
        barChart.setTitle("Consomation de calories selon l'organisme et l'activité");
        dataSeries1.getData().add(new XYChart.Data("DER", calculEnergie()));
        dataSeries1.getData().add(new XYChart.Data("Normal", calculEnergie()*1.375));
        dataSeries1.getData().add(new XYChart.Data("Activité légère", calculEnergie()*1.56));
        dataSeries1.getData().add(new XYChart.Data("Activité modérée", calculEnergie()*1.64));
        dataSeries1.getData().add(new XYChart.Data("Activité intense", calculEnergie()*1.82));
        barChart.getData().add(dataSeries1);
        sp.setContent(barChart);
        
    }
    private int calculEnergiePropre() throws SQLException
    {
        SuiviService SS =new SuiviService();
        SuiviRegime SR=SS.readoneLastSuivi(client);
        if(SR.getHeursActivite()==0)
        {
            return (int) (calculEnergie()*1.375);
        }
        if(SR.getHeursActivite()==1 || SR.getHeursActivite()==2)
        {
            return (int) (calculEnergie()*1.56);
        }
        if(SR.getHeursActivite()>2 || SR.getHeursActivite()<=5)
        {
            return (int) (calculEnergie()*1.64);
        }
        if(SR.getHeursActivite()>5)
        {
            return (int) (calculEnergie()*1.82);
        }
        return 0;
        
    }
    private int Age(java.sql.Date date)
    {
        Date today = new Date();
        today.setYear(2021);
        return today.getYear()-date.getYear();
    }
    private int calculEnergie() throws SQLException
    {
        SuiviService SS =new SuiviService();
        SuiviRegime SR=SS.readoneLastSuivi(client);
        System.out.println(SR.getIMC());
        int DER;
        if((Age(client.getBday())>18 && Age(client.getBday())<60) || (9.740*SR.getPoid()/Math.pow(SR.getTaille(),2)>25) ){
        if(client.getGenre().equals("Femme")){
            System.out.println(client.getGenre());
            DER=(int)(9.740*SR.getPoid()+172.9*SR.getTaille()-4.737*Age(client.getBday())+ 667.051);
        }
        else
        {
            System.out.println(client.getGenre());
            DER =(int)(13.707*SR.getPoid()+492.3*SR.getTaille()-6.673*Age(client.getBday())+77.607);            
        }
        }else
        {
            if(client.getGenre().equals("Femme")){
            System.out.println(client.getGenre());
            DER=(int) (0.963 * Math.pow(SR.getPoid(),0.48) * Math.pow(SR.getTaille(), 0.50) * Math.pow(SR.getTaille(), -0.13) * (1000/4.1855));
        }
        else
        {
            System.out.println(client.getGenre());
            DER =(int) (1.083 * Math.pow(SR.getPoid(), 0.48) * Math.pow(SR.getTaille(), 0.50) * Math.pow(SR.getTaille(), -0.13) * (1000/4.1855) )  ;        
        }
        }
        return DER;
    }
    
    private int poidIdeal(Utilisateur user,Float taille,Double IMC) throws SQLException
    {
        int Poids;
        Poids = (int) (IMC * Math.pow(taille,2));
        return Poids;
    }
    private boolean controledesaisie(JFXTextField tf,String Pattern)
    {
        if(tf.getText().isEmpty() || !tf.getText().matches(Pattern))
        {
            return false;
        }
            //poids "^([4-9][0-9]|[1][0-9][0-9]|200)$"
            //taille "^([1][0-9][0-9]|[2][0-2][0-5])$"
        return true;
    }

    @FXML
    private void IMC(MouseEvent event) throws SQLException {
        
SuiviService SS =new SuiviService();
List<SuiviRegime> SR=SS.readAll(client);
NumberAxis xAxis = new NumberAxis();
xAxis.setLabel("Numero du Suivi");

NumberAxis yAxis = new NumberAxis();
yAxis.setLabel("Poids");

XYChart.Series<Integer,Integer> dataSeries1 = new XYChart.Series();
XYChart.Series<Integer,Integer> dataSeries2 = new XYChart.Series();
XYChart.Series<Integer,Integer> dataSeries3 = new XYChart.Series();
XYChart.Series<Integer,Integer> dataSeries4 = new XYChart.Series();
XYChart.Series<Integer,Integer> dataSeries5 = new XYChart.Series();
XYChart.Series<Integer,Integer> dataSeries6 = new XYChart.Series();
XYChart.Series<Integer,Integer> dataSeries7 = new XYChart.Series();

dataSeries1.setName("Obesité");
dataSeries2.setName("Maigreur");
dataSeries3.setName("Poids Ideale");
dataSeries4.setName("Surpoids");

dataSeries5.setName("Evolution de votre Poids");
dataSeries6.setName("Obesité Sévére");
dataSeries7.setName("Obesité Sévére");
LineChart lineChart = new LineChart(xAxis, yAxis);
lineChart.setTitle("Evolutions de poids selon les valeurs de references");

for(int i=0;i<SR.size();i++){
dataSeries1.getData().add(new XYChart.Data(i,poidIdeal(client,SR.get(i).getTaille(),30.0)));
dataSeries2.getData().add(new XYChart.Data(i,poidIdeal(client,SR.get(i).getTaille(),17.5)));
dataSeries3.getData().add(new XYChart.Data(i,poidIdeal(client,SR.get(i).getTaille(),20.0)));
dataSeries4.getData().add(new XYChart.Data(i,poidIdeal(client,SR.get(i).getTaille(),25.0)));
dataSeries5.getData().add(new XYChart.Data(i,SR.get(i).getPoid()));
dataSeries6.getData().add(new XYChart.Data(i,poidIdeal(client,SR.get(i).getTaille(),35.0)));
dataSeries7.getData().add(new XYChart.Data(i,poidIdeal(client,SR.get(i).getTaille(),40.0)));
}
lineChart.getData().addAll(dataSeries1,dataSeries2,dataSeries3,dataSeries4,dataSeries5,dataSeries6,dataSeries7);
lineChart.setAnimated(true);
        sp.setContent(lineChart);
    } 
}

