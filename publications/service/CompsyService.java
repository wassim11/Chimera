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
import com.mycompany.entities.ComPsy;
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
public class CompsyService {
       public static CompsyService instance = null;
    
    private ConnectionRequest req;
    
    public static CompsyService getInstance() {
        if (instance == null)
            instance = new CompsyService();
        return instance ;
    }
            
      public CompsyService()
      {
           req = new ConnectionRequest();
           
      }
        public void ajoutComPsy(ComPsy ComPsy)
        {
            req = new ConnectionRequest();
            String url = Statics.BASE_URL+"/commentairepsy/newcompsy/"+ComPsy.getId_publication2()+"/?cmt="+ComPsy.getContenu();
            req.setHttpMethod("GET");
            req.setUrl(url);
            req.addResponseListener( (e) -> {
                String str = new String(req.getResponseData());
                System.out.println("data =="+str);
                });
                NetworkManager.getInstance().addToQueueAndWait(req);
                
        }
        
        public ArrayList<ComPsy>affichageCompsys(int idPub) {
                       req = new ConnectionRequest();
ArrayList<ComPsy> result = new ArrayList<>();
String url = Statics. BASE_URL+"/commentairepsy/affichagecompsy/"+idPub;
req.setHttpMethod("GET");
req.setUrl(url);
req.addResponseListener (new ActionListener<NetworkEvent>() {  
@Override

public void actionPerformed (NetworkEvent evt) {
JSONParser jsonp;
jsonp = new JSONParser();
try {
Map<String, Object>mapCompsys = jsonp.parseJSON (new CharArrayReader (new String (req.getResponseData() ).toCharArray()));
List<Map<String, Object>> listOfMaps =(List<Map<String, Object>>) mapCompsys.get("root");

for (Map<String, Object> obj : listOfMaps) {

ComPsy re = new ComPsy();
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
    
    



