package com.android.systemui.navigationbar.icon;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

final class NavBarIconResourceMapper$supportThemeIcon$1 extends Lambda implements Function0 {
    final /* synthetic */ NavBarIconResourceMapper this$0;

    public NavBarIconResourceMapper$supportThemeIcon$1(NavBarIconResourceMapper navBarIconResourceMapper) {
        super(0);
        this.this$0 = navBarIconResourceMapper;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Context context = this.this$0.context;
        NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
        String string = Settings.System.getString(context.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE);
        boolean z = false;
        if (string != null && string.length() > 0) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}
