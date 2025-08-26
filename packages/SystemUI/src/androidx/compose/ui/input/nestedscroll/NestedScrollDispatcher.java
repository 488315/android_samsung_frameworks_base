package androidx.compose.ui.input.nestedscroll;

import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.node.TraversableNodeKt;
import androidx.compose.ui.unit.Velocity;
import kotlin.ResultKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class NestedScrollDispatcher {
    public Lambda calculateNestedScrollScope = new Function0() { // from class: androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$calculateNestedScrollScope$1
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return this.this$0.scope;
        }
    };
    public NestedScrollNode lastKnownParentNode;
    public NestedScrollNode nestedScrollNode;
    public CoroutineScope scope;

    /* JADX WARN: Code restructure failed: missing block: B:30:0x005e, code lost:
    
        if (r0 == r1) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0084, code lost:
    
        if (r0 == r1) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0086, code lost:
    
        return r1;
     */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /* renamed from: dispatchPostFling-RZ2iAVY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m582dispatchPostFlingRZ2iAVY(long j, long j2, ContinuationImpl continuationImpl) {
        NestedScrollDispatcher$dispatchPostFling$1 nestedScrollDispatcher$dispatchPostFling$1;
        long j3;
        if (continuationImpl instanceof NestedScrollDispatcher$dispatchPostFling$1) {
            nestedScrollDispatcher$dispatchPostFling$1 = (NestedScrollDispatcher$dispatchPostFling$1) continuationImpl;
            int i = nestedScrollDispatcher$dispatchPostFling$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                nestedScrollDispatcher$dispatchPostFling$1.label = i - Integer.MIN_VALUE;
            } else {
                nestedScrollDispatcher$dispatchPostFling$1 = new NestedScrollDispatcher$dispatchPostFling$1(this, continuationImpl);
            }
        }
        NestedScrollDispatcher$dispatchPostFling$1 nestedScrollDispatcher$dispatchPostFling$12 = nestedScrollDispatcher$dispatchPostFling$1;
        Object objMo78onPostFlingRZ2iAVY = nestedScrollDispatcher$dispatchPostFling$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = nestedScrollDispatcher$dispatchPostFling$12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objMo78onPostFlingRZ2iAVY);
            NestedScrollNode nestedScrollNode = this.nestedScrollNode;
            NestedScrollNode nestedScrollNode2 = null;
            j3 = 0;
            if (((nestedScrollNode == null || !nestedScrollNode.isAttached) ? null : (NestedScrollNode) TraversableNodeKt.findNearestAncestor(nestedScrollNode)) == null && ComposeUiFlags.NewNestedScrollFlingDispatchingEnabled) {
                NestedScrollNode nestedScrollNode3 = this.lastKnownParentNode;
                if (nestedScrollNode3 != null) {
                    nestedScrollDispatcher$dispatchPostFling$12.label = 1;
                    objMo78onPostFlingRZ2iAVY = nestedScrollNode3.mo78onPostFlingRZ2iAVY(j, j2, nestedScrollDispatcher$dispatchPostFling$12);
                } else {
                    Velocity.Companion.getClass();
                }
            } else {
                NestedScrollNode nestedScrollNode4 = this.nestedScrollNode;
                if (nestedScrollNode4 != null && nestedScrollNode4.isAttached) {
                    nestedScrollNode2 = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(nestedScrollNode4);
                }
                NestedScrollNode nestedScrollNode5 = nestedScrollNode2;
                if (nestedScrollNode5 != null) {
                    nestedScrollDispatcher$dispatchPostFling$12.label = 2;
                    objMo78onPostFlingRZ2iAVY = nestedScrollNode5.mo78onPostFlingRZ2iAVY(j, j2, nestedScrollDispatcher$dispatchPostFling$12);
                } else {
                    Velocity.Companion.getClass();
                }
            }
        } else if (i2 == 1) {
            ResultKt.throwOnFailure(objMo78onPostFlingRZ2iAVY);
            j3 = ((Velocity) objMo78onPostFlingRZ2iAVY).packedValue;
        } else {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objMo78onPostFlingRZ2iAVY);
            j3 = ((Velocity) objMo78onPostFlingRZ2iAVY).packedValue;
        }
        return Velocity.m878boximpl(j3);
    }

    /* renamed from: dispatchPostScroll-DzOQY0M, reason: not valid java name */
    public final long m583dispatchPostScrollDzOQY0M(int i, long j, long j2) {
        NestedScrollNode nestedScrollNode = this.nestedScrollNode;
        NestedScrollNode nestedScrollNode2 = null;
        if (nestedScrollNode != null && nestedScrollNode.isAttached) {
            nestedScrollNode2 = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(nestedScrollNode);
        }
        NestedScrollNode nestedScrollNode3 = nestedScrollNode2;
        if (nestedScrollNode3 != null) {
            return nestedScrollNode3.mo79onPostScrollDzOQY0M(i, j, j2);
        }
        Offset.Companion.getClass();
        return 0L;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: dispatchPreFling-QWom1Mo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m584dispatchPreFlingQWom1Mo(long j, ContinuationImpl continuationImpl) {
        NestedScrollDispatcher$dispatchPreFling$1 nestedScrollDispatcher$dispatchPreFling$1;
        long j2;
        if (continuationImpl instanceof NestedScrollDispatcher$dispatchPreFling$1) {
            nestedScrollDispatcher$dispatchPreFling$1 = (NestedScrollDispatcher$dispatchPreFling$1) continuationImpl;
            int i = nestedScrollDispatcher$dispatchPreFling$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                nestedScrollDispatcher$dispatchPreFling$1.label = i - Integer.MIN_VALUE;
            } else {
                nestedScrollDispatcher$dispatchPreFling$1 = new NestedScrollDispatcher$dispatchPreFling$1(this, continuationImpl);
            }
        }
        Object objMo289onPreFlingQWom1Mo = nestedScrollDispatcher$dispatchPreFling$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = nestedScrollDispatcher$dispatchPreFling$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objMo289onPreFlingQWom1Mo);
            NestedScrollNode nestedScrollNode = this.nestedScrollNode;
            NestedScrollNode nestedScrollNode2 = null;
            if (nestedScrollNode != null && nestedScrollNode.isAttached) {
                nestedScrollNode2 = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(nestedScrollNode);
            }
            if (nestedScrollNode2 == null) {
                Velocity.Companion.getClass();
                j2 = 0;
                return Velocity.m878boximpl(j2);
            }
            nestedScrollDispatcher$dispatchPreFling$1.label = 1;
            objMo289onPreFlingQWom1Mo = nestedScrollNode2.mo289onPreFlingQWom1Mo(j, nestedScrollDispatcher$dispatchPreFling$1);
            if (objMo289onPreFlingQWom1Mo == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objMo289onPreFlingQWom1Mo);
        }
        j2 = ((Velocity) objMo289onPreFlingQWom1Mo).packedValue;
        return Velocity.m878boximpl(j2);
    }

    /* renamed from: dispatchPreScroll-OzD1aCk, reason: not valid java name */
    public final long m585dispatchPreScrollOzD1aCk(int i, long j) {
        NestedScrollNode nestedScrollNode = this.nestedScrollNode;
        NestedScrollNode nestedScrollNode2 = null;
        if (nestedScrollNode != null && nestedScrollNode.isAttached) {
            nestedScrollNode2 = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(nestedScrollNode);
        }
        if (nestedScrollNode2 != null) {
            return nestedScrollNode2.mo176onPreScrollOzD1aCk(i, j);
        }
        Offset.Companion.getClass();
        return 0L;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
    public final CoroutineScope getCoroutineScope() {
        CoroutineScope coroutineScope = (CoroutineScope) this.calculateNestedScrollScope.invoke();
        if (coroutineScope != null) {
            return coroutineScope;
        }
        throw new IllegalStateException("in order to access nested coroutine scope you need to attach dispatcher to the `Modifier.nestedScroll` first.");
    }
}
