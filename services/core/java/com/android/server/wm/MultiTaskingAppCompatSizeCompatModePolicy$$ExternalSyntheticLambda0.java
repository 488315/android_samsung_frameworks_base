package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultiTaskingAppCompatSizeCompatModePolicy$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ MultiTaskingAppCompatSizeCompatModePolicy$$ExternalSyntheticLambda0(boolean z, boolean z2) {
        this.f$0 = z;
        this.f$1 = z2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        boolean z = this.f$0;
        boolean z2 = this.f$1;
        AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy = ((ActivityRecord) obj).mAppCompatController.mAppCompatSizeCompatModePolicy;
        if (appCompatSizeCompatModePolicy.hasAppCompatDisplayInsetsWithoutInheritance()) {
            appCompatSizeCompatModePolicy.clearSizeCompatMode(z, z2);
        }
    }
}
