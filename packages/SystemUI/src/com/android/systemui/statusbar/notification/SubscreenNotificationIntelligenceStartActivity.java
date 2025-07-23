package com.android.systemui.statusbar.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubscreenNotificationIntelligenceStartActivity extends Activity implements CommandQueue.Callbacks {
    public static final String TAG;
    public final SubscreenNotificationController controller;
    public boolean needToShowFTU;
    private final SettingsHelper settingsHelper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = "S.S.N.SuggestedRepliesOnboarding";
    }

    public SubscreenNotificationIntelligenceStartActivity(SubscreenNotificationController subscreenNotificationController, SettingsHelper settingsHelper) {
        this.controller = subscreenNotificationController;
        this.settingsHelper = settingsHelper;
        Log.d(TAG, "SubscreenNotificationIntelligenceStartActivity()");
    }

    public final void checkWritingAssistFTU() {
        Log.d(TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("checkWritingAssistFTU() needToShow = ", this.needToShowFTU));
        if (this.needToShowFTU) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = this.controller.mDeviceModel;
            startActivity(subscreenDeviceModelParent != null ? subscreenDeviceModelParent.getWritingAssistFTUIntent() : null);
        }
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        SubscreenDeviceModelParent subscreenDeviceModelParent;
        super.onActivityResult(i, i2, intent);
        String m = ListImplementation$$ExternalSyntheticOutline0.m(i, i2, "onActivityResult() request: ", ", result: ");
        String str = TAG;
        Log.d(str, m);
        if (i != 10) {
            if (i == 20 && i2 == -1) {
                setAiInfoConfirmed();
                checkWritingAssistFTU();
            }
        } else if (i2 == -1 && (subscreenDeviceModelParent = this.controller.mDeviceModel) != null) {
            getBaseContext();
            if (!subscreenDeviceModelParent.isSamsungAccountLoggedIn()) {
                Log.d(str, "startSamsungAccountSignInPopup()");
                Intent intent2 = new Intent("com.msc.action.samsungaccount.SIGNIN_POPUP");
                intent2.setFlags(603979776);
                intent2.putExtra("client_id", "i5to7wq0er");
                startActivityForResult(intent2, 20);
                return;
            }
            setAiInfoConfirmed();
            checkWritingAssistFTU();
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.subscreen_notification_intelligence_start_activity);
        this.needToShowFTU = getIntent().getBooleanExtra("needToShowFTU", false);
        startActivityForResult(new Intent("com.samsung.android.settings.action.INTELLIGENCE_SERVICE_SETTINGS"), 10, null);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
    }

    public final void setAiInfoConfirmed() {
        Log.d(TAG, "set ai_info_confirmed to 1");
        this.settingsHelper.setAiInfoConfirmed(true);
    }
}
