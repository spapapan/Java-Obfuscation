package pkg;

 
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Obfuscator {
	
	public final static String alphanumeric = "abcdefghijklmnopqrstuvwxyz0123456789_";

	public static void obfuscate(ArrayList<NameItem> config)
	{ 
		ArrayList<String> javaText = Config.getText(Main.javaClassName);
		ArrayList<String> obfuscatedText = obfuscateText(javaText,config);
		Config.createTextFile(obfuscatedText);
	}
	
	private static ArrayList<String> obfuscateText(ArrayList<String> javaText,ArrayList<NameItem> config)
	{
		ArrayList<String> obfuscatedLines = new ArrayList<>();
		
		for (String text : javaText)
		{
			String editText = obfuscateLineText(text,config);
			obfuscatedLines.add(editText);
		}
		
		return obfuscatedLines; 
	}
	 
	private static String obfuscateLineText(String lineText, ArrayList<NameItem> config)
	{
		for (NameItem item : config)
		{
			if (lineText.contains(item.defaultName))
			{   
				//lineText = lineText.replace(item.defaultName, item.obfuscateName);
				lineText = safeReplace(lineText,item.defaultName, item.obfuscateName);
				//break;
			}
		}
		 
		System.out.println(lineText);
		//System.out.println(item.defaultName + " , " +  item.obfuscateName);
		return lineText;
	}
	
	//Check if the name is not part of a bigger name
	private static String safeReplace(String lineText, String initialText,String replaceText)
	{
		String finalText="";
		String tempString = lineText;
		
		ArrayList<String> parts = new ArrayList<>(); 
		System.out.println("Line text: " + lineText);
		while(tempString.contains(initialText))
		{
			int nextPosition = tempString.indexOf(initialText)+ initialText.length();
			if (tempString.length()>=nextPosition)
			{
				System.out.println("yo: " + tempString); 
				if (tempString.length()>nextPosition) {
					nextPosition++; 
				} 
				String tempText = tempString.substring(0,nextPosition); 
				  
				parts.add(tempText);
				System.out.println("yi: " + tempText);
				
				tempString = tempString.substring(nextPosition,tempString.length());
				if (!tempString.contains(initialText))
					parts.add(tempString); 
			}
			else
			{
				parts.add(tempString);
				tempString="";
			} 
		}
		
		for (String part : parts)
		{
			boolean valid=true;
			int index = part.indexOf(initialText);
			if (index>0)
			{
				String beforeCharacter = String.valueOf(part.charAt(index-1));
				beforeCharacter = beforeCharacter.toLowerCase();
				if (alphanumeric.contains(beforeCharacter))
					valid=false;
			}
			int nextCharPosition = part.indexOf(initialText) + initialText.length();
			if (part.length()>nextCharPosition)
			{
				String afterCharacter = String.valueOf(part.charAt(nextCharPosition));
				afterCharacter = afterCharacter.toLowerCase();
				if (alphanumeric.contains(afterCharacter))
					valid=false;
			}
			
			if (valid)
				part = part.replace(initialText, replaceText);
			
			finalText = finalText + part;
		}
		
		return finalText;
	}

}
