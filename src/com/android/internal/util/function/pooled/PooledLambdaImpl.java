package com.android.internal.util.function.pooled;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.BitUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.function.DecConsumer;
import com.android.internal.util.function.DecFunction;
import com.android.internal.util.function.DecPredicate;
import com.android.internal.util.function.DodecConsumer;
import com.android.internal.util.function.DodecFunction;
import com.android.internal.util.function.DodecPredicate;
import com.android.internal.util.function.HeptConsumer;
import com.android.internal.util.function.HeptFunction;
import com.android.internal.util.function.HeptPredicate;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.HexFunction;
import com.android.internal.util.function.HexPredicate;
import com.android.internal.util.function.NonaConsumer;
import com.android.internal.util.function.NonaFunction;
import com.android.internal.util.function.NonaPredicate;
import com.android.internal.util.function.OctConsumer;
import com.android.internal.util.function.OctFunction;
import com.android.internal.util.function.OctPredicate;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.QuadPredicate;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.QuintFunction;
import com.android.internal.util.function.QuintPredicate;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.TriFunction;
import com.android.internal.util.function.TriPredicate;
import com.android.internal.util.function.UndecConsumer;
import com.android.internal.util.function.UndecFunction;
import com.android.internal.util.function.UndecPredicate;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
final class PooledLambdaImpl<R> extends OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, R> {
    private static final boolean DEBUG = false;
    private static final int FLAG_ACQUIRED_FROM_MESSAGE_CALLBACKS_POOL = 8192;
    private static final int FLAG_RECYCLED = 2048;
    private static final int FLAG_RECYCLE_ON_USE = 4096;
    private static final String LOG_TAG = "PooledLambdaImpl";
    static final int MASK_EXPOSED_AS = 2080768;
    static final int MASK_FUNC_TYPE = 266338304;
    private static final int MAX_ARGS = 11;
    private static final int MAX_POOL_SIZE = 50;
    long mConstValue;
    Object mFunc;
    static final Pool sPool = new Pool(new Object());
    static final Pool sMessageCallbacksPool = new Pool(Message.sPoolSync);
    Object[] mArgs = null;
    int mFlags = 0;

    static class Pool extends Pools.SynchronizedPool<PooledLambdaImpl> {
        public Pool(Object obj) {
            super(50, obj);
        }
    }

    private PooledLambdaImpl() {
    }

    @Override // com.android.internal.util.function.pooled.PooledLambda
    public void recycle() {
        if (isRecycled()) {
            return;
        }
        doRecycle();
    }

    private void doRecycle() {
        Pool pool;
        if ((this.mFlags & 8192) != 0) {
            pool = sMessageCallbacksPool;
        } else {
            pool = sPool;
        }
        this.mFunc = null;
        Object[] objArr = this.mArgs;
        if (objArr != null) {
            Arrays.fill(objArr, (Object) null);
        }
        this.mFlags = 2048;
        this.mConstValue = 0L;
        pool.release(this);
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction
    R invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11) {
        checkNotRecycled();
        if (fillInArg(obj) && fillInArg(obj2) && fillInArg(obj3) && fillInArg(obj4) && fillInArg(obj5) && fillInArg(obj6) && fillInArg(obj7) && fillInArg(obj8) && fillInArg(obj9) && fillInArg(obj10)) {
            fillInArg(obj11);
        }
        int iDecodeArgCount = LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE));
        int i = 0;
        if (iDecodeArgCount != 15) {
            for (int i2 = 0; i2 < iDecodeArgCount; i2++) {
                if (this.mArgs[i2] == ArgumentPlaceholder.INSTANCE) {
                    throw new IllegalStateException("Missing argument #" + i2 + " among " + Arrays.toString(this.mArgs));
                }
            }
        }
        try {
            R rDoInvoke = doInvoke();
            if (isRecycleOnUse()) {
                doRecycle();
                return rDoInvoke;
            }
            if (!isRecycled()) {
                int size = ArrayUtils.size(this.mArgs);
                while (i < size) {
                    popArg(i);
                    i++;
                }
            }
            return rDoInvoke;
        } catch (Throwable th) {
            if (isRecycleOnUse()) {
                doRecycle();
            } else if (!isRecycled()) {
                int size2 = ArrayUtils.size(this.mArgs);
                while (i < size2) {
                    popArg(i);
                    i++;
                }
            }
            throw th;
        }
    }

    private boolean fillInArg(Object obj) {
        int size = ArrayUtils.size(this.mArgs);
        for (int i = 0; i < size; i++) {
            if (this.mArgs[i] == ArgumentPlaceholder.INSTANCE) {
                this.mArgs[i] = obj;
                this.mFlags = (int) (this.mFlags | BitUtils.bitAt(i));
                return true;
            }
        }
        if (obj == null || obj == ArgumentPlaceholder.INSTANCE) {
            return false;
        }
        throw new IllegalStateException("No more arguments expected for provided arg " + obj + " among " + Arrays.toString(this.mArgs));
    }

    private void checkNotRecycled() {
        if (isRecycled()) {
            throw new IllegalStateException("Instance is recycled: " + this);
        }
    }

    private R doInvoke() {
        int flags = getFlags(MASK_FUNC_TYPE);
        int iDecodeArgCount = LambdaType.decodeArgCount(flags);
        int iDecodeReturnType = LambdaType.decodeReturnType(flags);
        if (iDecodeArgCount == 15) {
            if (iDecodeReturnType == 4) {
                return (R) Integer.valueOf(getAsInt());
            }
            if (iDecodeReturnType == 5) {
                return (R) Long.valueOf(getAsLong());
            }
            if (iDecodeReturnType == 6) {
                return (R) Double.valueOf(getAsDouble());
            }
            return (R) this.mFunc;
        }
        switch (iDecodeArgCount) {
            case 0:
                if (iDecodeReturnType == 1) {
                    ((Runnable) this.mFunc).run();
                    return null;
                }
                if (iDecodeReturnType == 2 || iDecodeReturnType == 3) {
                    return (R) ((Supplier) this.mFunc).get();
                }
            case 1:
                if (iDecodeReturnType == 1) {
                    ((Consumer) this.mFunc).accept(popArg(0));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((Predicate) this.mFunc).test(popArg(0)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((Function) this.mFunc).apply(popArg(0));
                }
                break;
            case 2:
                if (iDecodeReturnType == 1) {
                    ((BiConsumer) this.mFunc).accept(popArg(0), popArg(1));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((BiPredicate) this.mFunc).test(popArg(0), popArg(1)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((BiFunction) this.mFunc).apply(popArg(0), popArg(1));
                }
                break;
            case 3:
                if (iDecodeReturnType == 1) {
                    ((TriConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((TriPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((TriFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2));
                }
                break;
            case 4:
                if (iDecodeReturnType == 1) {
                    ((QuadConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((QuadPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((QuadFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3));
                }
                break;
            case 5:
                if (iDecodeReturnType == 1) {
                    ((QuintConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((QuintPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((QuintFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4));
                }
                break;
            case 6:
                if (iDecodeReturnType == 1) {
                    ((HexConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((HexPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((HexFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5));
                }
                break;
            case 7:
                if (iDecodeReturnType == 1) {
                    ((HeptConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((HeptPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((HeptFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6));
                }
                break;
            case 8:
                if (iDecodeReturnType == 1) {
                    ((OctConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((OctPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((OctFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7));
                }
                break;
            case 9:
                if (iDecodeReturnType == 1) {
                    ((NonaConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((NonaPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((NonaFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8));
                }
                break;
            case 10:
                if (iDecodeReturnType == 1) {
                    ((DecConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((DecPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((DecFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9));
                }
                break;
            case 11:
                if (iDecodeReturnType == 1) {
                    ((UndecConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((UndecPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((UndecFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10));
                }
                break;
            case 12:
                if (iDecodeReturnType == 1) {
                    ((DodecConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10), popArg(11));
                    return null;
                }
                if (iDecodeReturnType == 2) {
                    return (R) Boolean.valueOf(((DodecPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10), popArg(11)));
                }
                if (iDecodeReturnType == 3) {
                    return (R) ((DodecFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8), popArg(9), popArg(10), popArg(11));
                }
                break;
        }
        throw new IllegalStateException("Unknown function type: " + LambdaType.toString(flags));
    }

    private boolean isConstSupplier() {
        return LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE)) == 15;
    }

    private Object popArg(int i) {
        Object obj = this.mArgs[i];
        if (isInvocationArgAtIndex(i)) {
            this.mArgs[i] = ArgumentPlaceholder.INSTANCE;
            this.mFlags = (int) (this.mFlags & (~BitUtils.bitAt(i)));
        }
        return obj;
    }

    public String toString() {
        if (isRecycled()) {
            return "<recycled PooledLambda@" + hashCodeHex(this) + ">";
        }
        StringBuilder sb = new StringBuilder();
        if (isConstSupplier()) {
            sb.append(getFuncTypeAsString());
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(doInvoke());
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        } else {
            Object obj = this.mFunc;
            if (obj instanceof PooledLambdaImpl) {
                sb.append(obj);
            } else {
                sb.append(getFuncTypeAsString());
                sb.append("@");
                sb.append(hashCodeHex(obj));
            }
            sb.append(NavigationBarInflaterView.KEY_CODE_START);
            sb.append(commaSeparateFirstN(this.mArgs, LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE))));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        return sb.toString();
    }

    private String commaSeparateFirstN(Object[] objArr, int i) {
        if (objArr == null) {
            return "";
        }
        return TextUtils.join(",", Arrays.copyOf(objArr, i));
    }

    private static String hashCodeHex(Object obj) {
        return Integer.toHexString(Objects.hashCode(obj));
    }

    private String getFuncTypeAsString() {
        if (isRecycled()) {
            return "<recycled>";
        }
        if (isConstSupplier()) {
            return "supplier";
        }
        String string = LambdaType.toString(getFlags(MASK_EXPOSED_AS));
        return string.endsWith("Consumer") ? "consumer" : string.endsWith("Function") ? "function" : string.endsWith("Predicate") ? "predicate" : string.endsWith("Supplier") ? "supplier" : string.endsWith("Runnable") ? "runnable" : string;
    }

    static <E extends PooledLambda> E acquire(Pool pool, Object obj, int i, int i2, int i3, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10, Object obj11, Object obj12, Object obj13) {
        PooledLambdaImpl pooledLambdaImplAcquire = acquire(pool);
        pooledLambdaImplAcquire.mFunc = Objects.requireNonNull(obj);
        pooledLambdaImplAcquire.setFlags(MASK_FUNC_TYPE, LambdaType.encode(i, i3));
        pooledLambdaImplAcquire.setFlags(MASK_EXPOSED_AS, LambdaType.encode(i2, i3));
        if (ArrayUtils.size(pooledLambdaImplAcquire.mArgs) < i) {
            pooledLambdaImplAcquire.mArgs = new Object[i];
        }
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 0, obj2);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 1, obj3);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 2, obj4);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 3, obj5);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 4, obj6);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 5, obj7);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 6, obj8);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 7, obj9);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 8, obj10);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 9, obj11);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 10, obj12);
        setIfInBounds(pooledLambdaImplAcquire.mArgs, 11, obj13);
        return pooledLambdaImplAcquire;
    }

    static PooledLambdaImpl acquireConstSupplier(int i) {
        PooledLambdaImpl pooledLambdaImplAcquire = acquire(sPool);
        int iEncode = LambdaType.encode(15, i);
        pooledLambdaImplAcquire.setFlags(MASK_FUNC_TYPE, iEncode);
        pooledLambdaImplAcquire.setFlags(MASK_EXPOSED_AS, iEncode);
        return pooledLambdaImplAcquire;
    }

    static PooledLambdaImpl acquire(Pool pool) {
        PooledLambdaImpl pooledLambdaImplAcquire = pool.acquire();
        if (pooledLambdaImplAcquire == null) {
            pooledLambdaImplAcquire = new PooledLambdaImpl();
        }
        pooledLambdaImplAcquire.mFlags &= -2049;
        pooledLambdaImplAcquire.setFlags(8192, pool == sMessageCallbacksPool ? 1 : 0);
        return pooledLambdaImplAcquire;
    }

    private static void setIfInBounds(Object[] objArr, int i, Object obj) {
        if (i < ArrayUtils.size(objArr)) {
            objArr[i] = obj;
        }
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction, java.util.function.Predicate, java.util.function.BiPredicate
    public OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, R> negate() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction, java.util.function.BiFunction
    public <V> OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, V> andThen(Function<? super R, ? extends V> function) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.function.DoubleSupplier
    public double getAsDouble() {
        return Double.longBitsToDouble(this.mConstValue);
    }

    @Override // java.util.function.IntSupplier
    public int getAsInt() {
        return (int) this.mConstValue;
    }

    @Override // java.util.function.LongSupplier
    public long getAsLong() {
        return this.mConstValue;
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction, com.android.internal.util.function.pooled.PooledPredicate, com.android.internal.util.function.pooled.PooledLambda, com.android.internal.util.function.pooled.PooledSupplier, com.android.internal.util.function.pooled.PooledRunnable, com.android.internal.util.function.pooled.PooledSupplier.OfInt, com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
    public OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, R> recycleOnUse() {
        this.mFlags |= 4096;
        return this;
    }

    @Override // android.os.TraceNameSupplier
    public String getTraceName() {
        return FunctionalUtils.getLambdaName(this.mFunc);
    }

    private boolean isRecycled() {
        return (this.mFlags & 2048) != 0;
    }

    private boolean isRecycleOnUse() {
        return (this.mFlags & 4096) != 0;
    }

    private boolean isInvocationArgAtIndex(int i) {
        return (this.mFlags & (1 << i)) != 0;
    }

    int getFlags(int i) {
        return unmask(i, this.mFlags);
    }

    void setFlags(int i, int i2) {
        int i3 = this.mFlags & (~i);
        this.mFlags = i3;
        this.mFlags = mask(i, i2) | i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int mask(int i, int i2) {
        return i & (i2 << Integer.numberOfTrailingZeros(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int unmask(int i, int i2) {
        return (i2 & i) / (1 << Integer.numberOfTrailingZeros(i));
    }

    static class LambdaType {
        public static final int MASK = 127;
        public static final int MASK_ARG_COUNT = 15;
        public static final int MASK_BIT_COUNT = 7;
        public static final int MASK_RETURN_TYPE = 112;

        static int decodeArgCount(int i) {
            return i & 15;
        }

        LambdaType() {
        }

        static int encode(int i, int i2) {
            return PooledLambdaImpl.mask(15, i) | PooledLambdaImpl.mask(112, i2);
        }

        static int decodeReturnType(int i) {
            return PooledLambdaImpl.unmask(112, i);
        }

        static String toString(int i) {
            int iDecodeArgCount = decodeArgCount(i);
            int iDecodeReturnType = decodeReturnType(i);
            if (iDecodeArgCount == 0) {
                if (iDecodeReturnType == 1) {
                    return "Runnable";
                }
                if (iDecodeReturnType == 3 || iDecodeReturnType == 2) {
                    return "Supplier";
                }
            }
            return argCountPrefix(iDecodeArgCount) + ReturnType.lambdaSuffix(iDecodeReturnType);
        }

        private static String argCountPrefix(int i) {
            if (i != 15) {
                switch (i) {
                    case 0:
                    case 1:
                        break;
                    case 2:
                        return "Bi";
                    case 3:
                        return "Tri";
                    case 4:
                        return "Quad";
                    case 5:
                        return "Quint";
                    case 6:
                        return "Hex";
                    case 7:
                        return "Hept";
                    case 8:
                        return "Oct";
                    case 9:
                        return "Nona";
                    case 10:
                        return "Dec";
                    case 11:
                        return "Undec";
                    default:
                        return "" + i + "arg";
                }
            }
            return "";
        }

        static class ReturnType {
            public static final int BOOLEAN = 2;
            public static final int DOUBLE = 6;
            public static final int INT = 4;
            public static final int LONG = 5;
            public static final int OBJECT = 3;
            public static final int VOID = 1;

            ReturnType() {
            }

            static String toString(int i) {
                switch (i) {
                    case 1:
                        return "VOID";
                    case 2:
                        return "BOOLEAN";
                    case 3:
                        return "OBJECT";
                    case 4:
                        return "INT";
                    case 5:
                        return "LONG";
                    case 6:
                        return "DOUBLE";
                    default:
                        return "" + i;
                }
            }

            static String lambdaSuffix(int i) {
                return prefix(i) + suffix(i);
            }

            private static String prefix(int i) {
                if (i == 4) {
                    return "Int";
                }
                if (i == 5) {
                    return "Long";
                }
                if (i == 6) {
                    return "Double";
                }
                return "";
            }

            private static String suffix(int i) {
                if (i == 1) {
                    return "Consumer";
                }
                if (i == 2) {
                    return "Predicate";
                }
                if (i == 3) {
                    return "Function";
                }
                return "Supplier";
            }
        }
    }
}
