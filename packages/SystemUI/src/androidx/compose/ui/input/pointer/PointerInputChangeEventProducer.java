package androidx.compose.ui.input.pointer;

import androidx.collection.LongSparseArray;
import androidx.compose.ui.platform.AndroidComposeView;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PointerInputChangeEventProducer {
    public final LongSparseArray previousPointerInputData = new LongSparseArray(0, 1, null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class PointerInputData {
        public final boolean down;
        public final long positionOnScreen;
        public final long uptime;

        public /* synthetic */ PointerInputData(long j, long j2, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, j2, z);
        }

        private PointerInputData(long j, long j2, boolean z) {
            this.uptime = j;
            this.positionOnScreen = j2;
            this.down = z;
        }
    }

    public final InternalPointerEvent produce(PointerInputEvent pointerInputEvent, AndroidComposeView androidComposeView) {
        long m696screenToLocalMKHz9U;
        boolean z;
        long j;
        LongSparseArray longSparseArray = new LongSparseArray(pointerInputEvent.pointers.size());
        List list = pointerInputEvent.pointers;
        int size = list.size();
        int i = 0;
        while (i < size) {
            PointerInputEventData pointerInputEventData = (PointerInputEventData) list.get(i);
            long j2 = pointerInputEventData.id;
            LongSparseArray longSparseArray2 = this.previousPointerInputData;
            PointerInputData pointerInputData = (PointerInputData) longSparseArray2.get(j2);
            if (pointerInputData == null) {
                long j3 = pointerInputEventData.uptime;
                m696screenToLocalMKHz9U = pointerInputEventData.position;
                j = j3;
                z = false;
            } else {
                m696screenToLocalMKHz9U = androidComposeView.m696screenToLocalMKHz9U(pointerInputData.positionOnScreen);
                long j4 = pointerInputData.uptime;
                z = pointerInputData.down;
                j = j4;
            }
            long j5 = m696screenToLocalMKHz9U;
            List list2 = pointerInputEventData.historical;
            long j6 = pointerInputEventData.scrollDelta;
            long j7 = pointerInputEventData.originalEventPosition;
            int i2 = i;
            long j8 = pointerInputEventData.id;
            List list3 = list;
            int i3 = size;
            longSparseArray.put(j8, new PointerInputChange(j8, pointerInputEventData.uptime, pointerInputEventData.position, pointerInputEventData.down, pointerInputEventData.pressure, j, j5, z, false, pointerInputEventData.type, list2, j6, j7, null));
            long j9 = pointerInputEventData.id;
            boolean z2 = pointerInputEventData.down;
            if (z2) {
                longSparseArray2.put(j9, new PointerInputData(pointerInputEventData.uptime, pointerInputEventData.positionOnScreen, z2, null));
            } else {
                longSparseArray2.remove(j9);
            }
            i = i2 + 1;
            list = list3;
            size = i3;
        }
        return new InternalPointerEvent(longSparseArray, pointerInputEvent);
    }
}
