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

package org.dturanski.irealpro.setlist.domain;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author David Turanski
 **/
@Data
@Table("Z_PRIMARYKEY")
public class PrimaryKey {

	@Id
	@Column(value = "Z_ENT",keyColumn = "Z_ENT")
	private Long id;

	@Column("Z_NAME")
	private String name;

	@Column("Z_SUPER")
	private Long unused;

	@Column("Z_MAX")
	private Long max;
}
