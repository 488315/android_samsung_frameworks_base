package android.media.tv.tuner;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaMetrics;
import android.media.tv.tuner.dvr.DvrPlayback;
import android.media.tv.tuner.dvr.DvrRecorder;
import android.media.tv.tuner.dvr.OnPlaybackStatusChangedListener;
import android.media.tv.tuner.dvr.OnRecordStatusChangedListener;
import android.media.tv.tuner.filter.Filter;
import android.media.tv.tuner.filter.FilterCallback;
import android.media.tv.tuner.filter.SharedFilter;
import android.media.tv.tuner.filter.SharedFilterCallback;
import android.media.tv.tuner.filter.TimeFilter;
import android.media.tv.tuner.frontend.Atsc3PlpInfo;
import android.media.tv.tuner.frontend.FrontendInfo;
import android.media.tv.tuner.frontend.FrontendSettings;
import android.media.tv.tuner.frontend.FrontendStatus;
import android.media.tv.tuner.frontend.FrontendStatusReadiness;
import android.media.tv.tuner.frontend.OnTuneEventListener;
import android.media.tv.tuner.frontend.ScanCallback;
import android.media.tv.tunerresourcemanager.ResourceClientProfile;
import android.media.tv.tunerresourcemanager.TunerCiCamRequest;
import android.media.tv.tunerresourcemanager.TunerDemuxRequest;
import android.media.tv.tunerresourcemanager.TunerDescramblerRequest;
import android.media.tv.tunerresourcemanager.TunerFrontendRequest;
import android.media.tv.tunerresourcemanager.TunerLnbRequest;
import android.media.tv.tunerresourcemanager.TunerResourceManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntFunction;
import java.util.function.Predicate;

@SystemApi
/* loaded from: classes3.dex */
public class Tuner implements AutoCloseable {
    public static final int DVR_TYPE_PLAYBACK = 1;
    public static final int DVR_TYPE_RECORD = 0;
    private static final int FILTER_CLEANUP_THRESHOLD = 256;
    public static final int INVALID_AV_SYNC_ID = -1;
    public static final int INVALID_FILTER_ID = -1;
    public static final long INVALID_FILTER_ID_LONG = -1;
    public static final int INVALID_FIRST_MACROBLOCK_IN_SLICE = -1;
    public static final int INVALID_FRONTEND_ID = -1;
    public static final int INVALID_FRONTEND_SETTING_FREQUENCY = -1;
    public static final int INVALID_LNB_ID = -1;
    public static final int INVALID_LTS_ID = -1;
    public static final int INVALID_MMTP_RECORD_EVENT_MPT_SEQUENCE_NUM = -1;
    public static final int INVALID_STREAM_ID = 65535;
    public static final long INVALID_TIMESTAMP = -1;
    public static final int INVALID_TS_PID = 65535;
    private static final int MSG_ON_FILTER_EVENT = 2;
    private static final int MSG_ON_FILTER_STATUS = 3;
    private static final int MSG_ON_LNB_EVENT = 4;
    private static final int MSG_RESOURCE_LOST = 1;
    public static final int RESULT_INVALID_ARGUMENT = 4;
    public static final int RESULT_INVALID_STATE = 3;
    public static final int RESULT_NOT_INITIALIZED = 2;
    public static final int RESULT_OUT_OF_MEMORY = 5;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_UNAVAILABLE = 1;
    public static final int RESULT_UNKNOWN_ERROR = 6;
    public static final int SCAN_TYPE_AUTO = 1;
    public static final int SCAN_TYPE_BLIND = 2;
    public static final int SCAN_TYPE_UNDEFINED = 0;
    private static int sTunerVersion;
    private final int mClientId;
    private final Context mContext;
    private Long mDemuxHandle;
    private Frontend mFrontend;
    private Long mFrontendCiCamHandle;
    private Integer mFrontendCiCamId;
    private Long mFrontendHandle;
    private FrontendInfo mFrontendInfo;
    private EventHandler mHandler;
    private Lnb mLnb;
    private Long mLnbHandle;
    private long mNativeContext;
    private OnResourceLostListener mOnResourceLostListener;
    private Executor mOnResourceLostListenerExecutor;
    private Executor mOnTuneEventExecutor;
    private OnTuneEventListener mOnTuneEventListener;
    private int mRequestedCiCamId;
    private final TunerResourceManager.ResourcesReclaimListener mResourceListener;
    private ScanCallback mScanCallback;
    private Executor mScanCallbackExecutor;
    private final TunerResourceManager mTunerResourceManager;
    private int mUserId;
    public static final byte[] VOID_KEYTOKEN = {0};
    private static final String TAG = "MediaTvTuner";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private DemuxInfo mDesiredDemuxInfo = new DemuxInfo(0);
    private boolean mClosed = false;
    private Tuner mFeOwnerTuner = null;
    private int mFrontendType = 0;
    private Integer mDesiredFrontendId = null;
    private final Object mOnTuneEventLock = new Object();
    private final Object mScanCallbackLock = new Object();
    private final Object mOnResourceLostListenerLock = new Object();
    private final ReentrantLock mFrontendLock = new ReentrantLock();
    private final ReentrantLock mLnbLock = new ReentrantLock();
    private final ReentrantLock mFrontendCiCamLock = new ReentrantLock();
    private final ReentrantLock mDemuxLock = new ReentrantLock();
    private Map<Long, WeakReference<Descrambler>> mDescramblers = new HashMap();
    private List<WeakReference<Filter>> mFilters = new ArrayList();

    @Retention(RetentionPolicy.SOURCE)
    public @interface DvrType {
    }

    public interface OnResourceLostListener {
        void onResourceLost(Tuner tuner);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Result {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScanType {
    }

    private native int nativeClose();

    private native int nativeCloseDemux(long j);

    private native int nativeCloseFrontend(long j);

    private native int nativeConnectCiCam(int i);

    private native int nativeDisconnectCiCam();

    private native Integer nativeGetAvSyncHwId(Filter filter);

    private native Long nativeGetAvSyncTime(int i);

    private native DemuxCapabilities nativeGetDemuxCapabilities();

    private native DemuxInfo nativeGetDemuxInfo(long j);

    private native String nativeGetFrontendHardwareInfo();

    private native List<Integer> nativeGetFrontendIds();

    private native FrontendInfo nativeGetFrontendInfo(int i);

    private native FrontendStatus nativeGetFrontendStatus(int[] iArr);

    private native FrontendStatusReadiness[] nativeGetFrontendStatusReadiness(int[] iArr);

    private native int nativeGetMaxNumberOfFrontends(int i);

    private native int nativeGetTunerVersion();

    private static native void nativeInit();

    private native boolean nativeIsLnaSupported();

    private native int nativeLinkCiCam(int i);

    private native int nativeOpenDemuxByhandle(long j);

    private native Descrambler nativeOpenDescramblerByHandle(long j);

    private native DvrPlayback nativeOpenDvrPlayback(long j);

    private native DvrRecorder nativeOpenDvrRecorder(long j);

    private native Filter nativeOpenFilter(int i, int i2, long j);

    private native Frontend nativeOpenFrontendByHandle(long j);

    private native Lnb nativeOpenLnbByHandle(long j);

    private native Lnb nativeOpenLnbByName(String str);

    private static native SharedFilter nativeOpenSharedFilter(String str);

    private native TimeFilter nativeOpenTimeFilter();

    private native void nativeRegisterFeCbListener(long j);

    private native int nativeRemoveOutputPid(int i);

    private native int nativeScan(int i, FrontendSettings frontendSettings, int i2);

    private native int nativeSetLna(boolean z);

    private native int nativeSetLnb(Lnb lnb);

    private native int nativeSetMaxNumberOfFrontends(int i, int i2);

    private native void nativeSetup();

    private native int nativeShareFrontend(int i);

    private native int nativeStopScan();

    private native int nativeStopTune();

    private native int nativeTune(int i, FrontendSettings frontendSettings);

    private native int nativeUnlinkCiCam(int i);

    private native void nativeUnregisterFeCbListener(long j);

    private native int nativeUnshareFrontend();

    private native void nativeUpdateFrontend(long j);

    static {
        try {
            System.loadLibrary("media_tv_tuner");
            nativeInit();
            Class.forName(MediaCodec.class.getName());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "MediaCodec class not found!", e);
        } catch (UnsatisfiedLinkError unused) {
            Log.d(TAG, "tuner JNI library not found!");
        }
        sTunerVersion = 0;
    }

    public Tuner(Context context, String str, int i) {
        TunerResourceManager.ResourcesReclaimListener resourcesReclaimListener = new TunerResourceManager.ResourcesReclaimListener() { // from class: android.media.tv.tuner.Tuner.1
            @Override // android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener
            public void onReclaimResources() {
                if (Tuner.this.mFrontend != null) {
                    FrameworkStatsLog.write(276, Tuner.this.mUserId, 0);
                }
                Tuner.this.releaseAll();
                Tuner.this.mHandler.sendMessage(Tuner.this.mHandler.obtainMessage(1));
            }
        };
        this.mResourceListener = resourcesReclaimListener;
        this.mContext = context;
        TunerResourceManager tunerResourceManager = (TunerResourceManager) context.getSystemService(TunerResourceManager.class);
        this.mTunerResourceManager = tunerResourceManager;
        if (tunerResourceManager == null) {
            throw new IllegalStateException("Tuner instance is created, but the device doesn't have tuner feature");
        }
        nativeSetup();
        int iNativeGetTunerVersion = nativeGetTunerVersion();
        sTunerVersion = iNativeGetTunerVersion;
        if (iNativeGetTunerVersion == 0) {
            Log.e(TAG, "Unknown Tuner version!");
        } else {
            Log.d(TAG, "Current Tuner version is " + TunerVersionChecker.getMajorVersion(sTunerVersion) + MediaMetrics.SEPARATOR + TunerVersionChecker.getMinorVersion(sTunerVersion) + MediaMetrics.SEPARATOR);
        }
        if (this.mHandler == null) {
            this.mHandler = createEventHandler();
        }
        int[] iArr = new int[1];
        ResourceClientProfile resourceClientProfile = new ResourceClientProfile();
        resourceClientProfile.tvInputSessionId = str;
        resourceClientProfile.useCase = i;
        tunerResourceManager.registerClientProfile(resourceClientProfile, new PendingIntent$$ExternalSyntheticLambda0(), resourcesReclaimListener, iArr);
        this.mClientId = iArr[0];
        this.mUserId = Process.myUid();
    }

    private FrontendInfo[] getFrontendInfoListInternal() {
        List<Integer> frontendIds = getFrontendIds();
        if (frontendIds == null) {
            return null;
        }
        FrontendInfo[] frontendInfoArr = new FrontendInfo[frontendIds.size()];
        for (int i = 0; i < frontendIds.size(); i++) {
            int iIntValue = frontendIds.get(i).intValue();
            FrontendInfo frontendInfoById = getFrontendInfoById(iIntValue);
            if (frontendInfoById == null) {
                Log.e(TAG, "Failed to get a FrontendInfo on frontend id:" + iIntValue + "!");
            } else {
                frontendInfoArr[i] = frontendInfoById;
            }
        }
        return (FrontendInfo[]) Arrays.stream(frontendInfoArr).filter(new Predicate() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((FrontendInfo) obj);
            }
        }).toArray(new IntFunction() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda13
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return Tuner.lambda$getFrontendInfoListInternal$0(i2);
            }
        });
    }

    static /* synthetic */ FrontendInfo[] lambda$getFrontendInfoListInternal$0(int i) {
        return new FrontendInfo[i];
    }

    public static int getTunerVersion() {
        return sTunerVersion;
    }

    public List<Integer> getFrontendIds() {
        this.mFrontendLock.lock();
        try {
            return nativeGetFrontendIds();
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public void setResourceLostListener(Executor executor, OnResourceLostListener onResourceLostListener) {
        synchronized (this.mOnResourceLostListenerLock) {
            Objects.requireNonNull(executor, "OnResourceLostListener must not be null");
            Objects.requireNonNull(onResourceLostListener, "executor must not be null");
            this.mOnResourceLostListener = onResourceLostListener;
            this.mOnResourceLostListenerExecutor = executor;
        }
    }

    public void clearResourceLostListener() {
        synchronized (this.mOnResourceLostListenerLock) {
            this.mOnResourceLostListener = null;
            this.mOnResourceLostListenerExecutor = null;
        }
    }

    public void shareFrontendFromTuner(Tuner tuner) {
        acquireTRMSLock("shareFrontendFromTuner()");
        this.mFrontendLock.lock();
        try {
            Tuner tuner2 = this.mFeOwnerTuner;
            if (tuner2 != null) {
                tuner2.unregisterFrontendCallbackListener(this);
                this.mFeOwnerTuner = null;
                nativeUnshareFrontend();
            }
            this.mTunerResourceManager.shareFrontend(this.mClientId, tuner.mClientId);
            this.mFeOwnerTuner = tuner;
            tuner.registerFrontendCallbackListener(this);
            Tuner tuner3 = this.mFeOwnerTuner;
            this.mFrontendHandle = tuner3.mFrontendHandle;
            Frontend frontend = tuner3.mFrontend;
            this.mFrontend = frontend;
            nativeShareFrontend(frontend.mId);
        } finally {
            releaseTRMSLock();
            this.mFrontendLock.unlock();
        }
    }

    public int transferOwner(Tuner tuner) {
        acquireTRMSLock("transferOwner()");
        this.mFrontendLock.lock();
        this.mFrontendCiCamLock.lock();
        this.mLnbLock.lock();
        try {
            if (isFrontendOwner() && isNewOwnerQualifiedForTransfer(tuner)) {
                int iTransferFeOwner = transferFeOwner(tuner);
                if (iTransferFeOwner == 0 && (iTransferFeOwner = transferCiCamOwner(tuner)) == 0) {
                    int iTransferLnbOwner = transferLnbOwner(tuner);
                    if (iTransferLnbOwner != 0) {
                        return iTransferLnbOwner;
                    }
                    this.mFrontendLock.unlock();
                    this.mFrontendCiCamLock.unlock();
                    this.mLnbLock.unlock();
                    releaseTRMSLock();
                    return 0;
                }
                return iTransferFeOwner;
            }
            this.mFrontendLock.unlock();
            this.mFrontendCiCamLock.unlock();
            this.mLnbLock.unlock();
            releaseTRMSLock();
            return 3;
        } finally {
            this.mFrontendLock.unlock();
            this.mFrontendCiCamLock.unlock();
            this.mLnbLock.unlock();
            releaseTRMSLock();
        }
    }

    private void replicateFrontendSettings(Tuner tuner) {
        this.mFrontendLock.lock();
        try {
            if (tuner == null) {
                if (DEBUG) {
                    Log.d(TAG, "resetting Frontend params for " + this.mClientId);
                }
                this.mFrontend = null;
                this.mFrontendHandle = null;
                this.mFrontendInfo = null;
                this.mFrontendType = 0;
            } else {
                if (DEBUG) {
                    Log.d(TAG, "copying Frontend params from " + tuner.mClientId + " to " + this.mClientId);
                }
                this.mFrontend = tuner.mFrontend;
                this.mFrontendHandle = tuner.mFrontendHandle;
                this.mFrontendInfo = tuner.mFrontendInfo;
                this.mFrontendType = tuner.mFrontendType;
            }
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private void setFrontendOwner(Tuner tuner) {
        this.mFrontendLock.lock();
        try {
            this.mFeOwnerTuner = tuner;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private void replicateCiCamSettings(Tuner tuner) {
        this.mFrontendCiCamLock.lock();
        try {
            if (tuner == null) {
                if (DEBUG) {
                    Log.d(TAG, "resetting CiCamParams: " + this.mClientId);
                }
                this.mFrontendCiCamHandle = null;
                this.mFrontendCiCamId = null;
            } else {
                if (DEBUG) {
                    Log.d(TAG, "copying CiCamParams from " + tuner.mClientId + " to " + this.mClientId);
                    Log.d(TAG, "mFrontendCiCamHandle:" + tuner.mFrontendCiCamHandle + ", mFrontendCiCamId:" + tuner.mFrontendCiCamId);
                }
                this.mFrontendCiCamHandle = tuner.mFrontendCiCamHandle;
                this.mFrontendCiCamId = tuner.mFrontendCiCamId;
            }
        } finally {
            this.mFrontendCiCamLock.unlock();
        }
    }

    private void replicateLnbSettings(Tuner tuner) {
        this.mLnbLock.lock();
        try {
            if (tuner == null) {
                if (DEBUG) {
                    Log.d(TAG, "resetting Lnb params");
                }
                this.mLnb = null;
                this.mLnbHandle = null;
            } else {
                if (DEBUG) {
                    Log.d(TAG, "copying Lnb params from " + tuner.mClientId + " to " + this.mClientId);
                }
                this.mLnb = tuner.mLnb;
                this.mLnbHandle = tuner.mLnbHandle;
            }
        } finally {
            this.mLnbLock.unlock();
        }
    }

    private boolean isFrontendOwner() {
        if (this.mFeOwnerTuner == null) {
            return true;
        }
        Log.e(TAG, "transferOwner() - cannot be called on the non-owner");
        return false;
    }

    private boolean isNewOwnerQualifiedForTransfer(Tuner tuner) {
        if (tuner.mFeOwnerTuner != this || !tuner.mFrontendHandle.equals(this.mFrontendHandle)) {
            Log.e(TAG, "transferOwner() - new owner must be the current sharee");
            return false;
        }
        if (tuner.mFrontendCiCamHandle == null && tuner.mLnb == null) {
            return true;
        }
        Log.e(TAG, "transferOwner() - new owner cannot be holding CiCam nor Lnb resource");
        return false;
    }

    private int transferFeOwner(Tuner tuner) {
        tuner.nativeUpdateFrontend(getNativeContext());
        nativeUpdateFrontend(0L);
        tuner.replicateFrontendSettings(this);
        setFrontendOwner(tuner);
        tuner.setFrontendOwner(null);
        return this.mTunerResourceManager.transferOwner(0, this.mClientId, tuner.mClientId) ? 0 : 6;
    }

    private int transferCiCamOwner(Tuner tuner) {
        if (this.mFrontendCiCamHandle == null) {
            return 0;
        }
        tuner.replicateCiCamSettings(this);
        replicateCiCamSettings(null);
        return this.mTunerResourceManager.transferOwner(5, this.mClientId, tuner.mClientId) ? 0 : 6;
    }

    private int transferLnbOwner(Tuner tuner) {
        Lnb lnb = this.mLnb;
        if (lnb == null) {
            return 0;
        }
        lnb.setOwner(tuner);
        tuner.replicateLnbSettings(this);
        replicateLnbSettings(null);
        return this.mTunerResourceManager.transferOwner(3, this.mClientId, tuner.mClientId) ? 0 : 6;
    }

    public void updateResourcePriority(int i, int i2) {
        this.mTunerResourceManager.updateClientPriority(this.mClientId, i, i2);
    }

    public void setResourceOwnershipRetention(boolean z) {
        this.mTunerResourceManager.setResourceOwnershipRetention(this.mClientId, z);
    }

    public boolean hasUnusedFrontend(int i) {
        return this.mTunerResourceManager.hasUnusedFrontend(i);
    }

    public boolean isLowestPriority(int i) {
        return this.mTunerResourceManager.isLowestPriority(this.mClientId, i);
    }

    private void registerFrontendCallbackListener(Tuner tuner) {
        nativeRegisterFeCbListener(tuner.getNativeContext());
    }

    private void unregisterFrontendCallbackListener(Tuner tuner) {
        nativeUnregisterFeCbListener(tuner.getNativeContext());
    }

    long getNativeContext() {
        return this.mNativeContext;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.mClosed) {
            return;
        }
        acquireTRMSLock("close()");
        try {
            releaseAll();
            this.mTunerResourceManager.unregisterClientProfile(this.mClientId);
            TunerUtils.throwExceptionForResult(nativeClose(), "failed to close tuner");
        } finally {
            releaseTRMSLock();
            this.mClosed = true;
        }
    }

    public void closeFrontend() {
        acquireTRMSLock("closeFrontend()");
        try {
            releaseFrontend();
        } finally {
            releaseTRMSLock();
        }
    }

    private void releaseFrontend() {
        boolean z = DEBUG;
        if (z) {
            Log.d(TAG, "Tuner#releaseFrontend");
        }
        this.mFrontendLock.lock();
        try {
            if (this.mFrontendHandle != null) {
                if (z) {
                    Log.d(TAG, "mFrontendHandle not null");
                }
                if (this.mFeOwnerTuner != null) {
                    if (z) {
                        Log.d(TAG, "mFeOwnerTuner not null - sharee");
                    }
                    this.mFeOwnerTuner.unregisterFrontendCallbackListener(this);
                    this.mFeOwnerTuner = null;
                    nativeUnshareFrontend();
                } else {
                    if (z) {
                        Log.d(TAG, "mFeOwnerTuner null - owner");
                    }
                    int iNativeCloseFrontend = nativeCloseFrontend(this.mFrontendHandle.longValue());
                    if (iNativeCloseFrontend != 0) {
                        TunerUtils.throwExceptionForResult(iNativeCloseFrontend, "failed to close frontend");
                    }
                }
                if (z) {
                    Log.d(TAG, "call TRM#releaseFrontend :" + this.mFrontendHandle + ", " + this.mClientId);
                }
                this.mTunerResourceManager.releaseFrontend(this.mFrontendHandle.longValue(), this.mClientId);
                FrameworkStatsLog.write(276, this.mUserId, 0);
                replicateFrontendSettings(null);
            }
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private void releaseCiCam() {
        this.mFrontendCiCamLock.lock();
        try {
            if (this.mFrontendCiCamHandle != null) {
                if (DEBUG) {
                    Log.d(TAG, "releasing CiCam : " + this.mFrontendCiCamHandle + " for " + this.mClientId);
                }
                nativeUnlinkCiCam(this.mFrontendCiCamId.intValue());
                this.mTunerResourceManager.releaseCiCam(this.mFrontendCiCamHandle.longValue(), this.mClientId);
                replicateCiCamSettings(null);
            } else if (DEBUG) {
                Log.d(TAG, "NOT releasing CiCam : " + this.mClientId);
            }
        } finally {
            this.mFrontendCiCamLock.unlock();
        }
    }

    private void closeLnb() {
        this.mLnbLock.lock();
        try {
            if (this.mLnb != null) {
                if (DEBUG) {
                    Log.d(TAG, "calling mLnb.close() : " + this.mClientId);
                }
                this.mLnb.closeInternal();
            } else if (DEBUG) {
                Log.d(TAG, "NOT calling mLnb.close() : " + this.mClientId);
            }
        } finally {
            this.mLnbLock.unlock();
        }
    }

    private void releaseFilters() {
        synchronized (this.mFilters) {
            if (!this.mFilters.isEmpty()) {
                Iterator<WeakReference<Filter>> it = this.mFilters.iterator();
                while (it.hasNext()) {
                    Filter filter = it.next().get();
                    if (filter != null) {
                        filter.close();
                    }
                }
                this.mFilters.clear();
            }
        }
    }

    private void releaseDescramblers() {
        synchronized (this.mDescramblers) {
            if (!this.mDescramblers.isEmpty()) {
                for (Map.Entry<Long, WeakReference<Descrambler>> entry : this.mDescramblers.entrySet()) {
                    Descrambler descrambler = entry.getValue().get();
                    if (descrambler != null) {
                        descrambler.close();
                    }
                    this.mTunerResourceManager.releaseDescrambler(entry.getKey().longValue(), this.mClientId);
                }
                this.mDescramblers.clear();
            }
        }
    }

    private void releaseDemux() {
        this.mDemuxLock.lock();
        try {
            Long l = this.mDemuxHandle;
            if (l != null) {
                int iNativeCloseDemux = nativeCloseDemux(l.longValue());
                if (iNativeCloseDemux != 0) {
                    TunerUtils.throwExceptionForResult(iNativeCloseDemux, "failed to close demux");
                }
                this.mTunerResourceManager.releaseDemux(this.mDemuxHandle.longValue(), this.mClientId);
                this.mDemuxHandle = null;
            }
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseAll() {
        releaseCiCam();
        releaseFrontend();
        closeLnb();
        releaseDescramblers();
        releaseFilters();
        releaseDemux();
    }

    private EventHandler createEventHandler() {
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            return new EventHandler(looperMyLooper);
        }
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper != null) {
            return new EventHandler(mainLooper);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EventHandler extends Handler {
        private EventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                synchronized (Tuner.this.mOnResourceLostListenerLock) {
                    if (Tuner.this.mOnResourceLostListener != null && Tuner.this.mOnResourceLostListenerExecutor != null) {
                        Tuner.this.mOnResourceLostListenerExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$EventHandler$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$0();
                            }
                        });
                    }
                }
                return;
            }
            if (i != 3) {
                return;
            }
            Filter filter = (Filter) message.obj;
            if (filter.getCallback() != null) {
                filter.getCallback().onFilterStatusChanged(filter, message.arg1);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0() {
            synchronized (Tuner.this.mOnResourceLostListenerLock) {
                if (Tuner.this.mOnResourceLostListener != null) {
                    Tuner.this.mOnResourceLostListener.onResourceLost(Tuner.this);
                }
            }
        }
    }

    private class Frontend {
        private int mId;

        private Frontend(Tuner tuner, int i) {
            this.mId = i;
        }
    }

    public void setOnTuneEventListener(Executor executor, OnTuneEventListener onTuneEventListener) {
        synchronized (this.mOnTuneEventLock) {
            this.mOnTuneEventListener = onTuneEventListener;
            this.mOnTuneEventExecutor = executor;
        }
    }

    public void clearOnTuneEventListener() {
        synchronized (this.mOnTuneEventLock) {
            this.mOnTuneEventListener = null;
            this.mOnTuneEventExecutor = null;
        }
    }

    public int tune(FrontendSettings frontendSettings) {
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
            } else {
                int type = frontendSettings.getType();
                if (this.mFrontendHandle != null && type != this.mFrontendType) {
                    Log.e(TAG, "Frontend was opened with type " + this.mFrontendType + ", new type is " + type);
                } else {
                    Log.d(TAG, "Tune to " + frontendSettings.getFrequencyLong());
                    this.mFrontendType = type;
                    if ((type != 10 || TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "Tuner with DTMB Frontend")) && ((this.mFrontendType != 11 || TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Tuner with IPTV Frontend")) && checkResource(0, this.mFrontendLock))) {
                        this.mFrontendInfo = null;
                        Log.d(TAG, "Write Stats Log for tuning.");
                        FrameworkStatsLog.write(276, this.mUserId, 1);
                        return nativeTune(frontendSettings.getType(), frontendSettings);
                    }
                    return 1;
                }
            }
            return 3;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int cancelTuning() {
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
                this.mFrontendLock.unlock();
                return 3;
            }
            return nativeStopTune();
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int scan(FrontendSettings frontendSettings, int i, Executor executor, ScanCallback scanCallback) {
        Executor executor2;
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
                this.mFrontendLock.unlock();
                return 3;
            }
            synchronized (this.mScanCallbackLock) {
                ScanCallback scanCallback2 = this.mScanCallback;
                if ((scanCallback2 != null && scanCallback2 != scanCallback) || ((executor2 = this.mScanCallbackExecutor) != null && executor2 != executor)) {
                    throw new IllegalStateException("Different Scan session already in progress.  stopScan must be called before a new scan session can be started.");
                }
                int type = frontendSettings.getType();
                this.mFrontendType = type;
                if (type != 10 || TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "Scan with DTMB Frontend")) {
                    if (this.mFrontendType != 11 || TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Tuner with IPTV Frontend")) {
                        if (checkResource(0, this.mFrontendLock)) {
                            this.mScanCallback = scanCallback;
                            this.mScanCallbackExecutor = executor;
                            this.mFrontendInfo = null;
                            FrameworkStatsLog.write(276, this.mUserId, 5);
                            return nativeScan(frontendSettings.getType(), frontendSettings, i);
                        }
                    }
                }
                return 1;
            }
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int cancelScanning() {
        int iNativeStopScan;
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
                this.mFrontendLock.unlock();
                return 3;
            }
            synchronized (this.mScanCallbackLock) {
                FrameworkStatsLog.write(276, this.mUserId, 6);
                iNativeStopScan = nativeStopScan();
                this.mScanCallback = null;
                this.mScanCallbackExecutor = null;
            }
            return iNativeStopScan;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private boolean requestFrontend() {
        Lnb lnb;
        long[] jArr = new long[1];
        try {
            TunerFrontendRequest tunerFrontendRequest = new TunerFrontendRequest();
            tunerFrontendRequest.clientId = this.mClientId;
            tunerFrontendRequest.frontendType = this.mFrontendType;
            Integer num = this.mDesiredFrontendId;
            tunerFrontendRequest.desiredId = num == null ? -1 : num.intValue();
            boolean zRequestFrontend = this.mTunerResourceManager.requestFrontend(tunerFrontendRequest, jArr);
            if (zRequestFrontend) {
                Long lValueOf = Long.valueOf(jArr[0]);
                this.mFrontendHandle = lValueOf;
                this.mFrontend = nativeOpenFrontendByHandle(lValueOf.longValue());
            }
            int i = this.mFrontendType;
            if (i != 5 && i != 7 && i != 8) {
                return zRequestFrontend;
            }
            this.mLnbLock.lock();
            try {
                if (this.mLnbHandle != null && (lnb = this.mLnb) != null) {
                    nativeSetLnb(lnb);
                }
                return zRequestFrontend;
            } finally {
                this.mLnbLock.unlock();
            }
        } finally {
            this.mDesiredFrontendId = null;
        }
    }

    private int setLnb(Lnb lnb) {
        this.mLnbLock.lock();
        try {
            return nativeSetLnb(lnb);
        } finally {
            this.mLnbLock.unlock();
        }
    }

    public boolean isLnaSupported() {
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "isLnaSupported")) {
            throw new UnsupportedOperationException("Tuner HAL version " + TunerVersionChecker.getTunerVersion() + " doesn't support this method.");
        }
        return nativeIsLnaSupported();
    }

    public int setLnaEnabled(boolean z) {
        return nativeSetLna(z);
    }

    public FrontendStatus getFrontendStatus(int[] iArr) {
        this.mFrontendLock.lock();
        try {
            if (this.mFrontend == null) {
                throw new IllegalStateException("frontend is not initialized");
            }
            if (this.mFeOwnerTuner != null) {
                throw new IllegalStateException("Operation cannot be done by sharee of tuner");
            }
            return nativeGetFrontendStatus(iArr);
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int getAvSyncHwId(Filter filter) {
        Integer numNativeGetAvSyncHwId;
        this.mDemuxLock.lock();
        try {
            int iIntValue = -1;
            if (checkResource(1, this.mDemuxLock) && (numNativeGetAvSyncHwId = nativeGetAvSyncHwId(filter)) != null) {
                iIntValue = numNativeGetAvSyncHwId.intValue();
            }
            return iIntValue;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public long getAvSyncTime(int i) {
        Long lNativeGetAvSyncTime;
        this.mDemuxLock.lock();
        try {
            long jLongValue = -1;
            if (checkResource(1, this.mDemuxLock) && (lNativeGetAvSyncTime = nativeGetAvSyncTime(i)) != null) {
                jLongValue = lNativeGetAvSyncTime.longValue();
            }
            return jLongValue;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int connectCiCam(int i) {
        this.mDemuxLock.lock();
        try {
            if (checkResource(1, this.mDemuxLock)) {
                return nativeConnectCiCam(i);
            }
            return 1;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int connectFrontendToCiCam(int i) {
        acquireTRMSLock("connectFrontendToCiCam()");
        this.mFrontendCiCamLock.lock();
        this.mFrontendLock.lock();
        try {
            if (this.mFrontendHandle == null) {
                Log.d(TAG, "Operation cannot be done without frontend");
            } else {
                if (this.mFeOwnerTuner == null) {
                    if (TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "linkFrontendToCiCam")) {
                        this.mRequestedCiCamId = i;
                        if (checkResource(5, null) && checkResource(0, null)) {
                            return nativeLinkCiCam(i);
                        }
                    }
                    releaseTRMSLock();
                    this.mFrontendCiCamLock.unlock();
                    this.mFrontendLock.unlock();
                    return -1;
                }
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
            }
            return 3;
        } finally {
            releaseTRMSLock();
            this.mFrontendCiCamLock.unlock();
            this.mFrontendLock.unlock();
        }
    }

    public int disconnectCiCam() {
        this.mDemuxLock.lock();
        try {
            if (this.mDemuxHandle != null) {
                return nativeDisconnectCiCam();
            }
            this.mDemuxLock.unlock();
            return 1;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int disconnectFrontendToCiCam(int i) {
        Integer num;
        acquireTRMSLock("disconnectFrontendToCiCam()");
        try {
            if (this.mFrontendHandle == null) {
                Log.d(TAG, "Operation cannot be done without frontend");
                return 3;
            }
            if (this.mFeOwnerTuner != null) {
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
                if (this.mFrontendCiCamLock.isLocked()) {
                    this.mFrontendCiCamLock.unlock();
                }
                releaseTRMSLock();
                return 3;
            }
            if (TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "unlinkFrontendToCiCam")) {
                this.mFrontendCiCamLock.lock();
                if (this.mFrontendCiCamHandle != null && (num = this.mFrontendCiCamId) != null && num.intValue() == i) {
                    int iNativeUnlinkCiCam = nativeUnlinkCiCam(i);
                    this.mTunerResourceManager.releaseCiCam(this.mFrontendCiCamHandle.longValue(), this.mClientId);
                    this.mFrontendCiCamId = null;
                    this.mFrontendCiCamHandle = null;
                    if (this.mFrontendCiCamLock.isLocked()) {
                        this.mFrontendCiCamLock.unlock();
                    }
                    releaseTRMSLock();
                    return iNativeUnlinkCiCam;
                }
            }
            if (this.mFrontendCiCamLock.isLocked()) {
                this.mFrontendCiCamLock.unlock();
            }
            releaseTRMSLock();
            return 1;
        } finally {
            if (this.mFrontendCiCamLock.isLocked()) {
                this.mFrontendCiCamLock.unlock();
            }
            releaseTRMSLock();
        }
    }

    public int removeOutputPid(int i) {
        this.mFrontendLock.lock();
        try {
            if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Remove output PID")) {
                this.mFrontendLock.unlock();
                return 1;
            }
            if (this.mFrontend == null) {
                throw new IllegalStateException("frontend is not initialized");
            }
            if (this.mFeOwnerTuner == null) {
                return nativeRemoveOutputPid(i);
            }
            Log.d(TAG, "Operation cannot be done by sharee of tuner");
            this.mFrontendLock.unlock();
            return 3;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public List<FrontendStatusReadiness> getFrontendStatusReadiness(int[] iArr) {
        List<FrontendStatusReadiness> listAsList;
        this.mFrontendLock.lock();
        try {
            if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Get fronted status readiness")) {
                listAsList = Collections.EMPTY_LIST;
            } else {
                if (this.mFrontend == null) {
                    throw new IllegalStateException("frontend is not initialized");
                }
                if (this.mFeOwnerTuner != null) {
                    throw new IllegalStateException("Operation cannot be done by sharee of tuner");
                }
                FrontendStatusReadiness[] frontendStatusReadinessArrNativeGetFrontendStatusReadiness = nativeGetFrontendStatusReadiness(iArr);
                if (frontendStatusReadinessArrNativeGetFrontendStatusReadiness == null) {
                    listAsList = Collections.EMPTY_LIST;
                } else {
                    listAsList = Arrays.asList(frontendStatusReadinessArrNativeGetFrontendStatusReadiness);
                }
            }
            return listAsList;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public FrontendInfo getFrontendInfo() {
        this.mFrontendLock.lock();
        try {
            if (checkResource(0, this.mFrontendLock)) {
                Frontend frontend = this.mFrontend;
                if (frontend == null) {
                    throw new IllegalStateException("frontend is not initialized");
                }
                if (this.mFrontendInfo == null) {
                    this.mFrontendInfo = getFrontendInfoById(frontend.mId);
                }
                return this.mFrontendInfo;
            }
            this.mFrontendLock.unlock();
            return null;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public List<FrontendInfo> getAvailableFrontendInfos() {
        FrontendInfo[] frontendInfoListInternal = getFrontendInfoListInternal();
        if (frontendInfoListInternal == null) {
            return null;
        }
        return Arrays.asList(frontendInfoListInternal);
    }

    public String getCurrentFrontendHardwareInfo() {
        this.mFrontendLock.lock();
        try {
            if (TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Get Frontend hardware info")) {
                if (this.mFrontend == null) {
                    throw new IllegalStateException("frontend is not initialized");
                }
                if (this.mFeOwnerTuner != null) {
                    throw new IllegalStateException("Operation cannot be done by sharee of tuner");
                }
                return nativeGetFrontendHardwareInfo();
            }
            this.mFrontendLock.unlock();
            return null;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int setMaxNumberOfFrontends(int i, int i2) {
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Set maximum Frontends")) {
            return 1;
        }
        if (i2 < 0) {
            return 4;
        }
        if (this.mFeOwnerTuner != null) {
            Log.d(TAG, "Operation cannot be done by sharee of tuner");
            return 3;
        }
        int iNativeSetMaxNumberOfFrontends = nativeSetMaxNumberOfFrontends(i, i2);
        if (iNativeSetMaxNumberOfFrontends != 0 || this.mTunerResourceManager.setMaxNumberOfFrontends(i, i2)) {
            return iNativeSetMaxNumberOfFrontends;
        }
        return 4;
    }

    public int getMaxNumberOfFrontends(int i) {
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Set maximum Frontends")) {
            return -1;
        }
        int iNativeGetMaxNumberOfFrontends = nativeGetMaxNumberOfFrontends(i);
        int maxNumberOfFrontends = this.mTunerResourceManager.getMaxNumberOfFrontends(i);
        if (iNativeGetMaxNumberOfFrontends != maxNumberOfFrontends) {
            Log.w(TAG, "max num of usable frontend is out-of-sync b/w " + iNativeGetMaxNumberOfFrontends + " != " + maxNumberOfFrontends);
        }
        return iNativeGetMaxNumberOfFrontends;
    }

    public FrontendInfo getFrontendInfoById(int i) {
        this.mFrontendLock.lock();
        try {
            return nativeGetFrontendInfo(i);
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public DemuxCapabilities getDemuxCapabilities() {
        this.mDemuxLock.lock();
        try {
            return nativeGetDemuxCapabilities();
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public DemuxInfo getCurrentDemuxInfo() {
        this.mDemuxLock.lock();
        try {
            Long l = this.mDemuxHandle;
            if (l != null) {
                return nativeGetDemuxInfo(l.longValue());
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public DemuxInfo getDesiredDemuxInfo() {
        return this.mDesiredDemuxInfo;
    }

    private void onFrontendEvent(final int i) {
        Log.d(TAG, "Got event from tuning. Event type: " + i + " for " + this);
        synchronized (this.mOnTuneEventLock) {
            Executor executor = this.mOnTuneEventExecutor;
            if (executor != null && this.mOnTuneEventListener != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda21
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onFrontendEvent$1(i);
                    }
                });
            }
        }
        Log.d(TAG, "Wrote Stats Log for the events from tuning.");
        if (i == 0) {
            FrameworkStatsLog.write(276, this.mUserId, 2);
        } else if (i == 1) {
            FrameworkStatsLog.write(276, this.mUserId, 3);
        } else if (i == 2) {
            FrameworkStatsLog.write(276, this.mUserId, 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrontendEvent$1(int i) {
        synchronized (this.mOnTuneEventLock) {
            OnTuneEventListener onTuneEventListener = this.mOnTuneEventListener;
            if (onTuneEventListener != null) {
                onTuneEventListener.onTuneEvent(i);
            }
        }
    }

    private void onLocked() {
        Log.d(TAG, "Wrote Stats Log for locked event from scanning.");
        FrameworkStatsLog.write(276, this.mUserId, 2);
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onLocked$2();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLocked$2() {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onLocked();
            }
        }
    }

    private void onUnlocked() {
        Log.d(TAG, "Wrote Stats Log for unlocked event from scanning.");
        FrameworkStatsLog.write(276, this.mUserId, 2);
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onUnlocked$3();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUnlocked$3() {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onUnlocked();
            }
        }
    }

    private void onScanStopped() {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onScanStopped$4();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onScanStopped$4() {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onScanStopped();
            }
        }
    }

    private void onProgress(final int i) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onProgress$5(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProgress$5(int i) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onProgress(i);
            }
        }
    }

    private void onFrequenciesReport(final long[] jArr) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onFrequenciesReport$6(jArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrequenciesReport$6(long[] jArr) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onFrequenciesLongReported(jArr);
            }
        }
    }

    private void onSymbolRates(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSymbolRates$7(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSymbolRates$7(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onSymbolRatesReported(iArr);
            }
        }
    }

    private void onHierarchy(final int i) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onHierarchy$8(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHierarchy$8(int i) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onHierarchyReported(i);
            }
        }
    }

    private void onSignalType(final int i) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSignalType$9(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSignalType$9(int i) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onSignalTypeReported(i);
            }
        }
    }

    private void onPlpIds(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onPlpIds$10(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPlpIds$10(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onPlpIdsReported(iArr);
            }
        }
    }

    private void onGroupIds(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onGroupIds$11(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGroupIds$11(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onGroupIdsReported(iArr);
            }
        }
    }

    private void onInputStreamIds(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onInputStreamIds$12(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInputStreamIds$12(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onInputStreamIdsReported(iArr);
            }
        }
    }

    private void onDvbsStandard(final int i) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDvbsStandard$13(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbsStandard$13(int i) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onDvbsStandardReported(i);
            }
        }
    }

    private void onDvbtStandard(final int i) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDvbtStandard$14(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbtStandard$14(int i) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onDvbtStandardReported(i);
            }
        }
    }

    private void onAnalogSifStandard(final int i) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onAnalogSifStandard$15(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAnalogSifStandard$15(int i) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onAnalogSifStandardReported(i);
            }
        }
    }

    private void onAtsc3PlpInfos(final Atsc3PlpInfo[] atsc3PlpInfoArr) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onAtsc3PlpInfos$16(atsc3PlpInfoArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAtsc3PlpInfos$16(Atsc3PlpInfo[] atsc3PlpInfoArr) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onAtsc3PlpInfosReported(atsc3PlpInfoArr);
            }
        }
    }

    private void onModulationReported(final int i) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onModulationReported$17(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onModulationReported$17(int i) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onModulationReported(i);
            }
        }
    }

    private void onPriorityReported(final boolean z) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onPriorityReported$18(z);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPriorityReported$18(boolean z) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onPriorityReported(z);
            }
        }
    }

    private void onDvbcAnnexReported(final int i) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDvbcAnnexReported$19(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbcAnnexReported$19(int i) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onDvbcAnnexReported(i);
            }
        }
    }

    private void onDvbtCellIdsReported(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            Executor executor = this.mScanCallbackExecutor;
            if (executor != null && this.mScanCallback != null) {
                executor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDvbtCellIdsReported$20(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbtCellIdsReported$20(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            ScanCallback scanCallback = this.mScanCallback;
            if (scanCallback != null) {
                scanCallback.onDvbtCellIdsReported(iArr);
            }
        }
    }

    public Filter openFilter(int i, int i2, long j, Executor executor, FilterCallback filterCallback) {
        this.mDemuxLock.lock();
        try {
            TunerVersionChecker.getMajorVersion(sTunerVersion);
            if (sTunerVersion >= 196608 && configureDemuxInternal(new DemuxInfo(i), false) != 0) {
                Log.e(TAG, "openFilter called for unsupported mainType: " + i);
            } else if (checkResource(1, this.mDemuxLock)) {
                Filter filterNativeOpenFilter = nativeOpenFilter(i, TunerUtils.getFilterSubtype(i, i2), j);
                if (filterNativeOpenFilter != null) {
                    filterNativeOpenFilter.setType(i, i2);
                    filterNativeOpenFilter.setCallback(filterCallback, executor);
                    if (this.mHandler == null) {
                        this.mHandler = createEventHandler();
                    }
                    synchronized (this.mFilters) {
                        this.mFilters.add(new WeakReference<>(filterNativeOpenFilter));
                        if (this.mFilters.size() > 256) {
                            Iterator<WeakReference<Filter>> it = this.mFilters.iterator();
                            while (it.hasNext()) {
                                if (it.next().get() == null) {
                                    it.remove();
                                }
                            }
                        }
                    }
                }
                return filterNativeOpenFilter;
            }
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public Lnb openLnb(Executor executor, LnbCallback lnbCallback) {
        Lnb lnb;
        Lnb lnb2;
        this.mLnbLock.lock();
        try {
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(lnbCallback, "LnbCallback must not be null");
            Lnb lnb3 = this.mLnb;
            if (lnb3 != null) {
                lnb3.setCallbackAndOwner(this, executor, lnbCallback);
                lnb2 = this.mLnb;
            } else if (checkResource(3, this.mLnbLock) && (lnb = this.mLnb) != null) {
                lnb.setCallbackAndOwner(this, executor, lnbCallback);
                if (this.mFrontendHandle != null && this.mFrontend != null) {
                    setLnb(this.mLnb);
                }
                lnb2 = this.mLnb;
            } else {
                this.mLnbLock.unlock();
                return null;
            }
            return lnb2;
        } finally {
            this.mLnbLock.unlock();
        }
    }

    public Lnb openLnbByName(String str, Executor executor, LnbCallback lnbCallback) {
        acquireTRMSLock("openLnbByName");
        this.mLnbLock.lock();
        try {
            Objects.requireNonNull(str, "LNB name must not be null");
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(lnbCallback, "LnbCallback must not be null");
            Lnb lnbNativeOpenLnbByName = nativeOpenLnbByName(str);
            if (lnbNativeOpenLnbByName != null) {
                Lnb lnb = this.mLnb;
                if (lnb != null) {
                    lnb.closeInternal();
                    this.mLnbHandle = null;
                }
                this.mLnb = lnbNativeOpenLnbByName;
                lnbNativeOpenLnbByName.setCallbackAndOwner(this, executor, lnbCallback);
                if (this.mFrontendHandle != null && this.mFrontend != null) {
                    setLnb(this.mLnb);
                }
            }
            return this.mLnb;
        } finally {
            releaseTRMSLock();
            this.mLnbLock.unlock();
        }
    }

    private boolean requestLnb() {
        long[] jArr = new long[1];
        TunerLnbRequest tunerLnbRequest = new TunerLnbRequest();
        tunerLnbRequest.clientId = this.mClientId;
        boolean zRequestLnb = this.mTunerResourceManager.requestLnb(tunerLnbRequest, jArr);
        if (zRequestLnb) {
            Long lValueOf = Long.valueOf(jArr[0]);
            this.mLnbHandle = lValueOf;
            this.mLnb = nativeOpenLnbByHandle(lValueOf.longValue());
        }
        return zRequestLnb;
    }

    public TimeFilter openTimeFilter() {
        this.mDemuxLock.lock();
        try {
            if (checkResource(1, this.mDemuxLock)) {
                return nativeOpenTimeFilter();
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public Descrambler openDescrambler() {
        acquireTRMSLock("openDescrambler()");
        this.mDemuxLock.lock();
        try {
            if (checkResource(1, null)) {
                return requestDescrambler();
            }
            return null;
        } finally {
            releaseTRMSLock();
            this.mDemuxLock.unlock();
        }
    }

    public DvrRecorder openDvrRecorder(long j, Executor executor, OnRecordStatusChangedListener onRecordStatusChangedListener) {
        this.mDemuxLock.lock();
        try {
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(onRecordStatusChangedListener, "OnRecordStatusChangedListener must not be null");
            if (checkResource(1, this.mDemuxLock)) {
                DvrRecorder dvrRecorderNativeOpenDvrRecorder = nativeOpenDvrRecorder(j);
                dvrRecorderNativeOpenDvrRecorder.setListener(executor, onRecordStatusChangedListener);
                return dvrRecorderNativeOpenDvrRecorder;
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public DvrPlayback openDvrPlayback(long j, Executor executor, OnPlaybackStatusChangedListener onPlaybackStatusChangedListener) {
        this.mDemuxLock.lock();
        try {
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(onPlaybackStatusChangedListener, "OnPlaybackStatusChangedListener must not be null");
            if (checkResource(1, this.mDemuxLock)) {
                DvrPlayback dvrPlaybackNativeOpenDvrPlayback = nativeOpenDvrPlayback(j);
                dvrPlaybackNativeOpenDvrPlayback.setListener(executor, onPlaybackStatusChangedListener);
                return dvrPlaybackNativeOpenDvrPlayback;
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int applyFrontend(FrontendInfo frontendInfo) {
        Objects.requireNonNull(frontendInfo, "desiredFrontendInfo must not be null");
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.e(TAG, "Operation connot be done by sharee of tuner");
            } else if (this.mFrontendHandle != null) {
                Log.e(TAG, "A frontend has been opened before");
            } else {
                this.mFrontendType = frontendInfo.getType();
                this.mDesiredFrontendId = Integer.valueOf(frontendInfo.getId());
                if (DEBUG) {
                    Log.d(TAG, "Applying frontend with type " + this.mFrontendType + ", id " + this.mDesiredFrontendId);
                }
                if (checkResource(0, this.mFrontendLock)) {
                    return 0;
                }
                this.mFrontendLock.unlock();
                return 1;
            }
            return 3;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int applyFrontendByType(int i) {
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.e(TAG, "Operation connot be done by sharee of tuner");
            } else if (this.mFrontendHandle != null) {
                Log.e(TAG, "A frontend has been opened before");
            } else {
                this.mDesiredFrontendId = null;
                this.mFrontendType = i;
                if (DEBUG) {
                    Log.d(TAG, "Applying frontend with type " + this.mFrontendType);
                }
                if (checkResource(0, this.mFrontendLock)) {
                    return 0;
                }
                this.mFrontendLock.unlock();
                return 1;
            }
            return 3;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public static SharedFilter openSharedFilter(Context context, String str, Executor executor, SharedFilterCallback sharedFilterCallback) {
        Objects.requireNonNull(str, "sharedFilterToken must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(sharedFilterCallback, "SharedFilterCallback must not be null");
        if (context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_TV_SHARED_FILTER) != 0) {
            throw new SecurityException("Caller must have ACCESS_TV_SHAREDFILTER permission.");
        }
        SharedFilter sharedFilterNativeOpenSharedFilter = nativeOpenSharedFilter(str);
        if (sharedFilterNativeOpenSharedFilter != null) {
            sharedFilterNativeOpenSharedFilter.setCallback(sharedFilterCallback, executor);
        }
        return sharedFilterNativeOpenSharedFilter;
    }

    public int configureDemux(DemuxInfo demuxInfo) {
        int iConfigureDemuxInternal;
        TunerVersionChecker.getMajorVersion(sTunerVersion);
        if (sTunerVersion < 196608) {
            Log.e(TAG, "configureDemux() is not supported for tuner version:" + TunerVersionChecker.getMajorVersion(sTunerVersion) + MediaMetrics.SEPARATOR + TunerVersionChecker.getMinorVersion(sTunerVersion) + MediaMetrics.SEPARATOR);
            return 1;
        }
        synchronized (this.mDemuxLock) {
            iConfigureDemuxInternal = configureDemuxInternal(demuxInfo, true);
        }
        return iConfigureDemuxInternal;
    }

    private int configureDemuxInternal(DemuxInfo demuxInfo, boolean z) {
        DemuxInfo demuxInfoNativeGetDemuxInfo;
        if (demuxInfo == null) {
            if (this.mDemuxHandle != null) {
                releaseFilters();
                releaseDemux();
            }
            return 0;
        }
        int filterTypes = demuxInfo.getFilterTypes();
        if ((this.mDesiredDemuxInfo.getFilterTypes() & filterTypes) == filterTypes) {
            if (z) {
                this.mDesiredDemuxInfo.setFilterTypes(filterTypes);
            }
            return 0;
        }
        DemuxCapabilities demuxCapabilitiesNativeGetDemuxCapabilities = nativeGetDemuxCapabilities();
        if (demuxCapabilitiesNativeGetDemuxCapabilities == null) {
            Log.e(TAG, "configureDemuxInternal:failed to get DemuxCapabilities");
            return 1;
        }
        int[] filterTypeCapabilityList = demuxCapabilitiesNativeGetDemuxCapabilities.getFilterTypeCapabilityList();
        if (filterTypeCapabilityList.length <= 0) {
            Log.e(TAG, "configureDemuxInternal: getFilterTypeCapabilityList() returned an empty array");
            return 1;
        }
        for (int i : filterTypeCapabilityList) {
            if ((i & filterTypes) == filterTypes) {
                Long l = this.mDemuxHandle;
                if (l != null && filterTypes != 0 && (demuxInfoNativeGetDemuxInfo = nativeGetDemuxInfo(l.longValue())) != null && (demuxInfoNativeGetDemuxInfo.getFilterTypes() & filterTypes) != filterTypes) {
                    releaseFilters();
                    releaseDemux();
                }
                this.mDesiredDemuxInfo.setFilterTypes(filterTypes);
                return 0;
            }
        }
        Log.e(TAG, "configureDemuxInternal: requested caps:" + filterTypes + " is not supported by the system");
        return 1;
    }

    private boolean requestDemux() {
        long[] jArr = new long[1];
        TunerDemuxRequest tunerDemuxRequest = new TunerDemuxRequest();
        tunerDemuxRequest.clientId = this.mClientId;
        tunerDemuxRequest.desiredFilterTypes = this.mDesiredDemuxInfo.getFilterTypes();
        boolean zRequestDemux = this.mTunerResourceManager.requestDemux(tunerDemuxRequest, jArr);
        if (zRequestDemux) {
            Long lValueOf = Long.valueOf(jArr[0]);
            this.mDemuxHandle = lValueOf;
            nativeOpenDemuxByhandle(lValueOf.longValue());
        }
        return zRequestDemux;
    }

    private Descrambler requestDescrambler() {
        long[] jArr = new long[1];
        TunerDescramblerRequest tunerDescramblerRequest = new TunerDescramblerRequest();
        tunerDescramblerRequest.clientId = this.mClientId;
        if (!this.mTunerResourceManager.requestDescrambler(tunerDescramblerRequest, jArr)) {
            return null;
        }
        long j = jArr[0];
        Descrambler descramblerNativeOpenDescramblerByHandle = nativeOpenDescramblerByHandle(j);
        if (descramblerNativeOpenDescramblerByHandle != null) {
            synchronized (this.mDescramblers) {
                this.mDescramblers.put(Long.valueOf(j), new WeakReference<>(descramblerNativeOpenDescramblerByHandle));
            }
            return descramblerNativeOpenDescramblerByHandle;
        }
        this.mTunerResourceManager.releaseDescrambler(j, this.mClientId);
        return descramblerNativeOpenDescramblerByHandle;
    }

    private boolean requestFrontendCiCam(int i) {
        long[] jArr = new long[1];
        TunerCiCamRequest tunerCiCamRequest = new TunerCiCamRequest();
        tunerCiCamRequest.clientId = this.mClientId;
        tunerCiCamRequest.ciCamId = i;
        boolean zRequestCiCam = this.mTunerResourceManager.requestCiCam(tunerCiCamRequest, jArr);
        if (zRequestCiCam) {
            this.mFrontendCiCamHandle = Long.valueOf(jArr[0]);
            this.mFrontendCiCamId = Integer.valueOf(i);
        }
        return zRequestCiCam;
    }

    private boolean checkResource(int i, ReentrantLock reentrantLock) {
        if (i != 0) {
            if (i != 1) {
                if (i != 3) {
                    if (i != 5) {
                        return false;
                    }
                    if (this.mFrontendCiCamHandle == null && !requestResource(i, reentrantLock)) {
                        return false;
                    }
                } else if (this.mLnb == null && !requestResource(i, reentrantLock)) {
                    return false;
                }
            } else if (this.mDemuxHandle == null && !requestResource(i, reentrantLock)) {
                return false;
            }
        } else if (this.mFrontendHandle == null && !requestResource(i, reentrantLock)) {
            return false;
        }
        return true;
    }

    private boolean requestResource(int i, ReentrantLock reentrantLock) {
        boolean z = reentrantLock != null;
        if (z) {
            if (!reentrantLock.isLocked()) {
                throw new IllegalStateException("local lock must be locked beforehand");
            }
            reentrantLock.unlock();
        }
        if (z) {
            acquireTRMSLock("requestResource:" + i);
        }
        if (z) {
            try {
                reentrantLock.lock();
            } finally {
                if (z) {
                    releaseTRMSLock();
                }
            }
        }
        if (i == 0) {
            boolean zRequestFrontend = requestFrontend();
            if (z) {
                releaseTRMSLock();
            }
            return zRequestFrontend;
        }
        if (i == 1) {
            boolean zRequestDemux = requestDemux();
            if (z) {
                releaseTRMSLock();
            }
            return zRequestDemux;
        }
        if (i == 3) {
            boolean zRequestLnb = requestLnb();
            if (z) {
                releaseTRMSLock();
            }
            return zRequestLnb;
        }
        if (i != 5) {
            return false;
        }
        boolean zRequestFrontendCiCam = requestFrontendCiCam(this.mRequestedCiCamId);
        if (z) {
            releaseTRMSLock();
        }
        return zRequestFrontendCiCam;
    }

    void releaseLnb() {
        this.mLnbLock.lock();
        try {
            if (this.mLnbHandle != null) {
                if (DEBUG) {
                    Log.d(TAG, "releasing Lnb");
                }
                this.mTunerResourceManager.releaseLnb(this.mLnbHandle.longValue(), this.mClientId);
                this.mLnbHandle = null;
            } else if (DEBUG) {
                Log.d(TAG, "NOT releasing Lnb because mLnbHandle is null");
            }
            this.mLnb = null;
        } finally {
            this.mLnbLock.unlock();
        }
    }

    public int getClientId() {
        return this.mClientId;
    }

    TunerResourceManager getTunerResourceManager() {
        return this.mTunerResourceManager;
    }

    private void acquireTRMSLock(String str) {
        if (DEBUG) {
            Log.d(TAG, "ATTEMPT:acquireLock() in " + str + "for clientId:" + this.mClientId);
        }
        if (this.mTunerResourceManager.acquireLock(this.mClientId)) {
            return;
        }
        Log.e(TAG, "FAILED:acquireLock() in " + str + " for clientId:" + this.mClientId + " - this can cause deadlock between Tuner API calls and onReclaimResources()");
    }

    private void releaseTRMSLock() {
        this.mTunerResourceManager.releaseLock(this.mClientId);
    }
}
