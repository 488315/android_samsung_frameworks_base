package com.android.wm.shell.bubbles;

import android.app.PendingIntent;
import android.content.Context;
import android.content.LocusId;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleData {
    public static final Comparator BUBBLES_BY_SORT_KEY_DESCENDING = Comparator.comparing(new BubbleData$$ExternalSyntheticLambda0()).reversed();
    public final Executor mBgExecutor;
    public BubbleController$$ExternalSyntheticLambda5 mBubbleMetadataFlagListener;
    public final List mBubbles;
    public BubbleController$$ExternalSyntheticLambda5 mCancelledListener;
    public int mCurrentUserId;
    public final BubbleEducationController mEducationController;
    public boolean mExpanded;
    public BubbleController.AnonymousClass10 mListener;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TimeSource {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Update {
        public Bubble addedBubble;
        public Bubble addedOverflowBubble;
        public final List bubbles;
        public boolean expanded;
        public boolean expandedChanged;
        public BubbleBarLocation mBubbleBarLocation;
        public boolean orderChanged;
        public final List overflowBubbles;
        public final List removedBubbles;
        public Bubble removedOverflowBubble;
        public BubbleViewProvider selectedBubble;
        public boolean selectionChanged;
        public boolean shouldShowEducation;
        public boolean showOverflowChanged;
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

    public BubbleData(Context context, BubbleLogger bubbleLogger, BubblePositioner bubblePositioner, BubbleEducationController bubbleEducationController, Executor executor, Executor executor2) {
        this.mLogger = bubbleLogger;
        this.mPositioner = bubblePositioner;
        this.mEducationController = bubbleEducationController;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
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

    public static Bubble getBubbleWithPredicate(List list, Predicate predicate) {
        for (int i = 0; i < list.size(); i++) {
            Bubble bubble = (Bubble) list.get(i);
            if (predicate.test(bubble)) {
                return bubble;
            }
        }
        return null;
    }

    public static void performActionOnBubblesMatching(List list, Predicate predicate, Consumer consumer) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Bubble bubble = (Bubble) it.next();
            if (predicate.test(bubble)) {
                arrayList.add(bubble);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            consumer.accept((Bubble) obj);
        }
    }

    public final void dismissAll(int i) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -7192058839625748459L, 1, Long.valueOf(i));
        }
        if (((ArrayList) this.mBubbles).isEmpty() && this.mSuppressedBubbles.isEmpty()) {
            return;
        }
        setExpandedInternal(false);
        setSelectedBubbleInternal(null);
        while (!((ArrayList) this.mBubbles).isEmpty()) {
            doRemove(i, ((Bubble) ((ArrayList) this.mBubbles).get(0)).mKey);
        }
        while (!this.mSuppressedBubbles.isEmpty()) {
            doRemove(i, ((Bubble) this.mSuppressedBubbles.removeAt(0)).mKey);
        }
        dispatchPendingChanges();
    }

    public final void dismissBubbleWithKey(int i, String str) {
        Bubble bubbleInStackWithKey;
        ((BubbleData$$ExternalSyntheticLambda3) this.mTimeSource).getClass();
        long currentTimeMillis = System.currentTimeMillis();
        if (i != 18 || (bubbleInStackWithKey = getBubbleInStackWithKey(str)) == null || Math.max(bubbleInStackWithKey.mLastUpdated, bubbleInStackWithKey.mLastAccessed) <= currentTimeMillis) {
            doRemove(i, str);
            dispatchPendingChanges();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0088, code lost:
    
        if ((android.provider.Settings.Secure.getInt(r4.context.getContentResolver(), "force_show_bubbles_user_education", 0) != 0) != false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x008c, code lost:
    
        if (r20.mExpanded == false) goto L53;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v6, types: [com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda28] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dispatchPendingChanges() {
        /*
            Method dump skipped, instructions count: 1394
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.bubbles.BubbleData.dispatchPendingChanges():void");
    }

    public final void doRemove(int i, String str) {
        PendingIntent pendingIntent;
        BubbleViewInfoTask bubbleViewInfoTask;
        if (this.mPendingBubbles.containsKey(str)) {
            this.mPendingBubbles.remove(str);
        }
        boolean z = i == 5 || i == 9 || i == 7 || i == 4 || i == 12 || i == 13 || i == 8 || i == 16;
        int i2 = 0;
        while (true) {
            if (i2 >= ((ArrayList) this.mBubbles).size()) {
                i2 = -1;
                break;
            } else if (((Bubble) ((ArrayList) this.mBubbles).get(i2)).mKey.equals(str)) {
                break;
            } else {
                i2++;
            }
        }
        BubbleLogger bubbleLogger = this.mLogger;
        BubblePositioner bubblePositioner = this.mPositioner;
        if (i2 != -1) {
            Bubble bubble = (Bubble) ((ArrayList) this.mBubbles).get(i2);
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -5005277508660872541L, 0, String.valueOf(bubble.mKey));
            }
            BubbleViewInfoTask bubbleViewInfoTask2 = bubble.mInflationTask;
            if (bubbleViewInfoTask2 != null) {
                bubbleViewInfoTask2.mCancelled.set(true);
            }
            overflowBubble(i, bubble);
            if (((ArrayList) this.mBubbles).size() == 1) {
                if (this.mExpanded) {
                    this.mShowingOverflow = false;
                    setExpandedInternal(false);
                    this.mSelectedBubble = null;
                } else {
                    setExpandedInternal(false);
                    this.mSelectedBubble = null;
                }
            }
            if (i2 < ((ArrayList) this.mBubbles).size() - 1) {
                this.mStateChange.orderChanged = true;
            }
            ((ArrayList) this.mBubbles).remove(i2);
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
                } catch (PendingIntent.CanceledException unused) {
                    Log.w("Bubbles", "Failed to send delete intent for bubble with key: " + bubble.mKey);
                }
            }
            bubblePositioner.getClass();
            return;
        }
        if (hasOverflowBubbleWithKey(str) && z) {
            Bubble overflowBubbleWithKey = getOverflowBubbleWithKey(str);
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -1868635834674103890L, 0, String.valueOf(str));
            }
            if (overflowBubbleWithKey != null && (bubbleViewInfoTask = overflowBubbleWithKey.mInflationTask) != null) {
                bubbleViewInfoTask.mCancelled.set(true);
            }
            bubblePositioner.getClass();
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
            Update update = this.mStateChange;
            update.removedOverflowBubble = overflowBubbleWithKey;
            update.showOverflowChanged = ((ArrayList) this.mOverflowBubbles).isEmpty();
        }
        if (this.mSuppressedBubbles.values().stream().anyMatch(new BubbleData$$ExternalSyntheticLambda1(str, 0)) && z) {
            Bubble suppressedBubbleWithKey = getSuppressedBubbleWithKey(str);
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -6737429659320042284L, 0, String.valueOf(str));
            }
            if (suppressedBubbleWithKey != null) {
                this.mSuppressedBubbles.remove(suppressedBubbleWithKey.mLocusId);
                BubbleViewInfoTask bubbleViewInfoTask3 = suppressedBubbleWithKey.mInflationTask;
                if (bubbleViewInfoTask3 != null) {
                    bubbleViewInfoTask3.mCancelled.set(true);
                }
                this.mStateChange.bubbleRemoved(i, suppressedBubbleWithKey);
            }
        }
    }

    public final void doSuppress(Bubble bubble) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 6133665050714652608L, 0, String.valueOf(bubble.mKey));
        }
        this.mStateChange.suppressedBubble = bubble;
        bubble.setSuppressBubble(true);
        int indexOf = ((ArrayList) this.mBubbles).indexOf(bubble);
        this.mStateChange.orderChanged = ((ArrayList) this.mBubbles).size() - 1 != indexOf;
        ((ArrayList) this.mBubbles).remove(indexOf);
        if (Objects.equals(this.mSelectedBubble, bubble)) {
            if (((ArrayList) this.mBubbles).isEmpty()) {
                this.mSelectedBubble = null;
            } else {
                setNewSelectedIndex(0);
            }
        }
    }

    public final void doUnsuppress(Bubble bubble) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -8932995660497406677L, 0, String.valueOf(bubble.mKey));
        }
        bubble.setSuppressBubble(false);
        this.mStateChange.unsuppressedBubble = bubble;
        ((ArrayList) this.mBubbles).add(bubble);
        if (((ArrayList) this.mBubbles).size() > 1) {
            repackAll();
            this.mStateChange.orderChanged = true;
        }
        if (((ArrayList) this.mBubbles).get(0) == bubble) {
            setNewSelectedIndex(0);
        }
    }

    public Bubble getAnyBubbleWithKey(String str) {
        Bubble bubbleInStackWithKey = getBubbleInStackWithKey(str);
        if (bubbleInStackWithKey == null) {
            bubbleInStackWithKey = getOverflowBubbleWithKey(str);
        }
        return bubbleInStackWithKey == null ? getSuppressedBubbleWithKey(str) : bubbleInStackWithKey;
    }

    public final Bubble getBubbleInStackWithKey(String str) {
        return getBubbleWithPredicate(this.mBubbles, new BubbleData$$ExternalSyntheticLambda1(str, 3));
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.wm.shell.bubbles.Bubble getOrCreateBubble(com.android.wm.shell.bubbles.BubbleEntry r9, com.android.wm.shell.bubbles.Bubble r10) {
        /*
            r8 = this;
            if (r10 == 0) goto L5
            java.lang.String r0 = r10.mKey
            goto Lb
        L5:
            android.service.notification.StatusBarNotification r0 = r9.mSbn
            java.lang.String r0 = r0.getKey()
        Lb:
            com.android.wm.shell.bubbles.Bubble r1 = r8.getBubbleInStackWithKey(r0)
            if (r1 != 0) goto L62
            com.android.wm.shell.bubbles.Bubble r1 = r8.getBubbleInStackWithKey(r0)
            if (r1 == 0) goto L18
            goto L4c
        L18:
            com.android.wm.shell.bubbles.Bubble r1 = r8.getOverflowBubbleWithKey(r0)
            if (r1 == 0) goto L3c
            java.util.List r2 = r8.mOverflowBubbles
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            r2.remove(r1)
            java.util.List r2 = r8.mOverflowBubbles
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            r2.remove(r1)
            java.util.List r2 = r8.mOverflowBubbles
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L4c
            com.android.wm.shell.bubbles.BubbleData$Update r2 = r8.mStateChange
            r3 = 1
            r2.showOverflowChanged = r3
            goto L4c
        L3c:
            java.util.HashMap r2 = r8.mPendingBubbles
            boolean r2 = r2.containsKey(r0)
            if (r2 == 0) goto L4c
            java.util.HashMap r1 = r8.mPendingBubbles
            java.lang.Object r1 = r1.get(r0)
            com.android.wm.shell.bubbles.Bubble r1 = (com.android.wm.shell.bubbles.Bubble) r1
        L4c:
            if (r1 != 0) goto L62
            if (r9 == 0) goto L60
            com.android.wm.shell.bubbles.Bubble r2 = new com.android.wm.shell.bubbles.Bubble
            com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda5 r4 = r8.mBubbleMetadataFlagListener
            com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda5 r5 = r8.mCancelledListener
            java.util.concurrent.Executor r6 = r8.mMainExecutor
            java.util.concurrent.Executor r7 = r8.mBgExecutor
            r3 = r9
            r2.<init>(r3, r4, r5, r6, r7)
            r10 = r2
            goto L64
        L60:
            r3 = r9
            goto L64
        L62:
            r3 = r9
            r10 = r1
        L64:
            if (r3 == 0) goto L69
            r10.setEntry(r3)
        L69:
            java.util.HashMap r8 = r8.mPendingBubbles
            r8.put(r0, r10)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.bubbles.BubbleData.getOrCreateBubble(com.android.wm.shell.bubbles.BubbleEntry, com.android.wm.shell.bubbles.Bubble):com.android.wm.shell.bubbles.Bubble");
    }

    public final Bubble getOverflowBubbleWithKey(String str) {
        return getBubbleWithPredicate(this.mOverflowBubbles, new BubbleData$$ExternalSyntheticLambda1(str, 2));
    }

    public List<Bubble> getOverflowBubbles() {
        return Collections.unmodifiableList(this.mOverflowBubbles);
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
        return hasBubbleInStackWithKey(str) || hasOverflowBubbleWithKey(str) || this.mSuppressedBubbles.values().stream().anyMatch(new BubbleData$$ExternalSyntheticLambda1(str, 0));
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

    public final void notificationEntryUpdated(Bubble bubble, boolean z, boolean z2, BubbleBarLocation bubbleBarLocation) {
        this.mPendingBubbles.remove(bubble.mKey);
        String str = bubble.mKey;
        Bubble bubbleInStackWithKey = getBubbleInStackWithKey(str);
        boolean z3 = z | (!bubble.mIsTextChanged);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 7240892502206002960L, 1020, String.valueOf(str), Boolean.valueOf(bubbleInStackWithKey != null), Boolean.valueOf(z3), Boolean.valueOf(z2), Boolean.valueOf(bubble.isEnabled(1)));
        }
        if (bubbleInStackWithKey == null) {
            bubble.mSuppressFlyout = z3;
            ((BubbleData$$ExternalSyntheticLambda3) this.mTimeSource).getClass();
            bubble.mLastUpdated = System.currentTimeMillis();
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 3174465872965350544L, 0, String.valueOf(str));
            }
            ((ArrayList) this.mBubbles).add(0, bubble);
            Update update = this.mStateChange;
            update.addedBubble = bubble;
            update.orderChanged = ((ArrayList) this.mBubbles).size() > 1;
            setSelectedBubbleInternal((BubbleViewProvider) ((ArrayList) this.mBubbles).get(0));
            trim();
        } else {
            bubble.mSuppressFlyout = z3;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -8830530833687167624L, 0, String.valueOf(str));
            }
            this.mStateChange.updatedBubble = bubble;
            if (!this.mExpanded && !z3) {
                int indexOf = ((ArrayList) this.mBubbles).indexOf(bubble);
                ((ArrayList) this.mBubbles).remove(bubble);
                ((ArrayList) this.mBubbles).add(0, bubble);
                this.mStateChange.orderChanged = indexOf != 0;
                setSelectedBubbleInternal((BubbleViewProvider) ((ArrayList) this.mBubbles).get(0));
            }
        }
        if (bubble.isEnabled(1)) {
            bubble.setShouldAutoExpand(false);
            setSelectedBubbleInternal(bubble);
            if (!this.mExpanded) {
                setExpandedInternal(true);
            }
        }
        boolean z4 = this.mExpanded && this.mSelectedBubble == bubble;
        bubble.setSuppressNotification((!z4 && z2 && bubble.showInShade()) ? false : true);
        bubble.setShowDot(!z4);
        LocusId locusId = bubble.mLocusId;
        if (locusId != null) {
            boolean containsKey = this.mSuppressedBubbles.containsKey(locusId);
            if (containsKey) {
                int i = bubble.mFlags;
                if ((i & 8) == 0 || (i & 4) == 0) {
                    this.mSuppressedBubbles.remove(locusId);
                    doUnsuppress(bubble);
                }
            }
            if (!containsKey) {
                int i2 = bubble.mFlags;
                if ((i2 & 8) != 0 || ((i2 & 4) != 0 && this.mVisibleLocusIds.contains(locusId))) {
                    this.mSuppressedBubbles.put(locusId, bubble);
                    doSuppress(bubble);
                }
            }
        }
        this.mStateChange.mBubbleBarLocation = bubbleBarLocation;
        dispatchPendingChanges();
    }

    public final void overflowBubble(int i, Bubble bubble) {
        if (bubble.mPendingIntentCanceled) {
            return;
        }
        if (i == 2 || i == 1 || i == 15) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 1732412474523466625L, 0, String.valueOf(bubble.mKey));
            }
            this.mPositioner.getClass();
            BubbleLogger bubbleLogger = this.mLogger;
            bubbleLogger.getClass();
            if (i == 2) {
                bubbleLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_ADD_AGED);
            } else if (i == 1) {
                bubbleLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_ADD_USER_GESTURE);
            } else if (i == 15) {
                bubbleLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_RECOVER);
            }
            if (((ArrayList) this.mOverflowBubbles).isEmpty()) {
                this.mStateChange.showOverflowChanged = true;
            }
            ((ArrayList) this.mOverflowBubbles).remove(bubble);
            ((ArrayList) this.mOverflowBubbles).add(0, bubble);
            this.mStateChange.addedOverflowBubble = bubble;
            BubbleViewInfoTask bubbleViewInfoTask = bubble.mInflationTask;
            if (bubbleViewInfoTask != null) {
                bubbleViewInfoTask.mCancelled.set(true);
            }
            if (((ArrayList) this.mOverflowBubbles).size() == this.mMaxOverflowBubbles + 1) {
                Bubble bubble2 = (Bubble) AlertController$$ExternalSyntheticOutline0.m((ArrayList) this.mOverflowBubbles, 1);
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -6275330112042878428L, 0, String.valueOf(bubble2.mKey));
                }
                this.mStateChange.bubbleRemoved(11, bubble2);
                bubbleLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_REMOVE_MAX_REACHED);
                ((ArrayList) this.mOverflowBubbles).remove(bubble2);
                this.mStateChange.removedOverflowBubble = bubble2;
            }
        }
    }

    public final boolean repackAll() {
        if (((ArrayList) this.mBubbles).isEmpty()) {
            return false;
        }
        ArrayList arrayList = new ArrayList(((ArrayList) this.mBubbles).size());
        this.mBubbles.stream().sorted(BUBBLES_BY_SORT_KEY_DESCENDING).forEachOrdered(new BubbleData$$ExternalSyntheticLambda9(arrayList, 3));
        if (arrayList.equals(this.mBubbles)) {
            return false;
        }
        ((ArrayList) this.mBubbles).clear();
        ((ArrayList) this.mBubbles).addAll(arrayList);
        return true;
    }

    public final void setExpanded(boolean z) {
        setExpandedInternal(z);
        dispatchPendingChanges();
    }

    public final void setExpandedInternal(boolean z) {
        if (this.mExpanded == z) {
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 7747077208712034159L, 3, Boolean.valueOf(z));
        }
        if (z) {
            if (((ArrayList) this.mBubbles).isEmpty() && !this.mShowingOverflow) {
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
            if (key.equals("Overflow") && !((ArrayList) this.mBubbles).isEmpty()) {
                setSelectedBubbleInternal((BubbleViewProvider) ((ArrayList) this.mBubbles).get(0));
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
        } else if (!((ArrayList) this.mBubbles).isEmpty()) {
            this.mStateChange.orderChanged |= repackAll();
            if (((ArrayList) this.mBubbles).indexOf(this.mSelectedBubble) > 0) {
                if (((ArrayList) this.mBubbles).indexOf(this.mSelectedBubble) != 0) {
                    ((ArrayList) this.mBubbles).remove((Bubble) this.mSelectedBubble);
                    ((ArrayList) this.mBubbles).add(0, (Bubble) this.mSelectedBubble);
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
        if (((ArrayList) this.mBubbles).isEmpty()) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Bubbles list empty when attempting to select index: ", "Bubbles");
        } else {
            setSelectedBubbleInternal((BubbleViewProvider) ((ArrayList) this.mBubbles).get(Math.min(i, ((ArrayList) this.mBubbles).size() - 1)));
        }
    }

    public final void setSelectedBubbleAndExpandStack(BubbleViewProvider bubbleViewProvider) {
        setSelectedBubbleInternal(bubbleViewProvider);
        setExpandedInternal(true);
        this.mStateChange.mBubbleBarLocation = null;
        dispatchPendingChanges();
    }

    public final void setSelectedBubbleFromLauncher(BubbleViewProvider bubbleViewProvider) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 1430521184300187429L, 0, String.valueOf(bubbleViewProvider != null ? bubbleViewProvider.getKey() : "null"));
        }
        this.mExpanded = true;
        if (Objects.equals(bubbleViewProvider, this.mSelectedBubble)) {
            return;
        }
        boolean z = bubbleViewProvider != null && "Overflow".equals(bubbleViewProvider.getKey());
        if (bubbleViewProvider != null && !((ArrayList) this.mBubbles).contains(bubbleViewProvider) && !((ArrayList) this.mOverflowBubbles).contains(bubbleViewProvider) && !z) {
            Log.e("Bubbles", "Cannot select bubble which doesn't exist! (" + bubbleViewProvider + ") bubbles=" + this.mBubbles);
            return;
        }
        if (bubbleViewProvider != null && !z) {
            Bubble bubble = (Bubble) bubbleViewProvider;
            ((BubbleData$$ExternalSyntheticLambda3) this.mTimeSource).getClass();
            bubble.mLastAccessed = System.currentTimeMillis();
            bubble.setSuppressNotification(true);
            bubble.setShowDot(false);
        }
        this.mSelectedBubble = bubbleViewProvider;
    }

    public final void setSelectedBubbleInternal(BubbleViewProvider bubbleViewProvider) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -8092587024843195529L, 0, String.valueOf(bubbleViewProvider != null ? bubbleViewProvider.getKey() : "null"));
        }
        if (Objects.equals(bubbleViewProvider, this.mSelectedBubble)) {
            return;
        }
        boolean z = bubbleViewProvider != null && "Overflow".equals(bubbleViewProvider.getKey());
        if (bubbleViewProvider != null && !((ArrayList) this.mBubbles).contains(bubbleViewProvider) && !((ArrayList) this.mOverflowBubbles).contains(bubbleViewProvider) && !z) {
            Log.e("Bubbles", "Cannot select bubble which doesn't exist! (" + bubbleViewProvider + ") bubbles=" + this.mBubbles);
            return;
        }
        if (this.mExpanded && bubbleViewProvider != null && !z) {
            Bubble bubble = (Bubble) bubbleViewProvider;
            ((BubbleData$$ExternalSyntheticLambda3) this.mTimeSource).getClass();
            bubble.mLastAccessed = System.currentTimeMillis();
            bubble.setSuppressNotification(true);
            bubble.setShowDot(false);
        }
        this.mSelectedBubble = bubbleViewProvider;
        if (z) {
            this.mShowingOverflow = true;
        }
        Update update = this.mStateChange;
        update.selectedBubble = bubbleViewProvider;
        update.selectionChanged = true;
    }

    public void setTimeSource(TimeSource timeSource) {
        this.mTimeSource = timeSource;
    }

    public final void trim() {
        if (((ArrayList) this.mBubbles).size() > this.mMaxBubbles) {
            final int size = ((ArrayList) this.mBubbles).size() - this.mMaxBubbles;
            final ArrayList arrayList = new ArrayList();
            this.mBubbles.stream().sorted(Comparator.comparingLong(new BubbleData$$ExternalSyntheticLambda6())).filter(new BubbleData$$ExternalSyntheticLambda7(this, 0)).forEachOrdered(new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ArrayList arrayList2 = arrayList;
                    int i = size;
                    Bubble bubble = (Bubble) obj;
                    Comparator comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                    if (arrayList2.size() < i) {
                        arrayList2.add(bubble);
                    }
                }
            });
            arrayList.forEach(new BubbleData$$ExternalSyntheticLambda9(this, 0));
        }
    }
}
