package android.animation;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.app.ActivityThread;
import android.app.Application;
import android.os.Looper;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LongArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class AnimatorSet extends Animator implements AnimationHandler.AnimationFrameCallback {
    private static final String TAG = "AnimatorSet";
    private long[] mChildStartAndStopTimes;
    private final boolean mEndCanBeCalled;
    private final boolean mShouldIgnoreEndWithoutStart;
    private final boolean mShouldResetValuesAtStart;
    private ArrayList<Node> mPlayingSet = new ArrayList<>();
    private ArrayMap<Animator, Node> mNodeMap = new ArrayMap<>();
    private ArrayList<AnimationEvent> mEvents = new ArrayList<>();
    private ArrayList<Node> mNodes = new ArrayList<>();
    private boolean mDependencyDirty = false;
    private boolean mStarted = false;
    private long mStartDelay = 0;
    private ValueAnimator mDelayAnim = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(0L);
    private Node mRootNode = new Node(this.mDelayAnim);
    private long mDuration = -1;
    private TimeInterpolator mInterpolator = null;
    private long mTotalDuration = 0;
    private long mLastFrameTime = -1;
    private long mFirstFrame = -1;
    private int mLastEventId = -1;
    private boolean mReversing = false;
    private boolean mSelfPulse = true;
    private SeekState mSeekState = new SeekState();
    private boolean mChildrenInitialized = false;
    private long mPauseTime = -1;
    private AnimatorListenerAdapter mAnimationEndListener = new AnimatorListenerAdapter() { // from class: android.animation.AnimatorSet.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            if (AnimatorSet.this.mNodeMap.get(animation) == null) {
                throw new AndroidRuntimeException("Error: animation ended is not in the node map");
            }
            ((Node) AnimatorSet.this.mNodeMap.get(animation)).mEnded = true;
        }
    };

    public AnimatorSet() {
        boolean isPreO;
        boolean z;
        this.mNodeMap.put(this.mDelayAnim, this.mRootNode);
        this.mNodes.add(this.mRootNode);
        Application app = ActivityThread.currentApplication();
        if (app == null || app.getApplicationInfo() == null) {
            this.mShouldIgnoreEndWithoutStart = true;
            isPreO = true;
        } else {
            if (app.getApplicationInfo().targetSdkVersion < 24) {
                this.mShouldIgnoreEndWithoutStart = true;
            } else {
                this.mShouldIgnoreEndWithoutStart = false;
            }
            if (app.getApplicationInfo().targetSdkVersion >= 26) {
                isPreO = false;
            } else {
                isPreO = true;
            }
        }
        if (isPreO) {
            z = false;
        } else {
            z = true;
        }
        this.mShouldResetValuesAtStart = z;
        this.mEndCanBeCalled = isPreO ? false : true;
    }

    public void playTogether(Animator... items) {
        if (items != null) {
            Builder builder = play(items[0]);
            for (int i = 1; i < items.length; i++) {
                builder.with(items[i]);
            }
        }
    }

    public void playTogether(Collection<Animator> items) {
        if (items != null && items.size() > 0) {
            Builder builder = null;
            for (Animator anim : items) {
                if (builder == null) {
                    builder = play(anim);
                } else {
                    builder.with(anim);
                }
            }
        }
    }

    public void playSequentially(Animator... items) {
        if (items != null) {
            if (items.length == 1) {
                play(items[0]);
                return;
            }
            for (int i = 0; i < items.length - 1; i++) {
                play(items[i]).before(items[i + 1]);
            }
        }
    }

    public void playSequentially(List<Animator> items) {
        if (items != null && items.size() > 0) {
            if (items.size() == 1) {
                play(items.get(0));
                return;
            }
            for (int i = 0; i < items.size() - 1; i++) {
                play(items.get(i)).before(items.get(i + 1));
            }
        }
    }

    public ArrayList<Animator> getChildAnimations() {
        ArrayList<Animator> childList = new ArrayList<>();
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            Node node = this.mNodes.get(i);
            if (node != this.mRootNode) {
                childList.add(node.mAnimation);
            }
        }
        return childList;
    }

    @Override // android.animation.Animator
    public void setTarget(Object target) {
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            Node node = this.mNodes.get(i);
            Animator animation = node.mAnimation;
            if (animation instanceof AnimatorSet) {
                ((AnimatorSet) animation).setTarget(target);
            } else if (animation instanceof ObjectAnimator) {
                ((ObjectAnimator) animation).setTarget(target);
            }
        }
    }

    @Override // android.animation.Animator
    public int getChangingConfigurations() {
        int conf = super.getChangingConfigurations();
        int nodeCount = this.mNodes.size();
        for (int i = 0; i < nodeCount; i++) {
            conf |= this.mNodes.get(i).mAnimation.getChangingConfigurations();
        }
        return conf;
    }

    @Override // android.animation.Animator
    public void setInterpolator(TimeInterpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    @Override // android.animation.Animator
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public Builder play(Animator anim) {
        if (anim != null) {
            return new Builder(anim);
        }
        return null;
    }

    @Override // android.animation.Animator
    public void cancel() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (isStarted() || this.mStartListenersCalled) {
            notifyListeners(Animator.AnimatorCaller.ON_CANCEL, false);
            callOnPlayingSet(new Consumer() { // from class: android.animation.AnimatorSet$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Animator) obj).cancel();
                }
            });
            this.mPlayingSet.clear();
            endAnimation();
        }
    }

    private void callOnPlayingSet(Consumer<Animator> consumer) {
        ArrayList<Node> list = this.mPlayingSet;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Animator animator = list.get(i).mAnimation;
            consumer.accept(animator);
        }
    }

    private void forceToEnd() {
        if (this.mEndCanBeCalled) {
            end();
            return;
        }
        if (this.mReversing) {
            handleAnimationEvents(this.mLastEventId, 0, getTotalDuration());
        } else {
            long zeroScalePlayTime = getTotalDuration();
            if (zeroScalePlayTime == -1) {
                zeroScalePlayTime = 2147483647L;
            }
            handleAnimationEvents(this.mLastEventId, this.mEvents.size() - 1, zeroScalePlayTime);
        }
        this.mPlayingSet.clear();
        endAnimation();
    }

    @Override // android.animation.Animator
    public void end() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mShouldIgnoreEndWithoutStart && !isStarted()) {
            return;
        }
        if (isStarted()) {
            this.mStarted = false;
            if (this.mReversing) {
                this.mLastEventId = this.mLastEventId == -1 ? this.mEvents.size() : this.mLastEventId;
                for (int eventId = this.mLastEventId - 1; eventId >= 0; eventId--) {
                    AnimationEvent event = this.mEvents.get(eventId);
                    Animator anim = event.mNode.mAnimation;
                    if (!this.mNodeMap.get(anim).mEnded) {
                        if (event.mEvent == 2) {
                            anim.reverse();
                        } else if (event.mEvent == 1 && anim.isStarted()) {
                            anim.end();
                        }
                    }
                }
            } else {
                for (int eventId2 = this.mLastEventId + 1; eventId2 < this.mEvents.size(); eventId2++) {
                    AnimationEvent event2 = this.mEvents.get(eventId2);
                    Animator anim2 = event2.mNode.mAnimation;
                    if (!this.mNodeMap.get(anim2).mEnded) {
                        if (event2.mEvent == 0) {
                            anim2.start();
                        } else if (event2.mEvent == 2 && anim2.isStarted()) {
                            anim2.end();
                        }
                    }
                }
            }
        }
        endAnimation();
    }

    @Override // android.animation.Animator
    public boolean isRunning() {
        if (this.mStartDelay == 0) {
            return this.mStarted;
        }
        return this.mLastFrameTime > 0;
    }

    @Override // android.animation.Animator
    public boolean isStarted() {
        return this.mStarted;
    }

    @Override // android.animation.Animator
    public long getStartDelay() {
        return this.mStartDelay;
    }

    @Override // android.animation.Animator
    public void setStartDelay(long startDelay) {
        if (startDelay < 0) {
            Log.w(TAG, "Start delay should always be non-negative");
            startDelay = 0;
        }
        long delta = startDelay - this.mStartDelay;
        if (delta == 0) {
            return;
        }
        this.mStartDelay = startDelay;
        if (!this.mDependencyDirty) {
            int size = this.mNodes.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                Node node = this.mNodes.get(i);
                if (node == this.mRootNode) {
                    node.mEndTime = this.mStartDelay;
                } else {
                    node.mStartTime = node.mStartTime == -1 ? -1L : node.mStartTime + delta;
                    node.mEndTime = node.mEndTime != -1 ? node.mEndTime + delta : -1L;
                }
                i++;
            }
            if (this.mTotalDuration != -1) {
                this.mTotalDuration += delta;
            }
        }
    }

    @Override // android.animation.Animator
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.animation.Animator
    public AnimatorSet setDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("duration must be a value of zero or greater");
        }
        this.mDependencyDirty = true;
        this.mDuration = duration;
        return this;
    }

    @Override // android.animation.Animator
    public void setupStartValues() {
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            Node node = this.mNodes.get(i);
            if (node != this.mRootNode) {
                node.mAnimation.setupStartValues();
            }
        }
    }

    @Override // android.animation.Animator
    public void setupEndValues() {
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            Node node = this.mNodes.get(i);
            if (node != this.mRootNode) {
                node.mAnimation.setupEndValues();
            }
        }
    }

    @Override // android.animation.Animator
    public void pause() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        boolean previouslyPaused = this.mPaused;
        super.pause();
        if (!previouslyPaused && this.mPaused) {
            this.mPauseTime = -1L;
            callOnPlayingSet(new Consumer() { // from class: android.animation.AnimatorSet$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Animator) obj).pause();
                }
            });
        }
    }

    @Override // android.animation.Animator
    public void resume() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        boolean previouslyPaused = this.mPaused;
        super.resume();
        if (previouslyPaused && !this.mPaused) {
            if (this.mPauseTime >= 0) {
                addAnimationCallback(0L);
            }
            callOnPlayingSet(new Consumer() { // from class: android.animation.AnimatorSet$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Animator) obj).resume();
                }
            });
        }
    }

    @Override // android.animation.Animator
    public void start() {
        start(false, true);
    }

    @Override // android.animation.Animator
    void startWithoutPulsing(boolean inReverse) {
        start(inReverse, false);
    }

    private void initAnimation() {
        if (this.mInterpolator != null) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                Node node = this.mNodes.get(i);
                node.mAnimation.setInterpolator(this.mInterpolator);
            }
        }
        updateAnimatorsDuration();
        createDependencyGraph();
    }

    private void start(boolean inReverse, boolean selfPulse) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (inReverse == this.mReversing && selfPulse == this.mSelfPulse && this.mStarted) {
            return;
        }
        this.mStarted = true;
        this.mSelfPulse = selfPulse;
        this.mPaused = false;
        this.mPauseTime = -1L;
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            Node node = this.mNodes.get(i);
            node.mEnded = false;
            node.mAnimation.setAllowRunningAsynchronously(false);
        }
        initAnimation();
        if (inReverse && !canReverse()) {
            throw new UnsupportedOperationException("Cannot reverse infinite AnimatorSet");
        }
        this.mReversing = inReverse;
        boolean isEmptySet = isEmptySet(this);
        if (!isEmptySet) {
            startAnimation();
        }
        notifyStartListeners(inReverse);
        if (isEmptySet) {
            end();
        }
    }

    private static boolean isEmptySet(AnimatorSet set) {
        if (set.getStartDelay() > 0) {
            return false;
        }
        for (int i = 0; i < set.getChildAnimations().size(); i++) {
            Animator anim = set.getChildAnimations().get(i);
            if (!(anim instanceof AnimatorSet) || !isEmptySet((AnimatorSet) anim)) {
                return false;
            }
        }
        return true;
    }

    private void updateAnimatorsDuration() {
        if (this.mDuration >= 0) {
            int size = this.mNodes.size();
            for (int i = 0; i < size; i++) {
                Node node = this.mNodes.get(i);
                node.mAnimation.setDuration(this.mDuration);
            }
        }
        this.mDelayAnim.setDuration(this.mStartDelay);
    }

    @Override // android.animation.Animator
    void skipToEndValue(boolean inReverse) {
        initAnimation();
        initChildren();
        if (inReverse) {
            for (int i = this.mEvents.size() - 1; i >= 0; i--) {
                AnimationEvent event = this.mEvents.get(i);
                if (event.mEvent == 1) {
                    event.mNode.mAnimation.skipToEndValue(true);
                }
            }
            return;
        }
        for (int i2 = 0; i2 < this.mEvents.size(); i2++) {
            AnimationEvent event2 = this.mEvents.get(i2);
            if (event2.mEvent == 2) {
                event2.mNode.mAnimation.skipToEndValue(false);
            }
        }
    }

    private void animateBasedOnPlayTime(long currentPlayTime, long lastPlayTime, boolean inReverse) {
        if (currentPlayTime < 0 || lastPlayTime < -1) {
            throw new UnsupportedOperationException("Error: Play time should never be negative.");
        }
        if (inReverse) {
            long duration = getTotalDuration();
            if (duration == -1) {
                throw new UnsupportedOperationException("Cannot reverse AnimatorSet with infinite duration");
            }
            currentPlayTime = duration - Math.min(currentPlayTime, duration);
            lastPlayTime = duration - lastPlayTime;
        }
        long[] startEndTimes = ensureChildStartAndEndTimes();
        int index = findNextIndex(lastPlayTime, startEndTimes);
        int endIndex = findNextIndex(currentPlayTime, startEndTimes);
        if (currentPlayTime >= lastPlayTime) {
            while (index < endIndex) {
                long playTime = startEndTimes[index];
                if (lastPlayTime != playTime) {
                    animateSkipToEnds(playTime, lastPlayTime);
                    animateValuesInRange(playTime, lastPlayTime);
                    lastPlayTime = playTime;
                }
                index++;
            }
        } else {
            while (index > endIndex) {
                index--;
                long playTime2 = startEndTimes[index];
                if (lastPlayTime != playTime2) {
                    animateSkipToEnds(playTime2, lastPlayTime);
                    animateValuesInRange(playTime2, lastPlayTime);
                    lastPlayTime = playTime2;
                }
            }
        }
        if (currentPlayTime != lastPlayTime) {
            animateSkipToEnds(currentPlayTime, lastPlayTime);
            animateValuesInRange(currentPlayTime, lastPlayTime);
        }
    }

    private int findNextIndex(long playTime, long[] startEndTimes) {
        int index = Arrays.binarySearch(startEndTimes, playTime);
        if (index < 0) {
            return (-index) - 1;
        }
        return index + 1;
    }

    @Override // android.animation.Animator
    void animateSkipToEnds(long currentPlayTime, long lastPlayTime) {
        initAnimation();
        long j = -1;
        if (lastPlayTime <= currentPlayTime) {
            notifyStartListeners(false);
            int eventsSize = this.mEvents.size();
            int i = 0;
            while (i < eventsSize) {
                AnimationEvent event = this.mEvents.get(i);
                Node node = event.mNode;
                if (event.mEvent == 1 && node.mStartTime != j) {
                    Animator animator = node.mAnimation;
                    long start = node.mStartTime;
                    long end = node.mTotalDuration == j ? Long.MAX_VALUE : node.mEndTime;
                    if (lastPlayTime < end && end <= currentPlayTime) {
                        animator.animateSkipToEnds(end - node.mStartTime, lastPlayTime - node.mStartTime);
                        this.mPlayingSet.remove(node);
                    } else if (start <= currentPlayTime && currentPlayTime <= end) {
                        animator.animateSkipToEnds(currentPlayTime - node.mStartTime, lastPlayTime - node.mStartTime);
                        if (!this.mPlayingSet.contains(node)) {
                            this.mPlayingSet.add(node);
                        }
                    }
                }
                i++;
                j = -1;
            }
            if (currentPlayTime >= getTotalDuration()) {
                notifyEndListeners(false);
                return;
            }
            return;
        }
        notifyStartListeners(true);
        for (int i2 = this.mEvents.size() - 1; i2 >= 0; i2--) {
            AnimationEvent event2 = this.mEvents.get(i2);
            Node node2 = event2.mNode;
            if (event2.mEvent == 2 && node2.mStartTime != -1) {
                Animator animator2 = node2.mAnimation;
                long start2 = node2.mStartTime;
                long end2 = node2.mTotalDuration == -1 ? Long.MAX_VALUE : node2.mEndTime;
                if (currentPlayTime <= start2 && start2 < lastPlayTime) {
                    animator2.animateSkipToEnds(0L, lastPlayTime - node2.mStartTime);
                    this.mPlayingSet.remove(node2);
                } else if (start2 <= currentPlayTime && currentPlayTime <= end2) {
                    animator2.animateSkipToEnds(currentPlayTime - node2.mStartTime, lastPlayTime - node2.mStartTime);
                    if (!this.mPlayingSet.contains(node2)) {
                        this.mPlayingSet.add(node2);
                    }
                }
            }
        }
        if (currentPlayTime <= 0) {
            notifyEndListeners(true);
        }
    }

    @Override // android.animation.Animator
    void animateValuesInRange(long currentPlayTime, long lastPlayTime) {
        initAnimation();
        int i = 1;
        if (lastPlayTime < 0 || (lastPlayTime == 0 && currentPlayTime > 0)) {
            notifyStartListeners(false);
        } else {
            long duration = getTotalDuration();
            if (duration >= 0 && (lastPlayTime > duration || (lastPlayTime == duration && currentPlayTime < duration))) {
                notifyStartListeners(true);
            }
        }
        int eventsSize = this.mEvents.size();
        int i2 = 0;
        while (i2 < eventsSize) {
            AnimationEvent event = this.mEvents.get(i2);
            Node node = event.mNode;
            if (event.mEvent == i && node.mStartTime != -1) {
                Animator animator = node.mAnimation;
                long start = node.mStartTime;
                long end = node.mTotalDuration == -1 ? Long.MAX_VALUE : node.mEndTime;
                if ((start < currentPlayTime && currentPlayTime < end) || ((start == currentPlayTime && lastPlayTime < start) || (end == currentPlayTime && lastPlayTime > end))) {
                    animator.animateValuesInRange(currentPlayTime - node.mStartTime, Math.max(-1L, lastPlayTime - node.mStartTime));
                }
            }
            i2++;
            i = 1;
        }
    }

    private long[] ensureChildStartAndEndTimes() {
        if (this.mChildStartAndStopTimes == null) {
            LongArray startAndEndTimes = new LongArray();
            getStartAndEndTimes(startAndEndTimes, 0L);
            long[] times = startAndEndTimes.toArray();
            Arrays.sort(times);
            this.mChildStartAndStopTimes = times;
        }
        return this.mChildStartAndStopTimes;
    }

    @Override // android.animation.Animator
    void getStartAndEndTimes(LongArray times, long offset) {
        int eventsSize = this.mEvents.size();
        for (int i = 0; i < eventsSize; i++) {
            AnimationEvent event = this.mEvents.get(i);
            if (event.mEvent == 1 && event.mNode.mStartTime != -1) {
                event.mNode.mAnimation.getStartAndEndTimes(times, event.mNode.mStartTime + offset);
            }
        }
    }

    @Override // android.animation.Animator
    boolean isInitialized() {
        if (this.mChildrenInitialized) {
            return true;
        }
        boolean allInitialized = true;
        int i = 0;
        while (true) {
            if (i >= this.mNodes.size()) {
                break;
            }
            if (this.mNodes.get(i).mAnimation.isInitialized()) {
                i++;
            } else {
                allInitialized = false;
                break;
            }
        }
        this.mChildrenInitialized = allInitialized;
        return this.mChildrenInitialized;
    }

    public void setCurrentPlayTime(long playTime) {
        if (this.mReversing && getTotalDuration() == -1) {
            throw new UnsupportedOperationException("Error: Cannot seek in reverse in an infinite AnimatorSet");
        }
        if ((getTotalDuration() != -1 && playTime > getTotalDuration() - this.mStartDelay) || playTime < 0) {
            throw new UnsupportedOperationException("Error: Play time should always be in between 0 and duration.");
        }
        initAnimation();
        long lastPlayTime = this.mSeekState.getPlayTime();
        if (!isStarted() || isPaused()) {
            if (this.mReversing && !isStarted()) {
                throw new UnsupportedOperationException("Error: Something went wrong. mReversing should not be set when AnimatorSet is not started.");
            }
            if (!this.mSeekState.isActive()) {
                findLatestEventIdForTime(0L);
                initChildren();
                skipToEndValue(!this.mReversing);
                this.mSeekState.setPlayTime(0L, this.mReversing);
            }
        }
        this.mSeekState.setPlayTime(playTime, this.mReversing);
        animateBasedOnPlayTime(playTime, lastPlayTime, this.mReversing);
    }

    public long getCurrentPlayTime() {
        if (this.mSeekState.isActive()) {
            return this.mSeekState.getPlayTime();
        }
        if (this.mLastFrameTime == -1) {
            return 0L;
        }
        float durationScale = ValueAnimator.getDurationScale();
        float durationScale2 = durationScale == 0.0f ? 1.0f : durationScale;
        if (this.mReversing) {
            return (long) ((this.mLastFrameTime - this.mFirstFrame) / durationScale2);
        }
        return (long) (((this.mLastFrameTime - this.mFirstFrame) - this.mStartDelay) / durationScale2);
    }

    private void initChildren() {
        if (!isInitialized()) {
            this.mChildrenInitialized = true;
            skipToEndValue(false);
        }
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public boolean doAnimationFrame(long frameTime) {
        float durationScale = ValueAnimator.getDurationScale();
        if (durationScale == 0.0f) {
            forceToEnd();
            return true;
        }
        if (this.mFirstFrame < 0) {
            this.mFirstFrame = frameTime;
        }
        if (this.mPaused) {
            this.mPauseTime = frameTime;
            removeAnimationCallback();
            return false;
        }
        if (this.mPauseTime > 0) {
            this.mFirstFrame += frameTime - this.mPauseTime;
            this.mPauseTime = -1L;
        }
        if (this.mSeekState.isActive()) {
            this.mSeekState.updateSeekDirection(this.mReversing);
            if (this.mReversing) {
                this.mFirstFrame = (long) (frameTime - (this.mSeekState.getPlayTime() * durationScale));
            } else {
                this.mFirstFrame = (long) (frameTime - ((this.mSeekState.getPlayTime() + this.mStartDelay) * durationScale));
            }
            this.mSeekState.reset();
        }
        if (!this.mReversing && frameTime < this.mFirstFrame + (this.mStartDelay * durationScale)) {
            return false;
        }
        long unscaledPlayTime = (long) ((frameTime - this.mFirstFrame) / durationScale);
        this.mLastFrameTime = frameTime;
        int latestId = findLatestEventIdForTime(unscaledPlayTime);
        int startId = this.mLastEventId;
        handleAnimationEvents(startId, latestId, unscaledPlayTime);
        this.mLastEventId = latestId;
        for (int i = 0; i < this.mPlayingSet.size(); i++) {
            Node node = this.mPlayingSet.get(i);
            if (!node.mEnded) {
                pulseFrame(node, getPlayTimeForNodeIncludingDelay(unscaledPlayTime, node));
            }
        }
        for (int i2 = this.mPlayingSet.size() - 1; i2 >= 0; i2--) {
            if (this.mPlayingSet.get(i2).mEnded) {
                this.mPlayingSet.remove(i2);
            }
        }
        int i3 = 0;
        if (this.mReversing) {
            if (this.mPlayingSet.size() == 1 && this.mPlayingSet.get(0) == this.mRootNode) {
                i3 = 1;
            } else if (this.mPlayingSet.isEmpty() && this.mLastEventId < 3) {
                i3 = 1;
            }
        } else {
            i3 = (this.mPlayingSet.isEmpty() && this.mLastEventId == this.mEvents.size() - 1) ? 1 : 0;
        }
        if (i3 == 0) {
            return false;
        }
        endAnimation();
        return true;
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public void commitAnimationFrame(long frameTime) {
    }

    @Override // android.animation.Animator
    boolean pulseAnimationFrame(long frameTime) {
        return doAnimationFrame(frameTime);
    }

    private void handleAnimationEvents(int startId, int latestId, long playTime) {
        if (this.mReversing) {
            for (int i = (startId == -1 ? this.mEvents.size() : startId) - 1; i >= latestId; i--) {
                AnimationEvent event = this.mEvents.get(i);
                Node node = event.mNode;
                if (event.mEvent == 2) {
                    if (node.mAnimation.isStarted()) {
                        node.mAnimation.cancel();
                    }
                    node.mEnded = false;
                    this.mPlayingSet.add(event.mNode);
                    node.mAnimation.startWithoutPulsing(true);
                    pulseFrame(node, 0L);
                } else if (event.mEvent == 1 && !node.mEnded) {
                    pulseFrame(node, getPlayTimeForNodeIncludingDelay(playTime, node));
                }
            }
            return;
        }
        for (int i2 = startId + 1; i2 <= latestId; i2++) {
            AnimationEvent event2 = this.mEvents.get(i2);
            Node node2 = event2.mNode;
            if (event2.mEvent == 0) {
                this.mPlayingSet.add(event2.mNode);
                if (node2.mAnimation.isStarted()) {
                    node2.mAnimation.cancel();
                }
                node2.mEnded = false;
                node2.mAnimation.startWithoutPulsing(false);
                pulseFrame(node2, 0L);
            } else if (event2.mEvent == 2 && !node2.mEnded) {
                pulseFrame(node2, getPlayTimeForNodeIncludingDelay(playTime, node2));
            }
        }
    }

    private void pulseFrame(Node node, long animPlayTime) {
        if (!node.mEnded) {
            float durationScale = ValueAnimator.getDurationScale();
            if (node.mAnimation.pulseAnimationFrame((long) (animPlayTime * (durationScale == 0.0f ? 1.0f : durationScale)))) {
                node.mEnded = true;
            }
        }
    }

    private long getPlayTimeForNodeIncludingDelay(long overallPlayTime, Node node) {
        return getPlayTimeForNodeIncludingDelay(overallPlayTime, node, this.mReversing);
    }

    private long getPlayTimeForNodeIncludingDelay(long overallPlayTime, Node node, boolean inReverse) {
        if (inReverse) {
            return node.mEndTime - (getTotalDuration() - overallPlayTime);
        }
        return overallPlayTime - node.mStartTime;
    }

    private void startAnimation() {
        long playTime;
        addAnimationEndListener();
        addAnimationCallback(0L);
        if (this.mSeekState.getPlayTimeNormalized() == 0 && this.mReversing) {
            this.mSeekState.reset();
        }
        if (this.mShouldResetValuesAtStart) {
            if (isInitialized()) {
                skipToEndValue(!this.mReversing);
            } else if (this.mReversing) {
                initChildren();
                skipToEndValue(!this.mReversing);
            } else {
                for (int i = this.mEvents.size() - 1; i >= 0; i--) {
                    if (this.mEvents.get(i).mEvent == 1) {
                        Animator anim = this.mEvents.get(i).mNode.mAnimation;
                        if (anim.isInitialized()) {
                            anim.skipToEndValue(true);
                        }
                    }
                }
            }
        }
        if (this.mReversing || this.mStartDelay == 0 || this.mSeekState.isActive()) {
            if (this.mSeekState.isActive()) {
                this.mSeekState.updateSeekDirection(this.mReversing);
                playTime = this.mSeekState.getPlayTime();
            } else {
                playTime = 0;
            }
            int toId = findLatestEventIdForTime(playTime);
            handleAnimationEvents(-1, toId, playTime);
            for (int i2 = this.mPlayingSet.size() - 1; i2 >= 0; i2--) {
                if (this.mPlayingSet.get(i2).mEnded) {
                    this.mPlayingSet.remove(i2);
                }
            }
            this.mLastEventId = toId;
        }
    }

    private void addAnimationEndListener() {
        for (int i = 1; i < this.mNodes.size(); i++) {
            this.mNodes.get(i).mAnimation.addListener(this.mAnimationEndListener);
        }
    }

    private void removeAnimationEndListener() {
        for (int i = 1; i < this.mNodes.size(); i++) {
            this.mNodes.get(i).mAnimation.removeListener(this.mAnimationEndListener);
        }
    }

    private int findLatestEventIdForTime(long currentPlayTime) {
        int size = this.mEvents.size();
        int latestId = this.mLastEventId;
        if (this.mReversing) {
            long currentPlayTime2 = getTotalDuration() - currentPlayTime;
            this.mLastEventId = this.mLastEventId == -1 ? size : this.mLastEventId;
            for (int j = this.mLastEventId - 1; j >= 0; j--) {
                if (this.mEvents.get(j).getTime() >= currentPlayTime2) {
                    latestId = j;
                }
            }
        } else {
            for (int i = this.mLastEventId + 1; i < size; i++) {
                AnimationEvent event = this.mEvents.get(i);
                if (event.getTime() != -1 && event.getTime() <= currentPlayTime) {
                    latestId = i;
                }
            }
        }
        return latestId;
    }

    private void endAnimation() {
        this.mStarted = false;
        this.mLastFrameTime = -1L;
        this.mFirstFrame = -1L;
        this.mLastEventId = -1;
        this.mPaused = false;
        this.mPauseTime = -1L;
        this.mSeekState.reset();
        this.mPlayingSet.clear();
        removeAnimationCallback();
        notifyEndListeners(this.mReversing);
        removeAnimationEndListener();
        this.mSelfPulse = true;
        this.mReversing = false;
    }

    private void removeAnimationCallback() {
        if (!this.mSelfPulse) {
            return;
        }
        AnimationHandler handler = AnimationHandler.getInstance();
        handler.removeCallback(this);
    }

    private void addAnimationCallback(long delay) {
        if (!this.mSelfPulse) {
            return;
        }
        AnimationHandler handler = AnimationHandler.getInstance();
        handler.addAnimationFrameCallback(this, delay);
    }

    @Override // android.animation.Animator
    /* renamed from: clone */
    public AnimatorSet mo77clone() {
        final AnimatorSet anim = (AnimatorSet) super.mo77clone();
        int nodeCount = this.mNodes.size();
        anim.mStarted = false;
        anim.mLastFrameTime = -1L;
        anim.mFirstFrame = -1L;
        anim.mLastEventId = -1;
        anim.mPaused = false;
        anim.mPauseTime = -1L;
        anim.mSeekState = new SeekState();
        anim.mSelfPulse = true;
        anim.mStartListenersCalled = false;
        anim.mPlayingSet = new ArrayList<>();
        anim.mNodeMap = new ArrayMap<>();
        anim.mNodes = new ArrayList<>(nodeCount);
        anim.mEvents = new ArrayList<>();
        anim.mAnimationEndListener = new AnimatorListenerAdapter() { // from class: android.animation.AnimatorSet.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (anim.mNodeMap.get(animation) == null) {
                    throw new AndroidRuntimeException("Error: animation ended is not in the node map");
                }
                ((Node) anim.mNodeMap.get(animation)).mEnded = true;
            }
        };
        anim.mReversing = false;
        anim.mDependencyDirty = true;
        HashMap<Node, Node> clonesMap = new HashMap<>(nodeCount);
        for (int n = 0; n < nodeCount; n++) {
            Node node = this.mNodes.get(n);
            Node nodeClone = node.m83clone();
            nodeClone.mAnimation.removeListener(this.mAnimationEndListener);
            clonesMap.put(node, nodeClone);
            anim.mNodes.add(nodeClone);
            anim.mNodeMap.put(nodeClone.mAnimation, nodeClone);
        }
        anim.mRootNode = clonesMap.get(this.mRootNode);
        anim.mDelayAnim = (ValueAnimator) anim.mRootNode.mAnimation;
        for (int i = 0; i < nodeCount; i++) {
            Node node2 = this.mNodes.get(i);
            Node nodeClone2 = clonesMap.get(node2);
            nodeClone2.mLatestParent = node2.mLatestParent == null ? null : clonesMap.get(node2.mLatestParent);
            int size = node2.mChildNodes == null ? 0 : node2.mChildNodes.size();
            for (int j = 0; j < size; j++) {
                nodeClone2.mChildNodes.set(j, clonesMap.get(node2.mChildNodes.get(j)));
            }
            int size2 = node2.mSiblings == null ? 0 : node2.mSiblings.size();
            for (int j2 = 0; j2 < size2; j2++) {
                nodeClone2.mSiblings.set(j2, clonesMap.get(node2.mSiblings.get(j2)));
            }
            int size3 = node2.mParents == null ? 0 : node2.mParents.size();
            for (int j3 = 0; j3 < size3; j3++) {
                nodeClone2.mParents.set(j3, clonesMap.get(node2.mParents.get(j3)));
            }
        }
        return anim;
    }

    @Override // android.animation.Animator
    public boolean canReverse() {
        return getTotalDuration() != -1;
    }

    @Override // android.animation.Animator
    public void reverse() {
        start(true, true);
    }

    public String toString() {
        String returnVal = "AnimatorSet@" + Integer.toHexString(hashCode()) + "{";
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            Node node = this.mNodes.get(i);
            returnVal = returnVal + "\n    " + node.mAnimation.toString();
        }
        return returnVal + "\n}";
    }

    private void printChildCount() {
        ArrayList<Node> list = new ArrayList<>(this.mNodes.size());
        list.add(this.mRootNode);
        Log.d(TAG, "Current tree: ");
        int index = 0;
        while (index < list.size()) {
            int listSize = list.size();
            StringBuilder builder = new StringBuilder();
            while (index < listSize) {
                Node node = list.get(index);
                int num = 0;
                if (node.mChildNodes != null) {
                    for (int i = 0; i < node.mChildNodes.size(); i++) {
                        Node child = node.mChildNodes.get(i);
                        if (child.mLatestParent == node) {
                            num++;
                            list.add(child);
                        }
                    }
                }
                builder.append(" ");
                builder.append(num);
                index++;
            }
            Log.d(TAG, builder.toString());
        }
    }

    private void createDependencyGraph() {
        if (!this.mDependencyDirty) {
            boolean durationChanged = false;
            int i = 0;
            while (true) {
                if (i >= this.mNodes.size()) {
                    break;
                }
                Animator anim = this.mNodes.get(i).mAnimation;
                if (this.mNodes.get(i).mTotalDuration == anim.getTotalDuration()) {
                    i++;
                } else {
                    durationChanged = true;
                    break;
                }
            }
            if (!durationChanged) {
                return;
            }
        }
        this.mDependencyDirty = false;
        int size = this.mNodes.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mNodes.get(i2).mParentsAdded = false;
        }
        for (int i3 = 0; i3 < size; i3++) {
            Node node = this.mNodes.get(i3);
            if (!node.mParentsAdded) {
                node.mParentsAdded = true;
                if (node.mSiblings != null) {
                    findSiblings(node, node.mSiblings);
                    node.mSiblings.remove(node);
                    int siblingSize = node.mSiblings.size();
                    for (int j = 0; j < siblingSize; j++) {
                        node.addParents(node.mSiblings.get(j).mParents);
                    }
                    for (int j2 = 0; j2 < siblingSize; j2++) {
                        Node sibling = node.mSiblings.get(j2);
                        sibling.addParents(node.mParents);
                        sibling.mParentsAdded = true;
                    }
                }
            }
        }
        for (int i4 = 0; i4 < size; i4++) {
            Node node2 = this.mNodes.get(i4);
            if (node2 != this.mRootNode && node2.mParents == null) {
                node2.addParent(this.mRootNode);
            }
        }
        ArrayList<Node> visited = new ArrayList<>(this.mNodes.size());
        this.mRootNode.mStartTime = 0L;
        this.mRootNode.mEndTime = this.mDelayAnim.getDuration();
        updatePlayTime(this.mRootNode, visited);
        sortAnimationEvents();
        this.mTotalDuration = this.mEvents.get(this.mEvents.size() - 1).getTime();
    }

    private void sortAnimationEvents() {
        boolean needToSwapStart;
        this.mEvents.clear();
        for (int i = 1; i < this.mNodes.size(); i++) {
            Node node = this.mNodes.get(i);
            this.mEvents.add(new AnimationEvent(node, 0));
            this.mEvents.add(new AnimationEvent(node, 1));
            this.mEvents.add(new AnimationEvent(node, 2));
        }
        this.mEvents.sort(new Comparator<AnimationEvent>() { // from class: android.animation.AnimatorSet.3
            @Override // java.util.Comparator
            public int compare(AnimationEvent e1, AnimationEvent e2) {
                long t1 = e1.getTime();
                long t2 = e2.getTime();
                if (t1 == t2) {
                    if (e2.mEvent + e1.mEvent == 1) {
                        return e1.mEvent - e2.mEvent;
                    }
                    return e2.mEvent - e1.mEvent;
                }
                if (t2 == -1) {
                    return -1;
                }
                return (t1 != -1 && t1 - t2 <= 0) ? -1 : 1;
            }
        });
        int eventSize = this.mEvents.size();
        int i2 = 0;
        while (i2 < eventSize) {
            AnimationEvent event = this.mEvents.get(i2);
            if (event.mEvent == 2) {
                if (event.mNode.mStartTime == event.mNode.mEndTime) {
                    needToSwapStart = true;
                } else if (event.mNode.mEndTime == event.mNode.mStartTime + event.mNode.mAnimation.getStartDelay()) {
                    needToSwapStart = false;
                } else {
                    i2++;
                }
                int startEventId = eventSize;
                int startDelayEndId = eventSize;
                for (int j = i2 + 1; j < eventSize && (startEventId >= eventSize || startDelayEndId >= eventSize); j++) {
                    if (this.mEvents.get(j).mNode == event.mNode) {
                        if (this.mEvents.get(j).mEvent == 0) {
                            startEventId = j;
                        } else if (this.mEvents.get(j).mEvent == 1) {
                            startDelayEndId = j;
                        }
                    }
                }
                if (needToSwapStart && startEventId == this.mEvents.size()) {
                    throw new UnsupportedOperationException("Something went wrong, no start isfound after stop for an animation that has the same start and endtime.");
                }
                if (startDelayEndId == this.mEvents.size()) {
                    throw new UnsupportedOperationException("Something went wrong, no startdelay end is found after stop for an animation");
                }
                if (needToSwapStart) {
                    AnimationEvent startEvent = this.mEvents.remove(startEventId);
                    this.mEvents.add(i2, startEvent);
                    i2++;
                }
                AnimationEvent startDelayEndEvent = this.mEvents.remove(startDelayEndId);
                this.mEvents.add(i2, startDelayEndEvent);
                i2 += 2;
            } else {
                i2++;
            }
        }
        if (!this.mEvents.isEmpty() && this.mEvents.get(0).mEvent != 0) {
            throw new UnsupportedOperationException("Sorting went bad, the start event should always be at index 0");
        }
        this.mEvents.add(0, new AnimationEvent(this.mRootNode, 0));
        this.mEvents.add(1, new AnimationEvent(this.mRootNode, 1));
        this.mEvents.add(2, new AnimationEvent(this.mRootNode, 2));
        if (this.mEvents.get(this.mEvents.size() - 1).mEvent == 0 || this.mEvents.get(this.mEvents.size() - 1).mEvent == 1) {
            throw new UnsupportedOperationException("Something went wrong, the last event is not an end event");
        }
    }

    private void updatePlayTime(Node parent, ArrayList<Node> visited) {
        if (parent.mChildNodes == null) {
            if (parent == this.mRootNode) {
                for (int i = 0; i < this.mNodes.size(); i++) {
                    Node node = this.mNodes.get(i);
                    if (node != this.mRootNode) {
                        node.mStartTime = -1L;
                        node.mEndTime = -1L;
                    }
                }
                return;
            }
            return;
        }
        visited.add(parent);
        int childrenSize = parent.mChildNodes.size();
        for (int i2 = 0; i2 < childrenSize; i2++) {
            Node child = parent.mChildNodes.get(i2);
            child.mTotalDuration = child.mAnimation.getTotalDuration();
            int index = visited.indexOf(child);
            if (index >= 0) {
                for (int j = index; j < visited.size(); j++) {
                    visited.get(j).mLatestParent = null;
                    visited.get(j).mStartTime = -1L;
                    visited.get(j).mEndTime = -1L;
                }
                child.mStartTime = -1L;
                child.mEndTime = -1L;
                child.mLatestParent = null;
                Log.w(TAG, "Cycle found in AnimatorSet: " + this);
            } else {
                if (child.mStartTime != -1) {
                    if (parent.mEndTime == -1) {
                        child.mLatestParent = parent;
                        child.mStartTime = -1L;
                        child.mEndTime = -1L;
                    } else {
                        if (parent.mEndTime >= child.mStartTime) {
                            child.mLatestParent = parent;
                            child.mStartTime = parent.mEndTime;
                        }
                        child.mEndTime = child.mTotalDuration == -1 ? -1L : child.mStartTime + child.mTotalDuration;
                    }
                }
                updatePlayTime(child, visited);
            }
        }
        visited.remove(parent);
    }

    private void findSiblings(Node node, ArrayList<Node> siblings) {
        if (!siblings.contains(node)) {
            siblings.add(node);
            if (node.mSiblings == null) {
                return;
            }
            for (int i = 0; i < node.mSiblings.size(); i++) {
                findSiblings(node.mSiblings.get(i), siblings);
            }
        }
    }

    public boolean shouldPlayTogether() {
        updateAnimatorsDuration();
        createDependencyGraph();
        return this.mRootNode.mChildNodes == null || this.mRootNode.mChildNodes.size() == this.mNodes.size() - 1;
    }

    @Override // android.animation.Animator
    public long getTotalDuration() {
        updateAnimatorsDuration();
        createDependencyGraph();
        return this.mTotalDuration;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Node getNodeForAnimation(Animator anim) {
        Node node = this.mNodeMap.get(anim);
        if (node == null) {
            Node node2 = new Node(anim);
            this.mNodeMap.put(anim, node2);
            this.mNodes.add(node2);
            return node2;
        }
        return node;
    }

    private static class Node implements Cloneable {
        Animator mAnimation;
        ArrayList<Node> mParents;
        ArrayList<Node> mSiblings;
        ArrayList<Node> mChildNodes = null;
        boolean mEnded = false;
        Node mLatestParent = null;
        boolean mParentsAdded = false;
        long mStartTime = 0;
        long mEndTime = 0;
        long mTotalDuration = 0;

        public Node(Animator animation) {
            this.mAnimation = animation;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Node m83clone() {
            try {
                Node node = (Node) super.clone();
                node.mAnimation = this.mAnimation.mo77clone();
                if (this.mChildNodes != null) {
                    node.mChildNodes = new ArrayList<>(this.mChildNodes);
                }
                if (this.mSiblings != null) {
                    node.mSiblings = new ArrayList<>(this.mSiblings);
                }
                if (this.mParents != null) {
                    node.mParents = new ArrayList<>(this.mParents);
                }
                node.mEnded = false;
                return node;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        void addChild(Node node) {
            if (this.mChildNodes == null) {
                this.mChildNodes = new ArrayList<>();
            }
            if (!this.mChildNodes.contains(node)) {
                this.mChildNodes.add(node);
                node.addParent(this);
            }
        }

        public void addSibling(Node node) {
            if (this.mSiblings == null) {
                this.mSiblings = new ArrayList<>();
            }
            if (!this.mSiblings.contains(node)) {
                this.mSiblings.add(node);
                node.addSibling(this);
            }
        }

        public void addParent(Node node) {
            if (this.mParents == null) {
                this.mParents = new ArrayList<>();
            }
            if (!this.mParents.contains(node)) {
                this.mParents.add(node);
                node.addChild(this);
            }
        }

        public void addParents(ArrayList<Node> parents) {
            if (parents == null) {
                return;
            }
            int size = parents.size();
            for (int i = 0; i < size; i++) {
                addParent(parents.get(i));
            }
        }
    }

    private static class AnimationEvent {
        static final int ANIMATION_DELAY_ENDED = 1;
        static final int ANIMATION_END = 2;
        static final int ANIMATION_START = 0;
        final int mEvent;
        final Node mNode;

        AnimationEvent(Node node, int event) {
            this.mNode = node;
            this.mEvent = event;
        }

        long getTime() {
            if (this.mEvent == 0) {
                return this.mNode.mStartTime;
            }
            if (this.mEvent != 1) {
                return this.mNode.mEndTime;
            }
            if (this.mNode.mStartTime == -1) {
                return -1L;
            }
            return this.mNode.mAnimation.getStartDelay() + this.mNode.mStartTime;
        }

        public String toString() {
            String eventStr;
            if (this.mEvent == 0) {
                eventStr = "start";
            } else {
                eventStr = this.mEvent == 1 ? "delay ended" : "end";
            }
            return eventStr + " " + this.mNode.mAnimation.toString();
        }
    }

    private class SeekState {
        private long mPlayTime;
        private boolean mSeekingInReverse;

        private SeekState() {
            this.mPlayTime = -1L;
            this.mSeekingInReverse = false;
        }

        void reset() {
            this.mPlayTime = -1L;
            this.mSeekingInReverse = false;
        }

        void setPlayTime(long playTime, boolean inReverse) {
            if (AnimatorSet.this.getTotalDuration() != -1) {
                this.mPlayTime = Math.min(playTime, AnimatorSet.this.getTotalDuration() - AnimatorSet.this.mStartDelay);
            } else {
                this.mPlayTime = playTime;
            }
            this.mPlayTime = Math.max(0L, this.mPlayTime);
            this.mSeekingInReverse = inReverse;
        }

        void updateSeekDirection(boolean inReverse) {
            if (inReverse && AnimatorSet.this.getTotalDuration() == -1) {
                throw new UnsupportedOperationException("Error: Cannot reverse infinite animator set");
            }
            if (this.mPlayTime >= 0 && inReverse != this.mSeekingInReverse) {
                this.mPlayTime = (AnimatorSet.this.getTotalDuration() - AnimatorSet.this.mStartDelay) - this.mPlayTime;
                this.mSeekingInReverse = inReverse;
            }
        }

        long getPlayTime() {
            return this.mPlayTime;
        }

        long getPlayTimeNormalized() {
            if (AnimatorSet.this.mReversing) {
                return (AnimatorSet.this.getTotalDuration() - AnimatorSet.this.mStartDelay) - this.mPlayTime;
            }
            return this.mPlayTime;
        }

        boolean isActive() {
            return this.mPlayTime != -1;
        }
    }

    public class Builder {
        private Node mCurrentNode;

        Builder(Animator anim) {
            AnimatorSet.this.mDependencyDirty = true;
            this.mCurrentNode = AnimatorSet.this.getNodeForAnimation(anim);
        }

        public Builder with(Animator anim) {
            Node node = AnimatorSet.this.getNodeForAnimation(anim);
            this.mCurrentNode.addSibling(node);
            return this;
        }

        public Builder before(Animator anim) {
            Node node = AnimatorSet.this.getNodeForAnimation(anim);
            this.mCurrentNode.addChild(node);
            return this;
        }

        public Builder after(Animator anim) {
            Node node = AnimatorSet.this.getNodeForAnimation(anim);
            this.mCurrentNode.addParent(node);
            return this;
        }

        public Builder after(long delay) {
            ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
            anim.setDuration(delay);
            after(anim);
            return this;
        }
    }
}
