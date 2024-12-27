package com.android.server.wm;

import android.media.projection.MediaProjectionInfo;

import java.util.function.Predicate;

public final /* synthetic */ class ScreenRecordingCallbackController$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ MediaProjectionInfo f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((ActivityRecord) obj).mLaunchCookie == this.f$0.getLaunchCookie().binder;
    }
}
