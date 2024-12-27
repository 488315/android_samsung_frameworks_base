package com.android.server.wm;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.IWindow;
import android.view.InputWindowHandle;
import android.view.MagnificationSpec;
import android.view.WindowInfo;
import android.window.WindowInfosListener;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.Flags;
import com.android.server.wm.utils.RegionUtils;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AccessibilityWindowsPopulator extends WindowInfosListener {
    public static final float[] sTempFloats = new float[9];
    public final AccessibilityController mAccessibilityController;
    public final MyHandler mHandler;
    public final WindowManagerService mService;
    public final SparseArray mInputWindowHandlesOnDisplays = new SparseArray();
    public final SparseArray mMagnificationSpecInverseMatrix = new SparseArray();
    public final SparseArray mDisplayInfos = new SparseArray();
    public final SparseArray mCurrentMagnificationSpec = new SparseArray();
    public final SparseArray mPreviousMagnificationSpec = new SparseArray();
    public final List mVisibleWindows = new ArrayList();
    public boolean mWindowsNotificationEnabled = false;
    public final Map mWindowsTransformMatrixMap = new HashMap();
    public final Object mLock = new Object();
    public final Matrix mTempMatrix1 = new Matrix();
    public final Matrix mTempMatrix2 = new Matrix();
    public final float[] mTempFloat1 = new float[9];
    public final float[] mTempFloat2 = new float[9];
    public final float[] mTempFloat3 = new float[9];

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccessibilityWindow {
        public int mDisplayId;
        public boolean mIgnoreDuetoRecentsAnimation;
        public int mInputConfig;
        public boolean mIsFocused;
        public boolean mIsPIPMenu;
        public int mPrivateFlags;
        public boolean mShouldMagnify;
        public int mType;
        public IBinder mWindow;
        public WindowInfo mWindowInfo;
        public final Region mTouchableRegionInScreen = new Region();
        public final Region mTouchableRegionInWindow = new Region();
        public Rect mSystemBarInsetFrame = null;

        public final boolean isTouchable() {
            return (this.mInputConfig & 8) == 0;
        }

        public final String toString() {
            IBinder iBinder = this.mWindow;
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("A11yWindow=[", iBinder != null ? iBinder.toString() : "(no window token)", ", displayId=");
            m.append(this.mDisplayId);
            m.append(", inputConfig=0x");
            BatteryService$$ExternalSyntheticOutline0.m(this.mInputConfig, m, ", type=");
            m.append(this.mType);
            m.append(", privateFlag=0x");
            BatteryService$$ExternalSyntheticOutline0.m(this.mPrivateFlags, m, ", focused=");
            m.append(this.mIsFocused);
            m.append(", shouldMagnify=");
            m.append(this.mShouldMagnify);
            m.append(", ignoreDuetoRecentsAnimation=");
            m.append(this.mIgnoreDuetoRecentsAnimation);
            m.append(", isTrustedOverlay=");
            m.append((this.mInputConfig & 256) != 0);
            m.append(", regionInScreen=");
            m.append(this.mTouchableRegionInScreen);
            m.append(", touchableRegion=");
            m.append(this.mTouchableRegionInWindow);
            m.append(", isPIPMenu=");
            m.append(this.mIsPIPMenu);
            m.append(", windowInfo=");
            m.append(this.mWindowInfo);
            m.append("]");
            return m.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            AccessibilityWindowsPopulator accessibilityWindowsPopulator = AccessibilityWindowsPopulator.this;
            if (i == 1) {
                accessibilityWindowsPopulator.notifyWindowsChanged((List) message.obj);
                return;
            }
            if (i == 2) {
                AccessibilityWindowsPopulator.m1048$$Nest$mforceUpdateWindows(accessibilityWindowsPopulator);
            } else {
                if (i != 3) {
                    return;
                }
                float[] fArr = AccessibilityWindowsPopulator.sTempFloats;
                Slog.w("AccessibilityWindowsPopulator", "Windows change within in 2 frames continuously over 500 ms and notify windows changed immediately");
                accessibilityWindowsPopulator.mHandler.removeMessages(2);
                AccessibilityWindowsPopulator.m1048$$Nest$mforceUpdateWindows(accessibilityWindowsPopulator);
            }
        }
    }

    /* renamed from: -$$Nest$mforceUpdateWindows, reason: not valid java name */
    public static void m1048$$Nest$mforceUpdateWindows(AccessibilityWindowsPopulator accessibilityWindowsPopulator) {
        accessibilityWindowsPopulator.getClass();
        ArrayList arrayList = new ArrayList();
        synchronized (accessibilityWindowsPopulator.mLock) {
            for (int i = 0; i < accessibilityWindowsPopulator.mInputWindowHandlesOnDisplays.size(); i++) {
                try {
                    arrayList.add(Integer.valueOf(accessibilityWindowsPopulator.mInputWindowHandlesOnDisplays.keyAt(i)));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        accessibilityWindowsPopulator.notifyWindowsChanged(arrayList);
    }

    public AccessibilityWindowsPopulator(WindowManagerService windowManagerService, AccessibilityController accessibilityController) {
        this.mService = windowManagerService;
        this.mAccessibilityController = accessibilityController;
        this.mHandler = new MyHandler(windowManagerService.mH.getLooper());
    }

    public static void generateInverseMatrix(MagnificationSpec magnificationSpec, Matrix matrix) {
        matrix.reset();
        Matrix matrix2 = new Matrix();
        matrix2.reset();
        float f = magnificationSpec.scale;
        matrix2.postScale(f, f);
        matrix2.postTranslate(magnificationSpec.offsetX, magnificationSpec.offsetY);
        if (matrix2.invert(matrix)) {
            return;
        }
        Slog.e("AccessibilityWindowsPopulator", "Can't inverse the magnification spec matrix with the magnification spec = " + magnificationSpec);
        matrix.reset();
    }

    public final void notifyWindowsChanged(List list) {
        this.mHandler.removeMessages(3);
        for (int i = 0; i < list.size(); i++) {
            this.mAccessibilityController.performComputeChangedWindowsNot(((Integer) list.get(i)).intValue(), false);
        }
    }

    public final void onWindowInfosChanged(final InputWindowHandle[] inputWindowHandleArr, final WindowInfosListener.DisplayInfo[] displayInfoArr) {
        Flags.removeOnWindowInfosChangedHandler();
        this.mHandler.post(new Runnable() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AccessibilityWindowsPopulator.this.onWindowInfosChangedInternal(inputWindowHandleArr, displayInfoArr);
            }
        });
    }

    public final void onWindowInfosChangedInternal(InputWindowHandle[] inputWindowHandleArr, WindowInfosListener.DisplayInfo[] displayInfoArr) {
        HashMap hashMap;
        ArrayList arrayList = new ArrayList();
        for (InputWindowHandle inputWindowHandle : inputWindowHandleArr) {
            int i = inputWindowHandle.inputConfig;
            boolean z = (i & 2) == 0;
            boolean z2 = (i & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) == 0;
            boolean z3 = !inputWindowHandle.touchableRegion.isEmpty();
            boolean isEmpty = true ^ inputWindowHandle.frame.isEmpty();
            if (z && z2 && z3 && isEmpty) {
                arrayList.add(inputWindowHandle);
            }
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                hashMap = new HashMap();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    IBinder windowToken = ((InputWindowHandle) it.next()).getWindowToken();
                    WindowState windowState = windowToken != null ? (WindowState) this.mService.mWindowMap.get(windowToken) : null;
                    if (windowState != null && windowState.shouldMagnify()) {
                        Matrix matrix = new Matrix();
                        windowState.getTransformationMatrix(sTempFloats, matrix);
                        hashMap.put(windowToken, matrix);
                    }
                }
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        synchronized (this.mLock) {
            try {
                ((HashMap) this.mWindowsTransformMatrixMap).clear();
                ((HashMap) this.mWindowsTransformMatrixMap).putAll(hashMap);
                ((ArrayList) this.mVisibleWindows).clear();
                ((ArrayList) this.mVisibleWindows).addAll(arrayList);
                this.mDisplayInfos.clear();
                for (WindowInfosListener.DisplayInfo displayInfo : displayInfoArr) {
                    this.mDisplayInfos.put(displayInfo.mDisplayId, displayInfo);
                }
                if (this.mWindowsNotificationEnabled) {
                    if (!this.mHandler.hasMessages(3)) {
                        this.mHandler.sendEmptyMessageDelayed(3, 450L);
                    }
                    populateVisibleWindowHandlesAndNotifyWindowsChangeIfNeeded();
                }
            } finally {
            }
        }
    }

    public final void populateVisibleWindowHandlesAndNotifyWindowsChangeIfNeeded() {
        SparseArray sparseArray = new SparseArray();
        Iterator it = ((ArrayList) this.mVisibleWindows).iterator();
        while (it.hasNext()) {
            InputWindowHandle inputWindowHandle = (InputWindowHandle) it.next();
            List list = (List) sparseArray.get(inputWindowHandle.displayId);
            if (list == null) {
                list = new ArrayList();
                sparseArray.put(inputWindowHandle.displayId, list);
            }
            list.add(inputWindowHandle);
        }
        int i = 0;
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            int keyAt = sparseArray.keyAt(i2);
            List list2 = (List) sparseArray.get(keyAt);
            MagnificationSpec magnificationSpec = (MagnificationSpec) this.mCurrentMagnificationSpec.get(keyAt);
            if (magnificationSpec != null) {
                MagnificationSpec magnificationSpec2 = new MagnificationSpec();
                magnificationSpec2.setTo(magnificationSpec);
                MagnificationSpec magnificationSpec3 = (MagnificationSpec) this.mPreviousMagnificationSpec.get(keyAt);
                if (magnificationSpec3 == null) {
                    Matrix matrix = new Matrix();
                    generateInverseMatrix(magnificationSpec2, matrix);
                    this.mMagnificationSpecInverseMatrix.put(keyAt, matrix);
                } else {
                    MagnificationSpec magnificationSpec4 = new MagnificationSpec();
                    magnificationSpec4.setTo(magnificationSpec3);
                    int size = list2.size() - 1;
                    while (true) {
                        if (size >= 0) {
                            Matrix matrix2 = this.mTempMatrix2;
                            InputWindowHandle inputWindowHandle2 = (InputWindowHandle) list2.get(size);
                            IBinder windowToken = inputWindowHandle2.getWindowToken();
                            Matrix matrix3 = windowToken != null ? (Matrix) ((HashMap) this.mWindowsTransformMatrixMap).get(windowToken) : null;
                            if (matrix3 == null) {
                                size--;
                            } else {
                                matrix2.set(matrix3);
                                float[] fArr = this.mTempFloat1;
                                Matrix matrix4 = this.mTempMatrix1;
                                matrix4.reset();
                                float f = magnificationSpec2.scale;
                                matrix4.postScale(f, f);
                                matrix4.postTranslate(magnificationSpec2.offsetX, magnificationSpec2.offsetY);
                                Matrix matrix5 = new Matrix(inputWindowHandle2.transform);
                                matrix5.preConcat(matrix4);
                                matrix5.preConcat(matrix2);
                                matrix5.getValues(fArr);
                                float[] fArr2 = this.mTempFloat2;
                                Matrix matrix6 = this.mTempMatrix1;
                                matrix6.reset();
                                float f2 = magnificationSpec4.scale;
                                matrix6.postScale(f2, f2);
                                matrix6.postTranslate(magnificationSpec4.offsetX, magnificationSpec4.offsetY);
                                Matrix matrix7 = new Matrix(inputWindowHandle2.transform);
                                matrix7.preConcat(matrix6);
                                matrix7.preConcat(matrix2);
                                matrix7.getValues(fArr2);
                                Matrix matrix8 = new Matrix();
                                float[] fArr3 = this.mTempFloat3;
                                Matrix.IDENTITY_MATRIX.getValues(fArr3);
                                float abs = Math.abs(fArr3[0] - fArr[0]);
                                float abs2 = Math.abs(fArr3[0] - fArr2[0]);
                                float abs3 = Math.abs(fArr3[2] - fArr[2]);
                                float abs4 = Math.abs(fArr3[2] - fArr2[2]);
                                float abs5 = abs3 + Math.abs(fArr3[5] - fArr[5]);
                                float abs6 = abs4 + Math.abs(fArr3[5] - fArr2[5]);
                                if (Float.compare(abs2, abs) > 0 || (Float.compare(abs2, abs) == 0 && Float.compare(abs6, abs5) > 0)) {
                                    generateInverseMatrix(magnificationSpec2, matrix8);
                                    this.mPreviousMagnificationSpec.remove(inputWindowHandle2.displayId);
                                    if (magnificationSpec2.isNop()) {
                                        this.mCurrentMagnificationSpec.remove(inputWindowHandle2.displayId);
                                        this.mMagnificationSpecInverseMatrix.remove(inputWindowHandle2.displayId);
                                    }
                                } else {
                                    generateInverseMatrix(magnificationSpec4, matrix8);
                                }
                                this.mMagnificationSpecInverseMatrix.put(inputWindowHandle2.displayId, matrix8);
                            }
                        }
                    }
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        SparseArray sparseArray2 = this.mInputWindowHandlesOnDisplays;
        int i3 = 0;
        while (i3 < sparseArray.size()) {
            int keyAt2 = sparseArray.keyAt(i3);
            List list3 = (List) sparseArray.get(keyAt2);
            List list4 = (List) sparseArray2.get(keyAt2);
            if (list4 != null && list4.size() == list3.size()) {
                int size2 = list3.size();
                int i4 = i;
                while (i4 < size2) {
                    IBinder windowToken2 = ((InputWindowHandle) list3.get(i4)).getWindowToken();
                    IBinder windowToken3 = ((InputWindowHandle) list4.get(i4)).getWindowToken();
                    int i5 = windowToken2 != null ? 1 : i;
                    if (windowToken3 != null) {
                        i = 1;
                    }
                    if (i5 == i && (i5 == 0 || i == 0 || windowToken2.equals(windowToken3))) {
                        i4++;
                        i = 0;
                    }
                }
                i3++;
                i = 0;
            }
            arrayList.add(Integer.valueOf(keyAt2));
            i3++;
            i = 0;
        }
        this.mInputWindowHandlesOnDisplays.clear();
        for (int i6 = 0; i6 < sparseArray.size(); i6++) {
            int keyAt3 = sparseArray.keyAt(i6);
            this.mInputWindowHandlesOnDisplays.put(keyAt3, (List) sparseArray.get(keyAt3));
        }
        if (arrayList.isEmpty()) {
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessageDelayed(2, 35L);
        } else {
            if (this.mHandler.hasMessages(1)) {
                return;
            }
            this.mHandler.obtainMessage(1, arrayList).sendToTarget();
        }
    }

    public final void populateVisibleWindowsOnScreenLocked(int i, List list) {
        WindowInfo obtain;
        InsetsSourceProvider controllableInsetProvider;
        Task task;
        IWindow iWindow;
        final Matrix matrix = new Matrix();
        final Matrix matrix2 = new Matrix();
        synchronized (this.mLock) {
            try {
                List<InputWindowHandle> list2 = (List) this.mInputWindowHandlesOnDisplays.get(i);
                if (list2 == null) {
                    ((ArrayList) list).clear();
                    return;
                }
                matrix.set((Matrix) this.mMagnificationSpecInverseMatrix.get(i));
                WindowInfosListener.DisplayInfo displayInfo = (WindowInfosListener.DisplayInfo) this.mDisplayInfos.get(i);
                if (displayInfo != null) {
                    matrix2.set(displayInfo.mTransform);
                } else {
                    Slog.w("AccessibilityWindowsPopulator", "The displayInfo of this displayId (" + i + ") called back from the surface fligner is null");
                }
                ShellRoot shellRoot = (ShellRoot) this.mService.mRoot.getDisplayContent(i).mShellRoots.get(1);
                IBinder asBinder = (shellRoot == null || (iWindow = shellRoot.mAccessibilityWindow) == null) ? null : iWindow.asBinder();
                for (InputWindowHandle inputWindowHandle : list2) {
                    WindowManagerService windowManagerService = this.mService;
                    IBinder windowToken = inputWindowHandle.getWindowToken();
                    WindowState windowState = windowToken != null ? (WindowState) windowManagerService.mWindowMap.get(windowToken) : null;
                    AccessibilityWindow accessibilityWindow = new AccessibilityWindow();
                    accessibilityWindow.mWindow = windowToken;
                    accessibilityWindow.mDisplayId = inputWindowHandle.displayId;
                    accessibilityWindow.mInputConfig = inputWindowHandle.inputConfig;
                    accessibilityWindow.mType = inputWindowHandle.layoutParamsType;
                    accessibilityWindow.mIsPIPMenu = windowToken != null && windowToken.equals(asBinder);
                    accessibilityWindow.mPrivateFlags = windowState != null ? windowState.mAttrs.privateFlags : 0;
                    accessibilityWindow.mIsFocused = windowState != null && windowState.isFocused();
                    accessibilityWindow.mShouldMagnify = windowState == null || windowState.shouldMagnify();
                    RecentsAnimationController recentsAnimationController = windowManagerService.mRecentsAnimationController;
                    accessibilityWindow.mIgnoreDuetoRecentsAnimation = (windowState == null || recentsAnimationController == null || (task = windowState.getTask()) == null || !recentsAnimationController.isAnimatingTask(task) || recentsAnimationController.isTargetApp(windowState.mActivityRecord)) ? false : true;
                    Rect rect = new Rect(inputWindowHandle.frame);
                    boolean z = accessibilityWindow.mShouldMagnify;
                    Region region = inputWindowHandle.touchableRegion;
                    final Region region2 = accessibilityWindow.mTouchableRegionInWindow;
                    Region region3 = new Region();
                    region3.set(region);
                    region3.op(rect, Region.Op.INTERSECT);
                    if ((!z || matrix.isIdentity()) && matrix2.isIdentity()) {
                        region2.set(region3);
                    } else {
                        RegionUtils.forEachRect(region3, new Consumer() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$AccessibilityWindow$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Matrix matrix3 = matrix2;
                                Matrix matrix4 = matrix;
                                Region region4 = region2;
                                RectF rectF = new RectF((Rect) obj);
                                matrix3.mapRect(rectF);
                                matrix4.mapRect(rectF);
                                region4.union(new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
                            }
                        });
                    }
                    boolean z2 = accessibilityWindow.mShouldMagnify;
                    Region region4 = inputWindowHandle.touchableRegion;
                    final Region region5 = accessibilityWindow.mTouchableRegionInScreen;
                    if ((!z2 || matrix.isIdentity()) && matrix2.isIdentity()) {
                        region5.set(region4);
                    } else {
                        RegionUtils.forEachRect(region4, new Consumer() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$AccessibilityWindow$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Matrix matrix3 = matrix2;
                                Matrix matrix4 = matrix;
                                Region region42 = region5;
                                RectF rectF = new RectF((Rect) obj);
                                matrix3.mapRect(rectF);
                                matrix4.mapRect(rectF);
                                region42.union(new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
                            }
                        });
                    }
                    if (windowState != null) {
                        obtain = WindowInfo.obtain();
                        obtain.displayId = windowState.getDisplayId();
                        obtain.type = windowState.mAttrs.type;
                        obtain.layer = 0;
                        obtain.token = windowState.mClient.asBinder();
                        ActivityRecord activityRecord = windowState.mActivityRecord;
                        if (activityRecord != null) {
                            obtain.activityToken = activityRecord.token;
                        }
                        obtain.accessibilityIdOfAnchor = windowState.mAttrs.accessibilityIdOfAnchor;
                        obtain.focused = windowState.isFocused();
                        Task task2 = windowState.getTask();
                        obtain.inPictureInPicture = task2 != null && task2.inPinnedWindowingMode();
                        obtain.taskId = task2 == null ? -1 : task2.mTaskId;
                        obtain.hasFlagWatchOutsideTouch = (windowState.mAttrs.flags & 262144) != 0;
                        if (windowState.mIsChildWindow) {
                            obtain.parentToken = windowState.getParentWindow().mClient.asBinder();
                        }
                        int size = windowState.mChildren.size();
                        if (size > 0) {
                            if (obtain.childTokens == null) {
                                obtain.childTokens = new ArrayList(size);
                            }
                            for (int i2 = 0; i2 < size; i2++) {
                                obtain.childTokens.add(((WindowState) windowState.mChildren.get(i2)).mClient.asBinder());
                            }
                        }
                    } else {
                        obtain = WindowInfo.obtain();
                        obtain.displayId = accessibilityWindow.mDisplayId;
                        obtain.type = accessibilityWindow.mType;
                        obtain.token = accessibilityWindow.mWindow;
                        obtain.hasFlagWatchOutsideTouch = (accessibilityWindow.mInputConfig & 512) != 0;
                        obtain.inPictureInPicture = accessibilityWindow.mIsPIPMenu;
                    }
                    accessibilityWindow.mWindowInfo = obtain;
                    Matrix matrix3 = new Matrix();
                    inputWindowHandle.transform.invert(matrix3);
                    matrix3.postConcat(matrix2);
                    matrix3.getValues(accessibilityWindow.mWindowInfo.mTransformMatrix);
                    Matrix matrix4 = new Matrix();
                    if (accessibilityWindow.mShouldMagnify && !matrix.isIdentity()) {
                        if (matrix.invert(matrix4)) {
                            float[] fArr = sTempFloats;
                            matrix4.getValues(fArr);
                            MagnificationSpec magnificationSpec = accessibilityWindow.mWindowInfo.mMagnificationSpec;
                            magnificationSpec.scale = fArr[0];
                            magnificationSpec.offsetX = fArr[2];
                            magnificationSpec.offsetY = fArr[5];
                        } else {
                            Slog.w("AccessibilityWindowsPopulator", "can't find spec");
                        }
                    }
                    Flags.computeWindowChangesOnA11yV2();
                    if (windowState != null) {
                        if ((accessibilityWindow.mType == 2019 ? accessibilityWindow.mTouchableRegionInScreen.isEmpty() : false) && (controllableInsetProvider = windowState.getControllableInsetProvider()) != null) {
                            accessibilityWindow.mSystemBarInsetFrame = controllableInsetProvider.mSource.getFrame();
                        }
                    }
                    ((ArrayList) list).add(accessibilityWindow);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void releaseResources() {
        this.mInputWindowHandlesOnDisplays.clear();
        this.mMagnificationSpecInverseMatrix.clear();
        ((ArrayList) this.mVisibleWindows).clear();
        this.mDisplayInfos.clear();
        this.mCurrentMagnificationSpec.clear();
        this.mPreviousMagnificationSpec.clear();
        ((HashMap) this.mWindowsTransformMatrixMap).clear();
        this.mWindowsNotificationEnabled = false;
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public final void setWindowsNotification(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mWindowsNotificationEnabled == z) {
                    return;
                }
                this.mWindowsNotificationEnabled = z;
                if (z) {
                    Pair register = register();
                    onWindowInfosChangedInternal((InputWindowHandle[]) register.first, (WindowInfosListener.DisplayInfo[]) register.second);
                } else {
                    unregister();
                    releaseResources();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
