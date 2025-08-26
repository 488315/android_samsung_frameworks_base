package com.samsung.android.knox.zt.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.KnoxZtException;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.samsung.android.knox.zt.devicetrust.cert.ICertProvisionListener;
import com.samsung.android.knox.zt.devicetrust.monitor.EndpointMonitoringConstants;
import com.samsung.android.knox.zt.devicetrust.monitor.IChunkedStringCallback;
import com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener;
import com.samsung.android.knox.zt.devicetrust.monitor.SignalMonitoringListener;
import com.samsung.android.knox.zt.service.IChunkedAidlInterface;
import com.samsung.android.knox.zt.service.IKnoxZtService;
import com.samsung.android.knox.zt.service.IServiceCertProvisionListener;
import com.samsung.android.knox.zt.service.IServiceMonitoringListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class KnoxZtService {
    public static final String SERVICE_NAME_KNOXZT = "knoxzt";
    public static final String TAG = "KnoxZtService";
    public final Context mContext;
    public final KeyAttestationHelper mKeyAttestationHelper;
    public final ConcurrentHashMap<IMonitoringListener, IServiceMonitoringListener> mMonitoringListeners = new ConcurrentHashMap<>();
    public Set<RegisteredMonitoringListener> mRegisteredMonitoringListenerSet = new HashSet();

    public class Chunk {
        public String data;
        public int sequenceNumber;

        public Chunk(int i, String str) {
            this.sequenceNumber = i;
            this.data = str;
        }
    }

    class RegisteredMonitoringListener {
        public IMonitoringListener listener;
        public Bundle options;
        public int type;

        public RegisteredMonitoringListener(int i, Bundle bundle, IMonitoringListener iMonitoringListener) {
            this.type = i;
            this.options = bundle;
            this.listener = iMonitoringListener;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                RegisteredMonitoringListener registeredMonitoringListener = (RegisteredMonitoringListener) obj;
                if (this.type == registeredMonitoringListener.type && Objects.equals(this.options, registeredMonitoringListener.options) && this.listener.equals(registeredMonitoringListener.listener)) {
                    return true;
                }
            }
            return false;
        }

        public final IMonitoringListener getListener() {
            return this.listener;
        }

        public final Bundle getOptions() {
            return this.options;
        }

        public final int getType() {
            return this.type;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.type), this.options, this.listener);
        }
    }

    class ZtfwReceiver extends BroadcastReceiver {
        public /* synthetic */ ZtfwReceiver(KnoxZtService knoxZtService, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (EndpointMonitoringConstants.ACTION_ZERO_TRUST_FRAMEWORK_STARTED.equals(intent.getAction())) {
                Log.i(KnoxZtService.TAG, "onReceive() - ACTION_ZERO_TRUST_FRAMEWORK_STARTED");
                KnoxZtService.this.onKnoxZtFrameworkStarted();
            }
        }

        private ZtfwReceiver() {
        }
    }

    public KnoxZtService(Context context) {
        this.mContext = context;
        this.mKeyAttestationHelper = new KeyAttestationHelper(context);
        context.registerReceiver(new ZtfwReceiver(this, 0), new IntentFilter(EndpointMonitoringConstants.ACTION_ZERO_TRUST_FRAMEWORK_STARTED), 4);
    }

    public String ackSignal(long[] jArr) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> ackSignal");
        String strAckSignal = getService().ackSignal(jArr);
        Log.i(str, "<= ackSignal");
        return strAckSignal;
    }

    public boolean attestKey(String str, byte[] bArr) {
        String str2 = TAG;
        Log.i(str2, "=> attestKey");
        boolean zAttestKey = this.mKeyAttestationHelper.attestKey(str, bArr, true);
        Log.i(str2, "<= attestKey");
        return zAttestKey;
    }

    public int getAppIdStatus(X509Certificate x509Certificate, Context context) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getAppIdStatus");
        int appIdStatus = getService().getAppIdStatus(new ParcelableCertificate(x509Certificate), context.getPackageManager().getPackagesForUid(context.getApplicationInfo().uid));
        Log.i(str, "<= getAppIdStatus");
        return appIdStatus;
    }

    public final Certificate getCertificate(ParcelableCertificate parcelableCertificate) {
        return parcelableCertificate.getCertificate();
    }

    public final Certificate[] getCertificates(ParcelableCertificate[] parcelableCertificateArr) {
        Certificate[] certificateArr = new Certificate[parcelableCertificateArr.length];
        for (int i = 0; i < parcelableCertificateArr.length; i++) {
            certificateArr[i] = parcelableCertificateArr[i].getCertificate();
        }
        return certificateArr;
    }

    public byte[] getChallenge(X509Certificate x509Certificate) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getChallenge");
        byte[] challenge = getService().getChallenge(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getChallenge");
        return challenge;
    }

    public String getDeviceId(X509Certificate x509Certificate) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getDeviceId");
        String deviceId = getService().getDeviceId(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getDeviceId");
        return deviceId;
    }

    public int getDeviceIdStatus(X509Certificate x509Certificate) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getDeviceIdStatus");
        int deviceIdStatus = getService().getDeviceIdStatus(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getDeviceIdStatus");
        return deviceIdStatus;
    }

    public final Long getInodeNumber(String str) {
        try {
            return (Long) Files.getAttribute(Paths.get(str, new String[0]), "unix:ino", new LinkOption[0]);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get ino for " + str + ", reason : " + e);
            return null;
        }
    }

    public int getIntegrityStatus(X509Certificate x509Certificate) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getIntegrityStatus");
        int integrityStatus = getService().getIntegrityStatus(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getIntegrityStatus");
        return integrityStatus;
    }

    public String getMonitoringSnapshot(int i) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getMonitoringSnapshot");
        String monitoringSnapshot = getService().getMonitoringSnapshot(i);
        Log.i(str, "<= getMonitoringSnapshot");
        return monitoringSnapshot;
    }

    public int getOrigin(X509Certificate x509Certificate) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getOrigin");
        int origin = getService().getOrigin(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getOrigin");
        return origin;
    }

    public final ParcelFileDescriptor getParcelFileDescriptor(String str, boolean z) {
        try {
            File file = new File(str);
            if (z && !file.isFile()) {
                throw new IOException("Only normal file is supported for IPC");
            }
            ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, 268435456);
            Log.d(TAG, "Succeeded to get pfd : " + str);
            return parcelFileDescriptorOpen;
        } catch (Throwable th) {
            Log.d(TAG, "Failed to get pfd : " + str + ", reason : " + th);
            return null;
        }
    }

    public final ParcelableCertificate getParcelableCertificate(Certificate certificate) {
        return new ParcelableCertificate(certificate);
    }

    public final ParcelableCertificate[] getParcelableCertificates(Certificate[] certificateArr) {
        ParcelableCertificate[] parcelableCertificateArr = new ParcelableCertificate[certificateArr.length];
        for (int i = 0; i < certificateArr.length; i++) {
            parcelableCertificateArr[i] = new ParcelableCertificate(certificateArr[i]);
        }
        return parcelableCertificateArr;
    }

    public final ParcelableProfile getParcelableProfile(CertProvisionProfile certProvisionProfile) {
        return new ParcelableProfile(certProvisionProfile.getRootCA(), certProvisionProfile.getProtocol(), certProvisionProfile.getProvisionType(), certProvisionProfile.getKeyProvider(), certProvisionProfile.getKeyOwner(), certProvisionProfile.getKeyAlias(), certProvisionProfile.getSubject(), certProvisionProfile.getServerHost(), certProvisionProfile.getServerPort(), certProvisionProfile.getServerPath(), certProvisionProfile.getSubjectAltName(), certProvisionProfile.getKeyExtendedPurposes(), certProvisionProfile.getChallengePassword(), certProvisionProfile.getClientIdentifierType(), certProvisionProfile.getClientIdentifiers(), certProvisionProfile.getSystemKeyType(), certProvisionProfile.getSystemKeyPurposes(), certProvisionProfile.getSystemKeySize());
    }

    public int getRootOfTrustStatus(X509Certificate x509Certificate) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getRootOfTrustStatus");
        int rootOfTrustStatus = getService().getRootOfTrustStatus(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getRootOfTrustStatus");
        return rootOfTrustStatus;
    }

    public int getSecurityLevel(X509Certificate x509Certificate) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getSecurityLevel");
        int securityLevel = getService().getSecurityLevel(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getSecurityLevel");
        return securityLevel;
    }

    public final IKnoxZtService getService() {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Object objInvoke = cls.getMethod("getService", String.class).invoke(cls, SERVICE_NAME_KNOXZT);
            if (objInvoke != null) {
                return IKnoxZtService.Stub.asInterface((IBinder) objInvoke);
            }
            throw new RuntimeException("failed to find knoxzt service");
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public String getVersion() throws RemoteException {
        String str = TAG;
        Log.i(str, "=> getVersion");
        String version = getService().getVersion();
        Log.i(str, "<= getVersion");
        return version;
    }

    public final void onKnoxZtFrameworkStarted() {
        Log.d(TAG, "onKnoxZtFrameworkStarted");
        restartTracingIfNecessary();
    }

    public int provisionCert(CertProvisionProfile certProvisionProfile, final ICertProvisionListener iCertProvisionListener) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> provisionCert");
        int iProvisionCert = getService().provisionCert(getParcelableProfile(certProvisionProfile), new IServiceCertProvisionListener.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.1
            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public boolean attestKey(String str2, byte[] bArr) {
                return KnoxZtService.this.mKeyAttestationHelper.attestKey(str2, bArr, true);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public ParcelableCertificate[] getCertificateChain(String str2) {
                KnoxZtService knoxZtService = KnoxZtService.this;
                return knoxZtService.getParcelableCertificates(knoxZtService.mKeyAttestationHelper.getCertificateChain(str2));
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public byte[] getSignature(String str2, byte[] bArr) {
                return KnoxZtService.this.mKeyAttestationHelper.sign(str2, bArr);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public void onError(int i, String str2) {
                iCertProvisionListener.onError(i, str2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public void onStatusChange(String str2, String str3) {
                iCertProvisionListener.onStatusChange(str2, str3);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public void onSuccess(Bundle bundle) {
                iCertProvisionListener.onSuccess(bundle);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public boolean setCertificateChain(String str2, ParcelableCertificate[] parcelableCertificateArr) {
                KnoxZtService knoxZtService = KnoxZtService.this;
                return knoxZtService.mKeyAttestationHelper.setCertificateChain(str2, knoxZtService.getCertificates(parcelableCertificateArr));
            }
        });
        Log.i(str, "<= provisionCert");
        return iProvisionCert;
    }

    public int queryAllSignals(final IChunkedStringCallback iChunkedStringCallback) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> queryAllSignals");
        int iQueryAllSignals = getService().queryAllSignals(new IChunkedAidlInterface.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.5
            @Override // com.samsung.android.knox.zt.service.IChunkedAidlInterface
            public void sendChunk(String str2, int i, boolean z) {
                iChunkedStringCallback.sendChunk(str2, i, z);
            }
        });
        Log.i(str, "<= queryAllSignals");
        return iQueryAllSignals;
    }

    public int querySignals(String str, final IChunkedStringCallback iChunkedStringCallback) throws RemoteException {
        String str2 = TAG;
        Log.i(str2, "=> querySignals");
        int iQuerySignals = getService().querySignals(str, new IChunkedAidlInterface.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.6
            @Override // com.samsung.android.knox.zt.service.IChunkedAidlInterface
            public void sendChunk(String str3, int i, boolean z) {
                iChunkedStringCallback.sendChunk(str3, i, z);
            }
        });
        Log.i(str2, "<= querySignals");
        return iQuerySignals;
    }

    public final void restartTracingIfNecessary() {
        if (this.mRegisteredMonitoringListenerSet.isEmpty()) {
            return;
        }
        for (RegisteredMonitoringListener registeredMonitoringListener : this.mRegisteredMonitoringListenerSet) {
            int i = registeredMonitoringListener.type;
            if (supportMultipleListeners(i)) {
                Bundle bundle = registeredMonitoringListener.options;
                IMonitoringListener iMonitoringListener = registeredMonitoringListener.listener;
                if (this.mMonitoringListeners.get(iMonitoringListener) != null) {
                    this.mMonitoringListeners.remove(iMonitoringListener);
                    try {
                        Log.d(TAG, "restart tracing - " + iMonitoringListener);
                        startTracing(i, bundle, iMonitoringListener);
                    } catch (Throwable th) {
                        throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("restartTracing failed : ", th));
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public int startMonitoringDomains(List<String> list, List<String> list2, final IMonitoringListener iMonitoringListener) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> startMonitoringDomains");
        int iStartMonitoringDomains = getService().startMonitoringDomains(list, list2, new IServiceMonitoringListener.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.3
            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public int checkUrlReputation(String str2) throws RemoteException {
                return -1;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public int onSignal(String str2) {
                return -1;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str2, String str3) {
                iMonitoringListener.onUnauthorizedAccessDetected(i, i2, i3, j, i4, i5, str2, str3);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEvent(int i, Bundle bundle) {
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEventGeneralized(int i, String str2) {
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEventSimplified(int i, String str2) {
            }
        });
        Log.i(str, "<= startMonitoringDomains");
        return iStartMonitoringDomains;
    }

    public int startMonitoringFiles(List<String> list, List<String> list2, final IMonitoringListener iMonitoringListener) throws RemoteException {
        Log.i(TAG, "=> startMonitoringFiles");
        ArrayList arrayList = new ArrayList();
        for (String str : list2) {
            Bundle bundle = new Bundle();
            bundle.putString("path", str);
            arrayList.add(bundle);
            ParcelFileDescriptor parcelFileDescriptor = getParcelFileDescriptor(str, true);
            if (parcelFileDescriptor != null) {
                bundle.putParcelable("pfd", parcelFileDescriptor);
            } else {
                Long inodeNumber = getInodeNumber(str);
                if (inodeNumber != null) {
                    bundle.putLong("ino", inodeNumber.longValue());
                }
            }
        }
        int iStartMonitoringFiles = getService().startMonitoringFiles(list, arrayList, new IServiceMonitoringListener.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.2
            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public int checkUrlReputation(String str2) throws RemoteException {
                return -1;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public int onSignal(String str2) {
                return -1;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str2, String str3) {
                iMonitoringListener.onUnauthorizedAccessDetected(i, i2, i3, j, i4, i5, str2, str3);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEvent(int i, Bundle bundle2) {
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEventGeneralized(int i, String str2) {
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEventSimplified(int i, String str2) {
            }
        });
        Log.i(TAG, "<= startMonitoringFiles");
        return iStartMonitoringFiles;
    }

    public int startMonitoringSignals(SignalMonitoringListener signalMonitoringListener) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> startMonitoringSignals");
        int iStartTracing = startTracing(9, null, signalMonitoringListener);
        Log.i(str, "<= startMonitoringSignals");
        return iStartTracing;
    }

    public synchronized int startTracing(int i, Bundle bundle, final IMonitoringListener iMonitoringListener) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> startTracing");
        if (supportMultipleListeners(i) && this.mMonitoringListeners.get(iMonitoringListener) != null) {
            Log.e(str, "listener already presents");
            return -1;
        }
        IServiceMonitoringListener.Stub stub = new IServiceMonitoringListener.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.4
            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public int checkUrlReputation(String str2) throws RemoteException {
                return iMonitoringListener.checkUrlReputation(str2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEvent(int i2, Bundle bundle2) {
                iMonitoringListener.onEvent(i2, bundle2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEventGeneralized(int i2, String str2) {
                iMonitoringListener.onEventGeneralized(i2, str2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onEventSimplified(int i2, String str2) {
                iMonitoringListener.onEventSimplified(i2, str2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public int onSignal(String str2) {
                try {
                    return iMonitoringListener.onSignal(str2);
                } catch (Throwable th) {
                    th.printStackTrace();
                    return -1;
                }
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public void onUnauthorizedAccessDetected(int i2, int i3, int i4, long j, int i5, int i6, String str2, String str3) {
            }
        };
        int iStartTracing = getService().startTracing(i, bundle, stub);
        if (supportMultipleListeners(i)) {
            this.mMonitoringListeners.put(iMonitoringListener, stub);
        }
        this.mRegisteredMonitoringListenerSet.add(new RegisteredMonitoringListener(i, bundle, iMonitoringListener));
        Log.i(str, "<= startTracing");
        return iStartTracing;
    }

    public int stopMonitoringDomains() throws RemoteException {
        String str = TAG;
        Log.i(str, "=> stopMonitoringDomains");
        int iStopMonitoringDomains = getService().stopMonitoringDomains();
        Log.i(str, "<= stopMonitoringDomains");
        return iStopMonitoringDomains;
    }

    public int stopMonitoringFiles() throws RemoteException {
        String str = TAG;
        Log.i(str, "=> stopMonitoringFiles");
        int iStopMonitoringFiles = getService().stopMonitoringFiles();
        Log.i(str, "<= stopMonitoringFiles");
        return iStopMonitoringFiles;
    }

    public int stopMonitoringSignals(SignalMonitoringListener signalMonitoringListener) throws RemoteException {
        String str = TAG;
        Log.i(str, "=> stopMonitoringSignals");
        int iStopTracing = stopTracing(9, signalMonitoringListener);
        Log.i(str, "<= stopMonitoringSignals");
        return iStopTracing;
    }

    public synchronized int stopTracing(int i, IMonitoringListener iMonitoringListener) throws RemoteException {
        IServiceMonitoringListener iServiceMonitoringListener;
        String str = TAG;
        Log.i(str, "=> stopTracing");
        if (supportMultipleListeners(i) && this.mMonitoringListeners.get(iMonitoringListener) == null) {
            Log.e(str, "listener doesn't present");
            return -1;
        }
        if (supportMultipleListeners(i)) {
            iServiceMonitoringListener = this.mMonitoringListeners.get(iMonitoringListener);
            this.mMonitoringListeners.remove(iMonitoringListener);
        } else {
            iServiceMonitoringListener = null;
        }
        if (!this.mRegisteredMonitoringListenerSet.isEmpty()) {
            Iterator<RegisteredMonitoringListener> it = this.mRegisteredMonitoringListenerSet.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                RegisteredMonitoringListener next = it.next();
                if (next.type == i && next.listener.equals(iMonitoringListener)) {
                    this.mRegisteredMonitoringListenerSet.remove(next);
                    break;
                }
            }
        }
        int iStopTracing = getService().stopTracing(i, iServiceMonitoringListener);
        Log.i(TAG, "<= stopTracing");
        return iStopTracing;
    }

    public final boolean supportMultipleListeners(int i) {
        return i == 8 || i == 9;
    }
}
