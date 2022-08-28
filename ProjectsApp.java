//Connection conn = DbConnection.getConnection();
//import projects.dao.DbConnection;

package projects;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exeption.DbException;
import projects.service.ProjectService;


public class ProjectsApp {
	
private static ProjectService projectService = new ProjectService();

private static Scanner scanner = new Scanner (System.in);
		
static // @formatter:off 
	  List<String> operations = List.of(
			  "1) Add a project"
			  
);
// @formatter:on	
	 	
	 public static void main(String[] args) {
		 
	 new ProjectsApp().processUserSelection();
	   processUserSelection();	   
	 }
	private static void processUserSelection() {
		boolean done = false;
		while(!done) {
			
			try {
				int selection = getUserSelection();
				  switch (selection) {
		          case -1:	
		        	  done = exitMenu();
		              break;  
		          case 1:
		        	  createProject();
		        	  default:
		        		  System.out.println("\n" + selection + 
		        		   " is not a valid selection. Try again.");
				}				
			}
			catch (Exception e) {
				System.out.println("\nError:" + e + "Try again");
			}
		}
	}
	private static void createProject() {
		String projectName = getStringInput("Enter the project name");
		BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
		BigDecimal actualHours = getDecimalInput("Enter the actual hours");
		Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
		String notes = getStringInput("Enter the project notes");
		
		Project project = new Project();
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("You have successfully created project: " + dbProject);
	}
	private static BigDecimal getDecimalInput(String promt) {
		String input = getStringInput(promt);
		
		if(Objects.isNull(input)) {
		return null;
	}
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException (input + "is not valid decimal number.");
		}
	}	
	private static boolean exitMenu() {
		System.out.println("Exiting thr meny.");
		return true;
	}
	private static int getUserSelection() {
		printOperations();
		Integer input = getIntInput("Inter a menu selection");
		return Objects.isNull(input) ? -1: input;
	}
	private static void printOperations() {
		System.out.println("\nThese are the available selection. Press the Enter key to quit:");
		operations.forEach(line -> System.out.println("   " + line));
	}
	private static Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
		
		if(Objects.isNull(input)) {
		return null;
		}
		try {
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException (input + "is not valid number.");
		}
	}
		
	private static String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();

	
	}
}
