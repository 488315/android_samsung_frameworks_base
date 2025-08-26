package com.android.internal.util.function.pooled;

import android.os.Message;
import com.android.internal.util.function.DecConsumer;
import com.android.internal.util.function.DodecConsumer;
import com.android.internal.util.function.HeptConsumer;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.NonaConsumer;
import com.android.internal.util.function.OctConsumer;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuadPredicate;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.QuintPredicate;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.TriPredicate;
import com.android.internal.util.function.UndecConsumer;
import com.android.internal.util.function.pooled.PooledSupplier;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public interface PooledLambda {
    void recycle();

    PooledLambda recycleOnUse();

    static <R> ArgumentPlaceholder<R> __() {
        return (ArgumentPlaceholder<R>) ArgumentPlaceholder.INSTANCE;
    }

    static <R> ArgumentPlaceholder<R> __(Class<R> cls) {
        return __();
    }

    static <R> PooledSupplier<R> obtainSupplier(R r) {
        PooledLambdaImpl pooledLambdaImplAcquireConstSupplier = PooledLambdaImpl.acquireConstSupplier(3);
        pooledLambdaImplAcquireConstSupplier.mFunc = r;
        return pooledLambdaImplAcquireConstSupplier;
    }

    static PooledSupplier.OfInt obtainSupplier(int i) {
        PooledLambdaImpl pooledLambdaImplAcquireConstSupplier = PooledLambdaImpl.acquireConstSupplier(4);
        pooledLambdaImplAcquireConstSupplier.mConstValue = i;
        return pooledLambdaImplAcquireConstSupplier;
    }

    static PooledSupplier.OfLong obtainSupplier(long j) {
        PooledLambdaImpl pooledLambdaImplAcquireConstSupplier = PooledLambdaImpl.acquireConstSupplier(5);
        pooledLambdaImplAcquireConstSupplier.mConstValue = j;
        return pooledLambdaImplAcquireConstSupplier;
    }

    static PooledSupplier.OfDouble obtainSupplier(double d) {
        PooledLambdaImpl pooledLambdaImplAcquireConstSupplier = PooledLambdaImpl.acquireConstSupplier(6);
        pooledLambdaImplAcquireConstSupplier.mConstValue = Double.doubleToRawLongBits(d);
        return pooledLambdaImplAcquireConstSupplier;
    }

    static <A> PooledRunnable obtainRunnable(Consumer<? super A> consumer, A a) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, consumer, 1, 0, 1, a, null, null, null, null, null, null, null, null, null, null, null);
    }

    static <A> Message obtainMessage(Consumer<? super A> consumer, A a) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, consumer, 1, 0, 1, a, null, null, null, null, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B> PooledRunnable obtainRunnable(BiConsumer<? super A, ? super B> biConsumer, A a, B b) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biConsumer, 2, 0, 1, a, b, null, null, null, null, null, null, null, null, null, null);
    }

    static <A, B> PooledPredicate<A> obtainPredicate(BiPredicate<? super A, ? super B> biPredicate, ArgumentPlaceholder<A> argumentPlaceholder, B b) {
        return (PooledPredicate) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biPredicate, 2, 1, 2, argumentPlaceholder, b, null, null, null, null, null, null, null, null, null, null);
    }

    static <A, B, C> PooledPredicate<A> obtainPredicate(TriPredicate<? super A, ? super B, ? super C> triPredicate, ArgumentPlaceholder<A> argumentPlaceholder, B b, C c) {
        return (PooledPredicate) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triPredicate, 3, 1, 2, argumentPlaceholder, b, c, null, null, null, null, null, null, null, null, null);
    }

    static <A, B, C, D> PooledPredicate<A> obtainPredicate(QuadPredicate<? super A, ? super B, ? super C, ? super D> quadPredicate, ArgumentPlaceholder<A> argumentPlaceholder, B b, C c, D d) {
        return (PooledPredicate) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadPredicate, 4, 1, 2, argumentPlaceholder, b, c, d, null, null, null, null, null, null, null, null);
    }

    static <A, B, C, D, E> PooledPredicate<A> obtainPredicate(QuintPredicate<? super A, ? super B, ? super C, ? super D, ? super E> quintPredicate, ArgumentPlaceholder<A> argumentPlaceholder, B b, C c, D d, E e) {
        return (PooledPredicate) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quintPredicate, 5, 1, 2, argumentPlaceholder, b, c, d, e, null, null, null, null, null, null, null);
    }

    static <A, B> PooledPredicate<B> obtainPredicate(BiPredicate<? super A, ? super B> biPredicate, A a, ArgumentPlaceholder<B> argumentPlaceholder) {
        return (PooledPredicate) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, biPredicate, 2, 1, 2, a, argumentPlaceholder, null, null, null, null, null, null, null, null, null, null);
    }

    static <A, B> Message obtainMessage(BiConsumer<? super A, ? super B> biConsumer, A a, B b) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, biConsumer, 2, 0, 1, a, b, null, null, null, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C> PooledRunnable obtainRunnable(TriConsumer<? super A, ? super B, ? super C> triConsumer, A a, B b, C c) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, triConsumer, 3, 0, 1, a, b, c, null, null, null, null, null, null, null, null, null);
    }

    static <A, B, C> Message obtainMessage(TriConsumer<? super A, ? super B, ? super C> triConsumer, A a, B b, C c) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, triConsumer, 3, 0, 1, a, b, c, null, null, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D> PooledRunnable obtainRunnable(QuadConsumer<? super A, ? super B, ? super C, ? super D> quadConsumer, A a, B b, C c, D d) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quadConsumer, 4, 0, 1, a, b, c, d, null, null, null, null, null, null, null, null);
    }

    static <A, B, C, D> Message obtainMessage(QuadConsumer<? super A, ? super B, ? super C, ? super D> quadConsumer, A a, B b, C c, D d) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, quadConsumer, 4, 0, 1, a, b, c, d, null, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E> PooledRunnable obtainRunnable(QuintConsumer<? super A, ? super B, ? super C, ? super D, ? super E> quintConsumer, A a, B b, C c, D d, E e) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, quintConsumer, 5, 0, 1, a, b, c, d, e, null, null, null, null, null, null, null);
    }

    static <A, B, C, D, E> Message obtainMessage(QuintConsumer<? super A, ? super B, ? super C, ? super D, ? super E> quintConsumer, A a, B b, C c, D d, E e) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, quintConsumer, 5, 0, 1, a, b, c, d, e, null, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F> PooledRunnable obtainRunnable(HexConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> hexConsumer, A a, B b, C c, D d, E e, F f) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, hexConsumer, 6, 0, 1, a, b, c, d, e, f, null, null, null, null, null, null);
    }

    static <A, B, C, D, E, F> Message obtainMessage(HexConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F> hexConsumer, A a, B b, C c, D d, E e, F f) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, hexConsumer, 6, 0, 1, a, b, c, d, e, f, null, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G> PooledRunnable obtainRunnable(HeptConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> heptConsumer, A a, B b, C c, D d, E e, F f, G g) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, heptConsumer, 7, 0, 1, a, b, c, d, e, f, g, null, null, null, null, null);
    }

    static <A, B, C, D, E, F, G> Message obtainMessage(HeptConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G> heptConsumer, A a, B b, C c, D d, E e, F f, G g) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, heptConsumer, 7, 0, 1, a, b, c, d, e, f, g, null, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H> PooledRunnable obtainRunnable(OctConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> octConsumer, A a, B b, C c, D d, E e, F f, G g, H h) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, octConsumer, 8, 0, 1, a, b, c, d, e, f, g, h, null, null, null, null);
    }

    static <A, B, C, D, E, F, G, H> Message obtainMessage(OctConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H> octConsumer, A a, B b, C c, D d, E e, F f, G g, H h) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, octConsumer, 8, 0, 1, a, b, c, d, e, f, g, h, null, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H, I> PooledRunnable obtainRunnable(NonaConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I> nonaConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, nonaConsumer, 9, 0, 1, a, b, c, d, e, f, g, h, i, null, null, null);
    }

    static <A, B, C, D, E, F, G, H, I> Message obtainMessage(NonaConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I> nonaConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, nonaConsumer, 9, 0, 1, a, b, c, d, e, f, g, h, i, null, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H, I, J> PooledRunnable obtainRunnable(DecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J> decConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, decConsumer, 10, 0, 1, a, b, c, d, e, f, g, h, i, j, null, null);
    }

    static <A, B, C, D, E, F, G, H, I, J> Message obtainMessage(DecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J> decConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, decConsumer, 10, 0, 1, a, b, c, d, e, f, g, h, i, j, null, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H, I, J, K> PooledRunnable obtainRunnable(UndecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J, ? super K> undecConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, undecConsumer, 11, 0, 1, a, b, c, d, e, f, g, h, i, j, k, null);
    }

    static <A, B, C, D, E, F, G, H, I, J, K> Message obtainMessage(UndecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J, ? super K> undecConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, undecConsumer, 11, 0, 1, a, b, c, d, e, f, g, h, i, j, k, null)).recycleOnUse());
        }
        return callback;
    }

    static <A, B, C, D, E, F, G, H, I, J, K, L> PooledRunnable obtainRunnable(DodecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J, ? super K, ? super L> dodecConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l) {
        return (PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sPool, dodecConsumer, 12, 0, 1, a, b, c, d, e, f, g, h, i, j, k, l);
    }

    static <A, B, C, D, E, F, G, H, I, J, K, L> Message obtainMessage(DodecConsumer<? super A, ? super B, ? super C, ? super D, ? super E, ? super F, ? super G, ? super H, ? super I, ? super J, ? super K, ? super L> dodecConsumer, A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k, L l) {
        Message callback;
        synchronized (Message.sPoolSync) {
            callback = Message.obtain().setCallback(((PooledRunnable) PooledLambdaImpl.acquire(PooledLambdaImpl.sMessageCallbacksPool, dodecConsumer, 12, 0, 1, a, b, c, d, e, f, g, h, i, j, k, l)).recycleOnUse());
        }
        return callback;
    }
}
