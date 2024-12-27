package com.android.server.tv.tunerresourcemanager;

import android.app.ActivityManager;
import android.content.Context;
import android.media.IResourceManagerService;
import android.media.tv.TvInputManager;
import android.media.tv.tunerresourcemanager.CasSessionRequest;
import android.media.tv.tunerresourcemanager.IResourcesReclaimListener;
import android.media.tv.tunerresourcemanager.ITunerResourceManager;
import android.media.tv.tunerresourcemanager.ResourceClientProfile;
import android.media.tv.tunerresourcemanager.TunerCiCamRequest;
import android.media.tv.tunerresourcemanager.TunerDemuxInfo;
import android.media.tv.tunerresourcemanager.TunerDemuxRequest;
import android.media.tv.tunerresourcemanager.TunerDescramblerRequest;
import android.media.tv.tunerresourcemanager.TunerFrontendInfo;
import android.media.tv.tunerresourcemanager.TunerFrontendRequest;
import android.media.tv.tunerresourcemanager.TunerLnbRequest;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.asks.ASKSManagerService$$ExternalSyntheticOutline0;
import com.android.server.tv.tunerresourcemanager.CasResource;
import com.android.server.tv.tunerresourcemanager.CiCamResource;
import com.android.server.tv.tunerresourcemanager.ClientProfile;
import com.android.server.tv.tunerresourcemanager.DemuxResource;
import com.android.server.tv.tunerresourcemanager.FrontendResource;
import com.android.server.tv.tunerresourcemanager.LnbResource;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TunerResourceManagerService extends SystemService implements IBinder.DeathRecipient {
    public static final boolean DEBUG = Log.isLoggable("TunerResourceManagerService", 3);
    public ActivityManager mActivityManager;
    public final Map mCasResources;
    public final Map mCiCamResources;
    public final Map mClientProfiles;
    public final Map mDemuxResources;
    public final SparseIntArray mFrontendExistingNums;
    public final SparseIntArray mFrontendExistingNumsBackup;
    public final SparseIntArray mFrontendMaxUsableNums;
    public final SparseIntArray mFrontendMaxUsableNumsBackup;
    public final Map mFrontendResources;
    public final Map mFrontendResourcesBackup;
    public final SparseIntArray mFrontendUsedNums;
    public final SparseIntArray mFrontendUsedNumsBackup;
    public final Map mListeners;
    public final Map mLnbResources;
    public final Object mLock;
    public final ReentrantLock mLockForTRMSLock;
    public IResourceManagerService mMediaResourceManager;
    public int mNextUnusedClientId;
    public final UseCasePriorityHints mPriorityCongfig;
    public int mResourceRequestCount;
    public int mTunerApiLockHolder;
    public long mTunerApiLockHolderThreadId;
    public int mTunerApiLockNestedCount;
    public final Condition mTunerApiLockReleasedCV;
    public TvInputManager mTvInputManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends ITunerResourceManager.Stub {
        public BinderService() {
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00a8 A[Catch: all -> 0x003a, InterruptedException -> 0x003d, TryCatch #0 {InterruptedException -> 0x003d, blocks: (B:8:0x0027, B:12:0x0031, B:20:0x0049, B:38:0x006f, B:43:0x0101, B:45:0x010b, B:57:0x0135, B:58:0x0159, B:60:0x015d, B:61:0x0182, B:63:0x0186, B:64:0x01a1, B:27:0x00a8, B:31:0x00b9), top: B:7:0x0027, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:37:0x006f A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0101 A[Catch: all -> 0x003a, InterruptedException -> 0x003d, TryCatch #0 {InterruptedException -> 0x003d, blocks: (B:8:0x0027, B:12:0x0031, B:20:0x0049, B:38:0x006f, B:43:0x0101, B:45:0x010b, B:57:0x0135, B:58:0x0159, B:60:0x015d, B:61:0x0182, B:63:0x0186, B:64:0x01a1, B:27:0x00a8, B:31:0x00b9), top: B:7:0x0027, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x01aa A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:53:0x01ba A[DONT_GENERATE] */
        /* JADX WARN: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:57:0x0135 A[Catch: all -> 0x003a, InterruptedException -> 0x003d, TryCatch #0 {InterruptedException -> 0x003d, blocks: (B:8:0x0027, B:12:0x0031, B:20:0x0049, B:38:0x006f, B:43:0x0101, B:45:0x010b, B:57:0x0135, B:58:0x0159, B:60:0x015d, B:61:0x0182, B:63:0x0186, B:64:0x01a1, B:27:0x00a8, B:31:0x00b9), top: B:7:0x0027, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:60:0x015d A[Catch: all -> 0x003a, InterruptedException -> 0x003d, TryCatch #0 {InterruptedException -> 0x003d, blocks: (B:8:0x0027, B:12:0x0031, B:20:0x0049, B:38:0x006f, B:43:0x0101, B:45:0x010b, B:57:0x0135, B:58:0x0159, B:60:0x015d, B:61:0x0182, B:63:0x0186, B:64:0x01a1, B:27:0x00a8, B:31:0x00b9), top: B:7:0x0027, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:63:0x0186 A[Catch: all -> 0x003a, InterruptedException -> 0x003d, TryCatch #0 {InterruptedException -> 0x003d, blocks: (B:8:0x0027, B:12:0x0031, B:20:0x0049, B:38:0x006f, B:43:0x0101, B:45:0x010b, B:57:0x0135, B:58:0x0159, B:60:0x015d, B:61:0x0182, B:63:0x0186, B:64:0x01a1, B:27:0x00a8, B:31:0x00b9), top: B:7:0x0027, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:65:0x0057  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean acquireLock(int r25, long r26) {
            /*
                Method dump skipped, instructions count: 518
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.BinderService.acquireLock(int, long):boolean");
        }

        public final void clearResourceMap(int i) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "clearResourceMap");
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService tunerResourceManagerService = TunerResourceManagerService.this;
                if (i != 0) {
                    tunerResourceManagerService.getClass();
                } else {
                    TunerResourceManagerService.replaceFeResourceMap(null, tunerResourceManagerService.mFrontendResources);
                    TunerResourceManagerService.replaceFeCounts(null, tunerResourceManagerService.mFrontendExistingNums);
                    TunerResourceManagerService.replaceFeCounts(null, tunerResourceManagerService.mFrontendUsedNums);
                    TunerResourceManagerService.replaceFeCounts(null, tunerResourceManagerService.mFrontendMaxUsableNums);
                }
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            if (TunerResourceManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
                indentingPrintWriter.println("Permission Denial: can't dump!");
                return;
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService tunerResourceManagerService = TunerResourceManagerService.this;
                TunerResourceManagerService.m996$$Nest$mdumpMap(tunerResourceManagerService, tunerResourceManagerService.mClientProfiles, "ClientProfiles:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService2 = TunerResourceManagerService.this;
                TunerResourceManagerService.m996$$Nest$mdumpMap(tunerResourceManagerService2, tunerResourceManagerService2.mFrontendResources, "FrontendResources:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService3 = TunerResourceManagerService.this;
                TunerResourceManagerService.m997$$Nest$mdumpSIA(tunerResourceManagerService3, tunerResourceManagerService3.mFrontendExistingNums, "FrontendExistingNums:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService4 = TunerResourceManagerService.this;
                TunerResourceManagerService.m997$$Nest$mdumpSIA(tunerResourceManagerService4, tunerResourceManagerService4.mFrontendUsedNums, "FrontendUsedNums:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService5 = TunerResourceManagerService.this;
                TunerResourceManagerService.m997$$Nest$mdumpSIA(tunerResourceManagerService5, tunerResourceManagerService5.mFrontendMaxUsableNums, "FrontendMaxUsableNums:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService6 = TunerResourceManagerService.this;
                TunerResourceManagerService.m996$$Nest$mdumpMap(tunerResourceManagerService6, tunerResourceManagerService6.mFrontendResourcesBackup, "FrontendResourcesBackUp:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService7 = TunerResourceManagerService.this;
                TunerResourceManagerService.m997$$Nest$mdumpSIA(tunerResourceManagerService7, tunerResourceManagerService7.mFrontendExistingNumsBackup, "FrontendExistingNumsBackup:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService8 = TunerResourceManagerService.this;
                TunerResourceManagerService.m997$$Nest$mdumpSIA(tunerResourceManagerService8, tunerResourceManagerService8.mFrontendUsedNumsBackup, "FrontendUsedNumsBackup:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService9 = TunerResourceManagerService.this;
                TunerResourceManagerService.m997$$Nest$mdumpSIA(tunerResourceManagerService9, tunerResourceManagerService9.mFrontendMaxUsableNumsBackup, "FrontendUsedNumsBackup:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService10 = TunerResourceManagerService.this;
                TunerResourceManagerService.m996$$Nest$mdumpMap(tunerResourceManagerService10, tunerResourceManagerService10.mDemuxResources, "DemuxResource:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService11 = TunerResourceManagerService.this;
                TunerResourceManagerService.m996$$Nest$mdumpMap(tunerResourceManagerService11, tunerResourceManagerService11.mLnbResources, "LnbResource:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService12 = TunerResourceManagerService.this;
                TunerResourceManagerService.m996$$Nest$mdumpMap(tunerResourceManagerService12, tunerResourceManagerService12.mCasResources, "CasResource:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService13 = TunerResourceManagerService.this;
                TunerResourceManagerService.m996$$Nest$mdumpMap(tunerResourceManagerService13, tunerResourceManagerService13.mCiCamResources, "CiCamResource:", indentingPrintWriter);
                TunerResourceManagerService tunerResourceManagerService14 = TunerResourceManagerService.this;
                TunerResourceManagerService.m996$$Nest$mdumpMap(tunerResourceManagerService14, tunerResourceManagerService14.mListeners, "Listners:", indentingPrintWriter);
            }
        }

        public final int getClientPriority(int i, int i2) {
            int clientPriority;
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "getClientPriority");
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService tunerResourceManagerService = TunerResourceManagerService.this;
                clientPriority = tunerResourceManagerService.getClientPriority(i, tunerResourceManagerService.checkIsForeground(i2));
            }
            return clientPriority;
        }

        public final int getConfigPriority(int i, boolean z) {
            int clientPriority;
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "getConfigPriority");
            synchronized (TunerResourceManagerService.this.mLock) {
                clientPriority = TunerResourceManagerService.this.getClientPriority(i, z);
            }
            return clientPriority;
        }

        public final int getMaxNumberOfFrontends(int i) {
            int i2;
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "getMaxNumberOfFrontends");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "getMaxNumberOfFrontends");
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService tunerResourceManagerService = TunerResourceManagerService.this;
                i2 = -1;
                int i3 = tunerResourceManagerService.mFrontendExistingNums.get(i, -1);
                if (i3 == -1) {
                    Log.e("TunerResourceManagerService", "existingNum is -1 for " + i);
                } else {
                    int i4 = tunerResourceManagerService.mFrontendMaxUsableNums.get(i, -1);
                    i2 = i4 == -1 ? i3 : i4;
                }
            }
            return i2;
        }

        public final boolean hasUnusedFrontend(int i) {
            boolean z;
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "hasUnusedFrontend");
            synchronized (TunerResourceManagerService.this.mLock) {
                Iterator it = TunerResourceManagerService.this.getFrontendResources().values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    FrontendResource frontendResource = (FrontendResource) it.next();
                    if (frontendResource.mType == i && !frontendResource.mIsInUse) {
                        z = true;
                        break;
                    }
                }
            }
            return z;
        }

        public final boolean isHigherPriority(ResourceClientProfile resourceClientProfile, ResourceClientProfile resourceClientProfile2) {
            boolean isHigherPriorityInternal;
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "isHigherPriority");
            if (resourceClientProfile == null || resourceClientProfile2 == null) {
                throw new RemoteException("Client profiles can't be null.");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                isHigherPriorityInternal = TunerResourceManagerService.this.isHigherPriorityInternal(resourceClientProfile, resourceClientProfile2);
            }
            return isHigherPriorityInternal;
        }

        public final boolean isLowestPriority(int i, int i2) {
            boolean z;
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "isLowestPriority");
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(i)) {
                        throw new RemoteException("isLowestPriority called from unregistered client: " + i);
                    }
                    TunerResourceManagerService tunerResourceManagerService = TunerResourceManagerService.this;
                    ClientProfile clientProfile = tunerResourceManagerService.getClientProfile(i);
                    z = true;
                    if (clientProfile != null) {
                        tunerResourceManagerService.clientPriorityUpdateOnRequest(clientProfile);
                        int priority = clientProfile.getPriority();
                        Iterator it = tunerResourceManagerService.getFrontendResources().values().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            FrontendResource frontendResource = (FrontendResource) it.next();
                            if (frontendResource.mType == i2 && frontendResource.mIsInUse && priority > tunerResourceManagerService.updateAndGetOwnerClientPriority(frontendResource.mOwnerClientId)) {
                                z = false;
                                break;
                            }
                        }
                    }
                } finally {
                }
            }
            return z;
        }

        public final void registerClientProfile(ResourceClientProfile resourceClientProfile, IResourcesReclaimListener iResourcesReclaimListener, int[] iArr) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "registerClientProfile");
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "registerClientProfile");
            if (resourceClientProfile == null) {
                throw new RemoteException("ResourceClientProfile can't be null");
            }
            if (iArr == null) {
                throw new RemoteException("clientId can't be null!");
            }
            if (iResourcesReclaimListener == null) {
                throw new RemoteException("IResourcesReclaimListener can't be null!");
            }
            UseCasePriorityHints useCasePriorityHints = TunerResourceManagerService.this.mPriorityCongfig;
            int i = resourceClientProfile.useCase;
            if (!((HashSet) useCasePriorityHints.mVendorDefinedUseCase).contains(Integer.valueOf(i)) && i != 100 && i != 200 && i != 300 && i != 400 && i != 500) {
                throw new RemoteException("Use undefined client use case:" + resourceClientProfile.useCase);
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService.this.registerClientProfileInternal(resourceClientProfile, iResourcesReclaimListener, iArr);
            }
        }

        public final void releaseCasSession(int i, int i2) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "releaseCasSession");
            if (!TunerResourceManagerService.m1001$$Nest$mvalidateResourceHandle(TunerResourceManagerService.this, 4, i)) {
                throw new RemoteException("casSessionHandle can't be invalid");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new RemoteException("Release cas from unregistered client:" + i2);
                    }
                    CasResource casResource = TunerResourceManagerService.this.getCasResource(TunerResourceManagerService.this.getClientProfile(i2).mUsingCasSystemId);
                    if (casResource == null) {
                        throw new RemoteException("Releasing cas does not exist.");
                    }
                    if (!casResource.getOwnerClientIds().contains(Integer.valueOf(i2))) {
                        throw new RemoteException("Client is not the current owner of the releasing cas.");
                    }
                    TunerResourceManagerService.this.releaseCasSessionInternal(casResource, i2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void releaseCiCam(int i, int i2) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "releaseCiCam");
            if (!TunerResourceManagerService.m1001$$Nest$mvalidateResourceHandle(TunerResourceManagerService.this, 5, i)) {
                throw new RemoteException("ciCamHandle can't be invalid");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new RemoteException("Release ciCam from unregistered client:" + i2);
                    }
                    int i3 = TunerResourceManagerService.this.getClientProfile(i2).mUsingCiCamId;
                    if (i3 != TunerResourceManagerService.this.getResourceIdFromHandle(i)) {
                        throw new RemoteException("The client " + i2 + " is not the owner of the releasing ciCam.");
                    }
                    CiCamResource ciCamResource = TunerResourceManagerService.this.getCiCamResource(i3);
                    if (ciCamResource == null) {
                        throw new RemoteException("Releasing ciCam does not exist.");
                    }
                    if (!ciCamResource.getOwnerClientIds().contains(Integer.valueOf(i2))) {
                        throw new RemoteException("Client is not the current owner of the releasing ciCam.");
                    }
                    TunerResourceManagerService.this.releaseCiCamInternal(ciCamResource, i2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void releaseDemux(int i, int i2) {
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "releaseDemux");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "releaseDemux");
            if (TunerResourceManagerService.DEBUG) {
                FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i, "releaseDemux(demuxHandle=", ")", "TunerResourceManagerService");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (((HashMap) TunerResourceManagerService.this.mDemuxResources).size() == 0) {
                        return;
                    }
                    if (!TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new RemoteException("Release demux for unregistered client:" + i2);
                    }
                    DemuxResource demuxResource = TunerResourceManagerService.this.getDemuxResource(i);
                    if (demuxResource == null) {
                        throw new RemoteException("Releasing demux does not exist.");
                    }
                    if (demuxResource.mOwnerClientId != i2) {
                        throw new RemoteException("Client is not the current owner of the releasing demux.");
                    }
                    TunerResourceManagerService.this.releaseDemuxInternal(demuxResource);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void releaseDescrambler(int i, int i2) {
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "releaseDescrambler");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "releaseDescrambler");
            if (TunerResourceManagerService.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "releaseDescrambler(descramblerHandle=", ")", "TunerResourceManagerService");
            }
        }

        public final void releaseFrontend(int i, int i2) {
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "releaseFrontend");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "releaseFrontend");
            if (!TunerResourceManagerService.m1001$$Nest$mvalidateResourceHandle(TunerResourceManagerService.this, 0, i)) {
                throw new RemoteException("frontendHandle can't be invalid");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new RemoteException("Release frontend from unregistered client:" + i2);
                    }
                    FrontendResource frontendResource = TunerResourceManagerService.this.getFrontendResource(i);
                    if (frontendResource == null) {
                        throw new RemoteException("Releasing frontend does not exist.");
                    }
                    int i3 = frontendResource.mOwnerClientId;
                    ClientProfile clientProfile = TunerResourceManagerService.this.getClientProfile(i3);
                    if (i3 != i2 && clientProfile != null) {
                        if (!((HashSet) clientProfile.mShareFeClientIds).contains(Integer.valueOf(i2))) {
                            throw new RemoteException("Client is not the current owner of the releasing fe.");
                        }
                    }
                    TunerResourceManagerService.this.releaseFrontendInternal(frontendResource, i2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void releaseLnb(int i, int i2) {
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "releaseLnb");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "releaseLnb");
            if (!TunerResourceManagerService.m1001$$Nest$mvalidateResourceHandle(TunerResourceManagerService.this, 3, i)) {
                throw new RemoteException("lnbHandle can't be invalid");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new RemoteException("Release lnb from unregistered client:" + i2);
                    }
                    LnbResource lnbResource = TunerResourceManagerService.this.getLnbResource(i);
                    if (lnbResource == null) {
                        throw new RemoteException("Releasing lnb does not exist.");
                    }
                    if (lnbResource.mOwnerClientId != i2) {
                        throw new RemoteException("Client is not the current owner of the releasing lnb.");
                    }
                    TunerResourceManagerService.this.releaseLnbInternal(lnbResource);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean releaseLock(int i) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "releaseLock");
            return TunerResourceManagerService.m1000$$Nest$mreleaseLockInternal(TunerResourceManagerService.this, i, false, false);
        }

        public final boolean requestCasSession(CasSessionRequest casSessionRequest, int[] iArr) {
            boolean requestCasSessionInternal;
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "requestCasSession");
            if (iArr == null) {
                throw new RemoteException("casSessionHandle can't be null");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(casSessionRequest.clientId)) {
                        throw new RemoteException("Request cas from unregistered client:" + casSessionRequest.clientId);
                    }
                    requestCasSessionInternal = TunerResourceManagerService.this.requestCasSessionInternal(casSessionRequest, iArr);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return requestCasSessionInternal;
        }

        public final boolean requestCiCam(TunerCiCamRequest tunerCiCamRequest, int[] iArr) {
            boolean requestCiCamInternal;
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "requestCiCam");
            if (iArr == null) {
                throw new RemoteException("ciCamHandle can't be null");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(tunerCiCamRequest.clientId)) {
                        throw new RemoteException("Request ciCam from unregistered client:" + tunerCiCamRequest.clientId);
                    }
                    requestCiCamInternal = TunerResourceManagerService.this.requestCiCamInternal(tunerCiCamRequest, iArr);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return requestCiCamInternal;
        }

        public final boolean requestDemux(TunerDemuxRequest tunerDemuxRequest, int[] iArr) {
            boolean requestDemuxInternal;
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "requestDemux");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "requestDemux");
            if (iArr == null) {
                throw new RemoteException("demuxHandle can't be null");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(tunerDemuxRequest.clientId)) {
                        throw new RemoteException("Request demux from unregistered client:" + tunerDemuxRequest.clientId);
                    }
                    requestDemuxInternal = TunerResourceManagerService.this.requestDemuxInternal(tunerDemuxRequest, iArr);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return requestDemuxInternal;
        }

        public final boolean requestDescrambler(TunerDescramblerRequest tunerDescramblerRequest, int[] iArr) {
            boolean requestDescramblerInternal;
            TunerResourceManagerService.this.getContext().enforceCallingPermission("android.permission.ACCESS_TV_DESCRAMBLER", "TunerResourceManagerService: requestDescrambler");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "requestDescrambler");
            if (iArr == null) {
                throw new RemoteException("descramblerHandle can't be null");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(tunerDescramblerRequest.clientId)) {
                        throw new RemoteException("Request descrambler from unregistered client:" + tunerDescramblerRequest.clientId);
                    }
                    requestDescramblerInternal = TunerResourceManagerService.this.requestDescramblerInternal(tunerDescramblerRequest, iArr);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return requestDescramblerInternal;
        }

        public final boolean requestFrontend(TunerFrontendRequest tunerFrontendRequest, int[] iArr) {
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "requestFrontend");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "requestFrontend");
            if (iArr == null) {
                Slog.e("TunerResourceManagerService", "frontendHandle can't be null");
                return false;
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(tunerFrontendRequest.clientId)) {
                        Slog.e("TunerResourceManagerService", "Request frontend from unregistered client: " + tunerFrontendRequest.clientId);
                        return false;
                    }
                    if (((HashSet) TunerResourceManagerService.this.getClientProfile(tunerFrontendRequest.clientId).mUsingFrontendHandles).isEmpty()) {
                        return TunerResourceManagerService.this.requestFrontendInternal(tunerFrontendRequest, iArr);
                    }
                    Slog.e("TunerResourceManagerService", "Release frontend before requesting another one. Client id: " + tunerFrontendRequest.clientId);
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean requestLnb(TunerLnbRequest tunerLnbRequest, int[] iArr) {
            boolean requestLnbInternal;
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "requestLnb");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "requestLnb");
            if (iArr == null) {
                throw new RemoteException("lnbHandle can't be null");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(tunerLnbRequest.clientId)) {
                        throw new RemoteException("Request lnb from unregistered client:" + tunerLnbRequest.clientId);
                    }
                    requestLnbInternal = TunerResourceManagerService.this.requestLnbInternal(tunerLnbRequest, iArr);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return requestLnbInternal;
        }

        public final void restoreResourceMap(int i) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "restoreResourceMap");
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService tunerResourceManagerService = TunerResourceManagerService.this;
                if (i != 0) {
                    tunerResourceManagerService.getClass();
                } else {
                    TunerResourceManagerService.replaceFeResourceMap(tunerResourceManagerService.mFrontendResourcesBackup, tunerResourceManagerService.mFrontendResources);
                    TunerResourceManagerService.replaceFeCounts(tunerResourceManagerService.mFrontendExistingNumsBackup, tunerResourceManagerService.mFrontendExistingNums);
                    TunerResourceManagerService.replaceFeCounts(tunerResourceManagerService.mFrontendUsedNumsBackup, tunerResourceManagerService.mFrontendUsedNums);
                    TunerResourceManagerService.replaceFeCounts(tunerResourceManagerService.mFrontendMaxUsableNumsBackup, tunerResourceManagerService.mFrontendMaxUsableNums);
                }
            }
        }

        public final void setDemuxInfoList(TunerDemuxInfo[] tunerDemuxInfoArr) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "setDemuxInfoList");
            if (tunerDemuxInfoArr == null) {
                throw new RemoteException("TunerDemuxInfo can't be null");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService.this.setDemuxInfoListInternal(tunerDemuxInfoArr);
            }
        }

        public final void setFrontendInfoList(TunerFrontendInfo[] tunerFrontendInfoArr) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "setFrontendInfoList");
            if (tunerFrontendInfoArr == null) {
                throw new RemoteException("TunerFrontendInfo can't be null");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService.this.setFrontendInfoListInternal(tunerFrontendInfoArr);
            }
        }

        public final void setLnbInfoList(int[] iArr) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "setLnbInfoList");
            if (iArr == null) {
                throw new RemoteException("Lnb handle list can't be null");
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService.this.setLnbInfoListInternal(iArr);
            }
        }

        public final boolean setMaxNumberOfFrontends(int i, int i2) {
            boolean z;
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "setMaxNumberOfFrontends");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "setMaxNumberOfFrontends");
            if (i2 < 0) {
                PendingIntentController$$ExternalSyntheticOutline0.m(i2, i, "setMaxNumberOfFrontends failed with maxUsableNum:", " frontendType:", "TunerResourceManagerService");
                return false;
            }
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService tunerResourceManagerService = TunerResourceManagerService.this;
                int i3 = tunerResourceManagerService.mFrontendUsedNums.get(i, -1);
                if (i3 != -1 && i3 > i2) {
                    VaultKeeperService$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "max number of frontend for frontendType: ", " cannot be set to a value lower than the current usage count. (requested max num = ", ", current usage = "), i3, "TunerResourceManagerService");
                    z = false;
                }
                tunerResourceManagerService.mFrontendMaxUsableNums.put(i, i2);
                z = true;
            }
            return z;
        }

        public final void shareFrontend(int i, int i2) {
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "shareFrontend");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "shareFrontend");
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(i)) {
                        throw new RemoteException("Share frontend request from an unregistered client:" + i);
                    }
                    if (!TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new RemoteException("Request to share frontend with an unregistered client:" + i2);
                    }
                    if (((HashSet) TunerResourceManagerService.this.getClientProfile(i2).mUsingFrontendHandles).isEmpty()) {
                        throw new RemoteException("Request to share frontend with a client that has no frontend resources. Target client id:" + i2);
                    }
                    TunerResourceManagerService.this.shareFrontendInternal(i, i2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void storeResourceMap(int i) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "storeResourceMap");
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService tunerResourceManagerService = TunerResourceManagerService.this;
                if (i != 0) {
                    tunerResourceManagerService.getClass();
                } else {
                    TunerResourceManagerService.replaceFeResourceMap(tunerResourceManagerService.mFrontendResources, tunerResourceManagerService.mFrontendResourcesBackup);
                    TunerResourceManagerService.replaceFeCounts(tunerResourceManagerService.mFrontendExistingNums, tunerResourceManagerService.mFrontendExistingNumsBackup);
                    TunerResourceManagerService.replaceFeCounts(tunerResourceManagerService.mFrontendUsedNums, tunerResourceManagerService.mFrontendUsedNumsBackup);
                    TunerResourceManagerService.replaceFeCounts(tunerResourceManagerService.mFrontendMaxUsableNums, tunerResourceManagerService.mFrontendMaxUsableNumsBackup);
                }
            }
        }

        public final boolean transferOwner(int i, int i2, int i3) {
            TunerResourceManagerService.m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService.this, "transferOwner");
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "transferOwner");
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (!TunerResourceManagerService.this.checkClientExists(i2)) {
                        Slog.e("TunerResourceManagerService", "currentOwnerId:" + i2 + " does not exit");
                        return false;
                    }
                    if (TunerResourceManagerService.this.checkClientExists(i3)) {
                        return TunerResourceManagerService.this.transferOwnerInternal(i, i2, i3);
                    }
                    Slog.e("TunerResourceManagerService", "newOwnerId:" + i3 + " does not exit");
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void unregisterClientProfile(int i) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "unregisterClientProfile");
            synchronized (TunerResourceManagerService.this.mLock) {
                try {
                    if (TunerResourceManagerService.this.checkClientExists(i)) {
                        TunerResourceManagerService.this.unregisterClientProfileInternal(i);
                        return;
                    }
                    Slog.e("TunerResourceManagerService", "Unregistering non exists client:" + i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateCasInfo(int i, int i2) {
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "updateCasInfo");
            synchronized (TunerResourceManagerService.this.mLock) {
                TunerResourceManagerService.this.updateCasInfoInternal(i, i2);
            }
        }

        public final boolean updateClientPriority(int i, int i2, int i3) {
            boolean updateClientPriorityInternal;
            TunerResourceManagerService.m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService.this, "updateClientPriority");
            synchronized (TunerResourceManagerService.this.mLock) {
                updateClientPriorityInternal = TunerResourceManagerService.this.updateClientPriorityInternal(i, i2, i3);
            }
            return updateClientPriorityInternal;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ResourcesReclaimListenerRecord implements IBinder.DeathRecipient {
        public final int mClientId;
        public final IResourcesReclaimListener mListener;

        public ResourcesReclaimListenerRecord(IResourcesReclaimListener iResourcesReclaimListener, int i) {
            this.mListener = iResourcesReclaimListener;
            this.mClientId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            try {
                synchronized (TunerResourceManagerService.this.mLock) {
                    try {
                        if (TunerResourceManagerService.this.checkClientExists(this.mClientId)) {
                            TunerResourceManagerService.this.removeClientProfile(this.mClientId);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                TunerResourceManagerService.m1000$$Nest$mreleaseLockInternal(TunerResourceManagerService.this, this.mClientId, true, true);
            }
        }
    }

    /* renamed from: -$$Nest$mdumpMap, reason: not valid java name */
    public static void m996$$Nest$mdumpMap(TunerResourceManagerService tunerResourceManagerService, Map map, String str, IndentingPrintWriter indentingPrintWriter) {
        tunerResourceManagerService.getClass();
        if (map != null) {
            indentingPrintWriter.println(str);
            indentingPrintWriter.increaseIndent();
            for (Map.Entry entry : map.entrySet()) {
                indentingPrintWriter.print(entry.getKey() + " : " + entry.getValue());
                indentingPrintWriter.print("\n");
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* renamed from: -$$Nest$mdumpSIA, reason: not valid java name */
    public static void m997$$Nest$mdumpSIA(TunerResourceManagerService tunerResourceManagerService, SparseIntArray sparseIntArray, String str, IndentingPrintWriter indentingPrintWriter) {
        tunerResourceManagerService.getClass();
        if (sparseIntArray != null) {
            indentingPrintWriter.println(str);
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < sparseIntArray.size(); i++) {
                indentingPrintWriter.print(sparseIntArray.keyAt(i) + " : " + sparseIntArray.valueAt(i));
                indentingPrintWriter.print(", ");
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* renamed from: -$$Nest$menforceTrmAccessPermission, reason: not valid java name */
    public static void m998$$Nest$menforceTrmAccessPermission(TunerResourceManagerService tunerResourceManagerService, String str) {
        tunerResourceManagerService.getContext().enforceCallingOrSelfPermission("android.permission.TUNER_RESOURCE_ACCESS", "TunerResourceManagerService: ".concat(str));
    }

    /* renamed from: -$$Nest$menforceTunerAccessPermission, reason: not valid java name */
    public static void m999$$Nest$menforceTunerAccessPermission(TunerResourceManagerService tunerResourceManagerService, String str) {
        tunerResourceManagerService.getContext().enforceCallingPermission("android.permission.ACCESS_TV_TUNER", "TunerResourceManagerService: ".concat(str));
    }

    /* renamed from: -$$Nest$mreleaseLockInternal, reason: not valid java name */
    public static boolean m1000$$Nest$mreleaseLockInternal(TunerResourceManagerService tunerResourceManagerService, int i, boolean z, boolean z2) {
        if (!tunerResourceManagerService.lockForTunerApiLock(i, "releaseLockInternal()")) {
            return false;
        }
        try {
            int i2 = tunerResourceManagerService.mTunerApiLockHolder;
            if (i2 != i) {
                if (i2 == -1) {
                    if (!z2) {
                        Slog.w("TunerResourceManagerService", "releaseLockInternal(" + i + ", 500) - called while there is no current holder");
                    }
                    if (!tunerResourceManagerService.mLockForTRMSLock.isHeldByCurrentThread()) {
                        return false;
                    }
                } else {
                    if (!z2) {
                        Slog.e("TunerResourceManagerService", "releaseLockInternal(" + i + ", 500) - called while someone else:" + tunerResourceManagerService.mTunerApiLockHolder + "is the current holder");
                    }
                    if (!tunerResourceManagerService.mLockForTRMSLock.isHeldByCurrentThread()) {
                        return false;
                    }
                }
                tunerResourceManagerService.mLockForTRMSLock.unlock();
                return false;
            }
            int i3 = tunerResourceManagerService.mTunerApiLockNestedCount - 1;
            tunerResourceManagerService.mTunerApiLockNestedCount = i3;
            if (z || i3 <= 0) {
                if (DEBUG) {
                    Slog.d("TunerResourceManagerService", "SUCCESS:releaseLockInternal(" + i + ", 500, " + z + ", " + z2 + ") - signaling!");
                }
                tunerResourceManagerService.mTunerApiLockHolder = -1;
                tunerResourceManagerService.mTunerApiLockHolderThreadId = -1L;
                tunerResourceManagerService.mTunerApiLockNestedCount = 0;
                tunerResourceManagerService.mTunerApiLockReleasedCV.signal();
            } else if (DEBUG) {
                Slog.d("TunerResourceManagerService", "releaseLockInternal(" + i + ", 500, " + z + ", " + z2 + ") - NOT signaling because nested count is not zero (" + tunerResourceManagerService.mTunerApiLockNestedCount + ")");
            }
            if (tunerResourceManagerService.mLockForTRMSLock.isHeldByCurrentThread()) {
                tunerResourceManagerService.mLockForTRMSLock.unlock();
            }
            return true;
        } catch (Throwable th) {
            if (tunerResourceManagerService.mLockForTRMSLock.isHeldByCurrentThread()) {
                tunerResourceManagerService.mLockForTRMSLock.unlock();
            }
            throw th;
        }
    }

    /* renamed from: -$$Nest$mvalidateResourceHandle, reason: not valid java name */
    public static boolean m1001$$Nest$mvalidateResourceHandle(TunerResourceManagerService tunerResourceManagerService, int i, int i2) {
        tunerResourceManagerService.getClass();
        return i2 != -1 && (((-16777216) & i2) >> 24) == i;
    }

    public TunerResourceManagerService(Context context) {
        super(context);
        this.mClientProfiles = new HashMap();
        this.mNextUnusedClientId = 0;
        this.mFrontendResources = new HashMap();
        this.mFrontendMaxUsableNums = new SparseIntArray();
        this.mFrontendUsedNums = new SparseIntArray();
        this.mFrontendExistingNums = new SparseIntArray();
        this.mFrontendResourcesBackup = new HashMap();
        this.mFrontendMaxUsableNumsBackup = new SparseIntArray();
        this.mFrontendUsedNumsBackup = new SparseIntArray();
        this.mFrontendExistingNumsBackup = new SparseIntArray();
        this.mDemuxResources = new HashMap();
        this.mLnbResources = new HashMap();
        this.mCasResources = new HashMap();
        this.mCiCamResources = new HashMap();
        this.mListeners = new HashMap();
        UseCasePriorityHints useCasePriorityHints = new UseCasePriorityHints();
        useCasePriorityHints.mPriorityHints = new SparseArray();
        useCasePriorityHints.mVendorDefinedUseCase = new HashSet();
        useCasePriorityHints.mDefaultForeground = 150;
        useCasePriorityHints.mDefaultBackground = 50;
        this.mPriorityCongfig = useCasePriorityHints;
        this.mResourceRequestCount = 0;
        this.mLock = new Object();
        ReentrantLock reentrantLock = new ReentrantLock();
        this.mLockForTRMSLock = reentrantLock;
        this.mTunerApiLockReleasedCV = reentrantLock.newCondition();
        this.mTunerApiLockHolder = -1;
        this.mTunerApiLockHolderThreadId = -1L;
        this.mTunerApiLockNestedCount = 0;
    }

    public static void replaceFeCounts(SparseIntArray sparseIntArray, SparseIntArray sparseIntArray2) {
        if (sparseIntArray2 != null) {
            sparseIntArray2.clear();
            if (sparseIntArray != null) {
                for (int i = 0; i < sparseIntArray.size(); i++) {
                    sparseIntArray2.put(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
                }
            }
        }
    }

    public static void replaceFeResourceMap(Map map, Map map2) {
        if (map2 != null) {
            map2.clear();
            if (map == null || map.size() <= 0) {
                return;
            }
            map2.putAll(map);
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        if (DEBUG) {
            Slog.w("TunerResourceManagerService", "Native media resource manager service has died");
        }
        synchronized (this.mLock) {
            this.mMediaResourceManager = null;
        }
    }

    public boolean checkClientExists(int i) {
        return ((HashMap) this.mClientProfiles).keySet().contains(Integer.valueOf(i));
    }

    public boolean checkIsForeground(int i) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = this.mActivityManager;
        if (activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == i && runningAppProcessInfo.importance == 100) {
                return true;
            }
        }
        return false;
    }

    public final void clearAllResourcesAndClientMapping(ClientProfile clientProfile) {
        if (clientProfile == null) {
            return;
        }
        Iterator it = ((HashSet) clientProfile.mUsingLnbHandles).iterator();
        while (it.hasNext()) {
            LnbResource lnbResource = getLnbResource(((Integer) it.next()).intValue());
            lnbResource.mIsInUse = false;
            lnbResource.mOwnerClientId = -1;
        }
        int i = clientProfile.mUsingCasSystemId;
        int i2 = clientProfile.mId;
        if (i != -1) {
            getCasResource(i).removeOwner(i2);
        }
        int i3 = clientProfile.mUsingCiCamId;
        if (i3 != -1) {
            getCiCamResource(i3).removeOwner(i2);
        }
        Iterator it2 = ((HashSet) clientProfile.mUsingDemuxHandles).iterator();
        while (it2.hasNext()) {
            DemuxResource demuxResource = getDemuxResource(((Integer) it2.next()).intValue());
            demuxResource.mIsInUse = false;
            demuxResource.mOwnerClientId = -1;
        }
        clearFrontendAndClientMapping(clientProfile);
        ((HashSet) clientProfile.mUsingFrontendHandles).clear();
        ((HashSet) clientProfile.mShareFeClientIds).clear();
        clientProfile.mPrimaryUsingFrontendHandle = -1;
        ((HashSet) clientProfile.mUsingLnbHandles).clear();
        clientProfile.mUsingCasSystemId = -1;
        clientProfile.mUsingCiCamId = -1;
    }

    public final void clearFrontendAndClientMapping(ClientProfile clientProfile) {
        FrontendResource frontendResource;
        SparseIntArray sparseIntArray;
        int i;
        int i2;
        if (clientProfile == null) {
            return;
        }
        Iterator it = ((HashSet) clientProfile.mUsingFrontendHandles).iterator();
        while (it.hasNext()) {
            FrontendResource frontendResource2 = getFrontendResource(((Integer) it.next()).intValue());
            int i3 = frontendResource2.mOwnerClientId;
            int i4 = clientProfile.mId;
            if (i3 == i4) {
                frontendResource2.mIsInUse = false;
                frontendResource2.mOwnerClientId = -1;
            } else {
                ClientProfile clientProfile2 = getClientProfile(i3);
                if (clientProfile2 != null) {
                    ((HashSet) clientProfile2.mShareFeClientIds).remove(Integer.valueOf(i4));
                }
            }
        }
        int i5 = clientProfile.mPrimaryUsingFrontendHandle;
        if (i5 != -1 && (frontendResource = getFrontendResource(i5)) != null && (i2 = (sparseIntArray = this.mFrontendUsedNums).get((i = frontendResource.mType), -1)) != -1) {
            sparseIntArray.put(i, i2 - 1);
        }
        ((HashSet) clientProfile.mUsingFrontendHandles).clear();
        ((HashSet) clientProfile.mShareFeClientIds).clear();
        clientProfile.mShareeFeClientId = -1;
        clientProfile.mPrimaryUsingFrontendHandle = -1;
    }

    public void clientPriorityUpdateOnRequest(ClientProfile clientProfile) {
        if (clientProfile.mIsPriorityOverwritten) {
            return;
        }
        int clientPriority = getClientPriority(clientProfile.mUseCase, checkIsForeground(clientProfile.mProcessId));
        if (clientPriority < 0) {
            return;
        }
        clientProfile.mPriority = clientPriority;
    }

    public final int generateResourceHandle(int i, int i2) {
        int i3 = ((i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) << 24) | (i2 << 16);
        int i4 = this.mResourceRequestCount;
        this.mResourceRequestCount = i4 + 1;
        return (65535 & i4) | i3;
    }

    public CasResource getCasResource(int i) {
        return (CasResource) ((HashMap) this.mCasResources).get(Integer.valueOf(i));
    }

    public Map getCasResources() {
        return this.mCasResources;
    }

    public CiCamResource getCiCamResource(int i) {
        return (CiCamResource) ((HashMap) this.mCiCamResources).get(Integer.valueOf(i));
    }

    public Map getCiCamResources() {
        return this.mCiCamResources;
    }

    public int getClientPriority(int i, boolean z) {
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "getClientPriority useCase=" + i + ", isForeground=" + z + ")");
        }
        if (z) {
            UseCasePriorityHints useCasePriorityHints = this.mPriorityCongfig;
            return (useCasePriorityHints.mPriorityHints.get(i) == null || ((int[]) useCasePriorityHints.mPriorityHints.get(i)).length != 2) ? useCasePriorityHints.mDefaultForeground : ((int[]) useCasePriorityHints.mPriorityHints.get(i))[0];
        }
        UseCasePriorityHints useCasePriorityHints2 = this.mPriorityCongfig;
        return (useCasePriorityHints2.mPriorityHints.get(i) == null || ((int[]) useCasePriorityHints2.mPriorityHints.get(i)).length != 2) ? useCasePriorityHints2.mDefaultBackground : ((int[]) useCasePriorityHints2.mPriorityHints.get(i))[1];
    }

    public ClientProfile getClientProfile(int i) {
        return (ClientProfile) ((HashMap) this.mClientProfiles).get(Integer.valueOf(i));
    }

    public DemuxResource getDemuxResource(int i) {
        return (DemuxResource) ((HashMap) this.mDemuxResources).get(Integer.valueOf(i));
    }

    public Map getDemuxResources() {
        return this.mDemuxResources;
    }

    public FrontendResource getFrontendResource(int i) {
        return (FrontendResource) ((HashMap) this.mFrontendResources).get(Integer.valueOf(i));
    }

    public Map getFrontendResources() {
        return this.mFrontendResources;
    }

    public LnbResource getLnbResource(int i) {
        return (LnbResource) ((HashMap) this.mLnbResources).get(Integer.valueOf(i));
    }

    public Map getLnbResources() {
        return this.mLnbResources;
    }

    public int getResourceIdFromHandle(int i) {
        return i == -1 ? i : (16711680 & i) >> 16;
    }

    public boolean isHigherPriorityInternal(ResourceClientProfile resourceClientProfile, ResourceClientProfile resourceClientProfile2) {
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "isHigherPriority(challengerProfile=" + resourceClientProfile + ", holderProfile=" + resourceClientProfile + ")");
        }
        TvInputManager tvInputManager = this.mTvInputManager;
        if (tvInputManager == null) {
            Slog.e("TunerResourceManagerService", "TvInputManager is null. Can't compare the priority.");
            return true;
        }
        String str = resourceClientProfile.tvInputSessionId;
        int callingPid = str == null ? Binder.getCallingPid() : tvInputManager.getClientPid(str);
        String str2 = resourceClientProfile2.tvInputSessionId;
        return getClientPriority(resourceClientProfile.useCase, checkIsForeground(callingPid)) > getClientPriority(resourceClientProfile2.useCase, checkIsForeground(str2 == null ? Binder.getCallingPid() : this.mTvInputManager.getClientPid(str2)));
    }

    public final boolean lockForTunerApiLock(int i, String str) {
        try {
            if (this.mLockForTRMSLock.tryLock(500L, TimeUnit.MILLISECONDS)) {
                return true;
            }
            Slog.e("TunerResourceManagerService", "FAILED to lock mLockForTRMSLock in " + str + ", clientId:" + i + ", timeoutMS:500, mTunerApiLockHolder:" + this.mTunerApiLockHolder);
            return false;
        } catch (InterruptedException e) {
            Slog.e("TunerResourceManagerService", "exception thrown in " + str + ":" + e);
            if (this.mLockForTRMSLock.isHeldByCurrentThread()) {
                this.mLockForTRMSLock.unlock();
            }
            return false;
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        onStart(false);
    }

    public void onStart(boolean z) {
        if (!z) {
            publishBinderService("tv_tuner_resource_mgr", new BinderService());
        }
        this.mTvInputManager = (TvInputManager) getContext().getSystemService("tv_input");
        this.mActivityManager = (ActivityManager) getContext().getSystemService("activity");
        UseCasePriorityHints useCasePriorityHints = this.mPriorityCongfig;
        useCasePriorityHints.getClass();
        File file = new File("/vendor/etc/tunerResourceManagerUseCaseConfig.xml");
        if (file.exists()) {
            try {
                useCasePriorityHints.parseInternal(new FileInputStream(file));
            } catch (IOException e) {
                Slog.e("UseCasePriorityHints", "Error reading vendor file: " + file, e);
            } catch (XmlPullParserException e2) {
                Slog.e("UseCasePriorityHints", "Unable to parse vendor file: " + file, e2);
            }
        } else {
            if (UseCasePriorityHints.DEBUG) {
                Slog.i("UseCasePriorityHints", "no vendor priority configuration available. Using default priority");
            }
            useCasePriorityHints.addNewUseCasePriority(100, 180, 100);
            useCasePriorityHints.addNewUseCasePriority(200, 450, 200);
            useCasePriorityHints.addNewUseCasePriority(300, SystemService.PHASE_LOCK_SETTINGS_READY, 300);
            useCasePriorityHints.addNewUseCasePriority(400, 490, 400);
            useCasePriorityHints.addNewUseCasePriority(500, 600, 500);
        }
        if (!z && !SystemProperties.getBoolean("ro.tuner.lazyhal", false)) {
            SystemProperties.set("tuner.server.enable", "true");
        }
        if (this.mMediaResourceManager == null) {
            IBinder binderService = getBinderService("media.resource_manager");
            if (binderService == null) {
                Slog.w("TunerResourceManagerService", "Resource Manager Service not available.");
                return;
            }
            try {
                binderService.linkToDeath(this, 0);
                this.mMediaResourceManager = IResourceManagerService.Stub.asInterface(binderService);
            } catch (RemoteException unused) {
                Slog.w("TunerResourceManagerService", "Could not link to death of native resource manager service.");
            }
        }
    }

    public boolean reclaimResource(int i, int i2) {
        Binder.allowBlockingForCurrentThread();
        ClientProfile clientProfile = getClientProfile(i);
        if (clientProfile == null) {
            return true;
        }
        Iterator it = ((HashSet) clientProfile.mShareFeClientIds).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            int intValue = num.intValue();
            try {
                ((ResourcesReclaimListenerRecord) ((HashMap) this.mListeners).get(num)).mListener.onReclaimResources();
                clearAllResourcesAndClientMapping(getClientProfile(intValue));
            } catch (RemoteException e) {
                Slog.e("TunerResourceManagerService", "Failed to reclaim resources on client " + intValue, e);
                return false;
            }
        }
        if (DEBUG) {
            ASKSManagerService$$ExternalSyntheticOutline0.m(i2, i, "Reclaiming resources because higher priority client request resource type ", ", clientId:", "TunerResourceManagerService");
        }
        try {
            ((ResourcesReclaimListenerRecord) ((HashMap) this.mListeners).get(Integer.valueOf(i))).mListener.onReclaimResources();
            clearAllResourcesAndClientMapping(clientProfile);
            return true;
        } catch (RemoteException e2) {
            Slog.e("TunerResourceManagerService", "Failed to reclaim resources on client " + i, e2);
            return false;
        }
    }

    public void registerClientProfileInternal(ResourceClientProfile resourceClientProfile, IResourcesReclaimListener iResourcesReclaimListener, int[] iArr) {
        IResourceManagerService iResourceManagerService;
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "registerClientProfile(clientProfile=" + resourceClientProfile + ")");
        }
        iArr[0] = -1;
        TvInputManager tvInputManager = this.mTvInputManager;
        if (tvInputManager == null) {
            Slog.e("TunerResourceManagerService", "TvInputManager is null. Can't register client profile.");
            return;
        }
        int i = this.mNextUnusedClientId;
        this.mNextUnusedClientId = i + 1;
        iArr[0] = i;
        String str = resourceClientProfile.tvInputSessionId;
        int callingPid = str == null ? Binder.getCallingPid() : tvInputManager.getClientPid(str);
        if (resourceClientProfile.tvInputSessionId != null && (iResourceManagerService = this.mMediaResourceManager) != null) {
            try {
                iResourceManagerService.overridePid(Binder.getCallingPid(), callingPid);
            } catch (RemoteException e) {
                Slog.e("TunerResourceManagerService", "Could not overridePid in resourceManagerSercice, remote exception: " + e);
            }
        }
        ClientProfile.Builder builder = new ClientProfile.Builder(iArr[0]);
        builder.mTvInputSessionId = resourceClientProfile.tvInputSessionId;
        builder.mUseCase = resourceClientProfile.useCase;
        builder.mProcessId = callingPid;
        ClientProfile clientProfile = new ClientProfile(builder);
        int clientPriority = getClientPriority(resourceClientProfile.useCase, checkIsForeground(callingPid));
        if (clientPriority >= 0) {
            clientProfile.mPriority = clientPriority;
        }
        int i2 = iArr[0];
        ((HashMap) this.mClientProfiles).put(Integer.valueOf(i2), clientProfile);
        if (iResourcesReclaimListener == null) {
            if (DEBUG) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i2, "Listener is null when client ", " registered!", "TunerResourceManagerService");
            }
        } else {
            ResourcesReclaimListenerRecord resourcesReclaimListenerRecord = new ResourcesReclaimListenerRecord(iResourcesReclaimListener, i2);
            try {
                iResourcesReclaimListener.asBinder().linkToDeath(resourcesReclaimListenerRecord, 0);
                ((HashMap) this.mListeners).put(Integer.valueOf(i2), resourcesReclaimListenerRecord);
            } catch (RemoteException unused) {
                Slog.w("TunerResourceManagerService", "Listener already died.");
            }
        }
    }

    public void releaseCasSessionInternal(CasResource casResource, int i) {
        if (DEBUG) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("releaseCasSession(sessionResourceId="), casResource.mSystemId, ")", "TunerResourceManagerService");
        }
        if (((HashMap) casResource.mOwnerClientIdsToSessionNum).containsKey(Integer.valueOf(i))) {
            int intValue = ((Integer) ((HashMap) casResource.mOwnerClientIdsToSessionNum).get(Integer.valueOf(i))).intValue();
            if (intValue > 0) {
                ((HashMap) casResource.mOwnerClientIdsToSessionNum).put(Integer.valueOf(i), Integer.valueOf(intValue - 1));
                casResource.mAvailableSessionNum++;
            }
        }
        if (((Integer) ((HashMap) casResource.mOwnerClientIdsToSessionNum).get(Integer.valueOf(i))).intValue() > 0) {
            return;
        }
        ClientProfile clientProfile = getClientProfile(i);
        casResource.removeOwner(i);
        clientProfile.mUsingCasSystemId = -1;
    }

    public void releaseCiCamInternal(CiCamResource ciCamResource, int i) {
        if (DEBUG) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("releaseCiCamInternal(ciCamId="), ciCamResource.mSystemId, ")", "TunerResourceManagerService");
        }
        ClientProfile clientProfile = getClientProfile(i);
        ciCamResource.removeOwner(i);
        clientProfile.mUsingCiCamId = -1;
    }

    public void releaseDemuxInternal(DemuxResource demuxResource) {
        if (DEBUG) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("releaseDemux(DemuxHandle="), demuxResource.mHandle, ")", "TunerResourceManagerService");
        }
        ClientProfile clientProfile = getClientProfile(demuxResource.mOwnerClientId);
        demuxResource.mIsInUse = false;
        demuxResource.mOwnerClientId = -1;
        ((HashSet) clientProfile.mUsingDemuxHandles).remove(Integer.valueOf(demuxResource.mHandle));
    }

    public void releaseFrontendInternal(FrontendResource frontendResource, int i) {
        ClientProfile clientProfile;
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", ActivityManagerService$$ExternalSyntheticOutline0.m(frontendResource.mHandle, i, ", clientId=", " )", new StringBuilder("releaseFrontend(id=")));
        }
        int i2 = frontendResource.mOwnerClientId;
        if (i == i2 && (clientProfile = getClientProfile(i2)) != null) {
            Iterator it = ((HashSet) clientProfile.mShareFeClientIds).iterator();
            while (it.hasNext()) {
                reclaimResource(((Integer) it.next()).intValue(), 0);
            }
        }
        clearFrontendAndClientMapping(getClientProfile(i));
    }

    public void releaseLnbInternal(LnbResource lnbResource) {
        if (DEBUG) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("releaseLnb(lnbHandle="), lnbResource.mHandle, ")", "TunerResourceManagerService");
        }
        ClientProfile clientProfile = getClientProfile(lnbResource.mOwnerClientId);
        lnbResource.mIsInUse = false;
        lnbResource.mOwnerClientId = -1;
        ((HashSet) clientProfile.mUsingLnbHandles).remove(Integer.valueOf(lnbResource.mHandle));
    }

    public final void removeClientProfile(int i) {
        Iterator it = ((HashSet) getClientProfile(i).mShareFeClientIds).iterator();
        while (it.hasNext()) {
            clearFrontendAndClientMapping(getClientProfile(((Integer) it.next()).intValue()));
        }
        clearAllResourcesAndClientMapping(getClientProfile(i));
        ((HashMap) this.mClientProfiles).remove(Integer.valueOf(i));
        synchronized (this.mLock) {
            try {
                ResourcesReclaimListenerRecord resourcesReclaimListenerRecord = (ResourcesReclaimListenerRecord) ((HashMap) this.mListeners).remove(Integer.valueOf(i));
                if (resourcesReclaimListenerRecord != null) {
                    resourcesReclaimListenerRecord.mListener.asBinder().unlinkToDeath(resourcesReclaimListenerRecord, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean requestCasSessionInternal(CasSessionRequest casSessionRequest, int[] iArr) {
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "requestCasSession(request=" + casSessionRequest + ")");
        }
        CasResource casResource = getCasResource(casSessionRequest.casSystemId);
        if (casResource == null) {
            CasResource.Builder builder = new CasResource.Builder(casSessionRequest.casSystemId);
            builder.mMaxSessionNum = Integer.MAX_VALUE;
            CasResource casResource2 = new CasResource(builder);
            ((HashMap) this.mCasResources).put(Integer.valueOf(casResource2.mSystemId), casResource2);
            casResource = casResource2;
        }
        iArr[0] = -1;
        ClientProfile clientProfile = getClientProfile(casSessionRequest.clientId);
        clientPriorityUpdateOnRequest(clientProfile);
        boolean z = casResource.mAvailableSessionNum == 0;
        int i = casResource.mSystemId;
        if (!z) {
            iArr[0] = generateResourceHandle(4, i);
            int i2 = casSessionRequest.casSystemId;
            int i3 = casSessionRequest.clientId;
            CasResource casResource3 = getCasResource(i2);
            ClientProfile clientProfile2 = getClientProfile(i3);
            casResource3.setOwner(i3);
            clientProfile2.mUsingCasSystemId = i2;
            return true;
        }
        Iterator it = casResource.getOwnerClientIds().iterator();
        int i4 = 1001;
        boolean z2 = false;
        int i5 = -1;
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            int updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority(intValue);
            if (i4 > updateAndGetOwnerClientPriority) {
                z2 = clientProfile.mProcessId == getClientProfile(intValue).mProcessId;
                i5 = intValue;
                i4 = updateAndGetOwnerClientPriority;
            }
        }
        if (i5 <= -1 || ((clientProfile.getPriority() <= i4 && !(clientProfile.getPriority() == i4 && z2)) || !reclaimResource(i5, 4))) {
            return false;
        }
        iArr[0] = generateResourceHandle(4, i);
        int i6 = casSessionRequest.casSystemId;
        int i7 = casSessionRequest.clientId;
        CasResource casResource4 = getCasResource(i6);
        ClientProfile clientProfile3 = getClientProfile(i7);
        casResource4.setOwner(i7);
        clientProfile3.mUsingCasSystemId = i6;
        return true;
    }

    public boolean requestCiCamInternal(TunerCiCamRequest tunerCiCamRequest, int[] iArr) {
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "requestCiCamInternal(TunerCiCamRequest=" + tunerCiCamRequest + ")");
        }
        CiCamResource ciCamResource = getCiCamResource(tunerCiCamRequest.ciCamId);
        if (ciCamResource == null) {
            CiCamResource.Builder builder = new CiCamResource.Builder(tunerCiCamRequest.ciCamId);
            builder.mMaxSessionNum = Integer.MAX_VALUE;
            CiCamResource ciCamResource2 = new CiCamResource(builder);
            ((HashMap) this.mCiCamResources).put(Integer.valueOf(ciCamResource2.mSystemId), ciCamResource2);
            ciCamResource = ciCamResource2;
        }
        iArr[0] = -1;
        ClientProfile clientProfile = getClientProfile(tunerCiCamRequest.clientId);
        clientPriorityUpdateOnRequest(clientProfile);
        boolean z = ciCamResource.mAvailableSessionNum == 0;
        int i = ciCamResource.mSystemId;
        if (!z) {
            iArr[0] = generateResourceHandle(5, i);
            int i2 = tunerCiCamRequest.ciCamId;
            int i3 = tunerCiCamRequest.clientId;
            CiCamResource ciCamResource3 = getCiCamResource(i2);
            ClientProfile clientProfile2 = getClientProfile(i3);
            ciCamResource3.setOwner(i3);
            clientProfile2.mUsingCiCamId = i2;
            return true;
        }
        Iterator it = ciCamResource.getOwnerClientIds().iterator();
        int i4 = 1001;
        boolean z2 = false;
        int i5 = -1;
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            int updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority(intValue);
            if (i4 > updateAndGetOwnerClientPriority) {
                z2 = clientProfile.mProcessId == getClientProfile(intValue).mProcessId;
                i5 = intValue;
                i4 = updateAndGetOwnerClientPriority;
            }
        }
        if (i5 <= -1 || ((clientProfile.getPriority() <= i4 && !(clientProfile.getPriority() == i4 && z2)) || !reclaimResource(i5, 5))) {
            return false;
        }
        iArr[0] = generateResourceHandle(5, i);
        int i6 = tunerCiCamRequest.ciCamId;
        int i7 = tunerCiCamRequest.clientId;
        CiCamResource ciCamResource4 = getCiCamResource(i6);
        ClientProfile clientProfile3 = getClientProfile(i7);
        ciCamResource4.setOwner(i7);
        clientProfile3.mUsingCiCamId = i6;
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0072, code lost:
    
        if (r15 == (r14.mFilterTypes & r15)) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00de A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean requestDemuxInternal(android.media.tv.tunerresourcemanager.TunerDemuxRequest r20, int[] r21) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.requestDemuxInternal(android.media.tv.tunerresourcemanager.TunerDemuxRequest, int[]):boolean");
    }

    public boolean requestDescramblerInternal(TunerDescramblerRequest tunerDescramblerRequest, int[] iArr) {
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "requestDescrambler(request=" + tunerDescramblerRequest + ")");
        }
        iArr[0] = generateResourceHandle(2, 0);
        return true;
    }

    public boolean requestFrontendInternal(TunerFrontendRequest tunerFrontendRequest, int[] iArr) {
        int updateAndGetOwnerClientPriority;
        int i;
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "requestFrontend(request=" + tunerFrontendRequest + ")");
        }
        int i2 = 0;
        iArr[0] = -1;
        ClientProfile clientProfile = getClientProfile(tunerFrontendRequest.clientId);
        if (clientProfile == null) {
            return false;
        }
        clientPriorityUpdateOnRequest(clientProfile);
        boolean z = tunerFrontendRequest.desiredId != -1;
        Iterator it = getFrontendResources().values().iterator();
        int i3 = 1001;
        boolean z2 = false;
        int i4 = -1;
        int i5 = -1;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            FrontendResource frontendResource = (FrontendResource) it.next();
            int resourceIdFromHandle = getResourceIdFromHandle(frontendResource.mHandle);
            int i6 = tunerFrontendRequest.frontendType;
            int i7 = frontendResource.mType;
            if (i7 == i6 && (!z || resourceIdFromHandle == tunerFrontendRequest.desiredId)) {
                boolean z3 = frontendResource.mIsInUse;
                int i8 = frontendResource.mHandle;
                if (!z3) {
                    int i9 = this.mFrontendMaxUsableNums.get(i6, -1);
                    if (i9 != -1) {
                        int i10 = this.mFrontendUsedNums.get(i6, -1);
                        if (i10 == -1) {
                            i10 = i2;
                        }
                        if (i10 >= i9) {
                            continue;
                        }
                    }
                    if (((HashSet) frontendResource.mExclusiveGroupMemberHandles).isEmpty()) {
                        i4 = i8;
                        break;
                    }
                    if (i4 == -1) {
                        i4 = i8;
                    }
                } else if (i4 == -1) {
                    int i11 = frontendResource.mOwnerClientId;
                    ClientProfile clientProfile2 = getClientProfile(i11);
                    if (clientProfile2 == null) {
                        updateAndGetOwnerClientPriority = i2;
                    } else {
                        updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority(i11);
                        Iterator it2 = ((HashSet) clientProfile2.mShareFeClientIds).iterator();
                        while (it2.hasNext()) {
                            int updateAndGetOwnerClientPriority2 = updateAndGetOwnerClientPriority(((Integer) it2.next()).intValue());
                            if (updateAndGetOwnerClientPriority2 > updateAndGetOwnerClientPriority) {
                                updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority2;
                            }
                        }
                    }
                    if (i3 > updateAndGetOwnerClientPriority) {
                        if (i7 != getFrontendResource(getClientProfile(frontendResource.mOwnerClientId).mPrimaryUsingFrontendHandle).mType && (i = this.mFrontendMaxUsableNums.get(i7, -1)) != -1) {
                            int i12 = this.mFrontendUsedNums.get(i7, -1);
                            if (i12 == -1) {
                                i12 = 0;
                            }
                            if (i12 >= i) {
                            }
                        }
                        z2 = clientProfile.mProcessId == getClientProfile(frontendResource.mOwnerClientId).mProcessId;
                        i3 = updateAndGetOwnerClientPriority;
                        i5 = i8;
                    }
                }
            }
            i2 = 0;
        }
        if (i4 != -1) {
            iArr[0] = i4;
            updateFrontendClientMappingOnNewGrant(i4, tunerFrontendRequest.clientId);
            return true;
        }
        if (i5 == -1) {
            return false;
        }
        if ((clientProfile.getPriority() <= i3 && (clientProfile.getPriority() != i3 || !z2)) || !reclaimResource(getFrontendResource(i5).mOwnerClientId, 0)) {
            return false;
        }
        iArr[0] = i5;
        updateFrontendClientMappingOnNewGrant(i5, tunerFrontendRequest.clientId);
        return true;
    }

    public boolean requestLnbInternal(TunerLnbRequest tunerLnbRequest, int[] iArr) {
        int i;
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "requestLnb(request=" + tunerLnbRequest + ")");
        }
        iArr[0] = -1;
        ClientProfile clientProfile = getClientProfile(tunerLnbRequest.clientId);
        clientPriorityUpdateOnRequest(clientProfile);
        Iterator it = getLnbResources().values().iterator();
        int i2 = 1001;
        boolean z = false;
        int i3 = -1;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            LnbResource lnbResource = (LnbResource) it.next();
            boolean z2 = lnbResource.mIsInUse;
            i = lnbResource.mHandle;
            if (!z2) {
                break;
            }
            int updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority(lnbResource.mOwnerClientId);
            if (i2 > updateAndGetOwnerClientPriority) {
                z = clientProfile.mProcessId == getClientProfile(lnbResource.mOwnerClientId).mProcessId;
                i2 = updateAndGetOwnerClientPriority;
                i3 = i;
            }
        }
        if (i > -1) {
            iArr[0] = i;
            int i4 = tunerLnbRequest.clientId;
            LnbResource lnbResource2 = getLnbResource(i);
            ClientProfile clientProfile2 = getClientProfile(i4);
            lnbResource2.setOwner(i4);
            ((HashSet) clientProfile2.mUsingLnbHandles).add(Integer.valueOf(i));
            return true;
        }
        if (i3 <= -1 || ((clientProfile.getPriority() <= i2 && !(clientProfile.getPriority() == i2 && z)) || !reclaimResource(getLnbResource(i3).mOwnerClientId, 3))) {
            return false;
        }
        iArr[0] = i3;
        int i5 = tunerLnbRequest.clientId;
        LnbResource lnbResource3 = getLnbResource(i3);
        ClientProfile clientProfile3 = getClientProfile(i5);
        lnbResource3.setOwner(i5);
        ((HashSet) clientProfile3.mUsingLnbHandles).add(Integer.valueOf(i3));
        return true;
    }

    public void setDemuxInfoListInternal(TunerDemuxInfo[] tunerDemuxInfoArr) {
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "updateDemuxInfo:");
            for (TunerDemuxInfo tunerDemuxInfo : tunerDemuxInfoArr) {
                Slog.d("TunerResourceManagerService", tunerDemuxInfo.toString());
            }
        }
        HashSet hashSet = new HashSet(getDemuxResources().keySet());
        for (int i = 0; i < tunerDemuxInfoArr.length; i++) {
            if (getDemuxResource(tunerDemuxInfoArr[i].handle) != null) {
                if (DEBUG) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Demux handle="), tunerDemuxInfoArr[i].handle, "exists.", "TunerResourceManagerService");
                }
                hashSet.remove(Integer.valueOf(tunerDemuxInfoArr[i].handle));
            } else {
                TunerDemuxInfo tunerDemuxInfo2 = tunerDemuxInfoArr[i];
                DemuxResource.Builder builder = new DemuxResource.Builder(tunerDemuxInfo2.handle);
                builder.mFilterTypes = tunerDemuxInfo2.filterTypes;
                DemuxResource demuxResource = new DemuxResource(builder);
                ((HashMap) this.mDemuxResources).put(Integer.valueOf(demuxResource.mHandle), demuxResource);
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            DemuxResource demuxResource2 = getDemuxResource(num.intValue());
            if (demuxResource2 != null) {
                if (demuxResource2.mIsInUse) {
                    releaseDemuxInternal(demuxResource2);
                }
                ((HashMap) this.mDemuxResources).remove(num);
            }
        }
    }

    public void setFrontendInfoListInternal(TunerFrontendInfo[] tunerFrontendInfoArr) {
        int i;
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", "updateFrontendInfo:");
            for (TunerFrontendInfo tunerFrontendInfo : tunerFrontendInfoArr) {
                Slog.d("TunerResourceManagerService", tunerFrontendInfo.toString());
            }
        }
        HashSet hashSet = new HashSet(getFrontendResources().keySet());
        for (int i2 = 0; i2 < tunerFrontendInfoArr.length; i2++) {
            if (getFrontendResource(tunerFrontendInfoArr[i2].handle) != null) {
                if (DEBUG) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Frontend handle="), tunerFrontendInfoArr[i2].handle, "exists.", "TunerResourceManagerService");
                }
                hashSet.remove(Integer.valueOf(tunerFrontendInfoArr[i2].handle));
            } else {
                TunerFrontendInfo tunerFrontendInfo2 = tunerFrontendInfoArr[i2];
                FrontendResource.Builder builder = new FrontendResource.Builder(tunerFrontendInfo2.handle);
                builder.mType = tunerFrontendInfo2.type;
                builder.mExclusiveGroupId = tunerFrontendInfo2.exclusiveGroupId;
                FrontendResource frontendResource = new FrontendResource(builder);
                Iterator it = getFrontendResources().values().iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    i = frontendResource.mHandle;
                    if (!hasNext) {
                        break;
                    }
                    FrontendResource frontendResource2 = (FrontendResource) it.next();
                    if (frontendResource2.mExclusiveGroupId == frontendResource.mExclusiveGroupId) {
                        ((HashSet) frontendResource.mExclusiveGroupMemberHandles).add(Integer.valueOf(frontendResource2.mHandle));
                        frontendResource.mExclusiveGroupMemberHandles.addAll(frontendResource2.mExclusiveGroupMemberHandles);
                        Iterator it2 = ((HashSet) frontendResource2.mExclusiveGroupMemberHandles).iterator();
                        while (it2.hasNext()) {
                            ((HashSet) getFrontendResource(((Integer) it2.next()).intValue()).mExclusiveGroupMemberHandles).add(Integer.valueOf(i));
                        }
                        ((HashSet) frontendResource2.mExclusiveGroupMemberHandles).add(Integer.valueOf(i));
                    }
                }
                ((HashMap) this.mFrontendResources).put(Integer.valueOf(i), frontendResource);
                SparseIntArray sparseIntArray = this.mFrontendExistingNums;
                int i3 = frontendResource.mType;
                int i4 = sparseIntArray.get(i3, -1);
                if (i4 == -1) {
                    sparseIntArray.put(i3, 1);
                } else {
                    sparseIntArray.put(i3, i4 + 1);
                }
            }
        }
        Iterator it3 = hashSet.iterator();
        while (it3.hasNext()) {
            Integer num = (Integer) it3.next();
            FrontendResource frontendResource3 = getFrontendResource(num.intValue());
            if (frontendResource3 != null) {
                if (frontendResource3.mIsInUse) {
                    ClientProfile clientProfile = getClientProfile(frontendResource3.mOwnerClientId);
                    Iterator it4 = ((HashSet) clientProfile.mShareFeClientIds).iterator();
                    while (it4.hasNext()) {
                        clearFrontendAndClientMapping(getClientProfile(((Integer) it4.next()).intValue()));
                    }
                    clearFrontendAndClientMapping(clientProfile);
                }
                Iterator it5 = ((HashSet) frontendResource3.mExclusiveGroupMemberHandles).iterator();
                while (it5.hasNext()) {
                    ((HashSet) getFrontendResource(((Integer) it5.next()).intValue()).mExclusiveGroupMemberHandles).remove(Integer.valueOf(frontendResource3.mHandle));
                }
                SparseIntArray sparseIntArray2 = this.mFrontendExistingNums;
                int i5 = frontendResource3.mType;
                int i6 = sparseIntArray2.get(i5, -1);
                if (i6 != -1) {
                    sparseIntArray2.put(i5, i6 - 1);
                }
                ((HashMap) this.mFrontendResources).remove(num);
            }
        }
    }

    public void setLnbInfoListInternal(int[] iArr) {
        if (DEBUG) {
            for (int i : iArr) {
                Slog.d("TunerResourceManagerService", "updateLnbInfo(lnbHanle=" + i + ")");
            }
        }
        HashSet hashSet = new HashSet(getLnbResources().keySet());
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (getLnbResource(iArr[i2]) != null) {
                if (DEBUG) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Lnb handle="), iArr[i2], "exists.", "TunerResourceManagerService");
                }
                hashSet.remove(Integer.valueOf(iArr[i2]));
            } else {
                LnbResource lnbResource = new LnbResource(new LnbResource.Builder(iArr[i2]));
                ((HashMap) this.mLnbResources).put(Integer.valueOf(lnbResource.mHandle), lnbResource);
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            LnbResource lnbResource2 = getLnbResource(num.intValue());
            if (lnbResource2 != null) {
                if (lnbResource2.mIsInUse) {
                    releaseLnbInternal(lnbResource2);
                }
                ((HashMap) this.mLnbResources).remove(num);
            }
        }
    }

    public void shareFrontendInternal(int i, int i2) {
        if (DEBUG) {
            ASKSManagerService$$ExternalSyntheticOutline0.m(i, i2, "shareFrontend from ", " with ", "TunerResourceManagerService");
        }
        Integer num = getClientProfile(i).mShareeFeClientId;
        if (num.intValue() != -1) {
            ((HashSet) getClientProfile(num.intValue()).mShareFeClientIds).remove(Integer.valueOf(i));
            ClientProfile clientProfile = getClientProfile(i);
            ((HashSet) clientProfile.mUsingFrontendHandles).clear();
            ((HashSet) clientProfile.mShareFeClientIds).clear();
            clientProfile.mShareeFeClientId = -1;
            clientProfile.mPrimaryUsingFrontendHandle = -1;
        }
        Iterator it = ((HashSet) getClientProfile(i2).mUsingFrontendHandles).iterator();
        while (it.hasNext()) {
            Integer num2 = (Integer) it.next();
            num2.getClass();
            ((HashSet) getClientProfile(i).mUsingFrontendHandles).add(num2);
        }
        getClientProfile(i).mShareeFeClientId = Integer.valueOf(i2);
        ((HashSet) getClientProfile(i2).mShareFeClientIds).add(Integer.valueOf(i));
    }

    public boolean transferOwnerInternal(int i, int i2, int i3) {
        if (i == 0) {
            ClientProfile clientProfile = getClientProfile(i2);
            ClientProfile clientProfile2 = getClientProfile(i3);
            ((HashSet) clientProfile2.mShareFeClientIds).add(Integer.valueOf(i2));
            ((HashSet) clientProfile.mShareFeClientIds).remove(Integer.valueOf(i3));
            clientProfile2.mShareeFeClientId = -1;
            clientProfile.mShareeFeClientId = Integer.valueOf(i3);
            Iterator it = ((HashSet) clientProfile2.mUsingFrontendHandles).iterator();
            while (it.hasNext()) {
                getFrontendResource(((Integer) it.next()).intValue()).setOwner(i3);
            }
            clientProfile2.mPrimaryUsingFrontendHandle = clientProfile.mPrimaryUsingFrontendHandle;
            clientProfile.mPrimaryUsingFrontendHandle = -1;
            Iterator it2 = ((HashSet) clientProfile.mUsingFrontendHandles).iterator();
            while (it2.hasNext()) {
                int intValue = ((Integer) it2.next()).intValue();
                int i4 = getFrontendResource(intValue).mOwnerClientId;
                if (i4 != i3) {
                    VaultKeeperService$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(intValue, i4, "something is wrong in transferFeOwner:", ", ", ", "), i3, "TunerResourceManagerService");
                    return false;
                }
            }
            return true;
        }
        if (i != 3) {
            if (i != 5) {
                NandswapManager$$ExternalSyntheticOutline0.m(i, "transferOwnerInternal. unsupported resourceType: ", "TunerResourceManagerService");
                return false;
            }
            ClientProfile clientProfile3 = getClientProfile(i2);
            ClientProfile clientProfile4 = getClientProfile(i3);
            int i5 = clientProfile3.mUsingCiCamId;
            clientProfile4.mUsingCiCamId = i5;
            getCiCamResource(i5).setOwner(i3);
            clientProfile3.mUsingCiCamId = -1;
            return true;
        }
        ClientProfile clientProfile5 = getClientProfile(i2);
        ClientProfile clientProfile6 = getClientProfile(i3);
        HashSet hashSet = new HashSet();
        Iterator it3 = ((HashSet) clientProfile5.mUsingLnbHandles).iterator();
        while (it3.hasNext()) {
            Integer num = (Integer) it3.next();
            num.getClass();
            ((HashSet) clientProfile6.mUsingLnbHandles).add(num);
            getLnbResource(num.intValue()).setOwner(i3);
            hashSet.add(num);
        }
        Iterator it4 = hashSet.iterator();
        while (it4.hasNext()) {
            Integer num2 = (Integer) it4.next();
            num2.getClass();
            ((HashSet) clientProfile5.mUsingLnbHandles).remove(num2);
        }
        return true;
    }

    public void unregisterClientProfileInternal(int i) {
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "unregisterClientProfile(clientId=", ")", "TunerResourceManagerService");
        }
        removeClientProfile(i);
        IResourceManagerService iResourceManagerService = this.mMediaResourceManager;
        if (iResourceManagerService != null) {
            try {
                iResourceManagerService.overridePid(Binder.getCallingPid(), -1);
            } catch (RemoteException e) {
                Slog.e("TunerResourceManagerService", "Could not overridePid in resourceManagerSercice when unregister, remote exception: " + e);
            }
        }
    }

    public final int updateAndGetOwnerClientPriority(int i) {
        ClientProfile clientProfile = getClientProfile(i);
        clientPriorityUpdateOnRequest(clientProfile);
        return clientProfile.getPriority();
    }

    public void updateCasInfoInternal(int i, int i2) {
        if (DEBUG) {
            Slog.d("TunerResourceManagerService", DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "updateCasInfo(casSystemId=", ", maxSessionNum=", ")"));
        }
        if (i2 == 0) {
            CasResource casResource = getCasResource(i);
            if (casResource != null) {
                Iterator it = casResource.getOwnerClientIds().iterator();
                while (it.hasNext()) {
                    getClientProfile(((Integer) it.next()).intValue()).mUsingCasSystemId = -1;
                }
                ((HashMap) this.mCasResources).remove(Integer.valueOf(i));
            }
            CiCamResource ciCamResource = getCiCamResource(i);
            if (ciCamResource == null) {
                return;
            }
            Iterator it2 = ciCamResource.getOwnerClientIds().iterator();
            while (it2.hasNext()) {
                getClientProfile(((Integer) it2.next()).intValue()).mUsingCiCamId = -1;
            }
            ((HashMap) this.mCiCamResources).remove(Integer.valueOf(i));
            return;
        }
        CasResource casResource2 = getCasResource(i);
        CiCamResource ciCamResource2 = getCiCamResource(i);
        if (casResource2 != null) {
            casResource2.mAvailableSessionNum = Math.max(0, (i2 - casResource2.mMaxSessionNum) + casResource2.mAvailableSessionNum);
            casResource2.mMaxSessionNum = i2;
            if (ciCamResource2 != null) {
                ciCamResource2.mAvailableSessionNum = Math.max(0, (i2 - ciCamResource2.mMaxSessionNum) + ciCamResource2.mAvailableSessionNum);
                ciCamResource2.mMaxSessionNum = i2;
                return;
            }
            return;
        }
        CasResource.Builder builder = new CasResource.Builder(i);
        builder.mMaxSessionNum = i2;
        CasResource casResource3 = new CasResource(builder);
        CiCamResource.Builder builder2 = new CiCamResource.Builder(i);
        builder2.mMaxSessionNum = i2;
        CiCamResource ciCamResource3 = new CiCamResource(builder2);
        ((HashMap) this.mCasResources).put(Integer.valueOf(casResource3.mSystemId), casResource3);
        ((HashMap) this.mCiCamResources).put(Integer.valueOf(ciCamResource3.mSystemId), ciCamResource3);
    }

    public boolean updateClientPriorityInternal(int i, int i2, int i3) {
        if (DEBUG) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "updateClientPriority(clientId=", ", priority=", ", niceValue="), i3, ")", "TunerResourceManagerService");
        }
        ClientProfile clientProfile = getClientProfile(i);
        if (clientProfile == null) {
            FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i, "Can not find client profile with id ", " when trying to update the client priority.", "TunerResourceManagerService");
            return false;
        }
        if (i2 >= 0) {
            clientProfile.mIsPriorityOverwritten = true;
            clientProfile.mPriority = i2;
        }
        clientProfile.mNiceValue = i3;
        return true;
    }

    public final void updateFrontendClientMappingOnNewGrant(int i, int i2) {
        FrontendResource frontendResource = getFrontendResource(i);
        ClientProfile clientProfile = getClientProfile(i2);
        frontendResource.setOwner(i2);
        SparseIntArray sparseIntArray = this.mFrontendUsedNums;
        int i3 = frontendResource.mType;
        int i4 = sparseIntArray.get(i3, -1);
        if (i4 == -1) {
            sparseIntArray.put(i3, 1);
        } else {
            sparseIntArray.put(i3, i4 + 1);
        }
        ((HashSet) clientProfile.mUsingFrontendHandles).add(Integer.valueOf(i));
        Iterator it = ((HashSet) frontendResource.mExclusiveGroupMemberHandles).iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            getFrontendResource(num.intValue()).setOwner(i2);
            ((HashSet) clientProfile.mUsingFrontendHandles).add(num);
        }
        clientProfile.mPrimaryUsingFrontendHandle = i;
    }
}
