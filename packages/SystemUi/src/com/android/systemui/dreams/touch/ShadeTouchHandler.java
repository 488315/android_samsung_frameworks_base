package com.android.systemui.dreams.touch;

import android.graphics.Rect;
import android.graphics.Region;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ShadeTouchHandler implements DreamTouchHandler {
    public final int mInitiationHeight;
    public final Optional mSurfaces;

    public ShadeTouchHandler(Optional<CentralSurfaces> optional, int i) {
        this.mSurfaces = optional;
        this.mInitiationHeight = i;
    }

    @Override // com.android.systemui.dreams.touch.DreamTouchHandler
    public final void getTouchInitiationRegion(Rect rect, Region region) {
        Rect rect2 = new Rect(rect);
        rect2.inset(0, 0, 0, rect2.height() - this.mInitiationHeight);
        region.op(rect2, Region.Op.UNION);
    }

    @Override // com.android.systemui.dreams.touch.DreamTouchHandler
    public final void onSessionStart(final DreamTouchHandler.TouchSession touchSession) {
        if (((Boolean) this.mSurfaces.map(new ShadeTouchHandler$$ExternalSyntheticLambda0(0)).orElse(Boolean.FALSE)).booleanValue()) {
            ((DreamOverlayTouchMonitor.TouchSessionImpl) touchSession).pop();
            return;
        }
        InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener = new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.dreams.touch.ShadeTouchHandler$$ExternalSyntheticLambda1
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                ShadeTouchHandler shadeTouchHandler = ShadeTouchHandler.this;
                shadeTouchHandler.getClass();
                ShadeViewController shadeViewController = (ShadeViewController) shadeTouchHandler.mSurfaces.map(new ShadeTouchHandler$$ExternalSyntheticLambda0(1)).orElse(null);
                if (shadeViewController != null) {
                    ((NotificationPanelViewController) shadeViewController).mTouchHandler.onTouchEvent((MotionEvent) inputEvent);
                }
                if ((inputEvent instanceof MotionEvent) && ((MotionEvent) inputEvent).getAction() == 1) {
                    ((DreamOverlayTouchMonitor.TouchSessionImpl) touchSession).pop();
                }
            }
        };
        DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = (DreamOverlayTouchMonitor.TouchSessionImpl) touchSession;
        touchSessionImpl.mEventListeners.add(inputChannelCompat$InputEventListener);
        touchSessionImpl.mGestureListeners.add(new GestureDetector.SimpleOnGestureListener(this) { // from class: com.android.systemui.dreams.touch.ShadeTouchHandler.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                return true;
            }
        });
    }
}
