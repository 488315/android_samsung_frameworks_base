package com.android.systemui.util;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.StrictMode;
import android.os.Trace;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.CoreStartable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes3.dex */
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

    final class BinderTransactionAnalysis {
        public static final Companion Companion = new Companion(null);
        private final boolean isSystemUi;
        private final String logMessage;
        private final String traceMessage;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
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
                        className2.getClass();
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

            private Companion() {
            }
        }

        public BinderTransactionAnalysis(boolean z, StackTraceElement stackTraceElement, StackTraceElement stackTraceElement2) {
            this.isSystemUi = z;
            String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(z ? BinderLogger.Companion.getSimpleCallRefWithFileAndLineNumber(stackTraceElement) : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(BinderLogger.Companion.getSimpleCallRef(stackTraceElement), "()"), " -> ", BinderLogger.Companion.getBinderCallRef(stackTraceElement2));
            this.logMessage = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Blocking binder transaction detected", !z ? ", but the call did not originate from System UI" : "", ": ", strM);
            this.traceMessage = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(z ? "sysui" : "core", ": ", strM);
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
            className.getClass();
            int iIndexOf$default = StringsKt__StringsKt.indexOf$default(className, "$Stub$Proxy", 0, false, 6);
            return iIndexOf$default > 0 ? className.substring(0, iIndexOf$default) : className;
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
            return ReorderTile$$ExternalSyntheticOutline0.m(stackTraceElement.getLineNumber(), ")", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("at ", BinderLogger.Companion.getSimpleCallRef(stackTraceElement), "(", stackTraceElement.getFileName(), ":"));
        }

        private final String getSimpleClassName(StackTraceElement stackTraceElement) throws ClassNotFoundException {
            Class<?> cls = Class.forName(stackTraceElement.getClassName());
            String canonicalName = cls.getCanonicalName();
            String strSubstring = canonicalName != null ? canonicalName.substring(cls.getPackageName().length() + 1) : null;
            return strSubstring == null ? stackTraceElement.getClassName() : strSubstring;
        }

        private Companion() {
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
        BinderTransactionAnalysis binderTransactionAnalysisFromStackTrace = BinderTransactionAnalysis.Companion.fromStackTrace(new Throwable().getStackTrace());
        Random.Default.getClass();
        int iNextInt = Random.defaultRandom.nextInt();
        Trace.asyncTraceForTrackBegin(4096L, TRACK_NAME, binderTransactionAnalysisFromStackTrace.getTraceMessage(), iNextInt);
        if (binderTransactionAnalysisFromStackTrace.isSystemUi()) {
            StrictMode.noteSlowCall(binderTransactionAnalysisFromStackTrace.getLogMessage());
        } else {
            binderTransactionAnalysisFromStackTrace.getLogMessage();
        }
        return Integer.valueOf(iNextInt);
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ void onTrimMemory(int i) {
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ void onBootCompleted() {
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public /* bridge */ /* synthetic */ void dump(PrintWriter printWriter, String[] strArr) {
    }
}
