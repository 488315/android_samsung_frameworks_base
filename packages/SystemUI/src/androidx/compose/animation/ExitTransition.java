package androidx.compose.animation;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ExitTransition {
    public static final Companion Companion = new Companion(null);
    public static final ExitTransition KeepUntilTransitionsFinished;
    public static final ExitTransition None;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        Fade fade = null;
        Slide slide = null;
        ChangeSize changeSize = null;
        Scale scale = null;
        Map map = null;
        None = new ExitTransitionImpl(new TransitionData(fade, slide, changeSize, scale, false, map, 63, defaultConstructorMarker));
        KeepUntilTransitionsFinished = new ExitTransitionImpl(new TransitionData(fade, slide, changeSize, scale, true, map, 47, defaultConstructorMarker));
    }

    public /* synthetic */ ExitTransition(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public final boolean equals(Object obj) {
        return (obj instanceof ExitTransition) && Intrinsics.areEqual(((ExitTransition) obj).getData$animation(), getData$animation());
    }

    public abstract TransitionData getData$animation();

    public final int hashCode() {
        return getData$animation().hashCode();
    }

    public final ExitTransition plus(ExitTransition exitTransition) {
        Fade fade = exitTransition.getData$animation().fade;
        if (fade == null) {
            fade = getData$animation().fade;
        }
        Slide slide = exitTransition.getData$animation().slide;
        if (slide == null) {
            slide = getData$animation().slide;
        }
        ChangeSize changeSize = exitTransition.getData$animation().changeSize;
        if (changeSize == null) {
            changeSize = getData$animation().changeSize;
        }
        Scale scale = exitTransition.getData$animation().scale;
        if (scale == null) {
            scale = getData$animation().scale;
        }
        boolean z = exitTransition.getData$animation().hold || getData$animation().hold;
        Map map = getData$animation().effectsMap;
        Map map2 = exitTransition.getData$animation().effectsMap;
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        linkedHashMap.putAll(map2);
        return new ExitTransitionImpl(new TransitionData(fade, slide, changeSize, scale, z, linkedHashMap));
    }

    public final String toString() {
        if (equals(None)) {
            return "ExitTransition.None";
        }
        if (equals(KeepUntilTransitionsFinished)) {
            return "ExitTransition.KeepUntilTransitionsFinished";
        }
        TransitionData data$animation = getData$animation();
        StringBuilder sb = new StringBuilder("ExitTransition: \nFade - ");
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
        sb.append(",\nKeepUntilTransitionsFinished - ");
        sb.append(data$animation.hold);
        return sb.toString();
    }

    private ExitTransition() {
    }
}
