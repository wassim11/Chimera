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
import com.mycompany.entities.Pubpsy;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wassim
 */
public class PubpsyService {
      public static PubpsyService instance = null;
    
    private ConnectionRequest req;
    
    public static PubpsyService getInstance() {
        if (instance == null)
            instance = new PubpsyService();
        return instance ;
    }
            
      public PubpsyService()
      {
           req = new ConnectionRequest();
           
      }
        public void ajoutPubPsy(Pubpsy pubpsy)
        {
            req = new ConnectionRequest();
            String url = Statics.BASE_URL+"/publication/psy/newPublicationPsychique?type="+pubpsy.getType()+"&img="+pubpsy.getImg()+"&description="+pubpsy.getDescription()+"&sujet="+pubpsy.getSujet();
     req.setUrl(url);
            req.addResponseListener( (e) -> {
                String str = new String(req.getResponseData());
                System.out.println("data =="+str);
                });
                NetworkManager.getInstance().addToQueueAndWait(req);
            
                    }
        
        public ArrayList<Pubpsy>affichagePubpsys() {
            req = new ConnectionRequest();
ArrayList<Pubpsy> result = new ArrayList<>();
String url = Statics. BASE_URL+"/publication/psy/affichagepsy";
req.setUrl(url);
req.setHttpMethod("GET");
req.addResponseListener (new ActionListener<NetworkEvent>() {  
@Override
public void actionPerformed (NetworkEvent evt) {
JSONParser jsonp;
jsonp = new JSONParser();
try {
Map<String, Object>mapPubpsys = jsonp.parseJSON (new CharArrayReader (new String (req.getResponseData() ).toCharArray()));
List<Map<String, Object>> listOfMaps =(List<Map<String, Object>>) mapPubpsys.get("root");

for (Map<String, Object> obj : listOfMaps) {

Pubpsy re = new Pubpsy();
//dima id fi codename one float Southouha
float id = Float.parseFloat(obj.get("id").toString());
String type = obj.get("type").toString();
String description = obj.get ("description").toString();
String img = obj.get ("img").toString();
String sujet = obj.get ("sujet").toString();


re.setId((int) id);
re.setType(type);
re.setDescription(description);
re.setImg(img);
re.setSujet(sujet);

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

public Pubpsy DetailPubpsy( int id ) {
    req = new ConnectionRequest();
 String url = Statics.BASE_URL+"/publication/psy/detailpubpsy?id="+id;
req.setHttpMethod("GET");
req.setUrl(url);
Pubpsy pubpsy = new Pubpsy();
req.addResponseListener(new ActionListener<NetworkEvent>() {  
@Override

public void actionPerformed (NetworkEvent evt) {
JSONParser jsonp;
jsonp = new JSONParser();
String str = new String (req.getResponseData());
try {
Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
pubpsy.setType(obj.get("type").toString());
pubpsy.setDescription(obj.get("description").toString());
pubpsy.setImg(obj.get("img").toString());
pubpsy.setSujet(obj.get("sujet").toString());
    
    
}   catch (IOException ex) {
ex.printStackTrace();
}
}
});

NetworkManager.getInstance().addToQueueAndWait(req);
return pubpsy ;


}



}
