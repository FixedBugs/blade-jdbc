package blade.jdbc.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import blade.jdbc.Pager;
import blade.jdbc.model.Person;
import blade.jdbc.tx.Transaction;

public class CRUDTest extends BaseTest {

	@Test
	public void testInsert(){
		Person joe = new Person();
		joe.name = "Li";
		joe.lastName = "Joy";
		joe.dob = new Date();
		joe.created_at = new Date();
		
		Person.db.insert(joe);
	}
	
	/**
	 * 查询列表
	 */
	@Test
	public void testSearch(){
		List<Person> person = Person.db.list(Person.class);
		System.out.println(person);
	}
	
	/**
	 * 分页查询
	 */
	@Test
	public void testPage(){
		Pager<Person> person = Person.db.page(1, 6, Person.class);
		System.out.println(person);
	}
	
	/**
	 * 自定义查询字段
	 */
	@Test
	public void testCustomSql(){
		List<Person> person = Person.db.sql("select id, name from person").list(Person.class);
		System.out.println(person);
	}
	
	/**
	 * 查询一条
	 */
	@Test
	public void testFindOne(){
		Person person = Person.db.eq("id", 1).first(Person.class);
		System.out.println(person);
	}
	
	/**
	 * 根据主键查询
	 */
	@Test
	public void testByPK(){
		Person person = Person.db.findByPK(1, Person.class);
		System.out.println(person);
	}
	
	/**
	 * 查询记录数
	 */
	@Test
	public void testCount(){
		Long count = Person.db.like("name", "ja%").count(Person.class);
		System.out.println(count);
	}
	
	/**
	 * In查询
	 */
	@Test
	public void testIn(){
		List<Person> person = Person.db.in("id", new Integer[]{1,2}).orderBy("created_at desc").list(Person.class);
		System.out.println(person);
	}
	
	/**
	 * where查询
	 */
	@Test
	public void testWhere(){
		List<Person> person = Person.db.lt("dob", new Date())
				.lt("id", 999)
				.orderBy("created_at desc").list(Person.class);
		System.out.println(person);
	}
	
	/**
	 * 事务测试
	 */
	@Test
	public void testTx(){
		Transaction trans = Person.db.startTransaction();
		try {
			Person row1 = new Person();
			row1.name = "hello";
			row1.lastName = "world";
			Person.db.transaction(trans).insert(row1);
			
			Person row2 = new Person();
			row2.id = 1;
			row2.name = "qqq";
			row2.lastName = "wannwanwa";
			row2.created_at = new Date();
			row2.dob = new Date();
			int a = 1 /0;
			System.out.println(a);
			Person.db.transaction(trans).update(row2, true);
		    trans.commit();
		} catch (Exception t) {
		    trans.rollback();
		    t.printStackTrace();
		}
	}
	
	/**
	 * 根据主键更新
	 */
	@Test
	public void updateByPk(){
		Person person = new Person();
		person.id = 1;
		person.dob = new Date();
		Person.db.update(person);
	}
}
