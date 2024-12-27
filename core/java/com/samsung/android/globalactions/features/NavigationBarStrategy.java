package com.samsung.android.globalactions.features;

import android.view.Window;

import com.samsung.android.globalactions.presentation.strategies.WindowDecorationStrategy;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.SystemConditions;

public class NavigationBarStrategy implements WindowDecorationStrategy {
    ConditionChecker mConditionChecker;

    public NavigationBarStrategy(ConditionChecker conditionChecker) {
        this.mConditionChecker = conditionChecker;
    }

    @Override // com.samsung.android.globalactions.presentation.strategies.WindowDecorationStrategy
    public void onDecorateWindow(Window window) {
        window.setNavigationBarColor(0);
        int systemUiFlags = window.getDecorView().getSystemUiVisibility();
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_WHITE_THEME)) {
            systemUiFlags |= 16;
        }
        window.getDecorView().setSystemUiVisibility(systemUiFlags);
    }
}
