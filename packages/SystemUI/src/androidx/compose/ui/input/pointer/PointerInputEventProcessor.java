package androidx.compose.ui.input.pointer;

import androidx.collection.LongSparseArray;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.node.HitTestResult;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.platform.AndroidComposeView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerInputEventProcessor {
    public final HitPathTracker hitPathTracker;
    public boolean isProcessing;
    public final LayoutNode root;
    public final PointerInputChangeEventProducer pointerInputChangeEventProducer = new PointerInputChangeEventProducer();
    public final HitTestResult hitResult = new HitTestResult();

    public PointerInputEventProcessor(LayoutNode layoutNode) {
        this.root = layoutNode;
        this.hitPathTracker = new HitPathTracker(layoutNode.nodes.innerCoordinator);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: process-BIzXfog, reason: not valid java name */
    public final int m593processBIzXfog(PointerInputEvent pointerInputEvent, AndroidComposeView androidComposeView, boolean z) {
        byte b;
        HitPathTracker hitPathTracker;
        int i;
        HitTestResult hitTestResult = this.hitResult;
        if (this.isProcessing) {
            return 0;
        }
        try {
            this.isProcessing = true;
            InternalPointerEvent produce = this.pointerInputChangeEventProducer.produce(pointerInputEvent, androidComposeView);
            LongSparseArray longSparseArray = produce.changes;
            int size = longSparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                PointerInputChange pointerInputChange = (PointerInputChange) longSparseArray.valueAt(i2);
                if (!pointerInputChange.pressed && !pointerInputChange.previousPressed) {
                }
                b = false;
                break;
            }
            b = true;
            int size2 = longSparseArray.size();
            int i3 = 0;
            while (true) {
                hitPathTracker = this.hitPathTracker;
                if (i3 >= size2) {
                    break;
                }
                PointerInputChange pointerInputChange2 = (PointerInputChange) longSparseArray.valueAt(i3);
                if (b != false || PointerEventKt.changedToDownIgnoreConsumed(pointerInputChange2)) {
                    LayoutNode layoutNode = this.root;
                    long j = pointerInputChange2.position;
                    HitTestResult hitTestResult2 = this.hitResult;
                    int i4 = pointerInputChange2.type;
                    LayoutNode.Companion companion = LayoutNode.Companion;
                    layoutNode.m640hitTest6fMxITs$ui_release(j, hitTestResult2, i4, true);
                    if (!hitTestResult.values.isEmpty()) {
                        hitPathTracker.m586addHitPathQJqDSyo(pointerInputChange2.id, hitTestResult, PointerEventKt.changedToDownIgnoreConsumed(pointerInputChange2));
                        hitTestResult.clear();
                    }
                }
                i3++;
            }
            boolean dispatchChanges = hitPathTracker.dispatchChanges(produce, z);
            if (!produce.suppressMovementConsumption) {
                int size3 = longSparseArray.size();
                for (int i5 = 0; i5 < size3; i5++) {
                    PointerInputChange pointerInputChange3 = (PointerInputChange) longSparseArray.valueAt(i5);
                    long positionChangeInternal = PointerEventKt.positionChangeInternal(pointerInputChange3, true);
                    Offset.Companion.getClass();
                    if (!Offset.m396equalsimpl0(positionChangeInternal, 0L) && pointerInputChange3.isConsumed()) {
                        i = 1;
                        break;
                    }
                }
            }
            i = 0;
            int i6 = (i << 1) | (dispatchChanges ? 1 : 0);
            this.isProcessing = false;
            return i6;
        } catch (Throwable th) {
            this.isProcessing = false;
            throw th;
        }
    }

    public final void processCancel() {
        if (this.isProcessing) {
            return;
        }
        this.pointerInputChangeEventProducer.previousPointerInputData.clear();
        HitPathTracker hitPathTracker = this.hitPathTracker;
        MutableVector mutableVector = hitPathTracker.root.children;
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((Node) objArr[i2]).dispatchCancel();
        }
        hitPathTracker.root.children.clear();
    }
}
