package com.android.server.pm;

import android.content.pm.UserProperties;
import android.os.Bundle;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UserTypeDetails {
    public final int mAccessibilityString;
    public final int[] mBadgeColors;
    public final int[] mBadgeLabels;
    public final int mBadgeNoBackground;
    public final int mBadgePlain;
    public final int mBaseType;
    public final int[] mDarkThemeBadgeColors;
    public final List mDefaultCrossProfileIntentFilters;
    public final Bundle mDefaultRestrictions;
    public final Bundle mDefaultSecureSettings;
    public final int mDefaultUserInfoPropertyFlags;
    public final UserProperties mDefaultUserProperties;
    public final boolean mEnabled;
    public final int mIconBadge;
    public final int[] mLabels;
    public final int mMaxAllowed;
    public final int mMaxAllowedPerParent;
    public final String mName;
    public final int mStatusBarIcon;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        public int mBaseType;
        public String mName;
        public int mMaxAllowed = -1;
        public int mMaxAllowedPerParent = -1;
        public int mDefaultUserInfoPropertyFlags = 0;
        public Bundle mDefaultRestrictions = null;
        public Bundle mDefaultSecureSettings = null;
        public List mDefaultCrossProfileIntentFilters = null;
        public int mEnabled = 1;
        public int[] mLabels = null;
        public int[] mBadgeLabels = null;
        public int[] mBadgeColors = null;
        public int[] mDarkThemeBadgeColors = null;
        public int mIconBadge = 0;
        public int mBadgePlain = 0;
        public int mBadgeNoBackground = 0;
        public int mStatusBarIcon = 0;
        public int mAccessibilityString = 0;
        public UserProperties mDefaultUserProperties = null;
    }

    public UserTypeDetails(
            String str,
            boolean z,
            int i,
            int i2,
            int i3,
            int[] iArr,
            int i4,
            int i5,
            int i6,
            int i7,
            int i8,
            int[] iArr2,
            int[] iArr3,
            int[] iArr4,
            Bundle bundle,
            Bundle bundle2,
            List list,
            int i9,
            UserProperties userProperties) {
        this.mName = str;
        this.mEnabled = z;
        this.mMaxAllowed = i;
        this.mMaxAllowedPerParent = i4;
        this.mBaseType = i2;
        this.mDefaultUserInfoPropertyFlags = i3;
        this.mDefaultRestrictions = bundle;
        this.mDefaultSecureSettings = bundle2;
        this.mDefaultCrossProfileIntentFilters = list;
        this.mIconBadge = i5;
        this.mBadgePlain = i6;
        this.mBadgeNoBackground = i7;
        this.mStatusBarIcon = i8;
        this.mLabels = iArr;
        this.mBadgeLabels = iArr2;
        this.mBadgeColors = iArr3;
        this.mDarkThemeBadgeColors = iArr4;
        this.mAccessibilityString = i9;
        this.mDefaultUserProperties = userProperties;
    }

    public final int getDefaultUserInfoFlags() {
        return this.mBaseType | this.mDefaultUserInfoPropertyFlags;
    }

    public final boolean hasBadge() {
        return this.mIconBadge != 0;
    }

    public final boolean isProfile() {
        return (this.mBaseType & 4096) != 0;
    }
}
