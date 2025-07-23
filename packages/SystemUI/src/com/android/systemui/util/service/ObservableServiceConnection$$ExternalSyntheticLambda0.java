package com.android.systemui.util.service;

import android.os.IBinder;
import android.util.IndentingPrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ObservableServiceConnection$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ObservableServiceConnection f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ObservableServiceConnection$$ExternalSyntheticLambda0(ObservableServiceConnection observableServiceConnection, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = observableServiceConnection;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$dump$7((IndentingPrintWriter) this.f$1);
                break;
            case 1:
                this.f$0.lambda$dump$8((IndentingPrintWriter) this.f$1);
                break;
            default:
                this.f$0.lambda$onServiceConnected$6((IBinder) this.f$1);
                break;
        }
    }
}
