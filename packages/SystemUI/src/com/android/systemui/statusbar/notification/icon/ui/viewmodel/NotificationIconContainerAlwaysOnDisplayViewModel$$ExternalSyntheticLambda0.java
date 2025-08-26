package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationIconContainerAlwaysOnDisplayViewModel$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        KeyguardState keyguardState = (KeyguardState) obj;
        return Boolean.valueOf((keyguardState == KeyguardState.AOD || keyguardState == KeyguardState.DOZING) ? false : true);
    }
}
