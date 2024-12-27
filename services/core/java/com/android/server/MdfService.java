package com.android.server;

import android.content.Context;
import android.os.SystemProperties;
import android.util.Log;
import com.samsung.android.security.mdf.MdfService.IMdfService;
import com.samsung.android.security.mdf.MdfService.MdfPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MdfService extends IMdfService.Stub {
    public final MdfPolicy mdfPolicy;

    public MdfService(Context context) {
        MdfPolicy mdfPolicy = MdfPolicy.getInstance(context);
        this.mdfPolicy = mdfPolicy;
        if (mdfPolicy == null) {
            Log.i("MdfService", "mdfService is null");
            return;
        }
        if (!"Enabled".equals(SystemProperties.get("ro.security.mdf.ux"))) {
            Log.i("MdfService", "This device does not support the MDF");
            return;
        }
        try {
            int initCCMode = mdfPolicy.initCCMode();
            if (initCCMode != 0) {
                Log.e("MdfService", "initCCMode res = " + Integer.toString(initCCMode));
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public final int initCCMode() {
        try {
            MdfPolicy mdfPolicy = this.mdfPolicy;
            if (mdfPolicy == null) {
                return -2;
            }
            return mdfPolicy.initCCMode();
        } catch (SecurityException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
