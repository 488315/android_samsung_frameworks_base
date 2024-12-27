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
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;

public final class NotificationIconsInteractor$filteredNotifSet$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ boolean $forceShowHeadsUp;
    final /* synthetic */ boolean $showAmbient;
    final /* synthetic */ boolean $showDismissed;
    final /* synthetic */ boolean $showLowPriority;
    final /* synthetic */ boolean $showPulsing;
    final /* synthetic */ boolean $showRepliedMessages;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ NotificationIconsInteractor this$0;

    public NotificationIconsInteractor$filteredNotifSet$1(NotificationIconsInteractor notificationIconsInteractor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Continuation continuation) {
        super(4, continuation);
        this.this$0 = notificationIconsInteractor;
        this.$forceShowHeadsUp = z;
        this.$showAmbient = z2;
        this.$showLowPriority = z3;
        this.$showDismissed = z4;
        this.$showRepliedMessages = z5;
        this.$showPulsing = z6;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        NotificationIconsInteractor$filteredNotifSet$1 notificationIconsInteractor$filteredNotifSet$1 = new NotificationIconsInteractor$filteredNotifSet$1(this.this$0, this.$forceShowHeadsUp, this.$showAmbient, this.$showLowPriority, this.$showDismissed, this.$showRepliedMessages, this.$showPulsing, (Continuation) obj4);
        notificationIconsInteractor$filteredNotifSet$1.L$0 = (List) obj;
        notificationIconsInteractor$filteredNotifSet$1.L$1 = (String) obj2;
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
        final boolean z = this.Z$0;
        CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1 = new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list);
        final NotificationIconsInteractor notificationIconsInteractor = this.this$0;
        final boolean z2 = this.$forceShowHeadsUp;
        final boolean z3 = this.$showAmbient;
        final boolean z4 = this.$showLowPriority;
        final boolean z5 = this.$showDismissed;
        final boolean z6 = this.$showRepliedMessages;
        final boolean z7 = this.$showPulsing;
        return SequencesKt___SequencesKt.toSet(SequencesKt___SequencesKt.filter(collectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1, new Function1() { // from class: com.android.systemui.statusbar.notification.icon.domain.interactor.NotificationIconsInteractor$filteredNotifSet$1.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                Bubbles bubbles;
                ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) obj2;
                NotificationIconsInteractor notificationIconsInteractor2 = NotificationIconsInteractor.this;
                boolean z8 = z2;
                boolean z9 = z3;
                boolean z10 = z4;
                boolean z11 = z5;
                boolean z12 = z6;
                boolean z13 = z7;
                String str2 = str;
                boolean z14 = z;
                notificationIconsInteractor2.getClass();
                String str3 = activeNotificationModel.key;
                boolean z15 = true;
                if ((!z8 || !Intrinsics.areEqual(str3, str2)) && ((!z9 && activeNotificationModel.isAmbient) || ((!z10 && activeNotificationModel.isSilent) || ((!z11 && activeNotificationModel.isRowDismissed) || ((!z12 && activeNotificationModel.isLastMessageFromReply) || ((!z9 && activeNotificationModel.isSuppressedFromStatusBar) || ((!z13 && activeNotificationModel.isPulsing && !z14) || ((bubbles = (Bubbles) notificationIconsInteractor2.bubbles.orElse(null)) != null && ((BubbleController.BubblesImpl) bubbles).isBubbleExpanded(str3))))))))) {
                    z15 = false;
                }
                return Boolean.valueOf(z15);
            }
        }));
    }
}
