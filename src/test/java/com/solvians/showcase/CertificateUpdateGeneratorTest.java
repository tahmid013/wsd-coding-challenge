package com.solvians.showcase;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertificateUpdateGeneratorTest {
    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    private static boolean isUppercaseLetter(char c) {
        return c >= 'A' && c <= 'Z';
    }
    private static boolean isAlphanumeric(char c) {
        return (c >= 'A' && c <= 'Z') ||(c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }
    private static String orgString = "DE123456789";

    @Test
    public void generateQuotes() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(10, 100);
        Stream<CertificateUpdate> quotes = certificateUpdateGenerator.generateQuotes();
        assertNotNull(quotes);
        assertEquals( 100, quotes.count());

    }
    @Test
    public void timeStampsConstraints(){
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(1, 1);
        Stream<CertificateUpdate> quotes = certificateUpdateGenerator.generateQuotes();
        CertificateUpdate certificateUpdate = quotes.collect(Collectors.toList()).get(0);

        //1
        long miliEpoch = certificateUpdate.getTimeStamp().toInstant(ZoneOffset.UTC).toEpochMilli();
        // checking mitiEpoch should be non zero to be after Jan 1 1970
        assertTrue(miliEpoch >=0 , "Timestamp should be greater than unix epoch");
    }
    @Test
    public void isinConstraints(){
        CertificateUpdate certificateUpdate = new CertificateUpdate();

        String isin = certificateUpdate.getIsin();
        assertEquals(12, isin.length(), "ISIN must be of size 12");
        String first2Char = isin.substring(0, 2);
        assertTrue(isUppercaseLetter(first2Char.charAt(0)), "1st character is not uppercase");
        assertTrue(isUppercaseLetter(first2Char.charAt(1)), "2nd character is not uppercase");

        String alphanumericChars = isin.substring(2, 11);
        for(int i = 0; i< 9; i++){
            assertTrue(isAlphanumeric(alphanumericChars.charAt(i)), i+2+"th character is not alphanumeric");
        }
        assertTrue(isDigit(isin.charAt(isin.length()-1)), "Last char is not digit");
    }
    // add remaining tests
    @Test
    void checkBidPrice(){
        CertificateUpdate certificateUpdate = new CertificateUpdate();
        Double bidPrice = certificateUpdate.getBidPrice();

        assertTrue(bidPrice>=100.00 && bidPrice <=200.00, "bid price must be between 100.00 and 200.00 inclusive");

        String price = bidPrice.toString();
        String[] decimalLength = price.split("\\.");
        assertTrue(decimalLength[1].length() <= 2, "bid price must hold 2 decimal point" );

    }
    @Test
    void checkAskPrice(){
        CertificateUpdate certificateUpdate = new CertificateUpdate();
        Double askPrice = certificateUpdate.getAskPrice();

        assertTrue(askPrice>=100.00 && askPrice <=200.00, "ask price must be between 100.00 and 200.00 inclusive");

        String price = askPrice.toString();
        String[] decimalLength = price.split("\\.");
        assertTrue(decimalLength[1].length() <= 2, "Ask price must hold 2 decimal point" );

    }

    @Test
    void checkBidSize(){
        CertificateUpdate certificateUpdate = new CertificateUpdate();
        Integer bidSize = certificateUpdate.getBidSize();

        assertTrue(bidSize>=1000 && bidSize<=5000, "Bid size must be between 1000 and 5000 inclusive");
    }
    @Test
    void checkAskSize(){
        CertificateUpdate certificateUpdate = new CertificateUpdate();
        Integer askSize = certificateUpdate.getAskSize();

        assertTrue(askSize>=1000 && askSize<=5000, "Bid size must be between 1000 and 5000 inclusive");
    }
    @Test
    void checkGeneratedMaturityDateWithin2Years(){
        CertificateUpdate certificateUpdate = new CertificateUpdate();
        LocalDate maturityDate = certificateUpdate.getMaturityDate();

        assertTrue(maturityDate.isEqual(LocalDate.now()) ||maturityDate.isAfter(LocalDate.now()), "Maturity date should not be in the past");
        assertTrue(maturityDate.isBefore(LocalDate.now().plusYears(2)), "Maturity date should be within 2 year");
    }
    @Test
    void testCheckDigit(){
        CertificateUpdate certificateUpdate = new CertificateUpdate();
        char c = certificateUpdate.getCheckDigit(orgString);
        assertEquals('6', c, "For the String: DE123456789 check digit should be 6");
    }
}