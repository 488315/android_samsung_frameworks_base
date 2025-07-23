package com.android.systemui.statusbar.pipeline.mobile.ui;

import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import java.util.List;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileUiAdapter$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MobileUiAdapter this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter$start$1$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        public AnonymousClass3() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Boolean bool = (Boolean) obj2;
            bool.booleanValue();
            return new Pair((List) obj, bool);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileUiAdapter$start$1(MobileUiAdapter mobileUiAdapter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileUiAdapter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MobileUiAdapter$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileUiAdapter$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MobileUiAdapter mobileUiAdapter = this.this$0;
            mobileUiAdapter.isCollecting = true;
            MobileIconsViewModel mobileIconsViewModel = mobileUiAdapter.mobileIconsViewModel;
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileIconsViewModel.subscriptionIdsFlow, mobileIconsViewModel.isStackable, AnonymousClass3.INSTANCE);
            final MobileUiAdapter mobileUiAdapter2 = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter$start$1.4
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Pair pair = (Pair) obj2;
                    List list = (List) pair.component1();
                    boolean booleanValue = ((Boolean) pair.component2()).booleanValue();
                    MobileUiAdapter mobileUiAdapter3 = MobileUiAdapter.this;
                    mobileUiAdapter3.logger.logUiAdapterSubIdsSentToIconController(list, booleanValue);
                    mobileUiAdapter3.lastValue = list;
                    StatusBarIconController statusBarIconController = mobileUiAdapter3.iconController;
                    if (booleanValue) {
                        ((StatusBarIconControllerImpl) statusBarIconController).setNewMobileIconSubIds(EmptyList.INSTANCE);
                    } else {
                        ((StatusBarIconControllerImpl) statusBarIconController).setNewMobileIconSubIds(list);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
