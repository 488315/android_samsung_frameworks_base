package com.android.systemui.media.controls.ui.controller;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Trace;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.MathUtils;
import android.util.StatsEvent;
import android.util.StatsLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.ui.binder.SeekBarObserver;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.controller.MediaPlayerData;
import com.android.systemui.media.controls.ui.controller.MediaViewController;
import com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.media.controls.ui.view.MediaScrollView;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.view.RecommendationViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.MediaCarouselViewModel;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel$listening$1;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PageIndicator;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
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
import com.sec.ims.volte2.data.QuantumSecurityInfo;
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
import java.util.concurrent.Executor;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

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
    public final List commonViewModels;
    public final Context context;
    public final Map controllerByViewModel;
    public int currentCarouselHeight;
    public int currentCarouselWidth;
    public boolean currentlyShowingOnlyActive;
    public final MediaCarouselControllerLogger debugLogger;
    public MediaHostState desiredHostState;
    public final FalsingManager falsingManager;
    public final GlobalSettings globalSettings;
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
    public final Provider mediaControlPanelFactory;
    public final MediaFlags mediaFlags;
    public final ViewGroup mediaFrame;
    public final MediaHostStatesManager mediaHostStatesManager;
    public final MediaDataManager mediaManager;
    public final Provider mediaViewControllerFactory;
    public final PageIndicator pageIndicator;
    public boolean playersVisible;
    public final SecureSettings secureSettings;
    public View settingsButton;
    public boolean shouldScrollToKey;
    public final SystemClock systemClock;
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
        public final Object invoke(Object obj) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            MediaCarouselController mediaCarouselController = (MediaCarouselController) this.receiver;
            Companion companion = MediaCarouselController.Companion;
            mediaCarouselController.updateSeekbarListening(booleanValue);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$4, reason: invalid class name */
    final /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass4(Object obj) {
            super(1, obj, MediaCarouselController.class, "closeGuts", "closeGuts(Z)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            ((MediaCarouselController) this.receiver).closeGuts(((Boolean) obj).booleanValue());
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$5, reason: invalid class name */
    final /* synthetic */ class AnonymousClass5 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass5(Object obj) {
            super(1, obj, MediaCarouselController.class, "logSmartspaceImpression", "logSmartspaceImpression(Z)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            ((MediaCarouselController) this.receiver).logSmartspaceImpression(((Boolean) obj).booleanValue());
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.media.controls.ui.controller.MediaCarouselController$8$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ MediaCarouselController this$0;

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
                this.this$0.listenForLockscreenSettingChanges$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
                this.this$0.mediaFlags.getClass();
                Flags.sceneContainer();
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass8(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass8 anonymousClass8 = MediaCarouselController.this.new AnonymousClass8((Continuation) obj3);
            anonymousClass8.L$0 = (LifecycleOwner) obj;
            return anonymousClass8.invokeSuspend(Unit.INSTANCE);
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
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaCarouselController(Context context, Provider provider, VisualStabilityProvider visualStabilityProvider, MediaHostStatesManager mediaHostStatesManager, ActivityStarter activityStarter, SystemClock systemClock, CoroutineDispatcher coroutineDispatcher, final DelayableExecutor delayableExecutor, Executor executor, CoroutineDispatcher coroutineDispatcher2, MediaDataManager mediaDataManager, ConfigurationController configurationController, FalsingManager falsingManager, DumpManager dumpManager, MediaUiEventLogger mediaUiEventLogger, MediaCarouselControllerLogger mediaCarouselControllerLogger, MediaFlags mediaFlags, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardTransitionInteractor keyguardTransitionInteractor, GlobalSettings globalSettings, SecureSettings secureSettings, MediaCarouselViewModel mediaCarouselViewModel, Provider provider2, SceneInteractor sceneInteractor) {
        int i;
        this.context = context;
        this.mediaControlPanelFactory = provider;
        this.visualStabilityProvider = visualStabilityProvider;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.activityStarter = activityStarter;
        this.systemClock = systemClock;
        this.bgExecutor = executor;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.mediaManager = mediaDataManager;
        this.falsingManager = falsingManager;
        this.logger = mediaUiEventLogger;
        this.debugLogger = mediaCarouselControllerLogger;
        this.mediaFlags = mediaFlags;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.globalSettings = globalSettings;
        this.secureSettings = secureSettings;
        this.mediaCarouselViewModel = mediaCarouselViewModel;
        this.animationScaleObserver = new ContentObserver(delayableExecutor) { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$animationScaleObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                this.mediaFlags.getClass();
                Flags.sceneContainer();
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
                        ViewGroup viewGroup = mediaScrollView.contentContainer;
                        if (viewGroup == null) {
                            viewGroup = null;
                        }
                        i2 = viewGroup.getWidth() - mediaScrollView.getWidth();
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
        ?? r11 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStrongAuthStateChanged(int i2) {
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                boolean isUserInLockdown = mediaCarouselController.keyguardUpdateMonitor.isUserInLockdown(i2);
                MediaCarouselControllerLogger mediaCarouselControllerLogger2 = mediaCarouselController.debugLogger;
                if (isUserInLockdown) {
                    mediaCarouselControllerLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    MediaCarouselControllerLogger$logCarouselHidden$2 mediaCarouselControllerLogger$logCarouselHidden$2 = new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger$logCarouselHidden$2
                        @Override // kotlin.jvm.functions.Function1
                        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            return "hiding carousel";
                        }
                    };
                    LogBuffer logBuffer = mediaCarouselControllerLogger2.buffer;
                    logBuffer.commit(logBuffer.obtain("MediaCarouselCtlrLog", logLevel, mediaCarouselControllerLogger$logCarouselHidden$2, null));
                    mediaCarouselController.mediaCarousel.setVisibility(8);
                    return;
                }
                if (mediaCarouselController.keyguardUpdateMonitor.mUserIsUnlocked.get(i2)) {
                    mediaCarouselControllerLogger2.getClass();
                    LogLevel logLevel2 = LogLevel.DEBUG;
                    MediaCarouselControllerLogger$logCarouselVisible$2 mediaCarouselControllerLogger$logCarouselVisible$2 = new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger$logCarouselVisible$2
                        @Override // kotlin.jvm.functions.Function1
                        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                            return "showing carousel";
                        }
                    };
                    LogBuffer logBuffer2 = mediaCarouselControllerLogger2.buffer;
                    logBuffer2.commit(logBuffer2.obtain("MediaCarouselCtlrLog", logLevel2, mediaCarouselControllerLogger$logCarouselVisible$2, null));
                    mediaCarouselController.mediaCarousel.setVisibility(0);
                }
            }
        };
        this.keyguardUpdateMonitorCallback = r11;
        this.updateHostVisibility = new Function0() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$updateHostVisibility$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
        new LinkedHashMap();
        this.commonViewModels = new ArrayList();
        DumpManager.registerDumpable$default(dumpManager, "MediaCarouselController", this);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.media_carousel, (ViewGroup) new UniqueObjectHostView(context), false);
        viewGroup.setLayoutDirection(3);
        this.mediaFrame = viewGroup;
        MediaScrollView mediaScrollView = (MediaScrollView) viewGroup.requireViewById(R.id.media_carousel_scroller);
        this.mediaCarousel = mediaScrollView;
        PageIndicator pageIndicator = (PageIndicator) viewGroup.requireViewById(R.id.media_page_indicator);
        this.pageIndicator = pageIndicator;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = new MediaCarouselScrollHandler(mediaScrollView, pageIndicator, delayableExecutor, new AnonymousClass1(this), new AnonymousClass2(this), new AnonymousClass3(this), new AnonymousClass4(this), falsingManager, new AnonymousClass5(this), mediaUiEventLogger);
        this.mediaCarouselScrollHandler = mediaCarouselScrollHandler;
        this.carouselLocale = context.getResources().getConfiguration().getLocales().get(0);
        ?? r14 = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1 ? 1 : 0;
        if (r14 != this.isRtl) {
            this.isRtl = r14;
            viewGroup.setLayoutDirection(r14);
            MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
            if (mediaScrollView2.isLayoutRtl()) {
                ViewGroup viewGroup2 = mediaScrollView2.contentContainer;
                i = (viewGroup2 == null ? null : viewGroup2).getWidth() - mediaScrollView2.getWidth();
            } else {
                i = 0;
            }
            mediaScrollView2.setScrollX(i);
        }
        inflateSettingsButton();
        this.mediaContent = (ViewGroup) mediaScrollView.requireViewById(R.id.media_carousel);
        ((ConfigurationControllerImpl) configurationController).addCallback(configurationListener);
        Flags.sceneContainer();
        viewGroup.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.6
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                Companion companion = MediaCarouselController.Companion;
                mediaCarouselController.updatePageIndicatorLocation();
            }
        });
        mediaHostStatesManager.callbacks.add(new MediaHostStatesManager.Callback() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.7
            @Override // com.android.systemui.media.controls.ui.controller.MediaHostStatesManager.Callback
            public final void onHostStateChanged(int i2, MediaHostState mediaHostState) {
                MediaCarouselController mediaCarouselController = MediaCarouselController.this;
                Function0 function0 = mediaCarouselController.updateUserVisibility;
                if (function0 == null) {
                    function0 = null;
                }
                function0.invoke();
                int i3 = mediaCarouselController.desiredLocation;
                if (i2 == i3) {
                    mediaCarouselController.onDesiredLocationChanged(i3, mediaHostState, false, 200L, 0L);
                }
            }
        });
        keyguardUpdateMonitor.registerCallback(r11);
        RepeatWhenAttachedKt.repeatWhenAttached(mediaScrollView, EmptyCoroutineContext.INSTANCE, new AnonymousClass8(null));
        executor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController.9
            @Override // java.lang.Runnable
            public final void run() {
                MediaCarouselController.this.globalSettings.registerContentObserverSync(Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE), MediaCarouselController.this.animationScaleObserver);
            }
        });
    }

    public static final void access$updateCarouselDimensions(MediaCarouselController mediaCarouselController) {
        mediaCarouselController.mediaFlags.getClass();
        Flags.sceneContainer();
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

    public static void logSmartspaceCardReported$default(MediaCarouselController mediaCarouselController, int i, int i2, int i3, int[] iArr, int i4, int i5, int i6, boolean z, int i7) {
        int[] iArr2 = iArr;
        int i8 = (i7 & 16) != 0 ? 0 : i4;
        int i9 = (i7 & 32) != 0 ? 0 : i5;
        int i10 = (i7 & 64) != 0 ? mediaCarouselController.mediaCarouselScrollHandler.visibleMediaIndex : i6;
        boolean z2 = (i7 & 256) != 0 ? false : z;
        mediaCarouselController.getClass();
        MediaPlayerData.INSTANCE.getClass();
        if (MediaPlayerData.mediaPlayers.values().size() <= i10) {
            return;
        }
        MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAt(MediaPlayerData.visibleMediaPlayers.values(), i10);
        boolean z3 = mediaSortKey.isSsMediaRec;
        if (z3 || mediaCarouselController.mediaManager.isRecommendationActive() || MediaPlayerData.smartspaceMediaData != null) {
            int childCount = mediaCarouselController.mediaContent.getChildCount();
            int length = iArr2.length;
            int i11 = 0;
            while (i11 < length) {
                int i12 = iArr2[i11];
                int i13 = z2 ? -1 : i10;
                boolean z4 = mediaSortKey.isSsReactivated;
                boolean z5 = z2;
                int i14 = z3 ? 15 : z4 ? 43 : 31;
                StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
                MediaPlayerData.MediaSortKey mediaSortKey2 = mediaSortKey;
                newBuilder.setAtomId(QuantumSecurityInfo.QUANTUM_KEY_STATUS.KEY_STATUS_EXCEPTION);
                newBuilder.writeInt(i);
                newBuilder.writeInt(i2);
                newBuilder.writeInt(0);
                newBuilder.writeInt(i12);
                newBuilder.writeInt(i13);
                newBuilder.writeInt(childCount);
                newBuilder.writeInt(i14);
                newBuilder.writeInt(i3);
                newBuilder.addBooleanAnnotation((byte) 1, true);
                newBuilder.writeInt(i8);
                newBuilder.writeInt(i9);
                newBuilder.writeInt(0);
                newBuilder.writeByteArray(new byte[0]);
                newBuilder.writeByteArray(new byte[0]);
                newBuilder.usePooledBuffer();
                StatsLog.write(newBuilder.build());
                if (MediaCarouselControllerKt.DEBUG) {
                    StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "Log Smartspace card event id: ", " instance id: ", " surface: ");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i12, " rank: ", i10, " cardinality: ");
                    m.append(childCount);
                    m.append(" isRecommendationCard: ");
                    m.append(z3);
                    m.append(" isSsReactivated: ");
                    m.append(z4);
                    m.append("uid: ");
                    m.append(i3);
                    m.append(" interactedSubcardRank: ");
                    AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, i8, " interactedSubcardCardinality: ", i9, " received_latency_millis: ");
                    RecyclerView$$ExternalSyntheticOutline0.m(0, "MediaCarouselController", m);
                }
                i11++;
                iArr2 = iArr;
                z2 = z5;
                mediaSortKey = mediaSortKey2;
            }
        }
    }

    public static /* synthetic */ MediaControlPanel removePlayer$default(MediaCarouselController mediaCarouselController, String str, int i) {
        return mediaCarouselController.removePlayer(str, false, (i & 4) != 0, false);
    }

    public final void closeGuts(boolean z) {
        this.mediaFlags.getClass();
        Flags.sceneContainer();
        MediaPlayerData.INSTANCE.getClass();
        Iterator it = MediaPlayerData.mediaPlayers.values().iterator();
        while (it.hasNext()) {
            ((MediaControlPanel) it.next()).closeGuts(z);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("keysNeedRemoval: " + this.keysNeedRemoval);
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        mediaPlayerData.getClass();
        printWriter.println("dataKeys: " + ((LinkedHashMap) MediaPlayerData.mediaData).keySet());
        mediaPlayerData.getClass();
        printWriter.println("orderedPlayerSortKeys: " + MediaPlayerData.mediaPlayers.keySet());
        mediaPlayerData.getClass();
        printWriter.println("visiblePlayerSortKeys: " + MediaPlayerData.visibleMediaPlayers.values());
        printWriter.println("commonViewModels: " + this.commonViewModels);
        mediaPlayerData.getClass();
        printWriter.println("smartspaceMediaData: " + MediaPlayerData.smartspaceMediaData);
        mediaPlayerData.getClass();
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("shouldPrioritizeSs: ", MediaPlayerData.shouldPrioritizeSs, printWriter);
        printWriter.println("current size: " + this.currentCarouselWidth + " x " + this.currentCarouselHeight);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("location: ", this.desiredLocation, printWriter);
        MediaHostState mediaHostState = this.desiredHostState;
        Float valueOf = mediaHostState != null ? Float.valueOf(mediaHostState.getExpansion()) : null;
        MediaHostState mediaHostState2 = this.desiredHostState;
        printWriter.println("state: " + valueOf + ", only active " + (mediaHostState2 != null ? Boolean.valueOf(mediaHostState2.getShowsOnlyActiveMedia()) : null));
        mediaPlayerData.getClass();
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("isSwipedAway: ", MediaPlayerData.isSwipedAway, printWriter);
    }

    public final void inflateSettingsButton() {
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.media_carousel_settings_button, this.mediaFrame, false);
        View view = this.settingsButton;
        if (view != null) {
            ViewGroup viewGroup = this.mediaFrame;
            if (view == null) {
                view = null;
            }
            viewGroup.removeView(view);
        }
        this.settingsButton = inflate;
        this.mediaFrame.addView(inflate);
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        mediaCarouselScrollHandler.settingsButton = inflate;
        Resources resources = inflate.getResources();
        View view2 = mediaCarouselScrollHandler.settingsButton;
        if (view2 == null) {
            view2 = null;
        }
        mediaCarouselScrollHandler.cornerRadius = resources.getDimensionPixelSize(Utils.getThemeAttr(android.R.attr.dialogCornerRadius, view2.getContext()));
        mediaCarouselScrollHandler.updateSettingsPresentation();
        mediaCarouselScrollHandler.scrollView.invalidateOutline();
        View view3 = this.settingsButton;
        (view3 != null ? view3 : null).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$inflateSettingsButton$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view4) {
                MediaCarouselController.this.logger.logger.log(MediaUiEvent.OPEN_SETTINGS_CAROUSEL);
                MediaCarouselController.this.activityStarter.startActivity(MediaCarouselControllerKt.settingsIntent, true);
            }
        });
    }

    public final Job listenForAnyStateToGoneKeyguardTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new MediaCarouselController$listenForAnyStateToGoneKeyguardTransition$1(this, null), 3);
    }

    public final Job listenForAnyStateToLockscreenTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new MediaCarouselController$listenForAnyStateToLockscreenTransition$1(this, null), 3);
    }

    public final Job listenForLockscreenSettingChanges$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope) {
        return BuildersKt.launch$default(coroutineScope, null, null, new MediaCarouselController$listenForLockscreenSettingChanges$1(this, null), 3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x006e, code lost:
    
        if (r4 == (-1)) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void logSmartspaceImpression(boolean r15) {
        /*
            r14 = this;
            com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler r0 = r14.mediaCarouselScrollHandler
            int r0 = r0.visibleMediaIndex
            com.android.systemui.media.controls.ui.controller.MediaPlayerData r1 = com.android.systemui.media.controls.ui.controller.MediaPlayerData.INSTANCE
            r1.getClass()
            java.util.TreeMap r1 = com.android.systemui.media.controls.ui.controller.MediaPlayerData.mediaPlayers
            java.util.Collection r2 = r1.values()
            int r2 = r2.size()
            if (r2 <= r0) goto L92
            java.util.LinkedHashMap r2 = com.android.systemui.media.controls.ui.controller.MediaPlayerData.visibleMediaPlayers
            java.util.Collection r2 = r2.values()
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.lang.Object r0 = kotlin.collections.CollectionsKt___CollectionsKt.elementAt(r2, r0)
            java.lang.Object r0 = r1.get(r0)
            com.android.systemui.media.controls.ui.controller.MediaControlPanel r0 = (com.android.systemui.media.controls.ui.controller.MediaControlPanel) r0
            com.android.systemui.media.controls.shared.model.SmartspaceMediaData r2 = com.android.systemui.media.controls.ui.controller.MediaPlayerData.smartspaceMediaData
            r3 = 1
            if (r2 == 0) goto L32
            boolean r2 = r2.isActive
            if (r2 == 0) goto L32
        L30:
            r2 = r3
            goto L71
        L32:
            java.util.Set r1 = r1.entrySet()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.Iterator r1 = r1.iterator()
            r2 = 0
            r4 = r2
        L3e:
            boolean r5 = r1.hasNext()
            r6 = -1
            if (r5 == 0) goto L6d
            java.lang.Object r5 = r1.next()
            int r7 = r4 + 1
            if (r4 < 0) goto L68
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r8 = r5.getKey()
            com.android.systemui.media.controls.ui.controller.MediaPlayerData$MediaSortKey r8 = (com.android.systemui.media.controls.ui.controller.MediaPlayerData.MediaSortKey) r8
            boolean r8 = r8.isSsMediaRec
            if (r8 != 0) goto L66
            java.lang.Object r5 = r5.getKey()
            com.android.systemui.media.controls.ui.controller.MediaPlayerData$MediaSortKey r5 = (com.android.systemui.media.controls.ui.controller.MediaPlayerData.MediaSortKey) r5
            com.android.systemui.media.controls.shared.model.MediaData r5 = r5.data
            boolean r5 = r5.active
            if (r5 == 0) goto L66
            goto L6e
        L66:
            r4 = r7
            goto L3e
        L68:
            kotlin.collections.CollectionsKt__CollectionsKt.throwIndexOverflow()
            r14 = 0
            throw r14
        L6d:
            r4 = r6
        L6e:
            if (r4 == r6) goto L71
            goto L30
        L71:
            if (r2 != 0) goto L76
            if (r15 != 0) goto L76
            return
        L76:
            if (r0 == 0) goto L92
            int r6 = r0.mSmartspaceId
            int r7 = r0.mUid
            int r15 = r0.getSurfaceForSmartspaceLogging()
            int[] r8 = new int[]{r15}
            r11 = 0
            r12 = 0
            r5 = 800(0x320, float:1.121E-42)
            r9 = 0
            r10 = 0
            r13 = 496(0x1f0, float:6.95E-43)
            r4 = r14
            logSmartspaceCardReported$default(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            r0.mIsImpressed = r3
        L92:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaCarouselController.logSmartspaceImpression(boolean):void");
    }

    public final Unit onDesiredLocationChanged(final int i, MediaHostState mediaHostState, boolean z, long j, long j2) {
        Unit unit;
        TransitionViewState obtainViewState;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaCarouselController#onDesiredLocationChanged");
        }
        if (mediaHostState != null) {
            try {
                if (this.desiredLocation != i) {
                    this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.controller.MediaCarouselController$onDesiredLocationChanged$1$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaUiEvent mediaUiEvent;
                            MediaUiEventLogger mediaUiEventLogger = MediaCarouselController.this.logger;
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
                            } else {
                                if (i2 != 4) {
                                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Unknown media carousel location "));
                                }
                                mediaUiEvent = MediaUiEvent.MEDIA_CAROUSEL_LOCATION_COMMUNAL;
                            }
                            mediaUiEventLogger.logger.log(mediaUiEvent);
                        }
                    });
                }
                this.desiredLocation = i;
                this.desiredHostState = mediaHostState;
                boolean z2 = mediaHostState.getExpansion() > 0.0f;
                boolean z3 = this.currentlyExpanded;
                MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
                if (z3 != z2) {
                    this.currentlyExpanded = z2;
                    updateSeekbarListening(mediaCarouselScrollHandler.visibleToUser);
                }
                boolean z4 = (this.currentlyExpanded || this.mediaManager.hasActiveMediaOrRecommendation() || !mediaHostState.getShowsOnlyActiveMedia()) ? false : true;
                this.mediaFlags.getClass();
                Flags.sceneContainer();
                MediaPlayerData.INSTANCE.getClass();
                for (MediaControlPanel mediaControlPanel : MediaPlayerData.mediaPlayers.values()) {
                    if (z) {
                        MediaViewController mediaViewController = mediaControlPanel.mMediaViewController;
                        mediaViewController.animateNextStateChange = true;
                        mediaViewController.animationDuration = j;
                        mediaViewController.animationDelay = j2;
                    }
                    if (z4 && mediaControlPanel.mMediaViewController.isGutsVisible) {
                        mediaControlPanel.closeGuts(!z);
                    }
                    MediaViewController mediaViewController2 = mediaControlPanel.mMediaViewController;
                    MediaHostState mediaHostState2 = (MediaHostState) ((LinkedHashMap) mediaViewController2.mediaHostStatesManager.mediaHostStates).get(Integer.valueOf(i));
                    if (mediaHostState2 == null) {
                        obtainViewState = null;
                    } else {
                        mediaViewController2.mediaFlags.getClass();
                        Flags.sceneContainer();
                        obtainViewState = mediaViewController2.obtainViewState(mediaHostState2, false);
                        if (obtainViewState != null) {
                            TransitionViewState transitionViewState = mediaViewController2.tmpState;
                            mediaViewController2.updateViewStateSize(obtainViewState, i, transitionViewState);
                            obtainViewState = transitionViewState;
                        }
                    }
                    if (obtainViewState != null) {
                        mediaViewController2.layoutController.setMeasureState(obtainViewState);
                    }
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
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        } else {
            unit = null;
        }
        if (isEnabled) {
            TraceUtilsKt.endSlice();
        }
        return unit;
    }

    public final void onSwipeToDismiss() {
        this.mediaFlags.getClass();
        Flags.sceneContainer();
        MediaPlayerData.INSTANCE.getClass();
        int i = 0;
        for (Object obj : MediaPlayerData.mediaPlayers.values()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            MediaControlPanel mediaControlPanel = (MediaControlPanel) obj;
            if (mediaControlPanel.mIsImpressed) {
                logSmartspaceCardReported$default(this, 761, mediaControlPanel.mSmartspaceId, mediaControlPanel.mUid, new int[]{mediaControlPanel.getSurfaceForSmartspaceLogging()}, 0, 0, i, true, 176);
                mediaControlPanel.mIsImpressed = false;
            }
            i = i2;
        }
        MediaPlayerData.INSTANCE.getClass();
        MediaPlayerData.isSwipedAway = true;
        this.logger.logger.log(MediaUiEvent.DISMISS_SWIPE);
        this.mediaManager.onSwipeToDismiss();
    }

    public final MediaControlPanel removePlayer(String str, boolean z, boolean z2, boolean z3) {
        SmartspaceMediaData smartspaceMediaData;
        MediaPlayerData.INSTANCE.getClass();
        if (str.equals(MediaPlayerData.smartspaceMediaKey()) && (smartspaceMediaData = MediaPlayerData.smartspaceMediaData) != null) {
            this.logger.logger.logWithInstanceId(MediaUiEvent.MEDIA_RECOMMENDATION_REMOVED, 0, smartspaceMediaData.packageName, smartspaceMediaData.instanceId);
        }
        MediaControlPanel removeMediaPlayer = MediaPlayerData.removeMediaPlayer(str, z || z2);
        if (removeMediaPlayer == null) {
            return null;
        }
        MediaViewHolder mediaViewHolder = removeMediaPlayer.mMediaViewHolder;
        TransitionLayout transitionLayout = mediaViewHolder != null ? mediaViewHolder.player : null;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.mediaCarouselScrollHandler;
        int indexOfChild = mediaCarouselScrollHandler.mediaContent.indexOfChild(transitionLayout);
        int i = mediaCarouselScrollHandler.visibleMediaIndex;
        boolean z4 = true;
        boolean z5 = indexOfChild <= i;
        if (z5) {
            mediaCarouselScrollHandler.visibleMediaIndex = Math.max(0, i - 1);
        }
        MediaScrollView mediaScrollView = mediaCarouselScrollHandler.scrollView;
        if (!mediaScrollView.isLayoutRtl() || mediaCarouselScrollHandler.visibleMediaIndex == 0) {
            z4 = z5;
        } else if (z5) {
            z4 = false;
        }
        if (z4) {
            mediaScrollView.setScrollX(Math.max(mediaScrollView.getScrollX() - mediaCarouselScrollHandler.playerWidthPlusPadding, 0));
        }
        ViewGroup viewGroup = this.mediaContent;
        MediaViewHolder mediaViewHolder2 = removeMediaPlayer.mMediaViewHolder;
        viewGroup.removeView(mediaViewHolder2 != null ? mediaViewHolder2.player : null);
        ViewGroup viewGroup2 = this.mediaContent;
        RecommendationViewHolder recommendationViewHolder = removeMediaPlayer.mRecommendationViewHolder;
        viewGroup2.removeView(recommendationViewHolder != null ? recommendationViewHolder.recommendations : null);
        removeMediaPlayer.onDestroy();
        mediaCarouselScrollHandler.onPlayersChanged();
        updatePageIndicator$2();
        MediaDataManager mediaDataManager = this.mediaManager;
        if (z) {
            mediaDataManager.dismissMediaData(str, 0L, z3);
        }
        if (!z2) {
            return removeMediaPlayer;
        }
        mediaDataManager.dismissSmartspaceRecommendation(str, 0L);
        return removeMediaPlayer;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e2, code lost:
    
        r0.scrollToPlayer(r3, r5);
        r1 = kotlin.Unit.INSTANCE;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reorderAllPlayers(com.android.systemui.media.controls.ui.controller.MediaPlayerData.MediaSortKey r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaCarouselController.reorderAllPlayers(com.android.systemui.media.controls.ui.controller.MediaPlayerData$MediaSortKey, java.lang.String):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0081, code lost:
    
        if (r2 == r3) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCurrentState(int r2, int r3, float r4, boolean r5) {
        /*
            r1 = this;
            int r0 = r1.currentStartLocation
            if (r2 != r0) goto L10
            int r0 = r1.currentEndLocation
            if (r3 != r0) goto L10
            float r0 = r1.currentTransitionProgress
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 != 0) goto L10
            if (r5 == 0) goto L8d
        L10:
            r1.currentStartLocation = r2
            r1.currentEndLocation = r3
            r1.currentTransitionProgress = r4
            com.android.systemui.media.controls.util.MediaFlags r2 = r1.mediaFlags
            r2.getClass()
            com.android.systemui.Flags.sceneContainer()
            com.android.systemui.media.controls.ui.controller.MediaPlayerData r2 = com.android.systemui.media.controls.ui.controller.MediaPlayerData.INSTANCE
            r2.getClass()
            java.util.TreeMap r2 = com.android.systemui.media.controls.ui.controller.MediaPlayerData.mediaPlayers
            java.util.Collection r2 = r2.values()
            java.util.Iterator r2 = r2.iterator()
        L2d:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L3f
            java.lang.Object r3 = r2.next()
            com.android.systemui.media.controls.ui.controller.MediaControlPanel r3 = (com.android.systemui.media.controls.ui.controller.MediaControlPanel) r3
            com.android.systemui.media.controls.ui.controller.MediaViewController r3 = r3.mMediaViewController
            r1.updateViewControllerToState(r3, r5)
            goto L2d
        L3f:
            com.android.systemui.media.controls.ui.controller.MediaHostStatesManager r2 = r1.mediaHostStatesManager
            java.util.Map r2 = r2.mediaHostStates
            int r3 = r1.currentEndLocation
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.util.LinkedHashMap r2 = (java.util.LinkedHashMap) r2
            java.lang.Object r3 = r2.get(r3)
            com.android.systemui.media.controls.ui.view.MediaHostState r3 = (com.android.systemui.media.controls.ui.view.MediaHostState) r3
            r4 = 1
            if (r3 == 0) goto L59
            boolean r3 = r3.getShowsOnlyActiveMedia()
            goto L5a
        L59:
            r3 = r4
        L5a:
            int r5 = r1.currentStartLocation
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Object r2 = r2.get(r5)
            com.android.systemui.media.controls.ui.view.MediaHostState r2 = (com.android.systemui.media.controls.ui.view.MediaHostState) r2
            if (r2 == 0) goto L6d
            boolean r2 = r2.getShowsOnlyActiveMedia()
            goto L6e
        L6d:
            r2 = r3
        L6e:
            boolean r5 = r1.currentlyShowingOnlyActive
            if (r5 != r3) goto L83
            float r5 = r1.currentTransitionProgress
            r0 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 != 0) goto L7b
            goto L8a
        L7b:
            r0 = 0
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 != 0) goto L81
            goto L8a
        L81:
            if (r2 == r3) goto L8a
        L83:
            r1.currentlyShowingOnlyActive = r3
            com.android.systemui.media.controls.ui.view.MediaCarouselScrollHandler r2 = r1.mediaCarouselScrollHandler
            r2.resetTranslation(r4)
        L8a:
            r1.updatePageIndicatorAlpha()
        L8d:
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

    public final void updatePlayers(boolean z) {
        boolean isEnabled;
        MediaFlags mediaFlags;
        boolean z2;
        String str;
        TransitionLayout transitionLayout;
        TransitionLayout transitionLayout2;
        MediaControlPanel removePlayer$default;
        MediaFlags mediaFlags2 = this.mediaFlags;
        mediaFlags2.getClass();
        Flags.sceneContainer();
        ColorStateList valueOf = ColorStateList.valueOf(this.context.getColor(R.color.media_paging_indicator));
        PageIndicator pageIndicator = this.pageIndicator;
        ?? r5 = 0;
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
        MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(values, mediaCarouselScrollHandler.visibleMediaIndex);
        Set<Map.Entry> entrySet = ((LinkedHashMap) MediaPlayerData.mediaData).entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
        for (Map.Entry entry : entrySet) {
            arrayList.add(new Triple(entry.getKey(), ((MediaPlayerData.MediaSortKey) entry.getValue()).data, Boolean.valueOf(((MediaPlayerData.MediaSortKey) entry.getValue()).isSsMediaRec)));
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Triple triple = (Triple) it.next();
            String str2 = (String) triple.component1();
            MediaData mediaData = (MediaData) triple.component2();
            boolean booleanValue = ((Boolean) triple.component3()).booleanValue();
            DefaultConstructorMarker defaultConstructorMarker = null;
            Provider provider = this.mediaControlPanelFactory;
            if (booleanValue) {
                MediaPlayerData.INSTANCE.getClass();
                SmartspaceMediaData smartspaceMediaData = MediaPlayerData.smartspaceMediaData;
                removePlayer$default(this, str2, 8);
                if (smartspaceMediaData != null) {
                    String str3 = smartspaceMediaData.targetId;
                    isEnabled = Trace.isEnabled();
                    if (isEnabled) {
                        TraceUtilsKt.beginSlice("MediaCarouselController#addSmartspaceMediaRecommendations");
                    }
                    try {
                        if (MediaCarouselControllerKt.DEBUG) {
                            Log.d("MediaCarouselController", "Updating smartspace target in carousel");
                        }
                        MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) ((LinkedHashMap) MediaPlayerData.mediaData).get(str3);
                        if ((mediaSortKey2 != null ? (MediaControlPanel) MediaPlayerData.mediaPlayers.get(mediaSortKey2) : null) == null) {
                            String smartspaceMediaKey = MediaPlayerData.smartspaceMediaKey();
                            MediaCarouselControllerLogger mediaCarouselControllerLogger = this.debugLogger;
                            if (smartspaceMediaKey != null && (removePlayer$default = removePlayer$default(this, smartspaceMediaKey, 12)) != null) {
                                mediaCarouselControllerLogger.logPotentialMemoryLeak(smartspaceMediaKey);
                                removePlayer$default.onDestroy();
                            }
                            MediaControlPanel mediaControlPanel = (MediaControlPanel) provider.get();
                            RecommendationViewHolder.Companion companion = RecommendationViewHolder.Companion;
                            LayoutInflater from = LayoutInflater.from(this.context);
                            ViewGroup viewGroup = this.mediaContent;
                            companion.getClass();
                            View inflate = from.inflate(R.layout.media_recommendations, viewGroup, (boolean) r5);
                            inflate.setLayoutDirection(3);
                            RecommendationViewHolder recommendationViewHolder = new RecommendationViewHolder(inflate, defaultConstructorMarker);
                            mediaControlPanel.mRecommendationViewHolder = recommendationViewHolder;
                            MediaViewController mediaViewController = mediaControlPanel.mMediaViewController;
                            mediaViewController.attach(recommendationViewHolder.recommendations, MediaViewController.TYPE.RECOMMENDATION);
                            mediaViewController.configurationChangeListener = new MediaControlPanel$$ExternalSyntheticLambda2(mediaControlPanel, 1);
                            mediaControlPanel.mRecommendationViewHolder.recommendations.setOnLongClickListener(new MediaControlPanel$$ExternalSyntheticLambda3(mediaControlPanel, 1));
                            mediaViewController.sizeChangedListener = new MediaCarouselController$addSmartspaceMediaRecommendations$1$3(this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                            RecommendationViewHolder recommendationViewHolder2 = mediaControlPanel.mRecommendationViewHolder;
                            if (recommendationViewHolder2 != null && (transitionLayout2 = recommendationViewHolder2.recommendations) != null) {
                                transitionLayout2.setLayoutParams(layoutParams);
                            }
                            if (mediaControlPanel.mRecommendationViewHolder != null) {
                                throw null;
                            }
                            MediaPlayerData.shouldPrioritizeSs = r5;
                            MediaControlPanel removeMediaPlayer = MediaPlayerData.removeMediaPlayer(str3, r5);
                            if (removeMediaPlayer == null) {
                                throw null;
                            }
                            if (removeMediaPlayer.equals(mediaControlPanel)) {
                                throw null;
                            }
                            if (mediaCarouselControllerLogger != null) {
                                mediaCarouselControllerLogger.logPotentialMemoryLeak(str3);
                            }
                            removeMediaPlayer.onDestroy();
                            throw null;
                        }
                        mediaFlags2.isPersistentSsCardEnabled();
                        Log.w("MediaCarouselController", "Skip adding smartspace target in carousel");
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    } finally {
                    }
                }
                mediaFlags = mediaFlags2;
            } else {
                MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
                mediaPlayerData.getClass();
                Map map = MediaPlayerData.mediaData;
                MediaPlayerData.MediaSortKey mediaSortKey3 = (MediaPlayerData.MediaSortKey) ((LinkedHashMap) map).get(str2);
                boolean z3 = mediaSortKey3 != null ? mediaSortKey3.isSsReactivated : r5;
                if (z) {
                    removePlayer$default(this, str2, 8);
                }
                isEnabled = Trace.isEnabled();
                if (isEnabled) {
                    TraceUtilsKt.beginSlice("MediaCarouselController#addOrUpdatePlayer");
                }
                try {
                    MediaPlayerData.MediaSortKey mediaSortKey4 = (MediaPlayerData.MediaSortKey) ((LinkedHashMap) map).get(str2);
                    MediaControlPanel mediaControlPanel2 = mediaSortKey4 != null ? (MediaControlPanel) MediaPlayerData.mediaPlayers.get(mediaSortKey4) : null;
                    MediaPlayerData.MediaSortKey mediaSortKey5 = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(MediaPlayerData.visibleMediaPlayers.values(), mediaCarouselScrollHandler.visibleMediaIndex);
                    if (mediaControlPanel2 == null) {
                        MediaControlPanel mediaControlPanel3 = (MediaControlPanel) provider.get();
                        Flags.sceneContainer();
                        MediaViewHolder.Companion companion2 = MediaViewHolder.Companion;
                        LayoutInflater from2 = LayoutInflater.from(this.context);
                        ViewGroup viewGroup2 = this.mediaContent;
                        companion2.getClass();
                        View inflate2 = from2.inflate(R.layout.media_session_view, viewGroup2, (boolean) r5);
                        inflate2.setLayerType(2, null);
                        inflate2.setLayoutDirection(3);
                        MediaViewHolder mediaViewHolder = new MediaViewHolder(inflate2);
                        mediaViewHolder.seekBar.setLayoutDirection(r5);
                        mediaControlPanel3.attachPlayer(mediaViewHolder);
                        MediaViewController mediaViewController2 = mediaControlPanel3.mMediaViewController;
                        mediaViewController2.sizeChangedListener = new MediaCarouselController$addOrUpdatePlayer$1$1(this);
                        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
                        MediaViewHolder mediaViewHolder2 = mediaControlPanel3.mMediaViewHolder;
                        if (mediaViewHolder2 != null && (transitionLayout = mediaViewHolder2.player) != null) {
                            transitionLayout.setLayoutParams(layoutParams2);
                        }
                        mediaControlPanel3.bindPlayer(mediaData, str2);
                        boolean z4 = (mediaCarouselScrollHandler.visibleToUser && this.currentlyExpanded) ? true : r5;
                        SeekBarViewModel seekBarViewModel = mediaControlPanel3.mSeekBarViewModel;
                        seekBarViewModel.getClass();
                        seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$listening$1(seekBarViewModel, z4));
                        mediaFlags = mediaFlags2;
                        mediaPlayerData.addMediaPlayer(str2, mediaData, mediaControlPanel3, this.systemClock, z3, this.debugLogger);
                        updateViewControllerToState(mediaViewController2, true);
                        if ((this.shouldScrollToKey && Intrinsics.areEqual(mediaData.isPlaying, Boolean.TRUE)) || (!this.shouldScrollToKey && mediaData.active)) {
                            reorderAllPlayers(mediaSortKey5, str2);
                        }
                        z2 = true;
                    } else {
                        mediaFlags = mediaFlags2;
                        z2 = true;
                        mediaControlPanel2.bindPlayer(mediaData, str2);
                        mediaPlayerData.addMediaPlayer(str2, mediaData, mediaControlPanel2, this.systemClock, z3, this.debugLogger);
                        SmartspaceMediaData smartspaceMediaData2 = MediaPlayerData.smartspaceMediaData;
                        if (smartspaceMediaData2 == null || (str = smartspaceMediaData2.packageName) == null) {
                            str = new String();
                        }
                        if (this.visualStabilityProvider.isReorderingAllowed || (this.shouldScrollToKey && Intrinsics.areEqual(mediaData.isPlaying, Boolean.TRUE) && str.equals(mediaData.packageName))) {
                            reorderAllPlayers(mediaSortKey5, str2);
                        }
                    }
                    updatePageIndicator$2();
                    mediaCarouselScrollHandler.onPlayersChanged();
                    UniqueObjectHostViewKt.setRequiresRemeasuring(this.mediaFrame, z2);
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                } finally {
                }
            }
            if (z) {
                reorderAllPlayers(mediaSortKey, null);
            }
            mediaFlags2 = mediaFlags;
            r5 = 0;
        }
    }

    public final void updateSeekbarListening(boolean z) {
        this.mediaFlags.getClass();
        Flags.sceneContainer();
        MediaPlayerData.INSTANCE.getClass();
        for (MediaControlPanel mediaControlPanel : MediaPlayerData.mediaPlayers.values()) {
            boolean z2 = z && this.currentlyExpanded;
            SeekBarViewModel seekBarViewModel = mediaControlPanel.mSeekBarViewModel;
            seekBarViewModel.getClass();
            seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$listening$1(seekBarViewModel, z2));
        }
    }

    public final void updateViewControllerToState(MediaViewController mediaViewController, boolean z) {
        int i = this.currentStartLocation;
        int i2 = this.currentEndLocation;
        float f = this.currentTransitionProgress;
        int i3 = MediaViewController.$r8$clinit;
        mediaViewController.setCurrentState(i, i2, f, z, false);
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
