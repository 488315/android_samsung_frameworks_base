package android.telephony.mbms.vendor;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.mbms.GroupCallCallback;
import android.telephony.mbms.IGroupCallCallback;
import android.telephony.mbms.IMbmsGroupCallSessionCallback;
import android.telephony.mbms.MbmsGroupCallSessionCallback;
import android.telephony.mbms.vendor.IMbmsGroupCallService;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public class MbmsGroupCallServiceBase extends Service {
    private final IBinder mInterface = new IMbmsGroupCallService.Stub() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1
        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public int initialize(final IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, final int i) throws RemoteException {
            if (iMbmsGroupCallSessionCallback == null) {
                throw new NullPointerException("Callback must not be null");
            }
            final int callingUid = Binder.getCallingUid();
            int initialize = MbmsGroupCallServiceBase.this.initialize(new MbmsGroupCallSessionCallback() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1.1
                @Override // android.telephony.mbms.MbmsGroupCallSessionCallback
                public void onError(int i2, String str) {
                    try {
                        if (i2 == -1) {
                            throw new IllegalArgumentException("Middleware cannot send an unknown error.");
                        }
                        iMbmsGroupCallSessionCallback.onError(i2, str);
                    } catch (RemoteException unused) {
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.MbmsGroupCallSessionCallback
                public void onAvailableSaisUpdated(List list, List list2) {
                    try {
                        iMbmsGroupCallSessionCallback.onAvailableSaisUpdated(list, list2);
                    } catch (RemoteException unused) {
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.MbmsGroupCallSessionCallback
                public void onServiceInterfaceAvailable(String str, int i2) {
                    try {
                        iMbmsGroupCallSessionCallback.onServiceInterfaceAvailable(str, i2);
                    } catch (RemoteException unused) {
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.MbmsGroupCallSessionCallback
                public void onMiddlewareReady() {
                    try {
                        iMbmsGroupCallSessionCallback.onMiddlewareReady();
                    } catch (RemoteException unused) {
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }
            }, i);
            if (initialize == 0) {
                iMbmsGroupCallSessionCallback.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1.2
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }, 0);
            }
            return initialize;
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void stopGroupCall(int i, long j) {
            MbmsGroupCallServiceBase.this.stopGroupCall(i, j);
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void updateGroupCall(int i, long j, List list, List list2) {
            MbmsGroupCallServiceBase.this.updateGroupCall(i, j, list, list2);
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public int startGroupCall(final int i, long j, List list, List list2, final IGroupCallCallback iGroupCallCallback) throws RemoteException {
            if (iGroupCallCallback == null) {
                throw new NullPointerException("Callback must not be null");
            }
            final int callingUid = Binder.getCallingUid();
            int startGroupCall = MbmsGroupCallServiceBase.this.startGroupCall(i, j, list, list2, new GroupCallCallback() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1.3
                @Override // android.telephony.mbms.GroupCallCallback
                public void onError(int i2, String str) {
                    try {
                        if (i2 == -1) {
                            throw new IllegalArgumentException("Middleware cannot send an unknown error.");
                        }
                        iGroupCallCallback.onError(i2, str);
                    } catch (RemoteException unused) {
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.GroupCallCallback
                public void onGroupCallStateChanged(int i2, int i3) {
                    try {
                        iGroupCallCallback.onGroupCallStateChanged(i2, i3);
                    } catch (RemoteException unused) {
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.GroupCallCallback
                public void onBroadcastSignalStrengthUpdated(int i2) {
                    try {
                        iGroupCallCallback.onBroadcastSignalStrengthUpdated(i2);
                    } catch (RemoteException unused) {
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }
            });
            if (startGroupCall == 0) {
                iGroupCallCallback.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1.4
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }, 0);
            }
            return startGroupCall;
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void dispose(int i) throws RemoteException {
            MbmsGroupCallServiceBase.this.dispose(i);
        }
    };

    public void onAppCallbackDied(int i, int i2) {
    }

    public int initialize(MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback, int i) throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int startGroupCall(int i, long j, List<Integer> list, List<Integer> list2, GroupCallCallback groupCallCallback) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void stopGroupCall(int i, long j) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void updateGroupCall(int i, long j, List<Integer> list, List<Integer> list2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void dispose(int i) throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mInterface;
    }
}
