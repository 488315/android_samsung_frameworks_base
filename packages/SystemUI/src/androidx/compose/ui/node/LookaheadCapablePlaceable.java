package androidx.compose.ui.node;

import androidx.collection.MutableObjectFloatMap;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.PlaceableKt;
import androidx.compose.ui.layout.Ruler;
import androidx.compose.ui.layout.VerticalAlignmentLine;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class LookaheadCapablePlaceable extends Placeable implements MeasureScopeWithLayoutNode, MotionReferencePlacementDelegate {
    public static final Function1 onCommitAffectingRuler;
    public boolean isPlacedUnderMotionFrameOfReference;
    public boolean isPlacingForAlignment;
    public boolean isShallowPlacing;
    public final Placeable.PlacementScope placementScope = PlaceableKt.PlacementScope(this);
    public MutableObjectFloatMap rulerValues;
    public MutableObjectFloatMap rulerValuesCache;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        onCommitAffectingRuler = new Function1() { // from class: androidx.compose.ui.node.LookaheadCapablePlaceable$Companion$onCommitAffectingRuler$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                PlaceableResult placeableResult = (PlaceableResult) obj;
                if (placeableResult.isValidOwnerScope()) {
                    placeableResult.placeable.captureRulers(placeableResult);
                }
                return Unit.INSTANCE;
            }
        };
    }

    public static void invalidateAlignmentLinesFromPositionChange(NodeCoordinator nodeCoordinator) {
        LayoutNodeAlignmentLines layoutNodeAlignmentLines;
        NodeCoordinator nodeCoordinator2 = nodeCoordinator.wrapped;
        LayoutNode layoutNode = nodeCoordinator2 != null ? nodeCoordinator2.layoutNode : null;
        LayoutNode layoutNode2 = nodeCoordinator.layoutNode;
        if (!Intrinsics.areEqual(layoutNode, layoutNode2)) {
            layoutNode2.layoutDelegate.measurePassDelegate.alignmentLines.onAlignmentsChanged();
            return;
        }
        AlignmentLinesOwner parentAlignmentLinesOwner = layoutNode2.layoutDelegate.measurePassDelegate.getParentAlignmentLinesOwner();
        if (parentAlignmentLinesOwner == null || (layoutNodeAlignmentLines = ((MeasurePassDelegate) parentAlignmentLinesOwner).alignmentLines) == null) {
            return;
        }
        layoutNodeAlignmentLines.onAlignmentsChanged();
    }

    public abstract int calculateAlignmentLine(AlignmentLine alignmentLine);

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00fa, code lost:
    
        if (((((~r8) << 6) & r8) & (-9187201950435737472L)) == 0) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00fc, code lost:
    
        r5 = r4.findFirstAvailableSlot(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0102, code lost:
    
        if (r4.growthLimit != 0) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0115, code lost:
    
        if (((r4.metadata[r5 >> 3] >> ((r5 & 7) << 3)) & r25) != 254) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0117, code lost:
    
        r36 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x011b, code lost:
    
        r5 = r4._capacity;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x011f, code lost:
    
        if (r5 <= 8) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0121, code lost:
    
        r6 = r4._size;
        r24 = kotlin.ULong.$r8$clinit;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0135, code lost:
    
        if (java.lang.Long.compareUnsigned(r6 * 32, r5 * 25) > 0) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0137, code lost:
    
        r5 = r4.metadata;
        r6 = r4._capacity;
        r7 = r4.keys;
        r8 = r4.values;
        r9 = (r6 + 7) >> 3;
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0147, code lost:
    
        if (r7 >= r9) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0149, code lost:
    
        r39 = r7;
        r7 = r5[r7] & (-9187201950435737472L);
        r5[r39] = (-72340172838076674L) & ((~r7) + (r7 >>> 7));
        r7 = r39 + 1;
        r8 = r8;
        r10 = r10;
        r9 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x016a, code lost:
    
        r34 = r8;
        r36 = r10;
        r7 = r5.length;
        r8 = r7 - 1;
        r7 = r7 - 2;
        r5[r7] = (r5[r7] & 72057594037927935L) | (-72057594037927936L);
        r5[r8] = r5[0];
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0188, code lost:
    
        if (r7 == r6) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x018a, code lost:
    
        r8 = r7 >> 3;
        r37 = (r7 & 7) << 3;
        r9 = (r5[r8] >> r37) & r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0198, code lost:
    
        if (r9 != 128) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x019a, code lost:
    
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x019f, code lost:
    
        if (r9 == 254) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01a2, code lost:
    
        r9 = r7[r7];
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01a4, code lost:
    
        if (r9 == null) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01a6, code lost:
    
        r9 = r9.hashCode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01ab, code lost:
    
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x01ad, code lost:
    
        r9 = r9 * (-862048943);
        r10 = (r9 ^ (r9 << 16)) >>> 7;
        r43 = r4.findFirstAvailableSlot(r10);
        r10 = r10 & r6;
        r45 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01cb, code lost:
    
        if ((((r43 - r10) & r6) / 8) != (((r7 - r10) & r45) / 8)) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01cd, code lost:
    
        r5[r8] = (r5[r8] & (~(r25 << r37))) | ((r9 & 127) << r37);
        r5[r5.length - 1] = (r5[0] & 72057594037927935L) | Long.MIN_VALUE;
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01eb, code lost:
    
        r6 = r45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01ee, code lost:
    
        r48 = r7;
        r6 = r43 >> 3;
        r49 = r5[r6];
        r7 = (r43 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01fe, code lost:
    
        if (((r49 >> r7) & r25) != 128) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0200, code lost:
    
        r5[r6] = (r49 & (~(r25 << r7))) | ((r9 & 127) << r7);
        r5[r8] = (r5[r8] & (~(r25 << r37))) | (128 << r37);
        r7[r43] = r7[r48];
        r7[r48] = null;
        r34[r43] = r34[r48];
        r34[r48] = 0.0f;
        r7 = r48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x022c, code lost:
    
        r5[r6] = ((r9 & 127) << r7) | (r49 & (~(r25 << r7)));
        r6 = r7[r43];
        r7[r43] = r7[r48];
        r7[r48] = r6;
        r6 = r34[r43];
        r34[r43] = r34[r48];
        r34[r48] = r6;
        r7 = r48 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x024f, code lost:
    
        r5[r5.length - 1] = (r5[0] & 72057594037927935L) | Long.MIN_VALUE;
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x025d, code lost:
    
        r4.growthLimit = androidx.collection.ScatterMapKt.loadedCapacity(r4._capacity) - r4._size;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x026a, code lost:
    
        r36 = r10;
        r5 = androidx.collection.ScatterMapKt.nextCapacity(r4._capacity);
        r6 = r4.metadata;
        r7 = r4.keys;
        r8 = r4.values;
        r9 = r4._capacity;
        r4.initializeStorage(r5);
        r5 = r4.metadata;
        r10 = r4.keys;
        r5 = r4.values;
        r5 = r4._capacity;
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x028d, code lost:
    
        if (r5 >= r9) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x029d, code lost:
    
        if (((r6[r5 >> 3] >> ((r5 & 7) << 3)) & r25) >= 128) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x029f, code lost:
    
        r34 = r7[r5];
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x02a1, code lost:
    
        if (r34 == null) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x02a3, code lost:
    
        r37 = r34.hashCode();
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x02a8, code lost:
    
        r37 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x02aa, code lost:
    
        r37 = r37 * (-862048943);
        r37 = r37 ^ (r37 << 16);
        r39 = r5;
        r5 = r4.findFirstAvailableSlot(r37 >>> 7);
        r5 = r37 & 127;
        r37 = r6;
        r43 = r5 >> 3;
        r44 = (r5 & 7) << 3;
        r5 = (r5[r43] & (~(r25 << r44))) | (r5 << r44);
        r5[r43] = r5;
        r5[(((r5 - 7) & r5) + (r5 & 7)) >> 3] = r5;
        r10[r5] = r34;
        r5[r5] = r8[r39];
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x02e7, code lost:
    
        r39 = r5;
        r37 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x02eb, code lost:
    
        r5 = r39 + 1;
        r6 = r37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x02f0, code lost:
    
        r5 = r4.findFirstAvailableSlot(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x02f4, code lost:
    
        r4._size++;
        r2 = r4.growthLimit;
        r6 = r4.metadata;
        r7 = r5 >> 3;
        r8 = r6[r7];
        r10 = (r5 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x030c, code lost:
    
        if (((r8 >> r10) & r25) != 128) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x030e, code lost:
    
        r24 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0311, code lost:
    
        r24 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0313, code lost:
    
        r4.growthLimit = r2 - r24;
        r2 = r4._capacity;
        r6 = ((~(r25 << r10)) & r8) | (r6 << r10);
        r6[r7] = r6;
        r6[(((r5 - 7) & r2) + (r2 & 7)) >> 3] = r6;
        r7 = ~r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void captureRulers(final PlaceableResult placeableResult) {
        int i;
        MutableObjectFloatMap mutableObjectFloatMap;
        long j;
        char c;
        long j2;
        LookaheadCapablePlaceable parent;
        OwnerSnapshotObserver ownerSnapshotObserver;
        Object[] objArr;
        float[] fArr;
        long[] jArr;
        long j3;
        MutableObjectFloatMap mutableObjectFloatMap2;
        Object[] objArr2;
        float[] fArr2;
        long[] jArr2;
        long j4;
        int i2;
        int iNumberOfTrailingZeros;
        if (this.isPlacingForAlignment || placeableResult.result.getRulers() == null) {
            return;
        }
        MutableObjectFloatMap mutableObjectFloatMap3 = this.rulerValuesCache;
        if (mutableObjectFloatMap3 == null) {
            mutableObjectFloatMap3 = new MutableObjectFloatMap(0, 1, null);
            this.rulerValuesCache = mutableObjectFloatMap3;
        }
        MutableObjectFloatMap mutableObjectFloatMap4 = this.rulerValues;
        if (mutableObjectFloatMap4 == null) {
            mutableObjectFloatMap4 = new MutableObjectFloatMap(0, 1, null);
            this.rulerValues = mutableObjectFloatMap4;
        }
        Object[] objArr3 = mutableObjectFloatMap4.keys;
        float[] fArr3 = mutableObjectFloatMap4.values;
        long[] jArr3 = mutableObjectFloatMap4.metadata;
        int length = jArr3.length - 2;
        long j5 = 255;
        int i3 = 8;
        if (length >= 0) {
            int i4 = 0;
            i = 0;
            c = 7;
            while (true) {
                long j6 = jArr3[i4];
                j2 = 128;
                if ((((~j6) << 7) & j6 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i5 = 8 - ((~(i4 - length)) >>> 31);
                    int i6 = 0;
                    while (i6 < i5) {
                        if ((j6 & j5) < 128) {
                            int i7 = (i4 << 3) + i6;
                            j4 = j5;
                            Object obj = objArr3[i7];
                            float f = fArr3[i7];
                            int iHashCode = (obj != null ? obj.hashCode() : 0) * (-862048943);
                            int i8 = iHashCode ^ (iHashCode << 16);
                            int i9 = i8 >>> 7;
                            j3 = j6;
                            int i10 = i8 & 127;
                            int i11 = mutableObjectFloatMap3._capacity;
                            int i12 = i9 & i11;
                            int i13 = 0;
                            while (true) {
                                long[] jArr4 = mutableObjectFloatMap3.metadata;
                                int i14 = i12 >> 3;
                                int i15 = (i12 & 7) << 3;
                                objArr2 = objArr3;
                                fArr2 = fArr3;
                                long j7 = (jArr4[i14] >>> i15) | ((jArr4[i14 + 1] << (64 - i15)) & ((-i15) >> 63));
                                mutableObjectFloatMap2 = mutableObjectFloatMap4;
                                long j8 = i10;
                                int i16 = i10;
                                long j9 = j7 ^ (j8 * 72340172838076673L);
                                long j10 = (j9 - 72340172838076673L) & (~j9) & (-9187201950435737472L);
                                while (true) {
                                    if (j10 == 0) {
                                        break;
                                    }
                                    iNumberOfTrailingZeros = (i12 + (Long.numberOfTrailingZeros(j10) >> 3)) & i11;
                                    long j11 = j10;
                                    if (Intrinsics.areEqual(mutableObjectFloatMap3.keys[iNumberOfTrailingZeros], obj)) {
                                        jArr2 = jArr3;
                                        break;
                                    }
                                    j10 = j11 & (j11 - 1);
                                }
                                i13 += 8;
                                i12 = (i12 + i13) & i11;
                                mutableObjectFloatMap4 = mutableObjectFloatMap2;
                                i10 = i16;
                                objArr3 = objArr2;
                                fArr3 = fArr2;
                            }
                            if (iNumberOfTrailingZeros < 0) {
                                iNumberOfTrailingZeros = ~iNumberOfTrailingZeros;
                            }
                            mutableObjectFloatMap3.keys[iNumberOfTrailingZeros] = obj;
                            mutableObjectFloatMap3.values[iNumberOfTrailingZeros] = f;
                            i2 = 8;
                        } else {
                            j3 = j6;
                            mutableObjectFloatMap2 = mutableObjectFloatMap4;
                            objArr2 = objArr3;
                            fArr2 = fArr3;
                            jArr2 = jArr3;
                            j4 = j5;
                            i2 = i3;
                        }
                        i6++;
                        i3 = i2;
                        j6 = j3 >> i2;
                        j5 = j4;
                        mutableObjectFloatMap4 = mutableObjectFloatMap2;
                        objArr3 = objArr2;
                        jArr3 = jArr2;
                        fArr3 = fArr2;
                    }
                    mutableObjectFloatMap = mutableObjectFloatMap4;
                    objArr = objArr3;
                    fArr = fArr3;
                    jArr = jArr3;
                    j = j5;
                    if (i5 != i3) {
                        break;
                    }
                } else {
                    mutableObjectFloatMap = mutableObjectFloatMap4;
                    objArr = objArr3;
                    fArr = fArr3;
                    jArr = jArr3;
                    j = j5;
                }
                if (i4 == length) {
                    break;
                }
                i4++;
                j5 = j;
                mutableObjectFloatMap4 = mutableObjectFloatMap;
                objArr3 = objArr;
                jArr3 = jArr;
                fArr3 = fArr;
                i3 = 8;
            }
        } else {
            i = 0;
            mutableObjectFloatMap = mutableObjectFloatMap4;
            j = 255;
            c = 7;
            j2 = 128;
        }
        mutableObjectFloatMap.clear();
        AndroidComposeView androidComposeView = getLayoutNode().owner;
        if (androidComposeView != null && (ownerSnapshotObserver = androidComposeView.snapshotObserver) != null) {
            ownerSnapshotObserver.observeReads$ui_release(placeableResult, onCommitAffectingRuler, new Function0() { // from class: androidx.compose.ui.node.LookaheadCapablePlaceable.captureRulers.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Function1 rulers = placeableResult.result.getRulers();
                    if (rulers != null) {
                        final LookaheadCapablePlaceable lookaheadCapablePlaceable = this;
                        lookaheadCapablePlaceable.getClass();
                        rulers.mo781invoke(new Density() { // from class: androidx.compose.ui.node.LookaheadCapablePlaceable$rulerScope$1
                            @Override // androidx.compose.ui.unit.Density
                            public final float getDensity() {
                                return lookaheadCapablePlaceable.getDensity();
                            }

                            @Override // androidx.compose.ui.unit.FontScaling
                            public final float getFontScale() {
                                return lookaheadCapablePlaceable.getFontScale();
                            }
                        });
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        MutableObjectFloatMap mutableObjectFloatMap5 = mutableObjectFloatMap;
        Object[] objArr4 = mutableObjectFloatMap5.keys;
        long[] jArr5 = mutableObjectFloatMap5.metadata;
        int length2 = jArr5.length - 2;
        if (length2 >= 0) {
            int i17 = i;
            while (true) {
                long j12 = jArr5[i17];
                if ((((~j12) << c) & j12 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i18 = 8 - ((~(i17 - length2)) >>> 31);
                    for (int i19 = i; i19 < i18; i19++) {
                        if ((j12 & j) < j2) {
                            Ruler ruler = (Ruler) objArr4[(i17 << 3) + i19];
                            if (mutableObjectFloatMap3.findKeyIndex(ruler) < 0 && (parent = getParent()) != null) {
                                do {
                                    MutableObjectFloatMap mutableObjectFloatMap6 = parent.rulerValues;
                                    if (mutableObjectFloatMap6 == null || mutableObjectFloatMap6.findKeyIndex(ruler) < 0) {
                                        parent = parent.getParent();
                                    }
                                } while (parent != null);
                            }
                        }
                        j12 >>= 8;
                    }
                    if (i18 != 8) {
                        break;
                    }
                }
                if (i17 == length2) {
                    break;
                } else {
                    i17++;
                }
            }
        }
        mutableObjectFloatMap3.clear();
    }

    @Override // androidx.compose.ui.layout.Measured
    public final int get(AlignmentLine alignmentLine) {
        int iCalculateAlignmentLine;
        long j;
        if (!getHasMeasureResult() || (iCalculateAlignmentLine = calculateAlignmentLine(alignmentLine)) == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        if (alignmentLine instanceof VerticalAlignmentLine) {
            long j2 = this.apparentToRealOffset;
            IntOffset.Companion companion = IntOffset.Companion;
            j = j2 >> 32;
        } else {
            long j3 = this.apparentToRealOffset;
            IntOffset.Companion companion2 = IntOffset.Companion;
            j = j3 & 4294967295L;
        }
        return iCalculateAlignmentLine + ((int) j);
    }

    public abstract LookaheadCapablePlaceable getChild();

    public abstract LayoutCoordinates getCoordinates();

    public abstract boolean getHasMeasureResult();

    @Override // androidx.compose.ui.node.MeasureScopeWithLayoutNode
    public abstract LayoutNode getLayoutNode();

    public abstract MeasureResult getMeasureResult$ui_release();

    public abstract LookaheadCapablePlaceable getParent();

    /* renamed from: getPosition-nOcc-ac, reason: not valid java name */
    public abstract long mo651getPositionnOccac();

    @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
    public boolean isLookingAhead() {
        return false;
    }

    @Override // androidx.compose.ui.layout.MeasureScope
    public final MeasureResult layout(final int i, final int i2, final Map map, final Function1 function1) {
        if ((i & (-16777216)) != 0 || ((-16777216) & i2) != 0) {
            InlineClassHelperKt.throwIllegalStateException("Size(" + i + " x " + i2 + ") is out of range. Each dimension must be between 0 and 16777215.");
        }
        final Function1 function12 = null;
        return new MeasureResult() { // from class: androidx.compose.ui.node.LookaheadCapablePlaceable.layout.1
            @Override // androidx.compose.ui.layout.MeasureResult
            public final Map getAlignmentLines() {
                return map;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final int getHeight() {
                return i2;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final Function1 getRulers() {
                return function12;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final int getWidth() {
                return i;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final void placeChildren() {
                function1.mo781invoke(this.placementScope);
            }
        };
    }

    public abstract void replace$ui_release();

    @Override // androidx.compose.ui.node.MotionReferencePlacementDelegate
    public final void updatePlacedUnderMotionFrameOfReference(boolean z) {
        LookaheadCapablePlaceable parent = getParent();
        LayoutNode layoutNode = parent != null ? parent.getLayoutNode() : null;
        if (Intrinsics.areEqual(layoutNode, getLayoutNode())) {
            this.isPlacedUnderMotionFrameOfReference = z;
            return;
        }
        if ((layoutNode != null ? layoutNode.layoutDelegate.layoutState : null) != LayoutNode.LayoutState.LayingOut) {
            if ((layoutNode != null ? layoutNode.layoutDelegate.layoutState : null) != LayoutNode.LayoutState.LookaheadLayingOut) {
                return;
            }
        }
        this.isPlacedUnderMotionFrameOfReference = z;
    }
}
