package com.android.server.ondeviceintelligence;

import android.os.Bundle;

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
