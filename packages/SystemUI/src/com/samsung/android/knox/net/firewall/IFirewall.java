package com.samsung.android.knox.net.firewall;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IFirewall extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.firewall.IFirewall";

    FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, int i) throws RemoteException;

    FirewallResponse[] addRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr) throws RemoteException;

    FirewallResponse[] clearAllDomainFilterRules(ContextInfo contextInfo) throws RemoteException;

    FirewallResponse[] clearRules(ContextInfo contextInfo, int i) throws RemoteException;

    FirewallResponse enableDomainFilterOnIptables(ContextInfo contextInfo, boolean z) throws RemoteException;

    FirewallResponse enableDomainFilterReport(ContextInfo contextInfo, boolean z) throws RemoteException;

    FirewallResponse enableFirewall(ContextInfo contextInfo, boolean z) throws RemoteException;

    List<DomainFilterReport> getDomainFilterReport(ContextInfo contextInfo, List<String> list) throws RemoteException;

    List<DomainFilterRule> getDomainFilterRules(ContextInfo contextInfo, List<String> list, int i) throws RemoteException;

    FirewallRule[] getRules(ContextInfo contextInfo, int i, String str) throws RemoteException;

    boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isDomainFilterReportEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isFirewallEnabled(ContextInfo contextInfo) throws RemoteException;

    String[] listIptablesRules(ContextInfo contextInfo) throws RemoteException;

    void populateDomainFilterBrokenRules(ContextInfo contextInfo, List<DomainFilterRule> list, int i) throws RemoteException;

    FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, int i) throws RemoteException;

    FirewallResponse[] removeRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr) throws RemoteException;

    boolean shouldBlockDownload(String str, String str2, int i) throws RemoteException;

    public abstract class Stub extends Binder implements IFirewall {
        public static final int TRANSACTION_addDomainFilterRules = 10;
        public static final int TRANSACTION_addRules = 1;
        public static final int TRANSACTION_clearAllDomainFilterRules = 9;
        public static final int TRANSACTION_clearRules = 4;
        public static final int TRANSACTION_enableDomainFilterOnIptables = 16;
        public static final int TRANSACTION_enableDomainFilterReport = 13;
        public static final int TRANSACTION_enableFirewall = 5;
        public static final int TRANSACTION_getDomainFilterReport = 15;
        public static final int TRANSACTION_getDomainFilterRules = 12;
        public static final int TRANSACTION_getRules = 3;
        public static final int TRANSACTION_isDomainFilterOnIptablesEnabled = 17;
        public static final int TRANSACTION_isDomainFilterReportEnabled = 14;
        public static final int TRANSACTION_isFirewallEnabled = 6;
        public static final int TRANSACTION_listIptablesRules = 7;
        public static final int TRANSACTION_populateDomainFilterBrokenRules = 8;
        public static final int TRANSACTION_removeDomainFilterRules = 11;
        public static final int TRANSACTION_removeRules = 2;
        public static final int TRANSACTION_shouldBlockDownload = 18;

        class Proxy implements IFirewall {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallResponse[]) parcelObtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] addRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedArray(firewallRuleArr, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallResponse[]) parcelObtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] clearAllDomainFilterRules(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallResponse[]) parcelObtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] clearRules(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallResponse[]) parcelObtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse enableDomainFilterOnIptables(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallResponse) parcelObtain2.readTypedObject(FirewallResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse enableDomainFilterReport(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallResponse) parcelObtain2.readTypedObject(FirewallResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse enableFirewall(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallResponse) parcelObtain2.readTypedObject(FirewallResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public List<DomainFilterReport> getDomainFilterReport(ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(DomainFilterReport.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public List<DomainFilterRule> getDomainFilterRules(ContextInfo contextInfo, List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(DomainFilterRule.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IFirewall.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallRule[] getRules(ContextInfo contextInfo, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallRule[]) parcelObtain2.createTypedArray(FirewallRule.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public boolean isDomainFilterReportEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public boolean isFirewallEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public String[] listIptablesRules(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public void populateDomainFilterBrokenRules(ContextInfo contextInfo, List<DomainFilterRule> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallResponse[]) parcelObtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] removeRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedArray(firewallRuleArr, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FirewallResponse[]) parcelObtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public boolean shouldBlockDownload(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IFirewall.DESCRIPTOR);
        }

        public static IFirewall asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFirewall.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IFirewall)) ? new Proxy(iBinder) : (IFirewall) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFirewall.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFirewall.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    FirewallRule[] firewallRuleArr = (FirewallRule[]) parcel.createTypedArray(FirewallRule.CREATOR);
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] firewallResponseArrAddRules = addRules(contextInfo, firewallRuleArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(firewallResponseArrAddRules, 1);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    FirewallRule[] firewallRuleArr2 = (FirewallRule[]) parcel.createTypedArray(FirewallRule.CREATOR);
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] firewallResponseArrRemoveRules = removeRules(contextInfo2, firewallRuleArr2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(firewallResponseArrRemoveRules, 1);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    FirewallRule[] rules = getRules(contextInfo3, i3, string);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(rules, 1);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] firewallResponseArrClearRules = clearRules(contextInfo4, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(firewallResponseArrClearRules, 1);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    FirewallResponse firewallResponseEnableFirewall = enableFirewall(contextInfo5, z);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(firewallResponseEnableFirewall, 1);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsFirewallEnabled = isFirewallEnabled(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFirewallEnabled);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] strArrListIptablesRules = listIptablesRules(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrListIptablesRules);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(DomainFilterRule.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    populateDomainFilterBrokenRules(contextInfo8, arrayListCreateTypedArrayList, i5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] firewallResponseArrClearAllDomainFilterRules = clearAllDomainFilterRules(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(firewallResponseArrClearAllDomainFilterRules, 1);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] firewallResponseArrAddDomainFilterRules = addDomainFilterRules(contextInfo10, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(firewallResponseArrAddDomainFilterRules, 1);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] firewallResponseArrRemoveDomainFilterRules = removeDomainFilterRules(contextInfo11, i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(firewallResponseArrRemoveDomainFilterRules, 1);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<DomainFilterRule> domainFilterRules = getDomainFilterRules(contextInfo12, arrayListCreateStringArrayList, i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(domainFilterRules, 1);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    FirewallResponse firewallResponseEnableDomainFilterReport = enableDomainFilterReport(contextInfo13, z2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(firewallResponseEnableDomainFilterReport, 1);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsDomainFilterReportEnabled = isDomainFilterReportEnabled(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDomainFilterReportEnabled);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List<DomainFilterReport> domainFilterReport = getDomainFilterReport(contextInfo15, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(domainFilterReport, 1);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    FirewallResponse firewallResponseEnableDomainFilterOnIptables = enableDomainFilterOnIptables(contextInfo16, z3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(firewallResponseEnableDomainFilterOnIptables, 1);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsDomainFilterOnIptablesEnabled = isDomainFilterOnIptablesEnabled(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDomainFilterOnIptablesEnabled);
                    return true;
                case 18:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zShouldBlockDownload = shouldBlockDownload(string2, string3, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldBlockDownload);
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

    public class Default implements IFirewall {
        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] addRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] clearAllDomainFilterRules(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] clearRules(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse enableDomainFilterOnIptables(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse enableDomainFilterReport(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse enableFirewall(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public List<DomainFilterReport> getDomainFilterReport(ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public List<DomainFilterRule> getDomainFilterRules(ContextInfo contextInfo, List<String> list, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallRule[] getRules(ContextInfo contextInfo, int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public boolean isDomainFilterReportEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public boolean isFirewallEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public String[] listIptablesRules(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] removeRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public boolean shouldBlockDownload(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public void populateDomainFilterBrokenRules(ContextInfo contextInfo, List<DomainFilterRule> list, int i) throws RemoteException {
        }
    }
}
