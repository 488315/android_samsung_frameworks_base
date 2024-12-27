package com.android.server.broadcastradio.hal2;

import android.hardware.broadcastradio.V2_0.IBroadcastRadio;
import android.hardware.broadcastradio.V2_0.ITunerSession$Proxy;
import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;
import android.hidl.manager.V1_0.IServiceManager;
import android.hidl.manager.V1_0.IServiceNotification;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.IHwBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.server.broadcastradio.RadioServiceUserController;
import com.android.server.utils.Slogf;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public final class BroadcastRadioService {
    public final AnonymousClass2 mDeathRecipient;
    public int mNextModuleId;
    public final AnonymousClass1 mServiceListener;
    public final Object mLock = new Object();
    public final Map mServiceNameToModuleIdMap = new ArrayMap();
    public final Map mModules = new ArrayMap();

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.broadcastradio.hal2.BroadcastRadioService$2] */
    public BroadcastRadioService(int i) {
        IServiceNotification.Stub stub =
                new IServiceNotification
                        .Stub() { // from class:
                                  // com.android.server.broadcastradio.hal2.BroadcastRadioService.1
                    @Override // android.hidl.manager.V1_0.IServiceNotification
                    public final void onRegistration(String str, String str2, boolean z) {
                        boolean z2;
                        StringBuilder m =
                                InitialConfiguration$$ExternalSyntheticOutline0.m(
                                        "onRegistration(", str, ", ", str2, ", ");
                        m.append(z);
                        m.append(")");
                        Slogf.v("BcRadio2Srv", m.toString());
                        synchronized (BroadcastRadioService.this.mLock) {
                            try {
                                Integer num =
                                        (Integer)
                                                ((ArrayMap)
                                                                BroadcastRadioService.this
                                                                        .mServiceNameToModuleIdMap)
                                                        .get(str2);
                                if (num == null) {
                                    num = Integer.valueOf(BroadcastRadioService.this.mNextModuleId);
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                                RadioModule tryLoadingModule =
                                        RadioModule.tryLoadingModule(num.intValue(), str2);
                                if (tryLoadingModule == null) {
                                    return;
                                }
                                Slogf.v(
                                        "BcRadio2Srv",
                                        "loaded broadcast radio module "
                                                + num
                                                + ": "
                                                + str2
                                                + " (HAL 2.0)");
                                RadioModule radioModule =
                                        (RadioModule)
                                                ((ArrayMap) BroadcastRadioService.this.mModules)
                                                        .put(num, tryLoadingModule);
                                if (radioModule != null) {
                                    radioModule.closeSessions();
                                }
                                if (z2) {
                                    ((ArrayMap)
                                                    BroadcastRadioService.this
                                                            .mServiceNameToModuleIdMap)
                                            .put(str2, num);
                                    BroadcastRadioService.this.mNextModuleId++;
                                }
                                try {
                                    ((IBroadcastRadio.Proxy) tryLoadingModule.mService)
                                            .linkToDeath(
                                                    BroadcastRadioService.this.mDeathRecipient,
                                                    num.intValue());
                                } catch (RemoteException unused) {
                                    ((ArrayMap) BroadcastRadioService.this.mModules).remove(num);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mDeathRecipient =
                new IHwBinder
                        .DeathRecipient() { // from class:
                                            // com.android.server.broadcastradio.hal2.BroadcastRadioService.2
                    public final void serviceDied(long j) {
                        Slogf.v("BcRadio2Srv", "serviceDied(" + j + ")");
                        synchronized (BroadcastRadioService.this.mLock) {
                            int i2 = (int) j;
                            try {
                                RadioModule radioModule =
                                        (RadioModule)
                                                ((ArrayMap) BroadcastRadioService.this.mModules)
                                                        .remove(Integer.valueOf(i2));
                                if (radioModule != null) {
                                    radioModule.closeSessions();
                                }
                                Iterator it =
                                        ((ArrayMap)
                                                        BroadcastRadioService.this
                                                                .mServiceNameToModuleIdMap)
                                                .entrySet()
                                                .iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    Map.Entry entry = (Map.Entry) it.next();
                                    if (((Integer) entry.getValue()).intValue() == i2) {
                                        Slogf.i(
                                                "BcRadio2Srv",
                                                "service "
                                                        + ((String) entry.getKey())
                                                        + " died; removed RadioModule with ID "
                                                        + i2);
                                        break;
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mNextModuleId = i;
        try {
            IServiceManager service = IServiceManager.getService();
            if (service == null) {
                Slogf.e("BcRadio2Srv", "failed to get HIDL Service Manager");
            } else {
                service.registerForNotifications(
                        "android.hardware.broadcastradio@2.0::IBroadcastRadio", "", stub);
            }
        } catch (RemoteException e) {
            Slogf.e("BcRadio2Srv", "failed to register for service notifications: ", e);
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.broadcastradio.hal2.BroadcastRadioService$2] */
    public BroadcastRadioService(int i, IServiceManager iServiceManager) {
        IServiceNotification.Stub stub =
                new IServiceNotification
                        .Stub() { // from class:
                                  // com.android.server.broadcastradio.hal2.BroadcastRadioService.1
                    @Override // android.hidl.manager.V1_0.IServiceNotification
                    public final void onRegistration(String str, String str2, boolean z) {
                        boolean z2;
                        StringBuilder m =
                                InitialConfiguration$$ExternalSyntheticOutline0.m(
                                        "onRegistration(", str, ", ", str2, ", ");
                        m.append(z);
                        m.append(")");
                        Slogf.v("BcRadio2Srv", m.toString());
                        synchronized (BroadcastRadioService.this.mLock) {
                            try {
                                Integer num =
                                        (Integer)
                                                ((ArrayMap)
                                                                BroadcastRadioService.this
                                                                        .mServiceNameToModuleIdMap)
                                                        .get(str2);
                                if (num == null) {
                                    num = Integer.valueOf(BroadcastRadioService.this.mNextModuleId);
                                    z2 = true;
                                } else {
                                    z2 = false;
                                }
                                RadioModule tryLoadingModule =
                                        RadioModule.tryLoadingModule(num.intValue(), str2);
                                if (tryLoadingModule == null) {
                                    return;
                                }
                                Slogf.v(
                                        "BcRadio2Srv",
                                        "loaded broadcast radio module "
                                                + num
                                                + ": "
                                                + str2
                                                + " (HAL 2.0)");
                                RadioModule radioModule =
                                        (RadioModule)
                                                ((ArrayMap) BroadcastRadioService.this.mModules)
                                                        .put(num, tryLoadingModule);
                                if (radioModule != null) {
                                    radioModule.closeSessions();
                                }
                                if (z2) {
                                    ((ArrayMap)
                                                    BroadcastRadioService.this
                                                            .mServiceNameToModuleIdMap)
                                            .put(str2, num);
                                    BroadcastRadioService.this.mNextModuleId++;
                                }
                                try {
                                    ((IBroadcastRadio.Proxy) tryLoadingModule.mService)
                                            .linkToDeath(
                                                    BroadcastRadioService.this.mDeathRecipient,
                                                    num.intValue());
                                } catch (RemoteException unused) {
                                    ((ArrayMap) BroadcastRadioService.this.mModules).remove(num);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mDeathRecipient =
                new IHwBinder
                        .DeathRecipient() { // from class:
                                            // com.android.server.broadcastradio.hal2.BroadcastRadioService.2
                    public final void serviceDied(long j) {
                        Slogf.v("BcRadio2Srv", "serviceDied(" + j + ")");
                        synchronized (BroadcastRadioService.this.mLock) {
                            int i2 = (int) j;
                            try {
                                RadioModule radioModule =
                                        (RadioModule)
                                                ((ArrayMap) BroadcastRadioService.this.mModules)
                                                        .remove(Integer.valueOf(i2));
                                if (radioModule != null) {
                                    radioModule.closeSessions();
                                }
                                Iterator it =
                                        ((ArrayMap)
                                                        BroadcastRadioService.this
                                                                .mServiceNameToModuleIdMap)
                                                .entrySet()
                                                .iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    Map.Entry entry = (Map.Entry) it.next();
                                    if (((Integer) entry.getValue()).intValue() == i2) {
                                        Slogf.i(
                                                "BcRadio2Srv",
                                                "service "
                                                        + ((String) entry.getKey())
                                                        + " died; removed RadioModule with ID "
                                                        + i2);
                                        break;
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mNextModuleId = i;
        Objects.requireNonNull(iServiceManager, "Service manager cannot be null");
        try {
            iServiceManager.registerForNotifications(
                    "android.hardware.broadcastradio@2.0::IBroadcastRadio", "", stub);
        } catch (RemoteException e) {
            Slogf.e("BcRadio2Srv", "Failed to register for service notifications: ", e);
        }
    }

    public final AnnouncementAggregator addAnnouncementListener(
            int[] iArr, IAnnouncementListener iAnnouncementListener) {
        boolean z;
        Slogf.v("BcRadio2Srv", "Add announcementListener");
        AnnouncementAggregator announcementAggregator =
                new AnnouncementAggregator(iAnnouncementListener, this.mLock);
        synchronized (this.mLock) {
            Iterator it = ((ArrayMap) this.mModules).values().iterator();
            z = false;
            while (it.hasNext()) {
                try {
                    announcementAggregator.watchModule((RadioModule) it.next(), iArr);
                    z = true;
                } catch (UnsupportedOperationException e) {
                    Slogf.v("BcRadio2Srv", "Announcements not supported for this module", e);
                }
            }
        }
        if (!z) {
            Slogf.i("BcRadio2Srv", "There are no HAL modules that support announcements");
        }
        return announcementAggregator;
    }

    public final TunerSession openSession(
            int i, RadioManager.BandConfig bandConfig, boolean z, ITunerCallback iTunerCallback) {
        RadioModule radioModule;
        TunerSession tunerSession;
        Slogf.v("BcRadio2Srv", "Open HIDL 2.0 session with module id " + i);
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.e("BcRadio2Srv", "Cannot open tuner on HAL 2.0 client for non-current user");
            throw new IllegalStateException("Cannot open session for non-current user");
        }
        Objects.requireNonNull(iTunerCallback);
        if (!z) {
            throw new IllegalArgumentException("Non-audio sessions not supported with HAL 2.0");
        }
        synchronized (this.mLock) {
            radioModule = (RadioModule) ((ArrayMap) this.mModules).get(Integer.valueOf(i));
            if (radioModule == null) {
                throw new IllegalArgumentException("Invalid module ID");
            }
        }
        radioModule.mEventLogger.logRadioEvent("Open TunerSession", new Object[0]);
        synchronized (radioModule.mLock) {
            try {
                if (radioModule.mHalTunerSession == null) {
                    Mutable mutable = new Mutable();
                    ((IBroadcastRadio.Proxy) radioModule.mService)
                            .openSession(
                                    radioModule.mHalTunerCallback,
                                    new RadioModule$$ExternalSyntheticLambda0(
                                            radioModule, mutable));
                    ITunerSession$Proxy iTunerSession$Proxy = (ITunerSession$Proxy) mutable.value;
                    Objects.requireNonNull(iTunerSession$Proxy);
                    radioModule.mHalTunerSession = iTunerSession$Proxy;
                }
                tunerSession =
                        new TunerSession(radioModule, radioModule.mHalTunerSession, iTunerCallback);
                ((ArraySet) radioModule.mAidlTunerSessions).add(tunerSession);
                Boolean bool = radioModule.mAntennaConnected;
                if (bool != null) {
                    iTunerCallback.onAntennaState(bool.booleanValue());
                }
                RadioManager.ProgramInfo programInfo = radioModule.mCurrentProgramInfo;
                if (programInfo != null) {
                    iTunerCallback.onCurrentProgramInfoChanged(programInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (bandConfig != null) {
            tunerSession.setConfiguration(bandConfig);
        }
        return tunerSession;
    }
}
