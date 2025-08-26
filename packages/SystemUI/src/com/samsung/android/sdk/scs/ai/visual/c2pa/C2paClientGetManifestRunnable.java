package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paResult;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.visual.ai.sdkcommon.IDpsC2pa;
import java.io.IOException;

/* loaded from: classes4.dex */
public class C2paClientGetManifestRunnable extends TaskRunnable {
    private static final String TAG = "C2paClientGetManifestRunnable";
    private C2paManifestsCallback mCallback = new C2paManifestsCallback() { // from class: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientGetManifestRunnable.1
        @Override // com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestsCallback, com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback
        public void onError(String str) throws RemoteException {
            ((TaskRunnable) C2paClientGetManifestRunnable.this).mSource.setResult(new C2paResult.Builder().setSuccess(false).setError(str).build());
        }

        @Override // com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestsCallback, com.samsung.android.visual.ai.sdkcommon.IC2paManifestsCallback
        public void onResult(String str, boolean z, boolean z2) throws RemoteException {
            ((TaskRunnable) C2paClientGetManifestRunnable.this).mSource.setResult(new C2paResult.Builder().setSuccess(true).setTrusted(z).setCompleted(z2).setManifestResult(str).build());
        }
    };
    private String mFilePath;
    private final C2paServiceExecutor mServiceExecutor;

    public C2paClientGetManifestRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public void execute() throws IOException {
        Log.d(TAG, "execute getManifestsAsString()");
        try {
            String fileExtension = C2paUtils.getFileExtension(this.mFilePath);
            ParcelFileDescriptor parcelFileDescriptor = C2paUtils.getParcelFileDescriptor(this.mFilePath);
            if (parcelFileDescriptor != null && fileExtension != null && this.mFilePath != null) {
                Bundle bundleBuild = new C2paParam.ExtractParamBuilder().setPfd(parcelFileDescriptor).setExtensionType(fileExtension).setFilePath(this.mFilePath).build();
                ((IDpsC2pa.Stub.Proxy) this.mServiceExecutor.getC2PAService()).getManifestsAsStringWithPfd(bundleBuild, this.mCallback);
                return;
            }
            if (parcelFileDescriptor != null) {
                try {
                    if (parcelFileDescriptor.getFileDescriptor().valid()) {
                        parcelFileDescriptor.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            throw new NullPointerException("Target PFD/Extension is NULL");
        } catch (Exception e2) {
            e2.printStackTrace();
            this.mSource.setException(e2);
        }
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public String getFeatureName() {
        return "FEATURE_C2PA";
    }

    public void setFilePath(String str) {
        this.mFilePath = str;
    }
}
