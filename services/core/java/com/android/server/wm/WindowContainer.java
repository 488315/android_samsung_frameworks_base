package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.GraphicBuffer;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pools;
import android.util.RotationUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayInfo;
import android.view.InsetsFrameProvider;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.MagnificationSpec;
import android.view.RemoteAnimationDefinition;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceSession;
import android.view.WindowManager;
import android.window.IWindowContainerToken;
import android.window.ScreenCapture;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.wm.BLASTSyncEngine;
import com.android.server.wm.RemoteAnimationController;
import com.android.server.wm.SurfaceAnimator;
import com.android.server.wm.SurfaceFreezer;
import com.android.server.wm.Transition;
import com.android.server.wm.TransparentPolicy;
import com.android.server.wm.utils.AlwaysTruePredicate;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class WindowContainer extends ConfigurationContainer implements Comparable, SurfaceAnimator.Animatable, InsetsControlTarget {
    private static final int INVALID_SYNC_ID = -1;
    static final int POSITION_BOTTOM = Integer.MIN_VALUE;
    static final int POSITION_TOP = Integer.MAX_VALUE;
    public static final int SYNC_STATE_NONE = 0;
    public static final int SYNC_STATE_READY = 2;
    public static final int SYNC_STATE_WAITING_FOR_DRAW = 1;
    private static final String TAG = "WindowManager";
    SurfaceControl mAnimationBoundsLayer;
    private SurfaceControl mAnimationLeash;
    private boolean mCommittedReparentToAnimationLeash;
    protected InsetsSourceProvider mControllableInsetProvider;
    DimAnimator mDimAnimator;
    protected DisplayContent mDisplayContent;
    protected final WindowManagerServiceExt mExt;
    private ArrayMap mInsetsOwnerDeathRecipientMap;
    private MagnificationSpec mLastMagnificationSpec;
    protected WindowContainer mLastOrientationSource;
    boolean mLaunchTaskBehind;
    boolean mNeedsAnimationBoundsLayer;
    boolean mNeedsZBoost;
    protected TrustedOverlayHost mOverlayHost;
    private final SurfaceControl.Transaction mPendingTransaction;
    boolean mReparenting;
    protected final SurfaceAnimator mSurfaceAnimator;
    protected SurfaceControl mSurfaceControl;
    final SurfaceFreezer mSurfaceFreezer;
    final SurfaceControl.Transaction mSyncTransaction;
    WindowContainerThumbnail mThumbnail;
    int mTransit;
    int mTransitFlags;
    final TransitionController mTransitionController;
    boolean mUseConfigurationInUdcCutout;
    boolean mUseLayoutInUdcCutout;
    protected boolean mVisibleRequested;
    protected final WindowManagerService mWmService;
    private WindowContainer mParent = null;
    SparseArray mLocalInsetsSources = null;
    protected SparseArray mInsetsSourceProviders = null;
    protected final WindowList mChildren = new WindowList();
    private int mOverrideOrientation = -2;
    private final Pools.SynchronizedPool mConsumerWrapperPool = new Pools.SynchronizedPool(3);
    private int mLastLayer = 0;
    private SurfaceControl mLastRelativeToLayer = null;
    final ArrayList mWaitingForDrawn = new ArrayList();
    private final ArraySet mSurfaceAnimationSources = new ArraySet();
    private final Point mTmpPos = new Point();
    protected final Point mLastSurfacePosition = new Point();
    protected int mLastDeltaRotation = 0;
    private int mTreeWeight = 1;
    private int mSyncTransactionCommitCallbackDepth = 0;
    final Point mTmpPoint = new Point();
    protected final Rect mTmpRect = new Rect();
    final Rect mTmpPrevBounds = new Rect();
    private boolean mIsFocusable = true;
    RemoteToken mRemoteToken = null;
    BLASTSyncEngine.SyncGroup mSyncGroup = null;
    int mSyncState = 0;
    int mSyncMethodOverride = -1;
    int mSyncIdForReparentSurfaceControl = -1;
    private final List mListeners = new ArrayList();
    float mFreeformAlpha = 1.0f;
    private final LinkedList mTmpChain1 = new LinkedList();
    private final LinkedList mTmpChain2 = new LinkedList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnimationRunnerBuilder {
        public final List mOnAnimationFinished = new LinkedList();
        public final List mOnAnimationCancelled = new LinkedList();

        public AnimationRunnerBuilder(WindowContainer windowContainer) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ConfigurationMerger {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeathRecipient implements IBinder.DeathRecipient {
        public final IBinder mOwner;
        public final ArraySet mSourceIds = new ArraySet();
        public final /* synthetic */ WindowContainer this$0;

        public DeathRecipient(IBinder iBinder, WindowContainer windowContainer) {
            this.this$0 = windowContainer;
            this.mOwner = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            DisplayContent displayContent;
            boolean z;
            WindowManagerGlobalLock windowManagerGlobalLock = this.this$0.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    boolean z2 = false;
                    for (int size = this.mSourceIds.size() - 1; size >= 0; size--) {
                        WindowContainer windowContainer = this.this$0;
                        int intValue = ((Integer) this.mSourceIds.valueAt(size)).intValue();
                        SparseArray sparseArray = windowContainer.mLocalInsetsSources;
                        if (sparseArray != null && sparseArray.removeReturnOld(intValue) != null) {
                            z = true;
                            z2 |= z;
                        }
                        z = false;
                        z2 |= z;
                    }
                    this.mSourceIds.clear();
                    this.mOwner.unlinkToDeath(this, 0);
                    this.this$0.mInsetsOwnerDeathRecipientMap.remove(this.mOwner);
                    if (z2 && (displayContent = this.this$0.mDisplayContent) != null) {
                        displayContent.mInsetsStateController.updateAboveInsetsState(true);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ForAllWindowsConsumerWrapper implements ToBooleanFunction {
        public Consumer mConsumer;

        public ForAllWindowsConsumerWrapper() {
        }

        public final boolean apply(Object obj) {
            this.mConsumer.accept((WindowState) obj);
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteToken extends IWindowContainerToken.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final WeakReference mWeakRef;
        public WindowContainerToken mWindowContainerToken;

        public RemoteToken(WindowContainer windowContainer) {
            this.mWeakRef = new WeakReference(windowContainer);
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "RemoteToken{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(' ');
            m.append(this.mWeakRef.get());
            m.append('}');
            return m.toString();
        }

        public final WindowContainerToken toWindowContainerToken() {
            if (this.mWindowContainerToken == null) {
                this.mWindowContainerToken = new WindowContainerToken(this);
            }
            return this.mWindowContainerToken;
        }
    }

    public WindowContainer(WindowManagerService windowManagerService) {
        this.mWmService = windowManagerService;
        this.mTransitionController = windowManagerService.mAtmService.mWindowOrganizerController.mTransitionController;
        this.mPendingTransaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        this.mSyncTransaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        this.mSurfaceAnimator = new SurfaceAnimator(this, new WindowContainer$$ExternalSyntheticLambda5(0, this), windowManagerService);
        this.mSurfaceFreezer = new SurfaceFreezer(this, windowManagerService);
        this.mExt = windowManagerService.mExt;
        if (CoreRune.FW_CUSTOM_BASIC_ANIM_WITH_DIM) {
            this.mDimAnimator = new DimAnimator(this);
        }
    }

    public static Predicate alwaysTruePredicate() {
        return AlwaysTruePredicate.INSTANCE;
    }

    public static int computeScreenLayout(int i, int i2, int i3) {
        return Configuration.reduceScreenLayout(i & 63, Math.max(i2, i3), Math.min(i2, i3));
    }

    public static SparseArray createMergedSparseArray(SparseArray sparseArray, SparseArray sparseArray2) {
        int size = sparseArray != null ? sparseArray.size() : 0;
        int size2 = sparseArray2 != null ? sparseArray2.size() : 0;
        SparseArray sparseArray3 = new SparseArray(size + size2);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                sparseArray3.append(sparseArray.keyAt(i), sparseArray.valueAt(i));
            }
        }
        if (size2 > 0) {
            for (int i2 = 0; i2 < size2; i2++) {
                sparseArray3.put(sparseArray2.keyAt(i2), sparseArray2.valueAt(i2));
            }
        }
        return sparseArray3;
    }

    public static void enforceSurfaceVisible(WindowContainer windowContainer) {
        if (windowContainer.mSurfaceControl == null) {
            return;
        }
        windowContainer.getSyncTransaction().show(windowContainer.mSurfaceControl);
        ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        if (asActivityRecord != null) {
            asActivityRecord.mLastSurfaceShowing = true;
        }
        for (WindowContainer parent = windowContainer.getParent(); parent != null && parent != windowContainer.mDisplayContent; parent = parent.getParent()) {
            if (parent.mSurfaceControl != null) {
                parent.getSyncTransaction().show(parent.mSurfaceControl);
                Task asTask = parent.asTask();
                if (asTask != null) {
                    asTask.mLastSurfaceShowing = true;
                }
            }
        }
        windowContainer.scheduleAnimation();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static WindowContainer fromBinder(IBinder iBinder) {
        int i = RemoteToken.$r8$clinit;
        return (WindowContainer) ((RemoteToken) iBinder).mWeakRef.get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.wm.ConfigurationContainerListener, com.android.server.wm.WindowContainer$1] */
    public static WindowContainerListener overrideConfigurationPropagation(WindowContainer windowContainer, final WindowContainer windowContainer2, final ConfigurationMerger configurationMerger) {
        final ?? r0 = new ConfigurationContainerListener(windowContainer) { // from class: com.android.server.wm.WindowContainer.1
            public final /* synthetic */ WindowContainer val$receiver;

            {
                this.val$receiver = windowContainer;
            }

            @Override // com.android.server.wm.ConfigurationContainerListener
            public final void onMergedOverrideConfigurationChanged(Configuration configuration) {
                Configuration configuration2;
                WindowContainer windowContainer3 = this.val$receiver;
                ConfigurationMerger configurationMerger2 = configurationMerger;
                if (configurationMerger2 != null) {
                    configuration2 = windowContainer3.getRequestedOverrideConfiguration();
                    TransparentPolicy$TransparentPolicyState$$ExternalSyntheticLambda1 transparentPolicy$TransparentPolicyState$$ExternalSyntheticLambda1 = (TransparentPolicy$TransparentPolicyState$$ExternalSyntheticLambda1) configurationMerger2;
                    TransparentPolicy.TransparentPolicyState transparentPolicyState = transparentPolicy$TransparentPolicyState$$ExternalSyntheticLambda1.f$0;
                    ActivityRecord activityRecord = transparentPolicyState.mActivityRecord;
                    int overrideOrientation = activityRecord.getOverrideOrientation();
                    if (overrideOrientation == -1 || !activityRecord.handlesOrientationChangeFromDescendant(overrideOrientation)) {
                        configuration2.orientation = 0;
                        configuration2.compatScreenWidthDp = 0;
                        configuration2.screenWidthDp = 0;
                        configuration2.compatScreenHeightDp = 0;
                        configuration2.screenHeightDp = 0;
                        configuration2.compatSmallestScreenWidthDp = 0;
                        configuration2.smallestScreenWidthDp = 0;
                        Rect bounds = transparentPolicy$TransparentPolicyState$$ExternalSyntheticLambda1.f$1.getWindowConfiguration().getBounds();
                        Rect bounds2 = configuration2.windowConfiguration.getBounds();
                        Rect bounds3 = configuration.windowConfiguration.getBounds();
                        int i = bounds.left;
                        bounds2.set(i, bounds.top, bounds3.width() + i, bounds3.height() + bounds.top);
                        configuration2.windowConfiguration.setAppBounds(new Rect());
                        transparentPolicyState.inheritFromOpaque(transparentPolicyState.mFirstOpaqueActivity);
                    } else {
                        configuration2.unset();
                    }
                } else {
                    configuration2 = windowContainer2.getConfiguration();
                }
                windowContainer3.onRequestedOverrideConfigurationChanged(configuration2);
            }
        };
        windowContainer2.registerConfigurationChangeListener(r0);
        WindowContainerListener windowContainerListener = new WindowContainerListener() { // from class: com.android.server.wm.WindowContainer.2
            @Override // com.android.server.wm.WindowContainerListener
            public final void onRemoved() {
                WindowContainer.this.unregisterWindowContainerListener(this);
                windowContainer2.unregisterConfigurationChangeListener(r0);
            }
        };
        windowContainer.registerWindowContainerListener(windowContainerListener);
        return windowContainerListener;
    }

    public static void overrideConfigurationPropagation(WindowContainer windowContainer, WindowContainer windowContainer2) {
        overrideConfigurationPropagation(windowContainer, windowContainer2, null);
    }

    public void addChild(WindowContainer windowContainer, int i) {
        if (!windowContainer.mReparenting && windowContainer.getParent() != null) {
            throw new IllegalArgumentException("addChild: container=" + windowContainer.getName() + " is already a child of container=" + windowContainer.getParent().getName() + " can't add to container=" + getName() + "\n callers=" + Debug.getCallers(15, "\n"));
        }
        if ((i < 0 && i != POSITION_BOTTOM) || (i > this.mChildren.size() && i != Integer.MAX_VALUE)) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "addChild: invalid position=", ", children number=");
            m.append(this.mChildren.size());
            throw new IllegalArgumentException(m.toString());
        }
        if (i == Integer.MAX_VALUE) {
            i = this.mChildren.size();
        } else if (i == POSITION_BOTTOM) {
            i = 0;
        }
        this.mChildren.add(i, windowContainer);
        windowContainer.setParent(this);
    }

    public void addChild(WindowContainer windowContainer, Comparator comparator) {
        int i;
        if (!windowContainer.mReparenting && windowContainer.getParent() != null) {
            throw new IllegalArgumentException("addChild: container=" + windowContainer.getName() + " is already a child of container=" + windowContainer.getParent().getName() + " can't add to container=" + getName());
        }
        if (comparator != null) {
            int size = this.mChildren.size();
            i = 0;
            while (i < size) {
                if (comparator.compare(windowContainer, (WindowContainer) this.mChildren.get(i)) < 0) {
                    break;
                } else {
                    i++;
                }
            }
        }
        i = -1;
        if (i == -1) {
            this.mChildren.add(windowContainer);
        } else {
            this.mChildren.add(i, windowContainer);
        }
        windowContainer.setParent(this);
    }

    public void addLocalInsetsFrameProvider(InsetsFrameProvider insetsFrameProvider, IBinder iBinder) {
        if (insetsFrameProvider == null || iBinder == null) {
            throw new IllegalArgumentException("Insets provider or owner not specified.");
        }
        if (this.mDisplayContent == null) {
            Slog.w(TAG, "Can't add insets frame provider when detached. " + this);
            return;
        }
        if (this.mInsetsOwnerDeathRecipientMap == null) {
            this.mInsetsOwnerDeathRecipientMap = new ArrayMap();
        }
        DeathRecipient deathRecipient = (DeathRecipient) this.mInsetsOwnerDeathRecipientMap.get(iBinder);
        if (deathRecipient == null) {
            deathRecipient = new DeathRecipient(iBinder, this);
            try {
                iBinder.linkToDeath(deathRecipient, 0);
                this.mInsetsOwnerDeathRecipientMap.put(iBinder, deathRecipient);
            } catch (RemoteException unused) {
                Slog.w(TAG, "Failed to add source for " + insetsFrameProvider + " since the owner has died.");
                return;
            }
        }
        int id = insetsFrameProvider.getId();
        deathRecipient.mSourceIds.add(Integer.valueOf(id));
        if (this.mLocalInsetsSources == null) {
            this.mLocalInsetsSources = new SparseArray();
        }
        this.mLocalInsetsSources.get(id);
        InsetsSource insetsSource = new InsetsSource(id, insetsFrameProvider.getType());
        insetsSource.setFrame(insetsFrameProvider.getArbitraryRectangle()).updateSideHint(getBounds()).setBoundingRects(insetsFrameProvider.getBoundingRects());
        insetsSource.setFlags(insetsFrameProvider.getFlags());
        this.mLocalInsetsSources.put(id, insetsSource);
        this.mDisplayContent.mInsetsStateController.updateAboveInsetsState(true);
    }

    public void addTrustedOverlay(SurfaceControlViewHost.SurfacePackage surfacePackage, WindowState windowState) {
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled;
        if (this.mOverlayHost == null) {
            this.mOverlayHost = new TrustedOverlayHost(this.mWmService);
        }
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        SurfaceControl surfaceControl = this.mSurfaceControl;
        SurfaceControl surfaceControl2 = trustedOverlayHost.mSurfaceControl;
        WindowManagerService windowManagerService = trustedOverlayHost.mWmService;
        if (surfaceControl2 == null) {
            trustedOverlayHost.mSurfaceControl = windowManagerService.makeSurfaceBuilder(null).setContainerLayer().setHidden(true).setCallsite("TrustedOverlayHost.requireOverlaySurfaceControl").setName("Overlay Host Leash").build();
            ((SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get()).setTrustedOverlay(trustedOverlayHost.mSurfaceControl, true).apply();
        }
        boolean z = false;
        for (int size = trustedOverlayHost.mOverlays.size() - 1; size >= 0; size--) {
            if (((SurfaceControlViewHost.SurfacePackage) trustedOverlayHost.mOverlays.get(size)).getSurfaceControl().isSameSurface(surfacePackage.getSurfaceControl())) {
                z = true;
            }
        }
        if (!z) {
            trustedOverlayHost.mOverlays.add(surfacePackage);
        }
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        transaction.reparent(surfacePackage.getSurfaceControl(), trustedOverlayHost.mSurfaceControl).show(surfacePackage.getSurfaceControl());
        SurfaceControl surfaceControl3 = trustedOverlayHost.mSurfaceControl;
        if (surfaceControl3 != null) {
            transaction.reparent(surfaceControl3, surfaceControl);
            if (surfaceControl != null) {
                transaction.show(trustedOverlayHost.mSurfaceControl);
            } else {
                transaction.hide(trustedOverlayHost.mSurfaceControl);
            }
        }
        transaction.apply();
        try {
            surfacePackage.getRemoteInterface().onConfigurationChanged(getConfiguration());
        } catch (Exception unused) {
            if (zArr[4]) {
                ProtoLogImpl_54989576.e(ProtoLogGroup.WM_DEBUG_ANIM, -4267530270533009730L, 0, null, null);
            }
            removeTrustedOverlay(surfacePackage);
        }
        if (windowState != null) {
            try {
                surfacePackage.getRemoteInterface().onInsetsChanged(windowState.getInsetsState(false), getBounds());
            } catch (Exception unused2) {
                if (zArr[4]) {
                    ProtoLogImpl_54989576.e(ProtoLogGroup.WM_DEBUG_ANIM, 5179630990780610966L, 0, null, null);
                }
                removeTrustedOverlay(surfacePackage);
            }
        }
    }

    public boolean allSyncFinished() {
        if (!isVisibleRequested()) {
            return true;
        }
        if (this.mSyncState != 2) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (!((WindowContainer) this.mChildren.get(size)).allSyncFinished()) {
                return false;
            }
        }
        return true;
    }

    public boolean allowEdgeExtension() {
        return true;
    }

    public boolean applyAnimation(WindowManager.LayoutParams layoutParams, int i, boolean z, boolean z2, ArrayList arrayList) {
        boolean z3 = this.mWmService.mDisableTransitionAnimation;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_ANIM_enabled;
        if (z3) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -8730310387200541562L, 0, null, String.valueOf(this));
            }
            cancelAnimation();
            return false;
        }
        try {
            Trace.traceBegin(32L, "WC#applyAnimation");
            if (okToAnimate()) {
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 2363818604357955690L, 12, null, String.valueOf(AppTransition.appTransitionOldToString(i)), Boolean.valueOf(z), String.valueOf(this));
                }
                applyAnimationUnchecked(layoutParams, z, i, z2, arrayList);
            } else {
                cancelAnimation();
            }
            Trace.traceEnd(32L);
            return isAnimating();
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0077, code lost:
    
        if (r14 != 18) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x008c, code lost:
    
        if (r14 != 30) goto L61;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void applyAnimationUnchecked(android.view.WindowManager.LayoutParams r12, boolean r13, int r14, boolean r15, java.util.ArrayList r16) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowContainer.applyAnimationUnchecked(android.view.WindowManager$LayoutParams, boolean, int, boolean, java.util.ArrayList):void");
    }

    public void applyMagnificationSpec(SurfaceControl.Transaction transaction, MagnificationSpec magnificationSpec) {
        if (!shouldMagnify()) {
            clearMagnificationSpec(transaction);
            for (int i = 0; i < this.mChildren.size(); i++) {
                ((WindowContainer) this.mChildren.get(i)).applyMagnificationSpec(transaction, magnificationSpec);
            }
            return;
        }
        SurfaceControl surfaceControl = this.mSurfaceControl;
        float f = magnificationSpec.scale;
        SurfaceControl.Transaction matrix = transaction.setMatrix(surfaceControl, f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, f);
        SurfaceControl surfaceControl2 = this.mSurfaceControl;
        float f2 = magnificationSpec.offsetX;
        Point point = this.mLastSurfacePosition;
        matrix.setPosition(surfaceControl2, f2 + point.x, magnificationSpec.offsetY + point.y);
        this.mLastMagnificationSpec = magnificationSpec;
    }

    public ActivityRecord asActivityRecord() {
        return null;
    }

    public DisplayArea asDisplayArea() {
        return null;
    }

    public DisplayContent asDisplayContent() {
        return null;
    }

    public RootDisplayArea asRootDisplayArea() {
        return null;
    }

    public Task asTask() {
        return null;
    }

    public TaskDisplayArea asTaskDisplayArea() {
        return null;
    }

    public TaskFragment asTaskFragment() {
        return null;
    }

    public TransientLaunchOverlayToken asTransientLaunchOverlay() {
        return null;
    }

    public WallpaperWindowToken asWallpaperToken() {
        return null;
    }

    public WindowState asWindowState() {
        return null;
    }

    public WindowToken asWindowToken() {
        return null;
    }

    public void assignChildLayers() {
        assignChildLayers(getSyncTransaction());
        scheduleAnimation();
    }

    public void assignChildLayers(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl;
        int i = 0;
        for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i2);
            windowContainer.assignChildLayers(transaction);
            if (!windowContainer.needsZBoost()) {
                windowContainer.assignLayer(transaction, i);
                i++;
            }
        }
        for (int i3 = 0; i3 < this.mChildren.size(); i3++) {
            WindowContainer windowContainer2 = (WindowContainer) this.mChildren.get(i3);
            if (windowContainer2.needsZBoost()) {
                windowContainer2.assignLayer(transaction, i);
                i++;
            }
        }
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost == null || (surfaceControl = trustedOverlayHost.mSurfaceControl) == null) {
            return;
        }
        transaction.setLayer(surfaceControl, i);
    }

    public void assignLayer(SurfaceControl.Transaction transaction, int i) {
        int i2;
        Transition transition;
        Transition.ChangeInfo changeInfo;
        TransitionController transitionController = this.mTransitionController;
        boolean z = true;
        if (transitionController.mBuildingFinishLayers) {
            if (asWindowState() != null) {
                return;
            }
        } else if (!CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION || (transition = transitionController.mCollectingTransition) == null || (changeInfo = (Transition.ChangeInfo) transition.mChanges.get(this)) == null || changeInfo.mMinimizeAnimState != 2) {
            if (CoreRune.MW_NATURAL_SWITCHING_PIP && asTask() != null) {
                if (transitionController.mAtm.mNaturalSwitchingController.mNaturalSwitchingPipTask == asTask()) {
                    return;
                }
            }
            if (CoreRune.MD_DEX_SHELL_TRANSITION && asTask() != null && inFreeformWindowingMode() && isDesktopModeEnabled()) {
                for (int size = transitionController.mPlayingTransitions.size() - 1; size >= 0; size--) {
                    TransitionInfo transitionInfo = ((Transition) transitionController.mPlayingTransitions.get(size)).mLogger.mInfo;
                    if (transitionInfo == null || transitionInfo.getRootCount() <= 0) {
                    }
                }
            }
            if ((asTask() == null || !inFreeformWindowingMode() || !transitionController.isCollecting() || ((i2 = transitionController.mCollectingTransition.mType) != 2 && i2 != 1)) && asWindowState() == null) {
                if (!transitionController.mPlayingTransitions.isEmpty()) {
                    return;
                }
                if (asTask() != null && transitionController.isCollecting()) {
                    return;
                }
            }
        }
        if (i == this.mLastLayer && this.mLastRelativeToLayer == null) {
            z = false;
        }
        if (this.mSurfaceControl == null || !z) {
            return;
        }
        setLayer(transaction, i);
        this.mLastLayer = i;
        this.mLastRelativeToLayer = null;
    }

    public void assignRelativeLayer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i) {
        assignRelativeLayer(transaction, surfaceControl, i, false);
    }

    public void assignRelativeLayer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i, boolean z) {
        boolean z2 = (i == this.mLastLayer && this.mLastRelativeToLayer == surfaceControl) ? false : true;
        if (this.mSurfaceControl != null) {
            if (z2 || z) {
                setRelativeLayer(transaction, surfaceControl, i);
                this.mLastLayer = i;
                this.mLastRelativeToLayer = surfaceControl;
            }
        }
    }

    public boolean canCreateRemoteAnimationTarget() {
        return this instanceof ActivityRecord;
    }

    public boolean canCustomizeAppTransition() {
        return false;
    }

    public boolean canStartChangeTransition() {
        if (this.mWmService.mDisableTransitionAnimation || !okToAnimate() || this.mDisplayContent == null || getSurfaceControl() == null || !isVisible() || !isVisibleRequested() || this.mDisplayContent.inTransition()) {
            return false;
        }
        if (ActivityTaskManagerService.isPip2ExperimentEnabled()) {
            return true;
        }
        return (inPinnedWindowingMode() || getParent() == null || getParent().inPinnedWindowingMode()) ? false : true;
    }

    public void cancelAnimation() {
        SurfaceAnimator surfaceAnimator = this.mSurfaceAnimator;
        doAnimationFinished(surfaceAnimator.mAnimationType, surfaceAnimator.mAnimation);
        this.mSurfaceAnimator.cancelAnimation();
        this.mSurfaceFreezer.unfreeze(getSyncTransaction());
    }

    public void checkAppWindowsReadyToShow() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).checkAppWindowsReadyToShow();
        }
    }

    public void clearMagnificationSpec(SurfaceControl.Transaction transaction) {
        if (this.mLastMagnificationSpec != null) {
            SurfaceControl.Transaction matrix = transaction.setMatrix(this.mSurfaceControl, 1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
            SurfaceControl surfaceControl = this.mSurfaceControl;
            Point point = this.mLastSurfacePosition;
            matrix.setPosition(surfaceControl, point.x, point.y);
        }
        this.mLastMagnificationSpec = null;
        for (int i = 0; i < this.mChildren.size(); i++) {
            ((WindowContainer) this.mChildren.get(i)).clearMagnificationSpec(transaction);
        }
    }

    public void commitPendingTransaction() {
        scheduleAnimation();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0067, code lost:
    
        if (r6 != r7) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0073, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0074, code lost:
    
        if (r6 != r8) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0080, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0081, code lost:
    
        r8 = r6.mChildren;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0093, code lost:
    
        if (r8.indexOf(r0.peekLast()) <= r8.indexOf(r3.peekLast())) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0095, code lost:
    
        r1 = 1;
     */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int compareTo(com.android.server.wm.WindowContainer r8) {
        /*
            r7 = this;
            if (r7 != r8) goto L4
            r7 = 0
            return r7
        L4:
            com.android.server.wm.WindowContainer r0 = r7.mParent
            r1 = -1
            r2 = 1
            if (r0 == 0) goto L1c
            com.android.server.wm.WindowContainer r3 = r8.mParent
            if (r0 != r3) goto L1c
            com.android.server.wm.WindowList r0 = r0.mChildren
            int r7 = r0.indexOf(r7)
            int r8 = r0.indexOf(r8)
            if (r7 <= r8) goto L1b
            r1 = r2
        L1b:
            return r1
        L1c:
            java.util.LinkedList r0 = r7.mTmpChain1
            java.util.LinkedList r3 = r7.mTmpChain2
            r0.clear()     // Catch: java.lang.Throwable -> L63
            r4 = r7
        L24:
            r0.addLast(r4)     // Catch: java.lang.Throwable -> L63
            com.android.server.wm.WindowContainer r4 = r4.mParent     // Catch: java.lang.Throwable -> L63
            if (r4 != 0) goto L24
            r8.getClass()     // Catch: java.lang.Throwable -> L63
            r3.clear()     // Catch: java.lang.Throwable -> L63
            r4 = r8
        L32:
            r3.addLast(r4)     // Catch: java.lang.Throwable -> L63
            com.android.server.wm.WindowContainer r4 = r4.mParent     // Catch: java.lang.Throwable -> L63
            if (r4 != 0) goto L32
            java.lang.Object r4 = r0.peekLast()     // Catch: java.lang.Throwable -> L63
            com.android.server.wm.WindowContainer r4 = (com.android.server.wm.WindowContainer) r4     // Catch: java.lang.Throwable -> L63
            java.lang.Object r5 = r3.peekLast()     // Catch: java.lang.Throwable -> L63
            com.android.server.wm.WindowContainer r5 = (com.android.server.wm.WindowContainer) r5     // Catch: java.lang.Throwable -> L63
            r6 = 0
        L46:
            if (r4 == 0) goto L65
            if (r5 == 0) goto L65
            if (r4 != r5) goto L65
            java.lang.Object r4 = r0.removeLast()     // Catch: java.lang.Throwable -> L63
            r6 = r4
            com.android.server.wm.WindowContainer r6 = (com.android.server.wm.WindowContainer) r6     // Catch: java.lang.Throwable -> L63
            r3.removeLast()     // Catch: java.lang.Throwable -> L63
            java.lang.Object r4 = r0.peekLast()     // Catch: java.lang.Throwable -> L63
            com.android.server.wm.WindowContainer r4 = (com.android.server.wm.WindowContainer) r4     // Catch: java.lang.Throwable -> L63
            java.lang.Object r5 = r3.peekLast()     // Catch: java.lang.Throwable -> L63
            com.android.server.wm.WindowContainer r5 = (com.android.server.wm.WindowContainer) r5     // Catch: java.lang.Throwable -> L63
            goto L46
        L63:
            r8 = move-exception
            goto Lb6
        L65:
            if (r6 == 0) goto L97
            if (r6 != r7) goto L74
        L69:
            java.util.LinkedList r8 = r7.mTmpChain1
            r8.clear()
            java.util.LinkedList r7 = r7.mTmpChain2
            r7.clear()
            return r1
        L74:
            if (r6 != r8) goto L81
            java.util.LinkedList r8 = r7.mTmpChain1
            r8.clear()
            java.util.LinkedList r7 = r7.mTmpChain2
            r7.clear()
            return r2
        L81:
            com.android.server.wm.WindowList r8 = r6.mChildren     // Catch: java.lang.Throwable -> L63
            java.lang.Object r0 = r0.peekLast()     // Catch: java.lang.Throwable -> L63
            int r0 = r8.indexOf(r0)     // Catch: java.lang.Throwable -> L63
            java.lang.Object r3 = r3.peekLast()     // Catch: java.lang.Throwable -> L63
            int r8 = r8.indexOf(r3)     // Catch: java.lang.Throwable -> L63
            if (r0 <= r8) goto L69
            r1 = r2
            goto L69
        L97:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L63
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L63
            r1.<init>()     // Catch: java.lang.Throwable -> L63
            java.lang.String r2 = "No in the same hierarchy this="
            r1.append(r2)     // Catch: java.lang.Throwable -> L63
            r1.append(r0)     // Catch: java.lang.Throwable -> L63
            java.lang.String r0 = " other="
            r1.append(r0)     // Catch: java.lang.Throwable -> L63
            r1.append(r3)     // Catch: java.lang.Throwable -> L63
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L63
            r8.<init>(r0)     // Catch: java.lang.Throwable -> L63
            throw r8     // Catch: java.lang.Throwable -> L63
        Lb6:
            java.util.LinkedList r0 = r7.mTmpChain1
            r0.clear()
            java.util.LinkedList r7 = r7.mTmpChain2
            r7.clear()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowContainer.compareTo(com.android.server.wm.WindowContainer):int");
    }

    public RemoteAnimationTarget createRemoteAnimationTarget(RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        return null;
    }

    public void createSurfaceControl(boolean z) {
        setInitialSurfaceControlProperties(makeSurface());
    }

    public final void doAnimationFinished(int i, AnimationAdapter animationAdapter) {
        for (int i2 = 0; i2 < this.mSurfaceAnimationSources.size(); i2++) {
            ((WindowContainer) this.mSurfaceAnimationSources.valueAt(i2)).onAnimationFinished(i, animationAdapter);
        }
        this.mSurfaceAnimationSources.clear();
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            if (displayContent.mImeScreenshot != null && ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled[2]) {
                ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_IME, -124113386733162358L, 0, null, String.valueOf(this), String.valueOf(SurfaceAnimator.animationTypeToString(i)), String.valueOf(displayContent.mImeScreenshot), String.valueOf(displayContent.mImeScreenshot.mImeTarget));
            }
            if ((i & 25) != 0) {
                displayContent.removeImeSurfaceByTarget(this);
            }
        }
    }

    public void dump(PrintWriter printWriter, String str, boolean z) {
        if (this.mSurfaceAnimator.isAnimating()) {
            printWriter.print(str);
            printWriter.println("ContainerAnimator:");
            SurfaceAnimator surfaceAnimator = this.mSurfaceAnimator;
            String str2 = str + "  ";
            surfaceAnimator.getClass();
            printWriter.print(str2);
            printWriter.print("mLeash=");
            printWriter.print(surfaceAnimator.mLeash);
            printWriter.print(" mAnimationType=" + SurfaceAnimator.animationTypeToString(surfaceAnimator.mAnimationType));
            ProcessList$$ExternalSyntheticOutline0.m(printWriter, surfaceAnimator.mAnimationStartDelayed ? " mAnimationStartDelayed=true" : "", str2, "Animation: ");
            printWriter.println(surfaceAnimator.mAnimation);
            AnimationAdapter animationAdapter = surfaceAnimator.mAnimation;
            if (animationAdapter != null) {
                animationAdapter.dump(printWriter, str2 + "  ");
            }
        }
        if (this.mLastOrientationSource != null && this == this.mDisplayContent) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mLastOrientationSource=");
            m.append(this.mLastOrientationSource);
            printWriter.println(m.toString());
            printWriter.println(str + "deepestLastOrientationSource=" + getLastOrientationSource());
        }
        SparseArray sparseArray = this.mLocalInsetsSources;
        if (sparseArray != null && sparseArray.size() != 0) {
            StringBuilder m2 = BootReceiver$$ExternalSyntheticOutline0.m(str);
            m2.append(this.mLocalInsetsSources.size());
            m2.append(" LocalInsetsSources");
            printWriter.println(m2.toString());
            String str3 = str + "  ";
            for (int i = 0; i < this.mLocalInsetsSources.size(); i++) {
                ((InsetsSource) this.mLocalInsetsSources.valueAt(i)).dump(str3, printWriter);
            }
        }
        if (this.mUseLayoutInUdcCutout || this.mUseConfigurationInUdcCutout) {
            printWriter.print(str + "UdcCutoutInfo:");
            StringBuilder sb = new StringBuilder(" mUseLayoutInUdcCutout=");
            sb.append(this.mUseLayoutInUdcCutout);
            printWriter.print(sb.toString());
            printWriter.print(" mUseConfigurationInUdcCutout=" + this.mUseConfigurationInUdcCutout);
            printWriter.println();
        }
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            if (this.mSyncState != 0) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str, "mSyncState="), this.mSyncState, printWriter);
            }
            if (this.mSyncTransactionCommitCallbackDepth > 0) {
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str, "mSyncTransactionCommitCallbackDepth="), this.mSyncTransactionCommitCallbackDepth, printWriter);
            }
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        boolean isVisible = isVisible();
        if (i != 2 || isVisible) {
            long start = protoOutputStream.start(j);
            super.dumpDebug(protoOutputStream, 1146756268033L, i);
            protoOutputStream.write(1120986464258L, this.mOverrideOrientation);
            protoOutputStream.write(1133871366147L, isVisible);
            writeIdentifierToProto(protoOutputStream, 1146756268038L);
            if (this.mSurfaceAnimator.isAnimating()) {
                this.mSurfaceAnimator.dumpDebug(protoOutputStream, 1146756268036L);
            }
            SurfaceControl surfaceControl = this.mSurfaceControl;
            if (surfaceControl != null) {
                surfaceControl.dumpDebug(protoOutputStream, 1146756268039L);
            }
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                long start2 = protoOutputStream.start(2246267895813L);
                WindowContainer childAt = getChildAt(i2);
                childAt.dumpDebug(protoOutputStream, childAt.getProtoFieldId(), i);
                protoOutputStream.end(start2);
            }
            protoOutputStream.end(start);
        }
    }

    public void endDelayingAnimationStart() {
        this.mSurfaceAnimator.endDelayingAnimationStart();
    }

    public boolean fillsParent() {
        return this instanceof WallpaperWindowToken;
    }

    public void finishSync(SurfaceControl.Transaction transaction, BLASTSyncEngine.SyncGroup syncGroup, boolean z) {
        BLASTSyncEngine.SyncGroup syncGroup2;
        if (this.mSyncState == 0) {
            if (this.mSyncGroup != null) {
                Slog.e(TAG, "finishSync: stale group " + this.mSyncGroup.mSyncId + " of " + this);
                this.mSyncGroup = null;
            }
            if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
                for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                    ((WindowContainer) this.mChildren.get(size)).finishSync(transaction, syncGroup, z);
                }
                return;
            }
            return;
        }
        if (isDifferentSyncGroup(syncGroup)) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_SYNC_ENGINE_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -3871009616397322067L, 3, null, Boolean.valueOf(z), String.valueOf(this));
        }
        transaction.merge(this.mSyncTransaction);
        for (int size2 = this.mChildren.size() - 1; size2 >= 0; size2--) {
            ((WindowContainer) this.mChildren.get(size2)).finishSync(transaction, syncGroup, z);
        }
        if (z && (syncGroup2 = this.mSyncGroup) != null) {
            syncGroup2.mRootMembers.remove(this);
        }
        this.mSyncState = 0;
        this.mSyncMethodOverride = -1;
        this.mSyncGroup = null;
        this.mSyncIdForReparentSurfaceControl = -1;
    }

    public void forAllActivities(Consumer consumer) {
        forAllActivities(consumer, true);
    }

    public void forAllActivities(Consumer consumer, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                ((WindowContainer) this.mChildren.get(size)).forAllActivities(consumer, z);
            }
            return;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            ((WindowContainer) this.mChildren.get(i)).forAllActivities(consumer, z);
        }
    }

    public boolean forAllActivities(Predicate predicate) {
        return forAllActivities(predicate, true);
    }

    public final boolean forAllActivities(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2) {
        return forAllActivities(predicate, windowContainer, z, z2, new boolean[1]);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003d A[LOOP:0: B:4:0x0012->B:11:0x003d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0072 A[LOOP:1: B:22:0x0047->B:29:0x0072, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0071 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean forAllActivities(java.util.function.Predicate r15, com.android.server.wm.WindowContainer r16, boolean r17, boolean r18, boolean[] r19) {
        /*
            r14 = this;
            r0 = r14
            r7 = r15
            r8 = r16
            r9 = r18
            r10 = 0
            r11 = 1
            if (r9 == 0) goto L40
            com.android.server.wm.WindowList r1 = r0.mChildren
            int r1 = r1.size()
            int r1 = r1 - r11
            r12 = r1
        L12:
            if (r12 < 0) goto L75
            com.android.server.wm.WindowList r1 = r0.mChildren
            java.lang.Object r1 = r1.get(r12)
            com.android.server.wm.WindowContainer r1 = (com.android.server.wm.WindowContainer) r1
            if (r1 != r8) goto L24
            r19[r10] = r11
            if (r17 != 0) goto L24
            r1 = r10
            goto L3a
        L24:
            boolean r2 = r19[r10]
            if (r2 == 0) goto L2d
            boolean r1 = r1.forAllActivities(r15, r9)
            goto L3a
        L2d:
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            boolean r1 = r1.forAllActivities(r2, r3, r4, r5, r6)
        L3a:
            if (r1 == 0) goto L3d
            return r11
        L3d:
            int r12 = r12 + (-1)
            goto L12
        L40:
            com.android.server.wm.WindowList r1 = r0.mChildren
            int r12 = r1.size()
            r13 = r10
        L47:
            if (r13 >= r12) goto L75
            com.android.server.wm.WindowList r1 = r0.mChildren
            java.lang.Object r1 = r1.get(r13)
            com.android.server.wm.WindowContainer r1 = (com.android.server.wm.WindowContainer) r1
            if (r1 != r8) goto L59
            r19[r10] = r11
            if (r17 != 0) goto L59
            r1 = r10
            goto L6f
        L59:
            boolean r2 = r19[r10]
            if (r2 == 0) goto L62
            boolean r1 = r1.forAllActivities(r15, r9)
            goto L6f
        L62:
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r19
            boolean r1 = r1.forAllActivities(r2, r3, r4, r5, r6)
        L6f:
            if (r1 == 0) goto L72
            return r11
        L72:
            int r13 = r13 + 1
            goto L47
        L75:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowContainer.forAllActivities(java.util.function.Predicate, com.android.server.wm.WindowContainer, boolean, boolean, boolean[]):boolean");
    }

    public boolean forAllActivities(Predicate predicate, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (((WindowContainer) this.mChildren.get(size)).forAllActivities(predicate, z)) {
                    return true;
                }
            }
        } else {
            int size2 = this.mChildren.size();
            for (int i = 0; i < size2; i++) {
                if (((WindowContainer) this.mChildren.get(i)).forAllActivities(predicate, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void forAllDisplayAreas(Consumer consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).forAllDisplayAreas(consumer);
        }
    }

    public void forAllLeafTaskFragments(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllLeafTaskFragments(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            ((WindowContainer) this.mChildren.get(i2)).forAllLeafTaskFragments(consumer, z);
        }
    }

    public boolean forAllLeafTaskFragments(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).forAllLeafTaskFragments(predicate)) {
                return true;
            }
        }
        return false;
    }

    public void forAllLeafTasks(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllLeafTasks(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            ((WindowContainer) this.mChildren.get(i2)).forAllLeafTasks(consumer, z);
        }
    }

    public boolean forAllLeafTasks(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).forAllLeafTasks(predicate)) {
                return true;
            }
        }
        return false;
    }

    public void forAllRootTasks(Consumer consumer) {
        forAllRootTasks(consumer, true);
    }

    public void forAllRootTasks(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllRootTasks(consumer, z);
            }
            return;
        }
        int i2 = 0;
        while (i2 < size) {
            ((WindowContainer) this.mChildren.get(i2)).forAllRootTasks(consumer, z);
            int size2 = this.mChildren.size();
            i2 = (i2 - (size - size2)) + 1;
            size = size2;
        }
    }

    public boolean forAllRootTasks(Predicate predicate) {
        return forAllRootTasks(predicate, true);
    }

    public boolean forAllRootTasks(Predicate predicate, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                if (((WindowContainer) this.mChildren.get(i)).forAllRootTasks(predicate, z)) {
                    return true;
                }
            }
        } else {
            int i2 = 0;
            while (i2 < size) {
                if (((WindowContainer) this.mChildren.get(i2)).forAllRootTasks(predicate, z)) {
                    return true;
                }
                int size2 = this.mChildren.size();
                i2 = (i2 - (size - size2)) + 1;
                size = size2;
            }
        }
        return false;
    }

    public void forAllTaskDisplayAreas(Consumer consumer) {
        forAllTaskDisplayAreas(consumer, true);
    }

    public void forAllTaskDisplayAreas(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        for (int i = size - 1; i >= 0 && i < size; i--) {
            ((WindowContainer) this.mChildren.get(i)).forAllTaskDisplayAreas(consumer, true);
        }
    }

    public boolean forAllTaskDisplayAreas(Predicate predicate) {
        return forAllTaskDisplayAreas(predicate, true);
    }

    public boolean forAllTaskDisplayAreas(Predicate predicate, boolean z) {
        int size = this.mChildren.size();
        for (int i = size - 1; i >= 0 && i < size; i--) {
            if (((WindowContainer) this.mChildren.get(i)).forAllTaskDisplayAreas(predicate, true)) {
                return true;
            }
        }
        return false;
    }

    public void forAllTaskFragments(Consumer consumer) {
        forAllTaskFragments(consumer, true);
    }

    public void forAllTaskFragments(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllTaskFragments(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            ((WindowContainer) this.mChildren.get(i2)).forAllTaskFragments(consumer, z);
        }
    }

    public void forAllTasks(Consumer consumer) {
        forAllTasks(consumer, true);
    }

    public void forAllTasks(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                ((WindowContainer) this.mChildren.get(i)).forAllTasks(consumer, z);
            }
            return;
        }
        for (int i2 = 0; i2 < size; i2++) {
            ((WindowContainer) this.mChildren.get(i2)).forAllTasks(consumer, z);
        }
    }

    public boolean forAllTasks(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).forAllTasks(predicate)) {
                return true;
            }
        }
        return false;
    }

    public void forAllWallpaperWindows(Consumer consumer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).forAllWallpaperWindows(consumer);
        }
    }

    public void forAllWindowContainers(Consumer consumer) {
        consumer.accept(this);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ((WindowContainer) this.mChildren.get(i)).forAllWindowContainers(consumer);
        }
    }

    public void forAllWindows(Consumer consumer, boolean z) {
        ForAllWindowsConsumerWrapper forAllWindowsConsumerWrapper = (ForAllWindowsConsumerWrapper) this.mConsumerWrapperPool.acquire();
        if (forAllWindowsConsumerWrapper == null) {
            forAllWindowsConsumerWrapper = new ForAllWindowsConsumerWrapper();
        }
        forAllWindowsConsumerWrapper.mConsumer = consumer;
        forAllWindows(forAllWindowsConsumerWrapper, z);
        forAllWindowsConsumerWrapper.mConsumer = null;
        WindowContainer.this.mConsumerWrapperPool.release(forAllWindowsConsumerWrapper);
    }

    public boolean forAllWindows(ToBooleanFunction toBooleanFunction, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (((WindowContainer) this.mChildren.get(size)).forAllWindows(toBooleanFunction, z)) {
                    return true;
                }
            }
        } else {
            int size2 = this.mChildren.size();
            for (int i = 0; i < size2; i++) {
                if (((WindowContainer) this.mChildren.get(i)).forAllWindows(toBooleanFunction, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ActivityRecord getActivity(Predicate predicate) {
        return getActivity(predicate, true);
    }

    public final ActivityRecord getActivity(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2) {
        return getActivity(predicate, windowContainer, z, z2, new boolean[1]);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0042 A[LOOP:0: B:4:0x0014->B:12:0x0042, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0041 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007a A[LOOP:1: B:23:0x004c->B:31:0x007a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0079 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.ActivityRecord getActivity(java.util.function.Predicate r16, com.android.server.wm.WindowContainer r17, boolean r18, boolean r19, boolean[] r20) {
        /*
            r15 = this;
            r0 = r15
            r7 = r16
            r8 = r17
            r9 = r19
            r10 = 0
            r11 = 0
            r12 = 1
            if (r9 == 0) goto L45
            com.android.server.wm.WindowList r1 = r0.mChildren
            int r1 = r1.size()
            int r1 = r1 - r12
            r13 = r1
        L14:
            if (r13 < 0) goto L7d
            com.android.server.wm.WindowList r1 = r0.mChildren
            java.lang.Object r1 = r1.get(r13)
            com.android.server.wm.WindowContainer r1 = (com.android.server.wm.WindowContainer) r1
            if (r1 == r8) goto L22
            if (r8 != 0) goto L28
        L22:
            r20[r11] = r12
            if (r18 != 0) goto L28
            r1 = r10
            goto L3f
        L28:
            boolean r2 = r20[r11]
            if (r2 == 0) goto L31
            com.android.server.wm.ActivityRecord r1 = r1.getActivity(r7, r9)
            goto L3f
        L31:
            r2 = r16
            r3 = r17
            r4 = r18
            r5 = r19
            r6 = r20
            com.android.server.wm.ActivityRecord r1 = r1.getActivity(r2, r3, r4, r5, r6)
        L3f:
            if (r1 == 0) goto L42
            return r1
        L42:
            int r13 = r13 + (-1)
            goto L14
        L45:
            com.android.server.wm.WindowList r1 = r0.mChildren
            int r13 = r1.size()
            r14 = r11
        L4c:
            if (r14 >= r13) goto L7d
            com.android.server.wm.WindowList r1 = r0.mChildren
            java.lang.Object r1 = r1.get(r14)
            com.android.server.wm.WindowContainer r1 = (com.android.server.wm.WindowContainer) r1
            if (r1 == r8) goto L5a
            if (r8 != 0) goto L60
        L5a:
            r20[r11] = r12
            if (r18 != 0) goto L60
            r1 = r10
            goto L77
        L60:
            boolean r2 = r20[r11]
            if (r2 == 0) goto L69
            com.android.server.wm.ActivityRecord r1 = r1.getActivity(r7, r9)
            goto L77
        L69:
            r2 = r16
            r3 = r17
            r4 = r18
            r5 = r19
            r6 = r20
            com.android.server.wm.ActivityRecord r1 = r1.getActivity(r2, r3, r4, r5, r6)
        L77:
            if (r1 == 0) goto L7a
            return r1
        L7a:
            int r14 = r14 + 1
            goto L4c
        L7d:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowContainer.getActivity(java.util.function.Predicate, com.android.server.wm.WindowContainer, boolean, boolean, boolean[]):com.android.server.wm.ActivityRecord");
    }

    public ActivityRecord getActivity(Predicate predicate, boolean z) {
        return getActivity(predicate, z, null);
    }

    public ActivityRecord getActivity(Predicate predicate, boolean z, ActivityRecord activityRecord) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
                if (windowContainer == activityRecord) {
                    return activityRecord;
                }
                ActivityRecord activity = windowContainer.getActivity(predicate, z, activityRecord);
                if (activity != null) {
                    return activity;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            WindowContainer windowContainer2 = (WindowContainer) this.mChildren.get(i);
            if (windowContainer2 == activityRecord) {
                return activityRecord;
            }
            ActivityRecord activity2 = windowContainer2.getActivity(predicate, z, activityRecord);
            if (activity2 != null) {
                return activity2;
            }
        }
        return null;
    }

    public ActivityRecord getActivityAbove(ActivityRecord activityRecord) {
        return getActivity(alwaysTruePredicate(), activityRecord, false, false);
    }

    public ActivityRecord getActivityBelow(ActivityRecord activityRecord) {
        return getActivity(alwaysTruePredicate(), activityRecord, false, true);
    }

    @Deprecated
    public final WindowContainer getAnimatingContainer() {
        return getAnimatingContainer(2, -1);
    }

    public WindowContainer getAnimatingContainer(int i, int i2) {
        if (isSelfAnimating(i, i2)) {
            return this;
        }
        if ((i & 2) != 0) {
            for (WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
                if (parent.isSelfAnimating(i, i2)) {
                    return parent;
                }
            }
        }
        if ((i & 4) == 0) {
            return null;
        }
        for (int i3 = 0; i3 < this.mChildren.size(); i3++) {
            WindowContainer animatingContainer = ((WindowContainer) this.mChildren.get(i3)).getAnimatingContainer(i & (-3), i2);
            if (animatingContainer != null) {
                return animatingContainer;
            }
        }
        return null;
    }

    public AnimationAdapter getAnimation() {
        return this.mSurfaceAnimator.mAnimation;
    }

    /* JADX WARN: Code restructure failed: missing block: B:191:0x0311, code lost:
    
        if (r54 == 10) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x0365, code lost:
    
        if (r54 == 11) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:413:0x05f0, code lost:
    
        if (r9 != false) goto L285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:414:0x05f2, code lost:
    
        r4 = 6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x05f4, code lost:
    
        r4 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:416:0x05f6, code lost:
    
        if (r9 != false) goto L288;
     */
    /* JADX WARN: Code restructure failed: missing block: B:417:0x05f8, code lost:
    
        r4 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:418:0x05fa, code lost:
    
        r4 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x05fc, code lost:
    
        if (r9 != false) goto L285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:420:0x05ff, code lost:
    
        if (r9 != false) goto L288;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:141:0x08db  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0832  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.util.Pair getAnimationAdapter(android.view.WindowManager.LayoutParams r53, int r54, boolean r55, boolean r56) {
        /*
            Method dump skipped, instructions count: 2454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowContainer.getAnimationAdapter(android.view.WindowManager$LayoutParams, int, boolean, boolean):android.util.Pair");
    }

    public Rect getAnimationBounds(int i) {
        return getBounds();
    }

    public void getAnimationFrames(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        DisplayInfo displayInfo = getDisplayContent().mDisplayInfo;
        rect.set(0, 0, displayInfo.appWidth, displayInfo.appHeight);
        rect2.setEmpty();
        rect3.setEmpty();
        rect4.setEmpty();
    }

    public SurfaceControl getAnimationLeash() {
        return this.mAnimationLeash;
    }

    public SurfaceControl getAnimationLeashParent() {
        return getParentSurfaceControl();
    }

    public void getAnimationPosition(Point point) {
        getRelativePosition(point);
    }

    public ArraySet getAnimationSources() {
        return this.mSurfaceAnimationSources;
    }

    public ActivityRecord getBottomMostActivity() {
        return getActivity(alwaysTruePredicate(), false);
    }

    public Task getBottomMostTask() {
        return getTask(alwaysTruePredicate(), false);
    }

    public int getCaptionHeight() {
        return getCaptionHeight(false, -1);
    }

    public int getCaptionHeight(boolean z) {
        return getCaptionHeight(z, -1);
    }

    public int getCaptionHeight(boolean z, int i) {
        DisplayPolicy displayPolicy;
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null || (displayPolicy = displayContent.mDisplayPolicy) == null) {
            return 0;
        }
        FreeformController freeformController = this.mWmService.mAtmService.mFreeformController;
        Resources currentUserResources = displayPolicy.getCurrentUserResources();
        if (i != -1) {
            freeformController.getClass();
        } else {
            i = freeformController.mFreeformCaptionType;
        }
        return isDexMode() ? (inFreeformWindowingMode() || z) ? currentUserResources.getDimensionPixelSize(17105844) : currentUserResources.getDimensionPixelSize(17105845) : MultiWindowManager.isCaptionTypeBar(i) ? currentUserResources.getDimensionPixelSize(17105842) : currentUserResources.getDimensionPixelSize(17105843);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public WindowContainer getChildAt(int i) {
        return (WindowContainer) this.mChildren.get(i);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public int getChildCount() {
        return this.mChildren.size();
    }

    public InsetsSourceProvider getControllableInsetProvider() {
        return this.mControllableInsetProvider;
    }

    public Dimmer getDimmer() {
        WindowContainer windowContainer = this.mParent;
        if (windowContainer == null) {
            return null;
        }
        return windowContainer.getDimmer();
    }

    public DisplayArea getDisplayArea() {
        WindowContainer parent = getParent();
        if (parent != null) {
            return parent.getDisplayArea();
        }
        return null;
    }

    public final DisplayContent getDisplayContent() {
        return this.mDisplayContent;
    }

    public int getDistanceFromTop(WindowContainer windowContainer) {
        int indexOf = this.mChildren.indexOf(windowContainer);
        if (indexOf < 0) {
            return -1;
        }
        return (this.mChildren.size() - 1) - indexOf;
    }

    public int getFreeformThickness() {
        DisplayPolicy displayPolicy;
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null || (displayPolicy = displayContent.mDisplayPolicy) == null) {
            return 0;
        }
        FreeformController freeformController = this.mWmService.mAtmService.mFreeformController;
        Resources currentUserResources = displayPolicy.getCurrentUserResources();
        freeformController.getClass();
        int dimensionPixelSize = currentUserResources.getDimensionPixelSize(17105848);
        return dimensionPixelSize % 2 == 0 ? dimensionPixelSize : dimensionPixelSize + 1;
    }

    public int getFreeformTopInset() {
        return getFreeformThickness();
    }

    public SurfaceControl getFreezeSnapshotTarget() {
        if (this.mDisplayContent.mAppTransition.containsTransitRequest(6) && this.mDisplayContent.mChangingContainers.contains(this)) {
            return getSurfaceControl();
        }
        return null;
    }

    public SparseArray getInsetsSourceProviders() {
        if (this.mInsetsSourceProviders == null) {
            this.mInsetsSourceProviders = new SparseArray();
        }
        return this.mInsetsSourceProviders;
    }

    public Object getItemFromDisplayAreas(Function function) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            Object itemFromDisplayAreas = ((WindowContainer) this.mChildren.get(size)).getItemFromDisplayAreas(function);
            if (itemFromDisplayAreas != null) {
                return itemFromDisplayAreas;
            }
        }
        return null;
    }

    public Object getItemFromTaskDisplayAreas(Function function) {
        return getItemFromTaskDisplayAreas(function, true);
    }

    public Object getItemFromTaskDisplayAreas(Function function, boolean z) {
        int size = this.mChildren.size();
        for (int i = size - 1; i >= 0 && i < size; i--) {
            Object itemFromTaskDisplayAreas = ((WindowContainer) this.mChildren.get(i)).getItemFromTaskDisplayAreas(function, true);
            if (itemFromTaskDisplayAreas != null) {
                return itemFromTaskDisplayAreas;
            }
        }
        return null;
    }

    public int getLastLayer() {
        return this.mLastLayer;
    }

    public WindowContainer getLastOrientationSource() {
        WindowContainer lastOrientationSource;
        WindowContainer windowContainer = this.mLastOrientationSource;
        return (windowContainer == null || windowContainer == this || (lastOrientationSource = windowContainer.getLastOrientationSource()) == null) ? windowContainer : lastOrientationSource;
    }

    public SurfaceControl getLastRelativeLayer() {
        return this.mLastRelativeToLayer;
    }

    public Point getLastSurfacePosition() {
        return this.mLastSurfacePosition;
    }

    public int getOrientation() {
        return getOrientation(getOverrideOrientation());
    }

    public int getOrientation(int i) {
        this.mLastOrientationSource = null;
        if (!providesOrientation()) {
            return -2;
        }
        if (getOverrideOrientation() != -2 && getOverrideOrientation() != -1) {
            this.mLastOrientationSource = this;
            return getOverrideOrientation();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            int orientation = windowContainer.getOrientation(i == 3 ? 3 : -2);
            if (orientation == 3) {
                this.mLastOrientationSource = windowContainer;
                i = orientation;
            } else if (orientation != -2 && (orientation != -1 || windowContainer.providesOrientation())) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, -5231580410559054259L, 4, null, String.valueOf(windowContainer.toString()), Long.valueOf(orientation), String.valueOf(ActivityInfo.screenOrientationToString(orientation)));
                }
                this.mLastOrientationSource = windowContainer;
                return orientation;
            }
        }
        return i;
    }

    public int getOverrideOrientation() {
        return this.mOverrideOrientation;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final WindowContainer getParent() {
        return this.mParent;
    }

    public SurfaceControl getParentSurfaceControl() {
        WindowContainer parent = getParent();
        if (parent == null) {
            return null;
        }
        return parent.getSurfaceControl();
    }

    public SurfaceControl.Transaction getPendingTransaction() {
        DisplayContent displayContent = getDisplayContent();
        return (displayContent == null || displayContent == this) ? this.mPendingTransaction : displayContent.getPendingTransaction();
    }

    public int getPrefixOrderIndex() {
        WindowContainer windowContainer = this.mParent;
        if (windowContainer == null) {
            return 0;
        }
        return windowContainer.getPrefixOrderIndex(this);
    }

    public final int getPrefixOrderIndex(WindowContainer windowContainer) {
        WindowContainer windowContainer2;
        int i = 0;
        for (int i2 = 0; i2 < this.mChildren.size() && windowContainer != (windowContainer2 = (WindowContainer) this.mChildren.get(i2)); i2++) {
            i += windowContainer2.mTreeWeight;
        }
        WindowContainer windowContainer3 = this.mParent;
        if (windowContainer3 != null) {
            i += windowContainer3.getPrefixOrderIndex(this);
        }
        return i + 1;
    }

    public long getProtoFieldId() {
        return 1146756268034L;
    }

    public int getRelativeDisplayRotation() {
        WindowContainer parent = getParent();
        if (parent == null) {
            return 0;
        }
        return RotationUtils.deltaRotation(getWindowConfiguration().getDisplayRotation(), parent.getWindowConfiguration().getDisplayRotation());
    }

    public void getRelativePosition(Point point) {
        getRelativePosition(getBounds(), point);
    }

    public void getRelativePosition(Rect rect, Point point) {
        point.set(rect.left, rect.top);
        WindowContainer parent = getParent();
        if (parent != null) {
            Rect bounds = parent.getBounds();
            point.offset(-bounds.left, -bounds.top);
        }
    }

    public RemoteAnimationDefinition getRemoteAnimationDefinition() {
        return null;
    }

    public int getRequestedConfigurationOrientation() {
        return getRequestedConfigurationOrientation(false);
    }

    public int getRequestedConfigurationOrientation(boolean z) {
        return getRequestedConfigurationOrientation(z, getOverrideOrientation());
    }

    public int getRequestedConfigurationOrientation(boolean z, int i) {
        RootDisplayArea rootDisplayArea = getRootDisplayArea();
        if (z && rootDisplayArea != null && rootDisplayArea.isOrientationDifferentFromDisplay()) {
            i = ActivityInfo.reverseOrientation(i);
        }
        if (i == 5) {
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent != null) {
                Configuration configuration = displayContent.getConfiguration();
                if (configuration.windowConfiguration.getDisplayRotation() == 0) {
                    return configuration.orientation;
                }
                Rect rect = displayContent.mDisplayPolicy.getDecorInsetsInfo(0, displayContent.mBaseDisplayWidth, displayContent.mBaseDisplayHeight).mConfigFrame;
                return rect.width() <= rect.height() ? 1 : 2;
            }
        } else {
            if (i == 14) {
                return getConfiguration().orientation;
            }
            if (ActivityInfo.isFixedOrientationLandscape(i)) {
                return 2;
            }
            if (ActivityInfo.isFixedOrientationPortrait(i)) {
                return 1;
            }
        }
        return 0;
    }

    public int getRequestedConfigurationOrientationInChildren() {
        int overrideOrientation = getOverrideOrientation();
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            int orientation = windowContainer.getOrientation(overrideOrientation == 3 ? 3 : -2);
            if (orientation == 3) {
                overrideOrientation = orientation;
            } else if (orientation != -2 && (windowContainer.providesOrientation() || orientation != -1)) {
                overrideOrientation = orientation;
                break;
            }
        }
        return getRequestedConfigurationOrientation(false, overrideOrientation);
    }

    public RootDisplayArea getRootDisplayArea() {
        WindowContainer parent = getParent();
        if (parent != null) {
            return parent.getRootDisplayArea();
        }
        return null;
    }

    public Task getRootTask(Predicate predicate) {
        return getRootTask(predicate, true);
    }

    public Task getRootTask(Predicate predicate, boolean z) {
        int size = this.mChildren.size();
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                Task rootTask = ((WindowContainer) this.mChildren.get(i)).getRootTask(predicate, z);
                if (rootTask != null) {
                    return rootTask;
                }
            }
            return null;
        }
        int i2 = 0;
        while (i2 < size) {
            Task rootTask2 = ((WindowContainer) this.mChildren.get(i2)).getRootTask(predicate, z);
            if (rootTask2 != null) {
                return rootTask2;
            }
            int size2 = this.mChildren.size();
            i2 = (i2 - (size - size2)) + 1;
            size = size2;
        }
        return null;
    }

    public SurfaceSession getSession() {
        if (getParent() != null) {
            return getParent().getSession();
        }
        return null;
    }

    public final SurfaceAnimationRunner getSurfaceAnimationRunner() {
        return this.mWmService.mSurfaceAnimationRunner;
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    public int getSurfaceHeight() {
        return this.mSurfaceControl.getHeight();
    }

    public int getSurfaceWidth() {
        return this.mSurfaceControl.getWidth();
    }

    public BLASTSyncEngine.SyncGroup getSyncGroup() {
        BLASTSyncEngine.SyncGroup syncGroup = this.mSyncGroup;
        if (syncGroup != null) {
            return syncGroup;
        }
        for (WindowContainer windowContainer = this.mParent; windowContainer != null; windowContainer = windowContainer.mParent) {
            BLASTSyncEngine.SyncGroup syncGroup2 = windowContainer.mSyncGroup;
            if (syncGroup2 != null) {
                return syncGroup2;
            }
        }
        return null;
    }

    public SurfaceControl.Transaction getSyncTransaction() {
        if (this.mSyncTransactionCommitCallbackDepth <= 0 && this.mSyncState == 0) {
            return getPendingTransaction();
        }
        return this.mSyncTransaction;
    }

    public Task getTask(Predicate predicate) {
        return getTask(predicate, true);
    }

    public final Task getTask(Predicate predicate, WindowContainer windowContainer, boolean z, boolean z2) {
        return getTask(predicate, windowContainer, z, z2, new boolean[1]);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0042 A[LOOP:0: B:4:0x0014->B:12:0x0042, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0041 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007a A[LOOP:1: B:23:0x004c->B:31:0x007a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0079 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.Task getTask(java.util.function.Predicate r16, com.android.server.wm.WindowContainer r17, boolean r18, boolean r19, boolean[] r20) {
        /*
            r15 = this;
            r0 = r15
            r7 = r16
            r8 = r17
            r9 = r19
            r10 = 0
            r11 = 0
            r12 = 1
            if (r9 == 0) goto L45
            com.android.server.wm.WindowList r1 = r0.mChildren
            int r1 = r1.size()
            int r1 = r1 - r12
            r13 = r1
        L14:
            if (r13 < 0) goto L7d
            com.android.server.wm.WindowList r1 = r0.mChildren
            java.lang.Object r1 = r1.get(r13)
            com.android.server.wm.WindowContainer r1 = (com.android.server.wm.WindowContainer) r1
            if (r1 == r8) goto L22
            if (r8 != 0) goto L28
        L22:
            r20[r11] = r12
            if (r18 != 0) goto L28
            r1 = r10
            goto L3f
        L28:
            boolean r2 = r20[r11]
            if (r2 == 0) goto L31
            com.android.server.wm.Task r1 = r1.getTask(r7, r9)
            goto L3f
        L31:
            r2 = r16
            r3 = r17
            r4 = r18
            r5 = r19
            r6 = r20
            com.android.server.wm.Task r1 = r1.getTask(r2, r3, r4, r5, r6)
        L3f:
            if (r1 == 0) goto L42
            return r1
        L42:
            int r13 = r13 + (-1)
            goto L14
        L45:
            com.android.server.wm.WindowList r1 = r0.mChildren
            int r13 = r1.size()
            r14 = r11
        L4c:
            if (r14 >= r13) goto L7d
            com.android.server.wm.WindowList r1 = r0.mChildren
            java.lang.Object r1 = r1.get(r14)
            com.android.server.wm.WindowContainer r1 = (com.android.server.wm.WindowContainer) r1
            if (r1 == r8) goto L5a
            if (r8 != 0) goto L60
        L5a:
            r20[r11] = r12
            if (r18 != 0) goto L60
            r1 = r10
            goto L77
        L60:
            boolean r2 = r20[r11]
            if (r2 == 0) goto L69
            com.android.server.wm.Task r1 = r1.getTask(r7, r9)
            goto L77
        L69:
            r2 = r16
            r3 = r17
            r4 = r18
            r5 = r19
            r6 = r20
            com.android.server.wm.Task r1 = r1.getTask(r2, r3, r4, r5, r6)
        L77:
            if (r1 == 0) goto L7a
            return r1
        L7a:
            int r14 = r14 + 1
            goto L4c
        L7d:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.WindowContainer.getTask(java.util.function.Predicate, com.android.server.wm.WindowContainer, boolean, boolean, boolean[]):com.android.server.wm.Task");
    }

    public Task getTask(Predicate predicate, boolean z) {
        if (z) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                Task task = ((WindowContainer) this.mChildren.get(size)).getTask(predicate, z);
                if (task != null) {
                    return task;
                }
            }
            return null;
        }
        int size2 = this.mChildren.size();
        for (int i = 0; i < size2; i++) {
            Task task2 = ((WindowContainer) this.mChildren.get(i)).getTask(predicate, z);
            if (task2 != null) {
                return task2;
            }
        }
        return null;
    }

    public Task getTaskBelow(Task task) {
        return getTask(alwaysTruePredicate(), task, false, true);
    }

    public TaskDisplayArea getTaskDisplayArea() {
        WindowContainer parent = getParent();
        if (parent != null) {
            return parent.getTaskDisplayArea();
        }
        return null;
    }

    public TaskFragment getTaskFragment(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            TaskFragment taskFragment = ((WindowContainer) this.mChildren.get(size)).getTaskFragment(predicate);
            if (taskFragment != null) {
                return taskFragment;
            }
        }
        return null;
    }

    public ActivityRecord getTopActivity(boolean z, boolean z2) {
        return z ? z2 ? getActivity(alwaysTruePredicate()) : getActivity(new WindowContainer$$ExternalSyntheticLambda0(0)) : z2 ? getActivity(new WindowContainer$$ExternalSyntheticLambda0(1)) : getActivity(new WindowContainer$$ExternalSyntheticLambda0(2));
    }

    public WindowContainer getTopChild() {
        return (WindowContainer) this.mChildren.peekLast();
    }

    public ActivityRecord getTopMostActivity() {
        return getActivity(alwaysTruePredicate(), true);
    }

    public Task getTopMostTask() {
        return getTask(alwaysTruePredicate(), true);
    }

    public int getTreeWeight() {
        return this.mTreeWeight;
    }

    public WindowState getWindow(Predicate predicate) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowState window = ((WindowContainer) this.mChildren.get(size)).getWindow(predicate);
            if (window != null) {
                return window;
            }
        }
        return null;
    }

    public int getWindowType() {
        return -1;
    }

    public boolean handleCompleteDeferredRemoval() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z |= ((WindowContainer) this.mChildren.get(size)).handleCompleteDeferredRemoval();
            if (!hasChild()) {
                return false;
            }
        }
        return z;
    }

    public boolean handlesOrientationChangeFromDescendant(int i) {
        WindowContainer parent = getParent();
        return parent != null && parent.handlesOrientationChangeFromDescendant(i);
    }

    public boolean hasActivity() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).hasActivity()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasChild(WindowContainer windowContainer) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer2 = (WindowContainer) this.mChildren.get(size);
            if (windowContainer2 == windowContainer || windowContainer2.hasChild(windowContainer)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCommittedReparentToAnimationLeash() {
        return this.mCommittedReparentToAnimationLeash;
    }

    public boolean hasContentToDisplay() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).hasContentToDisplay()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasInsetsSourceProvider() {
        return this.mInsetsSourceProviders != null;
    }

    public boolean hasRelativeLayer() {
        WindowContainer windowContainer;
        return this.mLastRelativeToLayer != null || ((windowContainer = this.mParent) != null && windowContainer.hasRelativeLayer());
    }

    public boolean hasWallpaper() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).hasWallpaper()) {
                return true;
            }
        }
        return false;
    }

    public boolean inTransition() {
        return this.mTransitionController.inTransition(this);
    }

    public boolean inTransitionSelfOrParent() {
        return !this.mTransitionController.isShellTransitionsEnabled() ? isAnimating(3, 9) : inTransition();
    }

    public void initializeChangeTransition(Rect rect) {
        initializeChangeTransition(rect, null);
    }

    public void initializeChangeTransition(Rect rect, SurfaceControl surfaceControl) {
        if (this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            this.mDisplayContent.mTransitionController.collectVisibleChange(this);
            return;
        }
        this.mDisplayContent.prepareAppTransition(6, 0);
        this.mDisplayContent.mChangingContainers.add(this);
        Rect bounds = getParent().getBounds();
        this.mTmpPoint.set(rect.left - bounds.left, rect.top - bounds.top);
        SurfaceFreezer surfaceFreezer = this.mSurfaceFreezer;
        SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        Point point = this.mTmpPoint;
        SurfaceFreezer.Snapshot snapshot = surfaceFreezer.mSnapshot;
        if (snapshot != null) {
            SurfaceControl surfaceControl2 = snapshot.mSurfaceControl;
            if (surfaceControl2 != null) {
                syncTransaction.remove(surfaceControl2);
                snapshot.mSurfaceControl = null;
            }
            surfaceFreezer.mSnapshot = null;
        }
        SurfaceControl surfaceControl3 = surfaceFreezer.mLeash;
        if (surfaceControl3 != null) {
            syncTransaction.remove(surfaceControl3);
            surfaceFreezer.mLeash = null;
        }
        surfaceFreezer.mFreezeBounds.set(rect);
        WindowContainer windowContainer = surfaceFreezer.mAnimatable;
        SurfaceControl surfaceControl4 = windowContainer.getSurfaceControl();
        int width = rect.width();
        int height = rect.height();
        int i = point.x;
        int i2 = point.y;
        Supplier supplier = surfaceFreezer.mWmService.mTransactionFactory;
        SurfaceControl createAnimationLeash = SurfaceAnimator.createAnimationLeash(windowContainer, surfaceControl4, syncTransaction, 2, width, height, i, i2, false);
        surfaceFreezer.mLeash = createAnimationLeash;
        windowContainer.onAnimationLeashCreated(syncTransaction, createAnimationLeash);
        if (surfaceControl == null) {
            surfaceControl = windowContainer.getFreezeSnapshotTarget();
        }
        if (surfaceControl != null) {
            ScreenCapture.ScreenshotHardwareBuffer createSnapshotBufferInner = surfaceFreezer.createSnapshotBufferInner(surfaceControl, rect);
            HardwareBuffer hardwareBuffer = createSnapshotBufferInner != null ? createSnapshotBufferInner.getHardwareBuffer() : null;
            if (hardwareBuffer == null || hardwareBuffer.getWidth() <= 1 || hardwareBuffer.getHeight() <= 1) {
                Slog.w("SurfaceFreezer", "Failed to capture screenshot for " + windowContainer);
                surfaceFreezer.unfreeze(syncTransaction);
                return;
            }
            SurfaceControl surfaceControl5 = surfaceFreezer.mLeash;
            SurfaceFreezer.Snapshot snapshot2 = new SurfaceFreezer.Snapshot();
            GraphicBuffer createFromHardwareBufferInner = surfaceFreezer.createFromHardwareBufferInner(createSnapshotBufferInner);
            SurfaceControl build = windowContainer.makeAnimationLeash().setName("snapshot anim: " + windowContainer.toString()).setFormat(-3).setParent(surfaceControl5).setSecure(createSnapshotBufferInner.containsSecureLayers()).setCallsite("SurfaceFreezer.Snapshot").setBLASTLayer().build();
            snapshot2.mSurfaceControl = build;
            if (ProtoLogImpl_54989576.Cache.WM_SHOW_TRANSACTIONS_enabled[2]) {
                ProtoLogImpl_54989576.i(ProtoLogGroup.WM_SHOW_TRANSACTIONS, -2595923278763115975L, 0, null, String.valueOf(build));
            }
            syncTransaction.setBuffer(snapshot2.mSurfaceControl, createFromHardwareBufferInner);
            syncTransaction.setColorSpace(snapshot2.mSurfaceControl, createSnapshotBufferInner.getColorSpace());
            syncTransaction.show(snapshot2.mSurfaceControl);
            syncTransaction.setLayer(snapshot2.mSurfaceControl, Integer.MAX_VALUE);
            surfaceFreezer.mSnapshot = snapshot2;
        }
    }

    public final boolean isAnimating() {
        return isAnimating(0);
    }

    @Deprecated
    public final boolean isAnimating(int i) {
        return isAnimating(i, -1);
    }

    public final boolean isAnimating(int i, int i2) {
        return getAnimatingContainer(i, i2) != null;
    }

    public boolean isAppTransitioning() {
        return getActivity(new WindowContainer$$ExternalSyntheticLambda0(3)) != null;
    }

    public boolean isAttached() {
        WindowContainer parent = getParent();
        return parent != null && parent.isAttached();
    }

    public boolean isChangingAppTransition() {
        DisplayContent displayContent = this.mDisplayContent;
        return displayContent != null && displayContent.mChangingContainers.contains(this);
    }

    public boolean isClosingWhenResizing() {
        DisplayContent displayContent = this.mDisplayContent;
        return displayContent != null && displayContent.mClosingChangingContainers.containsKey(this);
    }

    public boolean isConfigurationNeededInUdcCutout() {
        DisplayContent displayContent;
        UdcCutoutPolicy udcCutoutPolicy;
        return (!this.mUseConfigurationInUdcCutout || (displayContent = this.mDisplayContent) == null || (udcCutoutPolicy = displayContent.mUdcCutoutPolicy) == null || udcCutoutPolicy.mUdcCutout == null || inMultiWindowMode()) ? false : true;
    }

    public boolean isDescendantOf(WindowContainer windowContainer) {
        WindowContainer parent = getParent();
        if (parent == windowContainer) {
            return true;
        }
        return parent != null && parent.isDescendantOf(windowContainer);
    }

    public boolean isDifferentSyncGroup(BLASTSyncEngine.SyncGroup syncGroup) {
        BLASTSyncEngine.SyncGroup syncGroup2;
        if (syncGroup == null || (syncGroup2 = getSyncGroup()) == null || syncGroup == syncGroup2) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this);
        sb.append(" uses a different SyncGroup, current=");
        sb.append(syncGroup2.mSyncId);
        sb.append(" given=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, syncGroup.mSyncId, TAG);
        return true;
    }

    public boolean isEmbedded() {
        return false;
    }

    public boolean isExitAnimationRunningSelfOrChild() {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return isAnimating(5, 25);
        }
        if (this.mChildren.isEmpty() && inTransition()) {
            return true;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).isExitAnimationRunningSelfOrChild()) {
                return true;
            }
        }
        return false;
    }

    public boolean isFocusable() {
        WindowContainer parent = getParent();
        return (parent == null || parent.isFocusable()) && this.mIsFocusable;
    }

    public boolean isLayoutNeededInUdcCutout() {
        DisplayContent displayContent;
        UdcCutoutPolicy udcCutoutPolicy;
        return (!this.mUseLayoutInUdcCutout || (displayContent = this.mDisplayContent) == null || (udcCutoutPolicy = displayContent.mUdcCutoutPolicy) == null || udcCutoutPolicy.mUdcCutout == null || inMultiWindowMode()) ? false : true;
    }

    public boolean isOnTop() {
        WindowContainer parent = getParent();
        return parent != null && parent.getTopChild() == this && parent.isOnTop();
    }

    public boolean isOrganized() {
        return false;
    }

    public boolean isSelfAnimating(int i, int i2) {
        if (!this.mSurfaceAnimator.isAnimating() || (i2 & this.mSurfaceAnimator.mAnimationType) <= 0) {
            return (i & 1) != 0 && isWaitingForTransitionStart();
        }
        return true;
    }

    public boolean isSplitEmbedded() {
        return false;
    }

    public boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
        if (!isVisibleRequested()) {
            return true;
        }
        if (this.mSyncState == 0 && getSyncGroup() != null) {
            Slog.i(TAG, "prepareSync in isSyncFinished: " + this);
            prepareSync();
        }
        if (this.mSyncState == 1) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            boolean z = (syncGroup.mIgnoreIndirectMembers && windowContainer.asWindowState() == null && windowContainer.mSyncGroup != syncGroup) || windowContainer.isSyncFinished(syncGroup);
            if (z && windowContainer.isVisibleRequested() && windowContainer.fillsParent()) {
                return true;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public boolean isVisible() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).isVisible()) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisibleRequested() {
        return this.mVisibleRequested;
    }

    public boolean isWaitingForTransitionStart() {
        return false;
    }

    public SurfaceControl.Builder makeAnimationLeash() {
        return makeSurface().setContainerLayer();
    }

    public SurfaceControl.Builder makeChildSurface(WindowContainer windowContainer) {
        return getParent().makeChildSurface(windowContainer).setParent(this.mSurfaceControl);
    }

    public SurfaceControl.Builder makeSurface() {
        return getParent().makeChildSurface(this);
    }

    public void migrateToNewSurfaceControl(SurfaceControl.Transaction transaction) {
        transaction.remove(this.mSurfaceControl);
        this.mLastSurfacePosition.set(0, 0);
        this.mLastDeltaRotation = 0;
        setInitialSurfaceControlProperties(this.mWmService.makeSurfaceBuilder(null).setContainerLayer().setName(getName()));
        SurfaceControl surfaceControl = this.mSurfaceControl;
        WindowContainer windowContainer = this.mParent;
        transaction.reparent(surfaceControl, windowContainer != null ? windowContainer.mSurfaceControl : null);
        SurfaceControl surfaceControl2 = this.mLastRelativeToLayer;
        if (surfaceControl2 != null) {
            transaction.setRelativeLayer(this.mSurfaceControl, surfaceControl2, this.mLastLayer);
        } else {
            transaction.setLayer(this.mSurfaceControl, this.mLastLayer);
        }
        for (int i = 0; i < this.mChildren.size(); i++) {
            SurfaceControl surfaceControl3 = ((WindowContainer) this.mChildren.get(i)).getSurfaceControl();
            if (surfaceControl3 != null) {
                transaction.reparent(surfaceControl3, this.mSurfaceControl);
            }
        }
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost != null) {
            SurfaceControl surfaceControl4 = this.mSurfaceControl;
            SurfaceControl surfaceControl5 = trustedOverlayHost.mSurfaceControl;
            if (surfaceControl5 != null) {
                transaction.reparent(surfaceControl5, surfaceControl4);
                if (surfaceControl4 != null) {
                    transaction.show(trustedOverlayHost.mSurfaceControl);
                } else {
                    transaction.hide(trustedOverlayHost.mSurfaceControl);
                }
            }
        }
        scheduleAnimation();
    }

    public boolean needRemoteWallpaperAnim() {
        return false;
    }

    public boolean needsZBoost() {
        if (this.mNeedsZBoost) {
            return true;
        }
        for (int i = 0; i < this.mChildren.size(); i++) {
            if (((WindowContainer) this.mChildren.get(i)).needsZBoost()) {
                return true;
            }
        }
        return false;
    }

    public boolean okToAnimate() {
        return okToAnimate(false, false);
    }

    public boolean okToAnimate(boolean z, boolean z2) {
        DisplayContent displayContent = getDisplayContent();
        return displayContent != null && displayContent.okToAnimate(z, z2);
    }

    public boolean okToDisplay() {
        DisplayContent displayContent = getDisplayContent();
        return displayContent != null && displayContent.okToDisplay(false, false);
    }

    public void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
        doAnimationFinished(i, animationAdapter);
        this.mWmService.onAnimationFinished();
        this.mNeedsZBoost = false;
    }

    public void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        DimAnimator dimAnimator;
        SurfaceControl surfaceControl2;
        this.mLastLayer = -1;
        this.mAnimationLeash = surfaceControl;
        reassignLayer(transaction);
        resetSurfacePositionForAnimationLeash(transaction);
        if (CoreRune.FW_CUSTOM_BASIC_ANIM_WITH_DIM && (surfaceControl2 = (dimAnimator = this.mDimAnimator).mDimAnimationLayer) != null && surfaceControl2.isValid()) {
            WindowAnimationSpec windowAnimationSpec = new WindowAnimationSpec(TransitionAnimation.loadDimAnimation(dimAnimator.mContext, dimAnimator.mTransitType), new Point(0, 0), false, FullScreenMagnificationGestureHandler.MAX_SCALE);
            SurfaceControl surfaceControl3 = dimAnimator.mDimAnimationLayer;
            WindowContainer windowContainer = dimAnimator.mContainer;
            transaction.setRelativeLayer(surfaceControl3, windowContainer.getSurfaceControl(), -1);
            transaction.setAlpha(dimAnimator.mDimAnimationLayer, FullScreenMagnificationGestureHandler.MAX_SCALE);
            transaction.show(dimAnimator.mDimAnimationLayer);
            windowContainer.mWmService.mSurfaceAnimationRunner.startAnimation(windowAnimationSpec, dimAnimator.mDimAnimationLayer, windowContainer.getSyncTransaction(), null);
            windowContainer.scheduleAnimation();
        }
    }

    public void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
        this.mLastLayer = -1;
        SurfaceAnimationRunner surfaceAnimationRunner = this.mWmService.mSurfaceAnimationRunner;
        SurfaceControl surfaceControl = this.mAnimationLeash;
        synchronized (surfaceAnimationRunner.mEdgeExtensionLock) {
            try {
                if (surfaceAnimationRunner.mEdgeExtensions.containsKey(surfaceControl)) {
                    ArrayList arrayList = (ArrayList) surfaceAnimationRunner.mEdgeExtensions.get(surfaceControl);
                    for (int i = 0; i < arrayList.size(); i++) {
                        transaction.remove((SurfaceControl) arrayList.get(i));
                    }
                    surfaceAnimationRunner.mEdgeExtensions.remove(surfaceControl);
                }
            } finally {
            }
        }
        this.mAnimationLeash = null;
        this.mNeedsZBoost = false;
        reassignLayer(transaction);
        WindowState asWindowState = asWindowState();
        if (asWindowState != null && asWindowState.isPopOver() && asWindowState.inTransition() && asWindowState.isGoneForLayout()) {
            Point point = asWindowState.mSurfacePosition;
            if (point.x != 0 || point.y != 0) {
                asWindowState.mSurfacePlacementNeeded = true;
            }
        }
        updateSurfacePosition(transaction);
        if (CoreRune.FW_CUSTOM_BASIC_ANIM_WITH_DIM) {
            this.mDimAnimator.finishDimAnimation(0);
        }
    }

    public void onAppTransitionDone() {
        if (this.mSurfaceFreezer.hasLeash()) {
            this.mSurfaceFreezer.unfreeze(getSyncTransaction());
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).onAppTransitionDone();
        }
    }

    public void onChildPositionChanged(WindowContainer windowContainer) {
    }

    public final void onChildRemoved(WindowContainer windowContainer) {
        this.mTreeWeight -= windowContainer.mTreeWeight;
        for (WindowContainer parent = getParent(); parent != null; parent = parent.getParent()) {
            parent.mTreeWeight -= windowContainer.mTreeWeight;
        }
        onChildVisibleRequestedChanged(null);
        onChildPositionChanged(windowContainer);
    }

    public void onChildVisibilityRequested(boolean z) {
        DisplayContent displayContent;
        if (!z) {
            if (asTaskFragment() != null) {
                TaskFragment asTaskFragment = asTaskFragment();
                if (asTaskFragment.isOrganizedTaskFragment() && (displayContent = asTaskFragment.mDisplayContent) != null && displayContent.mChangingContainers.remove(asTaskFragment)) {
                    asTaskFragment.mDisplayContent.mClosingChangingContainers.put(asTaskFragment, new Rect(asTaskFragment.mSurfaceFreezer.mFreezeBounds));
                }
            }
            this.mSurfaceFreezer.unfreeze(getSyncTransaction());
        }
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.onChildVisibilityRequested(z);
        }
    }

    public boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
        boolean z = false;
        boolean z2 = windowContainer != null && windowContainer.isVisibleRequested();
        boolean z3 = this.mVisibleRequested;
        if (!z2 || z3) {
            if (z2 || !z3) {
                z = z3;
            } else {
                for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                    WindowContainer windowContainer2 = (WindowContainer) this.mChildren.get(size);
                    if (windowContainer2 == windowContainer || !windowContainer2.isVisibleRequested()) {
                    }
                }
            }
            return setVisibleRequested(z);
        }
        z = true;
        return setVisibleRequested(z);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateSurfacePositionNonOrganized();
        scheduleAnimation();
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost != null) {
            Configuration configuration2 = getConfiguration();
            for (int size = trustedOverlayHost.mOverlays.size() - 1; size >= 0; size--) {
                SurfaceControlViewHost.SurfacePackage surfacePackage = (SurfaceControlViewHost.SurfacePackage) trustedOverlayHost.mOverlays.get(size);
                try {
                    surfacePackage.getRemoteInterface().onConfigurationChanged(configuration2);
                } catch (Exception unused) {
                    trustedOverlayHost.removeOverlay(surfacePackage);
                }
            }
        }
    }

    public boolean onDescendantOrientationChanged(WindowContainer windowContainer) {
        WindowContainer parent = getParent();
        if (parent == null) {
            return false;
        }
        return parent.onDescendantOrientationChanged(windowContainer);
    }

    public void onDescendantOverrideConfigurationChanged() {
        WindowContainer windowContainer = this.mParent;
        if (windowContainer != null) {
            windowContainer.onDescendantOverrideConfigurationChanged();
        }
    }

    public void onDisplayChanged(DisplayContent displayContent) {
        DisplayContent displayContent2 = this.mDisplayContent;
        if (displayContent2 != null && displayContent2 != displayContent) {
            displayContent2.mClosingChangingContainers.remove(this);
            if (this.mDisplayContent.mChangingContainers.remove(this)) {
                this.mSurfaceFreezer.unfreeze(getSyncTransaction());
            }
        }
        this.mDisplayContent = displayContent;
        if (displayContent != null && displayContent != this) {
            displayContent.getPendingTransaction().merge(this.mPendingTransaction);
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).onDisplayChanged(displayContent);
        }
        for (int size2 = this.mListeners.size() - 1; size2 >= 0; size2--) {
            ((WindowContainerListener) this.mListeners.get(size2)).onDisplayChanged(displayContent);
        }
    }

    public void onMovedByResize() {
        InsetsSourceProvider insetsSourceProvider = this.mControllableInsetProvider;
        if (insetsSourceProvider != null) {
            insetsSourceProvider.mInsetsHintStale = true;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).onMovedByResize();
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void onParentChanged(ConfigurationContainer configurationContainer, ConfigurationContainer configurationContainer2) {
        onParentChanged(configurationContainer, configurationContainer2, null);
    }

    public void onParentChanged(ConfigurationContainer configurationContainer, ConfigurationContainer configurationContainer2, Runnable runnable) {
        super.onParentChanged(configurationContainer, configurationContainer2);
        if (this.mParent == null) {
            return;
        }
        if (this.mSurfaceControl == null) {
            createSurfaceControl(false);
        } else {
            reparentSurfaceControl(getSyncTransaction(), this.mParent.mSurfaceControl);
        }
        if (CoreRune.MW_SHELL_TRANSITION && runnable != null) {
            runnable.run();
        }
        this.mParent.assignChildLayers();
    }

    public void onParentResize() {
        if (hasOverrideBounds()) {
            return;
        }
        onResize();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void onRequestedOverrideConfigurationChanged(Configuration configuration) {
        int diffRequestedOverrideBounds = diffRequestedOverrideBounds(configuration.windowConfiguration.getBounds());
        super.onRequestedOverrideConfigurationChanged(configuration);
        WindowContainer windowContainer = this.mParent;
        if (windowContainer != null) {
            windowContainer.onDescendantOverrideConfigurationChanged();
        }
        if (diffRequestedOverrideBounds == 0) {
            return;
        }
        if ((diffRequestedOverrideBounds & 2) == 2) {
            onResize();
        } else {
            onMovedByResize();
        }
    }

    public void onResize() {
        InsetsSourceProvider insetsSourceProvider = this.mControllableInsetProvider;
        if (insetsSourceProvider != null) {
            insetsSourceProvider.mInsetsHintStale = true;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).onParentResize();
        }
    }

    public boolean onSyncFinishedDrawing() {
        if (this.mSyncState == 0) {
            return false;
        }
        this.mSyncState = 2;
        this.mSyncMethodOverride = -1;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_SYNC_ENGINE_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 5272307326252759722L, 0, null, String.valueOf(this));
        }
        return true;
    }

    public final void onSyncReparent(WindowContainer windowContainer, WindowContainer windowContainer2) {
        if (this.mSyncState != 0 && windowContainer != null && windowContainer2 != null && windowContainer.getDisplayContent() != null && windowContainer2.getDisplayContent() != null && windowContainer.getDisplayContent() != windowContainer2.getDisplayContent()) {
            this.mTransitionController.setReady(windowContainer.getDisplayContent(), true);
        }
        if (windowContainer2 == null || windowContainer2.mSyncState == 0) {
            if (this.mSyncState == 0) {
                return;
            }
            if (windowContainer2 == null) {
                BLASTSyncEngine.SyncGroup syncGroup = getSyncGroup();
                if (windowContainer.mSyncState != 0) {
                    finishSync(windowContainer.mSyncTransaction, syncGroup, true);
                    return;
                }
                if (syncGroup != null) {
                    if (syncGroup.mOrphanTransaction == null) {
                        syncGroup.mOrphanTransaction = (SurfaceControl.Transaction) BLASTSyncEngine.this.mWm.mTransactionFactory.get();
                    }
                    finishSync(syncGroup.mOrphanTransaction, syncGroup, true);
                    return;
                } else {
                    Slog.wtf(TAG, this + " is in sync mode without a sync group");
                    finishSync(getPendingTransaction(), null, true);
                    return;
                }
            }
            if (this.mSyncGroup == null) {
                finishSync(getPendingTransaction(), getSyncGroup(), true);
                return;
            }
        }
        if (windowContainer == null || windowContainer2 == null || shouldUpdateSyncOnReparent()) {
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                this.mSyncState = 0;
                this.mSyncMethodOverride = -1;
            }
            prepareSync();
        }
    }

    public void onSyncTransactionCommitted(SurfaceControl.Transaction transaction) {
        int i = this.mSyncTransactionCommitCallbackDepth - 1;
        this.mSyncTransactionCommitCallbackDepth = i;
        if (i <= 0 && this.mSyncState == 0) {
            transaction.merge(this.mSyncTransaction);
        }
    }

    public void onUnfrozen() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            displayContent.mChangingContainers.remove(this);
        }
    }

    public void positionChildAt(int i, WindowContainer windowContainer, boolean z) {
        if (windowContainer.getParent() != this) {
            throw new IllegalArgumentException("positionChildAt: container=" + windowContainer.getName() + " is not a child of container=" + getName() + " current parent=" + windowContainer.getParent());
        }
        if (i >= this.mChildren.size() - 1) {
            i = Integer.MAX_VALUE;
        } else if (i <= 0) {
            i = POSITION_BOTTOM;
        }
        if (i == POSITION_BOTTOM) {
            WindowList windowList = this.mChildren;
            if ((windowList.size() > 0 ? windowList.get(0) : null) != windowContainer) {
                this.mChildren.remove(windowContainer);
                this.mChildren.add(0, windowContainer);
                onChildPositionChanged(windowContainer);
            }
            if (!z || getParent() == null) {
                return;
            }
            getParent().positionChildAt(POSITION_BOTTOM, this, true);
            return;
        }
        if (i != Integer.MAX_VALUE) {
            if (this.mChildren.indexOf(windowContainer) != i) {
                this.mChildren.remove(windowContainer);
                this.mChildren.add(i, windowContainer);
                onChildPositionChanged(windowContainer);
                return;
            }
            return;
        }
        if (this.mChildren.peekLast() != windowContainer) {
            this.mChildren.remove(windowContainer);
            this.mChildren.add(windowContainer);
            onChildPositionChanged(windowContainer);
        }
        if (!z || getParent() == null) {
            return;
        }
        getParent().positionChildAt(Integer.MAX_VALUE, this, true);
    }

    public void prepareSurfaces() {
        this.mCommittedReparentToAnimationLeash = this.mSurfaceAnimator.hasLeash();
        for (int i = 0; i < this.mChildren.size(); i++) {
            ((WindowContainer) this.mChildren.get(i)).prepareSurfaces();
        }
    }

    public boolean prepareSync() {
        if (this.mSyncState != 0) {
            return false;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            getChildAt(childCount).prepareSync();
        }
        this.mSyncState = 2;
        return true;
    }

    public boolean providesOrientation() {
        return fillsParent();
    }

    public void reassignLayer(SurfaceControl.Transaction transaction) {
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.assignChildLayers(transaction);
        }
    }

    public Object reduceOnAllTaskDisplayAreas(BiFunction biFunction, Object obj) {
        return reduceOnAllTaskDisplayAreas(biFunction, obj, true);
    }

    public Object reduceOnAllTaskDisplayAreas(BiFunction biFunction, Object obj, boolean z) {
        int size = this.mChildren.size();
        for (int i = size - 1; i >= 0 && i < size; i--) {
            obj = ((WindowContainer) this.mChildren.get(i)).reduceOnAllTaskDisplayAreas(biFunction, obj, true);
        }
        return obj;
    }

    public void registerWindowContainerListener(WindowContainerListener windowContainerListener) {
        registerWindowContainerListener(windowContainerListener, true);
    }

    public void registerWindowContainerListener(WindowContainerListener windowContainerListener, boolean z) {
        if (this.mListeners.contains(windowContainerListener)) {
            return;
        }
        this.mListeners.add(windowContainerListener);
        registerConfigurationChangeListener(windowContainerListener, z);
        if (z) {
            windowContainerListener.onDisplayChanged(getDisplayContent());
        }
    }

    public void removeChild(WindowContainer windowContainer) {
        if (this.mChildren.remove(windowContainer)) {
            onChildRemoved(windowContainer);
            if (windowContainer.mReparenting) {
                return;
            }
            windowContainer.setParent(null);
            return;
        }
        throw new IllegalArgumentException("removeChild: container=" + windowContainer.getName() + " is not a child of container=" + getName());
    }

    public void removeIfPossible() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).removeIfPossible();
        }
    }

    public void removeImmediately() {
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            displayContent.mClosingChangingContainers.remove(this);
            this.mSurfaceFreezer.unfreeze(getSyncTransaction());
        }
        while (!this.mChildren.isEmpty()) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.peekLast();
            windowContainer.removeImmediately();
            if (this.mChildren.remove(windowContainer)) {
                onChildRemoved(windowContainer);
            }
        }
        if (CoreRune.FW_CUSTOM_BASIC_ANIM_WITH_DIM) {
            this.mDimAnimator.finishDimAnimation(0);
        }
        if (this.mSurfaceControl != null) {
            getSyncTransaction().remove(this.mSurfaceControl);
            setSurfaceControl(null);
            this.mLastSurfacePosition.set(0, 0);
            this.mLastDeltaRotation = 0;
            scheduleAnimation();
        }
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost != null) {
            trustedOverlayHost.release();
            this.mOverlayHost = null;
        }
        WindowContainer windowContainer2 = this.mParent;
        if (windowContainer2 != null) {
            windowContainer2.removeChild(this);
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((WindowContainerListener) this.mListeners.get(size)).onRemoved();
        }
    }

    public void removeLocalInsetsFrameProvider(InsetsFrameProvider insetsFrameProvider, IBinder iBinder) {
        DeathRecipient deathRecipient;
        DisplayContent displayContent;
        if (insetsFrameProvider == null || iBinder == null) {
            throw new IllegalArgumentException("Insets provider or owner not specified.");
        }
        int id = insetsFrameProvider.getId();
        SparseArray sparseArray = this.mLocalInsetsSources;
        if (((sparseArray == null || sparseArray.removeReturnOld(id) == null) ? false : true) && (displayContent = this.mDisplayContent) != null) {
            displayContent.mInsetsStateController.updateAboveInsetsState(true);
        }
        ArrayMap arrayMap = this.mInsetsOwnerDeathRecipientMap;
        if (arrayMap == null || (deathRecipient = (DeathRecipient) arrayMap.get(iBinder)) == null) {
            return;
        }
        deathRecipient.mSourceIds.remove(Integer.valueOf(id));
        if (!deathRecipient.mSourceIds.isEmpty()) {
            return;
        }
        iBinder.unlinkToDeath(deathRecipient, 0);
        this.mInsetsOwnerDeathRecipientMap.remove(iBinder);
    }

    public void removeTrustedOverlay(SurfaceControlViewHost.SurfacePackage surfacePackage) {
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost == null || trustedOverlayHost.removeOverlay(surfacePackage)) {
            return;
        }
        this.mOverlayHost.release();
        this.mOverlayHost = null;
    }

    public void reparent(WindowContainer windowContainer, int i) {
        if (windowContainer == null) {
            throw new IllegalArgumentException("reparent: can't reparent to null " + this);
        }
        if (windowContainer == this) {
            throw new IllegalArgumentException("Can not reparent to itself " + this);
        }
        WindowContainer windowContainer2 = this.mParent;
        if (windowContainer2 == windowContainer) {
            throw new IllegalArgumentException("WC=" + this + " already child of " + this.mParent);
        }
        TransitionController transitionController = this.mTransitionController;
        if (transitionController.isCollecting()) {
            Transition transition = transitionController.mCollectingTransition;
            if (transition.mChanges.containsKey(this)) {
                Transition.ChangeInfo changeInfo = (Transition.ChangeInfo) transition.mChanges.get(this);
                WindowContainer windowContainer3 = changeInfo.mStartParent;
                WindowContainer windowContainer4 = (windowContainer3 == null || windowContainer3.isAttached()) ? changeInfo.mStartParent : changeInfo.mCommonAncestor;
                if (windowContainer4 == null || !windowContainer4.isAttached()) {
                    Slog.w("Transition", "Trying to collect reparenting of a window after the previous parent has been detached: " + this);
                } else if (windowContainer4 == windowContainer) {
                    Slog.w("Transition", "Trying to collect reparenting of a window that has not been reparented: " + this);
                } else if (windowContainer.isAttached()) {
                    WindowContainer windowContainer5 = windowContainer;
                    while (windowContainer4 != windowContainer5 && !windowContainer4.isDescendantOf(windowContainer5)) {
                        windowContainer5 = windowContainer5.getParent();
                    }
                    changeInfo.mCommonAncestor = windowContainer5;
                } else {
                    Slog.w("Transition", "Trying to collect reparenting of a window that is not attached after reparenting: " + this);
                }
            }
        }
        DisplayContent displayContent = windowContainer2.getDisplayContent();
        DisplayContent displayContent2 = windowContainer.getDisplayContent();
        this.mReparenting = true;
        windowContainer2.removeChild(this);
        windowContainer.addChild(this, i);
        this.mReparenting = false;
        displayContent2.setLayoutNeeded();
        if (displayContent != displayContent2) {
            onDisplayChanged(displayContent2);
            displayContent.setLayoutNeeded();
        }
        onParentChanged(windowContainer, windowContainer2);
        onSyncReparent(windowContainer2, windowContainer);
    }

    public void reparentSurfaceControl(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (this.mSurfaceFreezer.hasLeash() || this.mSurfaceAnimator.hasLeash()) {
            return;
        }
        transaction.reparent(getSurfaceControl(), surfaceControl);
    }

    public void resetDragResizingChangeReported() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).resetDragResizingChangeReported();
        }
    }

    public void resetSurfacePositionForAnimationLeash(SurfaceControl.Transaction transaction) {
        transaction.setPosition(this.mSurfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE);
        SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        if (transaction != syncTransaction) {
            syncTransaction.setPosition(this.mSurfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE);
        }
        this.mLastSurfacePosition.set(0, 0);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void resolveOverrideConfiguration(Configuration configuration) {
        super.resolveOverrideConfiguration(configuration);
        if (!isConfigurationNeededInUdcCutout() || this.mDisplayContent.mUdcCutoutPolicy.mUdcConfiguration == null) {
            return;
        }
        getResolvedOverrideConfiguration().updateFrom(this.mDisplayContent.mUdcCutoutPolicy.mUdcConfiguration);
    }

    public void scheduleAnimation() {
        this.mWmService.scheduleAnimationLocked();
    }

    public void sendAppVisibilityToClients() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).sendAppVisibilityToClients();
        }
    }

    public boolean setCanScreenshot(SurfaceControl.Transaction transaction, boolean z) {
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl == null) {
            return false;
        }
        transaction.setSecure(surfaceControl, !z);
        return true;
    }

    public void setControllableInsetProvider(InsetsSourceProvider insetsSourceProvider) {
        this.mControllableInsetProvider = insetsSourceProvider;
    }

    public boolean setFocusable(boolean z) {
        if (this.mIsFocusable == z) {
            return false;
        }
        this.mIsFocusable = z;
        return true;
    }

    public void setInitialSurfaceControlProperties(SurfaceControl.Builder builder) {
        setSurfaceControl(builder.setCallsite("WindowContainer.setInitialSurfaceControlProperties").build());
        if (showSurfaceOnCreation()) {
            getSyncTransaction().show(this.mSurfaceControl);
        }
        updateSurfacePositionNonOrganized();
        if (this.mLastMagnificationSpec != null) {
            applyMagnificationSpec(getSyncTransaction(), this.mLastMagnificationSpec);
        }
    }

    public void setLayer(SurfaceControl.Transaction transaction, int i) {
        if (this.mSurfaceFreezer.hasLeash()) {
            SurfaceControl surfaceControl = this.mSurfaceFreezer.mLeash;
            if (surfaceControl != null) {
                transaction.setLayer(surfaceControl, i);
                return;
            }
            return;
        }
        SurfaceAnimator surfaceAnimator = this.mSurfaceAnimator;
        SurfaceControl surfaceControl2 = surfaceAnimator.mLeash;
        if (surfaceControl2 == null) {
            surfaceControl2 = surfaceAnimator.mAnimatable.getSurfaceControl();
        }
        transaction.setLayer(surfaceControl2, i);
    }

    public void setOrientation(int i) {
        setOrientation(i, null);
    }

    public void setOrientation(int i, WindowContainer windowContainer) {
        if (getOverrideOrientation() == i) {
            return;
        }
        setOverrideOrientation(i);
        WindowContainer parent = getParent();
        if (parent != null) {
            if (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY) {
                MultiTaskingAppCompatOrientationPolicy multiTaskingAppCompatOrientationPolicy = this.mWmService.mAtmService.mMultiTaskingAppCompatController.mOrientationPolicy;
                ActivityRecord asActivityRecord = asActivityRecord();
                multiTaskingAppCompatOrientationPolicy.getClass();
                if (asActivityRecord != null && asActivityRecord.mDisplayContent != null && asActivityRecord.getConfiguration().orientation != asActivityRecord.getRequestedConfigurationOrientation() && multiTaskingAppCompatOrientationPolicy.shouldIgnoreOrientationRequest(-1, asActivityRecord)) {
                    int i2 = asActivityRecord.mDisplayContent.mDisplayRotation.mRotation;
                    asActivityRecord.onDescendantOrientationChanged(asActivityRecord);
                    if (i2 == asActivityRecord.mDisplayContent.mDisplayRotation.mRotation) {
                        multiTaskingAppCompatOrientationPolicy.requestActivityBoundsChangedTransitionIfNeeded(asActivityRecord, new MultiTaskingAppCompatOrientationPolicy$$ExternalSyntheticLambda0(0, asActivityRecord));
                        return;
                    }
                    return;
                }
            }
            if (getConfiguration().orientation != getRequestedConfigurationOrientation() && (inMultiWindowMode() || !handlesOrientationChangeFromDescendant(i))) {
                onConfigurationChanged(parent.getConfiguration());
            }
            onDescendantOrientationChanged(windowContainer);
        }
    }

    public void setOverrideOrientation(int i) {
        this.mOverrideOrientation = i;
    }

    public final void setParent(WindowContainer windowContainer) {
        DisplayContent displayContent;
        WindowContainer windowContainer2 = this.mParent;
        this.mParent = windowContainer;
        if (windowContainer != null) {
            windowContainer.mTreeWeight += this.mTreeWeight;
            for (WindowContainer parent = windowContainer.getParent(); parent != null; parent = parent.getParent()) {
                parent.mTreeWeight += this.mTreeWeight;
            }
            windowContainer.onChildVisibleRequestedChanged(this);
            windowContainer.onChildPositionChanged(this);
        } else if (this.mSurfaceAnimator.hasLeash()) {
            this.mSurfaceAnimator.cancelAnimation();
        }
        if (this.mReparenting) {
            return;
        }
        onSyncReparent(windowContainer2, this.mParent);
        WindowContainer windowContainer3 = this.mParent;
        if (windowContainer3 != null && (displayContent = windowContainer3.mDisplayContent) != null && this.mDisplayContent != displayContent) {
            onDisplayChanged(displayContent);
        }
        onParentChanged(this.mParent, windowContainer2);
    }

    public void setRelativeLayer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i) {
        if (this.mSurfaceFreezer.hasLeash()) {
            SurfaceControl surfaceControl2 = this.mSurfaceFreezer.mLeash;
            if (surfaceControl2 != null) {
                transaction.setRelativeLayer(surfaceControl2, surfaceControl, i);
                return;
            }
            return;
        }
        SurfaceAnimator surfaceAnimator = this.mSurfaceAnimator;
        SurfaceControl surfaceControl3 = surfaceAnimator.mLeash;
        if (surfaceControl3 == null) {
            surfaceControl3 = surfaceAnimator.mAnimatable.getSurfaceControl();
        }
        transaction.setRelativeLayer(surfaceControl3, surfaceControl, i);
    }

    public void setSurfaceControl(SurfaceControl surfaceControl) {
        this.mSurfaceControl = surfaceControl;
    }

    public void setSyncGroup(BLASTSyncEngine.SyncGroup syncGroup) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_SYNC_ENGINE_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -8311909671193661340L, 1, null, Long.valueOf(syncGroup.mSyncId), String.valueOf(this));
        }
        BLASTSyncEngine.SyncGroup syncGroup2 = this.mSyncGroup;
        if (syncGroup2 == null || syncGroup2 == syncGroup) {
            this.mSyncGroup = syncGroup;
            return;
        }
        throw new IllegalStateException("Can't sync on 2 groups simultaneously currentSyncId=" + this.mSyncGroup.mSyncId + " newSyncId=" + syncGroup.mSyncId + " wc=" + this);
    }

    public boolean setVisibleRequested(boolean z) {
        if (this.mVisibleRequested == z) {
            return false;
        }
        this.mVisibleRequested = z;
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.onChildVisibleRequestedChanged(this);
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((WindowContainerListener) this.mListeners.get(size)).onVisibleRequestedChanged(this.mVisibleRequested);
        }
        return true;
    }

    public boolean shouldMagnify() {
        if (this.mSurfaceControl == null) {
            return false;
        }
        for (int i = 0; i < this.mChildren.size(); i++) {
            if (!((WindowContainer) this.mChildren.get(i)).shouldMagnify()) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldUpdateSyncOnReparent() {
        return true;
    }

    public boolean showSurfaceOnCreation() {
        return true;
    }

    public boolean showToCurrentUser() {
        return true;
    }

    public boolean showWallpaper() {
        if (isVisibleRequested() && !inMultiWindowMode()) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                if (((WindowContainer) this.mChildren.get(size)).showWallpaper()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i) {
        startAnimation(transaction, animationAdapter, z, i, null);
    }

    public void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i, SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        startAnimation(transaction, animationAdapter, z, i, onAnimationFinishedCallback, null, null);
    }

    public void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i, SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback, Runnable runnable, AnimationAdapter animationAdapter2) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ANIM, 6949303417875346627L, 4, null, String.valueOf(this), Long.valueOf(i), String.valueOf(animationAdapter));
        }
        this.mSurfaceAnimator.startAnimation(transaction, animationAdapter, z, i, onAnimationFinishedCallback, runnable, animationAdapter2, this.mSurfaceFreezer);
    }

    public void startDelayingAnimationStart() {
        SurfaceAnimator surfaceAnimator = this.mSurfaceAnimator;
        if (surfaceAnimator.isAnimating()) {
            return;
        }
        surfaceAnimator.mAnimationStartDelayed = true;
    }

    public void switchUser(int i) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).switchUser(i);
        }
    }

    public boolean syncNextBuffer() {
        return this.mSyncState != 0;
    }

    public void transferAnimation(WindowContainer windowContainer) {
        SurfaceAnimator surfaceAnimator = this.mSurfaceAnimator;
        SurfaceAnimator surfaceAnimator2 = windowContainer.mSurfaceAnimator;
        surfaceAnimator.getClass();
        if (surfaceAnimator2.mLeash == null) {
            return;
        }
        SurfaceControl surfaceControl = surfaceAnimator.mAnimatable.getSurfaceControl();
        SurfaceControl animationLeashParent = surfaceAnimator.mAnimatable.getAnimationLeashParent();
        if (surfaceControl == null || animationLeashParent == null) {
            Slog.w(TAG, "Unable to transfer animation, surface or parent is null");
            surfaceAnimator.cancelAnimation();
            return;
        }
        if (surfaceAnimator2.mAnimationFinished) {
            Slog.w(TAG, "Unable to transfer animation, because " + surfaceAnimator2 + " animation is finished");
            return;
        }
        Slog.i(TAG, "transferAnimation, surface=" + surfaceControl + ", parent=" + animationLeashParent + "");
        surfaceAnimator.endDelayingAnimationStart();
        SurfaceControl.Transaction syncTransaction = surfaceAnimator.mAnimatable.getSyncTransaction();
        surfaceAnimator.cancelAnimation(syncTransaction, true, true);
        surfaceAnimator.mLeash = surfaceAnimator2.mLeash;
        surfaceAnimator.mAnimation = surfaceAnimator2.mAnimation;
        surfaceAnimator.mAnimationType = surfaceAnimator2.mAnimationType;
        surfaceAnimator.mSurfaceAnimationFinishedCallback = surfaceAnimator2.mSurfaceAnimationFinishedCallback;
        surfaceAnimator.mAnimationCancelledCallback = surfaceAnimator2.mAnimationCancelledCallback;
        surfaceAnimator2.cancelAnimation(syncTransaction, false, false);
        syncTransaction.reparent(surfaceControl, surfaceAnimator.mLeash);
        syncTransaction.reparent(surfaceAnimator.mLeash, animationLeashParent);
        surfaceAnimator.mAnimatable.onAnimationLeashCreated(syncTransaction, surfaceAnimator.mLeash);
        surfaceAnimator.mService.mAnimationTransferMap.put(surfaceAnimator.mAnimation, surfaceAnimator);
    }

    public void transformFrameToSurfacePosition(int i, int i2, Point point) {
        point.set(i, i2);
        WindowContainer parent = getParent();
        if (parent == null) {
            return;
        }
        Rect bounds = parent.getBounds();
        point.offset(-bounds.left, -bounds.top);
    }

    public void unregisterWindowContainerListener(WindowContainerListener windowContainerListener) {
        this.mListeners.remove(windowContainerListener);
        unregisterConfigurationChangeListener(windowContainerListener);
    }

    public void updateAboveInsetsState(InsetsState insetsState, SparseArray sparseArray, ArraySet arraySet) {
        SparseArray createMergedSparseArray = createMergedSparseArray(sparseArray, this.mLocalInsetsSources);
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).updateAboveInsetsState(insetsState, createMergedSparseArray, arraySet);
        }
    }

    public void updateOverlayInsetsState(WindowState windowState) {
        WindowContainer parent = getParent();
        if (parent != null) {
            parent.updateOverlayInsetsState(windowState);
        }
    }

    public void updateSurfacePosition(SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null || this.mSurfaceAnimator.hasLeash() || this.mSurfaceFreezer.hasLeash()) {
            return;
        }
        if (isClosingWhenResizing()) {
            getRelativePosition((Rect) this.mDisplayContent.mClosingChangingContainers.get(this), this.mTmpPos);
        } else {
            getRelativePosition(this.mTmpPos);
        }
        int relativeDisplayRotation = getRelativeDisplayRotation();
        if (this.mTmpPos.equals(this.mLastSurfacePosition) && relativeDisplayRotation == this.mLastDeltaRotation) {
            return;
        }
        SurfaceControl surfaceControl = this.mSurfaceControl;
        Point point = this.mTmpPos;
        transaction.setPosition(surfaceControl, point.x, point.y);
        Point point2 = this.mLastSurfacePosition;
        Point point3 = this.mTmpPos;
        point2.set(point3.x, point3.y);
        if (this.mTransitionController.isShellTransitionsEnabled() && !this.mTransitionController.useShellTransitionsRotation()) {
            if (relativeDisplayRotation != 0) {
                updateSurfaceRotation(transaction, relativeDisplayRotation, null);
                getPendingTransaction().setFixedTransformHint(this.mSurfaceControl, getWindowConfiguration().getDisplayRotation());
            } else if (relativeDisplayRotation != this.mLastDeltaRotation) {
                transaction.setMatrix(this.mSurfaceControl, 1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f);
                getPendingTransaction().unsetFixedTransformHint(this.mSurfaceControl);
            }
        }
        this.mLastDeltaRotation = relativeDisplayRotation;
    }

    public final void updateSurfacePositionNonOrganized() {
        if (isOrganized()) {
            return;
        }
        updateSurfacePosition(getSyncTransaction());
    }

    public void updateSurfaceRotation(SurfaceControl.Transaction transaction, int i, SurfaceControl surfaceControl) {
        RotationUtils.rotateSurface(transaction, this.mSurfaceControl, i);
        Point point = this.mTmpPos;
        Point point2 = this.mLastSurfacePosition;
        point.set(point2.x, point2.y);
        Rect bounds = getParent().getBounds();
        boolean z = i % 2 != 0;
        RotationUtils.rotatePoint(this.mTmpPos, i, z ? bounds.height() : bounds.width(), z ? bounds.width() : bounds.height());
        if (surfaceControl == null) {
            surfaceControl = this.mSurfaceControl;
        }
        Point point3 = this.mTmpPos;
        transaction.setPosition(surfaceControl, point3.x, point3.y);
    }

    public boolean updateUseForceLayoutInUdcCutoutIfNeeded() {
        return false;
    }

    public void waitForAllWindowsDrawn() {
        forAllWindows(new Consumer() { // from class: com.android.server.wm.WindowContainer$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WindowState windowState = (WindowState) obj;
                ArrayList arrayList = WindowContainer.this.mWaitingForDrawn;
                if (windowState.isVisible()) {
                    WallpaperWindowToken asWallpaperToken = windowState.mToken.asWallpaperToken();
                    if (asWallpaperToken != null) {
                        if (asWallpaperToken.hasVisibleNotDrawnWallpaper()) {
                            arrayList.add(windowState);
                            return;
                        }
                        return;
                    }
                    ActivityRecord activityRecord = windowState.mActivityRecord;
                    if (activityRecord == null) {
                        if (!((PhoneWindowManager) windowState.mPolicy).isKeyguardHostWindow(windowState.mAttrs) || windowState.mWmService.mAtmService.mKeyguardController.isKeyguardGoingAway(windowState.getDisplayId())) {
                            return;
                        }
                    } else {
                        if (!activityRecord.isVisibleRequested()) {
                            return;
                        }
                        ActivityRecord activityRecord2 = windowState.mActivityRecord;
                        if (activityRecord2.allDrawn) {
                            return;
                        }
                        if (windowState.mAttrs.type == 3) {
                            if (windowState.isDrawn()) {
                                return;
                            }
                        } else if (activityRecord2.mStartingWindow != null) {
                            return;
                        }
                    }
                    windowState.mWinAnimator.mDrawState = 1;
                    windowState.forceReportingResized();
                    arrayList.add(windowState);
                }
            }
        }, true);
    }

    public void waitForSyncTransactionCommit(ArraySet arraySet) {
        if (arraySet.contains(this)) {
            return;
        }
        this.mSyncTransactionCommitCallbackDepth++;
        arraySet.add(this);
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((WindowContainer) this.mChildren.get(size)).waitForSyncTransactionCommit(arraySet);
        }
    }

    public void writeIdentifierToProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, -10000);
        protoOutputStream.write(1138166333443L, "WindowContainer");
        protoOutputStream.end(start);
    }
}
