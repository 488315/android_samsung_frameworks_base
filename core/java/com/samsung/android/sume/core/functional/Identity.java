package com.samsung.android.sume.core.functional;


@FunctionalInterface
public interface Identity<T extends Enum<?>> {
    T identify();
}
