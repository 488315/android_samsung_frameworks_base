package com.android.systemui.plugins.log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface TableLogBufferBase {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class DefaultImpls {
        public static void logChange(TableLogBufferBase tableLogBufferBase, String str, String str2, String str3) {
            tableLogBufferBase.logChange(str, str2, str3, false);
        }

        public static void logChange(TableLogBufferBase tableLogBufferBase, String str, String str2, boolean z) {
            tableLogBufferBase.logChange(str, str2, z, false);
        }

        public static void logChange(TableLogBufferBase tableLogBufferBase, String str, String str2, Integer num) {
            tableLogBufferBase.logChange(str, str2, num, false);
        }
    }

    void logChange(String str, String str2, Integer num);

    void logChange(String str, String str2, Integer num, boolean z);

    void logChange(String str, String str2, String str3);

    void logChange(String str, String str2, String str3, boolean z);

    void logChange(String str, String str2, boolean z);

    void logChange(String str, String str2, boolean z, boolean z2);
}
