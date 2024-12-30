import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Swift-to-Java Code Converter!");
        System.out.println("Enter your Swift code line by line. Type 'END' on a new line to finish.\n");

        String swiftCode = collectSwiftCode();
        String javaCode = convertSwiftToJavaCode(swiftCode);

        if (javaCode != null) {
            displayJavaCode(javaCode);
            offerToSave(javaCode);
        } else {
            System.err.println("Conversion failed. Please check your input.");
        }
    }

    private static String collectSwiftCode() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder swiftCode = new StringBuilder();
        String line;

        System.out.println("Enter Swift code:");
        while (!(line = scanner.nextLine()).equalsIgnoreCase("END")) {
            swiftCode.append(line).append("\n");
        }
        return swiftCode.toString();
    }

    private static String convertSwiftToJavaCode(String swiftCode) {
        try {
            return SwiftToJavaInterpreter.convertSwiftToJava(swiftCode);
        } catch (Exception e) {
            System.err.println("Error during conversion: " + e.getMessage());
            return null;
        }
    }

    private static void displayJavaCode(String javaCode) {
        System.out.println("\nConverted Java Code:\n");
        System.out.println(javaCode);
    }

    private static void offerToSave(String javaCode) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to save the output to a file? (yes/no)");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            try (FileWriter writer = new FileWriter("ConvertedJavaCode.java")) {
                writer.write(javaCode);
                System.out.println("Java code saved to 'ConvertedJavaCode.java'");
            } catch (IOException e) {
                System.err.println("Failed to save the file: " + e.getMessage());
            }
        }
    }
}
