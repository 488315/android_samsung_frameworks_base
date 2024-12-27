package com.android.systemui.qs.animator;

import android.content.res.Configuration;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class SecQSImplAnimatorManager$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecQSImplAnimatorManager$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((PrintWriter) obj2).println((String) obj);
                break;
            case 1:
                ((ArrayList) obj2).addAll((ArrayList) obj);
                break;
            case 2:
                ((SecQSImplAnimatorBase) obj).setShadeRepository((ShadeRepository) obj2);
                break;
            case 3:
                ((SecQSImplAnimatorBase) obj).onPanelExpansionChanged((ShadeExpansionChangeEvent) obj2);
                break;
            case 4:
                ((SecQSImplAnimatorBase) obj).onConfigurationChanged((Configuration) obj2);
                break;
            case 5:
                ((SecQSImplAnimatorBase) obj).setPanelViewController((NotificationPanelViewController) obj2);
                break;
            case 6:
                ((SecQSImplAnimatorBase) obj).setNotificationStackScrollerController((NotificationStackScrollLayoutController) obj2);
                break;
            case 7:
                ((SecQSImplAnimatorBase) obj).setQs((QS) obj2);
                break;
            default:
                ((SecQSImplAnimatorBase) obj).onPanelTransitionStateChanged((PanelTransitionStateChangeEvent) obj2);
                break;
        }
    }
}
