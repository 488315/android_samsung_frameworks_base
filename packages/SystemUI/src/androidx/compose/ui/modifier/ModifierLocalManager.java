package androidx.compose.ui.modifier;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.BackwardsCompatNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.platform.AndroidComposeView;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class ModifierLocalManager {
    public boolean invalidated;
    public final Owner owner;
    public final MutableVector inserted = new MutableVector(new BackwardsCompatNode[16], 0);
    public final MutableVector insertedLocal = new MutableVector(new ModifierLocal[16], 0);
    public final MutableVector removed = new MutableVector(new LayoutNode[16], 0);
    public final MutableVector removedLocal = new MutableVector(new ModifierLocal[16], 0);

    public ModifierLocalManager(Owner owner) {
        this.owner = owner;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r5v7, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    public static void invalidateConsumersOfNodeForKey(Modifier.Node node, ModifierLocal modifierLocal, Set set) {
        if (!node.node.isAttached) {
            InlineClassHelperKt.throwIllegalStateException("visitSubtreeIf called on an unattached node");
        }
        MutableVector mutableVector = new MutableVector(new Modifier.Node[16], 0);
        Modifier.Node node2 = node.node;
        Modifier.Node node3 = node2.child;
        if (node3 == null) {
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node2);
        } else {
            mutableVector.add(node3);
        }
        while (true) {
            int i = mutableVector.size;
            if (i == 0) {
                return;
            }
            Modifier.Node node4 = (Modifier.Node) mutableVector.removeAt(i - 1);
            if ((node4.aggregateChildKindSet & 32) != 0) {
                for (Modifier.Node node5 = node4; node5 != null; node5 = node5.child) {
                    if ((node5.kindSet & 32) != 0) {
                        DelegatingNode delegatingNodeAccess$pop = node5;
                        ?? mutableVector2 = 0;
                        while (delegatingNodeAccess$pop != 0) {
                            if (delegatingNodeAccess$pop instanceof ModifierLocalModifierNode) {
                                ModifierLocalModifierNode modifierLocalModifierNode = (ModifierLocalModifierNode) delegatingNodeAccess$pop;
                                if (modifierLocalModifierNode instanceof BackwardsCompatNode) {
                                    BackwardsCompatNode backwardsCompatNode = (BackwardsCompatNode) modifierLocalModifierNode;
                                    if ((backwardsCompatNode.element instanceof ModifierLocalConsumer) && backwardsCompatNode.readValues.contains(modifierLocal)) {
                                        ((HashSet) set).add(modifierLocalModifierNode);
                                    }
                                }
                                if (modifierLocalModifierNode.getProvidedValues().contains$ui_release(modifierLocal)) {
                                    break;
                                }
                            } else if ((delegatingNodeAccess$pop.kindSet & 32) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                Modifier.Node node6 = delegatingNodeAccess$pop.delegate;
                                int i2 = 0;
                                delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                mutableVector2 = mutableVector2;
                                while (node6 != null) {
                                    if ((node6.kindSet & 32) != 0) {
                                        i2++;
                                        mutableVector2 = mutableVector2;
                                        if (i2 == 1) {
                                            delegatingNodeAccess$pop = node6;
                                        } else {
                                            if (mutableVector2 == 0) {
                                                mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (delegatingNodeAccess$pop != 0) {
                                                mutableVector2.add(delegatingNodeAccess$pop);
                                                delegatingNodeAccess$pop = 0;
                                            }
                                            mutableVector2.add(node6);
                                        }
                                    }
                                    node6 = node6.child;
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
            DelegatableNodeKt.access$addLayoutNodeChildren(mutableVector, node4);
        }
    }

    public final void invalidate() {
        if (this.invalidated) {
            return;
        }
        this.invalidated = true;
        ((AndroidComposeView) this.owner).registerOnEndApplyChangesListener(new Function0() { // from class: androidx.compose.ui.modifier.ModifierLocalManager.invalidate.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MutableVector mutableVector;
                MutableVector mutableVector2;
                ModifierLocalManager modifierLocalManager = ModifierLocalManager.this;
                int i = 0;
                modifierLocalManager.invalidated = false;
                HashSet hashSet = new HashSet();
                MutableVector mutableVector3 = modifierLocalManager.removed;
                Object[] objArr = mutableVector3.content;
                int i2 = mutableVector3.size;
                int i3 = 0;
                while (true) {
                    mutableVector = modifierLocalManager.removedLocal;
                    if (i3 >= i2) {
                        break;
                    }
                    LayoutNode layoutNode = (LayoutNode) objArr[i3];
                    ModifierLocal modifierLocal = (ModifierLocal) mutableVector.content[i3];
                    Modifier.Node node = layoutNode.nodes.head;
                    if (node.isAttached) {
                        ModifierLocalManager.invalidateConsumersOfNodeForKey(node, modifierLocal, hashSet);
                    }
                    i3++;
                }
                mutableVector3.clear();
                mutableVector.clear();
                MutableVector mutableVector4 = modifierLocalManager.inserted;
                Object[] objArr2 = mutableVector4.content;
                int i4 = mutableVector4.size;
                while (true) {
                    mutableVector2 = modifierLocalManager.insertedLocal;
                    if (i >= i4) {
                        break;
                    }
                    BackwardsCompatNode backwardsCompatNode = (BackwardsCompatNode) objArr2[i];
                    ModifierLocal modifierLocal2 = (ModifierLocal) mutableVector2.content[i];
                    if (backwardsCompatNode.isAttached) {
                        ModifierLocalManager.invalidateConsumersOfNodeForKey(backwardsCompatNode, modifierLocal2, hashSet);
                    }
                    i++;
                }
                mutableVector4.clear();
                mutableVector2.clear();
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    ((BackwardsCompatNode) it.next()).updateModifierLocalConsumer();
                }
                return Unit.INSTANCE;
            }
        });
    }
}
