import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.UUID;

public class TreeParser {

	private static StringTokenizer fieldParser;
	private static StringTokenizer listParser; 
	private static String fieldDelim = "-";
	private static String listDelim = ",";
	private static ArrayList<String> tokenizedString;
	
	/**
	 * 
	 * @param rawString : string to tokenize
	 * @param delim : 
	 * @return tokenized string in ArrayList with following elements
	 * 0. UUID
	 * 1. name
	 * 2. DOB
	 * 3. Gender
	 * 4. Relation 1
	 * 5. Person name 1
	 * 6. Relation 2 
	 * 7. Person name 2 
	 * ------------------------- 
	 * 3. mom -- removed
	 * 4. dad -- removed
	 * 5. siblings -- removed
	 * 6. spouse(s) -- removed
	 */
	static ArrayList<String> tokenizeString(String rawString, String delim)
	{
		tokenizedString = new ArrayList<String>();
		fieldParser = new StringTokenizer(rawString,delim );
		
//		for(int i = 0; fieldParser.hasMoreTokens(); i++)
//		{
//			tokenizedString.add(fieldParser.nextToken());
//			if(tokenizedString.get(i).contains(";"))
//			{
//				
//			}
//		}
		
		while(fieldParser.hasMoreTokens())
		{
			tokenizedString.add(fieldParser.nextToken());
		}
		return tokenizedString;	
	}
	
	static ArrayList<String> tokenizeStringFromFile(String fileString)
	{
		return tokenizeString(fileString, fieldDelim);
	}
	
	static ArrayList<ArrayList<String>> tokenizeStringFromFileRecusive (String fileString)
	{
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();		
		ArrayList<String> tokenizedString = tokenizeStringFromFile(fileString);
		
		for(int i = 0 ; i<tokenizedString.size(); i++)
		{
			if(tokenizedString.get(i).contains(listDelim))
				temp.add(tokenizeString(tokenizedString.get(i), listDelim));
			else
			{
				temp.add(new ArrayList<String>());
				temp.get(i).add(tokenizedString.get(i));
				printArrayList(temp.get(i)); //testing
			}
		}
		
		return temp;
	}
	
	private static void printArrayList(ArrayList<String> temp)//testing
	{
		for(int i = 0; i<temp.size(); i++)
			System.out.println(temp.get(i));
		System.out.println();
	}
	
	public static Person parsePerson(ArrayList<String> strPerson)
	{
		Person temp = new Person(strPerson.get(1));
		temp.setPersonId(UUID.fromString(strPerson.get(0)));
		//finish this; rest of the person fields
		return temp;
		
		
	}
}
