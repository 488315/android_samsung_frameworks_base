package com.android.wm.shell.bubbles;

import android.app.INotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.ServiceManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.systemui.R;
import com.android.systemui.plugins.subscreen.SubRoom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleData {
    public static final Comparator BUBBLES_BY_SORT_KEY_DESCENDING = Comparator.comparing(new BubbleData$$ExternalSyntheticLambda1()).reversed();
    public Bubbles.BubbleMetadataFlagListener mBubbleMetadataFlagListener;
    public final List mBubbles;
    public Bubbles.PendingIntentCanceledListener mCancelledListener;
    public int mCurrentUserId;
    public boolean mExpanded;
    public Listener mListener;
    public final BubbleLogger mLogger;
    public final Executor mMainExecutor;
    public int mMaxBubbles;
    public int mMaxOverflowBubbles;
    public boolean mNeedsTrimming;
    public final BubbleOverflow mOverflow;
    public final List mOverflowBubbles;
    public final HashMap mPendingBubbles;
    public final BubblePositioner mPositioner;
    public BubbleViewProvider mSelectedBubble;
    public boolean mShowingOverflow;
    public Update mStateChange;
    public final ArrayMap mSuppressedBubbles = new ArrayMap();
    public final ArraySet mVisibleLocusIds = new ArraySet();
    public TimeSource mTimeSource = new BubbleData$$ExternalSyntheticLambda3();
    public final HashMap mSuppressedGroupKeys = new HashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Listener {
        void applyUpdate(Update update);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TimeSource {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Update {
        public Bubble addedBubble;
        public Bubble addedOverflowBubble;
        public final List bubbles;
        public boolean expanded;
        public boolean expandedChanged;
        public boolean orderChanged;
        public final List overflowBubbles;
        public final List removedBubbles;
        public Bubble removedOverflowBubble;
        public BubbleViewProvider selectedBubble;
        public boolean selectionChanged;
        public Bubble suppressedBubble;
        public boolean suppressedSummaryChanged;
        public String suppressedSummaryGroup;
        public Bubble unsuppressedBubble;
        public Bubble updatedBubble;

        public /* synthetic */ Update(List list, List list2, int i) {
            this(list, list2);
        }

        public final void bubbleRemoved(int i, Bubble bubble) {
            ((ArrayList) this.removedBubbles).add(new Pair(bubble, Integer.valueOf(i)));
        }

        private Update(List<Bubble> list, List<Bubble> list2) {
            this.removedBubbles = new ArrayList();
            this.bubbles = Collections.unmodifiableList(list);
            this.overflowBubbles = Collections.unmodifiableList(list2);
        }
    }

    public BubbleData(Context context, BubbleLogger bubbleLogger, BubblePositioner bubblePositioner, Executor executor) {
        this.mLogger = bubbleLogger;
        this.mPositioner = bubblePositioner;
        this.mMainExecutor = executor;
        this.mOverflow = new BubbleOverflow(context, bubblePositioner);
        ArrayList arrayList = new ArrayList();
        this.mBubbles = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mOverflowBubbles = arrayList2;
        this.mPendingBubbles = new HashMap();
        this.mStateChange = new Update(arrayList, arrayList2, 0);
        this.mMaxBubbles = bubblePositioner.mMaxBubbles;
        this.mMaxOverflowBubbles = context.getResources().getInteger(R.integer.bubbles_max_overflow);
    }

    public static void performActionOnBubblesMatching(List list, Predicate predicate, BubbleData$$ExternalSyntheticLambda6 bubbleData$$ExternalSyntheticLambda6) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Bubble bubble = (Bubble) it.next();
            if (predicate.test(bubble)) {
                arrayList.add(bubble);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            bubbleData$$ExternalSyntheticLambda6.accept((Bubble) it2.next());
        }
    }

    public final void dismissAll(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("dismissAll: reason=", i, "Bubbles");
        ArrayList arrayList = (ArrayList) this.mBubbles;
        boolean isEmpty = arrayList.isEmpty();
        ArrayMap arrayMap = this.mSuppressedBubbles;
        if (isEmpty && arrayMap.isEmpty()) {
            return;
        }
        setExpandedInternal(false);
        setSelectedBubbleInternal(null);
        while (!arrayList.isEmpty()) {
            doRemove(i, ((Bubble) arrayList.get(0)).mKey);
        }
        while (!arrayMap.isEmpty()) {
            doRemove(i, ((Bubble) arrayMap.removeAt(0)).mKey);
        }
        dispatchPendingChanges();
    }

    public final void dismissBubbleWithKey(int i, String str) {
        Log.d("Bubbles", "notificationEntryRemoved: key=" + str + " reason=" + i);
        doRemove(i, str);
        dispatchPendingChanges();
    }

    public final void dispatchPendingChanges() {
        int i = 0;
        if (this.mListener != null) {
            Update update = this.mStateChange;
            if ((!update.expandedChanged && !update.selectionChanged && update.addedBubble == null && update.updatedBubble == null && ((ArrayList) update.removedBubbles).isEmpty() && update.addedOverflowBubble == null && update.removedOverflowBubble == null && !update.orderChanged && update.suppressedBubble == null && update.unsuppressedBubble == null && !update.suppressedSummaryChanged && update.suppressedSummaryGroup == null) ? false : true) {
                this.mListener.applyUpdate(this.mStateChange);
            }
        }
        this.mStateChange = new Update(this.mBubbles, this.mOverflowBubbles, i);
    }

    public final void doRemove(int i, String str) {
        ArrayList arrayList;
        PendingIntent pendingIntent;
        BubbleViewInfoTask bubbleViewInfoTask;
        AbstractC0000x2c234b15.m3m("doRemove: ", str, "Bubbles");
        HashMap hashMap = this.mPendingBubbles;
        if (hashMap.containsKey(str)) {
            hashMap.remove(str);
        }
        boolean z = i == 5 || i == 9 || i == 7 || i == 4 || i == 12 || i == 13 || i == 8 || i == 16;
        int i2 = 0;
        while (true) {
            arrayList = (ArrayList) this.mBubbles;
            if (i2 >= arrayList.size()) {
                i2 = -1;
                break;
            } else if (((Bubble) arrayList.get(i2)).mKey.equals(str)) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 != -1) {
            Bubble bubble = (Bubble) arrayList.get(i2);
            BubbleViewInfoTask bubbleViewInfoTask2 = bubble.mInflationTask;
            if (bubbleViewInfoTask2 != null) {
                bubbleViewInfoTask2.cancel(true);
            }
            overflowBubble(i, bubble);
            if (arrayList.size() == 1) {
                if (this.mExpanded) {
                    this.mShowingOverflow = false;
                    setExpandedInternal(false);
                    this.mSelectedBubble = null;
                } else {
                    setExpandedInternal(false);
                    this.mSelectedBubble = null;
                }
            }
            if (i2 < arrayList.size() - 1) {
                this.mStateChange.orderChanged = true;
            }
            arrayList.remove(i2);
            this.mStateChange.bubbleRemoved(i, bubble);
            if (!this.mExpanded) {
                this.mStateChange.orderChanged |= repackAll();
            }
            if (Objects.equals(this.mSelectedBubble, bubble)) {
                setNewSelectedIndex(i2);
            }
            if (i == 1 && (pendingIntent = bubble.mDeleteIntent) != null) {
                try {
                    pendingIntent.send();
                    return;
                } catch (PendingIntent.CanceledException unused) {
                    Log.w("Bubbles", "Failed to send delete intent for bubble with key: " + bubble.mKey);
                    return;
                }
            }
            return;
        }
        if (hasOverflowBubbleWithKey(str) && z) {
            Bubble overflowBubbleWithKey = getOverflowBubbleWithKey(str);
            Log.d("Bubbles", "Cancel overflow bubble: " + overflowBubbleWithKey);
            if (overflowBubbleWithKey != null && (bubbleViewInfoTask = overflowBubbleWithKey.mInflationTask) != null) {
                bubbleViewInfoTask.cancel(true);
            }
            BubbleLogger bubbleLogger = this.mLogger;
            bubbleLogger.getClass();
            if (i == 5) {
                bubbleLogger.log(overflowBubbleWithKey, BubbleLogger.Event.BUBBLE_OVERFLOW_REMOVE_CANCEL);
            } else if (i == 9) {
                bubbleLogger.log(overflowBubbleWithKey, BubbleLogger.Event.BUBBLE_OVERFLOW_REMOVE_GROUP_CANCEL);
            } else if (i == 7) {
                bubbleLogger.log(overflowBubbleWithKey, BubbleLogger.Event.BUBBLE_OVERFLOW_REMOVE_NO_LONGER_BUBBLE);
            } else if (i == 4) {
                bubbleLogger.log(overflowBubbleWithKey, BubbleLogger.Event.BUBBLE_OVERFLOW_REMOVE_BLOCKED);
            }
            ((ArrayList) this.mOverflowBubbles).remove(overflowBubbleWithKey);
            this.mStateChange.bubbleRemoved(i, overflowBubbleWithKey);
            this.mStateChange.removedOverflowBubble = overflowBubbleWithKey;
        }
        ArrayMap arrayMap = this.mSuppressedBubbles;
        if (arrayMap.values().stream().anyMatch(new BubbleData$$ExternalSyntheticLambda2(str, 0)) && z) {
            Bubble suppressedBubbleWithKey = getSuppressedBubbleWithKey(str);
            Log.d("Bubbles", "Cancel suppressed bubble: " + suppressedBubbleWithKey);
            if (suppressedBubbleWithKey != null) {
                arrayMap.remove(suppressedBubbleWithKey.mLocusId);
                BubbleViewInfoTask bubbleViewInfoTask3 = suppressedBubbleWithKey.mInflationTask;
                if (bubbleViewInfoTask3 != null) {
                    bubbleViewInfoTask3.cancel(true);
                }
                this.mStateChange.bubbleRemoved(i, suppressedBubbleWithKey);
            }
        }
    }

    public final void doSuppress(Bubble bubble) {
        Log.d("Bubbles", "doSuppressed: " + bubble);
        this.mStateChange.suppressedBubble = bubble;
        bubble.setSuppressBubble(true);
        List list = this.mBubbles;
        int indexOf = ((ArrayList) list).indexOf(bubble);
        this.mStateChange.orderChanged = ((ArrayList) list).size() - 1 != indexOf;
        ((ArrayList) list).remove(indexOf);
        if (Objects.equals(this.mSelectedBubble, bubble)) {
            if (((ArrayList) list).isEmpty()) {
                this.mSelectedBubble = null;
            } else {
                setNewSelectedIndex(0);
            }
        }
    }

    public final void doUnsuppress(Bubble bubble) {
        Log.d("Bubbles", "doUnsuppressed: " + bubble);
        bubble.setSuppressBubble(false);
        this.mStateChange.unsuppressedBubble = bubble;
        List list = this.mBubbles;
        ((ArrayList) list).add(bubble);
        if (((ArrayList) list).size() > 1) {
            repackAll();
            this.mStateChange.orderChanged = true;
        }
        if (((ArrayList) list).get(0) == bubble) {
            setNewSelectedIndex(0);
        }
    }

    public Bubble getAnyBubbleWithkey(String str) {
        Bubble bubbleInStackWithKey = getBubbleInStackWithKey(str);
        if (bubbleInStackWithKey == null) {
            bubbleInStackWithKey = getOverflowBubbleWithKey(str);
        }
        return bubbleInStackWithKey == null ? getSuppressedBubbleWithKey(str) : bubbleInStackWithKey;
    }

    public Bubble getBubbleInStackWithKey(String str) {
        int i = 0;
        while (true) {
            List list = this.mBubbles;
            if (i >= ((ArrayList) list).size()) {
                return null;
            }
            Bubble bubble = (Bubble) ((ArrayList) list).get(i);
            if (bubble.mKey.equals(str)) {
                return bubble;
            }
            i++;
        }
    }

    public final Bubble getBubbleWithView(View view) {
        int i = 0;
        while (true) {
            List list = this.mBubbles;
            if (i >= ((ArrayList) list).size()) {
                return null;
            }
            Bubble bubble = (Bubble) ((ArrayList) list).get(i);
            BadgedImageView badgedImageView = bubble.mIconView;
            if (badgedImageView != null && badgedImageView.equals(view)) {
                return bubble;
            }
            i++;
        }
    }

    public List<Bubble> getBubbles() {
        return Collections.unmodifiableList(this.mBubbles);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bubble getOrCreateBubble(Bubble bubble, BubbleEntry bubbleEntry) {
        String key = bubble != null ? bubble.mKey : bubbleEntry.getKey();
        Bubble bubbleInStackWithKey = getBubbleInStackWithKey(key);
        HashMap hashMap = this.mPendingBubbles;
        if (bubbleInStackWithKey == null) {
            bubbleInStackWithKey = getOverflowBubbleWithKey(key);
            if (bubbleInStackWithKey == null) {
                if (hashMap.containsKey(key)) {
                    bubble = (Bubble) hashMap.get(key);
                } else if (bubbleEntry != null) {
                    bubble = new Bubble(bubbleEntry, this.mBubbleMetadataFlagListener, this.mCancelledListener, this.mMainExecutor);
                }
                if (bubbleEntry != null) {
                    bubble.setEntry(bubbleEntry);
                }
                hashMap.put(key, bubble);
                return bubble;
            }
            ((ArrayList) this.mOverflowBubbles).remove(bubbleInStackWithKey);
        }
        bubble = bubbleInStackWithKey;
        if (bubbleEntry != null) {
        }
        hashMap.put(key, bubble);
        return bubble;
    }

    public Bubble getOverflowBubbleWithKey(String str) {
        int i = 0;
        while (true) {
            List list = this.mOverflowBubbles;
            if (i >= ((ArrayList) list).size()) {
                return null;
            }
            Bubble bubble = (Bubble) ((ArrayList) list).get(i);
            if (bubble.mKey.equals(str)) {
                return bubble;
            }
            i++;
        }
    }

    public List<Bubble> getOverflowBubbles() {
        List list = this.mOverflowBubbles;
        try {
            INotificationManager asInterface = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
            for (int i = 0; i < ((ArrayList) list).size(); i++) {
                if (!asInterface.areNotificationsEnabledForPackage(((Bubble) ((ArrayList) list).get(i)).mAppName, ((Bubble) ((ArrayList) list).get(i)).mAppUid)) {
                    ((ArrayList) list).remove(i);
                }
            }
        } catch (Exception e) {
            AbsAdapter$1$$ExternalSyntheticOutline0.m39m("Exception is = ", e, "Bubbles");
        }
        return Collections.unmodifiableList(list);
    }

    public Bubble getPendingBubbleWithKey(String str) {
        for (Bubble bubble : this.mPendingBubbles.values()) {
            if (bubble.mKey.equals(str)) {
                return bubble;
            }
        }
        return null;
    }

    public Bubble getSuppressedBubbleWithKey(String str) {
        for (Bubble bubble : this.mSuppressedBubbles.values()) {
            if (bubble.mKey.equals(str)) {
                return bubble;
            }
        }
        return null;
    }

    public final boolean hasAnyBubbleWithKey(String str) {
        return hasBubbleInStackWithKey(str) || hasOverflowBubbleWithKey(str) || this.mSuppressedBubbles.values().stream().anyMatch(new BubbleData$$ExternalSyntheticLambda2(str, 0));
    }

    public final boolean hasBubbleInStackWithKey(String str) {
        return getBubbleInStackWithKey(str) != null;
    }

    public final boolean hasOverflowBubbleWithKey(String str) {
        return getOverflowBubbleWithKey(str) != null;
    }

    public boolean isSummarySuppressed(String str) {
        return this.mSuppressedGroupKeys.containsKey(str);
    }

    public final void overflowBubble(int i, Bubble bubble) {
        if (bubble.mPendingIntentCanceled) {
            return;
        }
        if ((i == 2 || i == 1 || i == 15) && !bubble.mIsAppBubble) {
            Log.d("Bubbles", "Overflowing: " + bubble);
            BubbleLogger bubbleLogger = this.mLogger;
            bubbleLogger.getClass();
            if (i == 2) {
                bubbleLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_ADD_AGED);
            } else if (i == 1) {
                bubbleLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_ADD_USER_GESTURE);
            } else if (i == 15) {
                bubbleLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_RECOVER);
            }
            ArrayList arrayList = (ArrayList) this.mOverflowBubbles;
            arrayList.remove(bubble);
            arrayList.add(0, bubble);
            this.mStateChange.addedOverflowBubble = bubble;
            BubbleViewInfoTask bubbleViewInfoTask = bubble.mInflationTask;
            if (bubbleViewInfoTask != null) {
                bubbleViewInfoTask.cancel(true);
            }
            if (arrayList.size() == this.mMaxOverflowBubbles + 1) {
                Bubble bubble2 = (Bubble) arrayList.get(arrayList.size() - 1);
                Log.d("Bubbles", "Overflow full. Remove: " + bubble2);
                this.mStateChange.bubbleRemoved(11, bubble2);
                bubbleLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_REMOVE_MAX_REACHED);
                arrayList.remove(bubble2);
                this.mStateChange.removedOverflowBubble = bubble2;
            }
        }
    }

    public final boolean repackAll() {
        Log.d("Bubbles", "repackAll()");
        List list = this.mBubbles;
        if (((ArrayList) list).isEmpty()) {
            return false;
        }
        ArrayList arrayList = new ArrayList(((ArrayList) list).size());
        list.stream().sorted(BUBBLES_BY_SORT_KEY_DESCENDING).forEachOrdered(new BubbleData$$ExternalSyntheticLambda0(arrayList, 0));
        if (arrayList.equals(list)) {
            return false;
        }
        ((ArrayList) list).clear();
        ((ArrayList) list).addAll(arrayList);
        return true;
    }

    public final void setExpanded(boolean z) {
        Log.d("Bubbles", "setExpanded: " + z);
        setExpandedInternal(z);
        dispatchPendingChanges();
    }

    public final void setExpandedInternal(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setExpandedInternal: shouldExpand=", z, "Bubbles");
        if (this.mExpanded == z) {
            return;
        }
        List list = this.mBubbles;
        if (z) {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.isEmpty() && !this.mShowingOverflow) {
                Log.e("Bubbles", "Attempt to expand stack when empty!");
                return;
            }
            BubbleViewProvider bubbleViewProvider = this.mSelectedBubble;
            if (bubbleViewProvider == null) {
                Log.e("Bubbles", "Attempt to expand stack without selected bubble!");
                return;
            }
            String key = bubbleViewProvider.getKey();
            this.mOverflow.getClass();
            if (key.equals("Overflow") && !arrayList.isEmpty()) {
                setSelectedBubbleInternal((BubbleViewProvider) arrayList.get(0));
            }
            BubbleViewProvider bubbleViewProvider2 = this.mSelectedBubble;
            if (bubbleViewProvider2 instanceof Bubble) {
                Bubble bubble = (Bubble) bubbleViewProvider2;
                ((BubbleData$$ExternalSyntheticLambda3) this.mTimeSource).getClass();
                bubble.mLastAccessed = System.currentTimeMillis();
                bubble.setSuppressNotification(true);
                bubble.setShowDot(false);
            }
            this.mStateChange.orderChanged |= repackAll();
        } else {
            ArrayList arrayList2 = (ArrayList) list;
            if (!arrayList2.isEmpty()) {
                this.mStateChange.orderChanged |= repackAll();
                if (arrayList2.indexOf(this.mSelectedBubble) > 0 && arrayList2.indexOf(this.mSelectedBubble) != 0) {
                    arrayList2.remove((Bubble) this.mSelectedBubble);
                    arrayList2.add(0, (Bubble) this.mSelectedBubble);
                    this.mStateChange.orderChanged = true;
                }
            }
        }
        if (this.mNeedsTrimming) {
            this.mNeedsTrimming = false;
            trim();
        }
        this.mExpanded = z;
        Update update = this.mStateChange;
        update.expanded = z;
        update.expandedChanged = true;
    }

    public void setMaxOverflowBubbles(int i) {
        this.mMaxOverflowBubbles = i;
    }

    public final void setNewSelectedIndex(int i) {
        List list = this.mBubbles;
        if (((ArrayList) list).isEmpty()) {
            IconCompat$$ExternalSyntheticOutline0.m30m("Bubbles list empty when attempting to select index: ", i, "Bubbles");
            return;
        }
        int min = Math.min(i, ((ArrayList) list).size() - 1);
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("setNewSelectedIndex: ", i, "Bubbles");
        setSelectedBubbleInternal((BubbleViewProvider) ((ArrayList) list).get(min));
    }

    public final void setSelectedBubble(BubbleViewProvider bubbleViewProvider) {
        Log.d("Bubbles", "setSelectedBubble: " + bubbleViewProvider);
        setSelectedBubbleInternal(bubbleViewProvider);
        dispatchPendingChanges();
    }

    public final void setSelectedBubbleInternal(BubbleViewProvider bubbleViewProvider) {
        Log.d("Bubbles", "setSelectedBubbleInternal: " + bubbleViewProvider);
        if (Objects.equals(bubbleViewProvider, this.mSelectedBubble)) {
            return;
        }
        boolean z = bubbleViewProvider != null && "Overflow".equals(bubbleViewProvider.getKey());
        if (bubbleViewProvider != null) {
            List list = this.mBubbles;
            if (!((ArrayList) list).contains(bubbleViewProvider) && !((ArrayList) this.mOverflowBubbles).contains(bubbleViewProvider) && !z) {
                Log.e("Bubbles", "Cannot select bubble which doesn't exist! (" + bubbleViewProvider + ") bubbles=" + list);
                return;
            }
        }
        if (this.mExpanded && bubbleViewProvider != null && !z) {
            Bubble bubble = (Bubble) bubbleViewProvider;
            ((BubbleData$$ExternalSyntheticLambda3) this.mTimeSource).getClass();
            bubble.mLastAccessed = System.currentTimeMillis();
            bubble.setSuppressNotification(true);
            bubble.setShowDot(false);
        }
        this.mSelectedBubble = bubbleViewProvider;
        Update update = this.mStateChange;
        update.selectedBubble = bubbleViewProvider;
        update.selectionChanged = true;
    }

    public void setTimeSource(TimeSource timeSource) {
        this.mTimeSource = timeSource;
    }

    public final void trim() {
        List list = this.mBubbles;
        if (((ArrayList) list).size() > this.mMaxBubbles) {
            int size = ((ArrayList) list).size() - this.mMaxBubbles;
            ArrayList arrayList = new ArrayList();
            list.stream().sorted(Comparator.comparingLong(new BubbleData$$ExternalSyntheticLambda4())).filter(new BubbleData$$ExternalSyntheticLambda2(this, 2)).forEachOrdered(new BubbleData$$ExternalSyntheticLambda6(size, 2, arrayList));
            arrayList.forEach(new BubbleData$$ExternalSyntheticLambda0(this, 1));
        }
    }
}
