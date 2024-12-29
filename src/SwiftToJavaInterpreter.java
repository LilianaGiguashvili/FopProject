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
        String returnType = "void"; // Default to void if no return type is specified
        
        if (line.contains("->")) {
            String[] parts = line.split("->");
            returnType = parts[1].trim().split("\\{")[0].replace("Int", "int").replace("Bool", "boolean");
        }

        line = line.replace("func", "public static")
                   .replace("->", "")
                   .replace("Int", "int")
                   .replace("Bool", "boolean");

        javaCode.append("public static ").append(returnType).append(" ").append(functionName).append(" {\n");
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
        line = line.replace("for", "for (int")
                .replace("in", "=")
                .replace("...", "<=")
                .replace("{", "; i++) {");
        javaCode.append(line).append("\n");
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
        if(!line.endsWith(";") && !line.endsWith("{") && !line.endsWith("}")){
            line += ";";
        }
        javaCode.append(line).append("\n");
    }

    private static void closeOpenBlocks() {
        javaCode.append("}\n");
    }
}

    
