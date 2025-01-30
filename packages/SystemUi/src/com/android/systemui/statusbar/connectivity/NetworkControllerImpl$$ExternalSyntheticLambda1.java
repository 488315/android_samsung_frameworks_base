package com.android.systemui.statusbar.connectivity;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NetworkControllerImpl$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WifiSignalController wifiSignalController = (WifiSignalController) this.f$0;
                wifiSignalController.getClass();
                wifiSignalController.doInBackground(new WifiSignalController$$ExternalSyntheticLambda0(wifiSignalController, 2));
                break;
            default:
                NetworkControllerImpl.this.handleConfigurationChanged();
                break;
        }
    }
}
