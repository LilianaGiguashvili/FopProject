import java.util.Scanner;

public class SwiftToJavaInterpreter {
    private static final StringBuilder javaCode = new StringBuilder(); // To store the generated Java code

    private static int openBraces = 0; // for brace counting
    
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
            } else if (line.contains("abs(")) {
                handleMathFunctions(line);
            } else if (line.startsWith("for")) {
                handleForLoop(line);
            } else if (line.startsWith("while")) {
                handleWhileLoop(line);
            } else if (line.startsWith("if") || line.startsWith("guard")) {
                handleCondition(line);
            } else if (line.contains("true") || line.contains("false")) {
                handleBooleans(line);
            } else if (line.startsWith("print")) {
                handlePrint(line);
            } else if (line.contains("readLine")) {
                handleInput();
            } else {
                handleDefault(line);
            }
        }

        closeOpenBlocks();
        return javaCode.toString();
    }

    private static void handleImport() {
        javaCode.append("import java.util.Scanner;\n");
    }

    private static void handleFunctionDefinition(String line) {
        String functionName = line.split("\\(")[0].replace("func", "").trim();
        String returnType = "void";

        if (line.contains("->")) {
            String[] parts = line.split("->");
            returnType = parts[1].trim().split("\\{")[0]
                    .replace("Int", "int")
                    .replace("Bool", "boolean");
        }

        String params = line.substring(line.indexOf('(') + 1, line.indexOf(')'))
                .replace("_", "") // Remove placeholder
                .replace(":", " ") // Convert Swift style to Java
                .replace("Int", "int")
                .replace("Bool", "boolean");

        javaCode.append("public static ").append(returnType).append(" ").append(functionName)
                .append("(").append(params).append(") {\n");
        increaseBraceCount();
    }

    private static void handleVariableDeclaration(String line, boolean isConstant) {
        if (isConstant) {
            line = line.replace("let", "final int");
        } else {
            line = line.replace("var", "int");
        }
        line = line.replace("=", "= ");
        if (!line.endsWith(";")) {
            line += ";";
        }
        javaCode.append(line).append(";\n");
    }
    private static void handleMathFunctions(String line) {
        line = line.replace("abs(", "Math.abs(");
        if (!line.endsWith(";")) {
            line += ";";
        }
        javaCode.append(line).append("\n");
    }
    private static void handleForLoop(String line) {
        if (line.contains("...")) {
            line = line.replace("for ", "for (int ")
                    .replace(" in ", " = ")
                    .replace("...", "; i <= ")
                    .replace("{", "; i++) {");
        }
        
        else if (line.contains("..<")) {
            line = line.replace("for ", "for (int ")
                    .replace(" in ", " = ")
                    .replace("..<", "; i < ")
                    .replace("{", "; i++) {");
        }

        javaCode.append(line).append("\n");
        increaseBraceCount();
    }

    private static void handleWhileLoop(String line) {
        line = line.replace("while", "while (")
                .replace("{", ") {");
        javaCode.append(line).append("\n");
    }
    
    private static void handleCondition(String line) {
        line = line.replace("if ", "if (")
                .replace("{", ") {")
                .replace("else", "} else {")
                .replace("}", "}");
        javaCode.append(line).append("\n");

        if (line.contains("{")) {
            increaseBraceCount();
        }
        
    }

    private static void handleBooleans(String line) {
        line = line.replace("true", "true").replace("false", "false");
        if (!line.endsWith(";")) {
            line += ";";
        }
        javaCode.append(line).append("\n");
    }

     private static void handlePrint(String line) {
        line = line.replace("print(", "System.out.println(")
                .replace(")", ");");
        javaCode.append(line).append("\n");
    }

    private static void handleInput() {
        javaCode.append("Scanner scanner = new Scanner(System.in);\n");
        javaCode.append("String input = scanner.nextLine();\n");
    }

    private static void handleDefault(String line) {
        if (line.startsWith("return ")) { // Handle return statement
            if (!line.endsWith(";")) {
                line += ";";
            }
        }
        if (line.equals("}")) { // Handle closing brace
            javaCode.append(line).append("\n");
            decreaseBraceCount();
        } else {
            if (!line.endsWith(";") && !line.endsWith("{") && !line.endsWith("}")) {
                line += ";";
            }
            if (line.endsWith("{")) { // Handle opening brace
                increaseBraceCount();
            }
            javaCode.append(line).append("\n");
        }
    }

    private static void increaseBraceCount() {
        openBraces++;
    }

    private static void decreaseBraceCount() {
        openBraces--;
    }

    private static void closeOpenBlocks() {
        while (openBraces > 0) {
            openBraces--;
        }
    }
}

    
