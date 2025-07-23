package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
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
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.TransitionLayout;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass3(Object obj) {
            super(1, obj, MediaCarouselController.class, "updateSeekbarListening", "updateSeekbarListening(Z)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            MediaCarouselController mediaCarouselController = (MediaCarouselController) this.receiver;
            Companion companion = MediaCarouselController.Companion;
            mediaCarouselController.updateSeekbarListening(booleanValue);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass4(Object obj) {
            super(1, obj, MediaCarouselController.class, "closeGuts", "closeGuts(Z)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            ((MediaCarouselController) this.receiver).getClass();
            MediaCarouselController.closeGuts(booleanValue);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        int i;
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
                int i2 = 0;
                ?? r1 = configuration.getLayoutDirection() != 1 ? 0 : 1;
                MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                if (r1 != mediaCarouselController.isRtl) {
                    mediaCarouselController.isRtl = r1;
                    mediaCarouselController.mediaFrame.setLayoutDirection(r1);
                    MediaScrollView mediaScrollView = mediaCarouselController.mediaCarouselScrollHandler.scrollView;
                    if (mediaScrollView.isLayoutRtl()) {
                        ViewGroup viewGroup2 = mediaScrollView.contentContainer;
                        if (viewGroup2 == null) {
                            viewGroup2 = null;
                        }
                        i2 = viewGroup2.getWidth() - mediaScrollView.getWidth();
                    }
                    mediaScrollView.setScrollX(i2);
                }
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                mediaCarouselController.updatePlayers(true);
                mediaCarouselController.inflateSettingsButton();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLocaleListChanged() {
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                if (Intrinsics.areEqual(mediaCarouselController.carouselLocale, mediaCarouselController.context.getResources().getConfiguration().getLocales().get(0))) {
                    return;
                }
                mediaCarouselController.carouselLocale = mediaCarouselController.context.getResources().getConfiguration().getLocales().get(0);
                mediaCarouselController.updatePlayers(true);
                mediaCarouselController.inflateSettingsButton();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                mediaCarouselController.updatePlayers(false);
                mediaCarouselController.inflateSettingsButton();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onUiModeChanged() {
                MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                mediaCarouselController.updatePlayers(false);
                mediaCarouselController.inflateSettingsButton();
            }
        };
        ?? r8 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i2) {
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                boolean isUserInLockdown = mediaCarouselController.keyguardUpdateMonitor.isUserInLockdown(i2);
                MediaCarouselControllerLogger mediaCarouselControllerLogger2 = mediaCarouselController.debugLogger;
                if (isUserInLockdown) {
                    mediaCarouselControllerLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    MediaCarouselControllerLogger$$ExternalSyntheticLambda0 mediaCarouselControllerLogger$$ExternalSyntheticLambda0 = new MediaCarouselControllerLogger$$ExternalSyntheticLambda0(1);
                    LogBuffer logBuffer = mediaCarouselControllerLogger2.buffer;
                    logBuffer.commit(logBuffer.obtain("MediaCarouselCtlrLog", logLevel, mediaCarouselControllerLogger$$ExternalSyntheticLambda0, null));
                    mediaCarouselController.mediaCarousel.setVisibility(8);
                    return;
                }
                if (mediaCarouselController.keyguardUpdateMonitor.mUserManager.isUserUnlocked(i2)) {
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
        Flow isFinishedIn = keyguardTransitionInteractor.isFinishedIn(KeyguardState.GONE);
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.TRUE;
        this.isOnGone = FlowKt.stateIn(isFinishedIn, coroutineScope, startedEagerly, bool);
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
                i = (viewGroup4 == null ? null : viewGroup4).getWidth() - mediaScrollView2.getWidth();
            } else {
                i = 0;
            }
            mediaScrollView2.setScrollX(i);
            viewGroup = viewGroup3;
        } else {
            viewGroup = viewGroup2;
        }
        inflateSettingsButton();
        this.mediaContent = (ViewGroup) mediaScrollView.requireViewById(R.id.media_carousel);
        ((ConfigurationControllerImpl) configurationController).addCallback(configurationListener);
        viewGroup.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.5
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                Companion companion = MediaCarouselController.Companion;
                mediaCarouselController.updatePageIndicatorLocation();
            }
        });
        mediaHostStatesManager.callbacks.add(new MediaHostStatesManager.Callback() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.6
            @Override // com.android.systemui.media.controls.ui.controller.MediaHostStatesManager.Callback
            public final void onHostStateChanged(int i2, MediaHostState mediaHostState) {
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                Function0 function0 = mediaCarouselController.updateUserVisibility;
                if (function0 == null) {
                    function0 = null;
                }
                ((MediaHierarchyManager.AnonymousClass5) function0).invoke();
                int i3 = mediaCarouselController.desiredLocation;
                if (i2 == i3) {
                    mediaCarouselController.onDesiredLocationChanged(i3, mediaHostState, false, 200L, 0L);
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
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            MediaViewController mediaViewController = ((MediaControlPanel) it.next()).mMediaViewController;
            int i3 = mediaViewController.currentWidth;
            TransitionLayout transitionLayout = mediaViewController.transitionLayout;
            float f = 0.0f;
            i = Math.max(i, i3 + ((int) (transitionLayout != null ? transitionLayout.getTranslationX() : 0.0f)));
            int i4 = mediaViewController.currentHeight;
            TransitionLayout transitionLayout2 = mediaViewController.transitionLayout;
            if (transitionLayout2 != null) {
                f = transitionLayout2.getTranslationY();
            }
            i2 = Math.max(i2, i4 + ((int) f));
        }
        if (i == mediaCarouselController.currentCarouselWidth && i2 == mediaCarouselController.currentCarouselHeight) {
            return;
        }
        mediaCarouselController.currentCarouselWidth = i;
        mediaCarouselController.currentCarouselHeight = i2;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController.mediaCarouselScrollHandler;
        int i5 = mediaCarouselScrollHandler.carouselHeight;
        if (i2 != i5 || i != i5) {
            mediaCarouselScrollHandler.carouselWidth = i;
            mediaCarouselScrollHandler.carouselHeight = i2;
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
        Float valueOf = mediaHostState != null ? Float.valueOf(mediaHostState.getExpansion()) : null;
        MediaHostState mediaHostState2 = this.desiredHostState;
        Boolean valueOf2 = mediaHostState2 != null ? Boolean.valueOf(mediaHostState2.getShowsOnlyActiveMedia()) : null;
        MediaHostState mediaHostState3 = this.desiredHostState;
        printWriter.println("state: " + valueOf + ", only active " + valueOf2 + ", visible " + (mediaHostState3 != null ? Boolean.valueOf(mediaHostState3.getVisible()) : null));
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
        (imageView3 != null ? imageView3 : null).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$inflateSettingsButton$1
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

    /* JADX WARN: Removed duplicated region for block: B:52:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00f9 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:11:0x0013, B:13:0x0017, B:14:0x0025, B:17:0x0039, B:20:0x003f, B:23:0x0049, B:26:0x004f, B:27:0x006e, B:29:0x0072, B:31:0x007a, B:34:0x0083, B:35:0x0092, B:37:0x0098, B:39:0x00a0, B:41:0x00bb, B:43:0x00c1, B:44:0x00c6, B:50:0x00d5, B:54:0x00f9, B:58:0x00eb, B:60:0x00f1, B:64:0x0103, B:66:0x011b, B:68:0x011f, B:69:0x0122, B:73:0x005c, B:76:0x0062), top: B:10:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00fe A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00eb A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:11:0x0013, B:13:0x0017, B:14:0x0025, B:17:0x0039, B:20:0x003f, B:23:0x0049, B:26:0x004f, B:27:0x006e, B:29:0x0072, B:31:0x007a, B:34:0x0083, B:35:0x0092, B:37:0x0098, B:39:0x00a0, B:41:0x00bb, B:43:0x00c1, B:44:0x00c6, B:50:0x00d5, B:54:0x00f9, B:58:0x00eb, B:60:0x00f1, B:64:0x0103, B:66:0x011b, B:68:0x011f, B:69:0x0122, B:73:0x005c, B:76:0x0062), top: B:10:0x0013 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.Unit onDesiredLocationChanged(final int r19, com.android.systemui.media.controls.ui.view.MediaHostState r20, boolean r21, long r22, long r24) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaCarouselController.onDesiredLocationChanged(int, com.android.systemui.media.controls.ui.view.MediaHostState, boolean, long, long):kotlin.Unit");
    }

    public final void onSwipeToDismiss() {
        MediaPlayerData.INSTANCE.getClass();
        MediaPlayerData.isSwipedAway = true;
        this.logger.logger.log(MediaUiEvent.DISMISS_SWIPE);
        this.mediaManager.onSwipeToDismiss();
    }

    public final MediaControlPanel removePlayer(String str, boolean z) {
        MediaPlayerData.INSTANCE.getClass();
        MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) MediaPlayerData.mediaData.remove(str);
        MediaControlPanel mediaControlPanel = mediaSortKey != null ? (MediaControlPanel) MediaPlayerData.mediaPlayers.remove(mediaSortKey) : null;
        if (mediaControlPanel == null) {
            return null;
        }
        MediaViewHolder mediaViewHolder = mediaControlPanel.mMediaViewHolder;
        TransitionLayout transitionLayout = mediaViewHolder != null ? mediaViewHolder.player : null;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        int indexOfChild = mediaCarouselScrollHandler.mediaContent.indexOfChild(transitionLayout);
        int i = mediaCarouselScrollHandler.visibleMediaIndex;
        boolean z2 = true;
        boolean z3 = indexOfChild <= i;
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

    public final void reorderAllPlayers() {
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

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b3, code lost:
    
        if (r0 == r1) goto L43;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCurrentState(int r7, int r8, float r9, boolean r10) {
        /*
            r6 = this;
            int r0 = r6.currentStartLocation
            if (r7 != r0) goto L12
            int r0 = r6.currentEndLocation
            if (r8 != r0) goto L12
            float r0 = r6.currentTransitionProgress
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r0 != 0) goto L12
            if (r10 == 0) goto L11
            goto L12
        L11:
            return
        L12:
            r6.currentStartLocation = r7
            r6.currentEndLocation = r8
            r6.currentTransitionProgress = r9
            com.android.systemui.media.controls.ui.controller.MediaPlayerData r7 = com.android.systemui.media.controls.ui.controller.MediaPlayerData.INSTANCE
            r7.getClass()
            java.util.TreeMap r7 = com.android.systemui.media.controls.ui.controller.MediaPlayerData.mediaPlayers
            java.util.Collection r7 = r7.values()
            java.util.Iterator r7 = r7.iterator()
        L27:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L44
            java.lang.Object r8 = r7.next()
            com.android.systemui.media.controls.ui.controller.MediaControlPanel r8 = (com.android.systemui.media.controls.ui.controller.MediaControlPanel) r8
            com.android.systemui.media.controls.ui.controller.MediaViewController r0 = r8.mMediaViewController
            int r1 = r6.currentStartLocation
            int r2 = r6.currentEndLocation
            float r3 = r6.currentTransitionProgress
            int r8 = com.android.systemui.media.controls.ui.controller.MediaViewController.$r8$clinit
            r5 = 0
            r4 = r10
            r0.setCurrentState(r1, r2, r3, r4, r5)
            r10 = r4
            goto L27
        L44:
            com.android.systemui.media.controls.ui.controller.MediaHostStatesManager r7 = r6.mediaHostStatesManager
            java.util.Map r7 = r7.mediaHostStates
            int r8 = r6.currentEndLocation
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            java.util.LinkedHashMap r7 = (java.util.LinkedHashMap) r7
            java.lang.Object r8 = r7.get(r8)
            com.android.systemui.media.controls.ui.view.MediaHostState r8 = (com.android.systemui.media.controls.ui.view.MediaHostState) r8
            r9 = 1
            if (r8 == 0) goto L5e
            boolean r8 = r8.getShowsOnlyActiveMedia()
            goto L5f
        L5e:
            r8 = r9
        L5f:
            int r10 = r6.currentStartLocation
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            java.lang.Object r10 = r7.get(r10)
            com.android.systemui.media.controls.ui.view.MediaHostState r10 = (com.android.systemui.media.controls.ui.view.MediaHostState) r10
            if (r10 == 0) goto L72
            boolean r10 = r10.getShowsOnlyActiveMedia()
            goto L73
        L72:
            r10 = r8
        L73:
            int r0 = r6.currentStartLocation
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            java.lang.Object r0 = r7.get(r0)
            com.android.systemui.media.controls.ui.view.MediaHostState r0 = (com.android.systemui.media.controls.ui.view.MediaHostState) r0
            r1 = 0
            if (r0 == 0) goto L87
            boolean r0 = r0.getDisableScrolling()
            goto L88
        L87:
            r0 = r1
        L88:
            int r2 = r6.currentEndLocation
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.Object r7 = r7.get(r2)
            com.android.systemui.media.controls.ui.view.MediaHostState r7 = (com.android.systemui.media.controls.ui.view.MediaHostState) r7
            if (r7 == 0) goto L9a
            boolean r1 = r7.getDisableScrolling()
        L9a:
            boolean r7 = r6.currentlyShowingOnlyActive
            if (r7 != r8) goto Lb5
            boolean r7 = r6.currentlyDisableScrolling
            if (r7 != r1) goto Lb5
            float r7 = r6.currentTransitionProgress
            r2 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 != 0) goto Lab
            goto Lc2
        Lab:
            r2 = 0
            int r7 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r7 != 0) goto Lb1
            goto Lc2
        Lb1:
            if (r10 != r8) goto Lb5
            if (r0 == r1) goto Lc2
        Lb5:
            r6.currentlyShowingOnlyActive = r8
            r6.currentlyDisableScrolling = r1
            com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler r7 = r6.mediaCarouselScrollHandler
            r7.resetTranslation(r9)
            boolean r8 = r6.currentlyDisableScrolling
            r7.scrollingDisabled = r8
        Lc2:
            r6.updatePageIndicatorAlpha()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaCarouselController.setCurrentState(int, int, float, boolean):void");
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
        int m = StrongAuthPopup$$ExternalSyntheticOutline0.m(this.context, R.dimen.qs_media_padding, width);
        MediaHostState mediaHostState3 = this.desiredHostState;
        int widthMeasureSpec = (mediaHostState3 == null || (measurementInput2 = mediaHostState3.getMeasurementInput()) == null) ? 0 : measurementInput2.getWidthMeasureSpec();
        MediaHostState mediaHostState4 = this.desiredHostState;
        int heightMeasureSpec = (mediaHostState4 == null || (measurementInput = mediaHostState4.getMeasurementInput()) == null) ? 0 : measurementInput.getHeightMeasureSpec();
        MediaScrollView mediaScrollView = this.mediaCarousel;
        mediaScrollView.measure(widthMeasureSpec, heightMeasureSpec);
        mediaScrollView.layout(0, 0, width, mediaScrollView.getMeasuredHeight());
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        mediaCarouselScrollHandler.playerWidthPlusPadding = m;
        int i = mediaCarouselScrollHandler.visibleMediaIndex * m;
        int i2 = mediaCarouselScrollHandler.scrollIntoCurrentMedia;
        int i3 = i2 > m ? (m - (i2 - m)) + i : i + i2;
        MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
        if (mediaScrollView2.isLayoutRtl()) {
            ViewGroup viewGroup = mediaScrollView2.contentContainer;
            if (viewGroup == null) {
                viewGroup = null;
            }
            i3 = (viewGroup.getWidth() - mediaScrollView2.getWidth()) - i3;
        }
        mediaScrollView2.setScrollX(i3);
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
        float f = 1.0f;
        float f2 = visible2 ? 1.0f : 0.0f;
        MediaHostState mediaHostState3 = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentEndLocation));
        float squishFraction = mediaHostState3 != null ? mediaHostState3.getSquishFraction() : 1.0f;
        float f3 = visible ? 1.0f : 0.0f;
        PageIndicator pageIndicator = this.pageIndicator;
        float translationY = (pageIndicator.getTranslationY() + pageIndicator.getHeight()) / this.mediaCarousel.getMeasuredHeight();
        Companion.getClass();
        float interpolation = TRANSFORM_BEZIER.getInterpolation(MathUtils.constrain((squishFraction - translationY) / (1.0f - translationY), 0.0f, 1.0f)) * f3;
        if (!visible || !visible2) {
            float f4 = this.currentTransitionProgress;
            if (!visible) {
                f4 = 1.0f - f4;
            }
            f = MathUtils.lerp(f2, interpolation, MathUtils.constrain(MathUtils.map(0.95f, 1.0f, 0.0f, 1.0f, f4), 0.0f, 1.0f));
        }
        pageIndicator.setAlpha(f);
    }

    public final void updatePageIndicatorLocation() {
        int i;
        int width;
        boolean z = this.isRtl;
        PageIndicator pageIndicator = this.pageIndicator;
        if (z) {
            i = pageIndicator.getWidth();
            width = this.currentCarouselWidth;
        } else {
            i = this.currentCarouselWidth;
            width = pageIndicator.getWidth();
        }
        pageIndicator.setTranslationX(((i - width) / 2.0f) + this.mediaCarouselScrollHandler.contentTranslation);
        pageIndicator.setTranslationY((this.mediaCarousel.getMeasuredHeight() - pageIndicator.getHeight()) - ((ViewGroup.MarginLayoutParams) pageIndicator.getLayoutParams()).bottomMargin);
    }

    public final void updatePlayers(final boolean z) {
        ColorStateList valueOf = ColorStateList.valueOf(this.context.getColor(R.color.media_paging_indicator));
        PageIndicator pageIndicator = this.pageIndicator;
        if (!valueOf.equals(pageIndicator.mTint)) {
            pageIndicator.mTint = valueOf;
            int childCount = pageIndicator.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = pageIndicator.getChildAt(i);
                if (childAt instanceof ImageView) {
                    ((ImageView) childAt).setImageTintList(pageIndicator.mTint);
                }
            }
        }
        MediaPlayerData.INSTANCE.getClass();
        Collection values = MediaPlayerData.visibleMediaPlayers.values();
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        final MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(values, mediaCarouselScrollHandler.visibleMediaIndex);
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$updatePlayers$onUiExecutionEnd$1
            @Override // java.lang.Runnable
            public final void run() {
                if (z) {
                    MediaCarouselController mediaCarouselController = this;
                    MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                    mediaCarouselController.reorderAllPlayers();
                }
            }
        };
        Set<Map.Entry> entrySet = ((LinkedHashMap) MediaPlayerData.mediaData).entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
        for (Map.Entry entry : entrySet) {
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
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
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
                            MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                            MediaCarouselController.Companion companion = MediaCarouselController.Companion;
                            mediaCarouselController.getClass();
                            MediaViewHolder.Companion companion2 = MediaViewHolder.Companion;
                            LayoutInflater from = LayoutInflater.from(mediaCarouselController.context);
                            ViewGroup viewGroup = mediaCarouselController.mediaContent;
                            companion2.getClass();
                            View inflate = from.inflate(R.layout.media_session_view, viewGroup, false);
                            inflate.setLayerType(2, null);
                            inflate.setLayoutDirection(3);
                            final MediaViewHolder mediaViewHolder = new MediaViewHolder(inflate);
                            mediaViewHolder.seekBar.setLayoutDirection(0);
                            final MediaCarouselController mediaCarouselController2 = MediaCarouselController.this;
                            DelayableExecutor delayableExecutor = mediaCarouselController2.uiExecutor;
                            final String str2 = str;
                            final MediaData mediaData2 = mediaData;
                            final MediaPlayerData.MediaSortKey mediaSortKey4 = mediaSortKey3;
                            final Runnable runnable2 = runnable;
                            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$addOrUpdatePlayer$1$1.1
                                /* JADX WARN: Code restructure failed: missing block: B:10:0x0098, code lost:
                                
                                    r2.player.setOnLongClickListener(new com.android.systemui.media.controls.ui.controller.MediaControlPanel$$ExternalSyntheticLambda4(r5));
                                    r5.mMediaViewHolder.albumView.setLayerType(2, null);
                                    r7 = r5.mMediaViewHolder;
                                    r11 = r7.titleText;
                                    r12 = r7.artistText;
                                    r7 = r7.explicitIndicator;
                                    r10 = r5.loadAnimator(com.android.systemui.R.anim.media_metadata_enter, com.android.app.animation.Interpolators.EMPHASIZED_DECELERATE, r11, r12, r7);
                                    r7 = r5.loadAnimator(com.android.systemui.R.anim.media_metadata_exit, com.android.app.animation.Interpolators.EMPHASIZED_ACCELERATE, r11, r12, r7);
                                    r5.mMultiRippleController = new com.android.systemui.surfaceeffects.ripple.MultiRippleController(r2.multiRippleView);
                                    r11 = android.graphics.BlendMode.SCREEN;
                                    r12 = r2.turbulenceNoiseView;
                                    r12.getClass();
                                    r12.paint.setBlendMode(r11);
                                    r2 = r2.loadingEffectView;
                                    r2.blendMode = r11;
                                    r2.setVisibility(4);
                                    r5.mColorSchemeTransition = new com.android.systemui.media.controls.ui.animation.ColorSchemeTransition(r5.mContext, r5.mMediaViewHolder, r5.mMultiRippleController, new com.android.systemui.surfaceeffects.turbulencenoise.TurbulenceNoiseController(r12));
                                    r5.mMetadataAnimationHandler = new com.android.systemui.media.controls.ui.animation.MetadataAnimationHandler(r7, r10);
                                    r9.sizeChangedListener = new com.android.systemui.media.controls.ui.controller.MediaCarouselController$setupNewPlayer$1(r1);
                                    r2 = new android.widget.LinearLayout.LayoutParams(-1, -2);
                                    r6 = r5.mMediaViewHolder;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:11:0x0121, code lost:
                                
                                    if (r6 == null) goto L18;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:12:0x0123, code lost:
                                
                                    r6 = r6.player;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:13:0x0125, code lost:
                                
                                    if (r6 == null) goto L18;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:14:0x0127, code lost:
                                
                                    r6.setLayoutParams(r2);
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:15:0x012a, code lost:
                                
                                    r5.bindPlayer(r4, r3);
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:16:0x0131, code lost:
                                
                                    if (r1.mediaCarouselScrollHandler.visibleToUser == false) goto L23;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:18:0x0135, code lost:
                                
                                    if (r1.currentlyExpanded == false) goto L23;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:19:0x0137, code lost:
                                
                                    r2 = true;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:20:0x013c, code lost:
                                
                                    r8.bgExecutor.execute(new com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$listening$1(r8, r2));
                                    r2 = com.android.systemui.media.controls.ui.controller.MediaPlayerData.INSTANCE;
                                    r2.addMediaPlayer(r3, r4, r5, r1.systemClock, r1.debugLogger);
                                    r10 = r1.currentStartLocation;
                                    r11 = r1.currentEndLocation;
                                    r12 = r1.currentTransitionProgress;
                                    r3 = com.android.systemui.media.controls.ui.controller.MediaViewController.$r8$clinit;
                                    r9.setCurrentState(r10, r11, r12, true, false);
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:21:0x015f, code lost:
                                
                                    if (r4.active == false) goto L27;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:22:0x0161, code lost:
                                
                                    r1.reorderAllPlayers();
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:23:0x0164, code lost:
                                
                                    r1.updatePageIndicator$2();
                                    r1.mediaCarouselScrollHandler.onPlayersChanged();
                                    r1 = r1.mediaControlChipInteractor;
                                    r2.getClass();
                                    r1.updateMediaControlChipModelLegacy(com.android.systemui.media.controls.ui.controller.MediaPlayerData.getFirstActiveMediaData());
                                    com.android.systemui.util.animation.UniqueObjectHostViewKt.setRequiresRemeasuring(r1.mediaFrame, true);
                                    r0 = r6;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:24:0x0188, code lost:
                                
                                    if (r0 == null) goto L37;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:25:0x018a, code lost:
                                
                                    r0.run();
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:26:0x018d, code lost:
                                
                                    return;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:28:?, code lost:
                                
                                    return;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:29:0x013a, code lost:
                                
                                    r2 = false;
                                 */
                                /* JADX WARN: Code restructure failed: missing block: B:31:0x0095, code lost:
                                
                                    if (r7 != false) goto L9;
                                 */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final void run() {
                                    /*
                                        Method dump skipped, instructions count: 405
                                        To view this dump change 'Code comments level' option to 'DEBUG'
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaCarouselController$addOrUpdatePlayer$1$1.AnonymousClass1.run():void");
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
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                i2 = i3;
            } catch (Throwable th) {
                if (isEnabled) {
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
