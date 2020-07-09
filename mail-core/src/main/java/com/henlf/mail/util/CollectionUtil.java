package com.henlf.mail.util;

import java.util.Collection;

/**
 * @author tanghongfeng
 */
public class CollectionUtil {
    public static <E> boolean isEmpty(Collection<E> coll) {
        return coll == null || coll.size() == 0;
    }

    public static <E> boolean isNotEmpty(Collection<E> coll) {
        return !isEmpty(coll);
    }
}
