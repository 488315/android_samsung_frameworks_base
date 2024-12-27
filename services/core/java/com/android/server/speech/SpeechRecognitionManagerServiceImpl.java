package com.android.server.speech;

import android.app.AppGlobals;
import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.permission.PermissionManager;
import android.provider.Settings;
import android.speech.IModelDownloadListener;
import android.speech.IRecognitionListener;
import android.speech.IRecognitionService;
import android.speech.IRecognitionServiceManagerCallback;
import android.speech.IRecognitionSupportCallback;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;
import com.android.modules.expresslog.Counter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.infra.AbstractMasterSystemService;
import com.android.server.infra.AbstractPerUserSystemService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class SpeechRecognitionManagerServiceImpl extends AbstractPerUserSystemService {
    public final Object mLock;
    public final Map mRemoteServicesByUid;
    public final SparseIntArray mSessionCountByUid;

    public SpeechRecognitionManagerServiceImpl(SpeechRecognitionManagerService speechRecognitionManagerService, Object obj, int i) {
        super(speechRecognitionManagerService, obj, i);
        this.mLock = new Object();
        this.mRemoteServicesByUid = new HashMap();
        this.mSessionCountByUid = new SparseIntArray();
    }

    public static void tryRespondWithError(IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback, int i) {
        try {
            iRecognitionServiceManagerCallback.onError(i);
        } catch (RemoteException unused) {
            Slog.w("SpeechRecognitionManagerServiceImpl", "Failed to respond with error");
        }
    }

    public final boolean checkPrivilege(ComponentName componentName) {
        boolean z;
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        ContentResolver contentResolver = abstractMasterSystemService.getContext().getContentResolver();
        int i = this.mUserId;
        String stringForUser = Settings.Secure.getStringForUser(contentResolver, "voice_recognition_service", i);
        ComponentName unflattenFromString = stringForUser == null ? null : ComponentName.unflattenFromString(stringForUser);
        ComponentName onDeviceComponentNameLocked = getOnDeviceComponentNameLocked();
        PackageManager packageManager = abstractMasterSystemService.getContext().getPackageManager();
        if (packageManager != null) {
            if ((packageManager.getApplicationInfoAsUser(componentName.getPackageName(), 1048576, i).flags & 1) != 0) {
                z = true;
                return !componentName.equals(unflattenFromString) ? true : true;
            }
        }
        z = false;
        return !componentName.equals(unflattenFromString) ? true : true;
    }

    public final boolean componentMapsToRecognitionService(ComponentName componentName) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List queryIntentServicesAsUser = this.mMaster.getContext().getPackageManager().queryIntentServicesAsUser(new Intent("android.speech.RecognitionService"), 0, this.mUserId);
            if (queryIntentServicesAsUser == null) {
                return false;
            }
            Iterator it = queryIntentServicesAsUser.iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                if (serviceInfo != null && componentName.equals(serviceInfo.getComponentName())) {
                    return true;
                }
            }
            Slog.w("SpeechRecognitionManagerServiceImpl", "serviceComponent is not RecognitionService: " + componentName);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void createSessionLocked(final ComponentName componentName, final IBinder iBinder, boolean z, final IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback) {
        final RemoteSpeechRecognitionService remoteSpeechRecognitionService;
        if (((SpeechRecognitionManagerService) this.mMaster).debug) {
            Slog.i("SpeechRecognitionManagerServiceImpl", "#createSessionLocked, component=" + componentName + ", onDevice=" + z);
        }
        if (z) {
            componentName = getOnDeviceComponentNameLocked();
        }
        if (!z && Process.isIsolated(Binder.getCallingUid())) {
            Slog.w("SpeechRecognitionManagerServiceImpl", "Isolated process can only start on device speech recognizer.");
            tryRespondWithError(iRecognitionServiceManagerCallback, 5);
            return;
        }
        if (componentName == null) {
            if (((SpeechRecognitionManagerService) this.mMaster).debug) {
                Slog.i("SpeechRecognitionManagerServiceImpl", "Service component is undefined, responding with error.");
            }
            tryRespondWithError(iRecognitionServiceManagerCallback, 5);
            return;
        }
        final int callingUid = Binder.getCallingUid();
        synchronized (this.mLock) {
            try {
                Set set = (Set) ((HashMap) this.mRemoteServicesByUid).get(Integer.valueOf(callingUid));
                remoteSpeechRecognitionService = null;
                if (set == null || set.size() < 10) {
                    if (this.mSessionCountByUid.get(callingUid, 0) == 10) {
                        Slog.w("SpeechRecognitionManagerServiceImpl", "Number of sessions exceeded for uid: " + callingUid);
                        Counter.logIncrementWithUid("speech_recognition.value_exceed_session_count", callingUid);
                    }
                    if (set != null) {
                        Optional findFirst = set.stream().filter(new Predicate() { // from class: com.android.server.speech.SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((RemoteSpeechRecognitionService) obj).getServiceComponentName().equals(componentName);
                            }
                        }).findFirst();
                        if (findFirst.isPresent()) {
                            if (((SpeechRecognitionManagerService) this.mMaster).debug) {
                                Slog.i("SpeechRecognitionManagerServiceImpl", "Reused existing connection to " + componentName);
                            }
                            incrementSessionCountForUidLocked(callingUid);
                            remoteSpeechRecognitionService = (RemoteSpeechRecognitionService) findFirst.get();
                        }
                    }
                    if (componentMapsToRecognitionService(componentName)) {
                        RemoteSpeechRecognitionService remoteSpeechRecognitionService2 = new RemoteSpeechRecognitionService(this.mUserId, callingUid, componentName, this.mMaster.getContext(), checkPrivilege(componentName));
                        ((Set) ((HashMap) this.mRemoteServicesByUid).computeIfAbsent(Integer.valueOf(callingUid), new SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda3())).add(remoteSpeechRecognitionService2);
                        if (((SpeechRecognitionManagerService) this.mMaster).debug) {
                            Slog.i("SpeechRecognitionManagerServiceImpl", "Creating a new connection to " + componentName);
                        }
                        incrementSessionCountForUidLocked(callingUid);
                        remoteSpeechRecognitionService = remoteSpeechRecognitionService2;
                    }
                } else {
                    Slog.w("SpeechRecognitionManagerServiceImpl", "Number of remote services exceeded for uid: " + callingUid);
                    Counter.logIncrementWithUid("speech_recognition.value_exceed_service_connections_count", callingUid);
                }
            } finally {
            }
        }
        if (remoteSpeechRecognitionService == null) {
            tryRespondWithError(iRecognitionServiceManagerCallback, 10);
            return;
        }
        final ?? r7 = new IBinder.DeathRecipient() { // from class: com.android.server.speech.SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                SpeechRecognitionManagerServiceImpl.this.handleClientDeath(iBinder, callingUid, remoteSpeechRecognitionService, true);
            }
        };
        try {
            iBinder.linkToDeath(r7, 0);
            remoteSpeechRecognitionService.connect().thenAccept(new Consumer() { // from class: com.android.server.speech.SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    final SpeechRecognitionManagerServiceImpl speechRecognitionManagerServiceImpl = SpeechRecognitionManagerServiceImpl.this;
                    IRecognitionServiceManagerCallback iRecognitionServiceManagerCallback2 = iRecognitionServiceManagerCallback;
                    final RemoteSpeechRecognitionService remoteSpeechRecognitionService3 = remoteSpeechRecognitionService;
                    final IBinder iBinder2 = iBinder;
                    final int i = callingUid;
                    IBinder.DeathRecipient deathRecipient = r7;
                    IRecognitionService iRecognitionService = (IRecognitionService) obj;
                    speechRecognitionManagerServiceImpl.getClass();
                    if (iRecognitionService == null) {
                        SpeechRecognitionManagerServiceImpl.tryRespondWithError(iRecognitionServiceManagerCallback2, 5);
                        return;
                    }
                    try {
                        final SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda0 speechRecognitionManagerServiceImpl$$ExternalSyntheticLambda0 = (SpeechRecognitionManagerServiceImpl$$ExternalSyntheticLambda0) deathRecipient;
                        iRecognitionServiceManagerCallback2.onSuccess(new IRecognitionService.Stub() { // from class: com.android.server.speech.SpeechRecognitionManagerServiceImpl.1
                            public final void cancel(IRecognitionListener iRecognitionListener, boolean z2) {
                                remoteSpeechRecognitionService3.cancel(iRecognitionListener, z2);
                                if (z2) {
                                    SpeechRecognitionManagerServiceImpl.this.handleClientDeath(iBinder2, i, remoteSpeechRecognitionService3, false);
                                    iBinder2.unlinkToDeath(speechRecognitionManagerServiceImpl$$ExternalSyntheticLambda0, 0);
                                }
                            }

                            public final void checkRecognitionSupport(Intent intent, AttributionSource attributionSource, IRecognitionSupportCallback iRecognitionSupportCallback) {
                                remoteSpeechRecognitionService3.checkRecognitionSupport(intent, attributionSource, iRecognitionSupportCallback);
                            }

                            public final void startListening(Intent intent, IRecognitionListener iRecognitionListener, AttributionSource attributionSource) {
                                attributionSource.enforceCallingUid();
                                if (!attributionSource.isTrusted(((SpeechRecognitionManagerService) SpeechRecognitionManagerServiceImpl.this.mMaster).getContext())) {
                                    attributionSource = ((PermissionManager) ((SpeechRecognitionManagerService) SpeechRecognitionManagerServiceImpl.this.mMaster).getContext().getSystemService(PermissionManager.class)).registerAttributionSource(attributionSource);
                                }
                                remoteSpeechRecognitionService3.startListening(intent, iRecognitionListener, attributionSource);
                                remoteSpeechRecognitionService3.associateClientWithActiveListener(iBinder2, iRecognitionListener);
                            }

                            public final void stopListening(IRecognitionListener iRecognitionListener) {
                                remoteSpeechRecognitionService3.stopListening(iRecognitionListener);
                            }

                            public final void triggerModelDownload(Intent intent, AttributionSource attributionSource, IModelDownloadListener iModelDownloadListener) {
                                remoteSpeechRecognitionService3.triggerModelDownload(intent, attributionSource, iModelDownloadListener);
                            }
                        });
                    } catch (RemoteException e) {
                        Slog.e("SpeechRecognitionManagerServiceImpl", "Error creating a speech recognition session", e);
                        SpeechRecognitionManagerServiceImpl.tryRespondWithError(iRecognitionServiceManagerCallback2, 5);
                    }
                }
            });
        } catch (RemoteException unused) {
            handleClientDeath(iBinder, callingUid, remoteSpeechRecognitionService, true);
        }
    }

    public final ComponentName getOnDeviceComponentNameLocked() {
        String componentNameLocked = getComponentNameLocked();
        AbstractMasterSystemService abstractMasterSystemService = this.mMaster;
        if (((SpeechRecognitionManagerService) abstractMasterSystemService).debug) {
            Slog.i("SpeechRecognitionManagerServiceImpl", "Resolved component name: " + componentNameLocked);
        }
        if (componentNameLocked != null) {
            return ComponentName.unflattenFromString(componentNameLocked);
        }
        if (!((SpeechRecognitionManagerService) abstractMasterSystemService).verbose) {
            return null;
        }
        Slog.v("SpeechRecognitionManagerServiceImpl", "ensureRemoteServiceLocked(): no service component name.");
        return null;
    }

    public final void handleClientDeath(IBinder iBinder, int i, RemoteSpeechRecognitionService remoteSpeechRecognitionService, boolean z) {
        if (z) {
            remoteSpeechRecognitionService.shutdown(iBinder);
        }
        synchronized (this.mLock) {
            int i2 = this.mSessionCountByUid.get(i, 1) - 1;
            if (i2 > 0) {
                this.mSessionCountByUid.put(i, i2);
            } else {
                this.mSessionCountByUid.delete(i);
            }
            if (!remoteSpeechRecognitionService.hasActiveSessions()) {
                synchronized (this.mLock) {
                    try {
                        Set set = (Set) ((HashMap) this.mRemoteServicesByUid).get(Integer.valueOf(i));
                        if (set != null) {
                            set.remove(remoteSpeechRecognitionService);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public final void incrementSessionCountForUidLocked(int i) {
        SparseIntArray sparseIntArray = this.mSessionCountByUid;
        sparseIntArray.put(i, sparseIntArray.get(i, 0) + 1);
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "Client ", " has opened ");
        m.append(this.mSessionCountByUid.get(i, 0));
        m.append(" sessions");
        Log.i("SpeechRecognitionManagerServiceImpl", m.toString());
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    public final ServiceInfo newServiceInfoLocked(ComponentName componentName) {
        try {
            return AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
        } catch (RemoteException unused) {
            throw new PackageManager.NameNotFoundException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "Could not get service for "));
        }
    }
}
