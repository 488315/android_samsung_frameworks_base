package com.android.wm.shell.freeform;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SemSystemProperties;
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
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.freeform.FreeformContainerFolderView;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class FreeformContainerManager {
    public static final String[] CHINA_SALES_CODES = {"CHN", "CHM", "CBK", "CTC", "CHU", "CHC"};
    public static FreeformContainerManager sFreeformContainerManager;
    public final Configuration mConfiguration;
    public final Context mContext;
    public final H mH;
    public final IntentFilter mIntentFilter;
    public final AnonymousClass2 mReceiver;
    public int mRotation;
    public final AnonymousClass1 mRotationWatcher;
    public final String mSalesCode = SemSystemProperties.getSalesCode();
    public final SettingsObserver mSettingsObserver;
    public final HandlerThread mThread;

    public final class H extends Handler {
        public final IWindowManager mIWindowManager;
        public boolean mIsBindingMinimizeContainerService;
        public boolean mIsBindingSmartPopupViewService;
        public final FreeformContainerItemController mItemController;
        public final FreeformContainerViewController mViewController;

        public /* synthetic */ H(FreeformContainerManager freeformContainerManager, Looper looper, int i) {
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
                ArrayList arrayList = (ArrayList) freeformContainerViewController.mCallBacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    FreeformContainerCallback freeformContainerCallback = (FreeformContainerCallback) obj;
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

        /* JADX WARN: Code restructure failed: missing block: B:150:0x032c, code lost:
        
            r5 = null;
         */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void handleMessage(Message message) throws Resources.NotFoundException {
            FreeformContainerItem freeformContainerItem;
            int i = message.what;
            boolean z = CoreRune.MW_FREEFORM_SMART_POPUP_VIEW;
            if ((z && (i == 21 || this.mIsBindingSmartPopupViewService)) || i == 11 || this.mIsBindingMinimizeContainerService) {
                Object obj = message.obj;
                FreeformContainerItem freeformContainerItem2 = obj instanceof FreeformContainerItem ? (FreeformContainerItem) obj : null;
                StringBuilder sb = new StringBuilder("[Manager] handleMessage: ");
                sb.append(messageToString(message.what));
                sb.append(freeformContainerItem2 == null ? "" : " item=" + freeformContainerItem2);
                Log.i("FreeformContainer", sb.toString());
                int i2 = message.what;
                int i3 = 0;
                if (i2 == 42) {
                    FreeformContainerViewController freeformContainerViewController = this.mViewController;
                    if (freeformContainerViewController.mState == 1) {
                        freeformContainerViewController.updateContainerState(0, false, true);
                        return;
                    }
                    return;
                }
                switch (i2) {
                    case 11:
                        if (noRunningService()) {
                            init();
                            registerReceivers();
                            if (message.arg1 == 1) {
                                Log.d("FreeformContainer", "restore all items on binding");
                                this.mItemController.restoreMinimizeContainerItems(FreeformContainerManager.this.mContext);
                            }
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
                        FreeformContainerManager freeformContainerManager = FreeformContainerManager.this;
                        try {
                            this.mIWindowManager.removeRotationWatcher(freeformContainerManager.mRotationWatcher);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        freeformContainerManager.mContext.unregisterReceiver(freeformContainerManager.mReceiver);
                        return;
                    case 13:
                        if (freeformContainerItem2 == null) {
                            return;
                        }
                        sendMessageDelayed(obtainMessage(16, freeformContainerItem2.getTaskId(), 0, freeformContainerItem2), freeformContainerItem2.mAnimationCompleted ? 0L : 3000L);
                        if (!CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                            this.mItemController.addItem(freeformContainerItem2);
                            return;
                        }
                        FreeformContainerItemController freeformContainerItemController = this.mItemController;
                        String str = freeformContainerItem2.mPackageName;
                        final int taskId = freeformContainerItem2.getTaskId();
                        freeformContainerItemController.getClass();
                        ArrayList arrayList = new ArrayList(freeformContainerItemController.mItemList);
                        int size = arrayList.size();
                        while (true) {
                            if (i3 >= size) {
                                break;
                            } else {
                                Object obj2 = arrayList.get(i3);
                                i3++;
                                freeformContainerItem = (FreeformContainerItem) obj2;
                                if (freeformContainerItem.mPackageName.equals(str)) {
                                    if (freeformContainerItem instanceof MultiInstanceItem) {
                                        if (freeformContainerItem.getItemList().stream().anyMatch(new Predicate() { // from class: com.android.wm.shell.freeform.FreeformContainerItemController$$ExternalSyntheticLambda1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj3) {
                                                return ((MultiInstanceItem) obj3).mTaskId == taskId;
                                            }
                                        })) {
                                            Log.w("FreeformContainer", "findCandidateItemForMultiInstance: already has child! " + freeformContainerItem);
                                            break;
                                        }
                                    } else if (!(freeformContainerItem instanceof MinimizeContainerItem) || freeformContainerItem.getTaskId() == taskId) {
                                    }
                                }
                            }
                        }
                        if (freeformContainerItem == null) {
                            this.mItemController.addItem(freeformContainerItem2);
                            return;
                        }
                        if (freeformContainerItem.asMultiInstanceItem() == null) {
                            this.mItemController.removeItem(freeformContainerItem);
                            MultiInstanceItem multiInstanceItem = new MultiInstanceItem(freeformContainerItem2, null);
                            multiInstanceItem.addChildItem(new MultiInstanceItem(freeformContainerItem, multiInstanceItem));
                            multiInstanceItem.addChildItem(new MultiInstanceItem(freeformContainerItem2, multiInstanceItem));
                            this.mItemController.addItem(multiInstanceItem);
                            return;
                        }
                        MultiInstanceItem multiInstanceItemAsMultiInstanceItem = freeformContainerItem.asMultiInstanceItem();
                        MultiInstanceItem multiInstanceItem2 = new MultiInstanceItem(freeformContainerItem2, multiInstanceItemAsMultiInstanceItem);
                        FreeformContainerItemController freeformContainerItemController2 = this.mItemController;
                        freeformContainerItemController2.getClass();
                        freeformContainerItemController2.mThreadPoolExecutor.execute(new FreeformContainerItemController$$ExternalSyntheticLambda0(freeformContainerItemController2, multiInstanceItem2));
                        multiInstanceItemAsMultiInstanceItem.addChildItem(multiInstanceItem2);
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
                                FreeformContainerManager freeformContainerManager2 = FreeformContainerManager.this;
                                try {
                                    this.mIWindowManager.removeRotationWatcher(freeformContainerManager2.mRotationWatcher);
                                } catch (RemoteException e2) {
                                    e2.printStackTrace();
                                }
                                freeformContainerManager2.mContext.unregisterReceiver(freeformContainerManager2.mReceiver);
                                return;
                            case 23:
                                if (!this.mViewController.isPointerView()) {
                                    this.mViewController.updateContainerState(0, true, true);
                                }
                                if (freeformContainerItem2 != null) {
                                    this.mItemController.addItem(freeformContainerItem2);
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
                                if (z) {
                                    this.mItemController.removeAllSmartPopupViewItem();
                                    return;
                                }
                                return;
                            default:
                                switch (i2) {
                                    case 30:
                                        if (freeformContainerItem2 != null) {
                                            this.mItemController.removeItem(freeformContainerItem2);
                                            freeformContainerItem2.launch();
                                            return;
                                        }
                                        return;
                                    case 31:
                                        if (freeformContainerItem2 != null) {
                                            if (!CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW || !(freeformContainerItem2 instanceof MultiInstanceItem)) {
                                                this.mItemController.iconLoadCompleted(freeformContainerItem2);
                                                return;
                                            }
                                            FreeformContainerItem itemById3 = this.mItemController.getItemById(freeformContainerItem2.getTaskId());
                                            if (itemById3 != null) {
                                                this.mItemController.iconLoadCompleted(itemById3);
                                                return;
                                            }
                                            return;
                                        }
                                        return;
                                    case 32:
                                        destroy();
                                        init();
                                        if (z) {
                                            this.mItemController.removeAllSmartPopupViewItem();
                                        }
                                        this.mItemController.restoreMinimizeContainerItems(FreeformContainerManager.this.mContext);
                                        return;
                                    case 33:
                                        FreeformContainerItemController freeformContainerItemController3 = this.mItemController;
                                        freeformContainerItemController3.getClass();
                                        ArrayList arrayList2 = new ArrayList(freeformContainerItemController3.mItemList);
                                        arrayList2.forEach(new FreeformContainerItemController$$ExternalSyntheticLambda2());
                                        destroy();
                                        init();
                                        FreeformContainerItemController freeformContainerItemController4 = this.mItemController;
                                        synchronized (freeformContainerItemController4.mItemList) {
                                            try {
                                                for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
                                                    freeformContainerItemController4.addItem((FreeformContainerItem) arrayList2.get(size2));
                                                }
                                            } catch (Throwable th) {
                                                throw th;
                                            }
                                        }
                                        return;
                                    case 34:
                                        int i4 = message.arg1;
                                        if (FreeformContainerManager.this.mRotation != i4) {
                                            this.mViewController.closeFullscreenMode("fullscreen_mode_request_screen_rotating");
                                            removeMessages(35, "fullscreen_mode_request_screen_rotating");
                                            if (this.mViewController.openFullscreenMode("fullscreen_mode_request_screen_rotating")) {
                                                sendMessageDelayed(obtainMessage(35, "fullscreen_mode_request_screen_rotating"), DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                                            }
                                            FreeformContainerItemController freeformContainerItemController5 = this.mItemController;
                                            freeformContainerItemController5.getClass();
                                            ArrayList arrayList3 = new ArrayList(freeformContainerItemController5.mItemList);
                                            int size3 = arrayList3.size();
                                            int i5 = 0;
                                            while (i5 < size3) {
                                                Object obj3 = arrayList3.get(i5);
                                                i5++;
                                                freeformContainerItemController5.animationCompleted((FreeformContainerItem) obj3);
                                            }
                                            this.mViewController.updateDisplayFrame(false);
                                            this.mViewController.hideDismissButton();
                                            FreeformContainerViewController freeformContainerViewController2 = this.mViewController;
                                            int i6 = FreeformContainerManager.this.mRotation;
                                            freeformContainerViewController2.createOrUpdateDismissButton();
                                            ArrayList arrayList4 = (ArrayList) freeformContainerViewController2.mCallBacks;
                                            int size4 = arrayList4.size();
                                            while (i3 < size4) {
                                                Object obj4 = arrayList4.get(i3);
                                                i3++;
                                                FreeformContainerCallback freeformContainerCallback = (FreeformContainerCallback) obj4;
                                                Log.i("FreeformContainer", "[ViewController] onRotationChanged: " + freeformContainerCallback);
                                                freeformContainerCallback.onRotationChanged(i6, i4, freeformContainerViewController2.mDisplayFrame);
                                            }
                                            FreeformContainerManager.this.mRotation = i4;
                                            return;
                                        }
                                        return;
                                    case 35:
                                        Object obj5 = message.obj;
                                        if (obj5 instanceof String) {
                                            if (this.mViewController.closeFullscreenMode((String) obj5)) {
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
                                            this.mViewController.updateContainerState(0, false, true);
                                            return;
                                        }
                                    case 37:
                                        Point point = (Point) message.obj;
                                        boolean z2 = message.arg1 == 1;
                                        FreeformContainerViewController freeformContainerViewController3 = this.mViewController;
                                        freeformContainerViewController3.mContainerView.setPointerPosition(point.x, point.y, z2);
                                        freeformContainerViewController3.mContainerView.mNeedInitPosition = false;
                                        return;
                                    default:
                                        return;
                                }
                        }
                }
            }
        }

        public final void init() throws Resources.NotFoundException {
            int i;
            FreeformContainerViewController freeformContainerViewController = this.mViewController;
            freeformContainerViewController.getClass();
            Log.i("FreeformContainer", "[ViewController] init");
            freeformContainerViewController.mH = this;
            FreeformContainerItemController freeformContainerItemController = this.mItemController;
            freeformContainerViewController.mItemController = freeformContainerItemController;
            freeformContainerViewController.mState = -1;
            ((ArrayList) freeformContainerViewController.mFullscreenModeRequests).clear();
            WindowManager.LayoutParams layoutParams = freeformContainerViewController.mLayoutParams;
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.type = 2604;
            layoutParams.flags = 25166632;
            layoutParams.format = -2;
            layoutParams.setTitle("FreeformContainer");
            WindowManager.LayoutParams layoutParams2 = freeformContainerViewController.mLayoutParams;
            layoutParams2.privateFlags |= 16;
            layoutParams2.samsungFlags |= 131072;
            layoutParams2.layoutInDisplayCutoutMode = 1;
            layoutParams2.gravity = 17;
            layoutParams2.windowAnimations = R.style.MinimizeContainer_WindowAnimation;
            freeformContainerViewController.updateDisplayFrame(false);
            FreeformContainerView freeformContainerView = (FreeformContainerView) freeformContainerViewController.mLayoutInflater.inflate(R.layout.freeform_container_layout, (ViewGroup) null);
            freeformContainerViewController.mContainerView = freeformContainerView;
            freeformContainerViewController.mFolderView = (FreeformContainerFolderView) freeformContainerView.findViewById(R.id.freeform_container_recycler_view);
            final FreeformContainerView freeformContainerView2 = freeformContainerViewController.mContainerView;
            H h = freeformContainerViewController.mH;
            freeformContainerView2.mViewController = freeformContainerViewController;
            Log.i("FreeformContainer", "[ViewController] registerCallback: " + freeformContainerView2);
            ((ArrayList) freeformContainerViewController.mCallBacks).add(freeformContainerView2);
            freeformContainerView2.mH = h;
            ViewTreeObserver viewTreeObserver = freeformContainerView2.getRootView().getViewTreeObserver();
            viewTreeObserver.addOnComputeInternalInsetsListener(freeformContainerView2.mInsetsComputer);
            viewTreeObserver.addOnDrawListener(freeformContainerView2.mSystemGestureExcludeUpdater);
            freeformContainerView2.mDefaultGapTop = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_pointer_default_gap_top);
            freeformContainerView2.mThresholdToMove = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_move_interval);
            freeformContainerView2.mPointerSettleDownGap = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_pointer_settle_down_gap);
            freeformContainerView2.mIconLeftMarginInFolder = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_left);
            freeformContainerView2.mIconItemTopMarginInFolder = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_top);
            freeformContainerView2.mAnimElevation = StrongAuthPopup$$ExternalSyntheticOutline0.m(freeformContainerView2.mContext, R.dimen.freeform_container_folder_elevation, 1);
            freeformContainerView2.mPointerViewSize = freeformContainerView2.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_outer_size);
            freeformContainerView2.mBackgroundDimView = (FrameLayout) freeformContainerView2.findViewById(R.id.freeform_container_dim_view);
            freeformContainerView2.mPointerGroupView = (ViewGroup) freeformContainerView2.findViewById(R.id.freeform_container_pointer_group_view);
            ImageButton imageButton = (ImageButton) freeformContainerView2.findViewById(R.id.freeform_container_pointer_control_view);
            freeformContainerView2.mPointerView = imageButton;
            imageButton.setColorFilter(0);
            freeformContainerView2.mPointerView.setHapticFeedbackEnabled(false);
            freeformContainerView2.mPointerView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda7
                /* JADX WARN: Removed duplicated region for block: B:33:0x00e7  */
                /* JADX WARN: Removed duplicated region for block: B:61:0x017f  */
                /* JADX WARN: Removed duplicated region for block: B:62:0x0187  */
                /* JADX WARN: Removed duplicated region for block: B:64:0x018a  */
                /* JADX WARN: Removed duplicated region for block: B:65:0x0192  */
                /* JADX WARN: Removed duplicated region for block: B:68:0x019b  */
                @Override // android.view.View.OnTouchListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    boolean z;
                    final boolean zIsEnterDismissButton;
                    final FreeformContainerView freeformContainerView3 = freeformContainerView2;
                    float[] fArr = FreeformContainerView.TAIL_ICON_ALPHA_ARRAY;
                    freeformContainerView3.getClass();
                    float rawX = motionEvent.getRawX();
                    float rawY = motionEvent.getRawY();
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        VelocityTracker velocityTracker = freeformContainerView3.mVelocityTracker;
                        if (velocityTracker == null) {
                            freeformContainerView3.mVelocityTracker = VelocityTracker.obtain();
                        } else {
                            velocityTracker.clear();
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
                        return false;
                    }
                    if (action != 1) {
                        if (action != 2) {
                            if (action == 3) {
                                freeformContainerView3.mViewController.hideDismissButton();
                                VelocityTracker velocityTracker2 = freeformContainerView3.mVelocityTracker;
                                if (velocityTracker2 != null) {
                                    velocityTracker2.recycle();
                                    freeformContainerView3.mVelocityTracker = null;
                                    return false;
                                }
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
                            Rect rect = freeformContainerView3.mTmpBounds;
                            FreeformContainerDismissButtonView freeformContainerDismissButtonView = freeformContainerViewController2.mDismissButtonView;
                            if (freeformContainerDismissButtonView != null) {
                                freeformContainerDismissButtonView.mDismissViewManager.mView.updateView(rect);
                                return false;
                            }
                        } else if (((float) Math.hypot(rawX - freeformContainerView3.mFirstDownX, rawY - freeformContainerView3.mFirstDownY)) >= freeformContainerView3.mThresholdToMove) {
                            freeformContainerView3.mIsAppIconMoving = true;
                            freeformContainerView3.getPointerViewBounds(freeformContainerView3.mTmpBounds);
                            if (!freeformContainerView3.mViewController.isDismissButtonShowing()) {
                                FreeformContainerViewController freeformContainerViewController3 = freeformContainerView3.mViewController;
                                Rect rect2 = freeformContainerView3.mTmpBounds;
                                freeformContainerViewController3.createOrUpdateDismissButton();
                                freeformContainerViewController3.mDismissButtonView.show(rect2);
                            }
                            freeformContainerView3.mPointerSettleDownEffectRequested = true;
                            freeformContainerView3.updateSpringConfig(10);
                            Log.i("FreeformContainer", "[ContainerView] onTouch(" + MotionEvent.actionToString(action) + "): Ready to move");
                            return false;
                        }
                        return false;
                    }
                    StringBuilder sb = new StringBuilder("[ContainerView] onTouch(");
                    sb.append(MotionEvent.actionToString(action));
                    sb.append(") mIsAppIconMoving=");
                    KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sb, freeformContainerView3.mIsAppIconMoving, "FreeformContainer");
                    if (freeformContainerView3.mIsAppIconMoving) {
                        freeformContainerView3.addMovementToVelocityTracker(motionEvent);
                        VelocityTracker velocityTracker3 = freeformContainerView3.mVelocityTracker;
                        if (velocityTracker3 == null) {
                            z = false;
                            int i2 = !z ? (int) ((freeformContainerView3.mVelocity.x / 700.0f) * 35.0f) : 0;
                            int i3 = !z ? (int) ((freeformContainerView3.mVelocity.y / 700.0f) * 35.0f) : 0;
                            zIsEnterDismissButton = freeformContainerView3.mViewController.isEnterDismissButton();
                            if (!zIsEnterDismissButton) {
                                float x = freeformContainerView3.mPointerView.getX() + i2;
                                float y = freeformContainerView3.mPointerView.getY() + i3;
                                if (z) {
                                    Rect rect3 = freeformContainerView3.mViewController.mNonDecorDisplayFrame;
                                    freeformContainerView3.updateSpringConfig((((float) rect3.left) > x || ((float) rect3.right) < x || ((float) rect3.top) > y || ((float) rect3.bottom) < y) ? 20 : 10);
                                }
                                freeformContainerView3.setPointerPosition(x, y, false);
                            }
                            freeformContainerView3.updateSpringChainEndValue();
                            freeformContainerView3.getParent().requestTransparentRegion(freeformContainerView3);
                            freeformContainerView3.mH.post(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda8
                                @Override // java.lang.Runnable
                                public final void run() {
                                    FreeformContainerView freeformContainerView4 = freeformContainerView3;
                                    boolean z2 = zIsEnterDismissButton;
                                    freeformContainerView4.mIsAppIconMoving = false;
                                    freeformContainerView4.getPointerViewBounds(freeformContainerView4.mTmpBounds);
                                    freeformContainerView4.mViewController.hideDismissButtonAndDismissIcon(null, freeformContainerView4.mPointerGroupView, freeformContainerView4.mTmpBounds);
                                    if (z2) {
                                        freeformContainerView4.setPointerPosition(freeformContainerView4.mFirstPointerX, freeformContainerView4.mFirstPointerY, false);
                                    }
                                }
                            });
                        } else {
                            Rect rect4 = freeformContainerView3.mViewController.mNonDecorDisplayFrame;
                            velocityTracker3.computeCurrentVelocity(1000, freeformContainerView3.mMaximumFlingVelocity);
                            freeformContainerView3.mVelocity.set(freeformContainerView3.mVelocityTracker.getXVelocity(), freeformContainerView3.mVelocityTracker.getYVelocity());
                            if (Math.abs(freeformContainerView3.mVelocity.x) > freeformContainerView3.mMinimumFlingVelocity || Math.abs(freeformContainerView3.mVelocity.y) > freeformContainerView3.mMinimumFlingVelocity) {
                                float f = freeformContainerView3.mVelocity.x;
                                boolean z2 = f < 0.0f;
                                if ((!z2 || freeformContainerView3.mFirstPointerX >= rect4.left) && ((z2 || freeformContainerView3.mFirstPointerX + freeformContainerView3.mPointerViewSize <= rect4.right) && (Math.abs(f) >= 700.0f || ((!z2 || freeformContainerView3.mPointerView.getX() >= rect4.left - 30) && (z2 || freeformContainerView3.mPointerView.getX() + freeformContainerView3.mPointerViewSize <= rect4.right + 30))))) {
                                    z = true;
                                }
                                if (!z) {
                                }
                                if (!z) {
                                }
                                zIsEnterDismissButton = freeformContainerView3.mViewController.isEnterDismissButton();
                                if (!zIsEnterDismissButton) {
                                }
                                freeformContainerView3.updateSpringChainEndValue();
                                freeformContainerView3.getParent().requestTransparentRegion(freeformContainerView3);
                                freeformContainerView3.mH.post(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda8
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        FreeformContainerView freeformContainerView4 = freeformContainerView3;
                                        boolean z22 = zIsEnterDismissButton;
                                        freeformContainerView4.mIsAppIconMoving = false;
                                        freeformContainerView4.getPointerViewBounds(freeformContainerView4.mTmpBounds);
                                        freeformContainerView4.mViewController.hideDismissButtonAndDismissIcon(null, freeformContainerView4.mPointerGroupView, freeformContainerView4.mTmpBounds);
                                        if (z22) {
                                            freeformContainerView4.setPointerPosition(freeformContainerView4.mFirstPointerX, freeformContainerView4.mFirstPointerY, false);
                                        }
                                    }
                                });
                            }
                        }
                    }
                    if (!freeformContainerView3.mViewController.isDismissButtonShowing() && !freeformContainerView3.mIsAppIconMoving && !freeformContainerView3.mViewController.mFolderView.mIsCollapseAnimating) {
                        if (freeformContainerView3.getIconViewListCount() == 1) {
                            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) freeformContainerView3.mViewController.mItemController.mItemList.get(0);
                            if (freeformContainerItem != null) {
                                if (!CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW || !(freeformContainerItem instanceof MultiInstanceItem)) {
                                    freeformContainerView3.mH.sendMessage(30, freeformContainerItem);
                                } else if (freeformContainerItem.getItemCount() == 1) {
                                    freeformContainerView3.mH.sendMessage(30, freeformContainerItem.getItemList().get(0));
                                } else {
                                    freeformContainerView3.mViewController.updateContainerState(1, true, true);
                                }
                            }
                        } else {
                            freeformContainerView3.mViewController.updateContainerState(1, true, true);
                        }
                    }
                    Log.i("FreeformContainer", "[ContainerView] onTouch(" + MotionEvent.actionToString(action) + ")");
                    return false;
                }
            });
            SharedPreferences sharedPreferences = freeformContainerView2.mContext.getSharedPreferences("freeform_container_pref", 0);
            if (sharedPreferences.contains("position_x") && sharedPreferences.contains("position_y")) {
                float fWidth = (freeformContainerView2.mViewController.mDisplayFrame.width() * 0.8f) - (freeformContainerView2.mPointerView.getWidth() / 2.0f);
                float f = freeformContainerView2.mViewController.mNonDecorDisplayFrame.top + freeformContainerView2.mDefaultGapTop;
                float f2 = sharedPreferences.getFloat("position_x", fWidth);
                float f3 = sharedPreferences.getFloat("position_y", f);
                Rect rect = freeformContainerView2.mTmpBounds;
                int i2 = (int) f2;
                int i3 = (int) f3;
                int i4 = freeformContainerView2.mPointerViewSize;
                rect.set(i2, i3, i2 + i4, i4 + i3);
                int rotation = freeformContainerView2.mContext.getDisplay().getRotation();
                int i5 = sharedPreferences.getInt("rotation", rotation);
                if (rotation != i5) {
                    FreeformContainerView.rotateBounds(i5, freeformContainerView2.mViewController.mDisplayFrame, freeformContainerView2.mTmpBounds, rotation);
                }
                StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("[ContainerView] loadPositionFromSharedPreferences, position=(", f2, ",", f3, ") default=(");
                sbM.append(fWidth);
                sbM.append(",");
                sbM.append(f);
                sbM.append(")");
                Log.i("FreeformContainer", sbM.toString());
                Rect rect2 = freeformContainerView2.mTmpBounds;
                i = 0;
                freeformContainerView2.setPointerPosition(rect2.left, rect2.top, false);
            } else {
                i = 0;
                Log.i("FreeformContainer", "[ContainerView] loadPositionFromSharedPreferences, need to init position");
                freeformContainerView2.mNeedInitPosition = true;
            }
            freeformContainerView2.setLayoutDirection(i);
            freeformContainerView2.setVisibility(8);
            freeformContainerView2.mMinimumFlingVelocity = ViewConfiguration.get(freeformContainerView2.mContext).getScaledMinimumFlingVelocity();
            freeformContainerView2.mMaximumFlingVelocity = ViewConfiguration.get(freeformContainerView2.mContext).getScaledMaximumFlingVelocity();
            FreeformContainerFolderView freeformContainerFolderView = freeformContainerViewController.mFolderView;
            H h2 = freeformContainerViewController.mH;
            freeformContainerFolderView.mViewController = freeformContainerViewController;
            Log.i("FreeformContainer", "[ViewController] registerCallback: " + freeformContainerFolderView);
            ((ArrayList) freeformContainerViewController.mCallBacks).add(freeformContainerFolderView);
            freeformContainerFolderView.mH = h2;
            freeformContainerFolderView.mDraggingIconView = (ImageView) freeformContainerFolderView.getRootView().findViewById(R.id.freeform_container_folder_dragging_icon_view);
            freeformContainerFolderView.mDraggingPreview = (ImageView) freeformContainerFolderView.getRootView().findViewById(R.id.freeform_container_folder_dragging_preview);
            freeformContainerFolderView.mTrayView = (FreeformContainerFolderTrayView) freeformContainerFolderView.getRootView().findViewById(R.id.freeform_container_tray_view);
            freeformContainerFolderView.mFolderMaxWidth = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_max_width);
            freeformContainerFolderView.mItemSize = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_item_size);
            freeformContainerFolderView.mPaddingLeft = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_position_padding_left);
            freeformContainerFolderView.mPaddingRight = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_folder_position_padding_right);
            freeformContainerFolderView.mThresholdToMove = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_move_interval);
            freeformContainerFolderView.mEmptySlotIcon = freeformContainerFolderView.mContext.getResources().getDrawable(R.drawable.ic_mw_popupview_min_ic_empty_mtrl);
            if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                freeformContainerFolderView.mThumbnailMargin = freeformContainerFolderView.getResources().getDimensionPixelSize(R.dimen.freeform_thumbnail_margin);
                freeformContainerFolderView.mPreviewWidth = freeformContainerFolderView.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_preview_width);
                freeformContainerFolderView.mPreviewHeight = freeformContainerFolderView.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_preview_height);
                freeformContainerFolderView.mVerticalPreviewMargin = freeformContainerFolderView.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_preview_padding);
                freeformContainerFolderView.mContext.getDisplay().getRealSize(freeformContainerFolderView.mDisplaySize);
                freeformContainerFolderView.mAirViewMargin = freeformContainerFolderView.getResources().getDimensionPixelSize(R.dimen.freeform_thumbnail_air_view_margin);
                freeformContainerFolderView.mOrientation = freeformContainerFolderView.getResources().getConfiguration().orientation;
                freeformContainerFolderView.mPointerSettleDownGap = freeformContainerFolderView.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_pointer_settle_down_gap);
                freeformContainerFolderView.computeInset();
            }
            freeformContainerFolderView.mTrayView.mCloseButton.setOnClickListener(new FreeformContainerFolderView$$ExternalSyntheticLambda3(freeformContainerFolderView, 0));
            freeformContainerFolderView.mTrayView.mOpenAllAppsButton.setOnClickListener(new FreeformContainerFolderView$$ExternalSyntheticLambda3(freeformContainerFolderView, 1));
            FreeformContainerFolderView.FolderItemDecoration folderItemDecoration = freeformContainerFolderView.mItemDecoration;
            folderItemDecoration.mItemMargin.left = FreeformContainerFolderView.this.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_left);
            folderItemDecoration.mItemMargin.top = FreeformContainerFolderView.this.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_top);
            folderItemDecoration.mItemMargin.right = FreeformContainerFolderView.this.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_right);
            folderItemDecoration.mItemMargin.bottom = FreeformContainerFolderView.this.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_margin_bottom);
            folderItemDecoration.mItemSpace = FreeformContainerFolderView.this.getResources().getDimensionPixelSize(R.dimen.freeform_container_item_space);
            FreeformContainerFolderView.this.mTrayView.mItemMargin.set(folderItemDecoration.mItemMargin);
            freeformContainerFolderView.calculateVisibleIconMaxCount();
            ColorStateList colorStateList = freeformContainerFolderView.getResources().getColorStateList(Settings.System.getInt(freeformContainerFolderView.mContext.getContentResolver(), "wallpapertheme_state", 0) == 1 ? 17171431 : freeformContainerFolderView.getResources().getConfiguration().isNightModeActive() ? R.color.mw_caption_button_icon_color_dark : R.color.mw_caption_button_icon_color_light, null);
            FreeformContainerFolderTrayView freeformContainerFolderTrayView = freeformContainerFolderView.mTrayView;
            freeformContainerFolderTrayView.mOpenAllAppsButton.setImageTintList(colorStateList);
            freeformContainerFolderTrayView.mCloseButton.setImageTintList(colorStateList);
            freeformContainerFolderView.getHorizontalScrollbarThumbDrawable().setColorFilter(freeformContainerFolderView.mContext.getColor(R.color.mw_popupview_min_scrollbar_tint), PorterDuff.Mode.SRC_ATOP);
            freeformContainerFolderView.setVisibility(8);
            freeformContainerViewController.mContainerView.setSystemUiVisibility(512);
            freeformContainerViewController.mWindowManager.addView(freeformContainerViewController.mContainerView, freeformContainerViewController.mLayoutParams);
            freeformContainerViewController.mHideCallback = new FreeformContainerViewController$$ExternalSyntheticLambda1(freeformContainerViewController, 1);
            freeformContainerViewController.createOrUpdateDismissButton();
            freeformContainerItemController.mH = this;
            freeformContainerItemController.mViewController = freeformContainerViewController;
            freeformContainerItemController.mFreeformContainerIconLoader.loadResources();
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
            freeformContainerItemController.mThreadPoolExecutor = threadPoolExecutor;
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
            FreeformContainerManager freeformContainerManager = FreeformContainerManager.this;
            try {
                this.mIWindowManager.watchRotation(freeformContainerManager.mRotationWatcher, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            freeformContainerManager.mContext.registerReceiver(freeformContainerManager.mReceiver, freeformContainerManager.mIntentFilter, 2);
        }

        public final void sendMessage(int i, Object obj) {
            sendMessage(obtainMessage(i, obj));
        }

        private H(Looper looper) {
            super(looper, null, true);
            this.mViewController = new FreeformContainerViewController(FreeformContainerManager.this.mContext);
            this.mItemController = new FreeformContainerItemController(FreeformContainerManager.this.mContext);
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

    public final class SettingsObserver extends ContentObserver {
        public final Uri mColorThemeAppIconUri;
        public final Uri mCurrentSecAppIconThemePackageUri;
        public final Uri mLeboSettingUri;
        public final Uri mWallpaperThemeColorUri;
        public final Uri mWallpaperThemeStateUri;

        public SettingsObserver() {
            super(null);
            Uri uriFor = Settings.System.getUriFor(SettingsHelper.INDEX_COLOR_THEME_APP_ICON);
            this.mColorThemeAppIconUri = uriFor;
            Uri uriFor2 = Settings.System.getUriFor("wallpapertheme_state");
            this.mWallpaperThemeStateUri = uriFor2;
            Uri uriFor3 = Settings.System.getUriFor("wallpapertheme_color");
            this.mWallpaperThemeColorUri = uriFor3;
            Uri uriFor4 = Settings.System.getUriFor(SettingsHelper.INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE);
            this.mCurrentSecAppIconThemePackageUri = uriFor4;
            Uri uriFor5 = Settings.Global.getUriFor("lelink_cast_on");
            this.mLeboSettingUri = uriFor5;
            ContentResolver contentResolver = FreeformContainerManager.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(uriFor, false, this, -1);
            contentResolver.registerContentObserver(uriFor2, false, this, -1);
            contentResolver.registerContentObserver(uriFor3, false, this, -1);
            contentResolver.registerContentObserver(uriFor4, false, this, -1);
            if (CoreRune.BAIDU_CARLIFE) {
                contentResolver.registerContentObserver(uriFor5, false, this, -1);
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            if (this.mColorThemeAppIconUri.equals(uri) || this.mWallpaperThemeStateUri.equals(uri) || this.mWallpaperThemeColorUri.equals(uri) || this.mCurrentSecAppIconThemePackageUri.equals(uri)) {
                FreeformContainerManager.this.rebuildAll("colorPalette");
            } else if (this.mLeboSettingUri.equals(uri) && FreeformContainerManager.m3267$$Nest$mshouldHideInformation(FreeformContainerManager.this)) {
                FreeformContainerManager.this.mH.sendMessage(25);
            }
        }
    }

    /* renamed from: -$$Nest$mshouldHideInformation, reason: not valid java name */
    public static boolean m3267$$Nest$mshouldHideInformation(FreeformContainerManager freeformContainerManager) {
        String str;
        DisplayManager displayManager;
        int activeDisplayState;
        freeformContainerManager.getClass();
        if (CoreRune.BAIDU_CARLIFE && (str = freeformContainerManager.mSalesCode) != null) {
            String[] strArr = CHINA_SALES_CODES;
            int i = 0;
            while (true) {
                if (i >= 6) {
                    break;
                }
                if (strArr[i].equals(str)) {
                    if (Settings.Global.getInt(FreeformContainerManager.this.mContext.getContentResolver(), "lelink_cast_on", 0) != 1 && ((displayManager = (DisplayManager) freeformContainerManager.mContext.getSystemService("display")) == null || displayManager.semGetWifiDisplayStatus() == null || !((activeDisplayState = displayManager.semGetWifiDisplayStatus().getActiveDisplayState()) == 1 || activeDisplayState == 2))) {
                        break;
                    }
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.freeform.FreeformContainerManager$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.wm.shell.freeform.FreeformContainerManager$2] */
    private FreeformContainerManager(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        this.mIntentFilter = intentFilter;
        this.mConfiguration = new Configuration();
        this.mRotationWatcher = new IRotationWatcher.Stub() { // from class: com.android.wm.shell.freeform.FreeformContainerManager.1
            public final void onRotationChanged(int i) {
                Log.i("FreeformContainer", "[Manager] onRotationChanged: " + Surface.rotationToString(i));
                H h = FreeformContainerManager.this.mH;
                h.sendMessage(h.obtainMessage(34, i, 0));
            }
        };
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.freeform.FreeformContainerManager.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action;
                action = intent.getAction();
                action.getClass();
                switch (action) {
                    case "android.intent.action.SCREEN_OFF":
                    case "android.intent.action.CLOSE_SYSTEM_DIALOGS":
                        Log.i("FreeformContainer", "[Manager] onReceive: " + action + ", Collapse minimized container tray");
                        FreeformContainerManager.this.mH.sendMessage(42);
                        break;
                    case "com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE":
                    case "com.samsung.intent.action.LELINK_CAST_CONNECTION_CHANGED":
                        if (FreeformContainerManager.m3267$$Nest$mshouldHideInformation(FreeformContainerManager.this)) {
                            FreeformContainerManager.this.mH.sendMessage(25);
                            break;
                        }
                        break;
                    case "android.intent.action.USER_SWITCHED":
                        Log.i("FreeformContainer", "[Manager] onReceive: " + action + ", Restore only minimized container items");
                        FreeformContainerManager.this.mH.sendMessage(32);
                        break;
                    case "android.intent.action.DATE_CHANGED":
                    case "android.os.action.POWER_SAVE_MODE_CHANGED":
                        FreeformContainerManager.this.rebuildAll(action);
                        break;
                }
            }
        };
        this.mContext = context.getApplicationContext();
        HandlerThread handlerThread = new HandlerThread("FreeformContainerHandlerThread", 0);
        this.mThread = handlerThread;
        handlerThread.start();
        this.mSettingsObserver = new SettingsObserver();
        this.mH = new H(this, handlerThread.getLooper(), 0);
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.DATE_CHANGED", "android.os.action.POWER_SAVE_MODE_CHANGED", "android.intent.action.USER_SWITCHED", PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        if (CoreRune.BAIDU_CARLIFE) {
            intentFilter.addAction("com.samsung.intent.action.LELINK_CAST_CONNECTION_CHANGED");
            intentFilter.addAction("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE");
        }
    }

    public static FreeformContainerManager getInstance(Context context) {
        if (sFreeformContainerManager == null) {
            synchronized (FreeformContainerManager.class) {
                try {
                    if (sFreeformContainerManager == null) {
                        sFreeformContainerManager = new FreeformContainerManager(context);
                    }
                } finally {
                }
            }
        }
        return sFreeformContainerManager;
    }

    public static void getOverrideStableInsets(Rect rect) {
        try {
            WindowManagerGlobal.getWindowManagerService().getOverrideStableInsets(0, rect);
        } catch (RemoteException e) {
            Log.e("FreeformContainer", "Failed to get override stable insets", e);
        }
    }

    public final void finalize() {
        this.mThread.quit();
    }

    public final void rebuildAll(String str) {
        H h = this.mH;
        if (h.hasMessages(33)) {
            h.removeMessages(33);
        }
        Log.i("FreeformContainer", "[Manager] rebuild all, reason: ".concat(str));
        h.sendMessage(33);
    }
}
