/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pers.ryan.personalinfotracer.dao.ipml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import pers.ryan.personalinfotracer.dao.PersonDao;
import pers.ryan.personalinfotracer.domain.Person;
import pers.ryan.personalinfotracer.utils.CsvUtil;

/**
 *
 * @author Ryan
 */
public class PersonDaoImpl implements PersonDao{
    
    private static String SEPARATOR = "&~&";
    private String fileName = null;
    
    /***
     * 1.  
     */
    public PersonDaoImpl(){
        InputStream in = null;
        try {
            in = new FileInputStream(System.getProperty("user.dir")+"/application.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PersonDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException ex) {
            Logger.getLogger(PersonDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        fileName = p.getProperty("lastOpenFile");
        
        if(CsvUtil.isCsvFileExists(fileName)){
        }else{
            CsvUtil.createCsvFile(fileName);
        }
    }
    
    public int addPerson(Person p) {
        int impactedRowsNum = 0;

        if(!CsvUtil.hasHead(fileName)){
            CsvUtil.writeHead(Person.class, fileName);
        }else{
    }
        p.setId(findUnusedId(fileName));
        CsvUtil.writeRow(toArray(p), fileName);
        return impactedRowsNum ++;
    }

    public int updatePerson(Person p) {
        String[][] rows = null;
        int id = p.getId();
        int impactedRowNum = 0;
        rows = CsvUtil.readAllRow(fileName);
        CsvUtil.writeHead(Person.class, fileName);
        for(String[] row: rows){
            if(Integer.parseInt(row[0]) == id){
                row = toArray(p);
                impactedRowNum++;
            }
            CsvUtil.writeRow(row, fileName);
        }
        return impactedRowNum;
    }

    public int delPerson(int id) {
        String[][] rows = null;
        int impactedRowNum = 0;
        rows = CsvUtil.readAllRow(fileName);
        CsvUtil.writeHead(Person.class, fileName);
        for(String[] row: rows){
            if(Integer.parseInt(row[0]) == id){
                impactedRowNum++;
                continue;
            }
            CsvUtil.writeRow(row, fileName);
        }
        return impactedRowNum;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String[] toArray(Person p) {
        String dateString = "";
        String likeString = "";
        String disLikeString = "";
        
        likeString = stringArrayToString(p.getLikes());
        disLikeString = stringArrayToString(p.getDislikes());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateString = sdf.format(p.getDOB());
        
        String[] row = {String.valueOf(p.getId()) , p.getName(), likeString, disLikeString, dateString};
        return row;
    }
    
    private String stringArrayToString(String[] strings){
        String result = ""; 
        for(String s: strings){
            result += (s + SEPARATOR);
        }
        result = result.substring(0, result.length() - SEPARATOR.length());
        return result;
    }
    
        private String[] stringToStringArray(String string){
        String[] result =  string.split(SEPARATOR);
        for(int i = 0; i < result.length; i++){
            result[i] = result[i].trim();
        }
        return result;
    }
    
    private Person stringArrayToPerson(String[] row){
        Person p = null;
        if(row != null){
            p = new Person();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            p.setId(Integer.parseInt(row[0]));
            p.setName(row[1]);
            p.setLikes(stringToStringArray(row[2]));
            p.setDislikes(stringToStringArray(row[3]));
        try {
            p.setDOB(sdf.parse(row[4]));
        } catch (ParseException ex) {
            throw new RuntimeException("DOB can't be changed to Date type. ");
        }
        }
        return p;
    }
            
    public String[] findHead() {
        String[] result = null;
        if(CsvUtil.hasHead(fileName)){
            result = CsvUtil.readHead(fileName);
        }else{
            throw new RuntimeException("The file doesn't have a head.");
        }   
        return result;
    }

    public Person findPersonById(int id) {
        String[] row = CsvUtil.readRow(fileName, id);
        return stringArrayToPerson(row);
    }

    public Person[] findPersonByName(String name) {
        String[][] rows = CsvUtil.readAllRow(fileName);
        List<Person> personList = new ArrayList<Person>();
        Person[] person = null;
        if(rows != null){
            for(String[] row: rows){
                if(row[1].toLowerCase().contains(name.toLowerCase())){
                    personList.add(stringArrayToPerson(row));
                }
            }
        }
        person = new Person[personList.size()];
        return personList.toArray(person);
    }

    public Person[] findAllPerson() {
        Person[] person = null;
        String[][] rows = CsvUtil.readAllRow(fileName);
        if(rows != null){
            person = new Person[rows.length];
            for(int i = 0; i < rows.length; i++){
            person[i] = stringArrayToPerson(rows[i]);
            }
        }
        return person;
    }

    private int findUnusedId(String fileName) {
        int id = 0;
        while(CsvUtil.readRow(fileName, id) != null){
            id++;
        }
        return id;
    }

    public int addAlldisplayingPeople(Person[] displayingPeople) {
        int impactedRowNum = 0;
        CsvUtil.writeHead(Person.class, fileName);
        for(Person p : displayingPeople){
            CsvUtil.writeRow(toArray(p), fileName);
            impactedRowNum++;
        }
        return impactedRowNum;
    }
}