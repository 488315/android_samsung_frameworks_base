package com.android.systemui.statusbar.notification.promoted.domain.interactor;

import com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1 implements Flow {
    public final /* synthetic */ String $hostPackage$inlined;
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ PromotedNotificationsInteractor this$0;

    /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ String $hostPackage$inlined;
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ PromotedNotificationsInteractor this$0;

        /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            int label;
            /* synthetic */ Object result;

            public AnonymousClass1(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass2.this.emit(null, this);
            }
        }

        public AnonymousClass2(FlowCollector flowCollector, PromotedNotificationsInteractor promotedNotificationsInteractor, String str) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = promotedNotificationsInteractor;
            this.$hostPackage$inlined = str;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i = anonymousClass1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    anonymousClass1.label = i - Integer.MIN_VALUE;
                } else {
                    anonymousClass1 = new AnonymousClass1(continuation);
                }
            }
            Object obj2 = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = anonymousClass1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                Collection collectionValues = ((Map) obj).values();
                this.this$0.getClass();
                ArrayList arrayList = new ArrayList();
                for (Object obj3 : collectionValues) {
                    if (Intrinsics.areEqual(((ActiveNotificationModel) obj3).packageName, this.$hostPackage$inlined)) {
                        arrayList.add(obj3);
                    }
                }
                PromotedNotificationsInteractor.NotifAndPromotedContent notifAndPromotedContent = null;
                if (!arrayList.isEmpty()) {
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj4 : arrayList) {
                        if (((ActiveNotificationModel) obj4).isForegroundService) {
                            arrayList2.add(obj4);
                        }
                    }
                    ActiveNotificationModel activeNotificationModel = arrayList2.size() == 1 ? (ActiveNotificationModel) CollectionsKt___CollectionsKt.first((List) arrayList2) : null;
                    if (activeNotificationModel != null) {
                        notifAndPromotedContent = new PromotedNotificationsInteractor.NotifAndPromotedContent(activeNotificationModel.key, activeNotificationModel.promotedContent);
                    } else {
                        ArrayList arrayList3 = new ArrayList();
                        for (Object obj5 : arrayList) {
                            if (((ActiveNotificationModel) obj5).isOngoingEvent) {
                                arrayList3.add(obj5);
                            }
                        }
                        ActiveNotificationModel activeNotificationModel2 = arrayList3.size() == 1 ? (ActiveNotificationModel) CollectionsKt___CollectionsKt.first((List) arrayList3) : null;
                        if (activeNotificationModel2 != null) {
                            notifAndPromotedContent = new PromotedNotificationsInteractor.NotifAndPromotedContent(activeNotificationModel2.key, activeNotificationModel2.promotedContent);
                        } else {
                            ArrayList arrayList4 = new ArrayList();
                            for (Object obj6 : arrayList) {
                                ActiveNotificationModel activeNotificationModel3 = (ActiveNotificationModel) obj6;
                                if (activeNotificationModel3.isForegroundService && activeNotificationModel3.isOngoingEvent) {
                                    arrayList4.add(obj6);
                                }
                            }
                            ActiveNotificationModel activeNotificationModel4 = arrayList4.size() == 1 ? (ActiveNotificationModel) CollectionsKt___CollectionsKt.first((List) arrayList4) : null;
                            if (activeNotificationModel4 != null) {
                                notifAndPromotedContent = new PromotedNotificationsInteractor.NotifAndPromotedContent(activeNotificationModel4.key, activeNotificationModel4.promotedContent);
                            }
                        }
                    }
                }
                anonymousClass1.label = 1;
                if (this.$this_unsafeFlow.emit(notifAndPromotedContent, anonymousClass1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj2);
            }
            return Unit.INSTANCE;
        }
    }

    public PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1(Flow flow, PromotedNotificationsInteractor promotedNotificationsInteractor, String str) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = promotedNotificationsInteractor;
        this.$hostPackage$inlined = str;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0, this.$hostPackage$inlined), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
