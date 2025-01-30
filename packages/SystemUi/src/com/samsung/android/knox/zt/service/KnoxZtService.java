package com.samsung.android.knox.zt.service;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.zt.devicetrust.cert.CertProvisionProfile;
import com.samsung.android.knox.zt.devicetrust.cert.ICertProvisionListener;
import com.samsung.android.knox.zt.devicetrust.monitor.IMonitoringListener;
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
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KnoxZtService {
    public static final String SERVICE_NAME_KNOXZT = "knoxzt";
    public static final String TAG = "KnoxZtService";
    public final Context mContext;
    public final KeyAttestationHelper mKeyAttestationHelper;
    public final ConcurrentHashMap<IMonitoringListener, IServiceMonitoringListener> mMonitoringListeners = new ConcurrentHashMap<>();

    public KnoxZtService(Context context) {
        this.mContext = context;
        this.mKeyAttestationHelper = new KeyAttestationHelper(context);
    }

    public final boolean attestKey(String str, byte[] bArr) {
        String str2 = TAG;
        Log.i(str2, "=> attestKey");
        boolean attestKey = this.mKeyAttestationHelper.attestKey(str, bArr, true);
        Log.i(str2, "<= attestKey");
        return attestKey;
    }

    public final int getAppIdStatus(X509Certificate x509Certificate, Context context) {
        String str = TAG;
        Log.i(str, "=> getAppIdStatus");
        int appIdStatus = getService().getAppIdStatus(new ParcelableCertificate(x509Certificate), context.getPackageManager().getPackagesForUid(context.getApplicationInfo().uid));
        Log.i(str, "<= getAppIdStatus");
        return appIdStatus;
    }

    public final Certificate getCertificate(ParcelableCertificate parcelableCertificate) {
        return parcelableCertificate.mCertificate;
    }

    public final Certificate[] getCertificates(ParcelableCertificate[] parcelableCertificateArr) {
        Certificate[] certificateArr = new Certificate[parcelableCertificateArr.length];
        for (int i = 0; i < parcelableCertificateArr.length; i++) {
            certificateArr[i] = parcelableCertificateArr[i].mCertificate;
        }
        return certificateArr;
    }

    public final byte[] getChallenge(X509Certificate x509Certificate) {
        String str = TAG;
        Log.i(str, "=> getChallenge");
        byte[] challenge = getService().getChallenge(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getChallenge");
        return challenge;
    }

    public final String getDeviceId(X509Certificate x509Certificate) {
        String str = TAG;
        Log.i(str, "=> getDeviceId");
        String deviceId = getService().getDeviceId(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getDeviceId");
        return deviceId;
    }

    public final int getDeviceIdStatus(X509Certificate x509Certificate) {
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

    public final int getIntegrityStatus(X509Certificate x509Certificate) {
        String str = TAG;
        Log.i(str, "=> getIntegrityStatus");
        int integrityStatus = getService().getIntegrityStatus(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getIntegrityStatus");
        return integrityStatus;
    }

    public final int getOrigin(X509Certificate x509Certificate) {
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
            ParcelFileDescriptor open = ParcelFileDescriptor.open(file, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            Log.d(TAG, "Succeeded to get pfd : " + str);
            return open;
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
        return new ParcelableProfile(certProvisionProfile.mRootCA, certProvisionProfile.mProtocol, certProvisionProfile.mProvisionType, certProvisionProfile.mKeyProvider, certProvisionProfile.mKeyOwner, certProvisionProfile.mKeyAlias, certProvisionProfile.mSubject, certProvisionProfile.mServerHost, certProvisionProfile.mServerPort, certProvisionProfile.mServerPath, certProvisionProfile.mSubjectAltName, certProvisionProfile.mKeyExtendedPurposes, certProvisionProfile.mChallengePassword, certProvisionProfile.mClientIdentifierType, certProvisionProfile.mClientIdentifiers, certProvisionProfile.mSystemKeyType, certProvisionProfile.mSystemKeyPurposes, certProvisionProfile.mSystemKeySize);
    }

    public final int getRootOfTrustStatus(X509Certificate x509Certificate) {
        String str = TAG;
        Log.i(str, "=> getRootOfTrustStatus");
        int rootOfTrustStatus = getService().getRootOfTrustStatus(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getRootOfTrustStatus");
        return rootOfTrustStatus;
    }

    public final int getSecurityLevel(X509Certificate x509Certificate) {
        String str = TAG;
        Log.i(str, "=> getSecurityLevel");
        int securityLevel = getService().getSecurityLevel(new ParcelableCertificate(x509Certificate));
        Log.i(str, "<= getSecurityLevel");
        return securityLevel;
    }

    public final IKnoxZtService getService() {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Object invoke = cls.getMethod("getService", String.class).invoke(cls, SERVICE_NAME_KNOXZT);
            if (invoke != null) {
                return IKnoxZtService.Stub.asInterface((IBinder) invoke);
            }
            throw new RuntimeException("failed to find knoxzt service");
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public final int provisionCert(CertProvisionProfile certProvisionProfile, final ICertProvisionListener iCertProvisionListener) {
        String str = TAG;
        Log.i(str, "=> provisionCert");
        int provisionCert = getService().provisionCert(getParcelableProfile(certProvisionProfile), new IServiceCertProvisionListener.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.1
            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public final boolean attestKey(String str2, byte[] bArr) {
                return KnoxZtService.this.mKeyAttestationHelper.attestKey(str2, bArr, true);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public final ParcelableCertificate[] getCertificateChain(String str2) {
                KnoxZtService knoxZtService = KnoxZtService.this;
                return knoxZtService.getParcelableCertificates(knoxZtService.mKeyAttestationHelper.getCertificateChain(str2));
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public final byte[] getSignature(String str2, byte[] bArr) {
                return KnoxZtService.this.mKeyAttestationHelper.sign(str2, bArr);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public final void onError(int i, String str2) {
                iCertProvisionListener.onError(i, str2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public final void onStatusChange(String str2, String str3) {
                iCertProvisionListener.onStatusChange(str2, str3);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public final void onSuccess(Bundle bundle) {
                iCertProvisionListener.onSuccess(bundle);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public final boolean setCertificateChain(String str2, ParcelableCertificate[] parcelableCertificateArr) {
                KnoxZtService knoxZtService = KnoxZtService.this;
                return knoxZtService.mKeyAttestationHelper.setCertificateChain(str2, knoxZtService.getCertificates(parcelableCertificateArr));
            }
        });
        Log.i(str, "<= provisionCert");
        return provisionCert;
    }

    public final int startMonitoringDomains(List<String> list, List<String> list2, final IMonitoringListener iMonitoringListener) {
        String str = TAG;
        Log.i(str, "=> startMonitoringDomains");
        int startMonitoringDomains = getService().startMonitoringDomains(list, list2, new IServiceMonitoringListener.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.3
            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final int checkUrlReputation(String str2) {
                return -1;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str2, String str3) {
                iMonitoringListener.onUnauthorizedAccessDetected(i, i2, i3, j, i4, i5, str2, str3);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onEvent(int i, Bundle bundle) {
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onEventGeneralized(int i, String str2) {
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onEventSimplified(int i, String str2) {
            }
        });
        Log.i(str, "<= startMonitoringDomains");
        return startMonitoringDomains;
    }

    public final int startMonitoringFiles(List<String> list, List<String> list2, final IMonitoringListener iMonitoringListener) {
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
        int startMonitoringFiles = getService().startMonitoringFiles(list, arrayList, new IServiceMonitoringListener.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.2
            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final int checkUrlReputation(String str2) {
                return -1;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str2, String str3) {
                iMonitoringListener.onUnauthorizedAccessDetected(i, i2, i3, j, i4, i5, str2, str3);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onEvent(int i, Bundle bundle2) {
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onEventGeneralized(int i, String str2) {
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onEventSimplified(int i, String str2) {
            }
        });
        Log.i(TAG, "<= startMonitoringFiles");
        return startMonitoringFiles;
    }

    public final int startTracing(int i, Bundle bundle, final IMonitoringListener iMonitoringListener) {
        String str = TAG;
        Log.i(str, "=> startTracing");
        if (supportMultipleListeners(i) && this.mMonitoringListeners.get(iMonitoringListener) != null) {
            Log.e(str, "listener already presents");
            return -1;
        }
        IServiceMonitoringListener.Stub stub = new IServiceMonitoringListener.Stub() { // from class: com.samsung.android.knox.zt.service.KnoxZtService.4
            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final int checkUrlReputation(String str2) {
                return iMonitoringListener.checkUrlReputation(str2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onEvent(int i2, Bundle bundle2) {
                iMonitoringListener.onEvent(i2, bundle2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onEventGeneralized(int i2, String str2) {
                iMonitoringListener.onEventGeneralized(i2, str2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onEventSimplified(int i2, String str2) {
                iMonitoringListener.onEventSimplified(i2, str2);
            }

            @Override // com.samsung.android.knox.zt.service.IServiceMonitoringListener
            public final void onUnauthorizedAccessDetected(int i2, int i3, int i4, long j, int i5, int i6, String str2, String str3) {
            }
        };
        int startTracing = getService().startTracing(i, bundle, stub);
        if (supportMultipleListeners(i)) {
            this.mMonitoringListeners.put(iMonitoringListener, stub);
        }
        Log.i(str, "<= startTracing");
        return startTracing;
    }

    public final int stopMonitoringDomains() {
        String str = TAG;
        Log.i(str, "=> stopMonitoringDomains");
        int stopMonitoringDomains = getService().stopMonitoringDomains();
        Log.i(str, "<= stopMonitoringDomains");
        return stopMonitoringDomains;
    }

    public final int stopMonitoringFiles() {
        String str = TAG;
        Log.i(str, "=> stopMonitoringFiles");
        int stopMonitoringFiles = getService().stopMonitoringFiles();
        Log.i(str, "<= stopMonitoringFiles");
        return stopMonitoringFiles;
    }

    public final int stopTracing(int i, IMonitoringListener iMonitoringListener) {
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
        int stopTracing = getService().stopTracing(i, iServiceMonitoringListener);
        Log.i(str, "<= stopTracing");
        return stopTracing;
    }

    public final boolean supportMultipleListeners(int i) {
        return i == 8;
    }
}
