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

    public BinderfsStatsReader(String path) {
        this.mPath = path;
    }

    public void handleFreeAsyncSpace(Predicate<Integer> predicate, BiConsumer<Integer, Integer> biConsumer, Consumer<Exception> consumer) {
        try {
            ProcFileReader mReader = new ProcFileReader(new FileInputStream(this.mPath));
            while (mReader.hasMoreData()) {
                try {
                    if (!mReader.nextString().equals("proc")) {
                        mReader.finishLine();
                    } else {
                        int pid = mReader.nextInt();
                        mReader.finishLine();
                        if (predicate.test(Integer.valueOf(pid))) {
                            mReader.finishLine();
                            mReader.finishLine();
                            mReader.finishLine();
                            mReader.finishLine();
                            if (!mReader.nextString().equals(SemSdCardEncryption.STATUS_FREE)) {
                                mReader.finishLine();
                            } else if (!mReader.nextString().equals("async")) {
                                mReader.finishLine();
                            } else if (!mReader.nextString().equals(NavigationBarInflaterView.NAVSPACE)) {
                                mReader.finishLine();
                            } else {
                                int free = mReader.nextInt();
                                mReader.finishLine();
                                biConsumer.accept(Integer.valueOf(pid), Integer.valueOf(free));
                            }
                        }
                    }
                } catch (Throwable th) {
                    try {
                        mReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            mReader.close();
        } catch (IOException | NumberFormatException e) {
            consumer.accept(e);
        }
    }
}
