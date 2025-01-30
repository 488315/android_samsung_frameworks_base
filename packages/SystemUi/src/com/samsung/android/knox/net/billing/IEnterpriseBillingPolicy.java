package com.samsung.android.knox.net.billing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IEnterpriseBillingPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy";

    boolean activateProfile(ContextInfo contextInfo, String str, boolean z);

    boolean addProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile);

    boolean addProfileForCurrentContainer(EnterpriseBillingProfile enterpriseBillingProfile);

    boolean addVpnToBillingProfile(ContextInfo contextInfo, String str, String str2, String str3);

    boolean addVpnToBillingProfileForCurrentContainer(String str, String str2, String str3);

    boolean allowRoaming(ContextInfo contextInfo, String str, boolean z);

    void allowWifiFallback(ContextInfo contextInfo, String str, boolean z);

    boolean disableProfile(ContextInfo contextInfo, String str);

    boolean disableProfileForApps(ContextInfo contextInfo, List<String> list);

    boolean disableProfileForContainer(ContextInfo contextInfo);

    boolean disableProfileForCurrentContainer();

    boolean enableProfileForApps(ContextInfo contextInfo, String str, List<String> list);

    boolean enableProfileForContainer(ContextInfo contextInfo, String str);

    boolean enableProfileForCurrentContainer(String str);

    List getApplicationsUsingProfile(ContextInfo contextInfo, String str);

    List getAvailableProfiles(ContextInfo contextInfo);

    List getAvailableProfilesForCaller();

    List getContainersUsingProfile(ContextInfo contextInfo, String str);

    EnterpriseBillingProfile getProfileDetails(ContextInfo contextInfo, String str);

    EnterpriseBillingProfile getProfileForApplication(ContextInfo contextInfo, String str);

    EnterpriseBillingProfile getProfileForContainer(ContextInfo contextInfo);

    List<String> getVpnsBoundToProfile(ContextInfo contextInfo, String str);

    boolean isProfileActive(ContextInfo contextInfo, String str);

    boolean isProfileActiveByCaller(String str);

    boolean isProfileEnabled(ContextInfo contextInfo, String str);

    boolean isProfileTurnedOn(ContextInfo contextInfo, String str);

    boolean isRoamingAllowed(ContextInfo contextInfo, String str);

    boolean isWifiFallbackAllowed(ContextInfo contextInfo, String str);

    boolean removeProfile(ContextInfo contextInfo, String str);

    boolean removeProfileForCurrentContainer(String str);

    boolean removeVpnFromBillingProfile(ContextInfo contextInfo, String str, String str2);

    boolean removeVpnFromBillingProfileForCurrentContainer(String str);

    boolean turnOffProfile(ContextInfo contextInfo, String str);

    boolean turnOnProfile(ContextInfo contextInfo, String str);

    boolean updateProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IEnterpriseBillingPolicy {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IEnterpriseBillingPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean activateProfile(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean addProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(enterpriseBillingProfile, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean addProfileForCurrentContainer(EnterpriseBillingProfile enterpriseBillingProfile) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(enterpriseBillingProfile, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean addVpnToBillingProfile(ContextInfo contextInfo, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean addVpnToBillingProfileForCurrentContainer(String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean allowRoaming(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final void allowWifiFallback(ContextInfo contextInfo, String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean disableProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean disableProfileForApps(ContextInfo contextInfo, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean disableProfileForContainer(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean disableProfileForCurrentContainer() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean enableProfileForApps(ContextInfo contextInfo, String str, List<String> list) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean enableProfileForContainer(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean enableProfileForCurrentContainer(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final List getApplicationsUsingProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final List getAvailableProfiles(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final List getAvailableProfilesForCaller() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final List getContainersUsingProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IEnterpriseBillingPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final EnterpriseBillingProfile getProfileDetails(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseBillingProfile) obtain2.readTypedObject(EnterpriseBillingProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final EnterpriseBillingProfile getProfileForApplication(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseBillingProfile) obtain2.readTypedObject(EnterpriseBillingProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final EnterpriseBillingProfile getProfileForContainer(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (EnterpriseBillingProfile) obtain2.readTypedObject(EnterpriseBillingProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final List<String> getVpnsBoundToProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean isProfileActive(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean isProfileActiveByCaller(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean isProfileEnabled(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean isProfileTurnedOn(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean isRoamingAllowed(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean isWifiFallbackAllowed(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean removeProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
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

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean removeProfileForCurrentContainer(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean removeVpnFromBillingProfile(ContextInfo contextInfo, String str, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean removeVpnFromBillingProfileForCurrentContainer(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean turnOffProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean turnOnProfile(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
            public final boolean updateProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseBillingPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(enterpriseBillingProfile, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IEnterpriseBillingPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IEnterpriseBillingPolicy)) ? new Proxy(iBinder) : (IEnterpriseBillingPolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    boolean addProfile = addProfile(contextInfo, enterpriseBillingProfile);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addProfile);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    EnterpriseBillingProfile enterpriseBillingProfile2 = (EnterpriseBillingProfile) parcel.readTypedObject(EnterpriseBillingProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateProfile = updateProfile(contextInfo2, enterpriseBillingProfile2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateProfile);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeProfile = removeProfile(contextInfo3, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeProfile);
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
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseBillingProfile profileDetails = getProfileDetails(contextInfo5, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileDetails, 1);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean enableProfileForContainer = enableProfileForContainer(contextInfo6, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableProfileForContainer);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean enableProfileForApps = enableProfileForApps(contextInfo7, readString4, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableProfileForApps);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean disableProfileForContainer = disableProfileForContainer(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableProfileForContainer);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean disableProfileForApps = disableProfileForApps(contextInfo9, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableProfileForApps);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean disableProfile = disableProfile(contextInfo10, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableProfile);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProfileEnabled = isProfileEnabled(contextInfo11, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProfileEnabled);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean turnOnProfile = turnOnProfile(contextInfo12, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(turnOnProfile);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean turnOffProfile = turnOffProfile(contextInfo13, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(turnOffProfile);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProfileTurnedOn = isProfileTurnedOn(contextInfo14, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProfileTurnedOn);
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
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    EnterpriseBillingProfile profileForApplication = getProfileForApplication(contextInfo16, readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(profileForApplication, 1);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List containersUsingProfile = getContainersUsingProfile(contextInfo17, readString11);
                    parcel2.writeNoException();
                    parcel2.writeList(containersUsingProfile);
                    return true;
                case 18:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List applicationsUsingProfile = getApplicationsUsingProfile(contextInfo18, readString12);
                    parcel2.writeNoException();
                    parcel2.writeList(applicationsUsingProfile);
                    return true;
                case 19:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString13 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    allowWifiFallback(contextInfo19, readString13, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isWifiFallbackAllowed = isWifiFallbackAllowed(contextInfo20, readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWifiFallbackAllowed);
                    return true;
                case 21:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addVpnToBillingProfile = addVpnToBillingProfile(contextInfo21, readString15, readString16, readString17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addVpnToBillingProfile);
                    return true;
                case 22:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeVpnFromBillingProfile = removeVpnFromBillingProfile(contextInfo22, readString18, readString19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeVpnFromBillingProfile);
                    return true;
                case 23:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> vpnsBoundToProfile = getVpnsBoundToProfile(contextInfo23, readString20);
                    parcel2.writeNoException();
                    parcel2.writeStringList(vpnsBoundToProfile);
                    return true;
                case 24:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString21 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowRoaming = allowRoaming(contextInfo24, readString21, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowRoaming);
                    return true;
                case 25:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRoamingAllowed = isRoamingAllowed(contextInfo25, readString22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRoamingAllowed);
                    return true;
                case 26:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString23 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean activateProfile = activateProfile(contextInfo26, readString23, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(activateProfile);
                    return true;
                case 27:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProfileActive = isProfileActive(contextInfo27, readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProfileActive);
                    return true;
                case 28:
                    EnterpriseBillingProfile enterpriseBillingProfile3 = (EnterpriseBillingProfile) parcel.readTypedObject(EnterpriseBillingProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addProfileForCurrentContainer = addProfileForCurrentContainer(enterpriseBillingProfile3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addProfileForCurrentContainer);
                    return true;
                case 29:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeProfileForCurrentContainer = removeProfileForCurrentContainer(readString25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeProfileForCurrentContainer);
                    return true;
                case 30:
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean enableProfileForCurrentContainer = enableProfileForCurrentContainer(readString26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableProfileForCurrentContainer);
                    return true;
                case 31:
                    boolean disableProfileForCurrentContainer = disableProfileForCurrentContainer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableProfileForCurrentContainer);
                    return true;
                case 32:
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isProfileActiveByCaller = isProfileActiveByCaller(readString27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isProfileActiveByCaller);
                    return true;
                case 33:
                    List availableProfilesForCaller = getAvailableProfilesForCaller();
                    parcel2.writeNoException();
                    parcel2.writeList(availableProfilesForCaller);
                    return true;
                case 34:
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeVpnFromBillingProfileForCurrentContainer = removeVpnFromBillingProfileForCurrentContainer(readString28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeVpnFromBillingProfileForCurrentContainer);
                    return true;
                case 35:
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addVpnToBillingProfileForCurrentContainer = addVpnToBillingProfileForCurrentContainer(readString29, readString30, readString31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addVpnToBillingProfileForCurrentContainer);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IEnterpriseBillingPolicy {
        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean activateProfile(ContextInfo contextInfo, String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean addProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean addProfileForCurrentContainer(EnterpriseBillingProfile enterpriseBillingProfile) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean addVpnToBillingProfile(ContextInfo contextInfo, String str, String str2, String str3) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean addVpnToBillingProfileForCurrentContainer(String str, String str2, String str3) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean allowRoaming(ContextInfo contextInfo, String str, boolean z) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean disableProfile(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean disableProfileForApps(ContextInfo contextInfo, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean disableProfileForContainer(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean disableProfileForCurrentContainer() {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean enableProfileForApps(ContextInfo contextInfo, String str, List<String> list) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean enableProfileForContainer(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean enableProfileForCurrentContainer(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final List getApplicationsUsingProfile(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final List getAvailableProfiles(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final List getAvailableProfilesForCaller() {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final List getContainersUsingProfile(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final EnterpriseBillingProfile getProfileDetails(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final EnterpriseBillingProfile getProfileForApplication(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final EnterpriseBillingProfile getProfileForContainer(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final List<String> getVpnsBoundToProfile(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean isProfileActive(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean isProfileActiveByCaller(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean isProfileEnabled(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean isProfileTurnedOn(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean isRoamingAllowed(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean isWifiFallbackAllowed(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean removeProfile(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean removeProfileForCurrentContainer(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean removeVpnFromBillingProfile(ContextInfo contextInfo, String str, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean removeVpnFromBillingProfileForCurrentContainer(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean turnOffProfile(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean turnOnProfile(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final boolean updateProfile(ContextInfo contextInfo, EnterpriseBillingProfile enterpriseBillingProfile) {
            return false;
        }

        @Override // com.samsung.android.knox.net.billing.IEnterpriseBillingPolicy
        public final void allowWifiFallback(ContextInfo contextInfo, String str, boolean z) {
        }
    }
}
