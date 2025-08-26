package androidx.compose.foundation.gestures;

import androidx.compose.foundation.ComposeFoundationFlags;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException;
import androidx.compose.ui.input.pointer.PointerId;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Dp;
import com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0;
import com.android.compose.gesture.NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda1;
import com.samsung.sesl.compose.foundation.BasicSwitchKt$$ExternalSyntheticLambda1;
import com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$LongRef;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public abstract class DragGestureDetectorKt {
    public static final float mouseToTouchSlopRatio;

    /* renamed from: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGestures$9, reason: invalid class name */
    final class AnonymousClass9 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function2 $onDrag;
        final /* synthetic */ Function0 $onDragCancel;
        final /* synthetic */ Function1 $onDragEnd;
        final /* synthetic */ Function3 $onDragStart;
        final /* synthetic */ Orientation $orientationLock;
        final /* synthetic */ Ref$LongRef $overSlop;
        final /* synthetic */ Function0 $shouldAwaitTouchSlop;
        float F$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass9(Function0 function0, Ref$LongRef ref$LongRef, Orientation orientation, Function3 function3, Function2 function2, Function0 function02, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$shouldAwaitTouchSlop = function0;
            this.$overSlop = ref$LongRef;
            this.$orientationLock = orientation;
            this.$onDragStart = function3;
            this.$onDrag = function2;
            this.$onDragCancel = function02;
            this.$onDragEnd = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass9 anonymousClass9 = new AnonymousClass9(this.$shouldAwaitTouchSlop, this.$overSlop, this.$orientationLock, this.$onDragStart, this.$onDrag, this.$onDragCancel, this.$onDragEnd, continuation);
            anonymousClass9.L$0 = obj;
            return anonymousClass9;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass9) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:152:0x0464, code lost:
        
            if (r8.awaitPointerEvent(r3, r22) == r1) goto L167;
         */
        /* JADX WARN: Code restructure failed: missing block: B:162:0x04ad, code lost:
        
            if (androidx.compose.foundation.gestures.DragGestureDetectorKt.m74isPointerUpDmW0f2w(r4, r5) != false) goto L163;
         */
        /* JADX WARN: Code restructure failed: missing block: B:166:0x04d4, code lost:
        
            if (r3 != r1) goto L168;
         */
        /* JADX WARN: Code restructure failed: missing block: B:200:0x0556, code lost:
        
            if (r9 == 0.0f) goto L165;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x018c, code lost:
        
            if (r9 != r1) goto L24;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x019b, code lost:
        
            if (r2 != false) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:89:0x0303, code lost:
        
            if (r3 == r1) goto L167;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Path cross not found for [B:180:0x050b, B:191:0x0533], limit reached: 217 */
        /* JADX WARN: Removed duplicated region for block: B:118:0x03b7 A[PHI: r2 r3 r5 r6 r7 r8 r9 r11 r16 r18
          0x03b7: PHI (r2v43 androidx.compose.ui.input.pointer.AwaitPointerEventScope) = 
          (r2v26 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
          (r2v44 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
         binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]
          0x03b7: PHI (r3v33 java.lang.Object) = (r3v18 java.lang.Object), (r3v46 java.lang.Object) binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]
          0x03b7: PHI (r5v24 kotlin.jvm.internal.Ref$LongRef) = (r5v9 kotlin.jvm.internal.Ref$LongRef), (r5v25 kotlin.jvm.internal.Ref$LongRef) binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]
          0x03b7: PHI (r6v24 float) = (r6v10 float), (r6v25 float) binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]
          0x03b7: PHI (r7v36 androidx.compose.ui.input.pointer.PointerInputChange) = 
          (r7v27 androidx.compose.ui.input.pointer.PointerInputChange)
          (r7v38 androidx.compose.ui.input.pointer.PointerInputChange)
         binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]
          0x03b7: PHI (r8v39 androidx.compose.ui.input.pointer.AwaitPointerEventScope) = 
          (r8v33 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
          (r8v42 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
         binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]
          0x03b7: PHI (r9v45 androidx.compose.foundation.gestures.TouchSlopDetector) = 
          (r9v29 androidx.compose.foundation.gestures.TouchSlopDetector)
          (r9v47 androidx.compose.foundation.gestures.TouchSlopDetector)
         binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]
          0x03b7: PHI (r11v13 kotlin.jvm.internal.Ref$LongRef) = (r11v10 kotlin.jvm.internal.Ref$LongRef), (r11v14 kotlin.jvm.internal.Ref$LongRef) binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]
          0x03b7: PHI (r16v20 long) = (r16v14 long), (r16v21 long) binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]
          0x03b7: PHI (r18v20 long) = (r18v14 long), (r18v21 long) binds: [B:8:0x0068, B:116:0x03b3] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:120:0x03c5  */
        /* JADX WARN: Removed duplicated region for block: B:133:0x03f5  */
        /* JADX WARN: Removed duplicated region for block: B:144:0x041e  */
        /* JADX WARN: Removed duplicated region for block: B:161:0x0485  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x017c  */
        /* JADX WARN: Removed duplicated region for block: B:222:0x03e0 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:234:0x0214 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x01b2  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x01b5  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x01e7 A[PHI: r2 r7 r8 r9 r10 r11 r12 r13 r14 r16 r18
          0x01e7: PHI (r2v18 androidx.compose.ui.input.pointer.AwaitPointerEventScope) = 
          (r2v13 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
          (r2v19 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
         binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r7v15 kotlin.jvm.internal.Ref$LongRef) = (r7v10 kotlin.jvm.internal.Ref$LongRef), (r7v18 kotlin.jvm.internal.Ref$LongRef) binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r8v10 androidx.compose.ui.input.pointer.AwaitPointerEventScope) = 
          (r8v6 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
          (r8v24 androidx.compose.ui.input.pointer.AwaitPointerEventScope)
         binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r9v11 androidx.compose.ui.input.pointer.PointerInputChange) = 
          (r9v7 androidx.compose.ui.input.pointer.PointerInputChange)
          (r9v22 androidx.compose.ui.input.pointer.PointerInputChange)
         binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r10v9 float) = (r10v3 float), (r10v11 float) binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r11v3 int) = (r11v0 int), (r11v8 int) binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r12v6 androidx.compose.foundation.gestures.TouchSlopDetector) = 
          (r12v3 androidx.compose.foundation.gestures.TouchSlopDetector)
          (r12v7 androidx.compose.foundation.gestures.TouchSlopDetector)
         binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r13v3 java.lang.Object) = (r13v2 java.lang.Object), (r13v6 java.lang.Object) binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r14v4 kotlin.jvm.internal.Ref$LongRef) = (r14v1 kotlin.jvm.internal.Ref$LongRef), (r14v5 kotlin.jvm.internal.Ref$LongRef) binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r16v9 long) = (r16v5 long), (r16v10 long) binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]
          0x01e7: PHI (r18v9 long) = (r18v5 long), (r18v10 long) binds: [B:11:0x00eb, B:31:0x01e3] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:35:0x01f5  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0232  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0258  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x02b2  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x02c0  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x02cd A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:85:0x02df  */
        /* JADX WARN: Type inference failed for: r12v18, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r12v25 */
        /* JADX WARN: Type inference failed for: r12v26 */
        /* JADX WARN: Type inference failed for: r12v30, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r13v21, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r21v0 */
        /* JADX WARN: Type inference failed for: r21v1 */
        /* JADX WARN: Type inference failed for: r21v2, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r2v37, types: [kotlin.jvm.functions.Function3] */
        /* JADX WARN: Type inference failed for: r2v38, types: [kotlin.jvm.functions.Function2] */
        /* JADX WARN: Type inference failed for: r7v11 */
        /* JADX WARN: Type inference failed for: r7v13 */
        /* JADX WARN: Type inference failed for: r7v14, types: [androidx.compose.ui.input.pointer.PointerInputChange] */
        /* JADX WARN: Type inference failed for: r7v17 */
        /* JADX WARN: Type inference failed for: r7v29 */
        /* JADX WARN: Type inference failed for: r7v31, types: [androidx.compose.ui.input.pointer.PointerInputChange, java.lang.Object] */
        /* JADX WARN: Type inference failed for: r7v33 */
        /* JADX WARN: Type inference failed for: r7v35 */
        /* JADX WARN: Type inference failed for: r7v37 */
        /* JADX WARN: Type inference failed for: r7v41 */
        /* JADX WARN: Type inference failed for: r7v5 */
        /* JADX WARN: Type inference failed for: r7v50 */
        /* JADX WARN: Type inference failed for: r7v51 */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:111:0x037c -> B:112:0x037e). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:114:0x0384 -> B:115:0x039c). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:143:0x0417 -> B:150:0x0446). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:149:0x043d -> B:150:0x0446). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:152:0x0464 -> B:154:0x0468). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:158:0x0479 -> B:80:0x02c9). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:166:0x04d4 -> B:168:0x04d7). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x01b2 -> B:74:0x02b0). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x01b5 -> B:30:0x01cc). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:70:0x02a4 -> B:71:0x02a8). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:98:0x0335 -> B:88:0x02eb). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            long j;
            long j2;
            AwaitPointerEventScope awaitPointerEventScope;
            Object objAwaitFirstDown;
            AwaitPointerEventScope awaitPointerEventScope2;
            boolean zBooleanValue;
            Object objAwaitFirstDown$default;
            ?? r7;
            PointerInputChange pointerInputChange;
            TouchSlopDetector touchSlopDetector;
            Ref$LongRef ref$LongRef;
            AwaitPointerEventScope awaitPointerEventScope3;
            AwaitPointerEventScope awaitPointerEventScope4;
            PointerInputChange pointerInputChange2;
            PointerInputChange pointerInputChange3;
            float f;
            Ref$LongRef ref$LongRef2;
            PointerInputChange pointerInputChange4;
            Object obj2;
            PointerInputChange pointerInputChange5;
            PointerInputChange pointerInputChange6;
            Object objAwaitPointerEvent;
            Object objAwaitPointerEvent2;
            float fM75pointerSlopE8SPZFQ;
            Ref$LongRef ref$LongRef3;
            Ref$LongRef ref$LongRef4;
            AwaitPointerEventScope awaitPointerEventScope5;
            TouchSlopDetector touchSlopDetector2;
            long j3;
            Object obj3;
            Function2 function2;
            Orientation orientation;
            long j4;
            int size;
            int i;
            int size2;
            int i2;
            PointerInputChange pointerInputChange7;
            PointerInputChange pointerInputChange8;
            PointerInputChange pointerInputChange9;
            int i3;
            PointerInputChange pointerInputChange10;
            PointerInputChange pointerInputChange11;
            PointerInputChange pointerInputChange12;
            Object obj4;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            DefaultConstructorMarker defaultConstructorMarker = null;
            long j5 = 0;
            int i4 = 0;
            switch (this.label) {
                case 0:
                    j = 9205357640488583168L;
                    j2 = 9223372034707292159L;
                    ResultKt.throwOnFailure(obj);
                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                    PointerEventPass pointerEventPass = PointerEventPass.Initial;
                    this.L$0 = awaitPointerEventScope;
                    this.label = 1;
                    objAwaitFirstDown = TapGestureDetectorKt.awaitFirstDown(awaitPointerEventScope, false, pointerEventPass, this);
                    if (objAwaitFirstDown != coroutineSingletons) {
                        awaitPointerEventScope2 = awaitPointerEventScope;
                        PointerInputChange pointerInputChange13 = (PointerInputChange) objAwaitFirstDown;
                        zBooleanValue = ((Boolean) this.$shouldAwaitTouchSlop.invoke()).booleanValue();
                        if (!zBooleanValue) {
                            pointerInputChange13.consume();
                        }
                        this.L$0 = awaitPointerEventScope2;
                        this.L$1 = pointerInputChange13;
                        this.Z$0 = zBooleanValue;
                        this.label = 2;
                        objAwaitFirstDown$default = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope2, null, this, 2);
                        r7 = pointerInputChange13;
                        break;
                    }
                    return coroutineSingletons;
                case 1:
                    j = 9205357640488583168L;
                    j2 = 9223372034707292159L;
                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objAwaitFirstDown = obj;
                    awaitPointerEventScope2 = awaitPointerEventScope;
                    PointerInputChange pointerInputChange132 = (PointerInputChange) objAwaitFirstDown;
                    zBooleanValue = ((Boolean) this.$shouldAwaitTouchSlop.invoke()).booleanValue();
                    if (!zBooleanValue) {
                    }
                    this.L$0 = awaitPointerEventScope2;
                    this.L$1 = pointerInputChange132;
                    this.Z$0 = zBooleanValue;
                    this.label = 2;
                    objAwaitFirstDown$default = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope2, null, this, 2);
                    r7 = pointerInputChange132;
                    break;
                case 2:
                    j = 9205357640488583168L;
                    j2 = 9223372034707292159L;
                    zBooleanValue = this.Z$0;
                    PointerInputChange pointerInputChange14 = (PointerInputChange) this.L$1;
                    awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objAwaitFirstDown$default = obj;
                    r7 = pointerInputChange14;
                    pointerInputChange = (PointerInputChange) objAwaitFirstDown$default;
                    Ref$LongRef ref$LongRef5 = this.$overSlop;
                    Offset.Companion.getClass();
                    ref$LongRef5.element = 0L;
                    break;
                case 3:
                    j = 9205357640488583168L;
                    j2 = 9223372034707292159L;
                    float f2 = this.F$0;
                    TouchSlopDetector touchSlopDetector3 = (TouchSlopDetector) this.L$5;
                    Ref$LongRef ref$LongRef6 = (Ref$LongRef) this.L$4;
                    Ref$LongRef ref$LongRef7 = (Ref$LongRef) this.L$3;
                    AwaitPointerEventScope awaitPointerEventScope6 = (AwaitPointerEventScope) this.L$2;
                    PointerInputChange pointerInputChange15 = (PointerInputChange) this.L$1;
                    AwaitPointerEventScope awaitPointerEventScope7 = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    touchSlopDetector = touchSlopDetector3;
                    Ref$LongRef ref$LongRef8 = ref$LongRef7;
                    pointerInputChange = pointerInputChange15;
                    ref$LongRef = ref$LongRef6;
                    awaitPointerEventScope2 = awaitPointerEventScope6;
                    float fM75pointerSlopE8SPZFQ2 = f2;
                    awaitPointerEventScope3 = awaitPointerEventScope7;
                    Object objAwaitPointerEvent3 = obj;
                    PointerEvent pointerEvent = (PointerEvent) objAwaitPointerEvent3;
                    List list = pointerEvent.changes;
                    int size3 = list.size();
                    while (true) {
                        if (i4 >= size3) {
                            pointerInputChange3 = list.get(i4);
                            awaitPointerEventScope4 = awaitPointerEventScope2;
                            pointerInputChange2 = pointerInputChange;
                            if (!PointerId.m593equalsimpl0(((PointerInputChange) pointerInputChange3).id, ref$LongRef.element)) {
                                i4++;
                                pointerInputChange = pointerInputChange2;
                                awaitPointerEventScope2 = awaitPointerEventScope4;
                            }
                        } else {
                            awaitPointerEventScope4 = awaitPointerEventScope2;
                            pointerInputChange2 = pointerInputChange;
                            pointerInputChange3 = 0;
                        }
                    }
                    PointerInputChange pointerInputChange16 = pointerInputChange3;
                    if (pointerInputChange16 != null && !pointerInputChange16.isConsumed()) {
                        if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange16)) {
                            long jM87addPointerInputChangedBAh8RU = touchSlopDetector.m87addPointerInputChangedBAh8RU(pointerInputChange16, fM75pointerSlopE8SPZFQ2);
                            if ((jM87addPointerInputChangedBAh8RU & j2) == j) {
                                PointerEventPass pointerEventPass2 = PointerEventPass.Final;
                                this.L$0 = awaitPointerEventScope3;
                                pointerInputChange = pointerInputChange2;
                                this.L$1 = pointerInputChange;
                                this.L$2 = awaitPointerEventScope4;
                                this.L$3 = ref$LongRef8;
                                this.L$4 = ref$LongRef;
                                this.L$5 = touchSlopDetector;
                                this.L$6 = pointerInputChange16;
                                this.F$0 = fM75pointerSlopE8SPZFQ2;
                                this.label = 4;
                                if (awaitPointerEventScope4.awaitPointerEvent(pointerEventPass2, this) != coroutineSingletons) {
                                    awaitPointerEventScope2 = awaitPointerEventScope4;
                                    f = fM75pointerSlopE8SPZFQ2;
                                    ref$LongRef2 = ref$LongRef8;
                                    pointerInputChange4 = pointerInputChange16;
                                    if (!pointerInputChange4.isConsumed()) {
                                        ref$LongRef8 = ref$LongRef2;
                                        i4 = 0;
                                        fM75pointerSlopE8SPZFQ2 = f;
                                        defaultConstructorMarker = null;
                                    }
                                    awaitPointerEventScope2 = awaitPointerEventScope3;
                                    r7 = 0;
                                    if (r7 != 0) {
                                    }
                                    if (ComposeFoundationFlags.DragGesturePickUpEnabled) {
                                        List list2 = awaitPointerEventScope2.getCurrentEvent().changes;
                                        size = list2.size();
                                        while (i < size) {
                                        }
                                    }
                                    if (r7 != 0) {
                                    }
                                    return Unit.INSTANCE;
                                }
                                return coroutineSingletons;
                            }
                            pointerInputChange16.consume();
                            ref$LongRef8.element = jM87addPointerInputChangedBAh8RU;
                            if (pointerInputChange16.isConsumed()) {
                                pointerInputChange = pointerInputChange2;
                                awaitPointerEventScope2 = awaitPointerEventScope3;
                                r7 = pointerInputChange16;
                                if (r7 != 0 && !r7.isConsumed()) {
                                    defaultConstructorMarker = null;
                                    j5 = 0;
                                    i4 = 0;
                                    long j6 = pointerInputChange.id;
                                    Orientation orientation2 = this.$orientationLock;
                                    ref$LongRef8 = this.$overSlop;
                                    Offset.Companion.getClass();
                                    if (!DragGestureDetectorKt.m74isPointerUpDmW0f2w(awaitPointerEventScope2.getCurrentEvent(), j6)) {
                                        r7 = defaultConstructorMarker;
                                        if (r7 != 0) {
                                            defaultConstructorMarker = null;
                                            j5 = 0;
                                            i4 = 0;
                                            long j62 = pointerInputChange.id;
                                            Orientation orientation22 = this.$orientationLock;
                                            ref$LongRef8 = this.$overSlop;
                                            Offset.Companion.getClass();
                                            if (!DragGestureDetectorKt.m74isPointerUpDmW0f2w(awaitPointerEventScope2.getCurrentEvent(), j62)) {
                                                fM75pointerSlopE8SPZFQ2 = DragGestureDetectorKt.m75pointerSlopE8SPZFQ(awaitPointerEventScope2.getViewConfiguration(), pointerInputChange.type);
                                                ref$LongRef = new Ref$LongRef();
                                                ref$LongRef.element = j62;
                                                touchSlopDetector = new TouchSlopDetector(orientation22, j5, defaultConstructorMarker);
                                                awaitPointerEventScope3 = awaitPointerEventScope2;
                                            }
                                        }
                                    }
                                }
                                if (ComposeFoundationFlags.DragGesturePickUpEnabled && r7 == 0) {
                                    List list22 = awaitPointerEventScope2.getCurrentEvent().changes;
                                    size = list22.size();
                                    for (i = 0; i < size; i++) {
                                        if (((PointerInputChange) list22.get(i)).pressed) {
                                            pointerInputChange5 = r7;
                                            pointerInputChange6 = pointerInputChange;
                                            PointerEventPass pointerEventPass3 = PointerEventPass.Final;
                                            this.L$0 = awaitPointerEventScope2;
                                            this.L$1 = pointerInputChange6;
                                            this.L$2 = pointerInputChange5;
                                            this.L$3 = null;
                                            this.L$4 = null;
                                            this.L$5 = null;
                                            this.L$6 = null;
                                            this.label = 5;
                                            objAwaitPointerEvent = awaitPointerEventScope2.awaitPointerEvent(pointerEventPass3, this);
                                            break;
                                        }
                                    }
                                }
                                if (r7 != 0) {
                                    this.$onDragStart.invoke(pointerInputChange, r7, Offset.m395boximpl(this.$overSlop.element));
                                    this.$onDrag.invoke(r7, Offset.m395boximpl(this.$overSlop.element));
                                    function2 = this.$onDrag;
                                    orientation = this.$orientationLock;
                                    PointerEvent currentEvent = awaitPointerEventScope2.getCurrentEvent();
                                    j4 = r7.id;
                                    break;
                                }
                                return Unit.INSTANCE;
                            }
                            Offset.Companion.getClass();
                            touchSlopDetector.totalPositionChange = 0L;
                            pointerInputChange = pointerInputChange2;
                            awaitPointerEventScope2 = awaitPointerEventScope4;
                            defaultConstructorMarker = null;
                            i4 = 0;
                        } else {
                            List list3 = pointerEvent.changes;
                            int size4 = list3.size();
                            int i5 = 0;
                            while (true) {
                                if (i5 < size4) {
                                    obj2 = list3.get(i5);
                                    if (!((PointerInputChange) obj2).pressed) {
                                        i5++;
                                    }
                                } else {
                                    obj2 = null;
                                }
                            }
                            PointerInputChange pointerInputChange17 = (PointerInputChange) obj2;
                            if (pointerInputChange17 != null) {
                                ref$LongRef.element = pointerInputChange17.id;
                                pointerInputChange = pointerInputChange2;
                                awaitPointerEventScope2 = awaitPointerEventScope4;
                                defaultConstructorMarker = null;
                                i4 = 0;
                            }
                        }
                        this.L$0 = awaitPointerEventScope3;
                        this.L$1 = pointerInputChange;
                        this.L$2 = awaitPointerEventScope2;
                        this.L$3 = ref$LongRef8;
                        this.L$4 = ref$LongRef;
                        this.L$5 = touchSlopDetector;
                        this.L$6 = defaultConstructorMarker;
                        this.F$0 = fM75pointerSlopE8SPZFQ2;
                        this.label = 3;
                        objAwaitPointerEvent3 = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, this);
                        if (objAwaitPointerEvent3 != coroutineSingletons) {
                            PointerEvent pointerEvent2 = (PointerEvent) objAwaitPointerEvent3;
                            List list4 = pointerEvent2.changes;
                            int size32 = list4.size();
                            while (true) {
                                if (i4 >= size32) {
                                }
                                i4++;
                                pointerInputChange = pointerInputChange2;
                                awaitPointerEventScope2 = awaitPointerEventScope4;
                            }
                            PointerInputChange pointerInputChange162 = pointerInputChange3;
                            if (pointerInputChange162 != null) {
                                if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange162)) {
                                }
                                this.L$0 = awaitPointerEventScope3;
                                this.L$1 = pointerInputChange;
                                this.L$2 = awaitPointerEventScope2;
                                this.L$3 = ref$LongRef8;
                                this.L$4 = ref$LongRef;
                                this.L$5 = touchSlopDetector;
                                this.L$6 = defaultConstructorMarker;
                                this.F$0 = fM75pointerSlopE8SPZFQ2;
                                this.label = 3;
                                objAwaitPointerEvent3 = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, this);
                                if (objAwaitPointerEvent3 != coroutineSingletons) {
                                }
                            }
                        }
                        return coroutineSingletons;
                    }
                    pointerInputChange = pointerInputChange2;
                    awaitPointerEventScope2 = awaitPointerEventScope3;
                    r7 = 0;
                    if (r7 != 0) {
                    }
                    if (ComposeFoundationFlags.DragGesturePickUpEnabled) {
                    }
                    if (r7 != 0) {
                    }
                    return Unit.INSTANCE;
                case 4:
                    j = 9205357640488583168L;
                    j2 = 9223372034707292159L;
                    float f3 = this.F$0;
                    pointerInputChange4 = (PointerInputChange) this.L$6;
                    TouchSlopDetector touchSlopDetector4 = (TouchSlopDetector) this.L$5;
                    Ref$LongRef ref$LongRef9 = (Ref$LongRef) this.L$4;
                    ref$LongRef2 = (Ref$LongRef) this.L$3;
                    AwaitPointerEventScope awaitPointerEventScope8 = (AwaitPointerEventScope) this.L$2;
                    PointerInputChange pointerInputChange18 = (PointerInputChange) this.L$1;
                    AwaitPointerEventScope awaitPointerEventScope9 = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    touchSlopDetector = touchSlopDetector4;
                    awaitPointerEventScope2 = awaitPointerEventScope8;
                    f = f3;
                    awaitPointerEventScope3 = awaitPointerEventScope9;
                    ref$LongRef = ref$LongRef9;
                    pointerInputChange = pointerInputChange18;
                    if (!pointerInputChange4.isConsumed()) {
                    }
                    awaitPointerEventScope2 = awaitPointerEventScope3;
                    r7 = 0;
                    if (r7 != 0) {
                    }
                    if (ComposeFoundationFlags.DragGesturePickUpEnabled) {
                    }
                    if (r7 != 0) {
                    }
                    return Unit.INSTANCE;
                case 5:
                    j = 9205357640488583168L;
                    j2 = 9223372034707292159L;
                    pointerInputChange5 = (PointerInputChange) this.L$2;
                    pointerInputChange6 = (PointerInputChange) this.L$1;
                    awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objAwaitPointerEvent = obj;
                    PointerEvent pointerEvent3 = (PointerEvent) objAwaitPointerEvent;
                    List list5 = pointerEvent3.changes;
                    int size5 = list5.size();
                    int i6 = 0;
                    while (true) {
                        if (i6 < size5) {
                            if (((PointerInputChange) list5.get(i6)).isConsumed()) {
                                List list6 = pointerEvent3.changes;
                                int size6 = list6.size();
                                for (int i7 = 0; i7 < size6; i7++) {
                                    if (((PointerInputChange) list6.get(i7)).pressed) {
                                        break;
                                    }
                                }
                            } else {
                                i6++;
                            }
                        }
                    }
                    List list7 = pointerEvent3.changes;
                    int size7 = list7.size();
                    for (int i8 = 0; i8 < size7; i8++) {
                        if (((PointerInputChange) list7.get(i8)).pressed) {
                            PointerInputChange pointerInputChange19 = (PointerInputChange) CollectionsKt___CollectionsKt.firstOrNull(pointerEvent3.changes);
                            if (pointerInputChange19 != null) {
                                j3 = pointerInputChange19.position;
                            } else {
                                Offset.Companion.getClass();
                                j3 = 0;
                            }
                            long jM402minusMKHz9U = Offset.m402minusMKHz9U(j3, pointerInputChange6.position);
                            Orientation orientation3 = this.$orientationLock;
                            ref$LongRef4 = this.$overSlop;
                            PointerEvent currentEvent2 = awaitPointerEventScope2.getCurrentEvent();
                            long j7 = pointerInputChange6.id;
                            if (!DragGestureDetectorKt.m74isPointerUpDmW0f2w(currentEvent2, j7)) {
                                fM75pointerSlopE8SPZFQ = DragGestureDetectorKt.m75pointerSlopE8SPZFQ(awaitPointerEventScope2.getViewConfiguration(), pointerInputChange6.type);
                                ref$LongRef3 = new Ref$LongRef();
                                ref$LongRef3.element = j7;
                                obj3 = null;
                                touchSlopDetector2 = new TouchSlopDetector(orientation3, jM402minusMKHz9U, null);
                                awaitPointerEventScope5 = awaitPointerEventScope2;
                                this.L$0 = awaitPointerEventScope5;
                                this.L$1 = pointerInputChange6;
                                this.L$2 = awaitPointerEventScope2;
                                this.L$3 = ref$LongRef4;
                                this.L$4 = ref$LongRef3;
                                this.L$5 = touchSlopDetector2;
                                this.L$6 = obj3;
                                this.F$0 = fM75pointerSlopE8SPZFQ;
                                this.label = 6;
                                objAwaitPointerEvent2 = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, this);
                                if (objAwaitPointerEvent2 != coroutineSingletons) {
                                    PointerEvent pointerEvent4 = (PointerEvent) objAwaitPointerEvent2;
                                    List list8 = pointerEvent4.changes;
                                    size2 = list8.size();
                                    i2 = 0;
                                    while (true) {
                                        if (i2 >= size2) {
                                            ?? r13 = list8.get(i2);
                                            i3 = i2;
                                            if (PointerId.m593equalsimpl0(((PointerInputChange) r13).id, ref$LongRef3.element)) {
                                                pointerInputChange7 = r13;
                                            } else {
                                                i2 = i3 + 1;
                                            }
                                        } else {
                                            pointerInputChange7 = null;
                                        }
                                    }
                                    pointerInputChange8 = pointerInputChange7;
                                    if (pointerInputChange8 != null && !pointerInputChange8.isConsumed()) {
                                        if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange8)) {
                                            List list9 = pointerEvent4.changes;
                                            int size8 = list9.size();
                                            int i9 = 0;
                                            while (true) {
                                                if (i9 < size8) {
                                                    ?? r12 = list9.get(i9);
                                                    if (((PointerInputChange) r12).pressed) {
                                                        pointerInputChange9 = r12;
                                                    } else {
                                                        i9++;
                                                    }
                                                } else {
                                                    pointerInputChange9 = null;
                                                }
                                            }
                                            PointerInputChange pointerInputChange20 = pointerInputChange9;
                                            if (pointerInputChange20 != null) {
                                                ref$LongRef3.element = pointerInputChange20.id;
                                            }
                                        } else if ((touchSlopDetector2.m87addPointerInputChangedBAh8RU(pointerInputChange8, fM75pointerSlopE8SPZFQ) & j2) == j) {
                                            PointerEventPass pointerEventPass4 = PointerEventPass.Final;
                                            this.L$0 = awaitPointerEventScope5;
                                            this.L$1 = pointerInputChange6;
                                            this.L$2 = awaitPointerEventScope2;
                                            this.L$3 = ref$LongRef4;
                                            this.L$4 = ref$LongRef3;
                                            this.L$5 = touchSlopDetector2;
                                            this.L$6 = pointerInputChange8;
                                            this.F$0 = fM75pointerSlopE8SPZFQ;
                                            this.label = 7;
                                            break;
                                        } else {
                                            pointerInputChange8.consume();
                                            ref$LongRef4.element = PointerEventKt.positionChangeInternal(pointerInputChange8, false);
                                            if (pointerInputChange8.isConsumed()) {
                                                awaitPointerEventScope2 = awaitPointerEventScope5;
                                                pointerInputChange = pointerInputChange6;
                                                r7 = pointerInputChange8;
                                                if (ComposeFoundationFlags.DragGesturePickUpEnabled) {
                                                }
                                                if (r7 != 0) {
                                                }
                                                return Unit.INSTANCE;
                                            }
                                            Offset.Companion.getClass();
                                            touchSlopDetector2.totalPositionChange = 0L;
                                        }
                                        obj3 = null;
                                        this.L$0 = awaitPointerEventScope5;
                                        this.L$1 = pointerInputChange6;
                                        this.L$2 = awaitPointerEventScope2;
                                        this.L$3 = ref$LongRef4;
                                        this.L$4 = ref$LongRef3;
                                        this.L$5 = touchSlopDetector2;
                                        this.L$6 = obj3;
                                        this.F$0 = fM75pointerSlopE8SPZFQ;
                                        this.label = 6;
                                        objAwaitPointerEvent2 = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, this);
                                        if (objAwaitPointerEvent2 != coroutineSingletons) {
                                        }
                                    }
                                    awaitPointerEventScope2 = awaitPointerEventScope5;
                                }
                                return coroutineSingletons;
                            }
                            pointerInputChange = pointerInputChange6;
                            r7 = 0;
                            if (ComposeFoundationFlags.DragGesturePickUpEnabled) {
                            }
                            if (r7 != 0) {
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    pointerInputChange = pointerInputChange6;
                    r7 = pointerInputChange5;
                    if (ComposeFoundationFlags.DragGesturePickUpEnabled) {
                    }
                    if (r7 != 0) {
                    }
                    return Unit.INSTANCE;
                case 6:
                    j = 9205357640488583168L;
                    j2 = 9223372034707292159L;
                    float f4 = this.F$0;
                    TouchSlopDetector touchSlopDetector5 = (TouchSlopDetector) this.L$5;
                    Ref$LongRef ref$LongRef10 = (Ref$LongRef) this.L$4;
                    Ref$LongRef ref$LongRef11 = (Ref$LongRef) this.L$3;
                    AwaitPointerEventScope awaitPointerEventScope10 = (AwaitPointerEventScope) this.L$2;
                    PointerInputChange pointerInputChange21 = (PointerInputChange) this.L$1;
                    AwaitPointerEventScope awaitPointerEventScope11 = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    objAwaitPointerEvent2 = obj;
                    fM75pointerSlopE8SPZFQ = f4;
                    ref$LongRef3 = ref$LongRef10;
                    ref$LongRef4 = ref$LongRef11;
                    awaitPointerEventScope2 = awaitPointerEventScope10;
                    awaitPointerEventScope5 = awaitPointerEventScope11;
                    touchSlopDetector2 = touchSlopDetector5;
                    pointerInputChange6 = pointerInputChange21;
                    PointerEvent pointerEvent42 = (PointerEvent) objAwaitPointerEvent2;
                    List list82 = pointerEvent42.changes;
                    size2 = list82.size();
                    i2 = 0;
                    while (true) {
                        if (i2 >= size2) {
                        }
                        i2 = i3 + 1;
                    }
                    pointerInputChange8 = pointerInputChange7;
                    if (pointerInputChange8 != null) {
                        if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange8)) {
                        }
                        obj3 = null;
                        this.L$0 = awaitPointerEventScope5;
                        this.L$1 = pointerInputChange6;
                        this.L$2 = awaitPointerEventScope2;
                        this.L$3 = ref$LongRef4;
                        this.L$4 = ref$LongRef3;
                        this.L$5 = touchSlopDetector2;
                        this.L$6 = obj3;
                        this.F$0 = fM75pointerSlopE8SPZFQ;
                        this.label = 6;
                        objAwaitPointerEvent2 = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, this);
                        if (objAwaitPointerEvent2 != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    awaitPointerEventScope2 = awaitPointerEventScope5;
                    pointerInputChange = pointerInputChange6;
                    r7 = 0;
                    if (ComposeFoundationFlags.DragGesturePickUpEnabled) {
                    }
                    if (r7 != 0) {
                    }
                    return Unit.INSTANCE;
                case 7:
                    float f5 = this.F$0;
                    pointerInputChange8 = (PointerInputChange) this.L$6;
                    TouchSlopDetector touchSlopDetector6 = (TouchSlopDetector) this.L$5;
                    Ref$LongRef ref$LongRef12 = (Ref$LongRef) this.L$4;
                    Ref$LongRef ref$LongRef13 = (Ref$LongRef) this.L$3;
                    j = 9205357640488583168L;
                    AwaitPointerEventScope awaitPointerEventScope12 = (AwaitPointerEventScope) this.L$2;
                    PointerInputChange pointerInputChange22 = (PointerInputChange) this.L$1;
                    j2 = 9223372034707292159L;
                    AwaitPointerEventScope awaitPointerEventScope13 = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    awaitPointerEventScope2 = awaitPointerEventScope12;
                    pointerInputChange6 = pointerInputChange22;
                    fM75pointerSlopE8SPZFQ = f5;
                    awaitPointerEventScope5 = awaitPointerEventScope13;
                    touchSlopDetector2 = touchSlopDetector6;
                    ref$LongRef3 = ref$LongRef12;
                    ref$LongRef4 = ref$LongRef13;
                    if (pointerInputChange8.isConsumed()) {
                        awaitPointerEventScope2 = awaitPointerEventScope5;
                        pointerInputChange = pointerInputChange6;
                        r7 = 0;
                        if (ComposeFoundationFlags.DragGesturePickUpEnabled) {
                        }
                        if (r7 != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                    obj3 = null;
                    this.L$0 = awaitPointerEventScope5;
                    this.L$1 = pointerInputChange6;
                    this.L$2 = awaitPointerEventScope2;
                    this.L$3 = ref$LongRef4;
                    this.L$4 = ref$LongRef3;
                    this.L$5 = touchSlopDetector2;
                    this.L$6 = obj3;
                    this.F$0 = fM75pointerSlopE8SPZFQ;
                    this.label = 6;
                    objAwaitPointerEvent2 = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, this);
                    if (objAwaitPointerEvent2 != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 8:
                    Ref$LongRef ref$LongRef14 = (Ref$LongRef) this.L$4;
                    AwaitPointerEventScope awaitPointerEventScope14 = (AwaitPointerEventScope) this.L$3;
                    Orientation orientation4 = (Orientation) this.L$2;
                    Function2 function22 = (Function2) this.L$1;
                    awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    Object objAwaitPointerEvent4 = obj;
                    PointerEvent pointerEvent5 = (PointerEvent) objAwaitPointerEvent4;
                    List list10 = pointerEvent5.changes;
                    int size9 = list10.size();
                    int i10 = 0;
                    while (true) {
                        if (i10 < size9) {
                            pointerInputChange11 = list10.get(i10);
                            int i11 = i10;
                            if (!PointerId.m593equalsimpl0(((PointerInputChange) pointerInputChange11).id, ref$LongRef14.element)) {
                                i10 = i11 + 1;
                            }
                        } else {
                            pointerInputChange11 = null;
                        }
                    }
                    PointerInputChange pointerInputChange23 = pointerInputChange11;
                    if (pointerInputChange23 == null) {
                        pointerInputChange12 = null;
                    } else if (!PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange23)) {
                        long jPositionChangeInternal = PointerEventKt.positionChangeInternal(pointerInputChange23, true);
                        break;
                    } else {
                        List list11 = pointerEvent5.changes;
                        int size10 = list11.size();
                        int i12 = 0;
                        while (true) {
                            if (i12 < size10) {
                                obj4 = list11.get(i12);
                                if (!((PointerInputChange) obj4).pressed) {
                                    i12++;
                                }
                            } else {
                                obj4 = null;
                            }
                        }
                        PointerInputChange pointerInputChange24 = (PointerInputChange) obj4;
                        if (pointerInputChange24 != null) {
                            ref$LongRef14.element = pointerInputChange24.id;
                            this.L$0 = awaitPointerEventScope2;
                            this.L$1 = function22;
                            this.L$2 = orientation4;
                            this.L$3 = awaitPointerEventScope14;
                            this.L$4 = ref$LongRef14;
                            this.L$5 = null;
                            this.L$6 = null;
                            this.label = 8;
                            objAwaitPointerEvent4 = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope14).awaitPointerEvent(PointerEventPass.Main, this);
                            break;
                        } else {
                            pointerInputChange12 = pointerInputChange23;
                        }
                    }
                    if (pointerInputChange12 == null || pointerInputChange12.isConsumed()) {
                        pointerInputChange10 = null;
                    } else if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange12)) {
                        pointerInputChange10 = pointerInputChange12;
                    } else {
                        function22.invoke(pointerInputChange12, Offset.m395boximpl(PointerEventKt.positionChangeInternal(pointerInputChange12, false)));
                        pointerInputChange12.consume();
                        orientation = orientation4;
                        function2 = function22;
                        j4 = pointerInputChange12.id;
                        Ref$LongRef ref$LongRef15 = new Ref$LongRef();
                        ref$LongRef15.element = j4;
                        function22 = function2;
                        orientation4 = orientation;
                        ref$LongRef14 = ref$LongRef15;
                        awaitPointerEventScope14 = awaitPointerEventScope2;
                        this.L$0 = awaitPointerEventScope2;
                        this.L$1 = function22;
                        this.L$2 = orientation4;
                        this.L$3 = awaitPointerEventScope14;
                        this.L$4 = ref$LongRef14;
                        this.L$5 = null;
                        this.L$6 = null;
                        this.label = 8;
                        objAwaitPointerEvent4 = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope14).awaitPointerEvent(PointerEventPass.Main, this);
                    }
                    if (pointerInputChange10 == null) {
                        this.$onDragCancel.invoke();
                    } else {
                        this.$onDragEnd.mo781invoke(pointerInputChange10);
                    }
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectDragGesturesAfterLongPress$5, reason: invalid class name and case insensitive filesystem */
    final class C06915 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function2 $onDrag;
        final /* synthetic */ Function0 $onDragCancel;
        final /* synthetic */ Function0 $onDragEnd;
        final /* synthetic */ Function1 $onDragStart;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C06915(Function1 function1, Function0 function0, Function0 function02, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$onDragStart = function1;
            this.$onDragEnd = function0;
            this.$onDragCancel = function02;
            this.$onDrag = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C06915 c06915 = new C06915(this.$onDragStart, this.$onDragEnd, this.$onDragCancel, this.$onDrag, continuation);
            c06915.L$0 = obj;
            return c06915;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C06915) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x0059 A[Catch: CancellationException -> 0x0017, TryCatch #0 {CancellationException -> 0x0017, blocks: (B:8:0x0013, B:33:0x0079, B:35:0x0081, B:37:0x0091, B:39:0x009d, B:40:0x00a0, B:41:0x00a3, B:42:0x00a9, B:15:0x0026, B:27:0x0055, B:29:0x0059, B:18:0x002e, B:24:0x0046, B:21:0x003a), top: B:47:0x0007 }] */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0081 A[Catch: CancellationException -> 0x0017, TryCatch #0 {CancellationException -> 0x0017, blocks: (B:8:0x0013, B:33:0x0079, B:35:0x0081, B:37:0x0091, B:39:0x009d, B:40:0x00a0, B:41:0x00a3, B:42:0x00a9, B:15:0x0026, B:27:0x0055, B:29:0x0059, B:18:0x002e, B:24:0x0046, B:21:0x003a), top: B:47:0x0007 }] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00a9 A[Catch: CancellationException -> 0x0017, TRY_LEAVE, TryCatch #0 {CancellationException -> 0x0017, blocks: (B:8:0x0013, B:33:0x0079, B:35:0x0081, B:37:0x0091, B:39:0x009d, B:40:0x00a0, B:41:0x00a3, B:42:0x00a9, B:15:0x0026, B:27:0x0055, B:29:0x0059, B:18:0x002e, B:24:0x0046, B:21:0x003a), top: B:47:0x0007 }] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            AwaitPointerEventScope awaitPointerEventScope;
            PointerInputChange pointerInputChange;
            AwaitPointerEventScope awaitPointerEventScope2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                    this.L$0 = awaitPointerEventScope;
                    this.label = 1;
                    obj = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, null, this, 2);
                    if (obj == coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        if (((Boolean) obj).booleanValue()) {
                            this.$onDragCancel.invoke();
                        } else {
                            List list = awaitPointerEventScope2.getCurrentEvent().changes;
                            int size = list.size();
                            for (int i2 = 0; i2 < size; i2++) {
                                PointerInputChange pointerInputChange2 = (PointerInputChange) list.get(i2);
                                if (PointerEventKt.changedToUp(pointerInputChange2)) {
                                    pointerInputChange2.consume();
                                }
                            }
                            this.$onDragEnd.invoke();
                        }
                        return Unit.INSTANCE;
                    }
                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    pointerInputChange = (PointerInputChange) obj;
                    if (pointerInputChange != null) {
                        this.$onDragStart.mo781invoke(Offset.m395boximpl(pointerInputChange.position));
                        long j = pointerInputChange.id;
                        final Function2 function2 = this.$onDrag;
                        Function1 function1 = new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectDragGesturesAfterLongPress.5.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                PointerInputChange pointerInputChange3 = (PointerInputChange) obj2;
                                function2.invoke(pointerInputChange3, Offset.m395boximpl(PointerEventKt.positionChangeInternal(pointerInputChange3, false)));
                                pointerInputChange3.consume();
                                return Unit.INSTANCE;
                            }
                        };
                        this.L$0 = awaitPointerEventScope;
                        this.label = 3;
                        obj = DragGestureDetectorKt.m72dragjO51t88(awaitPointerEventScope, j, function1, this);
                        if (obj != coroutineSingletons) {
                            awaitPointerEventScope2 = awaitPointerEventScope;
                            if (((Boolean) obj).booleanValue()) {
                            }
                        }
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                long j2 = ((PointerInputChange) obj).id;
                this.L$0 = awaitPointerEventScope;
                this.label = 2;
                obj = DragGestureDetectorKt.m70awaitLongPressOrCancellationrnUCldI(awaitPointerEventScope, j2, this);
                if (obj != coroutineSingletons) {
                    pointerInputChange = (PointerInputChange) obj;
                    if (pointerInputChange != null) {
                    }
                    return Unit.INSTANCE;
                }
                return coroutineSingletons;
            } catch (CancellationException e) {
                this.$onDragCancel.invoke();
                throw e;
            }
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$5, reason: invalid class name and case insensitive filesystem */
    final class C06955 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ Function0 $onDragCancel;
        final /* synthetic */ Function0 $onDragEnd;
        final /* synthetic */ Function1 $onDragStart;
        final /* synthetic */ Function2 $onHorizontalDrag;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C06955(Function1 function1, Function2 function2, Function0 function0, Function0 function02, Continuation continuation) {
            super(2, continuation);
            this.$onDragStart = function1;
            this.$onHorizontalDrag = function2;
            this.$onDragEnd = function0;
            this.$onDragCancel = function02;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C06955 c06955 = new C06955(this.$onDragStart, this.$onHorizontalDrag, this.$onDragEnd, this.$onDragCancel, continuation);
            c06955.L$0 = obj;
            return c06955;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C06955) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x0094, code lost:
        
            if (r12 == r0) goto L24;
         */
        /* JADX WARN: Removed duplicated region for block: B:22:0x006a  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            AwaitPointerEventScope awaitPointerEventScope;
            final Ref$FloatRef ref$FloatRef;
            C06955 c06955;
            AwaitPointerEventScope awaitPointerEventScope2;
            PointerInputChange pointerInputChange;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                this.L$0 = awaitPointerEventScope;
                this.label = 1;
                obj = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope, null, this, 2);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    c06955 = this;
                    if (((Boolean) obj).booleanValue()) {
                        c06955.$onDragEnd.invoke();
                    } else {
                        c06955.$onDragCancel.invoke();
                    }
                    return Unit.INSTANCE;
                }
                ref$FloatRef = (Ref$FloatRef) this.L$1;
                awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                c06955 = this;
                pointerInputChange = (PointerInputChange) obj;
                if (pointerInputChange != null) {
                    c06955.$onDragStart.mo781invoke(Offset.m395boximpl(pointerInputChange.position));
                    c06955.$onHorizontalDrag.invoke(pointerInputChange, new Float(ref$FloatRef.element));
                    final Function2 function2 = c06955.$onHorizontalDrag;
                    Function1 function1 = new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectHorizontalDragGestures.5.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            PointerInputChange pointerInputChange2 = (PointerInputChange) obj2;
                            function2.invoke(pointerInputChange2, Float.valueOf(Float.intBitsToFloat((int) (PointerEventKt.positionChangeInternal(pointerInputChange2, false) >> 32))));
                            pointerInputChange2.consume();
                            return Unit.INSTANCE;
                        }
                    };
                    c06955.L$0 = null;
                    c06955.L$1 = null;
                    c06955.label = 3;
                    obj = DragGestureDetectorKt.m73horizontalDragjO51t88(awaitPointerEventScope2, pointerInputChange.id, function1, c06955);
                }
                return Unit.INSTANCE;
            }
            awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            AwaitPointerEventScope awaitPointerEventScope3 = awaitPointerEventScope;
            PointerInputChange pointerInputChange2 = (PointerInputChange) obj;
            ref$FloatRef = new Ref$FloatRef();
            long j = pointerInputChange2.id;
            Function2 function22 = new Function2() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt$detectHorizontalDragGestures$5$drag$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    float fFloatValue = ((Number) obj3).floatValue();
                    ((PointerInputChange) obj2).consume();
                    ref$FloatRef.element = fFloatValue;
                    return Unit.INSTANCE;
                }
            };
            this.L$0 = awaitPointerEventScope3;
            this.L$1 = ref$FloatRef;
            this.label = 2;
            c06955 = this;
            obj = DragGestureDetectorKt.m68awaitHorizontalPointerSlopOrCancellationgDDlDlE(awaitPointerEventScope3, j, pointerInputChange2.type, function22, c06955);
            if (obj != coroutineSingletons) {
                awaitPointerEventScope2 = awaitPointerEventScope3;
                pointerInputChange = (PointerInputChange) obj;
                if (pointerInputChange != null) {
                }
                return Unit.INSTANCE;
            }
            return coroutineSingletons;
        }
    }

    static {
        Dp.Companion companion = Dp.Companion;
        mouseToTouchSlopRatio = ((float) 0.125d) / 18;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d0, code lost:
    
        if (androidx.compose.ui.geometry.Offset.m398equalsimpl0(r8, 0) == false) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0065 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x008f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0066 -> B:22:0x006b). Please report as a decompilation issue!!! */
    /* renamed from: awaitDragOrCancellation-rnUCldI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m67awaitDragOrCancellationrnUCldI(AwaitPointerEventScope awaitPointerEventScope, long j, ContinuationImpl continuationImpl) {
        DragGestureDetectorKt$awaitDragOrCancellation$1 dragGestureDetectorKt$awaitDragOrCancellation$1;
        Ref$LongRef ref$LongRef;
        AwaitPointerEventScope awaitPointerEventScope2;
        Object objAwaitPointerEvent;
        Object obj;
        Object obj2;
        if (continuationImpl instanceof DragGestureDetectorKt$awaitDragOrCancellation$1) {
            dragGestureDetectorKt$awaitDragOrCancellation$1 = (DragGestureDetectorKt$awaitDragOrCancellation$1) continuationImpl;
            int i = dragGestureDetectorKt$awaitDragOrCancellation$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dragGestureDetectorKt$awaitDragOrCancellation$1.label = i - Integer.MIN_VALUE;
            } else {
                dragGestureDetectorKt$awaitDragOrCancellation$1 = new DragGestureDetectorKt$awaitDragOrCancellation$1(continuationImpl);
            }
        }
        Object obj3 = dragGestureDetectorKt$awaitDragOrCancellation$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dragGestureDetectorKt$awaitDragOrCancellation$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj3);
            if (!m74isPointerUpDmW0f2w(awaitPointerEventScope.getCurrentEvent(), j)) {
                ref$LongRef = new Ref$LongRef();
                ref$LongRef.element = j;
                awaitPointerEventScope2 = awaitPointerEventScope;
                dragGestureDetectorKt$awaitDragOrCancellation$1.L$0 = awaitPointerEventScope2;
                dragGestureDetectorKt$awaitDragOrCancellation$1.L$1 = ref$LongRef;
                dragGestureDetectorKt$awaitDragOrCancellation$1.label = 1;
                objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitDragOrCancellation$1);
                if (objAwaitPointerEvent != coroutineSingletons) {
                }
            }
            return null;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Ref$LongRef ref$LongRef2 = (Ref$LongRef) dragGestureDetectorKt$awaitDragOrCancellation$1.L$1;
        AwaitPointerEventScope awaitPointerEventScope3 = (AwaitPointerEventScope) dragGestureDetectorKt$awaitDragOrCancellation$1.L$0;
        ResultKt.throwOnFailure(obj3);
        Ref$LongRef ref$LongRef3 = ref$LongRef2;
        awaitPointerEventScope2 = awaitPointerEventScope3;
        PointerEvent pointerEvent = (PointerEvent) obj3;
        List list = pointerEvent.changes;
        int size = list.size();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i4 < size) {
                obj = null;
                break;
            }
            obj = list.get(i4);
            if (PointerId.m593equalsimpl0(((PointerInputChange) obj).id, ref$LongRef3.element)) {
                break;
            }
            i4++;
        }
        PointerInputChange pointerInputChange = (PointerInputChange) obj;
        if (pointerInputChange == null) {
            if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
                List list2 = pointerEvent.changes;
                int size2 = list2.size();
                while (true) {
                    if (i3 >= size2) {
                        obj2 = null;
                        break;
                    }
                    obj2 = list2.get(i3);
                    if (((PointerInputChange) obj2).pressed) {
                        break;
                    }
                    i3++;
                }
                PointerInputChange pointerInputChange2 = (PointerInputChange) obj2;
                if (pointerInputChange2 != null) {
                    ref$LongRef3.element = pointerInputChange2.id;
                    ref$LongRef = ref$LongRef3;
                    dragGestureDetectorKt$awaitDragOrCancellation$1.L$0 = awaitPointerEventScope2;
                    dragGestureDetectorKt$awaitDragOrCancellation$1.L$1 = ref$LongRef;
                    dragGestureDetectorKt$awaitDragOrCancellation$1.label = 1;
                    objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitDragOrCancellation$1);
                    if (objAwaitPointerEvent != coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    Ref$LongRef ref$LongRef4 = ref$LongRef;
                    obj3 = objAwaitPointerEvent;
                    ref$LongRef3 = ref$LongRef4;
                }
            } else {
                long jPositionChangeInternal = PointerEventKt.positionChangeInternal(pointerInputChange, true);
                Offset.Companion.getClass();
            }
            PointerEvent pointerEvent2 = (PointerEvent) obj3;
            List list3 = pointerEvent2.changes;
            int size3 = list3.size();
            int i32 = 0;
            int i42 = 0;
            while (true) {
                if (i42 < size3) {
                }
                i42++;
            }
            PointerInputChange pointerInputChange3 = (PointerInputChange) obj;
            if (pointerInputChange3 == null) {
                pointerInputChange3 = null;
            }
        }
        if (pointerInputChange3 == null || pointerInputChange3.isConsumed()) {
            return null;
        }
        return pointerInputChange3;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x018f -> B:62:0x0195). Please report as a decompilation issue!!! */
    /* renamed from: awaitHorizontalPointerSlopOrCancellation-gDDlDlE, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m68awaitHorizontalPointerSlopOrCancellationgDDlDlE(AwaitPointerEventScope awaitPointerEventScope, long j, int i, Function2 function2, BaseContinuationImpl baseContinuationImpl) {
        DragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1 dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1;
        float fM75pointerSlopE8SPZFQ;
        Ref$LongRef ref$LongRef;
        Function2 function22;
        TouchSlopDetector touchSlopDetector;
        AwaitPointerEventScope awaitPointerEventScope2;
        DragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1 dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$12;
        Ref$LongRef ref$LongRef2;
        float f;
        TouchSlopDetector touchSlopDetector2;
        int size;
        int i2;
        Object obj;
        Object obj2;
        PointerInputChange pointerInputChange;
        Object obj3;
        Object objAwaitPointerEvent;
        if (baseContinuationImpl instanceof DragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1) {
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1 = (DragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1) baseContinuationImpl;
            int i3 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.label = i3 - Integer.MIN_VALUE;
            } else {
                dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1 = new DragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1(baseContinuationImpl);
            }
        }
        Object obj4 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.label;
        int i5 = 1;
        Object obj5 = null;
        if (i4 == 0) {
            ResultKt.throwOnFailure(obj4);
            Orientation orientation = Orientation.Horizontal;
            Offset.Companion.getClass();
            if (m74isPointerUpDmW0f2w(awaitPointerEventScope.getCurrentEvent(), j)) {
                return null;
            }
            fM75pointerSlopE8SPZFQ = m75pointerSlopE8SPZFQ(awaitPointerEventScope.getViewConfiguration(), i);
            ref$LongRef = new Ref$LongRef();
            ref$LongRef.element = j;
            function22 = function2;
            touchSlopDetector = new TouchSlopDetector(orientation, 0L, null);
            awaitPointerEventScope2 = awaitPointerEventScope;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$0 = function22;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$1 = awaitPointerEventScope2;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$2 = ref$LongRef;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$3 = touchSlopDetector;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$4 = obj5;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.F$0 = fM75pointerSlopE8SPZFQ;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.label = i5;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i4 == 1) {
            float f2 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.F$0;
            TouchSlopDetector touchSlopDetector3 = (TouchSlopDetector) dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$3;
            Ref$LongRef ref$LongRef3 = (Ref$LongRef) dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$2;
            AwaitPointerEventScope awaitPointerEventScope3 = (AwaitPointerEventScope) dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$1;
            Function2 function23 = (Function2) dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$0;
            ResultKt.throwOnFailure(obj4);
            f = f2;
            awaitPointerEventScope2 = awaitPointerEventScope3;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$12 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1;
            touchSlopDetector2 = touchSlopDetector3;
            function22 = function23;
            ref$LongRef2 = ref$LongRef3;
            DragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1 dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$13 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$12;
            PointerEvent pointerEvent = (PointerEvent) obj4;
            List list = pointerEvent.changes;
            size = list.size();
            int i6 = 0;
            i2 = 0;
            while (true) {
                if (i2 < size) {
                }
                i2++;
                obj5 = obj;
            }
            pointerInputChange = (PointerInputChange) obj2;
            if (pointerInputChange != null) {
                return obj;
            }
            if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
            }
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$0 = function22;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$1 = awaitPointerEventScope2;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$2 = ref$LongRef;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$3 = touchSlopDetector;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$4 = obj5;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.F$0 = fM75pointerSlopE8SPZFQ;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.label = i5;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i4 != 2) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        float f3 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.F$0;
        PointerInputChange pointerInputChange2 = (PointerInputChange) dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$4;
        TouchSlopDetector touchSlopDetector4 = (TouchSlopDetector) dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$3;
        ref$LongRef = (Ref$LongRef) dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$2;
        AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$1;
        Function2 function24 = (Function2) dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$0;
        ResultKt.throwOnFailure(obj4);
        touchSlopDetector = touchSlopDetector4;
        long j2 = 0;
        obj = null;
        fM75pointerSlopE8SPZFQ = f3;
        awaitPointerEventScope2 = awaitPointerEventScope4;
        if (!pointerInputChange2.isConsumed()) {
            return obj;
        }
        function22 = function24;
        obj5 = obj;
        i5 = 1;
        dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$0 = function22;
        dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$1 = awaitPointerEventScope2;
        dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$2 = ref$LongRef;
        dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$3 = touchSlopDetector;
        dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$4 = obj5;
        dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.F$0 = fM75pointerSlopE8SPZFQ;
        dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.label = i5;
        objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1);
        if (objAwaitPointerEvent != coroutineSingletons) {
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$12 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1;
            touchSlopDetector2 = touchSlopDetector;
            obj4 = objAwaitPointerEvent;
            ref$LongRef2 = ref$LongRef;
            f = fM75pointerSlopE8SPZFQ;
            DragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1 dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$12;
            PointerEvent pointerEvent2 = (PointerEvent) obj4;
            List list2 = pointerEvent2.changes;
            size = list2.size();
            int i62 = 0;
            i2 = 0;
            while (true) {
                if (i2 < size) {
                    obj = obj5;
                    obj2 = obj;
                    break;
                }
                obj2 = list2.get(i2);
                obj = obj5;
                if (PointerId.m593equalsimpl0(((PointerInputChange) obj2).id, ref$LongRef2.element)) {
                    break;
                }
                i2++;
                obj5 = obj;
            }
            pointerInputChange = (PointerInputChange) obj2;
            if (pointerInputChange != null || pointerInputChange.isConsumed()) {
                return obj;
            }
            if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
                long jM87addPointerInputChangedBAh8RU = touchSlopDetector2.m87addPointerInputChangedBAh8RU(pointerInputChange, f);
                if ((9223372034707292159L & jM87addPointerInputChangedBAh8RU) != 9205357640488583168L) {
                    function22.invoke(pointerInputChange, new Float(Float.intBitsToFloat((int) (jM87addPointerInputChangedBAh8RU >> 32))));
                    if (pointerInputChange.isConsumed()) {
                        return pointerInputChange;
                    }
                    Offset.Companion.getClass();
                    touchSlopDetector2.totalPositionChange = 0L;
                    touchSlopDetector = touchSlopDetector2;
                    dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132;
                    fM75pointerSlopE8SPZFQ = f;
                    ref$LongRef = ref$LongRef2;
                    obj5 = obj;
                    i5 = 1;
                } else {
                    j2 = 0;
                    PointerEventPass pointerEventPass = PointerEventPass.Final;
                    dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132.L$0 = function22;
                    dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132.L$1 = awaitPointerEventScope2;
                    dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132.L$2 = ref$LongRef2;
                    dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132.L$3 = touchSlopDetector2;
                    dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132.L$4 = pointerInputChange;
                    dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132.F$0 = f;
                    dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132.label = 2;
                    if (awaitPointerEventScope2.awaitPointerEvent(pointerEventPass, dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132) != coroutineSingletons) {
                        function24 = function22;
                        touchSlopDetector = touchSlopDetector2;
                        dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132;
                        pointerInputChange2 = pointerInputChange;
                        fM75pointerSlopE8SPZFQ = f;
                        ref$LongRef = ref$LongRef2;
                        if (!pointerInputChange2.isConsumed()) {
                        }
                    }
                }
            } else {
                List list3 = pointerEvent2.changes;
                int size2 = list3.size();
                while (true) {
                    if (i62 >= size2) {
                        obj3 = obj;
                        break;
                    }
                    obj3 = list3.get(i62);
                    if (((PointerInputChange) obj3).pressed) {
                        break;
                    }
                    i62++;
                }
                PointerInputChange pointerInputChange3 = (PointerInputChange) obj3;
                if (pointerInputChange3 == null) {
                    return obj;
                }
                ref$LongRef2.element = pointerInputChange3.id;
                touchSlopDetector = touchSlopDetector2;
                dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1 = dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$132;
                fM75pointerSlopE8SPZFQ = f;
                ref$LongRef = ref$LongRef2;
                obj5 = obj;
                i5 = 1;
            }
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$0 = function22;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$1 = awaitPointerEventScope2;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$2 = ref$LongRef;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$3 = touchSlopDetector;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.L$4 = obj5;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.F$0 = fM75pointerSlopE8SPZFQ;
            dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1.label = i5;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitHorizontalPointerSlopOrCancellation$1);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00fa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x0195 -> B:61:0x019b). Please report as a decompilation issue!!! */
    /* renamed from: awaitHorizontalTouchSlopOrCancellation-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m69awaitHorizontalTouchSlopOrCancellationjO51t88(AwaitPointerEventScope awaitPointerEventScope, long j, NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0 nestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0, BaseContinuationImpl baseContinuationImpl) {
        DragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1 dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1;
        Ref$LongRef ref$LongRef;
        Function2 function2;
        DragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1 dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12;
        float f;
        TouchSlopDetector touchSlopDetector;
        AwaitPointerEventScope awaitPointerEventScope2;
        Ref$LongRef ref$LongRef2;
        DragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1 dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13;
        float f2;
        TouchSlopDetector touchSlopDetector2;
        int size;
        int i;
        Object obj;
        Object obj2;
        PointerInputChange pointerInputChange;
        Object obj3;
        Object objAwaitPointerEvent;
        if (baseContinuationImpl instanceof DragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1) {
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1 = (DragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1) baseContinuationImpl;
            int i2 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.label = i2 - Integer.MIN_VALUE;
            } else {
                dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1 = new DragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1(baseContinuationImpl);
            }
        }
        Object obj4 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.label;
        int i4 = 1;
        Object obj5 = null;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj4);
            PointerType.Companion.getClass();
            int i5 = PointerType.Touch;
            Orientation orientation = Orientation.Horizontal;
            Offset.Companion.getClass();
            if (m74isPointerUpDmW0f2w(awaitPointerEventScope.getCurrentEvent(), j)) {
                return null;
            }
            float fM75pointerSlopE8SPZFQ = m75pointerSlopE8SPZFQ(awaitPointerEventScope.getViewConfiguration(), i5);
            ref$LongRef = new Ref$LongRef();
            ref$LongRef.element = j;
            TouchSlopDetector touchSlopDetector3 = new TouchSlopDetector(orientation, 0L, null);
            function2 = nestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1;
            f = fM75pointerSlopE8SPZFQ;
            touchSlopDetector = touchSlopDetector3;
            awaitPointerEventScope2 = awaitPointerEventScope;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$0 = function2;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$1 = awaitPointerEventScope2;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$2 = ref$LongRef;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$3 = touchSlopDetector;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$4 = obj5;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.F$0 = f;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.label = i4;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i3 == 1) {
            float f3 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.F$0;
            TouchSlopDetector touchSlopDetector4 = (TouchSlopDetector) dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.L$3;
            Ref$LongRef ref$LongRef3 = (Ref$LongRef) dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.L$2;
            AwaitPointerEventScope awaitPointerEventScope3 = (AwaitPointerEventScope) dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.L$1;
            Function2 function22 = (Function2) dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.L$0;
            ResultKt.throwOnFailure(obj4);
            f2 = f3;
            awaitPointerEventScope2 = awaitPointerEventScope3;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1;
            touchSlopDetector2 = touchSlopDetector4;
            function2 = function22;
            ref$LongRef2 = ref$LongRef3;
            PointerEvent pointerEvent = (PointerEvent) obj4;
            List list = pointerEvent.changes;
            size = list.size();
            int i6 = 0;
            i = 0;
            while (true) {
                if (i < size) {
                }
                i++;
                obj5 = obj;
            }
            pointerInputChange = (PointerInputChange) obj2;
            if (pointerInputChange != null) {
                return obj;
            }
            if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
            }
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$0 = function2;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$1 = awaitPointerEventScope2;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$2 = ref$LongRef;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$3 = touchSlopDetector;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$4 = obj5;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.F$0 = f;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.label = i4;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i3 != 2) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        float f4 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.F$0;
        PointerInputChange pointerInputChange2 = (PointerInputChange) dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.L$4;
        TouchSlopDetector touchSlopDetector5 = (TouchSlopDetector) dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.L$3;
        ref$LongRef = (Ref$LongRef) dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.L$2;
        AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.L$1;
        Function2 function23 = (Function2) dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1.L$0;
        ResultKt.throwOnFailure(obj4);
        touchSlopDetector = touchSlopDetector5;
        long j2 = 0;
        obj = null;
        dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$1;
        f = f4;
        awaitPointerEventScope2 = awaitPointerEventScope4;
        if (!pointerInputChange2.isConsumed()) {
            return obj;
        }
        function2 = function23;
        obj5 = obj;
        i4 = 1;
        dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$0 = function2;
        dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$1 = awaitPointerEventScope2;
        dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$2 = ref$LongRef;
        dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$3 = touchSlopDetector;
        dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$4 = obj5;
        dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.F$0 = f;
        dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.label = i4;
        objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12);
        if (objAwaitPointerEvent != coroutineSingletons) {
            float f5 = f;
            touchSlopDetector2 = touchSlopDetector;
            obj4 = objAwaitPointerEvent;
            ref$LongRef2 = ref$LongRef;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12;
            f2 = f5;
            PointerEvent pointerEvent2 = (PointerEvent) obj4;
            List list2 = pointerEvent2.changes;
            size = list2.size();
            int i62 = 0;
            i = 0;
            while (true) {
                if (i < size) {
                    obj = obj5;
                    obj2 = obj;
                    break;
                }
                obj2 = list2.get(i);
                obj = obj5;
                if (PointerId.m593equalsimpl0(((PointerInputChange) obj2).id, ref$LongRef2.element)) {
                    break;
                }
                i++;
                obj5 = obj;
            }
            pointerInputChange = (PointerInputChange) obj2;
            if (pointerInputChange != null || pointerInputChange.isConsumed()) {
                return obj;
            }
            if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
                long jM87addPointerInputChangedBAh8RU = touchSlopDetector2.m87addPointerInputChangedBAh8RU(pointerInputChange, f2);
                if ((9223372034707292159L & jM87addPointerInputChangedBAh8RU) != 9205357640488583168L) {
                    function2.invoke(pointerInputChange, new Float(Float.intBitsToFloat((int) (jM87addPointerInputChangedBAh8RU >> 32))));
                    if (pointerInputChange.isConsumed()) {
                        return pointerInputChange;
                    }
                    Offset.Companion.getClass();
                    touchSlopDetector2.totalPositionChange = 0L;
                    touchSlopDetector = touchSlopDetector2;
                    f = f2;
                    dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13;
                    ref$LongRef = ref$LongRef2;
                    obj5 = obj;
                    i4 = 1;
                } else {
                    j2 = 0;
                    PointerEventPass pointerEventPass = PointerEventPass.Final;
                    dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13.L$0 = function2;
                    dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13.L$1 = awaitPointerEventScope2;
                    dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13.L$2 = ref$LongRef2;
                    dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13.L$3 = touchSlopDetector2;
                    dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13.L$4 = pointerInputChange;
                    dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13.F$0 = f2;
                    dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13.label = 2;
                    if (awaitPointerEventScope2.awaitPointerEvent(pointerEventPass, dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13) != coroutineSingletons) {
                        function23 = function2;
                        touchSlopDetector = touchSlopDetector2;
                        f = f2;
                        pointerInputChange2 = pointerInputChange;
                        dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13;
                        ref$LongRef = ref$LongRef2;
                        if (!pointerInputChange2.isConsumed()) {
                        }
                    }
                }
            } else {
                List list3 = pointerEvent2.changes;
                int size2 = list3.size();
                while (true) {
                    if (i62 >= size2) {
                        obj3 = obj;
                        break;
                    }
                    obj3 = list3.get(i62);
                    if (((PointerInputChange) obj3).pressed) {
                        break;
                    }
                    i62++;
                }
                PointerInputChange pointerInputChange3 = (PointerInputChange) obj3;
                if (pointerInputChange3 == null) {
                    return obj;
                }
                ref$LongRef2.element = pointerInputChange3.id;
                touchSlopDetector = touchSlopDetector2;
                f = f2;
                dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$13;
                ref$LongRef = ref$LongRef2;
                obj5 = obj;
                i4 = 1;
            }
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$0 = function2;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$1 = awaitPointerEventScope2;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$2 = ref$LongRef;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$3 = touchSlopDetector;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.L$4 = obj5;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.F$0 = f;
            dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12.label = i4;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitHorizontalTouchSlopOrCancellation$12);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r10v3, types: [kotlin.jvm.internal.Ref$ObjectRef] */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r11v3, types: [T, androidx.compose.ui.input.pointer.PointerInputChange, java.lang.Object] */
    /* renamed from: awaitLongPressOrCancellation-rnUCldI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m70awaitLongPressOrCancellationrnUCldI(AwaitPointerEventScope awaitPointerEventScope, long j, BaseContinuationImpl baseContinuationImpl) {
        DragGestureDetectorKt$awaitLongPressOrCancellation$1 dragGestureDetectorKt$awaitLongPressOrCancellation$1;
        Object obj;
        Ref$BooleanRef ref$BooleanRef;
        PointerInputChange pointerInputChange;
        if (baseContinuationImpl instanceof DragGestureDetectorKt$awaitLongPressOrCancellation$1) {
            dragGestureDetectorKt$awaitLongPressOrCancellation$1 = (DragGestureDetectorKt$awaitLongPressOrCancellation$1) baseContinuationImpl;
            int i = dragGestureDetectorKt$awaitLongPressOrCancellation$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dragGestureDetectorKt$awaitLongPressOrCancellation$1.label = i - Integer.MIN_VALUE;
            } else {
                dragGestureDetectorKt$awaitLongPressOrCancellation$1 = new DragGestureDetectorKt$awaitLongPressOrCancellation$1(baseContinuationImpl);
            }
        }
        Object obj2 = dragGestureDetectorKt$awaitLongPressOrCancellation$1.result;
        Object obj3 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dragGestureDetectorKt$awaitLongPressOrCancellation$1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                if (!m74isPointerUpDmW0f2w(awaitPointerEventScope.getCurrentEvent(), j)) {
                    List list = awaitPointerEventScope.getCurrentEvent().changes;
                    int size = list.size();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size) {
                            obj = null;
                            break;
                        }
                        obj = list.get(i3);
                        if (PointerId.m593equalsimpl0(((PointerInputChange) obj).id, j)) {
                            break;
                        }
                        i3++;
                    }
                    ?? r11 = (PointerInputChange) obj;
                    if (r11 != 0) {
                        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                        Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                        ref$ObjectRef2.element = r11;
                        long longPressTimeoutMillis = awaitPointerEventScope.getViewConfiguration().getLongPressTimeoutMillis();
                        Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                        Function2 dragGestureDetectorKt$awaitLongPressOrCancellation$2 = new DragGestureDetectorKt$awaitLongPressOrCancellation$2(ref$BooleanRef2, ref$ObjectRef2, ref$ObjectRef, null);
                        dragGestureDetectorKt$awaitLongPressOrCancellation$1.L$0 = r11;
                        dragGestureDetectorKt$awaitLongPressOrCancellation$1.L$1 = ref$ObjectRef;
                        dragGestureDetectorKt$awaitLongPressOrCancellation$1.L$2 = ref$BooleanRef2;
                        dragGestureDetectorKt$awaitLongPressOrCancellation$1.label = 1;
                        if (awaitPointerEventScope.withTimeout(longPressTimeoutMillis, dragGestureDetectorKt$awaitLongPressOrCancellation$2, dragGestureDetectorKt$awaitLongPressOrCancellation$1) == obj3) {
                            return obj3;
                        }
                        ref$BooleanRef = ref$BooleanRef2;
                        j = ref$ObjectRef;
                        pointerInputChange = r11;
                    }
                }
                return null;
            }
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ref$BooleanRef = (Ref$BooleanRef) dragGestureDetectorKt$awaitLongPressOrCancellation$1.L$2;
            Ref$ObjectRef ref$ObjectRef3 = (Ref$ObjectRef) dragGestureDetectorKt$awaitLongPressOrCancellation$1.L$1;
            PointerInputChange pointerInputChange2 = (PointerInputChange) dragGestureDetectorKt$awaitLongPressOrCancellation$1.L$0;
            ResultKt.throwOnFailure(obj2);
            j = ref$ObjectRef3;
            pointerInputChange = pointerInputChange2;
            if (ref$BooleanRef.element) {
                PointerInputChange pointerInputChange3 = (PointerInputChange) j.element;
                return pointerInputChange3 == null ? pointerInputChange : pointerInputChange3;
            }
            return null;
        } catch (PointerEventTimeoutCancellationException unused) {
            PointerInputChange pointerInputChange4 = (PointerInputChange) j.element;
            return pointerInputChange4 == null ? pointerInputChange : pointerInputChange4;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00fa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:60:0x0198 -> B:61:0x019e). Please report as a decompilation issue!!! */
    /* renamed from: awaitVerticalTouchSlopOrCancellation-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m71awaitVerticalTouchSlopOrCancellationjO51t88(AwaitPointerEventScope awaitPointerEventScope, long j, NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0 nestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0, BaseContinuationImpl baseContinuationImpl) {
        DragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1 dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1;
        Ref$LongRef ref$LongRef;
        Function2 function2;
        DragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1 dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12;
        float f;
        TouchSlopDetector touchSlopDetector;
        AwaitPointerEventScope awaitPointerEventScope2;
        Ref$LongRef ref$LongRef2;
        DragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1 dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13;
        float f2;
        TouchSlopDetector touchSlopDetector2;
        int size;
        int i;
        Object obj;
        Object obj2;
        PointerInputChange pointerInputChange;
        Object obj3;
        Object objAwaitPointerEvent;
        if (baseContinuationImpl instanceof DragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1) {
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1 = (DragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1) baseContinuationImpl;
            int i2 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.label = i2 - Integer.MIN_VALUE;
            } else {
                dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1 = new DragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1(baseContinuationImpl);
            }
        }
        Object obj4 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.label;
        int i4 = 1;
        Object obj5 = null;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj4);
            PointerType.Companion.getClass();
            int i5 = PointerType.Touch;
            Orientation orientation = Orientation.Vertical;
            Offset.Companion.getClass();
            if (m74isPointerUpDmW0f2w(awaitPointerEventScope.getCurrentEvent(), j)) {
                return null;
            }
            float fM75pointerSlopE8SPZFQ = m75pointerSlopE8SPZFQ(awaitPointerEventScope.getViewConfiguration(), i5);
            ref$LongRef = new Ref$LongRef();
            ref$LongRef.element = j;
            TouchSlopDetector touchSlopDetector3 = new TouchSlopDetector(orientation, 0L, null);
            function2 = nestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda0;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1;
            f = fM75pointerSlopE8SPZFQ;
            touchSlopDetector = touchSlopDetector3;
            awaitPointerEventScope2 = awaitPointerEventScope;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$0 = function2;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$1 = awaitPointerEventScope2;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$2 = ref$LongRef;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$3 = touchSlopDetector;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$4 = obj5;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.F$0 = f;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.label = i4;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i3 == 1) {
            float f3 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.F$0;
            TouchSlopDetector touchSlopDetector4 = (TouchSlopDetector) dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.L$3;
            Ref$LongRef ref$LongRef3 = (Ref$LongRef) dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.L$2;
            AwaitPointerEventScope awaitPointerEventScope3 = (AwaitPointerEventScope) dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.L$1;
            Function2 function22 = (Function2) dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.L$0;
            ResultKt.throwOnFailure(obj4);
            f2 = f3;
            awaitPointerEventScope2 = awaitPointerEventScope3;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1;
            touchSlopDetector2 = touchSlopDetector4;
            function2 = function22;
            ref$LongRef2 = ref$LongRef3;
            PointerEvent pointerEvent = (PointerEvent) obj4;
            List list = pointerEvent.changes;
            size = list.size();
            int i6 = 0;
            i = 0;
            while (true) {
                if (i < size) {
                }
                i++;
                obj5 = obj;
            }
            pointerInputChange = (PointerInputChange) obj2;
            if (pointerInputChange != null) {
                return obj;
            }
            if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
            }
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$0 = function2;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$1 = awaitPointerEventScope2;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$2 = ref$LongRef;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$3 = touchSlopDetector;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$4 = obj5;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.F$0 = f;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.label = i4;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i3 != 2) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        float f4 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.F$0;
        PointerInputChange pointerInputChange2 = (PointerInputChange) dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.L$4;
        TouchSlopDetector touchSlopDetector5 = (TouchSlopDetector) dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.L$3;
        ref$LongRef = (Ref$LongRef) dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.L$2;
        AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.L$1;
        Function2 function23 = (Function2) dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1.L$0;
        ResultKt.throwOnFailure(obj4);
        touchSlopDetector = touchSlopDetector5;
        long j2 = 0;
        obj = null;
        dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$1;
        f = f4;
        awaitPointerEventScope2 = awaitPointerEventScope4;
        if (!pointerInputChange2.isConsumed()) {
            return obj;
        }
        function2 = function23;
        obj5 = obj;
        i4 = 1;
        dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$0 = function2;
        dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$1 = awaitPointerEventScope2;
        dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$2 = ref$LongRef;
        dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$3 = touchSlopDetector;
        dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$4 = obj5;
        dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.F$0 = f;
        dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.label = i4;
        objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12);
        if (objAwaitPointerEvent != coroutineSingletons) {
            float f5 = f;
            touchSlopDetector2 = touchSlopDetector;
            obj4 = objAwaitPointerEvent;
            ref$LongRef2 = ref$LongRef;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12;
            f2 = f5;
            PointerEvent pointerEvent2 = (PointerEvent) obj4;
            List list2 = pointerEvent2.changes;
            size = list2.size();
            int i62 = 0;
            i = 0;
            while (true) {
                if (i < size) {
                    obj = obj5;
                    obj2 = obj;
                    break;
                }
                obj2 = list2.get(i);
                obj = obj5;
                if (PointerId.m593equalsimpl0(((PointerInputChange) obj2).id, ref$LongRef2.element)) {
                    break;
                }
                i++;
                obj5 = obj;
            }
            pointerInputChange = (PointerInputChange) obj2;
            if (pointerInputChange != null || pointerInputChange.isConsumed()) {
                return obj;
            }
            if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
                long jM87addPointerInputChangedBAh8RU = touchSlopDetector2.m87addPointerInputChangedBAh8RU(pointerInputChange, f2);
                if ((9223372034707292159L & jM87addPointerInputChangedBAh8RU) != 9205357640488583168L) {
                    function2.invoke(pointerInputChange, new Float(Float.intBitsToFloat((int) (jM87addPointerInputChangedBAh8RU & 4294967295L))));
                    if (pointerInputChange.isConsumed()) {
                        return pointerInputChange;
                    }
                    Offset.Companion.getClass();
                    touchSlopDetector2.totalPositionChange = 0L;
                    touchSlopDetector = touchSlopDetector2;
                    f = f2;
                    dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13;
                    ref$LongRef = ref$LongRef2;
                    obj5 = obj;
                    i4 = 1;
                } else {
                    j2 = 0;
                    PointerEventPass pointerEventPass = PointerEventPass.Final;
                    dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13.L$0 = function2;
                    dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13.L$1 = awaitPointerEventScope2;
                    dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13.L$2 = ref$LongRef2;
                    dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13.L$3 = touchSlopDetector2;
                    dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13.L$4 = pointerInputChange;
                    dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13.F$0 = f2;
                    dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13.label = 2;
                    if (awaitPointerEventScope2.awaitPointerEvent(pointerEventPass, dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13) != coroutineSingletons) {
                        function23 = function2;
                        touchSlopDetector = touchSlopDetector2;
                        f = f2;
                        pointerInputChange2 = pointerInputChange;
                        dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13;
                        ref$LongRef = ref$LongRef2;
                        if (!pointerInputChange2.isConsumed()) {
                        }
                    }
                }
            } else {
                List list3 = pointerEvent2.changes;
                int size2 = list3.size();
                while (true) {
                    if (i62 >= size2) {
                        obj3 = obj;
                        break;
                    }
                    obj3 = list3.get(i62);
                    if (((PointerInputChange) obj3).pressed) {
                        break;
                    }
                    i62++;
                }
                PointerInputChange pointerInputChange3 = (PointerInputChange) obj3;
                if (pointerInputChange3 == null) {
                    return obj;
                }
                ref$LongRef2.element = pointerInputChange3.id;
                touchSlopDetector = touchSlopDetector2;
                f = f2;
                dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12 = dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$13;
                ref$LongRef = ref$LongRef2;
                obj5 = obj;
                i4 = 1;
            }
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$0 = function2;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$1 = awaitPointerEventScope2;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$2 = ref$LongRef;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$3 = touchSlopDetector;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.L$4 = obj5;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.F$0 = f;
            dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12.label = i4;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$awaitVerticalTouchSlopOrCancellation$12);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    public static final Object detectDragGestures(PointerInputScope pointerInputScope, final Function1 function1, final Function0 function0, Function0 function02, Function2 function2, Continuation continuation) {
        Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new AnonymousClass9(new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectDragGestures.7
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Boolean.TRUE;
            }
        }, new Ref$LongRef(), null, new Function3() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectDragGestures.5
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                long j = ((Offset) obj3).packedValue;
                function1.mo781invoke(Offset.m395boximpl(((PointerInputChange) obj2).position));
                return Unit.INSTANCE;
            }
        }, function2, function02, new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectDragGestures.6
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                function0.invoke();
                return Unit.INSTANCE;
            }
        }, null), continuation);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objAwaitEachGesture != coroutineSingletons) {
            objAwaitEachGesture = Unit.INSTANCE;
        }
        return objAwaitEachGesture == coroutineSingletons ? objAwaitEachGesture : Unit.INSTANCE;
    }

    public static /* synthetic */ Object detectDragGestures$default(PointerInputScope pointerInputScope, Function1 function1, Function0 function0, Function2 function2, Continuation continuation, int i) {
        if ((i & 1) != 0) {
            function1 = new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectDragGestures.2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* synthetic */ Object mo781invoke(Object obj) {
                    long j = ((Offset) obj).packedValue;
                    return Unit.INSTANCE;
                }
            };
        }
        Function1 function12 = function1;
        if ((i & 2) != 0) {
            function0 = new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectDragGestures.3
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            };
        }
        return detectDragGestures(pointerInputScope, function12, function0, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectDragGestures.4
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        }, function2, continuation);
    }

    public static final Object detectDragGesturesAfterLongPress(PointerInputScope pointerInputScope, Function1 function1, Function0 function0, Function0 function02, Function2 function2, Continuation continuation) {
        Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new C06915(function1, function0, function02, function2, null), continuation);
        return objAwaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitEachGesture : Unit.INSTANCE;
    }

    public static final Object detectHorizontalDragGestures(PointerInputScope pointerInputScope, Function1 function1, Function0 function0, Function0 function02, Function2 function2, Continuation continuation) {
        Object objAwaitEachGesture = ForEachGestureKt.awaitEachGesture(pointerInputScope, new C06955(function1, function2, function0, function02, null), continuation);
        return objAwaitEachGesture == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitEachGesture : Unit.INSTANCE;
    }

    public static /* synthetic */ Object detectHorizontalDragGestures$default(PointerInputScope pointerInputScope, BasicSwitchKt$$ExternalSyntheticLambda1 basicSwitchKt$$ExternalSyntheticLambda1, BasicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1 basicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1, Function2 function2, Continuation continuation, int i) {
        Function1 function1 = basicSwitchKt$$ExternalSyntheticLambda1;
        if ((i & 1) != 0) {
            function1 = new Function1() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectHorizontalDragGestures.2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* synthetic */ Object mo781invoke(Object obj) {
                    long j = ((Offset) obj).packedValue;
                    return Unit.INSTANCE;
                }
            };
        }
        Function1 function12 = function1;
        Function0 function0 = basicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1;
        if ((i & 2) != 0) {
            function0 = new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectHorizontalDragGestures.3
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            };
        }
        return detectHorizontalDragGestures(pointerInputScope, function12, function0, new Function0() { // from class: androidx.compose.foundation.gestures.DragGestureDetectorKt.detectHorizontalDragGestures.4
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        }, function2, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0048 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0046 -> B:18:0x0049). Please report as a decompilation issue!!! */
    /* renamed from: drag-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m72dragjO51t88(AwaitPointerEventScope awaitPointerEventScope, long j, Function1 function1, BaseContinuationImpl baseContinuationImpl) {
        DragGestureDetectorKt$drag$1 dragGestureDetectorKt$drag$1;
        PointerInputChange pointerInputChange;
        if (baseContinuationImpl instanceof DragGestureDetectorKt$drag$1) {
            dragGestureDetectorKt$drag$1 = (DragGestureDetectorKt$drag$1) baseContinuationImpl;
            int i = dragGestureDetectorKt$drag$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dragGestureDetectorKt$drag$1.label = i - Integer.MIN_VALUE;
            } else {
                dragGestureDetectorKt$drag$1 = new DragGestureDetectorKt$drag$1(baseContinuationImpl);
            }
        }
        Object objM67awaitDragOrCancellationrnUCldI = dragGestureDetectorKt$drag$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dragGestureDetectorKt$drag$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objM67awaitDragOrCancellationrnUCldI);
            dragGestureDetectorKt$drag$1.L$0 = awaitPointerEventScope;
            dragGestureDetectorKt$drag$1.L$1 = function1;
            dragGestureDetectorKt$drag$1.label = 1;
            objM67awaitDragOrCancellationrnUCldI = m67awaitDragOrCancellationrnUCldI(awaitPointerEventScope, j, dragGestureDetectorKt$drag$1);
            if (objM67awaitDragOrCancellationrnUCldI == coroutineSingletons) {
            }
            pointerInputChange = (PointerInputChange) objM67awaitDragOrCancellationrnUCldI;
            if (pointerInputChange == null) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Function1 function12 = (Function1) dragGestureDetectorKt$drag$1.L$1;
            AwaitPointerEventScope awaitPointerEventScope2 = (AwaitPointerEventScope) dragGestureDetectorKt$drag$1.L$0;
            ResultKt.throwOnFailure(objM67awaitDragOrCancellationrnUCldI);
            function1 = function12;
            awaitPointerEventScope = awaitPointerEventScope2;
            pointerInputChange = (PointerInputChange) objM67awaitDragOrCancellationrnUCldI;
            if (pointerInputChange == null) {
                if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
                    return Boolean.TRUE;
                }
                function1.mo781invoke(pointerInputChange);
                j = pointerInputChange.id;
                dragGestureDetectorKt$drag$1.L$0 = awaitPointerEventScope;
                dragGestureDetectorKt$drag$1.L$1 = function1;
                dragGestureDetectorKt$drag$1.label = 1;
                objM67awaitDragOrCancellationrnUCldI = m67awaitDragOrCancellationrnUCldI(awaitPointerEventScope, j, dragGestureDetectorKt$drag$1);
                if (objM67awaitDragOrCancellationrnUCldI == coroutineSingletons) {
                    return coroutineSingletons;
                }
                pointerInputChange = (PointerInputChange) objM67awaitDragOrCancellationrnUCldI;
                if (pointerInputChange == null) {
                    return Boolean.FALSE;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x010b, code lost:
    
        if (r0 == 0.0f) goto L56;
     */
    /* JADX WARN: Path cross not found for [B:35:0x00c3, B:46:0x00e9], limit reached: 69 */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0080 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00b2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0081 -> B:23:0x0087). Please report as a decompilation issue!!! */
    /* renamed from: horizontalDrag-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m73horizontalDragjO51t88(AwaitPointerEventScope awaitPointerEventScope, long j, Function1 function1, BaseContinuationImpl baseContinuationImpl) {
        DragGestureDetectorKt$horizontalDrag$1 dragGestureDetectorKt$horizontalDrag$1;
        long j2;
        Orientation orientation;
        DragGestureDetectorKt$horizontalDrag$1 dragGestureDetectorKt$horizontalDrag$12;
        Function1 function12;
        PointerInputChange pointerInputChange;
        Orientation orientation2;
        AwaitPointerEventScope awaitPointerEventScope2;
        Ref$LongRef ref$LongRef;
        AwaitPointerEventScope awaitPointerEventScope3;
        Object objAwaitPointerEvent;
        DragGestureDetectorKt$horizontalDrag$1 dragGestureDetectorKt$horizontalDrag$13;
        Object obj;
        float fIntBitsToFloat;
        Object obj2;
        if (baseContinuationImpl instanceof DragGestureDetectorKt$horizontalDrag$1) {
            dragGestureDetectorKt$horizontalDrag$1 = (DragGestureDetectorKt$horizontalDrag$1) baseContinuationImpl;
            int i = dragGestureDetectorKt$horizontalDrag$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dragGestureDetectorKt$horizontalDrag$1.label = i - Integer.MIN_VALUE;
            } else {
                dragGestureDetectorKt$horizontalDrag$1 = new DragGestureDetectorKt$horizontalDrag$1(baseContinuationImpl);
            }
        }
        Object obj3 = dragGestureDetectorKt$horizontalDrag$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dragGestureDetectorKt$horizontalDrag$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj3);
            Orientation orientation3 = Orientation.Horizontal;
            j2 = j;
            if (!m74isPointerUpDmW0f2w(awaitPointerEventScope.getCurrentEvent(), j2)) {
                orientation = orientation3;
                dragGestureDetectorKt$horizontalDrag$12 = dragGestureDetectorKt$horizontalDrag$1;
                function12 = function1;
                awaitPointerEventScope3 = awaitPointerEventScope;
                Ref$LongRef ref$LongRef2 = new Ref$LongRef();
                ref$LongRef2.element = j2;
                awaitPointerEventScope2 = awaitPointerEventScope3;
                orientation2 = orientation;
                ref$LongRef = ref$LongRef2;
                dragGestureDetectorKt$horizontalDrag$12.L$0 = function12;
                dragGestureDetectorKt$horizontalDrag$12.L$1 = awaitPointerEventScope3;
                dragGestureDetectorKt$horizontalDrag$12.L$2 = orientation2;
                dragGestureDetectorKt$horizontalDrag$12.L$3 = awaitPointerEventScope2;
                dragGestureDetectorKt$horizontalDrag$12.L$4 = ref$LongRef;
                dragGestureDetectorKt$horizontalDrag$12.label = 1;
                objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$horizontalDrag$12);
                if (objAwaitPointerEvent == coroutineSingletons) {
                }
            }
            pointerInputChange = null;
            return Boolean.valueOf(pointerInputChange != null);
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ref$LongRef = (Ref$LongRef) dragGestureDetectorKt$horizontalDrag$1.L$4;
        awaitPointerEventScope2 = (AwaitPointerEventScope) dragGestureDetectorKt$horizontalDrag$1.L$3;
        orientation2 = (Orientation) dragGestureDetectorKt$horizontalDrag$1.L$2;
        AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) dragGestureDetectorKt$horizontalDrag$1.L$1;
        Function1 function13 = (Function1) dragGestureDetectorKt$horizontalDrag$1.L$0;
        ResultKt.throwOnFailure(obj3);
        DragGestureDetectorKt$horizontalDrag$1 dragGestureDetectorKt$horizontalDrag$14 = dragGestureDetectorKt$horizontalDrag$1;
        function12 = function13;
        PointerEvent pointerEvent = (PointerEvent) obj3;
        List list = pointerEvent.changes;
        int size = list.size();
        int i3 = 0;
        while (true) {
            if (i3 < size) {
                awaitPointerEventScope = awaitPointerEventScope4;
                dragGestureDetectorKt$horizontalDrag$13 = dragGestureDetectorKt$horizontalDrag$14;
                obj = null;
                break;
            }
            obj = list.get(i3);
            awaitPointerEventScope = awaitPointerEventScope4;
            dragGestureDetectorKt$horizontalDrag$13 = dragGestureDetectorKt$horizontalDrag$14;
            if (PointerId.m593equalsimpl0(((PointerInputChange) obj).id, ref$LongRef.element)) {
                break;
            }
            i3++;
            awaitPointerEventScope4 = awaitPointerEventScope;
            dragGestureDetectorKt$horizontalDrag$14 = dragGestureDetectorKt$horizontalDrag$13;
        }
        PointerInputChange pointerInputChange2 = (PointerInputChange) obj;
        if (pointerInputChange2 == null) {
            if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange2)) {
                List list2 = pointerEvent.changes;
                int size2 = list2.size();
                int i4 = 0;
                while (true) {
                    if (i4 >= size2) {
                        obj2 = null;
                        break;
                    }
                    obj2 = list2.get(i4);
                    if (((PointerInputChange) obj2).pressed) {
                        break;
                    }
                    i4++;
                }
                PointerInputChange pointerInputChange3 = (PointerInputChange) obj2;
                if (pointerInputChange3 != null) {
                    ref$LongRef.element = pointerInputChange3.id;
                    awaitPointerEventScope3 = awaitPointerEventScope;
                    dragGestureDetectorKt$horizontalDrag$12 = dragGestureDetectorKt$horizontalDrag$13;
                    dragGestureDetectorKt$horizontalDrag$12.L$0 = function12;
                    dragGestureDetectorKt$horizontalDrag$12.L$1 = awaitPointerEventScope3;
                    dragGestureDetectorKt$horizontalDrag$12.L$2 = orientation2;
                    dragGestureDetectorKt$horizontalDrag$12.L$3 = awaitPointerEventScope2;
                    dragGestureDetectorKt$horizontalDrag$12.L$4 = ref$LongRef;
                    dragGestureDetectorKt$horizontalDrag$12.label = 1;
                    objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$horizontalDrag$12);
                    if (objAwaitPointerEvent == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    DragGestureDetectorKt$horizontalDrag$1 dragGestureDetectorKt$horizontalDrag$15 = dragGestureDetectorKt$horizontalDrag$12;
                    awaitPointerEventScope4 = awaitPointerEventScope3;
                    obj3 = objAwaitPointerEvent;
                    dragGestureDetectorKt$horizontalDrag$14 = dragGestureDetectorKt$horizontalDrag$15;
                    PointerEvent pointerEvent2 = (PointerEvent) obj3;
                    List list3 = pointerEvent2.changes;
                    int size3 = list3.size();
                    int i32 = 0;
                    while (true) {
                        if (i32 < size3) {
                        }
                        i32++;
                        awaitPointerEventScope4 = awaitPointerEventScope;
                        dragGestureDetectorKt$horizontalDrag$14 = dragGestureDetectorKt$horizontalDrag$13;
                    }
                    PointerInputChange pointerInputChange22 = (PointerInputChange) obj;
                    if (pointerInputChange22 == null) {
                        pointerInputChange22 = null;
                    }
                }
            } else {
                long jPositionChangeInternal = PointerEventKt.positionChangeInternal(pointerInputChange22, true);
                if (orientation2 != null) {
                    fIntBitsToFloat = Float.intBitsToFloat((int) (orientation2 == Orientation.Vertical ? jPositionChangeInternal & 4294967295L : jPositionChangeInternal >> 32));
                } else {
                    fIntBitsToFloat = Offset.m399getDistanceimpl(jPositionChangeInternal);
                }
            }
        }
        if (pointerInputChange22 == null || pointerInputChange22.isConsumed()) {
            pointerInputChange = null;
        } else if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange22)) {
            pointerInputChange = pointerInputChange22;
        } else {
            function12.mo781invoke(pointerInputChange22);
            dragGestureDetectorKt$horizontalDrag$12 = dragGestureDetectorKt$horizontalDrag$13;
            orientation = orientation2;
            j2 = pointerInputChange22.id;
            awaitPointerEventScope3 = awaitPointerEventScope;
            Ref$LongRef ref$LongRef22 = new Ref$LongRef();
            ref$LongRef22.element = j2;
            awaitPointerEventScope2 = awaitPointerEventScope3;
            orientation2 = orientation;
            ref$LongRef = ref$LongRef22;
            dragGestureDetectorKt$horizontalDrag$12.L$0 = function12;
            dragGestureDetectorKt$horizontalDrag$12.L$1 = awaitPointerEventScope3;
            dragGestureDetectorKt$horizontalDrag$12.L$2 = orientation2;
            dragGestureDetectorKt$horizontalDrag$12.L$3 = awaitPointerEventScope2;
            dragGestureDetectorKt$horizontalDrag$12.L$4 = ref$LongRef;
            dragGestureDetectorKt$horizontalDrag$12.label = 1;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$horizontalDrag$12);
            if (objAwaitPointerEvent == coroutineSingletons) {
            }
        }
        return Boolean.valueOf(pointerInputChange != null);
    }

    /* renamed from: isPointerUp-DmW0f2w, reason: not valid java name */
    public static final boolean m74isPointerUpDmW0f2w(PointerEvent pointerEvent, long j) {
        Object obj;
        List list = pointerEvent.changes;
        int size = list.size();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= size) {
                obj = null;
                break;
            }
            obj = list.get(i);
            if (PointerId.m593equalsimpl0(((PointerInputChange) obj).id, j)) {
                break;
            }
            i++;
        }
        PointerInputChange pointerInputChange = (PointerInputChange) obj;
        if (pointerInputChange != null && pointerInputChange.pressed) {
            z = true;
        }
        return true ^ z;
    }

    /* renamed from: pointerSlop-E8SPZFQ, reason: not valid java name */
    public static final float m75pointerSlopE8SPZFQ(ViewConfiguration viewConfiguration, int i) {
        PointerType.Companion.getClass();
        return i == PointerType.Mouse ? viewConfiguration.getTouchSlop() * mouseToTouchSlopRatio : viewConfiguration.getTouchSlop();
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x010b, code lost:
    
        if (r0 == 0.0f) goto L56;
     */
    /* JADX WARN: Path cross not found for [B:35:0x00c3, B:46:0x00e9], limit reached: 69 */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0080 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00b2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0081 -> B:23:0x0087). Please report as a decompilation issue!!! */
    /* renamed from: verticalDrag-jO51t88, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m76verticalDragjO51t88(AwaitPointerEventScope awaitPointerEventScope, long j, NestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda1 nestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda1, BaseContinuationImpl baseContinuationImpl) {
        DragGestureDetectorKt$verticalDrag$1 dragGestureDetectorKt$verticalDrag$1;
        long j2;
        Orientation orientation;
        DragGestureDetectorKt$verticalDrag$1 dragGestureDetectorKt$verticalDrag$12;
        Function1 function1;
        PointerInputChange pointerInputChange;
        Orientation orientation2;
        AwaitPointerEventScope awaitPointerEventScope2;
        Ref$LongRef ref$LongRef;
        AwaitPointerEventScope awaitPointerEventScope3;
        Object objAwaitPointerEvent;
        DragGestureDetectorKt$verticalDrag$1 dragGestureDetectorKt$verticalDrag$13;
        Object obj;
        float fIntBitsToFloat;
        Object obj2;
        if (baseContinuationImpl instanceof DragGestureDetectorKt$verticalDrag$1) {
            dragGestureDetectorKt$verticalDrag$1 = (DragGestureDetectorKt$verticalDrag$1) baseContinuationImpl;
            int i = dragGestureDetectorKt$verticalDrag$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dragGestureDetectorKt$verticalDrag$1.label = i - Integer.MIN_VALUE;
            } else {
                dragGestureDetectorKt$verticalDrag$1 = new DragGestureDetectorKt$verticalDrag$1(baseContinuationImpl);
            }
        }
        Object obj3 = dragGestureDetectorKt$verticalDrag$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dragGestureDetectorKt$verticalDrag$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj3);
            Orientation orientation3 = Orientation.Vertical;
            j2 = j;
            if (!m74isPointerUpDmW0f2w(awaitPointerEventScope.getCurrentEvent(), j2)) {
                orientation = orientation3;
                dragGestureDetectorKt$verticalDrag$12 = dragGestureDetectorKt$verticalDrag$1;
                function1 = nestedDraggableNode$detectDrags$2$$ExternalSyntheticLambda1;
                awaitPointerEventScope3 = awaitPointerEventScope;
                Ref$LongRef ref$LongRef2 = new Ref$LongRef();
                ref$LongRef2.element = j2;
                awaitPointerEventScope2 = awaitPointerEventScope3;
                orientation2 = orientation;
                ref$LongRef = ref$LongRef2;
                dragGestureDetectorKt$verticalDrag$12.L$0 = function1;
                dragGestureDetectorKt$verticalDrag$12.L$1 = awaitPointerEventScope3;
                dragGestureDetectorKt$verticalDrag$12.L$2 = orientation2;
                dragGestureDetectorKt$verticalDrag$12.L$3 = awaitPointerEventScope2;
                dragGestureDetectorKt$verticalDrag$12.L$4 = ref$LongRef;
                dragGestureDetectorKt$verticalDrag$12.label = 1;
                objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$verticalDrag$12);
                if (objAwaitPointerEvent == coroutineSingletons) {
                }
            }
            pointerInputChange = null;
            return Boolean.valueOf(pointerInputChange != null);
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ref$LongRef = (Ref$LongRef) dragGestureDetectorKt$verticalDrag$1.L$4;
        awaitPointerEventScope2 = (AwaitPointerEventScope) dragGestureDetectorKt$verticalDrag$1.L$3;
        orientation2 = (Orientation) dragGestureDetectorKt$verticalDrag$1.L$2;
        AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) dragGestureDetectorKt$verticalDrag$1.L$1;
        Function1 function12 = (Function1) dragGestureDetectorKt$verticalDrag$1.L$0;
        ResultKt.throwOnFailure(obj3);
        DragGestureDetectorKt$verticalDrag$1 dragGestureDetectorKt$verticalDrag$14 = dragGestureDetectorKt$verticalDrag$1;
        function1 = function12;
        PointerEvent pointerEvent = (PointerEvent) obj3;
        List list = pointerEvent.changes;
        int size = list.size();
        int i3 = 0;
        while (true) {
            if (i3 < size) {
                awaitPointerEventScope = awaitPointerEventScope4;
                dragGestureDetectorKt$verticalDrag$13 = dragGestureDetectorKt$verticalDrag$14;
                obj = null;
                break;
            }
            obj = list.get(i3);
            awaitPointerEventScope = awaitPointerEventScope4;
            dragGestureDetectorKt$verticalDrag$13 = dragGestureDetectorKt$verticalDrag$14;
            if (PointerId.m593equalsimpl0(((PointerInputChange) obj).id, ref$LongRef.element)) {
                break;
            }
            i3++;
            awaitPointerEventScope4 = awaitPointerEventScope;
            dragGestureDetectorKt$verticalDrag$14 = dragGestureDetectorKt$verticalDrag$13;
        }
        PointerInputChange pointerInputChange2 = (PointerInputChange) obj;
        if (pointerInputChange2 == null) {
            if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange2)) {
                List list2 = pointerEvent.changes;
                int size2 = list2.size();
                int i4 = 0;
                while (true) {
                    if (i4 >= size2) {
                        obj2 = null;
                        break;
                    }
                    obj2 = list2.get(i4);
                    if (((PointerInputChange) obj2).pressed) {
                        break;
                    }
                    i4++;
                }
                PointerInputChange pointerInputChange3 = (PointerInputChange) obj2;
                if (pointerInputChange3 != null) {
                    ref$LongRef.element = pointerInputChange3.id;
                    awaitPointerEventScope3 = awaitPointerEventScope;
                    dragGestureDetectorKt$verticalDrag$12 = dragGestureDetectorKt$verticalDrag$13;
                    dragGestureDetectorKt$verticalDrag$12.L$0 = function1;
                    dragGestureDetectorKt$verticalDrag$12.L$1 = awaitPointerEventScope3;
                    dragGestureDetectorKt$verticalDrag$12.L$2 = orientation2;
                    dragGestureDetectorKt$verticalDrag$12.L$3 = awaitPointerEventScope2;
                    dragGestureDetectorKt$verticalDrag$12.L$4 = ref$LongRef;
                    dragGestureDetectorKt$verticalDrag$12.label = 1;
                    objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$verticalDrag$12);
                    if (objAwaitPointerEvent == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    DragGestureDetectorKt$verticalDrag$1 dragGestureDetectorKt$verticalDrag$15 = dragGestureDetectorKt$verticalDrag$12;
                    awaitPointerEventScope4 = awaitPointerEventScope3;
                    obj3 = objAwaitPointerEvent;
                    dragGestureDetectorKt$verticalDrag$14 = dragGestureDetectorKt$verticalDrag$15;
                    PointerEvent pointerEvent2 = (PointerEvent) obj3;
                    List list3 = pointerEvent2.changes;
                    int size3 = list3.size();
                    int i32 = 0;
                    while (true) {
                        if (i32 < size3) {
                        }
                        i32++;
                        awaitPointerEventScope4 = awaitPointerEventScope;
                        dragGestureDetectorKt$verticalDrag$14 = dragGestureDetectorKt$verticalDrag$13;
                    }
                    PointerInputChange pointerInputChange22 = (PointerInputChange) obj;
                    if (pointerInputChange22 == null) {
                        pointerInputChange22 = null;
                    }
                }
            } else {
                long jPositionChangeInternal = PointerEventKt.positionChangeInternal(pointerInputChange22, true);
                if (orientation2 != null) {
                    fIntBitsToFloat = Float.intBitsToFloat((int) (orientation2 == Orientation.Vertical ? jPositionChangeInternal & 4294967295L : jPositionChangeInternal >> 32));
                } else {
                    fIntBitsToFloat = Offset.m399getDistanceimpl(jPositionChangeInternal);
                }
            }
        }
        if (pointerInputChange22 == null || pointerInputChange22.isConsumed()) {
            pointerInputChange = null;
        } else if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange22)) {
            pointerInputChange = pointerInputChange22;
        } else {
            function1.mo781invoke(pointerInputChange22);
            dragGestureDetectorKt$verticalDrag$12 = dragGestureDetectorKt$verticalDrag$13;
            orientation = orientation2;
            j2 = pointerInputChange22.id;
            awaitPointerEventScope3 = awaitPointerEventScope;
            Ref$LongRef ref$LongRef22 = new Ref$LongRef();
            ref$LongRef22.element = j2;
            awaitPointerEventScope2 = awaitPointerEventScope3;
            orientation2 = orientation;
            ref$LongRef = ref$LongRef22;
            dragGestureDetectorKt$verticalDrag$12.L$0 = function1;
            dragGestureDetectorKt$verticalDrag$12.L$1 = awaitPointerEventScope3;
            dragGestureDetectorKt$verticalDrag$12.L$2 = orientation2;
            dragGestureDetectorKt$verticalDrag$12.L$3 = awaitPointerEventScope2;
            dragGestureDetectorKt$verticalDrag$12.L$4 = ref$LongRef;
            dragGestureDetectorKt$verticalDrag$12.label = 1;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope2).awaitPointerEvent(PointerEventPass.Main, dragGestureDetectorKt$verticalDrag$12);
            if (objAwaitPointerEvent == coroutineSingletons) {
            }
        }
        return Boolean.valueOf(pointerInputChange != null);
    }
}
