package com.android.systemui.statusbar.policy;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.shade.SecPanelExpansionStateNotifier;
import com.android.systemui.slimindicator.SlimIndicatorViewMediator;
import com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl;
import com.android.systemui.slimindicator.SlimIndicatorViewSubscriber;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSClockQuickStarHelper implements SlimIndicatorViewSubscriber {
    public final Runnable mRingBellOfTowerRunnable;
    public final RunnableC34222 mSecondTick = new Runnable() { // from class: com.android.systemui.statusbar.policy.QSClockQuickStarHelper.2
        @Override // java.lang.Runnable
        public final void run() {
            QSClockQuickStarHelper.this.mRingBellOfTowerRunnable.run();
            QSClockQuickStarHelper qSClockQuickStarHelper = QSClockQuickStarHelper.this;
            Handler handler = qSClockQuickStarHelper.mSecondsHandler;
            if (handler != null) {
                qSClockQuickStarHelper.getClass();
                handler.postAtTime(this, (SystemClock.uptimeMillis() + 1000) - (System.currentTimeMillis() % 1000));
            }
        }
    };
    public Handler mSecondsHandler;
    public boolean mShouldShowSecondsClockByQuickStar;
    public final SlimIndicatorViewMediator mSlimIndicatorViewMediator;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.policy.QSClockQuickStarHelper$2] */
    public QSClockQuickStarHelper(SlimIndicatorViewMediator slimIndicatorViewMediator, Runnable runnable) {
        this.mSlimIndicatorViewMediator = slimIndicatorViewMediator;
        this.mRingBellOfTowerRunnable = runnable;
    }

    public final boolean shouldShowSecondsClock() {
        if (!this.mShouldShowSecondsClockByQuickStar) {
            return false;
        }
        int i = ((SecPanelExpansionStateNotifier) Dependency.get(SecPanelExpansionStateNotifier.class)).mModel.panelOpenState;
        return i == 2 || i == 1;
    }

    @Override // com.android.systemui.slimindicator.SlimIndicatorViewSubscriber
    public final void updateQuickStarStyle() {
        SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = (SlimIndicatorViewMediatorImpl) this.mSlimIndicatorViewMediator;
        String iconBlacklist = slimIndicatorViewMediatorImpl.mSettingsHelper.getIconBlacklist();
        boolean z = slimIndicatorViewMediatorImpl.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_SHOW_SECONDS);
        Log.d("QSClockBellTower", "updateQuickStarStyle() shouldShowSecondsClock(" + this.mShouldShowSecondsClockByQuickStar + " >> " + z + ")");
        if (this.mShouldShowSecondsClockByQuickStar != z) {
            this.mShouldShowSecondsClockByQuickStar = z;
            updateSecondsClockHandler();
        }
        this.mRingBellOfTowerRunnable.run();
    }

    public final void updateSecondsClockHandler() {
        boolean shouldShowSecondsClock = shouldShowSecondsClock();
        RunnableC34222 runnableC34222 = this.mSecondTick;
        if (shouldShowSecondsClock) {
            if (this.mSecondsHandler == null) {
                Handler handler = new Handler();
                this.mSecondsHandler = handler;
                handler.post(runnableC34222);
                return;
            }
            return;
        }
        Handler handler2 = this.mSecondsHandler;
        if (handler2 != null) {
            handler2.removeCallbacks(runnableC34222);
            this.mSecondsHandler = null;
        }
    }
}
