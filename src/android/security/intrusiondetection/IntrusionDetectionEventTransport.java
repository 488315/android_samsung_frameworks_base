package android.security.intrusiondetection;

import android.annotation.SystemApi;
import android.os.IBinder;
import android.security.intrusiondetection.IIntrusionDetectionEventTransport;
import com.android.internal.infra.AndroidFuture;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public class IntrusionDetectionEventTransport {
    IIntrusionDetectionEventTransport mBinderImpl = new TransportImpl();

    public boolean addData(List<IntrusionDetectionEvent> list) {
        return false;
    }

    public boolean initialize() {
        return false;
    }

    public boolean release() {
        return false;
    }

    public IBinder getBinder() {
        return this.mBinderImpl.asBinder();
    }

    class TransportImpl extends IIntrusionDetectionEventTransport.Stub {
        TransportImpl() {
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionEventTransport
        public void initialize(AndroidFuture<Boolean> androidFuture) {
            try {
                androidFuture.complete(Boolean.valueOf(IntrusionDetectionEventTransport.this.initialize()));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionEventTransport
        public void addData(List<IntrusionDetectionEvent> list, AndroidFuture<Boolean> androidFuture) {
            try {
                androidFuture.complete(Boolean.valueOf(IntrusionDetectionEventTransport.this.addData(list)));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionEventTransport
        public void release(AndroidFuture<Boolean> androidFuture) {
            try {
                androidFuture.complete(Boolean.valueOf(IntrusionDetectionEventTransport.this.release()));
            } catch (RuntimeException unused) {
                androidFuture.cancel(true);
            }
        }
    }
}
