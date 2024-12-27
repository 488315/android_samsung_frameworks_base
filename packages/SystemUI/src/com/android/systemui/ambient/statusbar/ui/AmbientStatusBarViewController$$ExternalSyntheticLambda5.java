package com.android.systemui.ambient.statusbar.ui;

import com.android.systemui.dreams.DreamOverlayNotificationCountProvider;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class AmbientStatusBarViewController$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AmbientStatusBarViewController f$0;

    public /* synthetic */ AmbientStatusBarViewController$$ExternalSyntheticLambda5(AmbientStatusBarViewController ambientStatusBarViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = ambientStatusBarViewController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        AmbientStatusBarViewController ambientStatusBarViewController = this.f$0;
        switch (i) {
            case 0:
                ((ArrayList) ((DreamOverlayNotificationCountProvider) obj).mCallbacks).remove(ambientStatusBarViewController.mNotificationCountCallback);
                break;
            case 1:
                ambientStatusBarViewController.getClass();
                ambientStatusBarViewController.updateWifiUnavailableStatusIcon(((WifiNetworkModel) obj) instanceof WifiNetworkModel.Active);
                break;
            case 2:
                ambientStatusBarViewController.mCommunalVisible = ((Boolean) obj).booleanValue();
                ambientStatusBarViewController.updateVisibility$3();
                break;
            default:
                DreamOverlayNotificationCountProvider dreamOverlayNotificationCountProvider = (DreamOverlayNotificationCountProvider) obj;
                AmbientStatusBarViewController$$ExternalSyntheticLambda2 ambientStatusBarViewController$$ExternalSyntheticLambda2 = ambientStatusBarViewController.mNotificationCountCallback;
                if (!((ArrayList) dreamOverlayNotificationCountProvider.mCallbacks).contains(ambientStatusBarViewController$$ExternalSyntheticLambda2)) {
                    ((ArrayList) dreamOverlayNotificationCountProvider.mCallbacks).add(ambientStatusBarViewController$$ExternalSyntheticLambda2);
                    ambientStatusBarViewController$$ExternalSyntheticLambda2.onNotificationCountChanged(((HashSet) dreamOverlayNotificationCountProvider.mNotificationKeys).size());
                    break;
                }
                break;
        }
    }
}
