package pro.yuchen.demo.java8.chapter_03;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @description:
 * @author: Mr.Lee
 * @date: 2019-05-29 13:47
 */
public class Example {

	@Test
	public void test() {
		ArrayList<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(5);
		list.add(1);
		list.add(3);
		list.add(4);
		Integer[] arr =  list.toArray(new Integer[list.size()]);
		System.out.println(Arrays.toString(arr));
		Arrays.sort(arr);
		System.out.println(Arrays.toString(arr));
	}

	@Test
	public void example_00() {
		// 延迟执行接口函数
		repeat(10, x -> System.out.println("Countdown: " + (9 - x)));
	}

	@Test
	public void example_01() {
		// 返回接口函数, 有点闭包的影子了
		System.out.println(re("1").test("1"));
		System.out.println(re(1).test(1));

	}

	@Test
	public void example_02() {
		// 组合接口函数
		UnaryOperator<Integer> u = compose(x -> x + 1, x-> x + 2);
		System.out.println(u.apply(1));  // 4 -> 1 + 1 + 2
	}

	/**
	 * 延迟执行
	 * @param n
	 * @param action
	 */
	private static void repeat(int n, IntConsumer action) {
		for (int i = 0; i < n; i++) {
			action.accept(i);
		}
	}

	/**
	 * 返回接口函数
	 * @param t 操作实体
	 * @param <T> 返回函数操作类型
	 * @return
	 */
	private static <T> Predicate<T> re(T t) {
		if (t instanceof String) {
			return x -> t.equals(x);
		} else {
			return x -> Objects.nonNull(x) && t.equals(x);
		}
	}

	private static UnaryOperator<Integer> compose(UnaryOperator<Integer> ope1, UnaryOperator<Integer> ope2) {
		return x -> ope2.apply(ope1.apply(x));
	}

}
