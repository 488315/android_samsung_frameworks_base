package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.draganddrop.SplitDragPolicy;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MultiSplitDropTargetProvider extends SplitDropTargetProvider {
    public MultiSplitDropTargetProvider(SplitDragPolicy splitDragPolicy, Context context) {
        super(splitDragPolicy, context);
    }

    public static SplitDragPolicy.Target createMultiSplitTarget(int i, Rect rect, boolean z, Insets insets) {
        if (!z) {
            int i2 = (rect.right + insets.left) / 2;
            switch (i) {
                case 6:
                case 7:
                    return new SplitDragPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right / 3, rect.bottom), new Rect(rect.left, rect.top, i2, rect.bottom), -1);
                case 8:
                case 9:
                    int i3 = rect.right;
                    return new SplitDragPolicy.Target(i, new Rect((i3 * 2) / 3, rect.top, i3 + insets.right, rect.bottom), new Rect(i2, rect.top, rect.right, rect.bottom), -1);
                case 10:
                case 12:
                default:
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Wrong DropTarget type: #"));
                case 11:
                    int i4 = rect.left;
                    int i5 = rect.top;
                    Rect rect2 = new Rect(i4, i5, rect.right, i5 + 200);
                    int i6 = rect.left;
                    int i7 = rect.top;
                    return new SplitDragPolicy.Target(i, rect2, new Rect(i6, i7, rect.right, i7 + 200), -1);
                case 13:
                    int i8 = rect.left;
                    int i9 = rect.bottom;
                    Rect rect3 = new Rect(i8, i9 - 200, rect.right, i9);
                    int i10 = rect.left;
                    int i11 = rect.bottom;
                    return new SplitDragPolicy.Target(i, rect3, new Rect(i10, i11 - 200, rect.right, i11), -1);
            }
        }
        int i12 = (rect.bottom + insets.top) / 2;
        switch (i) {
            case 6:
                return new SplitDragPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right, rect.bottom / 3), new Rect(rect.left, rect.top, rect.right, i12), -1);
            case 7:
                int i13 = rect.left - insets.left;
                int i14 = rect.bottom;
                return new SplitDragPolicy.Target(i, new Rect(i13, (i14 * 2) / 3, rect.right, i14), new Rect(rect.left, i12, rect.right, rect.bottom), -1);
            case 8:
                return new SplitDragPolicy.Target(i, new Rect(rect.left, rect.top, rect.right + insets.right, rect.bottom / 3), new Rect(rect.left, rect.top, rect.right, i12), -1);
            case 9:
                int i15 = rect.left;
                int i16 = rect.bottom;
                return new SplitDragPolicy.Target(i, new Rect(i15, (i16 * 2) / 3, rect.right + insets.right, i16), new Rect(rect.left, i12, rect.right, rect.bottom), -1);
            case 10:
                int i17 = rect.left;
                Rect rect4 = new Rect(i17, rect.top, i17 + 200, rect.bottom);
                int i18 = rect.left;
                return new SplitDragPolicy.Target(i, rect4, new Rect(i18, rect.top, i18 + 200, rect.bottom), -1);
            case 11:
            default:
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Wrong DropTarget type: #"));
            case 12:
                int i19 = rect.right;
                Rect rect5 = new Rect(i19 - 200, rect.top, i19, rect.bottom);
                int i20 = rect.right;
                return new SplitDragPolicy.Target(i, rect5, new Rect(i20 - 200, rect.top, i20, rect.bottom), -1);
        }
    }

    public static SplitDragPolicy.Target createTarget(int i, Insets insets, Rect rect) {
        if (i != 1) {
            if (i != 3) {
                switch (i) {
                    case 6:
                    case 7:
                        break;
                    case 8:
                    case 9:
                        break;
                    default:
                        return new SplitDragPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right + insets.right, rect.bottom), new Rect(rect), -1);
                }
            }
            return new SplitDragPolicy.Target(i, new Rect(rect.left, rect.top, rect.right + insets.right, rect.bottom), new Rect(rect), -1);
        }
        return new SplitDragPolicy.Target(i, new Rect(rect.left - insets.left, rect.top, rect.right, rect.bottom), new Rect(rect), -1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:123:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0228  */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v3 */
    @Override // com.android.wm.shell.draganddrop.SplitDropTargetProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addSplitTargets(android.graphics.Rect r26, boolean r27, boolean r28, float r29, java.util.ArrayList r30) {
        /*
            Method dump skipped, instructions count: 719
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.draganddrop.MultiSplitDropTargetProvider.addSplitTargets(android.graphics.Rect, boolean, boolean, float, java.util.ArrayList):void");
    }

    public final List getPolygonTouchRegion(int i, Rect rect) {
        SplitDragPolicy splitDragPolicy = this.mPolicy;
        Rect centerFreeformBounds = splitDragPolicy.getCenterFreeformBounds();
        int i2 = -splitDragPolicy.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_hit_size);
        centerFreeformBounds.inset(i2, i2);
        ArrayList arrayList = new ArrayList();
        if (i == 2) {
            arrayList.add(new PointF(rect.left, rect.top));
            arrayList.add(new PointF(rect.right, rect.top));
            arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
            arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
            return arrayList;
        }
        if (i == 3) {
            arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
            arrayList.add(new PointF(rect.right, rect.top));
            arrayList.add(new PointF(rect.right, rect.bottom));
            arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.bottom));
            return arrayList;
        }
        if (i == 1) {
            arrayList.add(new PointF(rect.left, rect.top));
            arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
            arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.bottom));
            arrayList.add(new PointF(rect.left, rect.bottom));
            return arrayList;
        }
        if (i == 4) {
            arrayList.add(new PointF(centerFreeformBounds.left, rect.top));
            arrayList.add(new PointF(centerFreeformBounds.right, rect.top));
            arrayList.add(new PointF(rect.right, rect.bottom));
            arrayList.add(new PointF(rect.left, rect.bottom));
        }
        return arrayList;
    }
}
