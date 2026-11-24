import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {

        while(true) {
         System.out.print("$ "); // Prompt that indicates ready for input

         Scanner inputScanner = new Scanner(System.in); // Create a scanner obj to read inputs from user
         String commandInput = inputScanner.nextLine(); // Collect user input 
         Set<String> builtInCommands = new HashSet<>(Set.of("echo", "exit", "type"));

         if(commandInput.equals("exit")) {
            break;  
        } 
         else if(commandInput.startsWith("type ")) {

            if(builtInCommands.contains(commandInput.substring(5)))  { // If it is a built in command
                System.out.println(commandInput.substring(5) + " is a shell builtin");
            } 
            // TODO: the else statement after this will never run so figure that out  
            else if(!builtInCommands.contains(commandInput.substring(5))) { // If it is not a built in command
                String PATH = System.getenv("PATH"); 
                String[] directories = PATH.split(":"); 
                Boolean fileExistsAndIsExecutable = false; 
                String filePath; 
                
                outerLoop: 
                for(String directoryPath : directories) {
                    Path directory = Paths.get(directoryPath); // Create a Path object for that directory

                    DirectoryStream<Path> stream = Files.newDirectoryStream(directory); // DirectoryStream allows us to get all files present in a specific directory
                     
                    for(Path file : stream) {
                        if(file.getFileName().toString() == commandInput.substring(5) && Files.isExecutable(file)) { // If the file with the command exists and is executable
                            fileExistsAndIsExecutable = true; 
                            filePath = file.toString();
                            break outerLoop;  
                        }
                        else if ((file.getFileName() == commandInput.substring(5)) && !(Files.isExecutable(file))) continue; // If the file exists but lacks execute permissions, skip it
                    }
                }

                if(fileExistsAndIsExecutable) System.out.println(commandInput.substring(5) + " is " + filePath);
                else { 
                    System.out.println(commandInput.substring(5) + ": not found");
                }
            }
        } 
         else if(commandInput.startsWith("echo ")) {
            System.out.println(commandInput.substring(5)); // Echo command prints the input given 
        }
         else {
            System.out.println(commandInput + ": command not found");
        }
        
        } 
    }
}
