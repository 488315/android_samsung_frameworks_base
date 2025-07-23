package com.android.systemui.screenshot.appclips;

import android.os.IBinder;
import com.android.internal.statusbar.IAppClipsService;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppClipsTrampolineActivity$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IAppClipsService.Stub.asInterface((IBinder) obj);
    }
}
