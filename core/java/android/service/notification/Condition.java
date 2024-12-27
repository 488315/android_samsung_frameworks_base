package android.service.notification;

import android.app.Flags;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.Preconditions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class Condition implements Parcelable {
    public static final Parcelable.Creator<Condition> CREATOR =
            new Parcelable.Creator<
                    Condition>() { // from class: android.service.notification.Condition.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Condition createFromParcel(Parcel source) {
                    return new Condition(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Condition[] newArray(int size) {
                    return new Condition[size];
                }
            };
    public static final int FLAG_RELEVANT_ALWAYS = 2;
    public static final int FLAG_RELEVANT_NOW = 1;
    public static final int MAX_STRING_LENGTH = 1000;
    public static final String SCHEME = "condition";
    public static final int SOURCE_CONTEXT = 3;
    public static final int SOURCE_SCHEDULE = 2;
    public static final int SOURCE_UNKNOWN = 0;
    public static final int SOURCE_USER_ACTION = 1;
    public static final int STATE_ERROR = 3;
    public static final int STATE_FALSE = 0;
    public static final int STATE_TRUE = 1;
    public static final int STATE_UNKNOWN = 2;
    public final int flags;
    public final int icon;
    public final Uri id;
    public final String line1;
    public final String line2;
    public final int source;
    public final int state;
    public final String summary;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    public Condition(Uri id, String summary, int state) {
        this(id, summary, "", "", -1, state, 0, 2);
    }

    public Condition(Uri id, String summary, int state, int source) {
        this(id, summary, "", "", -1, state, source, 2);
    }

    public Condition(
            Uri id, String summary, String line1, String line2, int icon, int state, int flags) {
        this(id, summary, line1, line2, icon, state, 0, flags);
    }

    public Condition(
            Uri id,
            String summary,
            String line1,
            String line2,
            int icon,
            int state,
            int source,
            int flags) {
        if (id == null) {
            throw new IllegalArgumentException("id is required");
        }
        if (summary == null) {
            throw new IllegalArgumentException("summary is required");
        }
        if (!isValidState(state)) {
            throw new IllegalArgumentException("state is invalid: " + state);
        }
        this.id = getTrimmedUri(id);
        this.summary = getTrimmedString(summary);
        this.line1 = getTrimmedString(line1);
        this.line2 = getTrimmedString(line2);
        this.icon = icon;
        this.state = state;
        this.source = checkValidSource(source);
        this.flags = flags;
    }

    public Condition(Parcel source) {
        this(
                (Uri) source.readParcelable(Condition.class.getClassLoader(), Uri.class),
                source.readString(),
                source.readString(),
                source.readString(),
                source.readInt(),
                source.readInt(),
                Flags.modesApi() ? source.readInt() : 0,
                source.readInt());
    }

    public void validate() {
        if (Flags.modesApi()) {
            checkValidSource(this.source);
        }
    }

    private static boolean isValidState(int state) {
        return state >= 0 && state <= 3;
    }

    private static int checkValidSource(int source) {
        if (Flags.modesApi()) {
            Preconditions.checkArgument(
                    source >= 0 && source <= 3,
                    "Condition source must be one of SOURCE_UNKNOWN, SOURCE_USER_ACTION,"
                        + " SOURCE_SCHEDULE, or SOURCE_CONTEXT");
        }
        return source;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.id, 0);
        dest.writeString(this.summary);
        dest.writeString(this.line1);
        dest.writeString(this.line2);
        dest.writeInt(this.icon);
        dest.writeInt(this.state);
        if (Flags.modesApi()) {
            dest.writeInt(this.source);
        }
        dest.writeInt(this.flags);
    }

    public String toString() {
        StringBuilder sb =
                new StringBuilder(Condition.class.getSimpleName())
                        .append('[')
                        .append("state=")
                        .append(stateToString(this.state))
                        .append(",id=")
                        .append(this.id)
                        .append(",summary=")
                        .append(this.summary)
                        .append(",line1=")
                        .append(this.line1)
                        .append(",line2=")
                        .append(this.line2)
                        .append(",icon=")
                        .append(this.icon);
        if (Flags.modesApi()) {
            sb.append(",source=").append(sourceToString(this.source));
        }
        return sb.append(",flags=").append(this.flags).append(']').toString();
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1138166333441L, this.id.toString());
        proto.write(1138166333442L, this.summary);
        proto.write(1138166333443L, this.line1);
        proto.write(1138166333444L, this.line2);
        proto.write(1120986464261L, this.icon);
        proto.write(1159641169926L, this.state);
        proto.write(1120986464263L, this.flags);
        proto.end(token);
    }

    public static String stateToString(int state) {
        if (state == 0) {
            return "STATE_FALSE";
        }
        if (state == 1) {
            return "STATE_TRUE";
        }
        if (state == 2) {
            return "STATE_UNKNOWN";
        }
        if (state == 3) {
            return "STATE_ERROR";
        }
        throw new IllegalArgumentException("state is invalid: " + state);
    }

    public static String sourceToString(int source) {
        if (source == 0) {
            return "SOURCE_UNKNOWN";
        }
        if (source == 1) {
            return "SOURCE_USER_ACTION";
        }
        if (source == 2) {
            return "SOURCE_SCHEDULE";
        }
        if (source == 3) {
            return "SOURCE_CONTEXT";
        }
        throw new IllegalArgumentException("source is invalid: " + source);
    }

    public static String relevanceToString(int flags) {
        boolean now = (flags & 1) != 0;
        boolean always = (flags & 2) != 0;
        return (now || always)
                ? (now && always) ? "NOW, ALWAYS" : now ? "NOW" : "ALWAYS"
                : KeyProperties.DIGEST_NONE;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Condition)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Condition other = (Condition) o;
        boolean finalEquals =
                Objects.equals(other.id, this.id)
                        && Objects.equals(other.summary, this.summary)
                        && Objects.equals(other.line1, this.line1)
                        && Objects.equals(other.line2, this.line2)
                        && other.icon == this.icon
                        && other.state == this.state
                        && other.flags == this.flags;
        if (Flags.modesApi()) {
            return finalEquals && other.source == this.source;
        }
        return finalEquals;
    }

    public int hashCode() {
        if (Flags.modesApi()) {
            return Objects.hash(
                    this.id,
                    this.summary,
                    this.line1,
                    this.line2,
                    Integer.valueOf(this.icon),
                    Integer.valueOf(this.state),
                    Integer.valueOf(this.source),
                    Integer.valueOf(this.flags));
        }
        return Objects.hash(
                this.id,
                this.summary,
                this.line1,
                this.line2,
                Integer.valueOf(this.icon),
                Integer.valueOf(this.state),
                Integer.valueOf(this.flags));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Condition copy() {
        Parcel parcel = Parcel.obtain();
        try {
            writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            return new Condition(parcel);
        } finally {
            parcel.recycle();
        }
    }

    public static Uri.Builder newId(Context context) {
        return new Uri.Builder().scheme("condition").authority(context.getPackageName());
    }

    public static boolean isValidId(Uri id, String pkg) {
        return id != null && "condition".equals(id.getScheme()) && pkg.equals(id.getAuthority());
    }

    private static String getTrimmedString(String input) {
        if (input != null && input.length() > 1000) {
            return input.substring(0, 1000);
        }
        return input;
    }

    private static Uri getTrimmedUri(Uri input) {
        if (input != null && input.toString().length() > 1000) {
            return Uri.parse(getTrimmedString(input.toString()));
        }
        return input;
    }
}
