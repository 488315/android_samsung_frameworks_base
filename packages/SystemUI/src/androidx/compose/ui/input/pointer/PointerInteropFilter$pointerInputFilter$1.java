package androidx.compose.ui.input.pointer;

import android.os.SystemClock;
import android.view.MotionEvent;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInteropFilter;
import androidx.compose.ui.layout.LayoutCoordinates;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
                PointerInteropUtils_androidKt.m597toMotionEventScopeubNVwUQ(pointerEvent, layoutCoordinates.mo615localToRootMKHz9U(0L), new Function1() { // from class: androidx.compose.ui.input.pointer.PointerInteropFilter$pointerInputFilter$1$dispatchToView$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MotionEvent motionEvent = (MotionEvent) obj;
                        if (motionEvent.getActionMasked() == 0) {
                            PointerInteropFilter$pointerInputFilter$1 pointerInteropFilter$pointerInputFilter$1 = this.this$0;
                            Function1 function1 = pointerInteropFilter.onTouchEvent;
                            pointerInteropFilter$pointerInputFilter$1.state = ((Boolean) (function1 != null ? function1 : null).mo781invoke(motionEvent)).booleanValue() ? PointerInteropFilter.DispatchToViewState.Dispatching : PointerInteropFilter.DispatchToViewState.NotDispatching;
                        } else {
                            Function1 function12 = pointerInteropFilter.onTouchEvent;
                            (function12 != null ? function12 : null).mo781invoke(motionEvent);
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
                    PointerInteropUtils_androidKt.m597toMotionEventScopeubNVwUQ(pointerEvent, layoutCoordinates2.mo615localToRootMKHz9U(0L), new Function1() { // from class: androidx.compose.ui.input.pointer.PointerInteropFilter$pointerInputFilter$1$dispatchToView$2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            MotionEvent motionEvent = (MotionEvent) obj;
                            Function1 function1 = pointerInteropFilter.onTouchEvent;
                            if (function1 == null) {
                                function1 = null;
                            }
                            function1.mo781invoke(motionEvent);
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
            long jUptimeMillis = SystemClock.uptimeMillis();
            final PointerInteropFilter pointerInteropFilter = this.this$0;
            Function1 function1 = new Function1() { // from class: androidx.compose.ui.input.pointer.PointerInteropFilter$pointerInputFilter$1$onCancel$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    MotionEvent motionEvent = (MotionEvent) obj;
                    Function1 function12 = pointerInteropFilter.onTouchEvent;
                    if (function12 == null) {
                        function12 = null;
                    }
                    function12.mo781invoke(motionEvent);
                    return Unit.INSTANCE;
                }
            };
            MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
            motionEventObtain.setSource(0);
            function1.mo781invoke(motionEventObtain);
            motionEventObtain.recycle();
            this.state = PointerInteropFilter.DispatchToViewState.Unknown;
            pointerInteropFilter.disallowIntercept = false;
        }
    }

    /* renamed from: onPointerEvent-H0pRuoY, reason: not valid java name */
    public final void m596onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass) {
        boolean z;
        List list = pointerEvent.changes;
        PointerInteropFilter pointerInteropFilter = this.this$0;
        if (pointerInteropFilter.disallowIntercept) {
            z = true;
            break;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            PointerInputChange pointerInputChange = (PointerInputChange) list.get(i);
            if (PointerEventKt.changedToDownIgnoreConsumed(pointerInputChange) || PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
                z = true;
                break;
            }
        }
        z = false;
        if (this.state != PointerInteropFilter.DispatchToViewState.NotDispatching) {
            if (pointerEventPass == PointerEventPass.Initial && z) {
                dispatchToView(pointerEvent);
            }
            if (pointerEventPass == PointerEventPass.Final && !z) {
                dispatchToView(pointerEvent);
            }
        }
        if (pointerEventPass == PointerEventPass.Final) {
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                if (!PointerEventKt.changedToUpIgnoreConsumed((PointerInputChange) list.get(i2))) {
                    return;
                }
            }
            this.state = PointerInteropFilter.DispatchToViewState.Unknown;
            pointerInteropFilter.disallowIntercept = false;
        }
    }
}
