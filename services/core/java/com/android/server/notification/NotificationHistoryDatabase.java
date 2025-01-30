package com.android.server.notification;

import android.app.NotificationHistory;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.AtomicFile;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.notification.NotificationHistoryFilter;
import com.samsung.android.server.notification.NotificationHistoryImageProvider;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class NotificationHistoryDatabase {
    public static final boolean DEBUG = NotificationManagerService.DBG;
    public final Handler mFileWriteHandler;
    public final File mHistoryDir;
    public final File mVersionFile;
    public final Object mLock = new Object();
    public ArrayList mUris = new ArrayList();
    public int mCurrentVersion = 1;
    final List mHistoryFiles = new ArrayList();
    NotificationHistory mBuffer = new NotificationHistory();
    public final WriteBufferRunnable mWriteBufferRunnable = new WriteBufferRunnable();

    public NotificationHistoryDatabase(Handler handler, File file) {
        this.mFileWriteHandler = handler;
        this.mVersionFile = new File(file, "version");
        this.mHistoryDir = new File(file, "history");
    }

    public void init() {
        synchronized (this.mLock) {
            try {
                if (!this.mHistoryDir.exists() && !this.mHistoryDir.mkdir()) {
                    throw new IllegalStateException("could not create history directory");
                }
                this.mVersionFile.createNewFile();
            } catch (Exception e) {
                Slog.e("NotiHistoryDatabase", "could not create needed files", e);
            }
            checkVersionAndBuildLocked();
            indexFilesLocked();
            prune();
        }
    }

    public final void indexFilesLocked() {
        this.mHistoryFiles.clear();
        File[] listFiles = this.mHistoryDir.listFiles();
        if (listFiles == null) {
            return;
        }
        Arrays.sort(listFiles, new Comparator() { // from class: com.android.server.notification.NotificationHistoryDatabase$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$indexFilesLocked$0;
                lambda$indexFilesLocked$0 = NotificationHistoryDatabase.lambda$indexFilesLocked$0((File) obj, (File) obj2);
                return lambda$indexFilesLocked$0;
            }
        });
        for (File file : listFiles) {
            this.mHistoryFiles.add(new AtomicFile(file));
        }
    }

    public static /* synthetic */ int lambda$indexFilesLocked$0(File file, File file2) {
        return Long.compare(safeParseLong(file2.getName()), safeParseLong(file.getName()));
    }

    public final void checkVersionAndBuildLocked() {
        int i;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.mVersionFile));
            try {
                i = Integer.parseInt(bufferedReader.readLine());
                bufferedReader.close();
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | NumberFormatException unused) {
            i = 0;
        }
        if (i == this.mCurrentVersion || !this.mVersionFile.exists()) {
            return;
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.mVersionFile));
            try {
                bufferedWriter.write(Integer.toString(this.mCurrentVersion));
                bufferedWriter.write(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                bufferedWriter.flush();
                bufferedWriter.close();
            } finally {
            }
        } catch (IOException e) {
            Slog.e("NotiHistoryDatabase", "Failed to write new version");
            throw new RuntimeException(e);
        }
    }

    public void forceWriteToDisk() {
        this.mFileWriteHandler.post(this.mWriteBufferRunnable);
    }

    public void onPackageRemoved(String str) {
        this.mFileWriteHandler.post(new RemovePackageRunnable(str));
    }

    public void deleteNotificationHistoryItem(String str, long j) {
        this.mFileWriteHandler.post(new RemoveNotificationRunnable(str, j));
    }

    public void deleteConversations(String str, Set set) {
        this.mFileWriteHandler.post(new RemoveConversationRunnable(str, set));
    }

    public void deleteNotificationChannel(String str, String str2) {
        this.mFileWriteHandler.post(new RemoveChannelRunnable(str, str2));
    }

    public void addNotification(NotificationHistory.HistoricalNotification historicalNotification) {
        synchronized (this.mLock) {
            this.mBuffer.addNewNotificationToWrite(historicalNotification);
            Uri uri = historicalNotification.getUri();
            if (uri != null) {
                this.mUris.add(uri.toString());
            }
            if (this.mBuffer.getHistoryCount() == 1) {
                this.mFileWriteHandler.postDelayed(this.mWriteBufferRunnable, 1200000L);
            }
        }
    }

    public NotificationHistory readNotificationHistory() {
        NotificationHistory notificationHistory;
        synchronized (this.mLock) {
            notificationHistory = new NotificationHistory();
            notificationHistory.addNotificationsToWrite(this.mBuffer);
            for (AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    readLocked(atomicFile, notificationHistory, new NotificationHistoryFilter.Builder().build());
                } catch (Exception e) {
                    Slog.e("NotiHistoryDatabase", "error reading " + atomicFile.getBaseFile().getAbsolutePath(), e);
                }
            }
        }
        return notificationHistory;
    }

    public void updateNotificationItems(int i, NotificationHistory.HistoricalNotification historicalNotification) {
        String sbnKey = historicalNotification.getSbnKey();
        String text = historicalNotification.getText();
        Uri uri = historicalNotification.getUri();
        if (i == 0) {
            this.mFileWriteHandler.post(new RemoveImageRunnable(sbnKey, text, uri));
        }
    }

    public void updateCancelEvent(String str, boolean z) {
        synchronized (this.mLock) {
            this.mBuffer.updateNotificationToWrite(str, z);
            for (AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    NotificationHistory notificationHistory = new NotificationHistory();
                    readLocked(atomicFile, notificationHistory, new NotificationHistoryFilter.Builder().build());
                    if (notificationHistory.updateNotificationToWrite(str, z)) {
                        writeLocked(atomicFile, notificationHistory);
                    }
                } catch (Exception e) {
                    Slog.e("NotiHistoryDatabase", "Cannot clean up file on conversation removal " + atomicFile.getBaseFile().getName(), e);
                }
            }
        }
    }

    public NotificationHistory readNotificationHistoryForPackage(String str, String str2, int i) {
        NotificationHistory notificationHistory;
        synchronized (this.mLock) {
            notificationHistory = new NotificationHistory();
            notificationHistory.addNotificationsToWrite(this.mBuffer, str2, i);
            for (AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    readLocked(atomicFile, notificationHistory, new NotificationHistoryFilter.Builder().setPackage(str).setSbnKey(str2).setMaxNotifications(i).build());
                } catch (Exception e) {
                    Slog.e("NotiHistoryDatabase", "error reading " + atomicFile.getBaseFile().getAbsolutePath(), e);
                }
                if (i == notificationHistory.getHistoryCount()) {
                    break;
                }
            }
        }
        return notificationHistory;
    }

    public NotificationHistory readNotificationHistoryForDump(String str, int i) {
        NotificationHistory notificationHistory;
        synchronized (this.mLock) {
            notificationHistory = new NotificationHistory();
            notificationHistory.addNotificationsForDump(this.mBuffer, str, i);
            for (AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    readLocked(atomicFile, notificationHistory, new NotificationHistoryFilter.Builder().setPackage(str).setMaxNotifications(i).build());
                } catch (Exception e) {
                    Slog.e("NotiHistoryDatabase", "error reading(for dump) " + atomicFile.getBaseFile().getAbsolutePath(), e);
                }
                if (i == notificationHistory.getHistoryCount()) {
                    break;
                }
            }
        }
        return notificationHistory;
    }

    public final class RemoveImageRunnable implements Runnable {
        public String mSbnKey;
        public String mText;
        public Uri mUri;

        public RemoveImageRunnable(String str, String str2, Uri uri) {
            this.mSbnKey = str;
            this.mText = str2;
            this.mUri = uri;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                Slog.d("NotiHistoryDatabase", "RemoveImageRunnable");
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                if (!NotificationHistoryDatabase.this.mBuffer.removeImageNotificationsFromWrite(this.mSbnKey, this.mText, this.mUri)) {
                    Iterator it = NotificationHistoryDatabase.this.mHistoryFiles.iterator();
                    if (it.hasNext()) {
                        AtomicFile atomicFile = (AtomicFile) it.next();
                        try {
                            NotificationHistory notificationHistory = new NotificationHistory();
                            NotificationHistoryDatabase.readLocked(atomicFile, notificationHistory, new NotificationHistoryFilter.Builder().build());
                            if (notificationHistory.removeImageNotificationsFromWrite(this.mSbnKey, this.mText, this.mUri)) {
                                NotificationHistoryDatabase.this.writeLocked(atomicFile, notificationHistory);
                            }
                        } catch (Exception e) {
                            Slog.e("NotiHistoryDatabase", "Cannot clean up file on channel removal " + atomicFile.getBaseFile().getName(), e);
                        }
                    }
                }
            }
        }
    }

    public void disableHistory(Context context) {
        synchronized (this.mLock) {
            File[] listFiles = this.mHistoryDir.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    file.delete();
                }
            }
            Iterator it = this.mHistoryFiles.iterator();
            while (it.hasNext()) {
                ((AtomicFile) it.next()).delete();
            }
            this.mHistoryDir.delete();
            this.mHistoryFiles.clear();
            context.getContentResolver().delete(NotificationHistoryImageProvider.CONTENT_URI, null, null);
        }
    }

    public void prune() {
        prune(1, System.currentTimeMillis());
    }

    public void prune(int i, long j) {
        synchronized (this.mLock) {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTimeInMillis(j);
            gregorianCalendar.add(5, i * (-1));
            for (int size = this.mHistoryFiles.size() - 1; size >= 0; size--) {
                AtomicFile atomicFile = (AtomicFile) this.mHistoryFiles.get(size);
                long safeParseLong = safeParseLong(atomicFile.getBaseFile().getName());
                if (DEBUG) {
                    Slog.d("NotiHistoryDatabase", "File " + atomicFile.getBaseFile().getName() + " created on " + safeParseLong);
                }
                if (safeParseLong <= gregorianCalendar.getTimeInMillis()) {
                    deleteFile(atomicFile);
                }
            }
        }
    }

    public void removeFilePathFromHistory(String str) {
        if (str == null) {
            return;
        }
        Iterator it = this.mHistoryFiles.iterator();
        while (it.hasNext()) {
            AtomicFile atomicFile = (AtomicFile) it.next();
            if (atomicFile != null && str.equals(atomicFile.getBaseFile().getAbsolutePath())) {
                it.remove();
                return;
            }
        }
    }

    public final void deleteFile(AtomicFile atomicFile) {
        if (DEBUG) {
            Slog.d("NotiHistoryDatabase", "Removed " + atomicFile.getBaseFile().getName());
        }
        atomicFile.delete();
        removeFilePathFromHistory(atomicFile.getBaseFile().getAbsolutePath());
        NotificationHistoryImageProvider.getInstance().deleteRows(System.currentTimeMillis());
    }

    public final void writeLocked(AtomicFile atomicFile, NotificationHistory notificationHistory) {
        FileOutputStream startWrite = atomicFile.startWrite();
        try {
            NotificationHistoryProtoHelper.write(startWrite, notificationHistory, this.mCurrentVersion);
            atomicFile.finishWrite(startWrite);
            atomicFile.failWrite(null);
        } catch (Throwable th) {
            atomicFile.failWrite(startWrite);
            throw th;
        }
    }

    public static void readLocked(AtomicFile atomicFile, NotificationHistory notificationHistory, NotificationHistoryFilter notificationHistoryFilter) {
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = atomicFile.openRead();
                NotificationHistoryProtoHelper.read(fileInputStream, notificationHistory, notificationHistoryFilter);
            } catch (FileNotFoundException e) {
                Slog.e("NotiHistoryDatabase", "Cannot open " + atomicFile.getBaseFile().getAbsolutePath(), e);
                throw e;
            }
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    public static long safeParseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return -1L;
        }
    }

    public final class WriteBufferRunnable implements Runnable {
        public WriteBufferRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            run(new AtomicFile(new File(NotificationHistoryDatabase.this.mHistoryDir, String.valueOf(System.currentTimeMillis()))));
        }

        public void run(AtomicFile atomicFile) {
            synchronized (NotificationHistoryDatabase.this.mLock) {
                if (NotificationHistoryDatabase.DEBUG) {
                    Slog.d("NotiHistoryDatabase", "WriteBufferRunnable " + atomicFile.getBaseFile().getAbsolutePath());
                }
                try {
                    NotificationHistoryDatabase notificationHistoryDatabase = NotificationHistoryDatabase.this;
                    notificationHistoryDatabase.writeLocked(atomicFile, notificationHistoryDatabase.mBuffer);
                    NotificationHistoryDatabase.this.mHistoryFiles.add(0, atomicFile);
                    NotificationHistoryDatabase.this.mBuffer = new NotificationHistory();
                    NotificationHistoryImageProvider.getInstance().updatePostedTime(System.currentTimeMillis(), NotificationHistoryDatabase.this.mUris);
                    NotificationHistoryDatabase.this.mUris.clear();
                } catch (IOException e) {
                    Slog.e("NotiHistoryDatabase", "Failed to write buffer to disk. not flushing buffer", e);
                }
            }
        }
    }

    public final class RemovePackageRunnable implements Runnable {
        public String mPkg;

        public RemovePackageRunnable(String str) {
            this.mPkg = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                Slog.d("NotiHistoryDatabase", "RemovePackageRunnable " + this.mPkg);
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                NotificationHistoryDatabase.this.mBuffer.removeNotificationsFromWrite(this.mPkg);
                for (AtomicFile atomicFile : NotificationHistoryDatabase.this.mHistoryFiles) {
                    try {
                        NotificationHistory notificationHistory = new NotificationHistory();
                        NotificationHistoryDatabase.readLocked(atomicFile, notificationHistory, new NotificationHistoryFilter.Builder().build());
                        notificationHistory.removeNotificationsFromWrite(this.mPkg);
                        NotificationHistoryDatabase.this.writeLocked(atomicFile, notificationHistory);
                    } catch (Exception e) {
                        Slog.e("NotiHistoryDatabase", "Cannot clean up file on pkg removal " + atomicFile.getBaseFile().getAbsolutePath(), e);
                    }
                }
            }
        }
    }

    public final class RemoveNotificationRunnable implements Runnable {
        public NotificationHistory mNotificationHistory;
        public String mPkg;
        public long mPostedTime;

        public RemoveNotificationRunnable(String str, long j) {
            this.mPkg = str;
            this.mPostedTime = j;
        }

        public void setNotificationHistory(NotificationHistory notificationHistory) {
            this.mNotificationHistory = notificationHistory;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                Slog.d("NotiHistoryDatabase", "RemoveNotificationRunnable");
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                NotificationHistoryDatabase.this.mBuffer.removeNotificationFromWrite(this.mPkg, this.mPostedTime);
                for (AtomicFile atomicFile : NotificationHistoryDatabase.this.mHistoryFiles) {
                    try {
                        NotificationHistory notificationHistory = this.mNotificationHistory;
                        if (notificationHistory == null) {
                            notificationHistory = new NotificationHistory();
                        }
                        NotificationHistoryDatabase.readLocked(atomicFile, notificationHistory, new NotificationHistoryFilter.Builder().build());
                        if (notificationHistory.removeNotificationFromWrite(this.mPkg, this.mPostedTime)) {
                            NotificationHistoryDatabase.this.writeLocked(atomicFile, notificationHistory);
                        }
                    } catch (Exception e) {
                        Slog.e("NotiHistoryDatabase", "Cannot clean up file on notification removal " + atomicFile.getBaseFile().getName(), e);
                    }
                }
            }
        }
    }

    public final class RemoveConversationRunnable implements Runnable {
        public Set mConversationIds;
        public NotificationHistory mNotificationHistory;
        public String mPkg;

        public RemoveConversationRunnable(String str, Set set) {
            this.mPkg = str;
            this.mConversationIds = set;
        }

        public void setNotificationHistory(NotificationHistory notificationHistory) {
            this.mNotificationHistory = notificationHistory;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                Slog.d("NotiHistoryDatabase", "RemoveConversationRunnable " + this.mPkg + " " + this.mConversationIds);
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                NotificationHistoryDatabase.this.mBuffer.removeConversationsFromWrite(this.mPkg, this.mConversationIds);
                for (AtomicFile atomicFile : NotificationHistoryDatabase.this.mHistoryFiles) {
                    try {
                        NotificationHistory notificationHistory = this.mNotificationHistory;
                        if (notificationHistory == null) {
                            notificationHistory = new NotificationHistory();
                        }
                        NotificationHistoryDatabase.readLocked(atomicFile, notificationHistory, new NotificationHistoryFilter.Builder().build());
                        if (notificationHistory.removeConversationsFromWrite(this.mPkg, this.mConversationIds)) {
                            NotificationHistoryDatabase.this.writeLocked(atomicFile, notificationHistory);
                        }
                    } catch (Exception e) {
                        Slog.e("NotiHistoryDatabase", "Cannot clean up file on conversation removal " + atomicFile.getBaseFile().getName(), e);
                    }
                }
            }
        }
    }

    public final class RemoveChannelRunnable implements Runnable {
        public String mChannelId;
        public NotificationHistory mNotificationHistory;
        public String mPkg;

        public RemoveChannelRunnable(String str, String str2) {
            this.mPkg = str;
            this.mChannelId = str2;
        }

        public void setNotificationHistory(NotificationHistory notificationHistory) {
            this.mNotificationHistory = notificationHistory;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                Slog.d("NotiHistoryDatabase", "RemoveChannelRunnable");
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                NotificationHistoryDatabase.this.mBuffer.removeChannelFromWrite(this.mPkg, this.mChannelId);
                for (AtomicFile atomicFile : NotificationHistoryDatabase.this.mHistoryFiles) {
                    try {
                        NotificationHistory notificationHistory = this.mNotificationHistory;
                        if (notificationHistory == null) {
                            notificationHistory = new NotificationHistory();
                        }
                        NotificationHistoryDatabase.readLocked(atomicFile, notificationHistory, new NotificationHistoryFilter.Builder().build());
                        if (notificationHistory.removeChannelFromWrite(this.mPkg, this.mChannelId)) {
                            NotificationHistoryDatabase.this.writeLocked(atomicFile, notificationHistory);
                        }
                    } catch (Exception e) {
                        Slog.e("NotiHistoryDatabase", "Cannot clean up file on channel removal " + atomicFile.getBaseFile().getName(), e);
                    }
                }
            }
        }
    }
}
