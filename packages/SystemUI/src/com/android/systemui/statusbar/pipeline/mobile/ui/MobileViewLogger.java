package com.android.systemui.statusbar.pipeline.mobile.ui;

import android.view.View;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MobileViewLogger implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;
    public final Map collectionStatuses;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static String getIdForLogging(Object obj) {
            return Integer.toHexString(System.identityHashCode(obj));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        ((LinkedHashMap) this.collectionStatuses).forEach(new BiConsumer() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger$dump$1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                printWriter.println("viewId=" + ((String) obj) + ", isCollecting=" + booleanValue);
            }
        });
    }

    public final void logCollectionStarted(View view, LocationBasedMobileViewModel locationBasedMobileViewModel) {
        Map map = this.collectionStatuses;
        Companion.getClass();
        map.put(Companion.getIdForLogging(view), Boolean.TRUE);
        LogLevel logLevel = LogLevel.INFO;
        MobileViewLogger$logCollectionStarted$2 mobileViewLogger$logCollectionStarted$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger$logCollectionStarted$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Collection started. viewId=", str1, ", viewModelId=", str2, ", viewModelLocation=");
                m.append(str3);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$logCollectionStarted$2, null);
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
        MobileViewLogger$logCollectionStopped$2 mobileViewLogger$logCollectionStopped$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger$logCollectionStopped$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Collection stopped. viewId=", str1, ", viewModelId=", str2, ", viewModelLocation=");
                m.append(str3);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$logCollectionStopped$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = Companion.getIdForLogging(view);
        logMessageImpl.str2 = Companion.getIdForLogging(locationBasedMobileViewModel);
        logMessageImpl.str3 = locationBasedMobileViewModel.location.name();
        logBuffer.commit(obtain);
    }

    public final void logNewViewBinding(View view, LocationBasedMobileViewModel locationBasedMobileViewModel) {
        LogLevel logLevel = LogLevel.INFO;
        MobileViewLogger$logNewViewBinding$2 mobileViewLogger$logNewViewBinding$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger$logNewViewBinding$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("New view binding. viewId=", str1, ", viewModelId=", str2, ", viewModelLocation=");
                m.append(str3);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileViewLogger", logLevel, mobileViewLogger$logNewViewBinding$2, null);
        Companion.getClass();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = Companion.getIdForLogging(view);
        logMessageImpl.str2 = Companion.getIdForLogging(locationBasedMobileViewModel);
        logMessageImpl.str3 = locationBasedMobileViewModel.location.name();
        logBuffer.commit(obtain);
    }
}
