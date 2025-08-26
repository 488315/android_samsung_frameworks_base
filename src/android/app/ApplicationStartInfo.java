package android.app;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.system.ErrnoException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Xml;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.WireTypeMismatchException;
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
        public ApplicationStartInfo createFromParcel(Parcel parcel) {
            return new ApplicationStartInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApplicationStartInfo[] newArray(int i) {
            return new ApplicationStartInfo[i];
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
    public static final int START_COMPONENT_ACTIVITY = 1;
    public static final int START_COMPONENT_BROADCAST = 2;
    public static final int START_COMPONENT_CONTENT_PROVIDER = 3;
    public static final int START_COMPONENT_OTHER = 5;
    public static final int START_COMPONENT_SERVICE = 4;
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
    private long mMonotonicCreationTimeMs;
    private String mPackageName;
    private int mPackageUid;
    private int mPid;
    private String mProcessName;
    private int mRealUid;
    private int mReason;
    private int mStartComponent;
    private Intent mStartIntent;
    private int mStartType;
    private int mStartupState;
    private ArrayMap<Integer, Long> mStartupTimestampsNs;
    private boolean mWasForceStopped;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StartComponent {
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setStartupState(int i) {
        this.mStartupState = i;
    }

    public void setPid(int i) {
        this.mPid = i;
    }

    public void setRealUid(int i) {
        this.mRealUid = i;
    }

    public void setPackageUid(int i) {
        this.mPackageUid = i;
    }

    public void setDefiningUid(int i) {
        this.mDefiningUid = i;
    }

    public void setPackageName(String str) {
        this.mPackageName = intern(str);
    }

    public void setProcessName(String str) {
        this.mProcessName = intern(str);
    }

    public void setReason(int i) {
        this.mReason = i;
    }

    public void addStartupTimestamp(int i, long j) {
        if (i < 0 || i > 30) {
            return;
        }
        if (this.mStartupTimestampsNs == null) {
            this.mStartupTimestampsNs = new ArrayMap<>();
        }
        this.mStartupTimestampsNs.put(Integer.valueOf(i), Long.valueOf(j));
    }

    public void setStartType(int i) {
        this.mStartType = i;
    }

    public void setIntent(Intent intent) {
        if (intent != null) {
            if (intent.canStripForHistory()) {
                this.mStartIntent = intent.maybeStripForHistory();
            } else if (intent.getExtras() != null) {
                this.mStartIntent = intent.cloneFilter();
            } else {
                this.mStartIntent = new Intent(intent);
            }
            if (this.mStartIntent.getOriginalIntent() != null) {
                this.mStartIntent.setOriginalIntent(null);
            }
        }
    }

    public void setLaunchMode(int i) {
        this.mLaunchMode = i;
    }

    public void setForceStopped(boolean z) {
        this.mWasForceStopped = z;
    }

    public void setStartComponent(int i) {
        this.mStartComponent = i;
    }

    public int getStartupState() {
        return this.mStartupState;
    }

    public long getMonotonicCreationTimeMs() {
        return this.mMonotonicCreationTimeMs;
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

    public int getStartComponent() {
        return this.mStartComponent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStartupState);
        parcel.writeInt(this.mPid);
        parcel.writeInt(this.mRealUid);
        parcel.writeInt(this.mPackageUid);
        parcel.writeInt(this.mDefiningUid);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mProcessName);
        parcel.writeInt(this.mReason);
        ArrayMap<Integer, Long> arrayMap = this.mStartupTimestampsNs;
        parcel.writeInt(arrayMap == null ? 0 : arrayMap.size());
        if (this.mStartupTimestampsNs != null) {
            for (int i2 = 0; i2 < this.mStartupTimestampsNs.size(); i2++) {
                parcel.writeInt(this.mStartupTimestampsNs.keyAt(i2).intValue());
                parcel.writeLong(this.mStartupTimestampsNs.valueAt(i2).longValue());
            }
        }
        parcel.writeInt(this.mStartType);
        parcel.writeParcelable(this.mStartIntent, i);
        parcel.writeInt(this.mLaunchMode);
        parcel.writeBoolean(this.mWasForceStopped);
        parcel.writeLong(this.mMonotonicCreationTimeMs);
        parcel.writeInt(this.mStartComponent);
    }

    public ApplicationStartInfo(long j) {
        this.mMonotonicCreationTimeMs = j;
    }

    public ApplicationStartInfo(ApplicationStartInfo applicationStartInfo) {
        this.mStartupState = applicationStartInfo.mStartupState;
        this.mPid = applicationStartInfo.mPid;
        this.mRealUid = applicationStartInfo.mRealUid;
        this.mPackageUid = applicationStartInfo.mPackageUid;
        this.mDefiningUid = applicationStartInfo.mDefiningUid;
        this.mPackageName = applicationStartInfo.mPackageName;
        this.mProcessName = applicationStartInfo.mProcessName;
        this.mReason = applicationStartInfo.mReason;
        this.mStartupTimestampsNs = applicationStartInfo.mStartupTimestampsNs;
        this.mStartType = applicationStartInfo.mStartType;
        this.mStartIntent = applicationStartInfo.mStartIntent;
        this.mLaunchMode = applicationStartInfo.mLaunchMode;
        this.mWasForceStopped = applicationStartInfo.mWasForceStopped;
        this.mMonotonicCreationTimeMs = applicationStartInfo.mMonotonicCreationTimeMs;
        this.mStartComponent = applicationStartInfo.mStartComponent;
    }

    public ApplicationStartInfo(Parcel parcel) {
        this.mStartupState = parcel.readInt();
        this.mPid = parcel.readInt();
        this.mRealUid = parcel.readInt();
        this.mPackageUid = parcel.readInt();
        this.mDefiningUid = parcel.readInt();
        this.mPackageName = intern(parcel.readString());
        this.mProcessName = intern(parcel.readString());
        this.mReason = parcel.readInt();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            addStartupTimestamp(parcel.readInt(), parcel.readLong());
        }
        this.mStartType = parcel.readInt();
        this.mStartIntent = (Intent) parcel.readParcelable(Intent.class.getClassLoader(), Intent.class);
        this.mLaunchMode = parcel.readInt();
        this.mWasForceStopped = parcel.readBoolean();
        this.mMonotonicCreationTimeMs = parcel.readLong();
        this.mStartComponent = parcel.readInt();
    }

    private static String intern(String str) {
        if (str != null) {
            return str.intern();
        }
        return null;
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long j, ByteArrayOutputStream byteArrayOutputStream, ObjectOutputStream objectOutputStream, TypedXmlSerializer typedXmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mPid);
        protoOutputStream.write(1120986464258L, this.mRealUid);
        protoOutputStream.write(1120986464259L, this.mPackageUid);
        protoOutputStream.write(1120986464260L, this.mDefiningUid);
        protoOutputStream.write(1138166333445L, this.mProcessName);
        protoOutputStream.write(1159641169926L, this.mStartupState);
        protoOutputStream.write(1159641169927L, this.mReason);
        ArrayMap<Integer, Long> arrayMap = this.mStartupTimestampsNs;
        if (arrayMap != null && arrayMap.size() > 0) {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream2);
            TypedXmlSerializer typedXmlSerializerResolveSerializer = Xml.resolveSerializer(objectOutputStream2);
            typedXmlSerializerResolveSerializer.startDocument(null, true);
            typedXmlSerializerResolveSerializer.startTag(null, "timestamps");
            for (int i = 0; i < this.mStartupTimestampsNs.size(); i++) {
                typedXmlSerializerResolveSerializer.startTag(null, "timestamp");
                typedXmlSerializerResolveSerializer.attributeInt(null, "key", this.mStartupTimestampsNs.keyAt(i).intValue());
                typedXmlSerializerResolveSerializer.attributeLong(null, PROTO_SERIALIZER_ATTRIBUTE_TS, this.mStartupTimestampsNs.valueAt(i).longValue());
                typedXmlSerializerResolveSerializer.endTag(null, "timestamp");
            }
            typedXmlSerializerResolveSerializer.endTag(null, "timestamps");
            typedXmlSerializerResolveSerializer.endDocument();
            protoOutputStream.write(1151051235336L, byteArrayOutputStream2.toByteArray());
            objectOutputStream2.close();
        }
        protoOutputStream.write(1159641169929L, this.mStartType);
        if (this.mStartIntent != null) {
            ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream3 = new ObjectOutputStream(byteArrayOutputStream3);
            TypedXmlSerializer typedXmlSerializerResolveSerializer2 = Xml.resolveSerializer(objectOutputStream3);
            typedXmlSerializerResolveSerializer2.startDocument(null, true);
            typedXmlSerializerResolveSerializer2.startTag(null, "intent");
            this.mStartIntent.saveToXml(typedXmlSerializerResolveSerializer2);
            typedXmlSerializerResolveSerializer2.endTag(null, "intent");
            typedXmlSerializerResolveSerializer2.endDocument();
            protoOutputStream.write(ApplicationStartInfoProto.START_INTENT, byteArrayOutputStream3.toByteArray());
            objectOutputStream3.close();
        }
        protoOutputStream.write(1159641169931L, this.mLaunchMode);
        protoOutputStream.write(1133871366156L, this.mWasForceStopped);
        protoOutputStream.write(1112396529677L, this.mMonotonicCreationTimeMs);
        protoOutputStream.write(1120986464270L, this.mStartComponent);
        protoOutputStream.end(jStart);
    }

    public void readFromProto(ProtoInputStream protoInputStream, long j, ByteArrayInputStream byteArrayInputStream, ObjectInputStream objectInputStream, TypedXmlPullParser typedXmlPullParser) throws WireTypeMismatchException, IOException, ClassNotFoundException, ErrnoException {
        long jStart = protoInputStream.start(j);
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    this.mPid = protoInputStream.readInt(1120986464257L);
                    break;
                case 2:
                    this.mRealUid = protoInputStream.readInt(1120986464258L);
                    break;
                case 3:
                    this.mPackageUid = protoInputStream.readInt(1120986464259L);
                    break;
                case 4:
                    this.mDefiningUid = protoInputStream.readInt(1120986464260L);
                    break;
                case 5:
                    this.mProcessName = intern(protoInputStream.readString(1138166333445L));
                    break;
                case 6:
                    this.mStartupState = protoInputStream.readInt(1159641169926L);
                    break;
                case 7:
                    this.mReason = protoInputStream.readInt(1159641169927L);
                    break;
                case 8:
                    ObjectInputStream objectInputStream2 = new ObjectInputStream(new ByteArrayInputStream(protoInputStream.readBytes(1151051235336L)));
                    this.mStartupTimestampsNs = new ArrayMap<>();
                    try {
                        TypedXmlPullParser typedXmlPullParserResolvePullParser = Xml.resolvePullParser(objectInputStream2);
                        XmlUtils.beginDocument(typedXmlPullParserResolvePullParser, "timestamps");
                        int depth = typedXmlPullParserResolvePullParser.getDepth();
                        while (XmlUtils.nextElementWithin(typedXmlPullParserResolvePullParser, depth)) {
                            if ("timestamp".equals(typedXmlPullParserResolvePullParser.getName())) {
                                this.mStartupTimestampsNs.put(Integer.valueOf(typedXmlPullParserResolvePullParser.getAttributeInt(null, "key")), Long.valueOf(typedXmlPullParserResolvePullParser.getAttributeLong(null, PROTO_SERIALIZER_ATTRIBUTE_TS)));
                            }
                        }
                    } catch (XmlPullParserException unused) {
                    }
                    objectInputStream2.close();
                    break;
                case 9:
                    this.mStartType = protoInputStream.readInt(1159641169929L);
                    break;
                case 10:
                    ObjectInputStream objectInputStream3 = new ObjectInputStream(new ByteArrayInputStream(protoInputStream.readBytes(ApplicationStartInfoProto.START_INTENT)));
                    try {
                        TypedXmlPullParser typedXmlPullParserResolvePullParser2 = Xml.resolvePullParser(objectInputStream3);
                        XmlUtils.beginDocument(typedXmlPullParserResolvePullParser2, "intent");
                        this.mStartIntent = Intent.restoreFromXml(typedXmlPullParserResolvePullParser2);
                    } catch (XmlPullParserException unused2) {
                    }
                    objectInputStream3.close();
                    break;
                case 11:
                    this.mLaunchMode = protoInputStream.readInt(1159641169931L);
                    break;
                case 12:
                    this.mWasForceStopped = protoInputStream.readBoolean(1133871366156L);
                    break;
                case 13:
                    this.mMonotonicCreationTimeMs = protoInputStream.readLong(1112396529677L);
                    break;
                case 14:
                    this.mStartComponent = protoInputStream.readInt(1120986464270L);
                    break;
            }
        }
        protoInputStream.end(jStart);
    }

    public void dump(PrintWriter printWriter, String str, String str2, SimpleDateFormat simpleDateFormat) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("ApplicationStartInfo ");
        sb.append(str2);
        sb.append(":\n monotonicCreationTimeMs=");
        sb.append(this.mMonotonicCreationTimeMs);
        sb.append("\n pid=");
        sb.append(this.mPid);
        sb.append(" realUid=");
        sb.append(this.mRealUid);
        sb.append(" packageUid=");
        sb.append(this.mPackageUid);
        sb.append(" definingUid=");
        sb.append(this.mDefiningUid);
        sb.append(" user=");
        sb.append(UserHandle.getUserId(this.mPackageUid));
        sb.append("\n package=");
        sb.append(this.mPackageName);
        sb.append(" process=");
        sb.append(this.mProcessName);
        sb.append(" startupState=");
        sb.append(this.mStartupState);
        sb.append(" reason=");
        sb.append(reasonToString(this.mReason));
        sb.append(" startType=");
        sb.append(startTypeToString(this.mStartType));
        sb.append(" launchMode=");
        sb.append(this.mLaunchMode);
        sb.append(" wasForceStopped=");
        sb.append(this.mWasForceStopped);
        if (Flags.appStartInfoComponent()) {
            sb.append(" startComponent=");
            sb.append(startComponentToString(this.mStartComponent));
        }
        sb.append('\n');
        if (this.mStartIntent != null) {
            sb.append(" intent=");
            sb.append(this.mStartIntent.toString());
            sb.append('\n');
        }
        ArrayMap<Integer, Long> arrayMap = this.mStartupTimestampsNs;
        if (arrayMap != null && arrayMap.size() > 0) {
            sb.append(" timestamps: ");
            for (int i = 0; i < this.mStartupTimestampsNs.size(); i++) {
                sb.append(this.mStartupTimestampsNs.keyAt(i));
                sb.append("=");
                sb.append(this.mStartupTimestampsNs.valueAt(i));
                sb.append(" ");
            }
            sb.append('\n');
        }
        printWriter.print(sb.toString());
    }

    private static String reasonToString(int i) {
        switch (i) {
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

    private static String startTypeToString(int i) {
        if (i == 0) {
            return "UNSET";
        }
        if (i == 1) {
            return "COLD";
        }
        if (i == 2) {
            return "WARM";
        }
        if (i == 3) {
            return "HOT";
        }
        return "";
    }

    private static String startComponentToString(int i) {
        if (i == 1) {
            return "ACTIVITY";
        }
        if (i == 2) {
            return "BROADCAST";
        }
        if (i == 3) {
            return "CONTENT PROVIDER";
        }
        if (i == 4) {
            return "SERVICE";
        }
        if (i == 5) {
            return "OTHER";
        }
        return "";
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean equals(Object obj) {
        boolean zFilterEquals;
        if (obj != null && (obj instanceof ApplicationStartInfo)) {
            ApplicationStartInfo applicationStartInfo = (ApplicationStartInfo) obj;
            if (com.android.internal.hidden_from_bootclasspath.android.content.flags.Flags.intentSaveToXmlPackage()) {
                Intent intent = this.mStartIntent;
                if (intent == null) {
                    if (applicationStartInfo.mStartIntent != null) {
                        zFilterEquals = false;
                    }
                } else {
                    zFilterEquals = intent.filterEquals(applicationStartInfo.mStartIntent);
                }
                if (this.mPid != applicationStartInfo.mPid) {
                }
            } else {
                zFilterEquals = true;
                if (this.mPid != applicationStartInfo.mPid && this.mRealUid == applicationStartInfo.mRealUid && this.mPackageUid == applicationStartInfo.mPackageUid && this.mDefiningUid == applicationStartInfo.mDefiningUid && this.mReason == applicationStartInfo.mReason && this.mStartupState == applicationStartInfo.mStartupState && this.mStartType == applicationStartInfo.mStartType && this.mLaunchMode == applicationStartInfo.mLaunchMode && TextUtils.equals(this.mPackageName, applicationStartInfo.mPackageName) && TextUtils.equals(this.mProcessName, applicationStartInfo.mProcessName) && timestampsEquals(applicationStartInfo) && this.mWasForceStopped == applicationStartInfo.mWasForceStopped && this.mMonotonicCreationTimeMs == applicationStartInfo.mMonotonicCreationTimeMs && this.mStartComponent == applicationStartInfo.mStartComponent && zFilterEquals) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPid), Integer.valueOf(this.mRealUid), Integer.valueOf(this.mPackageUid), Integer.valueOf(this.mDefiningUid), Integer.valueOf(this.mReason), Integer.valueOf(this.mStartupState), Integer.valueOf(this.mStartType), Integer.valueOf(this.mLaunchMode), this.mPackageName, this.mProcessName, this.mStartupTimestampsNs, Long.valueOf(this.mMonotonicCreationTimeMs), Integer.valueOf(this.mStartComponent), com.android.internal.hidden_from_bootclasspath.android.content.flags.Flags.intentSaveToXmlPackage() ? this.mStartIntent : null);
    }

    private boolean timestampsEquals(ApplicationStartInfo applicationStartInfo) {
        ArrayMap<Integer, Long> arrayMap;
        ArrayMap<Integer, Long> arrayMap2 = this.mStartupTimestampsNs;
        if (arrayMap2 == null && applicationStartInfo.mStartupTimestampsNs == null) {
            return true;
        }
        if (arrayMap2 == null || (arrayMap = applicationStartInfo.mStartupTimestampsNs) == null) {
            return false;
        }
        return arrayMap2.equals(arrayMap);
    }
}
