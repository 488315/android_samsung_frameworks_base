package com.android.systemui.screenshot.appclips;

import android.os.IBinder;
import android.os.IInterface;
import com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppClipsCrossProcessHelper$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        IBinder iBinder = (IBinder) obj;
        int i = IAppClipsScreenshotHelperService.Stub.$r8$clinit;
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService");
        return (queryLocalInterface == null || !(queryLocalInterface instanceof IAppClipsScreenshotHelperService)) ? new IAppClipsScreenshotHelperService.Stub.Proxy(iBinder) : (IAppClipsScreenshotHelperService) queryLocalInterface;
    }
}
