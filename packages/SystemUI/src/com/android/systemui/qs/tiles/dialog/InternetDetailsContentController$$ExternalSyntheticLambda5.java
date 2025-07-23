package com.android.systemui.qs.tiles.dialog;

import android.graphics.drawable.Drawable;
import android.telephony.SubscriptionInfo;
import com.android.systemui.qs.tiles.dialog.InternetDetailsContentController;
import java.util.Set;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class InternetDetailsContentController$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InternetDetailsContentController$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                InternetDetailsContentController.C1DisplayInfo c1DisplayInfo = (InternetDetailsContentController.C1DisplayInfo) obj;
                Drawable drawable = InternetDetailsContentController.EMPTY_DRAWABLE;
                if (((Set) obj2).contains(c1DisplayInfo.uniqueName)) {
                    c1DisplayInfo.uniqueName = ((Object) c1DisplayInfo.originalName) + " " + c1DisplayInfo.subscriptionInfo.getSubscriptionId();
                }
                return c1DisplayInfo;
            default:
                InternetDetailsContentController internetDetailsContentController = (InternetDetailsContentController) obj2;
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
                Drawable drawable2 = InternetDetailsContentController.EMPTY_DRAWABLE;
                internetDetailsContentController.getClass();
                return new InternetDetailsContentController.C1DisplayInfo(internetDetailsContentController, subscriptionInfo, subscriptionInfo.getDisplayName().toString().trim());
        }
    }
}
