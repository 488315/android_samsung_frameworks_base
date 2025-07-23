package androidx.compose.ui.focus;

import androidx.collection.MutableLongSet;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.NodeChain;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                return FocusOwnerImpl.this.rootFocusNode;
            }

            public final boolean equals(Object obj) {
                return obj == this;
            }

            public final int hashCode() {
                return FocusOwnerImpl.this.rootFocusNode.hashCode();
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
            LayoutNode requireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNode);
            while (requireLayoutNode != null) {
                if ((requireLayoutNode.nodes.head.aggregateChildKindSet & 1024) != 0) {
                    while (node != null) {
                        if ((node.kindSet & 1024) != 0) {
                            Modifier.Node node2 = node;
                            MutableVector mutableVector = null;
                            while (node2 != null) {
                                if (node2 instanceof FocusTargetNode) {
                                    ((FocusTargetNode) node2).dispatchFocusCallbacks$ui_release(FocusStateImpl.ActiveParent, FocusStateImpl.Inactive);
                                } else if ((node2.kindSet & 1024) != 0 && (node2 instanceof DelegatingNode)) {
                                    int i = 0;
                                    for (Modifier.Node node3 = ((DelegatingNode) node2).delegate; node3 != null; node3 = node3.child) {
                                        if ((node3.kindSet & 1024) != 0) {
                                            i++;
                                            if (i == 1) {
                                                node2 = node3;
                                            } else {
                                                if (mutableVector == null) {
                                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (node2 != null) {
                                                    mutableVector.add(node2);
                                                    node2 = null;
                                                }
                                                mutableVector.add(node3);
                                            }
                                        }
                                    }
                                    if (i == 1) {
                                    }
                                }
                                node2 = DelegatableNodeKt.access$pop(mutableVector);
                            }
                        }
                        node = node.parent;
                    }
                }
                requireLayoutNode = requireLayoutNode.getParent$ui_release();
                node = (requireLayoutNode == null || (nodeChain = requireLayoutNode.nodes) == null) ? null : nodeChain.tail;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0059, code lost:
    
        if (r9 != 3) goto L30;
     */
    /* renamed from: clearFocus-I7lrPNg, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean m370clearFocusI7lrPNg(int r9, boolean r10, boolean r11) {
        /*
            r8 = this;
            boolean r0 = androidx.compose.ui.ComposeUiFlags.isTrackFocusEnabled
            androidx.compose.ui.focus.FocusTargetNode r1 = r8.rootFocusNode
            r2 = 0
            r3 = 3
            r4 = 2
            r5 = 1
            if (r0 == 0) goto L30
            if (r10 != 0) goto L2c
            androidx.compose.ui.focus.CustomDestinationResult r9 = androidx.compose.ui.focus.FocusTransactionsKt.m379performCustomClearFocusMxy_nc0(r1, r9)
            int[] r0 = androidx.compose.ui.focus.FocusOwnerImpl.WhenMappings.$EnumSwitchMapping$0
            int r9 = r9.ordinal()
            r9 = r0[r9]
            if (r9 == r5) goto L62
            if (r9 == r4) goto L62
            if (r9 == r3) goto L62
            r0 = 4
            if (r9 != r0) goto L26
            r8.clearFocus(r10)
        L24:
            r2 = r5
            goto L62
        L26:
            kotlin.NoWhenBranchMatchedException r8 = new kotlin.NoWhenBranchMatchedException
            r8.<init>()
            throw r8
        L2c:
            r8.clearFocus(r10)
            goto L24
        L30:
            androidx.compose.ui.focus.FocusTransactionManager r0 = r8.focusTransactionManager
            androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1 r6 = new kotlin.jvm.functions.Function0() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1
                static {
                    /*
                        androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1 r0 = new androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1) androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1.INSTANCE androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 0
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ java.lang.Object invoke() {
                    /*
                        r0 = this;
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl$clearFocus$clearedFocusSuccessfully$1.invoke():java.lang.Object");
                }
            }
            boolean r7 = r0.ongoingTransaction     // Catch: java.lang.Throwable -> L3c
            if (r7 == 0) goto L3e
            androidx.compose.ui.focus.FocusTransactionManager.access$cancelTransaction(r0)     // Catch: java.lang.Throwable -> L3c
            goto L3e
        L3c:
            r8 = move-exception
            goto L6c
        L3e:
            r0.ongoingTransaction = r5     // Catch: java.lang.Throwable -> L3c
            if (r6 == 0) goto L47
            androidx.compose.runtime.collection.MutableVector r7 = r0.cancellationListener     // Catch: java.lang.Throwable -> L3c
            r7.add(r6)     // Catch: java.lang.Throwable -> L3c
        L47:
            if (r10 != 0) goto L5b
            androidx.compose.ui.focus.CustomDestinationResult r9 = androidx.compose.ui.focus.FocusTransactionsKt.m379performCustomClearFocusMxy_nc0(r1, r9)     // Catch: java.lang.Throwable -> L3c
            int[] r6 = androidx.compose.ui.focus.FocusOwnerImpl.WhenMappings.$EnumSwitchMapping$0     // Catch: java.lang.Throwable -> L3c
            int r9 = r9.ordinal()     // Catch: java.lang.Throwable -> L3c
            r9 = r6[r9]     // Catch: java.lang.Throwable -> L3c
            if (r9 == r5) goto L5f
            if (r9 == r4) goto L5f
            if (r9 == r3) goto L5f
        L5b:
            boolean r2 = androidx.compose.ui.focus.FocusTransactionsKt.clearFocus(r1, r10)     // Catch: java.lang.Throwable -> L3c
        L5f:
            androidx.compose.ui.focus.FocusTransactionManager.access$commitTransaction(r0)
        L62:
            if (r2 == 0) goto L6b
            if (r11 == 0) goto L6b
            kotlin.jvm.functions.Function0 r8 = r8.onClearFocusForOwner
            r8.invoke()
        L6b:
            return r2
        L6c:
            androidx.compose.ui.focus.FocusTransactionManager.access$commitTransaction(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl.m370clearFocusI7lrPNg(int, boolean, boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x005b, code lost:
    
        if (r7 == null) goto L31;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0169 A[Catch: all -> 0x0330, TryCatch #0 {all -> 0x0330, blocks: (B:3:0x0008, B:5:0x0011, B:9:0x001c, B:13:0x0026, B:16:0x0032, B:18:0x0038, B:19:0x003e, B:21:0x0046, B:23:0x004b, B:25:0x0051, B:29:0x0057, B:34:0x0169, B:36:0x016f, B:37:0x0172, B:39:0x017d, B:42:0x0189, B:46:0x0193, B:81:0x0199, B:82:0x019e, B:75:0x01d7, B:48:0x01a2, B:50:0x01a8, B:52:0x01ac, B:54:0x01b4, B:56:0x01ba, B:62:0x01c1, B:64:0x01ca, B:65:0x01ce, B:60:0x01d1, B:84:0x01dc, B:87:0x01df, B:89:0x01e5, B:96:0x01e9, B:101:0x01f0, B:103:0x01f8, B:111:0x020f, B:112:0x0211, B:113:0x021f, B:115:0x0223, B:154:0x0227, B:149:0x027c, B:117:0x0233, B:119:0x023c, B:121:0x0240, B:123:0x0247, B:125:0x024d, B:127:0x0250, B:130:0x0253, B:132:0x0259, B:133:0x0260, B:135:0x0268, B:136:0x026d, B:138:0x0273, B:129:0x0276, B:160:0x0287, B:164:0x0297, B:165:0x02a5, B:167:0x02a9, B:206:0x02ad, B:201:0x0302, B:169:0x02b9, B:171:0x02c2, B:173:0x02c6, B:175:0x02cd, B:177:0x02d3, B:179:0x02d6, B:182:0x02d9, B:184:0x02df, B:185:0x02e6, B:187:0x02ee, B:188:0x02f3, B:190:0x02f9, B:181:0x02fc, B:213:0x030f, B:215:0x0316, B:222:0x0328, B:223:0x032a, B:230:0x005f, B:232:0x0065, B:233:0x0068, B:235:0x0070, B:238:0x007c, B:242:0x0086, B:277:0x00d8, B:279:0x00dc, B:244:0x008b, B:246:0x0091, B:248:0x0095, B:250:0x009d, B:252:0x00a3, B:258:0x00aa, B:260:0x00b3, B:261:0x00b7, B:256:0x00ba, B:267:0x00c0, B:281:0x00c5, B:284:0x00c8, B:286:0x00ce, B:293:0x00d2, B:298:0x00e2, B:300:0x00e8, B:301:0x00eb, B:303:0x00f5, B:306:0x0101, B:310:0x010b, B:345:0x015d, B:347:0x0161, B:312:0x0110, B:314:0x0116, B:316:0x011a, B:318:0x0122, B:320:0x0128, B:326:0x012f, B:328:0x0138, B:329:0x013c, B:324:0x013f, B:335:0x0145, B:350:0x014a, B:353:0x014d, B:355:0x0153, B:362:0x0157), top: B:2:0x0008 }] */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean m371dispatchKeyEventYhN2O0w(android.view.KeyEvent r13, kotlin.jvm.functions.Function0 r14) {
        /*
            Method dump skipped, instructions count: 821
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl.m371dispatchKeyEventYhN2O0w(android.view.KeyEvent, kotlin.jvm.functions.Function0):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0105  */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Boolean m372focusSearchULY8qGw(int r19, androidx.compose.ui.geometry.Rect r20, kotlin.jvm.functions.Function1 r21) {
        /*
            Method dump skipped, instructions count: 648
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl.m372focusSearchULY8qGw(int, androidx.compose.ui.geometry.Rect, kotlin.jvm.functions.Function1):java.lang.Boolean");
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [T, java.lang.Boolean] */
    /* renamed from: moveFocus-3ESFkO8, reason: not valid java name */
    public final boolean m373moveFocus3ESFkO8(final int i) {
        boolean z = ComposeUiFlags.isViewFocusFixEnabled;
        Function1 function1 = this.onMoveFocusInterop;
        if (z && ((Boolean) function1.mo779invoke(FocusDirection.m366boximpl(i))).booleanValue()) {
            return true;
        }
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = Boolean.FALSE;
        FocusTransactionManager focusTransactionManager = this.focusTransactionManager;
        int i2 = focusTransactionManager.generation;
        FocusTargetNode focusTargetNode = this.activeFocusTargetNode;
        Boolean m372focusSearchULY8qGw = m372focusSearchULY8qGw(i, (Rect) this.onFocusRectInterop.invoke(), new Function1() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$moveFocus$focusSearchSuccess$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Type inference failed for: r3v3, types: [T, java.lang.Boolean] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ref$ObjectRef.element = Boolean.valueOf(((FocusTargetNode) obj).m378requestFocus3ESFkO8(i));
                Boolean bool = ref$ObjectRef.element;
                return Boolean.valueOf(bool != null ? bool.booleanValue() : false);
            }
        });
        int i3 = focusTransactionManager.generation;
        Boolean bool = Boolean.TRUE;
        if (Intrinsics.areEqual(m372focusSearchULY8qGw, bool)) {
            if (i2 != i3) {
                return true;
            }
            if (ComposeUiFlags.isTrackFocusEnabled && focusTargetNode != this.activeFocusTargetNode) {
                return true;
            }
        }
        if (m372focusSearchULY8qGw != null && ref$ObjectRef.element != 0) {
            if (m372focusSearchULY8qGw.equals(bool) && Intrinsics.areEqual(ref$ObjectRef.element, bool)) {
                return true;
            }
            if (FocusOwnerImplKt.m375is1dFocusSearch3ESFkO8(i)) {
                if (m370clearFocusI7lrPNg(i, false, false)) {
                    Boolean m372focusSearchULY8qGw2 = m372focusSearchULY8qGw(i, null, new Function1() { // from class: androidx.compose.ui.focus.FocusOwnerImpl$takeFocus$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj) {
                            return Boolean.valueOf(((FocusTargetNode) obj).m378requestFocus3ESFkO8(i));
                        }
                    });
                    if (m372focusSearchULY8qGw2 != null ? m372focusSearchULY8qGw2.booleanValue() : false) {
                        return true;
                    }
                }
            } else if (!z && ((Boolean) function1.mo779invoke(FocusDirection.m366boximpl(i))).booleanValue()) {
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

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0335, code lost:
    
        r1 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00a9, code lost:
    
        if (((r3 & ((~r3) << 6)) & (-9187201950435737472L)) == 0) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00ab, code lost:
    
        r0 = r8.findFirstAvailableSlot(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00b1, code lost:
    
        if (r8.growthLimit != 0) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c2, code lost:
    
        if (((r8.metadata[r0 >> 3] >> ((r0 & 7) << 3)) & 255) != 254) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00ca, code lost:
    
        r0 = r8._capacity;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00cc, code lost:
    
        if (r0 <= 8) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ce, code lost:
    
        r11 = r8._size;
        r1 = kotlin.ULong.$r8$clinit;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00de, code lost:
    
        if (java.lang.Long.compareUnsigned(r11 * 32, r0 * 25) > 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00e0, code lost:
    
        r0 = r8.metadata;
        r1 = r8._capacity;
        r11 = r8.elements;
        r12 = (r1 + 7) >> 3;
        r14 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00ec, code lost:
    
        if (r14 >= r12) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00ee, code lost:
    
        r3 = r0[r14] & (-9187201950435737472L);
        r15 = r14;
        r0[r15] = (-72340172838076674L) & ((~r3) + (r3 >>> r32));
        r14 = r15 + 1;
        r13 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0108, code lost:
    
        r39 = 128;
        r3 = r0.length;
        r4 = r3 - 1;
        r3 = r3 - 2;
        r14 = 72057594037927935L;
        r0[r3] = (r0[r3] & 72057594037927935L) | (-72057594037927936L);
        r0[r4] = r0[0];
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0125, code lost:
    
        if (r3 == r1) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0127, code lost:
    
        r4 = r3 >> 3;
        r16 = (r3 & 7) << 3;
        r12 = (r0[r4] >> r16) & 255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0135, code lost:
    
        if (r12 != 128) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x013b, code lost:
    
        if (r12 == 254) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x013e, code lost:
    
        r12 = java.lang.Long.hashCode(r11[r3]) * (-862048943);
        r13 = (r12 ^ (r12 << 16)) >>> 7;
        r19 = r8.findFirstAvailableSlot(r13);
        r13 = r13 & r1;
        r21 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x015f, code lost:
    
        if ((((r19 - r13) & r1) / 8) != (((r3 - r13) & r1) / 8)) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0161, code lost:
    
        r34 = r2;
        r0[r4] = ((~(255 << r16)) & r0[r4]) | ((r12 & 127) << r16);
        r0[r0.length - 1] = (r0[0] & r21) | Long.MIN_VALUE;
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0180, code lost:
    
        r14 = r21;
        r2 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0185, code lost:
    
        r34 = r2;
        r20 = r3;
        r2 = r19 >> 3;
        r13 = r0[r2];
        r3 = (r19 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0197, code lost:
    
        if (((r13 >> r3) & 255) != 128) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0199, code lost:
    
        r15 = r1;
        r0[r2] = ((~(255 << r3)) & r13) | ((r12 & 127) << r3);
        r0[r4] = (r0[r4] & (~(255 << r16))) | (128 << r16);
        r11[r19] = r11[r20];
        r11[r20] = 0;
        r3 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01d9, code lost:
    
        r0[r0.length - 1] = (r0[0] & r21) | Long.MIN_VALUE;
        r3 = r3 + 1;
        r1 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x01be, code lost:
    
        r15 = r1;
        r0[r2] = ((~(255 << r3)) & r13) | ((r12 & 127) << r3);
        r1 = r11[r19];
        r11[r19] = r11[r20];
        r11[r20] = r1;
        r3 = r20 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0137, code lost:
    
        r3 = r3 + r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01e8, code lost:
    
        r34 = r2;
        r8.growthLimit = androidx.collection.ScatterMapKt.loadedCapacity(r8._capacity) - r8._size;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x026c, code lost:
    
        r0 = r8.findFirstAvailableSlot(r7);
        r34 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0270, code lost:
    
        r33 = r0;
        r8._size++;
        r0 = r8.growthLimit;
        r1 = r8.metadata;
        r2 = r33 >> 3;
        r3 = r1[r2];
        r7 = (r33 & 7) << 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x028a, code lost:
    
        if (((r3 >> r7) & 255) != r39) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x028c, code lost:
    
        r25 = r34 == true ? 1 : 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x028e, code lost:
    
        r8.growthLimit = r0 - r25;
        r0 = r8._capacity;
        r3 = (r3 & (~(255 << r7))) | (r9 << r7);
        r1[r2] = r3;
        r1[(((r33 - 7) & r0) + (r0 & 7)) >> 3] = r3;
        r34 = r34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01f7, code lost:
    
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
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0212, code lost:
    
        if (r12 >= r3) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0221, code lost:
    
        if (((r1[r12 >> 3] >> ((r12 & 7) << 3)) & 255) >= 128) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0223, code lost:
    
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
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0265, code lost:
    
        r12 = r12 + 1;
        r0 = r16;
        r1 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0261, code lost:
    
        r16 = r0;
        r17 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00c4, code lost:
    
        r34 = 1;
        r39 = 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0333, code lost:
    
        if (((r7 & ((~r7) << 6)) & (-9187201950435737472L)) == 0) goto L85;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: validateKeyEvent-ZmokQxo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean m374validateKeyEventZmokQxo(android.view.KeyEvent r40) {
        /*
            Method dump skipped, instructions count: 881
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusOwnerImpl.m374validateKeyEventZmokQxo(android.view.KeyEvent):boolean");
    }
}
