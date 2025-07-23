package android.app;

import android.annotation.SystemApi;
import android.app.IInstantAppResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.InstantAppRequestInfo;
import android.content.pm.InstantAppResolveInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.SomeArgs;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SystemApi
/* loaded from: classes.dex */
public abstract class InstantAppResolverService extends Service {
    private static final boolean DEBUG_INSTANT = Build.IS_DEBUGGABLE;
    public static final String EXTRA_RESOLVE_INFO = "android.app.extra.RESOLVE_INFO";
    public static final String EXTRA_SEQUENCE = "android.app.extra.SEQUENCE";
    private static final String TAG = "PackageManager";
    Handler mHandler;

    @Deprecated
    public void onGetInstantAppResolveInfo(int[] iArr, String str, InstantAppResolutionCallback instantAppResolutionCallback) {
        throw new IllegalStateException("Must define onGetInstantAppResolveInfo");
    }

    @Deprecated
    public void onGetInstantAppIntentFilter(int[] iArr, String str, InstantAppResolutionCallback instantAppResolutionCallback) {
        throw new IllegalStateException("Must define onGetInstantAppIntentFilter");
    }

    @Deprecated
    public void onGetInstantAppResolveInfo(Intent intent, int[] iArr, String str, InstantAppResolutionCallback instantAppResolutionCallback) {
        if (intent.isWebIntent()) {
            onGetInstantAppResolveInfo(iArr, str, instantAppResolutionCallback);
        } else {
            instantAppResolutionCallback.onInstantAppResolveInfo(Collections.EMPTY_LIST);
        }
    }

    @Deprecated
    public void onGetInstantAppIntentFilter(Intent intent, int[] iArr, String str, InstantAppResolutionCallback instantAppResolutionCallback) {
        Log.e(TAG, "New onGetInstantAppIntentFilter is not overridden");
        if (intent.isWebIntent()) {
            onGetInstantAppIntentFilter(iArr, str, instantAppResolutionCallback);
        } else {
            instantAppResolutionCallback.onInstantAppResolveInfo(Collections.EMPTY_LIST);
        }
    }

    @Deprecated
    public void onGetInstantAppResolveInfo(Intent intent, int[] iArr, UserHandle userHandle, String str, InstantAppResolutionCallback instantAppResolutionCallback) {
        onGetInstantAppResolveInfo(intent, iArr, str, instantAppResolutionCallback);
    }

    @Deprecated
    public void onGetInstantAppIntentFilter(Intent intent, int[] iArr, UserHandle userHandle, String str, InstantAppResolutionCallback instantAppResolutionCallback) {
        onGetInstantAppIntentFilter(intent, iArr, str, instantAppResolutionCallback);
    }

    public void onGetInstantAppResolveInfo(InstantAppRequestInfo instantAppRequestInfo, InstantAppResolutionCallback instantAppResolutionCallback) {
        onGetInstantAppResolveInfo(instantAppRequestInfo.getIntent(), instantAppRequestInfo.getHostDigestPrefix(), instantAppRequestInfo.getUserHandle(), instantAppRequestInfo.getToken(), instantAppResolutionCallback);
    }

    public void onGetInstantAppIntentFilter(InstantAppRequestInfo instantAppRequestInfo, InstantAppResolutionCallback instantAppResolutionCallback) {
        onGetInstantAppIntentFilter(instantAppRequestInfo.getIntent(), instantAppRequestInfo.getHostDigestPrefix(), instantAppRequestInfo.getUserHandle(), instantAppRequestInfo.getToken(), instantAppResolutionCallback);
    }

    Looper getLooper() {
        return getBaseContext().getMainLooper();
    }

    @Override // android.app.Service, android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new ServiceHandler(getLooper());
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new IInstantAppResolver.Stub() { // from class: android.app.InstantAppResolverService.1
            @Override // android.app.IInstantAppResolver
            public void getInstantAppResolveInfoList(InstantAppRequestInfo instantAppRequestInfo, int i, IRemoteCallback iRemoteCallback) {
                if (InstantAppResolverService.DEBUG_INSTANT) {
                    Slog.v(InstantAppResolverService.TAG, NavigationBarInflaterView.SIZE_MOD_START + instantAppRequestInfo.getToken() + "] Phase1 called; posting");
                }
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = instantAppRequestInfo;
                obtain.arg2 = iRemoteCallback;
                InstantAppResolverService.this.mHandler.obtainMessage(1, i, 0, obtain).sendToTarget();
            }

            @Override // android.app.IInstantAppResolver
            public void getInstantAppIntentFilterList(InstantAppRequestInfo instantAppRequestInfo, IRemoteCallback iRemoteCallback) {
                if (InstantAppResolverService.DEBUG_INSTANT) {
                    Slog.v(InstantAppResolverService.TAG, NavigationBarInflaterView.SIZE_MOD_START + instantAppRequestInfo.getToken() + "] Phase2 called; posting");
                }
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = instantAppRequestInfo;
                obtain.arg2 = iRemoteCallback;
                InstantAppResolverService.this.mHandler.obtainMessage(2, obtain).sendToTarget();
            }
        };
    }

    public static final class InstantAppResolutionCallback {
        private final IRemoteCallback mCallback;
        private final int mSequence;

        public InstantAppResolutionCallback(int i, IRemoteCallback iRemoteCallback) {
            this.mCallback = iRemoteCallback;
            this.mSequence = i;
        }

        public void onInstantAppResolveInfo(List<InstantAppResolveInfo> list) {
            Bundle bundle = new Bundle();
            bundle.putParcelableList(InstantAppResolverService.EXTRA_RESOLVE_INFO, list);
            bundle.putInt(InstantAppResolverService.EXTRA_SEQUENCE, this.mSequence);
            try {
                this.mCallback.sendResult(bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    private final class ServiceHandler extends Handler {
        public static final int MSG_GET_INSTANT_APP_INTENT_FILTER = 2;
        public static final int MSG_GET_INSTANT_APP_RESOLVE_INFO = 1;

        public ServiceHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                SomeArgs someArgs = (SomeArgs) message.obj;
                InstantAppRequestInfo instantAppRequestInfo = (InstantAppRequestInfo) someArgs.arg1;
                IRemoteCallback iRemoteCallback = (IRemoteCallback) someArgs.arg2;
                someArgs.recycle();
                int i2 = message.arg1;
                if (InstantAppResolverService.DEBUG_INSTANT) {
                    Slog.d(InstantAppResolverService.TAG, NavigationBarInflaterView.SIZE_MOD_START + instantAppRequestInfo.getToken() + "] Phase1 request; prefix: " + Arrays.toString(instantAppRequestInfo.getHostDigestPrefix()) + ", userId: " + instantAppRequestInfo.getUserHandle().getIdentifier());
                }
                InstantAppResolverService.this.onGetInstantAppResolveInfo(instantAppRequestInfo, new InstantAppResolutionCallback(i2, iRemoteCallback));
                return;
            }
            if (i == 2) {
                SomeArgs someArgs2 = (SomeArgs) message.obj;
                InstantAppRequestInfo instantAppRequestInfo2 = (InstantAppRequestInfo) someArgs2.arg1;
                IRemoteCallback iRemoteCallback2 = (IRemoteCallback) someArgs2.arg2;
                someArgs2.recycle();
                if (InstantAppResolverService.DEBUG_INSTANT) {
                    Slog.d(InstantAppResolverService.TAG, NavigationBarInflaterView.SIZE_MOD_START + instantAppRequestInfo2.getToken() + "] Phase2 request; prefix: " + Arrays.toString(instantAppRequestInfo2.getHostDigestPrefix()) + ", userId: " + instantAppRequestInfo2.getUserHandle().getIdentifier());
                }
                InstantAppResolverService.this.onGetInstantAppIntentFilter(instantAppRequestInfo2, new InstantAppResolutionCallback(-1, iRemoteCallback2));
                return;
            }
            throw new IllegalArgumentException("Unknown message: " + i);
        }
    }
}
