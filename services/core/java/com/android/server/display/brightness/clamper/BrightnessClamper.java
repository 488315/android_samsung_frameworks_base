package com.android.server.display.brightness.clamper;

import android.os.Handler;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BrightnessClamper {
    public final BrightnessClamperController.ClamperChangeListener mChangeListener;
    public final Handler mHandler;
    public float mBrightnessCap = 1.0f;
    public boolean mIsActive = false;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Type {
        public static final /* synthetic */ Type[] $VALUES;
        public static final Type POWER;
        public static final Type THERMAL;
        public static final Type WEAR_BEDTIME_MODE;

        static {
            Type type = new Type("THERMAL", 0);
            THERMAL = type;
            Type type2 = new Type("POWER", 1);
            POWER = type2;
            Type type3 = new Type("WEAR_BEDTIME_MODE", 2);
            WEAR_BEDTIME_MODE = type3;
            $VALUES = new Type[] {type, type2, type3};
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }
    }

    public BrightnessClamper(
            Handler handler,
            BrightnessClamperController.ClamperChangeListener clamperChangeListener) {
        this.mHandler = handler;
        this.mChangeListener = clamperChangeListener;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("BrightnessClamper:" + getType());
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                KillPolicyManager$$ExternalSyntheticOutline0.m(
                        new StringBuilder(" mBrightnessCap: "),
                        this.mBrightnessCap,
                        printWriter,
                        " mIsActive: "),
                this.mIsActive,
                printWriter);
    }

    public abstract Type getType();

    public abstract void onDeviceConfigChanged();

    public abstract void onDisplayChanged(Object obj);

    public abstract void stop();
}
