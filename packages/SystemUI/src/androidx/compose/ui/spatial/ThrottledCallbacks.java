package androidx.compose.ui.spatial;

import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class ThrottledCallbacks {
    public final Entry globalChangeEntries;
    public long screenOffset;
    public float[] viewToWindowMatrix;
    public long windowOffset;
    public final MutableIntObjectMap rectChangedMap = IntObjectMapKt.mutableIntObjectMapOf();
    public long minDebounceDeadline = -1;

    public final class Entry {
        public long bottomRight;
        public final Function1 callback;
        public final long debounceMillis;
        public long lastInvokeMillis;
        public long lastUninvokedFireMillis = -1;
        public final Entry next;
        public final DelegatableNode node;
        public final long throttleMillis;
        public long topLeft;

        public Entry(int i, long j, long j2, DelegatableNode delegatableNode, Function1 function1) {
            this.throttleMillis = j;
            this.debounceMillis = j2;
            this.node = delegatableNode;
            this.callback = function1;
            this.lastInvokeMillis = -j;
        }

        /* renamed from: fire-9b-9wPM, reason: not valid java name */
        public final void m727fire9b9wPM(long j, long j2, long j3, long j4, float[] fArr) {
            RelativeLayoutBounds relativeLayoutBounds;
            RelativeLayoutBounds relativeLayoutBounds2;
            DelegatableNode delegatableNode = this.node;
            LayoutCoordinates layoutCoordinatesM634requireCoordinator64DMado = DelegatableNodeKt.m634requireCoordinator64DMado(delegatableNode, 2);
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(delegatableNode);
            if (layoutNodeRequireLayoutNode.isPlaced()) {
                NodeCoordinator nodeCoordinator = layoutNodeRequireLayoutNode.nodes.outerCoordinator;
                if (nodeCoordinator != layoutCoordinatesM634requireCoordinator64DMado) {
                    nodeCoordinator.getClass();
                    Rect rectLocalBoundingBoxOf = nodeCoordinator.localBoundingBoxOf(layoutCoordinatesM634requireCoordinator64DMado, true);
                    relativeLayoutBounds = new RelativeLayoutBounds(IntOffsetKt.m856roundk4lQ0M(rectLocalBoundingBoxOf.m411getTopLeftF1C5BW0()), IntOffsetKt.m856roundk4lQ0M(rectLocalBoundingBoxOf.m408getBottomRightF1C5BW0()), j3, j4, fArr, delegatableNode, null);
                } else {
                    relativeLayoutBounds = new RelativeLayoutBounds(j, j2, j3, j4, fArr, delegatableNode, null);
                }
                relativeLayoutBounds2 = relativeLayoutBounds;
            } else {
                relativeLayoutBounds2 = null;
            }
            if (relativeLayoutBounds2 == null) {
                return;
            }
            this.callback.mo781invoke(relativeLayoutBounds2);
        }
    }

    public ThrottledCallbacks() {
        IntOffset.Companion companion = IntOffset.Companion;
        companion.getClass();
        this.windowOffset = 0L;
        companion.getClass();
        this.screenOffset = 0L;
    }

    /* renamed from: debounceEntry-b8qMvQI, reason: not valid java name */
    public static long m725debounceEntryb8qMvQI(Entry entry, long j, long j2, float[] fArr, long j3, long j4) {
        long j5 = entry.debounceMillis;
        if (j5 > 0) {
            long j6 = entry.lastUninvokedFireMillis;
            if (j6 > 0) {
                if (j3 - j6 <= j5) {
                    return Math.min(j4, j6 + j5);
                }
                entry.lastInvokeMillis = j3;
                entry.lastUninvokedFireMillis = -1L;
                entry.m727fire9b9wPM(entry.topLeft, entry.bottomRight, j, j2, fArr);
                return j4;
            }
        }
        return j4;
    }

    /* renamed from: fire-WY9HvpM, reason: not valid java name */
    public final void m726fireWY9HvpM(Entry entry, long j, long j2, float[] fArr, long j3) {
        boolean z = j3 - entry.lastInvokeMillis > entry.throttleMillis;
        long j4 = entry.debounceMillis;
        boolean z2 = j4 == 0;
        entry.lastUninvokedFireMillis = j3;
        if (z && z2) {
            entry.lastInvokeMillis = j3;
            entry.m727fire9b9wPM(entry.topLeft, entry.bottomRight, j, j2, fArr);
        }
        if (z2) {
            return;
        }
        long j5 = this.minDebounceDeadline;
        long j6 = j3 + j4;
        if (j5 <= 0 || j6 >= j5) {
            return;
        }
        this.minDebounceDeadline = j5;
    }
}
