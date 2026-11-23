import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        while(true) {
         System.out.print("$ ");

         Scanner inputScanner = new Scanner(System.in); // Create a scanner obj to read inputs from user
         String commandInput = inputScanner.nextLine(); // Collect user input 

         if(commandInput.equals("exit")) break;  
         if(commandInput[0].equals("echo")) System.out.println(commandInput.substring(5)); 

         System.out.println(commandInput + ": command not found");
        
        } 
    }
}
