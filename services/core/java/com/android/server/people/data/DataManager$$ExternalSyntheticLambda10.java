package com.android.server.people.data;

import android.util.Pair;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DataManager$$ExternalSyntheticLambda10
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        ConversationInfo conversationInfo = (ConversationInfo) ((Pair) obj).second;
        return Math.max(conversationInfo.mLastEventTimestamp, conversationInfo.mCreationTimestamp);
    }
}
