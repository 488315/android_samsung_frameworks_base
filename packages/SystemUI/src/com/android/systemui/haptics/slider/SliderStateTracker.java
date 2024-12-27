package com.android.systemui.haptics.slider;

import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.VibratorHelper$$ExternalSyntheticLambda1;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SliderStateTracker extends SliderTracker {
    public final SeekableSliderTrackerConfig config;
    public float latestProgress;
    public Job timerJob;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[SliderState.values().length];
            try {
                iArr[SliderState.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SliderState.WAIT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SliderState.DRAG_HANDLE_ACQUIRED_BY_TOUCH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SliderState.DRAG_HANDLE_DRAGGING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SliderState.DRAG_HANDLE_REACHED_BOOKEND.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[SliderState.DRAG_HANDLE_RELEASED_FROM_TOUCH.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[SliderState.JUMP_TRACK_LOCATION_SELECTED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[SliderState.JUMP_BOOKEND_SELECTED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[SliderState.ARROW_HANDLE_MOVED_ONCE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[SliderState.ARROW_HANDLE_MOVES_CONTINUOUSLY.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[SliderState.ARROW_HANDLE_REACHED_BOOKEND.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[SliderEventType.values().length];
            try {
                iArr2[SliderEventType.PROGRESS_CHANGE_BY_USER.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[SliderEventType.STOPPED_TRACKING_TOUCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[SliderEventType.STARTED_TRACKING_TOUCH.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr2[SliderEventType.PROGRESS_CHANGE_BY_PROGRAM.ordinal()] = 4;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[SliderEventType.STOPPED_TRACKING_PROGRAM.ordinal()] = 5;
            } catch (NoSuchFieldError unused16) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public /* synthetic */ SliderStateTracker(SliderStateListener sliderStateListener, SliderEventProducer sliderEventProducer, CoroutineScope coroutineScope, SeekableSliderTrackerConfig seekableSliderTrackerConfig, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(sliderStateListener, sliderEventProducer, coroutineScope, (i & 8) != 0 ? new SeekableSliderTrackerConfig(0L, 0.0f, 0.0f, 0.0f, 15, null) : seekableSliderTrackerConfig);
    }

    public final boolean bookendReached(float f) {
        SeekableSliderTrackerConfig seekableSliderTrackerConfig = this.config;
        return f >= seekableSliderTrackerConfig.upperBookendThreshold || f <= seekableSliderTrackerConfig.lowerBookendThreshold;
    }

    public final void executeOnBookend() {
        float f = this.latestProgress;
        float f2 = this.config.upperBookendThreshold;
        SliderStateListener sliderStateListener = this.sliderListener;
        if (f >= f2) {
            SliderHapticFeedbackProvider sliderHapticFeedbackProvider = (SliderHapticFeedbackProvider) sliderStateListener;
            if (sliderHapticFeedbackProvider.hasVibratedAtUpperBookend) {
                return;
            }
            VibrationEffect compose = VibrationEffect.startComposition().addPrimitive(1, sliderHapticFeedbackProvider.scaleOnEdgeCollision(Math.abs(sliderHapticFeedbackProvider.getTrackedVelocity()))).compose();
            VibrationAttributes vibrationAttributes = SliderHapticFeedbackProvider.VIBRATION_ATTRIBUTES_PIPELINING;
            VibratorHelper vibratorHelper = sliderHapticFeedbackProvider.vibratorHelper;
            if (vibratorHelper.hasVibrator()) {
                vibratorHelper.mExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda1(vibratorHelper, compose, vibrationAttributes));
            }
            sliderHapticFeedbackProvider.hasVibratedAtUpperBookend = true;
            return;
        }
        SliderHapticFeedbackProvider sliderHapticFeedbackProvider2 = (SliderHapticFeedbackProvider) sliderStateListener;
        if (sliderHapticFeedbackProvider2.hasVibratedAtLowerBookend) {
            return;
        }
        VibrationEffect compose2 = VibrationEffect.startComposition().addPrimitive(1, sliderHapticFeedbackProvider2.scaleOnEdgeCollision(Math.abs(sliderHapticFeedbackProvider2.getTrackedVelocity()))).compose();
        VibrationAttributes vibrationAttributes2 = SliderHapticFeedbackProvider.VIBRATION_ATTRIBUTES_PIPELINING;
        VibratorHelper vibratorHelper2 = sliderHapticFeedbackProvider2.vibratorHelper;
        if (vibratorHelper2.hasVibrator()) {
            vibratorHelper2.mExecutor.execute(new VibratorHelper$$ExternalSyntheticLambda1(vibratorHelper2, compose2, vibrationAttributes2));
        }
        sliderHapticFeedbackProvider2.hasVibratedAtLowerBookend = true;
    }

    @Override // com.android.systemui.haptics.slider.SliderTracker
    public final void executeOnState(SliderState sliderState) {
        int i = WhenMappings.$EnumSwitchMapping$0[sliderState.ordinal()];
        SliderStateListener sliderStateListener = this.sliderListener;
        switch (i) {
            case 3:
                sliderStateListener.getClass();
                break;
            case 4:
                ((SliderHapticFeedbackProvider) sliderStateListener).onProgress(this.latestProgress);
                break;
            case 5:
                executeOnBookend();
                break;
            case 6:
                ((SliderHapticFeedbackProvider) sliderStateListener).dragTextureLastProgress = -1.0f;
                Job job = this.timerJob;
                if (job != null) {
                    job.cancel(null);
                }
                this.timerJob = null;
                this.currentState = SliderState.IDLE;
                break;
            case 7:
                sliderStateListener.getClass();
                break;
            case 9:
                sliderStateListener.getClass();
                break;
            case 10:
                ((SliderHapticFeedbackProvider) sliderStateListener).onProgress(this.latestProgress);
                break;
            case 11:
                executeOnBookend();
                Job job2 = this.timerJob;
                if (job2 != null) {
                    job2.cancel(null);
                }
                this.timerJob = null;
                this.currentState = SliderState.IDLE;
                break;
        }
    }

    @Override // com.android.systemui.haptics.slider.SliderTracker
    public final Unit iterateState(SliderEvent sliderEvent) {
        SliderState sliderState;
        SliderState sliderState2;
        int i = WhenMappings.$EnumSwitchMapping$0[this.currentState.ordinal()];
        CoroutineScope coroutineScope = this.scope;
        switch (i) {
            case 1:
                SliderEventType sliderEventType = sliderEvent.type;
                if (sliderEventType != SliderEventType.STARTED_TRACKING_TOUCH) {
                    if (sliderEventType == SliderEventType.STARTED_TRACKING_PROGRAM) {
                        setState(bookendReached(sliderEvent.currentProgress) ? SliderState.ARROW_HANDLE_REACHED_BOOKEND : SliderState.ARROW_HANDLE_MOVED_ONCE);
                        break;
                    }
                } else {
                    this.timerJob = BuildersKt.launch$default(coroutineScope, null, null, new SliderStateTracker$launchTimer$1(this, null), 3);
                    setState(SliderState.WAIT);
                    break;
                }
                break;
            case 2:
                SliderEventType sliderEventType2 = sliderEvent.type;
                SliderState sliderState3 = this.currentState;
                SliderState sliderState4 = SliderState.WAIT;
                if (sliderState3 == sliderState4) {
                    float f = this.latestProgress;
                    float f2 = sliderEvent.currentProgress;
                    boolean z = Math.abs(f2 - f) > this.config.jumpThreshold - 1.0E-5f;
                    if (sliderEventType2 == SliderEventType.PROGRESS_CHANGE_BY_USER) {
                        if (bookendReached(f2)) {
                            setState(SliderState.JUMP_BOOKEND_SELECTED);
                        } else if (z) {
                            setState(SliderState.JUMP_TRACK_LOCATION_SELECTED);
                        } else {
                            setState(SliderState.DRAG_HANDLE_ACQUIRED_BY_TOUCH);
                        }
                    } else if (sliderEventType2 == SliderEventType.STOPPED_TRACKING_TOUCH) {
                        setState(SliderState.IDLE);
                    }
                    if (this.currentState != sliderState4) {
                        Job job = this.timerJob;
                        if (job != null) {
                            job.cancel(null);
                        }
                        this.timerJob = null;
                        break;
                    }
                }
                break;
            case 3:
                SliderEventType sliderEventType3 = sliderEvent.type;
                if (sliderEventType3 != SliderEventType.STOPPED_TRACKING_TOUCH) {
                    if (sliderEventType3 == SliderEventType.PROGRESS_CHANGE_BY_USER) {
                        setState(SliderState.DRAG_HANDLE_DRAGGING);
                        break;
                    }
                } else {
                    setState(SliderState.DRAG_HANDLE_RELEASED_FROM_TOUCH);
                    break;
                }
                break;
            case 4:
                SliderEventType sliderEventType4 = sliderEvent.type;
                if (sliderEventType4 != SliderEventType.STOPPED_TRACKING_TOUCH) {
                    if (sliderEventType4 == SliderEventType.PROGRESS_CHANGE_BY_USER && bookendReached(sliderEvent.currentProgress)) {
                        setState(SliderState.DRAG_HANDLE_REACHED_BOOKEND);
                        break;
                    }
                } else {
                    setState(SliderState.DRAG_HANDLE_RELEASED_FROM_TOUCH);
                    break;
                }
                break;
            case 5:
                SliderEventType sliderEventType5 = sliderEvent.type;
                if (sliderEventType5 != SliderEventType.PROGRESS_CHANGE_BY_USER) {
                    if (sliderEventType5 == SliderEventType.STOPPED_TRACKING_TOUCH) {
                        setState(SliderState.DRAG_HANDLE_RELEASED_FROM_TOUCH);
                        break;
                    }
                } else if (!bookendReached(sliderEvent.currentProgress)) {
                    setState(SliderState.DRAG_HANDLE_DRAGGING);
                    break;
                }
                break;
            case 6:
                setState(SliderState.IDLE);
                break;
            case 7:
                int i2 = WhenMappings.$EnumSwitchMapping$1[sliderEvent.type.ordinal()];
                if (i2 == 1) {
                    setState(SliderState.DRAG_HANDLE_DRAGGING);
                    break;
                } else if (i2 == 2) {
                    setState(SliderState.DRAG_HANDLE_RELEASED_FROM_TOUCH);
                    break;
                }
                break;
            case 8:
                int i3 = WhenMappings.$EnumSwitchMapping$1[sliderEvent.type.ordinal()];
                if (i3 == 1) {
                    setState(SliderState.DRAG_HANDLE_DRAGGING);
                    break;
                } else if (i3 == 2) {
                    setState(SliderState.DRAG_HANDLE_RELEASED_FROM_TOUCH);
                    break;
                }
                break;
            case 9:
                int i4 = WhenMappings.$EnumSwitchMapping$1[sliderEvent.type.ordinal()];
                if (i4 != 3) {
                    sliderState = i4 != 4 ? i4 != 5 ? SliderState.ARROW_HANDLE_MOVED_ONCE : SliderState.IDLE : SliderState.ARROW_HANDLE_MOVES_CONTINUOUSLY;
                } else {
                    this.timerJob = BuildersKt.launch$default(coroutineScope, null, null, new SliderStateTracker$launchTimer$1(this, null), 3);
                    sliderState = SliderState.WAIT;
                }
                setState(sliderState);
                break;
            case 10:
                SliderEventType sliderEventType6 = sliderEvent.type;
                boolean bookendReached = bookendReached(sliderEvent.currentProgress);
                int i5 = WhenMappings.$EnumSwitchMapping$1[sliderEventType6.ordinal()];
                if (i5 != 3) {
                    sliderState2 = i5 != 4 ? i5 != 5 ? SliderState.ARROW_HANDLE_MOVES_CONTINUOUSLY : SliderState.IDLE : bookendReached ? SliderState.ARROW_HANDLE_REACHED_BOOKEND : SliderState.ARROW_HANDLE_MOVES_CONTINUOUSLY;
                } else {
                    this.timerJob = BuildersKt.launch$default(coroutineScope, null, null, new SliderStateTracker$launchTimer$1(this, null), 3);
                    sliderState2 = SliderState.WAIT;
                }
                setState(sliderState2);
                break;
            case 11:
                setState(SliderState.IDLE);
                break;
        }
        this.latestProgress = sliderEvent.currentProgress;
        return Unit.INSTANCE;
    }

    public final void setState(SliderState sliderState) {
        this.currentState = sliderState;
    }

    public SliderStateTracker(SliderStateListener sliderStateListener, SliderEventProducer sliderEventProducer, CoroutineScope coroutineScope, SeekableSliderTrackerConfig seekableSliderTrackerConfig) {
        super(coroutineScope, sliderStateListener, sliderEventProducer, null);
        this.config = seekableSliderTrackerConfig;
    }
}
