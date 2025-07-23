package com.android.systemui.statusbar.policy;

import android.graphics.drawable.Drawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface UserInfoController extends CallbackController {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnUserInfoChangedListener {
        void onUserInfoChanged(String str, Drawable drawable, String str2);
    }
}
