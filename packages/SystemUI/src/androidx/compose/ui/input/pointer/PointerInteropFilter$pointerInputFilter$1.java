package androidx.compose.ui.input.pointer;

import android.os.SystemClock;
import android.view.MotionEvent;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInteropFilter;
import androidx.compose.ui.layout.LayoutCoordinates;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerInteropFilter$pointerInputFilter$1 extends PointerInputFilter {
    public PointerInteropFilter.DispatchToViewState state = PointerInteropFilter.DispatchToViewState.Unknown;
    public final /* synthetic */ PointerInteropFilter this$0;

    public PointerInteropFilter$pointerInputFilter$1(PointerInteropFilter pointerInteropFilter) {
        this.this$0 = pointerInteropFilter;
    }

    public final void dispatchToView(PointerEvent pointerEvent) {
        List list = pointerEvent.changes;
        List list2 = list;
        int size = list2.size();
        int i = 0;
        while (true) {
            final PointerInteropFilter pointerInteropFilter = this.this$0;
            if (i >= size) {
                LayoutCoordinates layoutCoordinates = this.layoutCoordinates;
                if (layoutCoordinates == null) {
                    throw new IllegalStateException("layoutCoordinates not set");
                }
                Offset.Companion.getClass();
                PointerInteropUtils_androidKt.m595toMotionEventScopeubNVwUQ(pointerEvent, layoutCoordinates.mo613localToRootMKHz9U(0L), new Function1() { // from class: androidx.compose.ui.input.pointer.PointerInteropFilter$pointerInputFilter$1$dispatchToView$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        MotionEvent motionEvent = (MotionEvent) obj;
                        if (motionEvent.getActionMasked() == 0) {
                            PointerInteropFilter$pointerInputFilter$1 pointerInteropFilter$pointerInputFilter$1 = PointerInteropFilter$pointerInputFilter$1.this;
                            Function1 function1 = pointerInteropFilter.onTouchEvent;
                            pointerInteropFilter$pointerInputFilter$1.state = ((Boolean) (function1 != null ? function1 : null).mo779invoke(motionEvent)).booleanValue() ? PointerInteropFilter.DispatchToViewState.Dispatching : PointerInteropFilter.DispatchToViewState.NotDispatching;
                        } else {
                            Function1 function12 = pointerInteropFilter.onTouchEvent;
                            (function12 != null ? function12 : null).mo779invoke(motionEvent);
                        }
                        return Unit.INSTANCE;
                    }
                }, false);
                if (this.state == PointerInteropFilter.DispatchToViewState.Dispatching) {
                    int size2 = list2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((PointerInputChange) list.get(i2)).consume();
                    }
                    InternalPointerEvent internalPointerEvent = pointerEvent.internalPointerEvent;
                    if (internalPointerEvent == null) {
                        return;
                    }
                    internalPointerEvent.suppressMovementConsumption = !pointerInteropFilter.disallowIntercept;
                    return;
                }
                return;
            }
            if (((PointerInputChange) list.get(i)).isConsumed()) {
                if (this.state == PointerInteropFilter.DispatchToViewState.Dispatching) {
                    LayoutCoordinates layoutCoordinates2 = this.layoutCoordinates;
                    if (layoutCoordinates2 == null) {
                        throw new IllegalStateException("layoutCoordinates not set");
                    }
                    Offset.Companion.getClass();
                    PointerInteropUtils_androidKt.m595toMotionEventScopeubNVwUQ(pointerEvent, layoutCoordinates2.mo613localToRootMKHz9U(0L), new Function1() { // from class: androidx.compose.ui.input.pointer.PointerInteropFilter$pointerInputFilter$1$dispatchToView$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            MotionEvent motionEvent = (MotionEvent) obj;
                            Function1 function1 = PointerInteropFilter.this.onTouchEvent;
                            if (function1 == null) {
                                function1 = null;
                            }
                            function1.mo779invoke(motionEvent);
                            return Unit.INSTANCE;
                        }
                    }, true);
                }
                this.state = PointerInteropFilter.DispatchToViewState.NotDispatching;
                return;
            }
            i++;
        }
    }

    public final void onCancel() {
        if (this.state == PointerInteropFilter.DispatchToViewState.Dispatching) {
            long uptimeMillis = SystemClock.uptimeMillis();
            final PointerInteropFilter pointerInteropFilter = this.this$0;
            Function1 function1 = new Function1() { // from class: androidx.compose.ui.input.pointer.PointerInteropFilter$pointerInputFilter$1$onCancel$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    MotionEvent motionEvent = (MotionEvent) obj;
                    Function1 function12 = PointerInteropFilter.this.onTouchEvent;
                    if (function12 == null) {
                        function12 = null;
                    }
                    function12.mo779invoke(motionEvent);
                    return Unit.INSTANCE;
                }
            };
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
            obtain.setSource(0);
            function1.mo779invoke(obtain);
            obtain.recycle();
            this.state = PointerInteropFilter.DispatchToViewState.Unknown;
            pointerInteropFilter.disallowIntercept = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* renamed from: onPointerEvent-H0pRuoY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m594onPointerEventH0pRuoY(androidx.compose.ui.input.pointer.PointerEvent r8, androidx.compose.ui.input.pointer.PointerEventPass r9) {
        /*
            r7 = this;
            java.util.List r0 = r8.changes
            androidx.compose.ui.input.pointer.PointerInteropFilter r1 = r7.this$0
            boolean r2 = r1.disallowIntercept
            r3 = 0
            if (r2 != 0) goto L2b
            r2 = r0
            java.util.Collection r2 = (java.util.Collection) r2
            int r2 = r2.size()
            r4 = r3
        L11:
            if (r4 >= r2) goto L29
            java.lang.Object r5 = r0.get(r4)
            androidx.compose.ui.input.pointer.PointerInputChange r5 = (androidx.compose.ui.input.pointer.PointerInputChange) r5
            boolean r6 = androidx.compose.ui.input.pointer.PointerEventKt.changedToDownIgnoreConsumed(r5)
            if (r6 != 0) goto L2b
            boolean r5 = androidx.compose.ui.input.pointer.PointerEventKt.changedToUpIgnoreConsumed(r5)
            if (r5 == 0) goto L26
            goto L2b
        L26:
            int r4 = r4 + 1
            goto L11
        L29:
            r2 = r3
            goto L2c
        L2b:
            r2 = 1
        L2c:
            androidx.compose.ui.input.pointer.PointerInteropFilter$DispatchToViewState r4 = r7.state
            androidx.compose.ui.input.pointer.PointerInteropFilter$DispatchToViewState r5 = androidx.compose.ui.input.pointer.PointerInteropFilter.DispatchToViewState.NotDispatching
            if (r4 == r5) goto L44
            androidx.compose.ui.input.pointer.PointerEventPass r4 = androidx.compose.ui.input.pointer.PointerEventPass.Initial
            if (r9 != r4) goto L3b
            if (r2 == 0) goto L3b
            r7.dispatchToView(r8)
        L3b:
            androidx.compose.ui.input.pointer.PointerEventPass r4 = androidx.compose.ui.input.pointer.PointerEventPass.Final
            if (r9 != r4) goto L44
            if (r2 != 0) goto L44
            r7.dispatchToView(r8)
        L44:
            androidx.compose.ui.input.pointer.PointerEventPass r8 = androidx.compose.ui.input.pointer.PointerEventPass.Final
            if (r9 != r8) goto L68
            r8 = r0
            java.util.Collection r8 = (java.util.Collection) r8
            int r8 = r8.size()
            r9 = r3
        L50:
            if (r9 >= r8) goto L62
            java.lang.Object r2 = r0.get(r9)
            androidx.compose.ui.input.pointer.PointerInputChange r2 = (androidx.compose.ui.input.pointer.PointerInputChange) r2
            boolean r2 = androidx.compose.ui.input.pointer.PointerEventKt.changedToUpIgnoreConsumed(r2)
            if (r2 != 0) goto L5f
            goto L68
        L5f:
            int r9 = r9 + 1
            goto L50
        L62:
            androidx.compose.ui.input.pointer.PointerInteropFilter$DispatchToViewState r8 = androidx.compose.ui.input.pointer.PointerInteropFilter.DispatchToViewState.Unknown
            r7.state = r8
            r1.disallowIntercept = r3
        L68:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.PointerInteropFilter$pointerInputFilter$1.m594onPointerEventH0pRuoY(androidx.compose.ui.input.pointer.PointerEvent, androidx.compose.ui.input.pointer.PointerEventPass):void");
    }
}
