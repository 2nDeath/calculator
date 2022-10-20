import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        try {
        String output = calc(input);
        System.out.println(output);
        }catch (Exception e){
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) throws Exception{
        input = input.trim();
        // проверка на недопустимые символы
        if(!areCharsAllowed(input)){
            throw new Exception("Есть недопустимые символы");
        }
        // поиск оператора
        char ourOp = findOp(input);
        if(ourOp == '.')
            throw new Exception("Строка не является математической операцией");
        String regOp = opToReg(ourOp);
        // получение операндов и проверка их количества
        String[] tmp = input.split(regOp);
        if (tmp.length < 2)
            throw new Exception("Операндов недостаточно");
        String operand1 = tmp[0].trim();
        String operand2 = tmp[1].trim();
        tmp = operand1.split(" ");
        if (tmp.length > 1)
            throw new Exception("Операндов больше двух");
        tmp = operand2.split(" ");
        if (tmp.length > 1)
            throw new Exception("Операндов больше двух");
        // определение системы счисления и вычисление
        if (isRoman(operand1) != isRoman(operand2))
            throw new Exception("Операнды разного вида");
        if (isRoman(operand1)) {
            Converter converter = new Converter();
            int a = converter.romanToInt(operand1);
            int b = converter.romanToInt(operand2);
            int result = (calculation(a, b, ourOp));
            if (result <= 0)
                throw new Exception("Результат отрицательный либо равен 0");
            return converter.intToRoman(result);
        }else{
            int a = Integer.parseInt(operand1);
            int b = Integer.parseInt(operand2);
            return String.valueOf(calculation(a, b, ourOp));
        }
    }

    private static boolean areCharsAllowed(String input){
        String allowedChars= "IVX+-*/ 1234567890";
        for(int i = 0; i < input.length(); i++){
            if (!allowedChars.contains("" + input.charAt(i))){
                return false;
            }
        }
        return true;
    }

    private static char findOp(String input) throws Exception{
        char[] operators = {'+', '-', '/', '*'};
        boolean opFound = false;
        char ourOp = '.';
        for(int i = 0; i < input.length(); i++){
            for (char operator : operators) {
                if (input.charAt(i) == operator) {
                    if (!opFound) {
                        opFound = true;
                        ourOp = operator;
                    } else {
                        throw new Exception("Несколько операторов");
                    }
                }
            }
        }
        return ourOp;
    }

    private static String opToReg(char ourOp){
        return switch (ourOp) {
            case '+' -> "\\+";
            case '-' -> "-";
            case '*' -> "\\*";
            case '/' -> "/";
            default -> "";
        };
    }

    private static boolean isRoman(String operand) throws Exception{
        String roman = "IXV";
        String arabic = "0123456789";
        boolean arab = false;
        boolean rom = false;
        for(int i = 0; i < operand.length(); i++){
            if(roman.contains("" + operand.charAt(i)))
                rom = true;
            if(arabic.contains("" + operand.charAt(i)))
                arab = true;
        }
        if(rom && arab)
            throw new Exception("Операнд состоит из символов разных систем счисления");
        if(rom)
            return true;
        else return false;
    }

    private static int calculation(int a, int b, char op) throws Exception{
        if(a > 10 || a < 1 || b > 10 || b < 1){
            throw new Exception("Числа за допустимыми границами");
        }
        return switch (op) {
            case '+' -> (a + b);
            case '-' -> (a - b);
            case '/' -> (a / b);
            case '*' -> (a * b);
            default -> 0;
        };
    }
}