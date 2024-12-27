package com.android.server.am;

import java.util.Comparator;

public final /* synthetic */ class ProcessReceiverRecord$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        String str = (String) obj;
        String str2 = (String) obj2;
        if (str == null && str2 == null) {
            return 0;
        }
        if (str == null && str2 != null) {
            return 1;
        }
        if (str != null && str2 == null) {
            return -1;
        }
        return str.split(" ")[3].compareTo(str2.split(" ")[3]);
    }
}
