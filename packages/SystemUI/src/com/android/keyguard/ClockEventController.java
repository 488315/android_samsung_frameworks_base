package com.android.keyguard;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Trace;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ClockEventController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.core.MessageBuffer;
import com.android.systemui.modes.shared.ModesUi;
import com.android.systemui.plugins.clocks.AlarmData;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockEventListener;
import com.android.systemui.plugins.clocks.ClockEvents;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ClockMessageBuffers;
import com.android.systemui.plugins.clocks.ClockTickRate;
import com.android.systemui.plugins.clocks.ThemeConfig;
import com.android.systemui.plugins.clocks.VRectF;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.plugins.clocks.ZenData;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.util.annotations.DeprecatedSysuiVisibleForTesting;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ClockEventController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlarmData alarmData;
    public final ClockEventController$batteryCallback$1 batteryCallback;
    public final BatteryController batteryController;
    public final Executor bgExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public ClockController clock;
    public final ClockEventController$clockListener$1 clockListener;
    public final ClockEventController$configListener$1 configListener;
    public final ConfigurationController configurationController;
    public final Context context;
    public RepeatWhenAttachedKt$repeatWhenAttached$1 disposableHandle;
    public final StateFlowImpl dozeAmount;
    public final FeatureFlagsClassic featureFlags;
    public boolean isCharging;
    public boolean isKeyguardVisible;
    public boolean isRegistered;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ClockEventController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public ClockEventController$connectClock$10 largeClockOnAttachStateChangeListener;
    public boolean largeClockOnSecondaryDisplay;
    public TimeListener largeTimeListener;
    public final ClockEventController$localeBroadcastReceiver$1 localeBroadcastReceiver;
    public final List loggers;
    public final DelayableExecutor mainExecutor;
    public final StateFlowImpl onClockBoundsChanged;
    public ClockEventController$connectClock$9$onViewAttachedToWindow$1$1 onGlobalLayoutListener;
    public final Resources resources;
    public ViewGroup smallClockFrame;
    public ClockEventController$connectClock$9 smallClockOnAttachStateChangeListener;
    public TimeListener smallTimeListener;
    public final UserTracker userTracker;
    public WeatherData weatherData;
    public ZenData zenData;
    public final ClockEventController$zenModeCallback$1 zenModeCallback;
    public final ZenModeController zenModeController;
    public final ZenModeInteractor zenModeInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TimeListener {
        public final ClockFaceController clockFace;
        public final DelayableExecutor executor;
        public boolean isRunning;
        public final ClockEventController$TimeListener$predrawListener$1 predrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.keyguard.ClockEventController$TimeListener$predrawListener$1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                ClockEventController.TimeListener.this.clockFace.getEvents().onTimeTick();
                return true;
            }
        };
        public final ClockEventController$TimeListener$secondsRunnable$1 secondsRunnable = new Runnable() { // from class: com.android.keyguard.ClockEventController$TimeListener$secondsRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                ClockEventController.TimeListener timeListener = ClockEventController.TimeListener.this;
                if (timeListener.isRunning) {
                    timeListener.executor.executeDelayed(this, 990L);
                    ClockEventController.TimeListener.this.clockFace.getEvents().onTimeTick();
                }
            }
        };

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ClockTickRate.values().length];
                try {
                    iArr[ClockTickRate.PER_MINUTE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ClockTickRate.PER_SECOND.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ClockTickRate.PER_FRAME.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.ClockEventController$TimeListener$predrawListener$1] */
        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.ClockEventController$TimeListener$secondsRunnable$1] */
        public TimeListener(ClockFaceController clockFaceController, DelayableExecutor delayableExecutor) {
            this.clockFace = clockFaceController;
            this.executor = delayableExecutor;
        }

        public final void stop() {
            if (this.isRunning) {
                this.isRunning = false;
                this.clockFace.getView().getViewTreeObserver().removeOnPreDrawListener(this.predrawListener);
            }
        }

        public final void update(boolean z) {
            if (!z) {
                stop();
                return;
            }
            if (this.isRunning) {
                return;
            }
            this.isRunning = true;
            ClockFaceController clockFaceController = this.clockFace;
            int i = WhenMappings.$EnumSwitchMapping$0[clockFaceController.getConfig().getTickRate().ordinal()];
            if (i != 1) {
                if (i == 2) {
                    this.executor.execute(this.secondsRunnable);
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    clockFaceController.getView().getViewTreeObserver().addOnPreDrawListener(this.predrawListener);
                    clockFaceController.getView().invalidate();
                }
            }
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v14, types: [com.android.keyguard.ClockEventController$clockListener$1] */
    /* JADX WARN: Type inference failed for: r1v15, types: [com.android.keyguard.ClockEventController$configListener$1] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.keyguard.ClockEventController$batteryCallback$1] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.keyguard.ClockEventController$localeBroadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.keyguard.ClockEventController$keyguardUpdateMonitorCallback$1] */
    public ClockEventController(KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, BroadcastDispatcher broadcastDispatcher, BatteryController batteryController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, Resources resources, Context context, DelayableExecutor delayableExecutor, Executor executor, ClockMessageBuffers clockMessageBuffers, FeatureFlagsClassic featureFlagsClassic, ZenModeController zenModeController, ZenModeInteractor zenModeInteractor, UserTracker userTracker) {
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.broadcastDispatcher = broadcastDispatcher;
        this.batteryController = batteryController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.configurationController = configurationController;
        this.resources = resources;
        this.context = context;
        this.mainExecutor = delayableExecutor;
        this.bgExecutor = executor;
        this.featureFlags = featureFlagsClassic;
        this.zenModeController = zenModeController;
        this.zenModeInteractor = zenModeInteractor;
        this.userTracker = userTracker;
        List asList = Arrays.asList(clockMessageBuffers.getInfraMessageBuffer(), clockMessageBuffers.getSmallClockMessageBuffer(), clockMessageBuffers.getLargeClockMessageBuffer());
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(asList, 10));
        Iterator it = asList.iterator();
        while (it.hasNext()) {
            arrayList.add(new Logger((MessageBuffer) it.next(), "ClockEventController"));
        }
        this.loggers = arrayList;
        FeatureFlagsClassic featureFlagsClassic2 = this.featureFlags;
        Flags.INSTANCE.getClass();
        featureFlagsClassic2.getClass();
        this.dozeAmount = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this.onClockBoundsChanged = StateFlowKt.MutableStateFlow(VRectF.m2809boximpl(VRectF.Companion.m2833getZERO3Hl7r_E()));
        this.clockListener = new ClockEventListener() { // from class: com.android.keyguard.ClockEventController$clockListener$1
            @Override // com.android.systemui.plugins.clocks.ClockEventListener
            /* renamed from: onBoundsChanged-TTAm5xc, reason: not valid java name */
            public final void mo945onBoundsChangedTTAm5xc(long j) {
                ClockEventController.this.onClockBoundsChanged.setValue(VRectF.m2809boximpl(j));
            }
        };
        this.configListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.ClockEventController$configListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ClockEventController.this.updateFontSizes();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                int i = ClockEventController.$r8$clinit;
                ClockEventController.this.updateColors();
            }
        };
        this.batteryCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.keyguard.ClockEventController$batteryCallback$1
            @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
                ClockController clockController;
                ClockEventController clockEventController = ClockEventController.this;
                if (clockEventController.isKeyguardVisible && !clockEventController.isCharging && z2 && (clockController = clockEventController.clock) != null) {
                    clockController.getSmallClock().getAnimations().charge();
                    clockController.getLargeClock().getAnimations().charge();
                }
                clockEventController.isCharging = z2;
            }
        };
        this.localeBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.ClockEventController$localeBroadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                ClockController clockController = ClockEventController.this.clock;
                if (clockController != null) {
                    clockController.getEvents().onLocaleChanged(Locale.getDefault());
                }
            }
        };
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.ClockEventController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                ClockEventController clockEventController = ClockEventController.this;
                clockEventController.isKeyguardVisible = z;
                if (z) {
                    refreshTime();
                }
                ClockEventController.TimeListener timeListener = clockEventController.smallTimeListener;
                if (timeListener != null) {
                    timeListener.update(clockEventController.getShouldTimeListenerRun());
                }
                ClockEventController.TimeListener timeListener2 = clockEventController.largeTimeListener;
                if (timeListener2 != null) {
                    timeListener2.update(clockEventController.getShouldTimeListenerRun());
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTimeChanged() {
                refreshTime();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTimeFormatChanged(String str) {
                boolean is24HourFormat;
                ClockEventController clockEventController = ClockEventController.this;
                ClockController clockController = clockEventController.clock;
                if (clockController != null) {
                    ClockEvents events = clockController.getEvents();
                    is24HourFormat = DateFormat.is24HourFormat(clockEventController.context, ((UserTrackerImpl) clockEventController.userTracker).getUserId());
                    events.onTimeFormatChanged(is24HourFormat);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTimeZoneChanged(TimeZone timeZone) {
                ClockController clockController = ClockEventController.this.clock;
                if (clockController != null) {
                    clockController.getEvents().onTimeZoneChanged(timeZone);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                ClockEventController clockEventController = ClockEventController.this;
                ClockController clockController = clockEventController.clock;
                if (clockController != null) {
                    clockController.getEvents().onTimeFormatChanged(DateFormat.is24HourFormat(clockEventController.context, i));
                }
                clockEventController.zenModeCallback.onNextAlarmChanged();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onWeatherDataChanged(WeatherData weatherData) {
                ClockEventController clockEventController = ClockEventController.this;
                clockEventController.weatherData = weatherData;
                ClockController clockController = clockEventController.clock;
                if (clockController != null) {
                    clockController.getEvents().onWeatherDataChanged(weatherData);
                }
            }

            public final void refreshTime() {
                ClockFaceController largeClock;
                ClockFaceEvents events;
                ClockFaceController smallClock;
                ClockFaceEvents events2;
                ClockEventController clockEventController = ClockEventController.this;
                ClockController clockController = clockEventController.clock;
                if (clockController != null && (smallClock = clockController.getSmallClock()) != null && (events2 = smallClock.getEvents()) != null) {
                    events2.onTimeTick();
                }
                ClockController clockController2 = clockEventController.clock;
                if (clockController2 == null || (largeClock = clockController2.getLargeClock()) == null || (events = largeClock.getEvents()) == null) {
                    return;
                }
                events.onTimeTick();
            }
        };
        this.zenModeCallback = new ClockEventController$zenModeCallback$1(this);
    }

    public static final void access$handleZenMode(final ClockEventController clockEventController, int i) {
        clockEventController.getClass();
        ZenData.ZenMode fromInt = ZenData.ZenMode.Companion.fromInt(i);
        if (fromInt == null) {
            ClockEventController$$ExternalSyntheticOutline0.m(i, "Failed to get zen mode from int: ", "ClockEventController");
            return;
        }
        final ZenData zenData = new ZenData(fromInt, fromInt == ZenData.ZenMode.OFF ? "dnd_is_off" : "dnd_is_on");
        clockEventController.mainExecutor.execute(new Runnable() { // from class: com.android.keyguard.ClockEventController$handleZenMode$1$1
            @Override // java.lang.Runnable
            public final void run() {
                ClockController clockController = ClockEventController.this.clock;
                if (clockController != null) {
                    clockController.getEvents().onZenDataChanged(zenData);
                }
            }
        });
        clockEventController.zenData = zenData;
    }

    public final boolean getShouldTimeListenerRun() {
        return this.isKeyguardVisible && ((Number) this.dozeAmount.getValue()).floatValue() < 0.99f;
    }

    public final void handleDoze(float f) {
        ClockController clockController = this.clock;
        if (clockController != null) {
            Trace.beginSection("ClockEventController#smallClock.animations.doze");
            clockController.getSmallClock().getAnimations().doze(f);
            Trace.endSection();
            Trace.beginSection("ClockEventController#largeClock.animations.doze");
            clockController.getLargeClock().getAnimations().doze(f);
            Trace.endSection();
        }
        TimeListener timeListener = this.smallTimeListener;
        if (timeListener != null) {
            timeListener.update(f < 0.99f);
        }
        TimeListener timeListener2 = this.largeTimeListener;
        if (timeListener2 != null) {
            timeListener2.update(f < 0.99f);
        }
        this.dozeAmount.updateState(null, Float.valueOf(f));
    }

    @DeprecatedSysuiVisibleForTesting
    public final Job listenForAnyStateToAodTransition(CoroutineScope coroutineScope) {
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new ClockEventController$listenForAnyStateToAodTransition$1(this, null), 7);
    }

    @DeprecatedSysuiVisibleForTesting
    public final Job listenForAnyStateToDozingTransition(CoroutineScope coroutineScope) {
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new ClockEventController$listenForAnyStateToDozingTransition$1(this, null), 7);
    }

    @DeprecatedSysuiVisibleForTesting
    public final Job listenForAnyStateToLockscreenTransition(CoroutineScope coroutineScope) {
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new ClockEventController$listenForAnyStateToLockscreenTransition$1(this, null), 7);
    }

    @DeprecatedSysuiVisibleForTesting
    public final Job listenForDnd(CoroutineScope coroutineScope) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = ModesUi.$r8$clinit;
        throw new IllegalStateException("New code path not supported when android.app.modes_ui is disabled.");
    }

    @DeprecatedSysuiVisibleForTesting
    public final Job listenForDozeAmountTransition(CoroutineScope coroutineScope) {
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new ClockEventController$listenForDozeAmountTransition$1(this, null), 7);
    }

    public final void registerListeners(View view) {
        if (this.isRegistered) {
            return;
        }
        this.isRegistered = true;
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.localeBroadcastReceiver, new IntentFilter("android.intent.action.LOCALE_CHANGED"), null, null, 0, null, 60);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configListener);
        ((BatteryControllerImpl) this.batteryController).addCallback(this.batteryCallback);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        ((ZenModeControllerImpl) this.zenModeController).addCallback(this.zenModeCallback);
        ClockEventController$registerListeners$1 clockEventController$registerListeners$1 = new ClockEventController$registerListeners$1(this, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        this.disposableHandle = RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, clockEventController$registerListeners$1);
        TimeListener timeListener = this.smallTimeListener;
        if (timeListener != null) {
            timeListener.update(getShouldTimeListenerRun());
        }
        TimeListener timeListener2 = this.largeTimeListener;
        if (timeListener2 != null) {
            timeListener2.update(getShouldTimeListenerRun());
        }
        this.bgExecutor.execute(new Runnable() { // from class: com.android.keyguard.ClockEventController$registerListeners$2
            @Override // java.lang.Runnable
            public final void run() {
                ClockEventController clockEventController = ClockEventController.this;
                clockEventController.zenModeCallback.onZenChanged(((ZenModeControllerImpl) clockEventController.zenModeController).mZenMode);
                ClockEventController.this.zenModeCallback.onNextAlarmChanged();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v17, types: [com.android.keyguard.ClockEventController$connectClock$10] */
    public final void setClock(final ClockController clockController) {
        ViewTreeObserver viewTreeObserver;
        ClockController clockController2 = this.clock;
        if (clockController2 != null) {
            ClockEventController$connectClock$9 clockEventController$connectClock$9 = this.smallClockOnAttachStateChangeListener;
            if (clockEventController$connectClock$9 != null) {
                clockController2.getSmallClock().getView().removeOnAttachStateChangeListener(clockEventController$connectClock$9);
                ViewGroup viewGroup = this.smallClockFrame;
                if (viewGroup != null && (viewTreeObserver = viewGroup.getViewTreeObserver()) != null) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
                }
            }
            ClockEventController$connectClock$10 clockEventController$connectClock$10 = this.largeClockOnAttachStateChangeListener;
            if (clockEventController$connectClock$10 != null) {
                clockController2.getLargeClock().getView().removeOnAttachStateChangeListener(clockEventController$connectClock$10);
            }
        }
        this.clock = clockController;
        if (clockController == null) {
            return;
        }
        String obj = clockController.toString();
        ArrayList arrayList = (ArrayList) this.loggers;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            Logger logger = (Logger) obj2;
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new ClockEventController$$ExternalSyntheticLambda1(), null);
            obtain.setStr1(obj);
            logger.getBuffer().commit(obtain);
        }
        TypedValue typedValue = new TypedValue();
        this.context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        clockController.initialize(typedValue.data == 0, ((Number) this.dozeAmount.getValue()).floatValue(), 0.0f, this.clockListener);
        updateColors();
        updateFontSizes();
        TimeListener timeListener = this.smallTimeListener;
        if (timeListener != null) {
            timeListener.stop();
        }
        TimeListener timeListener2 = this.largeTimeListener;
        if (timeListener2 != null) {
            timeListener2.stop();
        }
        this.smallTimeListener = null;
        this.largeTimeListener = null;
        ClockController clockController3 = this.clock;
        if (clockController3 != null) {
            ClockFaceController smallClock = clockController3.getSmallClock();
            DelayableExecutor delayableExecutor = this.mainExecutor;
            TimeListener timeListener3 = new TimeListener(smallClock, delayableExecutor);
            timeListener3.update(getShouldTimeListenerRun());
            this.smallTimeListener = timeListener3;
            TimeListener timeListener4 = new TimeListener(clockController3.getLargeClock(), delayableExecutor);
            timeListener4.update(getShouldTimeListenerRun());
            this.largeTimeListener = timeListener4;
        }
        WeatherData weatherData = this.weatherData;
        if (weatherData != null) {
            Log.i("ClockEventController", "Pushing cached weather data to new clock: " + weatherData);
            clockController.getEvents().onWeatherDataChanged(weatherData);
        }
        ZenData zenData = this.zenData;
        if (zenData != null) {
            clockController.getEvents().onZenDataChanged(zenData);
        }
        AlarmData alarmData = this.alarmData;
        if (alarmData != null) {
            clockController.getEvents().onAlarmDataChanged(alarmData);
        }
        this.smallClockOnAttachStateChangeListener = new ClockEventController$connectClock$9(clockController, this);
        clockController.getSmallClock().getView().addOnAttachStateChangeListener(this.smallClockOnAttachStateChangeListener);
        this.largeClockOnAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.keyguard.ClockEventController$connectClock$10
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                boolean is24HourFormat;
                ClockEvents events = ClockController.this.getEvents();
                is24HourFormat = DateFormat.is24HourFormat(r0.context, ((UserTrackerImpl) this.userTracker).getUserId());
                events.onTimeFormatChanged(is24HourFormat);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        };
        clockController.getLargeClock().getView().addOnAttachStateChangeListener(this.largeClockOnAttachStateChangeListener);
    }

    public final void updateColors() {
        TypedValue typedValue = new TypedValue();
        this.context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        boolean z = typedValue.data == 0;
        ClockController clockController = this.clock;
        if (clockController != null) {
            Log.i("ClockEventController", "isThemeDark: " + z);
            clockController.getSmallClock().getEvents().onThemeChanged(ThemeConfig.copy$default(clockController.getSmallClock().getTheme(), z, null, 2, null));
            clockController.getLargeClock().getEvents().onThemeChanged(ThemeConfig.copy$default(clockController.getLargeClock().getTheme(), z, null, 2, null));
        }
    }

    public final void updateFontSizes() {
        ClockController clockController = this.clock;
        if (clockController != null) {
            clockController.getSmallClock().getEvents().onFontSettingChanged(this.resources.getDimensionPixelSize(R$dimen.small_clock_text_size));
            clockController.getLargeClock().getEvents().onFontSettingChanged(this.largeClockOnSecondaryDisplay ? this.resources.getDimensionPixelSize(R$dimen.presentation_clock_text_size) : this.resources.getDimensionPixelSize(R$dimen.large_clock_text_size));
        }
    }

    public static /* synthetic */ void getLargeClockOnAttachStateChangeListener$annotations() {
    }

    public static /* synthetic */ void getSmallClockOnAttachStateChangeListener$annotations() {
    }
}
