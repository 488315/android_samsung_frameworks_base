package com.android.server.notification;

import android.app.NotificationHistory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class NotificationHistoryProtoHelper {
    public static void read(
            InputStream inputStream,
            NotificationHistory notificationHistory,
            NotificationHistoryFilter notificationHistoryFilter) {
        ProtoInputStream protoInputStream = new ProtoInputStream(inputStream);
        ArrayList arrayList = new ArrayList();
        while (true) {
            int nextField = protoInputStream.nextField();
            if (nextField == -1) {
                break;
            }
            if (nextField == 1) {
                long start = protoInputStream.start(1146756268033L);
                arrayList =
                        protoInputStream.nextField(1120986464257L)
                                ? new ArrayList(protoInputStream.readInt(1120986464257L))
                                : new ArrayList();
                while (protoInputStream.nextField() != -1) {
                    if (protoInputStream.getFieldNumber() == 2) {
                        arrayList.add(protoInputStream.readString(2237677961218L));
                    }
                }
                protoInputStream.end(start);
            } else if (nextField == 3) {
                long start2 = protoInputStream.start(2246267895811L);
                try {
                    try {
                        NotificationHistory.HistoricalNotification readNotification =
                                readNotification(protoInputStream, arrayList);
                        if (notificationHistoryFilter.mSbnKey != null) {
                            if (notificationHistoryFilter.matchesPackageAndSbnKeyFilter(
                                            readNotification)
                                    && notificationHistory.getHistoryCount()
                                            < notificationHistoryFilter.mNotificationCount) {
                                notificationHistory.addNotificationToWrite(readNotification);
                            }
                        } else if (notificationHistoryFilter.matchesPackageAndChannelFilter(
                                        readNotification)
                                && notificationHistory.getHistoryCount()
                                        < notificationHistoryFilter.mNotificationCount) {
                            notificationHistory.addNotificationToWrite(readNotification);
                        }
                    } catch (Exception e) {
                        Slog.e("NotifHistoryProto", "Error reading notification", e);
                    }
                    protoInputStream.end(start2);
                } catch (Throwable th) {
                    protoInputStream.end(start2);
                    throw th;
                }
            }
        }
        if (notificationHistoryFilter.mPackage == null
                && notificationHistoryFilter.mChannel == null
                && notificationHistoryFilter.mNotificationCount >= Integer.MAX_VALUE) {
            notificationHistory.addPooledStrings(arrayList);
        } else {
            notificationHistory.poolStringsFromNotifications();
        }
    }

    public static NotificationHistory.HistoricalNotification readNotification(
            ProtoInputStream protoInputStream, List list) {
        String str;
        String str2;
        NotificationHistory.HistoricalNotification.Builder builder =
                new NotificationHistory.HistoricalNotification.Builder();
        String str3 = null;
        while (true) {
            switch (protoInputStream.nextField()) {
                case -1:
                    break;
                case 0:
                default:
                    str = str3;
                    str3 = str;
                case 1:
                    str3 = protoInputStream.readString(1138166333441L);
                    builder.setPackage(str3);
                    list.add(str3);
                case 2:
                    str3 = (String) list.get(protoInputStream.readInt(1120986464258L) - 1);
                    builder.setPackage(str3);
                case 3:
                    str = str3;
                    String readString = protoInputStream.readString(1138166333443L);
                    builder.setChannelName(readString);
                    list.add(readString);
                    str3 = str;
                case 4:
                    str = str3;
                    builder.setChannelName(
                            (String) list.get(protoInputStream.readInt(1120986464260L) - 1));
                    str3 = str;
                case 5:
                    str = str3;
                    String readString2 = protoInputStream.readString(1138166333445L);
                    builder.setChannelId(readString2);
                    list.add(readString2);
                    str3 = str;
                case 6:
                    str = str3;
                    builder.setChannelId(
                            (String) list.get(protoInputStream.readInt(1120986464262L) - 1));
                    str3 = str;
                case 7:
                    str = str3;
                    builder.setUid(protoInputStream.readInt(1120986464263L));
                    str3 = str;
                case 8:
                    str = str3;
                    builder.setUserId(protoInputStream.readInt(1120986464264L));
                    str3 = str;
                case 9:
                    str = str3;
                    builder.setPostedTimeMs(protoInputStream.readLong(1112396529673L));
                    str3 = str;
                case 10:
                    str = str3;
                    builder.setTitle(protoInputStream.readString(1138166333450L));
                    str3 = str;
                case 11:
                    str = str3;
                    builder.setText(protoInputStream.readString(1138166333451L));
                    str3 = str;
                case 12:
                    long start = protoInputStream.start(1146756268044L);
                    int i = 0;
                    int i2 = 0;
                    int i3 = 0;
                    int i4 = 0;
                    byte[] bArr = null;
                    String str4 = null;
                    String str5 = null;
                    while (true) {
                        switch (protoInputStream.nextField()) {
                            case -1:
                                break;
                            case 0:
                            default:
                                str2 = str3;
                                str3 = str2;
                            case 1:
                                str2 = str3;
                                i = protoInputStream.readInt(1159641169921L);
                                str3 = str2;
                            case 2:
                                str2 = str3;
                                protoInputStream.readString(1138166333442L);
                                str3 = str2;
                            case 3:
                                str2 = str3;
                                i2 = protoInputStream.readInt(1120986464259L);
                                str3 = str2;
                            case 4:
                                str2 = str3;
                                str5 = protoInputStream.readString(1138166333444L);
                                str3 = str2;
                            case 5:
                                str2 = str3;
                                bArr = protoInputStream.readBytes(1151051235333L);
                                str3 = str2;
                            case 6:
                                str2 = str3;
                                i4 = protoInputStream.readInt(1120986464262L);
                                str3 = str2;
                            case 7:
                                str2 = str3;
                                i3 = protoInputStream.readInt(1120986464263L);
                                str3 = str2;
                            case 8:
                                str2 = str3;
                                str4 = protoInputStream.readString(1138166333448L);
                                str3 = str2;
                        }
                        str = str3;
                        if (i == 3) {
                            if (bArr != null) {
                                try {
                                    builder.setIcon(Icon.createWithData(bArr, i3, i4));
                                } catch (IllegalArgumentException e) {
                                    Slog.d(
                                            "NotifHistoryProto",
                                            "loadIcon IllegalArgumentException " + e);
                                    builder.setIcon(Icon.createWithResource("", 17304445));
                                }
                            }
                        } else if (i == 2) {
                            if (i2 != 0) {
                                builder.setIcon(
                                        Icon.createWithResource(str5 != null ? str5 : str, i2));
                            }
                        } else if (i == 4 && str4 != null) {
                            builder.setIcon(Icon.createWithContentUri(str4));
                        }
                        protoInputStream.end(start);
                        str3 = str;
                    }
                case 13:
                    String readString3 = protoInputStream.readString(1138166333453L);
                    builder.setConversationId(readString3);
                    list.add(readString3);
                    str = str3;
                    str3 = str;
                case 14:
                    builder.setConversationId(
                            (String) list.get(protoInputStream.readInt(1120986464270L) - 1));
                    str = str3;
                    str3 = str;
                case 15:
                    builder.setSbnKey(protoInputStream.readString(1138166333455L));
                    str = str3;
                    str3 = str;
                case 16:
                    builder.setType(protoInputStream.readInt(1120986464272L));
                    str = str3;
                    str3 = str;
                case 17:
                    builder.setChecked(protoInputStream.readBoolean(1133871366161L));
                    str = str3;
                    str3 = str;
                case 18:
                    String readString4 = protoInputStream.readString(1138166333458L);
                    builder.setUri(readString4 == null ? null : Uri.parse(readString4));
                    str = str3;
                    str3 = str;
                case 19:
                    builder.setWhen(protoInputStream.readLong(1112396529683L));
                    str = str3;
                    str3 = str;
                case 20:
                    builder.setExtraTitle(protoInputStream.readString(1138166333460L));
                    str = str3;
                    str3 = str;
            }
            return builder.build();
        }
    }

    public static void write(
            OutputStream outputStream, NotificationHistory notificationHistory, int i) {
        List list;
        ProtoOutputStream protoOutputStream = new ProtoOutputStream(outputStream);
        long j = 1120986464258L;
        protoOutputStream.write(1120986464258L, i);
        long start = protoOutputStream.start(1146756268033L);
        String[] pooledStringsToWrite = notificationHistory.getPooledStringsToWrite();
        protoOutputStream.write(1120986464257L, pooledStringsToWrite.length);
        int i2 = 0;
        for (String str : pooledStringsToWrite) {
            protoOutputStream.write(2237677961218L, str);
        }
        protoOutputStream.end(start);
        List notificationsToWrite = notificationHistory.getNotificationsToWrite();
        int size = notificationsToWrite.size();
        while (i2 < size) {
            String[] pooledStringsToWrite2 = notificationHistory.getPooledStringsToWrite();
            NotificationHistory.HistoricalNotification historicalNotification =
                    (NotificationHistory.HistoricalNotification) notificationsToWrite.get(i2);
            long start2 = protoOutputStream.start(2246267895811L);
            try {
                try {
                    int binarySearch =
                            Arrays.binarySearch(
                                    pooledStringsToWrite2, historicalNotification.getPackage());
                    if (binarySearch >= 0) {
                        protoOutputStream.write(j, binarySearch + 1);
                    } else {
                        Slog.w(
                                "NotifHistoryProto",
                                "notification package name ("
                                        + historicalNotification.getPackage()
                                        + ") not found in string cache");
                        protoOutputStream.write(
                                1138166333441L, historicalNotification.getPackage());
                    }
                    int binarySearch2 =
                            Arrays.binarySearch(
                                    pooledStringsToWrite2, historicalNotification.getChannelName());
                    if (binarySearch2 >= 0) {
                        list = notificationsToWrite;
                        try {
                            protoOutputStream.write(1120986464260L, binarySearch2 + 1);
                        } catch (Exception e) {
                            e = e;
                            Slog.e("NotifHistoryProto", "Error writing notification -", e);
                            protoOutputStream.end(start2);
                            i2++;
                            notificationsToWrite = list;
                            j = 1120986464258L;
                        }
                    } else {
                        list = notificationsToWrite;
                        protoOutputStream.write(
                                1138166333443L, historicalNotification.getChannelName());
                    }
                    int binarySearch3 =
                            Arrays.binarySearch(
                                    pooledStringsToWrite2, historicalNotification.getChannelId());
                    if (binarySearch3 >= 0) {
                        protoOutputStream.write(1120986464262L, binarySearch3 + 1);
                    } else {
                        protoOutputStream.write(
                                1138166333445L, historicalNotification.getChannelId());
                    }
                    if (!TextUtils.isEmpty(historicalNotification.getConversationId())) {
                        int binarySearch4 =
                                Arrays.binarySearch(
                                        pooledStringsToWrite2,
                                        historicalNotification.getConversationId());
                        if (binarySearch4 >= 0) {
                            protoOutputStream.write(1120986464270L, binarySearch4 + 1);
                        } else {
                            Slog.w(
                                    "NotifHistoryProto",
                                    "notification conversation id ("
                                            + historicalNotification.getConversationId()
                                            + ") not found in string cache");
                            protoOutputStream.write(
                                    1138166333453L, historicalNotification.getConversationId());
                        }
                    }
                    protoOutputStream.write(1120986464263L, historicalNotification.getUid());
                    protoOutputStream.write(1120986464264L, historicalNotification.getUserId());
                    protoOutputStream.write(
                            1112396529673L, historicalNotification.getPostedTimeMs());
                    protoOutputStream.write(1138166333450L, historicalNotification.getTitle());
                    protoOutputStream.write(1138166333451L, historicalNotification.getText());
                    protoOutputStream.write(1138166333455L, historicalNotification.getSbnKey());
                    protoOutputStream.write(1120986464272L, historicalNotification.getType());
                    protoOutputStream.write(1133871366161L, historicalNotification.getChecked());
                    Uri uri = historicalNotification.getUri();
                    if (uri != null) {
                        protoOutputStream.write(1138166333458L, uri.toString());
                    } else {
                        protoOutputStream.write(1138166333458L, "null");
                    }
                    protoOutputStream.write(1112396529683L, historicalNotification.getWhen());
                    protoOutputStream.write(1138166333460L, historicalNotification.getExtraTitle());
                    writeIcon(protoOutputStream, historicalNotification);
                } catch (Exception e2) {
                    e = e2;
                    list = notificationsToWrite;
                }
                protoOutputStream.end(start2);
                i2++;
                notificationsToWrite = list;
                j = 1120986464258L;
            } catch (Throwable th) {
                protoOutputStream.end(start2);
                throw th;
            }
        }
        protoOutputStream.flush();
    }

    public static void writeIcon(
            ProtoOutputStream protoOutputStream,
            NotificationHistory.HistoricalNotification historicalNotification) {
        long start = protoOutputStream.start(1146756268044L);
        try {
            try {
                protoOutputStream.write(1159641169921L, historicalNotification.getIcon().getType());
                int type = historicalNotification.getIcon().getType();
                if (type == 2) {
                    protoOutputStream.write(
                            1120986464259L, historicalNotification.getIcon().getResId());
                    if (!historicalNotification
                            .getPackage()
                            .equals(historicalNotification.getIcon().getResPackage())) {
                        protoOutputStream.write(
                                1138166333444L, historicalNotification.getIcon().getResPackage());
                    }
                } else if (type == 3) {
                    protoOutputStream.write(
                            1151051235333L, historicalNotification.getIcon().getDataBytes());
                    protoOutputStream.write(
                            1120986464262L, historicalNotification.getIcon().getDataLength());
                    protoOutputStream.write(
                            1120986464263L, historicalNotification.getIcon().getDataOffset());
                } else if (type == 4) {
                    protoOutputStream.write(
                            1138166333448L, historicalNotification.getIcon().getUriString());
                }
            } catch (Exception e) {
                Slog.e("NotifHistoryProto", "Error writing notification icon -", e);
            }
            protoOutputStream.end(start);
        } catch (Throwable th) {
            protoOutputStream.end(start);
            throw th;
        }
    }
}
