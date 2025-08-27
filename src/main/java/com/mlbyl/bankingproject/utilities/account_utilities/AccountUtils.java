package com.mlbyl.bankingproject.utilities.account_utilities;

public class AccountUtils {
    public static String generateAccountNumber() {
        return String.valueOf((long) (Math.random() * 100000000l));
    }

    public static String generateAccontIBAN(String bankCode) {
        String accontNumber = generateAccountNumber();
        return bankCode + accontNumber;
    }
}
