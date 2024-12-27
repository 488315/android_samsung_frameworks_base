package com.android.server.broadcastradio;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.IBroadcastRadio;
import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.hardware.radio.IRadioService;
import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;

import com.android.server.broadcastradio.aidl.AnnouncementAggregator;
import com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl;
import com.android.server.broadcastradio.aidl.RadioModule;
import com.android.server.broadcastradio.aidl.TunerSession;
import com.android.server.utils.Slogf;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class IRadioServiceAidlImpl extends IRadioService.Stub {
    public static final List SERVICE_NAMES;
    public final BroadcastRadioServiceImpl mAidlHalClient;
    public final BroadcastRadioService mService;

    static {
        StringBuilder sb = new StringBuilder();
        String str = IBroadcastRadio.DESCRIPTOR;
        SERVICE_NAMES =
                Arrays.asList(
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, str, "/amfm"),
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "/dab"));
    }

    public IRadioServiceAidlImpl(
            BroadcastRadioService broadcastRadioService,
            BroadcastRadioServiceImpl broadcastRadioServiceImpl) {
        Objects.requireNonNull(broadcastRadioService, "Broadcast radio service cannot be null");
        this.mService = broadcastRadioService;
        Objects.requireNonNull(
                broadcastRadioServiceImpl,
                "Broadcast radio service implementation for AIDL HAL cannot be null");
        this.mAidlHalClient = broadcastRadioServiceImpl;
    }

    public final ICloseHandle addAnnouncementListener(
            int[] iArr, IAnnouncementListener iAnnouncementListener) {
        boolean z;
        if (Log.isLoggable("BcRadioSrvAidl", 3)) {
            Slogf.d("BcRadioSrvAidl", "Adding announcement listener for %s", Arrays.toString(iArr));
        }
        Objects.requireNonNull(iArr, "Enabled announcement types cannot be null");
        Objects.requireNonNull(iAnnouncementListener, "Announcement listener cannot be null");
        this.mService.enforcePolicyAccess();
        BroadcastRadioServiceImpl broadcastRadioServiceImpl = this.mAidlHalClient;
        broadcastRadioServiceImpl.getClass();
        if (BroadcastRadioServiceImpl.DEBUG) {
            Slogf.d(
                    "BcRadioAidlSrv",
                    "Add AnnouncementListener with enable types %s",
                    Arrays.toString(iArr));
        }
        AnnouncementAggregator announcementAggregator =
                new AnnouncementAggregator(iAnnouncementListener, broadcastRadioServiceImpl.mLock);
        synchronized (broadcastRadioServiceImpl.mLock) {
            z = false;
            for (int i = 0; i < broadcastRadioServiceImpl.mModules.size(); i++) {
                try {
                    announcementAggregator.watchModule(
                            (RadioModule) broadcastRadioServiceImpl.mModules.valueAt(i), iArr);
                    z = true;
                } catch (UnsupportedOperationException e) {
                    Slogf.w(
                            "BcRadioAidlSrv",
                            e,
                            "Announcements not supported for this module",
                            new Object[0]);
                }
            }
        }
        if (!z) {
            Slogf.w("BcRadioAidlSrv", "There are no HAL modules that support announcements");
        }
        return announcementAggregator;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mService.getContext().checkCallingOrSelfPermission("android.permission.DUMP")
                != 0) {
            printWriter.println(
                    "Permission Denial: can't dump AIDL BroadcastRadioService from from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid()
                            + " without permission android.permission.DUMP");
            return;
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.printf("BroadcastRadioService\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("AIDL HAL client:\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        BroadcastRadioServiceImpl broadcastRadioServiceImpl = this.mAidlHalClient;
        synchronized (broadcastRadioServiceImpl.mLock) {
            try {
                indentingPrintWriter.printf(
                        "Next module id available: %d\n",
                        new Object[] {Integer.valueOf(broadcastRadioServiceImpl.mNextModuleId)});
                indentingPrintWriter.printf("ServiceName to module id map:\n", new Object[0]);
                indentingPrintWriter.increaseIndent();
                for (Map.Entry entry :
                        ((ArrayMap) broadcastRadioServiceImpl.mServiceNameToModuleIdMap)
                                .entrySet()) {
                    indentingPrintWriter.printf(
                            "Service name: %s, module id: %d\n",
                            new Object[] {entry.getKey(), entry.getValue()});
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.printf(
                        "Radio modules [%d]:\n",
                        new Object[] {Integer.valueOf(broadcastRadioServiceImpl.mModules.size())});
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < broadcastRadioServiceImpl.mModules.size(); i++) {
                    indentingPrintWriter.printf(
                            "Module id=%d:\n",
                            new Object[] {
                                Integer.valueOf(broadcastRadioServiceImpl.mModules.keyAt(i))
                            });
                    indentingPrintWriter.increaseIndent();
                    ((RadioModule) broadcastRadioServiceImpl.mModules.valueAt(i))
                            .dumpInfo(indentingPrintWriter);
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
        ArrayList arrayList;
        this.mService.enforcePolicyAccess();
        BroadcastRadioServiceImpl broadcastRadioServiceImpl = this.mAidlHalClient;
        synchronized (broadcastRadioServiceImpl.mLock) {
            try {
                arrayList = new ArrayList(broadcastRadioServiceImpl.mModules.size());
                for (int i = 0; i < broadcastRadioServiceImpl.mModules.size(); i++) {
                    arrayList.add(
                            ((RadioModule) broadcastRadioServiceImpl.mModules.valueAt(i))
                                    .mProperties);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final ITuner openTuner(
            int i, RadioManager.BandConfig bandConfig, boolean z, ITunerCallback iTunerCallback) {
        TunerSession tunerSession;
        Boolean bool;
        RadioManager.ProgramInfo programInfo;
        if (Log.isLoggable("BcRadioSrvAidl", 3)) {
            Slogf.d("BcRadioSrvAidl", "Opening module %d", Integer.valueOf(i));
        }
        this.mService.enforcePolicyAccess();
        if (iTunerCallback == null) {
            throw new IllegalArgumentException("Callback must not be null");
        }
        BroadcastRadioServiceImpl broadcastRadioServiceImpl = this.mAidlHalClient;
        broadcastRadioServiceImpl.getClass();
        if (BroadcastRadioServiceImpl.DEBUG) {
            Slogf.d("BcRadioAidlSrv", "Open AIDL radio session");
        }
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.e("BcRadioAidlSrv", "Cannot open tuner on AIDL HAL client for non-current user");
            throw new IllegalStateException("Cannot open session for non-current user");
        }
        if (!z) {
            throw new IllegalArgumentException("Non-audio sessions not supported with AIDL HAL");
        }
        synchronized (broadcastRadioServiceImpl.mLock) {
            try {
                RadioModule radioModule = (RadioModule) broadcastRadioServiceImpl.mModules.get(i);
                if (radioModule == null) {
                    Slogf.e("BcRadioAidlSrv", "Invalid module ID %d", Integer.valueOf(i));
                    return null;
                }
                radioModule.mLogger.logRadioEvent("Open TunerSession", new Object[0]);
                synchronized (radioModule.mLock) {
                    try {
                        boolean isEmpty = radioModule.mAidlTunerSessions.isEmpty();
                        tunerSession =
                                new TunerSession(radioModule, radioModule.mService, iTunerCallback);
                        radioModule.mAidlTunerSessions.add(tunerSession);
                        bool = radioModule.mAntennaConnected;
                        programInfo = radioModule.mCurrentProgramInfo;
                        if (isEmpty) {
                            ((IBroadcastRadio.Stub.Proxy) radioModule.mService)
                                    .setTunerCallback(radioModule.mHalTunerCallback);
                        }
                    } finally {
                    }
                }
                if (bool != null) {
                    iTunerCallback.onAntennaState(bool.booleanValue());
                }
                if (programInfo != null) {
                    iTunerCallback.onCurrentProgramInfoChanged(programInfo);
                }
                if (bandConfig != null) {
                    tunerSession.setConfiguration(bandConfig);
                }
                return tunerSession;
            } finally {
            }
        }
    }
}
