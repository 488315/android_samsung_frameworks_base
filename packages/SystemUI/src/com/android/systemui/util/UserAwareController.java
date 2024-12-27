package com.android.systemui.util;

import android.os.UserHandle;

public interface UserAwareController {
    int getCurrentUserId();

    default void changeUser(UserHandle userHandle) {
    }
}
