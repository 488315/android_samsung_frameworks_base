package com.android.server.people.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;
import android.telephony.PhoneNumberUtils;
import android.util.Slog;
import android.util.SparseIntArray;

import java.util.function.BiConsumer;

public final class MmsQueryHelper {
    public static final SparseIntArray MSG_BOX_TO_EVENT_TYPE;
    public final Context mContext;
    public final String mCurrentCountryIso;
    public final BiConsumer mEventConsumer;
    public long mLastMessageTimestamp;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        MSG_BOX_TO_EVENT_TYPE = sparseIntArray;
        sparseIntArray.put(1, 9);
        sparseIntArray.put(2, 8);
    }

    public MmsQueryHelper(Context context, BiConsumer biConsumer) {
        this.mContext = context;
        this.mEventConsumer = biConsumer;
        this.mCurrentCountryIso = Utils.getCurrentCountryIso(context);
    }

    public final String getMmsAddress(int i, String str) {
        Cursor query =
                this.mContext
                        .getContentResolver()
                        .query(
                                Telephony.Mms.Addr.getAddrUriForMessage(str),
                                new String[] {"address", "type"},
                                null,
                                null,
                                null);
        try {
            if (query == null) {
                Slog.w("MmsQueryHelper", "Cursor is null when querying MMS address table.");
                if (query != null) {
                    query.close();
                }
                return null;
            }
            String str2 = null;
            while (query.moveToNext()) {
                int i2 = query.getInt(query.getColumnIndex("type"));
                if ((i == 1 && i2 == 137) || (i == 2 && i2 == 151)) {
                    str2 = query.getString(query.getColumnIndex("address"));
                }
            }
            query.close();
            if (Telephony.Mms.isPhoneNumber(str2)) {
                return PhoneNumberUtils.formatNumberToE164(str2, this.mCurrentCountryIso);
            }
            return null;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
