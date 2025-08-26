package com.android.systemui.user.ui.viewmodel;

import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.user.legacyhelper.ui.LegacyUserUiHelper;
import com.android.systemui.user.shared.model.UserActionModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class UserSwitcherViewModel$special$$inlined$map$4 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ UserSwitcherViewModel this$0;

    /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$4$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ UserSwitcherViewModel this$0;

        /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$4$2$1, reason: invalid class name */
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

        public AnonymousClass2(FlowCollector flowCollector, UserSwitcherViewModel userSwitcherViewModel) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = userSwitcherViewModel;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
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
            int i3 = 1;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                List list = (List) obj;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    UserActionModel userActionModel = (UserActionModel) it.next();
                    UserSwitcherViewModel userSwitcherViewModel = this.this$0;
                    userSwitcherViewModel.getClass();
                    long jOrdinal = userActionModel.ordinal();
                    LegacyUserUiHelper legacyUserUiHelper = LegacyUserUiHelper.INSTANCE;
                    UserActionModel userActionModel2 = UserActionModel.ADD_SUPERVISED_USER;
                    boolean z = userActionModel == userActionModel2 ? i3 : 0;
                    UserActionModel userActionModel3 = UserActionModel.SIGN_OUT;
                    boolean z2 = userActionModel == userActionModel3 ? i3 : 0;
                    UserActionModel userActionModel4 = UserActionModel.ADD_USER;
                    boolean z3 = userActionModel == userActionModel4 ? i3 : 0;
                    UserActionModel userActionModel5 = UserActionModel.ENTER_GUEST_MODE;
                    boolean z4 = userActionModel == userActionModel5;
                    UserActionModel userActionModel6 = UserActionModel.NAVIGATE_TO_USER_MANAGEMENT;
                    Iterator it2 = it;
                    int userSwitcherActionIconResourceId = LegacyUserUiHelper.getUserSwitcherActionIconResourceId(z3, z4, z, z2, true, userActionModel == userActionModel6);
                    boolean z5 = userActionModel == userActionModel5;
                    GuestUserInteractor guestUserInteractor = userSwitcherViewModel.guestUserInteractor;
                    arrayList.add(new UserActionViewModel(jOrdinal, userSwitcherActionIconResourceId, LegacyUserUiHelper.getUserSwitcherActionTextResourceId(z5, guestUserInteractor.isGuestUserAutoCreated, guestUserInteractor.isGuestUserResetting, userActionModel == userActionModel4, userActionModel == userActionModel2, userActionModel == userActionModel3, true, userActionModel == userActionModel6), new UserSwitcherViewModel$$ExternalSyntheticLambda0(userSwitcherViewModel, userActionModel, 0)));
                    it = it2;
                    i3 = 1;
                }
                anonymousClass1.label = i3;
                if (this.$this_unsafeFlow.emit(arrayList, anonymousClass1) == coroutineSingletons) {
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

    public UserSwitcherViewModel$special$$inlined$map$4(Flow flow, UserSwitcherViewModel userSwitcherViewModel) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = userSwitcherViewModel;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
