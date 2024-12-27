package com.android.server.voiceinteraction;

import android.os.ParcelFileDescriptor;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

public final /* synthetic */ class DetectorSession$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ParcelFileDescriptor f$0;
    public final /* synthetic */ InputStream f$1;

    public /* synthetic */ DetectorSession$3$$ExternalSyntheticLambda0(
            ParcelFileDescriptor parcelFileDescriptor, InputStream inputStream, int i) {
        this.$r8$classId = i;
        this.f$0 = parcelFileDescriptor;
        this.f$1 = inputStream;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 0;
        switch (this.$r8$classId) {
            case 0:
                Closeable[] closeableArr = {this.f$0, this.f$1};
                Duration duration = DetectorSession.MAX_UPDATE_TIMEOUT_DURATION;
                while (i < 2) {
                    try {
                        closeableArr[i].close();
                    } catch (IOException unused) {
                    }
                    i++;
                }
                break;
            default:
                Closeable[] closeableArr2 = {this.f$0, this.f$1};
                Duration duration2 = DetectorSession.MAX_UPDATE_TIMEOUT_DURATION;
                while (i < 2) {
                    try {
                        closeableArr2[i].close();
                    } catch (IOException unused2) {
                    }
                    i++;
                }
                break;
        }
    }
}
