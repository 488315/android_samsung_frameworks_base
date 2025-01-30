package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.systemui.Prefs;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AccessibilityFloatingMenu implements IAccessibilityFloatingMenu {
    public final C10101 mContentObserver;
    public final Context mContext;
    public final DockTooltipView mDockTooltipView;
    public final C10134 mEnabledA11yServicesContentObserver;
    public final C10123 mFadeOutContentObserver;
    public final boolean mIsHideHandle;
    public final AccessibilityFloatingMenuView mMenuView;
    public final C10112 mSizeContentObserver;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu$2] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu$3] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu$4] */
    public AccessibilityFloatingMenu(Context context, boolean z) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                AccessibilityFloatingMenu accessibilityFloatingMenu = AccessibilityFloatingMenu.this;
                accessibilityFloatingMenu.mMenuView.onTargetsChanged(AccessibilityTargetHelper.getTargets(accessibilityFloatingMenu.mContext, 0));
            }
        };
        this.mSizeContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                AccessibilityFloatingMenu accessibilityFloatingMenu = AccessibilityFloatingMenu.this;
                accessibilityFloatingMenu.mMenuView.setSizeType(Settings.Secure.getIntForUser(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_size", 9, -2));
            }
        };
        this.mFadeOutContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu.3
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                AccessibilityFloatingMenu accessibilityFloatingMenu = AccessibilityFloatingMenu.this;
                accessibilityFloatingMenu.mMenuView.updateOpacityWith(Settings.Secure.getFloat(AccessibilityFloatingMenu.this.mContext.getContentResolver(), "accessibility_floating_menu_opacity", 0.55f), Settings.Secure.getInt(accessibilityFloatingMenu.mContext.getContentResolver(), "accessibility_floating_menu_fade_enabled", 1) == 1);
            }
        };
        this.mEnabledA11yServicesContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu.4
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                AccessibilityFloatingMenu.this.mMenuView.mAdapter.notifyDataSetChanged();
            }
        };
        this.mContext = context;
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = new AccessibilityFloatingMenuView(context, getPosition(context));
        this.mMenuView = accessibilityFloatingMenuView;
        this.mDockTooltipView = new DockTooltipView(context, accessibilityFloatingMenuView);
        this.mIsHideHandle = z;
    }

    public static Position getPosition(Context context) {
        String string = Prefs.getString(context, "AccessibilityFloatingMenuPosition", null);
        if (TextUtils.isEmpty(string)) {
            return new Position(1.0f, 0.77f);
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter = Position.sStringCommaSplitter;
        simpleStringSplitter.setString(string);
        if (simpleStringSplitter.hasNext()) {
            return new Position(Float.parseFloat(simpleStringSplitter.next()), Float.parseFloat(simpleStringSplitter.next()));
        }
        throw new IllegalArgumentException(KeyAttributes$$ExternalSyntheticOutline0.m21m("Invalid Position string: ", string));
    }
}
