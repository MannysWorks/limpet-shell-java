import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        while (true) {
            System.out.print("$ "); // Prompt that indicates ready for input

            Scanner inputScanner = new Scanner(System.in); // Create a scanner obj to read inputs from user
            String commandInput = inputScanner.nextLine(); // Collect user input
            Set<String> builtInCommands = new HashSet<>(Set.of("echo", "exit", "type"));
            Boolean fileExistsAndIsExecutable = !isCommandAnExecutable(commandInput.substring(5)).isEmpty(); // If the
                                                                                                             // command
                                                                                                             // is an
                                                                                                             // executable
                                                                                                             // then the
                                                                                                             // file
                                                                                                             // exists
                                                                                                             // and is
                                                                                                             // an
                                                                                                             // executable
            Boolean commandCanBeExecuted = !isCommandAnExecutable(commandInput.substring(0)).isEmpty();

            String fullPath = isCommandAnExecutable(commandInput.substring(5)); // Get the path of the executable file
                                                                                // if it exists

            if (commandInput.equals("exit")) {

                break;

            } else if (commandInput.startsWith("type ")) {

                if (builtInCommands.contains(commandInput.substring(5))) { // If it is a built in command

                    System.out.println(commandInput.substring(5) + " is a shell builtin");

                } else { // If it is not a built in command

                    if (fileExistsAndIsExecutable)
                        System.out.println(commandInput.substring(5) + " is " + fullPath);
                    else {
                        System.out.println(commandInput.substring(5) + ": not found");
                    }

                }
            } else if (commandInput.startsWith("echo ")) {
                System.out.println(commandInput.substring(5)); // Echo command prints the input given
            } else if (commandCanBeExecuted) {
                List<String> commands = new ArrayList<String>();
                while (inputScanner.hasNext()) {
                    commands.add(inputScanner.next()); // Get all commands and load into a list
                }

                // Creating the process with the given commands
                ProcessBuilder pBuilder = new ProcessBuilder(commands);

                // Starting the process
                Process process = pBuilder.start();
            } else {
                System.out.println(commandInput + ": command not found");
            }

        }
    }

    // If the command is executable then it exists so return the full Path or an
    // empty string if it does not
    public static String isCommandAnExecutable(String command) {
        String PATH = System.getenv("PATH");
        String[] directories = PATH.split(File.pathSeparator);
        String fullPath = "";

        for (String directoryPath : directories) {
            Path directory = Paths.get(directoryPath); // Create a Path object for that directory
            Path filePath = directory.resolve(command); // Append the command to the path
                                                        // and return a Path obj

            if (Files.exists(filePath) && Files.isExecutable(filePath)) {
                fullPath = filePath.toString();
                break;
            } else if (Files.exists(filePath) && !Files.isExecutable(filePath)) {
                continue;
            }

        }
        return fullPath;
    }

}