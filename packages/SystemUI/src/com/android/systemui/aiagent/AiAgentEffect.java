package com.android.systemui.aiagent;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.IForegroundServiceObserver;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.core.view.OneShotPreDrawListener;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.aiagent.AiAgentEffect;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.util.AnimHelper;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.sesl.transparentvideo.TransparentVideoView;
import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$MediaError;
import com.sec.ims.volte2.data.VolteConstants;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes.dex */
public final class AiAgentEffect {
    public static final AnimHelper.AnimProperty HIDE_ANIM_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty HIDE_ANIM_TRANSLATION_Y_PROPERTY;
    public static final AnimHelper.AnimationType[] INIT_PROPERTY_FIELDS;
    public static final AnimHelper.AnimProperty SHOW_ANIM_ALPHA_PROPERTY;
    public static final AnimHelper.AnimProperty SHOW_ANIM_TRANSLATION_Y_PROPERTY;
    private final SettingsHelper.OnChangedCallback aodShowStateCallback;
    public final PrivacyItemController.Callback callback;
    public final TransparentVideoView.Configs config;
    public final Context context;
    public State currentState;
    public final DisplayLifecycle displayLifecycle;
    public PowerManager.WakeLock drawWakeLock;
    public final AiAgentEffect$geminiStateObserver$1 geminiStateObserver;
    public final Handler handler;
    public AnimatorSet hideAnimSet;
    public boolean isAnimating;
    public boolean isAttachedView;
    public boolean isHideAnimating;
    public boolean isShowAnimating;
    public boolean isVideoPlaying;
    public boolean isVisibleView;
    public int lastDensityDpi;
    public long lastTimeStampElapsed;
    public View layout;
    public final WindowManager.LayoutParams layoutParams;
    public final Object lock;
    public final PowerInteractor powerInteractor;
    public final PowerManager powerManager;
    public final PrivacyItemController privacyController;
    public final AiAgentEffect$$ExternalSyntheticLambda0 releaseRunnable;
    public StandaloneCoroutine screenOffStateObserver;
    public AnimatorSet showAnimSet;
    public State state;
    public final StatusBarStateController statusBarStateController;
    public final AiAgentEffect$statusBarStateListener$1 statusBarStateListener;
    public TransparentVideoView videoView;
    public final Handler wakeLockHandler;
    public final ArrayList showAnimList = new ArrayList();
    public final ArrayList hideAnimList = new ArrayList();
    private final SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class HideAnimatorListener extends AnimHelper.BaseAnimatorListener {
        public HideAnimatorListener(String str) {
            super("AiAgentEffect", str, false);
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            AiAgentEffect aiAgentEffect = AiAgentEffect.this;
            aiAgentEffect.isHideAnimating = false;
            aiAgentEffect.initAnimProperties(true);
            AiAgentEffect.this.releaseDrawWakeLock();
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            AiAgentEffect aiAgentEffect = AiAgentEffect.this;
            aiAgentEffect.isHideAnimating = true;
            aiAgentEffect.isShowAnimating = false;
        }
    }

    public final class ShowAnimatorListener extends AnimHelper.BaseAnimatorListener {
        public ShowAnimatorListener(String str) {
            super("AiAgentEffect", str, false);
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            AnimatorSet animatorSet;
            if (getDebug()) {
                String tag = getTag();
                String logPrefix = getLogPrefix();
                AiAgentEffect aiAgentEffect = AiAgentEffect.this;
                boolean z = aiAgentEffect.isAnimating;
                AnimatorSet animatorSet2 = aiAgentEffect.showAnimSet;
                Boolean boolValueOf = animatorSet2 != null ? Boolean.valueOf(animatorSet2.isRunning()) : null;
                Log.d(tag, logPrefix + " onAnimationCancel isAnimating = " + z + ", isRunning = " + boolValueOf + ", isAttachedView = " + AiAgentEffect.this.isAttachedView);
            }
            setCanceled(true);
            AiAgentEffect aiAgentEffect2 = AiAgentEffect.this;
            if (aiAgentEffect2.isAnimating && (animatorSet = aiAgentEffect2.showAnimSet) != null && animatorSet.isRunning()) {
                AnimatorSet animatorSet3 = AiAgentEffect.this.hideAnimSet;
                if (animatorSet3 != null) {
                    animatorSet3.start();
                    return;
                }
                return;
            }
            AiAgentEffect aiAgentEffect3 = AiAgentEffect.this;
            if (aiAgentEffect3.isAttachedView) {
                aiAgentEffect3.initAnimProperties(true);
            } else {
                aiAgentEffect3.isAnimating = false;
            }
        }

        @Override // com.android.systemui.util.AnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            final TransparentVideoView transparentVideoView;
            if (getDebug()) {
                String tag = getTag();
                String logPrefix = getLogPrefix();
                AiAgentEffect aiAgentEffect = AiAgentEffect.this;
                boolean z = aiAgentEffect.isVisibleView;
                boolean z2 = aiAgentEffect.isAttachedView;
                View view = aiAgentEffect.layout;
                Log.d(tag, logPrefix + " onAnimationStart isVisibleView = " + z + ", isAttachedView = " + z2 + ", visible = " + (view != null ? Integer.valueOf(view.getVisibility()) : null));
            }
            AiAgentEffect aiAgentEffect2 = AiAgentEffect.this;
            if (!aiAgentEffect2.isVisibleView) {
                aiAgentEffect2.isVisibleView = true;
                aiAgentEffect2.updateLayoutVisibility();
            }
            String tag2 = getTag();
            String logPrefix2 = getLogPrefix();
            View view2 = AiAgentEffect.this.layout;
            Integer numValueOf = view2 != null ? Integer.valueOf(view2.getVisibility()) : null;
            Log.d(tag2, logPrefix2 + " onAnimationStart visible = " + numValueOf + ", layout = " + AiAgentEffect.this.layout);
            AiAgentEffect.this.initAnimProperties(false);
            setCanceled(false);
            AiAgentEffect aiAgentEffect3 = AiAgentEffect.this;
            aiAgentEffect3.isShowAnimating = true;
            try {
                if (aiAgentEffect3.drawWakeLock == null) {
                    aiAgentEffect3.drawWakeLock = aiAgentEffect3.powerManager.newWakeLock(128, "AiAgentEffect");
                }
                PowerManager.WakeLock wakeLock = aiAgentEffect3.drawWakeLock;
                if (wakeLock != null) {
                    PowerManager.WakeLock wakeLock2 = wakeLock.isHeld() ? null : wakeLock;
                    if (wakeLock2 != null) {
                        Log.d("AiAgentEffect", "applyDrawWakeLock");
                        wakeLock2.acquire();
                        aiAgentEffect3.wakeLockHandler.postDelayed(new AiAgentEffect$sam$java_lang_Runnable$0(aiAgentEffect3.releaseRunnable), 60000L);
                    }
                }
            } catch (Exception e) {
                EmergencyButton$$ExternalSyntheticOutline0.m("applyDrawWakeLock exception = ", e, "AiAgentEffect");
            }
            AiAgentEffect aiAgentEffect4 = AiAgentEffect.this;
            if (aiAgentEffect4.isVideoPlaying || (transparentVideoView = aiAgentEffect4.videoView) == null) {
                return;
            }
            aiAgentEffect4.isVideoPlaying = true;
            transparentVideoView.load(aiAgentEffect4.context.getResources().openRawResourceFd(R.raw.wave_mid), aiAgentEffect4.config, new Runnable() { // from class: com.android.systemui.aiagent.AiAgentEffect$ShowAnimatorListener$onAnimationStart$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    transparentVideoView.play();
                }
            }, new Function1() { // from class: com.android.systemui.aiagent.AiAgentEffect$ShowAnimatorListener$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    Log.e(this.f$0.getTag(), "cannot load transparent video: " + ((IMediaPlayer$MediaError) obj));
                    return Unit.INSTANCE;
                }
            });
        }
    }

    public final class State {
        public final boolean isAODShown;
        public final boolean isDozing;
        public final boolean isFolderOpened;
        public final boolean isForeground;
        public final boolean isGeminiForeground;
        public final boolean isPlaying;
        public final boolean isScreenOn;
        public final boolean isUsingMicForEffect;

        public State() {
            this(false, false, false, false, false, false, false, false, 255, null);
        }

        public static State copy$default(State state, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i) {
            if ((i & 1) != 0) {
                z = state.isGeminiForeground;
            }
            boolean z9 = z;
            if ((i & 2) != 0) {
                z2 = state.isForeground;
            }
            boolean z10 = z2;
            if ((i & 4) != 0) {
                z3 = state.isPlaying;
            }
            boolean z11 = z3;
            if ((i & 8) != 0) {
                z4 = state.isUsingMicForEffect;
            }
            boolean z12 = z4;
            if ((i & 16) != 0) {
                z5 = state.isFolderOpened;
            }
            boolean z13 = z5;
            if ((i & 32) != 0) {
                z6 = state.isAODShown;
            }
            boolean z14 = z6;
            boolean z15 = (i & 64) != 0 ? state.isDozing : z7;
            boolean z16 = (i & 128) != 0 ? state.isScreenOn : z8;
            state.getClass();
            return new State(z9, z10, z11, z12, z13, z14, z15, z16);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof State)) {
                return false;
            }
            State state = (State) obj;
            return this.isGeminiForeground == state.isGeminiForeground && this.isForeground == state.isForeground && this.isPlaying == state.isPlaying && this.isUsingMicForEffect == state.isUsingMicForEffect && this.isFolderOpened == state.isFolderOpened && this.isAODShown == state.isAODShown && this.isDozing == state.isDozing && this.isScreenOn == state.isScreenOn;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isScreenOn) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.isGeminiForeground) * 31, 31, this.isForeground), 31, this.isPlaying), 31, this.isUsingMicForEffect), 31, this.isFolderOpened), 31, this.isAODShown), 31, this.isDozing);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("State(isGeminiForeground=");
            sb.append(this.isGeminiForeground);
            sb.append(", isForeground=");
            sb.append(this.isForeground);
            sb.append(", isPlaying=");
            sb.append(this.isPlaying);
            sb.append(", isUsingMicForEffect=");
            sb.append(this.isUsingMicForEffect);
            sb.append(", isFolderOpened=");
            sb.append(this.isFolderOpened);
            sb.append(", isAODShown=");
            sb.append(this.isAODShown);
            sb.append(", isDozing=");
            sb.append(this.isDozing);
            sb.append(", isScreenOn=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isScreenOn, ")");
        }

        public State(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
            this.isGeminiForeground = z;
            this.isForeground = z2;
            this.isPlaying = z3;
            this.isUsingMicForEffect = z4;
            this.isFolderOpened = z5;
            this.isAODShown = z6;
            this.isDozing = z7;
            this.isScreenOn = z8;
        }

        public /* synthetic */ State(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? false : z3, (i & 8) != 0 ? false : z4, (i & 16) != 0 ? false : z5, (i & 32) != 0 ? false : z6, (i & 64) != 0 ? false : z7, (i & 128) != 0 ? false : z8);
        }
    }

    static {
        new Companion(null);
        AnimHelper.AnimationType animationType = AnimHelper.AnimationType.ALPHA;
        AnimHelper.AnimationType animationType2 = AnimHelper.AnimationType.TRANSLATION_Y;
        INIT_PROPERTY_FIELDS = new AnimHelper.AnimationType[]{animationType, animationType2};
        PathInterpolator pathInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.32f, 0.94f, 0.6f, 1.0f);
        PathInterpolator pathInterpolator3 = new PathInterpolator(0.16f, 0.0f, 0.3f, 1.0f);
        PathInterpolator pathInterpolator4 = new PathInterpolator(0.0f, 0.0f, 1.0f, 1.0f);
        SHOW_ANIM_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 500, 0, 0.0f, 1.0f, pathInterpolator2);
        SHOW_ANIM_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 500, 0, 0.0f, -1.0f, pathInterpolator);
        HIDE_ANIM_ALPHA_PROPERTY = new AnimHelper.AnimProperty(animationType, 200, 300, 1.0f, 0.0f, pathInterpolator4);
        HIDE_ANIM_TRANSLATION_Y_PROPERTY = new AnimHelper.AnimProperty(animationType2, 500, 0, -1.0f, 0.0f, pathInterpolator3);
    }

    /* JADX WARN: Type inference failed for: r12v18, types: [com.android.systemui.aiagent.AiAgentEffect$statusBarStateListener$1] */
    /* JADX WARN: Type inference failed for: r12v25, types: [com.android.systemui.aiagent.AiAgentEffect$$ExternalSyntheticLambda0] */
    public AiAgentEffect(Context context, PrivacyItemController privacyItemController, PowerManager powerManager, StatusBarStateController statusBarStateController, PowerInteractor powerInteractor, IActivityManager iActivityManager) {
        this.context = context;
        this.privacyController = privacyItemController;
        this.powerManager = powerManager;
        this.statusBarStateController = statusBarStateController;
        this.powerInteractor = powerInteractor;
        this.lastDensityDpi = context.getResources().getConfiguration().densityDpi;
        AnimHelper.AnimationState animationState = AnimHelper.AnimationState.NONE;
        this.handler = new Handler(Looper.getMainLooper());
        this.wakeLockHandler = new Handler(Looper.getMainLooper());
        this.displayLifecycle = (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class);
        boolean z = false;
        State state = new State(false, z, false, false, false, false, false, false, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount, null);
        this.currentState = state;
        this.state = State.copy$default(state, false, false, false, false, false, false, false, false, 255);
        this.lock = new Object();
        this.config = new TransparentVideoView.Configs(0.2f, z, null, 2, null);
        IForegroundServiceObserver.Stub stub = new IForegroundServiceObserver.Stub() { // from class: com.android.systemui.aiagent.AiAgentEffect$foregroundServiceObserver$1
            public final void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z2) {
                if (Intrinsics.areEqual(str, "com.google.android.googlequicksearchbox")) {
                    AiAgentEffect aiAgentEffect = this.this$0;
                    synchronized (aiAgentEffect.lock) {
                        aiAgentEffect.setState(AiAgentEffect.State.copy$default(aiAgentEffect.state, false, z2, false, false, false, false, false, false, IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList));
                        Unit unit = Unit.INSTANCE;
                    }
                }
            }
        };
        Log.d("AiAgentEffect", "init");
        iActivityManager.registerForegroundServiceObserver(stub);
        this.aodShowStateCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.aiagent.AiAgentEffect$aodShowStateCallback$1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null) {
                    AiAgentEffect aiAgentEffect = this.this$0;
                    if (Intrinsics.areEqual(Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE), uri)) {
                        boolean zIsAODShown = aiAgentEffect.settingsHelper.isAODShown();
                        synchronized (aiAgentEffect.lock) {
                            aiAgentEffect.setState(AiAgentEffect.State.copy$default(aiAgentEffect.state, false, false, false, false, false, zIsAODShown, false, false, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut));
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                }
            }
        };
        this.callback = new PrivacyItemController.Callback() { // from class: com.android.systemui.aiagent.AiAgentEffect$callback$1
            public static final void onPrivacyItemsChanged$resetEffect(AiAgentEffect aiAgentEffect) {
                Log.d("AiAgentEffect", "onPrivacyItemsChanged privacyItems isEmpty or found item is null");
                synchronized (aiAgentEffect.lock) {
                    aiAgentEffect.setState(AiAgentEffect.State.copy$default(aiAgentEffect.state, false, false, false, false, false, false, false, false, IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut));
                    Unit unit = Unit.INSTANCE;
                }
                AiAgentEffect.access$unregisterListener(aiAgentEffect);
                aiAgentEffect.lastTimeStampElapsed = 0L;
            }

            @Override // com.android.systemui.privacy.PrivacyItemController.Callback
            public final void onPrivacyItemsChanged(List list) {
                Object next;
                ListPopupWindow$$ExternalSyntheticOutline0.m(list.size(), "onPrivacyItemsChanged privacyItems.size = ", "AiAgentEffect");
                AnimHelper.AnimationState animationState2 = AnimHelper.AnimationState.NONE;
                AnimHelper.AnimationType[] animationTypeArr = AiAgentEffect.INIT_PROPERTY_FIELDS;
                AiAgentEffect aiAgentEffect = this.this$0;
                aiAgentEffect.getClass();
                if (list.isEmpty()) {
                    onPrivacyItemsChanged$resetEffect(aiAgentEffect);
                    return;
                }
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    PrivacyItem privacyItem = (PrivacyItem) next;
                    if (privacyItem.privacyType == PrivacyType.TYPE_MICROPHONE && Intrinsics.areEqual(privacyItem.application.packageName, "com.google.android.googlequicksearchbox")) {
                        break;
                    }
                }
                PrivacyItem privacyItem2 = (PrivacyItem) next;
                if (privacyItem2 == null) {
                    onPrivacyItemsChanged$resetEffect(aiAgentEffect);
                } else {
                    AiAgentEffect.access$display(aiAgentEffect, privacyItem2);
                }
            }
        };
        this.statusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.aiagent.AiAgentEffect$statusBarStateListener$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z2) {
                AiAgentEffect aiAgentEffect = this.this$0;
                synchronized (aiAgentEffect.lock) {
                    aiAgentEffect.setState(AiAgentEffect.State.copy$default(aiAgentEffect.state, false, false, false, false, false, false, z2, false, 191));
                    Unit unit = Unit.INSTANCE;
                }
            }
        };
        this.geminiStateObserver = new AiAgentEffect$geminiStateObserver$1(this);
        new DisplayLifecycle.Observer() { // from class: com.android.systemui.aiagent.AiAgentEffect$displayLifecycleObserver$1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z2) {
                AiAgentEffect aiAgentEffect = this.this$0;
                aiAgentEffect.lastDensityDpi = aiAgentEffect.context.getResources().getConfiguration().densityDpi;
                AiAgentEffect aiAgentEffect2 = this.this$0;
                synchronized (aiAgentEffect2.lock) {
                    aiAgentEffect2.setState(AiAgentEffect.State.copy$default(aiAgentEffect2.state, false, false, false, false, z2, false, false, false, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount));
                    Unit unit = Unit.INSTANCE;
                }
            }
        };
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 0, 0, VolteConstants.ErrorCode.REG_SUBSCRIBED, 1336, -3);
        layoutParams.semAddExtensionFlags(262144);
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("AiAgentEffectView");
        layoutParams.setTrustedOverlay();
        this.layoutParams = layoutParams;
        this.releaseRunnable = new Function0() { // from class: com.android.systemui.aiagent.AiAgentEffect$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PowerManager.WakeLock wakeLock;
                AiAgentEffect aiAgentEffect = this.f$0;
                AiAgentEffect.State state2 = aiAgentEffect.currentState;
                if ((state2.isUsingMicForEffect && state2.isGeminiForeground) || (wakeLock = aiAgentEffect.drawWakeLock) == null || !wakeLock.isHeld()) {
                    Log.d("AiAgentEffect", "releaseRunnable releaseIfNeed re-waiting");
                    aiAgentEffect.wakeLockHandler.postDelayed(new AiAgentEffect$sam$java_lang_Runnable$0(aiAgentEffect.releaseRunnable), 60000L);
                } else {
                    Log.d("AiAgentEffect", "releaseRunnable releaseDrawWakeLock");
                    aiAgentEffect.releaseDrawWakeLock();
                }
                return Unit.INSTANCE;
            }
        };
    }

    public static final void access$display(AiAgentEffect aiAgentEffect, PrivacyItem privacyItem) {
        aiAgentEffect.getClass();
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("display usingMicItem = ", privacyItem.log, "AiAgentEffect");
        AnimHelper.AnimationState animationState = AnimHelper.AnimationState.NONE;
        if (aiAgentEffect.lastTimeStampElapsed != privacyItem.timeStampElapsed) {
            synchronized (aiAgentEffect.lock) {
                aiAgentEffect.setState(State.copy$default(aiAgentEffect.state, false, false, false, false, aiAgentEffect.displayLifecycle.mIsFolderOpened, aiAgentEffect.settingsHelper.isAODShown(), aiAgentEffect.statusBarStateController.isDozing(), true, 15));
                Unit unit = Unit.INSTANCE;
            }
            aiAgentEffect.settingsHelper.registerCallback(aiAgentEffect.aodShowStateCallback, Settings.System.getUriFor(SettingsHelper.INDEX_AOD_SHOW_STATE));
            aiAgentEffect.statusBarStateController.addCallback(aiAgentEffect.statusBarStateListener);
            OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
            AiAgentEffect$geminiStateObserver$1 aiAgentEffect$geminiStateObserver$1 = aiAgentEffect.geminiStateObserver;
            ongoingActivityDataHelper.getClass();
            ((ArrayList) OngoingActivityDataHelper.geminiStateObservers).add(aiAgentEffect$geminiStateObserver$1);
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            aiAgentEffect.screenOffStateObserver = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE), null, null, new AiAgentEffect$registerListener$2(aiAgentEffect, null), 3);
            aiAgentEffect.lastTimeStampElapsed = privacyItem.timeStampElapsed;
            synchronized (aiAgentEffect.lock) {
                aiAgentEffect.setState(State.copy$default(aiAgentEffect.state, false, false, false, true, false, false, false, false, IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut));
            }
        }
    }

    public static final void access$hiding(AiAgentEffect aiAgentEffect) {
        boolean z = aiAgentEffect.isVisibleView;
        boolean z2 = aiAgentEffect.isShowAnimating;
        boolean z3 = aiAgentEffect.isAnimating;
        AnimatorSet animatorSet = aiAgentEffect.showAnimSet;
        Boolean boolValueOf = animatorSet != null ? Boolean.valueOf(animatorSet.isRunning()) : null;
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("hiding isVisibleView = ", ", isStartAnimating = ", ", isAnimating = ", z, z2);
        sbM.append(z3);
        sbM.append(", isShowRunning = ");
        sbM.append(boolValueOf);
        Log.d("AiAgentEffect", sbM.toString());
        if (aiAgentEffect.settingsHelper.isRemoveAnimation() || !aiAgentEffect.isAttachedView) {
            return;
        }
        if (aiAgentEffect.isShowAnimating) {
            AnimatorSet animatorSet2 = aiAgentEffect.showAnimSet;
            if (animatorSet2 != null) {
                animatorSet2.cancel();
            }
            AnimatorSet animatorSet3 = aiAgentEffect.hideAnimSet;
            if (animatorSet3 != null) {
                animatorSet3.start();
                return;
            }
            return;
        }
        if (!aiAgentEffect.isVisibleView) {
            if (aiAgentEffect.isAnimating) {
                aiAgentEffect.isAnimating = false;
                return;
            }
            return;
        }
        AnimatorSet animatorSet4 = aiAgentEffect.showAnimSet;
        if (animatorSet4 != null) {
            animatorSet4.cancel();
        }
        AnimatorSet animatorSet5 = aiAgentEffect.hideAnimSet;
        if (animatorSet5 != null) {
            animatorSet5.start();
        }
    }

    public static final void access$showing(final AiAgentEffect aiAgentEffect) {
        CarrierTextManager$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("showing isAnimating = ", " -> true, isAttachedView = ", ", isVisibleView = ", aiAgentEffect.isAnimating, aiAgentEffect.isAttachedView), aiAgentEffect.isVisibleView, ", isHideAnimating = ", aiAgentEffect.isHideAnimating, "AiAgentEffect");
        if (aiAgentEffect.settingsHelper.isRemoveAnimation()) {
            return;
        }
        if (aiAgentEffect.isAttachedView) {
            if (aiAgentEffect.isAnimating) {
                AnimatorSet animatorSet = aiAgentEffect.showAnimSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                AnimatorSet animatorSet2 = aiAgentEffect.hideAnimSet;
                if (animatorSet2 != null) {
                    animatorSet2.cancel();
                }
            }
            aiAgentEffect.initAnimProperties(false);
            aiAgentEffect.isAnimating = true;
            AnimatorSet animatorSet3 = aiAgentEffect.showAnimSet;
            if (animatorSet3 != null) {
                animatorSet3.start();
                return;
            }
            return;
        }
        aiAgentEffect.isAttachedView = true;
        boolean z = aiAgentEffect.currentState.isFolderOpened;
        Context context = aiAgentEffect.context;
        Log.d("AiAgentEffect", "updateLayoutAndAnimators config = " + context.getResources().getConfiguration());
        aiAgentEffect.layout = LayoutInflater.from(context).inflate(R.layout.ai_agent_effect_layout, (ViewGroup) null);
        aiAgentEffect.updateLayoutVisibility();
        View view = aiAgentEffect.layout;
        TransparentVideoView transparentVideoView = view != null ? (TransparentVideoView) view.findViewById(R.id.ai_agent_effect) : null;
        aiAgentEffect.videoView = transparentVideoView;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) (transparentVideoView != null ? transparentVideoView.getLayoutParams() : null);
        if (marginLayoutParams != null) {
            marginLayoutParams.height = aiAgentEffect.context.getResources().getDimensionPixelSize(R.dimen.ai_agent_effect_translation_y) * (-1);
            marginLayoutParams.bottomMargin = aiAgentEffect.context.getResources().getDimensionPixelSize(R.dimen.ai_agent_effect_translation_y);
        }
        TransparentVideoView transparentVideoView2 = aiAgentEffect.videoView;
        if (transparentVideoView2 != null) {
            transparentVideoView2.setLayoutParams(marginLayoutParams);
        }
        aiAgentEffect.initAnimProperties(false);
        AnimHelper animHelper = AnimHelper.INSTANCE;
        AnimHelper.AnimPairSet animPairSet = new AnimHelper.AnimPairSet(aiAgentEffect.videoView, SHOW_ANIM_ALPHA_PROPERTY);
        TransparentVideoView transparentVideoView3 = aiAgentEffect.videoView;
        AnimHelper.AnimProperty animProperty = SHOW_ANIM_TRANSLATION_Y_PROPERTY;
        animProperty.setToValue(aiAgentEffect.context.getResources().getDimensionPixelSize(R.dimen.ai_agent_effect_translation_y) * 0.9f);
        Unit unit = Unit.INSTANCE;
        aiAgentEffect.showAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet, new AnimHelper.AnimPairSet(transparentVideoView3, animProperty)}, aiAgentEffect.showAnimList, aiAgentEffect.new ShowAnimatorListener("Show"));
        AnimHelper.AnimPairSet animPairSet2 = new AnimHelper.AnimPairSet(aiAgentEffect.videoView, HIDE_ANIM_ALPHA_PROPERTY);
        TransparentVideoView transparentVideoView4 = aiAgentEffect.videoView;
        AnimHelper.AnimProperty animProperty2 = HIDE_ANIM_TRANSLATION_Y_PROPERTY;
        animProperty2.setFromValue(aiAgentEffect.context.getResources().getDimensionPixelSize(R.dimen.ai_agent_effect_translation_y) * 0.9f);
        aiAgentEffect.hideAnimSet = animHelper.makeAnimSet(new AnimHelper.AnimPairSet[]{animPairSet2, new AnimHelper.AnimPairSet(transparentVideoView4, animProperty2)}, aiAgentEffect.hideAnimList, aiAgentEffect.new HideAnimatorListener("Hide"));
        final TransparentVideoView transparentVideoView5 = aiAgentEffect.videoView;
        if (transparentVideoView5 != null) {
            OneShotPreDrawListener.add(transparentVideoView5, new Runnable() { // from class: com.android.systemui.aiagent.AiAgentEffect$updateLayoutAndAnimators$$inlined$doOnPreDraw$1
                @Override // java.lang.Runnable
                public final void run() {
                    Log.d("AiAgentEffect", "doOnPreDraw");
                    AiAgentEffect aiAgentEffect2 = aiAgentEffect;
                    aiAgentEffect2.isVisibleView = true;
                    aiAgentEffect2.updateLayoutVisibility();
                    AiAgentEffect.access$showing(aiAgentEffect);
                }
            });
        }
        boolean z2 = aiAgentEffect.currentState.isFolderOpened;
        ((WindowManager) aiAgentEffect.context.getSystemService("window")).addView(aiAgentEffect.layout, aiAgentEffect.layoutParams);
    }

    public static final void access$unregisterListener(AiAgentEffect aiAgentEffect) {
        aiAgentEffect.settingsHelper.unregisterCallback(aiAgentEffect.aodShowStateCallback);
        aiAgentEffect.statusBarStateController.removeCallback(aiAgentEffect.statusBarStateListener);
        StandaloneCoroutine standaloneCoroutine = aiAgentEffect.screenOffStateObserver;
        if (standaloneCoroutine == null || !standaloneCoroutine.isActive()) {
            return;
        }
        StandaloneCoroutine standaloneCoroutine2 = aiAgentEffect.screenOffStateObserver;
        standaloneCoroutine2.getClass();
        standaloneCoroutine2.cancel(null);
    }

    public static boolean shouldShowEffect(State state) {
        if (!state.isGeminiForeground || !state.isForeground || !state.isPlaying || !state.isUsingMicForEffect) {
            return false;
        }
        if (state.isAODShown) {
            return true;
        }
        return !state.isDozing && state.isScreenOn;
    }

    public final void displayEffect(final AnimHelper.AnimationState animationState) {
        Log.d("AiAgentEffect", "displayEffect animState = " + animationState);
        this.handler.post(new Runnable() { // from class: com.android.systemui.aiagent.AiAgentEffect.displayEffect.1

            /* renamed from: com.android.systemui.aiagent.AiAgentEffect$displayEffect$1$WhenMappings */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[AnimHelper.AnimationState.values().length];
                    try {
                        iArr[AnimHelper.AnimationState.SHOWING.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[AnimHelper.AnimationState.HIDING.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i = WhenMappings.$EnumSwitchMapping$0[animationState.ordinal()];
                if (i == 1) {
                    AiAgentEffect.access$showing(this);
                } else {
                    if (i != 2) {
                        return;
                    }
                    AiAgentEffect.access$hiding(this);
                }
            }
        });
    }

    public final void initAnimProperties(boolean z) {
        TransparentVideoView transparentVideoView;
        AnimHelper.INSTANCE.initProperty(INIT_PROPERTY_FIELDS, this.videoView);
        if (z) {
            this.isAnimating = false;
            if (this.isAttachedView) {
                this.isVisibleView = false;
                updateLayoutVisibility();
            }
            if (!this.isVideoPlaying || (transparentVideoView = this.videoView) == null) {
                return;
            }
            transparentVideoView.pause();
            transparentVideoView.release();
            this.isVideoPlaying = false;
        }
    }

    public final void releaseDrawWakeLock() {
        try {
            Log.d("AiAgentEffect", "releaseDrawWakeLock");
            PowerManager.WakeLock wakeLock = this.drawWakeLock;
            if (wakeLock != null) {
                if (!wakeLock.isHeld()) {
                    wakeLock = null;
                }
                if (wakeLock != null) {
                    wakeLock.release();
                }
            }
            this.wakeLockHandler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("releaseDrawWakeLock exception = ", e, "AiAgentEffect");
        }
    }

    public final void setState(State state) throws SecurityException {
        State stateCopy$default;
        ArrayList arrayList;
        boolean z;
        if (Intrinsics.areEqual(this.state, state)) {
            return;
        }
        this.state = state;
        synchronized (this.lock) {
            stateCopy$default = State.copy$default(this.state, false, false, false, false, false, false, false, false, 255);
            Unit unit = Unit.INSTANCE;
        }
        boolean z2 = this.currentState.isFolderOpened;
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) this.context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (runningServices != null) {
            arrayList = new ArrayList();
            for (Object obj : runningServices) {
                if (((ActivityManager.RunningServiceInfo) obj).foreground) {
                    arrayList.add(obj);
                }
            }
        } else {
            arrayList = null;
        }
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                if (Intrinsics.areEqual(((ActivityManager.RunningServiceInfo) obj2).service.getClassName(), "com.google.android.apps.search.assistant.surfaces.voice.robin.ui.conversationmode.service.ConversationService")) {
                    z = true;
                    break;
                }
            }
            z = false;
        } else {
            z = false;
        }
        State stateCopy$default2 = State.copy$default(stateCopy$default, z, false, false, false, false, false, false, false, 254);
        boolean z3 = this.isVisibleView;
        boolean z4 = this.isAttachedView;
        boolean z5 = this.isAnimating;
        boolean z6 = this.isShowAnimating;
        boolean z7 = this.isHideAnimating;
        boolean z8 = this.isVideoPlaying;
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("vis=", ", attached=", ", animating=", z3, z4);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z5, ", showing=", z6, ", hiding=");
        Log.d("AiAgentEffect", "updateState: " + stateCopy$default2 + ", " + KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z7, ", playing=", z8));
        if (shouldShowEffect(stateCopy$default2)) {
            if (!shouldShowEffect(this.currentState) || !this.isVisibleView || !this.isAnimating) {
                displayEffect(AnimHelper.AnimationState.SHOWING);
            }
        } else if (!shouldShowEffect(stateCopy$default2) && (shouldShowEffect(this.currentState) || (this.isVisibleView && this.isAnimating && !this.isHideAnimating))) {
            displayEffect(AnimHelper.AnimationState.HIDING);
        }
        this.currentState = stateCopy$default2;
    }

    public final void updateConfiguration(Configuration configuration) {
        if (this.settingsHelper.isRemoveAnimation() || !this.isAttachedView) {
            this.lastDensityDpi = configuration.densityDpi;
            return;
        }
        int i = this.lastDensityDpi;
        int i2 = configuration.densityDpi;
        if (i != i2) {
            this.lastDensityDpi = i2;
        }
    }

    public final void updateLayoutVisibility() {
        View view = this.layout;
        if (view != null) {
            view.setVisibility(this.isVisibleView ? 0 : 8);
        }
    }
}
