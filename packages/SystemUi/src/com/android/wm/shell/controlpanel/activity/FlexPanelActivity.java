package com.android.wm.shell.controlpanel.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.SemWallpaperColors;
import android.app.TaskStackListener;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.devicestate.DeviceStateManager;
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
import android.os.UserHandle;
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
import android.view.accessibility.AccessibilityManager;
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
import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.wm.shell.controlpanel.GridPanelAdapter;
import com.android.wm.shell.controlpanel.GridUIManager;
import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import com.android.wm.shell.controlpanel.action.FlexPanelSettingsAction;
import com.android.wm.shell.controlpanel.action.GridItems;
import com.android.wm.shell.controlpanel.action.MenuActionType;
import com.android.wm.shell.controlpanel.action.QuickPanelAction;
import com.android.wm.shell.controlpanel.action.ScreenCaptureAction;
import com.android.wm.shell.controlpanel.action.SplitScreenAction;
import com.android.wm.shell.controlpanel.action.TouchPadAction;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.audio.AudioCallback;
import com.android.wm.shell.controlpanel.utils.CheckControlWindowState;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import com.android.wm.shell.controlpanel.widget.BrightnessVolumeView;
import com.android.wm.shell.controlpanel.widget.CustomWheelView;
import com.android.wm.shell.controlpanel.widget.WheelScrollView;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.R;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;
import com.samsung.android.widget.SemTipPopup;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FlexPanelActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, View.OnDragListener, GridUIManager {
    public static final int mEditPanelItemSize;
    public static final boolean mIsFold;
    public static FlexPanelActivity sFlexPanelActivity;
    public static boolean sTalkbackEnabled;
    public Interpolator i_22_25_0_1;
    public AccessibilityManager mAccessibilityManager;
    public ArrayList mActions;
    public ActivityManager mActivityManager;
    public int mBaseDeviceState;
    public int mBasicGridViewHeight;
    public int mBasicGridViewWidth;
    public BrightnessVolumeView mBrightnessVolumeView;
    public boolean mCloseState;
    public Map mCustomDimen;
    public SemDesktopModeManager mDesktopModeManager;
    public int mDeviceState;
    public DeviceStateManager mDeviceStateManager;
    public ControlPanelAction.Action mDraggedAction;
    public ArrayList mEditActions;
    public EventReceiver mEventReceiver;
    public Animation mFadeIn;
    public Animation mFadeOut;
    public float mFirstScrollTouchedPosition;
    public FlexMediaPanel mFlexMediaPanel;
    public GridPanelAdapter mGridAdapter;
    public GridLayout mGridLayout;
    public GridView mGridView;
    public InputMonitor mInputMonitor;
    public boolean mIsDisplayTouchPad;
    public boolean mIsEditPanel;
    public boolean mIsMediaPanel;
    public float mLastScrollPosition;
    public MediaController mMediaController;
    public MediaSessionManager mMediaSessionManager;
    public LinearLayout mMediaView;
    public FlexPanelActivity mOwnActivity;
    public boolean mPanelInit;
    public LinearLayout mPanelView;
    public int mPrevOrientation;
    public WheelScrollView mScrollWheel;
    public SharedPreferences mSharedPreferences;
    public Animation mSliderIn;
    public Animation mSliderOut;
    public ToolbarTipPopup mToolbarTipPopup;
    public boolean mTooltipInit;
    public TouchPad mTouchPad;
    public TouchPadMediaPanel mTouchPadMediaPanel;
    public LinearLayout mUpperArea;

    /* renamed from: mX */
    public int f441mX;

    /* renamed from: mY */
    public int f442mY;
    public int mBrightnessVolumeType = -1;
    public long mDragEnteredTime = 0;
    public boolean mForceTouchPadRemoved = false;
    public boolean mPendingShowTouchPad = false;
    public boolean mWindowAttached = false;
    public boolean mOnDragAnimation = false;
    public boolean mOnDragEnded = false;
    public boolean mIsResumeCalled = false;
    public final C39131 mReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.getClass();
            action.hashCode();
            switch (action) {
                case "android.media.VOLUME_CHANGED_ACTION":
                    if (!ControlPanelUtils.isClockActivity(context)) {
                        BrightnessVolumeView brightnessVolumeView = FlexPanelActivity.this.mBrightnessVolumeView;
                        int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                        int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
                        int intExtra3 = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
                        if (!brightnessVolumeView.mVolumeSeekBarTracking) {
                            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("setVolumeProgress streamType : ", intExtra, ", newVolume : ", intExtra2, ", oldVolume : ");
                            m45m.append(intExtra3);
                            Log.i("BrightnessVolumeView", m45m.toString());
                            brightnessVolumeView.setVolumeSeekBar(intExtra);
                            brightnessVolumeView.setVolumeIcon(intExtra);
                            break;
                        }
                    }
                    break;
                case "com.samsung.android.app.screenrecorder.on":
                    FlexPanelActivity.this.mIsScreenRecordingStarted = true;
                    break;
                case "android.intent.action.COLLAPSE_FLEX_PANEL":
                    if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                        ControlPanelUtils.eventLogging("F004", "a", new HashMap());
                    }
                    FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                    int i = FlexPanelActivity.mEditPanelItemSize;
                    flexPanelActivity.closeOperation();
                    break;
                case "android.intent.action.CLOSE_SYSTEM_DIALOGS":
                    if (ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_RECENTS.equalsIgnoreCase(intent.getStringExtra("reason"))) {
                        FlexPanelActivity.this.mGridAdapter.notifyDataSetChanged();
                        break;
                    }
                    break;
                case "com.samsung.android.app.screenrecorder.off":
                    FlexPanelActivity.this.mIsScreenRecordingStarted = false;
                    break;
            }
        }
    };
    public final C39242 mBrightnessObserver = new ContentObserver(new Handler()) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.2
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
    public final C39273 mModeEnableObserver = new C39273(new Handler());
    public final C39284 mTalkbackObserver = new ContentObserver(new Handler()) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.4
        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            String string = Settings.Secure.getString(FlexPanelActivity.this.getContentResolver(), "enabled_accessibility_services");
            FlexPanelActivity.sTalkbackEnabled = string != null && string.contains(ControlPanelUtils.TALKBACK_SERVICE);
            if (ControlPanelUtils.isWheelActive(FlexPanelActivity.this.mOwnActivity)) {
                FlexPanelActivity.this.removeTouchPad();
                FlexPanelActivity.this.displayTouchPadIfNeed();
            }
        }
    };
    public final C39295 mDeviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.5
        public final void onBaseStateChanged(int i) {
            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
            flexPanelActivity.mBaseDeviceState = i;
            if (flexPanelActivity.mDeviceState != 4 || i == 2) {
                return;
            }
            FlexPanelActivity.m2747$$Nest$monTableModeChanged(flexPanelActivity);
        }

        public final void onStateChanged(int i) {
            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
            flexPanelActivity.mDeviceState = i;
            boolean z = true;
            if (i != 4 ? i != 2 : flexPanelActivity.mBaseDeviceState != 2) {
                z = false;
            }
            if (z) {
                return;
            }
            FlexPanelActivity.m2747$$Nest$monTableModeChanged(flexPanelActivity);
        }
    };
    public final Impl mTaskStackListener = new Impl();
    public final FlexPanelActivity$$ExternalSyntheticLambda0 mDesktopModeListener = new SemDesktopModeManager.DesktopModeListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda0
        public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
            int i = FlexPanelActivity.mEditPanelItemSize;
            flexPanelActivity.getClass();
            if (semDesktopModeState.getEnabled() == 4 && semDesktopModeState.getState() == 50) {
                flexPanelActivity.runOnUiThread(new FlexPanelActivity$$ExternalSyntheticLambda2(flexPanelActivity, 6));
            }
        }
    };
    public int mScrollVibrationThreshold = 30;
    public boolean mIsScreenRecordingStarted = false;
    public int mImmersiveState = 0;
    public boolean mIsDimTouched = false;
    public final HandlerC3934H mDimHandler = new HandlerC3934H();
    public final FlexPanelActivity$$ExternalSyntheticLambda1 mActiveSessionsChangedListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda1
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
    public final C392520 mCallback = new C392520();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$16 */
    public final class ViewTreeObserverOnGlobalLayoutListenerC392016 implements ViewTreeObserver.OnGlobalLayoutListener {
        public final /* synthetic */ LinearLayout val$editPanelView;

        public ViewTreeObserverOnGlobalLayoutListenerC392016(LinearLayout linearLayout) {
            this.val$editPanelView = linearLayout;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            FlexPanelActivity.this.mPanelView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            if (FlexPanelActivity.this.findViewById(R.id.gridLayout) != null) {
                final FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                final LinearLayout linearLayout = flexPanelActivity.mPanelView;
                flexPanelActivity.getClass();
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
                    FlexPanelActivity.ViewTreeObserverOnGlobalLayoutListenerC392016 viewTreeObserverOnGlobalLayoutListenerC392016 = FlexPanelActivity.ViewTreeObserverOnGlobalLayoutListenerC392016.this;
                    LinearLayout linearLayout3 = linearLayout2;
                    if (FlexPanelActivity.this.findViewById(R.id.edit_panel_view) != null) {
                        linearLayout3.setVisibility(0);
                        linearLayout3.startAnimation(FlexPanelActivity.this.mSliderIn);
                    }
                }
            }, 100L);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$20 */
    public final class C392520 extends AudioCallback {
        public C392520() {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$3 */
    public final class C39273 extends ContentObserver {
        public C39273(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (Settings.Global.getInt(FlexPanelActivity.this.getContentResolver(), "flex_mode_panel_enabled", 1) != 1) {
                FlexPanelActivity.this.runOnUiThread(new FlexPanelActivity$$ExternalSyntheticLambda3(this, 1));
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EventReceiver extends InputEventReceiver {
        public EventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        public final void onInputEvent(InputEvent inputEvent) {
            boolean z = false;
            if (inputEvent instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                FlexPanelActivity.this.mDimHandler.removeMessages(1);
                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                if (flexPanelActivity.mIsMediaPanel && flexPanelActivity.mFlexMediaPanel != null) {
                    flexPanelActivity.mDimHandler.sendEmptyMessageDelayed(1, 5000L);
                }
                if (FlexPanelActivity.this.mImmersiveState != 0 && motionEvent.getAction() == 0) {
                    FlexMediaPanel flexMediaPanel = FlexPanelActivity.this.mFlexMediaPanel;
                    if (flexMediaPanel != null) {
                        flexMediaPanel.updateImmersiveState(false);
                    }
                    FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                    if (flexPanelActivity2.mIsDimTouched) {
                        flexPanelActivity2.mIsDimTouched = false;
                    } else {
                        FlexDimActivity flexDimActivity = FlexDimActivity.sFlexDimActivity;
                        if (flexDimActivity != null) {
                            flexDimActivity.finish();
                        }
                    }
                    FlexPanelActivity.this.mImmersiveState = 0;
                }
                if ((motionEvent.getFlags() & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) == 0 || FlexPanelActivity.this.mScrollWheel == null) {
                    finishInputEvent(inputEvent, true);
                    return;
                }
                if (motionEvent.getAction() == 0) {
                    FlexPanelActivity flexPanelActivity3 = FlexPanelActivity.this;
                    flexPanelActivity3.mLastScrollPosition = -2.1474836E9f;
                    flexPanelActivity3.mFirstScrollTouchedPosition = motionEvent.getY();
                }
                float y = motionEvent.getY();
                FlexPanelActivity flexPanelActivity4 = FlexPanelActivity.this;
                WheelScrollView wheelScrollView = flexPanelActivity4.mScrollWheel;
                int i = (int) (flexPanelActivity4.mFirstScrollTouchedPosition - y);
                CustomWheelView customWheelView = wheelScrollView.mCustomWheelView;
                if (customWheelView != null) {
                    customWheelView.updateScrollView(i);
                }
                float abs = Math.abs(y - FlexPanelActivity.this.mLastScrollPosition);
                FlexPanelActivity flexPanelActivity5 = FlexPanelActivity.this;
                if (abs > flexPanelActivity5.mScrollVibrationThreshold) {
                    flexPanelActivity5.mLastScrollPosition = y;
                    View view = flexPanelActivity5.mScrollWheel.mOverlayView;
                    if (view != null && !flexPanelActivity5.mIsScreenRecordingStarted) {
                        view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(50));
                    }
                }
                z = true;
            }
            finishInputEvent(inputEvent, z);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$H */
    public final class HandlerC3934H extends Handler {
        public HandlerC3934H() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            if (CoreRune.MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE) {
                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                if (flexPanelActivity.mIsMediaPanel && flexPanelActivity.mImmersiveState != 1 && flexPanelActivity.mFlexMediaPanel != null && FlexPanelActivity.sFlexPanelActivity != null && FlexDimActivity.sFlexDimActivity == null) {
                    AccessibilityManager accessibilityManager = flexPanelActivity.mAccessibilityManager;
                    if (!(accessibilityManager != null ? accessibilityManager.semIsScreenReaderEnabled() : false)) {
                        FlexPanelActivity.this.mFlexMediaPanel.updateImmersiveState(true);
                        FlexPanelActivity.this.mImmersiveState = 1;
                        Intent intent = new Intent(FlexPanelActivity.this.getApplicationContext(), (Class<?>) FlexDimActivity.class);
                        ActivityOptions makeBasic = ActivityOptions.makeBasic();
                        makeBasic.setLaunchTaskId(FlexPanelActivity.this.getTaskId());
                        makeBasic.setTaskOverlay(true, true);
                        intent.addFlags(805371904);
                        FlexPanelActivity.this.startActivityAsUser(intent, makeBasic.toBundle(), UserHandle.CURRENT);
                        return;
                    }
                }
            }
            StringBuilder sb = new StringBuilder("Cancel FlexDimActivity: mIsMediaPanel=");
            sb.append(FlexPanelActivity.this.mIsMediaPanel);
            sb.append(" mImmersiveState=");
            sb.append(FlexPanelActivity.this.mImmersiveState);
            sb.append(" mFlexMediaPanel=");
            sb.append(FlexPanelActivity.this.mFlexMediaPanel != null);
            sb.append(" sFlexPanelActivity=");
            sb.append(FlexPanelActivity.sFlexPanelActivity != null);
            sb.append(" sFlexDimActivity=");
            sb.append(FlexDimActivity.sFlexDimActivity != null);
            sb.append(" usingTalkBack=");
            AccessibilityManager accessibilityManager2 = FlexPanelActivity.this.mAccessibilityManager;
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, accessibilityManager2 != null ? accessibilityManager2.semIsScreenReaderEnabled() : false, "FlexPanelActivity");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Impl extends TaskStackListener {
        public Impl() {
        }

        public final void onTaskFocusChanged(int i, boolean z) {
            final int i2 = 2;
            List<ActivityManager.RunningTaskInfo> runningTasks = FlexPanelActivity.this.mActivityManager.getRunningTasks(2);
            final int i3 = 0;
            if (runningTasks.get(0).semIsFreeform() || 2 == runningTasks.get(0).getWindowingMode()) {
                return;
            }
            final int i4 = 1;
            ActivityManager.RunningTaskInfo runningTaskInfo = "com.android.wm.shell.controlpanel.activity.FlexPanelActivity".equalsIgnoreCase(runningTasks.get(0).baseActivity.getShortClassName()) ? runningTasks.get(1) : runningTasks.get(0);
            String packageName = runningTaskInfo.baseActivity.getPackageName();
            int topTaskUserId = ControlPanelUtils.getTopTaskUserId(FlexPanelActivity.this.mOwnActivity);
            if (MultiWindowUtils.isKeepFlexPanelTask(packageName) || (SemWindowManager.getInstance().getSupportsFlexPanel(topTaskUserId, packageName) & 2) == 0) {
                FlexPanelActivity.this.runOnUiThread(new Runnable(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$Impl$$ExternalSyntheticLambda0
                    public final /* synthetic */ FlexPanelActivity.Impl f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                                int i5 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity.mCloseState = true;
                                break;
                            case 1:
                                FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                                int i6 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity2.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity2.mCloseState = true;
                                flexPanelActivity2.removeTouchPadImmediate();
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
                FlexPanelActivity.this.runOnUiThread(new Runnable(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$Impl$$ExternalSyntheticLambda0
                    public final /* synthetic */ FlexPanelActivity.Impl f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                                int i5 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity.mCloseState = true;
                                break;
                            case 1:
                                FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                                int i6 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity2.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity2.mCloseState = true;
                                flexPanelActivity2.removeTouchPadImmediate();
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
                FlexPanelActivity.this.runOnUiThread(new Runnable(this) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$Impl$$ExternalSyntheticLambda0
                    public final /* synthetic */ FlexPanelActivity.Impl f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i4) {
                            case 0:
                                FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                                int i5 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity.mCloseState = true;
                                break;
                            case 1:
                                FlexPanelActivity flexPanelActivity2 = FlexPanelActivity.this;
                                int i6 = FlexPanelActivity.mEditPanelItemSize;
                                flexPanelActivity2.getClass();
                                if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                                    ControlPanelUtils.eventLogging("F004", "c", new HashMap());
                                }
                                flexPanelActivity2.mCloseState = true;
                                flexPanelActivity2.removeTouchPadImmediate();
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

    /* renamed from: -$$Nest$monTableModeChanged, reason: not valid java name */
    public static void m2747$$Nest$monTableModeChanged(FlexPanelActivity flexPanelActivity) {
        flexPanelActivity.getClass();
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
            ControlPanelUtils.eventLogging("F004", "b", new HashMap());
        }
        if (flexPanelActivity.mOwnActivity.semIsResumed()) {
            flexPanelActivity.runOnUiThread(new FlexPanelActivity$$ExternalSyntheticLambda2(flexPanelActivity, 7));
            return;
        }
        FlexDimActivity flexDimActivity = FlexDimActivity.sFlexDimActivity;
        if (flexDimActivity != null && !flexDimActivity.isFinishing()) {
            FlexDimActivity.sFlexDimActivity.finish();
        }
        flexPanelActivity.removeTouchPadImmediate();
        if (((ComponentActivity) flexPanelActivity).mLifecycleRegistry.mState != Lifecycle.State.DESTROYED && flexPanelActivity.mIsResumeCalled) {
            MultiWindowManager.getInstance().dismissSplitTask(flexPanelActivity.getActivityToken(), false);
        }
        flexPanelActivity.finish();
    }

    static {
        mEditPanelItemSize = ControlPanelUtils.isTypeFold() ? 5 : 4;
        mIsFold = ControlPanelUtils.isTypeFold();
        sTalkbackEnabled = false;
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
        ControlPanelUtils.eventLogging(getPreferences$1("MEDIA_PANEL") ? "F003" : "F002", getString(i), map);
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
                        flexPanelActivity8.removeTouchPad();
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
                    return z ? (i2 - 1) + 10 : i3;
                }
            }
        }
        return i;
    }

    public final void closeOperation() {
        removeTouchPadImmediate();
        this.mGridAdapter.notifyDataSetChanged();
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        this.mPanelView.startAnimation(loadAnimation);
        if (this.mIsMediaPanel) {
            this.mMediaView.startAnimation(loadAnimation);
        }
        this.mCloseState = true;
        finish();
    }

    public final void displayTouchPadIfNeed() {
        if (!this.mForceTouchPadRemoved && ControlPanelUtils.isTouchPadEnabled(this.mSharedPreferences) && !this.mPendingShowTouchPad && this.mWindowAttached) {
            int rotation = getDisplay().getRotation();
            if (mIsFold && (rotation == 0 || 2 == rotation)) {
                this.mIsDisplayTouchPad = true;
                return;
            }
            if (this.mIsEditPanel) {
                this.mIsDisplayTouchPad = true;
                return;
            }
            if (this.mTouchPad == null) {
                TouchPad touchPad = new TouchPad(this, this.mIsMediaPanel);
                this.mTouchPad = touchPad;
                touchPad.showView();
            }
            if (CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL && ControlPanelUtils.isWheelActive(this) && this.mScrollWheel == null && !sTalkbackEnabled) {
                WheelScrollView wheelScrollView = new WheelScrollView(this, this.mIsMediaPanel);
                this.mScrollWheel = wheelScrollView;
                wheelScrollView.showView();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x010e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void excuteAct(int i) {
        ControlPanelAction.Action action;
        MenuActionType createAction;
        boolean z;
        char c;
        boolean z2;
        ComponentName topActivity = ControlPanelUtils.getTopActivity(this);
        ((HashMap) this.mCustomDimen).put("packageName", topActivity.getPackageName());
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
            if (ControlPanelAction.Action.TouchPad.getValue() == i) {
                boolean preferences$1 = getPreferences$1("TOUCH_PAD_ENABLED");
                int i2 = R.string.touchpad_off_sa_logging;
                int i3 = preferences$1 ? R.string.touchpad_off_sa_logging : R.string.touchpad_on_sa_logging;
                if (getPreferences$1("MEDIA_PANEL")) {
                    if (!getPreferences$1("MEDIA_TOUCH_PAD_ENABLED")) {
                        i2 = R.string.touchpad_on_sa_logging;
                    }
                    i3 = i2;
                }
                buttonLoggingByString(i3, this.mCustomDimen);
            } else if (ControlPanelAction.Action.EditPanel.getValue() == i) {
                buttonLoggingByString(this.mIsEditPanel ? R.string.close_toolbar_sa_logging : R.string.expand_toolbar_sa_logging, this.mCustomDimen);
            } else {
                Map map = this.mCustomDimen;
                ControlPanelUtils.eventLogging(getPreferences$1("MEDIA_PANEL") ? "F003" : "F002", ControlPanelAction.getLoggingID(i, this), map);
                ControlPanelUtils.eventLogging("F005", ControlPanelAction.getLoggingID(i, this), map);
            }
        }
        int i4 = 0;
        if (ControlPanelAction.Action.VolumeControl.getValue() == i) {
            setupBrightnessVolumeView(R.id.media_volume_layout, 0);
            return;
        }
        int i5 = 1;
        if (ControlPanelAction.Action.BrightnessControl.getValue() != i) {
            if (ControlPanelAction.Action.EditPanel.getValue() != i) {
                if (ControlPanelAction.Action.DragCircle.getValue() == i || ControlPanelAction.Action.None.getValue() == i) {
                    return;
                }
                ArrayList arrayList = ControlPanelAction.mActionType;
                ControlPanelAction.Action[] values = ControlPanelAction.Action.values();
                int length = values.length;
                while (true) {
                    if (i4 >= length) {
                        action = ControlPanelAction.Action.None;
                        break;
                    }
                    action = values[i4];
                    if (action.getValue() == i) {
                        break;
                    } else {
                        i4++;
                    }
                }
                int i6 = MenuActionType.AbstractC39081.f440xc89be842[action.ordinal()];
                if (i6 == 1) {
                    createAction = ScreenCaptureAction.createAction(this);
                } else if (i6 == 2) {
                    createAction = QuickPanelAction.createAction(this);
                } else if (i6 == 3) {
                    createAction = SplitScreenAction.createAction(this);
                } else if (i6 == 4) {
                    createAction = FlexPanelSettingsAction.createAction(this);
                } else {
                    if (i6 != 5) {
                        throw new IllegalArgumentException("Wrong action");
                    }
                    createAction = TouchPadAction.createAction();
                }
                createAction.doControlAction(topActivity.getClassName(), this);
                return;
            }
            if (this.mIsEditPanel) {
                this.mBasicGridViewWidth = this.mGridView.getWidth();
                this.mBasicGridViewHeight = mIsFold ? this.mGridView.getHeight() : this.mUpperArea.getHeight();
                findViewById(R.id.edit_panel_view).startAnimation(this.mSliderOut);
                new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda2(this, i4), 100L);
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
                new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda2(this, i5), 350L);
                return;
            }
            setPreferences("MEDIA_PANEL", this.mIsMediaPanel);
            this.mIsMediaPanel = false;
            this.mIsEditPanel = true;
            this.mBasicGridViewWidth = this.mGridView.getWidth();
            boolean z3 = mIsFold;
            this.mBasicGridViewHeight = z3 ? this.mGridView.getHeight() : this.mUpperArea.getHeight();
            setContentView(R.layout.edit_panel_layout);
            if (this.mTooltipInit) {
                setPreferences("tooltip_init", false);
                this.mTooltipInit = false;
                ToolbarTipPopup toolbarTipPopup = new ToolbarTipPopup(this);
                this.mToolbarTipPopup = toolbarTipPopup;
                if (z3) {
                    int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.toolbar_tips_popup_right_margin_fold) + getResources().getDimensionPixelSize(R.dimen.basic_panel_left_margin) + ((int) ((this.f441mX * 6.76d) / 100.0d));
                    if (getResources().getConfiguration().getLayoutDirection() == 1) {
                        dimensionPixelSize = this.f441mX - dimensionPixelSize;
                    }
                    this.mToolbarTipPopup.requestShowPopUp(dimensionPixelSize, (this.f442mY * 75) / 100);
                } else {
                    toolbarTipPopup.requestShowPopUp(this.f441mX / 2, (((this.f442mY * 80) / 100) - getResources().getDimensionPixelSize(R.dimen.basic_panel_bottom_margin)) - getResources().getDimensionPixelSize(R.dimen.toolbar_tip_popup_bottom_margin));
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
            if (z3) {
                this.mGridLayout.setOrientation(1);
                this.mGridLayout.setRowCount(mEditPanelItemSize);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
                layoutParams.setMarginStart((int) ((this.f441mX * 0.2d) / 100.0d));
                layoutParams.setMarginEnd((int) ((this.f441mX * 1.7d) / 100.0d));
                layoutParams.topMargin = (int) ((this.f442mY * 2.1d) / 100.0d);
                this.mGridLayout.setLayoutParams(layoutParams);
                this.mPanelView.setY(getResources().getDimensionPixelSize(R.dimen.basic_panel_top_margin));
                this.mPanelView.setLayoutParams(new LinearLayout.LayoutParams(-2, (int) ((this.f442mY * 37.2d) / 100.0d)));
            } else {
                this.mGridLayout.setOrientation(0);
                this.mGridLayout.setColumnCount(mEditPanelItemSize);
                linearLayout2.setRotationX(180.0f);
                this.mPanelView.setY(getResources().getDimensionPixelSize(R.dimen.basic_panel_bottom_margin) * (-1));
                this.mPanelView.setLayoutParams(new LinearLayout.LayoutParams((int) ((this.f441mX * 88.9d) / 100.0d), -2));
                ControlPanelUtils.setRatioPadding(this, this.mGridLayout, 0.0d, 1.4d, 0.0d, 0.0d);
            }
            Iterator it = this.mEditActions.iterator();
            while (it.hasNext()) {
                this.mGridLayout.addView(getEditButton((ControlPanelAction.Action) it.next()));
            }
            this.mPanelView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserverOnGlobalLayoutListenerC392016(linearLayout2));
            if (this.mTouchPad != null) {
                removeTouchPad();
                return;
            }
            return;
        }
        if (Settings.System.getInt(getContentResolver(), "pms_notification_panel_brightness_adjustment", 1) != 0) {
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
                } catch (Exception unused) {
                    query.close();
                } catch (Throwable th) {
                    query.close();
                    throw th;
                }
                if (c == 65535 && c == 0) {
                    Log.d("ControlPanelUtils", "getSettingsChangeAllowed:false");
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (z2) {
                    z = true;
                    if (z) {
                        Toast.makeText(this, R.string.toast_can_not_change_brightness, 0).show();
                        return;
                    } else {
                        setupBrightnessVolumeView(R.id.media_brightness_layout, 1);
                        return;
                    }
                }
            }
            c = 65535;
            if (c == 65535) {
            }
            z2 = true;
            if (z2) {
            }
        }
        z = false;
        if (z) {
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
                    if (!z || (!DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.equals(string2) && !DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER.equals(string2))) {
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
                if (!DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.equals(string2) && !DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER.equals(string2) && !DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB.equals(string2)) {
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

    public final LinearLayout getEditButton(ControlPanelAction.Action action) {
        LinearLayout.LayoutParams ratioLayoutParams;
        LinearLayout linearLayout = new LinearLayout(this);
        boolean z = mIsFold;
        if (z) {
            linearLayout.setOrientation(0);
        } else {
            linearLayout.setOrientation(1);
        }
        linearLayout.setGravity(17);
        TextView textView = new TextView(this);
        if (z) {
            ratioLayoutParams = ControlPanelUtils.getRatioLayoutParams(this, 8.0d, 5.125d);
            ratioLayoutParams.setMarginStart((int) ((this.f441mX * 0.5d) / 100.0d));
        } else {
            ratioLayoutParams = ControlPanelUtils.getRatioLayoutParams(this, 17.8d, 3.3d);
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
        textView.setTextColor(ContextCompat.getColorStateList(R.color.panel_menu_icon_color_expand, this));
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
            layoutParams.setMarginStart((int) ((this.f441mX * 1.2d) / 100.0d));
        } else {
            linearLayout.setRotationX(180.0f);
            layoutParams.setMarginStart((int) ((this.f441mX * 1.1d) / 100.0d));
            layoutParams.setMarginEnd((int) ((this.f441mX * 1.1d) / 100.0d));
        }
        layoutParams.bottomMargin = (int) ((this.f442mY * 1.2d) / 100.0d);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
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

    public final boolean getPreferences$1(String str) {
        return this.mSharedPreferences.getBoolean(str, false);
    }

    public final void onActionArrayAdd(String str, int i, ControlPanelAction.Action action) {
        StringBuilder m92m = AbstractC0950x8906c950.m92m("onActionArrayAdd list :", str, " addAt : ", i, " addAction : ");
        m92m.append(action);
        Log.d("FlexPanelActivity", m92m.toString());
        if ("basic_panel_action_list".equals(str)) {
            this.mActions.add(i, action);
            this.mGridAdapter.items.add(i, action);
            onGridViewChanged();
            this.mGridView.setAdapter((ListAdapter) this.mGridAdapter);
        } else if ("edit_panel_action_list".equals(str) && this.mGridLayout != null) {
            this.mEditActions.add(i, action);
            this.mGridLayout.removeAllViews();
            Iterator it = this.mEditActions.iterator();
            while (it.hasNext()) {
                this.mGridLayout.addView(getEditButton((ControlPanelAction.Action) it.next()));
            }
        }
        ArrayList actionArray = getActionArray(str, false);
        actionArray.add(i, String.valueOf(action.getValue()));
        setActionArray(str, actionArray);
    }

    public final void onActionArrayRemove(int i, String str) {
        Log.d("FlexPanelActivity", "onActionArrayRemove list :" + str + " removeAt : " + i);
        if ("basic_panel_action_list".equals(str)) {
            this.mActions.remove(i);
            this.mGridAdapter.items.remove(i);
            onGridViewChanged();
            this.mGridView.setAdapter((ListAdapter) this.mGridAdapter);
        } else if ("edit_panel_action_list".equals(str) && this.mGridLayout != null) {
            this.mEditActions.remove(i);
            this.mGridLayout.removeAllViews();
            Iterator it = this.mEditActions.iterator();
            while (it.hasNext()) {
                this.mGridLayout.addView(getEditButton((ControlPanelAction.Action) it.next()));
            }
        }
        ArrayList actionArray = getActionArray(str, false);
        actionArray.remove(i);
        setActionArray(str, actionArray);
    }

    public final void onActionArrayRemoveAdd(String str, int i, int i2, ControlPanelAction.Action action) {
        StringBuilder m92m = AbstractC0950x8906c950.m92m("onActionArrayRemoveAdd list :", str, " removeAt : ", i, " addAt : ");
        m92m.append(i2);
        m92m.append(" addAction : ");
        m92m.append(action);
        Log.d("FlexPanelActivity", m92m.toString());
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
            Iterator it = this.mEditActions.iterator();
            while (it.hasNext()) {
                this.mGridLayout.addView(getEditButton((ControlPanelAction.Action) it.next()));
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
            excuteAct(((Integer) view.getTag(R.id.grid_button)).intValue());
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.21
                @Override // java.lang.Runnable
                public final void run() {
                    FlexPanelActivity.this.mGridAdapter.notifyDataSetChanged();
                }
            }, 100L);
        } else if (view.getTag(R.id.gridLayout) != null) {
            excuteAct(((Integer) view.getTag(R.id.gridLayout)).intValue());
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

    /* JADX WARN: Removed duplicated region for block: B:26:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01e4  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0181  */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) {
        SemWallpaperColors.Item item;
        int i;
        MediaController mediaController;
        super.onCreate(bundle);
        getWindow().addFlags(8);
        this.mOwnActivity = this;
        sFlexPanelActivity = this;
        String string = Settings.Secure.getString(getContentResolver(), "enabled_accessibility_services");
        sTalkbackEnabled = string != null && string.contains(ControlPanelUtils.TALKBACK_SERVICE);
        this.mActivityManager = (ActivityManager) getSystemService(ActivityManager.class);
        this.mCustomDimen = new HashMap();
        this.mSharedPreferences = getSharedPreferences("flex_panel_pref", 0);
        DeviceStateManager deviceStateManager = (DeviceStateManager) getSystemService(DeviceStateManager.class);
        this.mDeviceStateManager = deviceStateManager;
        deviceStateManager.registerCallback(getMainExecutor(), this.mDeviceStateCallback);
        ((HashMap) this.mCustomDimen).put("packageName", ControlPanelUtils.getTopActivity(this).getPackageName());
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
            if ("android.intent.action.EXPAND_FLEX_PANEL".equals(getIntent().getAction())) {
                ControlPanelUtils.eventLogging("F001", "a", this.mCustomDimen);
            } else if ("android.intent.action.AUTORUN_FLEX_PANEL".equals(getIntent().getAction())) {
                ControlPanelUtils.eventLogging("F001", "b", this.mCustomDimen);
            }
        }
        boolean z = Settings.Global.getInt(getContentResolver(), "low_power", 0) == 1;
        boolean z2 = Settings.Global.getInt(getContentResolver(), "sem_power_mode_limited_apps_and_home_screen", 0) == 1;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        if (!z || !z2) {
            try {
                try {
                    Bitmap bitmap = ((BitmapDrawable) resizeDrawable(wallpaperManager.semGetDrawable(5))).getBitmap();
                    Rect[] rectArr = {new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight())};
                    item = SemWallpaperColors.fromBitmap(this, bitmap, 0, 0, rectArr).get(rectArr[0]);
                } catch (ClassCastException e) {
                    Log.e("FlexPanelActivity", "ClassCastException : " + e.toString());
                } catch (IllegalArgumentException e2) {
                    Log.e("FlexPanelActivity", "IllegalArgumentException : " + e2.toString());
                } catch (NullPointerException e3) {
                    Log.e("FlexPanelActivity", "NullPointerException : " + e3.toString());
                }
            } catch (Throwable unused) {
            }
            if (item != null) {
                if (item.getFontColor() == 1) {
                    i = 1;
                    getDelegate().setLocalNightMode(i);
                    getWindow().getAttributes().samsungFlags |= 16777216;
                    getWindow().setDecorFitsSystemWindows(false);
                    this.mScrollVibrationThreshold = getResources().getDimensionPixelSize(R.dimen.scroll_wheel_haptic_threshold);
                    this.mMediaSessionManager = (MediaSessionManager) getSystemService("media_session");
                    this.mMediaSessionManager.addOnActiveSessionsChangedListener(this.mActiveSessionsChangedListener, null, Looper.myLooper() != null ? new Handler() : null);
                    this.mFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
                    this.mFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
                    this.mSliderIn = AnimationUtils.loadAnimation(this, R.anim.sliderin);
                    this.mSliderOut = AnimationUtils.loadAnimation(this, R.anim.sliderout);
                    this.i_22_25_0_1 = AnimationUtils.loadInterpolator(this, R.interpolator.i_22_25_0_1);
                    this.f441mX = ControlPanelUtils.getDisplayX(this);
                    this.f442mY = ControlPanelUtils.getDisplayY(this);
                    this.mMediaController = CheckControlWindowState.getMediaController(this, this.mMediaSessionManager);
                    this.mPanelInit = this.mSharedPreferences.getBoolean("panel_init", true);
                    this.mTooltipInit = this.mSharedPreferences.getBoolean("tooltip_init", true);
                    mediaController = this.mMediaController;
                    if (mediaController != null) {
                        mediaController.registerCallback(this.mCallback);
                    }
                    this.mPrevOrientation = getResources().getConfiguration().orientation;
                    if (Settings.System.getInt(getContentResolver(), "media_floating_only", 0) != 1 || CheckControlWindowState.isMediaPanelRequestedState(this, this.mMediaController)) {
                        setupMediaPanel();
                    } else {
                        if (bundle == null) {
                            this.mPendingShowTouchPad = true;
                        }
                        setupBasicPanel();
                    }
                    SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) getSystemService("desktopmode");
                    this.mDesktopModeManager = semDesktopModeManager;
                    semDesktopModeManager.registerListener(this.mDesktopModeListener);
                    if (CoreRune.MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE) {
                        this.mAccessibilityManager = AccessibilityManager.getInstance(this);
                    }
                    if (getWindow().getInsetsController() == null && i == 1) {
                        getWindow().getInsetsController().setSystemBarsAppearance(16, 16);
                        return;
                    }
                }
            }
        }
        i = 2;
        getDelegate().setLocalNightMode(i);
        getWindow().getAttributes().samsungFlags |= 16777216;
        getWindow().setDecorFitsSystemWindows(false);
        this.mScrollVibrationThreshold = getResources().getDimensionPixelSize(R.dimen.scroll_wheel_haptic_threshold);
        this.mMediaSessionManager = (MediaSessionManager) getSystemService("media_session");
        this.mMediaSessionManager.addOnActiveSessionsChangedListener(this.mActiveSessionsChangedListener, null, Looper.myLooper() != null ? new Handler() : null);
        this.mFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        this.mFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        this.mSliderIn = AnimationUtils.loadAnimation(this, R.anim.sliderin);
        this.mSliderOut = AnimationUtils.loadAnimation(this, R.anim.sliderout);
        this.i_22_25_0_1 = AnimationUtils.loadInterpolator(this, R.interpolator.i_22_25_0_1);
        this.f441mX = ControlPanelUtils.getDisplayX(this);
        this.f442mY = ControlPanelUtils.getDisplayY(this);
        this.mMediaController = CheckControlWindowState.getMediaController(this, this.mMediaSessionManager);
        this.mPanelInit = this.mSharedPreferences.getBoolean("panel_init", true);
        this.mTooltipInit = this.mSharedPreferences.getBoolean("tooltip_init", true);
        mediaController = this.mMediaController;
        if (mediaController != null) {
        }
        this.mPrevOrientation = getResources().getConfiguration().orientation;
        if (Settings.System.getInt(getContentResolver(), "media_floating_only", 0) != 1) {
        }
        setupMediaPanel();
        SemDesktopModeManager semDesktopModeManager2 = (SemDesktopModeManager) getSystemService("desktopmode");
        this.mDesktopModeManager = semDesktopModeManager2;
        semDesktopModeManager2.registerListener(this.mDesktopModeListener);
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE) {
        }
        if (getWindow().getInsetsController() == null) {
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE) {
            sFlexPanelActivity = null;
            this.mDimHandler.removeMessages(1);
        }
        if (CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL || CoreRune.MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE) {
            InputMonitor inputMonitor = this.mInputMonitor;
            if (inputMonitor != null) {
                inputMonitor.dispose();
                this.mInputMonitor = null;
            }
            EventReceiver eventReceiver = this.mEventReceiver;
            if (eventReceiver != null) {
                eventReceiver.dispose();
                this.mEventReceiver = null;
            }
        }
        unregisterReceiver(this.mReceiver);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(InputMethodManager.class);
        if (inputMethodManager != null && inputMethodManager.isInputMethodShown()) {
            new Handler().post(new FlexPanelActivity$$ExternalSyntheticLambda3(inputMethodManager, 0));
        }
        removeTouchPadImmediate();
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
        this.mDesktopModeManager.unregisterListener(this.mDesktopModeListener);
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
            int i = 2;
            if (action == 2) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime - this.mDragEnteredTime >= 450) {
                    this.mDragEnteredTime = elapsedRealtime;
                    int positionByAction2 = getPositionByAction(ControlPanelAction.Action.DragCircle.getValue());
                    int checkToValueNone = checkToValueNone(positionByAction, true);
                    if (positionByAction2 != -1 && positionByAction2 != checkToValueNone && (getActionByPosition(checkToValueNone) != ControlPanelAction.Action.EditPanel || (positionByAction2 >= 10 && this.mActions.size() != 5))) {
                        if (this.mOnDragAnimation) {
                            new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                                    View view2 = view;
                                    DragEvent dragEvent2 = dragEvent;
                                    int i2 = FlexPanelActivity.mEditPanelItemSize;
                                    flexPanelActivity.onDrag(view2, dragEvent2);
                                }
                            }, 450L);
                        } else {
                            if (this.mPanelInit) {
                                setPreferences("panel_init", false);
                                this.mPanelInit = false;
                            }
                            Log.d("FlexPanelActivity", "onDragAnimation start, from : " + positionByAction2 + " to : " + checkToValueNone);
                            onDragAnimation(positionByAction2, checkToValueNone);
                        }
                    }
                }
            } else if (action == 4 && !this.mOnDragEnded) {
                this.mOnDragEnded = true;
                new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda2(this, i), 450L);
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
        boolean z = true;
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
                int i4 = i;
                while (i4 > i2) {
                    View childAt2 = this.mGridView.getChildAt(i4);
                    i4--;
                    setDragAnimation(animatorSet, childAt2, this.mGridView.getChildAt(i4));
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
                    View childAt3 = this.mGridLayout.getChildAt(i5);
                    i5++;
                    setDragAnimation(animatorSet, childAt3, this.mGridLayout.getChildAt(i5));
                }
            } else {
                int i6 = checkFromValueNone - 10;
                while (i6 > checkToValueNone - 10) {
                    View childAt4 = this.mGridLayout.getChildAt(i6);
                    i6--;
                    setDragAnimation(animatorSet, childAt4, this.mGridLayout.getChildAt(i6));
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
            gridLayout2.addView(getEditButton(action));
            this.mEditActions.add(action);
            final GridLayout gridLayout3 = this.mGridLayout;
            gridLayout3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.10
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    gridLayout3.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    gridLayout3.getLeft();
                    int i7 = checkToValueNone2 - 10;
                    while (true) {
                        i7++;
                        if (i7 >= gridLayout3.getChildCount()) {
                            FlexPanelActivity.this.mEditActions.remove(r0.getPositionByAction(ControlPanelAction.Action.None.getValue()) - 10);
                            animatorSet.start();
                            return;
                        } else {
                            FlexPanelActivity flexPanelActivity = FlexPanelActivity.this;
                            AnimatorSet animatorSet2 = animatorSet;
                            View childAt5 = gridLayout3.getChildAt(i7);
                            View childAt6 = gridLayout3.getChildAt(i7 - 1);
                            int i8 = FlexPanelActivity.mEditPanelItemSize;
                            flexPanelActivity.getClass();
                            FlexPanelActivity.setDragAnimation(animatorSet2, childAt5, childAt6);
                        }
                    }
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
            int size2 = this.mActions.size() - 2;
            while (size2 > i2) {
                View childAt5 = this.mGridView.getChildAt(size2);
                size2--;
                setDragAnimation(animatorSet, childAt5, this.mGridView.getChildAt(size2));
            }
            int i7 = checkFromValueNone2 - 10;
            while (i7 > 0) {
                View childAt6 = this.mGridLayout.getChildAt(i7);
                i7--;
                setDragAnimation(animatorSet, childAt6, this.mGridLayout.getChildAt(i7));
            }
        } else {
            this.mGridLayout.getChildAt(i - 10).setVisibility(4);
            int i8 = checkFromValueNone2 - 10;
            while (i8 < this.mEditActions.size() - 1) {
                View childAt7 = this.mGridLayout.getChildAt(i8);
                i8++;
                setDragAnimation(animatorSet, childAt7, this.mGridLayout.getChildAt(i8));
            }
            if (mIsFold) {
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
            } else {
                animatorSet.start();
            }
            z = false;
        }
        if (z) {
            animatorSet.start();
        }
    }

    public final void onDragEnded() {
        if (this.mOnDragAnimation) {
            return;
        }
        int positionByAction = getPositionByAction(ControlPanelAction.Action.DragCircle.getValue());
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onDragEnded, dropPosition : ", positionByAction, "FlexPanelActivity");
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
                float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.basic_panel_top_margin);
                this.mBrightnessVolumeView.setY(dimensionPixelSize);
                this.mGridView.setY(dimensionPixelSize);
            }
            this.mUpperArea.setLayoutParams(new LinearLayout.LayoutParams((int) ((this.f441mX * 6.76d) / 100.0d), -1));
            this.mGridView.setGravity(17);
            this.mGridView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            return;
        }
        int size = this.mActions.size();
        this.mGridView.setNumColumns(size);
        double d = size != 1 ? size != 2 ? size != 3 ? size != 4 ? 88.9d : 71.5d : 56.1d : 37.7d : 23.3d;
        this.mUpperArea.setLayoutParams(new LinearLayout.LayoutParams(-2, (int) ((this.f442mY * 6.4d) / 100.0d)));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) ((this.f441mX * d) / 100.0d), (int) ((this.f442mY * 4.55d) / 100.0d));
        GridView gridView = this.mGridView;
        int i = this.f441mX;
        double d2 = d;
        gridView.setPadding((int) ((i * 6.08d) / 100.0d), 0, (int) ((i * 6.08d) / 100.0d), 0);
        layoutParams.gravity = 17;
        this.mGridView.setLayoutParams(layoutParams);
        int i2 = this.f441mX;
        int i3 = (int) ((i2 * 11.11d) / 100.0d);
        if (size > 1) {
            GridView gridView2 = this.mGridView;
            gridView2.setHorizontalSpacing((((((int) ((i2 * d2) / 100.0d)) - gridView2.getPaddingLeft()) - this.mGridView.getPaddingRight()) - (i3 * size)) / (size - 1));
        }
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        ControlPanelAction.Action action;
        if (this.mOnDragEnded) {
            return true;
        }
        if (view.getTag(R.id.grid_button) != null && ((Integer) view.getTag(R.id.grid_button)).intValue() == ControlPanelAction.Action.EditPanel.getValue()) {
            onClick(view);
            return true;
        }
        Iterator it = GridItems.ALL_ACTIONS.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ControlPanelAction.Action action2 = (ControlPanelAction.Action) it.next();
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
            final int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.grid_menu_layout_size);
            if (findViewById.startDragAndDrop(null, new View.DragShadowBuilder(findViewById) { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity.12
                @Override // android.view.View.DragShadowBuilder
                public final void onProvideShadowMetrics(Point point, Point point2) {
                    int i = dimensionPixelSize;
                    point.set(i * 2, i);
                    point2.set(findViewById.getWidth() / 2, FlexPanelActivity.this.getResources().getDimensionPixelSize(R.dimen.drag_selected_top_margin) + (findViewById.getHeight() / 2));
                }
            }, null, QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING)) {
                Log.d("FlexPanelActivity", "startDragAndDrop, mDraggedAction : " + this.mDraggedAction + " draggedPosition : " + positionByAction);
                view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
                if (view.getTag(R.id.gridLayout) == null || positionByAction < 10) {
                    onActionArrayRemoveAdd("basic_panel_action_list", positionByAction, positionByAction, action);
                } else {
                    int i = positionByAction - 10;
                    onActionArrayRemoveAdd("edit_panel_action_list", i, i, action);
                }
                if (this.mIsEditPanel) {
                    addEditPanelNone();
                }
            } else {
                Log.d("FlexPanelActivity", "startDrag fail");
                this.mGridView.setAdapter((ListAdapter) this.mGridAdapter);
                GridLayout gridLayout = this.mGridLayout;
                if (gridLayout != null) {
                    gridLayout.removeAllViews();
                    Iterator it2 = this.mEditActions.iterator();
                    while (it2.hasNext()) {
                        this.mGridLayout.addView(getEditButton((ControlPanelAction.Action) it2.next()));
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
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE) {
            this.mDimHandler.removeMessages(1);
        }
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
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE && this.mIsMediaPanel && this.mFlexMediaPanel != null) {
            this.mDimHandler.removeMessages(1);
            this.mDimHandler.sendEmptyMessageDelayed(1, 5000L);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.COLLAPSE_FLEX_PANEL");
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("com.samsung.android.app.screenrecorder.on");
        intentFilter.addAction("com.samsung.android.app.screenrecorder.off");
        registerReceiver(this.mReceiver, intentFilter, 2);
        boolean z = CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL;
        if (this.mInputMonitor == null) {
            this.mInputMonitor = InputManager.getInstance().monitorGestureInput("caption-touch", 0);
        }
        if (this.mEventReceiver == null) {
            this.mEventReceiver = new EventReceiver(this.mInputMonitor.getInputChannel(), Looper.myLooper());
        }
        getContentResolver().registerContentObserver(Settings.System.getUriFor("screen_brightness"), false, this.mBrightnessObserver);
        getContentResolver().registerContentObserver(Settings.Global.getUriFor("flex_mode_panel_enabled"), false, this.mModeEnableObserver);
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor("enabled_accessibility_services"), false, this.mTalkbackObserver);
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

    public final void removeTouchPad() {
        final WheelScrollView wheelScrollView;
        TouchPad touchPad = this.mTouchPad;
        if (touchPad != null) {
            View view = touchPad.mTouchPadBg;
            Context context = touchPad.mContext;
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeout));
            touchPad.mCenterText.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeout));
            touchPad.mTouchPadLine.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeout));
            new Handler(Looper.getMainLooper()).postDelayed(new TouchPad$$ExternalSyntheticLambda0(touchPad, 1), 100L);
            this.mTouchPad = null;
        }
        if (!CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL || (wheelScrollView = this.mScrollWheel) == null) {
            return;
        }
        CustomWheelView customWheelView = wheelScrollView.mCustomWheelView;
        if (customWheelView != null) {
            customWheelView.onFadeOutAnimation();
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.widget.WheelScrollView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WheelScrollView wheelScrollView2 = WheelScrollView.this;
                View view2 = wheelScrollView2.mOverlayView;
                if (view2 != null) {
                    view2.setVisibility(8);
                    if (wheelScrollView2.mOverlayView.isAttachedToWindow()) {
                        wheelScrollView2.removeView();
                    }
                }
            }
        }, 100L);
        this.mScrollWheel = null;
    }

    public final void removeTouchPadImmediate() {
        WheelScrollView wheelScrollView;
        TouchPad touchPad = this.mTouchPad;
        if (touchPad != null) {
            View view = touchPad.mOverlayView;
            if (view != null && view.isAttachedToWindow()) {
                touchPad.mWindowManager.removeViewImmediate(touchPad.mOverlayView);
            }
            this.mTouchPad = null;
        }
        if (!CoreRune.MW_SPLIT_IS_FLEX_SCROLL_WHEEL || (wheelScrollView = this.mScrollWheel) == null) {
            return;
        }
        View view2 = wheelScrollView.mOverlayView;
        if (view2 != null && view2.isAttachedToWindow()) {
            wheelScrollView.mWindowManager.removeViewImmediate(wheelScrollView.mOverlayView);
            wheelScrollView.mCustomWheelView = null;
            wheelScrollView.mOverlayView = null;
        }
        this.mScrollWheel = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable resizeDrawable(Drawable drawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        if (!mIsFold) {
            Point point = new Point();
            getWindowManager().getDefaultDisplay().getRealSize(point);
            int i = point.x;
            int i2 = point.y;
            if (bitmap != null && !bitmap.isRecycled()) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                float f = width;
                float f2 = f / 2.0f;
                float f3 = height;
                float f4 = f3 / 2.0f;
                float f5 = width * i2 > i * height ? i2 / f3 : i / f;
                SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("metricsHeight=", i2, " metricsWidth=", i, "FlexPanelActivity");
                float f6 = i / f5;
                float f7 = i2 / f5;
                float f8 = f2 - (f6 / 2.0f);
                if (f8 < 0.0f) {
                    f8 = 0.0f;
                }
                float f9 = f4 - (f7 / 2.0f);
                float f10 = f9 >= 0.0f ? f9 : 0.0f;
                if (Math.round(f8) == 0 && Math.round(f10) == 0 && width == Math.round(f6) && height == Math.round(f7)) {
                    Log.d("FlexPanelActivity", "It doesn't need to crop bitmap");
                } else if (Math.round(f6) < 1 || Math.round(f7) < 1 || i < 1 || i2 < 1) {
                    Log.d("FlexPanelActivity", "Math.round(width) < 1 || Math.round(height) < 1 || mMetricsWidth < 1 || mMetricsHeight < 1");
                } else {
                    if (Math.round(f8) + Math.round(f6) <= width) {
                        if (Math.round(f10) + Math.round(f7) <= height) {
                            Log.d("FlexPanelActivity", "Cropping...");
                            bitmap = Bitmap.createBitmap(bitmap, Math.round(f8), Math.round(f10), Math.round(f6), Math.round(f7));
                        }
                    }
                    Log.d("FlexPanelActivity", "Calculated crop size error");
                }
                if (bitmap == null) {
                    bitmap = bitmapDrawable.getBitmap();
                }
            }
            bitmap = null;
            if (bitmap == null) {
            }
        }
        return new BitmapDrawable(getResources(), Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 2, bitmap.getWidth(), bitmap.getHeight() / 2));
    }

    public final void returnToMenu() {
        if (this.mBrightnessVolumeView.getVisibility() != 8) {
            this.mBrightnessVolumeView.startAnimation(this.mSliderOut);
            new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda2(this, 3), 100L);
        }
    }

    public final void setActionArray(String str, ArrayList arrayList) {
        SharedPreferences.Editor edit = this.mSharedPreferences.edit();
        JSONArray jSONArray = new JSONArray();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            jSONArray.put((String) it.next());
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
            new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda6
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
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MEDIA_IMMERSIVE_MODE) {
            this.mDimHandler.removeMessages(1);
            this.mIsDimTouched = false;
            if (this.mIsMediaPanel) {
                this.mDimHandler.sendEmptyMessageDelayed(1, 5000L);
            }
            FlexDimActivity flexDimActivity = FlexDimActivity.sFlexDimActivity;
            if (flexDimActivity != null) {
                flexDimActivity.finish();
            }
            this.mImmersiveState = 0;
        }
        this.mGridLayout = this.mIsEditPanel ? (GridLayout) findViewById(R.id.gridLayout) : null;
        findViewById(R.id.wallpaper_area).setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda5
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
        boolean z = Settings.Global.getInt(getContentResolver(), "low_power", 0) == 1;
        boolean z2 = Settings.Global.getInt(getContentResolver(), "sem_power_mode_limited_apps_and_home_screen", 0) == 1;
        if (!z || !z2) {
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
            Iterator it = this.mActions.iterator();
            while (it.hasNext()) {
                arrayList.add(String.valueOf(((ControlPanelAction.Action) it.next()).getValue()));
            }
            setActionArray("basic_panel_action_list", arrayList);
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = this.mEditActions.iterator();
            while (it2.hasNext()) {
                arrayList2.add(String.valueOf(((ControlPanelAction.Action) it2.next()).getValue()));
            }
            setActionArray("edit_panel_action_list", arrayList2);
        } else {
            this.mActions = new ArrayList();
            this.mEditActions = new ArrayList();
            Iterator it3 = getActionArray("basic_panel_action_list", false).iterator();
            boolean z3 = false;
            while (it3.hasNext()) {
                String str = (String) it3.next();
                if (Integer.parseInt(str) == ControlPanelAction.Action.EditPanel.getValue()) {
                    z3 = true;
                }
                Iterator it4 = GridItems.ALL_ACTIONS.iterator();
                while (it4.hasNext()) {
                    ControlPanelAction.Action action = (ControlPanelAction.Action) it4.next();
                    if (Integer.parseInt(str) == action.getValue()) {
                        this.mActions.add(action);
                    }
                }
            }
            Iterator it5 = getActionArray("edit_panel_action_list", false).iterator();
            while (it5.hasNext()) {
                String str2 = (String) it5.next();
                Iterator it6 = GridItems.ALL_ACTIONS.iterator();
                while (it6.hasNext()) {
                    ControlPanelAction.Action action2 = (ControlPanelAction.Action) it6.next();
                    if (Integer.parseInt(str2) == action2.getValue()) {
                        this.mEditActions.add(action2);
                    }
                }
            }
            if (!z3) {
                this.mActions.add(ControlPanelAction.Action.EditPanel);
            }
        }
        Iterator it7 = this.mActions.iterator();
        while (it7.hasNext()) {
            gridPanelAdapter.items.add((ControlPanelAction.Action) it7.next());
        }
        gridPanelAdapter.notifyDataSetChanged();
        this.mGridAdapter = gridPanelAdapter;
        onGridViewChanged();
        this.mGridView.setAdapter((ListAdapter) this.mGridAdapter);
        if (mIsFold) {
            GridView gridView = this.mGridView;
            ControlPanelUtils.setRatioPadding(this, gridView, 0.0d, 2.1d, 0.0d, 2.1d);
            gridView.setVerticalSpacing((int) ((this.f442mY * 1.2d) / 100.0d));
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
        removeTouchPad();
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
        JSONException e;
        int i;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        getActionArrayForStatusLogging("basic_panel_action_list", arrayList);
        getActionArrayForStatusLogging("edit_panel_action_list", arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (num.intValue() == ControlPanelAction.Action.TouchPad.getValue()) {
                arrayList2.add(this.mSharedPreferences.getBoolean("TOUCH_PAD_ENABLED", true) ? "Touchpad on" : "Touchpad off");
            } else {
                arrayList2.add(ControlPanelAction.getLoggingID(num.intValue(), this));
            }
        }
        this.mSharedPreferences.edit().putString("F006", arrayList2.toString()).apply();
        if (z) {
            SharedPreferences.Editor edit = this.mSharedPreferences.edit();
            String string = this.mSharedPreferences.getString("basic_panel_action_list", null);
            if (string == null) {
                i = 4;
            } else {
                try {
                    JSONArray jSONArray = new JSONArray(string);
                    i = 0;
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        try {
                            String string2 = jSONArray.getString(i2);
                            if (!DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.equals(string2) && !DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER.equals(string2) && !DATA.DM_FIELD_INDEX.AMR_AUDIO_BITRATE_WB.equals(string2)) {
                                i++;
                            }
                        } catch (JSONException e2) {
                            e = e2;
                            e.printStackTrace();
                            edit.putInt("F007", i).apply();
                        }
                    }
                } catch (JSONException e3) {
                    e = e3;
                    i = 0;
                }
            }
            edit.putInt("F007", i).apply();
        }
    }
}
