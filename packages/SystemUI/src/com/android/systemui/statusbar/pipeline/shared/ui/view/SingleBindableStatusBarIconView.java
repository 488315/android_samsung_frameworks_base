package com.android.systemui.statusbar.pipeline.shared.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.datastore.preferences.core.MutablePreferences$toString$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SingleBindableStatusBarIconView extends ModernStatusBarView {
    public static final Companion Companion = new Companion(null);
    public StatusBarIconView dotView;
    public ImageView iconView;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        /* JADX WARN: Type inference failed for: r13v1, types: [com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$2] */
        public static SingleBindableStatusBarIconView$Companion$withDefaultBinding$2 withDefaultBinding(SingleBindableStatusBarIconView singleBindableStatusBarIconView, final Function0 function0, Function3 function3) {
            final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(2);
            final StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(-1);
            final StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(-1);
            final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
            RepeatWhenAttachedKt.repeatWhenAttached(singleBindableStatusBarIconView, EmptyCoroutineContext.INSTANCE, new SingleBindableStatusBarIconView$Companion$withDefaultBinding$1(function3, singleBindableStatusBarIconView, ref$BooleanRef, MutableStateFlow, MutableStateFlow2, MutableStateFlow3, null));
            return new SingleBindableStatusBarIconViewBinding() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$2
                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final boolean getShouldIconBeVisible() {
                    return ((Boolean) function0.invoke()).booleanValue();
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final boolean isCollecting() {
                    return ref$BooleanRef.element;
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final void onDecorTintChanged(int i) {
                    ((StateFlowImpl) MutableStateFlow.this).updateState(null, Integer.valueOf(i));
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final void onIconTintChanged(int i, int i2) {
                    ((StateFlowImpl) MutableStateFlow2).updateState(null, Integer.valueOf(i));
                }

                @Override // com.android.systemui.statusbar.pipeline.shared.ui.binder.ModernStatusBarViewBinding
                public final void onVisibilityStateChanged(int i) {
                    ((StateFlowImpl) MutableStateFlow).updateState(null, Integer.valueOf(i));
                }
            };
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SingleBindableStatusBarIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView
    public final void initView(String str, Function0 function0) {
        super.initView(str, function0);
        this.iconView = (ImageView) requireViewById(R.id.icon_view);
        this.dotView = (StatusBarIconView) requireViewById(R.id.status_bar_dot);
    }

    @Override // android.view.View
    public final String toString() {
        ModernStatusBarViewBinding modernStatusBarViewBinding = this.binding;
        if (modernStatusBarViewBinding == null) {
            modernStatusBarViewBinding = null;
        }
        Boolean valueOf = modernStatusBarViewBinding != null ? Boolean.valueOf(modernStatusBarViewBinding.isCollecting()) : null;
        String slot = getSlot();
        String visibleStateString = StatusBarIconView.getVisibleStateString(this.iconVisibleState);
        String frameLayout = super.toString();
        StringBuilder sb = new StringBuilder("SingleBindableStatusBarIcon(slot='");
        sb.append(slot);
        sb.append("', isCollecting=");
        sb.append(valueOf);
        sb.append(", visibleState=");
        return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(sb, visibleStateString, "); viewString=", frameLayout);
    }
}
