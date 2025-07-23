package com.android.systemui.statusbar.notification.collection;

import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.systemui.Dumpable;
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
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.PipelineState;
import com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.DefaultNotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager$attach$1;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.util.Assert;
import com.android.systemui.util.NamedListenerSet;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).str1 = str;
            logBuffer.commit(obtain);
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
                int compare;
                ShadeListBuilder shadeListBuilder = ShadeListBuilder.this;
                PipelineEntry pipelineEntry = (PipelineEntry) obj;
                PipelineEntry pipelineEntry2 = (PipelineEntry) obj2;
                int i = ShadeListBuilder.MAX_CONSECUTIVE_REENTRANT_REBUILDS;
                NotifSection notifSection = pipelineEntry.mAttachState.section;
                int i2 = notifSection != null ? notifSection.index : -1;
                NotifSection notifSection2 = pipelineEntry2.mAttachState.section;
                int compare2 = Integer.compare(i2, notifSection2 != null ? notifSection2.index : -1);
                if (compare2 != 0) {
                    return compare2;
                }
                NotifSection notifSection3 = pipelineEntry.mAttachState.section;
                if (notifSection3 != pipelineEntry2.mAttachState.section) {
                    throw new RuntimeException("Entry ordering should only be done within sections");
                }
                NotifComparator notifComparator = notifSection3 != null ? notifSection3.comparator : null;
                if (notifComparator != null && (compare = notifComparator.compare(pipelineEntry, pipelineEntry2)) != 0) {
                    return compare;
                }
                for (int i3 = 0; i3 < ((ArrayList) shadeListBuilder.mNotifComparators).size(); i3++) {
                    int compare3 = ((NotifComparator) ((ArrayList) shadeListBuilder.mNotifComparators).get(i3)).compare(pipelineEntry, pipelineEntry2);
                    if (compare3 != 0) {
                        return compare3;
                    }
                }
                int compare4 = Integer.compare(pipelineEntry.getRepresentativeEntry().mRanking.getRank(), pipelineEntry2.getRepresentativeEntry().mRanking.getRank());
                return compare4 != 0 ? compare4 : Long.compare(pipelineEntry.getRepresentativeEntry().mSbn.getNotification().getWhen(), pipelineEntry2.getRepresentativeEntry().mSbn.getNotification().getWhen()) * (-1);
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

    /* JADX WARN: Code restructure failed: missing block: B:379:0x0823, code lost:
    
        if (r4 != false) goto L294;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void buildList() {
        /*
            Method dump skipped, instructions count: 2552
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.ShadeListBuilder.buildList():void");
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
            String num = Integer.toString(i);
            String logKey = NotificationUtils.logKey(pipelineEntry);
            NotificationInteractionTracker notificationInteractionTracker = this.mInteractionTracker;
            Boolean bool = (Boolean) ((LinkedHashMap) notificationInteractionTracker.interactions).get(logKey);
            ListDumper.dumpEntry(pipelineEntry, num, "\t\t", sb, true, bool != null ? bool.booleanValue() : false);
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
        long uptimeMillis = this.mSystemClock.uptimeMillis();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            PipelineEntry pipelineEntry = (PipelineEntry) it.next();
            if (pipelineEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) pipelineEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (applyFilters(notificationEntry, uptimeMillis, list2)) {
                    groupEntry.mSummary = null;
                    annulAddition(notificationEntry);
                }
                ArrayList arrayList = (ArrayList) groupEntry.mChildren;
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.get(size);
                    if (applyFilters(notificationEntry2, uptimeMillis, list2)) {
                        arrayList.remove(notificationEntry2);
                        annulAddition(notificationEntry2);
                    }
                }
                ((ArrayList) list).add(groupEntry);
            } else if (applyFilters((NotificationEntry) pipelineEntry, uptimeMillis, list2)) {
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
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$$ExternalSyntheticLambda0, null);
        long j = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.long1 = j;
        logMessageImpl.str1 = NotificationUtils.logKey(listEntry);
        logMessageImpl.str2 = pipelineEntry != null ? NotificationUtils.logKey(pipelineEntry) : null;
        logMessageImpl.str3 = pipelineEntry2 != null ? NotificationUtils.logKey(pipelineEntry2) : null;
        logBuffer.commit(obtain);
        PipelineEntry pipelineEntry3 = listAttachState.parent;
        PipelineEntry pipelineEntry4 = listAttachState2.parent;
        if (pipelineEntry3 != pipelineEntry4) {
            int i2 = this.mIterationCount;
            LogMessage obtain2 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(1), null);
            long j2 = i2;
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
            logMessageImpl2.long1 = j2;
            logMessageImpl2.str1 = pipelineEntry4 != null ? NotificationUtils.logKey(pipelineEntry4) : null;
            logMessageImpl2.str2 = pipelineEntry3 != null ? NotificationUtils.logKey(pipelineEntry3) : null;
            logBuffer.commit(obtain2);
        }
        SuppressedAttachState suppressedAttachState = listAttachState.suppressedChanges;
        PipelineEntry pipelineEntry5 = suppressedAttachState.parent;
        PipelineEntry pipelineEntry6 = listAttachState2.suppressedChanges.parent;
        if (pipelineEntry5 != null && (pipelineEntry6 == null || !pipelineEntry6.getKey().equals(pipelineEntry5.getKey()))) {
            int i3 = this.mIterationCount;
            PipelineEntry pipelineEntry7 = listAttachState.parent;
            LogMessage obtain3 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(18), null);
            long j3 = i3;
            LogMessageImpl logMessageImpl3 = (LogMessageImpl) obtain3;
            logMessageImpl3.long1 = j3;
            logMessageImpl3.str1 = NotificationUtils.logKey(pipelineEntry5);
            logMessageImpl3.str2 = pipelineEntry7 != null ? NotificationUtils.logKey(pipelineEntry7) : null;
            logBuffer.commit(obtain3);
        }
        if (pipelineEntry6 != null && pipelineEntry5 == null) {
            int i4 = this.mIterationCount;
            PipelineEntry pipelineEntry8 = listAttachState2.parent;
            LogMessage obtain4 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(4), null);
            long j4 = i4;
            LogMessageImpl logMessageImpl4 = (LogMessageImpl) obtain4;
            logMessageImpl4.long1 = j4;
            logMessageImpl4.str1 = NotificationUtils.logKey(pipelineEntry6);
            logMessageImpl4.str2 = pipelineEntry8 != null ? NotificationUtils.logKey(pipelineEntry8) : null;
            logBuffer.commit(obtain4);
        }
        NotifSection notifSection3 = suppressedAttachState.section;
        if (notifSection3 != null) {
            int i5 = this.mIterationCount;
            NotifSection notifSection4 = listAttachState.section;
            LogMessage obtain5 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(11), null);
            long j5 = i5;
            LogMessageImpl logMessageImpl5 = (LogMessageImpl) obtain5;
            logMessageImpl5.long1 = j5;
            logMessageImpl5.str1 = notifSection3.label;
            logMessageImpl5.str2 = notifSection4 != null ? notifSection4.label : null;
            logBuffer.commit(obtain5);
        }
        if (suppressedAttachState.wasPruneSuppressed) {
            int i6 = this.mIterationCount;
            PipelineEntry pipelineEntry9 = listAttachState.parent;
            LogMessage obtain6 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(3), null);
            long j6 = i6;
            LogMessageImpl logMessageImpl6 = (LogMessageImpl) obtain6;
            logMessageImpl6.long1 = j6;
            logMessageImpl6.str1 = pipelineEntry9 != null ? NotificationUtils.logKey(pipelineEntry9) : null;
            logBuffer.commit(obtain6);
        }
        if (!Objects.equals(listAttachState.groupPruneReason, listAttachState2.groupPruneReason)) {
            int i7 = this.mIterationCount;
            String str = listAttachState2.groupPruneReason;
            String str2 = listAttachState.groupPruneReason;
            LogMessage obtain7 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(2), null);
            long j7 = i7;
            LogMessageImpl logMessageImpl7 = (LogMessageImpl) obtain7;
            logMessageImpl7.long1 = j7;
            logMessageImpl7.str1 = str;
            logMessageImpl7.str2 = str2;
            logBuffer.commit(obtain7);
        }
        NotifFilter notifFilter = listAttachState.excludingFilter;
        NotifFilter notifFilter2 = listAttachState2.excludingFilter;
        if (notifFilter != notifFilter2) {
            int i8 = this.mIterationCount;
            LogMessage obtain8 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(12), null);
            long j8 = i8;
            LogMessageImpl logMessageImpl8 = (LogMessageImpl) obtain8;
            logMessageImpl8.long1 = j8;
            logMessageImpl8.str1 = notifFilter2 != null ? notifFilter2.getName() : null;
            logMessageImpl8.str2 = notifFilter != null ? notifFilter.getName() : null;
            logBuffer.commit(obtain8);
        }
        boolean z = listAttachState.parent == null && listAttachState2.parent != null;
        if (!z && (notifPromoter = listAttachState.promoter) != (notifPromoter2 = listAttachState2.promoter)) {
            int i9 = this.mIterationCount;
            LogMessage obtain9 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(7), null);
            long j9 = i9;
            LogMessageImpl logMessageImpl9 = (LogMessageImpl) obtain9;
            logMessageImpl9.long1 = j9;
            logMessageImpl9.str1 = notifPromoter2 != null ? notifPromoter2.getName() : null;
            logMessageImpl9.str2 = notifPromoter != null ? notifPromoter.getName() : null;
            logBuffer.commit(obtain9);
        }
        if (z || (notifSection = listAttachState.section) == (notifSection2 = listAttachState2.section)) {
            return;
        }
        int i10 = this.mIterationCount;
        LogMessage obtain10 = logBuffer.obtain("ShadeListBuilder", logLevel, new ShadeListBuilderLogger$$ExternalSyntheticLambda0(6), null);
        long j10 = i10;
        LogMessageImpl logMessageImpl10 = (LogMessageImpl) obtain10;
        logMessageImpl10.long1 = j10;
        logMessageImpl10.str1 = notifSection2 != null ? notifSection2.label : null;
        logMessageImpl10.str2 = notifSection != null ? notifSection.label : null;
        logBuffer.commit(obtain10);
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
                        boolean contains = arraySet.contains(str);
                        ListAttachState listAttachState = groupEntry.mAttachState;
                        if (contains) {
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
