package org.dturanski.irealpro.setlist;

import java.io.IOException;

import org.dturanski.irealpro.setlist.domain.SetList;
import org.dturanski.irealpro.setlist.service.SetListService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author David Turanski
 **/
@SpringBootTest(properties = {"logging.level.org.dturanski.irealpro=INFO"})

@RunWith(SpringRunner.class)
public class SetListServiceTests {

	@Autowired
	private SetListService setListService;

	@Test
	public void prepare() throws IOException {

		SetList setList = readResourceAndPrepare("setlist1.txt");
		System.out.println("\nsetlist1\n");
		setList.getCandidateSongs().stream().forEach(System.out::println);

		setList = readResourceAndPrepare("setlist2.txt");
		System.out.println("\nsetlist2\n");
		setList.getCandidateSongs().stream().forEach(System.out::println);

		setList = readResourceAndPrepare("setlist-nokey.txt");
		System.out.println("\nsetlist-nokey\n");
		setList.getCandidateSongs().stream().forEach(System.out::println);

	}

	private SetList readResourceAndPrepare(String resourcePath) throws IOException {
		String contents = convertStreamToString(new ClassPathResource(resourcePath).getInputStream());
		return setListService.prepare(contents);
	}

	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

}
