package com.android.systemui.dextouchpad.data;

import com.android.systemui.R;
import com.android.systemui.dextouchpad.data.GuideItems;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SPenGesturesGuideItems extends GuideItems {
    public SPenGesturesGuideItems() {
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_spen_gesture_tap_title, R.drawable.spen_setting_detached_01, R.string.dex_touchpad_dialog_spen_gesture_tap_content));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_spen_gesture_tap_drag_title, R.drawable.spen_setting_detached_02, R.string.dex_touchpad_dialog_spen_gesture_tap_drag_content));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_spen_gesture_touch_and_hold_title, R.drawable.spen_setting_detached_03, R.string.dex_touchpad_dialog_spen_gesture_touch_hold_content));
        this.mItemList.add(new GuideItems.ItemInfo(R.string.dex_touchpad_dialog_spen_gesture_touch_hold_drag_title, R.drawable.spen_setting_detached_04, R.string.dex_touchpad_dialog_spen_gesture_touch_hold_drag_content));
    }
}
