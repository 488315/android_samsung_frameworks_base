package android.view;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.ClickableSpan;
import android.util.LongSparseArray;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeIdManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.accessibility.AccessibilityRequestPreparer;
import android.view.accessibility.Flags;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.view.accessibility.IWindowSurfaceInfoCallback;
import android.window.ScreenCapture;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public final class AccessibilityInteractionController {
    private static final boolean CONSIDER_REQUEST_PREPARERS = false;
    private static final boolean ENFORCE_NODE_TREE_CONSISTENT = false;
    private static final int FLAGS_AFFECTING_REPORTED_DATA = 896;
    private static final boolean IGNORE_REQUEST_PREPARERS = true;
    private static final String LOG_TAG = "AccessibilityInteractionController";
    private static final long REQUEST_PREPARER_TIMEOUT_MS = 500;
    private final AccessibilityManager mA11yManager;
    private int mActiveRequestPreparerId;
    private AddNodeInfosForViewId mAddNodeInfosForViewId;
    private final PrivateHandler mHandler;
    private List<MessageHolder> mMessagesWaitingForRequestPreparer;
    private final long mMyLooperThreadId;
    private final int mMyProcessId;
    private int mNumActiveRequestPreparers;
    private ArrayList<Message> mPendingFindNodeByIdMessages;
    private final AccessibilityNodePrefetcher mPrefetcher;
    private final ViewRootImpl mViewRootImpl;
    private final ArrayList<AccessibilityNodeInfo> mTempAccessibilityNodeInfoList = new ArrayList<>();
    private final Object mLock = new Object();
    private final ArrayList<View> mTempArrayList = new ArrayList<>();
    private final Rect mTempRect = new Rect();
    private final RectF mTempRectF = new RectF();

    interface DequeNode {
        void addChildren(AccessibilityNodeInfo accessibilityNodeInfo, PrefetchDeque prefetchDeque);

        AccessibilityNodeInfo getA11yNodeInfo();
    }

    public AccessibilityInteractionController(ViewRootImpl viewRootImpl) {
        Looper looper = viewRootImpl.mHandler.getLooper();
        this.mMyLooperThreadId = looper.getThread().getId();
        this.mMyProcessId = Process.myPid();
        this.mHandler = new PrivateHandler(looper);
        this.mViewRootImpl = viewRootImpl;
        this.mPrefetcher = new AccessibilityNodePrefetcher();
        this.mA11yManager = (AccessibilityManager) viewRootImpl.mContext.getSystemService(AccessibilityManager.class);
        this.mPendingFindNodeByIdMessages = new ArrayList<>();
    }

    private void scheduleMessage(Message message, int i, long j, boolean z) throws Throwable {
        if (z || !holdOffMessageIfNeeded(message, i, j)) {
            if (i == this.mMyProcessId && j == this.mMyLooperThreadId && this.mHandler.hasAccessibilityCallback(message)) {
                AccessibilityInteractionClient.getInstanceForThread(j).setSameThreadMessage(message);
            } else if (!this.mHandler.hasAccessibilityCallback(message) && Thread.currentThread().getId() == this.mMyLooperThreadId) {
                this.mHandler.handleMessage(message);
            } else {
                this.mHandler.sendMessage(message);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShown(View view) {
        return view != null && view.getWindowVisibility() == 0 && view.isShown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isVisibleToAccessibilityService(View view) {
        if (view != null) {
            return this.mA11yManager.isRequestFromAccessibilityTool() || !view.isAccessibilityDataSensitive();
        }
        return false;
    }

    public void findAccessibilityNodeInfoByAccessibilityIdClientThread(long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr, Bundle bundle) {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 2;
        messageObtainMessage.arg1 = i2;
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi1 = AccessibilityNodeInfo.getAccessibilityViewId(j);
        someArgsObtain.argi2 = AccessibilityNodeInfo.getVirtualDescendantId(j);
        someArgsObtain.argi3 = i;
        someArgsObtain.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgsObtain.arg2 = magnificationSpec;
        someArgsObtain.arg3 = region;
        someArgsObtain.arg4 = bundle;
        someArgsObtain.arg5 = fArr;
        messageObtainMessage.obj = someArgsObtain;
        synchronized (this.mLock) {
            this.mPendingFindNodeByIdMessages.add(messageObtainMessage);
            scheduleMessage(messageObtainMessage, i3, j2, false);
        }
    }

    private boolean holdOffMessageIfNeeded(Message message, int i, long j) {
        synchronized (this.mLock) {
            if (this.mNumActiveRequestPreparers != 0) {
                queueMessageToHandleOncePrepared(message, i, j);
                return true;
            }
            if (message.what != 2) {
                return false;
            }
            SomeArgs someArgs = (SomeArgs) message.obj;
            Bundle bundle = (Bundle) someArgs.arg4;
            if (bundle == null) {
                return false;
            }
            List<AccessibilityRequestPreparer> requestPreparersForAccessibilityId = this.mA11yManager.getRequestPreparersForAccessibilityId(someArgs.argi1);
            if (requestPreparersForAccessibilityId == null) {
                return false;
            }
            String string = bundle.getString(AccessibilityNodeInfo.EXTRA_DATA_REQUESTED_KEY);
            if (string == null) {
                return false;
            }
            this.mNumActiveRequestPreparers = requestPreparersForAccessibilityId.size();
            for (int i2 = 0; i2 < requestPreparersForAccessibilityId.size(); i2++) {
                Message messageObtainMessage = this.mHandler.obtainMessage(7);
                SomeArgs someArgsObtain = SomeArgs.obtain();
                someArgsObtain.argi1 = someArgs.argi2 == Integer.MAX_VALUE ? -1 : someArgs.argi2;
                someArgsObtain.arg1 = requestPreparersForAccessibilityId.get(i2);
                someArgsObtain.arg2 = string;
                someArgsObtain.arg3 = bundle;
                Message messageObtainMessage2 = this.mHandler.obtainMessage(8);
                int i3 = this.mActiveRequestPreparerId + 1;
                this.mActiveRequestPreparerId = i3;
                messageObtainMessage2.arg1 = i3;
                someArgsObtain.arg4 = messageObtainMessage2;
                messageObtainMessage.obj = someArgsObtain;
                scheduleMessage(messageObtainMessage, i, j, true);
                this.mHandler.obtainMessage(9);
                this.mHandler.sendEmptyMessageDelayed(9, REQUEST_PREPARER_TIMEOUT_MS);
            }
            queueMessageToHandleOncePrepared(message, i, j);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareForExtraDataRequestUiThread(Message message) {
        SomeArgs someArgs = (SomeArgs) message.obj;
        ((AccessibilityRequestPreparer) someArgs.arg1).onPrepareExtraData(someArgs.argi1, (String) someArgs.arg2, (Bundle) someArgs.arg3, (Message) someArgs.arg4);
    }

    private void queueMessageToHandleOncePrepared(Message message, int i, long j) {
        if (this.mMessagesWaitingForRequestPreparer == null) {
            this.mMessagesWaitingForRequestPreparer = new ArrayList(1);
        }
        this.mMessagesWaitingForRequestPreparer.add(new MessageHolder(message, i, j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPreparerDoneUiThread(Message message) {
        synchronized (this.mLock) {
            if (message.arg1 != this.mActiveRequestPreparerId) {
                Slog.e(LOG_TAG, "Surprising AccessibilityRequestPreparer callback (likely late)");
                return;
            }
            int i = this.mNumActiveRequestPreparers - 1;
            this.mNumActiveRequestPreparers = i;
            if (i <= 0) {
                this.mHandler.removeMessages(9);
                scheduleAllMessagesWaitingForRequestPreparerLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPreparerTimeoutUiThread() {
        synchronized (this.mLock) {
            Slog.e(LOG_TAG, "AccessibilityRequestPreparer timed out");
            scheduleAllMessagesWaitingForRequestPreparerLocked();
        }
    }

    private void scheduleAllMessagesWaitingForRequestPreparerLocked() throws Throwable {
        int size = this.mMessagesWaitingForRequestPreparer.size();
        int i = 0;
        while (i < size) {
            MessageHolder messageHolder = this.mMessagesWaitingForRequestPreparer.get(i);
            AccessibilityInteractionController accessibilityInteractionController = this;
            accessibilityInteractionController.scheduleMessage(messageHolder.mMessage, messageHolder.mInterrogatingPid, messageHolder.mInterrogatingTid, i == 0);
            i++;
            this = accessibilityInteractionController;
        }
        AccessibilityInteractionController accessibilityInteractionController2 = this;
        accessibilityInteractionController2.mMessagesWaitingForRequestPreparer.clear();
        accessibilityInteractionController2.mNumActiveRequestPreparers = 0;
        accessibilityInteractionController2.mActiveRequestPreparerId = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void findAccessibilityNodeInfoByAccessibilityIdUiThread(Message message) throws Throwable {
        AccessibilityInteractionController accessibilityInteractionController;
        ArrayList<AccessibilityNodeInfo> arrayList;
        AccessibilityNodeInfo accessibilityNodeInfoPopulateAccessibilityNodeInfoForView;
        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) {
            IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback) ((SomeArgs) message.obj).arg1;
            try {
                Slog.e(LOG_TAG, "mViewRootImpl is invalid");
                iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, Integer.MAX_VALUE);
                return;
            } catch (RemoteException unused) {
                return;
            }
        }
        synchronized (this.mLock) {
            this.mPendingFindNodeByIdMessages.remove(message);
        }
        int i = message.arg1;
        SomeArgs someArgs = (SomeArgs) message.obj;
        int i2 = someArgs.argi1;
        int i3 = someArgs.argi2;
        int i4 = someArgs.argi3;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback2 = (IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        MagnificationSpec magnificationSpec = (MagnificationSpec) someArgs.arg2;
        Region region = (Region) someArgs.arg3;
        Bundle bundle = (Bundle) someArgs.arg4;
        float[] fArr = (float[]) someArgs.arg5;
        someArgs.recycle();
        boolean z = (i & 32) == 0;
        ArrayList<AccessibilityNodeInfo> arrayList2 = this.mTempAccessibilityNodeInfoList;
        arrayList2.clear();
        try {
            setAccessibilityFetchFlags(i);
            View viewFindViewByAccessibilityId = findViewByAccessibilityId(i2);
            if (viewFindViewByAccessibilityId != null) {
                try {
                    if (isShown(viewFindViewByAccessibilityId)) {
                        accessibilityNodeInfoPopulateAccessibilityNodeInfoForView = populateAccessibilityNodeInfoForView(viewFindViewByAccessibilityId, bundle, i3);
                        try {
                            this.mPrefetcher.mInterruptPrefetch = z;
                            this.mPrefetcher.mFetchFlags = i & 63;
                            if (!z) {
                                arrayList2.add(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView);
                                this.mPrefetcher.prefetchAccessibilityNodeInfos(viewFindViewByAccessibilityId, accessibilityNodeInfoPopulateAccessibilityNodeInfoForView == null ? null : new AccessibilityNodeInfo(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView), arrayList2);
                                resetAccessibilityFetchFlags();
                            }
                        } catch (Throwable th) {
                            th = th;
                            accessibilityInteractionController = this;
                            arrayList = arrayList2;
                            if (!z) {
                                ArrayList<AccessibilityNodeInfo> arrayList3 = arrayList;
                                accessibilityInteractionController.updateInfosForViewportAndReturnFindNodeResult(arrayList3, iAccessibilityInteractionConnectionCallback2, i4, magnificationSpec, fArr, region);
                                SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedRequestInPrefetch = accessibilityInteractionController.getSatisfiedRequestInPrefetch(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView != null ? accessibilityNodeInfoPopulateAccessibilityNodeInfoForView : null, arrayList3, i);
                                if (satisfiedRequestInPrefetch != null) {
                                    accessibilityInteractionController.returnFindNodeResult(satisfiedRequestInPrefetch);
                                    return;
                                }
                                return;
                            }
                            accessibilityInteractionController.updateInfoForViewportAndReturnFindNodeResult(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView != null ? new AccessibilityNodeInfo(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView) : null, iAccessibilityInteractionConnectionCallback2, i4, magnificationSpec, fArr, region);
                            throw th;
                        }
                    } else {
                        accessibilityNodeInfoPopulateAccessibilityNodeInfoForView = null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    accessibilityInteractionController = this;
                    accessibilityNodeInfoPopulateAccessibilityNodeInfoForView = null;
                }
            }
            if (!z) {
                updateInfosForViewportAndReturnFindNodeResult(arrayList2, iAccessibilityInteractionConnectionCallback2, i4, magnificationSpec, fArr, region);
                SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedRequestInPrefetch2 = getSatisfiedRequestInPrefetch(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView != null ? accessibilityNodeInfoPopulateAccessibilityNodeInfoForView : null, arrayList2, i);
                if (satisfiedRequestInPrefetch2 != null) {
                    returnFindNodeResult(satisfiedRequestInPrefetch2);
                    return;
                }
                return;
            }
            updateInfoForViewportAndReturnFindNodeResult(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView == null ? null : new AccessibilityNodeInfo(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView), iAccessibilityInteractionConnectionCallback2, i4, magnificationSpec, fArr, region);
            this.mPrefetcher.prefetchAccessibilityNodeInfos(viewFindViewByAccessibilityId, accessibilityNodeInfoPopulateAccessibilityNodeInfoForView == null ? null : new AccessibilityNodeInfo(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView), arrayList2);
            resetAccessibilityFetchFlags();
            updateInfosForViewPort(arrayList2, magnificationSpec, fArr, region);
            SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedRequestInPrefetch3 = getSatisfiedRequestInPrefetch(accessibilityNodeInfoPopulateAccessibilityNodeInfoForView != null ? accessibilityNodeInfoPopulateAccessibilityNodeInfoForView : null, arrayList2, i);
            returnPrefetchResult(i4, arrayList2, iAccessibilityInteractionConnectionCallback2);
            if (satisfiedRequestInPrefetch3 != null) {
                returnFindNodeResult(satisfiedRequestInPrefetch3);
            }
        } catch (Throwable th3) {
            th = th3;
            accessibilityInteractionController = this;
            arrayList = arrayList2;
            accessibilityNodeInfoPopulateAccessibilityNodeInfoForView = null;
        }
    }

    private AccessibilityNodeInfo populateAccessibilityNodeInfoForView(View view, Bundle bundle, int i) {
        AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
        String string = bundle == null ? null : bundle.getString(AccessibilityNodeInfo.EXTRA_DATA_REQUESTED_KEY);
        if (accessibilityNodeProvider == null) {
            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = view.createAccessibilityNodeInfo();
            if (accessibilityNodeInfoCreateAccessibilityNodeInfo != null && string != null) {
                view.addExtraDataToAccessibilityNodeInfo(accessibilityNodeInfoCreateAccessibilityNodeInfo, string, bundle);
            }
            return accessibilityNodeInfoCreateAccessibilityNodeInfo;
        }
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(i);
        if (accessibilityNodeInfoCreateAccessibilityNodeInfo2 != null && string != null) {
            accessibilityNodeProvider.addExtraDataToAccessibilityNodeInfo(i, accessibilityNodeInfoCreateAccessibilityNodeInfo2, string, bundle);
        }
        return accessibilityNodeInfoCreateAccessibilityNodeInfo2;
    }

    public void findAccessibilityNodeInfosByViewIdClientThread(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 3;
        messageObtainMessage.arg1 = i2;
        messageObtainMessage.arg2 = AccessibilityNodeInfo.getAccessibilityViewId(j);
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi1 = i;
        someArgsObtain.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgsObtain.arg2 = magnificationSpec;
        someArgsObtain.arg3 = str;
        someArgsObtain.arg4 = region;
        someArgsObtain.arg5 = fArr;
        messageObtainMessage.obj = someArgsObtain;
        scheduleMessage(messageObtainMessage, i3, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void findAccessibilityNodeInfosByViewIdUiThread(Message message) throws Throwable {
        AccessibilityInteractionController accessibilityInteractionController;
        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) {
            return;
        }
        int i = message.arg1;
        int i2 = message.arg2;
        SomeArgs someArgs = (SomeArgs) message.obj;
        int i3 = someArgs.argi1;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        MagnificationSpec magnificationSpec = (MagnificationSpec) someArgs.arg2;
        String str = (String) someArgs.arg3;
        Region region = (Region) someArgs.arg4;
        float[] fArr = (float[]) someArgs.arg5;
        someArgs.recycle();
        ArrayList<AccessibilityNodeInfo> arrayList = this.mTempAccessibilityNodeInfoList;
        arrayList.clear();
        try {
            setAccessibilityFetchFlags(i);
            View viewFindViewByAccessibilityId = findViewByAccessibilityId(i2);
            if (viewFindViewByAccessibilityId != null) {
                int identifier = viewFindViewByAccessibilityId.getContext().getResources().getIdentifier(str, null, null);
                if (identifier > 0) {
                    accessibilityInteractionController = this;
                    try {
                        if (accessibilityInteractionController.mAddNodeInfosForViewId == null) {
                            accessibilityInteractionController.mAddNodeInfosForViewId = new AddNodeInfosForViewId();
                        }
                        accessibilityInteractionController.mAddNodeInfosForViewId.init(identifier, arrayList);
                        viewFindViewByAccessibilityId.findViewByPredicate(accessibilityInteractionController.mAddNodeInfosForViewId);
                        accessibilityInteractionController.mAddNodeInfosForViewId.reset();
                    } catch (Throwable th) {
                        th = th;
                        Throwable th2 = th;
                        accessibilityInteractionController.resetAccessibilityFetchFlags();
                        accessibilityInteractionController.updateInfosForViewportAndReturnFindNodeResult(arrayList, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
                        throw th2;
                    }
                } else {
                    resetAccessibilityFetchFlags();
                    accessibilityInteractionController = this;
                    accessibilityInteractionController.updateInfosForViewportAndReturnFindNodeResult(arrayList, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
                }
            } else {
                accessibilityInteractionController = this;
            }
            accessibilityInteractionController.resetAccessibilityFetchFlags();
            accessibilityInteractionController.updateInfosForViewportAndReturnFindNodeResult(arrayList, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
        } catch (Throwable th3) {
            th = th3;
            accessibilityInteractionController = this;
        }
    }

    public void findAccessibilityNodeInfosByTextClientThread(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 4;
        messageObtainMessage.arg1 = i2;
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.arg1 = str;
        someArgsObtain.arg2 = iAccessibilityInteractionConnectionCallback;
        someArgsObtain.arg3 = magnificationSpec;
        someArgsObtain.argi1 = AccessibilityNodeInfo.getAccessibilityViewId(j);
        someArgsObtain.argi2 = AccessibilityNodeInfo.getVirtualDescendantId(j);
        someArgsObtain.argi3 = i;
        someArgsObtain.arg4 = region;
        someArgsObtain.arg5 = fArr;
        messageObtainMessage.obj = someArgsObtain;
        scheduleMessage(messageObtainMessage, i3, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void findAccessibilityNodeInfosByTextUiThread(Message message) {
        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) {
            return;
        }
        int i = message.arg1;
        SomeArgs someArgs = (SomeArgs) message.obj;
        String str = (String) someArgs.arg1;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback) someArgs.arg2;
        MagnificationSpec magnificationSpec = (MagnificationSpec) someArgs.arg3;
        int i2 = someArgs.argi1;
        int i3 = someArgs.argi2;
        int i4 = someArgs.argi3;
        Region region = (Region) someArgs.arg4;
        float[] fArr = (float[]) someArgs.arg5;
        someArgs.recycle();
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = null;
        try {
            setAccessibilityFetchFlags(i);
            View viewFindViewByAccessibilityId = findViewByAccessibilityId(i2);
            if (viewFindViewByAccessibilityId != null && isShown(viewFindViewByAccessibilityId)) {
                AccessibilityNodeProvider accessibilityNodeProvider = viewFindViewByAccessibilityId.getAccessibilityNodeProvider();
                if (accessibilityNodeProvider != null) {
                    listFindAccessibilityNodeInfosByText = accessibilityNodeProvider.findAccessibilityNodeInfosByText(str, i3);
                } else if (i3 == -1) {
                    ArrayList<View> arrayList = this.mTempArrayList;
                    arrayList.clear();
                    viewFindViewByAccessibilityId.findViewsWithText(arrayList, str, 7);
                    if (!arrayList.isEmpty()) {
                        listFindAccessibilityNodeInfosByText = this.mTempAccessibilityNodeInfoList;
                        listFindAccessibilityNodeInfosByText.clear();
                        int size = arrayList.size();
                        for (int i5 = 0; i5 < size; i5++) {
                            View view = arrayList.get(i5);
                            if (isShown(view) && isVisibleToAccessibilityService(view)) {
                                AccessibilityNodeProvider accessibilityNodeProvider2 = view.getAccessibilityNodeProvider();
                                if (accessibilityNodeProvider2 != null) {
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeProvider2.findAccessibilityNodeInfosByText(str, -1);
                                    if (listFindAccessibilityNodeInfosByText2 != null) {
                                        listFindAccessibilityNodeInfosByText.addAll(listFindAccessibilityNodeInfosByText2);
                                    }
                                } else {
                                    listFindAccessibilityNodeInfosByText.add(view.createAccessibilityNodeInfo());
                                }
                            }
                        }
                    }
                }
            }
            resetAccessibilityFetchFlags();
            updateInfosForViewportAndReturnFindNodeResult(listFindAccessibilityNodeInfosByText, iAccessibilityInteractionConnectionCallback, i4, magnificationSpec, fArr, region);
        } catch (Throwable th) {
            resetAccessibilityFetchFlags();
            updateInfosForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, i4, magnificationSpec, fArr, region);
            throw th;
        }
    }

    public void takeScreenshotOfWindowClientThread(int i, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.view.AccessibilityInteractionController$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.function.QuadConsumer
            public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                ((AccessibilityInteractionController) obj).takeScreenshotOfWindowUiThread(((Integer) obj2).intValue(), (ScreenCapture.ScreenCaptureListener) obj3, (IAccessibilityInteractionConnectionCallback) obj4);
            }
        }, this, Integer.valueOf(i), screenCaptureListener, iAccessibilityInteractionConnectionCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void takeScreenshotOfWindowUiThread(int i, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        try {
            if ((this.mViewRootImpl.getWindowFlags() & 8192) != 0) {
                iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(6, i);
            } else if (ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(this.mViewRootImpl.getSurfaceControl()).setChildrenOnly(false).setUid(Process.myUid()).build(), screenCaptureListener) != 0) {
                iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(1, i);
            }
        } catch (RemoteException unused) {
        }
    }

    public void getWindowSurfaceInfoClientThread(IWindowSurfaceInfoCallback iWindowSurfaceInfoCallback) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.view.AccessibilityInteractionController$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((AccessibilityInteractionController) obj).getWindowSurfaceInfoUiThread((IWindowSurfaceInfoCallback) obj2);
            }
        }, this, iWindowSurfaceInfoCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getWindowSurfaceInfoUiThread(IWindowSurfaceInfoCallback iWindowSurfaceInfoCallback) {
        try {
            iWindowSurfaceInfoCallback.provideWindowSurfaceInfo(this.mViewRootImpl.getWindowFlags(), Process.myUid(), this.mViewRootImpl.getSurfaceControl());
        } catch (RemoteException unused) {
        }
    }

    public void findFocusClientThread(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 5;
        messageObtainMessage.arg1 = i3;
        messageObtainMessage.arg2 = i;
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi1 = i2;
        someArgsObtain.argi2 = AccessibilityNodeInfo.getAccessibilityViewId(j);
        someArgsObtain.argi3 = AccessibilityNodeInfo.getVirtualDescendantId(j);
        someArgsObtain.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgsObtain.arg2 = magnificationSpec;
        someArgsObtain.arg3 = region;
        someArgsObtain.arg4 = fArr;
        messageObtainMessage.obj = someArgsObtain;
        scheduleMessage(messageObtainMessage, i4, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void findFocusUiThread(Message message) {
        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) {
            return;
        }
        int i = message.arg1;
        int i2 = message.arg2;
        SomeArgs someArgs = (SomeArgs) message.obj;
        int i3 = someArgs.argi1;
        int i4 = someArgs.argi2;
        int i5 = someArgs.argi3;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        MagnificationSpec magnificationSpec = (MagnificationSpec) someArgs.arg2;
        Region region = (Region) someArgs.arg3;
        float[] fArr = (float[]) someArgs.arg4;
        someArgs.recycle();
        try {
            setAccessibilityFetchFlags(i);
            View viewFindViewByAccessibilityId = findViewByAccessibilityId(i4);
            if (viewFindViewByAccessibilityId != null && isShown(viewFindViewByAccessibilityId)) {
                if (i2 == 1) {
                    View viewFindFocus = viewFindViewByAccessibilityId.findFocus();
                    if (isShown(viewFindFocus) && isVisibleToAccessibilityService(viewFindFocus)) {
                        AccessibilityNodeProvider accessibilityNodeProvider = viewFindFocus.getAccessibilityNodeProvider();
                        accessibilityNodeInfoFindFocus = accessibilityNodeProvider != null ? accessibilityNodeProvider.findFocus(i2) : null;
                        if (accessibilityNodeInfoFindFocus == null) {
                            accessibilityNodeInfoFindFocus = viewFindFocus.createAccessibilityNodeInfo();
                        }
                    }
                } else if (i2 == 2) {
                    View view = this.mViewRootImpl.mAccessibilityFocusedHost;
                    if (view != null && ViewRootImpl.isViewDescendantOf(view, viewFindViewByAccessibilityId) && isShown(view) && isVisibleToAccessibilityService(view)) {
                        AccessibilityNodeProvider accessibilityNodeProvider2 = view.getAccessibilityNodeProvider();
                        if (accessibilityNodeProvider2 != null) {
                            AccessibilityNodeInfo accessibilityNodeInfo = this.mViewRootImpl.mAccessibilityFocusedVirtualView;
                            if (accessibilityNodeInfo != null) {
                                accessibilityNodeInfoFindFocus = accessibilityNodeProvider2.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId()));
                            }
                        } else if (i5 == -1) {
                            accessibilityNodeInfoFindFocus = view.createAccessibilityNodeInfo();
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Unknown focus type: " + i2);
                }
            }
            resetAccessibilityFetchFlags();
            updateInfoForViewportAndReturnFindNodeResult(accessibilityNodeInfoFindFocus, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
        } catch (Throwable th) {
            resetAccessibilityFetchFlags();
            updateInfoForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
            throw th;
        }
    }

    public void focusSearchClientThread(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec, float[] fArr) {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 6;
        messageObtainMessage.arg1 = i3;
        messageObtainMessage.arg2 = AccessibilityNodeInfo.getAccessibilityViewId(j);
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi2 = i;
        someArgsObtain.argi3 = i2;
        someArgsObtain.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgsObtain.arg2 = magnificationSpec;
        someArgsObtain.arg3 = region;
        someArgsObtain.arg4 = fArr;
        messageObtainMessage.obj = someArgsObtain;
        scheduleMessage(messageObtainMessage, i4, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void focusSearchUiThread(Message message) throws Throwable {
        AccessibilityInteractionController accessibilityInteractionController;
        Throwable th;
        View viewFocusSearch;
        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) {
            return;
        }
        int i = message.arg1;
        int i2 = message.arg2;
        SomeArgs someArgs = (SomeArgs) message.obj;
        int i3 = someArgs.argi2;
        int i4 = someArgs.argi3;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        MagnificationSpec magnificationSpec = (MagnificationSpec) someArgs.arg2;
        Region region = (Region) someArgs.arg3;
        float[] fArr = (float[]) someArgs.arg4;
        someArgs.recycle();
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = null;
        try {
            setAccessibilityFetchFlags(i);
            View viewFindViewByAccessibilityId = findViewByAccessibilityId(i2);
            if (viewFindViewByAccessibilityId != null) {
                try {
                    if (isShown(viewFindViewByAccessibilityId) && (viewFocusSearch = viewFindViewByAccessibilityId.focusSearch(i3)) != null) {
                        accessibilityNodeInfoCreateAccessibilityNodeInfo = viewFocusSearch.createAccessibilityNodeInfo();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    accessibilityInteractionController = this;
                    accessibilityInteractionController.resetAccessibilityFetchFlags();
                    accessibilityInteractionController.updateInfoForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, i4, magnificationSpec, fArr, region);
                    throw th;
                }
            }
            resetAccessibilityFetchFlags();
            updateInfoForViewportAndReturnFindNodeResult(accessibilityNodeInfoCreateAccessibilityNodeInfo, iAccessibilityInteractionConnectionCallback, i4, magnificationSpec, fArr, region);
        } catch (Throwable th3) {
            accessibilityInteractionController = this;
            th = th3;
        }
    }

    public void performAccessibilityActionClientThread(long j, int i, Bundle bundle, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 1;
        messageObtainMessage.arg1 = i3;
        messageObtainMessage.arg2 = AccessibilityNodeInfo.getAccessibilityViewId(j);
        SomeArgs someArgsObtain = SomeArgs.obtain();
        someArgsObtain.argi1 = AccessibilityNodeInfo.getVirtualDescendantId(j);
        someArgsObtain.argi2 = i;
        someArgsObtain.argi3 = i2;
        someArgsObtain.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgsObtain.arg2 = bundle;
        messageObtainMessage.obj = someArgsObtain;
        scheduleMessage(messageObtainMessage, i4, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performAccessibilityActionUiThread(Message message) throws Throwable {
        boolean zPerformAccessibilityAction;
        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null || this.mViewRootImpl.mStopped || this.mViewRootImpl.mPausedForTransition) {
            return;
        }
        int i = message.arg1;
        int i2 = message.arg2;
        SomeArgs someArgs = (SomeArgs) message.obj;
        int i3 = someArgs.argi1;
        int i4 = someArgs.argi2;
        int i5 = someArgs.argi3;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        Bundle bundle = (Bundle) someArgs.arg2;
        someArgs.recycle();
        boolean z = false;
        try {
            setAccessibilityFetchFlags(i);
            View viewFindViewByAccessibilityId = findViewByAccessibilityId(i2);
            if (viewFindViewByAccessibilityId != null && isShown(viewFindViewByAccessibilityId) && isVisibleToAccessibilityService(viewFindViewByAccessibilityId)) {
                this.mA11yManager.notifyPerformingAction(i4);
                if (i4 == 16908714) {
                    zPerformAccessibilityAction = handleClickableSpanActionUiThread(viewFindViewByAccessibilityId, i3, bundle);
                } else {
                    AccessibilityNodeProvider accessibilityNodeProvider = viewFindViewByAccessibilityId.getAccessibilityNodeProvider();
                    if (accessibilityNodeProvider != null) {
                        zPerformAccessibilityAction = accessibilityNodeProvider.performAction(i3, i4, bundle);
                    } else {
                        zPerformAccessibilityAction = i3 == -1 ? viewFindViewByAccessibilityId.performAccessibilityAction(i4, bundle) : false;
                    }
                }
                try {
                    this.mA11yManager.notifyPerformingAction(0);
                    z = zPerformAccessibilityAction;
                } catch (Throwable th) {
                    boolean z2 = zPerformAccessibilityAction;
                    th = th;
                    z = z2;
                    try {
                        resetAccessibilityFetchFlags();
                        iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(z, i5);
                    } catch (RemoteException unused) {
                        Slog.e(LOG_TAG, "remote exception in performAccessibilityActionUiThread()");
                    }
                    throw th;
                }
            }
            try {
                resetAccessibilityFetchFlags();
                iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(z, i5);
            } catch (RemoteException unused2) {
                Slog.e(LOG_TAG, "remote exception in performAccessibilityActionUiThread()");
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void clearAccessibilityFocusClientThread() {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 101;
        scheduleMessage(messageObtainMessage, 0, 0L, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccessibilityFocusUiThread() {
        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) {
            return;
        }
        try {
            setAccessibilityFetchFlags(640);
            View rootView = getRootView();
            if (rootView != null && isShown(rootView)) {
                View view = this.mViewRootImpl.mAccessibilityFocusedHost;
                if (view != null && ViewRootImpl.isViewDescendantOf(view, rootView)) {
                    AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
                    AccessibilityNodeInfo accessibilityNodeInfo = this.mViewRootImpl.mAccessibilityFocusedVirtualView;
                    if (accessibilityNodeProvider != null && accessibilityNodeInfo != null) {
                        accessibilityNodeProvider.performAction(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId()), AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
                    } else {
                        view.performAccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
                    }
                }
            }
        } finally {
            resetAccessibilityFetchFlags();
        }
    }

    public void notifyOutsideTouchClientThread() {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 102;
        scheduleMessage(messageObtainMessage, 0, 0L, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOutsideTouchUiThread() {
        View rootView;
        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null || this.mViewRootImpl.mStopped || this.mViewRootImpl.mPausedForTransition || (rootView = getRootView()) == null || !isShown(rootView)) {
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 4, 0.0f, 0.0f, 0);
        motionEventObtain.setSource(4098);
        this.mViewRootImpl.dispatchInputEvent(motionEventObtain);
    }

    private View findViewByAccessibilityId(int i) {
        if (i == 2147483646) {
            return getRootView();
        }
        return AccessibilityNodeIdManager.getInstance().findView(i);
    }

    private View getRootView() {
        if (isVisibleToAccessibilityService(this.mViewRootImpl.mView)) {
            return this.mViewRootImpl.mView;
        }
        return null;
    }

    private void setAccessibilityFetchFlags(int i) {
        this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = i;
        this.mA11yManager.setRequestFromAccessibilityTool((i & 512) != 0);
    }

    private void resetAccessibilityFetchFlags() {
        this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = 0;
        this.mA11yManager.setRequestFromAccessibilityTool(false);
    }

    private void adjustIsVisibleToUserIfNeeded(AccessibilityNodeInfo accessibilityNodeInfo, Region region, MagnificationSpec magnificationSpec) {
        if (region == null || accessibilityNodeInfo == null) {
            return;
        }
        Rect rect = this.mTempRect;
        accessibilityNodeInfo.getBoundsInScreen(rect);
        if (magnificationSpec != null && !magnificationSpec.isNop()) {
            rect.offset((int) (-magnificationSpec.offsetX), (int) (-magnificationSpec.offsetY));
            rect.scale(1.0f / magnificationSpec.scale);
        }
        if (!region.quickReject(rect) || shouldBypassAdjustIsVisible()) {
            return;
        }
        accessibilityNodeInfo.setVisibleToUser(false);
    }

    private boolean shouldBypassAdjustIsVisible() {
        int i = this.mViewRootImpl.mOrigWindowType;
        return i == 2011 || i == 2034;
    }

    private void applyHostWindowMatrixIfNeeded(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null || shouldBypassApplyWindowMatrix()) {
            return;
        }
        Rect rect = this.mTempRect;
        RectF rectF = this.mTempRectF;
        Matrix matrix = this.mViewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy;
        accessibilityNodeInfo.getBoundsInScreen(rect);
        rectF.set(rect);
        matrix.mapRect(rectF);
        rect.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        accessibilityNodeInfo.setBoundsInScreen(rect);
    }

    private boolean shouldBypassApplyWindowMatrix() {
        Matrix matrix = this.mViewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy;
        return matrix == null || matrix.isIdentity();
    }

    private void associateLeashedParentIfNeeded(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null || shouldBypassAssociateLeashedParent() || this.mViewRootImpl.mView.getAccessibilityViewId() != AccessibilityNodeInfo.getAccessibilityViewId(accessibilityNodeInfo.getSourceNodeId())) {
            return;
        }
        accessibilityNodeInfo.setLeashedParent(this.mViewRootImpl.mAttachInfo.mLeashedParentToken, this.mViewRootImpl.mAttachInfo.mLeashedParentAccessibilityViewId);
    }

    private boolean shouldBypassAssociateLeashedParent() {
        return this.mViewRootImpl.mAttachInfo.mLeashedParentToken == null && this.mViewRootImpl.mAttachInfo.mLeashedParentAccessibilityViewId == -1;
    }

    private boolean shouldApplyAppScaleAndMagnificationSpec(float f, MagnificationSpec magnificationSpec) {
        if (f == 1.0f) {
            return (magnificationSpec == null || magnificationSpec.isNop()) ? false : true;
        }
        return true;
    }

    private void updateInfosForViewPort(List<AccessibilityNodeInfo> list, MagnificationSpec magnificationSpec, float[] fArr, Region region) {
        for (int i = 0; i < list.size(); i++) {
            updateInfoForViewPort(list.get(i), magnificationSpec, fArr, region);
        }
    }

    private void updateInfoForViewPort(AccessibilityNodeInfo accessibilityNodeInfo, MagnificationSpec magnificationSpec, float[] fArr, Region region) {
        associateLeashedParentIfNeeded(accessibilityNodeInfo);
        applyHostWindowMatrixIfNeeded(accessibilityNodeInfo);
        transformBoundsWithScreenMatrix(accessibilityNodeInfo, fArr);
        adjustIsVisibleToUserIfNeeded(accessibilityNodeInfo, region, magnificationSpec);
    }

    private void transformBoundsWithScreenMatrix(AccessibilityNodeInfo accessibilityNodeInfo, float[] fArr) {
        RectF[] rectFArr;
        if (accessibilityNodeInfo == null || fArr == null) {
            return;
        }
        Rect rect = this.mTempRect;
        RectF rectF = this.mTempRectF;
        accessibilityNodeInfo.getBoundsInScreen(rect);
        rectF.set(rect);
        Matrix matrix = new Matrix();
        matrix.setValues(fArr);
        float f = this.mViewRootImpl.mAttachInfo.mApplicationScale;
        if (f != 1.0f) {
            matrix.preScale(f, f);
        }
        if (this.mViewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy == null) {
            matrix.preTranslate(-this.mViewRootImpl.mAttachInfo.mWindowLeft, -this.mViewRootImpl.mAttachInfo.mWindowTop);
        }
        if (matrix.isIdentity()) {
            return;
        }
        matrix.mapRect(rectF);
        roundRectFToRect(rectF, rect);
        accessibilityNodeInfo.setBoundsInScreen(rect);
        if (accessibilityNodeInfo.hasExtras() && (rectFArr = (RectF[]) accessibilityNodeInfo.getExtras().getParcelableArray(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY, RectF.class)) != null) {
            for (RectF rectF2 : rectFArr) {
                if (rectF2 != null) {
                    matrix.mapRect(rectF2);
                }
            }
        }
        applyTransformMatrixToBoundsInParentIfNeeded(accessibilityNodeInfo, matrix);
    }

    private void applyTransformMatrixToBoundsInParentIfNeeded(AccessibilityNodeInfo accessibilityNodeInfo, Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        Matrix matrix2 = new Matrix();
        float f = fArr[0];
        matrix2.setScale(f, f);
        if (matrix2.isIdentity()) {
            return;
        }
        Rect rect = this.mTempRect;
        RectF rectF = this.mTempRectF;
        accessibilityNodeInfo.getBoundsInParent(rect);
        rectF.set(rect);
        matrix2.mapRect(rectF);
        roundRectFToRect(rectF, rect);
        accessibilityNodeInfo.setBoundsInParent(rect);
    }

    private void updateInfosForViewportAndReturnFindNodeResult(List<AccessibilityNodeInfo> list, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i, MagnificationSpec magnificationSpec, float[] fArr, Region region) {
        if (list != null) {
            updateInfosForViewPort(list, magnificationSpec, fArr, region);
        }
        returnFindNodesResult(list, iAccessibilityInteractionConnectionCallback, i);
    }

    private void returnFindNodeResult(AccessibilityNodeInfo accessibilityNodeInfo, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i) {
        try {
            iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(accessibilityNodeInfo, i);
        } catch (RemoteException unused) {
        }
    }

    private void returnFindNodeResult(SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedFindAccessibilityNodeByAccessibilityIdRequest) {
        try {
            satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestCallback.setFindAccessibilityNodeInfoResult(satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestNode, satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestInteractionId);
        } catch (RemoteException unused) {
        }
    }

    private void returnFindNodesResult(List<AccessibilityNodeInfo> list, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i) {
        try {
            iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(list, i);
            if (list != null) {
                list.clear();
            }
        } catch (RemoteException unused) {
            Slog.e(LOG_TAG, "remote exception in updateInfosForViewportAndReturnFindNodeResult()");
        }
    }

    private SatisfiedFindAccessibilityNodeByAccessibilityIdRequest getSatisfiedRequestInPrefetch(AccessibilityNodeInfo accessibilityNodeInfo, List<AccessibilityNodeInfo> list, int i) {
        SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedFindAccessibilityNodeByAccessibilityIdRequest;
        synchronized (this.mLock) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.mPendingFindNodeByIdMessages.size()) {
                    satisfiedFindAccessibilityNodeByAccessibilityIdRequest = null;
                    break;
                }
                Message message = this.mPendingFindNodeByIdMessages.get(i2);
                if ((message.arg1 & 896) == (i & 896)) {
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    AccessibilityNodeInfo accessibilityNodeInfoNodeWithIdFromList = nodeWithIdFromList(accessibilityNodeInfo, list, AccessibilityNodeInfo.makeNodeId(someArgs.argi1, someArgs.argi2));
                    if (accessibilityNodeInfoNodeWithIdFromList != null) {
                        this.mHandler.removeMessages(2, message.obj);
                        satisfiedFindAccessibilityNodeByAccessibilityIdRequest = new SatisfiedFindAccessibilityNodeByAccessibilityIdRequest(accessibilityNodeInfoNodeWithIdFromList, (IAccessibilityInteractionConnectionCallback) someArgs.arg1, someArgs.argi3);
                        someArgs.recycle();
                        break;
                    }
                }
                i2++;
            }
            this.mPendingFindNodeByIdMessages.clear();
            if (satisfiedFindAccessibilityNodeByAccessibilityIdRequest != null && satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestNode != accessibilityNodeInfo) {
                list.remove(satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestNode);
            }
        }
        return satisfiedFindAccessibilityNodeByAccessibilityIdRequest;
    }

    private AccessibilityNodeInfo nodeWithIdFromList(AccessibilityNodeInfo accessibilityNodeInfo, List<AccessibilityNodeInfo> list, long j) {
        if (accessibilityNodeInfo != null && accessibilityNodeInfo.getSourceNodeId() == j) {
            return accessibilityNodeInfo;
        }
        for (int i = 0; i < list.size(); i++) {
            AccessibilityNodeInfo accessibilityNodeInfo2 = list.get(i);
            if (accessibilityNodeInfo2.getSourceNodeId() == j) {
                return accessibilityNodeInfo2;
            }
        }
        return null;
    }

    private void returnPrefetchResult(int i, List<AccessibilityNodeInfo> list, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        if (list.size() > 0) {
            try {
                iAccessibilityInteractionConnectionCallback.setPrefetchAccessibilityNodeInfoResult(list, i);
            } catch (RemoteException unused) {
            }
        }
    }

    private void updateInfoForViewportAndReturnFindNodeResult(AccessibilityNodeInfo accessibilityNodeInfo, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i, MagnificationSpec magnificationSpec, float[] fArr, Region region) {
        updateInfoForViewPort(accessibilityNodeInfo, magnificationSpec, fArr, region);
        returnFindNodeResult(accessibilityNodeInfo, iAccessibilityInteractionConnectionCallback, i);
    }

    private boolean handleClickableSpanActionUiThread(View view, int i, Bundle bundle) {
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo;
        ClickableSpan clickableSpanFindClickableSpan;
        Parcelable parcelable = bundle.getParcelable(AccessibilityNodeInfo.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN);
        if (!(parcelable instanceof AccessibilityClickableSpan)) {
            return false;
        }
        AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
        if (accessibilityNodeProvider != null) {
            accessibilityNodeInfoCreateAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(i);
        } else {
            accessibilityNodeInfoCreateAccessibilityNodeInfo = i == -1 ? view.createAccessibilityNodeInfo() : null;
        }
        if (accessibilityNodeInfoCreateAccessibilityNodeInfo == null || (clickableSpanFindClickableSpan = ((AccessibilityClickableSpan) parcelable).findClickableSpan(accessibilityNodeInfoCreateAccessibilityNodeInfo.getOriginalText())) == null) {
            return false;
        }
        clickableSpanFindClickableSpan.onClick(view);
        return true;
    }

    private static void roundRectFToRect(RectF rectF, Rect rect) {
        rect.set((int) (rectF.left + 0.5d), (int) (rectF.top + 0.5d), (int) (rectF.right + 0.5d), (int) (rectF.bottom + 0.5d));
    }

    public void destroy() {
        if (Flags.preventLeakingViewrootimpl()) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    private class AccessibilityNodePrefetcher {
        private int mFetchFlags;
        private boolean mInterruptPrefetch;
        private final ArrayList<View> mTempViewList;

        private AccessibilityNodePrefetcher() {
            this.mTempViewList = new ArrayList<>();
        }

        public void prefetchAccessibilityNodeInfos(View view, AccessibilityNodeInfo accessibilityNodeInfo, List<AccessibilityNodeInfo> list) {
            AccessibilityNodePrefetcher accessibilityNodePrefetcher;
            View view2;
            AccessibilityNodeInfo accessibilityNodeInfo2;
            List<AccessibilityNodeInfo> list2;
            if (accessibilityNodeInfo == null) {
                return;
            }
            AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
            boolean zIsFlagSet = isFlagSet(1);
            if (accessibilityNodeProvider == null) {
                if (zIsFlagSet) {
                    prefetchPredecessorsOfRealNode(view, list);
                }
                if (isFlagSet(2)) {
                    prefetchSiblingsOfRealNode(view, list, zIsFlagSet);
                }
                if (isFlagSet(4)) {
                    prefetchDescendantsOfRealNode(view, list);
                }
                accessibilityNodePrefetcher = this;
                view2 = view;
                accessibilityNodeInfo2 = accessibilityNodeInfo;
                list2 = list;
            } else {
                if (zIsFlagSet) {
                    prefetchPredecessorsOfVirtualNode(accessibilityNodeInfo, view, accessibilityNodeProvider, list);
                }
                if (isFlagSet(2)) {
                    accessibilityNodePrefetcher = this;
                    view2 = view;
                    accessibilityNodeInfo2 = accessibilityNodeInfo;
                    list2 = list;
                    accessibilityNodePrefetcher.prefetchSiblingsOfVirtualNode(accessibilityNodeInfo2, view2, accessibilityNodeProvider, list2, zIsFlagSet);
                } else {
                    accessibilityNodePrefetcher = this;
                    view2 = view;
                    accessibilityNodeInfo2 = accessibilityNodeInfo;
                    list2 = list;
                }
                if (accessibilityNodePrefetcher.isFlagSet(4)) {
                    accessibilityNodePrefetcher.prefetchDescendantsOfVirtualNode(accessibilityNodeInfo2, accessibilityNodeProvider, list2);
                }
            }
            if ((accessibilityNodePrefetcher.isFlagSet(8) || accessibilityNodePrefetcher.isFlagSet(16)) && !accessibilityNodePrefetcher.shouldStopPrefetching(list2)) {
                PrefetchDeque prefetchDeque = AccessibilityInteractionController.this.new PrefetchDeque(accessibilityNodePrefetcher.mFetchFlags & 28, list2);
                accessibilityNodePrefetcher.addChildrenOfRoot(view2, accessibilityNodeInfo2, accessibilityNodeProvider, prefetchDeque);
                prefetchDeque.performTraversalAndPrefetch();
            }
        }

        private void addChildrenOfRoot(View view, AccessibilityNodeInfo accessibilityNodeInfo, AccessibilityNodeProvider accessibilityNodeProvider, PrefetchDeque prefetchDeque) {
            DequeNode virtualNode;
            if (accessibilityNodeProvider == null) {
                virtualNode = AccessibilityInteractionController.this.new ViewNode(view);
            } else {
                virtualNode = AccessibilityInteractionController.this.new VirtualNode(-1L, accessibilityNodeProvider);
            }
            virtualNode.addChildren(accessibilityNodeInfo, prefetchDeque);
        }

        private boolean isFlagSet(int i) {
            return (this.mFetchFlags & i) != 0;
        }

        public boolean shouldStopPrefetching(List list) {
            return (AccessibilityInteractionController.this.mHandler.hasUserInteractiveMessagesWaiting() && this.mInterruptPrefetch) || list.size() >= 50;
        }

        private void enforceNodeTreeConsistent(AccessibilityNodeInfo accessibilityNodeInfo, List<AccessibilityNodeInfo> list) {
            LongSparseArray longSparseArray = new LongSparseArray();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                AccessibilityNodeInfo accessibilityNodeInfo2 = list.get(i);
                longSparseArray.put(accessibilityNodeInfo2.getSourceNodeId(), accessibilityNodeInfo2);
            }
            AccessibilityNodeInfo accessibilityNodeInfo3 = accessibilityNodeInfo;
            while (accessibilityNodeInfo != null) {
                accessibilityNodeInfo3 = accessibilityNodeInfo;
                accessibilityNodeInfo = (AccessibilityNodeInfo) longSparseArray.get(accessibilityNodeInfo.getParentNodeId());
            }
            HashSet hashSet = new HashSet();
            LinkedList linkedList = new LinkedList();
            linkedList.add(accessibilityNodeInfo3);
            AccessibilityNodeInfo accessibilityNodeInfo4 = null;
            AccessibilityNodeInfo accessibilityNodeInfo5 = null;
            while (!linkedList.isEmpty()) {
                AccessibilityNodeInfo accessibilityNodeInfo6 = (AccessibilityNodeInfo) linkedList.poll();
                if (!hashSet.add(accessibilityNodeInfo6)) {
                    throw new IllegalStateException("Duplicate node: " + accessibilityNodeInfo6 + " in window:" + AccessibilityInteractionController.this.mViewRootImpl.mAttachInfo.mAccessibilityWindowId);
                }
                if (accessibilityNodeInfo6.isAccessibilityFocused()) {
                    if (accessibilityNodeInfo4 != null) {
                        throw new IllegalStateException("Duplicate accessibility focus:" + accessibilityNodeInfo6 + " in window:" + AccessibilityInteractionController.this.mViewRootImpl.mAttachInfo.mAccessibilityWindowId);
                    }
                    accessibilityNodeInfo4 = accessibilityNodeInfo6;
                }
                if (accessibilityNodeInfo6.isFocused()) {
                    if (accessibilityNodeInfo5 != null) {
                        throw new IllegalStateException("Duplicate input focus: " + accessibilityNodeInfo6 + " in window:" + AccessibilityInteractionController.this.mViewRootImpl.mAttachInfo.mAccessibilityWindowId);
                    }
                    accessibilityNodeInfo5 = accessibilityNodeInfo6;
                }
                int childCount = accessibilityNodeInfo6.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    AccessibilityNodeInfo accessibilityNodeInfo7 = (AccessibilityNodeInfo) longSparseArray.get(accessibilityNodeInfo6.getChildId(i2));
                    if (accessibilityNodeInfo7 != null) {
                        linkedList.add(accessibilityNodeInfo7);
                    }
                }
            }
            for (int size2 = longSparseArray.size() - 1; size2 >= 0; size2--) {
                AccessibilityNodeInfo accessibilityNodeInfo8 = (AccessibilityNodeInfo) longSparseArray.valueAt(size2);
                if (!hashSet.contains(accessibilityNodeInfo8)) {
                    throw new IllegalStateException("Disconnected node: " + accessibilityNodeInfo8);
                }
            }
        }

        private void prefetchPredecessorsOfRealNode(View view, List<AccessibilityNodeInfo> list) {
            if (shouldStopPrefetching(list)) {
                return;
            }
            for (ViewParent parentForAccessibility = view.getParentForAccessibility(); (parentForAccessibility instanceof View) && !shouldStopPrefetching(list); parentForAccessibility = parentForAccessibility.getParentForAccessibility()) {
                AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = ((View) parentForAccessibility).createAccessibilityNodeInfo();
                if (accessibilityNodeInfoCreateAccessibilityNodeInfo != null) {
                    list.add(accessibilityNodeInfoCreateAccessibilityNodeInfo);
                }
            }
        }

        private void prefetchSiblingsOfRealNode(View view, List<AccessibilityNodeInfo> list, boolean z) {
            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo;
            if (shouldStopPrefetching(list)) {
                return;
            }
            ViewParent parentForAccessibility = view.getParentForAccessibility();
            if (parentForAccessibility instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parentForAccessibility;
                ArrayList<View> arrayList = this.mTempViewList;
                arrayList.clear();
                if (!z) {
                    try {
                        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo2 = ((ViewGroup) parentForAccessibility).createAccessibilityNodeInfo();
                        if (accessibilityNodeInfoCreateAccessibilityNodeInfo2 != null) {
                            list.add(accessibilityNodeInfoCreateAccessibilityNodeInfo2);
                        }
                    } finally {
                        arrayList.clear();
                    }
                }
                viewGroup.addChildrenForAccessibility(arrayList);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    if (shouldStopPrefetching(list)) {
                        return;
                    }
                    View view2 = arrayList.get(i);
                    if (view2.getAccessibilityViewId() != view.getAccessibilityViewId() && AccessibilityInteractionController.this.isShown(view2)) {
                        AccessibilityNodeProvider accessibilityNodeProvider = view2.getAccessibilityNodeProvider();
                        if (accessibilityNodeProvider == null) {
                            accessibilityNodeInfoCreateAccessibilityNodeInfo = view2.createAccessibilityNodeInfo();
                        } else {
                            accessibilityNodeInfoCreateAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(-1);
                        }
                        if (accessibilityNodeInfoCreateAccessibilityNodeInfo != null) {
                            list.add(accessibilityNodeInfoCreateAccessibilityNodeInfo);
                        }
                    }
                }
            }
        }

        private void prefetchDescendantsOfRealNode(View view, List<AccessibilityNodeInfo> list) {
            if (shouldStopPrefetching(list) || !(view instanceof ViewGroup)) {
                return;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            ArrayList<View> arrayList = this.mTempViewList;
            arrayList.clear();
            try {
                view.addChildrenForAccessibility(arrayList);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    if (shouldStopPrefetching(list)) {
                        return;
                    }
                    View view2 = arrayList.get(i);
                    if (AccessibilityInteractionController.this.isShown(view2)) {
                        AccessibilityNodeProvider accessibilityNodeProvider = view2.getAccessibilityNodeProvider();
                        if (accessibilityNodeProvider == null) {
                            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = view2.createAccessibilityNodeInfo();
                            if (accessibilityNodeInfoCreateAccessibilityNodeInfo != null) {
                                list.add(accessibilityNodeInfoCreateAccessibilityNodeInfo);
                                linkedHashMap.put(view2, null);
                            }
                        } else {
                            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(-1);
                            if (accessibilityNodeInfoCreateAccessibilityNodeInfo2 != null) {
                                list.add(accessibilityNodeInfoCreateAccessibilityNodeInfo2);
                                linkedHashMap.put(view2, accessibilityNodeInfoCreateAccessibilityNodeInfo2);
                            }
                        }
                    }
                }
                arrayList.clear();
                if (shouldStopPrefetching(list)) {
                    return;
                }
                for (Map.Entry entry : linkedHashMap.entrySet()) {
                    View view3 = (View) entry.getKey();
                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) entry.getValue();
                    if (accessibilityNodeInfo == null) {
                        prefetchDescendantsOfRealNode(view3, list);
                    } else {
                        prefetchDescendantsOfVirtualNode(accessibilityNodeInfo, view3.getAccessibilityNodeProvider(), list);
                    }
                }
            } finally {
                arrayList.clear();
            }
        }

        private void prefetchPredecessorsOfVirtualNode(AccessibilityNodeInfo accessibilityNodeInfo, View view, AccessibilityNodeProvider accessibilityNodeProvider, List<AccessibilityNodeInfo> list) {
            int size = list.size();
            long parentNodeId = accessibilityNodeInfo.getParentNodeId();
            int accessibilityViewId = AccessibilityNodeInfo.getAccessibilityViewId(parentNodeId);
            while (accessibilityViewId != Integer.MAX_VALUE && !shouldStopPrefetching(list)) {
                int virtualDescendantId = AccessibilityNodeInfo.getVirtualDescendantId(parentNodeId);
                if (virtualDescendantId != -1 || accessibilityViewId == view.getAccessibilityViewId()) {
                    AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(virtualDescendantId);
                    if (accessibilityNodeInfoCreateAccessibilityNodeInfo == null) {
                        for (int size2 = list.size() - 1; size2 >= size; size2--) {
                            list.remove(size2);
                        }
                        return;
                    }
                    list.add(accessibilityNodeInfoCreateAccessibilityNodeInfo);
                    parentNodeId = accessibilityNodeInfoCreateAccessibilityNodeInfo.getParentNodeId();
                    accessibilityViewId = AccessibilityNodeInfo.getAccessibilityViewId(parentNodeId);
                } else {
                    prefetchPredecessorsOfRealNode(view, list);
                    return;
                }
            }
        }

        private void prefetchSiblingsOfVirtualNode(AccessibilityNodeInfo accessibilityNodeInfo, View view, AccessibilityNodeProvider accessibilityNodeProvider, List<AccessibilityNodeInfo> list, boolean z) {
            AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo;
            long parentNodeId = accessibilityNodeInfo.getParentNodeId();
            int accessibilityViewId = AccessibilityNodeInfo.getAccessibilityViewId(parentNodeId);
            int virtualDescendantId = AccessibilityNodeInfo.getVirtualDescendantId(parentNodeId);
            if (virtualDescendantId != -1 || accessibilityViewId == view.getAccessibilityViewId()) {
                AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(virtualDescendantId);
                if (accessibilityNodeInfoCreateAccessibilityNodeInfo2 != null) {
                    if (!z) {
                        list.add(accessibilityNodeInfoCreateAccessibilityNodeInfo2);
                    }
                    int childCount = accessibilityNodeInfoCreateAccessibilityNodeInfo2.getChildCount();
                    for (int i = 0; i < childCount && !shouldStopPrefetching(list); i++) {
                        long childId = accessibilityNodeInfoCreateAccessibilityNodeInfo2.getChildId(i);
                        if (childId != accessibilityNodeInfo.getSourceNodeId() && (accessibilityNodeInfoCreateAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(childId))) != null) {
                            list.add(accessibilityNodeInfoCreateAccessibilityNodeInfo);
                        }
                    }
                    return;
                }
                return;
            }
            prefetchSiblingsOfRealNode(view, list, z);
        }

        private void prefetchDescendantsOfVirtualNode(AccessibilityNodeInfo accessibilityNodeInfo, AccessibilityNodeProvider accessibilityNodeProvider, List<AccessibilityNodeInfo> list) {
            int size = list.size();
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (shouldStopPrefetching(list)) {
                    return;
                }
                AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getChildId(i)));
                if (accessibilityNodeInfoCreateAccessibilityNodeInfo != null) {
                    list.add(accessibilityNodeInfoCreateAccessibilityNodeInfo);
                }
            }
            if (shouldStopPrefetching(list)) {
                return;
            }
            int size2 = list.size() - size;
            for (int i2 = 0; i2 < size2; i2++) {
                prefetchDescendantsOfVirtualNode(list.get(size + i2), accessibilityNodeProvider, list);
            }
        }
    }

    private class PrivateHandler extends Handler {
        private static final int FIRST_NO_ACCESSIBILITY_CALLBACK_MSG = 100;
        private static final int MSG_APP_PREPARATION_FINISHED = 8;
        private static final int MSG_APP_PREPARATION_TIMEOUT = 9;
        private static final int MSG_CLEAR_ACCESSIBILITY_FOCUS = 101;
        private static final int MSG_FIND_ACCESSIBILITY_NODE_INFOS_BY_VIEW_ID = 3;
        private static final int MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_ACCESSIBILITY_ID = 2;
        private static final int MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_TEXT = 4;
        private static final int MSG_FIND_FOCUS = 5;
        private static final int MSG_FOCUS_SEARCH = 6;
        private static final int MSG_NOTIFY_OUTSIDE_TOUCH = 102;
        private static final int MSG_PERFORM_ACCESSIBILITY_ACTION = 1;
        private static final int MSG_PREPARE_FOR_EXTRA_DATA_REQUEST = 7;

        public PrivateHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public String getMessageName(Message message) {
            int i = message.what;
            if (i == 101) {
                return "MSG_CLEAR_ACCESSIBILITY_FOCUS";
            }
            if (i != 102) {
                switch (i) {
                    case 1:
                        return "MSG_PERFORM_ACCESSIBILITY_ACTION";
                    case 2:
                        return "MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_ACCESSIBILITY_ID";
                    case 3:
                        return "MSG_FIND_ACCESSIBILITY_NODE_INFOS_BY_VIEW_ID";
                    case 4:
                        return "MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_TEXT";
                    case 5:
                        return "MSG_FIND_FOCUS";
                    case 6:
                        return "MSG_FOCUS_SEARCH";
                    case 7:
                        return "MSG_PREPARE_FOR_EXTRA_DATA_REQUEST";
                    case 8:
                        return "MSG_APP_PREPARATION_FINISHED";
                    case 9:
                        return "MSG_APP_PREPARATION_TIMEOUT";
                    default:
                        throw new IllegalArgumentException("Unknown message type: " + i);
                }
            }
            return "MSG_NOTIFY_OUTSIDE_TOUCH";
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws Throwable {
            int i = message.what;
            if (i == 101) {
                AccessibilityInteractionController.this.clearAccessibilityFocusUiThread();
                return;
            }
            if (i != 102) {
                switch (i) {
                    case 1:
                        AccessibilityInteractionController.this.performAccessibilityActionUiThread(message);
                        return;
                    case 2:
                        AccessibilityInteractionController.this.findAccessibilityNodeInfoByAccessibilityIdUiThread(message);
                        return;
                    case 3:
                        AccessibilityInteractionController.this.findAccessibilityNodeInfosByViewIdUiThread(message);
                        return;
                    case 4:
                        AccessibilityInteractionController.this.findAccessibilityNodeInfosByTextUiThread(message);
                        return;
                    case 5:
                        AccessibilityInteractionController.this.findFocusUiThread(message);
                        return;
                    case 6:
                        AccessibilityInteractionController.this.focusSearchUiThread(message);
                        return;
                    case 7:
                        AccessibilityInteractionController.this.prepareForExtraDataRequestUiThread(message);
                        return;
                    case 8:
                        AccessibilityInteractionController.this.requestPreparerDoneUiThread(message);
                        return;
                    case 9:
                        AccessibilityInteractionController.this.requestPreparerTimeoutUiThread();
                        return;
                    default:
                        throw new IllegalArgumentException("Unknown message type: " + i);
                }
            }
            AccessibilityInteractionController.this.notifyOutsideTouchUiThread();
        }

        boolean hasAccessibilityCallback(Message message) {
            return message.what < 100;
        }

        boolean hasUserInteractiveMessagesWaiting() {
            return hasMessagesOrCallbacks();
        }
    }

    private final class AddNodeInfosForViewId implements Predicate<View> {
        private List<AccessibilityNodeInfo> mInfos;
        private int mViewId;

        private AddNodeInfosForViewId() {
            this.mViewId = -1;
        }

        public void init(int i, List<AccessibilityNodeInfo> list) {
            this.mViewId = i;
            this.mInfos = list;
        }

        public void reset() {
            this.mViewId = -1;
            this.mInfos = null;
        }

        @Override // java.util.function.Predicate
        public boolean test(View view) {
            if (view.getId() != this.mViewId || !AccessibilityInteractionController.this.isShown(view) || !AccessibilityInteractionController.this.isVisibleToAccessibilityService(view)) {
                return false;
            }
            this.mInfos.add(view.createAccessibilityNodeInfo());
            return false;
        }
    }

    private static final class MessageHolder {
        final int mInterrogatingPid;
        final long mInterrogatingTid;
        final Message mMessage;

        MessageHolder(Message message, int i, long j) {
            this.mMessage = message;
            this.mInterrogatingPid = i;
            this.mInterrogatingTid = j;
        }
    }

    private static class SatisfiedFindAccessibilityNodeByAccessibilityIdRequest {
        final IAccessibilityInteractionConnectionCallback mSatisfiedRequestCallback;
        final int mSatisfiedRequestInteractionId;
        final AccessibilityNodeInfo mSatisfiedRequestNode;

        SatisfiedFindAccessibilityNodeByAccessibilityIdRequest(AccessibilityNodeInfo accessibilityNodeInfo, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i) {
            this.mSatisfiedRequestNode = accessibilityNodeInfo;
            this.mSatisfiedRequestCallback = iAccessibilityInteractionConnectionCallback;
            this.mSatisfiedRequestInteractionId = i;
        }
    }

    private class PrefetchDeque<E extends DequeNode> extends ArrayDeque<E> {
        List<AccessibilityNodeInfo> mPrefetchOutput;
        int mStrategy;

        PrefetchDeque(int i, List<AccessibilityNodeInfo> list) {
            this.mStrategy = i;
            this.mPrefetchOutput = list;
        }

        void performTraversalAndPrefetch() {
            while (!isEmpty()) {
                try {
                    DequeNode next = getNext();
                    AccessibilityNodeInfo a11yNodeInfo = next.getA11yNodeInfo();
                    if (a11yNodeInfo != null) {
                        this.mPrefetchOutput.add(a11yNodeInfo);
                    }
                    if (AccessibilityInteractionController.this.mPrefetcher.shouldStopPrefetching(this.mPrefetchOutput)) {
                        return;
                    } else {
                        next.addChildren(a11yNodeInfo, this);
                    }
                } finally {
                    clear();
                }
            }
        }

        E getNext() {
            if (isStack()) {
                return pop();
            }
            return removeLast();
        }

        boolean isStack() {
            return (this.mStrategy & 8) != 0;
        }
    }

    private class ViewNode implements DequeNode {
        private final ArrayList<View> mTempViewList = new ArrayList<>();
        View mView;

        ViewNode(View view) {
            this.mView = view;
        }

        @Override // android.view.AccessibilityInteractionController.DequeNode
        public AccessibilityNodeInfo getA11yNodeInfo() {
            View view = this.mView;
            if (view == null) {
                return null;
            }
            return view.createAccessibilityNodeInfo();
        }

        @Override // android.view.AccessibilityInteractionController.DequeNode
        public void addChildren(AccessibilityNodeInfo accessibilityNodeInfo, PrefetchDeque prefetchDeque) {
            View view = this.mView;
            if (view != null && (view instanceof ViewGroup)) {
                ArrayList<View> arrayList = this.mTempViewList;
                arrayList.clear();
                try {
                    this.mView.addChildrenForAccessibility(arrayList);
                    int size = arrayList.size();
                    if (prefetchDeque.isStack()) {
                        for (int i = size - 1; i >= 0; i--) {
                            addChild(prefetchDeque, arrayList.get(i));
                        }
                    } else {
                        for (int i2 = 0; i2 < size; i2++) {
                            addChild(prefetchDeque, arrayList.get(i2));
                        }
                    }
                } finally {
                    arrayList.clear();
                }
            }
        }

        private void addChild(ArrayDeque arrayDeque, View view) {
            if (AccessibilityInteractionController.this.isShown(view)) {
                AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
                if (accessibilityNodeProvider == null) {
                    arrayDeque.push(AccessibilityInteractionController.this.new ViewNode(view));
                } else {
                    arrayDeque.push(AccessibilityInteractionController.this.new VirtualNode(-1L, accessibilityNodeProvider));
                }
            }
        }
    }

    private class VirtualNode implements DequeNode {
        long mInfoId;
        AccessibilityNodeProvider mProvider;

        VirtualNode(long j, AccessibilityNodeProvider accessibilityNodeProvider) {
            this.mInfoId = j;
            this.mProvider = accessibilityNodeProvider;
        }

        @Override // android.view.AccessibilityInteractionController.DequeNode
        public AccessibilityNodeInfo getA11yNodeInfo() {
            AccessibilityNodeProvider accessibilityNodeProvider = this.mProvider;
            if (accessibilityNodeProvider == null) {
                return null;
            }
            return accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(this.mInfoId));
        }

        @Override // android.view.AccessibilityInteractionController.DequeNode
        public void addChildren(AccessibilityNodeInfo accessibilityNodeInfo, PrefetchDeque prefetchDeque) {
            if (accessibilityNodeInfo == null) {
                return;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            if (prefetchDeque.isStack()) {
                for (int i = childCount - 1; i >= 0; i--) {
                    prefetchDeque.push(AccessibilityInteractionController.this.new VirtualNode(accessibilityNodeInfo.getChildId(i), this.mProvider));
                }
                return;
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                prefetchDeque.push(AccessibilityInteractionController.this.new VirtualNode(accessibilityNodeInfo.getChildId(i2), this.mProvider));
            }
        }
    }

    public void attachAccessibilityOverlayToWindowClientThread(SurfaceControl surfaceControl, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        this.mHandler.sendMessage(PooledLambda.obtainMessage(new QuadConsumer() { // from class: android.view.AccessibilityInteractionController$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.function.QuadConsumer
            public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                ((AccessibilityInteractionController) obj).attachAccessibilityOverlayToWindowUiThread((SurfaceControl) obj2, ((Integer) obj3).intValue(), (IAccessibilityInteractionConnectionCallback) obj4);
            }
        }, this, surfaceControl, Integer.valueOf(i), iAccessibilityInteractionConnectionCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attachAccessibilityOverlayToWindowUiThread(SurfaceControl surfaceControl, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        SurfaceControl surfaceControl2 = this.mViewRootImpl.getSurfaceControl();
        if (!surfaceControl2.isValid()) {
            try {
                iAccessibilityInteractionConnectionCallback.sendAttachOverlayResult(1, i);
                return;
            } catch (RemoteException unused) {
            }
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.reparent(surfaceControl, surfaceControl2).apply();
        transaction.close();
        try {
            iAccessibilityInteractionConnectionCallback.sendAttachOverlayResult(0, i);
        } catch (RemoteException unused2) {
        }
    }
}
