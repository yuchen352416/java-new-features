package pro.yuchen.demo.java8.chapter_02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: Mr.Lee
 * @Date: 2019-05-02 21:58
 */
public class Example {

	public static void main(String[] args) {
		example_004();

	}


	/**
	 * 模拟分页
	 */
	private static void example_004() {
		int pageSize = 4;
		int pageNum = 1;
		List<String> array = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
		// limit 返回包含n个元素的新流
		List<String> list = array.stream().limit(pageSize).collect(Collectors.toList());
		list.forEach(System.out::print);
		System.out.println();
		// skip 返回跳过n个元素的新流
		list = array.stream().skip(pageSize * pageNum).collect(Collectors.toList());
		list.forEach(System.out::print);
		System.out.println();
		// 模拟分页
		list = array.stream().skip(pageSize * pageNum).limit(pageSize).collect(Collectors.toList());
		list.forEach(System.out::print);
	}

	private static void example_002() {
		Stream<String> stream = Stream.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
		List<String> list = stream.limit(4).collect(Collectors.toList());
		list.forEach(System.out::println);
	}


}
