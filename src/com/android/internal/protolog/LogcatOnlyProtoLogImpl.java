package com.android.internal.protolog;

import android.text.TextUtils;
import android.util.Log;
import com.android.internal.protolog.common.ILogger;
import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;
import com.android.internal.protolog.common.LogLevel;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class LogcatOnlyProtoLogImpl implements IProtoLog {
    private static final String LOG_TAG = "com.android.internal.protolog.LogcatOnlyProtoLogImpl";

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isEnabled(IProtoLogGroup iProtoLogGroup, LogLevel logLevel) {
        return true;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isProtoEnabled() {
        return false;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int startLoggingToLogcat(String[] strArr, ILogger iLogger) {
        return 0;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int stopLoggingToLogcat(String[] strArr, ILogger iLogger) {
        return 0;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, long j, int i, Object[] objArr) {
        throw new RuntimeException("Not supported when using LogcatOnlyProtoLogImpl");
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, String str, Object[] objArr) {
        if (ProtoLog.REQUIRE_PROTOLOGTOOL && iProtoLogGroup.isLogToProto()) {
            Log.w(LOG_TAG, "ProtoLog message not processed. Failed to log it to proto. Logging it below to logcat instead.");
        }
        if (iProtoLogGroup.isLogToLogcat() || iProtoLogGroup.isLogToProto()) {
            String simple = TextUtils.formatSimple(str, objArr);
            switch (AnonymousClass1.$SwitchMap$com$android$internal$protolog$common$LogLevel[logLevel.ordinal()]) {
                case 1:
                    Log.v(iProtoLogGroup.getTag(), simple);
                    break;
                case 2:
                    Log.i(iProtoLogGroup.getTag(), simple);
                    break;
                case 3:
                    Log.d(iProtoLogGroup.getTag(), simple);
                    break;
                case 4:
                    Log.w(iProtoLogGroup.getTag(), simple);
                    break;
                case 5:
                    Log.e(iProtoLogGroup.getTag(), simple);
                    break;
                case 6:
                    Log.wtf(iProtoLogGroup.getTag(), simple);
                    break;
            }
        }
    }

    /* renamed from: com.android.internal.protolog.LogcatOnlyProtoLogImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$internal$protolog$common$LogLevel;

        static {
            int[] iArr = new int[LogLevel.values().length];
            $SwitchMap$com$android$internal$protolog$common$LogLevel = iArr;
            try {
                iArr[LogLevel.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.INFO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.DEBUG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$internal$protolog$common$LogLevel[LogLevel.WTF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public List<IProtoLogGroup> getRegisteredGroups() {
        return Collections.EMPTY_LIST;
    }
}
