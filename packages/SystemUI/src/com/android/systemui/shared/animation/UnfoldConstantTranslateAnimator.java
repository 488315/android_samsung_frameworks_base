package com.android.systemui.shared.animation;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;

public final class UnfoldConstantTranslateAnimator implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final UnfoldTransitionProgressProvider progressProvider;
    public ViewGroup rootView;
    public float translationMax;
    public final Set viewsIdToTranslate;
    public List viewsToTranslate = EmptyList.INSTANCE;

    public final class Direction {
        public static final /* synthetic */ Direction[] $VALUES;
        public static final Direction END;
        public static final Direction START;
        private final float multiplier;

        static {
            Direction direction = new Direction("START", 0, -1.0f);
            START = direction;
            Direction direction2 = new Direction("END", 1, 1.0f);
            END = direction2;
            Direction[] directionArr = {direction, direction2};
            $VALUES = directionArr;
            EnumEntriesKt.enumEntries(directionArr);
        }

        private Direction(String str, int i, float f) {
            this.multiplier = f;
        }

        public static Direction valueOf(String str) {
            return (Direction) Enum.valueOf(Direction.class, str);
        }

        public static Direction[] values() {
            return (Direction[]) $VALUES.clone();
        }

        public final float getMultiplier() {
            return this.multiplier;
        }
    }

    public final class ViewToTranslate {
        public final Direction direction;
        public final Function2 translateFunc;
        public final WeakReference view;

        public ViewToTranslate(WeakReference<View> weakReference, Direction direction, Function2 function2) {
            this.view = weakReference;
            this.direction = direction;
            this.translateFunc = function2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ViewToTranslate)) {
                return false;
            }
            ViewToTranslate viewToTranslate = (ViewToTranslate) obj;
            return Intrinsics.areEqual(this.view, viewToTranslate.view) && this.direction == viewToTranslate.direction && Intrinsics.areEqual(this.translateFunc, viewToTranslate.translateFunc);
        }

        public final int hashCode() {
            return this.translateFunc.hashCode() + ((this.direction.hashCode() + (this.view.hashCode() * 31)) * 31);
        }

        public final String toString() {
            return "ViewToTranslate(view=" + this.view + ", direction=" + this.direction + ", translateFunc=" + this.translateFunc + ")";
        }
    }

    public UnfoldConstantTranslateAnimator(Set<ViewIdToTranslate> set, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.viewsIdToTranslate = set;
        this.progressProvider = unfoldTransitionProgressProvider;
    }

    public final void init(ViewGroup viewGroup, float f) {
        if (this.rootView == null) {
            this.progressProvider.addCallback(this);
        }
        this.rootView = viewGroup;
        this.translationMax = f;
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinished() {
        translateViews(1.0f);
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionProgress(float f) {
        translateViews(f);
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        final ViewGroup viewGroup = this.rootView;
        if (viewGroup == null) {
            viewGroup = null;
        }
        this.viewsToTranslate = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.viewsIdToTranslate), new Function1() { // from class: com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator$registerViewsForAnimation$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return (Boolean) ((UnfoldConstantTranslateAnimator.ViewIdToTranslate) obj).shouldBeAnimated.invoke();
            }
        }), new Function1() { // from class: com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator$registerViewsForAnimation$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate = (UnfoldConstantTranslateAnimator.ViewIdToTranslate) obj;
                View findViewById = viewGroup.findViewById(viewIdToTranslate.viewId);
                if (findViewById != null) {
                    return new UnfoldConstantTranslateAnimator.ViewToTranslate(new WeakReference(findViewById), viewIdToTranslate.direction, viewIdToTranslate.translateFunc);
                }
                return null;
            }
        }));
    }

    public final void translateViews(float f) {
        float f2 = (f - 1.0f) * this.translationMax;
        ViewGroup viewGroup = this.rootView;
        if (viewGroup == null) {
            viewGroup = null;
        }
        int i = viewGroup.getLayoutDirection() == 1 ? -1 : 1;
        for (ViewToTranslate viewToTranslate : this.viewsToTranslate) {
            View view = (View) viewToTranslate.view.get();
            if (view != null) {
                viewToTranslate.translateFunc.invoke(view, Float.valueOf(viewToTranslate.direction.getMultiplier() * f2 * i));
            }
        }
    }

    public final class ViewIdToTranslate {
        public final Direction direction;
        public final Function0 shouldBeAnimated;
        public final Function2 translateFunc;
        public final int viewId;

        public ViewIdToTranslate(int i, Direction direction, Function0 function0, Function2 function2) {
            this.viewId = i;
            this.direction = direction;
            this.shouldBeAnimated = function0;
            this.translateFunc = function2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ViewIdToTranslate)) {
                return false;
            }
            ViewIdToTranslate viewIdToTranslate = (ViewIdToTranslate) obj;
            return this.viewId == viewIdToTranslate.viewId && this.direction == viewIdToTranslate.direction && Intrinsics.areEqual(this.shouldBeAnimated, viewIdToTranslate.shouldBeAnimated) && Intrinsics.areEqual(this.translateFunc, viewIdToTranslate.translateFunc);
        }

        public final int hashCode() {
            return this.translateFunc.hashCode() + ((this.shouldBeAnimated.hashCode() + ((this.direction.hashCode() + (Integer.hashCode(this.viewId) * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "ViewIdToTranslate(viewId=" + this.viewId + ", direction=" + this.direction + ", shouldBeAnimated=" + this.shouldBeAnimated + ", translateFunc=" + this.translateFunc + ")";
        }

        public /* synthetic */ ViewIdToTranslate(int i, Direction direction, Function0 function0, Function2 function2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, direction, (i2 & 4) != 0 ? new Function0() { // from class: com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator.ViewIdToTranslate.1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Boolean.TRUE;
                }
            } : function0, (i2 & 8) != 0 ? new Function2() { // from class: com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator.ViewIdToTranslate.2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((View) obj).setTranslationX(((Number) obj2).floatValue());
                    return Unit.INSTANCE;
                }
            } : function2);
        }
    }
}
