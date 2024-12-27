package com.android.server.backup;

import android.util.Slog;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProcessedPackagesJournal {
    public final Set mProcessedPackages = new HashSet();
    public final File mStateDirectory;

    public ProcessedPackagesJournal(File file) {
        this.mStateDirectory = file;
    }

    public final void loadFromDisk() {
        File file = new File(this.mStateDirectory, "processed");
        if (!file.exists()) {
            return;
        }
        try {
            DataInputStream dataInputStream =
                    new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
            while (true) {
                try {
                    String readUTF = dataInputStream.readUTF();
                    Slog.v("ProcessedPackagesJournal", "   + " + readUTF);
                    ((HashSet) this.mProcessedPackages).add(readUTF);
                } catch (Throwable th) {
                    try {
                        dataInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        } catch (EOFException unused) {
        } catch (IOException e) {
            Slog.e("ProcessedPackagesJournal", "Error reading processed packages journal", e);
        }
    }
}
