package com.android.keyguard;

public interface KeyguardSecurityView {
    boolean needsInput();

    default void onStartingToHide() {
    }
}
