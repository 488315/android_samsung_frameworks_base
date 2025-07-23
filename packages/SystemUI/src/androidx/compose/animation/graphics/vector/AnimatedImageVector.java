package androidx.compose.animation.graphics.vector;

import androidx.compose.ui.graphics.vector.ImageVector;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AnimatedImageVector {
    public static final Companion Companion = null;
    public final ImageVector imageVector;
    public final List targets;
    public final int totalDuration;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AnimatedImageVector(ImageVector imageVector, List<AnimatedVectorTarget> list) {
        AnimatedVectorTarget animatedVectorTarget;
        Animator animator;
        this.imageVector = imageVector;
        this.targets = list;
        int i = 0;
        if (list.isEmpty()) {
            animatedVectorTarget = null;
        } else {
            animatedVectorTarget = list.get(0);
            int totalDuration = animatedVectorTarget.animator.getTotalDuration();
            int i2 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    AnimatedVectorTarget animatedVectorTarget2 = list.get(i2);
                    int totalDuration2 = animatedVectorTarget2.animator.getTotalDuration();
                    if (totalDuration < totalDuration2) {
                        animatedVectorTarget = animatedVectorTarget2;
                        totalDuration = totalDuration2;
                    }
                    if (i2 == size) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
        }
        AnimatedVectorTarget animatedVectorTarget3 = animatedVectorTarget;
        if (animatedVectorTarget3 != null && (animator = animatedVectorTarget3.animator) != null) {
            i = animator.getTotalDuration();
        }
        this.totalDuration = i;
    }
}
