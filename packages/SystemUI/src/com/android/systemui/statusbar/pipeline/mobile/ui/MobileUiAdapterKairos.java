package com.android.systemui.statusbar.pipeline.mobile.ui;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.KairosActivatable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.KairosCoroutineScope;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModelKairos;
import java.io.PrintWriter;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final class MobileUiAdapterKairos implements KairosActivatable, Dumpable {
    public final StatusBarIconController iconController;
    public boolean isCollecting;
    public List lastValue;
    public final MobileViewLogger logger;
    public final MobileIconsViewModelKairos mobileIconsViewModel;

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapterKairos$activate$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MobileUiAdapterKairos.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((KairosCoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final MobileUiAdapterKairos mobileUiAdapterKairos = MobileUiAdapterKairos.this;
                mobileUiAdapterKairos.isCollecting = true;
                Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapterKairos$activate$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        mobileUiAdapterKairos.isCollecting = false;
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (BuildScopeKt.awaitClose(function0, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    public MobileUiAdapterKairos(StatusBarIconController statusBarIconController, MobileIconsViewModelKairos mobileIconsViewModelKairos, MobileViewLogger mobileViewLogger, DumpManager dumpManager) {
        this.iconController = statusBarIconController;
        this.mobileIconsViewModel = mobileIconsViewModelKairos;
        this.logger = mobileViewLogger;
        dumpManager.registerNormalDumpable(this);
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        BuildScopeKt.launchEffect(buildScope, new AnonymousClass1(null));
        MobileIconsViewModelKairos mobileIconsViewModelKairos = this.mobileIconsViewModel;
        ((BuildScopeImpl) buildScope).observe(CombineKt.combine(mobileIconsViewModelKairos.subscriptionIds, ((MobileIconsInteractorKairosImpl) mobileIconsViewModelKairos.interactor).isStackable, new MobileUiAdapterKairos$$ExternalSyntheticLambda0()), new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapterKairos$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Pair pair = (Pair) obj2;
                List list = (List) pair.component1();
                boolean zBooleanValue = ((Boolean) pair.component2()).booleanValue();
                MobileUiAdapterKairos mobileUiAdapterKairos = this.f$0;
                mobileUiAdapterKairos.logger.logUiAdapterSubIdsSentToIconController(list, zBooleanValue);
                mobileUiAdapterKairos.lastValue = list;
                StatusBarIconController statusBarIconController = mobileUiAdapterKairos.iconController;
                if (zBooleanValue) {
                    ((StatusBarIconControllerImpl) statusBarIconController).setNewMobileIconSubIds(EmptyList.INSTANCE);
                } else {
                    ((StatusBarIconControllerImpl) statusBarIconController).setNewMobileIconSubIds(list);
                }
                return Unit.INSTANCE;
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "isCollecting=", this.isCollecting);
        printWriter.println("Last values sent to icon controller: " + this.lastValue);
    }
}
