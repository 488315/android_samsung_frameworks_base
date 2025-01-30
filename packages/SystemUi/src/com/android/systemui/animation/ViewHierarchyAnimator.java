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
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.animation.ViewHierarchyAnimator;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator$animateViewOut$fullEndRunnable$1;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewHierarchyAnimator {
    public static final Companion Companion = new Companion(null);
    public static final Interpolator DEFAULT_FADE_IN_INTERPOLATOR;
    public static final Map PROPERTIES;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    abstract class Bound {
        public static final /* synthetic */ Bound[] $VALUES;
        public static final BOTTOM BOTTOM;
        public static final LEFT LEFT;
        public static final RIGHT RIGHT;
        public static final TOP TOP;
        private final String label;
        private final int overrideTag;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class BOTTOM extends Bound {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class LEFT extends Bound {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class RIGHT extends Bound {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class TOP extends Bound {
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
            $VALUES = new Bound[]{left, top, right, bottom};
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        public static void addListener(View view, ViewHierarchyAnimator$Companion$createListener$1 viewHierarchyAnimator$Companion$createListener$1, boolean z) {
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
                    addListener(viewGroup.getChildAt(i), viewHierarchyAnimator$Companion$createListener$1, true);
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:53:0x03c3  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x03c5  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x03c8  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x03da  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x03dc  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x03de A[PHI: r14
          0x03de: PHI (r14v9 int) = (r14v8 int), (r14v10 int) binds: [B:57:0x03d1, B:59:0x03dc] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:61:0x03e0 A[PHI: r12
          0x03e0: PHI (r12v13 int) = (r12v12 int), (r12v15 int), (r12v16 int) binds: [B:57:0x03d1, B:60:0x03de, B:58:0x03da] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:63:0x03ef  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x03f1  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x03f3 A[PHI: r15
          0x03f3: PHI (r15v9 int) = (r15v8 int), (r15v10 int) binds: [B:62:0x03e6, B:64:0x03f1] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:66:0x03f5 A[PHI: r13
          0x03f5: PHI (r13v9 int) = (r13v8 int), (r13v10 int), (r13v11 int) binds: [B:62:0x03e6, B:65:0x03f3, B:63:0x03ef] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0436  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x0449  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x045c  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x046f  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x0472 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:81:0x03e9 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:84:0x03d4 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:87:0x03bd A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static boolean animateRemoval(final View view, Hotspot hotspot, Interpolator interpolator, final ChipbarCoordinator$animateViewOut$fullEndRunnable$1 chipbarCoordinator$animateViewOut$fullEndRunnable$1) {
            DimenHolder dimenHolder;
            Map mapOf;
            int i;
            if (!((view.getVisibility() == 8 || view.getLeft() == view.getRight() || view.getTop() == view.getBottom()) ? false : true)) {
                return false;
            }
            final ViewGroup viewGroup = (ViewGroup) view.getParent();
            ViewHierarchyAnimator$Companion$createListener$1 viewHierarchyAnimator$Companion$createListener$1 = new ViewHierarchyAnimator$Companion$createListener$1(null, false, interpolator, 250L, true, null);
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (!Intrinsics.areEqual(childAt, view)) {
                    addListener(childAt, viewHierarchyAnimator$Companion$createListener$1, false);
                }
            }
            final boolean z = viewGroup.getChildCount() > 1;
            if (z) {
                viewGroup.removeView(view);
                viewGroup.getOverlay().add(view);
            }
            Runnable runnable = new Runnable() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$animateRemoval$endRunnable$1
                @Override // java.lang.Runnable
                public final void run() {
                    if (z) {
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
            Map mapOf2 = MapsKt__MapsKt.mapOf(pair, pair2, pair3, new Pair(bottom, Integer.valueOf(view.getBottom())));
            int left2 = view.getLeft();
            int top2 = view.getTop();
            int right2 = view.getRight();
            int bottom2 = view.getBottom();
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                dimenHolder = new DimenHolder(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            } else {
                dimenHolder = new DimenHolder(0, 0, 0, 0);
            }
            int i3 = left2 - dimenHolder.left;
            int i4 = top2 - dimenHolder.top;
            int i5 = dimenHolder.right + right2;
            int i6 = dimenHolder.bottom + bottom2;
            switch (WhenMappings.$EnumSwitchMapping$0[hotspot.ordinal()]) {
                case 1:
                    int i7 = (i3 + i5) / 2;
                    int i8 = (i4 + i6) / 2;
                    mapOf = MapsKt__MapsKt.mapOf(new Pair(left, Integer.valueOf(i7)), new Pair(right, Integer.valueOf(i7)), new Pair(top, Integer.valueOf(i8)), new Pair(bottom, Integer.valueOf(i8)));
                    break;
                case 2:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair(bottom, Integer.valueOf(i6)), new Pair(top, Integer.valueOf(i6)), new Pair(left, Integer.valueOf(i3)), new Pair(right, Integer.valueOf(i3)));
                    break;
                case 3:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair(left, Integer.valueOf(i3)), new Pair(right, Integer.valueOf(i3)), new Pair(top, Integer.valueOf(top2)), new Pair(bottom, Integer.valueOf(bottom2)));
                    break;
                case 4:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair(top, Integer.valueOf(i4)), new Pair(bottom, Integer.valueOf(i4)), new Pair(left, Integer.valueOf(i3)), new Pair(right, Integer.valueOf(i3)));
                    break;
                case 5:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair(top, Integer.valueOf(i4)), new Pair(bottom, Integer.valueOf(i4)), new Pair(left, Integer.valueOf(left2)), new Pair(right, Integer.valueOf(right2)));
                    break;
                case 6:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair(bottom, Integer.valueOf(i6)), new Pair(top, Integer.valueOf(i6)), new Pair(left, Integer.valueOf(left2)), new Pair(right, Integer.valueOf(right2)));
                    break;
                case 7:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair(top, Integer.valueOf(i4)), new Pair(bottom, Integer.valueOf(i4)), new Pair(right, Integer.valueOf(i5)), new Pair(left, Integer.valueOf(i5)));
                    break;
                case 8:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair(right, Integer.valueOf(i5)), new Pair(left, Integer.valueOf(i5)), new Pair(top, Integer.valueOf(top2)), new Pair(bottom, Integer.valueOf(bottom2)));
                    break;
                case 9:
                    mapOf = MapsKt__MapsKt.mapOf(new Pair(bottom, Integer.valueOf(i6)), new Pair(top, Integer.valueOf(i6)), new Pair(right, Integer.valueOf(i5)), new Pair(left, Integer.valueOf(i5)));
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            Map map = mapOf;
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            if (view.getLeft() != ((Number) MapsKt__MapsKt.getValue(map, left)).intValue()) {
                linkedHashSet.add(left);
            }
            if (view.getTop() != ((Number) MapsKt__MapsKt.getValue(map, top)).intValue()) {
                linkedHashSet.add(top);
            }
            if (view.getRight() != ((Number) MapsKt__MapsKt.getValue(map, right)).intValue()) {
                linkedHashSet.add(right);
            }
            if (view.getBottom() != ((Number) MapsKt__MapsKt.getValue(map, bottom)).intValue()) {
                linkedHashSet.add(bottom);
            }
            startAnimation(view, linkedHashSet, mapOf2, map, interpolator, 250L, true, runnable);
            ViewGroup viewGroup2 = (ViewGroup) view;
            int childCount2 = viewGroup2.getChildCount();
            int i9 = 0;
            while (i9 < childCount2) {
                View childAt2 = viewGroup2.getChildAt(i9);
                Bound.LEFT left3 = Bound.LEFT;
                Pair pair4 = new Pair(left3, Integer.valueOf(childAt2.getLeft()));
                Bound.TOP top3 = Bound.TOP;
                Pair pair5 = new Pair(top3, Integer.valueOf(childAt2.getTop()));
                Bound.RIGHT right3 = Bound.RIGHT;
                Pair pair6 = new Pair(right3, Integer.valueOf(childAt2.getRight()));
                Bound.BOTTOM bottom3 = Bound.BOTTOM;
                Map mapOf3 = MapsKt__MapsKt.mapOf(pair4, pair5, pair6, new Pair(bottom3, Integer.valueOf(childAt2.getBottom())));
                int left4 = childAt2.getLeft();
                int top4 = childAt2.getTop();
                int right4 = childAt2.getRight();
                int bottom4 = childAt2.getBottom();
                int intValue = ((Number) MapsKt__MapsKt.getValue(map, right3)).intValue() - ((Number) MapsKt__MapsKt.getValue(map, left3)).intValue();
                int intValue2 = ((Number) MapsKt__MapsKt.getValue(map, bottom3)).intValue() - ((Number) MapsKt__MapsKt.getValue(map, top3)).intValue();
                int i10 = childCount2;
                int i11 = (right4 - left4) / 2;
                int i12 = left4;
                int i13 = (bottom4 - top4) / 2;
                int[] iArr = WhenMappings.$EnumSwitchMapping$0;
                switch (iArr[hotspot.ordinal()]) {
                    case 1:
                        i = (intValue / 2) - i11;
                        i12 = i;
                        switch (iArr[hotspot.ordinal()]) {
                            case 1:
                                top4 = (intValue2 / 2) - i13;
                                break;
                            case 2:
                            case 6:
                            case 9:
                                top4 = intValue2 - i13;
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
                                intValue /= 2;
                                right4 = intValue + i11;
                                switch (iArr[hotspot.ordinal()]) {
                                    case 1:
                                        intValue2 /= 2;
                                        bottom4 = intValue2 + i13;
                                        Map mapOf4 = MapsKt__MapsKt.mapOf(new Pair(left3, Integer.valueOf(i12)), new Pair(top3, Integer.valueOf(top4)), new Pair(right3, Integer.valueOf(right4)), new Pair(bottom3, Integer.valueOf(bottom4)));
                                        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                                        if (childAt2.getLeft() != ((Number) MapsKt__MapsKt.getValue(map, left3)).intValue()) {
                                            linkedHashSet2.add(left3);
                                        }
                                        if (childAt2.getTop() != ((Number) MapsKt__MapsKt.getValue(map, top3)).intValue()) {
                                            linkedHashSet2.add(top3);
                                        }
                                        if (childAt2.getRight() != ((Number) MapsKt__MapsKt.getValue(map, right3)).intValue()) {
                                            linkedHashSet2.add(right3);
                                        }
                                        if (childAt2.getBottom() == ((Number) MapsKt__MapsKt.getValue(map, bottom3)).intValue()) {
                                            linkedHashSet2.add(bottom3);
                                        }
                                        startAnimation(childAt2, linkedHashSet2, mapOf3, mapOf4, interpolator, 250L, true, null);
                                        i9++;
                                        childCount2 = i10;
                                    case 2:
                                    case 6:
                                    case 9:
                                        bottom4 = intValue2 + i13;
                                        Map mapOf42 = MapsKt__MapsKt.mapOf(new Pair(left3, Integer.valueOf(i12)), new Pair(top3, Integer.valueOf(top4)), new Pair(right3, Integer.valueOf(right4)), new Pair(bottom3, Integer.valueOf(bottom4)));
                                        LinkedHashSet linkedHashSet22 = new LinkedHashSet();
                                        if (childAt2.getLeft() != ((Number) MapsKt__MapsKt.getValue(map, left3)).intValue()) {
                                        }
                                        if (childAt2.getTop() != ((Number) MapsKt__MapsKt.getValue(map, top3)).intValue()) {
                                        }
                                        if (childAt2.getRight() != ((Number) MapsKt__MapsKt.getValue(map, right3)).intValue()) {
                                        }
                                        if (childAt2.getBottom() == ((Number) MapsKt__MapsKt.getValue(map, bottom3)).intValue()) {
                                        }
                                        startAnimation(childAt2, linkedHashSet22, mapOf3, mapOf42, interpolator, 250L, true, null);
                                        i9++;
                                        childCount2 = i10;
                                        break;
                                    case 3:
                                    case 8:
                                        Map mapOf422 = MapsKt__MapsKt.mapOf(new Pair(left3, Integer.valueOf(i12)), new Pair(top3, Integer.valueOf(top4)), new Pair(right3, Integer.valueOf(right4)), new Pair(bottom3, Integer.valueOf(bottom4)));
                                        LinkedHashSet linkedHashSet222 = new LinkedHashSet();
                                        if (childAt2.getLeft() != ((Number) MapsKt__MapsKt.getValue(map, left3)).intValue()) {
                                        }
                                        if (childAt2.getTop() != ((Number) MapsKt__MapsKt.getValue(map, top3)).intValue()) {
                                        }
                                        if (childAt2.getRight() != ((Number) MapsKt__MapsKt.getValue(map, right3)).intValue()) {
                                        }
                                        if (childAt2.getBottom() == ((Number) MapsKt__MapsKt.getValue(map, bottom3)).intValue()) {
                                        }
                                        startAnimation(childAt2, linkedHashSet222, mapOf3, mapOf422, interpolator, 250L, true, null);
                                        i9++;
                                        childCount2 = i10;
                                        break;
                                    case 4:
                                    case 5:
                                    case 7:
                                        bottom4 = i13;
                                        Map mapOf4222 = MapsKt__MapsKt.mapOf(new Pair(left3, Integer.valueOf(i12)), new Pair(top3, Integer.valueOf(top4)), new Pair(right3, Integer.valueOf(right4)), new Pair(bottom3, Integer.valueOf(bottom4)));
                                        LinkedHashSet linkedHashSet2222 = new LinkedHashSet();
                                        if (childAt2.getLeft() != ((Number) MapsKt__MapsKt.getValue(map, left3)).intValue()) {
                                        }
                                        if (childAt2.getTop() != ((Number) MapsKt__MapsKt.getValue(map, top3)).intValue()) {
                                        }
                                        if (childAt2.getRight() != ((Number) MapsKt__MapsKt.getValue(map, right3)).intValue()) {
                                        }
                                        if (childAt2.getBottom() == ((Number) MapsKt__MapsKt.getValue(map, bottom3)).intValue()) {
                                        }
                                        startAnimation(childAt2, linkedHashSet2222, mapOf3, mapOf4222, interpolator, 250L, true, null);
                                        i9++;
                                        childCount2 = i10;
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
                                right4 = intValue + i11;
                                switch (iArr[hotspot.ordinal()]) {
                                }
                            default:
                                throw new NoWhenBranchMatchedException();
                        }
                    case 2:
                    case 3:
                    case 4:
                        i = -i11;
                        i12 = i;
                        switch (iArr[hotspot.ordinal()]) {
                        }
                        switch (iArr[hotspot.ordinal()]) {
                        }
                    case 7:
                    case 8:
                    case 9:
                        i12 = intValue - i11;
                    case 5:
                    case 6:
                        switch (iArr[hotspot.ordinal()]) {
                        }
                        switch (iArr[hotspot.ordinal()]) {
                        }
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }
            final float[] fArr = new float[viewGroup2.getChildCount()];
            int childCount3 = viewGroup2.getChildCount();
            for (int i14 = 0; i14 < childCount3; i14++) {
                fArr[i14] = viewGroup2.getChildAt(i14).getAlpha();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat.setInterpolator(Interpolators.ALPHA_OUT);
            final long j = 250;
            ofFloat.setDuration(250 / 2);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$animateRemoval$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int childCount4 = ((ViewGroup) view).getChildCount();
                    for (int i15 = 0; i15 < childCount4; i15++) {
                        ((ViewGroup) view).getChildAt(i15).setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue() * fArr[i15]);
                    }
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.ViewHierarchyAnimator$Companion$animateRemoval$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    view.animate().alpha(0.0f).setInterpolator(Interpolators.ALPHA_OUT).setDuration(j / 2).start();
                }
            });
            ofFloat.start();
            return true;
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
                listBuilder.add(PropertyValuesHolder.ofInt((Property<?, Integer>) ViewHierarchyAnimator.PROPERTIES.get(bound), ((Number) MapsKt__MapsKt.getValue(map, bound)).intValue(), ((Number) MapsKt__MapsKt.getValue(map2, bound)).intValue()));
            }
            listBuilder.build();
            PropertyValuesHolder[] propertyValuesHolderArr = (PropertyValuesHolder[]) listBuilder.toArray(new PropertyValuesHolder[0]);
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
                int intValue = ((Number) MapsKt__MapsKt.getValue(map, bound2)).intValue();
                companion.getClass();
                setBound(view, bound2, intValue);
            }
            view.setTag(R.id.tag_animator, ofPropertyValuesHolder);
            ofPropertyValuesHolder.start();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return Integer.hashCode(this.bottom) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.right, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.top, Integer.hashCode(this.left) * 31, 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DimenHolder(left=");
            sb.append(this.left);
            sb.append(", top=");
            sb.append(this.top);
            sb.append(", right=");
            sb.append(this.right);
            sb.append(", bottom=");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.bottom, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Hotspot {
        CENTER,
        LEFT,
        TOP_LEFT,
        TOP,
        TOP_RIGHT,
        RIGHT,
        BOTTOM_RIGHT,
        BOTTOM,
        BOTTOM_LEFT
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
