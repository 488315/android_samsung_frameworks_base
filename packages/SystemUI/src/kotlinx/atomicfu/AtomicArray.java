package kotlinx.atomicfu;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
