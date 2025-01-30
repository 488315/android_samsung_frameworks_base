package com.android.server.wm;

import android.graphics.Rect;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.DisplayShape;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.PrivacyIndicatorBounds;
import android.view.RoundedCorners;
import android.view.WindowInsets;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class DisplayFrames {
    public final Rect mDisplayCutoutSafe;
    public int mHeight;
    public final InsetsState mInsetsState;
    public int mRotation;
    public UdcCutoutPolicy mUdcCutoutPolicy;
    public final Rect mUnrestricted;
    public int mWidth;
    public static final int ID_DISPLAY_CUTOUT_LEFT = InsetsSource.createId((Object) null, 0, WindowInsets.Type.displayCutout());
    public static final int ID_DISPLAY_CUTOUT_TOP = InsetsSource.createId((Object) null, 1, WindowInsets.Type.displayCutout());
    public static final int ID_DISPLAY_CUTOUT_RIGHT = InsetsSource.createId((Object) null, 2, WindowInsets.Type.displayCutout());
    public static final int ID_DISPLAY_CUTOUT_BOTTOM = InsetsSource.createId((Object) null, 3, WindowInsets.Type.displayCutout());

    public DisplayFrames(InsetsState insetsState, DisplayInfo displayInfo, DisplayCutout displayCutout, RoundedCorners roundedCorners, PrivacyIndicatorBounds privacyIndicatorBounds, DisplayShape displayShape) {
        this(insetsState, displayInfo, displayCutout, roundedCorners, privacyIndicatorBounds, displayShape, null);
    }

    public DisplayFrames(InsetsState insetsState, DisplayInfo displayInfo, DisplayCutout displayCutout, RoundedCorners roundedCorners, PrivacyIndicatorBounds privacyIndicatorBounds, DisplayShape displayShape, UdcCutoutPolicy udcCutoutPolicy) {
        this.mUnrestricted = new Rect();
        this.mDisplayCutoutSafe = new Rect();
        this.mUdcCutoutPolicy = udcCutoutPolicy;
        this.mInsetsState = insetsState;
        update(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight, displayCutout, roundedCorners, privacyIndicatorBounds, displayShape);
    }

    public DisplayFrames() {
        this.mUnrestricted = new Rect();
        this.mDisplayCutoutSafe = new Rect();
        this.mInsetsState = new InsetsState();
    }

    public boolean update(int i, int i2, int i3, DisplayCutout displayCutout, RoundedCorners roundedCorners, PrivacyIndicatorBounds privacyIndicatorBounds, DisplayShape displayShape) {
        Rect rect;
        InsetsState insetsState = this.mInsetsState;
        Rect rect2 = this.mDisplayCutoutSafe;
        UdcCutoutPolicy udcCutoutPolicy = this.mUdcCutoutPolicy;
        if (udcCutoutPolicy != null) {
            rect = rect2;
            udcCutoutPolicy.onDisplayInfoUpdated(insetsState, i, i2, i3, roundedCorners, privacyIndicatorBounds, displayShape);
        } else {
            rect = rect2;
        }
        if (this.mRotation == i && this.mWidth == i2 && this.mHeight == i3 && this.mInsetsState.getDisplayCutout().equals(displayCutout) && insetsState.getRoundedCorners().equals(roundedCorners) && insetsState.getPrivacyIndicatorBounds().equals(privacyIndicatorBounds)) {
            return false;
        }
        this.mRotation = i;
        this.mWidth = i2;
        this.mHeight = i3;
        Rect rect3 = this.mUnrestricted;
        rect3.set(0, 0, i2, i3);
        insetsState.setDisplayFrame(rect3);
        insetsState.setDisplayCutout(displayCutout);
        insetsState.setRoundedCorners(roundedCorners);
        insetsState.setPrivacyIndicatorBounds(privacyIndicatorBounds);
        insetsState.setDisplayShape(displayShape);
        Rect rect4 = rect;
        insetsState.getDisplayCutoutSafe(rect4);
        if (rect4.left > rect3.left) {
            insetsState.getOrCreateSource(ID_DISPLAY_CUTOUT_LEFT, WindowInsets.Type.displayCutout()).setFrame(rect3.left, rect3.top, rect4.left, rect3.bottom);
        } else {
            insetsState.removeSource(ID_DISPLAY_CUTOUT_LEFT);
        }
        if (rect4.top > rect3.top) {
            insetsState.getOrCreateSource(ID_DISPLAY_CUTOUT_TOP, WindowInsets.Type.displayCutout()).setFrame(rect3.left, rect3.top, rect3.right, rect4.top);
        } else {
            insetsState.removeSource(ID_DISPLAY_CUTOUT_TOP);
        }
        if (rect4.right < rect3.right) {
            insetsState.getOrCreateSource(ID_DISPLAY_CUTOUT_RIGHT, WindowInsets.Type.displayCutout()).setFrame(rect4.right, rect3.top, rect3.right, rect3.bottom);
        } else {
            insetsState.removeSource(ID_DISPLAY_CUTOUT_RIGHT);
        }
        if (rect4.bottom < rect3.bottom) {
            insetsState.getOrCreateSource(ID_DISPLAY_CUTOUT_BOTTOM, WindowInsets.Type.displayCutout()).setFrame(rect3.left, rect4.bottom, rect3.right, rect3.bottom);
            return true;
        }
        insetsState.removeSource(ID_DISPLAY_CUTOUT_BOTTOM);
        return true;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        protoOutputStream.end(protoOutputStream.start(j));
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.println(str + "DisplayFrames w=" + this.mWidth + " h=" + this.mHeight + " r=" + this.mRotation);
    }
}
