package com.android.systemui.util.service;

import android.os.IBinder;
import android.util.IndentingPrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
