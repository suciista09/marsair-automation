package com.marsair.data;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * Generates promotional codes from the acceptance criteria:
 * format XX9-XXX-999, first digit = discount (1-9 -> 10%-90%),
 * next two digits random, last digit = sum of the other digits modulo 10.
 */
public final class PromoCodeGenerator {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();

    private PromoCodeGenerator() {
    }

    /** A valid code with a random discount digit 1-9. */
    public static String validCode() {
        int discountDigit = 1 + RANDOM.nextInt(9);
        int second = RANDOM.nextInt(10);
        int third = RANDOM.nextInt(10);
        return build(discountDigit, second, third, checkDigit(discountDigit, second, third));
    }

    /** Invalid code: correct format, but the check digit is wrong. */
    public static String codeWithWrongCheckDigit() {
        int discountDigit = 1 + RANDOM.nextInt(9);
        int second = RANDOM.nextInt(10);
        int third = RANDOM.nextInt(10);
        int wrong = (checkDigit(discountDigit, second, third) + 1 + RANDOM.nextInt(9)) % 10;
        return build(discountDigit, second, third, wrong);
    }

    /** Invalid code: discount digit 0 (assumption: only 1-9 are valid), check digit otherwise correct. */
    public static String codeWithZeroDiscount() {
        int second = RANDOM.nextInt(10);
        int third = RANDOM.nextInt(10);
        return build(0, second, third, checkDigit(0, second, third));
    }

    /** Invalid code: a valid code with the dashes removed (breaks the XX9-XXX-999 format). */
    public static String codeWithoutDashes() {
        return validCode().replace("-", "");
    }

    /** Discount percentage of a code, read from its first digit. */
    public static int discountOf(String code) {
        return Character.getNumericValue(code.charAt(2)) * 10;
    }

    /** Oracle: true if the code matches the format and its check digit is correct. */
    public static boolean isValid(String code) {
        if (code == null || !code.matches("^[A-Z]{2}[1-9]-[A-Z]{3}-\\d{3}$")) {
            return false;
        }
        int d1 = Character.getNumericValue(code.charAt(2));
        int d2 = Character.getNumericValue(code.charAt(8));
        int d3 = Character.getNumericValue(code.charAt(9));
        int check = Character.getNumericValue(code.charAt(10));
        return checkDigit(d1, d2, d3) == check;
    }

    private static int checkDigit(int d1, int d2, int d3) {
        return (d1 + d2 + d3) % 10;
    }

    private static String build(int d1, int d2, int d3, int check) {
        return String.format(Locale.ROOT, "%s%d-%s-%d%d%d", letters(2), d1, letters(3), d2, d3, check);
    }

    private static String letters(int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        }
        return sb.toString();
    }
}
