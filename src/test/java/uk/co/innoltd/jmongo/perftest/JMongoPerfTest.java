package uk.co.innoltd.jmongo.perftest;

import uk.co.innoltd.jmongo.JMongo;

public class JMongoPerfTest {
	public static void main(String[] args) throws Exception {
		JMongoPerfTest test = new JMongoPerfTest();
		test.runJMongoCachedTests(1, 1000);
		test.runJMongoUnCachedTests(1, 1000);
		test.runNoJMongoTests(1, 1000);
		
		test.runJMongoCachedTests(10, 1000);
		test.runJMongoUnCachedTests(10, 1000);
		test.runNoJMongoTests(10, 1000);
		
		test.runJMongoCachedTests(100, 1000);
		test.runJMongoUnCachedTests(100, 1000);
		test.runNoJMongoTests(100, 1000);
		
		test.runJMongoCachedTests(100, 10000);
		test.runJMongoUnCachedTests(100, 10000);
		test.runNoJMongoTests(100, 10000);
	}

	private void runJMongoCachedTests(int nbThreads, int nbExecutions) throws Exception {
		SamplerThread[] threads = new SamplerThread[nbThreads];

		System.out.printf("Cached, %d threads, %d times : ", threads.length, nbExecutions);

		JMongo jMongo = new JMongo();

		for (int count = 0; count < threads.length; count++) {
			threads[count] = new CachedJMongoThread(jMongo, nbExecutions);
			threads[count].start();
		}

		long avgDuration = 0;
		for (int count = 0; count < threads.length; count++) {
			threads[count].join();
			avgDuration += threads[count].getDuration();
		}
		avgDuration = avgDuration / threads.length;

		System.out.printf("Average exectution time is %d ms\n", avgDuration);
	}

	private void runJMongoUnCachedTests(int nbThreads, int nbExecutions) throws Exception {
		SamplerThread[] threads = new SamplerThread[nbThreads];

		System.out.printf("Uncached, %d threads, %d times : ", threads.length, nbExecutions);

		for (int count = 0; count < threads.length; count++) {
			threads[count] = new UnCachedJMongoThread(nbExecutions);
			threads[count].start();
		}

		long avgDuration = 0;
		for (int count = 0; count < threads.length; count++) {
			threads[count].join();
			avgDuration += threads[count].getDuration();
		}
		avgDuration = avgDuration / threads.length;

		System.out.printf("Average exectution time is %d ms\n", avgDuration);
	}

	private void runNoJMongoTests(int nbThreads, int nbExecutions) throws Exception {
		SamplerThread[] threads = new SamplerThread[nbThreads];

		System.out.printf("Without JMongo, %d threads, %d times : ", threads.length,nbExecutions);

		for (int count = 0; count < threads.length; count++) {
			threads[count] = new NonJMongoThread(nbExecutions);
			threads[count].start();
		}

		long avgDuration = 0;
		for (int count = 0; count < threads.length; count++) {
			threads[count].join();
			avgDuration += threads[count].getDuration();
		}
		avgDuration = avgDuration / threads.length;

		System.out.printf("Average exectution time is %d ms\n", avgDuration);
	}

}
