package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.BeyondBoundsLayout;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TwoDimensionalFocusSearchKt {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FocusStateImpl.values().length];
            try {
                iArr[FocusStateImpl.ActiveParent.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FocusStateImpl.Active.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FocusStateImpl.Captured.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FocusStateImpl.Inactive.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0053, code lost:
    
        r4.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0056, code lost:
    
        if (r21 != r6) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0059, code lost:
    
        r4.getClass();
        r15 = androidx.compose.ui.focus.FocusDirection.Right;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x005e, code lost:
    
        if (r21 != r15) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0061, code lost:
    
        r4.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0064, code lost:
    
        if (r21 != r6) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0066, code lost:
    
        r1 = r0 - r19.right;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x006a, code lost:
    
        r16 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0088, code lost:
    
        if (r1 >= 0.0f) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x008a, code lost:
    
        r1 = 0.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x008b, code lost:
    
        r4.getClass();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x008e, code lost:
    
        if (r21 != r6) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0090, code lost:
    
        r0 = r0 - r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00a8, code lost:
    
        if (r0 >= 1.0f) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00aa, code lost:
    
        r0 = 1.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ad, code lost:
    
        if (r1 >= r0) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00af, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b0, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0092, code lost:
    
        if (r21 != r15) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0094, code lost:
    
        r0 = r2 - r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0099, code lost:
    
        if (r21 != androidx.compose.ui.focus.FocusDirection.Up) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x009b, code lost:
    
        r0 = r13 - r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00a0, code lost:
    
        if (r21 != androidx.compose.ui.focus.FocusDirection.Down) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a2, code lost:
    
        r0 = r10 - r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b6, code lost:
    
        throw new java.lang.IllegalStateException("This function should only be used for 2-D focus search");
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x006d, code lost:
    
        if (r21 != r15) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x006f, code lost:
    
        r1 = r19.left - r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0073, code lost:
    
        r16 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0077, code lost:
    
        if (r21 != androidx.compose.ui.focus.FocusDirection.Up) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0079, code lost:
    
        r1 = r13 - r19.bottom;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0080, code lost:
    
        if (r21 != androidx.compose.ui.focus.FocusDirection.Down) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0082, code lost:
    
        r1 = r19.top - r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00bc, code lost:
    
        throw new java.lang.IllegalStateException("This function should only be used for 2-D focus search");
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0060, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x003f, code lost:
    
        if (r14 <= r11) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0048, code lost:
    
        if (r13 >= r10) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0051, code lost:
    
        if (r12 <= r9) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0036, code lost:
    
        if (r0 >= r2) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x00bd, code lost:
    
        return true;
     */
    /* renamed from: beamBeats-I7lrPNg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean m384beamBeatsI7lrPNg(androidx.compose.ui.geometry.Rect r18, androidx.compose.ui.geometry.Rect r19, androidx.compose.ui.geometry.Rect r20, int r21) {
        /*
            Method dump skipped, instructions count: 197
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.TwoDimensionalFocusSearchKt.m384beamBeatsI7lrPNg(androidx.compose.ui.geometry.Rect, androidx.compose.ui.geometry.Rect, androidx.compose.ui.geometry.Rect, int):boolean");
    }

    public static final boolean beamBeats_I7lrPNg$inSourceBeam(int i, Rect rect, Rect rect2) {
        FocusDirection.Companion companion = FocusDirection.Companion;
        companion.getClass();
        if (i != FocusDirection.Left) {
            companion.getClass();
            if (i != FocusDirection.Right) {
                companion.getClass();
                if (i != FocusDirection.Up) {
                    companion.getClass();
                    if (i != FocusDirection.Down) {
                        throw new IllegalStateException("This function should only be used for 2-D focus search");
                    }
                }
                return rect.right > rect2.left && rect.left < rect2.right;
            }
        }
        return rect.bottom > rect2.top && rect.top < rect2.bottom;
    }

    public static final void collectAccessibleChildren(FocusTargetNode focusTargetNode, MutableVector mutableVector) {
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
        }
        MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node = focusTargetNode.node;
        Modifier.Node node2 = node.child;
        if (node2 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node);
        } else {
            mutableVector2.add(node2);
        }
        while (true) {
            int i = mutableVector2.size;
            if (i == 0) {
                return;
            }
            Modifier.Node node3 = (Modifier.Node) mutableVector2.removeAt(i - 1);
            if ((node3.aggregateChildKindSet & 1024) == 0) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node3);
            } else {
                while (true) {
                    if (node3 == null) {
                        break;
                    }
                    if ((node3.kindSet & 1024) != 0) {
                        MutableVector mutableVector3 = null;
                        while (node3 != null) {
                            if (node3 instanceof FocusTargetNode) {
                                FocusTargetNode focusTargetNode2 = (FocusTargetNode) node3;
                                if (focusTargetNode2.isAttached && !DelegatableNodeKt.requireLayoutNode(focusTargetNode2).isDeactivated) {
                                    if (focusTargetNode2.fetchFocusProperties$ui_release().canFocus) {
                                        mutableVector.add(focusTargetNode2);
                                    } else {
                                        collectAccessibleChildren(focusTargetNode2, mutableVector);
                                    }
                                }
                            } else if ((node3.kindSet & 1024) != 0 && (node3 instanceof DelegatingNode)) {
                                int i2 = 0;
                                for (Modifier.Node node4 = ((DelegatingNode) node3).delegate; node4 != null; node4 = node4.child) {
                                    if ((node4.kindSet & 1024) != 0) {
                                        i2++;
                                        if (i2 == 1) {
                                            node3 = node4;
                                        } else {
                                            if (mutableVector3 == null) {
                                                mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (node3 != null) {
                                                mutableVector3.add(node3);
                                                node3 = null;
                                            }
                                            mutableVector3.add(node4);
                                        }
                                    }
                                }
                                if (i2 == 1) {
                                }
                            }
                            node3 = DelegatableNodeKt.access$pop(mutableVector3);
                        }
                    } else {
                        node3 = node3.child;
                    }
                }
            }
        }
    }

    /* renamed from: findBestCandidate-4WY_MpI, reason: not valid java name */
    public static final FocusTargetNode m385findBestCandidate4WY_MpI(MutableVector mutableVector, Rect rect, int i) {
        Rect translate;
        FocusDirection.Companion.getClass();
        if (i == FocusDirection.Left) {
            translate = rect.translate((rect.right - rect.left) + 1, 0.0f);
        } else if (i == FocusDirection.Right) {
            translate = rect.translate(-((rect.right - rect.left) + 1), 0.0f);
        } else if (i == FocusDirection.Up) {
            translate = rect.translate(0.0f, (rect.bottom - rect.top) + 1);
        } else {
            if (i != FocusDirection.Down) {
                throw new IllegalStateException("This function should only be used for 2-D focus search");
            }
            translate = rect.translate(0.0f, -((rect.bottom - rect.top) + 1));
        }
        Object[] objArr = mutableVector.content;
        int i2 = mutableVector.size;
        FocusTargetNode focusTargetNode = null;
        for (int i3 = 0; i3 < i2; i3++) {
            FocusTargetNode focusTargetNode2 = (FocusTargetNode) objArr[i3];
            if (FocusTraversalKt.isEligibleForFocusSearch(focusTargetNode2)) {
                Rect focusRect = FocusTraversalKt.focusRect(focusTargetNode2);
                if (m388isBetterCandidateI7lrPNg(focusRect, translate, rect, i)) {
                    focusTargetNode = focusTargetNode2;
                    translate = focusRect;
                }
            }
        }
        return focusTargetNode;
    }

    /* renamed from: findChildCorrespondingToFocusEnter--OM-vw8, reason: not valid java name */
    public static final boolean m386findChildCorrespondingToFocusEnterOMvw8(FocusTargetNode focusTargetNode, int i, Function1 function1) {
        Rect rect;
        MutableVector mutableVector = new MutableVector(new FocusTargetNode[16], 0);
        collectAccessibleChildren(focusTargetNode, mutableVector);
        int i2 = mutableVector.size;
        if (i2 <= 1) {
            FocusTargetNode focusTargetNode2 = (FocusTargetNode) (i2 == 0 ? null : mutableVector.content[0]);
            if (focusTargetNode2 != null) {
                return ((Boolean) function1.mo779invoke(focusTargetNode2)).booleanValue();
            }
        } else {
            FocusDirection.Companion.getClass();
            if (i == FocusDirection.Enter) {
                i = FocusDirection.Right;
            }
            if (i == FocusDirection.Right || i == FocusDirection.Down) {
                Rect focusRect = FocusTraversalKt.focusRect(focusTargetNode);
                float f = focusRect.left;
                float f2 = focusRect.top;
                rect = new Rect(f, f2, f, f2);
            } else {
                if (i != FocusDirection.Left && i != FocusDirection.Up) {
                    throw new IllegalStateException("This function should only be used for 2-D focus search");
                }
                Rect focusRect2 = FocusTraversalKt.focusRect(focusTargetNode);
                float f3 = focusRect2.right;
                float f4 = focusRect2.bottom;
                rect = new Rect(f3, f4, f3, f4);
            }
            FocusTargetNode m385findBestCandidate4WY_MpI = m385findBestCandidate4WY_MpI(mutableVector, rect, i);
            if (m385findBestCandidate4WY_MpI != null) {
                return ((Boolean) function1.mo779invoke(m385findBestCandidate4WY_MpI)).booleanValue();
            }
        }
        return false;
    }

    /* renamed from: generateAndSearchChildren-4C6V_qg, reason: not valid java name */
    public static final boolean m387generateAndSearchChildren4C6V_qg(final int i, final FocusTargetNode focusTargetNode, final Rect rect, final Function1 function1) {
        if (m389searchChildren4C6V_qg(i, focusTargetNode, rect, function1)) {
            return true;
        }
        final FocusTransactionManager requireTransactionManager = FocusTargetNodeKt.requireTransactionManager(focusTargetNode);
        final int i2 = requireTransactionManager.generation;
        final FocusTargetNode focusTargetNode2 = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.activeFocusTargetNode;
        Boolean bool = (Boolean) BeyondBoundsLayoutKt.m365searchBeyondBoundsOMvw8(focusTargetNode, i, new Function1() { // from class: androidx.compose.ui.focus.TwoDimensionalFocusSearchKt$generateAndSearchChildren$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                BeyondBoundsLayout.BeyondBoundsScope beyondBoundsScope = (BeyondBoundsLayout.BeyondBoundsScope) obj;
                if (i2 != requireTransactionManager.generation || (ComposeUiFlags.isTrackFocusEnabled && focusTargetNode2 != ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNode)).focusOwner.activeFocusTargetNode)) {
                    return Boolean.TRUE;
                }
                boolean m389searchChildren4C6V_qg = TwoDimensionalFocusSearchKt.m389searchChildren4C6V_qg(i, focusTargetNode, rect, function1);
                Boolean valueOf = Boolean.valueOf(m389searchChildren4C6V_qg);
                if (m389searchChildren4C6V_qg || !beyondBoundsScope.getHasMoreContent()) {
                    return valueOf;
                }
                return null;
            }
        });
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    /* renamed from: isBetterCandidate-I7lrPNg, reason: not valid java name */
    public static final boolean m388isBetterCandidateI7lrPNg(Rect rect, Rect rect2, Rect rect3, int i) {
        if (!isBetterCandidate_I7lrPNg$isCandidate(i, rect, rect3)) {
            return false;
        }
        if (isBetterCandidate_I7lrPNg$isCandidate(i, rect2, rect3) && !m384beamBeatsI7lrPNg(rect3, rect, rect2, i)) {
            return !m384beamBeatsI7lrPNg(rect3, rect2, rect, i) && isBetterCandidate_I7lrPNg$weightedDistance(i, rect3, rect) < isBetterCandidate_I7lrPNg$weightedDistance(i, rect3, rect2);
        }
        return true;
    }

    public static final boolean isBetterCandidate_I7lrPNg$isCandidate(int i, Rect rect, Rect rect2) {
        FocusDirection.Companion companion = FocusDirection.Companion;
        companion.getClass();
        if (i == FocusDirection.Left) {
            float f = rect2.right;
            float f2 = rect.right;
            float f3 = rect2.left;
            return (f > f2 || f3 >= f2) && f3 > rect.left;
        }
        companion.getClass();
        if (i == FocusDirection.Right) {
            float f4 = rect2.left;
            float f5 = rect.left;
            float f6 = rect2.right;
            return (f4 < f5 || f6 <= f5) && f6 < rect.right;
        }
        companion.getClass();
        if (i == FocusDirection.Up) {
            float f7 = rect2.bottom;
            float f8 = rect.bottom;
            float f9 = rect2.top;
            return (f7 > f8 || f9 >= f8) && f9 > rect.top;
        }
        companion.getClass();
        if (i != FocusDirection.Down) {
            throw new IllegalStateException("This function should only be used for 2-D focus search");
        }
        float f10 = rect2.top;
        float f11 = rect.top;
        float f12 = rect2.bottom;
        return (f10 < f11 || f12 <= f11) && f12 < rect.bottom;
    }

    public static final long isBetterCandidate_I7lrPNg$weightedDistance(int i, Rect rect, Rect rect2) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        FocusDirection.Companion companion = FocusDirection.Companion;
        companion.getClass();
        int i2 = FocusDirection.Left;
        if (i == i2) {
            f = rect.left;
            f2 = rect2.right;
        } else if (i == FocusDirection.Right) {
            f = rect2.left;
            f2 = rect.right;
        } else if (i == FocusDirection.Up) {
            f = rect.top;
            f2 = rect2.bottom;
        } else {
            if (i != FocusDirection.Down) {
                throw new IllegalStateException("This function should only be used for 2-D focus search");
            }
            f = rect2.top;
            f2 = rect.bottom;
        }
        float f7 = f - f2;
        if (f7 < 0.0f) {
            f7 = 0.0f;
        }
        long j = (long) f7;
        companion.getClass();
        if (i == i2 || i == FocusDirection.Right) {
            float f8 = rect.bottom;
            float f9 = rect.top;
            f3 = 2;
            f4 = ((f8 - f9) / f3) + f9;
            f5 = rect2.bottom;
            f6 = rect2.top;
        } else {
            if (i != FocusDirection.Up && i != FocusDirection.Down) {
                throw new IllegalStateException("This function should only be used for 2-D focus search");
            }
            float f10 = rect.right;
            float f11 = rect.left;
            f3 = 2;
            f4 = ((f10 - f11) / f3) + f11;
            f5 = rect2.right;
            f6 = rect2.left;
        }
        long j2 = (long) (f4 - (((f5 - f6) / f3) + f6));
        return (j2 * j2) + (13 * j * j);
    }

    /* renamed from: searchChildren-4C6V_qg, reason: not valid java name */
    public static final boolean m389searchChildren4C6V_qg(int i, FocusTargetNode focusTargetNode, Rect rect, Function1 function1) {
        FocusTargetNode m385findBestCandidate4WY_MpI;
        MutableVector mutableVector = new MutableVector(new FocusTargetNode[16], 0);
        if (!focusTargetNode.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitChildren called on an unattached node");
        }
        MutableVector mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node = focusTargetNode.node;
        Modifier.Node node2 = node.child;
        if (node2 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node);
        } else {
            mutableVector2.add(node2);
        }
        while (true) {
            int i2 = mutableVector2.size;
            if (i2 == 0) {
                break;
            }
            Modifier.Node node3 = (Modifier.Node) mutableVector2.removeAt(i2 - 1);
            if ((node3.aggregateChildKindSet & 1024) == 0) {
                DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector2, node3);
            } else {
                while (true) {
                    if (node3 == null) {
                        break;
                    }
                    if ((node3.kindSet & 1024) != 0) {
                        MutableVector mutableVector3 = null;
                        while (node3 != null) {
                            if (node3 instanceof FocusTargetNode) {
                                FocusTargetNode focusTargetNode2 = (FocusTargetNode) node3;
                                if (focusTargetNode2.isAttached) {
                                    mutableVector.add(focusTargetNode2);
                                }
                            } else if ((node3.kindSet & 1024) != 0 && (node3 instanceof DelegatingNode)) {
                                int i3 = 0;
                                for (Modifier.Node node4 = ((DelegatingNode) node3).delegate; node4 != null; node4 = node4.child) {
                                    if ((node4.kindSet & 1024) != 0) {
                                        i3++;
                                        if (i3 == 1) {
                                            node3 = node4;
                                        } else {
                                            if (mutableVector3 == null) {
                                                mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (node3 != null) {
                                                mutableVector3.add(node3);
                                                node3 = null;
                                            }
                                            mutableVector3.add(node4);
                                        }
                                    }
                                }
                                if (i3 == 1) {
                                }
                            }
                            node3 = DelegatableNodeKt.access$pop(mutableVector3);
                        }
                    } else {
                        node3 = node3.child;
                    }
                }
            }
        }
        while (mutableVector.size != 0 && (m385findBestCandidate4WY_MpI = m385findBestCandidate4WY_MpI(mutableVector, rect, i)) != null) {
            if (m385findBestCandidate4WY_MpI.fetchFocusProperties$ui_release().canFocus) {
                return ((Boolean) function1.mo779invoke(m385findBestCandidate4WY_MpI)).booleanValue();
            }
            if (m387generateAndSearchChildren4C6V_qg(i, m385findBestCandidate4WY_MpI, rect, function1)) {
                return true;
            }
            mutableVector.remove(m385findBestCandidate4WY_MpI);
        }
        return false;
    }

    /* renamed from: twoDimensionalFocusSearch-sMXa3k8, reason: not valid java name */
    public static final Boolean m390twoDimensionalFocusSearchsMXa3k8(int i, FocusTargetNode focusTargetNode, Rect rect, Function1 function1) {
        FocusStateImpl focusState = focusTargetNode.getFocusState();
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        int i2 = iArr[focusState.ordinal()];
        if (i2 != 1) {
            if (i2 == 2 || i2 == 3) {
                return Boolean.valueOf(m386findChildCorrespondingToFocusEnterOMvw8(focusTargetNode, i, function1));
            }
            if (i2 == 4) {
                return focusTargetNode.fetchFocusProperties$ui_release().canFocus ? (Boolean) ((FocusOwnerImpl$focusSearch$1) function1).mo779invoke(focusTargetNode) : rect == null ? Boolean.valueOf(m386findChildCorrespondingToFocusEnterOMvw8(focusTargetNode, i, function1)) : Boolean.valueOf(m389searchChildren4C6V_qg(i, focusTargetNode, rect, function1));
            }
            throw new NoWhenBranchMatchedException();
        }
        FocusTargetNode activeChild = FocusTraversalKt.getActiveChild(focusTargetNode);
        if (activeChild == null) {
            throw new IllegalStateException("ActiveParent must have a focusedChild");
        }
        int i3 = iArr[activeChild.getFocusState().ordinal()];
        if (i3 != 1) {
            if (i3 == 2 || i3 == 3) {
                if (rect == null) {
                    rect = FocusTraversalKt.focusRect(activeChild);
                }
                return Boolean.valueOf(m387generateAndSearchChildren4C6V_qg(i, focusTargetNode, rect, function1));
            }
            if (i3 != 4) {
                throw new NoWhenBranchMatchedException();
            }
            throw new IllegalStateException("ActiveParent must have a focusedChild");
        }
        Boolean m390twoDimensionalFocusSearchsMXa3k8 = m390twoDimensionalFocusSearchsMXa3k8(i, activeChild, rect, function1);
        if (!Intrinsics.areEqual(m390twoDimensionalFocusSearchsMXa3k8, Boolean.FALSE)) {
            return m390twoDimensionalFocusSearchsMXa3k8;
        }
        if (rect == null) {
            if (activeChild.getFocusState() != FocusStateImpl.ActiveParent) {
                throw new IllegalStateException("Searching for active node in inactive hierarchy");
            }
            FocusTargetNode findActiveFocusNode = FocusTraversalKt.findActiveFocusNode(activeChild);
            if (findActiveFocusNode == null) {
                throw new IllegalStateException("ActiveParent must have a focusedChild");
            }
            rect = FocusTraversalKt.focusRect(findActiveFocusNode);
        }
        return Boolean.valueOf(m387generateAndSearchChildren4C6V_qg(i, focusTargetNode, rect, function1));
    }
}
