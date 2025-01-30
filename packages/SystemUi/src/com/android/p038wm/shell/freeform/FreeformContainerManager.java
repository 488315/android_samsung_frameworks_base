package com.android.p038wm.shell.freeform;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.freeform.FreeformContainerFolderView;
import com.android.systemui.R;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformContainerManager {
    public static FreeformContainerManager sFreeformContainerManager;
    public Configuration mConfiguration;
    public final Context mContext;
    public final IntentFilter mFreeformContainerFilter;
    public final C39822 mFreeformContainerReceiver;

    /* renamed from: mH */
    public final HandlerC3983H f447mH;
    public int mRotation;
    public final C39811 mRotationWatcher = new IRotationWatcher.Stub() { // from class: com.android.wm.shell.freeform.FreeformContainerManager.1
        public final void onRotationChanged(int i) {
            Log.i("FreeformContainer", "[Manager] onRotationChanged: " + Surface.rotationToString(i));
            HandlerC3983H handlerC3983H = FreeformContainerManager.this.f447mH;
            handlerC3983H.sendMessage(handlerC3983H.obtainMessage(34, i, 0));
        }
    };
    public final HandlerThread mThread;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.freeform.FreeformContainerManager$H */
    public final class HandlerC3983H extends Handler {
        public final IWindowManager mIWindowManager;
        public boolean mIsBindingMinimizeContainerService;
        public boolean mIsBindingSmartPopupViewService;
        public final FreeformContainerItemController mItemController;
        public final FreeformContainerViewController mViewController;

        public /* synthetic */ HandlerC3983H(FreeformContainerManager freeformContainerManager, Looper looper, int i) {
            this(looper);
        }

        public static String messageToString(int i) {
            switch (i) {
                case 11:
                    return "MINIMIZE_CONTAINER_SERVICE_BIND";
                case 12:
                    return "MINIMIZE_CONTAINER_SERVICE_UNBIND";
                case 13:
                    return "MINIMIZE_CONTAINER_ADD_ITEM";
                case 14:
                    return "MINIMIZE_CONTAINER_REMOVE_ITEM";
                case 15:
                    return "MINIMIZE_CONTAINER_ANIM_COMPLETED";
                case 16:
                    return "MINIMIZE_CONTAINER_MINIMIZE_TIMEOUT";
                case 17:
                    return "MINIMIZE_CONTAINER_REMOVE_ALL_ITEM";
                case 18:
                case 19:
                case 20:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 38:
                case 39:
                default:
                    return "UNKNOWN";
                case 21:
                    return "SMART_POPUP_VIEW_SERVICE_BIND";
                case 22:
                    return "SMART_POPUP_VIEW_SERVICE_UNBIND";
                case 23:
                    return "SMART_POPUP_VIEW_ADD_ITEM";
                case 24:
                    return "SMART_POPUP_VIEW_REMOVE_ITEM";
                case 30:
                    return "FREEFORM_CONTAINER_LAUNCH_ITEM";
                case 31:
                    return "FREEFORM_CONTAINER_LOAD_ICON_COMPLETED";
                case 32:
                    return "FREEFORM_CONTAINER_USER_SWITCH";
                case 33:
                    return "FREEFORM_CONTAINER_REBUILD_ALL";
                case 34:
                    return "FREEFORM_CONTAINER_ROTATION_CHANGED";
                case 35:
                    return "FREEFORM_CONTAINER_CLOSE_FULLSCREEN_MODE";
                case 36:
                    return "FREEFORM_CONTAINER_CONFIGURATION_CHANGED";
                case 37:
                    return "FREEFORM_CONTAINER_SET_POINTER_POSITION";
                case 40:
                    return "TASK_MOVE_STARTED";
                case 41:
                    return "TASK_MOVE_ENDED";
                case 42:
                    return "MINIMIZE_CONTAINER_TRAY_COLLAPSE";
            }
        }

        public final void destroy() {
            removeCallbacksAndMessages(null);
            FreeformContainerViewController freeformContainerViewController = this.mViewController;
            if (freeformContainerViewController.mContainerView != null) {
                Log.i("FreeformContainer", "[ViewController] destroy");
                Iterator it = ((ArrayList) freeformContainerViewController.mCallBacks).iterator();
                while (it.hasNext()) {
                    FreeformContainerCallback freeformContainerCallback = (FreeformContainerCallback) it.next();
                    Log.i("FreeformContainer", "[ViewController] onViewDestroyed: " + freeformContainerCallback);
                    freeformContainerCallback.onViewDestroyed();
                }
                freeformContainerViewController.mWindowManager.removeViewImmediate(freeformContainerViewController.mContainerView);
                freeformContainerViewController.mContainerView = null;
            }
            synchronized (freeformContainerViewController.mCallBacks) {
                ((ArrayList) freeformContainerViewController.mCallBacks).clear();
            }
            FreeformContainerItemController freeformContainerItemController = this.mItemController;
            freeformContainerItemController.mThreadPoolExecutor.shutdownNow();
            freeformContainerItemController.mThreadPoolExecutor = null;
            freeformContainerItemController.mItemList.clear();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if ((CoreRune.MW_FREEFORM_SMART_POPUP_VIEW && (i == 21 || this.mIsBindingSmartPopupViewService)) || i == 11 || this.mIsBindingMinimizeContainerService) {
                Object obj = message.obj;
                FreeformContainerItem freeformContainerItem = obj instanceof FreeformContainerItem ? (FreeformContainerItem) obj : null;
                StringBuilder sb = new StringBuilder("[Manager] handleMessage: ");
                sb.append(messageToString(message.what));
                sb.append(freeformContainerItem == null ? "" : " item=" + freeformContainerItem);
                Log.i("FreeformContainer", sb.toString());
                int i2 = message.what;
                if (i2 == 42) {
                    FreeformContainerViewController freeformContainerViewController = this.mViewController;
                    if (freeformContainerViewController.mState == 2) {
                        freeformContainerViewController.updateContainerState(1, false, true);
                        return;
                    }
                    return;
                }
                switch (i2) {
                    case 11:
                        if (noRunningService()) {
                            init();
                            registerReceivers();
                        }
                        this.mIsBindingMinimizeContainerService = true;
                        return;
                    case 12:
                        this.mIsBindingMinimizeContainerService = false;
                        if (!noRunningService()) {
                            this.mItemController.removeAllMinimizeContainerItem();
                            return;
                        }
                        destroy();
                        try {
                            this.mIWindowManager.removeRotationWatcher(FreeformContainerManager.this.mRotationWatcher);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        FreeformContainerManager freeformContainerManager = FreeformContainerManager.this;
                        freeformContainerManager.mContext.unregisterReceiver(freeformContainerManager.mFreeformContainerReceiver);
                        return;
                    case 13:
                        if (freeformContainerItem != null) {
                            sendMessageDelayed(obtainMessage(16, freeformContainerItem.getTaskId(), 0, freeformContainerItem), 3000L);
                            this.mItemController.addItem(freeformContainerItem);
                            return;
                        }
                        return;
                    case 14:
                        FreeformContainerItem itemById = this.mItemController.getItemById(message.arg1);
                        if (itemById != null) {
                            this.mItemController.removeItem(itemById);
                            return;
                        }
                        Log.w("FreeformContainer", "[Manager] " + messageToString(message.what) + " failed, due to no taskId: " + message.arg1);
                        return;
                    case 15:
                    case 16:
                        FreeformContainerItem itemById2 = this.mItemController.getItemById(message.arg1);
                        if (itemById2 != null) {
                            this.mItemController.animationCompleted(itemById2);
                            return;
                        }
                        Log.w("FreeformContainer", "[Manager] " + messageToString(message.what) + " failed, due to no taskId: " + message.arg1);
                        return;
                    case 17:
                        this.mItemController.removeAllMinimizeContainerItem();
                        return;
                    default:
                        switch (i2) {
                            case 21:
                                if (noRunningService()) {
                                    init();
                                    registerReceivers();
                                }
                                this.mIsBindingSmartPopupViewService = true;
                                return;
                            case 22:
                                this.mIsBindingSmartPopupViewService = false;
                                if (!noRunningService()) {
                                    this.mItemController.removeAllSmartPopupViewItem();
                                    return;
                                }
                                destroy();
                                try {
                                    this.mIWindowManager.removeRotationWatcher(FreeformContainerManager.this.mRotationWatcher);
                                } catch (RemoteException e2) {
                                    e2.printStackTrace();
                                }
                                FreeformContainerManager freeformContainerManager2 = FreeformContainerManager.this;
                                freeformContainerManager2.mContext.unregisterReceiver(freeformContainerManager2.mFreeformContainerReceiver);
                                return;
                            case 23:
                                if (!this.mViewController.isPointerView()) {
                                    this.mViewController.updateContainerState(1, true, true);
                                }
                                if (freeformContainerItem != null) {
                                    this.mItemController.addItem(freeformContainerItem);
                                    return;
                                }
                                return;
                            case 24:
                                FreeformContainerItem itemByName = this.mItemController.getItemByName((String) message.obj);
                                if (itemByName instanceof SmartPopupViewItem) {
                                    this.mItemController.removeItem(itemByName);
                                    return;
                                }
                                Log.w("FreeformContainer", "[Manager] " + messageToString(message.what) + " failed, due to no smart popup view item which has packageName: " + message.obj);
                                return;
                            case 25:
                                if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                                    this.mItemController.removeAllSmartPopupViewItem();
                                    return;
                                }
                                return;
                            default:
                                switch (i2) {
                                    case 30:
                                        if (freeformContainerItem != null) {
                                            this.mItemController.removeItem(freeformContainerItem);
                                            freeformContainerItem.launch();
                                            return;
                                        }
                                        return;
                                    case 31:
                                        if (freeformContainerItem != null) {
                                            FreeformContainerItemController freeformContainerItemController = this.mItemController;
                                            freeformContainerItemController.getClass();
                                            Log.i("FreeformContainer", "[ItemController] iconLoadCompleted: item=" + freeformContainerItem);
                                            if (!freeformContainerItemController.mItemList.contains(freeformContainerItem)) {
                                                Log.w("FreeformContainer", "[ItemController] iconLoadCompleted failed item(=" + freeformContainerItem + ") is not in list");
                                                return;
                                            }
                                            if (!freeformContainerItem.mIconLoadCompleted) {
                                                freeformContainerItem.mIconLoadCompleted = true;
                                            }
                                            freeformContainerItemController.publishItemIfNeeded(freeformContainerItem);
                                            return;
                                        }
                                        return;
                                    case 32:
                                        destroy();
                                        init();
                                        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                                            this.mItemController.removeAllSmartPopupViewItem();
                                        }
                                        FreeformContainerItemController freeformContainerItemController2 = this.mItemController;
                                        Context context = FreeformContainerManager.this.mContext;
                                        freeformContainerItemController2.mItemList.clear();
                                        List<ActivityManager.RunningTaskInfo> minimizedFreeformTasksForCurrentUser = MultiWindowManager.getInstance().getMinimizedFreeformTasksForCurrentUser();
                                        if (minimizedFreeformTasksForCurrentUser == null || minimizedFreeformTasksForCurrentUser.isEmpty()) {
                                            return;
                                        }
                                        for (ActivityManager.RunningTaskInfo runningTaskInfo : minimizedFreeformTasksForCurrentUser) {
                                            try {
                                                ActivityInfo activityInfo = AppGlobals.getPackageManager().getActivityInfo(runningTaskInfo.realActivity, 128L, runningTaskInfo.userId);
                                                if (activityInfo != null) {
                                                    freeformContainerItemController2.addItem(new MinimizeContainerItem(context, activityInfo.packageName, runningTaskInfo.realActivity, runningTaskInfo.taskId, runningTaskInfo.userId, true));
                                                }
                                            } catch (RemoteException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        return;
                                    case 33:
                                        FreeformContainerItemController freeformContainerItemController3 = this.mItemController;
                                        freeformContainerItemController3.getClass();
                                        ArrayList arrayList = new ArrayList(freeformContainerItemController3.mItemList);
                                        arrayList.forEach(new FreeformContainerItemController$$ExternalSyntheticLambda0());
                                        destroy();
                                        init();
                                        FreeformContainerItemController freeformContainerItemController4 = this.mItemController;
                                        synchronized (freeformContainerItemController4.mItemList) {
                                            int size = arrayList.size();
                                            while (true) {
                                                size--;
                                                if (size >= 0) {
                                                    freeformContainerItemController4.addItem((FreeformContainerItem) arrayList.get(size));
                                                }
                                            }
                                        }
                                        return;
                                    case 34:
                                        int i3 = message.arg1;
                                        if (FreeformContainerManager.this.mRotation != i3) {
                                            this.mViewController.closeFullscreenMode("fullscreen_mode_request_screen_rotating");
                                            removeMessages(35, "fullscreen_mode_request_screen_rotating");
                                            if (this.mViewController.openFullscreenMode("fullscreen_mode_request_screen_rotating")) {
                                                sendMessageDelayed(obtainMessage(35, "fullscreen_mode_request_screen_rotating"), 2000L);
                                            }
                                            FreeformContainerItemController freeformContainerItemController5 = this.mItemController;
                                            freeformContainerItemController5.getClass();
                                            Iterator it = new ArrayList(freeformContainerItemController5.mItemList).iterator();
                                            while (it.hasNext()) {
                                                freeformContainerItemController5.animationCompleted((FreeformContainerItem) it.next());
                                            }
                                            this.mViewController.updateDisplayFrame(false);
                                            FreeformContainerViewController freeformContainerViewController2 = this.mViewController;
                                            int i4 = FreeformContainerManager.this.mRotation;
                                            freeformContainerViewController2.createOrUpdateDismissButton();
                                            Iterator it2 = ((ArrayList) freeformContainerViewController2.mCallBacks).iterator();
                                            while (it2.hasNext()) {
                                                FreeformContainerCallback freeformContainerCallback = (FreeformContainerCallback) it2.next();
                                                Log.i("FreeformContainer", "[ViewController] onRotationChanged: " + freeformContainerCallback);
                                                freeformContainerCallback.onRotationChanged(i4, i3, freeformContainerViewController2.mDisplayFrame);
                                            }
                                            FreeformContainerManager.this.mRotation = i3;
                                            return;
                                        }
                                        return;
                                    case 35:
                                        Object obj2 = message.obj;
                                        if (obj2 instanceof String) {
                                            if (this.mViewController.closeFullscreenMode((String) obj2)) {
                                                this.mViewController.mContainerView.requestLayout();
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    case 36:
                                        if (this.mViewController.isPointerView()) {
                                            this.mViewController.updateDisplayFrame(true);
                                            this.mViewController.mContainerView.updatePointerViewImmediately();
                                            return;
                                        } else {
                                            this.mViewController.updateDisplayFrame(true);
                                            this.mViewController.updateContainerState(1, false, true);
                                            return;
                                        }
                                    case 37:
                                        Point point = (Point) message.obj;
                                        boolean z = message.arg1 == 1;
                                        FreeformContainerViewController freeformContainerViewController3 = this.mViewController;
                                        freeformContainerViewController3.mContainerView.setPointerPosition(point.x, point.y, z);
                                        freeformContainerViewController3.mContainerView.mNeedInitPosition = false;
                                        return;
                                    default:
                                        return;
                                }
                        }
                }
            }
        }

        public final void init() {
            FreeformContainerViewController freeformContainerViewController = this.mViewController;
            FreeformContainerItemController freeformContainerItemController = this.mItemController;
            freeformContainerViewController.getClass();
            Log.i("FreeformContainer", "[ViewController] init");
            freeformContainerViewController.f449mH = this;
            freeformContainerViewController.mItemController = freeformContainerItemController;
            int i = 0;
            freeformContainerViewController.mState = 0;
            ((ArrayList) freeformContainerViewController.mFullscreenModeRequests).clear();
            WindowManager.LayoutParams layoutParams = freeformContainerViewController.mLayoutParams;
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.type = 2604;
            layoutParams.flags = 25166632;
            layoutParams.format = -2;
            layoutParams.setTitle("FreeformContainer");
            layoutParams.privateFlags |= 16;
            layoutParams.samsungFlags |= 131072;
            layoutParams.layoutInDisplayCutoutMode = 1;
            layoutParams.gravity = 17;
            layoutParams.windowAnimations = R.style.MinimizeContainer_WindowAnimation;
            freeformContainerViewController.updateDisplayFrame(false);
            FreeformContainerView freeformContainerView = (FreeformContainerView) freeformContainerViewController.mLayoutInflater.inflate(R.layout.freeform_container_layout, (ViewGroup) null);
            freeformContainerViewController.mContainerView = freeformContainerView;
            freeformContainerViewController.mFolderView = (FreeformContainerFolderView) freeformContainerView.findViewById(R.id.freeform_container_recycler_view);
            final FreeformContainerView freeformContainerView2 = freeformContainerViewController.mContainerView;
            HandlerC3983H handlerC3983H = freeformContainerViewController.f449mH;
            freeformContainerView2.mViewController = freeformContainerViewController;
            Log.i("FreeformContainer", "[ViewController] registerCallback: " + freeformContainerView2);
            List list = freeformContainerViewController.mCallBacks;
            ((ArrayList) list).add(freeformContainerView2);
            freeformContainerView2.f448mH = handlerC3983H;
            ViewTreeObserver viewTreeObserver = freeformContainerView2.getRootView().getViewTreeObserver();
            viewTreeObserver.addOnComputeInternalInsetsListener(freeformContainerView2.mInsetsComputer);
            viewTreeObserver.addOnDrawListener(freeformContainerView2.mSystemGestureExcludeUpdater);
            freeformContainerView2.mDefaultGapTop = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_pointer_default_gap_top);
            freeformContainerView2.mThresholdToMove = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_move_interval);
            freeformContainerView2.mPointerSettleDownGap = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_pointer_settle_down_gap);
            freeformContainerView2.mIconLeftMarginInFolder = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_left);
            freeformContainerView2.mIconItemTopMarginInFolder = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_top);
            freeformContainerView2.mAnimElevation = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_elevation) + 1;
            freeformContainerView2.mPointerViewSize = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_outer_size);
            freeformContainerView2.mBackgroundDimView = (FrameLayout) freeformContainerView2.findViewById(R.id.freeform_container_dim_view);
            freeformContainerView2.mPointerGroupView = (ViewGroup) freeformContainerView2.findViewById(R.id.freeform_container_pointer_group_view);
            ImageButton imageButton = (ImageButton) freeformContainerView2.findViewById(R.id.freeform_container_pointer_control_view);
            freeformContainerView2.mPointerView = imageButton;
            imageButton.setColorFilter(0);
            freeformContainerView2.mPointerView.setHapticFeedbackEnabled(false);
            freeformContainerView2.mPointerView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda4
                /* JADX WARN: Removed duplicated region for block: B:53:0x019a  */
                /* JADX WARN: Removed duplicated region for block: B:55:0x01a5  */
                /* JADX WARN: Removed duplicated region for block: B:58:0x01b4  */
                /* JADX WARN: Removed duplicated region for block: B:60:0x01be  */
                /* JADX WARN: Removed duplicated region for block: B:78:0x01b6  */
                /* JADX WARN: Removed duplicated region for block: B:79:0x01ad  */
                /* JADX WARN: Removed duplicated region for block: B:80:0x01a2  */
                @Override // android.view.View.OnTouchListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    boolean z;
                    final boolean z2;
                    final FreeformContainerView freeformContainerView3 = FreeformContainerView.this;
                    float[] fArr = FreeformContainerView.TAIL_ICON_ALPHA_ARRAY;
                    freeformContainerView3.getClass();
                    float rawX = motionEvent.getRawX();
                    float rawY = motionEvent.getRawY();
                    int action = motionEvent.getAction();
                    if (action != 0) {
                        if (action == 1) {
                            StringBuilder sb = new StringBuilder("[ContainerView] onTouch(");
                            sb.append(MotionEvent.actionToString(action));
                            sb.append(") mIsAppIconMoving=");
                            NotificationListener$$ExternalSyntheticOutline0.m123m(sb, freeformContainerView3.mIsAppIconMoving, "FreeformContainer");
                            if (freeformContainerView3.mIsAppIconMoving) {
                                freeformContainerView3.addMovementToVelocityTracker(motionEvent);
                                VelocityTracker velocityTracker = freeformContainerView3.mVelocityTracker;
                                if (velocityTracker != null) {
                                    Rect rect = freeformContainerView3.mViewController.mNonDecorDisplayFrame;
                                    velocityTracker.computeCurrentVelocity(1000, freeformContainerView3.mMaximumFlingVelocity);
                                    freeformContainerView3.mVelocity.set(freeformContainerView3.mVelocityTracker.getXVelocity(), freeformContainerView3.mVelocityTracker.getYVelocity());
                                    if (Math.abs(freeformContainerView3.mVelocity.x) > freeformContainerView3.mMinimumFlingVelocity || Math.abs(freeformContainerView3.mVelocity.y) > freeformContainerView3.mMinimumFlingVelocity) {
                                        float f = freeformContainerView3.mVelocity.x;
                                        boolean z3 = f < 0.0f;
                                        if ((!z3 || freeformContainerView3.mFirstPointerX >= rect.left) && ((z3 || freeformContainerView3.mFirstPointerX + freeformContainerView3.mPointerViewSize <= rect.right) && (Math.abs(f) >= 700.0f || ((!z3 || freeformContainerView3.mPointerView.getX() >= rect.left - 30) && (z3 || freeformContainerView3.mPointerView.getX() + freeformContainerView3.mPointerViewSize <= rect.right + 30))))) {
                                            z = true;
                                            int i2 = !z ? (int) ((freeformContainerView3.mVelocity.x / 700.0f) * 35.0f) : 0;
                                            int i3 = !z ? (int) ((freeformContainerView3.mVelocity.y / 700.0f) * 35.0f) : 0;
                                            FreeformContainerDismissButtonView freeformContainerDismissButtonView = freeformContainerView3.mViewController.mDismissButtonView;
                                            z2 = freeformContainerDismissButtonView != null ? false : freeformContainerDismissButtonView.mDismissButtonManager.mView.mIsEnterDismissButton;
                                            if (!z2) {
                                                float x = freeformContainerView3.mPointerView.getX() + i2;
                                                float y = freeformContainerView3.mPointerView.getY() + i3;
                                                if (z) {
                                                    Rect rect2 = freeformContainerView3.mViewController.mNonDecorDisplayFrame;
                                                    boolean z4 = ((float) rect2.left) <= x && ((float) rect2.right) >= x;
                                                    if (rect2.top > y || rect2.bottom < y) {
                                                        z4 = false;
                                                    }
                                                    freeformContainerView3.updateSpringConfig(z4 ? 20 : 10);
                                                }
                                                freeformContainerView3.setPointerPosition(x, y, false);
                                            }
                                            freeformContainerView3.updateSpringChainEndValue();
                                            freeformContainerView3.getParent().requestTransparentRegion(freeformContainerView3);
                                            freeformContainerView3.f448mH.post(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda5
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    FreeformContainerView freeformContainerView4 = FreeformContainerView.this;
                                                    boolean z5 = z2;
                                                    freeformContainerView4.mIsAppIconMoving = false;
                                                    freeformContainerView4.getPointerViewBounds(freeformContainerView4.mTmpBounds);
                                                    freeformContainerView4.mViewController.hideDismissButtonAndDismissIcon(null, freeformContainerView4.mPointerGroupView, freeformContainerView4.mTmpBounds);
                                                    if (z5) {
                                                        freeformContainerView4.setPointerPosition(freeformContainerView4.mFirstPointerX, freeformContainerView4.mFirstPointerY, false);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                                z = false;
                                if (!z) {
                                }
                                if (!z) {
                                }
                                FreeformContainerDismissButtonView freeformContainerDismissButtonView2 = freeformContainerView3.mViewController.mDismissButtonView;
                                if (freeformContainerDismissButtonView2 != null) {
                                }
                                if (!z2) {
                                }
                                freeformContainerView3.updateSpringChainEndValue();
                                freeformContainerView3.getParent().requestTransparentRegion(freeformContainerView3);
                                freeformContainerView3.f448mH.post(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda5
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        FreeformContainerView freeformContainerView4 = FreeformContainerView.this;
                                        boolean z5 = z2;
                                        freeformContainerView4.mIsAppIconMoving = false;
                                        freeformContainerView4.getPointerViewBounds(freeformContainerView4.mTmpBounds);
                                        freeformContainerView4.mViewController.hideDismissButtonAndDismissIcon(null, freeformContainerView4.mPointerGroupView, freeformContainerView4.mTmpBounds);
                                        if (z5) {
                                            freeformContainerView4.setPointerPosition(freeformContainerView4.mFirstPointerX, freeformContainerView4.mFirstPointerY, false);
                                        }
                                    }
                                });
                            }
                            if (!freeformContainerView3.mViewController.isDismissButtonShowing() && !freeformContainerView3.mIsAppIconMoving && !freeformContainerView3.mViewController.mFolderView.mIsCollapseAnimating) {
                                if (freeformContainerView3.getIconViewListCount() == 1) {
                                    FreeformContainerItem freeformContainerItem = (FreeformContainerItem) freeformContainerView3.mViewController.mItemController.mItemList.get(0);
                                    if (freeformContainerItem != null) {
                                        freeformContainerView3.f448mH.sendMessage(30, freeformContainerItem);
                                    }
                                } else {
                                    freeformContainerView3.mViewController.updateContainerState(2, true, true);
                                }
                            }
                            Log.i("FreeformContainer", "[ContainerView] onTouch(" + MotionEvent.actionToString(action) + ")");
                        } else if (action != 2) {
                            if (action == 3) {
                                VelocityTracker velocityTracker2 = freeformContainerView3.mVelocityTracker;
                                if (velocityTracker2 != null) {
                                    velocityTracker2.recycle();
                                    freeformContainerView3.mVelocityTracker = null;
                                }
                                freeformContainerView3.updateSpringChainEndValue();
                                freeformContainerView3.setPointerPosition(freeformContainerView3.mPointerView.getX(), freeformContainerView3.mPointerView.getY(), false);
                                freeformContainerView3.f448mH.post(new FreeformContainerView$$ExternalSyntheticLambda0(freeformContainerView3, 1));
                            }
                        } else if (freeformContainerView3.mIsAppIconMoving) {
                            freeformContainerView3.addMovementToVelocityTracker(motionEvent);
                            view.setX((rawX - freeformContainerView3.mLastPositionX) + view.getX());
                            view.setY((rawY - freeformContainerView3.mLastPositionY) + view.getY());
                            freeformContainerView3.mLastPositionX = rawX;
                            freeformContainerView3.mLastPositionY = rawY;
                            freeformContainerView3.updateSpringChainEndValue();
                            freeformContainerView3.getPointerViewBounds(freeformContainerView3.mTmpBounds);
                            FreeformContainerViewController freeformContainerViewController2 = freeformContainerView3.mViewController;
                            Rect rect3 = freeformContainerView3.mTmpBounds;
                            FreeformContainerDismissButtonView freeformContainerDismissButtonView3 = freeformContainerViewController2.mDismissButtonView;
                            if (freeformContainerDismissButtonView3 != null) {
                                freeformContainerDismissButtonView3.mDismissButtonManager.mView.updateView(rect3);
                            }
                        } else if (((float) Math.hypot(rawX - freeformContainerView3.mFirstDownX, rawY - freeformContainerView3.mFirstDownY)) >= freeformContainerView3.mThresholdToMove) {
                            freeformContainerView3.mIsAppIconMoving = true;
                            freeformContainerView3.getPointerViewBounds(freeformContainerView3.mTmpBounds);
                            if (!freeformContainerView3.mViewController.isDismissButtonShowing()) {
                                FreeformContainerViewController freeformContainerViewController3 = freeformContainerView3.mViewController;
                                Rect rect4 = freeformContainerView3.mTmpBounds;
                                freeformContainerViewController3.createOrUpdateDismissButton();
                                freeformContainerViewController3.mDismissButtonView.show(rect4);
                            }
                            freeformContainerView3.mPointerSettleDownEffectRequested = true;
                            freeformContainerView3.updateSpringConfig(10);
                            Log.i("FreeformContainer", "[ContainerView] onTouch(" + MotionEvent.actionToString(action) + "): Ready to move");
                        }
                    } else {
                        VelocityTracker velocityTracker3 = freeformContainerView3.mVelocityTracker;
                        if (velocityTracker3 == null) {
                            freeformContainerView3.mVelocityTracker = VelocityTracker.obtain();
                        } else {
                            velocityTracker3.clear();
                        }
                        freeformContainerView3.addMovementToVelocityTracker(motionEvent);
                        freeformContainerView3.mIsAppIconMoving = false;
                        freeformContainerView3.mFirstDownX = rawX;
                        freeformContainerView3.mLastPositionX = rawX;
                        freeformContainerView3.mFirstDownY = rawY;
                        freeformContainerView3.mLastPositionY = rawY;
                        freeformContainerView3.mFirstPointerX = freeformContainerView3.mPointerView.getX();
                        freeformContainerView3.mFirstPointerY = freeformContainerView3.mPointerView.getY();
                        Log.i("FreeformContainer", "[ContainerView] onTouch(" + MotionEvent.actionToString(action) + ")");
                    }
                    return false;
                }
            });
            SharedPreferences sharedPreferences = freeformContainerView2.mContext.getSharedPreferences("freeform_container_pref", 0);
            if (sharedPreferences.contains("position_x") && sharedPreferences.contains("position_y")) {
                float width = (freeformContainerView2.mViewController.mDisplayFrame.width() * 0.8f) - (freeformContainerView2.mPointerView.getWidth() / 2.0f);
                float f = freeformContainerView2.mViewController.mNonDecorDisplayFrame.top + freeformContainerView2.mDefaultGapTop;
                float f2 = sharedPreferences.getFloat("position_x", width);
                float f3 = sharedPreferences.getFloat("position_y", f);
                Rect rect = freeformContainerView2.mTmpBounds;
                int i2 = (int) f2;
                int i3 = (int) f3;
                int i4 = freeformContainerView2.mPointerViewSize;
                rect.set(i2, i3, i2 + i4, i4 + i3);
                int rotation = freeformContainerView2.mContext.getDisplay().getRotation();
                int i5 = sharedPreferences.getInt("rotation", rotation);
                if (rotation != i5) {
                    FreeformContainerView.rotateBounds(freeformContainerView2.mViewController.mDisplayFrame, freeformContainerView2.mTmpBounds, i5, rotation);
                }
                StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("[ContainerView] loadPositionFromSharedPreferences, position=(", f2, ",", f3, ") default=(");
                m88m.append(width);
                m88m.append(",");
                m88m.append(f);
                m88m.append(")");
                Log.i("FreeformContainer", m88m.toString());
                Rect rect2 = freeformContainerView2.mTmpBounds;
                freeformContainerView2.setPointerPosition(rect2.left, rect2.top, false);
                i = 0;
            } else {
                Log.i("FreeformContainer", "[ContainerView] loadPositionFromSharedPreferences, need to init position");
                freeformContainerView2.mNeedInitPosition = true;
            }
            freeformContainerView2.setLayoutDirection(i);
            freeformContainerView2.setVisibility(8);
            freeformContainerView2.mMinimumFlingVelocity = ViewConfiguration.get(freeformContainerView2.mContext).getScaledMinimumFlingVelocity();
            freeformContainerView2.mMaximumFlingVelocity = ViewConfiguration.get(freeformContainerView2.mContext).getScaledMaximumFlingVelocity();
            final FreeformContainerFolderView freeformContainerFolderView = freeformContainerViewController.mFolderView;
            HandlerC3983H handlerC3983H2 = freeformContainerViewController.f449mH;
            freeformContainerFolderView.mViewController = freeformContainerViewController;
            Log.i("FreeformContainer", "[ViewController] registerCallback: " + freeformContainerFolderView);
            ((ArrayList) list).add(freeformContainerFolderView);
            freeformContainerFolderView.f445mH = handlerC3983H2;
            freeformContainerFolderView.mDraggingIconView = (ImageView) freeformContainerFolderView.getRootView().findViewById(R.id.freeform_container_folder_dragging_icon_view);
            freeformContainerFolderView.mTrayView = (FreeformContainerFolderTrayView) freeformContainerFolderView.getRootView().findViewById(R.id.freeform_container_tray_view);
            freeformContainerFolderView.mItemSize = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_item_size);
            freeformContainerFolderView.mPaddingLeft = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_position_padding_left);
            freeformContainerFolderView.mPaddingRight = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_position_padding_right);
            freeformContainerFolderView.mThresholdToMove = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_move_interval);
            freeformContainerFolderView.mEmptySlotIcon = freeformContainerFolderView.mContext.getResources().getDrawable(R.drawable.ic_mw_popupview_min_ic_empty_mtrl);
            final int i6 = 0;
            freeformContainerFolderView.mTrayView.mCloseButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i6) {
                        case 0:
                            FreeformContainerItemController freeformContainerItemController2 = freeformContainerFolderView.mViewController.mItemController;
                            freeformContainerItemController2.getClass();
                            Iterator it = new ArrayList(freeformContainerItemController2.mItemList).iterator();
                            while (it.hasNext()) {
                                ((FreeformContainerItem) it.next()).throwAway(freeformContainerItemController2);
                            }
                            break;
                        default:
                            FreeformContainerViewController freeformContainerViewController2 = freeformContainerFolderView.mViewController;
                            freeformContainerViewController2.mFolderView.collapse(false);
                            FreeformContainerItemController freeformContainerItemController3 = freeformContainerViewController2.mItemController;
                            freeformContainerItemController3.getClass();
                            Iterator it2 = new ArrayList(freeformContainerItemController3.mItemList).iterator();
                            while (it2.hasNext()) {
                                FreeformContainerItem freeformContainerItem = (FreeformContainerItem) it2.next();
                                freeformContainerItemController3.removeItem(freeformContainerItem);
                                freeformContainerItem.launch();
                            }
                            break;
                    }
                }
            });
            final int i7 = 1;
            freeformContainerFolderView.mTrayView.mOpenAllAppsButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i7) {
                        case 0:
                            FreeformContainerItemController freeformContainerItemController2 = freeformContainerFolderView.mViewController.mItemController;
                            freeformContainerItemController2.getClass();
                            Iterator it = new ArrayList(freeformContainerItemController2.mItemList).iterator();
                            while (it.hasNext()) {
                                ((FreeformContainerItem) it.next()).throwAway(freeformContainerItemController2);
                            }
                            break;
                        default:
                            FreeformContainerViewController freeformContainerViewController2 = freeformContainerFolderView.mViewController;
                            freeformContainerViewController2.mFolderView.collapse(false);
                            FreeformContainerItemController freeformContainerItemController3 = freeformContainerViewController2.mItemController;
                            freeformContainerItemController3.getClass();
                            Iterator it2 = new ArrayList(freeformContainerItemController3.mItemList).iterator();
                            while (it2.hasNext()) {
                                FreeformContainerItem freeformContainerItem = (FreeformContainerItem) it2.next();
                                freeformContainerItemController3.removeItem(freeformContainerItem);
                                freeformContainerItem.launch();
                            }
                            break;
                    }
                }
            });
            FreeformContainerFolderView.FolderItemDecoration folderItemDecoration = freeformContainerFolderView.mItemDecoration;
            folderItemDecoration.mItemMargin.left = FreeformContainerFolderView.this.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_left);
            folderItemDecoration.mItemMargin.top = FreeformContainerFolderView.this.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_top);
            folderItemDecoration.mItemMargin.right = FreeformContainerFolderView.this.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_right);
            folderItemDecoration.mItemMargin.bottom = FreeformContainerFolderView.this.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_bottom);
            folderItemDecoration.mItemSpace = FreeformContainerFolderView.this.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_space);
            FreeformContainerFolderView.this.mTrayView.mItemMargin.set(folderItemDecoration.mItemMargin);
            freeformContainerFolderView.mVisibleIconMaxCount = CoreRune.IS_TABLET_DEVICE ? 8 : 4;
            boolean isNightModeActive = freeformContainerFolderView.mContext.getResources().getConfiguration().isNightModeActive();
            if (Settings.System.getInt(freeformContainerFolderView.mContext.getContentResolver(), "wallpapertheme_state", 0) == 1) {
                freeformContainerFolderView.mColorTintList = freeformContainerFolderView.mContext.getResources().getColorStateList(17171332, null);
            } else if (isNightModeActive) {
                freeformContainerFolderView.mColorTintList = freeformContainerFolderView.mContext.getResources().getColorStateList(R.color.sec_decor_icon_color_dark, null);
            } else {
                freeformContainerFolderView.mColorTintList = freeformContainerFolderView.mContext.getResources().getColorStateList(R.color.sec_decor_icon_color_light, null);
            }
            FreeformContainerFolderTrayView freeformContainerFolderTrayView = freeformContainerFolderView.mTrayView;
            ColorStateList colorStateList = freeformContainerFolderView.mColorTintList;
            freeformContainerFolderTrayView.mOpenAllAppsButton.setImageTintList(colorStateList);
            freeformContainerFolderTrayView.mCloseButton.setImageTintList(colorStateList);
            freeformContainerFolderView.setVisibility(8);
            freeformContainerViewController.mContainerView.setSystemUiVisibility(512);
            freeformContainerViewController.mWindowManager.addView(freeformContainerViewController.mContainerView, layoutParams);
            freeformContainerViewController.mHideCallback = new FreeformContainerViewController$$ExternalSyntheticLambda1(freeformContainerViewController, 1);
            freeformContainerViewController.createOrUpdateDismissButton();
            FreeformContainerItemController freeformContainerItemController2 = this.mItemController;
            FreeformContainerViewController freeformContainerViewController2 = this.mViewController;
            freeformContainerItemController2.f446mH = this;
            freeformContainerItemController2.mViewController = freeformContainerViewController2;
            freeformContainerItemController2.mFreeformContainerIconLoader.loadResources();
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
            freeformContainerItemController2.mThreadPoolExecutor = threadPoolExecutor;
            threadPoolExecutor.allowCoreThreadTimeOut(true);
            FreeformContainerManager freeformContainerManager = FreeformContainerManager.this;
            freeformContainerManager.mRotation = freeformContainerManager.mContext.getDisplay().getRotation();
        }

        public final boolean noRunningService() {
            if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW && this.mIsBindingSmartPopupViewService) {
                return false;
            }
            return !this.mIsBindingMinimizeContainerService;
        }

        public final void registerReceivers() {
            try {
                this.mIWindowManager.watchRotation(FreeformContainerManager.this.mRotationWatcher, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            FreeformContainerManager.this.mConfiguration = new Configuration(FreeformContainerManager.this.mContext.getResources().getConfiguration());
            FreeformContainerManager freeformContainerManager = FreeformContainerManager.this;
            freeformContainerManager.mContext.registerReceiver(freeformContainerManager.mFreeformContainerReceiver, freeformContainerManager.mFreeformContainerFilter, 2);
        }

        public final void sendMessage(int i, Object obj) {
            sendMessage(obtainMessage(i, obj));
        }

        private HandlerC3983H(Looper looper) {
            super(looper, null, true);
            this.mViewController = new FreeformContainerViewController(FreeformContainerManager.this.mContext);
            Context context = FreeformContainerManager.this.mContext;
            this.mItemController = new FreeformContainerItemController(context);
            this.mIWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            this.mIsBindingMinimizeContainerService = false;
            if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                this.mIsBindingSmartPopupViewService = false;
            }
        }

        public final void sendMessage(int i) {
            sendMessage(obtainMessage(i));
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.freeform.FreeformContainerManager$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.freeform.FreeformContainerManager$2] */
    private FreeformContainerManager(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        this.mFreeformContainerFilter = intentFilter;
        this.mFreeformContainerReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.freeform.FreeformContainerManager.2
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                char c;
                String action = intent.getAction();
                switch (action.hashCode()) {
                    case -2128145023:
                        if (action.equals("android.intent.action.SCREEN_OFF")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1061859923:
                        if (action.equals("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -403228793:
                        if (action.equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 158859398:
                        if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 959232034:
                        if (action.equals("android.intent.action.USER_SWITCHED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1041332296:
                        if (action.equals("android.intent.action.DATE_CHANGED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1724567694:
                        if (action.equals("com.samsung.intent.action.LELINK_CAST_CONNECTION_CHANGED")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1779291251:
                        if (action.equals("android.os.action.POWER_SAVE_MODE_CHANGED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                if (c == 0) {
                    Configuration configuration = FreeformContainerManager.this.mContext.getResources().getConfiguration();
                    int diff = FreeformContainerManager.this.mConfiguration.diff(configuration);
                    FreeformContainerManager.this.mConfiguration = new Configuration(configuration);
                    if (((-2147405308) & diff) == 0) {
                        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("[Manager] onReceive: ", action, ", diff=0x");
                        m4m.append(Integer.toHexString(diff));
                        m4m.append(", No need to rebuild all");
                        Log.i("FreeformContainer", m4m.toString());
                        FreeformContainerManager.this.f447mH.sendMessage(36);
                        return;
                    }
                } else if (c != 1 && c != 2) {
                    if (c == 3) {
                        Log.i("FreeformContainer", "[Manager] onReceive: " + action + ", Restore only minimized container items");
                        FreeformContainerManager.this.f447mH.sendMessage(32);
                        return;
                    }
                    if (c == 4 || c == 5) {
                        Log.i("FreeformContainer", "[Manager] onReceive: " + action + ", Collapse minimized container tray");
                        FreeformContainerManager.this.f447mH.sendMessage(42);
                        return;
                    }
                    return;
                }
                HandlerC3983H handlerC3983H = FreeformContainerManager.this.f447mH;
                if (handlerC3983H.hasMessages(33)) {
                    handlerC3983H.removeMessages(33);
                }
                Log.i("FreeformContainer", "[Manager] rebuild all, reason: ".concat(action));
                handlerC3983H.sendMessage(33);
            }
        };
        this.mContext = context.getApplicationContext();
        HandlerThread handlerThread = new HandlerThread("FreeformContainerHandlerThread", 0);
        this.mThread = handlerThread;
        handlerThread.start();
        this.f447mH = new HandlerC3983H(this, handlerThread.getLooper(), 0);
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction("android.intent.action.DATE_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
    }

    public static FreeformContainerManager getInstance(Context context) {
        if (sFreeformContainerManager == null) {
            synchronized (FreeformContainerManager.class) {
                if (sFreeformContainerManager == null) {
                    sFreeformContainerManager = new FreeformContainerManager(context);
                }
            }
        }
        return sFreeformContainerManager;
    }

    public static void getStableInsets(Rect rect) {
        try {
            WindowManagerGlobal.getWindowManagerService().getStableInsets(0, rect);
        } catch (RemoteException e) {
            Log.e("FreeformContainer", "Failed to get stable insets", e);
        }
    }

    public final void finalize() {
        this.mThread.quit();
    }
}
