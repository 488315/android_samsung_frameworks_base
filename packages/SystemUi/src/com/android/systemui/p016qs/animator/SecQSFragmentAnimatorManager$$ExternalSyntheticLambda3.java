package com.android.systemui.p016qs.animator;

import android.content.res.Configuration;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecQSFragmentAnimatorManager$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecQSFragmentAnimatorManager$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SecQSFragmentAnimatorBase) obj).onPanelExpansionChanged((ShadeExpansionChangeEvent) this.f$0);
                break;
            case 1:
                ((SecQSFragmentAnimatorBase) obj).setQs((InterfaceC1922QS) this.f$0);
                break;
            case 2:
                ((SecQSFragmentAnimatorBase) obj).onConfigurationChanged((Configuration) this.f$0);
                break;
            case 3:
                ((ArrayList) this.f$0).addAll((ArrayList) obj);
                break;
            case 4:
                ((SecQSFragmentAnimatorBase) obj).setPanelViewController((NotificationPanelViewController) this.f$0);
                break;
            case 5:
                ((SecQSFragmentAnimatorBase) obj).setExpandImmediateSupplier((BooleanSupplier) this.f$0);
                break;
            default:
                ((SecQSFragmentAnimatorBase) obj).setNotificationStackScrollerController((NotificationStackScrollLayoutController) this.f$0);
                break;
        }
    }
}
