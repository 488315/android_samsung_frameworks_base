package com.android.server.wm;

import android.content.Context;
import android.content.pm.PackageItemInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.WindowInsets;
import com.android.server.wm.utils.RotationCache;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UdcCutoutPolicy {
    public final Context mContext;
    public final DisplayContent mDisplayContent;
    public final RotationCache mDisplayCutoutCache;
    public Rect mTmpBarContentFrame;
    public Configuration mUdcConfiguration;
    public DisplayCutout mUdcCutout;
    public DisplayFrames mUdcDisplayFrames;

    public UdcCutoutPolicy(DisplayContent displayContent, RotationCache rotationCache) {
        this.mDisplayContent = displayContent;
        this.mContext = displayContent.mWmService.mContext;
        this.mDisplayCutoutCache = rotationCache;
    }

    public static boolean supportsUdcCutout(PackageItemInfo packageItemInfo) {
        Bundle bundle;
        return (packageItemInfo == null || (bundle = packageItemInfo.metaData) == null || bundle.getBoolean("com.samsung.android.supports_udc_cutout", true)) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002e, code lost:
    
        if (supportsUdcCutout(r5.getApplicationInfo(r6, android.os.UserHandle.getUserId(r6), 0, r10)) != false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0047, code lost:
    
        if (supportsUdcCutout(r0.info.applicationInfo) != false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void updateUseLayoutInUdcCutoutIfNeeded(com.android.server.wm.WindowContainer r11) {
        /*
            com.android.server.wm.WindowState r0 = r11.asWindowState()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L31
            com.android.server.wm.ActivityRecord r3 = r0.mActivityRecord
            if (r3 == 0) goto L10
            boolean r1 = r3.mUseLayoutInUdcCutout
        Le:
            r2 = r1
            goto L4b
        L10:
            android.view.WindowManager$LayoutParams r3 = r0.mAttrs
            int r4 = r3.samsungFlags
            r4 = r4 & 8192(0x2000, float:1.14794E-41)
            if (r4 != 0) goto L4b
            com.android.server.wm.WindowManagerService r4 = r0.mWmService
            android.content.pm.PackageManagerInternal r5 = r4.mPmInternal
            java.lang.String r10 = r3.packageName
            int r6 = r0.mOwnerUid
            int r7 = android.os.UserHandle.getUserId(r6)
            r8 = 0
            android.content.pm.ApplicationInfo r0 = r5.getApplicationInfo(r6, r7, r8, r10)
            boolean r0 = supportsUdcCutout(r0)
            if (r0 == 0) goto L4a
            goto L4b
        L31:
            com.android.server.wm.ActivityRecord r0 = r11.asActivityRecord()
            if (r0 == 0) goto L4a
            android.content.pm.ActivityInfo r3 = r0.info
            boolean r3 = supportsUdcCutout(r3)
            if (r3 != 0) goto Le
            android.content.pm.ActivityInfo r0 = r0.info
            android.content.pm.ApplicationInfo r0 = r0.applicationInfo
            boolean r0 = supportsUdcCutout(r0)
            if (r0 == 0) goto L4a
            goto Le
        L4a:
            r1 = r2
        L4b:
            r11.mUseLayoutInUdcCutout = r1
            r11.mUseConfigurationInUdcCutout = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.UdcCutoutPolicy.updateUseLayoutInUdcCutoutIfNeeded(com.android.server.wm.WindowContainer):void");
    }

    public final void adjustInsetsForUdc(WindowContainer windowContainer, InsetsState insetsState) {
        DisplayFrames displayFrames;
        if (windowContainer instanceof ActivityRecord) {
            ActivityRecord activityRecord = (ActivityRecord) windowContainer;
            activityRecord.getClass();
            displayFrames = activityRecord.getFixedRotationTransformDisplayFrames();
        } else if (windowContainer instanceof WindowState) {
            WindowState windowState = (WindowState) windowContainer;
            windowState.getClass();
            ActivityRecord activityRecord2 = windowState.mActivityRecord;
            DisplayFrames fixedRotationTransformDisplayFrames = activityRecord2 != null ? activityRecord2.getFixedRotationTransformDisplayFrames() : null;
            displayFrames = fixedRotationTransformDisplayFrames == null ? windowState.mToken.getFixedRotationTransformDisplayFrames() : fixedRotationTransformDisplayFrames;
        } else {
            displayFrames = null;
        }
        if (displayFrames == null) {
            displayFrames = this.mUdcDisplayFrames;
        }
        DisplayCutout displayCutout = displayFrames.mInsetsState.getDisplayCutout();
        insetsState.setDisplayCutout(displayCutout);
        if (displayCutout.isEmpty()) {
            for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                if (insetsState.sourceAt(sourceSize).getType() == WindowInsets.Type.displayCutout()) {
                    insetsState.removeSourceAt(sourceSize);
                }
            }
            return;
        }
        Rect rect = displayFrames.mUnrestricted;
        Rect rect2 = displayFrames.mDisplayCutoutSafe;
        for (int sourceSize2 = insetsState.sourceSize() - 1; sourceSize2 >= 0; sourceSize2--) {
            InsetsSource sourceAt = insetsState.sourceAt(sourceSize2);
            if (sourceAt.getType() == WindowInsets.Type.displayCutout()) {
                int i = rect2.left;
                if (i != 0) {
                    sourceAt.setFrame(rect.left, rect.top, i, rect.bottom);
                    return;
                }
                int i2 = rect2.top;
                if (i2 != 0) {
                    sourceAt.setFrame(rect.left, rect.top, rect.right, i2);
                    return;
                }
                int i3 = rect2.right;
                if (i3 != 0) {
                    sourceAt.setFrame(i3, rect.top, rect.right, rect.bottom);
                    return;
                }
                int i4 = rect2.bottom;
                if (i4 != 0) {
                    sourceAt.setFrame(rect.left, i4, rect.right, rect.bottom);
                    return;
                }
                return;
            }
        }
    }
}
