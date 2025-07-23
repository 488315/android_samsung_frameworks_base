package android.graphics;

import dalvik.annotation.optimization.CriticalNative;
import dalvik.system.VMRuntime;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class PathIterator implements Iterator<Segment> {
    private static final boolean IS_DALVIK = "dalvik".equalsIgnoreCase(System.getProperty("java.vm.name"));
    private static final int POINT_ARRAY_SIZE = 8;
    public static final int VERB_CLOSE = 5;
    public static final int VERB_CONIC = 3;
    public static final int VERB_CUBIC = 4;
    public static final int VERB_DONE = 6;
    public static final int VERB_LINE = 1;
    public static final int VERB_MOVE = 0;
    public static final int VERB_QUAD = 2;
    private int mCachedVerb = -1;
    private boolean mDone = false;
    private final long mNativeIterator;
    private final Path mPath;
    private final int mPathGenerationId;
    private final long mPointsAddress;
    private final float[] mPointsArray;

    @Retention(RetentionPolicy.SOURCE)
    @interface Verb {
    }

    private static native long nCreate(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetFinalizer();

    @CriticalNative
    private static native int nNext(long j, long j2);

    private static native int nNextHost(long j, float[] fArr);

    @CriticalNative
    private static native int nPeek(long j);

    private static class NoImagePreloadHolder {
        private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(PathIterator.class.getClassLoader(), PathIterator.nGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    PathIterator(Path path) {
        this.mPath = path;
        long nCreate = nCreate(path.mNativePath);
        this.mNativeIterator = nCreate;
        this.mPathGenerationId = path.getGenerationId();
        if (IS_DALVIK) {
            VMRuntime runtime = VMRuntime.getRuntime();
            float[] fArr = (float[]) runtime.newNonMovableArray(Float.TYPE, 8);
            this.mPointsArray = fArr;
            this.mPointsAddress = runtime.addressOf(fArr);
        } else {
            this.mPointsArray = new float[8];
            this.mPointsAddress = 0L;
        }
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, nCreate);
    }

    public int next(float[] fArr, int i) {
        if (fArr.length < i + 8) {
            throw new ArrayIndexOutOfBoundsException("points array must be able to hold at least 8 entries");
        }
        int returnVerb = getReturnVerb(this.mCachedVerb);
        this.mCachedVerb = -1;
        System.arraycopy(this.mPointsArray, 0, fArr, i, 8);
        return returnVerb;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.mCachedVerb == -1) {
            this.mCachedVerb = nextInternal();
        }
        return this.mCachedVerb != 6;
    }

    public int peek() {
        if (this.mPathGenerationId != this.mPath.getGenerationId()) {
            throw new ConcurrentModificationException("Iterator cannot be used on modified Path");
        }
        if (this.mDone) {
            return 6;
        }
        return nPeek(this.mNativeIterator);
    }

    private int nextInternal() {
        if (this.mDone) {
            return 6;
        }
        if (this.mPathGenerationId != this.mPath.getGenerationId()) {
            throw new ConcurrentModificationException("Iterator cannot be used on modified Path");
        }
        int nNext = IS_DALVIK ? nNext(this.mNativeIterator, this.mPointsAddress) : nNextHost(this.mNativeIterator, this.mPointsArray);
        if (nNext == 6) {
            this.mDone = true;
        }
        return nNext;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public Segment next() {
        int returnVerb = getReturnVerb(this.mCachedVerb);
        this.mCachedVerb = -1;
        float f = returnVerb == 3 ? this.mPointsArray[6] : 0.0f;
        float[] fArr = new float[8];
        System.arraycopy(this.mPointsArray, 0, fArr, 0, 8);
        return new Segment(returnVerb, fArr, f);
    }

    private int getReturnVerb(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                return nextInternal();
        }
    }

    public static class Segment {
        private final float mConicWeight;
        private final float[] mPoints;
        private final int mVerb;

        public int getVerb() {
            return this.mVerb;
        }

        public float[] getPoints() {
            return this.mPoints;
        }

        public float getConicWeight() {
            return this.mConicWeight;
        }

        Segment(int i, float[] fArr, float f) {
            this.mVerb = i;
            this.mPoints = fArr;
            this.mConicWeight = f;
        }
    }
}
