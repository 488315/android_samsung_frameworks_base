package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class UserSwitcherInteractor$special$$inlined$map$3 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ UserSwitcherInteractor this$0;

    /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ UserSwitcherInteractor this$0;

        /* renamed from: com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass2.this.emit(null, this);
            }
        }

        public AnonymousClass2(FlowCollector flowCollector, UserSwitcherInteractor userSwitcherInteractor) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = userSwitcherInteractor;
        }

        /* JADX WARN: Code restructure failed: missing block: B:27:0x0099, code lost:
        
            if (r8.emit(r10, r0) != r1) goto L29;
         */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) throws Throwable {
            AnonymousClass1 anonymousClass1;
            UserInfo userInfo;
            FlowCollector flowCollector;
            Object objCanSwitchUsers;
            UserSwitcherInteractor userSwitcherInteractor;
            int i;
            FlowCollector flowCollector2;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i2 = anonymousClass1.label;
                if ((i2 & Integer.MIN_VALUE) != 0) {
                    anonymousClass1.label = i2 - Integer.MIN_VALUE;
                } else {
                    anonymousClass1 = new AnonymousClass1(continuation);
                }
            }
            Object obj2 = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i3 = anonymousClass1.label;
            if (i3 == 0) {
                ResultKt.throwOnFailure(obj2);
                userInfo = (UserInfo) obj;
                int i4 = userInfo.id;
                flowCollector = this.$this_unsafeFlow;
                anonymousClass1.L$0 = flowCollector;
                anonymousClass1.L$1 = userInfo;
                UserSwitcherInteractor userSwitcherInteractor2 = this.this$0;
                anonymousClass1.L$2 = userSwitcherInteractor2;
                anonymousClass1.I$0 = i4;
                anonymousClass1.label = 1;
                int i5 = UserSwitcherInteractor.$r8$clinit;
                objCanSwitchUsers = userSwitcherInteractor2.canSwitchUsers(i4, anonymousClass1, false);
                if (objCanSwitchUsers != coroutineSingletons) {
                    userSwitcherInteractor = userSwitcherInteractor2;
                    i = i4;
                }
                return coroutineSingletons;
            }
            if (i3 == 1) {
                i = anonymousClass1.I$0;
                userSwitcherInteractor = (UserSwitcherInteractor) anonymousClass1.L$2;
                userInfo = (UserInfo) anonymousClass1.L$1;
                FlowCollector flowCollector3 = (FlowCollector) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj2);
                objCanSwitchUsers = obj2;
                flowCollector = flowCollector3;
            } else {
                if (i3 != 2) {
                    if (i3 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj2);
                    return Unit.INSTANCE;
                }
                flowCollector2 = (FlowCollector) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj2);
                anonymousClass1.L$0 = null;
                anonymousClass1.label = 3;
            }
            boolean zBooleanValue = ((Boolean) objCanSwitchUsers).booleanValue();
            anonymousClass1.L$0 = flowCollector;
            anonymousClass1.L$1 = null;
            anonymousClass1.L$2 = null;
            anonymousClass1.label = 2;
            int i6 = UserSwitcherInteractor.$r8$clinit;
            Object userModel = userSwitcherInteractor.toUserModel(userInfo, i, zBooleanValue, anonymousClass1);
            if (userModel != coroutineSingletons) {
                FlowCollector flowCollector4 = flowCollector;
                obj2 = userModel;
                flowCollector2 = flowCollector4;
                anonymousClass1.L$0 = null;
                anonymousClass1.label = 3;
            }
            return coroutineSingletons;
        }
    }

    public UserSwitcherInteractor$special$$inlined$map$3(Flow flow, UserSwitcherInteractor userSwitcherInteractor) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = userSwitcherInteractor;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
