package com.android.systemui.util.service;

import com.android.systemui.util.service.ObservableServiceConnection;

public final /* synthetic */ class ObservableServiceConnection$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ObservableServiceConnection f$0;
    public final /* synthetic */ ObservableServiceConnection.Callback f$1;

    public /* synthetic */ ObservableServiceConnection$$ExternalSyntheticLambda1(ObservableServiceConnection observableServiceConnection, ObservableServiceConnection.Callback callback, int i) {
        this.$r8$classId = i;
        this.f$0 = observableServiceConnection;
        this.f$1 = callback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$removeCallback$3(this.f$1);
                break;
            default:
                this.f$0.lambda$addCallback$1(this.f$1);
                break;
        }
    }
}
