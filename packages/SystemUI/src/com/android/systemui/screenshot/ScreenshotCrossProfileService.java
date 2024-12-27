package com.android.systemui.screenshot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenshotCrossProfileService extends Service {
    public final ScreenshotCrossProfileService$mBinder$1 mBinder = new ScreenshotCrossProfileService$mBinder$1(this);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        Log.d("ScreenshotProxyService", "onBind: " + intent);
        return this.mBinder;
    }
}
