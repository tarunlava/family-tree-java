import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

//This class is to write the tree data to a text file

public class TreeWriter {

	private static FileWriter writer;
	private static File treeFile;
	
	static void writeTree(String str, String fileName) throws IOException
	{
		treeFile = new File(fileName+Tree.FILE_EXTENSION);
		writer = new FileWriter(treeFile);
		writer.write(str);
		writer.close();
	}
	
	static void appendToTree(String str, String fileName) throws IOException
	{
		writer = new FileWriter(treeFile, true) ;
		writer.append(str);
		writer.flush();
		writer.close();
	}
	
	//testing serialization
	static void writePersonToFile(Person p, String treeFile) throws IOException
	{
		try {
			FileOutputStream fileOut = new FileOutputStream(treeFile+Tree.FILE_EXTENSION, true);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(p);
			//fileOut.write('\n');
			objectOut.close();

			fileOut.close();
			System.out.println("Serialized data written to file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//write the tree to a file as serialized object
	static void writeTreeToFile(Tree treeToWrite, String fileName) 
	{
		try{
			FileOutputStream fileOut = new FileOutputStream(fileName+Tree.FILE_EXTENSION);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			try
			{
				objectOut.writeObject(treeToWrite);
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				objectOut.close();
				fileOut.close();
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}		
	}
}
