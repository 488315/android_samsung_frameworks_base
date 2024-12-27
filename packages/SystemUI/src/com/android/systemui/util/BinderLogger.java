package com.android.systemui.util;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.StrictMode;
import android.os.Trace;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.CoreStartable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import java.io.PrintWriter;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BinderLogger implements CoreStartable, Binder.ProxyTransactListener {
    private static final String AOSP_SOURCE_FILE_MARKER = "go/retraceme ";
    private static final String KEYGUARD_PKG = "com.android.keyguard";
    private static final String R8_SOURCE_FILE_MARKER = "R8_";
    private static final String SYSUI_PKG = "com.android.systemui";
    private static final String TAG = "SystemUIBinder";
    private static final String TRACK_NAME = "Blocking Binder Transactions";
    private static final String UNKNOWN = "<unknown>";
    private final FeatureFlags featureFlags;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class BinderTransactionAnalysis {
        public static final Companion Companion = new Companion(null);
        private final boolean isSystemUi;
        private final String logMessage;
        private final String traceMessage;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Companion {
            private Companion() {
            }

            public final BinderTransactionAnalysis fromStackTrace(StackTraceElement[] stackTraceElementArr) {
                StackTraceElement stackTraceElement = null;
                if (stackTraceElementArr.length < 2) {
                    return new BinderTransactionAnalysis(false, null, null);
                }
                StackTraceElement stackTraceElement2 = (StackTraceElement) ArraysKt___ArraysKt.first(stackTraceElementArr);
                int length = stackTraceElementArr.length;
                StackTraceElement stackTraceElement3 = null;
                int i = 1;
                while (i < length) {
                    StackTraceElement stackTraceElement4 = stackTraceElementArr[i];
                    String className = stackTraceElement2.getClassName();
                    if (className != null && className.endsWith("$Stub$Proxy")) {
                        stackTraceElement3 = stackTraceElement2;
                        stackTraceElement = stackTraceElement4;
                    }
                    String className2 = stackTraceElement4.getClassName();
                    if (!Intrinsics.areEqual(className2, BinderLogger.class.getName())) {
                        Intrinsics.checkNotNull(className2);
                        if (className2.startsWith(BinderLogger.SYSUI_PKG) || className2.startsWith(BinderLogger.KEYGUARD_PKG)) {
                            if (stackTraceElement3 != null) {
                                stackTraceElement2 = stackTraceElement3;
                            }
                            return new BinderTransactionAnalysis(true, stackTraceElement4, stackTraceElement2);
                        }
                    }
                    i++;
                    stackTraceElement2 = stackTraceElement4;
                }
                return new BinderTransactionAnalysis(false, stackTraceElement, stackTraceElement3);
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public BinderTransactionAnalysis(boolean z, StackTraceElement stackTraceElement, StackTraceElement stackTraceElement2) {
            this.isSystemUi = z;
            String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(z ? BinderLogger.Companion.getSimpleCallRefWithFileAndLineNumber(stackTraceElement) : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(BinderLogger.Companion.getSimpleCallRef(stackTraceElement), "()"), " -> ", BinderLogger.Companion.getBinderCallRef(stackTraceElement2));
            this.logMessage = FontProvider$$ExternalSyntheticOutline0.m("Blocking binder transaction detected", !z ? ", but the call did not originate from System UI" : "", ": ", m);
            this.traceMessage = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(z ? "sysui" : "core", ": ", m);
        }

        public final String getLogMessage() {
            return this.logMessage;
        }

        public final String getTraceMessage() {
            return this.traceMessage;
        }

        public final boolean isSystemUi() {
            return this.isSystemUi;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getBinderCallRef(StackTraceElement stackTraceElement) {
            if (stackTraceElement == null) {
                return BinderLogger.UNKNOWN;
            }
            return getBinderClassName(stackTraceElement) + "#" + stackTraceElement.getMethodName() + "()";
        }

        private final String getBinderClassName(StackTraceElement stackTraceElement) {
            String className = stackTraceElement.getClassName();
            Intrinsics.checkNotNull(className);
            int indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) className, "$Stub$Proxy", 0, false, 6);
            return indexOf$default > 0 ? className.substring(0, indexOf$default) : className;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getSimpleCallRef(StackTraceElement stackTraceElement) {
            return stackTraceElement != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(getSimpleClassName(stackTraceElement), "#", stackTraceElement.getMethodName()) : BinderLogger.UNKNOWN;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getSimpleCallRefWithFileAndLineNumber(StackTraceElement stackTraceElement) {
            if (stackTraceElement == null) {
                return BinderLogger.UNKNOWN;
            }
            if (stackTraceElement.getFileName() == null || stackTraceElement.getFileName().startsWith(BinderLogger.AOSP_SOURCE_FILE_MARKER) || stackTraceElement.getFileName().startsWith(BinderLogger.R8_SOURCE_FILE_MARKER)) {
                return "at " + stackTraceElement;
            }
            return Anchor$$ExternalSyntheticOutline0.m(stackTraceElement.getLineNumber(), ")", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("at ", BinderLogger.Companion.getSimpleCallRef(stackTraceElement), "(", stackTraceElement.getFileName(), ":"));
        }

        private final String getSimpleClassName(StackTraceElement stackTraceElement) {
            Class<?> cls = Class.forName(stackTraceElement.getClassName());
            String canonicalName = cls.getCanonicalName();
            String substring = canonicalName != null ? canonicalName.substring(cls.getPackageName().length() + 1) : null;
            return substring == null ? stackTraceElement.getClassName() : substring;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BinderLogger(FeatureFlags featureFlags) {
        this.featureFlags = featureFlags;
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ boolean isDumpCritical() {
        return true;
    }

    public void onTransactEnded(Object obj) {
        if (obj instanceof Integer) {
            Trace.asyncTraceForTrackEnd(4096L, TRACK_NAME, ((Number) obj).intValue());
        }
    }

    public Object onTransactStarted(IBinder iBinder, int i) {
        return null;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        if (Build.IS_USER) {
            return;
        }
        FeatureFlags featureFlags = this.featureFlags;
        Flags flags = Flags.INSTANCE;
        featureFlags.getClass();
    }

    public Object onTransactStarted(IBinder iBinder, int i, int i2) {
        if ((i2 & 1) != 0 || !ThreadUtils.isMainThread()) {
            return null;
        }
        BinderTransactionAnalysis fromStackTrace = BinderTransactionAnalysis.Companion.fromStackTrace(new Throwable().getStackTrace());
        Random.Default.getClass();
        int nextInt = Random.defaultRandom.nextInt();
        Trace.asyncTraceForTrackBegin(4096L, TRACK_NAME, fromStackTrace.getTraceMessage(), nextInt);
        if (fromStackTrace.isSystemUi()) {
            StrictMode.noteSlowCall(fromStackTrace.getLogMessage());
        } else {
            fromStackTrace.getLogMessage();
        }
        return Integer.valueOf(nextInt);
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ void onBootCompleted() {
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ void onTrimMemory(int i) {
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public /* bridge */ /* synthetic */ void dump(PrintWriter printWriter, String[] strArr) {
    }
}
