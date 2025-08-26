package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1 implements Flow {
    public final /* synthetic */ Function1 $onUserSwitcherClicked$inlined;
    public final /* synthetic */ Context $themedContext$inlined;
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

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
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel] */
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
                UserSwitcherStatusModel userSwitcherStatusModel = (UserSwitcherStatusModel) obj;
                if (!Intrinsics.areEqual(userSwitcherStatusModel, UserSwitcherStatusModel.Disabled.INSTANCE)) {
                    if (!(userSwitcherStatusModel instanceof UserSwitcherStatusModel.Enabled)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    UserSwitcherStatusModel.Enabled enabled = (UserSwitcherStatusModel.Enabled) userSwitcherStatusModel;
                    Drawable drawable = enabled.currentUserImage;
                    if (drawable == null) {
                        Log.e("FooterActionsViewModel", "Skipped the addition of user switcher button because currentUserImage is missing");
                    } else {
                        Context context = this.$themedContext$inlined;
                        drawable.getClass();
                        String str = enabled.currentUserName;
                        footerActionsButtonViewModel = new FooterActionsButtonViewModel(R.id.multi_user_switch, new Icon.Loaded(drawable, new ContentDescription.Loaded(str != null ? context.getString(R.string.accessibility_quick_settings_user, str) : null), null, 4, null), null, R.attr.shadeInactive, this.$onUserSwitcherClicked$inlined);
                    }
                }
                anonymousClass1.label = 1;
                if (this.$this_unsafeFlow.emit(footerActionsButtonViewModel, anonymousClass1) == coroutineSingletons) {
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

    public FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1(Flow flow, Context context, Function1 function1) {
        this.$this_unsafeTransform$inlined = flow;
        this.$themedContext$inlined = context;
        this.$onUserSwitcherClicked$inlined = function1;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.$themedContext$inlined, this.$onUserSwitcherClicked$inlined), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
