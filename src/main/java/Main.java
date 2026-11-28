import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
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
            String[] words = commandInput.trim().split("\\s+"); // Split command string into an array of words

            Set<String> builtInCommands = new HashSet<>(Set.of("echo", "exit", "type", "pwd"));

            List<String> commands = new ArrayList<String>();
            for (String word : words) {
                commands.add(word); // Get all commands and load into a list
            }

            Boolean commandCanBeExecuted = !isCommandAnExecutable(commands.get(0)).isEmpty();

            if (commandInput.equals("exit")) {
                break;
            } else if (commandInput.startsWith("type ")) {

                if (builtInCommands.contains(commandInput.substring(5))) { // If it is a built in command

                    System.out.println(commandInput.substring(5) + " is a shell builtin");

                } else { // If it is not a built in command
                    String fullPath = isCommandAnExecutable(commandInput.substring(5)); // Get the path of the
                                                                                        // executable file if it exists

                    // If the command is an executable then the file exists and is an executable
                    Boolean fileExistsAndIsExecutable = !isCommandAnExecutable(commandInput.substring(5)).isEmpty();

                    if (fileExistsAndIsExecutable)
                        System.out.println(commandInput.substring(5) + " is " + fullPath);
                    else {
                        System.out.println(commandInput.substring(5) + ": not found");
                    }

                }
            } else if (commandInput.startsWith("echo ")) {
                System.out.println(commandInput.substring(5)); // Echo command prints the input given
            } else if (commandInput.startsWith("pwd")) {
                String userPresentWorkingDirectory = System.getProperty("user.dir");
                System.out.println(userPresentWorkingDirectory);
            } else if (commandCanBeExecuted) {
                // Creating the process with the given commands
                ProcessBuilder pBuilder = new ProcessBuilder(commands);

                // Starting the process
                Process process = pBuilder.start();

                // getInputStream gives an Input stream connected to
                // the process standard output (and vice versa). We use
                // that to construct a BufferedReader so we can readLine( ) it.
                BufferedReader is = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;

                while ((line = is.readLine()) != null)
                    System.out.println(line); // Print output from the running process after reading it

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
        String fullPathWithExt = "";

        for (String directoryPath : directories) {
            Path directory = Paths.get(directoryPath); // Create a Path object for that directory
            String pathExt = System.getenv("PATHEXT"); // Get the path extensions
            Path filePath = directory.resolve(command); // Append the command to the path and return a Path obj

            Path filePathWithExt = Path.of("");
            String[] extensions = {};

            if (pathExt != null) {
                // Split extensions into arrays of individuals strings
                extensions = pathExt.split(File.pathSeparator);

                for (String extension : extensions) {
                    filePathWithExt = Path.of(filePath + extension);
                    if (Files.exists(filePathWithExt) && Files.isExecutable(filePathWithExt)) {
                        fullPathWithExt = filePathWithExt.toString();
                        break;
                    }
                }
            }

            if (Files.exists(filePath) && Files.isExecutable(filePath)) {
                fullPath = filePath.toString();
                break;
            } else if (Files.exists(filePath) && !Files.isExecutable(filePath)) {
                continue;
            }

        }
        return (!fullPath.isEmpty()) ? fullPath : fullPathWithExt;
    }

}