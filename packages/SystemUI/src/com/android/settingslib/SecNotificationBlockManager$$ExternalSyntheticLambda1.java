package com.android.settingslib;

import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class SecNotificationBlockManager$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z = SecNotificationBlockManager.DEBUG;
        return ((String) obj).equals("android.permission.POST_NOTIFICATIONS");
    }
}
