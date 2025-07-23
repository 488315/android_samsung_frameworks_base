package androidx.compose.animation;

import java.util.Map;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransitionData {
    public final ChangeSize changeSize;
    public final Map effectsMap;
    public final Fade fade;
    public final boolean hold;
    public final Scale scale;
    public final Slide slide;

    public TransitionData() {
        this(null, null, null, null, false, null, 63, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransitionData)) {
            return false;
        }
        TransitionData transitionData = (TransitionData) obj;
        return Intrinsics.areEqual(this.fade, transitionData.fade) && Intrinsics.areEqual(this.slide, transitionData.slide) && Intrinsics.areEqual(this.changeSize, transitionData.changeSize) && Intrinsics.areEqual(this.scale, transitionData.scale) && this.hold == transitionData.hold && Intrinsics.areEqual(this.effectsMap, transitionData.effectsMap);
    }

    public final int hashCode() {
        Fade fade = this.fade;
        int hashCode = (fade == null ? 0 : fade.hashCode()) * 31;
        Slide slide = this.slide;
        int hashCode2 = (hashCode + (slide == null ? 0 : slide.hashCode())) * 31;
        ChangeSize changeSize = this.changeSize;
        int hashCode3 = (hashCode2 + (changeSize == null ? 0 : changeSize.hashCode())) * 31;
        Scale scale = this.scale;
        return this.effectsMap.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((hashCode3 + (scale != null ? scale.hashCode() : 0)) * 31, 31, this.hold);
    }

    public final String toString() {
        return "TransitionData(fade=" + this.fade + ", slide=" + this.slide + ", changeSize=" + this.changeSize + ", scale=" + this.scale + ", hold=" + this.hold + ", effectsMap=" + this.effectsMap + ')';
    }

    public TransitionData(Fade fade, Slide slide, ChangeSize changeSize, Scale scale, boolean z, Map<Object, Object> map) {
        this.fade = fade;
        this.slide = slide;
        this.changeSize = changeSize;
        this.scale = scale;
        this.hold = z;
        this.effectsMap = map;
    }

    public /* synthetic */ TransitionData(Fade fade, Slide slide, ChangeSize changeSize, Scale scale, boolean z, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : fade, (i & 2) != 0 ? null : slide, (i & 4) != 0 ? null : changeSize, (i & 8) != 0 ? null : scale, (i & 16) != 0 ? false : z, (i & 32) != 0 ? MapsKt__MapsKt.emptyMap() : map);
    }
}
