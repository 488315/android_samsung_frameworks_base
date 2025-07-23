package androidx.compose.ui.layout;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.node.NodeCoordinator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LayoutCoordinatesKt {
    public static final Rect boundsInParent(LayoutCoordinates layoutCoordinates) {
        Rect localBoundingBoxOf;
        LayoutCoordinates parentLayoutCoordinates$1 = layoutCoordinates.getParentLayoutCoordinates$1();
        return (parentLayoutCoordinates$1 == null || (localBoundingBoxOf = parentLayoutCoordinates$1.localBoundingBoxOf(layoutCoordinates, true)) == null) ? new Rect(0.0f, 0.0f, (int) (layoutCoordinates.mo610getSizeYbymL2g() >> 32), (int) (layoutCoordinates.mo610getSizeYbymL2g() & 4294967295L)) : localBoundingBoxOf;
    }

    public static final Rect boundsInRoot(LayoutCoordinates layoutCoordinates) {
        return findRootCoordinates(layoutCoordinates).localBoundingBoxOf(layoutCoordinates, true);
    }

    public static final Rect boundsInWindow(LayoutCoordinates layoutCoordinates) {
        LayoutCoordinates findRootCoordinates = findRootCoordinates(layoutCoordinates);
        float mo610getSizeYbymL2g = (int) (findRootCoordinates.mo610getSizeYbymL2g() >> 32);
        float mo610getSizeYbymL2g2 = (int) (findRootCoordinates.mo610getSizeYbymL2g() & 4294967295L);
        Rect localBoundingBoxOf = findRootCoordinates.localBoundingBoxOf(layoutCoordinates, true);
        float f = localBoundingBoxOf.left;
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > mo610getSizeYbymL2g) {
            f = mo610getSizeYbymL2g;
        }
        float f2 = localBoundingBoxOf.top;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > mo610getSizeYbymL2g2) {
            f2 = mo610getSizeYbymL2g2;
        }
        float f3 = localBoundingBoxOf.right;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 <= mo610getSizeYbymL2g) {
            mo610getSizeYbymL2g = f3;
        }
        float f4 = localBoundingBoxOf.bottom;
        float f5 = f4 >= 0.0f ? f4 : 0.0f;
        if (f5 <= mo610getSizeYbymL2g2) {
            mo610getSizeYbymL2g2 = f5;
        }
        if (f == mo610getSizeYbymL2g || f2 == mo610getSizeYbymL2g2) {
            Rect.Companion.getClass();
            return Rect.Zero;
        }
        Offset.Companion companion = Offset.Companion;
        long mo615localToWindowMKHz9U = findRootCoordinates.mo615localToWindowMKHz9U((Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L));
        long mo615localToWindowMKHz9U2 = findRootCoordinates.mo615localToWindowMKHz9U((Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(mo610getSizeYbymL2g) << 32));
        long mo615localToWindowMKHz9U3 = findRootCoordinates.mo615localToWindowMKHz9U((Float.floatToRawIntBits(mo610getSizeYbymL2g) << 32) | (Float.floatToRawIntBits(mo610getSizeYbymL2g2) & 4294967295L));
        long mo615localToWindowMKHz9U4 = findRootCoordinates.mo615localToWindowMKHz9U((Float.floatToRawIntBits(mo610getSizeYbymL2g2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
        float intBitsToFloat = Float.intBitsToFloat((int) (mo615localToWindowMKHz9U >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (mo615localToWindowMKHz9U2 >> 32));
        float intBitsToFloat3 = Float.intBitsToFloat((int) (mo615localToWindowMKHz9U4 >> 32));
        float intBitsToFloat4 = Float.intBitsToFloat((int) (mo615localToWindowMKHz9U3 >> 32));
        float min = Math.min(intBitsToFloat, Math.min(intBitsToFloat2, Math.min(intBitsToFloat3, intBitsToFloat4)));
        float max = Math.max(intBitsToFloat, Math.max(intBitsToFloat2, Math.max(intBitsToFloat3, intBitsToFloat4)));
        float intBitsToFloat5 = Float.intBitsToFloat((int) (mo615localToWindowMKHz9U & 4294967295L));
        float intBitsToFloat6 = Float.intBitsToFloat((int) (mo615localToWindowMKHz9U2 & 4294967295L));
        float intBitsToFloat7 = Float.intBitsToFloat((int) (mo615localToWindowMKHz9U4 & 4294967295L));
        float intBitsToFloat8 = Float.intBitsToFloat((int) (mo615localToWindowMKHz9U3 & 4294967295L));
        return new Rect(min, Math.min(intBitsToFloat5, Math.min(intBitsToFloat6, Math.min(intBitsToFloat7, intBitsToFloat8))), max, Math.max(intBitsToFloat5, Math.max(intBitsToFloat6, Math.max(intBitsToFloat7, intBitsToFloat8))));
    }

    public static final LayoutCoordinates findRootCoordinates(LayoutCoordinates layoutCoordinates) {
        LayoutCoordinates layoutCoordinates2;
        LayoutCoordinates parentLayoutCoordinates$1 = layoutCoordinates.getParentLayoutCoordinates$1();
        while (true) {
            LayoutCoordinates layoutCoordinates3 = parentLayoutCoordinates$1;
            layoutCoordinates2 = layoutCoordinates;
            layoutCoordinates = layoutCoordinates3;
            if (layoutCoordinates == null) {
                break;
            }
            parentLayoutCoordinates$1 = layoutCoordinates.getParentLayoutCoordinates$1();
        }
        NodeCoordinator nodeCoordinator = layoutCoordinates2 instanceof NodeCoordinator ? (NodeCoordinator) layoutCoordinates2 : null;
        if (nodeCoordinator == null) {
            return layoutCoordinates2;
        }
        NodeCoordinator nodeCoordinator2 = nodeCoordinator.wrappedBy;
        while (true) {
            NodeCoordinator nodeCoordinator3 = nodeCoordinator2;
            NodeCoordinator nodeCoordinator4 = nodeCoordinator;
            nodeCoordinator = nodeCoordinator3;
            if (nodeCoordinator == null) {
                return nodeCoordinator4;
            }
            nodeCoordinator2 = nodeCoordinator.wrappedBy;
        }
    }

    public static final long positionInRoot(LayoutCoordinates layoutCoordinates) {
        Offset.Companion.getClass();
        return layoutCoordinates.mo613localToRootMKHz9U(0L);
    }

    public static final long positionInWindow(LayoutCoordinates layoutCoordinates) {
        Offset.Companion.getClass();
        return layoutCoordinates.mo615localToWindowMKHz9U(0L);
    }
}
