import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.spi.CurrencyNameProvider;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.swing.text.DateFormatter;

/**
 * This class was originally created for a basic console based UI to test the functions.
 * Might be able to convert to this to a controller class.  
 * 
 */
public class TreeProgram {

	private final static int MAX_RETRIES = 10; 
	private static Scanner scan;
	private static Person root;
	private static ArrayList<Tree> treeList;
	private static Tree currTree;

	static {
		treeList = new ArrayList<Tree>();
	}
	public static void main(String[] args) 
	{

		scan = new Scanner(System.in);
		getTreeListFromFile();
		mainMenu();

	//	printListOfTrees();

		scan.close();
	}

	private static void printListOfTrees()
	{
		if(TreeReader.getTreeListNames() != null)
		{
			System.out.println("List of files");
			ArrayList<String> temp = TreeReader.getTreeListNames();
			for (String string : temp) {
				System.out.println(string);
			}
		}
		else
			System.out.println("No tree found");
	}
	
	private static void getTreeListFromFile() 
	{
		ArrayList<String> nameList = TreeReader.getTreeListNames();
		if(nameList != null)
		{
			for (String string : nameList) {
				try {
					treeList.add(TreeReader.readTreeFromFile(string));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else
			System.out.println("No tree found");
	}

	private static void mainMenu()
	{
		int choice = 0; 
		for( int i =0 ; i > -1; i++)
		{
			System.out.println("Welcome to the Family tree program");
			System.out.println("1. Access existing tree");
			System.out.println("2. Create new tree");
			System.out.println("3. exit");
			System.out.println("Enter your choice");
			choice = scan.nextInt();
			switch (choice) {
			case 1:
				pickExistingTree();
				break;
			case 2: 
				//create a new Tree object and add it to the arraylist
				createTree();
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid input. Please try again.");
			}
		}	
		
	}
	
	//show list of currently available trees and prompt the user to pick one
	private static void pickExistingTree()
	{
		if(treeList.isEmpty())
			System.out.println("No existing trees");
		else
		{
			for (Tree tree : treeList) 
				System.out.println(treeList.indexOf(tree)+". "+tree.getName());
			int choice = -1; 
			System.out.println("enter your choice ");
			try{
				choice = scan.nextInt();
			}
			catch(InputMismatchException e)
			{
				System.out.println("Invalid input");
			}
			catch(IndexOutOfBoundsException e)
			{
				System.out.println("Invalid input, not on the list");
				return;
			}
			currTree = treeList.get(choice);	
			showTreeMenu();
		}
	}
	
	private static void createTree()
	{
		System.out.println("Enter a name for the new family tree");
		String name = scan.next();
		Tree temp = new Tree(name);
		treeList.add(temp);
		TreeWriter.writeTreeToFile(temp, name);
		currTree = temp;
		showTreeMenu();
	}
	
	private static void showTreeMenu()
	{

		int choice = 0; 
		for( int i = 0; i< MAX_RETRIES ; i++)
		{
			printCurrTree();
			System.out.println("1. Add Person");
			System.out.println("2. Edit a Person");
			System.out.println("3. Navigate Tree");
			System.out.println("4. Back to Main Menu");
			System.out.println("5. View ALL");
			System.out.println("Enter your choice");
			choice = scan.nextInt();
			switch (choice) {
			case 1:
				addPerson();
				i = 0; 
				break;
			case 2:
				editPerson();
				 i = 0;
				break;
			case 3:
				navigateTree();
				 i = 0; 
				break;
			case 4:
				return;
			case 5:
				viewAllDetails(currTree);
				break;
			default:
				System.out.println("invalid choice, try again");
				break;
			}
			
		}
		System.out.println("Sorry, we got tired of waiting.  ");
		mainMenu();
	}
	
	private static void viewAllDetails(Tree treeToShow) 
	{
		int i = 0; 
		for (Person p : treeToShow.getPersonList()) {
			System.out.println("________________________________________________");
			System.out.println(i + " "+ p.getName());
			System.out.println();
			p.printData();
			System.out.println();
			p.printRelationships();
			i++;
		}
	}

	private static void printCurrTree() 
	{
		System.out.println();
		System.out.println("current tree is " +currTree.getName());
		if(currTree.getCurrPerson() != null)
			System.out.println("Current person is " + currTree.getCurrPerson().getName());
		else
			System.out.println("No one in this tree");
	}

	private static Person addPerson()
	{
		/*
		 * 		case 1: 
	
			//gender
			System.out.println("Enter in new gender");
			toChange.setGender(getGenderFromPrompt());
			break;
		case 3: 
			//dob
			toChange.setDOB(getDateFromPrompt());
			break;
		case 4: 
			//alive
			toChange.setAlive(getLifeFromPrompt());
			break;
		case 5: 
			//relationship
			break;
		default:
			System.out.println("Invalid input");
		}
		 */
		Person pTemp; 
//		Gender genGender = null;
//		Date date = null;
		System.out.println("Adding person");
		System.out.println("Enter the following details");
		System.out.println("Name: ");
		String name = scan.next();
		pTemp = new Person(name);
//		System.out.println("DOB? (DDMMYYYY) Enter no if none/unknown");
//		String DOB = scan.next();
//		if(DOB.equals("no"))
//			DOB = null;
		pTemp.setDOB(getDateFromPrompt());
		pTemp.setGender(getGenderFromPrompt());
		pTemp.setAlive(getLifeFromPrompt());
		currTree.insertPerson(pTemp);
		return pTemp;
//		else
//		{
//			try{
//				date = new Date(Long.parseLong(DOB));
//			}
//			catch(NumberFormatException e)
//			{
//				System.out.println("Not parsable to long");
//			}
//			System.out.println("Gender? M or F ? Enter no if none/unknown");
//			String strGender = scan.next();
//			if(strGender.equals("no"))
//				strGender = null;
//			else
//			{
//				if(strGender.equalsIgnoreCase("M"))
//					genGender = Gender.MALE;
//				else if(strGender.equalsIgnoreCase("F"))
//					genGender = Gender.FEMALE;
//				else
//					genGender = Gender.OTHER;
//			}
			
//		}
////		if(root == null)
//			if(DOB == null)
//			{	
//				if(genGender == null)
//				//	root = new Person(name);
//					currTree.insertPerson(new Person(name));
//				else
//			//		root = new Person(name, genGender);
//					currTree.insertPerson(new Person(name,genGender));
//			}
//			else
//				//root = new Person(name,date);
//				currTree.insertPerson(new Person(name,date));
//	
	}
	
	private static void editPerson()
	{
		System.out.println("Pick a person to edit");
		Person temp = pickAPerson(currTree);
		System.out.println("Current person details: ");
		currTree.setCurrPerson(currTree.getPersonList().indexOf(temp));
		System.out.println(currTree.getCurrPerson().getName() +" is the curre");
		temp.printData();
		System.out.println("What do you want to change?");
		showPersonMenu(temp);
	}
	
	private static void showPersonMenu(Person toChange)
	{

		int choice = 0; 
		System.out.println("--------------------------------------");
		System.out.println("1. Name");
		System.out.println("2. Gender");
		System.out.println("3. Date of Birth");
		System.out.println("4. Alive or Dead");
		System.out.println("5. Add a Relationship");
		System.out.println("6. View relationships");
		System.out.println("Enter your choice");
		choice = scan.nextInt();
		
		switch(choice)
		{
		case 1: 
			System.out.println("Enter new name");
			toChange.setName(scan.next());
			System.out.println("Name has been changed to: " + toChange.getName());
			break;
		case 2: 
			//gender
			System.out.println("Enter in new gender");
			toChange.setGender(getGenderFromPrompt());
			break;
		case 3: 
			//dob
			toChange.setDOB(getDateFromPrompt());
			break;
		case 4: 
			//alive
			toChange.setAlive(getLifeFromPrompt());
			break;
		case 5: 
			//relationship
			getNewRelationshipFromPrompt();
			break;
		case 6:
			showCurrentRelationships();
			break;
		default:
			System.out.println("Invalid input");
		}
		TreeWriter.writeTreeToFile(currTree, currTree.getName());
	}
	
	private static void showCurrentRelationships() 
	{
		System.out.println(currTree.getCurrPerson().getName() + "'s relationships are: ");
		if(currTree.getCurrPerson().checkRelationLists() == 0 )
		{
			ArrayList<BasicRelation> tempBR = currTree.getCurrPerson().getRelationName();
			ArrayList<Person> tempName = currTree.getCurrPerson().getRelationPerson();
			for(int i = 0 ; i <currTree.getCurrPerson().getRelationName().size(); i++)
			{
				System.out.println(tempName.get(i).getName() + ":" + tempBR.get(i));
			}
		}
	}
	

	private static void getNewRelationshipFromPrompt() 
	{
		int relationOpt = -1;
		int personOpt = -1; 
		Person pTemp = null;
		Person oldPerson = currTree.getCurrPerson();
		System.out.println("select a relationship to add");
		printRelationList();
		relationOpt = scan.nextInt();
		
		System.out.println("Do you want to add a new person or use an existing one");
		System.out.println("1. Add a new person");
		System.out.println("2. Use an existing person");
		personOpt = scan.nextInt();
		switch(personOpt)
		{
		case 1:
			pTemp = addPerson();
			System.out.println("assigning " + pTemp.getName() + " as " + BasicRelation.values()[relationOpt] + " to " + oldPerson.getName());
			oldPerson.addRelation(pTemp, BasicRelation.values()[relationOpt]);
			oldPerson.addReverseRelation(pTemp, BasicRelation.values()[relationOpt]);//new
			break;
		case 2: 
			System.out.println("select a person from the list");
			pTemp = pickAPerson(currTree);
			if(pTemp != null){
				System.out.println("assigning " + pTemp.getName() + " as " + BasicRelation.values()[relationOpt] + " to " + oldPerson.getName());
				oldPerson.addRelation(pTemp,  BasicRelation.values()[relationOpt]);
				oldPerson.addReverseRelation(pTemp, BasicRelation.values()[relationOpt]);
			}
			break;
		default:
			System.out.println("invalid option");
		}
	}
	
	private static void printRelationList()
	{
		int i = 0; 
		for(BasicRelation br: BasicRelation.values())
		{
			System.out.println(i+ ": "+br.toString());
			i++;
		}
//		for(Relationship.ExtendedRelation er: Relationship.ExtendedRelation.values())
//		{		
//			System.out.println(i+ ": "+er.toString());
//			i++;
//		}
			
	}

	private static boolean getLifeFromPrompt()
	{
		System.out.println("Enter in if alive: true or false? ");
		boolean tempBool = false;
		try{
		//	tempBool = Boolean.getBoolean(scan.next());
			if(scan.next().equalsIgnoreCase("true"))
				tempBool = true;
		}
		catch(IllegalArgumentException iae)
		{
			System.err.println("Illegal Argument!");
		}
		System.out.println("alive is " + tempBool);
		return tempBool;
	}
	
	private static Gender getGenderFromPrompt()
	{
		System.out.println("Enter in m or f");
		String temp = scan.next();	
		if(temp.equalsIgnoreCase("m"))
			return Gender.MALE;
		else if(temp.equalsIgnoreCase("f"))
			return Gender.FEMALE;
		else 
			return Gender.OTHER;
	}
	
	private static Date getDateFromPrompt()
	{
		Date tempDate = null;

		System.out.println("Enter in the date 'mm/dd/yyyy'");
		try{
			tempDate = new SimpleDateFormat("MM/dd/yyyy").parse(scan.next());
				
		}
		catch(ParseException e)
		{
			System.err.println(tempDate + "is not parsable to a date");
		}
		return tempDate;
	}
	
	
	private static void showPersonList(Tree treeToShow)
	{
		System.out.println("List of people in "+ treeToShow.getName() + " are: ");
		int i = 0; 
		for (Person p : treeToShow.getPersonList()) {
			System.out.println(i + " "+ p.getName());
			i++;
		}
	}

	private static Person pickAPerson(Tree treeToPickFrom)
	{
		showPersonList(treeToPickFrom);
		System.out.println("enter your choice");
		int choice = -1;
		Person temp = null;
		try{
			choice = scan.nextInt();
			temp=treeToPickFrom.getPersonList().get(choice);
		}
		catch(InputMismatchException e )
		{
			System.out.println("Invalid input");
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("Invalid input, not on the list");
		}
		return temp;
		
	}
	
	private static void navigateTree()
	{}
	
	private static void removePerson()
	{}
	
	
	private static void test(){
		Person p = new Person("Jack");
		p.printData();
		Person mom = new Person("Mom");
		//p.setMom(mom);
		Relationship.isMother(p, mom);
				
		try {
			TreeReader tr = new TreeReader("testfile.txt");
			TreeWriter.writeTree("testing-hello-batman", "testfile.txt");
			TreeWriter.appendToTree("-checking-bye-superman", "testfile.txt");
			
			ArrayList<String> test = tr.readPerson();
			
			for (String string : test) {
				System.out.println(string);
			}
			
		} catch (IOException e) {
			System.out.println("io  problem");
		}
///////////////////////////////////////////////////////////////////////////////
	
			Person stepmom = new Person("new mom");
			Person oldMan = new Person("Old man",Gender.MALE);
//			p.setMom(mom);
		//	System.out.println(Relationship.isMother(p, mom));
			//System.out.println(Relationships.BROTHER);
			p.addRelation(mom, BasicRelation.MOTHER);
			p.addRelation(stepmom, BasicRelation.MOTHER);
			mom.addRelation(oldMan, BasicRelation.MOTHER);
//			System.out.println(p.getRelationPerson().get(0));
//			System.out.println(mom);
//			System.out.println(Relationship.isMother(p, mom));
//			
//			ArrayList<Person> temp = p.getMothers();
//			for (Person person : temp) {
//				person.printData();
//			}
			
			System.out.println(Relationship.isMomGrandFather(p, oldMan));
/////////////////////////////////////////////////////////////////////////////////
			/*
			 * 
			 * 		try {
			Person p1 = new Person("Jack");
			Person p2 = new Person("Jill");
			Person p3 = new Person("Perr");
			System.out.println("Writing to file");
			TreeWriter.writePersonToFile(p1, "testSerial");
			TreeWriter.writePersonToFile(p2, "testSerial");
			TreeWriter.writePersonToFile(p3, "testSerial");
			System.out.println("getting from file");
			TreeReader.readTreeFromFile("testSerial");
		} catch (IOException e) {
			e.printStackTrace();
		}
			 */
	}

}
