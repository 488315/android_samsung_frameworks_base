package com.android.systemui.recordissue;

import android.content.Intent;
import com.android.systemui.settings.UserContextProvider;

/* loaded from: classes2.dex */
public final class IssueRecordingServiceConnection extends UserAwareConnection {

    public final class Provider {
        public final UserContextProvider userContextProvider;

        public Provider(UserContextProvider userContextProvider) {
            this.userContextProvider = userContextProvider;
        }
    }

    public IssueRecordingServiceConnection(UserContextProvider userContextProvider) {
        super(userContextProvider, new Intent().setClassName("com.android.systemui", IssueRecordingService.class.getName()));
    }
}
