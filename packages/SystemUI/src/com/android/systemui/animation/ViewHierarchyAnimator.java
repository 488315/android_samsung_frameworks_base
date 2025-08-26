package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.IntProperty;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.animation.ViewHierarchyAnimator;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewOut$fullEndRunnable$1;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ViewHierarchyAnimator {
    public static final Companion Companion = new Companion(null);
    public static final Interpolator DEFAULT_FADE_IN_INTERPOLATOR;
    public static final Interpolator DEFAULT_INTERPOLATOR = null;
    public static final Map PROPERTIES;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    abstract class Bound {
        public static final /* synthetic */ Bound[] $VALUES;
        public static final BOTTOM BOTTOM;
        public static final LEFT LEFT;
        public static final RIGHT RIGHT;
        public static final TOP TOP;
        private final String label;
        private final int overrideTag;

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

    public final class Companion {

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

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final Integer access$getBound(Companion companion, View view, Bound bound) {
            companion.getClass();
            Object tag = view.getTag(bound.getOverrideTag());
            if (tag instanceof Integer) {
                return (Integer) tag;
            }
            return null;
        }

        public static void addListener(View view, ViewHierarchyAnimator$Companion$createListener$1 viewHierarchyAnimator$Companion$createListener$1, boolean z, Set set) {
            if (set.contains(view)) {
                return;
            }
            Object tag = view.getTag(R.id.tag_layout_listener);
            if (tag != null && (tag instanceof View.OnLayoutChangeListener)) {
                view.removeOnLayoutChangeListener((View.OnLayoutChangeListener) tag);
            }
            view.addOnLayoutChangeListener(viewHierarchyAnimator$Companion$createListener$1);
            view.setTag(R.id.tag_layout_listener, viewHierarchyAnimator$Companion$createListener$1);
            if ((view instanceof ViewGroup) && z) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    addListener(viewGroup.getChildAt(i), viewHierarchyAnimator$Companion$createListener$1, true, set);
                }
            }
        }

        public static void addListener$default(Companion companion, View view, ViewHierarchyAnimator$Companion$createListener$1 viewHierarchyAnimator$Companion$createListener$1, boolean z) {
            EmptySet emptySet = EmptySet.INSTANCE;
            companion.getClass();
            addListener(view, viewHierarchyAnimator$Companion$createListener$1, z, emptySet);
        }

        public static void createAndStartFadeInAnimator(final View view, long j, long j2, Interpolator interpolator) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f);
            objectAnimatorOfFloat.setStartDelay(j2);
            objectAnimatorOfFloat.setDuration(j);
            objectAnimatorOfFloat.setInterpolator(interpolator);
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createAndStartFadeInAnimator$1
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
            view.setTag(R.id.tag_alpha_animator, objectAnimatorOfFloat);
            objectAnimatorOfFloat.start();
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
            ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Bound bound = (Bound) it.next();
                listBuilderCreateListBuilder.add(PropertyValuesHolder.ofInt((Property<?, Integer>) ViewHierarchyAnimator.PROPERTIES.get(bound), ((Number) MapsKt__MapsKt.getValue(bound, map)).intValue(), ((Number) MapsKt__MapsKt.getValue(bound, map2)).intValue()));
            }
            PropertyValuesHolder[] propertyValuesHolderArr = (PropertyValuesHolder[]) listBuilderCreateListBuilder.build().toArray(new PropertyValuesHolder[0]);
            Object tag = view.getTag(R.id.tag_animator);
            ObjectAnimator objectAnimator = tag instanceof ObjectAnimator ? (ObjectAnimator) tag : null;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, (PropertyValuesHolder[]) Arrays.copyOf(propertyValuesHolderArr, propertyValuesHolderArr.length));
            objectAnimatorOfPropertyValuesHolder.setInterpolator(interpolator);
            objectAnimatorOfPropertyValuesHolder.setDuration(j);
            objectAnimatorOfPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$startAnimation$1
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.cancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
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
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            Iterator it2 = set.iterator();
            while (it2.hasNext()) {
                Bound bound2 = (Bound) it2.next();
                Companion companion = ViewHierarchyAnimator.Companion;
                int iIntValue = ((Number) MapsKt__MapsKt.getValue(bound2, map)).intValue();
                companion.getClass();
                setBound(view, bound2, iIntValue);
            }
            view.setTag(R.id.tag_animator, objectAnimatorOfPropertyValuesHolder);
            objectAnimatorOfPropertyValuesHolder.start();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:104:0x047a A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:74:0x03f5  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x03f7 A[PHI: r15
          0x03f7: PHI (r15v13 int) = (r15v11 int), (r15v12 int) binds: [B:71:0x03ec, B:76:0x03fa] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:76:0x03fa  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x03fd A[PHI: r13
          0x03fd: PHI (r13v6 int) = (r13v5 int), (r13v7 int), (r13v8 int) binds: [B:71:0x03ec, B:75:0x03f7, B:74:0x03f5] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:79:0x043e  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x0451  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x0464  */
        /* JADX WARN: Removed duplicated region for block: B:88:0x0477  */
        /* JADX WARN: Removed duplicated region for block: B:99:0x03ef A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean animateRemoval(final View view, Hotspot hotspot, Interpolator interpolator, final ChipbarCoordinator$animateViewOut$fullEndRunnable$1 chipbarCoordinator$animateViewOut$fullEndRunnable$1) {
            boolean z;
            int i;
            DimenHolder dimenHolder;
            int i2;
            Map mapMapOf;
            if (!occupiesSpace(view.getVisibility(), view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                return false;
            }
            final ViewGroup viewGroup = (ViewGroup) view.getParent();
            long j = 250;
            ViewHierarchyAnimator$Companion$createListener$1 viewHierarchyAnimator$Companion$createListener$1 = new ViewHierarchyAnimator$Companion$createListener$1(null, false, interpolator, 250L, true, null);
            int childCount = viewGroup.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = viewGroup.getChildAt(i3);
                if (!Intrinsics.areEqual(childAt, view)) {
                    childAt.getClass();
                    addListener$default(this, childAt, viewHierarchyAnimator$Companion$createListener$1, false);
                }
            }
            final boolean z2 = viewGroup.getChildCount() > 1;
            if (z2) {
                viewGroup.removeView(view);
                viewGroup.getOverlay().add(view);
            }
            Runnable runnable = new Runnable() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$animateRemoval$endRunnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (z2) {
                        viewGroup.getOverlay().remove(view);
                    } else {
                        viewGroup.removeView(view);
                    }
                    Runnable runnable2 = chipbarCoordinator$animateViewOut$fullEndRunnable$1;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            };
            Bound.LEFT left = Bound.LEFT;
            Pair pair = new Pair(left, Integer.valueOf(view.getLeft()));
            Bound.TOP top = Bound.TOP;
            Pair pair2 = new Pair(top, Integer.valueOf(view.getTop()));
            Bound.RIGHT right = Bound.RIGHT;
            Pair pair3 = new Pair(right, Integer.valueOf(view.getRight()));
            Bound.BOTTOM bottom = Bound.BOTTOM;
            Map mapMapOf2 = MapsKt__MapsKt.mapOf(pair, pair2, pair3, new Pair(bottom, Integer.valueOf(view.getBottom())));
            int left2 = view.getLeft();
            int top2 = view.getTop();
            int right2 = view.getRight();
            int bottom2 = view.getBottom();
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                z = true;
                i = 2;
                dimenHolder = new DimenHolder(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                i2 = 0;
            } else {
                z = true;
                i = 2;
                i2 = 0;
                dimenHolder = new DimenHolder(0, 0, 0, 0);
            }
            int i4 = left2 - dimenHolder.left;
            int i5 = top2 - dimenHolder.top;
            int i6 = dimenHolder.right + right2;
            int i7 = dimenHolder.bottom + bottom2;
            switch (WhenMappings.$EnumSwitchMapping$0[hotspot.ordinal()]) {
                case 1:
                    int i8 = (i4 + i6) / 2;
                    int i9 = (i5 + i7) / 2;
                    mapMapOf = MapsKt__MapsKt.mapOf(new Pair(left, Integer.valueOf(i8)), new Pair(right, Integer.valueOf(i8)), new Pair(top, Integer.valueOf(i9)), new Pair(bottom, Integer.valueOf(i9)));
                    break;
                case 2:
                    mapMapOf = MapsKt__MapsKt.mapOf(new Pair(bottom, Integer.valueOf(i7)), new Pair(top, Integer.valueOf(i7)), new Pair(left, Integer.valueOf(i4)), new Pair(right, Integer.valueOf(i4)));
                    break;
                case 3:
                    mapMapOf = MapsKt__MapsKt.mapOf(new Pair(left, Integer.valueOf(i4)), new Pair(right, Integer.valueOf(i4)), new Pair(top, Integer.valueOf(top2)), new Pair(bottom, Integer.valueOf(bottom2)));
                    break;
                case 4:
                    mapMapOf = MapsKt__MapsKt.mapOf(new Pair(top, Integer.valueOf(i5)), new Pair(bottom, Integer.valueOf(i5)), new Pair(left, Integer.valueOf(i4)), new Pair(right, Integer.valueOf(i4)));
                    break;
                case 5:
                    mapMapOf = MapsKt__MapsKt.mapOf(new Pair(top, Integer.valueOf(i5)), new Pair(bottom, Integer.valueOf(i5)), new Pair(left, Integer.valueOf(left2)), new Pair(right, Integer.valueOf(right2)));
                    break;
                case 6:
                    mapMapOf = MapsKt__MapsKt.mapOf(new Pair(bottom, Integer.valueOf(i7)), new Pair(top, Integer.valueOf(i7)), new Pair(left, Integer.valueOf(left2)), new Pair(right, Integer.valueOf(right2)));
                    break;
                case 7:
                    mapMapOf = MapsKt__MapsKt.mapOf(new Pair(top, Integer.valueOf(i5)), new Pair(bottom, Integer.valueOf(i5)), new Pair(right, Integer.valueOf(i6)), new Pair(left, Integer.valueOf(i6)));
                    break;
                case 8:
                    mapMapOf = MapsKt__MapsKt.mapOf(new Pair(right, Integer.valueOf(i6)), new Pair(left, Integer.valueOf(i6)), new Pair(top, Integer.valueOf(top2)), new Pair(bottom, Integer.valueOf(bottom2)));
                    break;
                case 9:
                    mapMapOf = MapsKt__MapsKt.mapOf(new Pair(bottom, Integer.valueOf(i7)), new Pair(top, Integer.valueOf(i7)), new Pair(right, Integer.valueOf(i6)), new Pair(left, Integer.valueOf(i6)));
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            if (view.getLeft() != ((Number) MapsKt__MapsKt.getValue(left, mapMapOf)).intValue()) {
                linkedHashSet.add(left);
            }
            if (view.getTop() != ((Number) MapsKt__MapsKt.getValue(top, mapMapOf)).intValue()) {
                linkedHashSet.add(top);
            }
            if (view.getRight() != ((Number) MapsKt__MapsKt.getValue(right, mapMapOf)).intValue()) {
                linkedHashSet.add(right);
            }
            if (view.getBottom() != ((Number) MapsKt__MapsKt.getValue(bottom, mapMapOf)).intValue()) {
                linkedHashSet.add(bottom);
            }
            Map map = mapMapOf;
            startAnimation(view, linkedHashSet, mapMapOf2, map, interpolator, 250L, true, runnable);
            ViewGroup viewGroup2 = (ViewGroup) view;
            int childCount2 = viewGroup2.getChildCount();
            int i10 = i2;
            while (i10 < childCount2) {
                View childAt2 = viewGroup2.getChildAt(i10);
                Bound.LEFT left3 = Bound.LEFT;
                Pair pair4 = new Pair(left3, Integer.valueOf(childAt2.getLeft()));
                Bound.TOP top3 = Bound.TOP;
                Pair pair5 = new Pair(top3, Integer.valueOf(childAt2.getTop()));
                Bound.RIGHT right3 = Bound.RIGHT;
                Pair pair6 = new Pair(right3, Integer.valueOf(childAt2.getRight()));
                Bound.BOTTOM bottom3 = Bound.BOTTOM;
                Map mapMapOf3 = MapsKt__MapsKt.mapOf(pair4, pair5, pair6, new Pair(bottom3, Integer.valueOf(childAt2.getBottom())));
                int left4 = childAt2.getLeft();
                int top4 = childAt2.getTop();
                int right4 = childAt2.getRight();
                int bottom4 = childAt2.getBottom();
                int iIntValue = ((Number) MapsKt__MapsKt.getValue(right3, map)).intValue() - ((Number) MapsKt__MapsKt.getValue(left3, map)).intValue();
                int iIntValue2 = ((Number) MapsKt__MapsKt.getValue(bottom3, map)).intValue() - ((Number) MapsKt__MapsKt.getValue(top3, map)).intValue();
                int i11 = (right4 - left4) / 2;
                int i12 = childCount2;
                int i13 = (bottom4 - top4) / 2;
                int[] iArr = WhenMappings.$EnumSwitchMapping$0;
                switch (iArr[hotspot.ordinal()]) {
                    case 1:
                        left4 = (iIntValue / 2) - i11;
                        break;
                    case 2:
                    case 3:
                    case 4:
                        left4 = -i11;
                        break;
                    case 5:
                    case 6:
                        break;
                    case 7:
                    case 8:
                    case 9:
                        left4 = iIntValue - i11;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                switch (iArr[hotspot.ordinal()]) {
                    case 1:
                        top4 = (iIntValue2 / 2) - i13;
                        break;
                    case 2:
                    case 6:
                    case 9:
                        top4 = iIntValue2 - i13;
                        break;
                    case 3:
                    case 8:
                        break;
                    case 4:
                    case 5:
                    case 7:
                        top4 = -i13;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
                switch (iArr[hotspot.ordinal()]) {
                    case 1:
                        iIntValue /= 2;
                        right4 = iIntValue + i11;
                        switch (iArr[hotspot.ordinal()]) {
                            case 1:
                                iIntValue2 /= 2;
                                bottom4 = iIntValue2 + i13;
                                Map mapMapOf4 = MapsKt__MapsKt.mapOf(new Pair(left3, Integer.valueOf(left4)), new Pair(top3, Integer.valueOf(top4)), new Pair(right3, Integer.valueOf(right4)), new Pair(bottom3, Integer.valueOf(bottom4)));
                                LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                                if (childAt2.getLeft() != ((Number) MapsKt__MapsKt.getValue(left3, map)).intValue()) {
                                    linkedHashSet2.add(left3);
                                }
                                if (childAt2.getTop() != ((Number) MapsKt__MapsKt.getValue(top3, map)).intValue()) {
                                    linkedHashSet2.add(top3);
                                }
                                if (childAt2.getRight() != ((Number) MapsKt__MapsKt.getValue(right3, map)).intValue()) {
                                    linkedHashSet2.add(right3);
                                }
                                if (childAt2.getBottom() == ((Number) MapsKt__MapsKt.getValue(bottom3, map)).intValue()) {
                                    linkedHashSet2.add(bottom3);
                                }
                                long j2 = j;
                                startAnimation(childAt2, linkedHashSet2, mapMapOf3, mapMapOf4, interpolator, j2, true, null);
                                i10++;
                                childCount2 = i12;
                                j = j2;
                            case 2:
                            case 6:
                            case 9:
                                bottom4 = iIntValue2 + i13;
                                Map mapMapOf42 = MapsKt__MapsKt.mapOf(new Pair(left3, Integer.valueOf(left4)), new Pair(top3, Integer.valueOf(top4)), new Pair(right3, Integer.valueOf(right4)), new Pair(bottom3, Integer.valueOf(bottom4)));
                                LinkedHashSet linkedHashSet22 = new LinkedHashSet();
                                if (childAt2.getLeft() != ((Number) MapsKt__MapsKt.getValue(left3, map)).intValue()) {
                                }
                                if (childAt2.getTop() != ((Number) MapsKt__MapsKt.getValue(top3, map)).intValue()) {
                                }
                                if (childAt2.getRight() != ((Number) MapsKt__MapsKt.getValue(right3, map)).intValue()) {
                                }
                                if (childAt2.getBottom() == ((Number) MapsKt__MapsKt.getValue(bottom3, map)).intValue()) {
                                }
                                long j22 = j;
                                startAnimation(childAt2, linkedHashSet22, mapMapOf3, mapMapOf42, interpolator, j22, true, null);
                                i10++;
                                childCount2 = i12;
                                j = j22;
                                break;
                            case 3:
                            case 8:
                                Map mapMapOf422 = MapsKt__MapsKt.mapOf(new Pair(left3, Integer.valueOf(left4)), new Pair(top3, Integer.valueOf(top4)), new Pair(right3, Integer.valueOf(right4)), new Pair(bottom3, Integer.valueOf(bottom4)));
                                LinkedHashSet linkedHashSet222 = new LinkedHashSet();
                                if (childAt2.getLeft() != ((Number) MapsKt__MapsKt.getValue(left3, map)).intValue()) {
                                }
                                if (childAt2.getTop() != ((Number) MapsKt__MapsKt.getValue(top3, map)).intValue()) {
                                }
                                if (childAt2.getRight() != ((Number) MapsKt__MapsKt.getValue(right3, map)).intValue()) {
                                }
                                if (childAt2.getBottom() == ((Number) MapsKt__MapsKt.getValue(bottom3, map)).intValue()) {
                                }
                                long j222 = j;
                                startAnimation(childAt2, linkedHashSet222, mapMapOf3, mapMapOf422, interpolator, j222, true, null);
                                i10++;
                                childCount2 = i12;
                                j = j222;
                                break;
                            case 4:
                            case 5:
                            case 7:
                                bottom4 = i13;
                                Map mapMapOf4222 = MapsKt__MapsKt.mapOf(new Pair(left3, Integer.valueOf(left4)), new Pair(top3, Integer.valueOf(top4)), new Pair(right3, Integer.valueOf(right4)), new Pair(bottom3, Integer.valueOf(bottom4)));
                                LinkedHashSet linkedHashSet2222 = new LinkedHashSet();
                                if (childAt2.getLeft() != ((Number) MapsKt__MapsKt.getValue(left3, map)).intValue()) {
                                }
                                if (childAt2.getTop() != ((Number) MapsKt__MapsKt.getValue(top3, map)).intValue()) {
                                }
                                if (childAt2.getRight() != ((Number) MapsKt__MapsKt.getValue(right3, map)).intValue()) {
                                }
                                if (childAt2.getBottom() == ((Number) MapsKt__MapsKt.getValue(bottom3, map)).intValue()) {
                                }
                                long j2222 = j;
                                startAnimation(childAt2, linkedHashSet2222, mapMapOf3, mapMapOf4222, interpolator, j2222, true, null);
                                i10++;
                                childCount2 = i12;
                                j = j2222;
                                break;
                            default:
                                throw new NoWhenBranchMatchedException();
                        }
                    case 2:
                    case 3:
                    case 4:
                        right4 = i11;
                        switch (iArr[hotspot.ordinal()]) {
                        }
                    case 5:
                    case 6:
                        switch (iArr[hotspot.ordinal()]) {
                        }
                    case 7:
                    case 8:
                    case 9:
                        right4 = iIntValue + i11;
                        switch (iArr[hotspot.ordinal()]) {
                        }
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }
            final long j3 = j;
            final float[] fArr = new float[viewGroup2.getChildCount()];
            int childCount3 = viewGroup2.getChildCount();
            for (int i14 = 0; i14 < childCount3; i14++) {
                fArr[i14] = viewGroup2.getChildAt(i14).getAlpha();
            }
            int i15 = i;
            float[] fArr2 = new float[i15];
            // fill-array-data instruction
            fArr2[0] = 1.0f;
            fArr2[1] = 0.0f;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(fArr2);
            valueAnimatorOfFloat.setInterpolator(Interpolators.ALPHA_OUT);
            valueAnimatorOfFloat.setDuration(j3 / i15);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$animateRemoval$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int childCount4 = ((ViewGroup) view).getChildCount();
                    for (int i16 = 0; i16 < childCount4; i16++) {
                        ((ViewGroup) view).getChildAt(i16).setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue() * fArr[i16]);
                    }
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$animateRemoval$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.animate().alpha(0.0f).setInterpolator(Interpolators.ALPHA_OUT).setDuration(j3 / 2).start();
                }
            });
            valueAnimatorOfFloat.start();
            return z;
        }

        private Companion() {
        }
    }

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
            return Integer.hashCode(this.bottom) + ReorderTile$$ExternalSyntheticOutline0.m(this.right, ReorderTile$$ExternalSyntheticOutline0.m(this.top, Integer.hashCode(this.left) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DimenHolder(left=");
            sb.append(this.left);
            sb.append(", top=");
            sb.append(this.top);
            sb.append(", right=");
            sb.append(this.right);
            sb.append(", bottom=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.bottom, ")", sb);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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
        Interpolator interpolator = Interpolators.STANDARD;
        Interpolator interpolator2 = Interpolators.EMPHASIZED;
        DEFAULT_FADE_IN_INTERPOLATOR = Interpolators.ALPHA_IN;
        final Bound.LEFT left = Bound.LEFT;
        final String label = left.getLabel();
        Pair pair = new Pair(left, new IntProperty(label) { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$createViewProperty$1
            @Override // android.util.Property
            public final Integer get(Object obj) {
                View view = (View) obj;
                Integer numAccess$getBound = ViewHierarchyAnimator.Companion.access$getBound(ViewHierarchyAnimator.Companion, view, left);
                return Integer.valueOf(numAccess$getBound != null ? numAccess$getBound.intValue() : left.getValue(view));
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                ViewHierarchyAnimator.Bound bound = left;
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
                Integer numAccess$getBound = ViewHierarchyAnimator.Companion.access$getBound(ViewHierarchyAnimator.Companion, view, top);
                return Integer.valueOf(numAccess$getBound != null ? numAccess$getBound.intValue() : top.getValue(view));
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                ViewHierarchyAnimator.Bound bound = top;
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
                Integer numAccess$getBound = ViewHierarchyAnimator.Companion.access$getBound(ViewHierarchyAnimator.Companion, view, right);
                return Integer.valueOf(numAccess$getBound != null ? numAccess$getBound.intValue() : right.getValue(view));
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                ViewHierarchyAnimator.Bound bound = right;
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
                Integer numAccess$getBound = ViewHierarchyAnimator.Companion.access$getBound(ViewHierarchyAnimator.Companion, view, bottom);
                return Integer.valueOf(numAccess$getBound != null ? numAccess$getBound.intValue() : bottom.getValue(view));
            }

            @Override // android.util.IntProperty
            public final void setValue(Object obj, int i) {
                ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
                ViewHierarchyAnimator.Bound bound = bottom;
                companion.getClass();
                ViewHierarchyAnimator.Companion.setBound((View) obj, bound, i);
            }
        }));
    }
}
