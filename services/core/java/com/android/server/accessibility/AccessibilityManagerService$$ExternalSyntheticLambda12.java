package com.android.server.accessibility;

import android.graphics.Region;
import android.os.RemoteCallbackList;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityEvent;

import com.android.internal.inputmethod.IAccessibilityInputMethodSession;
import com.android.internal.util.function.TriConsumer;
import com.android.server.accessibility.gestures.TouchExplorer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda12
        implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda12(int i) {
        this.$r8$classId = i;
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda16(
                    Object obj, Object obj2, Object obj3) {
        AccessibilityInputFilter accessibilityInputFilter;
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        synchronized (accessibilityManagerService.mLock) {
            try {
                if (accessibilityManagerService.mHasInputFilter
                        && (accessibilityInputFilter = accessibilityManagerService.mInputFilter)
                                != null) {
                    accessibilityInputFilter.requestDragging(intValue, intValue2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda29(
                    Object obj, Object obj2, Object obj3) {
        AccessibilityUserState userStateLocked;
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        accessibilityManagerService.sendStateToClients(
                intValue, accessibilityManagerService.mGlobalClients);
        synchronized (accessibilityManagerService.mLock) {
            userStateLocked = accessibilityManagerService.getUserStateLocked(intValue2);
        }
        accessibilityManagerService.sendStateToClients(intValue, userStateLocked.mUserClients);
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda37(
                    Object obj, Object obj2, Object obj3) {
        AccessibilityUserState userStateLocked;
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        synchronized (accessibilityManagerService.mLock) {
            userStateLocked = accessibilityManagerService.getUserStateLocked(intValue2);
        }
        accessibilityManagerService.sendStateToClients(intValue, userStateLocked.mUserClients);
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        AccessibilityInputFilter accessibilityInputFilter;
        AccessibilityInputFilter accessibilityInputFilter2;
        AccessibilityInputFilter accessibilityInputFilter3;
        switch (this.$r8$classId) {
            case 0:
                AccessibilityManagerService accessibilityManagerService =
                        (AccessibilityManagerService) obj;
                int intValue = ((Integer) obj2).intValue();
                Region region = (Region) obj3;
                synchronized (accessibilityManagerService.mLock) {
                    try {
                        if (accessibilityManagerService.mHasInputFilter
                                && (accessibilityInputFilter =
                                                accessibilityManagerService.mInputFilter)
                                        != null
                                && region != null
                                && accessibilityInputFilter.mTouchExplorer.contains(intValue)) {
                            ((TouchExplorer) accessibilityInputFilter.mTouchExplorer.get(intValue))
                                            .mTouchExplorationPassthroughRegion =
                                    region;
                        }
                    } finally {
                    }
                }
                return;
            case 1:
                AccessibilityManagerService accessibilityManagerService2 =
                        (AccessibilityManagerService) obj;
                int intValue2 = ((Integer) obj2).intValue();
                Region region2 = (Region) obj3;
                synchronized (accessibilityManagerService2.mLock) {
                    try {
                        if (accessibilityManagerService2.mHasInputFilter
                                && (accessibilityInputFilter2 =
                                                accessibilityManagerService2.mInputFilter)
                                        != null
                                && region2 != null
                                && accessibilityInputFilter2.mTouchExplorer.contains(intValue2)) {
                            ((TouchExplorer)
                                                    accessibilityInputFilter2.mTouchExplorer.get(
                                                            intValue2))
                                            .mGestureDetectionPassthroughRegion =
                                    region2;
                        }
                    } finally {
                    }
                }
                return;
            case 2:
                AccessibilityManagerService accessibilityManagerService3 =
                        (AccessibilityManagerService) obj;
                int intValue3 = ((Integer) obj2).intValue();
                Boolean bool = (Boolean) obj3;
                boolean booleanValue = bool.booleanValue();
                synchronized (accessibilityManagerService3.mLock) {
                    try {
                        accessibilityManagerService3
                                .getUserStateLocked(accessibilityManagerService3.mCurrentUserId)
                                .mServiceDetectsGestures
                                .put(intValue3, bool);
                        if (accessibilityManagerService3.mHasInputFilter
                                && (accessibilityInputFilter3 =
                                                accessibilityManagerService3.mInputFilter)
                                        != null) {
                            if (accessibilityInputFilter3.mTouchExplorer.contains(intValue3)) {
                                ((TouchExplorer)
                                                accessibilityInputFilter3.mTouchExplorer.get(
                                                        intValue3))
                                        .setServiceDetectsGestures(booleanValue);
                            }
                            accessibilityInputFilter3.mServiceDetectsGestures.put(intValue3, bool);
                        }
                    } finally {
                    }
                }
                return;
            case 3:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda16(
                        obj, obj2, obj3);
                return;
            case 4:
                AccessibilityManagerService accessibilityManagerService4 =
                        (AccessibilityManagerService) obj;
                long longValue = ((Long) obj3).longValue();
                accessibilityManagerService4.notifyClientsOfServicesStateChange(
                        accessibilityManagerService4.mGlobalClients, longValue);
                accessibilityManagerService4.notifyClientsOfServicesStateChange(
                        (RemoteCallbackList) obj2, longValue);
                return;
            case 5:
                ((AccessibilityManagerService) obj)
                        .sendAccessibilityEvent(
                                (AccessibilityEvent) obj2, ((Integer) obj3).intValue());
                return;
            case 6:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda29(
                        obj, obj2, obj3);
                return;
            case 7:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda37(
                        obj, obj2, obj3);
                return;
            default:
                AccessibilityManagerService accessibilityManagerService5 =
                        (AccessibilityManagerService) obj;
                SparseArray sparseArray = (SparseArray) obj2;
                boolean booleanValue2 = ((Boolean) obj3).booleanValue();
                synchronized (accessibilityManagerService5.mLock) {
                    try {
                        AccessibilityUserState userStateLocked =
                                accessibilityManagerService5.getUserStateLocked(
                                        accessibilityManagerService5.mCurrentUserId);
                        for (int size = userStateLocked.mBoundServices.size() - 1;
                                size >= 0;
                                size--) {
                            AccessibilityServiceConnection accessibilityServiceConnection =
                                    (AccessibilityServiceConnection)
                                            userStateLocked.mBoundServices.get(size);
                            if (sparseArray.contains(accessibilityServiceConnection.mId)
                                    && accessibilityServiceConnection.mRequestImeApis) {
                                accessibilityServiceConnection
                                        .mInvocationHandler
                                        .obtainMessage(
                                                11,
                                                booleanValue2 ? 1 : 0,
                                                0,
                                                (IAccessibilityInputMethodSession)
                                                        sparseArray.get(
                                                                accessibilityServiceConnection.mId))
                                        .sendToTarget();
                            }
                        }
                    } finally {
                    }
                }
                return;
        }
    }
}
