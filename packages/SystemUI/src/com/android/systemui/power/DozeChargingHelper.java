package com.android.systemui.power;

import android.content.Context;
import android.hardware.display.IDisplayManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import com.android.systemui.LsRune;
import com.android.systemui.doze.PluginAODManager;
import com.samsung.android.hardware.display.IRefreshRateToken;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DozeChargingHelper {
    public IDisplayManager displayManager;
    public PowerManager.WakeLock dozeChargingDrawWakelock;
    public boolean isDozeChargingWorking;
    public final Lazy pluginAODManagerLazy;
    public final PowerManager powerManager;
    public IRefreshRateToken refreshRateToken;
    public final IBinder displayStateLock = new Binder();
    public final IBinder passiveModeLock = new Binder();

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

    public DozeChargingHelper(Context context, Lazy lazy) {
        this.pluginAODManagerLazy = lazy;
        this.powerManager = (PowerManager) context.getSystemService("power");
    }

    public final void handleDisplayStateWhenDozeCharging() {
        if (isDozeChargingCondition()) {
            Slog.i("PowerUI.DozeChargingHelper", "Lcd OFF -> so call chargingAnimStarted(true)");
            ((PluginAODManager) this.pluginAODManagerLazy.get()).chargingAnimStarted(true);
            if (LsRune.AOD_FULLSCREEN) {
                ((PluginAODManager) this.pluginAODManagerLazy.get()).updateRefreshRate(false);
            }
            synchronized (this) {
                try {
                    PowerManager powerManager = this.powerManager;
                    PowerManager.WakeLock newWakeLock = powerManager != null ? powerManager.newWakeLock(128, "PowerUI") : null;
                    this.dozeChargingDrawWakelock = newWakeLock;
                    if (newWakeLock != null) {
                        newWakeLock.acquire(4000L);
                    }
                } finally {
                }
            }
            IDisplayManager asInterface = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
            this.displayManager = asInterface;
            try {
                Slog.i("PowerUI.DozeChargingHelper", "setDisplayLimit(Display.STATE_ON), " + asInterface);
                IDisplayManager iDisplayManager = this.displayManager;
                if (iDisplayManager != null) {
                    iDisplayManager.setDisplayStateOverride(this.displayStateLock, 2, 4500);
                }
                IDisplayManager iDisplayManager2 = this.displayManager;
                this.refreshRateToken = iDisplayManager2 != null ? iDisplayManager2.acquirePassiveModeToken(this.passiveModeLock, "PowerUI") : null;
            } catch (RemoteException e) {
                Slog.e("PowerUI.DozeChargingHelper", "setDisplayLimit : RemoteException!! ERROR case", e);
            }
            this.isDozeChargingWorking = true;
        }
    }

    public final void handlePluginAodCharging(String str) {
        if (isDozeChargingCondition()) {
            Slog.w("PowerUI.DozeChargingHelper", str);
            Lazy lazy = this.pluginAODManagerLazy;
            ((PluginAODManager) lazy.get()).chargingAnimStarted(true);
            ((PluginAODManager) lazy.get()).chargingAnimStarted(false);
        }
    }

    public final boolean isDozeChargingCondition() {
        PowerManager powerManager;
        PowerManager powerManager2 = this.powerManager;
        return !(powerManager2 == null || powerManager2.isInteractive()) || ((powerManager = this.powerManager) != null && powerManager.isScreenCurtainEnabled());
    }

    public final void restoreDisplayStateWhenDozeCharging() {
        PowerManager.WakeLock wakeLock;
        Slog.d("PowerUI.DozeChargingHelper", "restoreDisplayStateWhenDozeCharging : " + this.isDozeChargingWorking);
        if (this.isDozeChargingWorking) {
            Slog.i("PowerUI.DozeChargingHelper", "isDozeChargingWorking is true -> so call chargingAnimStarted(false)");
            IDisplayManager asInterface = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
            this.displayManager = asInterface;
            try {
                Slog.i("PowerUI.DozeChargingHelper", "releaseDisplayStateLimit(Display.STATE_UNKNOWN), " + asInterface);
                IDisplayManager iDisplayManager = this.displayManager;
                if (iDisplayManager != null) {
                    iDisplayManager.setDisplayStateOverride(this.displayStateLock, 0, -1);
                }
                IRefreshRateToken iRefreshRateToken = this.refreshRateToken;
                if (iRefreshRateToken != null) {
                    iRefreshRateToken.release();
                }
                this.refreshRateToken = null;
            } catch (RemoteException e) {
                Slog.e("PowerUI.DozeChargingHelper", "releaseDisplayStateLimit : RemoteException!! ERROR case", e);
            }
            synchronized (this) {
                PowerManager.WakeLock wakeLock2 = this.dozeChargingDrawWakelock;
                if (wakeLock2 != null && wakeLock2.isHeld() && (wakeLock = this.dozeChargingDrawWakelock) != null) {
                    wakeLock.release();
                }
            }
            if (LsRune.AOD_FULLSCREEN) {
                ((PluginAODManager) this.pluginAODManagerLazy.get()).updateRefreshRate(true);
            }
            ((PluginAODManager) this.pluginAODManagerLazy.get()).chargingAnimStarted(false);
            this.isDozeChargingWorking = false;
        }
    }
}
