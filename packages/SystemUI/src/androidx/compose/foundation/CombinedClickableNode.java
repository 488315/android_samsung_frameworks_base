package androidx.compose.foundation;

import android.view.KeyEvent;
import androidx.collection.LongObjectMapKt;
import androidx.collection.MutableLongObjectMap;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.hapticfeedback.HapticFeedbackType;
import androidx.compose.ui.hapticfeedback.PlatformHapticFeedbackType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class CombinedClickableNode extends AbstractClickableNode implements CompositionLocalConsumerModifierNode {
    public final MutableLongObjectMap doubleKeyClickStates;
    public boolean hapticFeedbackEnabled;
    public final MutableLongObjectMap longKeyPressJobs;
    public Function0 onDoubleClick;
    public Function0 onLongClick;
    public String onLongClickLabel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DoubleKeyClickState {
        public boolean doubleTapMinTimeMillisElapsed;
        public final Job job;

        public DoubleKeyClickState(Job job) {
            this.job = job;
        }
    }

    public /* synthetic */ CombinedClickableNode(Function0 function0, String str, Function0 function02, Function0 function03, boolean z, MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z2, String str2, Role role, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0, str, function02, function03, z, mutableInteractionSource, indicationNodeFactory, z2, str2, role);
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    public final void applyAdditionalSemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        if (this.onLongClick != null) {
            SemanticsPropertiesKt.onLongClick(semanticsPropertyReceiver, this.onLongClickLabel, new Function0() { // from class: androidx.compose.foundation.CombinedClickableNode$applyAdditionalSemantics$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Function0 function0 = CombinedClickableNode.this.onLongClick;
                    if (function0 != null) {
                        function0.invoke();
                    }
                    return Boolean.TRUE;
                }
            });
        }
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    public final Object clickPointerInput(PointerInputScope pointerInputScope, Continuation continuation) {
        Object detectTapGestures = TapGestureDetectorKt.detectTapGestures(pointerInputScope, new CombinedClickableNode$clickPointerInput$4(this, null), (!this.enabled || this.onDoubleClick == null) ? null : new Function1() { // from class: androidx.compose.foundation.CombinedClickableNode$clickPointerInput$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                long j = ((Offset) obj).packedValue;
                Function0 function0 = CombinedClickableNode.this.onDoubleClick;
                if (function0 != null) {
                    function0.invoke();
                }
                return Unit.INSTANCE;
            }
        }, (!this.enabled || this.onLongClick == null) ? null : new Function1() { // from class: androidx.compose.foundation.CombinedClickableNode$clickPointerInput$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                long j = ((Offset) obj).packedValue;
                Function0 function0 = CombinedClickableNode.this.onLongClick;
                if (function0 != null) {
                    function0.invoke();
                }
                CombinedClickableNode combinedClickableNode = CombinedClickableNode.this;
                if (combinedClickableNode.hapticFeedbackEnabled) {
                    HapticFeedback hapticFeedback = (HapticFeedback) CompositionLocalConsumerModifierNodeKt.currentValueOf(combinedClickableNode, CompositionLocalsKt.LocalHapticFeedback);
                    HapticFeedbackType.Companion.getClass();
                    PlatformHapticFeedbackType.INSTANCE.getClass();
                    hapticFeedback.mo570performHapticFeedbackCdsT49E(0);
                }
                return Unit.INSTANCE;
            }
        }, new Function1() { // from class: androidx.compose.foundation.CombinedClickableNode$clickPointerInput$5
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                long j = ((Offset) obj).packedValue;
                CombinedClickableNode combinedClickableNode = CombinedClickableNode.this;
                if (combinedClickableNode.enabled) {
                    combinedClickableNode.onClick.invoke();
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return detectTapGestures == CoroutineSingletons.COROUTINE_SUSPENDED ? detectTapGestures : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    public final void onCancelKeyInput() {
        resetKeyPressState();
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x002f  */
    @Override // androidx.compose.foundation.AbstractClickableNode
    /* renamed from: onClickKeyDownEvent-ZmokQxo */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean mo13onClickKeyDownEventZmokQxo(android.view.KeyEvent r8) {
        /*
            r7 = this;
            long r0 = androidx.compose.ui.input.key.KeyEvent_androidKt.m578getKeyZmokQxo(r8)
            kotlin.jvm.functions.Function0 r8 = r7.onLongClick
            r2 = 0
            if (r8 == 0) goto L24
            androidx.collection.MutableLongObjectMap r8 = r7.longKeyPressJobs
            java.lang.Object r3 = r8.get(r0)
            if (r3 != 0) goto L24
            kotlinx.coroutines.CoroutineScope r3 = r7.getCoroutineScope()
            androidx.compose.foundation.CombinedClickableNode$onClickKeyDownEvent$1 r4 = new androidx.compose.foundation.CombinedClickableNode$onClickKeyDownEvent$1
            r4.<init>(r7, r2)
            r5 = 3
            kotlinx.coroutines.StandaloneCoroutine r3 = kotlinx.coroutines.BuildersKt.launch$default(r3, r2, r2, r4, r5)
            r8.set(r0, r3)
            r8 = 1
            goto L25
        L24:
            r8 = 0
        L25:
            androidx.collection.MutableLongObjectMap r3 = r7.doubleKeyClickStates
            java.lang.Object r4 = r3.get(r0)
            androidx.compose.foundation.CombinedClickableNode$DoubleKeyClickState r4 = (androidx.compose.foundation.CombinedClickableNode.DoubleKeyClickState) r4
            if (r4 == 0) goto L4a
            kotlinx.coroutines.Job r5 = r4.job
            boolean r6 = r5.isActive()
            if (r6 == 0) goto L47
            r5.cancel(r2)
            boolean r2 = r4.doubleTapMinTimeMillisElapsed
            if (r2 != 0) goto L4a
            kotlin.jvm.functions.Function0 r7 = r7.onClick
            r7.invoke()
            r3.remove(r0)
            return r8
        L47:
            r3.remove(r0)
        L4a:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.CombinedClickableNode.mo13onClickKeyDownEventZmokQxo(android.view.KeyEvent):boolean");
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    /* renamed from: onClickKeyUpEvent-ZmokQxo */
    public final void mo14onClickKeyUpEventZmokQxo(KeyEvent keyEvent) {
        Function0 function0;
        long m578getKeyZmokQxo = KeyEvent_androidKt.m578getKeyZmokQxo(keyEvent);
        MutableLongObjectMap mutableLongObjectMap = this.longKeyPressJobs;
        boolean z = false;
        if (mutableLongObjectMap.get(m578getKeyZmokQxo) != null) {
            Job job = (Job) mutableLongObjectMap.get(m578getKeyZmokQxo);
            if (job != null) {
                if (job.isActive()) {
                    job.cancel(null);
                } else {
                    z = true;
                }
            }
            mutableLongObjectMap.remove(m578getKeyZmokQxo);
        }
        if (this.onDoubleClick == null) {
            if (z) {
                return;
            }
            this.onClick.invoke();
            return;
        }
        MutableLongObjectMap mutableLongObjectMap2 = this.doubleKeyClickStates;
        if (mutableLongObjectMap2.get(m578getKeyZmokQxo) == null) {
            if (z) {
                return;
            }
            mutableLongObjectMap2.set(m578getKeyZmokQxo, new DoubleKeyClickState(BuildersKt.launch$default(getCoroutineScope(), null, null, new CombinedClickableNode$onClickKeyUpEvent$2(this, m578getKeyZmokQxo, null), 3)));
        } else {
            if (!z && (function0 = this.onDoubleClick) != null) {
                function0.invoke();
            }
            mutableLongObjectMap2.remove(m578getKeyZmokQxo);
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onReset() {
        resetKeyPressState();
    }

    public final void resetKeyPressState() {
        char c;
        long j;
        long j2;
        char c2;
        MutableLongObjectMap mutableLongObjectMap = this.longKeyPressJobs;
        Object[] objArr = mutableLongObjectMap.values;
        long[] jArr = mutableLongObjectMap.metadata;
        int length = jArr.length - 2;
        char c3 = 7;
        if (length >= 0) {
            int i = 0;
            j = 128;
            while (true) {
                long j3 = jArr[i];
                j2 = 255;
                if ((((~j3) << c3) & j3 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    int i3 = 0;
                    while (i3 < i2) {
                        if ((j3 & 255) < 128) {
                            c2 = c3;
                            ((Job) objArr[(i << 3) + i3]).cancel(null);
                        } else {
                            c2 = c3;
                        }
                        j3 >>= 8;
                        i3++;
                        c3 = c2;
                    }
                    c = c3;
                    if (i2 != 8) {
                        break;
                    }
                } else {
                    c = c3;
                }
                if (i == length) {
                    break;
                }
                i++;
                c3 = c;
            }
        } else {
            c = 7;
            j = 128;
            j2 = 255;
        }
        mutableLongObjectMap.clear();
        MutableLongObjectMap mutableLongObjectMap2 = this.doubleKeyClickStates;
        Object[] objArr2 = mutableLongObjectMap2.values;
        long[] jArr2 = mutableLongObjectMap2.metadata;
        int length2 = jArr2.length - 2;
        if (length2 >= 0) {
            int i4 = 0;
            while (true) {
                long j4 = jArr2[i4];
                if ((((~j4) << c) & j4 & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i5 = 8 - ((~(i4 - length2)) >>> 31);
                    for (int i6 = 0; i6 < i5; i6++) {
                        if ((j4 & j2) < j) {
                            ((DoubleKeyClickState) objArr2[(i4 << 3) + i6]).job.cancel(null);
                        }
                        j4 >>= 8;
                    }
                    if (i5 != 8) {
                        break;
                    }
                }
                if (i4 == length2) {
                    break;
                } else {
                    i4++;
                }
            }
        }
        mutableLongObjectMap2.clear();
    }

    private CombinedClickableNode(Function0 function0, String str, Function0 function02, Function0 function03, boolean z, MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z2, String str2, Role role) {
        super(mutableInteractionSource, indicationNodeFactory, z2, str2, role, function0, null);
        this.onLongClickLabel = str;
        this.onLongClick = function02;
        this.onDoubleClick = function03;
        this.hapticFeedbackEnabled = z;
        int i = LongObjectMapKt.$r8$clinit;
        this.longKeyPressJobs = new MutableLongObjectMap(0, 1, null);
        this.doubleKeyClickStates = new MutableLongObjectMap(0, 1, null);
    }
}
