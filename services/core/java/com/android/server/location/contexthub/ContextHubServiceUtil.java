package com.android.server.location.contexthub;

import android.hardware.contexthub.ContextHubMessage;
import android.hardware.contexthub.V1_0.ContextHubMsg;
import android.hardware.location.NanoAppMessage;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class ContextHubServiceUtil {
    public static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());

    public static ContextHubMessage createAidlContextHubMessage(
            short s, NanoAppMessage nanoAppMessage) {
        ContextHubMessage contextHubMessage = new ContextHubMessage();
        contextHubMessage.nanoappId = nanoAppMessage.getNanoAppId();
        contextHubMessage.hostEndPoint = (char) s;
        contextHubMessage.messageType = nanoAppMessage.getMessageType();
        contextHubMessage.messageBody = nanoAppMessage.getMessageBody();
        contextHubMessage.permissions = new String[0];
        contextHubMessage.isReliable = nanoAppMessage.isReliable();
        contextHubMessage.messageSequenceNumber = nanoAppMessage.getMessageSequenceNumber();
        return contextHubMessage;
    }

    public static NanoAppMessage createNanoAppMessage(ContextHubMsg contextHubMsg) {
        ArrayList arrayList = contextHubMsg.msg;
        byte[] bArr = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            bArr[i] = ((Byte) arrayList.get(i)).byteValue();
        }
        return NanoAppMessage.createMessageFromNanoApp(
                contextHubMsg.appName,
                contextHubMsg.msgType,
                bArr,
                contextHubMsg.hostEndPoint == -1);
    }

    public static String formatDateFromTimestamp(long j) {
        return DATE_FORMATTER.format(Instant.ofEpochMilli(j));
    }

    public static int toTransactionResult(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 5) {
            return 4;
        }
        int i2 = 2;
        if (i != 2) {
            i2 = 3;
            if (i != 3) {
                return 1;
            }
        }
        return i2;
    }
}
