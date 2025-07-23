package androidx.compose.foundation;

import android.view.KeyEvent;
import androidx.collection.LongObjectMapKt;
import androidx.collection.MutableLongObjectMap;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.HoverInteraction$Exit;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Cancel;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.ui.focus.Focusability;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.key.KeyInputModifierNode;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventType;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.PointerInputModifierNode;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.IntOffset;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AbstractClickableNode extends DelegatingNode implements PointerInputModifierNode, KeyInputModifierNode, SemanticsModifierNode, TraversableNode {
    public static final TraverseKey TraverseKey = new TraverseKey(null);
    public long centerOffset;
    public final MutableLongObjectMap currentKeyPressInteractions;
    public boolean enabled;
    public final FocusableNode focusableNode;
    public HoverInteraction$Enter hoverInteraction;
    public DelegatableNode indicationNode;
    public IndicationNodeFactory indicationNodeFactory;
    public MutableInteractionSource interactionSource;
    public boolean lazilyCreateIndication;
    public Function0 onClick;
    public String onClickLabel;
    public SuspendingPointerInputModifierNode pointerInputNode;
    public PressInteraction$Press pressInteraction;
    public Role role;
    public final TraverseKey traverseKey;
    public MutableInteractionSource userProvidedInteractionSource;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TraverseKey {
        public /* synthetic */ TraverseKey(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private TraverseKey() {
        }
    }

    public /* synthetic */ AbstractClickableNode(MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z, String str, Role role, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableInteractionSource, indicationNodeFactory, z, str, role, function0);
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        Role role = this.role;
        if (role != null) {
            SemanticsPropertiesKt.m717setRolekuIjeqM(semanticsPropertyReceiver, role.value);
        }
        SemanticsPropertiesKt.onClick(semanticsPropertyReceiver, this.onClickLabel, new Function0() { // from class: androidx.compose.foundation.AbstractClickableNode$applySemantics$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AbstractClickableNode.this.onClick.invoke();
                return Boolean.TRUE;
            }
        });
        if (this.enabled) {
            this.focusableNode.applySemantics(semanticsPropertyReceiver);
        } else {
            SemanticsPropertiesKt.disabled(semanticsPropertyReceiver);
        }
        applyAdditionalSemantics(semanticsPropertyReceiver);
    }

    public abstract Object clickPointerInput(PointerInputScope pointerInputScope, Continuation continuation);

    public final void disposeInteractions() {
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        MutableLongObjectMap mutableLongObjectMap = this.currentKeyPressInteractions;
        if (mutableInteractionSource != null) {
            PressInteraction$Press pressInteraction$Press = this.pressInteraction;
            if (pressInteraction$Press != null) {
                mutableInteractionSource.tryEmit(new PressInteraction$Cancel(pressInteraction$Press));
            }
            HoverInteraction$Enter hoverInteraction$Enter = this.hoverInteraction;
            if (hoverInteraction$Enter != null) {
                mutableInteractionSource.tryEmit(new HoverInteraction$Exit(hoverInteraction$Enter));
            }
            Object[] objArr = mutableLongObjectMap.values;
            long[] jArr = mutableLongObjectMap.metadata;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i = 0;
                while (true) {
                    long j = jArr[i];
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i2 = 8 - ((~(i - length)) >>> 31);
                        for (int i3 = 0; i3 < i2; i3++) {
                            if ((255 & j) < 128) {
                                mutableInteractionSource.tryEmit(new PressInteraction$Cancel((PressInteraction$Press) objArr[(i << 3) + i3]));
                            }
                            j >>= 8;
                        }
                        if (i2 != 8) {
                            break;
                        }
                    }
                    if (i == length) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        this.pressInteraction = null;
        this.hoverInteraction = null;
        mutableLongObjectMap.clear();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final boolean getShouldMergeDescendantSemantics() {
        return true;
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public final Object getTraverseKey() {
        return this.traverseKey;
    }

    public final void initializeIndicationAndInteractionSourceIfNeeded() {
        IndicationNodeFactory indicationNodeFactory;
        if (this.indicationNode == null && (indicationNodeFactory = this.indicationNodeFactory) != null) {
            if (this.interactionSource == null) {
                this.interactionSource = InteractionSourceKt.MutableInteractionSource();
            }
            this.focusableNode.update(this.interactionSource);
            MutableInteractionSource mutableInteractionSource = this.interactionSource;
            mutableInteractionSource.getClass();
            DelegatableNode create = indicationNodeFactory.create(mutableInteractionSource);
            delegate(create);
            this.indicationNode = create;
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        if (!this.lazilyCreateIndication) {
            initializeIndicationAndInteractionSourceIfNeeded();
        }
        if (this.enabled) {
            delegate(this.focusableNode);
        }
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        HoverInteraction$Enter hoverInteraction$Enter;
        MutableInteractionSource mutableInteractionSource = this.interactionSource;
        if (mutableInteractionSource != null && (hoverInteraction$Enter = this.hoverInteraction) != null) {
            mutableInteractionSource.tryEmit(new HoverInteraction$Exit(hoverInteraction$Enter));
        }
        this.hoverInteraction = null;
        SuspendingPointerInputModifierNode suspendingPointerInputModifierNode = this.pointerInputNode;
        if (suspendingPointerInputModifierNode != null) {
            ((SuspendingPointerInputModifierNodeImpl) suspendingPointerInputModifierNode).onCancelPointerInput();
        }
    }

    /* renamed from: onClickKeyDownEvent-ZmokQxo, reason: not valid java name */
    public abstract boolean mo13onClickKeyDownEventZmokQxo(KeyEvent keyEvent);

    /* renamed from: onClickKeyUpEvent-ZmokQxo, reason: not valid java name */
    public abstract void mo14onClickKeyUpEventZmokQxo(KeyEvent keyEvent);

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        disposeInteractions();
        if (this.userProvidedInteractionSource == null) {
            this.interactionSource = null;
        }
        DelegatableNode delegatableNode = this.indicationNode;
        if (delegatableNode != null) {
            undelegate(delegatableNode);
        }
        this.indicationNode = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0084 A[RETURN] */
    @Override // androidx.compose.ui.input.key.KeyInputModifierNode
    /* renamed from: onKeyEvent-ZmokQxo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean mo15onKeyEventZmokQxo(android.view.KeyEvent r11) {
        /*
            r10 = this;
            r10.initializeIndicationAndInteractionSourceIfNeeded()
            long r0 = androidx.compose.ui.input.key.KeyEvent_androidKt.m578getKeyZmokQxo(r11)
            boolean r2 = r10.enabled
            r3 = 3
            r4 = 1
            r5 = 0
            androidx.collection.MutableLongObjectMap r6 = r10.currentKeyPressInteractions
            r7 = 0
            if (r2 == 0) goto L50
            int r2 = androidx.compose.ui.input.key.KeyEvent_androidKt.m579getTypeZmokQxo(r11)
            androidx.compose.ui.input.key.KeyEventType$Companion r8 = androidx.compose.ui.input.key.KeyEventType.Companion
            r8.getClass()
            int r8 = androidx.compose.ui.input.key.KeyEventType.KeyDown
            if (r2 != r8) goto L50
            boolean r2 = androidx.compose.foundation.ClickableKt.m39isEnterZmokQxo(r11)
            if (r2 == 0) goto L50
            boolean r2 = r6.containsKey(r0)
            if (r2 != 0) goto L46
            androidx.compose.foundation.interaction.PressInteraction$Press r2 = new androidx.compose.foundation.interaction.PressInteraction$Press
            long r8 = r10.centerOffset
            r2.<init>(r8, r5)
            r6.set(r0, r2)
            androidx.compose.foundation.interaction.MutableInteractionSource r0 = r10.interactionSource
            if (r0 == 0) goto L44
            kotlinx.coroutines.CoroutineScope r0 = r10.getCoroutineScope()
            androidx.compose.foundation.AbstractClickableNode$onKeyEvent$1 r1 = new androidx.compose.foundation.AbstractClickableNode$onKeyEvent$1
            r1.<init>(r10, r2, r5)
            kotlinx.coroutines.BuildersKt.launch$default(r0, r5, r5, r1, r3)
        L44:
            r0 = r4
            goto L47
        L46:
            r0 = r7
        L47:
            boolean r10 = r10.mo13onClickKeyDownEventZmokQxo(r11)
            if (r10 != 0) goto L84
            if (r0 == 0) goto L85
            goto L84
        L50:
            boolean r2 = r10.enabled
            if (r2 == 0) goto L85
            int r2 = androidx.compose.ui.input.key.KeyEvent_androidKt.m579getTypeZmokQxo(r11)
            androidx.compose.ui.input.key.KeyEventType$Companion r8 = androidx.compose.ui.input.key.KeyEventType.Companion
            r8.getClass()
            int r8 = androidx.compose.ui.input.key.KeyEventType.KeyUp
            if (r2 != r8) goto L85
            boolean r2 = androidx.compose.foundation.ClickableKt.m39isEnterZmokQxo(r11)
            if (r2 == 0) goto L85
            java.lang.Object r0 = r6.remove(r0)
            androidx.compose.foundation.interaction.PressInteraction$Press r0 = (androidx.compose.foundation.interaction.PressInteraction$Press) r0
            if (r0 == 0) goto L82
            androidx.compose.foundation.interaction.MutableInteractionSource r1 = r10.interactionSource
            if (r1 == 0) goto L7f
            kotlinx.coroutines.CoroutineScope r1 = r10.getCoroutineScope()
            androidx.compose.foundation.AbstractClickableNode$onKeyEvent$2 r2 = new androidx.compose.foundation.AbstractClickableNode$onKeyEvent$2
            r2.<init>(r10, r0, r5)
            kotlinx.coroutines.BuildersKt.launch$default(r1, r5, r5, r2, r3)
        L7f:
            r10.mo14onClickKeyUpEventZmokQxo(r11)
        L82:
            if (r0 == 0) goto L85
        L84:
            return r4
        L85:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AbstractClickableNode.mo15onKeyEventZmokQxo(android.view.KeyEvent):boolean");
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY, reason: not valid java name */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        long j2 = ((j >> 33) << 32) | (((j << 32) >> 33) & 4294967295L);
        IntOffset.Companion companion = IntOffset.Companion;
        Offset.Companion companion2 = Offset.Companion;
        this.centerOffset = (Float.floatToRawIntBits((int) (j2 >> 32)) << 32) | (Float.floatToRawIntBits((int) (j2 & 4294967295L)) & 4294967295L);
        initializeIndicationAndInteractionSourceIfNeeded();
        if (this.enabled && pointerEventPass == PointerEventPass.Main) {
            int i = pointerEvent.type;
            PointerEventType.Companion.getClass();
            if (i == PointerEventType.Enter) {
                BuildersKt.launch$default(getCoroutineScope(), null, null, new AbstractClickableNode$onPointerEvent$1(this, null), 3);
            } else if (i == PointerEventType.Exit) {
                BuildersKt.launch$default(getCoroutineScope(), null, null, new AbstractClickableNode$onPointerEvent$2(this, null), 3);
            }
        }
        if (this.pointerInputNode == null) {
            SuspendingPointerInputModifierNodeImpl SuspendingPointerInputModifierNode = SuspendingPointerInputFilterKt.SuspendingPointerInputModifierNode(new PointerInputEventHandler() { // from class: androidx.compose.foundation.AbstractClickableNode$onPointerEvent$3
                @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                    Object clickPointerInput = AbstractClickableNode.this.clickPointerInput(pointerInputScope, continuation);
                    return clickPointerInput == CoroutineSingletons.COROUTINE_SUSPENDED ? clickPointerInput : Unit.INSTANCE;
                }
            });
            delegate(SuspendingPointerInputModifierNode);
            this.pointerInputNode = SuspendingPointerInputModifierNode;
        }
        SuspendingPointerInputModifierNode suspendingPointerInputModifierNode = this.pointerInputNode;
        if (suspendingPointerInputModifierNode != null) {
            ((SuspendingPointerInputModifierNodeImpl) suspendingPointerInputModifierNode).mo16onPointerEventH0pRuoY(pointerEvent, pointerEventPass, j);
        }
    }

    @Override // androidx.compose.ui.input.key.KeyInputModifierNode
    /* renamed from: onPreKeyEvent-ZmokQxo, reason: not valid java name */
    public final boolean mo17onPreKeyEventZmokQxo(KeyEvent keyEvent) {
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x006e, code lost:
    
        if (r3.indicationNode == null) goto L38;
     */
    /* renamed from: updateCommon-QzZPfjk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m18updateCommonQzZPfjk(androidx.compose.foundation.interaction.MutableInteractionSource r4, androidx.compose.foundation.IndicationNodeFactory r5, boolean r6, java.lang.String r7, androidx.compose.ui.semantics.Role r8, kotlin.jvm.functions.Function0 r9) {
        /*
            r3 = this;
            androidx.compose.foundation.interaction.MutableInteractionSource r0 = r3.userProvidedInteractionSource
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r4)
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L13
            r3.disposeInteractions()
            r3.userProvidedInteractionSource = r4
            r3.interactionSource = r4
            r4 = r2
            goto L14
        L13:
            r4 = r1
        L14:
            androidx.compose.foundation.IndicationNodeFactory r0 = r3.indicationNodeFactory
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r5)
            if (r0 != 0) goto L1f
            r3.indicationNodeFactory = r5
            r4 = r2
        L1f:
            boolean r5 = r3.enabled
            androidx.compose.foundation.FocusableNode r0 = r3.focusableNode
            if (r5 == r6) goto L36
            if (r6 == 0) goto L2b
            r3.delegate(r0)
            goto L31
        L2b:
            r3.undelegate(r0)
            r3.disposeInteractions()
        L31:
            androidx.compose.ui.node.SemanticsModifierNodeKt.invalidateSemantics(r3)
            r3.enabled = r6
        L36:
            java.lang.String r5 = r3.onClickLabel
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r7)
            if (r5 != 0) goto L43
            r3.onClickLabel = r7
            androidx.compose.ui.node.SemanticsModifierNodeKt.invalidateSemantics(r3)
        L43:
            androidx.compose.ui.semantics.Role r5 = r3.role
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r8)
            if (r5 != 0) goto L50
            r3.role = r8
            androidx.compose.ui.node.SemanticsModifierNodeKt.invalidateSemantics(r3)
        L50:
            r3.onClick = r9
            boolean r5 = r3.lazilyCreateIndication
            androidx.compose.foundation.interaction.MutableInteractionSource r6 = r3.userProvidedInteractionSource
            if (r6 != 0) goto L5e
            androidx.compose.foundation.IndicationNodeFactory r7 = r3.indicationNodeFactory
            if (r7 == 0) goto L5e
            r7 = r2
            goto L5f
        L5e:
            r7 = r1
        L5f:
            if (r5 == r7) goto L71
            if (r6 != 0) goto L68
            androidx.compose.foundation.IndicationNodeFactory r5 = r3.indicationNodeFactory
            if (r5 == 0) goto L68
            r1 = r2
        L68:
            r3.lazilyCreateIndication = r1
            if (r1 != 0) goto L71
            androidx.compose.ui.node.DelegatableNode r5 = r3.indicationNode
            if (r5 != 0) goto L71
            goto L72
        L71:
            r2 = r4
        L72:
            if (r2 == 0) goto L87
            androidx.compose.ui.node.DelegatableNode r4 = r3.indicationNode
            if (r4 != 0) goto L7c
            boolean r5 = r3.lazilyCreateIndication
            if (r5 != 0) goto L87
        L7c:
            if (r4 == 0) goto L81
            r3.undelegate(r4)
        L81:
            r4 = 0
            r3.indicationNode = r4
            r3.initializeIndicationAndInteractionSourceIfNeeded()
        L87:
            androidx.compose.foundation.interaction.MutableInteractionSource r3 = r3.interactionSource
            r0.update(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.AbstractClickableNode.m18updateCommonQzZPfjk(androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.foundation.IndicationNodeFactory, boolean, java.lang.String, androidx.compose.ui.semantics.Role, kotlin.jvm.functions.Function0):void");
    }

    private AbstractClickableNode(MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z, String str, Role role, Function0 function0) {
        this.interactionSource = mutableInteractionSource;
        this.indicationNodeFactory = indicationNodeFactory;
        this.onClickLabel = str;
        this.role = role;
        this.enabled = z;
        this.onClick = function0;
        MutableInteractionSource mutableInteractionSource2 = this.interactionSource;
        Focusability.Companion.getClass();
        boolean z2 = false;
        z2 = false;
        this.focusableNode = new FocusableNode(mutableInteractionSource2, z2 ? 1 : 0, new AbstractClickableNode$focusableNode$1(this), null);
        int i = LongObjectMapKt.$r8$clinit;
        this.currentKeyPressInteractions = new MutableLongObjectMap(0, 1, null);
        Offset.Companion.getClass();
        this.centerOffset = 0L;
        MutableInteractionSource mutableInteractionSource3 = this.interactionSource;
        this.userProvidedInteractionSource = mutableInteractionSource3;
        if (mutableInteractionSource3 == null && this.indicationNodeFactory != null) {
            z2 = true;
        }
        this.lazilyCreateIndication = z2;
        this.traverseKey = TraverseKey;
    }

    public void onCancelKeyInput() {
    }

    public void applyAdditionalSemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
    }
}
