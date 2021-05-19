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
import com.mycompany.entities.Com;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wassim
 */
public class ComService {
           public static ComService instance = null;
    
    private ConnectionRequest req;
    
    public static ComService getInstance() {
        if (instance == null)
            instance = new ComService();
        return instance ;
    }
            
      public ComService()
      {
           req = new ConnectionRequest();
           
      }
        public void ajoutComPhy(Com Com)
        {
                                        req = new ConnectionRequest();
            String url = Statics.BASE_URL+"/commentaire/newcom/"+Com.getId_publication()+"/?cmt="+Com.getContenu();
            req.setUrl(url);
                        req.setHttpMethod("GET");

            req.addResponseListener( (e) -> {
                String str = new String(req.getResponseData());
                System.out.println("data =="+str);
                });
                NetworkManager.getInstance().addToQueueAndWait(req);
            
                    }
        
        public ArrayList<Com>affichageComs(int idPub) {
req = new ConnectionRequest();
req.setPost(false);
ArrayList<Com> result = new ArrayList<>();
String url = Statics. BASE_URL+"/commentaire/affichagecom/"+idPub;
req.setUrl(url);
req.addResponseListener (new ActionListener<NetworkEvent>() {  
@Override

public void actionPerformed (NetworkEvent evt) {
JSONParser jsonp;
jsonp = new JSONParser();
try {
Map<String, Object>mapComs = jsonp.parseJSON (new CharArrayReader (new String (req.getResponseData() ).toCharArray()));
List<Map<String, Object>> listOfMaps =(List<Map<String, Object>>) mapComs.get("root");

for (Map<String, Object> obj : listOfMaps) {

Com re = new Com();
//dima id fi codename one float Southouha
float id = Float.parseFloat(obj.get("id").toString());
String contenu = obj.get ("contenu").toString();
String date = obj.get ("date").toString();


re.setId((int) id);
re.setContenu(contenu);
re.setDate(date);


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



}
