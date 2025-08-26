package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import android.content.Intent;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
public final /* synthetic */ class FooterViewModel$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = ModesEmptyShadeFix.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new NotificationActivityStarter.SettingsIntent(new Intent("android.settings.NOTIFICATION_SETTINGS"), null, null, 6, null));
    }
}
