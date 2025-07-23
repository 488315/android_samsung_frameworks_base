package android.nfc.cardemulation;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.server.SecureKeyConst;
import java.io.PrintWriter;

@SystemApi
/* loaded from: classes3.dex */
public final class NfcFServiceInfo implements Parcelable {
    public static final Parcelable.Creator<NfcFServiceInfo> CREATOR = new Parcelable.Creator<NfcFServiceInfo>() { // from class: android.nfc.cardemulation.NfcFServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NfcFServiceInfo createFromParcel(Parcel parcel) {
            return new NfcFServiceInfo(ResolveInfo.CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? parcel.readString() : null, parcel.readString(), parcel.readInt() != 0 ? parcel.readString() : null, parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NfcFServiceInfo[] newArray(int i) {
            return new NfcFServiceInfo[i];
        }
    };
    private static final String DEFAULT_T3T_PMM = "FFFFFFFFFFFFFFFF";
    static final String TAG = "NfcFServiceInfo";
    private final String mDescription;
    private String mDynamicNfcid2;
    private String mDynamicSystemCode;
    private final String mNfcid2;
    private final ResolveInfo mService;
    private final String mSystemCode;
    private final String mT3tPmm;
    private final int mUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NfcFServiceInfo(ResolveInfo resolveInfo, String str, String str2, String str3, String str4, String str5, int i, String str6) {
        this.mService = resolveInfo;
        this.mDescription = str;
        this.mSystemCode = str2;
        this.mDynamicSystemCode = str3;
        this.mNfcid2 = str4;
        this.mDynamicNfcid2 = str5;
        this.mUid = i;
        this.mT3tPmm = str6;
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x0168  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NfcFServiceInfo(android.content.pm.PackageManager r17, android.content.pm.ResolveInfo r18) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.cardemulation.NfcFServiceInfo.<init>(android.content.pm.PackageManager, android.content.pm.ResolveInfo):void");
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public String getSystemCode() {
        String str = this.mDynamicSystemCode;
        return str == null ? this.mSystemCode : str;
    }

    public void setDynamicSystemCode(String str) {
        this.mDynamicSystemCode = str;
    }

    public String getNfcid2() {
        String str = this.mDynamicNfcid2;
        return str == null ? this.mNfcid2 : str;
    }

    public void setDynamicNfcid2(String str) {
        this.mDynamicNfcid2 = str;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int getUid() {
        return this.mUid;
    }

    public String getT3tPmm() {
        return this.mT3tPmm;
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NfcFService: ");
        sb.append(getComponent());
        sb.append(", UID: " + this.mUid);
        sb.append(", description: " + this.mDescription);
        sb.append(", System Code: " + this.mSystemCode);
        if (this.mDynamicSystemCode != null) {
            sb.append(", dynamic System Code: " + this.mDynamicSystemCode);
        }
        sb.append(", NFCID2: " + this.mNfcid2);
        if (this.mDynamicNfcid2 != null) {
            sb.append(", dynamic NFCID2: " + this.mDynamicNfcid2);
        }
        sb.append(", T3T PMM:" + this.mT3tPmm);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NfcFServiceInfo)) {
            return false;
        }
        NfcFServiceInfo nfcFServiceInfo = (NfcFServiceInfo) obj;
        return nfcFServiceInfo.getComponent().equals(getComponent()) && nfcFServiceInfo.getUid() == getUid() && nfcFServiceInfo.mSystemCode.equalsIgnoreCase(this.mSystemCode) && nfcFServiceInfo.mNfcid2.equalsIgnoreCase(this.mNfcid2) && nfcFServiceInfo.mT3tPmm.equalsIgnoreCase(this.mT3tPmm);
    }

    public int hashCode() {
        return getComponent().hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mDescription);
        parcel.writeString(this.mSystemCode);
        parcel.writeInt(this.mDynamicSystemCode != null ? 1 : 0);
        String str = this.mDynamicSystemCode;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeString(this.mNfcid2);
        parcel.writeInt(this.mDynamicNfcid2 == null ? 0 : 1);
        String str2 = this.mDynamicNfcid2;
        if (str2 != null) {
            parcel.writeString(str2);
        }
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mT3tPmm);
    }

    public void dump(ParcelFileDescriptor parcelFileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("    " + getComponent() + " (Description: " + getDescription() + ") (UID: " + getUid() + NavigationBarInflaterView.KEY_CODE_END);
        StringBuilder sb = new StringBuilder("    System Code: ");
        sb.append(getSystemCode());
        printWriter.println(sb.toString());
        StringBuilder sb2 = new StringBuilder("    NFCID2: ");
        sb2.append(getNfcid2());
        printWriter.println(sb2.toString());
        printWriter.println("    T3tPmm: " + getT3tPmm());
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream) {
        getComponent().dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1138166333442L, getDescription());
        protoOutputStream.write(1138166333443L, getSystemCode());
        protoOutputStream.write(1138166333444L, getNfcid2());
        protoOutputStream.write(1138166333445L, getT3tPmm());
    }

    private static boolean isValidSystemCode(String str) {
        if (str == null) {
            return false;
        }
        if (str.length() != 4) {
            Log.e(TAG, "System Code " + str + " is not a valid System Code.");
            return false;
        }
        if (!str.startsWith("4") || str.toUpperCase().endsWith(SecureKeyConst.AT_CMD_DRK_V1_WRITING_END)) {
            Log.e(TAG, "System Code " + str + " is not a valid System Code.");
            return false;
        }
        try {
            Integer.parseInt(str, 16);
            return true;
        } catch (NumberFormatException unused) {
            Log.e(TAG, "System Code " + str + " is not a valid System Code.");
            return false;
        }
    }

    private static boolean isValidNfcid2(String str) {
        if (str == null) {
            return false;
        }
        if (str.length() != 16) {
            Log.e(TAG, "NFCID2 " + str + " is not a valid NFCID2.");
            return false;
        }
        if (!str.toUpperCase().startsWith("02FE")) {
            Log.e(TAG, "NFCID2 " + str + " is not a valid NFCID2.");
            return false;
        }
        try {
            Long.parseLong(str, 16);
            return true;
        } catch (NumberFormatException unused) {
            Log.e(TAG, "NFCID2 " + str + " is not a valid NFCID2.");
            return false;
        }
    }
}
