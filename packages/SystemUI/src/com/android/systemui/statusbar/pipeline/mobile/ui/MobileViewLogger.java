package com.android.systemui.statusbar.pipeline.mobile.ui;

import android.view.View;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileViewLogger implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;
    public final Map collectionStatuses;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String getIdForLogging(Object obj) {
            return Integer.toHexString(System.identityHashCode(obj));
        }

        private Companion() {
        }
    }

    public MobileViewLogger(LogBuffer logBuffer, DumpManager dumpManager) {
        this.buffer = logBuffer;
        dumpManager.registerNormalDumpable(this);
        this.collectionStatuses = new LinkedHashMap();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.println("Collection statuses per view:---");
        Map map = this.collectionStatuses;
        final Function2 function2 = new Function2() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                PrintWriter printWriter2 = printWriter;
                MobileViewLogger.Companion companion = MobileViewLogger.Companion;
                printWriter2.println("viewId=" + ((String) obj) + ", isCollecting=" + ((Boolean) obj2));
                return Unit.INSTANCE;
            }
        };
        ((LinkedHashMap) map).forEach(new BiConsumer() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLoggerKt$sam$java_util_function_BiConsumer$0
            @Override // java.util.function.BiConsumer
            public final /* synthetic */ void accept(Object obj, Object obj2) {
                Function2.this.invoke(obj, obj2);
            }
        });
    }

    public final void logCollectionStarted(View view, LocationBasedMobileViewModel locationBasedMobileViewModel) {
        Map map = this.collectionStatuses;
        Companion.getClass();
        map.put(Companion.getIdForLogging(view), Boolean.TRUE);
        LogLevel logLevel = LogLevel.INFO;
        MobileViewLogger$$ExternalSyntheticLambda1 mobileViewLogger$$ExternalSyntheticLambda1 = new MobileViewLogger$$ExternalSyntheticLambda1(3);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = Companion.getIdForLogging(view);
        logMessageImpl.str2 = Companion.getIdForLogging(locationBasedMobileViewModel);
        logMessageImpl.str3 = locationBasedMobileViewModel.location.name();
        logBuffer.commit(obtain);
    }

    public final void logCollectionStopped(View view, LocationBasedMobileViewModel locationBasedMobileViewModel) {
        Map map = this.collectionStatuses;
        Companion.getClass();
        map.put(Companion.getIdForLogging(view), Boolean.FALSE);
        LogLevel logLevel = LogLevel.INFO;
        MobileViewLogger$$ExternalSyntheticLambda1 mobileViewLogger$$ExternalSyntheticLambda1 = new MobileViewLogger$$ExternalSyntheticLambda1(0);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = Companion.getIdForLogging(view);
        logMessageImpl.str2 = Companion.getIdForLogging(locationBasedMobileViewModel);
        logMessageImpl.str3 = locationBasedMobileViewModel.location.name();
        logBuffer.commit(obtain);
    }

    public final void logUiAdapterSubIdsSentToIconController(List list, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        MobileViewLogger$$ExternalSyntheticLambda1 mobileViewLogger$$ExternalSyntheticLambda1 = new MobileViewLogger$$ExternalSyntheticLambda1(2);
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) obtain).str1 = list.toString();
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }
}
