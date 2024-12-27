package com.android.server.people.data;

import android.util.Pair;

import java.util.function.ToLongFunction;

public final /* synthetic */ class DataManager$$ExternalSyntheticLambda10
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        ConversationInfo conversationInfo = (ConversationInfo) ((Pair) obj).second;
        return Math.max(conversationInfo.mLastEventTimestamp, conversationInfo.mCreationTimestamp);
    }
}
