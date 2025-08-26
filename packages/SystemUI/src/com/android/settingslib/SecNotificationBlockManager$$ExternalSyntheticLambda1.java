package com.android.settingslib;

import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class SecNotificationBlockManager$$ExternalSyntheticLambda1 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z = SecNotificationBlockManager.DEBUG;
        return ((String) obj).equals("android.permission.POST_NOTIFICATIONS");
    }
}
