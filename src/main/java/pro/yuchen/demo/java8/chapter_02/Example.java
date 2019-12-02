package pro.yuchen.demo.java8.chapter_02;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
	 * 判断流中是否包含Item
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
		x = null;
		Optional<Double> o = Optional.of(Optional.ofNullable(x).orElseGet(() -> 0.0));
		System.out.println(o.get());
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

		// toList
		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		stream.collect(Collectors.toList()).forEach(System.out::print); // ABCDEFGH

		// toSet
		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		stream.collect(Collectors.toSet()).forEach(System.out::print); // ABCDEFGH

		// HashSet
		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		stream.collect(Collectors.toCollection(HashSet::new)).forEach(System.out::print); // ABCDEFGH

		// joining
		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		System.out.println(stream.collect(Collectors.joining(",", "(", ")"))); // (A,B,C,D,E,F,G,H)

		// toMap
		System.out.println();
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H");
		Map<String, Integer> map = stream.collect(Collectors.toMap(Function.identity(), x -> (int) x.charAt(0)));
		System.out.println(map);
//		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "", "H");
//		map = stream.filter(StrUtil::isNotBlank).collect(Collectors.toMap(Function.identity(), x -> (int) x.charAt(0), (x, y) -> x, TreeMap::new));
//		System.out.println(map);
	}

	@Test
	public void example_011_00() {

		// 分组
		Stream<String> stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "", "H");
		Map<Integer, List<String>> map_0 = stream.filter(StrUtil::isNotBlank).collect(Collectors.groupingBy(x -> x.charAt(0) % 3, Collectors.toList()));
		System.out.println(map_0);

		// 分片
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "", "H");
		Predicate<String> predicate = x -> x.charAt(0) % 2 == 0;
		Map<Boolean, Set<String>> map_1 = stream.filter(StrUtil::isNotBlank).collect(Collectors.partitioningBy(predicate, Collectors.toSet()));
		System.out.println(map_1);

		// downstream 处理
		stream = Stream.of( "A", "B", "C", "D", "E", "F", "G", "H", "", "H");
		// count
		Map<Integer, Long> map_2 = stream.filter(StrUtil::isNotBlank).collect(Collectors.groupingBy(x -> x.charAt(0) % 3, Collectors.counting()));
		System.out.println(map_2);

		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "", "H");
		// sum
		Map<Integer, Long> map_3 = stream.filter(StrUtil::isNotBlank).collect(Collectors.groupingBy(x -> x.charAt(0) % 3, Collectors.summingLong(x -> x.charAt(0))));
		System.out.println(map_3);

		/*
		 返回负数，意味着x比y小
		 返回零，  意味着x等于y
		 返回正数，意味着x比y大
		 */
		stream = Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "", "H");
		Comparator<String> comparator = (x, y) -> {
			if (x.charAt(0) > y.charAt(0)) {
				return 1;
			} else if (x.charAt(0) < y.charAt(0)) {
				return -1;
			} else {
				return 0;
			}
		};
		// max or min
		Map<Object, Optional<String>> map_4 = stream.filter(StrUtil::isNotBlank).collect(Collectors.groupingBy(x -> x.charAt(0) % 3, Collectors.maxBy(comparator)));
		System.out.println(map_4);

		// groupingby & mapping
		Stream<BlogPost> blogs = Stream.of(
			new BlogPost("A", "yuchen", BlogPostType.GUIDE, 1),
			new BlogPost("B", "yuchen", BlogPostType.GUIDE, 2),
			new BlogPost("C", "yuchen", BlogPostType.GUIDE, 3),
			new BlogPost("D", "yuchen", BlogPostType.REVIEW, 4),
			new BlogPost("E", "yuchen", BlogPostType.REVIEW, 5),
			new BlogPost("F", "yuchen", BlogPostType.REVIEW, 6),
			new BlogPost("G", "yuchen", BlogPostType.NEWS, 7),
			new BlogPost("H", "yuchen", BlogPostType.NEWS, 8),
			new BlogPost("I", "yuchen", BlogPostType.NEWS, 9)
		);
		Map<BlogPostType, List<BlogPost>> map_5 = blogs.collect(Collectors.groupingBy(BlogPost::getType, Collectors.mapping(Function.identity(), Collectors.toList())));
		JSONObject json = new JSONObject(map_5);
		System.out.println(json.toJSONString(0));

		// 分组聚合操作
		blogs = Stream.of(
			new BlogPost("A", "yuchen", BlogPostType.GUIDE, 1),
			new BlogPost("B", "yuchen", BlogPostType.GUIDE, 2),
			new BlogPost("C", "yuchen", BlogPostType.GUIDE, 3),
			new BlogPost("D", "yuchen", BlogPostType.REVIEW, 4),
			new BlogPost("E", "yuchen", BlogPostType.REVIEW, 5),
			new BlogPost("F", "yuchen", BlogPostType.REVIEW, 6),
			new BlogPost("G", "yuchen", BlogPostType.NEWS, 7),
			new BlogPost("H", "yuchen", BlogPostType.NEWS, 8),
			new BlogPost("I", "yuchen", BlogPostType.NEWS, 9)
		);

		Map<String, String> map_6 = blogs.collect(Collectors.groupingBy(x -> x.getType().toString(), Collectors.reducing("", BlogPost::getTitle, (x, y) -> x + y)));
		json = new JSONObject(map_6);
		System.out.println(json.toString());
	}

	@Test
	public void example_012_00() {
		// 原始类型
		IntStream stream = IntStream.of(1, 2, 3);
		stream.forEach(System.out::print);

		System.out.println();
		int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		stream = Arrays.stream(arr, 1, 8);
		stream.forEach(System.out::print);

		System.out.println();
		stream = IntStream.range(0, 100); // 0~99
		stream.forEach(x -> System.out.print(x + " "));

		System.out.println();
		stream = IntStream.rangeClosed(0, 100); // 0~100
		stream.forEach(x -> System.out.print(x + " "));

		// mapToInt, mapToLong, mapToDouble
		System.out.println();
		stream = Stream.of("", "A", "B", "C", "D", "E", "F").mapToInt(String::length);
		stream.forEach(x -> System.out.print(x + " "));

		// 原始流转对象流
		System.out.println();
		Stream<Integer> stream_1 = IntStream.of(arr).boxed();
		stream_1.forEach(x -> System.out.print(x + " "));
	}

	@Test
	public void example_013_00() {
		// 并行流
		int[] arr = new int[12];
		Stream<String> words = Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "", "AAA");
		// parallel 使 consumer 在多线程下执行, 但主线程会进行等待
		// 由于 arr 并非原子操作, 当在多线程环境下, 同时对同一下标的item进行修改时, 可能会出现值覆盖现象
		Consumer<String> consumer = x -> {
//			try { Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
			if(x.length() < 12) {
				arr[x.length()]++;
			}
		};
		words.parallel().forEach(consumer);
		// 但结果是混沌的
		System.out.println(Arrays.toString(arr)); // [0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

		words = Stream.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "", "AAA");
		Collection<Long> list = words.collect(Collectors.groupingBy(String::length, Collectors.counting())).values();
		System.out.println(list); // [1, 11, 1]

		List<String> arr_1 = new ArrayList<>(Arrays.asList("A", "B", "C", "", "A"));
		words = arr_1.stream();
		arr_1.addAll(Arrays.asList("X", "Y", "Z"));
		Long n = words.distinct().filter(StrUtil::isNotBlank).count();
		System.out.println(n); // 6
	}

	@Test
	public void example_014_00() {
		// 函数式接口
		/*
		函数式接口				参数类型		返回类型		描述
		Supplier<T>				无			T			提供一个T类型的值
		Consumer<T>				T			void		处理一个T类型的值
		BiConsumer<T, U>		T, U		void		处理T类型和U类型的值
		Predicate<T>			T			boolean 	一个计算boolean值的函数
		ToIntFunction<T>		T			int			参数为T, 返回为int
		TOLongFunction<T>		T			long		参数为T, 返回为long
		ToDoubleFunction<T>		T			double		参数为T, 返回为double
		IntFunction<R>			int			R			参数为int, 返回为R
		LongFunction<R>	    	long		R			参数为long, 返回为R
		DoubleFunction<R>		double		R			参数为double, 返回为R
		Function<T, R>			T 			R			参数为T, 返回为R
		BiFunction<T, U, R> 	T, U 		R 			参数为T, U, 返回为R
		UnaryOperator<T>		T			T			参数为T, 返回为T
		BinaryOperator<T>		T, T 		T			参数为T, T, 返回为T
		*/
		Supplier<String> supplier = () -> "Hello Supplier"; // 原来的无参方法
		System.out.println(supplier.get());

		Consumer<StringJoiner> consumer = x -> x.add("Hello").add("Consumer");
		StringJoiner joiner = new StringJoiner(" ");
		consumer.accept(joiner);
		System.out.println(joiner.toString());

		BiConsumer<StringJoiner, String> biConsumer = (join, s) -> join.add(String.valueOf(s.length()));
		biConsumer.accept(joiner, "X.X.X");
		System.out.println(joiner.toString());

		Predicate<String> predicate = x -> x.length() > 12;
		System.out.println(predicate.test("xxx"));

		ToIntFunction<String> toInt = x -> x.length(); // String::length
		System.out.println(toInt.applyAsInt("xxx"));

		ToLongFunction<String> toLong = x -> Long.valueOf(x.length());
		System.out.println(toLong.applyAsLong("xxx"));

		ToDoubleFunction<String> toDouble = x -> Double.valueOf(x.length());
		System.out.println(toDouble.applyAsDouble("xxx"));

		IntFunction<String> intFunction = x -> String.valueOf(x); // String::valueOf
		System.out.println(intFunction.apply(1));

		LongFunction<String> longFunction = x -> String.valueOf(x); // String::valueOf
		System.out.println(longFunction.apply(1L));

		DoubleFunction<String> doubleFunction = x -> String.valueOf(x); // String::valueOf
		System.out.println(doubleFunction.apply(1.0));

		Function<String, Integer> function = s -> s.length(); // String::length
		System.out.println(function.apply("xxx"));

		BiFunction<Integer, Double, String> biFunction = (x, y) -> String.valueOf(x + y);
		System.out.println(biFunction.apply(1, 1.0));

		UnaryOperator<StringJoiner> unaryOperator = x -> x.add("X.X.X.X.X.X.X");
		joiner = unaryOperator.apply(joiner);
		System.out.println(joiner.toString());

		BinaryOperator<String> binaryOperator = (x, y) -> x + y;
		System.out.println(binaryOperator.apply("ABC", "DEF"));

	}

	@Test
	public void example() {
		String expression = "TEXT_1 + sum (TEXT_2)";
		List<String> functions = Arrays.asList("AVG", "COUNT", "MAX", "MIN", "SUM",
											   "COUNT_BIG", "GROUPING", "BINARY_CHECKSUM", "CHECKSUM_AGG",
											   "CHECKSUM", "STDEV", "STDEVP", "VAR", "VARP");
		boolean isAgg = functions.stream().parallel().anyMatch(x -> expression.replace(" ", "").toLowerCase().contains(x.toLowerCase() + "("));
		System.out.println(isAgg);
		System.out.println(expression);
	}

	@Test
	public void demo() {
		// 以10分钟为单位显示时间
		Long t = new Date().getTime();
		System.out.println(DateUtil.format(new Date(t), "yyyy-MM-dd HH:mm:ss"));
		t = t - t % (1000 * 60 * 10);
		System.out.println(DateUtil.format(new Date(t), "yyyy-MM-dd HH:mm:ss"));
		// 比较器
		/*
		 返回负数，意味着x比y小
		 返回零，  意味着x等于y
		 返回正数，意味着x比y大
		 */
		String [] array = {"1_20120522183000","1_20190522184000","1_20290522181000","1_20190422181000"};
		Optional<String> str = Stream.of(array).max((x, y) -> {
			Long _x = DateUtil.parse(x.split("_")[1], "yyyyMMddHHmmss").getTime();
			Long _y = DateUtil.parse(y.split("_")[1], "yyyyMMddHHmmss").getTime();
			if (_x < _y) {
				return -1;
			} else if (_x > _y) {
				return 1;
			} else {
				return 0;
			}
		});
		System.out.println(str.get());

	}


}

class BlogPost {
	String title;
	String author;
	BlogPostType type;
	int likes;

	public BlogPost(String title, String author, BlogPostType type, int likes) {
		this.title = title;
		this.author = author;
		this.type = type;
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "BlogPost{" +
				"title='" + title + '\'' +
				", author='" + author + '\'' +
				", type=" + type +
				", likes=" + likes +
				'}';
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public BlogPostType getType() {
		return type;
	}

	public int getLikes() {
		return likes;
	}
}

enum BlogPostType {
	NEWS,
	REVIEW,
	GUIDE
}


