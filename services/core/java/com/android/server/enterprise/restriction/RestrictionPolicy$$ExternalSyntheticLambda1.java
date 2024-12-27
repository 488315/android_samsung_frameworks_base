package com.android.server.enterprise.restriction;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.IWindowManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RestrictionPolicy$$ExternalSyntheticLambda1 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        String[] strArr = RestrictionPolicy.excludedAdminList;
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window")).refreshScreenCaptureDisabled();
        } catch (RemoteException e) {
            Log.e("RestrictionPolicy", "Unable to notify WindowManager.", e);
        }
    }
}
