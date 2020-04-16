/**
 *       Obfuscate a .java file
 */
package pkg;

import java.io.File;
import java.util.ArrayList;

/**
 * @author spapapan
 *
 */
public class Main {

	public final static String javaClassName = "className.java";
	public final static String varAndMethodNames = "names.txt";
	public final static int OBFUSCATED_STRING_LENGTH = 100;
 
	public Main() 
	{
		Config config = new Config();
		ArrayList<NameItem> pairs = config.getConfig();
		
		//Obfuscate
		Obfuscator.obfuscate(pairs); 
	}
 
	public static void main(String[] args) {
		new Main();
	}

}
