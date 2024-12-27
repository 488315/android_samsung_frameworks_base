package com.android.systemui.power;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import java.util.Locale;

public final class ChargerAnimationUtil {
    public static final ChargerAnimationUtil INSTANCE = new ChargerAnimationUtil();

    private ChargerAnimationUtil() {
    }

    public static final boolean checkExceptionalLanguage() {
        String language = Locale.getDefault().getLanguage();
        return "he".equals(language) || "ur".equals(language) || "tr".equals(language) || "eu".equals(language);
    }

    public static float getNowBarCollapsedWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels * (context.getResources().getConfiguration().orientation == 2 ? 0.4f : 0.588f);
    }

    public static final boolean isAodOrLockScreen() {
        int i = ((StatusBarStateControllerImpl) ((SysuiStatusBarStateController) Dependency.sDependency.getDependencyInner(StatusBarStateController.class))).mState;
        return i == 1 || i == 2;
    }
}
