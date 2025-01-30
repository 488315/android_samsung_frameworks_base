package com.android.server.wm;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.SystemClock;
import android.util.Slog;
import android.window.TaskSnapshot;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.BaseAppSnapshotPersister;
import com.android.server.wm.nano.WindowManagerProtos;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/* loaded from: classes3.dex */
public class AppSnapshotLoader {
    public final BaseAppSnapshotPersister.PersistInfoProvider mPersistInfoProvider;

    public AppSnapshotLoader(BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
        this.mPersistInfoProvider = persistInfoProvider;
    }

    public class PreRLegacySnapshotConfig {
        public final boolean mForceLoadReducedJpeg;
        public final float mScale;

        public PreRLegacySnapshotConfig(float f, boolean z) {
            this.mScale = f;
            this.mForceLoadReducedJpeg = z;
        }
    }

    public PreRLegacySnapshotConfig getLegacySnapshotConfig(int i, float f, boolean z, boolean z2) {
        boolean z3 = true;
        boolean z4 = i == 0;
        if (!z4) {
            return null;
        }
        if (z4 && Float.compare(f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON) == 0) {
            if (!ActivityManager.isLowRamDeviceStatic() || z) {
                f = z2 ? 0.5f : 1.0f;
                z3 = false;
            } else {
                f = 0.6f;
            }
        } else if (!z4) {
            z3 = false;
            f = 0.0f;
        } else if (!ActivityManager.isLowRamDeviceStatic()) {
            if (z2) {
                f *= 0.5f;
            }
            z3 = false;
        }
        return new PreRLegacySnapshotConfig(f, z3);
    }

    public TaskSnapshot loadTask(int i, int i2, boolean z) {
        String str;
        Point point;
        Rect rect;
        Rect rect2;
        boolean z2;
        int i3;
        int i4;
        boolean z3;
        Rect rect3;
        File protoFile = this.mPersistInfoProvider.getProtoFile(i, i2);
        if (!protoFile.exists()) {
            return null;
        }
        try {
            WindowManagerProtos.TaskSnapshotProto parseFrom = WindowManagerProtos.TaskSnapshotProto.parseFrom(Files.readAllBytes(protoFile.toPath()));
            File highResolutionBitmapFile = this.mPersistInfoProvider.getHighResolutionBitmapFile(i, i2);
            PreRLegacySnapshotConfig legacySnapshotConfig = getLegacySnapshotConfig(parseFrom.taskWidth, parseFrom.legacyScale, highResolutionBitmapFile.exists(), z);
            boolean z4 = legacySnapshotConfig != null && legacySnapshotConfig.mForceLoadReducedJpeg;
            if (z || z4) {
                highResolutionBitmapFile = this.mPersistInfoProvider.getLowResolutionBitmapFile(i, i2);
            }
            if (!highResolutionBitmapFile.exists()) {
                return null;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = (!this.mPersistInfoProvider.use16BitFormat() || parseFrom.isTranslucent) ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
            Bitmap decodeFile = BitmapFactory.decodeFile(highResolutionBitmapFile.getPath(), options);
            if (decodeFile == null) {
                Slog.w(StartingSurfaceController.TAG, "Failed to load bitmap: " + highResolutionBitmapFile.getPath());
                return null;
            }
            Bitmap copy = decodeFile.copy(Bitmap.Config.HARDWARE, false);
            decodeFile.recycle();
            if (copy == null) {
                Slog.w(StartingSurfaceController.TAG, "Failed to create hardware bitmap: " + highResolutionBitmapFile.getPath());
                return null;
            }
            HardwareBuffer hardwareBuffer = copy.getHardwareBuffer();
            if (hardwareBuffer == null) {
                Slog.w(StartingSurfaceController.TAG, "Failed to retrieve gralloc buffer for bitmap: " + highResolutionBitmapFile.getPath());
                return null;
            }
            ComponentName unflattenFromString = ComponentName.unflattenFromString(parseFrom.topActivityComponent);
            if (legacySnapshotConfig != null) {
                point = new Point((int) (copy.getWidth() / legacySnapshotConfig.mScale), (int) (copy.getHeight() / legacySnapshotConfig.mScale));
            } else {
                point = new Point(parseFrom.taskWidth, parseFrom.taskHeight);
            }
            long j = parseFrom.id;
            long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
            ColorSpace colorSpace = copy.getColorSpace();
            int i5 = parseFrom.orientation;
            int i6 = parseFrom.rotation;
            try {
                rect = new Rect(parseFrom.insetLeft, parseFrom.insetTop, parseFrom.insetRight, parseFrom.insetBottom);
                rect2 = new Rect(parseFrom.letterboxInsetLeft, parseFrom.letterboxInsetTop, parseFrom.letterboxInsetRight, parseFrom.letterboxInsetBottom);
                z2 = parseFrom.isRealSnapshot;
                i3 = parseFrom.windowingMode;
                i4 = parseFrom.appearance;
                z3 = parseFrom.isTranslucent;
                rect3 = new Rect(parseFrom.cutoutInsetLeft, parseFrom.cutoutInsetTop, parseFrom.cutoutInsetRight, parseFrom.cutoutInsetBottom);
                str = StartingSurfaceController.TAG;
            } catch (IOException unused) {
                str = StartingSurfaceController.TAG;
            }
            try {
                return new TaskSnapshot(j, elapsedRealtimeNanos, unflattenFromString, hardwareBuffer, colorSpace, i5, i6, point, rect, rect2, z, z2, i3, i4, z3, false, rect3);
            } catch (IOException unused2) {
                Slog.w(str, "Unable to load task snapshot data for Id=" + i);
                return null;
            }
        } catch (IOException unused3) {
            str = StartingSurfaceController.TAG;
        }
    }
}
