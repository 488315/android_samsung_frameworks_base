package com.android.systemui.shade;

import java.util.function.Consumer;

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
