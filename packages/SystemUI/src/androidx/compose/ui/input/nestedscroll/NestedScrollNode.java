package androidx.compose.ui.input.nestedscroll;

import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNodeKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NestedScrollNode extends Modifier.Node implements TraversableNode, NestedScrollConnection {
    public NestedScrollConnection connection;
    public NestedScrollNode lastKnownParentNode;
    public NestedScrollDispatcher resolvedDispatcher;
    public final Object traverseKey;

    public NestedScrollNode(NestedScrollConnection nestedScrollConnection, NestedScrollDispatcher nestedScrollDispatcher) {
        this.connection = nestedScrollConnection;
        this.resolvedDispatcher = nestedScrollDispatcher == null ? new NestedScrollDispatcher() : nestedScrollDispatcher;
        this.traverseKey = "androidx.compose.ui.input.nestedscroll.NestedScrollNode";
    }

    public final CoroutineScope getNestedCoroutineScope() {
        NestedScrollNode nestedScrollNode = this.isAttached ? (NestedScrollNode) TraversableNodeKt.findNearestAncestor(this) : null;
        if (nestedScrollNode != null) {
            return nestedScrollNode.getNestedCoroutineScope();
        }
        CoroutineScope coroutineScope = this.resolvedDispatcher.scope;
        if (coroutineScope != null) {
            return coroutineScope;
        }
        throw new IllegalStateException("in order to access nested coroutine scope you need to attach dispatcher to the `Modifier.nestedScroll` first.");
    }

    @Override // androidx.compose.ui.node.TraversableNode
    public final Object getTraverseKey() {
        return this.traverseKey;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        updateDispatcherFields();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        if (ComposeUiFlags.NewNestedScrollFlingDispatchingEnabled) {
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            TraversableNodeKt.traverseAncestors(this, new Function1() { // from class: androidx.compose.ui.input.nestedscroll.NestedScrollNodeKt$findNearestAttachedAncestor$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Type inference failed for: r2v1, types: [T, androidx.compose.ui.node.TraversableNode] */
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    boolean z;
                    ?? r2 = (TraversableNode) obj;
                    if (((Modifier.Node) r2).node.isAttached) {
                        ref$ObjectRef.element = r2;
                        z = false;
                    } else {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
            });
            NestedScrollNode nestedScrollNode = (NestedScrollNode) ((TraversableNode) ref$ObjectRef.element);
            this.lastKnownParentNode = nestedScrollNode;
            this.resolvedDispatcher.lastKnownParentNode = nestedScrollNode;
        }
        NestedScrollDispatcher nestedScrollDispatcher = this.resolvedDispatcher;
        if (nestedScrollDispatcher.nestedScrollNode == this) {
            nestedScrollDispatcher.nestedScrollNode = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostFling-RZ2iAVY */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object mo77onPostFlingRZ2iAVY(long r12, long r14, kotlin.coroutines.Continuation r16) {
        /*
            r11 = this;
            r0 = r16
            boolean r1 = r0 instanceof androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPostFling$1
            if (r1 == 0) goto L16
            r1 = r0
            androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPostFling$1 r1 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPostFling$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L16
            int r2 = r2 - r3
            r1.label = r2
        L14:
            r7 = r1
            goto L1c
        L16:
            androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPostFling$1 r1 = new androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPostFling$1
            r1.<init>(r11, r0)
            goto L14
        L1c:
            java.lang.Object r0 = r7.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r7.label
            r8 = 2
            r3 = 1
            if (r2 == 0) goto L49
            if (r2 == r3) goto L39
            if (r2 != r8) goto L31
            long r11 = r7.J$0
            kotlin.ResultKt.throwOnFailure(r0)
            goto La4
        L31:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L39:
            long r11 = r7.J$1
            long r2 = r7.J$0
            java.lang.Object r13 = r7.L$0
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r13 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode) r13
            kotlin.ResultKt.throwOnFailure(r0)
            r9 = r11
            r11 = r13
            r12 = r2
            r2 = r9
            goto L60
        L49:
            kotlin.ResultKt.throwOnFailure(r0)
            androidx.compose.ui.input.nestedscroll.NestedScrollConnection r2 = r11.connection
            r7.L$0 = r11
            r7.J$0 = r12
            r7.J$1 = r14
            r7.label = r3
            r3 = r12
            r5 = r14
            java.lang.Object r0 = r2.mo77onPostFlingRZ2iAVY(r3, r5, r7)
            if (r0 != r1) goto L5f
            goto La2
        L5f:
            r2 = r14
        L60:
            androidx.compose.ui.unit.Velocity r0 = (androidx.compose.ui.unit.Velocity) r0
            long r4 = r0.packedValue
            boolean r0 = androidx.compose.ui.ComposeUiFlags.NewNestedScrollFlingDispatchingEnabled
            r6 = 0
            if (r0 == 0) goto L7d
            boolean r0 = r11.isAttached
            if (r0 == 0) goto L7a
            if (r0 == 0) goto L78
            if (r0 == 0) goto L78
            androidx.compose.ui.node.TraversableNode r11 = androidx.compose.ui.node.TraversableNodeKt.findNearestAncestor(r11)
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r11 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode) r11
            goto L89
        L78:
            r11 = r6
            goto L89
        L7a:
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r11 = r11.lastKnownParentNode
            goto L89
        L7d:
            boolean r0 = r11.isAttached
            if (r0 == 0) goto L78
            if (r0 == 0) goto L78
            androidx.compose.ui.node.TraversableNode r11 = androidx.compose.ui.node.TraversableNodeKt.findNearestAncestor(r11)
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r11 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode) r11
        L89:
            if (r11 == 0) goto Laa
            long r12 = androidx.compose.ui.unit.Velocity.m881plusAH228Gc(r12, r4)
            long r2 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r2, r4)
            r7.L$0 = r6
            r7.J$0 = r4
            r7.label = r8
            r14 = r2
            r16 = r7
            java.lang.Object r0 = r11.mo77onPostFlingRZ2iAVY(r12, r14, r16)
            if (r0 != r1) goto La3
        La2:
            return r1
        La3:
            r11 = r4
        La4:
            androidx.compose.ui.unit.Velocity r0 = (androidx.compose.ui.unit.Velocity) r0
            long r0 = r0.packedValue
            r4 = r11
            goto Lb1
        Laa:
            androidx.compose.ui.unit.Velocity$Companion r11 = androidx.compose.ui.unit.Velocity.Companion
            r11.getClass()
            r0 = 0
        Lb1:
            long r11 = androidx.compose.ui.unit.Velocity.m881plusAH228Gc(r4, r0)
            androidx.compose.ui.unit.Velocity r11 = androidx.compose.ui.unit.Velocity.m876boximpl(r11)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.nestedscroll.NestedScrollNode.mo77onPostFlingRZ2iAVY(long, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostScroll-DzOQY0M */
    public final long mo78onPostScrollDzOQY0M(int i, long j, long j2) {
        long j3;
        long mo78onPostScrollDzOQY0M = this.connection.mo78onPostScrollDzOQY0M(i, j, j2);
        boolean z = this.isAttached;
        NestedScrollNode nestedScrollNode = null;
        if (z && z) {
            nestedScrollNode = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(this);
        }
        NestedScrollNode nestedScrollNode2 = nestedScrollNode;
        if (nestedScrollNode2 != null) {
            j3 = nestedScrollNode2.mo78onPostScrollDzOQY0M(i, Offset.m401plusMKHz9U(j, mo78onPostScrollDzOQY0M), Offset.m400minusMKHz9U(j2, mo78onPostScrollDzOQY0M));
        } else {
            Offset.Companion.getClass();
            j3 = 0;
        }
        return Offset.m401plusMKHz9U(mo78onPostScrollDzOQY0M, j3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0080, code lost:
    
        if (r12 == r1) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0082, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005c, code lost:
    
        if (r12 == r1) goto L30;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPreFling-QWom1Mo */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object mo288onPreFlingQWom1Mo(long r10, kotlin.coroutines.Continuation r12) {
        /*
            r9 = this;
            boolean r0 = r12 instanceof androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPreFling$1
            if (r0 == 0) goto L13
            r0 = r12
            androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPreFling$1 r0 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPreFling$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPreFling$1 r0 = new androidx.compose.ui.input.nestedscroll.NestedScrollNode$onPreFling$1
            r0.<init>(r9, r12)
        L18:
            java.lang.Object r12 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L3f
            if (r2 == r5) goto L35
            if (r2 != r4) goto L2d
            long r9 = r0.J$0
            kotlin.ResultKt.throwOnFailure(r12)
            goto L83
        L2d:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L35:
            long r10 = r0.J$0
            java.lang.Object r9 = r0.L$0
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r9 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode) r9
            kotlin.ResultKt.throwOnFailure(r12)
            goto L5f
        L3f:
            kotlin.ResultKt.throwOnFailure(r12)
            boolean r12 = r9.isAttached
            if (r12 == 0) goto L4f
            if (r12 == 0) goto L4f
            androidx.compose.ui.node.TraversableNode r12 = androidx.compose.ui.node.TraversableNodeKt.findNearestAncestor(r9)
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r12 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode) r12
            goto L50
        L4f:
            r12 = r3
        L50:
            if (r12 == 0) goto L68
            r0.L$0 = r9
            r0.J$0 = r10
            r0.label = r5
            java.lang.Object r12 = r12.mo288onPreFlingQWom1Mo(r10, r0)
            if (r12 != r1) goto L5f
            goto L82
        L5f:
            androidx.compose.ui.unit.Velocity r12 = (androidx.compose.ui.unit.Velocity) r12
            long r5 = r12.packedValue
        L63:
            r7 = r10
            r11 = r9
            r9 = r5
            r5 = r7
            goto L70
        L68:
            androidx.compose.ui.unit.Velocity$Companion r12 = androidx.compose.ui.unit.Velocity.Companion
            r12.getClass()
            r5 = 0
            goto L63
        L70:
            androidx.compose.ui.input.nestedscroll.NestedScrollConnection r11 = r11.connection
            long r5 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r5, r9)
            r0.L$0 = r3
            r0.J$0 = r9
            r0.label = r4
            java.lang.Object r12 = r11.mo288onPreFlingQWom1Mo(r5, r0)
            if (r12 != r1) goto L83
        L82:
            return r1
        L83:
            androidx.compose.ui.unit.Velocity r12 = (androidx.compose.ui.unit.Velocity) r12
            long r11 = r12.packedValue
            long r9 = androidx.compose.ui.unit.Velocity.m881plusAH228Gc(r9, r11)
            androidx.compose.ui.unit.Velocity r9 = androidx.compose.ui.unit.Velocity.m876boximpl(r9)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.nestedscroll.NestedScrollNode.mo288onPreFlingQWom1Mo(long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPreScroll-OzD1aCk */
    public final long mo175onPreScrollOzD1aCk(int i, long j) {
        long j2;
        boolean z = this.isAttached;
        NestedScrollNode nestedScrollNode = null;
        if (z && z) {
            nestedScrollNode = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(this);
        }
        if (nestedScrollNode != null) {
            j2 = nestedScrollNode.mo175onPreScrollOzD1aCk(i, j);
        } else {
            Offset.Companion.getClass();
            j2 = 0;
        }
        return Offset.m401plusMKHz9U(j2, this.connection.mo175onPreScrollOzD1aCk(i, Offset.m400minusMKHz9U(j, j2)));
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.compose.ui.input.nestedscroll.NestedScrollNode$updateDispatcherFields$1, kotlin.jvm.internal.Lambda] */
    public final void updateDispatcherFields() {
        NestedScrollDispatcher nestedScrollDispatcher = this.resolvedDispatcher;
        nestedScrollDispatcher.nestedScrollNode = this;
        if (ComposeUiFlags.NewNestedScrollFlingDispatchingEnabled) {
            nestedScrollDispatcher.lastKnownParentNode = null;
            this.lastKnownParentNode = null;
        }
        nestedScrollDispatcher.calculateNestedScrollScope = new Function0() { // from class: androidx.compose.ui.input.nestedscroll.NestedScrollNode$updateDispatcherFields$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return NestedScrollNode.this.getNestedCoroutineScope();
            }
        };
        this.resolvedDispatcher.scope = getCoroutineScope();
    }
}
