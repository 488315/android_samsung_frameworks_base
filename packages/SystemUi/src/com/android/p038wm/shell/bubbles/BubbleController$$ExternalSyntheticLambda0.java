package com.android.p038wm.shell.bubbles;

import android.content.Context;
import android.content.LocusId;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.BubbleController.C37913;
import com.android.p038wm.shell.bubbles.animation.ExpandedAnimationController;
import com.android.p038wm.shell.bubbles.animation.StackAnimationController;
import com.android.p038wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.p038wm.shell.common.DisplayChangeController;
import com.android.p038wm.shell.taskview.TaskView;
import com.android.wm.shell.bubbles.BubbleController.IBubblesImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt__IterablesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0137 A[LOOP:1: B:32:0x0131->B:34:0x0137, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x014a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        boolean z;
        Iterator it;
        BubbleVolatileRepository bubbleVolatileRepository;
        switch (this.$r8$classId) {
            case 0:
                final BubbleController bubbleController = (BubbleController) this.f$0;
                bubbleController.isShowingAsBubbleBar();
                BubbleData bubbleData = bubbleController.mBubbleData;
                bubbleData.mListener = bubbleController.mBubbleDataListener;
                boolean z2 = false;
                Object[] objArr = 0;
                Object[] objArr2 = 0;
                bubbleData.mBubbleMetadataFlagListener = new BubbleController$$ExternalSyntheticLambda5(bubbleController, 0 == true ? 1 : 0);
                int i = 1;
                bubbleController.mDataRepository.bubbleMetadataFlagListener = new BubbleController$$ExternalSyntheticLambda5(bubbleController, i);
                bubbleData.mCancelledListener = new BubbleController$$ExternalSyntheticLambda5(bubbleController, 2);
                try {
                    bubbleController.mWindowManagerShellWrapper.addPinnedStackListener(new BubbleController.BubblesImeListener(bubbleController, objArr2 == true ? 1 : 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                bubbleController.mBubbleData.mCurrentUserId = bubbleController.mCurrentUserId;
                ShellTaskOrganizer shellTaskOrganizer = bubbleController.mTaskOrganizer;
                ShellTaskOrganizer.LocusIdListener locusIdListener = new ShellTaskOrganizer.LocusIdListener() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda6
                    @Override // com.android.wm.shell.ShellTaskOrganizer.LocusIdListener
                    public final void onVisibilityChanged(int i2, LocusId locusId, boolean z3) {
                        Bubble bubble;
                        BubbleData bubbleData2 = BubbleController.this.mBubbleData;
                        bubbleData2.getClass();
                        Log.d("Bubbles", "onLocusVisibilityChanged: " + locusId + " visible=" + z3);
                        if (locusId != null) {
                            int i3 = 0;
                            while (true) {
                                ArrayList arrayList = (ArrayList) bubbleData2.mBubbles;
                                if (i3 >= arrayList.size()) {
                                    break;
                                }
                                bubble = (Bubble) arrayList.get(i3);
                                if (locusId.equals(bubble.mLocusId)) {
                                    break;
                                } else {
                                    i3++;
                                }
                            }
                        }
                        bubble = null;
                        ArraySet arraySet = bubbleData2.mVisibleLocusIds;
                        if (!z3 || (bubble != null && bubble.getTaskId() == i2)) {
                            arraySet.remove(locusId);
                        } else {
                            arraySet.add(locusId);
                        }
                        ArrayMap arrayMap = bubbleData2.mSuppressedBubbles;
                        if (bubble == null && (bubble = (Bubble) arrayMap.get(locusId)) == null) {
                            return;
                        }
                        boolean z4 = arrayMap.get(locusId) != null;
                        if (z3 && !z4) {
                            if (((bubble.mFlags & 4) != 0) && i2 != bubble.getTaskId()) {
                                arrayMap.put(locusId, bubble);
                                bubbleData2.doSuppress(bubble);
                                bubbleData2.dispatchPendingChanges();
                                return;
                            }
                        }
                        if (z3) {
                            return;
                        }
                        Bubble bubble2 = (Bubble) arrayMap.remove(locusId);
                        if (bubble2 != null) {
                            bubbleData2.doUnsuppress(bubble2);
                        }
                        bubbleData2.dispatchPendingChanges();
                    }
                };
                synchronized (shellTaskOrganizer.mLock) {
                    shellTaskOrganizer.mLocusIdListeners.add(locusIdListener);
                    for (int i2 = 0; i2 < shellTaskOrganizer.mVisibleTasksWithLocusId.size(); i2++) {
                        locusIdListener.onVisibilityChanged(shellTaskOrganizer.mVisibleTasksWithLocusId.keyAt(i2), (LocusId) shellTaskOrganizer.mVisibleTasksWithLocusId.valueAt(i2), true);
                    }
                }
                bubbleController.mLauncherApps.registerCallback(new LauncherApps.Callback() { // from class: com.android.wm.shell.bubbles.BubbleController.2
                    public C37902() {
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackageRemoved(String str, UserHandle userHandle) {
                        BubbleData bubbleData2 = BubbleController.this.mBubbleData;
                        bubbleData2.getClass();
                        BubbleData$$ExternalSyntheticLambda2 bubbleData$$ExternalSyntheticLambda2 = new BubbleData$$ExternalSyntheticLambda2(str, 1);
                        BubbleData$$ExternalSyntheticLambda6 bubbleData$$ExternalSyntheticLambda6 = new BubbleData$$ExternalSyntheticLambda6(13, 1, bubbleData2);
                        BubbleData.performActionOnBubblesMatching(bubbleData2.getBubbles(), bubbleData$$ExternalSyntheticLambda2, bubbleData$$ExternalSyntheticLambda6);
                        BubbleData.performActionOnBubblesMatching(bubbleData2.getOverflowBubbles(), bubbleData$$ExternalSyntheticLambda2, bubbleData$$ExternalSyntheticLambda6);
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackagesUnavailable(String[] strArr, UserHandle userHandle, boolean z3) {
                        for (String str : strArr) {
                            BubbleData bubbleData2 = BubbleController.this.mBubbleData;
                            bubbleData2.getClass();
                            BubbleData$$ExternalSyntheticLambda2 bubbleData$$ExternalSyntheticLambda2 = new BubbleData$$ExternalSyntheticLambda2(str, 1);
                            BubbleData$$ExternalSyntheticLambda6 bubbleData$$ExternalSyntheticLambda6 = new BubbleData$$ExternalSyntheticLambda6(13, 1, bubbleData2);
                            BubbleData.performActionOnBubblesMatching(bubbleData2.getBubbles(), bubbleData$$ExternalSyntheticLambda2, bubbleData$$ExternalSyntheticLambda6);
                            BubbleData.performActionOnBubblesMatching(bubbleData2.getOverflowBubbles(), bubbleData$$ExternalSyntheticLambda2, bubbleData$$ExternalSyntheticLambda6);
                        }
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onShortcutsChanged(final String str, List list, UserHandle userHandle) {
                        super.onShortcutsChanged(str, list, userHandle);
                        BubbleData bubbleData2 = BubbleController.this.mBubbleData;
                        bubbleData2.getClass();
                        final HashSet hashSet = new HashSet();
                        Iterator it2 = list.iterator();
                        while (it2.hasNext()) {
                            hashSet.add(((ShortcutInfo) it2.next()).getId());
                        }
                        Predicate predicate = new Predicate() { // from class: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda5
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                ShortcutInfo shortcutInfo;
                                String str2 = str;
                                Set set = hashSet;
                                Bubble bubble = (Bubble) obj;
                                boolean equals = str2.equals(bubble.mPackageName);
                                boolean hasMetadataShortcutId = bubble.hasMetadataShortcutId();
                                if (equals && hasMetadataShortcutId) {
                                    return equals && !(bubble.hasMetadataShortcutId() && (shortcutInfo = bubble.mShortcutInfo) != null && shortcutInfo.isEnabled() && set.contains(bubble.mShortcutInfo.getId()));
                                }
                                return false;
                            }
                        };
                        BubbleData$$ExternalSyntheticLambda6 bubbleData$$ExternalSyntheticLambda6 = new BubbleData$$ExternalSyntheticLambda6(12, 0, bubbleData2);
                        BubbleData.performActionOnBubblesMatching(bubbleData2.getBubbles(), predicate, bubbleData$$ExternalSyntheticLambda6);
                        BubbleData.performActionOnBubblesMatching(bubbleData2.getOverflowBubbles(), predicate, bubbleData$$ExternalSyntheticLambda6);
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackageAdded(String str, UserHandle userHandle) {
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackageChanged(String str, UserHandle userHandle) {
                    }

                    @Override // android.content.pm.LauncherApps.Callback
                    public final void onPackagesAvailable(String[] strArr, UserHandle userHandle, boolean z3) {
                    }
                }, bubbleController.mMainHandler);
                bubbleController.mTaskStackListener.addListener(bubbleController.new C37913());
                bubbleController.mDisplayController.mChangeController.mDisplayChangeListener.add(new DisplayChangeController.OnDisplayChangingListener() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda7
                    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
                    public final void onDisplayChange(int i3, int i4, int i5, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
                        BubbleController bubbleController2 = BubbleController.this;
                        if (i4 == i5) {
                            bubbleController2.getClass();
                            return;
                        }
                        BubbleStackView bubbleStackView = bubbleController2.mStackView;
                        if (bubbleStackView != null) {
                            bubbleStackView.onOrientationChanged();
                        }
                    }
                });
                bubbleController.mOneHandedOptional.ifPresent(new BubbleController$$ExternalSyntheticLambda8(bubbleController, objArr == true ? 1 : 0));
                bubbleController.mDragAndDropController.ifPresent(new BubbleController$$ExternalSyntheticLambda8(bubbleController, i));
                final SecHideInformationMirroringController secHideInformationMirroringController = new SecHideInformationMirroringController(bubbleController.mContext, new BubbleController$$ExternalSyntheticLambda5(bubbleController, 3));
                bubbleController.mHideInformationMirroringController = secHideInformationMirroringController;
                StringBuilder sb = new StringBuilder("init() hide?");
                SecHideInformationMirroringModel secHideInformationMirroringModel = secHideInformationMirroringController.mModel;
                Context context = secHideInformationMirroringController.mContext;
                secHideInformationMirroringModel.getClass();
                if (context != null) {
                    if ((Settings.Global.getInt(context.getContentResolver(), "smart_view_show_notification_on", 0) == 0) != false) {
                        z = true;
                        sb.append(z);
                        Log.d("SecHideInformationMirroringController-Bubble", sb.toString());
                        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("smart_view_show_notification_on"), false, new ContentObserver(new Handler()) { // from class: com.android.wm.shell.bubbles.SecHideInformationMirroringController.1
                            public C38281(Handler handler) {
                                super(handler);
                            }

                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z3) {
                                super.onChange(z3);
                                SecHideInformationMirroringController.this.updateMirroringWindowFlag();
                            }
                        });
                        secHideInformationMirroringController.updateMirroringWindowFlag();
                        List aliveUsers = bubbleController.mUserManager.getAliveUsers();
                        BubbleDataRepository bubbleDataRepository = bubbleController.mDataRepository;
                        bubbleDataRepository.getClass();
                        final ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(aliveUsers, 10));
                        it = aliveUsers.iterator();
                        while (it.hasNext()) {
                            arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
                        }
                        bubbleVolatileRepository = bubbleDataRepository.volatileRepository;
                        synchronized (bubbleVolatileRepository) {
                            int size = bubbleVolatileRepository.entitiesByUser.size();
                            int i3 = 0;
                            while (true) {
                                if (i3 < size) {
                                    int keyAt = bubbleVolatileRepository.entitiesByUser.keyAt(i3);
                                    if (!arrayList.contains(Integer.valueOf(keyAt))) {
                                        bubbleVolatileRepository.entitiesByUser.remove(keyAt);
                                        z2 = true;
                                    } else if (bubbleVolatileRepository.entitiesByUser.get(keyAt) != null) {
                                        z2 = ((List) bubbleVolatileRepository.entitiesByUser.get(keyAt)).removeIf(new Predicate() { // from class: com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$sanitizeBubbles$1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return !arrayList.contains(Integer.valueOf(((BubbleEntity) obj).userId));
                                            }
                                        });
                                    } else {
                                        i3++;
                                    }
                                }
                            }
                        }
                        if (z2) {
                            bubbleDataRepository.persistToDisk();
                        }
                        SparseArray sparseArray = new SparseArray();
                        for (UserInfo userInfo : bubbleController.mUserManager.getProfiles(bubbleController.mCurrentUserId)) {
                            sparseArray.put(userInfo.id, userInfo);
                        }
                        bubbleController.mCurrentProfiles = sparseArray;
                        bubbleController.mShellController.addConfigurationChangeListener(bubbleController);
                        bubbleController.mShellController.addExternalInterface("extra_shell_bubbles", new Supplier() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda9
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                BubbleController bubbleController2 = BubbleController.this;
                                bubbleController2.getClass();
                                return bubbleController2.new IBubblesImpl(bubbleController2);
                            }
                        }, bubbleController);
                        bubbleController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda10
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                BubbleController bubbleController2 = BubbleController.this;
                                PrintWriter printWriter = (PrintWriter) obj;
                                bubbleController2.getClass();
                                printWriter.println("BubbleController state:");
                                BubbleData bubbleData2 = bubbleController2.mBubbleData;
                                bubbleData2.getClass();
                                printWriter.print("selected: ");
                                BubbleViewProvider bubbleViewProvider = bubbleData2.mSelectedBubble;
                                printWriter.println(bubbleViewProvider != null ? bubbleViewProvider.getKey() : "null");
                                printWriter.print("expanded: ");
                                printWriter.println(bubbleData2.mExpanded);
                                printWriter.print("stack bubble count:    ");
                                ArrayList arrayList2 = (ArrayList) bubbleData2.mBubbles;
                                printWriter.println(arrayList2.size());
                                Iterator it2 = arrayList2.iterator();
                                while (it2.hasNext()) {
                                    ((Bubble) it2.next()).dump(printWriter);
                                }
                                printWriter.print("overflow bubble count:    ");
                                ArrayList arrayList3 = (ArrayList) bubbleData2.mOverflowBubbles;
                                printWriter.println(arrayList3.size());
                                Iterator it3 = arrayList3.iterator();
                                while (it3.hasNext()) {
                                    ((Bubble) it3.next()).dump(printWriter);
                                }
                                printWriter.print("summaryKeys: ");
                                HashMap hashMap = bubbleData2.mSuppressedGroupKeys;
                                printWriter.println(hashMap.size());
                                Iterator it4 = hashMap.keySet().iterator();
                                while (it4.hasNext()) {
                                    FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("   suppressing: ", (String) it4.next(), printWriter);
                                }
                                printWriter.println();
                                BubbleStackView bubbleStackView = bubbleController2.mStackView;
                                if (bubbleStackView != null) {
                                    printWriter.println("Stack view state:");
                                    ArrayList arrayList4 = new ArrayList();
                                    for (int i4 = 0; i4 < bubbleStackView.getBubbleCount(); i4++) {
                                        View childAt = bubbleStackView.mBubbleContainer.getChildAt(i4);
                                        if (childAt instanceof BadgedImageView) {
                                            BubbleViewProvider bubbleViewProvider2 = ((BadgedImageView) childAt).mBubble;
                                            arrayList4.add(bubbleStackView.mBubbleData.getBubbleInStackWithKey(bubbleViewProvider2 != null ? bubbleViewProvider2.getKey() : null));
                                        }
                                    }
                                    String formatBubblesString = BubbleDebugConfig.formatBubblesString(arrayList4, bubbleStackView.getExpandedBubble());
                                    printWriter.print("  bubbles on screen:       ");
                                    printWriter.println(formatBubblesString);
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
                                    printWriter.println(false);
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
                                        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "Expanded bubble state:", "  expandedBubbleKey: ");
                                        m75m.append(bubbleStackView.mExpandedBubble.getKey());
                                        printWriter.println(m75m.toString());
                                        BubbleExpandedView expandedView = bubbleStackView.mExpandedBubble.getExpandedView();
                                        if (expandedView != null) {
                                            printWriter.println("  expandedViewVis:    " + expandedView.getVisibility());
                                            printWriter.println("  expandedViewAlpha:  " + expandedView.getAlpha());
                                            printWriter.println("  expandedViewTaskId: " + expandedView.mTaskId);
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
                                    printWriter.println("BubbleImpl.CachedState state:");
                                    printWriter.println("mIsStackExpanded: " + cachedState.mIsStackExpanded);
                                    printWriter.println("mSelectedBubbleKey: " + cachedState.mSelectedBubbleKey);
                                    printWriter.print("mSuppressedBubbleKeys: ");
                                    printWriter.println(cachedState.mSuppressedBubbleKeys.size());
                                    Iterator it5 = cachedState.mSuppressedBubbleKeys.iterator();
                                    while (it5.hasNext()) {
                                        printWriter.println("   suppressing: " + ((String) it5.next()));
                                    }
                                    printWriter.print("mSuppressedGroupToNotifKeys: ");
                                    printWriter.println(cachedState.mSuppressedGroupToNotifKeys.size());
                                    Iterator it6 = cachedState.mSuppressedGroupToNotifKeys.keySet().iterator();
                                    while (it6.hasNext()) {
                                        printWriter.println("   suppressing: " + ((String) it6.next()));
                                    }
                                    printWriter.print("mAppBubbleTaskIds: " + cachedState.mAppBubbleTaskIds.values());
                                }
                            }
                        }, bubbleController);
                        return;
                    }
                }
                z = false;
                sb.append(z);
                Log.d("SecHideInformationMirroringController-Bubble", sb.toString());
                context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("smart_view_show_notification_on"), false, new ContentObserver(new Handler()) { // from class: com.android.wm.shell.bubbles.SecHideInformationMirroringController.1
                    public C38281(Handler handler) {
                        super(handler);
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z3) {
                        super.onChange(z3);
                        SecHideInformationMirroringController.this.updateMirroringWindowFlag();
                    }
                });
                secHideInformationMirroringController.updateMirroringWindowFlag();
                List aliveUsers2 = bubbleController.mUserManager.getAliveUsers();
                BubbleDataRepository bubbleDataRepository2 = bubbleController.mDataRepository;
                bubbleDataRepository2.getClass();
                final List<Integer> arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(aliveUsers2, 10));
                it = aliveUsers2.iterator();
                while (it.hasNext()) {
                }
                bubbleVolatileRepository = bubbleDataRepository2.volatileRepository;
                synchronized (bubbleVolatileRepository) {
                }
            case 1:
                BubbleController bubbleController2 = (BubbleController) this.f$0;
                bubbleController2.getClass();
                try {
                    bubbleController2.mContext.unregisterReceiver(bubbleController2.mBroadcastReceiver);
                    return;
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                    return;
                }
            default:
                BubbleController.this.collapseStack();
                return;
        }
    }
}
