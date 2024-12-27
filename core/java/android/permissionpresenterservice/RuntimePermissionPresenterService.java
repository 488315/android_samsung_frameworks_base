package android.permissionpresenterservice;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.permission.IRuntimePermissionPresenter;
import android.content.pm.permission.RuntimePermissionPresentationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallback;

import com.android.internal.util.Preconditions;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

import java.util.List;

@SystemApi
@Deprecated
public abstract class RuntimePermissionPresenterService extends Service {
    private static final String KEY_RESULT =
            "android.content.pm.permission.RuntimePermissionPresenter.key.result";
    public static final String SERVICE_INTERFACE =
            "android.permissionpresenterservice.RuntimePermissionPresenterService";
    private Handler mHandler;

    public abstract List<RuntimePermissionPresentationInfo> onGetAppPermissions(String str);

    @Override // android.app.Service, android.content.ContextWrapper
    public final void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mHandler = new Handler(base.getMainLooper());
    }

    /* renamed from: android.permissionpresenterservice.RuntimePermissionPresenterService$1, reason: invalid class name */
    class AnonymousClass1 extends IRuntimePermissionPresenter.Stub {
        AnonymousClass1() {}

        @Override // android.content.pm.permission.IRuntimePermissionPresenter
        public void getAppPermissions(String packageName, RemoteCallback callback) {
            Preconditions.checkNotNull(packageName, "packageName");
            Preconditions.checkNotNull(callback, "callback");
            RuntimePermissionPresenterService.this.mHandler.sendMessage(
                    PooledLambda.obtainMessage(
                            new TriConsumer() { // from class:
                                                // android.permissionpresenterservice.RuntimePermissionPresenterService$1$$ExternalSyntheticLambda0
                                @Override // com.android.internal.util.function.TriConsumer
                                public final void accept(Object obj, Object obj2, Object obj3) {
                                    ((RuntimePermissionPresenterService) obj)
                                            .getAppPermissions(
                                                    (String) obj2, (RemoteCallback) obj3);
                                }
                            },
                            RuntimePermissionPresenterService.this,
                            packageName,
                            callback));
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new AnonymousClass1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAppPermissions(String packageName, RemoteCallback callback) {
        List<RuntimePermissionPresentationInfo> permissions = onGetAppPermissions(packageName);
        if (permissions != null && !permissions.isEmpty()) {
            Bundle result = new Bundle();
            result.putParcelableList(KEY_RESULT, permissions);
            callback.sendResult(result);
            return;
        }
        callback.sendResult(null);
    }
}
