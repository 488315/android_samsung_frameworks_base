package com.android.server.ondeviceintelligence;

import android.os.Bundle;
import android.os.RemoteCallback;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BundleUtil$1$$ExternalSyntheticLambda2
        implements RemoteCallback.OnResultListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RemoteCallback f$0;
    public final /* synthetic */ Executor f$1;

    public /* synthetic */ BundleUtil$1$$ExternalSyntheticLambda2(
            RemoteCallback remoteCallback, Executor executor, int i) {
        this.$r8$classId = i;
        this.f$0 = remoteCallback;
        this.f$1 = executor;
    }

    public final void onResult(Bundle bundle) {
        switch (this.$r8$classId) {
            case 0:
                RemoteCallback remoteCallback = this.f$0;
                Executor executor = this.f$1;
                try {
                    BundleUtil.sanitizeInferenceParams(bundle);
                    remoteCallback.sendResult(bundle);
                    return;
                } finally {
                    executor.execute(new BundleUtil$1$$ExternalSyntheticLambda0(3, bundle));
                }
            default:
                RemoteCallback remoteCallback2 = this.f$0;
                Executor executor2 = this.f$1;
                try {
                    BundleUtil.sanitizeInferenceParams(bundle);
                    remoteCallback2.sendResult(bundle);
                    return;
                } finally {
                    executor2.execute(new BundleUtil$1$$ExternalSyntheticLambda0(6, bundle));
                }
        }
    }
}
