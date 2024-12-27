package com.android.server.pm;

public final class ApexSystemServiceInfo implements Comparable {
    public final int mInitOrder;
    public final String mJarPath;
    public final String mName;

    public ApexSystemServiceInfo(int i, String str, String str2) {
        this.mName = str;
        this.mJarPath = str2;
        this.mInitOrder = i;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        ApexSystemServiceInfo apexSystemServiceInfo = (ApexSystemServiceInfo) obj;
        int i = this.mInitOrder;
        int i2 = apexSystemServiceInfo.mInitOrder;
        return i == i2
                ? this.mName.compareTo(apexSystemServiceInfo.mName)
                : -Integer.compare(i, i2);
    }
}
