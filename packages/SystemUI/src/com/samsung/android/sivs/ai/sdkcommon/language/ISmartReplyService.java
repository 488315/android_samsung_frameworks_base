package com.samsung.android.sivs.ai.sdkcommon.language;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.sdk.scs.ai.language.service.LlmServiceObserver2;
import com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2;
import com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes4.dex */
public interface ISmartReplyService extends IInterface {
    void identifyLanguage();

    void reply();

    void replyWithCategory(Map map, String str, String str2, LlmServiceObserver2 llmServiceObserver2, Map map2);

    void replyWithHeader();

    void replyWithHeader2();

    void replyWithHeader3(Map map, String str, LlmServiceObserver2 llmServiceObserver2, Map map2);

    void unLoadModel();

    public abstract class Stub extends Binder implements ISmartReplyService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements ISmartReplyService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService
            public final void replyWithCategory(Map map, String str, String str2, LlmServiceObserver2 llmServiceObserver2, Map map2) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
                    HashMap map3 = (HashMap) map;
                    parcelObtain.writeInt(map3.size());
                    map3.forEach(new ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0(parcelObtain, 0));
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(llmServiceObserver2);
                    parcelObtain.writeInt(map2.size());
                    ((LinkedHashMap) map2).forEach(new ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0(parcelObtain, 1));
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService
            public final void replyWithHeader3(Map map, String str, LlmServiceObserver2 llmServiceObserver2, Map map2) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
                    HashMap map3 = (HashMap) map;
                    parcelObtain.writeInt(map3.size());
                    map3.forEach(new ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0(parcelObtain, 2));
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(llmServiceObserver2);
                    parcelObtain.writeInt(map2.size());
                    ((LinkedHashMap) map2).forEach(new ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0(parcelObtain, 3));
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService
            public final void unLoadModel() {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, final Parcel parcel, Parcel parcel2, int i2) {
            final HashMap map;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.readString();
                    final IBinder strongBinder = parcel.readStrongBinder();
                    if (strongBinder != null) {
                        IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver");
                        if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ILlmServiceObserver$Stub$Proxy)) {
                            new IInterface(strongBinder) { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver$Stub$Proxy
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
                    reply();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    map = i3 >= 0 ? new HashMap() : null;
                    final int i4 = 0;
                    IntStream.range(0, i3).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i5) {
                            switch (i4) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map2 = map;
                                    int i6 = ISmartReplyService.Stub.$r8$clinit;
                                    map2.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map3 = map;
                                    int i7 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map4 = map;
                                    int i8 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map5 = map;
                                    int i9 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map6 = map;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map7 = map;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map8 = map;
                                    int i12 = ISmartReplyService.Stub.$r8$clinit;
                                    map8.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    parcel.readString();
                    final IBinder strongBinder2 = parcel.readStrongBinder();
                    if (strongBinder2 != null) {
                        IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver");
                        if (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof ILlmServiceObserver$Stub$Proxy)) {
                            new IInterface(strongBinder2) { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver$Stub$Proxy
                                public final IBinder mRemote;

                                {
                                    this.mRemote = strongBinder2;
                                }

                                @Override // android.os.IInterface
                                public final IBinder asBinder() {
                                    return this.mRemote;
                                }
                            };
                        }
                    }
                    replyWithHeader();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    map = i5 >= 0 ? new HashMap() : null;
                    final int i6 = 1;
                    IntStream.range(0, i5).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i52) {
                            switch (i6) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map2 = map;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map2.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map3 = map;
                                    int i7 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map4 = map;
                                    int i8 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map5 = map;
                                    int i9 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map6 = map;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map7 = map;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map8 = map;
                                    int i12 = ISmartReplyService.Stub.$r8$clinit;
                                    map8.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    parcel.readString();
                    ILlmServiceObserver2.Stub.asInterface(parcel.readStrongBinder());
                    replyWithHeader2();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    final HashMap map2 = i7 < 0 ? null : new HashMap();
                    final int i8 = 2;
                    IntStream.range(0, i7).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i52) {
                            switch (i8) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map22 = map2;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map22.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map3 = map2;
                                    int i72 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map4 = map2;
                                    int i82 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map5 = map2;
                                    int i9 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map6 = map2;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map7 = map2;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map8 = map2;
                                    int i12 = ISmartReplyService.Stub.$r8$clinit;
                                    map8.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    String string = parcel.readString();
                    ILlmServiceObserver2 iLlmServiceObserver2AsInterface = ILlmServiceObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int i9 = parcel.readInt();
                    map = i9 >= 0 ? new HashMap() : null;
                    final int i10 = 3;
                    IntStream.range(0, i9).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i52) {
                            switch (i10) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map22 = map;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map22.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map3 = map;
                                    int i72 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map4 = map;
                                    int i82 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map5 = map;
                                    int i92 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map6 = map;
                                    int i102 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map7 = map;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map8 = map;
                                    int i12 = ISmartReplyService.Stub.$r8$clinit;
                                    map8.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    replyWithHeader3(map2, string, (LlmServiceObserver2) iLlmServiceObserver2AsInterface, map);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    unLoadModel();
                    return true;
                case 6:
                    int i11 = parcel.readInt();
                    map = i11 >= 0 ? new HashMap() : null;
                    final int i12 = 4;
                    IntStream.range(0, i11).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i52) {
                            switch (i12) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map22 = map;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map22.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map3 = map;
                                    int i72 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map4 = map;
                                    int i82 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map5 = map;
                                    int i92 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map6 = map;
                                    int i102 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map7 = map;
                                    int i112 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map8 = map;
                                    int i122 = ISmartReplyService.Stub.$r8$clinit;
                                    map8.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    parcel.readString();
                    ILlmServiceObserver2.Stub.asInterface(parcel.readStrongBinder());
                    identifyLanguage();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i13 = parcel.readInt();
                    final HashMap map3 = i13 < 0 ? null : new HashMap();
                    final int i14 = 5;
                    IntStream.range(0, i13).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i52) {
                            switch (i14) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map22 = map3;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map22.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map32 = map3;
                                    int i72 = ISmartReplyService.Stub.$r8$clinit;
                                    map32.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map4 = map3;
                                    int i82 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map5 = map3;
                                    int i92 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map6 = map3;
                                    int i102 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map7 = map3;
                                    int i112 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map8 = map3;
                                    int i122 = ISmartReplyService.Stub.$r8$clinit;
                                    map8.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    ILlmServiceObserver2 iLlmServiceObserver2AsInterface2 = ILlmServiceObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int i15 = parcel.readInt();
                    final HashMap map4 = i15 >= 0 ? new HashMap() : null;
                    final int i16 = 6;
                    IntStream.range(0, i15).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i52) {
                            switch (i16) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map22 = map4;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map22.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map32 = map4;
                                    int i72 = ISmartReplyService.Stub.$r8$clinit;
                                    map32.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map42 = map4;
                                    int i82 = ISmartReplyService.Stub.$r8$clinit;
                                    map42.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map5 = map4;
                                    int i92 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map6 = map4;
                                    int i102 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map7 = map4;
                                    int i112 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map8 = map4;
                                    int i122 = ISmartReplyService.Stub.$r8$clinit;
                                    map8.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    replyWithCategory(map3, string2, string3, (LlmServiceObserver2) iLlmServiceObserver2AsInterface2, map4);
                    parcel2.writeNoException();
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
