package com.samsung.android.sume.core.format;

public interface Copyable<T> extends Cloneable {
    T copy();

    T deepCopy();
}
