package com.android.systemui.statusbar.notification.collection.listbuilder;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentTransaction$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.appops.AppOpItem$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeListBuilderLogger {
    public final LogBuffer buffer;

    public ShadeListBuilderLogger(NotifPipelineFlags notifPipelineFlags, LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logDuplicateSummary(int i, GroupEntry groupEntry, NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        LogLevel logLevel = LogLevel.WARNING;
        ShadeListBuilderLogger$logDuplicateSummary$2 shadeListBuilderLogger$logDuplicateSummary$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logDuplicateSummary$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sb = new StringBuilder("(Build ");
                sb.append(long1);
                sb.append(") Duplicate summary for group \"");
                sb.append(str1);
                AppOpItem$$ExternalSyntheticOutline0.m97m(sb, "\": \"", str2, "\" vs. \"", str3);
                sb.append("\"");
                return sb.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logDuplicateSummary$2, null);
        obtain.setLong1(i);
        obtain.setStr1(NotificationUtilsKt.getLogKey(groupEntry));
        obtain.setStr2(NotificationUtilsKt.getLogKey(notificationEntry));
        obtain.setStr3(NotificationUtilsKt.getLogKey(notificationEntry2));
        logBuffer.commit(obtain);
    }

    public final void logDuplicateTopLevelKey(int i, String str) {
        LogLevel logLevel = LogLevel.WARNING;
        ShadeListBuilderLogger$logDuplicateTopLevelKey$2 shadeListBuilderLogger$logDuplicateTopLevelKey$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logDuplicateTopLevelKey$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "(Build " + logMessage.getLong1() + ") Duplicate top-level key: " + logMessage.getStr1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logDuplicateTopLevelKey$2, null);
        obtain.setLong1(i);
        obtain.setStr1(NotificationUtils.logKey(str));
        logBuffer.commit(obtain);
    }

    public final void logEndBuildList(int i, int i2, int i3, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logEndBuildList$2 shadeListBuilderLogger$logEndBuildList$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logEndBuildList$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "(Build " + logMessage.getLong1() + ") Build complete (" + logMessage.getInt1() + " top-level entries, " + logMessage.getInt2() + " children) enforcedVisualStability=" + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logEndBuildList$2, null);
        obtain.setLong1(i);
        obtain.setInt1(i2);
        obtain.setInt2(i3);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logEntryAttachStateChanged(int i, ListEntry listEntry, GroupEntry groupEntry, GroupEntry groupEntry2) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logEntryAttachStateChanged$2 shadeListBuilderLogger$logEntryAttachStateChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logEntryAttachStateChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str = (logMessage.getStr2() != null || logMessage.getStr3() == null) ? (logMessage.getStr2() == null || logMessage.getStr3() != null) ? (logMessage.getStr2() == null && logMessage.getStr3() == null) ? "MODIFIED (DETACHED)" : "MODIFIED (ATTACHED)" : "DETACHED" : "ATTACHED";
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                StringBuilder sb = new StringBuilder("(Build ");
                sb.append(long1);
                sb.append(") ");
                sb.append(str);
                return FragmentTransaction$$ExternalSyntheticOutline0.m38m(sb, " {", str1, "}");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logEntryAttachStateChanged$2, null);
        obtain.setLong1(i);
        obtain.setStr1(NotificationUtilsKt.getLogKey(listEntry));
        obtain.setStr2(groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null);
        obtain.setStr3(groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null);
        logBuffer.commit(obtain);
    }

    public final void logFilterChanged(int i, NotifFilter notifFilter, NotifFilter notifFilter2) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logFilterChanged$2 shadeListBuilderLogger$logFilterChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logFilterChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder sb = new StringBuilder("(Build ");
                sb.append(long1);
                sb.append(")     Filter changed: ");
                sb.append(str1);
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, " -> ", str2);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logFilterChanged$2, null);
        obtain.setLong1(i);
        obtain.setStr1(notifFilter != null ? notifFilter.mName : null);
        obtain.setStr2(notifFilter2 != null ? notifFilter2.mName : null);
        logBuffer.commit(obtain);
    }

    public final void logFinalList(List list) {
        boolean isEmpty = list.isEmpty();
        LogBuffer logBuffer = this.buffer;
        if (isEmpty) {
            logBuffer.commit(logBuffer.obtain("ShadeListBuilder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logFinalList$2
                @Override // kotlin.jvm.functions.Function1
                public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return "(empty list)";
                }
            }, null));
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ListEntry listEntry = (ListEntry) list.get(i);
            LogLevel logLevel = LogLevel.DEBUG;
            LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logFinalList$4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    String str = "[" + logMessage.getInt1() + "] " + logMessage.getStr1();
                    if (!logMessage.getBool1()) {
                        return str;
                    }
                    return str + " rank=" + logMessage.getInt2();
                }
            }, null);
            obtain.setInt1(i);
            obtain.setStr1(NotificationUtilsKt.getLogKey(listEntry));
            obtain.setBool1(false);
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            Intrinsics.checkNotNull(representativeEntry);
            obtain.setInt2(representativeEntry.mRanking.getRank());
            logBuffer.commit(obtain);
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (notificationEntry != null) {
                    LogMessage obtain2 = logBuffer.obtain("ShadeListBuilder", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logFinalList$5$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            String m29m = PathParser$$ExternalSyntheticOutline0.m29m("  [*] ", logMessage.getStr1(), " (summary)");
                            if (!logMessage.getBool1()) {
                                return m29m;
                            }
                            return m29m + " rank=" + logMessage.getInt2();
                        }
                    }, null);
                    obtain2.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
                    obtain2.setBool1(false);
                    obtain2.setInt2(notificationEntry.mRanking.getRank());
                    logBuffer.commit(obtain2);
                }
                List list2 = groupEntry.mUnmodifiableChildren;
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    NotificationEntry notificationEntry2 = (NotificationEntry) list2.get(i2);
                    LogMessage obtain3 = logBuffer.obtain("ShadeListBuilder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logFinalList$7
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            String str = "  [" + logMessage.getInt1() + "] " + logMessage.getStr1();
                            if (!logMessage.getBool1()) {
                                return str;
                            }
                            return str + " rank=" + logMessage.getInt2();
                        }
                    }, null);
                    obtain3.setInt1(i2);
                    obtain3.setStr1(NotificationUtilsKt.getLogKey(notificationEntry2));
                    obtain3.setBool1(false);
                    obtain3.setInt2(notificationEntry2.mRanking.getRank());
                    logBuffer.commit(obtain3);
                }
            }
        }
    }

    public final void logGroupPruningSuppressed(int i, GroupEntry groupEntry) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logGroupPruningSuppressed$2 shadeListBuilderLogger$logGroupPruningSuppressed$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logGroupPruningSuppressed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "(Build " + logMessage.getLong1() + ")     Group pruning suppressed; keeping parent '" + logMessage.getStr1() + "'";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logGroupPruningSuppressed$2, null);
        obtain.setLong1(i);
        obtain.setStr1(groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null);
        logBuffer.commit(obtain);
    }

    public final void logOnBuildList(String str) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logOnBuildList$2 shadeListBuilderLogger$logOnBuildList$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logOnBuildList$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Request received from NotifCollection for ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logOnBuildList$2, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logParentChangeSuppressedStarted(int i, GroupEntry groupEntry, GroupEntry groupEntry2) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logParentChangeSuppressedStarted$2 shadeListBuilderLogger$logParentChangeSuppressedStarted$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logParentChangeSuppressedStarted$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder sb = new StringBuilder("(Build ");
                sb.append(long1);
                sb.append(")     Change of parent to '");
                sb.append(str1);
                return FragmentTransaction$$ExternalSyntheticOutline0.m38m(sb, "' suppressed; keeping parent '", str2, "'");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logParentChangeSuppressedStarted$2, null);
        obtain.setLong1(i);
        obtain.setStr1(NotificationUtilsKt.getLogKey(groupEntry));
        obtain.setStr2(groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null);
        logBuffer.commit(obtain);
    }

    public final void logParentChangeSuppressedStopped(int i, GroupEntry groupEntry, GroupEntry groupEntry2) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logParentChangeSuppressedStopped$2 shadeListBuilderLogger$logParentChangeSuppressedStopped$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logParentChangeSuppressedStopped$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder sb = new StringBuilder("(Build ");
                sb.append(long1);
                sb.append(")     Change of parent to '");
                sb.append(str1);
                return FragmentTransaction$$ExternalSyntheticOutline0.m38m(sb, "' no longer suppressed; replaced parent '", str2, "'");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logParentChangeSuppressedStopped$2, null);
        obtain.setLong1(i);
        obtain.setStr1(NotificationUtilsKt.getLogKey(groupEntry));
        obtain.setStr2(groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null);
        logBuffer.commit(obtain);
    }

    public final void logParentChanged(int i, GroupEntry groupEntry, GroupEntry groupEntry2) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logParentChanged$2 shadeListBuilderLogger$logParentChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logParentChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                if (logMessage.getStr1() == null && logMessage.getStr2() != null) {
                    return "(Build " + logMessage.getLong1() + ")     Parent is {" + logMessage.getStr2() + "}";
                }
                if (logMessage.getStr1() == null || logMessage.getStr2() != null) {
                    long long1 = logMessage.getLong1();
                    String str1 = logMessage.getStr1();
                    String str2 = logMessage.getStr2();
                    StringBuilder sb = new StringBuilder("(Build ");
                    sb.append(long1);
                    sb.append(")     Reparent: {");
                    sb.append(str1);
                    return FragmentTransaction$$ExternalSyntheticOutline0.m38m(sb, "} -> {", str2, "}");
                }
                return "(Build " + logMessage.getLong1() + ")     Parent was {" + logMessage.getStr1() + "}";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logParentChanged$2, null);
        obtain.setLong1(i);
        obtain.setStr1(groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null);
        obtain.setStr2(groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null);
        logBuffer.commit(obtain);
    }

    public final void logPipelineRunSuppressed() {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logPipelineRunSuppressed$2 shadeListBuilderLogger$logPipelineRunSuppressed$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logPipelineRunSuppressed$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Suppressing pipeline run during animation.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logPipelineRunSuppressed$2, null));
    }

    public final void logPluggableInvalidated(String str, Pluggable pluggable, int i, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeListBuilderLogger$logPluggableInvalidated$2 shadeListBuilderLogger$logPluggableInvalidated$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logPluggableInvalidated$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String stateName = PipelineState.getStateName(logMessage.getInt1());
                String str1 = logMessage.getStr1();
                return FragmentTransaction$$ExternalSyntheticOutline0.m38m(AbstractC0866xb1ce8deb.m87m("Invalidated while ", stateName, " by ", str1, " \""), logMessage.getStr2(), "\" because ", logMessage.getStr3());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logPluggableInvalidated$2, null);
        obtain.setStr1(str);
        obtain.setStr2(pluggable.mName);
        obtain.setInt1(i);
        obtain.setStr3(str2);
        logBuffer.commit(obtain);
    }

    public final void logPromoterChanged(int i, NotifPromoter notifPromoter, NotifPromoter notifPromoter2) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logPromoterChanged$2 shadeListBuilderLogger$logPromoterChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logPromoterChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder sb = new StringBuilder("(Build ");
                sb.append(long1);
                sb.append(")     Promoter changed: ");
                sb.append(str1);
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, " -> ", str2);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logPromoterChanged$2, null);
        obtain.setLong1(i);
        obtain.setStr1(notifPromoter != null ? notifPromoter.mName : null);
        obtain.setStr2(notifPromoter2 != null ? notifPromoter2.mName : null);
        logBuffer.commit(obtain);
    }

    public final void logPrunedReasonChanged(int i, String str, String str2) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logPrunedReasonChanged$2 shadeListBuilderLogger$logPrunedReasonChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logPrunedReasonChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                StringBuilder sb = new StringBuilder("(Build ");
                sb.append(long1);
                sb.append(")     Pruned reason changed: ");
                sb.append(str1);
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, " -> ", str22);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logPrunedReasonChanged$2, null);
        obtain.setLong1(i);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logSectionChangeSuppressed(int i, NotifSection notifSection, NotifSection notifSection2) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logSectionChangeSuppressed$2 shadeListBuilderLogger$logSectionChangeSuppressed$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logSectionChangeSuppressed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder sb = new StringBuilder("(Build ");
                sb.append(long1);
                sb.append(")     Suppressing section change to ");
                sb.append(str1);
                return FragmentTransaction$$ExternalSyntheticOutline0.m38m(sb, " (staying at ", str2, ")");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logSectionChangeSuppressed$2, null);
        obtain.setLong1(i);
        obtain.setStr1(notifSection != null ? notifSection.label : null);
        obtain.setStr2(notifSection2 != null ? notifSection2.label : null);
        logBuffer.commit(obtain);
    }

    public final void logSectionChanged(int i, NotifSection notifSection, NotifSection notifSection2) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logSectionChanged$2 shadeListBuilderLogger$logSectionChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logSectionChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                if (logMessage.getStr1() == null) {
                    return "(Build " + logMessage.getLong1() + ")     Section assigned: " + logMessage.getStr2();
                }
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder sb = new StringBuilder("(Build ");
                sb.append(long1);
                sb.append(")     Section changed: ");
                sb.append(str1);
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, " -> ", str2);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logSectionChanged$2, null);
        obtain.setLong1(i);
        obtain.setStr1(notifSection != null ? notifSection.label : null);
        obtain.setStr2(notifSection2 != null ? notifSection2.label : null);
        logBuffer.commit(obtain);
    }
}
