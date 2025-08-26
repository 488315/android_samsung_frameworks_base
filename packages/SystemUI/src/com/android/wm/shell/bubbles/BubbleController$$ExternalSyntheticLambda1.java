package com.android.wm.shell.bubbles;

import android.app.ActivityManager;
import android.content.LocusId;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.content.pm.UserInfo;
import android.graphics.Rect;
import android.os.UserHandle;
import android.util.SparseArray;
import android.view.View;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController.BubblesImeListener;
import com.android.wm.shell.bubbles.BubbleController.IBubblesImpl;
import com.android.wm.shell.bubbles.animation.ExpandedAnimationController;
import com.android.wm.shell.bubbles.animation.StackAnimationController;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$$ExternalSyntheticLambda0;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.taskview.TaskView;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt__IterablesKt;

/* loaded from: classes3.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BubbleController f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda1(BubbleController bubbleController, int i) {
        this.$r8$classId = i;
        this.f$0 = bubbleController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean zRemoveIf;
        int i = this.$r8$classId;
        final BubbleController bubbleController = this.f$0;
        switch (i) {
            case 0:
                bubbleController.mBubbleViewCallback = bubbleController.mBubbleStackViewCallback;
                BubbleData bubbleData = bubbleController.mBubbleData;
                bubbleData.mListener = bubbleController.mBubbleDataListener;
                bubbleData.mBubbleMetadataFlagListener = new BubbleController$$ExternalSyntheticLambda5(bubbleController);
                bubbleController.mDataRepository.bubbleMetadataFlagListener = new BubbleController$$ExternalSyntheticLambda5(bubbleController);
                bubbleData.mCancelledListener = new BubbleController$$ExternalSyntheticLambda5(bubbleController);
                BubbleController.BubblesImeListener bubblesImeListener = bubbleController.new BubblesImeListener(bubbleController.mDisplayController, bubbleController.mContext.getDisplayId());
                bubbleController.mDisplayInsetsController.addInsetsChangedListener(bubbleController.mContext.getDisplayId(), bubblesImeListener);
                bubbleController.mDisplayImeController.addPositionProcessor(bubblesImeListener);
                bubbleController.mBubbleData.mCurrentUserId = bubbleController.mCurrentUserId;
                ShellTaskOrganizer shellTaskOrganizer = bubbleController.mTaskOrganizer;
                ShellTaskOrganizer.LocusIdListener locusIdListener = new ShellTaskOrganizer.LocusIdListener() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda7
                    @Override // com.android.wm.shell.ShellTaskOrganizer.LocusIdListener
                    public final void onVisibilityChanged(int i2, LocusId locusId, boolean z) {
                        BubbleData bubbleData2 = bubbleController.mBubbleData;
                        bubbleData2.getClass();
                        if (locusId == null) {
                            return;
                        }
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 5004922702703375966L, 28, String.valueOf(locusId.getId()), Boolean.valueOf(z), Long.valueOf(i2));
                        }
                        Bubble bubbleWithPredicate = BubbleData.getBubbleWithPredicate(bubbleData2.mBubbles, new BubbleData$$ExternalSyntheticLambda7(locusId, 1));
                        if (!z || (bubbleWithPredicate != null && bubbleWithPredicate.getTaskId() == i2)) {
                            bubbleData2.mVisibleLocusIds.remove(locusId);
                        } else {
                            bubbleData2.mVisibleLocusIds.add(locusId);
                        }
                        if (bubbleWithPredicate == null && (bubbleWithPredicate = (Bubble) bubbleData2.mSuppressedBubbles.get(locusId)) == null) {
                            return;
                        }
                        boolean z2 = bubbleData2.mSuppressedBubbles.get(locusId) != null;
                        if (z && !z2 && (bubbleWithPredicate.mFlags & 4) != 0 && i2 != bubbleWithPredicate.getTaskId()) {
                            bubbleData2.mSuppressedBubbles.put(locusId, bubbleWithPredicate);
                            bubbleData2.doSuppress(bubbleWithPredicate);
                            bubbleData2.dispatchPendingChanges();
                        } else {
                            if (z) {
                                return;
                            }
                            Bubble bubble = (Bubble) bubbleData2.mSuppressedBubbles.remove(locusId);
                            if (bubble != null) {
                                bubbleData2.doUnsuppress(bubble);
                            }
                            bubbleData2.dispatchPendingChanges();
                        }
                    }
                };
                synchronized (shellTaskOrganizer.mLock) {
                    try {
                        shellTaskOrganizer.mLocusIdListeners.add(locusIdListener);
                        zRemoveIf = false;
                        for (int i2 = 0; i2 < shellTaskOrganizer.mVisibleTasksWithLocusId.size(); i2++) {
                            locusIdListener.onVisibilityChanged(shellTaskOrganizer.mVisibleTasksWithLocusId.keyAt(i2), (LocusId) shellTaskOrganizer.mVisibleTasksWithLocusId.valueAt(i2), true);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                bubbleController.mLauncherApps.registerCallback(new LauncherApps.Callback() { // from class: com.android.wm.shell.bubbles.BubbleController.3
                    public AnonymousClass3() {
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackageRemoved(String str, UserHandle userHandle) {
                        BubbleData bubbleData2 = BubbleController.this.mBubbleData;
                        bubbleData2.getClass();
                        BubbleData$$ExternalSyntheticLambda1 bubbleData$$ExternalSyntheticLambda1 = new BubbleData$$ExternalSyntheticLambda1(str, 1);
                        BubbleData$$ExternalSyntheticLambda9 bubbleData$$ExternalSyntheticLambda9 = new BubbleData$$ExternalSyntheticLambda9(bubbleData2, 1);
                        BubbleData.performActionOnBubblesMatching(Collections.unmodifiableList(bubbleData2.mBubbles), bubbleData$$ExternalSyntheticLambda1, bubbleData$$ExternalSyntheticLambda9);
                        BubbleData.performActionOnBubblesMatching(bubbleData2.getOverflowBubbles(), bubbleData$$ExternalSyntheticLambda1, bubbleData$$ExternalSyntheticLambda9);
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackagesUnavailable(String[] strArr, UserHandle userHandle, boolean z) {
                        for (String str : strArr) {
                            BubbleData bubbleData2 = BubbleController.this.mBubbleData;
                            bubbleData2.getClass();
                            BubbleData$$ExternalSyntheticLambda1 bubbleData$$ExternalSyntheticLambda1 = new BubbleData$$ExternalSyntheticLambda1(str, 1);
                            BubbleData$$ExternalSyntheticLambda9 bubbleData$$ExternalSyntheticLambda9 = new BubbleData$$ExternalSyntheticLambda9(bubbleData2, 1);
                            BubbleData.performActionOnBubblesMatching(Collections.unmodifiableList(bubbleData2.mBubbles), bubbleData$$ExternalSyntheticLambda1, bubbleData$$ExternalSyntheticLambda9);
                            BubbleData.performActionOnBubblesMatching(bubbleData2.getOverflowBubbles(), bubbleData$$ExternalSyntheticLambda1, bubbleData$$ExternalSyntheticLambda9);
                        }
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onShortcutsChanged(final String str, List list, UserHandle userHandle) {
                        super.onShortcutsChanged(str, list, userHandle);
                        BubbleData bubbleData2 = BubbleController.this.mBubbleData;
                        bubbleData2.getClass();
                        final HashSet hashSet = new HashSet();
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            hashSet.add(((ShortcutInfo) it.next()).getId());
                        }
                        Predicate predicate = new Predicate() { // from class: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda16
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                ShortcutInfo shortcutInfo;
                                String str2 = str;
                                Set set = hashSet;
                                Bubble bubble = (Bubble) obj;
                                Comparator comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                                boolean zEquals = str2.equals(bubble.mPackageName);
                                boolean zHasMetadataShortcutId = bubble.hasMetadataShortcutId();
                                if (zEquals && zHasMetadataShortcutId) {
                                    boolean z = bubble.hasMetadataShortcutId() && (shortcutInfo = bubble.mShortcutInfo) != null && shortcutInfo.isEnabled() && set.contains(bubble.mShortcutInfo.getId());
                                    if (zEquals && !z) {
                                        return true;
                                    }
                                }
                                return false;
                            }
                        };
                        BubbleData$$ExternalSyntheticLambda9 bubbleData$$ExternalSyntheticLambda9 = new BubbleData$$ExternalSyntheticLambda9(bubbleData2, 2);
                        BubbleData.performActionOnBubblesMatching(Collections.unmodifiableList(bubbleData2.mBubbles), predicate, bubbleData$$ExternalSyntheticLambda9);
                        BubbleData.performActionOnBubblesMatching(bubbleData2.getOverflowBubbles(), predicate, bubbleData$$ExternalSyntheticLambda9);
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackageAdded(String str, UserHandle userHandle) {
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackageChanged(String str, UserHandle userHandle) {
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackagesAvailable(String[] strArr, UserHandle userHandle, boolean z) {
                    }
                }, bubbleController.mMainHandler);
                bubbleController.mTransitions.registerObserver(new BubblesTransitionObserver(bubbleController, bubbleController.mBubbleData));
                bubbleController.mTaskStackListener.addListener(new TaskStackListenerCallback() { // from class: com.android.wm.shell.bubbles.BubbleController.4
                    public AnonymousClass4() {
                    }

                    @Override // com.android.wm.shell.common.TaskStackListenerCallback
                    public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2) {
                        final int i3 = runningTaskInfo.taskId;
                        BubbleController bubbleController2 = BubbleController.this;
                        final int i4 = 0;
                        Bubble bubbleWithPredicate = BubbleData.getBubbleWithPredicate(bubbleController2.mBubbleData.mBubbles, new Predicate() { // from class: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda14
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i5 = i4;
                                int i6 = i3;
                                Bubble bubble = (Bubble) obj;
                                switch (i5) {
                                    case 0:
                                        Comparator comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                                        if (bubble.getTaskId() == i6) {
                                            break;
                                        }
                                        break;
                                    default:
                                        Comparator comparator2 = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                                        if (bubble.getTaskId() == i6) {
                                            break;
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                        BubbleData bubbleData2 = bubbleController2.mBubbleData;
                        if (bubbleWithPredicate != null) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -6884963536522955080L, 1, Long.valueOf(i3), String.valueOf(bubbleWithPredicate.mKey));
                            }
                            bubbleData2.setSelectedBubbleAndExpandStack(bubbleWithPredicate);
                            return;
                        }
                        final int i5 = 1;
                        Bubble bubbleWithPredicate2 = BubbleData.getBubbleWithPredicate(bubbleData2.mOverflowBubbles, new Predicate() { // from class: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda14
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                int i52 = i5;
                                int i6 = i3;
                                Bubble bubble = (Bubble) obj;
                                switch (i52) {
                                    case 0:
                                        Comparator comparator = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                                        if (bubble.getTaskId() == i6) {
                                            break;
                                        }
                                        break;
                                    default:
                                        Comparator comparator2 = BubbleData.BUBBLES_BY_SORT_KEY_DESCENDING;
                                        if (bubble.getTaskId() == i6) {
                                            break;
                                        }
                                        break;
                                }
                                return true;
                            }
                        });
                        if (bubbleWithPredicate2 != null) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 7156592394091969590L, 1, Long.valueOf(i3), String.valueOf(bubbleWithPredicate2.mKey));
                            }
                            bubbleController2.promoteBubbleFromOverflow(bubbleWithPredicate2);
                            bubbleData2.setExpanded(true);
                        }
                    }
                });
                bubbleController.mDisplayController.addDisplayChangingController(new DisplayChangeController.OnDisplayChangingListener() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda8
                    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
                    public final void onDisplayChange(int i3, int i4, int i5, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
                        BubbleStackView bubbleStackView;
                        BubbleController bubbleController2 = bubbleController;
                        Rect rect = new Rect();
                        if (displayAreaInfo != null) {
                            rect = displayAreaInfo.configuration.windowConfiguration.getBounds();
                        }
                        if ((i4 == i5 && rect.equals(bubbleController2.mScreenBounds)) || (bubbleStackView = bubbleController2.mStackView) == null) {
                            return;
                        }
                        bubbleStackView.onOrientationChanged();
                    }
                });
                bubbleController.mOneHandedOptional.ifPresent(new BubbleController$$ExternalSyntheticLambda9(bubbleController, 0));
                bubbleController.mDragAndDropController.mListeners.add(new DragAndDropController.DragAndDropListener() { // from class: com.android.wm.shell.bubbles.BubbleController.5
                    public AnonymousClass5() {
                    }

                    @Override // com.android.wm.shell.draganddrop.DragAndDropController.DragAndDropListener
                    public final void onDragStarted() {
                        BubbleController.this.collapseStack();
                    }
                });
                List aliveUsers = bubbleController.mUserManager.getAliveUsers();
                BubbleDataRepository bubbleDataRepository = bubbleController.mDataRepository;
                bubbleDataRepository.getClass();
                List list = aliveUsers;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
                }
                BubbleVolatileRepository bubbleVolatileRepository = bubbleDataRepository.volatileRepository;
                synchronized (bubbleVolatileRepository) {
                    int size = bubbleVolatileRepository.entitiesByUser.size();
                    int i3 = 0;
                    while (true) {
                        if (i3 < size) {
                            int iKeyAt = bubbleVolatileRepository.entitiesByUser.keyAt(i3);
                            if (!arrayList.contains(Integer.valueOf(iKeyAt))) {
                                bubbleVolatileRepository.entitiesByUser.remove(iKeyAt);
                                zRemoveIf = true;
                            } else if (bubbleVolatileRepository.entitiesByUser.get(iKeyAt) != null) {
                                zRemoveIf = ((List) bubbleVolatileRepository.entitiesByUser.get(iKeyAt)).removeIf(new BubbleVolatileRepositoryKt$sam$java_util_function_Predicate$0(new BubbleVolatileRepository$$ExternalSyntheticLambda0(arrayList, 2)));
                            } else {
                                i3++;
                            }
                        }
                    }
                }
                if (zRemoveIf) {
                    BubbleDataRepository.persistToDisk$default(bubbleDataRepository);
                }
                SparseArray sparseArray = new SparseArray();
                for (UserInfo userInfo : bubbleController.mUserManager.getProfiles(bubbleController.mCurrentUserId)) {
                    sparseArray.put(userInfo.id, userInfo);
                }
                bubbleController.mCurrentProfiles = sparseArray;
                bubbleController.mShellController.addConfigurationChangeListener(bubbleController);
                bubbleController.mShellController.addExternalInterface("com.android.wm.shell.bubbles.IBubbles", new Supplier() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda10
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        BubbleController bubbleController2 = bubbleController;
                        bubbleController2.getClass();
                        return bubbleController2.new IBubblesImpl(bubbleController2);
                    }
                }, bubbleController);
                bubbleController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda11
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        BubbleController bubbleController2 = bubbleController;
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        printWriter.print(str);
                        printWriter.println("BubbleController state:");
                        printWriter.print(str);
                        printWriter.println("  currentUserId= " + bubbleController2.mCurrentUserId);
                        printWriter.print(str);
                        printWriter.println("  isStatusBarShade= " + bubbleController2.mIsStatusBarShade);
                        printWriter.print(str);
                        printWriter.println("  isShowingAsBubbleBar= false");
                        printWriter.print(str);
                        printWriter.println("  isImeVisible= " + bubbleController2.mBubblePositioner.mImeVisible);
                        printWriter.println();
                        BubbleData bubbleData2 = bubbleController2.mBubbleData;
                        bubbleData2.getClass();
                        printWriter.println("BubbleData state:");
                        printWriter.print("  selected: ");
                        BubbleViewProvider bubbleViewProvider = bubbleData2.mSelectedBubble;
                        printWriter.println(bubbleViewProvider != null ? bubbleViewProvider.getKey() : null);
                        printWriter.print("  expanded: ");
                        printWriter.println(bubbleData2.mExpanded);
                        printWriter.print("Stack bubble count: ");
                        printWriter.println(((ArrayList) bubbleData2.mBubbles).size());
                        ArrayList arrayList2 = (ArrayList) bubbleData2.mBubbles;
                        int size2 = arrayList2.size();
                        int i4 = 0;
                        while (i4 < size2) {
                            Object obj3 = arrayList2.get(i4);
                            i4++;
                            ((Bubble) obj3).dump(printWriter);
                        }
                        printWriter.print("Overflow bubble count: ");
                        printWriter.println(((ArrayList) bubbleData2.mOverflowBubbles).size());
                        ArrayList arrayList3 = (ArrayList) bubbleData2.mOverflowBubbles;
                        int size3 = arrayList3.size();
                        int i5 = 0;
                        while (i5 < size3) {
                            Object obj4 = arrayList3.get(i5);
                            i5++;
                            ((Bubble) obj4).dump(printWriter);
                        }
                        printWriter.print("SummaryKeys: ");
                        printWriter.println(bubbleData2.mSuppressedGroupKeys.size());
                        Iterator it2 = bubbleData2.mSuppressedGroupKeys.keySet().iterator();
                        while (it2.hasNext()) {
                            ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "     suppressing: ", (String) it2.next());
                        }
                        printWriter.println();
                        BubbleStackView bubbleStackView = bubbleController2.mStackView;
                        if (bubbleStackView != null) {
                            printWriter.println("Stack view state:");
                            ArrayList arrayList4 = new ArrayList();
                            for (int i6 = 0; i6 < bubbleStackView.getBubbleCount(); i6++) {
                                View childAt = bubbleStackView.mBubbleContainer.getChildAt(i6);
                                if (childAt instanceof BadgedImageView) {
                                    BubbleViewProvider bubbleViewProvider2 = ((BadgedImageView) childAt).mBubble;
                                    arrayList4.add(bubbleStackView.mBubbleData.getBubbleInStackWithKey(bubbleViewProvider2 != null ? bubbleViewProvider2.getKey() : null));
                                }
                            }
                            BubbleViewProvider expandedBubble = bubbleStackView.getExpandedBubble();
                            StringBuilder sb = new StringBuilder();
                            for (int i7 = 0; i7 < arrayList4.size(); i7++) {
                                Bubble bubble = (Bubble) arrayList4.get(i7);
                                if (bubble == null) {
                                    sb.append("   <null> !!!!!");
                                } else {
                                    sb.append(String.format("%s Bubble{act=%12d, showInShade=%d, key=%s}", (expandedBubble == null || "Overflow".equals(expandedBubble.getKey()) || bubble != expandedBubble) ? "  " : "=>", Long.valueOf(Math.max(bubble.mLastUpdated, bubble.mLastAccessed)), Integer.valueOf(bubble.showInShade() ? 1 : 0), bubble.mKey));
                                }
                                if (i7 != arrayList4.size() - 1) {
                                    sb.append("\n");
                                }
                            }
                            String string = sb.toString();
                            printWriter.println("  bubbles on screen:       ");
                            printWriter.println(string);
                            printWriter.print("  gestureInProgress:       ");
                            printWriter.println(bubbleStackView.mIsGestureInProgress);
                            printWriter.print("  showingDismiss:          ");
                            printWriter.println(bubbleStackView.mDismissView.isShowing);
                            printWriter.print("  isExpansionAnimating:    ");
                            printWriter.println(bubbleStackView.mIsExpansionAnimating);
                            printWriter.print("  expandedContainerVis:    ");
                            printWriter.println(bubbleStackView.mExpandedViewContainer.getVisibility());
                            printWriter.print("  expandedContainerAlpha:  ");
                            printWriter.println(bubbleStackView.mExpandedViewContainer.getAlpha());
                            printWriter.print("  expandedContainerMatrix: ");
                            printWriter.println(bubbleStackView.mExpandedViewContainer.getAnimationMatrix());
                            printWriter.print("  stack visibility :       ");
                            printWriter.println(bubbleStackView.getVisibility());
                            printWriter.print("  temporarilyInvisible:    ");
                            printWriter.println(bubbleStackView.mTemporarilyInvisible);
                            printWriter.print("  expandedViewTemporarilyHidden: ");
                            printWriter.println(bubbleStackView.mExpandedViewTemporarilyHidden);
                            StackAnimationController stackAnimationController = bubbleStackView.mStackAnimationController;
                            stackAnimationController.getClass();
                            printWriter.println("StackAnimationController state:");
                            printWriter.print("  isActive:             ");
                            printWriter.println(stackAnimationController.isActiveController());
                            printWriter.print("  restingStackPos:      ");
                            printWriter.println(stackAnimationController.mPositioner.getRestingPosition().toString());
                            printWriter.print("  currentStackPos:      ");
                            printWriter.println(stackAnimationController.mStackPosition.toString());
                            printWriter.print("  isMovingFromFlinging: ");
                            printWriter.println(stackAnimationController.mIsMovingFromFlinging);
                            printWriter.print("  withinDismiss:        ");
                            printWriter.println(stackAnimationController.isStackStuckToTarget());
                            printWriter.print("  firstBubbleSpringing: ");
                            printWriter.println(stackAnimationController.mFirstBubbleSpringingToTouch);
                            ExpandedAnimationController expandedAnimationController = bubbleStackView.mExpandedAnimationController;
                            expandedAnimationController.getClass();
                            printWriter.println("ExpandedAnimationController state:");
                            printWriter.print("  isActive:          ");
                            printWriter.println(expandedAnimationController.isActiveController());
                            printWriter.print("  animatingExpand:   ");
                            printWriter.println(expandedAnimationController.mAnimatingExpand);
                            printWriter.print("  animatingCollapse: ");
                            printWriter.println(expandedAnimationController.mAnimatingCollapse);
                            printWriter.print("  springingBubble:   ");
                            printWriter.println(expandedAnimationController.mSpringingBubbleToTouch);
                            if (bubbleStackView.mExpandedBubble != null) {
                                StringBuilder sbM = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "Expanded bubble state:", "  expandedBubbleKey: ");
                                sbM.append(bubbleStackView.mExpandedBubble.getKey());
                                printWriter.println(sbM.toString());
                                BubbleExpandedView expandedView = bubbleStackView.getExpandedView();
                                if (expandedView != null) {
                                    printWriter.println("  expandedViewVis:    " + expandedView.getVisibility());
                                    printWriter.println("  expandedViewAlpha:  " + expandedView.getAlpha());
                                    MagnificationImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  expandedViewTaskId: "), expandedView.mTaskId, printWriter);
                                    TaskView taskView = expandedView.mTaskView;
                                    if (taskView != null) {
                                        printWriter.println("  activityViewVis:    " + taskView.getVisibility());
                                        printWriter.println("  activityViewAlpha:  " + taskView.getAlpha());
                                    } else {
                                        printWriter.println("  activityView is null");
                                    }
                                } else {
                                    printWriter.println("Expanded bubble view state: expanded bubble view is null");
                                }
                            } else {
                                printWriter.println("Expanded bubble state: expanded bubble is null");
                            }
                        }
                        printWriter.println();
                        BubbleController.BubblesImpl.CachedState cachedState = bubbleController2.mImpl.mCachedState;
                        synchronized (cachedState) {
                            try {
                                printWriter.println("BubbleImpl.CachedState state:");
                                printWriter.println("mIsStackExpanded: " + cachedState.mIsStackExpanded);
                                printWriter.println("mSelectedBubbleKey: " + cachedState.mSelectedBubbleKey);
                                printWriter.println("mSuppressedBubbleKeys: " + cachedState.mSuppressedBubbleKeys.size());
                                Iterator it3 = cachedState.mSuppressedBubbleKeys.iterator();
                                while (it3.hasNext()) {
                                    printWriter.println("   suppressing: " + ((String) it3.next()));
                                }
                                printWriter.print("mSuppressedGroupToNotifKeys: ");
                                printWriter.println(cachedState.mSuppressedGroupToNotifKeys.size());
                                Iterator it4 = cachedState.mSuppressedGroupToNotifKeys.keySet().iterator();
                                while (it4.hasNext()) {
                                    printWriter.println("   suppressing: " + ((String) it4.next()));
                                }
                                printWriter.println("mNoteBubbleTaskIds: " + cachedState.mNoteBubbleTaskIds.values());
                            } catch (Throwable th2) {
                                throw th2;
                            }
                        }
                    }
                }, bubbleController);
                return;
            case 1:
                bubbleController.getClass();
                try {
                    bubbleController.mContext.unregisterReceiver(bubbleController.mBroadcastReceiver);
                    return;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return;
                }
            default:
                bubbleController.collapseStack();
                return;
        }
    }
}
