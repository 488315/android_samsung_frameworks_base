package com.android.server.pm.pkg;

import android.annotation.CurrentTimeMillisLong;
import android.annotation.NonNull;
import android.content.ComponentName;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public final class ArchiveState {
    public final List mActivityInfos;
    public final long mArchiveTimeMillis;
    public final String mInstallerTitle;

    public final class ArchiveActivityInfo {
        public final Path mIconBitmap;
        public final Path mMonochromeIconBitmap;
        public final ComponentName mOriginalComponentName;
        public final String mTitle;

        public ArchiveActivityInfo(String str, ComponentName componentName, Path path, Path path2) {
            this.mTitle = str;
            AnnotationValidations.validate(NonNull.class, (NonNull) null, str);
            this.mOriginalComponentName = componentName;
            AnnotationValidations.validate(NonNull.class, (NonNull) null, componentName);
            this.mIconBitmap = path;
            this.mMonochromeIconBitmap = path2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || ArchiveActivityInfo.class != obj.getClass()) {
                return false;
            }
            ArchiveActivityInfo archiveActivityInfo = (ArchiveActivityInfo) obj;
            return Objects.equals(this.mTitle, archiveActivityInfo.mTitle)
                    && Objects.equals(
                            this.mOriginalComponentName, archiveActivityInfo.mOriginalComponentName)
                    && Objects.equals(this.mIconBitmap, archiveActivityInfo.mIconBitmap)
                    && Objects.equals(
                            this.mMonochromeIconBitmap, archiveActivityInfo.mMonochromeIconBitmap);
        }

        public final int hashCode() {
            return Objects.hashCode(this.mMonochromeIconBitmap)
                    + ((Objects.hashCode(this.mIconBitmap)
                                    + ((Objects.hashCode(this.mOriginalComponentName)
                                                    + ((Objects.hashCode(this.mTitle) + 31) * 31))
                                            * 31))
                            * 31);
        }

        public final String toString() {
            return "ArchiveActivityInfo { title = "
                    + this.mTitle
                    + ", originalComponentName = "
                    + this.mOriginalComponentName
                    + ", iconBitmap = "
                    + this.mIconBitmap
                    + ", monochromeIconBitmap = "
                    + this.mMonochromeIconBitmap
                    + " }";
        }
    }

    public ArchiveState(String str, long j, List list) {
        this.mActivityInfos = list;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, list);
        this.mInstallerTitle = str;
        AnnotationValidations.validate(NonNull.class, (NonNull) null, str);
        this.mArchiveTimeMillis = j;
        AnnotationValidations.validate(CurrentTimeMillisLong.class, (Annotation) null, j);
    }

    public ArchiveState(String str, List list) {
        this(str, System.currentTimeMillis(), list);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ArchiveState.class != obj.getClass()) {
            return false;
        }
        ArchiveState archiveState = (ArchiveState) obj;
        return Objects.equals(this.mActivityInfos, archiveState.mActivityInfos)
                && Objects.equals(this.mInstallerTitle, archiveState.mInstallerTitle)
                && this.mArchiveTimeMillis == archiveState.mArchiveTimeMillis;
    }

    public final int hashCode() {
        return Long.hashCode(this.mArchiveTimeMillis)
                + ((Objects.hashCode(this.mInstallerTitle)
                                + ((Objects.hashCode(this.mActivityInfos) + 31) * 31))
                        * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ArchiveState { activityInfos = ");
        sb.append(this.mActivityInfos);
        sb.append(", installerTitle = ");
        sb.append(this.mInstallerTitle);
        sb.append(", archiveTimeMillis = ");
        return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.mArchiveTimeMillis, " }");
    }
}
