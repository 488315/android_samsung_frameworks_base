package com.android.systemui.recents;

import android.util.Log;
import android.view.MotionEvent;
import com.android.systemui.Dependency;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import dagger.Lazy;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LauncherProxyService$1$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LauncherProxyService.AnonymousClass1 f$0;
    public final /* synthetic */ MotionEvent f$1;

    public /* synthetic */ LauncherProxyService$1$$ExternalSyntheticLambda3(LauncherProxyService.AnonymousClass1 anonymousClass1, MotionEvent motionEvent, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = motionEvent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LauncherProxyService.AnonymousClass1 anonymousClass1 = this.f$0;
                MotionEvent motionEvent = this.f$1;
                int i = LauncherProxyService.AnonymousClass1.$r8$clinit;
                int i2 = SceneContainerFlag.$r8$clinit;
                if (motionEvent.getActionMasked() == 0) {
                    ((ShadeViewController) LauncherProxyService.this.mShadeViewControllerLazy.get()).startExpandLatencyTracking();
                }
                LauncherProxyService.this.mHandler.post(new LauncherProxyService$1$$ExternalSyntheticLambda3(anonymousClass1, motionEvent, 2));
                break;
            case 1:
                LauncherProxyService.AnonymousClass1 anonymousClass12 = this.f$0;
                MotionEvent motionEvent2 = this.f$1;
                int i3 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                int i4 = SceneContainerFlag.$r8$clinit;
                ((ShadeViewController) LauncherProxyService.this.mShadeViewControllerLazy.get()).handleExternalTouch(motionEvent2);
                break;
            default:
                LauncherProxyService.AnonymousClass1 anonymousClass13 = this.f$0;
                MotionEvent motionEvent3 = this.f$1;
                int i5 = LauncherProxyService.AnonymousClass1.$r8$clinit;
                int actionMasked = motionEvent3.getActionMasked();
                if (actionMasked == 0) {
                    if (((Boolean) ((ShadeInteractorImpl) ((ShadeInteractor) LauncherProxyService.this.mShadeInteractor.get())).isUserInteracting.$$delegate_0.getValue()).booleanValue()) {
                        Log.i("LauncherProxyService", "onStatusBarTouchEvent: already user interacting");
                        break;
                    } else {
                        anonymousClass13.mDownTime = motionEvent3.getDownTime();
                        LauncherProxyService launcherProxyService = LauncherProxyService.this;
                        launcherProxyService.mInputFocusTransferStarted = true;
                        launcherProxyService.mInputFocusTransferStartY = motionEvent3.getY();
                        LauncherProxyService.this.mInputFocusTransferStartMillis = motionEvent3.getEventTime();
                        int i6 = SceneContainerFlag.$r8$clinit;
                        ((ShadeViewController) LauncherProxyService.this.mShadeViewControllerLazy.get()).startInputFocusTransfer();
                        if (SecPanelSplitHelper.isEnabled()) {
                            ((ShadeViewController) LauncherProxyService.this.mShadeViewControllerLazy.get()).setOnStatusBarDownEvent(MotionEvent.obtain(motionEvent3));
                        }
                    }
                }
                if (actionMasked == 1 || actionMasked == 3) {
                    if (anonymousClass13.mDownTime != motionEvent3.getDownTime()) {
                        Log.i("LauncherProxyService", "onStatusBarTouchEvent: invalid actions");
                        break;
                    } else {
                        LauncherProxyService.this.mInputFocusTransferStarted = false;
                        int i7 = SceneContainerFlag.$r8$clinit;
                        float y = motionEvent3.getY() - LauncherProxyService.this.mInputFocusTransferStartY;
                        long eventTime = motionEvent3.getEventTime();
                        float f = y / (eventTime - r3.mInputFocusTransferStartMillis);
                        Lazy lazy = LauncherProxyService.this.mShadeViewControllerLazy;
                        if (actionMasked == 3) {
                            ((ShadeViewController) lazy.get()).cancelInputFocusTransfer();
                        } else {
                            ((ShadeViewController) lazy.get()).finishInputFocusTransfer(f);
                            if (SecPanelSplitHelper.isEnabled()) {
                                ((ShadeViewController) LauncherProxyService.this.mShadeViewControllerLazy.get()).setOnStatusBarDownEvent(null);
                            }
                        }
                        SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
                        secPanelSAStatusLogInteractor.getClass();
                        SecPanelSplitHelper.Companion.getClass();
                        if (SecPanelSplitHelper.isEnabled) {
                            StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openNotificationPanelFromHomescreen;
                            LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                        }
                    }
                }
                motionEvent3.recycle();
                break;
        }
    }
}
