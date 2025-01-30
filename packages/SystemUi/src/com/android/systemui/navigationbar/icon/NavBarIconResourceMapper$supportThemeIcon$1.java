package com.android.systemui.navigationbar.icon;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarIconResourceMapper$supportThemeIcon$1 extends Lambda implements Function0 {
    final /* synthetic */ NavBarIconResourceMapper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NavBarIconResourceMapper$supportThemeIcon$1(NavBarIconResourceMapper navBarIconResourceMapper) {
        super(0);
        this.this$0 = navBarIconResourceMapper;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Context context = this.this$0.context;
        NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
        String string = Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage");
        return Boolean.valueOf(string != null && string.length() > 0);
    }
}
