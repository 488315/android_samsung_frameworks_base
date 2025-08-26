package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.DeltaCounter;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.MutabilityOwnership;
import java.util.Arrays;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class TrieNode<K, V> {
    public static final Companion Companion = new Companion(null);
    public static final TrieNode EMPTY = new TrieNode(0, 0, new Object[0]);
    public Object[] buffer;
    public int dataMap;
    public int nodeMap;
    public final MutabilityOwnership ownedBy;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class ModificationResult<K, V> {
        public TrieNode node;
        public final int sizeDelta;

        public ModificationResult(TrieNode<K, V> trieNode, int i) {
            this.node = trieNode;
            this.sizeDelta = i;
        }
    }

    public TrieNode(int i, int i2, Object[] objArr, MutabilityOwnership mutabilityOwnership) {
        this.dataMap = i;
        this.nodeMap = i2;
        this.ownedBy = mutabilityOwnership;
        this.buffer = objArr;
    }

    public static TrieNode makeNode(int i, Object obj, Object obj2, int i2, Object obj3, Object obj4, int i3, MutabilityOwnership mutabilityOwnership) {
        if (i3 > 30) {
            return new TrieNode(0, 0, new Object[]{obj, obj2, obj3, obj4}, mutabilityOwnership);
        }
        int iIndexSegment = TrieNodeKt.indexSegment(i, i3);
        int iIndexSegment2 = TrieNodeKt.indexSegment(i2, i3);
        if (iIndexSegment != iIndexSegment2) {
            return new TrieNode((1 << iIndexSegment) | (1 << iIndexSegment2), 0, iIndexSegment < iIndexSegment2 ? new Object[]{obj, obj2, obj3, obj4} : new Object[]{obj3, obj4, obj, obj2}, mutabilityOwnership);
        }
        return new TrieNode(0, 1 << iIndexSegment, new Object[]{makeNode(i, obj, obj2, i2, obj3, obj4, i3 + 5, mutabilityOwnership)}, mutabilityOwnership);
    }

    public final Object[] bufferMoveEntryToNode(int i, int i2, int i3, Object obj, Object obj2, int i4, MutabilityOwnership mutabilityOwnership) {
        Object obj3 = this.buffer[i];
        TrieNode trieNodeMakeNode = makeNode(obj3 != null ? obj3.hashCode() : 0, obj3, valueAtKeyIndex(i), i3, obj, obj2, i4 + 5, mutabilityOwnership);
        int iNodeIndex$runtime_release = nodeIndex$runtime_release(i2);
        int i5 = iNodeIndex$runtime_release + 1;
        Object[] objArr = this.buffer;
        Object[] objArr2 = new Object[objArr.length - 1];
        ArraysKt___ArraysJvmKt.copyInto$default(objArr, objArr2, 0, i, 6);
        ArraysKt___ArraysJvmKt.copyInto(objArr, objArr2, i, i + 2, i5);
        objArr2[iNodeIndex$runtime_release - 1] = trieNodeMakeNode;
        ArraysKt___ArraysJvmKt.copyInto(objArr, objArr2, iNodeIndex$runtime_release, i5, objArr.length);
        return objArr2;
    }

    public final int calculateSize() {
        if (this.nodeMap == 0) {
            return this.buffer.length / 2;
        }
        int iBitCount = Integer.bitCount(this.dataMap);
        int length = this.buffer.length;
        for (int i = iBitCount * 2; i < length; i++) {
            iBitCount += nodeAtIndex$runtime_release(i).calculateSize();
        }
        return iBitCount;
    }

    public final boolean collisionContainsKey(Object obj) {
        IntProgression intProgressionStep = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, this.buffer.length), 2);
        int i = intProgressionStep.first;
        int i2 = intProgressionStep.last;
        int i3 = intProgressionStep.step;
        if ((i3 > 0 && i <= i2) || (i3 < 0 && i2 <= i)) {
            while (!Intrinsics.areEqual(obj, this.buffer[i])) {
                if (i != i2) {
                    i += i3;
                }
            }
            return true;
        }
        return false;
    }

    public final boolean containsKey(int i, int i2, Object obj) {
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$runtime_release(iIndexSegment)) {
            return Intrinsics.areEqual(obj, this.buffer[entryKeyIndex$runtime_release(iIndexSegment)]);
        }
        if (!hasNodeAt(iIndexSegment)) {
            return false;
        }
        TrieNode trieNodeNodeAtIndex$runtime_release = nodeAtIndex$runtime_release(nodeIndex$runtime_release(iIndexSegment));
        return i2 == 30 ? trieNodeNodeAtIndex$runtime_release.collisionContainsKey(obj) : trieNodeNodeAtIndex$runtime_release.containsKey(i, i2 + 5, obj);
    }

    public final boolean elementsIdentityEquals(TrieNode trieNode) {
        if (this == trieNode) {
            return true;
        }
        if (this.nodeMap != trieNode.nodeMap || this.dataMap != trieNode.dataMap) {
            return false;
        }
        int length = this.buffer.length;
        for (int i = 0; i < length; i++) {
            if (this.buffer[i] != trieNode.buffer[i]) {
                return false;
            }
        }
        return true;
    }

    public final int entryKeyIndex$runtime_release(int i) {
        return Integer.bitCount(this.dataMap & (i - 1)) * 2;
    }

    public final Object get(int i, int i2, Object obj) {
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$runtime_release(iIndexSegment)) {
            int iEntryKeyIndex$runtime_release = entryKeyIndex$runtime_release(iIndexSegment);
            if (Intrinsics.areEqual(obj, this.buffer[iEntryKeyIndex$runtime_release])) {
                return valueAtKeyIndex(iEntryKeyIndex$runtime_release);
            }
            return null;
        }
        if (!hasNodeAt(iIndexSegment)) {
            return null;
        }
        TrieNode trieNodeNodeAtIndex$runtime_release = nodeAtIndex$runtime_release(nodeIndex$runtime_release(iIndexSegment));
        if (i2 != 30) {
            return trieNodeNodeAtIndex$runtime_release.get(i, i2 + 5, obj);
        }
        IntProgression intProgressionStep = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, trieNodeNodeAtIndex$runtime_release.buffer.length), 2);
        int i3 = intProgressionStep.first;
        int i4 = intProgressionStep.last;
        int i5 = intProgressionStep.step;
        if ((i5 <= 0 || i3 > i4) && (i5 >= 0 || i4 > i3)) {
            return null;
        }
        while (!Intrinsics.areEqual(obj, trieNodeNodeAtIndex$runtime_release.buffer[i3])) {
            if (i3 == i4) {
                return null;
            }
            i3 += i5;
        }
        return trieNodeNodeAtIndex$runtime_release.valueAtKeyIndex(i3);
    }

    public final boolean hasEntryAt$runtime_release(int i) {
        return (this.dataMap & i) != 0;
    }

    public final boolean hasNodeAt(int i) {
        return (this.nodeMap & i) != 0;
    }

    public final TrieNode mutableCollisionRemoveEntryAtIndex(int i, PersistentHashMapBuilder persistentHashMapBuilder) {
        persistentHashMapBuilder.setSize(persistentHashMapBuilder.size - 1);
        persistentHashMapBuilder.operationResult = valueAtKeyIndex(i);
        Object[] objArr = this.buffer;
        if (objArr.length == 2) {
            return null;
        }
        if (this.ownedBy != persistentHashMapBuilder.ownership) {
            return new TrieNode(0, 0, TrieNodeKt.access$removeEntryAtIndex(i, objArr), persistentHashMapBuilder.ownership);
        }
        this.buffer = TrieNodeKt.access$removeEntryAtIndex(i, objArr);
        return this;
    }

    public final TrieNode mutablePut(int i, Object obj, Object obj2, int i2, PersistentHashMapBuilder persistentHashMapBuilder) {
        PersistentHashMapBuilder persistentHashMapBuilder2;
        TrieNode trieNodeMutablePut;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        boolean zHasEntryAt$runtime_release = hasEntryAt$runtime_release(iIndexSegment);
        MutabilityOwnership mutabilityOwnership = this.ownedBy;
        if (zHasEntryAt$runtime_release) {
            int iEntryKeyIndex$runtime_release = entryKeyIndex$runtime_release(iIndexSegment);
            if (!Intrinsics.areEqual(obj, this.buffer[iEntryKeyIndex$runtime_release])) {
                persistentHashMapBuilder.setSize(persistentHashMapBuilder.size + 1);
                MutabilityOwnership mutabilityOwnership2 = persistentHashMapBuilder.ownership;
                if (mutabilityOwnership != mutabilityOwnership2) {
                    return new TrieNode(this.dataMap ^ iIndexSegment, this.nodeMap | iIndexSegment, bufferMoveEntryToNode(iEntryKeyIndex$runtime_release, iIndexSegment, i, obj, obj2, i2, mutabilityOwnership2), mutabilityOwnership2);
                }
                this.buffer = bufferMoveEntryToNode(iEntryKeyIndex$runtime_release, iIndexSegment, i, obj, obj2, i2, mutabilityOwnership2);
                this.dataMap ^= iIndexSegment;
                this.nodeMap |= iIndexSegment;
                return this;
            }
            persistentHashMapBuilder.operationResult = valueAtKeyIndex(iEntryKeyIndex$runtime_release);
            if (valueAtKeyIndex(iEntryKeyIndex$runtime_release) == obj2) {
                return this;
            }
            if (mutabilityOwnership == persistentHashMapBuilder.ownership) {
                this.buffer[iEntryKeyIndex$runtime_release + 1] = obj2;
                return this;
            }
            persistentHashMapBuilder.modCount++;
            Object[] objArr = this.buffer;
            Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
            objArrCopyOf[iEntryKeyIndex$runtime_release + 1] = obj2;
            return new TrieNode(this.dataMap, this.nodeMap, objArrCopyOf, persistentHashMapBuilder.ownership);
        }
        if (!hasNodeAt(iIndexSegment)) {
            persistentHashMapBuilder.setSize(persistentHashMapBuilder.size + 1);
            MutabilityOwnership mutabilityOwnership3 = persistentHashMapBuilder.ownership;
            int iEntryKeyIndex$runtime_release2 = entryKeyIndex$runtime_release(iIndexSegment);
            if (mutabilityOwnership != mutabilityOwnership3) {
                return new TrieNode(this.dataMap | iIndexSegment, this.nodeMap, TrieNodeKt.access$insertEntryAtIndex(this.buffer, iEntryKeyIndex$runtime_release2, obj, obj2), mutabilityOwnership3);
            }
            this.buffer = TrieNodeKt.access$insertEntryAtIndex(this.buffer, iEntryKeyIndex$runtime_release2, obj, obj2);
            this.dataMap |= iIndexSegment;
            return this;
        }
        int iNodeIndex$runtime_release = nodeIndex$runtime_release(iIndexSegment);
        TrieNode trieNodeNodeAtIndex$runtime_release = nodeAtIndex$runtime_release(iNodeIndex$runtime_release);
        if (i2 == 30) {
            IntProgression intProgressionStep = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, trieNodeNodeAtIndex$runtime_release.buffer.length), 2);
            int i3 = intProgressionStep.first;
            int i4 = intProgressionStep.last;
            int i5 = intProgressionStep.step;
            if ((i5 <= 0 || i3 > i4) && (i5 >= 0 || i4 > i3)) {
                persistentHashMapBuilder.setSize(persistentHashMapBuilder.size + 1);
                trieNodeMutablePut = new TrieNode(0, 0, TrieNodeKt.access$insertEntryAtIndex(trieNodeNodeAtIndex$runtime_release.buffer, 0, obj, obj2), persistentHashMapBuilder.ownership);
                persistentHashMapBuilder2 = persistentHashMapBuilder;
            } else {
                while (!Intrinsics.areEqual(obj, trieNodeNodeAtIndex$runtime_release.buffer[i3])) {
                    if (i3 == i4) {
                        persistentHashMapBuilder.setSize(persistentHashMapBuilder.size + 1);
                        trieNodeMutablePut = new TrieNode(0, 0, TrieNodeKt.access$insertEntryAtIndex(trieNodeNodeAtIndex$runtime_release.buffer, 0, obj, obj2), persistentHashMapBuilder.ownership);
                        break;
                    }
                    i3 += i5;
                }
                persistentHashMapBuilder.operationResult = trieNodeNodeAtIndex$runtime_release.valueAtKeyIndex(i3);
                if (trieNodeNodeAtIndex$runtime_release.ownedBy == persistentHashMapBuilder.ownership) {
                    trieNodeNodeAtIndex$runtime_release.buffer[i3 + 1] = obj2;
                    trieNodeMutablePut = trieNodeNodeAtIndex$runtime_release;
                } else {
                    persistentHashMapBuilder.modCount++;
                    Object[] objArr2 = trieNodeNodeAtIndex$runtime_release.buffer;
                    Object[] objArrCopyOf2 = Arrays.copyOf(objArr2, objArr2.length);
                    objArrCopyOf2[i3 + 1] = obj2;
                    trieNodeMutablePut = new TrieNode(0, 0, objArrCopyOf2, persistentHashMapBuilder.ownership);
                }
                persistentHashMapBuilder2 = persistentHashMapBuilder;
            }
        } else {
            persistentHashMapBuilder2 = persistentHashMapBuilder;
            trieNodeMutablePut = trieNodeNodeAtIndex$runtime_release.mutablePut(i, obj, obj2, i2 + 5, persistentHashMapBuilder2);
        }
        return trieNodeNodeAtIndex$runtime_release == trieNodeMutablePut ? this : mutableUpdateNodeAtIndex(iNodeIndex$runtime_release, trieNodeMutablePut, persistentHashMapBuilder2.ownership);
    }

    public final TrieNode mutablePutAll(TrieNode trieNode, int i, DeltaCounter deltaCounter, PersistentHashMapBuilder persistentHashMapBuilder) {
        Object[] objArr;
        TrieNode trieNodeMakeNode;
        if (this == trieNode) {
            deltaCounter.count += calculateSize();
            return this;
        }
        int i2 = 0;
        if (i > 30) {
            MutabilityOwnership mutabilityOwnership = persistentHashMapBuilder.ownership;
            int i3 = trieNode.nodeMap;
            Object[] objArr2 = this.buffer;
            Object[] objArrCopyOf = Arrays.copyOf(objArr2, objArr2.length + trieNode.buffer.length);
            int length = this.buffer.length;
            IntProgression intProgressionStep = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, trieNode.buffer.length), 2);
            int i4 = intProgressionStep.first;
            int i5 = intProgressionStep.last;
            int i6 = intProgressionStep.step;
            if ((i6 > 0 && i4 <= i5) || (i6 < 0 && i5 <= i4)) {
                while (true) {
                    if (collisionContainsKey(trieNode.buffer[i4])) {
                        deltaCounter.count++;
                    } else {
                        Object[] objArr3 = trieNode.buffer;
                        objArrCopyOf[length] = objArr3[i4];
                        objArrCopyOf[length + 1] = objArr3[i4 + 1];
                        length += 2;
                    }
                    if (i4 == i5) {
                        break;
                    }
                    i4 += i6;
                }
            }
            if (length != this.buffer.length) {
                return length == trieNode.buffer.length ? trieNode : length == objArrCopyOf.length ? new TrieNode(0, 0, objArrCopyOf, mutabilityOwnership) : new TrieNode(0, 0, Arrays.copyOf(objArrCopyOf, length), mutabilityOwnership);
            }
        } else {
            int i7 = this.nodeMap | trieNode.nodeMap;
            int i8 = this.dataMap;
            int i9 = trieNode.dataMap;
            int i10 = (i8 ^ i9) & (~i7);
            int i11 = i8 & i9;
            int i12 = i10;
            while (i11 != 0) {
                int iLowestOneBit = Integer.lowestOneBit(i11);
                if (Intrinsics.areEqual(this.buffer[entryKeyIndex$runtime_release(iLowestOneBit)], trieNode.buffer[trieNode.entryKeyIndex$runtime_release(iLowestOneBit)])) {
                    i12 |= iLowestOneBit;
                } else {
                    i7 |= iLowestOneBit;
                }
                i11 ^= iLowestOneBit;
            }
            if ((i7 & i12) != 0) {
                PreconditionsKt.throwIllegalStateException("Check failed.");
            }
            TrieNode trieNode2 = (Intrinsics.areEqual(this.ownedBy, persistentHashMapBuilder.ownership) && this.dataMap == i12 && this.nodeMap == i7) ? this : new TrieNode(i12, i7, new Object[Integer.bitCount(i7) + (Integer.bitCount(i12) * 2)]);
            int i13 = i7;
            int i14 = 0;
            while (i13 != 0) {
                int iLowestOneBit2 = Integer.lowestOneBit(i13);
                Object[] objArr4 = trieNode2.buffer;
                int length2 = (objArr4.length - 1) - i14;
                if (hasNodeAt(iLowestOneBit2)) {
                    trieNodeMakeNode = nodeAtIndex$runtime_release(nodeIndex$runtime_release(iLowestOneBit2));
                    if (trieNode.hasNodeAt(iLowestOneBit2)) {
                        trieNodeMakeNode = trieNodeMakeNode.mutablePutAll(trieNode.nodeAtIndex$runtime_release(trieNode.nodeIndex$runtime_release(iLowestOneBit2)), i + 5, deltaCounter, persistentHashMapBuilder);
                        objArr = objArr4;
                    } else if (trieNode.hasEntryAt$runtime_release(iLowestOneBit2)) {
                        int iEntryKeyIndex$runtime_release = trieNode.entryKeyIndex$runtime_release(iLowestOneBit2);
                        Object obj = trieNode.buffer[iEntryKeyIndex$runtime_release];
                        Object objValueAtKeyIndex = trieNode.valueAtKeyIndex(iEntryKeyIndex$runtime_release);
                        int i15 = persistentHashMapBuilder.size;
                        objArr = objArr4;
                        trieNodeMakeNode = trieNodeMakeNode.mutablePut(obj != null ? obj.hashCode() : i2, obj, objValueAtKeyIndex, i + 5, persistentHashMapBuilder);
                        if (persistentHashMapBuilder.size == i15) {
                            deltaCounter.count++;
                        }
                    } else {
                        objArr = objArr4;
                    }
                } else {
                    objArr = objArr4;
                    if (trieNode.hasNodeAt(iLowestOneBit2)) {
                        TrieNode trieNodeNodeAtIndex$runtime_release = trieNode.nodeAtIndex$runtime_release(trieNode.nodeIndex$runtime_release(iLowestOneBit2));
                        if (hasEntryAt$runtime_release(iLowestOneBit2)) {
                            int iEntryKeyIndex$runtime_release2 = entryKeyIndex$runtime_release(iLowestOneBit2);
                            Object obj2 = this.buffer[iEntryKeyIndex$runtime_release2];
                            int i16 = i + 5;
                            if (trieNodeNodeAtIndex$runtime_release.containsKey(obj2 != null ? obj2.hashCode() : 0, i16, obj2)) {
                                deltaCounter.count++;
                                trieNodeMakeNode = trieNodeNodeAtIndex$runtime_release;
                            } else {
                                trieNodeMakeNode = trieNodeNodeAtIndex$runtime_release.mutablePut(obj2 != null ? obj2.hashCode() : 0, obj2, valueAtKeyIndex(iEntryKeyIndex$runtime_release2), i16, persistentHashMapBuilder);
                            }
                        } else {
                            trieNodeMakeNode = trieNodeNodeAtIndex$runtime_release;
                        }
                    } else {
                        int iEntryKeyIndex$runtime_release3 = entryKeyIndex$runtime_release(iLowestOneBit2);
                        Object obj3 = this.buffer[iEntryKeyIndex$runtime_release3];
                        Object objValueAtKeyIndex2 = valueAtKeyIndex(iEntryKeyIndex$runtime_release3);
                        int iEntryKeyIndex$runtime_release4 = trieNode.entryKeyIndex$runtime_release(iLowestOneBit2);
                        Object obj4 = trieNode.buffer[iEntryKeyIndex$runtime_release4];
                        trieNodeMakeNode = makeNode(obj3 != null ? obj3.hashCode() : 0, obj3, objValueAtKeyIndex2, obj4 != null ? obj4.hashCode() : 0, obj4, trieNode.valueAtKeyIndex(iEntryKeyIndex$runtime_release4), i + 5, persistentHashMapBuilder.ownership);
                    }
                }
                objArr[length2] = trieNodeMakeNode;
                i14++;
                i13 ^= iLowestOneBit2;
                i2 = 0;
            }
            int i17 = 0;
            while (i12 != 0) {
                int iLowestOneBit3 = Integer.lowestOneBit(i12);
                int i18 = i17 * 2;
                if (trieNode.hasEntryAt$runtime_release(iLowestOneBit3)) {
                    int iEntryKeyIndex$runtime_release5 = trieNode.entryKeyIndex$runtime_release(iLowestOneBit3);
                    Object[] objArr5 = trieNode2.buffer;
                    objArr5[i18] = trieNode.buffer[iEntryKeyIndex$runtime_release5];
                    objArr5[i18 + 1] = trieNode.valueAtKeyIndex(iEntryKeyIndex$runtime_release5);
                    if (hasEntryAt$runtime_release(iLowestOneBit3)) {
                        deltaCounter.count++;
                    }
                } else {
                    int iEntryKeyIndex$runtime_release6 = entryKeyIndex$runtime_release(iLowestOneBit3);
                    Object[] objArr6 = trieNode2.buffer;
                    objArr6[i18] = this.buffer[iEntryKeyIndex$runtime_release6];
                    objArr6[i18 + 1] = valueAtKeyIndex(iEntryKeyIndex$runtime_release6);
                }
                i17++;
                i12 ^= iLowestOneBit3;
            }
            if (!elementsIdentityEquals(trieNode2)) {
                return trieNode.elementsIdentityEquals(trieNode2) ? trieNode : trieNode2;
            }
        }
        return this;
    }

    public final TrieNode mutableRemove(int i, Object obj, int i2, PersistentHashMapBuilder persistentHashMapBuilder) {
        TrieNode trieNodeMutableRemove;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$runtime_release(iIndexSegment)) {
            int iEntryKeyIndex$runtime_release = entryKeyIndex$runtime_release(iIndexSegment);
            if (Intrinsics.areEqual(obj, this.buffer[iEntryKeyIndex$runtime_release])) {
                return mutableRemoveEntryAtIndex(iEntryKeyIndex$runtime_release, iIndexSegment, persistentHashMapBuilder);
            }
        } else if (hasNodeAt(iIndexSegment)) {
            int iNodeIndex$runtime_release = nodeIndex$runtime_release(iIndexSegment);
            TrieNode trieNodeNodeAtIndex$runtime_release = nodeAtIndex$runtime_release(iNodeIndex$runtime_release);
            if (i2 == 30) {
                IntProgression intProgressionStep = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, trieNodeNodeAtIndex$runtime_release.buffer.length), 2);
                int i3 = intProgressionStep.first;
                int i4 = intProgressionStep.last;
                int i5 = intProgressionStep.step;
                if ((i5 <= 0 || i3 > i4) && (i5 >= 0 || i4 > i3)) {
                    trieNodeMutableRemove = trieNodeNodeAtIndex$runtime_release;
                    break;
                }
                while (!Intrinsics.areEqual(obj, trieNodeNodeAtIndex$runtime_release.buffer[i3])) {
                    if (i3 == i4) {
                        trieNodeMutableRemove = trieNodeNodeAtIndex$runtime_release;
                        break;
                    }
                    i3 += i5;
                }
                trieNodeMutableRemove = trieNodeNodeAtIndex$runtime_release.mutableCollisionRemoveEntryAtIndex(i3, persistentHashMapBuilder);
            } else {
                trieNodeMutableRemove = trieNodeNodeAtIndex$runtime_release.mutableRemove(i, obj, i2 + 5, persistentHashMapBuilder);
            }
            return mutableReplaceNode(trieNodeNodeAtIndex$runtime_release, trieNodeMutableRemove, iNodeIndex$runtime_release, iIndexSegment, persistentHashMapBuilder.ownership);
        }
        return this;
    }

    public final TrieNode mutableRemoveEntryAtIndex(int i, int i2, PersistentHashMapBuilder persistentHashMapBuilder) {
        persistentHashMapBuilder.setSize(persistentHashMapBuilder.size - 1);
        persistentHashMapBuilder.operationResult = valueAtKeyIndex(i);
        Object[] objArr = this.buffer;
        if (objArr.length == 2) {
            return null;
        }
        if (this.ownedBy != persistentHashMapBuilder.ownership) {
            return new TrieNode(i2 ^ this.dataMap, this.nodeMap, TrieNodeKt.access$removeEntryAtIndex(i, objArr), persistentHashMapBuilder.ownership);
        }
        this.buffer = TrieNodeKt.access$removeEntryAtIndex(i, objArr);
        this.dataMap ^= i2;
        return this;
    }

    public final TrieNode mutableReplaceNode(TrieNode trieNode, TrieNode trieNode2, int i, int i2, MutabilityOwnership mutabilityOwnership) {
        MutabilityOwnership mutabilityOwnership2 = this.ownedBy;
        if (trieNode2 != null) {
            return (mutabilityOwnership2 == mutabilityOwnership || trieNode != trieNode2) ? mutableUpdateNodeAtIndex(i, trieNode2, mutabilityOwnership) : this;
        }
        Object[] objArr = this.buffer;
        if (objArr.length == 1) {
            return null;
        }
        if (mutabilityOwnership2 != mutabilityOwnership) {
            return new TrieNode(this.dataMap, this.nodeMap ^ i2, TrieNodeKt.access$removeNodeAtIndex(i, objArr), mutabilityOwnership);
        }
        this.buffer = TrieNodeKt.access$removeNodeAtIndex(i, objArr);
        this.nodeMap ^= i2;
        return this;
    }

    public final TrieNode mutableUpdateNodeAtIndex(int i, TrieNode trieNode, MutabilityOwnership mutabilityOwnership) {
        Object[] objArr = this.buffer;
        if (objArr.length == 1 && trieNode.buffer.length == 2 && trieNode.nodeMap == 0) {
            trieNode.dataMap = this.nodeMap;
            return trieNode;
        }
        if (this.ownedBy == mutabilityOwnership) {
            objArr[i] = trieNode;
            return this;
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        objArrCopyOf[i] = trieNode;
        return new TrieNode(this.dataMap, this.nodeMap, objArrCopyOf, mutabilityOwnership);
    }

    public final TrieNode nodeAtIndex$runtime_release(int i) {
        return (TrieNode) this.buffer[i];
    }

    public final int nodeIndex$runtime_release(int i) {
        return (this.buffer.length - 1) - Integer.bitCount(this.nodeMap & (i - 1));
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00c8, code lost:
    
        if (r12 != null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d1, code lost:
    
        if (r12 == null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00d4, code lost:
    
        r12.node = updateNodeAtIndex(r10, r4, r12.node);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00dc, code lost:
    
        return r12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ModificationResult put(int i, int i2, Object obj, Object obj2) {
        ModificationResult modificationResult;
        ModificationResult modificationResultPut;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$runtime_release(iIndexSegment)) {
            modificationResult = null;
            int iEntryKeyIndex$runtime_release = entryKeyIndex$runtime_release(iIndexSegment);
            if (!Intrinsics.areEqual(obj, this.buffer[iEntryKeyIndex$runtime_release])) {
                return new ModificationResult(new TrieNode(this.dataMap ^ iIndexSegment, this.nodeMap | iIndexSegment, bufferMoveEntryToNode(iEntryKeyIndex$runtime_release, iIndexSegment, i, obj, obj2, i2, null)), 1);
            }
            if (valueAtKeyIndex(iEntryKeyIndex$runtime_release) != obj2) {
                Object[] objArr = this.buffer;
                Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
                objArrCopyOf[iEntryKeyIndex$runtime_release + 1] = obj2;
                return new ModificationResult(new TrieNode(this.dataMap, this.nodeMap, objArrCopyOf), 0);
            }
        } else {
            modificationResult = null;
            if (!hasNodeAt(iIndexSegment)) {
                return new ModificationResult(new TrieNode(this.dataMap | iIndexSegment, this.nodeMap, TrieNodeKt.access$insertEntryAtIndex(this.buffer, entryKeyIndex$runtime_release(iIndexSegment), obj, obj2)), 1);
            }
            int iNodeIndex$runtime_release = nodeIndex$runtime_release(iIndexSegment);
            TrieNode trieNodeNodeAtIndex$runtime_release = nodeAtIndex$runtime_release(iNodeIndex$runtime_release);
            if (i2 == 30) {
                IntProgression intProgressionStep = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, trieNodeNodeAtIndex$runtime_release.buffer.length), 2);
                int i3 = intProgressionStep.first;
                int i4 = intProgressionStep.last;
                int i5 = intProgressionStep.step;
                if ((i5 > 0 && i3 <= i4) || (i5 < 0 && i4 <= i3)) {
                    while (!Intrinsics.areEqual(obj, trieNodeNodeAtIndex$runtime_release.buffer[i3])) {
                        if (i3 != i4) {
                            i3 += i5;
                        }
                    }
                    if (obj2 == trieNodeNodeAtIndex$runtime_release.valueAtKeyIndex(i3)) {
                        modificationResultPut = null;
                    } else {
                        Object[] objArr2 = trieNodeNodeAtIndex$runtime_release.buffer;
                        Object[] objArrCopyOf2 = Arrays.copyOf(objArr2, objArr2.length);
                        objArrCopyOf2[i3 + 1] = obj2;
                        modificationResultPut = new ModificationResult(new TrieNode(0, 0, objArrCopyOf2), 0);
                    }
                }
                modificationResultPut = new ModificationResult(new TrieNode(0, 0, TrieNodeKt.access$insertEntryAtIndex(trieNodeNodeAtIndex$runtime_release.buffer, 0, obj, obj2)), 1);
                break;
            }
            modificationResultPut = trieNodeNodeAtIndex$runtime_release.put(i, i2 + 5, obj, obj2);
        }
        return modificationResult;
    }

    public final TrieNode remove(int i, int i2, Object obj) {
        TrieNode trieNodeRemove;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$runtime_release(iIndexSegment)) {
            int iEntryKeyIndex$runtime_release = entryKeyIndex$runtime_release(iIndexSegment);
            if (!Intrinsics.areEqual(obj, this.buffer[iEntryKeyIndex$runtime_release])) {
                return this;
            }
            Object[] objArr = this.buffer;
            if (objArr.length != 2) {
                return new TrieNode(this.dataMap ^ iIndexSegment, this.nodeMap, TrieNodeKt.access$removeEntryAtIndex(iEntryKeyIndex$runtime_release, objArr));
            }
        } else {
            if (!hasNodeAt(iIndexSegment)) {
                return this;
            }
            int iNodeIndex$runtime_release = nodeIndex$runtime_release(iIndexSegment);
            TrieNode trieNodeNodeAtIndex$runtime_release = nodeAtIndex$runtime_release(iNodeIndex$runtime_release);
            if (i2 == 30) {
                IntProgression intProgressionStep = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, trieNodeNodeAtIndex$runtime_release.buffer.length), 2);
                int i3 = intProgressionStep.first;
                int i4 = intProgressionStep.last;
                int i5 = intProgressionStep.step;
                if ((i5 <= 0 || i3 > i4) && (i5 >= 0 || i4 > i3)) {
                    trieNodeRemove = trieNodeNodeAtIndex$runtime_release;
                    break;
                }
                while (!Intrinsics.areEqual(obj, trieNodeNodeAtIndex$runtime_release.buffer[i3])) {
                    if (i3 == i4) {
                        trieNodeRemove = trieNodeNodeAtIndex$runtime_release;
                        break;
                    }
                    i3 += i5;
                }
                Object[] objArr2 = trieNodeNodeAtIndex$runtime_release.buffer;
                trieNodeRemove = objArr2.length == 2 ? null : new TrieNode(0, 0, TrieNodeKt.access$removeEntryAtIndex(i3, objArr2));
            } else {
                trieNodeRemove = trieNodeNodeAtIndex$runtime_release.remove(i, i2 + 5, obj);
            }
            if (trieNodeRemove != null) {
                return trieNodeNodeAtIndex$runtime_release != trieNodeRemove ? updateNodeAtIndex(iNodeIndex$runtime_release, iIndexSegment, trieNodeRemove) : this;
            }
            Object[] objArr3 = this.buffer;
            if (objArr3.length != 1) {
                return new TrieNode(this.dataMap, this.nodeMap ^ iIndexSegment, TrieNodeKt.access$removeNodeAtIndex(iNodeIndex$runtime_release, objArr3));
            }
        }
        return null;
    }

    public final TrieNode updateNodeAtIndex(int i, int i2, TrieNode trieNode) {
        Object[] objArr = trieNode.buffer;
        if (objArr.length != 2 || trieNode.nodeMap != 0) {
            Object[] objArr2 = this.buffer;
            Object[] objArrCopyOf = Arrays.copyOf(objArr2, objArr2.length);
            objArrCopyOf[i] = trieNode;
            return new TrieNode(this.dataMap, this.nodeMap, objArrCopyOf);
        }
        if (this.buffer.length == 1) {
            trieNode.dataMap = this.nodeMap;
            return trieNode;
        }
        int iEntryKeyIndex$runtime_release = entryKeyIndex$runtime_release(i2);
        Object[] objArr3 = this.buffer;
        Object obj = objArr[0];
        Object obj2 = objArr[1];
        Object[] objArrCopyOf2 = Arrays.copyOf(objArr3, objArr3.length + 1);
        ArraysKt___ArraysJvmKt.copyInto(objArrCopyOf2, objArrCopyOf2, i + 2, i + 1, objArr3.length);
        ArraysKt___ArraysJvmKt.copyInto(objArrCopyOf2, objArrCopyOf2, iEntryKeyIndex$runtime_release + 2, iEntryKeyIndex$runtime_release, i);
        objArrCopyOf2[iEntryKeyIndex$runtime_release] = obj;
        objArrCopyOf2[iEntryKeyIndex$runtime_release + 1] = obj2;
        return new TrieNode(this.dataMap ^ i2, this.nodeMap ^ i2, objArrCopyOf2);
    }

    public final Object valueAtKeyIndex(int i) {
        return this.buffer[i + 1];
    }

    public TrieNode(int i, int i2, Object[] objArr) {
        this(i, i2, objArr, null);
    }

    public final TrieNode mutableRemove(int i, Object obj, Object obj2, int i2, PersistentHashMapBuilder persistentHashMapBuilder) {
        PersistentHashMapBuilder persistentHashMapBuilder2;
        TrieNode trieNodeMutableRemove;
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i2);
        if (hasEntryAt$runtime_release(iIndexSegment)) {
            int iEntryKeyIndex$runtime_release = entryKeyIndex$runtime_release(iIndexSegment);
            return (Intrinsics.areEqual(obj, this.buffer[iEntryKeyIndex$runtime_release]) && Intrinsics.areEqual(obj2, valueAtKeyIndex(iEntryKeyIndex$runtime_release))) ? mutableRemoveEntryAtIndex(iEntryKeyIndex$runtime_release, iIndexSegment, persistentHashMapBuilder) : this;
        }
        if (!hasNodeAt(iIndexSegment)) {
            return this;
        }
        int iNodeIndex$runtime_release = nodeIndex$runtime_release(iIndexSegment);
        TrieNode trieNodeNodeAtIndex$runtime_release = nodeAtIndex$runtime_release(iNodeIndex$runtime_release);
        if (i2 == 30) {
            IntProgression intProgressionStep = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, trieNodeNodeAtIndex$runtime_release.buffer.length), 2);
            int i3 = intProgressionStep.first;
            int i4 = intProgressionStep.last;
            int i5 = intProgressionStep.step;
            if ((i5 <= 0 || i3 > i4) && (i5 >= 0 || i4 > i3)) {
                trieNodeMutableRemove = trieNodeNodeAtIndex$runtime_release;
                persistentHashMapBuilder2 = persistentHashMapBuilder;
            } else {
                while (true) {
                    if (!Intrinsics.areEqual(obj, trieNodeNodeAtIndex$runtime_release.buffer[i3]) || !Intrinsics.areEqual(obj2, trieNodeNodeAtIndex$runtime_release.valueAtKeyIndex(i3))) {
                        if (i3 == i4) {
                            break;
                        }
                        i3 += i5;
                    } else {
                        trieNodeMutableRemove = trieNodeNodeAtIndex$runtime_release.mutableCollisionRemoveEntryAtIndex(i3, persistentHashMapBuilder);
                        break;
                    }
                }
                trieNodeMutableRemove = trieNodeNodeAtIndex$runtime_release;
                persistentHashMapBuilder2 = persistentHashMapBuilder;
            }
        } else {
            persistentHashMapBuilder2 = persistentHashMapBuilder;
            trieNodeMutableRemove = trieNodeNodeAtIndex$runtime_release.mutableRemove(i, obj, obj2, i2 + 5, persistentHashMapBuilder2);
        }
        return mutableReplaceNode(trieNodeNodeAtIndex$runtime_release, trieNodeMutableRemove, iNodeIndex$runtime_release, iIndexSegment, persistentHashMapBuilder2.ownership);
    }
}
