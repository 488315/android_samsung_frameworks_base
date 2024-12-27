package com.android.internal.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class RingBuffer<T> {
    private final T[] mBuffer;
    private long mCursor;
    private final Supplier<T> mNewItem;

    @Deprecated
    public RingBuffer(final Class<T> c, int capacity) {
        this(new Supplier() { // from class: com.android.internal.util.RingBuffer$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                Object createNewItem;
                createNewItem = RingBuffer.createNewItem(c);
                return createNewItem;
            }
        }, new IntFunction() { // from class: com.android.internal.util.RingBuffer$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return RingBuffer.lambda$new$1(c, i);
            }
        }, capacity);
    }

    static /* synthetic */ Object[] lambda$new$1(Class c, int cap) {
        return (Object[]) Array.newInstance((Class<?>) c, cap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object createNewItem(Class c) {
        try {
            return c.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    public RingBuffer(Supplier<T> newItem, IntFunction<T[]> newBacking, int capacity) {
        this.mCursor = 0L;
        Preconditions.checkArgumentPositive(capacity, "A RingBuffer cannot have 0 capacity");
        this.mBuffer = newBacking.apply(capacity);
        this.mNewItem = newItem;
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
        if (this.mBuffer[indexOf] == null) {
            this.mBuffer[indexOf] = this.mNewItem.get();
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

    private int indexOf(long cursor) {
        return (int) Math.abs(cursor % this.mBuffer.length);
    }
}
