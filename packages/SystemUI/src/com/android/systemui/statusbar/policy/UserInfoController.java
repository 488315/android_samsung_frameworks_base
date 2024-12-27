package com.android.systemui.statusbar.policy;

import android.graphics.drawable.Drawable;

public interface UserInfoController extends CallbackController {

    public interface OnUserInfoChangedListener {
        void onUserInfoChanged(String str, Drawable drawable, String str2);
    }
}
