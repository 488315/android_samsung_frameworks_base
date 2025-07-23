package android.media.tv.tunerresourcemanager;

import android.media.tv.tunerresourcemanager.IResourcesReclaimListener;
import android.media.tv.tunerresourcemanager.TunerResourceManager;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class TunerResourceManager {
    public static final int INVALID_OWNER_ID = -1;
    public static final long INVALID_RESOURCE_HANDLE = -1;
    public static final int TUNER_RESOURCE_TYPE_CAS_SESSION = 4;
    public static final int TUNER_RESOURCE_TYPE_DEMUX = 1;
    public static final int TUNER_RESOURCE_TYPE_DESCRAMBLER = 2;
    public static final int TUNER_RESOURCE_TYPE_FRONTEND = 0;
    public static final int TUNER_RESOURCE_TYPE_FRONTEND_CICAM = 5;
    public static final int TUNER_RESOURCE_TYPE_LNB = 3;
    public static final int TUNER_RESOURCE_TYPE_MAX = 6;
    private final ITunerResourceManager mService;
    private final int mUserId;
    private static final String TAG = "TunerResourceManager";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public static abstract class ResourcesReclaimListener {
        public abstract void onReclaimResources();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TunerResourceType {
    }

    public TunerResourceManager(ITunerResourceManager iTunerResourceManager, int i) {
        this.mService = iTunerResourceManager;
        this.mUserId = i;
    }

    /* renamed from: android.media.tv.tunerresourcemanager.TunerResourceManager$1, reason: invalid class name */
    class AnonymousClass1 extends IResourcesReclaimListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ ResourcesReclaimListener val$listener;

        AnonymousClass1(TunerResourceManager tunerResourceManager, Executor executor, ResourcesReclaimListener resourcesReclaimListener) {
            this.val$executor = executor;
            this.val$listener = resourcesReclaimListener;
        }

        @Override // android.media.tv.tunerresourcemanager.IResourcesReclaimListener
        public void onReclaimResources() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final ResourcesReclaimListener resourcesReclaimListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.media.tv.tunerresourcemanager.TunerResourceManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TunerResourceManager.ResourcesReclaimListener.this.onReclaimResources();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void registerClientProfile(ResourceClientProfile resourceClientProfile, Executor executor, ResourcesReclaimListener resourcesReclaimListener, int[] iArr) {
        try {
            this.mService.registerClientProfile(resourceClientProfile, new AnonymousClass1(this, executor, resourcesReclaimListener), iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterClientProfile(int i) {
        try {
            this.mService.unregisterClientProfile(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateClientPriority(int i, int i2, int i3) {
        try {
            return this.mService.updateClientPriority(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasUnusedFrontend(int i) {
        try {
            return this.mService.hasUnusedFrontend(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isLowestPriority(int i, int i2) {
        try {
            return this.mService.isLowestPriority(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setResourceOwnershipRetention(int i, boolean z) {
        try {
            this.mService.setResourceOwnershipRetention(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void storeResourceMap(int i) {
        try {
            this.mService.storeResourceMap(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearResourceMap(int i) {
        try {
            this.mService.clearResourceMap(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void restoreResourceMap(int i) {
        try {
            this.mService.restoreResourceMap(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setFrontendInfoList(TunerFrontendInfo[] tunerFrontendInfoArr) {
        try {
            this.mService.setFrontendInfoList(tunerFrontendInfoArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDemuxInfoList(TunerDemuxInfo[] tunerDemuxInfoArr) {
        try {
            this.mService.setDemuxInfoList(tunerDemuxInfoArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateCasInfo(int i, int i2) {
        try {
            this.mService.updateCasInfo(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setLnbInfoList(long[] jArr) {
        try {
            this.mService.setLnbInfoList(jArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean acquireLock(int i) {
        try {
            return this.mService.acquireLock(i, Thread.currentThread().getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean releaseLock(int i) {
        try {
            return this.mService.releaseLock(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestFrontend(TunerFrontendRequest tunerFrontendRequest, long[] jArr) {
        try {
            return this.mService.requestFrontend(tunerFrontendRequest, jArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setMaxNumberOfFrontends(int i, int i2) {
        try {
            return this.mService.setMaxNumberOfFrontends(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMaxNumberOfFrontends(int i) {
        try {
            return this.mService.getMaxNumberOfFrontends(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void shareFrontend(int i, int i2) {
        try {
            this.mService.shareFrontend(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean transferOwner(int i, int i2, int i3) {
        try {
            return this.mService.transferOwner(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestDemux(TunerDemuxRequest tunerDemuxRequest, long[] jArr) {
        try {
            return this.mService.requestDemux(tunerDemuxRequest, jArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestDescrambler(TunerDescramblerRequest tunerDescramblerRequest, long[] jArr) {
        try {
            return this.mService.requestDescrambler(tunerDescramblerRequest, jArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestCasSession(CasSessionRequest casSessionRequest, long[] jArr) {
        try {
            return this.mService.requestCasSession(casSessionRequest, jArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestCiCam(TunerCiCamRequest tunerCiCamRequest, long[] jArr) {
        try {
            return this.mService.requestCiCam(tunerCiCamRequest, jArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestLnb(TunerLnbRequest tunerLnbRequest, long[] jArr) {
        try {
            return this.mService.requestLnb(tunerLnbRequest, jArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseFrontend(long j, int i) {
        try {
            this.mService.releaseFrontend(j, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseDemux(long j, int i) {
        try {
            this.mService.releaseDemux(j, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseDescrambler(long j, int i) {
        try {
            this.mService.releaseDescrambler(j, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseCasSession(long j, int i) {
        try {
            this.mService.releaseCasSession(j, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseCiCam(long j, int i) {
        try {
            this.mService.releaseCiCam(j, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseLnb(long j, int i) {
        try {
            this.mService.releaseLnb(j, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isHigherPriority(ResourceClientProfile resourceClientProfile, ResourceClientProfile resourceClientProfile2) {
        try {
            return this.mService.isHigherPriority(resourceClientProfile, resourceClientProfile2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getClientPriority(int i, int i2) {
        try {
            return this.mService.getClientPriority(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getConfigPriority(int i, boolean z) {
        try {
            return this.mService.getConfigPriority(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
