package com.samsung.android.smartthingsmediasdk.mediasdk.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import com.samsung.android.oneconnect.mediaoutput.IMediaOutputService;
import com.samsung.android.smartthingsmediasdk.mediasdk.base.debug.DLog;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes4.dex */
public final class MediaSdkSupportServiceClient {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _serviceBindStateFlow;
    public final StateFlowImpl _serviceConnectedStateFlow;
    public final Context context;
    public IMediaOutputService mediaSdkSupportService;
    public final MediaSdkSupportServiceClient$serviceConnection$1 serviceConnection;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient$serviceConnection$1] */
    public MediaSdkSupportServiceClient(Context context) {
        this.context = context;
        Boolean bool = Boolean.FALSE;
        this._serviceBindStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._serviceConnectedStateFlow = StateFlowKt.MutableStateFlow(bool);
        this.serviceConnection = new ServiceConnection() { // from class: com.samsung.android.smartthingsmediasdk.mediasdk.service.MediaSdkSupportServiceClient$serviceConnection$1
            @Override // android.content.ServiceConnection
            public final void onNullBinding(ComponentName componentName) {
                DLog.Companion.getClass();
                DLog.Companion.i("MediaSdkSupportServiceClient", "onNullBinding", "ComponentName: " + componentName);
                MediaSdkSupportServiceClient mediaSdkSupportServiceClient = this.this$0;
                int i = MediaSdkSupportServiceClient.$r8$clinit;
                mediaSdkSupportServiceClient.emitMediaSdkServiceDisconnectedState();
            }

            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IMediaOutputService proxy;
                MediaSdkSupportServiceClient mediaSdkSupportServiceClient = this.this$0;
                int i = IMediaOutputService.Stub.$r8$clinit;
                if (iBinder == null) {
                    proxy = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMediaOutputService)) ? new IMediaOutputService.Stub.Proxy(iBinder) : (IMediaOutputService) iInterfaceQueryLocalInterface;
                }
                mediaSdkSupportServiceClient.mediaSdkSupportService = proxy;
                this.this$0._serviceConnectedStateFlow.updateState(null, Boolean.TRUE);
                DLog.Companion.getClass();
                DLog.Companion.i("MediaSdkSupportServiceClient", "onServiceConnected", "ServiceConnectedEmitted: true | Version: 1.0.1.17 | Build type: release | isDebug: false");
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                DLog.Companion.getClass();
                DLog.Companion.i("MediaSdkSupportServiceClient", "onServiceDisconnected", "ComponentName: " + componentName);
                MediaSdkSupportServiceClient mediaSdkSupportServiceClient = this.this$0;
                int i = MediaSdkSupportServiceClient.$r8$clinit;
                mediaSdkSupportServiceClient.emitMediaSdkServiceDisconnectedState();
            }
        };
    }

    public final void emitMediaSdkServiceDisconnectedState() {
        this.mediaSdkSupportService = null;
        this._serviceConnectedStateFlow.updateState(null, Boolean.FALSE);
        DLog.Companion.getClass();
        DLog.Companion.i("MediaSdkSupportServiceClient", "emitMediaSdkServiceDisconnectedState", "ServiceDisconnectedStateEmitted: true | Version: 1.0.1.17 | Build type: release | isDebug: false");
    }
}
