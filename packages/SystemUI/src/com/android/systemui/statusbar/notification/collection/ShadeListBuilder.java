package com.android.systemui.statusbar.notification.collection;

import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.util.Preconditions;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.NotiRune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.NotificationInteractionTracker;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.PipelineState;
import com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderHelper;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.DefaultNotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager$attach$1;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager$viewRenderer$1;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.util.Assert;
import com.android.systemui.util.NamedListenerSet;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* loaded from: classes3.dex */
public class ShadeListBuilder implements Dumpable, PipelineDumpable {
    public static final AnonymousClass2 DEFAULT_SECTIONER = new NotifSectioner("UnknownSection", 0) { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder.2
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final boolean isInSection(PipelineEntry pipelineEntry) {
            return true;
        }
    };
    public static final int MAX_CONSECUTIVE_REENTRANT_REBUILDS = 3;
    public Collection mAllEntries;
    public final NotifPipelineChoreographer mChoreographer;
    public int mConsecutiveReentrantRebuilds;
    public final DumpManager mDumpManager;
    public final ShadeListBuilder$$ExternalSyntheticLambda4 mGroupChildrenComparator;
    public final Map mIdToBundleEntry;
    public final NotificationInteractionTracker mInteractionTracker;
    public int mIterationCount;
    public final ShadeListBuilderLogger mLogger;
    public final List mNotifComparators;
    public final List mNotifFinalizeFilters;
    public final List mNotifPreGroupFilters;
    public final List mNotifPromoters;
    public final List mNotifSections;
    public NotifStabilityManager mNotifStabilityManager;
    public final NamedListenerSet mOnBeforeFinalizeFilterListeners;
    public final NamedListenerSet mOnBeforeRenderListListeners;
    public final NamedListenerSet mOnBeforeSortListeners;
    public final NamedListenerSet mOnBeforeTransformGroupsListeners;
    public RenderStageManager$attach$1 mOnRenderListListener;
    public Collection mPendingEntries;
    public List mReadOnlyNewNotifList;
    public List mReadOnlyNotifList;
    public final AnonymousClass1 mReadyForBuildListener;
    public final SystemClock mSystemClock;
    public final ShadeListBuilder$$ExternalSyntheticLambda3 mTopLevelComparator;
    public final ArrayList mTempSectionMembers = new ArrayList();
    public List mNotifList = new ArrayList();
    public List mNewNotifList = new ArrayList();
    public final SemiStableSort mSemiStableSort = new SemiStableSort();
    public final ShadeListBuilder$$ExternalSyntheticLambda0 mStableOrder = new ShadeListBuilder$$ExternalSyntheticLambda0(this, 4);
    public final PipelineState mPipelineState = new PipelineState();
    public final Map mGroups = new ArrayMap();

    /* renamed from: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public final void onBuildList(Collection collection, String str) {
            Assert.isMainThread();
            ArrayList arrayList = new ArrayList(collection);
            ShadeListBuilder shadeListBuilder = ShadeListBuilder.this;
            shadeListBuilder.mPendingEntries = arrayList;
            ShadeListBuilderLogger shadeListBuilderLogger = shadeListBuilder.mLogger;
            shadeListBuilderLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            ShadeListBuilderLogger$$ExternalSyntheticLambda0 shadeListBuilderLogger$$ExternalSyntheticLambda0 = new ShadeListBuilderLogger$$ExternalSyntheticLambda0(10);
            LogBuffer logBuffer = shadeListBuilderLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = str;
            logBuffer.commit(logMessageObtain);
            shadeListBuilder.rebuildListIfBefore(1);
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda3] */
    public ShadeListBuilder(DumpManager dumpManager, NotifPipelineChoreographer notifPipelineChoreographer, NotifPipelineFlags notifPipelineFlags, NotificationInteractionTracker notificationInteractionTracker, ShadeListBuilderLogger shadeListBuilderLogger, SystemClock systemClock) {
        List list = Collections.EMPTY_LIST;
        this.mAllEntries = list;
        this.mPendingEntries = null;
        this.mIterationCount = 0;
        this.mNotifPreGroupFilters = new ArrayList();
        this.mNotifPromoters = new ArrayList();
        this.mNotifFinalizeFilters = new ArrayList();
        this.mNotifComparators = new ArrayList();
        this.mNotifSections = new ArrayList();
        this.mIdToBundleEntry = new HashMap();
        this.mOnBeforeTransformGroupsListeners = new NamedListenerSet();
        this.mOnBeforeSortListeners = new NamedListenerSet();
        this.mOnBeforeFinalizeFilterListeners = new NamedListenerSet();
        this.mOnBeforeRenderListListeners = new NamedListenerSet();
        this.mReadOnlyNotifList = Collections.unmodifiableList(this.mNotifList);
        this.mReadOnlyNewNotifList = Collections.unmodifiableList(this.mNewNotifList);
        this.mConsecutiveReentrantRebuilds = 0;
        this.mReadyForBuildListener = new AnonymousClass1();
        this.mTopLevelComparator = new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int iCompare;
                ShadeListBuilder shadeListBuilder = this.f$0;
                PipelineEntry pipelineEntry = (PipelineEntry) obj;
                PipelineEntry pipelineEntry2 = (PipelineEntry) obj2;
                int i = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                NotifSection notifSection = pipelineEntry.mAttachState.section;
                int i2 = notifSection != null ? notifSection.index : -1;
                NotifSection notifSection2 = pipelineEntry2.mAttachState.section;
                int iCompare2 = Integer.compare(i2, notifSection2 != null ? notifSection2.index : -1);
                if (iCompare2 != 0) {
                    return iCompare2;
                }
                NotifSection notifSection3 = pipelineEntry.mAttachState.section;
                if (notifSection3 != pipelineEntry2.mAttachState.section) {
                    throw new RuntimeException("Entry ordering should only be done within sections");
                }
                NotifComparator notifComparator = notifSection3 != null ? notifSection3.comparator : null;
                if (notifComparator != null && (iCompare = notifComparator.compare(pipelineEntry, pipelineEntry2)) != 0) {
                    return iCompare;
                }
                for (int i3 = 0; i3 < ((ArrayList) shadeListBuilder.mNotifComparators).size(); i3++) {
                    int iCompare3 = ((NotifComparator) ((ArrayList) shadeListBuilder.mNotifComparators).get(i3)).compare(pipelineEntry, pipelineEntry2);
                    if (iCompare3 != 0) {
                        return iCompare3;
                    }
                }
                int iCompare4 = Integer.compare(pipelineEntry.getRepresentativeEntry().mRanking.getRank(), pipelineEntry2.getRepresentativeEntry().mRanking.getRank());
                return iCompare4 != 0 ? iCompare4 : Long.compare(pipelineEntry.getRepresentativeEntry().mSbn.getNotification().getWhen(), pipelineEntry2.getRepresentativeEntry().mSbn.getNotification().getWhen()) * (-1);
            }
        };
        this.mGroupChildrenComparator = new ShadeListBuilder$$ExternalSyntheticLambda4();
        this.mSystemClock = systemClock;
        this.mLogger = shadeListBuilderLogger;
        notifPipelineFlags.getClass();
        Flags.INSTANCE.getClass();
        notifPipelineFlags.featureFlags.getClass();
        this.mInteractionTracker = notificationInteractionTracker;
        this.mChoreographer = notifPipelineChoreographer;
        this.mDumpManager = dumpManager;
        setSectioners(list);
    }

    public static void annulAddition(PipelineEntry pipelineEntry) {
        ListAttachState listAttachState = pipelineEntry.mAttachState;
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
        notificationEntry.mAttachState.excludingFilter = notifFilter;
        if (notifFilter != null) {
            int i2 = NotificationBundleUi.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
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

    /* JADX WARN: Multi-variable type inference failed */
    public final void buildList() {
        final ShadeListBuilder$$ExternalSyntheticLambda0 shadeListBuilder$$ExternalSyntheticLambda0;
        SemiStableSort semiStableSort;
        int i;
        ArrayList arrayList;
        int i2;
        Iterator it;
        int i3;
        int i4;
        int i5;
        String str;
        String str2;
        int i6 = 2;
        int i7 = 1;
        Trace.beginSection("ShadeListBuilder.buildList");
        PipelineState pipelineState = this.mPipelineState;
        if (pipelineState.mState >= 1) {
            throw new IllegalStateException("Required state is <1 but actual state is " + pipelineState.mState);
        }
        Collection collection = this.mPendingEntries;
        if (collection != null) {
            this.mAllEntries = collection;
            this.mPendingEntries = null;
        }
        boolean zIsPipelineRunAllowed = getStabilityManager().isPipelineRunAllowed();
        String str3 = "ShadeListBuilder";
        ShadeListBuilderLogger shadeListBuilderLogger = this.mLogger;
        if (!zIsPipelineRunAllowed) {
            shadeListBuilderLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            ShadeListBuilderLogger$$ExternalSyntheticLambda0 shadeListBuilderLogger$$ExternalSyntheticLambda0 = new ShadeListBuilderLogger$$ExternalSyntheticLambda0(19);
            LogBuffer logBuffer = shadeListBuilderLogger.buffer;
            logBuffer.commit(logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$$ExternalSyntheticLambda0, null));
            Trace.endSection();
            return;
        }
        pipelineState.mState = 1;
        pipelineState.incrementTo(2);
        for (GroupEntry groupEntry : ((ArrayMap) this.mGroups).values()) {
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
        for (BundleEntry bundleEntry : ((HashMap) this.mIdToBundleEntry).values()) {
            bundleEntry.beginNewAttachState();
            ((ArrayList) bundleEntry._children).clear();
        }
        ((ArrayList) this.mNotifList).clear();
        getStabilityManager().onBeginRun();
        pipelineState.incrementTo(3);
        filterNotifs(this.mAllEntries, this.mNotifList, this.mNotifPreGroupFilters);
        pipelineState.incrementTo(4);
        List list = this.mNotifList;
        List list2 = this.mNewNotifList;
        Trace.beginSection("ShadeListBuilder.groupNotifs");
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        int i8 = 0;
        while (i8 < size) {
            Object obj = arrayList2.get(i8);
            i8 += i7;
            NotificationEntry notificationEntry2 = (NotificationEntry) ((PipelineEntry) obj);
            boolean zIsInsignificant = notificationEntry2.isInsignificant();
            SystemClock systemClock = this.mSystemClock;
            int i9 = i6;
            ListAttachState listAttachState = notificationEntry2.mAttachState;
            if (zIsInsignificant) {
                i5 = i7;
                GroupEntry groupEntry2 = (GroupEntry) ((ArrayMap) this.mGroups).get("INSIGNIFICANT");
                if (groupEntry2 == null) {
                    str = str3;
                    groupEntry2 = new GroupEntry("INSIGNIFICANT", systemClock.uptimeMillis());
                    ((ArrayMap) this.mGroups).put("INSIGNIFICANT", groupEntry2);
                } else {
                    str = str3;
                }
                ListAttachState listAttachState2 = groupEntry2.mAttachState;
                if (listAttachState2.parent == null) {
                    listAttachState2.parent = GroupEntry.ROOT_ENTRY;
                    ((ArrayList) list2).add(groupEntry2);
                }
                listAttachState.parent = groupEntry2;
                if (NotiRune.NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT) {
                    notificationEntry2.mGroupEntry = groupEntry2;
                }
                String groupKey = notificationEntry2.mSbn.getGroupKey();
                if (groupKey == null || !groupKey.contains("INSIGNIFICANT")) {
                    ((ArrayList) groupEntry2.mChildren).add(notificationEntry2);
                } else {
                    groupEntry2.mSummary = notificationEntry2;
                }
            } else {
                i5 = i7;
                str = str3;
                if (notificationEntry2.mSbn.isGroup()) {
                    String groupKey2 = notificationEntry2.mSbn.getGroupKey();
                    GroupEntry groupEntry3 = (GroupEntry) ((ArrayMap) this.mGroups).get(groupKey2);
                    if (groupEntry3 == null) {
                        int i10 = UseElapsedRealtimeForCreationTime.$r8$clinit;
                        groupEntry3 = new GroupEntry(groupKey2, systemClock.uptimeMillis());
                        ((ArrayMap) this.mGroups).put(groupKey2, groupEntry3);
                    }
                    ListAttachState listAttachState3 = groupEntry3.mAttachState;
                    if (listAttachState3.parent == null) {
                        listAttachState3.parent = GroupEntry.ROOT_ENTRY;
                        ((ArrayList) list2).add(groupEntry3);
                    }
                    listAttachState.parent = groupEntry3;
                    boolean z = NotiRune.NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT;
                    if (z) {
                        notificationEntry2.mGroupEntry = groupEntry3;
                    }
                    if (notificationEntry2.mSbn.getNotification().isGroupSummary()) {
                        NotificationEntry notificationEntry3 = groupEntry3.mSummary;
                        if (notificationEntry3 == null) {
                            groupEntry3.mSummary = notificationEntry2;
                            if (z) {
                                groupEntry3.mLogicalSummary = notificationEntry2;
                            }
                        } else {
                            int i11 = this.mIterationCount;
                            shadeListBuilderLogger.getClass();
                            LogLevel logLevel2 = LogLevel.WARNING;
                            ShadeListBuilderLogger$$ExternalSyntheticLambda0 shadeListBuilderLogger$$ExternalSyntheticLambda02 = new ShadeListBuilderLogger$$ExternalSyntheticLambda0(8);
                            LogBuffer logBuffer2 = shadeListBuilderLogger.buffer;
                            str2 = str;
                            LogMessage logMessageObtain = logBuffer2.obtain(str2, logLevel2, shadeListBuilderLogger$$ExternalSyntheticLambda02, null);
                            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                            logMessageImpl.long1 = i11;
                            logMessageImpl.str1 = NotificationUtils.logKey(groupEntry3);
                            logMessageImpl.str2 = NotificationUtils.logKey(notificationEntry3);
                            logMessageImpl.str3 = NotificationUtils.logKey(notificationEntry2);
                            logBuffer2.commit(logMessageObtain);
                            if (notificationEntry2.mSbn.getPostTime() > notificationEntry3.mSbn.getPostTime()) {
                                groupEntry3.mSummary = notificationEntry2;
                                if (z) {
                                    groupEntry3.mLogicalSummary = notificationEntry2;
                                }
                                annulAddition(list2, notificationEntry3);
                            } else {
                                annulAddition(list2, notificationEntry2);
                            }
                        }
                    } else {
                        str2 = str;
                        ((ArrayList) groupEntry3.mChildren).add(notificationEntry2);
                    }
                } else {
                    str2 = str;
                    ArrayMap arrayMap = (ArrayMap) this.mGroups;
                    String str4 = notificationEntry2.mKey;
                    if (arrayMap.containsKey(str4)) {
                        int i12 = this.mIterationCount;
                        shadeListBuilderLogger.getClass();
                        LogLevel logLevel3 = LogLevel.WARNING;
                        ShadeListBuilderLogger$$ExternalSyntheticLambda0 shadeListBuilderLogger$$ExternalSyntheticLambda03 = new ShadeListBuilderLogger$$ExternalSyntheticLambda0(13);
                        LogBuffer logBuffer3 = shadeListBuilderLogger.buffer;
                        LogMessage logMessageObtain2 = logBuffer3.obtain(str2, logLevel3, shadeListBuilderLogger$$ExternalSyntheticLambda03, null);
                        long j = i12;
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                        logMessageImpl2.long1 = j;
                        logMessageImpl2.str1 = NotificationUtils.logKey(str4);
                        logBuffer3.commit(logMessageObtain2);
                    } else {
                        listAttachState.parent = GroupEntry.ROOT_ENTRY;
                        ((ArrayList) list2).add(notificationEntry2);
                    }
                }
                str3 = str2;
                i6 = i9;
                i7 = i5;
            }
            str2 = str;
            str3 = str2;
            i6 = i9;
            i7 = i5;
        }
        final int i13 = i6;
        int i14 = i7;
        String str5 = str3;
        Trace.endSection();
        applyNewNotifList();
        pruneIncompleteGroups(this.mNotifList);
        final List list3 = this.mReadOnlyNotifList;
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeTransformGroups");
        final int i15 = 0;
        this.mOnBeforeTransformGroupsListeners.forEachTraced(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                int i16 = i15;
                List list4 = list3;
                switch (i16) {
                    case 0:
                        int i17 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                        ((OnBeforeTransformGroupsListener) obj2).onBeforeTransformGroups(list4);
                        break;
                    case 1:
                        int i18 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                        ((OnBeforeRenderListListener) obj2).onBeforeRenderList(list4);
                        break;
                    default:
                        int i19 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                        ((OnBeforeFinalizeFilterListener) obj2).onBeforeFinalizeFilter(list4);
                        break;
                }
            }
        });
        Trace.endSection();
        pipelineState.incrementTo(5);
        List list4 = this.mNotifList;
        Trace.beginSection("ShadeListBuilder.promoteNotifs");
        int i16 = 0;
        while (true) {
            final ArrayList arrayList3 = (ArrayList) list4;
            if (i16 >= arrayList3.size()) {
                break;
            }
            PipelineEntry pipelineEntry = (PipelineEntry) arrayList3.get(i16);
            if (pipelineEntry instanceof GroupEntry) {
                ((ArrayList) ((GroupEntry) pipelineEntry).mChildren).removeIf(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda12
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        NotifPromoter notifPromoter;
                        ShadeListBuilder shadeListBuilder = this.f$0;
                        List list5 = arrayList3;
                        NotificationEntry notificationEntry4 = (NotificationEntry) obj2;
                        int i17 = 0;
                        while (true) {
                            if (i17 >= ((ArrayList) shadeListBuilder.mNotifPromoters).size()) {
                                notifPromoter = null;
                                break;
                            }
                            notifPromoter = (NotifPromoter) ((ArrayList) shadeListBuilder.mNotifPromoters).get(i17);
                            if (notifPromoter.shouldPromoteToTopLevel(notificationEntry4)) {
                                break;
                            }
                            i17++;
                        }
                        ListAttachState listAttachState4 = notificationEntry4.mAttachState;
                        listAttachState4.promoter = notifPromoter;
                        boolean z2 = notifPromoter != null;
                        if (z2) {
                            listAttachState4.parent = GroupEntry.ROOT_ENTRY;
                            list5.add(notificationEntry4);
                        }
                        return z2;
                    }
                });
            }
            i16++;
        }
        Trace.endSection();
        pruneIncompleteGroups(this.mNotifList);
        pipelineState.incrementTo(6);
        List list5 = this.mNotifList;
        if (!getStabilityManager().isEveryChangeAllowed()) {
            Trace.beginSection("ShadeListBuilder.stabilizeGroupingNotifs");
            int i17 = 0;
            while (true) {
                ArrayList arrayList4 = (ArrayList) list5;
                if (i17 >= arrayList4.size()) {
                    break;
                }
                PipelineEntry pipelineEntry2 = (PipelineEntry) arrayList4.get(i17);
                if (pipelineEntry2 instanceof GroupEntry) {
                    GroupEntry groupEntry4 = (GroupEntry) pipelineEntry2;
                    List list6 = groupEntry4.mChildren;
                    int i18 = 0;
                    while (i18 < groupEntry4.mUnmodifiableChildren.size()) {
                        ArrayList arrayList5 = (ArrayList) list6;
                        if (maybeSuppressGroupChange((NotificationEntry) arrayList5.get(i18), arrayList4)) {
                            arrayList5.remove(i18);
                            i18--;
                        }
                        i18++;
                    }
                } else if (pipelineEntry2 instanceof NotificationEntry) {
                    NotificationEntry notificationEntry4 = (NotificationEntry) pipelineEntry2;
                    notificationEntry4.getClass();
                    if (maybeSuppressGroupChange(notificationEntry4, arrayList4)) {
                        arrayList4.remove(i17);
                        i17--;
                    }
                }
                i17++;
            }
            Trace.endSection();
        }
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeSort");
        this.mOnBeforeSortListeners.forEachTraced(new ShadeListBuilder$$ExternalSyntheticLambda8());
        Trace.endSection();
        pipelineState.incrementTo(7);
        Trace.beginSection("ShadeListBuilder.assignSections");
        ArrayList arrayList6 = (ArrayList) this.mNotifList;
        int size2 = arrayList6.size();
        int i19 = 0;
        while (i19 < size2) {
            Object obj2 = arrayList6.get(i19);
            i19++;
            PipelineEntry pipelineEntry3 = (PipelineEntry) obj2;
            for (int i20 = 0; i20 < ((ArrayList) this.mNotifSections).size(); i20++) {
                NotifSection notifSection = (NotifSection) ((ArrayList) this.mNotifSections).get(i20);
                if (notifSection.sectioner.isInSection(pipelineEntry3)) {
                    ListAttachState listAttachState4 = pipelineEntry3.mPreviousAttachState;
                    boolean zWasAttachedInPreviousPass = pipelineEntry3.wasAttachedInPreviousPass();
                    ListAttachState listAttachState5 = pipelineEntry3.mAttachState;
                    if (zWasAttachedInPreviousPass && notifSection != listAttachState4.section && !getStabilityManager().isSectionChangeAllowed(pipelineEntry3.getRepresentativeEntry())) {
                        listAttachState5.suppressedChanges.section = notifSection;
                        notifSection = listAttachState4.section;
                    }
                    listAttachState5.section = notifSection;
                    NotificationEntry representativeEntry = pipelineEntry3.getRepresentativeEntry();
                    if (representativeEntry != null) {
                        representativeEntry.mAttachState.section = notifSection;
                        if (notifSection != null) {
                            representativeEntry.mBucket = notifSection.bucket;
                        }
                    }
                    if (pipelineEntry3 instanceof GroupEntry) {
                        for (NotificationEntry notificationEntry5 : ((GroupEntry) pipelineEntry3).mUnmodifiableChildren) {
                            notificationEntry5.mAttachState.section = notifSection;
                            notificationEntry5.mAttachState.section = notifSection;
                            if (notifSection != null) {
                                notificationEntry5.mBucket = notifSection.bucket;
                            }
                        }
                    }
                }
            }
            throw new RuntimeException("Missing default sectioner!");
        }
        Trace.endSection();
        Trace.beginSection("ShadeListBuilder.notifySectionEntriesUpdated");
        this.mTempSectionMembers.clear();
        ArrayList arrayList7 = (ArrayList) this.mNotifSections;
        int size3 = arrayList7.size();
        int i21 = 0;
        while (i21 < size3) {
            Object obj3 = arrayList7.get(i21);
            i21++;
            NotifSection notifSection2 = (NotifSection) obj3;
            ArrayList arrayList8 = (ArrayList) this.mNotifList;
            int size4 = arrayList8.size();
            int i22 = 0;
            while (i22 < size4) {
                Object obj4 = arrayList8.get(i22);
                i22++;
                PipelineEntry pipelineEntry4 = (PipelineEntry) obj4;
                if (notifSection2 == pipelineEntry4.mAttachState.section) {
                    this.mTempSectionMembers.add(pipelineEntry4);
                }
            }
            Trace.beginSection(notifSection2.label);
            notifSection2.sectioner.onEntriesUpdated(this.mTempSectionMembers);
            Trace.endSection();
            this.mTempSectionMembers.clear();
        }
        Trace.endSection();
        Trace.beginSection("ShadeListBuilder.sortListAndGroups");
        Iterator it2 = ((ArrayList) this.mNotifList).iterator();
        int i23 = i14;
        while (true) {
            boolean zHasNext = it2.hasNext();
            shadeListBuilder$$ExternalSyntheticLambda0 = this.mStableOrder;
            semiStableSort = this.mSemiStableSort;
            if (!zHasNext) {
                break;
            }
            PipelineEntry pipelineEntry5 = (PipelineEntry) it2.next();
            if (pipelineEntry5 instanceof GroupEntry) {
                List list7 = ((GroupEntry) pipelineEntry5).mChildren;
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getNotificationSortOrderValue() == i14) {
                    it = it2;
                    i3 = i23;
                    i4 = 1;
                    i23 = i3 & i4;
                } else {
                    boolean zIsEveryChangeAllowed = getStabilityManager().isEveryChangeAllowed();
                    ShadeListBuilder$$ExternalSyntheticLambda4 shadeListBuilder$$ExternalSyntheticLambda4 = this.mGroupChildrenComparator;
                    if (zIsEveryChangeAllowed) {
                        ((ArrayList) list7).sort(shadeListBuilder$$ExternalSyntheticLambda4);
                        it = it2;
                        i3 = i23;
                        i4 = 1;
                        i23 = i3 & i4;
                    } else {
                        ((ArrayList) semiStableSort.preallocatedWorkspace$delegate.getValue()).clear();
                        ArrayList arrayList9 = (ArrayList) semiStableSort.preallocatedWorkspace$delegate.getValue();
                        List listSubList = arrayList9.isEmpty() ? arrayList9 : null;
                        if (listSubList == null) {
                            listSubList = arrayList9.subList(arrayList9.size(), arrayList9.size());
                        }
                        ArrayList arrayList10 = (ArrayList) list7;
                        int size5 = arrayList10.size();
                        int i24 = 0;
                        while (i24 < size5) {
                            Object obj5 = arrayList10.get(i24);
                            i24++;
                            if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(obj5) != null) {
                                listSubList.add(obj5);
                            }
                        }
                        if (listSubList.size() > 1) {
                            CollectionsKt__MutableCollectionsJVMKt.sortWith(listSubList, new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$sortTo$$inlined$sortBy$1
                                @Override // java.util.Comparator
                                public final int compare(Object obj6, Object obj7) {
                                    Integer rank = ((ShadeListBuilder$$ExternalSyntheticLambda0) shadeListBuilder$$ExternalSyntheticLambda0).getRank(obj6);
                                    rank.getClass();
                                    Integer rank2 = ((ShadeListBuilder$$ExternalSyntheticLambda0) shadeListBuilder$$ExternalSyntheticLambda0).getRank(obj7);
                                    rank2.getClass();
                                    return ComparisonsKt__ComparisonsKt.compareValues(rank, rank2);
                                }
                            });
                        }
                        SemiStableSort.Companion companion = SemiStableSort.Companion;
                        boolean zIsSorted = companion.isSorted(listSubList, shadeListBuilder$$ExternalSyntheticLambda4);
                        semiStableSort.getPreallocatedAdditions().clear();
                        ArrayList preallocatedAdditions = semiStableSort.getPreallocatedAdditions();
                        int size6 = arrayList10.size();
                        it = it2;
                        int i25 = 0;
                        while (i25 < size6) {
                            int i26 = i23;
                            Object obj6 = arrayList10.get(i25);
                            i25++;
                            if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(obj6) == null) {
                                preallocatedAdditions.add(obj6);
                            }
                            i23 = i26;
                        }
                        i3 = i23;
                        CollectionsKt__MutableCollectionsJVMKt.sortWith(preallocatedAdditions, shadeListBuilder$$ExternalSyntheticLambda4);
                        SemiStableSort.Companion.access$insertPreSortedElementsWithFewestMisOrderings(companion, listSubList, preallocatedAdditions, shadeListBuilder$$ExternalSyntheticLambda4);
                        semiStableSort.getPreallocatedAdditions().clear();
                        arrayList10.clear();
                        arrayList10.addAll(arrayList9);
                        i4 = zIsSorted;
                        i23 = i3 & i4;
                    }
                }
            } else {
                it = it2;
            }
            it2 = it;
            i14 = 1;
        }
        int i27 = i23;
        ((ArrayList) this.mNotifList).sort(this.mTopLevelComparator);
        if (!getStabilityManager().isEveryChangeAllowed()) {
            List list8 = this.mNotifList;
            ShadeListBuilderHelper.INSTANCE.getClass();
            ArrayList arrayList11 = new ArrayList();
            ArrayList arrayList12 = (ArrayList) list8;
            int size7 = arrayList12.size();
            Integer num = null;
            int i28 = 0;
            int i29 = 0;
            while (i28 < size7) {
                NotifSection notifSection3 = ((PipelineEntry) arrayList12.get(i28)).mAttachState.section;
                Integer numValueOf = Integer.valueOf(notifSection3 != null ? notifSection3.index : -1);
                if (num == null) {
                    num = numValueOf;
                } else {
                    if (!num.equals(numValueOf)) {
                        i2 = 1;
                        if (i28 - i29 >= 1) {
                            arrayList11.add(arrayList12.subList(i29, i28));
                        }
                        i29 = i28;
                        num = numValueOf;
                    }
                    i28 += i2;
                }
                i2 = 1;
                i28 += i2;
            }
            int i30 = 1;
            if (size7 - i29 >= 1) {
                arrayList11.add(arrayList12.subList(i29, size7));
            }
            int size8 = arrayList11.size();
            int i31 = 0;
            while (i31 < size8) {
                Object obj7 = arrayList11.get(i31);
                i31 += i30;
                List list9 = (List) obj7;
                List list10 = this.mNewNotifList;
                semiStableSort.getClass();
                ArrayList arrayList13 = (ArrayList) list10;
                List listSubList2 = arrayList13.isEmpty() ? arrayList13 : null;
                if (listSubList2 == null) {
                    listSubList2 = arrayList13.subList(arrayList13.size(), arrayList13.size());
                }
                for (Object obj8 : list9) {
                    if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(obj8) != null) {
                        listSubList2.add(obj8);
                    }
                }
                Comparator comparator = new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$stabilizeTo$$inlined$compareBy$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj9, Object obj10) {
                        Integer rank = ((ShadeListBuilder$$ExternalSyntheticLambda0) shadeListBuilder$$ExternalSyntheticLambda0).getRank(obj9);
                        rank.getClass();
                        Integer rank2 = ((ShadeListBuilder$$ExternalSyntheticLambda0) shadeListBuilder$$ExternalSyntheticLambda0).getRank(obj10);
                        rank2.getClass();
                        return ComparisonsKt__ComparisonsKt.compareValues(rank, rank2);
                    }
                };
                SemiStableSort.Companion companion2 = SemiStableSort.Companion;
                boolean zIsSorted2 = companion2.isSorted(listSubList2, comparator);
                if (!zIsSorted2) {
                    CollectionsKt__MutableCollectionsJVMKt.sortWith(listSubList2, comparator);
                }
                if (listSubList2.isEmpty()) {
                    for (Object obj9 : list9) {
                        if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(obj9) == null) {
                            listSubList2.add(obj9);
                        }
                    }
                    i = size8;
                    arrayList = arrayList11;
                } else {
                    semiStableSort.getPreallocatedAdditions().clear();
                    ArrayList preallocatedAdditions2 = semiStableSort.getPreallocatedAdditions();
                    for (Object obj10 : list9) {
                        if (shadeListBuilder$$ExternalSyntheticLambda0.getRank(obj10) == null) {
                            preallocatedAdditions2.add(obj10);
                        }
                    }
                    if (preallocatedAdditions2.isEmpty()) {
                        i = size8;
                        arrayList = arrayList11;
                    } else {
                        Lazy lazy = semiStableSort.preallocatedMapToIndex$delegate;
                        ((HashMap) lazy.getValue()).clear();
                        int i32 = 0;
                        for (Object obj11 : list9) {
                            int i33 = size8;
                            int i34 = i32 + 1;
                            if (i32 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            ((HashMap) lazy.getValue()).put(obj11, Integer.valueOf(i32));
                            size8 = i33;
                            i32 = i34;
                            arrayList11 = arrayList11;
                        }
                        i = size8;
                        arrayList = arrayList11;
                        SemiStableSort.Companion.access$insertPreSortedElementsWithFewestMisOrderings(companion2, listSubList2, preallocatedAdditions2, (Comparator) semiStableSort.preallocatedMapToIndexComparator$delegate.getValue());
                        ((HashMap) lazy.getValue()).clear();
                    }
                    semiStableSort.getPreallocatedAdditions().clear();
                }
                i27 &= zIsSorted2 ? 1 : 0;
                size8 = i;
                arrayList11 = arrayList;
                i30 = 1;
            }
            applyNewNotifList();
        }
        int i35 = i27;
        ArrayList arrayList14 = (ArrayList) this.mNotifList;
        if (arrayList14.size() != 0) {
            NotifSection notifSection4 = ((PipelineEntry) arrayList14.get(0)).mAttachState.section;
            Objects.requireNonNull(notifSection4);
            int i36 = 0;
            for (int i37 = 0; i37 < arrayList14.size(); i37++) {
                PipelineEntry pipelineEntry6 = (PipelineEntry) arrayList14.get(i37);
                NotifSection notifSection5 = pipelineEntry6.mAttachState.section;
                Objects.requireNonNull(notifSection5);
                if (notifSection5.index != notifSection4.index) {
                    notifSection4 = notifSection5;
                    i36 = 0;
                }
                int i38 = i36 + 1;
                pipelineEntry6.mAttachState.stableIndex = i36;
                if (pipelineEntry6 instanceof GroupEntry) {
                    GroupEntry groupEntry5 = (GroupEntry) pipelineEntry6;
                    NotificationEntry notificationEntry6 = groupEntry5.mSummary;
                    if (notificationEntry6 != null) {
                        notificationEntry6.mAttachState.stableIndex = i38;
                        i38 = i36 + 2;
                    }
                    Iterator it3 = groupEntry5.mUnmodifiableChildren.iterator();
                    while (it3.hasNext()) {
                        ((NotificationEntry) it3.next()).mAttachState.stableIndex = i38;
                        i38++;
                    }
                }
                i36 = i38;
            }
        }
        if (i35 == 0) {
            getStabilityManager().onEntryReorderSuppressed();
        }
        Trace.endSection();
        final List list11 = this.mReadOnlyNotifList;
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeFinalizeFilter");
        this.mOnBeforeFinalizeFilterListeners.forEachTraced(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj22) {
                int i162 = i13;
                List list42 = list11;
                switch (i162) {
                    case 0:
                        int i172 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                        ((OnBeforeTransformGroupsListener) obj22).onBeforeTransformGroups(list42);
                        break;
                    case 1:
                        int i182 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                        ((OnBeforeRenderListListener) obj22).onBeforeRenderList(list42);
                        break;
                    default:
                        int i192 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                        ((OnBeforeFinalizeFilterListener) obj22).onBeforeFinalizeFilter(list42);
                        break;
                }
            }
        });
        Trace.endSection();
        pipelineState.incrementTo(8);
        filterNotifs(this.mNotifList, this.mNewNotifList, this.mNotifFinalizeFilters);
        applyNewNotifList();
        pruneIncompleteGroups(this.mNotifList);
        pipelineState.incrementTo(9);
        Trace.beginSection("ShadeListBuilder.logChanges");
        Iterator it4 = this.mAllEntries.iterator();
        while (it4.hasNext()) {
            logAttachStateChanges((NotificationEntry) it4.next());
        }
        Iterator it5 = ((ArrayMap) this.mGroups).values().iterator();
        while (it5.hasNext()) {
            logAttachStateChanges((GroupEntry) it5.next());
        }
        Trace.endSection();
        Trace.beginSection("ShadeListBuilder.freeEmptyGroups");
        ((ArrayMap) this.mGroups).values().removeIf(new ShadeListBuilder$$ExternalSyntheticLambda5());
        Trace.endSection();
        Trace.beginSection("ShadeListBuilder.cleanupPluggables");
        callOnCleanup(this.mNotifPreGroupFilters);
        callOnCleanup(this.mNotifPromoters);
        callOnCleanup(this.mNotifFinalizeFilters);
        callOnCleanup(this.mNotifComparators);
        for (int i39 = 0; i39 < ((ArrayList) this.mNotifSections).size(); i39++) {
            NotifSection notifSection6 = (NotifSection) ((ArrayList) this.mNotifSections).get(i39);
            notifSection6.sectioner.onCleanup();
            NotifComparator notifComparator = notifSection6.comparator;
            if (notifComparator != null) {
                notifComparator.onCleanup();
            }
        }
        callOnCleanup(List.of(getStabilityManager()));
        Trace.endSection();
        final List list12 = this.mReadOnlyNotifList;
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeRenderList");
        final int i40 = 1;
        this.mOnBeforeRenderListListeners.forEachTraced(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj22) {
                int i162 = i40;
                List list42 = list12;
                switch (i162) {
                    case 0:
                        int i172 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                        ((OnBeforeTransformGroupsListener) obj22).onBeforeTransformGroups(list42);
                        break;
                    case 1:
                        int i182 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                        ((OnBeforeRenderListListener) obj22).onBeforeRenderList(list42);
                        break;
                    default:
                        int i192 = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                        ((OnBeforeFinalizeFilterListener) obj22).onBeforeFinalizeFilter(list42);
                        break;
                }
            }
        });
        Trace.endSection();
        Trace.beginSection("ShadeListBuilder.onRenderList");
        RenderStageManager$attach$1 renderStageManager$attach$1 = this.mOnRenderListListener;
        if (renderStageManager$attach$1 != null) {
            List list13 = this.mReadOnlyNotifList;
            RenderStageManager renderStageManager = renderStageManager$attach$1.$tmp0;
            renderStageManager.getClass();
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("RenderStageManager.onRenderList");
            }
            try {
                ShadeViewManager$viewRenderer$1 shadeViewManager$viewRenderer$1 = renderStageManager.viewRenderer;
                if (shadeViewManager$viewRenderer$1 != null) {
                    ShadeViewManager shadeViewManager = shadeViewManager$viewRenderer$1.this$0;
                    boolean zIsEnabled2 = Trace.isEnabled();
                    if (zIsEnabled2) {
                        TraceUtilsKt.beginSlice("ShadeViewManager.onRenderList");
                    }
                    try {
                        shadeViewManager.viewDiffer.applySpec(shadeViewManager.specBuilder.buildNodeSpec(shadeViewManager.rootController, list13));
                        Unit unit = Unit.INSTANCE;
                        if (zIsEnabled2) {
                            TraceUtilsKt.endSlice();
                        }
                        boolean zIsEnabled3 = Trace.isEnabled();
                        if (zIsEnabled3) {
                            TraceUtilsKt.beginSlice("RenderStageManager.dispatchOnAfterRenderList");
                        }
                        try {
                            ArrayList arrayList15 = (ArrayList) renderStageManager.onAfterRenderListListeners;
                            int size9 = arrayList15.size();
                            int i41 = 0;
                            while (i41 < size9) {
                                Object obj12 = arrayList15.get(i41);
                                i41++;
                                ((OnAfterRenderListListener) obj12).onAfterRenderList(list13);
                            }
                            Unit unit2 = Unit.INSTANCE;
                            if (zIsEnabled3) {
                                TraceUtilsKt.endSlice();
                            }
                            renderStageManager.dispatchOnAfterRenderGroups(shadeViewManager$viewRenderer$1, list13);
                            renderStageManager.dispatchOnAfterRenderEntries(shadeViewManager$viewRenderer$1, list13);
                            if (zIsEnabled) {
                                TraceUtilsKt.endSlice();
                            }
                        } finally {
                        }
                    } finally {
                        if (zIsEnabled2) {
                            TraceUtilsKt.endSlice();
                        }
                    }
                } else if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Trace.endSection();
        Trace.beginSection("ShadeListBuilder.logEndBuildList");
        int i42 = this.mIterationCount;
        int size10 = this.mReadOnlyNotifList.size();
        List list14 = this.mReadOnlyNotifList;
        int size11 = 0;
        for (int i43 = 0; i43 < list14.size(); i43++) {
            PipelineEntry pipelineEntry7 = (PipelineEntry) list14.get(i43);
            if (pipelineEntry7 instanceof GroupEntry) {
                size11 = ((GroupEntry) pipelineEntry7).mUnmodifiableChildren.size() + size11;
            }
        }
        boolean z2 = !getStabilityManager().isEveryChangeAllowed();
        shadeListBuilderLogger.getClass();
        LogLevel logLevel4 = LogLevel.INFO;
        ShadeListBuilderLogger$$ExternalSyntheticLambda0 shadeListBuilderLogger$$ExternalSyntheticLambda04 = new ShadeListBuilderLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer4 = shadeListBuilderLogger.buffer;
        LogMessage logMessageObtain3 = logBuffer4.obtain(str5, logLevel4, shadeListBuilderLogger$$ExternalSyntheticLambda04, null);
        long j2 = i42;
        LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain3;
        logMessageImpl3.long1 = j2;
        logMessageImpl3.int1 = size10;
        logMessageImpl3.int2 = size11;
        logMessageImpl3.bool1 = z2;
        logBuffer4.commit(logMessageObtain3);
        if (this.mIterationCount % 10 == 0) {
            Trace.beginSection("ShadeListBuilder.logFinalList");
            ArrayList arrayList16 = (ArrayList) this.mNotifList;
            if (arrayList16.isEmpty()) {
                logBuffer4.commit(logBuffer4.obtain(str5, LogLevel.DEBUG, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(14), null));
            }
            int size12 = arrayList16.size();
            for (int i44 = 0; i44 < size12; i44++) {
                PipelineEntry pipelineEntry8 = (PipelineEntry) arrayList16.get(i44);
                LogLevel logLevel5 = LogLevel.DEBUG;
                LogMessage logMessageObtain4 = logBuffer4.obtain(str5, logLevel5, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(15), null);
                LogMessageImpl logMessageImpl4 = (LogMessageImpl) logMessageObtain4;
                logMessageImpl4.int1 = i44;
                logMessageImpl4.str1 = NotificationUtilsKt.getLogKey(pipelineEntry8);
                logMessageImpl4.bool1 = false;
                NotificationEntry representativeEntry2 = pipelineEntry8.getRepresentativeEntry();
                representativeEntry2.getClass();
                logMessageImpl4.int2 = representativeEntry2.mRanking.getRank();
                logBuffer4.commit(logMessageObtain4);
                if (pipelineEntry8 instanceof GroupEntry) {
                    GroupEntry groupEntry6 = (GroupEntry) pipelineEntry8;
                    NotificationEntry notificationEntry7 = groupEntry6.mSummary;
                    if (notificationEntry7 != null) {
                        LogMessage logMessageObtain5 = logBuffer4.obtain(str5, logLevel5, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(16), null);
                        LogMessageImpl logMessageImpl5 = (LogMessageImpl) logMessageObtain5;
                        logMessageImpl5.str1 = NotificationUtils.logKey(notificationEntry7);
                        logMessageImpl5.bool1 = false;
                        logMessageImpl5.int2 = notificationEntry7.mRanking.getRank();
                        logBuffer4.commit(logMessageObtain5);
                    }
                    int size13 = groupEntry6.mUnmodifiableChildren.size();
                    for (int i45 = 0; i45 < size13; i45++) {
                        NotificationEntry notificationEntry8 = (NotificationEntry) groupEntry6.mUnmodifiableChildren.get(i45);
                        LogMessage logMessageObtain6 = logBuffer4.obtain(str5, LogLevel.DEBUG, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(17), null);
                        LogMessageImpl logMessageImpl6 = (LogMessageImpl) logMessageObtain6;
                        logMessageImpl6.int1 = i45;
                        logMessageImpl6.str1 = NotificationUtilsKt.getLogKey(notificationEntry8);
                        logMessageImpl6.bool1 = false;
                        logMessageImpl6.int2 = notificationEntry8.mRanking.getRank();
                        logBuffer4.commit(logMessageObtain6);
                    }
                }
            }
            Trace.endSection();
        }
        Trace.endSection();
        pipelineState.mState = 0;
        this.mIterationCount++;
        Trace.endSection();
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
            PipelineEntry pipelineEntry = (PipelineEntry) list.get(i);
            String string = Integer.toString(i);
            String strLogKey = NotificationUtils.logKey(pipelineEntry);
            NotificationInteractionTracker notificationInteractionTracker = this.mInteractionTracker;
            Boolean bool = (Boolean) ((LinkedHashMap) notificationInteractionTracker.interactions).get(strLogKey);
            ListDumper.dumpEntry(pipelineEntry, string, "\t\t", sb, true, bool != null ? bool.booleanValue() : false);
            if (pipelineEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (notificationEntry != null) {
                    String str = i + ":*";
                    Boolean bool2 = (Boolean) ((LinkedHashMap) notificationInteractionTracker.interactions).get(NotificationUtils.logKey(notificationEntry));
                    ListDumper.dumpEntry(notificationEntry, str, "\t\t  ", sb, true, bool2 != null ? bool2.booleanValue() : false);
                }
                List list2 = groupEntry.mUnmodifiableChildren;
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    NotificationEntry notificationEntry2 = (NotificationEntry) list2.get(i2);
                    String str2 = i + "." + i2;
                    Boolean bool3 = (Boolean) ((LinkedHashMap) notificationInteractionTracker.interactions).get(NotificationUtils.logKey(notificationEntry2));
                    ListDumper.dumpEntry(notificationEntry2, str2, "\t\t  ", sb, true, bool3 != null ? bool3.booleanValue() : false);
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
        int i = UseElapsedRealtimeForCreationTime.$r8$clinit;
        long jUptimeMillis = this.mSystemClock.uptimeMillis();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            PipelineEntry pipelineEntry = (PipelineEntry) it.next();
            if (pipelineEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (applyFilters(notificationEntry, jUptimeMillis, list2)) {
                    groupEntry.mSummary = null;
                    annulAddition(notificationEntry);
                }
                ArrayList arrayList = (ArrayList) groupEntry.mChildren;
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.get(size);
                    if (applyFilters(notificationEntry2, jUptimeMillis, list2)) {
                        arrayList.remove(notificationEntry2);
                        annulAddition(notificationEntry2);
                    }
                }
                ((ArrayList) list).add(groupEntry);
            } else if (applyFilters((NotificationEntry) pipelineEntry, jUptimeMillis, list2)) {
                annulAddition(pipelineEntry);
            } else {
                ((ArrayList) list).add(pipelineEntry);
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
        ListAttachState listAttachState2 = listEntry.mPreviousAttachState;
        if (Objects.equals(listAttachState, listAttachState2)) {
            return;
        }
        int i = this.mIterationCount;
        PipelineEntry pipelineEntry = listAttachState2.parent;
        PipelineEntry pipelineEntry2 = listAttachState.parent;
        ShadeListBuilderLogger shadeListBuilderLogger = this.mLogger;
        shadeListBuilderLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$$ExternalSyntheticLambda0 shadeListBuilderLogger$$ExternalSyntheticLambda0 = new ShadeListBuilderLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = shadeListBuilderLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$$ExternalSyntheticLambda0, null);
        long j = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.long1 = j;
        logMessageImpl.str1 = NotificationUtils.logKey(listEntry);
        logMessageImpl.str2 = pipelineEntry != null ? NotificationUtils.logKey(pipelineEntry) : null;
        logMessageImpl.str3 = pipelineEntry2 != null ? NotificationUtils.logKey(pipelineEntry2) : null;
        logBuffer.commit(logMessageObtain);
        PipelineEntry pipelineEntry3 = listAttachState.parent;
        PipelineEntry pipelineEntry4 = listAttachState2.parent;
        if (pipelineEntry3 != pipelineEntry4) {
            int i2 = this.mIterationCount;
            LogMessage logMessageObtain2 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(1), null);
            long j2 = i2;
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
            logMessageImpl2.long1 = j2;
            logMessageImpl2.str1 = pipelineEntry4 != null ? NotificationUtils.logKey(pipelineEntry4) : null;
            logMessageImpl2.str2 = pipelineEntry3 != null ? NotificationUtils.logKey(pipelineEntry3) : null;
            logBuffer.commit(logMessageObtain2);
        }
        SuppressedAttachState suppressedAttachState = listAttachState.suppressedChanges;
        PipelineEntry pipelineEntry5 = suppressedAttachState.parent;
        PipelineEntry pipelineEntry6 = listAttachState2.suppressedChanges.parent;
        if (pipelineEntry5 != null && (pipelineEntry6 == null || !pipelineEntry6.getKey().equals(pipelineEntry5.getKey()))) {
            int i3 = this.mIterationCount;
            PipelineEntry pipelineEntry7 = listAttachState.parent;
            LogMessage logMessageObtain3 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(18), null);
            long j3 = i3;
            LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain3;
            logMessageImpl3.long1 = j3;
            logMessageImpl3.str1 = NotificationUtils.logKey(pipelineEntry5);
            logMessageImpl3.str2 = pipelineEntry7 != null ? NotificationUtils.logKey(pipelineEntry7) : null;
            logBuffer.commit(logMessageObtain3);
        }
        if (pipelineEntry6 != null && pipelineEntry5 == null) {
            int i4 = this.mIterationCount;
            PipelineEntry pipelineEntry8 = listAttachState2.parent;
            LogMessage logMessageObtain4 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(4), null);
            long j4 = i4;
            LogMessageImpl logMessageImpl4 = (LogMessageImpl) logMessageObtain4;
            logMessageImpl4.long1 = j4;
            logMessageImpl4.str1 = NotificationUtils.logKey(pipelineEntry6);
            logMessageImpl4.str2 = pipelineEntry8 != null ? NotificationUtils.logKey(pipelineEntry8) : null;
            logBuffer.commit(logMessageObtain4);
        }
        NotifSection notifSection3 = suppressedAttachState.section;
        if (notifSection3 != null) {
            int i5 = this.mIterationCount;
            NotifSection notifSection4 = listAttachState.section;
            LogMessage logMessageObtain5 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(11), null);
            long j5 = i5;
            LogMessageImpl logMessageImpl5 = (LogMessageImpl) logMessageObtain5;
            logMessageImpl5.long1 = j5;
            logMessageImpl5.str1 = notifSection3.label;
            logMessageImpl5.str2 = notifSection4 != null ? notifSection4.label : null;
            logBuffer.commit(logMessageObtain5);
        }
        if (suppressedAttachState.wasPruneSuppressed) {
            int i6 = this.mIterationCount;
            PipelineEntry pipelineEntry9 = listAttachState.parent;
            LogMessage logMessageObtain6 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(3), null);
            long j6 = i6;
            LogMessageImpl logMessageImpl6 = (LogMessageImpl) logMessageObtain6;
            logMessageImpl6.long1 = j6;
            logMessageImpl6.str1 = pipelineEntry9 != null ? NotificationUtils.logKey(pipelineEntry9) : null;
            logBuffer.commit(logMessageObtain6);
        }
        if (!Objects.equals(listAttachState.groupPruneReason, listAttachState2.groupPruneReason)) {
            int i7 = this.mIterationCount;
            String str = listAttachState2.groupPruneReason;
            String str2 = listAttachState.groupPruneReason;
            LogMessage logMessageObtain7 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(2), null);
            long j7 = i7;
            LogMessageImpl logMessageImpl7 = (LogMessageImpl) logMessageObtain7;
            logMessageImpl7.long1 = j7;
            logMessageImpl7.str1 = str;
            logMessageImpl7.str2 = str2;
            logBuffer.commit(logMessageObtain7);
        }
        NotifFilter notifFilter = listAttachState.excludingFilter;
        NotifFilter notifFilter2 = listAttachState2.excludingFilter;
        if (notifFilter != notifFilter2) {
            int i8 = this.mIterationCount;
            LogMessage logMessageObtain8 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(12), null);
            long j8 = i8;
            LogMessageImpl logMessageImpl8 = (LogMessageImpl) logMessageObtain8;
            logMessageImpl8.long1 = j8;
            logMessageImpl8.str1 = notifFilter2 != null ? notifFilter2.getName() : null;
            logMessageImpl8.str2 = notifFilter != null ? notifFilter.getName() : null;
            logBuffer.commit(logMessageObtain8);
        }
        boolean z = listAttachState.parent == null && listAttachState2.parent != null;
        if (!z && (notifPromoter = listAttachState.promoter) != (notifPromoter2 = listAttachState2.promoter)) {
            int i9 = this.mIterationCount;
            LogMessage logMessageObtain9 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(7), null);
            long j9 = i9;
            LogMessageImpl logMessageImpl9 = (LogMessageImpl) logMessageObtain9;
            logMessageImpl9.long1 = j9;
            logMessageImpl9.str1 = notifPromoter2 != null ? notifPromoter2.getName() : null;
            logMessageImpl9.str2 = notifPromoter != null ? notifPromoter.getName() : null;
            logBuffer.commit(logMessageObtain9);
        }
        if (z || (notifSection = listAttachState.section) == (notifSection2 = listAttachState2.section)) {
            return;
        }
        int i10 = this.mIterationCount;
        LogMessage logMessageObtain10 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(6), null);
        long j10 = i10;
        LogMessageImpl logMessageImpl10 = (LogMessageImpl) logMessageObtain10;
        logMessageImpl10.long1 = j10;
        logMessageImpl10.str1 = notifSection2 != null ? notifSection2.label : null;
        logMessageImpl10.str2 = notifSection != null ? notifSection.label : null;
        logBuffer.commit(logMessageObtain10);
    }

    public final boolean maybeSuppressGroupChange(NotificationEntry notificationEntry, List list) {
        ListAttachState listAttachState;
        PipelineEntry pipelineEntry;
        PipelineEntry pipelineEntry2 = notificationEntry.mPreviousAttachState.parent;
        if (pipelineEntry2 == null || pipelineEntry2 == (pipelineEntry = (listAttachState = notificationEntry.mAttachState).parent)) {
            return false;
        }
        GroupEntry groupEntry = GroupEntry.ROOT_ENTRY;
        if ((pipelineEntry2 != groupEntry && pipelineEntry2.getParent() == null) || getStabilityManager().isGroupChangeAllowed(notificationEntry)) {
            return false;
        }
        listAttachState.suppressedChanges.parent = pipelineEntry;
        listAttachState.parent = pipelineEntry2;
        if (pipelineEntry2 == groupEntry) {
            ((ArrayList) list).add(notificationEntry);
            return true;
        }
        if (!(pipelineEntry2 instanceof GroupEntry)) {
            return true;
        }
        GroupEntry groupEntry2 = (GroupEntry) pipelineEntry2;
        ((ArrayList) groupEntry2.mChildren).add(notificationEntry);
        if (((ArrayMap) this.mGroups).containsKey(pipelineEntry2.getKey())) {
            return true;
        }
        ((ArrayMap) this.mGroups).put(pipelineEntry2.getKey(), groupEntry2);
        return true;
    }

    public final void pruneGroupAtIndexAndPromoteAnyChildren(List list, GroupEntry groupEntry, int i) {
        ArrayList arrayList = (ArrayList) list;
        Preconditions.checkState(((PipelineEntry) arrayList.remove(i)) == groupEntry);
        List list2 = groupEntry.mChildren;
        NotificationEntry notificationEntry = groupEntry.mSummary;
        boolean z = notificationEntry != null;
        PipelineState pipelineState = this.mPipelineState;
        if (z) {
            groupEntry.mSummary = null;
            annulAddition(arrayList, notificationEntry);
            notificationEntry.mAttachState.groupPruneReason = "SUMMARY with too few children @ " + PipelineState.getStateName(pipelineState.mState);
        }
        ArrayList arrayList2 = (ArrayList) list2;
        if (!arrayList2.isEmpty()) {
            String str = z ? "CHILD with " + (arrayList2.size() - 1) + " siblings @ " + PipelineState.getStateName(pipelineState.mState) : "CHILD with no summary @ " + PipelineState.getStateName(pipelineState.mState);
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                NotificationEntry notificationEntry2 = (NotificationEntry) arrayList2.get(i2);
                GroupEntry groupEntry2 = GroupEntry.ROOT_ENTRY;
                ListAttachState listAttachState = notificationEntry2.mAttachState;
                listAttachState.parent = groupEntry2;
                Objects.requireNonNull(str);
                listAttachState.groupPruneReason = str;
            }
            arrayList.addAll(i, arrayList2);
            arrayList2.clear();
        }
        annulAddition(arrayList, groupEntry);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.util.ArraySet] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.util.Collection, java.util.Set] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.util.Set] */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.android.systemui.statusbar.notification.collection.ShadeListBuilder] */
    public final void pruneIncompleteGroups(List list) {
        ?? arraySet;
        ArrayList arrayList;
        Trace.beginSection("ShadeListBuilder.pruneIncompleteGroups");
        if (!getStabilityManager().isEveryChangeAllowed()) {
            arraySet = new ArraySet();
            int i = 0;
            while (true) {
                ArrayList arrayList2 = (ArrayList) list;
                if (i >= arrayList2.size()) {
                    break;
                }
                PipelineEntry pipelineEntry = ((PipelineEntry) arrayList2.get(i)).mAttachState.suppressedChanges.parent;
                if (pipelineEntry != null) {
                    arraySet.add(pipelineEntry.getKey());
                }
                i++;
            }
        } else {
            arraySet = Collections.EMPTY_SET;
        }
        ArraySet arraySet2 = new ArraySet((Collection) arraySet);
        for (PipelineEntry pipelineEntry2 : this.mAllEntries) {
            StatusBarNotification statusBarNotification = pipelineEntry2.getRepresentativeEntry().mSbn;
            if (statusBarNotification.isGroup() && !statusBarNotification.getNotification().isGroupSummary() && pipelineEntry2.mAttachState.excludingFilter != null) {
                arraySet2.add(statusBarNotification.getGroupKey());
            }
        }
        int i2 = 0;
        while (true) {
            arrayList = (ArrayList) list;
            if (i2 >= arrayList.size()) {
                break;
            }
            PipelineEntry pipelineEntry3 = (PipelineEntry) arrayList.get(i2);
            if (pipelineEntry3.mAttachState.promoter != null) {
                arraySet2.add(pipelineEntry3.getRepresentativeEntry().mSbn.getGroupKey());
            }
            i2++;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            PipelineEntry pipelineEntry4 = (PipelineEntry) arrayList.get(size);
            if (pipelineEntry4 instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) pipelineEntry4;
                List list2 = groupEntry.mChildren;
                boolean z = groupEntry.mSummary != null;
                String str = groupEntry.mKey;
                if (z && ((ArrayList) list2).size() == 0) {
                    if (arraySet2.contains(str)) {
                        pruneGroupAtIndexAndPromoteAnyChildren(arrayList, groupEntry, size);
                    } else {
                        Preconditions.checkArgument(groupEntry.mUnmodifiableChildren.isEmpty(), "group should have no children");
                        NotificationEntry notificationEntry = groupEntry.mSummary;
                        notificationEntry.mAttachState.parent = GroupEntry.ROOT_ENTRY;
                        Preconditions.checkState(((PipelineEntry) arrayList.set(size, notificationEntry)) == groupEntry);
                        groupEntry.mSummary = null;
                        annulAddition(arrayList, groupEntry);
                        notificationEntry.mAttachState.groupPruneReason = "SUMMARY with no children @ " + PipelineState.getStateName(this.mPipelineState.mState);
                    }
                } else if (z) {
                    if (((ArrayList) list2).size() < 2 && !str.contains("INSIGNIFICANT")) {
                        Preconditions.checkState(z, "group must have summary at this point");
                        Preconditions.checkState(!r6.isEmpty(), "empty group should have been promoted");
                        boolean zContains = arraySet.contains(str);
                        ListAttachState listAttachState = groupEntry.mAttachState;
                        if (zContains) {
                            listAttachState.suppressedChanges.wasPruneSuppressed = true;
                        } else if (!groupEntry.wasAttachedInPreviousPass() || getStabilityManager().isGroupPruneAllowed(groupEntry)) {
                            pruneGroupAtIndexAndPromoteAnyChildren(arrayList, groupEntry, size);
                        } else {
                            Preconditions.checkState(!r6.isEmpty(), "empty group should have been pruned");
                            listAttachState.suppressedChanges.wasPruneSuppressed = true;
                        }
                    }
                } else {
                    pruneGroupAtIndexAndPromoteAnyChildren(arrayList, groupEntry, size);
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
        IllegalStateException illegalStateException = new IllegalStateException(MotionLayout$$ExternalSyntheticOutline0.m("Reentrant notification pipeline rebuild of state ", PipelineState.getStateName(i), " while pipeline in state ", PipelineState.getStateName(this.mPipelineState.mState), "."));
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
        ((ArrayList) this.mNotifSections).clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NotifSectioner notifSectioner = (NotifSectioner) it.next();
            NotifSection notifSection = new NotifSection(notifSectioner, ((ArrayList) this.mNotifSections).size());
            ((ArrayList) this.mNotifSections).add(notifSection);
            notifSectioner.setInvalidationListener(new ShadeListBuilder$$ExternalSyntheticLambda0(this, 5));
            NotifComparator notifComparator = notifSection.comparator;
            if (notifComparator != null) {
                notifComparator.setInvalidationListener(new ShadeListBuilder$$ExternalSyntheticLambda0(this, 6));
            }
        }
        ArrayList arrayList = (ArrayList) this.mNotifSections;
        arrayList.add(new NotifSection(DEFAULT_SECTIONER, arrayList.size()));
        ArraySet arraySet = new ArraySet();
        int i = 0;
        int i2 = ((ArrayList) this.mNotifSections).size() > 0 ? ((NotifSection) ((ArrayList) this.mNotifSections).get(0)).bucket : 0;
        ArrayList arrayList2 = (ArrayList) this.mNotifSections;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            NotifSection notifSection2 = (NotifSection) obj;
            int i3 = notifSection2.bucket;
            if (i2 != i3 && arraySet.contains(Integer.valueOf(i3))) {
                throw new IllegalStateException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("setSectioners with non contiguous sections "), notifSection2.label, " has an already seen bucket"));
            }
            i2 = notifSection2.bucket;
            arraySet.add(Integer.valueOf(i2));
        }
    }

    public static void annulAddition(List list, PipelineEntry pipelineEntry) {
        if (pipelineEntry.getParent() != null) {
            if (pipelineEntry.getParent() == GroupEntry.ROOT_ENTRY && ((ArrayList) list).contains(pipelineEntry)) {
                throw new IllegalStateException("Cannot nullify addition of " + pipelineEntry.getKey() + ": it's still in the shade list.");
            }
            if (pipelineEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                String str = groupEntry.mKey;
                if (notificationEntry == null) {
                    if (!groupEntry.mUnmodifiableChildren.isEmpty()) {
                        throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot nullify group ", str, ": still has children"));
                    }
                } else {
                    throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot nullify group ", str, ": summary is not null"));
                }
            } else if ((pipelineEntry instanceof NotificationEntry) && (pipelineEntry.getParent() instanceof GroupEntry)) {
                GroupEntry groupEntry2 = (GroupEntry) pipelineEntry.getParent();
                if (pipelineEntry == groupEntry2.mSummary || groupEntry2.mUnmodifiableChildren.contains(pipelineEntry)) {
                    throw new IllegalStateException("Cannot nullify addition of child " + pipelineEntry.getKey() + ": it's still attached to its parent.");
                }
            }
            annulAddition(pipelineEntry);
            return;
        }
        throw new IllegalStateException("Cannot nullify addition of " + pipelineEntry.getKey() + ": no parent.");
    }
}
