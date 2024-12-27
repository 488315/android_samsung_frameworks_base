package com.android.internal.org.bouncycastle.util;

public interface Selector<T> extends Cloneable {
    Object clone();

    boolean match(T t);
}
