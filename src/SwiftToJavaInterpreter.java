import java.util.Scanner;

public class SwiftToJavaInterpreter {
    private static final StringBuilder javaCode = new StringBuilder(); // To store the generated Java code

    public static String convertSwiftToJava(String swiftCode) {
        String[] lines = swiftCode.split("\\n"); // Split by lines
        for (String line : lines) {
            line = line.trim(); // Remove leading/trailing spaces
            if (line.isEmpty()) continue; // Skip empty lines

            if (line.startsWith("import Foundation")) {
                handleImport();
            } else if (line.startsWith("func")) {
                handleFunctionDefinition(line);
            } else if (line.startsWith("var")) {
                handleVariableDeclaration(line, false);
            } else if (line.startsWith("let")) {
                handleVariableDeclaration(line, true);
            }
        }
        
        return javaCode.toString();
    }

    private static void handleImport() {
        javaCode.append("import java.util.Scanner;\n");
    }

    private static void handleFunctionDefinition(String line) {
        line = line.replace("func", "public static")
                .replace("->", "")
                .replace("Int", "int");
        javaCode.append(line).append(" {\n");
    }

    private static void handleVariableDeclaration(String line, boolean isConstant) {
        if (isConstant) {
            line = line.replace("let", "final int");
        } else {
            line = line.replace("var", "int");
        }
        line = line.replace("=", "= ");
        javaCode.append(line).append(";\n");
    }

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
