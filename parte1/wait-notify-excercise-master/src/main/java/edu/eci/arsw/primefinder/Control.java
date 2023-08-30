/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Scanner;

public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;
    private final int NDATA = MAXVALUE / NTHREADS;
    private int threadSearchEnd;
    private PrimeFinderThread pft[];
    Scanner scanner = new Scanner(System.in);

    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];
        int i;
        for(i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA);
            pft[i] = elem;
            threadSearchEnd = (i == NTHREADS)? MAXVALUE: ((i+1)) * NDATA-1;
        }
        pft[i] = new PrimeFinderThread(i * NDATA,threadSearchEnd);
    }

    public static Control newControl() {
        return new Control();
    }

    public void run() {
        for(int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }

        try {
            Thread.sleep(TMILISECONDS);

            int cont = 0;
            for (PrimeFinderThread t : pft) {
                t.stopThread();
                cont += t.getSize();
            }


            System.out.println("Stopped threads");
            System.out.println("Cantidad de " + cont + " números primos.");
            System.out.println("--> ENTER");
            scanner.nextLine();

            cont = 0;
            for (PrimeFinderThread t : pft) {
                t.continueThread();
                t.join();
                cont += t.getSize();
            }
            System.out.println("Continued threads");
            System.out.println("Cantidad de " + cont + " números primos.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}