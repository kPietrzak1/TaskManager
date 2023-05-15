package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks;

    public static void main(String[] args) {

        loadTasksFromFile("src/main/tasks.csv");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayOptions();
            String input = scanner.nextLine();

            switch (input) {
                case "add":
                    addTask(scanner);
                    break;
                case "remove":
                    removeTask(scanner);
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    exitAndSave();
                    return;
                default:
                    System.out.println("Please select a correct option.");
            }
        }
    }

    public static void displayOptions() {
        String[] options = {"add", "remove", "list", "exit"};

        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        for (String option : options) {
            System.out.println(option);
        }
        System.out.println();
    }

    public static void loadTasksFromFile(String filename) {
        List<String[]> taskList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                taskList.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tasks = new String[taskList.size()][];
        for (int i = 0; i < taskList.size(); i++) {
            tasks[i] = taskList.get(i);
        }
    }

    public static void addTask(Scanner in) {

        System.out.println("Enter task details:");
        String taskDetail = in.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[]{taskDetail};
        System.out.println();
    }

    public static void listTasks() {

        for (int i = 0; i < tasks.length; i++) {
            System.out.println((i + 1) + ". " + tasks[i][0]);
        }
        System.out.println();
    }


    public static void removeTask(Scanner scanner) {

        while (true) {
            System.out.println("Enter task number to remove:");
            String taskNumberStr = scanner.nextLine();
            try {
                int taskNumber = Integer.parseInt(taskNumberStr);
                if (taskNumber < 0) {
                    throw new NumberFormatException();
                }
                tasks = ArrayUtils.remove(tasks, taskNumber - 1);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No task with such number. Please try again.");
            }
        }
        System.out.println();
    }

    public static void exitAndSave() {

        try (PrintWriter writer = new PrintWriter("tasks.csv")) {
            for (String[] task : tasks) {
                writer.println(task[0]);
            }
            System.out.println(ConsoleColors.RED + "Bye bye!" + ConsoleColors.RESET);
        } catch (IOException e) {
            System.out.println("An error occurred while saving tasks to file.");
        }
    }
}
