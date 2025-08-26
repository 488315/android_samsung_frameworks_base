package com.android.wm.shell.draganddrop;

import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Slog;
import android.view.DragEvent;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.draganddrop.GlobalDragListener;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes3.dex */
public class UnhandledDragController implements DragAndDropController.DragAndDropListener {
    public static final PointF DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX = new PointF(0.541f, 0.65f);
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final MultiInstanceHelper mMultiInstanceHelper;
    public final Transitions mTransitions;
    public final Rect mLaunchBounds = new Rect();
    public final Rect mTempRect = new Rect();
    public final Rect mTempRect2 = new Rect();

    public UnhandledDragController(Context context, Transitions transitions, MultiInstanceHelper multiInstanceHelper, DisplayController displayController) {
        this.mContext = context;
        this.mTransitions = transitions;
        this.mMultiInstanceHelper = multiInstanceHelper;
        this.mDisplayController = displayController;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0095  */
    @Override // com.android.wm.shell.draganddrop.DragAndDropController.DragAndDropListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onUnhandledDrag(PendingIntent pendingIntent, int i, DragEvent dragEvent, GlobalDragListener.AnonymousClass1 anonymousClass1) {
        ResolveInfo resolveInfoResolveActivity;
        ActivityInfo activityInfo;
        int i2;
        float f;
        float f2;
        Intent intent = pendingIntent.getIntent();
        if (!this.mMultiInstanceHelper.supportsMultiInstanceSplit(i, intent != null ? intent.getComponent() : null)) {
            Slog.w("UnhandledDragController", "supportsMultiInstanceSplit() returns false");
            return false;
        }
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchWindowingMode(5);
        activityOptionsMakeBasic.setPendingIntentLaunchFlags(402653184);
        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(2);
        int displayId = dragEvent.getDisplayId();
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(displayId != -1 ? displayId : 0);
        if (displayLayout == null) {
            Slog.w("UnhandledDragController", "DisplayLayout is null for displayId=" + displayId);
            return false;
        }
        displayLayout.getDisplayBounds(this.mTempRect);
        int iWidth = this.mTempRect.width();
        int iHeight = this.mTempRect.height();
        if (CoreRune.MT_NEW_DEX_BOUNDS_POLICY) {
            if (iWidth > iHeight) {
                PointF pointF = DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX;
                i2 = (int) (iWidth * pointF.x);
                f = iHeight;
                f2 = pointF.y;
            } else {
                PointF pointF2 = DEX_DEFAULT_SIZE_RATIO_FOR_NEW_DEX;
                i2 = (int) (iWidth * pointF2.y);
                f = iHeight;
                f2 = pointF2.x;
            }
            this.mLaunchBounds.set(0, 0, i2, (int) (f * f2));
        } else {
            displayLayout.getStableBounds(this.mTempRect2, false);
            Intent intent2 = pendingIntent.getIntent();
            if (intent2 != null) {
                PackageManager packageManager = ActivityThread.currentApplication() != null ? ActivityThread.currentApplication().getPackageManager() : null;
                ActivityInfo.WindowLayout windowLayoutRecalculateWindowLayout = (packageManager == null || (resolveInfoResolveActivity = packageManager.resolveActivity(intent2, PackageManager.ResolveInfoFlags.of(131072L))) == null || (activityInfo = resolveInfoResolveActivity.activityInfo) == null) ? null : activityInfo.windowLayout;
                if (windowLayoutRecalculateWindowLayout != null) {
                    float f3 = this.mContext.getResources().getConfiguration().densityDpi;
                    float initialDensity = SemWindowManager.getInstance().getInitialDensity();
                    Intent intent3 = pendingIntent.getIntent();
                    windowLayoutRecalculateWindowLayout = MultiWindowUtils.recalculateWindowLayout(f3, initialDensity, windowLayoutRecalculateWindowLayout, intent3 != null ? intent3.getPackage() : null);
                }
                MultiWindowUtils.getDefaultFreeformBounds(this.mTempRect, this.mTempRect2, windowLayoutRecalculateWindowLayout, this.mLaunchBounds);
            }
        }
        this.mLaunchBounds.offset(((int) dragEvent.getX()) - (this.mLaunchBounds.width() / 2), (int) dragEvent.getY());
        Rect rect = this.mLaunchBounds;
        int iWidth2 = rect.left;
        int iHeight2 = rect.top;
        displayLayout.getStableBounds(this.mTempRect, false);
        Rect rect2 = this.mLaunchBounds;
        int i3 = rect2.right;
        Rect rect3 = this.mTempRect;
        int i4 = rect3.right;
        if (i3 > i4) {
            iWidth2 = i4 - rect2.width();
        } else {
            int i5 = rect2.left;
            int i6 = rect3.left;
            if (i5 < i6) {
                iWidth2 = i6;
            }
        }
        Rect rect4 = this.mLaunchBounds;
        int i7 = rect4.bottom;
        Rect rect5 = this.mTempRect;
        int i8 = rect5.bottom;
        if (i7 > i8) {
            iHeight2 = i8 - rect4.height();
        } else {
            int i9 = rect4.top;
            int i10 = rect5.top;
            if (i9 < i10) {
                iHeight2 = i10;
            }
        }
        this.mLaunchBounds.offsetTo(iWidth2, iHeight2);
        Bundle bundle = activityOptionsMakeBasic.toBundle();
        bundle.putParcelable("android:activity.launchBounds", this.mLaunchBounds);
        bundle.putBoolean("android:activity.unhandledDropLaunch", true);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.sendPendingIntent(pendingIntent, (Intent) null, bundle);
        this.mTransitions.startTransition(1, windowContainerTransaction, null);
        anonymousClass1.accept(Boolean.TRUE);
        SurfaceControl dragSurface = dragEvent.getDragSurface();
        if (dragSurface == null) {
            return true;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        try {
            transaction.remove(dragSurface);
            transaction.apply();
            transaction.close();
            return true;
        } catch (Throwable th) {
            try {
                transaction.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
