package com.android.wm.shell.bubbles;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.LocusId;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.NotificationListenerService;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.wm.shell.QpShellRune;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.WindowManagerShellWrapper;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleData;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubbleStackView;
import com.android.wm.shell.bubbles.BubbleViewInfoTask;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.bubbles.animation.AnimatableScaleMatrix;
import com.android.wm.shell.bubbles.animation.ExpandedAnimationController;
import com.android.wm.shell.bubbles.animation.PhysicsAnimationLayout;
import com.android.wm.shell.bubbles.animation.StackAnimationController;
import com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda4;
import com.android.wm.shell.bubbles.storage.BubbleEntity;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.pip.PinnedStackListenerForwarder;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda1;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda2;
import com.android.systemui.wmshell.BubblesManager$4$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.BubblesManager$4$$ExternalSyntheticLambda1;
import com.samsung.android.desktopsystemui.sharedlib.system.ActivityManagerWrapper;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleController implements ConfigurationChangeListener, RemoteCallable {
    public static final boolean BUBBLE_BAR_ENABLED = SystemProperties.getBoolean("persist.wm.debug.bubble_bar", false);
    public boolean isBubbleConversationOff;
    public final ShellExecutor mBackgroundExecutor;
    public final IStatusBarService mBarService;
    public BubbleBadgeIconFactory mBubbleBadgeIconFactory;
    public final BubbleData mBubbleData;
    public BubbleIconFactory mBubbleIconFactory;
    public final BubblePositioner mBubblePositioner;
    public BubblesManager$$ExternalSyntheticLambda1 mBubbleSALogger;
    public final Context mContext;
    public SparseArray mCurrentProfiles;
    public int mCurrentUserId;
    public final BubbleDataRepository mDataRepository;
    public final DisplayController mDisplayController;
    public final Optional mDragAndDropController;
    public int mExpandDeferCount;
    public BubbleController$$ExternalSyntheticLambda4 mExpandListener;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public SecHideInformationMirroringController mHideInformationMirroringController;
    public final BubblesImpl mImpl;
    public boolean mInflateSynchronously;
    public final LauncherApps mLauncherApps;
    public final BubbleLogger mLogger;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public BubbleEntry mNotifEntryToExpandOnShadeUnlock;
    public final Optional mOneHandedOptional;
    public int mOrientation;
    public final SparseArray mSavedUserBubbleData;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public BubbleStackView mStackView;
    public final BubbleStackView.SurfaceSynchronizer mSurfaceSynchronizer;
    public final SyncTransactionQueue mSyncQueue;
    public BubblesManager.C37394 mSysuiProxy;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TaskStackListenerImpl mTaskStackListener;
    public final TaskViewTransitions mTaskViewTransitions;
    public NotificationListenerService.Ranking mTmpRanking;
    public final UserManager mUserManager;
    public WindowInsets mWindowInsets;
    public final WindowManager mWindowManager;
    public final WindowManagerShellWrapper mWindowManagerShellWrapper;
    public WindowManager.LayoutParams mWmLayoutParams;
    public final IWindowManager mWmService;
    public BubbleData.Listener mOverflowListener = null;
    public boolean mOverflowDataLoadNeeded = true;
    public boolean mAddedToWindowManager = false;
    public int mDensityDpi = 0;
    public final Rect mScreenBounds = new Rect();
    public float mFontScale = 0.0f;
    public int mLayoutDirection = -1;
    public boolean mIsStatusBarShade = true;
    public final C37924 mBroadcastReceiver = new C37924();
    public final C37935 mBubbleStackViewCallback = new Object(this) { // from class: com.android.wm.shell.bubbles.BubbleController.5
    };
    public final C37946 mBubbleBarViewCallback = new Object(this) { // from class: com.android.wm.shell.bubbles.BubbleController.6
    };
    public final C37957 mBubbleDataListener = new BubbleData.Listener() { // from class: com.android.wm.shell.bubbles.BubbleController.7
        /* JADX WARN: Code restructure failed: missing block: B:100:0x01e3, code lost:
        
            if (r13.removeIf(new com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$removeBubbles$1$1(r12)) == false) goto L231;
         */
        /* JADX WARN: Code restructure failed: missing block: B:102:0x01e5, code lost:
        
            r9.add(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x0156, code lost:
        
            if ((r7 == 5 || r7 == 9) != false) goto L75;
         */
        @Override // com.android.wm.shell.bubbles.BubbleData.Listener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void applyUpdate(BubbleData.Update update) {
            int i;
            BubbleStackView bubbleStackView;
            BubbleStackView bubbleStackView2;
            boolean z;
            List list;
            boolean z2;
            BubbleController bubbleController = BubbleController.this;
            boolean z3 = BubbleController.BUBBLE_BAR_ENABLED;
            bubbleController.ensureBubbleViewsAndWindowCreated();
            final BubbleController bubbleController2 = BubbleController.this;
            if (bubbleController2.mOverflowDataLoadNeeded) {
                bubbleController2.mOverflowDataLoadNeeded = false;
                int i2 = bubbleController2.mCurrentUserId;
                Function1 function1 = new Function1() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda16
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        BubbleController bubbleController3 = BubbleController.this;
                        bubbleController3.getClass();
                        ((List) obj).forEach(new BubbleController$$ExternalSyntheticLambda8(bubbleController3, 2));
                        return null;
                    }
                };
                BubbleDataRepository bubbleDataRepository = bubbleController2.mDataRepository;
                BuildersKt.launch$default(bubbleDataRepository.ioScope, null, null, new BubbleDataRepository$loadBubbles$1(bubbleDataRepository, i2, function1, null), 3);
            }
            BubbleStackView bubbleStackView3 = bubbleController2.mStackView;
            Iterator<Bubble> it = bubbleStackView3.mBubbleData.getOverflowBubbles().iterator();
            while (true) {
                i = 1;
                if (!it.hasNext()) {
                    BubbleOverflow bubbleOverflow = bubbleStackView3.mBubbleOverflow;
                    bubbleOverflow.showDot = false;
                    BadgedImageView badgedImageView = bubbleOverflow.overflowBtn;
                    if (badgedImageView != null) {
                        badgedImageView.updateDotVisibility(true);
                    }
                } else if (it.next().showDot()) {
                    BubbleOverflow bubbleOverflow2 = bubbleStackView3.mBubbleOverflow;
                    bubbleOverflow2.showDot = true;
                    BadgedImageView badgedImageView2 = bubbleOverflow2.overflowBtn;
                    if (badgedImageView2 != null) {
                        badgedImageView2.updateDotVisibility(true);
                    }
                }
            }
            BubbleData.Listener listener = bubbleController2.mOverflowListener;
            if (listener != null) {
                listener.applyUpdate(update);
            }
            BubbleController bubbleController3 = BubbleController.this;
            bubbleController3.expandIfChanged(update, false);
            ArrayList arrayList = new ArrayList(update.removedBubbles);
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Pair pair = (Pair) it2.next();
                Bubble bubble = (Bubble) pair.first;
                int intValue = ((Integer) pair.second).intValue();
                BubbleStackView bubbleStackView4 = bubbleController3.mStackView;
                if (bubbleStackView4 != null) {
                    int i3 = 0;
                    while (true) {
                        if (i3 < bubbleStackView4.getBubbleCount()) {
                            View childAt = bubbleStackView4.mBubbleContainer.getChildAt(i3);
                            if (childAt instanceof BadgedImageView) {
                                BubbleViewProvider bubbleViewProvider = ((BadgedImageView) childAt).mBubble;
                                if ((bubbleViewProvider != null ? bubbleViewProvider.getKey() : null).equals(bubble.mKey)) {
                                    bubbleStackView4.mBubbleContainer.removeViewAt(i3);
                                    if (bubbleStackView4.mBubbleData.hasOverflowBubbleWithKey(bubble.mKey)) {
                                        bubble.cleanupExpandedView();
                                    } else {
                                        bubble.cleanupExpandedView();
                                        bubble.mIconView = null;
                                    }
                                    bubbleStackView4.updateExpandedView();
                                    if (bubbleStackView4.getBubbleCount() == 0 && !bubbleStackView4.mIsExpanded) {
                                        bubbleStackView4.mStackAnimationController.setStackPosition(bubbleStackView4.mPositioner.getRestingPosition());
                                        bubbleStackView4.mDismissView.hide();
                                    }
                                    bubbleStackView4.logBubbleEvent(bubble, 5);
                                }
                            }
                            i3++;
                        } else {
                            if ((bubble.mFlags & 8) != 0) {
                                bubble.cleanupExpandedView();
                                bubble.mIconView = null;
                                bubbleStackView4.logBubbleEvent(bubble, 5);
                            } else {
                                Log.d("Bubbles", "was asked to remove Bubble, but didn't find the view! " + bubble);
                            }
                        }
                    }
                }
                if (intValue != 8 && intValue != 14) {
                    if (intValue == 5 || intValue == 12) {
                        arrayList2.add(bubble);
                    }
                    String str = bubble.mKey;
                    BubbleData bubbleData = bubbleController3.mBubbleData;
                    boolean hasBubbleInStackWithKey = bubbleData.hasBubbleInStackWithKey(str);
                    final String str2 = bubble.mKey;
                    if (!hasBubbleInStackWithKey) {
                        if (!bubbleData.hasOverflowBubbleWithKey(str2)) {
                            if (bubble.showInShade()) {
                            }
                            Log.d("Bubbles", "hiding notification after bubble removed");
                            final BubblesManager.C37394 c37394 = bubbleController3.mSysuiProxy;
                            c37394.getClass();
                            c37394.val$sysuiMainExecutor.execute(new Runnable() { // from class: com.android.systemui.wmshell.BubblesManager$4$$ExternalSyntheticLambda2
                                public final /* synthetic */ int f$2 = 2;

                                @Override // java.lang.Runnable
                                public final void run() {
                                    BubblesManager.C37394 c373942 = BubblesManager.C37394.this;
                                    String str3 = str2;
                                    BubblesManager bubblesManager = BubblesManager.this;
                                    NotificationEntry entry = ((NotifPipeline) bubblesManager.mCommonNotifCollection).getEntry(str3);
                                    if (entry != null) {
                                        Iterator it3 = ((ArrayList) bubblesManager.mCallbacks).iterator();
                                        while (it3.hasNext()) {
                                            ((BubbleCoordinator.C28003) it3.next()).removeNotification(entry, new DismissedByUserStats(4, 1, ((NotificationVisibilityProviderImpl) bubblesManager.mVisibilityProvider).obtain(entry)));
                                        }
                                    }
                                }
                            });
                            bubbleController3.isBubbleConversationOff = true;
                        }
                        if (bubble.mIsBubble) {
                            bubbleController3.setIsBubble(bubble, false);
                        }
                        BubblesManager.C37394 c373942 = bubbleController3.mSysuiProxy;
                        c373942.getClass();
                        c373942.val$sysuiMainExecutor.execute(new BubblesManager$4$$ExternalSyntheticLambda0(c373942, str2, i));
                    }
                    BubblesManager.C37394 c373943 = bubbleController3.mSysuiProxy;
                    BubbleController$$ExternalSyntheticLambda2 bubbleController$$ExternalSyntheticLambda2 = new BubbleController$$ExternalSyntheticLambda2(bubbleController3, bubble, i);
                    c373943.getClass();
                    c373943.val$sysuiMainExecutor.execute(new BubblesManager$$ExternalSyntheticLambda2(c373943, str2, bubbleController$$ExternalSyntheticLambda2));
                }
            }
            BubbleDataRepository bubbleDataRepository2 = bubbleController3.mDataRepository;
            int i4 = bubbleController3.mCurrentUserId;
            bubbleDataRepository2.getClass();
            List transform = BubbleDataRepository.transform(arrayList2);
            BubbleVolatileRepository bubbleVolatileRepository = bubbleDataRepository2.volatileRepository;
            synchronized (bubbleVolatileRepository) {
                ArrayList arrayList3 = new ArrayList();
                Iterator it3 = ((ArrayList) transform).iterator();
                while (it3.hasNext()) {
                    Object next = it3.next();
                    final BubbleEntity bubbleEntity = (BubbleEntity) next;
                    synchronized (bubbleVolatileRepository) {
                        List list2 = (List) bubbleVolatileRepository.entitiesByUser.get(i4);
                        if (list2 == null) {
                            list2 = new ArrayList();
                            bubbleVolatileRepository.entitiesByUser.put(i4, list2);
                        }
                    }
                }
                bubbleVolatileRepository.uncache(arrayList3);
            }
            if (!((ArrayList) transform).isEmpty()) {
                bubbleDataRepository2.persistToDisk();
            }
            BubbleController bubbleController4 = BubbleController.this;
            int i5 = 2;
            if (bubbleController4.mStackView != null) {
                Bubble bubble2 = update.addedBubble;
                BubbleDataRepository bubbleDataRepository3 = bubbleController4.mDataRepository;
                if (bubble2 != null) {
                    int i6 = bubbleController4.mCurrentUserId;
                    bubbleDataRepository3.getClass();
                    bubbleDataRepository3.volatileRepository.addBubbles(i6, BubbleDataRepository.transform(Collections.singletonList(bubble2)));
                    if (!((ArrayList) r3).isEmpty()) {
                        bubbleDataRepository3.persistToDisk();
                    }
                    bubbleController4.mStackView.addBubble(update.addedBubble);
                }
                Bubble bubble3 = update.updatedBubble;
                if (bubble3 != null) {
                    BubbleStackView bubbleStackView5 = bubbleController4.mStackView;
                    bubbleStackView5.animateInFlyoutForBubble(bubble3);
                    bubbleStackView5.requestUpdate();
                    bubbleStackView5.logBubbleEvent(bubble3, 2);
                }
                if (update.orderChanged) {
                    int i7 = bubbleController4.mCurrentUserId;
                    List list3 = update.bubbles;
                    bubbleDataRepository3.getClass();
                    bubbleDataRepository3.volatileRepository.addBubbles(i7, BubbleDataRepository.transform(list3));
                    if (!((ArrayList) r10).isEmpty()) {
                        bubbleDataRepository3.persistToDisk();
                    }
                    BubbleStackView bubbleStackView6 = bubbleController4.mStackView;
                    bubbleStackView6.getClass();
                    final BubbleStackView$$ExternalSyntheticLambda1 bubbleStackView$$ExternalSyntheticLambda1 = new BubbleStackView$$ExternalSyntheticLambda1(bubbleStackView6, list3, i5);
                    if (bubbleStackView6.mIsExpanded || (z = bubbleStackView6.mIsExpansionAnimating)) {
                        bubbleStackView$$ExternalSyntheticLambda1.run();
                        bubbleStackView6.updateBadges(false);
                        bubbleStackView6.updateZOrder();
                    } else if (!z) {
                        List list4 = (List) list3.stream().map(new BubbleStackView$$ExternalSyntheticLambda12()).collect(Collectors.toList());
                        final StackAnimationController stackAnimationController = bubbleStackView6.mStackAnimationController;
                        stackAnimationController.getClass();
                        final StackAnimationController$$ExternalSyntheticLambda4 stackAnimationController$$ExternalSyntheticLambda4 = new StackAnimationController$$ExternalSyntheticLambda4(0, stackAnimationController, list4);
                        int i8 = 0;
                        boolean z4 = false;
                        while (i8 < list4.size()) {
                            final View view = (View) list4.get(i8);
                            if (i8 == stackAnimationController.mLayout.indexOfChild(view)) {
                                stackAnimationController.moveToFinalIndex(view, i8, bubbleStackView$$ExternalSyntheticLambda1);
                                z2 = false;
                                list = list4;
                            } else {
                                if (i8 != 0) {
                                    list = list4;
                                    stackAnimationController.moveToFinalIndex(view, i8, bubbleStackView$$ExternalSyntheticLambda1);
                                } else if (view != null) {
                                    list = list4;
                                    view.setTag(R.id.reorder_animator_tag, view.animate().translationY(stackAnimationController.mStackPosition.y - stackAnimationController.mSwapAnimationOffset).setDuration(300L).withEndAction(new Runnable() { // from class: com.android.wm.shell.bubbles.animation.StackAnimationController$$ExternalSyntheticLambda5
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            StackAnimationController stackAnimationController2 = StackAnimationController.this;
                                            Runnable runnable = stackAnimationController$$ExternalSyntheticLambda4;
                                            View view2 = view;
                                            Runnable runnable2 = bubbleStackView$$ExternalSyntheticLambda1;
                                            stackAnimationController2.getClass();
                                            runnable.run();
                                            stackAnimationController2.moveToFinalIndex(view2, 0, runnable2);
                                        }
                                    }));
                                } else {
                                    list = list4;
                                }
                                z2 = true;
                            }
                            z4 |= z2;
                            i8++;
                            list4 = list;
                        }
                        if (!z4) {
                            stackAnimationController$$ExternalSyntheticLambda4.run();
                        }
                    }
                    bubbleStackView6.updatePointerPosition();
                }
                if (update.selectionChanged) {
                    bubbleController4.mStackView.setSelectedBubble(update.selectedBubble);
                    BubbleViewProvider bubbleViewProvider2 = update.selectedBubble;
                    if (bubbleViewProvider2 != null) {
                        BubblesManager.C37394 c373944 = bubbleController4.mSysuiProxy;
                        bubbleViewProvider2.getKey();
                        c373944.getClass();
                    }
                }
                Bubble bubble4 = update.suppressedBubble;
                if (bubble4 != null && (bubbleStackView2 = bubbleController4.mStackView) != null) {
                    bubbleStackView2.setBubbleSuppressed(bubble4, true);
                }
                Bubble bubble5 = update.unsuppressedBubble;
                if (bubble5 != null && (bubbleStackView = bubbleController4.mStackView) != null) {
                    bubbleStackView.setBubbleSuppressed(bubble5, false);
                }
                bubbleController4.expandIfChanged(update, true);
            }
            BubblesManager.C37394 c373945 = BubbleController.this.mSysuiProxy;
            c373945.getClass();
            c373945.val$sysuiMainExecutor.execute(new BubblesManager$4$$ExternalSyntheticLambda0(c373945, "BubbleData.Listener.applyUpdate", 2));
            BubbleController bubbleController5 = BubbleController.this;
            BubbleStackView bubbleStackView7 = bubbleController5.mStackView;
            if (bubbleStackView7 != null) {
                if (!bubbleController5.mIsStatusBarShade) {
                    bubbleStackView7.setVisibility(4);
                } else if (bubbleController5.hasBubbles()) {
                    bubbleController5.mStackView.setVisibility(0);
                }
                bubbleController5.mStackView.updateContentDescription();
                bubbleController5.mStackView.updateBubblesAcessibillityStates();
            }
            BubblesImpl.CachedState cachedState = BubbleController.this.mImpl.mCachedState;
            synchronized (cachedState) {
                if (update.selectionChanged) {
                    BubbleViewProvider bubbleViewProvider3 = update.selectedBubble;
                    cachedState.mSelectedBubbleKey = bubbleViewProvider3 != null ? bubbleViewProvider3.getKey() : null;
                }
                if (update.expandedChanged) {
                    cachedState.mIsStackExpanded = update.expanded;
                }
                if (update.suppressedSummaryChanged) {
                    String str3 = (String) BubbleController.this.mBubbleData.mSuppressedGroupKeys.get(update.suppressedSummaryGroup);
                    if (str3 != null) {
                        cachedState.mSuppressedGroupToNotifKeys.put(update.suppressedSummaryGroup, str3);
                    } else {
                        cachedState.mSuppressedGroupToNotifKeys.remove(update.suppressedSummaryGroup);
                    }
                } else if (BubbleController.this.isBubbleConversationOff) {
                    Log.d("Bubbles", "clearing mSuppressedGroupToNotifKeys = " + update.suppressedSummaryGroup);
                    cachedState.mSuppressedGroupToNotifKeys.clear();
                    BubbleController.this.isBubbleConversationOff = false;
                }
                cachedState.mTmpBubbles.clear();
                cachedState.mTmpBubbles.addAll(update.bubbles);
                cachedState.mTmpBubbles.addAll(update.overflowBubbles);
                cachedState.mSuppressedBubbleKeys.clear();
                cachedState.mShortcutIdToBubble.clear();
                cachedState.mAppBubbleTaskIds.clear();
                Iterator it4 = cachedState.mTmpBubbles.iterator();
                while (it4.hasNext()) {
                    Bubble bubble6 = (Bubble) it4.next();
                    HashMap hashMap = cachedState.mShortcutIdToBubble;
                    ShortcutInfo shortcutInfo = bubble6.mShortcutInfo;
                    hashMap.put(shortcutInfo != null ? shortcutInfo.getId() : bubble6.mMetadataShortcutId, bubble6);
                    cachedState.updateBubbleSuppressedState(bubble6);
                    if (bubble6.mIsAppBubble) {
                        cachedState.mAppBubbleTaskIds.put(bubble6.mKey, Integer.valueOf(bubble6.getTaskId()));
                    }
                }
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.bubbles.BubbleController$3 */
    public final class C37913 implements TaskStackListenerCallback {
        public C37913() {
        }

        @Override // com.android.wm.shell.common.TaskStackListenerCallback
        public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
            BubbleData bubbleData;
            Bubble next;
            BubbleController bubbleController = BubbleController.this;
            Iterator<Bubble> it = bubbleController.mBubbleData.getBubbles().iterator();
            do {
                boolean hasNext = it.hasNext();
                bubbleData = bubbleController.mBubbleData;
                if (!hasNext) {
                    for (Bubble bubble : bubbleData.getOverflowBubbles()) {
                        if (runningTaskInfo.taskId == bubble.getTaskId()) {
                            bubbleController.promoteBubbleFromOverflow(bubble);
                            bubbleData.setExpanded(true);
                            return;
                        }
                    }
                    return;
                }
                next = it.next();
            } while (runningTaskInfo.taskId != next.getTaskId());
            bubbleData.setSelectedBubble(next);
            bubbleData.setExpanded(true);
        }

        @Override // com.android.wm.shell.common.TaskStackListenerCallback
        public final void onTaskMovedToFront(int i) {
            ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$3$$ExternalSyntheticLambda0(i, 0, this));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.bubbles.BubbleController$4 */
    public final class C37924 extends BroadcastReceiver {
        public C37924() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (BubbleController.this.isStackExpanded()) {
                String action = intent.getAction();
                Log.d("Bubbles", "onReceive: action=" + action);
                String stringExtra = intent.getStringExtra("reason");
                if (("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action) && (ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_RECENTS.equals(stringExtra) || ActivityManagerWrapper.CLOSE_SYSTEM_WINDOWS_REASON_HOME_KEY.equals(stringExtra) || "gestureNav".equals(stringExtra))) || "android.intent.action.SCREEN_OFF".equals(action)) {
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda0(this, 2));
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BubblesImeListener extends PinnedStackListenerForwarder.PinnedTaskListener {
        public /* synthetic */ BubblesImeListener(BubbleController bubbleController, int i) {
            this();
        }

        @Override // com.android.wm.shell.pip.PinnedStackListenerForwarder.PinnedTaskListener
        public final void onImeVisibilityChanged(final boolean z, int i) {
            float f;
            BubbleController bubbleController = BubbleController.this;
            BubblePositioner bubblePositioner = bubbleController.mBubblePositioner;
            bubblePositioner.mImeVisible = z;
            bubblePositioner.mImeHeight = i;
            final BubbleStackView bubbleStackView = bubbleController.mStackView;
            if (bubbleStackView != null) {
                if ((bubbleStackView.mIsExpansionAnimating || bubbleStackView.mIsBubbleSwitchAnimating) && bubbleStackView.mIsExpanded) {
                    bubbleStackView.mExpandedAnimationController.expandFromStack(new Runnable(z) { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            BubbleStackView bubbleStackView2 = BubbleStackView.this;
                            bubbleStackView2.updatePointerPosition();
                            bubbleStackView2.mIsExpansionAnimating = false;
                            bubbleStackView2.updateExpandedView();
                            bubbleStackView2.requestUpdate();
                            bubbleStackView2.mExpandedViewContainer.setVisibility(0);
                            bubbleStackView2.mExpandedViewAnimationController.getClass();
                        }
                    });
                    return;
                }
                if (!bubbleStackView.mIsExpanded && bubbleStackView.getBubbleCount() > 0) {
                    StackAnimationController stackAnimationController = bubbleStackView.mStackAnimationController;
                    float f2 = stackAnimationController.mPositioner.getAllowableStackPositionRegion(stackAnimationController.getBubbleCount()).bottom;
                    PointF pointF = stackAnimationController.mStackPosition;
                    if (z) {
                        float f3 = pointF.y;
                        if (f3 > f2 && stackAnimationController.mPreImeY == -1.4E-45f) {
                            stackAnimationController.mPreImeY = f3;
                            f = f2;
                        }
                        f = -1.4E-45f;
                    } else {
                        f2 = stackAnimationController.mPreImeY;
                        if (f2 != -1.4E-45f) {
                            stackAnimationController.mPreImeY = -1.4E-45f;
                            f = f2;
                        }
                        f = -1.4E-45f;
                    }
                    if (f != -1.4E-45f) {
                        DynamicAnimation.C01912 c01912 = DynamicAnimation.TRANSLATION_Y;
                        SpringForce springForce = stackAnimationController.getSpringForce();
                        springForce.setStiffness(200.0f);
                        stackAnimationController.springFirstBubbleWithStackFollowing(c01912, springForce, 0.0f, f, new Runnable[0]);
                        stackAnimationController.notifyFloatingCoordinatorStackAnimatingTo(pointF.x, f);
                    }
                    if (f == -1.4E-45f) {
                        f = pointF.y;
                    }
                    float f4 = f - bubbleStackView.mStackAnimationController.mStackPosition.y;
                    if (bubbleStackView.mFlyout.getVisibility() == 0) {
                        PhysicsAnimator physicsAnimator = PhysicsAnimator.getInstance(bubbleStackView.mFlyout);
                        physicsAnimator.spring(DynamicAnimation.TRANSLATION_Y, bubbleStackView.mFlyout.getTranslationY() + f4, 0.0f, BubbleStackView.FLYOUT_IME_ANIMATION_SPRING_CONFIG);
                        physicsAnimator.start();
                    }
                }
                if (bubbleStackView.mIsExpanded) {
                    bubbleStackView.mExpandedViewAnimationController.getClass();
                }
            }
        }

        private BubblesImeListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BubblesImpl implements Bubbles {
        public final CachedState mCachedState;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public class CachedState {
            public boolean mIsStackExpanded;
            public String mSelectedBubbleKey;
            public final HashSet mSuppressedBubbleKeys = new HashSet();
            public final HashMap mSuppressedGroupToNotifKeys = new HashMap();
            public final HashMap mShortcutIdToBubble = new HashMap();
            public final HashMap mAppBubbleTaskIds = new HashMap();
            public final ArrayList mTmpBubbles = new ArrayList();

            public CachedState() {
            }

            public final synchronized void updateBubbleSuppressedState(Bubble bubble) {
                if (bubble.showInShade()) {
                    this.mSuppressedBubbleKeys.remove(bubble.mKey);
                } else {
                    this.mSuppressedBubbleKeys.add(bubble.mKey);
                }
            }
        }

        public /* synthetic */ BubblesImpl(BubbleController bubbleController, int i) {
            this();
        }

        /* JADX WARN: Code restructure failed: missing block: B:9:0x001d, code lost:
        
            if (r2.equals(r1.mSuppressedGroupToNotifKeys.get(r3)) != false) goto L12;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean isBubbleNotificationSuppressedFromShade(String str, String str2) {
            boolean z;
            CachedState cachedState = this.mCachedState;
            synchronized (cachedState) {
                if (!cachedState.mSuppressedBubbleKeys.contains(str)) {
                    if (cachedState.mSuppressedGroupToNotifKeys.containsKey(str2)) {
                    }
                    z = false;
                }
                z = true;
            }
            return z;
        }

        private BubblesImpl() {
            this.mCachedState = new CachedState();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IBubblesImpl extends IBubbles$Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public BubbleController mController;
        public final SingleInstanceRemoteListener mListener;

        public IBubblesImpl(BubbleController bubbleController) {
            new Object(this) { // from class: com.android.wm.shell.bubbles.BubbleController.IBubblesImpl.1
            };
            this.mController = bubbleController;
            this.mListener = new SingleInstanceRemoteListener(bubbleController, new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    BubbleController.IBubblesImpl iBubblesImpl = (BubbleController.IBubblesImpl) this;
                    int i = BubbleController.IBubblesImpl.$r8$clinit;
                    iBubblesImpl.getClass();
                    ((BubbleController) obj).isShowingAsBubbleBar();
                }
            }, new BubbleController$IBubblesImpl$$ExternalSyntheticLambda1());
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UserBubbleData {
        public final Map mKeyToShownInShadeMap;

        public /* synthetic */ UserBubbleData(int i) {
            this();
        }

        private UserBubbleData() {
            this.mKeyToShownInShadeMap = new HashMap();
        }
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.wm.shell.bubbles.BubbleController$5] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.wm.shell.bubbles.BubbleController$6] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.wm.shell.bubbles.BubbleController$7] */
    public BubbleController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, BubbleData bubbleData, BubbleStackView.SurfaceSynchronizer surfaceSynchronizer, FloatingContentCoordinator floatingContentCoordinator, BubbleDataRepository bubbleDataRepository, IStatusBarService iStatusBarService, WindowManager windowManager, WindowManagerShellWrapper windowManagerShellWrapper, UserManager userManager, LauncherApps launcherApps, BubbleLogger bubbleLogger, TaskStackListenerImpl taskStackListenerImpl, ShellTaskOrganizer shellTaskOrganizer, BubblePositioner bubblePositioner, DisplayController displayController, Optional<OneHandedController> optional, Optional<DragAndDropController> optional2, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2, TaskViewTransitions taskViewTransitions, SyncTransactionQueue syncTransactionQueue, IWindowManager iWindowManager) {
        int i = 0;
        this.mImpl = new BubblesImpl(this, i);
        this.mContext = context;
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellController = shellController;
        this.mLauncherApps = launcherApps;
        this.mBarService = iStatusBarService == null ? IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar")) : iStatusBarService;
        this.mWindowManager = windowManager;
        this.mWindowManagerShellWrapper = windowManagerShellWrapper;
        this.mUserManager = userManager;
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        this.mDataRepository = bubbleDataRepository;
        this.mLogger = bubbleLogger;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mBackgroundExecutor = shellExecutor2;
        this.mTaskStackListener = taskStackListenerImpl;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mSurfaceSynchronizer = surfaceSynchronizer;
        this.mCurrentUserId = ActivityManager.getCurrentUser();
        this.mBubblePositioner = bubblePositioner;
        this.mBubbleData = bubbleData;
        this.mSavedUserBubbleData = new SparseArray();
        this.mBubbleIconFactory = new BubbleIconFactory(context);
        this.mBubbleBadgeIconFactory = new BubbleBadgeIconFactory(context);
        this.mOrientation = context.getResources().getConfiguration().orientation;
        this.mDisplayController = displayController;
        this.mTaskViewTransitions = taskViewTransitions;
        this.mOneHandedOptional = optional;
        this.mDragAndDropController = optional2;
        this.mSyncQueue = syncTransactionQueue;
        this.mWmService = iWindowManager;
        shellInit.addInitCallback(new BubbleController$$ExternalSyntheticLambda0(this, i), this);
    }

    public static boolean canLaunchInTaskView(Context context, BubbleEntry bubbleEntry) {
        PendingIntent intent = bubbleEntry.getBubbleMetadata() != null ? bubbleEntry.getBubbleMetadata().getIntent() : null;
        if (bubbleEntry.getBubbleMetadata() != null && bubbleEntry.getBubbleMetadata().getShortcutId() != null) {
            return true;
        }
        if (intent != null) {
            return isResizableActivity(intent.getIntent(), getPackageManagerForUser(bubbleEntry.mSbn.getUser().getIdentifier(), context), bubbleEntry.getKey());
        }
        Log.w("Bubbles", "Unable to create bubble -- no intent: " + bubbleEntry.getKey());
        return false;
    }

    public static PackageManager getPackageManagerForUser(int i, Context context) {
        if (i >= 0) {
            try {
                context = context.createPackageContextAsUser(context.getPackageName(), 4, new UserHandle(i));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return context.getPackageManager();
    }

    public static boolean isResizableActivity(Intent intent, PackageManager packageManager, String str) {
        if (intent == null) {
            Log.w("Bubbles", "Unable to send as bubble: " + str + " null intent");
            return false;
        }
        ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(packageManager, 0);
        if (resolveActivityInfo == null) {
            Log.w("Bubbles", "Unable to send as bubble: " + str + " couldn't find activity info for intent: " + intent);
            return false;
        }
        if (ActivityInfo.isResizeableMode(resolveActivityInfo.resizeMode)) {
            return true;
        }
        Log.w("Bubbles", "Unable to send as bubble: " + str + " activity is not resizable for intent: " + intent);
        return false;
    }

    public Bubbles asBubbles() {
        return this.mImpl;
    }

    public final void collapseStack() {
        this.mBubbleData.setExpanded(false);
    }

    public final void ensureBubbleViewsAndWindowCreated() {
        isShowingAsBubbleBar();
        this.mBubblePositioner.mShowingInBubbleBar = false;
        isShowingAsBubbleBar();
        if (this.mStackView == null) {
            BubbleStackView bubbleStackView = new BubbleStackView(this.mContext, this, this.mBubbleData, this.mSurfaceSynchronizer, this.mFloatingContentCoordinator, this.mMainExecutor);
            this.mStackView = bubbleStackView;
            bubbleStackView.onOrientationChanged();
            BubbleController$$ExternalSyntheticLambda4 bubbleController$$ExternalSyntheticLambda4 = this.mExpandListener;
            if (bubbleController$$ExternalSyntheticLambda4 != null) {
                this.mStackView.mExpandListener = bubbleController$$ExternalSyntheticLambda4;
            }
            BubbleStackView bubbleStackView2 = this.mStackView;
            Objects.requireNonNull(this.mSysuiProxy);
            bubbleStackView2.getClass();
        }
        this.mStackView.setDefaultFocusHighlightEnabled(false);
        if (this.mAddedToWindowManager) {
            return;
        }
        isShowingAsBubbleBar();
        isShowingAsBubbleBar();
        if (this.mStackView == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2038, 25165864, -3);
        this.mWmLayoutParams = layoutParams;
        layoutParams.setTrustedOverlay();
        this.mWmLayoutParams.setFitInsetsTypes(0);
        WindowManager.LayoutParams layoutParams2 = this.mWmLayoutParams;
        layoutParams2.softInputMode = 16;
        layoutParams2.token = new Binder();
        this.mWmLayoutParams.setTitle("Bubbles!");
        WindowManager.LayoutParams layoutParams3 = this.mWmLayoutParams;
        Context context = this.mContext;
        layoutParams3.packageName = context.getPackageName();
        WindowManager.LayoutParams layoutParams4 = this.mWmLayoutParams;
        layoutParams4.layoutInDisplayCutoutMode = 3;
        layoutParams4.privateFlags = 16 | layoutParams4.privateFlags;
        SecHideInformationMirroringController secHideInformationMirroringController = this.mHideInformationMirroringController;
        if (secHideInformationMirroringController != null) {
            secHideInformationMirroringController.updateMirroringWindowFlag();
        }
        try {
            this.mAddedToWindowManager = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            context.registerReceiver(this.mBroadcastReceiver, intentFilter, 2);
            BubbleOverflow bubbleOverflow = this.mBubbleData.mOverflow;
            BubbleExpandedView bubbleExpandedView = (BubbleExpandedView) bubbleOverflow.inflater.inflate(R.layout.bubble_expanded_view, (ViewGroup) null, false);
            bubbleOverflow.expandedView = bubbleExpandedView;
            bubbleExpandedView.applyThemeAttrs();
            bubbleOverflow.updateResources();
            BubbleExpandedView bubbleExpandedView2 = bubbleOverflow.expandedView;
            if (bubbleExpandedView2 != null) {
                bubbleExpandedView2.initialize(this, getStackView(), true);
            }
            isShowingAsBubbleBar();
            this.mWindowManager.addView(this.mStackView, this.mWmLayoutParams);
            this.mStackView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda14
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0031, code lost:
                
                    if ((r3.getResources().getConfiguration().orientation == 2) != false) goto L17;
                 */
                @Override // android.view.View.OnApplyWindowInsetsListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    BubbleController bubbleController = BubbleController.this;
                    if (!windowInsets.equals(bubbleController.mWindowInsets)) {
                        if (bubbleController.isStackExpanded() || bubbleController.mIsStatusBarShade) {
                            SemWindowManager semWindowManager = SemWindowManager.getInstance();
                            if (semWindowManager != null ? semWindowManager.isFolded() : false) {
                            }
                        }
                        bubbleController.mWindowInsets = windowInsets;
                        bubbleController.mBubblePositioner.update();
                        bubbleController.mStackView.onDisplaySizeChanged();
                    }
                    return windowInsets;
                }
            });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v11, types: [com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda16, java.lang.Runnable] */
    public final void expandIfChanged(BubbleData.Update update, boolean z) {
        int i;
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView != null && update.expandedChanged && z == update.expanded) {
            if (QpShellRune.NOTI_BUBBLE_STYLE_FLIP) {
                if (bubbleStackView.getDisplay() != null && this.mStackView.getDisplay().getState() == 1 && (i = this.mExpandDeferCount) < 5) {
                    this.mExpandDeferCount = i + 1;
                    this.mMainHandler.postDelayed(new BubbleController$$ExternalSyntheticLambda17(this, update, z), 200L);
                    return;
                }
                this.mExpandDeferCount = 0;
            }
            final BubbleStackView bubbleStackView2 = this.mStackView;
            boolean z2 = bubbleStackView2.mIsExpanded;
            if (z != z2) {
                bubbleStackView2.hideCurrentInputMethod();
                BubblesManager.C37394 c37394 = bubbleStackView2.mBubbleController.mSysuiProxy;
                c37394.getClass();
                c37394.val$sysuiMainExecutor.execute(new BubblesManager$4$$ExternalSyntheticLambda1(c37394, c37394.val$sysUiState, z));
                int i2 = 2;
                if (z2) {
                    bubbleStackView2.setBackgroundColor(0);
                    bubbleStackView2.stopMonitoringSwipeUpGestureInternal();
                    ((HandlerExecutor) bubbleStackView2.mMainExecutor).removeCallbacks(bubbleStackView2.mDelayedAnimation);
                    bubbleStackView2.mIsExpansionAnimating = false;
                    bubbleStackView2.mIsBubbleSwitchAnimating = false;
                    if (bubbleStackView2.isManageEduVisible()) {
                        bubbleStackView2.mManageEduView.hide();
                    }
                    bubbleStackView2.showManageMenu(false);
                    bubbleStackView2.mIsExpanded = false;
                    bubbleStackView2.mIsExpansionAnimating = true;
                    bubbleStackView2.showScrim(false);
                    PhysicsAnimationLayout physicsAnimationLayout = bubbleStackView2.mBubbleContainer;
                    PhysicsAnimationLayout.PhysicsAnimationController physicsAnimationController = physicsAnimationLayout.mController;
                    if (physicsAnimationController != null) {
                        physicsAnimationLayout.cancelAllAnimationsOfProperties((DynamicAnimation.ViewProperty[]) physicsAnimationController.getAnimatedProperties().toArray(new DynamicAnimation.ViewProperty[0]));
                    }
                    PhysicsAnimator.getInstance(bubbleStackView2.mAnimatingOutSurfaceContainer).cancel();
                    bubbleStackView2.mAnimatingOutSurfaceContainer.setScaleX(0.0f);
                    bubbleStackView2.mAnimatingOutSurfaceContainer.setScaleY(0.0f);
                    ExpandedAnimationController expandedAnimationController = bubbleStackView2.mExpandedAnimationController;
                    expandedAnimationController.mPreparingToCollapse = true;
                    StackAnimationController stackAnimationController = bubbleStackView2.mStackAnimationController;
                    PointF pointF = stackAnimationController.mStackPosition;
                    boolean isFirstChildXLeftOfCenter = stackAnimationController.mLayout.isFirstChildXLeftOfCenter(pointF.x);
                    RectF allowableStackPositionRegion = stackAnimationController.mPositioner.getAllowableStackPositionRegion(stackAnimationController.getBubbleCount());
                    pointF.x = isFirstChildXLeftOfCenter ? allowableStackPositionRegion.left : allowableStackPositionRegion.right;
                    BubbleStackView$$ExternalSyntheticLambda3 bubbleStackView$$ExternalSyntheticLambda3 = new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView2, 8);
                    expandedAnimationController.mAnimatingExpand = false;
                    expandedAnimationController.mPreparingToCollapse = false;
                    expandedAnimationController.mAnimatingCollapse = true;
                    expandedAnimationController.mAfterCollapse = bubbleStackView$$ExternalSyntheticLambda3;
                    expandedAnimationController.mCollapsePoint = pointF;
                    expandedAnimationController.startOrUpdatePathAnimation(false);
                    BubbleViewProvider bubbleViewProvider = bubbleStackView2.mExpandedBubble;
                    PointF expandedBubbleXY = bubbleStackView2.mPositioner.getExpandedBubbleXY((bubbleViewProvider == null || !"Overflow".equals(bubbleViewProvider.getKey())) ? bubbleStackView2.mBubbleData.getBubbles().indexOf(bubbleStackView2.mExpandedBubble) : bubbleStackView2.mBubbleData.getBubbles().size(), bubbleStackView2.getState());
                    if (bubbleStackView2.mPositioner.showBubblesVertically()) {
                        bubbleStackView2.mExpandedViewContainerMatrix.setScale(1.0f, 1.0f, bubbleStackView2.mStackOnLeftOrWillBe ? bubbleStackView2.mPositioner.mPositionRect.left + r6 + bubbleStackView2.mExpandedViewPadding : (bubbleStackView2.mPositioner.mPositionRect.right - r6) - bubbleStackView2.mExpandedViewPadding, (bubbleStackView2.mBubbleSize / 2.0f) + expandedBubbleXY.y);
                    } else {
                        AnimatableScaleMatrix animatableScaleMatrix = bubbleStackView2.mExpandedViewContainerMatrix;
                        float f = expandedBubbleXY.x;
                        float f2 = bubbleStackView2.mBubbleSize;
                        animatableScaleMatrix.setScale(1.0f, 1.0f, (f2 / 2.0f) + f, expandedBubbleXY.y + f2 + bubbleStackView2.mExpandedViewPadding);
                    }
                    bubbleStackView2.mExpandedViewAlphaAnimator.reverse();
                    if (bubbleStackView2.mExpandedBubble.getExpandedView() != null) {
                        bubbleStackView2.mExpandedBubble.getExpandedView().setContentVisibility(false);
                    }
                    PhysicsAnimator.getInstance(bubbleStackView2.mExpandedViewContainerMatrix).cancel();
                    PhysicsAnimator physicsAnimator = PhysicsAnimator.getInstance(bubbleStackView2.mExpandedViewContainerMatrix);
                    physicsAnimator.spring(AnimatableScaleMatrix.SCALE_X, 449.99997f, 0.0f, bubbleStackView2.mScaleOutSpringConfig);
                    physicsAnimator.spring(AnimatableScaleMatrix.SCALE_Y, 449.99997f, 0.0f, bubbleStackView2.mScaleOutSpringConfig);
                    physicsAnimator.updateListeners.add(new BubbleStackView$$ExternalSyntheticLambda11(bubbleStackView2, i2));
                    physicsAnimator.withEndActions(new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView2, 9));
                    physicsAnimator.start();
                    bubbleStackView2.showManageMenu(false);
                    bubbleStackView2.logBubbleEvent(bubbleStackView2.mExpandedBubble, 4);
                } else {
                    bubbleStackView2.setBackgroundColor(1711276032);
                    bubbleStackView2.mExpandedViewTemporarilyHidden = false;
                    ((HandlerExecutor) bubbleStackView2.mMainExecutor).removeCallbacks(bubbleStackView2.mDelayedAnimation);
                    bubbleStackView2.mIsExpansionAnimating = false;
                    bubbleStackView2.mIsBubbleSwitchAnimating = false;
                    final boolean showBubblesVertically = bubbleStackView2.mPositioner.showBubblesVertically();
                    bubbleStackView2.mIsExpanded = true;
                    if (bubbleStackView2.isStackEduVisible()) {
                        bubbleStackView2.mStackEduView.hide(true);
                    }
                    bubbleStackView2.mIsExpansionAnimating = true;
                    bubbleStackView2.hideFlyoutImmediate();
                    bubbleStackView2.updateExpandedBubble();
                    bubbleStackView2.showScrim(true);
                    bubbleStackView2.updateZOrder();
                    bubbleStackView2.updateBadges(false);
                    bubbleStackView2.mBubbleContainer.setActiveController(bubbleStackView2.mExpandedAnimationController);
                    bubbleStackView2.updateOverflowVisibility();
                    bubbleStackView2.updatePointerPosition();
                    bubbleStackView2.mExpandedAnimationController.expandFromStack(new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView2, 10));
                    BubbleViewProvider bubbleViewProvider2 = bubbleStackView2.mExpandedBubble;
                    PointF expandedBubbleXY2 = bubbleStackView2.mPositioner.getExpandedBubbleXY((bubbleViewProvider2 == null || !"Overflow".equals(bubbleViewProvider2.getKey())) ? bubbleStackView2.getBubbleIndex(bubbleStackView2.mExpandedBubble) : bubbleStackView2.mBubbleData.getBubbles().size(), bubbleStackView2.getState());
                    BubblePositioner bubblePositioner = bubbleStackView2.mPositioner;
                    float expandedViewY = bubblePositioner.getExpandedViewY(bubbleStackView2.mExpandedBubble, bubblePositioner.showBubblesVertically() ? expandedBubbleXY2.y : expandedBubbleXY2.x);
                    bubbleStackView2.mExpandedViewContainer.setTranslationX(bubbleStackView2.getInitialTranslationX());
                    bubbleStackView2.mExpandedViewContainer.setTranslationY(expandedViewY);
                    bubbleStackView2.mExpandedViewContainer.setAlpha(1.0f);
                    float f3 = showBubblesVertically ? bubbleStackView2.mStackAnimationController.mStackPosition.y : bubbleStackView2.mStackAnimationController.mStackPosition.x;
                    final float f4 = showBubblesVertically ? expandedBubbleXY2.y : expandedBubbleXY2.x;
                    long abs = bubbleStackView2.getWidth() > 0 ? (long) (((Math.abs(f4 - f3) / bubbleStackView2.getWidth()) * 30.0f) + 210.00002f) : 0L;
                    if (showBubblesVertically) {
                        bubbleStackView2.mExpandedViewContainerMatrix.setScale(0.9f, 0.9f, bubbleStackView2.mStackOnLeftOrWillBe ? expandedBubbleXY2.x + bubbleStackView2.mBubbleSize + bubbleStackView2.mExpandedViewPadding : expandedBubbleXY2.x - bubbleStackView2.mExpandedViewPadding, (bubbleStackView2.mBubbleSize / 2.0f) + expandedBubbleXY2.y);
                    } else {
                        AnimatableScaleMatrix animatableScaleMatrix2 = bubbleStackView2.mExpandedViewContainerMatrix;
                        float f5 = expandedBubbleXY2.x;
                        float f6 = bubbleStackView2.mBubbleSize;
                        animatableScaleMatrix2.setScale(0.9f, 0.9f, (f6 / 2.0f) + f5, expandedBubbleXY2.y + f6 + bubbleStackView2.mExpandedViewPadding);
                    }
                    bubbleStackView2.mExpandedViewContainer.setAnimationMatrix(bubbleStackView2.mExpandedViewContainerMatrix);
                    if (bubbleStackView2.mExpandedBubble.getExpandedView() != null) {
                        bubbleStackView2.mExpandedBubble.getExpandedView().setContentAlpha(0.0f);
                        BubbleExpandedView expandedView = bubbleStackView2.mExpandedBubble.getExpandedView();
                        expandedView.mPointerView.setAlpha(0.0f);
                        expandedView.setAlpha(0.0f);
                        bubbleStackView2.mExpandedBubble.getExpandedView().mIsAnimating = true;
                    }
                    ?? r4 = new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda16
                        @Override // java.lang.Runnable
                        public final void run() {
                            final BubbleStackView bubbleStackView3 = BubbleStackView.this;
                            final boolean z3 = showBubblesVertically;
                            final float f7 = f4;
                            bubbleStackView3.mExpandedViewAlphaAnimator.start();
                            bubbleStackView3.mIsExpansionAnimating = true;
                            bubbleStackView3.updateExpandedView();
                            PhysicsAnimator.getInstance(bubbleStackView3.mExpandedViewContainerMatrix).cancel();
                            PhysicsAnimator physicsAnimator2 = PhysicsAnimator.getInstance(bubbleStackView3.mExpandedViewContainerMatrix);
                            physicsAnimator2.spring(AnimatableScaleMatrix.SCALE_X, 499.99997f, 0.0f, bubbleStackView3.mScaleInSpringConfig);
                            physicsAnimator2.spring(AnimatableScaleMatrix.SCALE_Y, 499.99997f, 0.0f, bubbleStackView3.mScaleInSpringConfig);
                            physicsAnimator2.updateListeners.add(new PhysicsAnimator.UpdateListener() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda17
                                @Override // com.android.wm.shell.animation.PhysicsAnimator.UpdateListener
                                public final void onAnimationUpdateForProperty(Object obj) {
                                    BubbleStackView bubbleStackView4 = BubbleStackView.this;
                                    BubbleViewProvider bubbleViewProvider3 = bubbleStackView4.mExpandedBubble;
                                    if (bubbleViewProvider3 == null || bubbleViewProvider3.getIconView$1() == null) {
                                        return;
                                    }
                                    bubbleStackView4.mExpandedViewContainerMatrix.postTranslate((z3 ? bubbleStackView4.mExpandedBubble.getIconView$1().getTranslationY() : bubbleStackView4.mExpandedBubble.getIconView$1().getTranslationX()) - f7, 0.0f);
                                    bubbleStackView4.mExpandedViewContainer.setAnimationMatrix(bubbleStackView4.mExpandedViewContainerMatrix);
                                }
                            });
                            physicsAnimator2.withEndActions(new BubbleStackView$$ExternalSyntheticLambda3(bubbleStackView3, 12));
                            physicsAnimator2.start();
                        }
                    };
                    bubbleStackView2.mDelayedAnimation = r4;
                    ((HandlerExecutor) bubbleStackView2.mMainExecutor).executeDelayed(abs, r4);
                    bubbleStackView2.logBubbleEvent(bubbleStackView2.mExpandedBubble, 3);
                    bubbleStackView2.logBubbleEvent(bubbleStackView2.mExpandedBubble, 15);
                    BubbleController bubbleController = bubbleStackView2.mBubbleController;
                    Consumer consumer = new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda14
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            BubbleStackView bubbleStackView3 = BubbleStackView.this;
                            bubbleStackView3.getClass();
                            if (((Boolean) obj).booleanValue() || !bubbleStackView3.mIsExpanded) {
                                return;
                            }
                            bubbleStackView3.startMonitoringSwipeUpGesture();
                        }
                    };
                    BubblesManager.C37394 c373942 = bubbleController.mSysuiProxy;
                    BubbleController$$ExternalSyntheticLambda2 bubbleController$$ExternalSyntheticLambda2 = new BubbleController$$ExternalSyntheticLambda2(bubbleController, consumer, i2);
                    c373942.getClass();
                    c373942.val$sysuiMainExecutor.execute(new BubblesManager$4$$ExternalSyntheticLambda0(c373942, bubbleController$$ExternalSyntheticLambda2, 4));
                }
                BubbleViewProvider bubbleViewProvider3 = bubbleStackView2.mExpandedBubble;
                boolean z3 = bubbleStackView2.mIsExpanded;
                Bubbles.BubbleExpandListener bubbleExpandListener = bubbleStackView2.mExpandListener;
                if (bubbleExpandListener != null && bubbleViewProvider3 != null) {
                    bubbleExpandListener.onBubbleExpandChanged(bubbleViewProvider3.getKey(), z3);
                }
            }
            BubblesManager.C37394 c373943 = this.mSysuiProxy;
            c373943.getClass();
            c373943.val$sysuiMainExecutor.execute(new BubblesManager$4$$ExternalSyntheticLambda1(c373943, z));
        }
    }

    public final void expandStackAndSelectBubble(BubbleEntry bubbleEntry) {
        if (!this.mIsStatusBarShade) {
            this.mNotifEntryToExpandOnShadeUnlock = bubbleEntry;
            return;
        }
        this.mNotifEntryToExpandOnShadeUnlock = null;
        String key = bubbleEntry.getKey();
        BubbleData bubbleData = this.mBubbleData;
        Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(key);
        if (bubbleInStackWithKey != null) {
            bubbleData.setSelectedBubble(bubbleInStackWithKey);
            bubbleData.setExpanded(true);
            return;
        }
        Bubble overflowBubbleWithKey = bubbleData.getOverflowBubbleWithKey(key);
        if (overflowBubbleWithKey != null) {
            promoteBubbleFromOverflow(overflowBubbleWithKey);
        } else if (bubbleEntry.mRanking.canBubble()) {
            setIsBubble(bubbleEntry, true, true);
        }
    }

    public final ArrayList getBubblesInGroup(String str) {
        ArrayList arrayList = new ArrayList();
        if (str == null) {
            return arrayList;
        }
        for (Bubble bubble : Collections.unmodifiableList(this.mBubbleData.mBubbles)) {
            String str2 = bubble.mGroupKey;
            if (str2 != null && str.equals(str2)) {
                arrayList.add(bubble);
            }
        }
        return arrayList;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    public BubblesImpl.CachedState getImplCachedState() {
        return this.mImpl.mCachedState;
    }

    public BubblePositioner getPositioner() {
        return this.mBubblePositioner;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public BubbleStackView getStackView() {
        return this.mStackView;
    }

    public boolean hasBubbles() {
        if (this.mStackView == null) {
            return false;
        }
        BubbleData bubbleData = this.mBubbleData;
        if (!(!((ArrayList) bubbleData.mBubbles).isEmpty())) {
            if (!(bubbleData.mShowingOverflow && bubbleData.mExpanded)) {
                return false;
            }
        }
        return true;
    }

    public void inflateAndAdd(Bubble bubble, final boolean z, final boolean z2) {
        ensureBubbleViewsAndWindowCreated();
        bubble.setInflateSynchronously(this.mInflateSynchronously);
        bubble.inflate(new BubbleViewInfoTask.Callback() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda3
            /* JADX WARN: Code restructure failed: missing block: B:34:0x0105, code lost:
            
                if (((r3 & 4) != 0) == false) goto L50;
             */
            /* JADX WARN: Code restructure failed: missing block: B:48:0x0129, code lost:
            
                if (r0.mVisibleLocusIds.contains(r9) != false) goto L64;
             */
            @Override // com.android.wm.shell.bubbles.BubbleViewInfoTask.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onBubbleViewsReady(Bubble bubble2) {
                BubbleData bubbleData = BubbleController.this.mBubbleData;
                bubbleData.getClass();
                Log.d("Bubbles", "notificationEntryUpdated: " + bubble2);
                bubbleData.mPendingBubbles.remove(bubble2.mKey);
                Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(bubble2.mKey);
                boolean z3 = (!bubble2.mIsTextChanged) | z;
                List list = bubbleData.mBubbles;
                if (bubbleInStackWithKey == null) {
                    bubble2.mSuppressFlyout = z3;
                    ((BubbleData$$ExternalSyntheticLambda3) bubbleData.mTimeSource).getClass();
                    bubble2.mLastUpdated = System.currentTimeMillis();
                    Log.d("Bubbles", "doAdd: " + bubble2);
                    ArrayList arrayList = (ArrayList) list;
                    arrayList.add(0, bubble2);
                    BubbleData.Update update = bubbleData.mStateChange;
                    update.addedBubble = bubble2;
                    update.orderChanged = arrayList.size() > 1;
                    bubbleData.setSelectedBubbleInternal((BubbleViewProvider) arrayList.get(0));
                    bubbleData.trim();
                } else {
                    bubble2.mSuppressFlyout = z3;
                    boolean z4 = !z3;
                    Log.d("Bubbles", "doUpdate: " + bubble2);
                    bubbleData.mStateChange.updatedBubble = bubble2;
                    if (!bubbleData.mExpanded && z4) {
                        ArrayList arrayList2 = (ArrayList) list;
                        int indexOf = arrayList2.indexOf(bubble2);
                        arrayList2.remove(bubble2);
                        arrayList2.add(0, bubble2);
                        bubbleData.mStateChange.orderChanged = indexOf != 0;
                        bubbleData.setSelectedBubbleInternal((BubbleViewProvider) arrayList2.get(0));
                    }
                }
                if (bubble2.isEnabled(1)) {
                    bubble2.setShouldAutoExpand(false);
                    bubbleData.setSelectedBubbleInternal(bubble2);
                    if (!bubbleData.mExpanded) {
                        bubbleData.setExpandedInternal(true);
                    }
                }
                boolean z5 = bubbleData.mExpanded && bubbleData.mSelectedBubble == bubble2;
                bubble2.setSuppressNotification((!z5 && z2 && bubble2.showInShade()) ? false : true);
                bubble2.setShowDot(!z5);
                LocusId locusId = bubble2.mLocusId;
                if (locusId != null) {
                    ArrayMap arrayMap = bubbleData.mSuppressedBubbles;
                    boolean containsKey = arrayMap.containsKey(locusId);
                    if (containsKey) {
                        int i = bubble2.mFlags;
                        if ((i & 8) != 0) {
                        }
                        arrayMap.remove(locusId);
                        bubbleData.doUnsuppress(bubble2);
                    }
                    if (!containsKey) {
                        int i2 = bubble2.mFlags;
                        if (!((i2 & 8) != 0)) {
                            if ((i2 & 4) != 0) {
                            }
                        }
                        arrayMap.put(locusId, bubble2);
                        bubbleData.doSuppress(bubble2);
                    }
                }
                bubbleData.dispatchPendingChanges();
            }
        }, this.mContext, this, this.mStackView, this.mBubbleIconFactory, this.mBubbleBadgeIconFactory, false);
    }

    public boolean isBubbleNotificationSuppressedFromShade(String str, String str2) {
        BubbleData bubbleData = this.mBubbleData;
        return (str.equals((String) bubbleData.mSuppressedGroupKeys.get(str2)) && bubbleData.isSummarySuppressed(str2)) || (bubbleData.hasAnyBubbleWithKey(str) && !bubbleData.getAnyBubbleWithkey(str).showInShade());
    }

    public final void isShowingAsBubbleBar() {
        if (BUBBLE_BAR_ENABLED) {
            this.mBubblePositioner.getClass();
        }
    }

    public boolean isStackExpanded() {
        return this.mBubbleData.mExpanded;
    }

    public final boolean isSummaryOfBubbles(BubbleEntry bubbleEntry) {
        String groupKey = bubbleEntry.mSbn.getGroupKey();
        ArrayList bubblesInGroup = getBubblesInGroup(groupKey);
        BubbleData bubbleData = this.mBubbleData;
        return ((bubbleData.isSummarySuppressed(groupKey) && ((String) bubbleData.mSuppressedGroupKeys.get(groupKey)).equals(bubbleEntry.getKey())) || bubbleEntry.mSbn.getNotification().isGroupSummary()) && !bubblesInGroup.isEmpty();
    }

    public void onAllBubblesAnimatedOut() {
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView != null) {
            bubbleStackView.setVisibility(4);
            if (this.mAddedToWindowManager) {
                this.mAddedToWindowManager = false;
                ((HandlerExecutor) this.mBackgroundExecutor).execute(new BubbleController$$ExternalSyntheticLambda0(this, 1));
                try {
                    BubbleStackView bubbleStackView2 = this.mStackView;
                    if (bubbleStackView2 != null) {
                        this.mWindowManager.removeView(bubbleStackView2);
                        BubbleOverflow bubbleOverflow = this.mBubbleData.mOverflow;
                        BubbleExpandedView bubbleExpandedView = bubbleOverflow.expandedView;
                        if (bubbleExpandedView != null) {
                            bubbleExpandedView.cleanUpExpandedState();
                        }
                        bubbleOverflow.expandedView = null;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onBubbleMetadataFlagChanged(Bubble bubble) {
        try {
            this.mBarService.onBubbleMetadataFlagChanged(bubble.mKey, bubble.mFlags);
        } catch (RemoteException unused) {
        }
        this.mImpl.mCachedState.updateBubbleSuppressedState(bubble);
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        BubbleExpandedView bubbleExpandedView;
        BubblePositioner bubblePositioner = this.mBubblePositioner;
        if (bubblePositioner != null) {
            bubblePositioner.update();
        }
        if (this.mStackView == null || configuration == null) {
            return;
        }
        int i = configuration.densityDpi;
        int i2 = this.mDensityDpi;
        Rect rect = this.mScreenBounds;
        if (i != i2 || !configuration.windowConfiguration.getBounds().equals(rect)) {
            this.mDensityDpi = configuration.densityDpi;
            rect.set(configuration.windowConfiguration.getBounds());
            BubbleData bubbleData = this.mBubbleData;
            bubbleData.mMaxBubbles = bubbleData.mPositioner.mMaxBubbles;
            if (bubbleData.mExpanded) {
                bubbleData.mNeedsTrimming = true;
            } else {
                bubbleData.trim();
                bubbleData.dispatchPendingChanges();
            }
            Context context = this.mContext;
            this.mBubbleIconFactory = new BubbleIconFactory(context);
            this.mBubbleBadgeIconFactory = new BubbleBadgeIconFactory(context);
            int i3 = configuration.orientation;
            if (i3 == this.mOrientation) {
                this.mStackView.onDisplaySizeChanged();
            } else {
                this.mOrientation = i3;
            }
        }
        float f = configuration.fontScale;
        if (f != this.mFontScale) {
            this.mFontScale = f;
            BubbleStackView bubbleStackView = this.mStackView;
            bubbleStackView.mFlyout.updateFontSize();
            Iterator<Bubble> it = bubbleStackView.mBubbleData.getBubbles().iterator();
            while (it.hasNext()) {
                BubbleExpandedView bubbleExpandedView2 = it.next().mExpandedView;
            }
            BubbleOverflow bubbleOverflow = bubbleStackView.mBubbleOverflow;
            if (bubbleOverflow != null && (bubbleExpandedView = bubbleOverflow.expandedView) != null) {
                bubbleExpandedView.updateFontSize();
            }
        }
        if (configuration.getLayoutDirection() != this.mLayoutDirection) {
            final int layoutDirection = configuration.getLayoutDirection();
            this.mLayoutDirection = layoutDirection;
            BubbleStackView bubbleStackView2 = this.mStackView;
            bubbleStackView2.mFlyout.setLayoutDirection(layoutDirection);
            StackEducationView stackEducationView = bubbleStackView2.mStackEduView;
            if (stackEducationView != null) {
                stackEducationView.setLayoutDirection(layoutDirection);
            }
            ManageEducationView manageEducationView = bubbleStackView2.mManageEduView;
            if (manageEducationView != null) {
                manageEducationView.setLayoutDirection(layoutDirection);
            }
            List<Bubble> bubbles = bubbleStackView2.mBubbleData.getBubbles();
            if (bubbles.isEmpty()) {
                return;
            }
            bubbles.forEach(new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i4 = layoutDirection;
                    BubbleExpandedView bubbleExpandedView3 = ((Bubble) obj).mExpandedView;
                    if (bubbleExpandedView3 != null) {
                        bubbleExpandedView3.setLayoutDirection(i4);
                    }
                }
            });
        }
    }

    public void onEntryUpdated(BubbleEntry bubbleEntry, boolean z, boolean z2) {
        if (z2) {
            boolean z3 = z && canLaunchInTaskView(this.mContext, bubbleEntry);
            BubbleData bubbleData = this.mBubbleData;
            if (!z3 && bubbleData.hasAnyBubbleWithKey(bubbleEntry.getKey())) {
                removeBubble(bubbleEntry.getKey(), 7);
            } else if (z3 && bubbleEntry.isBubble()) {
                updateBubble(bubbleEntry);
            }
            String groupKey = bubbleEntry.mSbn.getGroupKey();
            if (isSummaryOfBubbles(bubbleEntry) && bubbleData.isSummarySuppressed(groupKey)) {
                bubbleData.mSuppressedGroupKeys.remove(groupKey);
                BubbleData.Update update = bubbleData.mStateChange;
                update.suppressedSummaryChanged = true;
                update.suppressedSummaryGroup = groupKey;
                bubbleData.dispatchPendingChanges();
            }
        }
    }

    public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        BubbleData bubbleData = this.mBubbleData;
        ArrayList arrayList = new ArrayList(bubbleData.getOverflowBubbles());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Bubble bubble = (Bubble) arrayList.get(i2);
            ShortcutInfo shortcutInfo = bubble.mShortcutInfo;
            if (Objects.equals(shortcutInfo != null ? shortcutInfo.getId() : bubble.mMetadataShortcutId, notificationChannel.getConversationId()) && bubble.mPackageName.equals(str) && bubble.mUser.getIdentifier() == userHandle.getIdentifier() && (!notificationChannel.canBubble() || notificationChannel.isDeleted())) {
                bubbleData.dismissBubbleWithKey(7, bubble.mKey);
            }
        }
    }

    public void onRankingUpdated(NotificationListenerService.RankingMap rankingMap, HashMap<String, Pair<BubbleEntry, Boolean>> hashMap) {
        SparseArray sparseArray;
        if (this.mTmpRanking == null) {
            this.mTmpRanking = new NotificationListenerService.Ranking();
        }
        for (String str : rankingMap.getOrderedKeys()) {
            Pair<BubbleEntry, Boolean> pair = hashMap.get(str);
            BubbleEntry bubbleEntry = (BubbleEntry) pair.first;
            boolean booleanValue = ((Boolean) pair.second).booleanValue();
            if (bubbleEntry != null) {
                int identifier = bubbleEntry.mSbn.getUser().getIdentifier();
                if (!(identifier == -1 || !((sparseArray = this.mCurrentProfiles) == null || sparseArray.get(identifier) == null))) {
                    return;
                }
            }
            if (bubbleEntry != null && (bubbleEntry.mShouldSuppressNotificationList || bubbleEntry.mRanking.isSuspended())) {
                booleanValue = false;
            }
            rankingMap.getRanking(str, this.mTmpRanking);
            BubbleData bubbleData = this.mBubbleData;
            boolean hasAnyBubbleWithKey = bubbleData.hasAnyBubbleWithKey(str);
            boolean hasBubbleInStackWithKey = bubbleData.hasBubbleInStackWithKey(str);
            if (Build.IS_ENG || Build.IS_USERDEBUG) {
                StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("onRankingUpdated : isActiveOrInOverflow= ", hasAnyBubbleWithKey, " , mTmpRanking.canBubble()= ");
                m49m.append(this.mTmpRanking.canBubble());
                m49m.append(" ,isActive= ");
                m49m.append(hasBubbleInStackWithKey);
                m49m.append(" ,shouldBubbleUp= ");
                m49m.append(booleanValue);
                m49m.append(" ,mTmpRanking.isBubble()= ");
                m49m.append(this.mTmpRanking.isBubble());
                Log.d("Bubbles", m49m.toString());
            }
            if (hasAnyBubbleWithKey && !this.mTmpRanking.canBubble()) {
                bubbleData.dismissBubbleWithKey(4, str);
            } else if (hasAnyBubbleWithKey && !booleanValue) {
                bubbleData.dismissBubbleWithKey(14, str);
            } else if (bubbleEntry != null && this.mTmpRanking.isBubble() && booleanValue && !hasBubbleInStackWithKey) {
                bubbleEntry.setFlagBubble(true);
                onEntryUpdated(bubbleEntry, true, true);
            }
        }
    }

    public void onStatusBarStateChanged(boolean z) {
        BubbleStackView bubbleStackView;
        boolean z2 = this.mIsStatusBarShade != z;
        this.mIsStatusBarShade = z;
        if (!z && z2) {
            collapseStack();
            if (this.mStackView != null) {
                MotionEvent.obtain(0L, 0L, 0, 0.0f, 0.0f, 0);
                BubbleStackView bubbleStackView2 = this.mStackView;
                bubbleStackView2.mStackAnimationController.setStackPosition(bubbleStackView2.mPositioner.getRestingPosition());
                bubbleStackView2.mDismissView.hide();
                this.mStackView.resetDismissAnimator();
            }
        }
        BubbleEntry bubbleEntry = this.mNotifEntryToExpandOnShadeUnlock;
        if (bubbleEntry != null) {
            expandStackAndSelectBubble(bubbleEntry);
        }
        BubbleStackView bubbleStackView3 = this.mStackView;
        if (bubbleStackView3 == null) {
            return;
        }
        if (!this.mIsStatusBarShade) {
            bubbleStackView3.setVisibility(4);
        } else if (hasBubbles() && (bubbleStackView = this.mStackView) != null) {
            bubbleStackView.setVisibility(0);
        }
        BubbleStackView bubbleStackView4 = this.mStackView;
        if (bubbleStackView4 != null) {
            bubbleStackView4.updateContentDescription();
            this.mStackView.updateBubblesAcessibillityStates();
        }
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onThemeChanged() {
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView != null) {
            bubbleStackView.setUpFlyout();
            bubbleStackView.setUpDismissView();
            bubbleStackView.updateOverflow();
            bubbleStackView.updateUserEdu();
            List<Bubble> bubbles = bubbleStackView.mBubbleData.getBubbles();
            if (!bubbles.isEmpty()) {
                bubbles.forEach(new BubbleStackView$$ExternalSyntheticLambda2());
                bubbleStackView.updateExpandedView();
            }
            bubbleStackView.mScrim.setBackgroundDrawable(new ColorDrawable(bubbleStackView.getResources().getColor(android.R.color.system_neutral1_1000)));
            bubbleStackView.mManageMenuScrim.setBackgroundDrawable(new ColorDrawable(bubbleStackView.getResources().getColor(android.R.color.system_neutral1_1000)));
        }
        Context context = this.mContext;
        this.mBubbleIconFactory = new BubbleIconFactory(context);
        this.mBubbleBadgeIconFactory = new BubbleBadgeIconFactory(context);
        BubbleData bubbleData = this.mBubbleData;
        Iterator<Bubble> it = bubbleData.getBubbles().iterator();
        while (it.hasNext()) {
            it.next().inflate(null, this.mContext, this, this.mStackView, this.mBubbleIconFactory, this.mBubbleBadgeIconFactory, false);
        }
        Iterator<Bubble> it2 = bubbleData.getOverflowBubbles().iterator();
        while (it2.hasNext()) {
            it2.next().inflate(null, this.mContext, this, this.mStackView, this.mBubbleIconFactory, this.mBubbleBadgeIconFactory, false);
        }
    }

    public void onUserChanged(int i) {
        int i2 = this.mCurrentUserId;
        SparseArray sparseArray = this.mSavedUserBubbleData;
        sparseArray.remove(i2);
        int i3 = 0;
        UserBubbleData userBubbleData = new UserBubbleData(i3);
        BubbleData bubbleData = this.mBubbleData;
        for (Bubble bubble : bubbleData.getBubbles()) {
            String str = bubble.mKey;
            boolean showInShade = bubble.showInShade();
            ((HashMap) userBubbleData.mKeyToShownInShadeMap).put(str, Boolean.valueOf(showInShade));
        }
        sparseArray.put(i2, userBubbleData);
        this.mCurrentUserId = i;
        bubbleData.dismissAll(8);
        while (true) {
            ArrayList arrayList = (ArrayList) bubbleData.mOverflowBubbles;
            if (arrayList.isEmpty()) {
                break;
            } else {
                bubbleData.doRemove(8, ((Bubble) arrayList.get(0)).mKey);
            }
        }
        bubbleData.dispatchPendingChanges();
        this.mOverflowDataLoadNeeded = true;
        UserBubbleData userBubbleData2 = (UserBubbleData) sparseArray.get(i);
        if (userBubbleData2 != null) {
            BubblesManager.C37394 c37394 = this.mSysuiProxy;
            Set keySet = ((HashMap) userBubbleData2.mKeyToShownInShadeMap).keySet();
            BubbleController$$ExternalSyntheticLambda2 bubbleController$$ExternalSyntheticLambda2 = new BubbleController$$ExternalSyntheticLambda2(this, userBubbleData2, i3);
            c37394.getClass();
            c37394.val$sysuiMainExecutor.execute(new BubblesManager$$ExternalSyntheticLambda2(c37394, keySet, 1, bubbleController$$ExternalSyntheticLambda2));
            sparseArray.remove(i);
        }
        bubbleData.mCurrentUserId = i;
    }

    public final void promoteBubbleFromOverflow(Bubble bubble) {
        this.mLogger.log(bubble, BubbleLogger.Event.BUBBLE_OVERFLOW_REMOVE_BACK_TO_STACK);
        bubble.setInflateSynchronously(this.mInflateSynchronously);
        bubble.setShouldAutoExpand(true);
        bubble.mLastAccessed = System.currentTimeMillis();
        bubble.setSuppressNotification(true);
        bubble.setShowDot(false);
        setIsBubble(bubble, true);
    }

    public void removeBubble(String str, int i) {
        BubbleData bubbleData = this.mBubbleData;
        if (bubbleData.hasAnyBubbleWithKey(str)) {
            bubbleData.dismissBubbleWithKey(i, str);
        }
    }

    public void setExpandListener(Bubbles.BubbleExpandListener bubbleExpandListener) {
        BubbleController$$ExternalSyntheticLambda4 bubbleController$$ExternalSyntheticLambda4 = new BubbleController$$ExternalSyntheticLambda4(bubbleExpandListener);
        this.mExpandListener = bubbleController$$ExternalSyntheticLambda4;
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView != null) {
            bubbleStackView.mExpandListener = bubbleController$$ExternalSyntheticLambda4;
        }
    }

    public void setInflateSynchronously(boolean z) {
        this.mInflateSynchronously = z;
    }

    public final void setIsBubble(BubbleEntry bubbleEntry, boolean z, boolean z2) {
        Objects.requireNonNull(bubbleEntry);
        bubbleEntry.setFlagBubble(z);
        BubblesManager.C37394 c37394 = this.mSysuiProxy;
        String key = bubbleEntry.getKey();
        c37394.getClass();
        c37394.val$sysuiMainExecutor.execute(new BubblesManager$4$$ExternalSyntheticLambda0(c37394, key, 1));
        try {
            this.mBarService.onNotificationBubbleChanged(bubbleEntry.getKey(), z, z2 ? 3 : 0);
        } catch (RemoteException unused) {
        }
    }

    public void updateBubble(BubbleEntry bubbleEntry) {
        SparseArray sparseArray;
        int userId = bubbleEntry.mSbn.getUserId();
        int i = 0;
        if (userId == -1 || !((sparseArray = this.mCurrentProfiles) == null || sparseArray.get(userId) == null)) {
            updateBubble(bubbleEntry, false, true);
        } else {
            ((HashMap) ((UserBubbleData) this.mSavedUserBubbleData.get(userId, new UserBubbleData(i))).mKeyToShownInShadeMap).put(bubbleEntry.getKey(), Boolean.TRUE);
        }
    }

    public final void updateNotNotifyingEntry(Bubble bubble, BubbleEntry bubbleEntry, boolean z) {
        boolean showInShade = bubble.showInShade();
        boolean z2 = isStackExpanded() && bubble.equals(this.mBubbleData.mSelectedBubble);
        bubble.setEntry(bubbleEntry);
        bubble.setSuppressNotification((!z2 && z && bubble.showInShade()) ? false : true);
        bubble.setShowDot(!z2);
        if (showInShade != bubble.showInShade()) {
            this.mImpl.mCachedState.updateBubbleSuppressedState(bubble);
        }
    }

    public final void updateWindowFlagsForBackpress(boolean z) {
        BubbleStackView bubbleStackView = this.mStackView;
        if (bubbleStackView == null || !this.mAddedToWindowManager) {
            return;
        }
        WindowManager.LayoutParams layoutParams = this.mWmLayoutParams;
        layoutParams.flags = (z ? 0 : 40) | 16777216;
        this.mWindowManager.updateViewLayout(bubbleStackView, layoutParams);
    }

    public final void setIsBubble(final Bubble bubble, final boolean z) {
        Objects.requireNonNull(bubble);
        bubble.mIsBubble = z;
        BubblesManager.C37394 c37394 = this.mSysuiProxy;
        Consumer consumer = new Consumer() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final BubbleController bubbleController = BubbleController.this;
                final boolean z2 = z;
                final Bubble bubble2 = bubble;
                final BubbleEntry bubbleEntry = (BubbleEntry) obj;
                bubbleController.getClass();
                ((HandlerExecutor) bubbleController.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        BubbleController bubbleController2 = bubbleController;
                        BubbleEntry bubbleEntry2 = bubbleEntry;
                        boolean z3 = z2;
                        Bubble bubble3 = bubble2;
                        bubbleController2.getClass();
                        if (bubbleEntry2 != null) {
                            bubbleController2.setIsBubble(bubbleEntry2, z3, bubble3.isEnabled(1));
                        } else if (z3) {
                            Bubble orCreateBubble = bubbleController2.mBubbleData.getOrCreateBubble(bubble3, null);
                            bubbleController2.inflateAndAdd(orCreateBubble, orCreateBubble.isEnabled(1), !orCreateBubble.isEnabled(1));
                        }
                    }
                });
            }
        };
        c37394.getClass();
        c37394.val$sysuiMainExecutor.execute(new BubblesManager$$ExternalSyntheticLambda2(c37394, bubble.mKey, consumer));
    }

    public void updateBubble(BubbleEntry bubbleEntry, boolean z, boolean z2) {
        BubblesManager.C37394 c37394 = this.mSysuiProxy;
        String key = bubbleEntry.getKey();
        c37394.getClass();
        c37394.val$sysuiMainExecutor.execute(new BubblesManager$4$$ExternalSyntheticLambda0(c37394, key, 0));
        boolean z3 = (bubbleEntry.mRanking.canBubble() || bubbleEntry.getBubbleMetadata() == null || bubbleEntry.getBubbleMetadata().getAutoExpandBubble()) ? false : true;
        BubbleData bubbleData = this.mBubbleData;
        if (z3 && bubbleData.hasOverflowBubbleWithKey(bubbleEntry.getKey())) {
            Bubble overflowBubbleWithKey = bubbleData.getOverflowBubbleWithKey(bubbleEntry.getKey());
            if (bubbleEntry.isBubble()) {
                bubbleEntry.setFlagBubble(false);
            }
            updateNotNotifyingEntry(overflowBubbleWithKey, bubbleEntry, z2);
            return;
        }
        if (bubbleData.hasAnyBubbleWithKey(bubbleEntry.getKey()) && z3) {
            Bubble anyBubbleWithkey = bubbleData.getAnyBubbleWithkey(bubbleEntry.getKey());
            if (anyBubbleWithkey != null) {
                updateNotNotifyingEntry(anyBubbleWithkey, bubbleEntry, z2);
                return;
            }
            return;
        }
        if (bubbleData.mSuppressedBubbles.get(bubbleEntry.mSbn.getNotification().getLocusId()) != null) {
            Bubble suppressedBubbleWithKey = bubbleData.getSuppressedBubbleWithKey(bubbleEntry.getKey());
            if (suppressedBubbleWithKey != null) {
                updateNotNotifyingEntry(suppressedBubbleWithKey, bubbleEntry, z2);
                return;
            }
            return;
        }
        Bubble orCreateBubble = bubbleData.getOrCreateBubble(null, bubbleEntry);
        if (bubbleEntry.mShouldSuppressNotificationList) {
            if (orCreateBubble.isEnabled(1)) {
                orCreateBubble.setShouldAutoExpand(false);
            }
            this.mImpl.mCachedState.updateBubbleSuppressedState(orCreateBubble);
            return;
        }
        inflateAndAdd(orCreateBubble, z, z2);
    }
}
