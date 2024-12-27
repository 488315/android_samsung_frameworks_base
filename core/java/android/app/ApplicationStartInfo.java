package android.app;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Xml;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.WireTypeMismatchException;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ApplicationStartInfo implements Parcelable {
    public static final Parcelable.Creator<ApplicationStartInfo> CREATOR = new Parcelable.Creator<ApplicationStartInfo>() { // from class: android.app.ApplicationStartInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApplicationStartInfo createFromParcel(Parcel in) {
            return new ApplicationStartInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApplicationStartInfo[] newArray(int size) {
            return new ApplicationStartInfo[size];
        }
    };
    public static final int LAUNCH_MODE_SINGLE_INSTANCE = 2;
    public static final int LAUNCH_MODE_SINGLE_INSTANCE_PER_TASK = 4;
    public static final int LAUNCH_MODE_SINGLE_TASK = 3;
    public static final int LAUNCH_MODE_SINGLE_TOP = 1;
    public static final int LAUNCH_MODE_STANDARD = 0;
    private static final String PROTO_SERIALIZER_ATTRIBUTE_INTENT = "intent";
    private static final String PROTO_SERIALIZER_ATTRIBUTE_KEY = "key";
    private static final String PROTO_SERIALIZER_ATTRIBUTE_TIMESTAMP = "timestamp";
    private static final String PROTO_SERIALIZER_ATTRIBUTE_TIMESTAMPS = "timestamps";
    private static final String PROTO_SERIALIZER_ATTRIBUTE_TS = "ts";
    public static final int STARTUP_STATE_ERROR = 1;
    public static final int STARTUP_STATE_FIRST_FRAME_DRAWN = 2;
    public static final int STARTUP_STATE_STARTED = 0;
    public static final int START_REASON_ALARM = 0;
    public static final int START_REASON_BACKUP = 1;
    public static final int START_REASON_BOOT_COMPLETE = 2;
    public static final int START_REASON_BROADCAST = 3;
    public static final int START_REASON_CONTENT_PROVIDER = 4;
    public static final int START_REASON_JOB = 5;
    public static final int START_REASON_LAUNCHER = 6;
    public static final int START_REASON_LAUNCHER_RECENTS = 7;
    public static final int START_REASON_OTHER = 8;
    public static final int START_REASON_PUSH = 9;
    public static final int START_REASON_SERVICE = 10;
    public static final int START_REASON_START_ACTIVITY = 11;
    public static final int START_TIMESTAMP_APPLICATION_ONCREATE = 2;
    public static final int START_TIMESTAMP_BIND_APPLICATION = 3;
    public static final int START_TIMESTAMP_FIRST_FRAME = 4;
    public static final int START_TIMESTAMP_FORK = 1;
    public static final int START_TIMESTAMP_FULLY_DRAWN = 5;
    public static final int START_TIMESTAMP_INITIAL_RENDERTHREAD_FRAME = 6;
    public static final int START_TIMESTAMP_LAUNCH = 0;
    public static final int START_TIMESTAMP_RESERVED_RANGE_DEVELOPER = 30;
    public static final int START_TIMESTAMP_RESERVED_RANGE_DEVELOPER_START = 21;
    public static final int START_TIMESTAMP_RESERVED_RANGE_SYSTEM = 20;
    public static final int START_TIMESTAMP_SURFACEFLINGER_COMPOSITION_COMPLETE = 7;
    public static final int START_TYPE_COLD = 1;
    public static final int START_TYPE_HOT = 3;
    public static final int START_TYPE_UNSET = 0;
    public static final int START_TYPE_WARM = 2;
    private int mDefiningUid;
    private int mLaunchMode;
    private String mPackageName;
    private int mPackageUid;
    private int mPid;
    private String mProcessName;
    private int mRealUid;
    private int mReason;
    private Intent mStartIntent;
    private int mStartType;
    private int mStartupState;
    private ArrayMap<Integer, Long> mStartupTimestampsNs;
    private boolean mWasForceStopped;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartupState {
    }

    public void setStartupState(int startupState) {
        this.mStartupState = startupState;
    }

    public void setPid(int pid) {
        this.mPid = pid;
    }

    public void setRealUid(int uid) {
        this.mRealUid = uid;
    }

    public void setPackageUid(int uid) {
        this.mPackageUid = uid;
    }

    public void setDefiningUid(int uid) {
        this.mDefiningUid = uid;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = intern(packageName);
    }

    public void setProcessName(String processName) {
        this.mProcessName = intern(processName);
    }

    public void setReason(int reason) {
        this.mReason = reason;
    }

    public void addStartupTimestamp(int key, long timestampNs) {
        if (key < 0 || key > 30) {
            return;
        }
        if (this.mStartupTimestampsNs == null) {
            this.mStartupTimestampsNs = new ArrayMap<>();
        }
        this.mStartupTimestampsNs.put(Integer.valueOf(key), Long.valueOf(timestampNs));
    }

    public void setStartType(int startType) {
        this.mStartType = startType;
    }

    public void setIntent(Intent startIntent) {
        if (startIntent != null) {
            if (startIntent.canStripForHistory()) {
                this.mStartIntent = startIntent.maybeStripForHistory();
            } else if (startIntent.getExtras() != null) {
                this.mStartIntent = startIntent.cloneFilter();
            } else {
                this.mStartIntent = new Intent(startIntent);
            }
            if (this.mStartIntent.getOriginalIntent() != null) {
                this.mStartIntent.setOriginalIntent(null);
            }
        }
    }

    public void setLaunchMode(int launchMode) {
        this.mLaunchMode = launchMode;
    }

    public void setForceStopped(boolean wasForceStopped) {
        this.mWasForceStopped = wasForceStopped;
    }

    public int getStartupState() {
        return this.mStartupState;
    }

    public int getPid() {
        return this.mPid;
    }

    public int getRealUid() {
        return this.mRealUid;
    }

    public int getPackageUid() {
        return this.mPackageUid;
    }

    public int getDefiningUid() {
        return this.mDefiningUid;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getProcessName() {
        return this.mProcessName;
    }

    public int getReason() {
        return this.mReason;
    }

    public Map<Integer, Long> getStartupTimestamps() {
        if (this.mStartupTimestampsNs == null) {
            this.mStartupTimestampsNs = new ArrayMap<>();
        }
        return this.mStartupTimestampsNs;
    }

    public int getStartType() {
        return this.mStartType;
    }

    public Intent getIntent() {
        return this.mStartIntent;
    }

    public int getLaunchMode() {
        return this.mLaunchMode;
    }

    public boolean wasForceStopped() {
        return this.mWasForceStopped;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mStartupState);
        dest.writeInt(this.mPid);
        dest.writeInt(this.mRealUid);
        dest.writeInt(this.mPackageUid);
        dest.writeInt(this.mDefiningUid);
        dest.writeString(this.mPackageName);
        dest.writeString(this.mProcessName);
        dest.writeInt(this.mReason);
        dest.writeInt(this.mStartupTimestampsNs == null ? 0 : this.mStartupTimestampsNs.size());
        if (this.mStartupTimestampsNs != null) {
            for (int i = 0; i < this.mStartupTimestampsNs.size(); i++) {
                dest.writeInt(this.mStartupTimestampsNs.keyAt(i).intValue());
                dest.writeLong(this.mStartupTimestampsNs.valueAt(i).longValue());
            }
        }
        int i2 = this.mStartType;
        dest.writeInt(i2);
        dest.writeParcelable(this.mStartIntent, flags);
        dest.writeInt(this.mLaunchMode);
        dest.writeBoolean(this.mWasForceStopped);
    }

    public ApplicationStartInfo() {
    }

    public ApplicationStartInfo(ApplicationStartInfo other) {
        this.mStartupState = other.mStartupState;
        this.mPid = other.mPid;
        this.mRealUid = other.mRealUid;
        this.mPackageUid = other.mPackageUid;
        this.mDefiningUid = other.mDefiningUid;
        this.mPackageName = other.mPackageName;
        this.mProcessName = other.mProcessName;
        this.mReason = other.mReason;
        this.mStartupTimestampsNs = other.mStartupTimestampsNs;
        this.mStartType = other.mStartType;
        this.mStartIntent = other.mStartIntent;
        this.mLaunchMode = other.mLaunchMode;
        this.mWasForceStopped = other.mWasForceStopped;
    }

    private ApplicationStartInfo(Parcel in) {
        this.mStartupState = in.readInt();
        this.mPid = in.readInt();
        this.mRealUid = in.readInt();
        this.mPackageUid = in.readInt();
        this.mDefiningUid = in.readInt();
        this.mPackageName = intern(in.readString());
        this.mProcessName = intern(in.readString());
        this.mReason = in.readInt();
        int starupTimestampCount = in.readInt();
        for (int i = 0; i < starupTimestampCount; i++) {
            int key = in.readInt();
            long val = in.readLong();
            addStartupTimestamp(key, val);
        }
        int i2 = in.readInt();
        this.mStartType = i2;
        this.mStartIntent = (Intent) in.readParcelable(Intent.class.getClassLoader(), Intent.class);
        this.mLaunchMode = in.readInt();
        this.mWasForceStopped = in.readBoolean();
    }

    private static String intern(String source) {
        if (source != null) {
            return source.intern();
        }
        return null;
    }

    public void writeToProto(ProtoOutputStream proto, long fieldId) throws IOException {
        long token = proto.start(fieldId);
        proto.write(1120986464257L, this.mPid);
        proto.write(1120986464258L, this.mRealUid);
        proto.write(1120986464259L, this.mPackageUid);
        proto.write(1120986464260L, this.mDefiningUid);
        proto.write(1138166333445L, this.mProcessName);
        proto.write(1159641169926L, this.mStartupState);
        proto.write(1159641169927L, this.mReason);
        if (this.mStartupTimestampsNs != null && this.mStartupTimestampsNs.size() > 0) {
            ByteArrayOutputStream timestampsBytes = new ByteArrayOutputStream();
            ObjectOutputStream timestampsOut = new ObjectOutputStream(timestampsBytes);
            TypedXmlSerializer serializer = Xml.resolveSerializer(timestampsOut);
            serializer.startDocument(null, true);
            serializer.startTag(null, "timestamps");
            for (int i = 0; i < this.mStartupTimestampsNs.size(); i++) {
                serializer.startTag(null, "timestamp");
                serializer.attributeInt(null, "key", this.mStartupTimestampsNs.keyAt(i).intValue());
                serializer.attributeLong(null, PROTO_SERIALIZER_ATTRIBUTE_TS, this.mStartupTimestampsNs.valueAt(i).longValue());
                serializer.endTag(null, "timestamp");
            }
            serializer.endTag(null, "timestamps");
            serializer.endDocument();
            proto.write(ApplicationStartInfoProto.STARTUP_TIMESTAMPS, timestampsBytes.toByteArray());
            timestampsOut.close();
        }
        proto.write(1159641169929L, this.mStartType);
        if (this.mStartIntent != null) {
            ByteArrayOutputStream intentBytes = new ByteArrayOutputStream();
            ObjectOutputStream intentOut = new ObjectOutputStream(intentBytes);
            TypedXmlSerializer serializer2 = Xml.resolveSerializer(intentOut);
            serializer2.startDocument(null, true);
            serializer2.startTag(null, "intent");
            this.mStartIntent.saveToXml(serializer2);
            serializer2.endTag(null, "intent");
            serializer2.endDocument();
            proto.write(ApplicationStartInfoProto.START_INTENT, intentBytes.toByteArray());
            intentOut.close();
        }
        proto.write(1159641169931L, this.mLaunchMode);
        proto.write(1133871366156L, this.mWasForceStopped);
        proto.end(token);
    }

    public void readFromProto(ProtoInputStream proto, long fieldId) throws IOException, WireTypeMismatchException, ClassNotFoundException {
        long token = proto.start(fieldId);
        while (proto.nextField() != -1) {
            switch (proto.getFieldNumber()) {
                case 1:
                    this.mPid = proto.readInt(1120986464257L);
                    break;
                case 2:
                    this.mRealUid = proto.readInt(1120986464258L);
                    break;
                case 3:
                    this.mPackageUid = proto.readInt(1120986464259L);
                    break;
                case 4:
                    this.mDefiningUid = proto.readInt(1120986464260L);
                    break;
                case 5:
                    this.mProcessName = intern(proto.readString(1138166333445L));
                    break;
                case 6:
                    this.mStartupState = proto.readInt(1159641169926L);
                    break;
                case 7:
                    this.mReason = proto.readInt(1159641169927L);
                    break;
                case 8:
                    ByteArrayInputStream timestampsBytes = new ByteArrayInputStream(proto.readBytes(ApplicationStartInfoProto.STARTUP_TIMESTAMPS));
                    ObjectInputStream timestampsIn = new ObjectInputStream(timestampsBytes);
                    this.mStartupTimestampsNs = new ArrayMap<>();
                    try {
                        TypedXmlPullParser parser = Xml.resolvePullParser(timestampsIn);
                        XmlUtils.beginDocument(parser, "timestamps");
                        int depth = parser.getDepth();
                        while (XmlUtils.nextElementWithin(parser, depth)) {
                            if ("timestamp".equals(parser.getName())) {
                                int key = parser.getAttributeInt(null, "key");
                                long ts = parser.getAttributeLong(null, PROTO_SERIALIZER_ATTRIBUTE_TS);
                                this.mStartupTimestampsNs.put(Integer.valueOf(key), Long.valueOf(ts));
                            }
                        }
                    } catch (XmlPullParserException e) {
                    }
                    timestampsIn.close();
                    break;
                case 9:
                    this.mStartType = proto.readInt(1159641169929L);
                    break;
                case 10:
                    ByteArrayInputStream intentBytes = new ByteArrayInputStream(proto.readBytes(ApplicationStartInfoProto.START_INTENT));
                    ObjectInputStream intentIn = new ObjectInputStream(intentBytes);
                    try {
                        TypedXmlPullParser parser2 = Xml.resolvePullParser(intentIn);
                        XmlUtils.beginDocument(parser2, "intent");
                        this.mStartIntent = Intent.restoreFromXml(parser2);
                    } catch (XmlPullParserException e2) {
                    }
                    intentIn.close();
                    break;
                case 11:
                    this.mLaunchMode = proto.readInt(1159641169931L);
                    break;
                case 12:
                    this.mWasForceStopped = proto.readBoolean(1133871366156L);
                    break;
            }
        }
        proto.end(token);
    }

    public void dump(PrintWriter pw, String prefix, String seqSuffix, SimpleDateFormat sdf) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append("ApplicationStartInfo ").append(seqSuffix).append(ShortcutConstants.SERVICES_SEPARATOR).append('\n').append(" pid=").append(this.mPid).append(" realUid=").append(this.mRealUid).append(" packageUid=").append(this.mPackageUid).append(" definingUid=").append(this.mDefiningUid).append(" user=").append(UserHandle.getUserId(this.mPackageUid)).append('\n').append(" package=").append(this.mPackageName).append(" process=").append(this.mProcessName).append(" startupState=").append(this.mStartupState).append(" reason=").append(reasonToString(this.mReason)).append(" startType=").append(startTypeToString(this.mStartType)).append(" launchMode=").append(this.mLaunchMode).append(" wasForceStopped=").append(this.mWasForceStopped).append('\n');
        if (this.mStartIntent != null) {
            sb.append(" intent=").append(this.mStartIntent.toString()).append('\n');
        }
        if (this.mStartupTimestampsNs != null && this.mStartupTimestampsNs.size() > 0) {
            sb.append(" timestamps: ");
            for (int i = 0; i < this.mStartupTimestampsNs.size(); i++) {
                sb.append(this.mStartupTimestampsNs.keyAt(i)).append("=").append(this.mStartupTimestampsNs.valueAt(i)).append(" ");
            }
            sb.append('\n');
        }
        pw.print(sb.toString());
    }

    private static String reasonToString(int reason) {
        switch (reason) {
            case 0:
                return "ALARM";
            case 1:
                return "BACKUP";
            case 2:
                return "BOOT COMPLETE";
            case 3:
                return "BROADCAST";
            case 4:
                return "CONTENT PROVIDER";
            case 5:
                return "JOB";
            case 6:
                return "LAUNCHER";
            case 7:
                return "LAUNCHER RECENTS";
            case 8:
                return "OTHER";
            case 9:
                return "PUSH";
            case 10:
                return "SERVICE";
            case 11:
                return "START ACTIVITY";
            default:
                return "";
        }
    }

    private static String startTypeToString(int startType) {
        switch (startType) {
            case 0:
                return "UNSET";
            case 1:
                return "COLD";
            case 2:
                return "WARM";
            case 3:
                return "HOT";
            default:
                return "";
        }
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof ApplicationStartInfo)) {
            return false;
        }
        ApplicationStartInfo o = (ApplicationStartInfo) other;
        return this.mPid == o.mPid && this.mRealUid == o.mRealUid && this.mPackageUid == o.mPackageUid && this.mDefiningUid == o.mDefiningUid && this.mReason == o.mReason && this.mStartupState == o.mStartupState && this.mStartType == o.mStartType && this.mLaunchMode == o.mLaunchMode && TextUtils.equals(this.mProcessName, o.mProcessName) && timestampsEquals(o) && this.mWasForceStopped == o.mWasForceStopped;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPid), Integer.valueOf(this.mRealUid), Integer.valueOf(this.mPackageUid), Integer.valueOf(this.mDefiningUid), Integer.valueOf(this.mReason), Integer.valueOf(this.mStartupState), Integer.valueOf(this.mStartType), Integer.valueOf(this.mLaunchMode), this.mProcessName, this.mStartupTimestampsNs);
    }

    private boolean timestampsEquals(ApplicationStartInfo other) {
        if (this.mStartupTimestampsNs == null && other.mStartupTimestampsNs == null) {
            return true;
        }
        if (this.mStartupTimestampsNs == null || other.mStartupTimestampsNs == null) {
            return false;
        }
        return this.mStartupTimestampsNs.equals(other.mStartupTimestampsNs);
    }
}
