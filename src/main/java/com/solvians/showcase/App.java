package com.solvians.showcase;

import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {
    public App(String threads, String quotes) {

    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length >= 2) {
            int threads = Integer.parseInt(args[0]);
            int quotes = Integer.parseInt(args[1]);

            CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(threads, quotes);
            Stream<CertificateUpdate> streams = certificateUpdateGenerator.generateQuotes();

            streams.forEach( certificateUpdate -> {
                    certificateUpdate.printCertificateUpdate();
                    }
            );


        } else
            throw new RuntimeException("Expect at least number of threads and number of quotes. But got: " + args);
    }
}
