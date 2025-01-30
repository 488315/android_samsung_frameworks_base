package com.android.server.accessibility.autoaction;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.accessibility.autoaction.actiontype.CornerActionType;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.widget.SemTipPopup;
import java.util.HashMap;

/* loaded from: classes.dex */
public class CornerActionController {
    public static final int[] POPUP_DIRECTION;
    public static final int[] POPUP_GRAVITY;
    public static final int[] TTS_CORNER;
    public static HashMap mGestureActionFlag;
    public ContentResolver mContentResolver;
    public final Context mContext;
    public String[] mCornerActions;
    public CornerActionType mDragAction;
    public String mGestureAction;
    public WindowManager.LayoutParams mParams;
    public int mScreenHeight;
    public int mScreenWidth;
    public View mTipAnchorView;
    public SemTipPopup mTipPopup;
    public final int mUserId;
    public WindowManager mWindowManager;
    public CornerActionCircleCue mDurationProgress = null;
    public boolean mIsAnimating = false;
    public ContentObserver mCornerActionTypeObserver = new ContentObserver(new Handler()) { // from class: com.android.server.accessibility.autoaction.CornerActionController.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            CornerActionController.this.setCornerAction();
        }
    };

    static {
        HashMap hashMap = new HashMap();
        mGestureActionFlag = hashMap;
        hashMap.put("double_click", 1);
        mGestureActionFlag.put("zoom_in", 2);
        mGestureActionFlag.put("zoom_out", 4);
        mGestureActionFlag.put("swipe_left", 8);
        mGestureActionFlag.put("swipe_right", 16);
        mGestureActionFlag.put("swipe_up", 32);
        mGestureActionFlag.put("swipe_down", 64);
        mGestureActionFlag.put("click_and_hold", 128);
        mGestureActionFlag.put("drag", 256);
        mGestureActionFlag.put("drag_and_drop", 512);
        POPUP_GRAVITY = new int[]{51, 53, 83, 85};
        POPUP_DIRECTION = new int[]{3, 2, 1, 0};
        TTS_CORNER = new int[]{R.string.accessibility_system_action_recents_label, R.string.accessibility_system_action_screenshot_label, R.string.accessibility_system_action_on_screen_a11y_shortcut_label, R.string.accessibility_system_action_power_dialog_label};
    }

    public CornerActionController(Context context, int i) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mUserId = i;
        initGestureActionTip();
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("accessibility_corner_actions"), false, this.mCornerActionTypeObserver, i);
        this.mCornerActionTypeObserver.onChange(true);
    }

    public int handleCornerAction(int i, int i2) {
        if (this.mCornerActions == null) {
            return -1;
        }
        CornerActionCircleCue cornerActionCircleCue = this.mDurationProgress;
        if (cornerActionCircleCue != null) {
            cornerActionCircleCue.announceForAccessibility(this.mContext.getString(TTS_CORNER[i]));
        }
        String[] split = this.mCornerActions[i].split(",");
        if (split.length > 1) {
            return 2;
        }
        return performCornerAction(split[0], i2, i);
    }

    public final void setCornerAction() {
        String stringForUser = Settings.Secure.getStringForUser(this.mContentResolver, "accessibility_corner_actions", this.mUserId);
        if (stringForUser != null) {
            this.mCornerActions = stringForUser.split(XmlUtils.STRING_ARRAY_SEPARATOR);
        }
    }

    public int getCorner(float f, float f2, int i) {
        Display display;
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
        if (displayManager != null && (display = displayManager.getDisplay(i)) != null) {
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            this.mScreenWidth = displayInfo.logicalWidth;
            this.mScreenHeight = displayInfo.logicalHeight;
        }
        if (f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON && f2 < 40.0f) {
            return 0;
        }
        if (f < 40.0f && f2 == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            return 0;
        }
        int i2 = this.mScreenWidth;
        if ((f == i2 - 1 && f2 < 40.0f) || (i2 - f < 40.0f && f2 == DisplayPowerController2.RATE_FROM_DOZE_TO_ON)) {
            return 1;
        }
        if (f < 40.0f && f2 == this.mScreenHeight - 1) {
            return 2;
        }
        if (f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON && this.mScreenHeight - f2 < 40.0f) {
            return 2;
        }
        if (i2 - f >= 40.0f || f2 != this.mScreenHeight - 1) {
            return (f != ((float) (i2 - 1)) || ((float) this.mScreenHeight) - f2 >= 40.0f) ? -1 : 3;
        }
        return 3;
    }

    public int performCornerAction(String str, int i, int i2) {
        str.hashCode();
        switch (str) {
            case "click_and_hold":
            case "zoom_in":
            case "swipe_up":
            case "drag":
            case "drag_and_drop":
            case "swipe_down":
            case "swipe_left":
            case "zoom_out":
            case "swipe_right":
            case "double_click":
                this.mGestureAction = str;
                int intForUser = Settings.Secure.getIntForUser(this.mContentResolver, "accessibility_corner_action_tip_shown", 0, this.mUserId);
                int intValue = ((Integer) mGestureActionFlag.get(str)).intValue();
                if ((intForUser & intValue) == 0) {
                    showGestureActionTip(str, i, i2);
                    Settings.Secure.putIntForUser(this.mContentResolver, "accessibility_corner_action_tip_shown", intForUser | intValue, this.mUserId);
                }
                return 1;
            case "pause_resume_auto_click":
                return 3;
            default:
                CornerActionType create = CornerActionType.create(str, this.mContext, this.mUserId);
                if (create != null) {
                    create.performCornerAction(i);
                }
                return 0;
        }
    }

    public int performGestureAction(MotionEvent motionEvent) {
        SemTipPopup semTipPopup = this.mTipPopup;
        if (semTipPopup != null && semTipPopup.isShowing()) {
            this.mTipPopup.dismiss(false);
        }
        String str = this.mGestureAction;
        str.hashCode();
        if (str.equals("drag") || str.equals("drag_and_drop")) {
            CornerActionType cornerActionType = this.mDragAction;
            if (cornerActionType != null) {
                cornerActionType.setMotionEventForDragAction(motionEvent);
                this.mDragAction.performCornerAction(motionEvent.getDisplayId());
                this.mDragAction = null;
                return 0;
            }
            CornerActionType create = CornerActionType.create(this.mGestureAction, this.mContext, motionEvent);
            this.mDragAction = create;
            if (create == null) {
                return 1;
            }
            create.performCornerAction(motionEvent.getDisplayId());
            return 1;
        }
        CornerActionType create2 = CornerActionType.create(this.mGestureAction, this.mContext, motionEvent);
        if (create2 != null) {
            create2.performCornerAction(motionEvent.getDisplayId());
        }
        return 0;
    }

    public String[] getCornerActions(int i) {
        return this.mCornerActions[i].split(",");
    }

    public String convertKeyToTitle(String str) {
        return this.mContext.getString(CornerActionType.getTitleResId(str));
    }

    public void createDurationProgress(int i) {
        if (this.mDurationProgress == null) {
            this.mDurationProgress = new CornerActionCircleCue(getDisplayContext(i), 0);
        }
    }

    public void updateDurationViewXY(float f, float f2) {
        CornerActionCircleCue cornerActionCircleCue = this.mDurationProgress;
        if (cornerActionCircleCue != null) {
            cornerActionCircleCue.updateView(f, f2);
        }
    }

    public void startDuration(long j) {
        CornerActionCircleCue cornerActionCircleCue = this.mDurationProgress;
        if (cornerActionCircleCue == null || this.mIsAnimating) {
            return;
        }
        cornerActionCircleCue.setDurationTime(j);
        this.mDurationProgress.startAnimation();
        this.mIsAnimating = true;
    }

    public void clearDuration() {
        CornerActionCircleCue cornerActionCircleCue = this.mDurationProgress;
        if (cornerActionCircleCue != null) {
            cornerActionCircleCue.clearAnimation();
            this.mIsAnimating = false;
        }
    }

    public void setDurationViewOnOff(boolean z) {
        CornerActionCircleCue cornerActionCircleCue = this.mDurationProgress;
        if (cornerActionCircleCue != null) {
            cornerActionCircleCue.setViewOnOff(z);
        }
    }

    public Context getDisplayContext(int i) {
        Display display;
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
        if (displayManager != null && (display = displayManager.getDisplay(i)) != null) {
            return new ContextThemeWrapper(this.mContext.createDisplayContext(display), this.mContext.getTheme());
        }
        return this.mContext;
    }

    public final void initGestureActionTip() {
        this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mParams = layoutParams;
        layoutParams.height = 0;
        layoutParams.width = 0;
        layoutParams.type = 2006;
        layoutParams.flags = 32;
        layoutParams.samsungFlags |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
    }

    public final void showGestureActionTip(String str, int i, final int i2) {
        View view = new View(getDisplayContext(i));
        this.mTipAnchorView = view;
        WindowManager.LayoutParams layoutParams = this.mParams;
        layoutParams.gravity = POPUP_GRAVITY[i2];
        this.mWindowManager.addView(view, layoutParams);
        this.mTipPopup = new SemTipPopup(this.mTipAnchorView);
        Context context = this.mContext;
        String string = context.getString(R.string.accessibility_system_action_quick_settings_label, context.getString(CornerActionType.getTitleResId(str)));
        this.mTipPopup.setMessage(string);
        this.mTipPopup.setExpanded(true);
        this.mTipPopup.setTargetPosition(i2 % 2 == 0 ? 0 : this.mScreenWidth, i2 < 2 ? 0 : this.mScreenHeight);
        final boolean[] zArr = {false};
        Runnable runnable = new Runnable() { // from class: com.android.server.accessibility.autoaction.CornerActionController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CornerActionController.this.lambda$showGestureActionTip$0(zArr);
            }
        };
        this.mTipPopup.setOnDismissListener(new SemTipPopup.OnDismissListener() { // from class: com.android.server.accessibility.autoaction.CornerActionController$$ExternalSyntheticLambda1
            public final void onDismiss() {
                CornerActionController.this.lambda$showGestureActionTip$1(zArr);
            }
        });
        View contentView = this.mTipPopup.semGetBalloonPopupWindow().getContentView();
        contentView.setFocusable(true);
        contentView.setContentDescription(string);
        contentView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.server.accessibility.autoaction.CornerActionController.2
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, CornerActionController.this.mContext.getString(R.string.config_supervisedUserCreationPackage)));
            }
        });
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() { // from class: com.android.server.accessibility.autoaction.CornerActionController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CornerActionController.this.lambda$showGestureActionTip$2(i2);
            }
        });
        handler.postDelayed(runnable, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showGestureActionTip$0(boolean[] zArr) {
        if (this.mTipPopup.isShowing()) {
            zArr[0] = true;
            this.mTipPopup.dismiss(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showGestureActionTip$1(boolean[] zArr) {
        View view;
        if (!zArr[0] || (view = this.mTipAnchorView) == null) {
            return;
        }
        this.mWindowManager.removeView(view);
        this.mTipAnchorView = null;
        zArr[0] = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showGestureActionTip$2(int i) {
        this.mTipPopup.show(POPUP_DIRECTION[i]);
    }
}
