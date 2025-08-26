package androidx.compose.ui.input.pointer;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DpTouchBoundsExpansion;
import androidx.compose.ui.node.PointerInputModifierNode;
import androidx.compose.ui.node.TouchBoundsExpansion;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNode$Companion$TraverseDescendantsAction;
import androidx.compose.ui.node.TraversableNodeKt;
import androidx.compose.ui.unit.Density;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public abstract class HoverIconModifierNode extends Modifier.Node implements TraversableNode, PointerInputModifierNode, CompositionLocalConsumerModifierNode {
    public boolean cursorInBoundsOfNode;
    public DpTouchBoundsExpansion dpTouchBoundsExpansion;
    public PointerIcon icon;
    public boolean overrideDescendants;

    public /* synthetic */ HoverIconModifierNode(PointerIcon pointerIcon, boolean z, DpTouchBoundsExpansion dpTouchBoundsExpansion, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(pointerIcon, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : dpTouchBoundsExpansion);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void displayIcon() {
        PointerIcon pointerIcon;
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        TraversableNodeKt.traverseAncestors(this, new Function1() { // from class: androidx.compose.ui.input.pointer.HoverIconModifierNode$findOverridingAncestorNode$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Type inference failed for: r2v1, types: [T, androidx.compose.ui.input.pointer.HoverIconModifierNode] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ?? r2 = (HoverIconModifierNode) obj;
                if (r2.overrideDescendants && r2.cursorInBoundsOfNode) {
                    ref$ObjectRef.element = r2;
                }
                return Boolean.TRUE;
            }
        });
        HoverIconModifierNode hoverIconModifierNode = (HoverIconModifierNode) ref$ObjectRef.element;
        if (hoverIconModifierNode == null || (pointerIcon = hoverIconModifierNode.icon) == null) {
            pointerIcon = this.icon;
        }
        displayIcon(pointerIcon);
    }

    public abstract void displayIcon(PointerIcon pointerIcon);

    public final void displayIconIfDescendantsDoNotHavePriority() {
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ref$BooleanRef.element = true;
        if (!this.overrideDescendants) {
            TraversableNodeKt.traverseDescendants(this, new Function1() { // from class: androidx.compose.ui.input.pointer.HoverIconModifierNode.displayIconIfDescendantsDoNotHavePriority.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    if (!((HoverIconModifierNode) obj).cursorInBoundsOfNode) {
                        return TraversableNode$Companion$TraverseDescendantsAction.ContinueTraversal;
                    }
                    ref$BooleanRef.element = false;
                    return TraversableNode$Companion$TraverseDescendantsAction.CancelTraversal;
                }
            });
        }
        if (ref$BooleanRef.element) {
            displayIcon();
        }
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: getTouchBoundsExpansion-RZrCHBk */
    public final long mo213getTouchBoundsExpansionRZrCHBk() {
        DpTouchBoundsExpansion dpTouchBoundsExpansion = this.dpTouchBoundsExpansion;
        if (dpTouchBoundsExpansion == null) {
            TouchBoundsExpansion.Companion.getClass();
            return TouchBoundsExpansion.None;
        }
        Density density = DelegatableNodeKt.requireLayoutNode(this).density;
        TouchBoundsExpansion.Companion companion = TouchBoundsExpansion.Companion;
        int iMo52roundToPx0680j_4 = density.mo52roundToPx0680j_4(dpTouchBoundsExpansion.start);
        int iMo52roundToPx0680j_42 = density.mo52roundToPx0680j_4(dpTouchBoundsExpansion.top);
        int iMo52roundToPx0680j_43 = density.mo52roundToPx0680j_4(dpTouchBoundsExpansion.end);
        int iMo52roundToPx0680j_44 = density.mo52roundToPx0680j_4(dpTouchBoundsExpansion.bottom);
        companion.getClass();
        return TouchBoundsExpansion.Companion.pack$ui_release(dpTouchBoundsExpansion.isLayoutDirectionAware, iMo52roundToPx0680j_4, iMo52roundToPx0680j_42, iMo52roundToPx0680j_43, iMo52roundToPx0680j_44);
    }

    /* renamed from: isRelevantPointerType-uerMTgs, reason: not valid java name */
    public abstract boolean mo589isRelevantPointerTypeuerMTgs(int i);

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        onExit();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        onExit();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onExit() {
        Unit unit;
        if (this.cursorInBoundsOfNode) {
            this.cursorInBoundsOfNode = false;
            if (this.isAttached) {
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                TraversableNodeKt.traverseAncestors(this, new Function1() { // from class: androidx.compose.ui.input.pointer.HoverIconModifierNode$displayIconFromAncestorNodeWithCursorInBoundsOrDefaultIcon$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Type inference failed for: r3v1, types: [T, androidx.compose.ui.input.pointer.HoverIconModifierNode] */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        ?? r3 = (HoverIconModifierNode) obj;
                        Ref$ObjectRef<HoverIconModifierNode> ref$ObjectRef2 = ref$ObjectRef;
                        HoverIconModifierNode hoverIconModifierNode = ref$ObjectRef2.element;
                        if (hoverIconModifierNode == null && r3.cursorInBoundsOfNode) {
                            ref$ObjectRef2.element = r3;
                        } else if (hoverIconModifierNode != null && r3.overrideDescendants && r3.cursorInBoundsOfNode) {
                            ref$ObjectRef2.element = r3;
                        }
                        return Boolean.TRUE;
                    }
                });
                HoverIconModifierNode hoverIconModifierNode = (HoverIconModifierNode) ref$ObjectRef.element;
                if (hoverIconModifierNode != null) {
                    hoverIconModifierNode.displayIcon();
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                if (unit == null) {
                    displayIcon(null);
                }
            }
        }
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        if (pointerEventPass == PointerEventPass.Main) {
            List list = pointerEvent.changes;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (mo589isRelevantPointerTypeuerMTgs(((PointerInputChange) list.get(i)).type)) {
                    int i2 = pointerEvent.type;
                    PointerEventType.Companion.getClass();
                    if (i2 == PointerEventType.Enter) {
                        this.cursorInBoundsOfNode = true;
                        displayIconIfDescendantsDoNotHavePriority();
                        return;
                    } else {
                        if (pointerEvent.type == PointerEventType.Exit) {
                            onExit();
                            return;
                        }
                        return;
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setOverrideDescendants(boolean z) {
        if (this.overrideDescendants != z) {
            this.overrideDescendants = z;
            if (z) {
                if (this.cursorInBoundsOfNode) {
                    displayIcon();
                    return;
                }
                return;
            }
            boolean z2 = this.cursorInBoundsOfNode;
            if (z2 && z2) {
                if (!z) {
                    final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                    TraversableNodeKt.traverseDescendants(this, new Function1() { // from class: androidx.compose.ui.input.pointer.HoverIconModifierNode$findDescendantNodeWithCursorInBounds$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        /* JADX WARN: Type inference failed for: r3v1, types: [T, androidx.compose.ui.input.pointer.HoverIconModifierNode] */
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            ?? r3 = (HoverIconModifierNode) obj;
                            TraversableNode$Companion$TraverseDescendantsAction traversableNode$Companion$TraverseDescendantsAction = TraversableNode$Companion$TraverseDescendantsAction.ContinueTraversal;
                            if (r3.cursorInBoundsOfNode) {
                                ref$ObjectRef.element = r3;
                                if (r3.overrideDescendants) {
                                    return TraversableNode$Companion$TraverseDescendantsAction.SkipSubtreeAndContinueTraversal;
                                }
                            }
                            return traversableNode$Companion$TraverseDescendantsAction;
                        }
                    });
                    HoverIconModifierNode hoverIconModifierNode = (HoverIconModifierNode) ref$ObjectRef.element;
                    if (hoverIconModifierNode != null) {
                        this = hoverIconModifierNode;
                    }
                }
                this.displayIcon();
            }
        }
    }

    public HoverIconModifierNode(PointerIcon pointerIcon, boolean z, DpTouchBoundsExpansion dpTouchBoundsExpansion) {
        this.dpTouchBoundsExpansion = dpTouchBoundsExpansion;
        this.icon = pointerIcon;
        this.overrideDescendants = z;
    }
}
