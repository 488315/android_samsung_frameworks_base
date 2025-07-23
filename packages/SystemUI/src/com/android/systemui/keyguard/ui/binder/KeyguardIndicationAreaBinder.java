package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.view.KeyguardIndicationArea;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.util.kotlin.DisposableHandles;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardIndicationAreaBinder {
    public static final KeyguardIndicationAreaBinder INSTANCE = new KeyguardIndicationAreaBinder();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ConfigurationBasedDimensions {
        public final int defaultBurnInPreventionYOffsetPx;
        public final int indicationAreaPaddingPx;
        public final int indicationTextSizePx;

        public ConfigurationBasedDimensions(int i, int i2, int i3) {
            this.defaultBurnInPreventionYOffsetPx = i;
            this.indicationAreaPaddingPx = i2;
            this.indicationTextSizePx = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return this.defaultBurnInPreventionYOffsetPx == configurationBasedDimensions.defaultBurnInPreventionYOffsetPx && this.indicationAreaPaddingPx == configurationBasedDimensions.indicationAreaPaddingPx && this.indicationTextSizePx == configurationBasedDimensions.indicationTextSizePx;
        }

        public final int hashCode() {
            return Integer.hashCode(this.indicationTextSizePx) + ReorderTile$$ExternalSyntheticOutline0.m(this.indicationAreaPaddingPx, Integer.hashCode(this.defaultBurnInPreventionYOffsetPx) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ConfigurationBasedDimensions(defaultBurnInPreventionYOffsetPx=");
            sb.append(this.defaultBurnInPreventionYOffsetPx);
            sb.append(", indicationAreaPaddingPx=");
            sb.append(this.indicationAreaPaddingPx);
            sb.append(", indicationTextSizePx=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.indicationTextSizePx, ")", sb);
        }
    }

    private KeyguardIndicationAreaBinder() {
    }

    public static final DisposableHandles bind(KeyguardIndicationArea keyguardIndicationArea, KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, final KeyguardIndicationController keyguardIndicationController) {
        DisposableHandles disposableHandles = new DisposableHandles();
        final ViewGroup viewGroup = keyguardIndicationController.mIndicationArea;
        keyguardIndicationController.setIndicationArea(keyguardIndicationArea);
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$1
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                ViewGroup viewGroup2 = viewGroup;
                if (viewGroup2 != null) {
                    keyguardIndicationController.setIndicationArea(viewGroup2);
                }
            }
        });
        TextView textView = (TextView) keyguardIndicationArea.requireViewById(R.id.keyguard_indication_text);
        TextView textView2 = (TextView) keyguardIndicationArea.requireViewById(R.id.keyguard_indication_text_bottom);
        keyguardIndicationArea.setClipChildren(false);
        keyguardIndicationArea.setClipToPadding(false);
        INSTANCE.getClass();
        KeyguardIndicationAreaBinder$bind$2 keyguardIndicationAreaBinder$bind$2 = new KeyguardIndicationAreaBinder$bind$2(keyguardIndicationAreaViewModel, keyguardIndicationArea, StateFlowKt.MutableStateFlow(loadFromResources(keyguardIndicationArea)), textView, textView2, keyguardIndicationController, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(keyguardIndicationArea, EmptyCoroutineContext.INSTANCE, keyguardIndicationAreaBinder$bind$2));
        return disposableHandles;
    }

    public static ConfigurationBasedDimensions loadFromResources(View view) {
        return new ConfigurationBasedDimensions(view.getResources().getDimensionPixelOffset(R.dimen.default_burn_in_prevention_offset), view.getResources().getDimensionPixelOffset(R.dimen.keyguard_indication_area_padding), view.getResources().getDimensionPixelSize(17106416));
    }
}
