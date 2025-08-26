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

/* loaded from: classes2.dex */
final class FooterActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ ActivityStarter $activityStarter;
    final /* synthetic */ FalsingManager $falsingManager;
    final /* synthetic */ FooterActionsInteractor $footerActionsInteractor;
    final /* synthetic */ ContextThemeWrapper $qsThemedContext;
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    /* renamed from: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        final /* synthetic */ ActivityStarter $activityStarter;
        final /* synthetic */ FalsingManager $falsingManager;
        final /* synthetic */ FooterActionsInteractor $footerActionsInteractor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FalsingManager falsingManager, ActivityStarter activityStarter, FooterActionsInteractor footerActionsInteractor) {
            super(1, Intrinsics.Kotlin.class, "onForegroundServiceButtonClicked", "createFooterActionsViewModel$onForegroundServiceButtonClicked(Lcom/android/systemui/plugins/FalsingManager;Lcom/android/systemui/plugins/ActivityStarter;Lcom/android/systemui/qs/footer/domain/interactor/FooterActionsInteractor;Lcom/android/systemui/animation/Expandable;)V", 0);
            this.$falsingManager = falsingManager;
            this.$activityStarter = activityStarter;
            this.$footerActionsInteractor = footerActionsInteractor;
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            final Expandable expandable = (Expandable) obj;
            FalsingManager falsingManager = this.$falsingManager;
            ActivityStarter activityStarter = this.$activityStarter;
            final FooterActionsInteractor footerActionsInteractor = this.$footerActionsInteractor;
            if (!falsingManager.isFalseTap(1)) {
                activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction(expandable) { // from class: com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$createFooterActionsViewModel$onForegroundServiceButtonClicked$1
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        this.$footerActionsInteractor.getClass();
                        return false;
                    }
                }, null, true);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1(ContextThemeWrapper contextThemeWrapper, FalsingManager falsingManager, ActivityStarter activityStarter, FooterActionsInteractor footerActionsInteractor, Continuation continuation) {
        super(4, continuation);
        this.$qsThemedContext = contextThemeWrapper;
        this.$falsingManager = falsingManager;
        this.$activityStarter = activityStarter;
        this.$footerActionsInteractor = footerActionsInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int iIntValue = ((Number) obj).intValue();
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        FooterActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1 footerActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1 = new FooterActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1(this.$qsThemedContext, this.$falsingManager, this.$activityStarter, this.$footerActionsInteractor, (Continuation) obj4);
        footerActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1.I$0 = iIntValue;
        footerActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1.Z$0 = zBooleanValue;
        footerActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1.L$0 = (FooterActionsSecurityButtonViewModel) obj3;
        return footerActionsViewModelKt$createFooterActionsViewModel$foregroundServices$1.invokeSuspend(Unit.INSTANCE);
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
