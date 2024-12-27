package com.android.systemui.statusbar.notification.stack;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

final class NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ float $shelfHeight;
    final /* synthetic */ NotificationStackScrollLayout $stack;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    Object L$7;
    Object L$8;
    boolean Z$0;
    int label;
    final /* synthetic */ NotificationStackSizeCalculator this$0;

    public NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1(NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationStackScrollLayout notificationStackScrollLayout, float f, Continuation continuation) {
        super(2, continuation);
        this.this$0 = notificationStackSizeCalculator;
        this.$stack = notificationStackScrollLayout;
        this.$shelfHeight = f;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1 notificationStackSizeCalculator$computeHeightPerNotificationLimit$1 = new NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1(this.this$0, this.$stack, this.$shelfHeight, continuation);
        notificationStackSizeCalculator$computeHeightPerNotificationLimit$1.L$0 = obj;
        return notificationStackSizeCalculator$computeHeightPerNotificationLimit$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0167, code lost:
    
        if (r10.intValue() == 1) goto L52;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r23) {
        /*
            Method dump skipped, instructions count: 510
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
