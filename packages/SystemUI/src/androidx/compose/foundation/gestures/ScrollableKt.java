package androidx.compose.foundation.gestures;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.MotionDurationScale;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.unit.Density;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ScrollableKt {
    public static final Function1 CanDragCalculation = new Function1() { // from class: androidx.compose.foundation.gestures.ScrollableKt$CanDragCalculation$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            int i = ((PointerInputChange) obj).type;
            PointerType.Companion.getClass();
            return Boolean.valueOf(!(i == PointerType.Mouse));
        }
    };
    public static final ScrollableKt$NoOpScrollScope$1 NoOpScrollScope = new ScrollScope() { // from class: androidx.compose.foundation.gestures.ScrollableKt$NoOpScrollScope$1
        @Override // androidx.compose.foundation.gestures.ScrollScope
        public final float scrollBy(float f) {
            return f;
        }
    };
    public static final ScrollableKt$DefaultScrollMotionDurationScale$1 DefaultScrollMotionDurationScale = new MotionDurationScale() { // from class: androidx.compose.foundation.gestures.ScrollableKt$DefaultScrollMotionDurationScale$1
        @Override // kotlin.coroutines.CoroutineContext
        public final Object fold(Object obj, Function2 function2) {
            return function2.invoke(obj, this);
        }

        @Override // kotlin.coroutines.CoroutineContext
        public final CoroutineContext.Element get(CoroutineContext.Key key) {
            return CoroutineContext.DefaultImpls.get(this, key);
        }

        @Override // androidx.compose.ui.MotionDurationScale
        public final float getScaleFactor() {
            return 1.0f;
        }

        @Override // kotlin.coroutines.CoroutineContext
        public final CoroutineContext minusKey(CoroutineContext.Key key) {
            return CoroutineContext.DefaultImpls.minusKey(this, key);
        }

        @Override // kotlin.coroutines.CoroutineContext
        public final CoroutineContext plus(CoroutineContext coroutineContext) {
            return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
        }
    };
    public static final ScrollableKt$UnityDensity$1 UnityDensity = new Density() { // from class: androidx.compose.foundation.gestures.ScrollableKt$UnityDensity$1
        @Override // androidx.compose.ui.unit.Density
        public final float getDensity() {
            return 1.0f;
        }

        @Override // androidx.compose.ui.unit.FontScaling
        public final float getFontScale() {
            return 1.0f;
        }
    };

    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* renamed from: access$semanticsScrollBy-d-4ec7I, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m76access$semanticsScrollByd4ec7I(androidx.compose.foundation.gestures.ScrollingLogic r10, long r11, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            boolean r0 = r13 instanceof androidx.compose.foundation.gestures.ScrollableKt$semanticsScrollBy$1
            if (r0 == 0) goto L13
            r0 = r13
            androidx.compose.foundation.gestures.ScrollableKt$semanticsScrollBy$1 r0 = (androidx.compose.foundation.gestures.ScrollableKt$semanticsScrollBy$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.ScrollableKt$semanticsScrollBy$1 r0 = new androidx.compose.foundation.gestures.ScrollableKt$semanticsScrollBy$1
            r0.<init>(r13)
        L18:
            java.lang.Object r13 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r10 = r0.L$1
            kotlin.jvm.internal.Ref$FloatRef r10 = (kotlin.jvm.internal.Ref$FloatRef) r10
            java.lang.Object r11 = r0.L$0
            androidx.compose.foundation.gestures.ScrollingLogic r11 = (androidx.compose.foundation.gestures.ScrollingLogic) r11
            kotlin.ResultKt.throwOnFailure(r13)
            r8 = r10
            r10 = r11
            goto L59
        L31:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L39:
            kotlin.ResultKt.throwOnFailure(r13)
            kotlin.jvm.internal.Ref$FloatRef r8 = new kotlin.jvm.internal.Ref$FloatRef
            r8.<init>()
            androidx.compose.foundation.MutatePriority r13 = androidx.compose.foundation.MutatePriority.Default
            androidx.compose.foundation.gestures.ScrollableKt$semanticsScrollBy$2 r4 = new androidx.compose.foundation.gestures.ScrollableKt$semanticsScrollBy$2
            r9 = 0
            r5 = r10
            r6 = r11
            r4.<init>(r5, r6, r8, r9)
            r0.L$0 = r5
            r0.L$1 = r8
            r0.label = r3
            java.lang.Object r10 = r5.scroll(r13, r4, r0)
            if (r10 != r1) goto L58
            return r1
        L58:
            r10 = r5
        L59:
            float r11 = r8.element
            long r10 = r10.m85toOffsettuRUvjQ(r11)
            androidx.compose.ui.geometry.Offset r10 = androidx.compose.ui.geometry.Offset.m393boximpl(r10)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.ScrollableKt.m76access$semanticsScrollByd4ec7I(androidx.compose.foundation.gestures.ScrollingLogic, long, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static Modifier scrollable$default(Modifier modifier, ScrollableState scrollableState, Orientation orientation, boolean z, boolean z2, MutableInteractionSource mutableInteractionSource, int i) {
        if ((i & 4) != 0) {
            z = true;
        }
        boolean z3 = z;
        if ((i & 8) != 0) {
            z2 = false;
        }
        boolean z4 = z2;
        if ((i & 32) != 0) {
            mutableInteractionSource = null;
        }
        return modifier.then(new ScrollableElement(scrollableState, orientation, null, z3, z4, null, mutableInteractionSource, null));
    }
}
