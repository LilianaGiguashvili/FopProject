import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter Swift code (type 'END' to finish):");
        StringBuilder swiftCode = new StringBuilder();
        String line;

        while (!(line = scanner.nextLine()).equals("END")) {
            swiftCode.append(line).append("\n");
        }
        
        String javaCode = SwiftToJavaInterpreter.convertSwiftToJava(swiftCode.toString());
        
        System.out.println("\nConverted Java Code:\n");
        System.out.println(javaCode);

        scanner.close();
    }
}
