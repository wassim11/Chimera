/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Pubphy;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wassim
 */
public class PubphyService {
    public static PubphyService instance = null;
    
    private ConnectionRequest req;
    
    public static PubphyService getInstance() {
        if (instance == null)
            instance = new PubphyService();
        return instance ;
    }
            
      public PubphyService()
      {
           req = new ConnectionRequest();
           
      }
        public void ajoutPubPhy(Pubphy pubphy)
        {
            req = new ConnectionRequest();
            String url = Statics.BASE_URL+"/publication/physique/newPublicationPhysique?type="+pubphy.getType()+"&img="+pubphy.getImg()+"&description="+pubphy.getDescription()+"&nom="+pubphy.getNom()+"&duree="+pubphy.getDuree()+"&repetition="+pubphy.getRepetition();
     req.setUrl(url);
            req.addResponseListener( (e) -> {
                String str = new String(req.getResponseData());
                System.out.println("data =="+str);
                });
                NetworkManager.getInstance().addToQueueAndWait(req);
            
                    }
        
        public ArrayList<Pubphy>affichagePubphys() {
                        req = new ConnectionRequest();
ArrayList<Pubphy> result = new ArrayList<>();
String url = Statics. BASE_URL+"/publication/physique/affichagephy";
req.setUrl(url);
req.setHttpMethod("GET");
req.addResponseListener (new ActionListener<NetworkEvent>() {  
@Override

public void actionPerformed (NetworkEvent evt) {
JSONParser jsonp;
jsonp = new JSONParser();
try {
Map<String, Object>mapPubphys = jsonp.parseJSON (new CharArrayReader (new String (req.getResponseData() ).toCharArray()));
List<Map<String, Object>> listOfMaps =(List<Map<String, Object>>) mapPubphys.get("root");

for (Map<String, Object> obj : listOfMaps) {

Pubphy re = new Pubphy();
//dima id fi codename one float Southouha
float id = Float.parseFloat(obj.get("id").toString());
String type = obj.get("type").toString();
String description = obj.get ("description").toString();
String img = obj.get ("img").toString();
String nom = obj.get ("nom").toString();
double repetition = (Double.parseDouble(obj.get("repetition").toString()));
double duree = (Double.parseDouble(obj.get("duree").toString()));

re.setId((int) id);
re.setType(type);
re.setDescription(description);
re.setImg(img);
re.setNom(nom);
re.setRepetition((int)repetition);
re.setDuree((int)duree);

result.add(re);
  
}
    
    
}   catch (IOException ex) {
ex.printStackTrace();
}

}
});


NetworkManager.getInstance().addToQueueAndWait(req);
return result;
}

public Pubphy DetailPubphy( int id) {
                            req = new ConnectionRequest();
String url = Statics.BASE_URL+"/publication/physique/detailpubphy?id="+id;
req.setUrl(url);
req.setHttpMethod("GET");
Pubphy pubphy = new Pubphy();
req.addResponseListener(new ActionListener<NetworkEvent>() {  
@Override

public void actionPerformed (NetworkEvent evt) {
JSONParser jsonp;
jsonp = new JSONParser();
String str = new String (req.getResponseData());
try {
Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
pubphy.setType(obj.get("type").toString());
pubphy.setDescription(obj.get("description").toString());
pubphy.setImg(obj.get("img").toString());
pubphy.setNom(obj.get("nom").toString());
double repetition = (Double.parseDouble(obj.get("repetition").toString()));
double duree = (Double.parseDouble(obj.get("duree").toString()));
pubphy.setRepetition((int) repetition);
pubphy.setDuree((int) duree);
}
catch (IOException ex) {
ex.printStackTrace();
}
}
});

NetworkManager.getInstance().addToQueueAndWait(req);
return pubphy ;


}




}
    
    

