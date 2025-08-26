package com.android.systemui.statusbar.pipeline.mobile.ui;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import java.io.PrintWriter;
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

/* loaded from: classes3.dex */
public final class MobileUiAdapter implements CoreStartable {
    public final StatusBarIconController iconController;
    public boolean isCollecting;
    public List lastValue;
    public final MobileViewLogger logger;
    public final MobileIconsViewModel mobileIconsViewModel;
    public final CoroutineScope scope;

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

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

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MobileUiAdapter.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MobileUiAdapter mobileUiAdapter = MobileUiAdapter.this;
                mobileUiAdapter.isCollecting = true;
                MobileIconsViewModel mobileIconsViewModel = mobileUiAdapter.mobileIconsViewModel;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(mobileIconsViewModel.subscriptionIdsFlow, mobileIconsViewModel.isStackable, AnonymousClass3.INSTANCE);
                final MobileUiAdapter mobileUiAdapter2 = MobileUiAdapter.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter.start.1.4
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        List list = (List) pair.component1();
                        boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
                        MobileUiAdapter mobileUiAdapter3 = mobileUiAdapter2;
                        mobileUiAdapter3.logger.logUiAdapterSubIdsSentToIconController(list, zBooleanValue);
                        mobileUiAdapter3.lastValue = list;
                        StatusBarIconController statusBarIconController = mobileUiAdapter3.iconController;
                        if (zBooleanValue) {
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

    public MobileUiAdapter(StatusBarIconController statusBarIconController, MobileIconsViewModel mobileIconsViewModel, MobileViewLogger mobileViewLogger, CoroutineScope coroutineScope) {
        this.iconController = statusBarIconController;
        this.mobileIconsViewModel = mobileIconsViewModel;
        this.logger = mobileViewLogger;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "isCollecting=", this.isCollecting);
        printWriter.println("Last values sent to icon controller: " + this.lastValue);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(null), 7);
    }
}
