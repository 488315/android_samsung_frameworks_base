package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationListViewBinder$bindSilentHeaderClickListener$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ StateFlow $hasNonClearableSilentNotifications;
    final /* synthetic */ NotificationStackScrollLayout $parentView;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NotificationListViewBinder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationListViewBinder$bindSilentHeaderClickListener$2(NotificationListViewBinder notificationListViewBinder, NotificationStackScrollLayout notificationStackScrollLayout, StateFlow stateFlow, Continuation continuation) {
        super(2, continuation);
        this.this$0 = notificationListViewBinder;
        this.$parentView = notificationStackScrollLayout;
        this.$hasNonClearableSilentNotifications = stateFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationListViewBinder$bindSilentHeaderClickListener$2 notificationListViewBinder$bindSilentHeaderClickListener$2 = new NotificationListViewBinder$bindSilentHeaderClickListener$2(this.this$0, this.$parentView, this.$hasNonClearableSilentNotifications, continuation);
        notificationListViewBinder$bindSilentHeaderClickListener$2.L$0 = obj;
        return notificationListViewBinder$bindSilentHeaderClickListener$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationListViewBinder$bindSilentHeaderClickListener$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0058, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r7) == r0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005a, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0031, code lost:
    
        if (r8 == r0) goto L21;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1e
            if (r1 == r3) goto L1a
            if (r1 == r2) goto L14
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L14:
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L18
            goto L5b
        L18:
            r8 = move-exception
            goto L61
        L1a:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L34
        L1e:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.CoroutineScope r8 = (kotlinx.coroutines.CoroutineScope) r8
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r1 = r7.this$0
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel r1 = r1.viewModel
            kotlinx.coroutines.flow.Flow r1 = r1.hasClearableAlertingNotifications
            r7.label = r3
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.stateIn(r1, r8, r7)
            if (r8 != r0) goto L34
            goto L5a
        L34:
            kotlinx.coroutines.flow.StateFlow r8 = (kotlinx.coroutines.flow.StateFlow) r8
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r1 = r7.this$0
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r3 = r1.silentHeaderController
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$1 r4 = new com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$1
            com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout r5 = r7.$parentView
            kotlinx.coroutines.flow.StateFlow r6 = r7.$hasNonClearableSilentNotifications
            r4.<init>()
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl r3 = (com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl) r3
            r3.clearAllClickListener = r4
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r8 = r3._view
            if (r8 == 0) goto L52
            r8.mOnClearClickListener = r4
            android.widget.ImageView r8 = r8.mClearAllButton
            r8.setOnClickListener(r4)
        L52:
            r7.label = r2     // Catch: java.lang.Throwable -> L18
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = kotlinx.coroutines.DelayKt.awaitCancellation(r7)     // Catch: java.lang.Throwable -> L18
            if (r8 != r0) goto L5b
        L5a:
            return r0
        L5b:
            kotlin.KotlinNothingValueException r8 = new kotlin.KotlinNothingValueException     // Catch: java.lang.Throwable -> L18
            r8.<init>()     // Catch: java.lang.Throwable -> L18
            throw r8     // Catch: java.lang.Throwable -> L18
        L61:
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder r7 = r7.this$0
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderController r7 = r7.silentHeaderController
            com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2 r0 = new android.view.View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2.2
                static {
                    /*
                        com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2 r0 = new com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2
                        r0.<init>()
                        
                        // error: 0x0005: SPUT 
  (r0 I:com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2)
 com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2.2.INSTANCE com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2$2
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2.AnonymousClass2.<clinit>():void");
                }

                {
                    /*
                        r0 = this;
                        r0.<init>()
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2.AnonymousClass2.<init>():void");
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View r1) {
                    /*
                        r0 = this;
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2.AnonymousClass2.onClick(android.view.View):void");
                }
            }
            com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl r7 = (com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl) r7
            r7.clearAllClickListener = r0
            com.android.systemui.statusbar.notification.stack.SectionHeaderView r7 = r7._view
            if (r7 == 0) goto L76
            r7.mOnClearClickListener = r0
            android.widget.ImageView r7 = r7.mClearAllButton
            r7.setOnClickListener(r0)
        L76:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewbinder.NotificationListViewBinder$bindSilentHeaderClickListener$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
