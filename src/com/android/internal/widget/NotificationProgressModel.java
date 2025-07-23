package com.android.internal.widget;

import android.app.Notification;
import android.os.Bundle;
import com.android.internal.util.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class NotificationProgressModel {
    public static final int INVALID_COLOR = 0;
    private static final String KEY_INDETERMINATE_COLOR = "indeterminateColor";
    private static final String KEY_IS_STYLED_BY_PROGRESS = "isStyledByProgress";
    private static final String KEY_POINTS = "points";
    private static final String KEY_PROGRESS = "progress";
    private static final String KEY_SEGMENTS = "segments";
    private static final String KEY_SEGMENTS_FALLBACK_COLOR = "segmentsFallColor";
    private final int mIndeterminateColor;
    private final boolean mIsStyledByProgress;
    private final List<Notification.ProgressStyle.Point> mPoints;
    private final int mProgress;
    private final List<Notification.ProgressStyle.Segment> mSegments;
    private final int mSegmentsFallbackColor;

    public NotificationProgressModel(List<Notification.ProgressStyle.Segment> list, List<Notification.ProgressStyle.Point> list2, int i, boolean z, int i2) {
        Preconditions.checkArgument(i >= 0);
        Preconditions.checkArgument(true ^ list.isEmpty());
        this.mSegments = list;
        this.mPoints = list2;
        this.mProgress = i;
        this.mIsStyledByProgress = z;
        this.mSegmentsFallbackColor = i2;
        this.mIndeterminateColor = 0;
    }

    public NotificationProgressModel(int i) {
        Preconditions.checkArgument(i != 0);
        this.mSegments = Collections.EMPTY_LIST;
        this.mPoints = Collections.EMPTY_LIST;
        this.mProgress = 0;
        this.mIsStyledByProgress = false;
        this.mSegmentsFallbackColor = 0;
        this.mIndeterminateColor = i;
    }

    public List<Notification.ProgressStyle.Segment> getSegments() {
        return this.mSegments;
    }

    public List<Notification.ProgressStyle.Point> getPoints() {
        return this.mPoints;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public int getProgressMax() {
        return this.mSegments.stream().mapToInt(new NotificationProgressBar$$ExternalSyntheticLambda1()).sum();
    }

    public boolean isStyledByProgress() {
        return this.mIsStyledByProgress;
    }

    public int getSegmentsFallbackColor() {
        return this.mSegmentsFallbackColor;
    }

    public int getIndeterminateColor() {
        return this.mIndeterminateColor;
    }

    public boolean isIndeterminate() {
        return this.mIndeterminateColor != 0;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        int i = this.mIndeterminateColor;
        if (i != 0) {
            bundle.putInt(KEY_INDETERMINATE_COLOR, i);
            return bundle;
        }
        bundle.putParcelableList(KEY_SEGMENTS, Notification.ProgressStyle.getProgressSegmentsAsBundleList(this.mSegments));
        bundle.putParcelableList(KEY_POINTS, Notification.ProgressStyle.getProgressPointsAsBundleList(this.mPoints));
        bundle.putInt("progress", this.mProgress);
        bundle.putBoolean(KEY_IS_STYLED_BY_PROGRESS, this.mIsStyledByProgress);
        int i2 = this.mSegmentsFallbackColor;
        if (i2 != 0) {
            bundle.putInt(KEY_SEGMENTS_FALLBACK_COLOR, i2);
        }
        return bundle;
    }

    public static NotificationProgressModel fromBundle(Bundle bundle) {
        int i = bundle.getInt(KEY_INDETERMINATE_COLOR, 0);
        if (i != 0) {
            return new NotificationProgressModel(i);
        }
        return new NotificationProgressModel(Notification.ProgressStyle.getProgressSegmentsFromBundleList(bundle.getParcelableArrayList(KEY_SEGMENTS, Bundle.class)), Notification.ProgressStyle.getProgressPointsFromBundleList(bundle.getParcelableArrayList(KEY_POINTS, Bundle.class)), bundle.getInt("progress"), bundle.getBoolean(KEY_IS_STYLED_BY_PROGRESS), bundle.getInt(KEY_SEGMENTS_FALLBACK_COLOR, 0));
    }

    public String toString() {
        return "NotificationProgressModel{mSegments=" + this.mSegments + ", mPoints=" + this.mPoints + ", mProgress=" + this.mProgress + ", mIsStyledByProgress=" + this.mIsStyledByProgress + ", mSegmentsFallbackColor=" + this.mSegmentsFallbackColor + ", mIndeterminateColor=" + this.mIndeterminateColor + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NotificationProgressModel notificationProgressModel = (NotificationProgressModel) obj;
            if (this.mProgress == notificationProgressModel.mProgress && this.mIsStyledByProgress == notificationProgressModel.mIsStyledByProgress && this.mSegmentsFallbackColor == notificationProgressModel.mSegmentsFallbackColor && this.mIndeterminateColor == notificationProgressModel.mIndeterminateColor && Objects.equals(this.mSegments, notificationProgressModel.mSegments) && Objects.equals(this.mPoints, notificationProgressModel.mPoints)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mSegments, this.mPoints, Integer.valueOf(this.mProgress), Boolean.valueOf(this.mIsStyledByProgress), Integer.valueOf(this.mSegmentsFallbackColor), Integer.valueOf(this.mIndeterminateColor));
    }
}
