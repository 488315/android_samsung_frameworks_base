package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.android.internal.telephony.HbpcdLookup;

/* loaded from: classes4.dex */
public final class HbpcdUtils {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "HbpcdUtils";
    private ContentResolver resolver;

    public HbpcdUtils(Context context) {
        this.resolver = null;
        this.resolver = context.getContentResolver();
    }

    public int getMcc(int i, int i2, int i3, boolean z) {
        Cursor cursorQuery = this.resolver.query(HbpcdLookup.ArbitraryMccSidMatch.CONTENT_URI, new String[]{"MCC"}, "SID=" + i, null, null);
        if (cursorQuery != null) {
            if (cursorQuery.getCount() == 1) {
                cursorQuery.moveToFirst();
                int i4 = cursorQuery.getInt(0);
                cursorQuery.close();
                return i4;
            }
            cursorQuery.close();
        }
        Cursor cursorQuery2 = this.resolver.query(HbpcdLookup.MccSidConflicts.CONTENT_URI, new String[]{"MCC"}, "SID_Conflict=" + i + " and (((GMT_Offset_Low<=" + i2 + ") and (" + i2 + "<=GMT_Offset_High) and (0=" + i3 + ")) or ((GMT_DST_Low<=" + i2 + ") and (" + i2 + "<=GMT_DST_High) and (1=" + i3 + ")))", null, null);
        if (cursorQuery2 != null) {
            int count = cursorQuery2.getCount();
            if (count > 0) {
                if (count > 1) {
                    Log.w(LOG_TAG, "something wrong, get more results for 1 conflict SID: " + cursorQuery2);
                }
                cursorQuery2.moveToFirst();
                int i5 = z ? cursorQuery2.getInt(0) : 0;
                cursorQuery2.close();
                return i5;
            }
            cursorQuery2.close();
        }
        Cursor cursorQuery3 = this.resolver.query(HbpcdLookup.MccSidRange.CONTENT_URI, new String[]{"MCC"}, "SID_Range_Low<=" + i + " and SID_Range_High>=" + i, null, null);
        if (cursorQuery3 != null) {
            if (cursorQuery3.getCount() > 0) {
                cursorQuery3.moveToFirst();
                int i6 = cursorQuery3.getInt(0);
                cursorQuery3.close();
                return i6;
            }
            cursorQuery3.close();
        }
        return 0;
    }

    public String getIddByMcc(int i) {
        String[] strArr = {HbpcdLookup.MccIdd.IDD};
        Cursor cursorQuery = this.resolver.query(HbpcdLookup.MccIdd.CONTENT_URI, strArr, "MCC=" + i, null, null);
        String string = "";
        if (cursorQuery != null) {
            if (cursorQuery.getCount() > 0) {
                cursorQuery.moveToFirst();
                string = cursorQuery.getString(0);
            }
            cursorQuery.close();
        }
        return string;
    }
}
