package com.android.systemui.activity.data.repository;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), ": Security exception on #addOnUidImportanceListener");
            case 1:
                return logMessage.getStr1() + ": Setting lastAppVisibleTime=" + logMessage.getLong1();
            case 2:
                return logMessage.getStr1() + ": #onUidImportance. importance=" + logMessage.getInt1() + ", isAppVisible=" + logMessage.getBool1();
            case 3:
                return logMessage.getStr1() + ": Starting UID observation. isAppVisible=" + logMessage.getBool1();
            default:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), ": Security exception on #getUidImportance");
        }
    }
}
