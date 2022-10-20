import java.util.LinkedHashMap;

public class Converter {
    private static LinkedHashMap<Integer, String > intRom = new LinkedHashMap<>();

    public Converter(){
        intRom.put(100,"C");
        intRom.put(90,"XC");
        intRom.put(50,"L");
        intRom.put(40,"XL");
        intRom.put(10,"X");
        intRom.put(9,"IX");
        intRom.put(5,"V");
        intRom.put(4,"IV");
        intRom.put(1,"I");
    }

    public int romanToInt(String roman){
        int result = 0;
        result+= romanCharToInt(roman.charAt(roman.length()-1));
        for(int i = roman.length() - 2; i >= 0; i--){
            int cur = romanCharToInt(roman.charAt(i));
            int prev = romanCharToInt(roman.charAt(i+1));
            if(cur < prev)
                result -= cur;
            else result += cur;
        }
        return result;
    }

    private int romanCharToInt(char c){
        return switch (c){
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            default -> 0;
        };
    }

    public String intToRoman(int a){
        String result = "";
        while(a > 0) {
            for (int key : intRom.keySet()) {
                if (key <= a) {
                    result += intRom.get(key);
                    a -= key;
                    break;
                }
            }
        }
        return result;
    }
}
