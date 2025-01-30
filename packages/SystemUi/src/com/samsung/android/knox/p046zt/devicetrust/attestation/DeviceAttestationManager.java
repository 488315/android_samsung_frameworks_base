package com.samsung.android.knox.p046zt.devicetrust.attestation;

import android.content.Context;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.samsung.android.knox.p046zt.KnoxZtException;
import com.samsung.android.knox.p046zt.service.KnoxZtService;
import java.security.cert.X509Certificate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DeviceAttestationManager {
    public static volatile DeviceAttestationManager sInstance;
    public final KnoxZtService mService;

    private DeviceAttestationManager(Context context) {
        try {
            this.mService = new KnoxZtService(context);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("DeviceAttestationManager failed : ", th));
        }
    }

    public static DeviceAttestationManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DeviceAttestationManager.class) {
                if (sInstance == null) {
                    sInstance = new DeviceAttestationManager(context);
                }
            }
        }
        return sInstance;
    }

    public final boolean attestKey(String str, byte[] bArr) {
        try {
            return this.mService.attestKey(str, bArr);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("attestKey failed : ", th));
        }
    }

    public final int getAppIdStatus(X509Certificate x509Certificate, Context context) {
        try {
            return this.mService.getAppIdStatus(x509Certificate, context);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("getAppIdStatus failed : ", th));
        }
    }

    public final byte[] getChallenge(X509Certificate x509Certificate) {
        try {
            return this.mService.getChallenge(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("getChallenge failed : ", th));
        }
    }

    public final String getDeviceId(X509Certificate x509Certificate) {
        try {
            return this.mService.getDeviceId(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("getDeviceId failed : ", th));
        }
    }

    public final int getDeviceIdStatus(X509Certificate x509Certificate) {
        try {
            return this.mService.getDeviceIdStatus(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("getDeviceIdStatus failed : ", th));
        }
    }

    public final int getIntegrityStatus(X509Certificate x509Certificate) {
        try {
            return this.mService.getIntegrityStatus(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("getIntegrityStatus failed : ", th));
        }
    }

    public final int getOrigin(X509Certificate x509Certificate) {
        try {
            return this.mService.getOrigin(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("getOrigin failed : ", th));
        }
    }

    public final int getRootOfTrustStatus(X509Certificate x509Certificate) {
        try {
            return this.mService.getRootOfTrustStatus(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("getRootOfTrustStatus failed : ", th));
        }
    }

    public final int getSecurityLevel(X509Certificate x509Certificate) {
        try {
            return this.mService.getSecurityLevel(x509Certificate);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("getSecurityLevel failed : ", th));
        }
    }
}
