package com.samsung.context.sdk.samsunganalytics.internal.util;

import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import java.util.Map;

/* loaded from: classes.dex */
public final class Delimiter<K, V> {

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
        String sb;
        String str = null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (str == null) {
                sb = entry.getKey().toString();
            } else {
                StringBuilder m2m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(str);
                m2m.append(depth.getCollectionDLM());
                StringBuilder m2m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(m2m.toString());
                m2m2.append(entry.getKey());
                sb = m2m2.toString();
            }
            StringBuilder m2m3 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(sb);
            m2m3.append(depth.getKeyValueDLM());
            StringBuilder m2m4 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m2m(m2m3.toString());
            m2m4.append(entry.getValue());
            str = m2m4.toString();
        }
        return str;
    }
}
