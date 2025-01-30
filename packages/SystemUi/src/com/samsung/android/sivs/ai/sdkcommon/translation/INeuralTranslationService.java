package com.samsung.android.sivs.ai.sdkcommon.translation;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface INeuralTranslationService extends IInterface {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class _Parcel {
        /* renamed from: -$$Nest$smreadTypedObject, reason: not valid java name */
        public static Object m2800$$Nest$smreadTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }
    }

    void clear();

    void dispose();

    Map getLanguageDirectionStateMap();

    String getResourcePackPackageName();

    List getSourceLanguageList();

    List getTargetLanguageList();

    String identifyLanguage(Bundle bundle);

    List identifyLanguageAndGetCandidate();

    List identifyLanguageWithList();

    boolean isAvailableDirection();

    void refresh();

    void translate();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements INeuralTranslationService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Proxy implements INeuralTranslationService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService
            public final Map getLanguageDirectionStateMap() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService
            public final String identifyLanguage(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService
            public final void refresh() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
                return true;
            }
            switch (i) {
                case 1:
                    refresh();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    final IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder != null) {
                        IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationCallback");
                        if (queryLocalInterface == null || !(queryLocalInterface instanceof INeuralTranslationCallback)) {
                            new INeuralTranslationCallback(readStrongBinder) { // from class: com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationCallback$Stub$Proxy
                                public final IBinder mRemote;

                                {
                                    this.mRemote = readStrongBinder;
                                }

                                @Override // android.os.IInterface
                                public final IBinder asBinder() {
                                    return this.mRemote;
                                }
                            };
                        }
                    }
                    translate();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    clear();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    dispose();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    List<String> sourceLanguageList = getSourceLanguageList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(sourceLanguageList);
                    return true;
                case 6:
                    parcel.readString();
                    List<String> targetLanguageList = getTargetLanguageList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(targetLanguageList);
                    return true;
                case 7:
                    boolean isAvailableDirection = isAvailableDirection();
                    parcel2.writeNoException();
                    parcel2.writeInt(isAvailableDirection ? 1 : 0);
                    return true;
                case 8:
                    String identifyLanguage = identifyLanguage((Bundle) _Parcel.m2800$$Nest$smreadTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeString(identifyLanguage);
                    return true;
                case 9:
                    Map languageDirectionStateMap = getLanguageDirectionStateMap();
                    parcel2.writeNoException();
                    parcel2.writeMap(languageDirectionStateMap);
                    return true;
                case 10:
                    parcel.readString();
                    parcel.readString();
                    String resourcePackPackageName = getResourcePackPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(resourcePackPackageName);
                    return true;
                case 11:
                    List<String> identifyLanguageWithList = identifyLanguageWithList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(identifyLanguageWithList);
                    return true;
                case 12:
                    List identifyLanguageAndGetCandidate = identifyLanguageAndGetCandidate();
                    parcel2.writeNoException();
                    if (identifyLanguageAndGetCandidate == null) {
                        parcel2.writeInt(-1);
                    } else {
                        int size = identifyLanguageAndGetCandidate.size();
                        parcel2.writeInt(size);
                        for (int i3 = 0; i3 < size; i3++) {
                            Parcelable parcelable = (Parcelable) identifyLanguageAndGetCandidate.get(i3);
                            if (parcelable != null) {
                                parcel2.writeInt(1);
                                parcelable.writeToParcel(parcel2, 1);
                            } else {
                                parcel2.writeInt(0);
                            }
                        }
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
