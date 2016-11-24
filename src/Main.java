import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class Main{
	static Hashtable<String, Integer> WORDS = new Hashtable<>();
	static ArrayList<Table> TABLE = new ArrayList<>();
	
	private static final Set<Character> PUNCT_SET = new HashSet<>(Arrays.asList('!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-',
	        '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^',
	        '_', '`', '{', '|', '}', '~'
	));
	
	public static void main(String[] args){
		getWords("c:/Users/Server/workspace/english text/texts/1/(1).txt");
		getWords("c:/Users/Server/workspace/english text/texts/1/(2).txt");
		getWords("c:/Users/Server/workspace/english text/texts/1/(3).txt");
		getWords("c:/Users/Server/workspace/english text/texts/1/(4).txt");
		getWords("c:/Users/Server/workspace/english text/texts/1/(5).txt");
		getWords("c:/Users/Server/workspace/english text/texts/1/(6).txt");
		getWords("c:/Users/Server/workspace/english text/texts/1/(7).txt");
		
		for(String key : WORDS.keySet()) {
			TABLE.add(new Table(key, WORDS.get(key)));
	    }
		Collections.sort(TABLE, new Comp());
		
		
		for(Table t : TABLE) t.output();
	}
	
	static String getString(String path){
		String str = "";
		File f = new File(path);
        BufferedReader fin = null;
		try {
			fin = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        String line;
		try {
			while ((line = fin.readLine()) != null) str += line;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	static String removePunct(String str) {
	    StringBuilder result = new StringBuilder(str.length());
	    for (int i = 0; i < str.length(); i++) {
	        char c = str.charAt(i);
	        if (!PUNCT_SET.contains(Character.valueOf(c))) {
	            result.append(c);
	        }
	    }
	    return result.toString();
	}
	
	static void getWords(String path){
		String str = getString(path);
		str = str.replaceAll("-", " ");
		str = removePunct(str);
		String[] spl = str.split(" ");
		for (String s : spl) {
			   if (WORDS.get(s) != null) {
				   WORDS.put(s, WORDS.get(s) + 1);
			   }else{
				   WORDS.put(s, 1);
			   }
		}
	}
	
	static class Table{
		public String KEY = "";
		public int VALUE = 0;
		
		public Table(String key, int value){
			this.KEY = key;
			this.VALUE = value;
		}
		
		public void output(){
			System.out.println("(" + KEY + ")(" + VALUE + ")"); 
		}
	}
	
	static class Comp implements Comparator<Table> {
	    public int compare(Table table1, Table table2) {
	        return table2.VALUE - table1.VALUE;
	    }
	}
}
