package com.android.wm.shell.bubbles;

import android.app.PendingIntent;
import android.content.Context;
import android.content.LocusId;
import android.content.pm.ShortcutInfo;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda1;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubbleOverflowContainerView;
import com.android.wm.shell.bubbles.storage.BubbleEntity;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$$ExternalSyntheticLambda0;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0;
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

    public interface TimeSource {
    }

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
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (i != 18 || (bubbleInStackWithKey = getBubbleInStackWithKey(str)) == null || Math.max(bubbleInStackWithKey.mLastUpdated, bubbleInStackWithKey.mLastAccessed) <= jCurrentTimeMillis) {
            doRemove(i, str);
            dispatchPendingChanges();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:49:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x008f  */
    /* JADX WARN: Type inference failed for: r15v6, types: [com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda28] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchPendingChanges() {
        ArrayList arrayList;
        int i;
        BubbleStackView bubbleStackView;
        BadgedImageView badgedImageView;
        BadgedImageView badgedImageView2;
        if (this.mListener != null) {
            Update update = this.mStateChange;
            if (update.expandedChanged || update.selectionChanged || update.addedBubble != null || update.updatedBubble != null || !((ArrayList) update.removedBubbles).isEmpty() || update.addedOverflowBubble != null || update.removedOverflowBubble != null || update.orderChanged || update.suppressedBubble != null || update.unsuppressedBubble != null || update.suppressedSummaryChanged || update.suppressedSummaryGroup != null || update.mBubbleBarLocation != null || update.showOverflowChanged) {
                Update update2 = this.mStateChange;
                BubbleViewProvider bubbleViewProvider = this.mSelectedBubble;
                if (bubbleViewProvider != null) {
                    BubbleEducationController bubbleEducationController = this.mEducationController;
                    if (Settings.Secure.getInt(bubbleEducationController.context.getContentResolver(), "force_hide_bubbles_user_education", 0) == 0) {
                        if (bubbleViewProvider instanceof Bubble ? ((Bubble) bubbleViewProvider).isChat() : false) {
                            if (bubbleEducationController.prefs.getBoolean("HasSeenBubblesOnboarding", false)) {
                                if ((Settings.Secure.getInt(bubbleEducationController.context.getContentResolver(), "force_show_bubbles_user_education", 0) != 0) != false) {
                                }
                            } else {
                                boolean z = this.mExpanded ? false : true;
                                update2.shouldShowEducation = z;
                                BubbleController.AnonymousClass10 anonymousClass10 = this.mListener;
                                Update update3 = this.mStateChange;
                                anonymousClass10.getClass();
                                final boolean z2 = true;
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                    Bubble bubble = update3.addedBubble;
                                    String strValueOf = String.valueOf(bubble != null ? bubble.mKey : "null");
                                    boolean z3 = !((ArrayList) update3.removedBubbles).isEmpty();
                                    Bubble bubble2 = update3.updatedBubble;
                                    String strValueOf2 = String.valueOf(bubble2 != null ? bubble2.mKey : "null");
                                    boolean z4 = update3.orderChanged;
                                    boolean z5 = update3.expandedChanged;
                                    boolean z6 = update3.expanded;
                                    boolean z7 = update3.selectionChanged;
                                    BubbleViewProvider bubbleViewProvider2 = update3.selectedBubble;
                                    String strValueOf3 = String.valueOf(bubbleViewProvider2 != null ? bubbleViewProvider2.getKey() : "null");
                                    Bubble bubble3 = update3.suppressedBubble;
                                    String strValueOf4 = String.valueOf(bubble3 != null ? bubble3.mKey : "null");
                                    Bubble bubble4 = update3.unsuppressedBubble;
                                    String strValueOf5 = String.valueOf(bubble4 != null ? bubble4.mKey : "null");
                                    boolean z8 = update3.shouldShowEducation;
                                    boolean z9 = update3.showOverflowChanged;
                                    BubbleBarLocation bubbleBarLocation = update3.mBubbleBarLocation;
                                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 1730797934466383175L, 15744972, strValueOf, Boolean.valueOf(z3), strValueOf2, Boolean.valueOf(z4), Boolean.valueOf(z5), Boolean.valueOf(z6), Boolean.valueOf(z7), strValueOf3, strValueOf4, strValueOf5, Boolean.valueOf(z8), Boolean.valueOf(z9), String.valueOf(bubbleBarLocation != null ? bubbleBarLocation.toString() : "null"));
                                }
                                BubbleController.this.ensureBubbleViewsAndWindowCreated();
                                BubbleController.this.loadOverflowBubblesFromDisk();
                                if (update3.showOverflowChanged) {
                                    BubbleController.AnonymousClass8 anonymousClass8 = BubbleController.this.mBubbleViewCallback;
                                    update3.overflowBubbles.isEmpty();
                                    anonymousClass8.getClass();
                                }
                                BubbleData bubbleData = BubbleController.this.mBubbleData;
                                BubbleOverflow bubbleOverflow = bubbleData.mOverflow;
                                if (bubbleOverflow != null) {
                                    Iterator<Bubble> it = bubbleData.getOverflowBubbles().iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            bubbleOverflow.showDot = false;
                                            BadgedImageView badgedImageView3 = bubbleOverflow.overflowBtn;
                                            if (badgedImageView3 != null && badgedImageView3.getVisibility() == 0 && (badgedImageView = bubbleOverflow.overflowBtn) != null) {
                                                badgedImageView.updateDotVisibility(true);
                                            }
                                        } else if (it.next().showDot()) {
                                            bubbleOverflow.showDot = true;
                                            BadgedImageView badgedImageView4 = bubbleOverflow.overflowBtn;
                                            if (badgedImageView4 != null && badgedImageView4.getVisibility() == 0 && (badgedImageView2 = bubbleOverflow.overflowBtn) != null) {
                                                badgedImageView2.updateDotVisibility(true);
                                            }
                                        }
                                    }
                                }
                                BubbleOverflowContainerView.AnonymousClass2 anonymousClass2 = BubbleController.this.mOverflowListener;
                                if (anonymousClass2 != null) {
                                    anonymousClass2.getClass();
                                    Bubble bubble5 = update3.removedOverflowBubble;
                                    BubbleOverflowContainerView bubbleOverflowContainerView = BubbleOverflowContainerView.this;
                                    if (bubble5 != null) {
                                        bubble5.cleanupViews();
                                        int iIndexOf = ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).indexOf(bubble5);
                                        ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).remove(bubble5);
                                        bubbleOverflowContainerView.mAdapter.notifyItemRemoved(iIndexOf);
                                    }
                                    Bubble bubble6 = update3.addedOverflowBubble;
                                    if (bubble6 != null) {
                                        int iIndexOf2 = ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).indexOf(bubble6);
                                        if (iIndexOf2 > 0) {
                                            ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).remove(bubble6);
                                            ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).add(0, bubble6);
                                            bubbleOverflowContainerView.mAdapter.notifyItemMoved(iIndexOf2, 0);
                                        } else {
                                            ((ArrayList) bubbleOverflowContainerView.mOverflowBubbles).add(0, bubble6);
                                            bubbleOverflowContainerView.mAdapter.notifyItemInserted(0);
                                        }
                                    }
                                    bubbleOverflowContainerView.updateEmptyStateVisibility();
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 2347520435196804818L, 0, String.valueOf(bubble6 != null ? bubble6.mKey : "null"), String.valueOf(bubble5 != null ? bubble5.mKey : "null"));
                                    }
                                }
                                ArrayList arrayList2 = new ArrayList(update3.removedBubbles);
                                ArrayList arrayList3 = new ArrayList();
                                int size = arrayList2.size();
                                int i2 = 0;
                                while (i2 < size) {
                                    Object obj = arrayList2.get(i2);
                                    i2++;
                                    Pair pair = (Pair) obj;
                                    final Bubble bubble7 = (Bubble) pair.first;
                                    int iIntValue = ((Integer) pair.second).intValue();
                                    final BubbleStackView bubbleStackView2 = BubbleController.this.mStackView;
                                    if (bubbleStackView2 != 0) {
                                        if (bubbleStackView2.mIsExpanded && bubbleStackView2.getBubbleCount() == 1) {
                                            bubbleStackView2.mRemovingLastBubbleWhileExpanded = true;
                                            final BadgedImageView badgedImageView5 = bubble7.mIconView;
                                            final BubbleViewProvider bubbleViewProvider3 = bubbleStackView2.mExpandedBubble;
                                            bubbleStackView2.showScrim(false, new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda28
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    BubbleStackView bubbleStackView3 = bubbleStackView2;
                                                    Bubble bubble8 = bubble7;
                                                    BadgedImageView badgedImageView6 = badgedImageView5;
                                                    BubbleViewProvider bubbleViewProvider4 = bubbleViewProvider3;
                                                    bubbleStackView3.mRemovingLastBubbleWhileExpanded = false;
                                                    bubble8.cleanupExpandedView(true);
                                                    if (badgedImageView6 != null) {
                                                        bubbleStackView3.mBubbleContainer.removeView(badgedImageView6);
                                                    }
                                                    bubble8.cleanupViews();
                                                    bubbleStackView3.updateExpandedView();
                                                    if (bubbleViewProvider4 == bubbleStackView3.mExpandedBubble) {
                                                        bubbleStackView3.mExpandedBubble = null;
                                                    }
                                                }
                                            });
                                            bubbleStackView2.logBubbleEvent(bubble7, 5);
                                        } else {
                                            if (bubbleStackView2.getBubbleCount() == 1) {
                                                bubbleStackView2.mExpandedBubble = null;
                                            }
                                            int i3 = 0;
                                            while (true) {
                                                if (i3 < bubbleStackView2.getBubbleCount()) {
                                                    View childAt = bubbleStackView2.mBubbleContainer.getChildAt(i3);
                                                    if (childAt instanceof BadgedImageView) {
                                                        BubbleViewProvider bubbleViewProvider4 = ((BadgedImageView) childAt).mBubble;
                                                        if ((bubbleViewProvider4 != null ? bubbleViewProvider4.getKey() : null).equals(bubble7.mKey)) {
                                                            bubbleStackView2.mBubbleContainer.removeViewAt(i3);
                                                            if (bubbleStackView2.mBubbleData.hasOverflowBubbleWithKey(bubble7.mKey)) {
                                                                bubble7.cleanupExpandedView(true);
                                                            } else {
                                                                bubble7.cleanupViews();
                                                            }
                                                            bubbleStackView2.updateExpandedView();
                                                            if (bubbleStackView2.getBubbleCount() == 0 && !bubbleStackView2.mIsExpanded) {
                                                                bubbleStackView2.mStackAnimationController.setStackPosition(bubbleStackView2.mPositioner.getRestingPosition());
                                                                bubbleStackView2.mDismissView.hide();
                                                            }
                                                            bubbleStackView2.logBubbleEvent(bubble7, 5);
                                                        }
                                                    }
                                                    i3++;
                                                } else if ((bubble7.mFlags & 8) != 0) {
                                                    bubble7.cleanupViews();
                                                    bubbleStackView2.logBubbleEvent(bubble7, 5);
                                                } else {
                                                    Log.w("Bubbles", "was asked to remove Bubble, but didn't find the view! " + bubble7);
                                                }
                                            }
                                        }
                                    }
                                    if (iIntValue != 8 && iIntValue != 14) {
                                        if (iIntValue == 5 || iIntValue == 12) {
                                            arrayList3.add(bubble7);
                                        }
                                        if (!BubbleController.this.mBubbleData.hasBubbleInStackWithKey(bubble7.mKey)) {
                                            if (BubbleController.this.mBubbleData.hasOverflowBubbleWithKey(bubble7.mKey) || !(!bubble7.showInShade() || iIntValue == 5 || iIntValue == 9)) {
                                                if (bubble7.mIsBubble) {
                                                    BubbleController.this.setIsBubble(bubble7, false);
                                                }
                                                BubblesManager.AnonymousClass5 anonymousClass5 = BubbleController.this.mSysuiProxy;
                                                anonymousClass5.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(anonymousClass5, bubble7.mKey, 0));
                                            } else {
                                                BubblesManager.AnonymousClass5 anonymousClass52 = BubbleController.this.mSysuiProxy;
                                                anonymousClass52.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(anonymousClass52, bubble7.mKey, 2));
                                            }
                                        }
                                    }
                                }
                                BubbleController bubbleController = BubbleController.this;
                                BubbleDataRepository bubbleDataRepository = bubbleController.mDataRepository;
                                int i4 = bubbleController.mCurrentUserId;
                                bubbleDataRepository.getClass();
                                List listTransform = BubbleDataRepository.transform(arrayList3);
                                BubbleVolatileRepository bubbleVolatileRepository = bubbleDataRepository.volatileRepository;
                                synchronized (bubbleVolatileRepository) {
                                    try {
                                        ArrayList arrayList4 = new ArrayList();
                                        arrayList = (ArrayList) listTransform;
                                        int size2 = arrayList.size();
                                        int i5 = 0;
                                        while (i5 < size2) {
                                            Object obj2 = arrayList.get(i5);
                                            i5++;
                                            if (bubbleVolatileRepository.getEntities(i4).removeIf(new BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0(new BubbleVolatileRepository$$ExternalSyntheticLambda0((BubbleEntity) obj2, 1)))) {
                                                arrayList4.add(obj2);
                                            }
                                        }
                                        bubbleVolatileRepository.uncache(arrayList4);
                                    } finally {
                                    }
                                }
                                if (!arrayList.isEmpty()) {
                                    BubbleDataRepository.persistToDisk$default(bubbleDataRepository);
                                }
                                Bubble bubble8 = update3.addedBubble;
                                if (bubble8 != null) {
                                    BubbleController bubbleController2 = BubbleController.this;
                                    BubbleDataRepository bubbleDataRepository2 = bubbleController2.mDataRepository;
                                    int i6 = bubbleController2.mCurrentUserId;
                                    bubbleDataRepository2.getClass();
                                    List listTransform2 = BubbleDataRepository.transform(Collections.singletonList(bubble8));
                                    bubbleDataRepository2.volatileRepository.addBubbles(i6, listTransform2);
                                    if (!((ArrayList) listTransform2).isEmpty()) {
                                        BubbleDataRepository.persistToDisk$default(bubbleDataRepository2);
                                    }
                                    BubbleController.AnonymousClass8 anonymousClass82 = BubbleController.this.mBubbleViewCallback;
                                    Bubble bubble9 = update3.addedBubble;
                                    BubbleStackView bubbleStackView3 = BubbleController.this.mStackView;
                                    if (bubbleStackView3 != null) {
                                        bubbleStackView3.addBubble(bubble9);
                                    }
                                }
                                Bubble bubble10 = update3.updatedBubble;
                                if (bubble10 != null && (bubbleStackView = BubbleController.this.mStackView) != null) {
                                    bubbleStackView.animateInFlyoutForBubble(bubble10);
                                    bubbleStackView.requestUpdate();
                                    bubbleStackView.logBubbleEvent(bubble10, 2);
                                }
                                Bubble bubble11 = update3.suppressedBubble;
                                if (bubble11 != null) {
                                    BubbleController.this.mBubbleViewCallback.suppressionChanged(bubble11, true);
                                }
                                Bubble bubble12 = update3.unsuppressedBubble;
                                if (bubble12 != null) {
                                    BubbleController.this.mBubbleViewCallback.suppressionChanged(bubble12, false);
                                }
                                boolean z10 = update3.expandedChanged && !update3.expanded;
                                if (update3.orderChanged) {
                                    BubbleController bubbleController3 = BubbleController.this;
                                    BubbleDataRepository bubbleDataRepository3 = bubbleController3.mDataRepository;
                                    int i7 = bubbleController3.mCurrentUserId;
                                    List list = update3.bubbles;
                                    bubbleDataRepository3.getClass();
                                    List listTransform3 = BubbleDataRepository.transform(list);
                                    bubbleDataRepository3.volatileRepository.addBubbles(i7, listTransform3);
                                    if (!((ArrayList) listTransform3).isEmpty()) {
                                        BubbleDataRepository.persistToDisk$default(bubbleDataRepository3);
                                    }
                                    BubbleController.AnonymousClass8 anonymousClass83 = BubbleController.this.mBubbleViewCallback;
                                    List list2 = update3.bubbles;
                                    boolean z11 = !z10;
                                    BubbleStackView bubbleStackView4 = BubbleController.this.mStackView;
                                    if (bubbleStackView4 != null) {
                                        if (bubbleStackView4.mIsGestureInProgress) {
                                            bubbleStackView4.mShouldReorderBubblesAfterGestureCompletes = true;
                                        } else {
                                            bubbleStackView4.updateBubbleOrderInternal(list2, z11);
                                        }
                                    }
                                }
                                if (z10) {
                                    i = 0;
                                    BubbleController.this.mBubbleViewCallback.expansionChanged(false);
                                    final BubblesManager.AnonymousClass5 anonymousClass53 = BubbleController.this.mSysuiProxy;
                                    Executor executor = anonymousClass53.val$sysuiMainExecutor;
                                    final Object[] objArr = 0 == true ? 1 : 0;
                                    executor.execute(new Runnable() { // from class: com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda6
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            BubblesManager.AnonymousClass5 anonymousClass54 = anonymousClass53;
                                            ((NotificationShadeWindowControllerImpl) BubblesManager.this.mNotificationShadeWindowController).setRequestTopUi("Bubbles", objArr);
                                        }
                                    });
                                } else {
                                    i = 0;
                                }
                                if (update3.selectionChanged) {
                                    BubbleController.AnonymousClass8 anonymousClass84 = BubbleController.this.mBubbleViewCallback;
                                    BubbleViewProvider bubbleViewProvider5 = update3.selectedBubble;
                                    BubbleStackView bubbleStackView5 = BubbleController.this.mStackView;
                                    if (bubbleStackView5 != null) {
                                        bubbleStackView5.setSelectedBubble(bubbleViewProvider5);
                                    }
                                }
                                if (update3.expandedChanged && update3.expanded) {
                                    BubbleController.this.mBubbleViewCallback.expansionChanged(true);
                                    final BubblesManager.AnonymousClass5 anonymousClass54 = BubbleController.this.mSysuiProxy;
                                    anonymousClass54.val$sysuiMainExecutor.execute(new Runnable() { // from class: com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda6
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            BubblesManager.AnonymousClass5 anonymousClass542 = anonymousClass54;
                                            ((NotificationShadeWindowControllerImpl) BubblesManager.this.mNotificationShadeWindowController).setRequestTopUi("Bubbles", z2);
                                        }
                                    });
                                }
                                BubblesManager.AnonymousClass5 anonymousClass55 = BubbleController.this.mSysuiProxy;
                                anonymousClass55.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(anonymousClass55, "BubbleData.Listener.applyUpdate", 3));
                                BubbleController.this.updateBubbleViews();
                                BubbleController.BubblesImpl.CachedState cachedState = BubbleController.this.mImpl.mCachedState;
                                synchronized (cachedState) {
                                    try {
                                        if (update3.selectionChanged) {
                                            BubbleViewProvider bubbleViewProvider6 = update3.selectedBubble;
                                            cachedState.mSelectedBubbleKey = bubbleViewProvider6 != null ? bubbleViewProvider6.getKey() : null;
                                        }
                                        if (update3.expandedChanged) {
                                            cachedState.mIsStackExpanded = update3.expanded;
                                        }
                                        if (update3.suppressedSummaryChanged) {
                                            String str = (String) BubbleController.this.mBubbleData.mSuppressedGroupKeys.get(update3.suppressedSummaryGroup);
                                            if (str != null) {
                                                cachedState.mSuppressedGroupToNotifKeys.put(update3.suppressedSummaryGroup, str);
                                            } else {
                                                cachedState.mSuppressedGroupToNotifKeys.remove(update3.suppressedSummaryGroup);
                                            }
                                        }
                                        cachedState.mTmpBubbles.clear();
                                        cachedState.mTmpBubbles.addAll(update3.bubbles);
                                        cachedState.mTmpBubbles.addAll(update3.overflowBubbles);
                                        cachedState.mSuppressedBubbleKeys.clear();
                                        cachedState.mShortcutIdToBubble.clear();
                                        cachedState.mNoteBubbleTaskIds.clear();
                                        ArrayList arrayList5 = cachedState.mTmpBubbles;
                                        int size3 = arrayList5.size();
                                        while (i < size3) {
                                            Object obj3 = arrayList5.get(i);
                                            i++;
                                            Bubble bubble13 = (Bubble) obj3;
                                            HashMap map = cachedState.mShortcutIdToBubble;
                                            ShortcutInfo shortcutInfo = bubble13.mShortcutInfo;
                                            map.put(shortcutInfo != null ? shortcutInfo.getId() : bubble13.mMetadataShortcutId, bubble13);
                                            cachedState.updateBubbleSuppressedState(bubble13);
                                            if (bubble13.isNote()) {
                                                cachedState.mNoteBubbleTaskIds.put(bubble13.mKey, Integer.valueOf(bubble13.getTaskId()));
                                            }
                                        }
                                    } finally {
                                    }
                                }
                                BubbleController.this.getClass();
                            }
                        }
                    }
                }
            }
        }
        this.mStateChange = new Update(this.mBubbles, this.mOverflowBubbles, 0);
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
        int iIndexOf = ((ArrayList) this.mBubbles).indexOf(bubble);
        this.mStateChange.orderChanged = ((ArrayList) this.mBubbles).size() - 1 != iIndexOf;
        ((ArrayList) this.mBubbles).remove(iIndexOf);
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0062 A[PHI: r1
      0x0062: PHI (r1v1 com.android.wm.shell.bubbles.Bubble) = (r1v0 com.android.wm.shell.bubbles.Bubble), (r1v3 com.android.wm.shell.bubbles.Bubble) binds: [B:6:0x000f, B:18:0x004c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bubble getOrCreateBubble(BubbleEntry bubbleEntry, Bubble bubble) {
        BubbleEntry bubbleEntry2;
        String key = bubble != null ? bubble.mKey : bubbleEntry.mSbn.getKey();
        Bubble bubbleInStackWithKey = getBubbleInStackWithKey(key);
        if (bubbleInStackWithKey != null) {
            bubbleEntry2 = bubbleEntry;
            bubble = bubbleInStackWithKey;
        } else {
            bubbleInStackWithKey = getBubbleInStackWithKey(key);
            if (bubbleInStackWithKey == null) {
                bubbleInStackWithKey = getOverflowBubbleWithKey(key);
                if (bubbleInStackWithKey != null) {
                    ((ArrayList) this.mOverflowBubbles).remove(bubbleInStackWithKey);
                    ((ArrayList) this.mOverflowBubbles).remove(bubbleInStackWithKey);
                    if (((ArrayList) this.mOverflowBubbles).isEmpty()) {
                        this.mStateChange.showOverflowChanged = true;
                    }
                } else if (this.mPendingBubbles.containsKey(key)) {
                    bubbleInStackWithKey = (Bubble) this.mPendingBubbles.get(key);
                }
            }
            if (bubbleInStackWithKey == null) {
                if (bubbleEntry != null) {
                    bubbleEntry2 = bubbleEntry;
                    bubble = new Bubble(bubbleEntry2, this.mBubbleMetadataFlagListener, this.mCancelledListener, this.mMainExecutor, this.mBgExecutor);
                } else {
                    bubbleEntry2 = bubbleEntry;
                }
            }
        }
        if (bubbleEntry2 != null) {
            bubble.setEntry(bubbleEntry2);
        }
        this.mPendingBubbles.put(key, bubble);
        return bubble;
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

    /* JADX WARN: Removed duplicated region for block: B:55:0x013a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
                int iIndexOf = ((ArrayList) this.mBubbles).indexOf(bubble);
                ((ArrayList) this.mBubbles).remove(bubble);
                ((ArrayList) this.mBubbles).add(0, bubble);
                this.mStateChange.orderChanged = iIndexOf != 0;
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
            boolean zContainsKey = this.mSuppressedBubbles.containsKey(locusId);
            if (zContainsKey) {
                int i = bubble.mFlags;
                if ((i & 8) == 0 || (i & 4) == 0) {
                    this.mSuppressedBubbles.remove(locusId);
                    doUnsuppress(bubble);
                } else if (!zContainsKey) {
                    int i2 = bubble.mFlags;
                    if ((i2 & 8) != 0 || ((i2 & 4) != 0 && this.mVisibleLocusIds.contains(locusId))) {
                        this.mSuppressedBubbles.put(locusId, bubble);
                        doSuppress(bubble);
                    }
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
                Bubble bubble2 = (Bubble) AlertController$$ExternalSyntheticOutline0.m(1, (ArrayList) this.mOverflowBubbles);
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
