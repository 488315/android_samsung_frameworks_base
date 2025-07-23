package androidx.compose.ui.spatial;

import android.os.Handler;
import android.os.Trace;
import androidx.collection.IntObjectMap;
import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Actual_androidKt;
import androidx.compose.ui.Actual_androidKt$$ExternalSyntheticLambda0;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.MatrixKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.MeasurePassDelegate;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.OwnedLayer;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RectManager {
    public final MutableRect cachedRect;
    public final MutableObjectList callbacks;
    public final Function0 dispatchLambda;
    public Actual_androidKt$$ExternalSyntheticLambda0 dispatchToken;
    public boolean isDirty;
    public boolean isFragmented;
    public boolean isScreenOrWindowDirty;
    public final RectList rects;
    public long scheduledDispatchDeadline;
    public final ThrottledCallbacks throttledCallbacks;

    public RectManager() {
        this(null, 1, null);
    }

    /* renamed from: outerToInnerOffset-Bjo55l4, reason: not valid java name */
    public static long m718outerToInnerOffsetBjo55l4(LayoutNode layoutNode) {
        float[] mo682getUnderlyingMatrixsQKQjiQ;
        int m721access$analyzeComponents58bKbWc;
        NodeCoordinator nodeCoordinator = layoutNode.nodes.outerCoordinator;
        Offset.Companion.getClass();
        NodeCoordinator nodeCoordinator2 = layoutNode.nodes.innerCoordinator;
        long j = 0;
        while (nodeCoordinator2 != null && nodeCoordinator2 != nodeCoordinator) {
            OwnedLayer ownedLayer = nodeCoordinator2.layer;
            j = IntOffsetKt.m853plusNvtHpc(j, nodeCoordinator2.position);
            nodeCoordinator2 = nodeCoordinator2.wrappedBy;
            if (ownedLayer != null && (m721access$analyzeComponents58bKbWc = RectManagerKt.m721access$analyzeComponents58bKbWc((mo682getUnderlyingMatrixsQKQjiQ = ownedLayer.mo682getUnderlyingMatrixsQKQjiQ()))) != 3) {
                if ((m721access$analyzeComponents58bKbWc & 2) == 0) {
                    IntOffset.Companion.getClass();
                    return IntOffset.Max;
                }
                j = Matrix.m482mapMKHz9U(j, mo682getUnderlyingMatrixsQKQjiQ);
            }
        }
        return IntOffsetKt.m854roundk4lQ0M(j);
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x0250 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x020a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dispatchCallbacks() {
        /*
            Method dump skipped, instructions count: 733
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.spatial.RectManager.dispatchCallbacks():void");
    }

    /* renamed from: insertOrUpdateTransformedNode-70tqf50, reason: not valid java name */
    public final void m719insertOrUpdateTransformedNode70tqf50(LayoutNode layoutNode, long j, boolean z) {
        NodeCoordinator nodeCoordinator = layoutNode.nodes.outerCoordinator;
        MeasurePassDelegate measurePassDelegate = layoutNode.layoutDelegate.measurePassDelegate;
        int measuredWidth = measurePassDelegate.getMeasuredWidth();
        int measuredHeight = measurePassDelegate.getMeasuredHeight();
        IntOffset.Companion companion = IntOffset.Companion;
        char c = ' ';
        int i = (int) (j >> 32);
        float f = i;
        long j2 = 4294967295L;
        float f2 = i + measuredWidth;
        MutableRect mutableRect = this.cachedRect;
        mutableRect.left = f;
        mutableRect.top = (int) (j & 4294967295L);
        mutableRect.right = f2;
        mutableRect.bottom = r10 + measuredHeight;
        while (nodeCoordinator != null) {
            OwnedLayer ownedLayer = nodeCoordinator.layer;
            long j3 = nodeCoordinator.position;
            IntOffset.Companion companion2 = IntOffset.Companion;
            long floatToRawIntBits = (Float.floatToRawIntBits((int) (j3 >> 32)) << 32) | (Float.floatToRawIntBits((int) (j3 & 4294967295L)) & 4294967295L);
            Offset.Companion companion3 = Offset.Companion;
            float intBitsToFloat = Float.intBitsToFloat((int) (floatToRawIntBits >> 32));
            float intBitsToFloat2 = Float.intBitsToFloat((int) (floatToRawIntBits & 4294967295L));
            mutableRect.left += intBitsToFloat;
            mutableRect.top += intBitsToFloat2;
            mutableRect.right += intBitsToFloat;
            mutableRect.bottom += intBitsToFloat2;
            nodeCoordinator = nodeCoordinator.wrappedBy;
            if (ownedLayer != null) {
                float[] mo682getUnderlyingMatrixsQKQjiQ = ownedLayer.mo682getUnderlyingMatrixsQKQjiQ();
                if (!MatrixKt.m489isIdentity58bKbWc(mo682getUnderlyingMatrixsQKQjiQ)) {
                    Matrix.m483mapimpl(mo682getUnderlyingMatrixsQKQjiQ, mutableRect);
                }
            }
        }
        int i2 = (int) mutableRect.left;
        int i3 = (int) mutableRect.top;
        int i4 = (int) mutableRect.right;
        int i5 = (int) mutableRect.bottom;
        int i6 = layoutNode.semanticsId;
        if (!z) {
            int i7 = i6 & 67108863;
            RectList rectList = this.rects;
            long[] jArr = rectList.items;
            int i8 = rectList.itemsSize;
            int i9 = 0;
            while (i9 < jArr.length - 2 && i9 < i8) {
                int i10 = i9 + 2;
                char c2 = c;
                int i11 = i8;
                long j4 = jArr[i10];
                long j5 = j2;
                if ((((int) j4) & 67108863) == i7) {
                    jArr[i9] = (i2 << c2) | (i3 & j5);
                    jArr[i9 + 1] = (i4 << c2) | (i5 & j5);
                    jArr[i10] = j4 | 2305843009213693952L;
                    break;
                } else {
                    i9 += 3;
                    i8 = i11;
                    c = c2;
                    j2 = j5;
                }
            }
        }
        LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
        RectList.insert$default(this.rects, i6, i2, i3, i4, i5, parent$ui_release != null ? parent$ui_release.semanticsId : -1);
        this.isDirty = true;
    }

    public final void insertOrUpdateTransformedNodeSubhierarchy(LayoutNode layoutNode) {
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            m719insertOrUpdateTransformedNode70tqf50(layoutNode2, layoutNode2.nodes.outerCoordinator.position, false);
            insertOrUpdateTransformedNodeSubhierarchy(layoutNode2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.compose.ui.Actual_androidKt$$ExternalSyntheticLambda0, java.lang.Runnable] */
    public final void invalidateCallbacksFor(LayoutNode layoutNode) {
        this.isDirty = true;
        int i = layoutNode.semanticsId & 67108863;
        RectList rectList = this.rects;
        long[] jArr = rectList.items;
        int i2 = rectList.itemsSize;
        int i3 = 0;
        while (true) {
            if (i3 >= jArr.length - 2 || i3 >= i2) {
                break;
            }
            int i4 = i3 + 2;
            long j = jArr[i4];
            if ((((int) j) & 67108863) == i) {
                jArr[i4] = 2305843009213693952L | j;
                break;
            }
            i3 += 3;
        }
        Actual_androidKt$$ExternalSyntheticLambda0 actual_androidKt$$ExternalSyntheticLambda0 = this.dispatchToken;
        boolean z = actual_androidKt$$ExternalSyntheticLambda0 != null;
        long j2 = this.throttledCallbacks.minDebounceDeadline;
        if (j2 >= 0 || !z) {
            if (this.scheduledDispatchDeadline == j2 && z) {
                return;
            }
            if (actual_androidKt$$ExternalSyntheticLambda0 != null) {
                Handler handler = Actual_androidKt.handler;
                Actual_androidKt.handler.removeCallbacks(actual_androidKt$$ExternalSyntheticLambda0);
            }
            Handler handler2 = Actual_androidKt.handler;
            long currentTimeMillis = System.currentTimeMillis();
            long max = Math.max(j2, 16 + currentTimeMillis);
            this.scheduledDispatchDeadline = max;
            final Function0 function0 = this.dispatchLambda;
            ?? r2 = new Runnable() { // from class: androidx.compose.ui.Actual_androidKt$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Function0 function02 = Function0.this;
                    Handler handler3 = Actual_androidKt.handler;
                    function02.invoke();
                }
            };
            Actual_androidKt.handler.postDelayed(r2, max - currentTimeMillis);
            this.dispatchToken = r2;
        }
    }

    public final void onLayoutLayerPositionalPropertiesChanged(LayoutNode layoutNode) {
        if (ComposeUiFlags.isRectTrackingEnabled) {
            long m718outerToInnerOffsetBjo55l4 = m718outerToInnerOffsetBjo55l4(layoutNode);
            if (!RectManagerKt.m722access$isSetgyyYBs(m718outerToInnerOffsetBjo55l4)) {
                insertOrUpdateTransformedNodeSubhierarchy(layoutNode);
                return;
            }
            layoutNode.outerToInnerOffset = m718outerToInnerOffsetBjo55l4;
            layoutNode.outerToInnerOffsetDirty = false;
            MutableVector mutableVector = layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            for (int i2 = 0; i2 < i; i2++) {
                LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
                m720onLayoutPositionChanged70tqf50(layoutNode2, layoutNode2.nodes.outerCoordinator.position, false);
            }
            invalidateCallbacksFor(layoutNode);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [int] */
    /* renamed from: onLayoutPositionChanged-70tqf50, reason: not valid java name */
    public final void m720onLayoutPositionChanged70tqf50(LayoutNode layoutNode, long j, boolean z) {
        boolean z2;
        int i;
        char c;
        int i2;
        long j2;
        int i3;
        boolean z3;
        long j3;
        int i4;
        char c2;
        int i5;
        char c3;
        long m854roundk4lQ0M;
        float[] mo682getUnderlyingMatrixsQKQjiQ;
        int m721access$analyzeComponents58bKbWc;
        char c4 = 3;
        boolean z4 = true;
        if (ComposeUiFlags.isRectTrackingEnabled) {
            MeasurePassDelegate measurePassDelegate = layoutNode.layoutDelegate.measurePassDelegate;
            int measuredWidth = measurePassDelegate.getMeasuredWidth();
            int measuredHeight = measurePassDelegate.getMeasuredHeight();
            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
            long j4 = layoutNode.offsetFromRoot;
            long j5 = layoutNode.lastSize;
            int i6 = (int) (j5 >> 32);
            int i7 = (int) (j5 & 4294967295L);
            boolean z5 = false;
            if (parent$ui_release != null) {
                i3 = -1;
                boolean z6 = parent$ui_release.outerToInnerOffsetDirty;
                j2 = 4294967295L;
                long j6 = parent$ui_release.offsetFromRoot;
                char c5 = ' ';
                i2 = i6;
                long j7 = parent$ui_release.outerToInnerOffset;
                if (RectManagerKt.m722access$isSetgyyYBs(j6)) {
                    if (z6) {
                        j7 = m718outerToInnerOffsetBjo55l4(parent$ui_release);
                        parent$ui_release.outerToInnerOffset = j7;
                        parent$ui_release.outerToInnerOffsetDirty = false;
                    }
                    z3 = !RectManagerKt.m722access$isSetgyyYBs(j7);
                    j3 = IntOffset.m851plusqkQi6aY(IntOffset.m851plusqkQi6aY(j6, j7), j);
                    z2 = true;
                    i = measuredHeight;
                    c = c5;
                } else {
                    NodeCoordinator nodeCoordinator = layoutNode.nodes.outerCoordinator;
                    Offset.Companion.getClass();
                    long j8 = 0;
                    while (true) {
                        if (nodeCoordinator == null) {
                            z2 = z4;
                            i = measuredHeight;
                            m854roundk4lQ0M = IntOffsetKt.m854roundk4lQ0M(j8);
                            break;
                        }
                        OwnedLayer ownedLayer = nodeCoordinator.layer;
                        z2 = z4;
                        i = measuredHeight;
                        j8 = IntOffsetKt.m853plusNvtHpc(j8, nodeCoordinator.position);
                        nodeCoordinator = nodeCoordinator.wrappedBy;
                        if (ownedLayer != null && (m721access$analyzeComponents58bKbWc = RectManagerKt.m721access$analyzeComponents58bKbWc((mo682getUnderlyingMatrixsQKQjiQ = ownedLayer.mo682getUnderlyingMatrixsQKQjiQ()))) != 3) {
                            if ((m721access$analyzeComponents58bKbWc & 2) == 0) {
                                IntOffset.Companion.getClass();
                                m854roundk4lQ0M = IntOffset.Max;
                                break;
                            }
                            j8 = Matrix.m482mapMKHz9U(j8, mo682getUnderlyingMatrixsQKQjiQ);
                        }
                        z4 = z2;
                        measuredHeight = i;
                    }
                    j3 = m854roundk4lQ0M;
                    z3 = false;
                    c = c5;
                }
            } else {
                z2 = true;
                i = measuredHeight;
                c = ' ';
                i2 = i6;
                j2 = 4294967295L;
                i3 = -1;
                z3 = false;
                j3 = j;
            }
            if (z3 || !RectManagerKt.m722access$isSetgyyYBs(j3)) {
                m719insertOrUpdateTransformedNode70tqf50(layoutNode, j, z);
                return;
            }
            layoutNode.offsetFromRoot = j3;
            int i8 = i;
            IntSize.Companion companion = IntSize.Companion;
            layoutNode.lastSize = (measuredWidth << c) | (i8 & j2);
            int i9 = (int) (j3 >> c);
            int i10 = (int) (j3 & j2);
            int i11 = i9 + measuredWidth;
            int i12 = i10 + i8;
            if (!z && IntOffset.m849equalsimpl0(j3, j4) && i2 == measuredWidth && i7 == i8) {
                return;
            }
            int i13 = layoutNode.semanticsId;
            if (!z) {
                int i14 = i13 & 67108863;
                RectList rectList = this.rects;
                long[] jArr = rectList.items;
                int i15 = rectList.itemsSize;
                int i16 = 0;
                while (i16 < jArr.length - 2 && i16 < i15) {
                    int i17 = i16 + 2;
                    boolean z7 = z5;
                    int i18 = i15;
                    long j9 = jArr[i17];
                    char c6 = c4;
                    if ((((int) j9) & 67108863) == i14) {
                        long j10 = jArr[i16];
                        jArr[i16] = (i10 & j2) | (i9 << c);
                        jArr[i16 + 1] = (i12 & j2) | (i11 << c);
                        long j11 = 2305843009213693952L;
                        jArr[i17] = j9 | 2305843009213693952L;
                        if ((i9 - ((int) (j10 >> c)) != 0 ? z2 : z7 ? 1 : 0) | (i10 - ((int) j10) != 0 ? z2 : z7 ? 1 : 0)) {
                            long j12 = -4503599560261633L;
                            char c7 = 26;
                            long j13 = (j9 & (-4503599560261633L)) | (((i16 + 3) & 67108863) << 26);
                            long[] jArr2 = rectList.items;
                            long[] jArr3 = rectList.stack;
                            int i19 = rectList.itemsSize / 3;
                            jArr3[z7 ? 1 : 0] = j13;
                            for (?? r7 = z2; r7 > 0; r7 = i4) {
                                i4 = r7 - 1;
                                long j14 = jArr3[i4];
                                int i20 = ((int) j14) & 67108863;
                                long j15 = j12;
                                int i21 = ((int) (j14 >> c7)) & 67108863;
                                char c8 = '4';
                                char c9 = 511;
                                int i22 = ((int) (j14 >> 52)) & 511;
                                int i23 = i22 == 511 ? i19 : i22 + i21;
                                if (i21 < 0) {
                                    break;
                                }
                                while (i21 < jArr2.length - 2 && i21 < i23) {
                                    int i24 = i21 + 2;
                                    long j16 = jArr2[i24];
                                    char c10 = c8;
                                    long j17 = j11;
                                    if ((((int) (j16 >> c7)) & 67108863) == i20) {
                                        long j18 = jArr2[i21];
                                        int i25 = i21 + 1;
                                        c2 = c7;
                                        i5 = i23;
                                        long j19 = jArr2[i25];
                                        jArr2[i21] = ((((int) j18) + r5) & j2) | ((((int) (j18 >> c)) + r6) << c);
                                        jArr2[i25] = ((((int) j19) + r5) & j2) | ((((int) (j19 >> c)) + r6) << c);
                                        jArr2[i24] = j16 | j17;
                                        c3 = 511;
                                        if ((((int) (j16 >> c10)) & 511) > 0) {
                                            int i26 = (i4 == true ? 1 : 0) + 1;
                                            jArr3[i4 == true ? 1 : 0] = (j16 & j15) | (((i21 + 3) & 67108863) << c2);
                                            i4 = i26;
                                        }
                                    } else {
                                        c2 = c7;
                                        i5 = i23;
                                        c3 = c9;
                                    }
                                    i21 += 3;
                                    c8 = c10;
                                    c9 = c3;
                                    i23 = i5;
                                    c7 = c2;
                                    j11 = j17;
                                }
                                j12 = j15;
                                c7 = c7;
                                j11 = j11;
                            }
                        }
                        this.isDirty = z2;
                    }
                    i16 += 3;
                    z5 = z7 ? 1 : 0;
                    i15 = i18;
                    c4 = c6;
                }
            }
            LayoutNode parent$ui_release2 = layoutNode.getParent$ui_release();
            RectList.insert$default(this.rects, i13, i9, i10, i11, i12, parent$ui_release2 != null ? parent$ui_release2.semanticsId : i3);
            this.isDirty = z2;
        }
    }

    public final void remove(LayoutNode layoutNode) {
        int i = layoutNode.semanticsId & 67108863;
        RectList rectList = this.rects;
        long[] jArr = rectList.items;
        int i2 = rectList.itemsSize;
        int i3 = 0;
        while (true) {
            if (i3 >= jArr.length - 2 || i3 >= i2) {
                break;
            }
            int i4 = i3 + 2;
            if ((((int) jArr[i4]) & 67108863) == i) {
                jArr[i3] = -1;
                jArr[i3 + 1] = -1;
                jArr[i4] = 2305843009213693951L;
                break;
            }
            i3 += 3;
        }
        this.isDirty = true;
        this.isFragmented = true;
    }

    public RectManager(IntObjectMap intObjectMap) {
        this.rects = new RectList();
        this.throttledCallbacks = new ThrottledCallbacks();
        this.callbacks = new MutableObjectList(0, 1, null);
        this.scheduledDispatchDeadline = -1L;
        this.dispatchLambda = new Function0() { // from class: androidx.compose.ui.spatial.RectManager$dispatchLambda$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RectManager rectManager = RectManager.this;
                rectManager.dispatchToken = null;
                Trace.beginSection("OnPositionedDispatch");
                try {
                    rectManager.dispatchCallbacks();
                    Unit unit = Unit.INSTANCE;
                    Trace.endSection();
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    Trace.endSection();
                    throw th;
                }
            }
        };
        this.cachedRect = new MutableRect(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public RectManager(IntObjectMap intObjectMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? IntObjectMapKt.EmptyIntObjectMap : intObjectMap);
    }
}
