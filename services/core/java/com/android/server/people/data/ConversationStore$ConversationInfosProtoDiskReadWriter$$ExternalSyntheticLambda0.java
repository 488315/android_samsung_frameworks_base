package com.android.server.people.data;

import android.net.Uri;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import com.google.android.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class ConversationStore$ConversationInfosProtoDiskReadWriter$$ExternalSyntheticLambda0
        implements AbstractProtoDiskReadWriter.ProtoStreamWriter,
                AbstractProtoDiskReadWriter.ProtoStreamReader {
    @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader
    public Object read(ProtoInputStream protoInputStream) {
        ArrayList newArrayList = Lists.newArrayList();
        while (protoInputStream.nextField() != -1) {
            try {
                if (protoInputStream.getFieldNumber() == 1) {
                    long start = protoInputStream.start(2246267895809L);
                    ConversationInfo readFromProto =
                            ConversationInfo.readFromProto(protoInputStream);
                    protoInputStream.end(start);
                    newArrayList.add(readFromProto);
                }
            } catch (IOException e) {
                Slog.e("ConversationStore", "Failed to read protobuf input stream.", e);
            }
        }
        return newArrayList;
    }

    @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter
    public void write(ProtoOutputStream protoOutputStream, Object obj) {
        for (ConversationInfo conversationInfo : (List) obj) {
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1138166333441L, conversationInfo.mShortcutId);
            if (conversationInfo.mLocusId != null) {
                long start2 = protoOutputStream.start(1146756268034L);
                protoOutputStream.write(1138166333441L, conversationInfo.mLocusId.getId());
                protoOutputStream.end(start2);
            }
            Uri uri = conversationInfo.mContactUri;
            if (uri != null) {
                protoOutputStream.write(1138166333443L, uri.toString());
            }
            String str = conversationInfo.mNotificationChannelId;
            if (str != null) {
                protoOutputStream.write(1138166333444L, str);
            }
            String str2 = conversationInfo.mParentNotificationChannelId;
            if (str2 != null) {
                protoOutputStream.write(1138166333448L, str2);
            }
            protoOutputStream.write(1112396529673L, conversationInfo.mLastEventTimestamp);
            protoOutputStream.write(1112396529674L, conversationInfo.mCreationTimestamp);
            protoOutputStream.write(1120986464261L, conversationInfo.mShortcutFlags);
            protoOutputStream.write(1120986464262L, conversationInfo.mConversationFlags);
            String str3 = conversationInfo.mContactPhoneNumber;
            if (str3 != null) {
                protoOutputStream.write(1138166333447L, str3);
            }
            protoOutputStream.end(start);
        }
    }
}
