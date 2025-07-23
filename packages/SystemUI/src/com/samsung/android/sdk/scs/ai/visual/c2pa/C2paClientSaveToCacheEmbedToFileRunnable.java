package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.RemoteException;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paResult;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.visual.ai.sdkcommon.IC2paEmbedCallback;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class C2paClientSaveToCacheEmbedToFileRunnable extends TaskRunnable {
    private static final String TAG = "C2paClientSaveToCacheEmbedToFileRunnable";
    IC2paEmbedCallback mCallback = new IC2paEmbedCallback.Stub() { // from class: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientSaveToCacheEmbedToFileRunnable.1
        @Override // com.samsung.android.visual.ai.sdkcommon.IC2paEmbedCallback
        public void onError(String str) throws RemoteException {
            ((TaskRunnable) C2paClientSaveToCacheEmbedToFileRunnable.this).mSource.setResult(new C2paResult.Builder().setSuccess(false).setError(str).build());
        }

        @Override // com.samsung.android.visual.ai.sdkcommon.IC2paEmbedCallback
        public void onSuccess() {
            ((TaskRunnable) C2paClientSaveToCacheEmbedToFileRunnable.this).mSource.setResult(new C2paResult.Builder().setSuccess(true).build());
        }
    };
    List<String> mIngredientPaths;
    private String mJsonStr;
    String mParentPath;
    private final C2paServiceExecutor mServiceExecutor;
    private String mTargetPath;

    public C2paClientSaveToCacheEmbedToFileRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00fc, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x010e, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f6, code lost:
    
        if (r2.getFileDescriptor().valid() == false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00f8, code lost:
    
        r2.close();
     */
    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void execute() {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientSaveToCacheEmbedToFileRunnable.execute():void");
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public String getFeatureName() {
        return "FEATURE_C2PA";
    }

    public void setIngredientPaths(List<String> list) {
        this.mIngredientPaths = list;
    }

    public void setJson(String str) {
        this.mJsonStr = str;
    }

    public void setParentPath(String str) {
        this.mParentPath = str;
    }

    public void setTargetPath(String str) {
        this.mTargetPath = str;
    }
}
