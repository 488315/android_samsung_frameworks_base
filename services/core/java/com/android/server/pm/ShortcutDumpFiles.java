package com.android.server.pm;

import android.text.format.TimeMigrationUtils;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ShortcutDumpFiles {
    public final ShortcutService mService;

    public ShortcutDumpFiles(ShortcutService shortcutService) {
        this.mService = shortcutService;
    }

    public final void dumpAll(PrintWriter printWriter) {
        try {
            ShortcutService shortcutService = this.mService;
            shortcutService.getClass();
            File file = new File(shortcutService.injectUserDataPath(0), "shortcut_dump");
            File[] listFiles = file.listFiles(new ShortcutDumpFiles$$ExternalSyntheticLambda1());
            if (file.exists() && !ArrayUtils.isEmpty(listFiles)) {
                Arrays.sort(
                        listFiles,
                        Comparator.comparing(new ShortcutDumpFiles$$ExternalSyntheticLambda2()));
                for (File file2 : listFiles) {
                    printWriter.print("*** Dumping: ");
                    printWriter.println(file2.getName());
                    printWriter.print("mtime: ");
                    printWriter.println(
                            TimeMigrationUtils.formatMillisWithFixedFormat(file2.lastModified()));
                    BufferedReader bufferedReader =
                            new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            } else {
                                printWriter.println(readLine);
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
                    bufferedReader.close();
                }
                return;
            }
            printWriter.print("  No dump files found.");
        } catch (IOException | RuntimeException e) {
            Slog.w("ShortcutService", "Failed to print dump files", e);
        }
    }

    public final boolean save(String str, Consumer consumer) {
        try {
            ShortcutService shortcutService = this.mService;
            shortcutService.getClass();
            File file = new File(shortcutService.injectUserDataPath(0), "shortcut_dump");
            file.mkdirs();
            if (!file.exists()) {
                Slog.e("ShortcutService", "Failed to create directory: " + file);
                return false;
            }
            PrintWriter printWriter =
                    new PrintWriter(
                            new BufferedOutputStream(new FileOutputStream(new File(file, str))));
            try {
                consumer.accept(printWriter);
                printWriter.close();
                return true;
            } catch (Throwable th) {
                try {
                    printWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | RuntimeException e) {
            Slog.w("ShortcutService", "Failed to create dump file: ".concat(str), e);
            return false;
        }
    }
}
