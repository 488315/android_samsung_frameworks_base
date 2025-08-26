package com.android.systemui.plugins.log;

/* loaded from: classes2.dex */
public interface TableLogBufferBase {

    public final class DefaultImpls {
        public static void logChange(TableLogBufferBase tableLogBufferBase, String str, String str2, String str3) {
            tableLogBufferBase.logChange(str, str2, str3, false);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, String str3, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, str3);
        }

        public static void logChange(TableLogBufferBase tableLogBufferBase, String str, String str2, boolean z) {
            tableLogBufferBase.logChange(str, str2, z, false);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, String str3, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, str3, z);
        }

        public static void logChange(TableLogBufferBase tableLogBufferBase, String str, String str2, Integer num) {
            tableLogBufferBase.logChange(str, str2, num, false);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, z);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, z, z2);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, Integer num, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, num);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, Integer num, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, num, z);
        }
    }

    void logChange(String str, String str2, Integer num);

    void logChange(String str, String str2, Integer num, boolean z);

    void logChange(String str, String str2, String str3);

    void logChange(String str, String str2, String str3, boolean z);

    void logChange(String str, String str2, boolean z);

    void logChange(String str, String str2, boolean z, boolean z2);
}
