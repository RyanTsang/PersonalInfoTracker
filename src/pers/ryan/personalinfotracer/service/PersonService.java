/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pers.ryan.personalinfotracer.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import pers.ryan.personalinfotracer.dao.PersonDao;
import pers.ryan.personalinfotracer.dao.ipml.PersonDaoImpl;
import pers.ryan.personalinfotracer.domain.Person;

/**
 *
 * @author Ryan Tsang
 */
public class PersonService {
    
    PersonDao pd = null;
    
    public Person[] searchByName(String name){
        pd = new PersonDaoImpl();
        return pd.findPersonByName(name);
    }
    
    public Person[] searchByMonthOfDob(int month){
        pd = new PersonDaoImpl();
        Person[] allPeople = pd.findAllPerson();
        if(allPeople == null){
            return null;
        }
        List<Person> personList = new ArrayList<Person>();
        Person[] result = null;
        for(Person p: allPeople){
            Calendar cal = Calendar.getInstance();
            cal.setTime(p.getDOB());
            if(month == cal.get(Calendar.MONTH)){
                personList.add(p);
            }
        }
        result = new Person[personList.size()];
        return personList.toArray(result);
    }
    
    public int delPerson(Person p){
        pd = new PersonDaoImpl();
        return pd.delPerson(p.getId());
    }
    
    public int addPerson(Person p){
        pd = new PersonDaoImpl();
        return pd.addPerson(p);
    }
    
     public int updatePerson(Person p){
         pd = new PersonDaoImpl();
        return pd.updatePerson(p);
    }

    public int saveAllPeople(Person[] displayingPeople) {
        pd = new PersonDaoImpl();
        return pd.addAlldisplayingPeople(displayingPeople);
    }
}
