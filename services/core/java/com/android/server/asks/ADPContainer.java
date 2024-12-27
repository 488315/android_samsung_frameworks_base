package com.android.server.asks;

import java.util.ArrayList;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ADPContainer {
    public ArrayList mADPPolicy;
    public String packageName;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ADPPolicy implements Comparable {
        public String format;
        public String hashCode;
        public String pattern;
        public int versionType;

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            ADPPolicy aDPPolicy = (ADPPolicy) obj;
            int i = this.versionType;
            if (i != 1703114115) {
                return ADPOperation.isGreaterOrEqual(i, this.hashCode, aDPPolicy.hashCode) ? -1 : 1;
            }
            String str = this.format;
            return (str != null && ADPOperation.isGreaterOrEqual(Pattern.compile(str), this.hashCode, aDPPolicy.hashCode)) ? -1 : 1;
        }

        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            ADPPolicy aDPPolicy = (ADPPolicy) obj;
            if (Pattern.compile(this.pattern).matcher(aDPPolicy.hashCode).find()) {
                return Pattern.compile(aDPPolicy.pattern).matcher(this.hashCode).find();
            }
            return false;
        }
    }
}
