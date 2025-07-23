package com.android.systemui.statusbar.connectivity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NetworkControllerImpl$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                WifiSignalController wifiSignalController = (WifiSignalController) obj;
                wifiSignalController.getClass();
                wifiSignalController.doInBackground(new WifiSignalController$$ExternalSyntheticLambda0(wifiSignalController, 2));
                break;
            default:
                NetworkControllerImpl.this.handleConfigurationChanged();
                break;
        }
    }
}
