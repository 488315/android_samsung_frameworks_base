package com.android.server.wm;

import android.os.StrictMode;
import android.util.AtomicFile;
import android.util.Slog;

import com.android.server.wm.nano.WindowManagerProtos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class AppCompatConfigurationPersister {
    public final Consumer mCompletionCallback;
    public final AtomicFile mConfigurationFile;
    public final Supplier mDefaultBookModeReachabilitySupplier;
    public final Supplier mDefaultHorizontalReachabilitySupplier;
    public final Supplier mDefaultTabletopModeReachabilitySupplier;
    public final Supplier mDefaultVerticalReachabilitySupplier;
    public volatile int mLetterboxPositionForBookModeReachability;
    public volatile int mLetterboxPositionForHorizontalReachability;
    public volatile int mLetterboxPositionForTabletopModeReachability;
    public volatile int mLetterboxPositionForVerticalReachability;
    public final PersisterQueue mPersisterQueue;

    public final class UpdateValuesCommand implements PersisterQueue.WriteQueueItem {
        public final int mBookModeReachability;
        public final AtomicFile mFileToUpdate;
        public final int mHorizontalReachability;
        public final Consumer mOnComplete;
        public final int mTabletopModeReachability;
        public final int mVerticalReachability;

        public UpdateValuesCommand(
                AtomicFile atomicFile, int i, int i2, int i3, int i4, Consumer consumer) {
            this.mFileToUpdate = atomicFile;
            this.mHorizontalReachability = i;
            this.mVerticalReachability = i2;
            this.mBookModeReachability = i3;
            this.mTabletopModeReachability = i4;
            this.mOnComplete = consumer;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
            Consumer consumer;
            WindowManagerProtos.LetterboxProto letterboxProto =
                    new WindowManagerProtos.LetterboxProto();
            letterboxProto.letterboxPositionForHorizontalReachability =
                    this.mHorizontalReachability;
            letterboxProto.letterboxPositionForVerticalReachability = this.mVerticalReachability;
            letterboxProto.letterboxPositionForBookModeReachability = this.mBookModeReachability;
            letterboxProto.letterboxPositionForTabletopModeReachability =
                    this.mTabletopModeReachability;
            byte[] byteArray = WindowManagerProtos.LetterboxProto.toByteArray(letterboxProto);
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    fileOutputStream = this.mFileToUpdate.startWrite();
                    fileOutputStream.write(byteArray);
                    this.mFileToUpdate.finishWrite(fileOutputStream);
                    consumer = this.mOnComplete;
                    if (consumer == null) {
                        return;
                    }
                } catch (IOException e) {
                    this.mFileToUpdate.failWrite(fileOutputStream);
                    Slog.e(
                            "WindowManager",
                            "Error writing to AppCompatConfigurationPersister. Using default"
                                + " values!",
                            e);
                    consumer = this.mOnComplete;
                    if (consumer == null) {
                        return;
                    }
                }
                consumer.accept("UpdateValuesCommand");
            } catch (Throwable th) {
                Consumer consumer2 = this.mOnComplete;
                if (consumer2 != null) {
                    consumer2.accept("UpdateValuesCommand");
                }
                throw th;
            }
        }
    }

    public AppCompatConfigurationPersister(
            Supplier supplier,
            Supplier supplier2,
            Supplier supplier3,
            Supplier supplier4,
            File file,
            PersisterQueue persisterQueue,
            Consumer consumer,
            String str) {
        this.mDefaultHorizontalReachabilitySupplier = supplier;
        this.mDefaultVerticalReachabilitySupplier = supplier2;
        this.mDefaultBookModeReachabilitySupplier = supplier3;
        this.mDefaultTabletopModeReachabilitySupplier = supplier4;
        this.mCompletionCallback = consumer;
        this.mConfigurationFile = new AtomicFile(new File(file, str));
        this.mPersisterQueue = persisterQueue;
        Runnable runnable = new Runnable() { // from class:
                    // com.android.server.wm.AppCompatConfigurationPersister$$ExternalSyntheticLambda0
                    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x0036 -> B:10:0x004d). Please report as a decompilation issue!!! */
                    @Override // java.lang.Runnable
                    public final void run() {
                        AppCompatConfigurationPersister appCompatConfigurationPersister =
                                AppCompatConfigurationPersister.this;
                        if (!appCompatConfigurationPersister.mConfigurationFile.exists()) {
                            appCompatConfigurationPersister.useDefaultValue();
                            return;
                        }
                        FileInputStream fileInputStream = null;
                        try {
                            try {
                                try {
                                    fileInputStream =
                                            appCompatConfigurationPersister.mConfigurationFile
                                                    .openRead();
                                    WindowManagerProtos.LetterboxProto parseFrom =
                                            WindowManagerProtos.LetterboxProto.parseFrom(
                                                    AppCompatConfigurationPersister.readInputStream(
                                                            fileInputStream));
                                    appCompatConfigurationPersister
                                                    .mLetterboxPositionForHorizontalReachability =
                                            parseFrom.letterboxPositionForHorizontalReachability;
                                    appCompatConfigurationPersister
                                                    .mLetterboxPositionForVerticalReachability =
                                            parseFrom.letterboxPositionForVerticalReachability;
                                    appCompatConfigurationPersister
                                                    .mLetterboxPositionForBookModeReachability =
                                            parseFrom.letterboxPositionForBookModeReachability;
                                    appCompatConfigurationPersister
                                                    .mLetterboxPositionForTabletopModeReachability =
                                            parseFrom.letterboxPositionForTabletopModeReachability;
                                    fileInputStream.close();
                                } catch (Throwable th) {
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e) {
                                            appCompatConfigurationPersister.useDefaultValue();
                                            Slog.e(
                                                    "WindowManager",
                                                    "Error reading from"
                                                        + " AppCompatConfigurationPersister ",
                                                    e);
                                        }
                                    }
                                    throw th;
                                }
                            } catch (IOException e2) {
                                Slog.e(
                                        "WindowManager",
                                        "Error reading from AppCompatConfigurationPersister. Using"
                                            + " default values!",
                                        e2);
                                appCompatConfigurationPersister.useDefaultValue();
                                if (fileInputStream == null) {
                                } else {
                                    fileInputStream.close();
                                }
                            }
                        } catch (IOException e3) {
                            appCompatConfigurationPersister.useDefaultValue();
                            Slog.e(
                                    "WindowManager",
                                    "Error reading from AppCompatConfigurationPersister ",
                                    e3);
                        }
                    }
                };
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitDiskReads().build());
        runnable.run();
        StrictMode.setThreadPolicy(threadPolicy);
    }

    public static byte[] readInputStream(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[1024];
            int read = inputStream.read(bArr);
            while (read > 0) {
                byteArrayOutputStream.write(bArr, 0, read);
                read = inputStream.read(bArr);
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th) {
            byteArrayOutputStream.close();
            throw th;
        }
    }

    public final int getLetterboxPositionForHorizontalReachability(boolean z) {
        return z
                ? this.mLetterboxPositionForBookModeReachability
                : this.mLetterboxPositionForHorizontalReachability;
    }

    public final int getLetterboxPositionForVerticalReachability(boolean z) {
        return z
                ? this.mLetterboxPositionForTabletopModeReachability
                : this.mLetterboxPositionForVerticalReachability;
    }

    public final void setLetterboxPositionForHorizontalReachability(int i, boolean z) {
        if (z) {
            if (this.mLetterboxPositionForBookModeReachability != i) {
                this.mLetterboxPositionForBookModeReachability = i;
                updateConfiguration();
                return;
            }
            return;
        }
        if (this.mLetterboxPositionForHorizontalReachability != i) {
            this.mLetterboxPositionForHorizontalReachability = i;
            updateConfiguration();
        }
    }

    public final void setLetterboxPositionForVerticalReachability(int i, boolean z) {
        if (z) {
            if (this.mLetterboxPositionForTabletopModeReachability != i) {
                this.mLetterboxPositionForTabletopModeReachability = i;
                updateConfiguration();
                return;
            }
            return;
        }
        if (this.mLetterboxPositionForVerticalReachability != i) {
            this.mLetterboxPositionForVerticalReachability = i;
            updateConfiguration();
        }
    }

    public final void updateConfiguration() {
        this.mPersisterQueue.addItem(
                new UpdateValuesCommand(
                        this.mConfigurationFile,
                        this.mLetterboxPositionForHorizontalReachability,
                        this.mLetterboxPositionForVerticalReachability,
                        this.mLetterboxPositionForBookModeReachability,
                        this.mLetterboxPositionForTabletopModeReachability,
                        this.mCompletionCallback),
                true);
    }

    public void useDefaultValue() {
        this.mLetterboxPositionForHorizontalReachability =
                ((Integer) this.mDefaultHorizontalReachabilitySupplier.get()).intValue();
        this.mLetterboxPositionForVerticalReachability =
                ((Integer) this.mDefaultVerticalReachabilitySupplier.get()).intValue();
        this.mLetterboxPositionForBookModeReachability =
                ((Integer) this.mDefaultBookModeReachabilitySupplier.get()).intValue();
        this.mLetterboxPositionForTabletopModeReachability =
                ((Integer) this.mDefaultTabletopModeReachabilitySupplier.get()).intValue();
    }
}
