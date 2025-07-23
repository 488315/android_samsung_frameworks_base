package android.service.tracing;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.ParcelFileDescriptor;
import android.tracing.TraceReportParams;
import android.util.Log;
import java.io.IOException;
import java.util.UUID;

@SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
/* loaded from: classes3.dex */
public class TraceReportService extends Service {
    public static final int MSG_REPORT_TRACE = 1;
    private static final String TAG = "TraceReportService";
    private Messenger mMessenger = null;

    public void onReportTrace(TraceParams traceParams) {
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public static final class TraceParams {
        private final ParcelFileDescriptor mFd;
        private final UUID mUuid;

        private TraceParams(TraceReportParams traceReportParams) {
            this.mFd = traceReportParams.fd;
            this.mUuid = new UUID(traceReportParams.uuidMsb, traceReportParams.uuidLsb);
        }

        public ParcelFileDescriptor getFd() {
            return this.mFd;
        }

        public UUID getUuid() {
            return this.mUuid;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onMessage(Message message) {
        if (message.what != 1) {
            return false;
        }
        if (!(message.obj instanceof TraceReportParams)) {
            Log.e(TAG, "Received invalid type for report trace message.");
            return false;
        }
        TraceParams traceParams = new TraceParams((TraceReportParams) message.obj);
        try {
            onReportTrace(traceParams);
            return true;
        } finally {
            try {
                traceParams.getFd().close();
            } catch (IOException unused) {
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (this.mMessenger == null) {
            this.mMessenger = new Messenger(new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: android.service.tracing.TraceReportService$$ExternalSyntheticLambda0
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    boolean onMessage;
                    onMessage = TraceReportService.this.onMessage(message);
                    return onMessage;
                }
            }));
        }
        return this.mMessenger.getBinder();
    }
}
