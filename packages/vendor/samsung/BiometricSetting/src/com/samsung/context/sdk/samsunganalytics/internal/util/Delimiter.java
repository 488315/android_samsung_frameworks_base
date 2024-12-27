package com.samsung.context.sdk.samsunganalytics.internal.util;

import java.util.Map;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class Delimiter {

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public enum Depth {
        ONE_DEPTH("\u0002", "\u0003"),
        TWO_DEPTH("\u0004", "\u0005"),
        THREE_DEPTH("\u0006", "\u0007");

        private String collDlm;
        private String keyvalueDlm;

        Depth(String str, String str2) {
            this.collDlm = str;
            this.keyvalueDlm = str2;
        }

        public final String getCollectionDLM() {
            return this.collDlm;
        }

        public final String getKeyValueDLM() {
            return this.keyvalueDlm;
        }
    }

    public static String makeDelimiterString(Map map, Depth depth) {
        String str = null;
        for (Map.Entry entry : map.entrySet()) {
            str =
                    ((str == null
                                            ? entry.getKey().toString()
                                            : (str + depth.getCollectionDLM()) + entry.getKey())
                                    + depth.getKeyValueDLM())
                            + entry.getValue();
        }
        return str;
    }
}
