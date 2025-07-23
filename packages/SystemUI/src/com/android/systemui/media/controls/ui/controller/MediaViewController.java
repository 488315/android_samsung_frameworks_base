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
import com.android.systemui.R;
import com.android.systemui.media.controls.ui.view.GutsViewHolder;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import com.android.systemui.media.controls.ui.view.MediaViewHolder;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MediaViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long GUTS_ANIMATION_DURATION;
    public boolean animateNextStateChange;
    public long animationDelay;
    public long animationDuration;
    public ConstraintSet collapsedLayout;
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
    public boolean isFontUpdateAllowed;
    public boolean isGutsVisible;
    public final TransitionLayoutController layoutController;
    public final MediaViewLogger logger;
    public final DelayableExecutor mainExecutor;
    public final MeasurementOutput measurement;
    public final MediaHostStatesManager mediaHostStatesManager;
    public FunctionReferenceImpl sizeChangedListener;
    public final MediaViewController$stateCallback$1 stateCallback;
    public final CacheKey tmpKey;
    public final TransitionViewState tmpState;
    public final TransitionViewState tmpState2;
    public final TransitionViewState tmpState3;
    public TransitionLayout transitionLayout;
    public final Map viewStates;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        GUTS_ANIMATION_DURATION = 234L;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.android.systemui.media.controls.ui.controller.MediaViewController$configurationListener$1, java.lang.Object] */
    public MediaViewController(Context context, ConfigurationController configurationController, MediaHostStatesManager mediaHostStatesManager, MediaViewLogger mediaViewLogger, SeekBarViewModel seekBarViewModel, DelayableExecutor delayableExecutor, GlobalSettings globalSettings) {
        this.context = context;
        this.configurationController = configurationController;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.logger = mediaViewLogger;
        this.mainExecutor = delayableExecutor;
        this.globalSettings = globalSettings;
        TransitionLayoutController transitionLayoutController = new TransitionLayoutController();
        this.layoutController = transitionLayoutController;
        this.measurement = new MeasurementOutput(0, 0);
        this.viewStates = new LinkedHashMap();
        this.currentEndLocation = -1;
        this.currentStartLocation = -1;
        this.currentTransitionProgress = 1.0f;
        this.tmpState = new TransitionViewState();
        this.tmpState2 = new TransitionViewState();
        this.tmpState3 = new TransitionViewState();
        this.tmpKey = new CacheKey(0, 0, 0.0f, false, 15, null);
        this.isFontUpdateAllowed = true;
        new Object(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$scrubbingChangeListener$1
        };
        new Object(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$enabledChangeListener$1
        };
        new Object(this) { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$seekbarDescriptionListener$1
        };
        ?? r8 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$configurationListener$1
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
                }
            }
        };
        this.configurationListener = r8;
        this.stateCallback = new MediaViewController$stateCallback$1(this);
        this.collapsedLayout = new ConstraintSet();
        this.expandedLayout = new ConstraintSet();
        mediaHostStatesManager.controllers.add(this);
        transitionLayoutController.setSizeChangedListener(new Function2() { // from class: com.android.systemui.media.controls.ui.controller.MediaViewController$$ExternalSyntheticLambda0
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v3, types: [kotlin.jvm.functions.Function0] */
            /* JADX WARN: Type inference failed for: r0v5 */
            /* JADX WARN: Type inference failed for: r0v6 */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                int intValue = ((Integer) obj).intValue();
                int intValue2 = ((Integer) obj2).intValue();
                MediaViewController mediaViewController = MediaViewController.this;
                mediaViewController.currentWidth = intValue;
                mediaViewController.currentHeight = intValue2;
                FunctionReferenceImpl functionReferenceImpl = mediaViewController.sizeChangedListener;
                ?? r0 = functionReferenceImpl;
                if (functionReferenceImpl == null) {
                    r0 = 0;
                }
                r0.invoke();
                return Unit.INSTANCE;
            }
        });
        ((ConfigurationControllerImpl) configurationController).addCallback(r8);
    }

    public static void calculateWidgetGroupAlphaForSquishiness(Set set, float f, TransitionViewState transitionViewState, float f2) {
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
        return copy$default;
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

    public final TransitionViewState obtainViewState(MediaHostState mediaHostState, boolean z) {
        TransitionViewState calculateViewState;
        if (mediaHostState == null || mediaHostState.getMeasurementInput() == null) {
            return null;
        }
        boolean z2 = this.isGutsVisible;
        MeasurementInput measurementInput = mediaHostState.getMeasurementInput();
        int heightMeasureSpec = measurementInput != null ? measurementInput.getHeightMeasureSpec() : 0;
        CacheKey cacheKey = this.tmpKey;
        cacheKey.heightMeasureSpec = heightMeasureSpec;
        MeasurementInput measurementInput2 = mediaHostState.getMeasurementInput();
        cacheKey.widthMeasureSpec = measurementInput2 != null ? measurementInput2.getWidthMeasureSpec() : 0;
        cacheKey.expansion = mediaHostState.getExpansion();
        cacheKey.gutsVisible = z2;
        TransitionViewState transitionViewState = (TransitionViewState) ((LinkedHashMap) this.viewStates).get(cacheKey);
        if (transitionViewState != null) {
            return (mediaHostState.getSquishFraction() > 1.0f || z) ? transitionViewState : squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(transitionViewState, mediaHostState.getSquishFraction());
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
            transitionLayout.getClass();
            MeasurementInput measurementInput3 = mediaHostState.getMeasurementInput();
            measurementInput3.getClass();
            calculateViewState = transitionLayout.calculateViewState(measurementInput3, mediaHostState.getExpansion() > 0.0f ? this.expandedLayout : this.collapsedLayout, new TransitionViewState());
            MediaViewHolder.Companion.getClass();
            Set set = MediaViewHolder.controlsIds;
            GutsViewHolder.Companion.getClass();
            Set set2 = GutsViewHolder.ids;
            Iterator it = set.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WidgetState widgetState = calculateViewState.getWidgetStates().get(Integer.valueOf(((Number) it.next()).intValue()));
                if (widgetState != null) {
                    widgetState.setAlpha(this.isGutsVisible ? 0.0f : widgetState.getAlpha());
                    widgetState.setGone(this.isGutsVisible ? true : widgetState.getGone());
                }
            }
            Iterator it2 = set2.iterator();
            while (it2.hasNext()) {
                WidgetState widgetState2 = calculateViewState.getWidgetStates().get(Integer.valueOf(((Number) it2.next()).intValue()));
                if (widgetState2 != null) {
                    widgetState2.setAlpha(this.isGutsVisible ? widgetState2.getAlpha() : 0.0f);
                    widgetState2.setGone(this.isGutsVisible ? widgetState2.getGone() : true);
                }
            }
            this.viewStates.put(cacheKey2, calculateViewState);
        } else {
            MediaHost.MediaHostStateHolder copy = mediaHostState.copy();
            copy.setExpansion(0.0f);
            TransitionViewState obtainViewState = obtainViewState(copy, z);
            MediaHost.MediaHostStateHolder copy2 = mediaHostState.copy();
            copy2.setExpansion(1.0f);
            calculateViewState = TransitionLayoutController.getInterpolatedState$default(this.layoutController, obtainViewState, obtainViewState(copy2, z), mediaHostState.getExpansion(), null, 8, null);
        }
        return (mediaHostState.getSquishFraction() > 1.0f || z) ? calculateViewState : squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(calculateViewState, mediaHostState.getSquishFraction());
    }

    public final void refreshState() {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaViewController#refreshState");
        }
        try {
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
        } finally {
        }
    }

    public final void setBackgroundHeights(int i) {
        MediaViewHolder.Companion.getClass();
        Iterator it = MediaViewHolder.backgroundIds.iterator();
        while (it.hasNext()) {
            this.expandedLayout.getConstraint(((Number) it.next()).intValue()).layout.mHeight = i;
        }
    }

    public final void setCollapsedLayout(ConstraintSet constraintSet) {
        this.collapsedLayout = constraintSet;
    }

    public final void setCurrentState(int i, int i2, float f, boolean z, boolean z2) {
        MediaHostStatesManager mediaHostStatesManager = this.mediaHostStatesManager;
        TransitionLayoutController transitionLayoutController = this.layoutController;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaViewController#setCurrentState");
        }
        try {
            if (this.currentEndLocation != i2) {
                this.currentEndLocation = i2;
            }
            this.currentStartLocation = i;
            this.currentTransitionProgress = f;
            boolean z3 = this.animateNextStateChange && !z;
            MediaHostState mediaHostState = (MediaHostState) ((LinkedHashMap) mediaHostStatesManager.mediaHostStates).get(Integer.valueOf(i2));
            if (mediaHostState == null) {
                if (isEnabled) {
                    return;
                } else {
                    return;
                }
            }
            MediaHostState mediaHostState2 = (MediaHostState) ((LinkedHashMap) mediaHostStatesManager.mediaHostStates).get(Integer.valueOf(i));
            TransitionViewState obtainViewState = obtainViewState(mediaHostState, z2);
            if (obtainViewState == null) {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            TransitionViewState updateViewStateSize = updateViewStateSize(obtainViewState, i2, this.tmpState2);
            updateViewStateSize.getClass();
            transitionLayoutController.setMeasureState(updateViewStateSize);
            this.animateNextStateChange = false;
            TransitionLayout transitionLayout = this.transitionLayout;
            MediaViewLogger mediaViewLogger = this.logger;
            if (transitionLayout == null) {
                mediaViewLogger.logMediaLocation(i, i2, "setCurrentState: view not bound");
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            TransitionViewState updateViewStateSize2 = updateViewStateSize(obtainViewState(mediaHostState2, z2), i, this.tmpState3);
            boolean visible = mediaHostState.getVisible();
            TransitionViewState transitionViewState = this.tmpState;
            if (visible) {
                if (mediaHostState2 != null && !mediaHostState2.getVisible()) {
                    updateViewStateSize = transitionLayoutController.getGoneState(updateViewStateSize, mediaHostState.getDisappearParameters(), 1.0f - f, transitionViewState);
                } else if (f != 1.0f && updateViewStateSize2 != null) {
                    updateViewStateSize = f == 0.0f ? updateViewStateSize2 : transitionLayoutController.getInterpolatedState(updateViewStateSize2, updateViewStateSize, f, transitionViewState);
                }
            } else if (updateViewStateSize2 != null && mediaHostState2 != null && mediaHostState2.getVisible()) {
                updateViewStateSize = transitionLayoutController.getGoneState(updateViewStateSize2, mediaHostState2.getDisappearParameters(), f, transitionViewState);
            }
            mediaViewLogger.logMediaSize(updateViewStateSize.getWidth(), updateViewStateSize.getHeight(), "setCurrentState " + i + " -> " + i2 + " (progress " + f + ")");
            this.layoutController.setState(updateViewStateSize, z, z3, this.animationDuration, this.animationDelay, z2);
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
        boolean z = false;
        if (measurementOutput != null) {
            if (copy.getMeasureHeight() != measurementOutput.getMeasuredHeight() || copy.getMeasureWidth() != measurementOutput.getMeasuredWidth()) {
                copy.setMeasureHeight(Math.max(measurementOutput.getMeasuredHeight(), copy.getMeasureHeight()));
                copy.setMeasureWidth(Math.max(measurementOutput.getMeasuredWidth(), copy.getMeasureWidth()));
                z = true;
            }
            if (z) {
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
            }
        }
        if (z && mediaHostState != null && mediaHostState.getSquishFraction() <= 1.0f) {
            copy = squishViewState$frameworks__base__packages__SystemUI__android_common__SystemUI_core(copy, mediaHostState.getSquishFraction());
        }
        this.logger.logMediaSize(copy.getWidth(), copy.getHeight(), "update to carousel");
        return copy;
    }

    private static /* synthetic */ void getTransitionLayout$annotations() {
    }
}
