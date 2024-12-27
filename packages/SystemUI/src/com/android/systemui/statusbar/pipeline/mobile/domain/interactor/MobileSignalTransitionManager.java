package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import android.util.Log;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ChannelCoroutine;

public final class MobileSignalTransitionManager {
    public int currentSignalStrength;
    public TransitionSignalState currentState;
    public boolean isTransition;
    public TransitionSignalState previousState;
    public int targetSignalStrength;
    public MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1 updateCallback;
    public final long updatePeriod;

    public final class TransitionSignalState {
        public static final /* synthetic */ TransitionSignalState[] $VALUES;
        public static final TransitionSignalState IN_SERVICE;
        public static final TransitionSignalState NO_SERVICE;

        static {
            TransitionSignalState transitionSignalState = new TransitionSignalState("NO_SERVICE", 0);
            NO_SERVICE = transitionSignalState;
            TransitionSignalState transitionSignalState2 = new TransitionSignalState("IN_SERVICE", 1);
            IN_SERVICE = transitionSignalState2;
            TransitionSignalState[] transitionSignalStateArr = {transitionSignalState, transitionSignalState2};
            $VALUES = transitionSignalStateArr;
            EnumEntriesKt.enumEntries(transitionSignalStateArr);
        }

        private TransitionSignalState(String str, int i) {
        }

        public static TransitionSignalState valueOf(String str) {
            return (TransitionSignalState) Enum.valueOf(TransitionSignalState.class, str);
        }

        public static TransitionSignalState[] values() {
            return (TransitionSignalState[]) $VALUES.clone();
        }
    }

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
        Log.d("MobileSignalTransitionManager", "updateSignalOneLevelPerSec state transition=" + this.isTransition + " prev=" + transitionSignalState + ", cur=" + transitionSignalState2 + ", max=" + i2);
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
                MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1 mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1 = this.updateCallback;
                Intrinsics.checkNotNull(mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1);
                ((ChannelCoroutine) mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1.$$this$conflatedCallbackFlow).mo2552trySendJP2dKIU(-1);
                return;
            }
            MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1 mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$12 = this.updateCallback;
            Intrinsics.checkNotNull(mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$12);
            final MobileIconInteractorImpl mobileIconInteractorImpl = mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$12.this$0;
            mobileIconInteractorImpl.bgHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1$postUpdate$1
                @Override // java.lang.Runnable
                public final void run() {
                    MobileIconInteractorImpl mobileIconInteractorImpl2 = MobileIconInteractorImpl.this;
                    mobileIconInteractorImpl2.mobileSignalTransition.updateSignalOneLevelPerSec(((Number) MobileIconInteractorImpl.this.cellularShownLevel.$$delegate_0.getValue()).intValue(), ((Number) MobileIconInteractorImpl.this.numberOfLevels.getValue()).intValue(), ((Boolean) mobileIconInteractorImpl2.isInService.getValue()).booleanValue());
                }
            }, 100L);
            this.isTransition = true;
            return;
        }
        int i5 = iArr[this.currentState.ordinal()];
        long j = this.updatePeriod;
        if (i5 == 1) {
            this.targetSignalStrength = i;
            Log.d("MobileSignalTransitionManager", HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(this.currentSignalStrength, i, "updateSignalOneLevelPerSec ", " -> ", " in service"));
            if (this.currentSignalStrength == this.targetSignalStrength) {
                this.isTransition = false;
                return;
            } else if (!this.isTransition) {
                this.isTransition = true;
            }
        } else if (i5 == 2) {
            this.targetSignalStrength = 0;
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.currentSignalStrength, "updateSignalOneLevelPerSec ", " -> 0 no service", "MobileSignalTransitionManager");
            if (this.currentSignalStrength == this.targetSignalStrength || ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAirplaneModeOn()) {
                MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1 mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$13 = this.updateCallback;
                Intrinsics.checkNotNull(mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$13);
                final MobileIconInteractorImpl mobileIconInteractorImpl2 = mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$13.this$0;
                mobileIconInteractorImpl2.bgHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1$postUpdate$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MobileIconInteractorImpl mobileIconInteractorImpl22 = MobileIconInteractorImpl.this;
                        mobileIconInteractorImpl22.mobileSignalTransition.updateSignalOneLevelPerSec(((Number) MobileIconInteractorImpl.this.cellularShownLevel.$$delegate_0.getValue()).intValue(), ((Number) MobileIconInteractorImpl.this.numberOfLevels.getValue()).intValue(), ((Boolean) mobileIconInteractorImpl22.isInService.getValue()).booleanValue());
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
        MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1 mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$14 = this.updateCallback;
        Intrinsics.checkNotNull(mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$14);
        ((ChannelCoroutine) mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$14.$$this$conflatedCallbackFlow).mo2552trySendJP2dKIU(Integer.valueOf(this.currentSignalStrength));
        MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1 mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$15 = this.updateCallback;
        Intrinsics.checkNotNull(mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$15);
        final MobileIconInteractorImpl mobileIconInteractorImpl3 = mobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$15.this$0;
        mobileIconInteractorImpl3.bgHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractorImpl$signalLevelUpdate$1$mSignalUpdateCallback$1$postUpdate$1
            @Override // java.lang.Runnable
            public final void run() {
                MobileIconInteractorImpl mobileIconInteractorImpl22 = MobileIconInteractorImpl.this;
                mobileIconInteractorImpl22.mobileSignalTransition.updateSignalOneLevelPerSec(((Number) MobileIconInteractorImpl.this.cellularShownLevel.$$delegate_0.getValue()).intValue(), ((Number) MobileIconInteractorImpl.this.numberOfLevels.getValue()).intValue(), ((Boolean) mobileIconInteractorImpl22.isInService.getValue()).booleanValue());
            }
        }, j);
    }
}
