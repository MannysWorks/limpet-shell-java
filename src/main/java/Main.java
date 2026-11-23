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

         if(commandInput.startsWith("type ") && builtInCommands.contains(commandInput.substring(5))) {
            System.out.println(commandInput.substring(5) + " is a shell builtin"); 
            break; 
        } else {
            System.out.println(commandInput.substring(5) + ": not found");
            break; 
        }

         if(commandInput.equals("exit")) {
            break;  
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
