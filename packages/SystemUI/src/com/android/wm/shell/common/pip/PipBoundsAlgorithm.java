package com.android.wm.shell.common.pip;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Rational;
import android.util.Size;
import android.view.Gravity;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipBoundsAlgorithm {
    public float mDefaultAspectRatio;
    public int mDefaultStackGravity;
    public float mMaxAspectRatio;
    public float mMinAspectRatio;
    public final PipBoundsState mPipBoundsState;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public final PipKeepClearAlgorithmInterface mPipKeepClearAlgorithm;
    public final SizeSpecSource mSizeSpecSource;
    public final PipSnapAlgorithm mSnapAlgorithm;

    public PipBoundsAlgorithm(Context context, PipBoundsState pipBoundsState, PipSnapAlgorithm pipSnapAlgorithm, PipKeepClearAlgorithmInterface pipKeepClearAlgorithmInterface, PipDisplayLayoutState pipDisplayLayoutState, SizeSpecSource sizeSpecSource) {
        this.mPipBoundsState = pipBoundsState;
        this.mSnapAlgorithm = pipSnapAlgorithm;
        this.mPipKeepClearAlgorithm = pipKeepClearAlgorithmInterface;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mSizeSpecSource = sizeSpecSource;
        reloadResources(context);
        pipBoundsState.setAspectRatio(this.mDefaultAspectRatio);
    }

    public static Rect getValidSourceHintRect(PictureInPictureParams pictureInPictureParams, Rect rect) {
        Rect sourceRectHint = (pictureInPictureParams == null || !pictureInPictureParams.hasSourceBoundsHint()) ? null : pictureInPictureParams.getSourceRectHint();
        if (sourceRectHint == null || !rect.contains(sourceRectHint)) {
            return null;
        }
        return sourceRectHint;
    }

    public static boolean isSourceRectHintValidForEnterPip(Rect rect, Rect rect2) {
        if (rect == null || rect.isEmpty()) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 195527804713777749L, 0, null);
            }
            return false;
        }
        if (rect.width() <= rect2.width() || rect.height() <= rect2.height()) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -3421291399359087650L, 0, String.valueOf(rect), String.valueOf(rect2));
            }
            return false;
        }
        if (PictureInPictureParams.isSameAspectRatio(rect2, new Rational(rect.width(), rect.height()))) {
            return true;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 4209504781583464887L, 0, String.valueOf(rect), String.valueOf(rect2));
        }
        return false;
    }

    public final Rect adjustNormalBoundsToFitMenu(Rect rect, Size size) {
        int height;
        int round;
        if (size == null) {
            return rect;
        }
        if (rect.width() >= size.getWidth() && rect.height() >= size.getHeight()) {
            return rect;
        }
        Rect rect2 = new Rect();
        boolean z = size.getWidth() > rect.width();
        boolean z2 = size.getHeight() > rect.height();
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (z && z2) {
            if (size.getWidth() / rect.width() > size.getHeight() / rect.height()) {
                round = size.getWidth();
                height = Math.round(round / pipBoundsState.mAspectRatio);
            } else {
                height = size.getHeight();
                round = Math.round(height * pipBoundsState.mAspectRatio);
            }
        } else if (z) {
            round = size.getWidth();
            height = Math.round(round / pipBoundsState.mAspectRatio);
        } else {
            height = size.getHeight();
            round = Math.round(height * pipBoundsState.mAspectRatio);
        }
        rect2.set(0, 0, round, height);
        transformBoundsToAspectRatio(pipBoundsState.mAspectRatio, rect2, true, true);
        return rect2;
    }

    public final void dump(PrintWriter printWriter, String str) {
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        printWriter.println(str + "PipBoundsAlgorithm");
        printWriter.println(m + "mDefaultAspectRatio=" + this.mDefaultAspectRatio);
        printWriter.println(m + "mMinAspectRatio=" + this.mMinAspectRatio);
        printWriter.println(m + "mMaxAspectRatio=" + this.mMaxAspectRatio);
        printWriter.println(m + "mDefaultStackGravity=" + this.mDefaultStackGravity);
        printWriter.println(m + "mSnapAlgorithm" + this.mSnapAlgorithm);
    }

    public final Rect getDefaultBounds() {
        return getDefaultBounds(-1.0f, null);
    }

    public final Rect getEntryDestinationBounds() {
        Rect entryDestinationBoundsIgnoringKeepClearAreas = getEntryDestinationBoundsIgnoringKeepClearAreas();
        Rect rect = new Rect();
        getInsetBounds(rect);
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        return ((PhonePipKeepClearAlgorithm) this.mPipKeepClearAlgorithm).findUnoccludedPosition(entryDestinationBoundsIgnoringKeepClearAreas, pipBoundsState.mRestrictedKeepClearAreas, pipBoundsState.getUnrestrictedKeepClearAreas(), rect);
    }

    public final Rect getEntryDestinationBoundsIgnoringKeepClearAreas() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        PipBoundsState.PipReentryState pipReentryState = pipBoundsState.mPipReentryState;
        Rect defaultBounds = getDefaultBounds();
        if (pipReentryState != null) {
            float f = pipBoundsState.mMaxSize.x;
            float f2 = pipReentryState.mBoundsScale;
            defaultBounds.set(getDefaultBounds(pipReentryState.mSnapFraction, new Size(Math.round(f * f2), Math.round(pipBoundsState.mMaxSize.y * f2))));
        }
        return transformBoundsToAspectRatioIfValid(pipBoundsState.mAspectRatio, defaultBounds, false, pipReentryState != null && defaultBounds.width() > ((PhoneSizeSpecSource) this.mSizeSpecSource).mDefaultMinWidth);
    }

    public final void getInsetBounds(Rect rect) {
        rect.set(this.mPipDisplayLayoutState.getInsetBounds());
    }

    public final Size getMinimalSize(ActivityInfo activityInfo) {
        ActivityInfo.WindowLayout windowLayout;
        if (activityInfo == null || (windowLayout = activityInfo.windowLayout) == null || windowLayout.minWidth <= 0 || windowLayout.minHeight <= 0) {
            return null;
        }
        int i = windowLayout.minWidth;
        SizeSpecSource sizeSpecSource = this.mSizeSpecSource;
        return new Size(Math.max(i, sizeSpecSource.getOverrideMinEdgeSize()), Math.max(windowLayout.minHeight, sizeSpecSource.getOverrideMinEdgeSize()));
    }

    public final Rect getMovementBounds(Rect rect, boolean z) {
        int i;
        Rect rect2 = new Rect();
        getInsetBounds(rect2);
        if (z) {
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            if (pipBoundsState.mIsImeShowing) {
                i = pipBoundsState.mImeHeight;
                getMovementBounds(rect, rect2, rect2, i);
                return rect2;
            }
        }
        i = 0;
        getMovementBounds(rect, rect2, rect2, i);
        return rect2;
    }

    public final float getSnapFraction(Rect rect) {
        return this.mSnapAlgorithm.getSnapFraction(0, rect, getMovementBounds(rect, true));
    }

    public final boolean isValidPictureInPictureAspectRatio(float f) {
        return Float.compare(this.mMinAspectRatio, f) <= 0 && Float.compare(f, this.mMaxAspectRatio) <= 0;
    }

    public final void reloadResources(Context context) {
        Resources resources = context.getResources();
        this.mDefaultAspectRatio = resources.getFloat(R.dimen.config_pictureInPictureDefaultAspectRatio);
        this.mDefaultStackGravity = resources.getInteger(R.integer.config_defaultPictureInPictureGravity);
        this.mMinAspectRatio = resources.getFloat(android.R.dimen.conversation_icon_circle_start);
        this.mMaxAspectRatio = resources.getFloat(android.R.dimen.conversation_header_expanded_padding_end);
    }

    public final void transformBoundsToAspectRatio(float f, Rect rect, boolean z, boolean z2) {
        Size sizeForAspectRatio;
        float snapFraction = this.mSnapAlgorithm.getSnapFraction(this.mPipBoundsState.mStashedState, rect, getMovementBounds(rect, true));
        SizeSpecSource sizeSpecSource = this.mSizeSpecSource;
        if (z || z2) {
            sizeForAspectRatio = ((PhoneSizeSpecSource) sizeSpecSource).getSizeForAspectRatio(f, new Size(rect.width(), rect.height()));
        } else {
            sizeForAspectRatio = ((PhoneSizeSpecSource) sizeSpecSource).getDefaultSize(f);
        }
        int centerX = (int) (rect.centerX() - (sizeForAspectRatio.getWidth() / 2.0f));
        int centerY = (int) (rect.centerY() - (sizeForAspectRatio.getHeight() / 2.0f));
        rect.set(centerX, centerY, sizeForAspectRatio.getWidth() + centerX, sizeForAspectRatio.getHeight() + centerY);
        PipSnapAlgorithm.applySnapFraction(rect, getMovementBounds(rect, true), snapFraction);
    }

    public final Rect transformBoundsToAspectRatioIfValid(float f, Rect rect, boolean z, boolean z2) {
        Rect rect2 = new Rect(rect);
        if (isValidPictureInPictureAspectRatio(f)) {
            transformBoundsToAspectRatio(f, rect2, z, z2);
        }
        return rect2;
    }

    public final Rect getDefaultBounds(float f, Size size) {
        Rect rect = new Rect();
        PipSnapAlgorithm pipSnapAlgorithm = this.mSnapAlgorithm;
        if (f != -1.0f && size != null) {
            rect.set(0, 0, size.getWidth(), size.getHeight());
            Rect movementBounds = getMovementBounds(rect, true);
            pipSnapAlgorithm.getClass();
            PipSnapAlgorithm.applySnapFraction(rect, movementBounds, f);
            return rect;
        }
        Rect rect2 = new Rect();
        getInsetBounds(rect2);
        Size defaultSize = ((PhoneSizeSpecSource) this.mSizeSpecSource).getDefaultSize(this.mDefaultAspectRatio);
        if (f != -1.0f) {
            rect.set(0, 0, defaultSize.getWidth(), defaultSize.getHeight());
            Rect movementBounds2 = getMovementBounds(rect, true);
            pipSnapAlgorithm.getClass();
            PipSnapAlgorithm.applySnapFraction(rect, movementBounds2, f);
            return rect;
        }
        int i = this.mDefaultStackGravity;
        int width = defaultSize.getWidth();
        int height = defaultSize.getHeight();
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Gravity.apply(i, width, height, rect2, 0, Math.max(pipBoundsState.mIsImeShowing ? pipBoundsState.mImeHeight : 0, 0), rect);
        return rect;
    }

    public static void getMovementBounds(Rect rect, Rect rect2, Rect rect3, int i) {
        rect3.set(rect2);
        rect3.right = Math.max(rect2.left, rect2.right - rect.width());
        rect3.bottom = Math.max(rect2.top, rect2.bottom - rect.height()) - i;
    }
}
