package com.android.server.power.shutdown;

import android.app.Presentation;
import android.content.Context;
import android.util.Pair;
import android.widget.ImageView;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AnimationPlayer implements PlayerInterface {
    public final String FILE_EXTENSION;
    public final String[] SHUTDOWN_MAIN_ANI_FILES;
    public final String SHUTDOWN_MAIN_LOOP_FILE;
    public final String[] SHUTDOWN_SUB_ANI_FILES;
    public final String SHUTDOWN_SUB_LOOP_FILE;
    public final Context context;
    public ImageView mainImageView;
    public Presentation subDialog;
    public ImageView subImageView;
    public boolean hasSubResources = false;
    public final ResourceManager resourceManager = new ResourceManager(this);

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class DisplayMode {
        public static final /* synthetic */ DisplayMode[] $VALUES;
        public static final DisplayMode MAIN_MAIN;
        public static final DisplayMode MAIN_SUB;
        public static final DisplayMode SUB_MAIN;
        public static final DisplayMode SUB_SUB;

        /* JADX INFO: Fake field, exist only in values array */
        DisplayMode EF0;

        static {
            DisplayMode displayMode = new DisplayMode("UNKNOWN", 0);
            DisplayMode displayMode2 = new DisplayMode("MAIN_MAIN", 1);
            MAIN_MAIN = displayMode2;
            DisplayMode displayMode3 = new DisplayMode("MAIN_SUB", 2);
            MAIN_SUB = displayMode3;
            DisplayMode displayMode4 = new DisplayMode("SUB_MAIN", 3);
            SUB_MAIN = displayMode4;
            DisplayMode displayMode5 = new DisplayMode("SUB_SUB", 4);
            SUB_SUB = displayMode5;
            $VALUES =
                    new DisplayMode[] {
                        displayMode, displayMode2, displayMode3, displayMode4, displayMode5
                    };
        }

        public static DisplayMode valueOf(String str) {
            return (DisplayMode) Enum.valueOf(DisplayMode.class, str);
        }

        public static DisplayMode[] values() {
            return (DisplayMode[]) $VALUES.clone();
        }
    }

    public AnimationPlayer(Context context, String str) {
        this.context = context;
        this.FILE_EXTENSION = str;
        this.SHUTDOWN_MAIN_ANI_FILES = initAnimationFiles("shutdown", str);
        this.SHUTDOWN_SUB_ANI_FILES = initAnimationFiles("sub_shutdown", str);
        this.SHUTDOWN_MAIN_LOOP_FILE = "shutdownloop".concat(str);
        this.SHUTDOWN_SUB_LOOP_FILE = "sub_shutdownloop".concat(str);
    }

    public static String[] initAnimationFiles(String str, String str2) {
        return new String[] {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(str, "before", str2),
            str.concat(str2),
            AnyMotionDetector$$ExternalSyntheticOutline0.m(str, "after", str2)
        };
    }

    public abstract Pair getMainAnimationWidthHeight();

    public abstract void setView(ShutdownAnimatedImageView shutdownAnimatedImageView);
}
