package com.samsung.android.sivs.ai.sdkcommon.language;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ISmartReplyService extends IInterface {
    void reply();

    void replyWithHeader();

    void replyWithHeader2();

    void replyWithHeader3(Map map, String str, ILlmServiceObserver2 iLlmServiceObserver2, Map map2);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements ISmartReplyService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Proxy implements ISmartReplyService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService
            public final void replyWithHeader3(Map map, String str, ILlmServiceObserver2 iLlmServiceObserver2, Map map2) {
                final Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
                    final int i = 0;
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                switch (i) {
                                    case 0:
                                        Parcel parcel = obtain;
                                        parcel.writeString((String) obj);
                                        parcel.writeString((String) obj2);
                                        break;
                                    default:
                                        Parcel parcel2 = obtain;
                                        parcel2.writeString((String) obj);
                                        parcel2.writeString((String) obj2);
                                        break;
                                }
                            }
                        });
                    }
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iLlmServiceObserver2);
                    if (map2 == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map2.size());
                        final int i2 = 1;
                        map2.forEach(new BiConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                switch (i2) {
                                    case 0:
                                        Parcel parcel = obtain;
                                        parcel.writeString((String) obj);
                                        parcel.writeString((String) obj2);
                                        break;
                                    default:
                                        Parcel parcel2 = obtain;
                                        parcel2.writeString((String) obj);
                                        parcel2.writeString((String) obj2);
                                        break;
                                }
                            }
                        });
                    }
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
            ILlmServiceObserver2 proxy;
            final int i3 = 1;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService");
                return true;
            }
            if (i != 1) {
                final int i4 = 2;
                final int i5 = 0;
                if (i != 2) {
                    final int i6 = 3;
                    if (i == 3) {
                        int readInt = parcel.readInt();
                        hashMap = readInt >= 0 ? new HashMap() : null;
                        IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.IntConsumer
                            public final void accept(int i7) {
                                switch (i3) {
                                    case 0:
                                        Parcel parcel3 = parcel;
                                        hashMap.put(parcel3.readString(), parcel3.readString());
                                        break;
                                    case 1:
                                        Parcel parcel4 = parcel;
                                        hashMap.put(parcel4.readString(), parcel4.readString());
                                        break;
                                    case 2:
                                        Parcel parcel5 = parcel;
                                        hashMap.put(parcel5.readString(), parcel5.readString());
                                        break;
                                    default:
                                        Parcel parcel6 = parcel;
                                        hashMap.put(parcel6.readString(), parcel6.readString());
                                        break;
                                }
                            }
                        });
                        parcel.readString();
                        IBinder readStrongBinder = parcel.readStrongBinder();
                        if (readStrongBinder != null) {
                            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2");
                            if (queryLocalInterface == null || !(queryLocalInterface instanceof ILlmServiceObserver2)) {
                                new ILlmServiceObserver2.Stub.Proxy(readStrongBinder);
                            }
                        }
                        replyWithHeader2();
                        parcel2.writeNoException();
                    } else {
                        if (i != 4) {
                            return super.onTransact(i, parcel, parcel2, i2);
                        }
                        int readInt2 = parcel.readInt();
                        final HashMap hashMap2 = readInt2 < 0 ? null : new HashMap();
                        IntStream.range(0, readInt2).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.IntConsumer
                            public final void accept(int i7) {
                                switch (i4) {
                                    case 0:
                                        Parcel parcel3 = parcel;
                                        hashMap2.put(parcel3.readString(), parcel3.readString());
                                        break;
                                    case 1:
                                        Parcel parcel4 = parcel;
                                        hashMap2.put(parcel4.readString(), parcel4.readString());
                                        break;
                                    case 2:
                                        Parcel parcel5 = parcel;
                                        hashMap2.put(parcel5.readString(), parcel5.readString());
                                        break;
                                    default:
                                        Parcel parcel6 = parcel;
                                        hashMap2.put(parcel6.readString(), parcel6.readString());
                                        break;
                                }
                            }
                        });
                        String readString = parcel.readString();
                        IBinder readStrongBinder2 = parcel.readStrongBinder();
                        if (readStrongBinder2 == null) {
                            proxy = null;
                        } else {
                            IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver2");
                            proxy = (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof ILlmServiceObserver2)) ? new ILlmServiceObserver2.Stub.Proxy(readStrongBinder2) : (ILlmServiceObserver2) queryLocalInterface2;
                        }
                        int readInt3 = parcel.readInt();
                        hashMap = readInt3 >= 0 ? new HashMap() : null;
                        IntStream.range(0, readInt3).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.IntConsumer
                            public final void accept(int i7) {
                                switch (i6) {
                                    case 0:
                                        Parcel parcel3 = parcel;
                                        hashMap.put(parcel3.readString(), parcel3.readString());
                                        break;
                                    case 1:
                                        Parcel parcel4 = parcel;
                                        hashMap.put(parcel4.readString(), parcel4.readString());
                                        break;
                                    case 2:
                                        Parcel parcel5 = parcel;
                                        hashMap.put(parcel5.readString(), parcel5.readString());
                                        break;
                                    default:
                                        Parcel parcel6 = parcel;
                                        hashMap.put(parcel6.readString(), parcel6.readString());
                                        break;
                                }
                            }
                        });
                        replyWithHeader3(hashMap2, readString, proxy, hashMap);
                        parcel2.writeNoException();
                    }
                } else {
                    int readInt4 = parcel.readInt();
                    hashMap = readInt4 >= 0 ? new HashMap() : null;
                    IntStream.range(0, readInt4).forEach(new IntConsumer() { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ISmartReplyService$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i7) {
                            switch (i5) {
                                case 0:
                                    Parcel parcel3 = parcel;
                                    hashMap.put(parcel3.readString(), parcel3.readString());
                                    break;
                                case 1:
                                    Parcel parcel4 = parcel;
                                    hashMap.put(parcel4.readString(), parcel4.readString());
                                    break;
                                case 2:
                                    Parcel parcel5 = parcel;
                                    hashMap.put(parcel5.readString(), parcel5.readString());
                                    break;
                                default:
                                    Parcel parcel6 = parcel;
                                    hashMap.put(parcel6.readString(), parcel6.readString());
                                    break;
                            }
                        }
                    });
                    parcel.readString();
                    final IBinder readStrongBinder3 = parcel.readStrongBinder();
                    if (readStrongBinder3 != null) {
                        IInterface queryLocalInterface3 = readStrongBinder3.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver");
                        if (queryLocalInterface3 == null || !(queryLocalInterface3 instanceof ILlmServiceObserver)) {
                            new ILlmServiceObserver(readStrongBinder3) { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver$Stub$Proxy
                                public final IBinder mRemote;

                                {
                                    this.mRemote = readStrongBinder3;
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
                }
            } else {
                parcel.readString();
                final IBinder readStrongBinder4 = parcel.readStrongBinder();
                if (readStrongBinder4 != null) {
                    IInterface queryLocalInterface4 = readStrongBinder4.queryLocalInterface("com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver");
                    if (queryLocalInterface4 == null || !(queryLocalInterface4 instanceof ILlmServiceObserver)) {
                        new ILlmServiceObserver(readStrongBinder4) { // from class: com.samsung.android.sivs.ai.sdkcommon.language.ILlmServiceObserver$Stub$Proxy
                            public final IBinder mRemote;

                            {
                                this.mRemote = readStrongBinder4;
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
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
