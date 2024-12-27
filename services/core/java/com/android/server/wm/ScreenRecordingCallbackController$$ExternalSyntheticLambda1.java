package com.android.server.wm;

import android.media.projection.MediaProjectionInfo;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScreenRecordingCallbackController$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ MediaProjectionInfo f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((ActivityRecord) obj).mLaunchCookie == this.f$0.getLaunchCookie().binder;
    }
}
