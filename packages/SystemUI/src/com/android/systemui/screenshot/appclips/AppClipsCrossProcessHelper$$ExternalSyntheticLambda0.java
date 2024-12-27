package com.android.systemui.screenshot.appclips;

import android.os.IBinder;
import android.os.IInterface;
import com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
