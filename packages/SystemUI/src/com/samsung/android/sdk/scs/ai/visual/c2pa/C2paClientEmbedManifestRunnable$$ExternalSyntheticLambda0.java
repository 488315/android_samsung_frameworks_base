package com.samsung.android.sdk.scs.ai.visual.c2pa;

import java.util.function.Function;

/* loaded from: classes4.dex */
public final /* synthetic */ class C2paClientEmbedManifestRunnable$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String str = (String) obj;
        switch (this.$r8$classId) {
            case 0:
                return C2paUtils.getParcelFileDescriptor(str);
            default:
                return C2paUtils.getFileExtension(str);
        }
    }
}
