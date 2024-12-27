package com.android.server.wm;

import android.graphics.Rect;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.DisplayShape;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.PrivacyIndicatorBounds;
import android.view.RoundedCorners;
import android.view.WindowInsets;

import com.android.server.wm.utils.WmDisplayCutout;

public final class DisplayFrames {
    public final Rect mDisplayCutoutSafe;
    public int mHeight;
    public final InsetsState mInsetsState;
    public int mRotation;
    public final UdcCutoutPolicy mUdcCutoutPolicy;
    public final Rect mUnrestricted;
    public int mWidth;
    public static final int ID_DISPLAY_CUTOUT_LEFT =
            InsetsSource.createId((Object) null, 0, WindowInsets.Type.displayCutout());
    public static final int ID_DISPLAY_CUTOUT_TOP =
            InsetsSource.createId((Object) null, 1, WindowInsets.Type.displayCutout());
    public static final int ID_DISPLAY_CUTOUT_RIGHT =
            InsetsSource.createId((Object) null, 2, WindowInsets.Type.displayCutout());
    public static final int ID_DISPLAY_CUTOUT_BOTTOM =
            InsetsSource.createId((Object) null, 3, WindowInsets.Type.displayCutout());

    public DisplayFrames() {
        this.mUnrestricted = new Rect();
        this.mDisplayCutoutSafe = new Rect();
        this.mInsetsState = new InsetsState();
    }

    public DisplayFrames(
            InsetsState insetsState,
            DisplayInfo displayInfo,
            DisplayCutout displayCutout,
            RoundedCorners roundedCorners,
            PrivacyIndicatorBounds privacyIndicatorBounds,
            DisplayShape displayShape,
            UdcCutoutPolicy udcCutoutPolicy) {
        this.mUnrestricted = new Rect();
        this.mDisplayCutoutSafe = new Rect();
        this.mUdcCutoutPolicy = udcCutoutPolicy;
        this.mInsetsState = insetsState;
        update(
                displayInfo.rotation,
                displayInfo.logicalWidth,
                displayInfo.logicalHeight,
                displayCutout,
                roundedCorners,
                privacyIndicatorBounds,
                displayShape);
    }

    public final boolean update(
            int i,
            int i2,
            int i3,
            DisplayCutout displayCutout,
            RoundedCorners roundedCorners,
            PrivacyIndicatorBounds privacyIndicatorBounds,
            DisplayShape displayShape) {
        boolean z;
        Rect rect;
        PrivacyIndicatorBounds privacyIndicatorBounds2;
        InsetsState insetsState = this.mInsetsState;
        Rect rect2 = this.mDisplayCutoutSafe;
        UdcCutoutPolicy udcCutoutPolicy = this.mUdcCutoutPolicy;
        if (udcCutoutPolicy != null) {
            DisplayCutout displayCutout2 =
                    ((WmDisplayCutout)
                                    udcCutoutPolicy.mDisplayCutoutCache.getOrCompute(
                                            i, udcCutoutPolicy.mUdcCutout))
                            .mInner;
            DisplayFrames displayFrames = udcCutoutPolicy.mUdcDisplayFrames;
            if (displayFrames == null) {
                DisplayInfo displayInfo = new DisplayInfo();
                displayInfo.rotation = i;
                displayInfo.logicalWidth = i2;
                displayInfo.logicalHeight = i3;
                rect = rect2;
                udcCutoutPolicy.mUdcDisplayFrames =
                        new DisplayFrames(
                                new InsetsState(insetsState, true),
                                displayInfo,
                                displayCutout2,
                                roundedCorners,
                                privacyIndicatorBounds,
                                displayShape,
                                null);
                z = true;
            } else {
                rect = rect2;
                displayFrames.mInsetsState.set(insetsState, true);
                z = true;
                udcCutoutPolicy.mUdcDisplayFrames.update(
                        i,
                        i2,
                        i3,
                        displayCutout2,
                        roundedCorners,
                        privacyIndicatorBounds,
                        displayShape);
            }
        } else {
            z = true;
            rect = rect2;
        }
        if (this.mRotation == i
                && this.mWidth == i2
                && this.mHeight == i3
                && this.mInsetsState.getDisplayCutout().equals(displayCutout)
                && insetsState.getRoundedCorners().equals(roundedCorners)) {
            privacyIndicatorBounds2 = privacyIndicatorBounds;
            if (insetsState.getPrivacyIndicatorBounds().equals(privacyIndicatorBounds2)) {
                return false;
            }
        } else {
            privacyIndicatorBounds2 = privacyIndicatorBounds;
        }
        this.mRotation = i;
        this.mWidth = i2;
        this.mHeight = i3;
        Rect rect3 = this.mUnrestricted;
        rect3.set(0, 0, i2, i3);
        insetsState.setDisplayFrame(rect3);
        insetsState.setDisplayCutout(displayCutout);
        insetsState.setRoundedCorners(roundedCorners);
        insetsState.setPrivacyIndicatorBounds(privacyIndicatorBounds2);
        insetsState.setDisplayShape(displayShape);
        Rect rect4 = rect;
        insetsState.getDisplayCutoutSafe(rect4);
        int i4 = rect4.left;
        int i5 = rect3.left;
        int i6 = ID_DISPLAY_CUTOUT_LEFT;
        if (i4 > i5) {
            insetsState
                    .getOrCreateSource(i6, WindowInsets.Type.displayCutout())
                    .setFrame(rect3.left, rect3.top, rect4.left, rect3.bottom)
                    .updateSideHint(rect3);
        } else {
            insetsState.removeSource(i6);
        }
        int i7 = rect4.top;
        int i8 = rect3.top;
        int i9 = ID_DISPLAY_CUTOUT_TOP;
        if (i7 > i8) {
            insetsState
                    .getOrCreateSource(i9, WindowInsets.Type.displayCutout())
                    .setFrame(rect3.left, rect3.top, rect3.right, rect4.top)
                    .updateSideHint(rect3);
        } else {
            insetsState.removeSource(i9);
        }
        int i10 = rect4.right;
        int i11 = rect3.right;
        int i12 = ID_DISPLAY_CUTOUT_RIGHT;
        if (i10 < i11) {
            insetsState
                    .getOrCreateSource(i12, WindowInsets.Type.displayCutout())
                    .setFrame(rect4.right, rect3.top, rect3.right, rect3.bottom)
                    .updateSideHint(rect3);
        } else {
            insetsState.removeSource(i12);
        }
        int i13 = rect4.bottom;
        int i14 = rect3.bottom;
        int i15 = ID_DISPLAY_CUTOUT_BOTTOM;
        if (i13 < i14) {
            insetsState
                    .getOrCreateSource(i15, WindowInsets.Type.displayCutout())
                    .setFrame(rect3.left, rect4.bottom, rect3.right, rect3.bottom)
                    .updateSideHint(rect3);
        } else {
            insetsState.removeSource(i15);
        }
        return z;
    }
}
