package with_module;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    List<Module> modules;
    Scanner scan;
    // make a new module
    Module mod1 = new Module("CS11110");
    Module mod2 = new Module("CS11223");
    Module mod3 = new Module("CS114440");

    public Application() {
        modules = new ArrayList<>();
        scan = new Scanner(System.in);
        // add that to the array list
        modules.add(mod1);
        modules.add(mod2);
        modules.add(mod3);
    }

    public static void main(String[] args) {
        Application app = new Application();
       // while(true) {
            //app.createNewQuestion();
        //}
    }

    public void run() {
        System.out.println(modules);
    }


    /**
     * Creating banks
     */
    public void createBank() {
        Bank newBank = new Bank();
        // pick a module
        System.out.println("Which module do you want it to belong to?");
        // print the modules, let user choose
        printModules();
        int mod = Integer.parseInt(scan.nextLine());
        switch (mod) {
            case 1 -> addingBank(mod1, newBank);
            case 2 -> addingBank(mod2, newBank);
            case 3 -> addingBank(mod3, newBank);
            default -> System.out.println("Wrong choice");
        }
    }

    /**
     * A helper function that allows me to avoid code duplication
     * @param mod - the with_module.Module which we want to add a bank to
     * @param bank - the new bank that we are adding
     */
    private void addingBank(Module mod, Bank bank) {
        mod.addBank(bank);
        // pick a bank identifier
        System.out.println("What is the id of that bank?");
        String bankID = scan.nextLine();
        // make the id of the bank be in form module-id:bank-id
        bankID = mod.getId() + ":" + bankID;
        bank.setId(bankID);
    }

    /**
     * A helper function that prints all the modules
     */
    private void printModules() {
        int i = 1;
        for (Module el : modules) {
            System.out.println(i + ". " + el.getId());
            i++;
        }
    }
    /**
     * A helper function that prints all the banks
     */
    private void printBanks() {
        int i = 1;
        for (Module el : modules) {
            System.out.println(i + ". " + el.getBankId());
            i++;
        }
    }


    public void createNewQuestion(Question testQuestion) {
        // for now i am going to add a bank here
        Bank testBank = new Bank();
        testBank.setId("CS12320:BankTest");
        mod1.addBank(testBank);


        // 1. pick a bank
        printBanks();
        System.out.println("Pick a bank you want to add questions to (with_module.Module.id:with_module.Bank.id): ");
        String bankID = "CS12320:BankTest";//scan.nextLine();

        Bank targetBank = new Bank(bankID);
        Bank currentBank = new Bank();

        for (Module el : modules) {
            if(el.pickBank(targetBank)!=null){
                currentBank = el.pickBank(targetBank);
                break;
            } else if(el.pickBank(targetBank) == null){
                System.out.println("No banks matched");
            }
        }

        addEngQuestion(currentBank, testQuestion);
        addPlQuestion(currentBank, testQuestion);
        System.out.println(currentBank);

    }


    private void addQuestion() {
        Question question = new Question();
        System.out.println("choose the Type:");
        System.out.println("1. Single choice");
        System.out.println("2. Fill the blanks.");
        int type = Integer.parseInt(scan.nextLine());
        switch(type){
            case 1 -> {
               question = new SingleChoice();
            }
            default -> System.out.println("Wrong choice.");
        }
    }
    /**
     * Helper function
     * @param bank
     * @param question
     */
    private void addEngQuestion(Bank bank, Question question) {
        System.out.println("Enter the question in english: ");
        question.setContent("Do you like ice-cream?");
        bank.addEngBank(question);
    }

    /**
     * Helper function
     * @param bank
     * @param question
     */
    private void addPlQuestion(Bank bank, Question question) {
        System.out.println("Enter the question in polish: ");
        question.setContent("Lubisz lody?");
        bank.addPlBank(question);
    }
}
