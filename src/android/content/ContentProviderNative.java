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
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws Throwable {
        String[] strArr;
        Throwable th;
        int i3 = 0;
        try {
            if (i == 1) {
                parcel.enforceInterface(IContentProvider.descriptor);
                AttributionSource attributionSourceCreateFromParcel = AttributionSource.CREATOR.createFromParcel(parcel);
                Uri uriCreateFromParcel = Uri.CREATOR.createFromParcel(parcel);
                int i4 = parcel.readInt();
                CursorToBulkCursorAdaptor cursorToBulkCursorAdaptor = null;
                if (i4 > 0) {
                    String[] strArr2 = new String[i4];
                    for (int i5 = 0; i5 < i4; i5++) {
                        strArr2[i5] = parcel.readString();
                    }
                    strArr = strArr2;
                } else {
                    strArr = null;
                }
                Bundle bundle = parcel.readBundle();
                IContentObserver iContentObserverAsInterface = IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                Cursor cursorQuery = query(attributionSourceCreateFromParcel, uriCreateFromParcel, strArr, bundle, ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                if (cursorQuery != null) {
                    try {
                        CursorToBulkCursorAdaptor cursorToBulkCursorAdaptor2 = new CursorToBulkCursorAdaptor(cursorQuery, iContentObserverAsInterface, getProviderName());
                        try {
                            BulkCursorDescriptor bulkCursorDescriptor = cursorToBulkCursorAdaptor2.getBulkCursorDescriptor();
                            try {
                                parcel2.writeNoException();
                                parcel2.writeInt(1);
                                bulkCursorDescriptor.writeToParcel(parcel2, 1);
                            } catch (Throwable th2) {
                                th = th2;
                                cursorQuery = null;
                                if (cursorToBulkCursorAdaptor != null) {
                                    cursorToBulkCursorAdaptor.close();
                                }
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                    throw th;
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            cursorQuery = null;
                            cursorToBulkCursorAdaptor = cursorToBulkCursorAdaptor2;
                        }
                    } catch (Throwable th4) {
                        th = th4;
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
                Uri uriInsert = insert(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), ContentValues.CREATOR.createFromParcel(parcel), parcel.readBundle());
                parcel2.writeNoException();
                Uri.writeToParcel(parcel2, uriInsert);
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface(IContentProvider.descriptor);
                int iDelete = delete(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readBundle());
                parcel2.writeNoException();
                parcel2.writeInt(iDelete);
                return true;
            }
            if (i != 10) {
                switch (i) {
                    case 13:
                        parcel.enforceInterface(IContentProvider.descriptor);
                        int iBulkInsert = bulkInsert(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), (ContentValues[]) parcel.createTypedArray(ContentValues.CREATOR));
                        parcel2.writeNoException();
                        parcel2.writeInt(iBulkInsert);
                        return true;
                    case 14:
                        parcel.enforceInterface(IContentProvider.descriptor);
                        ParcelFileDescriptor parcelFileDescriptorOpenFile = openFile(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readString(), ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (parcelFileDescriptorOpenFile != null) {
                            parcel2.writeInt(1);
                            parcelFileDescriptorOpenFile.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    case 15:
                        parcel.enforceInterface(IContentProvider.descriptor);
                        AssetFileDescriptor assetFileDescriptorOpenAssetFile = openAssetFile(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readString(), ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        if (assetFileDescriptorOpenAssetFile != null) {
                            parcel2.writeInt(1);
                            assetFileDescriptorOpenAssetFile.writeToParcel(parcel2, 1);
                        } else {
                            parcel2.writeInt(0);
                        }
                        return true;
                    default:
                        switch (i) {
                            case 20:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                AttributionSource attributionSourceCreateFromParcel2 = AttributionSource.CREATOR.createFromParcel(parcel);
                                String string = parcel.readString();
                                int i6 = parcel.readInt();
                                ArrayList<ContentProviderOperation> arrayList = new ArrayList<>(i6);
                                for (int i7 = 0; i7 < i6; i7++) {
                                    arrayList.add(i7, ContentProviderOperation.CREATOR.createFromParcel(parcel));
                                }
                                ContentProviderResult[] contentProviderResultArrApplyBatch = applyBatch(attributionSourceCreateFromParcel2, string, arrayList);
                                parcel2.writeNoException();
                                parcel2.writeTypedArray(contentProviderResultArrApplyBatch, 0);
                                return true;
                            case 21:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                Bundle bundleCall = call(AttributionSource.CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readBundle());
                                parcel2.writeNoException();
                                parcel2.writeBundle(bundleCall);
                                return true;
                            case 22:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                String[] streamTypes = getStreamTypes(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readString());
                                parcel2.writeNoException();
                                parcel2.writeStringArray(streamTypes);
                                return true;
                            case 23:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                AssetFileDescriptor assetFileDescriptorOpenTypedAssetFile = openTypedAssetFile(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readBundle(), ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                if (assetFileDescriptorOpenTypedAssetFile != null) {
                                    parcel2.writeInt(1);
                                    assetFileDescriptorOpenTypedAssetFile.writeToParcel(parcel2, 1);
                                } else {
                                    parcel2.writeInt(0);
                                }
                                return true;
                            case 24:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                ICancellationSignal iCancellationSignalCreateCancellationSignal = createCancellationSignal();
                                parcel2.writeNoException();
                                parcel2.writeStrongBinder(iCancellationSignalCreateCancellationSignal.asBinder());
                                return true;
                            case 25:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                Uri uriCanonicalize = canonicalize(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel));
                                parcel2.writeNoException();
                                Uri.writeToParcel(parcel2, uriCanonicalize);
                                return true;
                            case 26:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                Uri uriUncanonicalize = uncanonicalize(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel));
                                parcel2.writeNoException();
                                Uri.writeToParcel(parcel2, uriUncanonicalize);
                                return true;
                            case 27:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                boolean zRefresh = refresh(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readBundle(), ICancellationSignal.Stub.asInterface(parcel.readStrongBinder()));
                                parcel2.writeNoException();
                                if (!zRefresh) {
                                    i3 = -1;
                                }
                                parcel2.writeInt(i3);
                                return true;
                            case 28:
                                parcel.enforceInterface(IContentProvider.descriptor);
                                int iCheckUriPermission = checkUriPermission(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), parcel.readInt(), parcel.readInt());
                                parcel2.writeNoException();
                                parcel2.writeInt(iCheckUriPermission);
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
            int iUpdate = update(AttributionSource.CREATOR.createFromParcel(parcel), Uri.CREATOR.createFromParcel(parcel), ContentValues.CREATOR.createFromParcel(parcel), parcel.readBundle());
            parcel2.writeNoException();
            parcel2.writeInt(iUpdate);
            return true;
        } catch (Exception e) {
            DatabaseUtils.writeExceptionToParcel(parcel2, e);
            return true;
        }
    }
}
