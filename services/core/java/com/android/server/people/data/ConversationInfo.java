package com.android.server.people.data;

import android.content.LocusId;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Slog;
import android.util.proto.ProtoInputStream;

import com.android.internal.util.Preconditions;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ConversationInfo {
    public String mContactPhoneNumber;
    public Uri mContactUri;
    public int mConversationFlags;
    public long mCreationTimestamp;
    public Map mCurrStatuses;
    public long mLastEventTimestamp;
    public LocusId mLocusId;
    public String mNotificationChannelId;
    public String mParentNotificationChannelId;
    public int mShortcutFlags;
    public String mShortcutId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        public String mContactPhoneNumber;
        public Uri mContactUri;
        public int mConversationFlags;
        public long mCreationTimestamp;
        public final Map mCurrStatuses;
        public final long mLastEventTimestamp;
        public LocusId mLocusId;
        public String mNotificationChannelId;
        public final String mParentNotificationChannelId;
        public int mShortcutFlags;
        public String mShortcutId;

        public Builder() {
            this.mCurrStatuses = new HashMap();
        }

        public Builder(ConversationInfo conversationInfo) {
            this.mCurrStatuses = new HashMap();
            String str = this.mShortcutId;
            if (str == null) {
                this.mShortcutId = conversationInfo.mShortcutId;
            } else {
                Preconditions.checkArgument(str.equals(conversationInfo.mShortcutId));
            }
            this.mLocusId = conversationInfo.mLocusId;
            this.mContactUri = conversationInfo.mContactUri;
            this.mContactPhoneNumber = conversationInfo.mContactPhoneNumber;
            this.mNotificationChannelId = conversationInfo.mNotificationChannelId;
            this.mParentNotificationChannelId = conversationInfo.mParentNotificationChannelId;
            this.mLastEventTimestamp = conversationInfo.mLastEventTimestamp;
            this.mCreationTimestamp = conversationInfo.mCreationTimestamp;
            this.mShortcutFlags = conversationInfo.mShortcutFlags;
            this.mConversationFlags = conversationInfo.mConversationFlags;
            this.mCurrStatuses = conversationInfo.mCurrStatuses;
        }

        public final ConversationInfo build() {
            Objects.requireNonNull(this.mShortcutId);
            ConversationInfo conversationInfo = new ConversationInfo();
            conversationInfo.mShortcutId = this.mShortcutId;
            conversationInfo.mLocusId = this.mLocusId;
            conversationInfo.mContactUri = this.mContactUri;
            conversationInfo.mContactPhoneNumber = this.mContactPhoneNumber;
            conversationInfo.mNotificationChannelId = this.mNotificationChannelId;
            conversationInfo.mParentNotificationChannelId = this.mParentNotificationChannelId;
            conversationInfo.mLastEventTimestamp = this.mLastEventTimestamp;
            conversationInfo.mCreationTimestamp = this.mCreationTimestamp;
            conversationInfo.mShortcutFlags = this.mShortcutFlags;
            conversationInfo.mConversationFlags = this.mConversationFlags;
            conversationInfo.mCurrStatuses = this.mCurrStatuses;
            return conversationInfo;
        }

        public final void setConversationFlag(int i, boolean z) {
            if (z) {
                this.mConversationFlags = i | this.mConversationFlags;
            } else {
                this.mConversationFlags = (~i) & this.mConversationFlags;
            }
        }
    }

    public static ConversationInfo readFromBackupPayload(byte[] bArr) {
        int i;
        HashMap hashMap = new HashMap();
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        try {
            String readUTF = dataInputStream.readUTF();
            String readUTF2 = dataInputStream.readUTF();
            LocusId locusId = !TextUtils.isEmpty(readUTF2) ? new LocusId(readUTF2) : null;
            String readUTF3 = dataInputStream.readUTF();
            Uri parse = !TextUtils.isEmpty(readUTF3) ? Uri.parse(readUTF3) : null;
            String readUTF4 = dataInputStream.readUTF();
            if (TextUtils.isEmpty(readUTF4)) {
                readUTF4 = null;
            }
            int readInt = dataInputStream.readInt();
            int readInt2 = dataInputStream.readInt();
            String readUTF5 = dataInputStream.readUTF();
            if (TextUtils.isEmpty(readUTF5)) {
                readUTF5 = null;
            }
            String readUTF6 = dataInputStream.readUTF();
            if (TextUtils.isEmpty(readUTF6)) {
                readUTF6 = null;
            }
            long readLong = dataInputStream.readLong();
            try {
                i = dataInputStream.readInt();
            } catch (EOFException unused) {
                i = 0;
            }
            long readLong2 = i == 1 ? dataInputStream.readLong() : 0L;
            Objects.requireNonNull(readUTF);
            ConversationInfo conversationInfo = new ConversationInfo();
            conversationInfo.mShortcutId = readUTF;
            conversationInfo.mLocusId = locusId;
            conversationInfo.mContactUri = parse;
            conversationInfo.mContactPhoneNumber = readUTF5;
            conversationInfo.mNotificationChannelId = readUTF4;
            conversationInfo.mParentNotificationChannelId = readUTF6;
            conversationInfo.mLastEventTimestamp = readLong;
            conversationInfo.mCreationTimestamp = readLong2;
            conversationInfo.mShortcutFlags = readInt;
            conversationInfo.mConversationFlags = readInt2;
            conversationInfo.mCurrStatuses = hashMap;
            return conversationInfo;
        } catch (IOException e) {
            Slog.e(
                    "ConversationInfo",
                    "Failed to read conversation info fields from backup payload.",
                    e);
            return null;
        }
    }

    public static ConversationInfo readFromProto(ProtoInputStream protoInputStream) {
        HashMap hashMap = new HashMap();
        int i = 0;
        long j = 0;
        String str = null;
        LocusId locusId = null;
        Uri uri = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        int i2 = 0;
        long j2 = 0;
        while (true) {
            if (protoInputStream.nextField() == -1) {
                Objects.requireNonNull(str);
                ConversationInfo conversationInfo = new ConversationInfo();
                conversationInfo.mShortcutId = str;
                conversationInfo.mLocusId = locusId;
                conversationInfo.mContactUri = uri;
                conversationInfo.mContactPhoneNumber = str2;
                conversationInfo.mNotificationChannelId = str3;
                conversationInfo.mParentNotificationChannelId = str4;
                conversationInfo.mLastEventTimestamp = j2;
                conversationInfo.mCreationTimestamp = j;
                conversationInfo.mShortcutFlags = i;
                conversationInfo.mConversationFlags = i2;
                conversationInfo.mCurrStatuses = hashMap;
                return conversationInfo;
            }
            HashMap hashMap2 = hashMap;
            int i3 = i;
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    str = protoInputStream.readString(1138166333441L);
                    hashMap = hashMap2;
                    i = i3;
                    i2 = i2;
                    continue;
                case 2:
                    long start = protoInputStream.start(1146756268034L);
                    for (int i4 = -1; protoInputStream.nextField() != i4; i4 = -1) {
                        if (protoInputStream.getFieldNumber() == 1) {
                            locusId = new LocusId(protoInputStream.readString(1138166333441L));
                            i2 = i2;
                        }
                    }
                    protoInputStream.end(start);
                    break;
                case 3:
                    uri = Uri.parse(protoInputStream.readString(1138166333443L));
                    break;
                case 4:
                    str3 = protoInputStream.readString(1138166333444L);
                    break;
                case 5:
                    i = protoInputStream.readInt(1120986464261L);
                    hashMap = hashMap2;
                    continue;
                case 6:
                    i2 = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    str2 = protoInputStream.readString(1138166333447L);
                    break;
                case 8:
                    str4 = protoInputStream.readString(1138166333448L);
                    break;
                case 9:
                    j2 = protoInputStream.readLong(1112396529673L);
                    break;
                case 10:
                    j = protoInputStream.readLong(1112396529674L);
                    break;
                default:
                    Slog.w(
                            "ConversationInfo",
                            "Could not read undefined field: " + protoInputStream.getFieldNumber());
                    break;
            }
            hashMap = hashMap2;
            i = i3;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConversationInfo)) {
            return false;
        }
        ConversationInfo conversationInfo = (ConversationInfo) obj;
        return Objects.equals(this.mShortcutId, conversationInfo.mShortcutId)
                && Objects.equals(this.mLocusId, conversationInfo.mLocusId)
                && Objects.equals(this.mContactUri, conversationInfo.mContactUri)
                && Objects.equals(this.mContactPhoneNumber, conversationInfo.mContactPhoneNumber)
                && Objects.equals(
                        this.mNotificationChannelId, conversationInfo.mNotificationChannelId)
                && Objects.equals(
                        this.mParentNotificationChannelId,
                        conversationInfo.mParentNotificationChannelId)
                && Long.valueOf(this.mLastEventTimestamp)
                        .equals(Long.valueOf(conversationInfo.mLastEventTimestamp))
                && this.mCreationTimestamp == conversationInfo.mCreationTimestamp
                && this.mShortcutFlags == conversationInfo.mShortcutFlags
                && this.mConversationFlags == conversationInfo.mConversationFlags
                && Objects.equals(this.mCurrStatuses, conversationInfo.mCurrStatuses);
    }

    public final boolean hasConversationFlags(int i) {
        return (this.mConversationFlags & i) == i;
    }

    public final boolean hasShortcutFlags(int i) {
        return (this.mShortcutFlags & i) == i;
    }

    public final int hashCode() {
        return Objects.hash(
                this.mShortcutId,
                this.mLocusId,
                this.mContactUri,
                this.mContactPhoneNumber,
                this.mNotificationChannelId,
                this.mParentNotificationChannelId,
                Long.valueOf(this.mLastEventTimestamp),
                Long.valueOf(this.mCreationTimestamp),
                Integer.valueOf(this.mShortcutFlags),
                Integer.valueOf(this.mConversationFlags),
                this.mCurrStatuses);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ConversationInfo {shortcutId=");
        sb.append(this.mShortcutId);
        sb.append(", locusId=");
        sb.append(this.mLocusId);
        sb.append(", contactUri=");
        sb.append(this.mContactUri);
        sb.append(", phoneNumber=");
        sb.append(this.mContactPhoneNumber);
        sb.append(", notificationChannelId=");
        sb.append(this.mNotificationChannelId);
        sb.append(", parentNotificationChannelId=");
        sb.append(this.mParentNotificationChannelId);
        sb.append(", lastEventTimestamp=");
        sb.append(this.mLastEventTimestamp);
        sb.append(", creationTimestamp=");
        sb.append(this.mCreationTimestamp);
        sb.append(", statuses=");
        sb.append(this.mCurrStatuses);
        sb.append(", shortcutFlags=0x");
        sb.append(Integer.toHexString(this.mShortcutFlags));
        sb.append(" [");
        if (hasShortcutFlags(8192)) {
            sb.append("Liv");
        }
        if (hasShortcutFlags(EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)) {
            sb.append("Cac");
        }
        sb.append("], conversationFlags=0x");
        sb.append(Integer.toHexString(this.mConversationFlags));
        sb.append(" [");
        if (hasConversationFlags(1)) {
            sb.append("Imp");
        }
        if (hasConversationFlags(2)) {
            sb.append("Sil");
        }
        if (hasConversationFlags(4)) {
            sb.append("Bub");
        }
        if (hasConversationFlags(64)) {
            sb.append("Dem");
        }
        if (hasConversationFlags(8)) {
            sb.append("PIm");
        }
        if (hasConversationFlags(16)) {
            sb.append("Bot");
        }
        if (hasConversationFlags(32)) {
            sb.append("Sta");
        }
        sb.append("]}");
        return sb.toString();
    }
}
