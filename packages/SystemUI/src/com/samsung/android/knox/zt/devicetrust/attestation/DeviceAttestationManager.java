package com.samsung.android.knox.zt.devicetrust.attestation;

import android.content.Context;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.KnoxZtException;
import com.samsung.android.knox.zt.service.KnoxZtService;
import java.security.cert.X509Certificate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DeviceAttestationManager {
    public static volatile DeviceAttestationManager sInstance;
    public final KnoxZtService mService;

    private DeviceAttestationManager(Context context) throws KnoxZtException {
        try {
            this.mService = new KnoxZtService(context);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("DeviceAttestationManager failed : ", th));
        }
    }

    public static DeviceAttestationManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DeviceAttestationManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new DeviceAttestationManager(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public boolean attestKey(String str, byte[] bArr) throws KnoxZtException {
        try {
            return this.mService.attestKey(str, bArr);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("attestKey failed : ", th));
        }
    }

    public int getAppIdStatus(X509Certificate x509Certificate, Context context) throws KnoxZtException {
        try {
            return this.mService.getAppIdStatus(x509Certificate, context);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getAppIdStatus failed : ", th));
        }
    }

    public byte[] getChallenge(X509Certificate x509Certificate) throws KnoxZtException {
        try {
            return this.mService.getChallenge(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getChallenge failed : ", th));
        }
    }

    public String getDeviceId(X509Certificate x509Certificate) throws KnoxZtException {
        try {
            return this.mService.getDeviceId(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getDeviceId failed : ", th));
        }
    }

    public int getDeviceIdStatus(X509Certificate x509Certificate) throws KnoxZtException {
        try {
            return this.mService.getDeviceIdStatus(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getDeviceIdStatus failed : ", th));
        }
    }

    public int getIntegrityStatus(X509Certificate x509Certificate) throws KnoxZtException {
        try {
            return this.mService.getIntegrityStatus(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getIntegrityStatus failed : ", th));
        }
    }

    public int getOrigin(X509Certificate x509Certificate) throws KnoxZtException {
        try {
            return this.mService.getOrigin(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getOrigin failed : ", th));
        }
    }

    public int getRootOfTrustStatus(X509Certificate x509Certificate) throws KnoxZtException {
        try {
            return this.mService.getRootOfTrustStatus(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getRootOfTrustStatus failed : ", th));
        }
    }

    public int getSecurityLevel(X509Certificate x509Certificate) throws KnoxZtException {
        try {
            return this.mService.getSecurityLevel(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("getSecurityLevel failed : ", th));
        }
    }
}
