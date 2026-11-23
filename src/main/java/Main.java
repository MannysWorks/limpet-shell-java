import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        while(true) {
         System.out.print("$ ");

         Scanner inputScanner = new Scanner(System.in); // Create a scanner obj to read inputs from user
         String commandInput = inputScanner.nextLine(); // Collect user input 

         if(commandInput.equals("exit")) break;  
        
         
         String commandInputArr[] = commandInput.split(" "); // Split the input into an array of substrings
         if(commandInputArr[0].equals("echo")) System.out.println(commandInput.substring(5)); // Echo command prints the input given 
         
         else
         System.out.println(commandInput + ": command not found");
        
        } 
    }
}
