package com.android.compose.animation.scene;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.content.state.TransitionState;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Element {
    public static final float AlphaUnspecified;
    public static final Companion Companion = new Companion(null);
    public static final long SizeUnspecified;
    public final ElementKey key;
    public TransitionState.Transition lastTransition;
    public final SnapshotStateMap stateByContent = new SnapshotStateMap();
    public boolean wasDrawnInAnyContent;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class State {
        public float alphaBeforeInterruption;
        public float alphaInterruptionDelta;
        public final MutableState approachSize$delegate;
        public final List contents;
        public float lastAlpha;
        public long lastOffset;
        public Scale lastScale;
        public long lastSize;
        public final Set nodes;
        public long offsetBeforeInterruption;
        public long offsetInterruptionDelta;
        public Scale scaleBeforeInterruption;
        public Scale scaleInterruptionDelta;
        public long sizeBeforeInterruption;
        public long sizeInterruptionDelta;
        public final MutableState targetOffset$delegate;
        public final MutableState targetSize$delegate;

        public State(List<? extends ContentKey> list) {
            this.contents = list;
            Element.Companion.getClass();
            long j = Element.SizeUnspecified;
            this.targetSize$delegate = SnapshotStateKt.mutableStateOf$default(IntSize.m859boximpl(j));
            Offset.Companion.getClass();
            long j2 = Offset.Unspecified;
            this.targetOffset$delegate = SnapshotStateKt.mutableStateOf$default(Offset.m393boximpl(j2));
            this.approachSize$delegate = SnapshotStateKt.mutableStateOf$default(IntSize.m859boximpl(j));
            this.lastOffset = j2;
            this.lastSize = j;
            Scale.Companion.getClass();
            Scale scale = Scale.Unspecified;
            this.lastScale = scale;
            float f = Element.AlphaUnspecified;
            this.lastAlpha = f;
            this.offsetBeforeInterruption = j2;
            this.sizeBeforeInterruption = j;
            this.scaleBeforeInterruption = scale;
            this.alphaBeforeInterruption = f;
            this.offsetInterruptionDelta = 0L;
            IntSize.Companion.getClass();
            this.sizeInterruptionDelta = 0L;
            this.scaleInterruptionDelta = Scale.Zero;
            this.nodes = new LinkedHashSet();
        }

        /* renamed from: getTargetOffset-F1C5BW0, reason: not valid java name */
        public final long m920getTargetOffsetF1C5BW0() {
            return ((Offset) ((SnapshotMutableStateImpl) this.targetOffset$delegate).getValue()).packedValue;
        }

        /* renamed from: getTargetSize-YbymL2g, reason: not valid java name */
        public final long m921getTargetSizeYbymL2g() {
            return ((IntSize) ((SnapshotMutableStateImpl) this.targetSize$delegate).getValue()).packedValue;
        }
    }

    static {
        long j = Integer.MAX_VALUE;
        IntSize.Companion companion = IntSize.Companion;
        SizeUnspecified = (j & 4294967295L) | (j << 32);
        AlphaUnspecified = Float.MAX_VALUE;
    }

    public Element(ElementKey elementKey) {
        this.key = elementKey;
    }

    public final String toString() {
        return "Element(key=" + this.key + ")";
    }
}
