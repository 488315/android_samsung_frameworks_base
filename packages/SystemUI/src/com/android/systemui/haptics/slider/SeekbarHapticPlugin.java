package com.android.systemui.haptics.slider;

import android.view.VelocityTracker;
import android.widget.SeekBar;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class SeekbarHapticPlugin {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Job keyUpJob;
    public CoroutineScope pluginScope;
    public final SliderStateProducer sliderEventProducer;
    public final SliderHapticFeedbackProvider sliderHapticFeedbackProvider;
    public SliderStateTracker sliderTracker;
    public final SeekableSliderTrackerConfig sliderTrackerConfig;
    public final VelocityTracker velocityTracker;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SeekbarHapticPlugin(VibratorHelper vibratorHelper, SystemClock systemClock) {
        this(vibratorHelper, systemClock, null, null, 12, null);
    }

    public static float normalizeProgress(SeekBar seekBar, int i) {
        if (seekBar.getMax() == seekBar.getMin()) {
            return 1.0f;
        }
        return (i - seekBar.getMin()) / (seekBar.getMax() - seekBar.getMin());
    }

    public final boolean isTracking() {
        Job job;
        SliderStateTracker sliderStateTracker = this.sliderTracker;
        return (sliderStateTracker == null || (job = sliderStateTracker.job) == null || !job.isActive()) ? false : true;
    }

    public final void onKeyDown() {
        Job job;
        if (isTracking()) {
            Job job2 = this.keyUpJob;
            if (job2 != null && job2.isActive() && (job = this.keyUpJob) != null) {
                job.cancel(null);
            }
            CoroutineScope coroutineScope = this.pluginScope;
            this.keyUpJob = coroutineScope != null ? BuildersKt.launch$default(coroutineScope, null, null, new SeekbarHapticPlugin$onKeyDown$1(this, null), 3) : null;
        }
    }

    public SeekbarHapticPlugin(VibratorHelper vibratorHelper, SystemClock systemClock, SliderHapticFeedbackConfig sliderHapticFeedbackConfig) {
        this(vibratorHelper, systemClock, sliderHapticFeedbackConfig, null, 8, null);
    }

    public SeekbarHapticPlugin(VibratorHelper vibratorHelper, SystemClock systemClock, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SeekableSliderTrackerConfig seekableSliderTrackerConfig) {
        this.sliderTrackerConfig = seekableSliderTrackerConfig;
        VelocityTracker obtain = VelocityTracker.obtain();
        this.velocityTracker = obtain;
        this.sliderEventProducer = new SliderStateProducer();
        this.sliderHapticFeedbackProvider = new SliderHapticFeedbackProvider(vibratorHelper, obtain, sliderHapticFeedbackConfig, systemClock);
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ SeekbarHapticPlugin(com.android.systemui.statusbar.VibratorHelper r18, com.android.systemui.util.time.SystemClock r19, com.android.systemui.haptics.slider.SliderHapticFeedbackConfig r20, com.android.systemui.haptics.slider.SeekableSliderTrackerConfig r21, int r22, kotlin.jvm.internal.DefaultConstructorMarker r23) {
        /*
            r17 = this;
            r0 = r22 & 4
            if (r0 == 0) goto L1c
            com.android.systemui.haptics.slider.SliderHapticFeedbackConfig r0 = new com.android.systemui.haptics.slider.SliderHapticFeedbackConfig
            r15 = 8191(0x1fff, float:1.1478E-41)
            r16 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r1 = r0
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)
            goto L1e
        L1c:
            r0 = r20
        L1e:
            r1 = r22 & 8
            if (r1 == 0) goto L37
            com.android.systemui.haptics.slider.SeekableSliderTrackerConfig r1 = new com.android.systemui.haptics.slider.SeekableSliderTrackerConfig
            r8 = 15
            r9 = 0
            r3 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r2 = r1
            r2.<init>(r3, r5, r6, r7, r8, r9)
            r2 = r17
            r3 = r18
            r4 = r19
            goto L3f
        L37:
            r2 = r17
            r3 = r18
            r4 = r19
            r1 = r21
        L3f:
            r2.<init>(r3, r4, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.haptics.slider.SeekbarHapticPlugin.<init>(com.android.systemui.statusbar.VibratorHelper, com.android.systemui.util.time.SystemClock, com.android.systemui.haptics.slider.SliderHapticFeedbackConfig, com.android.systemui.haptics.slider.SeekableSliderTrackerConfig, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public static /* synthetic */ void isKeyUpTimerWaiting$annotations() {
    }
}
