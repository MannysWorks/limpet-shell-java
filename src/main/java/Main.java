import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean process = true; // Variable to know if the shell process is running
        while(process) {
         System.out.print("$ ");

         Scanner inputScanner = new Scanner(System.in); // Create a scanner obj to read inputs from user
         String commandInput = inputScanner.nextLine(); // Collect user input 

         if(commandInput == "exit") {
            process = false; 
         else 
         System.out.println(commandInput + ": command not found");
        
        } 
    }
}
