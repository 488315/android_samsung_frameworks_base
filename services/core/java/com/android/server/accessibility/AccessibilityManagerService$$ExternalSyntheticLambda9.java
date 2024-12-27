package com.android.server.accessibility;

import android.R;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.hardware.fingerprint.IFingerprintService;
import android.os.Binder;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.ArraySet;
import android.view.Display;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.EditorInfo;

import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.os.SomeArgs;
import com.android.server.accessibility.gestures.TouchExplorer;
import com.android.server.accessibility.gestures.TouchState;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.BiConsumer;

public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda9
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda9(int i) {
        this.$r8$classId = i;
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda34(
                    Object obj, Object obj2) {
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        AccessibilityServiceConnection accessibilityServiceConnection =
                (AccessibilityServiceConnection) obj2;
        synchronized (accessibilityManagerService.mLock) {
            if (accessibilityManagerService.mInputSessionRequested) {
                accessibilityServiceConnection.mInvocationHandler.obtainMessage(10).sendToTarget();
            }
        }
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda35(
                    Object obj, Object obj2) {
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        AccessibilityServiceConnection accessibilityServiceConnection =
                (AccessibilityServiceConnection) obj2;
        synchronized (accessibilityManagerService.mLock) {
            try {
                if (accessibilityManagerService.mInputBound) {
                    accessibilityServiceConnection
                            .mInvocationHandler
                            .obtainMessage(12)
                            .sendToTarget();
                    IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection =
                            accessibilityManagerService.mRemoteInputConnection;
                    EditorInfo editorInfo = accessibilityManagerService.mEditorInfo;
                    boolean z = accessibilityManagerService.mRestarting;
                    AbstractAccessibilityServiceConnection.InvocationHandler invocationHandler =
                            accessibilityServiceConnection.mInvocationHandler;
                    invocationHandler.getClass();
                    SomeArgs obtain = SomeArgs.obtain();
                    obtain.arg1 = iRemoteAccessibilityInputConnection;
                    obtain.arg2 = editorInfo;
                    invocationHandler.obtainMessage(14, z ? 1 : 0, 0, obtain).sendToTarget();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda36(
                    Object obj, Object obj2) {
        AccessibilityInputFilter accessibilityInputFilter;
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        int intValue = ((Integer) obj2).intValue();
        synchronized (accessibilityManagerService.mLock) {
            try {
                if (!accessibilityManagerService.mHasInputFilter
                        || (accessibilityInputFilter = accessibilityManagerService.mInputFilter)
                                == null) {
                    accessibilityInputFilter = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (accessibilityInputFilter == null
                || !accessibilityInputFilter.mTouchExplorer.contains(intValue)) {
            return;
        }
        TouchExplorer touchExplorer =
                (TouchExplorer) accessibilityInputFilter.mTouchExplorer.get(intValue);
        TouchState touchState = touchExplorer.mState;
        touchExplorer.onDoubleTap(
                touchState.mLastReceivedEvent,
                touchState.mLastReceivedRawEvent,
                touchState.mLastReceivedPolicyFlags);
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda39(
                    Object obj, Object obj2) {
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        AccessibilityServiceConnection accessibilityServiceConnection =
                (AccessibilityServiceConnection) obj2;
        accessibilityManagerService.getClass();
        InputMethodManagerInternal.get()
                .unbindAccessibilityFromCurrentClient(accessibilityServiceConnection.mId);
        synchronized (accessibilityManagerService.mLock) {
            accessibilityServiceConnection.mInvocationHandler.obtainMessage(13).sendToTarget();
        }
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda44(
                    Object obj, Object obj2) {
        AccessibilityInputFilter accessibilityInputFilter;
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        int intValue = ((Integer) obj2).intValue();
        synchronized (accessibilityManagerService.mLock) {
            try {
                if (accessibilityManagerService.mHasInputFilter
                        && (accessibilityInputFilter = accessibilityManagerService.mInputFilter)
                                != null) {
                    accessibilityInputFilter.requestDelegating(intValue);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001e, code lost:

       if (r10.mIsAMEnabled != false) goto L11;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda45(
                    java.lang.Object r9, java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 398
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda9.accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda45(java.lang.Object,"
                    + " java.lang.Object):void");
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda47(
                    Object obj, Object obj2) {
        ArrayList arrayList;
        int i;
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        AccessibilityUserState accessibilityUserState = (AccessibilityUserState) obj2;
        synchronized (accessibilityManagerService.mLock) {
            try {
                arrayList = accessibilityUserState.mBoundServices;
                if (accessibilityManagerService.mFingerprintGestureDispatcher == null
                        && accessibilityManagerService.mPackageManager.hasSystemFeature(
                                "android.hardware.fingerprint")) {
                    int size = arrayList.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        if (((AccessibilityServiceConnection) arrayList.get(i2))
                                .isCapturingFingerprintGestures()) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                IFingerprintService asInterface =
                                        IFingerprintService.Stub.asInterface(
                                                ServiceManager.getService("fingerprint"));
                                if (asInterface != null) {
                                    accessibilityManagerService.mFingerprintGestureDispatcher =
                                            new FingerprintGestureDispatcher(
                                                    asInterface,
                                                    accessibilityManagerService.mContext
                                                            .getResources(),
                                                    accessibilityManagerService.mLock);
                                    break;
                                }
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                        i2++;
                    }
                }
            } finally {
            }
        }
        FingerprintGestureDispatcher fingerprintGestureDispatcher =
                accessibilityManagerService.mFingerprintGestureDispatcher;
        if (fingerprintGestureDispatcher == null
                || !fingerprintGestureDispatcher.mHardwareSupportsGestures) {
            return;
        }
        synchronized (fingerprintGestureDispatcher.mLock) {
            try {
                ((ArrayList) fingerprintGestureDispatcher.mCapturingClients).clear();
                for (i = 0; i < arrayList.size(); i++) {
                    FingerprintGestureDispatcher.FingerprintGestureClient fingerprintGestureClient =
                            (FingerprintGestureDispatcher.FingerprintGestureClient)
                                    arrayList.get(i);
                    if (fingerprintGestureClient.isCapturingFingerprintGestures()) {
                        ((ArrayList) fingerprintGestureDispatcher.mCapturingClients)
                                .add(fingerprintGestureClient);
                    }
                }
                if (((ArrayList) fingerprintGestureDispatcher.mCapturingClients).isEmpty()) {
                    if (fingerprintGestureDispatcher.mRegisteredReadOnlyExceptInHandler) {
                        fingerprintGestureDispatcher.mHandler.obtainMessage(2).sendToTarget();
                    }
                } else if (!fingerprintGestureDispatcher.mRegisteredReadOnlyExceptInHandler) {
                    fingerprintGestureDispatcher.mHandler.obtainMessage(1).sendToTarget();
                }
            } finally {
            }
        }
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda48(
                    Object obj, Object obj2) {
        final AccessibilityManagerService accessibilityManagerService =
                (AccessibilityManagerService) obj;
        final AccessibilityServiceConnection accessibilityServiceConnection =
                (AccessibilityServiceConnection) obj2;
        synchronized (accessibilityManagerService.mLock) {
            try {
                String charSequence =
                        accessibilityServiceConnection
                                .mAccessibilityServiceInfo
                                .getResolveInfo()
                                .loadLabel(accessibilityManagerService.mContext.getPackageManager())
                                .toString();
                final AccessibilityUserState userStateLocked =
                        accessibilityManagerService.getUserStateLocked(
                                accessibilityManagerService.mCurrentUserId);
                if (userStateLocked.mIsTouchExplorationEnabled) {
                    return;
                }
                AlertDialog alertDialog = accessibilityManagerService.mEnableTouchExplorationDialog;
                if (alertDialog == null || !alertDialog.isShowing()) {
                    AlertDialog create =
                            new AlertDialog.Builder(accessibilityManagerService.mContext)
                                    .setIconAttribute(R.attr.alertDialogIcon)
                                    .setPositiveButton(
                                            R.string.ok,
                                            new DialogInterface.OnClickListener() { // from class:
                                                // com.android.server.accessibility.AccessibilityManagerService.6
                                                public final /* synthetic */
                                                AccessibilityServiceConnection val$service;
                                                public final /* synthetic */ AccessibilityUserState
                                                        val$userState;

                                                public AnonymousClass6(
                                                        final AccessibilityUserState
                                                                userStateLocked2,
                                                        final AccessibilityServiceConnection
                                                                accessibilityServiceConnection2) {
                                                    r2 = userStateLocked2;
                                                    r3 = accessibilityServiceConnection2;
                                                }

                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i) {
                                                    ((HashSet) r2.mTouchExplorationGrantedServices)
                                                            .add(r3.mComponentName);
                                                    AccessibilityManagerService
                                                            accessibilityManagerService2 =
                                                                    AccessibilityManagerService
                                                                            .this;
                                                    AccessibilityUserState accessibilityUserState =
                                                            r2;
                                                    accessibilityManagerService2
                                                            .persistComponentNamesToSettingLocked(
                                                                    accessibilityUserState.mUserId,
                                                                    "touch_exploration_granted_accessibility_services",
                                                                    accessibilityUserState
                                                                            .mTouchExplorationGrantedServices);
                                                    r2.mIsTouchExplorationEnabled = true;
                                                    long clearCallingIdentity =
                                                            Binder.clearCallingIdentity();
                                                    try {
                                                        Settings.Secure.putIntForUser(
                                                                AccessibilityManagerService.this
                                                                        .mContext
                                                                        .getContentResolver(),
                                                                "touch_exploration_enabled",
                                                                1,
                                                                r2.mUserId);
                                                        Binder.restoreCallingIdentity(
                                                                clearCallingIdentity);
                                                        AccessibilityManagerService.this
                                                                .onUserStateChangedLocked(
                                                                        r2, false);
                                                    } catch (Throwable th) {
                                                        Binder.restoreCallingIdentity(
                                                                clearCallingIdentity);
                                                        throw th;
                                                    }
                                                }
                                            })
                                    .setNegativeButton(
                                            R.string.cancel,
                                            new AccessibilityManagerService.AnonymousClass5())
                                    .setTitle(R.string.indeterminate_progress_57)
                                    .setMessage(
                                            accessibilityManagerService.mContext.getString(
                                                    R.string.indeterminate_progress_56,
                                                    charSequence))
                                    .create();
                    accessibilityManagerService.mEnableTouchExplorationDialog = create;
                    create.getWindow().setType(2003);
                    accessibilityManagerService
                                    .mEnableTouchExplorationDialog
                                    .getWindow()
                                    .getAttributes()
                                    .privateFlags |=
                            16;
                    accessibilityManagerService.mEnableTouchExplorationDialog
                            .setCanceledOnTouchOutside(true);
                    accessibilityManagerService.mEnableTouchExplorationDialog.show();
                }
            } finally {
            }
        }
    }

    private final void
            accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda64(
                    Object obj, Object obj2) {
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        ArraySet arraySet = (ArraySet) obj2;
        synchronized (accessibilityManagerService.mLock) {
            try {
                accessibilityManagerService.mInputSessionRequested = true;
                AccessibilityUserState userStateLocked =
                        accessibilityManagerService.getUserStateLocked(
                                accessibilityManagerService.mCurrentUserId);
                for (int size = userStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    AccessibilityServiceConnection accessibilityServiceConnection =
                            (AccessibilityServiceConnection)
                                    userStateLocked.mBoundServices.get(size);
                    if (!arraySet.contains(Integer.valueOf(accessibilityServiceConnection.mId))
                            && accessibilityServiceConnection.mRequestImeApis) {
                        accessibilityServiceConnection
                                .mInvocationHandler
                                .obtainMessage(10)
                                .sendToTarget();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        AccessibilityInputFilter accessibilityInputFilter;
        AccessibilityInputFilter accessibilityInputFilter2;
        AccessibilityInputFilter accessibilityInputFilter3;
        switch (this.$r8$classId) {
            case 0:
                AccessibilityManagerService accessibilityManagerService =
                        (AccessibilityManagerService) obj;
                AccessibilityEvent accessibilityEvent = (AccessibilityEvent) obj2;
                synchronized (accessibilityManagerService.mLock) {
                    try {
                        if (accessibilityManagerService.mHasInputFilter
                                && (accessibilityInputFilter =
                                                accessibilityManagerService.mInputFilter)
                                        != null) {
                            for (int i = 0;
                                    i < accessibilityInputFilter.mEventHandler.size();
                                    i++) {
                                EventStreamTransformation eventStreamTransformation =
                                        (EventStreamTransformation)
                                                accessibilityInputFilter.mEventHandler.valueAt(i);
                                if (eventStreamTransformation != null) {
                                    eventStreamTransformation.onAccessibilityEvent(
                                            accessibilityEvent);
                                }
                            }
                        }
                    } finally {
                    }
                }
                accessibilityEvent.recycle();
                return;
            case 1:
                AccessibilityManagerService accessibilityManagerService2 =
                        (AccessibilityManagerService) obj;
                int intValue = ((Integer) obj2).intValue();
                synchronized (accessibilityManagerService2.mLock) {
                    try {
                        if (accessibilityManagerService2.mHasInputFilter
                                && (accessibilityInputFilter2 =
                                                accessibilityManagerService2.mInputFilter)
                                        != null
                                && accessibilityInputFilter2.mTouchExplorer.contains(intValue)) {
                            TouchExplorer touchExplorer =
                                    (TouchExplorer)
                                            accessibilityInputFilter2.mTouchExplorer.get(intValue);
                            TouchState touchState = touchExplorer.mState;
                            touchExplorer.onDoubleTapAndHold(
                                    touchState.mLastReceivedEvent,
                                    touchState.mLastReceivedRawEvent,
                                    touchState.mLastReceivedPolicyFlags);
                        }
                    } finally {
                    }
                }
                return;
            case 2:
                AccessibilityManagerService accessibilityManagerService3 =
                        (AccessibilityManagerService) obj;
                int intValue2 = ((Integer) obj2).intValue();
                synchronized (accessibilityManagerService3.mLock) {
                    try {
                        if (accessibilityManagerService3.mHasInputFilter
                                && (accessibilityInputFilter3 =
                                                accessibilityManagerService3.mInputFilter)
                                        != null) {
                            accessibilityInputFilter3.requestTouchExploration(intValue2);
                        }
                    } finally {
                    }
                }
                return;
            case 3:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda34(
                        obj, obj2);
                return;
            case 4:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda35(
                        obj, obj2);
                return;
            case 5:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda36(
                        obj, obj2);
                return;
            case 6:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda39(
                        obj, obj2);
                return;
            case 7:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda44(
                        obj, obj2);
                return;
            case 8:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda45(
                        obj, obj2);
                return;
            case 9:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda47(
                        obj, obj2);
                return;
            case 10:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda48(
                        obj, obj2);
                return;
            case 11:
                ComponentName componentName = (ComponentName) obj2;
                StatusBarManagerService.AnonymousClass2 anonymousClass2 =
                        (StatusBarManagerService.AnonymousClass2) ((StatusBarManagerInternal) obj);
                anonymousClass2.getClass();
                if (android.view.accessibility.Flags.a11yQsShortcut()) {
                    StatusBarManagerService.this.remTile(componentName);
                    return;
                }
                return;
            case 12:
                accept$com$android$server$accessibility$AccessibilityManagerService$$ExternalSyntheticLambda64(
                        obj, obj2);
                return;
            default:
                AccessibilityManagerService accessibilityManagerService4 =
                        (AccessibilityManagerService) obj;
                int intValue3 = ((Integer) obj2).intValue();
                synchronized (accessibilityManagerService4.mLock) {
                    try {
                        if (accessibilityManagerService4.mHasInputFilter) {
                            ArrayList validDisplayList =
                                    accessibilityManagerService4.getValidDisplayList();
                            for (int i2 = 0; i2 < validDisplayList.size(); i2++) {
                                Display display = (Display) validDisplayList.get(i2);
                                if (display != null && display.getDisplayId() == intValue3) {
                                    accessibilityManagerService4.mInputFilter
                                            .refreshMagnificationMode(display);
                                    return;
                                }
                            }
                            return;
                        }
                        return;
                    } finally {
                    }
                }
        }
    }
}
