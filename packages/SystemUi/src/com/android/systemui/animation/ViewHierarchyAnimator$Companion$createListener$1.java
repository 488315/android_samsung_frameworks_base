package com.android.systemui.animation;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.systemui.R;
import com.android.systemui.animation.ViewHierarchyAnimator;
import java.util.LinkedHashSet;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewHierarchyAnimator$Companion$createListener$1 implements View.OnLayoutChangeListener {
    public final /* synthetic */ long $duration;
    public final /* synthetic */ boolean $ephemeral;
    public final /* synthetic */ boolean $ignorePreviousValues;
    public final /* synthetic */ Interpolator $interpolator;
    public final /* synthetic */ Runnable $onAnimationEnd;
    public final /* synthetic */ ViewHierarchyAnimator.Hotspot $origin;

    public ViewHierarchyAnimator$Companion$createListener$1(ViewHierarchyAnimator.Hotspot hotspot, boolean z, Interpolator interpolator, long j, boolean z2, Runnable runnable) {
        this.$origin = hotspot;
        this.$ignorePreviousValues = z;
        this.$interpolator = interpolator;
        this.$duration = j;
        this.$ephemeral = z2;
        this.$onAnimationEnd = runnable;
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0111  */
    @Override // android.view.View.OnLayoutChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9;
        int i10;
        int i11;
        if (view == null) {
            return;
        }
        ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
        ViewHierarchyAnimator.Bound.LEFT left = ViewHierarchyAnimator.Bound.LEFT;
        Integer access$getBound = ViewHierarchyAnimator.Companion.access$getBound(companion, view, left);
        int intValue = access$getBound != null ? access$getBound.intValue() : i5;
        ViewHierarchyAnimator.Bound.TOP top = ViewHierarchyAnimator.Bound.TOP;
        Integer access$getBound2 = ViewHierarchyAnimator.Companion.access$getBound(companion, view, top);
        int intValue2 = access$getBound2 != null ? access$getBound2.intValue() : i6;
        ViewHierarchyAnimator.Bound.RIGHT right = ViewHierarchyAnimator.Bound.RIGHT;
        Integer access$getBound3 = ViewHierarchyAnimator.Companion.access$getBound(companion, view, right);
        int intValue3 = access$getBound3 != null ? access$getBound3.intValue() : i7;
        ViewHierarchyAnimator.Bound.BOTTOM bottom = ViewHierarchyAnimator.Bound.BOTTOM;
        Integer access$getBound4 = ViewHierarchyAnimator.Companion.access$getBound(companion, view, bottom);
        int intValue4 = access$getBound4 != null ? access$getBound4.intValue() : i8;
        Object tag = view.getTag(R.id.tag_animator);
        ObjectAnimator objectAnimator = tag instanceof ObjectAnimator ? (ObjectAnimator) tag : null;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (!((view.getVisibility() == 8 || i == i3 || i2 == i4) ? false : true)) {
            ViewHierarchyAnimator.Companion.setBound(view, left, i);
            ViewHierarchyAnimator.Companion.setBound(view, top, i2);
            ViewHierarchyAnimator.Companion.setBound(view, right, i3);
            ViewHierarchyAnimator.Companion.setBound(view, bottom, i4);
            return;
        }
        ViewHierarchyAnimator.Hotspot hotspot = this.$origin;
        boolean z = this.$ignorePreviousValues;
        if (z) {
            intValue = i;
        }
        if (z) {
            intValue2 = i2;
        }
        if (z) {
            intValue3 = i3;
        }
        if (z) {
            intValue4 = i4;
        }
        if (hotspot != null) {
            int[] iArr = ViewHierarchyAnimator.Companion.WhenMappings.$EnumSwitchMapping$0;
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    i9 = (i + i3) / 2;
                    break;
                case 2:
                case 3:
                case 4:
                    i9 = Math.min(intValue, i);
                    break;
                case 5:
                case 6:
                    i9 = i;
                    break;
                case 7:
                case 8:
                case 9:
                    i9 = Math.max(intValue3, i3);
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    i10 = (i2 + i4) / 2;
                    break;
                case 2:
                case 6:
                case 9:
                    i10 = Math.max(intValue4, i4);
                    break;
                case 3:
                case 8:
                    i10 = i2;
                    break;
                case 4:
                case 5:
                case 7:
                    i10 = Math.min(intValue2, i2);
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    i11 = (i + i3) / 2;
                    intValue3 = i11;
                    switch (iArr[hotspot.ordinal()]) {
                        case 1:
                            intValue4 = (i2 + i4) / 2;
                            break;
                        case 2:
                        case 6:
                        case 9:
                            intValue4 = Math.max(intValue4, i4);
                            break;
                        case 3:
                        case 8:
                            intValue4 = i4;
                            break;
                        case 4:
                        case 5:
                        case 7:
                            intValue4 = Math.min(intValue2, i2);
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    intValue = i9;
                    intValue2 = i10;
                    break;
                case 2:
                case 3:
                case 4:
                    i11 = Math.min(intValue, i);
                    intValue3 = i11;
                    switch (iArr[hotspot.ordinal()]) {
                    }
                    intValue = i9;
                    intValue2 = i10;
                    break;
                case 5:
                case 6:
                    intValue3 = i3;
                    switch (iArr[hotspot.ordinal()]) {
                    }
                    intValue = i9;
                    intValue2 = i10;
                    break;
                case 7:
                case 8:
                case 9:
                    i11 = Math.max(intValue3, i3);
                    intValue3 = i11;
                    switch (iArr[hotspot.ordinal()]) {
                    }
                    intValue = i9;
                    intValue2 = i10;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
        Map mapOf = MapsKt__MapsKt.mapOf(new Pair(left, Integer.valueOf(intValue)), new Pair(top, Integer.valueOf(intValue2)), new Pair(right, Integer.valueOf(intValue3)), new Pair(bottom, Integer.valueOf(intValue4)));
        Map mapOf2 = MapsKt__MapsKt.mapOf(new Pair(left, Integer.valueOf(i)), new Pair(top, Integer.valueOf(i2)), new Pair(right, Integer.valueOf(i3)), new Pair(bottom, Integer.valueOf(i4)));
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (((Number) MapsKt__MapsKt.getValue(mapOf, left)).intValue() != i) {
            linkedHashSet.add(left);
        }
        if (((Number) MapsKt__MapsKt.getValue(mapOf, top)).intValue() != i2) {
            linkedHashSet.add(top);
        }
        if (((Number) MapsKt__MapsKt.getValue(mapOf, right)).intValue() != i3) {
            linkedHashSet.add(right);
        }
        if (((Number) MapsKt__MapsKt.getValue(mapOf, bottom)).intValue() != i4) {
            linkedHashSet.add(bottom);
        }
        if (!linkedHashSet.isEmpty()) {
            ViewHierarchyAnimator.Companion.startAnimation(view, linkedHashSet, mapOf, mapOf2, this.$interpolator, this.$duration, this.$ephemeral, this.$onAnimationEnd);
        }
    }
}
