package com.android.systemui.util.service;

import com.android.systemui.util.service.ObservableServiceConnection;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ObservableServiceConnection$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ObservableServiceConnection f$0;

    public /* synthetic */ ObservableServiceConnection$$ExternalSyntheticLambda2(ObservableServiceConnection observableServiceConnection, int i) {
        this.$r8$classId = i;
        this.f$0 = observableServiceConnection;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ObservableServiceConnection observableServiceConnection = this.f$0;
        ObservableServiceConnection.Callback callback = (ObservableServiceConnection.Callback) obj;
        switch (i) {
            case 0:
                observableServiceConnection.lambda$onServiceConnected$5(callback);
                break;
            default:
                observableServiceConnection.lambda$onDisconnected$4(callback);
                break;
        }
    }
}
