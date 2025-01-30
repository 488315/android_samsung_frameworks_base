package com.android.systemui.dreams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayStatusBarViewController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DreamOverlayStatusBarViewController f$0;

    public /* synthetic */ DreamOverlayStatusBarViewController$$ExternalSyntheticLambda0(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = dreamOverlayStatusBarViewController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DreamOverlayNotificationCountProvider dreamOverlayNotificationCountProvider = (DreamOverlayNotificationCountProvider) obj;
                DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3 dreamOverlayStatusBarViewController$$ExternalSyntheticLambda3 = this.f$0.mNotificationCountCallback;
                ArrayList arrayList = (ArrayList) dreamOverlayNotificationCountProvider.mCallbacks;
                if (!arrayList.contains(dreamOverlayStatusBarViewController$$ExternalSyntheticLambda3)) {
                    arrayList.add(dreamOverlayStatusBarViewController$$ExternalSyntheticLambda3);
                    dreamOverlayStatusBarViewController$$ExternalSyntheticLambda3.onNotificationCountChanged(((HashSet) dreamOverlayNotificationCountProvider.mNotificationKeys).size());
                    break;
                }
                break;
            default:
                ((ArrayList) ((DreamOverlayNotificationCountProvider) obj).mCallbacks).remove(this.f$0.mNotificationCountCallback);
                break;
        }
    }
}
