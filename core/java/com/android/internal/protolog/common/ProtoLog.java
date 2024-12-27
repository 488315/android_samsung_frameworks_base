package com.android.internal.protolog.common;

/* loaded from: classes5.dex */
public class ProtoLog {
    public static boolean REQUIRE_PROTOLOGTOOL = true;

    public static void d(IProtoLogGroup group, String messageString, Object... args) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void v(IProtoLogGroup group, String messageString, Object... args) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void i(IProtoLogGroup group, String messageString, Object... args) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void w(IProtoLogGroup group, String messageString, Object... args) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void e(IProtoLogGroup group, String messageString, Object... args) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void wtf(IProtoLogGroup group, String messageString, Object... args) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static boolean isEnabled(IProtoLogGroup group, LogLevel level) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
        return false;
    }

    public static IProtoLog getSingleInstance() {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
        return null;
    }
}
