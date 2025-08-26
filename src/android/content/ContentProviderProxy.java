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
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            try {
                parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
                attributionSource.writeToParcel(parcelObtain, 0);
                uri.writeToParcel(parcelObtain, 0);
                int length = strArr != null ? strArr.length : 0;
                parcelObtain.writeInt(length);
                for (int i = 0; i < length; i++) {
                    parcelObtain.writeString(strArr[i]);
                }
                parcelObtain.writeBundle(bundle);
                parcelObtain.writeStrongBinder(bulkCursorToCursorAdaptor.getObserver().asBinder());
                parcelObtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
                this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                DatabaseUtils.readExceptionFromParcel(parcelObtain2);
                if (parcelObtain2.readInt() != 0) {
                    BulkCursorDescriptor bulkCursorDescriptorCreateFromParcel = BulkCursorDescriptor.CREATOR.createFromParcel(parcelObtain2);
                    Binder.copyAllowBlocking(this.mRemote, bulkCursorDescriptorCreateFromParcel.cursor != null ? bulkCursorDescriptorCreateFromParcel.cursor.asBinder() : null);
                    bulkCursorToCursorAdaptor.initialize(bulkCursorDescriptorCreateFromParcel);
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
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public String getType(AttributionSource attributionSource, Uri uri) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readString();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void getTypeAsync(AttributionSource attributionSource, Uri uri, RemoteCallback remoteCallback) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            remoteCallback.writeToParcel(parcelObtain, 0);
            this.mRemote.transact(29, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void getTypeAnonymousAsync(Uri uri, RemoteCallback remoteCallback) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            uri.writeToParcel(parcelObtain, 0);
            remoteCallback.writeToParcel(parcelObtain, 0);
            this.mRemote.transact(32, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public Uri insert(AttributionSource attributionSource, Uri uri, ContentValues contentValues, Bundle bundle) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            contentValues.writeToParcel(parcelObtain, 0);
            parcelObtain.writeBundle(bundle);
            this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return Uri.CREATOR.createFromParcel(parcelObtain2);
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int bulkInsert(AttributionSource attributionSource, Uri uri, ContentValues[] contentValuesArr) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            parcelObtain.writeTypedArray(contentValuesArr, 0);
            this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readInt();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public ContentProviderResult[] applyBatch(AttributionSource attributionSource, String str, ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            parcelObtain.writeString(str);
            parcelObtain.writeInt(arrayList.size());
            Iterator<ContentProviderOperation> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(parcelObtain, 0);
            }
            this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionWithOperationApplicationExceptionFromParcel(parcelObtain2);
            return (ContentProviderResult[]) parcelObtain2.createTypedArray(ContentProviderResult.CREATOR);
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int delete(AttributionSource attributionSource, Uri uri, Bundle bundle) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            parcelObtain.writeBundle(bundle);
            this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readInt();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int update(AttributionSource attributionSource, Uri uri, ContentValues contentValues, Bundle bundle) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            contentValues.writeToParcel(parcelObtain, 0);
            parcelObtain.writeBundle(bundle);
            this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readInt();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public ParcelFileDescriptor openFile(AttributionSource attributionSource, Uri uri, String str, ICancellationSignal iCancellationSignal) throws RemoteException, FileNotFoundException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            parcelObtain.writeString(str);
            parcelObtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public AssetFileDescriptor openAssetFile(AttributionSource attributionSource, Uri uri, String str, ICancellationSignal iCancellationSignal) throws RemoteException, FileNotFoundException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            parcelObtain.writeString(str);
            parcelObtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readInt() != 0 ? AssetFileDescriptor.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public Bundle call(AttributionSource attributionSource, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            parcelObtain.writeString(str);
            parcelObtain.writeString(str2);
            parcelObtain.writeString(str3);
            parcelObtain.writeBundle(bundle);
            this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readBundle();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public String[] getStreamTypes(AttributionSource attributionSource, Uri uri, String str) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            parcelObtain.writeString(str);
            this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return parcelObtain2.createStringArray();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public AssetFileDescriptor openTypedAssetFile(AttributionSource attributionSource, Uri uri, String str, Bundle bundle, ICancellationSignal iCancellationSignal) throws RemoteException, FileNotFoundException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            parcelObtain.writeString(str);
            parcelObtain.writeBundle(bundle);
            parcelObtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readInt() != 0 ? AssetFileDescriptor.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public ICancellationSignal createCancellationSignal() throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public Uri canonicalize(AttributionSource attributionSource, Uri uri) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return Uri.CREATOR.createFromParcel(parcelObtain2);
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void canonicalizeAsync(AttributionSource attributionSource, Uri uri, RemoteCallback remoteCallback) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            remoteCallback.writeToParcel(parcelObtain, 0);
            this.mRemote.transact(30, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public Uri uncanonicalize(AttributionSource attributionSource, Uri uri) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return Uri.CREATOR.createFromParcel(parcelObtain2);
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public void uncanonicalizeAsync(AttributionSource attributionSource, Uri uri, RemoteCallback remoteCallback) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            remoteCallback.writeToParcel(parcelObtain, 0);
            this.mRemote.transact(31, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public boolean refresh(AttributionSource attributionSource, Uri uri, Bundle bundle, ICancellationSignal iCancellationSignal) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            parcelObtain.writeBundle(bundle);
            parcelObtain.writeStrongBinder(iCancellationSignal != null ? iCancellationSignal.asBinder() : null);
            this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readInt() == 0;
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    @Override // android.content.IContentProvider
    public int checkUriPermission(AttributionSource attributionSource, Uri uri, int i, int i2) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(IContentProvider.descriptor);
            attributionSource.writeToParcel(parcelObtain, 0);
            uri.writeToParcel(parcelObtain, 0);
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
            DatabaseUtils.readExceptionFromParcel(parcelObtain2);
            return parcelObtain2.readInt();
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }
}
