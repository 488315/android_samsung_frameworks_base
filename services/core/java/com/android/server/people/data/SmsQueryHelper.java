package com.android.server.people.data;

import android.content.Context;
import android.util.SparseIntArray;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SmsQueryHelper {
    public static final SparseIntArray SMS_TYPE_TO_EVENT_TYPE;
    public final Context mContext;
    public final String mCurrentCountryIso;
    public final BiConsumer mEventConsumer;
    public long mLastMessageTimestamp;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        SMS_TYPE_TO_EVENT_TYPE = sparseIntArray;
        sparseIntArray.put(1, 9);
        sparseIntArray.put(2, 8);
    }

    public SmsQueryHelper(Context context, BiConsumer biConsumer) {
        this.mContext = context;
        this.mEventConsumer = biConsumer;
        this.mCurrentCountryIso = Utils.getCurrentCountryIso(context);
    }
}
