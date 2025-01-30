package com.android.systemui.user.p035ui.viewmodel;

import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.user.domain.interactor.UserInteractor;
import com.android.systemui.user.legacyhelper.p034ui.LegacyUserUiHelper;
import com.android.systemui.user.shared.model.UserActionModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserSwitcherViewModel$special$$inlined$map$3 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ UserSwitcherViewModel this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3$2 */
    public final class C35712 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ UserSwitcherViewModel this$0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3$2", m277f = "UserSwitcherViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
        /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                return C35712.this.emit(null, this);
            }
        }

        public C35712(FlowCollector flowCollector, UserSwitcherViewModel userSwitcherViewModel) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = userSwitcherViewModel;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            int i;
            C35712 c35712 = this;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i2 = anonymousClass1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    int i3 = 1;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj2);
                        List list = (List) obj;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            final UserActionModel userActionModel = (UserActionModel) it.next();
                            final UserSwitcherViewModel userSwitcherViewModel = c35712.this$0;
                            userSwitcherViewModel.getClass();
                            long ordinal = userActionModel.ordinal();
                            LegacyUserUiHelper legacyUserUiHelper = LegacyUserUiHelper.INSTANCE;
                            UserActionModel userActionModel2 = UserActionModel.ADD_SUPERVISED_USER;
                            boolean z = userActionModel == userActionModel2 ? i3 : 0;
                            UserActionModel userActionModel3 = UserActionModel.ADD_USER;
                            boolean z2 = userActionModel == userActionModel3 ? i3 : 0;
                            UserActionModel userActionModel4 = UserActionModel.ENTER_GUEST_MODE;
                            Iterator it2 = it;
                            boolean z3 = userActionModel == userActionModel4 ? i3 : 0;
                            UserActionModel userActionModel5 = UserActionModel.NAVIGATE_TO_USER_MANAGEMENT;
                            CoroutineSingletons coroutineSingletons2 = coroutineSingletons;
                            int userSwitcherActionIconResourceId = LegacyUserUiHelper.getUserSwitcherActionIconResourceId(z2, z3, z, true, userActionModel == userActionModel5);
                            boolean z4 = userActionModel == userActionModel4;
                            GuestUserInteractor guestUserInteractor = userSwitcherViewModel.guestUserInteractor;
                            arrayList.add(new UserActionViewModel(ordinal, userSwitcherActionIconResourceId, LegacyUserUiHelper.getUserSwitcherActionTextResourceId(z4, guestUserInteractor.isGuestUserAutoCreated, guestUserInteractor.isGuestUserResetting, userActionModel == userActionModel3, userActionModel == userActionModel2, true, userActionModel == userActionModel5), new Function0() { // from class: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$toViewModel$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    UserInteractor userInteractor = UserSwitcherViewModel.this.userInteractor;
                                    UserActionModel userActionModel6 = userActionModel;
                                    int i4 = UserInteractor.$r8$clinit;
                                    userInteractor.executeAction(userActionModel6, null);
                                    if (userActionModel != UserActionModel.ADD_USER) {
                                        UserSwitcherViewModel.this.isFinishRequiredDueToExecutedAction.setValue(Boolean.TRUE);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }));
                            c35712 = this;
                            it = it2;
                            coroutineSingletons = coroutineSingletons2;
                            i3 = 1;
                        }
                        CoroutineSingletons coroutineSingletons3 = coroutineSingletons;
                        anonymousClass1.label = i3;
                        if (this.$this_unsafeFlow.emit(arrayList, anonymousClass1) == coroutineSingletons3) {
                            return coroutineSingletons3;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }
            anonymousClass1 = c35712.new AnonymousClass1(continuation);
            Object obj22 = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons4 = CoroutineSingletons.COROUTINE_SUSPENDED;
            i = anonymousClass1.label;
            int i32 = 1;
            if (i != 0) {
            }
            return Unit.INSTANCE;
        }
    }

    public UserSwitcherViewModel$special$$inlined$map$3(Flow flow, UserSwitcherViewModel userSwitcherViewModel) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = userSwitcherViewModel;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new C35712(flowCollector, this.this$0), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
