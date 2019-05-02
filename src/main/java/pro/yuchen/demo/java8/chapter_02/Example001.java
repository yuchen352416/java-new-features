package pro.yuchen.demo.java8.chapter_02;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: Mr.Lee
 * @Date: 2019-05-02 21:58
 */
public class Example001 {

	public static void main(String[] args) {
		Stream<String> stream = Stream.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
		List<String> list = stream.limit(4).collect(Collectors.toList());
		list.forEach(System.out::println);
	}


}
