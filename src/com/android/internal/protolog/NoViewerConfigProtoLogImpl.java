package com.android.internal.protolog;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.protolog.common.ILogger;
import com.android.internal.protolog.common.IProtoLog;
import com.android.internal.protolog.common.IProtoLogGroup;
import com.android.internal.protolog.common.LogLevel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
/* loaded from: classes4.dex */
public class NoViewerConfigProtoLogImpl implements IProtoLog {
    private static final String LOG_TAG = "ProtoLog";

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isEnabled(IProtoLogGroup iProtoLogGroup, LogLevel logLevel) {
        return false;
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
        Log.w(LOG_TAG, "ProtoLogging is not available due to missing viewer config file...");
        logMessage(logLevel, iProtoLogGroup.getTag(), "PROTOLOG#" + j + NavigationBarInflaterView.KEY_CODE_START + ((String) Arrays.stream(objArr).map(new NoViewerConfigProtoLogImpl$$ExternalSyntheticLambda0()).collect(Collectors.joining())) + NavigationBarInflaterView.KEY_CODE_END);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(LogLevel logLevel, IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        logMessage(logLevel, iProtoLogGroup.getTag(), TextUtils.formatSimple(str, objArr));
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public List<IProtoLogGroup> getRegisteredGroups() {
        return Collections.EMPTY_LIST;
    }

    /* renamed from: com.android.internal.protolog.NoViewerConfigProtoLogImpl$1, reason: invalid class name */
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

    private void logMessage(LogLevel logLevel, String str, String str2) {
        switch (AnonymousClass1.$SwitchMap$com$android$internal$protolog$common$LogLevel[logLevel.ordinal()]) {
            case 1:
                Log.v(str, str2);
                break;
            case 2:
                Log.i(str, str2);
                break;
            case 3:
                Log.d(str, str2);
                break;
            case 4:
                Log.w(str, str2);
                break;
            case 5:
                Log.e(str, str2);
                break;
            case 6:
                Log.wtf(str, str2);
                break;
        }
    }
}
