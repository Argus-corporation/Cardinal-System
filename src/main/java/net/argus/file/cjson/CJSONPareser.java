package net.argus.file.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.file.CJSONFile;
import net.argus.util.ArrayManager;

@Deprecated
public class CJSONPareser {
	
	public static CJSON parse(char[] chars) {
		List<CJSONItem> items = new ArrayList<CJSONItem>();
		
		while(ArrayManager.isExist(chars, 0)) {
			items.add(CJSONItem.nextItem(chars));
			chars = removeFirstItem(chars);
		}
		
		return new CJSON(items);
		
	}
	
	public static CJSON parse(String file) {
		return parse(getFile(file));
	}
	
	public static CJSON parse(CJSONFile file) {
		return parse(getFile(file.toArray()));
	}
	
	private static char[] getFile(String file) {
		return getFile(new String[] {file});
	}
		
	private static char[] getFile(String[] file) {
		boolean valid = false;
		String lines = "";
		
		for(String str : file)
			lines += str;
		
		char[] characters = lines.toCharArray();
		characters = ArrayManager.remove(characters, '\t');
		
		while(!valid) {
			boolean strOpen = false;
			valid = true;
			
			for(int i = 0; i < characters.length; i++) {
				if(characters[i] == '\\') {
					i += 2;
				}
				
				if(characters[i] == '"') {
					strOpen = !strOpen;
				}
				
				if(characters[i] == ' ' && !strOpen) {
					characters = ArrayManager.remove(characters, i);
					valid = false;
					break;
				}
			}
		}
		return characters;
	}
	
	public static char[] removeFirstItem(char[] chars) {
		for(int i = 0, count = 0; i < chars.length; i++) {
			if(chars[i] == '{')
				count++;
			
			if(chars[i] == '}') {
				count--;
				if(count == 0) {
					chars = ArrayManager.remove(chars, i + 1, chars.length);
					i = chars.length;
				}
			}
		}
		return chars;
	}
	
	public static char[] removeNextItem(char[] chars) {
		for(int i = 0, count = 0; i < chars.length; i++) {
			if(chars[i] == '{')
				count++;
			
			if(chars[i] == '}') {
				count--;
				if(count == 0) {
					chars = ArrayManager.remove(chars, 0, i + 1);
					i = chars.length;
				}
			}
		}
		return chars;
	}
	
	public static char[] removeFirstArray(char[] chars) {
		for(int i = 0, count = 0; i < chars.length; i++) {
			if(chars[i] == '[')
				count++;
			
			if(chars[i] == ']') {
				count--;
				if(count == 0) {
					chars = ArrayManager.remove(chars, i + 1, chars.length);
					i = chars.length;
				}
			}
		}
		return chars;
	}
	
	public static char[] removeNextArray(char[] chars) {
		for(int i = 0, count = 0; i < chars.length; i++) {
			if(chars[i] == '[')
				count++;
			
			if(chars[i] == ']') {
				count--;
				if(count == 0) {
					chars = ArrayManager.remove(chars, 0, i + 1);
					i = chars.length;
				}
			}
		}
		return chars;
	}

}
