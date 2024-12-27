package com.android.internal.util.function;

@FunctionalInterface
public interface LongObjPredicate<T> {
    boolean test(long j, T t);
}
