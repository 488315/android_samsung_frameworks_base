package android.app;

import android.annotation.SystemApi;
import android.content.pm.ParceledListSlice;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.util.proto.ProtoOutputStream;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class NotificationChannelGroup implements Parcelable {
    private static final String ATT_BLOCKED = "blocked";
    private static final String ATT_DESC = "desc";
    private static final String ATT_ID = "id";
    private static final String ATT_NAME = "name";
    private static final String ATT_USER_LOCKED = "locked";
    public static final Parcelable.Creator<NotificationChannelGroup> CREATOR =
            new Parcelable.Creator<NotificationChannelGroup>() { // from class:
                // android.app.NotificationChannelGroup.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NotificationChannelGroup createFromParcel(Parcel in) {
                    return new NotificationChannelGroup(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public NotificationChannelGroup[] newArray(int size) {
                    return new NotificationChannelGroup[size];
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

    public NotificationChannelGroup(String id, CharSequence name) {
        this.mChannels = new ArrayList();
        this.mId = getTrimmedString(id);
        this.mName = name != null ? getTrimmedString(name.toString()) : null;
    }

    protected NotificationChannelGroup(Parcel in) {
        this.mChannels = new ArrayList();
        if (in.readByte() != 0) {
            this.mId = getTrimmedString(in.readString());
        } else {
            this.mId = null;
        }
        if (in.readByte() != 0) {
            this.mName = getTrimmedString(in.readString());
        } else {
            this.mName = "";
        }
        if (in.readByte() != 0) {
            this.mDescription = getTrimmedString(in.readString());
        } else {
            this.mDescription = null;
        }
        if (in.readByte() != 0) {
            this.mChannels =
                    ((ParceledListSlice)
                                    in.readParcelable(
                                            NotificationChannelGroup.class.getClassLoader(),
                                            ParceledListSlice.class))
                            .getList();
        } else {
            this.mChannels = new ArrayList();
        }
        this.mBlocked = in.readBoolean();
        this.mUserLockedFields = in.readInt();
    }

    private String getTrimmedString(String input) {
        if (input != null && input.length() > 1000) {
            return input.substring(0, 1000);
        }
        return input;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (this.mId != null) {
            dest.writeByte((byte) 1);
            dest.writeString(this.mId);
        } else {
            dest.writeByte((byte) 0);
        }
        if (this.mName != null) {
            dest.writeByte((byte) 1);
            dest.writeString(this.mName.toString());
        } else {
            dest.writeByte((byte) 0);
        }
        if (this.mDescription != null) {
            dest.writeByte((byte) 1);
            dest.writeString(this.mDescription);
        } else {
            dest.writeByte((byte) 0);
        }
        if (this.mChannels != null) {
            dest.writeByte((byte) 1);
            dest.writeParcelable(new ParceledListSlice(this.mChannels), flags);
        } else {
            dest.writeByte((byte) 0);
        }
        dest.writeBoolean(this.mBlocked);
        dest.writeInt(this.mUserLockedFields);
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

    public void setDescription(String description) {
        this.mDescription = getTrimmedString(description);
    }

    public void setBlocked(boolean blocked) {
        this.mBlocked = blocked;
    }

    public void addChannel(NotificationChannel channel) {
        this.mChannels.add(channel);
    }

    public void setChannels(List<NotificationChannel> channels) {
        this.mChannels = channels;
    }

    public void lockFields(int field) {
        this.mUserLockedFields |= field;
    }

    public void unlockFields(int field) {
        this.mUserLockedFields &= ~field;
    }

    public int getUserLockedFields() {
        return this.mUserLockedFields;
    }

    public void populateFromXml(TypedXmlPullParser parser) {
        setDescription(parser.getAttributeValue(null, ATT_DESC));
        setBlocked(parser.getAttributeBoolean(null, "blocked", false));
    }

    public void writeXml(TypedXmlSerializer out) throws IOException {
        out.startTag(null, TAG_GROUP);
        out.attribute(null, "id", getId());
        if (getName() != null) {
            out.attribute(null, "name", getName().toString());
        }
        if (getDescription() != null) {
            out.attribute(null, ATT_DESC, getDescription().toString());
        }
        out.attributeBoolean(null, "blocked", isBlocked());
        out.attributeInt(null, "locked", this.mUserLockedFields);
        out.endTag(null, TAG_GROUP);
    }

    @SystemApi
    public JSONObject toJson() throws JSONException {
        JSONObject record = new JSONObject();
        record.put("id", getId());
        record.put("name", getName());
        record.put(ATT_DESC, getDescription());
        record.put("blocked", isBlocked());
        record.put("locked", this.mUserLockedFields);
        return record;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationChannelGroup that = (NotificationChannelGroup) o;
        if (isBlocked() == that.isBlocked()
                && this.mUserLockedFields == that.mUserLockedFields
                && Objects.equals(getId(), that.getId())
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getChannels(), that.getChannels())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                getId(),
                getName(),
                getDescription(),
                Boolean.valueOf(isBlocked()),
                getChannels(),
                Integer.valueOf(this.mUserLockedFields));
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public NotificationChannelGroup m454clone() {
        NotificationChannelGroup cloned = new NotificationChannelGroup(getId(), getName());
        cloned.setDescription(getDescription());
        cloned.setBlocked(isBlocked());
        cloned.setChannels(getChannels());
        cloned.lockFields(this.mUserLockedFields);
        return cloned;
    }

    public String toString() {
        String fixedId = getRedatedString(this.mId);
        String fixedName = getRedatedString(this.mName.toString());
        return "NotificationChannelGroup{mId='"
                + fixedId
                + DateFormat.QUOTE
                + ", mName="
                + fixedName
                + ", mDescription="
                + (!TextUtils.isEmpty(this.mDescription) ? "hasDescription " : "")
                + ", mBlocked="
                + this.mBlocked
                + ", mChannels="
                + this.mChannels
                + ", mUserLockedFields="
                + this.mUserLockedFields
                + '}';
    }

    private String getRedatedString(String str) {
        if (isMatchPrivatePattern(str)) {
            return (String) TextUtils.trimToLengthWithEllipsis(str, 6);
        }
        return str;
    }

    private boolean isMatchPrivatePattern(String pattern) {
        if (pattern == null) {
            return false;
        }
        if (Patterns.PHONE.matcher(pattern).matches()
                || Patterns.WEB_URL.matcher(pattern).matches()) {
            return true;
        }
        boolean atFound = false;
        boolean commaFound = false;
        int i = 0;
        while (true) {
            if (i < pattern.length()) {
                if (pattern.charAt(i) == '@') {
                    atFound = true;
                }
                if (!atFound || pattern.charAt(i) != '.') {
                    i++;
                } else {
                    commaFound = true;
                    break;
                }
            } else {
                break;
            }
        }
        return commaFound;
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, this.mId);
        proto.write(1138166333442L, this.mName.toString());
        proto.write(1138166333443L, this.mDescription);
        proto.write(1133871366148L, this.mBlocked);
        for (NotificationChannel channel : this.mChannels) {
            channel.dumpDebug(proto, 2246267895813L);
        }
        proto.end(token);
    }
}
