package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.ParcelFileDescriptor;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.visual.ai.sdkcommon.IDpsC2pa;
import java.io.IOException;

/* loaded from: classes4.dex */
public class C2paClientIsC2paExistRunnable extends TaskRunnable {
    private static final String TAG = "C2paClientIsC2paExistRunnable";
    String mFilePath;
    private final C2paServiceExecutor mServiceExecutor;

    public C2paClientIsC2paExistRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public void execute() throws IOException {
        Log.d(TAG, "execute isC2paExist()");
        try {
            String fileExtension = C2paUtils.getFileExtension(this.mFilePath);
            ParcelFileDescriptor parcelFileDescriptor = C2paUtils.getParcelFileDescriptor(this.mFilePath);
            if (parcelFileDescriptor != null && fileExtension != null) {
                this.mSource.setResult(Boolean.valueOf(((IDpsC2pa.Stub.Proxy) this.mServiceExecutor.getC2PAService()).isC2paInfoExistWithPfd(new C2paParam.C2paExistParamBuilder().setPfd(parcelFileDescriptor).setExtensionType(fileExtension).build())));
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

    public void setParentPath(String str) {
        this.mFilePath = str;
    }
}
