package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import kotlin.Function;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionAdapter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AnimatedImageNotificationManager$bind$3 implements BindEventManager.Listener, FunctionAdapter {
    public final /* synthetic */ AnimatedImageNotificationManager $tmp0;

    public AnimatedImageNotificationManager$bind$3(AnimatedImageNotificationManager animatedImageNotificationManager) {
        this.$tmp0 = animatedImageNotificationManager;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof BindEventManager.Listener) && (obj instanceof FunctionAdapter)) {
            return getFunctionDelegate().equals(((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return new AdaptedFunctionReference(1, this.$tmp0, AnimatedImageNotificationManager.class, "updateAnimatedImageDrawables", "updateAnimatedImageDrawables(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;)Lkotlin/Unit;", 8);
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.BindEventManager.Listener
    public final void onViewBound(NotificationEntry notificationEntry) {
        AnimatedImageNotificationManager.access$updateAnimatedImageDrawables(this.$tmp0, notificationEntry);
    }
}
