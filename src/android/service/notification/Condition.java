package android.service.notification;

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
    public static final Parcelable.Creator<Condition> CREATOR = new Parcelable.Creator<Condition>() { // from class: android.service.notification.Condition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Condition createFromParcel(Parcel parcel) {
            return new Condition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Condition[] newArray(int i) {
            return new Condition[i];
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
    public @interface Source {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    private static boolean isValidState(int i) {
        return i >= 0 && i <= 3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Condition(Uri uri, String str, int i) {
        this(uri, str, "", "", -1, i, 0, 2);
    }

    public Condition(Uri uri, String str, int i, int i2) {
        this(uri, str, "", "", -1, i, i2, 2);
    }

    public Condition(Uri uri, String str, String str2, String str3, int i, int i2, int i3) {
        this(uri, str, str2, str3, i, i2, 0, i3);
    }

    public Condition(Uri uri, String str, String str2, String str3, int i, int i2, int i3, int i4) {
        if (uri == null) {
            throw new IllegalArgumentException("id is required");
        }
        if (str == null) {
            throw new IllegalArgumentException("summary is required");
        }
        if (!isValidState(i2)) {
            throw new IllegalArgumentException("state is invalid: " + i2);
        }
        this.id = getTrimmedUri(uri);
        this.summary = getTrimmedString(str);
        this.line1 = getTrimmedString(str2);
        this.line2 = getTrimmedString(str3);
        this.icon = i;
        this.state = i2;
        this.source = checkValidSource(i3);
        this.flags = i4;
    }

    public Condition(Parcel parcel) {
        this((Uri) parcel.readParcelable(Condition.class.getClassLoader(), Uri.class), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    public void validate() {
        checkValidSource(this.source);
    }

    private static int checkValidSource(int i) {
        Preconditions.checkArgument(i >= 0 && i <= 3, "Condition source must be one of SOURCE_UNKNOWN, SOURCE_USER_ACTION, SOURCE_SCHEDULE, or SOURCE_CONTEXT");
        return i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.id, 0);
        parcel.writeString(this.summary);
        parcel.writeString(this.line1);
        parcel.writeString(this.line2);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.state);
        parcel.writeInt(this.source);
        parcel.writeInt(this.flags);
    }

    public String toString() {
        return "Condition[state=" + stateToString(this.state) + ",id=" + this.id + ",summary=" + this.summary + ",line1=" + this.line1 + ",line2=" + this.line2 + ",icon=" + this.icon + ",source=" + sourceToString(this.source) + ",flags=" + this.flags + ']';
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.id.toString());
        protoOutputStream.write(1138166333442L, this.summary);
        protoOutputStream.write(1138166333443L, this.line1);
        protoOutputStream.write(1138166333444L, this.line2);
        protoOutputStream.write(1120986464261L, this.icon);
        protoOutputStream.write(1159641169926L, this.state);
        protoOutputStream.write(1120986464263L, this.flags);
        protoOutputStream.end(jStart);
    }

    public static String stateToString(int i) {
        if (i == 0) {
            return "STATE_FALSE";
        }
        if (i == 1) {
            return "STATE_TRUE";
        }
        if (i == 2) {
            return "STATE_UNKNOWN";
        }
        if (i == 3) {
            return "STATE_ERROR";
        }
        throw new IllegalArgumentException("state is invalid: " + i);
    }

    public static String sourceToString(int i) {
        if (i == 0) {
            return "SOURCE_UNKNOWN";
        }
        if (i == 1) {
            return "SOURCE_USER_ACTION";
        }
        if (i == 2) {
            return "SOURCE_SCHEDULE";
        }
        if (i == 3) {
            return "SOURCE_CONTEXT";
        }
        throw new IllegalArgumentException("source is invalid: " + i);
    }

    public static String relevanceToString(int i) {
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 2) != 0;
        if (!z && !z2) {
            return KeyProperties.DIGEST_NONE;
        }
        if (z && z2) {
            return "NOW, ALWAYS";
        }
        return z ? "NOW" : "ALWAYS";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Condition)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Condition condition = (Condition) obj;
        return Objects.equals(condition.id, this.id) && Objects.equals(condition.summary, this.summary) && Objects.equals(condition.line1, this.line1) && Objects.equals(condition.line2, this.line2) && condition.icon == this.icon && condition.state == this.state && condition.flags == this.flags && condition.source == this.source;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.summary, this.line1, this.line2, Integer.valueOf(this.icon), Integer.valueOf(this.state), Integer.valueOf(this.source), Integer.valueOf(this.flags));
    }

    public Condition copy() {
        Parcel parcelObtain = Parcel.obtain();
        try {
            writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            return new Condition(parcelObtain);
        } finally {
            parcelObtain.recycle();
        }
    }

    public static Uri.Builder newId(Context context) {
        return new Uri.Builder().scheme("condition").authority(context.getPackageName());
    }

    public static boolean isValidId(Uri uri, String str) {
        return uri != null && "condition".equals(uri.getScheme()) && str.equals(uri.getAuthority());
    }

    private static String getTrimmedString(String str) {
        return (str == null || str.length() <= 1000) ? str : str.substring(0, 1000);
    }

    private static Uri getTrimmedUri(Uri uri) {
        return (uri == null || uri.toString().length() <= 1000) ? uri : Uri.parse(getTrimmedString(uri.toString()));
    }
}
