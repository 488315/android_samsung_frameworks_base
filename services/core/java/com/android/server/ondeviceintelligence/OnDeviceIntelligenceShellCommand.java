package com.android.server.ondeviceintelligence;

import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.ShellCommand;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class OnDeviceIntelligenceShellCommand extends ShellCommand {
    public final OnDeviceIntelligenceManagerService mService;

    public OnDeviceIntelligenceShellCommand(OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService) {
        this.mService = onDeviceIntelligenceManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int onCommand(String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -2091542783:
                if (str.equals("set-model-broadcasts")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -365435659:
                if (str.equals("get-services")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -108354651:
                if (str.equals("set-temporary-services")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1159014577:
                if (str.equals("set-deviceconfig-namespace")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                PrintWriter outPrintWriter = getOutPrintWriter();
                String nextArgRequired = getNextArgRequired();
                String nextArgRequired2 = getNextArgRequired();
                String nextArg = getNextArg();
                int parseInt = Integer.parseInt(getNextArgRequired());
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService = this.mService;
                String[] strArr = {nextArgRequired, nextArgRequired2};
                onDeviceIntelligenceManagerService.getClass();
                OnDeviceIntelligenceManagerService.enforceShellOnly(Binder.getCallingUid(), "setModelBroadcastKeys");
                onDeviceIntelligenceManagerService.mContext.enforceCallingPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", "OnDeviceIntelligenceManagerService");
                synchronized (onDeviceIntelligenceManagerService.mLock) {
                    try {
                        onDeviceIntelligenceManagerService.mTemporaryBroadcastKeys = strArr;
                        onDeviceIntelligenceManagerService.mBroadcastPackageName = nextArg;
                        if (parseInt != -1) {
                            onDeviceIntelligenceManagerService.getTemporaryHandler().sendEmptyMessageDelayed(1, parseInt);
                        }
                    } finally {
                    }
                }
                outPrintWriter.println(ActiveServices$$ExternalSyntheticOutline0.m(parseInt, nextArg, " for ", "ms", InitialConfiguration$$ExternalSyntheticOutline0.m("OnDeviceIntelligence Model Loading broadcast keys temporarily set to ", nextArgRequired, " \n and \n OnDeviceTrustedInferenceService set to ", nextArgRequired2, "\n and Package name set to : ")));
                return 0;
            case 1:
                PrintWriter outPrintWriter2 = getOutPrintWriter();
                String[] serviceNames = this.mService.getServiceNames();
                StringBuilder sb = new StringBuilder("OnDeviceIntelligenceService set to :  ");
                sb.append(serviceNames[0]);
                sb.append(" \n and \n OnDeviceTrustedInferenceService set to : ");
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, serviceNames[1], outPrintWriter2);
                return 0;
            case 2:
                PrintWriter outPrintWriter3 = getOutPrintWriter();
                String nextArg2 = getNextArg();
                String nextArg3 = getNextArg();
                if (getRemainingArgsCount() == 0 && nextArg2 == null && nextArg3 == null) {
                    OnDeviceIntelligenceManagerService.enforceShellOnly(Binder.getCallingUid(), "resetTemporaryServices");
                    this.mService.resetTemporaryServices();
                    outPrintWriter3.println("OnDeviceIntelligenceManagerService temporary reset. ");
                } else {
                    Objects.requireNonNull(nextArg2);
                    Objects.requireNonNull(nextArg3);
                    int parseInt2 = Integer.parseInt(getNextArgRequired());
                    OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService2 = this.mService;
                    String[] strArr2 = {nextArg2, nextArg3};
                    onDeviceIntelligenceManagerService2.getClass();
                    OnDeviceIntelligenceManagerService.enforceShellOnly(Binder.getCallingUid(), "setTemporaryServices");
                    onDeviceIntelligenceManagerService2.mContext.enforceCallingPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", "OnDeviceIntelligenceManagerService");
                    synchronized (onDeviceIntelligenceManagerService2.mLock) {
                        try {
                            onDeviceIntelligenceManagerService2.mTemporaryServiceNames = strArr2;
                            RemoteOnDeviceSandboxedInferenceService remoteOnDeviceSandboxedInferenceService = onDeviceIntelligenceManagerService2.mRemoteInferenceService;
                            if (remoteOnDeviceSandboxedInferenceService != null) {
                                remoteOnDeviceSandboxedInferenceService.unbind();
                                onDeviceIntelligenceManagerService2.mRemoteInferenceService = null;
                            }
                            RemoteOnDeviceIntelligenceService remoteOnDeviceIntelligenceService = onDeviceIntelligenceManagerService2.mRemoteOnDeviceIntelligenceService;
                            if (remoteOnDeviceIntelligenceService != null) {
                                remoteOnDeviceIntelligenceService.unbind();
                                onDeviceIntelligenceManagerService2.mRemoteOnDeviceIntelligenceService = null;
                            }
                            if (parseInt2 != -1) {
                                onDeviceIntelligenceManagerService2.getTemporaryHandler().sendEmptyMessageDelayed(0, parseInt2);
                            }
                        } finally {
                        }
                    }
                    StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("OnDeviceIntelligenceService temporarily set to ", nextArg2, " \n and \n OnDeviceTrustedInferenceService set to ", nextArg3, " for ");
                    m.append(parseInt2);
                    m.append("ms");
                    outPrintWriter3.println(m.toString());
                }
                return 0;
            case 3:
                PrintWriter outPrintWriter4 = getOutPrintWriter();
                String nextArg4 = getNextArg();
                int parseInt3 = Integer.parseInt(getNextArgRequired());
                OnDeviceIntelligenceManagerService onDeviceIntelligenceManagerService3 = this.mService;
                onDeviceIntelligenceManagerService3.getClass();
                Objects.requireNonNull(nextArg4);
                OnDeviceIntelligenceManagerService.enforceShellOnly(Binder.getCallingUid(), "setTemporaryDeviceConfigNamespace");
                onDeviceIntelligenceManagerService3.mContext.enforceCallingPermission("android.permission.USE_ON_DEVICE_INTELLIGENCE", "OnDeviceIntelligenceManagerService");
                synchronized (onDeviceIntelligenceManagerService3.mLock) {
                    try {
                        onDeviceIntelligenceManagerService3.mTemporaryConfigNamespace = nextArg4;
                        if (parseInt3 != -1) {
                            onDeviceIntelligenceManagerService3.getTemporaryHandler().sendEmptyMessageDelayed(2, parseInt3);
                        }
                    } finally {
                    }
                }
                outPrintWriter4.println(AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(parseInt3, "OnDeviceIntelligence DeviceConfig Namespace temporarily set to ", nextArg4, " for ", "ms"));
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("OnDeviceIntelligenceShellCommand commands: ");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  set-temporary-services [IntelligenceServiceComponentName] [InferenceServiceComponentName] [DURATION]");
        BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Temporarily (for DURATION ms) changes the service implementations.", "    To reset, call without any arguments.", "  get-services To get the names of services that are currently being used.", "  set-model-broadcasts [ModelLoadedBroadcastKey] [ModelUnloadedBroadcastKey] [ReceiverPackageName] [DURATION] To set the names of broadcast intent keys that are to be emitted for cts tests.");
        outPrintWriter.println("  set-deviceconfig-namespace [DeviceConfigNamespace] [DURATION] To set the device config namespace to use for cts tests.");
    }
}
