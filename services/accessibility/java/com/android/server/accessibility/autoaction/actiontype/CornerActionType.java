package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.Context;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public abstract class CornerActionType {
  public abstract void performCornerAction(int i);

  public void setMotionEventForDragAction(MotionEvent motionEvent) {}

  public static CornerActionType create(String str, Context context, int i) {
    str.hashCode();
    switch (str) {
      case "open_close_notifications":
        return OpenCloseNotifications.createAction(context);
      case "accessibility_button":
      case "back":
      case "home":
      case "recents":
        return NavigationBarAction.createAction(context, str, i);
      case "talk_to_bixby":
        return TalkToBixby.createAction(context, i);
      case "ringtone_volume_up":
      case "sound_vibrate_mute":
      case "media_volume_down":
      case "media_volume_up":
      case "ringtone_volume_down":
        return SoundAction.createAction(context, str);
      case "screen_rotation":
        return ScreenRotation.createAction(context, i);
      case "screen_off":
        return ScreenOff.createAction(context);
      case "reduce_brightness":
      case "increase_brightness":
        return BrightnessAction.createAction(context, str, i);
      case "screen_shot":
        return ScreenShot.createAction(context);
      case "none":
        return null;
      case "power_off_menu":
        return PowerOffMenu.createAction(context);
      case "send_sos_messages":
        return SendSOSMessages.createAction(context, i);
      case "open_close_quick_panel":
        return OpenCloseQuickPanel.createAction(context);
      default:
        throw new IllegalArgumentException("Wrong Corner Action Type");
    }
  }

  public static CornerActionType create(String str, Context context, MotionEvent motionEvent) {
    str.hashCode();
    switch (str) {
      case "click_and_hold":
        return ClickAndHold.createAction(context, motionEvent);
      case "zoom_in":
      case "zoom_out":
        return Zoom.createAction(context, motionEvent, str);
      case "swipe_up":
      case "swipe_down":
      case "swipe_left":
      case "swipe_right":
        return Swipe.createAction(context, motionEvent, str);
      case "drag":
      case "drag_and_drop":
        return DragAction.createAction(context, motionEvent, str);
      case "double_click":
        return DoubleClick.createAction(context, motionEvent);
      default:
        throw new IllegalArgumentException("Wrong Corner Action Type");
    }
  }

  public static int getTitleResId(String str) {
    str.hashCode();
    switch (str) {
      case "open_close_notifications":
        return OpenCloseNotifications.getStringResId();
      case "accessibility_button":
      case "back":
      case "home":
      case "recents":
        return NavigationBarAction.getStringResId(str);
      case "talk_to_bixby":
        return TalkToBixby.getStringResId();
      case "click_and_hold":
        return ClickAndHold.getStringResId();
      case "ringtone_volume_up":
      case "sound_vibrate_mute":
      case "media_volume_down":
      case "media_volume_up":
      case "ringtone_volume_down":
        return SoundAction.getStringResId(str);
      case "screen_rotation":
        return ScreenRotation.getStringResId();
      case "screen_off":
        return ScreenOff.getStringResId();
      case "reduce_brightness":
      case "increase_brightness":
        return BrightnessAction.getStringResId(str);
      case "sound_mute":
        return R.string.aerr_application;
      case "zoom_in":
      case "zoom_out":
        return Zoom.getStringResId(str);
      case "swipe_up":
      case "swipe_down":
      case "swipe_left":
      case "swipe_right":
        return Swipe.getStringResId(str);
      case "screen_shot":
        return ScreenShot.getStringResId();
      case "drag":
      case "drag_and_drop":
        return DragAction.getStringResId(str);
      case "power_off_menu":
        return PowerOffMenu.getStringResId();
      case "send_sos_messages":
        return SendSOSMessages.getStringResId();
      case "double_click":
        return DoubleClick.getStringResId();
      case "resume_auto_click":
        return R.string.adb_active_notification_title;
      case "pause_auto_click":
        return R.string.activity_resolver_work_profiles_support;
      case "open_close_quick_panel":
        return OpenCloseQuickPanel.getStringResId();
      default:
        throw new IllegalArgumentException("Wrong Corner Action Type");
    }
  }
}
