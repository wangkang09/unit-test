package com.wangkang.spock.test

import com.wangkang.spock.mapper.UserMapper
import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * @author wangkang
 * @date 2020/4/10- 22:53 
 * @since
 */
@SpringBootTest(classes = SpringbootTest)
class SpringbootTestNotRollback extends Specification {

    @Autowired
    UserMapper mapper

    def "mapper 层测试"() {
        expect:
        mapper.addUser("1", 1)
        mapper.count() == 1
    }

    def "mapper rollback 测试"() {
        expect:
        mapper.count() == 0
    }
}
