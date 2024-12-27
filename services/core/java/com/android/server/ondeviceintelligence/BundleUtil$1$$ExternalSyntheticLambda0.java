package com.android.server.ondeviceintelligence;

import android.os.Bundle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BundleUtil$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Bundle f$0;

    public /* synthetic */ BundleUtil$1$$ExternalSyntheticLambda0(int i, Bundle bundle) {
        this.$r8$classId = i;
        this.f$0 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Bundle bundle = this.f$0;
        switch (i) {
            case 0:
                BundleUtil.tryCloseResource(bundle);
                break;
            case 1:
                BundleUtil.tryCloseResource(bundle);
                break;
            case 2:
                BundleUtil.tryCloseResource(bundle);
                break;
            case 3:
                BundleUtil.tryCloseResource(bundle);
                break;
            case 4:
                BundleUtil.tryCloseResource(bundle);
                break;
            case 5:
                BundleUtil.tryCloseResource(bundle);
                break;
            default:
                BundleUtil.tryCloseResource(bundle);
                break;
        }
    }
}
