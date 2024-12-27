package com.android.systemui.plugins.log;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface TableLogBufferBase {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DefaultImpls {
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
