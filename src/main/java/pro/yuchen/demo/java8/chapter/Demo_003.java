package pro.yuchen.demo.java8.chapter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @description:
 * @author: Mr.Lee
 * @date: 2019-06-04 15:49
 */
public class Demo_003 {

	public static void main(String[] args) {
		List<X> list = Lists.newArrayList();
		list.add(new X(1, "1"));
		list.add(new X(2, "2"));
		list.add(new X(3, "3"));
		list.add(new X(4, "4"));

		X x = list.get(1);
		System.out.println(x);


	}
}


class X {
	private Integer id;
	private String name;

	public X(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof  X) {
			X x = (X) o;
			if (Objects.equals(x.id, this.id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "X{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}