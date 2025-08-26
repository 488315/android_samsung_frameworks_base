package com.android.internal.os;

import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.util.HeavyHitterSketch;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class BinderCallHeavyHitterWatcher {
    private static final float EPSILON = 1.0E-5f;
    private static final String TAG = "BinderCallHeavyHitterWatcher";
    private static BinderCallHeavyHitterWatcher sInstance;
    private static final Object sLock = new Object();
    private long mBatchStartTimeStamp;
    private HeavyHitterContainer[] mCachedCandidateContainers;
    private int mCachedCandidateContainersIndex;
    private int mCurrentInputSize;
    private boolean mEnabled;
    private HeavyHitterSketch<Integer> mHeavyHitterSketch;
    private int mInputSize;
    private BinderCallHeavyHitterListener mListener;
    private float mThreshold;
    private int mTotalInputSize;
    private final SparseArray<HeavyHitterContainer> mHeavyHitterCandiates = new SparseArray<>();
    private final ArrayList<Integer> mCachedCandidateList = new ArrayList<>();
    private final ArrayList<Float> mCachedCandidateFrequencies = new ArrayList<>();
    private ArraySet<Integer> mCachedCandidateSet = new ArraySet<>();
    private final Object mLock = new Object();

    public interface BinderCallHeavyHitterListener {
        void onHeavyHit(List<HeavyHitterContainer> list, int i, float f, long j);
    }

    public static final class HeavyHitterContainer {
        public Class mClass;
        public int mCode;
        public float mFrequency;
        public int mUid;

        public HeavyHitterContainer() {
        }

        public HeavyHitterContainer(HeavyHitterContainer heavyHitterContainer) {
            this.mUid = heavyHitterContainer.mUid;
            this.mClass = heavyHitterContainer.mClass;
            this.mCode = heavyHitterContainer.mCode;
            this.mFrequency = heavyHitterContainer.mFrequency;
        }

        public boolean equals(Object obj) {
            if (obj != null && (obj instanceof HeavyHitterContainer)) {
                HeavyHitterContainer heavyHitterContainer = (HeavyHitterContainer) obj;
                if (this.mUid == heavyHitterContainer.mUid && this.mClass == heavyHitterContainer.mClass && this.mCode == heavyHitterContainer.mCode && Math.abs(this.mFrequency - heavyHitterContainer.mFrequency) < 1.0E-5f) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return hashCode(this.mUid, this.mClass, this.mCode);
        }

        static int hashCode(int i, Class cls, int i2) {
            return (((i * 31) + cls.hashCode()) * 31) + i2;
        }
    }

    public static BinderCallHeavyHitterWatcher getInstance() {
        BinderCallHeavyHitterWatcher binderCallHeavyHitterWatcher;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new BinderCallHeavyHitterWatcher();
            }
            binderCallHeavyHitterWatcher = sInstance;
        }
        return binderCallHeavyHitterWatcher;
    }

    public void setConfig(boolean z, int i, float f, BinderCallHeavyHitterListener binderCallHeavyHitterListener) {
        synchronized (this.mLock) {
            if (!z) {
                if (this.mEnabled) {
                    resetInternalLocked(null, null, 0, 0, 0.0f, 0);
                    this.mEnabled = false;
                }
                return;
            }
            this.mEnabled = true;
            if (f >= 1.0E-5f && f <= 1.0f) {
                if (i == this.mTotalInputSize && Math.abs(f - this.mThreshold) < 1.0E-5f) {
                    this.mListener = binderCallHeavyHitterListener;
                    return;
                }
                int i2 = (int) (1.0f / f);
                HeavyHitterSketch<Integer> heavyHitterSketchNewDefault = HeavyHitterSketch.newDefault();
                float requiredValidationInputRatio = heavyHitterSketchNewDefault.getRequiredValidationInputRatio();
                int i3 = !Float.isNaN(requiredValidationInputRatio) ? (int) (i * (1.0f - requiredValidationInputRatio)) : i;
                try {
                    heavyHitterSketchNewDefault.setConfig(i, i2);
                    resetInternalLocked(binderCallHeavyHitterListener, heavyHitterSketchNewDefault, i3, i, f, i2);
                } catch (IllegalArgumentException unused) {
                    Log.w(TAG, "Invalid parameter to heavy hitter watcher: " + i + ", " + i2);
                }
            }
        }
    }

    private void resetInternalLocked(BinderCallHeavyHitterListener binderCallHeavyHitterListener, HeavyHitterSketch<Integer> heavyHitterSketch, int i, int i2, float f, int i3) {
        this.mListener = binderCallHeavyHitterListener;
        this.mHeavyHitterSketch = heavyHitterSketch;
        this.mHeavyHitterCandiates.clear();
        this.mCachedCandidateList.clear();
        this.mCachedCandidateFrequencies.clear();
        this.mCachedCandidateSet.clear();
        this.mInputSize = i;
        this.mTotalInputSize = i2;
        this.mCurrentInputSize = 0;
        this.mThreshold = f;
        this.mBatchStartTimeStamp = SystemClock.elapsedRealtime();
        initCachedCandidateContainersLocked(i3);
    }

    private void initCachedCandidateContainersLocked(int i) {
        if (i > 0) {
            this.mCachedCandidateContainers = new HeavyHitterContainer[i];
            int i2 = 0;
            while (true) {
                HeavyHitterContainer[] heavyHitterContainerArr = this.mCachedCandidateContainers;
                if (i2 >= heavyHitterContainerArr.length) {
                    break;
                }
                heavyHitterContainerArr[i2] = new HeavyHitterContainer();
                i2++;
            }
        } else {
            this.mCachedCandidateContainers = null;
        }
        this.mCachedCandidateContainersIndex = 0;
    }

    private HeavyHitterContainer acquireHeavyHitterContainerLocked() {
        HeavyHitterContainer[] heavyHitterContainerArr = this.mCachedCandidateContainers;
        int i = this.mCachedCandidateContainersIndex;
        this.mCachedCandidateContainersIndex = i + 1;
        return heavyHitterContainerArr[i];
    }

    private void releaseHeavyHitterContainerLocked(HeavyHitterContainer heavyHitterContainer) {
        HeavyHitterContainer[] heavyHitterContainerArr = this.mCachedCandidateContainers;
        int i = this.mCachedCandidateContainersIndex - 1;
        this.mCachedCandidateContainersIndex = i;
        heavyHitterContainerArr[i] = heavyHitterContainer;
    }

    public void onTransaction(int i, Class cls, int i2) {
        List<Integer> topHeavyHitters;
        int size;
        synchronized (this.mLock) {
            if (this.mEnabled) {
                HeavyHitterSketch<Integer> heavyHitterSketch = this.mHeavyHitterSketch;
                if (heavyHitterSketch == null) {
                    return;
                }
                int iHashCode = HeavyHitterContainer.hashCode(i, cls, i2);
                heavyHitterSketch.add(Integer.valueOf(iHashCode));
                int i3 = this.mCurrentInputSize + 1;
                this.mCurrentInputSize = i3;
                int i4 = this.mInputSize;
                if (i3 == i4) {
                    heavyHitterSketch.getCandidates(this.mCachedCandidateList);
                    this.mCachedCandidateSet.addAll(this.mCachedCandidateList);
                    this.mCachedCandidateList.clear();
                } else if (i3 > i4 && i3 < this.mTotalInputSize) {
                    if (this.mCachedCandidateSet.contains(Integer.valueOf(iHashCode)) && this.mHeavyHitterCandiates.indexOfKey(iHashCode) < 0) {
                        HeavyHitterContainer heavyHitterContainerAcquireHeavyHitterContainerLocked = acquireHeavyHitterContainerLocked();
                        heavyHitterContainerAcquireHeavyHitterContainerLocked.mUid = i;
                        heavyHitterContainerAcquireHeavyHitterContainerLocked.mClass = cls;
                        heavyHitterContainerAcquireHeavyHitterContainerLocked.mCode = i2;
                        this.mHeavyHitterCandiates.put(iHashCode, heavyHitterContainerAcquireHeavyHitterContainerLocked);
                    }
                } else if (i3 == this.mTotalInputSize) {
                    if (this.mListener != null && (topHeavyHitters = heavyHitterSketch.getTopHeavyHitters(0, this.mCachedCandidateList, this.mCachedCandidateFrequencies)) != null && (size = topHeavyHitters.size()) > 0) {
                        ArrayList arrayList = new ArrayList();
                        for (int i5 = 0; i5 < size; i5++) {
                            HeavyHitterContainer heavyHitterContainer = this.mHeavyHitterCandiates.get(topHeavyHitters.get(i5).intValue());
                            if (heavyHitterContainer != null) {
                                HeavyHitterContainer heavyHitterContainer2 = new HeavyHitterContainer(heavyHitterContainer);
                                heavyHitterContainer2.mFrequency = this.mCachedCandidateFrequencies.get(i5).floatValue();
                                arrayList.add(heavyHitterContainer2);
                            }
                        }
                        this.mListener.onHeavyHit(arrayList, this.mTotalInputSize, this.mThreshold, SystemClock.elapsedRealtime() - this.mBatchStartTimeStamp);
                    }
                    this.mHeavyHitterSketch.reset();
                    this.mHeavyHitterCandiates.clear();
                    this.mCachedCandidateList.clear();
                    this.mCachedCandidateFrequencies.clear();
                    this.mCachedCandidateSet.clear();
                    this.mCachedCandidateContainersIndex = 0;
                    this.mCurrentInputSize = 0;
                    this.mBatchStartTimeStamp = SystemClock.elapsedRealtime();
                }
            }
        }
    }
}
