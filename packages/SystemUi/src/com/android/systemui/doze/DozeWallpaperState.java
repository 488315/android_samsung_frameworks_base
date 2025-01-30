package com.android.systemui.doze;

import android.app.IWallpaperManager;
import android.os.RemoteException;
import android.util.Log;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.DozeParameters;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeWallpaperState implements DozeMachine.Part {
    public static final boolean DEBUG = Log.isLoggable("DozeWallpaperState", 3);
    public final BiometricUnlockController mBiometricUnlockController;
    public final DozeParameters mDozeParameters;
    public boolean mIsAmbientMode;
    public final IWallpaperManager mWallpaperManagerService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.doze.DozeWallpaperState$1 */
    public abstract /* synthetic */ class AbstractC12541 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING_BRIGHT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public DozeWallpaperState(IWallpaperManager iWallpaperManager, BiometricUnlockController biometricUnlockController, DozeParameters dozeParameters) {
        this.mWallpaperManagerService = iWallpaperManager;
        this.mBiometricUnlockController = biometricUnlockController;
        this.mDozeParameters = dozeParameters;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void dump(PrintWriter printWriter) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "DozeWallpaperState:", " isAmbientMode: "), this.mIsAmbientMode, printWriter, " hasWallpaperService: "), this.mWallpaperManagerService != null, printWriter);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x003d, code lost:
    
        if ((r9.isWakeAndUnlock() || r9.mFadedAwayAfterWakeAndUnlock) != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0041, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0042, code lost:
    
        r8 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x003f, code lost:
    
        if (r8 != false) goto L26;
     */
    @Override // com.android.systemui.doze.DozeMachine.Part
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        switch (AbstractC12541.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        DozeParameters dozeParameters = this.mDozeParameters;
        if (z) {
            z2 = dozeParameters.mControlScreenOffAnimation;
        } else {
            boolean z4 = state == DozeMachine.State.DOZE_PULSING && state2 == DozeMachine.State.FINISH;
            if (!dozeParameters.getDisplayNeedsBlanking()) {
                BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
            }
        }
        if (z != this.mIsAmbientMode) {
            this.mIsAmbientMode = z;
            IWallpaperManager iWallpaperManager = this.mWallpaperManagerService;
            if (iWallpaperManager != null) {
                long j = z2 ? 500L : 0L;
                try {
                    if (DEBUG) {
                        Log.i("DozeWallpaperState", "AOD wallpaper state changed to: " + this.mIsAmbientMode + ", animationDuration: " + j);
                    }
                    iWallpaperManager.setInAmbientMode(this.mIsAmbientMode, j);
                } catch (RemoteException unused) {
                    Log.w("DozeWallpaperState", "Cannot notify state to WallpaperManagerService: " + this.mIsAmbientMode);
                }
            }
        }
    }
}
