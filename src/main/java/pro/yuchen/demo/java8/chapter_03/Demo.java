package pro.yuchen.demo.java8.chapter_03;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Mr.Lee
 * @date: 2019-05-16 17:24
 */
public class Demo {

	private static List<DataBaseFunction> functions = new ArrayList<>();

	static {
		functions.add(new DataBaseFunction(1, "SQL Server", 2005f));
		functions.add(new DataBaseFunction(2, "SQL Server", 2005f));
		functions.add(new DataBaseFunction(3, "SQL Server", 2005f));
		functions.add(new DataBaseFunction(4, "SQL Server", 2005f));
		functions.add(new DataBaseFunction(5, "SQL Server", 2005f));

		functions.add(new DataBaseFunction(6, "SQL Server", 2012f));
		functions.add(new DataBaseFunction(7, "SQL Server", 2012f));
		functions.add(new DataBaseFunction(8, "SQL Server", 2012f));
		functions.add(new DataBaseFunction(9, "SQL Server", 2012f));
		functions.add(new DataBaseFunction(10, "SQL Server", 2012f));

		functions.add(new DataBaseFunction(11, "SQL Server", 2019f));
		functions.add(new DataBaseFunction(12, "SQL Server", 2019f));
		functions.add(new DataBaseFunction(13, "SQL Server", 2019f));
		functions.add(new DataBaseFunction(14, "SQL Server", 2019f));
		functions.add(new DataBaseFunction(15, "SQL Server", 2019f));

		functions.add(new DataBaseFunction(16, "MySQL", 5.5f));
		functions.add(new DataBaseFunction(17, "MySQL", 5.5f));
		functions.add(new DataBaseFunction(18, "MySQL", 5.5f));
		functions.add(new DataBaseFunction(19, "MySQL", 5.5f));
		functions.add(new DataBaseFunction(20, "MySQL", 5.5f));

		functions.add(new DataBaseFunction(21, "MySQL", 5.6f));
		functions.add(new DataBaseFunction(22, "MySQL", 5.6f));
		functions.add(new DataBaseFunction(23, "MySQL", 5.6f));
		functions.add(new DataBaseFunction(24, "MySQL", 5.6f));
		functions.add(new DataBaseFunction(25, "MySQL", 5.6f));

		functions.add(new DataBaseFunction(26, "MySQL", 5.7f));
		functions.add(new DataBaseFunction(27, "MySQL", 5.7f));
		functions.add(new DataBaseFunction(28, "MySQL", 5.7f));
		functions.add(new DataBaseFunction(29, "MySQL", 5.7f));
		functions.add(new DataBaseFunction(30, "MySQL", 5.7f));
	}



	public static void main(String[] args) {
		// 查询 mysql 5.6
		List<DataBaseFunction> list = functions.stream().filter(x -> x.getDataBaseType().equals("MySQL"))
				.filter(x -> x.getVersion() <= 5.6).collect(Collectors.toList());
		list.forEach(System.out::println);
	}


	private static class DataBaseFunction {
		Integer id;
		String dataBaseType;
		Float version;

		public DataBaseFunction(Integer id, String dataBaseType, Float version) {
			this.id = id;
			this.dataBaseType = dataBaseType;
			this.version = version;
		}

		public Integer getId() {
			return id;
		}

		public String getDataBaseType() {
			return dataBaseType;
		}

		public Float getVersion() {
			return version;
		}

		@Override
		public String toString() {
			StringJoiner joiner = new StringJoiner(" ");
			joiner.add(id.toString()).add(dataBaseType).add(version.toString());
			return joiner.toString();
		}
	}
}
