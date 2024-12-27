package com.android.server.sepunion;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.sepunion.cover.BaseNfcLedCoverController;
import com.android.server.sepunion.cover.CoverManagerServiceImpl;
import com.android.server.sepunion.cover.CoverServiceManager;
import com.android.server.sepunion.cover.CoverTestModeUtils;

import com.samsung.android.cover.CoverState;
import com.samsung.android.sepunion.IPluginManager;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemPluginManagerLocal;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class SemPluginManagerService extends IPluginManager.Stub
        implements AbsSemSystemService, AbsSemSystemServiceForS {
    public final Context mContext;
    public CoverManagerServiceImpl mCoverManagerServiceImpl;
    public final boolean mIsCoverSupport;

    public SemPluginManagerService(Context context) {
        Log.d("SemPluginManagerService", "SemPluginManagerService");
        this.mContext = context;
        this.mIsCoverSupport =
                context.getPackageManager().hasSystemFeature("com.sec.feature.cover");
        initialize();
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (strArr == null || strArr.length == 0 || !strArr[0].equals("cover")) {
            dumpCoverInfomation(fileDescriptor, printWriter, strArr);
        } else {
            dumpCoverInfomation(fileDescriptor, printWriter, strArr);
        }
    }

    public final void dumpCoverInfomation(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mIsCoverSupport) {
            printWriter.println(
                    "\n"
                        + "##### SEP COVER MANAGER SERVICE #####\n"
                        + "##### (dumpsys sepunion cover) #####\n");
            if (strArr == null || strArr.length == 0) {
                this.mCoverManagerServiceImpl.dump(fileDescriptor, printWriter, strArr);
                return;
            }
            if (strArr[0].equals("close")) {
                CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
                System.nanoTime();
                coverManagerServiceImpl.notifyCoverSwitchStateChanged(false);
                return;
            }
            if (strArr[0].equals("open")) {
                CoverManagerServiceImpl coverManagerServiceImpl2 = this.mCoverManagerServiceImpl;
                System.nanoTime();
                coverManagerServiceImpl2.notifyCoverSwitchStateChanged(true);
            } else if (strArr[0].equals("secure_on")) {
                if (((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                        .resetPassword("111111", 1)) {
                    return;
                }
                Log.d(
                        "SemPluginManagerService",
                        "dumpCoverInfomation: resetPassword(secure_on) failed.");
            } else if (!strArr[0].equals("secure_off")) {
                this.mCoverManagerServiceImpl.dump(fileDescriptor, printWriter, strArr);
            } else {
                if (((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                        .resetPassword("", 1)) {
                    return;
                }
                Log.d(
                        "SemPluginManagerService",
                        "dumpCoverInfomation: resetPassword(secure_off) failed.");
            }
        }
    }

    public final CoverManagerServiceImpl getCoverManagerServiceImpl() {
        return this.mCoverManagerServiceImpl;
    }

    public final CoverState getCoverState() {
        if (this.mIsCoverSupport) {
            return this.mCoverManagerServiceImpl.getCoverState();
        }
        return null;
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    public final void initialize() {
        if (this.mIsCoverSupport) {
            this.mCoverManagerServiceImpl = new CoverManagerServiceImpl(this.mContext);
        }
        if (((SemPluginManagerLocal) LocalServices.getService(SemPluginManagerLocal.class))
                == null) {
            LocalServices.addService(
                    SemPluginManagerLocal.class, new SemPluginLocalService(this.mContext));
        }
    }

    public final void notifyCoverSwitchStateChanged(long j, boolean z) {
        Log.d("SemPluginManagerService", "notifyCoverSwitchStateChanged");
        if (this.mIsCoverSupport) {
            this.mCoverManagerServiceImpl.notifyCoverSwitchStateChanged(z);
        }
    }

    public final void notifySmartCoverAttachStateChanged(long j, boolean z, CoverState coverState) {
        Log.d("SemPluginManagerService", "notifySmartCoverAttachStateChanged");
        if (this.mIsCoverSupport) {
            CoverManagerServiceImpl coverManagerServiceImpl = this.mCoverManagerServiceImpl;
            if (!coverManagerServiceImpl.mSystemReady) {
                Log.d(
                        "CoverManager_CoverManagerServiceImpl",
                        "notifySmartCoverAttachStateChanged : return because system is not yet"
                            + " ready");
                return;
            }
            if (!CoverTestModeUtils.isTestMode() && Binder.getCallingUid() != Process.myUid()) {
                throw new SecurityException("Caller is not SYSTEM_PROCESS");
            }
            if (coverState != null
                    && coverState.getType() != 9
                    && coverState.getType() != 10
                    && !CoverServiceManager.verifySystemFeature(
                            coverManagerServiceImpl.mContext, coverState.getType())) {
                Log.d(
                        "CoverManager_CoverManagerServiceImpl",
                        "notifySmartCoverAttachStateChanged : not supported cover type = "
                                + coverState.getType());
                Log.addLogString(
                        "CoverManager_",
                        "cover attach is failed - not supported cover type = "
                                + coverState.getType());
                return;
            }
            Log.d(
                    "CoverManager_CoverManagerServiceImpl",
                    "notifySmartCoverAttachStateChanged : attach = " + z);
            if (coverState != null) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "cover attach : ", ", cover type : ", z);
                m.append(coverState.getType());
                Log.addLogString("CoverManager_", m.toString());
            }
            BaseNfcLedCoverController baseNfcLedCoverController =
                    coverManagerServiceImpl.mNfcLedCoverController;
            if (baseNfcLedCoverController != null) {
                baseNfcLedCoverController.notifyAuthenticationResponse();
            }
            coverManagerServiceImpl.updateCoverAttachState(z, false, coverState);
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBootPhase(int r9) {
        /*
            r8 = this;
            r0 = 600(0x258, float:8.41E-43)
            r1 = 0
            r2 = 1
            if (r9 != r0) goto L8c
            boolean r9 = r8.mIsCoverSupport
            if (r9 == 0) goto Lbb
            com.android.server.sepunion.cover.CoverManagerServiceImpl r8 = r8.mCoverManagerServiceImpl
            boolean r9 = r8.mSystemReady
            if (r9 != 0) goto Lbb
            r8.mSystemReady = r2
            boolean r9 = com.android.server.sepunion.cover.CoverTestModeUtils.isTestMode()
            if (r9 == 0) goto L29
            android.os.Handler r9 = new android.os.Handler
            r9.<init>()
            com.android.server.sepunion.cover.CoverManagerServiceImpl$6 r0 = new com.android.server.sepunion.cover.CoverManagerServiceImpl$6
            r0.<init>()
            r1 = 5000(0x1388, double:2.4703E-320)
            r9.postDelayed(r0, r1)
            goto Lbb
        L29:
            com.android.server.sepunion.cover.AutoScreenOn r9 = r8.mAutoScreenOn
            boolean r0 = r9.support()
            java.lang.String r3 = "CoverManager_CoverManagerServiceImpl"
            if (r0 == 0) goto L86
            com.android.server.input.InputManagerService r0 = r8.mInputManagerService
            if (r0 != 0) goto L45
            java.lang.String r0 = "input"
            android.os.IBinder r0 = android.os.ServiceManager.getService(r0)
            android.hardware.input.IInputManager r0 = android.hardware.input.IInputManager.Stub.asInterface(r0)
            com.android.server.input.InputManagerService r0 = (com.android.server.input.InputManagerService) r0
            r8.mInputManagerService = r0
        L45:
            com.android.server.input.InputManagerService r0 = r8.mInputManagerService
            r4 = 0
            if (r0 == 0) goto L5f
            com.android.server.input.NativeInputManagerService$NativeImpl r0 = r0.mNative     // Catch: java.lang.Exception -> L59
            r5 = -256(0xffffffffffffff00, float:NaN)
            r6 = 26
            r7 = -1
            int r0 = r0.getSwitchState(r7, r5, r6)     // Catch: java.lang.Exception -> L59
            if (r0 <= 0) goto L64
            r0 = r2
            goto L65
        L59:
            java.lang.String r0 = "getCoverAttachStateFromInputManager : Can't get cover attach state"
            com.samsung.android.sepunion.Log.e(r3, r0)
            goto L64
        L5f:
            java.lang.String r0 = "getCoverAttachStateFromInputManager : InputManager is null"
            com.samsung.android.sepunion.Log.d(r3, r0)
        L64:
            r0 = r4
        L65:
            r8.updateCoverAttachState(r0, r2, r1)
            int r0 = r8.getCoverSwitchStateFromInputManager()
            if (r0 == r2) goto L6f
            r4 = r2
        L6f:
            com.android.server.sepunion.cover.CoverDisabler r0 = r8.mCoverDisabler
            boolean r0 = r0.mCoverManagerDisabled
            if (r0 != 0) goto L81
            boolean r0 = android.os.FactoryTest.isFactoryBinary()
            if (r0 != 0) goto L81
            boolean r9 = r9.off()
            if (r9 == 0) goto L82
        L81:
            r4 = r2
        L82:
            r8.updateCoverSwitchState(r4, r2)
            goto Lbb
        L86:
            java.lang.String r8 = "Nfc authentication supported, skipping creating first coverState"
            com.samsung.android.sepunion.Log.d(r3, r8)
            goto Lbb
        L8c:
            r0 = 1000(0x3e8, float:1.401E-42)
            if (r9 != r0) goto Lbb
            boolean r9 = r8.mIsCoverSupport
            if (r9 == 0) goto Lbb
            com.android.server.sepunion.cover.CoverManagerServiceImpl r8 = r8.mCoverManagerServiceImpl
            com.android.server.sepunion.cover.StateNotifier r9 = r8.mStateNotifier
            boolean r0 = r9.mBootComplete
            if (r0 != 0) goto Laf
            java.lang.String r0 = "CoverManager_StateNotifier"
            java.lang.String r3 = "onBootComplete"
            com.samsung.android.sepunion.Log.w(r0, r3)
            r9.mBootComplete = r2
            com.android.server.sepunion.cover.StateNotifier$$ExternalSyntheticLambda0 r0 = r9.mGoToSleepRunnable
            if (r0 == 0) goto Laf
            r0.run()
            r9.mGoToSleepRunnable = r1
        Laf:
            android.content.Context r9 = r8.mContext
            java.lang.String r0 = "SemInputDeviceManagerService"
            java.lang.Object r9 = r9.getSystemService(r0)
            com.samsung.android.hardware.secinputdev.SemInputDeviceManager r9 = (com.samsung.android.hardware.secinputdev.SemInputDeviceManager) r9
            r8.mInputDeviceManager = r9
        Lbb:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.sepunion.SemPluginManagerService.onBootPhase(int):void");
    }

    public final void onCleanupUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {}

    public final void onDestroy() {}

    public final void onStart() {}

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {
        if (this.mIsCoverSupport) {
            this.mCoverManagerServiceImpl.onSwitchUser(i);
        }
    }

    public final void onUnlockUser(int i) {
        if (this.mIsCoverSupport) {
            this.mCoverManagerServiceImpl.onUserUnlocked(i);
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserStarting(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserStopped(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserStopping(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserSwitching(int i, int i2) {
        if (this.mIsCoverSupport) {
            this.mCoverManagerServiceImpl.onSwitchUser(i2);
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserUnlocked(int i) {
        if (this.mIsCoverSupport) {
            this.mCoverManagerServiceImpl.onUserUnlocked(i);
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemServiceForS
    public final void onUserUnlocking(int i) {}
}
