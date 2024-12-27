package android.nfc.cardemulation;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;

import com.android.internal.R;
import com.android.server.SecureKeyConst;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.PrintWriter;

@SystemApi
public final class NfcFServiceInfo implements Parcelable {
    public static final Parcelable.Creator<NfcFServiceInfo> CREATOR =
            new Parcelable.Creator<
                    NfcFServiceInfo>() { // from class: android.nfc.cardemulation.NfcFServiceInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NfcFServiceInfo createFromParcel(Parcel source) {
                    String dynamicSystemCode;
                    String dynamicNfcid2;
                    ResolveInfo info = ResolveInfo.CREATOR.createFromParcel(source);
                    String description = source.readString();
                    String systemCode = source.readString();
                    if (source.readInt() == 0) {
                        dynamicSystemCode = null;
                    } else {
                        String dynamicSystemCode2 = source.readString();
                        dynamicSystemCode = dynamicSystemCode2;
                    }
                    String nfcid2 = source.readString();
                    if (source.readInt() == 0) {
                        dynamicNfcid2 = null;
                    } else {
                        String dynamicNfcid22 = source.readString();
                        dynamicNfcid2 = dynamicNfcid22;
                    }
                    int uid = source.readInt();
                    String t3tPmm = source.readString();
                    NfcFServiceInfo service =
                            new NfcFServiceInfo(
                                    info,
                                    description,
                                    systemCode,
                                    dynamicSystemCode,
                                    nfcid2,
                                    dynamicNfcid2,
                                    uid,
                                    t3tPmm);
                    return service;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NfcFServiceInfo[] newArray(int size) {
                    return new NfcFServiceInfo[size];
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

    public NfcFServiceInfo(
            ResolveInfo info,
            String description,
            String systemCode,
            String dynamicSystemCode,
            String nfcid2,
            String dynamicNfcid2,
            int uid,
            String t3tPmm) {
        this.mService = info;
        this.mDescription = description;
        this.mSystemCode = systemCode;
        this.mDynamicSystemCode = dynamicSystemCode;
        this.mNfcid2 = nfcid2;
        this.mDynamicNfcid2 = dynamicNfcid2;
        this.mUid = uid;
        this.mT3tPmm = t3tPmm;
    }

    public NfcFServiceInfo(PackageManager pm, ResolveInfo info)
            throws XmlPullParserException, IOException {
        String str;
        int depth;
        ServiceInfo si = info.serviceInfo;
        XmlResourceParser parser = null;
        try {
            try {
                parser = si.loadXmlMetaData(pm, "android.nfc.cardemulation.host_nfcf_service");
                if (parser == null) {
                    throw new XmlPullParserException(
                            "No android.nfc.cardemulation.host_nfcf_service meta-data");
                }
                for (int eventType = parser.getEventType();
                        eventType != 2 && eventType != 1;
                        eventType = parser.next()) {}
                if (!"host-nfcf-service".equals(parser.getName())) {
                    throw new XmlPullParserException(
                            "Meta-data does not start with <host-nfcf-service> tag");
                }
                Resources res = pm.getResourcesForApplication(si.applicationInfo);
                AttributeSet attrs = Xml.asAttributeSet(parser);
                TypedArray sa = res.obtainAttributes(attrs, R.styleable.HostNfcFService);
                this.mService = info;
                this.mDescription = sa.getString(0);
                this.mDynamicSystemCode = null;
                this.mDynamicNfcid2 = null;
                sa.recycle();
                String systemCode = null;
                String nfcid2 = null;
                String t3tPmm = null;
                int depth2 = parser.getDepth();
                while (true) {
                    int eventType2 = parser.next();
                    str = "NULL";
                    if (eventType2 != 3) {
                        depth = depth2;
                    } else {
                        depth = depth2;
                        if (parser.getDepth() <= depth) {
                            break;
                        }
                    }
                    if (eventType2 == 1) {
                        break;
                    }
                    String tagName = parser.getName();
                    if (eventType2 == 2
                            && "system-code-filter".equals(tagName)
                            && systemCode == null) {
                        TypedArray a = res.obtainAttributes(attrs, R.styleable.SystemCodeFilter);
                        String systemCode2 = a.getString(0).toUpperCase();
                        if (!isValidSystemCode(systemCode2)
                                && !systemCode2.equalsIgnoreCase("NULL")) {
                            Log.e(TAG, "Invalid System Code: " + systemCode2);
                            systemCode = null;
                        } else {
                            systemCode = systemCode2;
                        }
                        a.recycle();
                        depth2 = depth;
                    } else if (eventType2 == 2
                            && "nfcid2-filter".equals(tagName)
                            && nfcid2 == null) {
                        TypedArray a2 = res.obtainAttributes(attrs, R.styleable.Nfcid2Filter);
                        String nfcid22 = a2.getString(0).toUpperCase();
                        if (!nfcid22.equalsIgnoreCase("RANDOM")
                                && !nfcid22.equalsIgnoreCase("NULL")
                                && !isValidNfcid2(nfcid22)) {
                            Log.e(TAG, "Invalid NFCID2: " + nfcid22);
                            nfcid2 = null;
                        } else {
                            nfcid2 = nfcid22;
                        }
                        a2.recycle();
                        depth2 = depth;
                    } else if (eventType2 == 2
                            && tagName.equals("t3tPmm-filter")
                            && t3tPmm == null) {
                        TypedArray a3 = res.obtainAttributes(attrs, R.styleable.T3tPmmFilter);
                        t3tPmm = a3.getString(0).toUpperCase();
                        a3.recycle();
                        depth2 = depth;
                    } else {
                        depth2 = depth;
                    }
                }
                this.mSystemCode = systemCode == null ? "NULL" : systemCode;
                if (nfcid2 != null) {
                    str = nfcid2;
                }
                this.mNfcid2 = str;
                this.mT3tPmm = t3tPmm == null ? DEFAULT_T3T_PMM : t3tPmm;
                this.mUid = si.applicationInfo.uid;
            } catch (PackageManager.NameNotFoundException e) {
                throw new XmlPullParserException("Unable to create context for: " + si.packageName);
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    public ComponentName getComponent() {
        return new ComponentName(
                this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public String getSystemCode() {
        return this.mDynamicSystemCode == null ? this.mSystemCode : this.mDynamicSystemCode;
    }

    public void setDynamicSystemCode(String systemCode) {
        this.mDynamicSystemCode = systemCode;
    }

    public String getNfcid2() {
        return this.mDynamicNfcid2 == null ? this.mNfcid2 : this.mDynamicNfcid2;
    }

    public void setDynamicNfcid2(String nfcid2) {
        this.mDynamicNfcid2 = nfcid2;
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

    public CharSequence loadLabel(PackageManager pm) {
        return this.mService.loadLabel(pm);
    }

    public Drawable loadIcon(PackageManager pm) {
        return this.mService.loadIcon(pm);
    }

    public String toString() {
        StringBuilder out = new StringBuilder("NfcFService: ");
        out.append(getComponent());
        out.append(", UID: " + this.mUid);
        out.append(", description: " + this.mDescription);
        out.append(", System Code: " + this.mSystemCode);
        if (this.mDynamicSystemCode != null) {
            out.append(", dynamic System Code: " + this.mDynamicSystemCode);
        }
        out.append(", NFCID2: " + this.mNfcid2);
        if (this.mDynamicNfcid2 != null) {
            out.append(", dynamic NFCID2: " + this.mDynamicNfcid2);
        }
        out.append(", T3T PMM:" + this.mT3tPmm);
        return out.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NfcFServiceInfo)) {
            return false;
        }
        NfcFServiceInfo thatService = (NfcFServiceInfo) o;
        return thatService.getComponent().equals(getComponent())
                && thatService.getUid() == getUid()
                && thatService.mSystemCode.equalsIgnoreCase(this.mSystemCode)
                && thatService.mNfcid2.equalsIgnoreCase(this.mNfcid2)
                && thatService.mT3tPmm.equalsIgnoreCase(this.mT3tPmm);
    }

    public int hashCode() {
        return getComponent().hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mService.writeToParcel(dest, flags);
        dest.writeString(this.mDescription);
        dest.writeString(this.mSystemCode);
        dest.writeInt(this.mDynamicSystemCode != null ? 1 : 0);
        if (this.mDynamicSystemCode != null) {
            dest.writeString(this.mDynamicSystemCode);
        }
        dest.writeString(this.mNfcid2);
        dest.writeInt(this.mDynamicNfcid2 == null ? 0 : 1);
        if (this.mDynamicNfcid2 != null) {
            dest.writeString(this.mDynamicNfcid2);
        }
        dest.writeInt(this.mUid);
        dest.writeString(this.mT3tPmm);
    }

    public void dump(ParcelFileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println(
                "    "
                        + getComponent()
                        + " (Description: "
                        + getDescription()
                        + ") (UID: "
                        + getUid()
                        + NavigationBarInflaterView.KEY_CODE_END);
        pw.println("    System Code: " + getSystemCode());
        pw.println("    NFCID2: " + getNfcid2());
        pw.println("    T3tPmm: " + getT3tPmm());
    }

    public void dumpDebug(ProtoOutputStream proto) {
        getComponent().dumpDebug(proto, 1146756268033L);
        proto.write(1138166333442L, getDescription());
        proto.write(1138166333443L, getSystemCode());
        proto.write(1138166333444L, getNfcid2());
        proto.write(1138166333445L, getT3tPmm());
    }

    private static boolean isValidSystemCode(String systemCode) {
        if (systemCode == null) {
            return false;
        }
        if (systemCode.length() != 4) {
            Log.e(TAG, "System Code " + systemCode + " is not a valid System Code.");
            return false;
        }
        if (!systemCode.startsWith("4")
                || systemCode.toUpperCase().endsWith(SecureKeyConst.AT_CMD_DRK_V1_WRITING_END)) {
            Log.e(TAG, "System Code " + systemCode + " is not a valid System Code.");
            return false;
        }
        try {
            Integer.parseInt(systemCode, 16);
            return true;
        } catch (NumberFormatException e) {
            Log.e(TAG, "System Code " + systemCode + " is not a valid System Code.");
            return false;
        }
    }

    private static boolean isValidNfcid2(String nfcid2) {
        if (nfcid2 == null) {
            return false;
        }
        if (nfcid2.length() != 16) {
            Log.e(TAG, "NFCID2 " + nfcid2 + " is not a valid NFCID2.");
            return false;
        }
        if (!nfcid2.toUpperCase().startsWith("02FE")) {
            Log.e(TAG, "NFCID2 " + nfcid2 + " is not a valid NFCID2.");
            return false;
        }
        try {
            Long.parseLong(nfcid2, 16);
            return true;
        } catch (NumberFormatException e) {
            Log.e(TAG, "NFCID2 " + nfcid2 + " is not a valid NFCID2.");
            return false;
        }
    }
}
