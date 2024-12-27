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
