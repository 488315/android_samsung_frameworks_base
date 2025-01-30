package com.android.systemui.statusbar.notification.logging.nano;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Notifications$NotificationList extends MessageNano {
    public Notifications$Notification[] notifications;

    public Notifications$NotificationList() {
        clear();
    }

    public Notifications$NotificationList clear() {
        this.notifications = Notifications$Notification.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        Notifications$Notification[] notifications$NotificationArr = this.notifications;
        int i = 0;
        if (notifications$NotificationArr == null || notifications$NotificationArr.length <= 0) {
            return 0;
        }
        int i2 = 0;
        while (true) {
            Notifications$Notification[] notifications$NotificationArr2 = this.notifications;
            if (i >= notifications$NotificationArr2.length) {
                return i2;
            }
            Notifications$Notification notifications$Notification = notifications$NotificationArr2[i];
            if (notifications$Notification != null) {
                i2 += CodedOutputByteBufferNano.computeMessageSize(1, notifications$Notification);
            }
            i++;
        }
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        Notifications$Notification[] notifications$NotificationArr = this.notifications;
        if (notifications$NotificationArr == null || notifications$NotificationArr.length <= 0) {
            return;
        }
        int i = 0;
        while (true) {
            Notifications$Notification[] notifications$NotificationArr2 = this.notifications;
            if (i >= notifications$NotificationArr2.length) {
                return;
            }
            Notifications$Notification notifications$Notification = notifications$NotificationArr2[i];
            if (notifications$Notification != null) {
                codedOutputByteBufferNano.writeMessage(1, notifications$Notification);
            }
            i++;
        }
    }
}
