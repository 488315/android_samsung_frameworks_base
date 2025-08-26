package com.android.systemui.screenshot.proxy;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.screenshot.proxy.IScreenshotProxy;
import java.util.function.Function;

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
                IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.proxy.IScreenshotProxy");
                return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IScreenshotProxy)) ? new IScreenshotProxy.Stub.Proxy(iBinder) : (IScreenshotProxy) iInterfaceQueryLocalInterface;
            }
        });
    }
}
