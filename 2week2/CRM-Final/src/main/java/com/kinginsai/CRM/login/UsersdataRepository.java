/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kinginsai.CRM.login;

import com.kinginsai.CRM.entity.Usersdata;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsersdataRepository extends Repository<Usersdata, String> {

    @Transactional
    Usersdata save(Usersdata usersdata);

    @Transactional(readOnly = true)
    @Query(value = "select u.*,v.users_info from users u LEFT JOIN usersinfo v " +
            " on u.users_name = v.users_name " +
            " where u.users_name = :usersName", nativeQuery = true)
    List<Usersdata> findByUsersName(@Param("usersName") String usersName) throws DataAccessException;
}
