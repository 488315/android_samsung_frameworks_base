package com.android.systemui.statusbar.notification.collection;

import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.systemui.Dumpable;
import com.android.systemui.NotiRune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.NotificationInteractionTracker;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$attach$1;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.PipelineState;
import com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderHelper;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.DefaultNotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager$attach$1;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager$viewRenderer$1;
import com.android.systemui.util.Assert;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeListBuilder implements Dumpable, PipelineDumpable {
    public static final C27942 DEFAULT_SECTIONER = new NotifSectioner("UnknownSection", 0) { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder.2
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final boolean isInSection(ListEntry listEntry) {
            return true;
        }
    };
    public static final int MAX_CONSECUTIVE_REENTRANT_REBUILDS = 3;
    public final NotifPipelineChoreographer mChoreographer;
    public final DumpManager mDumpManager;
    public final NotificationInteractionTracker mInteractionTracker;
    public final ShadeListBuilderLogger mLogger;
    public NotifStabilityManager mNotifStabilityManager;
    public RenderStageManager$attach$1 mOnRenderListListener;
    public final SystemClock mSystemClock;
    public final ArrayList mTempSectionMembers = new ArrayList();
    public List mNotifList = new ArrayList();
    public List mNewNotifList = new ArrayList();
    public final SemiStableSort mSemiStableSort = new SemiStableSort();
    public final ShadeListBuilder$$ExternalSyntheticLambda0 mStableOrder = new ShadeListBuilder$$ExternalSyntheticLambda0(this, 0);
    public final PipelineState mPipelineState = new PipelineState();
    public final Map mGroups = new ArrayMap();
    public Collection mAllEntries = Collections.emptyList();
    public Collection mPendingEntries = null;
    public int mIterationCount = 0;
    public final List mNotifPreGroupFilters = new ArrayList();
    public final List mNotifPromoters = new ArrayList();
    public final List mNotifFinalizeFilters = new ArrayList();
    public final List mNotifComparators = new ArrayList();
    public final List mNotifSections = new ArrayList();
    public final List mOnBeforeTransformGroupsListeners = new ArrayList();
    public final List mOnBeforeSortListeners = new ArrayList();
    public final List mOnBeforeFinalizeFilterListeners = new ArrayList();
    public final List mOnBeforeRenderListListeners = new ArrayList();
    public List mReadOnlyNotifList = Collections.unmodifiableList(this.mNotifList);
    public List mReadOnlyNewNotifList = Collections.unmodifiableList(this.mNewNotifList);
    public int mConsecutiveReentrantRebuilds = 0;
    public final C27931 mReadyForBuildListener = new C27931();
    public final ShadeListBuilder$$ExternalSyntheticLambda1 mTopLevelComparator = new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int compare;
            ShadeListBuilder shadeListBuilder = ShadeListBuilder.this;
            ListEntry listEntry = (ListEntry) obj;
            ListEntry listEntry2 = (ListEntry) obj2;
            shadeListBuilder.getClass();
            ListAttachState listAttachState = listEntry.mAttachState;
            ListEntry.checkNull(listAttachState);
            NotifSection notifSection = listAttachState.section;
            int i = notifSection != null ? notifSection.index : -1;
            ListAttachState listAttachState2 = listEntry2.mAttachState;
            ListEntry.checkNull(listAttachState2);
            NotifSection notifSection2 = listAttachState2.section;
            int compare2 = Integer.compare(i, notifSection2 != null ? notifSection2.index : -1);
            if (compare2 != 0) {
                return compare2;
            }
            NotifSection section = listEntry.getSection();
            if (section != listEntry2.getSection()) {
                throw new RuntimeException("Entry ordering should only be done within sections");
            }
            NotifComparator notifComparator = section != null ? section.comparator : null;
            if (notifComparator != null && (compare = notifComparator.compare(listEntry, listEntry2)) != 0) {
                return compare;
            }
            int i2 = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) shadeListBuilder.mNotifComparators;
                if (i2 >= arrayList.size()) {
                    int compare3 = Integer.compare(listEntry.getRepresentativeEntry().mRanking.getRank(), listEntry2.getRepresentativeEntry().mRanking.getRank());
                    return compare3 != 0 ? compare3 : Long.compare(listEntry.getRepresentativeEntry().mSbn.getNotification().when, listEntry2.getRepresentativeEntry().mSbn.getNotification().when) * (-1);
                }
                int compare4 = ((NotifComparator) arrayList.get(i2)).compare(listEntry, listEntry2);
                if (compare4 != 0) {
                    return compare4;
                }
                i2++;
            }
        }
    };
    public final ShadeListBuilder$$ExternalSyntheticLambda2 mGroupChildrenComparator = new ShadeListBuilder$$ExternalSyntheticLambda2();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$1 */
    public final class C27931 {
        public C27931() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v23, types: [com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda1] */
    public ShadeListBuilder(DumpManager dumpManager, NotifPipelineChoreographer notifPipelineChoreographer, NotifPipelineFlags notifPipelineFlags, NotificationInteractionTracker notificationInteractionTracker, ShadeListBuilderLogger shadeListBuilderLogger, SystemClock systemClock) {
        this.mSystemClock = systemClock;
        this.mLogger = shadeListBuilderLogger;
        notifPipelineFlags.getClass();
        Flags.INSTANCE.getClass();
        notifPipelineFlags.featureFlags.getClass();
        this.mInteractionTracker = notificationInteractionTracker;
        this.mChoreographer = notifPipelineChoreographer;
        this.mDumpManager = dumpManager;
        setSectioners(Collections.emptyList());
    }

    public static void annulAddition(ListEntry listEntry) {
        ListAttachState listAttachState = listEntry.mAttachState;
        ListEntry.checkNull(listAttachState);
        listAttachState.parent = null;
        listAttachState.section = null;
        listAttachState.promoter = null;
        listAttachState.stableIndex = -1;
    }

    public static boolean applyFilters(NotificationEntry notificationEntry, long j, List list) {
        NotifFilter notifFilter;
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                notifFilter = null;
                break;
            }
            notifFilter = (NotifFilter) arrayList.get(i);
            if (notifFilter.shouldFilterOut(notificationEntry, j)) {
                break;
            }
            i++;
        }
        ListAttachState listAttachState = notificationEntry.mAttachState;
        ListEntry.checkNull(listAttachState);
        listAttachState.excludingFilter = notifFilter;
        if (notifFilter != null) {
            notificationEntry.initializationTime = -1L;
        }
        return notifFilter != null;
    }

    public static void callOnCleanup(List list) {
        for (int i = 0; i < list.size(); i++) {
            ((Pluggable) list.get(i)).onCleanup();
        }
    }

    public static <T> boolean isSorted(List<T> list, Comparator<? super T> comparator) {
        if (list.size() <= 1) {
            return true;
        }
        Iterator<T> it = list.iterator();
        T next = it.next();
        while (it.hasNext()) {
            T next2 = it.next();
            if (comparator.compare(next, next2) > 0) {
                return false;
            }
            next = next2;
        }
        return true;
    }

    public final void applyNewNotifList() {
        this.mNotifList.clear();
        List list = this.mNotifList;
        this.mNotifList = this.mNewNotifList;
        this.mNewNotifList = list;
        List list2 = this.mReadOnlyNotifList;
        this.mReadOnlyNotifList = this.mReadOnlyNewNotifList;
        this.mReadOnlyNewNotifList = list2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:130:0x0243, code lost:
    
        r10 = r9.mPreviousAttachState;
        com.android.systemui.statusbar.notification.collection.ListEntry.checkNull(r10);
        r11 = r9.wasAttachedInPreviousPass();
        r13 = r9.mAttachState;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x024e, code lost:
    
        if (r11 == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0252, code lost:
    
        if (r12 == r10.section) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0260, code lost:
    
        if (getStabilityManager().isSectionChangeAllowed(r9.getRepresentativeEntry()) != false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0262, code lost:
    
        com.android.systemui.statusbar.notification.collection.ListEntry.checkNull(r13);
        r13.suppressedChanges.section = r12;
        r12 = r10.section;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x026b, code lost:
    
        com.android.systemui.statusbar.notification.collection.ListEntry.checkNull(r13);
        r13.section = r12;
        r10 = r9.getRepresentativeEntry();
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0274, code lost:
    
        if (r10 == null) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0276, code lost:
    
        r11 = r10.mAttachState;
        com.android.systemui.statusbar.notification.collection.ListEntry.checkNull(r11);
        r11.section = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x027d, code lost:
    
        if (r12 == null) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x027f, code lost:
    
        r10.mBucket = r12.bucket;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0285, code lost:
    
        if ((r9 instanceof com.android.systemui.statusbar.notification.collection.GroupEntry) == false) goto L363;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0287, code lost:
    
        r9 = ((com.android.systemui.statusbar.notification.collection.GroupEntry) r9).mUnmodifiableChildren.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0293, code lost:
    
        if (r9.hasNext() == false) goto L364;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0295, code lost:
    
        r10 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r9.next();
        r11 = r10.mAttachState;
        com.android.systemui.statusbar.notification.collection.ListEntry.checkNull(r11);
        r11.section = r12;
        r11 = r10.mAttachState;
        com.android.systemui.statusbar.notification.collection.ListEntry.checkNull(r11);
        r11.section = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02a9, code lost:
    
        if (r12 == null) goto L368;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02ab, code lost:
    
        r10.mBucket = r12.bucket;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void buildList() {
        final ShadeListBuilder$$ExternalSyntheticLambda0 shadeListBuilder$$ExternalSyntheticLambda0;
        SemiStableSort semiStableSort;
        Iterator it;
        ShadeListBuilder$$ExternalSyntheticLambda0 shadeListBuilder$$ExternalSyntheticLambda02;
        boolean z;
        Iterator it2;
        Trace.beginSection("ShadeListBuilder.buildList");
        PipelineState pipelineState = this.mPipelineState;
        boolean z2 = true;
        if (pipelineState.mState >= 1) {
            throw new IllegalStateException("Required state is <1 but actual state is " + pipelineState.mState);
        }
        Collection collection = this.mPendingEntries;
        List list = null;
        if (collection != null) {
            this.mAllEntries = collection;
            this.mPendingEntries = null;
        }
        boolean isPipelineRunAllowed = getStabilityManager().isPipelineRunAllowed();
        ShadeListBuilderLogger shadeListBuilderLogger = this.mLogger;
        if (!isPipelineRunAllowed) {
            shadeListBuilderLogger.logPipelineRunSuppressed();
            Trace.endSection();
            return;
        }
        pipelineState.mState = 1;
        pipelineState.incrementTo(2);
        ArrayMap arrayMap = (ArrayMap) this.mGroups;
        for (GroupEntry groupEntry : arrayMap.values()) {
            groupEntry.beginNewAttachState();
            ((ArrayList) groupEntry.mChildren).clear();
            groupEntry.mSummary = null;
            if (NotiRune.NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT) {
                groupEntry.mLogicalSummary = null;
            }
        }
        for (NotificationEntry notificationEntry : this.mAllEntries) {
            notificationEntry.beginNewAttachState();
            if (NotiRune.NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT) {
                notificationEntry.mGroupEntry = null;
            }
        }
        this.mNotifList.clear();
        getStabilityManager().onBeginRun();
        pipelineState.incrementTo(3);
        Collection collection2 = this.mAllEntries;
        List list2 = this.mNotifList;
        List list3 = this.mNotifPreGroupFilters;
        filterNotifs(collection2, list2, list3);
        pipelineState.incrementTo(4);
        List list4 = this.mNotifList;
        List list5 = this.mNewNotifList;
        Trace.beginSection("ShadeListBuilder.groupNotifs");
        Iterator it3 = list4.iterator();
        while (it3.hasNext()) {
            NotificationEntry notificationEntry2 = (NotificationEntry) ((ListEntry) it3.next());
            if (notificationEntry2.mSbn.isGroup()) {
                String groupKey = notificationEntry2.mSbn.getGroupKey();
                GroupEntry groupEntry2 = (GroupEntry) arrayMap.get(groupKey);
                if (groupEntry2 == null) {
                    ((SystemClockImpl) this.mSystemClock).getClass();
                    groupEntry2 = new GroupEntry(groupKey, android.os.SystemClock.uptimeMillis());
                    arrayMap.put(groupKey, groupEntry2);
                }
                if (groupEntry2.getParent() == null) {
                    groupEntry2.setParent(GroupEntry.ROOT_ENTRY);
                    list5.add(groupEntry2);
                }
                notificationEntry2.setParent(groupEntry2);
                boolean z3 = NotiRune.NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT;
                if (z3) {
                    notificationEntry2.mGroupEntry = groupEntry2;
                }
                if (notificationEntry2.mSbn.getNotification().isGroupSummary()) {
                    NotificationEntry notificationEntry3 = groupEntry2.mSummary;
                    if (notificationEntry3 == null) {
                        groupEntry2.mSummary = notificationEntry2;
                        if (z3) {
                            groupEntry2.mLogicalSummary = notificationEntry2;
                        }
                    } else {
                        shadeListBuilderLogger.logDuplicateSummary(this.mIterationCount, groupEntry2, notificationEntry3, notificationEntry2);
                        if (notificationEntry2.mSbn.getPostTime() > notificationEntry3.mSbn.getPostTime()) {
                            groupEntry2.mSummary = notificationEntry2;
                            if (z3) {
                                groupEntry2.mLogicalSummary = notificationEntry2;
                            }
                            annulAddition(list5, notificationEntry3);
                        } else {
                            annulAddition(list5, notificationEntry2);
                        }
                    }
                } else {
                    ((ArrayList) groupEntry2.mChildren).add(notificationEntry2);
                }
            } else {
                String str = notificationEntry2.mKey;
                if (arrayMap.containsKey(str)) {
                    shadeListBuilderLogger.logDuplicateTopLevelKey(this.mIterationCount, str);
                } else {
                    notificationEntry2.setParent(GroupEntry.ROOT_ENTRY);
                    list5.add(notificationEntry2);
                }
            }
        }
        Trace.endSection();
        applyNewNotifList();
        pruneIncompleteGroups(this.mNotifList);
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeTransformGroups");
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) this.mOnBeforeTransformGroupsListeners;
            if (i >= arrayList.size()) {
                break;
            }
            ((HeadsUpCoordinator$attach$1) arrayList.get(i)).onBeforeTransformGroups();
            i++;
        }
        Trace.endSection();
        pipelineState.incrementTo(5);
        final List list6 = this.mNotifList;
        Trace.beginSection("ShadeListBuilder.promoteNotifs");
        for (int i2 = 0; i2 < list6.size(); i2++) {
            ListEntry listEntry = (ListEntry) list6.get(i2);
            if (listEntry instanceof GroupEntry) {
                ((ArrayList) ((GroupEntry) listEntry).mChildren).removeIf(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        NotifPromoter notifPromoter;
                        ShadeListBuilder shadeListBuilder = ShadeListBuilder.this;
                        List list7 = list6;
                        NotificationEntry notificationEntry4 = (NotificationEntry) obj;
                        int i3 = 0;
                        while (true) {
                            ArrayList arrayList2 = (ArrayList) shadeListBuilder.mNotifPromoters;
                            if (i3 >= arrayList2.size()) {
                                notifPromoter = null;
                                break;
                            }
                            notifPromoter = (NotifPromoter) arrayList2.get(i3);
                            if (notifPromoter.shouldPromoteToTopLevel(notificationEntry4)) {
                                break;
                            }
                            i3++;
                        }
                        ListAttachState listAttachState = notificationEntry4.mAttachState;
                        ListEntry.checkNull(listAttachState);
                        listAttachState.promoter = notifPromoter;
                        boolean z4 = notifPromoter != null;
                        if (z4) {
                            notificationEntry4.setParent(GroupEntry.ROOT_ENTRY);
                            list7.add(notificationEntry4);
                        }
                        return z4;
                    }
                });
            }
        }
        Trace.endSection();
        pruneIncompleteGroups(this.mNotifList);
        pipelineState.incrementTo(6);
        List list7 = this.mNotifList;
        if (!getStabilityManager().isEveryChangeAllowed()) {
            Trace.beginSection("ShadeListBuilder.stabilizeGroupingNotifs");
            for (int i3 = 0; i3 < list7.size(); i3++) {
                ListEntry listEntry2 = (ListEntry) list7.get(i3);
                if (listEntry2 instanceof GroupEntry) {
                    GroupEntry groupEntry3 = (GroupEntry) listEntry2;
                    List list8 = groupEntry3.mChildren;
                    for (int i4 = 0; i4 < groupEntry3.mUnmodifiableChildren.size(); i4++) {
                        maybeSuppressGroupChange((NotificationEntry) ((ArrayList) list8).get(i4), list7);
                    }
                } else {
                    maybeSuppressGroupChange(listEntry2.getRepresentativeEntry(), list7);
                }
            }
            Trace.endSection();
        }
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeSort");
        ArrayList arrayList2 = (ArrayList) this.mOnBeforeSortListeners;
        if (arrayList2.size() > 0) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(arrayList2.get(0));
            throw null;
        }
        Trace.endSection();
        pipelineState.incrementTo(7);
        Trace.beginSection("ShadeListBuilder.assignSections");
        Iterator it4 = this.mNotifList.iterator();
        while (true) {
            boolean hasNext = it4.hasNext();
            List list9 = this.mNotifSections;
            if (!hasNext) {
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.notifySectionEntriesUpdated");
                ArrayList arrayList3 = this.mTempSectionMembers;
                arrayList3.clear();
                ArrayList arrayList4 = (ArrayList) list9;
                Iterator it5 = arrayList4.iterator();
                while (it5.hasNext()) {
                    NotifSection notifSection = (NotifSection) it5.next();
                    for (ListEntry listEntry3 : this.mNotifList) {
                        if (notifSection == listEntry3.getSection()) {
                            arrayList3.add(listEntry3);
                        }
                    }
                    notifSection.sectioner.onEntriesUpdated(arrayList3);
                    arrayList3.clear();
                }
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.sortListAndGroups");
                Iterator it6 = this.mNotifList.iterator();
                boolean z4 = true;
                while (true) {
                    boolean hasNext2 = it6.hasNext();
                    shadeListBuilder$$ExternalSyntheticLambda0 = this.mStableOrder;
                    semiStableSort = this.mSemiStableSort;
                    if (!hasNext2) {
                        break;
                    }
                    ListEntry listEntry4 = (ListEntry) it6.next();
                    if (listEntry4 instanceof GroupEntry) {
                        List list10 = ((GroupEntry) listEntry4).mChildren;
                        boolean isEveryChangeAllowed = getStabilityManager().isEveryChangeAllowed();
                        ShadeListBuilder$$ExternalSyntheticLambda2 shadeListBuilder$$ExternalSyntheticLambda2 = this.mGroupChildrenComparator;
                        if (isEveryChangeAllowed) {
                            ((ArrayList) list10).sort(shadeListBuilder$$ExternalSyntheticLambda2);
                            it2 = it6;
                        } else {
                            ((ArrayList) semiStableSort.preallocatedWorkspace$delegate.getValue()).clear();
                            ArrayList arrayList5 = (ArrayList) semiStableSort.preallocatedWorkspace$delegate.getValue();
                            List list11 = arrayList5.isEmpty() ? arrayList5 : list;
                            if (list11 == null) {
                                list11 = arrayList5.subList(arrayList5.size(), arrayList5.size());
                            }
                            List list12 = list11;
                            ArrayList arrayList6 = (ArrayList) list10;
                            Iterator it7 = arrayList6.iterator();
                            while (it7.hasNext()) {
                                Object next = it7.next();
                                if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(next) != null) {
                                    list12.add(next);
                                }
                            }
                            if (list12.size() > 1) {
                                CollectionsKt__MutableCollectionsJVMKt.sortWith(list12, new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$sortTo$$inlined$sortBy$1
                                    @Override // java.util.Comparator
                                    public final int compare(Object obj, Object obj2) {
                                        Integer rank = ((ShadeListBuilder$$ExternalSyntheticLambda0) SemiStableSort.StableOrder.this).getRank(obj);
                                        Intrinsics.checkNotNull(rank);
                                        Integer rank2 = ((ShadeListBuilder$$ExternalSyntheticLambda0) SemiStableSort.StableOrder.this).getRank(obj2);
                                        Intrinsics.checkNotNull(rank2);
                                        return ComparisonsKt__ComparisonsKt.compareValues(rank, rank2);
                                    }
                                });
                            }
                            SemiStableSort.Companion companion = SemiStableSort.Companion;
                            boolean isSorted = companion.isSorted(list12, shadeListBuilder$$ExternalSyntheticLambda2);
                            semiStableSort.getPreallocatedAdditions().clear();
                            ArrayList preallocatedAdditions = semiStableSort.getPreallocatedAdditions();
                            for (Object obj : arrayList6) {
                                Iterator it8 = it6;
                                if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(obj) == null) {
                                    preallocatedAdditions.add(obj);
                                }
                                it6 = it8;
                            }
                            it2 = it6;
                            CollectionsKt__MutableCollectionsJVMKt.sortWith(preallocatedAdditions, shadeListBuilder$$ExternalSyntheticLambda2);
                            SemiStableSort.Companion.access$insertPreSortedElementsWithFewestMisOrderings(companion, list12, preallocatedAdditions, shadeListBuilder$$ExternalSyntheticLambda2);
                            semiStableSort.getPreallocatedAdditions().clear();
                            arrayList6.clear();
                            arrayList6.addAll(arrayList5);
                            z2 = isSorted;
                        }
                        z4 &= z2;
                        it6 = it2;
                        z2 = true;
                        list = null;
                    }
                }
                this.mNotifList.sort(this.mTopLevelComparator);
                if (!getStabilityManager().isEveryChangeAllowed()) {
                    List list13 = this.mNotifList;
                    ShadeListBuilderHelper.INSTANCE.getClass();
                    ArrayList arrayList7 = new ArrayList();
                    int size = list13.size();
                    int i5 = 0;
                    int i6 = 0;
                    Integer num = null;
                    while (i5 < size) {
                        ListAttachState listAttachState = ((ListEntry) list13.get(i5)).mAttachState;
                        ListEntry.checkNull(listAttachState);
                        NotifSection notifSection2 = listAttachState.section;
                        Integer valueOf = Integer.valueOf(notifSection2 != null ? notifSection2.index : -1);
                        if (num == null) {
                            z = z4;
                        } else if (Intrinsics.areEqual(num, valueOf)) {
                            z = z4;
                            i5++;
                            z4 = z;
                        } else {
                            z = z4;
                            if (i5 - i6 >= 1) {
                                arrayList7.add(list13.subList(i6, i5));
                            }
                            i6 = i5;
                        }
                        num = valueOf;
                        i5++;
                        z4 = z;
                    }
                    boolean z5 = z4;
                    if (size - i6 >= 1) {
                        arrayList7.add(list13.subList(i6, size));
                    }
                    Iterator it9 = arrayList7.iterator();
                    z4 = z5;
                    while (it9.hasNext()) {
                        List list14 = (List) it9.next();
                        List list15 = this.mNewNotifList;
                        semiStableSort.getClass();
                        List list16 = list15.isEmpty() ? list15 : null;
                        if (list16 == null) {
                            list16 = list15.subList(list15.size(), list15.size());
                        }
                        for (Object obj2 : list14) {
                            if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(obj2) != null) {
                                list16.add(obj2);
                            }
                        }
                        Comparator comparator = new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$stabilizeTo$$inlined$compareBy$1
                            @Override // java.util.Comparator
                            public final int compare(Object obj3, Object obj4) {
                                Integer rank = ((ShadeListBuilder$$ExternalSyntheticLambda0) SemiStableSort.StableOrder.this).getRank(obj3);
                                Intrinsics.checkNotNull(rank);
                                Integer rank2 = ((ShadeListBuilder$$ExternalSyntheticLambda0) SemiStableSort.StableOrder.this).getRank(obj4);
                                Intrinsics.checkNotNull(rank2);
                                return ComparisonsKt__ComparisonsKt.compareValues(rank, rank2);
                            }
                        };
                        SemiStableSort.Companion companion2 = SemiStableSort.Companion;
                        boolean isSorted2 = companion2.isSorted(list16, comparator);
                        if (!isSorted2) {
                            CollectionsKt__MutableCollectionsJVMKt.sortWith(list16, comparator);
                        }
                        if (list16.isEmpty()) {
                            for (Object obj3 : list14) {
                                if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(obj3) == null) {
                                    list16.add(obj3);
                                }
                            }
                            it = it9;
                            shadeListBuilder$$ExternalSyntheticLambda02 = shadeListBuilder$$ExternalSyntheticLambda0;
                        } else {
                            semiStableSort.getPreallocatedAdditions().clear();
                            ArrayList preallocatedAdditions2 = semiStableSort.getPreallocatedAdditions();
                            for (Object obj4 : list14) {
                                Iterator it10 = it9;
                                if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(obj4) == null) {
                                    preallocatedAdditions2.add(obj4);
                                }
                                it9 = it10;
                            }
                            it = it9;
                            if (!preallocatedAdditions2.isEmpty()) {
                                Lazy lazy = semiStableSort.preallocatedMapToIndex$delegate;
                                ((HashMap) lazy.getValue()).clear();
                                int i7 = 0;
                                for (Iterator it11 = list14.iterator(); it11.hasNext(); it11 = it11) {
                                    ShadeListBuilder$$ExternalSyntheticLambda0 shadeListBuilder$$ExternalSyntheticLambda03 = shadeListBuilder$$ExternalSyntheticLambda0;
                                    Object next2 = it11.next();
                                    int i8 = i7 + 1;
                                    if (i7 < 0) {
                                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                                        throw null;
                                    }
                                    ((HashMap) lazy.getValue()).put(next2, Integer.valueOf(i7));
                                    i7 = i8;
                                    shadeListBuilder$$ExternalSyntheticLambda0 = shadeListBuilder$$ExternalSyntheticLambda03;
                                }
                                shadeListBuilder$$ExternalSyntheticLambda02 = shadeListBuilder$$ExternalSyntheticLambda0;
                                SemiStableSort.Companion.access$insertPreSortedElementsWithFewestMisOrderings(companion2, list16, preallocatedAdditions2, (Comparator) semiStableSort.preallocatedMapToIndexComparator$delegate.getValue());
                                ((HashMap) lazy.getValue()).clear();
                            } else {
                                shadeListBuilder$$ExternalSyntheticLambda02 = shadeListBuilder$$ExternalSyntheticLambda0;
                            }
                            semiStableSort.getPreallocatedAdditions().clear();
                        }
                        z4 &= isSorted2;
                        it9 = it;
                        shadeListBuilder$$ExternalSyntheticLambda0 = shadeListBuilder$$ExternalSyntheticLambda02;
                    }
                    applyNewNotifList();
                }
                List list17 = this.mNotifList;
                if (list17.size() != 0) {
                    NotifSection section = ((ListEntry) list17.get(0)).getSection();
                    Objects.requireNonNull(section);
                    NotifSection notifSection3 = section;
                    int i9 = 0;
                    for (int i10 = 0; i10 < list17.size(); i10++) {
                        ListEntry listEntry5 = (ListEntry) list17.get(i10);
                        NotifSection section2 = listEntry5.getSection();
                        Objects.requireNonNull(section2);
                        if (section2.index != notifSection3.index) {
                            notifSection3 = section2;
                            i9 = 0;
                        }
                        ListAttachState listAttachState2 = listEntry5.mAttachState;
                        ListEntry.checkNull(listAttachState2);
                        int i11 = i9 + 1;
                        listAttachState2.stableIndex = i9;
                        if (listEntry5 instanceof GroupEntry) {
                            GroupEntry groupEntry4 = (GroupEntry) listEntry5;
                            NotificationEntry notificationEntry4 = groupEntry4.mSummary;
                            if (notificationEntry4 != null) {
                                ListAttachState listAttachState3 = notificationEntry4.mAttachState;
                                ListEntry.checkNull(listAttachState3);
                                listAttachState3.stableIndex = i11;
                                i11++;
                            }
                            Iterator it12 = groupEntry4.mUnmodifiableChildren.iterator();
                            while (it12.hasNext()) {
                                ListAttachState listAttachState4 = ((NotificationEntry) it12.next()).mAttachState;
                                ListEntry.checkNull(listAttachState4);
                                listAttachState4.stableIndex = i11;
                                i11++;
                            }
                        }
                        i9 = i11;
                    }
                }
                if (!z4) {
                    getStabilityManager().onEntryReorderSuppressed();
                }
                Trace.endSection();
                List list18 = this.mReadOnlyNotifList;
                Trace.beginSection("ShadeListBuilder.dispatchOnBeforeFinalizeFilter");
                int i12 = 0;
                while (true) {
                    ArrayList arrayList8 = (ArrayList) this.mOnBeforeFinalizeFilterListeners;
                    if (i12 >= arrayList8.size()) {
                        break;
                    }
                    ((OnBeforeFinalizeFilterListener) arrayList8.get(i12)).onBeforeFinalizeFilter(list18);
                    i12++;
                }
                Trace.endSection();
                pipelineState.incrementTo(8);
                List list19 = this.mNotifList;
                List list20 = this.mNewNotifList;
                List list21 = this.mNotifFinalizeFilters;
                filterNotifs(list19, list20, list21);
                applyNewNotifList();
                pruneIncompleteGroups(this.mNotifList);
                pipelineState.incrementTo(9);
                Trace.beginSection("ShadeListBuilder.logChanges");
                Iterator it13 = this.mAllEntries.iterator();
                while (it13.hasNext()) {
                    logAttachStateChanges((NotificationEntry) it13.next());
                }
                Iterator it14 = arrayMap.values().iterator();
                while (it14.hasNext()) {
                    logAttachStateChanges((GroupEntry) it14.next());
                }
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.freeEmptyGroups");
                arrayMap.values().removeIf(new ShadeListBuilder$$ExternalSyntheticLambda3());
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.cleanupPluggables");
                callOnCleanup(list3);
                callOnCleanup(this.mNotifPromoters);
                callOnCleanup(list21);
                callOnCleanup(this.mNotifComparators);
                for (int i13 = 0; i13 < arrayList4.size(); i13++) {
                    ((NotifSection) arrayList4.get(i13)).sectioner.getClass();
                }
                callOnCleanup(List.of(getStabilityManager()));
                Trace.endSection();
                List list22 = this.mReadOnlyNotifList;
                Trace.beginSection("ShadeListBuilder.dispatchOnBeforeRenderList");
                int i14 = 0;
                while (true) {
                    ArrayList arrayList9 = (ArrayList) this.mOnBeforeRenderListListeners;
                    if (i14 >= arrayList9.size()) {
                        break;
                    }
                    ((OnBeforeRenderListListener) arrayList9.get(i14)).onBeforeRenderList(list22);
                    i14++;
                }
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.onRenderList");
                RenderStageManager$attach$1 renderStageManager$attach$1 = this.mOnRenderListListener;
                if (renderStageManager$attach$1 != null) {
                    List list23 = this.mReadOnlyNotifList;
                    RenderStageManager renderStageManager = renderStageManager$attach$1.$tmp0;
                    renderStageManager.getClass();
                    if (Trace.isTagEnabled(4096L)) {
                        Trace.traceBegin(4096L, "RenderStageManager.onRenderList");
                        try {
                            ShadeViewManager$viewRenderer$1 shadeViewManager$viewRenderer$1 = renderStageManager.viewRenderer;
                            if (shadeViewManager$viewRenderer$1 != null) {
                                shadeViewManager$viewRenderer$1.onRenderList(list23);
                                renderStageManager.dispatchOnAfterRenderList(shadeViewManager$viewRenderer$1, list23);
                                renderStageManager.dispatchOnAfterRenderGroups(shadeViewManager$viewRenderer$1, list23);
                                renderStageManager.dispatchOnAfterRenderEntries(shadeViewManager$viewRenderer$1, list23);
                                Unit unit = Unit.INSTANCE;
                            }
                        } finally {
                            Trace.traceEnd(4096L);
                        }
                    } else {
                        ShadeViewManager$viewRenderer$1 shadeViewManager$viewRenderer$12 = renderStageManager.viewRenderer;
                        if (shadeViewManager$viewRenderer$12 != null) {
                            shadeViewManager$viewRenderer$12.onRenderList(list23);
                            renderStageManager.dispatchOnAfterRenderList(shadeViewManager$viewRenderer$12, list23);
                            renderStageManager.dispatchOnAfterRenderGroups(shadeViewManager$viewRenderer$12, list23);
                            renderStageManager.dispatchOnAfterRenderEntries(shadeViewManager$viewRenderer$12, list23);
                        }
                    }
                }
                Trace.endSection();
                Trace.beginSection("ShadeListBuilder.logEndBuildList");
                int i15 = this.mIterationCount;
                int size2 = this.mReadOnlyNotifList.size();
                List list24 = this.mReadOnlyNotifList;
                int i16 = 0;
                for (int i17 = 0; i17 < list24.size(); i17++) {
                    ListEntry listEntry6 = (ListEntry) list24.get(i17);
                    if (listEntry6 instanceof GroupEntry) {
                        i16 = ((GroupEntry) listEntry6).mUnmodifiableChildren.size() + i16;
                    }
                }
                shadeListBuilderLogger.logEndBuildList(i15, size2, i16, !getStabilityManager().isEveryChangeAllowed());
                if (this.mIterationCount % 10 == 0) {
                    Trace.beginSection("ShadeListBuilder.logFinalList");
                    shadeListBuilderLogger.logFinalList(this.mNotifList);
                    Trace.endSection();
                }
                Trace.endSection();
                pipelineState.mState = 0;
                this.mIterationCount++;
                Trace.endSection();
                return;
            }
            ListEntry listEntry7 = (ListEntry) it4.next();
            int i18 = 0;
            while (true) {
                ArrayList arrayList10 = (ArrayList) list9;
                if (i18 >= arrayList10.size()) {
                    throw new RuntimeException("Missing default sectioner!");
                }
                NotifSection notifSection4 = (NotifSection) arrayList10.get(i18);
                if (notifSection4.sectioner.isInSection(listEntry7)) {
                    break;
                } else {
                    i18++;
                }
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("\tShadeListBuilder shade notifications:");
        Assert.isMainThread();
        PipelineState pipelineState = this.mPipelineState;
        pipelineState.requireState();
        if (this.mReadOnlyNotifList.size() == 0) {
            printWriter.println("\t\t None");
        }
        Assert.isMainThread();
        pipelineState.requireState();
        List list = this.mReadOnlyNotifList;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ListEntry listEntry = (ListEntry) list.get(i);
            String num = Integer.toString(i);
            String logKey = NotificationUtils.logKey(listEntry);
            NotificationInteractionTracker notificationInteractionTracker = this.mInteractionTracker;
            ListDumper.dumpEntry(listEntry, num, "\t\t", sb, true, notificationInteractionTracker.hasUserInteractedWith(logKey));
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (notificationEntry != null) {
                    ListDumper.dumpEntry(notificationEntry, i + ":*", "\t\t  ", sb, true, notificationInteractionTracker.hasUserInteractedWith(NotificationUtils.logKey(notificationEntry)));
                }
                int i2 = 0;
                while (true) {
                    List list2 = groupEntry.mUnmodifiableChildren;
                    if (i2 < list2.size()) {
                        NotificationEntry notificationEntry2 = (NotificationEntry) list2.get(i2);
                        ListDumper.dumpEntry(notificationEntry2, i + "." + i2, "\t\t  ", sb, true, notificationInteractionTracker.hasUserInteractedWith(NotificationUtils.logKey(notificationEntry2)));
                        i2++;
                    }
                }
            }
        }
        printWriter.println(sb.toString());
    }

    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    public final void dumpPipeline(PipelineDumper pipelineDumper) {
        pipelineDumper.dump(this.mChoreographer, "choreographer");
        pipelineDumper.dump(this.mNotifPreGroupFilters, "notifPreGroupFilters");
        pipelineDumper.dump(this.mOnBeforeTransformGroupsListeners, "onBeforeTransformGroupsListeners");
        pipelineDumper.dump(this.mNotifPromoters, "notifPromoters");
        pipelineDumper.dump(this.mOnBeforeSortListeners, "onBeforeSortListeners");
        pipelineDumper.dump(this.mNotifSections, "notifSections");
        pipelineDumper.dump(this.mNotifComparators, "notifComparators");
        pipelineDumper.dump(this.mOnBeforeFinalizeFilterListeners, "onBeforeFinalizeFilterListeners");
        pipelineDumper.dump(this.mNotifFinalizeFilters, "notifFinalizeFilters");
        pipelineDumper.dump(this.mOnBeforeRenderListListeners, "onBeforeRenderListListeners");
        pipelineDumper.dump(this.mOnRenderListListener, "onRenderListListener");
    }

    public final void filterNotifs(Collection collection, List list, List list2) {
        Trace.beginSection("ShadeListBuilder.filterNotifs");
        ((SystemClockImpl) this.mSystemClock).getClass();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (applyFilters(notificationEntry, uptimeMillis, list2)) {
                    groupEntry.mSummary = null;
                    annulAddition(notificationEntry);
                }
                ArrayList arrayList = (ArrayList) groupEntry.mChildren;
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.get(size);
                    if (applyFilters(notificationEntry2, uptimeMillis, list2)) {
                        arrayList.remove(notificationEntry2);
                        annulAddition(notificationEntry2);
                    }
                }
                list.add(groupEntry);
            } else if (applyFilters((NotificationEntry) listEntry, uptimeMillis, list2)) {
                annulAddition(listEntry);
            } else {
                list.add(listEntry);
            }
        }
        Trace.endSection();
    }

    public final NotifStabilityManager getStabilityManager() {
        NotifStabilityManager notifStabilityManager = this.mNotifStabilityManager;
        return notifStabilityManager == null ? DefaultNotifStabilityManager.INSTANCE : notifStabilityManager;
    }

    public final void logAttachStateChanges(ListEntry listEntry) {
        NotifSection notifSection;
        NotifSection notifSection2;
        NotifPromoter notifPromoter;
        NotifPromoter notifPromoter2;
        ListAttachState listAttachState = listEntry.mAttachState;
        ListEntry.checkNull(listAttachState);
        ListAttachState listAttachState2 = listEntry.mPreviousAttachState;
        ListEntry.checkNull(listAttachState2);
        if (Objects.equals(listAttachState, listAttachState2)) {
            return;
        }
        int i = this.mIterationCount;
        GroupEntry groupEntry = listAttachState2.parent;
        GroupEntry groupEntry2 = listAttachState.parent;
        ShadeListBuilderLogger shadeListBuilderLogger = this.mLogger;
        shadeListBuilderLogger.logEntryAttachStateChanged(i, listEntry, groupEntry, groupEntry2);
        GroupEntry groupEntry3 = listAttachState.parent;
        GroupEntry groupEntry4 = listAttachState2.parent;
        if (groupEntry3 != groupEntry4) {
            shadeListBuilderLogger.logParentChanged(this.mIterationCount, groupEntry4, groupEntry3);
        }
        SuppressedAttachState suppressedAttachState = listAttachState.suppressedChanges;
        GroupEntry groupEntry5 = suppressedAttachState.parent;
        GroupEntry groupEntry6 = listAttachState2.suppressedChanges.parent;
        if (groupEntry5 != null && (groupEntry6 == null || !groupEntry6.mKey.equals(groupEntry5.mKey))) {
            shadeListBuilderLogger.logParentChangeSuppressedStarted(this.mIterationCount, groupEntry5, listAttachState.parent);
        }
        if (groupEntry6 != null && groupEntry5 == null) {
            shadeListBuilderLogger.logParentChangeSuppressedStopped(this.mIterationCount, groupEntry6, listAttachState2.parent);
        }
        NotifSection notifSection3 = suppressedAttachState.section;
        if (notifSection3 != null) {
            shadeListBuilderLogger.logSectionChangeSuppressed(this.mIterationCount, notifSection3, listAttachState.section);
        }
        if (suppressedAttachState.wasPruneSuppressed) {
            shadeListBuilderLogger.logGroupPruningSuppressed(this.mIterationCount, listAttachState.parent);
        }
        if (!Objects.equals(listAttachState.groupPruneReason, listAttachState2.groupPruneReason)) {
            shadeListBuilderLogger.logPrunedReasonChanged(this.mIterationCount, listAttachState2.groupPruneReason, listAttachState.groupPruneReason);
        }
        NotifFilter notifFilter = listAttachState.excludingFilter;
        NotifFilter notifFilter2 = listAttachState2.excludingFilter;
        if (notifFilter != notifFilter2) {
            shadeListBuilderLogger.logFilterChanged(this.mIterationCount, notifFilter2, notifFilter);
        }
        boolean z = listAttachState.parent == null && listAttachState2.parent != null;
        if (!z && (notifPromoter = listAttachState.promoter) != (notifPromoter2 = listAttachState2.promoter)) {
            shadeListBuilderLogger.logPromoterChanged(this.mIterationCount, notifPromoter2, notifPromoter);
        }
        if (z || (notifSection = listAttachState.section) == (notifSection2 = listAttachState2.section)) {
            return;
        }
        shadeListBuilderLogger.logSectionChanged(this.mIterationCount, notifSection2, notifSection);
    }

    public final boolean maybeSuppressGroupChange(NotificationEntry notificationEntry, List list) {
        ListAttachState listAttachState = notificationEntry.mPreviousAttachState;
        ListEntry.checkNull(listAttachState);
        GroupEntry groupEntry = listAttachState.parent;
        if (groupEntry == null || groupEntry == notificationEntry.getParent()) {
            return false;
        }
        if (groupEntry != GroupEntry.ROOT_ENTRY && groupEntry.getParent() == null) {
            return false;
        }
        getStabilityManager().isGroupChangeAllowed();
        return false;
    }

    public final void pruneGroupAtIndexAndPromoteAnyChildren(List list, GroupEntry groupEntry, int i) {
        Preconditions.checkState(((ListEntry) list.remove(i)) == groupEntry);
        List list2 = groupEntry.mChildren;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        boolean z = notificationEntry != null;
        PipelineState pipelineState = this.mPipelineState;
        if (z) {
            groupEntry.mSummary = null;
            annulAddition(list, notificationEntry);
            ListAttachState listAttachState = notificationEntry.mAttachState;
            ListEntry.checkNull(listAttachState);
            listAttachState.groupPruneReason = "SUMMARY with too few children @ " + PipelineState.getStateName(pipelineState.mState);
        }
        ArrayList arrayList = (ArrayList) list2;
        if (!arrayList.isEmpty()) {
            String str = z ? "CHILD with " + (arrayList.size() - 1) + " siblings @ " + PipelineState.getStateName(pipelineState.mState) : "CHILD with no summary @ " + PipelineState.getStateName(pipelineState.mState);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.get(i2);
                notificationEntry2.setParent(GroupEntry.ROOT_ENTRY);
                ListAttachState listAttachState2 = notificationEntry2.mAttachState;
                ListEntry.checkNull(listAttachState2);
                Objects.requireNonNull(str);
                listAttachState2.groupPruneReason = str;
            }
            list.addAll(i, arrayList);
            arrayList.clear();
        }
        annulAddition(list, groupEntry);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.util.ArraySet] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.util.Collection, java.util.Set] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.util.Set] */
    public final void pruneIncompleteGroups(List list) {
        ?? arraySet;
        Trace.beginSection("ShadeListBuilder.pruneIncompleteGroups");
        if (getStabilityManager().isEveryChangeAllowed()) {
            arraySet = Collections.emptySet();
        } else {
            arraySet = new ArraySet();
            for (int i = 0; i < list.size(); i++) {
                ListAttachState listAttachState = ((ListEntry) list.get(i)).mAttachState;
                ListEntry.checkNull(listAttachState);
                GroupEntry groupEntry = listAttachState.suppressedChanges.parent;
                if (groupEntry != null) {
                    arraySet.add(groupEntry.mKey);
                }
            }
        }
        ArraySet arraySet2 = new ArraySet((Collection) arraySet);
        for (ListEntry listEntry : this.mAllEntries) {
            StatusBarNotification statusBarNotification = listEntry.getRepresentativeEntry().mSbn;
            if (statusBarNotification.isGroup() && !statusBarNotification.getNotification().isGroupSummary()) {
                ListAttachState listAttachState2 = listEntry.mAttachState;
                ListEntry.checkNull(listAttachState2);
                if (listAttachState2.excludingFilter != null) {
                    arraySet2.add(statusBarNotification.getGroupKey());
                }
            }
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ListEntry listEntry2 = (ListEntry) list.get(i2);
            ListAttachState listAttachState3 = listEntry2.mAttachState;
            ListEntry.checkNull(listAttachState3);
            if (listAttachState3.promoter != null) {
                arraySet2.add(listEntry2.getRepresentativeEntry().mSbn.getGroupKey());
            }
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            ListEntry listEntry3 = (ListEntry) list.get(size);
            if (listEntry3 instanceof GroupEntry) {
                GroupEntry groupEntry2 = (GroupEntry) listEntry3;
                List list2 = groupEntry2.mChildren;
                boolean z = groupEntry2.mSummary != null;
                String str = groupEntry2.mKey;
                if (z && ((ArrayList) list2).size() == 0) {
                    if (arraySet2.contains(str)) {
                        pruneGroupAtIndexAndPromoteAnyChildren(list, groupEntry2, size);
                    } else {
                        Preconditions.checkArgument(groupEntry2.mUnmodifiableChildren.isEmpty(), "group should have no children");
                        NotificationEntry notificationEntry = groupEntry2.mSummary;
                        notificationEntry.setParent(GroupEntry.ROOT_ENTRY);
                        Preconditions.checkState(((ListEntry) list.set(size, notificationEntry)) == groupEntry2);
                        groupEntry2.mSummary = null;
                        annulAddition(list, groupEntry2);
                        ListAttachState listAttachState4 = notificationEntry.mAttachState;
                        ListEntry.checkNull(listAttachState4);
                        listAttachState4.groupPruneReason = "SUMMARY with no children @ " + PipelineState.getStateName(this.mPipelineState.mState);
                    }
                } else if (z) {
                    if (((ArrayList) list2).size() < 2) {
                        Preconditions.checkState(z, "group must have summary at this point");
                        Preconditions.checkState(!r6.isEmpty(), "empty group should have been promoted");
                        boolean contains = arraySet.contains(str);
                        ListAttachState listAttachState5 = groupEntry2.mAttachState;
                        if (contains) {
                            ListEntry.checkNull(listAttachState5);
                            listAttachState5.suppressedChanges.wasPruneSuppressed = true;
                        } else if (!groupEntry2.wasAttachedInPreviousPass() || getStabilityManager().isGroupPruneAllowed()) {
                            pruneGroupAtIndexAndPromoteAnyChildren(list, groupEntry2, size);
                        } else {
                            Preconditions.checkState(!r6.isEmpty(), "empty group should have been pruned");
                            ListEntry.checkNull(listAttachState5);
                            listAttachState5.suppressedChanges.wasPruneSuppressed = true;
                        }
                    }
                } else {
                    pruneGroupAtIndexAndPromoteAnyChildren(list, groupEntry2, size);
                }
            }
        }
        Trace.endSection();
    }

    public final void rebuildListIfBefore(int i) {
        int i2 = this.mPipelineState.mState;
        if (i2 == 0) {
            scheduleRebuild(i, false);
        } else {
            if (i > i2) {
                return;
            }
            scheduleRebuild(i, true);
        }
    }

    public final void scheduleRebuild(int i, boolean z) {
        NotifPipelineChoreographer notifPipelineChoreographer = this.mChoreographer;
        if (!z) {
            this.mConsecutiveReentrantRebuilds = 0;
            ((NotifPipelineChoreographerImpl) notifPipelineChoreographer).schedule();
            return;
        }
        IllegalStateException illegalStateException = new IllegalStateException(MotionLayout$$ExternalSyntheticOutline0.m22m("Reentrant notification pipeline rebuild of state ", PipelineState.getStateName(i), " while pipeline in state ", PipelineState.getStateName(this.mPipelineState.mState), "."));
        int i2 = this.mConsecutiveReentrantRebuilds + 1;
        this.mConsecutiveReentrantRebuilds = i2;
        if (i2 > 3) {
            Log.e("ShadeListBuilder", "Crashing after more than 3 consecutive reentrant notification pipeline rebuilds.", illegalStateException);
            throw illegalStateException;
        }
        Log.wtf("ShadeListBuilder", "Allowing " + this.mConsecutiveReentrantRebuilds + " consecutive reentrant notification pipeline rebuild(s).", illegalStateException);
        ((NotifPipelineChoreographerImpl) notifPipelineChoreographer).schedule();
    }

    public final void setSectioners(List list) {
        Assert.isMainThread();
        this.mPipelineState.requireState();
        ArrayList<NotifSection> arrayList = (ArrayList) this.mNotifSections;
        arrayList.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NotifSectioner notifSectioner = (NotifSectioner) it.next();
            NotifSection notifSection = new NotifSection(notifSectioner, arrayList.size());
            arrayList.add(notifSection);
            notifSectioner.mListener = new ShadeListBuilder$$ExternalSyntheticLambda0(this, 1);
            NotifComparator notifComparator = notifSection.comparator;
            if (notifComparator != null) {
                notifComparator.mListener = new ShadeListBuilder$$ExternalSyntheticLambda0(this, 2);
            }
        }
        arrayList.add(new NotifSection(DEFAULT_SECTIONER, arrayList.size()));
        ArraySet arraySet = new ArraySet();
        int i = arrayList.size() > 0 ? ((NotifSection) arrayList.get(0)).bucket : 0;
        for (NotifSection notifSection2 : arrayList) {
            int i2 = notifSection2.bucket;
            if (i != i2 && arraySet.contains(Integer.valueOf(i2))) {
                throw new IllegalStateException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("setSectioners with non contiguous sections "), notifSection2.label, " has an already seen bucket"));
            }
            i = notifSection2.bucket;
            arraySet.add(Integer.valueOf(i));
        }
    }

    public static void annulAddition(List list, ListEntry listEntry) {
        if (listEntry.getParent() != null) {
            if (listEntry.getParent() == GroupEntry.ROOT_ENTRY && list.contains(listEntry)) {
                throw new IllegalStateException("Cannot nullify addition of " + listEntry.getKey() + ": it's still in the shade list.");
            }
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                String str = groupEntry.mKey;
                if (notificationEntry == null) {
                    if (!groupEntry.mUnmodifiableChildren.isEmpty()) {
                        throw new IllegalStateException(PathParser$$ExternalSyntheticOutline0.m29m("Cannot nullify group ", str, ": still has children"));
                    }
                } else {
                    throw new IllegalStateException(PathParser$$ExternalSyntheticOutline0.m29m("Cannot nullify group ", str, ": summary is not null"));
                }
            } else if ((listEntry instanceof NotificationEntry) && (listEntry == listEntry.getParent().mSummary || listEntry.getParent().mUnmodifiableChildren.contains(listEntry))) {
                throw new IllegalStateException("Cannot nullify addition of child " + listEntry.getKey() + ": it's still attached to its parent.");
            }
            annulAddition(listEntry);
            return;
        }
        throw new IllegalStateException("Cannot nullify addition of " + listEntry.getKey() + ": no parent.");
    }
}
