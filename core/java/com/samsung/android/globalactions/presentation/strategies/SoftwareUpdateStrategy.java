package com.samsung.android.globalactions.presentation.strategies;

/* loaded from: classes6.dex */
public interface SoftwareUpdateStrategy {
    boolean onUpdate();

    default void update() {
    }
}
