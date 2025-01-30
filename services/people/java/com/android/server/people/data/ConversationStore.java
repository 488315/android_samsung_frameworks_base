package com.android.server.people.data;

import android.content.LocusId;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.IndentingPrintWriter;
import com.google.android.collect.Lists;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class ConversationStore {
  public static final String TAG = "ConversationStore";
  public ConversationInfosProtoDiskReadWriter mConversationInfosProtoDiskReadWriter;
  public final File mPackageDir;
  public final ScheduledExecutorService mScheduledExecutorService;
  public final Map mConversationInfoMap = new ArrayMap();
  public final Map mLocusIdToShortcutIdMap = new ArrayMap();
  public final Map mContactUriToShortcutIdMap = new ArrayMap();
  public final Map mPhoneNumberToShortcutIdMap = new ArrayMap();
  public final Map mNotifChannelIdToShortcutIdMap = new ArrayMap();

  public ConversationStore(File file, ScheduledExecutorService scheduledExecutorService) {
    this.mScheduledExecutorService = scheduledExecutorService;
    this.mPackageDir = file;
  }

  public void loadConversationsFromDisk() {
    List list;
    ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter =
        getConversationInfosProtoDiskReadWriter();
    if (conversationInfosProtoDiskReadWriter == null
        || (list = (List) conversationInfosProtoDiskReadWriter.read("conversations")) == null) {
      return;
    }
    Iterator it = list.iterator();
    while (it.hasNext()) {
      updateConversationsInMemory((ConversationInfo) it.next());
    }
  }

  public void saveConversationsToDisk() {
    ArrayList arrayList;
    ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter =
        getConversationInfosProtoDiskReadWriter();
    if (conversationInfosProtoDiskReadWriter != null) {
      synchronized (this) {
        arrayList = new ArrayList(this.mConversationInfoMap.values());
      }
      conversationInfosProtoDiskReadWriter.saveConversationsImmediately(arrayList);
    }
  }

  public void addOrUpdate(ConversationInfo conversationInfo) {
    updateConversationsInMemory(conversationInfo);
    scheduleUpdateConversationsOnDisk();
  }

  public ConversationInfo deleteConversation(String str) {
    synchronized (this) {
      ConversationInfo conversationInfo = (ConversationInfo) this.mConversationInfoMap.remove(str);
      if (conversationInfo == null) {
        return null;
      }
      LocusId locusId = conversationInfo.getLocusId();
      if (locusId != null) {
        this.mLocusIdToShortcutIdMap.remove(locusId);
      }
      Uri contactUri = conversationInfo.getContactUri();
      if (contactUri != null) {
        this.mContactUriToShortcutIdMap.remove(contactUri);
      }
      String contactPhoneNumber = conversationInfo.getContactPhoneNumber();
      if (contactPhoneNumber != null) {
        this.mPhoneNumberToShortcutIdMap.remove(contactPhoneNumber);
      }
      String notificationChannelId = conversationInfo.getNotificationChannelId();
      if (notificationChannelId != null) {
        this.mNotifChannelIdToShortcutIdMap.remove(notificationChannelId);
      }
      scheduleUpdateConversationsOnDisk();
      return conversationInfo;
    }
  }

  public void forAllConversations(Consumer consumer) {
    ArrayList arrayList;
    synchronized (this) {
      arrayList = new ArrayList(this.mConversationInfoMap.values());
    }
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      consumer.accept((ConversationInfo) it.next());
    }
  }

  public synchronized ConversationInfo getConversation(String str) {
    return str != null ? (ConversationInfo) this.mConversationInfoMap.get(str) : null;
  }

  public synchronized ConversationInfo getConversationByLocusId(LocusId locusId) {
    return getConversation((String) this.mLocusIdToShortcutIdMap.get(locusId));
  }

  public synchronized ConversationInfo getConversationByContactUri(Uri uri) {
    return getConversation((String) this.mContactUriToShortcutIdMap.get(uri));
  }

  public synchronized ConversationInfo getConversationByPhoneNumber(String str) {
    return getConversation((String) this.mPhoneNumberToShortcutIdMap.get(str));
  }

  public void onDestroy() {
    synchronized (this) {
      this.mConversationInfoMap.clear();
      this.mContactUriToShortcutIdMap.clear();
      this.mLocusIdToShortcutIdMap.clear();
      this.mNotifChannelIdToShortcutIdMap.clear();
      this.mPhoneNumberToShortcutIdMap.clear();
    }
    ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter =
        getConversationInfosProtoDiskReadWriter();
    if (conversationInfosProtoDiskReadWriter != null) {
      conversationInfosProtoDiskReadWriter.deleteConversationsFile();
    }
  }

  public byte[] getBackupPayload() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    forAllConversations(
        new Consumer() { // from class:
          // com.android.server.people.data.ConversationStore$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            ConversationStore.lambda$getBackupPayload$0(dataOutputStream, (ConversationInfo) obj);
          }
        });
    try {
      dataOutputStream.writeInt(-1);
      return byteArrayOutputStream.toByteArray();
    } catch (IOException e) {
      Slog.e(TAG, "Failed to write conversation infos end token to backup payload.", e);
      return null;
    }
  }

  public static /* synthetic */ void lambda$getBackupPayload$0(
      DataOutputStream dataOutputStream, ConversationInfo conversationInfo) {
    byte[] backupPayload = conversationInfo.getBackupPayload();
    if (backupPayload == null) {
      return;
    }
    try {
      dataOutputStream.writeInt(backupPayload.length);
      dataOutputStream.write(backupPayload);
    } catch (IOException e) {
      Slog.e(TAG, "Failed to write conversation info to backup payload.", e);
    }
  }

  public void restore(byte[] bArr) {
    DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
    try {
      for (int readInt = dataInputStream.readInt();
          readInt != -1;
          readInt = dataInputStream.readInt()) {
        byte[] bArr2 = new byte[readInt];
        dataInputStream.readFully(bArr2, 0, readInt);
        ConversationInfo readFromBackupPayload = ConversationInfo.readFromBackupPayload(bArr2);
        if (readFromBackupPayload != null) {
          addOrUpdate(readFromBackupPayload);
        }
      }
    } catch (IOException e) {
      Slog.e(TAG, "Failed to read conversation info from payload.", e);
    }
  }

  public final synchronized void updateConversationsInMemory(ConversationInfo conversationInfo) {
    this.mConversationInfoMap.put(conversationInfo.getShortcutId(), conversationInfo);
    LocusId locusId = conversationInfo.getLocusId();
    if (locusId != null) {
      this.mLocusIdToShortcutIdMap.put(locusId, conversationInfo.getShortcutId());
    }
    Uri contactUri = conversationInfo.getContactUri();
    if (contactUri != null) {
      this.mContactUriToShortcutIdMap.put(contactUri, conversationInfo.getShortcutId());
    }
    String contactPhoneNumber = conversationInfo.getContactPhoneNumber();
    if (contactPhoneNumber != null) {
      this.mPhoneNumberToShortcutIdMap.put(contactPhoneNumber, conversationInfo.getShortcutId());
    }
    String notificationChannelId = conversationInfo.getNotificationChannelId();
    if (notificationChannelId != null) {
      this.mNotifChannelIdToShortcutIdMap.put(
          notificationChannelId, conversationInfo.getShortcutId());
    }
  }

  public final void scheduleUpdateConversationsOnDisk() {
    ArrayList arrayList;
    ConversationInfosProtoDiskReadWriter conversationInfosProtoDiskReadWriter =
        getConversationInfosProtoDiskReadWriter();
    if (conversationInfosProtoDiskReadWriter != null) {
      synchronized (this) {
        arrayList = new ArrayList(this.mConversationInfoMap.values());
      }
      conversationInfosProtoDiskReadWriter.scheduleConversationsSave(arrayList);
    }
  }

  public final ConversationInfosProtoDiskReadWriter getConversationInfosProtoDiskReadWriter() {
    if (!this.mPackageDir.exists()) {
      Slog.e(TAG, "Package data directory does not exist: " + this.mPackageDir.getAbsolutePath());
      return null;
    }
    if (this.mConversationInfosProtoDiskReadWriter == null) {
      this.mConversationInfosProtoDiskReadWriter =
          new ConversationInfosProtoDiskReadWriter(
              this.mPackageDir, "conversations", this.mScheduledExecutorService);
    }
    return this.mConversationInfosProtoDiskReadWriter;
  }

  public class ConversationInfosProtoDiskReadWriter extends AbstractProtoDiskReadWriter {
    public final String mConversationInfoFileName;

    public ConversationInfosProtoDiskReadWriter(
        File file, String str, ScheduledExecutorService scheduledExecutorService) {
      super(file, scheduledExecutorService);
      this.mConversationInfoFileName = str;
    }

    @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
    public AbstractProtoDiskReadWriter.ProtoStreamWriter protoStreamWriter() {
      return new AbstractProtoDiskReadWriter.ProtoStreamWriter() { // from class:
        // com.android.server.people.data.ConversationStore$ConversationInfosProtoDiskReadWriter$$ExternalSyntheticLambda0
        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamWriter
        public final void write(ProtoOutputStream protoOutputStream, Object obj) {
          ConversationStore.ConversationInfosProtoDiskReadWriter.lambda$protoStreamWriter$0(
              protoOutputStream, (List) obj);
        }
      };
    }

    public static /* synthetic */ void lambda$protoStreamWriter$0(
        ProtoOutputStream protoOutputStream, List list) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        ConversationInfo conversationInfo = (ConversationInfo) it.next();
        long start = protoOutputStream.start(2246267895809L);
        conversationInfo.writeToProto(protoOutputStream);
        protoOutputStream.end(start);
      }
    }

    @Override // com.android.server.people.data.AbstractProtoDiskReadWriter
    public AbstractProtoDiskReadWriter.ProtoStreamReader protoStreamReader() {
      return new AbstractProtoDiskReadWriter.ProtoStreamReader() { // from class:
        // com.android.server.people.data.ConversationStore$ConversationInfosProtoDiskReadWriter$$ExternalSyntheticLambda1
        @Override // com.android.server.people.data.AbstractProtoDiskReadWriter.ProtoStreamReader
        public final Object read(ProtoInputStream protoInputStream) {
          List lambda$protoStreamReader$1;
          lambda$protoStreamReader$1 =
              ConversationStore.ConversationInfosProtoDiskReadWriter.lambda$protoStreamReader$1(
                  protoInputStream);
          return lambda$protoStreamReader$1;
        }
      };
    }

    public static /* synthetic */ List lambda$protoStreamReader$1(
        ProtoInputStream protoInputStream) {
      ArrayList newArrayList = Lists.newArrayList();
      while (protoInputStream.nextField() != -1) {
        try {
          if (protoInputStream.getFieldNumber() == 1) {
            long start = protoInputStream.start(2246267895809L);
            ConversationInfo readFromProto = ConversationInfo.readFromProto(protoInputStream);
            protoInputStream.end(start);
            newArrayList.add(readFromProto);
          }
        } catch (IOException e) {
          Slog.e(ConversationStore.TAG, "Failed to read protobuf input stream.", e);
        }
      }
      return newArrayList;
    }

    public void scheduleConversationsSave(List list) {
      scheduleSave(this.mConversationInfoFileName, list);
    }

    public void saveConversationsImmediately(List list) {
      saveImmediately(this.mConversationInfoFileName, list);
    }

    public void deleteConversationsFile() {
      delete(this.mConversationInfoFileName);
    }
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.printPair(
        "mConversationInfoMap", Integer.valueOf(this.mConversationInfoMap.size()));
    indentingPrintWriter.printPair(
        "mLocusIdToShortcutIdMap", Integer.valueOf(this.mLocusIdToShortcutIdMap.size()));
    indentingPrintWriter.printPair(
        "mContactUriToShortcutIdMap", Integer.valueOf(this.mContactUriToShortcutIdMap.size()));
    indentingPrintWriter.printPair(
        "mPhoneNumberToShortcutIdMap", Integer.valueOf(this.mPhoneNumberToShortcutIdMap.size()));
    indentingPrintWriter.printPair(
        "mNotifChannelIdToShortcutIdMap",
        Integer.valueOf(this.mNotifChannelIdToShortcutIdMap.size()));
  }
}
