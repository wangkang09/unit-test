package com.wangkang.mockitotest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockingDetails;

/**
 * @author wangkang
 * @date 2020/4/9- 22:28
 * @since
 */
@RunWith(MockitoJUnitRunner.class)
public class MockDetailTest {

    @Mock(lenient = true)
    List<Integer> list;
    @Test
    public void test() {
        assert mockingDetails(list).isMock();
        assert !mockingDetails(list).isSpy();

        MockingDetails details = mockingDetails(list);
        //获取 mock  的类型
        assert "interface java.util.List".equals(details.getMockCreationSettings().getTypeToMock().toString());
        //获取默认 Answer 类型
        assert "RETURNS_DEFAULTS".equals(details.getMockCreationSettings().getDefaultAnswer().toString());

        doReturn(1).when(list).get(1);
        list.add(1);
        //获取所有已调用的方法
        assert "[list.add(1);]".equals(details.getInvocations().toString());
        //获取所有打桩的方法信息
        assert "[list.get(1); stubbed with: [Returns: 1]]".equals(details.getStubbings().toString());
        //打印以上所有
        assert details.printInvocations().contains("list.add(1)") && details.printInvocations().contains("list.get(1)");
    }
}
