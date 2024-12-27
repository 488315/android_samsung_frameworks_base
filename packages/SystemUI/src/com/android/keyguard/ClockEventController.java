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
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.core.MessageBuffer;
import com.android.systemui.plugins.clocks.AlarmData;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockMessageBuffers;
import com.android.systemui.plugins.clocks.ClockTickRate;
import com.android.systemui.plugins.clocks.WeatherData;
import com.android.systemui.plugins.clocks.ZenData;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class ClockEventController {
    public AlarmData alarmData;
    public final ClockEventController$batteryCallback$1 batteryCallback;
    public final BatteryController batteryController;
    public final Executor bgExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public ClockController clock;
    public final ClockEventController$configListener$1 configListener;
    public final ConfigurationController configurationController;
    public final Context context;
    public RepeatWhenAttachedKt$repeatWhenAttached$1 disposableHandle;
    public float dozeAmount;
    public final FeatureFlagsClassic featureFlags;
    public boolean isCharging;
    public boolean isDozing;
    public boolean isKeyguardVisible;
    public boolean isRegistered;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ClockEventController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public ClockEventController$connectClock$10 largeClockOnAttachStateChangeListener;
    public boolean largeClockOnSecondaryDisplay;
    public TimeListener largeTimeListener;
    public final ClockEventController$localeBroadcastReceiver$1 localeBroadcastReceiver;
    public final List loggers;
    public final DelayableExecutor mainExecutor;
    public ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    public final Resources resources;
    public ViewGroup smallClockFrame;
    public ClockEventController$connectClock$9 smallClockOnAttachStateChangeListener;
    public TimeListener smallTimeListener;
    public WeatherData weatherData;
    public ZenData zenData;
    public final ClockEventController$zenModeCallback$1 zenModeCallback;
    public final ZenModeController zenModeController;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

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
            if (i == 2) {
                this.executor.execute(this.secondsRunnable);
            } else {
                if (i != 3) {
                    return;
                }
                clockFaceController.getView().getViewTreeObserver().addOnPreDrawListener(this.predrawListener);
                clockFaceController.getView().invalidate();
            }
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.keyguard.ClockEventController$keyguardUpdateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.keyguard.ClockEventController$configListener$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.keyguard.ClockEventController$batteryCallback$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.keyguard.ClockEventController$localeBroadcastReceiver$1] */
    public ClockEventController(KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, BroadcastDispatcher broadcastDispatcher, BatteryController batteryController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, Resources resources, Context context, DelayableExecutor delayableExecutor, Executor executor, ClockMessageBuffers clockMessageBuffers, FeatureFlagsClassic featureFlagsClassic, ZenModeController zenModeController) {
        this.keyguardInteractor = keyguardInteractor;
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
        List listOf = CollectionsKt__CollectionsKt.listOf(clockMessageBuffers.getInfraMessageBuffer(), clockMessageBuffers.getSmallClockMessageBuffer(), clockMessageBuffers.getLargeClockMessageBuffer());
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
        Iterator it = listOf.iterator();
        while (it.hasNext()) {
            arrayList.add(new Logger((MessageBuffer) it.next(), "ClockEventController"));
        }
        this.loggers = arrayList;
        FeatureFlagsClassic featureFlagsClassic2 = this.featureFlags;
        Flags.INSTANCE.getClass();
        featureFlagsClassic2.getClass();
        this.configListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.ClockEventController$configListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ClockEventController.this.updateFontSizes();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                ClockEventController clockEventController = ClockEventController.this;
                ClockController clockController = clockEventController.clock;
                if (clockController != null) {
                    clockController.getEvents().onColorPaletteChanged(clockEventController.resources);
                }
                clockEventController.updateColors();
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
                ClockController clockController;
                ClockEventController clockEventController = ClockEventController.this;
                clockEventController.isKeyguardVisible = z;
                com.android.systemui.Flags.migrateClocksToBlueprint();
                if (!clockEventController.isKeyguardVisible && (clockController = clockEventController.clock) != null) {
                    clockController.getSmallClock().getAnimations().doze(clockEventController.isDozing ? 1.0f : 0.0f);
                    clockController.getLargeClock().getAnimations().doze(clockEventController.isDozing ? 1.0f : 0.0f);
                }
                if (z) {
                    com.android.systemui.Flags.migrateClocksToBlueprint();
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
                com.android.systemui.Flags.migrateClocksToBlueprint();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTimeFormatChanged(String str) {
                ClockEventController clockEventController = ClockEventController.this;
                ClockController clockController = clockEventController.clock;
                if (clockController != null) {
                    clockController.getEvents().onTimeFormatChanged(DateFormat.is24HourFormat(clockEventController.context));
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
                    clockController.getEvents().onTimeFormatChanged(DateFormat.is24HourFormat(clockEventController.context));
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
        };
        this.zenModeCallback = new ClockEventController$zenModeCallback$1(this);
    }

    public static final void access$handleDoze(ClockEventController clockEventController, float f) {
        clockEventController.dozeAmount = f;
        ClockController clockController = clockEventController.clock;
        if (clockController != null) {
            Trace.beginSection("ClockEventController#smallClock.animations.doze");
            clockController.getSmallClock().getAnimations().doze(clockEventController.dozeAmount);
            Trace.endSection();
            Trace.beginSection("ClockEventController#largeClock.animations.doze");
            clockController.getLargeClock().getAnimations().doze(clockEventController.dozeAmount);
            Trace.endSection();
        }
        TimeListener timeListener = clockEventController.smallTimeListener;
        if (timeListener != null) {
            timeListener.update(f < 0.99f);
        }
        TimeListener timeListener2 = clockEventController.largeTimeListener;
        if (timeListener2 != null) {
            timeListener2.update(f < 0.99f);
        }
    }

    public final boolean getShouldTimeListenerRun() {
        return this.isKeyguardVisible && this.dozeAmount < 0.99f;
    }

    public final Job listenForAnyStateToAodTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForAnyStateToAodTransition$1(this, null), 3);
    }

    public final Job listenForAnyStateToDozingTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForAnyStateToDozingTransition$1(this, null), 3);
    }

    public final Job listenForAnyStateToLockscreenTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForAnyStateToLockscreenTransition$1(this, null), 3);
    }

    public final Job listenForDozeAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForDozeAmount$1(this, null), 3);
    }

    public final Job listenForDozeAmountTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForDozeAmountTransition$1(this, null), 3);
    }

    public final Job listenForDozing$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForDozing$1(this, null), 3);
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

    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.keyguard.ClockEventController$connectClock$10] */
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
        Iterator it = ((ArrayList) this.loggers).iterator();
        while (it.hasNext()) {
            Logger logger = (Logger) it.next();
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new Function1() { // from class: com.android.keyguard.ClockEventController$connectClock$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("New Clock: ", ((LogMessage) obj2).getStr1());
                }
            }, null);
            obtain.setStr1(obj);
            logger.getBuffer().commit(obtain);
        }
        clockController.initialize(this.resources, this.dozeAmount, 0.0f);
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
                ClockController.this.getEvents().onTimeFormatChanged(DateFormat.is24HourFormat(this.context));
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        };
        clockController.getLargeClock().getView().addOnAttachStateChangeListener(this.largeClockOnAttachStateChangeListener);
    }

    public final void unregisterListeners() {
        ViewTreeObserver viewTreeObserver;
        if (this.isRegistered) {
            this.isRegistered = false;
            RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttachedKt$repeatWhenAttached$1 = this.disposableHandle;
            if (repeatWhenAttachedKt$repeatWhenAttached$1 != null) {
                repeatWhenAttachedKt$repeatWhenAttached$1.dispose();
            }
            this.broadcastDispatcher.unregisterReceiver(this.localeBroadcastReceiver);
            ((ConfigurationControllerImpl) this.configurationController).removeCallback(this.configListener);
            ((BatteryControllerImpl) this.batteryController).removeCallback(this.batteryCallback);
            this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
            ((ZenModeControllerImpl) this.zenModeController).removeCallback(this.zenModeCallback);
            TimeListener timeListener = this.smallTimeListener;
            if (timeListener != null) {
                timeListener.stop();
            }
            TimeListener timeListener2 = this.largeTimeListener;
            if (timeListener2 != null) {
                timeListener2.stop();
            }
            ClockController clockController = this.clock;
            if (clockController != null) {
                clockController.getSmallClock().getView().removeOnAttachStateChangeListener(this.smallClockOnAttachStateChangeListener);
                clockController.getLargeClock().getView().removeOnAttachStateChangeListener(this.largeClockOnAttachStateChangeListener);
            }
            ViewGroup viewGroup = this.smallClockFrame;
            if (viewGroup == null || (viewTreeObserver = viewGroup.getViewTreeObserver()) == null) {
                return;
            }
            viewTreeObserver.removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
        }
    }

    public final void updateColors() {
        TypedValue typedValue = new TypedValue();
        this.context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        boolean z = typedValue.data == 0;
        ClockController clockController = this.clock;
        if (clockController != null) {
            Log.i("ClockEventController", "Region isDark: " + z);
            clockController.getSmallClock().getEvents().onRegionDarknessChanged(z);
            clockController.getLargeClock().getEvents().onRegionDarknessChanged(z);
        }
    }

    public final void updateFontSizes() {
        ClockController clockController = this.clock;
        if (clockController != null) {
            clockController.getSmallClock().getEvents().onFontSettingChanged(this.resources.getDimensionPixelSize(com.android.systemui.R.dimen.small_clock_text_size));
            clockController.getLargeClock().getEvents().onFontSettingChanged(this.largeClockOnSecondaryDisplay ? this.resources.getDimensionPixelSize(com.android.systemui.R.dimen.presentation_clock_text_size) : this.resources.getDimensionPixelSize(com.android.systemui.R.dimen.large_clock_text_size));
        }
    }

    public static /* synthetic */ void getLargeClockOnAttachStateChangeListener$annotations() {
    }

    public static /* synthetic */ void getSmallClockOnAttachStateChangeListener$annotations() {
    }
}
