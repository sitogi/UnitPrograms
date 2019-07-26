package takano.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadSample {

	public static void main(String[] args) {
		useThread();
	}

	private static void useThread() {
		final ExecutorService executorService = Executors.newFixedThreadPool(3);

		final List<Future<Long>> futures = new ArrayList<>();
		futures.add(executorService.submit(new CallableImpl()));
		futures.add(executorService.submit(new CallableImpl()));
		futures.add(executorService.submit(new CallableImpl()));

		/* このブロックが実質 join みたいな感じ？ */
		for (final Future<Long> future : futures) {
			try {
				System.out.println("future.get() start");
				System.out.println(future.get());
			} catch (InterruptedException | ExecutionException ignore) {
			}
		}

		System.out.println("終わりに到達");
		executorService.shutdown(); // 必ず shutdown すること！！！
	}

	private static class RunnableImpl implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 3; i++) {
				System.out.println(Thread.currentThread().getId());
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException ignore) {
				}
			}
		}

	}

	private static class CallableImpl implements Callable<Long> {

		@Override
		public Long call() throws InterruptedException {
			System.out.println("call is called.");
			TimeUnit.SECONDS.sleep(2);
			System.out.println("call is finished.");
			return Thread.currentThread().getId();
		}

	}

}
