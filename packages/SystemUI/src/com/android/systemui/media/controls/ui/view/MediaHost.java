package com.android.systemui.media.controls.ui.view;

import android.graphics.Rect;
import android.util.ArraySet;
import android.view.View;
import android.view.ViewGroupOverlay;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger;
import com.android.systemui.media.controls.ui.controller.MediaCarouselControllerLogger$$ExternalSyntheticLambda0;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final class MediaHost implements MediaHostState {
    public final MediaCarouselControllerLogger debugLogger;
    public UniqueObjectHostView hostView;
    public boolean inited;
    public boolean listeningToMediaData;
    public final MediaCarouselController mediaCarouselController;
    public final MediaDataManager mediaDataManager;
    public final MediaHierarchyManager mediaHierarchyManager;
    public final MediaHostStatesManager mediaHostStatesManager;
    public final MediaHostStateHolder state;
    public int location = -1;
    public final ArraySet visibleChangedListeners = new ArraySet();
    public final int[] tmpLocationOnScreen = {0, 0};
    public final Rect currentBounds = new Rect();
    public final Rect currentClipping = new Rect();
    public final MediaHost$listener$1 listener = new MediaDataManager.Listener() { // from class: com.android.systemui.media.controls.ui.view.MediaHost$listener$1
        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(String str, boolean z) {
            this.this$0.updateViewVisibility();
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
        }
    };

    public final class MediaHostStateHolder implements MediaHostState {
        public MediaHost$$ExternalSyntheticLambda0 changedListener;
        public boolean disableScrolling;
        public DisappearParameters disappearParameters;
        public boolean expandedMatchesParentHeight;
        public float expansion;
        public boolean falsingProtectionNeeded;
        public int lastDisappearHash;
        public MeasurementInput measurementInput;
        public boolean showsOnlyActiveMedia;
        public float squishFraction = 1.0f;
        public boolean visible = true;

        public MediaHostStateHolder() {
            DisappearParameters disappearParameters = new DisappearParameters();
            this.disappearParameters = disappearParameters;
            this.lastDisappearHash = disappearParameters.hashCode();
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final MediaHostStateHolder copy() {
            MediaHostStateHolder mediaHostStateHolder = new MediaHostStateHolder();
            mediaHostStateHolder.setExpansion(this.expansion);
            boolean z = this.expandedMatchesParentHeight;
            if (z != mediaHostStateHolder.expandedMatchesParentHeight) {
                mediaHostStateHolder.expandedMatchesParentHeight = z;
                MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda0 = mediaHostStateHolder.changedListener;
                if (mediaHost$$ExternalSyntheticLambda0 != null) {
                    mediaHost$$ExternalSyntheticLambda0.invoke();
                }
            }
            float f = this.squishFraction;
            if (!Float.valueOf(f).equals(Float.valueOf(mediaHostStateHolder.squishFraction))) {
                mediaHostStateHolder.squishFraction = f;
                MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda02 = mediaHostStateHolder.changedListener;
                if (mediaHost$$ExternalSyntheticLambda02 != null) {
                    mediaHost$$ExternalSyntheticLambda02.invoke();
                }
            }
            boolean z2 = this.showsOnlyActiveMedia;
            if (!Boolean.valueOf(z2).equals(Boolean.valueOf(mediaHostStateHolder.showsOnlyActiveMedia))) {
                mediaHostStateHolder.showsOnlyActiveMedia = z2;
                MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda03 = mediaHostStateHolder.changedListener;
                if (mediaHost$$ExternalSyntheticLambda03 != null) {
                    mediaHost$$ExternalSyntheticLambda03.invoke();
                }
            }
            MeasurementInput measurementInput = this.measurementInput;
            mediaHostStateHolder.setMeasurementInput(measurementInput != null ? MeasurementInput.copy$default(measurementInput, 0, 0, 3, null) : null);
            boolean z3 = this.visible;
            if (mediaHostStateHolder.visible != z3) {
                mediaHostStateHolder.visible = z3;
                MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda04 = mediaHostStateHolder.changedListener;
                if (mediaHost$$ExternalSyntheticLambda04 != null) {
                    mediaHost$$ExternalSyntheticLambda04.invoke();
                }
            }
            DisappearParameters disappearParametersDeepCopy = this.disappearParameters.deepCopy();
            int iHashCode = disappearParametersDeepCopy.hashCode();
            if (!Integer.valueOf(mediaHostStateHolder.lastDisappearHash).equals(Integer.valueOf(iHashCode))) {
                mediaHostStateHolder.disappearParameters = disappearParametersDeepCopy;
                mediaHostStateHolder.lastDisappearHash = iHashCode;
                MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda05 = mediaHostStateHolder.changedListener;
                if (mediaHost$$ExternalSyntheticLambda05 != null) {
                    mediaHost$$ExternalSyntheticLambda05.invoke();
                }
            }
            boolean z4 = this.falsingProtectionNeeded;
            if (mediaHostStateHolder.falsingProtectionNeeded != z4) {
                mediaHostStateHolder.falsingProtectionNeeded = z4;
                MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda06 = mediaHostStateHolder.changedListener;
                if (mediaHost$$ExternalSyntheticLambda06 != null) {
                    mediaHost$$ExternalSyntheticLambda06.invoke();
                }
            }
            boolean z5 = this.disableScrolling;
            if (mediaHostStateHolder.disableScrolling != z5) {
                mediaHostStateHolder.disableScrolling = z5;
                MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda07 = mediaHostStateHolder.changedListener;
                if (mediaHost$$ExternalSyntheticLambda07 != null) {
                    mediaHost$$ExternalSyntheticLambda07.invoke();
                }
            }
            return mediaHostStateHolder;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof MediaHostState)) {
                return false;
            }
            MediaHostState mediaHostState = (MediaHostState) obj;
            return Objects.equals(this.measurementInput, mediaHostState.getMeasurementInput()) && this.expansion == mediaHostState.getExpansion() && this.squishFraction == mediaHostState.getSquishFraction() && this.showsOnlyActiveMedia == mediaHostState.getShowsOnlyActiveMedia() && this.visible == mediaHostState.getVisible() && this.falsingProtectionNeeded == mediaHostState.getFalsingProtectionNeeded() && this.disappearParameters.equals(mediaHostState.getDisappearParameters()) && this.disableScrolling == mediaHostState.getDisableScrolling();
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final boolean getDisableScrolling() {
            return this.disableScrolling;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final DisappearParameters getDisappearParameters() {
            return this.disappearParameters;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final boolean getExpandedMatchesParentHeight() {
            return this.expandedMatchesParentHeight;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final float getExpansion() {
            return this.expansion;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final boolean getFalsingProtectionNeeded() {
            return this.falsingProtectionNeeded;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final MeasurementInput getMeasurementInput() {
            return this.measurementInput;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final boolean getShowsOnlyActiveMedia() {
            return this.showsOnlyActiveMedia;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final float getSquishFraction() {
            return this.squishFraction;
        }

        @Override // com.android.systemui.media.controls.ui.view.MediaHostState
        public final boolean getVisible() {
            return this.visible;
        }

        public final int hashCode() {
            MeasurementInput measurementInput = this.measurementInput;
            return Boolean.hashCode(this.disableScrolling) + ((this.disappearParameters.hashCode() + ((TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.squishFraction, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.expansion, (measurementInput != null ? measurementInput.hashCode() : 0) * 31, 31), 31), 31, this.falsingProtectionNeeded), 31, this.showsOnlyActiveMedia) + (this.visible ? 1 : 2)) * 31)) * 31);
        }

        public final void setExpansion(float f) {
            if (Float.valueOf(f).equals(Float.valueOf(this.expansion))) {
                return;
            }
            this.expansion = f;
            MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda0 = this.changedListener;
            if (mediaHost$$ExternalSyntheticLambda0 != null) {
                mediaHost$$ExternalSyntheticLambda0.invoke();
            }
        }

        public final void setMeasurementInput(MeasurementInput measurementInput) {
            if (measurementInput == null || !measurementInput.equals(this.measurementInput)) {
                this.measurementInput = measurementInput;
                MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda0 = this.changedListener;
                if (mediaHost$$ExternalSyntheticLambda0 != null) {
                    mediaHost$$ExternalSyntheticLambda0.invoke();
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.media.controls.ui.view.MediaHost$listener$1] */
    public MediaHost(MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager, MediaCarouselController mediaCarouselController, MediaCarouselControllerLogger mediaCarouselControllerLogger) {
        this.state = mediaHostStateHolder;
        this.mediaHierarchyManager = mediaHierarchyManager;
        this.mediaDataManager = mediaDataManager;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.mediaCarouselController = mediaCarouselController;
        this.debugLogger = mediaCarouselControllerLogger;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final MediaHostStateHolder copy() {
        return this.state.copy();
    }

    public final Rect getCurrentBounds() {
        UniqueObjectHostView uniqueObjectHostView = this.hostView;
        if (uniqueObjectHostView == null) {
            uniqueObjectHostView = null;
        }
        int[] iArr = this.tmpLocationOnScreen;
        uniqueObjectHostView.getLocationOnScreen(iArr);
        int i = 0;
        int i2 = iArr[0];
        UniqueObjectHostView uniqueObjectHostView2 = this.hostView;
        if (uniqueObjectHostView2 == null) {
            uniqueObjectHostView2 = null;
        }
        int paddingLeft = uniqueObjectHostView2.getPaddingLeft() + i2;
        int i3 = iArr[1];
        UniqueObjectHostView uniqueObjectHostView3 = this.hostView;
        if (uniqueObjectHostView3 == null) {
            uniqueObjectHostView3 = null;
        }
        int paddingTop = uniqueObjectHostView3.getPaddingTop() + i3;
        int i4 = iArr[0];
        UniqueObjectHostView uniqueObjectHostView4 = this.hostView;
        if (uniqueObjectHostView4 == null) {
            uniqueObjectHostView4 = null;
        }
        int width = uniqueObjectHostView4.getWidth() + i4;
        UniqueObjectHostView uniqueObjectHostView5 = this.hostView;
        if (uniqueObjectHostView5 == null) {
            uniqueObjectHostView5 = null;
        }
        int paddingRight = width - uniqueObjectHostView5.getPaddingRight();
        int i5 = iArr[1];
        UniqueObjectHostView uniqueObjectHostView6 = this.hostView;
        if (uniqueObjectHostView6 == null) {
            uniqueObjectHostView6 = null;
        }
        int height = uniqueObjectHostView6.getHeight() + i5;
        UniqueObjectHostView uniqueObjectHostView7 = this.hostView;
        int paddingBottom = height - (uniqueObjectHostView7 != null ? uniqueObjectHostView7 : null).getPaddingBottom();
        if (paddingRight < paddingLeft) {
            paddingLeft = 0;
            paddingRight = 0;
        }
        if (paddingBottom < paddingTop) {
            paddingBottom = 0;
        } else {
            i = paddingTop;
        }
        this.currentBounds.set(paddingLeft, i, paddingRight, paddingBottom);
        return this.currentBounds;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final boolean getDisableScrolling() {
        return this.state.disableScrolling;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final DisappearParameters getDisappearParameters() {
        return this.state.disappearParameters;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final boolean getExpandedMatchesParentHeight() {
        return this.state.expandedMatchesParentHeight;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final float getExpansion() {
        return this.state.expansion;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final boolean getFalsingProtectionNeeded() {
        return this.state.falsingProtectionNeeded;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final MeasurementInput getMeasurementInput() {
        return this.state.measurementInput;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final boolean getShowsOnlyActiveMedia() {
        return this.state.showsOnlyActiveMedia;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final float getSquishFraction() {
        return this.state.squishFraction;
    }

    @Override // com.android.systemui.media.controls.ui.view.MediaHostState
    public final boolean getVisible() {
        return this.state.visible;
    }

    public final void init(final int i) {
        if (this.inited) {
            return;
        }
        this.inited = true;
        this.location = i;
        final MediaHierarchyManager mediaHierarchyManager = this.mediaHierarchyManager;
        mediaHierarchyManager.getClass();
        final UniqueObjectHostView uniqueObjectHostView = new UniqueObjectHostView(mediaHierarchyManager.context);
        uniqueObjectHostView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$createUniqueObjectHost$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                MediaHierarchyManager mediaHierarchyManager2 = mediaHierarchyManager;
                if (mediaHierarchyManager2.rootOverlay == null) {
                    mediaHierarchyManager2.rootView = uniqueObjectHostView.getViewRootImpl().getView();
                    MediaHierarchyManager mediaHierarchyManager3 = mediaHierarchyManager;
                    View view2 = mediaHierarchyManager3.rootView;
                    view2.getClass();
                    mediaHierarchyManager3.rootOverlay = (ViewGroupOverlay) view2.getOverlay();
                }
                uniqueObjectHostView.removeOnAttachStateChangeListener(this);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        });
        this.hostView = uniqueObjectHostView;
        this.visibleChangedListeners.add(new Function1() { // from class: com.android.systemui.media.controls.ui.controller.MediaHierarchyManager$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ((Boolean) obj).getClass();
                MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
                return Unit.INSTANCE;
            }
        });
        int i2 = this.location;
        mediaHierarchyManager.mediaHosts[i2] = this;
        if (i2 == mediaHierarchyManager.desiredLocation) {
            mediaHierarchyManager.desiredLocation = -1;
        }
        if (i2 == mediaHierarchyManager.currentAttachmentLocation) {
            mediaHierarchyManager.currentAttachmentLocation = -1;
        }
        MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
        this.hostView = uniqueObjectHostView;
        setListeningToMediaData(true);
        UniqueObjectHostView uniqueObjectHostView2 = this.hostView;
        if (uniqueObjectHostView2 == null) {
            uniqueObjectHostView2 = null;
        }
        uniqueObjectHostView2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.controls.ui.view.MediaHost.init.1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                MediaHost.this.setListeningToMediaData(true);
                MediaHost.this.updateViewVisibility();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                MediaHost.this.setListeningToMediaData(false);
            }
        });
        UniqueObjectHostView uniqueObjectHostView3 = this.hostView;
        (uniqueObjectHostView3 != null ? uniqueObjectHostView3 : null).setMeasurementManager(new UniqueObjectHostView.MeasurementManager() { // from class: com.android.systemui.media.controls.ui.view.MediaHost.init.2
            @Override // com.android.systemui.util.animation.UniqueObjectHostView.MeasurementManager
            public final MeasurementOutput onMeasure(MeasurementInput measurementInput) {
                if (View.MeasureSpec.getMode(measurementInput.getWidthMeasureSpec()) == Integer.MIN_VALUE) {
                    measurementInput.setWidthMeasureSpec(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measurementInput.getWidthMeasureSpec()), 1073741824));
                }
                MediaHost mediaHost = MediaHost.this;
                mediaHost.state.setMeasurementInput(measurementInput);
                return mediaHost.mediaHostStatesManager.updateCarouselDimensions(i, mediaHost.state);
            }
        });
        this.state.changedListener = new MediaHost$$ExternalSyntheticLambda0(this, i);
        updateViewVisibility();
    }

    public final void setExpansion(float f) {
        this.state.setExpansion(f);
    }

    public final void setListeningToMediaData(boolean z) {
        if (z != this.listeningToMediaData) {
            this.listeningToMediaData = z;
            MediaHost$listener$1 mediaHost$listener$1 = this.listener;
            MediaDataManager mediaDataManager = this.mediaDataManager;
            if (z) {
                mediaDataManager.addListener(mediaHost$listener$1);
            } else {
                mediaDataManager.removeListener(mediaHost$listener$1);
            }
        }
    }

    public final void setShowsOnlyActiveMedia(boolean z) {
        Boolean boolValueOf = Boolean.valueOf(z);
        MediaHostStateHolder mediaHostStateHolder = this.state;
        if (boolValueOf.equals(Boolean.valueOf(mediaHostStateHolder.showsOnlyActiveMedia))) {
            return;
        }
        mediaHostStateHolder.showsOnlyActiveMedia = z;
        MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda0 = mediaHostStateHolder.changedListener;
        if (mediaHost$$ExternalSyntheticLambda0 != null) {
            mediaHost$$ExternalSyntheticLambda0.invoke();
        }
    }

    public final void updateViewVisibility() {
        boolean zHasActiveMediaOrRecommendation;
        MediaHostStateHolder mediaHostStateHolder = this.state;
        boolean z = mediaHostStateHolder.visible;
        MediaCarouselController mediaCarouselController = this.mediaCarouselController;
        if (!mediaCarouselController.allowMediaPlayerOnLockScreen && (!((Boolean) mediaCarouselController.isOnGone.$$delegate_0.getValue()).booleanValue() || ((Boolean) mediaCarouselController.isGoingToDozing.$$delegate_0.getValue()).booleanValue())) {
            zHasActiveMediaOrRecommendation = false;
        } else {
            boolean z2 = mediaHostStateHolder.showsOnlyActiveMedia;
            MediaDataManager mediaDataManager = this.mediaDataManager;
            zHasActiveMediaOrRecommendation = z2 ? mediaDataManager.hasActiveMediaOrRecommendation() : mediaDataManager.hasAnyMediaOrRecommendation();
        }
        if (mediaHostStateHolder.visible != zHasActiveMediaOrRecommendation) {
            mediaHostStateHolder.visible = zHasActiveMediaOrRecommendation;
            MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda0 = mediaHostStateHolder.changedListener;
            if (mediaHost$$ExternalSyntheticLambda0 != null) {
                mediaHost$$ExternalSyntheticLambda0.invoke();
            }
        }
        boolean z3 = mediaHostStateHolder.visible;
        int i = z3 ? 0 : 8;
        if (z == z3) {
            UniqueObjectHostView uniqueObjectHostView = this.hostView;
            if (uniqueObjectHostView == null) {
                uniqueObjectHostView = null;
            }
            if (i == uniqueObjectHostView.getVisibility()) {
                return;
            }
        }
        UniqueObjectHostView uniqueObjectHostView2 = this.hostView;
        if (uniqueObjectHostView2 == null) {
            uniqueObjectHostView2 = null;
        }
        uniqueObjectHostView2.setVisibility(i);
        int i2 = this.location;
        boolean z4 = mediaHostStateHolder.visible;
        MediaCarouselControllerLogger mediaCarouselControllerLogger = this.debugLogger;
        mediaCarouselControllerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaCarouselControllerLogger$$ExternalSyntheticLambda0 mediaCarouselControllerLogger$$ExternalSyntheticLambda0 = new MediaCarouselControllerLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = mediaCarouselControllerLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MediaCarouselCtlrLog", logLevel, mediaCarouselControllerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i2;
        logMessageImpl.bool1 = z4;
        logMessageImpl.bool2 = z;
        logBuffer.commit(logMessageObtain);
        Iterator it = this.visibleChangedListeners.iterator();
        while (it.hasNext()) {
            ((Function1) it.next()).mo781invoke(Boolean.valueOf(mediaHostStateHolder.visible));
        }
    }
}
