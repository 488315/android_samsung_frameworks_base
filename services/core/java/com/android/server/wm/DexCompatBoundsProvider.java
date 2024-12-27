package com.android.server.wm;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Slog;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.WindowInsets;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class DexCompatBoundsProvider {
    public boolean mIsPortrait;
    public final Rect mStableBounds = new Rect();
    public Task mTask;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CustomDexCompatBoundsProvider extends DexCompatBoundsProvider {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ CustomDexCompatBoundsProvider(int i) {
            this.$r8$classId = i;
        }

        @Override // com.android.server.wm.DexCompatBoundsProvider
        public final void getBounds(Rect rect) {
            switch (this.$r8$classId) {
                case 0:
                    Point point = this.mTask.mDexCompatCustomSize;
                    if (point != null) {
                        int max = Math.max(point.x, point.y);
                        Point point2 = this.mTask.mDexCompatCustomSize;
                        int min = Math.min(point2.x, point2.y);
                        if (!this.mIsPortrait) {
                            rect.set(0, 0, max, min);
                            break;
                        } else {
                            rect.set(0, 0, min, max);
                            break;
                        }
                    } else {
                        Slog.w("DexCompatBoundsProvider", "getBounds: Cannot find custom size, use default, " + this.mTask);
                        super.getBounds(rect);
                        break;
                    }
                default:
                    if (!this.mIsPortrait) {
                        rect.setEmpty();
                        break;
                    } else {
                        int defaultWidth = DexCompatBoundsProvider.getDefaultWidth(getDexMode(), getDisplayShortSize());
                        int defaultHeight = DexCompatBoundsProvider.getDefaultHeight(getDexMode(), getDisplayShortSize());
                        if (defaultWidth > 0 && defaultHeight > 0) {
                            float f = defaultWidth < defaultHeight ? defaultWidth / defaultHeight : defaultHeight / defaultWidth;
                            Rect appBounds = this.mTask.getDisplayContent().getWindowConfiguration().getAppBounds();
                            Rect rect2 = new Rect(appBounds);
                            if (appBounds == null) {
                                int height = (int) ((this.mTask.getDisplayContent().getBounds().height() * 0.9f) + 0.5f);
                                rect.set(0, 0, (int) ((height * f) + 0.5f), height);
                                break;
                            } else {
                                InsetsState insetsState = this.mTask.getDisplayContent().mInsetsStateController.mState;
                                int sourceSize = insetsState.sourceSize();
                                int i = 0;
                                while (true) {
                                    if (i < sourceSize) {
                                        InsetsSource sourceAt = insetsState.sourceAt(i);
                                        if (sourceAt.getType() != WindowInsets.Type.navigationBars()) {
                                            i++;
                                        } else if (sourceAt.isVisible()) {
                                            rect2.inset(0, 0, 0, sourceAt.getFrame().height());
                                        }
                                    }
                                }
                                rect.set(0, 0, (int) ((rect2.height() * f) + 0.5f), rect2.height() - (this.mTask.getFreeformThickness() * 2));
                                break;
                            }
                        } else {
                            PendingIntentController$$ExternalSyntheticOutline0.m(defaultWidth, defaultHeight, "getBounds: invalid value, ", "x", "DexCompatBoundsProvider");
                            break;
                        }
                    }
            }
        }
    }

    public static int getDefaultHeight(int i, int i2) {
        if (i == 1) {
            return (i2 <= 0 || i2 > 1400) ? 1050 : 757;
        }
        if (i != 2) {
            return -1;
        }
        if (CoreRune.IS_TABLET_DEVICE) {
            return 600;
        }
        return FrameworkStatsLog.WEAR_POWER_MENU_OPENED;
    }

    public static int getDefaultWidth(int i, int i2) {
        if (i == 1) {
            return (i2 <= 0 || i2 > 1400) ? 1400 : 1010;
        }
        if (i != 2) {
            return -1;
        }
        if (CoreRune.IS_TABLET_DEVICE) {
            return 800;
        }
        return FrameworkStatsLog.CAMERA_COMPAT_CONTROL_EVENT_REPORTED;
    }

    public void getBounds(Rect rect) {
        int defaultWidth = getDefaultWidth(getDexMode(), getDisplayShortSize());
        int defaultHeight = getDefaultHeight(getDexMode(), getDisplayShortSize());
        if (defaultWidth <= 0 || defaultHeight <= 0) {
            PendingIntentController$$ExternalSyntheticOutline0.m(defaultWidth, defaultHeight, "getBounds: Invalid default size, ", "x", "DexCompatBoundsProvider");
        } else if (this.mIsPortrait) {
            rect.set(0, 0, Math.min(defaultWidth, defaultHeight), Math.max(defaultWidth, defaultHeight));
        } else {
            rect.set(0, 0, Math.max(defaultWidth, defaultHeight), Math.min(defaultWidth, defaultHeight));
        }
    }

    public int getDexMode() {
        return this.mTask.mAtmService.mDexController.getDexModeLocked();
    }

    public final int getDisplayShortSize() {
        return Math.min(this.mStableBounds.width(), this.mStableBounds.height());
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0044, code lost:
    
        if (r6.width() <= r6.height()) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0047, code lost:
    
        r2 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0058, code lost:
    
        if (r5.x > r5.y) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0063, code lost:
    
        if (r6.width() > r6.height()) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0076, code lost:
    
        if (r6.width() > r6.height()) goto L45;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isPortrait(int r6) {
        /*
            r5 = this;
            boolean r0 = android.content.pm.ActivityInfo.isFixedOrientationPortrait(r6)
            r1 = 0
            r2 = 2
            r3 = 1
            if (r0 == 0) goto Lb
            r6 = r3
            goto L14
        Lb:
            boolean r6 = android.content.pm.ActivityInfo.isFixedOrientationLandscape(r6)
            if (r6 == 0) goto L13
            r6 = r2
            goto L14
        L13:
            r6 = r1
        L14:
            if (r6 != 0) goto L79
            com.android.server.wm.Task r5 = r5.mTask
            boolean r6 = r5.isDexCompatEnabled()
            if (r6 != 0) goto L21
            r6 = r1
            goto L79
        L21:
            android.graphics.Rect r6 = r5.getRequestedOverrideBounds()
            boolean r0 = com.samsung.android.rune.CoreRune.IS_TABLET_DEVICE
            if (r0 == 0) goto L2b
            r0 = r2
            goto L2c
        L2b:
            r0 = r3
        L2c:
            int r4 = r5.mDexCompatUiMode
            if (r4 == r3) goto L66
            if (r4 == r2) goto L49
            r5 = 3
            if (r4 == r5) goto L36
            goto L6c
        L36:
            boolean r5 = r6.isEmpty()
            if (r5 != 0) goto L78
            int r5 = r6.width()
            int r6 = r6.height()
            if (r5 <= r6) goto L47
            goto L78
        L47:
            r2 = r3
            goto L78
        L49:
            boolean r4 = r6.isEmpty()
            if (r4 == 0) goto L5b
            android.graphics.Point r5 = r5.mDexCompatCustomSize
            if (r5 != 0) goto L54
            goto L6c
        L54:
            int r6 = r5.x
            int r5 = r5.y
            if (r6 <= r5) goto L47
            goto L78
        L5b:
            int r5 = r6.width()
            int r6 = r6.height()
            if (r5 <= r6) goto L47
            goto L78
        L66:
            boolean r5 = r6.isEmpty()
            if (r5 == 0) goto L6e
        L6c:
            r2 = r0
            goto L78
        L6e:
            int r5 = r6.width()
            int r6 = r6.height()
            if (r5 <= r6) goto L47
        L78:
            r6 = r2
        L79:
            if (r6 != r3) goto L7c
            r1 = r3
        L7c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.DexCompatBoundsProvider.isPortrait(int):boolean");
    }
}
