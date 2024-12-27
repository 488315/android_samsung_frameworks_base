package com.android.systemui.qs.footer.ui.viewmodel;

import android.view.ContextThemeWrapper;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.util.PluralMessageFormaterKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

final class FooterActionsViewModelKt$FooterActionsViewModel$foregroundServices$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ ActivityStarter $activityStarter;
    final /* synthetic */ FalsingManager $falsingManager;
    final /* synthetic */ FooterActionsInteractor $footerActionsInteractor;
    final /* synthetic */ ContextThemeWrapper $qsThemedContext;
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$foregroundServices$1$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        final /* synthetic */ ActivityStarter $activityStarter;
        final /* synthetic */ FalsingManager $falsingManager;
        final /* synthetic */ FooterActionsInteractor $footerActionsInteractor;

        public AnonymousClass1(FalsingManager falsingManager, ActivityStarter activityStarter, FooterActionsInteractor footerActionsInteractor) {
            super(1, Intrinsics.Kotlin.class, "onForegroundServiceButtonClicked", "FooterActionsViewModel$onForegroundServiceButtonClicked(Lcom/android/systemui/plugins/FalsingManager;Lcom/android/systemui/plugins/ActivityStarter;Lcom/android/systemui/qs/footer/domain/interactor/FooterActionsInteractor;Lcom/android/systemui/animation/Expandable;)V", 0);
            this.$falsingManager = falsingManager;
            this.$activityStarter = activityStarter;
            this.$footerActionsInteractor = footerActionsInteractor;
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            final Expandable expandable = (Expandable) obj;
            FalsingManager falsingManager = this.$falsingManager;
            ActivityStarter activityStarter = this.$activityStarter;
            final FooterActionsInteractor footerActionsInteractor = this.$footerActionsInteractor;
            if (!falsingManager.isFalseTap(1)) {
                activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction(expandable) { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$FooterActionsViewModel$onForegroundServiceButtonClicked$1
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        FooterActionsInteractor.this.getClass();
                        return false;
                    }
                }, null, true);
            }
            return Unit.INSTANCE;
        }
    }

    public FooterActionsViewModelKt$FooterActionsViewModel$foregroundServices$1(ContextThemeWrapper contextThemeWrapper, FalsingManager falsingManager, ActivityStarter activityStarter, FooterActionsInteractor footerActionsInteractor, Continuation continuation) {
        super(4, continuation);
        this.$qsThemedContext = contextThemeWrapper;
        this.$falsingManager = falsingManager;
        this.$activityStarter = activityStarter;
        this.$footerActionsInteractor = footerActionsInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int intValue = ((Number) obj).intValue();
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        FooterActionsViewModelKt$FooterActionsViewModel$foregroundServices$1 footerActionsViewModelKt$FooterActionsViewModel$foregroundServices$1 = new FooterActionsViewModelKt$FooterActionsViewModel$foregroundServices$1(this.$qsThemedContext, this.$falsingManager, this.$activityStarter, this.$footerActionsInteractor, (Continuation) obj4);
        footerActionsViewModelKt$FooterActionsViewModel$foregroundServices$1.I$0 = intValue;
        footerActionsViewModelKt$FooterActionsViewModel$foregroundServices$1.Z$0 = booleanValue;
        footerActionsViewModelKt$FooterActionsViewModel$foregroundServices$1.L$0 = (FooterActionsSecurityButtonViewModel) obj3;
        return footerActionsViewModelKt$FooterActionsViewModel$foregroundServices$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        boolean z = this.Z$0;
        FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel = (FooterActionsSecurityButtonViewModel) this.L$0;
        if (i <= 0) {
            return null;
        }
        ContextThemeWrapper contextThemeWrapper = this.$qsThemedContext;
        return new FooterActionsForegroundServicesButtonViewModel(i, PluralMessageFormaterKt.icuMessageFormat(contextThemeWrapper.getResources(), R.string.fgs_manager_footer_label, i), footerActionsSecurityButtonViewModel == null, z, new AnonymousClass1(this.$falsingManager, this.$activityStarter, this.$footerActionsInteractor));
    }
}
