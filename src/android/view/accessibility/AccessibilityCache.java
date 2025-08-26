package android.view.accessibility;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongArray;
import android.util.LongSparseArray;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class AccessibilityCache {
    public static final int CACHE_CRITICAL_EVENTS_MASK = 4307005;
    private static final boolean CHECK_INTEGRITY;
    private static final boolean DEBUG;
    private static final String LOG_TAG = "AccessibilityCache";
    private static final boolean VERBOSE;
    private final AccessibilityNodeRefresher mAccessibilityNodeRefresher;
    private boolean mIsAllWindowsCached;
    private OnNodeAddedListener mOnNodeAddedListener;
    private boolean mEnabled = true;
    private final SparseArray<String> mWindowIdToEventSourceClassName = new SparseArray<>();
    private final Object mLock = new Object();
    private long mAccessibilityFocus = 2147483647L;
    private long mInputFocus = 2147483647L;
    private long mValidWindowCacheTimeStamp = 0;
    private int mAccessibilityFocusedWindow = -1;
    private int mInputFocusWindow = -1;
    private final SparseArray<SparseArray<AccessibilityWindowInfo>> mWindowCacheByDisplay = new SparseArray<>();
    private final SparseArray<LongSparseArray<AccessibilityNodeInfo>> mNodeCache = new SparseArray<>();
    private final SparseArray<AccessibilityWindowInfo> mTempWindowArray = new SparseArray<>();

    public interface OnNodeAddedListener {
        void onNodeAdded(AccessibilityNodeInfo accessibilityNodeInfo);
    }

    static {
        DEBUG = Log.isLoggable(LOG_TAG, 3) && Build.IS_DEBUGGABLE;
        VERBOSE = Log.isLoggable(LOG_TAG, 2) && Build.IS_DEBUGGABLE;
        CHECK_INTEGRITY = Build.IS_ENG;
    }

    public AccessibilityCache(AccessibilityNodeRefresher accessibilityNodeRefresher) {
        this.mAccessibilityNodeRefresher = accessibilityNodeRefresher;
    }

    public boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEnabled;
        }
        return z;
    }

    public void setEnabled(boolean z) {
        synchronized (this.mLock) {
            this.mEnabled = z;
            clear();
        }
    }

    public void setWindowsOnAllDisplays(SparseArray<List<AccessibilityWindowInfo>> sparseArray, long j) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return;
            }
            if (DEBUG) {
                Log.i(LOG_TAG, "Set windows");
            }
            if (this.mValidWindowCacheTimeStamp > j) {
                return;
            }
            clearWindowCacheLocked();
            if (sparseArray == null) {
                return;
            }
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                List<AccessibilityWindowInfo> listValueAt = sparseArray.valueAt(i);
                if (listValueAt != null) {
                    int iKeyAt = sparseArray.keyAt(i);
                    int size2 = listValueAt.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        addWindowByDisplayLocked(iKeyAt, listValueAt.get(i2));
                    }
                }
            }
            this.mIsAllWindowsCached = true;
        }
    }

    public void addWindow(AccessibilityWindowInfo accessibilityWindowInfo) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return;
            }
            if (DEBUG) {
                Log.i(LOG_TAG, "Caching window: " + accessibilityWindowInfo.getId() + " at display Id [ " + accessibilityWindowInfo.getDisplayId() + " ]");
            }
            addWindowByDisplayLocked(accessibilityWindowInfo.getDisplayId(), accessibilityWindowInfo);
        }
    }

    private void addWindowByDisplayLocked(int i, AccessibilityWindowInfo accessibilityWindowInfo) {
        SparseArray<AccessibilityWindowInfo> sparseArray = this.mWindowCacheByDisplay.get(i);
        if (sparseArray == null) {
            sparseArray = new SparseArray<>();
            this.mWindowCacheByDisplay.put(i, sparseArray);
        }
        sparseArray.put(accessibilityWindowInfo.getId(), new AccessibilityWindowInfo(accessibilityWindowInfo));
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return;
            }
            boolean z = DEBUG;
            if (z) {
                Log.i(LOG_TAG, "onAccessibilityEvent(" + accessibilityEvent + NavigationBarInflaterView.KEY_CODE_END);
            }
            AccessibilityNodeInfo accessibilityNodeInfoRemoveCachedNodeLocked = null;
            switch (accessibilityEvent.getEventType()) {
                case 1:
                case 4:
                case 16:
                case 8192:
                    accessibilityNodeInfoRemoveCachedNodeLocked = removeCachedNodeLocked(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                    if (accessibilityNodeInfoRemoveCachedNodeLocked != null) {
                        if (z) {
                            Log.i(LOG_TAG, "Refreshing and re-adding cached node.");
                        }
                        if (this.mAccessibilityNodeRefresher.refreshNode(accessibilityNodeInfoRemoveCachedNodeLocked, true)) {
                            add(accessibilityNodeInfoRemoveCachedNodeLocked);
                        }
                    }
                    if (CHECK_INTEGRITY) {
                        checkIntegrity();
                        return;
                    }
                    return;
                case 8:
                    if (this.mInputFocus != 2147483647L) {
                        removeCachedNodeLocked(accessibilityEvent.getWindowId(), this.mInputFocus);
                    }
                    this.mInputFocus = accessibilityEvent.getSourceNodeId();
                    this.mInputFocusWindow = accessibilityEvent.getWindowId();
                    accessibilityNodeInfoRemoveCachedNodeLocked = removeCachedNodeLocked(accessibilityEvent.getWindowId(), this.mInputFocus);
                    if (accessibilityNodeInfoRemoveCachedNodeLocked != null) {
                    }
                    if (CHECK_INTEGRITY) {
                    }
                    break;
                case 32:
                    this.mValidWindowCacheTimeStamp = accessibilityEvent.getEventTime();
                    if (accessibilityEvent.getContentChangeTypes() == 0 && accessibilityEvent.getClassName() != null) {
                        this.mWindowIdToEventSourceClassName.put(accessibilityEvent.getWindowId(), accessibilityEvent.getClassName().toString());
                    }
                    clear();
                    if (accessibilityNodeInfoRemoveCachedNodeLocked != null) {
                    }
                    if (CHECK_INTEGRITY) {
                    }
                    break;
                case 2048:
                    synchronized (this.mLock) {
                        int windowId = accessibilityEvent.getWindowId();
                        long sourceNodeId = accessibilityEvent.getSourceNodeId();
                        if ((accessibilityEvent.getContentChangeTypes() & 1) != 0) {
                            clearSubTreeLocked(windowId, sourceNodeId);
                        } else {
                            accessibilityNodeInfoRemoveCachedNodeLocked = removeCachedNodeLocked(windowId, sourceNodeId);
                        }
                    }
                    if (accessibilityNodeInfoRemoveCachedNodeLocked != null) {
                    }
                    if (CHECK_INTEGRITY) {
                    }
                    break;
                case 4096:
                    clearSubTreeLocked(accessibilityEvent.getWindowId(), accessibilityEvent.getSourceNodeId());
                    if (accessibilityNodeInfoRemoveCachedNodeLocked != null) {
                    }
                    if (CHECK_INTEGRITY) {
                    }
                    break;
                case 32768:
                    long j = this.mAccessibilityFocus;
                    if (j != 2147483647L) {
                        removeCachedNodeLocked(this.mAccessibilityFocusedWindow, j);
                    }
                    this.mAccessibilityFocus = accessibilityEvent.getSourceNodeId();
                    int windowId2 = accessibilityEvent.getWindowId();
                    this.mAccessibilityFocusedWindow = windowId2;
                    accessibilityNodeInfoRemoveCachedNodeLocked = removeCachedNodeLocked(windowId2, this.mAccessibilityFocus);
                    if (accessibilityNodeInfoRemoveCachedNodeLocked != null) {
                    }
                    if (CHECK_INTEGRITY) {
                    }
                    break;
                case 65536:
                    if (this.mAccessibilityFocus == accessibilityEvent.getSourceNodeId() && this.mAccessibilityFocusedWindow == accessibilityEvent.getWindowId()) {
                        accessibilityNodeInfoRemoveCachedNodeLocked = removeCachedNodeLocked(this.mAccessibilityFocusedWindow, this.mAccessibilityFocus);
                        this.mAccessibilityFocus = 2147483647L;
                        this.mAccessibilityFocusedWindow = -1;
                    }
                    if (accessibilityNodeInfoRemoveCachedNodeLocked != null) {
                    }
                    if (CHECK_INTEGRITY) {
                    }
                    break;
                case 4194304:
                    this.mValidWindowCacheTimeStamp = accessibilityEvent.getEventTime();
                    if (accessibilityEvent.getWindowChanges() == 2) {
                        this.mWindowIdToEventSourceClassName.remove(accessibilityEvent.getWindowId());
                    }
                    if (accessibilityEvent.getWindowChanges() == 128) {
                        clearWindowCacheLocked();
                    } else {
                        clear();
                    }
                    if (accessibilityNodeInfoRemoveCachedNodeLocked != null) {
                    }
                    if (CHECK_INTEGRITY) {
                    }
                    break;
                default:
                    if (accessibilityNodeInfoRemoveCachedNodeLocked != null) {
                    }
                    if (CHECK_INTEGRITY) {
                    }
                    break;
            }
        }
    }

    private AccessibilityNodeInfo removeCachedNodeLocked(int i, long j) {
        AccessibilityNodeInfo accessibilityNodeInfo;
        if (DEBUG) {
            Log.i(LOG_TAG, "Removing cached node.");
        }
        LongSparseArray<AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(i);
        if (longSparseArray == null || (accessibilityNodeInfo = longSparseArray.get(j)) == null) {
            return null;
        }
        longSparseArray.remove(j);
        return accessibilityNodeInfo;
    }

    public AccessibilityNodeInfo getNode(int i, long j) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return null;
            }
            LongSparseArray<AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(i);
            if (longSparseArray == null) {
                return null;
            }
            AccessibilityNodeInfo accessibilityNodeInfo = longSparseArray.get(j);
            if (accessibilityNodeInfo != null) {
                accessibilityNodeInfo = new AccessibilityNodeInfo(accessibilityNodeInfo);
            }
            if (VERBOSE) {
                Log.i(LOG_TAG, "get(0x" + Long.toHexString(j) + ") = " + accessibilityNodeInfo);
            }
            return accessibilityNodeInfo;
        }
    }

    public boolean isNodeInCache(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return false;
        }
        int windowId = accessibilityNodeInfo.getWindowId();
        long sourceNodeId = accessibilityNodeInfo.getSourceNodeId();
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return false;
            }
            LongSparseArray<AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(windowId);
            if (longSparseArray == null) {
                return false;
            }
            return longSparseArray.get(sourceNodeId) != null;
        }
    }

    public SparseArray<List<AccessibilityWindowInfo>> getWindowsOnAllDisplays() {
        int size;
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return null;
            }
            if (!this.mIsAllWindowsCached) {
                return null;
            }
            SparseArray<List<AccessibilityWindowInfo>> sparseArray = new SparseArray<>();
            int size2 = this.mWindowCacheByDisplay.size();
            if (size2 <= 0) {
                return null;
            }
            for (int i = 0; i < size2; i++) {
                int iKeyAt = this.mWindowCacheByDisplay.keyAt(i);
                SparseArray<AccessibilityWindowInfo> sparseArrayValueAt = this.mWindowCacheByDisplay.valueAt(i);
                if (sparseArrayValueAt != null && (size = sparseArrayValueAt.size()) > 0) {
                    SparseArray<AccessibilityWindowInfo> sparseArray2 = this.mTempWindowArray;
                    sparseArray2.clear();
                    for (int i2 = 0; i2 < size; i2++) {
                        AccessibilityWindowInfo accessibilityWindowInfoValueAt = sparseArrayValueAt.valueAt(i2);
                        sparseArray2.put(accessibilityWindowInfoValueAt.getLayer(), accessibilityWindowInfoValueAt);
                    }
                    int size3 = sparseArray2.size();
                    ArrayList arrayList = new ArrayList(size3);
                    for (int i3 = size3 - 1; i3 >= 0; i3--) {
                        arrayList.add(new AccessibilityWindowInfo(sparseArray2.valueAt(i3)));
                        sparseArray2.removeAt(i3);
                    }
                    sparseArray.put(iKeyAt, arrayList);
                }
            }
            return sparseArray;
        }
    }

    public AccessibilityWindowInfo getWindow(int i) {
        AccessibilityWindowInfo accessibilityWindowInfo;
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return null;
            }
            int size = this.mWindowCacheByDisplay.size();
            for (int i2 = 0; i2 < size; i2++) {
                SparseArray<AccessibilityWindowInfo> sparseArrayValueAt = this.mWindowCacheByDisplay.valueAt(i2);
                if (sparseArrayValueAt != null && (accessibilityWindowInfo = sparseArrayValueAt.get(i)) != null) {
                    return new AccessibilityWindowInfo(accessibilityWindowInfo);
                }
            }
            return null;
        }
    }

    public void add(AccessibilityNodeInfo accessibilityNodeInfo) {
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return;
            }
            if (VERBOSE) {
                Log.i(LOG_TAG, "add(" + accessibilityNodeInfo + NavigationBarInflaterView.KEY_CODE_END);
            }
            int windowId = accessibilityNodeInfo.getWindowId();
            LongSparseArray<AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(windowId);
            if (longSparseArray == null) {
                longSparseArray = new LongSparseArray<>();
                this.mNodeCache.put(windowId, longSparseArray);
            }
            long sourceNodeId = accessibilityNodeInfo.getSourceNodeId();
            AccessibilityNodeInfo accessibilityNodeInfo2 = longSparseArray.get(sourceNodeId);
            if (accessibilityNodeInfo2 != null) {
                LongArray childNodeIds = accessibilityNodeInfo.getChildNodeIds();
                int childCount = accessibilityNodeInfo2.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    long childId = accessibilityNodeInfo2.getChildId(i);
                    if (childNodeIds == null || childNodeIds.indexOf(childId) < 0) {
                        clearSubTreeLocked(windowId, childId);
                    }
                    if (longSparseArray.get(sourceNodeId) == null) {
                        clearNodesForWindowLocked(windowId);
                        return;
                    }
                }
                long parentNodeId = accessibilityNodeInfo2.getParentNodeId();
                if (accessibilityNodeInfo.getParentNodeId() != parentNodeId) {
                    clearSubTreeLocked(windowId, parentNodeId);
                }
            }
            AccessibilityNodeInfo accessibilityNodeInfo3 = new AccessibilityNodeInfo(accessibilityNodeInfo);
            longSparseArray.put(sourceNodeId, accessibilityNodeInfo3);
            if (accessibilityNodeInfo3.isAccessibilityFocused()) {
                long j = this.mAccessibilityFocus;
                if (j != 2147483647L && j != sourceNodeId) {
                    removeCachedNodeLocked(windowId, j);
                }
                this.mAccessibilityFocus = sourceNodeId;
                this.mAccessibilityFocusedWindow = windowId;
            } else if (this.mAccessibilityFocus == sourceNodeId) {
                this.mAccessibilityFocus = 2147483647L;
                this.mAccessibilityFocusedWindow = -1;
            }
            if (accessibilityNodeInfo3.isFocused()) {
                this.mInputFocus = sourceNodeId;
                this.mInputFocusWindow = windowId;
            }
            OnNodeAddedListener onNodeAddedListener = this.mOnNodeAddedListener;
            if (onNodeAddedListener != null) {
                onNodeAddedListener.onNodeAdded(accessibilityNodeInfo3);
            }
        }
    }

    public void clear() {
        synchronized (this.mLock) {
            if (DEBUG) {
                Log.i(LOG_TAG, "clear()");
            }
            clearWindowCacheLocked();
            for (int size = this.mNodeCache.size() - 1; size >= 0; size--) {
                clearNodesForWindowLocked(this.mNodeCache.keyAt(size));
            }
            this.mAccessibilityFocus = 2147483647L;
            this.mInputFocus = 2147483647L;
            this.mAccessibilityFocusedWindow = -1;
            this.mInputFocusWindow = -1;
        }
    }

    private void clearWindowCacheLocked() {
        if (DEBUG) {
            Log.i(LOG_TAG, "clearWindowCacheLocked");
        }
        int size = this.mWindowCacheByDisplay.size();
        if (size > 0) {
            for (int i = size - 1; i >= 0; i--) {
                int iKeyAt = this.mWindowCacheByDisplay.keyAt(i);
                SparseArray<AccessibilityWindowInfo> sparseArray = this.mWindowCacheByDisplay.get(iKeyAt);
                if (sparseArray != null) {
                    sparseArray.clear();
                }
                this.mWindowCacheByDisplay.remove(iKeyAt);
            }
        }
        this.mIsAllWindowsCached = false;
    }

    public AccessibilityNodeInfo getFocus(int i, long j, int i2) {
        int i3;
        long j2;
        String str;
        String str2;
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return null;
            }
            if (i == 2) {
                i3 = this.mAccessibilityFocusedWindow;
                j2 = this.mAccessibilityFocus;
            } else {
                i3 = this.mInputFocusWindow;
                j2 = this.mInputFocus;
            }
            long j3 = j2;
            if (i3 == -1) {
                return null;
            }
            if (i2 != -2 && i2 != i3) {
                return null;
            }
            LongSparseArray<AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(i3);
            if (longSparseArray == null) {
                return null;
            }
            AccessibilityNodeInfo accessibilityNodeInfo = longSparseArray.get(j3);
            if (accessibilityNodeInfo == null) {
                return null;
            }
            if (j != j3 && !isCachedNodeOrDescendantLocked(accessibilityNodeInfo.getParentNodeId(), j, longSparseArray)) {
                if (VERBOSE) {
                    if (i == 2) {
                        str2 = "FOCUS_ACCESSIBILITY";
                    } else {
                        str2 = "FOCUS_INPUT";
                    }
                    Log.i(LOG_TAG, "getFocus is null with type: ".concat(str2));
                }
                return null;
            }
            if (VERBOSE) {
                StringBuilder sb = new StringBuilder("getFocus(0x");
                sb.append(Long.toHexString(j3));
                sb.append(") = ");
                sb.append(accessibilityNodeInfo);
                sb.append(" with type: ");
                if (i == 2) {
                    str = "FOCUS_ACCESSIBILITY";
                } else {
                    str = "FOCUS_INPUT";
                }
                sb.append(str);
                Log.i(LOG_TAG, sb.toString());
            }
            return new AccessibilityNodeInfo(accessibilityNodeInfo);
        }
    }

    private boolean isCachedNodeOrDescendantLocked(long j, long j2, LongSparseArray<AccessibilityNodeInfo> longSparseArray) {
        if (j2 == j) {
            return true;
        }
        AccessibilityNodeInfo accessibilityNodeInfo = longSparseArray.get(j);
        if (accessibilityNodeInfo == null) {
            return false;
        }
        return isCachedNodeOrDescendantLocked(accessibilityNodeInfo.getParentNodeId(), j2, longSparseArray);
    }

    private void clearNodesForWindowLocked(int i) {
        if (DEBUG) {
            Log.i(LOG_TAG, "clearNodesForWindowLocked(" + i + NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mNodeCache.get(i) == null) {
            return;
        }
        this.mNodeCache.remove(i);
    }

    public boolean clearSubTree(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return false;
        }
        synchronized (this.mLock) {
            if (!this.mEnabled) {
                if (DEBUG) {
                    Log.i(LOG_TAG, "Cache is disabled");
                }
                return false;
            }
            clearSubTreeLocked(accessibilityNodeInfo.getWindowId(), accessibilityNodeInfo.getSourceNodeId());
            return true;
        }
    }

    private void clearSubTreeLocked(int i, long j) {
        if (DEBUG) {
            Log.i(LOG_TAG, "Clearing cached subtree.");
        }
        LongSparseArray<AccessibilityNodeInfo> longSparseArray = this.mNodeCache.get(i);
        if (longSparseArray != null) {
            clearSubTreeRecursiveLocked(longSparseArray, j);
        }
    }

    private boolean clearSubTreeRecursiveLocked(LongSparseArray<AccessibilityNodeInfo> longSparseArray, long j) {
        AccessibilityNodeInfo accessibilityNodeInfo = longSparseArray.get(j);
        if (accessibilityNodeInfo == null) {
            clear();
            return true;
        }
        longSparseArray.remove(j);
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (clearSubTreeRecursiveLocked(longSparseArray, accessibilityNodeInfo.getChildId(i))) {
                return true;
            }
        }
        return false;
    }

    public void checkIntegrity() {
        AccessibilityNodeInfo accessibilityNodeInfo;
        AccessibilityCache accessibilityCache = this;
        synchronized (accessibilityCache.mLock) {
            if (accessibilityCache.mWindowCacheByDisplay.size() > 0 || accessibilityCache.mNodeCache.size() != 0) {
                int size = accessibilityCache.mWindowCacheByDisplay.size();
                AccessibilityNodeInfo accessibilityNodeInfo2 = null;
                AccessibilityWindowInfo accessibilityWindowInfo = null;
                AccessibilityWindowInfo accessibilityWindowInfo2 = null;
                for (int i = 0; i < size; i++) {
                    SparseArray<AccessibilityWindowInfo> sparseArrayValueAt = accessibilityCache.mWindowCacheByDisplay.valueAt(i);
                    if (sparseArrayValueAt != null) {
                        int size2 = sparseArrayValueAt.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            AccessibilityWindowInfo accessibilityWindowInfoValueAt = sparseArrayValueAt.valueAt(i2);
                            if (accessibilityWindowInfoValueAt.isActive()) {
                                if (accessibilityWindowInfo != null) {
                                    Log.e(LOG_TAG, "Duplicate active window:" + accessibilityWindowInfoValueAt);
                                } else {
                                    accessibilityWindowInfo = accessibilityWindowInfoValueAt;
                                }
                            }
                            if (accessibilityWindowInfoValueAt.isFocused()) {
                                if (accessibilityWindowInfo2 != null) {
                                    Log.e(LOG_TAG, "Duplicate focused window:" + accessibilityWindowInfoValueAt);
                                } else {
                                    accessibilityWindowInfo2 = accessibilityWindowInfoValueAt;
                                }
                            }
                        }
                    }
                }
                int size3 = accessibilityCache.mNodeCache.size();
                AccessibilityNodeInfo accessibilityNodeInfo3 = null;
                int i3 = 0;
                while (i3 < size3) {
                    LongSparseArray<AccessibilityNodeInfo> longSparseArrayValueAt = accessibilityCache.mNodeCache.valueAt(i3);
                    if (longSparseArrayValueAt.size() > 0) {
                        ArraySet arraySet = new ArraySet();
                        int iKeyAt = accessibilityCache.mNodeCache.keyAt(i3);
                        int size4 = longSparseArrayValueAt.size();
                        for (int i4 = 0; i4 < size4; i4++) {
                            AccessibilityNodeInfo accessibilityNodeInfoValueAt = longSparseArrayValueAt.valueAt(i4);
                            if (!arraySet.add(accessibilityNodeInfoValueAt)) {
                                Log.e(LOG_TAG, "Duplicate node: " + accessibilityNodeInfoValueAt + " in window:" + iKeyAt);
                            } else {
                                if (accessibilityNodeInfoValueAt.isAccessibilityFocused()) {
                                    if (accessibilityNodeInfo2 != null) {
                                        Log.e(LOG_TAG, "Duplicate accessibility focus:" + accessibilityNodeInfoValueAt + " in window:" + iKeyAt);
                                    } else {
                                        accessibilityNodeInfo2 = accessibilityNodeInfoValueAt;
                                    }
                                }
                                if (accessibilityNodeInfoValueAt.isFocused()) {
                                    if (accessibilityNodeInfo3 != null) {
                                        Log.e(LOG_TAG, "Duplicate input focus: " + accessibilityNodeInfoValueAt + " in window:" + iKeyAt);
                                    } else {
                                        accessibilityNodeInfo3 = accessibilityNodeInfoValueAt;
                                    }
                                }
                                AccessibilityNodeInfo accessibilityNodeInfo4 = longSparseArrayValueAt.get(accessibilityNodeInfoValueAt.getParentNodeId());
                                if (accessibilityNodeInfo4 != null) {
                                    int childCount = accessibilityNodeInfo4.getChildCount();
                                    int i5 = 0;
                                    while (true) {
                                        if (i5 < childCount) {
                                            accessibilityNodeInfo = accessibilityNodeInfo3;
                                            if (longSparseArrayValueAt.get(accessibilityNodeInfo4.getChildId(i5)) == accessibilityNodeInfoValueAt) {
                                                break;
                                            }
                                            i5++;
                                            accessibilityNodeInfo3 = accessibilityNodeInfo;
                                        } else {
                                            accessibilityNodeInfo = accessibilityNodeInfo3;
                                            Log.e(LOG_TAG, "Invalid parent-child relation between parent: " + accessibilityNodeInfo4 + " and child: " + accessibilityNodeInfoValueAt);
                                            break;
                                        }
                                    }
                                } else {
                                    accessibilityNodeInfo = accessibilityNodeInfo3;
                                }
                                int childCount2 = accessibilityNodeInfoValueAt.getChildCount();
                                for (int i6 = 0; i6 < childCount2; i6++) {
                                    AccessibilityNodeInfo accessibilityNodeInfo5 = longSparseArrayValueAt.get(accessibilityNodeInfoValueAt.getChildId(i6));
                                    if (accessibilityNodeInfo5 != null && longSparseArrayValueAt.get(accessibilityNodeInfo5.getParentNodeId()) != accessibilityNodeInfoValueAt) {
                                        Log.e(LOG_TAG, "Invalid child-parent relation between child: " + accessibilityNodeInfoValueAt + " and parent: " + accessibilityNodeInfo4);
                                    }
                                }
                                accessibilityNodeInfo3 = accessibilityNodeInfo;
                            }
                        }
                    }
                    i3++;
                    accessibilityCache = this;
                }
            }
        }
    }

    public void registerOnNodeAddedListener(OnNodeAddedListener onNodeAddedListener) {
        synchronized (this.mLock) {
            this.mOnNodeAddedListener = onNodeAddedListener;
        }
    }

    public void clearOnNodeAddedListener() {
        synchronized (this.mLock) {
            this.mOnNodeAddedListener = null;
        }
    }

    public String getEventSourceClassName(int i) {
        return this.mWindowIdToEventSourceClassName.get(i);
    }

    public static class AccessibilityNodeRefresher {
        public boolean refreshNode(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            return accessibilityNodeInfo.refresh(null, z);
        }

        public boolean refreshWindow(AccessibilityWindowInfo accessibilityWindowInfo) {
            return accessibilityWindowInfo.refresh();
        }
    }
}
