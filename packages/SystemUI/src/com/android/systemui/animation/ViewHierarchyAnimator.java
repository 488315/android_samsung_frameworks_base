package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.IntProperty;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.animation.ViewHierarchyAnimator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ViewHierarchyAnimator {
    public static final Interpolator DEFAULT_FADE_IN_INTERPOLATOR;
    public static final Map PROPERTIES;
    public static final Companion Companion = new Companion(null);
    public static final Interpolator DEFAULT_INTERPOLATOR = Interpolators.STANDARD;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    abstract class Bound {
        public static final /* synthetic */ Bound[] $VALUES;
        public static final BOTTOM BOTTOM;
        public static final LEFT LEFT;
        public static final RIGHT RIGHT;
        public static final TOP TOP;
        private final String label;
        private final int overrideTag;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        final class BOTTOM extends Bound {
            public BOTTOM(String str, int i) {
                super(str, i, "bottom", R.id.tag_override_bottom, null);
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public final int getValue(View view) {
                return view.getBottom();
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public final void setValue(View view, int i) {
                view.setBottom(i);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        final class LEFT extends Bound {
            public LEFT(String str, int i) {
                super(str, i, "left", R.id.tag_override_left, null);
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public final int getValue(View view) {
                return view.getLeft();
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public final void setValue(View view, int i) {
                view.setLeft(i);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        final class RIGHT extends Bound {
            public RIGHT(String str, int i) {
                super(str, i, "right", R.id.tag_override_right, null);
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public final int getValue(View view) {
                return view.getRight();
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public final void setValue(View view, int i) {
                view.setRight(i);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        final class TOP extends Bound {
            public TOP(String str, int i) {
                super(str, i, "top", R.id.tag_override_top, null);
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public final int getValue(View view) {
                return view.getTop();
            }

            @Override // com.android.systemui.animation.ViewHierarchyAnimator.Bound
            public final void setValue(View view, int i) {
                view.setTop(i);
            }
        }

        static {
            LEFT left = new LEFT("LEFT", 0);
            LEFT = left;
            TOP top = new TOP("TOP", 1);
            TOP = top;
            RIGHT right = new RIGHT("RIGHT", 2);
            RIGHT = right;
            BOTTOM bottom = new BOTTOM("BOTTOM", 3);
            BOTTOM = bottom;
            Bound[] boundArr = {left, top, right, bottom};
            $VALUES = boundArr;
            EnumEntriesKt.enumEntries(boundArr);
        }

        public /* synthetic */ Bound(String str, int i, String str2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i, str2, i2);
        }

        public static Bound valueOf(String str) {
            return (Bound) Enum.valueOf(Bound.class, str);
        }

        public static Bound[] values() {
            return (Bound[]) $VALUES.clone();
        }

        public final String getLabel() {
            return this.label;
        }

        public final int getOverrideTag() {
            return this.overrideTag;
        }

        public abstract int getValue(View view);

        public abstract void setValue(View view, int i);

        private Bound(String str, int i, String str2, int i2) {
            this.label = str2;
            this.overrideTag = i2;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Hotspot.values().length];
                try {
                    iArr[Hotspot.CENTER.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Hotspot.BOTTOM_LEFT.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Hotspot.LEFT.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[Hotspot.TOP_LEFT.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[Hotspot.TOP.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[Hotspot.BOTTOM.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr[Hotspot.TOP_RIGHT.ordinal()] = 7;
                } catch (NoSuchFieldError unused7) {
                }
                try {
                    iArr[Hotspot.RIGHT.ordinal()] = 8;
                } catch (NoSuchFieldError unused8) {
                }
                try {
                    iArr[Hotspot.BOTTOM_RIGHT.ordinal()] = 9;
                } catch (NoSuchFieldError unused9) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private Companion() {
        }

        public static final Integer access$getBound(Companion companion, View view, Bound bound) {
            companion.getClass();
            Object tag = view.getTag(bound.getOverrideTag());
            if (tag instanceof Integer) {
                return (Integer) tag;
            }
            return null;
        }

        public static void addListener(View view, ViewHierarchyAnimator$Companion$createListener$1 viewHierarchyAnimator$Companion$createListener$1, boolean z, boolean z2, Set set) {
            if (set.contains(view)) {
                return;
            }
            Object tag = view.getTag(R.id.tag_layout_listener);
            if (tag != null && (tag instanceof View.OnLayoutChangeListener)) {
                view.removeOnLayoutChangeListener((View.OnLayoutChangeListener) tag);
            }
            view.addOnLayoutChangeListener(viewHierarchyAnimator$Companion$createListener$1);
            view.setTag(R.id.tag_layout_listener, viewHierarchyAnimator$Companion$createListener$1);
            if (z2 && (view instanceof ViewGroup) && z) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    addListener(viewGroup.getChildAt(i), viewHierarchyAnimator$Companion$createListener$1, true, z2, set);
                }
            }
        }

        public static void addListener$default(Companion companion, View view, ViewHierarchyAnimator$Companion$createListener$1 viewHierarchyAnimator$Companion$createListener$1, boolean z) {
            EmptySet emptySet = EmptySet.INSTANCE;
            companion.getClass();
            addListener(view, viewHierarchyAnimator$Companion$createListener$1, z, true, emptySet);
        }

        public static void createAndStartFadeInAnimator(final View view, long j, long j2, Interpolator interpolator) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f);
            ofFloat.setStartDelay(j2);
            ofFloat.setDuration(j);
            ofFloat.setInterpolator(interpolator);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createAndStartFadeInAnimator$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.setTag(R.id.tag_alpha_animator, null);
                }
            });
            Object tag = view.getTag(R.id.tag_alpha_animator);
            ObjectAnimator objectAnimator = tag instanceof ObjectAnimator ? (ObjectAnimator) tag : null;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            view.setTag(R.id.tag_alpha_animator, ofFloat);
            ofFloat.start();
        }

        public static boolean occupiesSpace(int i, int i2, int i3, int i4, int i5) {
            return (i == 8 || i2 == i4 || i3 == i5) ? false : true;
        }

        public static void recursivelyRemoveListener(View view) {
            Object tag = view.getTag(R.id.tag_layout_listener);
            if (tag != null && (tag instanceof View.OnLayoutChangeListener)) {
                view.setTag(R.id.tag_layout_listener, null);
                view.removeOnLayoutChangeListener((View.OnLayoutChangeListener) tag);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    recursivelyRemoveListener(viewGroup.getChildAt(i));
                }
            }
        }

        public static void setBound(View view, Bound bound, int i) {
            view.setTag(bound.getOverrideTag(), Integer.valueOf(i));
            bound.setValue(view, i);
        }

        public static void startAnimation(final View view, final Set set, Map map, Map map2, Interpolator interpolator, long j, final boolean z, final Runnable runnable) {
            ListBuilder listBuilder = new ListBuilder();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Bound bound = (Bound) it.next();
                listBuilder.add(PropertyValuesHolder.ofInt((Property<?, Integer>) ViewHierarchyAnimator.PROPERTIES.get(bound), ((Number) MapsKt__MapsKt.getValue(bound, map)).intValue(), ((Number) MapsKt__MapsKt.getValue(bound, map2)).intValue()));
            }
            PropertyValuesHolder[] propertyValuesHolderArr = (PropertyValuesHolder[]) listBuilder.build().toArray(new PropertyValuesHolder[0]);
            Object tag = view.getTag(R.id.tag_animator);
            ObjectAnimator objectAnimator = tag instanceof ObjectAnimator ? (ObjectAnimator) tag : null;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, (PropertyValuesHolder[]) Arrays.copyOf(propertyValuesHolderArr, propertyValuesHolderArr.length));
            ofPropertyValuesHolder.setInterpolator(interpolator);
            ofPropertyValuesHolder.setDuration(j);
            ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$startAnimation$1
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.cancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    Runnable runnable2;
                    view.setTag(R.id.tag_animator, null);
                    Set set2 = set;
                    View view2 = view;
                    Iterator it2 = set2.iterator();
                    while (it2.hasNext()) {
                        view2.setTag(((ViewHierarchyAnimator.Bound) it2.next()).getOverrideTag(), null);
                    }
                    if (z && !this.cancelled) {
                        ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                        View view3 = view;
                        companion.getClass();
                        ViewHierarchyAnimator.Companion.recursivelyRemoveListener(view3);
                    }
                    if (this.cancelled || (runnable2 = runnable) == null) {
                        return;
                    }
                    runnable2.run();
                }
            });
            Iterator it2 = set.iterator();
            while (it2.hasNext()) {
                Bound bound2 = (Bound) it2.next();
                Companion companion = ViewHierarchyAnimator.Companion;
                int intValue = ((Number) MapsKt__MapsKt.getValue(bound2, map)).intValue();
                companion.getClass();
                setBound(view, bound2, intValue);
            }
            view.setTag(R.id.tag_animator, ofPropertyValuesHolder);
            ofPropertyValuesHolder.start();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0417  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x0419 A[PHI: r18
          0x0419: PHI (r18v11 int) = (r18v8 int), (r18v10 int) binds: [B:55:0x040e, B:58:0x041c] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:58:0x041c  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x0420 A[PHI: r13
          0x0420: PHI (r13v4 int) = (r13v3 int), (r13v7 int), (r13v8 int) binds: [B:55:0x040e, B:57:0x0419, B:56:0x0417] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0461  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x0474  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0487  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x049a  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x049d A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0411 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean animateRemoval(final android.view.View r25, com.android.systemui.animation.ViewHierarchyAnimator.Hotspot r26, android.view.animation.Interpolator r27, final com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewOut$fullEndRunnable$1 r28) {
            /*
                Method dump skipped, instructions count: 1394
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.ViewHierarchyAnimator.Companion.animateRemoval(android.view.View, com.android.systemui.animation.ViewHierarchyAnimator$Hotspot, android.view.animation.Interpolator, com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewOut$fullEndRunnable$1):boolean");
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DimenHolder {
        public final int bottom;
        public final int left;
        public final int right;
        public final int top;

        public DimenHolder(int i, int i2, int i3, int i4) {
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DimenHolder)) {
                return false;
            }
            DimenHolder dimenHolder = (DimenHolder) obj;
            return this.left == dimenHolder.left && this.top == dimenHolder.top && this.right == dimenHolder.right && this.bottom == dimenHolder.bottom;
        }

        public final int hashCode() {
            return Integer.hashCode(this.bottom) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.right, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.top, Integer.hashCode(this.left) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DimenHolder(left=");
            sb.append(this.left);
            sb.append(", top=");
            sb.append(this.top);
            sb.append(", right=");
            sb.append(this.right);
            sb.append(", bottom=");
            return Anchor$$ExternalSyntheticOutline0.m(this.bottom, ")", sb);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Hotspot {
        public static final /* synthetic */ Hotspot[] $VALUES;
        public static final Hotspot BOTTOM;
        public static final Hotspot BOTTOM_LEFT;
        public static final Hotspot BOTTOM_RIGHT;
        public static final Hotspot CENTER;
        public static final Hotspot LEFT;
        public static final Hotspot RIGHT;
        public static final Hotspot TOP;
        public static final Hotspot TOP_LEFT;
        public static final Hotspot TOP_RIGHT;

        static {
            Hotspot hotspot = new Hotspot("CENTER", 0);
            CENTER = hotspot;
            Hotspot hotspot2 = new Hotspot("LEFT", 1);
            LEFT = hotspot2;
            Hotspot hotspot3 = new Hotspot("TOP_LEFT", 2);
            TOP_LEFT = hotspot3;
            Hotspot hotspot4 = new Hotspot("TOP", 3);
            TOP = hotspot4;
            Hotspot hotspot5 = new Hotspot("TOP_RIGHT", 4);
            TOP_RIGHT = hotspot5;
            Hotspot hotspot6 = new Hotspot("RIGHT", 5);
            RIGHT = hotspot6;
            Hotspot hotspot7 = new Hotspot("BOTTOM_RIGHT", 6);
            BOTTOM_RIGHT = hotspot7;
            Hotspot hotspot8 = new Hotspot("BOTTOM", 7);
            BOTTOM = hotspot8;
            Hotspot hotspot9 = new Hotspot("BOTTOM_LEFT", 8);
            BOTTOM_LEFT = hotspot9;
            Hotspot[] hotspotArr = {hotspot, hotspot2, hotspot3, hotspot4, hotspot5, hotspot6, hotspot7, hotspot8, hotspot9};
            $VALUES = hotspotArr;
            EnumEntriesKt.enumEntries(hotspotArr);
        }

        private Hotspot(String str, int i) {
        }

        public static Hotspot valueOf(String str) {
            return (Hotspot) Enum.valueOf(Hotspot.class, str);
        }

        public static Hotspot[] values() {
            return (Hotspot[]) $VALUES.clone();
        }
    }

    static {
        Interpolator interpolator = Interpolators.EMPHASIZED;
        DEFAULT_FADE_IN_INTERPOLATOR = Interpolators.ALPHA_IN;
        final Bound.LEFT left = Bound.LEFT;
        final String label = left.getLabel();
        Pair pair = new Pair(left, new IntProperty(label) { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createViewProperty$1
            @Override // android.util.Property
            public final Integer get(Object obj) {
                View view = (View) obj;
                Integer access$getBound = ViewHierarchyAnimator.Companion.access$getBound(ViewHierarchyAnimator.Companion, view, ViewHierarchyAnimator.Bound.this);
                return Integer.valueOf(access$getBound != null ? access$getBound.intValue() : ViewHierarchyAnimator.Bound.this.getValue(view));
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                ViewHierarchyAnimator.Bound bound = ViewHierarchyAnimator.Bound.this;
                companion.getClass();
                ViewHierarchyAnimator.Companion.setBound((View) obj, bound, i);
            }
        });
        final Bound.TOP top = Bound.TOP;
        final String label2 = top.getLabel();
        Pair pair2 = new Pair(top, new IntProperty(label2) { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createViewProperty$1
            @Override // android.util.Property
            public final Integer get(Object obj) {
                View view = (View) obj;
                Integer access$getBound = ViewHierarchyAnimator.Companion.access$getBound(ViewHierarchyAnimator.Companion, view, ViewHierarchyAnimator.Bound.this);
                return Integer.valueOf(access$getBound != null ? access$getBound.intValue() : ViewHierarchyAnimator.Bound.this.getValue(view));
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                ViewHierarchyAnimator.Bound bound = ViewHierarchyAnimator.Bound.this;
                companion.getClass();
                ViewHierarchyAnimator.Companion.setBound((View) obj, bound, i);
            }
        });
        final Bound.RIGHT right = Bound.RIGHT;
        final String label3 = right.getLabel();
        Pair pair3 = new Pair(right, new IntProperty(label3) { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createViewProperty$1
            @Override // android.util.Property
            public final Integer get(Object obj) {
                View view = (View) obj;
                Integer access$getBound = ViewHierarchyAnimator.Companion.access$getBound(ViewHierarchyAnimator.Companion, view, ViewHierarchyAnimator.Bound.this);
                return Integer.valueOf(access$getBound != null ? access$getBound.intValue() : ViewHierarchyAnimator.Bound.this.getValue(view));
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                ViewHierarchyAnimator.Bound bound = ViewHierarchyAnimator.Bound.this;
                companion.getClass();
                ViewHierarchyAnimator.Companion.setBound((View) obj, bound, i);
            }
        });
        final Bound.BOTTOM bottom = Bound.BOTTOM;
        final String label4 = bottom.getLabel();
        PROPERTIES = MapsKt__MapsKt.mapOf(pair, pair2, pair3, new Pair(bottom, new IntProperty(label4) { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createViewProperty$1
            @Override // android.util.Property
            public final Integer get(Object obj) {
                View view = (View) obj;
                Integer access$getBound = ViewHierarchyAnimator.Companion.access$getBound(ViewHierarchyAnimator.Companion, view, ViewHierarchyAnimator.Bound.this);
                return Integer.valueOf(access$getBound != null ? access$getBound.intValue() : ViewHierarchyAnimator.Bound.this.getValue(view));
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                ViewHierarchyAnimator.Bound bound = ViewHierarchyAnimator.Bound.this;
                companion.getClass();
                ViewHierarchyAnimator.Companion.setBound((View) obj, bound, i);
            }
        }));
    }
}
