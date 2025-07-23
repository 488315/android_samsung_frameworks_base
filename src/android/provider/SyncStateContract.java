package android.provider;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Pair;

/* loaded from: classes3.dex */
public class SyncStateContract {

    public interface Columns extends BaseColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String DATA = "data";
    }

    public static class Constants implements Columns {
        public static final String CONTENT_DIRECTORY = "syncstate";
    }

    public static final class Helpers {
        private static final String[] DATA_PROJECTION = {"data", "_id"};
        private static final String SELECT_BY_ACCOUNT = "account_name=? AND account_type=?";

        public static byte[] get(ContentProviderClient contentProviderClient, Uri uri, Account account) throws RemoteException {
            Cursor query = contentProviderClient.query(uri, DATA_PROJECTION, SELECT_BY_ACCOUNT, new String[]{account.name, account.type}, null);
            if (query == null) {
                throw new RemoteException();
            }
            try {
                if (query.moveToNext()) {
                    return query.getBlob(query.getColumnIndexOrThrow("data"));
                }
                query.close();
                return null;
            } finally {
                query.close();
            }
        }

        public static void set(ContentProviderClient contentProviderClient, Uri uri, Account account, byte[] bArr) throws RemoteException {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", bArr);
            contentValues.put("account_name", account.name);
            contentValues.put("account_type", account.type);
            contentProviderClient.insert(uri, contentValues);
        }

        public static Uri insert(ContentProviderClient contentProviderClient, Uri uri, Account account, byte[] bArr) throws RemoteException {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", bArr);
            contentValues.put("account_name", account.name);
            contentValues.put("account_type", account.type);
            return contentProviderClient.insert(uri, contentValues);
        }

        public static void update(ContentProviderClient contentProviderClient, Uri uri, byte[] bArr) throws RemoteException {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", bArr);
            contentProviderClient.update(uri, contentValues, null, null);
        }

        public static Pair<Uri, byte[]> getWithUri(ContentProviderClient contentProviderClient, Uri uri, Account account) throws RemoteException {
            Cursor query = contentProviderClient.query(uri, DATA_PROJECTION, SELECT_BY_ACCOUNT, new String[]{account.name, account.type}, null);
            if (query == null) {
                throw new RemoteException();
            }
            try {
                if (!query.moveToNext()) {
                    query.close();
                    return null;
                }
                long j = query.getLong(1);
                return Pair.create(ContentUris.withAppendedId(uri, j), query.getBlob(query.getColumnIndexOrThrow("data")));
            } finally {
                query.close();
            }
        }

        public static ContentProviderOperation newSetOperation(Uri uri, Account account, byte[] bArr) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", bArr);
            return ContentProviderOperation.newInsert(uri).withValue("account_name", account.name).withValue("account_type", account.type).withValues(contentValues).build();
        }

        public static ContentProviderOperation newUpdateOperation(Uri uri, byte[] bArr) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("data", bArr);
            return ContentProviderOperation.newUpdate(uri).withValues(contentValues).build();
        }
    }
}
