/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package urllibrary;

import javax.swing.UIManager;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import javax.swing.UIManager;





/**
 *
 * @author RS
 */
public class UrlLibrary {

   
    public static void main(String[] args) {
         try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (Exception e) {
            System.out.println(e);
        }
        
       MyInterface.ff.setVisible(true);
    }
    
}
