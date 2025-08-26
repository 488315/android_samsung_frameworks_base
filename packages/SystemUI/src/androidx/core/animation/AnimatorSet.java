package androidx.core.animation;

import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AndroidRuntimeException;
import android.util.Log;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.collection.SimpleArrayMap;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.animation.AnimationHandler;
import androidx.core.animation.Animator;
import com.android.wm.shell.animation.FlingAnimationUtils$AnimatorProperties$$ExternalSyntheticLambda0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class AnimatorSet extends Animator implements AnimationHandler.AnimationFrameCallback {
    public static final AnonymousClass3 EVENT_COMPARATOR = new Comparator() { // from class: androidx.core.animation.AnimatorSet.3
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            AnimationEvent animationEvent = (AnimationEvent) obj;
            AnimationEvent animationEvent2 = (AnimationEvent) obj2;
            long time = animationEvent.getTime();
            long time2 = animationEvent2.getTime();
            if (time == time2) {
                int i = animationEvent2.mEvent;
                int i2 = animationEvent.mEvent;
                return i + i2 == 1 ? i2 - i : i - i2;
            }
            if (time2 == -1) {
                return -1;
            }
            return (time != -1 && time - time2 <= 0) ? -1 : 1;
        }
    };
    public boolean mChildrenInitialized;
    public ValueAnimator mDelayAnim;
    public long mDuration;
    public long mFirstFrame;
    public FlingAnimationUtils$AnimatorProperties$$ExternalSyntheticLambda0 mInterpolator;
    public int mLastEventId;
    public AnimatorListenerAdapter mNoOpListener;
    public long mPauseTime;
    public boolean mReversing;
    public Node mRootNode;
    public SeekState mSeekState;
    public boolean mSelfPulse;
    public long mTotalDuration;
    public ArrayList mPlayingSet = new ArrayList();
    public SimpleArrayMap mNodeMap = new SimpleArrayMap();
    public ArrayList mEvents = new ArrayList();
    public ArrayList mNodes = new ArrayList();
    public boolean mDependencyDirty = false;
    public boolean mStarted = false;

    public class AnimationEvent {
        public final int mEvent;
        public final Node mNode;

        public AnimationEvent(Node node, int i) {
            this.mNode = node;
            this.mEvent = i;
        }

        public final long getTime() {
            Node node = this.mNode;
            int i = this.mEvent;
            if (i == 0) {
                return node.mStartTime;
            }
            if (i != 1) {
                return node.mEndTime;
            }
            long j = node.mStartTime;
            if (j == -1) {
                return -1L;
            }
            return node.mAnimation.getStartDelay() + j;
        }

        public final String toString() {
            int i = this.mEvent;
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i == 0 ? NetworkAnalyticsConstants.DataPoints.OPEN_TIME : i == 1 ? "delay ended" : NetworkAnalyticsConstants.DataPoints.CLOSE_TIME, " ");
            sbM.append(this.mNode.mAnimation.toString());
            return sbM.toString();
        }
    }

    public class Builder {
        public final Node mCurrentNode;

        public Builder(Animator animator) {
            AnimatorSet.this.mDependencyDirty = true;
            this.mCurrentNode = AnimatorSet.this.getNodeForAnimation(animator);
        }
    }

    public class Node implements Cloneable {
        public Animator mAnimation;
        public ArrayList mParents;
        public ArrayList mSiblings;
        public ArrayList mChildNodes = null;
        public boolean mEnded = false;
        public Node mLatestParent = null;
        public boolean mParentsAdded = false;
        public long mStartTime = 0;
        public long mEndTime = 0;
        public long mTotalDuration = 0;

        public Node(Animator animator) {
            this.mAnimation = animator;
        }

        public final void addParent(Node node) {
            if (this.mParents == null) {
                this.mParents = new ArrayList();
            }
            if (this.mParents.contains(node)) {
                return;
            }
            this.mParents.add(node);
            if (node.mChildNodes == null) {
                node.mChildNodes = new ArrayList();
            }
            if (node.mChildNodes.contains(this)) {
                return;
            }
            node.mChildNodes.add(this);
            addParent(node);
        }

        public final void addSibling(Node node) {
            if (this.mSiblings == null) {
                this.mSiblings = new ArrayList();
            }
            if (this.mSiblings.contains(node)) {
                return;
            }
            this.mSiblings.add(node);
            node.addSibling(this);
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public final Node m893clone() {
            try {
                Node node = (Node) super.clone();
                node.mAnimation = this.mAnimation.mo892clone();
                if (this.mChildNodes != null) {
                    node.mChildNodes = new ArrayList(this.mChildNodes);
                }
                if (this.mSiblings != null) {
                    node.mSiblings = new ArrayList(this.mSiblings);
                }
                if (this.mParents != null) {
                    node.mParents = new ArrayList(this.mParents);
                }
                node.mEnded = false;
                return node;
            } catch (CloneNotSupportedException unused) {
                throw new AssertionError();
            }
        }
    }

    public class SeekState {
        public long mPlayTime = -1;
        public boolean mSeekingInReverse = false;

        public SeekState() {
        }

        public final void updateSeekDirection(boolean z) {
            AnimatorSet animatorSet = AnimatorSet.this;
            if (z && animatorSet.getTotalDuration() == -1) {
                throw new UnsupportedOperationException("Error: Cannot reverse infinite animator set");
            }
            if (this.mPlayTime < 0 || z == this.mSeekingInReverse) {
                return;
            }
            this.mPlayTime = animatorSet.getTotalDuration() - this.mPlayTime;
            this.mSeekingInReverse = z;
        }
    }

    public AnimatorSet() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(0L);
        this.mDelayAnim = valueAnimatorOfFloat;
        this.mRootNode = new Node(valueAnimatorOfFloat);
        this.mDuration = -1L;
        this.mInterpolator = null;
        this.mTotalDuration = 0L;
        this.mFirstFrame = -1L;
        this.mLastEventId = -1;
        this.mReversing = false;
        this.mSelfPulse = true;
        this.mSeekState = new SeekState();
        this.mChildrenInitialized = false;
        this.mPauseTime = -1L;
        this.mNoOpListener = new AnimatorListenerAdapter() { // from class: androidx.core.animation.AnimatorSet.1
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                AnimatorSet animatorSet = AnimatorSet.this;
                if (animatorSet.mNodeMap.get(animator) == null) {
                    throw new AndroidRuntimeException("Error: animation ended is not in the node map");
                }
                ((Node) animatorSet.mNodeMap.get(animator)).mEnded = true;
            }
        };
        this.mNodeMap.put(this.mDelayAnim, this.mRootNode);
        this.mNodes.add(this.mRootNode);
    }

    public static void findSiblings(Node node, ArrayList arrayList) {
        if (arrayList.contains(node)) {
            return;
        }
        arrayList.add(node);
        if (node.mSiblings == null) {
            return;
        }
        for (int i = 0; i < node.mSiblings.size(); i++) {
            findSiblings((Node) node.mSiblings.get(i), arrayList);
        }
    }

    public static boolean isEmptySet(AnimatorSet animatorSet) {
        animatorSet.getClass();
        for (int i = 0; i < animatorSet.getChildAnimations().size(); i++) {
            Animator animator = (Animator) animatorSet.getChildAnimations().get(i);
            if (!(animator instanceof AnimatorSet) || !isEmptySet((AnimatorSet) animator)) {
                return false;
            }
        }
        return true;
    }

    public static void pulseFrame(long j, Node node) {
        if (node.mEnded) {
            return;
        }
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = ValueAnimator.sDefaultInterpolator;
        node.mEnded = node.mAnimation.pulseAnimationFrame((long) (j * 1.0f));
    }

    @Override // androidx.core.animation.Animator
    public final void cancel() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mStarted) {
            ArrayList arrayList = this.mListeners;
            if (arrayList != null) {
                ArrayList arrayList2 = (ArrayList) arrayList.clone();
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationCancel(this);
                }
            }
            ArrayList arrayList3 = new ArrayList(this.mPlayingSet);
            int size2 = arrayList3.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((Node) arrayList3.get(i2)).mAnimation.cancel();
            }
            this.mPlayingSet.clear();
            endAnimation();
        }
    }

    public final void createDependencyGraph() {
        boolean z;
        if (!this.mDependencyDirty) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                if (((Node) this.mNodes.get(i)).mTotalDuration == ((Node) this.mNodes.get(i)).mAnimation.getTotalDuration()) {
                }
            }
            return;
        }
        this.mDependencyDirty = false;
        int size = this.mNodes.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Node) this.mNodes.get(i2)).mParentsAdded = false;
        }
        for (int i3 = 0; i3 < size; i3++) {
            Node node = (Node) this.mNodes.get(i3);
            if (!node.mParentsAdded) {
                node.mParentsAdded = true;
                ArrayList arrayList = node.mSiblings;
                if (arrayList != null) {
                    findSiblings(node, arrayList);
                    node.mSiblings.remove(node);
                    int size2 = node.mSiblings.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        ArrayList arrayList2 = ((Node) node.mSiblings.get(i4)).mParents;
                        if (arrayList2 != null) {
                            int size3 = arrayList2.size();
                            for (int i5 = 0; i5 < size3; i5++) {
                                node.addParent((Node) arrayList2.get(i5));
                            }
                        }
                    }
                    for (int i6 = 0; i6 < size2; i6++) {
                        Node node2 = (Node) node.mSiblings.get(i6);
                        ArrayList arrayList3 = node.mParents;
                        node2.getClass();
                        if (arrayList3 != null) {
                            int size4 = arrayList3.size();
                            for (int i7 = 0; i7 < size4; i7++) {
                                node2.addParent((Node) arrayList3.get(i7));
                            }
                        }
                        node2.mParentsAdded = true;
                    }
                }
            }
        }
        for (int i8 = 0; i8 < size; i8++) {
            Node node3 = (Node) this.mNodes.get(i8);
            Node node4 = this.mRootNode;
            if (node3 != node4 && node3.mParents == null) {
                node3.addParent(node4);
            }
        }
        ArrayList arrayList4 = new ArrayList(this.mNodes.size());
        Node node5 = this.mRootNode;
        node5.mStartTime = 0L;
        node5.mEndTime = this.mDelayAnim.mDuration;
        updatePlayTime(node5, arrayList4);
        this.mEvents.clear();
        for (int i9 = 1; i9 < this.mNodes.size(); i9++) {
            Node node6 = (Node) this.mNodes.get(i9);
            this.mEvents.add(new AnimationEvent(node6, 0));
            this.mEvents.add(new AnimationEvent(node6, 1));
            this.mEvents.add(new AnimationEvent(node6, 2));
        }
        Collections.sort(this.mEvents, EVENT_COMPARATOR);
        int size5 = this.mEvents.size();
        int i10 = 0;
        while (i10 < size5) {
            AnimationEvent animationEvent = (AnimationEvent) this.mEvents.get(i10);
            if (animationEvent.mEvent == 2) {
                Node node7 = animationEvent.mNode;
                long j = node7.mStartTime;
                long j2 = node7.mEndTime;
                if (j == j2) {
                    z = true;
                } else if (j2 == node7.mAnimation.getStartDelay() + j) {
                    z = false;
                }
                int i11 = i10 + 1;
                int i12 = size5;
                int i13 = i12;
                for (int i14 = i11; i14 < size5 && (i12 >= size5 || i13 >= size5); i14++) {
                    if (((AnimationEvent) this.mEvents.get(i14)).mNode == node7) {
                        if (((AnimationEvent) this.mEvents.get(i14)).mEvent == 0) {
                            i12 = i14;
                        } else if (((AnimationEvent) this.mEvents.get(i14)).mEvent == 1) {
                            i13 = i14;
                        }
                    }
                }
                if (z && i12 == this.mEvents.size()) {
                    throw new UnsupportedOperationException("Something went wrong, no start isfound after stop for an animation that has the same start and endtime.");
                }
                if (i13 == this.mEvents.size()) {
                    throw new UnsupportedOperationException("Something went wrong, no startdelay end is found after stop for an animation");
                }
                if (z) {
                    this.mEvents.add(i10, (AnimationEvent) this.mEvents.remove(i12));
                    i10 = i11;
                }
                this.mEvents.add(i10, (AnimationEvent) this.mEvents.remove(i13));
                i10 += 2;
            }
            i10++;
        }
        if (!this.mEvents.isEmpty() && ((AnimationEvent) this.mEvents.get(0)).mEvent != 0) {
            throw new UnsupportedOperationException("Sorting went bad, the start event should always be at index 0");
        }
        this.mEvents.add(0, new AnimationEvent(this.mRootNode, 0));
        this.mEvents.add(1, new AnimationEvent(this.mRootNode, 1));
        this.mEvents.add(2, new AnimationEvent(this.mRootNode, 2));
        if (((AnimationEvent) AlertController$$ExternalSyntheticOutline0.m(1, this.mEvents)).mEvent == 0 || ((AnimationEvent) AlertController$$ExternalSyntheticOutline0.m(1, this.mEvents)).mEvent == 1) {
            throw new UnsupportedOperationException("Something went wrong, the last event is not an end event");
        }
        this.mTotalDuration = ((AnimationEvent) AlertController$$ExternalSyntheticOutline0.m(1, this.mEvents)).getTime();
    }

    @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallback
    public final boolean doAnimationFrame(long j) {
        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = ValueAnimator.sDefaultInterpolator;
        if (this.mFirstFrame < 0) {
            this.mFirstFrame = j;
        }
        long j2 = this.mPauseTime;
        if (j2 > 0) {
            this.mFirstFrame = (j - j2) + this.mFirstFrame;
            this.mPauseTime = -1L;
        }
        SeekState seekState = this.mSeekState;
        if (seekState.mPlayTime != -1) {
            seekState.updateSeekDirection(this.mReversing);
            boolean z = this.mReversing;
            if (z) {
                this.mFirstFrame = j - ((long) (this.mSeekState.mPlayTime * 1.0f));
            } else {
                this.mFirstFrame = j - ((long) (this.mSeekState.mPlayTime * 1.0f));
            }
            skipToEndValue(!z);
            this.mPlayingSet.clear();
            for (int size = this.mNodes.size() - 1; size >= 0; size--) {
                ((Node) this.mNodes.get(size)).mEnded = false;
            }
            this.mLastEventId = -1;
            SeekState seekState2 = this.mSeekState;
            seekState2.mPlayTime = -1L;
            seekState2.mSeekingInReverse = false;
        }
        if (this.mReversing || j >= this.mFirstFrame + ((long) (0 * 1.0f))) {
            long j3 = (long) ((j - this.mFirstFrame) / 1.0f);
            int iFindLatestEventIdForTime = findLatestEventIdForTime(j3);
            handleAnimationEvents(this.mLastEventId, iFindLatestEventIdForTime, j3);
            this.mLastEventId = iFindLatestEventIdForTime;
            for (int i = 0; i < this.mPlayingSet.size(); i++) {
                Node node = (Node) this.mPlayingSet.get(i);
                if (!node.mEnded) {
                    pulseFrame(getPlayTimeForNode(j3, node), node);
                }
            }
            for (int size2 = this.mPlayingSet.size() - 1; size2 >= 0; size2--) {
                if (((Node) this.mPlayingSet.get(size2)).mEnded) {
                    this.mPlayingSet.remove(size2);
                }
            }
            boolean z2 = !this.mReversing ? !(this.mPlayingSet.isEmpty() && this.mLastEventId == this.mEvents.size() - 1) : !(this.mPlayingSet.size() == 1 && this.mPlayingSet.get(0) == this.mRootNode) && (!this.mPlayingSet.isEmpty() || this.mLastEventId >= 3);
            if (this.mUpdateListeners != null) {
                for (int i2 = 0; i2 < this.mUpdateListeners.size(); i2++) {
                    ((Animator.AnimatorUpdateListener) this.mUpdateListeners.get(i2)).onAnimationUpdate(this);
                }
            }
            if (z2) {
                endAnimation();
                return true;
            }
        }
        return false;
    }

    @Override // androidx.core.animation.Animator
    public final void end() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mStarted) {
            if (this.mReversing) {
                int size = this.mLastEventId;
                if (size == -1) {
                    size = this.mEvents.size();
                }
                this.mLastEventId = size;
                while (true) {
                    int i = this.mLastEventId;
                    if (i <= 0) {
                        break;
                    }
                    int i2 = i - 1;
                    this.mLastEventId = i2;
                    AnimationEvent animationEvent = (AnimationEvent) this.mEvents.get(i2);
                    Animator animator = animationEvent.mNode.mAnimation;
                    if (!((Node) this.mNodeMap.get(animator)).mEnded) {
                        int i3 = animationEvent.mEvent;
                        if (i3 == 2) {
                            animator.reverse();
                        } else if (i3 == 1 && animator.isStarted()) {
                            animator.end();
                        }
                    }
                }
            } else {
                while (this.mLastEventId < this.mEvents.size() - 1) {
                    int i4 = this.mLastEventId + 1;
                    this.mLastEventId = i4;
                    AnimationEvent animationEvent2 = (AnimationEvent) this.mEvents.get(i4);
                    Animator animator2 = animationEvent2.mNode.mAnimation;
                    if (!((Node) this.mNodeMap.get(animator2)).mEnded) {
                        int i5 = animationEvent2.mEvent;
                        if (i5 == 0) {
                            animator2.start();
                        } else if (i5 == 2 && animator2.isStarted()) {
                            animator2.end();
                        }
                    }
                }
            }
            this.mPlayingSet.clear();
        }
        endAnimation();
    }

    public final void endAnimation() {
        this.mStarted = false;
        this.mFirstFrame = -1L;
        this.mLastEventId = -1;
        this.mPauseTime = -1L;
        SeekState seekState = this.mSeekState;
        seekState.mPlayTime = -1L;
        seekState.mSeekingInReverse = false;
        this.mPlayingSet.clear();
        if (this.mSelfPulse) {
            AnimationHandler animationHandler = AnimationHandler.getInstance();
            int iIndexOf = animationHandler.mAnimationCallbacks.indexOf(this);
            if (iIndexOf >= 0) {
                animationHandler.mAnimationCallbacks.set(iIndexOf, null);
                animationHandler.mListDirty = true;
            }
        }
        ArrayList arrayList = this.mListeners;
        if (arrayList != null) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationEnd(this, this.mReversing);
            }
        }
        for (int i2 = 1; i2 < this.mNodes.size(); i2++) {
            ((Node) this.mNodes.get(i2)).mAnimation.removeListener(this.mNoOpListener);
        }
        this.mSelfPulse = true;
        this.mReversing = false;
    }

    public final int findLatestEventIdForTime(long j) {
        int size = this.mEvents.size();
        int i = this.mLastEventId;
        if (!this.mReversing) {
            for (int i2 = i + 1; i2 < size; i2++) {
                AnimationEvent animationEvent = (AnimationEvent) this.mEvents.get(i2);
                if (animationEvent.getTime() != -1 && animationEvent.getTime() <= j) {
                    i = i2;
                }
            }
            return i;
        }
        long totalDuration = getTotalDuration() - j;
        int i3 = this.mLastEventId;
        if (i3 != -1) {
            size = i3;
        }
        this.mLastEventId = size;
        for (int i4 = size - 1; i4 >= 0; i4--) {
            if (((AnimationEvent) this.mEvents.get(i4)).getTime() >= totalDuration) {
                i = i4;
            }
        }
        return i;
    }

    public final ArrayList getChildAnimations() {
        ArrayList arrayList = new ArrayList();
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            Node node = (Node) this.mNodes.get(i);
            if (node != this.mRootNode) {
                arrayList.add(node.mAnimation);
            }
        }
        return arrayList;
    }

    @Override // androidx.core.animation.Animator
    public final long getDuration() {
        return this.mDuration;
    }

    public final Node getNodeForAnimation(Animator animator) {
        Node node = (Node) this.mNodeMap.get(animator);
        if (node == null) {
            node = new Node(animator);
            this.mNodeMap.put(animator, node);
            this.mNodes.add(node);
            if (animator instanceof AnimatorSet) {
                ((AnimatorSet) animator).mSelfPulse = false;
            }
        }
        return node;
    }

    public final long getPlayTimeForNode(long j, Node node) {
        if (!this.mReversing) {
            return j - node.mStartTime;
        }
        return node.mEndTime - (getTotalDuration() - j);
    }

    @Override // androidx.core.animation.Animator
    public final long getStartDelay() {
        return 0L;
    }

    @Override // androidx.core.animation.Animator
    public final long getTotalDuration() {
        updateAnimatorsDuration();
        createDependencyGraph();
        return this.mTotalDuration;
    }

    public final void handleAnimationEvents(int i, int i2, long j) {
        if (!this.mReversing) {
            for (int i3 = i + 1; i3 <= i2; i3++) {
                AnimationEvent animationEvent = (AnimationEvent) this.mEvents.get(i3);
                Node node = animationEvent.mNode;
                int i4 = animationEvent.mEvent;
                if (i4 == 0) {
                    this.mPlayingSet.add(node);
                    if (node.mAnimation.isStarted()) {
                        node.mAnimation.cancel();
                    }
                    node.mEnded = false;
                    node.mAnimation.startWithoutPulsing(false);
                    pulseFrame(0L, node);
                } else if (i4 == 2 && !node.mEnded) {
                    pulseFrame(getPlayTimeForNode(j, node), node);
                }
            }
            return;
        }
        if (i == -1) {
            i = this.mEvents.size();
        }
        for (int i5 = i - 1; i5 >= i2; i5--) {
            AnimationEvent animationEvent2 = (AnimationEvent) this.mEvents.get(i5);
            Node node2 = animationEvent2.mNode;
            int i6 = animationEvent2.mEvent;
            if (i6 == 2) {
                if (node2.mAnimation.isStarted()) {
                    node2.mAnimation.cancel();
                }
                node2.mEnded = false;
                this.mPlayingSet.add(animationEvent2.mNode);
                node2.mAnimation.startWithoutPulsing(true);
                pulseFrame(0L, node2);
            } else if (i6 == 1 && !node2.mEnded) {
                pulseFrame(getPlayTimeForNode(j, node2), node2);
            }
        }
    }

    public final void initAnimation() {
        if (this.mInterpolator != null) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                ((Node) this.mNodes.get(i)).mAnimation.setInterpolator(this.mInterpolator);
            }
        }
        updateAnimatorsDuration();
        createDependencyGraph();
    }

    @Override // androidx.core.animation.Animator
    public final boolean isInitialized() {
        boolean z = true;
        if (this.mChildrenInitialized) {
            return true;
        }
        int i = 0;
        while (true) {
            if (i >= this.mNodes.size()) {
                break;
            }
            if (!((Node) this.mNodes.get(i)).mAnimation.isInitialized()) {
                z = false;
                break;
            }
            i++;
        }
        this.mChildrenInitialized = z;
        return z;
    }

    @Override // androidx.core.animation.Animator
    public final boolean isRunning() {
        return this.mStarted;
    }

    @Override // androidx.core.animation.Animator
    public final boolean isStarted() {
        return this.mStarted;
    }

    public final void play(ValueAnimator valueAnimator) {
        new Builder(valueAnimator);
    }

    public final void playTogether(Animator... animatorArr) {
        Builder builder = new Builder(animatorArr[0]);
        for (int i = 1; i < animatorArr.length; i++) {
            builder.mCurrentNode.addSibling(AnimatorSet.this.getNodeForAnimation(animatorArr[i]));
        }
    }

    @Override // androidx.core.animation.Animator
    public final boolean pulseAnimationFrame(long j) {
        return doAnimationFrame(j);
    }

    @Override // androidx.core.animation.Animator
    public final void reverse() {
        start(true, true);
    }

    @Override // androidx.core.animation.Animator
    public final Animator setDuration(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("duration must be a value of zero or greater");
        }
        this.mDependencyDirty = true;
        this.mDuration = j;
        return this;
    }

    @Override // androidx.core.animation.Animator
    public final void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = (FlingAnimationUtils$AnimatorProperties$$ExternalSyntheticLambda0) interpolator;
    }

    @Override // androidx.core.animation.Animator
    public final void skipToEndValue(boolean z) {
        if (this.mSelfPulse && !isInitialized()) {
            throw new UnsupportedOperationException("Children must be initialized.");
        }
        initAnimation();
        if (z) {
            for (int size = this.mEvents.size() - 1; size >= 0; size--) {
                if (((AnimationEvent) this.mEvents.get(size)).mEvent == 1) {
                    ((AnimationEvent) this.mEvents.get(size)).mNode.mAnimation.skipToEndValue(true);
                }
            }
            return;
        }
        for (int i = 0; i < this.mEvents.size(); i++) {
            if (((AnimationEvent) this.mEvents.get(i)).mEvent == 2) {
                ((AnimationEvent) this.mEvents.get(i)).mNode.mAnimation.skipToEndValue(false);
            }
        }
    }

    @Override // androidx.core.animation.Animator
    public final void start() {
        start(false, true);
    }

    @Override // androidx.core.animation.Animator
    public final void startWithoutPulsing(boolean z) {
        start(z, false);
    }

    public final String toString() {
        String string = "AnimatorSet@" + Integer.toHexString(hashCode()) + "{";
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            Node node = (Node) this.mNodes.get(i);
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, "\n    ");
            sbM.append(node.mAnimation.toString());
            string = sbM.toString();
        }
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, "\n}");
    }

    public final void updateAnimatorsDuration() {
        if (this.mDuration >= 0) {
            int size = this.mNodes.size();
            for (int i = 0; i < size; i++) {
                ((Node) this.mNodes.get(i)).mAnimation.setDuration(this.mDuration);
            }
        }
        this.mDelayAnim.setDuration(0L);
    }

    public final void updatePlayTime(Node node, ArrayList arrayList) {
        int i = 0;
        if (node.mChildNodes == null) {
            if (node == this.mRootNode) {
                while (i < this.mNodes.size()) {
                    Node node2 = (Node) this.mNodes.get(i);
                    if (node2 != this.mRootNode) {
                        node2.mStartTime = -1L;
                        node2.mEndTime = -1L;
                    }
                    i++;
                }
                return;
            }
            return;
        }
        arrayList.add(node);
        int size = node.mChildNodes.size();
        while (i < size) {
            Node node3 = (Node) node.mChildNodes.get(i);
            node3.mTotalDuration = node3.mAnimation.getTotalDuration();
            int iIndexOf = arrayList.indexOf(node3);
            if (iIndexOf >= 0) {
                while (iIndexOf < arrayList.size()) {
                    ((Node) arrayList.get(iIndexOf)).mLatestParent = null;
                    ((Node) arrayList.get(iIndexOf)).mStartTime = -1L;
                    ((Node) arrayList.get(iIndexOf)).mEndTime = -1L;
                    iIndexOf++;
                }
                node3.mStartTime = -1L;
                node3.mEndTime = -1L;
                node3.mLatestParent = null;
                Log.w("AnimatorSet", "Cycle found in AnimatorSet: " + this);
            } else {
                long j = node3.mStartTime;
                if (j != -1) {
                    long j2 = node.mEndTime;
                    if (j2 == -1) {
                        node3.mLatestParent = node;
                        node3.mStartTime = -1L;
                        node3.mEndTime = -1L;
                    } else {
                        if (j2 >= j) {
                            node3.mLatestParent = node;
                            node3.mStartTime = j2;
                        }
                        long j3 = node3.mTotalDuration;
                        node3.mEndTime = j3 == -1 ? -1L : node3.mStartTime + j3;
                    }
                }
                updatePlayTime(node3, arrayList);
            }
            i++;
        }
        arrayList.remove(node);
    }

    public final void start(boolean z, boolean z2) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mStarted = true;
        this.mSelfPulse = z2;
        this.mPauseTime = -1L;
        int size = this.mNodes.size();
        for (int i = 0; i < size; i++) {
            ((Node) this.mNodes.get(i)).mEnded = false;
        }
        initAnimation();
        if (z && getTotalDuration() == -1) {
            throw new UnsupportedOperationException("Cannot reverse infinite AnimatorSet");
        }
        this.mReversing = z;
        boolean zIsEmptySet = isEmptySet(this);
        if (!zIsEmptySet) {
            for (int i2 = 1; i2 < this.mNodes.size(); i2++) {
                ((Node) this.mNodes.get(i2)).mAnimation.addListener(this.mNoOpListener);
            }
            SeekState seekState = this.mSeekState;
            AnimatorSet animatorSet = AnimatorSet.this;
            long j = 0;
            if ((animatorSet.mReversing ? animatorSet.getTotalDuration() - seekState.mPlayTime : seekState.mPlayTime) == 0 && this.mReversing) {
                SeekState seekState2 = this.mSeekState;
                seekState2.mPlayTime = -1L;
                seekState2.mSeekingInReverse = false;
            }
            if (isInitialized()) {
                skipToEndValue(!this.mReversing);
            } else if (this.mReversing) {
                if (!isInitialized()) {
                    this.mChildrenInitialized = true;
                    skipToEndValue(false);
                }
                skipToEndValue(!this.mReversing);
            } else {
                for (int size2 = this.mEvents.size() - 1; size2 >= 0; size2--) {
                    if (((AnimationEvent) this.mEvents.get(size2)).mEvent == 1) {
                        Animator animator = ((AnimationEvent) this.mEvents.get(size2)).mNode.mAnimation;
                        if (animator.isInitialized()) {
                            animator.skipToEndValue(true);
                        }
                    }
                }
            }
            boolean z3 = this.mReversing;
            SeekState seekState3 = this.mSeekState;
            if (seekState3.mPlayTime != -1) {
                seekState3.updateSeekDirection(z3);
                j = this.mSeekState.mPlayTime;
            }
            int iFindLatestEventIdForTime = findLatestEventIdForTime(j);
            handleAnimationEvents(-1, iFindLatestEventIdForTime, j);
            for (int size3 = this.mPlayingSet.size() - 1; size3 >= 0; size3--) {
                if (((Node) this.mPlayingSet.get(size3)).mEnded) {
                    this.mPlayingSet.remove(size3);
                }
            }
            this.mLastEventId = iFindLatestEventIdForTime;
            if (this.mSelfPulse) {
                Animator.addAnimationCallback(this);
            }
        }
        ArrayList arrayList = this.mListeners;
        if (arrayList != null) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size4 = arrayList2.size();
            for (int i3 = 0; i3 < size4; i3++) {
                ((Animator.AnimatorListener) arrayList2.get(i3)).onAnimationStart(this, z);
            }
        }
        if (zIsEmptySet) {
            end();
        }
    }

    @Override // androidx.core.animation.Animator
    /* renamed from: clone */
    public final AnimatorSet mo892clone() {
        final AnimatorSet animatorSet = (AnimatorSet) super.mo892clone();
        int size = this.mNodes.size();
        animatorSet.mStarted = false;
        animatorSet.mFirstFrame = -1L;
        animatorSet.mLastEventId = -1;
        animatorSet.mPauseTime = -1L;
        animatorSet.mSeekState = new SeekState();
        animatorSet.mSelfPulse = true;
        animatorSet.mPlayingSet = new ArrayList();
        animatorSet.mNodeMap = new SimpleArrayMap();
        animatorSet.mNodes = new ArrayList(size);
        animatorSet.mEvents = new ArrayList();
        animatorSet.mNoOpListener = new AnimatorListenerAdapter(this) { // from class: androidx.core.animation.AnimatorSet.2
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                AnimatorSet animatorSet2 = animatorSet;
                if (animatorSet2.mNodeMap.get(animator) == null) {
                    throw new AndroidRuntimeException("Error: animation ended is not in the node map");
                }
                ((Node) animatorSet2.mNodeMap.get(animator)).mEnded = true;
            }
        };
        animatorSet.mReversing = false;
        animatorSet.mDependencyDirty = true;
        HashMap map = new HashMap(size);
        for (int i = 0; i < size; i++) {
            Node node = (Node) this.mNodes.get(i);
            Node nodeM893clone = node.m893clone();
            nodeM893clone.mAnimation.removeListener(this.mNoOpListener);
            map.put(node, nodeM893clone);
            animatorSet.mNodes.add(nodeM893clone);
            animatorSet.mNodeMap.put(nodeM893clone.mAnimation, nodeM893clone);
        }
        Node node2 = (Node) map.get(this.mRootNode);
        animatorSet.mRootNode = node2;
        animatorSet.mDelayAnim = (ValueAnimator) node2.mAnimation;
        for (int i2 = 0; i2 < size; i2++) {
            Node node3 = (Node) this.mNodes.get(i2);
            Node node4 = (Node) map.get(node3);
            Node node5 = node3.mLatestParent;
            node4.mLatestParent = node5 == null ? null : (Node) map.get(node5);
            ArrayList arrayList = node3.mChildNodes;
            int size2 = arrayList == null ? 0 : arrayList.size();
            for (int i3 = 0; i3 < size2; i3++) {
                node4.mChildNodes.set(i3, (Node) map.get(node3.mChildNodes.get(i3)));
            }
            ArrayList arrayList2 = node3.mSiblings;
            int size3 = arrayList2 == null ? 0 : arrayList2.size();
            for (int i4 = 0; i4 < size3; i4++) {
                node4.mSiblings.set(i4, (Node) map.get(node3.mSiblings.get(i4)));
            }
            ArrayList arrayList3 = node3.mParents;
            int size4 = arrayList3 == null ? 0 : arrayList3.size();
            for (int i5 = 0; i5 < size4; i5++) {
                node4.mParents.set(i5, (Node) map.get(node3.mParents.get(i5)));
            }
        }
        return animatorSet;
    }

    public final void playTogether(Collection collection) {
        if (collection != null) {
            ArrayList arrayList = (ArrayList) collection;
            if (arrayList.size() > 0) {
                int size = arrayList.size();
                int i = 0;
                Builder builder = null;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    Animator animator = (Animator) obj;
                    if (builder == null) {
                        builder = new Builder(animator);
                    } else {
                        builder.mCurrentNode.addSibling(AnimatorSet.this.getNodeForAnimation(animator));
                    }
                }
            }
        }
    }
}
