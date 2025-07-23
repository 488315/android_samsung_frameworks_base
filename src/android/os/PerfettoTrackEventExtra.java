package android.os;

import android.os.PerfettoTrace;
import android.os.PerfettoTrackEventExtra;
import com.android.internal.ravenwood.RavenwoodEnvironment;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes3.dex */
public final class PerfettoTrackEventExtra {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_EXTRA_CACHE_SIZE = 5;
    private static final String TAG = "PerfettoTrackEventExtra";
    private final RingBuffer<ArgBool> mArgBoolCache;
    private final RingBuffer<ArgDouble> mArgDoubleCache;
    private final RingBuffer<ArgInt64> mArgInt64Cache;
    private final RingBuffer<ArgString> mArgStringCache;
    private final Pool<Builder> mBuilderCache;
    private CounterDouble mCounterDouble;
    private CounterInt64 mCounterInt64;
    private final RingBuffer<CounterTrack> mCounterTrackCache;
    private final Pool<FieldDouble> mFieldDoubleCache;
    private final Pool<FieldInt64> mFieldInt64Cache;
    private final Pool<FieldNested> mFieldNestedCache;
    private final Pool<FieldString> mFieldStringCache;
    private Flow mFlow;
    private final RingBuffer<NamedTrack> mNamedTrackCache;
    private final List<PerfettoPointer> mPendingPointers;
    private Proto mProto;
    private final long mPtr;
    private Flow mTerminatingFlow;
    private static final Builder NO_OP_BUILDER = new Builder(null, false);
    private static final ThreadLocal<PerfettoTrackEventExtra> sTrackEventExtra = new ThreadLocal<PerfettoTrackEventExtra>() { // from class: android.os.PerfettoTrackEventExtra.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public PerfettoTrackEventExtra initialValue() {
            return new PerfettoTrackEventExtra();
        }
    };
    private static final AtomicLong sNamedTrackId = new AtomicLong();
    private static final Supplier<Flow> sFlowSupplier = new Supplier() { // from class: android.os.PerfettoTrackEventExtra$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return new PerfettoTrackEventExtra.Flow();
        }
    };
    private static final Supplier<Builder> sBuilderSupplier = new Supplier() { // from class: android.os.PerfettoTrackEventExtra$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return PerfettoTrackEventExtra.$r8$lambda$PKockuywBd_S6YrwVRC15_taBZQ();
        }
    };
    private static final Supplier<FieldInt64> sFieldInt64Supplier = new Supplier() { // from class: android.os.PerfettoTrackEventExtra$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final Object get() {
            return new PerfettoTrackEventExtra.FieldInt64();
        }
    };
    private static final Supplier<FieldDouble> sFieldDoubleSupplier = new Supplier() { // from class: android.os.PerfettoTrackEventExtra$$ExternalSyntheticLambda3
        @Override // java.util.function.Supplier
        public final Object get() {
            return new PerfettoTrackEventExtra.FieldDouble();
        }
    };
    private static final Supplier<FieldString> sFieldStringSupplier = new Supplier() { // from class: android.os.PerfettoTrackEventExtra$$ExternalSyntheticLambda4
        @Override // java.util.function.Supplier
        public final Object get() {
            return new PerfettoTrackEventExtra.FieldString();
        }
    };
    private static final Supplier<FieldNested> sFieldNestedSupplier = new Supplier() { // from class: android.os.PerfettoTrackEventExtra$$ExternalSyntheticLambda5
        @Override // java.util.function.Supplier
        public final Object get() {
            return new PerfettoTrackEventExtra.FieldNested();
        }
    };
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(PerfettoTrackEventExtra.class.getClassLoader(), native_delete());

    public interface FieldContainer {
        void addField(PerfettoPointer perfettoPointer);
    }

    public interface PerfettoPointer {
        long getPtr();
    }

    public static /* synthetic */ Builder $r8$lambda$PKockuywBd_S6YrwVRC15_taBZQ() {
        return new Builder();
    }

    private CounterDouble getCounterDouble$ravenwood() {
        return null;
    }

    private CounterInt64 getCounterInt64$ravenwood() {
        return null;
    }

    private Flow getFlow$ravenwood() {
        return null;
    }

    private Proto getProto$ravenwood() {
        return null;
    }

    private Flow getTerminatingFlow$ravenwood() {
        return null;
    }

    @CriticalNative
    private static native void native_add_arg(long j, long j2);

    @CriticalNative
    private static native void native_clear_args(long j);

    @CriticalNative
    private static native long native_delete();

    private static long native_delete$ravenwood() {
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native void native_emit(int i, long j, String str, long j2);

    @CriticalNative
    private static native long native_init();

    private static long native_init$ravenwood() {
        return 0L;
    }

    private void reset$ravenwood() {
    }

    private static final class RingBuffer<T> {
        private final int mCapacity;
        private final int[] mKeyArray;
        private final T[] mValueArray;
        private int mWriteEnd = 0;

        RingBuffer(int i) {
            this.mCapacity = i;
            this.mKeyArray = new int[i];
            this.mValueArray = (T[]) new Object[i];
        }

        public void put(int i, T t) {
            int[] iArr = this.mKeyArray;
            int i2 = this.mWriteEnd;
            iArr[i2] = i;
            this.mValueArray[i2] = t;
            this.mWriteEnd = (i2 + 1) % this.mCapacity;
        }

        public T get(int i) {
            for (int i2 = 0; i2 < this.mCapacity; i2++) {
                if (this.mKeyArray[i2] == i) {
                    return this.mValueArray[i2];
                }
            }
            return null;
        }
    }

    private static final class Pool<T> {
        private final int mCapacity;
        private int mIdx = 0;
        private final T[] mValueArray;

        Pool(int i) {
            this.mCapacity = i;
            this.mValueArray = (T[]) new Object[i];
        }

        public void reset() {
            this.mIdx = 0;
        }

        public T get(Supplier<T> supplier) {
            int i = this.mIdx;
            if (i >= this.mCapacity) {
                return supplier.get();
            }
            T[] tArr = this.mValueArray;
            if (tArr[i] == null) {
                tArr[i] = supplier.get();
            }
            T[] tArr2 = this.mValueArray;
            int i2 = this.mIdx;
            this.mIdx = i2 + 1;
            return tArr2[i2];
        }
    }

    public static final class Builder {
        private final RingBuffer<ArgBool> mArgBoolCache;
        private final RingBuffer<ArgDouble> mArgDoubleCache;
        private final RingBuffer<ArgInt64> mArgInt64Cache;
        private final RingBuffer<ArgString> mArgStringCache;
        private final Pool<Builder> mBuilderCache;
        private PerfettoTrace.Category mCategory;
        private final CounterDouble mCounterDouble;
        private final CounterInt64 mCounterInt64;
        private final RingBuffer<CounterTrack> mCounterTrackCache;
        private FieldContainer mCurrentContainer;
        private String mEventName;
        private final PerfettoTrackEventExtra mExtra;
        private final Pool<FieldDouble> mFieldDoubleCache;
        private final Pool<FieldInt64> mFieldInt64Cache;
        private final Pool<FieldNested> mFieldNestedCache;
        private final Pool<FieldString> mFieldStringCache;
        private final Flow mFlow;
        private boolean mIsBuilt;
        private final boolean mIsCategoryEnabled;
        private final RingBuffer<NamedTrack> mNamedTrackCache;
        private Builder mParent;
        private final Proto mProto;
        private final Flow mTerminatingFlow;
        private int mTraceType;

        private Builder() {
            this((PerfettoTrackEventExtra) PerfettoTrackEventExtra.sTrackEventExtra.get(), true);
        }

        public Builder(PerfettoTrackEventExtra perfettoTrackEventExtra, boolean z) {
            this.mIsCategoryEnabled = z;
            this.mExtra = perfettoTrackEventExtra;
            this.mNamedTrackCache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mNamedTrackCache;
            this.mCounterTrackCache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mCounterTrackCache;
            this.mArgInt64Cache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mArgInt64Cache;
            this.mArgDoubleCache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mArgDoubleCache;
            this.mArgBoolCache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mArgBoolCache;
            this.mArgStringCache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mArgStringCache;
            this.mFieldInt64Cache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mFieldInt64Cache;
            this.mFieldDoubleCache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mFieldDoubleCache;
            this.mFieldStringCache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mFieldStringCache;
            this.mFieldNestedCache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mFieldNestedCache;
            this.mBuilderCache = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.mBuilderCache;
            this.mCounterInt64 = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.getCounterInt64();
            this.mCounterDouble = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.getCounterDouble();
            this.mProto = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.getProto();
            this.mFlow = perfettoTrackEventExtra == null ? null : perfettoTrackEventExtra.getFlow();
            this.mTerminatingFlow = perfettoTrackEventExtra != null ? perfettoTrackEventExtra.getTerminatingFlow() : null;
        }

        public void emit() {
            if (this.mIsCategoryEnabled) {
                this.mIsBuilt = true;
                PerfettoTrackEventExtra.native_emit(this.mTraceType, this.mCategory.getPtr(), this.mEventName, this.mExtra.getPtr());
            }
        }

        public Builder init(int i, PerfettoTrace.Category category) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            this.mTraceType = i;
            this.mCategory = category;
            this.mEventName = "";
            this.mFieldInt64Cache.reset();
            this.mFieldDoubleCache.reset();
            this.mFieldStringCache.reset();
            this.mFieldNestedCache.reset();
            this.mBuilderCache.reset();
            this.mExtra.reset();
            return initInternal(this, null);
        }

        public Builder setEventName(String str) {
            this.mEventName = str;
            return this;
        }

        public Builder addArg(String str, long j) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            ArgInt64 argInt64 = this.mArgInt64Cache.get(str.hashCode());
            if (argInt64 == null || !argInt64.getName().equals(str)) {
                argInt64 = new ArgInt64(str);
                this.mArgInt64Cache.put(str.hashCode(), argInt64);
            }
            argInt64.setValue(j);
            this.mExtra.addPerfettoPointer(argInt64);
            return this;
        }

        public Builder addArg(String str, boolean z) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            ArgBool argBool = this.mArgBoolCache.get(str.hashCode());
            if (argBool == null || !argBool.getName().equals(str)) {
                argBool = new ArgBool(str);
                this.mArgBoolCache.put(str.hashCode(), argBool);
            }
            argBool.setValue(z);
            this.mExtra.addPerfettoPointer(argBool);
            return this;
        }

        public Builder addArg(String str, double d) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            ArgDouble argDouble = this.mArgDoubleCache.get(str.hashCode());
            if (argDouble == null || !argDouble.getName().equals(str)) {
                argDouble = new ArgDouble(str);
                this.mArgDoubleCache.put(str.hashCode(), argDouble);
            }
            argDouble.setValue(d);
            this.mExtra.addPerfettoPointer(argDouble);
            return this;
        }

        public Builder addArg(String str, String str2) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            ArgString argString = this.mArgStringCache.get(str.hashCode());
            if (argString == null || !argString.getName().equals(str)) {
                argString = new ArgString(str);
                this.mArgStringCache.put(str.hashCode(), argString);
            }
            argString.setValue(str2);
            this.mExtra.addPerfettoPointer(argString);
            return this;
        }

        public Builder setFlow(long j) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            this.mFlow.setProcessFlow(j);
            this.mExtra.addPerfettoPointer(this.mFlow);
            return this;
        }

        public Builder setTerminatingFlow(long j) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            this.mTerminatingFlow.setProcessTerminatingFlow(j);
            this.mExtra.addPerfettoPointer(this.mTerminatingFlow);
            return this;
        }

        public Builder usingNamedTrack(long j, String str) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            NamedTrack namedTrack = this.mNamedTrackCache.get(str.hashCode());
            if (namedTrack == null || !namedTrack.getName().equals(str)) {
                namedTrack = new NamedTrack(str, j);
                this.mNamedTrackCache.put(str.hashCode(), namedTrack);
            }
            this.mExtra.addPerfettoPointer(namedTrack);
            return this;
        }

        public Builder usingProcessNamedTrack(String str) {
            return !this.mIsCategoryEnabled ? this : usingNamedTrack(PerfettoTrace.getProcessTrackUuid(), str);
        }

        public Builder usingThreadNamedTrack(long j, String str) {
            return !this.mIsCategoryEnabled ? this : usingNamedTrack(PerfettoTrace.getThreadTrackUuid(j), str);
        }

        public Builder usingCounterTrack(long j, String str) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            CounterTrack counterTrack = this.mCounterTrackCache.get(str.hashCode());
            if (counterTrack == null || !counterTrack.getName().equals(str)) {
                counterTrack = new CounterTrack(str, j);
                this.mCounterTrackCache.put(str.hashCode(), counterTrack);
            }
            this.mExtra.addPerfettoPointer(counterTrack);
            return this;
        }

        public Builder usingProcessCounterTrack(String str) {
            return !this.mIsCategoryEnabled ? this : usingCounterTrack(PerfettoTrace.getProcessTrackUuid(), str);
        }

        public Builder usingThreadCounterTrack(long j, String str) {
            return !this.mIsCategoryEnabled ? this : usingCounterTrack(PerfettoTrace.getThreadTrackUuid(j), str);
        }

        public Builder setCounter(long j) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            this.mCounterInt64.setValue(j);
            this.mExtra.addPerfettoPointer(this.mCounterInt64);
            return this;
        }

        public Builder setCounter(double d) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            this.mCounterDouble.setValue(d);
            this.mExtra.addPerfettoPointer(this.mCounterDouble);
            return this;
        }

        public Builder addField(long j, long j2) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            FieldInt64 fieldInt64 = this.mFieldInt64Cache.get(PerfettoTrackEventExtra.sFieldInt64Supplier);
            fieldInt64.setValue(j, j2);
            this.mExtra.addPerfettoPointer(this.mCurrentContainer, fieldInt64);
            return this;
        }

        public Builder addField(long j, double d) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            FieldDouble fieldDouble = this.mFieldDoubleCache.get(PerfettoTrackEventExtra.sFieldDoubleSupplier);
            fieldDouble.setValue(j, d);
            this.mExtra.addPerfettoPointer(this.mCurrentContainer, fieldDouble);
            return this;
        }

        public Builder addField(long j, String str) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            FieldString fieldString = this.mFieldStringCache.get(PerfettoTrackEventExtra.sFieldStringSupplier);
            fieldString.setValue(j, str);
            this.mExtra.addPerfettoPointer(this.mCurrentContainer, fieldString);
            return this;
        }

        public Builder beginProto() {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            this.mProto.clearFields();
            this.mExtra.addPerfettoPointer(this.mProto);
            return this.mBuilderCache.get(PerfettoTrackEventExtra.sBuilderSupplier).initInternal(this, this.mProto);
        }

        public Builder endProto() {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            Builder builder = this.mParent;
            if (builder == null || this.mCurrentContainer == null) {
                throw new IllegalStateException("No proto to end");
            }
            return builder;
        }

        public Builder beginNested(long j) {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            FieldNested fieldNested = this.mFieldNestedCache.get(PerfettoTrackEventExtra.sFieldNestedSupplier);
            fieldNested.setId(j);
            this.mExtra.addPerfettoPointer(this.mCurrentContainer, fieldNested);
            return this.mBuilderCache.get(PerfettoTrackEventExtra.sBuilderSupplier).initInternal(this, fieldNested);
        }

        public Builder endNested() {
            if (!this.mIsCategoryEnabled) {
                return this;
            }
            Builder builder = this.mParent;
            if (builder == null || this.mCurrentContainer == null) {
                throw new IllegalStateException("No nested field to end");
            }
            return builder;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Builder initInternal(Builder builder, FieldContainer fieldContainer) {
            this.mParent = builder;
            this.mCurrentContainer = fieldContainer;
            this.mIsBuilt = false;
            return this;
        }

        private void checkState() {
            if (this.mIsBuilt) {
                throw new IllegalStateException("This builder has already been used. Create a new builder for another event.");
            }
        }

        private void checkParent() {
            checkState();
            if (!equals(this.mParent)) {
                throw new IllegalStateException("Operation not supported for proto");
            }
        }

        private void checkContainer() {
            checkState();
            if (this.mCurrentContainer == null) {
                throw new IllegalStateException("Field operations must be within beginProto/endProto block");
            }
        }
    }

    public static Builder builder(boolean z) {
        if (z) {
            return sTrackEventExtra.get().mBuilderCache.get(sBuilderSupplier).initInternal(null, null);
        }
        return NO_OP_BUILDER;
    }

    private PerfettoTrackEventExtra() {
        this.mPendingPointers = new ArrayList();
        this.mNamedTrackCache = new RingBuffer<>(5);
        this.mCounterTrackCache = new RingBuffer<>(5);
        this.mArgInt64Cache = new RingBuffer<>(5);
        this.mArgBoolCache = new RingBuffer<>(5);
        this.mArgDoubleCache = new RingBuffer<>(5);
        this.mArgStringCache = new RingBuffer<>(5);
        this.mFieldInt64Cache = new Pool<>(5);
        this.mFieldDoubleCache = new Pool<>(5);
        this.mFieldStringCache = new Pool<>(5);
        this.mFieldNestedCache = new Pool<>(5);
        this.mBuilderCache = new Pool<>(5);
        long native_init = native_init();
        this.mPtr = native_init;
        if (RavenwoodEnvironment.getInstance().isRunningOnRavenwood()) {
            return;
        }
        sRegistry.registerNativeAllocation(this, native_init);
    }

    public long getPtr() {
        return this.mPtr;
    }

    public void addPerfettoPointer(PerfettoPointer perfettoPointer) {
        native_add_arg(this.mPtr, perfettoPointer.getPtr());
        this.mPendingPointers.add(perfettoPointer);
    }

    public void addPerfettoPointer(FieldContainer fieldContainer, PerfettoPointer perfettoPointer) {
        fieldContainer.addField(perfettoPointer);
        this.mPendingPointers.add(perfettoPointer);
    }

    public void reset() {
        native_clear_args(this.mPtr);
        this.mPendingPointers.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CounterInt64 getCounterInt64() {
        if (this.mCounterInt64 == null) {
            this.mCounterInt64 = new CounterInt64();
        }
        return this.mCounterInt64;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CounterDouble getCounterDouble() {
        if (this.mCounterDouble == null) {
            this.mCounterDouble = new CounterDouble();
        }
        return this.mCounterDouble;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Proto getProto() {
        if (this.mProto == null) {
            this.mProto = new Proto();
        }
        return this.mProto;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Flow getFlow() {
        if (this.mFlow == null) {
            this.mFlow = new Flow();
        }
        return this.mFlow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Flow getTerminatingFlow() {
        if (this.mTerminatingFlow == null) {
            this.mTerminatingFlow = new Flow();
        }
        return this.mTerminatingFlow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Flow implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Flow.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @CriticalNative
        private static native long native_init();

        @CriticalNative
        private static native void native_set_process_flow(long j, long j2);

        @CriticalNative
        private static native void native_set_process_terminating_flow(long j, long j2);

        Flow() {
            long native_init = native_init();
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            sRegistry.registerNativeAllocation(this, native_init);
        }

        public void setProcessFlow(long j) {
            native_set_process_flow(this.mPtr, j);
        }

        public void setProcessTerminatingFlow(long j) {
            native_set_process_terminating_flow(this.mPtr, j);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }
    }

    private static class NamedTrack implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(NamedTrack.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final String mName;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @FastNative
        private static native long native_init(long j, String str, long j2);

        NamedTrack(String str, long j) {
            long native_init = native_init(PerfettoTrackEventExtra.sNamedTrackId.incrementAndGet(), str, j);
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            this.mName = str;
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }

        public String getName() {
            return this.mName;
        }
    }

    private static final class CounterTrack implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(CounterTrack.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final String mName;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @FastNative
        private static native long native_init(String str, long j);

        CounterTrack(String str, long j) {
            long native_init = native_init(str, j);
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            this.mName = str;
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }

        public String getName() {
            return this.mName;
        }
    }

    private static final class CounterInt64 implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(CounterInt64.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @CriticalNative
        private static native long native_init();

        @CriticalNative
        private static native void native_set_value(long j, long j2);

        CounterInt64() {
            long native_init = native_init();
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }

        public void setValue(long j) {
            native_set_value(this.mPtr, j);
        }
    }

    private static final class CounterDouble implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(CounterDouble.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @CriticalNative
        private static native long native_init();

        @CriticalNative
        private static native void native_set_value(long j, double d);

        CounterDouble() {
            long native_init = native_init();
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }

        public void setValue(double d) {
            native_set_value(this.mPtr, d);
        }
    }

    private static final class ArgInt64 implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(ArgInt64.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final String mName;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @FastNative
        private static native long native_init(String str);

        @CriticalNative
        private static native void native_set_value(long j, long j2);

        ArgInt64(String str) {
            long native_init = native_init(str);
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            this.mName = str;
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }

        public String getName() {
            return this.mName;
        }

        public void setValue(long j) {
            native_set_value(this.mPtr, j);
        }
    }

    private static final class ArgBool implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(ArgBool.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final String mName;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @FastNative
        private static native long native_init(String str);

        @CriticalNative
        private static native void native_set_value(long j, boolean z);

        ArgBool(String str) {
            long native_init = native_init(str);
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            this.mName = str;
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }

        public String getName() {
            return this.mName;
        }

        public void setValue(boolean z) {
            native_set_value(this.mPtr, z);
        }
    }

    private static final class ArgDouble implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(ArgDouble.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final String mName;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @FastNative
        private static native long native_init(String str);

        @CriticalNative
        private static native void native_set_value(long j, double d);

        ArgDouble(String str) {
            long native_init = native_init(str);
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            this.mName = str;
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }

        public String getName() {
            return this.mName;
        }

        public void setValue(double d) {
            native_set_value(this.mPtr, d);
        }
    }

    private static final class ArgString implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(ArgString.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final String mName;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @FastNative
        private static native long native_init(String str);

        @FastNative
        private static native void native_set_value(long j, String str);

        ArgString(String str) {
            long native_init = native_init(str);
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            this.mName = str;
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }

        public String getName() {
            return this.mName;
        }

        public void setValue(String str) {
            native_set_value(this.mPtr, str);
        }
    }

    private static final class Proto implements PerfettoPointer, FieldContainer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Proto.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private final long mPtr;

        @CriticalNative
        private static native void native_add_field(long j, long j2);

        @CriticalNative
        private static native void native_clear_fields(long j);

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @CriticalNative
        private static native long native_init();

        Proto() {
            long native_init = native_init();
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }

        @Override // android.os.PerfettoTrackEventExtra.FieldContainer
        public void addField(PerfettoPointer perfettoPointer) {
            native_add_field(this.mPtr, perfettoPointer.getPtr());
        }

        public void clearFields() {
            native_clear_fields(this.mPtr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FieldInt64 implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(FieldInt64.class.getClassLoader(), native_delete());
        private final long mFieldPtr;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @CriticalNative
        private static native long native_init();

        @CriticalNative
        private static native void native_set_value(long j, long j2, long j3);

        FieldInt64() {
            long native_init = native_init();
            this.mPtr = native_init;
            this.mFieldPtr = native_get_extra_ptr(native_init);
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mFieldPtr;
        }

        public void setValue(long j, long j2) {
            native_set_value(this.mPtr, j, j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FieldDouble implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(FieldDouble.class.getClassLoader(), native_delete());
        private final long mFieldPtr;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @CriticalNative
        private static native long native_init();

        @CriticalNative
        private static native void native_set_value(long j, long j2, double d);

        FieldDouble() {
            long native_init = native_init();
            this.mPtr = native_init;
            this.mFieldPtr = native_get_extra_ptr(native_init);
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mFieldPtr;
        }

        public void setValue(long j, double d) {
            native_set_value(this.mPtr, j, d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FieldString implements PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(FieldString.class.getClassLoader(), native_delete());
        private final long mFieldPtr;
        private final long mPtr;

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @CriticalNative
        private static native long native_init();

        @FastNative
        private static native void native_set_value(long j, long j2, String str);

        FieldString() {
            long native_init = native_init();
            this.mPtr = native_init;
            this.mFieldPtr = native_get_extra_ptr(native_init);
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mFieldPtr;
        }

        public void setValue(long j, String str) {
            native_set_value(this.mPtr, j, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FieldNested implements PerfettoPointer, FieldContainer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(FieldNested.class.getClassLoader(), native_delete());
        private final long mFieldPtr;
        private final long mPtr;

        @CriticalNative
        private static native void native_add_field(long j, long j2);

        @CriticalNative
        private static native long native_delete();

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        @CriticalNative
        private static native long native_init();

        @CriticalNative
        private static native void native_set_id(long j, long j2);

        FieldNested() {
            long native_init = native_init();
            this.mPtr = native_init;
            this.mFieldPtr = native_get_extra_ptr(native_init);
            sRegistry.registerNativeAllocation(this, native_init);
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mFieldPtr;
        }

        @Override // android.os.PerfettoTrackEventExtra.FieldContainer
        public void addField(PerfettoPointer perfettoPointer) {
            native_add_field(this.mPtr, perfettoPointer.getPtr());
        }

        public void setId(long j) {
            native_set_id(this.mPtr, j);
        }
    }
}
