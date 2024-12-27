package com.android.systemui.user.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.ui.drawable.CircularDrawable;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3;
import com.android.systemui.user.shared.model.UserModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UserSwitcherViewModel {
    public final StateFlowImpl _isMenuVisible;
    public final GuestUserInteractor guestUserInteractor;
    public final StateFlowImpl hasCancelButtonBeenClicked;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isFinishRequested;
    public final StateFlowImpl isFinishRequiredDueToExecutedAction;
    public final StateFlowImpl isMenuVisible;
    public final UserSwitcherViewModel$special$$inlined$map$5 isOpenMenuButtonVisible;
    public final UserSwitcherViewModel$special$$inlined$map$3 maximumUserColumns;
    public final UserSwitcherViewModel$special$$inlined$map$4 menu;
    public final UserSwitcherViewModel$special$$inlined$map$1 selectedUser;
    public final StateFlowImpl userSwitched;
    public final UserSwitcherInteractor userSwitcherInteractor;
    public final UserSwitcherViewModel$special$$inlined$map$2 users;

    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1] */
    public UserSwitcherViewModel(UserSwitcherInteractor userSwitcherInteractor, GuestUserInteractor guestUserInteractor) {
        this.userSwitcherInteractor = userSwitcherInteractor;
        this.guestUserInteractor = guestUserInteractor;
        final UserSwitcherInteractor$special$$inlined$map$3 userSwitcherInteractor$special$$inlined$map$3 = new UserSwitcherInteractor$special$$inlined$map$3(((UserRepositoryImpl) userSwitcherInteractor.repository).selectedUserInfo, userSwitcherInteractor);
        this.selectedUser = new Flow() { // from class: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserSwitcherViewModel this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.user.shared.model.UserModel r5 = (com.android.systemui.user.shared.model.UserModel) r5
                        com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel r6 = r4.this$0
                        com.android.systemui.user.ui.viewmodel.UserViewModel r5 = com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel.access$toViewModel(r6, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        UserSwitcherViewModel$special$$inlined$map$2 userSwitcherViewModel$special$$inlined$map$2 = new UserSwitcherViewModel$special$$inlined$map$2(userSwitcherInteractor.getUsers(), this);
        this.users = userSwitcherViewModel$special$$inlined$map$2;
        this.maximumUserColumns = new UserSwitcherViewModel$special$$inlined$map$3(userSwitcherViewModel$special$$inlined$map$2, this);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isMenuVisible = MutableStateFlow;
        this.isMenuVisible = MutableStateFlow;
        UserSwitcherViewModel$special$$inlined$map$4 userSwitcherViewModel$special$$inlined$map$4 = new UserSwitcherViewModel$special$$inlined$map$4(userSwitcherInteractor.getActions(), this);
        this.menu = userSwitcherViewModel$special$$inlined$map$4;
        this.isOpenMenuButtonVisible = new UserSwitcherViewModel$special$$inlined$map$5(userSwitcherViewModel$special$$inlined$map$4);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this.hasCancelButtonBeenClicked = MutableStateFlow2;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this.isFinishRequiredDueToExecutedAction = MutableStateFlow3;
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this.userSwitched = MutableStateFlow4;
        this.isFinishRequested = FlowKt.combine(MutableStateFlow2, MutableStateFlow3, MutableStateFlow4, new UserSwitcherViewModel$createFinishRequestedFlow$1(null));
    }

    public static final UserViewModel access$toViewModel(final UserSwitcherViewModel userSwitcherViewModel, final UserModel userModel) {
        userSwitcherViewModel.getClass();
        int i = userModel.id;
        Text resource = (userModel.isGuest && userModel.isSelected) ? new Text.Resource(R.string.guest_exit_quick_settings_button) : userModel.name;
        CircularDrawable circularDrawable = new CircularDrawable(userModel.image);
        boolean z = userModel.isSelectable;
        return new UserViewModel(i, resource, circularDrawable, userModel.isSelected, z ? 1.0f : 0.38f, !z ? null : new Function0() { // from class: com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$createOnSelectedCallback$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                UserSwitcherInteractor userSwitcherInteractor = UserSwitcherViewModel.this.userSwitcherInteractor;
                int i2 = userModel.id;
                int i3 = UserSwitcherInteractor.$r8$clinit;
                userSwitcherInteractor.selectUser(i2, null);
                UserSwitcherViewModel.this.userSwitched.updateState(null, Boolean.TRUE);
                return Unit.INSTANCE;
            }
        });
    }
}
