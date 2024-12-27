package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemPhoneSubInfo extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ISemPhoneSubInfo";

    boolean changeDRX(int i, int i2, int i3) throws RemoteException;

    boolean changeDRXForKodiak(int i, int i2) throws RemoteException;

    void clearMwiNotificationAndVoicemailCount(int i, String str) throws RemoteException;

    String getBtid(int i) throws RemoteException;

    int getCurrentCycle(int i) throws RemoteException;

    int getCurrentModeForKodiak() throws RemoteException;

    int getDataServiceState() throws RemoteException;

    int getDataServiceStateUsingSubId(int i) throws RemoteException;

    int getDefaultCycle(int i) throws RemoteException;

    int getDefaultCycleForKodiak() throws RemoteException;

    int getDrxMode() throws RemoteException;

    String getGroupIdLevel2ForSubscriber(int i, String str, String str2) throws RemoteException;

    String[] getHomePlmns(int i) throws RemoteException;

    String getKeyLifetime(int i) throws RemoteException;

    byte[] getPsismsc(String str) throws RemoteException;

    byte[] getPsismscWithPhoneId(int i, String str) throws RemoteException;

    byte[] getRand(int i) throws RemoteException;

    String getSubscriberIdForUiccAppType(int i, int i2, String str) throws RemoteException;

    int[] getSupportedCycles(int i) throws RemoteException;

    int[] getSupportedModesForKodiak() throws RemoteException;

    int[] getUwbTimers() throws RemoteException;

    boolean hasCall(String str) throws RemoteException;

    boolean isGbaSupported() throws RemoteException;

    boolean isGbaSupportedForSubscriber(int i) throws RemoteException;

    boolean setDefaultSmsApplicationByForce(String str) throws RemoteException;

    boolean setDrxMode(int i) throws RemoteException;

    boolean setUwbTimers(int[] iArr) throws RemoteException;

    public static class Default implements ISemPhoneSubInfo {
        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean hasCall(String callType) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean changeDRX(int drxLibraryType, int cycle, int duration)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int[] getSupportedCycles(int drxLibraryType) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDefaultCycle(int drxLibraryType) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getCurrentCycle(int drxLibraryType) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean setDrxMode(int drxMode) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDrxMode() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDataServiceState() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDataServiceStateUsingSubId(int subId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean setUwbTimers(int[] timer) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int[] getUwbTimers() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean changeDRXForKodiak(int drxValue, int duration) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int[] getSupportedModesForKodiak() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDefaultCycleForKodiak() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getCurrentModeForKodiak() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String[] getHomePlmns(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String getSubscriberIdForUiccAppType(int subId, int appType, String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public byte[] getPsismsc(String callingPackage) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public byte[] getPsismscWithPhoneId(int phoneId, String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean isGbaSupported() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean isGbaSupportedForSubscriber(int subid) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public byte[] getRand(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String getBtid(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String getKeyLifetime(int subId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String getGroupIdLevel2ForSubscriber(
                int subId, String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean setDefaultSmsApplicationByForce(String packageName) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public void clearMwiNotificationAndVoicemailCount(int phoneId, String callingPackage)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemPhoneSubInfo {
        static final int TRANSACTION_changeDRX = 2;
        static final int TRANSACTION_changeDRXForKodiak = 12;
        static final int TRANSACTION_clearMwiNotificationAndVoicemailCount = 27;
        static final int TRANSACTION_getBtid = 23;
        static final int TRANSACTION_getCurrentCycle = 5;
        static final int TRANSACTION_getCurrentModeForKodiak = 15;
        static final int TRANSACTION_getDataServiceState = 8;
        static final int TRANSACTION_getDataServiceStateUsingSubId = 9;
        static final int TRANSACTION_getDefaultCycle = 4;
        static final int TRANSACTION_getDefaultCycleForKodiak = 14;
        static final int TRANSACTION_getDrxMode = 7;
        static final int TRANSACTION_getGroupIdLevel2ForSubscriber = 25;
        static final int TRANSACTION_getHomePlmns = 16;
        static final int TRANSACTION_getKeyLifetime = 24;
        static final int TRANSACTION_getPsismsc = 18;
        static final int TRANSACTION_getPsismscWithPhoneId = 19;
        static final int TRANSACTION_getRand = 22;
        static final int TRANSACTION_getSubscriberIdForUiccAppType = 17;
        static final int TRANSACTION_getSupportedCycles = 3;
        static final int TRANSACTION_getSupportedModesForKodiak = 13;
        static final int TRANSACTION_getUwbTimers = 11;
        static final int TRANSACTION_hasCall = 1;
        static final int TRANSACTION_isGbaSupported = 20;
        static final int TRANSACTION_isGbaSupportedForSubscriber = 21;
        static final int TRANSACTION_setDefaultSmsApplicationByForce = 26;
        static final int TRANSACTION_setDrxMode = 6;
        static final int TRANSACTION_setUwbTimers = 10;

        public Stub() {
            attachInterface(this, ISemPhoneSubInfo.DESCRIPTOR);
        }

        public static ISemPhoneSubInfo asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemPhoneSubInfo.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemPhoneSubInfo)) {
                return (ISemPhoneSubInfo) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "hasCall";
                case 2:
                    return "changeDRX";
                case 3:
                    return "getSupportedCycles";
                case 4:
                    return "getDefaultCycle";
                case 5:
                    return "getCurrentCycle";
                case 6:
                    return "setDrxMode";
                case 7:
                    return "getDrxMode";
                case 8:
                    return "getDataServiceState";
                case 9:
                    return "getDataServiceStateUsingSubId";
                case 10:
                    return "setUwbTimers";
                case 11:
                    return "getUwbTimers";
                case 12:
                    return "changeDRXForKodiak";
                case 13:
                    return "getSupportedModesForKodiak";
                case 14:
                    return "getDefaultCycleForKodiak";
                case 15:
                    return "getCurrentModeForKodiak";
                case 16:
                    return "getHomePlmns";
                case 17:
                    return "getSubscriberIdForUiccAppType";
                case 18:
                    return "getPsismsc";
                case 19:
                    return "getPsismscWithPhoneId";
                case 20:
                    return "isGbaSupported";
                case 21:
                    return "isGbaSupportedForSubscriber";
                case 22:
                    return "getRand";
                case 23:
                    return "getBtid";
                case 24:
                    return "getKeyLifetime";
                case 25:
                    return "getGroupIdLevel2ForSubscriber";
                case 26:
                    return "setDefaultSmsApplicationByForce";
                case 27:
                    return "clearMwiNotificationAndVoicemailCount";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemPhoneSubInfo.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemPhoneSubInfo.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result = hasCall(_arg0);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = changeDRX(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result3 = getSupportedCycles(_arg03);
                    reply.writeNoException();
                    reply.writeIntArray(_result3);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = getDefaultCycle(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result5 = getCurrentCycle(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 = setDrxMode(_arg06);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    int _result7 = getDrxMode();
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 8:
                    int _result8 = getDataServiceState();
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 9:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result9 = getDataServiceStateUsingSubId(_arg07);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 10:
                    int[] _arg08 = data.createIntArray();
                    data.enforceNoDataAvail();
                    boolean _result10 = setUwbTimers(_arg08);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 11:
                    int[] _result11 = getUwbTimers();
                    reply.writeNoException();
                    reply.writeIntArray(_result11);
                    return true;
                case 12:
                    int _arg09 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result12 = changeDRXForKodiak(_arg09, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 13:
                    int[] _result13 = getSupportedModesForKodiak();
                    reply.writeNoException();
                    reply.writeIntArray(_result13);
                    return true;
                case 14:
                    int _result14 = getDefaultCycleForKodiak();
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 15:
                    int _result15 = getCurrentModeForKodiak();
                    reply.writeNoException();
                    reply.writeInt(_result15);
                    return true;
                case 16:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result16 = getHomePlmns(_arg010);
                    reply.writeNoException();
                    reply.writeStringArray(_result16);
                    return true;
                case 17:
                    int _arg011 = data.readInt();
                    int _arg13 = data.readInt();
                    String _arg22 = data.readString();
                    data.enforceNoDataAvail();
                    String _result17 = getSubscriberIdForUiccAppType(_arg011, _arg13, _arg22);
                    reply.writeNoException();
                    reply.writeString(_result17);
                    return true;
                case 18:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    byte[] _result18 = getPsismsc(_arg012);
                    reply.writeNoException();
                    reply.writeByteArray(_result18);
                    return true;
                case 19:
                    int _arg013 = data.readInt();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    byte[] _result19 = getPsismscWithPhoneId(_arg013, _arg14);
                    reply.writeNoException();
                    reply.writeByteArray(_result19);
                    return true;
                case 20:
                    boolean _result20 = isGbaSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 21:
                    int _arg014 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result21 = isGbaSupportedForSubscriber(_arg014);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 22:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    byte[] _result22 = getRand(_arg015);
                    reply.writeNoException();
                    reply.writeByteArray(_result22);
                    return true;
                case 23:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result23 = getBtid(_arg016);
                    reply.writeNoException();
                    reply.writeString(_result23);
                    return true;
                case 24:
                    int _arg017 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result24 = getKeyLifetime(_arg017);
                    reply.writeNoException();
                    reply.writeString(_result24);
                    return true;
                case 25:
                    int _arg018 = data.readInt();
                    String _arg15 = data.readString();
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    String _result25 = getGroupIdLevel2ForSubscriber(_arg018, _arg15, _arg23);
                    reply.writeNoException();
                    reply.writeString(_result25);
                    return true;
                case 26:
                    String _arg019 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result26 = setDefaultSmsApplicationByForce(_arg019);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 27:
                    int _arg020 = data.readInt();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    clearMwiNotificationAndVoicemailCount(_arg020, _arg16);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemPhoneSubInfo {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemPhoneSubInfo.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean hasCall(String callType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeString(callType);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean changeDRX(int drxLibraryType, int cycle, int duration)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(drxLibraryType);
                    _data.writeInt(cycle);
                    _data.writeInt(duration);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int[] getSupportedCycles(int drxLibraryType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(drxLibraryType);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDefaultCycle(int drxLibraryType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(drxLibraryType);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getCurrentCycle(int drxLibraryType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(drxLibraryType);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean setDrxMode(int drxMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(drxMode);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDrxMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDataServiceState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDataServiceStateUsingSubId(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean setUwbTimers(int[] timer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeIntArray(timer);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int[] getUwbTimers() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean changeDRXForKodiak(int drxValue, int duration) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(drxValue);
                    _data.writeInt(duration);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int[] getSupportedModesForKodiak() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDefaultCycleForKodiak() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getCurrentModeForKodiak() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String[] getHomePlmns(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getSubscriberIdForUiccAppType(
                    int subId, int appType, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(appType);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public byte[] getPsismsc(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public byte[] getPsismscWithPhoneId(int phoneId, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean isGbaSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean isGbaSupportedForSubscriber(int subid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(subid);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public byte[] getRand(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getBtid(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getKeyLifetime(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getGroupIdLevel2ForSubscriber(
                    int subId, String callingPackage, String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean setDefaultSmsApplicationByForce(String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public void clearMwiNotificationAndVoicemailCount(int phoneId, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 26;
        }
    }
}
