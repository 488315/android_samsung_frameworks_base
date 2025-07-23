package androidx.compose.foundation;

import android.content.Context;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Density;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidEdgeEffectOverscrollFactory implements OverscrollFactory {
    public final Context context;
    public final Density density;
    public final long glowColor;
    public final PaddingValues glowDrawPadding;

    public /* synthetic */ AndroidEdgeEffectOverscrollFactory(Context context, Density density, long j, PaddingValues paddingValues, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, density, j, paddingValues);
    }

    @Override // androidx.compose.foundation.OverscrollFactory
    public final OverscrollEffect createOverscrollEffect() {
        return new AndroidEdgeEffectOverscrollEffect(this.context, this.density, this.glowColor, this.glowDrawPadding, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!AndroidEdgeEffectOverscrollFactory.class.equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        AndroidEdgeEffectOverscrollFactory androidEdgeEffectOverscrollFactory = (AndroidEdgeEffectOverscrollFactory) obj;
        if (!Intrinsics.areEqual(this.context, androidEdgeEffectOverscrollFactory.context) || !Intrinsics.areEqual(this.density, androidEdgeEffectOverscrollFactory.density)) {
            return false;
        }
        long j = androidEdgeEffectOverscrollFactory.glowColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.glowColor, j) && Intrinsics.areEqual(this.glowDrawPadding, androidEdgeEffectOverscrollFactory.glowDrawPadding);
    }

    public final int hashCode() {
        int hashCode = (this.density.hashCode() + (this.context.hashCode() * 31)) * 31;
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return this.glowDrawPadding.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(hashCode, 31, this.glowColor);
    }

    public /* synthetic */ AndroidEdgeEffectOverscrollFactory(Context context, Density density, long j, PaddingValues paddingValues, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, density, (i & 4) != 0 ? AndroidOverscroll_androidKt.DefaultGlowColor : j, (i & 8) != 0 ? AndroidOverscroll_androidKt.DefaultGlowPaddingValues : paddingValues, null);
    }

    private AndroidEdgeEffectOverscrollFactory(Context context, Density density, long j, PaddingValues paddingValues) {
        this.context = context;
        this.density = density;
        this.glowColor = j;
        this.glowDrawPadding = paddingValues;
    }
}
