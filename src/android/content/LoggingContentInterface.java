package android.content;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class LoggingContentInterface implements ContentInterface {
    private final ContentInterface delegate;
    private final String tag;

    public LoggingContentInterface(String str, ContentInterface contentInterface) {
        this.tag = str;
        this.delegate = contentInterface;
    }

    private class Logger implements AutoCloseable {
        private final StringBuilder sb = new StringBuilder();

        public Logger(String str, Object... objArr) {
            for (Object obj : objArr) {
                if (obj instanceof Bundle) {
                    ((Bundle) obj).size();
                }
            }
            StringBuilder sb = this.sb;
            sb.append("callingUid=");
            sb.append(Binder.getCallingUid());
            sb.append(' ');
            this.sb.append(str);
            StringBuilder sb2 = this.sb;
            sb2.append('(');
            sb2.append(deepToString(objArr));
            sb2.append(')');
        }

        private String deepToString(Object obj) {
            if (obj != null && obj.getClass().isArray()) {
                return Arrays.deepToString((Object[]) obj);
            }
            return String.valueOf(obj);
        }

        public <T> T setResult(T t) {
            if (t instanceof Cursor) {
                this.sb.append('\n');
                DatabaseUtils.dumpCursor((Cursor) t, this.sb);
                return t;
            }
            StringBuilder sb = this.sb;
            sb.append(" = ");
            sb.append(deepToString(t));
            return t;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            Log.v(LoggingContentInterface.this.tag, this.sb.toString());
        }
    }

    @Override // android.content.ContentInterface
    public Cursor query(Uri uri, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException {
        Logger logger = new Logger("query", uri, strArr, bundle, cancellationSignal);
        try {
            try {
                Cursor cursor = (Cursor) logger.setResult(this.delegate.query(uri, strArr, bundle, cancellationSignal));
                logger.close();
                return cursor;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public String getType(Uri uri) throws RemoteException {
        Logger logger = new Logger("getType", uri);
        try {
            try {
                String str = (String) logger.setResult(this.delegate.getType(uri));
                logger.close();
                return str;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public String[] getStreamTypes(Uri uri, String str) throws RemoteException {
        Logger logger = new Logger("getStreamTypes", uri, str);
        try {
            try {
                String[] strArr = (String[]) logger.setResult(this.delegate.getStreamTypes(uri, str));
                logger.close();
                return strArr;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public Uri canonicalize(Uri uri) throws RemoteException {
        Logger logger = new Logger("canonicalize", uri);
        try {
            try {
                Uri uri2 = (Uri) logger.setResult(this.delegate.canonicalize(uri));
                logger.close();
                return uri2;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public Uri uncanonicalize(Uri uri) throws RemoteException {
        Logger logger = new Logger("uncanonicalize", uri);
        try {
            try {
                Uri uri2 = (Uri) logger.setResult(this.delegate.uncanonicalize(uri));
                logger.close();
                return uri2;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public boolean refresh(Uri uri, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException {
        Logger logger = new Logger("refresh", uri, bundle, cancellationSignal);
        try {
            try {
                boolean zBooleanValue = ((Boolean) logger.setResult(Boolean.valueOf(this.delegate.refresh(uri, bundle, cancellationSignal)))).booleanValue();
                logger.close();
                return zBooleanValue;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public int checkUriPermission(Uri uri, int i, int i2) throws RemoteException {
        Logger logger = new Logger("checkUriPermission", uri, Integer.valueOf(i), Integer.valueOf(i2));
        try {
            try {
                int iIntValue = ((Integer) logger.setResult(Integer.valueOf(this.delegate.checkUriPermission(uri, i, i2)))).intValue();
                logger.close();
                return iIntValue;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public Uri insert(Uri uri, ContentValues contentValues, Bundle bundle) throws RemoteException {
        Logger logger = new Logger("insert", uri, contentValues, bundle);
        try {
            try {
                Uri uri2 = (Uri) logger.setResult(this.delegate.insert(uri, contentValues, bundle));
                logger.close();
                return uri2;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public int bulkInsert(Uri uri, ContentValues[] contentValuesArr) throws RemoteException {
        Logger logger = new Logger("bulkInsert", uri, contentValuesArr);
        try {
            try {
                int iIntValue = ((Integer) logger.setResult(Integer.valueOf(this.delegate.bulkInsert(uri, contentValuesArr)))).intValue();
                logger.close();
                return iIntValue;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public int delete(Uri uri, Bundle bundle) throws RemoteException {
        Logger logger = new Logger("delete", uri, bundle);
        try {
            try {
                int iIntValue = ((Integer) logger.setResult(Integer.valueOf(this.delegate.delete(uri, bundle)))).intValue();
                logger.close();
                return iIntValue;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public int update(Uri uri, ContentValues contentValues, Bundle bundle) throws RemoteException {
        Logger logger = new Logger("update", uri, contentValues, bundle);
        try {
            try {
                int iIntValue = ((Integer) logger.setResult(Integer.valueOf(this.delegate.update(uri, contentValues, bundle)))).intValue();
                logger.close();
                return iIntValue;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public ParcelFileDescriptor openFile(Uri uri, String str, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        Logger logger = new Logger("openFile", uri, str, cancellationSignal);
        try {
            try {
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) logger.setResult(this.delegate.openFile(uri, str, cancellationSignal));
                logger.close();
                return parcelFileDescriptor;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public AssetFileDescriptor openAssetFile(Uri uri, String str, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        Logger logger = new Logger("openAssetFile", uri, str, cancellationSignal);
        try {
            try {
                AssetFileDescriptor assetFileDescriptor = (AssetFileDescriptor) logger.setResult(this.delegate.openAssetFile(uri, str, cancellationSignal));
                logger.close();
                return assetFileDescriptor;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public AssetFileDescriptor openTypedAssetFile(Uri uri, String str, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        Logger logger = new Logger("openTypedAssetFile", uri, str, bundle, cancellationSignal);
        try {
            try {
                AssetFileDescriptor assetFileDescriptor = (AssetFileDescriptor) logger.setResult(this.delegate.openTypedAssetFile(uri, str, bundle, cancellationSignal));
                logger.close();
                return assetFileDescriptor;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public ContentProviderResult[] applyBatch(String str, ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        Logger logger = new Logger("applyBatch", str, arrayList);
        try {
            try {
                ContentProviderResult[] contentProviderResultArr = (ContentProviderResult[]) logger.setResult(this.delegate.applyBatch(str, arrayList));
                logger.close();
                return contentProviderResultArr;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public Bundle call(String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Logger logger = new Logger("call", str, str2, str3, bundle);
        try {
            try {
                Bundle bundle2 = (Bundle) logger.setResult(this.delegate.call(str, str2, str3, bundle));
                logger.close();
                return bundle2;
            } catch (Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (Throwable th) {
            try {
                logger.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
