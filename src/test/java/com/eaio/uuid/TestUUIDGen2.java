package com.eaio.uuid;


import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: dominictootell
 * Date: 31/03/2013
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
public class TestUUIDGen2 {

    @Test
    public void testUuidsAreUnique() {
        timeUUIDThreads(100000,4);
        timeUUIDThreads(50000,8);
    }

    private void timeUUIDThreads(int reps, int noOfthreads) {

        Thread[] threads = new Thread[noOfthreads];
        long numberOfUuidsToBeCreates = reps*noOfthreads;
        AtomicLong counter = new AtomicLong(0);
        Map<String,Short> uuids = new ConcurrentHashMap<String,Short>();

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new UUIDRunnable(reps,uuids,counter));
        }

        for (Thread t : threads)
        {
            t.start();
        }

        for (Thread t : threads)
        {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        assertEquals("Should have made " + numberOfUuidsToBeCreates,numberOfUuidsToBeCreates,counter.get());
        assertEquals("Should have made " + numberOfUuidsToBeCreates + " unique ids",numberOfUuidsToBeCreates,uuids.size());

    }

    private class UUIDRunnable implements  Runnable {
        private final long its;
        private final Map<String,Short> values;
        private final AtomicLong counter;

        public UUIDRunnable(long iterations, Map<String,Short> values, AtomicLong counter) {
            its = iterations;
            this.values = values;
            this.counter = counter;
        }

        public void run() {
            long i = its + 1;
            while (0 != --i)
            {
                values.put(new UUID().toString(),Short.MIN_VALUE);
                counter.incrementAndGet();
            }
        }
    }
}
