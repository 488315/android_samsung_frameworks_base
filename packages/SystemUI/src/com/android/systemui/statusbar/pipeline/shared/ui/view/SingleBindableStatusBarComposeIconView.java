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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SingleBindableStatusBarComposeIconView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);
    public ComposeView composeView;
    public StatusBarIconView dotView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2] */
        public static SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2 withDefaultBinding(SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, final StackedMobileIconBinder$$ExternalSyntheticLambda0 stackedMobileIconBinder$$ExternalSyntheticLambda0, Function4 function4) {
            final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(2);
            final StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(-1);
            final StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(-1);
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            RepeatWhenAttachedKt.repeatWhenAttached(singleBindableStatusBarComposeIconView, EmptyCoroutineContext.INSTANCE, new SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$1(function4, singleBindableStatusBarComposeIconView, MutableStateFlow2, ref$BooleanRef, MutableStateFlow, MutableStateFlow3, null));
            return new ModernStatusBarViewBinding() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2
                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final boolean getShouldIconBeVisible() {
                    return ((Boolean) Function0.this.invoke()).booleanValue();
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final boolean isCollecting() {
                    return ref$BooleanRef.element;
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final void onDecorTintChanged(int i) {
                    MutableStateFlow3.setValue(Integer.valueOf(i));
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final void onIconTintChanged(int i, int i2) {
                    MutableStateFlow2.setValue(Integer.valueOf(i));
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final void onVisibilityStateChanged(int i) {
                    MutableStateFlow.setValue(Integer.valueOf(i));
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
