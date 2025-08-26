package androidx.compose.runtime;

import androidx.collection.MutableIntList;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.Composer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SlotWriter {
    public static final Companion Companion = new Companion(null);
    public ArrayList anchors;
    public MutableIntObjectMap calledByMap;
    public boolean closed;
    public int currentGroup;
    public int currentGroupEnd;
    public int currentSlot;
    public int currentSlotEnd;
    public MutableIntObjectMap deferredSlotWrites;
    public final IntStack endStack;
    public int groupGapLen;
    public int groupGapStart;
    public int[] groups;
    public int insertCount;
    public int nodeCount;
    public final IntStack nodeCountStack;
    public int parent;
    public MutableIntList pendingRecalculateMarks;
    public Object[] slots;
    public int slotsGapLen;
    public int slotsGapOwner;
    public int slotsGapStart;
    public HashMap sourceInformationMap;
    public final IntStack startStack;
    public final SlotTable table;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static List moveGroup(SlotWriter slotWriter, int i, SlotWriter slotWriter2, boolean z, boolean z2, boolean z3) {
            EmptyList emptyList;
            EmptyList emptyList2;
            boolean zRemoveGroups;
            int i2;
            Anchor anchorTryAnchor$runtime_release;
            int i3;
            int i4;
            int iGroupSize = slotWriter.groupSize(i);
            int i5 = i + iGroupSize;
            int iDataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i), slotWriter.groups);
            int iDataIndex2 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(i5), slotWriter.groups);
            int i6 = iDataIndex2 - iDataIndex;
            boolean z4 = i >= 0 && (slotWriter.groups[(slotWriter.groupIndexToAddress(i) * 5) + 1] & 201326592) != 0;
            slotWriter2.insertGroups(iGroupSize);
            slotWriter2.insertSlots(i6, slotWriter2.currentGroup);
            if (slotWriter.groupGapStart < i5) {
                slotWriter.moveGroupGapTo(i5);
            }
            if (slotWriter.slotsGapStart < iDataIndex2) {
                slotWriter.moveSlotGapTo(iDataIndex2, i5);
            }
            int[] iArr = slotWriter2.groups;
            int i7 = slotWriter2.currentGroup;
            int i8 = i7 * 5;
            ArraysKt___ArraysJvmKt.copyInto(i8, i * 5, i5 * 5, slotWriter.groups, iArr);
            Object[] objArr = slotWriter2.slots;
            int i9 = slotWriter2.currentSlot;
            System.arraycopy(slotWriter.slots, iDataIndex, objArr, i9, i6);
            int i10 = slotWriter2.parent;
            iArr[i8 + 2] = i10;
            int i11 = i7 - i;
            int i12 = i7 + iGroupSize;
            int iDataIndex3 = i9 - slotWriter2.dataIndex(i7, iArr);
            int i13 = slotWriter2.slotsGapOwner;
            int i14 = slotWriter2.slotsGapLen;
            int length = objArr.length;
            boolean z5 = z4;
            int i15 = i13;
            int i16 = i7;
            while (i16 < i12) {
                if (i16 != i7) {
                    int i17 = (i16 * 5) + 2;
                    iArr[i17] = iArr[i17] + i11;
                }
                int[] iArr2 = iArr;
                int iDataIndex4 = slotWriter2.dataIndex(i16, iArr) + iDataIndex3;
                if (i15 < i16) {
                    i3 = i7;
                    i4 = 0;
                } else {
                    i3 = i7;
                    i4 = slotWriter2.slotsGapStart;
                }
                iArr2[(i16 * 5) + 4] = SlotWriter.dataIndexToDataAnchor(iDataIndex4, i4, i14, length);
                if (i16 == i15) {
                    i15++;
                }
                i16++;
                i7 = i3;
                iArr = iArr2;
            }
            int[] iArr3 = iArr;
            slotWriter2.slotsGapOwner = i15;
            int iAccess$locationOf = SlotTableKt.access$locationOf(slotWriter.anchors, i, slotWriter.getSize$runtime_release());
            int iAccess$locationOf2 = SlotTableKt.access$locationOf(slotWriter.anchors, i5, slotWriter.getSize$runtime_release());
            if (iAccess$locationOf < iAccess$locationOf2) {
                ArrayList arrayList = slotWriter.anchors;
                ArrayList arrayList2 = new ArrayList(iAccess$locationOf2 - iAccess$locationOf);
                for (int i18 = iAccess$locationOf; i18 < iAccess$locationOf2; i18++) {
                    Anchor anchor = (Anchor) arrayList.get(i18);
                    anchor.location += i11;
                    arrayList2.add(anchor);
                }
                slotWriter2.anchors.addAll(SlotTableKt.access$locationOf(slotWriter2.anchors, slotWriter2.currentGroup, slotWriter2.getSize$runtime_release()), arrayList2);
                arrayList.subList(iAccess$locationOf, iAccess$locationOf2).clear();
                emptyList = arrayList2;
            } else {
                emptyList = EmptyList.INSTANCE;
            }
            EmptyList emptyList3 = emptyList;
            if (!emptyList3.isEmpty()) {
                HashMap map = slotWriter.sourceInformationMap;
                HashMap map2 = slotWriter2.sourceInformationMap;
                if (map != null && map2 != null) {
                    int size = emptyList3.size();
                    for (int i19 = 0; i19 < size; i19++) {
                        Anchor anchor2 = (Anchor) emptyList.get(i19);
                        GroupSourceInformation groupSourceInformation = (GroupSourceInformation) map.get(anchor2);
                        if (groupSourceInformation != null) {
                            map.remove(anchor2);
                            map2.put(anchor2, groupSourceInformation);
                        }
                    }
                }
            }
            int i20 = slotWriter2.parent;
            GroupSourceInformation groupSourceInformationSourceInformationOf$runtime_release = slotWriter2.sourceInformationOf$runtime_release(i10);
            if (groupSourceInformationSourceInformationOf$runtime_release != null) {
                int i21 = i20 + 1;
                int i22 = slotWriter2.currentGroup;
                int i23 = -1;
                while (i21 < i22) {
                    i23 = i21;
                    i21 = slotWriter2.groups[(i21 * 5) + 3] + i21;
                }
                ArrayList arrayList3 = groupSourceInformationSourceInformationOf$runtime_release.groups;
                if (arrayList3 == null) {
                    arrayList3 = new ArrayList();
                    groupSourceInformationSourceInformationOf$runtime_release.groups = arrayList3;
                }
                if (i23 < 0 || (anchorTryAnchor$runtime_release = slotWriter2.tryAnchor$runtime_release(i23)) == null) {
                    emptyList2 = emptyList;
                    i2 = 0;
                } else {
                    int size2 = arrayList3.size();
                    int i24 = 0;
                    EmptyList emptyList4 = emptyList;
                    while (i24 < size2) {
                        Object obj = arrayList3.get(i24);
                        if (Intrinsics.areEqual(obj, anchorTryAnchor$runtime_release)) {
                            emptyList2 = emptyList4;
                        } else {
                            emptyList2 = emptyList4;
                            if (!(obj instanceof GroupSourceInformation) || !((GroupSourceInformation) obj).hasAnchor(anchorTryAnchor$runtime_release)) {
                                i24++;
                                emptyList4 = emptyList2;
                            }
                        }
                        i2 = i24;
                        break;
                    }
                    emptyList2 = emptyList4;
                    i2 = -1;
                }
                arrayList3.add(i2, slotWriter2.anchor(i22));
            } else {
                emptyList2 = emptyList;
            }
            int iParent = slotWriter.parent(i, slotWriter.groups);
            if (!z3) {
                zRemoveGroups = false;
            } else if (z) {
                boolean z6 = iParent >= 0;
                if (z6) {
                    slotWriter.startGroup();
                    slotWriter.advanceBy(iParent - slotWriter.currentGroup);
                    slotWriter.startGroup();
                }
                slotWriter.advanceBy(i - slotWriter.currentGroup);
                boolean zRemoveGroup = slotWriter.removeGroup();
                if (z6) {
                    slotWriter.skipToGroupEnd();
                    slotWriter.endGroup();
                    slotWriter.skipToGroupEnd();
                    slotWriter.endGroup();
                }
                zRemoveGroups = zRemoveGroup;
            } else {
                zRemoveGroups = slotWriter.removeGroups(i, iGroupSize);
                slotWriter.removeSlots(iDataIndex, i6, i - 1);
            }
            if (zRemoveGroups) {
                ComposerKt.composeImmediateRuntimeError("Unexpectedly removed anchors");
            }
            int i25 = slotWriter2.nodeCount;
            int i26 = iArr3[i8 + 1];
            slotWriter2.nodeCount = i25 + ((1073741824 & i26) == 0 ? i26 & 67108863 : 1);
            if (z2) {
                slotWriter2.currentGroup = i12;
                slotWriter2.currentSlot = i9 + i6;
            }
            if (z5) {
                slotWriter2.updateContainsMark(i10);
            }
            return emptyList2;
        }

        private Companion() {
        }
    }

    public SlotWriter(SlotTable slotTable) {
        this.table = slotTable;
        int[] iArr = slotTable.groups;
        this.groups = iArr;
        Object[] objArr = slotTable.slots;
        this.slots = objArr;
        this.anchors = slotTable.anchors;
        this.sourceInformationMap = slotTable.sourceInformationMap;
        this.calledByMap = slotTable.calledByMap;
        int i = slotTable.groupsSize;
        this.groupGapStart = i;
        this.groupGapLen = (iArr.length / 5) - i;
        int i2 = slotTable.slotsSize;
        this.slotsGapStart = i2;
        this.slotsGapLen = objArr.length - i2;
        this.slotsGapOwner = i;
        this.startStack = new IntStack();
        this.endStack = new IntStack();
        this.nodeCountStack = new IntStack();
        this.currentGroupEnd = slotTable.groupsSize;
        this.parent = -1;
    }

    public static int dataIndexToDataAnchor(int i, int i2, int i3, int i4) {
        return i > i2 ? -(((i4 - i3) - i) + 1) : i;
    }

    public static void markGroup$default(SlotWriter slotWriter) {
        int i = slotWriter.parent;
        int iGroupIndexToAddress = slotWriter.groupIndexToAddress(i);
        int[] iArr = slotWriter.groups;
        int i2 = (iGroupIndexToAddress * 5) + 1;
        int i3 = iArr[i2];
        if ((i3 & 134217728) != 0) {
            return;
        }
        int i4 = (i3 & (-134217729)) | 134217728;
        iArr[i2] = i4;
        if ((67108864 & i4) != 0) {
            return;
        }
        slotWriter.updateContainsMark(slotWriter.parent(i, iArr));
    }

    public final void advanceBy(int i) {
        boolean z = false;
        if (!(i >= 0)) {
            ComposerKt.composeImmediateRuntimeError("Cannot seek backwards");
        }
        if (!(this.insertCount <= 0)) {
            PreconditionsKt.throwIllegalStateException("Cannot call seek() while inserting");
        }
        if (i == 0) {
            return;
        }
        int i2 = this.currentGroup + i;
        if (i2 >= this.parent && i2 <= this.currentGroupEnd) {
            z = true;
        }
        if (!z) {
            ComposerKt.composeImmediateRuntimeError("Cannot seek outside the current group (" + this.parent + '-' + this.currentGroupEnd + ')');
        }
        this.currentGroup = i2;
        int iDataIndex = dataIndex(groupIndexToAddress(i2), this.groups);
        this.currentSlot = iDataIndex;
        this.currentSlotEnd = iDataIndex;
    }

    public final Anchor anchor(int i) {
        ArrayList arrayList = this.anchors;
        int iSearch = SlotTableKt.search(arrayList, i, getSize$runtime_release());
        if (iSearch >= 0) {
            return (Anchor) arrayList.get(iSearch);
        }
        if (i > this.groupGapStart) {
            i = -(getSize$runtime_release() - i);
        }
        Anchor anchor = new Anchor(i);
        arrayList.add(-(iSearch + 1), anchor);
        return anchor;
    }

    public final int anchorIndex(Anchor anchor) {
        int i = anchor.location;
        return i < 0 ? getSize$runtime_release() + i : i;
    }

    public final void beginInsert() {
        int i = this.insertCount;
        this.insertCount = i + 1;
        if (i == 0) {
            this.endStack.push((getCapacity() - this.groupGapLen) - this.currentGroupEnd);
        }
    }

    public final void close(boolean z) {
        this.closed = true;
        if (z && this.startStack.tos == 0) {
            moveGroupGapTo(getSize$runtime_release());
            moveSlotGapTo(this.slots.length - this.slotsGapLen, this.groupGapStart);
            int i = this.slotsGapStart;
            Arrays.fill(this.slots, i, this.slotsGapLen + i, (Object) null);
            recalculateMarks();
        }
        int[] iArr = this.groups;
        int i2 = this.groupGapStart;
        Object[] objArr = this.slots;
        int i3 = this.slotsGapStart;
        ArrayList arrayList = this.anchors;
        HashMap map = this.sourceInformationMap;
        MutableIntObjectMap mutableIntObjectMap = this.calledByMap;
        SlotTable slotTable = this.table;
        slotTable.getClass();
        if (!slotTable.writer) {
            PreconditionsKt.throwIllegalArgumentException("Unexpected writer close()");
        }
        slotTable.writer = false;
        slotTable.groups = iArr;
        slotTable.groupsSize = i2;
        slotTable.slots = objArr;
        slotTable.slotsSize = i3;
        slotTable.anchors = arrayList;
        slotTable.sourceInformationMap = map;
        slotTable.calledByMap = mutableIntObjectMap;
    }

    public final int dataIndex(int i, int[] iArr) {
        if (i >= getCapacity()) {
            return this.slots.length - this.slotsGapLen;
        }
        int i2 = iArr[(i * 5) + 4];
        return i2 < 0 ? (this.slots.length - this.slotsGapLen) + i2 + 1 : i2;
    }

    public final int dataIndexToDataAddress(int i) {
        return (this.slotsGapLen * (i < this.slotsGapStart ? 0 : 1)) + i;
    }

    public final void endGroup() {
        MutableObjectList mutableObjectList;
        boolean z = this.insertCount > 0;
        int i = this.currentGroup;
        int i2 = this.currentGroupEnd;
        int i3 = this.parent;
        int iGroupIndexToAddress = groupIndexToAddress(i3);
        int i4 = this.nodeCount;
        int i5 = i - i3;
        int i6 = iGroupIndexToAddress * 5;
        int i7 = i6 + 1;
        boolean z2 = (this.groups[i7] & 1073741824) != 0;
        IntStack intStack = this.nodeCountStack;
        if (z) {
            MutableIntObjectMap mutableIntObjectMap = this.deferredSlotWrites;
            if (mutableIntObjectMap != null && (mutableObjectList = (MutableObjectList) mutableIntObjectMap.get(i3)) != null) {
                Object[] objArr = mutableObjectList.content;
                int i8 = mutableObjectList._size;
                for (int i9 = 0; i9 < i8; i9++) {
                    rawUpdate(objArr[i9]);
                }
            }
            int[] iArr = this.groups;
            iArr[i6 + 3] = i5;
            SlotTableKt.access$updateNodeCount(iGroupIndexToAddress, i4, iArr);
            int iPop = intStack.pop();
            if (z2) {
                i4 = 1;
            }
            this.nodeCount = iPop + i4;
            int iParent = parent(i3, this.groups);
            this.parent = iParent;
            int size$runtime_release = iParent < 0 ? getSize$runtime_release() : groupIndexToAddress(iParent + 1);
            int iDataIndex = size$runtime_release >= 0 ? dataIndex(size$runtime_release, this.groups) : 0;
            this.currentSlot = iDataIndex;
            this.currentSlotEnd = iDataIndex;
            return;
        }
        if (i != i2) {
            ComposerKt.composeImmediateRuntimeError("Expected to be at the end of a group");
        }
        int[] iArr2 = this.groups;
        int i10 = i6 + 3;
        int i11 = iArr2[i10];
        int i12 = iArr2[i7] & 67108863;
        iArr2[i10] = i5;
        SlotTableKt.access$updateNodeCount(iGroupIndexToAddress, i4, iArr2);
        int iPop2 = this.startStack.pop();
        this.currentGroupEnd = (getCapacity() - this.groupGapLen) - this.endStack.pop();
        this.parent = iPop2;
        int iParent2 = parent(i3, this.groups);
        int iPop3 = intStack.pop();
        this.nodeCount = iPop3;
        if (iParent2 == iPop2) {
            this.nodeCount = iPop3 + (z2 ? 0 : i4 - i12);
            return;
        }
        int i13 = i5 - i11;
        int i14 = z2 ? 0 : i4 - i12;
        if (i13 != 0 || i14 != 0) {
            while (iParent2 != 0 && iParent2 != iPop2 && (i14 != 0 || i13 != 0)) {
                int iGroupIndexToAddress2 = groupIndexToAddress(iParent2);
                if (i13 != 0) {
                    int[] iArr3 = this.groups;
                    int i15 = (iGroupIndexToAddress2 * 5) + 3;
                    iArr3[i15] = iArr3[i15] + i13;
                }
                if (i14 != 0) {
                    int[] iArr4 = this.groups;
                    SlotTableKt.access$updateNodeCount(iGroupIndexToAddress2, (iArr4[(iGroupIndexToAddress2 * 5) + 1] & 67108863) + i14, iArr4);
                }
                int[] iArr5 = this.groups;
                if ((iArr5[(iGroupIndexToAddress2 * 5) + 1] & 1073741824) != 0) {
                    i14 = 0;
                }
                iParent2 = parent(iParent2, iArr5);
            }
        }
        this.nodeCount += i14;
    }

    public final void endInsert() {
        if (this.insertCount <= 0) {
            PreconditionsKt.throwIllegalStateException("Unbalanced begin/end insert");
        }
        int i = this.insertCount - 1;
        this.insertCount = i;
        if (i == 0) {
            if (this.nodeCountStack.tos != this.startStack.tos) {
                ComposerKt.composeImmediateRuntimeError("startGroup/endGroup mismatch while inserting");
            }
            this.currentGroupEnd = (getCapacity() - this.groupGapLen) - this.endStack.pop();
        }
    }

    public final void ensureStarted(int i) {
        boolean z = false;
        if (!(this.insertCount <= 0)) {
            ComposerKt.composeImmediateRuntimeError("Cannot call ensureStarted() while inserting");
        }
        int i2 = this.parent;
        if (i2 != i) {
            if (i >= i2 && i < this.currentGroupEnd) {
                z = true;
            }
            if (!z) {
                ComposerKt.composeImmediateRuntimeError("Started group at " + i + " must be a subgroup of the group at " + i2);
            }
            int i3 = this.currentGroup;
            int i4 = this.currentSlot;
            int i5 = this.currentSlotEnd;
            this.currentGroup = i;
            startGroup();
            this.currentGroup = i3;
            this.currentSlot = i4;
            this.currentSlotEnd = i5;
        }
    }

    public final void fixParentAnchorsFor(int i, int i2, int i3) {
        if (i >= this.groupGapStart) {
            i = -((getSize$runtime_release() - i) + 2);
        }
        while (i3 < i2) {
            this.groups[(groupIndexToAddress(i3) * 5) + 2] = i;
            int i4 = this.groups[(groupIndexToAddress(i3) * 5) + 3] + i3;
            fixParentAnchorsFor(i3, i4, i3 + 1);
            i3 = i4;
        }
    }

    public final int getCapacity() {
        return this.groups.length / 5;
    }

    public final int getSize$runtime_release() {
        return getCapacity() - this.groupGapLen;
    }

    public final int getSlotsSize() {
        return this.slots.length - this.slotsGapLen;
    }

    public final Object groupAux(int i) {
        int iGroupIndexToAddress = groupIndexToAddress(i);
        int[] iArr = this.groups;
        int i2 = (iGroupIndexToAddress * 5) + 1;
        if ((iArr[i2] & 268435456) == 0) {
            Composer.Companion.getClass();
            return Composer.Companion.Empty;
        }
        return this.slots[Integer.bitCount(iArr[i2] >> 29) + dataIndex(iGroupIndexToAddress, iArr)];
    }

    public final int groupIndexToAddress(int i) {
        return (this.groupGapLen * (i < this.groupGapStart ? 0 : 1)) + i;
    }

    public final Object groupObjectKey(int i) {
        int iGroupIndexToAddress = groupIndexToAddress(i);
        int[] iArr = this.groups;
        int i2 = iGroupIndexToAddress * 5;
        int i3 = iArr[i2 + 1];
        if ((536870912 & i3) == 0) {
            return null;
        }
        return this.slots[Integer.bitCount(i3 >> 30) + iArr[i2 + 4]];
    }

    public final int groupSize(int i) {
        return SlotTableKt.access$groupSize(groupIndexToAddress(i), this.groups);
    }

    public final boolean indexInGroup(int i, int i2) {
        int capacity;
        int iGroupSize;
        if (i2 == this.parent) {
            capacity = this.currentGroupEnd;
        } else {
            IntStack intStack = this.startStack;
            if (i2 > intStack.peekOr(0)) {
                iGroupSize = groupSize(i2);
            } else {
                int[] iArr = intStack.slots;
                int iMin = Math.min(iArr.length, intStack.tos);
                int i3 = 0;
                while (true) {
                    if (i3 >= iMin) {
                        i3 = -1;
                        break;
                    }
                    if (iArr[i3] == i2) {
                        break;
                    }
                    i3++;
                }
                if (i3 < 0) {
                    iGroupSize = groupSize(i2);
                } else {
                    capacity = (getCapacity() - this.groupGapLen) - this.endStack.slots[i3];
                }
            }
            capacity = iGroupSize + i2;
        }
        return i > i2 && i < capacity;
    }

    public final void insertGroups(int i) {
        if (i > 0) {
            int i2 = this.currentGroup;
            moveGroupGapTo(i2);
            int i3 = this.groupGapStart;
            int i4 = this.groupGapLen;
            int[] iArr = this.groups;
            int length = iArr.length / 5;
            int i5 = length - i4;
            if (i4 < i) {
                int iMax = Math.max(Math.max(length * 2, i5 + i), 32);
                int[] iArr2 = new int[iMax * 5];
                int i6 = iMax - i5;
                ArraysKt___ArraysJvmKt.copyInto(0, 0, i3 * 5, iArr, iArr2);
                ArraysKt___ArraysJvmKt.copyInto((i3 + i6) * 5, (i4 + i3) * 5, length * 5, iArr, iArr2);
                this.groups = iArr2;
                i4 = i6;
            }
            int i7 = this.currentGroupEnd;
            if (i7 >= i3) {
                this.currentGroupEnd = i7 + i;
            }
            int i8 = i3 + i;
            this.groupGapStart = i8;
            this.groupGapLen = i4 - i;
            int iDataIndexToDataAnchor = dataIndexToDataAnchor(i5 > 0 ? dataIndex(groupIndexToAddress(i2 + i), this.groups) : 0, this.slotsGapOwner >= i3 ? this.slotsGapStart : 0, this.slotsGapLen, this.slots.length);
            for (int i9 = i3; i9 < i8; i9++) {
                this.groups[(i9 * 5) + 4] = iDataIndexToDataAnchor;
            }
            int i10 = this.slotsGapOwner;
            if (i10 >= i3) {
                this.slotsGapOwner = i10 + i;
            }
        }
    }

    public final void insertSlots(int i, int i2) {
        if (i > 0) {
            moveSlotGapTo(this.currentSlot, i2);
            int i3 = this.slotsGapStart;
            int i4 = this.slotsGapLen;
            if (i4 < i) {
                Object[] objArr = this.slots;
                int length = objArr.length;
                int i5 = length - i4;
                int iMax = Math.max(Math.max(length * 2, i5 + i), 32);
                Object[] objArr2 = new Object[iMax];
                for (int i6 = 0; i6 < iMax; i6++) {
                    objArr2[i6] = null;
                }
                int i7 = iMax - i5;
                int i8 = i4 + i3;
                System.arraycopy(objArr, 0, objArr2, 0, i3);
                System.arraycopy(objArr, i8, objArr2, i3 + i7, length - i8);
                this.slots = objArr2;
                i4 = i7;
            }
            int i9 = this.currentSlotEnd;
            if (i9 >= i3) {
                this.currentSlotEnd = i9 + i;
            }
            this.slotsGapStart = i3 + i;
            this.slotsGapLen = i4 - i;
        }
    }

    public final boolean isNode(int i) {
        return (this.groups[(groupIndexToAddress(i) * 5) + 1] & 1073741824) != 0;
    }

    public final void moveFrom(SlotTable slotTable, int i) {
        if (this.insertCount <= 0) {
            ComposerKt.composeImmediateRuntimeError("Check failed");
        }
        if (i == 0 && this.currentGroup == 0 && this.table.groupsSize == 0) {
            int[] iArr = slotTable.groups;
            int i2 = iArr[(i * 5) + 3];
            int i3 = slotTable.groupsSize;
            if (i2 == i3) {
                int[] iArr2 = this.groups;
                Object[] objArr = this.slots;
                ArrayList arrayList = this.anchors;
                HashMap map = this.sourceInformationMap;
                MutableIntObjectMap mutableIntObjectMap = this.calledByMap;
                Object[] objArr2 = slotTable.slots;
                int i4 = slotTable.slotsSize;
                HashMap map2 = slotTable.sourceInformationMap;
                MutableIntObjectMap mutableIntObjectMap2 = slotTable.calledByMap;
                this.groups = iArr;
                this.slots = objArr2;
                this.anchors = slotTable.anchors;
                this.groupGapStart = i3;
                this.groupGapLen = (iArr.length / 5) - i3;
                this.slotsGapStart = i4;
                this.slotsGapLen = objArr2.length - i4;
                this.slotsGapOwner = i3;
                this.sourceInformationMap = map2;
                this.calledByMap = mutableIntObjectMap2;
                slotTable.groups = iArr2;
                slotTable.groupsSize = 0;
                slotTable.slots = objArr;
                slotTable.slotsSize = 0;
                slotTable.anchors = arrayList;
                slotTable.sourceInformationMap = map;
                slotTable.calledByMap = mutableIntObjectMap;
                return;
            }
        }
        SlotWriter slotWriterOpenWriter = slotTable.openWriter();
        try {
            Companion.getClass();
            Companion.moveGroup(slotWriterOpenWriter, i, this, true, true, false);
            slotWriterOpenWriter.close(true);
        } catch (Throwable th) {
            slotWriterOpenWriter.close(false);
            throw th;
        }
    }

    public final void moveGroupGapTo(int i) {
        Anchor anchor;
        int i2;
        Anchor anchor2;
        int i3;
        int i4;
        int i5 = this.groupGapLen;
        int i6 = this.groupGapStart;
        if (i6 != i) {
            if (!this.anchors.isEmpty()) {
                int capacity = getCapacity() - this.groupGapLen;
                if (i6 < i) {
                    for (int iAccess$locationOf = SlotTableKt.access$locationOf(this.anchors, i6, capacity); iAccess$locationOf < this.anchors.size() && (i3 = (anchor2 = (Anchor) this.anchors.get(iAccess$locationOf)).location) < 0 && (i4 = i3 + capacity) < i; iAccess$locationOf++) {
                        anchor2.location = i4;
                    }
                } else {
                    for (int iAccess$locationOf2 = SlotTableKt.access$locationOf(this.anchors, i, capacity); iAccess$locationOf2 < this.anchors.size() && (i2 = (anchor = (Anchor) this.anchors.get(iAccess$locationOf2)).location) >= 0; iAccess$locationOf2++) {
                        anchor.location = -(capacity - i2);
                    }
                }
            }
            if (i5 > 0) {
                int[] iArr = this.groups;
                int i7 = i * 5;
                int i8 = i5 * 5;
                int i9 = i6 * 5;
                if (i < i6) {
                    ArraysKt___ArraysJvmKt.copyInto(i8 + i7, i7, i9, iArr, iArr);
                } else {
                    ArraysKt___ArraysJvmKt.copyInto(i9, i9 + i8, i7 + i8, iArr, iArr);
                }
            }
            if (i < i6) {
                i6 = i + i5;
            }
            int capacity2 = getCapacity();
            if (i6 >= capacity2) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            while (i6 < capacity2) {
                int i10 = (i6 * 5) + 2;
                int i11 = this.groups[i10];
                int size$runtime_release = i11 > -2 ? i11 : (getSize$runtime_release() + i11) - (-2);
                if (size$runtime_release >= i) {
                    size$runtime_release = -((getSize$runtime_release() - size$runtime_release) - (-2));
                }
                if (size$runtime_release != i11) {
                    this.groups[i10] = size$runtime_release;
                }
                i6++;
                if (i6 == i) {
                    i6 += i5;
                }
            }
        }
        this.groupGapStart = i;
    }

    public final void moveSlotGapTo(int i, int i2) {
        int i3 = this.slotsGapLen;
        int i4 = this.slotsGapStart;
        int i5 = this.slotsGapOwner;
        if (i4 != i) {
            Object[] objArr = this.slots;
            if (i < i4) {
                System.arraycopy(objArr, i, objArr, i + i3, i4 - i);
            } else {
                int i6 = i4 + i3;
                System.arraycopy(objArr, i6, objArr, i4, (i + i3) - i6);
            }
        }
        int iMin = Math.min(i2 + 1, getSize$runtime_release());
        if (i5 != iMin) {
            int length = this.slots.length - i3;
            if (iMin < i5) {
                int iGroupIndexToAddress = groupIndexToAddress(iMin);
                int iGroupIndexToAddress2 = groupIndexToAddress(i5);
                int i7 = this.groupGapStart;
                while (iGroupIndexToAddress < iGroupIndexToAddress2) {
                    int i8 = (iGroupIndexToAddress * 5) + 4;
                    int i9 = this.groups[i8];
                    if (!(i9 >= 0)) {
                        ComposerKt.composeImmediateRuntimeError("Unexpected anchor value, expected a positive anchor");
                    }
                    this.groups[i8] = -((length - i9) + 1);
                    iGroupIndexToAddress++;
                    if (iGroupIndexToAddress == i7) {
                        iGroupIndexToAddress += this.groupGapLen;
                    }
                }
            } else {
                int iGroupIndexToAddress3 = groupIndexToAddress(i5);
                int iGroupIndexToAddress4 = groupIndexToAddress(iMin);
                while (iGroupIndexToAddress3 < iGroupIndexToAddress4) {
                    int i10 = (iGroupIndexToAddress3 * 5) + 4;
                    int i11 = this.groups[i10];
                    if (!(i11 < 0)) {
                        ComposerKt.composeImmediateRuntimeError("Unexpected anchor value, expected a negative anchor");
                    }
                    this.groups[i10] = i11 + length + 1;
                    iGroupIndexToAddress3++;
                    if (iGroupIndexToAddress3 == this.groupGapStart) {
                        iGroupIndexToAddress3 += this.groupGapLen;
                    }
                }
            }
            this.slotsGapOwner = iMin;
        }
        this.slotsGapStart = i;
    }

    public final List moveTo(Anchor anchor, SlotWriter slotWriter) {
        if (!(slotWriter.insertCount > 0)) {
            ComposerKt.composeImmediateRuntimeError("Check failed");
        }
        if (this.insertCount != 0) {
            ComposerKt.composeImmediateRuntimeError("Check failed");
        }
        if (!anchor.getValid()) {
            ComposerKt.composeImmediateRuntimeError("Check failed");
        }
        int iAnchorIndex = anchorIndex(anchor) + 1;
        int i = this.currentGroup;
        if (i > iAnchorIndex || iAnchorIndex >= this.currentGroupEnd) {
            ComposerKt.composeImmediateRuntimeError("Check failed");
        }
        int iParent = parent(iAnchorIndex, this.groups);
        int iGroupSize = groupSize(iAnchorIndex);
        int iNodeCount = isNode(iAnchorIndex) ? 1 : nodeCount(iAnchorIndex);
        Companion.getClass();
        List listMoveGroup = Companion.moveGroup(this, iAnchorIndex, slotWriter, false, false, true);
        updateContainsMark(iParent);
        boolean z = iNodeCount > 0;
        while (iParent >= i) {
            int iGroupIndexToAddress = groupIndexToAddress(iParent);
            int[] iArr = this.groups;
            int i2 = iGroupIndexToAddress * 5;
            int i3 = i2 + 3;
            iArr[i3] = iArr[i3] - iGroupSize;
            if (z) {
                int i4 = iArr[i2 + 1];
                if ((1073741824 & i4) != 0) {
                    z = false;
                } else {
                    SlotTableKt.access$updateNodeCount(iGroupIndexToAddress, (i4 & 67108863) - iNodeCount, iArr);
                }
            }
            iParent = parent(iParent, this.groups);
        }
        if (z) {
            if (this.nodeCount < iNodeCount) {
                ComposerKt.composeImmediateRuntimeError("Check failed");
            }
            this.nodeCount -= iNodeCount;
        }
        return listMoveGroup;
    }

    public final Object node(int i) {
        int iGroupIndexToAddress = groupIndexToAddress(i);
        int[] iArr = this.groups;
        if ((iArr[(iGroupIndexToAddress * 5) + 1] & 1073741824) != 0) {
            return this.slots[dataIndexToDataAddress(dataIndex(iGroupIndexToAddress, iArr))];
        }
        return null;
    }

    public final int nodeCount(int i) {
        return this.groups[(groupIndexToAddress(i) * 5) + 1] & 67108863;
    }

    public final int parent(int i, int[] iArr) {
        int i2 = iArr[(groupIndexToAddress(i) * 5) + 2];
        return i2 > -2 ? i2 : (getSize$runtime_release() + i2) - (-2);
    }

    public final Object rawUpdate(Object obj) {
        if (this.insertCount > 0) {
            insertSlots(1, this.parent);
        }
        Object[] objArr = this.slots;
        int i = this.currentSlot;
        this.currentSlot = i + 1;
        Object obj2 = objArr[dataIndexToDataAddress(i)];
        if (this.currentSlot > this.currentSlotEnd) {
            ComposerKt.composeImmediateRuntimeError("Writing to an invalid slot");
        }
        this.slots[dataIndexToDataAddress(this.currentSlot - 1)] = obj;
        return obj2;
    }

    public final void recalculateMarks() {
        int i;
        MutableIntList mutableIntList = this.pendingRecalculateMarks;
        if (mutableIntList != null) {
            while (mutableIntList._size != 0) {
                int iM335takeMaximpl = PrioritySet.m335takeMaximpl(mutableIntList);
                int iGroupIndexToAddress = groupIndexToAddress(iM335takeMaximpl);
                int iGroupSize = iM335takeMaximpl + 1;
                int iGroupSize2 = groupSize(iM335takeMaximpl) + iM335takeMaximpl;
                while (true) {
                    if (iGroupSize >= iGroupSize2) {
                        i = 0;
                        break;
                    } else {
                        if ((this.groups[(groupIndexToAddress(iGroupSize) * 5) + 1] & 201326592) != 0) {
                            i = 1;
                            break;
                        }
                        iGroupSize += groupSize(iGroupSize);
                    }
                }
                int[] iArr = this.groups;
                int i2 = (iGroupIndexToAddress * 5) + 1;
                int i3 = iArr[i2];
                if (((67108864 & i3) == 0 ? 0 : 1) != i) {
                    iArr[i2] = (i << 26) | ((-67108865) & i3);
                    int iParent = parent(iM335takeMaximpl, iArr);
                    if (iParent >= 0) {
                        PrioritySet.m334addimpl(mutableIntList, iParent);
                    }
                }
            }
        }
    }

    public final boolean removeGroup() {
        Anchor anchorTryAnchor$runtime_release;
        if (this.insertCount != 0) {
            ComposerKt.composeImmediateRuntimeError("Cannot remove group while inserting");
        }
        int i = this.currentGroup;
        int i2 = this.currentSlot;
        int iDataIndex = dataIndex(groupIndexToAddress(i), this.groups);
        int iSkipGroup = skipGroup();
        GroupSourceInformation groupSourceInformationSourceInformationOf$runtime_release = sourceInformationOf$runtime_release(this.parent);
        if (groupSourceInformationSourceInformationOf$runtime_release != null && (anchorTryAnchor$runtime_release = tryAnchor$runtime_release(i)) != null) {
            groupSourceInformationSourceInformationOf$runtime_release.removeAnchor(anchorTryAnchor$runtime_release);
        }
        MutableIntList mutableIntList = this.pendingRecalculateMarks;
        if (mutableIntList != null) {
            while (true) {
                int i3 = mutableIntList._size;
                if (i3 == 0) {
                    break;
                }
                if (i3 == 0) {
                    throw new NoSuchElementException("IntList is empty.");
                }
                if (mutableIntList.content[0] < i) {
                    break;
                }
                PrioritySet.m335takeMaximpl(mutableIntList);
            }
        }
        boolean zRemoveGroups = removeGroups(i, this.currentGroup - i);
        removeSlots(iDataIndex, this.currentSlot - iDataIndex, i - 1);
        this.currentGroup = i;
        this.currentSlot = i2;
        this.nodeCount -= iSkipGroup;
        return zRemoveGroups;
    }

    public final boolean removeGroups(int i, int i2) {
        if (i2 > 0) {
            ArrayList arrayList = this.anchors;
            moveGroupGapTo(i);
            if (!arrayList.isEmpty()) {
                HashMap map = this.sourceInformationMap;
                int i3 = i + i2;
                int iAccess$locationOf = SlotTableKt.access$locationOf(this.anchors, i3, getCapacity() - this.groupGapLen);
                if (iAccess$locationOf >= this.anchors.size()) {
                    iAccess$locationOf--;
                }
                int i4 = iAccess$locationOf + 1;
                int i5 = 0;
                while (iAccess$locationOf >= 0) {
                    Anchor anchor = (Anchor) this.anchors.get(iAccess$locationOf);
                    int iAnchorIndex = anchorIndex(anchor);
                    if (iAnchorIndex < i) {
                        break;
                    }
                    if (iAnchorIndex < i3) {
                        anchor.location = Integer.MIN_VALUE;
                        if (map != null) {
                        }
                        if (i5 == 0) {
                            i5 = iAccess$locationOf + 1;
                        }
                        i4 = iAccess$locationOf;
                    }
                    iAccess$locationOf--;
                }
                z = i4 < i5;
                if (z) {
                    this.anchors.subList(i4, i5).clear();
                }
            }
            this.groupGapStart = i;
            this.groupGapLen += i2;
            int i6 = this.slotsGapOwner;
            if (i6 > i) {
                this.slotsGapOwner = Math.max(i, i6 - i2);
            }
            int i7 = this.currentGroupEnd;
            if (i7 >= this.groupGapStart) {
                this.currentGroupEnd = i7 - i2;
            }
            int i8 = this.parent;
            if (i8 >= 0 && (this.groups[(groupIndexToAddress(i8) * 5) + 1] & 67108864) != 0) {
                updateContainsMark(i8);
            }
        }
        return z;
    }

    public final void removeSlots(int i, int i2, int i3) {
        if (i2 > 0) {
            int i4 = this.slotsGapLen;
            int i5 = i + i2;
            moveSlotGapTo(i5, i3);
            this.slotsGapStart = i;
            this.slotsGapLen = i4 + i2;
            Arrays.fill(this.slots, i, i5, (Object) null);
            int i6 = this.currentSlotEnd;
            if (i6 >= i) {
                this.currentSlotEnd = i6 - i2;
            }
        }
    }

    public final int skipGroup() {
        int iGroupIndexToAddress = groupIndexToAddress(this.currentGroup);
        int iAccess$groupSize = SlotTableKt.access$groupSize(iGroupIndexToAddress, this.groups) + this.currentGroup;
        this.currentGroup = iAccess$groupSize;
        this.currentSlot = dataIndex(groupIndexToAddress(iAccess$groupSize), this.groups);
        int i = this.groups[(iGroupIndexToAddress * 5) + 1];
        if ((1073741824 & i) != 0) {
            return 1;
        }
        return i & 67108863;
    }

    public final void skipToGroupEnd() {
        int i = this.currentGroupEnd;
        this.currentGroup = i;
        this.currentSlot = dataIndex(groupIndexToAddress(i), this.groups);
    }

    public final int slotIndex(int i, int[] iArr) {
        if (i >= getCapacity()) {
            return this.slots.length - this.slotsGapLen;
        }
        int iAccess$slotAnchor = SlotTableKt.access$slotAnchor(i, iArr);
        return iAccess$slotAnchor < 0 ? (this.slots.length - this.slotsGapLen) + iAccess$slotAnchor + 1 : iAccess$slotAnchor;
    }

    public final int slotIndexOfGroupSlotIndex(int i, int i2) {
        int iSlotIndex = slotIndex(groupIndexToAddress(i), this.groups);
        int i3 = iSlotIndex + i2;
        if (!(i3 >= iSlotIndex && i3 < dataIndex(groupIndexToAddress(i + 1), this.groups))) {
            ComposerKt.composeImmediateRuntimeError("Write to an invalid slot index " + i2 + " for group " + i);
        }
        return i3;
    }

    public final int slotsEndAllIndex$runtime_release(int i) {
        return dataIndex(groupIndexToAddress(groupSize(i) + i), this.groups);
    }

    public final GroupSourceInformation sourceInformationOf$runtime_release(int i) {
        Anchor anchorTryAnchor$runtime_release;
        HashMap map = this.sourceInformationMap;
        if (map == null || (anchorTryAnchor$runtime_release = tryAnchor$runtime_release(i)) == null) {
            return null;
        }
        return (GroupSourceInformation) map.get(anchorTryAnchor$runtime_release);
    }

    public final void startGroup() {
        if (this.insertCount != 0) {
            ComposerKt.composeImmediateRuntimeError("Key must be supplied when inserting");
        }
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        startGroup(0, composer$Companion$Empty$1, false, composer$Companion$Empty$1);
    }

    public final String toString() {
        return "SlotWriter(current = " + this.currentGroup + " end=" + this.currentGroupEnd + " size = " + getSize$runtime_release() + " gap=" + this.groupGapStart + '-' + (this.groupGapStart + this.groupGapLen) + ')';
    }

    public final Anchor tryAnchor$runtime_release(int i) {
        ArrayList arrayList;
        int iSearch;
        if (i < 0 || i >= getSize$runtime_release() || (iSearch = SlotTableKt.search((arrayList = this.anchors), i, getSize$runtime_release())) < 0) {
            return null;
        }
        return (Anchor) arrayList.get(iSearch);
    }

    public final void update(Object obj) {
        if (this.insertCount <= 0 || this.currentSlot == this.slotsGapStart) {
            rawUpdate(obj);
            return;
        }
        MutableIntObjectMap mutableIntObjectMap = this.deferredSlotWrites;
        DefaultConstructorMarker defaultConstructorMarker = null;
        int i = 1;
        int i2 = 0;
        if (mutableIntObjectMap == null) {
            mutableIntObjectMap = new MutableIntObjectMap(i2, i, defaultConstructorMarker);
        }
        this.deferredSlotWrites = mutableIntObjectMap;
        int i3 = this.parent;
        Object mutableObjectList = mutableIntObjectMap.get(i3);
        if (mutableObjectList == null) {
            mutableObjectList = new MutableObjectList(i2, i, defaultConstructorMarker);
            mutableIntObjectMap.set(i3, mutableObjectList);
        }
        ((MutableObjectList) mutableObjectList).add(obj);
        Composer.Companion.getClass();
        Composer.Companion companion = Composer.Companion.$$INSTANCE;
    }

    public final void updateAux(Object obj) {
        int iGroupIndexToAddress = groupIndexToAddress(this.currentGroup);
        int i = (iGroupIndexToAddress * 5) + 1;
        if ((this.groups[i] & 268435456) == 0) {
            ComposerKt.composeImmediateRuntimeError("Updating the data of a group that was not created with a data slot");
        }
        Object[] objArr = this.slots;
        int[] iArr = this.groups;
        objArr[dataIndexToDataAddress(Integer.bitCount(iArr[i] >> 29) + dataIndex(iGroupIndexToAddress, iArr))] = obj;
    }

    public final void updateContainsMark(int i) {
        if (i >= 0) {
            MutableIntList mutableIntList = this.pendingRecalculateMarks;
            if (mutableIntList == null) {
                mutableIntList = new MutableIntList(0, 1, null);
                this.pendingRecalculateMarks = mutableIntList;
            }
            PrioritySet.m334addimpl(mutableIntList, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateNodeOfGroup(int i, Object obj) {
        boolean z;
        int iGroupIndexToAddress = groupIndexToAddress(i);
        int[] iArr = this.groups;
        if (iGroupIndexToAddress < iArr.length) {
            z = (iArr[(iGroupIndexToAddress * 5) + 1] & 1073741824) != 0;
        }
        if (!z) {
            ComposerKt.composeImmediateRuntimeError("Updating the node of a group at " + i + " that was not created with as a node group");
        }
        this.slots[dataIndexToDataAddress(dataIndex(iGroupIndexToAddress, this.groups))] = obj;
    }

    public final void startGroup(int i, Object obj) {
        Composer.Companion.getClass();
        startGroup(i, obj, false, Composer.Companion.Empty);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void startGroup(int i, Object obj, boolean z, Object obj2) {
        int i2;
        GroupSourceInformation groupSourceInformationSourceInformationOf$runtime_release;
        int i3 = this.parent;
        Object[] objArr = this.insertCount > 0;
        this.nodeCountStack.push(this.nodeCount);
        Composer.Companion companion = Composer.Companion;
        if (objArr != false) {
            int i4 = this.currentGroup;
            int iDataIndex = dataIndex(groupIndexToAddress(i4), this.groups);
            insertGroups(1);
            this.currentSlot = iDataIndex;
            this.currentSlotEnd = iDataIndex;
            int iGroupIndexToAddress = groupIndexToAddress(i4);
            companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            int i5 = obj != composer$Companion$Empty$1 ? 1 : 0;
            int i6 = (z || obj2 == composer$Companion$Empty$1) ? 0 : 1;
            int iDataIndexToDataAnchor = dataIndexToDataAnchor(iDataIndex, this.slotsGapStart, this.slotsGapLen, this.slots.length);
            if (iDataIndexToDataAnchor >= 0 && this.slotsGapOwner < i4) {
                iDataIndexToDataAnchor = -(((this.slots.length - this.slotsGapLen) - iDataIndexToDataAnchor) + 1);
            }
            int[] iArr = this.groups;
            int i7 = this.parent;
            int i8 = iGroupIndexToAddress * 5;
            iArr[i8] = i;
            iArr[i8 + 1] = ((z ? 1 : 0) << 30) | (i5 << 29) | (i6 << 28);
            iArr[i8 + 2] = i7;
            iArr[i8 + 3] = 0;
            iArr[i8 + 4] = iDataIndexToDataAnchor;
            int i9 = (z ? 1 : 0) + i5 + i6;
            if (i9 > 0) {
                insertSlots(i9, i4);
                Object[] objArr2 = this.slots;
                int i10 = this.currentSlot;
                if (z) {
                    objArr2[i10] = obj2;
                    i10++;
                }
                if (i5 != 0) {
                    objArr2[i10] = obj;
                    i10++;
                }
                if (i6 != 0) {
                    objArr2[i10] = obj2;
                    i10++;
                }
                this.currentSlot = i10;
            }
            this.nodeCount = 0;
            i2 = i4 + 1;
            this.parent = i4;
            this.currentGroup = i2;
            if (i3 >= 0 && (groupSourceInformationSourceInformationOf$runtime_release = sourceInformationOf$runtime_release(i3)) != null) {
                GroupSourceInformation groupSourceInformationOpenInformation = groupSourceInformationSourceInformationOf$runtime_release.openInformation();
                Anchor anchor = anchor(i4);
                ArrayList arrayList = groupSourceInformationOpenInformation.groups;
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                groupSourceInformationOpenInformation.groups = arrayList;
                arrayList.add(anchor);
            }
        } else {
            this.startStack.push(i3);
            this.endStack.push((getCapacity() - this.groupGapLen) - this.currentGroupEnd);
            int i11 = this.currentGroup;
            int iGroupIndexToAddress2 = groupIndexToAddress(i11);
            companion.getClass();
            if (!Intrinsics.areEqual(obj2, Composer.Companion.Empty)) {
                if (z) {
                    updateNodeOfGroup(this.currentGroup, obj2);
                } else {
                    updateAux(obj2);
                }
            }
            this.currentSlot = slotIndex(iGroupIndexToAddress2, this.groups);
            this.currentSlotEnd = dataIndex(groupIndexToAddress(this.currentGroup + 1), this.groups);
            int[] iArr2 = this.groups;
            int i12 = iGroupIndexToAddress2 * 5;
            this.nodeCount = iArr2[i12 + 1] & 67108863;
            this.parent = i11;
            this.currentGroup = i11 + 1;
            i2 = i11 + iArr2[i12 + 3];
        }
        this.currentGroupEnd = i2;
    }
}
