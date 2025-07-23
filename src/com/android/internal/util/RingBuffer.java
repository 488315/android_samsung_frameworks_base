package com.android.internal.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class RingBuffer<T> {
    private final T[] mBuffer;
    private long mCursor;
    private final Supplier<T> mNewItem;

    @Deprecated
    public RingBuffer(final Class<T> cls, int i) {
        this(new Supplier() { // from class: com.android.internal.util.RingBuffer$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                Object createNewItem;
                createNewItem = RingBuffer.createNewItem(cls);
                return createNewItem;
            }
        }, new IntFunction() { // from class: com.android.internal.util.RingBuffer$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return RingBuffer.lambda$new$1(cls, i2);
            }
        }, i);
    }

    static /* synthetic */ Object[] lambda$new$1(Class cls, int i) {
        return (Object[]) Array.newInstance((Class<?>) cls, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object createNewItem(Class cls) {
        try {
            Class[] clsArr = new Class[0];
            return cls.getDeclaredConstructor(null).newInstance(null);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException unused) {
            return null;
        }
    }

    public RingBuffer(Supplier<T> supplier, IntFunction<T[]> intFunction, int i) {
        this.mCursor = 0L;
        Preconditions.checkArgumentPositive(i, "A RingBuffer cannot have 0 capacity");
        this.mBuffer = intFunction.apply(i);
        this.mNewItem = supplier;
    }

    public int size() {
        return (int) Math.min(this.mBuffer.length, this.mCursor);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        for (int i = 0; i < size(); i++) {
            this.mBuffer[i] = null;
        }
        this.mCursor = 0L;
    }

    public void append(T t) {
        T[] tArr = this.mBuffer;
        long j = this.mCursor;
        this.mCursor = 1 + j;
        tArr[indexOf(j)] = t;
    }

    public T getNextSlot() {
        long j = this.mCursor;
        this.mCursor = 1 + j;
        int indexOf = indexOf(j);
        T[] tArr = this.mBuffer;
        if (tArr[indexOf] == null) {
            tArr[indexOf] = this.mNewItem.get();
        }
        return this.mBuffer[indexOf];
    }

    public T[] toArray() {
        T[] tArr = (T[]) Arrays.copyOf(this.mBuffer, size(), this.mBuffer.getClass());
        long j = this.mCursor - 1;
        int length = tArr.length - 1;
        while (length >= 0) {
            tArr[length] = this.mBuffer[indexOf(j)];
            length--;
            j--;
        }
        return tArr;
    }

    private int indexOf(long j) {
        return (int) Math.abs(j % this.mBuffer.length);
    }
}
