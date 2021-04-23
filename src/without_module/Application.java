package without_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    private Scanner scan;
    private List<Module> modules;
    private Module currentModule;

    /**
     * Constructor for the Application class
     */
    public Application() {
        scan = new Scanner(System.in);
        modules = new ArrayList<>();
        currentModule = new Module();
    }

    /////////////////////////////////////// MAIN ///////////////////////////////////////////
    public static void main(String[] args) {
        Application app = new Application();
        app.runTest();
    }
    /////////////////////////////////////// MENU ///////////////////////////////////////////

    /**
     * Print the menu for the teacher
     */
    public void printMenuTeacher() {
        System.out.println("==========================================");
        System.out.println("These are your options: ");
        System.out.println("1. Create a new question bank.");
        System.out.println("2. Add a new question to an existing bank.");
        System.out.println("3. Delete a bank (must be empty).");
        System.out.println("4. List all the question banks for a specific module.");
        System.out.println("5. Remove a question from a bank.");
        System.out.println("6. Pick another module to work on.");
        System.out.println("q - Quit");
    }

    /**
     * Run the menu for the teacher
     */
    public void runMenuTeacher() {
        String option = "";
        System.out.println("Welcome to the Quiz application (Teacher)");
        System.out.println("Which module would you like to associate your new bank to");
        pickModule();
        do {
            printMenuTeacher();
            System.out.println("Your choice: ");
            option = scan.nextLine().toUpperCase();
            switch (option) {
                case "1":
                    currentModule.addBank();
                    break;
                case "2":
                    addQuestion();
                    break;
                case "3":
                    currentModule.deleteBank();
                    break;
                case "4":
                    currentModule.listBanks();
                    break;
                case "5":
                    removeQuestion();
                    break;
                case "6":
                    pickModule();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        } while (!(option.equals("Q")));
    }

    /**
     * Print the menu for the student
     */
    public void printMenuStudent() {
        System.out.println("==========================================");
        System.out.println("These are your options: ");
        System.out.println("1. List banks.");
        System.out.println("2. Pick a quiz.");
        System.out.println("3. Change the module.");
        System.out.println("q - Quit");
    }

    /**
     * Run the menu for the student
     */
    public void runMenuStudent() {
        String option = "";
        System.out.println("Welcome to the Quiz application (Student)");
        System.out.println("Pick the module:");
        pickModule();
        // pick the language
//        System.out.println("Pick the language you want to take the quiz in:");
//        String lang = scan.nextLine();
//        currentModule.pickTheLanguage(lang);

        do {
            printMenuStudent();
            option = scan.nextLine().toUpperCase();
            switch (option) {
                case "1":
                    // list question banks
                    currentModule.listBanks();
                    break;
                case "2":
                    // pick the quiz
                    currentModule.takeQuiz();
                    break;
                case "3":
                    // change the module
                    pickModule();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        } while (!(option.equals("Q")));
    }


    ////////////////////////////////LOAD/////////////////////////////////////////////////////
    private void load() {
        Module cs123 = new Module("CS123");
        Module cs107 = new Module("CS107");
        cs123.load();
        modules.add(cs107);
        modules.add(cs123);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Method that runs tests in our application so that main could be as short as possible
     */
    public void runTest() {
        load();
        System.out.println("Log in as: \n\tT - Teacher\n\tS - Student");
        String log = scan.nextLine().toUpperCase();
        switch (log) {
            case "T" -> runMenuTeacher();
            case "S" -> runMenuStudent();
            // could add a case for quitting and change the q - quit to log out so that we can go back and forth S - T
            default -> System.out.println("That account does not exist.");
        }
    }

    /**
     *  Allows us to pick the module and then also change it
     */
    public void pickModule() {
        printModules();
        boolean isModuleCorrect = false;

        while(!isModuleCorrect) {
            String moduleID = scan.nextLine().toUpperCase();
            if(!moduleID.equals("")) {
                if(getModule(moduleID)!=null) {
                    currentModule = getModule(moduleID);
                    isModuleCorrect = true;
                } else {
                    printModules();
                    System.out.println("Choose module again: ");
                }
            }
        }
    }

    /**
     * Remove a question
     */
    public void removeQuestion() {
        // list the banks
        currentModule.listBanks();
        System.out.println("Pick a bank");
        Bank which = currentModule.searchForBank(scan.nextLine());
        //list the questions for that bank
        currentModule.listQuestions(which);
        // remove the question
        currentModule.removeQuestion(which);
    }

    /*
     * Helper function for printing modules
     */
    private void printModules() {
        StringBuilder sb = new StringBuilder();
        for (Module m : modules) {
            sb.append(m.getId()).append("\n");
        }
        System.out.println(sb.toString());
    }

    /**
     *  Adding a question function
     */
    public void addQuestion() {
        // cannot add a question to module that has no banks
        if(currentModule.getBanks().size() == 0) {
            System.err.println("Cannot add a question if a module has no banks.");
        } else {
            currentModule.addQuestion();
        }
    }

    /**
     * Helper method for getting the module
     *
     * @return the module we want to work on
     */
    private Module getModule(String moduleID) {
        Module which = null;
        Module other = new Module(moduleID);

        if (!moduleID.equals("")) {
            for (Module m : modules) {
                if (m.equals(other)) {
                    which = m;
                }
            }
        }
        return which;
    }
}
