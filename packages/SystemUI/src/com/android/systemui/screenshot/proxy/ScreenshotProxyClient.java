package com.android.systemui.screenshot.proxy;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.screenshot.proxy.IScreenshotProxy;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenshotProxyClient implements ScreenshotProxy {
    public final ServiceConnector proxyConnector;

    public ScreenshotProxyClient(Context context) {
        this.proxyConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) ScreenshotProxyService.class), 1073741857, 0, new Function() { // from class: com.android.systemui.screenshot.proxy.ScreenshotProxyClient$proxyConnector$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                IBinder iBinder = (IBinder) obj;
                int i = IScreenshotProxy.Stub.$r8$clinit;
                if (iBinder == null) {
                    return null;
                }
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.proxy.IScreenshotProxy");
                return (queryLocalInterface == null || !(queryLocalInterface instanceof IScreenshotProxy)) ? new IScreenshotProxy.Stub.Proxy(iBinder) : (IScreenshotProxy) queryLocalInterface;
            }
        });
    }
}
