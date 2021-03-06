package net.argus.example;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.argus.security.Key;
import net.argus.system.InitializationSystem;
import net.argus.util.debug.Debug;

@SuppressWarnings("deprecation")
public class ExampleSecurity {
	
	public ExampleSecurity() {
		Key key = new Key("^m$�*�*�[*^f�g*sFDHfdg^d*�*�%�%H��P�k;f");
		
		String text = key.crypt("\"download: {");
		Debug.log("Text cripted: " + text);
		
		Debug.log("Text uncripted: " + key.decrypt(text));
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		InitializationSystem.initSystem(args);
		new ExampleSecurity();
	}

}
