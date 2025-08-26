package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.systemui.R;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class RenderInfo {
    public final int enabledBackground;
    public final int foreground;
    public final Drawable icon;
    public final Lazy secRenderInfo$delegate = LazyKt__LazyJVMKt.lazy(new RenderInfo$$ExternalSyntheticLambda0());
    public static final Companion Companion = new Companion(null);
    public static final SparseArray iconMap = new SparseArray();
    public static final ArrayMap appIconMap = new ArrayMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static RenderInfo lookup(Context context, ComponentName componentName, int i, int i2) {
            Drawable drawable;
            if (i2 > 0) {
                i = (i * 1000) + i2;
            }
            SecRenderInfo.Companion.getClass();
            Pair pair = (Pair) MapsKt__MapsKt.getValue(Integer.valueOf(i), SecRenderInfoKt.secDeviceColorMap);
            int iIntValue = ((Number) pair.component1()).intValue();
            int iIntValue2 = ((Number) pair.component2()).intValue();
            int iIntValue3 = ((Number) MapsKt__MapsKt.getValue(Integer.valueOf(i), RenderInfoKt.deviceIconMap)).intValue();
            if (iIntValue3 == -1) {
                ArrayMap arrayMap = RenderInfo.appIconMap;
                drawable = (Drawable) arrayMap.get(componentName);
                if (drawable == null) {
                    drawable = context.getResources().getDrawable(R.drawable.ic_device_unknown_on, null);
                    arrayMap.put(componentName, drawable);
                }
            } else {
                SparseArray sparseArray = RenderInfo.iconMap;
                drawable = (Drawable) sparseArray.get(iIntValue3);
                if (drawable == null) {
                    drawable = context.getResources().getDrawable(iIntValue3, null);
                    sparseArray.put(iIntValue3, drawable);
                }
            }
            Drawable.ConstantState constantState = drawable != null ? drawable.getConstantState() : null;
            if (constantState == null) {
                throw new IllegalStateException("Required value was null.");
            }
            RenderInfo renderInfo = new RenderInfo(constantState.newDrawable(context.getResources()), iIntValue, iIntValue2);
            int iIntValue4 = ((Number) MapsKt__MapsKt.getValue(Integer.valueOf(i), SecRenderInfoKt.defaultActionIconMap)).intValue();
            SparseArray sparseArray2 = SecRenderInfo.actionIconMap;
            Drawable drawable2 = (Drawable) sparseArray2.get(iIntValue4);
            if (drawable2 == null) {
                drawable2 = context.getResources().getDrawable(iIntValue4, null);
                sparseArray2.set(iIntValue4, drawable2);
            }
            SecRenderInfo secRenderInfo = (SecRenderInfo) renderInfo.secRenderInfo$delegate.getValue();
            Drawable.ConstantState constantState2 = drawable2.getConstantState();
            if (constantState2 == null) {
                throw new IllegalStateException("Required value was null.");
            }
            secRenderInfo.actionIcon = constantState2.newDrawable(context.getResources());
            return renderInfo;
        }

        private Companion() {
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
        return Integer.hashCode(this.enabledBackground) + ReorderTile$$ExternalSyntheticOutline0.m(this.foreground, this.icon.hashCode() * 31, 31);
    }

    public final String toString() {
        Drawable drawable = this.icon;
        StringBuilder sb = new StringBuilder("RenderInfo(icon=");
        sb.append(drawable);
        sb.append(", foreground=");
        sb.append(this.foreground);
        sb.append(", enabledBackground=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.enabledBackground, ")", sb);
    }
}
