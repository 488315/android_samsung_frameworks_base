package com.android.server.appop;

import android.app.AppOpsManager;
import android.media.AudioAttributes;
import android.util.ArraySet;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.input.KeyboardMetricsCollector;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioRestrictionManager {
    public static final SparseArray CAMERA_AUDIO_RESTRICTIONS;
    public final SparseArray mZenModeAudioRestrictions = new SparseArray();
    public int mCameraAudioRestriction = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Restriction {
        public static final ArraySet NO_EXCEPTIONS = new ArraySet();
        public ArraySet exceptionPackages;
        public int mode;
    }

    static {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray();
        for (int i : AudioAttributes.SDK_USAGES.toArray()) {
            int i2 = AudioAttributes.SUPPRESSIBLE_USAGES.get(i);
            if (i2 == 1 || i2 == 2 || i2 == 4) {
                sparseBooleanArray.append(i, true);
                sparseBooleanArray2.append(i, true);
            } else if (i2 != 5 && i2 != 6 && i2 != 3) {
                NandswapManager$$ExternalSyntheticOutline0.m(i2, "Unknown audio suppression behavior", "AudioRestriction");
            }
        }
        SparseArray sparseArray = new SparseArray();
        CAMERA_AUDIO_RESTRICTIONS = sparseArray;
        sparseArray.append(28, sparseBooleanArray);
        sparseArray.append(3, sparseBooleanArray2);
    }

    public final boolean dump(PrintWriter printWriter) {
        boolean hasActiveRestrictions = hasActiveRestrictions();
        synchronized (this) {
            boolean z = false;
            for (int i = 0; i < this.mZenModeAudioRestrictions.size(); i++) {
                try {
                    String opToName = AppOpsManager.opToName(this.mZenModeAudioRestrictions.keyAt(i));
                    SparseArray sparseArray = (SparseArray) this.mZenModeAudioRestrictions.valueAt(i);
                    for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                        if (!z) {
                            printWriter.println("  Zen Mode Audio Restrictions:");
                            z = true;
                        }
                        int keyAt = sparseArray.keyAt(i2);
                        printWriter.print("    ");
                        printWriter.print(opToName);
                        printWriter.print(" usage=");
                        printWriter.print(AudioAttributes.usageToString(keyAt));
                        Restriction restriction = (Restriction) sparseArray.valueAt(i2);
                        printWriter.print(": mode=");
                        printWriter.println(AppOpsManager.modeToName(restriction.mode));
                        if (!restriction.exceptionPackages.isEmpty()) {
                            printWriter.println("      Exceptions:");
                            for (int i3 = 0; i3 < restriction.exceptionPackages.size(); i3++) {
                                printWriter.print("        ");
                                printWriter.println((String) restriction.exceptionPackages.valueAt(i3));
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (this.mCameraAudioRestriction != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("  Camera Audio Restriction Mode: ");
                int i4 = this.mCameraAudioRestriction;
                sb.append(i4 != 0 ? i4 != 1 ? i4 != 3 ? "Unknown" : "MuteVibrationAndSound" : "MuteVibration" : KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                printWriter.println(sb.toString());
            }
        }
        return hasActiveRestrictions;
    }

    public final boolean hasActiveRestrictions() {
        boolean z;
        synchronized (this) {
            try {
                z = this.mZenModeAudioRestrictions.size() > 0 || this.mCameraAudioRestriction != 0;
            } finally {
            }
        }
        return z;
    }
}
