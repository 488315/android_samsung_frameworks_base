package com.android.server.wm;

import java.util.Objects;

public final class DisplayWindowSettings$SettingsProvider$SettingsEntry {
    public Boolean mDontMoveToTop;
    public Integer mFixedToUserRotation;
    public int mForcedDensity;
    public int mForcedHeight;
    public Integer mForcedScalingMode;
    public int mForcedWidth;
    public Boolean mIgnoreDisplayCutout;
    public Boolean mIgnoreOrientationRequest;
    public Integer mImePolicy;
    public Boolean mIsHomeSupported;
    public Boolean mShouldShowSystemDecors;
    public Boolean mShouldShowWithInsecureKeyguard;
    public Integer mUserRotation;
    public Integer mUserRotationMode;
    public int mWindowingMode = 0;
    public int mRemoveContentMode = 0;

    public DisplayWindowSettings$SettingsProvider$SettingsEntry() {}

    public DisplayWindowSettings$SettingsProvider$SettingsEntry(
            DisplayWindowSettings$SettingsProvider$SettingsEntry
                    displayWindowSettings$SettingsProvider$SettingsEntry) {
        setTo(displayWindowSettings$SettingsProvider$SettingsEntry);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null
                || DisplayWindowSettings$SettingsProvider$SettingsEntry.class != obj.getClass()) {
            return false;
        }
        DisplayWindowSettings$SettingsProvider$SettingsEntry
                displayWindowSettings$SettingsProvider$SettingsEntry =
                        (DisplayWindowSettings$SettingsProvider$SettingsEntry) obj;
        return this.mWindowingMode
                        == displayWindowSettings$SettingsProvider$SettingsEntry.mWindowingMode
                && this.mForcedWidth
                        == displayWindowSettings$SettingsProvider$SettingsEntry.mForcedWidth
                && this.mForcedHeight
                        == displayWindowSettings$SettingsProvider$SettingsEntry.mForcedHeight
                && this.mForcedDensity
                        == displayWindowSettings$SettingsProvider$SettingsEntry.mForcedDensity
                && this.mRemoveContentMode
                        == displayWindowSettings$SettingsProvider$SettingsEntry.mRemoveContentMode
                && Objects.equals(
                        this.mUserRotationMode,
                        displayWindowSettings$SettingsProvider$SettingsEntry.mUserRotationMode)
                && Objects.equals(
                        this.mUserRotation,
                        displayWindowSettings$SettingsProvider$SettingsEntry.mUserRotation)
                && Objects.equals(
                        this.mForcedScalingMode,
                        displayWindowSettings$SettingsProvider$SettingsEntry.mForcedScalingMode)
                && Objects.equals(
                        this.mShouldShowWithInsecureKeyguard,
                        displayWindowSettings$SettingsProvider$SettingsEntry
                                .mShouldShowWithInsecureKeyguard)
                && Objects.equals(
                        this.mShouldShowSystemDecors,
                        displayWindowSettings$SettingsProvider$SettingsEntry
                                .mShouldShowSystemDecors)
                && Objects.equals(
                        this.mIsHomeSupported,
                        displayWindowSettings$SettingsProvider$SettingsEntry.mIsHomeSupported)
                && Objects.equals(
                        this.mImePolicy,
                        displayWindowSettings$SettingsProvider$SettingsEntry.mImePolicy)
                && Objects.equals(
                        this.mFixedToUserRotation,
                        displayWindowSettings$SettingsProvider$SettingsEntry.mFixedToUserRotation)
                && Objects.equals(
                        this.mIgnoreOrientationRequest,
                        displayWindowSettings$SettingsProvider$SettingsEntry
                                .mIgnoreOrientationRequest)
                && Objects.equals(
                        this.mIgnoreDisplayCutout,
                        displayWindowSettings$SettingsProvider$SettingsEntry.mIgnoreDisplayCutout)
                && Objects.equals(
                        this.mDontMoveToTop,
                        displayWindowSettings$SettingsProvider$SettingsEntry.mDontMoveToTop);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mWindowingMode),
                this.mUserRotationMode,
                this.mUserRotation,
                Integer.valueOf(this.mForcedWidth),
                Integer.valueOf(this.mForcedHeight),
                Integer.valueOf(this.mForcedDensity),
                this.mForcedScalingMode,
                Integer.valueOf(this.mRemoveContentMode),
                this.mShouldShowWithInsecureKeyguard,
                this.mShouldShowSystemDecors,
                this.mIsHomeSupported,
                this.mImePolicy,
                this.mFixedToUserRotation,
                this.mIgnoreOrientationRequest,
                this.mIgnoreDisplayCutout,
                this.mDontMoveToTop);
    }

    public final boolean isEmpty() {
        return this.mWindowingMode == 0
                && this.mUserRotationMode == null
                && this.mUserRotation == null
                && this.mForcedWidth == 0
                && this.mForcedHeight == 0
                && this.mForcedDensity == 0
                && this.mForcedScalingMode == null
                && this.mRemoveContentMode == 0
                && this.mShouldShowWithInsecureKeyguard == null
                && this.mShouldShowSystemDecors == null
                && this.mIsHomeSupported == null
                && this.mImePolicy == null
                && this.mFixedToUserRotation == null
                && this.mIgnoreOrientationRequest == null
                && this.mIgnoreDisplayCutout == null
                && this.mDontMoveToTop == null;
    }

    public final boolean setTo(
            DisplayWindowSettings$SettingsProvider$SettingsEntry
                    displayWindowSettings$SettingsProvider$SettingsEntry) {
        boolean z;
        int i = displayWindowSettings$SettingsProvider$SettingsEntry.mWindowingMode;
        if (i != this.mWindowingMode) {
            this.mWindowingMode = i;
            z = true;
        } else {
            z = false;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mUserRotationMode,
                this.mUserRotationMode)) {
            this.mUserRotationMode =
                    displayWindowSettings$SettingsProvider$SettingsEntry.mUserRotationMode;
            z = true;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mUserRotation,
                this.mUserRotation)) {
            this.mUserRotation = displayWindowSettings$SettingsProvider$SettingsEntry.mUserRotation;
            z = true;
        }
        int i2 = displayWindowSettings$SettingsProvider$SettingsEntry.mForcedWidth;
        if (i2 != this.mForcedWidth) {
            this.mForcedWidth = i2;
            z = true;
        }
        int i3 = displayWindowSettings$SettingsProvider$SettingsEntry.mForcedHeight;
        if (i3 != this.mForcedHeight) {
            this.mForcedHeight = i3;
            z = true;
        }
        int i4 = displayWindowSettings$SettingsProvider$SettingsEntry.mForcedDensity;
        if (i4 != this.mForcedDensity) {
            this.mForcedDensity = i4;
            z = true;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mForcedScalingMode,
                this.mForcedScalingMode)) {
            this.mForcedScalingMode =
                    displayWindowSettings$SettingsProvider$SettingsEntry.mForcedScalingMode;
            z = true;
        }
        int i5 = displayWindowSettings$SettingsProvider$SettingsEntry.mRemoveContentMode;
        if (i5 != this.mRemoveContentMode) {
            this.mRemoveContentMode = i5;
            z = true;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry
                        .mShouldShowWithInsecureKeyguard,
                this.mShouldShowWithInsecureKeyguard)) {
            this.mShouldShowWithInsecureKeyguard =
                    displayWindowSettings$SettingsProvider$SettingsEntry
                            .mShouldShowWithInsecureKeyguard;
            z = true;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mShouldShowSystemDecors,
                this.mShouldShowSystemDecors)) {
            this.mShouldShowSystemDecors =
                    displayWindowSettings$SettingsProvider$SettingsEntry.mShouldShowSystemDecors;
            z = true;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mIsHomeSupported,
                this.mIsHomeSupported)) {
            this.mIsHomeSupported =
                    displayWindowSettings$SettingsProvider$SettingsEntry.mIsHomeSupported;
            z = true;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mImePolicy, this.mImePolicy)) {
            this.mImePolicy = displayWindowSettings$SettingsProvider$SettingsEntry.mImePolicy;
            z = true;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mFixedToUserRotation,
                this.mFixedToUserRotation)) {
            this.mFixedToUserRotation =
                    displayWindowSettings$SettingsProvider$SettingsEntry.mFixedToUserRotation;
            z = true;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mIgnoreOrientationRequest,
                this.mIgnoreOrientationRequest)) {
            this.mIgnoreOrientationRequest =
                    displayWindowSettings$SettingsProvider$SettingsEntry.mIgnoreOrientationRequest;
            z = true;
        }
        if (!Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mIgnoreDisplayCutout,
                this.mIgnoreDisplayCutout)) {
            this.mIgnoreDisplayCutout =
                    displayWindowSettings$SettingsProvider$SettingsEntry.mIgnoreDisplayCutout;
            z = true;
        }
        if (Objects.equals(
                displayWindowSettings$SettingsProvider$SettingsEntry.mDontMoveToTop,
                this.mDontMoveToTop)) {
            return z;
        }
        this.mDontMoveToTop = displayWindowSettings$SettingsProvider$SettingsEntry.mDontMoveToTop;
        return true;
    }

    public final String toString() {
        return "SettingsEntry{mWindowingMode="
                + this.mWindowingMode
                + ", mUserRotationMode="
                + this.mUserRotationMode
                + ", mUserRotation="
                + this.mUserRotation
                + ", mForcedWidth="
                + this.mForcedWidth
                + ", mForcedHeight="
                + this.mForcedHeight
                + ", mForcedDensity="
                + this.mForcedDensity
                + ", mForcedScalingMode="
                + this.mForcedScalingMode
                + ", mRemoveContentMode="
                + this.mRemoveContentMode
                + ", mShouldShowWithInsecureKeyguard="
                + this.mShouldShowWithInsecureKeyguard
                + ", mShouldShowSystemDecors="
                + this.mShouldShowSystemDecors
                + ", mIsHomeSupported="
                + this.mIsHomeSupported
                + ", mShouldShowIme="
                + this.mImePolicy
                + ", mFixedToUserRotation="
                + this.mFixedToUserRotation
                + ", mIgnoreOrientationRequest="
                + this.mIgnoreOrientationRequest
                + ", mIgnoreDisplayCutout="
                + this.mIgnoreDisplayCutout
                + ", mDontMoveToTop="
                + this.mDontMoveToTop
                + '}';
    }
}
