package com.android.systemui.volume.util;

import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.plugins.ActivityStarter;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import dagger.Lazy;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarWrapper {
    public final Context context;
    public final KeyguardManagerWrapper keyguardManagerWrapper;
    public final LogWrapper logWrapper;

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
    }

    public StatusBarWrapper(Context context, LogWrapper logWrapper, KeyguardManagerWrapper keyguardManagerWrapper, Lazy lazy) {
        this.context = context;
        this.logWrapper = logWrapper;
        this.keyguardManagerWrapper = keyguardManagerWrapper;
    }

    public final void startDoNotDisturbActivity() {
        Object failure;
        Intent intent = new Intent("android.settings.ZEN_MODE_SETTINGS");
        intent.addFlags(335544320);
        try {
            int i = Result.$r8$clinit;
            this.context.startActivityAsUser(intent, UserHandle.CURRENT);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            this.logWrapper.e("StatusBarWrapper", "startDoNotDisturbActivity : Exception = " + m3422exceptionOrNullimpl);
        }
    }

    public final void startHearingEnhancementsActivity() {
        Object failure;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$AccessibilityHearingEnhancementsActivity"));
        intent.addFlags(335544320);
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", "all_sound_off_key");
        Unit unit = Unit.INSTANCE;
        intent.putExtra(":settings:show_fragment_args", bundle);
        try {
            int i = Result.$r8$clinit;
            this.context.startActivityAsUser(intent, UserHandle.CURRENT);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            this.logWrapper.e("StatusBarWrapper", "startHearingEnhancementsActivity : Exception = " + m3422exceptionOrNullimpl);
        }
    }

    public final void startLeBroadcastActivity() {
        Object failure;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecBluetoothLeBroadcastSourceActivity"));
        intent.addFlags(335544320);
        try {
            int i = Result.$r8$clinit;
            this.context.startActivityAsUser(intent, UserHandle.CURRENT);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            this.logWrapper.e("StatusBarWrapper", "startLeBroadcastActivity : Exception = " + m3422exceptionOrNullimpl);
        }
    }

    public final void startSettingsActivity() {
        Object failure;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecVolumeLimiterSettingsActivity"));
        intent.addFlags(335544320);
        try {
            int i = Result.$r8$clinit;
            ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).startActivity(intent, true);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            this.logWrapper.e("StatusBarWrapper", "startSettingsActivity : Exception = " + m3422exceptionOrNullimpl);
        }
    }

    public final void startVolumeSettingsActivity() {
        Object failure;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$SecVolumeSettingsActivity"));
        intent.addFlags(335544320);
        try {
            int i = Result.$r8$clinit;
            KeyguardManagerWrapper keyguardManagerWrapper = this.keyguardManagerWrapper;
            keyguardManagerWrapper.getClass();
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context = keyguardManagerWrapper.context;
            systemServiceExtension.getClass();
            Object systemService = context.getSystemService((Class<Object>) KeyguardManager.class);
            systemService.getClass();
            if (((KeyguardManager) systemService).isKeyguardLocked()) {
                ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).startActivity(intent, false);
            } else {
                this.context.startActivityAsUser(intent, UserHandle.CURRENT);
            }
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            this.logWrapper.e("StatusBarWrapper", "startVolumeSettingsActivity : Exception = " + m3422exceptionOrNullimpl);
        }
    }
}
