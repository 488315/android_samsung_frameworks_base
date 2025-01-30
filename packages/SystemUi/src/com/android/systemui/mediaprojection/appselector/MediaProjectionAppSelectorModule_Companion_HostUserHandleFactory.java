package com.android.systemui.mediaprojection.appselector;

import android.os.Bundle;
import android.os.UserHandle;
import com.android.systemui.media.MediaProjectionAppSelectorActivity;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaProjectionAppSelectorModule_Companion_HostUserHandleFactory implements Provider {
    public final Provider activityProvider;

    public MediaProjectionAppSelectorModule_Companion_HostUserHandleFactory(Provider provider) {
        this.activityProvider = provider;
    }

    public static UserHandle hostUserHandle(MediaProjectionAppSelectorActivity mediaProjectionAppSelectorActivity) {
        MediaProjectionAppSelectorModule.Companion.getClass();
        Bundle extras = mediaProjectionAppSelectorActivity.getIntent().getExtras();
        if (extras == null) {
            throw new IllegalStateException("MediaProjectionAppSelectorActivity should be launched with extras".toString());
        }
        UserHandle userHandle = (UserHandle) extras.getParcelable("launched_from_user_handle");
        if (userHandle != null) {
            return userHandle;
        }
        throw new IllegalStateException("MediaProjectionAppSelectorActivity should be provided with launched_from_user_handle extra".toString());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return hostUserHandle((MediaProjectionAppSelectorActivity) this.activityProvider.get());
    }
}
