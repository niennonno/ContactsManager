package org.example.adityavikram.contactsmanager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aditya Vikram on 7/29/2015.
 */
public class Contact implements Serializable {
    private String CName;
    private String CNum;
    public ArrayList<String> ContactNum;

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

  public String getCNum() {
        return CNum;
    }

   public void setCNum(String CNum) {
       this.CNum = CNum;
   }
}
