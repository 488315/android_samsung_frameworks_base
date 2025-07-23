package android.content;

import android.content.res.AssetFileDescriptor;
import android.database.BulkCursorDescriptor;
import android.database.BulkCursorToCursorAdaptor;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ContentProviderNative.java */
/* loaded from: classes.dex */
final class ContentProviderProxy implements IContentProvider {
    private IBinder mRemote;

    public ContentProviderProxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.mRemote;
    }

    @Override // android.content.IContentProvider
    public Cursor query(AttributionSource attributionSource, Uri uri, String[] strArr, Bundle bundle, ICancellationSignal iCancellationSignal) throws RemoteException {
        BulkCursorToCursorAdaptor bulkCursorToCursorAdaptor = new BulkCursorToCursorAdaptor();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            try {
                obtain.writeInterfaceToken(IContentProvider.descriptor);
                attributionSource.writeToParcel(obtain, 0);
                uri.writeToParcel(obtain, 0);
                int length = strArr != null ? strArr.length : 0;
                obtain.writeInt(length);
                for (int i = 0; i < length; i++) {
                    obtain.writeString(strArr[i]);
                }
                obtain.writeBundle(bundle);
                obtain.writeStrongBinder(bulkCursorToCursorAdaptor.getObserver().asBinder());
                obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
                this.mRemote.transact(1, obtain, obtain2, 0);
                DatabaseUtils.readExceptionFromParcel(obtain2);
                if (obtain2.readInt() != 0) {
                    BulkCursorDescriptor createFromParcel = BulkCursorDescriptor.CREATOR.createFromParcel(obtain2);
                    Binder.copyAllowBlocking(this.mRemote, createFromParcel.cursor != null ? createFromParcel.cursor.asBinder() : null);
                    bulkCursorToCursorAdaptor.initialize(createFromParcel);
                } else {
                    bulkCursorToCursorAdaptor.close();
                    bulkCursorToCursorAdaptor = null;
                }
                return bulkCursorToCursorAdaptor;
            } catch (RemoteException e) {
                bulkCursorToCursorAdaptor.close();
                throw e;
            } catch (RuntimeException e2) {
                bulkCursorToCursorAdaptor.close();
                throw e2;
            }
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public String getType(AttributionSource attributionSource, Uri uri) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            this.mRemote.transact(2, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readString();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void getTypeAsync(AttributionSource attributionSource, Uri uri, RemoteCallback remoteCallback) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            remoteCallback.writeToParcel(obtain, 0);
            this.mRemote.transact(29, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void getTypeAnonymousAsync(Uri uri, RemoteCallback remoteCallback) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            uri.writeToParcel(obtain, 0);
            remoteCallback.writeToParcel(obtain, 0);
            this.mRemote.transact(32, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public Uri insert(AttributionSource attributionSource, Uri uri, ContentValues contentValues, Bundle bundle) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            contentValues.writeToParcel(obtain, 0);
            obtain.writeBundle(bundle);
            this.mRemote.transact(3, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return Uri.CREATOR.createFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int bulkInsert(AttributionSource attributionSource, Uri uri, ContentValues[] contentValuesArr) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeTypedArray(contentValuesArr, 0);
            this.mRemote.transact(13, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public ContentProviderResult[] applyBatch(AttributionSource attributionSource, String str, ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            obtain.writeString(str);
            obtain.writeInt(arrayList.size());
            Iterator<ContentProviderOperation> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(obtain, 0);
            }
            this.mRemote.transact(20, obtain, obtain2, 0);
            DatabaseUtils.readExceptionWithOperationApplicationExceptionFromParcel(obtain2);
            return (ContentProviderResult[]) obtain2.createTypedArray(ContentProviderResult.CREATOR);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int delete(AttributionSource attributionSource, Uri uri, Bundle bundle) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeBundle(bundle);
            this.mRemote.transact(4, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int update(AttributionSource attributionSource, Uri uri, ContentValues contentValues, Bundle bundle) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            contentValues.writeToParcel(obtain, 0);
            obtain.writeBundle(bundle);
            this.mRemote.transact(10, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public ParcelFileDescriptor openFile(AttributionSource attributionSource, Uri uri, String str, ICancellationSignal iCancellationSignal) throws RemoteException, FileNotFoundException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeString(str);
            obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(14, obtain, obtain2, 0);
            DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(obtain2);
            return obtain2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(obtain2) : null;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public AssetFileDescriptor openAssetFile(AttributionSource attributionSource, Uri uri, String str, ICancellationSignal iCancellationSignal) throws RemoteException, FileNotFoundException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeString(str);
            obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(15, obtain, obtain2, 0);
            DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(obtain2);
            return obtain2.readInt() != 0 ? AssetFileDescriptor.CREATOR.createFromParcel(obtain2) : null;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public Bundle call(AttributionSource attributionSource, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            obtain.writeString(str);
            obtain.writeString(str2);
            obtain.writeString(str3);
            obtain.writeBundle(bundle);
            this.mRemote.transact(21, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readBundle();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public String[] getStreamTypes(AttributionSource attributionSource, Uri uri, String str) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeString(str);
            this.mRemote.transact(22, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.createStringArray();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public AssetFileDescriptor openTypedAssetFile(AttributionSource attributionSource, Uri uri, String str, Bundle bundle, ICancellationSignal iCancellationSignal) throws RemoteException, FileNotFoundException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeString(str);
            obtain.writeBundle(bundle);
            obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(23, obtain, obtain2, 0);
            DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(obtain2);
            return obtain2.readInt() != 0 ? AssetFileDescriptor.CREATOR.createFromParcel(obtain2) : null;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public ICancellationSignal createCancellationSignal() throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            this.mRemote.transact(24, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public Uri canonicalize(AttributionSource attributionSource, Uri uri) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            this.mRemote.transact(25, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return Uri.CREATOR.createFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void canonicalizeAsync(AttributionSource attributionSource, Uri uri, RemoteCallback remoteCallback) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            remoteCallback.writeToParcel(obtain, 0);
            this.mRemote.transact(30, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public Uri uncanonicalize(AttributionSource attributionSource, Uri uri) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            this.mRemote.transact(26, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return Uri.CREATOR.createFromParcel(obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void uncanonicalizeAsync(AttributionSource attributionSource, Uri uri, RemoteCallback remoteCallback) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            remoteCallback.writeToParcel(obtain, 0);
            this.mRemote.transact(31, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public boolean refresh(AttributionSource attributionSource, Uri uri, Bundle bundle, ICancellationSignal iCancellationSignal) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeBundle(bundle);
            obtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(27, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt() == 0;
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int checkUriPermission(AttributionSource attributionSource, Uri uri, int i, int i2) throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(obtain, 0);
            uri.writeToParcel(obtain, 0);
            obtain.writeInt(i);
            obtain.writeInt(i2);
            this.mRemote.transact(28, obtain, obtain2, 0);
            DatabaseUtils.readExceptionFromParcel(obtain2);
            return obtain2.readInt();
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }
}
