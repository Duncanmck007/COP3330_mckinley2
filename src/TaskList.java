import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class TaskList {

    private ArrayList<TaskItem> taskList;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public boolean readTaskFromUser() {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Please enter the following details to add a task:");
            System.out.print("Task Title: ");
            String title = scan.nextLine();
            System.out.print("Description: ");
            String project = scan.nextLine();
            System.out.print("Due Date (example: 2020-11-20): ");
            LocalDate dueDate = LocalDate.parse(scan.nextLine());

            this.taskList.add(new TaskItem(title, project, dueDate));
            App.showMessage("Task is added successfully", false);

            return true;
        } catch (Exception e) {
            App.showMessage(e.getMessage(), true);
            return false;
        }

    }

    public boolean readTaskFromUserToUpdate(TaskItem task) {
        Scanner scan = new Scanner(System.in);
        boolean isTaskUpdated = false;

        try {
            System.out.println("Please enter the following details to update a task:\n");
            System.out.print("Task Title: ");
            String title = scan.nextLine();
            if (!(title.trim().equals("") || title == null)) {
                task.setTitle(title);
                isTaskUpdated = true;
            }

            System.out.print("Description: ");
            String project = scan.nextLine();
            if (!(project.trim().equals("") || project == null)) {
                task.setDescription(project);
                isTaskUpdated = true;
            }

            System.out.print("Due Date (example: 2020-11-20): ");
            String dueDate = scan.nextLine();
            if (!(dueDate.trim().equals("") || dueDate == null)) {
                task.setDueDate(LocalDate.parse(dueDate));
                isTaskUpdated = true;
            }

            App.showMessage("Task is " + (isTaskUpdated ? "updated successfully" : "NOT modified") + ": Returning to Main Menu", false);

            return true;
        } catch (Exception e) {
            App.showMessage(e.getMessage(), true);
            return false;
        }
    }

    public void listAllTasksWithIndex() {
        String displayFormat = "%-4s %-35s %-20s %-10s %-10s";

        if (taskList.size() > 0) {
            System.out.println(String.format(displayFormat, "Number", "Title", "Description", "Due Date", "Completed"));
            System.out.println(String.format(displayFormat, "------", "-----", "-----------", "--------", "---------"));
        } else {
            System.out.println("No tasks to show");
        }

        taskList.stream()
                .forEach(task -> System.out.println(String.format(displayFormat, taskList.indexOf(task) + 1, task.getTitle(), task.getDescription(), task.getDueDate(), (task.isComplete() ? "Yes" : "No"))));
    }

    public void listAllTasks(String sortBy) {

        System.out.println(
                "Total Tasks = " + taskList.size() +
                        "\t\t (Completed = " + completedCount() + "\t\t" + " Not Completed = " + notCompletedCount() + " )");

        String displayFormat = "%-10s %-10s %-10s %-10s %-10s";
        if (sortBy.equals("2")) {

            if (taskList.size() > 0) {
                System.out.println(String.format(displayFormat, "Number", "Title",  "Description", "Due Date", "Completed"));
                System.out.println(String.format(displayFormat, "------", "-----",  "-----------", "--------", "---------"));
            } else {
                System.out.println("No tasks to show");
            }

            taskList.stream()
                    .sorted(Comparator.comparing(TaskItem::getDescription))
                    .forEach(task -> System.out.println(String.format(displayFormat, taskList.indexOf(task), task.getTitle(),task.getDescription(), task.getDueDate(), (task.isComplete() ? "YES" : "NO"))));
        } else {

            if (taskList.size() > 0) {
                System.out.println(String.format(displayFormat, "Number", "Due Date", "Title", "Description", "Completed"));
                System.out.println(String.format(displayFormat, "------", "--------", "-----", "-----------", "---------"));
            } else {
                System.out.println("No tasks to show");
            }

            taskList.stream()
                    .sorted(Comparator.comparing(TaskItem::getDueDate))
                    .forEach(task -> System.out.println(String.format(displayFormat, taskList.indexOf(task), task.getDueDate(), task.getTitle(), task.getDescription(), (task.isComplete() ? "YES" : "NO"))));
        }
    }

    public void editTask(String selectedTask) throws NullPointerException {
        try {
            // checking if the task number is given and empty string or null
            if (selectedTask.trim().equals("") || selectedTask == null) {
                throw new NullPointerException("You didn't enter a task number");
            }

            int taskIndex = Integer.parseInt(selectedTask) - 1;
            if (taskIndex < 0 || taskIndex > taskList.size()) {
                throw new ArrayIndexOutOfBoundsException("Number is not a task.");
            }

            TaskItem task = taskList.get(taskIndex);

            App.showMessage("Task Number " + selectedTask + "  is selected:" + task.formattedStringOfTask(), false);

            App.editTask();
            Scanner scan = new Scanner(System.in);
            String editChoice = scan.nextLine();
            switch (editChoice) {
                case "1":
                    readTaskFromUserToUpdate(task);
                    break;
                case "2":
                    task.markCompleted();
                    App.showMessage("Task Number " + selectedTask + " is marked as complete.", false);
                    break;
                case "3":
                    task.markIncomplete();
                    App.showMessage("Task Number " + selectedTask + " is marked as Incomplete.", false);
                    break;
                case "4":
                    taskList.remove(task);
                    App.showMessage("Task Number " + selectedTask + " is deleted.", true);
                    break;
                default:
                    App.showMessage("Returning to Main Menu", true);
            }
        } catch (Exception e) {
            App.showMessage(e.getMessage(), true);
        }
    }

    public int completedCount() {
        return (int) taskList.stream()
                .filter(TaskItem::isComplete)
                .count();
    }

    public int notCompletedCount() {
        return (int) taskList.stream()
                .filter(task -> !task.isComplete())
                .count();
    }

    public boolean readFromFile(String filename) {
        boolean status = false;

        try {
            if (!Files.isReadable(Paths.get(filename))) {
                App.showMessage("The file " + filename + " does not exists", true);
                return false;
            }

            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            taskList = (ArrayList<TaskItem>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
            return true;

        } catch (Exception e) {
            App.showMessage(e.getMessage(), true);
            return false;
        }
    }

    public boolean saveToFile(String filename) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(taskList);

            objectOutputStream.close();
            fileOutputStream.close();
            return true;

        } catch (Exception e) {
            App.showMessage(e.getMessage(), true);
            return false;
        }
    }

    public static void NewFileCreate(String newFileName) {

        try {
            File myObj = new File(newFileName);
            myObj.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


