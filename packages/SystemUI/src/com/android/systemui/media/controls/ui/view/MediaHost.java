package com.android.systemui.media.controls.ui.view;

import android.graphics.Rect;
import android.os.Trace;
import android.util.ArraySet;
import android.view.View;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.controller.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.controller.MediaViewController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaHost implements MediaHostState {
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
        public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
            if (z) {
                MediaHost.this.updateViewVisibility();
            }
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(String str, boolean z) {
            MediaHost.this.updateViewVisibility();
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
            MediaHost.this.updateViewVisibility();
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
            if (z) {
                MediaHost.this.updateViewVisibility();
            }
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MediaHostStateHolder implements MediaHostState {
        public Function0 changedListener;
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
                Function0 function0 = mediaHostStateHolder.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
            float f = this.squishFraction;
            if (!Float.valueOf(f).equals(Float.valueOf(mediaHostStateHolder.squishFraction))) {
                mediaHostStateHolder.squishFraction = f;
                Function0 function02 = mediaHostStateHolder.changedListener;
                if (function02 != null) {
                    function02.invoke();
                }
            }
            boolean z2 = this.showsOnlyActiveMedia;
            if (!Boolean.valueOf(z2).equals(Boolean.valueOf(mediaHostStateHolder.showsOnlyActiveMedia))) {
                mediaHostStateHolder.showsOnlyActiveMedia = z2;
                Function0 function03 = mediaHostStateHolder.changedListener;
                if (function03 != null) {
                    function03.invoke();
                }
            }
            MeasurementInput measurementInput = this.measurementInput;
            mediaHostStateHolder.setMeasurementInput(measurementInput != null ? MeasurementInput.copy$default(measurementInput, 0, 0, 3, null) : null);
            boolean z3 = this.visible;
            if (mediaHostStateHolder.visible != z3) {
                mediaHostStateHolder.visible = z3;
                Function0 function04 = mediaHostStateHolder.changedListener;
                if (function04 != null) {
                    function04.invoke();
                }
            }
            DisappearParameters deepCopy = this.disappearParameters.deepCopy();
            int hashCode = deepCopy.hashCode();
            if (!Integer.valueOf(mediaHostStateHolder.lastDisappearHash).equals(Integer.valueOf(hashCode))) {
                mediaHostStateHolder.disappearParameters = deepCopy;
                mediaHostStateHolder.lastDisappearHash = hashCode;
                Function0 function05 = mediaHostStateHolder.changedListener;
                if (function05 != null) {
                    function05.invoke();
                }
            }
            boolean z4 = this.falsingProtectionNeeded;
            if (mediaHostStateHolder.falsingProtectionNeeded != z4) {
                mediaHostStateHolder.falsingProtectionNeeded = z4;
                Function0 function06 = mediaHostStateHolder.changedListener;
                if (function06 != null) {
                    function06.invoke();
                }
            }
            return mediaHostStateHolder;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof MediaHostState)) {
                return false;
            }
            MediaHostState mediaHostState = (MediaHostState) obj;
            return Objects.equals(this.measurementInput, mediaHostState.getMeasurementInput()) && this.expansion == mediaHostState.getExpansion() && this.squishFraction == mediaHostState.getSquishFraction() && this.showsOnlyActiveMedia == mediaHostState.getShowsOnlyActiveMedia() && this.visible == mediaHostState.getVisible() && this.falsingProtectionNeeded == mediaHostState.getFalsingProtectionNeeded() && this.disappearParameters.equals(mediaHostState.getDisappearParameters());
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
            return this.disappearParameters.hashCode() + ((TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.squishFraction, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.expansion, (measurementInput != null ? measurementInput.hashCode() : 0) * 31, 31), 31), 31, this.falsingProtectionNeeded), 31, this.showsOnlyActiveMedia) + (this.visible ? 1 : 2)) * 31);
        }

        public final void setExpansion(float f) {
            if (Float.valueOf(f).equals(Float.valueOf(this.expansion))) {
                return;
            }
            this.expansion = f;
            Function0 function0 = this.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }

        public final void setMeasurementInput(MeasurementInput measurementInput) {
            if (measurementInput == null || !measurementInput.equals(this.measurementInput)) {
                this.measurementInput = measurementInput;
                Function0 function0 = this.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.media.controls.ui.view.MediaHost$listener$1] */
    public MediaHost(MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager, MediaCarouselController mediaCarouselController) {
        this.state = mediaHostStateHolder;
        this.mediaHierarchyManager = mediaHierarchyManager;
        this.mediaDataManager = mediaDataManager;
        this.mediaHostStatesManager = mediaHostStatesManager;
        this.mediaCarouselController = mediaCarouselController;
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
        this.hostView = this.mediaHierarchyManager.register(this);
        setListeningToMediaData(true);
        UniqueObjectHostView uniqueObjectHostView = this.hostView;
        if (uniqueObjectHostView == null) {
            uniqueObjectHostView = null;
        }
        uniqueObjectHostView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.media.controls.ui.view.MediaHost$init$1
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
        UniqueObjectHostView uniqueObjectHostView2 = this.hostView;
        (uniqueObjectHostView2 != null ? uniqueObjectHostView2 : null).setMeasurementManager(new UniqueObjectHostView.MeasurementManager() { // from class: com.android.systemui.media.controls.ui.view.MediaHost$init$2
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
        this.state.changedListener = new Function0() { // from class: com.android.systemui.media.controls.ui.view.MediaHost$init$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MediaHost mediaHost = MediaHost.this;
                MediaHostStatesManager mediaHostStatesManager = mediaHost.mediaHostStatesManager;
                int i2 = i;
                MediaHost.MediaHostStateHolder mediaHostStateHolder = mediaHost.state;
                mediaHostStatesManager.getClass();
                boolean isEnabled = Trace.isEnabled();
                if (isEnabled) {
                    TraceUtilsKt.beginSlice("MediaHostStatesManager#updateHostState");
                }
                try {
                    if (!mediaHostStateHolder.equals((MediaHostState) ((LinkedHashMap) mediaHostStatesManager.mediaHostStates).get(Integer.valueOf(i2)))) {
                        MediaHost.MediaHostStateHolder copy = mediaHostStateHolder.copy();
                        mediaHostStatesManager.mediaHostStates.put(Integer.valueOf(i2), copy);
                        mediaHostStatesManager.updateCarouselDimensions(i2, mediaHostStateHolder);
                        Iterator it = mediaHostStatesManager.controllers.iterator();
                        while (it.hasNext()) {
                            ((MediaViewController) it.next()).stateCallback.onHostStateChanged(i2, copy);
                        }
                        Iterator it2 = mediaHostStatesManager.callbacks.iterator();
                        while (it2.hasNext()) {
                            ((MediaHostStatesManager.Callback) it2.next()).onHostStateChanged(i2, copy);
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                    return Unit.INSTANCE;
                } catch (Throwable th) {
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                    throw th;
                }
            }
        };
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0079 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateViewVisibility() {
        /*
            r4 = this;
            com.android.systemui.media.controls.ui.controller.MediaCarouselController r0 = r4.mediaCarouselController
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r1 = r0.keyguardTransitionInteractor
            kotlinx.coroutines.flow.ReadonlySharedFlow r1 = r1.finishedKeyguardState
            kotlinx.coroutines.flow.SharedFlow r1 = r1.$$delegate_0
            java.util.List r1 = r1.getReplayCache()
            java.lang.Object r1 = kotlin.collections.CollectionsKt___CollectionsKt.last(r1)
            com.android.systemui.keyguard.shared.model.KeyguardState r1 = (com.android.systemui.keyguard.shared.model.KeyguardState) r1
            boolean r0 = r0.allowMediaPlayerOnLockScreen
            r2 = 0
            com.android.systemui.media.controls.ui.view.MediaHost$MediaHostStateHolder r3 = r4.state
            if (r0 != 0) goto L24
            com.android.systemui.keyguard.shared.model.KeyguardState$Companion r0 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion
            r0.getClass()
            com.android.systemui.keyguard.shared.model.KeyguardState r0 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            if (r1 == r0) goto L24
            r0 = r2
            goto L33
        L24:
            boolean r0 = r3.showsOnlyActiveMedia
            com.android.systemui.media.controls.domain.pipeline.MediaDataManager r1 = r4.mediaDataManager
            if (r0 == 0) goto L2f
            boolean r0 = r1.hasActiveMediaOrRecommendation()
            goto L33
        L2f:
            boolean r0 = r1.hasAnyMediaOrRecommendation()
        L33:
            boolean r1 = r3.visible
            if (r1 != r0) goto L38
            goto L41
        L38:
            r3.visible = r0
            kotlin.jvm.functions.Function0 r0 = r3.changedListener
            if (r0 == 0) goto L41
            r0.invoke()
        L41:
            boolean r0 = r3.visible
            if (r0 == 0) goto L46
            goto L48
        L46:
            r2 = 8
        L48:
            com.android.systemui.util.animation.UniqueObjectHostView r0 = r4.hostView
            r1 = 0
            if (r0 == 0) goto L4e
            goto L4f
        L4e:
            r0 = r1
        L4f:
            int r0 = r0.getVisibility()
            if (r2 == r0) goto L79
            com.android.systemui.util.animation.UniqueObjectHostView r0 = r4.hostView
            if (r0 == 0) goto L5a
            r1 = r0
        L5a:
            r1.setVisibility(r2)
            android.util.ArraySet r4 = r4.visibleChangedListeners
            java.util.Iterator r4 = r4.iterator()
        L63:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L79
            java.lang.Object r0 = r4.next()
            kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0
            boolean r1 = r3.visible
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r0.invoke(r1)
            goto L63
        L79:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.view.MediaHost.updateViewVisibility():void");
    }
}
