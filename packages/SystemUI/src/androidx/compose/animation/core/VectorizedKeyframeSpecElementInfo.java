package androidx.compose.animation.core;

import androidx.compose.animation.core.AnimationVector;
import androidx.compose.animation.core.ArcMode;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorizedKeyframeSpecElementInfo<V extends AnimationVector> {
    public final int arcMode;
    public final Easing easing;
    public final AnimationVector vectorValue;

    public /* synthetic */ VectorizedKeyframeSpecElementInfo(AnimationVector animationVector, Easing easing, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(animationVector, easing, i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VectorizedKeyframeSpecElementInfo)) {
            return false;
        }
        VectorizedKeyframeSpecElementInfo vectorizedKeyframeSpecElementInfo = (VectorizedKeyframeSpecElementInfo) obj;
        if (!Intrinsics.areEqual(this.vectorValue, vectorizedKeyframeSpecElementInfo.vectorValue) || !Intrinsics.areEqual(this.easing, vectorizedKeyframeSpecElementInfo.easing)) {
            return false;
        }
        ArcMode.Companion companion = ArcMode.Companion;
        return this.arcMode == vectorizedKeyframeSpecElementInfo.arcMode;
    }

    public final int hashCode() {
        int hashCode = (this.easing.hashCode() + (this.vectorValue.hashCode() * 31)) * 31;
        ArcMode.Companion companion = ArcMode.Companion;
        return Integer.hashCode(this.arcMode) + hashCode;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VectorizedKeyframeSpecElementInfo(vectorValue=");
        sb.append(this.vectorValue);
        sb.append(", easing=");
        sb.append(this.easing);
        sb.append(", arcMode=");
        ArcMode.Companion companion = ArcMode.Companion;
        sb.append((Object) ("ArcMode(value=" + this.arcMode + ')'));
        sb.append(')');
        return sb.toString();
    }

    private VectorizedKeyframeSpecElementInfo(V v, Easing easing, int i) {
        this.vectorValue = v;
        this.easing = easing;
        this.arcMode = i;
    }
}
