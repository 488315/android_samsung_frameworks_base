package com.android.systemui.ambient.touch;

import android.app.DreamManager;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.Flags;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeTouchHandler implements TouchHandler {
    public Boolean mCapture;
    public final int mInitiationHeight;
    public final ShadeViewController mShadeViewController;
    public final Optional mSurfaces;

    public ShadeTouchHandler(Optional<CentralSurfaces> optional, ShadeViewController shadeViewController, DreamManager dreamManager, int i) {
        this.mSurfaces = optional;
        this.mShadeViewController = shadeViewController;
        this.mInitiationHeight = i;
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void getTouchInitiationRegion(Rect rect, Region region, Rect rect2) {
        Rect rect3 = new Rect(rect);
        rect3.inset(0, 0, 0, rect3.height() - this.mInitiationHeight);
        region.op(rect3, Region.Op.UNION);
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void onSessionStart(final TouchMonitor.TouchSessionImpl touchSessionImpl) {
        if (this.mSurfaces.isEmpty()) {
            touchSessionImpl.pop();
            return;
        }
        touchSessionImpl.mCallbacks.add(new TouchHandler$TouchSession$Callback() { // from class: com.android.systemui.ambient.touch.ShadeTouchHandler$$ExternalSyntheticLambda0
            @Override // com.android.systemui.ambient.touch.TouchHandler$TouchSession$Callback
            public final void onRemoved() {
                ShadeTouchHandler.this.mCapture = null;
            }
        });
        touchSessionImpl.mEventListeners.add(new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.ambient.touch.ShadeTouchHandler$$ExternalSyntheticLambda1
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                ShadeTouchHandler shadeTouchHandler = ShadeTouchHandler.this;
                shadeTouchHandler.getClass();
                if (inputEvent instanceof MotionEvent) {
                    Boolean bool = shadeTouchHandler.mCapture;
                    if (bool != null && bool.booleanValue()) {
                        Flags.communalHub();
                        shadeTouchHandler.mShadeViewController.handleExternalTouch((MotionEvent) inputEvent);
                    }
                    if (((MotionEvent) inputEvent).getAction() == 1) {
                        touchSessionImpl.pop();
                    }
                }
            }
        });
        touchSessionImpl.mGestureListeners.add(new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.ambient.touch.ShadeTouchHandler.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                return ShadeTouchHandler.this.mCapture.booleanValue();
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                ShadeTouchHandler shadeTouchHandler = ShadeTouchHandler.this;
                if (shadeTouchHandler.mCapture == null) {
                    shadeTouchHandler.mCapture = Boolean.valueOf(Math.abs(f2) > Math.abs(f) && f2 < 0.0f);
                    if (ShadeTouchHandler.this.mCapture.booleanValue()) {
                        ShadeTouchHandler shadeTouchHandler2 = ShadeTouchHandler.this;
                        shadeTouchHandler2.getClass();
                        Flags.communalHub();
                        shadeTouchHandler2.mShadeViewController.handleExternalTouch(motionEvent);
                        ShadeTouchHandler shadeTouchHandler3 = ShadeTouchHandler.this;
                        shadeTouchHandler3.getClass();
                        Flags.communalHub();
                        shadeTouchHandler3.mShadeViewController.handleExternalTouch(motionEvent2);
                    }
                }
                return ShadeTouchHandler.this.mCapture.booleanValue();
            }
        });
    }
}
