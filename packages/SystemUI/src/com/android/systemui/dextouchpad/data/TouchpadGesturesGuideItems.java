package com.android.systemui.dextouchpad.data;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.data.GuideItems;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TouchpadGesturesGuideItems extends GuideItems {
    public TouchpadGesturesGuideItems(Context context) {
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_tap, R.drawable.btkeyboard_gestures_popup_touchpad_tab, R.string.dex_touchpad_dialog_tap_desc));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_tap2, R.drawable.btkeyboard_gestures_popup_touchpad_2fingers, R.string.dex_touchpad_dialog_tap2_desc));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_swipe, R.drawable.btkeyboard_gestures_popup_touchpad_swipe_2fingers, R.string.dex_touchpad_dialog_swipe_desc));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_pinch, R.drawable.btkeyboard_gestures_popup_touchpad_pinch_2fingers, R.string.dex_touchpad_dialog_pinch_desc));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_touchhold, R.drawable.btkeyboard_gestures_popup_touchpad_touch_hold, R.string.dex_touchpad_dialog_touchhold_desc));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_touchholdmove, R.drawable.btkeyboard_gestures_popup_touchpad_hold_move, R.string.dex_touchpad_dialog_touchholdmove_desc));
        int i = Settings.Global.getInt(context.getContentResolver(), "three_finger_gesture", 2);
        int i2 = Settings.Global.getInt(context.getContentResolver(), "four_finger_gesture", 1);
        ArrayList arrayList = this.mItemList;
        int i3 = R.string.dex_touchpad_dialog_custom_gestures_none;
        arrayList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_3_finger_tap, R.drawable.btkeyboard_gestures_popup_touchpad_3fingers, i != 0 ? i != 1 ? i != 5 ? i != 6 ? R.string.dex_touchpad_dialog_custom_gestures_back : R.string.dex_touchpad_dialog_custom_gestures_open_quick_settings : R.string.dex_touchpad_dialog_custom_gestures_view_notification : R.string.dex_touchpad_dialog_custom_gestures_apps : R.string.dex_touchpad_dialog_custom_gestures_none));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_4_finger_tap, R.drawable.btkeyboard_gestures_popup_touchpad_4fingers, i2 != 0 ? i2 != 1 ? i2 != 5 ? i2 != 6 ? R.string.dex_touchpad_dialog_custom_gestures_back : R.string.dex_touchpad_dialog_custom_gestures_open_quick_settings : R.string.dex_touchpad_dialog_custom_gestures_view_notification : R.string.dex_touchpad_dialog_custom_gestures_apps : i3));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_swipe_up_down, R.raw.swipe_up_down_3fingers, R.string.dex_touchpad_dialog_swipe_up_down_desc, true));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_swipe_left_right, R.raw.swipe_left_right_3fingers, R.string.dex_touchpad_dialog_swipe_left_right_desc, true));
    }
}
