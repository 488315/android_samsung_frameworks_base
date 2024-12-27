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
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
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
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TestProtocolProvider extends ContentProvider implements SystemUIAppComponentFactoryBase.ContextInitializer {
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback;
    public SystemUIInitializer mInitializer;
    public PlankComponent plankComponent;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback = this.contextAvailableCallback;
        if (contextAvailableCallback == null) {
            contextAvailableCallback = null;
        }
        if (context == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        SystemUIInitializer onContextAvailable = contextAvailableCallback.onContextAvailable(context);
        this.mInitializer = onContextAvailable;
        onContextAvailable.getSysUIComponent().inject(this);
        super.attachInfo(context, providerInfo);
    }

    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        Optional empty;
        Optional empty2;
        Protocol.Command command;
        PlankDispatcherFactory.DispatcherType dispatcherType;
        Protocol.Command command2;
        String str3;
        if (this.plankComponent == null) {
            SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback = this.contextAvailableCallback;
            if (contextAvailableCallback == null) {
                contextAvailableCallback = null;
            }
            Context context = getContext();
            if (context == null) {
                throw new IllegalStateException("Required value was null.".toString());
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
            empty = Optional.of(plankComponent.lazyProtocolManager.get());
            Intrinsics.checkNotNull(empty);
        } else {
            empty = Optional.empty();
            Intrinsics.checkNotNull(empty);
        }
        if (!empty.isPresent()) {
            throw new RuntimeException(str.concat(" doesn't support!!!"));
        }
        PlankComponent plankComponent2 = this.plankComponent;
        if (plankComponent2 == null) {
            plankComponent2 = null;
        }
        if (plankComponent2.featureEnabled) {
            empty2 = Optional.of(plankComponent2.lazyProtocolManager.get());
            Intrinsics.checkNotNull(empty2);
        } else {
            empty2 = Optional.empty();
            Intrinsics.checkNotNull(empty2);
        }
        ProtocolManagerImpl protocolManagerImpl = (ProtocolManagerImpl) empty2.get();
        protocolManagerImpl.getClass();
        boolean z = true;
        if (!"__plank__".equals(str2)) {
            if (str2 == null) {
                Bundle bundle2 = new Bundle();
                bundle2.putBoolean("key_monitor_result", false);
                return bundle2;
            }
            protocolManagerImpl.protocol.getClass();
            try {
                command = Protocol.Command.valueOf(str);
            } catch (IllegalArgumentException unused) {
                command = Protocol.Command.none;
            }
            if (ProtocolManagerImpl.WhenMappings.$EnumSwitchMapping$0[command.ordinal()] == 3) {
                Bundle bundle3 = new Bundle();
                long j = bundle != null ? bundle.getLong("key_long_type", 0L) : 0L;
                protocolManagerImpl.apiLogger.getClass();
                List list = ApiLogger.list;
                if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
                    Iterator it = ((ArrayList) list).iterator();
                    while (it.hasNext()) {
                        ApiInfo apiInfo = (ApiInfo) it.next();
                        if (Intrinsics.areEqual(apiInfo.name, str2) && apiInfo.timestamp >= j) {
                            break;
                        }
                    }
                }
                z = false;
                bundle3.putBoolean("key_boolean_type", z);
                return bundle3;
            }
            PlankDispatcherFactory plankDispatcherFactory = protocolManagerImpl.plankDispatcherFactory;
            plankDispatcherFactory.getClass();
            try {
                dispatcherType = PlankDispatcherFactory.DispatcherType.valueOf(str2);
            } catch (IllegalArgumentException unused2) {
                dispatcherType = PlankDispatcherFactory.DispatcherType.none;
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
            PlankCommandDispatcher plankCommandDispatcher = (PlankCommandDispatcher) (map2 != null ? map2 : null).get(dispatcherType);
            return plankCommandDispatcher != null ? plankCommandDispatcher.dispatch(bundle, str) : new Bundle();
        }
        Bundle bundle4 = new Bundle();
        protocolManagerImpl.protocol.getClass();
        try {
            command2 = Protocol.Command.valueOf(str);
        } catch (IllegalArgumentException unused3) {
            command2 = Protocol.Command.none;
        }
        int i = ProtocolManagerImpl.WhenMappings.$EnumSwitchMapping$0[command2.ordinal()];
        if (i == 1) {
            protocolManagerImpl.apiLogger.getClass();
            ((ArrayList) ApiLogger.list).clear();
            TestInputMonitor testInputMonitor = protocolManagerImpl.testInputMonitor;
            if (testInputMonitor.mTestInputEventReceiver == null) {
                StringBuilder sb = new StringBuilder();
                String str4 = TestInputMonitor.tag;
                HandlerThread handlerThread = new HandlerThread(ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, str4, ".Thread"));
                testInputMonitor.mHandlerThread = handlerThread;
                handlerThread.start();
                InputManager inputManager = InputManager.getInstance();
                Display display = testInputMonitor.mContext.getDisplay();
                Intrinsics.checkNotNull(display);
                testInputMonitor.mInputMonitor = inputManager.monitorGestureInput(str4, display.getDisplayId());
                testInputMonitor.mInputHandler = new TestInputHandler(testInputMonitor.mContext);
                TestInputHandler testInputHandler = testInputMonitor.mInputHandler;
                Intrinsics.checkNotNull(testInputHandler);
                InputMonitor inputMonitor = testInputMonitor.mInputMonitor;
                Intrinsics.checkNotNull(inputMonitor);
                InputChannel inputChannel = inputMonitor.getInputChannel();
                HandlerThread handlerThread2 = testInputMonitor.mHandlerThread;
                Intrinsics.checkNotNull(handlerThread2);
                testInputMonitor.mTestInputEventReceiver = new TestInputMonitor.TestInputEventReceiver(testInputMonitor, testInputHandler, inputChannel, handlerThread2.getLooper());
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                testInputMonitor.mContext.startActivity(intent);
                Log.d(str4, ":: start ::");
            }
            bundle4.putBoolean("key_monitor_result", true);
            return bundle4;
        }
        if (i != 2) {
            bundle4.putBoolean("key_monitor_result", false);
            return bundle4;
        }
        bundle4.putBoolean("key_monitor_result", true);
        TestInputHandler testInputHandler2 = protocolManagerImpl.testInputMonitor.mInputHandler;
        if (testInputHandler2 != null) {
            StringBuilder sb2 = new StringBuilder();
            new GsonWrapper();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls = true;
            gsonBuilder.prettyPrinting = true;
            Gson create = gsonBuilder.create();
            List list2 = testInputHandler2.mEventHistory;
            if (list2 != null) {
                synchronized (list2) {
                    sb2.append(create.toJson(testInputHandler2.mEventHistory));
                }
            }
            str3 = sb2.toString();
        } else {
            str3 = "";
        }
        bundle4.putString("key_monitor_data", str3);
        protocolManagerImpl.apiLogger.getClass();
        new GsonWrapper();
        GsonBuilder gsonBuilder2 = new GsonBuilder();
        gsonBuilder2.serializeNulls = true;
        gsonBuilder2.prettyPrinting = true;
        bundle4.putString("key_logging_data", gsonBuilder2.create().toJson(ApiLogger.list));
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
