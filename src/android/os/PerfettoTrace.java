package android.os;

import android.os.PerfettoTrackEventExtra;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.ravenwood.RavenwoodEnvironment;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes3.dex */
public final class PerfettoTrace {
    private static final int PERFETTO_TE_TYPE_COUNTER = 4;
    private static final int PERFETTO_TE_TYPE_INSTANT = 3;
    private static final int PERFETTO_TE_TYPE_SLICE_BEGIN = 1;
    private static final int PERFETTO_TE_TYPE_SLICE_END = 2;
    private static final String TAG = "PerfettoTrace";
    private static final boolean IS_FLAG_ENABLED = Flags.perfettoSdkTracingV2();
    private static final AtomicInteger sFlowEventId = new AtomicInteger();
    public static final Category MQ_CATEGORY = new Category("mq");

    public static long getGlobalTrackUuid() {
        return 0L;
    }

    @FastNative
    private static native void native_activate_trigger(String str, int i);

    @CriticalNative
    private static native long native_get_process_track_uuid();

    @CriticalNative
    private static native long native_get_thread_track_uuid(long j);

    @FastNative
    private static native void native_register(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long native_start_session(boolean z, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native byte[] native_stop_session(long j);

    public static final class Category implements PerfettoTrackEventExtra.PerfettoPointer {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Category.class.getClassLoader(), native_delete());
        private final long mExtraPtr;
        private boolean mIsRegistered;
        private final String mName;
        private final long mPtr;
        private final String mSeverity;
        private final String mTag;

        @CriticalNative
        private static native long native_delete();

        private static long native_delete$ravenwood() {
            return 0L;
        }

        @CriticalNative
        private static native long native_get_extra_ptr(long j);

        private static long native_get_extra_ptr$ravenwood(long j) {
            return 0L;
        }

        @FastNative
        private static native long native_init(String str, String str2, String str3);

        private static long native_init$ravenwood(String str, String str2, String str3) {
            return 0L;
        }

        @CriticalNative
        private static native boolean native_is_enabled(long j);

        @CriticalNative
        private static native void native_register(long j);

        @CriticalNative
        private static native void native_unregister(long j);

        public boolean isEnabled$ravenwood() {
            return false;
        }

        public Category(String str) {
            this(str, "", "");
        }

        public Category(String str, String str2) {
            this(str, str2, "");
        }

        public Category(String str, String str2, String str3) {
            this.mName = str;
            this.mTag = str2;
            this.mSeverity = str3;
            long native_init = native_init(str, str2, str3);
            this.mPtr = native_init;
            this.mExtraPtr = native_get_extra_ptr(native_init);
            if (RavenwoodEnvironment.getInstance().isRunningOnRavenwood()) {
                return;
            }
            sRegistry.registerNativeAllocation(this, native_init);
        }

        public Category register() {
            native_register(this.mPtr);
            this.mIsRegistered = true;
            return this;
        }

        public Category unregister() {
            native_unregister(this.mPtr);
            this.mIsRegistered = false;
            return this;
        }

        public boolean isEnabled() {
            return PerfettoTrace.IS_FLAG_ENABLED && native_is_enabled(this.mPtr);
        }

        public boolean isRegistered() {
            return this.mIsRegistered;
        }

        @Override // android.os.PerfettoTrackEventExtra.PerfettoPointer
        public long getPtr() {
            return this.mExtraPtr;
        }
    }

    public static final class Session {
        private final long mPtr;

        public Session(boolean z, byte[] bArr) {
            this.mPtr = PerfettoTrace.native_start_session(z, bArr);
        }

        public byte[] close() {
            return PerfettoTrace.native_stop_session(this.mPtr);
        }
    }

    public static PerfettoTrackEventExtra.Builder instant(Category category, String str) {
        return PerfettoTrackEventExtra.builder(category.isEnabled()).init(3, category).setEventName(str);
    }

    public static PerfettoTrackEventExtra.Builder begin(Category category, String str) {
        return PerfettoTrackEventExtra.builder(category.isEnabled()).init(1, category).setEventName(str);
    }

    public static PerfettoTrackEventExtra.Builder end(Category category) {
        return PerfettoTrackEventExtra.builder(category.isEnabled()).init(2, category);
    }

    public static PerfettoTrackEventExtra.Builder counter(Category category, long j) {
        return PerfettoTrackEventExtra.builder(category.isEnabled()).init(4, category).setCounter(j);
    }

    public static PerfettoTrackEventExtra.Builder counter(Category category, long j, String str) {
        return counter(category, j).usingProcessCounterTrack(str);
    }

    public static PerfettoTrackEventExtra.Builder counter(Category category, double d) {
        return PerfettoTrackEventExtra.builder(category.isEnabled()).init(4, category).setCounter(d);
    }

    public static PerfettoTrackEventExtra.Builder counter(Category category, double d, String str) {
        return counter(category, d).usingProcessCounterTrack(str);
    }

    public static int getFlowId() {
        return sFlowEventId.incrementAndGet();
    }

    public static long getProcessTrackUuid() {
        if (IS_FLAG_ENABLED) {
            return native_get_process_track_uuid();
        }
        return 0L;
    }

    public static long getThreadTrackUuid(long j) {
        if (IS_FLAG_ENABLED) {
            return native_get_thread_track_uuid(j);
        }
        return 0L;
    }

    public static void activateTrigger(String str, int i) {
        if (IS_FLAG_ENABLED) {
            native_activate_trigger(str, i);
        }
    }

    public static void register(boolean z) {
        native_register(z);
    }

    public static void registerCategories() {
        MQ_CATEGORY.register();
    }
}
