package com.android.server.wm;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.SystemClock;
import android.util.Slog;
import android.window.TaskSnapshot;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.nano.WindowManagerProtos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppSnapshotLoader {
    public final BaseAppSnapshotPersister$PersistInfoProvider mPersistInfoProvider;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PreRLegacySnapshotConfig {
        public final boolean mForceLoadReducedJpeg;
        public final float mScale;

        public PreRLegacySnapshotConfig(boolean z, float f) {
            this.mScale = f;
            this.mForceLoadReducedJpeg = z;
        }
    }

    public AppSnapshotLoader(
            BaseAppSnapshotPersister$PersistInfoProvider
                    baseAppSnapshotPersister$PersistInfoProvider) {
        this.mPersistInfoProvider = baseAppSnapshotPersister$PersistInfoProvider;
    }

    public final TaskSnapshot loadTask(int i, int i2, boolean z) {
        String str;
        boolean z2;
        PreRLegacySnapshotConfig preRLegacySnapshotConfig;
        Point point;
        BaseAppSnapshotPersister$PersistInfoProvider baseAppSnapshotPersister$PersistInfoProvider =
                this.mPersistInfoProvider;
        File protoFile = baseAppSnapshotPersister$PersistInfoProvider.getProtoFile(i, i2);
        if (!protoFile.exists()) {
            return null;
        }
        try {
            WindowManagerProtos.TaskSnapshotProto parseFrom =
                    WindowManagerProtos.TaskSnapshotProto.parseFrom(
                            Files.readAllBytes(protoFile.toPath()));
            File highResolutionBitmapFile =
                    baseAppSnapshotPersister$PersistInfoProvider.getHighResolutionBitmapFile(i, i2);
            int i3 = parseFrom.taskWidth;
            float f = parseFrom.legacyScale;
            boolean exists = highResolutionBitmapFile.exists();
            boolean z3 = true;
            boolean z4 = i3 == 0;
            if (z4) {
                if (z4 && Float.compare(f, FullScreenMagnificationGestureHandler.MAX_SCALE) == 0) {
                    if (!ActivityManager.isLowRamDeviceStatic() || exists) {
                        f = z ? 0.5f : 1.0f;
                        z2 = false;
                    } else {
                        f = 0.6f;
                        z2 = true;
                    }
                } else if (!z4) {
                    z2 = false;
                    f = 0.0f;
                } else if (ActivityManager.isLowRamDeviceStatic()) {
                    z2 = true;
                } else {
                    if (z) {
                        f *= 0.5f;
                    }
                    z2 = false;
                }
                preRLegacySnapshotConfig = new PreRLegacySnapshotConfig(z2, f);
            } else {
                preRLegacySnapshotConfig = null;
            }
            if (preRLegacySnapshotConfig == null
                    || !preRLegacySnapshotConfig.mForceLoadReducedJpeg) {
                z3 = false;
            }
            if (z || z3) {
                highResolutionBitmapFile =
                        baseAppSnapshotPersister$PersistInfoProvider.getLowResolutionBitmapFile(
                                i, i2);
            }
            if (!highResolutionBitmapFile.exists()) {
                return null;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig =
                    (!baseAppSnapshotPersister$PersistInfoProvider.mUse16BitFormat
                                    || parseFrom.isTranslucent)
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565;
            Bitmap decodeFile =
                    BitmapFactory.decodeFile(highResolutionBitmapFile.getPath(), options);
            if (decodeFile == null) {
                Slog.w(
                        "WindowManager",
                        "Failed to load bitmap: " + highResolutionBitmapFile.getPath());
                return null;
            }
            Bitmap copy = decodeFile.copy(Bitmap.Config.HARDWARE, false);
            decodeFile.recycle();
            if (copy == null) {
                Slog.w(
                        "WindowManager",
                        "Failed to create hardware bitmap: " + highResolutionBitmapFile.getPath());
                return null;
            }
            HardwareBuffer hardwareBuffer = copy.getHardwareBuffer();
            if (hardwareBuffer == null) {
                Slog.w(
                        "WindowManager",
                        "Failed to retrieve gralloc buffer for bitmap: "
                                + highResolutionBitmapFile.getPath());
                return null;
            }
            ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(parseFrom.topActivityComponent);
            if (preRLegacySnapshotConfig != null) {
                float f2 = preRLegacySnapshotConfig.mScale;
                point = new Point((int) (copy.getWidth() / f2), (int) (copy.getHeight() / f2));
            } else {
                point = new Point(parseFrom.taskWidth, parseFrom.taskHeight);
            }
            try {
                str = "WindowManager";
            } catch (IOException unused) {
                str = "WindowManager";
            }
            try {
                return new TaskSnapshot(
                        parseFrom.id,
                        SystemClock.elapsedRealtimeNanos(),
                        unflattenFromString,
                        hardwareBuffer,
                        copy.getColorSpace(),
                        parseFrom.orientation,
                        parseFrom.rotation,
                        point,
                        new Rect(
                                parseFrom.insetLeft,
                                parseFrom.insetTop,
                                parseFrom.insetRight,
                                parseFrom.insetBottom),
                        new Rect(
                                parseFrom.letterboxInsetLeft,
                                parseFrom.letterboxInsetTop,
                                parseFrom.letterboxInsetRight,
                                parseFrom.letterboxInsetBottom),
                        z,
                        parseFrom.isRealSnapshot,
                        parseFrom.windowingMode,
                        parseFrom.appearance,
                        parseFrom.isTranslucent,
                        false,
                        new Rect(
                                parseFrom.cutoutInsetLeft,
                                parseFrom.cutoutInsetTop,
                                parseFrom.cutoutInsetRight,
                                parseFrom.cutoutInsetBottom));
            } catch (IOException unused2) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "Unable to load task snapshot data for Id=", str);
                return null;
            }
        } catch (IOException unused3) {
            str = "WindowManager";
        }
    }
}
