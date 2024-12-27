package com.android.server.accessibility;

import android.graphics.Region;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.WindowInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.IAccessibilityInteractionConnection;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.accessibility.AccessibilitySecurityPolicy;
import com.android.server.wm.WindowManagerInternal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AccessibilityWindowManager {
    public static int sNextWindowId;
    public final AccessibilityEventSender mAccessibilityEventSender;
    public final AccessibilitySecurityPolicy.AccessibilityUserManager mAccessibilityUserManager;
    public final Handler mHandler;
    public boolean mHasProxy;
    public int mLastNonProxyTopFocusedDisplayId;
    public final Object mLock;
    public RemoteAccessibilityConnection mPictureInPictureActionReplacingConnection;
    public final AccessibilitySecurityPolicy mSecurityPolicy;
    public int mTopFocusedDisplayId;
    public IBinder mTopFocusedWindowToken;
    public boolean mTouchInteractionInProgress;
    public final AccessibilityTraceManager mTraceManager;
    public final WindowManagerInternal mWindowManagerInternal;
    public final Region mTmpRegion = new Region();
    public final SparseArray mGlobalInteractionConnections = new SparseArray();
    public final SparseArray mGlobalWindowTokens = new SparseArray();
    public final SparseArray mInteractionConnections = new SparseArray();
    public final SparseArray mWindowTokens = new SparseArray();
    public int mActiveWindowId = -1;
    public int mTopFocusedWindowId = -1;
    public int mAccessibilityFocusedWindowId = -1;
    public long mAccessibilityFocusNodeId = 2147483647L;
    public int mAccessibilityFocusedDisplayId = -1;
    public final SparseArray mDisplayWindowsObservers = new SparseArray();
    public final ArrayMap mHostEmbeddedMap = new ArrayMap();
    public final SparseArray mWindowIdMap = new SparseArray();
    public final SparseArray mWindowAttributes = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AccessibilityEventSender {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayWindowsObserver implements WindowManagerInternal.WindowsForAccessibilityCallback {
        public final int mDisplayId;
        public boolean mHasWatchOutsideTouchWindow;
        public boolean mIsProxy;
        public List mWindows;
        public final SparseArray mA11yWindowInfoById = new SparseArray();
        public final SparseArray mWindowInfoById = new SparseArray();
        public final List mCachedWindowInfos = new ArrayList();
        public boolean mTrackingWindows = false;
        public int mProxyDisplayAccessibilityFocusedWindow = -1;

        public DisplayWindowsObserver(int i) {
            this.mDisplayId = i;
        }

        public final void cacheWindows(List list) {
            for (int size = ((ArrayList) this.mCachedWindowInfos).size() - 1; size >= 0; size--) {
                ((WindowInfo) ((ArrayList) this.mCachedWindowInfos).remove(size)).recycle();
            }
            ArrayList arrayList = (ArrayList) list;
            int size2 = arrayList.size();
            for (int i = 0; i < size2; i++) {
                ((ArrayList) this.mCachedWindowInfos).add(WindowInfo.obtain((WindowInfo) arrayList.get(i)));
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:42:0x00bb, code lost:
        
            if (r10 == 2030) goto L72;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List createWindowInfoListLocked(android.graphics.Point r14, java.util.List r15) {
            /*
                Method dump skipped, instructions count: 364
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver.createWindowInfoListLocked(android.graphics.Point, java.util.List):java.util.List");
        }

        public final List getWatchOutsideTouchWindowIdLocked(int i) {
            WindowInfo windowInfo = (WindowInfo) this.mWindowInfoById.get(i);
            if (windowInfo == null || !this.mHasWatchOutsideTouchWindow) {
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < this.mWindowInfoById.size(); i2++) {
                WindowInfo windowInfo2 = (WindowInfo) this.mWindowInfoById.valueAt(i2);
                if (windowInfo2 != null && windowInfo2.layer < windowInfo.layer && windowInfo2.hasFlagWatchOutsideTouch) {
                    arrayList.add(Integer.valueOf(this.mWindowInfoById.keyAt(i2)));
                }
            }
            return arrayList;
        }

        public final boolean setAccessibilityFocusedWindowLocked(int i) {
            List list = this.mWindows;
            if (list == null) {
                return false;
            }
            int size = list.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                AccessibilityWindowInfo accessibilityWindowInfo = (AccessibilityWindowInfo) this.mWindows.get(i2);
                if (accessibilityWindowInfo.getId() == i) {
                    z = true;
                    accessibilityWindowInfo.setAccessibilityFocused(true);
                } else {
                    accessibilityWindowInfo.setAccessibilityFocused(false);
                }
            }
            return z;
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x006d  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0081  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x0086  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0073  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean shouldUpdateWindowsLocked(java.util.List r10, boolean r11) {
            /*
                Method dump skipped, instructions count: 233
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver.shouldUpdateWindowsLocked(java.util.List, boolean):boolean");
        }

        public final void startTrackingWindowsLocked() {
            if (this.mTrackingWindows) {
                return;
            }
            this.mTrackingWindows = true;
            AccessibilityWindowManager accessibilityWindowManager = AccessibilityWindowManager.this;
            boolean traceWMEnabled = accessibilityWindowManager.traceWMEnabled();
            int i = this.mDisplayId;
            if (traceWMEnabled) {
                accessibilityWindowManager.logTraceWM("setWindowsForAccessibilityCallback", "displayId=" + i + ";callback=" + this);
            }
            accessibilityWindowManager.mWindowManagerInternal.setWindowsForAccessibilityCallback(i, this);
        }

        public final void stopTrackingWindowsLocked() {
            if (this.mTrackingWindows) {
                AccessibilityWindowManager accessibilityWindowManager = AccessibilityWindowManager.this;
                boolean traceWMEnabled = accessibilityWindowManager.traceWMEnabled();
                int i = this.mDisplayId;
                if (traceWMEnabled) {
                    accessibilityWindowManager.logTraceWM("setWindowsForAccessibilityCallback", "displayId=" + i + ";callback=null");
                }
                accessibilityWindowManager.mWindowManagerInternal.setWindowsForAccessibilityCallback(i, null);
                this.mTrackingWindows = false;
                List emptyList = Collections.emptyList();
                int i2 = accessibilityWindowManager.mActiveWindowId;
                updateWindowsLocked(((AccessibilityManagerService) accessibilityWindowManager.mAccessibilityUserManager).mCurrentUserId, emptyList);
                accessibilityWindowManager.mActiveWindowId = i2;
                this.mWindows = null;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:106:0x0174  */
        /* JADX WARN: Removed duplicated region for block: B:143:0x0244  */
        /* JADX WARN: Removed duplicated region for block: B:153:0x0270  */
        /* JADX WARN: Removed duplicated region for block: B:166:0x02ac A[LOOP:7: B:165:0x02aa->B:166:0x02ac, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:170:0x02c4 A[LOOP:8: B:169:0x02c2->B:170:0x02c4, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:173:0x02d2  */
        /* JADX WARN: Removed duplicated region for block: B:176:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:72:0x013e  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x0145  */
        /* JADX WARN: Removed duplicated region for block: B:86:0x016c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateWindowsLocked(int r21, java.util.List r22) {
            /*
                Method dump skipped, instructions count: 782
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver.updateWindowsLocked(int, java.util.List):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteAccessibilityConnection implements IBinder.DeathRecipient {
        public final IAccessibilityInteractionConnection mConnection;
        public final String mPackageName;
        public final int mUid;
        public final int mUserId;
        public final int mWindowId;

        public RemoteAccessibilityConnection(int i, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String str, int i2, int i3) {
            this.mWindowId = i;
            this.mPackageName = str;
            this.mUid = i2;
            this.mUserId = i3;
            this.mConnection = iAccessibilityInteractionConnection;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            this.mConnection.asBinder().unlinkToDeath(this, 0);
            synchronized (AccessibilityWindowManager.this.mLock) {
                AccessibilityWindowManager.m127$$Nest$mremoveAccessibilityInteractionConnectionLocked(AccessibilityWindowManager.this, this.mWindowId, this.mUserId);
            }
        }
    }

    /* renamed from: -$$Nest$mremoveAccessibilityInteractionConnectionLocked, reason: not valid java name */
    public static void m127$$Nest$mremoveAccessibilityInteractionConnectionLocked(AccessibilityWindowManager accessibilityWindowManager, int i, int i2) {
        IBinder iBinder;
        IBinder iBinder2;
        if (i2 == -1) {
            iBinder2 = (IBinder) accessibilityWindowManager.mGlobalWindowTokens.get(i);
            accessibilityWindowManager.mGlobalWindowTokens.remove(i);
            accessibilityWindowManager.mGlobalInteractionConnections.remove(i);
        } else {
            if (accessibilityWindowManager.mWindowTokens.indexOfKey(i2) >= 0) {
                iBinder = (IBinder) accessibilityWindowManager.getWindowTokensForUserLocked(i2).get(i);
                accessibilityWindowManager.getWindowTokensForUserLocked(i2).remove(i);
            } else {
                iBinder = null;
            }
            if (accessibilityWindowManager.mInteractionConnections.indexOfKey(i2) >= 0) {
                accessibilityWindowManager.getInteractionConnectionsForUserLocked(i2).remove(i);
            }
            iBinder2 = iBinder;
        }
        accessibilityWindowManager.onAccessibilityInteractionConnectionRemovedLocked(i, iBinder2);
    }

    public AccessibilityWindowManager(Object obj, AccessibilityManagerService.MainHandler mainHandler, WindowManagerInternal windowManagerInternal, AccessibilityEventSender accessibilityEventSender, AccessibilitySecurityPolicy accessibilitySecurityPolicy, AccessibilitySecurityPolicy.AccessibilityUserManager accessibilityUserManager, AccessibilityTraceManager accessibilityTraceManager) {
        this.mLock = obj;
        this.mHandler = mainHandler;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mAccessibilityEventSender = accessibilityEventSender;
        this.mSecurityPolicy = accessibilitySecurityPolicy;
        this.mAccessibilityUserManager = accessibilityUserManager;
        this.mTraceManager = accessibilityTraceManager;
    }

    public static int removeAccessibilityInteractionConnectionInternalLocked(IBinder iBinder, SparseArray sparseArray, SparseArray sparseArray2) {
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            if (sparseArray.valueAt(i) == iBinder) {
                int keyAt = sparseArray.keyAt(i);
                sparseArray.removeAt(i);
                RemoteAccessibilityConnection remoteAccessibilityConnection = (RemoteAccessibilityConnection) sparseArray2.get(keyAt);
                remoteAccessibilityConnection.mConnection.asBinder().unlinkToDeath(remoteAccessibilityConnection, 0);
                sparseArray2.remove(keyAt);
                return keyAt;
            }
        }
        return -1;
    }

    public final void clearAccessibilityFocusLocked(int i) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityWindowManager$$ExternalSyntheticLambda0(), this, Integer.valueOf(((AccessibilityManagerService) this.mAccessibilityUserManager).mCurrentUserId), Integer.valueOf(i)));
    }

    public final boolean computePartialInteractiveRegionForWindowLocked(int i, Region region) {
        int resolveParentWindowIdLocked = resolveParentWindowIdLocked(i);
        DisplayWindowsObserver displayWindowObserverByWindowIdLocked = getDisplayWindowObserverByWindowIdLocked(resolveParentWindowIdLocked);
        boolean z = false;
        if (displayWindowObserverByWindowIdLocked != null) {
            boolean z2 = resolveParentWindowIdLocked != i;
            List list = displayWindowObserverByWindowIdLocked.mWindows;
            if (list != null) {
                int size = ((ArrayList) list).size();
                Region region2 = new Region();
                Region region3 = null;
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    AccessibilityWindowInfo accessibilityWindowInfo = (AccessibilityWindowInfo) ((ArrayList) displayWindowObserverByWindowIdLocked.mWindows).get(i2);
                    if (region3 == null) {
                        if (accessibilityWindowInfo.getId() == resolveParentWindowIdLocked) {
                            accessibilityWindowInfo.getRegionInScreen(region2);
                            region.set(region2);
                            region3 = region;
                            if (!z2) {
                            }
                            z = true;
                        }
                    } else {
                        if (accessibilityWindowInfo.getType() != 4) {
                            accessibilityWindowInfo.getRegionInScreen(region2);
                            if (!region3.op(region2, Region.Op.DIFFERENCE)) {
                            }
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    public final void disassociateLocked(IBinder iBinder) {
        this.mHostEmbeddedMap.remove(iBinder);
        for (int size = this.mHostEmbeddedMap.size() - 1; size >= 0; size--) {
            if (((IBinder) this.mHostEmbeddedMap.valueAt(size)).equals(iBinder)) {
                this.mHostEmbeddedMap.removeAt(size);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0083 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.PrintWriter r18, java.lang.String[] r19) {
        /*
            Method dump skipped, instructions count: 446
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityWindowManager.dump(java.io.PrintWriter, java.lang.String[]):void");
    }

    public final AccessibilityWindowInfo findA11yWindowInfoByIdLocked(int i) {
        int resolveParentWindowIdLocked = resolveParentWindowIdLocked(i);
        DisplayWindowsObserver displayWindowObserverByWindowIdLocked = getDisplayWindowObserverByWindowIdLocked(resolveParentWindowIdLocked);
        if (displayWindowObserverByWindowIdLocked != null) {
            return (AccessibilityWindowInfo) displayWindowObserverByWindowIdLocked.mA11yWindowInfoById.get(resolveParentWindowIdLocked);
        }
        return null;
    }

    public final int findFocusedWindowId(int i) {
        int findWindowIdLocked;
        if (traceWMEnabled()) {
            logTraceWM("getFocusedWindowToken", "");
        }
        IBinder focusedWindowTokenFromWindowStates = this.mWindowManagerInternal.getFocusedWindowTokenFromWindowStates();
        synchronized (this.mLock) {
            findWindowIdLocked = findWindowIdLocked(i, focusedWindowTokenFromWindowStates);
        }
        return findWindowIdLocked;
    }

    public final int findWindowIdLocked(int i, IBinder iBinder) {
        int indexOfValue;
        int indexOfValue2 = this.mGlobalWindowTokens.indexOfValue(iBinder);
        if (indexOfValue2 >= 0) {
            return this.mGlobalWindowTokens.keyAt(indexOfValue2);
        }
        if (this.mWindowTokens.indexOfKey(i) < 0 || (indexOfValue = getWindowTokensForUserLocked(i).indexOfValue(iBinder)) < 0) {
            return -1;
        }
        return getWindowTokensForUserLocked(i).keyAt(indexOfValue);
    }

    public final int getActiveWindowId(int i) {
        if (this.mActiveWindowId == -1 && !this.mTouchInteractionInProgress) {
            this.mActiveWindowId = findFocusedWindowId(i);
        }
        return this.mActiveWindowId;
    }

    public final RemoteAccessibilityConnection getConnectionLocked(int i, int i2) {
        RemoteAccessibilityConnection remoteAccessibilityConnection = (RemoteAccessibilityConnection) this.mGlobalInteractionConnections.get(i2);
        if (remoteAccessibilityConnection == null && this.mInteractionConnections.indexOfKey(i) >= 0) {
            remoteAccessibilityConnection = (RemoteAccessibilityConnection) getInteractionConnectionsForUserLocked(i).get(i2);
        }
        if (remoteAccessibilityConnection == null || remoteAccessibilityConnection.mConnection == null) {
            return null;
        }
        return remoteAccessibilityConnection;
    }

    public final int getDefaultFocus(int i) {
        if (i == 1) {
            return this.mTopFocusedWindowId;
        }
        if (i == 2) {
            return this.mAccessibilityFocusedWindowId;
        }
        return -1;
    }

    public final int getDisplayIdByUserIdAndWindowId(int i, int i2) {
        IBinder windowTokenForUserAndWindowIdLocked;
        synchronized (this.mLock) {
            windowTokenForUserAndWindowIdLocked = getWindowTokenForUserAndWindowIdLocked(i, i2);
        }
        if (traceWMEnabled()) {
            logTraceWM("getDisplayIdForWindow", "token=" + windowTokenForUserAndWindowIdLocked);
        }
        return this.mWindowManagerInternal.getDisplayIdForWindow(windowTokenForUserAndWindowIdLocked);
    }

    public final DisplayWindowsObserver getDisplayWindowObserverByWindowIdLocked(int i) {
        int size = this.mDisplayWindowsObservers.size();
        for (int i2 = 0; i2 < size; i2++) {
            DisplayWindowsObserver displayWindowsObserver = (DisplayWindowsObserver) this.mDisplayWindowsObservers.valueAt(i2);
            if (displayWindowsObserver != null && ((WindowInfo) displayWindowsObserver.mWindowInfoById.get(i)) != null) {
                return (DisplayWindowsObserver) this.mDisplayWindowsObservers.get(displayWindowsObserver.mDisplayId);
            }
        }
        return null;
    }

    public final SparseArray getInteractionConnectionsForUserLocked(int i) {
        SparseArray sparseArray = (SparseArray) this.mInteractionConnections.get(i);
        if (sparseArray != null) {
            return sparseArray;
        }
        SparseArray sparseArray2 = new SparseArray();
        this.mInteractionConnections.put(i, sparseArray2);
        return sparseArray2;
    }

    public final IBinder getWindowTokenForUserAndWindowIdLocked(int i, int i2) {
        IBinder iBinder = (IBinder) this.mGlobalWindowTokens.get(i2);
        return (iBinder != null || this.mWindowTokens.indexOfKey(i) < 0) ? iBinder : (IBinder) getWindowTokensForUserLocked(i).get(i2);
    }

    public final SparseArray getWindowTokensForUserLocked(int i) {
        SparseArray sparseArray = (SparseArray) this.mWindowTokens.get(i);
        if (sparseArray != null) {
            return sparseArray;
        }
        SparseArray sparseArray2 = new SparseArray();
        this.mWindowTokens.put(i, sparseArray2);
        return sparseArray2;
    }

    public final boolean isTrackingWindowsLocked(int i) {
        DisplayWindowsObserver displayWindowsObserver = (DisplayWindowsObserver) this.mDisplayWindowsObservers.get(i);
        if (displayWindowsObserver != null) {
            return displayWindowsObserver.mTrackingWindows;
        }
        return false;
    }

    public final void logTraceWM(String str, String str2) {
        this.mTraceManager.logTrace("WindowManagerInternal.".concat(str), 512L, str2);
    }

    public final void notifyOutsideTouch(int i, int i2) {
        int i3;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                DisplayWindowsObserver displayWindowObserverByWindowIdLocked = getDisplayWindowObserverByWindowIdLocked(i2);
                if (displayWindowObserverByWindowIdLocked != null) {
                    List watchOutsideTouchWindowIdLocked = displayWindowObserverByWindowIdLocked.getWatchOutsideTouchWindowIdLocked(i2);
                    for (int i4 = 0; i4 < watchOutsideTouchWindowIdLocked.size(); i4++) {
                        arrayList.add(getConnectionLocked(i, ((Integer) watchOutsideTouchWindowIdLocked.get(i4)).intValue()));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        for (i3 = 0; i3 < arrayList.size(); i3++) {
            RemoteAccessibilityConnection remoteAccessibilityConnection = (RemoteAccessibilityConnection) arrayList.get(i3);
            if (remoteAccessibilityConnection != null) {
                if (this.mTraceManager.isA11yTracingEnabledForTypes(16L)) {
                    this.mTraceManager.logTrace("AccessibilityWindowManager.notifyOutsideTouch", 16L);
                }
                try {
                    remoteAccessibilityConnection.mConnection.notifyOutsideTouch();
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void onAccessibilityInteractionConnectionRemovedLocked(int i, IBinder iBinder) {
        if (!(this.mDisplayWindowsObservers.size() > 0) && i >= 0 && this.mActiveWindowId == i) {
            this.mActiveWindowId = -1;
        }
        if (iBinder != null) {
            if (traceWMEnabled()) {
                logTraceWM("setAccessibilityIdToSurfaceMetadata", "token=" + iBinder + ";windowId=AccessibilityWindowInfo.UNDEFINED_WINDOW_ID");
            }
            this.mWindowManagerInternal.setAccessibilityIdToSurfaceMetadata(iBinder, -1);
        }
        IBinder iBinder2 = (IBinder) this.mWindowIdMap.get(i);
        if (iBinder2 != null) {
            disassociateLocked(iBinder2);
            this.mWindowIdMap.remove(i);
        }
        this.mWindowAttributes.remove(i);
    }

    public final void resetHasProxyIfNeededLocked() {
        int size = this.mDisplayWindowsObservers.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            DisplayWindowsObserver displayWindowsObserver = (DisplayWindowsObserver) this.mDisplayWindowsObservers.valueAt(i);
            if (displayWindowsObserver != null && displayWindowsObserver.mIsProxy) {
                z = true;
            }
        }
        this.mHasProxy = z;
    }

    public final int resolveParentWindowIdLocked(int i) {
        IBinder iBinder = (IBinder) this.mWindowIdMap.get(i);
        if (iBinder == null) {
            return i;
        }
        int indexOfValue = this.mWindowIdMap.indexOfValue(resolveTopParentTokenLocked(iBinder));
        if (indexOfValue != -1) {
            indexOfValue = this.mWindowIdMap.keyAt(indexOfValue);
        }
        return indexOfValue != -1 ? indexOfValue : i;
    }

    public final IBinder resolveTopParentTokenLocked(IBinder iBinder) {
        IBinder iBinder2 = (IBinder) this.mHostEmbeddedMap.get(iBinder);
        return iBinder2 == null ? iBinder : resolveTopParentTokenLocked(iBinder2);
    }

    public final void setAccessibilityFocusedWindowLocked(int i) {
        int i2;
        if (this.mAccessibilityFocusedWindowId != i) {
            ArrayList arrayList = new ArrayList(2);
            int i3 = this.mAccessibilityFocusedDisplayId;
            if (i3 != -1 && (i2 = this.mAccessibilityFocusedWindowId) != -1) {
                arrayList.add(AccessibilityEvent.obtainWindowsChangedEvent(i3, i2, 128));
            }
            this.mAccessibilityFocusedWindowId = i;
            int size = this.mDisplayWindowsObservers.size();
            for (int i4 = 0; i4 < size; i4++) {
                DisplayWindowsObserver displayWindowsObserver = (DisplayWindowsObserver) this.mDisplayWindowsObservers.valueAt(i4);
                if (displayWindowsObserver != null && displayWindowsObserver.setAccessibilityFocusedWindowLocked(i)) {
                    int i5 = displayWindowsObserver.mDisplayId;
                    this.mAccessibilityFocusedDisplayId = i5;
                    arrayList.add(AccessibilityEvent.obtainWindowsChangedEvent(i5, i, 128));
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((AccessibilityManagerService) this.mAccessibilityEventSender).sendAccessibilityEventForCurrentUserLocked((AccessibilityEvent) it.next());
            }
        }
    }

    public final void setActiveWindowLocked(int i) {
        boolean z;
        DisplayWindowsObserver displayWindowObserverByWindowIdLocked;
        if (this.mActiveWindowId != i) {
            ArrayList arrayList = new ArrayList(2);
            int i2 = this.mActiveWindowId;
            if (i2 != -1 && (displayWindowObserverByWindowIdLocked = getDisplayWindowObserverByWindowIdLocked(i2)) != null) {
                arrayList.add(AccessibilityEvent.obtainWindowsChangedEvent(displayWindowObserverByWindowIdLocked.mDisplayId, this.mActiveWindowId, 32));
            }
            this.mActiveWindowId = i;
            int size = this.mDisplayWindowsObservers.size();
            for (int i3 = 0; i3 < size; i3++) {
                DisplayWindowsObserver displayWindowsObserver = (DisplayWindowsObserver) this.mDisplayWindowsObservers.valueAt(i3);
                if (displayWindowsObserver != null) {
                    List list = displayWindowsObserver.mWindows;
                    if (list != null) {
                        int size2 = list.size();
                        z = false;
                        for (int i4 = 0; i4 < size2; i4++) {
                            AccessibilityWindowInfo accessibilityWindowInfo = (AccessibilityWindowInfo) displayWindowsObserver.mWindows.get(i4);
                            if (accessibilityWindowInfo.getId() == i) {
                                z = true;
                                accessibilityWindowInfo.setActive(true);
                            } else {
                                accessibilityWindowInfo.setActive(false);
                            }
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        arrayList.add(AccessibilityEvent.obtainWindowsChangedEvent(displayWindowsObserver.mDisplayId, i, 32));
                    }
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((AccessibilityManagerService) this.mAccessibilityEventSender).sendAccessibilityEventForCurrentUserLocked((AccessibilityEvent) it.next());
            }
        }
    }

    public final boolean setProxyFocusLocked(int i) {
        for (int i2 = 0; i2 < this.mDisplayWindowsObservers.size(); i2++) {
            DisplayWindowsObserver displayWindowsObserver = (DisplayWindowsObserver) this.mDisplayWindowsObservers.valueAt(i2);
            if (displayWindowsObserver != null && displayWindowsObserver.mIsProxy && displayWindowsObserver.setAccessibilityFocusedWindowLocked(i)) {
                int i3 = displayWindowsObserver.mProxyDisplayAccessibilityFocusedWindow;
                if (i3 == i) {
                    return true;
                }
                AccessibilityEventSender accessibilityEventSender = this.mAccessibilityEventSender;
                int i4 = displayWindowsObserver.mDisplayId;
                if (i3 != -1) {
                    clearAccessibilityFocusLocked(i3);
                    ((AccessibilityManagerService) accessibilityEventSender).sendAccessibilityEventForCurrentUserLocked(AccessibilityEvent.obtainWindowsChangedEvent(i4, i3, 128));
                }
                displayWindowsObserver.mProxyDisplayAccessibilityFocusedWindow = i;
                ((AccessibilityManagerService) accessibilityEventSender).sendAccessibilityEventForCurrentUserLocked(AccessibilityEvent.obtainWindowsChangedEvent(i4, i, 128));
                return true;
            }
        }
        return false;
    }

    public final void stopTrackingWindows(int i) {
        synchronized (this.mLock) {
            try {
                DisplayWindowsObserver displayWindowsObserver = (DisplayWindowsObserver) this.mDisplayWindowsObservers.get(i);
                if (displayWindowsObserver != null) {
                    displayWindowsObserver.stopTrackingWindowsLocked();
                    this.mDisplayWindowsObservers.remove(i);
                }
                resetHasProxyIfNeededLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean traceWMEnabled() {
        return this.mTraceManager.isA11yTracingEnabledForTypes(512L);
    }

    public final void updateActiveAndAccessibilityFocusedWindowLocked(int i, int i2, int i3, int i4, long j) {
        List list;
        if (i3 == 32) {
            synchronized (this.mLock) {
                try {
                    if (this.mDisplayWindowsObservers.size() <= 0) {
                        int findFocusedWindowId = findFocusedWindowId(i);
                        this.mTopFocusedWindowId = findFocusedWindowId;
                        if (i2 == findFocusedWindowId) {
                            this.mActiveWindowId = i2;
                        }
                    }
                } finally {
                }
            }
            return;
        }
        if (i3 == 128) {
            synchronized (this.mLock) {
                try {
                    if (this.mTouchInteractionInProgress && this.mActiveWindowId != i2) {
                        setActiveWindowLocked(i2);
                    }
                } finally {
                }
            }
            return;
        }
        if (i3 == 32768) {
            synchronized (this.mLock) {
                try {
                    if (this.mHasProxy && setProxyFocusLocked(i2)) {
                        return;
                    }
                    int i5 = this.mAccessibilityFocusedWindowId;
                    if (i5 != i2) {
                        clearAccessibilityFocusLocked(i5);
                        setAccessibilityFocusedWindowLocked(i2);
                    }
                    this.mAccessibilityFocusNodeId = j;
                    return;
                } finally {
                }
            }
        }
        if (i3 != 65536) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mHasProxy && i4 != 64) {
                    for (int i6 = 0; i6 < this.mDisplayWindowsObservers.size(); i6++) {
                        DisplayWindowsObserver displayWindowsObserver = (DisplayWindowsObserver) this.mDisplayWindowsObservers.get(i6);
                        if (displayWindowsObserver != null && (list = displayWindowsObserver.mWindows) != null && displayWindowsObserver.mIsProxy) {
                            int size = ((ArrayList) list).size();
                            for (int i7 = 0; i7 < size; i7++) {
                                if (((AccessibilityWindowInfo) ((ArrayList) displayWindowsObserver.mWindows).get(i7)).getId() == i2) {
                                    displayWindowsObserver.mProxyDisplayAccessibilityFocusedWindow = -1;
                                    return;
                                }
                            }
                        }
                    }
                }
                if (this.mAccessibilityFocusNodeId == j) {
                    this.mAccessibilityFocusNodeId = 2147483647L;
                }
                if (this.mAccessibilityFocusNodeId == 2147483647L && this.mAccessibilityFocusedWindowId == i2 && i4 != 64) {
                    this.mAccessibilityFocusedWindowId = -1;
                    this.mAccessibilityFocusedDisplayId = -1;
                }
            } finally {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x003a, code lost:
    
        if ((r9 & 1) != 0) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean windowIdBelongsToDisplayType(int r8, int r9) {
        /*
            r7 = this;
            boolean r0 = r7.mHasProxy
            r1 = 1
            if (r0 != 0) goto L6
            return r1
        L6:
            r0 = r9 & 3
            r2 = 3
            if (r0 != r2) goto Lc
            return r1
        Lc:
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            android.util.SparseArray r2 = r7.mDisplayWindowsObservers     // Catch: java.lang.Throwable -> L3e
            int r2 = r2.size()     // Catch: java.lang.Throwable -> L3e
            r3 = 0
            r4 = r3
        L17:
            if (r4 >= r2) goto L43
            android.util.SparseArray r5 = r7.mDisplayWindowsObservers     // Catch: java.lang.Throwable -> L3e
            java.lang.Object r5 = r5.valueAt(r4)     // Catch: java.lang.Throwable -> L3e
            com.android.server.accessibility.AccessibilityWindowManager$DisplayWindowsObserver r5 = (com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver) r5     // Catch: java.lang.Throwable -> L3e
            if (r5 == 0) goto L40
            android.util.SparseArray r6 = r5.mA11yWindowInfoById     // Catch: java.lang.Throwable -> L3e
            java.lang.Object r6 = r6.get(r8)     // Catch: java.lang.Throwable -> L3e
            android.view.accessibility.AccessibilityWindowInfo r6 = (android.view.accessibility.AccessibilityWindowInfo) r6     // Catch: java.lang.Throwable -> L3e
            if (r6 == 0) goto L40
            boolean r7 = r5.mIsProxy     // Catch: java.lang.Throwable -> L3e
            if (r7 == 0) goto L38
            r7 = r9 & 2
            if (r7 == 0) goto L36
            goto L3c
        L36:
            r1 = r3
            goto L3c
        L38:
            r7 = r9 & 1
            if (r7 == 0) goto L36
        L3c:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            return r1
        L3e:
            r7 = move-exception
            goto L45
        L40:
            int r4 = r4 + 1
            goto L17
        L43:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            return r3
        L45:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityWindowManager.windowIdBelongsToDisplayType(int, int):boolean");
    }
}
