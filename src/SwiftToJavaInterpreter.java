import java.util.Scanner;

public class SwiftToJavaInterpreter {
    public static String convertSwiftToJava(String swiftCode) {
        StringBuilder javaCode = new StringBuilder();

        // Split the input Swift code by lines
        String[] lines = swiftCode.split("\\n");

        // Process each line of Swift code
        for (String line : lines) {
            line = line.trim(); // Remove leading/trailing spaces

            // Handle import statement
            if (line.startsWith("import Foundation")) {
                javaCode.append("import java.util.Scanner;\n");
            }
            // Handle function definition
            else if (line.startsWith("func")) {
                line = line.replace("func", "public static");
                line = line.replace("->", "");
                line = line.replace("Int", "int");
                javaCode.append(line).append(" {\n");
            }
            // Handle variable declaration (var)
            else if (line.startsWith("var")) {
                line = line.replace("var", "int");
                line = line.replace("=", "= ");
                javaCode.append(line).append(";\n");
            }
            // Handle variable declaration (let - constant)
            else if (line.startsWith("let")) {
                line = line.replace("let", "final int");
                line = line.replace("=", "= ");
                javaCode.append(line).append(";\n");
            }
            // Handle for loop
            else if (line.startsWith("for")) {
                line = line.replace("for", "for (int");
                line = line.replace("in", "=");
                line = line.replace("...", "<=");
                line = line.replace("{", "; i++) {");
                javaCode.append(line).append("\n");
            }
            // Handle print statements
            else if (line.startsWith("print")) {
                line = line.replace("print(", "System.out.println(");
                line = line.replace(")", ");");
                javaCode.append(line).append("\n");
            }
            // Handle input
            else if (line.contains("readLine")) {
                javaCode.append("Scanner scanner = new Scanner(System.in);\n");
                javaCode.append("int n = scanner.nextInt();\n");
            }
            // Default (copy any other code)
            else {
                javaCode.append(line).append("\n");
            }
        }

        // Close any open blocks
        javaCode.append("}\n");

        return javaCode.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input Swift code
        System.out.println("Enter Swift code (type 'END' to finish):");
        StringBuilder swiftCode = new StringBuilder();
        String line;

        while (!(line = scanner.nextLine()).equals("END")) {
            swiftCode.append(line).append("\n");
        }

        // Convert Swift code to Java code
        String javaCode = convertSwiftToJava(swiftCode.toString());

        // Output Java code
        System.out.println("\nConverted Java Code:\n");
        System.out.println(javaCode);

        scanner.close();
    }
}
