package com.android.server.people.data;

import android.content.Context;
import android.util.SparseIntArray;

import java.util.function.BiConsumer;

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
