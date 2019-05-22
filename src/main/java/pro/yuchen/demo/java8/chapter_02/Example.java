package pro.yuchen.demo.java8.chapter_02;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: Mr.Lee
 * @Date: 2019-05-02 21:58
 */
public class Example {

	/**
	 * 模拟分页
	 */
	@Test
	public void example_004_00() {
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
	@Test
	public void example_004_01() {
		// iterate 生成无限流
		// peek 会产生一个与原来相同的新流, 并每获得一个元素, 都会调用一次函数
		// limit 流的结束符
		Object[] powers = Stream.iterate(1.0, x -> x * 2)
								.peek(e -> System.out.println("Fetching " + e))
								.skip(10).limit(20).toArray();
	}

	/**
	 * 去重
	 */
	@Test
	public void example_005_00() {
		Stream<String> stream = Stream.of("A", "A", "A", "B").distinct();
		stream.forEach(System.out::println);
	}

	/**
	 * 排序
	 */
	@Test
	public void example_005_01() {

		// 先比较字符串长度, 若长度相同则比较Ascii, 反转结果
		Stream<String> stream = Stream.of("SSS", "SS", "S", "A", "B", "C", "D")
									  .sorted(Comparator.comparing(String::length)
									  .thenComparing(Comparator.comparingInt(x -> x.charAt(0))).reversed());
		stream.forEach(System.out::println);
	}

	/**
	 * 拿到最大值
	 */
	@Test
	public void example_006_00() {
		Optional<String> largest = Stream.of("SSS", "SS", "S", "A", "B", "C", "D").max(String::compareToIgnoreCase);
		System.out.println(largest.get());
	}

	/**
	 * 查找符合条件的item
	 */
	@Test
	public void example_006_01() {
		// 查找第一个符合条件的item(找到第一个, 结束流)
		Optional<String> largest = Stream.of("SSS", "SS", "S", "A", "B", "C", "D").filter(x -> x.startsWith("S")).findFirst();
		System.out.println(largest.get());

		// 查找任意一下符合条件的item, 多用于并行环境下(任意线程找到, 结束流)
		largest = Stream.of("SSS", "SS", "S", "A", "B", "C", "D").filter(x -> x.startsWith("S")).findAny();
		System.out.println(largest.get());
	}

	/**
	 * 是否匹配
	 */
	@Test
	public void example_006_02() {
		// 任意元素匹配
		boolean largest = Stream.of("SSS", "SS", "S", "A", "B", "C", "D").parallel().anyMatch(x -> x.startsWith("S"));
		System.out.println(largest); // true

		// 所有元素都匹配
		largest = Stream.of("SSS", "SS", "S", "A", "B", "C", "D").parallel().allMatch(x -> x.startsWith("S"));
		System.out.println(largest); // false

		// 没有元素匹配
		largest = Stream.of("SSS", "SS", "S", "A", "B", "C", "D", "").parallel().noneMatch(String::isEmpty);
		System.out.println(largest); // false
	}

	/**
	 * 优雅的选择执行
	 */
	@Test
	public void example_007_01() {
		String str = "";
		Optional<String> option = Optional.of(str);
		List<String> list = Lists.newArrayList();
		// ifPresent 如果Optional中元素,不为空则调用方法, 反之则不调用
		option.filter(StrUtil::isNotBlank).ifPresent(list::add);
		System.out.println(list.size());
	}

	/**
	 * 为空时, 别外的选择(默认值)
	 */
	@Test(expected=RuntimeException.class)
	public void example_007_02() {

		Optional<String> optional = Stream.of("A", "B", "C").filter(x -> x.length() > 0).findFirst();
		String str = optional.orElse("XX");
		System.out.println(optional.isPresent()); // true
		System.out.println(str); // A

		optional = Stream.of("A", "B", "C").filter(x -> x.length() > 1).findFirst();
		str = optional.orElse("XX");
		System.out.println(optional.isPresent()); // false
		System.out.println(str); // XX

		optional = Stream.of("A", "B", "C").filter(x -> x.length() > 1).findFirst();
		str = optional.orElseGet(() -> {
			System.out.println("Not Fond...");
			return "VV";
		});
		System.out.println(optional.isPresent()); // false
		System.out.println(str); // VV

		optional = Stream.of("A", "B", "C").filter(x -> x.length() > 1).findFirst();
		str = optional.orElseThrow(() -> {
			System.out.println("Can not null...");
			return new RuntimeException("Can not null...");
		});
	}

	@Test
	public void example_007_03() {
		Double x = 3.0;
		Optional<Double> optional = x > 0 ? Optional.of(1 / x) : Optional.empty();
		System.out.println(optional.get());

		// value == null ? empty() : of(value)
		optional = Optional.ofNullable(x);
		System.out.println(optional.get());
	}

	@Test
	public void example_007_04() {
		// flatMap()

	}

	@Test
	public void example_008_00() {
		// 聚合操作

		// e op x = x 做为起点
		Stream<Integer> values = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Optional<Integer> sum = values.reduce((x, y) -> x + y);
		System.out.println(sum.get());
		// 流只能被读一次
		values = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		sum = values.reduce(Integer::sum);
		System.out.println(sum.get());




	}

	@Test
	public void example_009_00() {
		// 收集结果
		Stream<String> stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		String[] arr = stream.toArray(String[]::new);
		Stream.of(arr).forEach(System.out::print); // ABCDEFGH

		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		stream.collect(Collectors.toList()).forEach(System.out::print); // ABCDEFGH

		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		stream.collect(Collectors.toSet()).forEach(System.out::print); // ABCDEFGH

		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		stream.collect(Collectors.toCollection(HashSet::new)).forEach(System.out::print); // ABCDEFGH

		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		stream.collect(Collectors.toCollection(HashSet::new)).forEach(System.out::print); // ABCDEFGH

		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		System.out.println(stream.collect(Collectors.joining(",", "(", ")"))); // (A,B,C,D,E,F,G,H)

		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		Map<String, Integer> map = stream.collect(Collectors.toMap(Function.identity(), x -> (int)x.charAt(0)));
		map.keySet().forEach(x -> System.out.println(x + ":" + map.get(x)));
	}

	@Test
	public void example() {
		String expression = "TEXT_1 +sum (TEXT_2)";
		List<String> functions = Arrays.asList("AVG", "COUNT", "MAX", "MIN", "SUM",
											   "COUNT_BIG", "GROUPING", "BINARY_CHECKSUM", "CHECKSUM_AGG",
											   "CHECKSUM", "STDEV", "STDEVP", "VAR", "VARP");
		boolean isAgg = functions.stream().parallel().anyMatch(x -> expression.replace(" ", "").toLowerCase().contains(x.toLowerCase() + "("));
		System.out.println(isAgg);
		System.out.println(expression);
	}




}
