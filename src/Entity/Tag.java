/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


/**
 *
 * @author Arshavin
 */
public class Tag {

    private Integer id_tag;
    private String name;

    public Integer getId_tag() {
        return id_tag;
    }

    public void setId_tag(Integer id_tag) {
        this.id_tag = id_tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" + "id_tag=" + id_tag + ", name=" + name + '}';
    }

}
