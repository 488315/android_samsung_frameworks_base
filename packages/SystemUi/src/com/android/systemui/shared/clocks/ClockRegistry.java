package com.android.systemui.shared.clocks;

import android.app.ActivityManager;
import android.app.UserSwitchObserver;
import android.content.Context;
import android.database.ContentObserver;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.ClockMetadata;
import com.android.systemui.plugins.ClockProvider;
import com.android.systemui.plugins.ClockProviderPlugin;
import com.android.systemui.plugins.ClockSettings;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginLifecycleManager;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.shared.clocks.ClockRegistryKt;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ClockRegistry {
    public final String TAG;
    public final ConcurrentHashMap availableClocks;
    public final CoroutineDispatcher bgDispatcher;
    public final List clockChangeListeners;
    public final Context context;
    public final String fallbackClockId;
    public final boolean handleAllUsers;
    public final AtomicBoolean isClockChanged;
    public final AtomicBoolean isClockListChanged;
    public final boolean isEnabled;
    public boolean isRegistered;
    public final AtomicBoolean isVerifying;
    public final boolean keepAllLoaded;
    public final LogBuffer logBuffer;
    public final CoroutineDispatcher mainDispatcher;
    public final ClockRegistry$pluginListener$1 pluginListener;
    public final PluginManager pluginManager;
    public final CoroutineScope scope;
    public final ClockRegistry$settingObserver$1 settingObserver;
    public ClockSettings settings;
    public final ClockRegistry$userSwitchObserver$1 userSwitchObserver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ClockChangeListener {
        void onAvailableClocksChanged();

        void onCurrentClockChanged();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public ClockRegistry(Context context, PluginManager pluginManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, boolean z, boolean z2, ClockProvider clockProvider, String str, LogBuffer logBuffer, boolean z3, String str2) {
        this.context = context;
        this.pluginManager = pluginManager;
        this.scope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.bgDispatcher = coroutineDispatcher2;
        this.isEnabled = z;
        this.handleAllUsers = z2;
        this.fallbackClockId = str;
        this.logBuffer = logBuffer;
        this.keepAllLoaded = z3;
        this.TAG = Reflection.getOrCreateKotlinClass(ClockRegistry.class).getSimpleName() + " (" + str2 + ")";
        this.availableClocks = new ConcurrentHashMap();
        this.clockChangeListeners = new ArrayList();
        this.settingObserver = new ContentObserver() { // from class: com.android.systemui.shared.clocks.ClockRegistry$settingObserver$1
            {
                super(null);
            }

            public final void onChange(boolean z4, Collection collection, int i, int i2) {
                ClockRegistry clockRegistry = ClockRegistry.this;
                BuildersKt.launch$default(clockRegistry.scope, clockRegistry.bgDispatcher, null, new ClockRegistry$settingObserver$1$onChange$1(clockRegistry, null), 2);
            }
        };
        this.pluginListener = new PluginListener() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.plugins.PluginListener
            public final boolean onPluginAttached(PluginLifecycleManager pluginLifecycleManager) {
                final ClockRegistry clockRegistry = ClockRegistry.this;
                if (clockRegistry.keepAllLoaded) {
                    return true;
                }
                List<ClockMetadata> list = (List) ClockRegistryKt.KNOWN_PLUGINS.get(pluginLifecycleManager.getPackage());
                String str3 = clockRegistry.TAG;
                LogBuffer logBuffer2 = clockRegistry.logBuffer;
                if (list == null) {
                    LogLevel logLevel = LogLevel.WARNING;
                    ClockRegistry$pluginListener$1$onPluginAttached$2 clockRegistry$pluginListener$1$onPluginAttached$2 = ClockRegistry$pluginListener$1$onPluginAttached$2.INSTANCE;
                    if (logBuffer2 != null) {
                        LogMessage obtain = logBuffer2.obtain(str3, logLevel, clockRegistry$pluginListener$1$onPluginAttached$2, null);
                        obtain.setStr1(pluginLifecycleManager.getPackage());
                        logBuffer2.commit(obtain);
                    } else {
                        ClockRegistryKt.access$getTMP_MESSAGE().setStr1(pluginLifecycleManager.getPackage());
                        String str4 = (String) clockRegistry$pluginListener$1$onPluginAttached$2.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                        int i = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel.ordinal()];
                        if (i == 2) {
                            Log.d(str3, str4, null);
                        } else if (i == 3) {
                            Log.i(str3, str4, null);
                        } else if (i == 4) {
                            Log.w(str3, str4, null);
                        } else if (i == 5) {
                            Log.e(str3, str4, null);
                        } else if (i == 6) {
                            Log.wtf(str3, str4, null);
                        }
                    }
                    return true;
                }
                LogLevel logLevel2 = LogLevel.INFO;
                ClockRegistry$pluginListener$1$onPluginAttached$4 clockRegistry$pluginListener$1$onPluginAttached$4 = ClockRegistry$pluginListener$1$onPluginAttached$4.INSTANCE;
                if (logBuffer2 != null) {
                    LogMessage obtain2 = logBuffer2.obtain(str3, logLevel2, clockRegistry$pluginListener$1$onPluginAttached$4, null);
                    obtain2.setStr1(pluginLifecycleManager.getPackage());
                    logBuffer2.commit(obtain2);
                } else {
                    ClockRegistryKt.access$getTMP_MESSAGE().setStr1(pluginLifecycleManager.getPackage());
                    String str5 = (String) clockRegistry$pluginListener$1$onPluginAttached$4.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                    int i2 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel2.ordinal()];
                    if (i2 == 2) {
                        Log.d(str3, str5, null);
                    } else if (i2 == 3) {
                        Log.i(str3, str5, null);
                    } else if (i2 == 4) {
                        Log.w(str3, str5, null);
                    } else if (i2 == 5) {
                        Log.e(str3, str5, null);
                    } else if (i2 == 6) {
                        Log.wtf(str3, str5, null);
                    }
                }
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                for (ClockMetadata clockMetadata : list) {
                    final String clockId = clockMetadata.getClockId();
                    ConcurrentHashMap concurrentHashMap = clockRegistry.availableClocks;
                    ClockRegistry.ClockInfo clockInfo = new ClockRegistry.ClockInfo(clockMetadata, null, pluginLifecycleManager);
                    Function0 function0 = new Function0() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginAttached$info$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Ref$BooleanRef.this.element = true;
                            ClockRegistry.access$onConnected(clockRegistry, clockId);
                            return Unit.INSTANCE;
                        }
                    };
                    Object putIfAbsent = concurrentHashMap.putIfAbsent(clockId, clockInfo);
                    if (putIfAbsent == 0) {
                        function0.invoke();
                    }
                    if (putIfAbsent != 0) {
                        clockInfo = putIfAbsent;
                    }
                    ClockRegistry.ClockInfo clockInfo2 = clockInfo;
                    if (Intrinsics.areEqual(pluginLifecycleManager, clockInfo2.manager)) {
                        clockInfo2.provider = null;
                    } else {
                        LogLevel logLevel3 = LogLevel.ERROR;
                        ClockRegistry$pluginListener$1$onPluginAttached$6 clockRegistry$pluginListener$1$onPluginAttached$6 = ClockRegistry$pluginListener$1$onPluginAttached$6.INSTANCE;
                        if (logBuffer2 != null) {
                            LogMessage obtain3 = logBuffer2.obtain(str3, logLevel3, clockRegistry$pluginListener$1$onPluginAttached$6, null);
                            obtain3.setStr1(clockId);
                            logBuffer2.commit(obtain3);
                        } else {
                            ClockRegistryKt.access$getTMP_MESSAGE().setStr1(clockId);
                            String str6 = (String) clockRegistry$pluginListener$1$onPluginAttached$6.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                            int i3 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel3.ordinal()];
                            if (i3 == 2) {
                                Log.d(str3, str6, null);
                            } else if (i3 == 3) {
                                Log.i(str3, str6, null);
                            } else if (i3 == 4) {
                                Log.w(str3, str6, null);
                            } else if (i3 == 5) {
                                Log.e(str3, str6, null);
                            } else if (i3 == 6) {
                                Log.wtf(str3, str6, null);
                            }
                        }
                    }
                }
                if (ref$BooleanRef.element) {
                    ClockRegistry.access$triggerOnAvailableClocksChanged(clockRegistry);
                }
                clockRegistry.verifyLoadedProviders();
                return false;
            }

            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginDetached(final PluginLifecycleManager pluginLifecycleManager) {
                final ArrayList arrayList = new ArrayList();
                ClockRegistry clockRegistry = ClockRegistry.this;
                CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt((Iterable) clockRegistry.availableClocks.entrySet(), new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginDetached$1
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
                        arrayList.add(entry.getKey());
                        return Boolean.TRUE;
                    }
                }, true);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    LogLevel logLevel = LogLevel.DEBUG;
                    ClockRegistry$onDisconnected$2 clockRegistry$onDisconnected$2 = ClockRegistry$onDisconnected$2.INSTANCE;
                    LogBuffer logBuffer2 = clockRegistry.logBuffer;
                    String str4 = clockRegistry.TAG;
                    if (logBuffer2 != null) {
                        LogMessage obtain = logBuffer2.obtain(str4, logLevel, clockRegistry$onDisconnected$2, null);
                        obtain.setStr1(str3);
                        logBuffer2.commit(obtain);
                    } else {
                        ClockRegistryKt.access$getTMP_MESSAGE().setStr1(str3);
                        String str5 = (String) clockRegistry$onDisconnected$2.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                        int i = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel.ordinal()];
                        if (i == 2) {
                            Log.d(str4, str5, null);
                        } else if (i == 3) {
                            Log.i(str4, str5, null);
                        } else if (i == 4) {
                            Log.w(str4, str5, null);
                        } else if (i == 5) {
                            Log.e(str4, str5, null);
                        } else if (i == 6) {
                            Log.wtf(str4, str5, null);
                        }
                    }
                    if (Intrinsics.areEqual(clockRegistry.getCurrentClockId(), str3)) {
                        LogLevel logLevel2 = LogLevel.WARNING;
                        ClockRegistry$onDisconnected$4 clockRegistry$onDisconnected$4 = ClockRegistry$onDisconnected$4.INSTANCE;
                        if (logBuffer2 != null) {
                            LogMessage obtain2 = logBuffer2.obtain(str4, logLevel2, clockRegistry$onDisconnected$4, null);
                            obtain2.setStr1(str3);
                            logBuffer2.commit(obtain2);
                        } else {
                            ClockRegistryKt.access$getTMP_MESSAGE().setStr1(str3);
                            String str6 = (String) clockRegistry$onDisconnected$4.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                            int i2 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel2.ordinal()];
                            if (i2 == 2) {
                                Log.d(str4, str6, null);
                            } else if (i2 == 3) {
                                Log.i(str4, str6, null);
                            } else if (i2 == 4) {
                                Log.w(str4, str6, null);
                            } else if (i2 == 5) {
                                Log.e(str4, str6, null);
                            } else if (i2 == 6) {
                                Log.wtf(str4, str6, null);
                            }
                        }
                    }
                }
                if (arrayList.size() > 0) {
                    ClockRegistry.access$triggerOnAvailableClocksChanged(clockRegistry);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginLoaded(Plugin plugin, Context context2, PluginLifecycleManager pluginLifecycleManager) {
                final ClockRegistry clockRegistry;
                Throwable th;
                ClockProviderPlugin clockProviderPlugin = (ClockProviderPlugin) plugin;
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                Iterator<ClockMetadata> it = clockProviderPlugin.getClocks().iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    clockRegistry = ClockRegistry.this;
                    if (!hasNext) {
                        break;
                    }
                    ClockMetadata next = it.next();
                    final String clockId = next.getClockId();
                    ConcurrentHashMap concurrentHashMap = clockRegistry.availableClocks;
                    ClockRegistry.ClockInfo clockInfo = new ClockRegistry.ClockInfo(next, clockProviderPlugin, pluginLifecycleManager);
                    Function0 function0 = new Function0() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginLoaded$info$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Ref$BooleanRef.this.element = true;
                            ClockRegistry.access$onConnected(clockRegistry, clockId);
                            return Unit.INSTANCE;
                        }
                    };
                    Map map = ClockRegistryKt.KNOWN_PLUGINS;
                    Object putIfAbsent = concurrentHashMap.putIfAbsent(clockId, clockInfo);
                    if (putIfAbsent == 0) {
                        function0.invoke();
                    }
                    if (putIfAbsent != 0) {
                        clockInfo = putIfAbsent;
                    }
                    ClockRegistry.ClockInfo clockInfo2 = clockInfo;
                    boolean areEqual = Intrinsics.areEqual(pluginLifecycleManager, clockInfo2.manager);
                    String str3 = clockRegistry.TAG;
                    LogBuffer logBuffer2 = clockRegistry.logBuffer;
                    if (areEqual) {
                        clockInfo2.provider = clockProviderPlugin;
                        LogLevel logLevel = LogLevel.DEBUG;
                        ClockRegistry$onLoaded$2 clockRegistry$onLoaded$2 = ClockRegistry$onLoaded$2.INSTANCE;
                        if (logBuffer2 != null) {
                            LogMessage obtain = logBuffer2.obtain(str3, logLevel, clockRegistry$onLoaded$2, null);
                            obtain.setStr1(clockId);
                            logBuffer2.commit(obtain);
                            th = null;
                        } else {
                            ClockRegistryKt.access$getTMP_MESSAGE().setStr1(clockId);
                            String str4 = (String) clockRegistry$onLoaded$2.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                            int i = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel.ordinal()];
                            if (i == 2) {
                                th = null;
                                Log.d(str3, str4, null);
                            } else if (i == 3) {
                                th = null;
                                Log.i(str3, str4, null);
                            } else if (i == 4) {
                                th = null;
                                Log.w(str3, str4, null);
                            } else if (i == 5) {
                                th = null;
                                Log.e(str3, str4, null);
                            } else if (i != 6) {
                                th = null;
                            } else {
                                th = null;
                                Log.wtf(str3, str4, null);
                            }
                        }
                        if (Intrinsics.areEqual(clockRegistry.getCurrentClockId(), clockId)) {
                            LogLevel logLevel2 = LogLevel.INFO;
                            ClockRegistry$onLoaded$4 clockRegistry$onLoaded$4 = ClockRegistry$onLoaded$4.INSTANCE;
                            if (logBuffer2 != null) {
                                LogMessage obtain2 = logBuffer2.obtain(str3, logLevel2, clockRegistry$onLoaded$4, th);
                                obtain2.setStr1(clockId);
                                logBuffer2.commit(obtain2);
                            } else {
                                ClockRegistryKt.access$getTMP_MESSAGE().setStr1(clockId);
                                String str5 = (String) clockRegistry$onLoaded$4.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                                int i2 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel2.ordinal()];
                                if (i2 == 2) {
                                    Log.d(str3, str5, null);
                                } else if (i2 == 3) {
                                    Log.i(str3, str5, null);
                                } else if (i2 == 4) {
                                    Log.w(str3, str5, null);
                                } else if (i2 == 5) {
                                    Log.e(str3, str5, null);
                                } else if (i2 == 6) {
                                    Log.wtf(str3, str5, null);
                                }
                            }
                            clockRegistry.triggerOnCurrentClockChanged();
                        }
                    } else {
                        LogLevel logLevel3 = LogLevel.ERROR;
                        ClockRegistry$pluginListener$1$onPluginLoaded$2 clockRegistry$pluginListener$1$onPluginLoaded$2 = ClockRegistry$pluginListener$1$onPluginLoaded$2.INSTANCE;
                        if (logBuffer2 != null) {
                            LogMessage obtain3 = logBuffer2.obtain(str3, logLevel3, clockRegistry$pluginListener$1$onPluginLoaded$2, null);
                            obtain3.setStr1(clockId);
                            logBuffer2.commit(obtain3);
                        } else {
                            ClockRegistryKt.access$getTMP_MESSAGE().setStr1(clockId);
                            String str6 = (String) clockRegistry$pluginListener$1$onPluginLoaded$2.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                            int i3 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel3.ordinal()];
                            if (i3 == 2) {
                                Log.d(str3, str6, null);
                            } else if (i3 == 3) {
                                Log.i(str3, str6, null);
                            } else if (i3 == 4) {
                                Log.w(str3, str6, null);
                            } else if (i3 == 5) {
                                Log.e(str3, str6, null);
                            } else if (i3 == 6) {
                                Log.wtf(str3, str6, null);
                            }
                        }
                        pluginLifecycleManager.unloadPlugin();
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
                    String str3 = clockRegistry.TAG;
                    LogBuffer logBuffer2 = clockRegistry.logBuffer;
                    if (areEqual) {
                        clockInfo.provider = null;
                        LogLevel logLevel = LogLevel.DEBUG;
                        ClockRegistry$onUnloaded$2 clockRegistry$onUnloaded$2 = ClockRegistry$onUnloaded$2.INSTANCE;
                        if (logBuffer2 != null) {
                            LogMessage obtain = logBuffer2.obtain(str3, logLevel, clockRegistry$onUnloaded$2, null);
                            obtain.setStr1(clockId);
                            logBuffer2.commit(obtain);
                        } else {
                            ClockRegistryKt.access$getTMP_MESSAGE().setStr1(clockId);
                            String str4 = (String) clockRegistry$onUnloaded$2.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                            int i = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel.ordinal()];
                            if (i == 2) {
                                Log.d(str3, str4, null);
                            } else if (i == 3) {
                                Log.i(str3, str4, null);
                            } else if (i == 4) {
                                Log.w(str3, str4, null);
                            } else if (i == 5) {
                                Log.e(str3, str4, null);
                            } else if (i == 6) {
                                Log.wtf(str3, str4, null);
                            }
                        }
                        if (Intrinsics.areEqual(clockRegistry.getCurrentClockId(), clockId)) {
                            LogLevel logLevel2 = LogLevel.WARNING;
                            ClockRegistry$onUnloaded$4 clockRegistry$onUnloaded$4 = ClockRegistry$onUnloaded$4.INSTANCE;
                            if (logBuffer2 != null) {
                                LogMessage obtain2 = logBuffer2.obtain(str3, logLevel2, clockRegistry$onUnloaded$4, null);
                                obtain2.setStr1(clockId);
                                logBuffer2.commit(obtain2);
                            } else {
                                ClockRegistryKt.access$getTMP_MESSAGE().setStr1(clockId);
                                String str5 = (String) clockRegistry$onUnloaded$4.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                                int i2 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel2.ordinal()];
                                if (i2 == 2) {
                                    Log.d(str3, str5, null);
                                } else if (i2 == 3) {
                                    Log.i(str3, str5, null);
                                } else if (i2 == 4) {
                                    Log.w(str3, str5, null);
                                } else if (i2 == 5) {
                                    Log.e(str3, str5, null);
                                } else if (i2 == 6) {
                                    Log.wtf(str3, str5, null);
                                }
                            }
                            clockRegistry.triggerOnCurrentClockChanged();
                        }
                    } else {
                        LogLevel logLevel3 = LogLevel.ERROR;
                        ClockRegistry$pluginListener$1$onPluginUnloaded$2 clockRegistry$pluginListener$1$onPluginUnloaded$2 = ClockRegistry$pluginListener$1$onPluginUnloaded$2.INSTANCE;
                        if (logBuffer2 != null) {
                            LogMessage obtain3 = logBuffer2.obtain(str3, logLevel3, clockRegistry$pluginListener$1$onPluginUnloaded$2, null);
                            obtain3.setStr1(clockId);
                            logBuffer2.commit(obtain3);
                        } else {
                            ClockRegistryKt.access$getTMP_MESSAGE().setStr1(clockId);
                            String str6 = (String) clockRegistry$pluginListener$1$onPluginUnloaded$2.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                            int i3 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel3.ordinal()];
                            if (i3 == 2) {
                                Log.d(str3, str6, null);
                            } else if (i3 == 3) {
                                Log.i(str3, str6, null);
                            } else if (i3 == 4) {
                                Log.w(str3, str6, null);
                            } else if (i3 == 5) {
                                Log.e(str3, str6, null);
                            } else if (i3 == 6) {
                                Log.wtf(str3, str6, null);
                            }
                        }
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
        for (ClockMetadata clockMetadata : clockProvider.getClocks()) {
            this.availableClocks.put(clockMetadata.getClockId(), new ClockInfo(clockMetadata, clockProvider, null));
        }
        if (this.availableClocks.containsKey("DEFAULT")) {
            this.isVerifying = new AtomicBoolean(false);
            return;
        }
        throw new IllegalArgumentException(clockProvider + " did not register clock at DEFAULT");
    }

    public static final void access$onConnected(ClockRegistry clockRegistry, String str) {
        clockRegistry.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ClockRegistry$onConnected$2 clockRegistry$onConnected$2 = ClockRegistry$onConnected$2.INSTANCE;
        LogBuffer logBuffer = clockRegistry.logBuffer;
        String str2 = clockRegistry.TAG;
        if (logBuffer != null) {
            LogMessage obtain = logBuffer.obtain(str2, logLevel, clockRegistry$onConnected$2, null);
            obtain.setStr1(str);
            logBuffer.commit(obtain);
        } else {
            ClockRegistryKt.access$getTMP_MESSAGE().setStr1(str);
            String str3 = (String) clockRegistry$onConnected$2.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
            int i = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel.ordinal()];
            if (i == 2) {
                Log.d(str2, str3, null);
            } else if (i == 3) {
                Log.i(str2, str3, null);
            } else if (i == 4) {
                Log.w(str2, str3, null);
            } else if (i == 5) {
                Log.e(str2, str3, null);
            } else if (i == 6) {
                Log.wtf(str2, str3, null);
            }
        }
        if (Intrinsics.areEqual(clockRegistry.getCurrentClockId(), str)) {
            LogLevel logLevel2 = LogLevel.INFO;
            ClockRegistry$onConnected$4 clockRegistry$onConnected$4 = ClockRegistry$onConnected$4.INSTANCE;
            if (logBuffer != null) {
                LogMessage obtain2 = logBuffer.obtain(str2, logLevel2, clockRegistry$onConnected$4, null);
                obtain2.setStr1(str);
                logBuffer.commit(obtain2);
                return;
            }
            ClockRegistryKt.access$getTMP_MESSAGE().setStr1(str);
            String str4 = (String) clockRegistry$onConnected$4.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
            int i2 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel2.ordinal()];
            if (i2 == 2) {
                Log.d(str2, str4, null);
                return;
            }
            if (i2 == 3) {
                Log.i(str2, str4, null);
                return;
            }
            if (i2 == 4) {
                Log.w(str2, str4, null);
            } else if (i2 == 5) {
                Log.e(str2, str4, null);
            } else {
                if (i2 != 6) {
                    return;
                }
                Log.wtf(str2, str4, null);
            }
        }
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
        if (this.isEnabled) {
            if (currentClockId.length() > 0) {
                ClockController createClock = createClock(currentClockId);
                String str = this.TAG;
                LogBuffer logBuffer = this.logBuffer;
                if (createClock != null) {
                    LogLevel logLevel = LogLevel.INFO;
                    ClockRegistry$createCurrentClock$2 clockRegistry$createCurrentClock$2 = ClockRegistry$createCurrentClock$2.INSTANCE;
                    if (logBuffer != null) {
                        LogMessage obtain = logBuffer.obtain(str, logLevel, clockRegistry$createCurrentClock$2, null);
                        obtain.setStr1(currentClockId);
                        logBuffer.commit(obtain);
                    } else {
                        ClockRegistryKt.access$getTMP_MESSAGE().setStr1(currentClockId);
                        String str2 = (String) clockRegistry$createCurrentClock$2.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                        int i = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel.ordinal()];
                        if (i == 2) {
                            Log.d(str, str2, null);
                        } else if (i == 3) {
                            Log.i(str, str2, null);
                        } else if (i == 4) {
                            Log.w(str, str2, null);
                        } else if (i == 5) {
                            Log.e(str, str2, null);
                        } else if (i == 6) {
                            Log.wtf(str, str2, null);
                        }
                    }
                    return createClock;
                }
                if (this.availableClocks.containsKey(currentClockId)) {
                    LogLevel logLevel2 = LogLevel.WARNING;
                    ClockRegistry$createCurrentClock$4 clockRegistry$createCurrentClock$4 = ClockRegistry$createCurrentClock$4.INSTANCE;
                    if (logBuffer != null) {
                        LogMessage obtain2 = logBuffer.obtain(str, logLevel2, clockRegistry$createCurrentClock$4, null);
                        obtain2.setStr1(currentClockId);
                        logBuffer.commit(obtain2);
                    } else {
                        ClockRegistryKt.access$getTMP_MESSAGE().setStr1(currentClockId);
                        String str3 = (String) clockRegistry$createCurrentClock$4.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                        int i2 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel2.ordinal()];
                        if (i2 == 2) {
                            Log.d(str, str3, null);
                        } else if (i2 == 3) {
                            Log.i(str, str3, null);
                        } else if (i2 == 4) {
                            Log.w(str, str3, null);
                        } else if (i2 == 5) {
                            Log.e(str, str3, null);
                        } else if (i2 == 6) {
                            Log.wtf(str, str3, null);
                        }
                    }
                } else {
                    LogLevel logLevel3 = LogLevel.ERROR;
                    ClockRegistry$createCurrentClock$6 clockRegistry$createCurrentClock$6 = ClockRegistry$createCurrentClock$6.INSTANCE;
                    if (logBuffer != null) {
                        LogMessage obtain3 = logBuffer.obtain(str, logLevel3, clockRegistry$createCurrentClock$6, null);
                        obtain3.setStr1(currentClockId);
                        logBuffer.commit(obtain3);
                    } else {
                        ClockRegistryKt.access$getTMP_MESSAGE().setStr1(currentClockId);
                        String str4 = (String) clockRegistry$createCurrentClock$6.invoke(ClockRegistryKt.access$getTMP_MESSAGE());
                        int i3 = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel3.ordinal()];
                        if (i3 == 2) {
                            Log.d(str, str4, null);
                        } else if (i3 == 3) {
                            Log.i(str, str4, null);
                        } else if (i3 == 4) {
                            Log.w(str, str4, null);
                        } else if (i3 == 5) {
                            Log.e(str, str4, null);
                        } else if (i3 == 6) {
                            Log.wtf(str, str4, null);
                        }
                    }
                }
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
        Assert.isNotMainThread();
        try {
            boolean z = this.handleAllUsers;
            Context context = this.context;
            clockSettings = ClockSettings.Companion.deserialize(z ? Settings.Secure.getStringForUser(context.getContentResolver(), "lock_screen_custom_clock_face", ActivityManager.getCurrentUser()) : Settings.Secure.getString(context.getContentResolver(), "lock_screen_custom_clock_face"));
        } catch (Exception e) {
            LogLevel logLevel = LogLevel.ERROR;
            ClockRegistry$querySettings$result$2 clockRegistry$querySettings$result$2 = new Function1() { // from class: com.android.systemui.shared.clocks.ClockRegistry$querySettings$result$2
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return "Failed to parse clock settings";
                }
            };
            LogBuffer logBuffer = this.logBuffer;
            String str = this.TAG;
            if (logBuffer != null) {
                logBuffer.commit(logBuffer.obtain(str, logLevel, clockRegistry$querySettings$result$2, e));
            } else {
                ClockRegistryKt.access$getTMP_MESSAGE();
                ClockRegistryKt.access$getTMP_MESSAGE();
                clockRegistry$querySettings$result$2.getClass();
                int i = ClockRegistryKt.WhenMappings.$EnumSwitchMapping$0[logLevel.ordinal()];
                if (i == 2) {
                    Log.d(str, "Failed to parse clock settings", e);
                } else if (i == 3) {
                    Log.i(str, "Failed to parse clock settings", e);
                } else if (i == 4) {
                    Log.w(str, "Failed to parse clock settings", e);
                } else if (i == 5) {
                    Log.e(str, "Failed to parse clock settings", e);
                } else if (i == 6) {
                    Log.wtf(str, "Failed to parse clock settings", e);
                }
            }
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
        Context context = this.context;
        if (!z) {
            context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_custom_clock_face"), false, clockRegistry$settingObserver$1);
        } else {
            context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("lock_screen_custom_clock_face"), false, clockRegistry$settingObserver$1, -1);
            ActivityManager.getService().registerUserSwitchObserver(this.userSwitchObserver, this.TAG);
        }
    }

    public final void triggerOnCurrentClockChanged() {
        if (this.isClockChanged.compareAndSet(false, true)) {
            BuildersKt.launch$default(this.scope, this.mainDispatcher, null, new ClockRegistry$triggerOnCurrentClockChanged$1(this, null), 2);
        }
    }

    public final void verifyLoadedProviders() {
        if (this.isVerifying.compareAndSet(false, true)) {
            BuildersKt.launch$default(this.scope, this.bgDispatcher, null, new ClockRegistry$verifyLoadedProviders$1(this, null), 2);
        }
    }

    public /* synthetic */ ClockRegistry(Context context, PluginManager pluginManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, boolean z, boolean z2, ClockProvider clockProvider, String str, LogBuffer logBuffer, boolean z3, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, pluginManager, coroutineScope, coroutineDispatcher, coroutineDispatcher2, z, z2, clockProvider, (i & 256) != 0 ? "DEFAULT" : str, (i & 512) != 0 ? null : logBuffer, z3, str2);
    }
}
