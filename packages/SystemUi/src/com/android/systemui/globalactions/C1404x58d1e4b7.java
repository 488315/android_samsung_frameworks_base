package com.android.systemui.globalactions;

import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9 */
/* loaded from: classes.dex */
public final /* synthetic */ class C1404x58d1e4b7 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ C1404x58d1e4b7(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).mCommandQueueCallbacks.animateExpandSettingsPanel(null);
                break;
            case 1:
                int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).mCommandQueueCallbacks.animateExpandNotificationsPanel();
                break;
            default:
                ((CentralSurfacesImpl) ((CentralSurfaces) obj)).collapseShade();
                break;
        }
    }
}
