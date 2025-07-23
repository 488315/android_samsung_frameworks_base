package com.android.internal.widget;

import android.app.Flags;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class NotificationRowIconView extends CachingIconView {
    private Drawable mAppIcon;
    private NotificationIconProvider mIconProvider;
    private Drawable mOriginalBackground;
    private int mOriginalBackgroundColor;
    private int mOriginalIconColor;
    private Rect mOriginalPadding;

    public interface NotificationIconProvider {
        Drawable getAppIcon();

        boolean shouldShowAppIcon();
    }

    static /* synthetic */ void lambda$setImageIconAsync$0() {
    }

    public NotificationRowIconView(Context context) {
        super(context);
        this.mAppIcon = null;
        this.mOriginalPadding = null;
        this.mOriginalBackground = null;
        this.mOriginalBackgroundColor = 1;
        this.mOriginalIconColor = 1;
    }

    public NotificationRowIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAppIcon = null;
        this.mOriginalPadding = null;
        this.mOriginalBackground = null;
        this.mOriginalBackgroundColor = 1;
        this.mOriginalIconColor = 1;
    }

    public NotificationRowIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAppIcon = null;
        this.mOriginalPadding = null;
        this.mOriginalBackground = null;
        this.mOriginalBackgroundColor = 1;
        this.mOriginalIconColor = 1;
    }

    public NotificationRowIconView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAppIcon = null;
        this.mOriginalPadding = null;
        this.mOriginalBackground = null;
        this.mOriginalBackgroundColor = 1;
        this.mOriginalIconColor = 1;
    }

    public void setIconProvider(NotificationIconProvider notificationIconProvider) {
        this.mIconProvider = notificationIconProvider;
    }

    private Drawable loadAppIcon() {
        NotificationIconProvider notificationIconProvider = this.mIconProvider;
        if (notificationIconProvider == null || !notificationIconProvider.shouldShowAppIcon()) {
            return null;
        }
        return this.mIconProvider.getAppIcon();
    }

    @Override // com.android.internal.widget.CachingIconView, android.widget.ImageView
    @RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(Icon icon) {
        if (Flags.notificationsRedesignAppIcons()) {
            if (this.mAppIcon != null) {
                return;
            }
            Drawable loadAppIcon = loadAppIcon();
            this.mAppIcon = loadAppIcon;
            if (loadAppIcon != null) {
                lambda$setImageURIAsync$2(loadAppIcon);
                adjustViewForAppIcon();
                return;
            } else {
                super.setImageIcon(icon);
                restoreViewForSmallIcon();
                return;
            }
        }
        super.setImageIcon(icon);
    }

    @Override // com.android.internal.widget.CachingIconView, android.widget.ImageView
    @RemotableViewMethod
    public Runnable setImageIconAsync(final Icon icon) {
        if (Flags.notificationsRedesignAppIcons()) {
            if (this.mAppIcon != null) {
                return new Runnable() { // from class: com.android.internal.widget.NotificationRowIconView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationRowIconView.lambda$setImageIconAsync$0();
                    }
                };
            }
            Drawable loadAppIcon = loadAppIcon();
            this.mAppIcon = loadAppIcon;
            if (loadAppIcon != null) {
                return new Runnable() { // from class: com.android.internal.widget.NotificationRowIconView$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationRowIconView.this.lambda$setImageIconAsync$1();
                    }
                };
            }
            return new Runnable() { // from class: com.android.internal.widget.NotificationRowIconView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationRowIconView.this.lambda$setImageIconAsync$2(icon);
                }
            };
        }
        return super.setImageIconAsync(icon);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setImageIconAsync$1() {
        lambda$setImageURIAsync$2(this.mAppIcon);
        adjustViewForAppIcon();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setImageIconAsync$2(Icon icon) {
        super.setImageIcon(icon);
        restoreViewForSmallIcon();
    }

    private void adjustViewForAppIcon() {
        removePadding();
        removeBackground();
    }

    private void restoreViewForSmallIcon() {
        restorePadding();
        restoreBackground();
        restoreColors();
    }

    private void removePadding() {
        if (this.mOriginalPadding == null) {
            this.mOriginalPadding = new Rect(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        setPadding(0, 0, 0, 0);
    }

    private void restorePadding() {
        Rect rect = this.mOriginalPadding;
        if (rect != null) {
            setPadding(rect.left, this.mOriginalPadding.top, this.mOriginalPadding.right, this.mOriginalPadding.bottom);
            this.mOriginalPadding = null;
        }
    }

    private void removeBackground() {
        if (this.mOriginalBackground == null) {
            this.mOriginalBackground = getBackground();
        }
        setBackground(null);
    }

    private void restoreBackground() {
        Drawable drawable = this.mOriginalBackground;
        if (drawable != null) {
            setBackground(drawable);
            this.mOriginalBackground = null;
        }
    }

    private void restoreColors() {
        int i = this.mOriginalBackgroundColor;
        if (i != 1) {
            super.setBackgroundColor(i);
            this.mOriginalBackgroundColor = 1;
        }
        int i2 = this.mOriginalIconColor;
        if (i2 != 1) {
            super.setOriginalIconColor(i2);
            this.mOriginalIconColor = 1;
        }
    }

    @Override // com.android.internal.widget.CachingIconView, android.view.View
    @RemotableViewMethod
    public void setBackgroundColor(int i) {
        if (this.mAppIcon == null) {
            super.setBackgroundColor(i);
        } else {
            this.mOriginalBackgroundColor = i;
        }
    }

    @Override // com.android.internal.widget.CachingIconView
    @RemotableViewMethod
    public void setOriginalIconColor(int i) {
        if (this.mAppIcon == null) {
            super.setOriginalIconColor(i);
        } else {
            this.mOriginalIconColor = i;
        }
    }
}
