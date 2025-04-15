package com.solvians.showcase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class CertificateUpdate {

    // TODO: implement me.
    private LocalDateTime timeStamp;
    private String isin;
    private Double bidPrice;
    private Integer bidSize;
    private Double askPrice;
    private Integer askSize;
    private LocalDate maturityDate;
    private Map<Character, Integer> map = new HashMap<>();

    public CertificateUpdate(){
        // random
        this.populateMap();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        // 55 years * 365
        this.timeStamp = LocalDateTime.now().minusDays(random.nextLong(1 + 55 * 365));
        this.isin = generateISIN(random);
        double bid_p = 100.00 + random.nextDouble() * 100.00;
        this.bidPrice = (double) Math.round(bid_p * 100) / 100;

        this.bidSize = random.nextInt(1000, 5001);

        double ask_p  = 100.00 + random.nextDouble() * 100.00;
        this.askPrice = (double) Math.round(ask_p * 100) / 100;
        this.askSize = random.nextInt(1000, 5001);
        // 2years * 12
        this.maturityDate = LocalDate.now().plusMonths(random.nextInt(25));
    }
    private String generateISIN(ThreadLocalRandom random){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char) random.nextInt(65, 91));
        stringBuilder.append((char) random.nextInt(65, 91));
        for(int i = 0; i<9;i++){
            int choice = random.nextInt(1,3);
            if(choice == 1){
                stringBuilder.append((char) random.nextInt(65, 91));
            } else if(choice == 2){
                stringBuilder.append((char) random.nextInt(97, 123));
            } else{
                stringBuilder.append((char) random.nextInt(48, 58));
            }
        }
        // add check digit
        stringBuilder.append(getCheckDigit(stringBuilder.toString()));
        return stringBuilder.toString();
    }

    private void populateMap(){
        int starter = 10;
        for(char c = 'a'; c<='z'; c++){
            this.map.put(c, starter++);
        }
        starter = 10;
        for(char c = 'A'; c<='Z'; c++){
            this.map.put(c, starter++);
        }
    }
    private char getCheckDigit(String orgString){
        int sum = 0;
        int cnt = 0;
        // starting from rightmost
        for(int i = orgString.length()-1; i>=0 ; i--){
            char c = orgString.charAt(i);
            int val = this.map.get(c);
            if(cnt%2 == 0){ // only one after another is multiplied by 2
                val = val*2;
            }
            if(val>9){
                sum+=val%10;
                sum+=(int)(val/10);
            } else{
                sum+=val;
            }
            cnt++;
        }
        // subtract from multiple of immediate grater value
        int immediateGreater;
        if(sum %10 == 0 )  immediateGreater = sum;
        else{
            immediateGreater = (sum/10 +1) * 10;
        }
        sum = immediateGreater - sum;
        return (char) ('0' + sum);
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Integer getAskSize() {
        return askSize;
    }

    public void setAskSize(Integer askSize) {
        this.askSize = askSize;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Integer getBidSize() {
        return bidSize;
    }

    public void setBidSize(Integer bidSize) {
        this.bidSize = bidSize;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }
    public void printCertificateUpdate(){
        StringBuilder report = new StringBuilder();
        report.append(" Printing Certificate Update \n");
        report.append(" ----------------------------\n");
        report.append("Timestamp: "+ this.timeStamp+"\n");
        report.append("ISIN: "+ this.isin+"\n");
        report.append("BidPrice: "+ this.bidPrice+"\n");
        report.append("BidSize: "+ this.bidSize+"\n");
        report.append("askPrice: "+ this.askPrice+"\n");
        report.append("AskSize: "+ this.askSize+"\n");
        report.append("Maturity Date: "+ this.maturityDate+"\n");
        report.append(" ----------------------------\n");
        System.out.println(report);


    }
}
