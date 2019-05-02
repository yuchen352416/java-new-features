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
//		example_004_00();
		example_004_01();

	}

	/**
	 * 模拟分页
	 */
	private static void example_004_00() {
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

	/**
	 * 无限流与peek
	 */
	private static void example_004_01() {
		// iterate 生成无限流
		// peek 会产生一个与原来相同的新流, 并每获得一个元素, 都会调用一次函数
		// limit 流的结束符
		Object[] powers = Stream.iterate(1.0, x -> x * 2)
								.peek(e -> System.out.println("Fetching " + e))
								.limit(20).toArray();
	}


}
