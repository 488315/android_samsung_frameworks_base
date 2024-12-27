package com.android.server.ambientcontext;

import android.app.ambientcontext.IAmbientContextObserver;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.ambientcontext.AmbientContextDetectionResult;
import android.service.ambientcontext.AmbientContextDetectionServiceStatus;
import android.util.Slog;
import com.android.server.ambientcontext.AmbientContextManagerService;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AmbientContextManagerPerUserService$$ExternalSyntheticLambda3 implements RemoteCallback.OnResultListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AmbientContextManagerPerUserService$$ExternalSyntheticLambda3(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void onResult(Bundle bundle) {
        IAmbientContextObserver iAmbientContextObserver;
        long clearCallingIdentity;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                AmbientContextManagerPerUserService ambientContextManagerPerUserService = (AmbientContextManagerPerUserService) obj;
                ambientContextManagerPerUserService.getClass();
                AmbientContextDetectionResult ambientContextDetectionResult = (AmbientContextDetectionResult) bundle.get("android.app.ambientcontext.AmbientContextDetectionResultBundleKey");
                String packageName = ambientContextDetectionResult.getPackageName();
                AmbientContextManagerService ambientContextManagerService = (AmbientContextManagerService) ambientContextManagerPerUserService.mMaster;
                int i2 = ambientContextManagerPerUserService.mUserId;
                synchronized (ambientContextManagerService.mExistingClientRequests) {
                    try {
                        Iterator it = ambientContextManagerService.mExistingClientRequests.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                AmbientContextManagerService.ClientRequest clientRequest = (AmbientContextManagerService.ClientRequest) it.next();
                                if (clientRequest.hasUserIdAndPackageName(i2, packageName)) {
                                    iAmbientContextObserver = clientRequest.mObserver;
                                }
                            } else {
                                iAmbientContextObserver = null;
                            }
                        }
                    } finally {
                    }
                }
                if (iAmbientContextObserver == null) {
                    return;
                }
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        iAmbientContextObserver.onEvents(ambientContextDetectionResult.getEvents());
                        Slog.i("AmbientContextManagerPerUserService", "Got detection result of " + ambientContextDetectionResult.getEvents() + " for " + packageName);
                    } catch (RemoteException e) {
                        Slog.w("AmbientContextManagerPerUserService", "Failed to call IAmbientContextObserver.onEvents: " + e.getMessage());
                    }
                    return;
                } finally {
                }
            default:
                Consumer consumer = (Consumer) obj;
                AmbientContextDetectionServiceStatus ambientContextDetectionServiceStatus = (AmbientContextDetectionServiceStatus) bundle.get("android.app.ambientcontext.AmbientContextServiceStatusBundleKey");
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    int statusCode = ambientContextDetectionServiceStatus.getStatusCode();
                    consumer.accept(Integer.valueOf(statusCode));
                    Slog.i("AmbientContextManagerPerUserService", "Got detection status of " + statusCode + " for " + ambientContextDetectionServiceStatus.getPackageName());
                    return;
                } finally {
                }
        }
    }
}
