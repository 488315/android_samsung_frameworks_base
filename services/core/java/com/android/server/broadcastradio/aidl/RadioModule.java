package com.android.server.broadcastradio.aidl;

import android.app.compat.CompatChanges;
import android.hardware.broadcastradio.AmFmRegionConfig;
import android.hardware.broadcastradio.Announcement;
import android.hardware.broadcastradio.DabTableEntry;
import android.hardware.broadcastradio.IAnnouncementListener;
import android.hardware.broadcastradio.IBroadcastRadio;
import android.hardware.broadcastradio.ICloseHandle;
import android.hardware.broadcastradio.ITunerCallback;
import android.hardware.broadcastradio.ProgramInfo;
import android.hardware.broadcastradio.ProgramListChunk;
import android.hardware.broadcastradio.ProgramSelector;
import android.hardware.broadcastradio.VendorKeyValue;
import android.hardware.radio.ICloseHandle;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;

import com.android.internal.hidden_from_bootclasspath.android.hardware.radio.Flags;
import com.android.server.broadcastradio.RadioEventLogger;
import com.android.server.broadcastradio.RadioServiceUserController;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.Objects;

public final class RadioModule {
    public Boolean mAntennaConnected;
    public RadioManager.ProgramInfo mCurrentProgramInfo;
    public final Handler mHandler;
    public final RadioEventLogger mLogger;
    public final RadioManager.ModuleProperties mProperties;
    public final IBroadcastRadio mService;
    public ProgramList.Filter mUnionOfAidlProgramFilters;
    public final Object mLock = new Object();
    public final ProgramInfoCache mProgramInfoCache = new ProgramInfoCache(null);
    public final ArraySet mAidlTunerSessions = new ArraySet();
    public final AnonymousClass1 mHalTunerCallback = new AnonymousClass1();

    /* renamed from: com.android.server.broadcastradio.aidl.RadioModule$1, reason: invalid class name */
    public final class AnonymousClass1 extends Binder implements ITunerCallback {
        public AnonymousClass1() {
            markVintfStability();
            attachInterface(this, ITunerCallback.DESCRIPTOR);
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ITunerCallback.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(2);
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString("bff68a8bc8b7cc191ab62bee10f7df8e79494467");
                return true;
            }
            switch (i) {
                case 1:
                    final int readInt = parcel.readInt();
                    final ProgramSelector programSelector =
                            (ProgramSelector) parcel.readTypedObject(ProgramSelector.CREATOR);
                    parcel.enforceNoDataAvail();
                    RadioModule.this.fireLater(
                            new Runnable() { // from class:
                                // com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    final int i3;
                                    RadioModule.AnonymousClass1 anonymousClass1 =
                                            RadioModule.AnonymousClass1.this;
                                    ProgramSelector programSelector2 = programSelector;
                                    int i4 = readInt;
                                    anonymousClass1.getClass();
                                    final android.hardware.radio.ProgramSelector
                                            programSelectorFromHalProgramSelector =
                                                    ConversionUtils
                                                            .programSelectorFromHalProgramSelector(
                                                                    programSelector2);
                                    switch (i4) {
                                        case 0:
                                            i3 = 0;
                                            break;
                                        case 1:
                                            i3 = 1;
                                            break;
                                        case 2:
                                            i3 = 2;
                                            break;
                                        case 3:
                                            i3 = 3;
                                            break;
                                        case 4:
                                            i3 = 4;
                                            break;
                                        case 5:
                                            i3 = 5;
                                            break;
                                        case 6:
                                            i3 = 6;
                                            break;
                                        default:
                                            i3 = 7;
                                            break;
                                    }
                                    synchronized (RadioModule.this.mLock) {
                                        RadioModule.this.fanoutAidlCallbackLocked(
                                                new RadioModule
                                                        .AidlCallbackRunnable() { // from class:
                                                    // com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda10
                                                    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
                                                    public final void run(
                                                            android.hardware.radio.ITunerCallback
                                                                    iTunerCallback,
                                                            int i5) {
                                                        android.hardware.radio.ProgramSelector
                                                                programSelector3 =
                                                                        programSelectorFromHalProgramSelector;
                                                        if (programSelector3 == null
                                                                || ConversionUtils
                                                                        .programSelectorMeetsSdkVersionRequirement(
                                                                                i5,
                                                                                programSelector3)) {
                                                            iTunerCallback.onTuneFailed(
                                                                    i3, programSelector3);
                                                        } else {
                                                            Slogf.e(
                                                                    "BcRadioAidlSrv.module",
                                                                    "onTuneFailed: cannot send"
                                                                        + " program selector"
                                                                        + " requiring higher target"
                                                                        + " SDK version");
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                    return true;
                case 2:
                    ProgramInfo programInfo =
                            (ProgramInfo) parcel.readTypedObject(ProgramInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    RadioModule.this.fireLater(
                            new RadioModule$$ExternalSyntheticLambda0(2, this, programInfo));
                    return true;
                case 3:
                    ProgramListChunk programListChunk =
                            (ProgramListChunk) parcel.readTypedObject(ProgramListChunk.CREATOR);
                    parcel.enforceNoDataAvail();
                    RadioModule.this.fireLater(
                            new RadioModule$$ExternalSyntheticLambda0(1, this, programListChunk));
                    return true;
                case 4:
                    final boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    RadioModule.this.fireLater(
                            new Runnable() { // from class:
                                             // com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RadioModule.AnonymousClass1 anonymousClass1 =
                                            RadioModule.AnonymousClass1.this;
                                    final boolean z = readBoolean;
                                    synchronized (RadioModule.this.mLock) {
                                        RadioModule.this.mAntennaConnected = Boolean.valueOf(z);
                                        RadioModule.this.fanoutAidlCallbackLocked(
                                                new RadioModule
                                                        .AidlCallbackRunnable() { // from class:
                                                                                  // com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda6
                                                    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
                                                    public final void run(
                                                            android.hardware.radio.ITunerCallback
                                                                    iTunerCallback,
                                                            int i3) {
                                                        iTunerCallback.onAntennaState(z);
                                                    }
                                                });
                                    }
                                }
                            });
                    return true;
                case 5:
                    final int readInt2 = parcel.readInt();
                    final boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    RadioModule.this.fireLater(
                            new Runnable() { // from class:
                                // com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RadioModule.AnonymousClass1 anonymousClass1 =
                                            RadioModule.AnonymousClass1.this;
                                    final int i3 = readInt2;
                                    final boolean z = readBoolean2;
                                    synchronized (RadioModule.this.mLock) {
                                        RadioModule.this.fanoutAidlCallbackLocked(
                                                new RadioModule
                                                        .AidlCallbackRunnable() { // from class:
                                                    // com.android.server.broadcastradio.aidl.RadioModule$1$$ExternalSyntheticLambda8
                                                    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
                                                    public final void run(
                                                            android.hardware.radio.ITunerCallback
                                                                    iTunerCallback,
                                                            int i4) {
                                                        boolean hdRadioImproved =
                                                                Flags.hdRadioImproved();
                                                        int i5 = i3;
                                                        if (!(hdRadioImproved
                                                                        && CompatChanges
                                                                                .isChangeEnabled(
                                                                                        302589903L,
                                                                                        i4))
                                                                && (i5 == 11 || i5 == 10)) {
                                                            Slogf.e(
                                                                    "BcRadioAidlSrv.module",
                                                                    "onConfigFlagUpdated: cannot"
                                                                        + " send program info"
                                                                        + " requiring higher target"
                                                                        + " SDK version");
                                                        } else {
                                                            iTunerCallback.onConfigFlagUpdated(
                                                                    i5, z);
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                    return true;
                case 6:
                    VendorKeyValue[] vendorKeyValueArr =
                            (VendorKeyValue[]) parcel.createTypedArray(VendorKeyValue.CREATOR);
                    parcel.enforceNoDataAvail();
                    RadioModule.this.fireLater(
                            new RadioModule$$ExternalSyntheticLambda0(3, this, vendorKeyValueArr));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    /* renamed from: com.android.server.broadcastradio.aidl.RadioModule$2, reason: invalid class name */
    public final class AnonymousClass2 extends Binder implements IAnnouncementListener {
        public final /* synthetic */ android.hardware.radio.IAnnouncementListener val$listener;

        public AnonymousClass2(AnnouncementAggregator.ModuleWatcher moduleWatcher) {
            this.val$listener = moduleWatcher;
            markVintfStability();
            attachInterface(this, IAnnouncementListener.DESCRIPTOR);
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = IAnnouncementListener.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(2);
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString("bff68a8bc8b7cc191ab62bee10f7df8e79494467");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            Announcement[] announcementArr =
                    (Announcement[]) parcel.createTypedArray(Announcement.CREATOR);
            parcel.enforceNoDataAvail();
            ArrayList arrayList = new ArrayList(announcementArr.length);
            for (Announcement announcement : announcementArr) {
                android.hardware.radio.ProgramSelector programSelectorFromHalProgramSelector =
                        ConversionUtils.programSelectorFromHalProgramSelector(
                                announcement.selector);
                Objects.requireNonNull(
                        programSelectorFromHalProgramSelector, "Program selector can not be null");
                arrayList.add(
                        new android.hardware.radio.Announcement(
                                programSelectorFromHalProgramSelector,
                                announcement.type,
                                ConversionUtils.vendorInfoFromHalVendorKeyValues(
                                        announcement.vendorInfo)));
            }
            this.val$listener.onListUpdated(arrayList);
            return true;
        }
    }

    public interface AidlCallbackRunnable {
        void run(android.hardware.radio.ITunerCallback iTunerCallback, int i);
    }

    public RadioModule(
            IBroadcastRadio iBroadcastRadio, RadioManager.ModuleProperties moduleProperties) {
        Objects.requireNonNull(moduleProperties, "properties cannot be null");
        this.mProperties = moduleProperties;
        Objects.requireNonNull(iBroadcastRadio, "service cannot be null");
        this.mService = iBroadcastRadio;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mLogger = new RadioEventLogger("BcRadioAidlSrv.module");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.hardware.broadcastradio.IBroadcastRadio] */
    public static RadioModule tryLoadingModule(int i, IBinder iBinder, String str) {
        IBroadcastRadio.Stub.Proxy proxy;
        AmFmRegionConfig amFmRegionConfig;
        DabTableEntry[] dabTableEntryArr;
        try {
            Slogf.i(
                    "BcRadioAidlSrv.module",
                    "Try loading module for module id = %d, module name = %s",
                    Integer.valueOf(i),
                    str);
            int i2 = IBroadcastRadio.Stub.$r8$clinit;
            if (iBinder == null) {
                proxy = null;
            } else {
                IInterface queryLocalInterface =
                        iBinder.queryLocalInterface(IBroadcastRadio.DESCRIPTOR);
                if (queryLocalInterface == null
                        || !(queryLocalInterface instanceof IBroadcastRadio)) {
                    IBroadcastRadio.Stub.Proxy proxy2 = new IBroadcastRadio.Stub.Proxy();
                    proxy2.mRemote = iBinder;
                    proxy = proxy2;
                } else {
                    proxy = (IBroadcastRadio) queryLocalInterface;
                }
            }
            if (proxy == null) {
                Slogf.w("BcRadioAidlSrv.module", "Module %s is null", str);
                return null;
            }
            try {
                amFmRegionConfig = proxy.getAmFmRegionConfig();
            } catch (RuntimeException unused) {
                Slogf.i("BcRadioAidlSrv.module", "Module %s does not has AMFM config", str);
                amFmRegionConfig = null;
            }
            try {
                dabTableEntryArr = proxy.getDabRegionConfig();
            } catch (RuntimeException unused2) {
                Slogf.i("BcRadioAidlSrv.module", "Module %s does not has DAB config", str);
                dabTableEntryArr = null;
            }
            return new RadioModule(
                    proxy,
                    ConversionUtils.propertiesFromHalProperties(
                            i, str, proxy.getProperties(), amFmRegionConfig, dabTableEntryArr));
        } catch (RemoteException e) {
            Slogf.e("BcRadioAidlSrv.module", e, "Failed to load module %s", str);
            return null;
        }
    }

    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.server.broadcastradio.aidl.RadioModule$3] */
    public final AnonymousClass3 addAnnouncementListener(
            AnnouncementAggregator.ModuleWatcher moduleWatcher, int[] iArr) {
        this.mLogger.logRadioEvent("Add AnnouncementListener", new Object[0]);
        int length = iArr.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) iArr[i];
        }
        final ICloseHandle[] iCloseHandleArr = {null};
        try {
            iCloseHandleArr[0] =
                    ((IBroadcastRadio.Stub.Proxy) this.mService)
                            .registerAnnouncementListener(new AnonymousClass2(moduleWatcher), bArr);
            return new ICloseHandle
                    .Stub() { // from class: com.android.server.broadcastradio.aidl.RadioModule.3
                public final void close() {
                    try {
                        ((ICloseHandle.Stub.Proxy) iCloseHandleArr[0]).close();
                    } catch (RemoteException e) {
                        Slogf.e(
                                "BcRadioAidlSrv.module",
                                e,
                                "Failed closing announcement listener",
                                new Object[0]);
                    }
                    iCloseHandleArr[0] = null;
                }
            };
        } catch (RuntimeException e) {
            throw ConversionUtils.throwOnError(e, "AnnouncementListener");
        }
    }

    public final void closeSessions() {
        int size;
        TunerSession[] tunerSessionArr;
        this.mLogger.logRadioEvent("Close TunerSessions %d", 0);
        synchronized (this.mLock) {
            size = this.mAidlTunerSessions.size();
            tunerSessionArr = new TunerSession[size];
            this.mAidlTunerSessions.toArray(tunerSessionArr);
        }
        for (int i = 0; i < size; i++) {
            TunerSession tunerSession = tunerSessionArr[i];
            try {
                tunerSession.close(0);
            } catch (Exception e) {
                Slogf.e(
                        "BcRadioAidlSrv.module",
                        "Failed to close TunerSession %s: %s",
                        tunerSession,
                        e);
            }
        }
    }

    public final void dumpInfo(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("RadioModule\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf(
                        "BroadcastRadioServiceImpl: %s\n", new Object[] {this.mService});
                indentingPrintWriter.printf("Properties: %s\n", new Object[] {this.mProperties});
                indentingPrintWriter.printf("Antenna state: ", new Object[0]);
                Boolean bool = this.mAntennaConnected;
                if (bool == null) {
                    indentingPrintWriter.printf("undetermined\n", new Object[0]);
                } else {
                    indentingPrintWriter.printf(
                            "%s\n",
                            new Object[] {bool.booleanValue() ? "connected" : "not connected"});
                }
                indentingPrintWriter.printf(
                        "current ProgramInfo: %s\n", new Object[] {this.mCurrentProgramInfo});
                indentingPrintWriter.printf(
                        "ProgramInfoCache: %s\n", new Object[] {this.mProgramInfoCache});
                indentingPrintWriter.printf(
                        "Union of AIDL ProgramFilters: %s\n",
                        new Object[] {this.mUnionOfAidlProgramFilters});
                indentingPrintWriter.printf(
                        "AIDL TunerSessions [%d]:\n",
                        new Object[] {Integer.valueOf(this.mAidlTunerSessions.size())});
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mAidlTunerSessions.size(); i++) {
                    ((TunerSession) this.mAidlTunerSessions.valueAt(i))
                            .dumpInfo(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.printf("Radio module events:\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mLogger.mEventLogger.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final void fanoutAidlCallbackLocked(AidlCallbackRunnable aidlCallbackRunnable) {
        int currentUser = RadioServiceUserController.getCurrentUser();
        ArrayList arrayList = null;
        for (int i = 0; i < this.mAidlTunerSessions.size(); i++) {
            if (((TunerSession) this.mAidlTunerSessions.valueAt(i)).mUserId == currentUser
                    || ((TunerSession) this.mAidlTunerSessions.valueAt(i)).mUserId == 0) {
                try {
                    aidlCallbackRunnable.run(
                            ((TunerSession) this.mAidlTunerSessions.valueAt(i)).mCallback,
                            ((TunerSession) this.mAidlTunerSessions.valueAt(i)).mUid);
                } catch (DeadObjectException unused) {
                    Slogf.e("BcRadioAidlSrv.module", "Removing dead TunerSession");
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add((TunerSession) this.mAidlTunerSessions.valueAt(i));
                } catch (RemoteException e) {
                    Slogf.e(
                            "BcRadioAidlSrv.module",
                            e,
                            "Failed to invoke ITunerCallback",
                            new Object[0]);
                }
            }
        }
        if (arrayList != null) {
            onTunerSessionsClosedLocked(
                    (TunerSession[]) arrayList.toArray(new TunerSession[arrayList.size()]));
        }
    }

    public final void fireLater(final Runnable runnable) {
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.broadcastradio.aidl.RadioModule$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        runnable.run();
                    }
                });
    }

    /* JADX WARN: Code restructure failed: missing block: B:88:0x00d9, code lost:

       r10 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0103, code lost:

       throw r10;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onTunerSessionProgramListFilterChangedLocked(
            com.android.server.broadcastradio.aidl.TunerSession r11) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.broadcastradio.aidl.RadioModule.onTunerSessionProgramListFilterChangedLocked(com.android.server.broadcastradio.aidl.TunerSession):void");
    }

    public final void onTunerSessionsClosedLocked(TunerSession... tunerSessionArr) {
        for (TunerSession tunerSession : tunerSessionArr) {
            this.mAidlTunerSessions.remove(tunerSession);
        }
        synchronized (this.mLock) {
            onTunerSessionProgramListFilterChangedLocked(null);
        }
        if (this.mAidlTunerSessions.isEmpty()) {
            try {
                ((IBroadcastRadio.Stub.Proxy) this.mService).unsetTunerCallback();
            } catch (RemoteException e) {
                Slogf.wtf(
                        "BcRadioAidlSrv.module",
                        e,
                        "Failed to unregister HAL callback for module %d",
                        Integer.valueOf(this.mProperties.getId()));
            }
        }
    }
}
