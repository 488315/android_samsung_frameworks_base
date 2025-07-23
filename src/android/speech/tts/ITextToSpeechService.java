package android.speech.tts;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.speech.tts.ITextToSpeechCallback;
import android.text.TextUtils;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITextToSpeechService extends IInterface {

    public static class Default implements ITextToSpeechService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public String[] getClientDefaultLanguage() throws RemoteException {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public String getDefaultVoiceNameFor(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public String[] getFeaturesForLanguage(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public String[] getLanguage() throws RemoteException {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public List<Voice> getVoices() throws RemoteException {
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int isLanguageAvailable(String str, String str2, String str3) throws RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public boolean isSpeaking() throws RemoteException {
            return false;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int loadLanguage(IBinder iBinder, String str, String str2, String str3) throws RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int loadVoice(IBinder iBinder, String str) throws RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int playAudio(IBinder iBinder, Uri uri, int i, Bundle bundle, String str) throws RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int playSilence(IBinder iBinder, long j, int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public void setCallback(IBinder iBinder, ITextToSpeechCallback iTextToSpeechCallback) throws RemoteException {
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int speak(IBinder iBinder, CharSequence charSequence, int i, Bundle bundle, String str) throws RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int stop(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int synthesizeToFileDescriptor(IBinder iBinder, CharSequence charSequence, ParcelFileDescriptor parcelFileDescriptor, Bundle bundle, String str) throws RemoteException {
            return 0;
        }
    }

    String[] getClientDefaultLanguage() throws RemoteException;

    String getDefaultVoiceNameFor(String str, String str2, String str3) throws RemoteException;

    String[] getFeaturesForLanguage(String str, String str2, String str3) throws RemoteException;

    String[] getLanguage() throws RemoteException;

    List<Voice> getVoices() throws RemoteException;

    int isLanguageAvailable(String str, String str2, String str3) throws RemoteException;

    boolean isSpeaking() throws RemoteException;

    int loadLanguage(IBinder iBinder, String str, String str2, String str3) throws RemoteException;

    int loadVoice(IBinder iBinder, String str) throws RemoteException;

    int playAudio(IBinder iBinder, Uri uri, int i, Bundle bundle, String str) throws RemoteException;

    int playSilence(IBinder iBinder, long j, int i, String str) throws RemoteException;

    void setCallback(IBinder iBinder, ITextToSpeechCallback iTextToSpeechCallback) throws RemoteException;

    int speak(IBinder iBinder, CharSequence charSequence, int i, Bundle bundle, String str) throws RemoteException;

    int stop(IBinder iBinder) throws RemoteException;

    int synthesizeToFileDescriptor(IBinder iBinder, CharSequence charSequence, ParcelFileDescriptor parcelFileDescriptor, Bundle bundle, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ITextToSpeechService {
        public static final String DESCRIPTOR = "android.speech.tts.ITextToSpeechService";
        static final int TRANSACTION_getClientDefaultLanguage = 8;
        static final int TRANSACTION_getDefaultVoiceNameFor = 15;
        static final int TRANSACTION_getFeaturesForLanguage = 10;
        static final int TRANSACTION_getLanguage = 7;
        static final int TRANSACTION_getVoices = 13;
        static final int TRANSACTION_isLanguageAvailable = 9;
        static final int TRANSACTION_isSpeaking = 5;
        static final int TRANSACTION_loadLanguage = 11;
        static final int TRANSACTION_loadVoice = 14;
        static final int TRANSACTION_playAudio = 3;
        static final int TRANSACTION_playSilence = 4;
        static final int TRANSACTION_setCallback = 12;
        static final int TRANSACTION_speak = 1;
        static final int TRANSACTION_stop = 6;
        static final int TRANSACTION_synthesizeToFileDescriptor = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITextToSpeechService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITextToSpeechService)) {
                return (ITextToSpeechService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "speak";
                case 2:
                    return "synthesizeToFileDescriptor";
                case 3:
                    return "playAudio";
                case 4:
                    return "playSilence";
                case 5:
                    return "isSpeaking";
                case 6:
                    return "stop";
                case 7:
                    return "getLanguage";
                case 8:
                    return "getClientDefaultLanguage";
                case 9:
                    return "isLanguageAvailable";
                case 10:
                    return "getFeaturesForLanguage";
                case 11:
                    return "loadLanguage";
                case 12:
                    return "setCallback";
                case 13:
                    return "getVoices";
                case 14:
                    return "loadVoice";
                case 15:
                    return "getDefaultVoiceNameFor";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int speak = speak(readStrongBinder, charSequence, readInt, bundle, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(speak);
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int synthesizeToFileDescriptor = synthesizeToFileDescriptor(readStrongBinder2, charSequence2, parcelFileDescriptor, bundle2, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(synthesizeToFileDescriptor);
                    return true;
                case 3:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt2 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int playAudio = playAudio(readStrongBinder3, uri, readInt2, bundle3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(playAudio);
                    return true;
                case 4:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    int readInt3 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int playSilence = playSilence(readStrongBinder4, readLong, readInt3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(playSilence);
                    return true;
                case 5:
                    boolean isSpeaking = isSpeaking();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpeaking);
                    return true;
                case 6:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int stop = stop(readStrongBinder5);
                    parcel2.writeNoException();
                    parcel2.writeInt(stop);
                    return true;
                case 7:
                    String[] language = getLanguage();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(language);
                    return true;
                case 8:
                    String[] clientDefaultLanguage = getClientDefaultLanguage();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(clientDefaultLanguage);
                    return true;
                case 9:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isLanguageAvailable = isLanguageAvailable(readString5, readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeInt(isLanguageAvailable);
                    return true;
                case 10:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] featuresForLanguage = getFeaturesForLanguage(readString8, readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(featuresForLanguage);
                    return true;
                case 11:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int loadLanguage = loadLanguage(readStrongBinder6, readString11, readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadLanguage);
                    return true;
                case 12:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    ITextToSpeechCallback asInterface = ITextToSpeechCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(readStrongBinder7, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    List<Voice> voices = getVoices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(voices, 1);
                    return true;
                case 14:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int loadVoice = loadVoice(readStrongBinder8, readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(loadVoice);
                    return true;
                case 15:
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String defaultVoiceNameFor = getDefaultVoiceNameFor(readString15, readString16, readString17);
                    parcel2.writeNoException();
                    parcel2.writeString(defaultVoiceNameFor);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITextToSpeechService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int speak(IBinder iBinder, CharSequence charSequence, int i, Bundle bundle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int synthesizeToFileDescriptor(IBinder iBinder, CharSequence charSequence, ParcelFileDescriptor parcelFileDescriptor, Bundle bundle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int playAudio(IBinder iBinder, Uri uri, int i, Bundle bundle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int playSilence(IBinder iBinder, long j, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public boolean isSpeaking() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int stop(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public String[] getLanguage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public String[] getClientDefaultLanguage() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int isLanguageAvailable(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public String[] getFeaturesForLanguage(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int loadLanguage(IBinder iBinder, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public void setCallback(IBinder iBinder, ITextToSpeechCallback iTextToSpeechCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iTextToSpeechCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public List<Voice> getVoices() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Voice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public int loadVoice(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.speech.tts.ITextToSpeechService
            public String getDefaultVoiceNameFor(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
