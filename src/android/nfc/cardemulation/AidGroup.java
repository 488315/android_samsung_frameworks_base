package android.nfc.cardemulation;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

@SystemApi
/* loaded from: classes3.dex */
public final class AidGroup implements Parcelable {
    private static final int MAX_NUM_AIDS = 256;
    private static final String TAG = "AidGroup";
    private final List<String> mAids;
    private final String mCategory;
    private final String mDescription;
    public static final Parcelable.Creator<AidGroup> CREATOR = new Parcelable.Creator<AidGroup>() { // from class: android.nfc.cardemulation.AidGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AidGroup createFromParcel(Parcel parcel) {
            String string8 = parcel.readString8();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList();
            if (i > 0) {
                parcel.readStringList(arrayList);
            }
            return new AidGroup(arrayList, string8);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AidGroup[] newArray(int i) {
            return new AidGroup[i];
        }
    };
    private static final Pattern AID_PATTERN = Pattern.compile("[0-9A-Fa-f]{10,32}\\*?\\#?");

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AidGroup(List<String> list, String str) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("No AIDS in AID group.");
        }
        if (list.size() > 256) {
            throw new IllegalArgumentException("Too many AIDs in AID group.");
        }
        for (String str2 : list) {
            if (!isValidAid(str2)) {
                throw new IllegalArgumentException("AID " + str2 + " is not a valid AID.");
            }
        }
        if (isValidCategory(str)) {
            this.mCategory = str;
        } else {
            this.mCategory = "other";
        }
        this.mAids = new ArrayList(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            this.mAids.add(it.next().toUpperCase(Locale.US));
        }
        this.mDescription = null;
    }

    AidGroup(String str, String str2) {
        this.mAids = new ArrayList();
        this.mCategory = str;
        this.mDescription = str2;
    }

    public String getCategory() {
        return this.mCategory;
    }

    public List<String> getAids() {
        return this.mAids;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Category: " + this.mCategory + ", AIDs:");
        Iterator<String> it = this.mAids.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(", ");
        }
        return sb.toString();
    }

    public void dump(ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1138166333441L, this.mCategory);
        Iterator<String> it = this.mAids.iterator();
        while (it.hasNext()) {
            protoOutputStream.write(2237677961218L, it.next());
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.mCategory);
        parcel.writeInt(this.mAids.size());
        if (this.mAids.size() > 0) {
            parcel.writeStringList(this.mAids);
        }
    }

    public static AidGroup createFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth();
        boolean z = false;
        String attributeValue = null;
        while (eventType != 1 && xmlPullParser.getDepth() >= depth) {
            String name = xmlPullParser.getName();
            if (eventType == 2) {
                if (name.equals("aid")) {
                    if (z) {
                        String attributeValue2 = xmlPullParser.getAttributeValue(null, "value");
                        if (attributeValue2 != null) {
                            arrayList.add(attributeValue2.toUpperCase());
                        }
                    } else {
                        Log.d(TAG, "Ignoring <aid> tag while not in group");
                    }
                } else if (name.equals("aid-group")) {
                    attributeValue = xmlPullParser.getAttributeValue(null, "category");
                    if (attributeValue == null) {
                        Log.e(TAG, "<aid-group> tag without valid category");
                        return null;
                    }
                    z = true;
                } else {
                    Log.d(TAG, "Ignoring unexpected tag: " + name);
                }
            } else if (eventType == 3 && name.equals("aid-group") && z && arrayList.size() > 0) {
                return new AidGroup(arrayList, attributeValue);
            }
            eventType = xmlPullParser.next();
        }
        return null;
    }

    public void writeAsXml(XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        xmlSerializer.startTag(null, "aid-group");
        xmlSerializer.attribute(null, "category", this.mCategory);
        for (String str : this.mAids) {
            xmlSerializer.startTag(null, "aid");
            xmlSerializer.attribute(null, "value", str);
            xmlSerializer.endTag(null, "aid");
        }
        xmlSerializer.endTag(null, "aid-group");
    }

    private static boolean isValidCategory(String str) {
        return "payment".equals(str) || "other".equals(str);
    }

    private static boolean isValidAid(String str) {
        if (str == null) {
            return false;
        }
        if ((str.endsWith("*") || str.endsWith("#")) && str.length() % 2 == 0) {
            Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        if (!str.endsWith("*") && !str.endsWith("#") && str.length() % 2 != 0) {
            Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        if (AID_PATTERN.matcher(str).matches()) {
            return true;
        }
        Log.e(TAG, "AID " + str + " is not a valid AID.");
        return false;
    }
}
