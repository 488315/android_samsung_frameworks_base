package com.android.server.people.data;

import android.content.Context;
import android.util.SparseIntArray;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CallLogQueryHelper {
    public static final SparseIntArray CALL_TYPE_TO_EVENT_TYPE;
    public final Context mContext;
    public final BiConsumer mEventConsumer;
    public long mLastCallTimestamp;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        CALL_TYPE_TO_EVENT_TYPE = sparseIntArray;
        sparseIntArray.put(1, 11);
        sparseIntArray.put(2, 10);
        sparseIntArray.put(3, 12);
    }

    public CallLogQueryHelper(Context context, BiConsumer biConsumer) {
        this.mContext = context;
        this.mEventConsumer = biConsumer;
    }
}
