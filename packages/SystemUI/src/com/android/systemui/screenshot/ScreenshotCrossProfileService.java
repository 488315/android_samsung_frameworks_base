package com.android.systemui.screenshot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ScreenshotCrossProfileService extends Service {
    public final ScreenshotCrossProfileService$mBinder$1 mBinder = new ScreenshotCrossProfileService$mBinder$1(this);

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
