package android.telephony.mbms.vendor;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.IMbmsStreamingSessionCallback;
import android.telephony.mbms.IStreamingServiceCallback;
import android.telephony.mbms.MbmsStreamingSessionCallback;
import android.telephony.mbms.StreamingServiceCallback;
import android.telephony.mbms.StreamingServiceInfo;
import android.telephony.mbms.vendor.IMbmsStreamingService;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public class MbmsStreamingServiceBase extends IMbmsStreamingService.Stub {
    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public void dispose(int i) throws RemoteException {
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public Uri getPlaybackUri(int i, String str) throws RemoteException {
        return null;
    }

    public int initialize(MbmsStreamingSessionCallback mbmsStreamingSessionCallback, int i) throws RemoteException {
        return 0;
    }

    public void onAppCallbackDied(int i, int i2) {
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public int requestUpdateStreamingServices(int i, List<String> list) throws RemoteException {
        return 0;
    }

    public int startStreaming(int i, String str, StreamingServiceCallback streamingServiceCallback) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public void stopStreaming(int i, String str) throws RemoteException {
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public final int initialize(final IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, final int i) throws RemoteException {
        if (iMbmsStreamingSessionCallback == null) {
            throw new NullPointerException("Callback must not be null");
        }
        final int callingUid = Binder.getCallingUid();
        int initialize = initialize(new MbmsStreamingSessionCallback() { // from class: android.telephony.mbms.vendor.MbmsStreamingServiceBase.1
            @Override // android.telephony.mbms.MbmsStreamingSessionCallback
            public void onError(int i2, String str) {
                try {
                    if (i2 == -1) {
                        throw new IllegalArgumentException("Middleware cannot send an unknown error.");
                    }
                    iMbmsStreamingSessionCallback.onError(i2, str);
                } catch (RemoteException unused) {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.MbmsStreamingSessionCallback
            public void onStreamingServicesUpdated(List<StreamingServiceInfo> list) {
                try {
                    iMbmsStreamingSessionCallback.onStreamingServicesUpdated(list);
                } catch (RemoteException unused) {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.MbmsStreamingSessionCallback
            public void onMiddlewareReady() {
                try {
                    iMbmsStreamingSessionCallback.onMiddlewareReady();
                } catch (RemoteException unused) {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }
        }, i);
        if (initialize == 0) {
            iMbmsStreamingSessionCallback.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsStreamingServiceBase.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }, 0);
        }
        return initialize;
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public int startStreaming(final int i, String str, final IStreamingServiceCallback iStreamingServiceCallback) throws RemoteException {
        if (iStreamingServiceCallback == null) {
            throw new NullPointerException("Callback must not be null");
        }
        final int callingUid = Binder.getCallingUid();
        int startStreaming = startStreaming(i, str, new StreamingServiceCallback() { // from class: android.telephony.mbms.vendor.MbmsStreamingServiceBase.3
            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onError(int i2, String str2) {
                try {
                    if (i2 == -1) {
                        throw new IllegalArgumentException("Middleware cannot send an unknown error.");
                    }
                    iStreamingServiceCallback.onError(i2, str2);
                } catch (RemoteException unused) {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onStreamStateUpdated(int i2, int i3) {
                try {
                    iStreamingServiceCallback.onStreamStateUpdated(i2, i3);
                } catch (RemoteException unused) {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onMediaDescriptionUpdated() {
                try {
                    iStreamingServiceCallback.onMediaDescriptionUpdated();
                } catch (RemoteException unused) {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onBroadcastSignalStrengthUpdated(int i2) {
                try {
                    iStreamingServiceCallback.onBroadcastSignalStrengthUpdated(i2);
                } catch (RemoteException unused) {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onStreamMethodUpdated(int i2) {
                try {
                    iStreamingServiceCallback.onStreamMethodUpdated(i2);
                } catch (RemoteException unused) {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }
        });
        if (startStreaming == 0) {
            iStreamingServiceCallback.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsStreamingServiceBase.4
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }, 0);
        }
        return startStreaming;
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService.Stub, android.os.IInterface
    @SystemApi
    public IBinder asBinder() {
        return super.asBinder();
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService.Stub, android.os.Binder
    @SystemApi
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        return super.onTransact(i, parcel, parcel2, i2);
    }
}
