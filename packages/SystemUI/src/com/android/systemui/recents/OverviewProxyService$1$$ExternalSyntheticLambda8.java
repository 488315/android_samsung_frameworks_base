package com.android.systemui.recents;

import android.view.MotionEvent;
import com.android.systemui.Flags;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeViewController;
import dagger.Lazy;

public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OverviewProxyService.AnonymousClass1 f$0;
    public final /* synthetic */ MotionEvent f$1;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda8(OverviewProxyService.AnonymousClass1 anonymousClass1, MotionEvent motionEvent, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = motionEvent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                OverviewProxyService.AnonymousClass1 anonymousClass1 = this.f$0;
                MotionEvent motionEvent = this.f$1;
                anonymousClass1.getClass();
                int i = SceneContainerFlag.$r8$clinit;
                Flags.sceneContainer();
                ((ShadeViewController) OverviewProxyService.this.mShadeViewControllerLazy.get()).handleExternalTouch(motionEvent);
                break;
            case 1:
                OverviewProxyService.AnonymousClass1 anonymousClass12 = this.f$0;
                MotionEvent motionEvent2 = this.f$1;
                anonymousClass12.getClass();
                int i2 = SceneContainerFlag.$r8$clinit;
                Flags.sceneContainer();
                if (motionEvent2.getActionMasked() == 0) {
                    ((ShadeViewController) OverviewProxyService.this.mShadeViewControllerLazy.get()).startExpandLatencyTracking();
                }
                OverviewProxyService.this.mHandler.post(new OverviewProxyService$1$$ExternalSyntheticLambda8(anonymousClass12, motionEvent2, 2));
                break;
            default:
                OverviewProxyService.AnonymousClass1 anonymousClass13 = this.f$0;
                MotionEvent motionEvent3 = this.f$1;
                anonymousClass13.getClass();
                int actionMasked = motionEvent3.getActionMasked();
                if (actionMasked == 0) {
                    OverviewProxyService overviewProxyService = OverviewProxyService.this;
                    overviewProxyService.mInputFocusTransferStarted = true;
                    overviewProxyService.mInputFocusTransferStartY = motionEvent3.getY();
                    OverviewProxyService.this.mInputFocusTransferStartMillis = motionEvent3.getEventTime();
                    int i3 = SceneContainerFlag.$r8$clinit;
                    Flags.sceneContainer();
                    ((ShadeViewController) OverviewProxyService.this.mShadeViewControllerLazy.get()).startInputFocusTransfer();
                    if (SecPanelSplitHelper.isEnabled()) {
                        ((ShadeViewController) OverviewProxyService.this.mShadeViewControllerLazy.get()).setOnStatusBarDownEvent(MotionEvent.obtain(motionEvent3));
                    }
                }
                if (actionMasked == 1 || actionMasked == 3) {
                    OverviewProxyService.this.mInputFocusTransferStarted = false;
                    int i4 = SceneContainerFlag.$r8$clinit;
                    Flags.sceneContainer();
                    float y = motionEvent3.getY() - OverviewProxyService.this.mInputFocusTransferStartY;
                    long eventTime = motionEvent3.getEventTime();
                    float f = y / (eventTime - r6.mInputFocusTransferStartMillis);
                    Lazy lazy = OverviewProxyService.this.mShadeViewControllerLazy;
                    if (actionMasked == 3) {
                        ((ShadeViewController) lazy.get()).cancelInputFocusTransfer();
                    } else {
                        ((ShadeViewController) lazy.get()).finishInputFocusTransfer(f);
                        if (SecPanelSplitHelper.isEnabled()) {
                            ((ShadeViewController) OverviewProxyService.this.mShadeViewControllerLazy.get()).setOnStatusBarDownEvent(null);
                        }
                    }
                }
                motionEvent3.recycle();
                break;
        }
    }
}
