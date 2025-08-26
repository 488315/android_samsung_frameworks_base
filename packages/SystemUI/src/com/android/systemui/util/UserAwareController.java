package com.android.systemui.util;

import android.os.UserHandle;

/* loaded from: classes3.dex */
public interface UserAwareController {
    int getCurrentUserId();

    default void changeUser(UserHandle userHandle) {
    }
}
