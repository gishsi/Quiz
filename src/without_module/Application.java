package without_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    private Scanner scan;
    private List<Module> modules;


    /**
     * Constructor for the Application class
     */
    public Application() {
        scan = new Scanner(System.in);
        modules = new ArrayList<>();
    }

    /////////////////////////////////////// MAIN ///////////////////////////////////////////
    public static void main(String[] args) {
        Application app = new Application();
        app.runTest();
        app.runMenu();
    }
    /////////////////////////////////////// MENU ///////////////////////////////////////////

    /**
     * Print the menu
     */
    public void printMenu() {
        System.out.println("1. Create a new question bank.");
        System.out.println("2. Add a new question to an existing bank.");
        System.out.println("3. Delete a bank (must be empty).");
        System.out.println("4. List all the question banks for a specific module.");
        System.out.println("5. Remove a question from a bank.");
        System.out.println("q - Quit");
    }

    /**
     * Run the menu
     */
    public void runMenu() {
        String option = "";
        Module module = null;
        boolean isModuleCorrect = false;
        System.out.println("Welcome to the Quiz application (Teacher)");
        printModules();
        System.out.println("Which module would you like to work on?");


        while(!isModuleCorrect) {
            String moduleID = scan.nextLine().toUpperCase();
            if(!moduleID.equals("")) {
                if(pickModule(moduleID)!=null) {
                    module = pickModule(moduleID);
                    isModuleCorrect = true;
                } else {
                    printModules();
                    System.out.println("Choose module again: ");
                }
            }
        }

        System.out.println("These are your options: ");
        do {
            printMenu();
            option = scan.nextLine().toUpperCase();
            switch (option) {
                case "1":
                    module.addBank();
                    break;
                case "2":
                    module.addQuestion();
                    break;
                case "3":
                    module.deleteBank();
                    break;
                case "4":
                    module.listBanks();
                    break;
                case "5":
                    removeQuestion(module);
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Wrong choice");
                    break;
            }
        } while (!(option.equals("Q")));
    }

    ////////////////////////////////////////////////////////////////////////////////////////
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
    }

    /**
     * Remove a question
     *
     * @param module - the module we are working on
     */
    public void removeQuestion(Module module) {
        // list the banks
        module.listBanks();
        System.out.println("Pick a bank");
        Bank which = module.searchForBank(scan.nextLine());
        //list the questions for that bank
        module.listQuestions(which);
        // remove the question
        module.removeQuestion(which);
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
     * Helper method for getting the banks
     *
     * @return the module we want to work on
     */
    private Module pickModule(String moduleID) {
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
