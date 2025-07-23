package android.app;

import android.app.IAppTraceRetriever;
import android.app.IParcelFileDescriptorRetriever;
import android.icu.text.SimpleDateFormat;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.DebugUtils;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.WireTypeMismatchException;
import com.android.internal.util.ArrayUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

/* loaded from: classes.dex */
public final class ApplicationExitInfo implements Parcelable {
    public static final Parcelable.Creator<ApplicationExitInfo> CREATOR = new Parcelable.Creator<ApplicationExitInfo>() { // from class: android.app.ApplicationExitInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApplicationExitInfo createFromParcel(Parcel parcel) {
            return new ApplicationExitInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApplicationExitInfo[] newArray(int i) {
            return new ApplicationExitInfo[i];
        }
    };
    public static final int REASON_ANR = 6;
    public static final int REASON_CRASH = 4;
    public static final int REASON_CRASH_NATIVE = 5;
    public static final int REASON_DEPENDENCY_DIED = 12;
    public static final int REASON_EXCESSIVE_RESOURCE_USAGE = 9;
    public static final int REASON_EXIT_SELF = 1;
    public static final int REASON_FREEZER = 14;
    public static final int REASON_GENAI = 17;
    public static final int REASON_INITIALIZATION_FAILURE = 7;
    public static final int REASON_LOW_MEMORY = 3;
    public static final int REASON_OTHER = 13;
    public static final int REASON_PACKAGE_STATE_CHANGE = 15;
    public static final int REASON_PACKAGE_UPDATED = 16;
    public static final int REASON_PERMISSION_CHANGE = 8;
    public static final int REASON_SIGNALED = 2;
    public static final int REASON_UNKNOWN = 0;
    public static final int REASON_USER_REQUESTED = 10;
    public static final int REASON_USER_STOPPED = 11;
    public static final int SUBREASON_CACHED_IDLE_FORCED_APP_STANDBY = 18;
    public static final int SUBREASON_EXCESSIVE_BINDER_OBJECTS = 29;
    public static final int SUBREASON_EXCESSIVE_CPU = 7;
    public static final int SUBREASON_EXCESSIVE_OUTGOING_BROADCASTS_WHILE_CACHED = 32;
    public static final int SUBREASON_FORCE_STOP = 21;
    public static final int SUBREASON_FREEZER_BINDER_ASYNC_FULL = 31;
    public static final int SUBREASON_FREEZER_BINDER_IOCTL = 19;
    public static final int SUBREASON_FREEZER_BINDER_TRANSACTION = 20;
    public static final int SUBREASON_GENAI_UNLOAD_POLICY = 1003;
    public static final int SUBREASON_IMPERCEPTIBLE = 15;
    public static final int SUBREASON_INVALID_START = 13;
    public static final int SUBREASON_INVALID_STATE = 14;
    public static final int SUBREASON_ISOLATED_NOT_NEEDED = 17;
    public static final int SUBREASON_KILL_ALL_BG_EXCEPT = 10;
    public static final int SUBREASON_KILL_ALL_FG = 9;
    public static final int SUBREASON_KILL_BACKGROUND = 24;
    public static final int SUBREASON_KILL_PID = 12;
    public static final int SUBREASON_KILL_UID = 11;
    public static final int SUBREASON_LARGE_CACHED = 5;
    public static final int SUBREASON_MARS_FREEZER_BINDER_TRANSACTION = 1002;
    public static final int SUBREASON_MARS_KILL = 1001;
    public static final int SUBREASON_MEMORY_PRESSURE = 6;
    public static final int SUBREASON_OOM_KILL = 30;
    public static final int SUBREASON_PACKAGE_UPDATE = 25;
    public static final int SUBREASON_REMOVE_LRU = 16;
    public static final int SUBREASON_REMOVE_TASK = 22;
    public static final int SUBREASON_SDK_SANDBOX_DIED = 27;
    public static final int SUBREASON_SDK_SANDBOX_NOT_NEEDED = 28;
    public static final int SUBREASON_STOP_APP = 23;
    public static final int SUBREASON_SYSTEM_UPDATE_DONE = 8;
    public static final int SUBREASON_TOO_MANY_CACHED = 2;
    public static final int SUBREASON_TOO_MANY_EMPTY = 3;
    public static final int SUBREASON_TRIM_EMPTY = 4;
    public static final int SUBREASON_UNDELIVERED_BROADCAST = 26;
    public static final int SUBREASON_UNKNOWN = 0;
    public static final int SUBREASON_WAIT_FOR_DEBUGGER = 1;
    private IAppTraceRetriever mAppTraceRetriever;
    private int mConnectionGroup;
    private int mDefiningUid;
    private String mDescription;
    private boolean mHasForegroundServices;
    private int mImportance;
    private boolean mLoggedInStatsd;
    private IParcelFileDescriptorRetriever mNativeTombstoneRetriever;
    private String[] mPackageList;
    private String mPackageName;
    private int mPackageUid;
    private int mPid;
    private String mProcessName;
    private long mPss;
    private int mRealUid;
    private int mReason;
    private long mRss;
    private byte[] mState;
    private int mStatus;
    private int mSubReason;
    private long mTimestamp;
    private File mTraceFile;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SubReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public String getProcessName() {
        return this.mProcessName;
    }

    public int getReason() {
        return this.mReason;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getImportance() {
        return this.mImportance;
    }

    public long getPss() {
        return this.mPss;
    }

    public long getRss() {
        return this.mRss;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        if (this.mSubReason != 0) {
            sb.append(NavigationBarInflaterView.SIZE_MOD_START);
            sb.append(subreasonToString(this.mSubReason));
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        }
        if (!TextUtils.isEmpty(this.mDescription)) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(this.mDescription);
        }
        return sb.toString();
    }

    public UserHandle getUserHandle() {
        return UserHandle.of(UserHandle.getUserId(this.mRealUid));
    }

    public byte[] getProcessStateSummary() {
        return this.mState;
    }

    public InputStream getTraceInputStream() throws IOException {
        IAppTraceRetriever iAppTraceRetriever = this.mAppTraceRetriever;
        if (iAppTraceRetriever == null && this.mNativeTombstoneRetriever == null) {
            return null;
        }
        try {
            IParcelFileDescriptorRetriever iParcelFileDescriptorRetriever = this.mNativeTombstoneRetriever;
            if (iParcelFileDescriptorRetriever != null) {
                ParcelFileDescriptor pfd = iParcelFileDescriptorRetriever.getPfd();
                if (pfd == null) {
                    return null;
                }
                return new ParcelFileDescriptor.AutoCloseInputStream(pfd);
            }
            ParcelFileDescriptor traceFileDescriptor = iAppTraceRetriever.getTraceFileDescriptor(this.mPackageName, this.mPackageUid, this.mPid);
            if (traceFileDescriptor == null) {
                return null;
            }
            return new GZIPInputStream(new ParcelFileDescriptor.AutoCloseInputStream(traceFileDescriptor));
        } catch (RemoteException unused) {
            return null;
        }
    }

    public File getTraceFile() {
        return this.mTraceFile;
    }

    public int getSubReason() {
        return this.mSubReason;
    }

    public int getConnectionGroup() {
        return this.mConnectionGroup;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String[] getPackageList() {
        return this.mPackageList;
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

    public void setProcessName(String str) {
        this.mProcessName = intern(str);
    }

    public void setReason(int i) {
        this.mReason = i;
    }

    public void setStatus(int i) {
        this.mStatus = i;
    }

    public void setImportance(int i) {
        this.mImportance = i;
    }

    public void setPss(long j) {
        this.mPss = j;
    }

    public void setRss(long j) {
        this.mRss = j;
    }

    public void setTimestamp(long j) {
        this.mTimestamp = j;
    }

    public void setDescription(String str) {
        this.mDescription = intern(str);
    }

    public void setSubReason(int i) {
        this.mSubReason = i;
    }

    public void setConnectionGroup(int i) {
        this.mConnectionGroup = i;
    }

    public void setPackageName(String str) {
        this.mPackageName = intern(str);
    }

    public void setPackageList(String[] strArr) {
        this.mPackageList = strArr;
    }

    public void setProcessStateSummary(byte[] bArr) {
        this.mState = bArr;
    }

    public void setTraceFile(File file) {
        this.mTraceFile = file;
    }

    public void setAppTraceRetriever(IAppTraceRetriever iAppTraceRetriever) {
        this.mAppTraceRetriever = iAppTraceRetriever;
    }

    public void setNativeTombstoneRetriever(IParcelFileDescriptorRetriever iParcelFileDescriptorRetriever) {
        this.mNativeTombstoneRetriever = iParcelFileDescriptorRetriever;
    }

    public boolean isLoggedInStatsd() {
        return this.mLoggedInStatsd;
    }

    public void setLoggedInStatsd(boolean z) {
        this.mLoggedInStatsd = z;
    }

    public boolean hasForegroundServices() {
        return this.mHasForegroundServices;
    }

    public void setHasForegroundServices(boolean z) {
        this.mHasForegroundServices = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPid);
        parcel.writeInt(this.mRealUid);
        parcel.writeInt(this.mPackageUid);
        parcel.writeInt(this.mDefiningUid);
        parcel.writeString(this.mProcessName);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mConnectionGroup);
        parcel.writeInt(this.mReason);
        parcel.writeInt(this.mSubReason);
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mImportance);
        parcel.writeLong(this.mPss);
        parcel.writeLong(this.mRss);
        parcel.writeLong(this.mTimestamp);
        parcel.writeString(this.mDescription);
        parcel.writeByteArray(this.mState);
        if (this.mAppTraceRetriever != null) {
            parcel.writeInt(1);
            parcel.writeStrongBinder(this.mAppTraceRetriever.asBinder());
        } else {
            parcel.writeInt(0);
        }
        if (this.mNativeTombstoneRetriever != null) {
            parcel.writeInt(1);
            parcel.writeStrongBinder(this.mNativeTombstoneRetriever.asBinder());
        } else {
            parcel.writeInt(0);
        }
    }

    public ApplicationExitInfo() {
    }

    public ApplicationExitInfo(ApplicationExitInfo applicationExitInfo) {
        this.mPid = applicationExitInfo.mPid;
        this.mRealUid = applicationExitInfo.mRealUid;
        this.mPackageUid = applicationExitInfo.mPackageUid;
        this.mDefiningUid = applicationExitInfo.mDefiningUid;
        this.mProcessName = applicationExitInfo.mProcessName;
        this.mPackageName = applicationExitInfo.mPackageName;
        this.mConnectionGroup = applicationExitInfo.mConnectionGroup;
        this.mReason = applicationExitInfo.mReason;
        this.mStatus = applicationExitInfo.mStatus;
        this.mSubReason = applicationExitInfo.mSubReason;
        this.mImportance = applicationExitInfo.mImportance;
        this.mPss = applicationExitInfo.mPss;
        this.mRss = applicationExitInfo.mRss;
        this.mTimestamp = applicationExitInfo.mTimestamp;
        this.mDescription = applicationExitInfo.mDescription;
        this.mPackageName = applicationExitInfo.mPackageName;
        this.mPackageList = applicationExitInfo.mPackageList;
        this.mState = applicationExitInfo.mState;
        this.mTraceFile = applicationExitInfo.mTraceFile;
        this.mAppTraceRetriever = applicationExitInfo.mAppTraceRetriever;
        this.mNativeTombstoneRetriever = applicationExitInfo.mNativeTombstoneRetriever;
        this.mLoggedInStatsd = applicationExitInfo.mLoggedInStatsd;
        this.mHasForegroundServices = applicationExitInfo.mHasForegroundServices;
    }

    private ApplicationExitInfo(Parcel parcel) {
        this.mPid = parcel.readInt();
        this.mRealUid = parcel.readInt();
        this.mPackageUid = parcel.readInt();
        this.mDefiningUid = parcel.readInt();
        this.mProcessName = intern(parcel.readString());
        this.mPackageName = intern(parcel.readString());
        this.mConnectionGroup = parcel.readInt();
        this.mReason = parcel.readInt();
        this.mSubReason = parcel.readInt();
        this.mStatus = parcel.readInt();
        this.mImportance = parcel.readInt();
        this.mPss = parcel.readLong();
        this.mRss = parcel.readLong();
        this.mTimestamp = parcel.readLong();
        this.mDescription = intern(parcel.readString());
        this.mState = parcel.createByteArray();
        if (parcel.readInt() == 1) {
            this.mAppTraceRetriever = IAppTraceRetriever.Stub.asInterface(parcel.readStrongBinder());
        }
        if (parcel.readInt() == 1) {
            this.mNativeTombstoneRetriever = IParcelFileDescriptorRetriever.Stub.asInterface(parcel.readStrongBinder());
        }
    }

    private static String intern(String str) {
        if (str != null) {
            return str.intern();
        }
        return null;
    }

    public void dump(PrintWriter printWriter, String str, String str2, SimpleDateFormat simpleDateFormat) {
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("ApplicationExitInfo ");
        sb.append(str2);
        sb.append(":\n");
        sb.append(str);
        sb.append("  timestamp=");
        sb.append(simpleDateFormat.format(new Date(this.mTimestamp)));
        sb.append(" pid=");
        sb.append(this.mPid);
        sb.append(" realUid=");
        sb.append(this.mRealUid);
        sb.append(" packageUid=");
        sb.append(this.mPackageUid);
        sb.append(" definingUid=");
        sb.append(this.mDefiningUid);
        sb.append(" user=");
        sb.append(UserHandle.getUserId(this.mPackageUid));
        sb.append('\n');
        sb.append(str);
        sb.append("  process=");
        sb.append(this.mProcessName);
        sb.append(" reason=");
        sb.append(this.mReason);
        sb.append(" (");
        sb.append(reasonCodeToString(this.mReason));
        sb.append(") subreason=");
        sb.append(this.mSubReason);
        sb.append(" (");
        sb.append(subreasonToString(this.mSubReason));
        sb.append(") status=");
        sb.append(this.mStatus);
        sb.append('\n');
        sb.append(str);
        sb.append("  importance=");
        sb.append(this.mImportance);
        sb.append(" pss=");
        DebugUtils.sizeValueToString(this.mPss << 10, sb);
        sb.append(" rss=");
        DebugUtils.sizeValueToString(this.mRss << 10, sb);
        sb.append(" description=");
        sb.append(this.mDescription);
        sb.append(" state=");
        if (ArrayUtils.isEmpty(this.mState)) {
            str3 = "empty";
        } else {
            str3 = Integer.toString(this.mState.length) + " bytes";
        }
        sb.append(str3);
        sb.append(" trace=");
        sb.append(this.mTraceFile);
        sb.append('\n');
        printWriter.print(sb.toString());
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("ApplicationExitInfo(timestamp=");
        sb.append(new SimpleDateFormat().format(new Date(this.mTimestamp)));
        sb.append(" pid=");
        sb.append(this.mPid);
        sb.append(" realUid=");
        sb.append(this.mRealUid);
        sb.append(" packageUid=");
        sb.append(this.mPackageUid);
        sb.append(" definingUid=");
        sb.append(this.mDefiningUid);
        sb.append(" user=");
        sb.append(UserHandle.getUserId(this.mPackageUid));
        sb.append(" process=");
        sb.append(this.mProcessName);
        sb.append(" reason=");
        sb.append(this.mReason);
        sb.append(" (");
        sb.append(reasonCodeToString(this.mReason));
        sb.append(") subreason=");
        sb.append(this.mSubReason);
        sb.append(" (");
        sb.append(subreasonToString(this.mSubReason));
        sb.append(") status=");
        sb.append(this.mStatus);
        sb.append(" importance=");
        sb.append(this.mImportance);
        sb.append(" pss=");
        DebugUtils.sizeValueToString(this.mPss << 10, sb);
        sb.append(" rss=");
        DebugUtils.sizeValueToString(this.mRss << 10, sb);
        sb.append(" description=");
        sb.append(this.mDescription);
        sb.append(" state=");
        if (ArrayUtils.isEmpty(this.mState)) {
            str = "empty";
        } else {
            str = Integer.toString(this.mState.length) + " bytes";
        }
        sb.append(str);
        sb.append(" trace=");
        sb.append(this.mTraceFile);
        return sb.toString();
    }

    public static String reasonCodeToString(int i) {
        switch (i) {
            case 1:
                return "EXIT_SELF";
            case 2:
                return "SIGNALED";
            case 3:
                return "LOW_MEMORY";
            case 4:
                return "APP CRASH(EXCEPTION)";
            case 5:
                return "APP CRASH(NATIVE)";
            case 6:
                return "ANR";
            case 7:
                return "INITIALIZATION FAILURE";
            case 8:
                return "PERMISSION CHANGE";
            case 9:
                return "EXCESSIVE RESOURCE USAGE";
            case 10:
                return "USER REQUESTED";
            case 11:
                return "USER STOPPED";
            case 12:
                return "DEPENDENCY DIED";
            case 13:
                return "OTHER KILLS BY SYSTEM";
            case 14:
                return "FREEZER";
            case 15:
                return "STATE CHANGE";
            case 16:
                return "PACKAGE UPDATED";
            default:
                return "UNKNOWN";
        }
    }

    public static String subreasonToString(int i) {
        switch (i) {
            case 1:
                return "WAIT FOR DEBUGGER";
            case 2:
                return "TOO MANY CACHED PROCS";
            case 3:
                return "TOO MANY EMPTY PROCS";
            case 4:
                return "TRIM EMPTY";
            case 5:
                return "LARGE CACHED";
            case 6:
                return "MEMORY PRESSURE";
            case 7:
                return "EXCESSIVE CPU USAGE";
            case 8:
                return "SYSTEM UPDATE_DONE";
            case 9:
                return "KILL ALL FG";
            case 10:
                return "KILL ALL BG EXCEPT";
            case 11:
                return "KILL UID";
            case 12:
                return "KILL PID";
            case 13:
                return "INVALID START";
            case 14:
                return "INVALID STATE";
            case 15:
                return "IMPERCEPTIBLE";
            case 16:
                return "REMOVE LRU";
            case 17:
                return "ISOLATED NOT NEEDED";
            default:
                switch (i) {
                    case 19:
                        return "FREEZER BINDER IOCTL";
                    case 20:
                        return "FREEZER BINDER TRANSACTION";
                    case 21:
                        return "FORCE STOP";
                    case 22:
                        return "REMOVE TASK";
                    case 23:
                        return "STOP APP";
                    case 24:
                        return "KILL BACKGROUND";
                    case 25:
                        return "PACKAGE UPDATE";
                    case 26:
                        return "UNDELIVERED BROADCAST";
                    default:
                        switch (i) {
                            case 29:
                                return "EXCESSIVE BINDER OBJECTS";
                            case 30:
                                return "OOM KILL";
                            case 31:
                                return "FREEZER BINDER ASYNC FULL";
                            case 32:
                                return "EXCESSIVE_OUTGOING_BROADCASTS_WHILE_CACHED";
                            default:
                                switch (i) {
                                    case 1001:
                                    case 1002:
                                        return "MARS KILL";
                                    case 1003:
                                        return "GENAI UNLOAD POLICY";
                                    default:
                                        return "UNKNOWN";
                                }
                        }
                }
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mPid);
        protoOutputStream.write(1120986464258L, this.mRealUid);
        protoOutputStream.write(1120986464259L, this.mPackageUid);
        protoOutputStream.write(1120986464260L, this.mDefiningUid);
        protoOutputStream.write(1138166333445L, this.mProcessName);
        protoOutputStream.write(1120986464262L, this.mConnectionGroup);
        protoOutputStream.write(1159641169927L, this.mReason);
        protoOutputStream.write(1159641169928L, this.mSubReason);
        protoOutputStream.write(1120986464265L, this.mStatus);
        protoOutputStream.write(1159641169930L, this.mImportance);
        protoOutputStream.write(1112396529675L, this.mPss);
        protoOutputStream.write(1112396529676L, this.mRss);
        protoOutputStream.write(1112396529677L, this.mTimestamp);
        protoOutputStream.write(1138166333454L, this.mDescription);
        protoOutputStream.write(1151051235343L, this.mState);
        File file = this.mTraceFile;
        protoOutputStream.write(1138166333456L, file == null ? null : file.getAbsolutePath());
        protoOutputStream.end(start);
    }

    public void readFromProto(ProtoInputStream protoInputStream, long j) throws IOException, WireTypeMismatchException {
        long start = protoInputStream.start(j);
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
                    this.mConnectionGroup = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    this.mReason = protoInputStream.readInt(1159641169927L);
                    break;
                case 8:
                    this.mSubReason = protoInputStream.readInt(1159641169928L);
                    break;
                case 9:
                    this.mStatus = protoInputStream.readInt(1120986464265L);
                    break;
                case 10:
                    this.mImportance = protoInputStream.readInt(1159641169930L);
                    break;
                case 11:
                    this.mPss = protoInputStream.readLong(1112396529675L);
                    break;
                case 12:
                    this.mRss = protoInputStream.readLong(1112396529676L);
                    break;
                case 13:
                    this.mTimestamp = protoInputStream.readLong(1112396529677L);
                    break;
                case 14:
                    this.mDescription = intern(protoInputStream.readString(1138166333454L));
                    break;
                case 15:
                    this.mState = protoInputStream.readBytes(1151051235343L);
                    break;
                case 16:
                    String readString = protoInputStream.readString(1138166333456L);
                    if (!TextUtils.isEmpty(readString)) {
                        this.mTraceFile = new File(readString);
                        break;
                    } else {
                        break;
                    }
            }
        }
        protoInputStream.end(start);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ApplicationExitInfo)) {
            ApplicationExitInfo applicationExitInfo = (ApplicationExitInfo) obj;
            if (this.mPid == applicationExitInfo.mPid && this.mRealUid == applicationExitInfo.mRealUid && this.mPackageUid == applicationExitInfo.mPackageUid && this.mDefiningUid == applicationExitInfo.mDefiningUid && this.mConnectionGroup == applicationExitInfo.mConnectionGroup && this.mReason == applicationExitInfo.mReason && this.mSubReason == applicationExitInfo.mSubReason && this.mImportance == applicationExitInfo.mImportance && this.mStatus == applicationExitInfo.mStatus && this.mTimestamp == applicationExitInfo.mTimestamp && this.mPss == applicationExitInfo.mPss && this.mRss == applicationExitInfo.mRss && TextUtils.equals(this.mProcessName, applicationExitInfo.mProcessName) && TextUtils.equals(this.mDescription, applicationExitInfo.mDescription)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((this.mPid * 31) + this.mRealUid) * 31) + this.mPackageUid) * 31) + this.mDefiningUid) * 31) + this.mConnectionGroup) * 31) + this.mReason) * 31) + this.mSubReason) * 31) + this.mImportance) * 31) + this.mStatus) * 31) + ((int) this.mPss)) * 31) + ((int) this.mRss)) * 31) + Long.hashCode(this.mTimestamp)) * 31) + Objects.hashCode(this.mProcessName)) * 31) + Objects.hashCode(this.mDescription);
    }
}
