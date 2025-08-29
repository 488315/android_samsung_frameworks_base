package com.samsung.android.sivs.ai.sdkcommon.translation;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public interface INeuralTranslationService extends IInterface {

    public class _Parcel {
        /* renamed from: -$$Nest$smreadTypedObject, reason: not valid java name */
        public static Object m3329$$Nest$smreadTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* renamed from: -$$Nest$smwriteTypedList, reason: not valid java name */
        public static void m3330$$Nest$smwriteTypedList(Parcel parcel, List list) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                Parcelable parcelable = (Parcelable) list.get(i);
                if (parcelable != null) {
                    parcel.writeInt(1);
                    parcelable.writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
            }
        }
    }

    List chunkText();

    void clear();

    void clearWithSourceId();

    void dispose();

    Map getLanguageDirectionStateMap();

    String getResourcePackPackageName();

    List getSourceLanguageList();

    List getTargetLanguageList();

    String identifyLanguage(Bundle bundle);

    List identifyLanguageAndGetCandidate();

    String identifyLanguagePackCode();

    List identifyLanguageWithList();

    boolean isAvailableDirection();

    boolean isTaggedTranslationSupported();

    void refresh();

    void translate();

    public abstract class Stub extends Binder implements INeuralTranslationService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements INeuralTranslationService {
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
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService
            public final String identifyLanguage(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
                    parcelObtain.writeInt(1);
                    bundle.writeToParcel(parcelObtain, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService
            public final void refresh() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationService");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
                    final IBinder strongBinder = parcel.readStrongBinder();
                    if (strongBinder != null) {
                        IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationCallback");
                        if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof INeuralTranslationCallback$Stub$Proxy)) {
                            new IInterface(strongBinder) { // from class: com.samsung.android.sivs.ai.sdkcommon.translation.INeuralTranslationCallback$Stub$Proxy
                                public final IBinder mRemote;

                                {
                                    this.mRemote = strongBinder;
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
                    boolean zIsAvailableDirection = isAvailableDirection();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsAvailableDirection ? 1 : 0);
                    return true;
                case 8:
                    String strIdentifyLanguage = identifyLanguage((Bundle) _Parcel.m3329$$Nest$smreadTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeString(strIdentifyLanguage);
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
                    List<String> listIdentifyLanguageWithList = identifyLanguageWithList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(listIdentifyLanguageWithList);
                    return true;
                case 12:
                    List listIdentifyLanguageAndGetCandidate = identifyLanguageAndGetCandidate();
                    parcel2.writeNoException();
                    _Parcel.m3330$$Nest$smwriteTypedList(parcel2, listIdentifyLanguageAndGetCandidate);
                    return true;
                case 13:
                    String strIdentifyLanguagePackCode = identifyLanguagePackCode();
                    parcel2.writeNoException();
                    parcel2.writeString(strIdentifyLanguagePackCode);
                    return true;
                case 14:
                    parcel.readString();
                    parcel.readString();
                    parcel.readInt();
                    List listChunkText = chunkText();
                    parcel2.writeNoException();
                    _Parcel.m3330$$Nest$smwriteTypedList(parcel2, listChunkText);
                    return true;
                case 15:
                    parcel.readString();
                    clearWithSourceId();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    parcel.readString();
                    parcel.readString();
                    boolean zIsTaggedTranslationSupported = isTaggedTranslationSupported();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsTaggedTranslationSupported ? 1 : 0);
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
