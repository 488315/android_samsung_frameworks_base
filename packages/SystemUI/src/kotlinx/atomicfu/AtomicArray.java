package kotlinx.atomicfu;

/* loaded from: classes4.dex */
public final class AtomicArray {
    public final AtomicRef[] array;

    public AtomicArray(int i) {
        AtomicRef[] atomicRefArr = new AtomicRef[i];
        for (int i2 = 0; i2 < i; i2++) {
            atomicRefArr[i2] = AtomicFU.atomic((Object) null);
        }
        this.array = atomicRefArr;
    }
}
