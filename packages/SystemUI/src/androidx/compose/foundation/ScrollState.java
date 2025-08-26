package androidx.compose.foundation;

import androidx.compose.foundation.gestures.ScrollExtensionsKt;
import androidx.compose.foundation.gestures.ScrollableState;
import androidx.compose.foundation.gestures.ScrollableStateKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.saveable.SaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class ScrollState implements ScrollableState {
    public static final Companion Companion = new Companion(null);
    public static final SaverKt$Saver$1 Saver;
    public float accumulator;
    public final MutableIntState value$delegate;
    public final MutableIntState viewportSize$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
    public final MutableInteractionSource internalInteractionSource = InteractionSourceKt.MutableInteractionSource();
    public final MutableIntState _maxValueState = SnapshotIntStateKt.mutableIntStateOf(Integer.MAX_VALUE);
    public final ScrollableState scrollableState = ScrollableStateKt.ScrollableState(new Function1() { // from class: androidx.compose.foundation.ScrollState$scrollableState$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            float fFloatValue = ((Number) obj).floatValue();
            float value = this.this$0.getValue() + fFloatValue + this.this$0.accumulator;
            float fCoerceIn = RangesKt___RangesKt.coerceIn(value, 0.0f, r1.getMaxValue());
            boolean z = value == fCoerceIn;
            float value2 = fCoerceIn - this.this$0.getValue();
            int iRound = Math.round(value2);
            ScrollState scrollState = this.this$0;
            ((SnapshotMutableIntStateImpl) scrollState.value$delegate).setIntValue(scrollState.getValue() + iRound);
            this.this$0.accumulator = value2 - iRound;
            if (!z) {
                fFloatValue = value2;
            }
            return Float.valueOf(fFloatValue);
        }
    });
    public final State canScrollForward$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.foundation.ScrollState$canScrollForward$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(this.this$0.getValue() < this.this$0.getMaxValue());
        }
    });
    public final State canScrollBackward$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.foundation.ScrollState$canScrollBackward$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(this.this$0.getValue() > 0);
        }
    });

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        ScrollState$Companion$Saver$1 scrollState$Companion$Saver$1 = new Function2() { // from class: androidx.compose.foundation.ScrollState$Companion$Saver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((ScrollState) obj2).getValue());
            }
        };
        ScrollState$Companion$Saver$2 scrollState$Companion$Saver$2 = new Function1() { // from class: androidx.compose.foundation.ScrollState$Companion$Saver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return new ScrollState(((Number) obj).intValue());
            }
        };
        SaverKt$Saver$1 saverKt$Saver$1 = SaverKt.AutoSaver;
        Saver = new SaverKt$Saver$1(scrollState$Companion$Saver$1, scrollState$Companion$Saver$2);
    }

    public ScrollState(int i) {
        this.value$delegate = SnapshotIntStateKt.mutableIntStateOf(i);
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final float dispatchRawDelta(float f) {
        return this.scrollableState.dispatchRawDelta(f);
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean getCanScrollBackward() {
        return ((Boolean) this.canScrollBackward$delegate.getValue()).booleanValue();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean getCanScrollForward() {
        return ((Boolean) this.canScrollForward$delegate.getValue()).booleanValue();
    }

    public final int getMaxValue() {
        return ((SnapshotMutableIntStateImpl) this._maxValueState).getIntValue();
    }

    public final int getValue() {
        return ((SnapshotMutableIntStateImpl) this.value$delegate).getIntValue();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final boolean isScrollInProgress() {
        return this.scrollableState.isScrollInProgress();
    }

    @Override // androidx.compose.foundation.gestures.ScrollableState
    public final Object scroll(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        Object objScroll = this.scrollableState.scroll(mutatePriority, function2, continuation);
        return objScroll == CoroutineSingletons.COROUTINE_SUSPENDED ? objScroll : Unit.INSTANCE;
    }

    public final Object scrollTo(int i, ContinuationImpl continuationImpl) {
        return ScrollExtensionsKt.scrollBy(this, i - getValue(), continuationImpl);
    }
}
