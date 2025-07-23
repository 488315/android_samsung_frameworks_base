package com.android.systemui.shade;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecNotificationShadeWindowControllerHelperImpl$attach$2 implements Consumer {
    public final /* synthetic */ SecNotificationShadeWindowControllerHelperImpl this$0;

    public SecNotificationShadeWindowControllerHelperImpl$attach$2(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl) {
        this.this$0 = secNotificationShadeWindowControllerHelperImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Boolean bool = (Boolean) obj;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.this$0;
        bool.getClass();
        SecNotificationShadeWindowControllerHelperImpl.access$setScreenOrientation(secNotificationShadeWindowControllerHelperImpl, bool.booleanValue());
    }
}
