package androidx.compose.animation.graphics.vector;

import androidx.collection.MutableScatterMap;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class AnimatorSet extends Animator {
    public final List animators;
    public final Ordering ordering;
    public final int totalDuration;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Ordering.values().length];
            try {
                iArr[Ordering.Together.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Ordering.Sequentially.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AnimatorSet(List<? extends Animator> list, Ordering ordering) {
        super(0 == true ? 1 : 0);
        Animator animator = null;
        this.animators = list;
        this.ordering = ordering;
        int i = WhenMappings.$EnumSwitchMapping$0[ordering.ordinal()];
        int i2 = 1;
        int totalDuration = 0;
        if (i == 1) {
            if (!list.isEmpty()) {
                Animator animator2 = list.get(0);
                int totalDuration2 = animator2.getTotalDuration();
                int size = list.size() - 1;
                if (1 <= size) {
                    while (true) {
                        Animator animator3 = list.get(i2);
                        int totalDuration3 = animator3.getTotalDuration();
                        if (totalDuration2 < totalDuration3) {
                            animator2 = animator3;
                            totalDuration2 = totalDuration3;
                        }
                        if (i2 == size) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                animator = animator2;
            }
            Animator animator4 = animator;
            if (animator4 != null) {
                totalDuration = animator4.getTotalDuration();
            }
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            int size2 = list.size();
            int totalDuration4 = 0;
            while (totalDuration < size2) {
                totalDuration4 += list.get(totalDuration).getTotalDuration();
                totalDuration++;
            }
            totalDuration = totalDuration4;
        }
        this.totalDuration = totalDuration;
    }

    @Override // androidx.compose.animation.graphics.vector.Animator
    public final void collectPropertyValues(MutableScatterMap mutableScatterMap, int i, int i2) {
        int i3 = WhenMappings.$EnumSwitchMapping$0[this.ordering.ordinal()];
        int i4 = 0;
        if (i3 == 1) {
            List list = this.animators;
            int size = list.size();
            while (i4 < size) {
                ((Animator) list.get(i4)).collectPropertyValues(mutableScatterMap, i, i2);
                i4++;
            }
            return;
        }
        if (i3 != 2) {
            return;
        }
        List list2 = this.animators;
        int size2 = list2.size();
        while (i4 < size2) {
            Animator animator = (Animator) list2.get(i4);
            animator.collectPropertyValues(mutableScatterMap, i, i2);
            i2 += animator.getTotalDuration();
            i4++;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AnimatorSet)) {
            return false;
        }
        AnimatorSet animatorSet = (AnimatorSet) obj;
        return Intrinsics.areEqual(this.animators, animatorSet.animators) && this.ordering == animatorSet.ordering;
    }

    @Override // androidx.compose.animation.graphics.vector.Animator
    public final int getTotalDuration() {
        return this.totalDuration;
    }

    public final int hashCode() {
        return this.ordering.hashCode() + (this.animators.hashCode() * 31);
    }

    public final String toString() {
        return "AnimatorSet(animators=" + this.animators + ", ordering=" + this.ordering + ')';
    }
}
