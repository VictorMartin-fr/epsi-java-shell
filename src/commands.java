import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class commands {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Path rootDir = Paths.get("").toAbsolutePath();
		
		while(true) {
			System.out.print(rootDir.normalize()+" > ");
			Scanner scanner = new Scanner( System.in );
			String input = scanner.nextLine();
			String[] elements = input.split(" ");
			String command = elements[0];
			String txtRootDir = rootDir.toString();
			
			switch(command) {
			case "ls":
				String relativePath = "";
				if (elements.length == 1) {
					relativePath = ".";
				} else {
					relativePath = elements[1];
				}
				Path path = Paths.get(txtRootDir,relativePath).toAbsolutePath();
				System.out.println(path.toString());
				ls(path);
				break;
			case "cd":
				if (elements.length == 1) {
					System.out.println("invalid command. Please use cd [path]");
					break;
				} 
				String newPath = elements[1];
				rootDir = Paths.get(txtRootDir,newPath).toAbsolutePath();
				break;
			case "cat":
				String file = "";
				if (elements.length == 1) {
					System.out.println("No file after the command. Please type : cat [filename]");
					break;
				} else {
					file = elements[1];
				}
				Path filename = Paths.get(txtRootDir,file).toAbsolutePath();
				cat(filename);
				break;
			case "mkdir":
				String directory = "";
				if (elements.length == 1) {
					System.out.println("invalid command. Please use mkdir [directory name] or mkdir [path/directory name]");
					break;
				} else {
					directory = elements[1];
				}
				Path directoryName = Paths.get(txtRootDir,directory).toAbsolutePath();
				mkdir(directoryName);
				break;
			case "touch":
				String createFile = "";
				if (elements.length == 1) {
					System.out.println("invalid command. Please use touch [name] or touch [path/filename]");
					break;
				} else {
					createFile = elements[1];
				}
				Path fileName = Paths.get(txtRootDir,createFile).toAbsolutePath();
				touch(fileName);
				break;
			case "cp":
				String OriginalFile = "";
				String DestionationFile = "";
				if (elements.length == 1) {
					System.out.println("invalid command. Please use cp [input] [dest]");
					break;
				} else if (elements.length == 2) {
					System.out.println("invalid command. Please use cp [original path] [destination path]");
				} else {
					OriginalFile = elements[1];
					DestionationFile = elements[2];
				}
				
				Path originalPath = Paths.get(txtRootDir,OriginalFile).toAbsolutePath();
				Path destinationPath = Paths.get(txtRootDir, DestionationFile).toAbsolutePath();
				cp(originalPath,destinationPath);
				break;
			case "mv":
				String OriginalMove = "";
				String DestionationMove = "";
				if (elements.length == 1) {
					System.out.println("invalid command. Please use mv [orignal path] [destination path]");
					break;
				} else if (elements.length == 2) {
					System.out.println("invalid command. Please use cp [original path] [destination path]");
				} else {
					OriginalMove = elements[1];
					DestionationMove = elements[2];
				}
				
				Path originalPathMove = Paths.get(txtRootDir,OriginalMove).toAbsolutePath();
				Path destinationPathMove = Paths.get(txtRootDir, DestionationMove).toAbsolutePath();
				mv(originalPathMove,destinationPathMove);
				break;
			case "exit":
				System.exit(0);
				break;
			case "help":
				System.out.println("--- Command Availables ---\n ls [path] => see files and directory who are in the path\n cd [path] => move to the path\n cat [file] => print the content form a file\n exit => exit the shell");
				break;
			default:
				System.out.println("Invalid command, please type 'help' to see commands availables");
			}
		}
	}
	
	public static void ls(Path path) throws IOException {
		DirectoryStream<Path> stream = Files.newDirectoryStream(path);
		try {
			Iterator<Path> iterator = stream.iterator();
			while(iterator.hasNext()) {
				Path current = iterator.next();
				
				BasicFileAttributes attributFiles = Files.readAttributes(current, BasicFileAttributes.class);
				String directory = "";
				if (attributFiles.isDirectory()) {
					directory = "d";
				} else {
					directory = "-";
				}
				
				String currentFileDate = new SimpleDateFormat("MMM dd HH:mm").format(attributFiles.lastModifiedTime().toMillis()); 
				System.out.printf("%s %8d %s %s \n",directory,attributFiles.size(),currentFileDate,current.getFileName());
			}
			
		} finally {
			stream.close();
		}
	}
	
	public static void cat(Path file) throws IOException {
		List<String> loadFile = Files.readAllLines(file);
		for (int i = 0; i < loadFile.size(); i++) {
			System.out.println(loadFile.get(i));
		}
	}
	
	public static void mkdir(Path directory) {
		try {
				Path newDirectory = Files.createDirectories(directory);
			} catch(IOException e) {
			e.printStackTrace();
			}
		}
	
	public static void touch(Path file) throws IOException {
		Path newFile = Files.createFile(file);
	}
	
	public static void cp(Path input, Path output) throws IOException {
		Path copyFile = Files.copy(input, output);
	}
	
	public static void mv(Path input, Path output) throws IOException {
		Path moveFile = Files.move(input, output);
	}
}
