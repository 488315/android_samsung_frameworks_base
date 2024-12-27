package com.android.server.timezonedetector;

import com.android.server.timezonedetector.ServiceConfigAccessorImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ServiceConfigAccessorImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((ServiceConfigAccessorImpl) obj).handleConfigurationInternalChangeOnMainThread();
                break;
            default:
                ((ServiceConfigAccessorImpl.AnonymousClass3) obj).this$0.handleConfigurationInternalChangeOnMainThread();
                break;
        }
    }
}
