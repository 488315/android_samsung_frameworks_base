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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ISmartReplyService extends IInterface {
    void identifyLanguage();

    void reply();

    void replyWithCategory(Map map, String str, String str2, LlmServiceObserver2 llmServiceObserver2, Map map2);

    void replyWithHeader();

    void replyWithHeader2();

    void replyWithHeader3(Map map, String str, LlmServiceObserver2 llmServiceObserver2, Map map2);

    void unLoadModel();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ISmartReplyService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
                    HashMap hashMap = (HashMap) map;
                    obtain.writeInt(hashMap.size());
                    hashMap.forEach(new ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0(obtain, 0));
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(llmServiceObserver2);
                    obtain.writeInt(map2.size());
                    ((LinkedHashMap) map2).forEach(new ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0(obtain, 1));
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService
            public final void replyWithHeader3(Map map, String str, LlmServiceObserver2 llmServiceObserver2, Map map2) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
                    HashMap hashMap = (HashMap) map;
                    obtain.writeInt(hashMap.size());
                    hashMap.forEach(new ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0(obtain, 2));
                    obtain.writeString(str);
                    obtain.writeStrongInterface(llmServiceObserver2);
                    obtain.writeInt(map2.size());
                    ((LinkedHashMap) map2).forEach(new ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0(obtain, 3));
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService
            public final void unLoadModel() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, final Parcel parcel, Parcel parcel2, int i2) {
            final HashMap hashMap;
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
                    final IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder != null) {
                        IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver");
                        if (queryLocalInterface == null || !(queryLocalInterface instanceof ILlmServiceObserver$Stub$Proxy)) {
                            new IInterface(readStrongBinder) { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver$Stub$Proxy
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
                    reply();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    hashMap = readInt >= 0 ? new HashMap() : null;
                    final int i3 = 0;
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i4) {
                            switch (i3) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map = hashMap;
                                    int i5 = ISmartReplyService.Stub.$r8$clinit;
                                    map.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map2 = hashMap;
                                    int i6 = ISmartReplyService.Stub.$r8$clinit;
                                    map2.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map3 = hashMap;
                                    int i7 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map4 = hashMap;
                                    int i8 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map5 = hashMap;
                                    int i9 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map6 = hashMap;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map7 = hashMap;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    parcel.readString();
                    final IBinder readStrongBinder2 = parcel.readStrongBinder();
                    if (readStrongBinder2 != null) {
                        IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver");
                        if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof ILlmServiceObserver$Stub$Proxy)) {
                            new IInterface(readStrongBinder2) { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver$Stub$Proxy
                                public final IBinder mRemote;

                                {
                                    this.mRemote = readStrongBinder2;
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
                    int readInt2 = parcel.readInt();
                    hashMap = readInt2 >= 0 ? new HashMap() : null;
                    final int i4 = 1;
                    IntStream.range(0, readInt2).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i42) {
                            switch (i4) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map = hashMap;
                                    int i5 = ISmartReplyService.Stub.$r8$clinit;
                                    map.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map2 = hashMap;
                                    int i6 = ISmartReplyService.Stub.$r8$clinit;
                                    map2.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map3 = hashMap;
                                    int i7 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map4 = hashMap;
                                    int i8 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map5 = hashMap;
                                    int i9 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map6 = hashMap;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map7 = hashMap;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel9.readString(), parcel9.readString());
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
                    int readInt3 = parcel.readInt();
                    final HashMap hashMap2 = readInt3 < 0 ? null : new HashMap();
                    final int i5 = 2;
                    IntStream.range(0, readInt3).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i42) {
                            switch (i5) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map = hashMap2;
                                    int i52 = ISmartReplyService.Stub.$r8$clinit;
                                    map.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map2 = hashMap2;
                                    int i6 = ISmartReplyService.Stub.$r8$clinit;
                                    map2.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map3 = hashMap2;
                                    int i7 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map4 = hashMap2;
                                    int i8 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map5 = hashMap2;
                                    int i9 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map6 = hashMap2;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map7 = hashMap2;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    String readString = parcel.readString();
                    ILlmServiceObserver2 asInterface = ILlmServiceObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    hashMap = readInt4 >= 0 ? new HashMap() : null;
                    final int i6 = 3;
                    IntStream.range(0, readInt4).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i42) {
                            switch (i6) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map = hashMap;
                                    int i52 = ISmartReplyService.Stub.$r8$clinit;
                                    map.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map2 = hashMap;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map2.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map3 = hashMap;
                                    int i7 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map4 = hashMap;
                                    int i8 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map5 = hashMap;
                                    int i9 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map6 = hashMap;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map7 = hashMap;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    replyWithHeader3(hashMap2, readString, (LlmServiceObserver2) asInterface, hashMap);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    unLoadModel();
                    return true;
                case 6:
                    int readInt5 = parcel.readInt();
                    hashMap = readInt5 >= 0 ? new HashMap() : null;
                    final int i7 = 4;
                    IntStream.range(0, readInt5).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i42) {
                            switch (i7) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map = hashMap;
                                    int i52 = ISmartReplyService.Stub.$r8$clinit;
                                    map.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map2 = hashMap;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map2.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map3 = hashMap;
                                    int i72 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map4 = hashMap;
                                    int i8 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map5 = hashMap;
                                    int i9 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map6 = hashMap;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map7 = hashMap;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel9.readString(), parcel9.readString());
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
                    int readInt6 = parcel.readInt();
                    final HashMap hashMap3 = readInt6 < 0 ? null : new HashMap();
                    final int i8 = 5;
                    IntStream.range(0, readInt6).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i42) {
                            switch (i8) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map = hashMap3;
                                    int i52 = ISmartReplyService.Stub.$r8$clinit;
                                    map.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map2 = hashMap3;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map2.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map3 = hashMap3;
                                    int i72 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map4 = hashMap3;
                                    int i82 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map5 = hashMap3;
                                    int i9 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map6 = hashMap3;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map7 = hashMap3;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    ILlmServiceObserver2 asInterface2 = ILlmServiceObserver2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    final HashMap hashMap4 = readInt7 >= 0 ? new HashMap() : null;
                    final int i9 = 6;
                    IntStream.range(0, readInt7).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i42) {
                            switch (i9) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    Map map = hashMap4;
                                    int i52 = ISmartReplyService.Stub.$r8$clinit;
                                    map.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    Map map2 = hashMap4;
                                    int i62 = ISmartReplyService.Stub.$r8$clinit;
                                    map2.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    Map map3 = hashMap4;
                                    int i72 = ISmartReplyService.Stub.$r8$clinit;
                                    map3.put(parcel5.readString(), parcel5.readString());
                                    break;
                                case 3:
                                    Parcel parcel6 = parcel;
                                    Map map4 = hashMap4;
                                    int i82 = ISmartReplyService.Stub.$r8$clinit;
                                    map4.put(parcel6.readString(), parcel6.readString());
                                    break;
                                case 4:
                                    Parcel parcel7 = parcel;
                                    Map map5 = hashMap4;
                                    int i92 = ISmartReplyService.Stub.$r8$clinit;
                                    map5.put(parcel7.readString(), parcel7.readString());
                                    break;
                                case 5:
                                    Parcel parcel8 = parcel;
                                    Map map6 = hashMap4;
                                    int i10 = ISmartReplyService.Stub.$r8$clinit;
                                    map6.put(parcel8.readString(), parcel8.readString());
                                    break;
                                default:
                                    Parcel parcel9 = parcel;
                                    Map map7 = hashMap4;
                                    int i11 = ISmartReplyService.Stub.$r8$clinit;
                                    map7.put(parcel9.readString(), parcel9.readString());
                                    break;
                            }
                        }
                    });
                    replyWithCategory(hashMap3, readString2, readString3, (LlmServiceObserver2) asInterface2, hashMap4);
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
