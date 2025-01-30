package com.android.wm.shell.protolog;

import com.android.internal.protolog.BaseProtoLogImpl;
import com.android.internal.protolog.ProtoLogViewerConfigReader;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.File;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShellProtoLogImpl extends BaseProtoLogImpl {
    public static ShellProtoLogImpl sServiceInstance;

    static {
        BaseProtoLogImpl.addLogGroupEnum(ShellProtoLogGroup.values());
    }

    private ShellProtoLogImpl() {
        super(new File("/data/misc/wmtrace/shell_log.winscope"), "/system_ext/etc/wmshell.protolog.json.gz", QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING, new ProtoLogViewerConfigReader());
    }

    /* renamed from: d */
    public static void m229d(ShellProtoLogGroup shellProtoLogGroup, int i, int i2, String str, Object... objArr) {
        getSingleInstance().log(BaseProtoLogImpl.LogLevel.DEBUG, shellProtoLogGroup, i, i2, str, objArr);
    }

    /* renamed from: e */
    public static void m230e(ShellProtoLogGroup shellProtoLogGroup, int i, int i2, String str, Object... objArr) {
        getSingleInstance().log(BaseProtoLogImpl.LogLevel.ERROR, shellProtoLogGroup, i, i2, str, objArr);
    }

    public static synchronized ShellProtoLogImpl getSingleInstance() {
        ShellProtoLogImpl shellProtoLogImpl;
        synchronized (ShellProtoLogImpl.class) {
            if (sServiceInstance == null) {
                sServiceInstance = new ShellProtoLogImpl();
            }
            shellProtoLogImpl = sServiceInstance;
        }
        return shellProtoLogImpl;
    }

    /* renamed from: i */
    public static void m231i(ShellProtoLogGroup shellProtoLogGroup, int i, String str, Object... objArr) {
        getSingleInstance().log(BaseProtoLogImpl.LogLevel.INFO, shellProtoLogGroup, i, 0, str, objArr);
    }

    public static boolean isEnabled(ShellProtoLogGroup shellProtoLogGroup) {
        return shellProtoLogGroup.isLogToLogcat() || (shellProtoLogGroup.isLogToProto() && getSingleInstance().isProtoEnabled());
    }

    /* renamed from: v */
    public static void m232v(ShellProtoLogGroup shellProtoLogGroup, int i, int i2, String str, Object... objArr) {
        getSingleInstance().log(BaseProtoLogImpl.LogLevel.VERBOSE, shellProtoLogGroup, i, i2, str, objArr);
    }

    /* renamed from: w */
    public static void m233w(ShellProtoLogGroup shellProtoLogGroup, int i, int i2, String str, Object... objArr) {
        getSingleInstance().log(BaseProtoLogImpl.LogLevel.WARN, shellProtoLogGroup, i, i2, str, objArr);
    }

    public static void wtf(ShellProtoLogGroup shellProtoLogGroup, int i, int i2, String str, Object... objArr) {
        getSingleInstance().log(BaseProtoLogImpl.LogLevel.WTF, shellProtoLogGroup, i, i2, str, objArr);
    }

    public final int startTextLogging(PrintWriter printWriter, String[] strArr) {
        ((BaseProtoLogImpl) this).mViewerConfig.loadViewerConfig(printWriter, "/system_ext/etc/wmshell.protolog.json.gz");
        return setLogging(true, true, printWriter, strArr);
    }

    public final int stopTextLogging(PrintWriter printWriter, String[] strArr) {
        return setLogging(true, false, printWriter, strArr);
    }
}
