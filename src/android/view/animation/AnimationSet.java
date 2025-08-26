package android.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class AnimationSet extends Animation {
    private static final int PROPERTY_CHANGE_BOUNDS_MASK = 128;
    private static final int PROPERTY_DURATION_MASK = 32;
    private static final int PROPERTY_FILL_AFTER_MASK = 1;
    private static final int PROPERTY_FILL_BEFORE_MASK = 2;
    private static final int PROPERTY_MORPH_MATRIX_MASK = 64;
    private static final int PROPERTY_REPEAT_MODE_MASK = 4;
    private static final int PROPERTY_SHARE_INTERPOLATOR_MASK = 16;
    private static final int PROPERTY_START_OFFSET_MASK = 8;
    private ArrayList<Animation> mAnimations;
    private boolean mDirty;
    private int mFlags;
    private boolean mHasAlpha;
    private long mLastEnd;
    private long[] mStoredOffsets;
    private Transformation mTempTransformation;

    public AnimationSet(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFlags = 0;
        this.mAnimations = new ArrayList<>();
        this.mTempTransformation = new Transformation();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AnimationSet);
        setFlag(16, typedArrayObtainStyledAttributes.getBoolean(1, true));
        init();
        if (context.getApplicationInfo().targetSdkVersion >= 14) {
            if (typedArrayObtainStyledAttributes.hasValue(0)) {
                this.mFlags |= 32;
            }
            if (typedArrayObtainStyledAttributes.hasValue(2)) {
                this.mFlags = 2 | this.mFlags;
            }
            if (typedArrayObtainStyledAttributes.hasValue(3)) {
                this.mFlags |= 1;
            }
            if (typedArrayObtainStyledAttributes.hasValue(5)) {
                this.mFlags |= 4;
            }
            if (typedArrayObtainStyledAttributes.hasValue(4)) {
                this.mFlags |= 8;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public AnimationSet(boolean z) {
        this.mFlags = 0;
        this.mAnimations = new ArrayList<>();
        this.mTempTransformation = new Transformation();
        setFlag(16, z);
        init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.animation.Animation
    /* renamed from: clone */
    public AnimationSet mo6227clone() throws CloneNotSupportedException {
        AnimationSet animationSet = (AnimationSet) super.mo6227clone();
        animationSet.mTempTransformation = new Transformation();
        animationSet.mAnimations = new ArrayList<>();
        int size = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        for (int i = 0; i < size; i++) {
            animationSet.mAnimations.add(arrayList.get(i).mo6227clone());
        }
        return animationSet;
    }

    private void setFlag(int i, boolean z) {
        if (z) {
            this.mFlags = i | this.mFlags;
        } else {
            this.mFlags = (~i) & this.mFlags;
        }
    }

    private void init() {
        this.mStartTime = 0L;
    }

    @Override // android.view.animation.Animation
    public void setFillAfter(boolean z) {
        this.mFlags |= 1;
        super.setFillAfter(z);
    }

    @Override // android.view.animation.Animation
    public void setFillBefore(boolean z) {
        this.mFlags |= 2;
        super.setFillBefore(z);
    }

    @Override // android.view.animation.Animation
    public void setRepeatMode(int i) {
        this.mFlags |= 4;
        super.setRepeatMode(i);
    }

    @Override // android.view.animation.Animation
    public void setStartOffset(long j) {
        this.mFlags |= 8;
        super.setStartOffset(j);
    }

    @Override // android.view.animation.Animation
    public boolean hasAlpha() {
        if (this.mDirty) {
            int i = 0;
            this.mHasAlpha = false;
            this.mDirty = false;
            int size = this.mAnimations.size();
            ArrayList<Animation> arrayList = this.mAnimations;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (arrayList.get(i).hasAlpha()) {
                    this.mHasAlpha = true;
                    break;
                }
                i++;
            }
        }
        return this.mHasAlpha;
    }

    @Override // android.view.animation.Animation
    public void setDuration(long j) {
        this.mFlags |= 32;
        super.setDuration(j);
        this.mLastEnd = this.mStartOffset + this.mDuration;
    }

    public void addAnimation(Animation animation) {
        this.mAnimations.add(animation);
        if ((this.mFlags & 64) == 0 && animation.willChangeTransformationMatrix()) {
            this.mFlags |= 64;
        }
        if ((this.mFlags & 128) == 0 && animation.willChangeBounds()) {
            this.mFlags |= 128;
        }
        if ((this.mFlags & 32) == 32) {
            this.mLastEnd = this.mStartOffset + this.mDuration;
        } else if (this.mAnimations.size() == 1) {
            this.mDuration = animation.getStartOffset() + animation.getDuration();
            this.mLastEnd = this.mStartOffset + this.mDuration;
        } else {
            long jMax = Math.max(this.mLastEnd, this.mStartOffset + animation.getStartOffset() + animation.getDuration());
            this.mLastEnd = jMax;
            this.mDuration = jMax - this.mStartOffset;
        }
        this.mDirty = true;
    }

    @Override // android.view.animation.Animation
    public void setStartTime(long j) {
        super.setStartTime(j);
        int size = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        for (int i = 0; i < size; i++) {
            arrayList.get(i).setStartTime(j);
        }
    }

    @Override // android.view.animation.Animation
    public long getStartTime() {
        int size = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        long jMin = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            jMin = Math.min(jMin, arrayList.get(i).getStartTime());
        }
        return jMin;
    }

    @Override // android.view.animation.Animation
    public void restrictDuration(long j) {
        super.restrictDuration(j);
        ArrayList<Animation> arrayList = this.mAnimations;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).restrictDuration(j);
        }
    }

    @Override // android.view.animation.Animation
    public long getDuration() {
        ArrayList<Animation> arrayList = this.mAnimations;
        int size = arrayList.size();
        if ((this.mFlags & 32) == 32) {
            return this.mDuration;
        }
        long jMax = 0;
        for (int i = 0; i < size; i++) {
            jMax = Math.max(jMax, arrayList.get(i).getDuration());
        }
        return jMax;
    }

    @Override // android.view.animation.Animation
    public long computeDurationHint() {
        int size = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        long j = 0;
        for (int i = size - 1; i >= 0; i--) {
            long jComputeDurationHint = arrayList.get(i).computeDurationHint();
            if (jComputeDurationHint > j) {
                j = jComputeDurationHint;
            }
        }
        return j;
    }

    @Override // android.view.animation.Animation
    public void initializeInvalidateRegion(int i, int i2, int i3, int i4) {
        RectF rectF = this.mPreviousRegion;
        rectF.set(i, i2, i3, i4);
        rectF.inset(-1.0f, -1.0f);
        if (this.mFillBefore) {
            int size = this.mAnimations.size();
            ArrayList<Animation> arrayList = this.mAnimations;
            Transformation transformation = this.mTempTransformation;
            Transformation transformation2 = this.mPreviousTransformation;
            for (int i5 = size - 1; i5 >= 0; i5--) {
                Animation animation = arrayList.get(i5);
                if (!animation.isFillEnabled() || animation.getFillBefore() || animation.getStartOffset() == 0) {
                    transformation.clear();
                    Interpolator interpolator = animation.mInterpolator;
                    animation.applyTransformation(interpolator != null ? interpolator.getInterpolation(0.0f) : 0.0f, transformation);
                    transformation2.compose(transformation);
                }
            }
        }
    }

    @Override // android.view.animation.Animation
    public void getTransformationAt(float f, Transformation transformation) {
        Transformation transformation2 = this.mTempTransformation;
        for (int size = this.mAnimations.size() - 1; size >= 0; size--) {
            Animation animation = this.mAnimations.get(size);
            transformation2.clear();
            animation.getTransformationAt(f, transformation2);
            transformation.compose(transformation2);
        }
    }

    @Override // android.view.animation.Animation
    public boolean getTransformation(long j, Transformation transformation) {
        int size = this.mAnimations.size();
        ArrayList<Animation> arrayList = this.mAnimations;
        Transformation transformation2 = this.mTempTransformation;
        transformation.clear();
        boolean z = true;
        boolean z2 = false;
        boolean z3 = false;
        for (int i = size - 1; i >= 0; i--) {
            Animation animation = arrayList.get(i);
            transformation2.clear();
            z3 = animation.getTransformation(j, transformation2, getScaleFactor()) || z3;
            transformation.compose(transformation2);
            z2 = z2 || animation.hasStarted();
            z = animation.hasEnded() && z;
        }
        if (z2 && !this.mStarted) {
            dispatchAnimationStart();
            this.mStarted = true;
        }
        if (z != this.mEnded) {
            dispatchAnimationEnd();
            this.mEnded = z;
        }
        return z3;
    }

    @Override // android.view.animation.Animation
    public void scaleCurrentDuration(float f) {
        ArrayList<Animation> arrayList = this.mAnimations;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).scaleCurrentDuration(f);
        }
    }

    @Override // android.view.animation.Animation
    public void initialize(int i, int i2, int i3, int i4) {
        long j;
        long[] jArr;
        ArrayList<Animation> arrayList;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        super.initialize(i, i2, i3, i4);
        int i10 = this.mFlags;
        boolean z = (i10 & 32) == 32;
        boolean z2 = (i10 & 1) == 1;
        boolean z3 = (i10 & 2) == 2;
        boolean z4 = (i10 & 4) == 4;
        boolean z5 = (i10 & 16) == 16;
        boolean z6 = (i10 & 8) == 8;
        if (z5) {
            ensureInterpolator();
        }
        ArrayList<Animation> arrayList2 = this.mAnimations;
        int size = arrayList2.size();
        long j2 = this.mDuration;
        boolean z7 = this.mFillAfter;
        boolean z8 = this.mFillBefore;
        int i11 = this.mRepeatMode;
        Interpolator interpolator = this.mInterpolator;
        boolean z9 = z6;
        long j3 = this.mStartOffset;
        boolean z10 = z;
        long[] jArr2 = this.mStoredOffsets;
        if (z9) {
            j = j3;
            if (jArr2 == null || jArr2.length != size) {
                jArr2 = new long[size];
                this.mStoredOffsets = jArr2;
            }
        } else {
            j = j3;
            if (jArr2 != null) {
                jArr2 = null;
                this.mStoredOffsets = null;
            }
        }
        int i12 = 0;
        while (i12 < size) {
            Animation animation = arrayList2.get(i12);
            if (z10) {
                animation.setDuration(j2);
            }
            if (z2) {
                animation.setFillAfter(z7);
            }
            if (z3) {
                animation.setFillBefore(z8);
            }
            if (z4) {
                animation.setRepeatMode(i11);
            }
            if (z5) {
                animation.setInterpolator(interpolator);
            }
            if (z9) {
                long startOffset = animation.getStartOffset();
                jArr = jArr2;
                animation.setStartOffset(startOffset + j);
                jArr[i12] = startOffset;
                arrayList = arrayList2;
                i8 = i;
                i5 = i2;
                i7 = i12;
                i9 = i3;
                i6 = i4;
            } else {
                jArr = jArr2;
                arrayList = arrayList2;
                i5 = i2;
                i6 = i4;
                i7 = i12;
                i8 = i;
                i9 = i3;
            }
            animation.initialize(i8, i5, i9, i6);
            jArr2 = jArr;
            i12 = i7 + 1;
            arrayList2 = arrayList;
        }
    }

    @Override // android.view.animation.Animation
    public void reset() {
        super.reset();
        restoreChildrenStartOffset();
    }

    void restoreChildrenStartOffset() {
        long[] jArr = this.mStoredOffsets;
        if (jArr == null) {
            return;
        }
        ArrayList<Animation> arrayList = this.mAnimations;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).setStartOffset(jArr[i]);
        }
    }

    public List<Animation> getAnimations() {
        return this.mAnimations;
    }

    @Override // android.view.animation.Animation
    public boolean willChangeTransformationMatrix() {
        return (this.mFlags & 64) == 64;
    }

    @Override // android.view.animation.Animation
    public boolean willChangeBounds() {
        return (this.mFlags & 128) == 128;
    }

    @Override // android.view.animation.Animation
    public int getExtensionEdges() {
        Iterator<Animation> it = this.mAnimations.iterator();
        int extensionEdges = 0;
        while (it.hasNext()) {
            extensionEdges |= it.next().getExtensionEdges();
        }
        return extensionEdges;
    }
}
