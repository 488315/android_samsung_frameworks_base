package android.content;

import android.content.res.AssetFileDescriptor;
import android.database.BulkCursorDescriptor;
import android.database.Cursor;
import android.database.CursorToBulkCursorAdaptor;
import android.database.DatabaseUtils;
import android.database.IContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.RemoteException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class ContentProviderNative extends Binder implements IContentProvider {
    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    public abstract String getProviderName();

    public ContentProviderNative() {
        attachInterface(this, IContentProvider.descriptor);
    }

    public static IContentProvider asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IContentProvider iContentProvider = (IContentProvider) iBinder.queryLocalInterface(IContentProvider.descriptor);
        return iContentProvider != null ? iContentProvider : new ContentProviderProxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        String[] strArr;
        Throwable th;
        BulkCursorDescriptor bulkCursorDescriptor;
        int i3 = 0;
        try {
            if (i == 1) {
                parcel.enforceInterface(IContentProvider.descriptor);
                AttributionSource createFromParcel = AttributionSource.CREATOR.createFromParcel(parcel);
                Uri createFromParcel2 = Uri.CREATOR.createFromParcel(parcel);
                int readInt = parcel.readInt();
                CursorToBulkCursorAdaptor cursorToBulkCursorAdaptor = null;
                if (readInt > 0) {
                    String[] strArr2 = new String[readInt];
                    for (int i4 = 0; i4 < readInt; i4++) {
                        strArr2[i4] = parcel.readString();
                    }
                    strArr = strArr2;
                } else {
                    strArr = null;
                }
                Bundle readBundle = parcel.readBundle();
                IContentObserver asInterface = IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                Cursor query = query(createFromParcel, createFromParcel2, strArr, readBundle, ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                if (query != null) {
                    try {
                        CursorToBulkCursorAdaptor cursorToBulkCursorAdaptor2 = new CursorToBulkCursorAdaptor(query, asInterface, getProviderName());
                        try {
                            bulkCursorDescriptor = cursorToBulkCursorAdaptor2.getBulkCursorDescriptor();
                        } catch (Throwable th2) {
                            th = th2;
                            query = null;
                            cursorToBulkCursorAdaptor = cursorToBulkCursorAdaptor2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                    try {
                        parcel2.writeNoException();
                        parcel2.writeInt(1);
                        bulkCursorDescriptor.writeToParcel(parcel2, 1);
                    } catch (Throwable th4) {
                        th = th4;
                        query = null;
                        if (cursorToBulkCursorAdaptor != null) {
                            cursorToBulkCursorAdaptor.close();
                        }
                        if (query != null) {
                            query.close();
                            throw th;
                        }
                        throw th;
                    }
                } else {
                    parcel2.writeNoException();
                    parcel2.writeInt(0);
                }
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(IContentProvider.descriptor);
                String type = getType(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel));
                parcel2.writeNoException();
                parcel2.writeString(type);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(IContentProvider.descriptor);
                Uri insert = insert(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), ContentValues.CREATOR.createFromParcel(parcel), parcel.readBundle());
                parcel2.writeNoException();
                Uri.writeToParcel(parcel2, insert);
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface(IContentProvider.descriptor);
                int delete = delete(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readBundle());
                parcel2.writeNoException();
                parcel2.writeInt(delete);
                return true;
            }
            if (i != 10) {
                switch (i) {
                    case 13:
                        parcel.enforceInterface(IContentProvider.descriptor);
                        int bulkInsert = bulkInsert(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), (ContentValues[]) parcel.createTypedArray(ContentValues.CREATOR));
                        parcel2.writeNoException();
                        parcel2.writeInt(bulkInsert);
                        return true;
                    case 14:
                        parcel.enforceInterface(IContentProvider.descriptor);
                        ParcelFileDescriptor openFile = openFile(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readString(), ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (openFile != null) {
                            parcel2.writeInt(1);
                            openFile.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 15:
                        parcel.enforceInterface(IContentProvider.descriptor);
                        AssetFileDescriptor openAssetFile = openAssetFile(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readString(), ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (openAssetFile != null) {
                            parcel2.writeInt(1);
                            openAssetFile.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    default:
                        switch (i) {
                            case 20:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                AttributionSource createFromParcel3 = AttributionSource.CREATOR.createFromParcel(parcel);
                                String readString = parcel.readString();
                                int readInt2 = parcel.readInt();
                                ArrayList<ContentProviderOperation> arrayList = new ArrayList<>(readInt2);
                                for (int i5 = 0; i5 < readInt2; i5++) {
                                    arrayList.add(i5, ContentProviderOperation.CREATOR.createFromParcel(parcel));
                                }
                                ContentProviderResult[] applyBatch = applyBatch(createFromParcel3, readString, arrayList);
                                parcel2.writeNoException();
                                parcel2.writeTypedArray(applyBatch, 0);
                                return true;
                            case 21:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                Bundle call = call(AttributionSource.CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readBundle());
                                parcel2.writeNoException();
                                parcel2.writeBundle(call);
                                return true;
                            case 22:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                String[] streamTypes = getStreamTypes(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readString());
                                parcel2.writeNoException();
                                parcel2.writeStringArray(streamTypes);
                                return true;
                            case 23:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                AssetFileDescriptor openTypedAssetFile = openTypedAssetFile(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readBundle(), ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                if (openTypedAssetFile != null) {
                                    parcel2.writeInt(1);
                                    openTypedAssetFile.writeToParcel(parcel2, 1);
                                } else {
                                    parcel2.writeInt(0);
                                }
                                return true;
                            case 24:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                ICancellationSignal createCancellationSignal = createCancellationSignal();
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(createCancellationSignal.asBinder());
                                return true;
                            case 25:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                Uri canonicalize = canonicalize(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel));
                                parcel2.writeNoException();
                                Uri.writeToParcel(parcel2, canonicalize);
                                return true;
                            case 26:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                Uri uncanonicalize = uncanonicalize(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel));
                                parcel2.writeNoException();
                                Uri.writeToParcel(parcel2, uncanonicalize);
                                return true;
                            case 27:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                boolean refresh = refresh(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readBundle(), ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                if (!refresh) {
                                    i3 = -1;
                                }
                                parcel2.writeInt(i3);
                                return true;
                            case 28:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                int checkUriPermission = checkUriPermission(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readInt(), parcel.readInt());
                                parcel2.writeNoException();
                                parcel2.writeInt(checkUriPermission);
                                return true;
                            case 29:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                getTypeAsync(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), RemoteCallback.CREATOR.createFromParcel(parcel));
                                return true;
                            case 30:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                canonicalizeAsync(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), RemoteCallback.CREATOR.createFromParcel(parcel));
                                return true;
                            case 31:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                uncanonicalizeAsync(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), RemoteCallback.CREATOR.createFromParcel(parcel));
                                return true;
                            case 32:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                getTypeAnonymousAsync(Uri.CREATOR.createFromParcel(parcel), RemoteCallback.CREATOR.createFromParcel(parcel));
                                return true;
                            default:
                                return super.onTransact(i, parcel, parcel2, i2);
                        }
                }
            }
            parcel.enforceInterface(IContentProvider.descriptor);
            int update = update(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), ContentValues.CREATOR.createFromParcel(parcel), parcel.readBundle());
            parcel2.writeNoException();
            parcel2.writeInt(update);
            return true;
        } catch (Exception e) {
            DatabaseUtils.writeExceptionToParcel(parcel2, e);
            return true;
        }
    }
}
