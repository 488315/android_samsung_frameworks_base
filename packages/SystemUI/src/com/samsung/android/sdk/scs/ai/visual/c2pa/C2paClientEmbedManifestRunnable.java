package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.os.ParcelFileDescriptor;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paResult;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.utils.Log;
import com.samsung.android.visual.ai.sdkcommon.IC2paEmbedCallback;
import com.samsung.android.visual.ai.sdkcommon.IDpsC2pa;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class C2paClientEmbedManifestRunnable extends TaskRunnable {
    private static final String TAG = "C2paClientEmbedManifestRunnable";
    IC2paEmbedCallback mCallback = new IC2paEmbedCallback.Stub() { // from class: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientEmbedManifestRunnable.1
        @Override // com.samsung.android.visual.ai.sdkcommon.IC2paEmbedCallback
        public void onError(String str) {
            ((TaskRunnable) C2paClientEmbedManifestRunnable.this).mSource.setResult(new C2paResult.Builder().setSuccess(false).setError(str).build());
        }

        @Override // com.samsung.android.visual.ai.sdkcommon.IC2paEmbedCallback
        public void onSuccess() {
            ((TaskRunnable) C2paClientEmbedManifestRunnable.this).mSource.setResult(new C2paResult.Builder().setSuccess(true).build());
        }
    };
    List<String> mIngredientPaths;
    private String mJsonStr;
    String mParentPath;
    private final C2paServiceExecutor mServiceExecutor;
    private String mTargetPath;

    public C2paClientEmbedManifestRunnable(C2paServiceExecutor c2paServiceExecutor) {
        this.mServiceExecutor = c2paServiceExecutor;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public void execute() throws IOException {
        Log.d(TAG, "execute embedManifestToFile()");
        try {
            ParcelFileDescriptor parcelFileDescriptor = C2paUtils.getParcelFileDescriptor(this.mTargetPath);
            String fileExtension = C2paUtils.getFileExtension(this.mTargetPath);
            if (parcelFileDescriptor != null && fileExtension != null && this.mJsonStr != null && this.mTargetPath != null) {
                C2paParam.EmbedParamBuilder targetPath = new C2paParam.EmbedParamBuilder().setManifestJson(this.mJsonStr).setTargetPFD(parcelFileDescriptor).setTargetExtensionType(fileExtension).setTargetPath(this.mTargetPath);
                String str = this.mParentPath;
                List<String> list = null;
                C2paParam.EmbedParamBuilder parentPFD = targetPath.setParentPFD(str == null ? null : C2paUtils.getParcelFileDescriptor(str));
                String str2 = this.mParentPath;
                C2paParam.EmbedParamBuilder parentPath = parentPFD.setParentExtensionType(str2 == null ? null : C2paUtils.getFileExtension(str2)).setParentPath(this.mParentPath);
                List<String> list2 = this.mIngredientPaths;
                C2paParam.EmbedParamBuilder ingredientPFD = parentPath.setIngredientPFD(list2 == null ? null : (List) list2.stream().map(new C2paClientEmbedManifestRunnable$$ExternalSyntheticLambda0(0)).collect(Collectors.toList()));
                List<String> list3 = this.mIngredientPaths;
                if (list3 != null) {
                    list = (List) list3.stream().map(new C2paClientEmbedManifestRunnable$$ExternalSyntheticLambda0(1)).collect(Collectors.toList());
                }
                ((IDpsC2pa.Stub.Proxy) this.mServiceExecutor.getC2PAService()).embedManifestToPfd(ingredientPFD.setIngredientExtensionTypes(list).setIngredientPaths(this.mIngredientPaths).build(), this.mCallback);
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
            throw new NullPointerException("Target PFD/Extension/JSON is NULL");
        } catch (Exception e2) {
            e2.printStackTrace();
            this.mSource.setException(e2);
        }
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
