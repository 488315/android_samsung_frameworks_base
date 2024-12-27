package com.android.systemui.shared.clocks;

import android.app.ActivityManager;
import android.app.UserSwitchObserver;
import android.content.Context;
import android.database.ContentObserver;
import android.provider.Settings;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.LogcatOnlyMessageBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.core.MessageBuffer;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginLifecycleManager;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockMessageBuffers;
import com.android.systemui.plugins.clocks.ClockMetadata;
import com.android.systemui.plugins.clocks.ClockProvider;
import com.android.systemui.plugins.clocks.ClockProviderPlugin;
import com.android.systemui.plugins.clocks.ClockSettings;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.util.ThreadAssert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class ClockRegistry {
    public final String TAG;

    /* renamed from: assert, reason: not valid java name */
    public final ThreadAssert f74assert;
    public final ConcurrentHashMap availableClocks;
    public final CoroutineDispatcher bgDispatcher;
    public final ClockMessageBuffers clockBuffers;
    public final List clockChangeListeners;
    public final Context context;
    public final String fallbackClockId;
    public final boolean handleAllUsers;
    public final AtomicBoolean isClockChanged;
    public final AtomicBoolean isClockListChanged;
    public final boolean isEnabled;
    public final AtomicBoolean isQueued;
    public boolean isRegistered;
    public final boolean isTransitClockEnabled;
    public final boolean keepAllLoaded;
    public final Logger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final ClockRegistry$pluginListener$1 pluginListener;
    public final PluginManager pluginManager;
    public final CoroutineScope scope;
    public final ClockRegistry$settingObserver$1 settingObserver;
    public ClockSettings settings;
    public final ClockRegistry$userSwitchObserver$1 userSwitchObserver;

    public interface ClockChangeListener {
        void onCurrentClockChanged();
    }

    public final class ClockInfo {
        public final PluginLifecycleManager manager;
        public final ClockMetadata metadata;
        public ClockProvider provider;

        public ClockInfo(ClockMetadata clockMetadata, ClockProvider clockProvider, PluginLifecycleManager<ClockProviderPlugin> pluginLifecycleManager) {
            this.metadata = clockMetadata;
            this.provider = clockProvider;
            this.manager = pluginLifecycleManager;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClockInfo)) {
                return false;
            }
            ClockInfo clockInfo = (ClockInfo) obj;
            return Intrinsics.areEqual(this.metadata, clockInfo.metadata) && Intrinsics.areEqual(this.provider, clockInfo.provider) && Intrinsics.areEqual(this.manager, clockInfo.manager);
        }

        public final int hashCode() {
            int hashCode = this.metadata.hashCode() * 31;
            ClockProvider clockProvider = this.provider;
            int hashCode2 = (hashCode + (clockProvider == null ? 0 : clockProvider.hashCode())) * 31;
            PluginLifecycleManager pluginLifecycleManager = this.manager;
            return hashCode2 + (pluginLifecycleManager != null ? pluginLifecycleManager.hashCode() : 0);
        }

        public final String toString() {
            return "ClockInfo(metadata=" + this.metadata + ", provider=" + this.provider + ", manager=" + this.manager + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.shared.clocks.ClockRegistry$userSwitchObserver$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.shared.clocks.ClockRegistry$settingObserver$1] */
    public ClockRegistry(Context context, PluginManager pluginManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, boolean z, boolean z2, ClockProvider clockProvider, String str, ClockMessageBuffers clockMessageBuffers, boolean z3, String str2, boolean z4, ThreadAssert threadAssert) {
        MessageBuffer infraMessageBuffer;
        this.context = context;
        this.pluginManager = pluginManager;
        this.scope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.bgDispatcher = coroutineDispatcher2;
        this.isEnabled = z;
        this.handleAllUsers = z2;
        this.fallbackClockId = str;
        this.clockBuffers = clockMessageBuffers;
        this.keepAllLoaded = z3;
        this.isTransitClockEnabled = z4;
        this.f74assert = threadAssert;
        String str3 = Reflection.getOrCreateKotlinClass(ClockRegistry.class).getSimpleName() + " (" + str2 + ")";
        this.TAG = str3;
        this.logger = new Logger((clockMessageBuffers == null || (infraMessageBuffer = clockMessageBuffers.getInfraMessageBuffer()) == null) ? new LogcatOnlyMessageBuffer(LogLevel.DEBUG) : infraMessageBuffer, str3);
        this.availableClocks = new ConcurrentHashMap();
        this.clockChangeListeners = new ArrayList();
        this.settingObserver = new ContentObserver() { // from class: com.android.systemui.shared.clocks.ClockRegistry$settingObserver$1
            {
                super(null);
            }

            public final void onChange(boolean z5, Collection collection, int i, int i2) {
                ClockRegistry clockRegistry = ClockRegistry.this;
                BuildersKt.launch$default(clockRegistry.scope, clockRegistry.bgDispatcher, null, new ClockRegistry$settingObserver$1$onChange$1(clockRegistry, null), 2);
            }
        };
        this.pluginListener = new PluginListener() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.plugins.PluginListener
            public final boolean onPluginAttached(PluginLifecycleManager pluginLifecycleManager) {
                final ClockRegistry clockRegistry = ClockRegistry.this;
                pluginLifecycleManager.setLogFunc(new BiConsumer() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginAttached$1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        String str4 = (String) obj;
                        String str5 = (String) obj2;
                        ClockMessageBuffers clockMessageBuffers2 = ClockRegistry.this.clockBuffers;
                        LogBuffer logBuffer = (LogBuffer) (clockMessageBuffers2 != null ? clockMessageBuffers2.getInfraMessageBuffer() : null);
                        if (logBuffer != null) {
                            Intrinsics.checkNotNull(str4);
                            LogLevel logLevel = LogLevel.DEBUG;
                            Intrinsics.checkNotNull(str5);
                            logBuffer.log(str4, logLevel, str5, null);
                        }
                    }
                });
                if (clockRegistry.keepAllLoaded) {
                    return true;
                }
                List<ClockMetadata> list = (List) ClockRegistryKt.KNOWN_PLUGINS.get(pluginLifecycleManager.getPackage());
                Logger logger = clockRegistry.logger;
                if (list == null) {
                    LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginAttached$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Loading unrecognized clock package: ", ((LogMessage) obj).getStr1());
                        }
                    }, null);
                    obtain.setStr1(pluginLifecycleManager.getPackage());
                    logger.getBuffer().commit(obtain);
                    return true;
                }
                LogMessage obtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginAttached$4
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Skipping initial load of known clock package package: ", ((LogMessage) obj).getStr1());
                    }
                }, null);
                obtain2.setStr1(pluginLifecycleManager.getPackage());
                logger.getBuffer().commit(obtain2);
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                boolean z5 = false;
                for (ClockMetadata clockMetadata : list) {
                    z5 = z5 || Intrinsics.areEqual(clockRegistry.getCurrentClockId(), clockMetadata.getClockId());
                    String clockId = clockMetadata.getClockId();
                    ConcurrentHashMap concurrentHashMap = clockRegistry.availableClocks;
                    ClockRegistry.ClockInfo clockInfo = new ClockRegistry.ClockInfo(clockMetadata, null, pluginLifecycleManager);
                    Function1 function1 = new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginAttached$info$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            Ref$BooleanRef.this.element = true;
                            ClockRegistry.access$onConnected(clockRegistry, (ClockRegistry.ClockInfo) obj);
                            return Unit.INSTANCE;
                        }
                    };
                    Object putIfAbsent = concurrentHashMap.putIfAbsent(clockId, clockInfo);
                    if (putIfAbsent == 0) {
                        function1.invoke(clockInfo);
                    }
                    if (putIfAbsent != 0) {
                        clockInfo = putIfAbsent;
                    }
                    ClockRegistry.ClockInfo clockInfo2 = clockInfo;
                    PluginLifecycleManager pluginLifecycleManager2 = clockInfo2.manager;
                    if (pluginLifecycleManager.equals(pluginLifecycleManager2)) {
                        clockInfo2.provider = null;
                    } else {
                        LogMessage obtain3 = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginAttached$6
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                String str1 = logMessage.getStr1();
                                String str22 = logMessage.getStr2();
                                String str32 = logMessage.getStr3();
                                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Clock Id conflict on attach: ", str1, " is double registered by ", str22, " and ");
                                m.append(str32);
                                return m.toString();
                            }
                        }, null);
                        obtain3.setStr1(clockId);
                        obtain3.setStr2(String.valueOf(pluginLifecycleManager2));
                        obtain3.setStr3(pluginLifecycleManager.toString());
                        logger.getBuffer().commit(obtain3);
                    }
                }
                if (ref$BooleanRef.element) {
                    ClockRegistry.access$triggerOnAvailableClocksChanged(clockRegistry);
                }
                clockRegistry.verifyLoadedProviders();
                return z5;
            }

            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginDetached(final PluginLifecycleManager pluginLifecycleManager) {
                final ArrayList<ClockRegistry.ClockInfo> arrayList = new ArrayList();
                ClockRegistry clockRegistry = ClockRegistry.this;
                CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(clockRegistry.availableClocks.entrySet(), new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginDetached$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Map.Entry entry = (Map.Entry) obj;
                        if (!Intrinsics.areEqual(((ClockRegistry.ClockInfo) entry.getValue()).manager, pluginLifecycleManager)) {
                            return Boolean.FALSE;
                        }
                        arrayList.add(entry.getValue());
                        return Boolean.TRUE;
                    }
                }, true);
                for (ClockRegistry.ClockInfo clockInfo : arrayList) {
                    boolean areEqual = Intrinsics.areEqual(clockRegistry.getCurrentClockId(), clockInfo.metadata.getClockId());
                    Logger logger = clockRegistry.logger;
                    LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), areEqual ? LogLevel.INFO : LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$onDisconnected$1
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            return MotionLayout$$ExternalSyntheticOutline0.m("Disconnected ", logMessage.getStr1(), " @", logMessage.getStr2(), logMessage.getBool1() ? " (Current Clock)" : "");
                        }
                    }, null);
                    obtain.setStr1(clockInfo.metadata.getClockId());
                    obtain.setStr2(String.valueOf(clockInfo.manager));
                    obtain.setBool1(areEqual);
                    logger.getBuffer().commit(obtain);
                }
                if (arrayList.size() > 0) {
                    ClockRegistry.access$triggerOnAvailableClocksChanged(clockRegistry);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginLoaded(Plugin plugin, Context context2, PluginLifecycleManager pluginLifecycleManager) {
                ClockProviderPlugin clockProviderPlugin = (ClockProviderPlugin) plugin;
                final ClockRegistry clockRegistry = ClockRegistry.this;
                clockProviderPlugin.initialize(clockRegistry.clockBuffers);
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                for (ClockMetadata clockMetadata : clockProviderPlugin.getClocks()) {
                    String clockId = clockMetadata.getClockId();
                    if (clockRegistry.isTransitClockEnabled || !Intrinsics.areEqual(clockId, "DIGITAL_CLOCK_METRO")) {
                        ConcurrentHashMap concurrentHashMap = clockRegistry.availableClocks;
                        ClockRegistry.ClockInfo clockInfo = new ClockRegistry.ClockInfo(clockMetadata, clockProviderPlugin, pluginLifecycleManager);
                        Function1 function1 = new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginLoaded$info$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                Ref$BooleanRef.this.element = true;
                                ClockRegistry.access$onConnected(clockRegistry, (ClockRegistry.ClockInfo) obj);
                                return Unit.INSTANCE;
                            }
                        };
                        Map map = ClockRegistryKt.KNOWN_PLUGINS;
                        Object putIfAbsent = concurrentHashMap.putIfAbsent(clockId, clockInfo);
                        if (putIfAbsent == 0) {
                            function1.invoke(clockInfo);
                        }
                        if (putIfAbsent != 0) {
                            clockInfo = putIfAbsent;
                        }
                        ClockRegistry.ClockInfo clockInfo2 = clockInfo;
                        PluginLifecycleManager pluginLifecycleManager2 = clockInfo2.manager;
                        boolean equals = pluginLifecycleManager.equals(pluginLifecycleManager2);
                        Logger logger = clockRegistry.logger;
                        if (equals) {
                            clockInfo2.provider = clockProviderPlugin;
                            String currentClockId = clockRegistry.getCurrentClockId();
                            ClockMetadata clockMetadata2 = clockInfo2.metadata;
                            boolean areEqual = Intrinsics.areEqual(currentClockId, clockMetadata2.getClockId());
                            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), areEqual ? LogLevel.INFO : LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$onLoaded$1
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    LogMessage logMessage = (LogMessage) obj;
                                    return MotionLayout$$ExternalSyntheticOutline0.m("Loaded ", logMessage.getStr1(), " @", logMessage.getStr2(), logMessage.getBool1() ? " (Current Clock)" : "");
                                }
                            }, null);
                            obtain.setStr1(clockMetadata2.getClockId());
                            obtain.setStr2(String.valueOf(pluginLifecycleManager2));
                            obtain.setBool1(areEqual);
                            logger.getBuffer().commit(obtain);
                            if (areEqual) {
                                clockRegistry.triggerOnCurrentClockChanged();
                            }
                        } else {
                            LogMessage obtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginLoaded$1
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    LogMessage logMessage = (LogMessage) obj;
                                    String str1 = logMessage.getStr1();
                                    String str22 = logMessage.getStr2();
                                    String str32 = logMessage.getStr3();
                                    StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Clock Id conflict on load: ", str1, " is double registered by ", str22, " and ");
                                    m.append(str32);
                                    return m.toString();
                                }
                            }, null);
                            obtain2.setStr1(clockId);
                            obtain2.setStr2(String.valueOf(pluginLifecycleManager2));
                            obtain2.setStr3(pluginLifecycleManager.toString());
                            logger.getBuffer().commit(obtain2);
                            pluginLifecycleManager.unloadPlugin();
                        }
                    }
                }
                if (ref$BooleanRef.element) {
                    ClockRegistry.access$triggerOnAvailableClocksChanged(clockRegistry);
                }
                clockRegistry.verifyLoadedProviders();
            }

            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginUnloaded(Plugin plugin, PluginLifecycleManager pluginLifecycleManager) {
                Iterator<ClockMetadata> it = ((ClockProviderPlugin) plugin).getClocks().iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    ClockRegistry clockRegistry = ClockRegistry.this;
                    if (!hasNext) {
                        clockRegistry.verifyLoadedProviders();
                        return;
                    }
                    String clockId = it.next().getClockId();
                    ClockRegistry.ClockInfo clockInfo = (ClockRegistry.ClockInfo) clockRegistry.availableClocks.get(clockId);
                    boolean areEqual = Intrinsics.areEqual(clockInfo != null ? clockInfo.manager : null, pluginLifecycleManager);
                    Logger logger = clockRegistry.logger;
                    if (areEqual) {
                        clockInfo.provider = null;
                        String currentClockId = clockRegistry.getCurrentClockId();
                        ClockMetadata clockMetadata = clockInfo.metadata;
                        boolean areEqual2 = Intrinsics.areEqual(currentClockId, clockMetadata.getClockId());
                        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), areEqual2 ? LogLevel.WARNING : LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$onUnloaded$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                return MotionLayout$$ExternalSyntheticOutline0.m("Unloaded ", logMessage.getStr1(), " @", logMessage.getStr2(), logMessage.getBool1() ? " (Current Clock)" : "");
                            }
                        }, null);
                        obtain.setStr1(clockMetadata.getClockId());
                        obtain.setStr2(String.valueOf(clockInfo.manager));
                        obtain.setBool1(areEqual2);
                        logger.getBuffer().commit(obtain);
                        if (areEqual2) {
                            clockRegistry.triggerOnCurrentClockChanged();
                        }
                    } else {
                        LogMessage obtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginUnloaded$1
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                String str1 = logMessage.getStr1();
                                String str22 = logMessage.getStr2();
                                String str32 = logMessage.getStr3();
                                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Clock Id conflict on unload: ", str1, " is double registered by ", str22, " and ");
                                m.append(str32);
                                return m.toString();
                            }
                        }, null);
                        obtain2.setStr1(clockId);
                        obtain2.setStr2(String.valueOf(clockInfo != null ? clockInfo.manager : null));
                        obtain2.setStr3(pluginLifecycleManager.toString());
                        logger.getBuffer().commit(obtain2);
                    }
                }
            }
        };
        this.userSwitchObserver = new UserSwitchObserver() { // from class: com.android.systemui.shared.clocks.ClockRegistry$userSwitchObserver$1
            public final void onUserSwitchComplete(int i) {
                ClockRegistry clockRegistry = ClockRegistry.this;
                BuildersKt.launch$default(clockRegistry.scope, clockRegistry.bgDispatcher, null, new ClockRegistry$userSwitchObserver$1$onUserSwitchComplete$1(clockRegistry, null), 2);
            }
        };
        this.isClockChanged = new AtomicBoolean(false);
        this.isClockListChanged = new AtomicBoolean(false);
        clockProvider.initialize(clockMessageBuffers);
        for (ClockMetadata clockMetadata : clockProvider.getClocks()) {
            this.availableClocks.put(clockMetadata.getClockId(), new ClockInfo(clockMetadata, clockProvider, null));
        }
        if (this.availableClocks.containsKey("DEFAULT")) {
            this.isQueued = new AtomicBoolean(false);
            return;
        }
        throw new IllegalArgumentException(clockProvider + " did not register clock at DEFAULT");
    }

    public static final void access$onConnected(ClockRegistry clockRegistry, ClockInfo clockInfo) {
        boolean areEqual = Intrinsics.areEqual(clockRegistry.getCurrentClockId(), clockInfo.metadata.getClockId());
        Logger logger = clockRegistry.logger;
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), areEqual ? LogLevel.INFO : LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$onConnected$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return MotionLayout$$ExternalSyntheticOutline0.m("Connected ", logMessage.getStr1(), " @", logMessage.getStr2(), logMessage.getBool1() ? " (Current Clock)" : "");
            }
        }, null);
        obtain.setStr1(clockInfo.metadata.getClockId());
        obtain.setStr2(String.valueOf(clockInfo.manager));
        obtain.setBool1(areEqual);
        logger.getBuffer().commit(obtain);
    }

    public static final void access$triggerOnAvailableClocksChanged(ClockRegistry clockRegistry) {
        if (clockRegistry.isClockListChanged.compareAndSet(false, true)) {
            BuildersKt.launch$default(clockRegistry.scope, clockRegistry.mainDispatcher, null, new ClockRegistry$triggerOnAvailableClocksChanged$1(clockRegistry, null), 2);
        }
    }

    public final ClockController createClock(String str) {
        ClockProvider clockProvider;
        ClockSettings clockSettings = this.settings;
        if (clockSettings == null) {
            clockSettings = new ClockSettings(null, null, 3, null);
        }
        if (!Intrinsics.areEqual(str, clockSettings.getClockId())) {
            clockSettings = ClockSettings.copy$default(clockSettings, str, null, 2, null);
        }
        ClockInfo clockInfo = (ClockInfo) this.availableClocks.get(str);
        if (clockInfo == null || (clockProvider = clockInfo.provider) == null) {
            return null;
        }
        return clockProvider.createClock(clockSettings);
    }

    public final ClockController createCurrentClock() {
        String currentClockId = getCurrentClockId();
        if (this.isEnabled && currentClockId.length() > 0) {
            ClockController createClock = createClock(currentClockId);
            Logger logger = this.logger;
            if (createClock != null) {
                ClockRegistry$createCurrentClock$1 clockRegistry$createCurrentClock$1 = new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$createCurrentClock$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Rendering clock ", ((LogMessage) obj).getStr1());
                    }
                };
                LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, clockRegistry$createCurrentClock$1, null);
                obtain.setStr1(currentClockId);
                logger.getBuffer().commit(obtain);
                return createClock;
            }
            if (this.availableClocks.containsKey(currentClockId)) {
                ClockRegistry$createCurrentClock$3 clockRegistry$createCurrentClock$3 = new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$createCurrentClock$3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Clock ", ((LogMessage) obj).getStr1(), " not loaded; using default");
                    }
                };
                LogMessage obtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, clockRegistry$createCurrentClock$3, null);
                obtain2.setStr1(currentClockId);
                logger.getBuffer().commit(obtain2);
                verifyLoadedProviders();
            } else {
                ClockRegistry$createCurrentClock$5 clockRegistry$createCurrentClock$5 = new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$createCurrentClock$5
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Clock ", ((LogMessage) obj).getStr1(), " not found; using default");
                    }
                };
                LogMessage obtain3 = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, clockRegistry$createCurrentClock$5, null);
                obtain3.setStr1(currentClockId);
                logger.getBuffer().commit(obtain3);
            }
        }
        ClockController createClock2 = createClock("DEFAULT");
        Intrinsics.checkNotNull(createClock2);
        return createClock2;
    }

    public final String getCurrentClockId() {
        String clockId;
        ClockSettings clockSettings = this.settings;
        return (clockSettings == null || (clockId = clockSettings.getClockId()) == null) ? this.fallbackClockId : clockId;
    }

    public final void querySettings() {
        ClockSettings clockSettings;
        this.f74assert.isNotMainThread();
        try {
            clockSettings = ClockSettings.Companion.deserialize(this.handleAllUsers ? Settings.Secure.getStringForUser(this.context.getContentResolver(), "lock_screen_custom_clock_face", ActivityManager.getCurrentUser()) : Settings.Secure.getString(this.context.getContentResolver(), "lock_screen_custom_clock_face"));
        } catch (Exception e) {
            this.logger.e("Failed to parse clock settings", e);
            clockSettings = null;
        }
        if (Intrinsics.areEqual(this.settings, clockSettings)) {
            return;
        }
        this.settings = clockSettings;
        verifyLoadedProviders();
        triggerOnCurrentClockChanged();
    }

    public final void registerListeners() {
        if (!this.isEnabled || this.isRegistered) {
            return;
        }
        this.isRegistered = true;
        this.pluginManager.addPluginListener((PluginListener) this.pluginListener, ClockProviderPlugin.class, true);
        BuildersKt.launch$default(this.scope, this.bgDispatcher, null, new ClockRegistry$registerListeners$1(this, null), 2);
        boolean z = this.handleAllUsers;
        ClockRegistry$settingObserver$1 clockRegistry$settingObserver$1 = this.settingObserver;
        if (!z) {
            this.context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_custom_clock_face"), false, clockRegistry$settingObserver$1);
        } else {
            this.context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_custom_clock_face"), false, clockRegistry$settingObserver$1, -1);
            ActivityManager.getService().registerUserSwitchObserver(this.userSwitchObserver, this.TAG);
        }
    }

    public final void triggerOnCurrentClockChanged() {
        if (this.isClockChanged.compareAndSet(false, true)) {
            BuildersKt.launch$default(this.scope, this.mainDispatcher, null, new ClockRegistry$triggerOnCurrentClockChanged$1(this, null), 2);
        }
    }

    public final void verifyLoadedProviders() {
        if (!this.isQueued.compareAndSet(false, true)) {
            Logger.v$default(this.logger, "verifyLoadedProviders: shouldSchedule=false", null, 2, null);
        } else {
            BuildersKt.launch$default(this.scope, this.bgDispatcher, null, new ClockRegistry$verifyLoadedProviders$1(this, null), 2);
        }
    }

    public /* synthetic */ ClockRegistry(Context context, PluginManager pluginManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, boolean z, boolean z2, ClockProvider clockProvider, String str, ClockMessageBuffers clockMessageBuffers, boolean z3, String str2, boolean z4, ThreadAssert threadAssert, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, pluginManager, coroutineScope, coroutineDispatcher, coroutineDispatcher2, z, z2, clockProvider, (i & 256) != 0 ? "DEFAULT" : str, (i & 512) != 0 ? null : clockMessageBuffers, z3, str2, (i & 4096) != 0 ? false : z4, (i & 8192) != 0 ? new ThreadAssert() : threadAssert);
    }
}
