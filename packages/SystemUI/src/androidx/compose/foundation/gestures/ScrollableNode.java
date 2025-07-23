package androidx.compose.foundation.gestures;

import android.view.KeyEvent;
import android.view.ViewConfiguration;
import androidx.compose.animation.SplineBasedFloatDecayAnimationSpec;
import androidx.compose.animation.core.DecayAnimationSpecKt;
import androidx.compose.foundation.FocusedBoundsObserverNode;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.MouseWheelScrollingLogic;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.relocation.BringIntoViewResponderNode;
import androidx.compose.ui.focus.FocusTargetNode;
import androidx.compose.ui.focus.Focusability;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.KeyInputModifierNode;
import androidx.compose.ui.input.key.Key_androidKt;
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher;
import androidx.compose.ui.input.nestedscroll.NestedScrollNode;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventType;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatableNode_androidKt;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.node.SemanticsModifierNodeKt;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScrollableNode extends DragGestureNode implements KeyInputModifierNode, SemanticsModifierNode, CompositionLocalConsumerModifierNode {
    public final ContentInViewNode contentInViewNode;
    public final DefaultFlingBehavior defaultFlingBehavior;
    public FlingBehavior flingBehavior;
    public MouseWheelScrollingLogic mouseWheelScrollingLogic;
    public final ScrollableNestedScrollConnection nestedScrollConnection;
    public final NestedScrollDispatcher nestedScrollDispatcher;
    public OverscrollEffect overscrollEffect;
    public Function2 scrollByAction;
    public Function2 scrollByOffsetAction;
    public final ScrollableContainerNode scrollableContainerNode;
    public final ScrollingLogic scrollingLogic;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v3, types: [androidx.compose.foundation.gestures.FlingBehavior] */
    /* JADX WARN: Type inference failed for: r9v0, types: [androidx.compose.foundation.gestures.ScrollableNode, androidx.compose.ui.node.DelegatingNode] */
    public ScrollableNode(ScrollableState scrollableState, OverscrollEffect overscrollEffect, FlingBehavior flingBehavior, Orientation orientation, boolean z, boolean z2, MutableInteractionSource mutableInteractionSource, BringIntoViewSpec bringIntoViewSpec) {
        super(ScrollableKt.CanDragCalculation, z, mutableInteractionSource, orientation);
        this.overscrollEffect = overscrollEffect;
        this.flingBehavior = flingBehavior;
        NestedScrollDispatcher nestedScrollDispatcher = new NestedScrollDispatcher();
        this.nestedScrollDispatcher = nestedScrollDispatcher;
        ScrollableContainerNode scrollableContainerNode = new ScrollableContainerNode(z);
        delegate(scrollableContainerNode);
        this.scrollableContainerNode = scrollableContainerNode;
        DefaultFlingBehavior defaultFlingBehavior = new DefaultFlingBehavior(DecayAnimationSpecKt.generateDecayAnimationSpec(new SplineBasedFloatDecayAnimationSpec(ScrollableKt.UnityDensity)), null, 2, null);
        this.defaultFlingBehavior = defaultFlingBehavior;
        OverscrollEffect overscrollEffect2 = this.overscrollEffect;
        ?? r12 = this.flingBehavior;
        ScrollingLogic scrollingLogic = new ScrollingLogic(scrollableState, overscrollEffect2, r12 == 0 ? defaultFlingBehavior : r12, orientation, z2, nestedScrollDispatcher, new Function0() { // from class: androidx.compose.foundation.gestures.ScrollableNode$scrollingLogic$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(ScrollableNode.this.isAttached);
            }
        });
        this.scrollingLogic = scrollingLogic;
        ScrollableNestedScrollConnection scrollableNestedScrollConnection = new ScrollableNestedScrollConnection(scrollingLogic, z);
        this.nestedScrollConnection = scrollableNestedScrollConnection;
        ContentInViewNode contentInViewNode = new ContentInViewNode(orientation, scrollingLogic, z2, bringIntoViewSpec);
        delegate(contentInViewNode);
        this.contentInViewNode = contentInViewNode;
        delegate(new NestedScrollNode(scrollableNestedScrollConnection, nestedScrollDispatcher));
        Focusability.Companion.getClass();
        delegate(new FocusTargetNode(Focusability.Never, null, null, 4, null));
        delegate(new BringIntoViewResponderNode(contentInViewNode));
        delegate(new FocusedBoundsObserverNode(new Function1() { // from class: androidx.compose.foundation.gestures.ScrollableNode.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Rect focusedChildBounds;
                ContentInViewNode contentInViewNode2 = ScrollableNode.this.contentInViewNode;
                contentInViewNode2.focusedChild = (LayoutCoordinates) obj;
                if (contentInViewNode2.childWasMaxVisibleBeforeViewportShrunk && (focusedChildBounds = contentInViewNode2.getFocusedChildBounds()) != null && !contentInViewNode2.m64isMaxVisibleO0kMr_c(focusedChildBounds, contentInViewNode2.viewportSize)) {
                    contentInViewNode2.trackingFocusedChild = true;
                    contentInViewNode2.launchAnimation();
                }
                contentInViewNode2.childWasMaxVisibleBeforeViewportShrunk = false;
                return Unit.INSTANCE;
            }
        }));
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        if (this.enabled && (this.scrollByAction == null || this.scrollByOffsetAction == null)) {
            this.scrollByAction = new Function2() { // from class: androidx.compose.foundation.gestures.ScrollableNode$setScrollSemanticsActions$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: androidx.compose.foundation.gestures.ScrollableNode$setScrollSemanticsActions$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    final /* synthetic */ float $x;
                    final /* synthetic */ float $y;
                    int label;
                    final /* synthetic */ ScrollableNode this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(ScrollableNode scrollableNode, float f, float f2, Continuation continuation) {
                        super(2, continuation);
                        this.this$0 = scrollableNode;
                        this.$x = f;
                        this.$y = f2;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass1(this.this$0, this.$x, this.$y, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            ScrollingLogic scrollingLogic = this.this$0.scrollingLogic;
                            float f = this.$x;
                            float f2 = this.$y;
                            long floatToRawIntBits = Float.floatToRawIntBits(f);
                            long floatToRawIntBits2 = Float.floatToRawIntBits(f2);
                            Offset.Companion companion = Offset.Companion;
                            this.label = 1;
                            if (ScrollableKt.m76access$semanticsScrollByd4ec7I(scrollingLogic, (floatToRawIntBits << 32) | (floatToRawIntBits2 & 4294967295L), this) == coroutineSingletons) {
                                return coroutineSingletons;
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

                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    BuildersKt.launch$default(ScrollableNode.this.getCoroutineScope(), null, null, new AnonymousClass1(ScrollableNode.this, ((Number) obj).floatValue(), ((Number) obj2).floatValue(), null), 3);
                    return Boolean.TRUE;
                }
            };
            this.scrollByOffsetAction = new ScrollableNode$setScrollSemanticsActions$2(this, null);
        }
        Function2 function2 = this.scrollByAction;
        if (function2 != null) {
            KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
            SemanticsActions.INSTANCE.getClass();
            ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsActions.ScrollBy, new AccessibilityAction(null, function2));
        }
        Function2 function22 = this.scrollByOffsetAction;
        if (function22 != null) {
            KProperty[] kPropertyArr2 = SemanticsPropertiesKt.$$delegatedProperties;
            SemanticsActions.INSTANCE.getClass();
            ((SemanticsConfiguration) semanticsPropertyReceiver).set(SemanticsActions.ScrollByOffset, function22);
        }
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    public final Object drag(Function2 function2, Continuation continuation) {
        MutatePriority mutatePriority = MutatePriority.UserInput;
        ScrollingLogic scrollingLogic = this.scrollingLogic;
        Object scroll = scrollingLogic.scroll(mutatePriority, new ScrollableNode$drag$2$1(function2, scrollingLogic, null), (ContinuationImpl) continuation);
        return scroll == CoroutineSingletons.COROUTINE_SUSPENDED ? scroll : Unit.INSTANCE;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        if (this.isAttached) {
            Density density = DelegatableNodeKt.requireLayoutNode(this).density;
            DefaultFlingBehavior defaultFlingBehavior = this.defaultFlingBehavior;
            defaultFlingBehavior.getClass();
            defaultFlingBehavior.flingDecay = DecayAnimationSpecKt.generateDecayAnimationSpec(new SplineBasedFloatDecayAnimationSpec(density));
        }
        MouseWheelScrollingLogic mouseWheelScrollingLogic = this.mouseWheelScrollingLogic;
        if (mouseWheelScrollingLogic != null) {
            mouseWheelScrollingLogic.density = DelegatableNodeKt.requireLayoutNode(this).density;
        }
    }

    @Override // androidx.compose.ui.node.DelegatableNode, androidx.compose.ui.node.PointerInputModifierNode
    public final void onDensityChange() {
        onCancelPointerInput();
        if (this.isAttached) {
            Density density = DelegatableNodeKt.requireLayoutNode(this).density;
            DefaultFlingBehavior defaultFlingBehavior = this.defaultFlingBehavior;
            defaultFlingBehavior.getClass();
            defaultFlingBehavior.flingDecay = DecayAnimationSpecKt.generateDecayAnimationSpec(new SplineBasedFloatDecayAnimationSpec(density));
        }
        MouseWheelScrollingLogic mouseWheelScrollingLogic = this.mouseWheelScrollingLogic;
        if (mouseWheelScrollingLogic != null) {
            mouseWheelScrollingLogic.density = DelegatableNodeKt.requireLayoutNode(this).density;
        }
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    /* renamed from: onDragStopped-TH1AsA0 */
    public final void mo63onDragStoppedTH1AsA0(long j) {
        BuildersKt.launch$default(this.nestedScrollDispatcher.getCoroutineScope(), null, null, new ScrollableNode$onDragStopped$1(this, j, null), 3);
    }

    @Override // androidx.compose.ui.input.key.KeyInputModifierNode
    /* renamed from: onKeyEvent-ZmokQxo */
    public final boolean mo15onKeyEventZmokQxo(KeyEvent keyEvent) {
        long floatToRawIntBits;
        if (this.enabled) {
            long m578getKeyZmokQxo = KeyEvent_androidKt.m578getKeyZmokQxo(keyEvent);
            Key.Companion.getClass();
            if (Key.m576equalsimpl0(m578getKeyZmokQxo, Key.PageDown) || Key.m576equalsimpl0(Key_androidKt.Key(keyEvent.getKeyCode()), Key.PageUp)) {
                int m579getTypeZmokQxo = KeyEvent_androidKt.m579getTypeZmokQxo(keyEvent);
                KeyEventType.Companion.getClass();
                if (m579getTypeZmokQxo == KeyEventType.KeyDown && !keyEvent.isCtrlPressed()) {
                    boolean z = this.scrollingLogic.orientation == Orientation.Vertical;
                    ContentInViewNode contentInViewNode = this.contentInViewNode;
                    if (z) {
                        int i = (int) (contentInViewNode.viewportSize & 4294967295L);
                        floatToRawIntBits = (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(Key.m576equalsimpl0(Key_androidKt.Key(keyEvent.getKeyCode()), Key.PageUp) ? i : -i) & 4294967295L);
                        Offset.Companion companion = Offset.Companion;
                    } else {
                        int i2 = (int) (contentInViewNode.viewportSize >> 32);
                        floatToRawIntBits = (Float.floatToRawIntBits(Key.m576equalsimpl0(Key_androidKt.Key(keyEvent.getKeyCode()), Key.PageUp) ? i2 : -i2) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
                        Offset.Companion companion2 = Offset.Companion;
                    }
                    BuildersKt.launch$default(getCoroutineScope(), null, null, new ScrollableNode$onKeyEvent$1(this, floatToRawIntBits, null), 3);
                    return true;
                }
            }
        }
        return false;
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode, androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        long j2;
        boolean z;
        List list = pointerEvent.changes;
        int size = list.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            if (((Boolean) this.canDrag.mo779invoke((PointerInputChange) list.get(i))).booleanValue()) {
                super.mo16onPointerEventH0pRuoY(pointerEvent, pointerEventPass, j);
                break;
            }
            i++;
        }
        if (this.enabled) {
            if (pointerEventPass == PointerEventPass.Initial) {
                int i2 = pointerEvent.type;
                PointerEventType.Companion.getClass();
                if (i2 == PointerEventType.Scroll) {
                    if (this.mouseWheelScrollingLogic == null) {
                        this.mouseWheelScrollingLogic = new MouseWheelScrollingLogic(this.scrollingLogic, new AndroidConfig(ViewConfiguration.get(DelegatableNode_androidKt.requireView(this).getContext())), new ScrollableNode$ensureMouseWheelScrollNodeInitialized$1(this), DelegatableNodeKt.requireLayoutNode(this).density);
                    }
                    MouseWheelScrollingLogic mouseWheelScrollingLogic = this.mouseWheelScrollingLogic;
                    if (mouseWheelScrollingLogic != null) {
                        CoroutineScope coroutineScope = getCoroutineScope();
                        if (mouseWheelScrollingLogic.receivingMouseWheelEventsJob == null) {
                            mouseWheelScrollingLogic.receivingMouseWheelEventsJob = BuildersKt.launch$default(coroutineScope, null, null, new MouseWheelScrollingLogic$startReceivingMouseWheelEvents$1(mouseWheelScrollingLogic, null), 3);
                        }
                    }
                }
            }
            MouseWheelScrollingLogic mouseWheelScrollingLogic2 = this.mouseWheelScrollingLogic;
            if (mouseWheelScrollingLogic2 == null || pointerEventPass != PointerEventPass.Main) {
                return;
            }
            int i3 = pointerEvent.type;
            PointerEventType.Companion.getClass();
            if (i3 == PointerEventType.Scroll) {
                List list2 = pointerEvent.changes;
                int size2 = list2.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    if (((PointerInputChange) list2.get(i4)).isConsumed()) {
                        return;
                    }
                }
                ScrollConfig scrollConfig = mouseWheelScrollingLogic2.mouseWheelScrollConfig;
                AndroidConfig androidConfig = (AndroidConfig) scrollConfig;
                androidConfig.getClass();
                ViewConfigurationApi26Impl viewConfigurationApi26Impl = ViewConfigurationApi26Impl.INSTANCE;
                ViewConfiguration viewConfiguration = androidConfig.viewConfiguration;
                viewConfigurationApi26Impl.getClass();
                float f = -viewConfiguration.getScaledVerticalScrollFactor();
                float f2 = -androidConfig.viewConfiguration.getScaledHorizontalScrollFactor();
                List list3 = pointerEvent.changes;
                Offset.Companion.getClass();
                Offset m393boximpl = Offset.m393boximpl(0L);
                int size3 = list3.size();
                int i5 = 0;
                while (true) {
                    j2 = m393boximpl.packedValue;
                    if (i5 >= size3) {
                        break;
                    }
                    m393boximpl = Offset.m393boximpl(Offset.m401plusMKHz9U(j2, ((PointerInputChange) list3.get(i5)).scrollDelta));
                    i5++;
                }
                long floatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j2 >> 32)) * f2) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j2 & 4294967295L)) * f) & 4294967295L);
                ScrollingLogic scrollingLogic = mouseWheelScrollingLogic2.scrollingLogic;
                float m84toFloatk4lQ0M = scrollingLogic.m84toFloatk4lQ0M(scrollingLogic.m83reverseIfNeededMKHz9U(floatToRawIntBits));
                if (m84toFloatk4lQ0M == 0.0f ? false : m84toFloatk4lQ0M > 0.0f ? scrollingLogic.scrollableState.getCanScrollForward() : scrollingLogic.scrollableState.getCanScrollBackward()) {
                    BufferedChannel bufferedChannel = mouseWheelScrollingLogic2.channel;
                    long j3 = ((PointerInputChange) CollectionsKt___CollectionsKt.first(pointerEvent.changes)).uptimeMillis;
                    scrollConfig.getClass();
                    Object mo3456trySendJP2dKIU = bufferedChannel.mo3456trySendJP2dKIU(new MouseWheelScrollingLogic.MouseWheelScrollDelta(floatToRawIntBits, j3, false, null));
                    ChannelResult.Companion companion = ChannelResult.Companion;
                    z = !(mo3456trySendJP2dKIU instanceof ChannelResult.Failed);
                } else {
                    z = mouseWheelScrollingLogic2.isScrolling;
                }
                if (z) {
                    List list4 = pointerEvent.changes;
                    int size4 = list4.size();
                    for (int i6 = 0; i6 < size4; i6++) {
                        ((PointerInputChange) list4.get(i6)).consume();
                    }
                }
            }
        }
    }

    @Override // androidx.compose.ui.input.key.KeyInputModifierNode
    /* renamed from: onPreKeyEvent-ZmokQxo */
    public final boolean mo17onPreKeyEventZmokQxo(KeyEvent keyEvent) {
        return false;
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    public final boolean startDragImmediately() {
        ScrollingLogic scrollingLogic = this.scrollingLogic;
        if (scrollingLogic.scrollableState.isScrollInProgress()) {
            return true;
        }
        OverscrollEffect overscrollEffect = scrollingLogic.overscrollEffect;
        return overscrollEffect != null ? overscrollEffect.isInProgress() : false;
    }

    public final void update(ScrollableState scrollableState, Orientation orientation, OverscrollEffect overscrollEffect, boolean z, boolean z2, FlingBehavior flingBehavior, MutableInteractionSource mutableInteractionSource, BringIntoViewSpec bringIntoViewSpec) {
        boolean z3;
        boolean z4 = true;
        boolean z5 = false;
        if (this.enabled != z) {
            this.nestedScrollConnection.enabled = z;
            this.scrollableContainerNode.enabled = z;
            z3 = true;
        } else {
            z3 = false;
        }
        FlingBehavior flingBehavior2 = flingBehavior == null ? this.defaultFlingBehavior : flingBehavior;
        ScrollingLogic scrollingLogic = this.scrollingLogic;
        if (!Intrinsics.areEqual(scrollingLogic.scrollableState, scrollableState)) {
            scrollingLogic.scrollableState = scrollableState;
            z5 = true;
        }
        scrollingLogic.overscrollEffect = overscrollEffect;
        if (scrollingLogic.orientation != orientation) {
            scrollingLogic.orientation = orientation;
            z5 = true;
        }
        if (scrollingLogic.reverseDirection != z2) {
            scrollingLogic.reverseDirection = z2;
        } else {
            z4 = z5;
        }
        scrollingLogic.flingBehavior = flingBehavior2;
        scrollingLogic.nestedScrollDispatcher = this.nestedScrollDispatcher;
        ContentInViewNode contentInViewNode = this.contentInViewNode;
        contentInViewNode.orientation = orientation;
        contentInViewNode.reverseDirection = z2;
        contentInViewNode.bringIntoViewSpec = bringIntoViewSpec;
        this.overscrollEffect = overscrollEffect;
        this.flingBehavior = flingBehavior;
        Function1 function1 = ScrollableKt.CanDragCalculation;
        Orientation orientation2 = scrollingLogic.orientation;
        Orientation orientation3 = Orientation.Vertical;
        if (orientation2 != orientation3) {
            orientation3 = Orientation.Horizontal;
        }
        update(function1, z, mutableInteractionSource, orientation3, z4);
        if (z3) {
            this.scrollByAction = null;
            this.scrollByOffsetAction = null;
            SemanticsModifierNodeKt.invalidateSemantics(this);
        }
    }

    @Override // androidx.compose.foundation.gestures.DragGestureNode
    /* renamed from: onDragStarted-k-4lQ0M */
    public final void mo62onDragStartedk4lQ0M(long j) {
    }
}
