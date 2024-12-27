package com.android.systemui.util.service;

import com.android.systemui.util.service.ObservableServiceConnection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
