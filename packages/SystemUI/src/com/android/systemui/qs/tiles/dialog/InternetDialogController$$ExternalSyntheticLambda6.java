package com.android.systemui.qs.tiles.dialog;

import android.telephony.SubscriptionInfo;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda6 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                InternetDialogController.C1DisplayInfo c1DisplayInfo = (InternetDialogController.C1DisplayInfo) obj;
                if (((Set) obj2).contains(c1DisplayInfo.uniqueName)) {
                    c1DisplayInfo.uniqueName = ((Object) c1DisplayInfo.originalName) + " " + c1DisplayInfo.subscriptionInfo.getSubscriptionId();
                }
                return c1DisplayInfo;
            default:
                InternetDialogController internetDialogController = (InternetDialogController) obj2;
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                internetDialogController.getClass();
                return new InternetDialogController.C1DisplayInfo(internetDialogController, subscriptionInfo, subscriptionInfo.getDisplayName().toString().trim());
        }
    }
}
