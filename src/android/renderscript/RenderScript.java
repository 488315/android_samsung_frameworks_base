package android.renderscript;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.os.SystemProperties;
import android.renderscript.Element;
import android.util.Log;
import android.view.Surface;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Deprecated
/* loaded from: classes3.dex */
public class RenderScript {
    public static final int CREATE_FLAG_LOW_LATENCY = 2;
    public static final int CREATE_FLAG_LOW_POWER = 4;
    public static final int CREATE_FLAG_NONE = 0;
    public static final int CREATE_FLAG_WAIT_FOR_ATTACH = 8;
    static final boolean DEBUG = false;
    static final boolean LOG_ENABLED = false;
    static final String LOG_TAG = "RenderScript_jni";
    static final long TRACE_TAG = 32768;
    private static String mCachePath = null;
    private static ArrayList<RenderScript> mProcessContextList = new ArrayList<>();
    static Method registerNativeAllocation = null;
    static Method registerNativeFree = null;
    static boolean sInitialized = false;
    static final long sMinorVersion = 1;
    static int sPointerSize;
    static Object sRuntime;
    private Context mApplicationContext;
    long mContext;
    volatile Element mElement_ALLOCATION;
    volatile Element mElement_A_8;
    volatile Element mElement_BOOLEAN;
    volatile Element mElement_CHAR_2;
    volatile Element mElement_CHAR_3;
    volatile Element mElement_CHAR_4;
    volatile Element mElement_DOUBLE_2;
    volatile Element mElement_DOUBLE_3;
    volatile Element mElement_DOUBLE_4;
    volatile Element mElement_ELEMENT;
    volatile Element mElement_F16;
    volatile Element mElement_F32;
    volatile Element mElement_F64;
    volatile Element mElement_FLOAT_2;
    volatile Element mElement_FLOAT_3;
    volatile Element mElement_FLOAT_4;
    volatile Element mElement_FONT;
    volatile Element mElement_HALF_2;
    volatile Element mElement_HALF_3;
    volatile Element mElement_HALF_4;
    volatile Element mElement_I16;
    volatile Element mElement_I32;
    volatile Element mElement_I64;
    volatile Element mElement_I8;
    volatile Element mElement_INT_2;
    volatile Element mElement_INT_3;
    volatile Element mElement_INT_4;
    volatile Element mElement_LONG_2;
    volatile Element mElement_LONG_3;
    volatile Element mElement_LONG_4;
    volatile Element mElement_MATRIX_2X2;
    volatile Element mElement_MATRIX_3X3;
    volatile Element mElement_MATRIX_4X4;
    volatile Element mElement_MESH;
    volatile Element mElement_PROGRAM_FRAGMENT;
    volatile Element mElement_PROGRAM_RASTER;
    volatile Element mElement_PROGRAM_STORE;
    volatile Element mElement_PROGRAM_VERTEX;
    volatile Element mElement_RGBA_4444;
    volatile Element mElement_RGBA_5551;
    volatile Element mElement_RGBA_8888;
    volatile Element mElement_RGB_565;
    volatile Element mElement_RGB_888;
    volatile Element mElement_SAMPLER;
    volatile Element mElement_SCRIPT;
    volatile Element mElement_SHORT_2;
    volatile Element mElement_SHORT_3;
    volatile Element mElement_SHORT_4;
    volatile Element mElement_TYPE;
    volatile Element mElement_U16;
    volatile Element mElement_U32;
    volatile Element mElement_U64;
    volatile Element mElement_U8;
    volatile Element mElement_UCHAR_2;
    volatile Element mElement_UCHAR_3;
    volatile Element mElement_UCHAR_4;
    volatile Element mElement_UINT_2;
    volatile Element mElement_UINT_3;
    volatile Element mElement_UINT_4;
    volatile Element mElement_ULONG_2;
    volatile Element mElement_ULONG_3;
    volatile Element mElement_ULONG_4;
    volatile Element mElement_USHORT_2;
    volatile Element mElement_USHORT_3;
    volatile Element mElement_USHORT_4;
    volatile Element mElement_YUV;
    MessageThread mMessageThread;
    ProgramRaster mProgramRaster_CULL_BACK;
    ProgramRaster mProgramRaster_CULL_FRONT;
    ProgramRaster mProgramRaster_CULL_NONE;
    ProgramStore mProgramStore_BLEND_ALPHA_DEPTH_NO_DEPTH;
    ProgramStore mProgramStore_BLEND_ALPHA_DEPTH_TEST;
    ProgramStore mProgramStore_BLEND_NONE_DEPTH_NO_DEPTH;
    ProgramStore mProgramStore_BLEND_NONE_DEPTH_TEST;
    ReentrantReadWriteLock mRWLock;
    volatile Sampler mSampler_CLAMP_LINEAR;
    volatile Sampler mSampler_CLAMP_LINEAR_MIP_LINEAR;
    volatile Sampler mSampler_CLAMP_NEAREST;
    volatile Sampler mSampler_MIRRORED_REPEAT_LINEAR;
    volatile Sampler mSampler_MIRRORED_REPEAT_LINEAR_MIP_LINEAR;
    volatile Sampler mSampler_MIRRORED_REPEAT_NEAREST;
    volatile Sampler mSampler_WRAP_LINEAR;
    volatile Sampler mSampler_WRAP_LINEAR_MIP_LINEAR;
    volatile Sampler mSampler_WRAP_NEAREST;
    private boolean mIsProcessContext = false;
    private int mContextFlags = 0;
    private int mContextSdkVersion = 0;
    private boolean mDestroyed = false;
    RSMessageHandler mMessageCallback = null;
    RSErrorHandler mErrorCallback = null;
    ContextType mContextType = ContextType.NORMAL;

    public static class RSErrorHandler implements Runnable {
        protected String mErrorMessage;
        protected int mErrorNum;

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    public static class RSMessageHandler implements Runnable {
        protected int[] mData;
        protected int mID;
        protected int mLength;

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    static native void _nInit();

    public static long getMinorID() {
        return 1L;
    }

    public static long getMinorVersion() {
        return 1L;
    }

    static native int rsnSystemGetPointerSize();

    native void nContextDeinitToClient(long j);

    native String nContextGetErrorMessage(long j);

    native int nContextGetUserMessage(long j, int[] iArr);

    native void nContextInitToClient(long j);

    native int nContextPeekMessage(long j, int[] iArr);

    native long nDeviceCreate();

    native void nDeviceDestroy(long j);

    native void nDeviceSetConfig(long j, int i, int i2);

    native long rsnAllocationAdapterCreate(long j, long j2, long j3);

    native void rsnAllocationAdapterOffset(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    native void rsnAllocationCopyFromBitmap(long j, long j2, Bitmap bitmap);

    native void rsnAllocationCopyToBitmap(long j, long j2, Bitmap bitmap);

    native long rsnAllocationCreateBitmapBackedAllocation(long j, long j2, int i, Bitmap bitmap, int i2);

    native long rsnAllocationCreateFromBitmap(long j, long j2, int i, Bitmap bitmap, int i2);

    native long rsnAllocationCreateTyped(long j, long j2, int i, int i2, long j3);

    native long rsnAllocationCubeCreateFromBitmap(long j, long j2, int i, Bitmap bitmap, int i2);

    native void rsnAllocationData1D(long j, long j2, int i, int i2, int i3, Object obj, int i4, int i5, int i6, boolean z);

    native void rsnAllocationData2D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, long j3, int i7, int i8, int i9, int i10);

    native void rsnAllocationData2D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, Object obj, int i7, int i8, int i9, boolean z);

    native void rsnAllocationData2D(long j, long j2, int i, int i2, int i3, int i4, Bitmap bitmap);

    native void rsnAllocationData3D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j3, int i8, int i9, int i10, int i11);

    native void rsnAllocationData3D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, Object obj, int i8, int i9, int i10, boolean z);

    native void rsnAllocationElementData(long j, long j2, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6);

    native void rsnAllocationElementRead(long j, long j2, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6);

    native void rsnAllocationGenerateMipmaps(long j, long j2);

    native ByteBuffer rsnAllocationGetByteBuffer(long j, long j2, long[] jArr, int i, int i2, int i3);

    native Surface rsnAllocationGetSurface(long j, long j2);

    native long rsnAllocationGetType(long j, long j2);

    native long rsnAllocationIoReceive(long j, long j2);

    native void rsnAllocationIoSend(long j, long j2);

    native void rsnAllocationRead(long j, long j2, Object obj, int i, int i2, boolean z);

    native void rsnAllocationRead1D(long j, long j2, int i, int i2, int i3, Object obj, int i4, int i5, int i6, boolean z);

    native void rsnAllocationRead2D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, Object obj, int i7, int i8, int i9, boolean z);

    native void rsnAllocationRead3D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, Object obj, int i8, int i9, int i10, boolean z);

    native void rsnAllocationResize1D(long j, long j2, int i);

    native void rsnAllocationSetSurface(long j, long j2, Surface surface);

    native void rsnAllocationSetupBufferQueue(long j, long j2, int i);

    native void rsnAllocationShareBufferQueue(long j, long j2, long j3);

    native void rsnAllocationSyncAll(long j, long j2, int i);

    native void rsnAssignName(long j, long j2, byte[] bArr);

    native long rsnClosureCreate(long j, long j2, long j3, long[] jArr, long[] jArr2, int[] iArr, long[] jArr3, long[] jArr4);

    native void rsnClosureSetArg(long j, long j2, int i, long j3, int i2);

    native void rsnClosureSetGlobal(long j, long j2, long j3, long j4, int i);

    native void rsnContextBindProgramFragment(long j, long j2);

    native void rsnContextBindProgramRaster(long j, long j2);

    native void rsnContextBindProgramStore(long j, long j2);

    native void rsnContextBindProgramVertex(long j, long j2);

    native void rsnContextBindRootScript(long j, long j2);

    native void rsnContextBindSampler(long j, int i, int i2);

    native long rsnContextCreate(long j, int i, int i2, int i3);

    native long rsnContextCreateGL(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, float f, int i13);

    native void rsnContextDestroy(long j);

    native void rsnContextDump(long j, int i);

    native void rsnContextFinish(long j);

    native void rsnContextPause(long j);

    native void rsnContextResume(long j);

    native void rsnContextSendMessage(long j, int i, int[] iArr);

    native void rsnContextSetCacheDir(long j, String str);

    native void rsnContextSetPriority(long j, int i);

    native void rsnContextSetSurface(long j, int i, int i2, Surface surface);

    native void rsnContextSetSurfaceTexture(long j, int i, int i2, SurfaceTexture surfaceTexture);

    native long rsnElementCreate(long j, long j2, int i, boolean z, int i2);

    native long rsnElementCreate2(long j, long[] jArr, String[] strArr, int[] iArr);

    native void rsnElementGetNativeData(long j, long j2, int[] iArr);

    native void rsnElementGetSubElements(long j, long j2, long[] jArr, String[] strArr, int[] iArr);

    native long rsnFileA3DCreateFromAsset(long j, AssetManager assetManager, String str);

    native long rsnFileA3DCreateFromAssetStream(long j, long j2);

    native long rsnFileA3DCreateFromFile(long j, String str);

    native long rsnFileA3DGetEntryByIndex(long j, long j2, int i);

    native void rsnFileA3DGetIndexEntries(long j, long j2, int i, int[] iArr, String[] strArr);

    native int rsnFileA3DGetNumIndexEntries(long j, long j2);

    native long rsnFontCreateFromAsset(long j, AssetManager assetManager, String str, float f, int i);

    native long rsnFontCreateFromAssetStream(long j, String str, float f, int i, long j2);

    native long rsnFontCreateFromFile(long j, String str, float f, int i);

    native String rsnGetName(long j, long j2);

    native long rsnInvokeClosureCreate(long j, long j2, byte[] bArr, long[] jArr, long[] jArr2, int[] iArr);

    native long rsnMeshCreate(long j, long[] jArr, long[] jArr2, int[] iArr);

    native int rsnMeshGetIndexCount(long j, long j2);

    native void rsnMeshGetIndices(long j, long j2, long[] jArr, int[] iArr, int i);

    native int rsnMeshGetVertexBufferCount(long j, long j2);

    native void rsnMeshGetVertices(long j, long j2, long[] jArr, int i);

    native void rsnObjDestroy(long j, long j2);

    native void rsnProgramBindConstants(long j, long j2, int i, long j3);

    native void rsnProgramBindSampler(long j, long j2, int i, long j3);

    native void rsnProgramBindTexture(long j, long j2, int i, long j3);

    native long rsnProgramFragmentCreate(long j, String str, String[] strArr, long[] jArr);

    native long rsnProgramRasterCreate(long j, boolean z, int i);

    native long rsnProgramStoreCreate(long j, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, int i2, int i3);

    native long rsnProgramVertexCreate(long j, String str, String[] strArr, long[] jArr);

    native long rsnSamplerCreate(long j, int i, int i2, int i3, int i4, int i5, float f);

    native void rsnScriptBindAllocation(long j, long j2, long j3, int i);

    native long rsnScriptCCreate(long j, String str, String str2, byte[] bArr, int i);

    native long rsnScriptFieldIDCreate(long j, long j2, int i);

    native void rsnScriptForEach(long j, long j2, int i, long[] jArr, long j3, byte[] bArr, int[] iArr);

    native double rsnScriptGetVarD(long j, long j2, int i);

    native float rsnScriptGetVarF(long j, long j2, int i);

    native int rsnScriptGetVarI(long j, long j2, int i);

    native long rsnScriptGetVarJ(long j, long j2, int i);

    native void rsnScriptGetVarV(long j, long j2, int i, byte[] bArr);

    native long rsnScriptGroup2Create(long j, String str, String str2, long[] jArr);

    native void rsnScriptGroup2Execute(long j, long j2);

    native long rsnScriptGroupCreate(long j, long[] jArr, long[] jArr2, long[] jArr3, long[] jArr4, long[] jArr5);

    native void rsnScriptGroupExecute(long j, long j2);

    native void rsnScriptGroupSetInput(long j, long j2, long j3, long j4);

    native void rsnScriptGroupSetOutput(long j, long j2, long j3, long j4);

    native void rsnScriptIntrinsicBLAS_BNNM(long j, long j2, int i, int i2, int i3, long j3, int i4, long j4, int i5, long j5, int i6, int i7);

    native void rsnScriptIntrinsicBLAS_Complex(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, float f2, long j3, long j4, float f3, float f4, long j5, int i10, int i11, int i12, int i13);

    native void rsnScriptIntrinsicBLAS_Double(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d, long j3, long j4, double d2, long j5, int i10, int i11, int i12, int i13);

    native void rsnScriptIntrinsicBLAS_Single(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, long j3, long j4, float f2, long j5, int i10, int i11, int i12, int i13);

    native void rsnScriptIntrinsicBLAS_Z(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d, double d2, long j3, long j4, double d3, double d4, long j5, int i10, int i11, int i12, int i13);

    native long rsnScriptIntrinsicCreate(long j, int i, long j2);

    native void rsnScriptInvoke(long j, long j2, int i);

    native long rsnScriptInvokeIDCreate(long j, long j2, int i);

    native void rsnScriptInvokeV(long j, long j2, int i, byte[] bArr);

    native long rsnScriptKernelIDCreate(long j, long j2, int i, int i2);

    native void rsnScriptReduce(long j, long j2, int i, long[] jArr, long j3, int[] iArr);

    native void rsnScriptSetTimeZone(long j, long j2, byte[] bArr);

    native void rsnScriptSetVarD(long j, long j2, int i, double d);

    native void rsnScriptSetVarF(long j, long j2, int i, float f);

    native void rsnScriptSetVarI(long j, long j2, int i, int i2);

    native void rsnScriptSetVarJ(long j, long j2, int i, long j3);

    native void rsnScriptSetVarObj(long j, long j2, int i, long j3);

    native void rsnScriptSetVarV(long j, long j2, int i, byte[] bArr);

    native void rsnScriptSetVarVE(long j, long j2, int i, byte[] bArr, long j3, int[] iArr);

    native long rsnTypeCreate(long j, long j2, int i, int i2, int i3, boolean z, boolean z2, int i4);

    native void rsnTypeGetNativeData(long j, long j2, long[] jArr);

    static {
        sInitialized = false;
        if (SystemProperties.getBoolean("config.disable_renderscript", false)) {
            return;
        }
        try {
            Class<?> cls = Class.forName("dalvik.system.VMRuntime");
            Class[] clsArr = new Class[0];
            sRuntime = cls.getDeclaredMethod("getRuntime", null).invoke(null, null);
            registerNativeAllocation = cls.getDeclaredMethod("registerNativeAllocation", Long.TYPE);
            registerNativeFree = cls.getDeclaredMethod("registerNativeFree", Long.TYPE);
            try {
                System.loadLibrary("rs_jni");
                _nInit();
                sInitialized = true;
                sPointerSize = rsnSystemGetPointerSize();
            } catch (UnsatisfiedLinkError e) {
                Log.e(LOG_TAG, "Error loading RS jni library: " + e);
                throw new RSRuntimeException("Error loading RS jni library: " + e);
            }
        } catch (Exception e2) {
            Log.e(LOG_TAG, "Error loading GC methods: " + e2);
            throw new RSRuntimeException("Error loading GC methods: " + e2);
        }
    }

    public enum ContextType {
        NORMAL(0),
        DEBUG(1),
        PROFILE(2);

        int mID;

        ContextType(int i) {
            this.mID = i;
        }
    }

    synchronized long nContextCreateGL(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, float f, int i13) {
        return rsnContextCreateGL(j, i, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, f, i13);
    }

    synchronized long nContextCreate(long j, int i, int i2, int i3) {
        return rsnContextCreate(j, i, i2, i3);
    }

    synchronized void nContextDestroy() {
        validate();
        ReentrantReadWriteLock.WriteLock writeLock = this.mRWLock.writeLock();
        writeLock.lock();
        long j = this.mContext;
        this.mContext = 0L;
        writeLock.unlock();
        rsnContextDestroy(j);
    }

    synchronized void nContextSetSurface(int i, int i2, Surface surface) throws Throwable {
        try {
            try {
                validate();
                rsnContextSetSurface(this.mContext, i, i2, surface);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nContextSetSurfaceTexture(int i, int i2, SurfaceTexture surfaceTexture) throws Throwable {
        try {
            try {
                validate();
                rsnContextSetSurfaceTexture(this.mContext, i, i2, surfaceTexture);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nContextSetPriority(int i) {
        validate();
        rsnContextSetPriority(this.mContext, i);
    }

    synchronized void nContextSetCacheDir(String str) {
        validate();
        rsnContextSetCacheDir(this.mContext, str);
    }

    synchronized void nContextDump(int i) {
        validate();
        rsnContextDump(this.mContext, i);
    }

    synchronized void nContextFinish() {
        validate();
        rsnContextFinish(this.mContext);
    }

    synchronized void nContextSendMessage(int i, int[] iArr) {
        validate();
        rsnContextSendMessage(this.mContext, i, iArr);
    }

    synchronized void nContextBindRootScript(long j) {
        validate();
        rsnContextBindRootScript(this.mContext, j);
    }

    synchronized void nContextBindSampler(int i, int i2) {
        validate();
        rsnContextBindSampler(this.mContext, i, i2);
    }

    synchronized void nContextBindProgramStore(long j) {
        validate();
        rsnContextBindProgramStore(this.mContext, j);
    }

    synchronized void nContextBindProgramFragment(long j) {
        validate();
        rsnContextBindProgramFragment(this.mContext, j);
    }

    synchronized void nContextBindProgramVertex(long j) {
        validate();
        rsnContextBindProgramVertex(this.mContext, j);
    }

    synchronized void nContextBindProgramRaster(long j) {
        validate();
        rsnContextBindProgramRaster(this.mContext, j);
    }

    synchronized void nContextPause() {
        validate();
        rsnContextPause(this.mContext);
    }

    synchronized void nContextResume() {
        validate();
        rsnContextResume(this.mContext);
    }

    synchronized long nClosureCreate(long j, long j2, long[] jArr, long[] jArr2, int[] iArr, long[] jArr3, long[] jArr4) {
        long jRsnClosureCreate;
        validate();
        jRsnClosureCreate = rsnClosureCreate(this.mContext, j, j2, jArr, jArr2, iArr, jArr3, jArr4);
        if (jRsnClosureCreate == 0) {
            throw new RSRuntimeException("Failed creating closure.");
        }
        return jRsnClosureCreate;
    }

    synchronized long nInvokeClosureCreate(long j, byte[] bArr, long[] jArr, long[] jArr2, int[] iArr) {
        long jRsnInvokeClosureCreate;
        validate();
        jRsnInvokeClosureCreate = rsnInvokeClosureCreate(this.mContext, j, bArr, jArr, jArr2, iArr);
        if (jRsnInvokeClosureCreate == 0) {
            throw new RSRuntimeException("Failed creating closure.");
        }
        return jRsnInvokeClosureCreate;
    }

    synchronized void nClosureSetArg(long j, int i, long j2, int i2) {
        validate();
        rsnClosureSetArg(this.mContext, j, i, j2, i2);
    }

    synchronized void nClosureSetGlobal(long j, long j2, long j3, int i) {
        validate();
        rsnClosureSetGlobal(this.mContext, j, j2, j3, i);
    }

    synchronized long nScriptGroup2Create(String str, String str2, long[] jArr) throws Throwable {
        try {
            try {
                validate();
                long jRsnScriptGroup2Create = rsnScriptGroup2Create(this.mContext, str, str2, jArr);
                if (jRsnScriptGroup2Create != 0) {
                    return jRsnScriptGroup2Create;
                }
                throw new RSRuntimeException("Failed creating script group.");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptGroup2Execute(long j) {
        validate();
        rsnScriptGroup2Execute(this.mContext, j);
    }

    synchronized void nAssignName(long j, byte[] bArr) {
        try {
            try {
                validate();
                rsnAssignName(this.mContext, j, bArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized String nGetName(long j) {
        validate();
        return rsnGetName(this.mContext, j);
    }

    void nObjDestroy(long j) {
        long j2 = this.mContext;
        if (j2 != 0) {
            rsnObjDestroy(j2, j);
        }
    }

    synchronized long nElementCreate(long j, int i, boolean z, int i2) throws Throwable {
        try {
            try {
                validate();
                return rsnElementCreate(this.mContext, j, i, z, i2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nElementCreate2(long[] jArr, String[] strArr, int[] iArr) throws Throwable {
        try {
            try {
                validate();
                return rsnElementCreate2(this.mContext, jArr, strArr, iArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized void nElementGetNativeData(long j, int[] iArr) throws Throwable {
        try {
            try {
                validate();
                rsnElementGetNativeData(this.mContext, j, iArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nElementGetSubElements(long j, long[] jArr, String[] strArr, int[] iArr) throws Throwable {
        try {
            try {
                validate();
                rsnElementGetSubElements(this.mContext, j, jArr, strArr, iArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nTypeCreate(long j, int i, int i2, int i3, boolean z, boolean z2, int i4) {
        validate();
        return rsnTypeCreate(this.mContext, j, i, i2, i3, z, z2, i4);
    }

    synchronized void nTypeGetNativeData(long j, long[] jArr) throws Throwable {
        try {
            try {
                validate();
                rsnTypeGetNativeData(this.mContext, j, jArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized long nAllocationCreateTyped(long j, int i, int i2, long j2) {
        validate();
        return rsnAllocationCreateTyped(this.mContext, j, i, i2, j2);
    }

    synchronized long nAllocationCreateFromBitmap(long j, int i, Bitmap bitmap, int i2) throws Throwable {
        try {
            try {
                validate();
                return rsnAllocationCreateFromBitmap(this.mContext, j, i, bitmap, i2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nAllocationCreateBitmapBackedAllocation(long j, int i, Bitmap bitmap, int i2) throws Throwable {
        try {
            try {
                validate();
                return rsnAllocationCreateBitmapBackedAllocation(this.mContext, j, i, bitmap, i2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nAllocationCubeCreateFromBitmap(long j, int i, Bitmap bitmap, int i2) {
        try {
            try {
                validate();
                return rsnAllocationCubeCreateFromBitmap(this.mContext, j, i, bitmap, i2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized void nAllocationCopyToBitmap(long j, Bitmap bitmap) throws Throwable {
        try {
            try {
                validate();
                rsnAllocationCopyToBitmap(this.mContext, j, bitmap);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nAllocationSyncAll(long j, int i) throws Throwable {
        try {
            try {
                validate();
                rsnAllocationSyncAll(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized ByteBuffer nAllocationGetByteBuffer(long j, long[] jArr, int i, int i2, int i3) {
        validate();
        return rsnAllocationGetByteBuffer(this.mContext, j, jArr, i, i2, i3);
    }

    synchronized void nAllocationSetupBufferQueue(long j, int i) {
        try {
            try {
                validate();
                rsnAllocationSetupBufferQueue(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nAllocationShareBufferQueue(long j, long j2) {
        try {
            try {
                validate();
                rsnAllocationShareBufferQueue(this.mContext, j, j2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized Surface nAllocationGetSurface(long j) {
        validate();
        return rsnAllocationGetSurface(this.mContext, j);
    }

    synchronized void nAllocationSetSurface(long j, Surface surface) {
        try {
            try {
                validate();
                rsnAllocationSetSurface(this.mContext, j, surface);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nAllocationIoSend(long j) {
        validate();
        rsnAllocationIoSend(this.mContext, j);
    }

    synchronized long nAllocationIoReceive(long j) {
        validate();
        return rsnAllocationIoReceive(this.mContext, j);
    }

    synchronized void nAllocationGenerateMipmaps(long j) {
        validate();
        rsnAllocationGenerateMipmaps(this.mContext, j);
    }

    synchronized void nAllocationCopyFromBitmap(long j, Bitmap bitmap) throws Throwable {
        try {
            try {
                validate();
                rsnAllocationCopyFromBitmap(this.mContext, j, bitmap);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nAllocationData1D(long j, int i, int i2, int i3, Object obj, int i4, Element.DataType dataType, int i5, boolean z) {
        validate();
        rsnAllocationData1D(this.mContext, j, i, i2, i3, obj, i4, dataType.mID, i5, z);
    }

    synchronized void nAllocationElementData(long j, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6) {
        validate();
        rsnAllocationElementData(this.mContext, j, i, i2, i3, i4, i5, bArr, i6);
    }

    synchronized void nAllocationData2D(long j, int i, int i2, int i3, int i4, int i5, int i6, long j2, int i7, int i8, int i9, int i10) {
        validate();
        rsnAllocationData2D(this.mContext, j, i, i2, i3, i4, i5, i6, j2, i7, i8, i9, i10);
    }

    synchronized void nAllocationData2D(long j, int i, int i2, int i3, int i4, int i5, int i6, Object obj, int i7, Element.DataType dataType, int i8, boolean z) {
        validate();
        rsnAllocationData2D(this.mContext, j, i, i2, i3, i4, i5, i6, obj, i7, dataType.mID, i8, z);
    }

    synchronized void nAllocationData2D(long j, int i, int i2, int i3, int i4, Bitmap bitmap) {
        validate();
        rsnAllocationData2D(this.mContext, j, i, i2, i3, i4, bitmap);
    }

    synchronized void nAllocationData3D(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j2, int i8, int i9, int i10, int i11) {
        validate();
        rsnAllocationData3D(this.mContext, j, i, i2, i3, i4, i5, i6, i7, j2, i8, i9, i10, i11);
    }

    synchronized void nAllocationData3D(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, Object obj, int i8, Element.DataType dataType, int i9, boolean z) {
        validate();
        rsnAllocationData3D(this.mContext, j, i, i2, i3, i4, i5, i6, i7, obj, i8, dataType.mID, i9, z);
    }

    synchronized void nAllocationRead(long j, Object obj, Element.DataType dataType, int i, boolean z) {
        validate();
        rsnAllocationRead(this.mContext, j, obj, dataType.mID, i, z);
    }

    synchronized void nAllocationRead1D(long j, int i, int i2, int i3, Object obj, int i4, Element.DataType dataType, int i5, boolean z) {
        validate();
        rsnAllocationRead1D(this.mContext, j, i, i2, i3, obj, i4, dataType.mID, i5, z);
    }

    synchronized void nAllocationElementRead(long j, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6) {
        validate();
        rsnAllocationElementRead(this.mContext, j, i, i2, i3, i4, i5, bArr, i6);
    }

    synchronized void nAllocationRead2D(long j, int i, int i2, int i3, int i4, int i5, int i6, Object obj, int i7, Element.DataType dataType, int i8, boolean z) {
        validate();
        rsnAllocationRead2D(this.mContext, j, i, i2, i3, i4, i5, i6, obj, i7, dataType.mID, i8, z);
    }

    synchronized void nAllocationRead3D(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, Object obj, int i8, Element.DataType dataType, int i9, boolean z) {
        validate();
        rsnAllocationRead3D(this.mContext, j, i, i2, i3, i4, i5, i6, i7, obj, i8, dataType.mID, i9, z);
    }

    synchronized long nAllocationGetType(long j) {
        validate();
        return rsnAllocationGetType(this.mContext, j);
    }

    synchronized void nAllocationResize1D(long j, int i) throws Throwable {
        try {
            try {
                validate();
                rsnAllocationResize1D(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized long nAllocationAdapterCreate(long j, long j2) throws Throwable {
        try {
            try {
                validate();
                return rsnAllocationAdapterCreate(this.mContext, j, j2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nAllocationAdapterOffset(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        validate();
        rsnAllocationAdapterOffset(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9);
    }

    synchronized long nFileA3DCreateFromAssetStream(long j) {
        validate();
        return rsnFileA3DCreateFromAssetStream(this.mContext, j);
    }

    synchronized long nFileA3DCreateFromFile(String str) {
        validate();
        return rsnFileA3DCreateFromFile(this.mContext, str);
    }

    synchronized long nFileA3DCreateFromAsset(AssetManager assetManager, String str) {
        validate();
        return rsnFileA3DCreateFromAsset(this.mContext, assetManager, str);
    }

    synchronized int nFileA3DGetNumIndexEntries(long j) {
        validate();
        return rsnFileA3DGetNumIndexEntries(this.mContext, j);
    }

    synchronized void nFileA3DGetIndexEntries(long j, int i, int[] iArr, String[] strArr) throws Throwable {
        try {
            try {
                validate();
                rsnFileA3DGetIndexEntries(this.mContext, j, i, iArr, strArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nFileA3DGetEntryByIndex(long j, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnFileA3DGetEntryByIndex(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized long nFontCreateFromFile(String str, float f, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnFontCreateFromFile(this.mContext, str, f, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nFontCreateFromAssetStream(String str, float f, int i, long j) throws Throwable {
        try {
            try {
                validate();
                return rsnFontCreateFromAssetStream(this.mContext, str, f, i, j);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nFontCreateFromAsset(AssetManager assetManager, String str, float f, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnFontCreateFromAsset(this.mContext, assetManager, str, f, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized void nScriptBindAllocation(long j, long j2, int i) throws Throwable {
        try {
            try {
                validate();
                rsnScriptBindAllocation(this.mContext, j, j2, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptSetTimeZone(long j, byte[] bArr) throws Throwable {
        try {
            try {
                validate();
                rsnScriptSetTimeZone(this.mContext, j, bArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptInvoke(long j, int i) throws Throwable {
        try {
            try {
                validate();
                rsnScriptInvoke(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptForEach(long j, int i, long[] jArr, long j2, byte[] bArr, int[] iArr) {
        validate();
        rsnScriptForEach(this.mContext, j, i, jArr, j2, bArr, iArr);
    }

    synchronized void nScriptReduce(long j, int i, long[] jArr, long j2, int[] iArr) {
        validate();
        rsnScriptReduce(this.mContext, j, i, jArr, j2, iArr);
    }

    synchronized void nScriptInvokeV(long j, int i, byte[] bArr) throws Throwable {
        try {
            try {
                validate();
                rsnScriptInvokeV(this.mContext, j, i, bArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptSetVarI(long j, int i, int i2) throws Throwable {
        try {
            try {
                validate();
                rsnScriptSetVarI(this.mContext, j, i, i2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized int nScriptGetVarI(long j, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptGetVarI(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptSetVarJ(long j, int i, long j2) throws Throwable {
        try {
            try {
                validate();
                rsnScriptSetVarJ(this.mContext, j, i, j2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized long nScriptGetVarJ(long j, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptGetVarJ(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptSetVarF(long j, int i, float f) throws Throwable {
        try {
            try {
                validate();
                rsnScriptSetVarF(this.mContext, j, i, f);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized float nScriptGetVarF(long j, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptGetVarF(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptSetVarD(long j, int i, double d) throws Throwable {
        try {
            try {
                validate();
                rsnScriptSetVarD(this.mContext, j, i, d);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized double nScriptGetVarD(long j, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptGetVarD(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptSetVarV(long j, int i, byte[] bArr) throws Throwable {
        try {
            try {
                validate();
                rsnScriptSetVarV(this.mContext, j, i, bArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptGetVarV(long j, int i, byte[] bArr) throws Throwable {
        try {
            try {
                validate();
                rsnScriptGetVarV(this.mContext, j, i, bArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nScriptSetVarVE(long j, int i, byte[] bArr, long j2, int[] iArr) {
        validate();
        rsnScriptSetVarVE(this.mContext, j, i, bArr, j2, iArr);
    }

    synchronized void nScriptSetVarObj(long j, int i, long j2) throws Throwable {
        try {
            try {
                validate();
                rsnScriptSetVarObj(this.mContext, j, i, j2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized long nScriptCCreate(String str, String str2, byte[] bArr, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptCCreate(this.mContext, str, str2, bArr, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nScriptIntrinsicCreate(int i, long j) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptIntrinsicCreate(this.mContext, i, j);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized long nScriptKernelIDCreate(long j, int i, int i2) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptKernelIDCreate(this.mContext, j, i, i2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nScriptInvokeIDCreate(long j, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptInvokeIDCreate(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized long nScriptFieldIDCreate(long j, int i) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptFieldIDCreate(this.mContext, j, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized long nScriptGroupCreate(long[] jArr, long[] jArr2, long[] jArr3, long[] jArr4, long[] jArr5) throws Throwable {
        try {
            try {
                validate();
                return rsnScriptGroupCreate(this.mContext, jArr, jArr2, jArr3, jArr4, jArr5);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized void nScriptGroupSetInput(long j, long j2, long j3) {
        validate();
        rsnScriptGroupSetInput(this.mContext, j, j2, j3);
    }

    synchronized void nScriptGroupSetOutput(long j, long j2, long j3) {
        validate();
        rsnScriptGroupSetOutput(this.mContext, j, j2, j3);
    }

    synchronized void nScriptGroupExecute(long j) {
        validate();
        rsnScriptGroupExecute(this.mContext, j);
    }

    synchronized long nSamplerCreate(int i, int i2, int i3, int i4, int i5, float f) {
        validate();
        return rsnSamplerCreate(this.mContext, i, i2, i3, i4, i5, f);
    }

    synchronized long nProgramStoreCreate(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, int i2, int i3) {
        validate();
        return rsnProgramStoreCreate(this.mContext, z, z2, z3, z4, z5, z6, i, i2, i3);
    }

    synchronized long nProgramRasterCreate(boolean z, int i) {
        validate();
        return rsnProgramRasterCreate(this.mContext, z, i);
    }

    synchronized void nProgramBindConstants(long j, int i, long j2) throws Throwable {
        try {
            try {
                validate();
                rsnProgramBindConstants(this.mContext, j, i, j2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nProgramBindTexture(long j, int i, long j2) throws Throwable {
        try {
            try {
                validate();
                rsnProgramBindTexture(this.mContext, j, i, j2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nProgramBindSampler(long j, int i, long j2) throws Throwable {
        try {
            try {
                validate();
                rsnProgramBindSampler(this.mContext, j, i, j2);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized long nProgramFragmentCreate(String str, String[] strArr, long[] jArr) throws Throwable {
        try {
            try {
                validate();
                return rsnProgramFragmentCreate(this.mContext, str, strArr, jArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nProgramVertexCreate(String str, String[] strArr, long[] jArr) throws Throwable {
        try {
            try {
                validate();
                return rsnProgramVertexCreate(this.mContext, str, strArr, jArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized long nMeshCreate(long[] jArr, long[] jArr2, int[] iArr) throws Throwable {
        try {
            try {
                validate();
                return rsnMeshCreate(this.mContext, jArr, jArr2, iArr);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized int nMeshGetVertexBufferCount(long j) {
        validate();
        return rsnMeshGetVertexBufferCount(this.mContext, j);
    }

    synchronized int nMeshGetIndexCount(long j) {
        validate();
        return rsnMeshGetIndexCount(this.mContext, j);
    }

    synchronized void nMeshGetVertices(long j, long[] jArr, int i) throws Throwable {
        try {
            try {
                validate();
                rsnMeshGetVertices(this.mContext, j, jArr, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    synchronized void nMeshGetIndices(long j, long[] jArr, int[] iArr, int i) throws Throwable {
        try {
            try {
                validate();
                rsnMeshGetIndices(this.mContext, j, jArr, iArr, i);
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    synchronized void nScriptIntrinsicBLAS_Single(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, long j2, long j3, float f2, long j4, int i10, int i11, int i12, int i13) {
        validate();
        rsnScriptIntrinsicBLAS_Single(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9, f, j2, j3, f2, j4, i10, i11, i12, i13);
    }

    synchronized void nScriptIntrinsicBLAS_Double(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d, long j2, long j3, double d2, long j4, int i10, int i11, int i12, int i13) {
        validate();
        rsnScriptIntrinsicBLAS_Double(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9, d, j2, j3, d2, j4, i10, i11, i12, i13);
    }

    synchronized void nScriptIntrinsicBLAS_Complex(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f, float f2, long j2, long j3, float f3, float f4, long j4, int i10, int i11, int i12, int i13) {
        validate();
        rsnScriptIntrinsicBLAS_Complex(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9, f, f2, j2, j3, f3, f4, j4, i10, i11, i12, i13);
    }

    synchronized void nScriptIntrinsicBLAS_Z(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d, double d2, long j2, long j3, double d3, double d4, long j4, int i10, int i11, int i12, int i13) {
        validate();
        rsnScriptIntrinsicBLAS_Z(this.mContext, j, i, i2, i3, i4, i5, i6, i7, i8, i9, d, d2, j2, j3, d3, d4, j4, i10, i11, i12, i13);
    }

    synchronized void nScriptIntrinsicBLAS_BNNM(long j, int i, int i2, int i3, long j2, int i4, long j3, int i5, long j4, int i6, int i7) {
        validate();
        rsnScriptIntrinsicBLAS_BNNM(this.mContext, j, i, i2, i3, j2, i4, j3, i5, j4, i6, i7);
    }

    public void setMessageHandler(RSMessageHandler rSMessageHandler) {
        this.mMessageCallback = rSMessageHandler;
    }

    public RSMessageHandler getMessageHandler() {
        return this.mMessageCallback;
    }

    public void sendMessage(int i, int[] iArr) {
        nContextSendMessage(i, iArr);
    }

    public void setErrorHandler(RSErrorHandler rSErrorHandler) {
        this.mErrorCallback = rSErrorHandler;
    }

    public RSErrorHandler getErrorHandler() {
        return this.mErrorCallback;
    }

    public enum Priority {
        LOW(15),
        NORMAL(-8);

        int mID;

        Priority(int i) {
            this.mID = i;
        }
    }

    void validateObject(BaseObj baseObj) {
        if (baseObj != null && baseObj.mRS != this) {
            throw new RSIllegalArgumentException("Attempting to use an object across contexts.");
        }
    }

    void validate() {
        if (this.mContext == 0) {
            throw new RSInvalidStateException("Calling RS with no Context active.");
        }
    }

    public void setPriority(Priority priority) {
        validate();
        nContextSetPriority(priority.mID);
    }

    static class MessageThread extends Thread {
        static final int RS_ERROR_FATAL_DEBUG = 2048;
        static final int RS_ERROR_FATAL_UNKNOWN = 4096;
        static final int RS_MESSAGE_TO_CLIENT_ERROR = 3;
        static final int RS_MESSAGE_TO_CLIENT_EXCEPTION = 1;
        static final int RS_MESSAGE_TO_CLIENT_NEW_BUFFER = 5;
        static final int RS_MESSAGE_TO_CLIENT_NONE = 0;
        static final int RS_MESSAGE_TO_CLIENT_RESIZE = 2;
        static final int RS_MESSAGE_TO_CLIENT_USER = 4;
        int[] mAuxData;
        RenderScript mRS;
        boolean mRun;

        MessageThread(RenderScript renderScript) {
            super("RSMessageThread");
            this.mRun = true;
            this.mAuxData = new int[2];
            this.mRS = renderScript;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            int[] iArr = new int[16];
            RenderScript renderScript = this.mRS;
            renderScript.nContextInitToClient(renderScript.mContext);
            while (this.mRun) {
                iArr[0] = 0;
                RenderScript renderScript2 = this.mRS;
                int iNContextPeekMessage = renderScript2.nContextPeekMessage(renderScript2.mContext, this.mAuxData);
                int[] iArr2 = this.mAuxData;
                int i = iArr2[1];
                int i2 = iArr2[0];
                if (iNContextPeekMessage == 4) {
                    if ((i >> 2) >= iArr.length) {
                        iArr = new int[(i + 3) >> 2];
                    }
                    RenderScript renderScript3 = this.mRS;
                    if (renderScript3.nContextGetUserMessage(renderScript3.mContext, iArr) != 4) {
                        throw new RSDriverException("Error processing message from RenderScript.");
                    }
                    if (this.mRS.mMessageCallback != null) {
                        this.mRS.mMessageCallback.mData = iArr;
                        this.mRS.mMessageCallback.mID = i2;
                        this.mRS.mMessageCallback.mLength = i;
                        this.mRS.mMessageCallback.run();
                    } else {
                        throw new RSInvalidStateException("Received a message from the script with no message handler installed.");
                    }
                } else if (iNContextPeekMessage == 3) {
                    RenderScript renderScript4 = this.mRS;
                    String strNContextGetErrorMessage = renderScript4.nContextGetErrorMessage(renderScript4.mContext);
                    if (i2 >= 4096 || (i2 >= 2048 && (this.mRS.mContextType != ContextType.DEBUG || this.mRS.mErrorCallback == null))) {
                        throw new RSRuntimeException("Fatal error " + i2 + ", details: " + strNContextGetErrorMessage);
                    }
                    if (this.mRS.mErrorCallback != null) {
                        this.mRS.mErrorCallback.mErrorMessage = strNContextGetErrorMessage;
                        this.mRS.mErrorCallback.mErrorNum = i2;
                        this.mRS.mErrorCallback.run();
                    } else {
                        Log.e(RenderScript.LOG_TAG, "non fatal RS error, " + strNContextGetErrorMessage);
                    }
                } else if (iNContextPeekMessage == 5) {
                    RenderScript renderScript5 = this.mRS;
                    if (renderScript5.nContextGetUserMessage(renderScript5.mContext, iArr) != 5) {
                        throw new RSDriverException("Error processing message from RenderScript.");
                    }
                    Allocation.sendBufferNotification((iArr[1] << 32) + (iArr[0] & 4294967295L));
                } else {
                    try {
                        sleep(1L, 0);
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
    }

    RenderScript(Context context) {
        if (context != null) {
            this.mApplicationContext = context.getApplicationContext();
        }
        this.mRWLock = new ReentrantReadWriteLock();
    }

    public final Context getApplicationContext() {
        return this.mApplicationContext;
    }

    static synchronized String getCachePath() {
        if (mCachePath == null) {
            if (RenderScriptCacheDir.mCacheDir == null) {
                throw new RSRuntimeException("RenderScript code cache directory uninitialized.");
            }
            File file = new File(RenderScriptCacheDir.mCacheDir, "com.android.renderscript.cache");
            mCachePath = file.getAbsolutePath();
            file.mkdirs();
        }
        return mCachePath;
    }

    private static RenderScript internalCreate(Context context, int i, ContextType contextType, int i2) {
        if (!sInitialized) {
            Log.e(LOG_TAG, "RenderScript.create() called when disabled; someone is likely to crash");
            return null;
        }
        if ((i2 & (-15)) != 0) {
            throw new RSIllegalArgumentException("Invalid flags passed.");
        }
        RenderScript renderScript = new RenderScript(context);
        long jNContextCreate = renderScript.nContextCreate(renderScript.nDeviceCreate(), i2, i, contextType.mID);
        renderScript.mContext = jNContextCreate;
        renderScript.mContextType = contextType;
        renderScript.mContextFlags = i2;
        renderScript.mContextSdkVersion = i;
        if (jNContextCreate == 0) {
            throw new RSDriverException("Failed to create RS context.");
        }
        renderScript.nContextSetCacheDir(getCachePath());
        MessageThread messageThread = new MessageThread(renderScript);
        renderScript.mMessageThread = messageThread;
        messageThread.start();
        return renderScript;
    }

    public static RenderScript create(Context context) {
        return create(context, ContextType.NORMAL);
    }

    public static RenderScript create(Context context, ContextType contextType) {
        return create(context, contextType, 0);
    }

    public static RenderScript create(Context context, ContextType contextType, int i) {
        return create(context, context.getApplicationInfo().targetSdkVersion, contextType, i);
    }

    public static RenderScript create(Context context, int i) {
        return create(context, i, ContextType.NORMAL, 0);
    }

    private static RenderScript create(Context context, int i, ContextType contextType, int i2) {
        if (i < 23) {
            return internalCreate(context, i, contextType, i2);
        }
        synchronized (mProcessContextList) {
            Iterator<RenderScript> it = mProcessContextList.iterator();
            while (it.hasNext()) {
                RenderScript next = it.next();
                if (next.mContextType == contextType && next.mContextFlags == i2 && next.mContextSdkVersion == i) {
                    return next;
                }
            }
            RenderScript renderScriptInternalCreate = internalCreate(context, i, contextType, i2);
            renderScriptInternalCreate.mIsProcessContext = true;
            mProcessContextList.add(renderScriptInternalCreate);
            return renderScriptInternalCreate;
        }
    }

    public static void releaseAllContexts() {
        ArrayList<RenderScript> arrayList;
        synchronized (mProcessContextList) {
            arrayList = mProcessContextList;
            mProcessContextList = new ArrayList<>();
        }
        Iterator<RenderScript> it = arrayList.iterator();
        while (it.hasNext()) {
            RenderScript next = it.next();
            next.mIsProcessContext = false;
            next.destroy();
        }
        arrayList.clear();
    }

    public static RenderScript createMultiContext(Context context, ContextType contextType, int i, int i2) {
        return internalCreate(context, i2, contextType, i);
    }

    public void contextDump() {
        validate();
        nContextDump(0);
    }

    public void finish() {
        nContextFinish();
    }

    private void helpDestroy() {
        boolean z;
        boolean z2;
        synchronized (this) {
            z = false;
            if (this.mDestroyed) {
                z2 = false;
            } else {
                this.mDestroyed = true;
                z2 = true;
            }
        }
        if (z2) {
            nContextFinish();
            nContextDeinitToClient(this.mContext);
            this.mMessageThread.mRun = false;
            this.mMessageThread.interrupt();
            boolean z3 = false;
            while (!z) {
                try {
                    this.mMessageThread.join();
                    z = true;
                } catch (InterruptedException unused) {
                    z3 = true;
                }
            }
            if (z3) {
                Log.v(LOG_TAG, "Interrupted during wait for MessageThread to join");
                Thread.currentThread().interrupt();
            }
            nContextDestroy();
        }
    }

    protected void finalize() throws Throwable {
        helpDestroy();
        super.finalize();
    }

    public void destroy() {
        if (this.mIsProcessContext) {
            return;
        }
        validate();
        helpDestroy();
    }

    boolean isAlive() {
        return this.mContext != 0;
    }

    long safeID(BaseObj baseObj) {
        if (baseObj != null) {
            return baseObj.getID(this);
        }
        return 0L;
    }
}
