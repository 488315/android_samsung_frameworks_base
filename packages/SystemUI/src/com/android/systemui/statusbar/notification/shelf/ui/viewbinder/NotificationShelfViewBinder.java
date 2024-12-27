package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerShelfViewBinder;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationShelfViewBinder {
    public static final NotificationShelfViewBinder INSTANCE = new NotificationShelfViewBinder();

    private NotificationShelfViewBinder() {
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlin.coroutines.intrinsics.CoroutineSingletons access$registerViewListenersWhileAttached(com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder r4, com.android.systemui.statusbar.NotificationShelf r5, final com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel r6, kotlin.coroutines.Continuation r7) {
        /*
            r4.getClass()
            boolean r0 = r7 instanceof com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$registerViewListenersWhileAttached$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$registerViewListenersWhileAttached$1 r0 = (com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$registerViewListenersWhileAttached$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$registerViewListenersWhileAttached$1 r0 = new com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$registerViewListenersWhileAttached$1
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
            com.android.systemui.statusbar.NotificationShelf r5 = (com.android.systemui.statusbar.NotificationShelf) r5
            kotlin.ResultKt.throwOnFailure(r4)     // Catch: java.lang.Throwable -> L36
            goto L4e
        L36:
            r4 = move-exception
            goto L54
        L38:
            kotlin.ResultKt.throwOnFailure(r4)
            com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$registerViewListenersWhileAttached$2 r4 = new com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$registerViewListenersWhileAttached$2     // Catch: java.lang.Throwable -> L36
            r4.<init>()     // Catch: java.lang.Throwable -> L36
            r5.setOnClickListener(r4)     // Catch: java.lang.Throwable -> L36
            r0.L$0 = r5     // Catch: java.lang.Throwable -> L36
            r0.label = r2     // Catch: java.lang.Throwable -> L36
            kotlin.coroutines.intrinsics.CoroutineSingletons r4 = kotlinx.coroutines.DelayKt.awaitCancellation(r0)     // Catch: java.lang.Throwable -> L36
            if (r4 != r7) goto L4e
            return r7
        L4e:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L36
            r4.<init>()     // Catch: java.lang.Throwable -> L36
            throw r4     // Catch: java.lang.Throwable -> L36
        L54:
            r6 = 0
            r5.setOnClickListener(r6)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder.access$registerViewListenersWhileAttached(com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder, com.android.systemui.statusbar.NotificationShelf, com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel, kotlin.coroutines.Continuation):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    public static Object bind(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, FalsingManager falsingManager, NotificationIconAreaController notificationIconAreaController, NotificationIconContainerShelfViewBinder notificationIconContainerShelfViewBinder, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new NotificationShelfViewBinder$bind$2(notificationShelfViewModel, notificationShelf, falsingManager, notificationIconAreaController, notificationIconContainerShelfViewBinder, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }
}
