package androidx.compose.ui.input.pointer;

import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import android.view.MotionEvent;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.platform.AndroidComposeView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class MotionEventAdapter {
    public long nextId;
    public final SparseLongArray motionEventToComposePointerIdMap = new SparseLongArray();
    public final SparseBooleanArray activeHoverIds = new SparseBooleanArray();
    public final List pointers = new ArrayList();
    public int previousToolType = -1;
    public int previousSource = -1;

    /* JADX WARN: Removed duplicated region for block: B:19:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x023d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final PointerInputEvent convertToPointerInputEvent$ui_release(AndroidComposeView androidComposeView, MotionEvent motionEvent) {
        long j;
        int i;
        int i2;
        boolean z;
        long jValueAt;
        float f;
        long jFloatToRawIntBits;
        long jM698screenToLocalMKHz9U;
        int i3;
        int historySize;
        int i4;
        char c;
        long jFloatToRawIntBits2;
        char c2;
        int i5;
        int i6;
        int actionMasked = motionEvent.getActionMasked();
        int i7 = 3;
        if (actionMasked != 3) {
            int i8 = 4;
            if (actionMasked != 4) {
                if (motionEvent.getPointerCount() == 1) {
                    int toolType = motionEvent.getToolType(0);
                    int source = motionEvent.getSource();
                    if (toolType != this.previousToolType || source != this.previousSource) {
                        this.previousToolType = toolType;
                        this.previousSource = source;
                        this.activeHoverIds.clear();
                        this.motionEventToComposePointerIdMap.clear();
                    }
                }
                int actionMasked2 = motionEvent.getActionMasked();
                if (actionMasked2 == 0 || actionMasked2 == 5) {
                    j = 1;
                    int actionIndex = motionEvent.getActionIndex();
                    int pointerId = motionEvent.getPointerId(actionIndex);
                    if (this.motionEventToComposePointerIdMap.indexOfKey(pointerId) < 0) {
                        SparseLongArray sparseLongArray = this.motionEventToComposePointerIdMap;
                        long j2 = this.nextId;
                        this.nextId = j2 + 1;
                        sparseLongArray.put(pointerId, j2);
                        if (motionEvent.getToolType(actionIndex) == 3) {
                            this.activeHoverIds.put(pointerId, true);
                        }
                    }
                } else if (actionMasked2 != 9) {
                    j = 1;
                } else {
                    int pointerId2 = motionEvent.getPointerId(0);
                    if (this.motionEventToComposePointerIdMap.indexOfKey(pointerId2) < 0) {
                        SparseLongArray sparseLongArray2 = this.motionEventToComposePointerIdMap;
                        long j3 = this.nextId;
                        j = 1;
                        this.nextId = j3 + 1;
                        sparseLongArray2.put(pointerId2, j3);
                    }
                }
                boolean z2 = actionMasked == 9 || actionMasked == 7 || actionMasked == 10;
                boolean z3 = actionMasked == 8;
                if (z2) {
                    i = 1;
                    this.activeHoverIds.put(motionEvent.getPointerId(motionEvent.getActionIndex()), true);
                } else {
                    i = 1;
                }
                int actionIndex2 = actionMasked != i ? actionMasked != 6 ? -1 : motionEvent.getActionIndex() : 0;
                ((ArrayList) this.pointers).clear();
                int pointerCount = motionEvent.getPointerCount();
                int i9 = 0;
                while (i9 < pointerCount) {
                    long j4 = j;
                    List list = this.pointers;
                    boolean z4 = (z2 || i9 == actionIndex2 || (z3 && motionEvent.getButtonState() == 0)) ? false : true;
                    int pointerId3 = motionEvent.getPointerId(i9);
                    int iIndexOfKey = this.motionEventToComposePointerIdMap.indexOfKey(pointerId3);
                    if (iIndexOfKey >= 0) {
                        z = z2;
                        jValueAt = this.motionEventToComposePointerIdMap.valueAt(iIndexOfKey);
                    } else {
                        long j5 = this.nextId;
                        z = z2;
                        this.nextId = j5 + j4;
                        this.motionEventToComposePointerIdMap.put(pointerId3, j5);
                        jValueAt = j5;
                    }
                    float pressure = motionEvent.getPressure(i9);
                    char c3 = ' ';
                    long jM396copydBAh8RU$default = Offset.m396copydBAh8RU$default((Float.floatToRawIntBits(motionEvent.getY(i9)) & 4294967295L) | (Float.floatToRawIntBits(motionEvent.getX(i9)) << 32), 0.0f, i7);
                    if (i9 == 0) {
                        f = 0.0f;
                        jFloatToRawIntBits = (Float.floatToRawIntBits(motionEvent.getRawY()) & 4294967295L) | (Float.floatToRawIntBits(motionEvent.getRawX()) << 32);
                        jM698screenToLocalMKHz9U = androidComposeView.m698screenToLocalMKHz9U(jFloatToRawIntBits);
                    } else {
                        f = 0.0f;
                        MotionEventHelper.INSTANCE.getClass();
                        jFloatToRawIntBits = (Float.floatToRawIntBits(motionEvent.getRawY(i9)) & 4294967295L) | (Float.floatToRawIntBits(motionEvent.getRawX(i9)) << 32);
                        jM698screenToLocalMKHz9U = androidComposeView.m698screenToLocalMKHz9U(jFloatToRawIntBits);
                    }
                    long j6 = jFloatToRawIntBits;
                    long j7 = jM698screenToLocalMKHz9U;
                    int toolType2 = motionEvent.getToolType(i9);
                    if (toolType2 != 0) {
                        if (toolType2 == 1) {
                            PointerType.Companion.getClass();
                            i6 = PointerType.Touch;
                        } else if (toolType2 == 2) {
                            PointerType.Companion.getClass();
                            i6 = PointerType.Stylus;
                        } else if (toolType2 == i7) {
                            PointerType.Companion.getClass();
                            i6 = PointerType.Mouse;
                        } else if (toolType2 != i8) {
                            PointerType.Companion.getClass();
                        } else {
                            PointerType.Companion.getClass();
                            i6 = PointerType.Eraser;
                        }
                        i3 = i6;
                        ArrayList arrayList = new ArrayList(motionEvent.getHistorySize());
                        historySize = motionEvent.getHistorySize();
                        i4 = 0;
                        while (i4 < historySize) {
                            float historicalX = motionEvent.getHistoricalX(i9, i4);
                            float historicalY = motionEvent.getHistoricalY(i9, i4);
                            if ((Float.floatToRawIntBits(historicalX) & Integer.MAX_VALUE) >= 2139095040 || (Float.floatToRawIntBits(historicalY) & Integer.MAX_VALUE) >= 2139095040) {
                                c2 = c3;
                                i5 = pointerCount;
                            } else {
                                long jFloatToRawIntBits3 = Float.floatToRawIntBits(historicalX);
                                int iFloatToRawIntBits = Float.floatToRawIntBits(historicalY);
                                c2 = c3;
                                i5 = pointerCount;
                                long j8 = (jFloatToRawIntBits3 << c2) | (iFloatToRawIntBits & 4294967295L);
                                arrayList.add(new HistoricalChange(motionEvent.getHistoricalEventTime(i4), j8, j8, null));
                            }
                            i4++;
                            c3 = c2;
                            pointerCount = i5;
                        }
                        char c4 = c3;
                        int i10 = pointerCount;
                        if (motionEvent.getActionMasked() != 8) {
                            c = '\t';
                            jFloatToRawIntBits2 = (Float.floatToRawIntBits((-motionEvent.getAxisValue(9)) + f) & 4294967295L) | (Float.floatToRawIntBits(motionEvent.getAxisValue(10)) << c4);
                        } else {
                            c = '\t';
                            Offset.Companion.getClass();
                            jFloatToRawIntBits2 = 0;
                        }
                        ((ArrayList) list).add(new PointerInputEventData(jValueAt, motionEvent.getEventTime(), j6, j7, z4, pressure, i3, this.activeHoverIds.get(motionEvent.getPointerId(i9), false), arrayList, jFloatToRawIntBits2, jM396copydBAh8RU$default, null));
                        i9++;
                        j = j4;
                        z2 = z;
                        pointerCount = i10;
                        i7 = 3;
                        i8 = 4;
                    } else {
                        PointerType.Companion.getClass();
                    }
                    i3 = 0;
                    ArrayList arrayList2 = new ArrayList(motionEvent.getHistorySize());
                    historySize = motionEvent.getHistorySize();
                    i4 = 0;
                    while (i4 < historySize) {
                    }
                    char c42 = c3;
                    int i102 = pointerCount;
                    if (motionEvent.getActionMasked() != 8) {
                    }
                    ((ArrayList) list).add(new PointerInputEventData(jValueAt, motionEvent.getEventTime(), j6, j7, z4, pressure, i3, this.activeHoverIds.get(motionEvent.getPointerId(i9), false), arrayList2, jFloatToRawIntBits2, jM396copydBAh8RU$default, null));
                    i9++;
                    j = j4;
                    z2 = z;
                    pointerCount = i102;
                    i7 = 3;
                    i8 = 4;
                }
                int actionMasked3 = motionEvent.getActionMasked();
                if (actionMasked3 == 1 || actionMasked3 == 6) {
                    int pointerId4 = motionEvent.getPointerId(motionEvent.getActionIndex());
                    i2 = 0;
                    if (!this.activeHoverIds.get(pointerId4, false)) {
                        this.motionEventToComposePointerIdMap.delete(pointerId4);
                        this.activeHoverIds.delete(pointerId4);
                    }
                } else {
                    i2 = 0;
                }
                if (this.motionEventToComposePointerIdMap.size() > motionEvent.getPointerCount()) {
                    for (int size = this.motionEventToComposePointerIdMap.size() - 1; -1 < size; size--) {
                        int iKeyAt = this.motionEventToComposePointerIdMap.keyAt(size);
                        int pointerCount2 = motionEvent.getPointerCount();
                        int i11 = i2;
                        while (true) {
                            if (i11 >= pointerCount2) {
                                this.motionEventToComposePointerIdMap.removeAt(size);
                                this.activeHoverIds.delete(iKeyAt);
                                break;
                            }
                            if (motionEvent.getPointerId(i11) == iKeyAt) {
                                break;
                            }
                            i11++;
                        }
                    }
                }
                return new PointerInputEvent(motionEvent.getEventTime(), this.pointers, motionEvent);
            }
        }
        this.motionEventToComposePointerIdMap.clear();
        this.activeHoverIds.clear();
        return null;
    }
}
