package androidx.compose.ui.input.nestedscroll;

import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNodeKt;
import androidx.compose.ui.unit.Velocity;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;

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
                public final Object mo781invoke(Object obj) {
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0016  */
    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostFling-RZ2iAVY */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo78onPostFlingRZ2iAVY(long j, long j2, Continuation continuation) {
        NestedScrollNode$onPostFling$1 nestedScrollNode$onPostFling$1;
        long j3;
        long j4;
        NestedScrollNode nestedScrollNode;
        long j5;
        long j6;
        if (continuation instanceof NestedScrollNode$onPostFling$1) {
            nestedScrollNode$onPostFling$1 = (NestedScrollNode$onPostFling$1) continuation;
            int i = nestedScrollNode$onPostFling$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                nestedScrollNode$onPostFling$1.label = i - Integer.MIN_VALUE;
            } else {
                nestedScrollNode$onPostFling$1 = new NestedScrollNode$onPostFling$1(this, continuation);
            }
        }
        NestedScrollNode$onPostFling$1 nestedScrollNode$onPostFling$12 = nestedScrollNode$onPostFling$1;
        Object objMo78onPostFlingRZ2iAVY = nestedScrollNode$onPostFling$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = nestedScrollNode$onPostFling$12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objMo78onPostFlingRZ2iAVY);
            NestedScrollConnection nestedScrollConnection = this.connection;
            nestedScrollNode$onPostFling$12.L$0 = this;
            nestedScrollNode$onPostFling$12.J$0 = j;
            nestedScrollNode$onPostFling$12.J$1 = j2;
            nestedScrollNode$onPostFling$12.label = 1;
            objMo78onPostFlingRZ2iAVY = nestedScrollConnection.mo78onPostFlingRZ2iAVY(j, j2, nestedScrollNode$onPostFling$12);
            if (objMo78onPostFlingRZ2iAVY != coroutineSingletons) {
                j3 = j2;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j6 = nestedScrollNode$onPostFling$12.J$0;
            ResultKt.throwOnFailure(objMo78onPostFlingRZ2iAVY);
            j5 = ((Velocity) objMo78onPostFlingRZ2iAVY).packedValue;
            j4 = j6;
            return Velocity.m878boximpl(Velocity.m883plusAH228Gc(j4, j5));
        }
        long j7 = nestedScrollNode$onPostFling$12.J$1;
        long j8 = nestedScrollNode$onPostFling$12.J$0;
        NestedScrollNode nestedScrollNode2 = (NestedScrollNode) nestedScrollNode$onPostFling$12.L$0;
        ResultKt.throwOnFailure(objMo78onPostFlingRZ2iAVY);
        this = nestedScrollNode2;
        j = j8;
        j3 = j7;
        j4 = ((Velocity) objMo78onPostFlingRZ2iAVY).packedValue;
        if (ComposeUiFlags.NewNestedScrollFlingDispatchingEnabled) {
            boolean z = this.isAttached;
            nestedScrollNode = z ? (z && z) ? (NestedScrollNode) TraversableNodeKt.findNearestAncestor(this) : null : this.lastKnownParentNode;
            if (nestedScrollNode == null) {
                long jM883plusAH228Gc = Velocity.m883plusAH228Gc(j, j4);
                long jM882minusAH228Gc = Velocity.m882minusAH228Gc(j3, j4);
                nestedScrollNode$onPostFling$12.L$0 = null;
                nestedScrollNode$onPostFling$12.J$0 = j4;
                nestedScrollNode$onPostFling$12.label = 2;
                objMo78onPostFlingRZ2iAVY = nestedScrollNode.mo78onPostFlingRZ2iAVY(jM883plusAH228Gc, jM882minusAH228Gc, nestedScrollNode$onPostFling$12);
                if (objMo78onPostFlingRZ2iAVY != coroutineSingletons) {
                    j6 = j4;
                    j5 = ((Velocity) objMo78onPostFlingRZ2iAVY).packedValue;
                    j4 = j6;
                }
                return coroutineSingletons;
            }
            Velocity.Companion.getClass();
            j5 = 0;
        } else {
            boolean z2 = this.isAttached;
            if (z2 && z2) {
                nestedScrollNode = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(this);
            }
            if (nestedScrollNode == null) {
            }
        }
        return Velocity.m878boximpl(Velocity.m883plusAH228Gc(j4, j5));
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostScroll-DzOQY0M */
    public final long mo79onPostScrollDzOQY0M(int i, long j, long j2) {
        long jMo79onPostScrollDzOQY0M;
        long jMo79onPostScrollDzOQY0M2 = this.connection.mo79onPostScrollDzOQY0M(i, j, j2);
        boolean z = this.isAttached;
        NestedScrollNode nestedScrollNode = null;
        if (z && z) {
            nestedScrollNode = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(this);
        }
        NestedScrollNode nestedScrollNode2 = nestedScrollNode;
        if (nestedScrollNode2 != null) {
            jMo79onPostScrollDzOQY0M = nestedScrollNode2.mo79onPostScrollDzOQY0M(i, Offset.m403plusMKHz9U(j, jMo79onPostScrollDzOQY0M2), Offset.m402minusMKHz9U(j2, jMo79onPostScrollDzOQY0M2));
        } else {
            Offset.Companion.getClass();
            jMo79onPostScrollDzOQY0M = 0;
        }
        return Offset.m403plusMKHz9U(jMo79onPostScrollDzOQY0M2, jMo79onPostScrollDzOQY0M);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x005c, code lost:
    
        if (r12 == r1) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0080, code lost:
    
        if (r12 != r1) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0082, code lost:
    
        return r1;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPreFling-QWom1Mo */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo289onPreFlingQWom1Mo(long j, Continuation continuation) {
        NestedScrollNode$onPreFling$1 nestedScrollNode$onPreFling$1;
        long j2;
        long j3;
        if (continuation instanceof NestedScrollNode$onPreFling$1) {
            nestedScrollNode$onPreFling$1 = (NestedScrollNode$onPreFling$1) continuation;
            int i = nestedScrollNode$onPreFling$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                nestedScrollNode$onPreFling$1.label = i - Integer.MIN_VALUE;
            } else {
                nestedScrollNode$onPreFling$1 = new NestedScrollNode$onPreFling$1(this, continuation);
            }
        }
        Object objMo289onPreFlingQWom1Mo = nestedScrollNode$onPreFling$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = nestedScrollNode$onPreFling$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objMo289onPreFlingQWom1Mo);
            boolean z = this.isAttached;
            NestedScrollNode nestedScrollNode = (z && z) ? (NestedScrollNode) TraversableNodeKt.findNearestAncestor(this) : null;
            if (nestedScrollNode != null) {
                nestedScrollNode$onPreFling$1.L$0 = this;
                nestedScrollNode$onPreFling$1.J$0 = j;
                nestedScrollNode$onPreFling$1.label = 1;
                objMo289onPreFlingQWom1Mo = nestedScrollNode.mo289onPreFlingQWom1Mo(j, nestedScrollNode$onPreFling$1);
            } else {
                Velocity.Companion.getClass();
                j2 = 0;
                long j4 = j;
                NestedScrollNode nestedScrollNode2 = this;
                j3 = j2;
                NestedScrollConnection nestedScrollConnection = nestedScrollNode2.connection;
                long jM882minusAH228Gc = Velocity.m882minusAH228Gc(j4, j3);
                nestedScrollNode$onPreFling$1.L$0 = null;
                nestedScrollNode$onPreFling$1.J$0 = j3;
                nestedScrollNode$onPreFling$1.label = 2;
                objMo289onPreFlingQWom1Mo = nestedScrollConnection.mo289onPreFlingQWom1Mo(jM882minusAH228Gc, nestedScrollNode$onPreFling$1);
            }
        } else {
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                j3 = nestedScrollNode$onPreFling$1.J$0;
                ResultKt.throwOnFailure(objMo289onPreFlingQWom1Mo);
                return Velocity.m878boximpl(Velocity.m883plusAH228Gc(j3, ((Velocity) objMo289onPreFlingQWom1Mo).packedValue));
            }
            j = nestedScrollNode$onPreFling$1.J$0;
            this = (NestedScrollNode) nestedScrollNode$onPreFling$1.L$0;
            ResultKt.throwOnFailure(objMo289onPreFlingQWom1Mo);
        }
        j2 = ((Velocity) objMo289onPreFlingQWom1Mo).packedValue;
        long j42 = j;
        NestedScrollNode nestedScrollNode22 = this;
        j3 = j2;
        NestedScrollConnection nestedScrollConnection2 = nestedScrollNode22.connection;
        long jM882minusAH228Gc2 = Velocity.m882minusAH228Gc(j42, j3);
        nestedScrollNode$onPreFling$1.L$0 = null;
        nestedScrollNode$onPreFling$1.J$0 = j3;
        nestedScrollNode$onPreFling$1.label = 2;
        objMo289onPreFlingQWom1Mo = nestedScrollConnection2.mo289onPreFlingQWom1Mo(jM882minusAH228Gc2, nestedScrollNode$onPreFling$1);
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPreScroll-OzD1aCk */
    public final long mo176onPreScrollOzD1aCk(int i, long j) {
        long jMo176onPreScrollOzD1aCk;
        boolean z = this.isAttached;
        NestedScrollNode nestedScrollNode = null;
        if (z && z) {
            nestedScrollNode = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(this);
        }
        if (nestedScrollNode != null) {
            jMo176onPreScrollOzD1aCk = nestedScrollNode.mo176onPreScrollOzD1aCk(i, j);
        } else {
            Offset.Companion.getClass();
            jMo176onPreScrollOzD1aCk = 0;
        }
        return Offset.m403plusMKHz9U(jMo176onPreScrollOzD1aCk, this.connection.mo176onPreScrollOzD1aCk(i, Offset.m402minusMKHz9U(j, jMo176onPreScrollOzD1aCk)));
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.compose.ui.input.nestedscroll.NestedScrollNode$updateDispatcherFields$1, kotlin.jvm.internal.Lambda] */
    public final void updateDispatcherFields() {
        NestedScrollDispatcher nestedScrollDispatcher = this.resolvedDispatcher;
        nestedScrollDispatcher.nestedScrollNode = this;
        if (ComposeUiFlags.NewNestedScrollFlingDispatchingEnabled) {
            nestedScrollDispatcher.lastKnownParentNode = null;
            this.lastKnownParentNode = null;
        }
        nestedScrollDispatcher.calculateNestedScrollScope = new Function0() { // from class: androidx.compose.ui.input.nestedscroll.NestedScrollNode.updateDispatcherFields.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return NestedScrollNode.this.getNestedCoroutineScope();
            }
        };
        this.resolvedDispatcher.scope = getCoroutineScope();
    }
}
