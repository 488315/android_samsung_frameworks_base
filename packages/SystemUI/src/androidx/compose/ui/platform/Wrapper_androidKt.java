package androidx.compose.ui.platform;

import android.view.View;
import android.view.ViewGroup;
import androidx.compose.runtime.Composition;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.CompositionImpl;
import androidx.compose.runtime.CompositionKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNode;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.PointerInputModifierNode;
import androidx.compose.ui.node.UiApplier;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* loaded from: classes.dex */
public abstract class Wrapper_androidKt {
    public static final ViewGroup.LayoutParams DefaultLayoutParams = new ViewGroup.LayoutParams(-2, -2);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bc  */
    /* JADX WARN: Type inference failed for: r11v0, types: [android.view.ViewGroup, androidx.compose.ui.platform.AbstractComposeView] */
    /* JADX WARN: Type inference failed for: r11v8, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8, types: [androidx.compose.runtime.collection.MutableVector] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Composition setContent(AbstractComposeView abstractComposeView, CompositionContext compositionContext, ComposableLambdaImpl composableLambdaImpl) {
        AndroidComposeView androidComposeView;
        WrappedComposition wrappedComposition;
        CompositionContext compositionContext2;
        GlobalSnapshotManager.INSTANCE.getClass();
        if (GlobalSnapshotManager.started.compareAndSet(false, true)) {
            final BufferedChannel bufferedChannelChannel$default = ChannelKt.Channel$default(1, null, null, 6);
            AndroidUiDispatcher.Companion.getClass();
            BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope((CoroutineContext) AndroidUiDispatcher.Main$delegate.getValue()), null, null, new GlobalSnapshotManager$ensureStarted$1(bufferedChannelChannel$default, null), 3);
            Snapshot.Companion companion = Snapshot.Companion;
            Function1 function1 = new Function1() { // from class: androidx.compose.ui.platform.GlobalSnapshotManager$ensureStarted$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    if (GlobalSnapshotManager.sent.compareAndSet(false, true)) {
                        bufferedChannelChannel$default.mo3475trySendJP2dKIU(Unit.INSTANCE);
                    }
                    return Unit.INSTANCE;
                }
            };
            companion.getClass();
            synchronized (SnapshotKt.lock) {
                SnapshotKt.globalWriteObservers = CollectionsKt___CollectionsKt.plus(SnapshotKt.globalWriteObservers, function1);
                Unit unit = Unit.INSTANCE;
            }
            SnapshotKt.advanceGlobalSnapshot(SnapshotKt.emptyLambda);
        }
        if (abstractComposeView.getChildCount() > 0) {
            View childAt = abstractComposeView.getChildAt(0);
            androidComposeView = childAt instanceof AndroidComposeView ? (AndroidComposeView) childAt : null;
            if (androidComposeView == null) {
                androidComposeView = new AndroidComposeView(abstractComposeView.getContext(), compositionContext.getEffectCoroutineContext());
                abstractComposeView.addView(androidComposeView, DefaultLayoutParams);
            }
            Function1 function12 = InspectableValueKt.NoInspectorInfo;
            Object tag = androidComposeView.getTag(R.id.wrapped_composition_tag);
            wrappedComposition = !(tag instanceof WrappedComposition) ? (WrappedComposition) tag : null;
            if (wrappedComposition != null) {
                UiApplier uiApplier = new UiApplier(androidComposeView.root);
                Object obj = CompositionKt.PendingApplyNoModifications;
                compositionContext2 = compositionContext;
                wrappedComposition = new WrappedComposition(androidComposeView, new CompositionImpl(compositionContext2, uiApplier, null, 4, null));
                androidComposeView.setTag(R.id.wrapped_composition_tag, wrappedComposition);
            } else {
                compositionContext2 = compositionContext;
            }
            wrappedComposition.setContent(composableLambdaImpl);
            if (!Intrinsics.areEqual(androidComposeView.coroutineContext, compositionContext2.getEffectCoroutineContext())) {
                androidComposeView.coroutineContext = compositionContext2.getEffectCoroutineContext();
                ?? r11 = androidComposeView.root.nodes.head;
                if (r11 instanceof SuspendingPointerInputModifierNode) {
                    ((SuspendingPointerInputModifierNodeImpl) ((SuspendingPointerInputModifierNode) r11)).resetPointerInputHandler();
                }
                if (!r11.node.isAttached) {
                    InlineClassHelperKt.throwIllegalStateException("visitSubtreeIf called on an unattached node");
                }
                MutableVector mutableVector = new MutableVector(new Modifier.Node[16], 0);
                Modifier.Node node = r11.node;
                Modifier.Node node2 = node.child;
                if (node2 == null) {
                    DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node);
                } else {
                    mutableVector.add(node2);
                }
                while (true) {
                    int i = mutableVector.size;
                    if (i == 0) {
                        break;
                    }
                    Modifier.Node node3 = (Modifier.Node) mutableVector.removeAt(i - 1);
                    if ((node3.aggregateChildKindSet & 16) != 0) {
                        for (Modifier.Node node4 = node3; node4 != null; node4 = node4.child) {
                            if ((node4.kindSet & 16) != 0) {
                                DelegatingNode delegatingNodeAccess$pop = node4;
                                ?? mutableVector2 = 0;
                                while (delegatingNodeAccess$pop != 0) {
                                    if (delegatingNodeAccess$pop instanceof PointerInputModifierNode) {
                                        PointerInputModifierNode pointerInputModifierNode = (PointerInputModifierNode) delegatingNodeAccess$pop;
                                        if (pointerInputModifierNode instanceof SuspendingPointerInputModifierNode) {
                                            ((SuspendingPointerInputModifierNodeImpl) ((SuspendingPointerInputModifierNode) pointerInputModifierNode)).resetPointerInputHandler();
                                        }
                                    } else if ((delegatingNodeAccess$pop.kindSet & 16) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                        Modifier.Node node5 = delegatingNodeAccess$pop.delegate;
                                        int i2 = 0;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector2 = mutableVector2;
                                        while (node5 != null) {
                                            if ((node5.kindSet & 16) != 0) {
                                                i2++;
                                                mutableVector2 = mutableVector2;
                                                if (i2 == 1) {
                                                    delegatingNodeAccess$pop = node5;
                                                } else {
                                                    if (mutableVector2 == 0) {
                                                        mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                                    }
                                                    if (delegatingNodeAccess$pop != 0) {
                                                        mutableVector2.add(delegatingNodeAccess$pop);
                                                        delegatingNodeAccess$pop = 0;
                                                    }
                                                    mutableVector2.add(node5);
                                                }
                                            }
                                            node5 = node5.child;
                                            delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                            mutableVector2 = mutableVector2;
                                        }
                                        if (i2 == 1) {
                                        }
                                    }
                                    delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector2);
                                }
                            }
                        }
                    }
                    DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node3);
                }
            }
            return wrappedComposition;
        }
        abstractComposeView.removeAllViews();
        if (androidComposeView == null) {
        }
        Function1 function122 = InspectableValueKt.NoInspectorInfo;
        Object tag2 = androidComposeView.getTag(R.id.wrapped_composition_tag);
        if (!(tag2 instanceof WrappedComposition)) {
        }
        if (wrappedComposition != null) {
        }
        wrappedComposition.setContent(composableLambdaImpl);
        if (!Intrinsics.areEqual(androidComposeView.coroutineContext, compositionContext2.getEffectCoroutineContext())) {
        }
        return wrappedComposition;
    }
}
