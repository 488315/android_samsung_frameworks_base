package com.android.server.timezonedetector;


public final /* synthetic */ class ServiceConfigAccessorImpl$$ExternalSyntheticLambda1
        implements Runnable {
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
                ((ServiceConfigAccessorImpl.AnonymousClass3) obj)
                        .this$0.handleConfigurationInternalChangeOnMainThread();
                break;
        }
    }
}
