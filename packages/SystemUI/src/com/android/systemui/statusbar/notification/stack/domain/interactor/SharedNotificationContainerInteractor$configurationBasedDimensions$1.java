package com.android.systemui.statusbar.notification.stack.domain.interactor;

import android.content.res.Resources;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import dagger.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
final class SharedNotificationContainerInteractor$configurationBasedDimensions$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ Lazy $largeScreenHeaderHelperLazy;
    /* synthetic */ int I$0;
    int label;
    final /* synthetic */ SharedNotificationContainerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerInteractor$configurationBasedDimensions$1(SharedNotificationContainerInteractor sharedNotificationContainerInteractor, Lazy lazy, Continuation continuation) {
        super(4, continuation);
        this.this$0 = sharedNotificationContainerInteractor;
        this.$largeScreenHeaderHelperLazy = lazy;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int iIntValue = ((Number) obj2).intValue();
        ((Number) obj3).intValue();
        SharedNotificationContainerInteractor$configurationBasedDimensions$1 sharedNotificationContainerInteractor$configurationBasedDimensions$1 = new SharedNotificationContainerInteractor$configurationBasedDimensions$1(this.this$0, this.$largeScreenHeaderHelperLazy, (Continuation) obj4);
        sharedNotificationContainerInteractor$configurationBasedDimensions$1.I$0 = iIntValue;
        return sharedNotificationContainerInteractor$configurationBasedDimensions$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        SplitShadeStateController splitShadeStateController = (SplitShadeStateController) this.this$0.splitShadeStateController.get();
        this.this$0.context.getResources();
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        Resources resources = this.this$0.context.getResources();
        return new SharedNotificationContainerInteractor.ConfigurationBasedDimensions(false, resources.getBoolean(R.bool.config_use_large_screen_shade_header), resources.getDimensionPixelSize(R.dimen.notification_panel_margin_horizontal), i, resources.getDimensionPixelSize(R.dimen.notification_panel_margin_top), ((LargeScreenHeaderHelper) this.$largeScreenHeaderHelperLazy.get()).getLargeScreenHeaderHeight(), resources.getDimensionPixelSize(R.dimen.keyguard_split_shade_top_margin), ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(this.this$0.context), ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQsFrameX(), (SecQsUiDisplayModeInteractor.UiDisplayMode) ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).getUiDisplayMode().getValue());
    }
}
