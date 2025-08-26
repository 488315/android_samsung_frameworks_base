package com.android.systemui.dextouchpad.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.activity.ButtonWindow;
import com.android.systemui.dextouchpad.activity.TouchpadWindow;
import com.android.systemui.dextouchpad.data.TouchpadButtonItems;
import com.android.systemui.dextouchpad.data.TouchpadGesturesGuideItems;
import com.android.systemui.dextouchpad.manager.notification.NotificationType;
import com.android.systemui.dextouchpad.manager.notification.TouchpadNotificationManager;
import com.android.systemui.dextouchpad.settings.Settings$Key;
import com.android.systemui.dextouchpad.settings.SettingsKeys;
import com.android.systemui.dextouchpad.settings.SettingsRepository;
import com.android.systemui.dextouchpad.util.Features;
import com.android.systemui.dextouchpad.util.Utils;
import com.android.systemui.dextouchpad.view.TouchpadGesturesInternalDialog;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import dagger.Lazy;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class TouchpadFragment extends Fragment {
    public FragmentActivity mActivity;
    public Context mApplicationContext;
    public DisplayManager mDisplayManager;
    public Handler mHandler;
    public SettingsObserver mSettingsObserver;
    public SettingsRepository mSettingsRepo;
    public ImageView mSpenIcon;
    public ViewHideScheduler mSpenIconViewHideScheduler;
    public View mSpenRecognitionArea;
    public TextView mTouchpadAutoRunTextView;
    public TouchpadNotificationManager mTouchpadNotificationManager;
    public TouchpadViewModel mViewModel;
    public int mDexDisplayRotation = 0;
    public final AnonymousClass1 mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.dextouchpad.activity.TouchpadFragment.1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            DisplayManager displayManager = TouchpadFragment.this.mDisplayManager;
            if (displayManager == null) {
                return;
            }
            Display display = displayManager.getDisplay(i);
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            if ((displayInfo.flags & 131072) == 0 || TouchpadFragment.this.mDexDisplayRotation == display.getRotation()) {
                return;
            }
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onDisplayChanged: id=", ", orientation: ");
            sbM.append(TouchpadFragment.this.mDexDisplayRotation);
            sbM.append("->");
            sbM.append(display.getRotation());
            Log.d("DexTouchpadFragment", sbM.toString());
            TouchpadFragment.this.mDexDisplayRotation = display.getRotation();
            TouchpadFragment.this.mActivity.recreate();
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }
    };
    public final AnonymousClass5 mRemoveSpenRecognitionAreaRunnable = new Runnable() { // from class: com.android.systemui.dextouchpad.activity.TouchpadFragment.5
        @Override // java.lang.Runnable
        public final void run() {
            View view = TouchpadFragment.this.mSpenRecognitionArea;
            if (view != null) {
                view.setVisibility(4);
            }
            TouchpadFragment.this.mViewModel.mTouchpadViewMover.setSpenEnabled(false);
            TouchpadFragment.this.mViewModel.mButtonWindowController.setRotationButtonVisibility(true);
        }
    };

    public class SettingsObserver extends ContentObserver {
        public final ContentResolver mResolver;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.mResolver = TouchpadFragment.this.requireContext().getContentResolver();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            TouchpadFragment touchpadFragment = TouchpadFragment.this;
            String lastPathSegment = uri.getLastPathSegment();
            touchpadFragment.getClass();
            if (!"SPEN_INPUT_MODE_DEX".equals(lastPathSegment)) {
                if ("autorun_touchpad".equals(lastPathSegment)) {
                    touchpadFragment.mViewModel.mIsTouchpadAutoRunShown = false;
                    touchpadFragment.mTouchpadAutoRunTextView.setVisibility(8);
                    return;
                }
                return;
            }
            touchpadFragment.updateSpenRecognitionArea();
            TouchpadNotificationManager touchpadNotificationManager = touchpadFragment.mTouchpadNotificationManager;
            if (touchpadNotificationManager != null) {
                touchpadNotificationManager.show(NotificationType.SPEN);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        if (Features.DEBUG) {
            Log.d("DexTouchpadFragment", "onCreate()");
        }
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        Context applicationContext = activity.getApplicationContext();
        this.mApplicationContext = applicationContext;
        this.mDisplayManager = (DisplayManager) applicationContext.getSystemService("display");
        this.mTouchpadNotificationManager = TouchpadNotificationManager.getsInstance(this.mApplicationContext);
        this.mSettingsRepo = SettingsRepository.getInstance(this.mApplicationContext);
        TouchpadWindow touchpadWindow = new TouchpadWindow(1000, "TouchpadActivity", VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, new AtomicBoolean(false), this.mApplicationContext);
        Lazy lazy = new Lazy(this) { // from class: com.android.systemui.dextouchpad.activity.TouchpadFragment.2
            @Override // dagger.Lazy
            public final Object get() {
                return new FloatingWindow(1002, "SPenRecognitionArea", 1073741824);
            }
        };
        ButtonWindowController buttonWindowController = new ButtonWindowController(new AtomicBoolean(false));
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mViewModel = new TouchpadViewModel(this.mActivity.getApplication(), touchpadWindow, buttonWindowController, lazy, new TouchpadViewMover(this.mHandler));
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
        if (bundle == null) {
            Utils.mLatestRotation = 0;
        }
        Window window = this.mActivity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.samsungFlags |= 16777216;
        window.setAttributes(attributes);
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (Features.DEBUG) {
            Log.d("DexTouchpadFragment", "onCreateView()");
        }
        return layoutInflater.inflate(Features.IS_SUPPORT_TABLET ? R.layout.fragment_touchpad_tablet : R.layout.fragment_touchpad, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (Features.DEBUG) {
            Log.d("DexTouchpadFragment", "onDestroy()");
        }
        this.mCalled = true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyView() {
        if (Features.DEBUG) {
            Log.d("DexTouchpadFragment", "onDestroyView()");
        }
        this.mCalled = true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDetach() {
        if (Features.DEBUG) {
            Log.d("DexTouchpadFragment", "onDetach()");
        }
        this.mActivity = null;
        this.mCalled = true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        if (Features.DEBUG) {
            Log.d("DexTouchpadFragment", "onResume()");
        }
        this.mCalled = true;
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.dextouchpad.activity.TouchpadFragment.3
            @Override // java.lang.Runnable
            public final void run() {
                TouchpadNotificationManager touchpadNotificationManager = TouchpadFragment.this.mTouchpadNotificationManager;
                NotificationType notificationType = NotificationType.TOUCHPAD;
                if (((HashMap) touchpadNotificationManager.mActiveNotifications).containsKey(notificationType)) {
                    TouchpadFragment.this.mTouchpadNotificationManager.remove(notificationType);
                }
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStart() {
        if (Features.DEBUG) {
            Log.d("DexTouchpadFragment", "onStart()");
        }
        this.mCalled = true;
        DisplayManager displayManager = this.mDisplayManager;
        if (displayManager != null) {
            displayManager.registerDisplayListener(this.mDisplayListener, null);
            Display[] displays = this.mDisplayManager.getDisplays();
            int length = displays.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Display display = displays[i];
                DisplayInfo displayInfo = new DisplayInfo();
                display.getDisplayInfo(displayInfo);
                if ((displayInfo.flags & 131072) != 0) {
                    Log.d("DexTouchpadFragment", "DeX display id=" + display.getDisplayId() + " rotation=" + display.getRotation());
                    this.mDexDisplayRotation = display.getRotation();
                    break;
                }
                i++;
            }
        } else {
            Log.w("DexTouchpadFragment", "DisplayManager is null");
        }
        Window window = this.mActivity.getWindow();
        if (window != null) {
            window.setDecorFitsSystemWindows(false);
            WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.systemBars());
                insetsController.setSystemBarsBehavior(2);
            }
        }
        boolean z = Features.DEBUG;
        if (z) {
            Log.d("DexTouchpadFragment", "setScreenRotation()");
        }
        Context context = this.mApplicationContext;
        if (Features.IS_SUPPORT_WINNER && context.getResources().getConfiguration().semDisplayDeviceType == 5) {
            int i2 = this.mActivity.getResources().getConfiguration().orientation;
            if (i2 == 2) {
                this.mActivity.setRequestedOrientation(6);
            } else if (i2 == 1) {
                this.mActivity.setRequestedOrientation(1);
            }
        }
        TouchpadViewModel touchpadViewModel = this.mViewModel;
        if (touchpadViewModel.mIsTouchpadEnabled) {
            touchpadViewModel.mTouchpadViewMover.updatePosition(true);
        } else {
            Settings.System.putIntForUser(this.mApplicationContext.getContentResolver(), "touchpad_enabled", 1, -2);
            TouchpadViewModel touchpadViewModel2 = this.mViewModel;
            touchpadViewModel2.mIsTouchpadEnabled = true;
            Utils.mIsTouchpadEnabled = true;
            touchpadViewModel2.mTouchpadStartTime = System.currentTimeMillis();
            CoreSaLogger.logForDexWithScreenId("700", "7000");
            TouchpadViewMover touchpadViewMover = this.mViewModel.mTouchpadViewMover;
            touchpadViewMover.getClass();
            if (z) {
                Log.d("DexTouchpadViewMover", "onStart()");
            }
            TouchpadViewMover$$ExternalSyntheticLambda0 touchpadViewMover$$ExternalSyntheticLambda0 = touchpadViewMover.mUpdatePositionRunnable;
            Handler handler = touchpadViewMover.mHandler;
            handler.removeCallbacks(touchpadViewMover$$ExternalSyntheticLambda0);
            touchpadViewMover.mPosition.set(0, 0);
            touchpadViewMover.executeAnimator();
            handler.postDelayed(touchpadViewMover$$ExternalSyntheticLambda0, TouchpadViewMover.MOVE_POSITION_TIMEOUT);
        }
        SettingsRepository settingsRepository = this.mSettingsRepo;
        Settings$Key settings$Key = SettingsKeys.TOUCHPAD_AUTO_RUN_GUIDE_COUNT;
        int i3 = settingsRepository.getInt(settings$Key);
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i3, "updateTouchpadAutoRunTextView, count=", "DexTouchpadFragment");
        }
        boolean z2 = this.mViewModel.mIsTouchpadAutoRunShown;
        if (z2 || i3 == 3) {
            if (!z2) {
                this.mSettingsRepo.putInt(settings$Key, i3 + 1);
                this.mViewModel.mIsTouchpadAutoRunShown = true;
            }
            this.mTouchpadAutoRunTextView.setVisibility(0);
        }
        updateSpenRecognitionArea();
        SettingsObserver settingsObserver = this.mSettingsObserver;
        settingsObserver.mResolver.registerContentObserver(Settings.Global.getUriFor("autorun_touchpad"), false, settingsObserver, -1);
        settingsObserver.mResolver.registerContentObserver(Settings.Global.getUriFor("SPEN_INPUT_MODE_DEX"), false, settingsObserver, -1);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onStop() {
        if (Features.DEBUG) {
            Log.d("DexTouchpadFragment", "onStop()");
        }
        this.mCalled = true;
        final int intForUser = Settings.Secure.getIntForUser(requireContext().getContentResolver(), SettingsHelper.INDEX_NAVIGATION_MODE, 0, -2);
        if (!this.mActivity.isChangingConfigurations() && this.mViewModel.mIsTouchpadEnabled) {
            Settings.System.putIntForUser(this.mApplicationContext.getContentResolver(), "touchpad_enabled", 0, -2);
            this.mViewModel.mIsTouchpadEnabled = false;
            Utils.mIsTouchpadEnabled = false;
            CoreSaLogger.logForDexWithScreenId("700", "7001", (System.currentTimeMillis() - this.mViewModel.mTouchpadStartTime) / 1000);
            TouchpadViewModel touchpadViewModel = this.mViewModel;
            touchpadViewModel.mTouchpadStartTime = 0L;
            if (touchpadViewModel.mSpenStartTime > 0) {
                CoreSaLogger.logForDexWithScreenId("701", "7003", (System.currentTimeMillis() - this.mViewModel.mSpenStartTime) / 1000);
                this.mViewModel.mSpenStartTime = 0L;
            }
            if (Features.IS_SPEN_INBOX_MODEL) {
                Utils.mIsSpenDetached = false;
            }
            TouchpadViewMover touchpadViewMover = this.mViewModel.mTouchpadViewMover;
            touchpadViewMover.mIsMoveStarted = false;
            touchpadViewMover.mHandler.removeCallbacks(touchpadViewMover.mUpdatePositionRunnable);
            this.mViewModel.mIsTouchpadAutoRunShown = false;
            this.mTouchpadAutoRunTextView.setVisibility(8);
        }
        ViewHideScheduler viewHideScheduler = this.mSpenIconViewHideScheduler;
        if (viewHideScheduler != null) {
            viewHideScheduler.cancel();
            this.mSpenIconViewHideScheduler = null;
        }
        View view = this.mSpenRecognitionArea;
        if (view != null) {
            view.removeCallbacks(this.mRemoveSpenRecognitionAreaRunnable);
            FloatingWindow floatingWindow = this.mViewModel.mSpenRecognitionWindow;
            if (floatingWindow != null) {
                floatingWindow.tearDown();
            }
        }
        DisplayManager displayManager = this.mDisplayManager;
        if (displayManager != null) {
            displayManager.unregisterDisplayListener(this.mDisplayListener);
        }
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.dextouchpad.activity.TouchpadFragment.4
            @Override // java.lang.Runnable
            public final void run() {
                if (QuickStepContract.isGesturalMode(intForUser)) {
                    TouchpadNotificationManager touchpadNotificationManager = TouchpadFragment.this.mTouchpadNotificationManager;
                    NotificationType notificationType = NotificationType.TOUCHPAD;
                    if (((HashMap) touchpadNotificationManager.mActiveNotifications).containsKey(notificationType)) {
                        return;
                    }
                    TouchpadFragment.this.mTouchpadNotificationManager.show(notificationType);
                }
            }
        });
        SettingsObserver settingsObserver = this.mSettingsObserver;
        settingsObserver.mResolver.unregisterContentObserver(settingsObserver);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        if (Features.DEBUG) {
            Log.d("DexTouchpadFragment", "onViewCreated()");
        }
        this.mSpenRecognitionArea = view.findViewById(R.id.spen_recognition_area);
        this.mSpenIcon = (ImageView) view.findViewById(R.id.spen_icon);
        this.mTouchpadAutoRunTextView = (TextView) view.findViewById(R.id.touchpad_auto_run_text);
        this.mViewModel.mTouchpadWindow.setup(this.mActivity, view.findViewById(R.id.touchpad_base));
        final ButtonWindowController buttonWindowController = this.mViewModel.mButtonWindowController;
        FragmentActivity fragmentActivity = this.mActivity;
        buttonWindowController.mActivity = fragmentActivity;
        buttonWindowController.mContext = view.getContext();
        View viewFindViewById = view.findViewById(R.id.touchpad_rotation_button);
        if (viewFindViewById != null) {
            if (buttonWindowController.mRotationButtonWindow == null) {
                buttonWindowController.mRotationButtonWindow = new RotationButtonWindow(buttonWindowController.mSpenNotSupportedToastBlocked);
            }
            buttonWindowController.mRotationButtonWindow.setup(fragmentActivity, viewFindViewById);
        }
        View viewFindViewById2 = view.findViewById(R.id.touchpad_guide_button);
        if (viewFindViewById2 != null) {
            if (buttonWindowController.mGuideButtonWindow == null) {
                buttonWindowController.mGuideButtonWindow = new ButtonWindow(TouchpadButtonItems.GUIDE, buttonWindowController.mSpenNotSupportedToastBlocked);
            }
            ButtonWindow buttonWindow = buttonWindowController.mGuideButtonWindow;
            final int i = 0;
            buttonWindow.mOnGestureListener = new ButtonWindow.OnGestureListener() { // from class: com.android.systemui.dextouchpad.activity.ButtonWindowController$$ExternalSyntheticLambda0
                @Override // com.android.systemui.dextouchpad.activity.ButtonWindow.OnGestureListener
                public final void onDoubleTap() {
                    switch (i) {
                        case 0:
                            ButtonWindowController buttonWindowController2 = buttonWindowController;
                            if (buttonWindowController2.mActivity != null) {
                                buttonWindowController2.mNightMode = (buttonWindowController2.mContext.getResources().getConfiguration().uiMode & 48) == 32;
                                new TouchpadGesturesInternalDialog(buttonWindowController2.mContext, buttonWindowController2.itemList, buttonWindowController2.mNightMode).show();
                                CoreSaLogger.logForDexWithScreenId("703", "7014");
                                break;
                            }
                            break;
                        default:
                            FragmentActivity fragmentActivity2 = buttonWindowController.mActivity;
                            if (fragmentActivity2 != null) {
                                fragmentActivity2.finish();
                                CoreSaLogger.logForDexWithScreenId("703", "7015");
                                break;
                            }
                            break;
                    }
                }
            };
            buttonWindow.setup(fragmentActivity, viewFindViewById2);
            buttonWindowController.itemList = new TouchpadGesturesGuideItems(buttonWindowController.mContext);
        }
        View viewFindViewById3 = view.findViewById(R.id.touchpad_close_button);
        if (viewFindViewById3 != null) {
            if (buttonWindowController.mCloseButtonWindow == null) {
                buttonWindowController.mCloseButtonWindow = new ButtonWindow(TouchpadButtonItems.CLOSE, buttonWindowController.mSpenNotSupportedToastBlocked);
            }
            ButtonWindow buttonWindow2 = buttonWindowController.mCloseButtonWindow;
            final int i2 = 1;
            buttonWindow2.mOnGestureListener = new ButtonWindow.OnGestureListener() { // from class: com.android.systemui.dextouchpad.activity.ButtonWindowController$$ExternalSyntheticLambda0
                @Override // com.android.systemui.dextouchpad.activity.ButtonWindow.OnGestureListener
                public final void onDoubleTap() {
                    switch (i2) {
                        case 0:
                            ButtonWindowController buttonWindowController2 = buttonWindowController;
                            if (buttonWindowController2.mActivity != null) {
                                buttonWindowController2.mNightMode = (buttonWindowController2.mContext.getResources().getConfiguration().uiMode & 48) == 32;
                                new TouchpadGesturesInternalDialog(buttonWindowController2.mContext, buttonWindowController2.itemList, buttonWindowController2.mNightMode).show();
                                CoreSaLogger.logForDexWithScreenId("703", "7014");
                                break;
                            }
                            break;
                        default:
                            FragmentActivity fragmentActivity2 = buttonWindowController.mActivity;
                            if (fragmentActivity2 != null) {
                                fragmentActivity2.finish();
                                CoreSaLogger.logForDexWithScreenId("703", "7015");
                                break;
                            }
                            break;
                    }
                }
            };
            buttonWindow2.setup(fragmentActivity, viewFindViewById3);
        }
        TouchpadViewMover touchpadViewMover = this.mViewModel.mTouchpadViewMover;
        View viewFindViewById4 = view.findViewById(R.id.touchpad);
        touchpadViewMover.getClass();
        touchpadViewMover.mView = new WeakReference(viewFindViewById4);
    }

    public final void updateSpenRecognitionArea() {
        int i;
        int i2;
        TouchpadNotificationManager touchpadNotificationManager;
        boolean z = Features.DEBUG;
        if (z) {
            Log.d("DexTouchpadFragment", "updateSpenRecognitionArea()");
        }
        boolean z2 = Settings.System.getIntForUser(this.mApplicationContext.getContentResolver(), "pen_digitizer_enabled", 0, -2) == 1;
        int i3 = Settings.Global.getInt(this.mApplicationContext.getContentResolver(), "SPEN_INPUT_MODE_DEX", 0);
        if (z2) {
            final TouchpadWindow touchpadWindow = this.mViewModel.mTouchpadWindow;
            touchpadWindow.mSpenMode = i3;
            if (Utils.mIsSpenDetached && (touchpadNotificationManager = touchpadWindow.mTouchpadNotificationManager) != null) {
                touchpadNotificationManager.show(NotificationType.SPEN);
            }
            touchpadWindow.mWindowView.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.dextouchpad.activity.TouchpadWindow$$ExternalSyntheticLambda0
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) throws Resources.NotFoundException {
                    TouchpadWindow touchpadWindow2 = touchpadWindow;
                    touchpadWindow2.getClass();
                    if (motionEvent.getAction() == 9 && motionEvent.isFromSource(16386)) {
                        if (!touchpadWindow2.mSpenNotSupportedToastBlocked.get() && touchpadWindow2.mSpenMode == 0) {
                            FragmentActivity fragmentActivity = touchpadWindow2.mActivity;
                            if (touchpadWindow2.mSpenNotSupportedToast == null || !touchpadWindow2.mSpenNotSupportedToastShown.get()) {
                                Toast toast = touchpadWindow2.mSpenNotSupportedToast;
                                TouchpadWindow.AnonymousClass1 anonymousClass1 = touchpadWindow2.mToastCallback;
                                if (toast != null) {
                                    toast.removeCallback(anonymousClass1);
                                    touchpadWindow2.mSpenNotSupportedToast.cancel();
                                }
                                touchpadWindow2.mSpenNotSupportedToastShown.set(false);
                                Toast toastMakeText = Toast.makeText(fragmentActivity, touchpadWindow2.mActivity.getResources().getConfiguration().orientation == 1 ? R.string.dex_spen_unsupported : R.string.dex_spen_unsupported_portrait, 1);
                                touchpadWindow2.mSpenNotSupportedToast = toastMakeText;
                                toastMakeText.addCallback(anonymousClass1);
                                touchpadWindow2.mSpenNotSupportedToast.show();
                                CoreSaLogger.logForDexWithScreenId("704", "7005");
                            }
                        }
                        TouchpadNotificationManager touchpadNotificationManager2 = touchpadWindow2.mTouchpadNotificationManager;
                        if (touchpadNotificationManager2 != null && !Utils.mIsSpenDetached) {
                            touchpadNotificationManager2.show(NotificationType.SPEN);
                        }
                        Utils.mIsSpenDetached = true;
                    }
                    return false;
                }
            });
        } else {
            this.mViewModel.mTouchpadWindow.unsetOnHoverListener();
        }
        int i4 = this.mActivity.getResources().getConfiguration().orientation;
        boolean z3 = i4 != 2 ? (i = this.mDexDisplayRotation) == 1 || i == 3 : (i2 = this.mDexDisplayRotation) == 0 || i2 == 2;
        RecyclerView$$ExternalSyntheticOutline0.m(this.mDexDisplayRotation, "DexTouchpadFragment", KeyguardFMMViewController$$ExternalSyntheticOutline0.m("rotationMatch=", i4, ", orientation=", z3, ", mDexDisplayRotation="));
        if (this.mSpenRecognitionArea == null || this.mApplicationContext.getResources().getConfiguration().semDisplayDeviceType == 5 || !z3) {
            if (this.mViewModel.mSpenStartTime > 0) {
                CoreSaLogger.logForDexWithScreenId("701", "7003", (System.currentTimeMillis() - this.mViewModel.mSpenStartTime) / 1000);
                this.mViewModel.mSpenStartTime = 0L;
            }
            View view = this.mSpenRecognitionArea;
            if (view != null) {
                view.setVisibility(8);
            }
            if (this.mTouchpadAutoRunTextView.getVisibility() == 0) {
                this.mTouchpadAutoRunTextView.setBackgroundResource(R.color.touchpad_auto_run_background);
            }
            FloatingWindow floatingWindow = this.mViewModel.mSpenRecognitionWindow;
            if (floatingWindow != null) {
                floatingWindow.tearDown();
            }
            ViewHideScheduler viewHideScheduler = this.mSpenIconViewHideScheduler;
            if (viewHideScheduler != null) {
                viewHideScheduler.cancel();
                this.mSpenIconViewHideScheduler = null;
            }
            this.mViewModel.mTouchpadViewMover.setSpenEnabled(false);
            this.mViewModel.mButtonWindowController.setRotationButtonVisibility(true);
            return;
        }
        this.mViewModel.mTouchpadWindow.unsetOnHoverListener();
        this.mSpenRecognitionArea.removeCallbacks(this.mRemoveSpenRecognitionAreaRunnable);
        if (!z2 || i3 != 0) {
            if (this.mViewModel.mSpenStartTime > 0) {
                CoreSaLogger.logForDexWithScreenId("701", "7003", (System.currentTimeMillis() - this.mViewModel.mSpenStartTime) / 1000);
                this.mViewModel.mSpenStartTime = 0L;
            }
            this.mSpenRecognitionArea.setVisibility(8);
            if (this.mTouchpadAutoRunTextView.getVisibility() == 0) {
                this.mTouchpadAutoRunTextView.setBackgroundResource(R.color.touchpad_auto_run_background);
            }
            FloatingWindow floatingWindow2 = this.mViewModel.mSpenRecognitionWindow;
            if (floatingWindow2 != null) {
                floatingWindow2.tearDown();
            }
            ViewHideScheduler viewHideScheduler2 = this.mSpenIconViewHideScheduler;
            if (viewHideScheduler2 != null) {
                viewHideScheduler2.cancel();
                this.mSpenIconViewHideScheduler = null;
            }
            this.mViewModel.mTouchpadViewMover.setSpenEnabled(false);
            this.mViewModel.mButtonWindowController.setRotationButtonVisibility(true);
            return;
        }
        this.mViewModel.mSpenStartTime = System.currentTimeMillis();
        CoreSaLogger.logForDexWithScreenId("701", "7002");
        this.mSpenRecognitionArea.setVisibility(0);
        if (this.mTouchpadAutoRunTextView.getVisibility() == 0) {
            this.mTouchpadAutoRunTextView.setBackground(null);
        }
        TouchpadViewModel touchpadViewModel = this.mViewModel;
        FloatingWindow floatingWindow3 = (FloatingWindow) touchpadViewModel.mSpenRecognitionWindowLazy.get();
        touchpadViewModel.mSpenRecognitionWindow = floatingWindow3;
        floatingWindow3.setup(this.mActivity, this.mSpenRecognitionArea);
        if (this.mSpenIcon != null && this.mSpenIconViewHideScheduler == null) {
            final ViewHideScheduler viewHideScheduler3 = new ViewHideScheduler(this.mSpenIcon, "SpenIcon");
            this.mSpenIconViewHideScheduler = viewHideScheduler3;
            if (z) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("sendDelayedRemoveIcon(), "), viewHideScheduler3.mTitle, "DexTouchpadViewHideScheduler");
            }
            ImageView imageView = viewHideScheduler3.mIcon;
            if (imageView != null) {
                imageView.setVisibility(0);
                Handler handler = viewHideScheduler3.mHandler;
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() { // from class: com.android.systemui.dextouchpad.activity.ViewHideScheduler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewHideScheduler viewHideScheduler4 = viewHideScheduler3;
                        int i5 = ViewHideScheduler.$r8$clinit;
                        viewHideScheduler4.getClass();
                        if (Features.DEBUG) {
                            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("removeIcon(), "), viewHideScheduler4.mTitle, "DexTouchpadViewHideScheduler");
                        }
                        ImageView imageView2 = viewHideScheduler4.mIcon;
                        if (imageView2 == null || !imageView2.isShown()) {
                            return;
                        }
                        viewHideScheduler4.mIcon.setVisibility(8);
                    }
                }, ViewHideScheduler.ICON_REMOVE_DELAY);
            }
        }
        this.mViewModel.mTouchpadViewMover.setSpenEnabled(true);
        this.mViewModel.mButtonWindowController.setRotationButtonVisibility(false);
    }
}
