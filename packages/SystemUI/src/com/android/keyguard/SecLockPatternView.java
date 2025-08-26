package com.android.keyguard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SecLockPatternView extends LockPatternView {
    public Bitmap mBitmapError;
    public int mBitmapHeight;
    public Bitmap mBitmapRegular;
    public Bitmap mBitmapSuccess;
    public int mBitmapWidth;
    public final Matrix mCircleMatrix;
    public boolean mDecoPatternEnabled;
    public boolean mIsWhiteWp;

    public SecLockPatternView(Context context) {
        this(context, null);
    }

    public final void addCellToPattern(LockPatternView.Cell cell) {
        super.addCellToPattern(cell);
    }

    public final Bitmap getScaledBitmapFor(int i) {
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getContext().getResources(), i);
        int dimension = (int) getContext().getResources().getDimension(R.dimen.theme_keyguard_pattern_dot_size);
        if (bitmapDecodeResource != null) {
            return Bitmap.createScaledBitmap(bitmapDecodeResource, dimension, dimension, true);
        }
        ClockEventController$$ExternalSyntheticOutline0.m(i, "getScaledBitmapFor() return null - bitmap is null resId = ", "SecLockPatternView");
        return null;
    }

    public final void handleActionMove(MotionEvent motionEvent) {
        if (!this.mDecoPatternEnabled) {
            super.handleActionMove(motionEvent);
            return;
        }
        float f = ((LockPatternView) this).mSquareWidth * 0.05f;
        int historySize = motionEvent.getHistorySize();
        ((LockPatternView) this).mTmpInvalidateRect.setEmpty();
        int i = 0;
        boolean z = false;
        while (i < historySize + 1) {
            float historicalX = i < historySize ? motionEvent.getHistoricalX(i) : motionEvent.getX();
            float historicalY = i < historySize ? motionEvent.getHistoricalY(i) : motionEvent.getY();
            LockPatternView.Cell cellDetectAndAddHit = detectAndAddHit(historicalX, historicalY);
            int size = ((LockPatternView) this).mPattern.size();
            if (cellDetectAndAddHit != null && size == 1) {
                ((LockPatternView) this).mPatternInProgress = true;
                notifyPatternStarted();
            }
            float fAbs = Math.abs(historicalX - ((LockPatternView) this).mInProgressX);
            float fAbs2 = Math.abs(historicalY - ((LockPatternView) this).mInProgressY);
            if (fAbs > 0.0f || fAbs2 > 0.0f) {
                z = true;
            }
            if (((LockPatternView) this).mPatternInProgress && size > 0) {
                LockPatternView.Cell cell = (LockPatternView.Cell) ((LockPatternView) this).mPattern.get(size - 1);
                float centerXForColumn = getCenterXForColumn(cell.getColumn());
                float centerYForRow = getCenterYForRow(cell.getRow());
                float fMin = (Math.min(centerXForColumn, historicalX) - f) - 20.0f;
                float fMax = Math.max(centerXForColumn, historicalX) + f + 20.0f;
                float fMin2 = (Math.min(centerYForRow, historicalY) - f) - 20.0f;
                float fMax2 = Math.max(centerYForRow, historicalY) + f + 20.0f;
                if (cellDetectAndAddHit != null) {
                    float f2 = ((LockPatternView) this).mSquareWidth * 0.5f;
                    float f3 = ((LockPatternView) this).mSquareHeight * 0.5f;
                    float centerXForColumn2 = getCenterXForColumn(cellDetectAndAddHit.getColumn());
                    float centerYForRow2 = getCenterYForRow(cellDetectAndAddHit.getRow());
                    fMin = Math.min(centerXForColumn2 - f2, fMin);
                    fMax = Math.max(centerXForColumn2 + f2, fMax);
                    fMin2 = Math.min(centerYForRow2 - f3, fMin2);
                    fMax2 = Math.max(centerYForRow2 + f3, fMax2);
                }
                ((LockPatternView) this).mTmpInvalidateRect.union(Math.round(fMin), Math.round(fMin2), Math.round(fMax), Math.round(fMax2));
            }
            i++;
        }
        ((LockPatternView) this).mInProgressX = motionEvent.getX();
        ((LockPatternView) this).mInProgressY = motionEvent.getY();
        if (z) {
            ((LockPatternView) this).mInvalidate.union(((LockPatternView) this).mTmpInvalidateRect);
            invalidate(((LockPatternView) this).mInvalidate);
            ((LockPatternView) this).mInvalidate.set(((LockPatternView) this).mTmpInvalidateRect);
        }
    }

    public final void onDraw(Canvas canvas) {
        Bitmap bitmap;
        if (!this.mDecoPatternEnabled) {
            super.onDraw(canvas);
            return;
        }
        ArrayList arrayList = ((LockPatternView) this).mPattern;
        int size = arrayList.size();
        boolean[][] zArr = ((LockPatternView) this).mPatternDrawLookup;
        if (((LockPatternView) this).mPatternDisplayMode == LockPatternView.DisplayMode.Animate) {
            int iElapsedRealtime = (((int) (SystemClock.elapsedRealtime() - ((LockPatternView) this).mAnimatingPeriodStart)) % ((size + 1) * KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED)) / KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED;
            clearPatternDrawLookup();
            for (int i = 0; i < iElapsedRealtime; i++) {
                LockPatternView.Cell cell = (LockPatternView.Cell) arrayList.get(i);
                zArr[cell.getRow()][cell.getColumn()] = true;
            }
            if (iElapsedRealtime > 0 && iElapsedRealtime < size) {
                float f = (r6 % KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED) / 700.0f;
                LockPatternView.Cell cell2 = (LockPatternView.Cell) arrayList.get(iElapsedRealtime - 1);
                float centerXForColumn = getCenterXForColumn(cell2.getColumn());
                float centerYForRow = getCenterYForRow(cell2.getRow());
                LockPatternView.Cell cell3 = (LockPatternView.Cell) arrayList.get(iElapsedRealtime);
                float centerXForColumn2 = (getCenterXForColumn(cell3.getColumn()) - centerXForColumn) * f;
                float centerYForRow2 = (getCenterYForRow(cell3.getRow()) - centerYForRow) * f;
                ((LockPatternView) this).mInProgressX = centerXForColumn + centerXForColumn2;
                ((LockPatternView) this).mInProgressY = centerYForRow + centerYForRow2;
            }
            invalidate();
        }
        float f2 = ((LockPatternView) this).mSquareWidth;
        float f3 = ((LockPatternView) this).mSquareHeight;
        ((LockPatternView) this).mPathPaint.setStrokeWidth(0.05f * f2);
        ((LockPatternView) this).mPathPaint.setAlpha(128);
        Path path = ((LockPatternView) this).mCurrentPath;
        path.rewind();
        boolean z = ((LockPatternView) this).mInStealthMode;
        if (!z) {
            if (this.mIsWhiteWp) {
                ((LockPatternView) this).mPathPaint.setColor(getResources().getColor(R.color.theme_pattern_line_color_black, null));
            } else {
                ((LockPatternView) this).mPathPaint.setColor(getResources().getColor(R.color.theme_pattern_line_color, null));
            }
        }
        if (!z) {
            float f4 = 0.0f;
            float f5 = 0.0f;
            int i2 = 0;
            boolean z2 = false;
            while (i2 < size) {
                LockPatternView.Cell cell4 = (LockPatternView.Cell) arrayList.get(i2);
                if (!zArr[cell4.getRow()][cell4.getColumn()]) {
                    break;
                }
                float centerXForColumn3 = getCenterXForColumn(cell4.getColumn());
                float centerYForRow3 = getCenterYForRow(cell4.getRow());
                if (i2 != 0) {
                    LockPatternView.CellState cellState = ((LockPatternView) this).mCellStates[cell4.getRow()][cell4.getColumn()];
                    path.rewind();
                    path.moveTo(f4, f5);
                    if (Float.compare(cellState.lineEndX, Float.MIN_VALUE) == 0 || Float.compare(cellState.lineEndY, Float.MIN_VALUE) == 0) {
                        path.lineTo(centerXForColumn3, centerYForRow3);
                    } else {
                        path.lineTo(cellState.lineEndX, cellState.lineEndY);
                    }
                    canvas.drawPath(path, ((LockPatternView) this).mPathPaint);
                }
                i2++;
                f4 = centerXForColumn3;
                f5 = centerYForRow3;
                z2 = true;
            }
            if ((((LockPatternView) this).mPatternInProgress || ((LockPatternView) this).mPatternDisplayMode == LockPatternView.DisplayMode.Animate) && z2) {
                path.rewind();
                path.moveTo(f4, f5);
                path.lineTo(((LockPatternView) this).mInProgressX, ((LockPatternView) this).mInProgressY);
                path.lineTo(((LockPatternView) this).mInProgressX, ((LockPatternView) this).mInProgressY);
                canvas.drawPath(path, ((LockPatternView) this).mPathPaint);
            }
        }
        int i3 = ((LockPatternView) this).mPaddingTop;
        int i4 = ((LockPatternView) this).mPaddingLeft;
        int i5 = 0;
        while (true) {
            if (i5 >= 3) {
                return;
            }
            float f6 = (i5 * f3) + i3;
            int i6 = 0;
            for (int i7 = 3; i6 < i7; i7 = 3) {
                int i8 = (int) ((i6 * f2) + i4);
                int i9 = (int) f6;
                if (!zArr[i5][i6] || ((LockPatternView) this).mInStealthMode) {
                    bitmap = this.mBitmapRegular;
                } else if (((LockPatternView) this).mPatternInProgress) {
                    bitmap = this.mBitmapSuccess;
                } else {
                    LockPatternView.DisplayMode displayMode = ((LockPatternView) this).mPatternDisplayMode;
                    if (displayMode == LockPatternView.DisplayMode.Wrong) {
                        bitmap = this.mBitmapError;
                        if (bitmap == null) {
                            bitmap = this.mBitmapRegular;
                        }
                    } else {
                        if (displayMode != LockPatternView.DisplayMode.Correct && displayMode != LockPatternView.DisplayMode.Animate) {
                            throw new IllegalStateException("unknown display mode " + ((LockPatternView) this).mPatternDisplayMode);
                        }
                        bitmap = this.mBitmapSuccess;
                    }
                }
                int i10 = this.mBitmapWidth;
                int i11 = this.mBitmapHeight;
                float f7 = ((LockPatternView) this).mSquareWidth;
                int i12 = i3;
                float f8 = i10;
                int i13 = (int) ((f7 - f8) / 2.0f);
                int i14 = (int) ((((LockPatternView) this).mSquareHeight - i11) / 2.0f);
                float fMin = Math.min(f7 / f8, 1.0f);
                float fMin2 = Math.min(((LockPatternView) this).mSquareHeight / this.mBitmapHeight, 1.0f);
                this.mCircleMatrix.setTranslate(i8 + i13, i9 + i14);
                this.mCircleMatrix.preTranslate(this.mBitmapWidth / 2.0f, this.mBitmapHeight / 2.0f);
                this.mCircleMatrix.preScale(fMin, fMin2);
                this.mCircleMatrix.preTranslate((-this.mBitmapWidth) / 2.0f, (-this.mBitmapHeight) / 2.0f);
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, this.mCircleMatrix, ((LockPatternView) this).mPaint);
                }
                i6++;
                i3 = i12;
            }
            i5++;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateViewStyle(int i, long j) {
        boolean z;
        boolean z2;
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY);
        boolean zIsUltraPowerSavingMode = settingsHelper.isUltraPowerSavingMode();
        boolean z3 = settingsHelper.isColorThemeEnabled() && (1024 & j) != 0;
        if (!WallpaperUtils.isOpenThemeLook()) {
            z = false;
            break;
        }
        int i2 = R.drawable.indicator_code_lock_point_area_green_holo_black;
        int i3 = R.drawable.indicator_code_lock_point_area_default_holo_black;
        if (zIsWhiteKeyguardWallpaper) {
            Bitmap[] bitmapArr = {getScaledBitmapFor(R.drawable.indicator_code_lock_point_area_default_holo_black), getScaledBitmapFor(R.drawable.indicator_code_lock_point_area_green_holo_black)};
            for (int i4 = 0; i4 < 2; i4++) {
                if (bitmapArr[i4] == null) {
                    z2 = false;
                    break;
                }
            }
            z2 = true;
            if (!z2) {
                i3 = R.drawable.indicator_code_lock_point_area_default_holo;
            }
            this.mBitmapRegular = getScaledBitmapFor(i3);
            this.mBitmapError = getScaledBitmapFor(!z2 ? R.drawable.indicator_code_lock_point_area_red_holo_black : R.drawable.indicator_code_lock_point_area_red_holo);
            if (!z2) {
                i2 = R.drawable.indicator_code_lock_point_area_green_holo;
            }
            Bitmap scaledBitmapFor = getScaledBitmapFor(i2);
            this.mBitmapSuccess = scaledBitmapFor;
            Bitmap bitmap = this.mBitmapRegular;
            Bitmap[] bitmapArr2 = (bitmap == null && scaledBitmapFor != null && this.mBitmapError == null) ? new Bitmap[]{bitmap, scaledBitmapFor} : new Bitmap[]{bitmap, scaledBitmapFor, this.mBitmapError};
            for (Bitmap bitmap2 : bitmapArr2) {
                if (bitmap2 == null) {
                    z = false;
                    break;
                } else {
                    this.mBitmapWidth = Math.max(this.mBitmapWidth, bitmap2.getWidth());
                    this.mBitmapHeight = Math.max(this.mBitmapHeight, bitmap2.getHeight());
                }
            }
            z = true;
        } else {
            z2 = false;
            if (!z2) {
            }
            this.mBitmapRegular = getScaledBitmapFor(i3);
            this.mBitmapError = getScaledBitmapFor(!z2 ? R.drawable.indicator_code_lock_point_area_red_holo_black : R.drawable.indicator_code_lock_point_area_red_holo);
            if (!z2) {
            }
            Bitmap scaledBitmapFor2 = getScaledBitmapFor(i2);
            this.mBitmapSuccess = scaledBitmapFor2;
            Bitmap bitmap3 = this.mBitmapRegular;
            if (bitmap3 == null) {
                while (i < r5) {
                }
                z = true;
            }
        }
        this.mDecoPatternEnabled = z;
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("updateViewStyle whiteWp = ", ", open theme enabled = ", zIsWhiteKeyguardWallpaper);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mDecoPatternEnabled, ", isSavingMode = ", zIsUltraPowerSavingMode, ", isColorTheme = ");
        sbM.append(z3);
        sbM.append(", themeColor = #");
        sbM.append(Integer.toHexString(i));
        sbM.append(", updateFlag = ");
        sbM.append(KeyguardWallpaperColors.getChangeFlagsString(j));
        Log.d("SecLockPatternView", sbM.toString());
        if (this.mDecoPatternEnabled) {
            setFadePattern(false);
            this.mIsWhiteWp = zIsWhiteKeyguardWallpaper;
            invalidate();
        } else if (z3 && !zIsUltraPowerSavingMode) {
            setColors(i, i, i);
        } else {
            setFadePattern(true);
            super.updateViewStyle(zIsWhiteKeyguardWallpaper);
        }
    }

    public SecLockPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDecoPatternEnabled = false;
        this.mCircleMatrix = new Matrix();
        this.mIsWhiteWp = false;
    }
}
