package com.android.systemui.keyguard.ui.binder;

import android.util.Size;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class KeyguardSecBottomAreaViewBinder extends KeyguardBottomAreaViewBinder {
    public static final KeyguardSecBottomAreaViewBinder INSTANCE = new KeyguardSecBottomAreaViewBinder();

    private KeyguardSecBottomAreaViewBinder() {
    }

    public static final KeyguardSecBottomAreaViewBinder$bind$1 bind(KeyguardSecBottomAreaView keyguardSecBottomAreaView, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel) {
        View requireViewById = keyguardSecBottomAreaView.requireViewById(R.id.keyguard_indication_area);
        View findViewById = keyguardSecBottomAreaView.findViewById(R.id.ambient_indication_container);
        View requireViewById2 = keyguardSecBottomAreaView.requireViewById(R.id.left_shortcut_area);
        View requireViewById3 = keyguardSecBottomAreaView.requireViewById(R.id.right_shortcut_area);
        KeyguardSecAffordanceView keyguardSecAffordanceView = (KeyguardSecAffordanceView) keyguardSecBottomAreaView.requireViewById(R.id.start_button);
        KeyguardSecAffordanceView keyguardSecAffordanceView2 = (KeyguardSecAffordanceView) keyguardSecBottomAreaView.requireViewById(R.id.end_button);
        KeyguardIndicationTextView keyguardIndicationTextView = (KeyguardIndicationTextView) keyguardSecBottomAreaView.requireViewById(R.id.keyguard_upper_fingerprint_indication);
        LinearLayout linearLayout = (LinearLayout) keyguardSecBottomAreaView.requireViewById(R.id.usim_text_area);
        keyguardSecBottomAreaView.setClipChildren(false);
        keyguardSecBottomAreaView.setClipToPadding(false);
        ConfigurationBasedDimensions configurationBasedDimensions = new ConfigurationBasedDimensions(keyguardSecBottomAreaView.getResources().getDimensionPixelOffset(R.dimen.default_burn_in_prevention_offset), keyguardSecBottomAreaView.getResources().getDimensionPixelOffset(R.dimen.keyguard_indication_area_padding), 0, 0, 0, null, 0, 0, false, 508, null);
        keyguardSecBottomAreaView.updateShortcutPosition(configurationBasedDimensions);
        keyguardSecBottomAreaView.updateIndicationPosition(configurationBasedDimensions);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(configurationBasedDimensions);
        KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1 keyguardSecBottomAreaViewBinder$bind$disposableHandle$1 = new KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1(keyguardBottomAreaViewModel, keyguardSecAffordanceView, keyguardSecAffordanceView2, keyguardSecBottomAreaView, findViewById, requireViewById, MutableStateFlow, requireViewById2, requireViewById3, keyguardIndicationTextView, linearLayout, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        return new KeyguardSecBottomAreaViewBinder$bind$1(MutableStateFlow, keyguardSecBottomAreaView, keyguardBottomAreaViewModel, RepeatWhenAttachedKt.repeatWhenAttached(keyguardSecBottomAreaView, EmptyCoroutineContext.INSTANCE, keyguardSecBottomAreaViewBinder$bind$disposableHandle$1));
    }

    public final class ConfigurationBasedDimensions {
        public Size buttonSizePx;
        public final int defaultBurnInPreventionYOffsetPx;
        public int indicationAreaBottomMargin;
        public int indicationAreaSideMargin;
        public boolean isOverlayView;
        public int shortcutBottomMargin;
        public int shortcutSideMargin;
        public int upperFPIndicationBottomMargin;
        public int usimTextAreaBottomMargin;

        public ConfigurationBasedDimensions(int i, int i2, int i3, int i4, int i5, Size size, int i6, int i7, boolean z) {
            this.defaultBurnInPreventionYOffsetPx = i;
            this.indicationAreaSideMargin = i2;
            this.indicationAreaBottomMargin = i3;
            this.upperFPIndicationBottomMargin = i4;
            this.usimTextAreaBottomMargin = i5;
            this.buttonSizePx = size;
            this.shortcutSideMargin = i6;
            this.shortcutBottomMargin = i7;
            this.isOverlayView = z;
        }

        public static ConfigurationBasedDimensions copy$default(ConfigurationBasedDimensions configurationBasedDimensions) {
            int i = configurationBasedDimensions.defaultBurnInPreventionYOffsetPx;
            int i2 = configurationBasedDimensions.indicationAreaSideMargin;
            int i3 = configurationBasedDimensions.indicationAreaBottomMargin;
            int i4 = configurationBasedDimensions.upperFPIndicationBottomMargin;
            int i5 = configurationBasedDimensions.usimTextAreaBottomMargin;
            Size size = configurationBasedDimensions.buttonSizePx;
            int i6 = configurationBasedDimensions.shortcutSideMargin;
            int i7 = configurationBasedDimensions.shortcutBottomMargin;
            boolean z = configurationBasedDimensions.isOverlayView;
            configurationBasedDimensions.getClass();
            return new ConfigurationBasedDimensions(i, i2, i3, i4, i5, size, i6, i7, z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return this.defaultBurnInPreventionYOffsetPx == configurationBasedDimensions.defaultBurnInPreventionYOffsetPx && this.indicationAreaSideMargin == configurationBasedDimensions.indicationAreaSideMargin && this.indicationAreaBottomMargin == configurationBasedDimensions.indicationAreaBottomMargin && this.upperFPIndicationBottomMargin == configurationBasedDimensions.upperFPIndicationBottomMargin && this.usimTextAreaBottomMargin == configurationBasedDimensions.usimTextAreaBottomMargin && Intrinsics.areEqual(this.buttonSizePx, configurationBasedDimensions.buttonSizePx) && this.shortcutSideMargin == configurationBasedDimensions.shortcutSideMargin && this.shortcutBottomMargin == configurationBasedDimensions.shortcutBottomMargin && this.isOverlayView == configurationBasedDimensions.isOverlayView;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isOverlayView) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.shortcutBottomMargin, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.shortcutSideMargin, (this.buttonSizePx.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.usimTextAreaBottomMargin, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.upperFPIndicationBottomMargin, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.indicationAreaBottomMargin, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.indicationAreaSideMargin, Integer.hashCode(this.defaultBurnInPreventionYOffsetPx) * 31, 31), 31), 31), 31)) * 31, 31), 31);
        }

        public final String toString() {
            int i = this.indicationAreaSideMargin;
            int i2 = this.indicationAreaBottomMargin;
            int i3 = this.upperFPIndicationBottomMargin;
            int i4 = this.usimTextAreaBottomMargin;
            Size size = this.buttonSizePx;
            int i5 = this.shortcutSideMargin;
            int i6 = this.shortcutBottomMargin;
            boolean z = this.isOverlayView;
            StringBuilder sb = new StringBuilder("ConfigurationBasedDimensions(defaultBurnInPreventionYOffsetPx=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, this.defaultBurnInPreventionYOffsetPx, ", indicationAreaSideMargin=", i, ", indicationAreaBottomMargin=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, i2, ", upperFPIndicationBottomMargin=", i3, ", usimTextAreaBottomMargin=");
            sb.append(i4);
            sb.append(", buttonSizePx=");
            sb.append(size);
            sb.append(", shortcutSideMargin=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(sb, i5, ", shortcutBottomMargin=", i6, ", isOverlayView=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, z, ")");
        }

        public /* synthetic */ ConfigurationBasedDimensions(int i, int i2, int i3, int i4, int i5, Size size, int i6, int i7, boolean z, int i8, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, (i8 & 2) != 0 ? 0 : i2, (i8 & 4) != 0 ? 0 : i3, (i8 & 8) != 0 ? 0 : i4, (i8 & 16) != 0 ? 0 : i5, (i8 & 32) != 0 ? new Size(0, 0) : size, (i8 & 64) != 0 ? 0 : i6, (i8 & 128) != 0 ? 0 : i7, (i8 & 256) == 0 ? z : false);
        }
    }
}
