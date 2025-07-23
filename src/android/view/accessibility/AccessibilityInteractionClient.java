package android.view.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.IAccessibilityServiceConnection;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseLongArray;
import android.view.SurfaceControl;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityCache;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.window.ScreenCapture;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;

/* loaded from: classes4.dex */
public final class AccessibilityInteractionClient extends IAccessibilityInteractionConnectionCallback.Stub {
    public static final String CALL_STACK = "call_stack";
    private static final boolean CHECK_INTEGRITY = true;
    private static final boolean DEBUG = false;
    public static final String IGNORE_CALL_STACK = "ignore_call_stack";
    private static final String LOG_TAG = "AccessibilityInteractionClient";
    public static final int NO_ID = -1;
    private static final long TIMEOUT_INTERACTION_MILLIS = 2000;
    private final AccessibilityManager mAccessibilityManager;
    private final SparseArray<Pair<Executor, IntConsumer>> mAttachAccessibilityOverlayCallbacks;
    private List<StackTraceElement> mCallStackOfCallback;
    private volatile int mCallingUid;
    private int mConnectionIdWaitingForPrefetchResult;
    private AccessibilityNodeInfo mFindAccessibilityNodeInfoResult;
    private List<AccessibilityNodeInfo> mFindAccessibilityNodeInfosResult;
    private final Object mInstanceLock;
    private volatile int mInteractionId;
    private final AtomicInteger mInteractionIdCounter;
    private int mInteractionIdWaitingForPrefetchResult;
    private Handler mMainHandler;
    private String[] mPackageNamesForNextPrefetchResult;
    private boolean mPerformAccessibilityActionResult;
    private Message mSameThreadMessage;
    private final SparseArray<Pair<Executor, AccessibilityService.TakeScreenshotCallback>> mTakeScreenshotOfWindowCallbacks;
    private static final long DISABLE_PREFETCHING_FOR_SCROLLING_MILLIS = (long) (ViewConfiguration.getSendRecurringAccessibilityEventsInterval() * 1.5d);
    private static final Object sStaticLock = new Object();
    private static final LongSparseArray<AccessibilityInteractionClient> sClients = new LongSparseArray<>();
    private static final SparseArray<IAccessibilityServiceConnection> sConnectionCache = new SparseArray<>();
    private static int sDirectConnectionIdCounter = 1073741824;
    private static int sDirectConnectionCount = 0;
    private static final SparseLongArray sScrollingWindows = new SparseLongArray();
    private static SparseArray<AccessibilityCache> sCaches = new SparseArray<>();

    public static AccessibilityInteractionClient getInstance() {
        return getInstanceForThread(Thread.currentThread().getId());
    }

    public static AccessibilityInteractionClient getInstanceForThread(long j) {
        AccessibilityInteractionClient accessibilityInteractionClient;
        synchronized (sStaticLock) {
            LongSparseArray<AccessibilityInteractionClient> longSparseArray = sClients;
            accessibilityInteractionClient = longSparseArray.get(j);
            if (accessibilityInteractionClient == null) {
                accessibilityInteractionClient = new AccessibilityInteractionClient();
                longSparseArray.put(j, accessibilityInteractionClient);
            }
        }
        return accessibilityInteractionClient;
    }

    public static AccessibilityInteractionClient getInstance(Context context) {
        long id = Thread.currentThread().getId();
        if (context != null) {
            return getInstanceForThread(id, context);
        }
        return getInstanceForThread(id);
    }

    public static AccessibilityInteractionClient getInstanceForThread(long j, Context context) {
        AccessibilityInteractionClient accessibilityInteractionClient;
        synchronized (sStaticLock) {
            LongSparseArray<AccessibilityInteractionClient> longSparseArray = sClients;
            accessibilityInteractionClient = longSparseArray.get(j);
            if (accessibilityInteractionClient == null) {
                accessibilityInteractionClient = new AccessibilityInteractionClient(context);
                longSparseArray.put(j, accessibilityInteractionClient);
            }
        }
        return accessibilityInteractionClient;
    }

    public static IAccessibilityServiceConnection getConnection(int i) {
        IAccessibilityServiceConnection iAccessibilityServiceConnection;
        SparseArray<IAccessibilityServiceConnection> sparseArray = sConnectionCache;
        synchronized (sparseArray) {
            iAccessibilityServiceConnection = sparseArray.get(i);
        }
        return iAccessibilityServiceConnection;
    }

    public static void addConnection(int i, IAccessibilityServiceConnection iAccessibilityServiceConnection, boolean z) {
        if (i == -1) {
            return;
        }
        SparseArray<IAccessibilityServiceConnection> sparseArray = sConnectionCache;
        synchronized (sparseArray) {
            if (getConnection(i) instanceof DirectAccessibilityConnection) {
                throw new IllegalArgumentException("Cannot add service connection with id " + i + " which conflicts with existing direct connection.");
            }
            sparseArray.put(i, iAccessibilityServiceConnection);
            if (z) {
                sCaches.put(i, new AccessibilityCache(new AccessibilityCache.AccessibilityNodeRefresher()));
            }
        }
    }

    public static int addDirectConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection, AccessibilityManager accessibilityManager) {
        int i;
        SparseArray<IAccessibilityServiceConnection> sparseArray = sConnectionCache;
        synchronized (sparseArray) {
            i = sDirectConnectionIdCounter;
            sDirectConnectionIdCounter = i + 1;
            if (getConnection(i) != null) {
                throw new IllegalArgumentException("Cannot add direct connection with existing id " + i);
            }
            sparseArray.put(i, new DirectAccessibilityConnection(iAccessibilityInteractionConnection, accessibilityManager));
            sDirectConnectionCount++;
        }
        return i;
    }

    public static boolean hasAnyDirectConnection() {
        return sDirectConnectionCount > 0;
    }

    public static AccessibilityCache getCache(int i) {
        AccessibilityCache accessibilityCache;
        synchronized (sConnectionCache) {
            accessibilityCache = sCaches.get(i);
        }
        return accessibilityCache;
    }

    public static void removeConnection(int i) {
        SparseArray<IAccessibilityServiceConnection> sparseArray = sConnectionCache;
        synchronized (sparseArray) {
            if (getConnection(i) instanceof DirectAccessibilityConnection) {
                sDirectConnectionCount--;
            }
            sparseArray.remove(i);
            sCaches.remove(i);
        }
    }

    public static void setCache(int i, AccessibilityCache accessibilityCache) {
        synchronized (sConnectionCache) {
            sCaches.put(i, accessibilityCache);
        }
    }

    private AccessibilityInteractionClient() {
        this.mInteractionIdCounter = new AtomicInteger();
        this.mInstanceLock = new Object();
        this.mInteractionId = -1;
        this.mCallingUid = -1;
        this.mTakeScreenshotOfWindowCallbacks = new SparseArray<>();
        this.mAttachAccessibilityOverlayCallbacks = new SparseArray<>();
        this.mInteractionIdWaitingForPrefetchResult = -1;
        this.mAccessibilityManager = null;
        try {
            this.mMainHandler = new Handler(Looper.getMainLooper());
        } catch (NullPointerException unused) {
            Log.w("AccessibilityInteractionClient", "Failed to initialize AccessibilityInteractionClient. But this may be initialized again later.");
        }
    }

    private AccessibilityInteractionClient(Context context) {
        this.mInteractionIdCounter = new AtomicInteger();
        this.mInstanceLock = new Object();
        this.mInteractionId = -1;
        this.mCallingUid = -1;
        this.mTakeScreenshotOfWindowCallbacks = new SparseArray<>();
        this.mAttachAccessibilityOverlayCallbacks = new SparseArray<>();
        this.mInteractionIdWaitingForPrefetchResult = -1;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mMainHandler = new Handler(Looper.getMainLooper());
    }

    public void setSameThreadMessage(Message message) {
        synchronized (this.mInstanceLock) {
            this.mSameThreadMessage = message;
            this.mInstanceLock.notifyAll();
        }
    }

    public AccessibilityNodeInfo getRootInActiveWindow(int i, int i2) {
        return findAccessibilityNodeInfoByAccessibilityId(i, Integer.MAX_VALUE, AccessibilityNodeInfo.ROOT_NODE_ID, false, i2, (Bundle) null);
    }

    public AccessibilityWindowInfo getWindow(int i, int i2) {
        return getWindow(i, i2, false);
    }

    public AccessibilityWindowInfo getWindow(int i, int i2, boolean z) {
        AccessibilityWindowInfo window;
        try {
            IAccessibilityServiceConnection connection = getConnection(i);
            if (connection == null) {
                return null;
            }
            AccessibilityCache cache = getCache(i);
            if (cache != null && !z && (window = cache.getWindow(i2)) != null) {
                if (shouldTraceClient()) {
                    logTraceClient(connection, "getWindow cache", "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";bypassCache=false");
                }
                return window;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AccessibilityWindowInfo window2 = connection.getWindow(i2);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (shouldTraceClient()) {
                    logTraceClient(connection, "getWindow", "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";bypassCache=" + z);
                }
                if (window2 == null) {
                    return null;
                }
                if (!z && cache != null) {
                    cache.addWindow(window2);
                }
                return window2;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (RemoteException e) {
            Log.e("AccessibilityInteractionClient", "Error while calling remote getWindow", e);
            return null;
        }
    }

    public List<AccessibilityWindowInfo> getWindows(int i) {
        return getWindowsOnDisplay(i, 0);
    }

    public List<AccessibilityWindowInfo> getWindowsOnDisplay(int i, int i2) {
        return getWindowsOnAllDisplays(i).get(i2, Collections.EMPTY_LIST);
    }

    public SparseArray<List<AccessibilityWindowInfo>> getWindowsOnAllDisplays(int i) {
        SparseArray<List<AccessibilityWindowInfo>> windowsOnAllDisplays;
        try {
            IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                AccessibilityCache cache = getCache(i);
                if (cache != null && (windowsOnAllDisplays = cache.getWindowsOnAllDisplays()) != null) {
                    if (shouldTraceClient()) {
                        logTraceClient(connection, "getWindows cache", "connectionId=" + i);
                    }
                    return windowsOnAllDisplays;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    AccessibilityWindowInfo.WindowListSparseArray windows = connection.getWindows();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (shouldTraceClient()) {
                        logTraceClient(connection, "getWindows", "connectionId=" + i);
                    }
                    if (windows != null) {
                        if (cache != null) {
                            cache.setWindowsOnAllDisplays(windows, uptimeMillis);
                        }
                        return windows;
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        } catch (RemoteException e) {
            Log.e("AccessibilityInteractionClient", "Error while calling remote getWindowsOnAllDisplays", e);
        }
        return new SparseArray<>();
    }

    public List<AccessibilityWindowInfo> getWindowsOnMainDisplays(int i) {
        try {
            IAccessibilityServiceConnection connection = getConnection(i);
            if (connection == null) {
                return null;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                List<AccessibilityWindowInfo> windowsMainDisplay = connection.getWindowsMainDisplay(0);
                if (windowsMainDisplay != null) {
                    return windowsMainDisplay;
                }
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (RemoteException e) {
            Log.e("AccessibilityInteractionClient", "Error while calling remote getWindowsOnMainDisplays", e);
            return null;
        }
    }

    public AccessibilityNodeInfo findAccessibilityNodeInfoByAccessibilityId(int i, IBinder iBinder, long j, boolean z, int i2, Bundle bundle) {
        int i3;
        if (iBinder == null) {
            return null;
        }
        try {
            IAccessibilityServiceConnection connection = getConnection(i);
            i3 = connection != null ? connection.getWindowIdForLeashToken(iBinder) : -1;
        } catch (RemoteException e) {
            Log.e("AccessibilityInteractionClient", "Error while calling remote getWindowIdForLeashToken", e);
            i3 = -1;
        }
        if (i3 == -1) {
            return null;
        }
        return findAccessibilityNodeInfoByAccessibilityId(i, i3, j, z, i2, bundle);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x006c, code lost:
    
        if (r15.isEnabled() == false) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0084 A[Catch: RemoteException -> 0x0175, TRY_LEAVE, TryCatch #1 {RemoteException -> 0x0175, blocks: (B:3:0x000e, B:7:0x0022, B:9:0x0028, B:11:0x002e, B:13:0x0034, B:16:0x0068, B:18:0x0070, B:20:0x0074, B:22:0x007a, B:23:0x007c, B:25:0x0084, B:28:0x0098, B:29:0x00d9, B:32:0x00f1, B:36:0x00fe, B:38:0x0108, B:39:0x0123, B:41:0x0128, B:43:0x012e, B:47:0x0136, B:49:0x0140, B:50:0x015b, B:53:0x0161, B:54:0x0164, B:59:0x0169, B:60:0x016c, B:62:0x016d, B:63:0x0174, B:64:0x006e, B:31:0x00dd), top: B:2:0x000e, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x016d A[Catch: RemoteException -> 0x0175, TryCatch #1 {RemoteException -> 0x0175, blocks: (B:3:0x000e, B:7:0x0022, B:9:0x0028, B:11:0x002e, B:13:0x0034, B:16:0x0068, B:18:0x0070, B:20:0x0074, B:22:0x007a, B:23:0x007c, B:25:0x0084, B:28:0x0098, B:29:0x00d9, B:32:0x00f1, B:36:0x00fe, B:38:0x0108, B:39:0x0123, B:41:0x0128, B:43:0x012e, B:47:0x0136, B:49:0x0140, B:50:0x015b, B:53:0x0161, B:54:0x0164, B:59:0x0169, B:60:0x016c, B:62:0x016d, B:63:0x0174, B:64:0x006e, B:31:0x00dd), top: B:2:0x000e, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.accessibility.AccessibilityNodeInfo findAccessibilityNodeInfoByAccessibilityId(int r19, int r20, long r21, boolean r23, int r24, android.os.Bundle r25) {
        /*
            Method dump skipped, instructions count: 383
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.AccessibilityInteractionClient.findAccessibilityNodeInfoByAccessibilityId(int, int, long, boolean, int, android.os.Bundle):android.view.accessibility.AccessibilityNodeInfo");
    }

    private void setInteractionWaitingForPrefetchResult(int i, int i2, String[] strArr) {
        synchronized (this.mInstanceLock) {
            this.mInteractionIdWaitingForPrefetchResult = i;
            this.mConnectionIdWaitingForPrefetchResult = i2;
            this.mPackageNamesForNextPrefetchResult = strArr;
        }
    }

    private static String idToString(int i, long j) {
        return i + "/" + AccessibilityNodeInfo.idToString(j);
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(int i, int i2, long j, String str) {
        int i3;
        long j2;
        String str2;
        try {
            IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    if (shouldTraceClient()) {
                        StringBuilder sb = new StringBuilder("InteractionId=");
                        sb.append(andIncrement);
                        sb.append(";connectionId=");
                        sb.append(i);
                        sb.append(";accessibilityWindowId=");
                        i3 = i2;
                        sb.append(i3);
                        sb.append(";accessibilityNodeId=");
                        j2 = j;
                        sb.append(j2);
                        sb.append(";viewId=");
                        str2 = str;
                        sb.append(str2);
                        logTraceClient(connection, "findAccessibilityNodeInfosByViewId", sb.toString());
                    } else {
                        i3 = i2;
                        j2 = j;
                        str2 = str;
                    }
                    String[] findAccessibilityNodeInfosByViewId = connection.findAccessibilityNodeInfosByViewId(i3, j2, str2, andIncrement, this, Thread.currentThread().getId());
                    if (findAccessibilityNodeInfosByViewId != null) {
                        List<AccessibilityNodeInfo> findAccessibilityNodeInfosResultAndClear = getFindAccessibilityNodeInfosResultAndClear(andIncrement);
                        if (shouldTraceCallback()) {
                            logTraceCallback(connection, "findAccessibilityNodeInfosByViewId", "InteractionId=" + andIncrement + ";connectionId=" + i + ":Result: " + findAccessibilityNodeInfosResultAndClear);
                        }
                        if (findAccessibilityNodeInfosResultAndClear != null) {
                            finalizeAndCacheAccessibilityNodeInfos(findAccessibilityNodeInfosResultAndClear, i, false, findAccessibilityNodeInfosByViewId);
                            return findAccessibilityNodeInfosResultAndClear;
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } catch (RemoteException e) {
            Log.w("AccessibilityInteractionClient", "Error while calling remote findAccessibilityNodeInfoByViewIdInActiveWindow", e);
        }
        return Collections.EMPTY_LIST;
    }

    public void takeScreenshotOfWindow(int i, int i2, Executor executor, final AccessibilityService.TakeScreenshotCallback takeScreenshotCallback) {
        IAccessibilityServiceConnection connection;
        synchronized (this.mInstanceLock) {
            try {
                connection = getConnection(i);
            } catch (RemoteException unused) {
                executor.execute(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityService.TakeScreenshotCallback.this.onFailure(1);
                    }
                });
            }
            if (connection == null) {
                executor.execute(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityService.TakeScreenshotCallback.this.onFailure(1);
                    }
                });
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                final int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                this.mTakeScreenshotOfWindowCallbacks.put(andIncrement, Pair.create(executor, takeScreenshotCallback));
                connection.takeScreenshotOfWindow(i2, andIncrement, new ScreenCapture.ScreenCaptureListener((ObjIntConsumer<ScreenCapture.ScreenshotHardwareBuffer>) new ObjIntConsumer() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda2
                    @Override // java.util.function.ObjIntConsumer
                    public final void accept(Object obj, int i3) {
                        AccessibilityInteractionClient.this.lambda$takeScreenshotOfWindow$1(andIncrement, (ScreenCapture.ScreenshotHardwareBuffer) obj, i3);
                    }
                }), this);
                this.mMainHandler.postDelayed(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityInteractionClient.this.lambda$takeScreenshotOfWindow$2(andIncrement);
                    }
                }, TIMEOUT_INTERACTION_MILLIS);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshotOfWindow$1(int i, ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, int i2) {
        if (i2 != 0) {
            sendTakeScreenshotOfWindowError(1, i);
        } else {
            sendWindowScreenshotSuccess(screenshotHardwareBuffer, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshotOfWindow$2(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mTakeScreenshotOfWindowCallbacks.contains(i)) {
                sendTakeScreenshotOfWindowError(1, i);
            }
        }
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(int i, int i2, long j, String str) {
        int i3;
        long j2;
        String str2;
        try {
            IAccessibilityServiceConnection connection = getConnection(i);
            if (connection != null) {
                int andIncrement = this.mInteractionIdCounter.getAndIncrement();
                if (shouldTraceClient()) {
                    StringBuilder sb = new StringBuilder("InteractionId:");
                    sb.append(andIncrement);
                    sb.append("connectionId=");
                    sb.append(i);
                    sb.append(";accessibilityWindowId=");
                    i3 = i2;
                    sb.append(i3);
                    sb.append(";accessibilityNodeId=");
                    j2 = j;
                    sb.append(j2);
                    sb.append(";text=");
                    str2 = str;
                    sb.append(str2);
                    logTraceClient(connection, "findAccessibilityNodeInfosByText", sb.toString());
                } else {
                    i3 = i2;
                    j2 = j;
                    str2 = str;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String[] findAccessibilityNodeInfosByText = connection.findAccessibilityNodeInfosByText(i3, j2, str2, andIncrement, this, Thread.currentThread().getId());
                    if (findAccessibilityNodeInfosByText != null) {
                        List<AccessibilityNodeInfo> findAccessibilityNodeInfosResultAndClear = getFindAccessibilityNodeInfosResultAndClear(andIncrement);
                        if (shouldTraceCallback()) {
                            logTraceCallback(connection, "findAccessibilityNodeInfosByText", "InteractionId=" + andIncrement + ";connectionId=" + i + ";Result: " + findAccessibilityNodeInfosResultAndClear);
                        }
                        if (findAccessibilityNodeInfosResultAndClear != null) {
                            finalizeAndCacheAccessibilityNodeInfos(findAccessibilityNodeInfosResultAndClear, i, false, findAccessibilityNodeInfosByText);
                            return findAccessibilityNodeInfosResultAndClear;
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        } catch (RemoteException e) {
            Log.w("AccessibilityInteractionClient", "Error while calling remote findAccessibilityNodeInfosByViewText", e);
        }
        return Collections.EMPTY_LIST;
    }

    public AccessibilityNodeInfo findFocus(int i, int i2, long j, int i3) {
        AccessibilityNodeInfo focus;
        try {
            IAccessibilityServiceConnection connection = getConnection(i);
            if (connection == null) {
                return null;
            }
            AccessibilityCache cache = getCache(i);
            if (cache != null && (focus = cache.getFocus(i3, j, i2)) != null) {
                return focus;
            }
            int andIncrement = this.mInteractionIdCounter.getAndIncrement();
            if (shouldTraceClient()) {
                logTraceClient(connection, "findFocus", "InteractionId:" + andIncrement + "connectionId=" + i + ";accessibilityWindowId=" + i2 + ";accessibilityNodeId=" + j + ";focusType=" + i3);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                String[] findFocus = connection.findFocus(i2, j, i3, andIncrement, this, Thread.currentThread().getId());
                if (findFocus == null) {
                    return null;
                }
                AccessibilityNodeInfo findAccessibilityNodeInfoResultAndClear = getFindAccessibilityNodeInfoResultAndClear(andIncrement);
                if (shouldTraceCallback()) {
                    logTraceCallback(connection, "findFocus", "InteractionId=" + andIncrement + ";connectionId=" + i + ";Result:" + findAccessibilityNodeInfoResultAndClear);
                }
                finalizeAndCacheAccessibilityNodeInfo(findAccessibilityNodeInfoResultAndClear, i, false, findFocus);
                return findAccessibilityNodeInfoResultAndClear;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (RemoteException e) {
            Log.w("AccessibilityInteractionClient", "Error while calling remote findFocus", e);
            return null;
        }
    }

    public AccessibilityNodeInfo focusSearch(int i, int i2, long j, int i3) {
        int i4;
        long j2;
        int i5;
        try {
            IAccessibilityServiceConnection connection = getConnection(i);
            if (connection == null) {
                return null;
            }
            int andIncrement = this.mInteractionIdCounter.getAndIncrement();
            if (shouldTraceClient()) {
                StringBuilder sb = new StringBuilder("InteractionId:");
                sb.append(andIncrement);
                sb.append("connectionId=");
                sb.append(i);
                sb.append(";accessibilityWindowId=");
                i4 = i2;
                sb.append(i4);
                sb.append(";accessibilityNodeId=");
                j2 = j;
                sb.append(j2);
                sb.append(";direction=");
                i5 = i3;
                sb.append(i5);
                logTraceClient(connection, "focusSearch", sb.toString());
            } else {
                i4 = i2;
                j2 = j;
                i5 = i3;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                String[] focusSearch = connection.focusSearch(i4, j2, i5, andIncrement, this, Thread.currentThread().getId());
                if (focusSearch == null) {
                    return null;
                }
                AccessibilityNodeInfo findAccessibilityNodeInfoResultAndClear = getFindAccessibilityNodeInfoResultAndClear(andIncrement);
                finalizeAndCacheAccessibilityNodeInfo(findAccessibilityNodeInfoResultAndClear, i, false, focusSearch);
                if (shouldTraceCallback()) {
                    logTraceCallback(connection, "focusSearch", "InteractionId=" + andIncrement + ";connectionId=" + i + ";Result:" + findAccessibilityNodeInfoResultAndClear);
                }
                return findAccessibilityNodeInfoResultAndClear;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (RemoteException e) {
            Log.w("AccessibilityInteractionClient", "Error while calling remote accessibilityFocusSearch", e);
            return null;
        }
    }

    public boolean performAccessibilityAction(int i, int i2, long j, int i3, Bundle bundle) {
        int i4;
        long j2;
        int i5;
        Bundle bundle2;
        try {
            IAccessibilityServiceConnection connection = getConnection(i);
            if (connection == null) {
                return false;
            }
            int andIncrement = this.mInteractionIdCounter.getAndIncrement();
            if (shouldTraceClient()) {
                StringBuilder sb = new StringBuilder("InteractionId:");
                sb.append(andIncrement);
                sb.append("connectionId=");
                sb.append(i);
                sb.append(";accessibilityWindowId=");
                i4 = i2;
                sb.append(i4);
                sb.append(";accessibilityNodeId=");
                j2 = j;
                sb.append(j2);
                sb.append(";action=");
                i5 = i3;
                sb.append(i5);
                sb.append(";arguments=");
                bundle2 = bundle;
                sb.append(bundle2);
                logTraceClient(connection, "performAccessibilityAction", sb.toString());
            } else {
                i4 = i2;
                j2 = j;
                i5 = i3;
                bundle2 = bundle;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!connection.performAccessibilityAction(i4, j2, i5, bundle2, andIncrement, this, Thread.currentThread().getId())) {
                    return false;
                }
                boolean performAccessibilityActionResultAndClear = getPerformAccessibilityActionResultAndClear(andIncrement);
                if (shouldTraceCallback()) {
                    logTraceCallback(connection, "performAccessibilityAction", "InteractionId=" + andIncrement + ";connectionId=" + i + ";Result: " + performAccessibilityActionResultAndClear);
                }
                return performAccessibilityActionResultAndClear;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (RemoteException e) {
            Log.w("AccessibilityInteractionClient", "Error while calling remote performAccessibilityAction", e);
            return false;
        }
    }

    public void clearCache(int i) {
        AccessibilityCache cache = getCache(i);
        if (cache == null) {
            return;
        }
        cache.clear();
    }

    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent, int i) {
        int eventType = accessibilityEvent.getEventType();
        if (eventType == 4096) {
            updateScrollingWindow(accessibilityEvent.getWindowId(), SystemClock.uptimeMillis());
        } else if (eventType == 4194304 && accessibilityEvent.getWindowChanges() == 2) {
            deleteScrollingWindow(accessibilityEvent.getWindowId());
        }
        AccessibilityCache cache = getCache(i);
        if (cache == null) {
            return;
        }
        cache.onAccessibilityEvent(accessibilityEvent);
    }

    private AccessibilityNodeInfo getFindAccessibilityNodeInfoResultAndClear(int i) {
        AccessibilityNodeInfo accessibilityNodeInfo;
        synchronized (this.mInstanceLock) {
            accessibilityNodeInfo = waitForResultTimedLocked(i) ? this.mFindAccessibilityNodeInfoResult : null;
            clearResultLocked();
        }
        return accessibilityNodeInfo;
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void setFindAccessibilityNodeInfoResult(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        synchronized (this.mInstanceLock) {
            if (i > this.mInteractionId) {
                this.mFindAccessibilityNodeInfoResult = accessibilityNodeInfo;
                this.mInteractionId = i;
                this.mCallingUid = Binder.getCallingUid();
                this.mCallStackOfCallback = new ArrayList(Arrays.asList(Thread.currentThread().getStackTrace()));
            }
            this.mInstanceLock.notifyAll();
        }
    }

    private List<AccessibilityNodeInfo> getFindAccessibilityNodeInfosResultAndClear(int i) {
        List<AccessibilityNodeInfo> list;
        synchronized (this.mInstanceLock) {
            if (waitForResultTimedLocked(i)) {
                list = this.mFindAccessibilityNodeInfosResult;
            } else {
                list = Collections.EMPTY_LIST;
            }
            clearResultLocked();
            if (Build.IS_DEBUGGABLE) {
                checkFindAccessibilityNodeInfoResultIntegrity(list);
            }
        }
        return list;
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void setFindAccessibilityNodeInfosResult(List<AccessibilityNodeInfo> list, int i) {
        synchronized (this.mInstanceLock) {
            if (i > this.mInteractionId) {
                if (list != null) {
                    if (Binder.getCallingPid() == Process.myPid()) {
                        this.mFindAccessibilityNodeInfosResult = new ArrayList(list);
                    } else {
                        this.mFindAccessibilityNodeInfosResult = list;
                    }
                } else {
                    this.mFindAccessibilityNodeInfosResult = Collections.EMPTY_LIST;
                }
                this.mInteractionId = i;
                this.mCallingUid = Binder.getCallingUid();
                this.mCallStackOfCallback = new ArrayList(Arrays.asList(Thread.currentThread().getStackTrace()));
            }
            this.mInstanceLock.notifyAll();
        }
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void setPrefetchAccessibilityNodeInfoResult(List<AccessibilityNodeInfo> list, int i) {
        int i2;
        String[] strArr;
        int i3;
        if (list.isEmpty()) {
            return;
        }
        synchronized (this.mInstanceLock) {
            i2 = this.mInteractionIdWaitingForPrefetchResult;
            strArr = null;
            if (i2 == i) {
                i3 = this.mConnectionIdWaitingForPrefetchResult;
                String[] strArr2 = this.mPackageNamesForNextPrefetchResult;
                if (strArr2 != null) {
                    strArr = new String[strArr2.length];
                    int i4 = 0;
                    while (true) {
                        String[] strArr3 = this.mPackageNamesForNextPrefetchResult;
                        if (i4 >= strArr3.length) {
                            break;
                        }
                        strArr[i4] = strArr3[i4];
                        i4++;
                    }
                }
            } else {
                i2 = -1;
                i3 = -1;
            }
        }
        if (i2 == i) {
            finalizeAndCacheAccessibilityNodeInfos(list, i3, false, strArr);
            if (shouldTraceCallback()) {
                logTrace(getConnection(i3), "setPrefetchAccessibilityNodeInfoResult", "InteractionId:" + i + ";connectionId=" + i3 + ";Result: " + list, Binder.getCallingUid(), Arrays.asList(Thread.currentThread().getStackTrace()), new HashSet<>(Collections.singletonList("getStackTrace")), 32L);
            }
        }
    }

    private boolean getPerformAccessibilityActionResultAndClear(int i) {
        boolean z;
        synchronized (this.mInstanceLock) {
            z = waitForResultTimedLocked(i) ? this.mPerformAccessibilityActionResult : false;
            clearResultLocked();
        }
        return z;
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void setPerformAccessibilityActionResult(boolean z, int i) {
        synchronized (this.mInstanceLock) {
            if (i > this.mInteractionId) {
                this.mPerformAccessibilityActionResult = z;
                this.mInteractionId = i;
                this.mCallingUid = Binder.getCallingUid();
                this.mCallStackOfCallback = new ArrayList(Arrays.asList(Thread.currentThread().getStackTrace()));
            }
            this.mInstanceLock.notifyAll();
        }
    }

    private void sendWindowScreenshotSuccess(ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, int i) {
        if (screenshotHardwareBuffer == null) {
            sendTakeScreenshotOfWindowError(1, i);
            return;
        }
        synchronized (this.mInstanceLock) {
            if (this.mTakeScreenshotOfWindowCallbacks.contains(i)) {
                final AccessibilityService.ScreenshotResult screenshotResult = new AccessibilityService.ScreenshotResult(screenshotHardwareBuffer.getHardwareBuffer(), screenshotHardwareBuffer.getColorSpace(), SystemClock.uptimeMillis());
                Pair<Executor, AccessibilityService.TakeScreenshotCallback> pair = this.mTakeScreenshotOfWindowCallbacks.get(i);
                Executor executor = pair.first;
                final AccessibilityService.TakeScreenshotCallback takeScreenshotCallback = pair.second;
                executor.execute(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityService.TakeScreenshotCallback.this.onSuccess(screenshotResult);
                    }
                });
                this.mTakeScreenshotOfWindowCallbacks.remove(i);
            }
        }
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void sendTakeScreenshotOfWindowError(final int i, int i2) {
        synchronized (this.mInstanceLock) {
            if (this.mTakeScreenshotOfWindowCallbacks.contains(i2)) {
                Pair<Executor, AccessibilityService.TakeScreenshotCallback> pair = this.mTakeScreenshotOfWindowCallbacks.get(i2);
                Executor executor = pair.first;
                final AccessibilityService.TakeScreenshotCallback takeScreenshotCallback = pair.second;
                executor.execute(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityService.TakeScreenshotCallback.this.onFailure(i);
                    }
                });
                this.mTakeScreenshotOfWindowCallbacks.remove(i2);
            }
        }
    }

    private void clearResultLocked() {
        this.mInteractionId = -1;
        this.mFindAccessibilityNodeInfoResult = null;
        this.mFindAccessibilityNodeInfosResult = null;
        this.mPerformAccessibilityActionResult = false;
    }

    private boolean waitForResultTimedLocked(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        while (true) {
            try {
                Message sameProcessMessageAndClear = getSameProcessMessageAndClear();
                if (sameProcessMessageAndClear != null) {
                    sameProcessMessageAndClear.getTarget().handleMessage(sameProcessMessageAndClear);
                }
            } catch (InterruptedException unused) {
            }
            if (this.mInteractionId == i) {
                return true;
            }
            if (this.mInteractionId > i) {
                return false;
            }
            long uptimeMillis2 = TIMEOUT_INTERACTION_MILLIS - (SystemClock.uptimeMillis() - uptimeMillis);
            if (uptimeMillis2 <= 0) {
                return false;
            }
            this.mInstanceLock.wait(uptimeMillis2);
        }
    }

    private void finalizeAndCacheAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo, int i, boolean z, String[] strArr) {
        AccessibilityCache cache;
        CharSequence packageName;
        if (accessibilityNodeInfo != null) {
            accessibilityNodeInfo.setConnectionId(i);
            if (!ArrayUtils.isEmpty(strArr) && ((packageName = accessibilityNodeInfo.getPackageName()) == null || !ArrayUtils.contains(strArr, packageName.toString()))) {
                accessibilityNodeInfo.setPackageName(strArr[0]);
            }
            accessibilityNodeInfo.setSealed(true);
            if (z || (cache = getCache(i)) == null) {
                return;
            }
            cache.add(accessibilityNodeInfo);
        }
    }

    private void finalizeAndCacheAccessibilityNodeInfos(List<AccessibilityNodeInfo> list, int i, boolean z, String[] strArr) {
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                finalizeAndCacheAccessibilityNodeInfo(list.get(i2), i, z, strArr);
            }
        }
    }

    private Message getSameProcessMessageAndClear() {
        Message message;
        synchronized (this.mInstanceLock) {
            message = this.mSameThreadMessage;
            this.mSameThreadMessage = null;
        }
        return message;
    }

    private void checkFindAccessibilityNodeInfoResultIntegrity(List<AccessibilityNodeInfo> list) {
        if (list.size() == 0) {
            return;
        }
        AccessibilityNodeInfo accessibilityNodeInfo = list.get(0);
        int size = list.size();
        for (int i = 1; i < size; i++) {
            int i2 = i;
            while (true) {
                if (i2 < size) {
                    AccessibilityNodeInfo accessibilityNodeInfo2 = list.get(i2);
                    if (accessibilityNodeInfo.getParentNodeId() == accessibilityNodeInfo2.getSourceNodeId()) {
                        accessibilityNodeInfo = accessibilityNodeInfo2;
                        break;
                    }
                    i2++;
                }
            }
        }
        if (accessibilityNodeInfo == null) {
            Log.e("AccessibilityInteractionClient", "No root.");
        }
        HashSet hashSet = new HashSet();
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(accessibilityNodeInfo);
        while (!arrayDeque.isEmpty()) {
            AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) arrayDeque.poll();
            if (!hashSet.add(accessibilityNodeInfo3)) {
                Log.e("AccessibilityInteractionClient", "Duplicate node.");
                return;
            }
            int childCount = accessibilityNodeInfo3.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                long childId = accessibilityNodeInfo3.getChildId(i3);
                for (int i4 = 0; i4 < size; i4++) {
                    AccessibilityNodeInfo accessibilityNodeInfo4 = list.get(i4);
                    if (accessibilityNodeInfo4.getSourceNodeId() == childId) {
                        arrayDeque.add(accessibilityNodeInfo4);
                    }
                }
            }
        }
        int size2 = list.size() - hashSet.size();
        if (size2 > 0) {
            Log.e("AccessibilityInteractionClient", size2 + " Disconnected nodes.");
        }
    }

    private void updateScrollingWindow(int i, long j) {
        SparseLongArray sparseLongArray = sScrollingWindows;
        synchronized (sparseLongArray) {
            sparseLongArray.put(i, j);
        }
    }

    private void deleteScrollingWindow(int i) {
        SparseLongArray sparseLongArray = sScrollingWindows;
        synchronized (sparseLongArray) {
            sparseLongArray.delete(i);
        }
    }

    private boolean isWindowScrolling(int i) {
        SparseLongArray sparseLongArray = sScrollingWindows;
        synchronized (sparseLongArray) {
            long j = sparseLongArray.get(i);
            if (j == 0) {
                return false;
            }
            if (SystemClock.uptimeMillis() <= j + DISABLE_PREFETCHING_FOR_SCROLLING_MILLIS) {
                return true;
            }
            sparseLongArray.delete(i);
            return false;
        }
    }

    private boolean shouldTraceClient() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        return accessibilityManager != null && accessibilityManager.isA11yInteractionClientTraceEnabled();
    }

    private boolean shouldTraceCallback() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        return accessibilityManager != null && accessibilityManager.isA11yInteractionConnectionCBTraceEnabled();
    }

    private void logTrace(IAccessibilityServiceConnection iAccessibilityServiceConnection, String str, String str2, int i, List<StackTraceElement> list, HashSet<String> hashSet, long j) {
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable(CALL_STACK, new ArrayList(list));
            if (hashSet != null) {
                bundle.putSerializable(IGNORE_CALL_STACK, hashSet);
            }
            iAccessibilityServiceConnection.logTrace(SystemClock.elapsedRealtimeNanos(), "AccessibilityInteractionClient." + str, j, str2, Process.myPid(), Thread.currentThread().getId(), i, bundle);
        } catch (RemoteException e) {
            Log.e("AccessibilityInteractionClient", "Failed to log trace. " + e);
        }
    }

    private void logTraceCallback(IAccessibilityServiceConnection iAccessibilityServiceConnection, String str, String str2) {
        logTrace(iAccessibilityServiceConnection, str + " callback", str2, this.mCallingUid, this.mCallStackOfCallback, new HashSet<>(Arrays.asList("getStackTrace")), 32L);
    }

    private void logTraceClient(IAccessibilityServiceConnection iAccessibilityServiceConnection, String str, String str2) {
        logTrace(iAccessibilityServiceConnection, str, str2, Binder.getCallingUid(), Arrays.asList(Thread.currentThread().getStackTrace()), new HashSet<>(Arrays.asList("getStackTrace", "logTraceClient")), 262144L);
    }

    public void attachAccessibilityOverlayToWindow(int i, int i2, SurfaceControl surfaceControl, Executor executor, final IntConsumer intConsumer) {
        IAccessibilityServiceConnection connection;
        synchronized (this.mInstanceLock) {
            try {
                connection = getConnection(i);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (connection == null) {
                executor.execute(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        intConsumer.accept(1);
                    }
                });
                return;
            }
            final int andIncrement = this.mInteractionIdCounter.getAndIncrement();
            this.mAttachAccessibilityOverlayCallbacks.put(andIncrement, Pair.create(executor, intConsumer));
            connection.attachAccessibilityOverlayToWindow(andIncrement, i2, surfaceControl, this);
            this.mMainHandler.postDelayed(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityInteractionClient.this.lambda$attachAccessibilityOverlayToWindow$7(andIncrement);
                }
            }, TIMEOUT_INTERACTION_MILLIS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attachAccessibilityOverlayToWindow$7(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mAttachAccessibilityOverlayCallbacks.contains(i)) {
                sendAttachOverlayResult(1, i);
            }
        }
    }

    public void attachAccessibilityOverlayToDisplay(int i, int i2, SurfaceControl surfaceControl, Executor executor, final IntConsumer intConsumer) {
        IAccessibilityServiceConnection connection;
        synchronized (this.mInstanceLock) {
            try {
                connection = getConnection(i);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            if (connection == null) {
                executor.execute(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        intConsumer.accept(1);
                    }
                });
                return;
            }
            final int andIncrement = this.mInteractionIdCounter.getAndIncrement();
            this.mAttachAccessibilityOverlayCallbacks.put(andIncrement, Pair.create(executor, intConsumer));
            connection.attachAccessibilityOverlayToDisplay(andIncrement, i2, surfaceControl, this);
            this.mMainHandler.postDelayed(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityInteractionClient.this.lambda$attachAccessibilityOverlayToDisplay$9(andIncrement);
                }
            }, TIMEOUT_INTERACTION_MILLIS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attachAccessibilityOverlayToDisplay$9(int i) {
        if (this.mAttachAccessibilityOverlayCallbacks.contains(i)) {
            sendAttachOverlayResult(1, i);
        }
    }

    @Override // android.view.accessibility.IAccessibilityInteractionConnectionCallback
    public void sendAttachOverlayResult(final int i, int i2) {
        if (Flags.a11yOverlayCallbacks()) {
            synchronized (this.mInstanceLock) {
                if (this.mAttachAccessibilityOverlayCallbacks.contains(i2)) {
                    Pair<Executor, IntConsumer> pair = this.mAttachAccessibilityOverlayCallbacks.get(i2);
                    if (pair == null) {
                        return;
                    }
                    Executor executor = pair.first;
                    final IntConsumer intConsumer = pair.second;
                    if (executor != null && intConsumer != null) {
                        executor.execute(new Runnable() { // from class: android.view.accessibility.AccessibilityInteractionClient$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                intConsumer.accept(i);
                            }
                        });
                        this.mAttachAccessibilityOverlayCallbacks.remove(i2);
                    }
                }
            }
        }
    }
}
