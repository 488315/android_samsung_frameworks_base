package com.android.systemui.screenshot.appclips;

import android.os.IBinder;
import com.android.internal.statusbar.IAppClipsService;
import java.util.function.Function;

public final /* synthetic */ class AppClipsTrampolineActivity$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IAppClipsService.Stub.asInterface((IBinder) obj);
    }
}
