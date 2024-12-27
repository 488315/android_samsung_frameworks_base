package com.android.systemui.media.controls.ui.controller;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Trace;
import android.util.MathUtils;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.ui.view.GutsViewHolder;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.view.RecommendationViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.animation.TransitionLayoutController;
import com.android.systemui.util.animation.TransitionViewState;
import com.android.systemui.util.animation.WidgetState;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long GUTS_ANIMATION_DURATION;
    public boolean animateNextStateChange;
    public long animationDelay;
    public long animationDuration;
    public ConstraintSet collapsedLayout;
    public MediaControlPanel$$ExternalSyntheticLambda2 configurationChangeListener;
    public final ConfigurationController configurationController;
    public final MediaViewController$configurationListener$1 configurationListener;
    public final Context context;
    public int currentEndLocation;
    public int currentHeight;
    public int currentStartLocation;
    public float currentTransitionProgress;
    public int currentWidth;
    public ConstraintSet expandedLayout;
    public boolean firstRefresh = true;
    public final GlobalSettings globalSettings;
    public boolean isGutsVisible;
    public final TransitionLayoutController layoutController;
    public final MediaViewLogger logger;
    public final DelayableExecutor mainExecutor;
    public final MeasurementOutput measurement;
    public final MediaFlags mediaFlags;
    public final MediaHostStatesManager mediaHostStatesManager;
    public final int nextNotVisibleValue;
    public final int prevNotVisibleValue;
    public Function0 sizeChangedListener;
    public final MediaViewController$stateCallback$1 stateCallback;
    public final CacheKey tmpKey;
    public final TransitionViewState tmpState;
    public final TransitionViewState tmpState2;
    public final TransitionViewState tmpState3;
    public TransitionLayout transitionLayout;
    public TYPE type;
    public final Map viewStates;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TYPE {
        public static final /* synthetic */ TYPE[] $VALUES;
        public static final TYPE PLAYER;
        public static final TYPE RECOMMENDATION;

        static {
            TYPE type = new TYPE("PLAYER", 0);
            PLAYER = type;
            TYPE type2 = new TYPE("RECOMMENDATION", 1);
            RECOMMENDATION = type2;
            TYPE[] typeArr = {type, type2};
            $VALUES = typeArr;
            EnumEntriesKt.enumEntries(typeArr);
        }

        private TYPE(String str, int i) {
        }

        public static TYPE valueOf(String str) {
            return (TYPE) Enum.valueOf(TYPE.class, str);
        }

        public static TYPE[] values() {
            return (TYPE[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TYPE.values().length];
            try {
                iArr[TYPE.PLAYER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TYPE.RECOMMENDATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        GUTS_ANIMATION_DURATION = 234L;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v12, types: [com.android.systemui.media.controls.ui.controller.MediaViewController$configurationListener$1, java.lang.Object] */
    public MediaViewController(Context context, ConfigurationController configurationController, MediaHostStatesManager mediaHostStatesManager, MediaViewLogger mediaViewLogger, SeekBarViewModel seekBarViewModel, DelayableExecutor delayableExecutor, MediaFlags mediaFlags, GlobalSettings globalSettings) {
        this.context = context;
        this.configurationController = configurationController;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.logger = mediaViewLogger;
        this.mainExecutor = delayableExecutor;
        this.mediaFlags = mediaFlags;
        this.globalSettings = globalSettings;
        TransitionLayoutController transitionLayoutController = new TransitionLayoutController();
        this.layoutController = transitionLayoutController;
        this.measurement = new MeasurementOutput(0, 0);
        this.type = TYPE.PLAYER;
        this.viewStates = new LinkedHashMap();
        this.currentEndLocation = -1;
        this.currentStartLocation = -1;
        this.currentTransitionProgress = 1.0f;
        this.tmpState = new TransitionViewState();
        this.tmpState2 = new TransitionViewState();
        this.tmpState3 = new TransitionViewState();
        this.tmpKey = new CacheKey(0, 0, 0.0f, false, 15, null);
        new SeekBarViewModel.ScrubbingChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$scrubbingChangeListener$1
            @Override // com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.ScrubbingChangeListener
            public final void onScrubbingChanged(boolean z) {
                MediaViewController.this.mediaFlags.getClass();
                Flags.sceneContainer();
            }
        };
        new SeekBarViewModel.EnabledChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$enabledChangeListener$1
            @Override // com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel.EnabledChangeListener
            public final void onEnabledChanged(boolean z) {
                MediaViewController.this.mediaFlags.getClass();
                Flags.sceneContainer();
            }
        };
        ?? r11 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$configurationListener$1
            public int lastOrientation = -1;

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                if (configuration != null) {
                    MediaViewController mediaViewController = MediaViewController.this;
                    TransitionLayout transitionLayout = mediaViewController.transitionLayout;
                    if (transitionLayout == null || transitionLayout.getRawLayoutDirection() != configuration.getLayoutDirection()) {
                        TransitionLayout transitionLayout2 = mediaViewController.transitionLayout;
                        if (transitionLayout2 != null) {
                            transitionLayout2.setLayoutDirection(configuration.getLayoutDirection());
                        }
                        mediaViewController.refreshState();
                    }
                    int i = configuration.orientation;
                    if (this.lastOrientation != i) {
                        this.lastOrientation = i;
                        mediaViewController.setBackgroundHeights(mediaViewController.context.getResources().getDimensionPixelSize(R.dimen.qs_media_session_height_expanded));
                    }
                    mediaViewController.mediaFlags.getClass();
                    Flags.sceneContainer();
                    MediaControlPanel$$ExternalSyntheticLambda2 mediaControlPanel$$ExternalSyntheticLambda2 = mediaViewController.configurationChangeListener;
                    if (mediaControlPanel$$ExternalSyntheticLambda2 != null) {
                        mediaControlPanel$$ExternalSyntheticLambda2.invoke();
                        mediaViewController.refreshState();
                    }
                }
            }
        };
        this.configurationListener = r11;
        this.stateCallback = new MediaViewController$stateCallback$1(this);
        this.collapsedLayout = new ConstraintSet();
        this.expandedLayout = new ConstraintSet();
        mediaHostStatesManager.controllers.add(this);
        transitionLayoutController.setSizeChangedListener(new Function2() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int intValue = ((Number) obj).intValue();
                int intValue2 = ((Number) obj2).intValue();
                MediaViewController mediaViewController = MediaViewController.this;
                mediaViewController.currentWidth = intValue;
                mediaViewController.currentHeight = intValue2;
                Function0 function0 = mediaViewController.sizeChangedListener;
                if (function0 == null) {
                    function0 = null;
                }
                function0.invoke();
                return Unit.INSTANCE;
            }
        });
        ((ConfigurationControllerImpl) configurationController).addCallback(r11);
    }

    public static float calculateWidgetGroupAlphaForSquishiness(Set set, float f, TransitionViewState transitionViewState, float f2) {
        int measureHeight = transitionViewState.getMeasureHeight();
        float measureHeight2 = transitionViewState.getMeasureHeight();
        Set set2 = set;
        Iterator it = set2.iterator();
        float f3 = 0.0f;
        while (it.hasNext()) {
            WidgetState widgetState = transitionViewState.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
            if (widgetState != null) {
                measureHeight2 = Float.min(measureHeight2, widgetState.getY());
                f3 = Float.max(f3, widgetState.getY() + widgetState.getHeight());
            }
        }
        if (f3 == f) {
            f3 = (float) (f - ((f3 - measureHeight2) * 0.2d));
        }
        Iterator it2 = set2.iterator();
        while (it2.hasNext()) {
            WidgetState widgetState2 = transitionViewState.getWidgetStates().get(Integer.valueOf(((Number) it2.next()).intValue()));
            if (widgetState2 != null && widgetState2.getAlpha() != 0.0f) {
                float f4 = measureHeight;
                float f5 = f3 / f4;
                MediaCarouselController.Companion.getClass();
                widgetState2.setAlpha(MediaCarouselController.TRANSFORM_BEZIER.getInterpolation(MathUtils.constrain((f2 - f5) / ((f / f4) - f5), 0.0f, 1.0f)));
            }
        }
        return measureHeight2;
    }

    public static TransitionViewState squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(TransitionViewState transitionViewState, float f) {
        TransitionViewState copy$default = TransitionViewState.copy$default(transitionViewState, null, 1, null);
        int measureHeight = (int) (copy$default.getMeasureHeight() * f);
        copy$default.setHeight(measureHeight);
        MediaViewHolder.Companion.getClass();
        Iterator it = MediaViewHolder.backgroundIds.iterator();
        while (it.hasNext()) {
            WidgetState widgetState = copy$default.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
            if (widgetState != null) {
                widgetState.setHeight(measureHeight);
            }
        }
        MediaViewHolder.Companion.getClass();
        calculateWidgetGroupAlphaForSquishiness(MediaViewHolder.expandedBottomActionIds, copy$default.getMeasureHeight(), copy$default, f);
        calculateWidgetGroupAlphaForSquishiness(MediaViewHolder.detailIds, copy$default.getMeasureHeight(), copy$default, f);
        RecommendationViewHolder.Companion.getClass();
        calculateWidgetGroupAlphaForSquishiness(RecommendationViewHolder.mediaContainersIds, calculateWidgetGroupAlphaForSquishiness(RecommendationViewHolder.mediaTitlesAndSubtitlesIds, copy$default.getMeasureHeight(), copy$default, f), copy$default, f);
        return copy$default;
    }

    public final void attach(TransitionLayout transitionLayout, TYPE type) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaViewController#attach");
        }
        try {
            loadLayoutForType(type);
            MediaViewLogger mediaViewLogger = this.logger;
            int i = this.currentStartLocation;
            int i2 = this.currentEndLocation;
            mediaViewLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaViewLogger$logMediaLocation$2 mediaViewLogger$logMediaLocation$2 = MediaViewLogger$logMediaLocation$2.INSTANCE;
            LogBuffer logBuffer = mediaViewLogger.buffer;
            LogMessage obtain = logBuffer.obtain("MediaView", logLevel, mediaViewLogger$logMediaLocation$2, null);
            ((LogMessageImpl) obtain).str1 = "attach " + type;
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.int1 = i;
            logMessageImpl.int2 = i2;
            logBuffer.commit(obtain);
            this.transitionLayout = transitionLayout;
            this.layoutController.attach(transitionLayout);
            int i3 = this.currentEndLocation;
            if (i3 == -1) {
                if (isEnabled) {
                    return;
                } else {
                    return;
                }
            }
            setCurrentState(this.currentStartLocation, i3, this.currentTransitionProgress, true, false);
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    public AnimatorSet loadAnimator(Context context, int i, Interpolator interpolator, View... viewArr) {
        ArrayList arrayList = new ArrayList();
        for (View view : viewArr) {
            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, i);
            animatorSet.getChildAnimations().get(0).setInterpolator(interpolator);
            animatorSet.setTarget(view);
            arrayList.add(animatorSet);
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(arrayList);
        return animatorSet2;
    }

    public final void loadLayoutForType(TYPE type) {
        this.type = type;
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            this.collapsedLayout.load(this.context, R.xml.media_session_collapsed);
            this.expandedLayout.load(this.context, R.xml.media_session_expanded);
        } else if (i == 2) {
            this.collapsedLayout.load(this.context, R.xml.media_recommendations_collapsed);
            this.expandedLayout.load(this.context, R.xml.media_recommendations_expanded);
        }
        refreshState();
    }

    public final TransitionViewState obtainViewState(MediaHostState mediaHostState, boolean z) {
        Set set;
        this.mediaFlags.getClass();
        Flags.sceneContainer();
        TransitionViewState transitionViewState = null;
        if (mediaHostState != null && mediaHostState.getMeasurementInput() != null) {
            boolean z2 = this.isGutsVisible;
            MeasurementInput measurementInput = mediaHostState.getMeasurementInput();
            int heightMeasureSpec = measurementInput != null ? measurementInput.getHeightMeasureSpec() : 0;
            CacheKey cacheKey = this.tmpKey;
            cacheKey.heightMeasureSpec = heightMeasureSpec;
            MeasurementInput measurementInput2 = mediaHostState.getMeasurementInput();
            cacheKey.widthMeasureSpec = measurementInput2 != null ? measurementInput2.getWidthMeasureSpec() : 0;
            cacheKey.expansion = mediaHostState.getExpansion();
            cacheKey.gutsVisible = z2;
            TransitionViewState transitionViewState2 = (TransitionViewState) ((LinkedHashMap) this.viewStates).get(cacheKey);
            if (transitionViewState2 != null) {
                return (mediaHostState.getSquishFraction() > 1.0f || z) ? transitionViewState2 : squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(transitionViewState2, mediaHostState.getSquishFraction());
            }
            CacheKey cacheKey2 = new CacheKey(cacheKey.widthMeasureSpec, cacheKey.heightMeasureSpec, cacheKey.expansion, cacheKey.gutsVisible);
            if (this.transitionLayout == null) {
                return null;
            }
            if (mediaHostState.getExpansion() == 0.0f || mediaHostState.getExpansion() == 1.0f) {
                if (mediaHostState.getExpansion() == 1.0f) {
                    setBackgroundHeights(mediaHostState.getExpandedMatchesParentHeight() ? 0 : this.context.getResources().getDimensionPixelSize(R.dimen.qs_media_session_height_expanded));
                }
                TransitionLayout transitionLayout = this.transitionLayout;
                Intrinsics.checkNotNull(transitionLayout);
                MeasurementInput measurementInput3 = mediaHostState.getMeasurementInput();
                Intrinsics.checkNotNull(measurementInput3);
                transitionViewState = transitionLayout.calculateViewState(measurementInput3, mediaHostState.getExpansion() > 0.0f ? this.expandedLayout : this.collapsedLayout, new TransitionViewState());
                int i = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
                if (i == 1) {
                    MediaViewHolder.Companion.getClass();
                    set = MediaViewHolder.controlsIds;
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    RecommendationViewHolder.Companion.getClass();
                    set = RecommendationViewHolder.controlsIds;
                }
                GutsViewHolder.Companion.getClass();
                Set set2 = GutsViewHolder.ids;
                Iterator it = set.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    WidgetState widgetState = transitionViewState.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
                    if (widgetState != null) {
                        widgetState.setAlpha(this.isGutsVisible ? 0.0f : widgetState.getAlpha());
                        widgetState.setGone(this.isGutsVisible ? true : widgetState.getGone());
                    }
                }
                Iterator it2 = set2.iterator();
                while (it2.hasNext()) {
                    WidgetState widgetState2 = transitionViewState.getWidgetStates().get(Integer.valueOf(((Number) it2.next()).intValue()));
                    if (widgetState2 != null) {
                        widgetState2.setAlpha(this.isGutsVisible ? widgetState2.getAlpha() : 0.0f);
                        widgetState2.setGone(this.isGutsVisible ? widgetState2.getGone() : true);
                    }
                }
                this.viewStates.put(cacheKey2, transitionViewState);
            } else {
                MediaHost.MediaHostStateHolder copy = mediaHostState.copy();
                copy.setExpansion(0.0f);
                TransitionViewState obtainViewState = obtainViewState(copy, z);
                MediaHost.MediaHostStateHolder copy2 = mediaHostState.copy();
                copy2.setExpansion(1.0f);
                transitionViewState = TransitionLayoutController.getInterpolatedState$default(this.layoutController, obtainViewState, obtainViewState(copy2, z), mediaHostState.getExpansion(), null, 8, null);
            }
            if (mediaHostState.getSquishFraction() <= 1.0f && !z) {
                return squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(transitionViewState, mediaHostState.getSquishFraction());
            }
        }
        return transitionViewState;
    }

    public final void refreshState() {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaViewController#refreshState");
        }
        try {
            this.mediaFlags.getClass();
            Flags.sceneContainer();
            ((LinkedHashMap) this.viewStates).clear();
            if (this.firstRefresh) {
                Iterator it = ((LinkedHashMap) this.mediaHostStatesManager.mediaHostStates).entrySet().iterator();
                while (it.hasNext()) {
                    obtainViewState((MediaHostState) ((Map.Entry) it.next()).getValue(), false);
                }
                this.firstRefresh = false;
            }
            setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, true, false);
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public final void setBackgroundHeights(int i) {
        Set singleton;
        if (this.type == TYPE.PLAYER) {
            MediaViewHolder.Companion.getClass();
            singleton = MediaViewHolder.backgroundIds;
        } else {
            RecommendationViewHolder.Companion.getClass();
            singleton = Collections.singleton(Integer.valueOf(RecommendationViewHolder.backgroundId));
        }
        Iterator it = singleton.iterator();
        while (it.hasNext()) {
            this.expandedLayout.getConstraint(((Number) it.next()).intValue()).layout.mHeight = i;
        }
    }

    public final void setCollapsedLayout(ConstraintSet constraintSet) {
        this.collapsedLayout = constraintSet;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCurrentState(int r17, int r18, float r19, boolean r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.controller.MediaViewController.setCurrentState(int, int, float, boolean, boolean):void");
    }

    public final void setExpandedLayout(ConstraintSet constraintSet) {
        this.expandedLayout = constraintSet;
    }

    public final TransitionViewState updateViewStateSize(TransitionViewState transitionViewState, int i, TransitionViewState transitionViewState2) {
        TransitionViewState copy;
        if (transitionViewState == null || (copy = transitionViewState.copy(transitionViewState2)) == null) {
            return null;
        }
        MediaHostStatesManager mediaHostStatesManager = this.mediaHostStatesManager;
        MediaHostState mediaHostState = (MediaHostState) ((LinkedHashMap) mediaHostStatesManager.mediaHostStates).get(Integer.valueOf(i));
        MeasurementOutput measurementOutput = (MeasurementOutput) ((LinkedHashMap) mediaHostStatesManager.carouselSizes).get(Integer.valueOf(i));
        if (measurementOutput != null && (copy.getMeasureHeight() != measurementOutput.getMeasuredHeight() || copy.getMeasureWidth() != measurementOutput.getMeasuredWidth())) {
            copy.setMeasureHeight(Math.max(measurementOutput.getMeasuredHeight(), copy.getMeasureHeight()));
            copy.setMeasureWidth(Math.max(measurementOutput.getMeasuredWidth(), copy.getMeasureWidth()));
            copy.setHeight(copy.getMeasureHeight());
            copy.setWidth(copy.getMeasureWidth());
            MediaViewHolder.Companion.getClass();
            Iterator it = MediaViewHolder.backgroundIds.iterator();
            while (it.hasNext()) {
                WidgetState widgetState = copy.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
                if (widgetState != null) {
                    widgetState.setHeight(copy.getHeight());
                    widgetState.setWidth(copy.getWidth());
                }
            }
            if (mediaHostState != null && mediaHostState.getSquishFraction() <= 1.0f) {
                copy = squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(copy, mediaHostState.getSquishFraction());
            }
        }
        this.logger.logMediaSize(copy.getWidth(), copy.getHeight(), "update to carousel");
        return copy;
    }

    private static /* synthetic */ void getTransitionLayout$annotations() {
    }
}
