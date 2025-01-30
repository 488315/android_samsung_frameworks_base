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
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        Bitmap decodeResource = BitmapFactory.decodeResource(getContext().getResources(), i);
        int dimension = (int) getContext().getResources().getDimension(R.dimen.theme_keyguard_pattern_dot_size);
        if (decodeResource != null) {
            return Bitmap.createScaledBitmap(decodeResource, dimension, dimension, true);
        }
        NestedScrollView$$ExternalSyntheticOutline0.m34m("getScaledBitmapFor() return null - bitmap is null resId = ", i, "SecLockPatternView");
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
            LockPatternView.Cell detectAndAddHit = detectAndAddHit(historicalX, historicalY);
            int size = ((LockPatternView) this).mPattern.size();
            if (detectAndAddHit != null && size == 1) {
                ((LockPatternView) this).mPatternInProgress = true;
                notifyPatternStarted();
            }
            float abs = Math.abs(historicalX - ((LockPatternView) this).mInProgressX);
            float abs2 = Math.abs(historicalY - ((LockPatternView) this).mInProgressY);
            if (abs > 0.0f || abs2 > 0.0f) {
                z = true;
            }
            if (((LockPatternView) this).mPatternInProgress && size > 0) {
                LockPatternView.Cell cell = (LockPatternView.Cell) ((LockPatternView) this).mPattern.get(size - 1);
                float centerXForColumn = getCenterXForColumn(cell.getColumn());
                float centerYForRow = getCenterYForRow(cell.getRow());
                float min = (Math.min(centerXForColumn, historicalX) - f) - 20.0f;
                float max = Math.max(centerXForColumn, historicalX) + f + 20.0f;
                float min2 = (Math.min(centerYForRow, historicalY) - f) - 20.0f;
                float max2 = Math.max(centerYForRow, historicalY) + f + 20.0f;
                if (detectAndAddHit != null) {
                    float f2 = ((LockPatternView) this).mSquareWidth * 0.5f;
                    float f3 = ((LockPatternView) this).mSquareHeight * 0.5f;
                    float centerXForColumn2 = getCenterXForColumn(detectAndAddHit.getColumn());
                    float centerYForRow2 = getCenterYForRow(detectAndAddHit.getRow());
                    min = Math.min(centerXForColumn2 - f2, min);
                    max = Math.max(centerXForColumn2 + f2, max);
                    min2 = Math.min(centerYForRow2 - f3, min2);
                    max2 = Math.max(centerYForRow2 + f3, max2);
                }
                ((LockPatternView) this).mTmpInvalidateRect.union(Math.round(min), Math.round(min2), Math.round(max), Math.round(max2));
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
            int elapsedRealtime = (((int) (SystemClock.elapsedRealtime() - ((LockPatternView) this).mAnimatingPeriodStart)) % ((size + 1) * KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED)) / KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED;
            clearPatternDrawLookup();
            for (int i = 0; i < elapsedRealtime; i++) {
                LockPatternView.Cell cell = (LockPatternView.Cell) arrayList.get(i);
                zArr[cell.getRow()][cell.getColumn()] = true;
            }
            if (elapsedRealtime > 0 && elapsedRealtime < size) {
                float f = (r6 % KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED) / 700.0f;
                LockPatternView.Cell cell2 = (LockPatternView.Cell) arrayList.get(elapsedRealtime - 1);
                float centerXForColumn = getCenterXForColumn(cell2.getColumn());
                float centerYForRow = getCenterYForRow(cell2.getRow());
                LockPatternView.Cell cell3 = (LockPatternView.Cell) arrayList.get(elapsedRealtime);
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
        boolean z = !((LockPatternView) this).mInStealthMode;
        if (z) {
            if (this.mIsWhiteWp) {
                ((LockPatternView) this).mPathPaint.setColor(getResources().getColor(R.color.theme_pattern_line_color_black, null));
            } else {
                ((LockPatternView) this).mPathPaint.setColor(getResources().getColor(R.color.theme_pattern_line_color, null));
            }
        }
        if (z) {
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
                int i13 = i4;
                int i14 = (int) ((f7 - f8) / 2.0f);
                int i15 = (int) ((((LockPatternView) this).mSquareHeight - i11) / 2.0f);
                float min = Math.min(f7 / f8, 1.0f);
                float min2 = Math.min(((LockPatternView) this).mSquareHeight / this.mBitmapHeight, 1.0f);
                this.mCircleMatrix.setTranslate(i8 + i14, i9 + i15);
                this.mCircleMatrix.preTranslate(this.mBitmapWidth / 2.0f, this.mBitmapHeight / 2.0f);
                this.mCircleMatrix.preScale(min, min2);
                this.mCircleMatrix.preTranslate((-this.mBitmapWidth) / 2.0f, (-this.mBitmapHeight) / 2.0f);
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, this.mCircleMatrix, ((LockPatternView) this).mPaint);
                }
                i6++;
                i3 = i12;
                i4 = i13;
            }
            i5++;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateViewStyle(int i, long j) {
        boolean z;
        boolean z2;
        int length;
        int i2;
        boolean z3;
        Bitmap bitmap;
        boolean z4;
        SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("background");
        boolean isUltraPowerSavingMode = settingsHelper.isUltraPowerSavingMode();
        boolean z5 = settingsHelper.isColorThemeEnabled$1() && (1024 & j) != 0;
        if (WallpaperUtils.isOpenThemeLook()) {
            int i3 = R.drawable.indicator_code_lock_point_area_green_holo_black;
            int i4 = R.drawable.indicator_code_lock_point_area_default_holo_black;
            if (isWhiteKeyguardWallpaper) {
                Bitmap[] bitmapArr = {getScaledBitmapFor(R.drawable.indicator_code_lock_point_area_default_holo_black), getScaledBitmapFor(R.drawable.indicator_code_lock_point_area_green_holo_black)};
                int i5 = 0;
                while (true) {
                    if (i5 >= 2) {
                        z4 = true;
                        break;
                    } else {
                        if (bitmapArr[i5] == null) {
                            z4 = false;
                            break;
                        }
                        i5++;
                    }
                }
                if (z4) {
                    z2 = true;
                    if (!z2) {
                        i4 = R.drawable.indicator_code_lock_point_area_default_holo;
                    }
                    this.mBitmapRegular = getScaledBitmapFor(i4);
                    this.mBitmapError = getScaledBitmapFor(!z2 ? R.drawable.indicator_code_lock_point_area_red_holo_black : R.drawable.indicator_code_lock_point_area_red_holo);
                    if (!z2) {
                        i3 = R.drawable.indicator_code_lock_point_area_green_holo;
                    }
                    Bitmap scaledBitmapFor = getScaledBitmapFor(i3);
                    this.mBitmapSuccess = scaledBitmapFor;
                    Bitmap bitmap2 = this.mBitmapRegular;
                    Bitmap[] bitmapArr2 = (bitmap2 == null && scaledBitmapFor != null && this.mBitmapError == null) ? new Bitmap[]{bitmap2, scaledBitmapFor} : new Bitmap[]{bitmap2, scaledBitmapFor, this.mBitmapError};
                    length = bitmapArr2.length;
                    i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            z3 = true;
                            break;
                        }
                        bitmap = bitmapArr2[i2];
                        if (bitmap == null) {
                            z3 = false;
                            break;
                        } else {
                            this.mBitmapWidth = Math.max(this.mBitmapWidth, bitmap.getWidth());
                            this.mBitmapHeight = Math.max(this.mBitmapHeight, bitmap.getHeight());
                            i2++;
                        }
                    }
                    if (z3) {
                        z = true;
                        this.mDecoPatternEnabled = z;
                        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("updateViewStyle whiteWp = ", isWhiteKeyguardWallpaper, ", open theme enabled = ");
                        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m49m, this.mDecoPatternEnabled, ", isSavingMode = ", isUltraPowerSavingMode, ", isColorTheme = ");
                        m49m.append(z5);
                        m49m.append(", themeColor = #");
                        m49m.append(Integer.toHexString(i));
                        m49m.append(", updateFlag = ");
                        m49m.append(KeyguardWallpaperColors.getChangeFlagsString(j));
                        Log.d("SecLockPatternView", m49m.toString());
                        if (!this.mDecoPatternEnabled) {
                            setFadePattern(false);
                            this.mIsWhiteWp = isWhiteKeyguardWallpaper;
                            invalidate();
                            return;
                        } else if (z5 && !isUltraPowerSavingMode) {
                            setColors(i, i, i);
                            return;
                        } else {
                            setFadePattern(true);
                            super.updateViewStyle(isWhiteKeyguardWallpaper);
                            return;
                        }
                    }
                }
            }
            z2 = false;
            if (!z2) {
            }
            this.mBitmapRegular = getScaledBitmapFor(i4);
            this.mBitmapError = getScaledBitmapFor(!z2 ? R.drawable.indicator_code_lock_point_area_red_holo_black : R.drawable.indicator_code_lock_point_area_red_holo);
            if (!z2) {
            }
            Bitmap scaledBitmapFor2 = getScaledBitmapFor(i3);
            this.mBitmapSuccess = scaledBitmapFor2;
            Bitmap bitmap22 = this.mBitmapRegular;
            if (bitmap22 == null) {
            }
            length = bitmapArr2.length;
            i2 = 0;
            while (true) {
                if (i2 >= length) {
                }
                this.mBitmapWidth = Math.max(this.mBitmapWidth, bitmap.getWidth());
                this.mBitmapHeight = Math.max(this.mBitmapHeight, bitmap.getHeight());
                i2++;
            }
            if (z3) {
            }
        }
        z = false;
        this.mDecoPatternEnabled = z;
        StringBuilder m49m2 = RowView$$ExternalSyntheticOutline0.m49m("updateViewStyle whiteWp = ", isWhiteKeyguardWallpaper, ", open theme enabled = ");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m49m2, this.mDecoPatternEnabled, ", isSavingMode = ", isUltraPowerSavingMode, ", isColorTheme = ");
        m49m2.append(z5);
        m49m2.append(", themeColor = #");
        m49m2.append(Integer.toHexString(i));
        m49m2.append(", updateFlag = ");
        m49m2.append(KeyguardWallpaperColors.getChangeFlagsString(j));
        Log.d("SecLockPatternView", m49m2.toString());
        if (!this.mDecoPatternEnabled) {
        }
    }

    public SecLockPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDecoPatternEnabled = false;
        this.mCircleMatrix = new Matrix();
        this.mIsWhiteWp = false;
    }
}
