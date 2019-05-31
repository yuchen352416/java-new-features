package pro.yuchen.demo.java8.chapter_03;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;

/**
 * @description:
 * @author: Mr.Lee
 * @date: 2019-05-30 18:16
 */
public class ExecutorDemo {

	@Test
	public void test_001() throws ExecutionException, InterruptedException {
		Callable<String> longTask = () -> {
			TimeUnit.SECONDS.sleep(5);
			System.out.println("execute long task");
			return "A";
		};

		Callable<String> shortTask = () -> {
			System.out.println("execute short task");
			return "B";
		};

		List<String> list = Lists.newArrayList();

		ExecutorService executor = Executors.newCachedThreadPool();

		executor.execute(() -> System.out.println("execute runnable"));

		list.add(executor.submit(shortTask).get());
		list.add(executor.submit(shortTask).get());
		list.add(executor.submit(longTask).get());
		list.add(executor.submit(shortTask).get());
		/*
		isShutDown当调用shutdown()或shutdownNow()方法后返回为true。 
		isTerminated当调用shutdown()方法后，并且所有提交的任务完成后返回为true;
		isTerminated当调用shutdownNow()方法后，成功停止后返回为true;
		如果线程池任务正常完成，都为false
		*/
		executor.shutdown();
		System.out.println("isShutdown: " + executor.isShutdown());
		System.out.println("isTerminated: " + executor.isTerminated());
		/*
		execute runnable
		execute short task
		execute short task
		execute long task
		execute short task
		*/
		// 因 get() 会阻塞主线程.
		System.out.println(list); // [B, B, A, B]


		ExecutorService es = Executors.newCachedThreadPool();
		Future<String> future_1 = es.submit(shortTask);
		Future<String> future_2 = es.submit(shortTask);
		Future<String> future_3 = es.submit(longTask);
		Future<String> future_4 = es.submit(shortTask);
		list.clear();
		list.add(future_1.get());
		list.add(future_2.get());
		list.add(future_3.get());
		list.add(future_4.get());
		/*
		isShutDown当调用shutdown()或shutdownNow()方法后返回为true。 
		isTerminated当调用shutdown()方法后，并且所有提交的任务完成后返回为true;
		isTerminated当调用shutdownNow()方法后，成功停止后返回为true;
		如果线程池任务正常完成，都为false
		*/
		System.out.println("isShutdown: " + es.isShutdown());
		System.out.println("isTerminated: " + es.isTerminated());
		/*
		execute short task
		execute short task
		execute short task
		execute long task
		*/
		// 因 get() 为后执行, 不会影响 shortTask 的执行
		System.out.println(list); // [B, B, A, B]

		int i = 0;
		while (!es.awaitTermination(1, TimeUnit.SECONDS)) {
			System.out.println();
			System.out.println("线程池没有关闭");
			System.out.println("isShutdown: " + es.isShutdown());
			System.out.println("isTerminated:" + es.isTerminated());
			if (3 < i++) {
				es.shutdown();
			}
		}
		System.out.println();
		System.out.println("线程池已关闭");
		System.out.println("isShutdown: " + es.isShutdown());
		System.out.println("isTerminated:" + es.isTerminated());
	}

	@Test
	public void test_002() {
		/*
		1. newCachedThreadPool 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
		2. newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
		3. newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
		4. newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
		*/




	}


}
