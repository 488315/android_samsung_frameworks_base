package androidx.compose.ui.input.nestedscroll;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.node.TraversableNodeKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NestedScrollDispatcher {
    public Lambda calculateNestedScrollScope = new Function0() { // from class: androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$calculateNestedScrollScope$1
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return NestedScrollDispatcher.this.scope;
        }
    };
    public NestedScrollNode lastKnownParentNode;
    public NestedScrollNode nestedScrollNode;
    public CoroutineScope scope;

    /* JADX WARN: Code restructure failed: missing block: B:32:0x005e, code lost:
    
        if (r0 == r1) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0086, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0084, code lost:
    
        if (r0 == r1) goto L43;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /* renamed from: dispatchPostFling-RZ2iAVY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m580dispatchPostFlingRZ2iAVY(long r8, long r10, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            r7 = this;
            boolean r0 = r12 instanceof androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPostFling$1
            if (r0 == 0) goto L14
            r0 = r12
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPostFling$1 r0 = (androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPostFling$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r12 = r0
            goto L1a
        L14:
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPostFling$1 r0 = new androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPostFling$1
            r0.<init>(r7, r12)
            goto L12
        L1a:
            java.lang.Object r0 = r12.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r12.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L38
            if (r2 == r4) goto L34
            if (r2 != r3) goto L2c
            kotlin.ResultKt.throwOnFailure(r0)
            goto L87
        L2c:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L34:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L61
        L38:
            kotlin.ResultKt.throwOnFailure(r0)
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r0 = r7.nestedScrollNode
            r2 = 0
            if (r0 == 0) goto L4b
            boolean r5 = r0.isAttached
            if (r5 == 0) goto L4b
            androidx.compose.ui.node.TraversableNode r0 = androidx.compose.ui.node.TraversableNodeKt.findNearestAncestor(r0)
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r0 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode) r0
            goto L4c
        L4b:
            r0 = r2
        L4c:
            r5 = 0
            if (r0 != 0) goto L6c
            boolean r0 = androidx.compose.ui.ComposeUiFlags.NewNestedScrollFlingDispatchingEnabled
            if (r0 == 0) goto L6c
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r7 = r7.lastKnownParentNode
            if (r7 == 0) goto L66
            r12.label = r4
            java.lang.Object r0 = r7.mo77onPostFlingRZ2iAVY(r8, r10, r12)
            if (r0 != r1) goto L61
            goto L86
        L61:
            androidx.compose.ui.unit.Velocity r0 = (androidx.compose.ui.unit.Velocity) r0
            long r5 = r0.packedValue
            goto L91
        L66:
            androidx.compose.ui.unit.Velocity$Companion r7 = androidx.compose.ui.unit.Velocity.Companion
            r7.getClass()
            goto L91
        L6c:
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r7 = r7.nestedScrollNode
            if (r7 == 0) goto L7b
            boolean r0 = r7.isAttached
            if (r0 == 0) goto L7b
            androidx.compose.ui.node.TraversableNode r7 = androidx.compose.ui.node.TraversableNodeKt.findNearestAncestor(r7)
            r2 = r7
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r2 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode) r2
        L7b:
            r7 = r2
            if (r7 == 0) goto L8c
            r12.label = r3
            java.lang.Object r0 = r7.mo77onPostFlingRZ2iAVY(r8, r10, r12)
            if (r0 != r1) goto L87
        L86:
            return r1
        L87:
            androidx.compose.ui.unit.Velocity r0 = (androidx.compose.ui.unit.Velocity) r0
            long r5 = r0.packedValue
            goto L91
        L8c:
            androidx.compose.ui.unit.Velocity$Companion r7 = androidx.compose.ui.unit.Velocity.Companion
            r7.getClass()
        L91:
            androidx.compose.ui.unit.Velocity r7 = androidx.compose.ui.unit.Velocity.m876boximpl(r5)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher.m580dispatchPostFlingRZ2iAVY(long, long, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* renamed from: dispatchPostScroll-DzOQY0M, reason: not valid java name */
    public final long m581dispatchPostScrollDzOQY0M(int i, long j, long j2) {
        NestedScrollNode nestedScrollNode = this.nestedScrollNode;
        NestedScrollNode nestedScrollNode2 = null;
        if (nestedScrollNode != null && nestedScrollNode.isAttached) {
            nestedScrollNode2 = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(nestedScrollNode);
        }
        NestedScrollNode nestedScrollNode3 = nestedScrollNode2;
        if (nestedScrollNode3 != null) {
            return nestedScrollNode3.mo78onPostScrollDzOQY0M(i, j, j2);
        }
        Offset.Companion.getClass();
        return 0L;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* renamed from: dispatchPreFling-QWom1Mo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object m582dispatchPreFlingQWom1Mo(long r5, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPreFling$1
            if (r0 == 0) goto L13
            r0 = r7
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPreFling$1 r0 = (androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPreFling$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPreFling$1 r0 = new androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher$dispatchPreFling$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4d
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r4 = r4.nestedScrollNode
            r7 = 0
            if (r4 == 0) goto L42
            boolean r2 = r4.isAttached
            if (r2 == 0) goto L42
            androidx.compose.ui.node.TraversableNode r4 = androidx.compose.ui.node.TraversableNodeKt.findNearestAncestor(r4)
            r7 = r4
            androidx.compose.ui.input.nestedscroll.NestedScrollNode r7 = (androidx.compose.ui.input.nestedscroll.NestedScrollNode) r7
        L42:
            if (r7 == 0) goto L52
            r0.label = r3
            java.lang.Object r7 = r7.mo288onPreFlingQWom1Mo(r5, r0)
            if (r7 != r1) goto L4d
            return r1
        L4d:
            androidx.compose.ui.unit.Velocity r7 = (androidx.compose.ui.unit.Velocity) r7
            long r4 = r7.packedValue
            goto L59
        L52:
            androidx.compose.ui.unit.Velocity$Companion r4 = androidx.compose.ui.unit.Velocity.Companion
            r4.getClass()
            r4 = 0
        L59:
            androidx.compose.ui.unit.Velocity r4 = androidx.compose.ui.unit.Velocity.m876boximpl(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher.m582dispatchPreFlingQWom1Mo(long, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* renamed from: dispatchPreScroll-OzD1aCk, reason: not valid java name */
    public final long m583dispatchPreScrollOzD1aCk(int i, long j) {
        NestedScrollNode nestedScrollNode = this.nestedScrollNode;
        NestedScrollNode nestedScrollNode2 = null;
        if (nestedScrollNode != null && nestedScrollNode.isAttached) {
            nestedScrollNode2 = (NestedScrollNode) TraversableNodeKt.findNearestAncestor(nestedScrollNode);
        }
        if (nestedScrollNode2 != null) {
            return nestedScrollNode2.mo175onPreScrollOzD1aCk(i, j);
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
