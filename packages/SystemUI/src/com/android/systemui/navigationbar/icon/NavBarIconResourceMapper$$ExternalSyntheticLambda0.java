package com.android.systemui.navigationbar.icon;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class NavBarIconResourceMapper$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavBarIconResourceMapper f$0;

    public /* synthetic */ NavBarIconResourceMapper$$ExternalSyntheticLambda0(NavBarIconResourceMapper navBarIconResourceMapper, int i) {
        this.$r8$classId = i;
        this.f$0 = navBarIconResourceMapper;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        boolean z = false;
        NavBarIconResourceMapper navBarIconResourceMapper = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Context context = navBarIconResourceMapper.context;
                NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
                String string = Settings.System.getString(context.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE);
                if (string != null && string.length() > 0) {
                    z = true;
                }
                return Boolean.valueOf(z);
            default:
                if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && navBarIconResourceMapper.context.getDisplayId() == 1) {
                    z = true;
                }
                return Boolean.valueOf(z);
        }
    }
}
