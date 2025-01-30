package com.samsung.android.knox.p046zt.devicetrust.cert;

import android.content.Context;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.samsung.android.knox.p046zt.KnoxZtException;
import com.samsung.android.knox.p046zt.service.KnoxZtService;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CertProvisioningManager {
    public static volatile CertProvisioningManager sInstance;
    public final KnoxZtService mService;

    private CertProvisioningManager(Context context) {
        try {
            this.mService = new KnoxZtService(context);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("CertProvisioningManager failed : ", th));
        }
    }

    public static CertProvisioningManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CertProvisioningManager.class) {
                if (sInstance == null) {
                    sInstance = new CertProvisioningManager(context);
                }
            }
        }
        return sInstance;
    }

    public final int provisionCert(CertProvisionProfile certProvisionProfile, ICertProvisionListener iCertProvisionListener) {
        try {
            return this.mService.provisionCert(certProvisionProfile, iCertProvisionListener);
        } catch (Throwable th) {
            throw new KnoxZtException(RowInflaterTask$$ExternalSyntheticOutline0.m206m("provisionCert failed : ", th));
        }
    }
}
