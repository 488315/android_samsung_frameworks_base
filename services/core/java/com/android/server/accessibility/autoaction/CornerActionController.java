package com.android.server.accessibility.autoaction;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;

import com.android.server.accessibility.autoaction.actiontype.BrightnessAction;
import com.android.server.accessibility.autoaction.actiontype.CornerActionType;
import com.android.server.accessibility.autoaction.actiontype.NavigationBarAction;
import com.android.server.accessibility.autoaction.actiontype.OpenCloseNotifications;
import com.android.server.accessibility.autoaction.actiontype.OpenCloseQuickPanel;
import com.android.server.accessibility.autoaction.actiontype.PowerOffMenu;
import com.android.server.accessibility.autoaction.actiontype.ScreenOff;
import com.android.server.accessibility.autoaction.actiontype.ScreenRotation;
import com.android.server.accessibility.autoaction.actiontype.ScreenShot;
import com.android.server.accessibility.autoaction.actiontype.SendSOSMessages;
import com.android.server.accessibility.autoaction.actiontype.SoundAction;
import com.android.server.accessibility.autoaction.actiontype.TalkToBixby;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.widget.SemTipPopup;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CornerActionController {
    public static final int[] POPUP_DIRECTION;
    public static final int[] POPUP_GRAVITY;
    public static final int[] TTS_CORNER;
    public static final HashMap mGestureActionFlag;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final AnonymousClass1 mCornerActionTypeObserver;
    public String[] mCornerActions;
    public CornerActionType mDragAction;
    public String mGestureAction;
    public final WindowManager.LayoutParams mParams;
    public int mScreenHeight;
    public int mScreenWidth;
    public View mTipAnchorView;
    public SemTipPopup mTipPopup;
    public final int mUserId;
    public final WindowManager mWindowManager;
    public CornerActionCircleCue mDurationProgress = null;
    public boolean mIsAnimating = false;

    static {
        HashMap hashMap = new HashMap();
        mGestureActionFlag = hashMap;
        hashMap.put("double_click", 1);
        hashMap.put("zoom_in", 2);
        hashMap.put("zoom_out", 4);
        hashMap.put("swipe_left", 8);
        hashMap.put("swipe_right", 16);
        hashMap.put("swipe_up", 32);
        hashMap.put("swipe_down", 64);
        hashMap.put("click_and_hold", 128);
        hashMap.put("drag", 256);
        hashMap.put("drag_and_drop", 512);
        POPUP_GRAVITY = new int[] {51, 53, 83, 85};
        POPUP_DIRECTION = new int[] {3, 2, 1, 0};
        TTS_CORNER =
                new int[] {
                    R.string.accessibility_uncheck_legacy_item_warning,
                    R.string.action_bar_home_description,
                    R.string.accessibility_system_action_quick_settings_label,
                    R.string.accessibility_system_action_recents_label
                };
    }

    public CornerActionController(Context context, int i) {
        ContentObserver contentObserver =
                new ContentObserver(
                        new Handler()) { // from class:
                                         // com.android.server.accessibility.autoaction.CornerActionController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        CornerActionController cornerActionController = CornerActionController.this;
                        String stringForUser =
                                Settings.Secure.getStringForUser(
                                        cornerActionController.mContentResolver,
                                        "accessibility_corner_actions",
                                        cornerActionController.mUserId);
                        if (stringForUser != null) {
                            cornerActionController.mCornerActions = stringForUser.split(":");
                        }
                    }
                };
        this.mContext = context;
        ContentResolver contentResolver = context.getContentResolver();
        this.mContentResolver = contentResolver;
        this.mUserId = i;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mParams = layoutParams;
        layoutParams.height = 0;
        layoutParams.width = 0;
        layoutParams.type = 2006;
        layoutParams.flags = 32;
        layoutParams.samsungFlags |= 131072;
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_corner_actions"),
                false,
                contentObserver,
                i);
        contentObserver.onChange(true);
    }

    public final void clearDuration() {
        CornerActionCircleCue cornerActionCircleCue = this.mDurationProgress;
        if (cornerActionCircleCue != null) {
            cornerActionCircleCue.getClass();
            cornerActionCircleCue.runOnUiThread(
                    new CornerActionCircleCue$$ExternalSyntheticLambda0(cornerActionCircleCue, 1));
            this.mIsAnimating = false;
        }
    }

    public final int getCorner(int i, float f, float f2) {
        Display display;
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
        if (displayManager != null && (display = displayManager.getDisplay(i)) != null) {
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            this.mScreenWidth = displayInfo.logicalWidth;
            this.mScreenHeight = displayInfo.logicalHeight;
        }
        if (f == FullScreenMagnificationGestureHandler.MAX_SCALE && f2 < 40.0f) {
            return 0;
        }
        if (f < 40.0f && f2 == FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return 0;
        }
        int i2 = this.mScreenWidth;
        if ((f == i2 - 1 && f2 < 40.0f)
                || (i2 - f < 40.0f && f2 == FullScreenMagnificationGestureHandler.MAX_SCALE)) {
            return 1;
        }
        if (f < 40.0f && f2 == this.mScreenHeight - 1) {
            return 2;
        }
        if (f == FullScreenMagnificationGestureHandler.MAX_SCALE
                && this.mScreenHeight - f2 < 40.0f) {
            return 2;
        }
        if (i2 - f >= 40.0f || f2 != this.mScreenHeight - 1) {
            return (f != ((float) (i2 - 1)) || ((float) this.mScreenHeight) - f2 >= 40.0f) ? -1 : 3;
        }
        return 3;
    }

    public final Context getDisplayContext(int i) {
        Display display;
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
        return (displayManager == null || (display = displayManager.getDisplay(i)) == null)
                ? this.mContext
                : new ContextThemeWrapper(
                        this.mContext.createDisplayContext(display), this.mContext.getTheme());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    public final int performCornerAction(int i, final int i2, String str) {
        int i3;
        char c;
        OpenCloseNotifications openCloseNotifications;
        str.getClass();
        i3 = this.mUserId;
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
                int intForUser =
                        Settings.Secure.getIntForUser(
                                this.mContentResolver,
                                "accessibility_corner_action_tip_shown",
                                0,
                                i3);
                int intValue = ((Integer) mGestureActionFlag.get(str)).intValue();
                if ((intForUser & intValue) == 0) {
                    View view = new View(getDisplayContext(i));
                    this.mTipAnchorView = view;
                    WindowManager.LayoutParams layoutParams = this.mParams;
                    layoutParams.gravity = POPUP_GRAVITY[i2];
                    this.mWindowManager.addView(view, layoutParams);
                    this.mTipPopup = new SemTipPopup(this.mTipAnchorView);
                    Context context = this.mContext;
                    String string =
                            context.getString(
                                    R.string.accessibility_system_action_screenshot_label,
                                    context.getString(CornerActionType.getTitleResId(str)));
                    this.mTipPopup.setMessage(string);
                    this.mTipPopup.setExpanded(true);
                    this.mTipPopup.setTargetPosition(
                            i2 % 2 == 0 ? 0 : this.mScreenWidth, i2 < 2 ? 0 : this.mScreenHeight);
                    final boolean[] zArr = {false};
                    Runnable runnable =
                            new Runnable() { // from class:
                                             // com.android.server.accessibility.autoaction.CornerActionController$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CornerActionController cornerActionController =
                                            CornerActionController.this;
                                    boolean[] zArr2 = zArr;
                                    if (cornerActionController.mTipPopup.isShowing()) {
                                        zArr2[0] = true;
                                        cornerActionController.mTipPopup.dismiss(false);
                                    }
                                }
                            };
                    this.mTipPopup.setOnDismissListener(
                            new SemTipPopup
                                    .OnDismissListener() { // from class:
                                                           // com.android.server.accessibility.autoaction.CornerActionController$$ExternalSyntheticLambda1
                                public final void onDismiss() {
                                    View view2;
                                    CornerActionController cornerActionController =
                                            CornerActionController.this;
                                    boolean[] zArr2 = zArr;
                                    cornerActionController.getClass();
                                    if (!zArr2[0]
                                            || (view2 = cornerActionController.mTipAnchorView)
                                                    == null) {
                                        return;
                                    }
                                    cornerActionController.mWindowManager.removeView(view2);
                                    cornerActionController.mTipAnchorView = null;
                                    zArr2[0] = false;
                                }
                            });
                    View contentView = this.mTipPopup.semGetBalloonPopupWindow().getContentView();
                    contentView.setFocusable(true);
                    contentView.setContentDescription(string);
                    contentView.setAccessibilityDelegate(
                            new View
                                    .AccessibilityDelegate() { // from class:
                                                               // com.android.server.accessibility.autoaction.CornerActionController.2
                                @Override // android.view.View.AccessibilityDelegate
                                public final void onInitializeAccessibilityNodeInfo(
                                        View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                                    super.onInitializeAccessibilityNodeInfo(
                                            view2, accessibilityNodeInfo);
                                    accessibilityNodeInfo.addAction(
                                            new AccessibilityNodeInfo.AccessibilityAction(
                                                    16,
                                                    CornerActionController.this.mContext.getString(
                                                            R.string.config_useragentprofile_url)));
                                }
                            });
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(
                            new Runnable() { // from class:
                                             // com.android.server.accessibility.autoaction.CornerActionController$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    CornerActionController.this.mTipPopup.show(
                                            CornerActionController.POPUP_DIRECTION[i2]);
                                }
                            });
                    handler.postDelayed(runnable, 5000L);
                    Settings.Secure.putIntForUser(
                            this.mContentResolver,
                            "accessibility_corner_action_tip_shown",
                            intForUser | intValue,
                            i3);
                }
                return 1;
            case "pause_resume_auto_click":
                return 3;
            default:
                Context context2 = this.mContext;
                switch (str.hashCode()) {
                    case -2126701204:
                        if (str.equals("open_close_notifications")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1961431229:
                        if (str.equals("accessibility_button")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1747947369:
                        if (str.equals("talk_to_bixby")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1428385405:
                        if (str.equals("ringtone_volume_up")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1366541839:
                        if (str.equals("screen_rotation")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -417942503:
                        if (str.equals("sound_vibrate_mute")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -417036516:
                        if (str.equals("screen_off")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -343519030:
                        if (str.equals("reduce_brightness")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -43108627:
                        if (str.equals("screen_shot")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 3015911:
                        if (str.equals("back")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case 3208415:
                        if (str.equals("home")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case 3387192:
                        if (str.equals("none")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case 339994222:
                        if (str.equals("increase_brightness")) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case 452226764:
                        if (str.equals("media_volume_down")) {
                            c = '\r';
                            break;
                        }
                        c = 65535;
                        break;
                    case 765078569:
                        if (str.equals("power_off_menu")) {
                            c = 14;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1060365899:
                        if (str.equals("send_sos_messages")) {
                            c = 15;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1082295672:
                        if (str.equals("recents")) {
                            c = 16;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1158011717:
                        if (str.equals("media_volume_up")) {
                            c = 17;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1710656906:
                        if (str.equals("ringtone_volume_down")) {
                            c = 18;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1729070774:
                        if (str.equals("open_close_quick_panel")) {
                            c = 19;
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
                        OpenCloseNotifications openCloseNotifications2 =
                                new OpenCloseNotifications();
                        openCloseNotifications2.mContext = context2;
                        openCloseNotifications = openCloseNotifications2;
                        break;
                    case 1:
                    case '\t':
                    case '\n':
                    case 16:
                        NavigationBarAction navigationBarAction = new NavigationBarAction();
                        navigationBarAction.mContext = context2;
                        navigationBarAction.mType = str;
                        navigationBarAction.mUserId = i3;
                        openCloseNotifications = navigationBarAction;
                        break;
                    case 2:
                        TalkToBixby talkToBixby = new TalkToBixby();
                        talkToBixby.mContext = context2;
                        talkToBixby.mUserId = i3;
                        openCloseNotifications = talkToBixby;
                        break;
                    case 3:
                    case 5:
                    case '\r':
                    case 17:
                    case 18:
                        SoundAction soundAction = new SoundAction();
                        soundAction.mAudioManager =
                                (AudioManager) context2.getSystemService("audio");
                        soundAction.mType = str;
                        openCloseNotifications = soundAction;
                        break;
                    case 4:
                        ScreenRotation screenRotation = new ScreenRotation();
                        screenRotation.mContext = context2;
                        screenRotation.mUserId = i3;
                        openCloseNotifications = screenRotation;
                        break;
                    case 6:
                        ScreenOff screenOff = new ScreenOff();
                        screenOff.mContext = context2;
                        openCloseNotifications = screenOff;
                        break;
                    case 7:
                    case '\f':
                        BrightnessAction brightnessAction = new BrightnessAction();
                        brightnessAction.mContext = context2;
                        brightnessAction.mType = str;
                        brightnessAction.mUserId = i3;
                        openCloseNotifications = brightnessAction;
                        break;
                    case '\b':
                        ScreenShot screenShot = new ScreenShot();
                        screenShot.mContext = context2;
                        openCloseNotifications = screenShot;
                        break;
                    case 11:
                        openCloseNotifications = null;
                        break;
                    case 14:
                        PowerOffMenu powerOffMenu = new PowerOffMenu();
                        powerOffMenu.mContext = context2;
                        openCloseNotifications = powerOffMenu;
                        break;
                    case 15:
                        SendSOSMessages sendSOSMessages = new SendSOSMessages();
                        sendSOSMessages.mContext = context2;
                        sendSOSMessages.mUserId = i3;
                        openCloseNotifications = sendSOSMessages;
                        break;
                    case 19:
                        OpenCloseQuickPanel openCloseQuickPanel = new OpenCloseQuickPanel();
                        openCloseQuickPanel.mContext = context2;
                        openCloseNotifications = openCloseQuickPanel;
                        break;
                    default:
                        throw new IllegalArgumentException("Wrong Corner Action Type");
                }
                if (openCloseNotifications != null) {
                    openCloseNotifications.performCornerAction(i);
                }
                return 0;
        }
    }
}
