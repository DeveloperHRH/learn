package com.learn.management.base;

import java.util.List;
import java.util.Map;

/**
 * <B>Function :</B> <br>
 * <B>General Usage :</B> <br>
 * <B>Special Usage :</B> <br>
 *
 * @author : HRH<br>
 * @version : v1.0
 * @since : 9:32 2018/7/25 0025<br>
 */
public interface BaseDao<T> {
    T select(T t);

    int count(Map map);

    List<T> list(Map map);

    int insert(T t);

    int update(T t);

    int delete(T t);

    int insertPart(T t);
}
