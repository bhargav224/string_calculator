import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private static final String DEFAULT_DELIMITER = ",|\n"; // Default delimiters: comma or newline
    private static final String CUSTOM_DELIMITER_PREFIX = "//";
    private static final String NEGATIVE_NUMBERS_MESSAGE = "Negative numbers not allowed: ";
    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 1000;

    private static final String PIPE = "|";
    private static final String MULTIPLE_DELIMITERS_REGEX = "\\[(.*?)\\]";
    private static final String SPECIAL_CHARACTERS_REGEX = "[\\.^$|?*+(){}]";
    private static final String SPECIAL_CHARACTERS_REPLACEMENT = "\\\\$0";

    public static final String NEW_LINE = "\n";
    public static final String OPEN_BRACKET = "[";

    public int add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0; // Return 0 for empty input
        }

        String delimiter = DEFAULT_DELIMITER;
        if (numbers.startsWith(CUSTOM_DELIMITER_PREFIX)) {
            int delimiterIndex = numbers.indexOf(NEW_LINE);
            String delimiterSection = numbers.substring(2, delimiterIndex);

            if (delimiterSection.startsWith(OPEN_BRACKET)) {
                delimiter = parseMultipleDelimiters(delimiterSection);
            } else {
                delimiter = escapeSpecialCharacters(delimiterSection);
            }

            numbers = numbers.substring(delimiterIndex + 1); // Trim delimiter declaration
        }

        String[] tokens = numbers.split(delimiter);
        List<Integer> negatives = new ArrayList<>();
        int sum = 0;

        for (String token : tokens) {
            if (!token.trim().isEmpty()) {
                int number = Integer.parseInt(token.trim());
                if (number < MIN_NUMBER) {
                    negatives.add(number);
                } else if (number <= MAX_NUMBER) { // Ignore numbers greater than 1000
                    sum += number;
                }
            }
        }

        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException(NEGATIVE_NUMBERS_MESSAGE + negatives);
        }

        return sum;
    }

    private String escapeSpecialCharacters(String delimiter) {
        return delimiter.replaceAll(SPECIAL_CHARACTERS_REGEX, SPECIAL_CHARACTERS_REPLACEMENT);
    }

    private String parseMultipleDelimiters(String delimiterSection) {
        Matcher matcher = Pattern.compile(MULTIPLE_DELIMITERS_REGEX).matcher(delimiterSection);
        List<String> delimiters = new ArrayList<>();

        while (matcher.find()) {
            delimiters.add(escapeSpecialCharacters(matcher.group(1)));
        }

        return String.join(PIPE, delimiters);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        // Test cases
        System.out.println(calculator.add("")); // Output: 0
        System.out.println(calculator.add("1")); // Output: 1
        System.out.println(calculator.add("1,5")); // Output: 6
        System.out.println(calculator.add("1\n2,3")); // Output: 6
        System.out.println(calculator.add("//;\n1;2")); // Output: 3
        System.out.println(calculator.add("//[***]\n1***2***3")); // Output: 6
        System.out.println(calculator.add("//[*][%]\n1*2%3")); // Output: 6
        System.out.println(calculator.add("//[***][%%%]\n1***2%%%3")); // Output: 6

        // Test ignoring numbers greater than 1000
        System.out.println(calculator.add("2,1001")); // Output: 2

        // Test negative numbers
        try {
            calculator.add("1,-2,3,-4");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Output: Negative numbers not allowed: [-2, -4]
        }
    }
}



