package com.android.systemui.dreams.touch;

import android.graphics.Rect;
import android.graphics.Region;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import androidx.lifecycle.Lifecycle;
import com.android.systemui.ambient.touch.TouchHandler;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.GlanceableHubContainerController;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.function.Consumer;
import javax.inject.Provider;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class CommunalTouchHandler implements TouchHandler {
    public final Optional mCentralSurfaces;
    public final ArrayList mFlows;
    public final int mInitiationWidth;
    final Consumer<Boolean> mIsCommunalAvailableCallback;
    public Boolean mIsEnabled = Boolean.FALSE;
    public int mLayoutDirection;
    final Consumer<Integer> mLayoutDirectionCallback;

    public CommunalTouchHandler(Optional<CentralSurfaces> optional, int i, CommunalInteractor communalInteractor, ConfigurationInteractor configurationInteractor, SceneInteractor sceneInteractor, Optional<Provider> optional2, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        this.mFlows = arrayList;
        this.mLayoutDirection = 0;
        final int i2 = 0;
        Consumer<Boolean> consumer = new Consumer(this) { // from class: com.android.systemui.dreams.touch.CommunalTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i3 = i2;
                CommunalTouchHandler communalTouchHandler = this.f$0;
                switch (i3) {
                    case 0:
                        communalTouchHandler.mIsEnabled = (Boolean) obj;
                        break;
                    default:
                        communalTouchHandler.getClass();
                        communalTouchHandler.mLayoutDirection = ((Integer) obj).intValue();
                        break;
                }
            }
        };
        this.mIsCommunalAvailableCallback = consumer;
        final int i3 = 1;
        Consumer<Integer> consumer2 = new Consumer(this) { // from class: com.android.systemui.dreams.touch.CommunalTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i3;
                CommunalTouchHandler communalTouchHandler = this.f$0;
                switch (i32) {
                    case 0:
                        communalTouchHandler.mIsEnabled = (Boolean) obj;
                        break;
                    default:
                        communalTouchHandler.getClass();
                        communalTouchHandler.mLayoutDirection = ((Integer) obj).intValue();
                        break;
                }
            }
        };
        this.mLayoutDirectionCallback = consumer2;
        this.mInitiationWidth = i;
        this.mCentralSurfaces = optional;
        arrayList.add(JavaAdapterKt.collectFlow(lifecycle, communalInteractor.isCommunalAvailable(), consumer));
        arrayList.add(JavaAdapterKt.collectFlow(lifecycle, ((ConfigurationInteractorImpl) configurationInteractor).layoutDirection, consumer2));
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void getTouchInitiationRegion(Rect rect, Region region, Rect rect2) {
        Rect rect3 = new Rect(rect);
        int width = rect3.width() - this.mInitiationWidth;
        if (this.mLayoutDirection == 0) {
            rect3.inset(width, 0, 0, 0);
        } else {
            rect3.inset(0, 0, width, 0);
        }
        region.op(rect3, Region.Op.UNION);
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final Boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void onDestroy() {
        ArrayList arrayList = this.mFlows;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Job) obj).cancel(new CancellationException());
        }
        this.mFlows.clear();
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void onSessionStart(final TouchHandler.TouchSession touchSession) {
        if (this.mIsEnabled.booleanValue()) {
            this.mCentralSurfaces.ifPresent(new Consumer() { // from class: com.android.systemui.dreams.touch.CommunalTouchHandler$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    final CommunalTouchHandler communalTouchHandler = CommunalTouchHandler.this;
                    final TouchHandler.TouchSession touchSession2 = touchSession;
                    final CentralSurfaces centralSurfaces = (CentralSurfaces) obj;
                    communalTouchHandler.getClass();
                    int i = SceneContainerFlag.$r8$clinit;
                    InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener = new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.dreams.touch.CommunalTouchHandler$$ExternalSyntheticLambda3
                        @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                        public final void onInputEvent(InputEvent inputEvent) {
                            CommunalTouchHandler.this.getClass();
                            int i2 = SceneContainerFlag.$r8$clinit;
                            MotionEvent motionEvent = (MotionEvent) inputEvent;
                            GlanceableHubContainerController glanceableHubContainerController = ((CentralSurfacesImpl) centralSurfaces).mGlanceableHubContainerController;
                            if (glanceableHubContainerController != null) {
                                glanceableHubContainerController.onTouchEvent(motionEvent);
                            }
                            if (inputEvent == null || ((MotionEvent) inputEvent).getAction() != 1) {
                                return;
                            }
                            ((TouchMonitor.TouchSessionImpl) touchSession2).pop();
                        }
                    };
                    TouchMonitor.TouchSessionImpl touchSessionImpl = (TouchMonitor.TouchSessionImpl) touchSession2;
                    touchSessionImpl.mEventListeners.add(inputChannelCompat$InputEventListener);
                    touchSessionImpl.mGestureListeners.add(new GestureDetector.SimpleOnGestureListener(communalTouchHandler) { // from class: com.android.systemui.dreams.touch.CommunalTouchHandler.1
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
