package com.android.server.ondeviceintelligence;

import android.app.ondeviceintelligence.InferenceInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Base64;
import android.util.Slog;

import com.android.server.ondeviceintelligence.nano.InferenceInfo;

import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

public final class InferenceInfoStore {
    public final TreeSet inferenceInfos =
            new TreeSet(
                    Comparator.comparingLong(new InferenceInfoStore$$ExternalSyntheticLambda0()));
    public final long maxAgeMs;

    public InferenceInfoStore(long j) {
        this.maxAgeMs = j;
    }

    public final synchronized void add(InferenceInfo inferenceInfo) {
        while (!this.inferenceInfos.isEmpty()
                && System.currentTimeMillis()
                                - ((android.app.ondeviceintelligence.InferenceInfo)
                                                this.inferenceInfos.first())
                                        .getStartTimeMs()
                        > this.maxAgeMs) {
            try {
                this.inferenceInfos.pollFirst();
            } catch (Throwable th) {
                throw th;
            }
        }
        this.inferenceInfos.add(
                new InferenceInfo.Builder()
                        .setUid(inferenceInfo.uid)
                        .setStartTimeMs(inferenceInfo.startTimeMs)
                        .setEndTimeMs(inferenceInfo.endTimeMs)
                        .setSuspendedTimeMs(inferenceInfo.suspendedTimeMs)
                        .build());
    }

    public final void addInferenceInfoFromBundle(Bundle bundle) {
        if (bundle.containsKey("inference_info")) {
            try {
                byte[] byteArray = bundle.getByteArray("inference_info");
                if (byteArray != null) {
                    add(
                            com.android.server.ondeviceintelligence.nano.InferenceInfo.parseFrom(
                                    byteArray));
                }
            } catch (IOException unused) {
                Slog.e(
                        "InferenceInfoStore",
                        "Unable to parse InferenceInfo from the received bytes.");
            }
        }
    }

    public final void addInferenceInfoFromBundle(PersistableBundle persistableBundle) {
        if (persistableBundle.containsKey("inference_info")) {
            try {
                String string = persistableBundle.getString("inference_info");
                if (string != null) {
                    add(
                            com.android.server.ondeviceintelligence.nano.InferenceInfo.parseFrom(
                                    Base64.decode(string, 0)));
                }
            } catch (IOException unused) {
                Slog.e(
                        "InferenceInfoStore",
                        "Unable to parse InferenceInfo from the received bytes.");
            }
        }
    }
}
