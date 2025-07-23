package androidx.compose.foundation.lazy.layout;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.unit.IntOffset;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyLayoutItemAnimation {
    public static final Companion Companion = new Companion(null);
    public static final long NotInitialized;
    public final CoroutineScope coroutineScope;
    public FiniteAnimationSpec fadeInSpec;
    public FiniteAnimationSpec fadeOutSpec;
    public long finalOffset;
    public final GraphicsContext graphicsContext;
    public final MutableState isAppearanceAnimationInProgress$delegate;
    public final MutableState isDisappearanceAnimationFinished$delegate;
    public final MutableState isDisappearanceAnimationInProgress$delegate;
    public final MutableState isPlacementAnimationInProgress$delegate;
    public boolean isRunningMovingAwayAnimation;
    public GraphicsLayer layer;
    public long lookaheadOffset;
    public final Function0 onLayerPropertyChanged;
    public final MutableState placementDelta$delegate;
    public final Animatable placementDeltaAnimation;
    public FiniteAnimationSpec placementSpec;
    public long rawOffset;
    public final Animatable visibilityAnimation;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        long j = Integer.MAX_VALUE;
        IntOffset.Companion companion = IntOffset.Companion;
        NotInitialized = (j & 4294967295L) | (j << 32);
    }

    public LazyLayoutItemAnimation(CoroutineScope coroutineScope, GraphicsContext graphicsContext, Function0 function0) {
        this.coroutineScope = coroutineScope;
        this.graphicsContext = graphicsContext;
        this.onLayerPropertyChanged = function0;
        Boolean bool = Boolean.FALSE;
        this.isPlacementAnimationInProgress$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isAppearanceAnimationInProgress$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isDisappearanceAnimationInProgress$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isDisappearanceAnimationFinished$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        long j = NotInitialized;
        this.rawOffset = j;
        IntOffset.Companion.getClass();
        this.finalOffset = 0L;
        this.layer = graphicsContext != null ? graphicsContext.createGraphicsLayer() : null;
        this.placementDeltaAnimation = new Animatable(IntOffset.m847boximpl(0L), VectorConvertersKt.IntOffsetToVector, null, null, 12, null);
        Float valueOf = Float.valueOf(1.0f);
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        this.visibilityAnimation = new Animatable(valueOf, VectorConvertersKt.FloatToVector, null, null, 12, null);
        this.placementDelta$delegate = SnapshotStateKt.mutableStateOf$default(IntOffset.m847boximpl(0L));
        this.lookaheadOffset = j;
    }

    public final void animateAppearance() {
        GraphicsLayer graphicsLayer = this.layer;
        FiniteAnimationSpec finiteAnimationSpec = this.fadeInSpec;
        boolean booleanValue = ((Boolean) ((SnapshotMutableStateImpl) this.isAppearanceAnimationInProgress$delegate).getValue()).booleanValue();
        CoroutineScope coroutineScope = this.coroutineScope;
        if (booleanValue || finiteAnimationSpec == null || graphicsLayer == null) {
            if (isDisappearanceAnimationInProgress()) {
                if (graphicsLayer != null) {
                    graphicsLayer.setAlpha(1.0f);
                }
                BuildersKt.launch$default(coroutineScope, null, null, new LazyLayoutItemAnimation$animateAppearance$1(this, null), 3);
                return;
            }
            return;
        }
        setAppearanceAnimationInProgress(true);
        boolean isDisappearanceAnimationInProgress = isDisappearanceAnimationInProgress();
        boolean z = !isDisappearanceAnimationInProgress;
        if (!isDisappearanceAnimationInProgress) {
            graphicsLayer.setAlpha(0.0f);
        }
        BuildersKt.launch$default(coroutineScope, null, null, new LazyLayoutItemAnimation$animateAppearance$2(z, this, finiteAnimationSpec, graphicsLayer, null), 3);
    }

    public final void cancelPlacementAnimation() {
        if (((Boolean) ((SnapshotMutableStateImpl) this.isPlacementAnimationInProgress$delegate).getValue()).booleanValue()) {
            BuildersKt.launch$default(this.coroutineScope, null, null, new LazyLayoutItemAnimation$cancelPlacementAnimation$1(this, null), 3);
        }
    }

    public final boolean isDisappearanceAnimationInProgress() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isDisappearanceAnimationInProgress$delegate).getValue()).booleanValue();
    }

    public final void release() {
        GraphicsContext graphicsContext;
        boolean booleanValue = ((Boolean) ((SnapshotMutableStateImpl) this.isPlacementAnimationInProgress$delegate).getValue()).booleanValue();
        CoroutineScope coroutineScope = this.coroutineScope;
        if (booleanValue) {
            setPlacementAnimationInProgress(false);
            BuildersKt.launch$default(coroutineScope, null, null, new LazyLayoutItemAnimation$release$1(this, null), 3);
        }
        if (((Boolean) ((SnapshotMutableStateImpl) this.isAppearanceAnimationInProgress$delegate).getValue()).booleanValue()) {
            setAppearanceAnimationInProgress(false);
            BuildersKt.launch$default(coroutineScope, null, null, new LazyLayoutItemAnimation$release$2(this, null), 3);
        }
        if (isDisappearanceAnimationInProgress()) {
            setDisappearanceAnimationInProgress(false);
            BuildersKt.launch$default(coroutineScope, null, null, new LazyLayoutItemAnimation$release$3(this, null), 3);
        }
        this.isRunningMovingAwayAnimation = false;
        IntOffset.Companion.getClass();
        m166setPlacementDeltagyyYBs(0L);
        this.rawOffset = NotInitialized;
        GraphicsLayer graphicsLayer = this.layer;
        if (graphicsLayer != null && (graphicsContext = this.graphicsContext) != null) {
            graphicsContext.releaseGraphicsLayer(graphicsLayer);
        }
        this.layer = null;
        this.fadeInSpec = null;
        this.fadeOutSpec = null;
        this.placementSpec = null;
    }

    public final void setAppearanceAnimationInProgress(boolean z) {
        ((SnapshotMutableStateImpl) this.isAppearanceAnimationInProgress$delegate).setValue(Boolean.valueOf(z));
    }

    public final void setDisappearanceAnimationInProgress(boolean z) {
        ((SnapshotMutableStateImpl) this.isDisappearanceAnimationInProgress$delegate).setValue(Boolean.valueOf(z));
    }

    public final void setPlacementAnimationInProgress(boolean z) {
        ((SnapshotMutableStateImpl) this.isPlacementAnimationInProgress$delegate).setValue(Boolean.valueOf(z));
    }

    /* renamed from: setPlacementDelta--gyyYBs, reason: not valid java name */
    public final void m166setPlacementDeltagyyYBs(long j) {
        ((SnapshotMutableStateImpl) this.placementDelta$delegate).setValue(IntOffset.m847boximpl(j));
    }

    public /* synthetic */ LazyLayoutItemAnimation(CoroutineScope coroutineScope, GraphicsContext graphicsContext, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, (i & 2) != 0 ? null : graphicsContext, (i & 4) != 0 ? new Function0() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimation.1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        } : function0);
    }
}
