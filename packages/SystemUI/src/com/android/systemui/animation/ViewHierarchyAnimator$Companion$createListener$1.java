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

    /* JADX WARN: Removed duplicated region for block: B:68:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0109  */
    @Override // android.view.View.OnLayoutChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int iMin;
        int iMax;
        int iMin2;
        if (view == null) {
            return;
        }
        ViewHierarchyAnimator.Companion companion = ViewHierarchyAnimator.Companion;
        ViewHierarchyAnimator.Bound.LEFT left = ViewHierarchyAnimator.Bound.LEFT;
        Integer numAccess$getBound = ViewHierarchyAnimator.Companion.access$getBound(companion, view, left);
        int iIntValue = numAccess$getBound != null ? numAccess$getBound.intValue() : i5;
        ViewHierarchyAnimator.Bound.TOP top = ViewHierarchyAnimator.Bound.TOP;
        Integer numAccess$getBound2 = ViewHierarchyAnimator.Companion.access$getBound(companion, view, top);
        int iIntValue2 = numAccess$getBound2 != null ? numAccess$getBound2.intValue() : i6;
        ViewHierarchyAnimator.Bound.RIGHT right = ViewHierarchyAnimator.Bound.RIGHT;
        Integer numAccess$getBound3 = ViewHierarchyAnimator.Companion.access$getBound(companion, view, right);
        int iIntValue3 = numAccess$getBound3 != null ? numAccess$getBound3.intValue() : i7;
        ViewHierarchyAnimator.Bound.BOTTOM bottom = ViewHierarchyAnimator.Bound.BOTTOM;
        Integer numAccess$getBound4 = ViewHierarchyAnimator.Companion.access$getBound(companion, view, bottom);
        int iIntValue4 = numAccess$getBound4 != null ? numAccess$getBound4.intValue() : i8;
        Object tag = view.getTag(R.id.tag_animator);
        ObjectAnimator objectAnimator = tag instanceof ObjectAnimator ? (ObjectAnimator) tag : null;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (!ViewHierarchyAnimator.Companion.occupiesSpace(view.getVisibility(), i, i2, i3, i4)) {
            ViewHierarchyAnimator.Companion.setBound(view, left, i);
            ViewHierarchyAnimator.Companion.setBound(view, top, i2);
            ViewHierarchyAnimator.Companion.setBound(view, right, i3);
            ViewHierarchyAnimator.Companion.setBound(view, bottom, i4);
            return;
        }
        ViewHierarchyAnimator.Hotspot hotspot = this.$origin;
        boolean z = this.$ignorePreviousValues;
        if (z) {
            iIntValue = i;
        }
        if (z) {
            iIntValue2 = i2;
        }
        if (z) {
            iIntValue3 = i3;
        }
        if (z) {
            iIntValue4 = i4;
        }
        if (hotspot != null) {
            int[] iArr = ViewHierarchyAnimator.Companion.WhenMappings.$EnumSwitchMapping$0;
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    iMin = (i + i3) / 2;
                    break;
                case 2:
                case 3:
                case 4:
                    iMin = Math.min(iIntValue, i);
                    break;
                case 5:
                case 6:
                    iMin = i;
                    break;
                case 7:
                case 8:
                case 9:
                    iMin = Math.max(iIntValue3, i3);
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    iMax = (i2 + i4) / 2;
                    break;
                case 2:
                case 6:
                case 9:
                    iMax = Math.max(iIntValue4, i4);
                    break;
                case 3:
                case 8:
                    iMax = i2;
                    break;
                case 4:
                case 5:
                case 7:
                    iMax = Math.min(iIntValue2, i2);
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            switch (iArr[hotspot.ordinal()]) {
                case 1:
                    iMin2 = (i + i3) / 2;
                    iIntValue3 = iMin2;
                    switch (iArr[hotspot.ordinal()]) {
                        case 1:
                            iIntValue4 = (i2 + i4) / 2;
                            break;
                        case 2:
                        case 6:
                        case 9:
                            iIntValue4 = Math.max(iIntValue4, i4);
                            break;
                        case 3:
                        case 8:
                            iIntValue4 = i4;
                            break;
                        case 4:
                        case 5:
                        case 7:
                            iIntValue4 = Math.min(iIntValue2, i2);
                            break;
                        default:
                            throw new NoWhenBranchMatchedException();
                    }
                    iIntValue = iMin;
                    iIntValue2 = iMax;
                    break;
                case 2:
                case 3:
                case 4:
                    iMin2 = Math.min(iIntValue, i);
                    iIntValue3 = iMin2;
                    switch (iArr[hotspot.ordinal()]) {
                    }
                    iIntValue = iMin;
                    iIntValue2 = iMax;
                    break;
                case 5:
                case 6:
                    iIntValue3 = i3;
                    switch (iArr[hotspot.ordinal()]) {
                    }
                    iIntValue = iMin;
                    iIntValue2 = iMax;
                    break;
                case 7:
                case 8:
                case 9:
                    iMin2 = Math.max(iIntValue3, i3);
                    iIntValue3 = iMin2;
                    switch (iArr[hotspot.ordinal()]) {
                    }
                    iIntValue = iMin;
                    iIntValue2 = iMax;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
        Map mapMapOf = MapsKt__MapsKt.mapOf(new Pair(left, Integer.valueOf(iIntValue)), new Pair(top, Integer.valueOf(iIntValue2)), new Pair(right, Integer.valueOf(iIntValue3)), new Pair(bottom, Integer.valueOf(iIntValue4)));
        Map mapMapOf2 = MapsKt__MapsKt.mapOf(new Pair(left, Integer.valueOf(i)), new Pair(top, Integer.valueOf(i2)), new Pair(right, Integer.valueOf(i3)), new Pair(bottom, Integer.valueOf(i4)));
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (((Number) MapsKt__MapsKt.getValue(left, mapMapOf)).intValue() != i) {
            linkedHashSet.add(left);
        }
        if (((Number) MapsKt__MapsKt.getValue(top, mapMapOf)).intValue() != i2) {
            linkedHashSet.add(top);
        }
        if (((Number) MapsKt__MapsKt.getValue(right, mapMapOf)).intValue() != i3) {
            linkedHashSet.add(right);
        }
        if (((Number) MapsKt__MapsKt.getValue(bottom, mapMapOf)).intValue() != i4) {
            linkedHashSet.add(bottom);
        }
        if (linkedHashSet.isEmpty()) {
            return;
        }
        ViewHierarchyAnimator.Companion.startAnimation(view, linkedHashSet, mapMapOf, mapMapOf2, this.$interpolator, this.$duration, this.$ephemeral, this.$onAnimationEnd);
    }
}
