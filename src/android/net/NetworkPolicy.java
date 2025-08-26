package android.net;

import android.net.NetworkTemplate;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.BackupUtils;
import android.util.Log;
import android.util.Range;
import android.util.RecurrenceRule;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public class NetworkPolicy implements Parcelable, Comparable<NetworkPolicy> {
    public static final Parcelable.Creator<NetworkPolicy> CREATOR = new Parcelable.Creator<NetworkPolicy>() { // from class: android.net.NetworkPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkPolicy createFromParcel(Parcel parcel) {
            return new NetworkPolicy(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkPolicy[] newArray(int i) {
            return new NetworkPolicy[i];
        }
    };
    public static final int CYCLE_NONE = -1;
    private static final long DEFAULT_MTU = 1500;
    public static final long LIMIT_DISABLED = -1;
    public static final long SNOOZE_NEVER = -1;
    private static final String TAG = "NetworkPolicy";
    private static final int TEMPLATE_BACKUP_VERSION_1_INIT = 1;
    private static final int TEMPLATE_BACKUP_VERSION_2_UNSUPPORTED = 2;
    private static final int TEMPLATE_BACKUP_VERSION_3_SUPPORT_CARRIER_TEMPLATE = 3;
    private static final int TEMPLATE_BACKUP_VERSION_LATEST = 3;
    private static final int VERSION_INIT = 1;
    private static final int VERSION_RAPID = 3;
    private static final int VERSION_RULE = 2;
    public static final long WARNING_DISABLED = -1;
    public RecurrenceRule cycleRule;
    public boolean inferred;
    public long lastLimitSnooze;
    public long lastRapidSnooze;
    public long lastWarningSnooze;
    public long limitBytes;

    @Deprecated
    public boolean metered;
    public NetworkTemplate template;
    public long warningBytes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static RecurrenceRule buildRule(int i, ZoneId zoneId) {
        if (i != -1) {
            return RecurrenceRule.buildRecurringMonthly(i, zoneId);
        }
        return RecurrenceRule.buildNever();
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate networkTemplate, int i, String str, long j, long j2, boolean z) {
        this(networkTemplate, i, str, j, j2, -1L, -1L, z, false);
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate networkTemplate, int i, String str, long j, long j2, long j3, long j4, boolean z, boolean z2) {
        this(networkTemplate, buildRule(i, ZoneId.of(str)), j, j2, j3, j4, z, z2);
    }

    @Deprecated
    public NetworkPolicy(NetworkTemplate networkTemplate, RecurrenceRule recurrenceRule, long j, long j2, long j3, long j4, boolean z, boolean z2) {
        this(networkTemplate, recurrenceRule, j, j2, j3, j4, -1L, z, z2);
    }

    public NetworkPolicy(NetworkTemplate networkTemplate, RecurrenceRule recurrenceRule, long j, long j2, long j3, long j4, long j5, boolean z, boolean z2) {
        this.warningBytes = -1L;
        this.limitBytes = -1L;
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
        this.metered = true;
        this.inferred = false;
        this.template = (NetworkTemplate) Preconditions.checkNotNull(networkTemplate, "missing NetworkTemplate");
        this.cycleRule = (RecurrenceRule) Preconditions.checkNotNull(recurrenceRule, "missing RecurrenceRule");
        this.warningBytes = j;
        this.limitBytes = j2;
        this.lastWarningSnooze = j3;
        this.lastLimitSnooze = j4;
        this.lastRapidSnooze = j5;
        this.metered = z;
        this.inferred = z2;
    }

    private NetworkPolicy(Parcel parcel) {
        this.warningBytes = -1L;
        this.limitBytes = -1L;
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
        this.metered = true;
        this.inferred = false;
        this.template = (NetworkTemplate) parcel.readParcelable(null, NetworkTemplate.class);
        this.cycleRule = (RecurrenceRule) parcel.readParcelable(null, RecurrenceRule.class);
        this.warningBytes = parcel.readLong();
        this.limitBytes = parcel.readLong();
        this.lastWarningSnooze = parcel.readLong();
        this.lastLimitSnooze = parcel.readLong();
        this.lastRapidSnooze = parcel.readLong();
        this.metered = parcel.readInt() != 0;
        this.inferred = parcel.readInt() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.template, i);
        parcel.writeParcelable(this.cycleRule, i);
        parcel.writeLong(this.warningBytes);
        parcel.writeLong(this.limitBytes);
        parcel.writeLong(this.lastWarningSnooze);
        parcel.writeLong(this.lastLimitSnooze);
        parcel.writeLong(this.lastRapidSnooze);
        parcel.writeInt(this.metered ? 1 : 0);
        parcel.writeInt(this.inferred ? 1 : 0);
    }

    public Iterator<Range<ZonedDateTime>> cycleIterator() {
        return this.cycleRule.cycleIterator();
    }

    public boolean isOverWarning(long j) {
        long j2 = this.warningBytes;
        return j2 != -1 && j >= j2;
    }

    public boolean isOverLimit(long j) {
        long j2 = j + 3000;
        long j3 = this.limitBytes;
        return j3 != -1 && j2 >= j3;
    }

    public void clearSnooze() {
        this.lastWarningSnooze = -1L;
        this.lastLimitSnooze = -1L;
        this.lastRapidSnooze = -1L;
    }

    public boolean hasCycle() {
        return this.cycleRule.cycleIterator().hasNext();
    }

    @Override // java.lang.Comparable
    public int compareTo(NetworkPolicy networkPolicy) {
        if (networkPolicy == null) {
            return -1;
        }
        long j = networkPolicy.limitBytes;
        if (j == -1) {
            return -1;
        }
        long j2 = this.limitBytes;
        return (j2 == -1 || j < j2) ? 1 : 0;
    }

    public int hashCode() {
        return Objects.hash(this.template, this.cycleRule, Long.valueOf(this.warningBytes), Long.valueOf(this.limitBytes), Long.valueOf(this.lastWarningSnooze), Long.valueOf(this.lastLimitSnooze), Long.valueOf(this.lastRapidSnooze), Boolean.valueOf(this.metered), Boolean.valueOf(this.inferred));
    }

    public boolean equals(Object obj) {
        if (obj instanceof NetworkPolicy) {
            NetworkPolicy networkPolicy = (NetworkPolicy) obj;
            if (this.warningBytes == networkPolicy.warningBytes && this.limitBytes == networkPolicy.limitBytes && this.lastWarningSnooze == networkPolicy.lastWarningSnooze && this.lastLimitSnooze == networkPolicy.lastLimitSnooze && this.lastRapidSnooze == networkPolicy.lastRapidSnooze && this.metered == networkPolicy.metered && this.inferred == networkPolicy.inferred && Objects.equals(this.template, networkPolicy.template) && Objects.equals(this.cycleRule, networkPolicy.cycleRule)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "NetworkPolicy{template=" + this.template + " cycleRule=" + this.cycleRule + " warningBytes=" + this.warningBytes + " limitBytes=" + this.limitBytes + " lastWarningSnooze=" + this.lastWarningSnooze + " lastLimitSnooze=" + this.lastLimitSnooze + " lastRapidSnooze=" + this.lastRapidSnooze + " metered=" + this.metered + " inferred=" + this.inferred + "}";
    }

    public byte[] getBytesForBackup() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(3);
        dataOutputStream.write(getNetworkTemplateBytesForBackup());
        this.cycleRule.writeToStream(dataOutputStream);
        dataOutputStream.writeLong(this.warningBytes);
        dataOutputStream.writeLong(this.limitBytes);
        dataOutputStream.writeLong(this.lastWarningSnooze);
        dataOutputStream.writeLong(this.lastLimitSnooze);
        dataOutputStream.writeLong(this.lastRapidSnooze);
        dataOutputStream.writeInt(this.metered ? 1 : 0);
        dataOutputStream.writeInt(this.inferred ? 1 : 0);
        return byteArrayOutputStream.toByteArray();
    }

    public static NetworkPolicy getNetworkPolicyFromBackup(DataInputStream dataInputStream) throws BackupUtils.BadVersionException, IOException {
        DataInputStream dataInputStream2;
        RecurrenceRule recurrenceRuleBuildRule;
        int i = dataInputStream.readInt();
        if (i < 1 || i > 3) {
            throw new BackupUtils.BadVersionException("Unknown backup version: " + i);
        }
        NetworkTemplate networkTemplateFromBackup = getNetworkTemplateFromBackup(dataInputStream);
        if (i >= 2) {
            dataInputStream2 = dataInputStream;
            recurrenceRuleBuildRule = new RecurrenceRule(dataInputStream2);
        } else {
            dataInputStream2 = dataInputStream;
            recurrenceRuleBuildRule = buildRule(dataInputStream2.readInt(), ZoneId.of(BackupUtils.readString(dataInputStream2)));
        }
        return new NetworkPolicy(networkTemplateFromBackup, recurrenceRuleBuildRule, dataInputStream2.readLong(), dataInputStream2.readLong(), dataInputStream2.readLong(), dataInputStream2.readLong(), i >= 3 ? dataInputStream2.readLong() : -1L, dataInputStream2.readInt() == 1, dataInputStream2.readInt() == 1);
    }

    private byte[] getNetworkTemplateBytesForBackup() throws IOException {
        if (!isTemplatePersistable(this.template)) {
            Log.wtf(TAG, "Trying to backup non-persistable template: " + this);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeInt(3);
        dataOutputStream.writeInt(this.template.getMatchRule());
        Set subscriberIds = this.template.getSubscriberIds();
        BackupUtils.writeString(dataOutputStream, subscriberIds.isEmpty() ? null : (String) subscriberIds.iterator().next());
        BackupUtils.writeString(dataOutputStream, this.template.getWifiNetworkKeys().isEmpty() ? null : (String) this.template.getWifiNetworkKeys().iterator().next());
        dataOutputStream.writeInt(this.template.getMeteredness());
        return byteArrayOutputStream.toByteArray();
    }

    private static NetworkTemplate getNetworkTemplateFromBackup(DataInputStream dataInputStream) throws BackupUtils.BadVersionException, IOException {
        int i;
        int i2 = dataInputStream.readInt();
        int i3 = 1;
        if (i2 < 1 || i2 > 3 || i2 == 2) {
            throw new BackupUtils.BadVersionException("Unknown Backup Serialization Version");
        }
        int i4 = dataInputStream.readInt();
        String string = BackupUtils.readString(dataInputStream);
        String string2 = BackupUtils.readString(dataInputStream);
        if (i2 >= 3) {
            i = dataInputStream.readInt();
        } else {
            if (i4 != 1 && i4 != 10) {
                i3 = -1;
            }
            i = i3;
        }
        try {
            NetworkTemplate.Builder meteredness = new NetworkTemplate.Builder(i4).setMeteredness(i);
            if (string != null) {
                meteredness.setSubscriberIds(Set.of(string));
            }
            if (string2 != null) {
                meteredness.setWifiNetworkKeys(Set.of(string2));
            }
            return meteredness.build();
        } catch (IllegalArgumentException e) {
            throw new BackupUtils.BadVersionException("Restored network template contains unknown match rule " + i4, e);
        }
    }

    public static boolean isTemplatePersistable(NetworkTemplate networkTemplate) {
        int matchRule = networkTemplate.getMatchRule();
        if (matchRule != 1) {
            if (matchRule != 8) {
                if (matchRule != 10) {
                    if (matchRule != 4) {
                        if (matchRule != 5) {
                            return false;
                        }
                    } else if (networkTemplate.getWifiNetworkKeys().isEmpty() && networkTemplate.getSubscriberIds().isEmpty()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return !networkTemplate.getSubscriberIds().isEmpty() && networkTemplate.getMeteredness() == 1;
    }
}
