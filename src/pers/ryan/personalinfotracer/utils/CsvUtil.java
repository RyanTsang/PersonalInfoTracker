/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pers.ryan.personalinfotracer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.codegen.CompilerConstants;

/**
 *
 * @author Ryan Tsang
 */
public class CsvUtil {
    private static String COMMA_DELIMITER = ",";
    private static String NEW_LINE_SEPARATOR = "\n";
    
    public static boolean isCsvFileExists(String fileName){
        File file = new File(fileName);
        return file.exists();
    }
    
    public static boolean createCsvFile(String fileName){
        File file = new File(fileName);
        try {
            return file.createNewFile();
        } catch (IOException ex) {
            return false;
        }
    }
   
   public static int writeHead(Class clazz, String fileName){
       Field[] fields = clazz.getDeclaredFields();
       String[] heads = new String[fields.length];
       FileWriter fileWriter = null;
       int impactedRowNum = 0;
       for(int i = 0; i < fields.length; i++){
           heads[i] = fields[i].getName();
       }
       try {
           fileWriter = new FileWriter(fileName);
           for(int i = 0; i < heads.length; i++){
               fileWriter.write(heads[i]);
               if(i != (heads.length-1)){
               fileWriter.write(COMMA_DELIMITER);
           }else{
               fileWriter.write(NEW_LINE_SEPARATOR);
               impactedRowNum++;
           }
       }
       } catch (IOException ex) {
           System.out.println(ex.getStackTrace());
       }finally {
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error while flushing/closing fileWriter !!!");
            e.printStackTrace();
        }
        }

       return impactedRowNum;
   }
   
   /***
    * Very time invoke this function, clean the file.
    * @param items
    * @param fileName
    * @return 
    */
   public static int writeRow(String[] items, String fileName){
       FileWriter fileWriter = null;

       int impactedRowNum = 0;
       try {
           fileWriter = new FileWriter(new File(fileName), true);
           for(int i = 0; i < items.length; i++){
           fileWriter.append(items[i]);
           if(i != (items.length-1)){
               fileWriter.append(COMMA_DELIMITER);
           }else{
               fileWriter.append(NEW_LINE_SEPARATOR);
               impactedRowNum++;
           }
       }
       } catch (IOException ex) {
           Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
       }finally {
        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error while flushing/closing fileWriter !!!");
            e.printStackTrace();
        }
        }
       
       return impactedRowNum;
   }
   
   
   public static boolean hasHead(String fileName){
       BufferedReader reader = null; 
       Boolean result = false;
       try {
            reader = new BufferedReader(new FileReader(fileName));
            String s = reader.readLine();
            if(!("".equals(s) || s == null)){
                result = true;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           try {
               reader.close();
           } catch (IOException ex) {
               Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return result;
   }
   
   public static String[] readHead(String fileName){
       BufferedReader reader = null; 
       String[] heads = null;
       try {
            reader = new BufferedReader(new FileReader(fileName));
            String s = reader.readLine();
            s = s.replaceAll("/n", "");
            heads = s.split(COMMA_DELIMITER);
       }catch (FileNotFoundException ex) {
            Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           try {
               reader.close();
           } catch (IOException ex) {
               Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return heads;
   }
   
   /***
    * Only support those entities which contain id. 
    * @param fileName
    * @param index
    * @return 
    */
   public static String[] readRow(String fileName, int index){
       BufferedReader reader = null; 
       String[] row = null;
       String currentRow = "";
       try {
           reader = new BufferedReader(new FileReader(fileName));
           reader.readLine();
            while((currentRow = reader.readLine()) != null){
                row = currentRow.split(COMMA_DELIMITER);
                if(Integer.parseInt(row[0]) == index){
                return row;
                }
       }
       } catch (Exception e) {
           e.printStackTrace();
       }finally{
           try {
               reader.close();
           } catch (IOException ex) {
               Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return null;
   }
   
   public static String[][] readAllRow(String fileName){
       BufferedReader reader = null; 
       String[][] rows = null;
       String[] row= null;
       List<String[]> rowList = new ArrayList<String[]>();
       try {
            reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();
            String thisLine = "";
            while((thisLine = reader.readLine()) != null){
                thisLine = thisLine.replaceAll("/n", "");
                row = thisLine.split(COMMA_DELIMITER);
                rowList.add(row);
            }
       }catch (FileNotFoundException ex) {
            Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           try {
               reader.close();
           } catch (IOException ex) {
               Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       if(rowList != null && row != null){
            rows = new String[rowList.size()][row.length];
            rows = rowList.toArray(rows);
       }
       return rows;
   }
   
//   private static FileWriter getExistFileWriter(String fileName){
//       BufferedReader reader = null;
//        String allText = "";
//        String currentLine = "";
//        FileWriter fileWriter = null;
//        try {
//            reader = new BufferedReader(new FileReader(fileName));
//            while((currentLine = reader.readLine()) != null){
//                allText += currentLine + "\n";
//            }
//            fileWriter = new FileWriter(fileName);
//            fileWriter.write(allText);
//        } catch (Exception ex) {
//            Logger.getLogger(CsvUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return fileWriter;
//   }
//   public static String[] createHeads(Class clazz){
//       String[] heads = null;
//       Field[] fields =  clazz.getDeclaredFields();
//       for(int i = 0; i < fields.length; i++){
//           heads = new String[fields.length];
//           heads[i] = fields[i].getName();
//       }
//       return heads;
//   }
}
