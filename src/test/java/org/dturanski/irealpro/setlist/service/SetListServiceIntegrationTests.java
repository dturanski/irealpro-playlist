/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dturanski.irealpro.setlist.service;

import java.io.IOException;

import org.dturanski.irealpro.setlist.domain.SetList;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author David Turanski
 **/
@SpringBootTest(properties = { "logging.level.org.dturanski.irealpro=INFO" })

@RunWith(SpringRunner.class)
public class SetListServiceIntegrationTests {

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
