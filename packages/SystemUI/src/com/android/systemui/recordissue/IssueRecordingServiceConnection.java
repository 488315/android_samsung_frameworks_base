package com.android.systemui.recordissue;

import android.content.Intent;
import com.android.systemui.settings.UserContextProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IssueRecordingServiceConnection extends UserAwareConnection {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
