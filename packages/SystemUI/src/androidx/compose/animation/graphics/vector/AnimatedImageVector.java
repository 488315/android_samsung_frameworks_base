package androidx.compose.animation.graphics.vector;

import androidx.compose.ui.graphics.vector.ImageVector;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class AnimatedImageVector {
    public static final Companion Companion = null;
    public final ImageVector imageVector;
    public final List targets;
    public final int totalDuration;

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
        int totalDuration = 0;
        if (list.isEmpty()) {
            animatedVectorTarget = null;
        } else {
            animatedVectorTarget = list.get(0);
            int totalDuration2 = animatedVectorTarget.animator.getTotalDuration();
            int i = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    AnimatedVectorTarget animatedVectorTarget2 = list.get(i);
                    int totalDuration3 = animatedVectorTarget2.animator.getTotalDuration();
                    if (totalDuration2 < totalDuration3) {
                        animatedVectorTarget = animatedVectorTarget2;
                        totalDuration2 = totalDuration3;
                    }
                    if (i == size) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        AnimatedVectorTarget animatedVectorTarget3 = animatedVectorTarget;
        if (animatedVectorTarget3 != null && (animator = animatedVectorTarget3.animator) != null) {
            totalDuration = animator.getTotalDuration();
        }
        this.totalDuration = totalDuration;
    }
}
