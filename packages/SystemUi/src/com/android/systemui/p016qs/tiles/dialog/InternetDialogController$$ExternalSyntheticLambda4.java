package com.android.systemui.p016qs.tiles.dialog;

import android.telephony.SubscriptionInfo;
import com.android.systemui.p016qs.tiles.dialog.InternetDialogController;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                if (((Set) this.f$0).contains(c1DisplayInfo.uniqueName)) {
                    c1DisplayInfo.uniqueName = ((Object) c1DisplayInfo.originalName) + " " + c1DisplayInfo.subscriptionInfo.getSubscriptionId();
                }
                return c1DisplayInfo;
            default:
                InternetDialogController internetDialogController = (InternetDialogController) this.f$0;
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                internetDialogController.getClass();
                return new InternetDialogController.C1DisplayInfo(internetDialogController, subscriptionInfo, subscriptionInfo.getDisplayName().toString().trim());
        }
    }
}
