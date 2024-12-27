package android.media.tv.tuner;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaMetrics;
import android.media.tv.tuner.Tuner;
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
    private Integer mDemuxHandle;
    private Frontend mFrontend;
    private Integer mFrontendCiCamHandle;
    private Integer mFrontendCiCamId;
    private Integer mFrontendHandle;
    private FrontendInfo mFrontendInfo;
    private EventHandler mHandler;
    private Lnb mLnb;
    private Integer mLnbHandle;
    private long mNativeContext;
    private OnResourceLostListener mOnResourceLostListener;
    private Executor mOnResourceLostListenerExecutor;
    private Executor mOnTuneEventExecutor;
    private OnTuneEventListener mOnTuneEventListener;
    private int mRequestedCiCamId;
    private ScanCallback mScanCallback;
    private Executor mScanCallbackExecutor;
    private final TunerResourceManager mTunerResourceManager;
    private int mUserId;
    public static final byte[] VOID_KEYTOKEN = {0};
    private static final String TAG = "MediaTvTuner";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private DemuxInfo mDesiredDemuxInfo = new DemuxInfo(0);
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
    private Map<Integer, WeakReference<Descrambler>> mDescramblers = new HashMap();
    private List<WeakReference<Filter>> mFilters = new ArrayList();
    private final TunerResourceManager.ResourcesReclaimListener mResourceListener = new TunerResourceManager.ResourcesReclaimListener() { // from class: android.media.tv.tuner.Tuner.1
        @Override // android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener
        public void onReclaimResources() {
            if (Tuner.this.mFrontend != null) {
                FrameworkStatsLog.write(276, Tuner.this.mUserId, 0);
            }
            Tuner.this.releaseAll();
            Tuner.this.mHandler.sendMessage(Tuner.this.mHandler.obtainMessage(1));
        }
    };

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

    private native int nativeCloseDemux(int i);

    private native int nativeCloseFrontend(int i);

    private native int nativeConnectCiCam(int i);

    private native int nativeDisconnectCiCam();

    private native Integer nativeGetAvSyncHwId(Filter filter);

    private native Long nativeGetAvSyncTime(int i);

    private native DemuxCapabilities nativeGetDemuxCapabilities();

    private native DemuxInfo nativeGetDemuxInfo(int i);

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

    private native int nativeOpenDemuxByhandle(int i);

    private native Descrambler nativeOpenDescramblerByHandle(int i);

    private native DvrPlayback nativeOpenDvrPlayback(long j);

    private native DvrRecorder nativeOpenDvrRecorder(long j);

    private native Filter nativeOpenFilter(int i, int i2, long j);

    private native Frontend nativeOpenFrontendByHandle(int i);

    private native Lnb nativeOpenLnbByHandle(int i);

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
        } catch (UnsatisfiedLinkError e2) {
            Log.d(TAG, "tuner JNI library not found!");
        }
        sTunerVersion = 0;
    }

    public Tuner(Context context, String tvInputSessionId, int useCase) {
        this.mContext = context;
        this.mTunerResourceManager = (TunerResourceManager) this.mContext.getSystemService(TunerResourceManager.class);
        if (this.mTunerResourceManager == null) {
            throw new IllegalStateException("Tuner instance is created, but the device doesn't have tuner feature");
        }
        nativeSetup();
        sTunerVersion = nativeGetTunerVersion();
        if (sTunerVersion == 0) {
            Log.e(TAG, "Unknown Tuner version!");
        } else {
            Log.d(TAG, "Current Tuner version is " + TunerVersionChecker.getMajorVersion(sTunerVersion) + MediaMetrics.SEPARATOR + TunerVersionChecker.getMinorVersion(sTunerVersion) + MediaMetrics.SEPARATOR);
        }
        if (this.mHandler == null) {
            this.mHandler = createEventHandler();
        }
        int[] clientId = new int[1];
        ResourceClientProfile profile = new ResourceClientProfile();
        profile.tvInputSessionId = tvInputSessionId;
        profile.useCase = useCase;
        this.mTunerResourceManager.registerClientProfile(profile, new PendingIntent$$ExternalSyntheticLambda0(), this.mResourceListener, clientId);
        this.mClientId = clientId[0];
        this.mUserId = Process.myUid();
    }

    private FrontendInfo[] getFrontendInfoListInternal() {
        List<Integer> ids = getFrontendIds();
        if (ids == null) {
            return null;
        }
        FrontendInfo[] infos = new FrontendInfo[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i).intValue();
            FrontendInfo frontendInfo = getFrontendInfoById(id);
            if (frontendInfo == null) {
                Log.e(TAG, "Failed to get a FrontendInfo on frontend id:" + id + "!");
            } else {
                infos[i] = frontendInfo;
            }
        }
        return (FrontendInfo[]) Arrays.stream(infos).filter(new Predicate() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda12
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

    static /* synthetic */ FrontendInfo[] lambda$getFrontendInfoListInternal$0(int x$0) {
        return new FrontendInfo[x$0];
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

    public void setResourceLostListener(Executor executor, OnResourceLostListener listener) {
        synchronized (this.mOnResourceLostListenerLock) {
            Objects.requireNonNull(executor, "OnResourceLostListener must not be null");
            Objects.requireNonNull(listener, "executor must not be null");
            this.mOnResourceLostListener = listener;
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
            if (this.mFeOwnerTuner != null) {
                this.mFeOwnerTuner.unregisterFrontendCallbackListener(this);
                this.mFeOwnerTuner = null;
                nativeUnshareFrontend();
            }
            this.mTunerResourceManager.shareFrontend(this.mClientId, tuner.mClientId);
            this.mFeOwnerTuner = tuner;
            this.mFeOwnerTuner.registerFrontendCallbackListener(this);
            this.mFrontendHandle = this.mFeOwnerTuner.mFrontendHandle;
            this.mFrontend = this.mFeOwnerTuner.mFrontend;
            nativeShareFrontend(this.mFrontend.mId);
        } finally {
            releaseTRMSLock();
            this.mFrontendLock.unlock();
        }
    }

    public int transferOwner(Tuner newOwner) {
        acquireTRMSLock("transferOwner()");
        this.mFrontendLock.lock();
        this.mFrontendCiCamLock.lock();
        this.mLnbLock.lock();
        try {
            if (isFrontendOwner() && isNewOwnerQualifiedForTransfer(newOwner)) {
                int res = transferFeOwner(newOwner);
                if (res != 0) {
                    return res;
                }
                int res2 = transferCiCamOwner(newOwner);
                if (res2 != 0) {
                    return res2;
                }
                int res3 = transferLnbOwner(newOwner);
                if (res3 != 0) {
                    return res3;
                }
                this.mFrontendLock.unlock();
                this.mFrontendCiCamLock.unlock();
                this.mLnbLock.unlock();
                releaseTRMSLock();
                return 0;
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

    private void replicateFrontendSettings(Tuner src) {
        this.mFrontendLock.lock();
        try {
            if (src == null) {
                if (DEBUG) {
                    Log.d(TAG, "resetting Frontend params for " + this.mClientId);
                }
                this.mFrontend = null;
                this.mFrontendHandle = null;
                this.mFrontendInfo = null;
                this.mFrontendType = 0;
            } else {
                if (DEBUG) {
                    Log.d(TAG, "copying Frontend params from " + src.mClientId + " to " + this.mClientId);
                }
                this.mFrontend = src.mFrontend;
                this.mFrontendHandle = src.mFrontendHandle;
                this.mFrontendInfo = src.mFrontendInfo;
                this.mFrontendType = src.mFrontendType;
            }
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private void setFrontendOwner(Tuner owner) {
        this.mFrontendLock.lock();
        try {
            this.mFeOwnerTuner = owner;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private void replicateCiCamSettings(Tuner src) {
        this.mFrontendCiCamLock.lock();
        try {
            if (src == null) {
                if (DEBUG) {
                    Log.d(TAG, "resetting CiCamParams: " + this.mClientId);
                }
                this.mFrontendCiCamHandle = null;
                this.mFrontendCiCamId = null;
            } else {
                if (DEBUG) {
                    Log.d(TAG, "copying CiCamParams from " + src.mClientId + " to " + this.mClientId);
                    Log.d(TAG, "mFrontendCiCamHandle:" + src.mFrontendCiCamHandle + ", mFrontendCiCamId:" + src.mFrontendCiCamId);
                }
                this.mFrontendCiCamHandle = src.mFrontendCiCamHandle;
                this.mFrontendCiCamId = src.mFrontendCiCamId;
            }
        } finally {
            this.mFrontendCiCamLock.unlock();
        }
    }

    private void replicateLnbSettings(Tuner src) {
        this.mLnbLock.lock();
        try {
            if (src == null) {
                if (DEBUG) {
                    Log.d(TAG, "resetting Lnb params");
                }
                this.mLnb = null;
                this.mLnbHandle = null;
            } else {
                if (DEBUG) {
                    Log.d(TAG, "copying Lnb params from " + src.mClientId + " to " + this.mClientId);
                }
                this.mLnb = src.mLnb;
                this.mLnbHandle = src.mLnbHandle;
            }
        } finally {
            this.mLnbLock.unlock();
        }
    }

    private boolean isFrontendOwner() {
        boolean notAnOwner = this.mFeOwnerTuner != null;
        if (!notAnOwner) {
            return true;
        }
        Log.e(TAG, "transferOwner() - cannot be called on the non-owner");
        return false;
    }

    private boolean isNewOwnerQualifiedForTransfer(Tuner newOwner) {
        boolean newOwnerIsTheCurrentSharee = newOwner.mFeOwnerTuner == this && newOwner.mFrontendHandle.equals(this.mFrontendHandle);
        if (!newOwnerIsTheCurrentSharee) {
            Log.e(TAG, "transferOwner() - new owner must be the current sharee");
            return false;
        }
        boolean newOwnerAlreadyHoldsToBeSharedResource = (newOwner.mFrontendCiCamHandle == null && newOwner.mLnb == null) ? false : true;
        if (!newOwnerAlreadyHoldsToBeSharedResource) {
            return true;
        }
        Log.e(TAG, "transferOwner() - new owner cannot be holding CiCam nor Lnb resource");
        return false;
    }

    private int transferFeOwner(Tuner newOwner) {
        newOwner.nativeUpdateFrontend(getNativeContext());
        nativeUpdateFrontend(0L);
        newOwner.replicateFrontendSettings(this);
        setFrontendOwner(newOwner);
        newOwner.setFrontendOwner(null);
        return this.mTunerResourceManager.transferOwner(0, this.mClientId, newOwner.mClientId) ? 0 : 6;
    }

    private int transferCiCamOwner(Tuner newOwner) {
        boolean notAnOwner = this.mFrontendCiCamHandle == null;
        if (notAnOwner) {
            return 0;
        }
        newOwner.replicateCiCamSettings(this);
        replicateCiCamSettings(null);
        return this.mTunerResourceManager.transferOwner(5, this.mClientId, newOwner.mClientId) ? 0 : 6;
    }

    private int transferLnbOwner(Tuner newOwner) {
        boolean notAnOwner = this.mLnb == null;
        if (notAnOwner) {
            return 0;
        }
        this.mLnb.setOwner(newOwner);
        newOwner.replicateLnbSettings(this);
        replicateLnbSettings(null);
        return this.mTunerResourceManager.transferOwner(3, this.mClientId, newOwner.mClientId) ? 0 : 6;
    }

    public void updateResourcePriority(int priority, int niceValue) {
        this.mTunerResourceManager.updateClientPriority(this.mClientId, priority, niceValue);
    }

    public boolean hasUnusedFrontend(int frontendType) {
        return this.mTunerResourceManager.hasUnusedFrontend(frontendType);
    }

    public boolean isLowestPriority(int frontendType) {
        return this.mTunerResourceManager.isLowestPriority(this.mClientId, frontendType);
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
        acquireTRMSLock("close()");
        try {
            releaseAll();
            this.mTunerResourceManager.unregisterClientProfile(this.mClientId);
            TunerUtils.throwExceptionForResult(nativeClose(), "failed to close tuner");
        } finally {
            releaseTRMSLock();
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
        if (DEBUG) {
            Log.d(TAG, "Tuner#releaseFrontend");
        }
        this.mFrontendLock.lock();
        try {
            if (this.mFrontendHandle != null) {
                if (DEBUG) {
                    Log.d(TAG, "mFrontendHandle not null");
                }
                if (this.mFeOwnerTuner != null) {
                    if (DEBUG) {
                        Log.d(TAG, "mFeOwnerTuner not null - sharee");
                    }
                    this.mFeOwnerTuner.unregisterFrontendCallbackListener(this);
                    this.mFeOwnerTuner = null;
                    nativeUnshareFrontend();
                } else {
                    if (DEBUG) {
                        Log.d(TAG, "mFeOwnerTuner null - owner");
                    }
                    int res = nativeCloseFrontend(this.mFrontendHandle.intValue());
                    if (res != 0) {
                        TunerUtils.throwExceptionForResult(res, "failed to close frontend");
                    }
                }
                if (DEBUG) {
                    Log.d(TAG, "call TRM#releaseFrontend :" + this.mFrontendHandle + ", " + this.mClientId);
                }
                this.mTunerResourceManager.releaseFrontend(this.mFrontendHandle.intValue(), this.mClientId);
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
                    Log.d(TAG, "unlinking CiCam : " + this.mFrontendCiCamHandle + " for " + this.mClientId);
                }
                int result = nativeUnlinkCiCam(this.mFrontendCiCamId.intValue());
                if (result == 0) {
                    this.mTunerResourceManager.releaseCiCam(this.mFrontendCiCamHandle.intValue(), this.mClientId);
                    replicateCiCamSettings(null);
                } else {
                    Log.e(TAG, "nativeUnlinkCiCam(" + this.mFrontendCiCamHandle + ") for mClientId:" + this.mClientId + "failed with result:" + result);
                }
            } else if (DEBUG) {
                Log.d(TAG, "NOT unlinking CiCam : " + this.mClientId);
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
                for (WeakReference<Filter> weakFilter : this.mFilters) {
                    Filter filter = weakFilter.get();
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
                for (Map.Entry<Integer, WeakReference<Descrambler>> d : this.mDescramblers.entrySet()) {
                    Descrambler descrambler = d.getValue().get();
                    if (descrambler != null) {
                        descrambler.close();
                    }
                    this.mTunerResourceManager.releaseDescrambler(d.getKey().intValue(), this.mClientId);
                }
                this.mDescramblers.clear();
            }
        }
    }

    private void releaseDemux() {
        this.mDemuxLock.lock();
        try {
            if (this.mDemuxHandle != null) {
                int res = nativeCloseDemux(this.mDemuxHandle.intValue());
                if (res != 0) {
                    TunerUtils.throwExceptionForResult(res, "failed to close demux");
                }
                this.mTunerResourceManager.releaseDemux(this.mDemuxHandle.intValue(), this.mClientId);
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
        Looper looper = Looper.myLooper();
        if (looper != null) {
            return new EventHandler(looper);
        }
        Looper looper2 = Looper.getMainLooper();
        if (looper2 != null) {
            return new EventHandler(looper2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EventHandler extends Handler {
        private EventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    synchronized (Tuner.this.mOnResourceLostListenerLock) {
                        if (Tuner.this.mOnResourceLostListener != null && Tuner.this.mOnResourceLostListenerExecutor != null) {
                            Tuner.this.mOnResourceLostListenerExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$EventHandler$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Tuner.EventHandler.this.lambda$handleMessage$0();
                                }
                            });
                        }
                    }
                    return;
                case 2:
                default:
                    return;
                case 3:
                    Filter filter = (Filter) msg.obj;
                    if (filter.getCallback() != null) {
                        filter.getCallback().onFilterStatusChanged(filter, msg.arg1);
                        return;
                    }
                    return;
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

        private Frontend(int id) {
            this.mId = id;
        }
    }

    public void setOnTuneEventListener(Executor executor, OnTuneEventListener eventListener) {
        synchronized (this.mOnTuneEventLock) {
            this.mOnTuneEventListener = eventListener;
            this.mOnTuneEventExecutor = executor;
        }
    }

    public void clearOnTuneEventListener() {
        synchronized (this.mOnTuneEventLock) {
            this.mOnTuneEventListener = null;
            this.mOnTuneEventExecutor = null;
        }
    }

    public int tune(FrontendSettings settings) {
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
                return 3;
            }
            int type = settings.getType();
            if (this.mFrontendHandle != null && type != this.mFrontendType) {
                Log.e(TAG, "Frontend was opened with type " + this.mFrontendType + ", new type is " + type);
                return 3;
            }
            Log.d(TAG, "Tune to " + settings.getFrequencyLong());
            this.mFrontendType = type;
            if (this.mFrontendType == 10 && !TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "Tuner with DTMB Frontend")) {
                return 1;
            }
            if (this.mFrontendType == 11 && !TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Tuner with IPTV Frontend")) {
                return 1;
            }
            if (!checkResource(0, this.mFrontendLock)) {
                return 1;
            }
            this.mFrontendInfo = null;
            Log.d(TAG, "Write Stats Log for tuning.");
            FrameworkStatsLog.write(276, this.mUserId, 1);
            int res = nativeTune(settings.getType(), settings);
            return res;
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

    public int scan(FrontendSettings settings, int scanType, Executor executor, ScanCallback scanCallback) {
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
                this.mFrontendLock.unlock();
                return 3;
            }
            synchronized (this.mScanCallbackLock) {
                if ((this.mScanCallback != null && this.mScanCallback != scanCallback) || (this.mScanCallbackExecutor != null && this.mScanCallbackExecutor != executor)) {
                    throw new IllegalStateException("Different Scan session already in progress.  stopScan must be called before a new scan session can be started.");
                }
                this.mFrontendType = settings.getType();
                if (this.mFrontendType == 10 && !TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "Scan with DTMB Frontend")) {
                    return 1;
                }
                if (this.mFrontendType == 11 && !TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Tuner with IPTV Frontend")) {
                    return 1;
                }
                if (!checkResource(0, this.mFrontendLock)) {
                    return 1;
                }
                this.mScanCallback = scanCallback;
                this.mScanCallbackExecutor = executor;
                this.mFrontendInfo = null;
                FrameworkStatsLog.write(276, this.mUserId, 5);
                return nativeScan(settings.getType(), settings, scanType);
            }
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int cancelScanning() {
        int retVal;
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
                this.mFrontendLock.unlock();
                return 3;
            }
            synchronized (this.mScanCallbackLock) {
                FrameworkStatsLog.write(276, this.mUserId, 6);
                retVal = nativeStopScan();
                this.mScanCallback = null;
                this.mScanCallbackExecutor = null;
            }
            return retVal;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private boolean requestFrontend() {
        int intValue;
        int[] feHandle = new int[1];
        try {
            TunerFrontendRequest request = new TunerFrontendRequest();
            request.clientId = this.mClientId;
            request.frontendType = this.mFrontendType;
            if (this.mDesiredFrontendId == null) {
                intValue = -1;
            } else {
                intValue = this.mDesiredFrontendId.intValue();
            }
            request.desiredId = intValue;
            boolean granted = this.mTunerResourceManager.requestFrontend(request, feHandle);
            if (granted) {
                this.mFrontendHandle = Integer.valueOf(feHandle[0]);
                this.mFrontend = nativeOpenFrontendByHandle(this.mFrontendHandle.intValue());
            }
            if (this.mFrontendType == 5 || this.mFrontendType == 7 || this.mFrontendType == 8) {
                this.mLnbLock.lock();
                try {
                    if (this.mLnbHandle != null && this.mLnb != null) {
                        nativeSetLnb(this.mLnb);
                    }
                } finally {
                    this.mLnbLock.unlock();
                }
            }
            return granted;
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

    public int setLnaEnabled(boolean enable) {
        return nativeSetLna(enable);
    }

    public FrontendStatus getFrontendStatus(int[] statusTypes) {
        this.mFrontendLock.lock();
        try {
            if (this.mFrontend == null) {
                throw new IllegalStateException("frontend is not initialized");
            }
            if (this.mFeOwnerTuner != null) {
                throw new IllegalStateException("Operation cannot be done by sharee of tuner");
            }
            return nativeGetFrontendStatus(statusTypes);
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int getAvSyncHwId(Filter filter) {
        this.mDemuxLock.lock();
        try {
            int i = -1;
            if (!checkResource(1, this.mDemuxLock)) {
                return -1;
            }
            Integer id = nativeGetAvSyncHwId(filter);
            if (id != null) {
                i = id.intValue();
            }
            return i;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public long getAvSyncTime(int avSyncHwId) {
        this.mDemuxLock.lock();
        try {
            long j = -1;
            if (!checkResource(1, this.mDemuxLock)) {
                return -1L;
            }
            Long time = nativeGetAvSyncTime(avSyncHwId);
            if (time != null) {
                j = time.longValue();
            }
            return j;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int connectCiCam(int ciCamId) {
        this.mDemuxLock.lock();
        try {
            if (checkResource(1, this.mDemuxLock)) {
                return nativeConnectCiCam(ciCamId);
            }
            return 1;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int connectFrontendToCiCam(int ciCamId) {
        acquireTRMSLock("connectFrontendToCiCam()");
        this.mFrontendCiCamLock.lock();
        this.mFrontendLock.lock();
        try {
            if (this.mFrontendHandle == null) {
                Log.d(TAG, "Operation cannot be done without frontend");
                return 3;
            }
            if (this.mFeOwnerTuner != null) {
                Log.d(TAG, "Operation cannot be done by sharee of tuner");
                return 3;
            }
            if (TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "linkFrontendToCiCam")) {
                this.mRequestedCiCamId = ciCamId;
                if (checkResource(5, null) && checkResource(0, null)) {
                    return nativeLinkCiCam(ciCamId);
                }
            }
            releaseTRMSLock();
            this.mFrontendCiCamLock.unlock();
            this.mFrontendLock.unlock();
            return -1;
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

    public int disconnectFrontendToCiCam(int ciCamId) {
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
                if (this.mFrontendCiCamHandle != null && this.mFrontendCiCamId != null && this.mFrontendCiCamId.intValue() == ciCamId) {
                    int result = nativeUnlinkCiCam(ciCamId);
                    if (result == 0) {
                        this.mTunerResourceManager.releaseCiCam(this.mFrontendCiCamHandle.intValue(), this.mClientId);
                        this.mFrontendCiCamId = null;
                        this.mFrontendCiCamHandle = null;
                    }
                    if (this.mFrontendCiCamLock.isLocked()) {
                        this.mFrontendCiCamLock.unlock();
                    }
                    releaseTRMSLock();
                    return result;
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

    public int removeOutputPid(int pid) {
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
                return nativeRemoveOutputPid(pid);
            }
            Log.d(TAG, "Operation cannot be done by sharee of tuner");
            this.mFrontendLock.unlock();
            return 3;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public List<FrontendStatusReadiness> getFrontendStatusReadiness(int[] statusTypes) {
        this.mFrontendLock.lock();
        try {
            if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Get fronted status readiness")) {
                return Collections.EMPTY_LIST;
            }
            if (this.mFrontend == null) {
                throw new IllegalStateException("frontend is not initialized");
            }
            if (this.mFeOwnerTuner != null) {
                throw new IllegalStateException("Operation cannot be done by sharee of tuner");
            }
            FrontendStatusReadiness[] readiness = nativeGetFrontendStatusReadiness(statusTypes);
            return readiness == null ? Collections.EMPTY_LIST : Arrays.asList(readiness);
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public FrontendInfo getFrontendInfo() {
        this.mFrontendLock.lock();
        try {
            if (checkResource(0, this.mFrontendLock)) {
                if (this.mFrontend == null) {
                    throw new IllegalStateException("frontend is not initialized");
                }
                if (this.mFrontendInfo == null) {
                    this.mFrontendInfo = getFrontendInfoById(this.mFrontend.mId);
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
        FrontendInfo[] feInfoList = getFrontendInfoListInternal();
        if (feInfoList == null) {
            return null;
        }
        return Arrays.asList(feInfoList);
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

    public int setMaxNumberOfFrontends(int frontendType, int maxNumber) {
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Set maximum Frontends")) {
            return 1;
        }
        if (maxNumber < 0) {
            return 4;
        }
        if (this.mFeOwnerTuner != null) {
            Log.d(TAG, "Operation cannot be done by sharee of tuner");
            return 3;
        }
        int res = nativeSetMaxNumberOfFrontends(frontendType, maxNumber);
        if (res == 0 && !this.mTunerResourceManager.setMaxNumberOfFrontends(frontendType, maxNumber)) {
            return 4;
        }
        return res;
    }

    public int getMaxNumberOfFrontends(int frontendType) {
        if (!TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Set maximum Frontends")) {
            return -1;
        }
        int maxNumFromHAL = nativeGetMaxNumberOfFrontends(frontendType);
        int maxNumFromTRM = this.mTunerResourceManager.getMaxNumberOfFrontends(frontendType);
        if (maxNumFromHAL != maxNumFromTRM) {
            Log.w(TAG, "max num of usable frontend is out-of-sync b/w " + maxNumFromHAL + " != " + maxNumFromTRM);
        }
        return maxNumFromHAL;
    }

    public FrontendInfo getFrontendInfoById(int id) {
        this.mFrontendLock.lock();
        try {
            return nativeGetFrontendInfo(id);
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
            if (this.mDemuxHandle != null) {
                return nativeGetDemuxInfo(this.mDemuxHandle.intValue());
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

    private void onFrontendEvent(final int eventType) {
        Log.d(TAG, "Got event from tuning. Event type: " + eventType + " for " + this);
        synchronized (this.mOnTuneEventLock) {
            if (this.mOnTuneEventExecutor != null && this.mOnTuneEventListener != null) {
                this.mOnTuneEventExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda21
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onFrontendEvent$1(eventType);
                    }
                });
            }
        }
        Log.d(TAG, "Wrote Stats Log for the events from tuning.");
        if (eventType == 0) {
            FrameworkStatsLog.write(276, this.mUserId, 2);
        } else if (eventType == 1) {
            FrameworkStatsLog.write(276, this.mUserId, 3);
        } else if (eventType == 2) {
            FrameworkStatsLog.write(276, this.mUserId, 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrontendEvent$1(int eventType) {
        synchronized (this.mOnTuneEventLock) {
            if (this.mOnTuneEventListener != null) {
                this.mOnTuneEventListener.onTuneEvent(eventType);
            }
        }
    }

    private void onLocked() {
        Log.d(TAG, "Wrote Stats Log for locked event from scanning.");
        FrameworkStatsLog.write(276, this.mUserId, 2);
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onLocked$2();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLocked$2() {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onLocked();
            }
        }
    }

    private void onUnlocked() {
        Log.d(TAG, "Wrote Stats Log for unlocked event from scanning.");
        FrameworkStatsLog.write(276, this.mUserId, 2);
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onUnlocked$3();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUnlocked$3() {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onUnlocked();
            }
        }
    }

    private void onScanStopped() {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onScanStopped$4();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onScanStopped$4() {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onScanStopped();
            }
        }
    }

    private void onProgress(final int percent) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onProgress$5(percent);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProgress$5(int percent) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onProgress(percent);
            }
        }
    }

    private void onFrequenciesReport(final long[] frequencies) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onFrequenciesReport$6(frequencies);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrequenciesReport$6(long[] frequencies) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onFrequenciesLongReported(frequencies);
            }
        }
    }

    private void onSymbolRates(final int[] rate) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onSymbolRates$7(rate);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSymbolRates$7(int[] rate) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onSymbolRatesReported(rate);
            }
        }
    }

    private void onHierarchy(final int hierarchy) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onHierarchy$8(hierarchy);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHierarchy$8(int hierarchy) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onHierarchyReported(hierarchy);
            }
        }
    }

    private void onSignalType(final int signalType) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onSignalType$9(signalType);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSignalType$9(int signalType) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onSignalTypeReported(signalType);
            }
        }
    }

    private void onPlpIds(final int[] plpIds) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onPlpIds$10(plpIds);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPlpIds$10(int[] plpIds) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onPlpIdsReported(plpIds);
            }
        }
    }

    private void onGroupIds(final int[] groupIds) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onGroupIds$11(groupIds);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGroupIds$11(int[] groupIds) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onGroupIdsReported(groupIds);
            }
        }
    }

    private void onInputStreamIds(final int[] inputStreamIds) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onInputStreamIds$12(inputStreamIds);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInputStreamIds$12(int[] inputStreamIds) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onInputStreamIdsReported(inputStreamIds);
            }
        }
    }

    private void onDvbsStandard(final int dvbsStandandard) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onDvbsStandard$13(dvbsStandandard);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbsStandard$13(int dvbsStandandard) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onDvbsStandardReported(dvbsStandandard);
            }
        }
    }

    private void onDvbtStandard(final int dvbtStandard) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onDvbtStandard$14(dvbtStandard);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbtStandard$14(int dvbtStandard) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onDvbtStandardReported(dvbtStandard);
            }
        }
    }

    private void onAnalogSifStandard(final int sif) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onAnalogSifStandard$15(sif);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAnalogSifStandard$15(int sif) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onAnalogSifStandardReported(sif);
            }
        }
    }

    private void onAtsc3PlpInfos(final Atsc3PlpInfo[] atsc3PlpInfos) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onAtsc3PlpInfos$16(atsc3PlpInfos);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAtsc3PlpInfos$16(Atsc3PlpInfo[] atsc3PlpInfos) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onAtsc3PlpInfosReported(atsc3PlpInfos);
            }
        }
    }

    private void onModulationReported(final int modulation) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onModulationReported$17(modulation);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onModulationReported$17(int modulation) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onModulationReported(modulation);
            }
        }
    }

    private void onPriorityReported(final boolean isHighPriority) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onPriorityReported$18(isHighPriority);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPriorityReported$18(boolean isHighPriority) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onPriorityReported(isHighPriority);
            }
        }
    }

    private void onDvbcAnnexReported(final int dvbcAnnex) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onDvbcAnnexReported$19(dvbcAnnex);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbcAnnexReported$19(int dvbcAnnex) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onDvbcAnnexReported(dvbcAnnex);
            }
        }
    }

    private void onDvbtCellIdsReported(final int[] dvbtCellIds) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Tuner.this.lambda$onDvbtCellIdsReported$20(dvbtCellIds);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbtCellIdsReported$20(int[] dvbtCellIds) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onDvbtCellIdsReported(dvbtCellIds);
            }
        }
    }

    public Filter openFilter(int mainType, int subType, long bufferSize, Executor executor, FilterCallback cb) {
        this.mDemuxLock.lock();
        try {
            TunerVersionChecker.getMajorVersion(sTunerVersion);
            if (sTunerVersion >= 196608) {
                DemuxInfo demuxInfo = new DemuxInfo(mainType);
                int res = configureDemuxInternal(demuxInfo, false);
                if (res != 0) {
                    Log.e(TAG, "openFilter called for unsupported mainType: " + mainType);
                    return null;
                }
            }
            if (!checkResource(1, this.mDemuxLock)) {
                return null;
            }
            Filter filter = nativeOpenFilter(mainType, TunerUtils.getFilterSubtype(mainType, subType), bufferSize);
            if (filter != null) {
                filter.setType(mainType, subType);
                filter.setCallback(cb, executor);
                if (this.mHandler == null) {
                    this.mHandler = createEventHandler();
                }
                synchronized (this.mFilters) {
                    WeakReference<Filter> weakFilter = new WeakReference<>(filter);
                    this.mFilters.add(weakFilter);
                    if (this.mFilters.size() > 256) {
                        Iterator<WeakReference<Filter>> iterator = this.mFilters.iterator();
                        while (iterator.hasNext()) {
                            WeakReference<Filter> wFilter = iterator.next();
                            if (wFilter.get() == null) {
                                iterator.remove();
                            }
                        }
                    }
                }
            }
            return filter;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public Lnb openLnb(Executor executor, LnbCallback cb) {
        this.mLnbLock.lock();
        try {
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(cb, "LnbCallback must not be null");
            if (this.mLnb != null) {
                this.mLnb.setCallbackAndOwner(this, executor, cb);
                return this.mLnb;
            }
            if (!checkResource(3, this.mLnbLock) || this.mLnb == null) {
                this.mLnbLock.unlock();
                return null;
            }
            this.mLnb.setCallbackAndOwner(this, executor, cb);
            if (this.mFrontendHandle != null && this.mFrontend != null) {
                setLnb(this.mLnb);
            }
            return this.mLnb;
        } finally {
            this.mLnbLock.unlock();
        }
    }

    public Lnb openLnbByName(String name, Executor executor, LnbCallback cb) {
        acquireTRMSLock("openLnbByName");
        this.mLnbLock.lock();
        try {
            Objects.requireNonNull(name, "LNB name must not be null");
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(cb, "LnbCallback must not be null");
            Lnb newLnb = nativeOpenLnbByName(name);
            if (newLnb != null) {
                if (this.mLnb != null) {
                    this.mLnb.closeInternal();
                    this.mLnbHandle = null;
                }
                this.mLnb = newLnb;
                this.mLnb.setCallbackAndOwner(this, executor, cb);
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
        int[] lnbHandle = new int[1];
        TunerLnbRequest request = new TunerLnbRequest();
        request.clientId = this.mClientId;
        boolean granted = this.mTunerResourceManager.requestLnb(request, lnbHandle);
        if (granted) {
            this.mLnbHandle = Integer.valueOf(lnbHandle[0]);
            this.mLnb = nativeOpenLnbByHandle(this.mLnbHandle.intValue());
        }
        return granted;
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

    public DvrRecorder openDvrRecorder(long bufferSize, Executor executor, OnRecordStatusChangedListener l) {
        this.mDemuxLock.lock();
        try {
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(l, "OnRecordStatusChangedListener must not be null");
            if (checkResource(1, this.mDemuxLock)) {
                DvrRecorder dvr = nativeOpenDvrRecorder(bufferSize);
                dvr.setListener(executor, l);
                return dvr;
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public DvrPlayback openDvrPlayback(long bufferSize, Executor executor, OnPlaybackStatusChangedListener l) {
        this.mDemuxLock.lock();
        try {
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(l, "OnPlaybackStatusChangedListener must not be null");
            if (checkResource(1, this.mDemuxLock)) {
                DvrPlayback dvr = nativeOpenDvrPlayback(bufferSize);
                dvr.setListener(executor, l);
                return dvr;
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int applyFrontend(FrontendInfo desiredFrontendInfo) {
        Objects.requireNonNull(desiredFrontendInfo, "desiredFrontendInfo must not be null");
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                Log.e(TAG, "Operation connot be done by sharee of tuner");
                return 3;
            }
            if (this.mFrontendHandle != null) {
                Log.e(TAG, "A frontend has been opened before");
                return 3;
            }
            this.mFrontendType = desiredFrontendInfo.getType();
            this.mDesiredFrontendId = Integer.valueOf(desiredFrontendInfo.getId());
            if (DEBUG) {
                Log.d(TAG, "Applying frontend with type " + this.mFrontendType + ", id " + this.mDesiredFrontendId);
            }
            if (checkResource(0, this.mFrontendLock)) {
                return 0;
            }
            this.mFrontendLock.unlock();
            return 1;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public static SharedFilter openSharedFilter(Context context, String sharedFilterToken, Executor executor, SharedFilterCallback cb) {
        Objects.requireNonNull(sharedFilterToken, "sharedFilterToken must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(cb, "SharedFilterCallback must not be null");
        if (context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_TV_SHARED_FILTER) != 0) {
            throw new SecurityException("Caller must have ACCESS_TV_SHAREDFILTER permission.");
        }
        SharedFilter filter = nativeOpenSharedFilter(sharedFilterToken);
        if (filter != null) {
            filter.setCallback(cb, executor);
        }
        return filter;
    }

    public int configureDemux(DemuxInfo desiredDemuxInfo) {
        int configureDemuxInternal;
        TunerVersionChecker.getMajorVersion(sTunerVersion);
        if (sTunerVersion < 196608) {
            Log.e(TAG, "configureDemux() is not supported for tuner version:" + TunerVersionChecker.getMajorVersion(sTunerVersion) + MediaMetrics.SEPARATOR + TunerVersionChecker.getMinorVersion(sTunerVersion) + MediaMetrics.SEPARATOR);
            return 1;
        }
        synchronized (this.mDemuxLock) {
            configureDemuxInternal = configureDemuxInternal(desiredDemuxInfo, true);
        }
        return configureDemuxInternal;
    }

    private int configureDemuxInternal(DemuxInfo desiredDemuxInfo, boolean reduceDesiredFilterTypes) {
        DemuxInfo currentDemuxInfo;
        if (desiredDemuxInfo == null) {
            if (this.mDemuxHandle != null) {
                releaseFilters();
                releaseDemux();
            }
            return 0;
        }
        int desiredFilterTypes = desiredDemuxInfo.getFilterTypes();
        if ((this.mDesiredDemuxInfo.getFilterTypes() & desiredFilterTypes) == desiredFilterTypes) {
            if (reduceDesiredFilterTypes) {
                this.mDesiredDemuxInfo.setFilterTypes(desiredFilterTypes);
            }
            return 0;
        }
        DemuxCapabilities caps = nativeGetDemuxCapabilities();
        if (caps == null) {
            Log.e(TAG, "configureDemuxInternal:failed to get DemuxCapabilities");
            return 1;
        }
        int[] filterCapsList = caps.getFilterTypeCapabilityList();
        if (filterCapsList.length <= 0) {
            Log.e(TAG, "configureDemuxInternal: getFilterTypeCapabilityList() returned an empty array");
            return 1;
        }
        boolean supported = false;
        int length = filterCapsList.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int filterCaps = filterCapsList[i];
            if ((desiredFilterTypes & filterCaps) != desiredFilterTypes) {
                i++;
            } else {
                supported = true;
                break;
            }
        }
        if (!supported) {
            Log.e(TAG, "configureDemuxInternal: requested caps:" + desiredFilterTypes + " is not supported by the system");
            return 1;
        }
        if (this.mDemuxHandle != null && desiredFilterTypes != 0 && (currentDemuxInfo = nativeGetDemuxInfo(this.mDemuxHandle.intValue())) != null && (currentDemuxInfo.getFilterTypes() & desiredFilterTypes) != desiredFilterTypes) {
            releaseFilters();
            releaseDemux();
        }
        this.mDesiredDemuxInfo.setFilterTypes(desiredFilterTypes);
        return 0;
    }

    private boolean requestDemux() {
        int[] demuxHandle = new int[1];
        TunerDemuxRequest request = new TunerDemuxRequest();
        request.clientId = this.mClientId;
        request.desiredFilterTypes = this.mDesiredDemuxInfo.getFilterTypes();
        boolean granted = this.mTunerResourceManager.requestDemux(request, demuxHandle);
        if (granted) {
            this.mDemuxHandle = Integer.valueOf(demuxHandle[0]);
            nativeOpenDemuxByhandle(this.mDemuxHandle.intValue());
        }
        return granted;
    }

    private Descrambler requestDescrambler() {
        int[] descramblerHandle = new int[1];
        TunerDescramblerRequest request = new TunerDescramblerRequest();
        request.clientId = this.mClientId;
        boolean granted = this.mTunerResourceManager.requestDescrambler(request, descramblerHandle);
        if (!granted) {
            return null;
        }
        int handle = descramblerHandle[0];
        Descrambler descrambler = nativeOpenDescramblerByHandle(handle);
        if (descrambler != null) {
            synchronized (this.mDescramblers) {
                WeakReference weakDescrambler = new WeakReference(descrambler);
                this.mDescramblers.put(Integer.valueOf(handle), weakDescrambler);
            }
        } else {
            this.mTunerResourceManager.releaseDescrambler(handle, this.mClientId);
        }
        return descrambler;
    }

    private boolean requestFrontendCiCam(int ciCamId) {
        int[] ciCamHandle = new int[1];
        TunerCiCamRequest request = new TunerCiCamRequest();
        request.clientId = this.mClientId;
        request.ciCamId = ciCamId;
        boolean granted = this.mTunerResourceManager.requestCiCam(request, ciCamHandle);
        if (granted) {
            this.mFrontendCiCamHandle = Integer.valueOf(ciCamHandle[0]);
            this.mFrontendCiCamId = Integer.valueOf(ciCamId);
        }
        return granted;
    }

    private boolean checkResource(int resourceType, ReentrantLock localLock) {
        switch (resourceType) {
            case 0:
                if (this.mFrontendHandle != null || requestResource(resourceType, localLock)) {
                }
                break;
            case 1:
                if (this.mDemuxHandle != null || requestResource(resourceType, localLock)) {
                }
                break;
            case 3:
                if (this.mLnb != null || requestResource(resourceType, localLock)) {
                }
                break;
            case 5:
                if (this.mFrontendCiCamHandle != null || requestResource(resourceType, localLock)) {
                }
                break;
        }
        return false;
    }

    private boolean requestResource(int resourceType, ReentrantLock localLock) {
        boolean enableLockOperations = localLock != null;
        if (enableLockOperations) {
            if (!localLock.isLocked()) {
                throw new IllegalStateException("local lock must be locked beforehand");
            }
            localLock.unlock();
        }
        if (enableLockOperations) {
            acquireTRMSLock("requestResource:" + resourceType);
        }
        if (enableLockOperations) {
            try {
                localLock.lock();
            } finally {
                if (enableLockOperations) {
                    releaseTRMSLock();
                }
            }
        }
        switch (resourceType) {
            case 0:
                boolean requestFrontend = requestFrontend();
                if (enableLockOperations) {
                    releaseTRMSLock();
                }
                return requestFrontend;
            case 1:
                boolean requestDemux = requestDemux();
                if (enableLockOperations) {
                    releaseTRMSLock();
                }
                return requestDemux;
            case 2:
            case 4:
            default:
                if (enableLockOperations) {
                    releaseTRMSLock();
                }
                return false;
            case 3:
                boolean requestLnb = requestLnb();
                if (enableLockOperations) {
                    releaseTRMSLock();
                }
                return requestLnb;
            case 5:
                return requestFrontendCiCam(this.mRequestedCiCamId);
        }
    }

    void releaseLnb() {
        this.mLnbLock.lock();
        try {
            if (this.mLnbHandle != null) {
                if (DEBUG) {
                    Log.d(TAG, "releasing Lnb");
                }
                this.mTunerResourceManager.releaseLnb(this.mLnbHandle.intValue(), this.mClientId);
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

    private void acquireTRMSLock(String functionNameForLog) {
        if (DEBUG) {
            Log.d(TAG, "ATTEMPT:acquireLock() in " + functionNameForLog + "for clientId:" + this.mClientId);
        }
        if (!this.mTunerResourceManager.acquireLock(this.mClientId)) {
            Log.e(TAG, "FAILED:acquireLock() in " + functionNameForLog + " for clientId:" + this.mClientId + " - this can cause deadlock between Tuner API calls and onReclaimResources()");
        }
    }

    private void releaseTRMSLock() {
        this.mTunerResourceManager.releaseLock(this.mClientId);
    }
}
