package com.android.systemui.statusbar.notification.stack;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ float $shelfHeight;
    final /* synthetic */ NotificationStackScrollLayout $stack;
    float F$0;
    float F$1;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* JADX WARN: Code restructure failed: missing block: B:43:0x01a6, code lost:
    
        if (r8.intValue() != 1) goto L45;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x024d  */
    /* JADX WARN: Type inference failed for: r19v1, types: [T, com.android.systemui.statusbar.notification.row.ExpandableView] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x015d -> B:7:0x0167). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x022f -> B:6:0x0235). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r25) {
        /*
            Method dump skipped, instructions count: 592
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$computeHeightPerNotificationLimit$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
