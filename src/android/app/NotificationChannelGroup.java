package android.app;

import android.annotation.SystemApi;
import android.content.pm.ParceledListSlice;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Patterns;
import android.util.proto.ProtoOutputStream;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class NotificationChannelGroup implements Parcelable {
    private static final String ATT_BLOCKED = "blocked";
    private static final String ATT_DESC = "desc";
    private static final String ATT_ID = "id";
    private static final String ATT_NAME = "name";
    private static final String ATT_USER_LOCKED = "locked";
    public static final Parcelable.Creator<NotificationChannelGroup> CREATOR = new Parcelable.Creator<NotificationChannelGroup>() { // from class: android.app.NotificationChannelGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationChannelGroup createFromParcel(Parcel parcel) {
            return new NotificationChannelGroup(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NotificationChannelGroup[] newArray(int i) {
            return new NotificationChannelGroup[i];
        }
    };
    public static final int MAX_TEXT_LENGTH = 1000;
    private static final String TAG_GROUP = "channelGroup";
    public static final int USER_LOCKED_BLOCKED_STATE = 1;
    private boolean mBlocked;
    private List<NotificationChannel> mChannels;
    private String mDescription;
    private final String mId;
    private CharSequence mName;
    private int mUserLockedFields;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NotificationChannelGroup(String str, CharSequence charSequence) {
        this.mChannels = new ArrayList();
        this.mId = getTrimmedString(str);
        this.mName = charSequence != null ? getTrimmedString(charSequence.toString()) : null;
    }

    protected NotificationChannelGroup(Parcel parcel) {
        this.mChannels = new ArrayList();
        if (parcel.readByte() != 0) {
            this.mId = getTrimmedString(parcel.readString());
        } else {
            this.mId = null;
        }
        if (parcel.readByte() != 0) {
            this.mName = getTrimmedString(parcel.readString());
        } else {
            this.mName = "";
        }
        if (parcel.readByte() != 0) {
            this.mDescription = getTrimmedString(parcel.readString());
        } else {
            this.mDescription = null;
        }
        if (parcel.readByte() != 0) {
            this.mChannels = ((ParceledListSlice) parcel.readParcelable(NotificationChannelGroup.class.getClassLoader(), ParceledListSlice.class)).getList();
        } else {
            this.mChannels = new ArrayList();
        }
        this.mBlocked = parcel.readBoolean();
        this.mUserLockedFields = parcel.readInt();
    }

    private String getTrimmedString(String str) {
        return (str == null || str.length() <= 1000) ? str : str.substring(0, 1000);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mId != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mId);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mName != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mName.toString());
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mDescription != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mDescription);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mChannels != null) {
            parcel.writeByte((byte) 1);
            parcel.writeParcelable(new ParceledListSlice(this.mChannels), i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeBoolean(this.mBlocked);
        parcel.writeInt(this.mUserLockedFields);
    }

    public String getId() {
        return this.mId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public List<NotificationChannel> getChannels() {
        return this.mChannels;
    }

    public boolean isBlocked() {
        return this.mBlocked;
    }

    public void setDescription(String str) {
        this.mDescription = getTrimmedString(str);
    }

    public void setBlocked(boolean z) {
        this.mBlocked = z;
    }

    public void addChannel(NotificationChannel notificationChannel) {
        this.mChannels.add(notificationChannel);
    }

    public void setChannels(List<NotificationChannel> list) {
        this.mChannels.clear();
        if (list != null) {
            this.mChannels.addAll(list);
        }
    }

    public void lockFields(int i) {
        this.mUserLockedFields = i | this.mUserLockedFields;
    }

    public void unlockFields(int i) {
        this.mUserLockedFields = (~i) & this.mUserLockedFields;
    }

    public int getUserLockedFields() {
        return this.mUserLockedFields;
    }

    public void populateFromXml(TypedXmlPullParser typedXmlPullParser) {
        setDescription(typedXmlPullParser.getAttributeValue(null, ATT_DESC));
        setBlocked(typedXmlPullParser.getAttributeBoolean(null, "blocked", false));
    }

    public void writeXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.startTag(null, TAG_GROUP);
        typedXmlSerializer.attribute(null, "id", getId());
        if (getName() != null) {
            typedXmlSerializer.attribute(null, "name", getName().toString());
        }
        if (getDescription() != null) {
            typedXmlSerializer.attribute(null, ATT_DESC, getDescription().toString());
        }
        typedXmlSerializer.attributeBoolean(null, "blocked", isBlocked());
        typedXmlSerializer.attributeInt(null, "locked", this.mUserLockedFields);
        typedXmlSerializer.endTag(null, TAG_GROUP);
    }

    @SystemApi
    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", getId());
        jSONObject.put("name", getName());
        jSONObject.put(ATT_DESC, getDescription());
        jSONObject.put("blocked", isBlocked());
        jSONObject.put("locked", this.mUserLockedFields);
        return jSONObject;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup) obj;
            if (isBlocked() == notificationChannelGroup.isBlocked() && this.mUserLockedFields == notificationChannelGroup.mUserLockedFields && Objects.equals(getId(), notificationChannelGroup.getId()) && Objects.equals(getName(), notificationChannelGroup.getName()) && Objects.equals(getDescription(), notificationChannelGroup.getDescription()) && Objects.equals(getChannels(), notificationChannelGroup.getChannels())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), Boolean.valueOf(isBlocked()), getChannels(), Integer.valueOf(this.mUserLockedFields));
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public NotificationChannelGroup m477clone() {
        NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(getId(), getName());
        notificationChannelGroup.setDescription(getDescription());
        notificationChannelGroup.setBlocked(isBlocked());
        Iterator<NotificationChannel> it = this.mChannels.iterator();
        while (it.hasNext()) {
            notificationChannelGroup.addChannel(it.next().copy());
        }
        notificationChannelGroup.lockFields(this.mUserLockedFields);
        return notificationChannelGroup;
    }

    public String toString() {
        String redatedString = getRedatedString(this.mId);
        String redatedString2 = getRedatedString(this.mName.toString());
        StringBuilder sb = new StringBuilder("NotificationChannelGroup{mId='");
        sb.append(redatedString);
        sb.append("', mName=");
        sb.append(redatedString2);
        sb.append(", mDescription=");
        sb.append(!TextUtils.isEmpty(this.mDescription) ? "hasDescription " : "");
        sb.append(", mBlocked=");
        sb.append(this.mBlocked);
        sb.append(", mChannels=");
        sb.append(this.mChannels);
        sb.append(", mUserLockedFields=");
        sb.append(this.mUserLockedFields);
        sb.append('}');
        return sb.toString();
    }

    private String getRedatedString(String str) {
        return isMatchPrivatePattern(str) ? (String) TextUtils.trimToLengthWithEllipsis(str, 6) : str;
    }

    private boolean isMatchPrivatePattern(String str) {
        if (str == null) {
            return false;
        }
        if (Patterns.PHONE.matcher(str).matches() || Patterns.WEB_URL.matcher(str).matches()) {
            return true;
        }
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '@') {
                z = true;
            }
            if (z && str.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mId);
        protoOutputStream.write(1138166333442L, this.mName.toString());
        protoOutputStream.write(1138166333443L, this.mDescription);
        protoOutputStream.write(1133871366148L, this.mBlocked);
        Iterator<NotificationChannel> it = this.mChannels.iterator();
        while (it.hasNext()) {
            it.next().dumpDebug(protoOutputStream, 2246267895813L);
        }
        protoOutputStream.end(jStart);
    }
}
