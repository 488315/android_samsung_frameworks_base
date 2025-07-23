package com.android.wm.shell.controlpanel.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.SemWallpaperColors;
import android.app.TaskStackListener;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.BrightnessInfo;
import android.hardware.input.InputManager;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Lifecycle;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.controlpanel.GridPanelAdapter;
import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import com.android.wm.shell.controlpanel.action.FlexPanelSettingsAction;
import com.android.wm.shell.controlpanel.action.GridItems;
import com.android.wm.shell.controlpanel.action.MenuActionType;
import com.android.wm.shell.controlpanel.action.QuickPanelAction;
import com.android.wm.shell.controlpanel.action.QuickSettingsAction;
import com.android.wm.shell.controlpanel.action.ScreenCaptureAction;
import com.android.wm.shell.controlpanel.action.ScreenRecordAction;
import com.android.wm.shell.controlpanel.action.SplitScreenAction;
import com.android.wm.shell.controlpanel.action.TouchPadAction;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.audio.AudioCallback;
import com.android.wm.shell.controlpanel.utils.CheckControlWindowState;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.android.wm.shell.controlpanel.widget.BrightnessVolumeView;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;
import com.samsung.android.widget.SemTipPopup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FlexPanelActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, View.OnDragListener {
    public static final int mEditPanelItemSize;
    public static final boolean mIsFold;
    public static FlexPanelActivity sFlexPanelActivity;
    public Interpolator i_22_25_0_1;
    public ArrayList mActions;
    public ActivityManager mActivityManager;
    public int mBasicGridViewHeight;
    public int mBasicGridViewWidth;
    public BrightnessVolumeView mBrightnessVolumeView;
    public boolean mCloseState;
    public Map mCustomDimen;
    public DeviceStateManager mDeviceStateManager;
    public ControlPanelAction.Action mDraggedAction;
    public ArrayList mEditActions;
    public EventReceiver mEventReceiver;
    public Animation mFadeIn;
    public Animation mFadeOut;
    public FlexMediaPanel mFlexMediaPanel;
    public GridPanelAdapter mGridAdapter;
    public GridLayout mGridLayout;
    public GridView mGridView;
    public InputMonitor mInputMonitor;
    public boolean mIsDisplayTouchPad;
    public boolean mIsEditPanel;
    public boolean mIsMediaPanel;
    public MediaController mMediaController;
    public MediaSessionManager mMediaSessionManager;
    public LinearLayout mMediaView;
    public FlexPanelActivity mOwnActivity;
    public boolean mPanelInit;
    public LinearLayout mPanelView;
    public int mPrevOrientation;
    public SharedPreferences mSharedPreferences;
    public Animation mSliderIn;
    public Animation mSliderOut;
    public ToolbarTipPopup mToolbarTipPopup;
    public boolean mTooltipInit;
    public TouchPad mTouchPad;
    public TouchPadMediaPanel mTouchPadMediaPanel;
    public LinearLayout mUpperArea;
    public int mX;
    public int mY;
    public int mBrightnessVolumeType = -1;
    public long mDragEnteredTime = 0;
    public boolean mForceTouchPadRemoved = false;
    public boolean mPendingShowTouchPad = false;
    public boolean mWindowAttached = false;
    public boolean mOnDragAnimation = false;
    public boolean mOnDragEnded = false;
    public boolean mIsResumeCalled = false;
    public final AnonymousClass1 mReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.1
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            char c;
            String action = intent.getAction();
            action.getClass();
            switch (action.hashCode()) {
                case -1940635523:
                    if (action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -806437194:
                    if (action.equals("com.samsung.android.app.screenrecorder.on")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -494116597:
                    if (action.equals("android.intent.action.COLLAPSE_FLEX_PANEL")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -403228793:
                    if (action.equals(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 770250616:
                    if (action.equals("com.samsung.android.app.screenrecorder.off")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (!ControlPanelUtils.isClockActivity(context)) {
                        BrightnessVolumeView brightnessVolumeView = FlexPanelActivity.this.mBrightnessVolumeView;
                        int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                        int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
                        int intExtra3 = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
                        if (!brightnessVolumeView.mVolumeSeekBarTracking) {
                            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(intExtra, intExtra2, "setVolumeProgress streamType : ", ", newVolume : ", ", oldVolume : ");
                            m.append(intExtra3);
                            Log.i("BrightnessVolumeView", m.toString());
                            brightnessVolumeView.setVolumeSeekBar(intExtra);
                            brightnessVolumeView.setVolumeIcon(intExtra);
                            break;
                        }
                    }
                    break;
                case 1:
                    FlexPanelActivity.this.getClass();
                    break;
                case 2:
                    if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                        String str = ControlPanelUtils.TALKBACK_SERVICE;
                        ControlPanelUtils.eventLogging("F004", "a", new HashMap());
                    }
                    FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                    int i = FlexPanelActivity.mEditPanelItemSize;
                    flexPanelActivity.closeOperation();
                    break;
                case 3:
                    if ("recentapps".equalsIgnoreCase(intent.getStringExtra("reason"))) {
                        FlexPanelActivity.this.mGridAdapter.notifyDataSetChanged();
                        break;
                    }
                    break;
                case 4:
                    FlexPanelActivity.this.getClass();
                    break;
            }
        }
    };
    public final AnonymousClass2 mBrightnessObserver = new ContentObserver(new Handler()) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.2
        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            int i = Settings.System.getInt(FlexPanelActivity.this.getContentResolver(), "screen_brightness", 0);
            BrightnessVolumeView brightnessVolumeView = FlexPanelActivity.this.mBrightnessVolumeView;
            brightnessVolumeView.mBrightnessSeekBar.setProgress(i);
            if (brightnessVolumeView.mBrightnessSeekBar.getVisibility() == 0) {
                brightnessVolumeView.setBrightnessViewColor(i);
            }
        }
    };
    public final AnonymousClass3 mModeEnableObserver = new AnonymousClass3(new Handler());
    public final AnonymousClass4 mTalkbackObserver = new ContentObserver(new Handler()) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.4
        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            String string = Settings.Secure.getString(FlexPanelActivity.this.getContentResolver(), SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES);
            if (string != null) {
                string.contains(ControlPanelUtils.TALKBACK_SERVICE);
            }
            int i = FlexPanelActivity.mEditPanelItemSize;
            int i2 = Settings.Global.getInt(FlexPanelActivity.this.mOwnActivity.getContentResolver(), "flex_mode_scroll_wheel_pos", 2);
            if (i2 == -1 || i2 == 0) {
                return;
            }
            FlexPanelActivity.this.removeTouchPad(false);
            FlexPanelActivity.this.displayTouchPadIfNeed();
        }
    };
    public final AnonymousClass5 mDeviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.5
        public final void onDeviceStateChanged(DeviceState deviceState) {
            int i = 2;
            if (deviceState.getConfiguration().getPhysicalProperties().contains(2)) {
                return;
            }
            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
            int i2 = FlexPanelActivity.mEditPanelItemSize;
            flexPanelActivity.getClass();
            if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                ControlPanelUtils.eventLogging("F004", "b", new HashMap());
            }
            if (flexPanelActivity.mOwnActivity.semIsResumed()) {
                flexPanelActivity.runOnUiThread(new FlexPanelActivity$$ExternalSyntheticLambda1(flexPanelActivity, i));
                return;
            }
            flexPanelActivity.removeTouchPad(true);
            ActivityManager.RunningTaskInfo runningTaskExcept = ControlPanelUtils.getRunningTaskExcept(flexPanelActivity.mOwnActivity);
            if (flexPanelActivity.lifecycleRegistry.state != Lifecycle.State.DESTROYED && flexPanelActivity.mIsResumeCalled && runningTaskExcept != null && runningTaskExcept.topActivityType != 2) {
                MultiWindowManager.getInstance().dismissSplitTask(flexPanelActivity.getActivityToken(), false);
            }
            flexPanelActivity.finish();
            FlexDimActivity flexDimActivity = FlexDimActivity.sFlexDimActivity;
            if (flexDimActivity == null || flexDimActivity.isFinishing()) {
                return;
            }
            FlexDimActivity.sFlexDimActivity.finish();
        }
    };
    public final Impl mTaskStackListener = new Impl();
    public final H mDimHandler = new H();
    public final FlexPanelActivity$$ExternalSyntheticLambda3 mActiveSessionsChangedListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda3
        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public final void onActiveSessionsChanged(List list) {
            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
            int i = FlexPanelActivity.mEditPanelItemSize;
            Log.i("FlexPanelActivity", "onActiveSessionsChanged closeState : " + flexPanelActivity.mCloseState + " controllers : " + list);
            if (flexPanelActivity.mCloseState) {
                return;
            }
            flexPanelActivity.checkActiveSession();
        }
    };
    public final AnonymousClass20 mCallback = new AnonymousClass20();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$16, reason: invalid class name */
    public class AnonymousClass16 implements ViewTreeObserver.OnGlobalLayoutListener {
        public final /* synthetic */ LinearLayout val$editPanelView;

        public AnonymousClass16(LinearLayout linearLayout) {
            this.val$editPanelView = linearLayout;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            FlexPanelActivity.this.mPanelView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            if (FlexPanelActivity.this.findViewById(R.id.gridLayout) != null) {
                final FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                final LinearLayout linearLayout = flexPanelActivity.mPanelView;
                final int width = linearLayout.getWidth();
                final int height = linearLayout.getHeight();
                linearLayout.getLayoutParams().width = flexPanelActivity.mBasicGridViewWidth;
                linearLayout.getLayoutParams().height = flexPanelActivity.mBasicGridViewHeight;
                linearLayout.requestLayout();
                Animation animation = new Animation() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.17
                    @Override // android.view.animation.Animation
                    public final void applyTransformation(float f, Transformation transformation) {
                        linearLayout.getLayoutParams().width = FlexPanelActivity.this.mBasicGridViewWidth + ((int) ((width - r0) * f));
                        linearLayout.getLayoutParams().height = FlexPanelActivity.this.mBasicGridViewHeight + ((int) ((height - r0) * f));
                        if (f == 1.0f) {
                            if (FlexPanelActivity.mIsFold) {
                                linearLayout.getLayoutParams().width = -2;
                            } else {
                                linearLayout.getLayoutParams().height = -2;
                            }
                        }
                        linearLayout.requestLayout();
                    }

                    @Override // android.view.animation.Animation
                    public final boolean willChangeBounds() {
                        return true;
                    }
                };
                animation.setInterpolator(flexPanelActivity.i_22_25_0_1);
                animation.setDuration(450L);
                linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(flexPanelActivity, linearLayout, animation) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.18
                    public final /* synthetic */ Animation val$a;
                    public final /* synthetic */ View val$v;

                    {
                        this.val$v = linearLayout;
                        this.val$a = animation;
                    }

                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        this.val$v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        this.val$v.setBackgroundResource(R.drawable.flex_panel_background_expand);
                        this.val$v.findViewById(R.id.upper_area).setBackground(null);
                        this.val$v.startAnimation(this.val$a);
                    }
                });
            }
            Handler handler = new Handler();
            final LinearLayout linearLayout2 = this.val$editPanelView;
            handler.postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$16$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FlexPanelActivity.AnonymousClass16 anonymousClass16 = FlexPanelActivity.AnonymousClass16.this;
                    LinearLayout linearLayout3 = linearLayout2;
                    if (FlexPanelActivity.this.findViewById(R.id.edit_panel_view) != null) {
                        linearLayout3.setVisibility(0);
                        linearLayout3.startAnimation(FlexPanelActivity.this.mSliderIn);
                    }
                }
            }, 100L);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$20, reason: invalid class name */
    public class AnonymousClass20 extends AudioCallback {
        public AnonymousClass20() {
        }

        public final void onMediaControllerConnected(MediaController mediaController) {
            if (mediaController == null) {
                Log.e("FlexPanelActivity", "FlexPanelActivity mCallback onMediaControllerConnected mMediaController == null");
                return;
            }
            Log.i("FlexPanelActivity", "FlexPanelActivity mCallback onMediaControllerConnected");
            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
            FlexMediaPanel flexMediaPanel = flexPanelActivity.mFlexMediaPanel;
            if (flexMediaPanel != null) {
                flexMediaPanel.updateMediaPanel();
                return;
            }
            TouchPadMediaPanel touchPadMediaPanel = flexPanelActivity.mTouchPadMediaPanel;
            if (touchPadMediaPanel != null) {
                touchPadMediaPanel.updateTouchPadMediaPanel();
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            Log.i("FlexPanelActivity", "FlexPanelActivity mCallback onMetadataChanged");
            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
            int i = FlexPanelActivity.mEditPanelItemSize;
            flexPanelActivity.checkActiveSession();
            FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
            FlexMediaPanel flexMediaPanel = flexPanelActivity2.mFlexMediaPanel;
            if (flexMediaPanel != null) {
                flexMediaPanel.updateMediaPanel();
                FlexPanelActivity.this.mFlexMediaPanel.mMetadataChanged = true;
            } else {
                TouchPadMediaPanel touchPadMediaPanel = flexPanelActivity2.mTouchPadMediaPanel;
                if (touchPadMediaPanel != null) {
                    touchPadMediaPanel.updateTouchPadMediaPanel();
                }
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            try {
                boolean isSupportButton = CheckControlWindowState.isSupportButton(FlexPanelActivity.this.mMediaController);
                boolean z = FlexPanelActivity.this.mMediaView.getVisibility() == 0;
                Log.i("FlexPanelActivity", "FlexPanelActivity mCallback onPlaybackStateChanged isSupportButton : " + isSupportButton + ", isVisible : " + z);
                if (isSupportButton && !z) {
                    FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                    if (flexPanelActivity.mTouchPad == null && !flexPanelActivity.mIsDisplayTouchPad && flexPanelActivity.semIsResumed()) {
                        FlexPanelActivity.this.mMediaView.setVisibility(0);
                        FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                        flexPanelActivity2.mMediaView.startAnimation(flexPanelActivity2.mFadeIn);
                    }
                }
                FlexPanelActivity flexPanelActivity3 = FlexPanelActivity.this;
                FlexMediaPanel flexMediaPanel = flexPanelActivity3.mFlexMediaPanel;
                if (flexMediaPanel != null) {
                    flexMediaPanel.updateMediaPanel();
                    return;
                }
                TouchPadMediaPanel touchPadMediaPanel = flexPanelActivity3.mTouchPadMediaPanel;
                if (touchPadMediaPanel != null) {
                    touchPadMediaPanel.updateTouchPadMediaPanel();
                }
            } catch (NullPointerException unused) {
                Log.i("FlexPanelActivity", "FlexPanelActivity mCallback onPlaybackStateChanged mediaController is null");
            }
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            Log.i("FlexPanelActivity", "FlexPanelActivity mCallback onSessionDestroyed");
            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
            FlexMediaPanel flexMediaPanel = flexPanelActivity.mFlexMediaPanel;
            if (flexMediaPanel != null) {
                flexMediaPanel.clearController();
            } else {
                TouchPadMediaPanel touchPadMediaPanel = flexPanelActivity.mTouchPadMediaPanel;
                if (touchPadMediaPanel != null) {
                    Log.i("TouchPadMediaPanel", "TouchPadMediaPanel clearController");
                    if (touchPadMediaPanel.mMediaController != null) {
                        touchPadMediaPanel.mMediaController = null;
                    }
                }
            }
            FlexPanelActivity.this.mMediaController = null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$3, reason: invalid class name */
    public class AnonymousClass3 extends ContentObserver {
        public AnonymousClass3(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (Settings.Global.getInt(FlexPanelActivity.this.getContentResolver(), "flex_mode_panel_enabled", 1) != 1) {
                FlexPanelActivity.this.runOnUiThread(new FlexPanelActivity$$ExternalSyntheticLambda4(this, 1));
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class EventReceiver extends InputEventReceiver {
        public EventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        public final void onInputEvent(InputEvent inputEvent) {
            if (!(inputEvent instanceof MotionEvent)) {
                finishInputEvent(inputEvent, false);
                return;
            }
            MotionEvent motionEvent = (MotionEvent) inputEvent;
            FlexPanelActivity.this.mDimHandler.removeMessages(1);
            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
            if (flexPanelActivity.mIsMediaPanel && flexPanelActivity.mFlexMediaPanel != null) {
                flexPanelActivity.mDimHandler.sendEmptyMessageDelayed(1, 5000L);
            }
            FlexPanelActivity.this.getClass();
            if ((motionEvent.getFlags() & 1048576) != 0) {
                FlexPanelActivity.this.getClass();
            }
            finishInputEvent(inputEvent, true);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class H extends Handler {
        public H() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                StringBuilder sb = new StringBuilder("Cancel FlexDimActivity: mIsMediaPanel=");
                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                sb.append(flexPanelActivity.mIsMediaPanel);
                sb.append(" mImmersiveState=0 mFlexMediaPanel=");
                sb.append(flexPanelActivity.mFlexMediaPanel != null);
                sb.append(" sFlexPanelActivity=");
                sb.append(FlexPanelActivity.sFlexPanelActivity != null);
                sb.append(" sFlexDimActivity=");
                sb.append(FlexDimActivity.sFlexDimActivity != null);
                sb.append(" usingTalkBack=false");
                Log.d("FlexPanelActivity", sb.toString());
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Impl extends TaskStackListener {
        public Impl() {
        }

        public final void onTaskFocusChanged(int i, boolean z) {
            List<ActivityManager.RunningTaskInfo> runningTasks = FlexPanelActivity.this.mActivityManager.getRunningTasks(2);
            if (runningTasks.get(0).semIsFreeform() || 2 == runningTasks.get(0).getWindowingMode()) {
                return;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = "com.android.wm.shell.controlpanel.activity.FlexPanelActivity".equalsIgnoreCase(runningTasks.get(0).baseActivity.getShortClassName()) ? runningTasks.get(1) : runningTasks.get(0);
            String packageName = runningTaskInfo.baseActivity.getPackageName();
            ActivityManager.RunningTaskInfo runningTaskExcept = ControlPanelUtils.getRunningTaskExcept(FlexPanelActivity.this.mOwnActivity);
            int i2 = runningTaskExcept != null ? runningTaskExcept.userId : 0;
            if (MultiWindowUtils.isKeepFlexPanelTask(packageName) || (SemWindowManager.getInstance().getSupportsFlexPanel(i2, packageName) & 2) == 0) {
                final int i3 = 2;
                FlexPanelActivity.this.runOnUiThread(new Runnable(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$Impl$$ExternalSyntheticLambda0
                    public final /* synthetic */ FlexPanelActivity.Impl f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i4 = i3;
                        FlexPanelActivity.Impl impl = this.f$0;
                        switch (i4) {
                            case 0:
                                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                                int i5 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    String str = ControlPanelUtils.TALKBACK_SERVICE;
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity.mCloseState = true;
                                break;
                            case 1:
                                FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                                int i6 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity2.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    String str2 = ControlPanelUtils.TALKBACK_SERVICE;
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity2.mCloseState = true;
                                flexPanelActivity2.removeTouchPad(true);
                                break;
                            default:
                                FlexPanelActivity flexPanelActivity3 = FlexPanelActivity.this;
                                int i7 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity3.checkActiveSession();
                                break;
                        }
                    }
                });
            } else if (runningTaskInfo.topActivityType == 2) {
                final int i4 = 0;
                FlexPanelActivity.this.runOnUiThread(new Runnable(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$Impl$$ExternalSyntheticLambda0
                    public final /* synthetic */ FlexPanelActivity.Impl f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i42 = i4;
                        FlexPanelActivity.Impl impl = this.f$0;
                        switch (i42) {
                            case 0:
                                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                                int i5 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    String str = ControlPanelUtils.TALKBACK_SERVICE;
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity.mCloseState = true;
                                break;
                            case 1:
                                FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                                int i6 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity2.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    String str2 = ControlPanelUtils.TALKBACK_SERVICE;
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity2.mCloseState = true;
                                flexPanelActivity2.removeTouchPad(true);
                                break;
                            default:
                                FlexPanelActivity flexPanelActivity3 = FlexPanelActivity.this;
                                int i7 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity3.checkActiveSession();
                                break;
                        }
                    }
                });
            } else {
                final int i5 = 1;
                FlexPanelActivity.this.runOnUiThread(new Runnable(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$Impl$$ExternalSyntheticLambda0
                    public final /* synthetic */ FlexPanelActivity.Impl f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i42 = i5;
                        FlexPanelActivity.Impl impl = this.f$0;
                        switch (i42) {
                            case 0:
                                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                                int i52 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    String str = ControlPanelUtils.TALKBACK_SERVICE;
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity.mCloseState = true;
                                break;
                            case 1:
                                FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                                int i6 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity2.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    String str2 = ControlPanelUtils.TALKBACK_SERVICE;
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity2.mCloseState = true;
                                flexPanelActivity2.removeTouchPad(true);
                                break;
                            default:
                                FlexPanelActivity flexPanelActivity3 = FlexPanelActivity.this;
                                int i7 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity3.checkActiveSession();
                                break;
                        }
                    }
                });
            }
        }
    }

    static {
        mEditPanelItemSize = ControlPanelUtils.isTypeFold() ? 5 : 4;
        mIsFold = ControlPanelUtils.isTypeFold();
    }

    public static int getRatioSize(double d, int i) {
        return (int) ((i * d) / 100.0d);
    }

    public static void setDragAnimation(AnimatorSet animatorSet, View view, View view2) {
        animatorSet.playTogether(ObjectAnimator.ofFloat(view2, "x", view2.getLeft(), view.getLeft()));
        animatorSet.playTogether(ObjectAnimator.ofFloat(view2, "y", view2.getTop(), view.getTop()));
    }

    public final void addEditPanelNone() {
        int size = this.mEditActions.size();
        int i = mEditPanelItemSize;
        int i2 = 0;
        if (size > i) {
            while (i2 < (mEditPanelItemSize * 2) - size) {
                onActionArrayAdd("edit_panel_action_list", this.mEditActions.size(), ControlPanelAction.Action.None);
                i2++;
            }
        } else if (size < i) {
            while (i2 < mEditPanelItemSize - size) {
                onActionArrayAdd("edit_panel_action_list", this.mEditActions.size(), ControlPanelAction.Action.None);
                i2++;
            }
        }
    }

    public final void buttonLoggingByString(int i, Map map) {
        ControlPanelUtils.eventLogging(getPreferences("MEDIA_PANEL") ? "F003" : "F002", getString(i), map);
        ControlPanelUtils.eventLogging("F005", getString(i), map);
    }

    public final void checkActiveSession() {
        List<ActivityManager.RunningTaskInfo> runningTasks = this.mActivityManager.getRunningTasks(1);
        if (runningTasks.get(0).semIsFreeform() || 2 == runningTasks.get(0).getWindowingMode()) {
            return;
        }
        final int[] iArr = {0};
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.13
            @Override // java.lang.Runnable
            public final void run() {
                if (FlexPanelActivity.sFlexPanelActivity == null) {
                    FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                    MediaController mediaController = flexPanelActivity.mMediaController;
                    if (mediaController != null) {
                        mediaController.unregisterCallback(flexPanelActivity.mCallback);
                        return;
                    }
                    return;
                }
                MediaController mediaController2 = FlexPanelActivity.this.mMediaController;
                if (mediaController2 == null && iArr[0] < 5) {
                    Log.i("FlexPanelActivity", "handler postDelayed mMediaController == null count : " + Arrays.toString(iArr));
                    FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                    flexPanelActivity2.mMediaController = CheckControlWindowState.getMediaController(flexPanelActivity2.mOwnActivity, flexPanelActivity2.mMediaSessionManager);
                    int[] iArr2 = iArr;
                    iArr2[0] = iArr2[0] + 1;
                    handler.postDelayed(this, 200L);
                } else if (mediaController2 != null) {
                    Log.i("FlexPanelActivity", "handler postDelayed mMediaController != null");
                    FlexPanelActivity flexPanelActivity3 = FlexPanelActivity.this;
                    flexPanelActivity3.mMediaController.registerCallback(flexPanelActivity3.mCallback);
                    FlexPanelActivity flexPanelActivity4 = FlexPanelActivity.this;
                    if (CheckControlWindowState.getMediaController(flexPanelActivity4.mOwnActivity, flexPanelActivity4.mMediaSessionManager) == null) {
                        FlexPanelActivity.this.mMediaController = null;
                    }
                }
                FlexPanelActivity flexPanelActivity5 = FlexPanelActivity.this;
                boolean isMediaPanelRequestedState = CheckControlWindowState.isMediaPanelRequestedState(flexPanelActivity5.mOwnActivity, flexPanelActivity5.mMediaController);
                Log.i("FlexPanelActivity", "FlexPanelActivity checkActiveSession isMediaPanel : " + FlexPanelActivity.this.mIsMediaPanel + ", isMediaPanelRequestedState : " + isMediaPanelRequestedState);
                FlexPanelActivity flexPanelActivity6 = FlexPanelActivity.this;
                if (!flexPanelActivity6.mIsMediaPanel) {
                    if (!isMediaPanelRequestedState || flexPanelActivity6.mIsEditPanel) {
                        return;
                    }
                    Log.i("FlexPanelActivity", "FlexPanelActivity checkActiveSession GridFloating hasActiveSessions");
                    FlexPanelActivity flexPanelActivity7 = FlexPanelActivity.this;
                    FlexMediaPanel flexMediaPanel = flexPanelActivity7.mFlexMediaPanel;
                    if (flexMediaPanel != null) {
                        flexMediaPanel.clearController();
                    } else {
                        TouchPadMediaPanel touchPadMediaPanel = flexPanelActivity7.mTouchPadMediaPanel;
                        if (touchPadMediaPanel != null) {
                            Log.i("TouchPadMediaPanel", "TouchPadMediaPanel clearController");
                            if (touchPadMediaPanel.mMediaController != null) {
                                touchPadMediaPanel.mMediaController = null;
                            }
                        }
                    }
                    FlexPanelActivity.this.setupMediaPanel();
                    return;
                }
                if (!isMediaPanelRequestedState) {
                    Log.i("FlexPanelActivity", "FlexPanelActivity checkActiveSession MediaFloating no hasActiveSessions");
                    FlexPanelActivity flexPanelActivity8 = FlexPanelActivity.this;
                    if (flexPanelActivity8.mTouchPadMediaPanel != null) {
                        flexPanelActivity8.removeTouchPad(false);
                    }
                    FlexPanelActivity.this.setupBasicPanel();
                    return;
                }
                FlexMediaPanel flexMediaPanel2 = flexPanelActivity6.mFlexMediaPanel;
                if (flexMediaPanel2 != null) {
                    MediaController mediaController3 = flexPanelActivity6.mMediaController;
                    Log.i("FlexMediaPanel", "MediaPanel setMediaController");
                    if (flexMediaPanel2.mMediaController != mediaController3) {
                        flexMediaPanel2.mMediaController = mediaController3;
                        return;
                    }
                    return;
                }
                TouchPadMediaPanel touchPadMediaPanel2 = flexPanelActivity6.mTouchPadMediaPanel;
                if (touchPadMediaPanel2 != null) {
                    MediaController mediaController4 = flexPanelActivity6.mMediaController;
                    Log.i("TouchPadMediaPanel", "TouchPadMediaPanel setMediaController");
                    if (touchPadMediaPanel2.mMediaController != mediaController4) {
                        touchPadMediaPanel2.mMediaController = mediaController4;
                    }
                }
            }
        }, 200L);
    }

    public final int checkFromValueNone(int i) {
        if (getActionByPosition(i) == ControlPanelAction.Action.None) {
            for (int i2 = 0; i2 < this.mEditActions.size(); i2++) {
                int i3 = i2 + 10;
                if (getActionByPosition(i3) == ControlPanelAction.Action.DragCircle) {
                    return i3;
                }
            }
        }
        return i;
    }

    public final int checkToValueNone(int i, boolean z) {
        if (getActionByPosition(i) == ControlPanelAction.Action.None) {
            for (int i2 = 0; i2 < this.mEditActions.size(); i2++) {
                int i3 = i2 + 10;
                if (getActionByPosition(i3) == ControlPanelAction.Action.None) {
                    return z ? i2 + 9 : i3;
                }
            }
        }
        return i;
    }

    public final void closeOperation() {
        removeTouchPad(true);
        this.mGridAdapter.notifyDataSetChanged();
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        this.mPanelView.startAnimation(loadAnimation);
        if (this.mIsMediaPanel) {
            this.mMediaView.startAnimation(loadAnimation);
        }
        this.mCloseState = true;
        finish();
    }

    public final LinearLayout createEditButton(ControlPanelAction.Action action) {
        LinearLayout.LayoutParams ratioLayoutParams;
        LinearLayout linearLayout = new LinearLayout(this);
        boolean z = mIsFold;
        linearLayout.setOrientation(!z ? 1 : 0);
        linearLayout.setGravity(17);
        TextView textView = new TextView(this);
        if (z) {
            ratioLayoutParams = ControlPanelUtils.getRatioLayoutParams(this, 8.0d, 5.125d);
            ratioLayoutParams.setMarginStart(getRatioSize(0.5d, this.mX));
        } else {
            ratioLayoutParams = ControlPanelUtils.getRatioLayoutParams(this, 17.8d, 3.8d);
        }
        textView.setLayoutParams(ratioLayoutParams);
        if (z) {
            textView.setGravity(16);
            textView.setTextAlignment(5);
        } else {
            textView.setGravity(1);
        }
        textView.setTextSize(1, 10.0f);
        textView.setText(ControlPanelAction.getStringIdByActionValue(action.getValue()));
        textView.setTextColor(ResourcesCompat.getColorStateList(R.color.panel_menu_icon_color_expand, getTheme(), getResources()));
        textView.setLines(2);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        int resourceIdByActionValue = ControlPanelAction.getResourceIdByActionValue(action.getValue());
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(this, R.layout.assistantmenu_menubutton, null);
        if (ControlPanelUtils.makeGridButton(this, relativeLayout, action.getValue(), resourceIdByActionValue, false, false)) {
            linearLayout.setOnClickListener(this);
        }
        linearLayout.addView(relativeLayout);
        linearLayout.addView(textView);
        linearLayout.setOnLongClickListener(this);
        linearLayout.setOnDragListener(this);
        linearLayout.setTag(R.id.gridLayout, Integer.valueOf(action.getValue()));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        if (z) {
            layoutParams.setMarginStart(getRatioSize(1.2d, this.mX));
        } else {
            linearLayout.setRotationX(180.0f);
            layoutParams.setMarginStart(getRatioSize(1.1d, this.mX));
            layoutParams.setMarginEnd(getRatioSize(1.1d, this.mX));
        }
        layoutParams.bottomMargin = getRatioSize(1.2d, this.mY);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

    public final void displayTouchPadIfNeed() {
        if (this.mForceTouchPadRemoved || !ControlPanelUtils.isTouchPadEnabled(this.mSharedPreferences) || this.mPendingShowTouchPad || !this.mWindowAttached) {
            return;
        }
        int rotation = getDisplay().getRotation();
        if (mIsFold && (rotation == 0 || 2 == rotation)) {
            this.mIsDisplayTouchPad = true;
            return;
        }
        if (this.mIsEditPanel) {
            this.mIsDisplayTouchPad = true;
        } else if (this.mTouchPad == null) {
            TouchPad touchPad = new TouchPad(this, this.mIsMediaPanel);
            this.mTouchPad = touchPad;
            touchPad.showView();
        }
    }

    public final void executeAct(int i) {
        ControlPanelAction.Action action;
        MenuActionType createAction;
        char c;
        int i2 = 4;
        ComponentName topActivity = ControlPanelUtils.getTopActivity(this);
        ((HashMap) this.mCustomDimen).put("packageName", topActivity.getPackageName());
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
            if (ControlPanelAction.Action.TouchPad.getValue() == i) {
                boolean preferences = getPreferences("MEDIA_PANEL");
                buttonLoggingByString((!(preferences && getPreferences("MEDIA_TOUCH_PAD_ENABLED")) && (preferences || !getPreferences("TOUCH_PAD_ENABLED"))) ? R.string.touchpad_on_sa_logging : R.string.touchpad_off_sa_logging, this.mCustomDimen);
            } else if (ControlPanelAction.Action.EditPanel.getValue() == i) {
                buttonLoggingByString(this.mIsEditPanel ? R.string.close_toolbar_sa_logging : R.string.expand_toolbar_sa_logging, this.mCustomDimen);
            } else {
                Map map = this.mCustomDimen;
                ControlPanelUtils.eventLogging(getPreferences("MEDIA_PANEL") ? "F003" : "F002", ControlPanelAction.getLoggingID(this, i), map);
                ControlPanelUtils.eventLogging("F005", ControlPanelAction.getLoggingID(this, i), map);
            }
        }
        int i3 = 0;
        if (ControlPanelAction.Action.VolumeControl.getValue() == i) {
            setupBrightnessVolumeView(R.id.media_volume_layout, 0);
            return;
        }
        ApplicationInfo applicationInfo = null;
        if (ControlPanelAction.Action.BrightnessControl.getValue() == i) {
            BrightnessInfo brightnessInfo = getDisplay().getBrightnessInfo();
            if (brightnessInfo != null) {
                String str = brightnessInfo.screenBrightnessOverridePackageByWindow;
                PackageManager packageManager = getPackageManager();
                try {
                    applicationInfo = packageManager.getApplicationInfo(str, 0);
                } catch (PackageManager.NameNotFoundException unused) {
                }
                if (applicationInfo != null) {
                    str = packageManager.getApplicationLabel(applicationInfo).toString();
                }
                if (TextUtils.isEmpty(str)) {
                    Cursor query = getContentResolver().query(Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3"), null, "isSettingsChangesAllowed", new String[]{"false"}, null);
                    if (query != null) {
                        try {
                            query.moveToFirst();
                            if ("true".equals(query.getString(query.getColumnIndex("isSettingsChangesAllowed")))) {
                                query.close();
                                c = 1;
                            } else {
                                query.close();
                                c = 0;
                            }
                        } catch (Exception unused2) {
                            query.close();
                        } catch (Throwable th) {
                            query.close();
                            throw th;
                        }
                        if (c != 65535 || c != 0) {
                            setupBrightnessVolumeView(R.id.media_brightness_layout, 1);
                            return;
                        }
                        Log.d("ControlPanelUtils", "getSettingsChangeAllowed:false");
                    }
                    c = 65535;
                    if (c != 65535) {
                    }
                    setupBrightnessVolumeView(R.id.media_brightness_layout, 1);
                    return;
                }
            }
            Toast.makeText(this, R.string.toast_can_not_change_brightness, 0).show();
            return;
        }
        if (ControlPanelAction.Action.EditPanel.getValue() != i) {
            if (ControlPanelAction.Action.DragCircle.getValue() == i || ControlPanelAction.Action.None.getValue() == i) {
                return;
            }
            ArrayList arrayList = ControlPanelAction.mActionType;
            ControlPanelAction.Action[] values = ControlPanelAction.Action.values();
            int length = values.length;
            while (true) {
                if (i3 >= length) {
                    action = ControlPanelAction.Action.None;
                    break;
                }
                action = values[i3];
                if (action.getValue() == i) {
                    break;
                } else {
                    i3++;
                }
            }
            switch (MenuActionType.AnonymousClass1.$SwitchMap$com$android$wm$shell$controlpanel$action$ControlPanelAction$Action[action.ordinal()]) {
                case 1:
                    createAction = ScreenCaptureAction.createAction(this);
                    break;
                case 2:
                    createAction = QuickPanelAction.createAction(this);
                    break;
                case 3:
                    createAction = SplitScreenAction.createAction(this);
                    break;
                case 4:
                    createAction = FlexPanelSettingsAction.createAction(this);
                    break;
                case 5:
                    createAction = TouchPadAction.createAction();
                    break;
                case 6:
                    createAction = QuickSettingsAction.createAction(this);
                    break;
                case 7:
                    createAction = ScreenRecordAction.createAction(this);
                    break;
                default:
                    throw new IllegalArgumentException("Wrong action");
            }
            createAction.doControlAction(topActivity.getClassName(), this);
            return;
        }
        if (this.mIsEditPanel) {
            this.mBasicGridViewWidth = this.mGridView.getWidth();
            this.mBasicGridViewHeight = mIsFold ? this.mGridView.getHeight() : this.mUpperArea.getHeight();
            findViewById(R.id.edit_panel_view).startAnimation(this.mSliderOut);
            new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda1(this, i2), 100L);
            final LinearLayout linearLayout = this.mPanelView;
            final int width = linearLayout.getWidth();
            final int height = linearLayout.getHeight();
            Animation animation = new Animation() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.19
                @Override // android.view.animation.Animation
                public final void applyTransformation(float f, Transformation transformation) {
                    linearLayout.getLayoutParams().width = width - ((int) ((r0 - FlexPanelActivity.this.mBasicGridViewWidth) * f));
                    linearLayout.getLayoutParams().height = height - ((int) ((r0 - FlexPanelActivity.this.mBasicGridViewHeight) * f));
                    if (f == 1.0f) {
                        if (FlexPanelActivity.mIsFold) {
                            linearLayout.getLayoutParams().width = -2;
                        } else {
                            linearLayout.getLayoutParams().height = -2;
                        }
                    }
                    linearLayout.requestLayout();
                }

                @Override // android.view.animation.Animation
                public final boolean willChangeBounds() {
                    return true;
                }
            };
            animation.setInterpolator(this.i_22_25_0_1);
            animation.setDuration(350L);
            linearLayout.startAnimation(animation);
            new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda1(this, 5), 350L);
            return;
        }
        setPreferences("MEDIA_PANEL", this.mIsMediaPanel);
        this.mIsMediaPanel = false;
        this.mIsEditPanel = true;
        this.mBasicGridViewWidth = this.mGridView.getWidth();
        boolean z = mIsFold;
        this.mBasicGridViewHeight = z ? this.mGridView.getHeight() : this.mUpperArea.getHeight();
        setContentView(R.layout.edit_panel_layout);
        if (this.mTooltipInit) {
            setPreferences("tooltip_init", false);
            this.mTooltipInit = false;
            ToolbarTipPopup toolbarTipPopup = new ToolbarTipPopup(this);
            this.mToolbarTipPopup = toolbarTipPopup;
            if (z) {
                int pixelSize = getPixelSize(R.dimen.toolbar_tips_popup_right_margin_fold) + getPixelSize(R.dimen.basic_panel_left_margin) + getRatioSize(6.76d, this.mX);
                if (getResources().getConfiguration().getLayoutDirection() == 1) {
                    pixelSize = this.mX - pixelSize;
                }
                this.mToolbarTipPopup.requestShowPopUp(pixelSize, getRatioSize(75.0d, this.mY));
            } else {
                toolbarTipPopup.requestShowPopUp(this.mX / 2, (getRatioSize(80.0d, this.mY) - getPixelSize(R.dimen.basic_panel_bottom_margin)) - getPixelSize(R.dimen.toolbar_tip_popup_bottom_margin));
            }
        }
        setupCommonPart();
        if (this.mGridLayout == null) {
            return;
        }
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.edit_panel_view);
        this.mPanelView.setBackground(null);
        this.mUpperArea.setBackgroundResource(R.drawable.flex_panel_background_expand);
        linearLayout2.setVisibility(4);
        if (z) {
            this.mGridLayout.setOrientation(1);
            this.mGridLayout.setRowCount(mEditPanelItemSize);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            layoutParams.setMarginStart(getRatioSize(0.2d, this.mX));
            layoutParams.setMarginEnd(getRatioSize(1.7d, this.mX));
            layoutParams.topMargin = getRatioSize(2.1d, this.mY);
            this.mGridLayout.setLayoutParams(layoutParams);
            this.mPanelView.setY(getPixelSize(R.dimen.basic_panel_top_margin));
            this.mPanelView.setLayoutParams(new LinearLayout.LayoutParams(-2, getRatioSize(37.2d, this.mY)));
        } else {
            this.mGridLayout.setOrientation(0);
            this.mGridLayout.setColumnCount(mEditPanelItemSize);
            linearLayout2.setRotationX(180.0f);
            this.mPanelView.setY(getPixelSize(R.dimen.basic_panel_bottom_margin) * (-1));
            this.mPanelView.setLayoutParams(new LinearLayout.LayoutParams(getRatioSize(88.9d, this.mX), -2));
            ControlPanelUtils.setRatioPadding(this, this.mGridLayout, 0.0d, 1.4d, 0.0d, 0.0d);
        }
        ArrayList arrayList2 = this.mEditActions;
        int size = arrayList2.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList2.get(i4);
            i4++;
            this.mGridLayout.addView(createEditButton((ControlPanelAction.Action) obj));
        }
        this.mPanelView.getViewTreeObserver().addOnGlobalLayoutListener(new AnonymousClass16(linearLayout2));
        if (this.mTouchPad != null) {
            removeTouchPad(false);
        }
    }

    public final ArrayList getActionArray(String str, boolean z) {
        String string = this.mSharedPreferences.getString(str, null);
        ArrayList arrayList = new ArrayList();
        if (string != null) {
            try {
                JSONArray jSONArray = new JSONArray(string);
                for (int i = 0; i < jSONArray.length(); i++) {
                    String string2 = jSONArray.getString(i);
                    if (!z || (!"0".equals(string2) && !"8".equals(string2))) {
                        arrayList.add(string2);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public final void getActionArrayForStatusLogging(String str, ArrayList arrayList) {
        String string = this.mSharedPreferences.getString(str, null);
        if (string == null) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(string);
            for (int i = 0; i < jSONArray.length(); i++) {
                String string2 = jSONArray.getString(i);
                if (!"0".equals(string2) && !"8".equals(string2) && !"7".equals(string2)) {
                    arrayList.add(Integer.valueOf(Integer.parseInt(string2)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public final ControlPanelAction.Action getActionByPosition(int i) {
        return i >= 10 ? (ControlPanelAction.Action) this.mEditActions.get(i - 10) : (ControlPanelAction.Action) this.mActions.get(i);
    }

    public final int getPixelSize(int i) {
        return getResources().getDimensionPixelSize(i);
    }

    public final int getPositionByAction(int i) {
        for (int i2 = 0; i2 < this.mActions.size(); i2++) {
            if (i == ((ControlPanelAction.Action) this.mActions.get(i2)).getValue()) {
                return i2;
            }
        }
        for (int i3 = 0; i3 < this.mEditActions.size(); i3++) {
            if (i == ((ControlPanelAction.Action) this.mEditActions.get(i3)).getValue()) {
                return i3 + 10;
            }
        }
        return -1;
    }

    public final boolean getPreferences(String str) {
        return this.mSharedPreferences.getBoolean(str, false);
    }

    public final void onActionArrayAdd(String str, int i, ControlPanelAction.Action action) {
        StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(i, "onActionArrayAdd list :", str, " addAt : ", " addAction : ");
        m888m.append(action);
        Log.d("FlexPanelActivity", m888m.toString());
        if ("basic_panel_action_list".equals(str)) {
            this.mActions.add(i, action);
            this.mGridAdapter.items.add(i, action);
            onGridViewChanged();
            this.mGridView.setAdapter((ListAdapter) this.mGridAdapter);
        } else if ("edit_panel_action_list".equals(str) && this.mGridLayout != null) {
            this.mEditActions.add(i, action);
            this.mGridLayout.removeAllViews();
            ArrayList arrayList = this.mEditActions;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                this.mGridLayout.addView(createEditButton((ControlPanelAction.Action) obj));
            }
        }
        ArrayList actionArray = getActionArray(str, false);
        actionArray.add(i, String.valueOf(action.getValue()));
        setActionArray(str, actionArray);
    }

    public final void onActionArrayRemove(int i, String str) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "onActionArrayRemove list :", str, " removeAt : ", "FlexPanelActivity");
        if ("basic_panel_action_list".equals(str)) {
            this.mActions.remove(i);
            this.mGridAdapter.items.remove(i);
            onGridViewChanged();
            this.mGridView.setAdapter((ListAdapter) this.mGridAdapter);
        } else if ("edit_panel_action_list".equals(str) && this.mGridLayout != null) {
            this.mEditActions.remove(i);
            this.mGridLayout.removeAllViews();
            ArrayList arrayList = this.mEditActions;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                this.mGridLayout.addView(createEditButton((ControlPanelAction.Action) obj));
            }
        }
        ArrayList actionArray = getActionArray(str, false);
        actionArray.remove(i);
        setActionArray(str, actionArray);
    }

    public final void onActionArrayRemoveAdd(String str, int i, int i2, ControlPanelAction.Action action) {
        StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(i, "onActionArrayRemoveAdd list :", str, " removeAt : ", " addAt : ");
        m888m.append(i2);
        m888m.append(" addAction : ");
        m888m.append(action);
        Log.d("FlexPanelActivity", m888m.toString());
        if ("basic_panel_action_list".equals(str)) {
            this.mActions.remove(i);
            this.mActions.add(i2, action);
            this.mGridAdapter.items.remove(i);
            this.mGridAdapter.items.add(i2, action);
            onGridViewChanged();
            this.mGridView.setAdapter((ListAdapter) this.mGridAdapter);
        } else if ("edit_panel_action_list".equals(str) && this.mGridLayout != null) {
            this.mEditActions.remove(i);
            this.mEditActions.add(i2, action);
            this.mGridLayout.removeAllViews();
            ArrayList arrayList = this.mEditActions;
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                this.mGridLayout.addView(createEditButton((ControlPanelAction.Action) obj));
            }
        }
        ArrayList actionArray = getActionArray(str, false);
        actionArray.remove(i);
        actionArray.add(i2, String.valueOf(action.getValue()));
        setActionArray(str, actionArray);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mWindowAttached = true;
        this.mPendingShowTouchPad = false;
        displayTouchPadIfNeed();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getTag(R.id.grid_button) != null) {
            executeAct(((Integer) view.getTag(R.id.grid_button)).intValue());
            new Handler(Looper.getMainLooper()).postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda1(this, 3), 100L);
        } else if (view.getTag(R.id.gridLayout) != null) {
            executeAct(((Integer) view.getTag(R.id.gridLayout)).intValue());
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        boolean z;
        int i = configuration.orientation;
        if (i != this.mPrevOrientation) {
            this.mPrevOrientation = i;
            z = true;
        } else {
            z = false;
        }
        super.onConfigurationChanged(configuration);
        if (z && (mIsFold || configuration.orientation == 1)) {
            if (Settings.System.getInt(getContentResolver(), "media_floating_only", 0) == 1 || CheckControlWindowState.isMediaPanelRequestedState(this, this.mMediaController)) {
                setupMediaPanel();
            } else {
                setupBasicPanel();
            }
        }
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        if (mIsFold && this.mIsDisplayTouchPad) {
            if (1 == rotation || 3 == rotation) {
                displayTouchPadIfNeed();
                this.mIsDisplayTouchPad = false;
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(8);
        this.mOwnActivity = this;
        sFlexPanelActivity = this;
        String string = Settings.Secure.getString(getContentResolver(), SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES);
        if (string != null) {
            string.contains(ControlPanelUtils.TALKBACK_SERVICE);
        }
        this.mActivityManager = (ActivityManager) getSystemService(ActivityManager.class);
        this.mCustomDimen = new HashMap();
        this.mSharedPreferences = getSharedPreferences(SystemUIAnalytics.FLEX_PANEL_PREF_NAME, 0);
        DeviceStateManager deviceStateManager = (DeviceStateManager) getSystemService(DeviceStateManager.class);
        this.mDeviceStateManager = deviceStateManager;
        deviceStateManager.registerCallback(getMainExecutor(), this.mDeviceStateCallback);
        this.mX = ControlPanelUtils.getDisplayX(this);
        this.mY = ControlPanelUtils.getDisplayY(this);
        this.mFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        this.mFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        this.mSliderIn = AnimationUtils.loadAnimation(this, R.anim.sliderin);
        this.mSliderOut = AnimationUtils.loadAnimation(this, R.anim.sliderout);
        this.i_22_25_0_1 = AnimationUtils.loadInterpolator(this, R.interpolator.i_22_25_0_1);
        this.mPanelInit = this.mSharedPreferences.getBoolean("panel_init", true);
        this.mTooltipInit = this.mSharedPreferences.getBoolean("tooltip_init", true);
        getPixelSize(R.dimen.scroll_wheel_haptic_threshold);
        this.mPrevOrientation = getResources().getConfiguration().orientation;
        ((HashMap) this.mCustomDimen).put("packageName", ControlPanelUtils.getTopActivity(this).getPackageName());
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
            String action = getIntent().getAction();
            if ("android.intent.action.EXPAND_FLEX_PANEL".equals(action)) {
                ControlPanelUtils.eventLogging("F001", "a", this.mCustomDimen);
            } else if ("android.intent.action.AUTORUN_FLEX_PANEL".equals(action)) {
                ControlPanelUtils.eventLogging("F001", "b", this.mCustomDimen);
            }
        }
        ContentResolver contentResolver = getContentResolver();
        int i = 2;
        if (!(Settings.Global.getInt(contentResolver, SettingsHelper.INDEX_LOW_POWER_MODE, 0) == 1 && Settings.Global.getInt(contentResolver, "sem_power_mode_limited_apps_and_home_screen", 0) == 1)) {
            try {
                try {
                    try {
                        try {
                            Bitmap bitmap = ((BitmapDrawable) resizeDrawable(WallpaperManager.getInstance(this).semGetDrawable(5))).getBitmap();
                            Rect[] rectArr = {new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())};
                            SemWallpaperColors.Item item = SemWallpaperColors.fromBitmap(this, bitmap, 0, 0, rectArr).get(rectArr[0]);
                            if (item != null) {
                                if (item.getFontColor() == 1) {
                                    i = 1;
                                }
                            }
                        } catch (ClassCastException e) {
                            Log.e("FlexPanelActivity", "ClassCastException : " + e.toString());
                        }
                    } catch (IllegalArgumentException e2) {
                        Log.e("FlexPanelActivity", "IllegalArgumentException : " + e2.toString());
                    }
                } catch (NullPointerException e3) {
                    Log.e("FlexPanelActivity", "NullPointerException : " + e3.toString());
                }
            } catch (Throwable unused) {
            }
            getDelegate().setLocalNightMode(i);
        }
        getWindow().getAttributes().samsungFlags |= 16777216;
        getWindow().setDecorFitsSystemWindows(false);
        this.mMediaSessionManager = (MediaSessionManager) getSystemService("media_session");
        this.mMediaSessionManager.addOnActiveSessionsChangedListener(this.mActiveSessionsChangedListener, null, Looper.myLooper() == null ? new Handler() : null);
        MediaController mediaController = CheckControlWindowState.getMediaController(this, this.mMediaSessionManager);
        this.mMediaController = mediaController;
        if (mediaController != null) {
            mediaController.registerCallback(this.mCallback);
        }
        if (Settings.System.getInt(getContentResolver(), "media_floating_only", 0) == 1 || CheckControlWindowState.isMediaPanelRequestedState(this, this.mMediaController)) {
            setupMediaPanel();
        } else {
            if (bundle == null) {
                this.mPendingShowTouchPad = true;
            }
            setupBasicPanel();
        }
        if (getWindow().getInsetsController() == null || i != 1) {
            return;
        }
        getWindow().getInsetsController().setSystemBarsAppearance(16, 16);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        unregisterReceiver(this.mReceiver);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(InputMethodManager.class);
        if (inputMethodManager != null && inputMethodManager.isInputMethodShown()) {
            new Handler().post(new FlexPanelActivity$$ExternalSyntheticLambda4(inputMethodManager, 0));
        }
        removeTouchPad(true);
        this.mDeviceStateManager.unregisterCallback(this.mDeviceStateCallback);
        this.mMediaSessionManager.removeOnActiveSessionsChangedListener(this.mActiveSessionsChangedListener);
        MediaController mediaController = this.mMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(this.mCallback);
        }
        if (this.mIsMediaPanel) {
            FlexMediaPanel flexMediaPanel = this.mFlexMediaPanel;
            if (flexMediaPanel != null) {
                flexMediaPanel.clearController();
            } else {
                TouchPadMediaPanel touchPadMediaPanel = this.mTouchPadMediaPanel;
                if (touchPadMediaPanel != null) {
                    Log.i("TouchPadMediaPanel", "TouchPadMediaPanel clearController");
                    if (touchPadMediaPanel.mMediaController != null) {
                        touchPadMediaPanel.mMediaController = null;
                    }
                }
            }
        }
        ToolbarTipPopup toolbarTipPopup = this.mToolbarTipPopup;
        if (toolbarTipPopup != null) {
            SemTipPopup semTipPopup = toolbarTipPopup.mTipPopup;
            if (semTipPopup != null) {
                semTipPopup.dismiss(true);
                toolbarTipPopup.mTipPopup = null;
            }
            FrameLayout frameLayout = toolbarTipPopup.mView;
            if (frameLayout != null) {
                toolbarTipPopup.mWindowManager.removeView(frameLayout);
                toolbarTipPopup.mView = null;
            }
        }
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mWindowAttached = false;
    }

    @Override // android.view.View.OnDragListener
    public final boolean onDrag(final View view, final DragEvent dragEvent) {
        int action = dragEvent.getAction();
        try {
            int positionByAction = view.getTag(R.id.gridLayout) != null ? getPositionByAction(((Integer) view.getTag(R.id.gridLayout)).intValue()) : view.getTag(R.id.grid_button) != null ? getPositionByAction(((Integer) view.getTag(R.id.grid_button)).intValue()) : -1;
            if (positionByAction == -1) {
                return false;
            }
            if (action == 2) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime - this.mDragEnteredTime >= 450) {
                    this.mDragEnteredTime = elapsedRealtime;
                    int positionByAction2 = getPositionByAction(ControlPanelAction.Action.DragCircle.getValue());
                    int checkToValueNone = checkToValueNone(positionByAction, true);
                    if (positionByAction2 != -1 && positionByAction2 != checkToValueNone && (getActionByPosition(checkToValueNone) != ControlPanelAction.Action.EditPanel || (positionByAction2 >= 10 && this.mActions.size() != 5))) {
                        if (!this.mOnDragAnimation) {
                            if (this.mPanelInit) {
                                setPreferences("panel_init", false);
                                this.mPanelInit = false;
                            }
                            Log.d("FlexPanelActivity", "onDragAnimation start, from : " + positionByAction2 + " to : " + checkToValueNone);
                            onDragAnimation(positionByAction2, checkToValueNone);
                            return true;
                        }
                        new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                                View view2 = view;
                                DragEvent dragEvent2 = dragEvent;
                                int i = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity.onDrag(view2, dragEvent2);
                            }
                        }, 450L);
                    }
                }
            } else if (action == 4 && !this.mOnDragEnded) {
                this.mOnDragEnded = true;
                new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda1(this, 0), 450L);
                return true;
            }
            return true;
        } catch (Exception e) {
            Log.e("FlexPanelActivity", "failed to Drag " + action, e);
            this.mOnDragAnimation = false;
            onDragEnded();
            return false;
        }
    }

    public final void onDragAnimation(final int i, final int i2) {
        this.mOnDragAnimation = true;
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(this.i_22_25_0_1);
        animatorSet.setDuration(450L);
        GridLayout gridLayout = this.mGridLayout;
        if (gridLayout == null || i < 10) {
            this.mGridView.getChildAt(i).setVisibility(4);
        } else {
            gridLayout.getChildAt(checkFromValueNone(i) - 10).setVisibility(4);
        }
        if (i < 10 && i2 < 10) {
            if (i < i2) {
                int i3 = i;
                while (i3 < i2) {
                    View childAt = this.mGridView.getChildAt(i3);
                    i3++;
                    setDragAnimation(animatorSet, childAt, this.mGridView.getChildAt(i3));
                }
            } else {
                for (int i4 = i; i4 > i2; i4--) {
                    setDragAnimation(animatorSet, this.mGridView.getChildAt(i4), this.mGridView.getChildAt(i4 - 1));
                }
            }
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    onAnimationEnd(animator);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                    int i5 = i;
                    int i6 = i2;
                    ControlPanelAction.Action action = ControlPanelAction.Action.DragCircle;
                    int i7 = FlexPanelActivity.mEditPanelItemSize;
                    flexPanelActivity.onActionArrayRemoveAdd("basic_panel_action_list", i5, i6, action);
                    FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                    flexPanelActivity2.mOnDragAnimation = false;
                    if (flexPanelActivity2.mOnDragEnded) {
                        flexPanelActivity2.onDragEnded();
                    }
                }
            });
            animatorSet.start();
            return;
        }
        if (i >= 10 && i2 >= 10 && this.mGridLayout != null) {
            if (getActionByPosition(i2) == ControlPanelAction.Action.DragCircle) {
                this.mOnDragAnimation = false;
                return;
            }
            final int checkFromValueNone = checkFromValueNone(i);
            final int checkToValueNone = checkToValueNone(i2, true);
            if (checkFromValueNone < checkToValueNone) {
                int i5 = checkFromValueNone - 10;
                while (i5 < checkToValueNone - 10) {
                    View childAt2 = this.mGridLayout.getChildAt(i5);
                    i5++;
                    setDragAnimation(animatorSet, childAt2, this.mGridLayout.getChildAt(i5));
                }
            } else {
                for (int i6 = checkFromValueNone - 10; i6 > checkToValueNone - 10; i6--) {
                    setDragAnimation(animatorSet, this.mGridLayout.getChildAt(i6), this.mGridLayout.getChildAt(i6 - 1));
                }
            }
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    onAnimationEnd(animator);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                    int i7 = checkFromValueNone - 10;
                    int i8 = checkToValueNone - 10;
                    ControlPanelAction.Action action = ControlPanelAction.Action.DragCircle;
                    int i9 = FlexPanelActivity.mEditPanelItemSize;
                    flexPanelActivity.onActionArrayRemoveAdd("edit_panel_action_list", i7, i8, action);
                    FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                    flexPanelActivity2.mOnDragAnimation = false;
                    if (flexPanelActivity2.mOnDragEnded) {
                        flexPanelActivity2.onDragEnded();
                    }
                }
            });
            animatorSet.start();
            return;
        }
        if (i < 10 || i2 >= 10 || this.mGridLayout == null) {
            if (i >= 10 || i2 < 10 || this.mGridLayout == null) {
                return;
            }
            final int checkToValueNone2 = checkToValueNone(i2, false);
            removeEditPanelNone();
            this.mGridView.getChildAt(i).setVisibility(4);
            for (int size = this.mActions.size() - 2; size >= i; size--) {
                setDragAnimation(animatorSet, this.mGridView.getChildAt(size), this.mGridView.getChildAt(size + 1));
            }
            GridLayout gridLayout2 = this.mGridLayout;
            ControlPanelAction.Action action = ControlPanelAction.Action.None;
            gridLayout2.addView(createEditButton(action));
            this.mEditActions.add(action);
            final GridLayout gridLayout3 = this.mGridLayout;
            gridLayout3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.10
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    gridLayout3.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    gridLayout3.getLeft();
                    for (int i7 = checkToValueNone2 - 9; i7 < gridLayout3.getChildCount(); i7++) {
                        FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                        AnimatorSet animatorSet2 = animatorSet;
                        View childAt3 = gridLayout3.getChildAt(i7);
                        View childAt4 = gridLayout3.getChildAt(i7 - 1);
                        int i8 = FlexPanelActivity.mEditPanelItemSize;
                        flexPanelActivity.getClass();
                        FlexPanelActivity.setDragAnimation(animatorSet2, childAt3, childAt4);
                    }
                    FlexPanelActivity.this.mEditActions.remove(r0.getPositionByAction(ControlPanelAction.Action.None.getValue()) - 10);
                    animatorSet.start();
                }
            });
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.11
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    onAnimationEnd(animator);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                    int i7 = i;
                    int i8 = FlexPanelActivity.mEditPanelItemSize;
                    flexPanelActivity.onActionArrayRemove(i7, "basic_panel_action_list");
                    FlexPanelActivity.this.onActionArrayAdd("edit_panel_action_list", checkToValueNone2 - 10, ControlPanelAction.Action.DragCircle);
                    FlexPanelActivity.this.addEditPanelNone();
                    FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                    flexPanelActivity2.mOnDragAnimation = false;
                    if (flexPanelActivity2.mOnDragEnded) {
                        flexPanelActivity2.onDragEnded();
                    }
                }
            });
            return;
        }
        final int checkFromValueNone2 = checkFromValueNone(i);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                onAnimationEnd(animator);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (FlexPanelActivity.this.mActions.size() > 4) {
                    FlexPanelActivity.this.onActionArrayRemoveAdd("edit_panel_action_list", checkFromValueNone2 - 10, 0, (ControlPanelAction.Action) FlexPanelActivity.this.mActions.get(3));
                    FlexPanelActivity.this.onActionArrayRemoveAdd("basic_panel_action_list", 3, i2, ControlPanelAction.Action.DragCircle);
                } else {
                    if (FlexPanelActivity.mIsFold) {
                        ArrayList actionArray = FlexPanelActivity.this.getActionArray("basic_panel_action_list", false);
                        actionArray.remove(actionArray.size() - 1);
                        FlexPanelActivity.this.setActionArray("basic_panel_action_list", actionArray);
                    }
                    FlexPanelActivity.this.onActionArrayRemove(checkFromValueNone2 - 10, "edit_panel_action_list");
                    FlexPanelActivity.this.onActionArrayAdd("basic_panel_action_list", i2, ControlPanelAction.Action.DragCircle);
                }
                FlexPanelActivity.this.removeEditPanelNone();
                FlexPanelActivity.this.addEditPanelNone();
                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                flexPanelActivity.mOnDragAnimation = false;
                if (flexPanelActivity.mOnDragEnded) {
                    flexPanelActivity.onDragEnded();
                }
            }
        });
        if (this.mActions.size() > 4) {
            this.mGridView.getChildAt(3).setVisibility(4);
            this.mGridLayout.getChildAt(i - 10).setVisibility(4);
            for (int size2 = this.mActions.size() - 2; size2 > i2; size2--) {
                setDragAnimation(animatorSet, this.mGridView.getChildAt(size2), this.mGridView.getChildAt(size2 - 1));
            }
            for (int i7 = checkFromValueNone2 - 10; i7 > 0; i7--) {
                setDragAnimation(animatorSet, this.mGridLayout.getChildAt(i7), this.mGridLayout.getChildAt(i7 - 1));
            }
            animatorSet.start();
            return;
        }
        this.mGridLayout.getChildAt(i - 10).setVisibility(4);
        int i8 = checkFromValueNone2 - 10;
        while (i8 < this.mEditActions.size() - 1) {
            View childAt3 = this.mGridLayout.getChildAt(i8);
            i8++;
            setDragAnimation(animatorSet, childAt3, this.mGridLayout.getChildAt(i8));
        }
        if (!mIsFold) {
            animatorSet.start();
        } else {
            onActionArrayAdd("basic_panel_action_list", this.mActions.size(), ControlPanelAction.Action.None);
            this.mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.9
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    FlexPanelActivity.this.mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int i9 = i2;
                    while (true) {
                        i9++;
                        if (i9 >= FlexPanelActivity.this.mActions.size()) {
                            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                            GridPanelAdapter gridPanelAdapter = flexPanelActivity.mGridAdapter;
                            ControlPanelAction.Action action2 = ControlPanelAction.Action.None;
                            gridPanelAdapter.items.remove(flexPanelActivity.getPositionByAction(action2.getValue()));
                            FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                            flexPanelActivity2.mActions.remove(flexPanelActivity2.getPositionByAction(action2.getValue()));
                            animatorSet.start();
                            return;
                        }
                        FlexPanelActivity.setDragAnimation(animatorSet, FlexPanelActivity.this.mGridView.getChildAt(i9), FlexPanelActivity.this.mGridView.getChildAt(i9 - 1));
                    }
                }
            });
        }
    }

    public final void onDragEnded() {
        if (this.mOnDragAnimation) {
            return;
        }
        int positionByAction = getPositionByAction(ControlPanelAction.Action.DragCircle.getValue());
        ListPopupWindow$$ExternalSyntheticOutline0.m(positionByAction, "onDragEnded, dropPosition : ", "FlexPanelActivity");
        if (positionByAction == -1) {
            this.mDraggedAction = ControlPanelAction.Action.None;
            this.mOnDragEnded = false;
            return;
        }
        if (positionByAction >= 10 && this.mGridLayout != null) {
            int i = positionByAction - 10;
            onActionArrayRemoveAdd("edit_panel_action_list", i, i, this.mDraggedAction);
        } else if (positionByAction != -1) {
            onActionArrayRemoveAdd("basic_panel_action_list", positionByAction, positionByAction, this.mDraggedAction);
        }
        removeEditPanelNone();
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
            ComponentName topActivity = ControlPanelUtils.getTopActivity(this);
            ((HashMap) this.mCustomDimen).put("packageName", topActivity.getPackageName());
            buttonLoggingByString(R.string.toolbar_reorder_sa_logging, this.mCustomDimen);
            updateStatusPreferences(true);
        }
        this.mDraggedAction = ControlPanelAction.Action.None;
        this.mOnDragEnded = false;
    }

    public final void onGridViewChanged() {
        if (mIsFold) {
            this.mBrightnessVolumeView.setLayoutParams(ControlPanelUtils.getRatioLayoutParams(this, 6.75d, 37.1d));
            if (!this.mIsEditPanel) {
                float pixelSize = getPixelSize(R.dimen.basic_panel_top_margin);
                this.mBrightnessVolumeView.setY(pixelSize);
                this.mGridView.setY(pixelSize);
            }
            this.mUpperArea.setLayoutParams(new LinearLayout.LayoutParams(getRatioSize(6.76d, this.mX), -1));
            this.mGridView.setGravity(17);
            this.mGridView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            return;
        }
        int size = this.mActions.size();
        this.mGridView.setNumColumns(size);
        int i = size - 1;
        double d = new double[]{23.3d, 37.7d, 56.1d, 71.5d, 88.9d}[i];
        this.mUpperArea.setLayoutParams(new LinearLayout.LayoutParams(-2, getRatioSize(6.4d, this.mY)));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getRatioSize(d, this.mX), getRatioSize(4.55d, this.mY));
        this.mGridView.setPadding(getRatioSize(6.08d, this.mX), 0, getRatioSize(6.08d, this.mX), 0);
        layoutParams.gravity = 17;
        this.mGridView.setLayoutParams(layoutParams);
        int ratioSize = getRatioSize(11.11d, this.mX);
        if (size > 1) {
            this.mGridView.setHorizontalSpacing((((getRatioSize(d, this.mX) - this.mGridView.getPaddingLeft()) - this.mGridView.getPaddingRight()) - (size * ratioSize)) / i);
        }
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        ControlPanelAction.Action action;
        if (!this.mOnDragEnded) {
            if (view.getTag(R.id.grid_button) != null && ((Integer) view.getTag(R.id.grid_button)).intValue() == ControlPanelAction.Action.EditPanel.getValue()) {
                onClick(view);
                return true;
            }
            ArrayList arrayList = GridItems.ALL_ACTIONS;
            int size = arrayList.size();
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                Object obj = arrayList.get(i2);
                i2++;
                ControlPanelAction.Action action2 = (ControlPanelAction.Action) obj;
                if (view.getTag(R.id.grid_button) != null && ((Integer) view.getTag(R.id.grid_button)).intValue() == action2.getValue()) {
                    this.mDraggedAction = action2;
                    break;
                }
                if (view.getTag(R.id.gridLayout) != null && ((Integer) view.getTag(R.id.gridLayout)).intValue() == action2.getValue()) {
                    this.mDraggedAction = action2;
                    break;
                }
            }
            int positionByAction = getPositionByAction(this.mDraggedAction.getValue());
            ControlPanelAction.Action action3 = this.mDraggedAction;
            if (action3 != ControlPanelAction.Action.None && action3 != (action = ControlPanelAction.Action.DragCircle) && positionByAction != -1 && this.mBrightnessVolumeView.getVisibility() == 8) {
                final View findViewById = view.findViewById(R.id.grid_button);
                ImageButton imageButton = (ImageButton) findViewById.findViewById(R.id.menubutton);
                imageButton.setBackgroundResource(R.drawable.drag_selected_background);
                imageButton.setBackgroundTintList(null);
                SemGfxImageFilter semGfxImageFilter = new SemGfxImageFilter();
                semGfxImageFilter.setBlurRadius(45.0f);
                imageButton.semSetGfxImageFilter(semGfxImageFilter);
                imageButton.setVisibility(0);
                findViewById.findViewById(R.id.drag_selected_border).setBackgroundResource(R.drawable.drag_selected_border);
                findViewById.findViewById(R.id.drag_selected_border).setVisibility(0);
                final int pixelSize = getPixelSize(R.dimen.grid_menu_layout_size);
                if (findViewById.startDragAndDrop(null, new View.DragShadowBuilder(findViewById) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.12
                    @Override // android.view.View.DragShadowBuilder
                    public final void onProvideShadowMetrics(Point point, Point point2) {
                        int i3 = pixelSize;
                        point.set(i3 * 2, i3);
                        int width = findViewById.getWidth() / 2;
                        int height = findViewById.getHeight() / 2;
                        FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                        int i4 = FlexPanelActivity.mEditPanelItemSize;
                        point2.set(width, flexPanelActivity.getPixelSize(R.dimen.drag_selected_top_margin) + height);
                    }
                }, null, 1048576)) {
                    Log.d("FlexPanelActivity", "startDragAndDrop, mDraggedAction : " + this.mDraggedAction + " draggedPosition : " + positionByAction);
                    view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                    if (view.getTag(R.id.gridLayout) == null || positionByAction < 10) {
                        onActionArrayRemoveAdd("basic_panel_action_list", positionByAction, positionByAction, action);
                    } else {
                        int i3 = positionByAction - 10;
                        onActionArrayRemoveAdd("edit_panel_action_list", i3, i3, action);
                    }
                    if (this.mIsEditPanel) {
                        addEditPanelNone();
                        return true;
                    }
                } else {
                    Log.d("FlexPanelActivity", "startDrag fail");
                    this.mGridView.setAdapter((ListAdapter) this.mGridAdapter);
                    GridLayout gridLayout = this.mGridLayout;
                    if (gridLayout != null) {
                        gridLayout.removeAllViews();
                        ArrayList arrayList2 = this.mEditActions;
                        int size2 = arrayList2.size();
                        while (i < size2) {
                            Object obj2 = arrayList2.get(i);
                            i++;
                            this.mGridLayout.addView(createEditButton((ControlPanelAction.Action) obj2));
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onMultiWindowModeChanged(boolean z, Configuration configuration) {
        if (!z) {
            finish();
        }
        super.onMultiWindowModeChanged(z, configuration);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        getContentResolver().unregisterContentObserver(this.mBrightnessObserver);
        getContentResolver().unregisterContentObserver(this.mModeEnableObserver);
        getContentResolver().unregisterContentObserver(this.mTalkbackObserver);
        try {
            ActivityTaskManager.getService().unregisterTaskStackListener(this.mTaskStackListener);
        } catch (RemoteException unused) {
        }
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) getSystemService(ActivityManager.class)).getRunningTasks(1);
            ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.size() > 0 ? runningTasks.get(0) : null;
            if (runningTaskInfo != null && runningTaskInfo.getActivityType() == 2) {
                ControlPanelUtils.eventLogging("F004", "c", new HashMap());
            }
        }
        if (this.mDraggedAction != ControlPanelAction.Action.None) {
            onDragEnded();
        }
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mIsResumeCalled = true;
        if (!isInMultiWindowMode()) {
            finish();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.COLLAPSE_FLEX_PANEL");
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("com.samsung.android.app.screenrecorder.on");
        intentFilter.addAction("com.samsung.android.app.screenrecorder.off");
        registerReceiver(this.mReceiver, intentFilter, 2);
        if (this.mInputMonitor == null) {
            this.mInputMonitor = InputManager.getInstance().monitorGestureInput("caption-touch", 0);
        }
        if (this.mEventReceiver == null) {
            this.mEventReceiver = new EventReceiver(this.mInputMonitor.getInputChannel(), Looper.myLooper());
        }
        getContentResolver().registerContentObserver(Settings.System.getUriFor("screen_brightness"), false, this.mBrightnessObserver);
        getContentResolver().registerContentObserver(Settings.Global.getUriFor("flex_mode_panel_enabled"), false, this.mModeEnableObserver);
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor(SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES), false, this.mTalkbackObserver);
        try {
            ActivityTaskManager.getService().registerTaskStackListener(this.mTaskStackListener);
        } catch (RemoteException unused) {
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        if (this.mCloseState) {
            finish();
        }
        super.onStop();
    }

    public final void removeEditPanelNone() {
        for (int size = this.mEditActions.size() - 1; size >= 0; size--) {
            if (this.mEditActions.get(size) == ControlPanelAction.Action.None) {
                onActionArrayRemove(size, "edit_panel_action_list");
            }
        }
    }

    public final void removeTouchPad(boolean z) {
        TouchPad touchPad = this.mTouchPad;
        if (touchPad == null) {
            return;
        }
        if (z) {
            View view = touchPad.mOverlayView;
            if (view != null && view.isAttachedToWindow()) {
                touchPad.mWindowManager.removeViewImmediate(touchPad.mOverlayView);
            }
        } else {
            touchPad.mTouchPadBg.startAnimation(AnimationUtils.loadAnimation(touchPad.mContext, R.anim.fadeout));
            touchPad.mCenterText.startAnimation(AnimationUtils.loadAnimation(touchPad.mContext, R.anim.fadeout));
            touchPad.mTouchPadLine.startAnimation(AnimationUtils.loadAnimation(touchPad.mContext, R.anim.fadeout));
            new Handler(Looper.getMainLooper()).postDelayed(new TouchPad$$ExternalSyntheticLambda0(touchPad, 1), 100L);
        }
        this.mTouchPad = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.drawable.Drawable resizeDrawable(android.graphics.drawable.Drawable r15) {
        /*
            Method dump skipped, instructions count: 247
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.resizeDrawable(android.graphics.drawable.Drawable):android.graphics.drawable.Drawable");
    }

    public final void returnToMenu() {
        if (this.mBrightnessVolumeView.getVisibility() != 8) {
            this.mBrightnessVolumeView.startAnimation(this.mSliderOut);
            new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda1(this, 6), 100L);
        }
    }

    public final void setActionArray(String str, ArrayList arrayList) {
        SharedPreferences.Editor edit = this.mSharedPreferences.edit();
        JSONArray jSONArray = new JSONArray();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            jSONArray.put((String) obj);
        }
        if (arrayList.isEmpty()) {
            edit.putString(str, null);
        } else {
            edit.putString(str, jSONArray.toString());
        }
        edit.apply();
    }

    public final void setPreferences(String str, boolean z) {
        this.mSharedPreferences.edit().putBoolean(str, z).apply();
    }

    public final void setupBasicPanel() {
        this.mIsMediaPanel = false;
        this.mIsEditPanel = false;
        setContentView(R.layout.basic_panel_layout);
        setupCommonPart();
        setPreferences("MEDIA_TOUCH_PAD_ENABLED", false);
        setPreferences("MEDIA_PANEL", false);
        displayTouchPadIfNeed();
    }

    public final void setupBrightnessVolumeView(final int i, int i2) {
        if (this.mBrightnessVolumeView.getVisibility() == 8) {
            this.mUpperArea.startAnimation(this.mSliderOut);
            new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                    int i3 = i;
                    flexPanelActivity.mUpperArea.setVisibility(8);
                    flexPanelActivity.mBrightnessVolumeView.setVisibility(0);
                    flexPanelActivity.mBrightnessVolumeView.findViewById(i3).callOnClick();
                    flexPanelActivity.mBrightnessVolumeView.startAnimation(flexPanelActivity.mSliderIn);
                }
            }, 100L);
        } else if (this.mBrightnessVolumeType != i2) {
            this.mBrightnessVolumeView.setVisibility(8);
            BrightnessVolumeView brightnessVolumeView = this.mBrightnessVolumeView;
            if (brightnessVolumeView.mBrightnessSeekBar.getVisibility() == 0) {
                brightnessVolumeView.mMediaBrightnessLayout.callOnClick();
            } else {
                brightnessVolumeView.mMediaVolumeLayout.callOnClick();
            }
            this.mBrightnessVolumeView.setVisibility(0);
            this.mBrightnessVolumeView.findViewById(i).callOnClick();
        } else {
            BrightnessVolumeView brightnessVolumeView2 = this.mBrightnessVolumeView;
            if (i2 == 0) {
                brightnessVolumeView2.handlerExcute(brightnessVolumeView2.mVolumeRunnable, true);
            } else {
                brightnessVolumeView2.handlerExcute(brightnessVolumeView2.mBrightnessRunnable, true);
            }
        }
        this.mBrightnessVolumeType = i2;
    }

    public final void setupCommonPart() {
        this.mGridLayout = this.mIsEditPanel ? (GridLayout) findViewById(R.id.gridLayout) : null;
        findViewById(R.id.wallpaper_area).setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                if (flexPanelActivity.mBrightnessVolumeView.getVisibility() != 8) {
                    BrightnessVolumeView brightnessVolumeView = flexPanelActivity.mBrightnessVolumeView;
                    if (brightnessVolumeView.mBrightnessSeekBar.getVisibility() == 0) {
                        brightnessVolumeView.mMediaBrightnessLayout.callOnClick();
                    } else {
                        brightnessVolumeView.mMediaVolumeLayout.callOnClick();
                    }
                    flexPanelActivity.returnToMenu();
                }
            }
        });
        View findViewById = findViewById(R.id.wallpaper_area);
        ContentResolver contentResolver = getContentResolver();
        int i = 0;
        if (Settings.Global.getInt(contentResolver, SettingsHelper.INDEX_LOW_POWER_MODE, 0) != 1 || Settings.Global.getInt(contentResolver, "sem_power_mode_limited_apps_and_home_screen", 0) != 1) {
            try {
                findViewById.setBackground(resizeDrawable(WallpaperManager.getInstance(this).semGetDrawable(5)));
                SemGfxImageFilter semGfxImageFilter = new SemGfxImageFilter();
                semGfxImageFilter.setBlurRadius(200.0f);
                findViewById.semSetGfxImageFilter(semGfxImageFilter);
            } catch (ClassCastException e) {
                Log.e("FlexPanelActivity", "ClassCastException : " + e.toString());
            } catch (IllegalArgumentException e2) {
                Log.e("FlexPanelActivity", "IllegalArgumentException : " + e2.toString());
            } catch (NullPointerException e3) {
                Log.e("FlexPanelActivity", "NullPointerException : " + e3.toString());
            }
        }
        this.mPanelView = (LinearLayout) findViewById(R.id.main_floating_area);
        this.mGridView = (GridView) findViewById(R.id.gridView);
        this.mUpperArea = (LinearLayout) findViewById(R.id.upper_area);
        BrightnessVolumeView brightnessVolumeView = (BrightnessVolumeView) findViewById(R.id.brightness_volume_view);
        this.mBrightnessVolumeView = brightnessVolumeView;
        brightnessVolumeView.mGridUIManager = this;
        GridPanelAdapter gridPanelAdapter = new GridPanelAdapter(this, this.mIsEditPanel);
        gridPanelAdapter.mOnClickListener = this;
        gridPanelAdapter.mOnLongClickListener = this;
        gridPanelAdapter.mOnDragListener = this;
        if (getActionArray("edit_panel_action_list", true).size() + getActionArray("basic_panel_action_list", true).size() != GridItems.ALL_ACTIONS.size()) {
            setPreferences("panel_init", true);
            this.mPanelInit = true;
        } else {
            ArrayList actionArray = getActionArray("basic_panel_action_list", true);
            actionArray.addAll(getActionArray("edit_panel_action_list", true));
            if (new HashSet(actionArray).size() != actionArray.size()) {
                setPreferences("panel_init", true);
                this.mPanelInit = true;
            }
        }
        if (this.mPanelInit) {
            this.mActions = new ArrayList(GridItems.ACTIVITY_BASIC);
            this.mEditActions = new ArrayList(GridItems.ACTIVITY_EDIT_BASIC);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = this.mActions;
            int size = arrayList2.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                arrayList.add(String.valueOf(((ControlPanelAction.Action) obj).getValue()));
            }
            setActionArray("basic_panel_action_list", arrayList);
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = this.mEditActions;
            int size2 = arrayList4.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList4.get(i3);
                i3++;
                arrayList3.add(String.valueOf(((ControlPanelAction.Action) obj2).getValue()));
            }
            setActionArray("edit_panel_action_list", arrayList3);
        } else {
            this.mActions = new ArrayList();
            this.mEditActions = new ArrayList();
            ArrayList actionArray2 = getActionArray("basic_panel_action_list", false);
            int size3 = actionArray2.size();
            boolean z = false;
            int i4 = 0;
            while (i4 < size3) {
                Object obj3 = actionArray2.get(i4);
                i4++;
                String str = (String) obj3;
                if (Integer.parseInt(str) == ControlPanelAction.Action.EditPanel.getValue()) {
                    z = true;
                }
                ArrayList arrayList5 = GridItems.ALL_ACTIONS;
                int size4 = arrayList5.size();
                int i5 = 0;
                while (i5 < size4) {
                    Object obj4 = arrayList5.get(i5);
                    i5++;
                    ControlPanelAction.Action action = (ControlPanelAction.Action) obj4;
                    if (Integer.parseInt(str) == action.getValue()) {
                        this.mActions.add(action);
                    }
                }
            }
            ArrayList actionArray3 = getActionArray("edit_panel_action_list", false);
            int size5 = actionArray3.size();
            int i6 = 0;
            while (i6 < size5) {
                Object obj5 = actionArray3.get(i6);
                i6++;
                String str2 = (String) obj5;
                ArrayList arrayList6 = GridItems.ALL_ACTIONS;
                int size6 = arrayList6.size();
                int i7 = 0;
                while (i7 < size6) {
                    Object obj6 = arrayList6.get(i7);
                    i7++;
                    ControlPanelAction.Action action2 = (ControlPanelAction.Action) obj6;
                    if (Integer.parseInt(str2) == action2.getValue()) {
                        this.mEditActions.add(action2);
                    }
                }
            }
            if (!z) {
                this.mActions.add(ControlPanelAction.Action.EditPanel);
            }
        }
        ArrayList arrayList7 = this.mActions;
        int size7 = arrayList7.size();
        while (i < size7) {
            Object obj7 = arrayList7.get(i);
            i++;
            gridPanelAdapter.items.add((ControlPanelAction.Action) obj7);
        }
        gridPanelAdapter.notifyDataSetChanged();
        this.mGridAdapter = gridPanelAdapter;
        onGridViewChanged();
        this.mGridView.setAdapter((ListAdapter) this.mGridAdapter);
        if (mIsFold) {
            GridView gridView = this.mGridView;
            ControlPanelUtils.setRatioPadding(this, gridView, 0.0d, 2.1d, 0.0d, 2.1d);
            gridView.setVerticalSpacing(getRatioSize(1.2d, this.mY));
        }
    }

    public final void setupMediaPanel() {
        if (!CheckControlWindowState.isSupportButton(this.mMediaController)) {
            setupBasicPanel();
            return;
        }
        setPreferences("MEDIA_PANEL", true);
        this.mIsMediaPanel = true;
        this.mIsEditPanel = false;
        setContentView(R.layout.flex_media_panel_layout);
        setupCommonPart();
        this.mMediaView = (LinearLayout) findViewById(R.id.media_background_area);
        this.mFlexMediaPanel = new FlexMediaPanel(this, (LinearLayout) findViewById(R.id.media_area), this.mMediaController);
        this.mTouchPadMediaPanel = null;
        this.mMediaController.registerCallback(this.mCallback);
        this.mCallback.onMediaControllerConnected(this.mMediaController);
        this.mMediaView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.14
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                FlexPanelActivity.this.mMediaView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                flexPanelActivity.mMediaView.startAnimation(flexPanelActivity.mFadeIn);
            }
        });
        removeTouchPad(false);
        setPreferences("MEDIA_TOUCH_PAD_ENABLED", false);
    }

    public final void setupTouchPadMediaPanel() {
        this.mIsMediaPanel = true;
        this.mIsEditPanel = false;
        setContentView(R.layout.touchpad_media_panel_layout);
        setupCommonPart();
        this.mMediaView = (LinearLayout) findViewById(R.id.touchpad_media_background_area);
        this.mTouchPadMediaPanel = new TouchPadMediaPanel(this, (LinearLayout) findViewById(R.id.touchpad_media_area), this.mMediaController);
        this.mFlexMediaPanel = null;
        this.mMediaController.registerCallback(this.mCallback);
        this.mCallback.onMediaControllerConnected(this.mMediaController);
        this.mMediaView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.15
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                FlexPanelActivity.this.mMediaView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                flexPanelActivity.mMediaView.startAnimation(flexPanelActivity.mFadeIn);
            }
        });
        setPreferences("MEDIA_TOUCH_PAD_ENABLED", true);
        displayTouchPadIfNeed();
    }

    public final void updateStatusPreferences(boolean z) {
        int i;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        getActionArrayForStatusLogging("basic_panel_action_list", arrayList);
        getActionArrayForStatusLogging("edit_panel_action_list", arrayList);
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            Integer num = (Integer) obj;
            if (num.intValue() == ControlPanelAction.Action.TouchPad.getValue()) {
                arrayList2.add(this.mSharedPreferences.getBoolean("TOUCH_PAD_ENABLED", true) ? "Touchpad on" : "Touchpad off");
            } else {
                arrayList2.add(ControlPanelAction.getLoggingID(this, num.intValue()));
            }
        }
        this.mSharedPreferences.edit().putString(SystemUIAnalytics.FLEX_TOOLBAR_FUNCTION_STATUS, arrayList2.toString()).apply();
        if (z) {
            SharedPreferences.Editor edit = this.mSharedPreferences.edit();
            String string = this.mSharedPreferences.getString("basic_panel_action_list", null);
            if (string == null) {
                i = 4;
            } else {
                try {
                    JSONArray jSONArray = new JSONArray(string);
                    i = 0;
                    while (i2 < jSONArray.length()) {
                        try {
                            String string2 = jSONArray.getString(i2);
                            if (!"0".equals(string2) && !"8".equals(string2) && !"7".equals(string2)) {
                                i++;
                            }
                            i2++;
                        } catch (JSONException e) {
                            e = e;
                            i2 = i;
                            e.printStackTrace();
                            i = i2;
                            edit.putInt(SystemUIAnalytics.FLEX_NUMBER_OF_TOOLS_STATUS, i).apply();
                        }
                    }
                } catch (JSONException e2) {
                    e = e2;
                }
            }
            edit.putInt(SystemUIAnalytics.FLEX_NUMBER_OF_TOOLS_STATUS, i).apply();
        }
    }
}
