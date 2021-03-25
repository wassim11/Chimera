/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.entities.centre;

/**
 *
 * @author amira
 */
public class StatService {
    
    private int nbrService;
    private Centre c;

    public void setNbrService(int nbrService) {
        this.nbrService = nbrService;
    }

    public void setC(Centre c) {
        this.c = c;
    }

    public int getNbrService() {
        return nbrService;
    }

    public Centre getC() {
        return c;
    }
    
    
    
}
