package android.view;

import android.annotation.SystemApi;
import android.content.pm.PackageManager;
import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.gui.BorderSettings;
import android.gui.StalledTransactionInfo;
import android.hardware.DisplayLuts;
import android.hardware.HardwareBuffer;
import android.hardware.OverlayProperties;
import android.hardware.SyncFence;
import android.hardware.display.DeviceProductInfo;
import android.hardware.display.DisplayedContentSample;
import android.hardware.display.DisplayedContentSamplingAttributes;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.quality.PictureProfileHandle;
import android.os.Build;
import android.os.Debug;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.SemBlurInfo;
import android.view.Surface;
import android.view.SurfaceControl;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.android.internal.util.Preconditions;
import com.samsung.android.rune.CoreRune;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes4.dex */
public final class SurfaceControl implements Parcelable {
    public static final int BUFFER_TRANSFORM_IDENTITY = 0;
    public static final int BUFFER_TRANSFORM_MIRROR_HORIZONTAL = 1;
    public static final int BUFFER_TRANSFORM_MIRROR_VERTICAL = 2;
    public static final int BUFFER_TRANSFORM_ROTATE_180 = 3;
    public static final int BUFFER_TRANSFORM_ROTATE_270 = 7;
    public static final int BUFFER_TRANSFORM_ROTATE_90 = 4;
    public static final int CACHING_DISABLED = 0;
    public static final int CACHING_ENABLED = 1;
    public static final int CAN_OCCLUDE_PRESENTATION = 4096;
    public static final int CURSOR_WINDOW = 8192;
    public static final int DISABLE_SUPER_HDR = 268435456;
    public static final int DISPLAY_DECORATION = 512;
    public static final int DISPLAY_RECEIVES_INPUT = 1;
    public static final int ENABLE_BACKPRESSURE = 256;
    public static final int FRAME_RATE_SELECTION_STRATEGY_OVERRIDE_CHILDREN = 1;
    public static final int FRAME_RATE_SELECTION_STRATEGY_PROPAGATE = 0;
    public static final int FRAME_RATE_SELECTION_STRATEGY_SELF = 2;
    public static final int FX_SURFACE_BLAST = 262144;
    public static final int FX_SURFACE_CONTAINER = 524288;
    public static final int FX_SURFACE_EFFECT = 131072;
    public static final int FX_SURFACE_MASK = 983040;
    public static final int FX_SURFACE_NORMAL = 0;
    public static final int HIDDEN = 4;
    public static final int IGNORE_DESTINATION_FRAME = 1024;
    public static final int LAYER_IS_REFRESH_RATE_INDICATOR = 2048;
    public static final int METADATA_ACCESSIBILITY_ID = 5;
    public static final int METADATA_GAME_MODE = 8;
    public static final int METADATA_MOUSE_CURSOR = 4;
    public static final int METADATA_OWNER_PID = 6;
    public static final int METADATA_OWNER_UID = 1;
    public static final int METADATA_SEC_CAN_RECEIVE_INPUT = 31;
    public static final int METADATA_SEC_HDR_CUSTOM_DIM_RATIO = 34;
    public static final int METADATA_SEC_HDR_OFF = 33;
    public static final int METADATA_SEC_INTERNAL_ONLY = 32;
    public static final int METADATA_SEC_SURFACE_TYPE = 30;
    public static final int METADATA_TASK_ID = 3;
    public static final int METADATA_WINDOW_TYPE = 2;
    public static final int NON_PREMULTIPLIED = 256;
    public static final int NO_COLOR_FILL = 16384;
    public static final int NO_REMOTECONTROL = 15728640;
    public static final int OPAQUE = 1024;
    public static final int POWER_MODE_DOZE = 1;
    public static final int POWER_MODE_DOZE_SUSPEND = 3;
    public static final int POWER_MODE_NORMAL = 2;
    public static final int POWER_MODE_OFF = 0;
    public static final int POWER_MODE_ON_SUSPEND = 4;
    public static final int PROTECTED_APP = 2048;
    public static final int RECOVERABLE_FROM_BUFFER_STUFFING = 8192;
    public static final int SECURE = 128;
    public static final int SKIP_SCREENSHOT = 64;
    private static final int SURFACE_HIDDEN = 1;
    private static final int SURFACE_OPAQUE = 2;
    private static final String TAG = "SurfaceControl";
    private static volatile boolean sDebugUsageAfterRelease = false;
    private String mCallsite;
    private Choreographer mChoreographer;
    private final Object mChoreographerLock;
    private final CloseGuard mCloseGuard;
    private Runnable mFreeNativeResources;
    private int mHeight;
    private boolean mIsInsetsLeash;
    private WeakReference<View> mLocalOwnerView;
    private final Object mLock;
    private String mName;
    private long mNativeHandle;
    public long mNativeObject;
    private Throwable mReleaseStack;
    private ArrayList<OnReparentListener> mReparentListeners;
    private TrustedPresentationCallback mTrustedPresentationCallback;
    private int mWidth;
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(SurfaceControl.class.getClassLoader(), nativeGetNativeSurfaceControlFinalizer());
    public static final Parcelable.Creator<SurfaceControl> CREATOR = new Parcelable.Creator<SurfaceControl>() { // from class: android.view.SurfaceControl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurfaceControl createFromParcel(Parcel parcel) {
            return new SurfaceControl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SurfaceControl[] newArray(int i) {
            return new SurfaceControl[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface BufferTransform {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CachingHint {
    }

    public static final class CieXyz {
        public float X;
        public float Y;
        public float Z;
    }

    public static final class DisplayPrimaries {
        public CieXyz blue;
        public CieXyz green;
        public CieXyz red;
        public CieXyz white;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrameRateSelectionStrategy {
    }

    public interface OnJankDataListener {
        void onJankDataAvailable(List<JankData> list);
    }

    public interface OnReparentListener {
        void onReparent(Transaction transaction, SurfaceControl surfaceControl);
    }

    public interface TransactionCommittedListener {
        void onTransactionCommitted();
    }

    @Deprecated
    public static void closeTransaction() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNativeTrustedPresentationCallbackFinalizer();

    private static native void nativeAddJankDataListener(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddTransactionCommittedListener(long j, TransactionCommittedListener transactionCommittedListener);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddTransactionCompletedListener(long j, Consumer<TransactionStats> consumer);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddWindowInfosReportedListener(long j, Runnable runnable);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeApplyTransaction(long j, boolean z, boolean z2);

    private static native boolean nativeBootFinished();

    private static native boolean nativeClearAnimationFrameStats();

    private static native void nativeClearBootDisplayMode(IBinder iBinder);

    private static native boolean nativeClearContentFrameStats(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeClearTransaction(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeClearTrustedPresentationCallback(long j, long j2);

    private static native long nativeCopyFromSurfaceControl(long j);

    private static native long nativeCreate(SurfaceSession surfaceSession, String str, int i, int i2, int i3, int i4, long j, Parcel parcel) throws Surface.OutOfResourcesException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateJankDataListenerWrapper(long j, OnJankDataListener onJankDataListener);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateTpc(TrustedPresentationCallback trustedPresentationCallback);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateTransaction();

    private static native void nativeDisconnect(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeEnableDebugLogCallPoints(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeFlushJankData(long j);

    private static native boolean nativeGetAnimationFrameStats(WindowAnimationFrameStats windowAnimationFrameStats);

    private static native boolean nativeGetBootDisplayModeSupport();

    private static native int[] nativeGetCompositionDataspaces();

    private static native boolean nativeGetContentFrameStats(long j, WindowContentFrameStats windowContentFrameStats);

    /* JADX INFO: Access modifiers changed from: private */
    public static native IBinder nativeGetDefaultApplyToken();

    private static native DesiredDisplayModeSpecs nativeGetDesiredDisplayModeSpecs(IBinder iBinder);

    private static native boolean nativeGetDisplayBrightnessSupport(IBinder iBinder);

    private static native DisplayDecorationSupport nativeGetDisplayDecorationSupport(IBinder iBinder);

    private static native DisplayPrimaries nativeGetDisplayNativePrimaries(IBinder iBinder);

    private static native DisplayedContentSample nativeGetDisplayedContentSample(IBinder iBinder, long j, long j2);

    private static native DisplayedContentSamplingAttributes nativeGetDisplayedContentSamplingAttributes(IBinder iBinder);

    private static native DynamicDisplayInfo nativeGetDynamicDisplayInfo(long j);

    private static native int nativeGetGPUContextPriority();

    private static native long nativeGetHandle(long j);

    private static native IdleBeginTime nativeGetIdleBeginTime(IBinder iBinder);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetJankDataListenerWrapperFinalizer();

    private static native int nativeGetLayerId(long j);

    private static native int nativeGetMaxPictureProfiles();

    private static native long nativeGetNativeSurfaceControlFinalizer();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetNativeTransactionFinalizer();

    private static native OverlayProperties nativeGetOverlaySupport();

    private static native long[] nativeGetPhysicalDisplayIds();

    private static native IBinder nativeGetPhysicalDisplayToken(long j);

    private static native boolean nativeGetProtectedContentSupport();

    private static native StalledTransactionInfo nativeGetStalledTransactionInfo(int i);

    private static native StaticDisplayInfo nativeGetStaticDisplayInfo(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetTransactionId(long j);

    private static native int nativeGetTransformHint(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeMergeTransaction(long j, long j2);

    private static native long nativeMirrorSurface(long j);

    private static native long nativeMirrorSurfaceWithStopLayer(long j, long j2);

    private static native void nativeNotifyHFRmode(IBinder iBinder, int i);

    private static native void nativeNotifyShutdown();

    private static native long nativeReadFromParcel(Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeReadTransactionFromParcel(Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeRemoveCurrentInputFocus(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeRemoveJankDataListener(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeReparent(long j, long j2, long j3);

    private static native void nativeRestrictHighRefreshRate(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSanitize(long j, int i, int i2);

    private static native boolean nativeSetActiveColorMode(IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetAlpha(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetAnimationTransaction(long j);

    private static native void nativeSetAutoLowLatencyMode(IBinder iBinder, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBackgroundBlurColorCurve(long j, long j2, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBackgroundBlurRadius(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBlurRegions(long j, long j2, float[][] fArr, int i);

    private static native void nativeSetBootDisplayMode(IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBorderSettings(long j, long j2, Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBuffer(long j, long j2, HardwareBuffer hardwareBuffer, long j3, Consumer<SyncFence> consumer);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBufferTransform(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetCachingHint(long j, long j2, int i);

    private static native void nativeSetCanOccludePresentation(long j, long j2, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetClientDrawnCornerRadius(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetColor(long j, long j2, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetColorSpaceAgnostic(long j, long j2, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetColorTransform(long j, long j2, float[] fArr, float[] fArr2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetContentPriority(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetCornerRadius(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetCrop(long j, long j2, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDamageRegion(long j, long j2, Region region);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDataSpace(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDefaultApplyToken(IBinder iBinder);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDefaultFrameRateCompatibility(long j, long j2, int i);

    private static native boolean nativeSetDesiredDisplayModeSpecs(IBinder iBinder, DesiredDisplayModeSpecs desiredDisplayModeSpecs);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDesiredHdrHeadroom(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDesiredPresentTimeNanos(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDestinationFrame(long j, long j2, int i, int i2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDimmingEnabled(long j, long j2, boolean z);

    private static native boolean nativeSetDisplayBrightness(IBinder iBinder, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayFlags(long j, IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayLayerStack(long j, IBinder iBinder, int i);

    private static native void nativeSetDisplayPowerMode(IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayProjection(long j, IBinder iBinder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayReluminoEffect(long j, IBinder iBinder, float f, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplaySize(long j, IBinder iBinder, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplaySurface(long j, IBinder iBinder, long j2);

    private static native boolean nativeSetDisplayedContentSamplingEnabled(IBinder iBinder, boolean z, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDropInputMode(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetEarlyWakeupEnd(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetEarlyWakeupStart(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetEdgeExtensionEffect(long j, long j2, boolean z, boolean z2, boolean z3, boolean z4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetExtendedRangeBrightness(long j, long j2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFixedTransformHint(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFlags(long j, long j2, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFocusedWindow(long j, IBinder iBinder, String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRate(long j, long j2, float f, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRateCategory(long j, long j2, int i, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRateSelectionPriority(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRateSelectionStrategy(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameTimelineVsync(long j, long j2);

    private static native void nativeSetGameContentType(IBinder iBinder, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetGeometry(long j, long j2, Rect rect, Rect rect2, long j3);

    private static native void nativeSetGlobalShadowSettings(float[] fArr, float[] fArr2, float f, float f2, float f3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetInputWindowInfo(long j, long j2, InputWindowHandle inputWindowHandle);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetLayer(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetLayerStack(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetLuts(long j, long j2, float[] fArr, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetMatrix(long j, long j2, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetMetadata(long j, long j2, int i, Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetPictureProfileId(long j, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetPosition(long j, long j2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetRelativeLayer(long j, long j2, long j3, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetScale(long j, long j2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetShadowRadius(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetStretchEffect(long j, long j2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10);

    private static native void nativeSetTransformHint(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetTransparentRegionHint(long j, long j2, Region region);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetTrustedOverlay(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetTrustedPresentationCallback(long j, long j2, long j3, TrustedPresentationThresholds trustedPresentationThresholds);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetWindowCrop(long j, long j2, int i, int i2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeStartChangeResolution(long j, IBinder iBinder, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeStartSurfaceAnimation(long j, long j2, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSurfaceFlushJankData(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeUnsetBuffer(long j, long j2);

    private static native void nativeUpdateDefaultBufferSize(long j, int i, int i2);

    private static native void nativeWriteToParcel(long j, Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeWriteTransactionToParcel(long j, Parcel parcel);

    @Deprecated
    public static void openTransaction() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static int rotationToBufferTransform(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 4;
        }
        if (i == 2) {
            return 3;
        }
        if (i == 3) {
            return 7;
        }
        Log.e(TAG, "Trying to convert unknown rotation=" + i);
        return 0;
    }

    public static class JankData {
        public static final int JANK_APPLICATION = 2;
        public static final int JANK_COMPOSER = 1;
        public static final int JANK_NONE = 0;
        public static final int JANK_OTHER = 4;
        private final long mActualAppFrameTimeNs;
        private final long mFrameIntervalNs;
        private final long mFrameVsyncId;
        private final int mJankType;
        private final long mScheduledAppFrameTimeNs;

        @Retention(RetentionPolicy.SOURCE)
        public @interface JankType {
        }

        public JankData(long j, int i, long j2, long j3, long j4) {
            this.mFrameVsyncId = j;
            this.mJankType = i;
            this.mFrameIntervalNs = j2;
            this.mScheduledAppFrameTimeNs = j3;
            this.mActualAppFrameTimeNs = j4;
        }

        public long getVsyncId() {
            return this.mFrameVsyncId;
        }

        public int getJankType() {
            return this.mJankType;
        }

        public long getFrameIntervalNanos() {
            return this.mFrameIntervalNs;
        }

        public long getScheduledAppFrameTimeNanos() {
            return this.mScheduledAppFrameTimeNs;
        }

        public long getActualAppFrameTimeNanos() {
            return this.mActualAppFrameTimeNs;
        }

        public String toString() {
            return "JankData{vsync=" + this.mFrameVsyncId + ", jankType=0x" + Integer.toHexString(this.mJankType) + ", frameInterval=" + this.mFrameIntervalNs + "ns, scheduledAppTime=" + this.mScheduledAppFrameTimeNs + "ns, actualAppTime=" + this.mActualAppFrameTimeNs + "ns}";
        }
    }

    public static class OnJankDataListenerRegistration {
        public static final OnJankDataListenerRegistration NONE = new OnJankDataListenerRegistration() { // from class: android.view.SurfaceControl.OnJankDataListenerRegistration.1
            @Override // android.view.SurfaceControl.OnJankDataListenerRegistration
            public void flush() {
            }

            @Override // android.view.SurfaceControl.OnJankDataListenerRegistration
            public void release() {
            }

            @Override // android.view.SurfaceControl.OnJankDataListenerRegistration
            public void removeAfter(long j) {
            }
        };
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(OnJankDataListenerRegistration.class.getClassLoader(), SurfaceControl.nativeGetJankDataListenerWrapperFinalizer());
        private final Runnable mFreeNativeResources;
        private OnJankDataListener mListener;
        private final long mNativeObject;
        private boolean mRemoved;

        static /* synthetic */ void lambda$new$0() {
        }

        static /* synthetic */ void lambda$new$1() {
        }

        private OnJankDataListenerRegistration() {
            this.mRemoved = false;
            this.mNativeObject = 0L;
            this.mFreeNativeResources = new Runnable() { // from class: android.view.SurfaceControl$OnJankDataListenerRegistration$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceControl.OnJankDataListenerRegistration.lambda$new$0();
                }
            };
        }

        OnJankDataListenerRegistration(SurfaceControl surfaceControl, OnJankDataListener onJankDataListener) {
            Runnable runnableRegisterNativeAllocation;
            this.mRemoved = false;
            long jNativeCreateJankDataListenerWrapper = SurfaceControl.nativeCreateJankDataListenerWrapper(surfaceControl.mNativeObject, onJankDataListener);
            this.mNativeObject = jNativeCreateJankDataListenerWrapper;
            if (jNativeCreateJankDataListenerWrapper == 0) {
                runnableRegisterNativeAllocation = new Runnable() { // from class: android.view.SurfaceControl$OnJankDataListenerRegistration$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceControl.OnJankDataListenerRegistration.lambda$new$1();
                    }
                };
            } else {
                runnableRegisterNativeAllocation = sRegistry.registerNativeAllocation(this, jNativeCreateJankDataListenerWrapper);
            }
            this.mFreeNativeResources = runnableRegisterNativeAllocation;
            this.mListener = onJankDataListener;
        }

        public void flush() {
            SurfaceControl.nativeFlushJankData(this.mNativeObject);
        }

        public void removeAfter(long j) {
            this.mRemoved = true;
            SurfaceControl.nativeRemoveJankDataListener(this.mNativeObject, j);
        }

        public void release() {
            if (!this.mRemoved) {
                removeAfter(0L);
            }
            this.mListener = null;
            this.mFreeNativeResources.run();
        }
    }

    public boolean addOnReparentListener(OnReparentListener onReparentListener) {
        boolean zAdd;
        synchronized (this.mLock) {
            if (this.mReparentListeners == null) {
                this.mReparentListeners = new ArrayList<>(1);
            }
            zAdd = this.mReparentListeners.add(onReparentListener);
        }
        return zAdd;
    }

    public boolean removeOnReparentListener(OnReparentListener onReparentListener) {
        boolean zRemove;
        synchronized (this.mLock) {
            zRemove = this.mReparentListeners.remove(onReparentListener);
            if (this.mReparentListeners.isEmpty()) {
                this.mReparentListeners = null;
            }
        }
        return zRemove;
    }

    private void assignNativeObject(long j, String str) {
        if (this.mNativeObject != 0) {
            release();
        }
        if (j != 0) {
            this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, j);
        }
        this.mNativeObject = j;
        this.mNativeHandle = j != 0 ? nativeGetHandle(j) : 0L;
        if (sDebugUsageAfterRelease && this.mNativeObject == 0) {
            this.mReleaseStack = new Throwable("Assigned invalid nativeObject");
        } else {
            this.mReleaseStack = null;
        }
        if (CoreRune.FW_SURFACE_DEBUG_REMOVE) {
            Log.i(TAG, "assignNativeObject, mNativeObject=" + this.mNativeObject + ", sc=" + this + ", caller=" + Debug.getCallers(7));
        }
        setUnreleasedWarningCallSite(str);
        if (j != 0) {
            SurfaceControlRegistry.getProcessInstance().add(this);
        }
    }

    public void copyFrom(SurfaceControl surfaceControl, String str) {
        this.mName = surfaceControl.mName;
        this.mWidth = surfaceControl.mWidth;
        this.mHeight = surfaceControl.mHeight;
        if (CoreRune.FW_TEMP_TOO_MANY_INSETS_LEASH_BUG_FIX) {
            this.mIsInsetsLeash = surfaceControl.mIsInsetsLeash;
        }
        this.mLocalOwnerView = surfaceControl.mLocalOwnerView;
        assignNativeObject(nativeCopyFromSurfaceControl(surfaceControl.mNativeObject), str);
    }

    public static class Builder {
        private int mHeight;
        private WeakReference<View> mLocalOwnerView;
        private SparseIntArray mMetadata;
        private String mName;
        private SurfaceControl mParent;
        private SurfaceSession mSession;
        private int mWidth;
        private int mFlags = 4;
        private int mFormat = -1;
        private String mCallsite = "SurfaceControl.Builder";

        public Builder(SurfaceSession surfaceSession) {
            this.mSession = surfaceSession;
        }

        public Builder() {
        }

        public SurfaceControl build() {
            int i;
            int i2 = this.mWidth;
            if (i2 < 0 || (i = this.mHeight) < 0) {
                throw new IllegalStateException("width and height must be positive or unset");
            }
            if ((i2 > 0 || i > 0) && (isEffectLayer() || isContainerLayer())) {
                throw new IllegalStateException("Only buffer layers can set a valid buffer size.");
            }
            if (this.mName == null) {
                Log.w(SurfaceControl.TAG, "Missing name for SurfaceControl", new Throwable());
            }
            if ((this.mFlags & 983040) == 0) {
                setBLASTLayer();
            }
            return new SurfaceControl(this.mSession, this.mName, this.mWidth, this.mHeight, this.mFormat, this.mFlags, this.mParent, this.mMetadata, this.mLocalOwnerView, this.mCallsite);
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setLocalOwnerView(View view) {
            this.mLocalOwnerView = new WeakReference<>(view);
            return this;
        }

        public Builder setBufferSize(int i, int i2) {
            if (i < 0 || i2 < 0) {
                throw new IllegalArgumentException("width and height must be positive");
            }
            this.mWidth = i;
            this.mHeight = i2;
            return setFlags(0, 983040);
        }

        private void unsetBufferSize() {
            this.mWidth = 0;
            this.mHeight = 0;
        }

        public Builder setFormat(int i) {
            this.mFormat = i;
            return this;
        }

        public Builder setProtected(boolean z) {
            if (z) {
                this.mFlags |= 2048;
                return this;
            }
            this.mFlags &= -2049;
            return this;
        }

        public Builder setSecure(boolean z) {
            if (z) {
                this.mFlags |= 128;
                return this;
            }
            this.mFlags &= PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            return this;
        }

        public Builder setOpaque(boolean z) {
            if (z) {
                this.mFlags |= 1024;
                return this;
            }
            this.mFlags &= -1025;
            return this;
        }

        public Builder setHidden(boolean z) {
            if (z) {
                this.mFlags |= 4;
                return this;
            }
            this.mFlags &= -5;
            return this;
        }

        public Builder setParent(SurfaceControl surfaceControl) {
            this.mParent = surfaceControl;
            return this;
        }

        public Builder setMetadata(int i, int i2) {
            if (this.mMetadata == null) {
                this.mMetadata = new SparseIntArray();
            }
            this.mMetadata.put(i, i2);
            return this;
        }

        public Builder setEffectLayer() {
            this.mFlags |= 16384;
            unsetBufferSize();
            return setFlags(131072, 983040);
        }

        public Builder setColorLayer() {
            unsetBufferSize();
            return setFlags(131072, 983040);
        }

        private boolean isEffectLayer() {
            return (this.mFlags & 131072) == 131072;
        }

        public Builder setBLASTLayer() {
            return setFlags(262144, 983040);
        }

        public Builder setContainerLayer() {
            unsetBufferSize();
            return setFlags(524288, 983040);
        }

        private boolean isContainerLayer() {
            return (this.mFlags & 524288) == 524288;
        }

        public Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public Builder setCallsite(String str) {
            this.mCallsite = str;
            return this;
        }

        private Builder setFlags(int i, int i2) {
            this.mFlags = i | ((~i2) & this.mFlags);
            return this;
        }
    }

    private SurfaceControl(SurfaceSession surfaceSession, String str, int i, int i2, int i3, int i4, SurfaceControl surfaceControl, SparseIntArray sparseIntArray, WeakReference<View> weakReference, String str2) throws Surface.OutOfResourcesException, IllegalArgumentException {
        this.mCloseGuard = CloseGuard.get();
        this.mIsInsetsLeash = false;
        this.mChoreographerLock = new Object();
        this.mLock = new Object();
        this.mReleaseStack = null;
        if (str == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        this.mName = str;
        this.mWidth = i;
        this.mHeight = i2;
        this.mLocalOwnerView = weakReference;
        Parcel parcelObtain = Parcel.obtain();
        if (sparseIntArray != null) {
            try {
                if (sparseIntArray.size() > 0) {
                    parcelObtain.writeInt(sparseIntArray.size());
                    for (int i5 = 0; i5 < sparseIntArray.size(); i5++) {
                        parcelObtain.writeInt(sparseIntArray.keyAt(i5));
                        parcelObtain.writeByteArray(ByteBuffer.allocate(4).order(ByteOrder.nativeOrder()).putInt(sparseIntArray.valueAt(i5)).array());
                    }
                    parcelObtain.setDataPosition(0);
                }
            } catch (Throwable th) {
                parcelObtain.recycle();
                throw th;
            }
        }
        long jNativeCreate = nativeCreate(surfaceSession, str, i, i2, i3, i4, surfaceControl != null ? surfaceControl.mNativeObject : 0L, parcelObtain);
        parcelObtain.recycle();
        if (jNativeCreate == 0) {
            throw new Surface.OutOfResourcesException("Couldn't allocate SurfaceControl native object");
        }
        assignNativeObject(jNativeCreate, str2);
        if (CoreRune.FW_SURFACE_DEBUG_CREATION) {
            SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("SurfaceControl is created", null, this, null, true);
        }
    }

    public SurfaceControl(SurfaceControl surfaceControl, String str) {
        this.mCloseGuard = CloseGuard.get();
        this.mIsInsetsLeash = false;
        this.mChoreographerLock = new Object();
        this.mLock = new Object();
        this.mReleaseStack = null;
        copyFrom(surfaceControl, str);
    }

    private SurfaceControl(Parcel parcel) {
        this.mCloseGuard = CloseGuard.get();
        this.mIsInsetsLeash = false;
        this.mChoreographerLock = new Object();
        this.mLock = new Object();
        this.mReleaseStack = null;
        readFromParcel(parcel);
    }

    public SurfaceControl() {
        this.mCloseGuard = CloseGuard.get();
        this.mIsInsetsLeash = false;
        this.mChoreographerLock = new Object();
        this.mLock = new Object();
        this.mReleaseStack = null;
    }

    public void setIsInsetsLeash() {
        this.mIsInsetsLeash = true;
    }

    public boolean isInsetsLeash() {
        return this.mIsInsetsLeash;
    }

    public void readFromParcel(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        this.mName = parcel.readString8();
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        if (CoreRune.FW_TEMP_TOO_MANY_INSETS_LEASH_BUG_FIX) {
            this.mIsInsetsLeash = parcel.readBoolean();
        }
        assignNativeObject(parcel.readInt() != 0 ? nativeReadFromParcel(parcel) : 0L, "readFromParcel");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (sDebugUsageAfterRelease) {
            checkNotReleased();
        }
        parcel.writeString8(this.mName);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        if (CoreRune.FW_TEMP_TOO_MANY_INSETS_LEASH_BUG_FIX) {
            parcel.writeBoolean(this.mIsInsetsLeash);
        }
        if (this.mNativeObject == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
        }
        nativeWriteToParcel(this.mNativeObject, parcel);
        if ((i & 1) != 0) {
            release();
        }
    }

    public static void setDebugUsageAfterRelease(boolean z) {
        if (Build.isDebuggable()) {
            sDebugUsageAfterRelease = z;
        }
    }

    public void setUnreleasedWarningCallSite(String str) {
        if (isValid()) {
            this.mCloseGuard.openWithCallSite("release", str);
            this.mCallsite = str;
        }
    }

    String getCallsite() {
        return this.mCallsite;
    }

    String getName() {
        return this.mName;
    }

    public boolean isSameSurface(SurfaceControl surfaceControl) {
        return surfaceControl.mNativeHandle == this.mNativeHandle;
    }

    public Choreographer getChoreographer() {
        checkNotReleased();
        synchronized (this.mChoreographerLock) {
            Choreographer choreographer = this.mChoreographer;
            if (choreographer != null) {
                return choreographer;
            }
            return getChoreographer(Looper.myLooper());
        }
    }

    public Choreographer getChoreographer(Looper looper) {
        Choreographer choreographer;
        checkNotReleased();
        synchronized (this.mChoreographerLock) {
            Choreographer choreographer2 = this.mChoreographer;
            if (choreographer2 == null) {
                this.mChoreographer = Choreographer.getInstanceForSurfaceControl(this.mNativeHandle, looper);
            } else if (!choreographer2.isTheLooperSame(looper)) {
                throw new IllegalStateException("Choreographer already exists with a different looper");
            }
            choreographer = this.mChoreographer;
        }
        return choreographer;
    }

    public boolean hasChoreographer() {
        boolean z;
        synchronized (this.mChoreographerLock) {
            z = this.mChoreographer != null;
        }
        return z;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        protoOutputStream.write(1138166333442L, this.mName);
        protoOutputStream.write(1120986464259L, getLayerId());
        protoOutputStream.end(jStart);
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            SurfaceControlRegistry.getProcessInstance().remove(this);
        } finally {
            super.finalize();
        }
    }

    public void release() {
        if (this.mNativeObject != 0) {
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("release", null, this, null);
            }
            if (CoreRune.FW_SURFACE_DEBUG_REMOVE) {
                Log.i(TAG, "release, mNativeObject=" + this.mNativeObject + ", sc=" + this + ", caller=" + Debug.getCallers(7));
            }
            this.mFreeNativeResources.run();
            this.mNativeObject = 0L;
            this.mNativeHandle = 0L;
            this.mReleaseStack = new Throwable("Released");
            this.mCloseGuard.close();
            synchronized (this.mChoreographerLock) {
                Choreographer choreographer = this.mChoreographer;
                if (choreographer != null) {
                    choreographer.invalidate();
                    this.mChoreographer = null;
                }
            }
            SurfaceControlRegistry.getProcessInstance().remove(this);
        }
    }

    public void disconnect() {
        long j = this.mNativeObject;
        if (j != 0) {
            nativeDisconnect(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkNotReleased() {
        if (this.mNativeObject == 0) {
            if (this.mReleaseStack != null) {
                throw new IllegalStateException("Invalid usage after release of " + this, this.mReleaseStack);
            }
            throw new NullPointerException("mNativeObject of " + this + " is null. Have you called release() already?");
        }
    }

    public boolean isValid() {
        return this.mNativeObject != 0;
    }

    public boolean clearContentFrameStats() {
        checkNotReleased();
        return nativeClearContentFrameStats(this.mNativeObject);
    }

    public boolean getContentFrameStats(WindowContentFrameStats windowContentFrameStats) {
        checkNotReleased();
        return nativeGetContentFrameStats(this.mNativeObject, windowContentFrameStats);
    }

    public static boolean clearAnimationFrameStats() {
        return nativeClearAnimationFrameStats();
    }

    public static boolean getAnimationFrameStats(WindowAnimationFrameStats windowAnimationFrameStats) {
        return nativeGetAnimationFrameStats(windowAnimationFrameStats);
    }

    public int getWidth() {
        int i;
        synchronized (this.mLock) {
            i = this.mWidth;
        }
        return i;
    }

    public int getHeight() {
        int i;
        synchronized (this.mLock) {
            i = this.mHeight;
        }
        return i;
    }

    public View getLocalOwnerView() {
        WeakReference<View> weakReference = this.mLocalOwnerView;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public String toString() {
        return "Surface(name=" + this.mName + ")/@0x" + Integer.toHexString(System.identityHashCode(this));
    }

    public static final class StaticDisplayInfo {
        public float density;
        public DeviceProductInfo deviceProductInfo;
        public int installOrientation;
        public boolean isInternal;
        public boolean secure;

        public String toString() {
            return "StaticDisplayInfo{isInternal=" + this.isInternal + ", density=" + this.density + ", secure=" + this.secure + ", deviceProductInfo=" + this.deviceProductInfo + ", installOrientation=" + this.installOrientation + "}";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                StaticDisplayInfo staticDisplayInfo = (StaticDisplayInfo) obj;
                if (this.isInternal == staticDisplayInfo.isInternal && this.density == staticDisplayInfo.density && this.secure == staticDisplayInfo.secure && Objects.equals(this.deviceProductInfo, staticDisplayInfo.deviceProductInfo) && this.installOrientation == staticDisplayInfo.installOrientation) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Boolean.valueOf(this.isInternal), Float.valueOf(this.density), Boolean.valueOf(this.secure), this.deviceProductInfo, Integer.valueOf(this.installOrientation));
        }
    }

    public static final class DynamicDisplayInfo {
        public int activeColorMode;
        public int activeDisplayModeId;
        public boolean autoLowLatencyModeSupported;
        public FrameRateCategoryRate frameRateCategoryRate;
        public boolean gameContentTypeSupported;
        public boolean hasArrSupport;
        public Display.HdrCapabilities hdrCapabilities;
        public int preferredBootDisplayMode;
        public float renderFrameRate;
        public int[] supportedColorModes;
        public DisplayMode[] supportedDisplayModes;
        public float[] supportedRefreshRates;

        public String toString() {
            return "DynamicDisplayInfo{supportedDisplayModes=" + Arrays.toString(this.supportedDisplayModes) + ", activeDisplayModeId=" + this.activeDisplayModeId + ", renderFrameRate=" + this.renderFrameRate + ", hasArrSupport=" + this.hasArrSupport + ", frameRateCategoryRate=" + this.frameRateCategoryRate + ", supportedRefreshRates=" + Arrays.toString(this.supportedRefreshRates) + ", supportedColorModes=" + Arrays.toString(this.supportedColorModes) + ", activeColorMode=" + this.activeColorMode + ", hdrCapabilities=" + this.hdrCapabilities + ", autoLowLatencyModeSupported=" + this.autoLowLatencyModeSupported + ", gameContentTypeSupported" + this.gameContentTypeSupported + ", preferredBootDisplayMode" + this.preferredBootDisplayMode + "}";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                DynamicDisplayInfo dynamicDisplayInfo = (DynamicDisplayInfo) obj;
                if (Arrays.equals(this.supportedDisplayModes, dynamicDisplayInfo.supportedDisplayModes) && this.activeDisplayModeId == dynamicDisplayInfo.activeDisplayModeId && this.renderFrameRate == dynamicDisplayInfo.renderFrameRate && Arrays.equals(this.supportedColorModes, dynamicDisplayInfo.supportedColorModes) && this.activeColorMode == dynamicDisplayInfo.activeColorMode && Objects.equals(this.hdrCapabilities, dynamicDisplayInfo.hdrCapabilities) && this.preferredBootDisplayMode == dynamicDisplayInfo.preferredBootDisplayMode && this.hasArrSupport == dynamicDisplayInfo.hasArrSupport && Objects.equals(this.frameRateCategoryRate, dynamicDisplayInfo.frameRateCategoryRate) && Arrays.equals(this.supportedRefreshRates, dynamicDisplayInfo.supportedRefreshRates)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(Arrays.hashCode(this.supportedDisplayModes)), Integer.valueOf(this.activeDisplayModeId), Float.valueOf(this.renderFrameRate), Integer.valueOf(this.activeColorMode), this.hdrCapabilities, Boolean.valueOf(this.hasArrSupport), this.frameRateCategoryRate, Integer.valueOf(Arrays.hashCode(this.supportedRefreshRates)));
        }
    }

    public static final class DisplayMode {
        public long appVsyncOffsetNanos;
        public int group;
        public int height;
        public int id;
        public float peakRefreshRate;
        public long presentationDeadlineNanos;
        public int[] supportedHdrTypes;
        public float vsyncRate;
        public int width;
        public float xDpi;
        public float yDpi;

        public String toString() {
            return "DisplayMode{id=" + this.id + ", width=" + this.width + ", height=" + this.height + ", xDpi=" + this.xDpi + ", yDpi=" + this.yDpi + ", peakRefreshRate=" + this.peakRefreshRate + ", vsyncRate=" + this.vsyncRate + ", appVsyncOffsetNanos=" + this.appVsyncOffsetNanos + ", presentationDeadlineNanos=" + this.presentationDeadlineNanos + ", supportedHdrTypes=" + Arrays.toString(this.supportedHdrTypes) + ", group=" + this.group + "}";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                DisplayMode displayMode = (DisplayMode) obj;
                if (this.id == displayMode.id && this.width == displayMode.width && this.height == displayMode.height && Float.compare(displayMode.xDpi, this.xDpi) == 0 && Float.compare(displayMode.yDpi, this.yDpi) == 0 && Float.compare(displayMode.peakRefreshRate, this.peakRefreshRate) == 0 && Float.compare(displayMode.vsyncRate, this.vsyncRate) == 0 && this.appVsyncOffsetNanos == displayMode.appVsyncOffsetNanos && this.presentationDeadlineNanos == displayMode.presentationDeadlineNanos && Arrays.equals(this.supportedHdrTypes, displayMode.supportedHdrTypes) && this.group == displayMode.group) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.id), Integer.valueOf(this.width), Integer.valueOf(this.height), Float.valueOf(this.xDpi), Float.valueOf(this.yDpi), Float.valueOf(this.peakRefreshRate), Float.valueOf(this.vsyncRate), Long.valueOf(this.appVsyncOffsetNanos), Long.valueOf(this.presentationDeadlineNanos), Integer.valueOf(this.group), Integer.valueOf(Arrays.hashCode(this.supportedHdrTypes)));
        }
    }

    public static void setDisplayPowerMode(IBinder iBinder, int i) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetDisplayPowerMode(iBinder, i);
    }

    public static StaticDisplayInfo getStaticDisplayInfo(long j) {
        return nativeGetStaticDisplayInfo(j);
    }

    public static DynamicDisplayInfo getDynamicDisplayInfo(long j) {
        return nativeGetDynamicDisplayInfo(j);
    }

    public static DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(IBinder iBinder) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDisplayedContentSamplingAttributes(iBinder);
    }

    public static boolean setDisplayedContentSamplingEnabled(IBinder iBinder, boolean z, int i, int i2) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        if ((i >> 4) != 0) {
            throw new IllegalArgumentException("invalid componentMask when enabling sampling");
        }
        return nativeSetDisplayedContentSamplingEnabled(iBinder, z, i, i2);
    }

    public static DisplayedContentSample getDisplayedContentSample(IBinder iBinder, long j, long j2) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDisplayedContentSample(iBinder, j, j2);
    }

    public static final class RefreshRateRange implements Parcelable {
        public static final Parcelable.Creator<RefreshRateRange> CREATOR = new Parcelable.Creator<RefreshRateRange>() { // from class: android.view.SurfaceControl.RefreshRateRange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RefreshRateRange createFromParcel(Parcel parcel) {
                return new RefreshRateRange(parcel.readFloat(), parcel.readFloat());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RefreshRateRange[] newArray(int i) {
                return new RefreshRateRange[i];
            }
        };
        public static final float FLOAT_TOLERANCE = 0.01f;
        public static final String TAG = "RefreshRateRange";
        public float max;
        public float min;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public RefreshRateRange() {
        }

        public RefreshRateRange(float f, float f2) {
            if (f < 0.0f || f2 < 0.0f || f > 0.01f + f2) {
                Slog.e(TAG, "Wrong values for min and max when initializing RefreshRateRange : " + f + " " + f2);
                this.max = 0.0f;
                this.min = 0.0f;
                return;
            }
            if (f > f2) {
                f2 = f;
                f = f2;
            }
            this.min = f;
            this.max = f2;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RefreshRateRange)) {
                return false;
            }
            RefreshRateRange refreshRateRange = (RefreshRateRange) obj;
            return this.min == refreshRateRange.min && this.max == refreshRateRange.max;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.min), Float.valueOf(this.max));
        }

        public String toString() {
            return NavigationBarInflaterView.KEY_CODE_START + this.min + " " + this.max + NavigationBarInflaterView.KEY_CODE_END;
        }

        public void copyFrom(RefreshRateRange refreshRateRange) {
            this.min = refreshRateRange.min;
            this.max = refreshRateRange.max;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeFloat(this.min);
            parcel.writeFloat(this.max);
        }
    }

    public static final class RefreshRateRanges {
        public static final String TAG = "RefreshRateRanges";
        public final RefreshRateRange physical;
        public final RefreshRateRange render;

        public RefreshRateRanges() {
            this.physical = new RefreshRateRange();
            this.render = new RefreshRateRange();
        }

        public RefreshRateRanges(RefreshRateRange refreshRateRange, RefreshRateRange refreshRateRange2) {
            this.physical = new RefreshRateRange(refreshRateRange.min, refreshRateRange.max);
            this.render = new RefreshRateRange(refreshRateRange2.min, refreshRateRange2.max);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RefreshRateRanges)) {
                return false;
            }
            RefreshRateRanges refreshRateRanges = (RefreshRateRanges) obj;
            return this.physical.equals(refreshRateRanges.physical) && this.render.equals(refreshRateRanges.render);
        }

        public int hashCode() {
            return Objects.hash(this.physical, this.render);
        }

        public String toString() {
            return "physical: " + this.physical + " render:  " + this.render;
        }

        public void copyFrom(RefreshRateRanges refreshRateRanges) {
            this.physical.copyFrom(refreshRateRanges.physical);
            this.render.copyFrom(refreshRateRanges.render);
        }
    }

    public static final class IdleScreenRefreshRateConfig {
        public int timeoutMillis;

        public IdleScreenRefreshRateConfig() {
            this.timeoutMillis = -1;
        }

        public IdleScreenRefreshRateConfig(int i) {
            this.timeoutMillis = i;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return (obj instanceof IdleScreenRefreshRateConfig) && obj != null && this.timeoutMillis == ((IdleScreenRefreshRateConfig) obj).timeoutMillis;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.timeoutMillis));
        }

        public String toString() {
            return "timeoutMillis: " + this.timeoutMillis;
        }

        public void copyFrom(IdleScreenRefreshRateConfig idleScreenRefreshRateConfig) {
            if (idleScreenRefreshRateConfig != null) {
                this.timeoutMillis = idleScreenRefreshRateConfig.timeoutMillis;
            }
        }
    }

    public static final class DesiredDisplayModeSpecs {
        public boolean allowGroupSwitching;
        public final RefreshRateRanges appRequestRanges;
        public int defaultMode;
        public IdleScreenRefreshRateConfig idleScreenRefreshRateConfig;
        public final RefreshRateRanges primaryRanges;

        public int hashCode() {
            return 0;
        }

        public DesiredDisplayModeSpecs() {
            this.primaryRanges = new RefreshRateRanges();
            this.appRequestRanges = new RefreshRateRanges();
        }

        public DesiredDisplayModeSpecs(DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            this.primaryRanges = new RefreshRateRanges();
            this.appRequestRanges = new RefreshRateRanges();
            copyFrom(desiredDisplayModeSpecs);
        }

        public DesiredDisplayModeSpecs(int i, boolean z, RefreshRateRanges refreshRateRanges, RefreshRateRanges refreshRateRanges2, IdleScreenRefreshRateConfig idleScreenRefreshRateConfig) {
            this.defaultMode = i;
            this.allowGroupSwitching = z;
            this.primaryRanges = new RefreshRateRanges(refreshRateRanges.physical, refreshRateRanges.render);
            this.appRequestRanges = new RefreshRateRanges(refreshRateRanges2.physical, refreshRateRanges2.render);
            this.idleScreenRefreshRateConfig = idleScreenRefreshRateConfig == null ? null : new IdleScreenRefreshRateConfig(idleScreenRefreshRateConfig.timeoutMillis);
        }

        public boolean equals(Object obj) {
            return (obj instanceof DesiredDisplayModeSpecs) && equals((DesiredDisplayModeSpecs) obj);
        }

        public boolean equals(DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            return desiredDisplayModeSpecs != null && this.defaultMode == desiredDisplayModeSpecs.defaultMode && this.allowGroupSwitching == desiredDisplayModeSpecs.allowGroupSwitching && this.primaryRanges.equals(desiredDisplayModeSpecs.primaryRanges) && this.appRequestRanges.equals(desiredDisplayModeSpecs.appRequestRanges) && Objects.equals(this.idleScreenRefreshRateConfig, desiredDisplayModeSpecs.idleScreenRefreshRateConfig);
        }

        public void copyFrom(DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            this.defaultMode = desiredDisplayModeSpecs.defaultMode;
            this.allowGroupSwitching = desiredDisplayModeSpecs.allowGroupSwitching;
            this.primaryRanges.copyFrom(desiredDisplayModeSpecs.primaryRanges);
            this.appRequestRanges.copyFrom(desiredDisplayModeSpecs.appRequestRanges);
            copyIdleScreenRefreshRateConfig(desiredDisplayModeSpecs.idleScreenRefreshRateConfig);
        }

        public String toString() {
            return "defaultMode=" + this.defaultMode + " allowGroupSwitching=" + this.allowGroupSwitching + " primaryRanges=" + this.primaryRanges + " appRequestRanges=" + this.appRequestRanges + " idleScreenRefreshRate=" + String.valueOf(this.idleScreenRefreshRateConfig);
        }

        private void copyIdleScreenRefreshRateConfig(IdleScreenRefreshRateConfig idleScreenRefreshRateConfig) {
            IdleScreenRefreshRateConfig idleScreenRefreshRateConfig2 = this.idleScreenRefreshRateConfig;
            if (idleScreenRefreshRateConfig2 == null) {
                if (idleScreenRefreshRateConfig != null) {
                    this.idleScreenRefreshRateConfig = new IdleScreenRefreshRateConfig(idleScreenRefreshRateConfig.timeoutMillis);
                }
            } else if (idleScreenRefreshRateConfig == null) {
                this.idleScreenRefreshRateConfig = null;
            } else {
                idleScreenRefreshRateConfig2.copyFrom(idleScreenRefreshRateConfig);
            }
        }
    }

    public static boolean setDesiredDisplayModeSpecs(IBinder iBinder, DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        if (desiredDisplayModeSpecs == null) {
            throw new IllegalArgumentException("desiredDisplayModeSpecs must not be null");
        }
        if (desiredDisplayModeSpecs.defaultMode < 0) {
            throw new IllegalArgumentException("defaultMode must be non-negative");
        }
        return nativeSetDesiredDisplayModeSpecs(iBinder, desiredDisplayModeSpecs);
    }

    public static DesiredDisplayModeSpecs getDesiredDisplayModeSpecs(IBinder iBinder) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDesiredDisplayModeSpecs(iBinder);
    }

    public static DisplayPrimaries getDisplayNativePrimaries(IBinder iBinder) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDisplayNativePrimaries(iBinder);
    }

    public static boolean setActiveColorMode(IBinder iBinder, int i) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeSetActiveColorMode(iBinder, i);
    }

    public static ColorSpace[] getCompositionColorSpaces() {
        int[] iArrNativeGetCompositionDataspaces = nativeGetCompositionDataspaces();
        ColorSpace colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        ColorSpace[] colorSpaceArr = new ColorSpace[2];
        colorSpaceArr[0] = colorSpace;
        colorSpaceArr[1] = colorSpace;
        if (iArrNativeGetCompositionDataspaces != null && iArrNativeGetCompositionDataspaces.length == 2) {
            for (int i = 0; i < 2; i++) {
                ColorSpace fromDataSpace = ColorSpace.getFromDataSpace(iArrNativeGetCompositionDataspaces[i]);
                if (fromDataSpace != null) {
                    colorSpaceArr[i] = fromDataSpace;
                }
            }
        }
        return colorSpaceArr;
    }

    public static OverlayProperties getOverlaySupport() {
        return nativeGetOverlaySupport();
    }

    public static boolean getBootDisplayModeSupport() {
        return nativeGetBootDisplayModeSupport();
    }

    public static void setBootDisplayMode(IBinder iBinder, int i) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetBootDisplayMode(iBinder, i);
    }

    public static void clearBootDisplayMode(IBinder iBinder) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeClearBootDisplayMode(iBinder);
    }

    public static void setAutoLowLatencyMode(IBinder iBinder, boolean z) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetAutoLowLatencyMode(iBinder, z);
    }

    public static void setGameContentType(IBinder iBinder, boolean z) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeSetGameContentType(iBinder, z);
    }

    public static long[] getPhysicalDisplayIds() {
        return nativeGetPhysicalDisplayIds();
    }

    public static IBinder getPhysicalDisplayToken(long j) {
        return nativeGetPhysicalDisplayToken(j);
    }

    public static boolean getProtectedContentSupport() {
        return nativeGetProtectedContentSupport();
    }

    public static boolean getDisplayBrightnessSupport(IBinder iBinder) {
        return nativeGetDisplayBrightnessSupport(iBinder);
    }

    public static boolean setDisplayBrightness(IBinder iBinder, float f) {
        return setDisplayBrightness(iBinder, f, -1.0f, f, -1.0f);
    }

    public static boolean setDisplayBrightness(IBinder iBinder, float f, float f2, float f3, float f4) {
        Objects.requireNonNull(iBinder);
        if (Float.isNaN(f3) || f3 > 1.0f || (f3 < 0.0f && f3 != -1.0f)) {
            throw new IllegalArgumentException("displayBrightness must be a number between 0.0f  and 1.0f, or -1 to turn the backlight off: " + f3);
        }
        if (Float.isNaN(f) || f > 1.0f || (f < 0.0f && f != -1.0f)) {
            throw new IllegalArgumentException("sdrBrightness must be a number between 0.0f and 1.0f, or -1 to turn the backlight off: " + f);
        }
        return nativeSetDisplayBrightness(iBinder, f, f2, f3, f4);
    }

    public static SurfaceControl mirrorSurface(SurfaceControl surfaceControl) {
        return mirrorSurface(surfaceControl, null);
    }

    public static SurfaceControl mirrorSurface(SurfaceControl surfaceControl, SurfaceControl surfaceControl2) {
        long jNativeMirrorSurfaceWithStopLayer = nativeMirrorSurfaceWithStopLayer(surfaceControl.mNativeObject, surfaceControl2 != null ? surfaceControl2.mNativeObject : 0L);
        SurfaceControl surfaceControl3 = new SurfaceControl();
        surfaceControl3.mName = surfaceControl.mName + " (mirror)";
        surfaceControl3.assignNativeObject(jNativeMirrorSurfaceWithStopLayer, "mirrorSurface");
        return surfaceControl3;
    }

    private static void validateColorArg(float[] fArr) {
        if (fArr.length != 4) {
            throw new IllegalArgumentException("Color must be specified as a float array with four values to represent r, g, b, a in range [0..1]");
        }
        for (float f : fArr) {
            if (f < 0.0f || f > 1.0f) {
                throw new IllegalArgumentException("Color must be specified as a float array with four values to represent r, g, b, a in range [0..1]");
            }
        }
    }

    public static void setGlobalShadowSettings(float[] fArr, float[] fArr2, float f, float f2, float f3) {
        validateColorArg(fArr);
        validateColorArg(fArr2);
        nativeSetGlobalShadowSettings(fArr, fArr2, f, f2, f3);
    }

    public static DisplayDecorationSupport getDisplayDecorationSupport(IBinder iBinder) {
        return nativeGetDisplayDecorationSupport(iBinder);
    }

    public OnJankDataListenerRegistration addOnJankDataListener(OnJankDataListener onJankDataListener) {
        return new OnJankDataListenerRegistration(this, onJankDataListener);
    }

    public static int getGPUContextPriority() {
        return nativeGetGPUContextPriority();
    }

    public static final class IdleBeginTime {
        public long beginTimeIdle;

        public String toString() {
            return "TimerInfo {beginTimeIdle=" + this.beginTimeIdle + "}";
        }
    }

    public static IdleBeginTime getIdleBeginTime(IBinder iBinder) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetIdleBeginTime(iBinder);
    }

    public static boolean bootFinished() {
        return nativeBootFinished();
    }

    public static int getMaxPictureProfiles() {
        return nativeGetMaxPictureProfiles();
    }

    public static final class TransactionStats {
        private long mLatchTimeNanos;
        private SyncFence mSyncFence;

        private TransactionStats(long j, long j2) {
            this.mLatchTimeNanos = j;
            this.mSyncFence = new SyncFence(j2);
        }

        public void close() {
            this.mSyncFence.close();
        }

        public long getLatchTimeNanos() {
            return this.mLatchTimeNanos;
        }

        public SyncFence getPresentFence() {
            return new SyncFence(this.mSyncFence);
        }
    }

    @Deprecated
    public static final class TrustedPresentationThresholds {
        private final float mMinAlpha;
        private final float mMinFractionRendered;
        private final int mStabilityRequirementMs;

        public TrustedPresentationThresholds(float f, float f2, int i) {
            this.mMinAlpha = f;
            this.mMinFractionRendered = f2;
            this.mStabilityRequirementMs = i;
            checkValid();
        }

        private void checkValid() {
            if (this.mMinAlpha <= 0.0f || this.mMinFractionRendered <= 0.0f || this.mStabilityRequirementMs < 1) {
                throw new IllegalArgumentException("TrustedPresentationThresholds values are invalid");
            }
        }
    }

    public static abstract class TrustedPresentationCallback {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(TrustedPresentationCallback.class.getClassLoader(), SurfaceControl.getNativeTrustedPresentationCallbackFinalizer());
        private final Runnable mFreeNativeResources;
        private final long mNativeObject;

        public abstract void onTrustedPresentationChanged(boolean z);

        private TrustedPresentationCallback() {
            long jNativeCreateTpc = SurfaceControl.nativeCreateTpc(this);
            this.mNativeObject = jNativeCreateTpc;
            this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, jNativeCreateTpc);
        }
    }

    public static class Transaction implements Closeable, Parcelable {
        ArrayList<String> mCalls;
        public String mDebugName;
        Runnable mFreeNativeResources;
        public String mLowDebugName;
        public long mNativeObject;
        private final ArrayMap<SurfaceControl, SurfaceControl> mReparentedSurfaces;
        private final ArrayMap<SurfaceControl, Point> mResizedSurfaces;
        public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(Transaction.class.getClassLoader(), SurfaceControl.nativeGetNativeTransactionFinalizer(), 512);
        private static final float[] INVALID_COLOR = {-1.0f, -1.0f, -1.0f};
        public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() { // from class: android.view.SurfaceControl.Transaction.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Transaction createFromParcel(Parcel parcel) {
                return new Transaction(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Transaction[] newArray(int i) {
                return new Transaction[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public void addDebugName(String str) {
            if (TextUtils.isEmpty(this.mDebugName)) {
                this.mDebugName = str;
                return;
            }
            this.mDebugName += " " + str;
        }

        public void addLowDebugName(String str) {
            if (TextUtils.isEmpty(this.mLowDebugName)) {
                this.mLowDebugName = str;
                return;
            }
            this.mLowDebugName += " " + str;
        }

        protected void checkPreconditions(SurfaceControl surfaceControl) {
            surfaceControl.checkNotReleased();
        }

        public Transaction() {
            this(SurfaceControl.nativeCreateTransaction());
        }

        private Transaction(long j) {
            this.mResizedSurfaces = new ArrayMap<>();
            this.mReparentedSurfaces = new ArrayMap<>();
            this.mNativeObject = j;
            this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, j);
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                this.mDebugName = null;
                this.mLowDebugName = null;
            }
            setUpForSurfaceControlRegistry();
        }

        private Transaction(Parcel parcel) {
            this.mResizedSurfaces = new ArrayMap<>();
            this.mReparentedSurfaces = new ArrayMap<>();
            readFromParcel(parcel);
            setUpForSurfaceControlRegistry();
        }

        private void setUpForSurfaceControlRegistry() {
            if (!SurfaceControlRegistry.sCallStackDebuggingInitialized) {
                SurfaceControlRegistry.initializeCallStackDebugging();
            }
            this.mCalls = SurfaceControlRegistry.sLogAllTxCallsOnApply ? new ArrayList<>() : null;
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("ctor", this, null, null);
            }
        }

        public static void setDefaultApplyToken(IBinder iBinder) {
            if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                Log.d(SurfaceControl.TAG, "setDefaultApplyToken, caller=" + Debug.getCallers(5));
            }
            SurfaceControl.nativeSetDefaultApplyToken(iBinder);
        }

        public static IBinder getDefaultApplyToken() {
            return SurfaceControl.nativeGetDefaultApplyToken();
        }

        public void apply() {
            apply(false);
        }

        public void applyAsyncUnsafe() {
            apply(false, true);
        }

        public void clear() {
            this.mResizedSurfaces.clear();
            this.mReparentedSurfaces.clear();
            long j = this.mNativeObject;
            if (j != 0) {
                SurfaceControl.nativeClearTransaction(j);
            }
            ArrayList<String> arrayList = this.mCalls;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.mResizedSurfaces.clear();
            this.mReparentedSurfaces.clear();
            this.mFreeNativeResources.run();
            this.mNativeObject = 0L;
            ArrayList<String> arrayList = this.mCalls;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        public void apply(boolean z) {
            apply(z, false);
        }

        private void apply(boolean z, boolean z2) {
            Transaction transaction;
            applyResizedSurfaces();
            notifyReparentedSurfaces();
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || (CoreRune.FW_SURFACE_DEBUG_APPLY && !(TextUtils.isEmpty(this.mDebugName) && TextUtils.isEmpty(this.mLowDebugName)))) {
                transaction = this;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("apply", transaction, null, null, CoreRune.FW_SURFACE_DEBUG_APPLY && !TextUtils.isEmpty(this.mDebugName));
                if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                    if (!TextUtils.isEmpty(transaction.mLowDebugName)) {
                        Log.i(SurfaceControl.TAG, "apply, lowDebugName=" + transaction.mLowDebugName + ", caller=" + Debug.getCallers(6));
                    }
                    transaction.mDebugName = null;
                    transaction.mLowDebugName = null;
                }
            } else {
                transaction = this;
            }
            SurfaceControl.nativeApplyTransaction(transaction.mNativeObject, z, z2);
        }

        protected void applyResizedSurfaces() {
            for (int size = this.mResizedSurfaces.size() - 1; size >= 0; size--) {
                Point pointValueAt = this.mResizedSurfaces.valueAt(size);
                SurfaceControl surfaceControlKeyAt = this.mResizedSurfaces.keyAt(size);
                synchronized (surfaceControlKeyAt.mLock) {
                    surfaceControlKeyAt.resize(pointValueAt.x, pointValueAt.y);
                }
            }
            this.mResizedSurfaces.clear();
        }

        protected void notifyReparentedSurfaces() {
            for (int size = this.mReparentedSurfaces.size() - 1; size >= 0; size--) {
                SurfaceControl surfaceControlKeyAt = this.mReparentedSurfaces.keyAt(size);
                synchronized (surfaceControlKeyAt.mLock) {
                    int size2 = surfaceControlKeyAt.mReparentListeners != null ? surfaceControlKeyAt.mReparentListeners.size() : 0;
                    for (int i = 0; i < size2; i++) {
                        ((OnReparentListener) surfaceControlKeyAt.mReparentListeners.get(i)).onReparent(this, this.mReparentedSurfaces.valueAt(size));
                    }
                    this.mReparentedSurfaces.removeAt(size);
                }
            }
        }

        public Transaction setVisibility(SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (z) {
                return show(surfaceControl);
            }
            return hide(surfaceControl);
        }

        public Transaction setFrameRateSelectionPriority(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetFrameRateSelectionPriority(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction show(SurfaceControl surfaceControl) {
            Transaction transaction;
            SurfaceControl surfaceControl2;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_VISIBILITY || !TextUtils.isEmpty(this.mDebugName)) {
                transaction = this;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging(ThreadedRenderer.OVERDRAW_PROPERTY_SHOW, transaction, surfaceControl, null, CoreRune.FW_SURFACE_DEBUG_VISIBILITY || !TextUtils.isEmpty(this.mDebugName));
                surfaceControl2 = surfaceControl;
            } else {
                transaction = this;
                surfaceControl2 = surfaceControl;
            }
            SurfaceControl.nativeSetFlags(transaction.mNativeObject, surfaceControl2.mNativeObject, 0, 1);
            return transaction;
        }

        public Transaction hide(SurfaceControl surfaceControl) {
            Transaction transaction;
            SurfaceControl surfaceControl2;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_VISIBILITY || !TextUtils.isEmpty(this.mDebugName)) {
                transaction = this;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("hide", transaction, surfaceControl, null, CoreRune.FW_SURFACE_DEBUG_VISIBILITY || !TextUtils.isEmpty(this.mDebugName));
                surfaceControl2 = surfaceControl;
            } else {
                transaction = this;
                surfaceControl2 = surfaceControl;
            }
            SurfaceControl.nativeSetFlags(transaction.mNativeObject, surfaceControl2.mNativeObject, 1, 1);
            return transaction;
        }

        public Transaction setPosition(SurfaceControl surfaceControl, float f, float f2) {
            Transaction transaction;
            SurfaceControl surfaceControl2;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_TRANSFORM) {
                transaction = this;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setPosition", transaction, surfaceControl, "x=" + f + " y=" + f2, CoreRune.FW_SURFACE_DEBUG_TRANSFORM);
                surfaceControl2 = surfaceControl;
            } else {
                transaction = this;
                surfaceControl2 = surfaceControl;
            }
            SurfaceControl.nativeSetPosition(transaction.mNativeObject, surfaceControl2.mNativeObject, f, f2);
            return transaction;
        }

        public Transaction setScale(SurfaceControl surfaceControl, float f, float f2) {
            checkPreconditions(surfaceControl);
            Preconditions.checkArgument(f >= 0.0f, "Negative value passed in for scaleX");
            Preconditions.checkArgument(f2 >= 0.0f, "Negative value passed in for scaleY");
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setScale", this, surfaceControl, "sx=" + f + " sy=" + f2);
            }
            SurfaceControl.nativeSetScale(this.mNativeObject, surfaceControl.mNativeObject, f, f2);
            return this;
        }

        public Transaction setBufferSize(SurfaceControl surfaceControl, int i, int i2) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setBufferSize", this, surfaceControl, "w=" + i + " h=" + i2);
            }
            this.mResizedSurfaces.put(surfaceControl, new Point(i, i2));
            return this;
        }

        public Transaction setFixedTransformHint(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setFixedTransformHint", this, surfaceControl, "hint=" + i);
            }
            SurfaceControl.nativeSetFixedTransformHint(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction unsetFixedTransformHint(SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("unsetFixedTransformHint", this, surfaceControl, null);
            }
            SurfaceControl.nativeSetFixedTransformHint(this.mNativeObject, surfaceControl.mNativeObject, -1);
            return this;
        }

        public Transaction setLayer(SurfaceControl surfaceControl, int i) {
            Transaction transaction;
            SurfaceControl surfaceControl2;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_LAYER) {
                transaction = this;
                surfaceControl2 = surfaceControl;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setLayer", transaction, surfaceControl2, "z=" + i, CoreRune.FW_SURFACE_DEBUG_LAYER);
            } else {
                transaction = this;
                surfaceControl2 = surfaceControl;
            }
            SurfaceControl.nativeSetLayer(transaction.mNativeObject, surfaceControl2.mNativeObject, i);
            return transaction;
        }

        public Transaction setRelativeLayer(SurfaceControl surfaceControl, SurfaceControl surfaceControl2, int i) {
            Transaction transaction;
            SurfaceControl surfaceControl3;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_LAYER) {
                transaction = this;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setRelativeLayer", transaction, surfaceControl, "relTo=" + surfaceControl2 + " z=" + i, CoreRune.FW_SURFACE_DEBUG_LAYER);
                surfaceControl3 = surfaceControl;
            } else {
                transaction = this;
                surfaceControl3 = surfaceControl;
            }
            SurfaceControl.nativeSetRelativeLayer(transaction.mNativeObject, surfaceControl3.mNativeObject, surfaceControl2.mNativeObject, i);
            return transaction;
        }

        public Transaction setTransparentRegionHint(SurfaceControl surfaceControl, Region region) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("unsetFixedTransformHint", this, surfaceControl, "region=" + region);
            }
            SurfaceControl.nativeSetTransparentRegionHint(this.mNativeObject, surfaceControl.mNativeObject, region);
            return this;
        }

        public Transaction setAlpha(SurfaceControl surfaceControl, float f) {
            Transaction transaction;
            SurfaceControl surfaceControl2;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_ALPHA) {
                transaction = this;
                surfaceControl2 = surfaceControl;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setAlpha", transaction, surfaceControl2, "alpha=" + f, CoreRune.FW_SURFACE_DEBUG_ALPHA);
            } else {
                transaction = this;
                surfaceControl2 = surfaceControl;
            }
            SurfaceControl.nativeSetAlpha(transaction.mNativeObject, surfaceControl2.mNativeObject, f);
            return transaction;
        }

        public Transaction setInputWindowInfo(SurfaceControl surfaceControl, InputWindowHandle inputWindowHandle) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetInputWindowInfo(this.mNativeObject, surfaceControl.mNativeObject, inputWindowHandle);
            return this;
        }

        public Transaction addWindowInfosReportedListener(Runnable runnable) {
            SurfaceControl.nativeAddWindowInfosReportedListener(this.mNativeObject, runnable);
            return this;
        }

        public Transaction setGeometry(SurfaceControl surfaceControl, Rect rect, Rect rect2, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetGeometry(this.mNativeObject, surfaceControl.mNativeObject, rect, rect2, i);
            return this;
        }

        public Transaction setMatrix(SurfaceControl surfaceControl, float f, float f2, float f3, float f4) {
            float f5;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_TRANSFORM) {
                SurfaceControlRegistry processInstance = SurfaceControlRegistry.getProcessInstance();
                StringBuilder sb = new StringBuilder("dsdx=");
                sb.append(f);
                sb.append(" dtdx=");
                sb.append(f2);
                sb.append(" dtdy=");
                sb.append(f3);
                sb.append(" dsdy=");
                f5 = f4;
                sb.append(f5);
                processInstance.checkCallStackDebugging("setMatrix", this, surfaceControl, sb.toString(), CoreRune.FW_SURFACE_DEBUG_TRANSFORM);
            } else {
                f5 = f4;
            }
            SurfaceControl.nativeSetMatrix(this.mNativeObject, surfaceControl.mNativeObject, f, f2, f3, f5);
            return this;
        }

        public Transaction setMatrix(SurfaceControl surfaceControl, Matrix matrix, float[] fArr) {
            matrix.getValues(fArr);
            setMatrix(surfaceControl, fArr[0], fArr[3], fArr[1], fArr[4]);
            setPosition(surfaceControl, fArr[2], fArr[5]);
            return this;
        }

        public Transaction setColorTransform(SurfaceControl surfaceControl, float[] fArr, float[] fArr2) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetColorTransform(this.mNativeObject, surfaceControl.mNativeObject, fArr, fArr2);
            return this;
        }

        public Transaction setColorSpaceAgnostic(SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetColorSpaceAgnostic(this.mNativeObject, surfaceControl.mNativeObject, z);
            return this;
        }

        @Deprecated
        public Transaction setWindowCrop(SurfaceControl surfaceControl, Rect rect) {
            Transaction transaction;
            SurfaceControl surfaceControl2;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_CROP) {
                transaction = this;
                surfaceControl2 = surfaceControl;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setWindowCrop", transaction, surfaceControl2, "crop=" + rect, CoreRune.FW_SURFACE_DEBUG_CROP);
            } else {
                transaction = this;
                surfaceControl2 = surfaceControl;
            }
            if (rect != null) {
                SurfaceControl.nativeSetWindowCrop(transaction.mNativeObject, surfaceControl2.mNativeObject, rect.left, rect.top, rect.right, rect.bottom);
                return transaction;
            }
            SurfaceControl.nativeSetWindowCrop(transaction.mNativeObject, surfaceControl2.mNativeObject, 0, 0, 0, 0);
            return transaction;
        }

        public Transaction setCrop(SurfaceControl surfaceControl, Rect rect) {
            Transaction transaction;
            SurfaceControl surfaceControl2;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_CROP) {
                transaction = this;
                surfaceControl2 = surfaceControl;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setCrop", transaction, surfaceControl2, "crop=" + rect, CoreRune.FW_SURFACE_DEBUG_CROP);
            } else {
                transaction = this;
                surfaceControl2 = surfaceControl;
            }
            if (rect != null) {
                Preconditions.checkArgument(rect.isValid(), "Crop " + rect + " isn't valid");
                SurfaceControl.nativeSetWindowCrop(transaction.mNativeObject, surfaceControl2.mNativeObject, rect.left, rect.top, rect.right, rect.bottom);
                return transaction;
            }
            SurfaceControl.nativeSetWindowCrop(transaction.mNativeObject, surfaceControl2.mNativeObject, 0, 0, 0, 0);
            return transaction;
        }

        public Transaction setWindowCrop(SurfaceControl surfaceControl, int i, int i2) {
            Transaction transaction;
            SurfaceControl surfaceControl2;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_CROP) {
                transaction = this;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setWindowCrop", transaction, surfaceControl, "w=" + i + " h=" + i2, CoreRune.FW_SURFACE_DEBUG_CROP);
                surfaceControl2 = surfaceControl;
            } else {
                transaction = this;
                surfaceControl2 = surfaceControl;
            }
            SurfaceControl.nativeSetWindowCrop(transaction.mNativeObject, surfaceControl2.mNativeObject, 0, 0, i, i2);
            return transaction;
        }

        public Transaction setCrop(SurfaceControl surfaceControl, float f, float f2, float f3, float f4) {
            float f5;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry processInstance = SurfaceControlRegistry.getProcessInstance();
                StringBuilder sb = new StringBuilder("crop={");
                sb.append(f2);
                sb.append(", ");
                sb.append(f);
                sb.append(", ");
                f5 = f4;
                sb.append(f5);
                sb.append(", ");
                sb.append(f3);
                sb.append("}");
                processInstance.checkCallStackDebugging("setCrop", this, surfaceControl, sb.toString());
            } else {
                f5 = f4;
            }
            SurfaceControl.nativeSetCrop(this.mNativeObject, surfaceControl.mNativeObject, f, f2, f3, f5);
            return this;
        }

        public Transaction setCornerRadius(SurfaceControl surfaceControl, float f) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setCornerRadius", this, surfaceControl, "cornerRadius=" + f);
            }
            SurfaceControl.nativeSetCornerRadius(this.mNativeObject, surfaceControl.mNativeObject, f);
            return this;
        }

        public Transaction setClientDrawnCornerRadius(SurfaceControl surfaceControl, float f) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setClientDrawnCornerRadius", this, surfaceControl, "clientDrawnCornerRadius=" + f);
            }
            if (Flags.ignoreCornerRadiusAndShadows()) {
                SurfaceControl.nativeSetClientDrawnCornerRadius(this.mNativeObject, surfaceControl.mNativeObject, f);
                return this;
            }
            Log.w(SurfaceControl.TAG, "setClientDrawnCornerRadius was called butignore_corner_radius_and_shadows flag is disabled");
            return this;
        }

        public Transaction setBackgroundBlurRadius(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setBackgroundBlurRadius", this, surfaceControl, "radius=" + i);
            }
            SurfaceControl.nativeSetBackgroundBlurRadius(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction setBlurRegions(SurfaceControl surfaceControl, float[][] fArr) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetBlurRegions(this.mNativeObject, surfaceControl.mNativeObject, fArr, fArr.length);
            return this;
        }

        public Transaction setStretchEffect(SurfaceControl surfaceControl, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetStretchEffect(this.mNativeObject, surfaceControl.mNativeObject, f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
            return this;
        }

        public Transaction setEdgeExtensionEffect(SurfaceControl surfaceControl, int i) {
            boolean z;
            boolean z2;
            boolean z3;
            checkPreconditions(surfaceControl);
            long j = this.mNativeObject;
            long j2 = surfaceControl.mNativeObject;
            boolean z4 = false;
            boolean z5 = true;
            if ((i & 1) != 0) {
                z = false;
                z4 = true;
            } else {
                z = false;
            }
            if ((i & 4) != 0) {
                z2 = true;
            } else {
                z2 = true;
                z5 = z;
            }
            if ((i & 2) != 0) {
                z3 = z2;
            } else {
                z3 = z2;
                z2 = z;
            }
            if ((i & 8) == 0) {
                z3 = z;
            }
            SurfaceControl.nativeSetEdgeExtensionEffect(j, j2, z4, z5, z2, z3);
            return this;
        }

        public Transaction startSurfaceAnimation(SurfaceControl surfaceControl, String str) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeStartSurfaceAnimation(this.mNativeObject, surfaceControl.mNativeObject, str);
            return this;
        }

        public Transaction setBackgroundBlurColorCurve(SurfaceControl surfaceControl, SemBlurInfo.ColorCurve colorCurve) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetBackgroundBlurColorCurve(this.mNativeObject, surfaceControl.mNativeObject, new float[]{colorCurve.mMinX, colorCurve.mMinY, colorCurve.mMaxX, colorCurve.mMaxY, colorCurve.mCurveBias, colorCurve.mSaturation});
            return this;
        }

        public Transaction setDisplayReluminoEffect(long j, float f, int i) {
            IBinder physicalDisplayToken = SurfaceControl.getPhysicalDisplayToken(j);
            if (physicalDisplayToken == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            SurfaceControl.nativeSetDisplayReluminoEffect(this.mNativeObject, physicalDisplayToken, f, i);
            return this;
        }

        public Transaction setLayerStack(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetLayerStack(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction reparent(SurfaceControl surfaceControl, SurfaceControl surfaceControl2) {
            Transaction transaction;
            SurfaceControl surfaceControl3;
            long j;
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled || CoreRune.FW_SURFACE_DEBUG_REPARENT) {
                transaction = this;
                surfaceControl3 = surfaceControl;
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("reparent", transaction, surfaceControl3, "newParent=" + surfaceControl2, CoreRune.FW_SURFACE_DEBUG_REPARENT);
            } else {
                transaction = this;
                surfaceControl3 = surfaceControl;
            }
            if (surfaceControl2 != null) {
                surfaceControl2.checkNotReleased();
                j = surfaceControl2.mNativeObject;
            } else {
                j = 0;
            }
            SurfaceControl.nativeReparent(transaction.mNativeObject, surfaceControl3.mNativeObject, j);
            transaction.mReparentedSurfaces.put(surfaceControl3, surfaceControl2);
            return transaction;
        }

        public Transaction setColor(SurfaceControl surfaceControl, float[] fArr) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setColor", this, surfaceControl, "r=" + fArr[0] + " g=" + fArr[1] + " b=" + fArr[2]);
            }
            SurfaceControl.nativeSetColor(this.mNativeObject, surfaceControl.mNativeObject, fArr);
            return this;
        }

        public Transaction unsetColor(SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("unsetColor", this, surfaceControl, null);
            }
            SurfaceControl.nativeSetColor(this.mNativeObject, surfaceControl.mNativeObject, INVALID_COLOR);
            return this;
        }

        public Transaction setSecure(SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setSecure", this, surfaceControl, "secure=" + z);
            }
            if (z) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 128, 128);
                return this;
            }
            SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 128);
            return this;
        }

        public Transaction setDisplayDecoration(SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (z) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 512, 512);
                return this;
            }
            SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 512);
            return this;
        }

        public Transaction setOpaque(SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setOpaque", this, surfaceControl, "opaque=" + z);
            }
            if (z) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 2, 2);
                return this;
            }
            SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 2);
            return this;
        }

        public Transaction setDisplaySurface(IBinder iBinder, Surface surface) {
            if (iBinder == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            if (surface != null) {
                synchronized (surface.mLock) {
                    SurfaceControl.nativeSetDisplaySurface(this.mNativeObject, iBinder, surface.mNativeObject);
                }
                return this;
            }
            SurfaceControl.nativeSetDisplaySurface(this.mNativeObject, iBinder, 0L);
            return this;
        }

        public Transaction setDisplayLayerStack(IBinder iBinder, int i) {
            if (iBinder == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            SurfaceControl.nativeSetDisplayLayerStack(this.mNativeObject, iBinder, i);
            return this;
        }

        public Transaction setDisplayFlags(IBinder iBinder, int i) {
            if (iBinder == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            SurfaceControl.nativeSetDisplayFlags(this.mNativeObject, iBinder, i);
            return this;
        }

        public Transaction setDisplayProjection(IBinder iBinder, int i, Rect rect, Rect rect2) {
            if (iBinder == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            if (rect == null) {
                throw new IllegalArgumentException("layerStackRect must not be null");
            }
            if (rect2 == null) {
                throw new IllegalArgumentException("displayRect must not be null");
            }
            SurfaceControl.nativeSetDisplayProjection(this.mNativeObject, iBinder, i, rect.left, rect.top, rect.right, rect.bottom, rect2.left, rect2.top, rect2.right, rect2.bottom);
            return this;
        }

        public Transaction setDisplaySize(IBinder iBinder, int i, int i2) {
            if (iBinder == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            if (i <= 0 || i2 <= 0) {
                throw new IllegalArgumentException("width and height must be positive");
            }
            SurfaceControl.nativeSetDisplaySize(this.mNativeObject, iBinder, i, i2);
            return this;
        }

        public Transaction setAnimationTransaction() {
            SurfaceControl.nativeSetAnimationTransaction(this.mNativeObject);
            return this;
        }

        public Transaction setEarlyWakeupStart() {
            SurfaceControl.nativeSetEarlyWakeupStart(this.mNativeObject);
            return this;
        }

        public Transaction setEarlyWakeupEnd() {
            SurfaceControl.nativeSetEarlyWakeupEnd(this.mNativeObject);
            return this;
        }

        public long getId() {
            return SurfaceControl.nativeGetTransactionId(this.mNativeObject);
        }

        public Transaction setMetadata(SurfaceControl surfaceControl, int i, int i2) {
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.writeInt(i2);
            try {
                setMetadata(surfaceControl, i, parcelObtain);
                return this;
            } finally {
                parcelObtain.recycle();
            }
        }

        public Transaction setMetadata(SurfaceControl surfaceControl, int i, Parcel parcel) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetMetadata(this.mNativeObject, surfaceControl.mNativeObject, i, parcel);
            return this;
        }

        public Transaction setShadowRadius(SurfaceControl surfaceControl, float f) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setShadowRadius", this, surfaceControl, "radius=" + f);
            }
            SurfaceControl.nativeSetShadowRadius(this.mNativeObject, surfaceControl.mNativeObject, f);
            return this;
        }

        public Transaction setBorderSettings(SurfaceControl surfaceControl, BorderSettings borderSettings) {
            checkPreconditions(surfaceControl);
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setBorderSettings", this, surfaceControl, "settings=" + borderSettings);
            }
            if (!Flags.enableBorderSettings()) {
                Log.w(SurfaceControl.TAG, "setBorderSettings was called butenable_border_settings flag is disabled");
                return this;
            }
            Parcel parcelObtain = Parcel.obtain();
            borderSettings.writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            SurfaceControl.nativeSetBorderSettings(this.mNativeObject, surfaceControl.mNativeObject, parcelObtain);
            return this;
        }

        public Transaction setFrameRate(SurfaceControl surfaceControl, float f, int i) {
            return setFrameRate(surfaceControl, f, i, 0);
        }

        public Transaction setFrameRate(SurfaceControl surfaceControl, float f, int i, int i2) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetFrameRate(this.mNativeObject, surfaceControl.mNativeObject, f, i, i2);
            return this;
        }

        public Transaction setFrameRate(SurfaceControl surfaceControl, Surface.FrameRateParams frameRateParams) {
            float fixedSourceRate;
            checkPreconditions(surfaceControl);
            if (com.android.graphics.surfaceflinger.flags.Flags.arrSetframerateApi()) {
                int i = frameRateParams.getFixedSourceRate() == 0.0f ? 0 : 1;
                if (i == 0) {
                    fixedSourceRate = frameRateParams.getDesiredMinRate();
                } else {
                    fixedSourceRate = frameRateParams.getFixedSourceRate();
                }
                SurfaceControl.nativeSetFrameRate(this.mNativeObject, surfaceControl.mNativeObject, fixedSourceRate, i, frameRateParams.getChangeFrameRateStrategy());
                return this;
            }
            Log.w(SurfaceControl.TAG, "setFrameRate was called but flag arr_setframerate_api is disabled");
            return this;
        }

        public Transaction clearFrameRate(SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetFrameRate(this.mNativeObject, surfaceControl.mNativeObject, 0.0f, 0, 1);
            return this;
        }

        public Transaction setDefaultFrameRateCompatibility(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetDefaultFrameRateCompatibility(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction setFrameRateCategory(SurfaceControl surfaceControl, int i, boolean z) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetFrameRateCategory(this.mNativeObject, surfaceControl.mNativeObject, i, z);
            return this;
        }

        public Transaction setFrameRateSelectionStrategy(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetFrameRateSelectionStrategy(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction setFocusedWindow(IBinder iBinder, String str, int i) {
            SurfaceControl.nativeSetFocusedWindow(this.mNativeObject, iBinder, str, i);
            return this;
        }

        public Transaction removeCurrentInputFocus(int i) {
            SurfaceControl.nativeRemoveCurrentInputFocus(this.mNativeObject, i);
            return this;
        }

        public Transaction setSkipScreenshot(SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (z) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 64, 64);
                return this;
            }
            SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 64);
            return this;
        }

        public Transaction setDisableSuperHDR(SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (z) {
                SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 268435456, 268435456);
                return this;
            }
            SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 268435456);
            return this;
        }

        @Deprecated
        public Transaction setBuffer(SurfaceControl surfaceControl, GraphicBuffer graphicBuffer) {
            return setBuffer(surfaceControl, HardwareBuffer.createFromGraphicBuffer(graphicBuffer));
        }

        public Transaction setBuffer(SurfaceControl surfaceControl, HardwareBuffer hardwareBuffer) {
            return setBuffer(surfaceControl, hardwareBuffer, null);
        }

        public Transaction unsetBuffer(SurfaceControl surfaceControl) {
            SurfaceControl.nativeUnsetBuffer(this.mNativeObject, surfaceControl.mNativeObject);
            return this;
        }

        public Transaction setBuffer(SurfaceControl surfaceControl, HardwareBuffer hardwareBuffer, SyncFence syncFence) {
            return setBuffer(surfaceControl, hardwareBuffer, syncFence, null);
        }

        public Transaction setBuffer(SurfaceControl surfaceControl, HardwareBuffer hardwareBuffer, SyncFence syncFence, Consumer<SyncFence> consumer) {
            checkPreconditions(surfaceControl);
            if (syncFence != null) {
                synchronized (syncFence.getLock()) {
                    SurfaceControl.nativeSetBuffer(this.mNativeObject, surfaceControl.mNativeObject, hardwareBuffer, syncFence.getNativeFence(), consumer);
                }
                return this;
            }
            SurfaceControl.nativeSetBuffer(this.mNativeObject, surfaceControl.mNativeObject, hardwareBuffer, 0L, consumer);
            return this;
        }

        public Transaction setBufferTransform(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetBufferTransform(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction setDamageRegion(SurfaceControl surfaceControl, Region region) {
            SurfaceControl.nativeSetDamageRegion(this.mNativeObject, surfaceControl.mNativeObject, region);
            return this;
        }

        public Transaction setDimmingEnabled(SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetDimmingEnabled(this.mNativeObject, surfaceControl.mNativeObject, z);
            return this;
        }

        @Deprecated
        public Transaction setColorSpace(SurfaceControl surfaceControl, ColorSpace colorSpace) {
            checkPreconditions(surfaceControl);
            if (colorSpace.getId() == ColorSpace.Named.DISPLAY_P3.ordinal()) {
                setDataSpace(surfaceControl, 143261696);
                return this;
            }
            setDataSpace(surfaceControl, 142671872);
            return this;
        }

        public Transaction setDataSpace(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetDataSpace(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction setExtendedRangeBrightness(SurfaceControl surfaceControl, float f, float f2) {
            checkPreconditions(surfaceControl);
            if (!Float.isFinite(f) || f < 1.0f) {
                throw new IllegalArgumentException("currentBufferRatio must be finite && >= 1.0f; got " + f);
            }
            if (!Float.isFinite(f2) || f2 < 1.0f) {
                throw new IllegalArgumentException("desiredRatio must be finite && >= 1.0f; got " + f2);
            }
            SurfaceControl.nativeSetExtendedRangeBrightness(this.mNativeObject, surfaceControl.mNativeObject, f, f2);
            return this;
        }

        public Transaction setDesiredHdrHeadroom(SurfaceControl surfaceControl, float f) {
            checkPreconditions(surfaceControl);
            if (!Float.isFinite(f) || (f != 0.0f && f < 1.0f)) {
                throw new IllegalArgumentException("desiredRatio must be finite && >= 1.0f or 0; got " + f);
            }
            SurfaceControl.nativeSetDesiredHdrHeadroom(this.mNativeObject, surfaceControl.mNativeObject, f);
            return this;
        }

        public Transaction setLuts(SurfaceControl surfaceControl, DisplayLuts displayLuts) {
            checkPreconditions(surfaceControl);
            if (displayLuts != null && displayLuts.valid()) {
                SurfaceControl.nativeSetLuts(this.mNativeObject, surfaceControl.mNativeObject, displayLuts.getLutBuffers(), displayLuts.getOffsets(), displayLuts.getLutDimensions(), displayLuts.getLutSizes(), displayLuts.getLutSamplingKeys());
                return this;
            }
            SurfaceControl.nativeSetLuts(this.mNativeObject, surfaceControl.mNativeObject, null, null, null, null, null);
            return this;
        }

        @SystemApi
        public Transaction setPictureProfileHandle(SurfaceControl surfaceControl, PictureProfileHandle pictureProfileHandle) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetPictureProfileId(this.mNativeObject, surfaceControl.mNativeObject, pictureProfileHandle.getId());
            return this;
        }

        public Transaction setContentPriority(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetContentPriority(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction setCachingHint(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetCachingHint(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction setTrustedOverlay(SurfaceControl surfaceControl, boolean z) {
            return setTrustedOverlay(surfaceControl, z ? 2 : 0);
        }

        public Transaction setTrustedOverlay(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetTrustedOverlay(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction setDropInputMode(SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetDropInputMode(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public Transaction setCanOccludePresentation(SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, z ? 4096 : 0, 4096);
            return this;
        }

        public static void sendSurfaceFlushJankData(SurfaceControl surfaceControl) {
            surfaceControl.checkNotReleased();
            SurfaceControl.nativeSurfaceFlushJankData(surfaceControl.mNativeObject);
        }

        public void sanitize(int i, int i2) {
            SurfaceControl.nativeSanitize(this.mNativeObject, i, i2);
        }

        public Transaction setDestinationFrame(SurfaceControl surfaceControl, Rect rect) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetDestinationFrame(this.mNativeObject, surfaceControl.mNativeObject, rect.left, rect.top, rect.right, rect.bottom);
            return this;
        }

        public Transaction setDestinationFrame(SurfaceControl surfaceControl, int i, int i2) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetDestinationFrame(this.mNativeObject, surfaceControl.mNativeObject, 0, 0, i, i2);
            return this;
        }

        public Transaction merge(Transaction transaction) {
            ArrayList<String> arrayList;
            if (this == transaction) {
                return this;
            }
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("merge", this, null, "otherTx=" + transaction.getId());
                ArrayList<String> arrayList2 = this.mCalls;
                if (arrayList2 != null && (arrayList = transaction.mCalls) != null) {
                    arrayList2.addAll(arrayList);
                    transaction.mCalls.clear();
                }
            }
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                if (!TextUtils.isEmpty(transaction.mDebugName)) {
                    if (this.mDebugName == null) {
                        this.mDebugName = "";
                    } else {
                        this.mDebugName += ", ";
                    }
                    this.mDebugName += transaction.mDebugName;
                    transaction.mDebugName = null;
                }
                if (!TextUtils.isEmpty(transaction.mLowDebugName)) {
                    if (this.mLowDebugName == null) {
                        this.mLowDebugName = "";
                    } else {
                        this.mLowDebugName += ", ";
                    }
                    this.mLowDebugName += transaction.mLowDebugName;
                    transaction.mLowDebugName = null;
                }
            }
            this.mResizedSurfaces.putAll((ArrayMap<? extends SurfaceControl, ? extends Point>) transaction.mResizedSurfaces);
            transaction.mResizedSurfaces.clear();
            this.mReparentedSurfaces.putAll((ArrayMap<? extends SurfaceControl, ? extends SurfaceControl>) transaction.mReparentedSurfaces);
            transaction.mReparentedSurfaces.clear();
            SurfaceControl.nativeMergeTransaction(this.mNativeObject, transaction.mNativeObject);
            return this;
        }

        void onMergeWithNextTransaction(CharSequence charSequence) {
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("merge", this, null, "window=" + ((Object) charSequence));
                ArrayList<String> arrayList = this.mCalls;
                if (arrayList != null) {
                    arrayList.clear();
                }
                SurfaceControl.nativeEnableDebugLogCallPoints(this.mNativeObject);
            }
        }

        public Transaction remove(SurfaceControl surfaceControl) {
            reparent(surfaceControl, null);
            surfaceControl.release();
            return this;
        }

        public Transaction setFrameTimeline(long j) {
            if (!Flags.sdkDesiredPresentTime()) {
                Log.w(SurfaceControl.TAG, "addTransactionCompletedListener was called but flag is disabled");
                return this;
            }
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setFrameTimeline", this, null, "vsyncId=" + j);
            }
            SurfaceControl.nativeSetFrameTimelineVsync(this.mNativeObject, j);
            return this;
        }

        public Transaction setFrameTimelineVsync(long j) {
            if (SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setFrameTimelineVsync", this, null, "frameTimelineVsyncId=" + j);
            }
            SurfaceControl.nativeSetFrameTimelineVsync(this.mNativeObject, j);
            return this;
        }

        public Transaction addTransactionCommittedListener(final Executor executor, final TransactionCommittedListener transactionCommittedListener) {
            SurfaceControl.nativeAddTransactionCommittedListener(this.mNativeObject, new TransactionCommittedListener() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda2
                @Override // android.view.SurfaceControl.TransactionCommittedListener
                public final void onTransactionCommitted() {
                    SurfaceControl.Transaction.lambda$addTransactionCommittedListener$0(executor, transactionCommittedListener);
                }
            });
            return this;
        }

        static /* synthetic */ void lambda$addTransactionCommittedListener$0(Executor executor, final TransactionCommittedListener transactionCommittedListener) {
            Objects.requireNonNull(transactionCommittedListener);
            executor.execute(new Runnable() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    transactionCommittedListener.onTransactionCommitted();
                }
            });
        }

        public Transaction addTransactionCompletedListener(final Executor executor, final Consumer<TransactionStats> consumer) {
            if (!Flags.sdkDesiredPresentTime()) {
                Log.w(SurfaceControl.TAG, "addTransactionCompletedListener was called but flag is disabled");
                return this;
            }
            SurfaceControl.nativeAddTransactionCompletedListener(this.mNativeObject, new Consumer() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    executor.execute(new Runnable() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            consumer.andThen(new Consumer() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    ((SurfaceControl.TransactionStats) obj2).close();
                                }
                            }).accept(transactionStats);
                        }
                    });
                }
            });
            return this;
        }

        /* renamed from: android.view.SurfaceControl$Transaction$1, reason: invalid class name */
        class AnonymousClass1 extends TrustedPresentationCallback {
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ Consumer val$listener;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(Transaction transaction, Executor executor, Consumer consumer) {
                super();
                this.val$executor = executor;
                this.val$listener = consumer;
            }

            @Override // android.view.SurfaceControl.TrustedPresentationCallback
            public void onTrustedPresentationChanged(final boolean z) {
                Executor executor = this.val$executor;
                final Consumer consumer = this.val$listener;
                executor.execute(new Runnable() { // from class: android.view.SurfaceControl$Transaction$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(Boolean.valueOf(z));
                    }
                });
            }
        }

        @Deprecated
        public Transaction setTrustedPresentationCallback(SurfaceControl surfaceControl, TrustedPresentationThresholds trustedPresentationThresholds, Executor executor, Consumer<Boolean> consumer) {
            checkPreconditions(surfaceControl);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, executor, consumer);
            if (surfaceControl.mTrustedPresentationCallback != null) {
                surfaceControl.mTrustedPresentationCallback.mFreeNativeResources.run();
            }
            SurfaceControl.nativeSetTrustedPresentationCallback(this.mNativeObject, surfaceControl.mNativeObject, ((TrustedPresentationCallback) anonymousClass1).mNativeObject, trustedPresentationThresholds);
            surfaceControl.mTrustedPresentationCallback = anonymousClass1;
            return this;
        }

        @Deprecated
        public Transaction clearTrustedPresentationCallback(SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeClearTrustedPresentationCallback(this.mNativeObject, surfaceControl.mNativeObject);
            if (surfaceControl.mTrustedPresentationCallback != null) {
                surfaceControl.mTrustedPresentationCallback.mFreeNativeResources.run();
                surfaceControl.mTrustedPresentationCallback = null;
            }
            return this;
        }

        public Transaction setDesiredPresentTimeNanos(long j) {
            if (!Flags.sdkDesiredPresentTime()) {
                Log.w(SurfaceControl.TAG, "addTransactionCompletedListener was called but flag is disabled");
                return this;
            }
            SurfaceControl.nativeSetDesiredPresentTimeNanos(this.mNativeObject, j);
            return this;
        }

        public Transaction setRecoverableFromBufferStuffing(SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 8192, 8192);
            return this;
        }

        public Transaction startChangeResolution(IBinder iBinder, boolean z) {
            if (iBinder == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            Log.i(SurfaceControl.TAG, "startChangeResolution, enabled=" + z + ", caller=" + Debug.getCallers(5));
            SurfaceControl.nativeStartChangeResolution(this.mNativeObject, iBinder, z);
            return this;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            if (this.mNativeObject == 0) {
                parcel.writeInt(0);
                return;
            }
            parcel.writeInt(1);
            SurfaceControl.nativeWriteTransactionToParcel(this.mNativeObject, parcel);
            if ((i & 1) != 0) {
                SurfaceControl.nativeClearTransaction(this.mNativeObject);
            }
            if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                parcel.writeString(this.mDebugName);
                this.mDebugName = null;
                parcel.writeString(this.mLowDebugName);
                this.mLowDebugName = null;
            }
        }

        private void readFromParcel(Parcel parcel) {
            this.mNativeObject = 0L;
            if (parcel.readInt() != 0) {
                long jNativeReadTransactionFromParcel = SurfaceControl.nativeReadTransactionFromParcel(parcel);
                this.mNativeObject = jNativeReadTransactionFromParcel;
                this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, jNativeReadTransactionFromParcel);
                if (CoreRune.FW_SURFACE_DEBUG_APPLY) {
                    this.mDebugName = parcel.readString();
                    this.mLowDebugName = parcel.readString();
                }
            }
        }
    }

    public static class LockDebuggingTransaction extends Transaction {
        Object mMonitor;

        public LockDebuggingTransaction(Object obj) {
            this.mMonitor = obj;
        }

        @Override // android.view.SurfaceControl.Transaction
        protected void checkPreconditions(SurfaceControl surfaceControl) {
            super.checkPreconditions(surfaceControl);
            if (!Thread.holdsLock(this.mMonitor)) {
                throw new RuntimeException("Unlocked access to synchronized SurfaceControl.Transaction");
            }
        }
    }

    public void resize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        nativeUpdateDefaultBufferSize(this.mNativeObject, i, i2);
    }

    public int getTransformHint() {
        checkNotReleased();
        return nativeGetTransformHint(this.mNativeObject);
    }

    public void setTransformHint(int i) {
        nativeSetTransformHint(this.mNativeObject, i);
    }

    public int getLayerId() {
        long j = this.mNativeObject;
        if (j != 0) {
            return nativeGetLayerId(j);
        }
        return -1;
    }

    private static void invokeReleaseCallback(Consumer<SyncFence> consumer, long j) {
        consumer.accept(new SyncFence(j));
    }

    public static StalledTransactionInfo getStalledTransactionInfo(int i) {
        return nativeGetStalledTransactionInfo(i);
    }

    public static void notifyShutdown() {
        nativeNotifyShutdown();
    }

    public static IBinder getDisplayToken(DisplayAddress displayAddress) {
        if (displayAddress instanceof DisplayAddress.Physical) {
            return getPhysicalDisplayToken(((DisplayAddress.Physical) displayAddress).getPhysicalDisplayId());
        }
        return null;
    }

    public static void notifyHFRmode(IBinder iBinder, int i) {
        if (CoreRune.FW_VRR_POLICY) {
            Log.d(TAG, "notifyHFRmode, displayToken=" + iBinder + ", hfrMode=" + Settings.Secure.refreshRateModeToString(i));
            if (iBinder == null) {
                throw new IllegalArgumentException("displayToken must not be null");
            }
            nativeNotifyHFRmode(iBinder, i);
        }
    }

    public static void restrictHighRefreshRate(boolean z) {
        Log.d(TAG, "restrictHighRefreshRate, enabled=" + z);
        nativeRestrictHighRefreshRate(z);
    }
}
