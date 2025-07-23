package com.android.wm.shell.freeform;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import android.view.Surface;
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
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.freeform.FreeformContainerFolderView;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Code restructure failed: missing block: B:196:0x0373, code lost:
        
            r5 = null;
         */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r9) {
            /*
                Method dump skipped, instructions count: 1092
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.freeform.FreeformContainerManager.H.handleMessage(android.os.Message):void");
        }

        public final void init() {
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
                /* JADX WARN: Removed duplicated region for block: B:35:0x017f  */
                /* JADX WARN: Removed duplicated region for block: B:37:0x018a  */
                /* JADX WARN: Removed duplicated region for block: B:40:0x019b  */
                /* JADX WARN: Removed duplicated region for block: B:54:0x0192  */
                /* JADX WARN: Removed duplicated region for block: B:55:0x0187  */
                @Override // android.view.View.OnTouchListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final boolean onTouch(android.view.View r13, android.view.MotionEvent r14) {
                    /*
                        Method dump skipped, instructions count: 663
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.freeform.FreeformContainerView$$ExternalSyntheticLambda7.onTouch(android.view.View, android.view.MotionEvent):boolean");
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
                    FreeformContainerView.rotateBounds(i5, freeformContainerView2.mViewController.mDisplayFrame, freeformContainerView2.mTmpBounds, rotation);
                }
                StringBuilder m = CubicBezierEasing$$ExternalSyntheticOutline0.m("[ContainerView] loadPositionFromSharedPreferences, position=(", f2, ",", f3, ") default=(");
                m.append(width);
                m.append(",");
                m.append(f);
                m.append(")");
                Log.i("FreeformContainer", m.toString());
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            } else if (this.mLeboSettingUri.equals(uri) && FreeformContainerManager.m3251$$Nest$mshouldHideInformation(FreeformContainerManager.this)) {
                FreeformContainerManager.this.mH.sendMessage(25);
            }
        }
    }

    /* renamed from: -$$Nest$mshouldHideInformation, reason: not valid java name */
    public static boolean m3251$$Nest$mshouldHideInformation(FreeformContainerManager freeformContainerManager) {
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
                        if (FreeformContainerManager.m3251$$Nest$mshouldHideInformation(FreeformContainerManager.this)) {
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
