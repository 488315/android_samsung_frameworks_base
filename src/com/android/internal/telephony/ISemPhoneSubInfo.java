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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemPhoneSubInfo.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemPhoneSubInfo)) {
                return (ISemPhoneSubInfo) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasCall = hasCall(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasCall);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zChangeDRX = changeDRX(i3, i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeDRX);
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] supportedCycles = getSupportedCycles(i6);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedCycles);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int defaultCycle = getDefaultCycle(i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultCycle);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int currentCycle = getCurrentCycle(i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentCycle);
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean drxMode = setDrxMode(i9);
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
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int dataServiceStateUsingSubId = getDataServiceStateUsingSubId(i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataServiceStateUsingSubId);
                    return true;
                case 10:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean uwbTimers = setUwbTimers(iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(uwbTimers);
                    return true;
                case 11:
                    int[] uwbTimers2 = getUwbTimers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(uwbTimers2);
                    return true;
                case 12:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zChangeDRXForKodiak = changeDRXForKodiak(i11, i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeDRXForKodiak);
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
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] homePlmns = getHomePlmns(i13);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(homePlmns);
                    return true;
                case 17:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String subscriberIdForUiccAppType = getSubscriberIdForUiccAppType(i14, i15, string2);
                    parcel2.writeNoException();
                    parcel2.writeString(subscriberIdForUiccAppType);
                    return true;
                case 18:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] psismsc = getPsismsc(string3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(psismsc);
                    return true;
                case 19:
                    int i16 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] psismscWithPhoneId = getPsismscWithPhoneId(i16, string4);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(psismscWithPhoneId);
                    return true;
                case 20:
                    boolean zIsGbaSupported = isGbaSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGbaSupported);
                    return true;
                case 21:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsGbaSupportedForSubscriber = isGbaSupportedForSubscriber(i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGbaSupportedForSubscriber);
                    return true;
                case 22:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] rand = getRand(i18);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(rand);
                    return true;
                case 23:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String btid = getBtid(i19);
                    parcel2.writeNoException();
                    parcel2.writeString(btid);
                    return true;
                case 24:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String keyLifetime = getKeyLifetime(i20);
                    parcel2.writeNoException();
                    parcel2.writeString(keyLifetime);
                    return true;
                case 25:
                    int i21 = parcel.readInt();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String groupIdLevel2ForSubscriber = getGroupIdLevel2ForSubscriber(i21, string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeString(groupIdLevel2ForSubscriber);
                    return true;
                case 26:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean defaultSmsApplicationByForce = setDefaultSmsApplicationByForce(string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultSmsApplicationByForce);
                    return true;
                case 27:
                    int i22 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearMwiNotificationAndVoicemailCount(i22, string8);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPcoValue(i23, i24, string9);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean changeDRX(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int[] getSupportedCycles(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDefaultCycle(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getCurrentCycle(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean setDrxMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDrxMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDataServiceState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDataServiceStateUsingSubId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean setUwbTimers(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int[] getUwbTimers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean changeDRXForKodiak(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int[] getSupportedModesForKodiak() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getDefaultCycleForKodiak() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public int getCurrentModeForKodiak() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String[] getHomePlmns(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getSubscriberIdForUiccAppType(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public byte[] getPsismsc(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public byte[] getPsismscWithPhoneId(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean isGbaSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean isGbaSupportedForSubscriber(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public byte[] getRand(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getBtid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getKeyLifetime(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public String getGroupIdLevel2ForSubscriber(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public boolean setDefaultSmsApplicationByForce(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public void clearMwiNotificationAndVoicemailCount(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemPhoneSubInfo
            public void setPcoValue(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemPhoneSubInfo.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
