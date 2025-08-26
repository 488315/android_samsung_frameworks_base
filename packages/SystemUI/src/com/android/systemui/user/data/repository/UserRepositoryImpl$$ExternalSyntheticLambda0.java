package com.android.systemui.user.data.repository;

import android.content.Intent;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class UserRepositoryImpl$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = UserRepositoryImpl.$r8$clinit;
        if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(((Intent) obj).getAction())) {
            return Unit.INSTANCE;
        }
        return null;
    }
}
