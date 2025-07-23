package com.android.systemui.statusbar.notification.icon.domain.interactor;

import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationIconsInteractor$filteredNotifSet$1 extends SuspendLambda implements Function5 {
    final /* synthetic */ boolean $forceShowHeadsUp;
    final /* synthetic */ boolean $showAmbient;
    final /* synthetic */ boolean $showDismissed;
    final /* synthetic */ boolean $showLowPriority;
    final /* synthetic */ boolean $showPulsing;
    final /* synthetic */ boolean $showRepliedMessages;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ NotificationIconsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationIconsInteractor$filteredNotifSet$1(NotificationIconsInteractor notificationIconsInteractor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Continuation continuation) {
        super(5, continuation);
        this.this$0 = notificationIconsInteractor;
        this.$forceShowHeadsUp = z;
        this.$showAmbient = z2;
        this.$showLowPriority = z3;
        this.$showDismissed = z4;
        this.$showRepliedMessages = z5;
        this.$showPulsing = z6;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj4).booleanValue();
        NotificationIconsInteractor$filteredNotifSet$1 notificationIconsInteractor$filteredNotifSet$1 = new NotificationIconsInteractor$filteredNotifSet$1(this.this$0, this.$forceShowHeadsUp, this.$showAmbient, this.$showLowPriority, this.$showDismissed, this.$showRepliedMessages, this.$showPulsing, (Continuation) obj5);
        notificationIconsInteractor$filteredNotifSet$1.L$0 = (List) obj;
        notificationIconsInteractor$filteredNotifSet$1.L$1 = (String) obj2;
        notificationIconsInteractor$filteredNotifSet$1.L$2 = (String) obj3;
        notificationIconsInteractor$filteredNotifSet$1.Z$0 = booleanValue;
        return notificationIconsInteractor$filteredNotifSet$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        final String str = (String) this.L$1;
        final String str2 = (String) this.L$2;
        final boolean z = this.Z$0;
        CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 = new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list);
        final NotificationIconsInteractor notificationIconsInteractor = this.this$0;
        final boolean z2 = this.$forceShowHeadsUp;
        final boolean z3 = this.$showAmbient;
        final boolean z4 = this.$showLowPriority;
        final boolean z5 = this.$showDismissed;
        final boolean z6 = this.$showRepliedMessages;
        final boolean z7 = this.$showPulsing;
        return SequencesKt___SequencesKt.toSet(SequencesKt___SequencesKt.filter(collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1, new Function1() { // from class: com.android.systemui.statusbar.notification.icon.domain.interactor.NotificationIconsInteractor$filteredNotifSet$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                boolean z8;
                Bubbles bubbles;
                ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) obj2;
                NotificationIconsInteractor notificationIconsInteractor2 = NotificationIconsInteractor.this;
                notificationIconsInteractor2.getClass();
                boolean z9 = true;
                if ((!z2 || !Intrinsics.areEqual(activeNotificationModel.key, str)) && ((!(z8 = z3) && activeNotificationModel.isAmbient) || ((!z4 && activeNotificationModel.isSilent) || ((!z5 && activeNotificationModel.isRowDismissed) || ((!z6 && activeNotificationModel.isLastMessageFromReply) || ((!z8 && activeNotificationModel.isSuppressedFromStatusBar) || ((!z7 && activeNotificationModel.isPulsing && !z) || Intrinsics.areEqual(activeNotificationModel.key, str2) || ((bubbles = (Bubbles) notificationIconsInteractor2.bubbles.orElse(null)) != null && ((BubbleController.BubblesImpl) bubbles).isBubbleExpanded(activeNotificationModel.key))))))))) {
                    z9 = false;
                }
                return Boolean.valueOf(z9);
            }
        }));
    }
}
