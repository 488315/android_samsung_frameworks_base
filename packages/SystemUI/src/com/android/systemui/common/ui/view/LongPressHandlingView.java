package com.android.systemui.common.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.systemui.common.ui.view.LongPressHandlingViewInteractionHandler;
import com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder$bind$1;
import com.android.systemui.shade.TouchLogger;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class LongPressHandlingView extends View {
    public final Lazy interactionHandler$delegate;
    public DeviceEntryIconViewBinder$bind$1 listener;

    public LongPressHandlingView(Context context, AttributeSet attributeSet, final Function0 function0) {
        super(context, attributeSet);
        this.interactionHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.common.ui.view.LongPressHandlingView$interactionHandler$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.common.ui.view.LongPressHandlingView$interactionHandler$2$2, reason: invalid class name */
            final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0 {
                public AnonymousClass2(Object obj) {
                    super(0, obj, LongPressHandlingView.class, "isAttachedToWindow", "isAttachedToWindow()Z", 0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(((LongPressHandlingView) this.receiver).isAttachedToWindow());
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final LongPressHandlingView longPressHandlingView = LongPressHandlingView.this;
                Function2 function2 = new Function2() { // from class: com.android.systemui.common.ui.view.LongPressHandlingView$interactionHandler$2.1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        long longValue = ((Number) obj2).longValue();
                        final Object obj3 = new Object();
                        LongPressHandlingView.this.getHandler().postDelayed((Runnable) obj, obj3, longValue);
                        final LongPressHandlingView longPressHandlingView2 = LongPressHandlingView.this;
                        return new DisposableHandle() { // from class: com.android.systemui.common.ui.view.LongPressHandlingView.interactionHandler.2.1.1
                            @Override // kotlinx.coroutines.DisposableHandle
                            public final void dispose() {
                                LongPressHandlingView.this.getHandler().removeCallbacksAndMessages(obj3);
                            }
                        };
                    }
                };
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(LongPressHandlingView.this);
                final LongPressHandlingView longPressHandlingView2 = LongPressHandlingView.this;
                Function2 function22 = new Function2() { // from class: com.android.systemui.common.ui.view.LongPressHandlingView$interactionHandler$2.3
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj).intValue();
                        ((Number) obj2).intValue();
                        LongPressHandlingView longPressHandlingView3 = LongPressHandlingView.this;
                        DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$1 = longPressHandlingView3.listener;
                        if (deviceEntryIconViewBinder$bind$1 != null) {
                            deviceEntryIconViewBinder$bind$1.onLongPressDetected(longPressHandlingView3);
                        }
                        return Unit.INSTANCE;
                    }
                };
                final LongPressHandlingView longPressHandlingView3 = LongPressHandlingView.this;
                return new LongPressHandlingViewInteractionHandler(function2, anonymousClass2, function22, new Function0() { // from class: com.android.systemui.common.ui.view.LongPressHandlingView$interactionHandler$2.4
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        DeviceEntryIconViewBinder$bind$1 deviceEntryIconViewBinder$bind$1 = LongPressHandlingView.this.listener;
                        return Unit.INSTANCE;
                    }
                }, function0);
            }
        });
    }

    @Override // android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        TouchLogger.Companion companion = TouchLogger.Companion;
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "long_press", dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        Object obj;
        DisposableHandle disposableHandle;
        DisposableHandle disposableHandle2;
        Object down;
        final LongPressHandlingViewInteractionHandler longPressHandlingViewInteractionHandler = (LongPressHandlingViewInteractionHandler) this.interactionHandler$delegate.getValue();
        boolean z = true;
        if (motionEvent != null) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                down = new LongPressHandlingViewInteractionHandler.MotionEventModel.Down((int) motionEvent.getX(), (int) motionEvent.getY());
            } else if (actionMasked == 1) {
                down = new LongPressHandlingViewInteractionHandler.MotionEventModel.Up(LongPressHandlingViewKt.distanceMoved(motionEvent), motionEvent.getEventTime() - motionEvent.getDownTime());
            } else if (actionMasked != 2) {
                obj = actionMasked != 3 ? LongPressHandlingViewInteractionHandler.MotionEventModel.Other.INSTANCE : LongPressHandlingViewInteractionHandler.MotionEventModel.Cancel.INSTANCE;
            } else {
                down = new LongPressHandlingViewInteractionHandler.MotionEventModel.Move(LongPressHandlingViewKt.distanceMoved(motionEvent));
            }
            obj = down;
        } else {
            obj = null;
        }
        if (!longPressHandlingViewInteractionHandler.isLongPressHandlingEnabled) {
            return false;
        }
        if (obj instanceof LongPressHandlingViewInteractionHandler.MotionEventModel.Down) {
            LongPressHandlingViewInteractionHandler.MotionEventModel.Down down2 = (LongPressHandlingViewInteractionHandler.MotionEventModel.Down) obj;
            final int i = down2.x;
            final int i2 = down2.y;
            longPressHandlingViewInteractionHandler.scheduledLongPressHandle = (DisposableHandle) longPressHandlingViewInteractionHandler.postDelayed.invoke(new Runnable() { // from class: com.android.systemui.common.ui.view.LongPressHandlingViewInteractionHandler$scheduleLongPress$1
                @Override // java.lang.Runnable
                public final void run() {
                    LongPressHandlingViewInteractionHandler longPressHandlingViewInteractionHandler2 = LongPressHandlingViewInteractionHandler.this;
                    int i3 = i;
                    int i4 = i2;
                    if (((Boolean) longPressHandlingViewInteractionHandler2.isAttachedToWindow.invoke()).booleanValue()) {
                        longPressHandlingViewInteractionHandler2.onLongPressDetected.invoke(Integer.valueOf(i3), Integer.valueOf(i4));
                    }
                }
            }, longPressHandlingViewInteractionHandler.longPressDuration.invoke());
        } else {
            if (obj instanceof LongPressHandlingViewInteractionHandler.MotionEventModel.Move) {
                if (((LongPressHandlingViewInteractionHandler.MotionEventModel.Move) obj).distanceMoved > ViewConfiguration.getTouchSlop() && (disposableHandle2 = longPressHandlingViewInteractionHandler.scheduledLongPressHandle) != null) {
                    disposableHandle2.dispose();
                }
            } else if (obj instanceof LongPressHandlingViewInteractionHandler.MotionEventModel.Up) {
                DisposableHandle disposableHandle3 = longPressHandlingViewInteractionHandler.scheduledLongPressHandle;
                if (disposableHandle3 != null) {
                    disposableHandle3.dispose();
                }
                LongPressHandlingViewInteractionHandler.MotionEventModel.Up up = (LongPressHandlingViewInteractionHandler.MotionEventModel.Up) obj;
                if (up.distanceMoved <= ViewConfiguration.getTouchSlop()) {
                    if (up.gestureDuration < ((Number) longPressHandlingViewInteractionHandler.longPressDuration.invoke()).longValue() && ((Boolean) longPressHandlingViewInteractionHandler.isAttachedToWindow.invoke()).booleanValue()) {
                        longPressHandlingViewInteractionHandler.onSingleTapDetected.invoke();
                    }
                }
            } else if ((obj instanceof LongPressHandlingViewInteractionHandler.MotionEventModel.Cancel) && (disposableHandle = longPressHandlingViewInteractionHandler.scheduledLongPressHandle) != null) {
                disposableHandle.dispose();
            }
            z = false;
        }
        return z;
    }

    public LongPressHandlingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, new Function0() { // from class: com.android.systemui.common.ui.view.LongPressHandlingView.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Long.valueOf(ViewConfiguration.getLongPressTimeout());
            }
        });
    }
}
