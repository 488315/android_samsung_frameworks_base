package android.os;

import android.app.AppGlobals;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.os.Parcelable;
import android.service.persistentdata.PersistentDataBlockManager;
import android.util.Log;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.TypedProperties;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.rune.CoreRune;
import dalvik.system.VMDebug;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* loaded from: classes3.dex */
public final class Debug {
    private static final String DEFAULT_TRACE_BODY = "dmtrace";
    private static final String DEFAULT_TRACE_EXTENSION = ".trace";
    public static final String EXTRA_INFO_BY_DEVICECARE = "Auto restart by DeviceCare";
    public static final String EXTRA_INFO_BY_HEAPFULL = "Auto restart by Heap Memory Full";
    private static final boolean IS_PRODUCT_SHIP = true;
    public static final int MEMINFO_ACTIVE = 20;
    public static final int MEMINFO_ACTIVE_ANON = 24;
    public static final int MEMINFO_ACTIVE_FILE = 26;
    public static final int MEMINFO_ANON_PAGES = 30;
    public static final int MEMINFO_AVAILABLE = 23;
    public static final int MEMINFO_BUFFERS = 2;
    public static final int MEMINFO_CACHED = 3;
    public static final int MEMINFO_CMA_FREE = 29;
    public static final int MEMINFO_CMA_TOTAL = 28;
    public static final int MEMINFO_COUNT = 36;
    public static final int MEMINFO_FREE = 1;
    public static final int MEMINFO_GPU_SWAP = 33;
    public static final int MEMINFO_GPU_TOTAL = 32;
    public static final int MEMINFO_INACTIVE = 21;
    public static final int MEMINFO_INACTIVE_ANON = 25;
    public static final int MEMINFO_INACTIVE_FILE = 27;
    public static final int MEMINFO_KERNEL_STACK = 18;
    public static final int MEMINFO_KRECLAIMABLE = 19;
    public static final int MEMINFO_LIGHT_COUNT = 14;
    public static final int MEMINFO_MAPPED = 15;
    public static final int MEMINFO_PAGE_TABLES = 17;
    public static final int MEMINFO_RBIN_ALLOC = 5;
    public static final int MEMINFO_RBIN_CACHED = 7;
    public static final int MEMINFO_RBIN_FREE = 6;
    public static final int MEMINFO_RBIN_TOTAL = 4;
    public static final int MEMINFO_SHMEM = 8;
    public static final int MEMINFO_SLAB = 9;
    public static final int MEMINFO_SLAB_RECLAIMABLE = 10;
    public static final int MEMINFO_SLAB_UNRECLAIMABLE = 11;
    public static final int MEMINFO_SWAP_FREE = 13;
    public static final int MEMINFO_SWAP_TOTAL = 12;
    public static final int MEMINFO_SYSTEM_CACHED = 34;
    public static final int MEMINFO_SYSTEM_UNCACHED = 35;
    public static final int MEMINFO_TOTAL = 0;
    public static final int MEMINFO_UNEVICTABLE = 22;
    public static final int MEMINFO_VM_ALLOC_USED = 16;
    public static final int MEMINFO_ZRAM0 = 31;
    public static final int MEMINFO_ZRAM_TOTAL = 14;
    private static final int MIN_DEBUGGER_IDLE = 1300;
    public static final String PLATFORM_EXCEPTION = "P|EX";
    public static final String PLATFORM_SILENT_RESET = "P|SR";
    public static final String PLATFORM_WATCHDOG = "P|WD";
    public static final int SHOW_CLASSLOADER = 2;
    public static final int SHOW_FULL_DETAIL = 1;
    public static final int SHOW_INITIALIZED = 4;
    private static final int SPIN_DELAY = 200;
    private static final String SYSFS_QEMU_TRACE_STATE = "/sys/qemu_trace/state";
    private static final String TAG = "Debug";

    @Deprecated
    public static final int TRACE_COUNT_ALLOCS = 1;
    private static volatile boolean mWaiting = false;
    private static final String[] FRAMEWORK_FEATURES = {"opengl-tracing", "view-hierarchy", "support_boot_stages", "app_info"};
    private static final TypedProperties debugProperties = null;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DebugProperty {
    }

    @Deprecated
    public static class InstructionCount {
        public boolean collect() {
            return false;
        }

        public int globalMethodInvocations() {
            return 0;
        }

        public int globalTotal() {
            return 0;
        }

        public boolean resetAndStart() {
            return false;
        }
    }

    @Deprecated
    public static void changeDebugPort(int i) {
    }

    public static native boolean dumpJavaBacktraceToFileTimeout(int i, String str, int i2);

    public static native boolean dumpNativeBacktraceToFileTimeout(int i, String str, int i2);

    public static native void dumpNativeHeap(FileDescriptor fileDescriptor);

    public static native void dumpNativeMallocInfo(FileDescriptor fileDescriptor);

    public static final native int getBinderDeathObjectCount();

    public static final native int getBinderLocalObjectCount();

    public static final native int getBinderProxyObjectCount();

    public static native int getBinderReceivedTransactions();

    public static native int getBinderSentTransactions();

    public static native long getDmabufHeapPoolsSizeKb();

    public static native long getDmabufHeapTotalExportedKb();

    public static native long getDmabufMappedSizeKb();

    public static native long getDmabufTotalExportedKb();

    @Deprecated
    public static int getGlobalExternalAllocCount() {
        return 0;
    }

    @Deprecated
    public static int getGlobalExternalAllocSize() {
        return 0;
    }

    @Deprecated
    public static int getGlobalExternalFreedCount() {
        return 0;
    }

    @Deprecated
    public static int getGlobalExternalFreedSize() {
        return 0;
    }

    public static native long getGpuPrivateMemoryKb();

    public static native long getGpuTotalUsageKb();

    public static native long getIonHeapsSizeKb();

    public static native long getIonPoolsSizeKb();

    public static native long getKernelCmaUsageKb();

    public static native void getMemInfo(long[] jArr);

    public static native void getMemoryInfo(MemoryInfo memoryInfo);

    public static native boolean getMemoryInfo(int i, MemoryInfo memoryInfo);

    public static native long getNativeHeapAllocatedSize();

    public static native long getNativeHeapFreeSize();

    public static native long getNativeHeapSize();

    public static native long getPss();

    public static native long getPss(int i, long[] jArr, long[] jArr2);

    public static native long getRss();

    public static native long getRss(int i, long[] jArr);

    @Deprecated
    public static int getThreadExternalAllocCount() {
        return 0;
    }

    @Deprecated
    public static int getThreadExternalAllocSize() {
        return 0;
    }

    public static native String getUnreachableMemory(int i, boolean z);

    public static native long getZramFreeKb();

    public static native boolean isVmapStack();

    public static native boolean logAllocatorStats();

    @Deprecated
    public static void resetGlobalExternalAllocCount() {
    }

    @Deprecated
    public static void resetGlobalExternalAllocSize() {
    }

    @Deprecated
    public static void resetGlobalExternalFreedCount() {
    }

    @Deprecated
    public static void resetGlobalExternalFreedSize() {
    }

    @Deprecated
    public static void resetThreadExternalAllocCount() {
    }

    @Deprecated
    public static void resetThreadExternalAllocSize() {
    }

    @Deprecated
    public static int setAllocationLimit(int i) {
        return -1;
    }

    @Deprecated
    public static int setGlobalAllocationLimit(int i) {
        return -1;
    }

    private Debug() {
    }

    public static class MemoryInfo implements Parcelable {
        public static final Parcelable.Creator<MemoryInfo> CREATOR = new Parcelable.Creator<MemoryInfo>() { // from class: android.os.Debug.MemoryInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MemoryInfo createFromParcel(Parcel parcel) {
                return new MemoryInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MemoryInfo[] newArray(int i) {
                return new MemoryInfo[i];
            }
        };
        public static final int HEAP_DALVIK = 1;
        public static final int HEAP_NATIVE = 2;
        public static final int HEAP_UNKNOWN = 0;
        public static final int NUM_CATEGORIES = 9;
        public static final int NUM_DVK_STATS = 15;
        public static final int NUM_OTHER_STATS = 17;
        public static final int OFFSET_PRIVATE_CLEAN = 5;
        public static final int OFFSET_PRIVATE_DIRTY = 3;
        public static final int OFFSET_PSS = 0;
        public static final int OFFSET_RSS = 2;
        public static final int OFFSET_SHARED_CLEAN = 6;
        public static final int OFFSET_SHARED_DIRTY = 4;
        public static final int OFFSET_SWAPPABLE_PSS = 1;
        public static final int OFFSET_SWAPPED_OUT = 7;
        public static final int OFFSET_SWAPPED_OUT_PSS = 8;
        public static final int OTHER_APK = 8;
        public static final int OTHER_ART = 12;
        public static final int OTHER_ART_APP = 30;
        public static final int OTHER_ART_BOOT = 31;
        public static final int OTHER_ASHMEM = 3;
        public static final int OTHER_CURSOR = 2;
        public static final int OTHER_DALVIK_LARGE = 18;
        public static final int OTHER_DALVIK_NON_MOVING = 20;
        public static final int OTHER_DALVIK_NORMAL = 17;
        public static final int OTHER_DALVIK_OTHER = 0;
        public static final int OTHER_DALVIK_OTHER_ACCOUNTING = 22;
        public static final int OTHER_DALVIK_OTHER_APP_CODE_CACHE = 24;
        public static final int OTHER_DALVIK_OTHER_COMPILER_METADATA = 25;
        public static final int OTHER_DALVIK_OTHER_INDIRECT_REFERENCE_TABLE = 26;
        public static final int OTHER_DALVIK_OTHER_LINEARALLOC = 21;
        public static final int OTHER_DALVIK_OTHER_ZYGOTE_CODE_CACHE = 23;
        public static final int OTHER_DALVIK_ZYGOTE = 19;
        public static final int OTHER_DEX = 10;
        public static final int OTHER_DEX_APP_DEX = 28;
        public static final int OTHER_DEX_APP_VDEX = 29;
        public static final int OTHER_DEX_BOOT_VDEX = 27;
        public static final int OTHER_DVK_STAT_ART_END = 14;
        public static final int OTHER_DVK_STAT_ART_START = 13;
        public static final int OTHER_DVK_STAT_DALVIK_END = 3;
        public static final int OTHER_DVK_STAT_DALVIK_OTHER_END = 9;
        public static final int OTHER_DVK_STAT_DALVIK_OTHER_START = 4;
        public static final int OTHER_DVK_STAT_DALVIK_START = 0;
        public static final int OTHER_DVK_STAT_DEX_END = 12;
        public static final int OTHER_DVK_STAT_DEX_START = 10;
        public static final int OTHER_GL = 15;
        public static final int OTHER_GL_DEV = 4;
        public static final int OTHER_GRAPHICS = 14;
        public static final int OTHER_JAR = 7;
        public static final int OTHER_OAT = 11;
        public static final int OTHER_OTHER_MEMTRACK = 16;
        public static final int OTHER_SO = 6;
        public static final int OTHER_STACK = 1;
        public static final int OTHER_TTF = 9;
        public static final int OTHER_UNKNOWN_DEV = 5;
        public static final int OTHER_UNKNOWN_MAP = 13;
        public int dalvikPrivateClean;
        public int dalvikPrivateDirty;
        public int dalvikPss;
        public int dalvikRss;
        public int dalvikSharedClean;
        public int dalvikSharedDirty;
        public int dalvikSwappablePss;
        public int dalvikSwappedOut;
        public int dalvikSwappedOutPss;
        public boolean hasSwappedOutPss;
        public int nativePrivateClean;
        public int nativePrivateDirty;
        public int nativePss;
        public int nativeRss;
        public int nativeSharedClean;
        public int nativeSharedDirty;
        public int nativeSwappablePss;
        public int nativeSwappedOut;
        public int nativeSwappedOutPss;
        public int otherPrivateClean;
        public int otherPrivateDirty;
        public int otherPss;
        public int otherRss;
        public int otherSharedClean;
        public int otherSharedDirty;
        private int[] otherStats;
        public int otherSwappablePss;
        public int otherSwappedOut;
        public int otherSwappedOutPss;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public MemoryInfo() {
            this.otherStats = new int[288];
        }

        public void set(MemoryInfo memoryInfo) {
            this.dalvikPss = memoryInfo.dalvikPss;
            this.dalvikSwappablePss = memoryInfo.dalvikSwappablePss;
            this.dalvikRss = memoryInfo.dalvikRss;
            this.dalvikPrivateDirty = memoryInfo.dalvikPrivateDirty;
            this.dalvikSharedDirty = memoryInfo.dalvikSharedDirty;
            this.dalvikPrivateClean = memoryInfo.dalvikPrivateClean;
            this.dalvikSharedClean = memoryInfo.dalvikSharedClean;
            this.dalvikSwappedOut = memoryInfo.dalvikSwappedOut;
            this.dalvikSwappedOutPss = memoryInfo.dalvikSwappedOutPss;
            this.nativePss = memoryInfo.nativePss;
            this.nativeSwappablePss = memoryInfo.nativeSwappablePss;
            this.nativeRss = memoryInfo.nativeRss;
            this.nativePrivateDirty = memoryInfo.nativePrivateDirty;
            this.nativeSharedDirty = memoryInfo.nativeSharedDirty;
            this.nativePrivateClean = memoryInfo.nativePrivateClean;
            this.nativeSharedClean = memoryInfo.nativeSharedClean;
            this.nativeSwappedOut = memoryInfo.nativeSwappedOut;
            this.nativeSwappedOutPss = memoryInfo.nativeSwappedOutPss;
            this.otherPss = memoryInfo.otherPss;
            this.otherSwappablePss = memoryInfo.otherSwappablePss;
            this.otherRss = memoryInfo.otherRss;
            this.otherPrivateDirty = memoryInfo.otherPrivateDirty;
            this.otherSharedDirty = memoryInfo.otherSharedDirty;
            this.otherPrivateClean = memoryInfo.otherPrivateClean;
            this.otherSharedClean = memoryInfo.otherSharedClean;
            this.otherSwappedOut = memoryInfo.otherSwappedOut;
            this.otherSwappedOutPss = memoryInfo.otherSwappedOutPss;
            this.hasSwappedOutPss = memoryInfo.hasSwappedOutPss;
            int[] iArr = memoryInfo.otherStats;
            int[] iArr2 = this.otherStats;
            System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
        }

        public int getTotalPss() {
            return this.dalvikPss + this.nativePss + this.otherPss + getTotalSwappedOutPss();
        }

        public int getTotalUss() {
            return this.dalvikPrivateClean + this.dalvikPrivateDirty + this.nativePrivateClean + this.nativePrivateDirty + this.otherPrivateClean + this.otherPrivateDirty;
        }

        public int getTotalSwappablePss() {
            return this.dalvikSwappablePss + this.nativeSwappablePss + this.otherSwappablePss;
        }

        public int getTotalRss() {
            return this.dalvikRss + this.nativeRss + this.otherRss;
        }

        public int getTotalPrivateDirty() {
            return this.dalvikPrivateDirty + this.nativePrivateDirty + this.otherPrivateDirty;
        }

        public int getTotalSharedDirty() {
            return this.dalvikSharedDirty + this.nativeSharedDirty + this.otherSharedDirty;
        }

        public int getTotalPrivateClean() {
            return this.dalvikPrivateClean + this.nativePrivateClean + this.otherPrivateClean;
        }

        public int getTotalSharedClean() {
            return this.dalvikSharedClean + this.nativeSharedClean + this.otherSharedClean;
        }

        public int getTotalSwappedOut() {
            return this.dalvikSwappedOut + this.nativeSwappedOut + this.otherSwappedOut;
        }

        public int getTotalSwappedOutPss() {
            return this.dalvikSwappedOutPss + this.nativeSwappedOutPss + this.otherSwappedOutPss;
        }

        public int getOtherPss(int i) {
            return this.otherStats[i * 9];
        }

        public int getOtherSwappablePss(int i) {
            return this.otherStats[(i * 9) + 1];
        }

        public int getOtherRss(int i) {
            return this.otherStats[(i * 9) + 2];
        }

        public int getOtherPrivateDirty(int i) {
            return this.otherStats[(i * 9) + 3];
        }

        public int getOtherSharedDirty(int i) {
            return this.otherStats[(i * 9) + 4];
        }

        public int getOtherPrivateClean(int i) {
            return this.otherStats[(i * 9) + 5];
        }

        public int getOtherPrivate(int i) {
            return getOtherPrivateClean(i) + getOtherPrivateDirty(i);
        }

        public int getOtherSharedClean(int i) {
            return this.otherStats[(i * 9) + 6];
        }

        public int getOtherSwappedOut(int i) {
            return this.otherStats[(i * 9) + 7];
        }

        public int getOtherSwappedOutPss(int i) {
            return this.otherStats[(i * 9) + 8];
        }

        public static String getOtherLabel(int i) {
            switch (i) {
                case 0:
                    return "Dalvik Other";
                case 1:
                    return "Stack";
                case 2:
                    return "Cursor";
                case 3:
                    return "Ashmem";
                case 4:
                    return "Gfx dev";
                case 5:
                    return "Other dev";
                case 6:
                    return ".so mmap";
                case 7:
                    return ".jar mmap";
                case 8:
                    return ".apk mmap";
                case 9:
                    return ".ttf mmap";
                case 10:
                    return ".dex mmap";
                case 11:
                    return ".oat mmap";
                case 12:
                    return ".art mmap";
                case 13:
                    return "Other mmap";
                case 14:
                    return "EGL mtrack";
                case 15:
                    return "GL mtrack";
                case 16:
                    return "Other mtrack";
                case 17:
                    return ".Heap";
                case 18:
                    return ".LOS";
                case 19:
                    return ".Zygote";
                case 20:
                    return ".NonMoving";
                case 21:
                    return ".LinearAlloc";
                case 22:
                    return ".GC";
                case 23:
                    return ".ZygoteJIT";
                case 24:
                    return ".AppJIT";
                case 25:
                    return ".CompilerMetadata";
                case 26:
                    return ".IndirectRef";
                case 27:
                    return ".Boot vdex";
                case 28:
                    return ".App dex";
                case 29:
                    return ".App vdex";
                case 30:
                    return ".App art";
                case 31:
                    return ".Boot art";
                default:
                    return "????";
            }
        }

        public String getMemoryStat(String str) {
            str.hashCode();
            switch (str) {
                case "summary.java-heap":
                    return Integer.toString(getSummaryJavaHeap());
                case "summary.total-pss":
                    return Integer.toString(getSummaryTotalPss());
                case "summary.private-other":
                    return Integer.toString(getSummaryPrivateOther());
                case "summary.native-heap":
                    return Integer.toString(getSummaryNativeHeap());
                case "summary.stack":
                    return Integer.toString(getSummaryStack());
                case "summary.system":
                    return Integer.toString(getSummarySystem());
                case "summary.code":
                    return Integer.toString(getSummaryCode());
                case "summary.graphics":
                    return Integer.toString(getSummaryGraphics());
                case "summary.total-swap":
                    return Integer.toString(getSummaryTotalSwap());
                default:
                    return null;
            }
        }

        public Map<String, String> getMemoryStats() {
            HashMap map = new HashMap();
            map.put("summary.java-heap", Integer.toString(getSummaryJavaHeap()));
            map.put("summary.native-heap", Integer.toString(getSummaryNativeHeap()));
            map.put("summary.code", Integer.toString(getSummaryCode()));
            map.put("summary.stack", Integer.toString(getSummaryStack()));
            map.put("summary.graphics", Integer.toString(getSummaryGraphics()));
            map.put("summary.private-other", Integer.toString(getSummaryPrivateOther()));
            map.put("summary.system", Integer.toString(getSummarySystem()));
            map.put("summary.total-pss", Integer.toString(getSummaryTotalPss()));
            map.put("summary.total-swap", Integer.toString(getSummaryTotalSwap()));
            return map;
        }

        public int getSummaryJavaHeap() {
            return this.dalvikPrivateDirty + getOtherPrivate(12);
        }

        public int getSummaryNativeHeap() {
            return this.nativePrivateDirty;
        }

        public int getSummaryCode() {
            return getOtherPrivate(6) + getOtherPrivate(7) + getOtherPrivate(8) + getOtherPrivate(9) + getOtherPrivate(10) + getOtherPrivate(11) + getOtherPrivate(23) + getOtherPrivate(24);
        }

        public int getSummaryStack() {
            return getOtherPrivateDirty(1);
        }

        public int getSummaryGraphics() {
            return getOtherPrivate(4) + getOtherPrivate(14) + getOtherPrivate(15);
        }

        public int getSummaryPrivateOther() {
            return (((((getTotalPrivateClean() + getTotalPrivateDirty()) - getSummaryJavaHeap()) - getSummaryNativeHeap()) - getSummaryCode()) - getSummaryStack()) - getSummaryGraphics();
        }

        public int getSummarySystem() {
            return (getTotalPss() - getTotalPrivateClean()) - getTotalPrivateDirty();
        }

        public int getSummaryJavaHeapRss() {
            return this.dalvikRss + getOtherRss(12);
        }

        public int getSummaryNativeHeapRss() {
            return this.nativeRss;
        }

        public int getSummaryCodeRss() {
            return getOtherRss(6) + getOtherRss(7) + getOtherRss(8) + getOtherRss(9) + getOtherRss(10) + getOtherRss(11) + getOtherRss(23) + getOtherRss(24);
        }

        public int getSummaryStackRss() {
            return getOtherRss(1);
        }

        public int getSummaryGraphicsRss() {
            return getOtherRss(4) + getOtherRss(14) + getOtherRss(15);
        }

        public int getSummaryUnknownRss() {
            return ((((getTotalRss() - getSummaryJavaHeapRss()) - getSummaryNativeHeapRss()) - getSummaryCodeRss()) - getSummaryStackRss()) - getSummaryGraphicsRss();
        }

        public int getSummaryTotalPss() {
            return getTotalPss();
        }

        public int getSummaryTotalSwap() {
            return getTotalSwappedOut();
        }

        public int getSummaryTotalSwapPss() {
            return getTotalSwappedOutPss();
        }

        public boolean hasSwappedOutPss() {
            return this.hasSwappedOutPss;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.dalvikPss);
            parcel.writeInt(this.dalvikSwappablePss);
            parcel.writeInt(this.dalvikRss);
            parcel.writeInt(this.dalvikPrivateDirty);
            parcel.writeInt(this.dalvikSharedDirty);
            parcel.writeInt(this.dalvikPrivateClean);
            parcel.writeInt(this.dalvikSharedClean);
            parcel.writeInt(this.dalvikSwappedOut);
            parcel.writeInt(this.dalvikSwappedOutPss);
            parcel.writeInt(this.nativePss);
            parcel.writeInt(this.nativeSwappablePss);
            parcel.writeInt(this.nativeRss);
            parcel.writeInt(this.nativePrivateDirty);
            parcel.writeInt(this.nativeSharedDirty);
            parcel.writeInt(this.nativePrivateClean);
            parcel.writeInt(this.nativeSharedClean);
            parcel.writeInt(this.nativeSwappedOut);
            parcel.writeInt(this.nativeSwappedOutPss);
            parcel.writeInt(this.otherPss);
            parcel.writeInt(this.otherSwappablePss);
            parcel.writeInt(this.otherRss);
            parcel.writeInt(this.otherPrivateDirty);
            parcel.writeInt(this.otherSharedDirty);
            parcel.writeInt(this.otherPrivateClean);
            parcel.writeInt(this.otherSharedClean);
            parcel.writeInt(this.otherSwappedOut);
            parcel.writeInt(this.hasSwappedOutPss ? 1 : 0);
            parcel.writeInt(this.otherSwappedOutPss);
            parcel.writeIntArray(this.otherStats);
        }

        public void readFromParcel(Parcel parcel) {
            this.dalvikPss = parcel.readInt();
            this.dalvikSwappablePss = parcel.readInt();
            this.dalvikRss = parcel.readInt();
            this.dalvikPrivateDirty = parcel.readInt();
            this.dalvikSharedDirty = parcel.readInt();
            this.dalvikPrivateClean = parcel.readInt();
            this.dalvikSharedClean = parcel.readInt();
            this.dalvikSwappedOut = parcel.readInt();
            this.dalvikSwappedOutPss = parcel.readInt();
            this.nativePss = parcel.readInt();
            this.nativeSwappablePss = parcel.readInt();
            this.nativeRss = parcel.readInt();
            this.nativePrivateDirty = parcel.readInt();
            this.nativeSharedDirty = parcel.readInt();
            this.nativePrivateClean = parcel.readInt();
            this.nativeSharedClean = parcel.readInt();
            this.nativeSwappedOut = parcel.readInt();
            this.nativeSwappedOutPss = parcel.readInt();
            this.otherPss = parcel.readInt();
            this.otherSwappablePss = parcel.readInt();
            this.otherRss = parcel.readInt();
            this.otherPrivateDirty = parcel.readInt();
            this.otherSharedDirty = parcel.readInt();
            this.otherPrivateClean = parcel.readInt();
            this.otherSharedClean = parcel.readInt();
            this.otherSwappedOut = parcel.readInt();
            this.hasSwappedOutPss = parcel.readInt() != 0;
            this.otherSwappedOutPss = parcel.readInt();
            this.otherStats = parcel.createIntArray();
        }

        private MemoryInfo(Parcel parcel) {
            this.otherStats = new int[288];
            readFromParcel(parcel);
        }
    }

    public static void suspendAllAndSendVmStart() throws InterruptedException {
        if (VMDebug.isDebuggingEnabled()) {
            System.out.println("Sending WAIT chunk");
            DdmServer.sendChunk(new Chunk(ChunkHandler.type("WAIT"), new byte[]{0}, 0, 1));
            System.out.println("Waiting for debugger first packet");
            setWaitingForDebugger(true);
            while (!isDebuggerConnected()) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException unused) {
                }
            }
            setWaitingForDebugger(false);
            System.out.println("Debug.suspendAllAndSentVmStart");
            VMDebug.suspendAllAndSendVmStart();
            System.out.println("Debug.suspendAllAndSendVmStart, resumed");
        }
    }

    public static void waitForDebugger() throws InterruptedException {
        if (!VMDebug.isDebuggingEnabled() || isDebuggerConnected()) {
            return;
        }
        System.out.println("Sending WAIT chunk");
        DdmServer.sendChunk(new Chunk(ChunkHandler.type("WAIT"), new byte[]{0}, 0, 1));
        setWaitingForDebugger(true);
        while (!isDebuggerConnected()) {
            try {
                Thread.sleep(200L);
            } catch (InterruptedException unused) {
            }
        }
        setWaitingForDebugger(false);
        System.out.println("Debugger has connected");
        while (true) {
            long jLastDebuggerActivity = VMDebug.lastDebuggerActivity();
            if (jLastDebuggerActivity < 0) {
                System.out.println("debugger detached?");
                return;
            }
            if (jLastDebuggerActivity < 1300) {
                System.out.println("waiting for debugger to settle...");
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException unused2) {
                }
            } else {
                System.out.println("debugger has settled (" + jLastDebuggerActivity + NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
        }
    }

    public static boolean waitingForDebugger() {
        return mWaiting;
    }

    public static boolean isDebuggerConnected() {
        return VMDebug.isDebuggerConnected();
    }

    public static String[] getVmFeatureList() {
        return VMDebug.getVmFeatureList();
    }

    private static void setWaitingForDebugger(boolean z) {
        mWaiting = z;
        VMDebug.setWaitingForDebugger(z);
    }

    public static String[] getFeatureList() {
        return FRAMEWORK_FEATURES;
    }

    public static void startNativeTracing() throws Throwable {
        FastPrintWriter fastPrintWriter;
        Throwable th;
        FastPrintWriter fastPrintWriter2 = null;
        try {
            fastPrintWriter = new FastPrintWriter(new FileOutputStream(SYSFS_QEMU_TRACE_STATE));
            try {
                fastPrintWriter.println("1");
                fastPrintWriter.close();
            } catch (Exception unused) {
                fastPrintWriter2 = fastPrintWriter;
                if (fastPrintWriter2 != null) {
                    fastPrintWriter2.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (fastPrintWriter != null) {
                    fastPrintWriter.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
        } catch (Throwable th3) {
            fastPrintWriter = null;
            th = th3;
        }
    }

    public static void stopNativeTracing() throws Throwable {
        FastPrintWriter fastPrintWriter;
        Throwable th;
        FastPrintWriter fastPrintWriter2 = null;
        try {
            fastPrintWriter = new FastPrintWriter(new FileOutputStream(SYSFS_QEMU_TRACE_STATE));
            try {
                fastPrintWriter.println("0");
                fastPrintWriter.close();
            } catch (Exception unused) {
                fastPrintWriter2 = fastPrintWriter;
                if (fastPrintWriter2 != null) {
                    fastPrintWriter2.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (fastPrintWriter != null) {
                    fastPrintWriter.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
        } catch (Throwable th3) {
            fastPrintWriter = null;
            th = th3;
        }
    }

    public static void enableEmulatorTraceOutput() {
        Log.w(TAG, "Unimplemented");
    }

    public static void startMethodTracing() {
        VMDebug.startMethodTracing(fixTracePath(null), 0, 0, false, 0);
    }

    public static void startMethodTracing(String str) {
        startMethodTracing(str, 0, 0);
    }

    public static void startMethodTracing(String str, int i) {
        startMethodTracing(str, i, 0);
    }

    public static void startMethodTracing(String str, int i, int i2) {
        VMDebug.startMethodTracing(fixTracePath(str), i, i2, false, 0);
    }

    public static void startMethodTracingSampling(String str, int i, int i2) {
        VMDebug.startMethodTracing(fixTracePath(str), i, 0, true, i2);
    }

    private static String fixTracePath(String str) {
        File externalStorageDirectory;
        if (str == null || str.charAt(0) != '/') {
            Application initialApplication = AppGlobals.getInitialApplication();
            if (initialApplication != null) {
                externalStorageDirectory = initialApplication.getExternalFilesDir(null);
            } else {
                externalStorageDirectory = Environment.getExternalStorageDirectory();
            }
            if (str == null) {
                str = new File(externalStorageDirectory, DEFAULT_TRACE_BODY).getAbsolutePath();
            } else {
                str = new File(externalStorageDirectory, str).getAbsolutePath();
            }
        }
        if (str.endsWith(DEFAULT_TRACE_EXTENSION)) {
            return str;
        }
        return str + DEFAULT_TRACE_EXTENSION;
    }

    public static void startMethodTracing(String str, FileDescriptor fileDescriptor, int i, int i2, boolean z) {
        VMDebug.startMethodTracing(str, fileDescriptor, i, i2, false, 0, z);
    }

    public static void startMethodTracingDdms(int i, int i2, boolean z, int i3) {
        VMDebug.startMethodTracingDdms(i, i2, z, i3);
    }

    public static int getMethodTracingMode() {
        return VMDebug.getMethodTracingMode();
    }

    public static void stopMethodTracing() {
        VMDebug.stopMethodTracing();
    }

    public static long threadCpuTimeNanos() {
        return VMDebug.threadCpuTimeNanos();
    }

    @Deprecated
    public static void startAllocCounting() {
        VMDebug.startAllocCounting();
    }

    @Deprecated
    public static void stopAllocCounting() {
        VMDebug.stopAllocCounting();
    }

    @Deprecated
    public static int getGlobalAllocCount() {
        return VMDebug.getAllocCount(1);
    }

    @Deprecated
    public static void resetGlobalAllocCount() {
        VMDebug.resetAllocCount(1);
    }

    @Deprecated
    public static int getGlobalAllocSize() {
        return VMDebug.getAllocCount(2);
    }

    @Deprecated
    public static void resetGlobalAllocSize() {
        VMDebug.resetAllocCount(2);
    }

    @Deprecated
    public static int getGlobalFreedCount() {
        return VMDebug.getAllocCount(4);
    }

    @Deprecated
    public static void resetGlobalFreedCount() {
        VMDebug.resetAllocCount(4);
    }

    @Deprecated
    public static int getGlobalFreedSize() {
        return VMDebug.getAllocCount(8);
    }

    @Deprecated
    public static void resetGlobalFreedSize() {
        VMDebug.resetAllocCount(8);
    }

    @Deprecated
    public static int getGlobalGcInvocationCount() {
        return VMDebug.getAllocCount(16);
    }

    @Deprecated
    public static void resetGlobalGcInvocationCount() {
        VMDebug.resetAllocCount(16);
    }

    @Deprecated
    public static int getGlobalClassInitCount() {
        return VMDebug.getAllocCount(32);
    }

    @Deprecated
    public static void resetGlobalClassInitCount() {
        VMDebug.resetAllocCount(32);
    }

    @Deprecated
    public static int getGlobalClassInitTime() {
        return VMDebug.getAllocCount(64);
    }

    @Deprecated
    public static void resetGlobalClassInitTime() {
        VMDebug.resetAllocCount(64);
    }

    @Deprecated
    public static int getThreadAllocCount() {
        return VMDebug.getAllocCount(65536);
    }

    @Deprecated
    public static void resetThreadAllocCount() {
        VMDebug.resetAllocCount(65536);
    }

    @Deprecated
    public static int getThreadAllocSize() {
        return VMDebug.getAllocCount(131072);
    }

    @Deprecated
    public static void resetThreadAllocSize() {
        VMDebug.resetAllocCount(131072);
    }

    @Deprecated
    public static int getThreadGcInvocationCount() {
        return VMDebug.getAllocCount(1048576);
    }

    @Deprecated
    public static void resetThreadGcInvocationCount() {
        VMDebug.resetAllocCount(1048576);
    }

    @Deprecated
    public static void resetAllCounts() {
        VMDebug.resetAllocCount(-1);
    }

    public static String getRuntimeStat(String str) {
        return VMDebug.getRuntimeStat(str);
    }

    public static Map<String, String> getRuntimeStats() {
        return VMDebug.getRuntimeStats();
    }

    @Deprecated
    public static boolean semIsProductDev() {
        return !SystemProperties.getBoolean("ro.product_ship", true);
    }

    public static void printLoadedClasses(int i) {
        VMDebug.printLoadedClasses(i);
    }

    public static int getLoadedClassCount() {
        return VMDebug.getLoadedClassCount();
    }

    public static void dumpHprofData(String str) throws IOException {
        VMDebug.dumpHprofData(str);
    }

    public static void dumpHprofData(String str, FileDescriptor fileDescriptor) throws IOException {
        VMDebug.dumpHprofData(str, fileDescriptor);
    }

    public static void dumpHprofData(String str, String str2) throws IOException {
        if (str2 != null) {
            try {
                Bitmap.dumpAll(str2);
            } finally {
                if (str2 != null) {
                    Bitmap.dumpAll(null);
                }
            }
        }
        VMDebug.dumpHprofData(str);
    }

    public static void dumpHprofData(String str, FileDescriptor fileDescriptor, String str2) throws IOException {
        if (str2 != null) {
            try {
                Bitmap.dumpAll(str2);
            } finally {
                if (str2 != null) {
                    Bitmap.dumpAll(null);
                }
            }
        }
        VMDebug.dumpHprofData(str, fileDescriptor);
    }

    public static void dumpHprofDataDdms() {
        VMDebug.dumpHprofDataDdms();
    }

    public static long countInstancesOfClass(Class cls) {
        return VMDebug.countInstancesOfClass(cls, true);
    }

    public static final void dumpReferenceTables() {
        VMDebug.dumpReferenceTables();
    }

    private static boolean fieldTypeMatches(Field field, Class<?> cls) {
        Class<?> type = field.getType();
        if (type == cls) {
            return true;
        }
        return type == ((Class) cls.getField(AccessibilityButtonChooserActivity.EXTRA_TYPE_TO_CHOOSE).get(null));
    }

    private static void modifyFieldIfSet(Field field, TypedProperties typedProperties, String str) throws IllegalAccessException, IllegalArgumentException {
        if (field.getType() == String.class) {
            int stringInfo = typedProperties.getStringInfo(str);
            if (stringInfo == -2) {
                throw new IllegalArgumentException("Type of " + str + "  does not match field type (" + field.getType() + NavigationBarInflaterView.KEY_CODE_END);
            }
            if (stringInfo == -1) {
                return;
            }
            if (stringInfo == 0) {
                try {
                    field.set(null, null);
                    return;
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("Cannot set field for " + str, e);
                }
            }
            if (stringInfo != 1) {
                throw new IllegalStateException("Unexpected getStringInfo(" + str + ") return value " + stringInfo);
            }
        }
        Object obj = typedProperties.get(str);
        if (obj != null) {
            if (!fieldTypeMatches(field, obj.getClass())) {
                throw new IllegalArgumentException("Type of " + str + " (" + obj.getClass() + ")  does not match field type (" + field.getType() + NavigationBarInflaterView.KEY_CODE_END);
            }
            try {
                field.set(null, obj);
            } catch (IllegalAccessException e2) {
                throw new IllegalArgumentException("Cannot set field for " + str, e2);
            }
        }
    }

    public static void setFieldsOn(Class<?> cls) {
        setFieldsOn(cls, false);
    }

    public static void setFieldsOn(Class<?> cls, boolean z) {
        StringBuilder sb = new StringBuilder("setFieldsOn(");
        sb.append(cls == null ? PerfettoProtoLogImpl.NULL_STRING : cls.getName());
        sb.append(") called in non-DEBUG build");
        Log.wtf(TAG, sb.toString());
    }

    public static boolean dumpService(String str, FileDescriptor fileDescriptor, String[] strArr) {
        IBinder service = ServiceManager.getService(str);
        if (service == null) {
            Log.e(TAG, "Can't find service to dump: " + str);
            return false;
        }
        try {
            service.dump(fileDescriptor, strArr);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Can't dump service: " + str, e);
            return false;
        }
    }

    private static String getCaller(StackTraceElement[] stackTraceElementArr, int i) {
        int i2 = i + 4;
        if (i2 >= stackTraceElementArr.length) {
            return "<bottom of call stack>";
        }
        StackTraceElement stackTraceElement = stackTraceElementArr[i2];
        return stackTraceElement.getClassName() + MediaMetrics.SEPARATOR + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber();
    }

    public static String getCallers(int i) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(getCaller(stackTrace, i2));
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String getCallers(int i, int i2) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        int i3 = i2 + i;
        while (i < i3) {
            sb.append(getCaller(stackTrace, i));
            sb.append(" ");
            i++;
        }
        return sb.toString();
    }

    public static String getCallers(int i, String str) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str);
            sb.append(getCaller(stackTrace, i2));
            sb.append(ShaderAssembler.NEWLINE);
        }
        return sb.toString();
    }

    public static String getCaller() {
        return getCaller(Thread.currentThread().getStackTrace(), 0);
    }

    public static boolean semIsOemUnlockEnabled(Context context) {
        PersistentDataBlockManager persistentDataBlockManager;
        try {
            persistentDataBlockManager = (PersistentDataBlockManager) context.getSystemService(Context.PERSISTENT_DATA_BLOCK_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
            persistentDataBlockManager = null;
        }
        if (persistentDataBlockManager == null) {
            Log.e(TAG, "PersistentDataBlockManager is null ");
            return false;
        }
        return persistentDataBlockManager.getOemUnlockEnabled();
    }

    public static synchronized void saveResetReason(String str, String str2) {
        if (str != null) {
            SystemProperties.set("sys.reset_reason", str);
            SystemProperties.set("sys.reset_info", str2.substring(0, Math.min(str2.length(), 91)));
            SystemProperties.set("ctl.start", "resetreason");
        } else {
            Log.d(TAG, "!@ saveResetReason, but reason is null");
        }
    }

    public static void attachJvmtiAgent(String str, String str2, ClassLoader classLoader) throws IOException {
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(!str.contains("="));
        if (str2 == null) {
            VMDebug.attachAgent(str, classLoader);
            return;
        }
        VMDebug.attachAgent(str + "=" + str2, classLoader);
    }

    public static synchronized void saveDump(BugreportParams bugreportParams, String str) {
        String str2 = SystemProperties.get("dumpstate.is_running", "0");
        String str3 = SystemProperties.get("bugreport.mode", "default");
        String str4 = "default";
        int mode = bugreportParams.getMode();
        if (mode == 11) {
            str4 = "sys_error";
        } else if (mode == 16) {
            str4 = "app_anr";
        } else if (mode == 13) {
            str4 = "sys_watchdog";
        } else if (mode == 14) {
            str4 = "app_error";
        }
        String str5 = SystemProperties.get("sys.reset_reason", "UNKNOWN");
        if ("sys_error".equals(str4) && PLATFORM_SILENT_RESET.equals(str5)) {
            Log.d(TAG, "No need to trigger dumpstate in PF_SR case");
            return;
        }
        if (CoreRune.IS_DEBUG_LEVEL_LOW) {
            try {
                if (str4.startsWith("app") && (str == null || (!str.contains("com.sec.") && !str.contains("com.samsung.") && !str.contains("com.android.phone")))) {
                    Log.d(TAG, "low && ship && 3rdparty app crash, do not dump");
                    return;
                }
            } catch (NullPointerException unused) {
                Log.d(TAG, "NullPointerException Occurred from saveDump");
                return;
            }
        }
        if ("1".equals(str2)) {
            if (!str3.startsWith(Notification.CATEGORY_SYSTEM) && str4.startsWith(Notification.CATEGORY_SYSTEM)) {
                Log.d(TAG, "cancel previous dumsptate, and start new one");
                SystemProperties.set("ctl.stop", "bugreportm");
                SystemProperties.set("ctl.stop", "bugreportd");
                SystemProperties.set("ctl.stop", "dumpstate");
                SystemProperties.set("ctl.stop", "dumpstatez");
            } else {
                Log.d(TAG, "dumpstate is already running, so skip");
                return;
            }
        }
        SystemProperties.set("bugreport.mode", str4);
        SystemProperties.set("dumpstate.process", str != null ? str.substring(0, Math.min(str.length(), 91)) : null);
        SystemProperties.set("ctl.start", "bugreportm");
    }
}
