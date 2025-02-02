package com.android.wm.shell.pip.tv;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.Size;
import android.view.Gravity;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipKeepClearAlgorithmInterface;
import com.android.wm.shell.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.tv.TvPipKeepClearAlgorithm;
import com.android.wm.shell.pip.phone.PipSizeSpecHandler;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipBoundsAlgorithm extends PipBoundsAlgorithm {
    public int mFixedExpandedHeightInPx;
    public int mFixedExpandedWidthInPx;
    public final TvPipKeepClearAlgorithm mKeepClearAlgorithm;
    public final TvPipBoundsState mTvPipBoundsState;

    public TvPipBoundsAlgorithm(Context context, TvPipBoundsState tvPipBoundsState, PipSnapAlgorithm pipSnapAlgorithm, PipSizeSpecHandler pipSizeSpecHandler) {
        super(context, tvPipBoundsState, pipSnapAlgorithm, new PipKeepClearAlgorithmInterface() { // from class: com.android.wm.shell.pip.tv.TvPipBoundsAlgorithm.1
        }, pipSizeSpecHandler);
        this.mTvPipBoundsState = tvPipBoundsState;
        this.mKeepClearAlgorithm = new TvPipKeepClearAlgorithm();
        reloadResources(context);
    }

    private void reloadResources(Context context) {
        Resources resources = context.getResources();
        this.mFixedExpandedHeightInPx = resources.getDimensionPixelSize(R.dimen.config_letterboxBackgroundWallpaperBlurRadius);
        this.mFixedExpandedWidthInPx = resources.getDimensionPixelSize(R.dimen.config_letterboxBookModePositionMultiplier);
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.pip_keep_clear_area_padding);
        TvPipKeepClearAlgorithm tvPipKeepClearAlgorithm = this.mKeepClearAlgorithm;
        tvPipKeepClearAlgorithm.pipAreaPadding = dimensionPixelSize;
        tvPipKeepClearAlgorithm.maxRestrictedDistanceFraction = resources.getFraction(com.android.systemui.R.fraction.config_pipMaxRestrictedMoveDistance, 1, 1);
    }

    public final Rect adjustBoundsForTemporaryDecor(Rect rect) {
        Rect rect2 = new Rect(rect);
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        Insets insets = tvPipBoundsState.mPipMenuTemporaryDecorInsets;
        Insets subtract = Insets.subtract(Insets.NONE, insets);
        rect2.inset(insets);
        Gravity.apply(tvPipBoundsState.mTvPipGravity, rect2.width(), rect2.height(), rect, rect2);
        rect2.inset(subtract);
        return rect2;
    }

    @Override // com.android.wm.shell.pip.PipBoundsAlgorithm
    public final Rect getAdjustedDestinationBounds(Rect rect, float f) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 39720036, 8, "%s: getAdjustedDestinationBounds: %f", "TvPipBoundsAlgorithm", Double.valueOf(f));
        }
        return adjustBoundsForTemporaryDecor(getTvPipPlacement().bounds);
    }

    @Override // com.android.wm.shell.pip.PipBoundsAlgorithm
    public final Rect getEntryDestinationBounds() {
        boolean z = false;
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1588940706, 0, "%s: getEntryDestinationBounds()", "TvPipBoundsAlgorithm");
        }
        updateExpandedPipSize();
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        if (tvPipBoundsState.mIsTvExpandedPipSupported && tvPipBoundsState.mDesiredTvExpandedAspectRatio != 0.0f && !tvPipBoundsState.mTvPipManuallyCollapsed) {
            z = true;
        }
        if (z) {
            updateGravityOnExpansionToggled(true);
        }
        tvPipBoundsState.mIsTvPipExpanded = z;
        return adjustBoundsForTemporaryDecor(getTvPipPlacement().bounds);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0476  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x049a  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x04fe  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x051d  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0533  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0549  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0518  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x04e3  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0487  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final TvPipKeepClearAlgorithm.Placement getTvPipPlacement() {
        Size size;
        boolean z;
        Set set;
        Object next;
        TvPipKeepClearAlgorithm.Placement placement;
        Object next2;
        Object next3;
        Object next4;
        Object next5;
        Rect rect;
        Rect rect2;
        int i;
        int i2;
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        if (tvPipBoundsState.mIsTvExpandedPipSupported && tvPipBoundsState.mIsTvPipExpanded && tvPipBoundsState.mDesiredTvExpandedAspectRatio != 0.0f) {
            size = tvPipBoundsState.mTvExpandedSize;
        } else {
            Rect defaultBounds = getDefaultBounds(null, -1.0f);
            float f = this.mPipBoundsState.mAspectRatio;
            Rect rect3 = new Rect(defaultBounds);
            if (Float.compare(this.mMinAspectRatio, f) <= 0 && Float.compare(f, this.mMaxAspectRatio) <= 0) {
                transformBoundsToAspectRatio(rect3, f, false, false);
            }
            size = new Size(rect3.width(), rect3.height());
        }
        Rect displayBounds = tvPipBoundsState.getDisplayBounds();
        Size size2 = new Size(displayBounds.width(), displayBounds.height());
        Rect rect4 = new Rect();
        getInsetBounds(rect4);
        Set set2 = tvPipBoundsState.mRestrictedKeepClearAreas;
        Set unrestrictedKeepClearAreas = tvPipBoundsState.getUnrestrictedKeepClearAreas();
        int i3 = tvPipBoundsState.mTvPipGravity;
        TvPipKeepClearAlgorithm tvPipKeepClearAlgorithm = this.mKeepClearAlgorithm;
        int i4 = tvPipKeepClearAlgorithm.pipGravity;
        Rect rect5 = tvPipKeepClearAlgorithm.movementBounds;
        if (i4 != i3) {
            tvPipKeepClearAlgorithm.pipGravity = i3;
            tvPipKeepClearAlgorithm.transformedScreenBounds = tvPipKeepClearAlgorithm.toTransformedSpace(new Rect(0, 0, tvPipKeepClearAlgorithm.screenSize.getWidth(), tvPipKeepClearAlgorithm.screenSize.getHeight()));
            tvPipKeepClearAlgorithm.transformedMovementBounds = tvPipKeepClearAlgorithm.toTransformedSpace(rect5);
        }
        if (!Intrinsics.areEqual(tvPipKeepClearAlgorithm.screenSize, size2)) {
            tvPipKeepClearAlgorithm.screenSize = size2;
            tvPipKeepClearAlgorithm.transformedScreenBounds = tvPipKeepClearAlgorithm.toTransformedSpace(new Rect(0, 0, tvPipKeepClearAlgorithm.screenSize.getWidth(), tvPipKeepClearAlgorithm.screenSize.getHeight()));
            tvPipKeepClearAlgorithm.transformedMovementBounds = tvPipKeepClearAlgorithm.toTransformedSpace(tvPipKeepClearAlgorithm.transformedMovementBounds);
        }
        if (!Intrinsics.areEqual(rect5, rect4)) {
            rect5.set(rect4);
            tvPipKeepClearAlgorithm.transformedMovementBounds = tvPipKeepClearAlgorithm.toTransformedSpace(rect5);
        }
        tvPipKeepClearAlgorithm.stashOffset = tvPipBoundsState.mStashOffset;
        tvPipKeepClearAlgorithm.pipPermanentDecorInsets = tvPipBoundsState.mPipMenuPermanentDecorInsets;
        Set transformAndFilterAreas = tvPipKeepClearAlgorithm.transformAndFilterAreas(set2);
        Set transformAndFilterAreas2 = tvPipKeepClearAlgorithm.transformAndFilterAreas(unrestrictedKeepClearAreas);
        Rect rect6 = new Rect(0, 0, size.getWidth(), size.getHeight());
        rect6.inset(tvPipKeepClearAlgorithm.pipPermanentDecorInsets);
        Size size3 = new Size(rect6.width(), rect6.height());
        Rect rect7 = tvPipKeepClearAlgorithm.transformedMovementBounds;
        if (tvPipKeepClearAlgorithm.shouldTransformRotate()) {
            size3 = new Size(size3.getHeight(), size3.getWidth());
        }
        Rect rect8 = new Rect();
        if (tvPipKeepClearAlgorithm.isPipAnchoredToCorner()) {
            Gravity.apply(85, size3.getWidth(), size3.getHeight(), rect7, rect8);
        } else {
            Gravity.apply(5, size3.getWidth(), size3.getHeight(), rect7, rect8);
        }
        Set<Rect> plus = SetsKt___SetsKt.plus(transformAndFilterAreas, transformAndFilterAreas2);
        if (!plus.isEmpty()) {
            for (Rect rect9 : plus) {
                if (TvPipKeepClearAlgorithm.intersectsX(rect9, rect8) && TvPipKeepClearAlgorithm.intersectsY(rect9, rect8)) {
                    z = false;
                    break;
                }
            }
        }
        z = true;
        if (z) {
            tvPipKeepClearAlgorithm.lastAreasOverlappingUnstashPosition = EmptySet.INSTANCE;
            placement = new TvPipKeepClearAlgorithm.Placement(rect8, rect8, 0, null, false, 28, null);
        } else {
            Rect findFreeMovePosition = tvPipKeepClearAlgorithm.findFreeMovePosition(rect8, transformAndFilterAreas, transformAndFilterAreas2);
            if (findFreeMovePosition == null) {
                Rect findRelaxedMovePosition = tvPipKeepClearAlgorithm.findRelaxedMovePosition(1, rect8, CollectionsKt___CollectionsKt.toMutableSet(transformAndFilterAreas), transformAndFilterAreas2);
                if (findRelaxedMovePosition == null && (findRelaxedMovePosition = tvPipKeepClearAlgorithm.findFreeMovePosition(rect8, EmptySet.INSTANCE, transformAndFilterAreas2)) == null) {
                    findRelaxedMovePosition = rect8;
                }
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                for (Object obj : plus) {
                    Rect rect10 = (Rect) obj;
                    if (TvPipKeepClearAlgorithm.intersectsX(rect10, findRelaxedMovePosition) && TvPipKeepClearAlgorithm.intersectsY(rect10, findRelaxedMovePosition)) {
                        linkedHashSet.add(obj);
                    }
                }
                boolean z2 = !tvPipKeepClearAlgorithm.lastAreasOverlappingUnstashPosition.containsAll(linkedHashSet);
                tvPipKeepClearAlgorithm.lastAreasOverlappingUnstashPosition = linkedHashSet;
                Rect rect11 = tvPipKeepClearAlgorithm.transformedScreenBounds;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : plus) {
                    if (TvPipKeepClearAlgorithm.intersectsX((Rect) obj2, findRelaxedMovePosition)) {
                        arrayList2.add(obj2);
                    }
                }
                ArrayList arrayList3 = new ArrayList();
                for (Object obj3 : plus) {
                    if (TvPipKeepClearAlgorithm.intersectsY((Rect) obj3, findRelaxedMovePosition)) {
                        arrayList3.add(obj3);
                    }
                }
                if (!arrayList2.isEmpty()) {
                    int i5 = rect11.bottom;
                    set = unrestrictedKeepClearAreas;
                    if (i5 - findRelaxedMovePosition.bottom <= findRelaxedMovePosition.top - rect11.top) {
                        int i6 = i5 - tvPipKeepClearAlgorithm.stashOffset;
                        Iterator it = arrayList2.iterator();
                        if (it.hasNext()) {
                            next5 = it.next();
                            if (it.hasNext()) {
                                int i7 = ((Rect) next5).bottom;
                                do {
                                    Object next6 = it.next();
                                    Object obj4 = next5;
                                    int i8 = ((Rect) next6).bottom;
                                    if (i7 < i8) {
                                        i7 = i8;
                                        next5 = next6;
                                    } else {
                                        next5 = obj4;
                                    }
                                } while (it.hasNext());
                            }
                        } else {
                            next5 = null;
                        }
                        Intrinsics.checkNotNull(next5);
                        int min = Math.min(i6, ((Rect) next5).bottom + tvPipKeepClearAlgorithm.pipAreaPadding);
                        if (min > findRelaxedMovePosition.top) {
                            Rect rect12 = new Rect(findRelaxedMovePosition);
                            rect12.offsetTo(findRelaxedMovePosition.left, min);
                            arrayList.add(rect12);
                        }
                    }
                    int i9 = rect11.bottom - findRelaxedMovePosition.bottom;
                    int i10 = findRelaxedMovePosition.top;
                    int i11 = rect11.top;
                    if (i9 >= i10 - i11) {
                        int height = (i11 - findRelaxedMovePosition.height()) + tvPipKeepClearAlgorithm.stashOffset;
                        Iterator it2 = arrayList2.iterator();
                        if (it2.hasNext()) {
                            next4 = it2.next();
                            if (it2.hasNext()) {
                                int i12 = ((Rect) next4).top;
                                do {
                                    Object next7 = it2.next();
                                    Object obj5 = next4;
                                    int i13 = ((Rect) next7).top;
                                    if (i12 > i13) {
                                        i12 = i13;
                                        next4 = next7;
                                    } else {
                                        next4 = obj5;
                                    }
                                } while (it2.hasNext());
                            }
                        } else {
                            next4 = null;
                        }
                        Intrinsics.checkNotNull(next4);
                        int max = Math.max(height, (((Rect) next4).top - findRelaxedMovePosition.height()) - tvPipKeepClearAlgorithm.pipAreaPadding);
                        if (max < findRelaxedMovePosition.top) {
                            Rect rect13 = new Rect(findRelaxedMovePosition);
                            rect13.offsetTo(findRelaxedMovePosition.left, max);
                            arrayList.add(rect13);
                        }
                    }
                } else {
                    set = unrestrictedKeepClearAreas;
                }
                if (!arrayList3.isEmpty()) {
                    int i14 = rect11.right;
                    if (i14 - findRelaxedMovePosition.right <= findRelaxedMovePosition.left - rect11.left) {
                        int i15 = i14 - tvPipKeepClearAlgorithm.stashOffset;
                        Iterator it3 = arrayList3.iterator();
                        if (it3.hasNext()) {
                            next3 = it3.next();
                            if (it3.hasNext()) {
                                int i16 = ((Rect) next3).right;
                                do {
                                    Object next8 = it3.next();
                                    Object obj6 = next3;
                                    int i17 = ((Rect) next8).right;
                                    if (i16 < i17) {
                                        i16 = i17;
                                        next3 = next8;
                                    } else {
                                        next3 = obj6;
                                    }
                                } while (it3.hasNext());
                            }
                        } else {
                            next3 = null;
                        }
                        Intrinsics.checkNotNull(next3);
                        int min2 = Math.min(i15, ((Rect) next3).right + tvPipKeepClearAlgorithm.pipAreaPadding);
                        if (min2 > findRelaxedMovePosition.left) {
                            Rect rect14 = new Rect(findRelaxedMovePosition);
                            rect14.offsetTo(min2, findRelaxedMovePosition.top);
                            arrayList.add(rect14);
                        }
                    }
                    int i18 = rect11.right - findRelaxedMovePosition.right;
                    int i19 = findRelaxedMovePosition.left;
                    int i20 = rect11.left;
                    if (i18 >= i19 - i20) {
                        int width = (i20 - findRelaxedMovePosition.width()) + tvPipKeepClearAlgorithm.stashOffset;
                        Iterator it4 = arrayList3.iterator();
                        if (it4.hasNext()) {
                            next2 = it4.next();
                            if (it4.hasNext()) {
                                int i21 = ((Rect) next2).left;
                                do {
                                    Object next9 = it4.next();
                                    int i22 = ((Rect) next9).left;
                                    if (i21 > i22) {
                                        next2 = next9;
                                        i21 = i22;
                                    }
                                } while (it4.hasNext());
                            }
                        } else {
                            next2 = null;
                        }
                        Intrinsics.checkNotNull(next2);
                        int max2 = Math.max(width, (((Rect) next2).left - findRelaxedMovePosition.width()) - tvPipKeepClearAlgorithm.pipAreaPadding);
                        if (max2 < findRelaxedMovePosition.left) {
                            Rect rect15 = new Rect(findRelaxedMovePosition);
                            rect15.offsetTo(max2, findRelaxedMovePosition.top);
                            arrayList.add(rect15);
                        }
                    }
                }
                Iterator it5 = arrayList.iterator();
                if (it5.hasNext()) {
                    next = it5.next();
                    if (it5.hasNext()) {
                        Rect rect16 = (Rect) next;
                        int abs = Math.abs(rect16.top - findRelaxedMovePosition.top) + Math.abs(rect16.left - findRelaxedMovePosition.left);
                        do {
                            Object next10 = it5.next();
                            Rect rect17 = (Rect) next10;
                            int abs2 = Math.abs(rect17.top - findRelaxedMovePosition.top) + Math.abs(rect17.left - findRelaxedMovePosition.left);
                            if (abs > abs2) {
                                next = next10;
                                abs = abs2;
                            }
                        } while (it5.hasNext());
                    }
                } else {
                    next = null;
                }
                Rect rect18 = (Rect) next;
                Rect rect19 = rect18 == null ? findRelaxedMovePosition : rect18;
                placement = new TvPipKeepClearAlgorithm.Placement(rect19, rect8, TvPipKeepClearAlgorithm.getStashType(rect19, findRelaxedMovePosition), findRelaxedMovePosition, z2);
                Rect fromTransformedSpace = tvPipKeepClearAlgorithm.fromTransformedSpace(placement.bounds);
                fromTransformedSpace.inset(Insets.subtract(Insets.NONE, tvPipKeepClearAlgorithm.pipPermanentDecorInsets));
                Rect fromTransformedSpace2 = tvPipKeepClearAlgorithm.fromTransformedSpace(placement.anchorBounds);
                fromTransformedSpace2.inset(Insets.subtract(Insets.NONE, tvPipKeepClearAlgorithm.pipPermanentDecorInsets));
                rect = placement.unstashDestinationBounds;
                if (rect == null) {
                    Rect fromTransformedSpace3 = tvPipKeepClearAlgorithm.fromTransformedSpace(rect);
                    fromTransformedSpace3.inset(Insets.subtract(Insets.NONE, tvPipKeepClearAlgorithm.pipPermanentDecorInsets));
                    rect2 = fromTransformedSpace3;
                } else {
                    rect2 = null;
                }
                TvPipKeepClearAlgorithm.Placement placement2 = new TvPipKeepClearAlgorithm.Placement(fromTransformedSpace, fromTransformedSpace2, TvPipKeepClearAlgorithm.getStashType(fromTransformedSpace, rect2), rect2, placement.triggerStash);
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1033643551, 0, "%s: screenSize: %s", "TvPipBoundsAlgorithm", String.valueOf(size2));
                }
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -203572733, 4, "%s: stashOffset: %d", "TvPipBoundsAlgorithm", Long.valueOf(tvPipBoundsState.mStashOffset));
                }
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    i = 0;
                } else {
                    i = 0;
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1549815332, 0, "%s: insetBounds: %s", "TvPipBoundsAlgorithm", String.valueOf(rect4.toShortString()));
                }
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1421472994, i, "%s: pipSize: %s", "TvPipBoundsAlgorithm", String.valueOf(size));
                }
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    i2 = 0;
                } else {
                    i2 = 0;
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1960718840, 0, "%s: gravity: %s", "TvPipBoundsAlgorithm", String.valueOf(Gravity.toString(tvPipBoundsState.mTvPipGravity)));
                }
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -543442837, i2, "%s: restrictedKeepClearAreas: %s", "TvPipBoundsAlgorithm", String.valueOf(set2));
                }
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -246266236, i2, "%s: unrestrictedKeepClearAreas: %s", "TvPipBoundsAlgorithm", String.valueOf(set));
                }
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1012228081, i2, "%s: placement: %s", "TvPipBoundsAlgorithm", String.valueOf(placement2));
                }
                return placement2;
            }
            tvPipKeepClearAlgorithm.lastAreasOverlappingUnstashPosition = EmptySet.INSTANCE;
            placement = new TvPipKeepClearAlgorithm.Placement(findFreeMovePosition, rect8, 0, null, false, 28, null);
        }
        set = unrestrictedKeepClearAreas;
        Rect fromTransformedSpace4 = tvPipKeepClearAlgorithm.fromTransformedSpace(placement.bounds);
        fromTransformedSpace4.inset(Insets.subtract(Insets.NONE, tvPipKeepClearAlgorithm.pipPermanentDecorInsets));
        Rect fromTransformedSpace22 = tvPipKeepClearAlgorithm.fromTransformedSpace(placement.anchorBounds);
        fromTransformedSpace22.inset(Insets.subtract(Insets.NONE, tvPipKeepClearAlgorithm.pipPermanentDecorInsets));
        rect = placement.unstashDestinationBounds;
        if (rect == null) {
        }
        TvPipKeepClearAlgorithm.Placement placement22 = new TvPipKeepClearAlgorithm.Placement(fromTransformedSpace4, fromTransformedSpace22, TvPipKeepClearAlgorithm.getStashType(fromTransformedSpace4, rect2), rect2, placement.triggerStash);
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
        }
        return placement22;
    }

    @Override // com.android.wm.shell.pip.PipBoundsAlgorithm
    public final void onConfigurationChanged(Context context) {
        reloadResources(context);
        reloadResources(context);
    }

    public final void updateExpandedPipSize() {
        Size size;
        Size size2;
        Size size3;
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        DisplayLayout displayLayout = tvPipBoundsState.getDisplayLayout();
        float f = tvPipBoundsState.mDesiredTvExpandedAspectRatio;
        Insets insets = tvPipBoundsState.mPipMenuPermanentDecorInsets;
        if (f == 0.0f) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -323100848, 0, "%s: updateExpandedPipSize(): Expanded mode aspect ratio of 0 not supported", "TvPipBoundsAlgorithm");
                return;
            }
            return;
        }
        PipSizeSpecHandler pipSizeSpecHandler = this.mPipSizeSpecHandler;
        if (f < 1.0f) {
            if (tvPipBoundsState.mTvFixedPipOrientation == 2) {
                size3 = tvPipBoundsState.mTvExpandedSize;
            } else {
                int i = ((displayLayout.mHeight - (pipSizeSpecHandler.mScreenEdgeInsets.y * 2)) - insets.top) - insets.bottom;
                float f2 = this.mFixedExpandedWidthInPx / f;
                if (i > f2) {
                    if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                        ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1627925042, 0, "%s: Accommodate aspect ratio", "TvPipBoundsAlgorithm");
                    }
                    size2 = new Size(this.mFixedExpandedWidthInPx, (int) f2);
                    size3 = size2;
                } else {
                    if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                        ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1761077575, 0, "%s: Aspect ratio is too extreme, use max size", "TvPipBoundsAlgorithm");
                    }
                    size = new Size(this.mFixedExpandedWidthInPx, i);
                    size3 = size;
                }
            }
        } else if (tvPipBoundsState.mTvFixedPipOrientation == 1) {
            size3 = tvPipBoundsState.mTvExpandedSize;
        } else {
            int i2 = ((displayLayout.mWidth - (pipSizeSpecHandler.mScreenEdgeInsets.x * 2)) - insets.left) - insets.right;
            float f3 = this.mFixedExpandedHeightInPx * f;
            if (i2 > f3) {
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1627925042, 0, "%s: Accommodate aspect ratio", "TvPipBoundsAlgorithm");
                }
                size2 = new Size((int) f3, this.mFixedExpandedHeightInPx);
                size3 = size2;
            } else {
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1761077575, 0, "%s: Aspect ratio is too extreme, use max size", "TvPipBoundsAlgorithm");
                }
                size = new Size(i2, this.mFixedExpandedHeightInPx);
                size3 = size;
            }
        }
        tvPipBoundsState.mTvExpandedSize = size3;
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 677213986, 20, "%s: updateExpandedPipSize(): expanded size, width: %d, height: %d", "TvPipBoundsAlgorithm", Long.valueOf(size3.getWidth()), Long.valueOf(size3.getHeight()));
        }
    }

    public final void updateGravityOnExpansionToggled(boolean z) {
        int i;
        boolean z2 = ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled;
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        if (z2) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -374942100, 28, "%s: updateGravity, expanding: %b, fixedExpandedOrientation: %d", "TvPipBoundsAlgorithm", Boolean.valueOf(z), Long.valueOf(tvPipBoundsState.mTvFixedPipOrientation));
        }
        int i2 = tvPipBoundsState.mTvPipGravity;
        int i3 = i2 & 7;
        int i4 = i2 & 112;
        int i5 = tvPipBoundsState.mPreviousCollapsedGravity;
        int i6 = i5 & 7;
        int i7 = i5 & 112;
        if (z) {
            tvPipBoundsState.mPreviousCollapsedGravity = i2;
            i = tvPipBoundsState.mTvFixedPipOrientation == 2 ? i4 | 1 : i3 | 16;
        } else {
            i = tvPipBoundsState.mTvFixedPipOrientation == 2 ? i6 | i4 : i3 | i7;
        }
        tvPipBoundsState.mTvPipGravity = i;
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1303376248, 0, "%s: new gravity: %s", "TvPipBoundsAlgorithm", String.valueOf(Gravity.toString(i)));
        }
    }
}
