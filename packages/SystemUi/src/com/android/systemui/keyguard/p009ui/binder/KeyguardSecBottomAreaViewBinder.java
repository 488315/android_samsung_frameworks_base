package com.android.systemui.keyguard.p009ui.binder;

import android.util.Size;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecBottomAreaViewBinder extends KeyguardBottomAreaViewBinder {
    public static final KeyguardSecBottomAreaViewBinder INSTANCE = new KeyguardSecBottomAreaViewBinder();

    private KeyguardSecBottomAreaViewBinder() {
    }

    public static final KeyguardSecBottomAreaViewBinder$bind$1 bind(KeyguardSecBottomAreaView keyguardSecBottomAreaView, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel) {
        RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttached;
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
        ConfigurationBasedDimensions configurationBasedDimensions = new ConfigurationBasedDimensions(keyguardSecBottomAreaView.getResources().getDimensionPixelOffset(R.dimen.default_burn_in_prevention_offset), keyguardSecBottomAreaView.getResources().getDimensionPixelOffset(R.dimen.keyguard_indication_area_padding), 0, 0, 0, 0, null, 0, 0, false, 1020, null);
        keyguardSecBottomAreaView.updateShortcutPosition(configurationBasedDimensions);
        keyguardSecBottomAreaView.updateIndicationPosition(configurationBasedDimensions);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(configurationBasedDimensions);
        repeatWhenAttached = RepeatWhenAttachedKt.repeatWhenAttached(keyguardSecBottomAreaView, EmptyCoroutineContext.INSTANCE, new KeyguardSecBottomAreaViewBinder$bind$disposableHandle$1(keyguardBottomAreaViewModel, keyguardSecAffordanceView, keyguardSecAffordanceView2, keyguardSecBottomAreaView, findViewById, requireViewById, MutableStateFlow, requireViewById2, requireViewById3, keyguardIndicationTextView, linearLayout, null));
        return new KeyguardSecBottomAreaViewBinder$bind$1(requireViewById, findViewById, MutableStateFlow, keyguardSecBottomAreaView, keyguardBottomAreaViewModel, repeatWhenAttached);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ConfigurationBasedDimensions {
        public Size buttonSizePx;
        public final int defaultBurnInPreventionYOffsetPx;
        public int indicationAreaBottomMargin;
        public final int indicationAreaPaddingPx;
        public int indicationAreaSideMargin;
        public boolean isOverlayView;
        public int shortcutBottomMargin;
        public int shortcutSideMargin;
        public int upperFPIndicationBottomMargin;
        public int usimTextAreaBottomMargin;

        public ConfigurationBasedDimensions(int i, int i2, int i3, int i4, int i5, int i6, Size size, int i7, int i8, boolean z) {
            this.defaultBurnInPreventionYOffsetPx = i;
            this.indicationAreaPaddingPx = i2;
            this.indicationAreaSideMargin = i3;
            this.indicationAreaBottomMargin = i4;
            this.upperFPIndicationBottomMargin = i5;
            this.usimTextAreaBottomMargin = i6;
            this.buttonSizePx = size;
            this.shortcutSideMargin = i7;
            this.shortcutBottomMargin = i8;
            this.isOverlayView = z;
        }

        public static ConfigurationBasedDimensions copy$default(ConfigurationBasedDimensions configurationBasedDimensions) {
            int i = configurationBasedDimensions.defaultBurnInPreventionYOffsetPx;
            int i2 = configurationBasedDimensions.indicationAreaPaddingPx;
            int i3 = configurationBasedDimensions.indicationAreaSideMargin;
            int i4 = configurationBasedDimensions.indicationAreaBottomMargin;
            int i5 = configurationBasedDimensions.upperFPIndicationBottomMargin;
            int i6 = configurationBasedDimensions.usimTextAreaBottomMargin;
            Size size = configurationBasedDimensions.buttonSizePx;
            int i7 = configurationBasedDimensions.shortcutSideMargin;
            int i8 = configurationBasedDimensions.shortcutBottomMargin;
            boolean z = configurationBasedDimensions.isOverlayView;
            configurationBasedDimensions.getClass();
            return new ConfigurationBasedDimensions(i, i2, i3, i4, i5, i6, size, i7, i8, z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return this.defaultBurnInPreventionYOffsetPx == configurationBasedDimensions.defaultBurnInPreventionYOffsetPx && this.indicationAreaPaddingPx == configurationBasedDimensions.indicationAreaPaddingPx && this.indicationAreaSideMargin == configurationBasedDimensions.indicationAreaSideMargin && this.indicationAreaBottomMargin == configurationBasedDimensions.indicationAreaBottomMargin && this.upperFPIndicationBottomMargin == configurationBasedDimensions.upperFPIndicationBottomMargin && this.usimTextAreaBottomMargin == configurationBasedDimensions.usimTextAreaBottomMargin && Intrinsics.areEqual(this.buttonSizePx, configurationBasedDimensions.buttonSizePx) && this.shortcutSideMargin == configurationBasedDimensions.shortcutSideMargin && this.shortcutBottomMargin == configurationBasedDimensions.shortcutBottomMargin && this.isOverlayView == configurationBasedDimensions.isOverlayView;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.shortcutBottomMargin, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.shortcutSideMargin, (this.buttonSizePx.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.usimTextAreaBottomMargin, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.upperFPIndicationBottomMargin, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.indicationAreaBottomMargin, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.indicationAreaSideMargin, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.indicationAreaPaddingPx, Integer.hashCode(this.defaultBurnInPreventionYOffsetPx) * 31, 31), 31), 31), 31), 31)) * 31, 31), 31);
            boolean z = this.isOverlayView;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return m42m + i;
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
            sb.append(this.defaultBurnInPreventionYOffsetPx);
            sb.append(", indicationAreaPaddingPx=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, this.indicationAreaPaddingPx, ", indicationAreaSideMargin=", i, ", indicationAreaBottomMargin=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, i2, ", upperFPIndicationBottomMargin=", i3, ", usimTextAreaBottomMargin=");
            sb.append(i4);
            sb.append(", buttonSizePx=");
            sb.append(size);
            sb.append(", shortcutSideMargin=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, i5, ", shortcutBottomMargin=", i6, ", isOverlayView=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, z, ")");
        }

        public /* synthetic */ ConfigurationBasedDimensions(int i, int i2, int i3, int i4, int i5, int i6, Size size, int i7, int i8, boolean z, int i9, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, (i9 & 4) != 0 ? 0 : i3, (i9 & 8) != 0 ? 0 : i4, (i9 & 16) != 0 ? 0 : i5, (i9 & 32) != 0 ? 0 : i6, (i9 & 64) != 0 ? new Size(0, 0) : size, (i9 & 128) != 0 ? 0 : i7, (i9 & 256) != 0 ? 0 : i8, (i9 & 512) != 0 ? false : z);
        }
    }
}
