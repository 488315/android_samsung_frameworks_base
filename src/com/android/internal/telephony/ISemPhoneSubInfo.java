package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISemPhoneSubInfo extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ISemPhoneSubInfo";

    public static class Default implements ISemPhoneSubInfo {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean changeDRX(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean changeDRXForKodiak(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public void clearMwiNotificationAndVoicemailCount(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String getBtid(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getCurrentCycle(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getCurrentModeForKodiak() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDataServiceState() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDataServiceStateUsingSubId(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDefaultCycle(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDefaultCycleForKodiak() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int getDrxMode() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String getGroupIdLevel2ForSubscriber(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String[] getHomePlmns(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String getKeyLifetime(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public byte[] getPsismsc(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public byte[] getPsismscWithPhoneId(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public byte[] getRand(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public String getSubscriberIdForUiccAppType(int i, int i2, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int[] getSupportedCycles(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int[] getSupportedModesForKodiak() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public int[] getUwbTimers() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean hasCall(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean isGbaSupported() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean isGbaSupportedForSubscriber(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean setDefaultSmsApplicationByForce(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean setDrxMode(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public void setPcoValue(int i, int i2, String str) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemPhoneSubInfo
        public boolean setUwbTimers(int[] iArr) throws RemoteException {
            return false;
        }
    }

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

    void setPcoValue(int i, int i2, String str) throws RemoteException;

    boolean setUwbTimers(int[] iArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemPhoneSubInfo {
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
        static final int TRANSACTION_setPcoValue = 28;
        static final int TRANSACTION_setUwbTimers = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 27;
        }

        public Stub() {
            attachInterface(this, ISemPhoneSubInfo.DESCRIPTOR);
        }

        public static ISemPhoneSubInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemPhoneSubInfo.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemPhoneSubInfo)) {
                return (ISemPhoneSubInfo) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                case 28:
                    return "setPcoValue";
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
                parcel.enforceInterface(ISemPhoneSubInfo.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemPhoneSubInfo.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasCall = hasCall(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCall);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean changeDRX = changeDRX(readInt, readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeDRX);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] supportedCycles = getSupportedCycles(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedCycles);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultCycle = getDefaultCycle(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultCycle);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentCycle = getCurrentCycle(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentCycle);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean drxMode = setDrxMode(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(drxMode);
                    return true;
                case 7:
                    int drxMode2 = getDrxMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(drxMode2);
                    return true;
                case 8:
                    int dataServiceState = getDataServiceState();
                    parcel2.writeNoException();
                    parcel2.writeInt(dataServiceState);
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dataServiceStateUsingSubId = getDataServiceStateUsingSubId(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataServiceStateUsingSubId);
                    return true;
                case 10:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean uwbTimers = setUwbTimers(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uwbTimers);
                    return true;
                case 11:
                    int[] uwbTimers2 = getUwbTimers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(uwbTimers2);
                    return true;
                case 12:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean changeDRXForKodiak = changeDRXForKodiak(readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeDRXForKodiak);
                    return true;
                case 13:
                    int[] supportedModesForKodiak = getSupportedModesForKodiak();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedModesForKodiak);
                    return true;
                case 14:
                    int defaultCycleForKodiak = getDefaultCycleForKodiak();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultCycleForKodiak);
                    return true;
                case 15:
                    int currentModeForKodiak = getCurrentModeForKodiak();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentModeForKodiak);
                    return true;
                case 16:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] homePlmns = getHomePlmns(readInt11);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(homePlmns);
                    return true;
                case 17:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriberIdForUiccAppType = getSubscriberIdForUiccAppType(readInt12, readInt13, readString2);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberIdForUiccAppType);
                    return true;
                case 18:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] psismsc = getPsismsc(readString3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(psismsc);
                    return true;
                case 19:
                    int readInt14 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] psismscWithPhoneId = getPsismscWithPhoneId(readInt14, readString4);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(psismscWithPhoneId);
                    return true;
                case 20:
                    boolean isGbaSupported = isGbaSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGbaSupported);
                    return true;
                case 21:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isGbaSupportedForSubscriber = isGbaSupportedForSubscriber(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGbaSupportedForSubscriber);
                    return true;
                case 22:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] rand = getRand(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(rand);
                    return true;
                case 23:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String btid = getBtid(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeString(btid);
                    return true;
                case 24:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String keyLifetime = getKeyLifetime(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeString(keyLifetime);
                    return true;
                case 25:
                    int readInt19 = parcel.readInt();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String groupIdLevel2ForSubscriber = getGroupIdLevel2ForSubscriber(readInt19, readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeString(groupIdLevel2ForSubscriber);
                    return true;
                case 26:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean defaultSmsApplicationByForce = setDefaultSmsApplicationByForce(readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultSmsApplicationByForce);
                    return true;
                case 27:
                    int readInt20 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearMwiNotificationAndVoicemailCount(readInt20, readString8);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPcoValue(readInt21, readInt22, readString9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemPhoneSubInfo {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemPhoneSubInfo.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean hasCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean changeDRX(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int[] getSupportedCycles(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDefaultCycle(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getCurrentCycle(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean setDrxMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDrxMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDataServiceState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDataServiceStateUsingSubId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean setUwbTimers(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int[] getUwbTimers() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean changeDRXForKodiak(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int[] getSupportedModesForKodiak() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDefaultCycleForKodiak() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getCurrentModeForKodiak() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String[] getHomePlmns(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getSubscriberIdForUiccAppType(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public byte[] getPsismsc(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public byte[] getPsismscWithPhoneId(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean isGbaSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean isGbaSupportedForSubscriber(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public byte[] getRand(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getBtid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getKeyLifetime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getGroupIdLevel2ForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean setDefaultSmsApplicationByForce(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public void clearMwiNotificationAndVoicemailCount(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public void setPcoValue(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
