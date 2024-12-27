package com.android.systemui.statusbar.policy;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface UserInfoController extends CallbackController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface OnUserInfoChangedListener {
        void onUserInfoChanged(String str, Drawable drawable, String str2);
    }
}
