package com.mlbyl.bankingproject.utilities.masker;

public class MaskUtil {
    public static String formattedIban(String iban) {
        return addSpace(maskIban(iban));
    }

    private static String maskIban(String iban) {
        if (iban == null && iban.length() < 4) return "****";
        return iban.substring(0, 8) + "****" + iban.substring(iban.length() - 4);
    }


    private static String addSpace(String value) {
        StringBuilder spacedValue = new StringBuilder();
        for (int i = 0; i < value.length(); i = i + 4) {
            if (i + 4 <= value.length() - 1) {
                spacedValue.append(value.substring(i, i + 4)).append("-");
            } else {
                spacedValue.append(value.substring(i));
            }
        }
        return spacedValue.toString();
    }

}
