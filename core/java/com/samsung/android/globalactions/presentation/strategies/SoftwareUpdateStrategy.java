package com.samsung.android.globalactions.presentation.strategies;

public interface SoftwareUpdateStrategy {
    boolean onUpdate();

    default void update() {}
}
