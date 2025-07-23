package com.android.internal.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.util.ProcFileReader;
import com.samsung.android.security.SemSdCardEncryption;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class BinderfsStatsReader {
    private final String mPath;

    public BinderfsStatsReader() {
        this.mPath = "/dev/binderfs/binder_logs/stats";
    }

    public BinderfsStatsReader(String str) {
        this.mPath = str;
    }

    public void handleFreeAsyncSpace(Predicate<Integer> predicate, BiConsumer<Integer, Integer> biConsumer, Consumer<Exception> consumer) {
        try {
            ProcFileReader procFileReader = new ProcFileReader(new FileInputStream(this.mPath));
            while (procFileReader.hasMoreData()) {
                try {
                    if (!procFileReader.nextString().equals("proc")) {
                        procFileReader.finishLine();
                    } else {
                        int nextInt = procFileReader.nextInt();
                        procFileReader.finishLine();
                        if (predicate.test(Integer.valueOf(nextInt))) {
                            procFileReader.finishLine();
                            procFileReader.finishLine();
                            procFileReader.finishLine();
                            procFileReader.finishLine();
                            if (!procFileReader.nextString().equals(SemSdCardEncryption.STATUS_FREE)) {
                                procFileReader.finishLine();
                            } else if (!procFileReader.nextString().equals("async")) {
                                procFileReader.finishLine();
                            } else if (!procFileReader.nextString().equals(NavigationBarInflaterView.NAVSPACE)) {
                                procFileReader.finishLine();
                            } else {
                                int nextInt2 = procFileReader.nextInt();
                                procFileReader.finishLine();
                                biConsumer.accept(Integer.valueOf(nextInt), Integer.valueOf(nextInt2));
                            }
                        }
                    }
                } catch (Throwable th) {
                    try {
                        procFileReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            procFileReader.close();
        } catch (IOException | NumberFormatException e) {
            consumer.accept(e);
        }
    }
}
