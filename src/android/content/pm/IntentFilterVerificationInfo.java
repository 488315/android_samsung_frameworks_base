package android.content.pm;

import android.annotation.SystemApi;
import android.hardware.input.KeyboardLayout;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes.dex */
public final class IntentFilterVerificationInfo implements Parcelable {
    private static final String ATTR_DOMAIN_NAME = "name";
    private static final String ATTR_PACKAGE_NAME = "packageName";
    private static final String ATTR_STATUS = "status";
    public static final Parcelable.Creator<IntentFilterVerificationInfo> CREATOR = new Parcelable.Creator<IntentFilterVerificationInfo>() { // from class: android.content.pm.IntentFilterVerificationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentFilterVerificationInfo createFromParcel(Parcel parcel) {
            return new IntentFilterVerificationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentFilterVerificationInfo[] newArray(int i) {
            return new IntentFilterVerificationInfo[i];
        }
    };
    private static final String TAG = "android.content.pm.IntentFilterVerificationInfo";
    private static final String TAG_DOMAIN = "domain";
    private ArraySet<String> mDomains;
    private String mPackageName;
    private int mStatus;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IntentFilterVerificationInfo() {
        this.mDomains = new ArraySet<>();
        this.mPackageName = null;
        this.mStatus = 0;
    }

    public IntentFilterVerificationInfo(String str, ArraySet<String> arraySet) {
        new ArraySet();
        this.mPackageName = str;
        this.mDomains = arraySet;
        this.mStatus = 0;
    }

    public IntentFilterVerificationInfo(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        this.mDomains = new ArraySet<>();
        readFromXml(typedXmlPullParser);
    }

    public IntentFilterVerificationInfo(Parcel parcel) {
        this.mDomains = new ArraySet<>();
        readFromParcel(parcel);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int i) {
        if (i >= 0 && i <= 3) {
            this.mStatus = i;
            return;
        }
        Log.w(TAG, "Trying to set a non supported status: " + i);
    }

    public Set<String> getDomains() {
        return this.mDomains;
    }

    public void setDomains(ArraySet<String> arraySet) {
        this.mDomains = arraySet;
    }

    public String getDomainsString() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = this.mDomains.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(next);
        }
        return sb.toString();
    }

    String getStringFromXml(TypedXmlPullParser typedXmlPullParser, String str, String str2) {
        String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (attributeValue != null) {
            return attributeValue;
        }
        StringBuilder sb = new StringBuilder("Missing element under ");
        String str3 = TAG;
        sb.append(str3);
        sb.append(": ");
        sb.append(str);
        sb.append(" at ");
        sb.append(typedXmlPullParser.getPositionDescription());
        Log.w(str3, sb.toString());
        return str2;
    }

    int getIntFromXml(TypedXmlPullParser typedXmlPullParser, String str, int i) {
        return typedXmlPullParser.getAttributeInt(null, str, i);
    }

    public void readFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        String stringFromXml = getStringFromXml(typedXmlPullParser, "packageName", null);
        this.mPackageName = stringFromXml;
        if (stringFromXml == null) {
            Log.e(TAG, "Package name cannot be null!");
        }
        int intFromXml = getIntFromXml(typedXmlPullParser, "status", -1);
        if (intFromXml == -1) {
            Log.e(TAG, "Unknown status value: " + intFromXml);
        }
        this.mStatus = intFromXml;
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if (name.equals(TAG_DOMAIN)) {
                    String stringFromXml2 = getStringFromXml(typedXmlPullParser, "name", null);
                    if (!TextUtils.isEmpty(stringFromXml2)) {
                        this.mDomains.add(stringFromXml2);
                    }
                } else {
                    Log.w(TAG, "Unknown tag parsing IntentFilter: " + name);
                }
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
    }

    public void writeToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attribute(null, "packageName", this.mPackageName);
        typedXmlSerializer.attributeInt(null, "status", this.mStatus);
        Iterator<String> it = this.mDomains.iterator();
        while (it.hasNext()) {
            String next = it.next();
            typedXmlSerializer.startTag(null, TAG_DOMAIN);
            typedXmlSerializer.attribute(null, "name", next);
            typedXmlSerializer.endTag(null, TAG_DOMAIN);
        }
    }

    public String getStatusString() {
        return getStatusStringFromValue(this.mStatus << 32);
    }

    public static String getStatusStringFromValue(long j) {
        StringBuilder sb = new StringBuilder();
        int i = (int) (j >> 32);
        if (i == 1) {
            sb.append("ask");
        } else if (i == 2) {
            sb.append("always : ");
            sb.append(Long.toHexString(j));
        } else if (i == 3) {
            sb.append("never");
        } else if (i == 4) {
            sb.append("always-ask");
        } else {
            sb.append(KeyboardLayout.LAYOUT_TYPE_UNDEFINED);
        }
        return sb.toString();
    }

    private void readFromParcel(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mStatus = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        this.mDomains.addAll(arrayList);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mStatus);
        parcel.writeStringList(new ArrayList(this.mDomains));
    }
}
