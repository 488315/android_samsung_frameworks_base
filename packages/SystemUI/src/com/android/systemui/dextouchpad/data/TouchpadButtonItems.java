package com.android.systemui.dextouchpad.data;

import com.android.systemui.R;

/* loaded from: classes2.dex */
public enum TouchpadButtonItems {
    ROTATION(1002, "TouchpadRotationButtonWindow", R.id.touchpad_rotation_image_button, R.string.dex_touchpad_toast_double_tap),
    GUIDE(1002, "TouchpadGuideButtonWindow", R.id.touchpad_guide_image_button, R.string.dex_touchpad_toast_double_tap_guide),
    CLOSE(1002, "TouchpadCloseButtonWindow", R.id.touchpad_close_image_button, R.string.dex_touchpad_toast_double_tap_close);

    private final int mButtonId;
    private final String mTitle;
    private final int mToastResId;
    private final int mType;

    TouchpadButtonItems(int i, String str, int i2, int i3) {
        this.mType = i;
        this.mTitle = str;
        this.mButtonId = i2;
        this.mToastResId = i3;
    }

    public final int getButtonId() {
        return this.mButtonId;
    }

    public final String getTitle() {
        return this.mTitle;
    }

    public final int getToastResId() {
        return this.mToastResId;
    }

    public final int getType() {
        return this.mType;
    }
}
