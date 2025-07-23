package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1 implements Flow {
    public final /* synthetic */ Function1 $onUserSwitcherClicked$inlined;
    public final /* synthetic */ Context $themedContext$inlined;
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ Function1 $onUserSwitcherClicked$inlined;
        public final /* synthetic */ Context $themedContext$inlined;
        public final /* synthetic */ FlowCollector $this_unsafeFlow;

        /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1$2$1, reason: invalid class name */
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

        public AnonymousClass2(FlowCollector flowCollector, Context context, Function1 function1) {
            this.$this_unsafeFlow = flowCollector;
            this.$themedContext$inlined = context;
            this.$onUserSwitcherClicked$inlined = function1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel] */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
            /*
                r12 = this;
                boolean r0 = r14 instanceof com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r14
                com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1$2$1
                r0.<init>(r14)
            L18:
                java.lang.Object r14 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r14)
                goto L8c
            L27:
                java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                r12.<init>(r13)
                throw r12
            L2f:
                kotlin.ResultKt.throwOnFailure(r14)
                com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel r13 = (com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel) r13
                com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel$Disabled r14 = com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel.Disabled.INSTANCE
                boolean r14 = kotlin.jvm.internal.Intrinsics.areEqual(r13, r14)
                r2 = 0
                if (r14 == 0) goto L3e
                goto L81
            L3e:
                boolean r14 = r13 instanceof com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel.Enabled
                if (r14 == 0) goto L8f
                com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel$Enabled r13 = (com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel.Enabled) r13
                android.graphics.drawable.Drawable r5 = r13.currentUserImage
                if (r5 != 0) goto L50
                java.lang.String r13 = "FooterActionsViewModel"
                java.lang.String r14 = "Skipped the addition of user switcher button because currentUserImage is missing"
                android.util.Log.e(r13, r14)
                goto L81
            L50:
                android.content.Context r14 = r12.$themedContext$inlined
                r5.getClass()
                com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel r10 = new com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel
                com.android.systemui.common.shared.model.Icon$Loaded r4 = new com.android.systemui.common.shared.model.Icon$Loaded
                com.android.systemui.common.shared.model.ContentDescription$Loaded r6 = new com.android.systemui.common.shared.model.ContentDescription$Loaded
                java.lang.String r13 = r13.currentUserName
                if (r13 == 0) goto L6a
                r2 = 2131951929(0x7f130139, float:1.9540286E38)
                java.lang.Object[] r13 = new java.lang.Object[]{r13}
                java.lang.String r2 = r14.getString(r2, r13)
            L6a:
                r6.<init>(r2)
                r9 = 0
                r7 = 0
                r8 = 4
                r4.<init>(r5, r6, r7, r8, r9)
                r6 = r10
                r10 = 2130970109(0x7f0405fd, float:1.7548919E38)
                r7 = 2131363879(0x7f0a0827, float:1.834758E38)
                kotlin.jvm.functions.Function1 r11 = r12.$onUserSwitcherClicked$inlined
                r8 = r4
                r6.<init>(r7, r8, r9, r10, r11)
                r2 = r6
            L81:
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r12 = r12.$this_unsafeFlow
                java.lang.Object r12 = r12.emit(r2, r0)
                if (r12 != r1) goto L8c
                return r1
            L8c:
                kotlin.Unit r12 = kotlin.Unit.INSTANCE
                return r12
            L8f:
                kotlin.NoWhenBranchMatchedException r12 = new kotlin.NoWhenBranchMatchedException
                r12.<init>()
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1(Flow flow, Context context, Function1 function1) {
        this.$this_unsafeTransform$inlined = flow;
        this.$themedContext$inlined = context;
        this.$onUserSwitcherClicked$inlined = function1;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.$themedContext$inlined, this.$onUserSwitcherClicked$inlined), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
