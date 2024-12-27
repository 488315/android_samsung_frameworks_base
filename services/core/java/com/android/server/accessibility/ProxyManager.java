package com.android.server.accessibility;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityTrace;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.companion.virtual.VirtualDeviceManager;
import android.content.ComponentName;
import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.accessibility.AccessibilityEvent;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IntPair;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.companion.virtual.VirtualDeviceManagerService;
import com.android.server.wm.WindowManagerInternal;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProxyManager {
    public static final boolean DEBUG;
    public AccessibilityInputFilter mA11yInputFilter;
    public final AccessibilityWindowManager mA11yWindowManager;
    public ProxyManager$$ExternalSyntheticLambda4 mAppsOnVirtualDeviceListener;
    public final Context mContext;
    public final Object mLock;
    public final Handler mMainHandler;
    public final SystemSupport mSystemSupport;
    public final UiAutomationManager mUiAutomationManager;
    public AnonymousClass2 mVirtualDeviceListener;
    public final SparseIntArray mLastStates = new SparseIntArray();
    public final SparseArray mProxyA11yServiceConnections = new SparseArray();
    public VirtualDeviceManagerInternal mLocalVdm =
            (VirtualDeviceManagerInternal)
                    LocalServices.getService(VirtualDeviceManagerInternal.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface SystemSupport {}

    static {
        DEBUG = Log.isLoggable("ProxyManager", 3) && Build.IS_DEBUGGABLE;
    }

    public ProxyManager(
            Object obj,
            AccessibilityWindowManager accessibilityWindowManager,
            Context context,
            AccessibilityManagerService.MainHandler mainHandler,
            UiAutomationManager uiAutomationManager,
            SystemSupport systemSupport) {
        this.mLock = obj;
        this.mA11yWindowManager = accessibilityWindowManager;
        this.mContext = context;
        this.mMainHandler = mainHandler;
        this.mUiAutomationManager = uiAutomationManager;
        this.mSystemSupport = systemSupport;
    }

    public static void printClientsForDeviceId(
            PrintWriter printWriter, RemoteCallbackList remoteCallbackList, int i) {
        if (remoteCallbackList != null) {
            for (int i2 = 0; i2 < remoteCallbackList.getRegisteredCallbackCount(); i2++) {
                AccessibilityManagerService.Client client =
                        (AccessibilityManagerService.Client)
                                remoteCallbackList.getRegisteredCallbackCookie(i2);
                if (client.mDeviceId == i) {
                    ProxyManager$$ExternalSyntheticOutline0.m(
                            printWriter,
                            Arrays.toString(client.mPackageNames),
                            "\n",
                            new StringBuilder("            "));
                }
            }
        }
    }

    public final void broadcastToClientsLocked(Consumer consumer) {
        SystemSupport systemSupport = this.mSystemSupport;
        RemoteCallbackList remoteCallbackList =
                ((AccessibilityManagerService) systemSupport).getCurrentUserState().mUserClients;
        RemoteCallbackList remoteCallbackList2 =
                ((AccessibilityManagerService) systemSupport).mGlobalClients;
        remoteCallbackList.broadcastForEachCookie(consumer);
        remoteCallbackList2.broadcastForEachCookie(consumer);
    }

    public final boolean clearConnectionAndUpdateState(int i) {
        int i2;
        boolean z;
        VirtualDeviceManagerInternal localVdm;
        ProxyManager$$ExternalSyntheticLambda4 proxyManager$$ExternalSyntheticLambda4;
        VirtualDeviceManager virtualDeviceManager;
        synchronized (this.mLock) {
            try {
                if (this.mProxyA11yServiceConnections.contains(i)) {
                    i2 =
                            ((ProxyAccessibilityServiceConnection)
                                            this.mProxyA11yServiceConnections.get(i))
                                    .mDeviceId;
                    this.mProxyA11yServiceConnections.remove(i);
                    if (this.mProxyA11yServiceConnections.size() == 0
                            && (virtualDeviceManager =
                                            (VirtualDeviceManager)
                                                    this.mContext.getSystemService(
                                                            VirtualDeviceManager.class))
                                    != null
                            && android.companion.virtual.flags.Flags.vdmPublicApis()) {
                        virtualDeviceManager.unregisterVirtualDeviceListener(
                                this.mVirtualDeviceListener);
                    }
                    z = true;
                } else {
                    i2 = -1;
                    z = false;
                }
            } finally {
            }
        }
        if (z) {
            AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
            synchronized (accessibilityWindowManager.mLock) {
                AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver =
                        (AccessibilityWindowManager.DisplayWindowsObserver)
                                accessibilityWindowManager.mDisplayWindowsObservers.get(i);
                if (displayWindowsObserver != null) {
                    displayWindowsObserver.mIsProxy = false;
                }
                accessibilityWindowManager.resetHasProxyIfNeededLocked();
            }
            this.mMainHandler.post(new ProxyManager$$ExternalSyntheticLambda5(this, i, 3));
            if (isProxyedDeviceId(i2)) {
                onProxyChanged(i2, false);
            } else {
                synchronized (this.mLock) {
                    Flags.proxyUseAppsOnVirtualDeviceListener();
                    if (this.mProxyA11yServiceConnections.size() == 0
                            && (localVdm = getLocalVdm()) != null
                            && (proxyManager$$ExternalSyntheticLambda4 =
                                            this.mAppsOnVirtualDeviceListener)
                                    != null) {
                        VirtualDeviceManagerService.LocalService localService =
                                (VirtualDeviceManagerService.LocalService) localVdm;
                        synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                            localService.mAppsOnVirtualDeviceListeners.remove(
                                    proxyManager$$ExternalSyntheticLambda4);
                        }
                        this.mAppsOnVirtualDeviceListener = null;
                    }
                    AccessibilityManagerService accessibilityManagerService =
                            (AccessibilityManagerService) this.mSystemSupport;
                    accessibilityManagerService.resetClientsLocked(
                            i2,
                            accessibilityManagerService.getUserStateLocked(
                                            accessibilityManagerService.mCurrentUserId)
                                    .mUserClients);
                    accessibilityManagerService.resetClientsLocked(
                            i2, accessibilityManagerService.mGlobalClients);
                    accessibilityManagerService.onClientChangeLocked(true, true);
                    this.mLastStates.delete(i2);
                }
            }
        }
        if (DEBUG) {
            Slog.v("ProxyManager", "Unregistered proxy for display id " + i + ": " + z);
        }
        return z;
    }

    public final void clearConnections(int i) {
        int i2;
        IntArray intArray = new IntArray();
        synchronized (this.mLock) {
            for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
                try {
                    ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
                            (ProxyAccessibilityServiceConnection)
                                    this.mProxyA11yServiceConnections.valueAt(i3);
                    if (proxyAccessibilityServiceConnection != null
                            && proxyAccessibilityServiceConnection.mDeviceId == i) {
                        intArray.add(proxyAccessibilityServiceConnection.mDisplayId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        for (i2 = 0; i2 < intArray.size(); i2++) {
            clearConnectionAndUpdateState(intArray.get(i2));
        }
    }

    public final int computeRelevantEventTypesLocked(AccessibilityManagerService.Client client) {
        UiAutomationManager.UiAutomationService uiAutomationService;
        int i;
        UiAutomationManager.UiAutomationService uiAutomationService2;
        int i2 = 0;
        for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
            ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
                    (ProxyAccessibilityServiceConnection)
                            this.mProxyA11yServiceConnections.valueAt(i3);
            if (proxyAccessibilityServiceConnection != null
                    && proxyAccessibilityServiceConnection.mDeviceId == client.mDeviceId) {
                int i4 =
                        i2
                                | proxyAccessibilityServiceConnection.mEventTypes
                                | (proxyAccessibilityServiceConnection.mUsesAccessibilityCache
                                        ? 4307005
                                        : 32);
                UiAutomationManager uiAutomationManager = this.mUiAutomationManager;
                synchronized (uiAutomationManager.mLock) {
                    uiAutomationService = uiAutomationManager.mUiAutomationService;
                }
                if (AccessibilityManagerService.isClientInPackageAllowlist(
                        uiAutomationService == null ? null : uiAutomationService.getServiceInfo(),
                        client)) {
                    synchronized (uiAutomationManager.mLock) {
                        uiAutomationService2 = uiAutomationManager.mUiAutomationService;
                    }
                    if (uiAutomationService2 != null) {
                        i =
                                uiAutomationService2.mEventTypes
                                        | (uiAutomationService2.mUsesAccessibilityCache
                                                ? 4307005
                                                : 32);
                        i2 = i4 | i;
                    }
                }
                i = 0;
                i2 = i4 | i;
            }
        }
        if (DEBUG) {
            Slog.v(
                    "ProxyManager",
                    "Relevant event types for device id "
                            + client.mDeviceId
                            + ": "
                            + AccessibilityEvent.eventTypeToString(i2));
        }
        return i2;
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.mLock) {
            try {
                printWriter.println();
                printWriter.println("Proxy manager state:");
                printWriter.println(
                        "    Number of proxy connections: "
                                + this.mProxyA11yServiceConnections.size());
                printWriter.println("    Registered proxy connections:");
                RemoteCallbackList remoteCallbackList =
                        ((AccessibilityManagerService) this.mSystemSupport)
                                .getCurrentUserState()
                                .mUserClients;
                RemoteCallbackList remoteCallbackList2 =
                        ((AccessibilityManagerService) this.mSystemSupport).mGlobalClients;
                for (int i = 0; i < this.mProxyA11yServiceConnections.size(); i++) {
                    ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
                            (ProxyAccessibilityServiceConnection)
                                    this.mProxyA11yServiceConnections.valueAt(i);
                    if (proxyAccessibilityServiceConnection != null) {
                        proxyAccessibilityServiceConnection.dump(
                                fileDescriptor, printWriter, strArr);
                    }
                    printWriter.println();
                    printWriter.println("        User clients for proxy's virtual device id");
                    printClientsForDeviceId(
                            printWriter,
                            remoteCallbackList,
                            proxyAccessibilityServiceConnection.mDeviceId);
                    printWriter.println();
                    printWriter.println("        Global clients for proxy's virtual device id");
                    printClientsForDeviceId(
                            printWriter,
                            remoteCallbackList2,
                            proxyAccessibilityServiceConnection.mDeviceId);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getFirstDeviceIdForUidLocked(int i) {
        VirtualDeviceManagerInternal localVdm = getLocalVdm();
        if (localVdm == null) {
            return -1;
        }
        Iterator it = localVdm.getDeviceIdsForUid(i).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() != 0 && num.intValue() != -1) {
                return num.intValue();
            }
        }
        return -1;
    }

    public final ProxyAccessibilityServiceConnection getFirstProxyForDeviceIdLocked(int i) {
        for (int i2 = 0; i2 < this.mProxyA11yServiceConnections.size(); i2++) {
            ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
                    (ProxyAccessibilityServiceConnection)
                            this.mProxyA11yServiceConnections.valueAt(i2);
            if (proxyAccessibilityServiceConnection != null
                    && proxyAccessibilityServiceConnection.mDeviceId == i) {
                return proxyAccessibilityServiceConnection;
            }
        }
        return null;
    }

    public final List getInstalledAndEnabledServiceInfosLocked(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < this.mProxyA11yServiceConnections.size(); i3++) {
            ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
                    (ProxyAccessibilityServiceConnection)
                            this.mProxyA11yServiceConnections.valueAt(i3);
            if (proxyAccessibilityServiceConnection != null
                    && proxyAccessibilityServiceConnection.mDeviceId == i2) {
                if (i == -1) {
                    arrayList.addAll(
                            proxyAccessibilityServiceConnection.getInstalledAndEnabledServices());
                } else if ((proxyAccessibilityServiceConnection.mFeedbackType & i) != 0) {
                    for (AccessibilityServiceInfo accessibilityServiceInfo :
                            proxyAccessibilityServiceConnection.getInstalledAndEnabledServices()) {
                        if ((accessibilityServiceInfo.feedbackType & i) != 0) {
                            arrayList.add(accessibilityServiceInfo);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public final VirtualDeviceManagerInternal getLocalVdm() {
        if (this.mLocalVdm == null) {
            this.mLocalVdm =
                    (VirtualDeviceManagerInternal)
                            LocalServices.getService(VirtualDeviceManagerInternal.class);
        }
        return this.mLocalVdm;
    }

    public final long getRecommendedTimeoutMillisLocked(int i) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.mProxyA11yServiceConnections.size(); i4++) {
            ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
                    (ProxyAccessibilityServiceConnection)
                            this.mProxyA11yServiceConnections.valueAt(i4);
            if (proxyAccessibilityServiceConnection != null
                    && proxyAccessibilityServiceConnection.mDeviceId == i) {
                int i5 = proxyAccessibilityServiceConnection.mInteractiveTimeout;
                int i6 = proxyAccessibilityServiceConnection.mNonInteractiveTimeout;
                i2 = Math.max(i5, i2);
                i3 = Math.max(i6, i3);
            }
        }
        return IntPair.of(i2, i3);
    }

    public final int getStateLocked(int i) {
        boolean z;
        int i2 = this.mUiAutomationManager.mUiAutomationService != null ? 1 : 0;
        int i3 = 0;
        while (true) {
            int size = this.mProxyA11yServiceConnections.size();
            z = DEBUG;
            if (i3 >= size) {
                break;
            }
            ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
                    (ProxyAccessibilityServiceConnection)
                            this.mProxyA11yServiceConnections.valueAt(i3);
            if (proxyAccessibilityServiceConnection != null
                    && proxyAccessibilityServiceConnection.mDeviceId == i) {
                int i4 = proxyAccessibilityServiceConnection.mRequestTouchExplorationMode ? 3 : 1;
                if (z) {
                    Slog.v("ProxyManager", "Accessibility is enabled for all proxies: true");
                    ProxyManager$$ExternalSyntheticOutline0.m(
                            "ProxyManager",
                            new StringBuilder("Touch exploration is enabled for all proxies: "),
                            (i4 & 2) != 0);
                }
                i2 |= i4;
            }
            i3++;
        }
        if (z) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "For device id ", " a11y is enabled: ");
            m.append((i2 & 1) != 0);
            Slog.v("ProxyManager", m.toString());
            StringBuilder sb = new StringBuilder("For device id ");
            sb.append(i);
            sb.append(" touch exploration is enabled: ");
            ProxyManager$$ExternalSyntheticOutline0.m("ProxyManager", sb, (i2 & 2) != 0);
        }
        return i2;
    }

    public final boolean isProxyedDeviceId(int i) {
        boolean z;
        if (i == 0 || i == -1) {
            return false;
        }
        synchronized (this.mLock) {
            z = getFirstProxyForDeviceIdLocked(i) != null;
        }
        if (DEBUG) {
            Slog.v("ProxyManager", "Tracking device " + i + " : " + z);
        }
        return z;
    }

    public final boolean isProxyedDisplay(int i) {
        boolean contains;
        synchronized (this.mLock) {
            try {
                contains = this.mProxyA11yServiceConnections.contains(i);
                if (DEBUG) {
                    Slog.v("ProxyManager", "Tracking proxy display " + i + " : " + contains);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return contains;
    }

    public void notifyProxyOfRunningAppsChange(Set set) {
        if (DEBUG) {
            Slog.v("ProxyManager", "notifyProxyOfRunningAppsChange: " + set);
        }
        synchronized (this.mLock) {
            try {
                if (this.mProxyA11yServiceConnections.size() == 0) {
                    return;
                }
                VirtualDeviceManagerInternal localVdm = getLocalVdm();
                if (localVdm == null) {
                    return;
                }
                ArraySet arraySet = new ArraySet();
                for (int i = 0; i < this.mProxyA11yServiceConnections.size(); i++) {
                    ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
                            (ProxyAccessibilityServiceConnection)
                                    this.mProxyA11yServiceConnections.valueAt(i);
                    if (proxyAccessibilityServiceConnection != null) {
                        int i2 = proxyAccessibilityServiceConnection.mDeviceId;
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            if (localVdm.getDeviceIdsForUid(((Integer) it.next()).intValue())
                                    .contains(Integer.valueOf(i2))) {
                                arraySet.add(Integer.valueOf(i2));
                            }
                        }
                    }
                }
                Iterator it2 = arraySet.iterator();
                while (it2.hasNext()) {
                    onProxyChanged(((Integer) it2.next()).intValue(), true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onProxyChanged(int i, boolean z) {
        boolean z2 = DEBUG;
        if (z2) {
            ProxyManager$$ExternalSyntheticOutline0.m(
                    i, "onProxyChanged called for deviceId: ", "ProxyManager");
        }
        synchronized (this.mLock) {
            SystemSupport systemSupport = this.mSystemSupport;
            RemoteCallbackList remoteCallbackList =
                    ((AccessibilityManagerService) systemSupport)
                            .getCurrentUserState()
                            .mUserClients;
            RemoteCallbackList remoteCallbackList2 =
                    ((AccessibilityManagerService) systemSupport).mGlobalClients;
            updateDeviceIdsIfNeededLocked(i, remoteCallbackList);
            updateDeviceIdsIfNeededLocked(i, remoteCallbackList2);
            AccessibilityManagerService accessibilityManagerService =
                    (AccessibilityManagerService) this.mSystemSupport;
            accessibilityManagerService.updateWindowsForAccessibilityCallbackLocked(
                    accessibilityManagerService.getUserStateLocked(
                            accessibilityManagerService.mCurrentUserId));
            if (isProxyedDeviceId(i)) {
                this.mMainHandler.post(new ProxyManager$$ExternalSyntheticLambda5(this, i, 2));
            }
            scheduleUpdateProxyClientsIfNeededLocked(i, z);
            scheduleNotifyProxyClientsOfServicesStateChangeLocked(i);
            if (z2) {
                Slog.v("ProxyManager", "Update proxy focus appearance at device id " + i);
            }
            final ProxyAccessibilityServiceConnection firstProxyForDeviceIdLocked =
                    getFirstProxyForDeviceIdLocked(i);
            if (firstProxyForDeviceIdLocked != null) {
                this.mMainHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                ProxyManager proxyManager = ProxyManager.this;
                                final ProxyAccessibilityServiceConnection
                                        proxyAccessibilityServiceConnection =
                                                firstProxyForDeviceIdLocked;
                                proxyManager.getClass();
                                proxyManager.broadcastToClientsLocked(
                                        FunctionalUtils.ignoreRemoteException(
                                                new FunctionalUtils
                                                        .RemoteExceptionIgnoringConsumer() { // from
                                                                                             // class: com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda13
                                                    public final void acceptOrThrow(Object obj) {
                                                        ProxyAccessibilityServiceConnection
                                                                proxyAccessibilityServiceConnection2 =
                                                                        ProxyAccessibilityServiceConnection
                                                                                .this;
                                                        AccessibilityManagerService.Client client =
                                                                (AccessibilityManagerService.Client)
                                                                        obj;
                                                        if (client.mDeviceId
                                                                == proxyAccessibilityServiceConnection2
                                                                        .mDeviceId) {
                                                            client.mCallback.setFocusAppearance(
                                                                    proxyAccessibilityServiceConnection2
                                                                            .mFocusStrokeWidth,
                                                                    proxyAccessibilityServiceConnection2
                                                                            .mFocusColor);
                                                        }
                                                    }
                                                }));
                            }
                        });
            }
            ((AccessibilityManagerService) this.mSystemSupport)
                    .notifyClearAccessibilityCacheLocked();
        }
    }

    /* JADX WARN: Type inference failed for: r6v11, types: [com.android.server.accessibility.ProxyManager$2] */
    public final void registerProxy(
            final IAccessibilityServiceClient iAccessibilityServiceClient,
            final int i,
            int i2,
            AccessibilitySecurityPolicy accessibilitySecurityPolicy,
            AbstractAccessibilityServiceConnection.SystemSupport systemSupport,
            AccessibilityTrace accessibilityTrace,
            WindowManagerInternal windowManagerInternal) {
        VirtualDeviceManager virtualDeviceManager;
        if (DEBUG) {
            ProxyManager$$ExternalSyntheticOutline0.m(
                    i, "Register proxy for display id: ", "ProxyManager");
        }
        VirtualDeviceManager virtualDeviceManager2 =
                (VirtualDeviceManager) this.mContext.getSystemService(VirtualDeviceManager.class);
        if (virtualDeviceManager2 == null) {
            return;
        }
        int deviceIdForDisplayId = virtualDeviceManager2.getDeviceIdForDisplayId(i);
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.setCapabilities(3);
        accessibilityServiceInfo.setComponentName(
                new ComponentName(
                        "ProxyPackage",
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "ProxyClass")));
        ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection =
                new ProxyAccessibilityServiceConnection(
                        null,
                        this.mContext,
                        accessibilityServiceInfo.getComponentName(),
                        accessibilityServiceInfo,
                        i2,
                        this.mMainHandler,
                        this.mLock,
                        accessibilitySecurityPolicy,
                        systemSupport,
                        accessibilityTrace,
                        windowManagerInternal,
                        null,
                        this.mA11yWindowManager,
                        null);
        proxyAccessibilityServiceConnection.mDisplayId = i;
        proxyAccessibilityServiceConnection.mDisplayTypes = 2;
        proxyAccessibilityServiceConnection.mFocusStrokeWidth =
                proxyAccessibilityServiceConnection
                        .mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.accessibility_focus_highlight_stroke_width);
        proxyAccessibilityServiceConnection.mFocusColor =
                proxyAccessibilityServiceConnection
                        .mContext
                        .getResources()
                        .getColor(
                                R.color
                                        .accessibility_magnification_thumbnail_container_background_color);
        proxyAccessibilityServiceConnection.mDeviceId = deviceIdForDisplayId;
        synchronized (this.mLock) {
            try {
                this.mProxyA11yServiceConnections.put(i, proxyAccessibilityServiceConnection);
                Flags.proxyUseAppsOnVirtualDeviceListener();
                if (this.mAppsOnVirtualDeviceListener == null) {
                    this.mAppsOnVirtualDeviceListener =
                            new ProxyManager$$ExternalSyntheticLambda4(this);
                    VirtualDeviceManagerInternal localVdm = getLocalVdm();
                    if (localVdm != null) {
                        ProxyManager$$ExternalSyntheticLambda4
                                proxyManager$$ExternalSyntheticLambda4 =
                                        this.mAppsOnVirtualDeviceListener;
                        VirtualDeviceManagerService.LocalService localService =
                                (VirtualDeviceManagerService.LocalService) localVdm;
                        synchronized (VirtualDeviceManagerService.this.mVirtualDeviceManagerLock) {
                            localService.mAppsOnVirtualDeviceListeners.add(
                                    proxyManager$$ExternalSyntheticLambda4);
                        }
                    }
                }
                if (this.mProxyA11yServiceConnections.size() == 1
                        && (virtualDeviceManager =
                                        (VirtualDeviceManager)
                                                this.mContext.getSystemService(
                                                        VirtualDeviceManager.class))
                                != null
                        && android.companion.virtual.flags.Flags.vdmPublicApis()) {
                    if (this.mVirtualDeviceListener == null) {
                        this.mVirtualDeviceListener =
                                new VirtualDeviceManager
                                        .VirtualDeviceListener() { // from class:
                                                                   // com.android.server.accessibility.ProxyManager.2
                                    public final void onVirtualDeviceClosed(int i3) {
                                        ProxyManager.this.clearConnections(i3);
                                    }
                                };
                    }
                    virtualDeviceManager.registerVirtualDeviceListener(
                            this.mContext.getMainExecutor(), this.mVirtualDeviceListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        iAccessibilityServiceClient
                .asBinder()
                .linkToDeath(
                        new IBinder
                                .DeathRecipient() { // from class:
                                                    // com.android.server.accessibility.ProxyManager.1
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                iAccessibilityServiceClient.asBinder().unlinkToDeath(this, 0);
                                ProxyManager.this.clearConnectionAndUpdateState(i);
                            }
                        },
                        0);
        this.mMainHandler.post(new ProxyManager$$ExternalSyntheticLambda5(this, i, 0));
        proxyAccessibilityServiceConnection.mServiceInterface = iAccessibilityServiceClient;
        proxyAccessibilityServiceConnection.mService = iAccessibilityServiceClient.asBinder();
        proxyAccessibilityServiceConnection.mServiceInterface.init(
                proxyAccessibilityServiceConnection,
                proxyAccessibilityServiceConnection.mId,
                (IBinder) proxyAccessibilityServiceConnection.mOverlayWindowTokens.get(i));
    }

    public final void scheduleNotifyProxyClientsOfServicesStateChangeLocked(int i) {
        if (DEBUG) {
            ProxyManager$$ExternalSyntheticOutline0.m(
                    i, "Notify services state change at device id ", "ProxyManager");
        }
        this.mMainHandler.post(new ProxyManager$$ExternalSyntheticLambda5(this, i, 1));
    }

    public final void scheduleUpdateProxyClientsIfNeededLocked(final int i, boolean z) {
        final int stateLocked = getStateLocked(i);
        if (DEBUG) {
            Slog.v("ProxyManager", "State for device id " + i + " is " + stateLocked);
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Last state for device id ", " is ");
            m.append(this.mLastStates.get(i, 0));
            Slog.v("ProxyManager", m.toString());
            Slog.v("ProxyManager", "force update: " + z);
        }
        if (this.mLastStates.get(i, 0) == stateLocked) {
            Flags.proxyUseAppsOnVirtualDeviceListener();
            if (!z) {
                return;
            }
        }
        this.mLastStates.put(i, stateLocked);
        this.mMainHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProxyManager proxyManager = ProxyManager.this;
                        final int i2 = i;
                        final int i3 = stateLocked;
                        synchronized (proxyManager.mLock) {
                            proxyManager.broadcastToClientsLocked(
                                    FunctionalUtils.ignoreRemoteException(
                                            new FunctionalUtils
                                                    .RemoteExceptionIgnoringConsumer() { // from
                                                                                         // class:
                                                                                         // com.android.server.accessibility.ProxyManager$$ExternalSyntheticLambda10
                                                public final void acceptOrThrow(Object obj) {
                                                    int i4 = i2;
                                                    int i5 = i3;
                                                    AccessibilityManagerService.Client client =
                                                            (AccessibilityManagerService.Client)
                                                                    obj;
                                                    if (client.mDeviceId == i4) {
                                                        client.mCallback.setState(i5);
                                                    }
                                                }
                                            }));
                        }
                    }
                });
    }

    public void setLocalVirtualDeviceManager(
            VirtualDeviceManagerInternal virtualDeviceManagerInternal) {
        this.mLocalVdm = virtualDeviceManagerInternal;
    }

    public final void updateDeviceIdsIfNeededLocked(int i, RemoteCallbackList remoteCallbackList) {
        VirtualDeviceManagerInternal localVdm = getLocalVdm();
        if (localVdm == null) {
            return;
        }
        for (int i2 = 0; i2 < remoteCallbackList.getRegisteredCallbackCount(); i2++) {
            AccessibilityManagerService.Client client =
                    (AccessibilityManagerService.Client)
                            remoteCallbackList.getRegisteredCallbackCookie(i2);
            Flags.proxyUseAppsOnVirtualDeviceListener();
            if (i != 0 && i != -1) {
                boolean contains =
                        localVdm.getDeviceIdsForUid(client.mUid).contains(Integer.valueOf(i));
                int i3 = client.mDeviceId;
                boolean z = DEBUG;
                String[] strArr = client.mPackageNames;
                if (i3 != i && contains) {
                    if (z) {
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        i, "Packages moved to device id ", " are ");
                        m.append(Arrays.toString(strArr));
                        Slog.v("ProxyManager", m.toString());
                    }
                    client.mDeviceId = i;
                } else if (i3 == i && !contains) {
                    client.mDeviceId = 0;
                    if (z) {
                        StringBuilder m2 =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        i,
                                        "Packages moved to the default device from device id ",
                                        " are ");
                        m2.append(Arrays.toString(strArr));
                        Slog.v("ProxyManager", m2.toString());
                    }
                }
            }
        }
    }
}
