package com.android.server.asks;

import java.util.ArrayList;
import java.util.regex.Pattern;

public final class ADPContainer {
    public ArrayList mADPPolicy;
    public String packageName;

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
            return (str != null
                            && ADPOperation.isGreaterOrEqual(
                                    Pattern.compile(str), this.hashCode, aDPPolicy.hashCode))
                    ? -1
                    : 1;
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
