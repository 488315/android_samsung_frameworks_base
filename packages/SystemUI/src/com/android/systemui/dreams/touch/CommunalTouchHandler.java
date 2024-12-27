package com.android.systemui.dreams.touch;

import android.graphics.Rect;
import android.graphics.Region;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import androidx.lifecycle.Lifecycle;
import com.android.systemui.ambient.touch.TouchHandler;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalTouchHandler implements TouchHandler {
    public final Optional mCentralSurfaces;
    public final int mInitiationWidth;
    final Consumer<Boolean> mIsCommunalAvailableCallback;
    public Boolean mIsEnabled = Boolean.FALSE;

    public CommunalTouchHandler(Optional<CentralSurfaces> optional, int i, CommunalInteractor communalInteractor, Lifecycle lifecycle) {
        Consumer<Boolean> consumer = new Consumer() { // from class: com.android.systemui.dreams.touch.CommunalTouchHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CommunalTouchHandler.this.mIsEnabled = (Boolean) obj;
            }
        };
        this.mIsCommunalAvailableCallback = consumer;
        this.mInitiationWidth = i;
        this.mCentralSurfaces = optional;
        JavaAdapterKt.collectFlow(lifecycle, communalInteractor.isCommunalAvailable, consumer);
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void getTouchInitiationRegion(Rect rect, Region region, Rect rect2) {
        Rect rect3 = new Rect(rect);
        rect3.inset(rect3.width() - this.mInitiationWidth, 0, 0, 0);
        region.op(rect3, Region.Op.UNION);
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final Boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void onSessionStart(final TouchMonitor.TouchSessionImpl touchSessionImpl) {
        if (this.mIsEnabled.booleanValue()) {
            this.mCentralSurfaces.ifPresent(new Consumer() { // from class: com.android.systemui.dreams.touch.CommunalTouchHandler$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CommunalTouchHandler communalTouchHandler = CommunalTouchHandler.this;
                    final TouchMonitor.TouchSessionImpl touchSessionImpl2 = touchSessionImpl;
                    final CentralSurfaces centralSurfaces = (CentralSurfaces) obj;
                    communalTouchHandler.getClass();
                    touchSessionImpl2.mEventListeners.add(new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.dreams.touch.CommunalTouchHandler$$ExternalSyntheticLambda2
                        @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                        public final void onInputEvent(InputEvent inputEvent) {
                            ((CentralSurfacesImpl) CentralSurfaces.this).mGlanceableHubContainerController.onTouchEvent((MotionEvent) inputEvent);
                            if (inputEvent == null || ((MotionEvent) inputEvent).getAction() != 1) {
                                return;
                            }
                            touchSessionImpl2.pop();
                        }
                    });
                    touchSessionImpl2.mGestureListeners.add(new GestureDetector.SimpleOnGestureListener(communalTouchHandler) { // from class: com.android.systemui.dreams.touch.CommunalTouchHandler.1
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
            });
        }
    }
}
