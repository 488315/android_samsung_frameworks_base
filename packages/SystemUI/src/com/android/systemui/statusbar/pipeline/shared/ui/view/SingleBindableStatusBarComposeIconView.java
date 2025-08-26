package com.android.systemui.statusbar.pipeline.shared.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import androidx.compose.ui.platform.ComposeView;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class SingleBindableStatusBarComposeIconView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);
    public ComposeView composeView;
    public StatusBarIconView dotView;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2] */
        public static SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2 withDefaultBinding(SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, final StackedMobileIconBinder$$ExternalSyntheticLambda0 stackedMobileIconBinder$$ExternalSyntheticLambda0, Function4 function4) {
            final StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(2);
            final StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(-1);
            final StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(-1);
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            RepeatWhenAttachedKt.repeatWhenAttached(singleBindableStatusBarComposeIconView, EmptyCoroutineContext.INSTANCE, new SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1(function4, singleBindableStatusBarComposeIconView, stateFlowImplMutableStateFlow2, ref$BooleanRef, stateFlowImplMutableStateFlow, stateFlowImplMutableStateFlow3, null));
            return new ModernStatusBarViewBinding() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2
                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final boolean getShouldIconBeVisible() {
                    return ((Boolean) stackedMobileIconBinder$$ExternalSyntheticLambda0.invoke()).booleanValue();
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final boolean isCollecting() {
                    return ref$BooleanRef.element;
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final void onDecorTintChanged(int i) {
                    stateFlowImplMutableStateFlow3.setValue(Integer.valueOf(i));
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final void onIconTintChanged(int i, int i2) {
                    stateFlowImplMutableStateFlow2.setValue(Integer.valueOf(i));
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final void onVisibilityStateChanged(int i) {
                    stateFlowImplMutableStateFlow.setValue(Integer.valueOf(i));
                }
            };
        }

        private Companion() {
        }
    }

    public SingleBindableStatusBarComposeIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView
    public final void initView(String str, Function0 function0) {
        super.initView(str, function0);
        this.composeView = (ComposeView) requireViewById(R.id.compose_view);
        this.dotView = (StatusBarIconView) requireViewById(R.id.status_bar_dot);
    }

    @Override // android.view.View
    public final String toString() {
        String str = this.slot;
        if (str == null) {
            str = null;
        }
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        return MutablePreferences$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("SingleBindableStatusBarComposeIcon(slot='", str, "', isCollecting=", ", visibleState=", (modernStatusBarViewBinding != null ? modernStatusBarViewBinding : null).isCollecting()), StatusBarIconView.getVisibleStateString(this.iconVisibleState), "); viewString=", super.toString());
    }
}
