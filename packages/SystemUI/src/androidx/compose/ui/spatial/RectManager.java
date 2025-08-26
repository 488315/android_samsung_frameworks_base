package androidx.compose.ui.spatial;

import android.os.Handler;
import android.os.Trace;
import androidx.collection.IntObjectMap;
import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Actual_androidKt;
import androidx.compose.ui.Actual_androidKt$$ExternalSyntheticLambda0;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.geometry.MutableRect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.MatrixKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.MeasurePassDelegate;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.OwnedLayer;
import androidx.compose.ui.spatial.ThrottledCallbacks;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
    public static long m720outerToInnerOffsetBjo55l4(LayoutNode layoutNode) {
        float[] fArrMo684getUnderlyingMatrixsQKQjiQ;
        int iM723access$analyzeComponents58bKbWc;
        NodeCoordinator nodeCoordinator = layoutNode.nodes.outerCoordinator;
        Offset.Companion.getClass();
        NodeCoordinator nodeCoordinator2 = layoutNode.nodes.innerCoordinator;
        long jM855plusNvtHpc = 0;
        while (nodeCoordinator2 != null && nodeCoordinator2 != nodeCoordinator) {
            OwnedLayer ownedLayer = nodeCoordinator2.layer;
            jM855plusNvtHpc = IntOffsetKt.m855plusNvtHpc(jM855plusNvtHpc, nodeCoordinator2.position);
            nodeCoordinator2 = nodeCoordinator2.wrappedBy;
            if (ownedLayer != null && (iM723access$analyzeComponents58bKbWc = RectManagerKt.m723access$analyzeComponents58bKbWc((fArrMo684getUnderlyingMatrixsQKQjiQ = ownedLayer.mo684getUnderlyingMatrixsQKQjiQ()))) != 3) {
                if ((iM723access$analyzeComponents58bKbWc & 2) == 0) {
                    IntOffset.Companion.getClass();
                    return IntOffset.Max;
                }
                jM855plusNvtHpc = Matrix.m484mapMKHz9U(jM855plusNvtHpc, fArrMo684getUnderlyingMatrixsQKQjiQ);
            }
        }
        return IntOffsetKt.m856roundk4lQ0M(jM855plusNvtHpc);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0250 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x020a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchCallbacks() {
        RectList rectList;
        ThrottledCallbacks throttledCallbacks;
        long j;
        long j2;
        char c;
        ThrottledCallbacks throttledCallbacks2;
        long jM725debounceEntryb8qMvQI;
        long[] jArr;
        long j3;
        long j4;
        boolean z = true;
        Handler handler = Actual_androidKt.handler;
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean z2 = this.isDirty;
        boolean z3 = z2 || this.isScreenOrWindowDirty;
        RectList rectList2 = this.rects;
        ThrottledCallbacks throttledCallbacks3 = this.throttledCallbacks;
        if (z2) {
            this.isDirty = false;
            MutableObjectList mutableObjectList = this.callbacks;
            Object[] objArr = mutableObjectList.content;
            int i = mutableObjectList._size;
            for (int i2 = 0; i2 < i; i2++) {
                ((Function0) objArr[i2]).invoke();
            }
            long[] jArr2 = rectList2.items;
            int i3 = rectList2.itemsSize;
            int i4 = 0;
            while (i4 < jArr2.length - 2 && i4 < i3) {
                long j5 = jArr2[i4 + 2];
                boolean z4 = z;
                long[] jArr3 = jArr2;
                if ((((int) (j5 >> 61)) & 1) != 0) {
                    long j6 = jArr3[i4];
                    long j7 = jArr3[i4 + 1];
                    ThrottledCallbacks.Entry entry = (ThrottledCallbacks.Entry) throttledCallbacks3.rectChangedMap.get(((int) j5) & 67108863);
                    while (entry != null) {
                        int i5 = i3;
                        int i6 = i4;
                        long j8 = jCurrentTimeMillis - entry.lastInvokeMillis;
                        long j9 = entry.throttleMillis;
                        boolean z5 = j8 >= j9 ? z4 : false;
                        long j10 = entry.debounceMillis;
                        boolean z6 = j10 == 0 ? z4 : false;
                        boolean z7 = j9 == 0 ? z4 : false;
                        entry.topLeft = j6;
                        entry.bottomRight = j7;
                        boolean z8 = ((z6 || z7) && !z6) ? false : z4;
                        if (z5 && z8) {
                            long j11 = j6;
                            entry.lastUninvokedFireMillis = -1L;
                            entry.lastInvokeMillis = jCurrentTimeMillis;
                            long j12 = j7;
                            entry.m727fire9b9wPM(j11, j12, throttledCallbacks3.windowOffset, throttledCallbacks3.screenOffset, throttledCallbacks3.viewToWindowMatrix);
                            j3 = j11;
                            j4 = j12;
                        } else {
                            j3 = j6;
                            j4 = j7;
                            if (!z6) {
                                entry.lastUninvokedFireMillis = jCurrentTimeMillis;
                                long j13 = throttledCallbacks3.minDebounceDeadline;
                                long j14 = j10 + jCurrentTimeMillis;
                                if (j13 > 0 && j14 < j13) {
                                    throttledCallbacks3.minDebounceDeadline = j13;
                                }
                            }
                        }
                        entry = entry.next;
                        j7 = j4;
                        i3 = i5;
                        i4 = i6;
                        j6 = j3;
                    }
                }
                i4 += 3;
                i3 = i3;
                z = z4;
                jArr2 = jArr3;
            }
            long[] jArr4 = rectList2.items;
            int i7 = rectList2.itemsSize;
            for (int i8 = 0; i8 < jArr4.length - 2 && i8 < i7; i8 += 3) {
                int i9 = i8 + 2;
                jArr4[i9] = jArr4[i9] & (-2305843009213693953L);
            }
        }
        if (this.isScreenOrWindowDirty) {
            this.isScreenOrWindowDirty = false;
            long j15 = throttledCallbacks3.windowOffset;
            long j16 = throttledCallbacks3.screenOffset;
            j = jCurrentTimeMillis;
            float[] fArr = throttledCallbacks3.viewToWindowMatrix;
            MutableIntObjectMap mutableIntObjectMap = throttledCallbacks3.rectChangedMap;
            j2 = 128;
            Object[] objArr2 = mutableIntObjectMap.values;
            long[] jArr5 = mutableIntObjectMap.metadata;
            int length = jArr5.length - 2;
            if (length >= 0) {
                RectList rectList3 = rectList2;
                int i10 = 0;
                c = 7;
                while (true) {
                    long j17 = jArr5[i10];
                    ThrottledCallbacks throttledCallbacks4 = throttledCallbacks3;
                    long j18 = j15;
                    if ((((~j17) << 7) & j17 & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i11 = 8 - ((~(i10 - length)) >>> 31);
                        long j19 = j17;
                        int i12 = 0;
                        while (i12 < i11) {
                            if ((j19 & 255) < 128) {
                                ThrottledCallbacks.Entry entry2 = (ThrottledCallbacks.Entry) objArr2[(i10 << 3) + i12];
                                while (entry2 != null) {
                                    long[] jArr6 = jArr5;
                                    RectList rectList4 = rectList3;
                                    int i13 = i12;
                                    int i14 = i11;
                                    ThrottledCallbacks.Entry entry3 = entry2;
                                    ThrottledCallbacks throttledCallbacks5 = throttledCallbacks4;
                                    throttledCallbacks5.m726fireWY9HvpM(entry3, j18, j16, fArr, j);
                                    throttledCallbacks4 = throttledCallbacks5;
                                    entry2 = entry3.next;
                                    i11 = i14;
                                    i12 = i13;
                                    rectList3 = rectList4;
                                    jArr5 = jArr6;
                                }
                            }
                            long[] jArr7 = jArr5;
                            j19 >>= 8;
                            i11 = i11;
                            i12++;
                            rectList3 = rectList3;
                            throttledCallbacks4 = throttledCallbacks4;
                            jArr5 = jArr7;
                        }
                        jArr = jArr5;
                        rectList = rectList3;
                        throttledCallbacks = throttledCallbacks4;
                        if (i11 != 8) {
                            break;
                        }
                    } else {
                        jArr = jArr5;
                        rectList = rectList3;
                        throttledCallbacks = throttledCallbacks4;
                    }
                    if (i10 == length) {
                        break;
                    }
                    i10++;
                    rectList3 = rectList;
                    throttledCallbacks3 = throttledCallbacks;
                    jArr5 = jArr;
                    j15 = j18;
                }
                if (z3) {
                    long j20 = throttledCallbacks.windowOffset;
                    long j21 = throttledCallbacks.screenOffset;
                    float[] fArr2 = throttledCallbacks.viewToWindowMatrix;
                    ThrottledCallbacks.Entry entry4 = throttledCallbacks.globalChangeEntries;
                    if (entry4 != null) {
                        ThrottledCallbacks.Entry entry5 = entry4;
                        while (entry5 != null) {
                            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(entry5.node);
                            long j22 = layoutNodeRequireLayoutNode.offsetFromRoot;
                            long j23 = layoutNodeRequireLayoutNode.lastSize;
                            entry5.topLeft = j22;
                            IntOffset.Companion companion = IntOffset.Companion;
                            ThrottledCallbacks throttledCallbacks6 = throttledCallbacks;
                            entry5.bottomRight = ((((int) (j22 & 4294967295L)) + ((int) (j23 & 4294967295L))) & 4294967295L) | ((((int) (j22 >> 32)) + ((int) (j23 >> 32))) << 32);
                            throttledCallbacks6.m726fireWY9HvpM(entry5, j20, j21, fArr2, j);
                            entry5 = entry5.next;
                            throttledCallbacks = throttledCallbacks6;
                        }
                    }
                }
                throttledCallbacks2 = throttledCallbacks;
                if (this.isFragmented) {
                    this.isFragmented = false;
                    long[] jArr8 = rectList.items;
                    int i15 = rectList.itemsSize;
                    long[] jArr9 = rectList.stack;
                    int i16 = 0;
                    for (int i17 = 0; i17 < jArr8.length - 2 && i16 < jArr9.length - 2 && i17 < i15; i17 += 3) {
                        int i18 = i17 + 2;
                        if (jArr8[i18] != 2305843009213693951L) {
                            jArr9[i16] = jArr8[i17];
                            jArr9[i16 + 1] = jArr8[i17 + 1];
                            jArr9[i16 + 2] = jArr8[i18];
                            i16 += 3;
                        }
                    }
                    rectList.itemsSize = i16;
                    rectList.items = jArr9;
                    rectList.stack = jArr8;
                }
                if (throttledCallbacks2.minDebounceDeadline <= j) {
                    return;
                }
                long j24 = throttledCallbacks2.windowOffset;
                long j25 = throttledCallbacks2.screenOffset;
                float[] fArr3 = throttledCallbacks2.viewToWindowMatrix;
                MutableIntObjectMap mutableIntObjectMap2 = throttledCallbacks2.rectChangedMap;
                Object[] objArr3 = mutableIntObjectMap2.values;
                long[] jArr10 = mutableIntObjectMap2.metadata;
                int length2 = jArr10.length - 2;
                if (length2 >= 0) {
                    int i19 = 0;
                    int i20 = length2;
                    jM725debounceEntryb8qMvQI = Long.MAX_VALUE;
                    while (true) {
                        long j26 = jArr10[i19];
                        long[] jArr11 = jArr10;
                        Object[] objArr4 = objArr3;
                        if ((((~j26) << c) & j26 & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i21 = 8 - ((~(i19 - i20)) >>> 31);
                            for (int i22 = 0; i22 < i21; i22++) {
                                if ((j26 & 255) < j2) {
                                    for (ThrottledCallbacks.Entry entry6 = (ThrottledCallbacks.Entry) objArr4[(i19 << 3) + i22]; entry6 != null; entry6 = entry6.next) {
                                        long j27 = j;
                                        jM725debounceEntryb8qMvQI = ThrottledCallbacks.m725debounceEntryb8qMvQI(entry6, j24, j25, fArr3, j27, jM725debounceEntryb8qMvQI);
                                        j = j27;
                                    }
                                }
                                j26 >>= 8;
                            }
                            if (i21 != 8) {
                                break;
                            }
                        }
                        int i23 = i20;
                        if (i19 == i23) {
                            break;
                        }
                        i19++;
                        i20 = i23;
                        objArr3 = objArr4;
                        jArr10 = jArr11;
                    }
                } else {
                    jM725debounceEntryb8qMvQI = Long.MAX_VALUE;
                }
                ThrottledCallbacks.Entry entry7 = throttledCallbacks2.globalChangeEntries;
                if (entry7 != null) {
                    for (ThrottledCallbacks.Entry entry8 = entry7; entry8 != null; entry8 = entry8.next) {
                        long j28 = j;
                        jM725debounceEntryb8qMvQI = ThrottledCallbacks.m725debounceEntryb8qMvQI(entry8, j24, j25, fArr3, j28, jM725debounceEntryb8qMvQI);
                        j = j28;
                    }
                }
                throttledCallbacks2.minDebounceDeadline = jM725debounceEntryb8qMvQI == Long.MAX_VALUE ? -1L : jM725debounceEntryb8qMvQI;
                return;
            }
            rectList = rectList2;
            throttledCallbacks = throttledCallbacks3;
        } else {
            rectList = rectList2;
            throttledCallbacks = throttledCallbacks3;
            j = jCurrentTimeMillis;
            j2 = 128;
        }
        c = 7;
        if (z3) {
        }
        throttledCallbacks2 = throttledCallbacks;
        if (this.isFragmented) {
        }
        if (throttledCallbacks2.minDebounceDeadline <= j) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00e7  */
    /* renamed from: insertOrUpdateTransformedNode-70tqf50, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m721insertOrUpdateTransformedNode70tqf50(LayoutNode layoutNode, long j, boolean z) {
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
            long jFloatToRawIntBits = (Float.floatToRawIntBits((int) (j3 >> 32)) << 32) | (Float.floatToRawIntBits((int) (j3 & 4294967295L)) & 4294967295L);
            Offset.Companion companion3 = Offset.Companion;
            float fIntBitsToFloat = Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32));
            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L));
            mutableRect.left += fIntBitsToFloat;
            mutableRect.top += fIntBitsToFloat2;
            mutableRect.right += fIntBitsToFloat;
            mutableRect.bottom += fIntBitsToFloat2;
            nodeCoordinator = nodeCoordinator.wrappedBy;
            if (ownedLayer != null) {
                float[] fArrMo684getUnderlyingMatrixsQKQjiQ = ownedLayer.mo684getUnderlyingMatrixsQKQjiQ();
                if (!MatrixKt.m491isIdentity58bKbWc(fArrMo684getUnderlyingMatrixsQKQjiQ)) {
                    Matrix.m485mapimpl(fArrMo684getUnderlyingMatrixsQKQjiQ, mutableRect);
                }
            }
        }
        int i2 = (int) mutableRect.left;
        int i3 = (int) mutableRect.top;
        int i4 = (int) mutableRect.right;
        int i5 = (int) mutableRect.bottom;
        int i6 = layoutNode.semanticsId;
        if (z) {
            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
            RectList.insert$default(this.rects, i6, i2, i3, i4, i5, parent$ui_release == null ? parent$ui_release.semanticsId : -1);
        } else {
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
            LayoutNode parent$ui_release2 = layoutNode.getParent$ui_release();
            RectList.insert$default(this.rects, i6, i2, i3, i4, i5, parent$ui_release2 == null ? parent$ui_release2.semanticsId : -1);
        }
        this.isDirty = true;
    }

    public final void insertOrUpdateTransformedNodeSubhierarchy(LayoutNode layoutNode) {
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
            m721insertOrUpdateTransformedNode70tqf50(layoutNode2, layoutNode2.nodes.outerCoordinator.position, false);
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
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jMax = Math.max(j2, 16 + jCurrentTimeMillis);
            this.scheduledDispatchDeadline = jMax;
            final Function0 function0 = this.dispatchLambda;
            ?? r2 = new Runnable() { // from class: androidx.compose.ui.Actual_androidKt$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Function0 function02 = function0;
                    Handler handler3 = Actual_androidKt.handler;
                    function02.invoke();
                }
            };
            Actual_androidKt.handler.postDelayed(r2, jMax - jCurrentTimeMillis);
            this.dispatchToken = r2;
        }
    }

    public final void onLayoutLayerPositionalPropertiesChanged(LayoutNode layoutNode) {
        if (ComposeUiFlags.isRectTrackingEnabled) {
            long jM720outerToInnerOffsetBjo55l4 = m720outerToInnerOffsetBjo55l4(layoutNode);
            if (!RectManagerKt.m724access$isSetgyyYBs(jM720outerToInnerOffsetBjo55l4)) {
                insertOrUpdateTransformedNodeSubhierarchy(layoutNode);
                return;
            }
            layoutNode.outerToInnerOffset = jM720outerToInnerOffsetBjo55l4;
            layoutNode.outerToInnerOffsetDirty = false;
            MutableVector mutableVector = layoutNode.get_children$ui_release();
            Object[] objArr = mutableVector.content;
            int i = mutableVector.size;
            for (int i2 = 0; i2 < i; i2++) {
                LayoutNode layoutNode2 = (LayoutNode) objArr[i2];
                m722onLayoutPositionChanged70tqf50(layoutNode2, layoutNode2.nodes.outerCoordinator.position, false);
            }
            invalidateCallbacksFor(layoutNode);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:78:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0223  */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [int] */
    /* renamed from: onLayoutPositionChanged-70tqf50, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m722onLayoutPositionChanged70tqf50(LayoutNode layoutNode, long j, boolean z) {
        boolean z2;
        int i;
        char c;
        int i2;
        long j2;
        int i3;
        boolean z3;
        long jM853plusqkQi6aY;
        int i4;
        char c2;
        int i5;
        char c3;
        long jM856roundk4lQ0M;
        float[] fArrMo684getUnderlyingMatrixsQKQjiQ;
        int iM723access$analyzeComponents58bKbWc;
        char c4 = 3;
        boolean z4 = true;
        if (ComposeUiFlags.isRectTrackingEnabled) {
            MeasurePassDelegate measurePassDelegate = layoutNode.layoutDelegate.measurePassDelegate;
            int measuredWidth = measurePassDelegate.getMeasuredWidth();
            int measuredHeight = measurePassDelegate.getMeasuredHeight();
            LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
            long j3 = layoutNode.offsetFromRoot;
            long j4 = layoutNode.lastSize;
            int i6 = (int) (j4 >> 32);
            int i7 = (int) (j4 & 4294967295L);
            boolean z5 = false;
            if (parent$ui_release != null) {
                i3 = -1;
                boolean z6 = parent$ui_release.outerToInnerOffsetDirty;
                j2 = 4294967295L;
                long j5 = parent$ui_release.offsetFromRoot;
                char c5 = ' ';
                i2 = i6;
                long jM720outerToInnerOffsetBjo55l4 = parent$ui_release.outerToInnerOffset;
                if (RectManagerKt.m724access$isSetgyyYBs(j5)) {
                    if (z6) {
                        jM720outerToInnerOffsetBjo55l4 = m720outerToInnerOffsetBjo55l4(parent$ui_release);
                        parent$ui_release.outerToInnerOffset = jM720outerToInnerOffsetBjo55l4;
                        parent$ui_release.outerToInnerOffsetDirty = false;
                    }
                    z3 = !RectManagerKt.m724access$isSetgyyYBs(jM720outerToInnerOffsetBjo55l4);
                    jM853plusqkQi6aY = IntOffset.m853plusqkQi6aY(IntOffset.m853plusqkQi6aY(j5, jM720outerToInnerOffsetBjo55l4), j);
                    z2 = true;
                    i = measuredHeight;
                    c = c5;
                } else {
                    NodeCoordinator nodeCoordinator = layoutNode.nodes.outerCoordinator;
                    Offset.Companion.getClass();
                    long jM855plusNvtHpc = 0;
                    while (true) {
                        if (nodeCoordinator == null) {
                            z2 = z4;
                            i = measuredHeight;
                            jM856roundk4lQ0M = IntOffsetKt.m856roundk4lQ0M(jM855plusNvtHpc);
                            break;
                        }
                        OwnedLayer ownedLayer = nodeCoordinator.layer;
                        z2 = z4;
                        i = measuredHeight;
                        jM855plusNvtHpc = IntOffsetKt.m855plusNvtHpc(jM855plusNvtHpc, nodeCoordinator.position);
                        nodeCoordinator = nodeCoordinator.wrappedBy;
                        if (ownedLayer != null && (iM723access$analyzeComponents58bKbWc = RectManagerKt.m723access$analyzeComponents58bKbWc((fArrMo684getUnderlyingMatrixsQKQjiQ = ownedLayer.mo684getUnderlyingMatrixsQKQjiQ()))) != 3) {
                            if ((iM723access$analyzeComponents58bKbWc & 2) == 0) {
                                IntOffset.Companion.getClass();
                                jM856roundk4lQ0M = IntOffset.Max;
                                break;
                            }
                            jM855plusNvtHpc = Matrix.m484mapMKHz9U(jM855plusNvtHpc, fArrMo684getUnderlyingMatrixsQKQjiQ);
                        }
                        z4 = z2;
                        measuredHeight = i;
                    }
                    jM853plusqkQi6aY = jM856roundk4lQ0M;
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
                jM853plusqkQi6aY = j;
            }
            if (z3 || !RectManagerKt.m724access$isSetgyyYBs(jM853plusqkQi6aY)) {
                m721insertOrUpdateTransformedNode70tqf50(layoutNode, j, z);
                return;
            }
            layoutNode.offsetFromRoot = jM853plusqkQi6aY;
            int i8 = i;
            IntSize.Companion companion = IntSize.Companion;
            layoutNode.lastSize = (measuredWidth << c) | (i8 & j2);
            int i9 = (int) (jM853plusqkQi6aY >> c);
            int i10 = (int) (jM853plusqkQi6aY & j2);
            int i11 = i9 + measuredWidth;
            int i12 = i10 + i8;
            if (!z && IntOffset.m851equalsimpl0(jM853plusqkQi6aY, j3) && i2 == measuredWidth && i7 == i8) {
                return;
            }
            int i13 = layoutNode.semanticsId;
            if (z) {
                LayoutNode parent$ui_release2 = layoutNode.getParent$ui_release();
                RectList.insert$default(this.rects, i13, i9, i10, i11, i12, parent$ui_release2 == null ? parent$ui_release2.semanticsId : i3);
            } else {
                int i14 = i13 & 67108863;
                RectList rectList = this.rects;
                long[] jArr = rectList.items;
                int i15 = rectList.itemsSize;
                int i16 = 0;
                while (i16 < jArr.length - 2 && i16 < i15) {
                    int i17 = i16 + 2;
                    boolean z7 = z5;
                    int i18 = i15;
                    long j6 = jArr[i17];
                    char c6 = c4;
                    if ((((int) j6) & 67108863) == i14) {
                        long j7 = jArr[i16];
                        jArr[i16] = (i10 & j2) | (i9 << c);
                        jArr[i16 + 1] = (i12 & j2) | (i11 << c);
                        long j8 = 2305843009213693952L;
                        jArr[i17] = j6 | 2305843009213693952L;
                        if ((i9 - ((int) (j7 >> c)) != 0 ? z2 : z7 ? 1 : 0) | (i10 - ((int) j7) != 0 ? z2 : z7 ? 1 : 0)) {
                            long j9 = -4503599560261633L;
                            char c7 = 26;
                            long j10 = (j6 & (-4503599560261633L)) | (((i16 + 3) & 67108863) << 26);
                            long[] jArr2 = rectList.items;
                            long[] jArr3 = rectList.stack;
                            int i19 = rectList.itemsSize / 3;
                            jArr3[z7 ? 1 : 0] = j10;
                            for (?? r7 = z2; r7 > 0; r7 = i4) {
                                i4 = r7 - 1;
                                long j11 = jArr3[i4];
                                int i20 = ((int) j11) & 67108863;
                                long j12 = j9;
                                int i21 = ((int) (j11 >> c7)) & 67108863;
                                char c8 = '4';
                                char c9 = 511;
                                int i22 = ((int) (j11 >> 52)) & 511;
                                int i23 = i22 == 511 ? i19 : i22 + i21;
                                if (i21 < 0) {
                                    break;
                                }
                                while (i21 < jArr2.length - 2 && i21 < i23) {
                                    int i24 = i21 + 2;
                                    long j13 = jArr2[i24];
                                    char c10 = c8;
                                    long j14 = j8;
                                    if ((((int) (j13 >> c7)) & 67108863) == i20) {
                                        long j15 = jArr2[i21];
                                        int i25 = i21 + 1;
                                        c2 = c7;
                                        i5 = i23;
                                        long j16 = jArr2[i25];
                                        jArr2[i21] = ((((int) j15) + r5) & j2) | ((((int) (j15 >> c)) + r6) << c);
                                        jArr2[i25] = ((((int) j16) + r5) & j2) | ((((int) (j16 >> c)) + r6) << c);
                                        jArr2[i24] = j13 | j14;
                                        c3 = 511;
                                        if ((((int) (j13 >> c10)) & 511) > 0) {
                                            int i26 = (i4 == true ? 1 : 0) + 1;
                                            jArr3[i4 == true ? 1 : 0] = (j13 & j12) | (((i21 + 3) & 67108863) << c2);
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
                                    j8 = j14;
                                }
                                j9 = j12;
                                c7 = c7;
                                j8 = j8;
                            }
                        }
                    } else {
                        i16 += 3;
                        z5 = z7 ? 1 : 0;
                        i15 = i18;
                        c4 = c6;
                    }
                }
                LayoutNode parent$ui_release22 = layoutNode.getParent$ui_release();
                RectList.insert$default(this.rects, i13, i9, i10, i11, i12, parent$ui_release22 == null ? parent$ui_release22.semanticsId : i3);
            }
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
                RectManager rectManager = this.this$0;
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
