import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.Scanner;

public class TreeReader {

	/*
	 * Structure of each file:
	 * name of the file will the family tree name 
	 * each row will contain the data of a person in that family 
	 */
	File treeFile;
	static Scanner reader; 
	
	
	TreeReader (String treeFile) throws FileNotFoundException
	{
		this.treeFile = new File(treeFile);
		reader = new Scanner(this.treeFile);
	}
	
	ArrayList<String> readPerson()
	{
		if(reader.hasNextLine())
			return TreeParser.tokenizeStringFromFile(reader.nextLine());
		else
			System.out.println("no line to read");
		return null;
	}
	/**
	 * 
	 * @return ArrayList of String :  contains all the tree files in the local directory
	 */
	static ArrayList<String> getTreeListNames()
	{
		File[] listOfFiles = (new File(".")).listFiles();
		ArrayList<String> listOfTrees = new ArrayList<String>();
		for (File file : listOfFiles) {
			if(file.getName().endsWith(Tree.FILE_EXTENSION))
			listOfTrees.add(file.getName());
		}
		return listOfTrees;
	}
	
	/**return a full tree
	 * 
	 * @param treeName
	 * @return
	 */
	Tree readTree(String treeName)
	{
		Tree tempTree = new Tree(treeName);
		while(reader.hasNextLine())
		//	tempTree.insertPerson(new Person(readPerson()));
			System.out.println("inserting person");
		return tempTree;
	}
	
	/**
	 * not needed
	 */
	static void readPersonFromTree(String treeFile) throws IOException
	{
		Person tempPerson ; 
		File temp = new File(treeFile+Tree.FILE_EXTENSION);
		FileInputStream eyes = new FileInputStream(temp);
		ObjectInputStream objectIn = null;
		try {	
			objectIn = new ObjectInputStream(eyes);
			tempPerson = (Person) objectIn.readObject();
			tempPerson.printData();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(eyes != null)
				eyes.close();
		}
	}
	
	/**
	 * 
	 * @param treeFile
	 * @return
	 * @throws IOException
	 */
	static Tree readTreeFromFile(String treeFile) throws IOException
	{
		
		Tree tempTree = null; 
		//File temp = new File();
		ObjectInputStream objectIn= null;
		try{
			FileInputStream eyes = new FileInputStream(treeFile);
			objectIn = new ObjectInputStream(eyes);;
			tempTree = (Tree) objectIn.readObject();
		}catch (OptionalDataException e	)
		{
			if (!e.eof) throw e;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		// should not be needed because of try-with-resources
		finally {
			if(objectIn != null)
				objectIn.close();
//			eyes.close();
		}
		
		return tempTree;
	}

}
