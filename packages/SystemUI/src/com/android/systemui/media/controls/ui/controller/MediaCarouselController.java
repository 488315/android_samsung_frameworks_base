package com.android.systemui.media.controls.ui.controller;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.BlendMode;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.animation.Interpolators;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.internal.widget.CachingIconView;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.ui.animation.ColorSchemeTransition;
import com.android.systemui.media.controls.ui.animation.MetadataAnimationHandler;
import com.android.systemui.media.controls.ui.binder.SeekBarObserver;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.controller.MediaPlayerData;
import com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.media.controls.ui.view.MediaScrollView;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$listening$1;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PageIndicator;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.featurepods.media.domain.interactor.MediaControlChipInteractor;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.surfaceeffects.loadingeffect.LoadingEffectView;
import com.android.systemui.surfaceeffects.ripple.MultiRippleController;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController;
import com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseView;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.animation.TransitionViewState;
import com.android.systemui.util.animation.UniqueObjectHostView;
import com.android.systemui.util.animation.UniqueObjectHostViewKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* loaded from: classes2.dex */
public final class MediaCarouselController implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final PathInterpolator TRANSFORM_BEZIER = new PathInterpolator(0.68f, 0.0f, 0.0f, 1.0f);
    public final ActivityStarter activityStarter;
    public boolean allowMediaPlayerOnLockScreen;
    public final MediaCarouselController$animationScaleObserver$1 animationScaleObserver;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Executor bgExecutor;
    public Locale carouselLocale;
    public int carouselMeasureHeight;
    public int carouselMeasureWidth;
    public final Context context;
    public final List controlViewModels;
    public final Map controllerById;
    public int currentCarouselHeight;
    public int currentCarouselWidth;
    public boolean currentlyDisableScrolling;
    public boolean currentlyShowingOnlyActive;
    public final MediaCarouselControllerLogger debugLogger;
    public MediaHostState desiredHostState;
    public final FalsingManager falsingManager;
    public final GlobalSettings globalSettings;
    public final ReadonlyStateFlow isGoingToDozing;
    public final ReadonlyStateFlow isOnGone;
    public boolean isRtl;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final MediaCarouselController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public final MediaUiEventLogger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final MediaScrollView mediaCarousel;
    public final MediaCarouselScrollHandler mediaCarouselScrollHandler;
    public final MediaCarouselViewModel mediaCarouselViewModel;
    public final ViewGroup mediaContent;
    public final MediaControlChipInteractor mediaControlChipInteractor;
    public final Provider mediaControlPanelFactory;
    public final ViewGroup mediaFrame;
    public final MediaHostStatesManager mediaHostStatesManager;
    public final MediaDataManager mediaManager;
    public final Provider mediaViewControllerFactory;
    public final PageIndicator pageIndicator;
    public boolean playersVisible;
    public final SecureSettings secureSettings;
    public ImageView settingsButton;
    public final SystemClock systemClock;
    public final DelayableExecutor uiExecutor;
    public Function0 updateHostVisibility;
    public Function0 updateUserVisibility;
    public final VisualStabilityProvider visualStabilityProvider;
    public int desiredLocation = -1;
    public int currentEndLocation = -1;
    public int currentStartLocation = -1;
    public float currentTransitionProgress = 1.0f;
    public final Set keysNeedRemoval = new LinkedHashSet();
    public boolean currentlyExpanded = true;

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass1(Object obj) {
            super(0, obj, MediaCarouselController.class, "onSwipeToDismiss", "onSwipeToDismiss()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ((MediaCarouselController) this.receiver).onSwipeToDismiss();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$2, reason: invalid class name */
    final /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0 {
        public AnonymousClass2(Object obj) {
            super(0, obj, MediaCarouselController.class, "updatePageIndicatorLocation", "updatePageIndicatorLocation()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            MediaCarouselController mediaCarouselController = (MediaCarouselController) this.receiver;
            Companion companion = MediaCarouselController.Companion;
            mediaCarouselController.updatePageIndicatorLocation();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass3(Object obj) {
            super(1, obj, MediaCarouselController.class, "updateSeekbarListening", "updateSeekbarListening(Z)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            MediaCarouselController mediaCarouselController = (MediaCarouselController) this.receiver;
            Companion companion = MediaCarouselController.Companion;
            mediaCarouselController.updateSeekbarListening(zBooleanValue);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass4(Object obj) {
            super(1, obj, MediaCarouselController.class, "closeGuts", "closeGuts(Z)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            ((MediaCarouselController) this.receiver).getClass();
            MediaCarouselController.closeGuts(zBooleanValue);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$7$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ MediaCarouselController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(MediaCarouselController mediaCarouselController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = mediaCarouselController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                this.this$0.listenForAnyStateToGoneKeyguardTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
                this.this$0.listenForAnyStateToLockscreenTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
                this.this$0.listenForAnyStateToDozingTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass7(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass7 anonymousClass7 = MediaCarouselController.this.new AnonymousClass7((Continuation) obj3);
            anonymousClass7.L$0 = (LifecycleOwner) obj;
            return anonymousClass7.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(MediaCarouselController.this, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v16, types: [com.android.systemui.media.controls.ui.controller.MediaCarouselController$animationScaleObserver$1] */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.android.keyguard.KeyguardUpdateMonitorCallback, com.android.systemui.media.controls.ui.controller.MediaCarouselController$keyguardUpdateMonitorCallback$1] */
    public MediaCarouselController(CoroutineScope coroutineScope, Context context, Provider provider, VisualStabilityProvider visualStabilityProvider, MediaHostStatesManager mediaHostStatesManager, ActivityStarter activityStarter, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, final DelayableExecutor delayableExecutor, Executor executor, CoroutineDispatcher coroutineDispatcher2, MediaDataManager mediaDataManager, ConfigurationController configurationController, FalsingManager falsingManager, DumpManager dumpManager, MediaUiEventLogger mediaUiEventLogger, MediaCarouselControllerLogger mediaCarouselControllerLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardTransitionInteractor keyguardTransitionInteractor, GlobalSettings globalSettings, SecureSettings secureSettings, MediaCarouselViewModel mediaCarouselViewModel, Provider provider2, DeviceEntryInteractor deviceEntryInteractor, MediaControlChipInteractor mediaControlChipInteractor) {
        ViewGroup viewGroup;
        int width;
        this.context = context;
        this.mediaControlPanelFactory = provider;
        this.visualStabilityProvider = visualStabilityProvider;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.activityStarter = activityStarter;
        this.systemClock = systemClock;
        this.mainDispatcher = coroutineDispatcher;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = executor;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.mediaManager = mediaDataManager;
        this.falsingManager = falsingManager;
        this.logger = mediaUiEventLogger;
        this.debugLogger = mediaCarouselControllerLogger;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.globalSettings = globalSettings;
        this.secureSettings = secureSettings;
        this.mediaCarouselViewModel = mediaCarouselViewModel;
        this.mediaViewControllerFactory = provider2;
        this.mediaControlChipInteractor = mediaControlChipInteractor;
        this.animationScaleObserver = new ContentObserver(this, delayableExecutor) { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$animationScaleObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                MediaPlayerData.INSTANCE.getClass();
                for (MediaControlPanel mediaControlPanel : MediaPlayerData.mediaPlayers.values()) {
                    SeekBarObserver seekBarObserver = mediaControlPanel.mSeekBarObserver;
                    if (seekBarObserver != null) {
                        seekBarObserver.animationEnabled = mediaControlPanel.mGlobalSettings.getFloat(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE, 1.0f) > 0.0f;
                    }
                }
            }
        };
        ConfigurationController.ConfigurationListener configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$configListener$1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v0 */
            /* JADX WARN: Type inference failed for: r1v1, types: [boolean, int] */
            /* JADX WARN: Type inference failed for: r1v2 */
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                if (configuration == null) {
                    return;
                }
                int width2 = 0;
                ?? r1 = configuration.getLayoutDirection() != 1 ? 0 : 1;
                MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                MediaCarouselController mediaCarouselController = this.this$0;
                if (r1 != mediaCarouselController.isRtl) {
                    mediaCarouselController.isRtl = r1;
                    mediaCarouselController.mediaFrame.setLayoutDirection(r1);
                    MediaScrollView mediaScrollView = mediaCarouselController.mediaCarouselScrollHandler.scrollView;
                    if (mediaScrollView.isLayoutRtl()) {
                        ViewGroup viewGroup2 = mediaScrollView.contentContainer;
                        if (viewGroup2 == null) {
                            viewGroup2 = null;
                        }
                        width2 = viewGroup2.getWidth() - mediaScrollView.getWidth();
                    }
                    mediaScrollView.setScrollX(width2);
                }
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() throws Resources.NotFoundException {
                MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                MediaCarouselController mediaCarouselController = this.this$0;
                mediaCarouselController.updatePlayers(true);
                mediaCarouselController.inflateSettingsButton();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLocaleListChanged() throws Resources.NotFoundException {
                MediaCarouselController mediaCarouselController = this.this$0;
                if (Intrinsics.areEqual(mediaCarouselController.carouselLocale, mediaCarouselController.context.getResources().getConfiguration().getLocales().get(0))) {
                    return;
                }
                mediaCarouselController.carouselLocale = mediaCarouselController.context.getResources().getConfiguration().getLocales().get(0);
                mediaCarouselController.updatePlayers(true);
                mediaCarouselController.inflateSettingsButton();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() throws Resources.NotFoundException {
                MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                MediaCarouselController mediaCarouselController = this.this$0;
                mediaCarouselController.updatePlayers(false);
                mediaCarouselController.inflateSettingsButton();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() throws Resources.NotFoundException {
                MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                MediaCarouselController mediaCarouselController = this.this$0;
                mediaCarouselController.updatePlayers(false);
                mediaCarouselController.inflateSettingsButton();
            }
        };
        ?? r8 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i) {
                MediaCarouselController mediaCarouselController = this.this$0;
                boolean zIsUserInLockdown = mediaCarouselController.keyguardUpdateMonitor.isUserInLockdown(i);
                MediaCarouselControllerLogger mediaCarouselControllerLogger2 = mediaCarouselController.debugLogger;
                if (zIsUserInLockdown) {
                    mediaCarouselControllerLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    MediaCarouselControllerLogger$$ExternalSyntheticLambda0 mediaCarouselControllerLogger$$ExternalSyntheticLambda0 = new MediaCarouselControllerLogger$$ExternalSyntheticLambda0(1);
                    LogBuffer logBuffer = mediaCarouselControllerLogger2.buffer;
                    logBuffer.commit(logBuffer.obtain("MediaCarouselCtlrLog", logLevel, mediaCarouselControllerLogger$$ExternalSyntheticLambda0, null));
                    mediaCarouselController.mediaCarousel.setVisibility(8);
                    return;
                }
                if (mediaCarouselController.keyguardUpdateMonitor.mUserManager.isUserUnlocked(i)) {
                    mediaCarouselControllerLogger2.getClass();
                    LogLevel logLevel2 = LogLevel.DEBUG;
                    MediaCarouselControllerLogger$$ExternalSyntheticLambda0 mediaCarouselControllerLogger$$ExternalSyntheticLambda02 = new MediaCarouselControllerLogger$$ExternalSyntheticLambda0(2);
                    LogBuffer logBuffer2 = mediaCarouselControllerLogger2.buffer;
                    logBuffer2.commit(logBuffer2.obtain("MediaCarouselCtlrLog", logLevel2, mediaCarouselControllerLogger$$ExternalSyntheticLambda02, null));
                    mediaCarouselController.mediaCarousel.setVisibility(0);
                }
            }
        };
        this.keyguardUpdateMonitorCallback = r8;
        this.updateHostVisibility = new MediaCarouselController$$ExternalSyntheticLambda0();
        this.controllerById = new LinkedHashMap();
        this.controlViewModels = new ArrayList();
        SceneKey sceneKey = Scenes.Communal;
        Flow flowIsFinishedIn = keyguardTransitionInteractor.isFinishedIn(KeyguardState.GONE);
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.TRUE;
        this.isOnGone = FlowKt.stateIn(flowIsFinishedIn, coroutineScope, startedEagerly, bool);
        this.isGoingToDozing = FlowKt.stateIn(keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(Edge.Companion, null, KeyguardState.DOZING, 1), null), coroutineScope, startedEagerly, bool);
        dumpManager.registerNormalDumpable("MediaCarouselController", this);
        ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.media_carousel, (ViewGroup) new UniqueObjectHostView(context), false);
        viewGroup2.setLayoutDirection(3);
        this.mediaFrame = viewGroup2;
        MediaScrollView mediaScrollView = (MediaScrollView) viewGroup2.requireViewById(R.id.media_carousel_scroller);
        this.mediaCarousel = mediaScrollView;
        PageIndicator pageIndicator = (PageIndicator) viewGroup2.requireViewById(R.id.media_page_indicator);
        this.pageIndicator = pageIndicator;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = new MediaCarouselScrollHandler(mediaScrollView, pageIndicator, delayableExecutor, new AnonymousClass1(this), new AnonymousClass2(this), new AnonymousClass3(this), new AnonymousClass4(this), falsingManager, mediaUiEventLogger);
        this.mediaCarouselScrollHandler = mediaCarouselScrollHandler;
        this.carouselLocale = context.getResources().getConfiguration().getLocales().get(0);
        ?? r6 = context.getResources().getConfiguration().getLayoutDirection() != 1 ? 0 : 1;
        if (r6 != this.isRtl) {
            this.isRtl = r6;
            ViewGroup viewGroup3 = viewGroup2;
            viewGroup3.setLayoutDirection(r6);
            MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
            if (mediaScrollView2.isLayoutRtl()) {
                ViewGroup viewGroup4 = mediaScrollView2.contentContainer;
                width = (viewGroup4 == null ? null : viewGroup4).getWidth() - mediaScrollView2.getWidth();
            } else {
                width = 0;
            }
            mediaScrollView2.setScrollX(width);
            viewGroup = viewGroup3;
        } else {
            viewGroup = viewGroup2;
        }
        inflateSettingsButton();
        this.mediaContent = (ViewGroup) mediaScrollView.requireViewById(R.id.media_carousel);
        ((ConfigurationControllerImpl) configurationController).addCallback(configurationListener);
        viewGroup.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.5
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                Companion companion = MediaCarouselController.Companion;
                mediaCarouselController.updatePageIndicatorLocation();
            }
        });
        mediaHostStatesManager.callbacks.add(new MediaHostStatesManager.Callback() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.6
            @Override // com.android.systemui.media.controls.ui.controller.MediaHostStatesManager.Callback
            public final void onHostStateChanged(int i, MediaHostState mediaHostState) {
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                Function0 function0 = mediaCarouselController.updateUserVisibility;
                if (function0 == null) {
                    function0 = null;
                }
                ((MediaHierarchyManager.AnonymousClass5) function0).invoke();
                int i2 = mediaCarouselController.desiredLocation;
                if (i == i2) {
                    mediaCarouselController.onDesiredLocationChanged(i2, mediaHostState, false, 200L, 0L);
                }
            }
        });
        keyguardUpdateMonitor.registerCallback(r8);
        RepeatWhenAttachedKt.repeatWhenAttached(mediaScrollView, EmptyCoroutineContext.INSTANCE, new AnonymousClass7(null));
        listenForLockscreenSettingChanges$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
        executor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.8
            @Override // java.lang.Runnable
            public final void run() {
                MediaCarouselController.this.globalSettings.registerContentObserverSync(Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE), MediaCarouselController.this.animationScaleObserver);
            }
        });
    }

    public static final void access$updateCarouselDimensions(MediaCarouselController mediaCarouselController) {
        mediaCarouselController.getClass();
        MediaPlayerData.INSTANCE.getClass();
        Iterator it = MediaPlayerData.mediaPlayers.values().iterator();
        int iMax = 0;
        int iMax2 = 0;
        while (it.hasNext()) {
            MediaViewController mediaViewController = ((MediaControlPanel) it.next()).mMediaViewController;
            int i = mediaViewController.currentWidth;
            TransitionLayout transitionLayout = mediaViewController.transitionLayout;
            float translationY = 0.0f;
            iMax = Math.max(iMax, i + ((int) (transitionLayout != null ? transitionLayout.getTranslationX() : 0.0f)));
            int i2 = mediaViewController.currentHeight;
            TransitionLayout transitionLayout2 = mediaViewController.transitionLayout;
            if (transitionLayout2 != null) {
                translationY = transitionLayout2.getTranslationY();
            }
            iMax2 = Math.max(iMax2, i2 + ((int) translationY));
        }
        if (iMax == mediaCarouselController.currentCarouselWidth && iMax2 == mediaCarouselController.currentCarouselHeight) {
            return;
        }
        mediaCarouselController.currentCarouselWidth = iMax;
        mediaCarouselController.currentCarouselHeight = iMax2;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController.mediaCarouselScrollHandler;
        int i3 = mediaCarouselScrollHandler.carouselHeight;
        if (iMax2 != i3 || iMax != i3) {
            mediaCarouselScrollHandler.carouselWidth = iMax;
            mediaCarouselScrollHandler.carouselHeight = iMax2;
            mediaCarouselScrollHandler.scrollView.invalidateOutline();
        }
        mediaCarouselController.updatePageIndicatorLocation();
        mediaCarouselController.updatePageIndicatorAlpha();
    }

    public static void closeGuts(boolean z) {
        MediaPlayerData.INSTANCE.getClass();
        Iterator it = MediaPlayerData.mediaPlayers.values().iterator();
        while (it.hasNext()) {
            ((MediaControlPanel) it.next()).closeGuts(z);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("keysNeedRemoval: " + this.keysNeedRemoval);
        MediaPlayerData.INSTANCE.getClass();
        printWriter.println("dataKeys: " + ((LinkedHashMap) MediaPlayerData.mediaData).keySet());
        printWriter.println("orderedPlayerSortKeys: " + MediaPlayerData.mediaPlayers.keySet());
        printWriter.println("visiblePlayerSortKeys: " + MediaPlayerData.visibleMediaPlayers.values());
        printWriter.println("controlViewModels: " + this.controlViewModels);
        printWriter.println("current size: " + this.currentCarouselWidth + " x " + this.currentCarouselHeight);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("location: ", this.desiredLocation, printWriter);
        MediaHostState mediaHostState = this.desiredHostState;
        Float fValueOf = mediaHostState != null ? Float.valueOf(mediaHostState.getExpansion()) : null;
        MediaHostState mediaHostState2 = this.desiredHostState;
        Boolean boolValueOf = mediaHostState2 != null ? Boolean.valueOf(mediaHostState2.getShowsOnlyActiveMedia()) : null;
        MediaHostState mediaHostState3 = this.desiredHostState;
        printWriter.println("state: " + fValueOf + ", only active " + boolValueOf + ", visible " + (mediaHostState3 != null ? Boolean.valueOf(mediaHostState3.getVisible()) : null));
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "isSwipedAway: ", MediaPlayerData.isSwipedAway);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "allowMediaPlayerOnLockScreen: ", this.allowMediaPlayerOnLockScreen);
    }

    public final void inflateSettingsButton() {
        ImageView imageView = (ImageView) LayoutInflater.from(this.context).inflate(R.layout.media_carousel_settings_button, this.mediaFrame, false);
        ImageView imageView2 = this.settingsButton;
        if (imageView2 != null) {
            ViewGroup viewGroup = this.mediaFrame;
            if (imageView2 == null) {
                imageView2 = null;
            }
            viewGroup.removeView(imageView2);
        }
        this.settingsButton = imageView;
        this.mediaFrame.addView(imageView != null ? imageView : null);
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        mediaCarouselScrollHandler.settingsButton = imageView;
        if (imageView == null) {
            imageView = null;
        }
        Resources resources = imageView.getResources();
        View view = mediaCarouselScrollHandler.settingsButton;
        if (view == null) {
            view = null;
        }
        mediaCarouselScrollHandler.cornerRadius = resources.getDimensionPixelSize(Utils.getThemeAttr(android.R.attr.dialogCornerRadius, view.getContext()));
        mediaCarouselScrollHandler.updateSettingsPresentation();
        mediaCarouselScrollHandler.scrollView.invalidateOutline();
        ImageView imageView3 = this.settingsButton;
        (imageView3 != null ? imageView3 : null).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.inflateSettingsButton.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MediaCarouselController.this.logger.logger.log(MediaUiEvent.OPEN_SETTINGS_CAROUSEL);
                MediaCarouselController.this.activityStarter.startActivity(MediaCarouselControllerKt.settingsIntent, true);
            }
        });
    }

    public final Job listenForAnyStateToDozingTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new MediaCarouselController$listenForAnyStateToDozingTransition$1(this, null), 7);
    }

    public final Job listenForAnyStateToGoneKeyguardTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new MediaCarouselController$listenForAnyStateToGoneKeyguardTransition$1(this, null), 7);
    }

    public final Job listenForAnyStateToLockscreenTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new MediaCarouselController$listenForAnyStateToLockscreenTransition$1(this, null), 7);
    }

    public final Job listenForLockscreenSettingChanges$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new MediaCarouselController$listenForLockscreenSettingChanges$1(this, null), 7);
    }

    public final Unit onDesiredLocationChanged(final int i, MediaHostState mediaHostState, boolean z, long j, long j2) {
        Unit unit;
        boolean z2;
        boolean z3;
        TransitionViewState transitionViewStateObtainViewState;
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("MediaCarouselController#onDesiredLocationChanged");
        }
        if (mediaHostState != null) {
            try {
                if (this.desiredLocation != i) {
                    this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$onDesiredLocationChanged$1$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaUiEvent mediaUiEvent;
                            MediaUiEventLogger mediaUiEventLogger = this.this$0.logger;
                            int i2 = i;
                            mediaUiEventLogger.getClass();
                            if (i2 == 0) {
                                mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_QS;
                            } else if (i2 == 1) {
                                mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_QQS;
                            } else if (i2 == 2) {
                                mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_LOCKSCREEN;
                            } else if (i2 == 3) {
                                mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_DREAM;
                            } else if (i2 == 4) {
                                mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_COMMUNAL;
                            } else {
                                if (i2 != 5) {
                                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Unknown media carousel location "));
                                }
                                mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_STATUS_BAR_POPUP;
                            }
                            mediaUiEventLogger.logger.log(mediaUiEvent);
                        }
                    });
                }
                int i2 = this.desiredLocation;
                this.desiredLocation = i;
                this.desiredHostState = mediaHostState;
                boolean z4 = true;
                boolean z5 = mediaHostState.getExpansion() > 0.0f;
                boolean z6 = this.currentlyExpanded;
                MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
                if (z6 != z5) {
                    this.currentlyExpanded = z5;
                    updateSeekbarListening(mediaCarouselScrollHandler.visibleToUser);
                }
                if (i == 4) {
                    ImageView imageView = this.settingsButton;
                    if (imageView == null) {
                        imageView = null;
                    }
                    imageView.setColorFilter(this.context.getColor(android.R.color.resolver_profile_tab_text));
                } else {
                    ImageView imageView2 = this.settingsButton;
                    if (imageView2 == null) {
                        imageView2 = null;
                    }
                    imageView2.setColorFilter(this.context.getColor(R.color.notification_gear_color));
                }
                boolean z7 = (this.currentlyExpanded || this.mediaManager.hasActiveMediaOrRecommendation() || !mediaHostState.getShowsOnlyActiveMedia()) ? false : true;
                MediaPlayerData.INSTANCE.getClass();
                for (MediaControlPanel mediaControlPanel : MediaPlayerData.mediaPlayers.values()) {
                    if (z) {
                        MediaViewController mediaViewController = mediaControlPanel.mMediaViewController;
                        mediaViewController.animateNextStateChange = z4;
                        mediaViewController.animationDuration = j;
                        z2 = z4;
                        z3 = z7;
                        mediaViewController.animationDelay = j2;
                    } else {
                        z2 = z4;
                        z3 = z7;
                    }
                    if (z3 && mediaControlPanel.mMediaViewController.isGutsVisible) {
                        mediaControlPanel.closeGuts(!z);
                    }
                    MediaViewController mediaViewController2 = mediaControlPanel.mMediaViewController;
                    mediaViewController2.isFontUpdateAllowed = (mediaViewController2.isFontUpdateAllowed || 4 == i || 4 == i2) ? z2 : false;
                    MediaHostState mediaHostState2 = (MediaHostState) ((LinkedHashMap) mediaViewController2.mediaHostStatesManager.mediaHostStates).get(Integer.valueOf(i));
                    if (mediaHostState2 == null) {
                        transitionViewStateObtainViewState = null;
                    } else {
                        transitionViewStateObtainViewState = mediaViewController2.obtainViewState(mediaHostState2, false);
                        if (transitionViewStateObtainViewState != null) {
                            TransitionViewState transitionViewState = mediaViewController2.tmpState;
                            mediaViewController2.updateViewStateSize(transitionViewStateObtainViewState, i, transitionViewState);
                            transitionViewStateObtainViewState = transitionViewState;
                        }
                    }
                    if (transitionViewStateObtainViewState != null) {
                        mediaViewController2.layoutController.setMeasureState(transitionViewStateObtainViewState);
                    }
                    z4 = z2;
                    z7 = z3;
                }
                mediaCarouselScrollHandler.showsSettingsButton = !mediaHostState.getShowsOnlyActiveMedia();
                mediaCarouselScrollHandler.falsingProtectionNeeded = mediaHostState.getFalsingProtectionNeeded();
                boolean visible = mediaHostState.getVisible();
                if (visible != this.playersVisible) {
                    this.playersVisible = visible;
                    if (visible) {
                        mediaCarouselScrollHandler.resetTranslation(false);
                    }
                }
                updateCarouselSize();
                unit = Unit.INSTANCE;
            } catch (Throwable th) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        } else {
            unit = null;
        }
        if (zIsEnabled) {
            TraceUtilsKt.endSlice();
        }
        return unit;
    }

    public final void onSwipeToDismiss() {
        MediaPlayerData.INSTANCE.getClass();
        MediaPlayerData.isSwipedAway = true;
        this.logger.logger.log(MediaUiEvent.DISMISS_SWIPE);
        this.mediaManager.onSwipeToDismiss();
    }

    public final MediaControlPanel removePlayer(String str, boolean z) throws Resources.NotFoundException {
        MediaPlayerData.INSTANCE.getClass();
        MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) MediaPlayerData.mediaData.remove(str);
        MediaControlPanel mediaControlPanel = mediaSortKey != null ? (MediaControlPanel) MediaPlayerData.mediaPlayers.remove(mediaSortKey) : null;
        if (mediaControlPanel == null) {
            return null;
        }
        MediaViewHolder mediaViewHolder = mediaControlPanel.mMediaViewHolder;
        TransitionLayout transitionLayout = mediaViewHolder != null ? mediaViewHolder.player : null;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        int iIndexOfChild = mediaCarouselScrollHandler.mediaContent.indexOfChild(transitionLayout);
        int i = mediaCarouselScrollHandler.visibleMediaIndex;
        boolean z2 = true;
        boolean z3 = iIndexOfChild <= i;
        if (z3) {
            mediaCarouselScrollHandler.visibleMediaIndex = Math.max(0, i - 1);
        }
        MediaScrollView mediaScrollView = mediaCarouselScrollHandler.scrollView;
        if (!mediaScrollView.isLayoutRtl() || mediaCarouselScrollHandler.visibleMediaIndex == 0) {
            z2 = z3;
        } else if (z3) {
            z2 = false;
        }
        if (z2) {
            mediaScrollView.setScrollX(Math.max(mediaScrollView.getScrollX() - mediaCarouselScrollHandler.playerWidthPlusPadding, 0));
        }
        ViewGroup viewGroup = this.mediaContent;
        MediaViewHolder mediaViewHolder2 = mediaControlPanel.mMediaViewHolder;
        viewGroup.removeView(mediaViewHolder2 != null ? mediaViewHolder2.player : null);
        mediaControlPanel.onDestroy();
        mediaCarouselScrollHandler.onPlayersChanged();
        this.mediaControlChipInteractor.updateMediaControlChipModelLegacy(MediaPlayerData.getFirstActiveMediaData());
        updatePageIndicator$2();
        return mediaControlPanel;
    }

    public final void reorderAllPlayers() throws Resources.NotFoundException {
        this.mediaContent.removeAllViews();
        MediaPlayerData.INSTANCE.getClass();
        Iterator it = MediaPlayerData.mediaPlayers.values().iterator();
        while (it.hasNext()) {
            MediaViewHolder mediaViewHolder = ((MediaControlPanel) it.next()).mMediaViewHolder;
            if (mediaViewHolder != null) {
                this.mediaContent.addView(mediaViewHolder.player);
            }
        }
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        mediaCarouselScrollHandler.onPlayersChanged();
        MediaPlayerData.INSTANCE.getClass();
        this.mediaControlChipInteractor.updateMediaControlChipModelLegacy(MediaPlayerData.getFirstActiveMediaData());
        MediaPlayerData.visibleMediaPlayers.clear();
        for (MediaPlayerData.MediaSortKey mediaSortKey : MediaPlayerData.mediaPlayers.keySet()) {
            MediaPlayerData.visibleMediaPlayers.put(mediaSortKey.key, mediaSortKey);
        }
        if (this.isRtl && this.mediaContent.getChildCount() > 0) {
            MediaCarouselScrollHandler.scrollToPlayer$default(mediaCarouselScrollHandler, 0);
        }
        MediaPlayerData.INSTANCE.getClass();
        TreeMap treeMap = MediaPlayerData.mediaPlayers;
        if (treeMap.values().size() != this.mediaContent.getChildCount()) {
            Log.e("MediaCarouselController", MutableVectorKt$$ExternalSyntheticOutline0.m(treeMap.values().size(), this.mediaContent.getChildCount(), "Size of players list and number of views in carousel are out of sync. Players size is ", ". View count is ", "."));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setCurrentState(int i, int i2, float f, boolean z) {
        if (i == this.currentStartLocation && i2 == this.currentEndLocation && f == this.currentTransitionProgress && !z) {
            return;
        }
        this.currentStartLocation = i;
        this.currentEndLocation = i2;
        this.currentTransitionProgress = f;
        MediaPlayerData.INSTANCE.getClass();
        Iterator it = MediaPlayerData.mediaPlayers.values().iterator();
        while (it.hasNext()) {
            MediaViewController mediaViewController = ((MediaControlPanel) it.next()).mMediaViewController;
            int i3 = this.currentStartLocation;
            int i4 = this.currentEndLocation;
            float f2 = this.currentTransitionProgress;
            int i5 = MediaViewController.$r8$clinit;
            boolean z2 = z;
            mediaViewController.setCurrentState(i3, i4, f2, z2, false);
            z = z2;
        }
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.mediaHostStatesManager.mediaHostStates;
        MediaHostState mediaHostState = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentEndLocation));
        boolean showsOnlyActiveMedia = mediaHostState != null ? mediaHostState.getShowsOnlyActiveMedia() : true;
        MediaHostState mediaHostState2 = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentStartLocation));
        boolean showsOnlyActiveMedia2 = mediaHostState2 != null ? mediaHostState2.getShowsOnlyActiveMedia() : showsOnlyActiveMedia;
        MediaHostState mediaHostState3 = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentStartLocation));
        boolean disableScrolling = mediaHostState3 != null ? mediaHostState3.getDisableScrolling() : false;
        MediaHostState mediaHostState4 = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentEndLocation));
        boolean disableScrolling2 = mediaHostState4 != null ? mediaHostState4.getDisableScrolling() : false;
        if (this.currentlyShowingOnlyActive == showsOnlyActiveMedia && this.currentlyDisableScrolling == disableScrolling2) {
            float f3 = this.currentTransitionProgress;
            if (f3 != 1.0f && f3 != 0.0f && (showsOnlyActiveMedia2 != showsOnlyActiveMedia || disableScrolling != disableScrolling2)) {
            }
        } else {
            this.currentlyShowingOnlyActive = showsOnlyActiveMedia;
            this.currentlyDisableScrolling = disableScrolling2;
            MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
            mediaCarouselScrollHandler.resetTranslation(true);
            mediaCarouselScrollHandler.scrollingDisabled = this.currentlyDisableScrolling;
        }
        updatePageIndicatorAlpha();
    }

    public final void updateCarouselSize() {
        MeasurementInput measurementInput;
        MeasurementInput measurementInput2;
        MeasurementInput measurementInput3;
        MeasurementInput measurementInput4;
        MediaHostState mediaHostState = this.desiredHostState;
        int width = (mediaHostState == null || (measurementInput4 = mediaHostState.getMeasurementInput()) == null) ? 0 : measurementInput4.getWidth();
        MediaHostState mediaHostState2 = this.desiredHostState;
        int height = (mediaHostState2 == null || (measurementInput3 = mediaHostState2.getMeasurementInput()) == null) ? 0 : measurementInput3.getHeight();
        if ((width == this.carouselMeasureWidth || width == 0) && (height == this.carouselMeasureHeight || height == 0)) {
            return;
        }
        this.carouselMeasureWidth = width;
        this.carouselMeasureHeight = height;
        int iM = StrongAuthPopup$$ExternalSyntheticOutline0.m(this.context, R.dimen.qs_media_padding, width);
        MediaHostState mediaHostState3 = this.desiredHostState;
        int widthMeasureSpec = (mediaHostState3 == null || (measurementInput2 = mediaHostState3.getMeasurementInput()) == null) ? 0 : measurementInput2.getWidthMeasureSpec();
        MediaHostState mediaHostState4 = this.desiredHostState;
        int heightMeasureSpec = (mediaHostState4 == null || (measurementInput = mediaHostState4.getMeasurementInput()) == null) ? 0 : measurementInput.getHeightMeasureSpec();
        MediaScrollView mediaScrollView = this.mediaCarousel;
        mediaScrollView.measure(widthMeasureSpec, heightMeasureSpec);
        mediaScrollView.layout(0, 0, width, mediaScrollView.getMeasuredHeight());
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        mediaCarouselScrollHandler.playerWidthPlusPadding = iM;
        int i = mediaCarouselScrollHandler.visibleMediaIndex * iM;
        int i2 = mediaCarouselScrollHandler.scrollIntoCurrentMedia;
        int width2 = i2 > iM ? (iM - (i2 - iM)) + i : i + i2;
        MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
        if (mediaScrollView2.isLayoutRtl()) {
            ViewGroup viewGroup = mediaScrollView2.contentContainer;
            if (viewGroup == null) {
                viewGroup = null;
            }
            width2 = (viewGroup.getWidth() - mediaScrollView2.getWidth()) - width2;
        }
        mediaScrollView2.setScrollX(width2);
    }

    public final void updatePageIndicator$2() {
        int childCount = this.mediaContent.getChildCount();
        PageIndicator pageIndicator = this.pageIndicator;
        pageIndicator.setNumPages(childCount);
        if (childCount == 1) {
            pageIndicator.setLocation(0.0f);
        }
        updatePageIndicatorAlpha();
    }

    public final void updatePageIndicatorAlpha() {
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.mediaHostStatesManager.mediaHostStates;
        MediaHostState mediaHostState = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentEndLocation));
        boolean visible = mediaHostState != null ? mediaHostState.getVisible() : false;
        MediaHostState mediaHostState2 = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentStartLocation));
        boolean visible2 = mediaHostState2 != null ? mediaHostState2.getVisible() : false;
        float fLerp = 1.0f;
        float f = visible2 ? 1.0f : 0.0f;
        MediaHostState mediaHostState3 = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentEndLocation));
        float squishFraction = mediaHostState3 != null ? mediaHostState3.getSquishFraction() : 1.0f;
        float f2 = visible ? 1.0f : 0.0f;
        PageIndicator pageIndicator = this.pageIndicator;
        float translationY = (pageIndicator.getTranslationY() + pageIndicator.getHeight()) / this.mediaCarousel.getMeasuredHeight();
        Companion.getClass();
        float interpolation = TRANSFORM_BEZIER.getInterpolation(MathUtils.constrain((squishFraction - translationY) / (1.0f - translationY), 0.0f, 1.0f)) * f2;
        if (!visible || !visible2) {
            float f3 = this.currentTransitionProgress;
            if (!visible) {
                f3 = 1.0f - f3;
            }
            fLerp = MathUtils.lerp(f, interpolation, MathUtils.constrain(MathUtils.map(0.95f, 1.0f, 0.0f, 1.0f, f3), 0.0f, 1.0f));
        }
        pageIndicator.setAlpha(fLerp);
    }

    public final void updatePageIndicatorLocation() {
        int width;
        int width2;
        boolean z = this.isRtl;
        PageIndicator pageIndicator = this.pageIndicator;
        if (z) {
            width = pageIndicator.getWidth();
            width2 = this.currentCarouselWidth;
        } else {
            width = this.currentCarouselWidth;
            width2 = pageIndicator.getWidth();
        }
        pageIndicator.setTranslationX(((width - width2) / 2.0f) + this.mediaCarouselScrollHandler.contentTranslation);
        pageIndicator.setTranslationY((this.mediaCarousel.getMeasuredHeight() - pageIndicator.getHeight()) - ((ViewGroup.MarginLayoutParams) pageIndicator.getLayoutParams()).bottomMargin);
    }

    public final void updatePlayers(final boolean z) throws Resources.NotFoundException {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(this.context.getColor(R.color.media_paging_indicator));
        PageIndicator pageIndicator = this.pageIndicator;
        if (!colorStateListValueOf.equals(pageIndicator.mTint)) {
            pageIndicator.mTint = colorStateListValueOf;
            int childCount = pageIndicator.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = pageIndicator.getChildAt(i);
                if (childAt instanceof ImageView) {
                    ((ImageView) childAt).setImageTintList(pageIndicator.mTint);
                }
            }
        }
        MediaPlayerData.INSTANCE.getClass();
        Collection collectionValues = MediaPlayerData.visibleMediaPlayers.values();
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        final MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(collectionValues, mediaCarouselScrollHandler.visibleMediaIndex);
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$updatePlayers$onUiExecutionEnd$1
            @Override // java.lang.Runnable
            public final void run() throws Resources.NotFoundException {
                if (z) {
                    MediaCarouselController mediaCarouselController = this;
                    MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                    mediaCarouselController.reorderAllPlayers();
                }
            }
        };
        Set<Map.Entry> setEntrySet = ((LinkedHashMap) MediaPlayerData.mediaData).entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setEntrySet, 10));
        for (Map.Entry entry : setEntrySet) {
            arrayList.add(new Pair(entry.getKey(), ((MediaPlayerData.MediaSortKey) entry.getValue()).data));
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            int i3 = i2 + 1;
            Pair pair = (Pair) arrayList.get(i2);
            final String str = (String) pair.component1();
            final MediaData mediaData = (MediaData) pair.component2();
            if (z) {
                removePlayer(str, false);
            }
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("MediaCarouselController#addOrUpdatePlayer");
            }
            try {
                MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
                mediaPlayerData.getClass();
                MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) ((LinkedHashMap) MediaPlayerData.mediaData).get(str);
                MediaControlPanel mediaControlPanel = mediaSortKey2 != null ? (MediaControlPanel) MediaPlayerData.mediaPlayers.get(mediaSortKey2) : null;
                final MediaPlayerData.MediaSortKey mediaSortKey3 = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(MediaPlayerData.visibleMediaPlayers.values(), mediaCarouselScrollHandler.visibleMediaIndex);
                if (mediaControlPanel == null) {
                    this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$addOrUpdatePlayer$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaCarouselController mediaCarouselController = this.this$0;
                            MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                            mediaCarouselController.getClass();
                            MediaViewHolder.Companion companion2 = MediaViewHolder.Companion;
                            LayoutInflater layoutInflaterFrom = LayoutInflater.from(mediaCarouselController.context);
                            ViewGroup viewGroup = mediaCarouselController.mediaContent;
                            companion2.getClass();
                            View viewInflate = layoutInflaterFrom.inflate(R.layout.media_session_view, viewGroup, false);
                            viewInflate.setLayerType(2, null);
                            viewInflate.setLayoutDirection(3);
                            final MediaViewHolder mediaViewHolder = new MediaViewHolder(viewInflate);
                            mediaViewHolder.seekBar.setLayoutDirection(0);
                            final MediaCarouselController mediaCarouselController2 = this.this$0;
                            DelayableExecutor delayableExecutor = mediaCarouselController2.uiExecutor;
                            final String str2 = str;
                            final MediaData mediaData2 = mediaData;
                            final MediaPlayerData.MediaSortKey mediaSortKey4 = mediaSortKey3;
                            final Runnable runnable2 = runnable;
                            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$addOrUpdatePlayer$1$1.1
                                /* JADX WARN: Removed duplicated region for block: B:9:0x0086 A[DONT_GENERATE] */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void run() throws Resources.NotFoundException {
                                    TransitionLayout transitionLayout;
                                    MediaCarouselController mediaCarouselController3 = mediaCarouselController2;
                                    String str3 = str2;
                                    MediaData mediaData3 = mediaData2;
                                    MediaViewHolder mediaViewHolder2 = mediaViewHolder;
                                    MediaCarouselController.Companion companion3 = MediaCarouselController.Companion;
                                    final MediaControlPanel mediaControlPanel2 = (MediaControlPanel) mediaCarouselController3.mediaControlPanelFactory.get();
                                    mediaControlPanel2.mMediaViewHolder = mediaViewHolder2;
                                    TransitionLayout transitionLayout2 = mediaViewHolder2.player;
                                    SeekBarObserver seekBarObserver = new SeekBarObserver(mediaViewHolder2);
                                    mediaControlPanel2.mSeekBarObserver = seekBarObserver;
                                    SeekBarViewModel seekBarViewModel = mediaControlPanel2.mSeekBarViewModel;
                                    seekBarViewModel._progress.observeForever(seekBarObserver);
                                    SeekBar seekBar = mediaViewHolder2.seekBar;
                                    seekBar.setOnSeekBarChangeListener(new SeekBarViewModel.SeekBarChangeListener(seekBarViewModel, seekBarViewModel.falsingManager));
                                    seekBar.setOnTouchListener(new SeekBarViewModel.SeekBarTouchListener(seekBarViewModel, seekBar));
                                    seekBarViewModel.scrubbingChangeListener = mediaControlPanel2.mScrubbingChangeListener;
                                    seekBarViewModel.enabledChangeListener = mediaControlPanel2.mEnabledChangeListener;
                                    seekBarViewModel.contentDescriptionListener = mediaControlPanel2.mContentDescriptionListener;
                                    MediaViewController mediaViewController = mediaControlPanel2.mMediaViewController;
                                    mediaViewController.getClass();
                                    boolean zIsEnabled2 = Trace.isEnabled();
                                    if (zIsEnabled2) {
                                        TraceUtilsKt.beginSlice("MediaViewController#attach");
                                    }
                                    try {
                                        mediaViewController.collapsedLayout.load(mediaViewController.context, R.xml.media_session_collapsed);
                                        mediaViewController.expandedLayout.load(mediaViewController.context, R.xml.media_session_expanded);
                                        mediaViewController.refreshState();
                                        mediaViewController.logger.logMediaLocation(mediaViewController.currentStartLocation, mediaViewController.currentEndLocation, "attach");
                                        mediaViewController.transitionLayout = transitionLayout2;
                                        mediaViewController.layoutController.attach(transitionLayout2);
                                        int i4 = mediaViewController.currentEndLocation;
                                        if (i4 != -1) {
                                            mediaViewController.setCurrentState(mediaViewController.currentStartLocation, i4, mediaViewController.currentTransitionProgress, true, false);
                                            Unit unit = Unit.INSTANCE;
                                            if (zIsEnabled2) {
                                            }
                                        }
                                        mediaViewHolder2.player.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda4
                                            @Override // android.view.View.OnLongClickListener
                                            public final boolean onLongClick(View view) {
                                                MediaControlPanel mediaControlPanel3 = mediaControlPanel2;
                                                if (mediaControlPanel3.mFalsingManager.isFalseLongTap(1)) {
                                                    return true;
                                                }
                                                MediaViewController mediaViewController2 = mediaControlPanel3.mMediaViewController;
                                                if (mediaViewController2.isGutsVisible) {
                                                    mediaControlPanel3.closeGuts(false);
                                                    return true;
                                                }
                                                MediaViewHolder mediaViewHolder3 = mediaControlPanel3.mMediaViewHolder;
                                                if (mediaViewHolder3 != null) {
                                                    mediaViewHolder3.marquee(MediaViewController.GUTS_ANIMATION_DURATION, true);
                                                }
                                                if (!mediaViewController2.isGutsVisible) {
                                                    mediaViewController2.isGutsVisible = true;
                                                    mediaViewController2.animateNextStateChange = true;
                                                    mediaViewController2.animationDuration = MediaViewController.GUTS_ANIMATION_DURATION;
                                                    mediaViewController2.animationDelay = 0L;
                                                    mediaViewController2.setCurrentState(mediaViewController2.currentStartLocation, mediaViewController2.currentEndLocation, mediaViewController2.currentTransitionProgress, false, true);
                                                }
                                                if (mediaControlPanel3.mMediaViewHolder != null) {
                                                    mediaControlPanel3.bindPlayerContentDescription(mediaControlPanel3.mMediaData);
                                                }
                                                mediaControlPanel3.mLogger.logger.logWithInstanceId(MediaUiEvent.OPEN_LONG_PRESS, mediaControlPanel3.mUid, mediaControlPanel3.mPackageName, mediaControlPanel3.mInstanceId);
                                                return true;
                                            }
                                        });
                                        mediaControlPanel2.mMediaViewHolder.albumView.setLayerType(2, null);
                                        MediaViewHolder mediaViewHolder3 = mediaControlPanel2.mMediaViewHolder;
                                        TextView textView = mediaViewHolder3.titleText;
                                        TextView textView2 = mediaViewHolder3.artistText;
                                        CachingIconView cachingIconView = mediaViewHolder3.explicitIndicator;
                                        AnimatorSet animatorSetLoadAnimator = mediaControlPanel2.loadAnimator(R.anim.media_metadata_enter, Interpolators.EMPHASIZED_DECELERATE, textView, textView2, cachingIconView);
                                        AnimatorSet animatorSetLoadAnimator2 = mediaControlPanel2.loadAnimator(R.anim.media_metadata_exit, Interpolators.EMPHASIZED_ACCELERATE, textView, textView2, cachingIconView);
                                        mediaControlPanel2.mMultiRippleController = new MultiRippleController(mediaViewHolder2.multiRippleView);
                                        BlendMode blendMode = BlendMode.SCREEN;
                                        TurbulenceNoiseView turbulenceNoiseView = mediaViewHolder2.turbulenceNoiseView;
                                        turbulenceNoiseView.getClass();
                                        turbulenceNoiseView.paint.setBlendMode(blendMode);
                                        LoadingEffectView loadingEffectView = mediaViewHolder2.loadingEffectView;
                                        loadingEffectView.blendMode = blendMode;
                                        loadingEffectView.setVisibility(4);
                                        mediaControlPanel2.mColorSchemeTransition = new ColorSchemeTransition(mediaControlPanel2.mContext, mediaControlPanel2.mMediaViewHolder, mediaControlPanel2.mMultiRippleController, new TurbulenceNoiseController(turbulenceNoiseView));
                                        mediaControlPanel2.mMetadataAnimationHandler = new MetadataAnimationHandler(animatorSetLoadAnimator2, animatorSetLoadAnimator);
                                        mediaViewController.sizeChangedListener = new MediaCarouselController$setupNewPlayer$1(mediaCarouselController3);
                                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                                        MediaViewHolder mediaViewHolder4 = mediaControlPanel2.mMediaViewHolder;
                                        if (mediaViewHolder4 != null && (transitionLayout = mediaViewHolder4.player) != null) {
                                            transitionLayout.setLayoutParams(layoutParams);
                                        }
                                        mediaControlPanel2.bindPlayer(mediaData3, str3);
                                        seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$listening$1(seekBarViewModel, mediaCarouselController3.mediaCarouselScrollHandler.visibleToUser && mediaCarouselController3.currentlyExpanded));
                                        MediaPlayerData mediaPlayerData2 = MediaPlayerData.INSTANCE;
                                        mediaPlayerData2.addMediaPlayer(str3, mediaData3, mediaControlPanel2, mediaCarouselController3.systemClock, mediaCarouselController3.debugLogger);
                                        int i5 = mediaCarouselController3.currentStartLocation;
                                        int i6 = mediaCarouselController3.currentEndLocation;
                                        float f = mediaCarouselController3.currentTransitionProgress;
                                        int i7 = MediaViewController.$r8$clinit;
                                        mediaViewController.setCurrentState(i5, i6, f, true, false);
                                        if (mediaData3.active) {
                                            mediaCarouselController3.reorderAllPlayers();
                                        }
                                        mediaCarouselController2.updatePageIndicator$2();
                                        mediaCarouselController2.mediaCarouselScrollHandler.onPlayersChanged();
                                        MediaControlChipInteractor mediaControlChipInteractor = mediaCarouselController2.mediaControlChipInteractor;
                                        mediaPlayerData2.getClass();
                                        mediaControlChipInteractor.updateMediaControlChipModelLegacy(MediaPlayerData.getFirstActiveMediaData());
                                        UniqueObjectHostViewKt.setRequiresRemeasuring(mediaCarouselController2.mediaFrame, true);
                                        Runnable runnable3 = runnable2;
                                        if (runnable3 != null) {
                                            runnable3.run();
                                        }
                                    } finally {
                                        if (zIsEnabled2) {
                                            TraceUtilsKt.endSlice();
                                        }
                                    }
                                }
                            });
                        }
                    });
                } else {
                    mediaControlPanel.bindPlayer(mediaData, str);
                    mediaPlayerData.addMediaPlayer(str, mediaData, mediaControlPanel, this.systemClock, this.debugLogger);
                    if (this.visualStabilityProvider.isReorderingAllowed) {
                        reorderAllPlayers();
                    }
                    updatePageIndicator$2();
                    mediaCarouselScrollHandler.onPlayersChanged();
                    this.mediaControlChipInteractor.updateMediaControlChipModelLegacy(MediaPlayerData.getFirstActiveMediaData());
                    UniqueObjectHostViewKt.setRequiresRemeasuring(this.mediaFrame, true);
                    runnable.run();
                }
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                i2 = i3;
            } catch (Throwable th) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
    }

    public final void updateSeekbarListening(boolean z) {
        MediaPlayerData.INSTANCE.getClass();
        for (MediaControlPanel mediaControlPanel : MediaPlayerData.mediaPlayers.values()) {
            boolean z2 = z && this.currentlyExpanded;
            SeekBarViewModel seekBarViewModel = mediaControlPanel.mSeekBarViewModel;
            seekBarViewModel.getClass();
            seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$listening$1(seekBarViewModel, z2));
        }
    }

    public static /* synthetic */ void getCurrentEndLocation$annotations() {
    }

    public static /* synthetic */ void getCurrentlyExpanded$annotations() {
    }

    public static /* synthetic */ void getMediaCarousel$annotations() {
    }

    public static /* synthetic */ void getPageIndicator$annotations() {
    }

    public static /* synthetic */ void getSettingsButton$annotations() {
    }
}
