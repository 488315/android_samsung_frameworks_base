package com.samsung.vekit.Animation;

import android.util.Log;
import com.samsung.vekit.Common.Object.BezierControlPoint;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.KeyFrame;
import com.samsung.vekit.Common.Type.AnimationType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.InterpolationType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Listener.AnimationStatusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class Animation<T> extends Element implements AnimationStatusListener {
    protected AnimationType animationType;
    protected long duration;
    protected boolean enableRollback;
    protected Element firstTarget;
    protected int fromLoop;
    protected ArrayList<KeyFrame<T>> keyFrameList;
    protected AnimationStatusListener listener;
    protected int repeatCount;
    protected T rollbackValue;
    protected long startTime;
    protected int toLoop;

    public enum AnimationStatus {
        INITIALIZED,
        STARTED,
        ANIMATING,
        CANCELED,
        FINISHED
    }

    @Deprecated
    public BezierControlPoint getBezierControlPoint() {
        return null;
    }

    @Deprecated
    public T getFrom() {
        return null;
    }

    @Deprecated
    public InterpolationType getInterpolationType() {
        return null;
    }

    @Deprecated
    public T getTo() {
        return null;
    }

    public void rollback() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Animation<?> setDuration(long j) {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Animation<?> setFrom(T t) {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Animation<?> setKeyFrame(KeyFrame<T> keyFrame) {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Animation<?> setTo(T t) {
        return this;
    }

    public void updateTargetValue(Object obj) {
    }

    protected Animation(VEContext vEContext, AnimationType animationType, int i, String str) {
        super(vEContext, ElementType.ANIMATION, i, str);
        this.keyFrameList = new ArrayList<>();
        setAnimationType(animationType);
        this.startTime = 0L;
        this.duration = 0L;
        this.fromLoop = 0;
        this.toLoop = 0;
        this.repeatCount = 0;
        this.rollbackValue = null;
        this.enableRollback = false;
        this.TAG = getClass().getSimpleName();
    }

    public void setRollbackValue(T t) {
        this.enableRollback = true;
        this.rollbackValue = t;
    }

    public boolean isEnableRollback() {
        return this.firstTarget != null && this.enableRollback;
    }

    public T getRollbackValue() {
        return this.rollbackValue;
    }

    public Element getTarget() {
        return this.firstTarget;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setTarget(Element element) {
        this.firstTarget = element;
        return this;
    }

    public void setListener(AnimationStatusListener animationStatusListener) {
        this.listener = animationStatusListener;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setInterpolationType(InterpolationType interpolationType) {
        Iterator<KeyFrame<T>> it = this.keyFrameList.iterator();
        while (it.hasNext()) {
            it.next().setInterpolationType(interpolationType);
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setBezierControlPoint(float f, float f2, float f3, float f4) {
        Iterator<KeyFrame<T>> it = this.keyFrameList.iterator();
        while (it.hasNext()) {
            it.next().setBezierControlPoint(f, f2, f3, f4);
        }
        return this;
    }

    public AnimationType getAnimationType() {
        return this.animationType;
    }

    protected void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    public long getStartTime() {
        return this.startTime;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setStartTime(long j) {
        this.startTime = j;
        return this;
    }

    public long getDuration() {
        return this.duration;
    }

    public void onAnimationStarted(Object obj) {
        AnimationStatusListener animationStatusListener = this.listener;
        if (animationStatusListener == null) {
            return;
        }
        animationStatusListener.onAnimationStarted(obj);
    }

    public void onAnimationUpdated(Object obj) {
        AnimationStatusListener animationStatusListener = this.listener;
        if (animationStatusListener == null) {
            return;
        }
        animationStatusListener.onAnimationUpdated(obj);
    }

    public void onAnimationFinished(Object obj) {
        AnimationStatusListener animationStatusListener = this.listener;
        if (animationStatusListener == null) {
            return;
        }
        animationStatusListener.onAnimationFinished(obj);
    }

    public void onAnimationCanceled(Object obj) {
        AnimationStatusListener animationStatusListener = this.listener;
        if (animationStatusListener == null) {
            return;
        }
        animationStatusListener.onAnimationCanceled(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setRepeat(int i, int i2, int i3) {
        this.fromLoop = Math.max(i, 0);
        this.toLoop = Math.min(i2, getKeyFrameCount() - 1);
        this.repeatCount = i3;
        calculateDuration();
        return this;
    }

    public int getFromLoop() {
        return this.fromLoop;
    }

    public int getToLoop() {
        return this.toLoop;
    }

    public int getRepeatCount() {
        return this.repeatCount;
    }

    private void calculateDuration() {
        this.duration = 0L;
        if (this.keyFrameList.size() < 2) {
            Log.e(this.TAG, "Invalid KeyFrame size : " + this.keyFrameList.size());
            return;
        }
        this.duration += this.keyFrameList.get(this.keyFrameList.size() - 1).getTime() - this.keyFrameList.get(0).getTime();
        if (this.repeatCount == 0) {
            return;
        }
        this.duration += (this.keyFrameList.get(this.toLoop).getTime() - this.keyFrameList.get(this.fromLoop).getTime()) * this.repeatCount;
    }

    public KeyFrame<T> getFirstKeyFrame() {
        if (this.keyFrameList.size() < 2) {
            Log.e(this.TAG, "Invalid KeyFrame size : " + this.keyFrameList.size());
            return null;
        }
        return this.keyFrameList.get(0);
    }

    public KeyFrame<T> getLastKeyFrame() {
        int size = this.keyFrameList.size();
        if (size < 2) {
            Log.e(this.TAG, "Invalid KeyFrame size : " + size);
            return null;
        }
        return this.keyFrameList.get(size - 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setKeyFrameList(ArrayList<KeyFrame<T>> arrayList) {
        this.keyFrameList.clear();
        this.keyFrameList.addAll(arrayList);
        sortKeyFrameList();
        calculateDuration();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setKeyFrame(KeyFrame<T> keyFrame, KeyFrame<T> keyFrame2) {
        this.keyFrameList.clear();
        this.keyFrameList.add(keyFrame);
        this.keyFrameList.add(keyFrame2);
        sortKeyFrameList();
        calculateDuration();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> addKeyFrame(KeyFrame<T> keyFrame) {
        this.keyFrameList.add(keyFrame);
        sortKeyFrameList();
        calculateDuration();
        return this;
    }

    public boolean removeKeyFrame(KeyFrame<T> keyFrame) {
        if (this.keyFrameList.size() <= 2) {
            Log.e(this.TAG, "Invalid KeyFrame size : " + this.keyFrameList.size());
            return false;
        }
        this.keyFrameList.remove(keyFrame);
        calculateDuration();
        return true;
    }

    public boolean removeKeyFrame(int i) {
        if (this.keyFrameList.size() <= 2) {
            Log.e(this.TAG, "Invalid KeyFrame size : " + this.keyFrameList.size());
            return false;
        }
        this.keyFrameList.remove(i);
        calculateDuration();
        return true;
    }

    public List<KeyFrame<T>> getKeyFrameList() {
        return Collections.unmodifiableList(this.keyFrameList);
    }

    public void clearKeyFrameList() {
        this.keyFrameList.clear();
        this.duration = 0L;
    }

    public int getKeyFrameCount() {
        return this.keyFrameList.size();
    }

    private void sortKeyFrameList() {
        this.keyFrameList.sort(new Comparator() { // from class: com.samsung.vekit.Animation.Animation$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Animation.lambda$sortKeyFrameList$0((KeyFrame) obj, (KeyFrame) obj2);
            }
        });
    }

    static /* synthetic */ int lambda$sortKeyFrameList$0(KeyFrame keyFrame, KeyFrame keyFrame2) {
        if (keyFrame.getTime() > keyFrame2.getTime()) {
            return 1;
        }
        return keyFrame.getTime() < keyFrame2.getTime() ? -1 : 0;
    }
}
