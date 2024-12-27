package com.android.systemui.statusbar.notification.logging.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                i2 = CodedOutputByteBufferNano.computeMessageSize(1, notifications$Notification) + i2;
            }
            i++;
        }
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
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

    @Override // com.google.protobuf.nano.MessageNano
    public Notifications$NotificationList mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                Notifications$Notification[] notifications$NotificationArr = this.notifications;
                int length = notifications$NotificationArr == null ? 0 : notifications$NotificationArr.length;
                int i = repeatedFieldArrayLength + length;
                Notifications$Notification[] notifications$NotificationArr2 = new Notifications$Notification[i];
                if (length != 0) {
                    System.arraycopy(notifications$NotificationArr, 0, notifications$NotificationArr2, 0, length);
                }
                while (length < i - 1) {
                    Notifications$Notification notifications$Notification = new Notifications$Notification();
                    notifications$NotificationArr2[length] = notifications$Notification;
                    codedInputByteBufferNano.readMessage(notifications$Notification);
                    codedInputByteBufferNano.readTag();
                    length++;
                }
                Notifications$Notification notifications$Notification2 = new Notifications$Notification();
                notifications$NotificationArr2[length] = notifications$Notification2;
                codedInputByteBufferNano.readMessage(notifications$Notification2);
                this.notifications = notifications$NotificationArr2;
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                return this;
            }
        }
    }
}
