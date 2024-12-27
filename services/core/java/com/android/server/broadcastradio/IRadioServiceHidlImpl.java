package com.android.server.broadcastradio;

import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.hardware.radio.IRadioService;
import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.broadcastradio.hal2.AnnouncementAggregator;
import com.android.server.broadcastradio.hal2.BroadcastRadioService$$ExternalSyntheticLambda0;
import com.android.server.broadcastradio.hal2.RadioModule;
import com.android.server.utils.Slogf;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IRadioServiceHidlImpl extends IRadioService.Stub {
    public final com.android.server.broadcastradio.hal1.BroadcastRadioService mHal1Client;
    public final com.android.server.broadcastradio.hal2.BroadcastRadioService mHal2Client;
    public final Object mLock = new Object();
    public final BroadcastRadioService mService;
    public final List mV1Modules;

    public IRadioServiceHidlImpl(BroadcastRadioService broadcastRadioService) {
        Objects.requireNonNull(broadcastRadioService, "broadcast radio service cannot be null");
        this.mService = broadcastRadioService;
        com.android.server.broadcastradio.hal1.BroadcastRadioService broadcastRadioService2 =
                new com.android.server.broadcastradio.hal1.BroadcastRadioService();
        this.mHal1Client = broadcastRadioService2;
        List loadModules = broadcastRadioService2.loadModules();
        this.mV1Modules = loadModules;
        OptionalInt max =
                loadModules.stream()
                        .mapToInt(new IRadioServiceHidlImpl$$ExternalSyntheticLambda0())
                        .max();
        this.mHal2Client =
                new com.android.server.broadcastradio.hal2.BroadcastRadioService(
                        max.isPresent() ? max.getAsInt() + 1 : 0);
    }

    public IRadioServiceHidlImpl(
            BroadcastRadioService broadcastRadioService,
            com.android.server.broadcastradio.hal1.BroadcastRadioService broadcastRadioService2,
            com.android.server.broadcastradio.hal2.BroadcastRadioService broadcastRadioService3) {
        Objects.requireNonNull(broadcastRadioService, "Broadcast radio service cannot be null");
        this.mService = broadcastRadioService;
        Objects.requireNonNull(
                broadcastRadioService2,
                "Broadcast radio service implementation for HIDL 1 HAL cannot be null");
        this.mHal1Client = broadcastRadioService2;
        this.mV1Modules = broadcastRadioService2.loadModules();
        Objects.requireNonNull(
                broadcastRadioService3,
                "Broadcast radio service implementation for HIDL 2 HAL cannot be null");
        this.mHal2Client = broadcastRadioService3;
    }

    public final ICloseHandle addAnnouncementListener(
            int[] iArr, IAnnouncementListener iAnnouncementListener) {
        boolean z;
        if (Log.isLoggable("BcRadioSrvHidl", 3)) {
            Slog.d("BcRadioSrvHidl", "Adding announcement listener for " + Arrays.toString(iArr));
        }
        Objects.requireNonNull(iArr, "Enabled announcement types cannot be null");
        Objects.requireNonNull(iAnnouncementListener, "Announcement listener cannot be null");
        this.mService.enforcePolicyAccess();
        synchronized (this.mLock) {
            try {
                com.android.server.broadcastradio.hal2.BroadcastRadioService broadcastRadioService =
                        this.mHal2Client;
                synchronized (broadcastRadioService.mLock) {
                    z = !((ArrayMap) broadcastRadioService.mModules).isEmpty();
                }
                if (z) {
                    return this.mHal2Client.addAnnouncementListener(iArr, iAnnouncementListener);
                }
                Slog.w("BcRadioSrvHidl", "There are no HAL 2.0 modules registered");
                return new AnnouncementAggregator(iAnnouncementListener, this.mLock);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mService.getContext().checkCallingOrSelfPermission("android.permission.DUMP")
                != 0) {
            printWriter.println(
                    "Permission Denial: can't dump HIDL BroadcastRadioService from from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid()
                            + " without permission android.permission.DUMP");
            return;
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.printf("BroadcastRadioService\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("HAL1 client: %s\n", new Object[] {this.mHal1Client});
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            indentingPrintWriter.printf(
                    "Modules of HAL1 client: %s\n", new Object[] {this.mV1Modules});
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.printf("HAL2 client:\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        com.android.server.broadcastradio.hal2.BroadcastRadioService broadcastRadioService =
                this.mHal2Client;
        synchronized (broadcastRadioService.mLock) {
            try {
                indentingPrintWriter.printf(
                        "Next module id available: %d\n",
                        new Object[] {Integer.valueOf(broadcastRadioService.mNextModuleId)});
                indentingPrintWriter.printf("ServiceName to module id map:\n", new Object[0]);
                indentingPrintWriter.increaseIndent();
                for (Map.Entry entry :
                        ((ArrayMap) broadcastRadioService.mServiceNameToModuleIdMap).entrySet()) {
                    indentingPrintWriter.printf(
                            "Service name: %s, module id: %d\n",
                            new Object[] {entry.getKey(), entry.getValue()});
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.printf("Radio modules:\n", new Object[0]);
                indentingPrintWriter.increaseIndent();
                for (Map.Entry entry2 : ((ArrayMap) broadcastRadioService.mModules).entrySet()) {
                    indentingPrintWriter.printf("Module id=%d:\n", new Object[] {entry2.getKey()});
                    indentingPrintWriter.increaseIndent();
                    ((RadioModule) entry2.getValue()).dumpInfo(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final List listModules() {
        Collection collection;
        ArrayList arrayList;
        this.mService.enforcePolicyAccess();
        com.android.server.broadcastradio.hal2.BroadcastRadioService broadcastRadioService =
                this.mHal2Client;
        broadcastRadioService.getClass();
        Slogf.v("BcRadio2Srv", "List HIDL 2.0 modules");
        synchronized (broadcastRadioService.mLock) {
            collection =
                    (Collection)
                            ((ArrayMap) broadcastRadioService.mModules)
                                    .values().stream()
                                            .map(
                                                    new BroadcastRadioService$$ExternalSyntheticLambda0())
                                            .collect(Collectors.toList());
        }
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mV1Modules.size() + collection.size());
            arrayList.addAll(this.mV1Modules);
        }
        arrayList.addAll(collection);
        return arrayList;
    }

    public final ITuner openTuner(
            int i, RadioManager.BandConfig bandConfig, boolean z, ITunerCallback iTunerCallback) {
        boolean containsKey;
        if (Log.isLoggable("BcRadioSrvHidl", 3)) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "Opening module ", "BcRadioSrvHidl");
        }
        this.mService.enforcePolicyAccess();
        Objects.requireNonNull(iTunerCallback, "Callback must not be null");
        synchronized (this.mLock) {
            try {
                com.android.server.broadcastradio.hal2.BroadcastRadioService broadcastRadioService =
                        this.mHal2Client;
                synchronized (broadcastRadioService.mLock) {
                    containsKey =
                            ((ArrayMap) broadcastRadioService.mModules)
                                    .containsKey(Integer.valueOf(i));
                }
                if (containsKey) {
                    return this.mHal2Client.openSession(i, bandConfig, z, iTunerCallback);
                }
                return this.mHal1Client.openTuner(i, bandConfig, z, iTunerCallback);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
