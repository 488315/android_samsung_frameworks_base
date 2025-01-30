package com.android.systemui.screenshot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotCrossProfileService extends Service {
    public final ScreenshotCrossProfileService$mBinder$1 mBinder = new ScreenshotCrossProfileService$mBinder$1(this);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
