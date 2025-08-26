package com.samsung.android.knox.zt.devicetrust.cert;

import android.content.Context;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.KnoxZtException;
import com.samsung.android.knox.zt.service.KnoxZtService;

/* loaded from: classes4.dex */
public class CertProvisioningManager {
    public static volatile CertProvisioningManager sInstance;
    public final KnoxZtService mService;

    private CertProvisioningManager(Context context) throws KnoxZtException {
        try {
            this.mService = new KnoxZtService(context);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("CertProvisioningManager failed : ", th));
        }
    }

    public static CertProvisioningManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CertProvisioningManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new CertProvisioningManager(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public int provisionCert(CertProvisionProfile certProvisionProfile, ICertProvisionListener iCertProvisionListener) throws KnoxZtException {
        try {
            return this.mService.provisionCert(certProvisionProfile, iCertProvisionListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m("provisionCert failed : ", th));
        }
    }
}
