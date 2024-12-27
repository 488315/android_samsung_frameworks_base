package com.android.server.notification;

import android.app.NotificationHistory;
import android.net.Uri;
import android.os.Handler;
import android.util.AtomicFile;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

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
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NotificationHistoryDatabase {
    public static final boolean DEBUG = NotificationManagerService.DBG;
    public final Handler mFileWriteHandler;
    public final File mHistoryDir;
    public final File mVersionFile;
    public final Object mLock = new Object();
    public final ArrayList mUris = new ArrayList();
    public final int mCurrentVersion = 1;
    final List mHistoryFiles = new ArrayList();
    NotificationHistory mBuffer = new NotificationHistory();
    public final WriteBufferRunnable mWriteBufferRunnable = new WriteBufferRunnable();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoveChannelRunnable implements Runnable {
        public final String mChannelId;
        public NotificationHistory mNotificationHistory;
        public final String mPkg;

        public RemoveChannelRunnable(String str, String str2) {
            this.mPkg = str;
            this.mChannelId = str2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                Slog.d("NotiHistoryDatabase", "RemoveChannelRunnable");
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                NotificationHistoryDatabase.this.mBuffer.removeChannelFromWrite(
                        this.mPkg, this.mChannelId);
                for (AtomicFile atomicFile : NotificationHistoryDatabase.this.mHistoryFiles) {
                    try {
                        NotificationHistory notificationHistory = this.mNotificationHistory;
                        if (notificationHistory == null) {
                            notificationHistory = new NotificationHistory();
                        }
                        NotificationHistoryFilter notificationHistoryFilter =
                                new NotificationHistoryFilter();
                        notificationHistoryFilter.mPackage = null;
                        notificationHistoryFilter.mChannel = null;
                        notificationHistoryFilter.mNotificationCount = Integer.MAX_VALUE;
                        notificationHistoryFilter.mSbnKey = null;
                        NotificationHistoryDatabase.readLocked(
                                atomicFile, notificationHistory, notificationHistoryFilter);
                        if (notificationHistory.removeChannelFromWrite(
                                this.mPkg, this.mChannelId)) {
                            NotificationHistoryDatabase.this.writeLocked(
                                    atomicFile, notificationHistory);
                        }
                    } catch (Exception e) {
                        Slog.e(
                                "NotiHistoryDatabase",
                                "Cannot clean up file on channel removal "
                                        + atomicFile.getBaseFile().getName(),
                                e);
                    }
                }
            }
        }

        public void setNotificationHistory(NotificationHistory notificationHistory) {
            this.mNotificationHistory = notificationHistory;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoveConversationRunnable implements Runnable {
        public final Set mConversationIds;
        public NotificationHistory mNotificationHistory;
        public final String mPkg;

        public RemoveConversationRunnable(String str, Set set) {
            this.mPkg = str;
            this.mConversationIds = set;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                Slog.d(
                        "NotiHistoryDatabase",
                        "RemoveConversationRunnable " + this.mPkg + " " + this.mConversationIds);
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                NotificationHistoryDatabase.this.mBuffer.removeConversationsFromWrite(
                        this.mPkg, this.mConversationIds);
                for (AtomicFile atomicFile : NotificationHistoryDatabase.this.mHistoryFiles) {
                    try {
                        NotificationHistory notificationHistory = this.mNotificationHistory;
                        if (notificationHistory == null) {
                            notificationHistory = new NotificationHistory();
                        }
                        NotificationHistoryFilter notificationHistoryFilter =
                                new NotificationHistoryFilter();
                        notificationHistoryFilter.mPackage = null;
                        notificationHistoryFilter.mChannel = null;
                        notificationHistoryFilter.mNotificationCount = Integer.MAX_VALUE;
                        notificationHistoryFilter.mSbnKey = null;
                        NotificationHistoryDatabase.readLocked(
                                atomicFile, notificationHistory, notificationHistoryFilter);
                        if (notificationHistory.removeConversationsFromWrite(
                                this.mPkg, this.mConversationIds)) {
                            NotificationHistoryDatabase.this.writeLocked(
                                    atomicFile, notificationHistory);
                        }
                    } catch (Exception e) {
                        Slog.e(
                                "NotiHistoryDatabase",
                                "Cannot clean up file on conversation removal "
                                        + atomicFile.getBaseFile().getName(),
                                e);
                    }
                }
            }
        }

        public void setNotificationHistory(NotificationHistory notificationHistory) {
            this.mNotificationHistory = notificationHistory;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoveImageRunnable implements Runnable {
        public final String mSbnKey;
        public final String mText;
        public final Uri mUri;

        public RemoveImageRunnable(String str, String str2, Uri uri) {
            this.mSbnKey = str;
            this.mText = str2;
            this.mUri = uri;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                Slog.d("NotiHistoryDatabase", "RemoveImageRunnable");
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                if (!NotificationHistoryDatabase.this.mBuffer.removeImageNotificationsFromWrite(
                        this.mSbnKey, this.mText, this.mUri)) {
                    Iterator it = NotificationHistoryDatabase.this.mHistoryFiles.iterator();
                    if (it.hasNext()) {
                        AtomicFile atomicFile = (AtomicFile) it.next();
                        try {
                            NotificationHistory notificationHistory = new NotificationHistory();
                            NotificationHistoryFilter notificationHistoryFilter =
                                    new NotificationHistoryFilter();
                            notificationHistoryFilter.mPackage = null;
                            notificationHistoryFilter.mChannel = null;
                            notificationHistoryFilter.mNotificationCount = Integer.MAX_VALUE;
                            notificationHistoryFilter.mSbnKey = null;
                            NotificationHistoryDatabase.readLocked(
                                    atomicFile, notificationHistory, notificationHistoryFilter);
                            if (notificationHistory.removeImageNotificationsFromWrite(
                                    this.mSbnKey, this.mText, this.mUri)) {
                                NotificationHistoryDatabase.this.writeLocked(
                                        atomicFile, notificationHistory);
                            }
                        } catch (Exception e) {
                            Slog.e(
                                    "NotiHistoryDatabase",
                                    "Cannot clean up file on channel removal "
                                            + atomicFile.getBaseFile().getName(),
                                    e);
                        }
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoveNotificationRunnable implements Runnable {
        public NotificationHistory mNotificationHistory;
        public final String mPkg;
        public final long mPostedTime;

        public RemoveNotificationRunnable(String str, long j) {
            this.mPkg = str;
            this.mPostedTime = j;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                Slog.d("NotiHistoryDatabase", "RemoveNotificationRunnable");
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                NotificationHistoryDatabase.this.mBuffer.removeNotificationFromWrite(
                        this.mPkg, this.mPostedTime);
                for (AtomicFile atomicFile : NotificationHistoryDatabase.this.mHistoryFiles) {
                    try {
                        NotificationHistory notificationHistory = this.mNotificationHistory;
                        if (notificationHistory == null) {
                            notificationHistory = new NotificationHistory();
                        }
                        NotificationHistoryFilter notificationHistoryFilter =
                                new NotificationHistoryFilter();
                        notificationHistoryFilter.mPackage = null;
                        notificationHistoryFilter.mChannel = null;
                        notificationHistoryFilter.mNotificationCount = Integer.MAX_VALUE;
                        notificationHistoryFilter.mSbnKey = null;
                        NotificationHistoryDatabase.readLocked(
                                atomicFile, notificationHistory, notificationHistoryFilter);
                        if (notificationHistory.removeNotificationFromWrite(
                                this.mPkg, this.mPostedTime)) {
                            NotificationHistoryDatabase.this.writeLocked(
                                    atomicFile, notificationHistory);
                        }
                    } catch (Exception e) {
                        Slog.e(
                                "NotiHistoryDatabase",
                                "Cannot clean up file on notification removal "
                                        + atomicFile.getBaseFile().getName(),
                                e);
                    }
                }
            }
        }

        public void setNotificationHistory(NotificationHistory notificationHistory) {
            this.mNotificationHistory = notificationHistory;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemovePackageRunnable implements Runnable {
        public final String mPkg;

        public RemovePackageRunnable(String str) {
            this.mPkg = str;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (NotificationHistoryDatabase.DEBUG) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder("RemovePackageRunnable "),
                        this.mPkg,
                        "NotiHistoryDatabase");
            }
            synchronized (NotificationHistoryDatabase.this.mLock) {
                NotificationHistoryDatabase.this.mBuffer.removeNotificationsFromWrite(this.mPkg);
                for (AtomicFile atomicFile : NotificationHistoryDatabase.this.mHistoryFiles) {
                    try {
                        NotificationHistory notificationHistory = new NotificationHistory();
                        NotificationHistoryFilter notificationHistoryFilter =
                                new NotificationHistoryFilter();
                        notificationHistoryFilter.mPackage = null;
                        notificationHistoryFilter.mChannel = null;
                        notificationHistoryFilter.mNotificationCount = Integer.MAX_VALUE;
                        notificationHistoryFilter.mSbnKey = null;
                        NotificationHistoryDatabase.readLocked(
                                atomicFile, notificationHistory, notificationHistoryFilter);
                        notificationHistory.removeNotificationsFromWrite(this.mPkg);
                        NotificationHistoryDatabase.this.writeLocked(
                                atomicFile, notificationHistory);
                    } catch (Exception e) {
                        Slog.e(
                                "NotiHistoryDatabase",
                                "Cannot clean up file on pkg removal "
                                        + atomicFile.getBaseFile().getAbsolutePath(),
                                e);
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WriteBufferRunnable implements Runnable {
        public WriteBufferRunnable() {}

        @Override // java.lang.Runnable
        public final void run() {
            AtomicFile atomicFile =
                    new AtomicFile(
                            new File(
                                    NotificationHistoryDatabase.this.mHistoryDir,
                                    String.valueOf(System.currentTimeMillis())));
            synchronized (NotificationHistoryDatabase.this.mLock) {
                if (NotificationHistoryDatabase.DEBUG) {
                    Slog.d(
                            "NotiHistoryDatabase",
                            "WriteBufferRunnable " + atomicFile.getBaseFile().getAbsolutePath());
                }
                try {
                    NotificationHistoryDatabase notificationHistoryDatabase =
                            NotificationHistoryDatabase.this;
                    notificationHistoryDatabase.writeLocked(
                            atomicFile, notificationHistoryDatabase.mBuffer);
                    NotificationHistoryDatabase.this.mHistoryFiles.add(0, atomicFile);
                    NotificationHistoryDatabase.this.mBuffer = new NotificationHistory();
                    NotificationHistoryImageProvider.getInstance()
                            .updatePostedTime(
                                    System.currentTimeMillis(),
                                    NotificationHistoryDatabase.this.mUris);
                    NotificationHistoryDatabase.this.mUris.clear();
                } catch (IOException e) {
                    Slog.e(
                            "NotiHistoryDatabase",
                            "Failed to write buffer to disk. not flushing buffer",
                            e);
                }
            }
        }
    }

    public NotificationHistoryDatabase(Handler handler, File file) {
        this.mFileWriteHandler = handler;
        this.mVersionFile = new File(file, "version");
        this.mHistoryDir = new File(file, "history");
    }

    public static void readLocked(
            AtomicFile atomicFile,
            NotificationHistory notificationHistory,
            NotificationHistoryFilter notificationHistoryFilter) {
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = atomicFile.openRead();
                NotificationHistoryProtoHelper.read(
                        fileInputStream, notificationHistory, notificationHistoryFilter);
            } catch (FileNotFoundException e) {
                Slog.e(
                        "NotiHistoryDatabase",
                        "Cannot open " + atomicFile.getBaseFile().getAbsolutePath(),
                        e);
                throw e;
            }
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    public final void addNotification(
            NotificationHistory.HistoricalNotification historicalNotification) {
        synchronized (this.mLock) {
            try {
                this.mBuffer.addNewNotificationToWrite(historicalNotification);
                Uri uri = historicalNotification.getUri();
                if (uri != null) {
                    this.mUris.add(uri.toString());
                }
                if (this.mBuffer.getHistoryCount() == 1) {
                    this.mFileWriteHandler.postDelayed(this.mWriteBufferRunnable, 1200000L);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void checkVersionAndBuildLocked() {
        int i;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(this.mVersionFile));
        } catch (IOException | NumberFormatException unused) {
            i = 0;
        }
        try {
            i = Integer.parseInt(bufferedReader.readLine());
            bufferedReader.close();
            int i2 = this.mCurrentVersion;
            if (i == i2 || !this.mVersionFile.exists()) {
                return;
            }
            try {
                BufferedWriter bufferedWriter =
                        new BufferedWriter(new FileWriter(this.mVersionFile));
                try {
                    bufferedWriter.write(Integer.toString(i2));
                    bufferedWriter.write("\n");
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } finally {
                }
            } catch (IOException e) {
                Slog.e("NotiHistoryDatabase", "Failed to write new version");
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void deleteFile(AtomicFile atomicFile) {
        if (DEBUG) {
            Slog.d("NotiHistoryDatabase", "Removed " + atomicFile.getBaseFile().getName());
        }
        atomicFile.delete();
        String absolutePath = atomicFile.getBaseFile().getAbsolutePath();
        if (absolutePath != null) {
            Iterator it = this.mHistoryFiles.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                AtomicFile atomicFile2 = (AtomicFile) it.next();
                if (atomicFile2 != null
                        && absolutePath.equals(atomicFile2.getBaseFile().getAbsolutePath())) {
                    it.remove();
                    break;
                }
            }
        }
        NotificationHistoryImageProvider.getInstance().deleteRows(System.currentTimeMillis());
    }

    public final void init() {
        synchronized (this.mLock) {
            try {
                try {
                    if (!this.mHistoryDir.exists() && !this.mHistoryDir.mkdir()) {
                        throw new IllegalStateException("could not create history directory");
                    }
                    this.mVersionFile.createNewFile();
                } catch (Exception e) {
                    Slog.e("NotiHistoryDatabase", "could not create needed files", e);
                }
                checkVersionAndBuildLocked();
                this.mHistoryFiles.clear();
                File[] listFiles = this.mHistoryDir.listFiles();
                if (listFiles != null) {
                    Arrays.sort(
                            listFiles, new NotificationHistoryDatabase$$ExternalSyntheticLambda0());
                    for (File file : listFiles) {
                        this.mHistoryFiles.add(new AtomicFile(file));
                    }
                }
                prune();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void prune() {
        long j;
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.mLock) {
            try {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(currentTimeMillis);
                gregorianCalendar.add(5, -1);
                for (int size = this.mHistoryFiles.size() - 1; size >= 0; size--) {
                    AtomicFile atomicFile = (AtomicFile) this.mHistoryFiles.get(size);
                    try {
                        j = Long.parseLong(atomicFile.getBaseFile().getName());
                    } catch (NumberFormatException unused) {
                        j = -1;
                    }
                    if (DEBUG) {
                        Slog.d(
                                "NotiHistoryDatabase",
                                "File " + atomicFile.getBaseFile().getName() + " created on " + j);
                    }
                    if (j <= gregorianCalendar.getTimeInMillis()) {
                        deleteFile(atomicFile);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final NotificationHistory readNotificationHistory() {
        NotificationHistory notificationHistory;
        synchronized (this.mLock) {
            notificationHistory = new NotificationHistory();
            notificationHistory.addNotificationsToWrite(this.mBuffer);
            for (AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    NotificationHistoryFilter notificationHistoryFilter =
                            new NotificationHistoryFilter();
                    notificationHistoryFilter.mPackage = null;
                    notificationHistoryFilter.mChannel = null;
                    notificationHistoryFilter.mNotificationCount = Integer.MAX_VALUE;
                    notificationHistoryFilter.mSbnKey = null;
                    readLocked(atomicFile, notificationHistory, notificationHistoryFilter);
                } catch (Exception e) {
                    Slog.e(
                            "NotiHistoryDatabase",
                            "error reading " + atomicFile.getBaseFile().getAbsolutePath(),
                            e);
                }
            }
        }
        return notificationHistory;
    }

    public final NotificationHistory readNotificationHistoryForDump(String str) {
        NotificationHistory notificationHistory;
        synchronized (this.mLock) {
            notificationHistory = new NotificationHistory();
            notificationHistory.addNotificationsForDump(this.mBuffer, str, 20);
            for (AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    NotificationHistoryFilter notificationHistoryFilter =
                            new NotificationHistoryFilter();
                    notificationHistoryFilter.mPackage = str;
                    notificationHistoryFilter.mChannel = null;
                    notificationHistoryFilter.mNotificationCount = 20;
                    notificationHistoryFilter.mSbnKey = null;
                    readLocked(atomicFile, notificationHistory, notificationHistoryFilter);
                } catch (Exception e) {
                    Slog.e(
                            "NotiHistoryDatabase",
                            "error reading(for dump) " + atomicFile.getBaseFile().getAbsolutePath(),
                            e);
                }
                if (20 == notificationHistory.getHistoryCount()) {
                    break;
                }
            }
        }
        return notificationHistory;
    }

    public final NotificationHistory readNotificationHistoryForPackage(
            int i, String str, String str2) {
        NotificationHistory notificationHistory;
        synchronized (this.mLock) {
            notificationHistory = new NotificationHistory();
            notificationHistory.addNotificationsToWrite(this.mBuffer, str2, i);
            for (AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    NotificationHistoryFilter notificationHistoryFilter =
                            new NotificationHistoryFilter();
                    notificationHistoryFilter.mPackage = str;
                    notificationHistoryFilter.mChannel = null;
                    notificationHistoryFilter.mNotificationCount = i;
                    notificationHistoryFilter.mSbnKey = str2;
                    readLocked(atomicFile, notificationHistory, notificationHistoryFilter);
                } catch (Exception e) {
                    Slog.e(
                            "NotiHistoryDatabase",
                            "error reading " + atomicFile.getBaseFile().getAbsolutePath(),
                            e);
                }
                if (i == notificationHistory.getHistoryCount()) {
                    break;
                }
            }
        }
        return notificationHistory;
    }

    public final NotificationHistory readNotificationHistoryWithNullKeyHandling(int i, String str) {
        NotificationHistory notificationHistory;
        synchronized (this.mLock) {
            notificationHistory = new NotificationHistory();
            notificationHistory.addNotificationsToWriteForPkgName(this.mBuffer, str);
            for (AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    NotificationHistoryFilter notificationHistoryFilter =
                            new NotificationHistoryFilter();
                    notificationHistoryFilter.mPackage = str;
                    notificationHistoryFilter.mChannel = null;
                    notificationHistoryFilter.mNotificationCount = Integer.MAX_VALUE;
                    notificationHistoryFilter.mSbnKey = null;
                    readLocked(atomicFile, notificationHistory, notificationHistoryFilter);
                } catch (Exception e) {
                    Slog.e(
                            "NotiHistoryDatabase",
                            "error reading " + atomicFile.getBaseFile().getAbsolutePath(),
                            e);
                }
                if (i == notificationHistory.getHistoryCount()) {
                    break;
                }
            }
        }
        return notificationHistory;
    }

    public final void updateCancelEvent(String str, boolean z) {
        synchronized (this.mLock) {
            this.mBuffer.updateNotificationToWrite(str, z);
            Slog.d("NotiHistoryDatabase", "updateCancelEvent key = " + str);
            for (AtomicFile atomicFile : this.mHistoryFiles) {
                try {
                    NotificationHistory notificationHistory = new NotificationHistory();
                    NotificationHistoryFilter notificationHistoryFilter =
                            new NotificationHistoryFilter();
                    notificationHistoryFilter.mPackage = null;
                    notificationHistoryFilter.mChannel = null;
                    notificationHistoryFilter.mNotificationCount = Integer.MAX_VALUE;
                    notificationHistoryFilter.mSbnKey = null;
                    readLocked(atomicFile, notificationHistory, notificationHistoryFilter);
                    if (notificationHistory.updateNotificationToWrite(str, z)) {
                        writeLocked(atomicFile, notificationHistory);
                    }
                } catch (Exception e) {
                    Slog.e(
                            "NotiHistoryDatabase",
                            "Cannot clean up file on conversation removal "
                                    + atomicFile.getBaseFile().getName(),
                            e);
                }
            }
        }
    }

    public final void writeLocked(AtomicFile atomicFile, NotificationHistory notificationHistory) {
        FileOutputStream startWrite = atomicFile.startWrite();
        try {
            NotificationHistoryProtoHelper.write(
                    startWrite, notificationHistory, this.mCurrentVersion);
            atomicFile.finishWrite(startWrite);
            atomicFile.failWrite(null);
        } catch (Throwable th) {
            atomicFile.failWrite(startWrite);
            throw th;
        }
    }
}
