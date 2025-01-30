package com.android.systemui.plugins;

import android.net.Uri;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(version = 6)
/* loaded from: classes2.dex */
public interface FalsingManager {
    public static final int HIGH_PENALTY = 3;
    public static final int LOW_PENALTY = 1;
    public static final int MODERATE_PENALTY = 2;
    public static final int NO_PENALTY = 0;
    public static final int VERSION = 6;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface FalsingBeliefListener {
        void onFalse();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface FalsingTapListener {
        void onAdditionalTapRequired();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @Retention(RetentionPolicy.SOURCE)
    public @interface Penalty {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ProximityEvent {
        boolean getCovered();

        long getTimestampNs();
    }

    void addFalsingBeliefListener(FalsingBeliefListener falsingBeliefListener);

    void addTapListener(FalsingTapListener falsingTapListener);

    void cleanupInternal();

    void dump(PrintWriter printWriter, String[] strArr);

    boolean isClassifierEnabled();

    boolean isFalseDoubleTap();

    boolean isFalseLongTap(int i);

    boolean isFalseTap(int i);

    boolean isFalseTouch(int i);

    boolean isProximityNear();

    boolean isReportingEnabled();

    boolean isSimpleTap();

    boolean isUnlockingDisabled();

    void onProximityEvent(ProximityEvent proximityEvent);

    void onSuccessfulUnlock();

    void removeFalsingBeliefListener(FalsingBeliefListener falsingBeliefListener);

    void removeTapListener(FalsingTapListener falsingTapListener);

    Uri reportRejectedTouch();

    boolean shouldEnforceBouncer();
}
