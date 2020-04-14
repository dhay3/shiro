package com.chz;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chz.dao.TeacherMapper;
import com.chz.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
class ShiroMybatisApplicationTests {
	@Autowired
	DataSource dataSource;
	@Autowired
	TeacherMapper teacherMapper;
	@Test
	void contextLoads() throws SQLException {
		System.out.println(dataSource.getConnection());
	}
	@Test
	public void testMp(){
		List<Map<String, Object>> list = teacherMapper.selectMaps(null);
		list.forEach(System.out::println);
		//逻辑删除测试
//		teacherMapper.deleteById(1);
		//乐观锁测试
//		Teacher teacher = new Teacher();
//		teacher.setTName("张三丰").setAge(19).setVersion(0);
//		teacherMapper.update(teacher,new UpdateWrapper<Teacher>().lambda()
//		.eq(Teacher::getId,4));
	}
	@Test
	public void test(){
		Integer i=123;
		String s="123";
		System.out.println(s.equals(i));
		System.out.println(Objects.equals(i,s));
	}
}
