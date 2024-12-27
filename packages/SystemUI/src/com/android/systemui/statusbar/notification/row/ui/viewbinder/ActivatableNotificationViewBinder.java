package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ActivatableNotificationViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

public final class ActivatableNotificationViewBinder {
    public static final ActivatableNotificationViewBinder INSTANCE = new ActivatableNotificationViewBinder();

    private ActivatableNotificationViewBinder() {
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons access$registerListenersWhileAttached(com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder r4, com.android.systemui.statusbar.notification.row.ActivatableNotificationView r5, com.android.systemui.statusbar.notification.row.ui.viewbinder.TouchHandler r6, kotlin.coroutines.Continuation r7) {
        /*
            r4.getClass()
            boolean r0 = r7 instanceof com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder$registerListenersWhileAttached$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder$registerListenersWhileAttached$1 r0 = (com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder$registerListenersWhileAttached$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder$registerListenersWhileAttached$1 r0 = new com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder$registerListenersWhileAttached$1
            r0.<init>(r4, r7)
        L1b:
            java.lang.Object r4 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r7 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r0.label
            r2 = 1
            if (r1 == 0) goto L38
            if (r1 == r2) goto L2e
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2e:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.statusbar.notification.row.ActivatableNotificationView r5 = (com.android.systemui.statusbar.notification.row.ActivatableNotificationView) r5
            kotlin.ResultKt.throwOnFailure(r4)     // Catch: java.lang.Throwable -> L36
            goto L4b
        L36:
            r4 = move-exception
            goto L51
        L38:
            kotlin.ResultKt.throwOnFailure(r4)
            r5.setOnTouchListener(r6)     // Catch: java.lang.Throwable -> L36
            r5.mTouchHandler = r6     // Catch: java.lang.Throwable -> L36
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L36
            r0.label = r2     // Catch: java.lang.Throwable -> L36
            kotlin.coroutines.intrinsics.CoroutineSingletons r4 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L36
            if (r4 != r7) goto L4b
            return r7
        L4b:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L36
            r4.<init>()     // Catch: java.lang.Throwable -> L36
            throw r4     // Catch: java.lang.Throwable -> L36
        L51:
            r6 = 0
            r5.mTouchHandler = r6
            r5.setOnTouchListener(r6)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder.access$registerListenersWhileAttached(com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder, com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ui.viewbinder.TouchHandler, kotlin.coroutines.Continuation):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    public static void bind(ActivatableNotificationViewModel activatableNotificationViewModel, ActivatableNotificationView activatableNotificationView, FalsingManager falsingManager) {
        ExpandableOutlineViewBinder.INSTANCE.getClass();
        ExpandableViewBinder.INSTANCE.getClass();
        RepeatWhenAttachedKt.repeatWhenAttached(activatableNotificationView, EmptyCoroutineContext.INSTANCE, new ActivatableNotificationViewBinder$bind$1(activatableNotificationView, new TouchHandler(activatableNotificationView, falsingManager), activatableNotificationViewModel, null));
    }
}
