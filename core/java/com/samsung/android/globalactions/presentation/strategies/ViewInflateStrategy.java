package com.samsung.android.globalactions.presentation.strategies;

import android.view.View;

public interface ViewInflateStrategy {
    default void onInflateView(View view) {}
}
