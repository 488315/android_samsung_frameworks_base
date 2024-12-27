package com.android.systemui.statusbar.notification.collection.listbuilder;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$toString$1$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
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
                ConstraintWidget$$ExternalSyntheticOutline0.m(sb, "\": \"", str2, "\" vs. \"", str3);
                sb.append("\"");
                return sb.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logDuplicateSummary$2, null);
        long j = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.long1 = j;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(groupEntry);
        logMessageImpl.str2 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str3 = NotificationUtilsKt.getLogKey(notificationEntry2);
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
        ((LogMessageImpl) obtain).long1 = i;
        ((LogMessageImpl) obtain).str1 = NotificationUtils.logKey(str);
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
        ((LogMessageImpl) obtain).long1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i2;
        logMessageImpl.int2 = i3;
        logMessageImpl.bool1 = z;
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
                return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(sb, " {", str1, "}");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logEntryAttachStateChanged$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        String logKey = NotificationUtilsKt.getLogKey(listEntry);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = logKey;
        logMessageImpl.str2 = groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null;
        logMessageImpl.str3 = groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null;
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
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, " -> ", str2);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logFilterChanged$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        String name = notifFilter != null ? notifFilter.getName() : null;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = name;
        logMessageImpl.str2 = notifFilter2 != null ? notifFilter2.getName() : null;
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
                    String m = BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "[", "] ", logMessage.getStr1());
                    if (!logMessage.getBool1()) {
                        return m;
                    }
                    return m + " rank=" + logMessage.getInt2();
                }
            }, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.int1 = i;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(listEntry);
            logMessageImpl.bool1 = false;
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            Intrinsics.checkNotNull(representativeEntry);
            logMessageImpl.int2 = representativeEntry.mRanking.getRank();
            logBuffer.commit(obtain);
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry notificationEntry = groupEntry.mSummary;
                if (notificationEntry != null) {
                    LogMessage obtain2 = logBuffer.obtain("ShadeListBuilder", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logFinalList$5$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            String m = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("  [*] ", logMessage.getStr1(), " (summary)");
                            if (!logMessage.getBool1()) {
                                return m;
                            }
                            return m + " rank=" + logMessage.getInt2();
                        }
                    }, null);
                    LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                    logMessageImpl2.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                    logMessageImpl2.bool1 = false;
                    logMessageImpl2.int2 = notificationEntry.mRanking.getRank();
                    logBuffer.commit(obtain2);
                }
                int size2 = groupEntry.mUnmodifiableChildren.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    NotificationEntry notificationEntry2 = (NotificationEntry) groupEntry.mUnmodifiableChildren.get(i2);
                    LogMessage obtain3 = logBuffer.obtain("ShadeListBuilder", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logFinalList$7
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            String m = BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "  [", "] ", logMessage.getStr1());
                            if (!logMessage.getBool1()) {
                                return m;
                            }
                            return m + " rank=" + logMessage.getInt2();
                        }
                    }, null);
                    LogMessageImpl logMessageImpl3 = (LogMessageImpl) obtain3;
                    logMessageImpl3.int1 = i2;
                    logMessageImpl3.str1 = NotificationUtilsKt.getLogKey(notificationEntry2);
                    logMessageImpl3.bool1 = false;
                    logMessageImpl3.int2 = notificationEntry2.mRanking.getRank();
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
        ((LogMessageImpl) obtain).long1 = i;
        ((LogMessageImpl) obtain).str1 = groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null;
        logBuffer.commit(obtain);
    }

    public final void logOnBuildList(String str) {
        LogLevel logLevel = LogLevel.INFO;
        ShadeListBuilderLogger$logOnBuildList$2 shadeListBuilderLogger$logOnBuildList$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger$logOnBuildList$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Request received from NotifCollection for ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logOnBuildList$2, null);
        ((LogMessageImpl) obtain).str1 = str;
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
                return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(sb, "' suppressed; keeping parent '", str2, "'");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logParentChangeSuppressedStarted$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        String logKey = groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = logKey;
        logMessageImpl.str2 = groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null;
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
                return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(sb, "' no longer suppressed; replaced parent '", str2, "'");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logParentChangeSuppressedStopped$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        String logKey = groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = logKey;
        logMessageImpl.str2 = groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null;
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
                    return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(sb, "} -> {", str2, "}");
                }
                return "(Build " + logMessage.getLong1() + ")     Parent was {" + logMessage.getStr1() + "}";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logParentChanged$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        String logKey = groupEntry != null ? NotificationUtilsKt.getLogKey(groupEntry) : null;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = logKey;
        logMessageImpl.str2 = groupEntry2 != null ? NotificationUtilsKt.getLogKey(groupEntry2) : null;
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
                return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Invalidated while ", stateName, " by ", str1, " \""), logMessage.getStr2(), "\" because ", logMessage.getStr3());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logPluggableInvalidated$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = pluggable.getName();
        logMessageImpl.int1 = i;
        logMessageImpl.str3 = str2;
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
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, " -> ", str2);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logPromoterChanged$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        String name = notifPromoter != null ? notifPromoter.getName() : null;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = name;
        logMessageImpl.str2 = notifPromoter2 != null ? notifPromoter2.getName() : null;
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
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, " -> ", str22);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logPrunedReasonChanged$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
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
                return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(sb, " (staying at ", str2, ")");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logSectionChangeSuppressed$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        String str = notifSection != null ? notifSection.label : null;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = notifSection2 != null ? notifSection2.label : null;
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
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, " -> ", str2);
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ShadeListBuilder", logLevel, shadeListBuilderLogger$logSectionChanged$2, null);
        ((LogMessageImpl) obtain).long1 = i;
        String str = notifSection != null ? notifSection.label : null;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = notifSection2 != null ? notifSection2.label : null;
        logBuffer.commit(obtain);
    }
}
