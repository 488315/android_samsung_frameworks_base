package com.android.server.notification;

import java.util.function.Predicate;

public final /* synthetic */ class NotificationManagerService$16$$ExternalSyntheticLambda7
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        String str = (String) obj;
        switch (this.$r8$classId) {
        }
        return str.equals("android.permission.POST_NOTIFICATIONS");
    }
}
