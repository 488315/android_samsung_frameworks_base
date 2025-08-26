package com.samsung.android.knox.net.billing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IEnterpriseBillingPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy";

    boolean activateProfile(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean addProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) throws RemoteException;

    boolean addProfileForCurrentContainer(EnterpriseBillingProfile enterpriseBillingProfile) throws RemoteException;

    boolean addVpnToBillingProfile(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException;

    boolean addVpnToBillingProfileForCurrentContainer(String str, String str2, String str3) throws RemoteException;

    boolean allowRoaming(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    void allowWifiFallback(ContextInfo contextInfo, String str, boolean z) throws RemoteException;

    boolean disableProfile(ContextInfo contextInfo, String str) throws RemoteException;

    boolean disableProfileForApps(ContextInfo contextInfo, List<String> list) throws RemoteException;

    boolean disableProfileForContainer(ContextInfo contextInfo) throws RemoteException;

    boolean disableProfileForCurrentContainer() throws RemoteException;

    boolean enableProfileForApps(ContextInfo contextInfo, String str, List<String> list) throws RemoteException;

    boolean enableProfileForContainer(ContextInfo contextInfo, String str) throws RemoteException;

    boolean enableProfileForCurrentContainer(String str) throws RemoteException;

    List getApplicationsUsingProfile(ContextInfo contextInfo, String str) throws RemoteException;

    List getAvailableProfiles(ContextInfo contextInfo) throws RemoteException;

    List getAvailableProfilesForCaller() throws RemoteException;

    List getContainersUsingProfile(ContextInfo contextInfo, String str) throws RemoteException;

    EnterpriseBillingProfile getProfileDetails(ContextInfo contextInfo, String str) throws RemoteException;

    EnterpriseBillingProfile getProfileForApplication(ContextInfo contextInfo, String str) throws RemoteException;

    EnterpriseBillingProfile getProfileForContainer(ContextInfo contextInfo) throws RemoteException;

    List<String> getVpnsBoundToProfile(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isProfileActive(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isProfileActiveByCaller(String str) throws RemoteException;

    boolean isProfileEnabled(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isProfileTurnedOn(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isRoamingAllowed(ContextInfo contextInfo, String str) throws RemoteException;

    boolean isWifiFallbackAllowed(ContextInfo contextInfo, String str) throws RemoteException;

    boolean removeProfile(ContextInfo contextInfo, String str) throws RemoteException;

    boolean removeProfileForCurrentContainer(String str) throws RemoteException;

    boolean removeVpnFromBillingProfile(ContextInfo contextInfo, String str, String str2) throws RemoteException;

    boolean removeVpnFromBillingProfileForCurrentContainer(String str) throws RemoteException;

    boolean turnOffProfile(ContextInfo contextInfo, String str) throws RemoteException;

    boolean turnOnProfile(ContextInfo contextInfo, String str) throws RemoteException;

    boolean updateProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) throws RemoteException;

    public abstract class Stub extends Binder implements IEnterpriseBillingPolicy {
        public static final int TRANSACTION_activateProfile = 26;
        public static final int TRANSACTION_addProfile = 1;
        public static final int TRANSACTION_addProfileForCurrentContainer = 28;
        public static final int TRANSACTION_addVpnToBillingProfile = 21;
        public static final int TRANSACTION_addVpnToBillingProfileForCurrentContainer = 35;
        public static final int TRANSACTION_allowRoaming = 24;
        public static final int TRANSACTION_allowWifiFallback = 19;
        public static final int TRANSACTION_disableProfile = 10;
        public static final int TRANSACTION_disableProfileForApps = 9;
        public static final int TRANSACTION_disableProfileForContainer = 8;
        public static final int TRANSACTION_disableProfileForCurrentContainer = 31;
        public static final int TRANSACTION_enableProfileForApps = 7;
        public static final int TRANSACTION_enableProfileForContainer = 6;
        public static final int TRANSACTION_enableProfileForCurrentContainer = 30;
        public static final int TRANSACTION_getApplicationsUsingProfile = 18;
        public static final int TRANSACTION_getAvailableProfiles = 4;
        public static final int TRANSACTION_getAvailableProfilesForCaller = 33;
        public static final int TRANSACTION_getContainersUsingProfile = 17;
        public static final int TRANSACTION_getProfileDetails = 5;
        public static final int TRANSACTION_getProfileForApplication = 16;
        public static final int TRANSACTION_getProfileForContainer = 15;
        public static final int TRANSACTION_getVpnsBoundToProfile = 23;
        public static final int TRANSACTION_isProfileActive = 27;
        public static final int TRANSACTION_isProfileActiveByCaller = 32;
        public static final int TRANSACTION_isProfileEnabled = 11;
        public static final int TRANSACTION_isProfileTurnedOn = 14;
        public static final int TRANSACTION_isRoamingAllowed = 25;
        public static final int TRANSACTION_isWifiFallbackAllowed = 20;
        public static final int TRANSACTION_removeProfile = 3;
        public static final int TRANSACTION_removeProfileForCurrentContainer = 29;
        public static final int TRANSACTION_removeVpnFromBillingProfile = 22;
        public static final int TRANSACTION_removeVpnFromBillingProfileForCurrentContainer = 34;
        public static final int TRANSACTION_turnOffProfile = 13;
        public static final int TRANSACTION_turnOnProfile = 12;
        public static final int TRANSACTION_updateProfile = 2;

        class Proxy implements IEnterpriseBillingPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean activateProfile(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean addProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(enterpriseBillingProfile, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean addProfileForCurrentContainer(EnterpriseBillingProfile enterpriseBillingProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(enterpriseBillingProfile, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean addVpnToBillingProfile(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean addVpnToBillingProfileForCurrentContainer(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean allowRoaming(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public void allowWifiFallback(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean disableProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean disableProfileForApps(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean disableProfileForContainer(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean disableProfileForCurrentContainer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean enableProfileForApps(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean enableProfileForContainer(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean enableProfileForCurrentContainer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public List getApplicationsUsingProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public List getAvailableProfiles(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public List getAvailableProfilesForCaller() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public List getContainersUsingProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IEnterpriseBillingPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public EnterpriseBillingProfile getProfileDetails(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseBillingProfile) parcelObtain2.readTypedObject(EnterpriseBillingProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public EnterpriseBillingProfile getProfileForApplication(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseBillingProfile) parcelObtain2.readTypedObject(EnterpriseBillingProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public EnterpriseBillingProfile getProfileForContainer(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (EnterpriseBillingProfile) parcelObtain2.readTypedObject(EnterpriseBillingProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public List<String> getVpnsBoundToProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean isProfileActive(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean isProfileActiveByCaller(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean isProfileEnabled(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean isProfileTurnedOn(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean isRoamingAllowed(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean isWifiFallbackAllowed(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean removeProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean removeProfileForCurrentContainer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean removeVpnFromBillingProfile(ContextInfo contextInfo, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean removeVpnFromBillingProfileForCurrentContainer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean turnOffProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean turnOnProfile(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public boolean updateProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(enterpriseBillingProfile, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEnterpriseBillingPolicy.DESCRIPTOR);
        }

        public static IEnterpriseBillingPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEnterpriseBillingPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IEnterpriseBillingPolicy)) ? new Proxy(iBinder) : (IEnterpriseBillingPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnterpriseBillingPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnterpriseBillingPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    EnterpriseBillingProfile enterpriseBillingProfile = (EnterpriseBillingProfile) parcel.readTypedObject(EnterpriseBillingProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddProfile = addProfile(contextInfo, enterpriseBillingProfile);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddProfile);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    EnterpriseBillingProfile enterpriseBillingProfile2 = (EnterpriseBillingProfile) parcel.readTypedObject(EnterpriseBillingProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zUpdateProfile = updateProfile(contextInfo2, enterpriseBillingProfile2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateProfile);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveProfile = removeProfile(contextInfo3, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveProfile);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List availableProfiles = getAvailableProfiles(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeList(availableProfiles);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseBillingProfile profileDetails = getProfileDetails(contextInfo5, string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileDetails, 1);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zEnableProfileForContainer = enableProfileForContainer(contextInfo6, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableProfileForContainer);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zEnableProfileForApps = enableProfileForApps(contextInfo7, string4, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableProfileForApps);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zDisableProfileForContainer = disableProfileForContainer(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableProfileForContainer);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zDisableProfileForApps = disableProfileForApps(contextInfo9, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableProfileForApps);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDisableProfile = disableProfile(contextInfo10, string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableProfile);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsProfileEnabled = isProfileEnabled(contextInfo11, string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProfileEnabled);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zTurnOnProfile = turnOnProfile(contextInfo12, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTurnOnProfile);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zTurnOffProfile = turnOffProfile(contextInfo13, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zTurnOffProfile);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsProfileTurnedOn = isProfileTurnedOn(contextInfo14, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProfileTurnedOn);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    EnterpriseBillingProfile profileForContainer = getProfileForContainer(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileForContainer, 1);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseBillingProfile profileForApplication = getProfileForApplication(contextInfo16, string10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileForApplication, 1);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List containersUsingProfile = getContainersUsingProfile(contextInfo17, string11);
                    parcel2.writeNoException();
                    parcel2.writeList(containersUsingProfile);
                    return true;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List applicationsUsingProfile = getApplicationsUsingProfile(contextInfo18, string12);
                    parcel2.writeNoException();
                    parcel2.writeList(applicationsUsingProfile);
                    return true;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string13 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    allowWifiFallback(contextInfo19, string13, z);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsWifiFallbackAllowed = isWifiFallbackAllowed(contextInfo20, string14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWifiFallbackAllowed);
                    return true;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddVpnToBillingProfile = addVpnToBillingProfile(contextInfo21, string15, string16, string17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddVpnToBillingProfile);
                    return true;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string18 = parcel.readString();
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveVpnFromBillingProfile = removeVpnFromBillingProfile(contextInfo22, string18, string19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveVpnFromBillingProfile);
                    return true;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> vpnsBoundToProfile = getVpnsBoundToProfile(contextInfo23, string20);
                    parcel2.writeNoException();
                    parcel2.writeStringList(vpnsBoundToProfile);
                    return true;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string21 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowRoaming = allowRoaming(contextInfo24, string21, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowRoaming);
                    return true;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsRoamingAllowed = isRoamingAllowed(contextInfo25, string22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRoamingAllowed);
                    return true;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string23 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zActivateProfile = activateProfile(contextInfo26, string23, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zActivateProfile);
                    return true;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsProfileActive = isProfileActive(contextInfo27, string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProfileActive);
                    return true;
                case 28:
                    EnterpriseBillingProfile enterpriseBillingProfile3 = (EnterpriseBillingProfile) parcel.readTypedObject(EnterpriseBillingProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAddProfileForCurrentContainer = addProfileForCurrentContainer(enterpriseBillingProfile3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddProfileForCurrentContainer);
                    return true;
                case 29:
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveProfileForCurrentContainer = removeProfileForCurrentContainer(string25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveProfileForCurrentContainer);
                    return true;
                case 30:
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zEnableProfileForCurrentContainer = enableProfileForCurrentContainer(string26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableProfileForCurrentContainer);
                    return true;
                case 31:
                    boolean zDisableProfileForCurrentContainer = disableProfileForCurrentContainer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableProfileForCurrentContainer);
                    return true;
                case 32:
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsProfileActiveByCaller = isProfileActiveByCaller(string27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsProfileActiveByCaller);
                    return true;
                case 33:
                    List availableProfilesForCaller = getAvailableProfilesForCaller();
                    parcel2.writeNoException();
                    parcel2.writeList(availableProfilesForCaller);
                    return true;
                case 34:
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveVpnFromBillingProfileForCurrentContainer = removeVpnFromBillingProfileForCurrentContainer(string28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveVpnFromBillingProfileForCurrentContainer);
                    return true;
                case 35:
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddVpnToBillingProfileForCurrentContainer = addVpnToBillingProfileForCurrentContainer(string29, string30, string31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddVpnToBillingProfileForCurrentContainer);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IEnterpriseBillingPolicy {
        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean activateProfile(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean addProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean addProfileForCurrentContainer(EnterpriseBillingProfile enterpriseBillingProfile) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean addVpnToBillingProfile(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean addVpnToBillingProfileForCurrentContainer(String str, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean allowRoaming(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean disableProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean disableProfileForApps(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean disableProfileForContainer(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean disableProfileForCurrentContainer() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean enableProfileForApps(ContextInfo contextInfo, String str, List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean enableProfileForContainer(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean enableProfileForCurrentContainer(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public List getApplicationsUsingProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public List getAvailableProfiles(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public List getAvailableProfilesForCaller() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public List getContainersUsingProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public EnterpriseBillingProfile getProfileDetails(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public EnterpriseBillingProfile getProfileForApplication(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public EnterpriseBillingProfile getProfileForContainer(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public List<String> getVpnsBoundToProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean isProfileActive(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean isProfileActiveByCaller(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean isProfileEnabled(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean isProfileTurnedOn(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean isRoamingAllowed(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean isWifiFallbackAllowed(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean removeProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean removeProfileForCurrentContainer(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean removeVpnFromBillingProfile(ContextInfo contextInfo, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean removeVpnFromBillingProfileForCurrentContainer(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean turnOffProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean turnOnProfile(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public boolean updateProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public void allowWifiFallback(ContextInfo contextInfo, String str, boolean z) throws RemoteException {
        }
    }
}
