package com.android.systemui.util.service;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ObservableServiceConnection$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ObservableServiceConnection f$0;

    public /* synthetic */ ObservableServiceConnection$$ExternalSyntheticLambda3(ObservableServiceConnection observableServiceConnection, int i) {
        this.$r8$classId = i;
        this.f$0 = observableServiceConnection;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ObservableServiceConnection observableServiceConnection = this.f$0;
        switch (i) {
            case 0:
                observableServiceConnection.lambda$onServiceDisconnected$9();
                break;
            case 1:
                observableServiceConnection.bindInternal();
                break;
            case 2:
                observableServiceConnection.lambda$onNullBinding$11();
                break;
            case 3:
                observableServiceConnection.lambda$unbind$0();
                break;
            default:
                observableServiceConnection.lambda$onBindingDied$10();
                break;
        }
    }
}
