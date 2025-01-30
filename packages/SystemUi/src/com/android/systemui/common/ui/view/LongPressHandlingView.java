package com.android.systemui.common.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.common.ui.view.LongPressHandlingViewInteractionHandler;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LongPressHandlingView extends View {
    public final Lazy interactionHandler$delegate;

    public LongPressHandlingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.interactionHandler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.common.ui.view.LongPressHandlingView$interactionHandler$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.common.ui.view.LongPressHandlingView$interactionHandler$2$2 */
            final /* synthetic */ class C11602 extends FunctionReferenceImpl implements Function0 {
                public C11602(Object obj) {
                    super(0, obj, LongPressHandlingView.class, "isAttachedToWindow", "isAttachedToWindow()Z", 0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(((LongPressHandlingView) this.receiver).isAttachedToWindow());
                }
            }

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
                C11602 c11602 = new C11602(LongPressHandlingView.this);
                final LongPressHandlingView longPressHandlingView2 = LongPressHandlingView.this;
                Function2 function22 = new Function2() { // from class: com.android.systemui.common.ui.view.LongPressHandlingView$interactionHandler$2.3
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj).intValue();
                        ((Number) obj2).intValue();
                        LongPressHandlingView.this.getClass();
                        return Unit.INSTANCE;
                    }
                };
                final LongPressHandlingView longPressHandlingView3 = LongPressHandlingView.this;
                return new LongPressHandlingViewInteractionHandler(function2, c11602, function22, new Function0() { // from class: com.android.systemui.common.ui.view.LongPressHandlingView$interactionHandler$2.4
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        LongPressHandlingView.this.getClass();
                        return Unit.INSTANCE;
                    }
                });
            }
        });
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        LongPressHandlingViewInteractionHandler longPressHandlingViewInteractionHandler = (LongPressHandlingViewInteractionHandler) this.interactionHandler$delegate.getValue();
        if (motionEvent != null) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                new LongPressHandlingViewInteractionHandler.MotionEventModel.Down((int) motionEvent.getX(), (int) motionEvent.getY());
            } else if (actionMasked == 1) {
                new LongPressHandlingViewInteractionHandler.MotionEventModel.C1163Up(LongPressHandlingViewKt.distanceMoved(motionEvent), motionEvent.getEventTime() - motionEvent.getDownTime());
            } else if (actionMasked == 2) {
                new LongPressHandlingViewInteractionHandler.MotionEventModel.Move(LongPressHandlingViewKt.distanceMoved(motionEvent));
            } else if (actionMasked != 3) {
                int i = LongPressHandlingViewInteractionHandler.MotionEventModel.Other.$r8$clinit;
            } else {
                int i2 = LongPressHandlingViewInteractionHandler.MotionEventModel.Cancel.$r8$clinit;
            }
        }
        longPressHandlingViewInteractionHandler.getClass();
        return false;
    }
}
