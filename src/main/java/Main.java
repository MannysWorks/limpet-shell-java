import java.io.File;

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
            else { // If it is not a built in command
                String PATH = System.getenv("PATH"); 
                String[] directories = PATH.split(File.pathSeparator); 
                Boolean fileExistsAndIsExecutable = false; 
                String fullPath = ""; 

                for(String directoryPath : directories) {
                    Path directory = Paths.get(directoryPath); // Create a Path object for that directory
                    Path filePath = directory.resolve(commandInput.substring(5)); // Append the command to the path and return a Path obj
                    
                    if(Files.exists(filePath) && Files.isExecutable(filePath)){
                        fileExistsAndIsExecutable = true; 
                        fullPath = filePath.toString(); 
                        break; 
                    } 
                    else if(Files.exists(filePath) && !Files.isExecutable(filePath)) continue; 
                }

                if(fileExistsAndIsExecutable) System.out.println(commandInput.substring(5) + " is " + fullPath);
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
