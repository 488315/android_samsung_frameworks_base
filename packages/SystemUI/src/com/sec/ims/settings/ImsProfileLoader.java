package com.sec.ims.settings;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes4.dex */
public class ImsProfileLoader {
    public static final String LOG_TAG = "ImsProfileLoader";
    public static String MOCK_MNO_PROPERTY = "persist.ims.mock.mno";
    public static final String PREF_SETTING_DB = "pref_setting_db";
    public static final String SETTING_DB_CREATED = "setting_db_created";
    public static final String SETTING_DB_JUST_CREATED = "setting_db_just_created";

    public ImsProfileLoader(Context context) {
    }

    public static Uri addProfile(Context context, ImsProfile imsProfile) {
        return context.getContentResolver().insert(Uri.parse("content://com.sec.ims.settings/profile"), getContentValues(imsProfile));
    }

    public static ImsProfile find(Collection<ImsProfile> collection, String str) {
        for (ImsProfile imsProfile : collection) {
            if (imsProfile.getPdn().contains(str)) {
                return imsProfile;
            }
        }
        return null;
    }

    private static ContentValues getContentValues(ImsProfile imsProfile) {
        return imsProfile.getAsContentValues();
    }

    public static ImsProfile getImsProfileFromRow(Context context, Cursor cursor) {
        return new ImsProfile(cursor.getString(cursor.getColumnIndex(ImsProfile.SERVICE_PROFILE)));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ImsProfile getProfile(Context context, int i) {
        ImsProfile imsProfileFromRow;
        Log.d(LOG_TAG, "getProfile: id " + i);
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.sec.ims.settings/profile/" + i), null, null, null, null);
        if (cursorQuery != null) {
            try {
                Log.d(LOG_TAG, "getProfile: found ");
                imsProfileFromRow = cursorQuery.moveToFirst() ? getImsProfileFromRow(context, cursorQuery) : null;
            } finally {
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return imsProfileFromRow;
    }

    public static List<ImsProfile> getProfileListWithMnoName(Context context, String str, int i) {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.sec.ims.settings/profile").buildUpon().fragment("simslot" + Integer.toString(i)).build(), null, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("mnoname=", str), null, null);
        if (cursorQuery != null) {
            try {
                Log.d(LOG_TAG, "getProfileList: found " + cursorQuery.getCount() + " profiles");
                if (cursorQuery.moveToFirst()) {
                    do {
                        arrayList.add(getImsProfileFromRow(context, cursorQuery));
                    } while (cursorQuery.moveToNext());
                }
            } finally {
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return arrayList;
    }

    public static int updateProfile(Context context, ImsProfile imsProfile) {
        return context.getContentResolver().update(Uri.parse("content://com.sec.ims.settings/profile/" + imsProfile.getId()), getContentValues(imsProfile), null, null);
    }

    public static int updateProfile(Context context, List<ImsProfile> list) throws RemoteException, OperationApplicationException {
        ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
        for (ImsProfile imsProfile : list) {
            arrayList.add(ContentProviderOperation.newUpdate(Uri.parse("content://com.sec.ims.settings/profile/" + imsProfile.getId())).withValues(getContentValues(imsProfile)).build());
        }
        int i = 0;
        try {
            ContentProviderResult[] contentProviderResultArrApplyBatch = context.getContentResolver().applyBatch(ImsSettings.AUTHORITY, arrayList);
            int length = contentProviderResultArrApplyBatch.length;
            int iIntValue = 0;
            while (i < length) {
                try {
                    iIntValue += contentProviderResultArrApplyBatch[i].count.intValue();
                    i++;
                } catch (OperationApplicationException e) {
                    e = e;
                    i = iIntValue;
                    e.printStackTrace();
                    return i;
                } catch (RemoteException e2) {
                    e = e2;
                    i = iIntValue;
                    e.printStackTrace();
                    return i;
                }
            }
            return iIntValue;
        } catch (OperationApplicationException e3) {
            e = e3;
        } catch (RemoteException e4) {
            e = e4;
        }
    }
}
