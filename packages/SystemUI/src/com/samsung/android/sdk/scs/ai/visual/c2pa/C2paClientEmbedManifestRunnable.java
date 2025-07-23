package com.samsung.android.sdk.scs.ai.visual.c2pa;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paResult;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.visual.ai.sdkcommon.IC2paEmbedCallback;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00c5, code lost:
    
        if (r0.getFileDescriptor().valid() == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00c7, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00cb, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00cc, code lost:
    
        r0.printStackTrace();
     */
    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void execute() {
        /*
            r5 = this;
            java.lang.String r0 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientEmbedManifestRunnable.TAG
            java.lang.String r1 = "execute embedManifestToFile()"
            com.samsung.android.sdk.scs.base.utils.Log.d(r0, r1)
            java.lang.String r0 = r5.mTargetPath     // Catch: java.lang.Exception -> Lb9
            android.os.ParcelFileDescriptor r0 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getParcelFileDescriptor(r0)     // Catch: java.lang.Exception -> Lb9
            java.lang.String r1 = r5.mTargetPath     // Catch: java.lang.Exception -> Lb9
            java.lang.String r1 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getFileExtension(r1)     // Catch: java.lang.Exception -> Lb9
            if (r0 == 0) goto Lbb
            if (r1 == 0) goto Lbb
            java.lang.String r2 = r5.mJsonStr     // Catch: java.lang.Exception -> Lb9
            if (r2 == 0) goto Lbb
            java.lang.String r2 = r5.mTargetPath     // Catch: java.lang.Exception -> Lb9
            if (r2 != 0) goto L21
            goto Lbb
        L21:
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r2 = new com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder     // Catch: java.lang.Exception -> Lb9
            r2.<init>()     // Catch: java.lang.Exception -> Lb9
            java.lang.String r3 = r5.mJsonStr     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r2 = r2.setManifestJson(r3)     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r0 = r2.setTargetPFD(r0)     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r0 = r0.setTargetExtensionType(r1)     // Catch: java.lang.Exception -> Lb9
            java.lang.String r1 = r5.mTargetPath     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r0 = r0.setTargetPath(r1)     // Catch: java.lang.Exception -> Lb9
            java.lang.String r1 = r5.mParentPath     // Catch: java.lang.Exception -> Lb9
            r2 = 0
            if (r1 != 0) goto L41
            r1 = r2
            goto L45
        L41:
            android.os.ParcelFileDescriptor r1 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getParcelFileDescriptor(r1)     // Catch: java.lang.Exception -> Lb9
        L45:
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r0 = r0.setParentPFD(r1)     // Catch: java.lang.Exception -> Lb9
            java.lang.String r1 = r5.mParentPath     // Catch: java.lang.Exception -> Lb9
            if (r1 != 0) goto L4f
            r1 = r2
            goto L53
        L4f:
            java.lang.String r1 = com.samsung.android.sdk.scs.ai.visual.c2pa.C2paUtils.getFileExtension(r1)     // Catch: java.lang.Exception -> Lb9
        L53:
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r0 = r0.setParentExtensionType(r1)     // Catch: java.lang.Exception -> Lb9
            java.lang.String r1 = r5.mParentPath     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r0 = r0.setParentPath(r1)     // Catch: java.lang.Exception -> Lb9
            java.util.List<java.lang.String> r1 = r5.mIngredientPaths     // Catch: java.lang.Exception -> Lb9
            if (r1 != 0) goto L63
            r1 = r2
            goto L7b
        L63:
            java.util.stream.Stream r1 = r1.stream()     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientEmbedManifestRunnable$$ExternalSyntheticLambda0 r3 = new com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientEmbedManifestRunnable$$ExternalSyntheticLambda0     // Catch: java.lang.Exception -> Lb9
            r4 = 0
            r3.<init>(r4)     // Catch: java.lang.Exception -> Lb9
            java.util.stream.Stream r1 = r1.map(r3)     // Catch: java.lang.Exception -> Lb9
            java.util.stream.Collector r3 = java.util.stream.Collectors.toList()     // Catch: java.lang.Exception -> Lb9
            java.lang.Object r1 = r1.collect(r3)     // Catch: java.lang.Exception -> Lb9
            java.util.List r1 = (java.util.List) r1     // Catch: java.lang.Exception -> Lb9
        L7b:
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r0 = r0.setIngredientPFD(r1)     // Catch: java.lang.Exception -> Lb9
            java.util.List<java.lang.String> r1 = r5.mIngredientPaths     // Catch: java.lang.Exception -> Lb9
            if (r1 != 0) goto L84
            goto L9d
        L84:
            java.util.stream.Stream r1 = r1.stream()     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientEmbedManifestRunnable$$ExternalSyntheticLambda0 r2 = new com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientEmbedManifestRunnable$$ExternalSyntheticLambda0     // Catch: java.lang.Exception -> Lb9
            r3 = 1
            r2.<init>(r3)     // Catch: java.lang.Exception -> Lb9
            java.util.stream.Stream r1 = r1.map(r2)     // Catch: java.lang.Exception -> Lb9
            java.util.stream.Collector r2 = java.util.stream.Collectors.toList()     // Catch: java.lang.Exception -> Lb9
            java.lang.Object r1 = r1.collect(r2)     // Catch: java.lang.Exception -> Lb9
            r2 = r1
            java.util.List r2 = (java.util.List) r2     // Catch: java.lang.Exception -> Lb9
        L9d:
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r0 = r0.setIngredientExtensionTypes(r2)     // Catch: java.lang.Exception -> Lb9
            java.util.List<java.lang.String> r1 = r5.mIngredientPaths     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paParam$EmbedParamBuilder r0 = r0.setIngredientPaths(r1)     // Catch: java.lang.Exception -> Lb9
            android.os.Bundle r0 = r0.build()     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.sdk.scs.ai.visual.c2pa.C2paServiceExecutor r1 = r5.mServiceExecutor     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.visual.ai.sdkcommon.IDpsC2pa r1 = r1.getC2PAService()     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.visual.ai.sdkcommon.IC2paEmbedCallback r2 = r5.mCallback     // Catch: java.lang.Exception -> Lb9
            com.samsung.android.visual.ai.sdkcommon.IDpsC2pa$Stub$Proxy r1 = (com.samsung.android.visual.ai.sdkcommon.IDpsC2pa.Stub.Proxy) r1     // Catch: java.lang.Exception -> Lb9
            r1.embedManifestToPfd(r0, r2)     // Catch: java.lang.Exception -> Lb9
            return
        Lb9:
            r0 = move-exception
            goto Ld7
        Lbb:
            if (r0 == 0) goto Lcf
            java.io.FileDescriptor r1 = r0.getFileDescriptor()     // Catch: java.lang.Exception -> Lcb
            boolean r1 = r1.valid()     // Catch: java.lang.Exception -> Lcb
            if (r1 == 0) goto Lcf
            r0.close()     // Catch: java.lang.Exception -> Lcb
            goto Lcf
        Lcb:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Exception -> Lb9
        Lcf:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException     // Catch: java.lang.Exception -> Lb9
            java.lang.String r1 = "Target PFD/Extension/JSON is NULL"
            r0.<init>(r1)     // Catch: java.lang.Exception -> Lb9
            throw r0     // Catch: java.lang.Exception -> Lb9
        Ld7:
            r0.printStackTrace()
            com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource r5 = r5.mSource
            r5.setException(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paClientEmbedManifestRunnable.execute():void");
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
