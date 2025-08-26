package androidx.compose.ui.layout;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.node.NodeCoordinator;

/* loaded from: classes.dex */
public abstract class LayoutCoordinatesKt {
    public static final Rect boundsInParent(LayoutCoordinates layoutCoordinates) {
        Rect rectLocalBoundingBoxOf;
        LayoutCoordinates parentLayoutCoordinates$1 = layoutCoordinates.getParentLayoutCoordinates$1();
        return (parentLayoutCoordinates$1 == null || (rectLocalBoundingBoxOf = parentLayoutCoordinates$1.localBoundingBoxOf(layoutCoordinates, true)) == null) ? new Rect(0.0f, 0.0f, (int) (layoutCoordinates.mo612getSizeYbymL2g() >> 32), (int) (layoutCoordinates.mo612getSizeYbymL2g() & 4294967295L)) : rectLocalBoundingBoxOf;
    }

    public static final Rect boundsInRoot(LayoutCoordinates layoutCoordinates) {
        return findRootCoordinates(layoutCoordinates).localBoundingBoxOf(layoutCoordinates, true);
    }

    public static final Rect boundsInWindow(LayoutCoordinates layoutCoordinates) {
        LayoutCoordinates layoutCoordinatesFindRootCoordinates = findRootCoordinates(layoutCoordinates);
        float fMo612getSizeYbymL2g = (int) (layoutCoordinatesFindRootCoordinates.mo612getSizeYbymL2g() >> 32);
        float fMo612getSizeYbymL2g2 = (int) (layoutCoordinatesFindRootCoordinates.mo612getSizeYbymL2g() & 4294967295L);
        Rect rectLocalBoundingBoxOf = layoutCoordinatesFindRootCoordinates.localBoundingBoxOf(layoutCoordinates, true);
        float f = rectLocalBoundingBoxOf.left;
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > fMo612getSizeYbymL2g) {
            f = fMo612getSizeYbymL2g;
        }
        float f2 = rectLocalBoundingBoxOf.top;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > fMo612getSizeYbymL2g2) {
            f2 = fMo612getSizeYbymL2g2;
        }
        float f3 = rectLocalBoundingBoxOf.right;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 <= fMo612getSizeYbymL2g) {
            fMo612getSizeYbymL2g = f3;
        }
        float f4 = rectLocalBoundingBoxOf.bottom;
        float f5 = f4 >= 0.0f ? f4 : 0.0f;
        if (f5 <= fMo612getSizeYbymL2g2) {
            fMo612getSizeYbymL2g2 = f5;
        }
        if (f == fMo612getSizeYbymL2g || f2 == fMo612getSizeYbymL2g2) {
            Rect.Companion.getClass();
            return Rect.Zero;
        }
        Offset.Companion companion = Offset.Companion;
        long jMo617localToWindowMKHz9U = layoutCoordinatesFindRootCoordinates.mo617localToWindowMKHz9U((Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L));
        long jMo617localToWindowMKHz9U2 = layoutCoordinatesFindRootCoordinates.mo617localToWindowMKHz9U((Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(fMo612getSizeYbymL2g) << 32));
        long jMo617localToWindowMKHz9U3 = layoutCoordinatesFindRootCoordinates.mo617localToWindowMKHz9U((Float.floatToRawIntBits(fMo612getSizeYbymL2g) << 32) | (Float.floatToRawIntBits(fMo612getSizeYbymL2g2) & 4294967295L));
        long jMo617localToWindowMKHz9U4 = layoutCoordinatesFindRootCoordinates.mo617localToWindowMKHz9U((Float.floatToRawIntBits(fMo612getSizeYbymL2g2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
        float fIntBitsToFloat = Float.intBitsToFloat((int) (jMo617localToWindowMKHz9U >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jMo617localToWindowMKHz9U2 >> 32));
        float fIntBitsToFloat3 = Float.intBitsToFloat((int) (jMo617localToWindowMKHz9U4 >> 32));
        float fIntBitsToFloat4 = Float.intBitsToFloat((int) (jMo617localToWindowMKHz9U3 >> 32));
        float fMin = Math.min(fIntBitsToFloat, Math.min(fIntBitsToFloat2, Math.min(fIntBitsToFloat3, fIntBitsToFloat4)));
        float fMax = Math.max(fIntBitsToFloat, Math.max(fIntBitsToFloat2, Math.max(fIntBitsToFloat3, fIntBitsToFloat4)));
        float fIntBitsToFloat5 = Float.intBitsToFloat((int) (jMo617localToWindowMKHz9U & 4294967295L));
        float fIntBitsToFloat6 = Float.intBitsToFloat((int) (jMo617localToWindowMKHz9U2 & 4294967295L));
        float fIntBitsToFloat7 = Float.intBitsToFloat((int) (jMo617localToWindowMKHz9U4 & 4294967295L));
        float fIntBitsToFloat8 = Float.intBitsToFloat((int) (jMo617localToWindowMKHz9U3 & 4294967295L));
        return new Rect(fMin, Math.min(fIntBitsToFloat5, Math.min(fIntBitsToFloat6, Math.min(fIntBitsToFloat7, fIntBitsToFloat8))), fMax, Math.max(fIntBitsToFloat5, Math.max(fIntBitsToFloat6, Math.max(fIntBitsToFloat7, fIntBitsToFloat8))));
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
        return layoutCoordinates.mo615localToRootMKHz9U(0L);
    }

    public static final long positionInWindow(LayoutCoordinates layoutCoordinates) {
        Offset.Companion.getClass();
        return layoutCoordinates.mo617localToWindowMKHz9U(0L);
    }
}
