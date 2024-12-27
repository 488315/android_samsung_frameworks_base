package com.android.server.policy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.samsung.android.rune.InputRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BixbyService {
    public final Context mContext;
    public final ComponentName mDefaultComponentName;
    public final ComponentName mOnboardingComponentName;
    public final PhoneWindowManagerExt mPolicyExt;
    public final PowerManager.WakeLock mWakeLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Params {
        public boolean doublePress;
        public final KeyEvent event;
        public final boolean interactive;
        public boolean isPowerCombination;
        public boolean longPress;
        public boolean showToast;

        public Params(KeyEvent keyEvent, boolean z) {
            this.showToast = false;
            this.longPress = false;
            this.doublePress = false;
            this.isPowerCombination = false;
            this.event = keyEvent;
            this.interactive = z;
        }

        public Params(Params params) {
            KeyEvent keyEvent = params.event;
            boolean z = params.showToast;
            boolean z2 = params.longPress;
            boolean z3 = params.doublePress;
            boolean z4 = params.isPowerCombination;
            this.event = keyEvent;
            this.interactive = params.interactive;
            this.showToast = z;
            this.longPress = z2;
            this.doublePress = z3;
            this.isPowerCombination = z4;
        }
    }

    public BixbyService(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
        this.mPolicyExt = phoneWindowManagerExt;
        this.mContext = context;
        this.mDefaultComponentName = !TextUtils.isEmpty("com.samsung.android.bixby.agent/com.samsung.android.bixby.WinkService") ? ComponentName.unflattenFromString("com.samsung.android.bixby.agent/com.samsung.android.bixby.WinkService") : null;
        this.mOnboardingComponentName = TextUtils.isEmpty("com.samsung.android.bixby.agent/com.samsung.android.bixby.BixbyKeyLService") ? null : ComponentName.unflattenFromString("com.samsung.android.bixby.agent/com.samsung.android.bixby.BixbyKeyLService");
        this.mWakeLock = phoneWindowManagerExt.mPolicy.mPowerManager.newWakeLock(1, "PhoneWindowManager.BixbyService.WakeLock");
    }

    public final Intent getIntent(Params params) {
        KeyEvent keyEvent = params.event;
        Intent intent = new Intent();
        boolean z = InputRune.PWM_SUPPORT_BIXBY_ONBOARDING_SERVICE;
        boolean z2 = params.longPress;
        boolean z3 = params.doublePress;
        if (z && (z2 || z3)) {
            intent.setComponent(this.mOnboardingComponentName);
        } else {
            intent.setComponent(this.mDefaultComponentName);
        }
        if (keyEvent != null) {
            intent.putExtra("KEYEVENT", KeyEvent.obtain(keyEvent));
        }
        boolean z4 = params.isPowerCombination;
        if (z4) {
            intent.putExtra("KEY_COMBINATION", true);
        }
        intent.putExtra("BIXBY_KEY_FW_VERSION", 2);
        boolean z5 = params.interactive;
        intent.putExtra("INTERACTIVE", z5);
        int i = (InputRune.PWM_SIDE_KEY_WAKE_UP_BIXBY && z2) ? 1 : (InputRune.PWM_SIDE_KEY_DOUBLE_PRESS && z3) ? 2 : -1;
        intent.putExtra("RESULT_BY_POWER", i);
        Log.d("BixbyService", "startBixbyService, keyPressType=" + i + " interactive=" + z5 + " longPress=" + z2 + " doublePress=" + z3 + " isUnlockFP=false isPowerCombination=" + z4);
        return intent;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [android.os.PowerManager$WakeLock] */
    public final void startService(Params params) {
        boolean z = params.showToast;
        PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
        String str = null;
        if (z && !TextUtils.isEmpty(null) && params.interactive) {
            phoneWindowManagerExt.showToast(this.mContext, null);
        }
        if (InputRune.PWM_KEY_FACTORY_MODE_POLICY) {
            Log.d("BixbyService", "Do nothing regarding key event of bixby service. Factory Binary");
            return;
        }
        if (!phoneWindowManagerExt.mPolicy.isUserSetupComplete()) {
            str = "UserSetup is not completed";
        } else if (this.mDefaultComponentName == null) {
            str = "bixbyComponentName is null";
        } else if (phoneWindowManagerExt.isCarrierLocked()) {
            str = "Carrier is Locked";
        } else if (phoneWindowManagerExt.mPolicy.mKeyguardDelegate.isSimLocked()) {
            str = "Sim is Locked";
        } else if ("true".equals(SystemProperties.get("ril.domesticOtaStart"))) {
            str = "DomesticOtaStart";
        }
        if (!TextUtils.isEmpty(str)) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("Do nothing regarding key event of bixby service. reason=", str, "BixbyService");
            return;
        }
        this.mWakeLock.acquire();
        try {
            try {
                this.mContext.startServiceAsUser(getIntent(params), UserHandle.SYSTEM);
            } catch (Exception e) {
                Log.e("BixbyService", "Can not start BixbyService: " + e);
            }
        } finally {
            this.mWakeLock.release();
        }
    }
}
