package android.nfc.cardemulation;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.proto.ProtoOutputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@SystemApi
/* loaded from: classes3.dex */
public final class AidGroup implements Parcelable {
    private static final int MAX_NUM_AIDS = 256;
    private static final String TAG = "AidGroup";
    private final List<String> mAids;
    private final String mCategory;
    private final String mDescription;
    public static final Parcelable.Creator<AidGroup> CREATOR =
            new Parcelable.Creator<AidGroup>() { // from class: android.nfc.cardemulation.AidGroup.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AidGroup createFromParcel(Parcel source) {
                    String category = source.readString8();
                    int listSize = source.readInt();
                    ArrayList<String> aidList = new ArrayList<>();
                    if (listSize > 0) {
                        source.readStringList(aidList);
                    }
                    return new AidGroup(aidList, category);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public AidGroup[] newArray(int size) {
                    return new AidGroup[size];
                }
            };
    private static final Pattern AID_PATTERN = Pattern.compile("[0-9A-Fa-f]{10,32}\\*?\\#?");

    public AidGroup(List<String> aids, String category) {
        if (aids == null || aids.size() == 0) {
            throw new IllegalArgumentException("No AIDS in AID group.");
        }
        if (aids.size() > 256) {
            throw new IllegalArgumentException("Too many AIDs in AID group.");
        }
        for (String aid : aids) {
            if (!isValidAid(aid)) {
                throw new IllegalArgumentException("AID " + aid + " is not a valid AID.");
            }
        }
        if (isValidCategory(category)) {
            this.mCategory = category;
        } else {
            this.mCategory = "other";
        }
        this.mAids = new ArrayList(aids.size());
        Iterator<String> it = aids.iterator();
        while (it.hasNext()) {
            this.mAids.add(it.next().toUpperCase(Locale.US));
        }
        this.mDescription = null;
    }

    AidGroup(String category, String description) {
        this.mAids = new ArrayList();
        this.mCategory = category;
        this.mDescription = description;
    }

    public String getCategory() {
        return this.mCategory;
    }

    public List<String> getAids() {
        return this.mAids;
    }

    public String toString() {
        StringBuilder out = new StringBuilder("Category: " + this.mCategory + ", AIDs:");
        for (String aid : this.mAids) {
            out.append(aid);
            out.append(", ");
        }
        return out.toString();
    }

    public void dump(ProtoOutputStream proto) {
        proto.write(1138166333441L, this.mCategory);
        for (String aid : this.mAids) {
            proto.write(2237677961218L, aid);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString8(this.mCategory);
        dest.writeInt(this.mAids.size());
        if (this.mAids.size() > 0) {
            dest.writeStringList(this.mAids);
        }
    }

    public static AidGroup createFromXml(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String category = null;
        ArrayList<String> aids = new ArrayList<>();
        boolean inGroup = false;
        int eventType = parser.getEventType();
        int minDepth = parser.getDepth();
        while (eventType != 1 && parser.getDepth() >= minDepth) {
            String tagName = parser.getName();
            if (eventType == 2) {
                if (tagName.equals("aid")) {
                    if (inGroup) {
                        String aid = parser.getAttributeValue(null, "value");
                        if (aid != null) {
                            aids.add(aid.toUpperCase());
                        }
                    } else {
                        Log.d(TAG, "Ignoring <aid> tag while not in group");
                    }
                } else if (tagName.equals("aid-group")) {
                    category = parser.getAttributeValue(null, "category");
                    if (category == null) {
                        Log.e(TAG, "<aid-group> tag without valid category");
                        return null;
                    }
                    inGroup = true;
                } else {
                    Log.d(TAG, "Ignoring unexpected tag: " + tagName);
                }
            } else if (eventType == 3
                    && tagName.equals("aid-group")
                    && inGroup
                    && aids.size() > 0) {
                AidGroup group = new AidGroup(aids, category);
                return group;
            }
            eventType = parser.next();
        }
        return null;
    }

    public void writeAsXml(XmlSerializer out) throws IOException {
        out.startTag(null, "aid-group");
        out.attribute(null, "category", this.mCategory);
        for (String aid : this.mAids) {
            out.startTag(null, "aid");
            out.attribute(null, "value", aid);
            out.endTag(null, "aid");
        }
        out.endTag(null, "aid-group");
    }

    private static boolean isValidCategory(String category) {
        return "payment".equals(category) || "other".equals(category);
    }

    private static boolean isValidAid(String aid) {
        if (aid == null) {
            return false;
        }
        if ((aid.endsWith("*") || aid.endsWith("#")) && aid.length() % 2 == 0) {
            Log.e(TAG, "AID " + aid + " is not a valid AID.");
            return false;
        }
        if (!aid.endsWith("*") && !aid.endsWith("#") && aid.length() % 2 != 0) {
            Log.e(TAG, "AID " + aid + " is not a valid AID.");
            return false;
        }
        if (!AID_PATTERN.matcher(aid).matches()) {
            Log.e(TAG, "AID " + aid + " is not a valid AID.");
            return false;
        }
        return true;
    }
}
