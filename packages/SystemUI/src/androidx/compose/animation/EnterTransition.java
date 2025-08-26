package androidx.compose.animation;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class EnterTransition {
    public static final Companion Companion = new Companion(null);
    public static final EnterTransition None = new EnterTransitionImpl(new TransitionData(null, null, null, null, false, null, 63, null));

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ EnterTransition(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public final boolean equals(Object obj) {
        return (obj instanceof EnterTransition) && Intrinsics.areEqual(((EnterTransition) obj).getData$animation(), getData$animation());
    }

    public abstract TransitionData getData$animation();

    public final int hashCode() {
        return getData$animation().hashCode();
    }

    public final EnterTransition plus(EnterTransition enterTransition) {
        Fade fade = enterTransition.getData$animation().fade;
        if (fade == null) {
            fade = getData$animation().fade;
        }
        Slide slide = enterTransition.getData$animation().slide;
        if (slide == null) {
            slide = getData$animation().slide;
        }
        ChangeSize changeSize = enterTransition.getData$animation().changeSize;
        if (changeSize == null) {
            changeSize = getData$animation().changeSize;
        }
        Scale scale = enterTransition.getData$animation().scale;
        if (scale == null) {
            scale = getData$animation().scale;
        }
        Map map = getData$animation().effectsMap;
        Map map2 = enterTransition.getData$animation().effectsMap;
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        linkedHashMap.putAll(map2);
        return new EnterTransitionImpl(new TransitionData(fade, slide, changeSize, scale, false, linkedHashMap, 16, null));
    }

    public final String toString() {
        if (equals(None)) {
            return "EnterTransition.None";
        }
        TransitionData data$animation = getData$animation();
        StringBuilder sb = new StringBuilder("EnterTransition: \nFade - ");
        Fade fade = data$animation.fade;
        sb.append(fade != null ? fade.toString() : null);
        sb.append(",\nSlide - ");
        Slide slide = data$animation.slide;
        sb.append(slide != null ? slide.toString() : null);
        sb.append(",\nShrink - ");
        ChangeSize changeSize = data$animation.changeSize;
        sb.append(changeSize != null ? changeSize.toString() : null);
        sb.append(",\nScale - ");
        Scale scale = data$animation.scale;
        sb.append(scale != null ? scale.toString() : null);
        return sb.toString();
    }

    private EnterTransition() {
    }
}
