package com.android.server.policy;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Slog;
import android.view.KeyEvent;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.wm.WmCoverState;

import com.samsung.android.cover.CoverState;
import com.samsung.android.rune.InputRune;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SideKeyDoublePress {
    public static Behavior mBehavior;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Behavior {
        public int mAction = 0;
        public PhoneWindowManagerExt mPolicyExt;
        public final String mTargetAppName;

        public Behavior(String str) {
            this.mTargetAppName = str;
        }

        public boolean doublePressLaunchPolicy(boolean z) {
            return this.mPolicyExt.doublePressLaunchPolicy(26);
        }

        public int getAction() {
            return this.mAction;
        }

        public Intent getIntent() {
            return new Intent("android.intent.action.MAIN")
                    .addCategory("android.intent.category.LAUNCHER")
                    .addFlags(270532608)
                    .setComponent(ComponentName.unflattenFromString(this.mTargetAppName));
        }

        public boolean preCondition(Intent intent, boolean z) {
            if (doublePressLaunchPolicy(z)) {
                return true;
            }
            PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
            phoneWindowManagerExt.getClass();
            if (TextUtils.isEmpty(null)) {
                return false;
            }
            phoneWindowManagerExt.showToast(phoneWindowManagerExt.mContext, null);
            return true;
        }

        public void setAction(int i) {
            this.mAction = i;
        }

        public boolean showCoverToast(Intent intent) {
            if (!WmCoverState.sIsEnabled) {
                return false;
            }
            WmCoverState wmCoverState = WmCoverState.getInstance();
            if (!(!((CoverState) wmCoverState).switchState)) {
                return false;
            }
            switch (((CoverState) wmCoverState).type) {
                case 15:
                case 16:
                case 17:
                    intent.putExtra("showCoverToast", true);
                    Slog.d("SideKeyDoublePress", "neededShowCoverToast for cover");
                    return true;
                default:
                    return false;
            }
        }

        public abstract void startTargetApp(
                KeyEvent keyEvent, boolean z, boolean z2, Intent intent, Intent intent2);

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(256, "Behavior=");
            m.append(getClass().getSimpleName());
            m.append(" keyCode=26 appName=");
            m.append(this.mTargetAppName);
            return m.toString();
        }

        public void updateTargetComponent(Intent intent) {}
    }

    public static Behavior getBehavior(String str) {
        PhoneWindowManagerExt.OpeningBixby openingBixby;
        boolean equals;
        int i = 5;
        int i2 = 4;
        int i3 = 3;
        int i4 = 2;
        int i5 = 1;
        int i6 = 0;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Behavior behavior = mBehavior;
        if (behavior != null) {
            behavior.getClass();
            if (TextUtils.isEmpty(str)) {
                Slog.d("SideKeyDoublePress", "appName is empty.");
                equals = false;
            } else {
                equals = str.equals(behavior.mTargetAppName);
            }
            if (equals) {
                return mBehavior;
            }
        }
        str.getClass();
        switch (str) {
            case "com.samsung.tvmode/com.samsung.tvmode.activity.MainActivity":
                openingBixby =
                        InputRune.PWM_POWER_KEY_DOUBLE_PRESS_ATT_TV_MODE
                                ? new PhoneWindowManagerExt.OpeningBixby(str, i5)
                                : null;
                mBehavior = openingBixby;
                return openingBixby;
            case "samsungpay://simplepay/sidekey":
                PhoneWindowManagerExt.OpeningBixby openingBixby2 =
                        new PhoneWindowManagerExt.OpeningBixby(str, i3);
                mBehavior = openingBixby2;
                return openingBixby2;
            case "com.sec.android.app.camera/com.sec.android.app.camera.Camera":
                PhoneWindowManagerExt.OpeningBixby openingBixby3 =
                        new PhoneWindowManagerExt.OpeningBixby(str, i4);
                mBehavior = openingBixby3;
                return openingBixby3;
            case "wakeBixby_openApps/wakeBixby_openApps":
                openingBixby =
                        InputRune.PWM_SIDE_KEY_WAKE_UP_BIXBY
                                ? new PhoneWindowManagerExt.OpeningBixby(str, i6)
                                : null;
                mBehavior = openingBixby;
                return openingBixby;
            case "torch/torch":
                openingBixby =
                        InputRune.PWM_SIDE_KEY_TORCH
                                ? new PhoneWindowManagerExt.OpeningBixby(str, i)
                                : null;
                mBehavior = openingBixby;
                return openingBixby;
            case "secureFolder/secureFolder":
                openingBixby =
                        InputRune.PWM_SIDE_KEY_DOUBLE_PRESS_SECURE_FOLDER
                                ? new PhoneWindowManagerExt.OpeningBixby(str, i2)
                                : null;
                mBehavior = openingBixby;
                return openingBixby;
            default:
                PhoneWindowManagerExt.OpeningApps openingApps =
                        new PhoneWindowManagerExt.OpeningApps(str);
                openingApps.mAction = 1;
                mBehavior = openingApps;
                return openingApps;
        }
    }

    public static void launch(
            PhoneWindowManagerExt phoneWindowManagerExt, KeyEvent keyEvent, boolean z) {
        String uri;
        Intent fillInIntent;
        SemWindowManager.KeyCustomizationInfo last =
                phoneWindowManagerExt.mKeyCustomizationPolicy.mKeyCustomizationInfoManager.getLast(
                        8, 26);
        if (last == null || last.getIntent() == null) {
            return;
        }
        Intent intent = (Intent) last.getIntent().clone();
        ComponentName component = intent.getComponent();
        if (component != null) {
            uri = component.flattenToString();
        } else {
            Uri data = intent.getData();
            uri = data != null ? data.toString() : null;
        }
        Behavior behavior = getBehavior(uri);
        if (behavior == null) {
            return;
        }
        if (behavior instanceof PhoneWindowManagerExt.OpeningApps) {
            behavior.setAction(last.action);
        }
        behavior.mPolicyExt = phoneWindowManagerExt;
        if (behavior.preCondition(intent, z)) {
            return;
        }
        behavior.updateTargetComponent(intent);
        if (intent.getExtras() == null
                || !intent.getExtras().getBoolean("show_on_keyguard", false)) {
            fillInIntent = PhoneWindowManagerExt.getFillInIntent();
        } else {
            fillInIntent = new Intent();
            fillInIntent.putExtra("ignoreKeyguardState", true);
            fillInIntent.putExtra("ignoreUnlock", true);
        }
        Intent intent2 = fillInIntent;
        boolean showCoverToast = behavior.showCoverToast(intent2);
        Slog.d("SideKeyDoublePress", "launch, showCoverToast=" + showCoverToast + " " + behavior);
        behavior.startTargetApp(keyEvent, z, showCoverToast, intent, intent2);
    }
}
