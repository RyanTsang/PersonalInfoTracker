/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pers.ryan.personalinfotracer.dao;

import pers.ryan.personalinfotracer.domain.Person;

/**
 *
 * @author Ryan Tsang
 */
public interface PersonDao {
    
    public int addPerson(Person p);
    
    public int updatePerson(Person p);
    
    public int delPerson(int id);
    
    public String[] findHead();
    
    public Person findPersonById(int id);
    
    public Person[] findPersonByName(String name);
    
    public Person[] findAllPerson();

    public int addAlldisplayingPeople(Person[] displayingPeople);
}
