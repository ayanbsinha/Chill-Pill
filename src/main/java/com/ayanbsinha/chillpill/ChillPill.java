package com.ayanbsinha.chillpill;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChillPill {
    static{
        alert();
        System.out.println("***** Take A Chill-Pill *****");
        System.out.println("# Keeps Your System Engage.");
        System.out.println("# Helps To Keeps Your Eyes Strain Free.");
        System.out.println("# Helps To Stay Hydrated.");
        System.out.println("Launching Chill-Pill......... \n\n");
    }
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);

        // Thread 1: Runs every 45 seconds
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                // Code to be executed every 59 seconds
                try {
                    ghostFly();
                } catch (AWTException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 45, TimeUnit.SECONDS);

        // Thread 2: Runs every 20 minutes
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                // Code to be executed every 20 minutes
                try {
                    t2020Rule();
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 20, 20, TimeUnit.MINUTES);

        // Thread 3: Runs every 1 hour
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                // Code to be executed every 1 hour
                alert();
                System.out.println(getDateTime() +" : Just checking, did you have water in the last hour?");
            }
        }, 0, 1, TimeUnit.HOURS);
        }
        private static String getDateTime(){
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
            return currentDateTime.format(formatter);
        }
        private static void ghostFly() throws AWTException {
            Point p = MouseInfo.getPointerInfo().getLocation();
            Robot robot = new Robot();
            robot.mouseMove(p.x+1, p.y+1);
        }
        private static void alert(){
            Toolkit.getDefaultToolkit().beep();
        }
        private static void t2020Rule() throws LineUnavailableException, InterruptedException {
            buzz();
            System.out.println(getDateTime() +" : Relax your eyes by looking at object kept 20 feet away for 20-second and wait for next buzz follow the 20-20-20 rule.");
            Thread.sleep(25000);
            buzz();
            System.out.println(getDateTime() +" : Lets resume to work.");
        }

        private static void buzz() throws LineUnavailableException {
            tone(400,500, 0.2);
        }
    private static float SAMPLE_RATE = 8000f;
    private static void tone(int hz, int msecs, double vol)
            throws LineUnavailableException
    {
        byte[] buf = new byte[1];
        AudioFormat af =
                new AudioFormat(
                        SAMPLE_RATE, // sampleRate
                        8,           // sampleSizeInBits
                        1,           // channels
                        true,        // signed
                        false);      // bigEndian
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();
        for (int i=0; i < msecs*8; i++) {
            double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
            buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
            sdl.write(buf,0,1);
        }
        sdl.drain();
        sdl.stop();
        sdl.close();
    }
}
