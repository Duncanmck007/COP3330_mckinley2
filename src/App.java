import java.util.Scanner;


public class App {
    public static String filename = "";
    public static void main(String args[]) {

        TaskList TaskList = new TaskList();
        String menuChoice = "0";

        try {

            Scanner input = new Scanner(System.in);
            firstMenu();
            String firstMenuChoice= "0";
            firstMenuChoice = input.nextLine();
            switch(firstMenuChoice)
            {
                case "1":
                    System.out.println("What would you like your new File to be called? (.obj Files Only)");
                    String newFileName = input.nextLine();
                    TaskList.NewFileCreate(newFileName);
                    filename = newFileName;

                    break;
                case "2":
                    System.out.println("Please enter the name of the file (.obj Files Only)");
                    String previousFileName = input.nextLine();
                    TaskList.readFromFile(previousFileName);
                    filename = previousFileName;
            }


            showMessage("Welcome to ToDoList", false);

            while (!menuChoice.equals("4")) {
                mainMenu(TaskList.notCompletedCount(), TaskList.completedCount());
                menuChoice = input.nextLine();

                switch (menuChoice) {
                    case "1":
                        TasksMenu();
                        TaskList.listAllTasks(input.nextLine());
                        break;
                    case "2":
                        TaskList.readTaskFromUser();
                        break;
                    case "3":
                        TaskList.listAllTasksWithIndex();
                        editTaskSelection();
                        TaskList.editTask(input.nextLine());
                        break;
                    case "4":
                        break;

                    default:
                        unknownMessage();
                }
            }

            TaskList.saveToFile(filename);
            bye();

        } catch (Exception e) {
            showMessage("Uncaught Exception", true);
            System.out.println("Trying to write the unsaved data of all tasks in data file");
            TaskList.saveToFile(filename);
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    public static void firstMenu(){
        System.out.println("Please use 1/2 to select an option:");
        System.out.println("(1) Create a new list");
        System.out.println("(2) Load an existing list\n");

    }

    public static void mainMenu(int incompleteTaskCount, int completedTaskCount) {
        System.out.println("\nMain Menu");
        System.out.println("You have " + incompleteTaskCount + " tasks to be completed and " + completedTaskCount + " completed tasks\n");
        System.out.println("Please select an option:");
        System.out.println("(1) Show Task List");
        System.out.println("(2) Add New Task");
        System.out.println("(3) Edit Task (update, mark as done, remove)");
        System.out.println("(4) Save and Quit\n");
        System.out.print("Please enter your choice [1-4]: ");
    }

    public static void TasksMenu() {
        System.out.println("\nDisplay All Tasks");
        System.out.println("Pick an option:");
        System.out.println("(1) Show Task List by date");
        System.out.println("(2) Show Task List by project");
        System.out.print("\nPlease enter your choice [1-2]: ");
    }


    public static void editTaskSelection() {
        System.out.print("Task number - Enter: ");
    }

    public static void editTask() {
        System.out.println("\nTask Edit Options");
        System.out.println("Pick an option:");
        System.out.println("(1) Modify selected task");
        System.out.println("(2) Mark selected task as Complete");
        System.out.println("(3) Mark selected task as Incomplete");
        System.out.println("(4) Delete selected task");
        System.out.println("(5) Return to main menu");
        System.out.print("\nPlease enter your choice [1-5]: ");
    }

    public static void bye() {
        System.out.println("All tasks are saved to data file");
        System.out.println("Thank you for using my Task Manager");

    }

    public static void unknownMessage() {

        System.out.println("Please make a valid choice. ");

    }

    public static void showMessage(String message, boolean warning) {

        System.out.println(message);

    }
}
