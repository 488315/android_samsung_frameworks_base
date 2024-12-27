package com.android.server.notification;

import java.io.File;
import java.util.Comparator;

public final /* synthetic */ class NotificationHistoryDatabase$$ExternalSyntheticLambda0
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        long j;
        File file = (File) obj;
        long j2 = -1;
        try {
            j = Long.parseLong(((File) obj2).getName());
        } catch (NumberFormatException unused) {
            j = -1;
        }
        try {
            j2 = Long.parseLong(file.getName());
        } catch (NumberFormatException unused2) {
        }
        return Long.compare(j, j2);
    }
}
