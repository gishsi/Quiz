package without_module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Application {
    private Scanner scan;
    private List<Module> modules;
    private Module currentModule;
    private String filename;
    private RuntimeTypeAdapterFactory<Question> runtimeTypeAdapterFactory;

    /**
     * Constructor for the Application class
     */
    public Application() {
        scan = new Scanner(System.in);
        modules = new ArrayList<>();
        currentModule = new Module();
        runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Question.class, "type")
                .registerSubtype(SingleChoice.class, "SingleChoice")
                .registerSubtype(FillTheBlanks.class, "FillTheBlanks");
    }

    /////////////////////////////////////// MAIN ///////////////////////////////////////////
    public static void main(String[] args) {
        Application app = new Application();
        boolean correct = false;
        while (!correct) {
            try {
                app.initialise();
                correct = true;
            } catch (FileNotFoundException e) {
                System.out.println("The file: " + app.filename + " does not exist.");
            }
        }
        app.runMenu();
        System.out.println("\tEnd of the program");
    }
    /////////////////////////////////////// MENUS ///////////////////////////////////////////

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
            option = "2";//scan.nextLine().toUpperCase();
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
                    save(filename);
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
                    takeQuiz();
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

    /**
     * The main menu that appears at the start of the program
     */
    public void runMenu() {
        String log = "";
        // MAIN MENU
        do {
            System.out.println("Log in as: \n\tT - Teacher\n\tS - Student\n\tQ - Quit");
            log = "S";//scan.nextLine().toUpperCase();
            switch (log) {
                case "T":
                    runMenuTeacher();
                    break;
                case "S":
                    runMenuStudent();
                    break;
                case "Q":
                    break;
                default:
                    System.err.println("That account does not exist.");
                    break;
            }
        } while (!(log.equals("Q")));
    }

    ////////////////////////////////LOAD AND SAVE/////////////////////////////////////////////////////

    /**
     * This function is used to read data
     */
    public void load(String filename) throws FileNotFoundException {
        // gson instance
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        // br instance - reading files
        BufferedReader br = null;
        try {
            // get the file
            br = new BufferedReader(new FileReader(filename));
            // with a list of objects such as  modules we need to get the type
            Type foundListType = new TypeToken<ArrayList<Module>>() {
            }.getType();
            // convert what you have read from the file to obj
            modules = gson.fromJson(br, foundListType);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Saving the modules to the database
     */
    public void save(String filename) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        Type foundListType = new TypeToken<List<Module>>() {
        }.getType();
        String jsonString = gson.toJson(modules, foundListType);
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename);
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initialise() throws FileNotFoundException {
        System.out.println("Enter the filename (filename.json): ");
        filename = "db.json";//scan.nextLine().toLowerCase(); //"db.json";//
        System.out.println("Filename: " + filename);
        load(filename);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Allows us to pick the module and then also change it
     */
    public void pickModule() {
        printModules();
        boolean isModuleCorrect = false;

        while (!isModuleCorrect) {
            String moduleID = scan.nextLine().toUpperCase();//"CS12320";//scan.nextLine().toUpperCase();
            if (!moduleID.equals("")) {
                if (getModule(moduleID) != null) {
                    currentModule = getModule(moduleID);
                    System.out.println("Using the " + moduleID + " module.");
                    isModuleCorrect = true;
                } else {
                    System.out.println("The module with the ID: " + moduleID + " does not exist in the database.");
                    System.out.println("Choose module again: ");
                    printModules();
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
        if(which!=null) {
            currentModule.listQuestions(which);
            currentModule.removeQuestion(which);
        } else {
            System.err.println("This bank does not exist.");
        }
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
     * Adding a question function
     */
    public void addQuestion() {
        // cannot add a question to module that has no banks
        if (currentModule.getBanks().size() == 0) {
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

    /**
     * Allows the user to take a quiz
     */
    public void takeQuiz() {
        Scanner scan = new Scanner(System.in);
        currentModule.listBanks();
        System.out.println("Pick a quiz");
        // which - the bank that the student picked
        Bank which = currentModule.searchForBank("Bank1"); //scan.nextLine()
        // needs check if the picked bank is alright
        if (which != null) {
            // the questions to be answered
            List<Question> questions = null;
            int Q = 0;
            // asking about the language
            System.out.println("Enter the preferred language (eg. english, polish): ");
            String language = "english";//scan.nextLine().toLowerCase(); //"english"//
            if (!language.equals("")) {
                // get the english array from bank
                // get the polish array from bank
                switch (language) {
                    case "english" -> questions = which.getQuestionsEng();
                    case "polish" -> questions = which.getQuestionsPL();
                    default -> System.out.println("Incorrect language choice.");
                }
            } else {
                System.out.println("Failed to add the new question to the bank.");
            }
            if (questions != null) {
                int tempQ = 0;
                System.out.println("Number of questions to display: ");
                try {
                    tempQ = 2;//Integer.parseInt(scan.nextLine()); // 2
                } catch (InputMismatchException | NumberFormatException e) {
                    System.err.println("Invalid input for questions number. Number of questions set to default (quiz size)");
                }
                if (tempQ > 0 && tempQ <= questions.size()) {
                    Q = tempQ;
                } else {
                    System.out.println("The number: " + tempQ + " is bigger that the number of questions in the quiz. Number of questions set to default (quiz size).");
                    Q = questions.size();
                }
                System.out.println("For SingleChoice questions: enter the whole answer.");
                System.out.println("For FillInBlanks questions: enter the whole answer in one line, separated by spaces.");
                // run the quiz
                answeringTheQuestion(questions, Q);
            } else {
                System.out.println("Failed to take the quiz.");
            }
        } else {
            System.err.println("Quiz not found.");
        }
    }

    /**
     * Answering a question - full process: random question, choices: 1: answer the question, 2: next question,
     * 3: previous question, 4: quit the quiz
     *
     * @param Q         - questions to be displayed
     * @param questions - list of quesions
     */
    private void answeringTheQuestion(List<Question> questions, int Q) {
        long start = System.nanoTime();
        int score = 0;
        String option = "";
        int answeredQuestions = 0;
        final int SECONDS = 60;
        List<Integer> order = createRandomOrder(Q);
        int questionNumber = 0;
        do {
            System.out.println("===============================================================");
            // display the question
            System.out.println((order.get(questionNumber) + 1) + ". " + questions.get(order.get(questionNumber)).display());
            System.out.println("Has this question been answered?: " + questions.get(order.get(questionNumber)).isAnswered());
            printMenuQuiz();
            option = scan.nextLine().toUpperCase();
            switch (option) {
                case "1":
                    //answer
                    Question current = questions.get(order.get(questionNumber));
                    boolean correct = answer(current);
                    if (current.isAnswered() == false) {
                        answeredQuestions++;
                    }
                    if (correct) {
                        score++;
                    }
                    if ((questionNumber + 1) > Q - 1) {
                        System.out.println("That is the last question");
                    } else {
                        questionNumber++;
                    }
                    current.setAnswered(true);
                    break;
                case "2":
                    // next question
                    if ((questionNumber + 1) > Q - 1) {
                        System.out.println("That is the last question");
                    } else {
                        questionNumber++;
                    }
                    break;
                case "3":
                    // previous question
                    if (questionNumber == 0) {
                        System.out.println("That is the first question");
                    } else {
                        questionNumber--;
                    }
                    break;
                case "Q":
                    // quit
                    break;
                default:
                    System.out.println("Wrong choice");
                    break;
            } //
        } while (!(option.equals("Q")) && answeredQuestions < Q);
        System.out.println("=================================================");
        System.out.println("End of the quiz.");
        long end = System.nanoTime();
        long elapsedTime = end - start;
        // SCORE
        System.out.println("Time: " + (TimeUnit.HOURS.convert(elapsedTime, TimeUnit.NANOSECONDS) % SECONDS) + ":" + (TimeUnit.MINUTES.convert(elapsedTime, TimeUnit.NANOSECONDS) % SECONDS) + ":" + (TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS)) % SECONDS);
        System.out.println("Questions answered: " + score);
        System.out.println("Questions not answered: " + (Q - score));
    }

    /**
     * @param q - the question to answer
     * @return true if the answer was correct, false otherwise
     */
    private boolean answer(Question q) {
        System.out.println("Your answer: ");
        String answer = null;
        answer = scan.nextLine();
        if (answer != null) {
            if (answer.equals(q.getAnswer())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Print the menu for the quiz
     */
    private void printMenuQuiz() {
        System.out.println("===============================================================");
        System.out.println("Your options: ");
        System.out.println("1. Answer ");
        System.out.println("2. Go to the next question ");
        System.out.println("3. Go to the previous question");
        System.out.println("Q. Quit the quiz");
    }

    /**
     * This function provides a way of displaying questions in a random order
     */
    private List<Integer> createRandomOrder(int Q) {
        // the list containing random numbers
        List<Integer> randomList = new ArrayList<>(Q);
        // first we give the list values in a sorted way
        for (int i = 0; i < Q; i++) {
            randomList.add(i);
        }
        Collections.shuffle(randomList);
        return randomList;
    }
}
