package android.telephony.mbms.vendor;

import android.annotation.SystemApi;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.DownloadProgressListener;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.DownloadStatusListener;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.FileServiceInfo;
import android.telephony.mbms.IDownloadProgressListener;
import android.telephony.mbms.IDownloadStatusListener;
import android.telephony.mbms.IMbmsDownloadSessionCallback;
import android.telephony.mbms.MbmsDownloadSessionCallback;
import android.telephony.mbms.vendor.IMbmsDownloadService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SystemApi
/* loaded from: classes4.dex */
public class MbmsDownloadServiceBase extends IMbmsDownloadService.Stub {
    private final Map<IBinder, DownloadStatusListener> mDownloadStatusListenerBinderMap = new HashMap();
    private final Map<IBinder, DownloadProgressListener> mDownloadProgressListenerBinderMap = new HashMap();
    private final Map<IBinder, IBinder.DeathRecipient> mDownloadCallbackDeathRecipients = new HashMap();

    public int addProgressListener(DownloadRequest downloadRequest, DownloadProgressListener downloadProgressListener) throws RemoteException {
        return 0;
    }

    public int addStatusListener(DownloadRequest downloadRequest, DownloadStatusListener downloadStatusListener) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int cancelDownload(DownloadRequest downloadRequest) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public void dispose(int i) throws RemoteException {
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int download(DownloadRequest downloadRequest) throws RemoteException {
        return 0;
    }

    public int initialize(int i, MbmsDownloadSessionCallback mbmsDownloadSessionCallback) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public List<DownloadRequest> listPendingDownloads(int i) throws RemoteException {
        return null;
    }

    public void onAppCallbackDied(int i, int i2) {
    }

    public int removeProgressListener(DownloadRequest downloadRequest, DownloadProgressListener downloadProgressListener) throws RemoteException {
        return 0;
    }

    public int removeStatusListener(DownloadRequest downloadRequest, DownloadStatusListener downloadStatusListener) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int requestUpdateFileServices(int i, List<String> list) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int setTempFileRootDirectory(int i, String str) throws RemoteException {
        return 0;
    }

    private static abstract class VendorDownloadStatusListener extends DownloadStatusListener {
        private final IDownloadStatusListener mListener;

        protected abstract void onRemoteException(RemoteException remoteException);

        public VendorDownloadStatusListener(IDownloadStatusListener iDownloadStatusListener) {
            this.mListener = iDownloadStatusListener;
        }

        @Override // android.telephony.mbms.DownloadStatusListener
        public void onStatusUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int i) {
            try {
                this.mListener.onStatusUpdated(downloadRequest, fileInfo, i);
            } catch (RemoteException e) {
                onRemoteException(e);
            }
        }
    }

    private static abstract class VendorDownloadProgressListener extends DownloadProgressListener {
        private final IDownloadProgressListener mListener;

        protected abstract void onRemoteException(RemoteException remoteException);

        public VendorDownloadProgressListener(IDownloadProgressListener iDownloadProgressListener) {
            this.mListener = iDownloadProgressListener;
        }

        @Override // android.telephony.mbms.DownloadProgressListener
        public void onProgressUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int i, int i2, int i3, int i4) {
            try {
                this.mListener.onProgressUpdated(downloadRequest, fileInfo, i, i2, i3, i4);
            } catch (RemoteException e) {
                onRemoteException(e);
            }
        }
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int initialize(final int i, final IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws RemoteException {
        if (iMbmsDownloadSessionCallback == null) {
            throw new NullPointerException("Callback must not be null");
        }
        final int callingUid = Binder.getCallingUid();
        int iInitialize = initialize(i, new MbmsDownloadSessionCallback() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.1
            @Override // android.telephony.mbms.MbmsDownloadSessionCallback
            public void onError(int i2, String str) {
                try {
                    if (i2 == -1) {
                        throw new IllegalArgumentException("Middleware cannot send an unknown error.");
                    }
                    iMbmsDownloadSessionCallback.onError(i2, str);
                } catch (RemoteException unused) {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.MbmsDownloadSessionCallback
            public void onFileServicesUpdated(List<FileServiceInfo> list) {
                try {
                    iMbmsDownloadSessionCallback.onFileServicesUpdated(list);
                } catch (RemoteException unused) {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.MbmsDownloadSessionCallback
            public void onMiddlewareReady() {
                try {
                    iMbmsDownloadSessionCallback.onMiddlewareReady();
                } catch (RemoteException unused) {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }
        });
        if (iInitialize == 0) {
            iMbmsDownloadSessionCallback.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }, 0);
        }
        return iInitialize;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int addServiceAnnouncement(int i, byte[] bArr) {
        throw new UnsupportedOperationException("addServiceAnnouncement not supported by this middleware.");
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int addStatusListener(final DownloadRequest downloadRequest, final IDownloadStatusListener iDownloadStatusListener) throws RemoteException {
        final int callingUid = Binder.getCallingUid();
        if (downloadRequest == null) {
            throw new NullPointerException("Download request must not be null");
        }
        if (iDownloadStatusListener == null) {
            throw new NullPointerException("Callback must not be null");
        }
        VendorDownloadStatusListener vendorDownloadStatusListener = new VendorDownloadStatusListener(iDownloadStatusListener) { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.3
            @Override // android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadStatusListener
            protected void onRemoteException(RemoteException remoteException) {
                MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, downloadRequest.getSubscriptionId());
            }
        };
        int iAddStatusListener = addStatusListener(downloadRequest, vendorDownloadStatusListener);
        if (iAddStatusListener == 0) {
            IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.4
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, downloadRequest.getSubscriptionId());
                    MbmsDownloadServiceBase.this.mDownloadStatusListenerBinderMap.remove(iDownloadStatusListener.asBinder());
                    MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(iDownloadStatusListener.asBinder());
                }
            };
            this.mDownloadCallbackDeathRecipients.put(iDownloadStatusListener.asBinder(), deathRecipient);
            iDownloadStatusListener.asBinder().linkToDeath(deathRecipient, 0);
            this.mDownloadStatusListenerBinderMap.put(iDownloadStatusListener.asBinder(), vendorDownloadStatusListener);
        }
        return iAddStatusListener;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener iDownloadStatusListener) throws RemoteException {
        if (downloadRequest == null) {
            throw new NullPointerException("Download request must not be null");
        }
        if (iDownloadStatusListener == null) {
            throw new NullPointerException("Callback must not be null");
        }
        IBinder.DeathRecipient deathRecipientRemove = this.mDownloadCallbackDeathRecipients.remove(iDownloadStatusListener.asBinder());
        if (deathRecipientRemove == null) {
            throw new IllegalArgumentException("Unknown listener");
        }
        iDownloadStatusListener.asBinder().unlinkToDeath(deathRecipientRemove, 0);
        DownloadStatusListener downloadStatusListenerRemove = this.mDownloadStatusListenerBinderMap.remove(iDownloadStatusListener.asBinder());
        if (downloadStatusListenerRemove == null) {
            throw new IllegalArgumentException("Unknown listener");
        }
        return removeStatusListener(downloadRequest, downloadStatusListenerRemove);
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int addProgressListener(final DownloadRequest downloadRequest, final IDownloadProgressListener iDownloadProgressListener) throws RemoteException {
        final int callingUid = Binder.getCallingUid();
        if (downloadRequest == null) {
            throw new NullPointerException("Download request must not be null");
        }
        if (iDownloadProgressListener == null) {
            throw new NullPointerException("Callback must not be null");
        }
        VendorDownloadProgressListener vendorDownloadProgressListener = new VendorDownloadProgressListener(iDownloadProgressListener) { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.5
            @Override // android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadProgressListener
            protected void onRemoteException(RemoteException remoteException) {
                MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, downloadRequest.getSubscriptionId());
            }
        };
        int iAddProgressListener = addProgressListener(downloadRequest, vendorDownloadProgressListener);
        if (iAddProgressListener == 0) {
            IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.6
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, downloadRequest.getSubscriptionId());
                    MbmsDownloadServiceBase.this.mDownloadProgressListenerBinderMap.remove(iDownloadProgressListener.asBinder());
                    MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(iDownloadProgressListener.asBinder());
                }
            };
            this.mDownloadCallbackDeathRecipients.put(iDownloadProgressListener.asBinder(), deathRecipient);
            iDownloadProgressListener.asBinder().linkToDeath(deathRecipient, 0);
            this.mDownloadProgressListenerBinderMap.put(iDownloadProgressListener.asBinder(), vendorDownloadProgressListener);
        }
        return iAddProgressListener;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener iDownloadProgressListener) throws RemoteException {
        if (downloadRequest == null) {
            throw new NullPointerException("Download request must not be null");
        }
        if (iDownloadProgressListener == null) {
            throw new NullPointerException("Callback must not be null");
        }
        IBinder.DeathRecipient deathRecipientRemove = this.mDownloadCallbackDeathRecipients.remove(iDownloadProgressListener.asBinder());
        if (deathRecipientRemove == null) {
            throw new IllegalArgumentException("Unknown listener");
        }
        iDownloadProgressListener.asBinder().unlinkToDeath(deathRecipientRemove, 0);
        DownloadProgressListener downloadProgressListenerRemove = this.mDownloadProgressListenerBinderMap.remove(iDownloadProgressListener.asBinder());
        if (downloadProgressListenerRemove == null) {
            throw new IllegalArgumentException("Unknown listener");
        }
        return removeProgressListener(downloadRequest, downloadProgressListenerRemove);
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService.Stub, android.os.IInterface
    @SystemApi
    public IBinder asBinder() {
        return super.asBinder();
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService.Stub, android.os.Binder
    @SystemApi
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        return super.onTransact(i, parcel, parcel2, i2);
    }
}
