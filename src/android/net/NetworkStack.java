package android.net;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.IBinder;
import android.os.ServiceManager;
import com.android.net.module.util.PermissionUtils;

@SystemApi
/* loaded from: classes3.dex */
public class NetworkStack {

    @SystemApi
    public static final String PERMISSION_MAINLINE_NETWORK_STACK = "android.permission.MAINLINE_NETWORK_STACK";
    private static volatile IBinder sMockService;

    @SystemApi
    public static IBinder getService() {
        IBinder iBinder = sMockService;
        return iBinder != null ? iBinder : ServiceManager.getService(Context.NETWORK_STACK_SERVICE);
    }

    public static void setServiceForTest(IBinder iBinder) {
        sMockService = iBinder;
    }

    private NetworkStack() {
    }

    @Deprecated
    public static void checkNetworkStackPermission(Context context) {
        PermissionUtils.enforceNetworkStackPermission(context);
    }

    @Deprecated
    public static void checkNetworkStackPermissionOr(Context context, String... strArr) {
        PermissionUtils.enforceNetworkStackPermissionOr(context, strArr);
    }
}
