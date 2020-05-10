/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookoauth;

/**
 *
 * @author olegb
 */
public class AccountPages {
    private String tocken;
    private String name;
    private String id;
    private String category;
    
    public AccountPages(String tocken, String name, String id, String category) {
        this.tocken = tocken;
        this.name = name;
        this.id = id;
        this.category = category;
    }
    
    public String getTocken() {
        return tocken;
    }
    
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getCategory() {
        return category;
    }
}
