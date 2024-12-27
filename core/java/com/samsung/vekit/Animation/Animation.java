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

    protected Animation(VEContext context, AnimationType type, int id, String name) {
        super(context, ElementType.ANIMATION, id, name);
        this.keyFrameList = new ArrayList<>();
        setAnimationType(type);
        this.startTime = 0L;
        this.duration = 0L;
        this.fromLoop = 0;
        this.toLoop = 0;
        this.repeatCount = 0;
        this.rollbackValue = null;
        this.enableRollback = false;
        this.TAG = getClass().getSimpleName();
    }

    public void setRollbackValue(T target) {
        this.enableRollback = true;
        this.rollbackValue = target;
    }

    public boolean isEnableRollback() {
        return this.firstTarget != null && this.enableRollback;
    }

    public T getRollbackValue() {
        return this.rollbackValue;
    }

    public void rollback() {}

    public Element getTarget() {
        return this.firstTarget;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setTarget(Element target) {
        this.firstTarget = target;
        return this;
    }

    public void setListener(AnimationStatusListener listener) {
        this.listener = listener;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setInterpolationType(InterpolationType interpolationType) {
        Iterator<KeyFrame<T>> it = this.keyFrameList.iterator();
        while (it.hasNext()) {
            KeyFrame<T> data = it.next();
            data.setInterpolationType(interpolationType);
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setBezierControlPoint(
            float controlPointX1,
            float controlPointY1,
            float controlPointX2,
            float controlPointY2) {
        Iterator<KeyFrame<T>> it = this.keyFrameList.iterator();
        while (it.hasNext()) {
            KeyFrame<T> data = it.next();
            data.setBezierControlPoint(
                    controlPointX1, controlPointY1, controlPointX2, controlPointY2);
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
    public Animation<?> setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long getDuration() {
        return this.duration;
    }

    public void onAnimationStarted(Object interpolatedValue) {
        if (this.listener == null) {
            return;
        }
        this.listener.onAnimationStarted(interpolatedValue);
    }

    public void onAnimationUpdated(Object interpolatedValue) {
        if (this.listener == null) {
            return;
        }
        this.listener.onAnimationUpdated(interpolatedValue);
    }

    public void onAnimationFinished(Object interpolatedValue) {
        if (this.listener == null) {
            return;
        }
        this.listener.onAnimationFinished(interpolatedValue);
    }

    public void onAnimationCanceled(Object interpolatedValue) {
        if (this.listener == null) {
            return;
        }
        this.listener.onAnimationCanceled(interpolatedValue);
    }

    public void updateTargetValue(Object interpolatedValue) {}

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setRepeat(int fromLoop, int toLoop, int repeatCount) {
        this.fromLoop = Math.max(fromLoop, 0);
        this.toLoop = Math.min(toLoop, getKeyFrameCount() - 1);
        this.repeatCount = repeatCount;
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
        int size = this.keyFrameList.size();
        this.duration +=
                this.keyFrameList.get(size - 1).getTime() - this.keyFrameList.get(0).getTime();
        if (this.repeatCount == 0) {
            return;
        }
        long repeatDuration =
                (this.keyFrameList.get(this.toLoop).getTime()
                                - this.keyFrameList.get(this.fromLoop).getTime())
                        * this.repeatCount;
        this.duration += repeatDuration;
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
    public Animation<?> setKeyFrameList(ArrayList<KeyFrame<T>> keyFrameList) {
        this.keyFrameList.clear();
        this.keyFrameList.addAll(keyFrameList);
        sortKeyFrameList();
        calculateDuration();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Animation<?> setKeyFrame(KeyFrame<T> firstKeyFrame, KeyFrame<T> secondKeyFrame) {
        this.keyFrameList.clear();
        this.keyFrameList.add(firstKeyFrame);
        this.keyFrameList.add(secondKeyFrame);
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

    public boolean removeKeyFrame(int index) {
        if (this.keyFrameList.size() <= 2) {
            Log.e(this.TAG, "Invalid KeyFrame size : " + this.keyFrameList.size());
            return false;
        }
        this.keyFrameList.remove(index);
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
        this.keyFrameList.sort(
                new Comparator() { // from class:
                                   // com.samsung.vekit.Animation.Animation$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return Animation.lambda$sortKeyFrameList$0((KeyFrame) obj, (KeyFrame) obj2);
                    }
                });
    }

    static /* synthetic */ int lambda$sortKeyFrameList$0(KeyFrame prev, KeyFrame next) {
        if (prev.getTime() > next.getTime()) {
            return 1;
        }
        if (prev.getTime() < next.getTime()) {
            return -1;
        }
        return 0;
    }

    @Deprecated
    public InterpolationType getInterpolationType() {
        return null;
    }

    @Deprecated
    public BezierControlPoint getBezierControlPoint() {
        return null;
    }

    @Deprecated
    public T getFrom() {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Animation<?> setFrom(T from) {
        return this;
    }

    @Deprecated
    public T getTo() {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Animation<?> setTo(T to) {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Animation<?> setDuration(long duration) {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public Animation<?> setKeyFrame(KeyFrame<T> keyFrame) {
        return this;
    }
}
