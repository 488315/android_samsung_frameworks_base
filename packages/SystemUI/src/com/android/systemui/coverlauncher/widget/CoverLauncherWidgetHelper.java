package com.android.systemui.coverlauncher.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class CoverLauncherWidgetHelper extends Activity {
    public final Lazy mGoToLabsIntent$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.coverlauncher.widget.CoverLauncherWidgetHelper$mGoToLabsIntent$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new Intent();
        }
    });

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

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((Intent) this.mGoToLabsIntent$delegate.getValue()).setAction("com.samsung.settings.APPS_ALLOWED_COVER_SCREEN");
        ((Intent) this.mGoToLabsIntent$delegate.getValue()).setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$AppAllowedCoverScreenSettingsActivity");
        ((Intent) this.mGoToLabsIntent$delegate.getValue()).setFlags(872415232);
        synchronized (this) {
            try {
                try {
                    Intent intent = getIntent();
                    if (intent != null && "android.appwidget.action.APPWIDGET_CONFIGURE".equals(intent.getAction())) {
                        setResult(-1);
                    }
                    startActivity((Intent) this.mGoToLabsIntent$delegate.getValue());
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Fail to go to Labs! error=" + e, 1).show();
                    e.printStackTrace();
                    finish();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
