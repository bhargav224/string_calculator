# String Calculator

## Solution Overview

The `src/Calculator.java` class provides a method to add numbers from a string input, supporting custom delimiters, handling newlines, and throwing exceptions for negative numbers.

## Features and Test Cases

```java
// Test case 1: Empty String
calculator.add(""); // Output: 0

// Test case 2: Single Number
calculator.add("1"); // Output: 1

// Test case 3: Two Numbers Separated by a Comma
calculator.add("1,5"); // Output: 6

// Test case 4: Numbers Separated by Comma and Newline
calculator.add("1\n2,3"); // Output: 6

// Test case 5: Custom Delimiters
calculator.add("//;\n1;2"); // Output: 3

// Test case 6: Delimiters of Varying Lengths
calculator.add("//[***]\n1***2***3"); // Output: 6

// Test case 7: Multiple Delimiters
calculator.add("//[*][%]\n1*2%3"); // Output: 6

// Test case 8: Ignoring Numbers Greater Than 1000
calculator.add("2,1001"); // Output: 2

// Test case 9: Negative Numbers (Throws Exception)
try {
    calculator.add("1,-2,3,-4");
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage()); // Output: Negative numbers not allowed: [-2, -4]
}
