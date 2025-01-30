package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.ArrayMap;
import android.util.SparseArray;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RenderInfo {
    public final Lazy customRenderInfo$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.controls.ui.RenderInfo$customRenderInfo$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new CustomRenderInfo(null);
        }
    });
    public final int enabledBackground;
    public final int foreground;
    public final Drawable icon;
    public static final Companion Companion = new Companion(null);
    public static final SparseArray iconMap = new SparseArray();
    public static final ArrayMap appIconMap = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static RenderInfo lookup(Context context, ComponentName componentName, int i, int i2) {
            Pair pair;
            Drawable drawable;
            if (i2 > 0) {
                i = (i * 1000) + i2;
            }
            boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
            if (z) {
                CustomRenderInfo.Companion.getClass();
                pair = (Pair) MapsKt__MapsKt.getValue(CustomRenderInfoKt.deviceCustomColorMap, Integer.valueOf(i));
            } else {
                pair = (Pair) MapsKt__MapsKt.getValue(RenderInfoKt.deviceColorMap, Integer.valueOf(i));
            }
            int intValue = ((Number) pair.component1()).intValue();
            int intValue2 = ((Number) pair.component2()).intValue();
            int intValue3 = ((Number) MapsKt__MapsKt.getValue(RenderInfoKt.deviceIconMap, Integer.valueOf(i))).intValue();
            if (intValue3 == -1) {
                ArrayMap arrayMap = RenderInfo.appIconMap;
                drawable = (Drawable) arrayMap.get(componentName);
                if (drawable == null) {
                    drawable = context.getResources().getDrawable(R.drawable.ic_device_unknown_on, null);
                    arrayMap.put(componentName, drawable);
                }
            } else {
                SparseArray sparseArray = RenderInfo.iconMap;
                drawable = (Drawable) sparseArray.get(intValue3);
                if (drawable == null) {
                    drawable = context.getResources().getDrawable(intValue3, null);
                    sparseArray.put(intValue3, drawable);
                }
            }
            Intrinsics.checkNotNull(drawable);
            RenderInfo renderInfo = new RenderInfo(drawable.getConstantState().newDrawable(context.getResources()), intValue, intValue2);
            if (z) {
                CustomRenderInfo.Companion.getClass();
                int intValue4 = ((Number) MapsKt__MapsKt.getValue(CustomRenderInfoKt.defaultActionIconMap, Integer.valueOf(i))).intValue();
                SparseArray sparseArray2 = CustomRenderInfo.actionIconMap;
                Drawable drawable2 = (Drawable) sparseArray2.get(intValue4);
                if (drawable2 == null) {
                    drawable2 = context.getResources().getDrawable(intValue4, null);
                    sparseArray2.set(intValue4, drawable2);
                }
                ((CustomRenderInfo) renderInfo.customRenderInfo$delegate.getValue()).actionIcon = drawable2.getConstantState().newDrawable(context.getResources());
            }
            return renderInfo;
        }
    }

    public RenderInfo(Drawable drawable, int i, int i2) {
        this.icon = drawable;
        this.foreground = i;
        this.enabledBackground = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RenderInfo)) {
            return false;
        }
        RenderInfo renderInfo = (RenderInfo) obj;
        return Intrinsics.areEqual(this.icon, renderInfo.icon) && this.foreground == renderInfo.foreground && this.enabledBackground == renderInfo.enabledBackground;
    }

    public final int hashCode() {
        return Integer.hashCode(this.enabledBackground) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.foreground, this.icon.hashCode() * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RenderInfo(icon=");
        sb.append(this.icon);
        sb.append(", foreground=");
        sb.append(this.foreground);
        sb.append(", enabledBackground=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.enabledBackground, ")");
    }
}
