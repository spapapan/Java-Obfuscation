package pkg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Config {
	
	private ArrayList<ArrayList> lists;
	
	public Config()
	{
		lists = getRandomNamesIndexList();
	}

	public ArrayList<NameItem> getConfig()
	{
		ArrayList<NameItem> list = new ArrayList<>(); 
		ArrayList<String> names = getNames();
		
		for (String name : names)
		{
			String randomString = getRandomString();
			list.add(new NameItem(name,randomString));
			System.out.println(name + " , " + randomString);
		}
		 
		return list;
	}
	

	//Get  variable and method names
	private static ArrayList<String> getNames()
	{
		ArrayList<String> finalNames = new ArrayList<>();
		String names=""; 
		ArrayList<String> namesList = getText(Main.varAndMethodNames);
		for (String s : namesList)
		{
			names = names + s;
		}
		
		//Remove duplicates
		String[] nameArray = names.split(",");
		for (int i=0; i<nameArray.length; i++)
		{
			for (int j=0; j<nameArray.length; j++)
			{
				if (i!=j)
				{  
					if (nameArray[i].equals(nameArray[j]))
					{
						System.out.println("Bazinga: " + nameArray[i] );
						nameArray[i] = "";
						nameArray[j] = "";
					}
				}
			}
		}
		
		for (int i=0; i<nameArray.length; i++)
		{
			if (!nameArray[i].equals(""))
			{
				finalNames.add(nameArray[i]);
			}
		}
		
		return finalNames;
	}

	//Get text from file
	public static ArrayList<String> getText(String fileName)
	{
		File file = new File(fileName);
		ArrayList<String> list = new ArrayList<>(); 
		BufferedReader reader = null;

		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;

		    while ((text = reader.readLine()) != null) { 
		    	text =  removeComments(text);
		    	text = removeWhiteSpace(text);
		    	if (!text.equals(""))
		    	{
		    		list.add(text);
		    	} 
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
		
		return list;
	}
	
	//Remove white space
	public static String removeWhiteSpace(String lineText)
	{
		String text = lineText.replace(" ", "");
		if (text.equals(""))
			return "";
		else
			return lineText;
	}
	
	//Remove comments
	public static String removeComments(String lineText)
	{
		return (lineText.contains("//")) ? lineText.substring(0,lineText.indexOf("//")) : lineText; 
	}

	//Create and write to text file
	public static void createTextFile(ArrayList<String> obfuscatedText)
	{ 
		PrintWriter writer=null;
        try { 
        	String fileName = "Outputs/" + Main.javaClassName;
            writer = new PrintWriter(fileName, "UTF-8");
            for (String textLine : obfuscatedText)
            { 
                writer.println(textLine); 
            }  
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
          if ( writer != null ) {
        	  writer.close();
          }
        }
	}
	
	
	//Get random String
	private String getRandomString()
	{ 
		String temp_res = "";
		int max = lists.get(0).size(); 
		int pointer;
		
		for (int i=0; i<Main.OBFUSCATED_STRING_LENGTH; i++)
		{
			pointer = ThreadLocalRandom.current().nextInt(0, max);
			temp_res = temp_res + lists.get(i).get(pointer);
		}
		
		String bazinga = "a" + temp_res;
		
		return bazinga; 
	}
	
	private ArrayList<ArrayList> getRandomNamesIndexList()
	{ 
		ArrayList<ArrayList> lists = new ArrayList<ArrayList>();
	    ArrayList<String> list = new ArrayList<String>();
		//For all lists
	    for (int i=0; i<Main.OBFUSCATED_STRING_LENGTH; i++)
		{
			list = new ArrayList<String>();
			
			//For all numbers
			for (int j=0; j< 10; j++)
			{
				list.add(Integer.toString(j));
			}
			
			//For all letters
			char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			for (int j=0; j<alphabet.length; j++)
			{
				list.add(Character.toString(alphabet[j]));
			}
			
			//For all uppercase letters
			char[] uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			for (int j=0; j<uppercase.length; j++)
			{
				list.add(Character.toString(uppercase[j]));
			}
		  
			lists.add(list);
		}
	    
	    return lists;        
	}
}
