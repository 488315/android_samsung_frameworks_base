package com.android.systemui.plank.protocol;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Display;
import android.view.InputChannel;
import android.view.InputMonitor;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.SystemUIInitializer;
import com.android.systemui.navigationbar.store.NavBarCommandDispatcher;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.plank.ApiInfo;
import com.android.systemui.plank.ApiLogger;
import com.android.systemui.plank.command.GlobalActionCommandDispatcher;
import com.android.systemui.plank.command.PlankCommandDispatcher;
import com.android.systemui.plank.command.PlankDispatcherFactory;
import com.android.systemui.plank.dagger.PlankComponent;
import com.android.systemui.plank.monitor.TestInputHandler;
import com.android.systemui.plank.monitor.TestInputMonitor;
import com.android.systemui.plank.protocol.Protocol;
import com.android.systemui.plank.protocol.ProtocolManagerImpl;
import com.android.systemui.plank.utils.GsonWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class TestProtocolProvider extends ContentProvider implements SystemUIAppComponentFactoryBase.ContextInitializer {
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback;
    public SystemUIInitializer mInitializer;
    public PlankComponent plankComponent;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback = this.contextAvailableCallback;
        if (contextAvailableCallback == null) {
            contextAvailableCallback = null;
        }
        if (context == null) {
            throw new IllegalStateException("Required value was null.");
        }
        SystemUIInitializer systemUIInitializerOnContextAvailable = contextAvailableCallback.onContextAvailable(context);
        this.mInitializer = systemUIInitializerOnContextAvailable;
        systemUIInitializerOnContextAvailable.getSysUIComponent().inject(this);
        super.attachInfo(context, providerInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x00e4  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bundle call(String str, String str2, Bundle bundle) {
        Optional optionalEmpty;
        Optional optionalEmpty2;
        Protocol.Command commandValueOf;
        PlankDispatcherFactory.DispatcherType dispatcherTypeValueOf;
        Protocol.Command commandValueOf2;
        String string;
        boolean z = true;
        if (this.plankComponent == null) {
            SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback = this.contextAvailableCallback;
            if (contextAvailableCallback == null) {
                contextAvailableCallback = null;
            }
            Context context = getContext();
            if (context == null) {
                throw new IllegalStateException("Required value was null.");
            }
            contextAvailableCallback.onContextAvailable(context);
            SystemUIInitializer systemUIInitializer = this.mInitializer;
            if (systemUIInitializer == null) {
                systemUIInitializer = null;
            }
            systemUIInitializer.getSysUIComponent().inject(this);
        }
        PlankComponent plankComponent = this.plankComponent;
        if (plankComponent == null) {
            plankComponent = null;
        }
        if (plankComponent.featureEnabled) {
            optionalEmpty = Optional.of(plankComponent.lazyProtocolManager.get());
            optionalEmpty.getClass();
        } else {
            optionalEmpty = Optional.empty();
            optionalEmpty.getClass();
        }
        if (!optionalEmpty.isPresent()) {
            throw new RuntimeException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " doesn't support!!!"));
        }
        PlankComponent plankComponent2 = this.plankComponent;
        if (plankComponent2 == null) {
            plankComponent2 = null;
        }
        if (plankComponent2.featureEnabled) {
            optionalEmpty2 = Optional.of(plankComponent2.lazyProtocolManager.get());
            optionalEmpty2.getClass();
        } else {
            optionalEmpty2 = Optional.empty();
            optionalEmpty2.getClass();
        }
        ProtocolManagerImpl protocolManagerImpl = (ProtocolManagerImpl) optionalEmpty2.get();
        protocolManagerImpl.getClass();
        if (!"__plank__".equals(str2)) {
            if (str2 == null) {
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("key_monitor_result", false);
                return bundle2;
            }
            protocolManagerImpl.protocol.getClass();
            try {
                commandValueOf = Protocol.Command.valueOf(str);
            } catch (IllegalArgumentException unused) {
                commandValueOf = Protocol.Command.none;
            }
            if (ProtocolManagerImpl.WhenMappings.$EnumSwitchMapping$0[commandValueOf.ordinal()] != 3) {
                PlankDispatcherFactory plankDispatcherFactory = protocolManagerImpl.plankDispatcherFactory;
                plankDispatcherFactory.getClass();
                try {
                    dispatcherTypeValueOf = PlankDispatcherFactory.DispatcherType.valueOf(str2);
                } catch (IllegalArgumentException unused2) {
                    dispatcherTypeValueOf = PlankDispatcherFactory.DispatcherType.none;
                }
                if (plankDispatcherFactory.dependencies == null) {
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    plankDispatcherFactory.dependencies = linkedHashMap;
                    linkedHashMap.put(PlankDispatcherFactory.DispatcherType.global_action, new GlobalActionCommandDispatcher());
                    if (BasicRune.NAVBAR_COMMAND) {
                        Map map = plankDispatcherFactory.dependencies;
                        if (map == null) {
                            map = null;
                        }
                        map.put(PlankDispatcherFactory.DispatcherType.navigation_bar, new NavBarCommandDispatcher((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class)));
                    }
                }
                Map map2 = plankDispatcherFactory.dependencies;
                PlankCommandDispatcher plankCommandDispatcher = (PlankCommandDispatcher) (map2 != null ? map2 : null).get(dispatcherTypeValueOf);
                return plankCommandDispatcher != null ? plankCommandDispatcher.dispatch(bundle, str) : new Bundle();
            }
            Bundle bundle3 = new Bundle();
            long j = bundle != null ? bundle.getLong("key_long_type", 0L) : 0L;
            protocolManagerImpl.apiLogger.getClass();
            List list = ApiLogger.list;
            if (list == null || !((ArrayList) list).isEmpty()) {
                ArrayList arrayList = (ArrayList) list;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ApiInfo apiInfo = (ApiInfo) obj;
                    if (Intrinsics.areEqual(apiInfo.name, str2) && apiInfo.timestamp >= j) {
                        break;
                    }
                }
                z = false;
            } else {
                z = false;
            }
            bundle3.putBoolean("key_boolean_type", z);
            return bundle3;
        }
        Bundle bundle4 = new Bundle();
        protocolManagerImpl.protocol.getClass();
        try {
            commandValueOf2 = Protocol.Command.valueOf(str);
        } catch (IllegalArgumentException unused3) {
            commandValueOf2 = Protocol.Command.none;
        }
        int i2 = ProtocolManagerImpl.WhenMappings.$EnumSwitchMapping$0[commandValueOf2.ordinal()];
        if (i2 == 1) {
            protocolManagerImpl.apiLogger.getClass();
            ((ArrayList) ApiLogger.list).clear();
            TestInputMonitor testInputMonitor = protocolManagerImpl.testInputMonitor;
            if (testInputMonitor.mTestInputEventReceiver == null) {
                StringBuilder sb = new StringBuilder();
                String str3 = TestInputMonitor.tag;
                HandlerThread handlerThread = new HandlerThread(TransitionKt$$ExternalSyntheticOutline0.m(sb, str3, ".Thread"));
                testInputMonitor.mHandlerThread = handlerThread;
                handlerThread.start();
                InputManager inputManager = InputManager.getInstance();
                Display display = testInputMonitor.mContext.getDisplay();
                display.getClass();
                testInputMonitor.mInputMonitor = inputManager.monitorGestureInput(str3, display.getDisplayId());
                testInputMonitor.mInputHandler = new TestInputHandler(testInputMonitor.mContext);
                TestInputHandler testInputHandler = testInputMonitor.mInputHandler;
                testInputHandler.getClass();
                InputMonitor inputMonitor = testInputMonitor.mInputMonitor;
                inputMonitor.getClass();
                InputChannel inputChannel = inputMonitor.getInputChannel();
                HandlerThread handlerThread2 = testInputMonitor.mHandlerThread;
                handlerThread2.getClass();
                testInputMonitor.mTestInputEventReceiver = new TestInputMonitor.TestInputEventReceiver(testInputMonitor, testInputHandler, inputChannel, handlerThread2.getLooper());
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.setFlags(268435456);
                testInputMonitor.mContext.startActivity(intent);
                Log.d(str3, ":: start ::");
            }
            bundle4.putBoolean("key_monitor_result", true);
            return bundle4;
        }
        if (i2 != 2) {
            bundle4.putBoolean("key_monitor_result", false);
            return bundle4;
        }
        bundle4.putBoolean("key_monitor_result", true);
        TestInputHandler testInputHandler2 = protocolManagerImpl.testInputMonitor.mInputHandler;
        if (testInputHandler2 != null) {
            StringBuilder sb2 = new StringBuilder();
            new GsonWrapper();
            Gson gsonCreate = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            List list2 = testInputHandler2.mEventHistory;
            if (list2 != null) {
                synchronized (list2) {
                    sb2.append(gsonCreate.toJson(testInputHandler2.mEventHistory));
                }
            }
            string = sb2.toString();
            if (string == null) {
                string = "";
            }
        }
        bundle4.putString("key_monitor_data", string);
        protocolManagerImpl.apiLogger.getClass();
        new GsonWrapper();
        bundle4.putString("key_logging_data", new GsonBuilder().serializeNulls().setPrettyPrinting().create().toJson(ApiLogger.list));
        TestInputMonitor testInputMonitor2 = protocolManagerImpl.testInputMonitor;
        InputMonitor inputMonitor2 = testInputMonitor2.mInputMonitor;
        if (inputMonitor2 != null) {
            inputMonitor2.dispose();
        }
        testInputMonitor2.mInputMonitor = null;
        TestInputMonitor.TestInputEventReceiver testInputEventReceiver = testInputMonitor2.mTestInputEventReceiver;
        if (testInputEventReceiver != null) {
            testInputEventReceiver.dispose();
        }
        testInputMonitor2.mTestInputEventReceiver = null;
        HandlerThread handlerThread3 = testInputMonitor2.mHandlerThread;
        if (handlerThread3 != null) {
            handlerThread3.quitSafely();
        }
        testInputMonitor2.mHandlerThread = null;
        TestInputHandler testInputHandler3 = testInputMonitor2.mInputHandler;
        if (testInputHandler3 != null) {
            synchronized (testInputHandler3.mEventHistory) {
                ((ArrayList) testInputHandler3.mEventHistory).clear();
                Unit unit = Unit.INSTANCE;
            }
        }
        testInputMonitor2.mInputHandler = null;
        Log.d(TestInputMonitor.tag, ":: stop ::");
        return bundle4;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return false;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public final void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.contextAvailableCallback = contextAvailableCallback;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
