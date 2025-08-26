package com.android.systemui.shared.clocks;

import android.app.ActivityManager;
import android.app.UserSwitchObserver;
import android.content.Context;
import android.database.ContentObserver;
import android.provider.Settings;
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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ClockRegistry {
    public final String TAG;

    /* renamed from: assert, reason: not valid java name */
    public final ThreadAssert f104assert;
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
    public final boolean keepAllLoaded;
    public final Logger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final ClockRegistry$pluginListener$1 pluginListener;
    public final PluginManager pluginManager;
    public final ConcurrentHashMap replacementMap;
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
            int iHashCode = this.metadata.hashCode() * 31;
            ClockProvider clockProvider = this.provider;
            int iHashCode2 = (iHashCode + (clockProvider == null ? 0 : clockProvider.hashCode())) * 31;
            PluginLifecycleManager pluginLifecycleManager = this.manager;
            return iHashCode2 + (pluginLifecycleManager != null ? pluginLifecycleManager.hashCode() : 0);
        }

        public final String toString() {
            return "ClockInfo(metadata=" + this.metadata + ", provider=" + this.provider + ", manager=" + this.manager + ")";
        }
    }

    /* renamed from: com.android.systemui.shared.clocks.ClockRegistry$registerListeners$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ClockRegistry.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ClockRegistry.this.querySettings();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.shared.clocks.ClockRegistry$triggerOnCurrentClockChanged$1, reason: invalid class name and case insensitive filesystem */
    final class C10431 extends SuspendLambda implements Function2 {
        int label;

        public C10431(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ClockRegistry.this.new C10431(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10431) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ClockRegistry.this.f104assert.isMainThread();
            int i = 0;
            ClockRegistry.this.isClockChanged.set(false);
            ArrayList arrayList = (ArrayList) ClockRegistry.this.clockChangeListeners;
            int size = arrayList.size();
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                ((ClockChangeListener) obj2).onCurrentClockChanged();
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.shared.clocks.ClockRegistry$verifyLoadedProviders$1, reason: invalid class name and case insensitive filesystem */
    final class C10441 extends SuspendLambda implements Function2 {
        int label;

        public C10441(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ClockRegistry.this.new C10441(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10441) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ClockRegistry clockRegistry = ClockRegistry.this;
            synchronized (clockRegistry.availableClocks) {
                try {
                    clockRegistry.isQueued.set(false);
                    if (clockRegistry.keepAllLoaded) {
                        Logger.i$default(clockRegistry.logger, "verifyLoadedProviders: keepAllLoaded=true", null, 2, null);
                        Iterator it = clockRegistry.availableClocks.entrySet().iterator();
                        while (it.hasNext()) {
                            PluginLifecycleManager pluginLifecycleManager = ((ClockInfo) ((Map.Entry) it.next()).getValue()).manager;
                            if (pluginLifecycleManager != null) {
                                pluginLifecycleManager.loadPlugin();
                            }
                        }
                        return Unit.INSTANCE;
                    }
                    ClockInfo clockInfo = (ClockInfo) clockRegistry.availableClocks.get(clockRegistry.getCurrentClockId());
                    if (clockInfo == null) {
                        Logger.i$default(clockRegistry.logger, "verifyLoadedProviders: currentClock=null", null, 2, null);
                        Iterator it2 = clockRegistry.availableClocks.entrySet().iterator();
                        while (it2.hasNext()) {
                            PluginLifecycleManager pluginLifecycleManager2 = ((ClockInfo) ((Map.Entry) it2.next()).getValue()).manager;
                            if (pluginLifecycleManager2 != null) {
                                pluginLifecycleManager2.unloadPlugin();
                            }
                        }
                        return Unit.INSTANCE;
                    }
                    Logger.i$default(clockRegistry.logger, "verifyLoadedProviders: load currentClock", null, 2, null);
                    PluginLifecycleManager pluginLifecycleManager3 = clockInfo.manager;
                    if (pluginLifecycleManager3 != null) {
                        pluginLifecycleManager3.loadPlugin();
                    }
                    Iterator it3 = clockRegistry.availableClocks.entrySet().iterator();
                    while (it3.hasNext()) {
                        PluginLifecycleManager pluginLifecycleManager4 = ((ClockInfo) ((Map.Entry) it3.next()).getValue()).manager;
                        if (pluginLifecycleManager4 != null && !Intrinsics.areEqual(pluginLifecycleManager3, pluginLifecycleManager4)) {
                            pluginLifecycleManager4.unloadPlugin();
                        }
                    }
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.shared.clocks.ClockRegistry$settingObserver$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.shared.clocks.ClockRegistry$userSwitchObserver$1] */
    public ClockRegistry(Context context, PluginManager pluginManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, boolean z, boolean z2, ClockProvider clockProvider, String str, ClockMessageBuffers clockMessageBuffers, boolean z3, String str2, ThreadAssert threadAssert) {
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
        this.f104assert = threadAssert;
        String str3 = Reflection.getOrCreateKotlinClass(ClockRegistry.class).getSimpleName() + " (" + str2 + ")";
        this.TAG = str3;
        this.logger = new Logger((clockMessageBuffers == null || (infraMessageBuffer = clockMessageBuffers.getInfraMessageBuffer()) == null) ? new LogcatOnlyMessageBuffer(LogLevel.DEBUG) : infraMessageBuffer, str3);
        this.replacementMap = new ConcurrentHashMap();
        this.availableClocks = new ConcurrentHashMap();
        this.clockChangeListeners = new ArrayList();
        this.settingObserver = new ContentObserver() { // from class: com.android.systemui.shared.clocks.ClockRegistry$settingObserver$1
            {
                super(null);
            }

            public final void onChange(boolean z4, Collection collection, int i, int i2) {
                ClockRegistry clockRegistry = this.this$0;
                BuildersKt.launch$default(clockRegistry.scope, clockRegistry.bgDispatcher, null, new ClockRegistry$settingObserver$1$onChange$1(clockRegistry, null), 2);
            }
        };
        this.pluginListener = new PluginListener() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.plugins.PluginListener
            public final boolean onPluginAttached(PluginLifecycleManager pluginLifecycleManager) {
                String string;
                final ClockRegistry clockRegistry = this.this$0;
                pluginLifecycleManager.setLogFunc(new BiConsumer() { // from class: com.android.systemui.shared.clocks.ClockRegistry$pluginListener$1$onPluginAttached$1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        String str4 = (String) obj;
                        String str5 = (String) obj2;
                        ClockMessageBuffers clockMessageBuffers2 = clockRegistry.clockBuffers;
                        LogBuffer logBuffer = (LogBuffer) (clockMessageBuffers2 != null ? clockMessageBuffers2.getInfraMessageBuffer() : null);
                        if (logBuffer != null) {
                            str4.getClass();
                            LogLevel logLevel = LogLevel.DEBUG;
                            str5.getClass();
                            LogBuffer.log$default(logBuffer, str4, logLevel, str5);
                        }
                    }
                });
                if (clockRegistry.keepAllLoaded) {
                    return true;
                }
                List<ClockMetadata> list = (List) ClockRegistryKt.KNOWN_PLUGINS.get(pluginLifecycleManager.getPackage());
                Logger logger = clockRegistry.logger;
                if (list == null) {
                    LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, new ClockRegistry$$ExternalSyntheticLambda0(8), null);
                    logMessageObtain.setStr1(pluginLifecycleManager.getPackage());
                    logger.getBuffer().commit(logMessageObtain);
                    return true;
                }
                LogMessage logMessageObtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, new ClockRegistry$$ExternalSyntheticLambda0(9), null);
                logMessageObtain2.setStr1(pluginLifecycleManager.getPackage());
                logger.getBuffer().commit(logMessageObtain2);
                Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                boolean z4 = false;
                for (ClockMetadata clockMetadata : list) {
                    String clockId = clockMetadata.getClockId();
                    ConcurrentHashMap concurrentHashMap = clockRegistry.availableClocks;
                    ClockRegistry.ClockInfo clockInfo = new ClockRegistry.ClockInfo(clockMetadata, null, pluginLifecycleManager);
                    Object objPutIfAbsent = concurrentHashMap.putIfAbsent(clockId, clockInfo);
                    if (objPutIfAbsent == 0) {
                        ref$BooleanRef.element = true;
                        ClockRegistry.access$onConnected(clockRegistry, clockInfo);
                        Unit unit = Unit.INSTANCE;
                    }
                    if (objPutIfAbsent != 0) {
                        clockInfo = objPutIfAbsent;
                    }
                    ClockRegistry.ClockInfo clockInfo2 = clockInfo;
                    PluginLifecycleManager pluginLifecycleManager2 = clockInfo2.manager;
                    if (pluginLifecycleManager.equals(pluginLifecycleManager2)) {
                        z4 = z4 || Intrinsics.areEqual(clockRegistry.getCurrentClockId(), clockMetadata.getClockId());
                        clockInfo2.provider = null;
                    } else {
                        LogMessage logMessageObtain3 = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, new ClockRegistry$$ExternalSyntheticLambda0(10), null);
                        logMessageObtain3.setStr1(clockId);
                        if (pluginLifecycleManager2 == null || (string = pluginLifecycleManager2.toString()) == null) {
                            ClockProvider clockProvider2 = clockInfo2.provider;
                            string = clockProvider2 != null ? clockProvider2.toString() : null;
                        }
                        logMessageObtain3.setStr2(string);
                        logMessageObtain3.setStr3(pluginLifecycleManager.toString());
                        logger.getBuffer().commit(logMessageObtain3);
                    }
                }
                if (ref$BooleanRef.element) {
                    ClockRegistry.access$triggerOnAvailableClocksChanged(clockRegistry);
                }
                clockRegistry.verifyLoadedProviders();
                return z4;
            }

            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginDetached(PluginLifecycleManager pluginLifecycleManager) {
                int i;
                ArrayList arrayList = new ArrayList();
                ClockRegistry clockRegistry = this.this$0;
                Iterator it = clockRegistry.availableClocks.entrySet().iterator();
                while (true) {
                    i = 0;
                    if (!it.hasNext()) {
                        break;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    if (Intrinsics.areEqual(((ClockRegistry.ClockInfo) entry.getValue()).manager, pluginLifecycleManager)) {
                        arrayList.add(entry.getValue());
                        i = 1;
                    }
                    if (i == 1) {
                        it.remove();
                    }
                }
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ClockRegistry.ClockInfo clockInfo = (ClockRegistry.ClockInfo) obj;
                    boolean zAreEqual = Intrinsics.areEqual(clockRegistry.getCurrentClockId(), clockInfo.metadata.getClockId());
                    Logger logger = clockRegistry.logger;
                    LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), zAreEqual ? LogLevel.INFO : LogLevel.DEBUG, new ClockRegistry$$ExternalSyntheticLambda0(3), null);
                    logMessageObtain.setStr1(clockInfo.metadata.getClockId());
                    logMessageObtain.setStr2(String.valueOf(clockInfo.manager));
                    logMessageObtain.setBool1(zAreEqual);
                    logger.getBuffer().commit(logMessageObtain);
                }
                if (arrayList.size() > 0) {
                    ClockRegistry.access$triggerOnAvailableClocksChanged(clockRegistry);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginLoaded(Plugin plugin, Context context2, PluginLifecycleManager pluginLifecycleManager) {
                String string;
                ClockProviderPlugin clockProviderPlugin = (ClockProviderPlugin) plugin;
                ClockRegistry clockRegistry = this.this$0;
                clockProviderPlugin.initialize(clockRegistry.clockBuffers);
                Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                for (ClockMetadata clockMetadata : clockProviderPlugin.getClocks()) {
                    String clockId = clockMetadata.getClockId();
                    ConcurrentHashMap concurrentHashMap = clockRegistry.availableClocks;
                    ClockRegistry.ClockInfo clockInfo = new ClockRegistry.ClockInfo(clockMetadata, clockProviderPlugin, pluginLifecycleManager);
                    Object objPutIfAbsent = concurrentHashMap.putIfAbsent(clockId, clockInfo);
                    if (objPutIfAbsent == 0) {
                        ref$BooleanRef.element = true;
                        ClockRegistry.access$onConnected(clockRegistry, clockInfo);
                        Unit unit = Unit.INSTANCE;
                    }
                    if (objPutIfAbsent != 0) {
                        clockInfo = objPutIfAbsent;
                    }
                    ClockRegistry.ClockInfo clockInfo2 = clockInfo;
                    PluginLifecycleManager pluginLifecycleManager2 = clockInfo2.manager;
                    boolean zAreEqual = Intrinsics.areEqual(pluginLifecycleManager, pluginLifecycleManager2);
                    Logger logger = clockRegistry.logger;
                    String string2 = null;
                    if (zAreEqual) {
                        String replacementTarget = clockMetadata.getReplacementTarget();
                        if (replacementTarget != null) {
                            clockRegistry.replacementMap.put(clockId, replacementTarget);
                        }
                        clockInfo2.provider = clockProviderPlugin;
                        String currentClockId = clockRegistry.getCurrentClockId();
                        ClockMetadata clockMetadata2 = clockInfo2.metadata;
                        boolean zAreEqual2 = Intrinsics.areEqual(currentClockId, clockMetadata2.getClockId());
                        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), zAreEqual2 ? LogLevel.INFO : LogLevel.DEBUG, new ClockRegistry$$ExternalSyntheticLambda0(6), null);
                        logMessageObtain.setStr1(clockMetadata2.getClockId());
                        logMessageObtain.setStr2(String.valueOf(pluginLifecycleManager2));
                        logMessageObtain.setBool1(zAreEqual2);
                        logger.getBuffer().commit(logMessageObtain);
                        if (zAreEqual2) {
                            clockRegistry.triggerOnCurrentClockChanged();
                        }
                    } else {
                        LogMessage logMessageObtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, new ClockRegistry$$ExternalSyntheticLambda0(7), null);
                        logMessageObtain2.setStr1(clockId);
                        if (pluginLifecycleManager2 == null || (string = pluginLifecycleManager2.toString()) == null) {
                            ClockProvider clockProvider2 = clockInfo2.provider;
                            if (clockProvider2 != null) {
                                string2 = clockProvider2.toString();
                            }
                        } else {
                            string2 = string;
                        }
                        logMessageObtain2.setStr2(string2);
                        logMessageObtain2.setStr3(pluginLifecycleManager.toString());
                        logger.getBuffer().commit(logMessageObtain2);
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
                ClockProvider clockProvider2;
                PluginLifecycleManager pluginLifecycleManager2;
                String string;
                Iterator<ClockMetadata> it = ((ClockProviderPlugin) plugin).getClocks().iterator();
                while (true) {
                    boolean zHasNext = it.hasNext();
                    ClockRegistry clockRegistry = this.this$0;
                    if (!zHasNext) {
                        clockRegistry.verifyLoadedProviders();
                        return;
                    }
                    String clockId = it.next().getClockId();
                    ClockRegistry.ClockInfo clockInfo = (ClockRegistry.ClockInfo) clockRegistry.availableClocks.get(clockId);
                    String string2 = null;
                    boolean zAreEqual = Intrinsics.areEqual(clockInfo != null ? clockInfo.manager : null, pluginLifecycleManager);
                    Logger logger = clockRegistry.logger;
                    if (zAreEqual) {
                        clockInfo.provider = null;
                        String currentClockId = clockRegistry.getCurrentClockId();
                        ClockMetadata clockMetadata = clockInfo.metadata;
                        boolean zAreEqual2 = Intrinsics.areEqual(currentClockId, clockMetadata.getClockId());
                        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), zAreEqual2 ? LogLevel.WARNING : LogLevel.DEBUG, new ClockRegistry$$ExternalSyntheticLambda0(4), null);
                        logMessageObtain.setStr1(clockMetadata.getClockId());
                        logMessageObtain.setStr2(String.valueOf(clockInfo.manager));
                        logMessageObtain.setBool1(zAreEqual2);
                        logger.getBuffer().commit(logMessageObtain);
                        if (zAreEqual2) {
                            clockRegistry.triggerOnCurrentClockChanged();
                        }
                    } else {
                        LogMessage logMessageObtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, new ClockRegistry$$ExternalSyntheticLambda0(11), null);
                        logMessageObtain2.setStr1(clockId);
                        if (clockInfo != null && (pluginLifecycleManager2 = clockInfo.manager) != null && (string = pluginLifecycleManager2.toString()) != null) {
                            string2 = string;
                        } else if (clockInfo != null && (clockProvider2 = clockInfo.provider) != null) {
                            string2 = clockProvider2.toString();
                        }
                        logMessageObtain2.setStr2(string2);
                        logMessageObtain2.setStr3(pluginLifecycleManager.toString());
                        logger.getBuffer().commit(logMessageObtain2);
                    }
                }
            }
        };
        this.userSwitchObserver = new UserSwitchObserver() { // from class: com.android.systemui.shared.clocks.ClockRegistry$userSwitchObserver$1
            public final void onUserSwitchComplete(int i) {
                ClockRegistry clockRegistry = this.this$0;
                BuildersKt.launch$default(clockRegistry.scope, clockRegistry.bgDispatcher, null, new ClockRegistry$userSwitchObserver$1$onUserSwitchComplete$1(clockRegistry, null), 2);
            }
        };
        this.isClockChanged = new AtomicBoolean(false);
        this.isClockListChanged = new AtomicBoolean(false);
        clockProvider.initialize(clockMessageBuffers);
        for (ClockMetadata clockMetadata : clockProvider.getClocks()) {
            this.availableClocks.put(clockMetadata.getClockId(), new ClockInfo(clockMetadata, clockProvider, null));
            String replacementTarget = clockMetadata.getReplacementTarget();
            if (replacementTarget != null) {
                this.replacementMap.put(clockMetadata.getClockId(), replacementTarget);
            }
        }
        if (this.availableClocks.containsKey("DEFAULT")) {
            this.isQueued = new AtomicBoolean(false);
            return;
        }
        throw new IllegalArgumentException(clockProvider + " did not register clock at DEFAULT");
    }

    public static final void access$onConnected(ClockRegistry clockRegistry, ClockInfo clockInfo) {
        boolean zAreEqual = Intrinsics.areEqual(clockRegistry.getCurrentClockId(), clockInfo.metadata.getClockId());
        Logger logger = clockRegistry.logger;
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), zAreEqual ? LogLevel.INFO : LogLevel.DEBUG, new ClockRegistry$$ExternalSyntheticLambda0(5), null);
        logMessageObtain.setStr1(clockInfo.metadata.getClockId());
        logMessageObtain.setStr2(String.valueOf(clockInfo.manager));
        logMessageObtain.setBool1(zAreEqual);
        logger.getBuffer().commit(logMessageObtain);
    }

    public static final void access$triggerOnAvailableClocksChanged(ClockRegistry clockRegistry) {
        if (clockRegistry.isClockListChanged.compareAndSet(false, true)) {
            BuildersKt.launch$default(clockRegistry.scope, clockRegistry.mainDispatcher, null, new ClockRegistry$triggerOnAvailableClocksChanged$1(clockRegistry, null), 2);
        }
    }

    public final ClockController createClock(String str) {
        String str2;
        ClockProvider clockProvider;
        ClockSettings clockSettings = this.settings;
        ClockSettings clockSettings2 = clockSettings == null ? new ClockSettings(null, null, null, 7, null) : clockSettings;
        if (Intrinsics.areEqual(str, clockSettings2.getClockId())) {
            str2 = str;
        } else {
            str2 = str;
            clockSettings2 = ClockSettings.copy$default(clockSettings2, str2, null, null, 6, null);
        }
        ClockInfo clockInfo = (ClockInfo) this.availableClocks.get(str2);
        if (clockInfo == null || (clockProvider = clockInfo.provider) == null) {
            return null;
        }
        return clockProvider.createClock(clockSettings2);
    }

    public final ClockController createCurrentClock() {
        String currentClockId = getCurrentClockId();
        if (this.isEnabled && currentClockId.length() > 0) {
            ClockController clockControllerCreateClock = createClock(currentClockId);
            Logger logger = this.logger;
            if (clockControllerCreateClock != null) {
                ClockRegistry$$ExternalSyntheticLambda0 clockRegistry$$ExternalSyntheticLambda0 = new ClockRegistry$$ExternalSyntheticLambda0(0);
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, clockRegistry$$ExternalSyntheticLambda0, null);
                logMessageObtain.setStr1(currentClockId);
                logger.getBuffer().commit(logMessageObtain);
                return clockControllerCreateClock;
            }
            if (this.availableClocks.containsKey(currentClockId)) {
                ClockRegistry$$ExternalSyntheticLambda0 clockRegistry$$ExternalSyntheticLambda02 = new ClockRegistry$$ExternalSyntheticLambda0(1);
                LogMessage logMessageObtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, clockRegistry$$ExternalSyntheticLambda02, null);
                logMessageObtain2.setStr1(currentClockId);
                logger.getBuffer().commit(logMessageObtain2);
                verifyLoadedProviders();
            } else {
                ClockRegistry$$ExternalSyntheticLambda0 clockRegistry$$ExternalSyntheticLambda03 = new ClockRegistry$$ExternalSyntheticLambda0(2);
                LogMessage logMessageObtain3 = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, clockRegistry$$ExternalSyntheticLambda03, null);
                logMessageObtain3.setStr1(currentClockId);
                logger.getBuffer().commit(logMessageObtain3);
            }
        }
        ClockController clockControllerCreateClock2 = createClock("DEFAULT");
        clockControllerCreateClock2.getClass();
        return clockControllerCreateClock2;
    }

    public final String getCurrentClockId() {
        String clockId;
        ClockSettings clockSettings = this.settings;
        return (clockSettings == null || (clockId = clockSettings.getClockId()) == null) ? this.fallbackClockId : clockId;
    }

    public final void querySettings() {
        this.f104assert.isNotMainThread();
        ClockSettings clockSettingsFromJson = null;
        try {
            String stringForUser = this.handleAllUsers ? Settings.Secure.getStringForUser(this.context.getContentResolver(), "lock_screen_custom_clock_face", ActivityManager.getCurrentUser()) : Settings.Secure.getString(this.context.getContentResolver(), "lock_screen_custom_clock_face");
            if (stringForUser != null) {
                clockSettingsFromJson = ClockSettings.Companion.fromJson(new JSONObject(stringForUser));
            }
        } catch (Exception e) {
            this.logger.e("Failed to parse clock settings", e);
        }
        if (Intrinsics.areEqual(this.settings, clockSettingsFromJson)) {
            return;
        }
        this.settings = clockSettingsFromJson;
        verifyLoadedProviders();
        triggerOnCurrentClockChanged();
    }

    public final void registerListeners() {
        if (!this.isEnabled || this.isRegistered) {
            return;
        }
        this.isRegistered = true;
        this.pluginManager.addPluginListener((PluginListener) this.pluginListener, ClockProviderPlugin.class, true);
        BuildersKt.launch$default(this.scope, this.bgDispatcher, null, new AnonymousClass1(null), 2);
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
            BuildersKt.launch$default(this.scope, this.mainDispatcher, null, new C10431(null), 2);
        }
    }

    public final void verifyLoadedProviders() {
        if (!this.isQueued.compareAndSet(false, true)) {
            Logger.v$default(this.logger, "verifyLoadedProviders: shouldSchedule=false", null, 2, null);
        } else {
            BuildersKt.launch$default(this.scope, this.bgDispatcher, null, new C10441(null), 2);
        }
    }

    public /* synthetic */ ClockRegistry(Context context, PluginManager pluginManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, boolean z, boolean z2, ClockProvider clockProvider, String str, ClockMessageBuffers clockMessageBuffers, boolean z3, String str2, ThreadAssert threadAssert, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, pluginManager, coroutineScope, coroutineDispatcher, coroutineDispatcher2, z, z2, clockProvider, (i & 256) != 0 ? "DEFAULT" : str, (i & 512) != 0 ? null : clockMessageBuffers, z3, str2, (i & 4096) != 0 ? new ThreadAssert() : threadAssert);
    }
}
