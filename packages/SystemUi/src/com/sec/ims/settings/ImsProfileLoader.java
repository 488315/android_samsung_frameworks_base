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
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:5:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ImsProfile getProfile(Context context, int i) {
        ImsProfile imsProfileFromRow;
        Log.d(LOG_TAG, "getProfile: id " + i);
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.sec.ims.settings/profile/" + i), null, null, null, null);
        if (query != null) {
            try {
                Log.d(LOG_TAG, "getProfile: found ");
                if (query.moveToFirst()) {
                    imsProfileFromRow = getImsProfileFromRow(context, query);
                    if (query != null) {
                        query.close();
                    }
                    return imsProfileFromRow;
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        imsProfileFromRow = null;
        if (query != null) {
        }
        return imsProfileFromRow;
    }

    public static List<ImsProfile> getProfileListWithMnoName(Context context, String str, int i) {
        ArrayList arrayList = new ArrayList();
        Cursor query = context.getContentResolver().query(Uri.parse("content://com.sec.ims.settings/profile").buildUpon().fragment("simslot" + Integer.toString(i)).build(), null, KeyAttributes$$ExternalSyntheticOutline0.m21m("mnoname=", str), null, null);
        if (query != null) {
            try {
                Log.d(LOG_TAG, "getProfileList: found " + query.getCount() + " profiles");
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(getImsProfileFromRow(context, query));
                    } while (query.moveToNext());
                }
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public static int updateProfile(Context context, ImsProfile imsProfile) {
        return context.getContentResolver().update(Uri.parse("content://com.sec.ims.settings/profile/" + imsProfile.getId()), getContentValues(imsProfile), null, null);
    }

    public static int updateProfile(Context context, List<ImsProfile> list) {
        ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
        for (ImsProfile imsProfile : list) {
            arrayList.add(ContentProviderOperation.newUpdate(Uri.parse("content://com.sec.ims.settings/profile/" + imsProfile.getId())).withValues(getContentValues(imsProfile)).build());
        }
        int i = 0;
        try {
            ContentProviderResult[] applyBatch = context.getContentResolver().applyBatch(ImsSettings.AUTHORITY, arrayList);
            int length = applyBatch.length;
            int i2 = 0;
            while (i < length) {
                try {
                    i2 += applyBatch[i].count.intValue();
                    i++;
                } catch (OperationApplicationException e) {
                    e = e;
                    i = i2;
                    e.printStackTrace();
                    return i;
                } catch (RemoteException e2) {
                    e = e2;
                    i = i2;
                    e.printStackTrace();
                    return i;
                }
            }
            return i2;
        } catch (OperationApplicationException e3) {
            e = e3;
        } catch (RemoteException e4) {
            e = e4;
        }
    }
}
