package com.android.keyguard;

import android.R;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import com.android.keyguard.ClockEventController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.ClockFaceController;
import com.android.systemui.plugins.ClockFaceEvents;
import com.android.systemui.plugins.ClockTickRate;
import com.android.systemui.plugins.WeatherData;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClockEventController {
    public static final float DOZE_TICKRATE_THRESHOLD;
    public static final String TAG;
    public final ClockEventController$batteryCallback$1 batteryCallback;
    public final BatteryController batteryController;
    public final Executor bgExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public WeatherData cachedWeatherData;
    public ClockController clock;
    public final ClockEventController$configListener$1 configListener;
    public final ConfigurationController configurationController;
    public final Context context;
    public RepeatWhenAttachedKt$repeatWhenAttached$1 disposableHandle;
    public float dozeAmount;
    public final FeatureFlags featureFlags;
    public boolean isCharging;
    public boolean isDozing;
    public boolean isKeyguardVisible;
    public boolean isRegistered;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ClockEventController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public boolean largeClockIsDark;
    public final LogBuffer largeLogBuffer;
    public TimeListener largeTimeListener;
    public final ClockEventController$localeBroadcastReceiver$1 localeBroadcastReceiver;
    public final DelayableExecutor mainExecutor;
    public final Resources resources;
    public boolean smallClockIsDark;
    public final LogBuffer smallLogBuffer;
    public TimeListener smallTimeListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                    timeListener.executor.executeDelayed(990L, this);
                    ClockEventController.TimeListener.this.clockFace.getEvents().onTimeTick();
                }
            }
        };

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                ((ExecutorImpl) this.executor).execute(this.secondsRunnable);
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
        String simpleName = Reflection.getOrCreateKotlinClass(ClockEventController.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
        DOZE_TICKRATE_THRESHOLD = 0.99f;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.ClockEventController$configListener$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.ClockEventController$batteryCallback$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.keyguard.ClockEventController$localeBroadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.keyguard.ClockEventController$keyguardUpdateMonitorCallback$1] */
    public ClockEventController(KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, BroadcastDispatcher broadcastDispatcher, BatteryController batteryController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, Resources resources, Context context, DelayableExecutor delayableExecutor, Executor executor, LogBuffer logBuffer, LogBuffer logBuffer2, FeatureFlags featureFlags) {
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
        this.smallLogBuffer = logBuffer;
        this.largeLogBuffer = logBuffer2;
        this.featureFlags = featureFlags;
        Flags.INSTANCE.getClass();
        this.smallClockIsDark = true;
        this.largeClockIsDark = true;
        this.configListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.ClockEventController$configListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                String str = ClockEventController.TAG;
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
                FeatureFlags featureFlags2 = clockEventController.featureFlags;
                Flags flags = Flags.INSTANCE;
                featureFlags2.getClass();
                if (!clockEventController.isKeyguardVisible && (clockController = clockEventController.clock) != null) {
                    clockController.getSmallClock().getAnimations().doze(clockEventController.isDozing ? 1.0f : 0.0f);
                    clockController.getLargeClock().getAnimations().doze(clockEventController.isDozing ? 1.0f : 0.0f);
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
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onWeatherDataChanged(WeatherData weatherData) {
                ClockEventController clockEventController = ClockEventController.this;
                clockEventController.cachedWeatherData = weatherData;
                ClockController clockController = clockEventController.clock;
                if (clockController != null) {
                    clockController.getEvents().onWeatherDataChanged(weatherData);
                }
            }
        };
    }

    public static final void access$handleDoze(ClockEventController clockEventController, float f) {
        clockEventController.dozeAmount = f;
        ClockController clockController = clockEventController.clock;
        if (clockController != null) {
            clockController.getSmallClock().getAnimations().doze(clockEventController.dozeAmount);
            clockController.getLargeClock().getAnimations().doze(clockEventController.dozeAmount);
        }
        TimeListener timeListener = clockEventController.smallTimeListener;
        float f2 = DOZE_TICKRATE_THRESHOLD;
        if (timeListener != null) {
            timeListener.update(f < f2);
        }
        TimeListener timeListener2 = clockEventController.largeTimeListener;
        if (timeListener2 != null) {
            timeListener2.update(f < f2);
        }
    }

    public final boolean getShouldTimeListenerRun() {
        return this.isKeyguardVisible && this.dozeAmount < DOZE_TICKRATE_THRESHOLD;
    }

    /* renamed from: listenForAnyStateToAodTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final Job m54x3c9a7d4f(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForAnyStateToAodTransition$1(this, null), 3);
    }

    /* renamed from: listenForDozeAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final Job m55xe30ca956(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForDozeAmount$1(this, null), 3);
    }

    /* renamed from: listenForDozeAmountTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final Job m56xb36d344b(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForDozeAmountTransition$1(this, null), 3);
    }

    /* renamed from: listenForDozing$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final Job m57xde5cc31b(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new ClockEventController$listenForDozing$1(this, null), 3);
    }

    public final void registerListeners(View view) {
        RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttached;
        if (this.isRegistered) {
            return;
        }
        this.isRegistered = true;
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.localeBroadcastReceiver, new IntentFilter("android.intent.action.LOCALE_CHANGED"), null, null, 0, null, 60);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configListener);
        ((BatteryControllerImpl) this.batteryController).addCallback(this.batteryCallback);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        repeatWhenAttached = RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new ClockEventController$registerListeners$1(this, null));
        this.disposableHandle = repeatWhenAttached;
        TimeListener timeListener = this.smallTimeListener;
        if (timeListener != null) {
            timeListener.update(getShouldTimeListenerRun());
        }
        TimeListener timeListener2 = this.largeTimeListener;
        if (timeListener2 != null) {
            timeListener2.update(getShouldTimeListenerRun());
        }
    }

    public final void setClock(final ClockController clockController) {
        this.clock = clockController;
        if (clockController != null) {
            LogBuffer logBuffer = this.smallLogBuffer;
            String str = TAG;
            if (logBuffer != null) {
                logBuffer.commit(logBuffer.obtain(str, LogLevel.DEBUG, new Function1() { // from class: com.android.keyguard.ClockEventController$clock$2
                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return "New Clock";
                    }
                }, null));
            }
            clockController.getSmallClock().setLogBuffer(logBuffer);
            LogBuffer logBuffer2 = this.largeLogBuffer;
            if (logBuffer2 != null) {
                logBuffer2.commit(logBuffer2.obtain(str, LogLevel.DEBUG, new Function1() { // from class: com.android.keyguard.ClockEventController$clock$4
                    @Override // kotlin.jvm.functions.Function1
                    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return "New Clock";
                    }
                }, null));
            }
            clockController.getLargeClock().setLogBuffer(logBuffer2);
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
            ClockController clockController2 = this.clock;
            if (clockController2 != null) {
                ClockFaceController smallClock = clockController2.getSmallClock();
                DelayableExecutor delayableExecutor = this.mainExecutor;
                TimeListener timeListener3 = new TimeListener(smallClock, delayableExecutor);
                timeListener3.update(getShouldTimeListenerRun());
                this.smallTimeListener = timeListener3;
                TimeListener timeListener4 = new TimeListener(clockController2.getLargeClock(), delayableExecutor);
                timeListener4.update(getShouldTimeListenerRun());
                this.largeTimeListener = timeListener4;
            }
            WeatherData weatherData = this.cachedWeatherData;
            if (weatherData != null) {
                Log.i(str, "Pushing cached weather data to new clock: " + weatherData);
                clockController.getEvents().onWeatherDataChanged(weatherData);
            }
            clockController.getSmallClock().getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.keyguard.ClockEventController$clock$6
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    ClockController.this.getEvents().onTimeFormatChanged(DateFormat.is24HourFormat(this.context));
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                }
            });
            clockController.getLargeClock().getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.keyguard.ClockEventController$clock$7
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    ClockController.this.getEvents().onTimeFormatChanged(DateFormat.is24HourFormat(this.context));
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                }
            });
        }
    }

    public final void unregisterListeners() {
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
            TimeListener timeListener = this.smallTimeListener;
            if (timeListener != null) {
                timeListener.stop();
            }
            TimeListener timeListener2 = this.largeTimeListener;
            if (timeListener2 != null) {
                timeListener2.stop();
            }
        }
    }

    public final void updateColors() {
        Context context = this.context;
        WallpaperManager.getInstance(context);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        int i = typedValue.data;
        this.smallClockIsDark = i == 0;
        this.largeClockIsDark = i == 0;
        ClockController clockController = this.clock;
        if (clockController != null) {
            clockController.getSmallClock().getEvents().onRegionDarknessChanged(this.smallClockIsDark);
            clockController.getLargeClock().getEvents().onRegionDarknessChanged(this.largeClockIsDark);
        }
    }

    public final void updateFontSizes() {
        ClockController clockController = this.clock;
        if (clockController != null) {
            ClockFaceEvents events = clockController.getSmallClock().getEvents();
            Resources resources = this.resources;
            events.onFontSettingChanged(resources.getDimensionPixelSize(com.android.systemui.R.dimen.small_clock_text_size));
            clockController.getLargeClock().getEvents().onFontSettingChanged(resources.getDimensionPixelSize(com.android.systemui.R.dimen.large_clock_text_size));
        }
    }
}
