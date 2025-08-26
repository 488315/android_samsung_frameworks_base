package androidx.compose.foundation;

import android.view.KeyEvent;
import androidx.collection.LongObjectMapKt;
import androidx.collection.MutableLongObjectMap;
import androidx.compose.foundation.gestures.PressGestureScope;
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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;

/* loaded from: classes.dex */
final class CombinedClickableNode extends AbstractClickableNode implements CompositionLocalConsumerModifierNode {
    public final MutableLongObjectMap doubleKeyClickStates;
    public boolean hapticFeedbackEnabled;
    public final MutableLongObjectMap longKeyPressJobs;
    public Function0 onDoubleClick;
    public Function0 onLongClick;
    public String onLongClickLabel;

    public final class DoubleKeyClickState {
        public boolean doubleTapMinTimeMillisElapsed;
        public final Job job;

        public DoubleKeyClickState(Job job) {
            this.job = job;
        }
    }

    /* renamed from: androidx.compose.foundation.CombinedClickableNode$clickPointerInput$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function3 {
        /* synthetic */ long J$0;
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            long j = ((Offset) obj2).packedValue;
            AnonymousClass4 anonymousClass4 = CombinedClickableNode.this.new AnonymousClass4((Continuation) obj3);
            anonymousClass4.L$0 = (PressGestureScope) obj;
            anonymousClass4.J$0 = j;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object objCoroutineScope;
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PressGestureScope pressGestureScope = (PressGestureScope) this.L$0;
                long j = this.J$0;
                CombinedClickableNode combinedClickableNode = CombinedClickableNode.this;
                if (combinedClickableNode.enabled) {
                    this.label = 1;
                    MutableInteractionSource mutableInteractionSource = combinedClickableNode.interactionSource;
                    if (mutableInteractionSource == null || (objCoroutineScope = CoroutineScopeKt.coroutineScope(new AbstractClickableNode$handlePressInteraction$2$1(pressGestureScope, j, mutableInteractionSource, combinedClickableNode, null), this)) != obj2) {
                        objCoroutineScope = Unit.INSTANCE;
                    }
                    if (objCoroutineScope == obj2) {
                        return obj2;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public /* synthetic */ CombinedClickableNode(Function0 function0, String str, Function0 function02, Function0 function03, boolean z, MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z2, String str2, Role role, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0, str, function02, function03, z, mutableInteractionSource, indicationNodeFactory, z2, str2, role);
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    public final void applyAdditionalSemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        if (this.onLongClick != null) {
            SemanticsPropertiesKt.onLongClick(semanticsPropertyReceiver, this.onLongClickLabel, new Function0() { // from class: androidx.compose.foundation.CombinedClickableNode.applyAdditionalSemantics.1
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
        Object objDetectTapGestures = TapGestureDetectorKt.detectTapGestures(pointerInputScope, new AnonymousClass4(null), (!this.enabled || this.onDoubleClick == null) ? null : new Function1() { // from class: androidx.compose.foundation.CombinedClickableNode.clickPointerInput.2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                long j = ((Offset) obj).packedValue;
                Function0 function0 = CombinedClickableNode.this.onDoubleClick;
                if (function0 != null) {
                    function0.invoke();
                }
                return Unit.INSTANCE;
            }
        }, (!this.enabled || this.onLongClick == null) ? null : new Function1() { // from class: androidx.compose.foundation.CombinedClickableNode.clickPointerInput.3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
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
                    hapticFeedback.mo572performHapticFeedbackCdsT49E(0);
                }
                return Unit.INSTANCE;
            }
        }, new Function1() { // from class: androidx.compose.foundation.CombinedClickableNode.clickPointerInput.5
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                long j = ((Offset) obj).packedValue;
                CombinedClickableNode combinedClickableNode = CombinedClickableNode.this;
                if (combinedClickableNode.enabled) {
                    combinedClickableNode.onClick.invoke();
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return objDetectTapGestures == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectTapGestures : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    public final void onCancelKeyInput() {
        resetKeyPressState();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0024  */
    @Override // androidx.compose.foundation.AbstractClickableNode
    /* renamed from: onClickKeyDownEvent-ZmokQxo */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean mo13onClickKeyDownEventZmokQxo(KeyEvent keyEvent) {
        boolean z;
        long jM580getKeyZmokQxo = KeyEvent_androidKt.m580getKeyZmokQxo(keyEvent);
        if (this.onLongClick != null) {
            MutableLongObjectMap mutableLongObjectMap = this.longKeyPressJobs;
            if (mutableLongObjectMap.get(jM580getKeyZmokQxo) == null) {
                mutableLongObjectMap.set(jM580getKeyZmokQxo, BuildersKt.launch$default(getCoroutineScope(), null, null, new CombinedClickableNode$onClickKeyDownEvent$1(this, null), 3));
                z = true;
            } else {
                z = false;
            }
        }
        MutableLongObjectMap mutableLongObjectMap2 = this.doubleKeyClickStates;
        DoubleKeyClickState doubleKeyClickState = (DoubleKeyClickState) mutableLongObjectMap2.get(jM580getKeyZmokQxo);
        if (doubleKeyClickState != null) {
            Job job = doubleKeyClickState.job;
            if (job.isActive()) {
                job.cancel(null);
                if (!doubleKeyClickState.doubleTapMinTimeMillisElapsed) {
                    this.onClick.invoke();
                    mutableLongObjectMap2.remove(jM580getKeyZmokQxo);
                    return z;
                }
            } else {
                mutableLongObjectMap2.remove(jM580getKeyZmokQxo);
            }
        }
        return z;
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    /* renamed from: onClickKeyUpEvent-ZmokQxo */
    public final void mo14onClickKeyUpEventZmokQxo(KeyEvent keyEvent) {
        Function0 function0;
        long jM580getKeyZmokQxo = KeyEvent_androidKt.m580getKeyZmokQxo(keyEvent);
        MutableLongObjectMap mutableLongObjectMap = this.longKeyPressJobs;
        boolean z = false;
        if (mutableLongObjectMap.get(jM580getKeyZmokQxo) != null) {
            Job job = (Job) mutableLongObjectMap.get(jM580getKeyZmokQxo);
            if (job != null) {
                if (job.isActive()) {
                    job.cancel(null);
                } else {
                    z = true;
                }
            }
            mutableLongObjectMap.remove(jM580getKeyZmokQxo);
        }
        if (this.onDoubleClick == null) {
            if (z) {
                return;
            }
            this.onClick.invoke();
            return;
        }
        MutableLongObjectMap mutableLongObjectMap2 = this.doubleKeyClickStates;
        if (mutableLongObjectMap2.get(jM580getKeyZmokQxo) == null) {
            if (z) {
                return;
            }
            mutableLongObjectMap2.set(jM580getKeyZmokQxo, new DoubleKeyClickState(BuildersKt.launch$default(getCoroutineScope(), null, null, new CombinedClickableNode$onClickKeyUpEvent$2(this, jM580getKeyZmokQxo, null), 3)));
        } else {
            if (!z && (function0 = this.onDoubleClick) != null) {
                function0.invoke();
            }
            mutableLongObjectMap2.remove(jM580getKeyZmokQxo);
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onReset() {
        resetKeyPressState();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                    } else if (i4 == length2) {
                        break;
                    } else {
                        i4++;
                    }
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
