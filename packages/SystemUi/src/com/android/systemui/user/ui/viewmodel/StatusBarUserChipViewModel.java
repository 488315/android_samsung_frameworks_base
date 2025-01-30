package com.android.systemui.user.ui.viewmodel;

import android.content.Context;
import android.content.pm.UserInfo;
import com.android.systemui.animation.Expandable;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.UserInteractor;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarUserChipViewModel {
    public final boolean chipEnabled;
    public final Flow isChipVisible;
    public final Function1 onClick;
    public final ChannelFlowTransformLatest userAvatar;
    public final ChannelFlowTransformLatest userCount;
    public final ChannelFlowTransformLatest userName;

    public StatusBarUserChipViewModel(Context context, final UserInteractor userInteractor) {
        boolean z = userInteractor.isStatusBarUserChipEnabled;
        this.chipEnabled = z;
        this.isChipVisible = !z ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : FlowKt.mapLatest(userInteractor.getUsers(), new StatusBarUserChipViewModel$isChipVisible$1(null));
        UserRepository userRepository = userInteractor.repository;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = ((UserRepositoryImpl) userRepository).selectedUserInfo;
        this.userName = FlowKt.mapLatest(new Flow() { // from class: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$3$2 */
            public final class C35512 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserInteractor this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$3$2", m277f = "UserInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getForceAutoShutDownState, IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    int I$0;
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C35512.this.emit(null, this);
                    }
                }

                public C35512(FlowCollector flowCollector, UserInteractor userInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:20:0x009a A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:24:0x008f A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:25:0x0056  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    CoroutineSingletons coroutineSingletons;
                    int i;
                    UserInfo userInfo;
                    int i2;
                    Object canSwitchUsers;
                    UserInteractor userInteractor;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i3 = anonymousClass1.label;
                        if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            obj2 = anonymousClass1.result;
                            coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                userInfo = (UserInfo) obj;
                                i2 = userInfo.id;
                                FlowCollector flowCollector2 = this.$this_unsafeFlow;
                                anonymousClass1.L$0 = flowCollector2;
                                anonymousClass1.L$1 = userInfo;
                                UserInteractor userInteractor2 = this.this$0;
                                anonymousClass1.L$2 = userInteractor2;
                                anonymousClass1.I$0 = i2;
                                anonymousClass1.label = 1;
                                int i4 = UserInteractor.$r8$clinit;
                                canSwitchUsers = userInteractor2.canSwitchUsers(i2, anonymousClass1, false);
                                if (canSwitchUsers == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                userInteractor = userInteractor2;
                                flowCollector = flowCollector2;
                            } else {
                                if (i != 1) {
                                    if (i != 2) {
                                        if (i != 3) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj2);
                                        return Unit.INSTANCE;
                                    }
                                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                                    ResultKt.throwOnFailure(obj2);
                                    anonymousClass1.L$0 = null;
                                    anonymousClass1.label = 3;
                                    if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                    return Unit.INSTANCE;
                                }
                                int i5 = anonymousClass1.I$0;
                                UserInteractor userInteractor3 = (UserInteractor) anonymousClass1.L$2;
                                userInfo = (UserInfo) anonymousClass1.L$1;
                                FlowCollector flowCollector3 = (FlowCollector) anonymousClass1.L$0;
                                ResultKt.throwOnFailure(obj2);
                                i2 = i5;
                                flowCollector = flowCollector3;
                                canSwitchUsers = obj2;
                                userInteractor = userInteractor3;
                            }
                            boolean booleanValue = ((Boolean) canSwitchUsers).booleanValue();
                            anonymousClass1.L$0 = flowCollector;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.L$2 = null;
                            anonymousClass1.label = 2;
                            int i6 = UserInteractor.$r8$clinit;
                            obj2 = userInteractor.toUserModel(userInfo, i2, booleanValue, anonymousClass1);
                            if (obj2 == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            anonymousClass1.L$0 = null;
                            anonymousClass1.label = 3;
                            if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    obj2 = anonymousClass1.result;
                    coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    boolean booleanValue2 = ((Boolean) canSwitchUsers).booleanValue();
                    anonymousClass1.L$0 = flowCollector;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.L$2 = null;
                    anonymousClass1.label = 2;
                    int i62 = UserInteractor.$r8$clinit;
                    obj2 = userInteractor.toUserModel(userInfo, i2, booleanValue2, anonymousClass1);
                    if (obj2 == coroutineSingletons) {
                    }
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 3;
                    if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C35512(flowCollector, userInteractor), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new StatusBarUserChipViewModel$userName$1(null));
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$12 = ((UserRepositoryImpl) userRepository).selectedUserInfo;
        this.userAvatar = FlowKt.mapLatest(new Flow() { // from class: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$3$2 */
            public final class C35512 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserInteractor this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$3$2", m277f = "UserInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getForceAutoShutDownState, IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.user.domain.interactor.UserInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    int I$0;
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C35512.this.emit(null, this);
                    }
                }

                public C35512(FlowCollector flowCollector, UserInteractor userInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:20:0x009a A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:24:0x008f A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:25:0x0056  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object obj2;
                    CoroutineSingletons coroutineSingletons;
                    int i;
                    UserInfo userInfo;
                    int i2;
                    Object canSwitchUsers;
                    UserInteractor userInteractor;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i3 = anonymousClass1.label;
                        if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            obj2 = anonymousClass1.result;
                            coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                userInfo = (UserInfo) obj;
                                i2 = userInfo.id;
                                FlowCollector flowCollector2 = this.$this_unsafeFlow;
                                anonymousClass1.L$0 = flowCollector2;
                                anonymousClass1.L$1 = userInfo;
                                UserInteractor userInteractor2 = this.this$0;
                                anonymousClass1.L$2 = userInteractor2;
                                anonymousClass1.I$0 = i2;
                                anonymousClass1.label = 1;
                                int i4 = UserInteractor.$r8$clinit;
                                canSwitchUsers = userInteractor2.canSwitchUsers(i2, anonymousClass1, false);
                                if (canSwitchUsers == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                userInteractor = userInteractor2;
                                flowCollector = flowCollector2;
                            } else {
                                if (i != 1) {
                                    if (i != 2) {
                                        if (i != 3) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj2);
                                        return Unit.INSTANCE;
                                    }
                                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                                    ResultKt.throwOnFailure(obj2);
                                    anonymousClass1.L$0 = null;
                                    anonymousClass1.label = 3;
                                    if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                    return Unit.INSTANCE;
                                }
                                int i5 = anonymousClass1.I$0;
                                UserInteractor userInteractor3 = (UserInteractor) anonymousClass1.L$2;
                                userInfo = (UserInfo) anonymousClass1.L$1;
                                FlowCollector flowCollector3 = (FlowCollector) anonymousClass1.L$0;
                                ResultKt.throwOnFailure(obj2);
                                i2 = i5;
                                flowCollector = flowCollector3;
                                canSwitchUsers = obj2;
                                userInteractor = userInteractor3;
                            }
                            boolean booleanValue2 = ((Boolean) canSwitchUsers).booleanValue();
                            anonymousClass1.L$0 = flowCollector;
                            anonymousClass1.L$1 = null;
                            anonymousClass1.L$2 = null;
                            anonymousClass1.label = 2;
                            int i62 = UserInteractor.$r8$clinit;
                            obj2 = userInteractor.toUserModel(userInfo, i2, booleanValue2, anonymousClass1);
                            if (obj2 == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            anonymousClass1.L$0 = null;
                            anonymousClass1.label = 3;
                            if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    obj2 = anonymousClass1.result;
                    coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    boolean booleanValue22 = ((Boolean) canSwitchUsers).booleanValue();
                    anonymousClass1.L$0 = flowCollector;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.L$2 = null;
                    anonymousClass1.label = 2;
                    int i622 = UserInteractor.$r8$clinit;
                    obj2 = userInteractor.toUserModel(userInfo, i2, booleanValue22, anonymousClass1);
                    if (obj2 == coroutineSingletons) {
                    }
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 3;
                    if (flowCollector.emit(obj2, anonymousClass1) == coroutineSingletons) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C35512(flowCollector, userInteractor), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new StatusBarUserChipViewModel$userAvatar$1(null));
        this.onClick = new Function1() { // from class: com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel$onClick$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                UserInteractor.this.showUserSwitcher((Expandable) obj);
                return Unit.INSTANCE;
            }
        };
        this.userCount = FlowKt.mapLatest(userInteractor.getUsers(), new StatusBarUserChipViewModel$userCount$1(null));
    }
}
