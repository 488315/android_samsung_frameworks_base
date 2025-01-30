package com.samsung.android.knox.datetime;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IDateTimePolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.datetime.IDateTimePolicy";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IDateTimePolicy {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean getAutomaticTime(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final String getDateFormat(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final long getDateTime(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean getDaylightSavingTime(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final NtpInfo getNtpInfo(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final String getNtpServer() {
            return null;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final long getNtpTimeout() {
            return 0L;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final String getTimeFormat(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final String getTimeZone(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean isDateTimeChangeEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean setAutomaticTime(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean setDateFormat(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean setDateTime(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean setDateTimeChangeEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean setNtpInfo(ContextInfo contextInfo, NtpInfo ntpInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean setTimeFormat(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.datetime.IDateTimePolicy
        public final boolean setTimeZone(ContextInfo contextInfo, String str) {
            return false;
        }
    }

    boolean getAutomaticTime(ContextInfo contextInfo);

    String getDateFormat(ContextInfo contextInfo);

    long getDateTime(ContextInfo contextInfo);

    boolean getDaylightSavingTime(ContextInfo contextInfo);

    NtpInfo getNtpInfo(ContextInfo contextInfo);

    String getNtpServer();

    long getNtpTimeout();

    String getTimeFormat(ContextInfo contextInfo);

    String getTimeZone(ContextInfo contextInfo);

    boolean isDateTimeChangeEnabled(ContextInfo contextInfo);

    boolean setAutomaticTime(ContextInfo contextInfo, boolean z);

    boolean setDateFormat(ContextInfo contextInfo, String str);

    boolean setDateTime(ContextInfo contextInfo, long j);

    boolean setDateTimeChangeEnabled(ContextInfo contextInfo, boolean z);

    boolean setNtpInfo(ContextInfo contextInfo, NtpInfo ntpInfo);

    boolean setTimeFormat(ContextInfo contextInfo, String str);

    boolean setTimeZone(ContextInfo contextInfo, String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IDateTimePolicy {
        public static final int TRANSACTION_getAutomaticTime = 10;
        public static final int TRANSACTION_getDateFormat = 8;
        public static final int TRANSACTION_getDateTime = 2;
        public static final int TRANSACTION_getDaylightSavingTime = 11;
        public static final int TRANSACTION_getNtpInfo = 15;
        public static final int TRANSACTION_getNtpServer = 16;
        public static final int TRANSACTION_getNtpTimeout = 17;
        public static final int TRANSACTION_getTimeFormat = 6;
        public static final int TRANSACTION_getTimeZone = 4;
        public static final int TRANSACTION_isDateTimeChangeEnabled = 13;
        public static final int TRANSACTION_setAutomaticTime = 9;
        public static final int TRANSACTION_setDateFormat = 7;
        public static final int TRANSACTION_setDateTime = 1;
        public static final int TRANSACTION_setDateTimeChangeEnabled = 12;
        public static final int TRANSACTION_setNtpInfo = 14;
        public static final int TRANSACTION_setTimeFormat = 5;
        public static final int TRANSACTION_setTimeZone = 3;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IDateTimePolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean getAutomaticTime(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final String getDateFormat(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final long getDateTime(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean getDaylightSavingTime(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IDateTimePolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final NtpInfo getNtpInfo(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NtpInfo) obtain2.readTypedObject(NtpInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final String getNtpServer() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final long getNtpTimeout() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final String getTimeFormat(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final String getTimeZone(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean isDateTimeChangeEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean setAutomaticTime(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean setDateFormat(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean setDateTime(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean setDateTimeChangeEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean setNtpInfo(ContextInfo contextInfo, NtpInfo ntpInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(ntpInfo, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean setTimeFormat(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.datetime.IDateTimePolicy
            public final boolean setTimeZone(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDateTimePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDateTimePolicy.DESCRIPTOR);
        }

        public static IDateTimePolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDateTimePolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDateTimePolicy)) ? new Proxy(iBinder) : (IDateTimePolicy) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setDateTime";
                case 2:
                    return "getDateTime";
                case 3:
                    return "setTimeZone";
                case 4:
                    return "getTimeZone";
                case 5:
                    return "setTimeFormat";
                case 6:
                    return "getTimeFormat";
                case 7:
                    return "setDateFormat";
                case 8:
                    return "getDateFormat";
                case 9:
                    return "setAutomaticTime";
                case 10:
                    return "getAutomaticTime";
                case 11:
                    return "getDaylightSavingTime";
                case 12:
                    return "setDateTimeChangeEnabled";
                case 13:
                    return "isDateTimeChangeEnabled";
                case 14:
                    return "setNtpInfo";
                case 15:
                    return "getNtpInfo";
                case 16:
                    return "getNtpServer";
                case 17:
                    return "getNtpTimeout";
                default:
                    return null;
            }
        }

        public final int getMaxTransactionId() {
            return 16;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDateTimePolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDateTimePolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean dateTime = setDateTime(contextInfo, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dateTime);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long dateTime2 = getDateTime(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeLong(dateTime2);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean timeZone = setTimeZone(contextInfo3, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(timeZone);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String timeZone2 = getTimeZone(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeString(timeZone2);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean timeFormat = setTimeFormat(contextInfo5, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(timeFormat);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String timeFormat2 = getTimeFormat(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeString(timeFormat2);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dateFormat = setDateFormat(contextInfo7, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dateFormat);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String dateFormat2 = getDateFormat(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeString(dateFormat2);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean automaticTime = setAutomaticTime(contextInfo9, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(automaticTime);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean automaticTime2 = getAutomaticTime(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(automaticTime2);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean daylightSavingTime = getDaylightSavingTime(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(daylightSavingTime);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dateTimeChangeEnabled = setDateTimeChangeEnabled(contextInfo12, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dateTimeChangeEnabled);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDateTimeChangeEnabled = isDateTimeChangeEnabled(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDateTimeChangeEnabled);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    NtpInfo ntpInfo = (NtpInfo) parcel.readTypedObject(NtpInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean ntpInfo2 = setNtpInfo(contextInfo14, ntpInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ntpInfo2);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    NtpInfo ntpInfo3 = getNtpInfo(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ntpInfo3, 1);
                    return true;
                case 16:
                    String ntpServer = getNtpServer();
                    parcel2.writeNoException();
                    parcel2.writeString(ntpServer);
                    return true;
                case 17:
                    long ntpTimeout = getNtpTimeout();
                    parcel2.writeNoException();
                    parcel2.writeLong(ntpTimeout);
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
