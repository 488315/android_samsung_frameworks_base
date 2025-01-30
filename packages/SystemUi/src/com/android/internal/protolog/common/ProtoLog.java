package com.android.internal.protolog.common;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ProtoLog {
    public static boolean REQUIRE_PROTOLOGTOOL = true;

    /* renamed from: v */
    public static void m52v(IProtoLogGroup iProtoLogGroup, String str, Object... objArr) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }
}
