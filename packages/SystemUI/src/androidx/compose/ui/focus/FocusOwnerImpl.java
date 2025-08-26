package androidx.compose.ui.focus;

import android.os.Trace;
import android.view.KeyEvent;
import androidx.collection.MutableLongSet;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusTraversalKt;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.KeyInputModifierNode;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.ArrayList;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public final class FocusOwnerImpl implements FocusOwner {
    public FocusTargetNode activeFocusTargetNode;
    public final FocusInvalidationManager focusInvalidationManager;
    public final FocusTransactionManager focusTransactionManager;
    public MutableLongSet keysCurrentlyDown;
    public final MutableObjectList listeners;
    public final FocusOwnerImpl$modifier$1 modifier;
    public final Function0 onClearFocusForOwner;
    public final Function0 onFocusRectInterop;
    public final Function0 onLayoutDirection;
    public final Function1 onMoveFocusInterop;
    public final Function2 onRequestFocusForOwner;
    public final FocusTargetNode rootFocusNode;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CustomDestinationResult.values().length];
            try {
                iArr[CustomDestinationResult.Redirected.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CustomDestinationResult.Cancelled.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CustomDestinationResult.RedirectCancelled.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CustomDestinationResult.None.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r7v2, types: [androidx.compose.ui.focus.FocusOwnerImpl$modifier$1] */
    public FocusOwnerImpl(Function1 function1, Function2 function2, Function1 function12, Function0 function0, Function0 function02, Function0 function03) {
        this.onRequestFocusForOwner = function2;
        this.onMoveFocusInterop = function12;
        this.onClearFocusForOwner = function0;
        this.onFocusRectInterop = function02;
        this.onLayoutDirection = function03;
        Focusability.Companion.getClass();
        this.rootFocusNode = new FocusTargetNode(Focusability.Never, null, null, 6, null);
        this.focusInvalidationManager = new FocusInvalidationManager(function1, new FocusOwnerImpl$focusInvalidationManager$1(this), new PropertyReference0Impl(this) { // from class: androidx.compose.ui.focus.FocusOwnerImpl$focusInvalidationManager$2
            @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return ((FocusOwnerImpl) this.receiver).rootFocusNode.getFocusState();
            }
        }, new MutablePropertyReference0Impl(this) { // from class: androidx.compose.ui.focus.FocusOwnerImpl$focusInvalidationManager$3
            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return ((FocusOwnerImpl) this.receiver).activeFocusTargetNode;
            }

            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
            public final void set(Object obj) {
                ((FocusOwnerImpl) this.receiver).setActiveFocusTargetNode((FocusTargetNode) obj);
            }
        });
        this.focusTransactionManager = new FocusTransactionManager();
        this.modifier = new ModifierNodeElement<FocusTargetNode>() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$modifier$1
            @Override // androidx.compose.ui.node.ModifierNodeElement
            public final Modifier.Node create() {
                return this.this$0.rootFocusNode;
            }

            public final boolean equals(Object obj) {
                return obj == this;
            }

            public final int hashCode() {
                return this.this$0.rootFocusNode.hashCode();
            }

            @Override // androidx.compose.ui.node.ModifierNodeElement
            public final /* bridge */ /* synthetic */ void update(Modifier.Node node) {
            }
        };
        this.listeners = new MutableObjectList(1);
    }

    public final boolean clearFocus(boolean z) {
        NodeChain nodeChain;
        FocusTargetNode focusTargetNode = this.activeFocusTargetNode;
        if (focusTargetNode != null) {
            setActiveFocusTargetNode(null);
            focusTargetNode.dispatchFocusCallbacks$ui_release(FocusStateImpl.Active, FocusStateImpl.Inactive);
            if (!focusTargetNode.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node node = focusTargetNode.node.parent;
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
            while (layoutNodeRequireLayoutNode != null) {
                if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                    while (node != null) {
                        if ((node.kindSet & 1024) != 0) {
                            Modifier.Node nodeAccess$pop = node;
                            MutableVector mutableVector = null;
                            while (nodeAccess$pop != null) {
                                if (nodeAccess$pop instanceof FocusTargetNode) {
                                    ((FocusTargetNode) nodeAccess$pop).dispatchFocusCallbacks$ui_release(FocusStateImpl.ActiveParent, FocusStateImpl.Inactive);
                                } else if ((nodeAccess$pop.kindSet & 1024) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                    int i = 0;
                                    for (Modifier.Node node2 = ((DelegatingNode) nodeAccess$pop).delegate; node2 != null; node2 = node2.child) {
                                        if ((node2.kindSet & 1024) != 0) {
                                            i++;
                                            if (i == 1) {
                                                nodeAccess$pop = node2;
                                            } else {
                                                if (mutableVector == null) {
                                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (nodeAccess$pop != null) {
                                                    mutableVector.add(nodeAccess$pop);
                                                    nodeAccess$pop = null;
                                                }
                                                mutableVector.add(node2);
                                            }
                                        }
                                    }
                                    if (i == 1) {
                                    }
                                }
                                nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                            }
                        }
                        node = node.parent;
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                node = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x005b A[Catch: all -> 0x003c, TRY_LEAVE, TryCatch #0 {all -> 0x003c, blocks: (B:17:0x0034, B:19:0x0038, B:22:0x003e, B:24:0x0042, B:26:0x0049, B:30:0x005b), top: B:38:0x0034 }] */
    /* renamed from: clearFocus-I7lrPNg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m372clearFocusI7lrPNg(int i, boolean z, boolean z2) {
        boolean z3 = ComposeUiFlags.isTrackFocusEnabled;
        FocusTargetNode focusTargetNode = this.rootFocusNode;
        boolean zClearFocus = false;
        if (z3) {
            if (z) {
                clearFocus(z);
            } else {
                int i2 = WhenMappings.$EnumSwitchMapping$0[FocusTransactionsKt.m381performCustomClearFocusMxy_nc0(focusTargetNode, i).ordinal()];
                if (i2 != 1 && i2 != 2 && i2 != 3) {
                    if (i2 != 4) {
                        throw new NoWhenBranchMatchedException();
                    }
                    clearFocus(z);
                }
            }
            zClearFocus = true;
        } else {
            FocusTransactionManager focusTransactionManager = this.focusTransactionManager;
            FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1 focusOwnerImpl$clearFocus$clearedFocusSuccessfully$1 = new Function0() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            };
            try {
                if (focusTransactionManager.ongoingTransaction) {
                    FocusTransactionManager.access$cancelTransaction(focusTransactionManager);
                }
                focusTransactionManager.ongoingTransaction = true;
                if (focusOwnerImpl$clearFocus$clearedFocusSuccessfully$1 != null) {
                    focusTransactionManager.cancellationListener.add(focusOwnerImpl$clearFocus$clearedFocusSuccessfully$1);
                }
                if (!z) {
                    int i3 = WhenMappings.$EnumSwitchMapping$0[FocusTransactionsKt.m381performCustomClearFocusMxy_nc0(focusTargetNode, i).ordinal()];
                    if (i3 != 1 && i3 != 2 && i3 != 3) {
                        zClearFocus = FocusTransactionsKt.clearFocus(focusTargetNode, z);
                    }
                    FocusTransactionManager.access$commitTransaction(focusTransactionManager);
                }
            } catch (Throwable th) {
                FocusTransactionManager.access$commitTransaction(focusTransactionManager);
                throw th;
            }
        }
        if (zClearFocus && z2) {
            this.onClearFocusForOwner.invoke();
        }
        return zClearFocus;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00e2 A[Catch: all -> 0x0330, TryCatch #0 {all -> 0x0330, blocks: (B:3:0x0008, B:5:0x0011, B:8:0x001c, B:12:0x0026, B:15:0x0032, B:17:0x0038, B:18:0x003e, B:20:0x0046, B:22:0x004b, B:24:0x0051, B:28:0x0057, B:126:0x0169, B:128:0x016f, B:129:0x0172, B:131:0x017d, B:134:0x0189, B:138:0x0193, B:141:0x0199, B:142:0x019e, B:162:0x01d7, B:143:0x01a2, B:145:0x01a8, B:147:0x01ac, B:149:0x01b4, B:151:0x01ba, B:155:0x01c1, B:157:0x01ca, B:158:0x01ce, B:159:0x01d1, B:163:0x01dc, B:164:0x01df, B:166:0x01e5, B:168:0x01e9, B:171:0x01f0, B:173:0x01f8, B:180:0x020f, B:181:0x0211, B:182:0x021f, B:184:0x0223, B:186:0x0227, B:213:0x027c, B:190:0x0233, B:192:0x023c, B:194:0x0240, B:196:0x0247, B:198:0x024d, B:200:0x0250, B:201:0x0253, B:203:0x0259, B:204:0x0260, B:206:0x0268, B:207:0x026d, B:209:0x0273, B:210:0x0276, B:214:0x0287, B:218:0x0297, B:219:0x02a5, B:221:0x02a9, B:223:0x02ad, B:250:0x0302, B:227:0x02b9, B:229:0x02c2, B:231:0x02c6, B:233:0x02cd, B:235:0x02d3, B:237:0x02d6, B:238:0x02d9, B:240:0x02df, B:241:0x02e6, B:243:0x02ee, B:244:0x02f3, B:246:0x02f9, B:247:0x02fc, B:252:0x030f, B:254:0x0316, B:259:0x0328, B:260:0x032a, B:32:0x005f, B:34:0x0065, B:35:0x0068, B:37:0x0070, B:40:0x007c, B:44:0x0086, B:75:0x00d8, B:77:0x00dc, B:47:0x008b, B:49:0x0091, B:51:0x0095, B:53:0x009d, B:55:0x00a3, B:59:0x00aa, B:61:0x00b3, B:62:0x00b7, B:63:0x00ba, B:66:0x00c0, B:67:0x00c5, B:68:0x00c8, B:70:0x00ce, B:72:0x00d2, B:78:0x00e2, B:80:0x00e8, B:81:0x00eb, B:83:0x00f5, B:86:0x0101, B:90:0x010b, B:121:0x015d, B:123:0x0161, B:93:0x0110, B:95:0x0116, B:97:0x011a, B:99:0x0122, B:101:0x0128, B:105:0x012f, B:107:0x0138, B:108:0x013c, B:109:0x013f, B:112:0x0145, B:113:0x014a, B:114:0x014d, B:116:0x0153, B:118:0x0157), top: B:266:0x0008 }] */
    /* JADX WARN: Type inference failed for: r12v23, types: [T, androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r12v28, types: [T, androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r12v37, types: [T, androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r12v40, types: [T, androidx.compose.ui.Modifier$Node, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v46, types: [T, androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r12v49, types: [T, androidx.compose.ui.Modifier$Node, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v65 */
    /* JADX WARN: Type inference failed for: r12v66 */
    /* JADX WARN: Type inference failed for: r12v67 */
    /* JADX WARN: Type inference failed for: r12v68 */
    /* JADX WARN: Type inference failed for: r7v35, types: [T, androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r7v47 */
    /* JADX WARN: Type inference failed for: r7v48 */
    /* JADX WARN: Type inference failed for: r9v17, types: [T, androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r9v31 */
    /* JADX WARN: Type inference failed for: r9v32 */
    /* renamed from: dispatchKeyEvent-YhN2O0w, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m373dispatchKeyEventYhN2O0w(KeyEvent keyEvent, Function0 function0) {
        Object obj;
        Modifier.Node node;
        NodeChain nodeChain;
        Object obj2;
        NodeChain nodeChain2;
        NodeChain nodeChain3;
        FocusTargetNode focusTargetNode = this.rootFocusNode;
        Trace.beginSection("FocusOwnerImpl:dispatchKeyEvent");
        try {
            if (this.focusInvalidationManager.hasPendingInvalidation()) {
                System.out.println((Object) "FocusRelatedWarning: Dispatching key event while focus system is invalidated.");
                return false;
            }
            if (!m376validateKeyEventZmokQxo(keyEvent)) {
                return false;
            }
            FocusTargetNode focusTargetNodeFindActiveFocusNode = FocusTraversalKt.findActiveFocusNode(focusTargetNode);
            if (focusTargetNodeFindActiveFocusNode != null) {
                if (!focusTargetNodeFindActiveFocusNode.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitLocalDescendants called on an unattached node");
                }
                Modifier.Node node2 = focusTargetNodeFindActiveFocusNode.node;
                if ((node2.aggregateChildKindSet & 9216) != 0) {
                    node = null;
                    for (Modifier.Node node3 = node2.child; node3 != null; node3 = node3.child) {
                        int i = node3.kindSet;
                        if ((i & 9216) != 0) {
                            if ((i & 1024) != 0) {
                                break;
                            }
                            node = node3;
                        }
                    }
                } else {
                    node = null;
                }
                if (node == null) {
                }
            } else if (focusTargetNodeFindActiveFocusNode == null) {
                if (!focusTargetNode.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                }
                Modifier.Node node4 = focusTargetNode.node.parent;
                LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
                loop15: while (true) {
                    if (layoutNodeRequireLayoutNode == null) {
                        obj = null;
                        break;
                    }
                    if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 8192) != 0) {
                        while (node4 != null) {
                            if ((node4.kindSet & 8192) != 0) {
                                Modifier.Node nodeAccess$pop = node4;
                                MutableVector mutableVector = null;
                                while (nodeAccess$pop != null) {
                                    if (nodeAccess$pop instanceof KeyInputModifierNode) {
                                        obj = nodeAccess$pop;
                                        break loop15;
                                    }
                                    if ((nodeAccess$pop.kindSet & 8192) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                        Modifier.Node node5 = ((DelegatingNode) nodeAccess$pop).delegate;
                                        int i2 = 0;
                                        nodeAccess$pop = nodeAccess$pop;
                                        mutableVector = mutableVector;
                                        while (node5 != null) {
                                            if ((node5.kindSet & 8192) != 0) {
                                                i2++;
                                                mutableVector = mutableVector;
                                                if (i2 == 1) {
                                                    nodeAccess$pop = node5;
                                                } else {
                                                    if (mutableVector == null) {
                                                        mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodeAccess$pop != null) {
                                                        mutableVector.add(nodeAccess$pop);
                                                        nodeAccess$pop = null;
                                                    }
                                                    mutableVector.add(node5);
                                                }
                                            }
                                            node5 = node5.child;
                                            nodeAccess$pop = nodeAccess$pop;
                                            mutableVector = mutableVector;
                                        }
                                        if (i2 == 1) {
                                        }
                                    }
                                    nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                                }
                            }
                            node4 = node4.parent;
                        }
                    }
                    layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                    node4 = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain.tail;
                }
                Object obj3 = (KeyInputModifierNode) obj;
                node = obj3 != null ? ((Modifier.Node) obj3).node : null;
            } else {
                if (!focusTargetNodeFindActiveFocusNode.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                }
                Modifier.Node node6 = focusTargetNodeFindActiveFocusNode.node;
                LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(focusTargetNodeFindActiveFocusNode);
                loop11: while (true) {
                    if (layoutNodeRequireLayoutNode2 == null) {
                        obj2 = null;
                        break;
                    }
                    if ((layoutNodeRequireLayoutNode2.nodes.head.aggregateChildKindSet & 8192) != 0) {
                        while (node6 != null) {
                            if ((node6.kindSet & 8192) != 0) {
                                MutableVector mutableVector2 = null;
                                Modifier.Node nodeAccess$pop2 = node6;
                                while (nodeAccess$pop2 != null) {
                                    if (nodeAccess$pop2 instanceof KeyInputModifierNode) {
                                        obj2 = nodeAccess$pop2;
                                        break loop11;
                                    }
                                    if ((nodeAccess$pop2.kindSet & 8192) != 0 && (nodeAccess$pop2 instanceof DelegatingNode)) {
                                        Modifier.Node node7 = ((DelegatingNode) nodeAccess$pop2).delegate;
                                        int i3 = 0;
                                        nodeAccess$pop2 = nodeAccess$pop2;
                                        mutableVector2 = mutableVector2;
                                        while (node7 != null) {
                                            if ((node7.kindSet & 8192) != 0) {
                                                i3++;
                                                mutableVector2 = mutableVector2;
                                                if (i3 == 1) {
                                                    nodeAccess$pop2 = node7;
                                                } else {
                                                    if (mutableVector2 == null) {
                                                        mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodeAccess$pop2 != null) {
                                                        mutableVector2.add(nodeAccess$pop2);
                                                        nodeAccess$pop2 = null;
                                                    }
                                                    mutableVector2.add(node7);
                                                }
                                            }
                                            node7 = node7.child;
                                            nodeAccess$pop2 = nodeAccess$pop2;
                                            mutableVector2 = mutableVector2;
                                        }
                                        if (i3 == 1) {
                                        }
                                    }
                                    nodeAccess$pop2 = DelegatableNodeKt.access$pop(mutableVector2);
                                }
                            }
                            node6 = node6.parent;
                        }
                    }
                    layoutNodeRequireLayoutNode2 = layoutNodeRequireLayoutNode2.getParent$ui_release();
                    node6 = (layoutNodeRequireLayoutNode2 == null || (nodeChain2 = layoutNodeRequireLayoutNode2.nodes) == null) ? null : nodeChain2.tail;
                }
                Object obj4 = (KeyInputModifierNode) obj2;
                if (obj4 != null) {
                    node = ((Modifier.Node) obj4).node;
                }
            }
            if (node != null) {
                if (!node.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                }
                Modifier.Node node8 = node.node.parent;
                LayoutNode layoutNodeRequireLayoutNode3 = DelegatableNodeKt.requireLayoutNode(node);
                ArrayList arrayList = null;
                while (layoutNodeRequireLayoutNode3 != null) {
                    if ((layoutNodeRequireLayoutNode3.nodes.head.aggregateChildKindSet & 8192) != 0) {
                        while (node8 != null) {
                            if ((node8.kindSet & 8192) != 0) {
                                Modifier.Node nodeAccess$pop3 = node8;
                                MutableVector mutableVector3 = null;
                                while (nodeAccess$pop3 != null) {
                                    if (nodeAccess$pop3 instanceof KeyInputModifierNode) {
                                        if (arrayList == null) {
                                            arrayList = new ArrayList();
                                        }
                                        arrayList.add(nodeAccess$pop3);
                                    } else if ((nodeAccess$pop3.kindSet & 8192) != 0 && (nodeAccess$pop3 instanceof DelegatingNode)) {
                                        int i4 = 0;
                                        for (Modifier.Node node9 = ((DelegatingNode) nodeAccess$pop3).delegate; node9 != null; node9 = node9.child) {
                                            if ((node9.kindSet & 8192) != 0) {
                                                i4++;
                                                if (i4 == 1) {
                                                    nodeAccess$pop3 = node9;
                                                } else {
                                                    if (mutableVector3 == null) {
                                                        mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (nodeAccess$pop3 != null) {
                                                        mutableVector3.add(nodeAccess$pop3);
                                                        nodeAccess$pop3 = null;
                                                    }
                                                    mutableVector3.add(node9);
                                                }
                                            }
                                        }
                                        if (i4 == 1) {
                                        }
                                    }
                                    nodeAccess$pop3 = DelegatableNodeKt.access$pop(mutableVector3);
                                }
                            }
                            node8 = node8.parent;
                        }
                    }
                    layoutNodeRequireLayoutNode3 = layoutNodeRequireLayoutNode3.getParent$ui_release();
                    node8 = (layoutNodeRequireLayoutNode3 == null || (nodeChain3 = layoutNodeRequireLayoutNode3.nodes) == null) ? null : nodeChain3.tail;
                }
                if (arrayList != null) {
                    int size = arrayList.size() - 1;
                    if (size >= 0) {
                        while (true) {
                            int i5 = size - 1;
                            if (((KeyInputModifierNode) arrayList.get(size)).mo17onPreKeyEventZmokQxo(keyEvent)) {
                                return true;
                            }
                            if (i5 < 0) {
                                break;
                            }
                            size = i5;
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
                ?? r12 = node.node;
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                ref$ObjectRef2.element = r12;
                while (true) {
                    T t = ref$ObjectRef2.element;
                    if (t != 0) {
                        if (t instanceof KeyInputModifierNode) {
                            if (((KeyInputModifierNode) t).mo17onPreKeyEventZmokQxo(keyEvent)) {
                                return true;
                            }
                        } else if ((((Modifier.Node) t).kindSet & 8192) != 0 && (t instanceof DelegatingNode)) {
                            int i6 = 0;
                            for (?? r122 = ((DelegatingNode) t).delegate; r122 != 0; r122 = r122.child) {
                                if ((r122.kindSet & 8192) != 0) {
                                    i6++;
                                    if (i6 == 1) {
                                        ref$ObjectRef2.element = r122;
                                    } else {
                                        MutableVector mutableVector4 = (MutableVector) ref$ObjectRef.element;
                                        ?? mutableVector5 = mutableVector4;
                                        if (mutableVector4 == null) {
                                            mutableVector5 = new MutableVector(new Modifier.Node[16], 0);
                                        }
                                        ref$ObjectRef.element = mutableVector5;
                                        Modifier.Node node10 = (Modifier.Node) ref$ObjectRef2.element;
                                        if (node10 != null) {
                                            mutableVector5.add(node10);
                                            ref$ObjectRef2.element = null;
                                        }
                                        MutableVector mutableVector6 = (MutableVector) ref$ObjectRef.element;
                                        if (mutableVector6 != 0) {
                                            mutableVector6.add(r122);
                                        }
                                    }
                                }
                            }
                            if (i6 == 1) {
                            }
                        }
                        ref$ObjectRef2.element = DelegatableNodeKt.access$pop((MutableVector) ref$ObjectRef.element);
                    } else {
                        if (((Boolean) function0.invoke()).booleanValue()) {
                            return true;
                        }
                        ?? r123 = node.node;
                        Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
                        Ref$ObjectRef ref$ObjectRef4 = new Ref$ObjectRef();
                        ref$ObjectRef4.element = r123;
                        while (true) {
                            T t2 = ref$ObjectRef4.element;
                            if (t2 != 0) {
                                if (t2 instanceof KeyInputModifierNode) {
                                    if (((KeyInputModifierNode) t2).mo15onKeyEventZmokQxo(keyEvent)) {
                                        return true;
                                    }
                                } else if ((((Modifier.Node) t2).kindSet & 8192) != 0 && (t2 instanceof DelegatingNode)) {
                                    int i7 = 0;
                                    for (?? r124 = ((DelegatingNode) t2).delegate; r124 != 0; r124 = r124.child) {
                                        if ((r124.kindSet & 8192) != 0) {
                                            i7++;
                                            if (i7 == 1) {
                                                ref$ObjectRef4.element = r124;
                                            } else {
                                                MutableVector mutableVector7 = (MutableVector) ref$ObjectRef3.element;
                                                ?? mutableVector8 = mutableVector7;
                                                if (mutableVector7 == null) {
                                                    mutableVector8 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                ref$ObjectRef3.element = mutableVector8;
                                                Modifier.Node node11 = (Modifier.Node) ref$ObjectRef4.element;
                                                if (node11 != null) {
                                                    mutableVector8.add(node11);
                                                    ref$ObjectRef4.element = null;
                                                }
                                                MutableVector mutableVector9 = (MutableVector) ref$ObjectRef3.element;
                                                if (mutableVector9 != 0) {
                                                    mutableVector9.add(r124);
                                                }
                                            }
                                        }
                                    }
                                    if (i7 == 1) {
                                    }
                                }
                                ref$ObjectRef4.element = DelegatableNodeKt.access$pop((MutableVector) ref$ObjectRef3.element);
                            } else {
                                if (arrayList != null) {
                                    int size2 = arrayList.size();
                                    for (int i8 = 0; i8 < size2; i8++) {
                                        if (((KeyInputModifierNode) arrayList.get(i8)).mo15onKeyEventZmokQxo(keyEvent)) {
                                            return true;
                                        }
                                    }
                                    Unit unit2 = Unit.INSTANCE;
                                }
                                Unit unit3 = Unit.INSTANCE;
                            }
                        }
                    }
                }
            }
            return false;
        } finally {
            Trace.endSection();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0105  */
    /* JADX WARN: Type inference failed for: r11v11, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r11v3, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v14, types: [androidx.compose.ui.node.TailModifierNode] */
    /* JADX WARN: Type inference failed for: r1v9, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v10, types: [androidx.compose.ui.focus.FocusTargetNode] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r2v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v10, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* renamed from: focusSearch-ULY8qGw, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Boolean m374focusSearchULY8qGw(int i, Rect rect, Function1 function1) {
        Boolean bool;
        boolean zBackwardFocusSearch;
        int i2;
        NodeChain nodeChain;
        FocusRequester focusRequester;
        FocusTargetNode focusTargetNode = this.rootFocusNode;
        FocusTargetNode focusTargetNodeFindActiveFocusNode = FocusTraversalKt.findActiveFocusNode(focusTargetNode);
        Function0 function0 = this.onLayoutDirection;
        if (focusTargetNodeFindActiveFocusNode != null) {
            LayoutDirection layoutDirection = (LayoutDirection) function0.invoke();
            FocusPropertiesImpl focusPropertiesImplFetchFocusProperties$ui_release = focusTargetNodeFindActiveFocusNode.fetchFocusProperties$ui_release();
            FocusDirection.Companion.getClass();
            if (i == FocusDirection.Next) {
                focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.next;
            } else if (i == FocusDirection.Previous) {
                focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.previous;
            } else if (i == FocusDirection.Up) {
                focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.up;
            } else if (i == FocusDirection.Down) {
                focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.down;
            } else if (i == FocusDirection.Left) {
                int i3 = FocusTraversalKt.WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
                if (i3 == 1) {
                    focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.start;
                } else {
                    if (i3 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.end;
                }
                FocusRequester.Companion.getClass();
                if (focusRequester == FocusRequester.Default) {
                    focusRequester = null;
                }
                if (focusRequester == null) {
                    focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.left;
                }
            } else {
                if (i != FocusDirection.Right) {
                    int i4 = FocusDirection.Enter;
                    if (i != i4 && i != FocusDirection.Exit) {
                        throw new IllegalStateException("invalid FocusDirection");
                    }
                    CancelIndicatingFocusBoundaryScope cancelIndicatingFocusBoundaryScope = new CancelIndicatingFocusBoundaryScope(i, null);
                    FocusTransactionManager focusTransactionManager = FocusTargetNodeKt.getFocusTransactionManager(focusTargetNodeFindActiveFocusNode);
                    int i5 = focusTransactionManager != null ? focusTransactionManager.generation : 0;
                    bool = null;
                    FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) DelegatableNodeKt.requireOwner(focusTargetNodeFindActiveFocusNode)).focusOwner;
                    FocusTargetNode focusTargetNode2 = focusOwnerImpl.activeFocusTargetNode;
                    if (i == i4) {
                        focusPropertiesImplFetchFocusProperties$ui_release.onEnter.mo781invoke(cancelIndicatingFocusBoundaryScope);
                    } else {
                        focusPropertiesImplFetchFocusProperties$ui_release.onExit.mo781invoke(cancelIndicatingFocusBoundaryScope);
                    }
                    int i6 = focusTransactionManager != null ? focusTransactionManager.generation : 0;
                    if (cancelIndicatingFocusBoundaryScope.isCanceled) {
                        FocusRequester.Companion.getClass();
                        focusRequester = FocusRequester.Cancel;
                    } else if (i5 != i6 || (ComposeUiFlags.isTrackFocusEnabled && focusTargetNode2 != focusOwnerImpl.activeFocusTargetNode)) {
                        FocusRequester.Companion.getClass();
                        focusRequester = FocusRequester.Redirect;
                    } else {
                        FocusRequester.Companion.getClass();
                        focusRequester = FocusRequester.Default;
                    }
                    FocusRequester.Companion.getClass();
                    if (!Intrinsics.areEqual(focusRequester, FocusRequester.Cancel)) {
                        if (Intrinsics.areEqual(focusRequester, FocusRequester.Redirect)) {
                            FocusTargetNode focusTargetNodeFindActiveFocusNode2 = FocusTraversalKt.findActiveFocusNode(focusTargetNode);
                            if (focusTargetNodeFindActiveFocusNode2 != null) {
                                return (Boolean) function1.mo781invoke(focusTargetNodeFindActiveFocusNode2);
                            }
                        } else if (!Intrinsics.areEqual(focusRequester, FocusRequester.Default)) {
                            return Boolean.valueOf(focusRequester.findFocusTargetNode$ui_release(function1));
                        }
                    }
                    return bool;
                }
                int i7 = FocusTraversalKt.WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
                if (i7 == 1) {
                    focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.end;
                } else {
                    if (i7 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.start;
                }
                FocusRequester.Companion.getClass();
                if (focusRequester == FocusRequester.Default) {
                    focusRequester = null;
                }
                if (focusRequester == null) {
                    focusRequester = focusPropertiesImplFetchFocusProperties$ui_release.right;
                }
            }
            bool = null;
            FocusRequester.Companion.getClass();
            if (!Intrinsics.areEqual(focusRequester, FocusRequester.Cancel)) {
            }
            return bool;
        }
        bool = null;
        focusTargetNodeFindActiveFocusNode = null;
        LayoutDirection layoutDirection2 = (LayoutDirection) function0.invoke();
        FocusOwnerImpl$focusSearch$1 focusOwnerImpl$focusSearch$1 = new FocusOwnerImpl$focusSearch$1(focusTargetNodeFindActiveFocusNode, this, function1);
        FocusDirection.Companion.getClass();
        int i8 = FocusDirection.Next;
        if (i == i8 || i == FocusDirection.Previous) {
            if (i == i8) {
                zBackwardFocusSearch = OneDimensionalFocusSearchKt.forwardFocusSearch(focusTargetNode, focusOwnerImpl$focusSearch$1);
            } else {
                if (i != FocusDirection.Previous) {
                    throw new IllegalStateException("This function should only be used for 1-D focus search");
                }
                zBackwardFocusSearch = OneDimensionalFocusSearchKt.backwardFocusSearch(focusTargetNode, focusOwnerImpl$focusSearch$1);
            }
            return Boolean.valueOf(zBackwardFocusSearch);
        }
        int i9 = FocusDirection.Left;
        if (i == i9 || i == (i2 = FocusDirection.Right) || i == FocusDirection.Up || i == FocusDirection.Down) {
            return TwoDimensionalFocusSearchKt.m392twoDimensionalFocusSearchsMXa3k8(i, focusTargetNode, rect, focusOwnerImpl$focusSearch$1);
        }
        if (i == FocusDirection.Enter) {
            int i10 = FocusTraversalKt.WhenMappings.$EnumSwitchMapping$0[layoutDirection2.ordinal()];
            if (i10 == 1) {
                i9 = i2;
            } else if (i10 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            FocusTargetNode focusTargetNodeFindActiveFocusNode3 = FocusTraversalKt.findActiveFocusNode(focusTargetNode);
            if (focusTargetNodeFindActiveFocusNode3 != null) {
                return TwoDimensionalFocusSearchKt.m392twoDimensionalFocusSearchsMXa3k8(i9, focusTargetNodeFindActiveFocusNode3, rect, focusOwnerImpl$focusSearch$1);
            }
            return bool;
        }
        if (i != FocusDirection.Exit) {
            throw new IllegalStateException(("Focus search invoked with invalid FocusDirection " + ((Object) FocusDirection.m369toStringimpl(i))).toString());
        }
        FocusTargetNode focusTargetNodeFindActiveFocusNode4 = FocusTraversalKt.findActiveFocusNode(focusTargetNode);
        if (focusTargetNodeFindActiveFocusNode4 != null) {
            if (!focusTargetNodeFindActiveFocusNode4.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            ?? r1 = focusTargetNodeFindActiveFocusNode4.node.parent;
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNodeFindActiveFocusNode4);
            loop0: while (true) {
                if (layoutNodeRequireLayoutNode == null) {
                    break;
                }
                if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                    for (Modifier.Node node = r1; node != null; node = node.parent) {
                        if ((node.kindSet & 1024) != 0) {
                            DelegatingNode delegatingNodeAccess$pop = node;
                            ?? mutableVector = bool;
                            while (delegatingNodeAccess$pop != 0) {
                                if (delegatingNodeAccess$pop instanceof FocusTargetNode) {
                                    ?? r2 = (FocusTargetNode) delegatingNodeAccess$pop;
                                    if (r2.fetchFocusProperties$ui_release().canFocus) {
                                        bool = r2;
                                        break loop0;
                                    }
                                } else if ((delegatingNodeAccess$pop.kindSet & 1024) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                    Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                                    int i11 = 0;
                                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                    mutableVector = mutableVector;
                                    while (node2 != null) {
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        if ((node2.kindSet & 1024) != 0) {
                                            i11++;
                                            if (i11 == 1) {
                                                delegatingNodeAccess$pop = node2;
                                            } else {
                                                mutableVector = mutableVector == 0 ? new MutableVector(new Modifier.Node[16], 0) : mutableVector;
                                                if (delegatingNodeAccess$pop != 0) {
                                                    mutableVector.add(delegatingNodeAccess$pop);
                                                    delegatingNodeAccess$pop = bool;
                                                }
                                                mutableVector.add(node2);
                                            }
                                        }
                                        node2 = node2.child;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector = mutableVector;
                                    }
                                    if (i11 != 1) {
                                        delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                                    }
                                }
                                delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                            }
                        }
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                r1 = (layoutNodeRequireLayoutNode == null || (nodeChain = layoutNodeRequireLayoutNode.nodes) == null) ? bool : nodeChain.tail;
            }
        }
        Boolean bool2 = bool;
        return Boolean.valueOf((bool2 == null || bool2.equals(focusTargetNode)) ? false : ((Boolean) focusOwnerImpl$focusSearch$1.mo781invoke(bool2)).booleanValue());
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [T, java.lang.Boolean] */
    /* renamed from: moveFocus-3ESFkO8, reason: not valid java name */
    public final boolean m375moveFocus3ESFkO8(final int i) {
        boolean z = ComposeUiFlags.isViewFocusFixEnabled;
        Function1 function1 = this.onMoveFocusInterop;
        if (z && ((Boolean) function1.mo781invoke(FocusDirection.m368boximpl(i))).booleanValue()) {
            return true;
        }
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = Boolean.FALSE;
        FocusTransactionManager focusTransactionManager = this.focusTransactionManager;
        int i2 = focusTransactionManager.generation;
        FocusTargetNode focusTargetNode = this.activeFocusTargetNode;
        Boolean boolM374focusSearchULY8qGw = m374focusSearchULY8qGw(i, (Rect) this.onFocusRectInterop.invoke(), new Function1() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$moveFocus$focusSearchSuccess$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Type inference failed for: r3v3, types: [T, java.lang.Boolean] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ref$ObjectRef.element = Boolean.valueOf(((FocusTargetNode) obj).m380requestFocus3ESFkO8(i));
                Boolean bool = ref$ObjectRef.element;
                return Boolean.valueOf(bool != null ? bool.booleanValue() : false);
            }
        });
        int i3 = focusTransactionManager.generation;
        Boolean bool = Boolean.TRUE;
        if (Intrinsics.areEqual(boolM374focusSearchULY8qGw, bool)) {
            if (i2 != i3) {
                return true;
            }
            if (ComposeUiFlags.isTrackFocusEnabled && focusTargetNode != this.activeFocusTargetNode) {
                return true;
            }
        }
        if (boolM374focusSearchULY8qGw != null && ref$ObjectRef.element != 0) {
            if (boolM374focusSearchULY8qGw.equals(bool) && Intrinsics.areEqual(ref$ObjectRef.element, bool)) {
                return true;
            }
            if (FocusOwnerImplKt.m377is1dFocusSearch3ESFkO8(i)) {
                if (m372clearFocusI7lrPNg(i, false, false)) {
                    Boolean boolM374focusSearchULY8qGw2 = m374focusSearchULY8qGw(i, null, new Function1() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$takeFocus$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            return Boolean.valueOf(((FocusTargetNode) obj).m380requestFocus3ESFkO8(i));
                        }
                    });
                    if (boolM374focusSearchULY8qGw2 != null ? boolM374focusSearchULY8qGw2.booleanValue() : false) {
                        return true;
                    }
                }
            } else if (!z && ((Boolean) function1.mo781invoke(FocusDirection.m368boximpl(i))).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public final void setActiveFocusTargetNode(FocusTargetNode focusTargetNode) {
        FocusTargetNode focusTargetNode2 = this.activeFocusTargetNode;
        this.activeFocusTargetNode = focusTargetNode;
        boolean z = ComposeUiFlags.isRectTrackingEnabled;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00a9, code lost:
    
        if (((r3 & ((~r3) << 6)) & (-9187201950435737472L)) == 0) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00ab, code lost:
    
        r0 = r8.findFirstAvailableSlot(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00b1, code lost:
    
        if (r8.growthLimit != 0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00c2, code lost:
    
        if (((r8.metadata[r0 >> 3] >> ((r0 & 7) << 3)) & 255) != 254) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00c4, code lost:
    
        r34 = 1;
        r39 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00ca, code lost:
    
        r0 = r8._capacity;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00cc, code lost:
    
        if (r0 <= 8) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00ce, code lost:
    
        r11 = r8._size;
        r1 = kotlin.ULong.$r8$clinit;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00de, code lost:
    
        if (java.lang.Long.compareUnsigned(r11 * 32, r0 * 25) > 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00e0, code lost:
    
        r0 = r8.metadata;
        r1 = r8._capacity;
        r11 = r8.elements;
        r12 = (r1 + 7) >> 3;
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00ec, code lost:
    
        if (r14 >= r12) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00ee, code lost:
    
        r3 = r0[r14] & (-9187201950435737472L);
        r15 = r14;
        r0[r15] = (-72340172838076674L) & ((~r3) + (r3 >>> r32));
        r14 = r15 + 1;
        r13 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0108, code lost:
    
        r39 = 128;
        r3 = r0.length;
        r4 = r3 - 1;
        r3 = r3 - 2;
        r14 = 72057594037927935L;
        r0[r3] = (r0[r3] & 72057594037927935L) | (-72057594037927936L);
        r0[r4] = r0[0];
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0125, code lost:
    
        if (r3 == r1) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0127, code lost:
    
        r4 = r3 >> 3;
        r16 = (r3 & 7) << 3;
        r12 = (r0[r4] >> r16) & 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0135, code lost:
    
        if (r12 != 128) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0137, code lost:
    
        r3 = r3 + r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x013b, code lost:
    
        if (r12 == 254) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x013e, code lost:
    
        r12 = java.lang.Long.hashCode(r11[r3]) * (-862048943);
        r13 = (r12 ^ (r12 << 16)) >>> 7;
        r19 = r8.findFirstAvailableSlot(r13);
        r13 = r13 & r1;
        r21 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x015f, code lost:
    
        if ((((r19 - r13) & r1) / 8) != (((r3 - r13) & r1) / 8)) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0161, code lost:
    
        r34 = r2;
        r0[r4] = ((~(255 << r16)) & r0[r4]) | ((r12 & 127) << r16);
        r0[r0.length - 1] = (r0[0] & r21) | Long.MIN_VALUE;
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0180, code lost:
    
        r14 = r21;
        r2 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0185, code lost:
    
        r34 = r2;
        r20 = r3;
        r2 = r19 >> 3;
        r13 = r0[r2];
        r3 = (r19 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0197, code lost:
    
        if (((r13 >> r3) & 255) != 128) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0199, code lost:
    
        r15 = r1;
        r0[r2] = ((~(255 << r3)) & r13) | ((r12 & 127) << r3);
        r0[r4] = (r0[r4] & (~(255 << r16))) | (128 << r16);
        r11[r19] = r11[r20];
        r11[r20] = 0;
        r3 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01be, code lost:
    
        r15 = r1;
        r0[r2] = ((~(255 << r3)) & r13) | ((r12 & 127) << r3);
        r1 = r11[r19];
        r11[r19] = r11[r20];
        r11[r20] = r1;
        r3 = r20 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01d9, code lost:
    
        r0[r0.length - 1] = (r0[0] & r21) | Long.MIN_VALUE;
        r3 = r3 + 1;
        r1 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01e8, code lost:
    
        r34 = r2;
        r8.growthLimit = androidx.collection.ScatterMapKt.loadedCapacity(r8._capacity) - r8._size;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01f7, code lost:
    
        r34 = 1;
        r39 = 128;
        r0 = androidx.collection.ScatterMapKt.nextCapacity(r8._capacity);
        r1 = r8.metadata;
        r2 = r8.elements;
        r3 = r8._capacity;
        r8.initializeStorage(r0);
        r0 = r8.metadata;
        r4 = r8.elements;
        r11 = r8._capacity;
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0212, code lost:
    
        if (r12 >= r3) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0221, code lost:
    
        if (((r1[r12 >> 3] >> ((r12 & 7) << 3)) & 255) >= 128) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0223, code lost:
    
        r13 = r2[r12];
        r15 = java.lang.Long.hashCode(r13) * (-862048943);
        r15 = r15 ^ (r15 << 16);
        r16 = r0;
        r0 = r8.findFirstAvailableSlot(r15 >>> 7);
        r17 = r1;
        r0 = r15 & 127;
        r15 = r0 >> 3;
        r19 = (r0 & 7) << 3;
        r0 = (r16[r15] & (~(255 << r19))) | (r0 << r19);
        r16[r15] = r0;
        r16[(((r0 - 7) & r11) + (r11 & 7)) >> 3] = r0;
        r4[r0] = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0261, code lost:
    
        r16 = r0;
        r17 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0265, code lost:
    
        r12 = r12 + 1;
        r0 = r16;
        r1 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x026c, code lost:
    
        r0 = r8.findFirstAvailableSlot(r7);
        r34 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0270, code lost:
    
        r33 = r0;
        r8._size++;
        r0 = r8.growthLimit;
        r1 = r8.metadata;
        r2 = r33 >> 3;
        r3 = r1[r2];
        r7 = (r33 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x028a, code lost:
    
        if (((r3 >> r7) & 255) != r39) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x028c, code lost:
    
        r25 = r34 == true ? 1 : 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x028e, code lost:
    
        r8.growthLimit = r0 - r25;
        r0 = r8._capacity;
        r3 = (r3 & (~(255 << r7))) | (r9 << r7);
        r1[r2] = r3;
        r1[(((r33 - 7) & r0) + (r0 & 7)) >> 3] = r3;
        r34 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0333, code lost:
    
        if (((r7 & ((~r7) << 6)) & (-9187201950435737472L)) == 0) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0335, code lost:
    
        r1 = -1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: validateKeyEvent-ZmokQxo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m376validateKeyEventZmokQxo(KeyEvent keyEvent) {
        int i;
        int iNumberOfTrailingZeros;
        boolean z;
        int i2 = 1;
        char c = 7;
        long jM580getKeyZmokQxo = KeyEvent_androidKt.m580getKeyZmokQxo(keyEvent);
        int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
        KeyEventType.Companion.getClass();
        char c2 = '\b';
        int i3 = 0;
        if (iM581getTypeZmokQxo == KeyEventType.KeyDown) {
            MutableLongSet mutableLongSet = this.keysCurrentlyDown;
            if (mutableLongSet == null) {
                mutableLongSet = new MutableLongSet(3);
                this.keysCurrentlyDown = mutableLongSet;
            }
            MutableLongSet mutableLongSet2 = mutableLongSet;
            int iHashCode = Long.hashCode(jM580getKeyZmokQxo) * (-862048943);
            int i4 = iHashCode ^ (iHashCode << 16);
            int i5 = i4 >>> 7;
            int i6 = i4 & 127;
            int i7 = mutableLongSet2._capacity;
            int i8 = i5 & i7;
            int i9 = 0;
            loop0: while (true) {
                long[] jArr = mutableLongSet2.metadata;
                int i10 = i8 >> 3;
                char c3 = c;
                int i11 = (i8 & 7) << 3;
                long j = (jArr[i10] >>> i11) | ((jArr[i10 + 1] << (64 - i11)) & ((-i11) >> 63));
                long j2 = i6;
                long j3 = j ^ (j2 * 72340172838076673L);
                long j4 = (j3 - 72340172838076673L) & (~j3) & (-9187201950435737472L);
                while (true) {
                    if (j4 == 0) {
                        break;
                    }
                    iNumberOfTrailingZeros = (i8 + (Long.numberOfTrailingZeros(j4) >> 3)) & i7;
                    if (mutableLongSet2.elements[iNumberOfTrailingZeros] == jM580getKeyZmokQxo) {
                        z = 1;
                        break loop0;
                    }
                    j4 &= j4 - 1;
                }
                i9 += 8;
                i8 = (i8 + i9) & i7;
                c = c3;
            }
            mutableLongSet2.elements[iNumberOfTrailingZeros] = jM580getKeyZmokQxo;
            return z;
        }
        if (iM581getTypeZmokQxo != KeyEventType.KeyUp) {
            return true;
        }
        MutableLongSet mutableLongSet3 = this.keysCurrentlyDown;
        if (mutableLongSet3 == null || !mutableLongSet3.contains(jM580getKeyZmokQxo)) {
            return false;
        }
        MutableLongSet mutableLongSet4 = this.keysCurrentlyDown;
        if (mutableLongSet4 != null) {
            int iHashCode2 = Long.hashCode(jM580getKeyZmokQxo) * (-862048943);
            int i12 = iHashCode2 ^ (iHashCode2 << 16);
            int i13 = i12 & 127;
            int i14 = mutableLongSet4._capacity;
            int i15 = i12 >>> 7;
            loop5: while (true) {
                int i16 = i15 & i14;
                long[] jArr2 = mutableLongSet4.metadata;
                int i17 = i16 >> 3;
                int i18 = (i16 & 7) << 3;
                long j5 = (((-i18) >> 63) & (jArr2[i17 + 1] << (64 - i18))) | (jArr2[i17] >>> i18);
                long j6 = (i13 * 72340172838076673L) ^ j5;
                long j7 = (~j6) & (j6 - 72340172838076673L) & (-9187201950435737472L);
                while (true) {
                    if (j7 == 0) {
                        break;
                    }
                    int iNumberOfTrailingZeros2 = ((Long.numberOfTrailingZeros(j7) >> 3) + i16) & i14;
                    if (mutableLongSet4.elements[iNumberOfTrailingZeros2] == jM580getKeyZmokQxo) {
                        i = iNumberOfTrailingZeros2;
                        break loop5;
                    }
                    j7 &= j7 - 1;
                }
                i3 += 8;
                i15 = i16 + i3;
            }
            if (i >= 0) {
                mutableLongSet4._size--;
                long[] jArr3 = mutableLongSet4.metadata;
                int i19 = mutableLongSet4._capacity;
                int i20 = i >> 3;
                int i21 = (i & 7) << 3;
                long j8 = (jArr3[i20] & (~(255 << i21))) | (254 << i21);
                jArr3[i20] = j8;
                jArr3[(((i - 7) & i19) + (i19 & 7)) >> 3] = j8;
                return true;
            }
        }
        return true;
    }
}
