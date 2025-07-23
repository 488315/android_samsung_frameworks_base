package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationIconContainerAlwaysOnDisplayViewModel$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        KeyguardState keyguardState = (KeyguardState) obj;
        return Boolean.valueOf((keyguardState == KeyguardState.AOD || keyguardState == KeyguardState.DOZING) ? false : true);
    }
}
