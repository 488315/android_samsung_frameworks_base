package com.android.systemui.screenshot.proxy;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.screenshot.IScreenshotProxy;
import com.android.systemui.screenshot.ScreenshotProxyService;
import java.util.function.Function;

public final class SystemUiProxyClient implements SystemUiProxy {
    public final ServiceConnector proxyConnector;

    public SystemUiProxyClient(Context context) {
        this.proxyConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) ScreenshotProxyService.class), 1073741857, context.getUserId(), new Function() { // from class: com.android.systemui.screenshot.proxy.SystemUiProxyClient$proxyConnector$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                IBinder iBinder = (IBinder) obj;
                int i = IScreenshotProxy.Stub.$r8$clinit;
                if (iBinder == null) {
                    return null;
                }
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.IScreenshotProxy");
                return (queryLocalInterface == null || !(queryLocalInterface instanceof IScreenshotProxy)) ? new IScreenshotProxy.Stub.Proxy(iBinder) : (IScreenshotProxy) queryLocalInterface;
            }
        });
    }
}
