package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.util.Log;
import androidx.core.app.AbstractC0147x487e7be7;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ChannelCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileSignalTransitionManager {
    public int currentSignalStrength;
    public TransitionSignalState currentState;
    public boolean isTransition;
    public TransitionSignalState previousState;
    public int targetSignalStrength;
    public C3320xf996240e updateCallback;
    public final long updatePeriod;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum TransitionSignalState {
        NO_SERVICE,
        IN_SERVICE
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TransitionSignalState.values().length];
            try {
                iArr[TransitionSignalState.IN_SERVICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TransitionSignalState.NO_SERVICE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public MobileSignalTransitionManager() {
        TransitionSignalState transitionSignalState = TransitionSignalState.NO_SERVICE;
        this.previousState = transitionSignalState;
        this.currentState = transitionSignalState;
        this.updatePeriod = 1000L;
    }

    public final void updateSignalOneLevelPerSec(int i, int i2, boolean z) {
        if (this.updateCallback == null) {
            return;
        }
        TransitionSignalState transitionSignalState = this.currentState;
        this.previousState = transitionSignalState;
        TransitionSignalState transitionSignalState2 = z ? TransitionSignalState.IN_SERVICE : TransitionSignalState.NO_SERVICE;
        this.currentState = transitionSignalState2;
        Log.d("MobileSignalTransitionManager", "updateSignalOneLevelPerSec state transition=" + this.isTransition + " prev=" + transitionSignalState + ", cur=" + transitionSignalState2);
        TransitionSignalState transitionSignalState3 = this.previousState;
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        int i3 = iArr[transitionSignalState3.ordinal()];
        if (i3 != 1) {
            if (i3 != 2) {
                return;
            }
            this.isTransition = false;
            this.currentSignalStrength = -1;
            int i4 = iArr[this.currentState.ordinal()];
            if (i4 != 1) {
                if (i4 != 2) {
                    return;
                }
                C3320xf996240e c3320xf996240e = this.updateCallback;
                Intrinsics.checkNotNull(c3320xf996240e);
                ((ChannelCoroutine) c3320xf996240e.$$this$conflatedCallbackFlow).mo2872trySendJP2dKIU(-1);
                return;
            }
            C3320xf996240e c3320xf996240e2 = this.updateCallback;
            Intrinsics.checkNotNull(c3320xf996240e2);
            final MobileIconInteractorImpl mobileIconInteractorImpl = c3320xf996240e2.this$0;
            mobileIconInteractorImpl.bgHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1$postUpdate$1
                @Override // java.lang.Runnable
                public final void run() {
                    MobileIconInteractorImpl mobileIconInteractorImpl2 = MobileIconInteractorImpl.this;
                    mobileIconInteractorImpl2.mobileSignalTransition.updateSignalOneLevelPerSec(((Number) MobileIconInteractorImpl.this.shownLevel.getValue()).intValue(), ((Number) MobileIconInteractorImpl.this.numberOfLevels.getValue()).intValue(), ((Boolean) mobileIconInteractorImpl2.isInService.getValue()).booleanValue());
                }
            }, 100L);
            this.isTransition = true;
            return;
        }
        int i5 = iArr[this.currentState.ordinal()];
        long j = this.updatePeriod;
        if (i5 == 1) {
            this.targetSignalStrength = i;
            Log.d("MobileSignalTransitionManager", SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("updateSignalOneLevelPerSec ", this.currentSignalStrength, " -> ", i, " in service"));
            if (this.currentSignalStrength == this.targetSignalStrength) {
                this.isTransition = false;
                return;
            } else if (!this.isTransition) {
                this.isTransition = true;
            }
        } else if (i5 == 2) {
            this.targetSignalStrength = 0;
            AbstractC0147x487e7be7.m26m("updateSignalOneLevelPerSec ", this.currentSignalStrength, " -> 0 no service", "MobileSignalTransitionManager");
            if (this.currentSignalStrength == this.targetSignalStrength || ((SettingsHelper) Dependency.get(SettingsHelper.class)).isAirplaneModeOn()) {
                C3320xf996240e c3320xf996240e3 = this.updateCallback;
                Intrinsics.checkNotNull(c3320xf996240e3);
                final MobileIconInteractorImpl mobileIconInteractorImpl2 = c3320xf996240e3.this$0;
                mobileIconInteractorImpl2.bgHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1$postUpdate$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MobileIconInteractorImpl mobileIconInteractorImpl22 = MobileIconInteractorImpl.this;
                        mobileIconInteractorImpl22.mobileSignalTransition.updateSignalOneLevelPerSec(((Number) MobileIconInteractorImpl.this.shownLevel.getValue()).intValue(), ((Number) MobileIconInteractorImpl.this.numberOfLevels.getValue()).intValue(), ((Boolean) mobileIconInteractorImpl22.isInService.getValue()).booleanValue());
                    }
                }, j);
                return;
            }
            this.currentState = TransitionSignalState.IN_SERVICE;
            this.isTransition = true;
        }
        int i6 = this.currentSignalStrength;
        int i7 = this.targetSignalStrength;
        if (i6 < i7 && i6 < i2) {
            this.currentSignalStrength = i6 + 1;
        } else if (i6 > i7 && i6 > 0) {
            this.currentSignalStrength = i6 - 1;
        }
        C3320xf996240e c3320xf996240e4 = this.updateCallback;
        Intrinsics.checkNotNull(c3320xf996240e4);
        ((ChannelCoroutine) c3320xf996240e4.$$this$conflatedCallbackFlow).mo2872trySendJP2dKIU(Integer.valueOf(this.currentSignalStrength));
        C3320xf996240e c3320xf996240e5 = this.updateCallback;
        Intrinsics.checkNotNull(c3320xf996240e5);
        final MobileIconInteractorImpl mobileIconInteractorImpl3 = c3320xf996240e5.this$0;
        mobileIconInteractorImpl3.bgHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1$postUpdate$1
            @Override // java.lang.Runnable
            public final void run() {
                MobileIconInteractorImpl mobileIconInteractorImpl22 = MobileIconInteractorImpl.this;
                mobileIconInteractorImpl22.mobileSignalTransition.updateSignalOneLevelPerSec(((Number) MobileIconInteractorImpl.this.shownLevel.getValue()).intValue(), ((Number) MobileIconInteractorImpl.this.numberOfLevels.getValue()).intValue(), ((Boolean) mobileIconInteractorImpl22.isInService.getValue()).booleanValue());
            }
        }, j);
    }
}
