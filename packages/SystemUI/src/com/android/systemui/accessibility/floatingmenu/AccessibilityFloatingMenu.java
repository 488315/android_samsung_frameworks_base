package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.systemui.Prefs;

public final class AccessibilityFloatingMenu implements IAccessibilityFloatingMenu {
    public final AnonymousClass1 mContentObserver;
    public final Context mContext;
    public final DockTooltipView mDockTooltipView;
    public final AnonymousClass4 mEnabledA11yServicesContentObserver;
    public final AnonymousClass3 mFadeOutContentObserver;
    public final boolean mIsHideHandle;
    public final AccessibilityFloatingMenuView mMenuView;
    public final AnonymousClass2 mSizeContentObserver;

    public AccessibilityFloatingMenu(Context context, boolean z) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z2) {
                AccessibilityFloatingMenu accessibilityFloatingMenu = AccessibilityFloatingMenu.this;
                accessibilityFloatingMenu.mMenuView.onTargetsChanged(AccessibilityTargetHelper.getTargets(accessibilityFloatingMenu.mContext, 1));
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
        String string = Prefs.get(context).getString("AccessibilityFloatingMenuPosition", null);
        if (TextUtils.isEmpty(string)) {
            return new Position(1.0f, 0.77f);
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter = Position.sStringCommaSplitter;
        simpleStringSplitter.setString(string);
        if (simpleStringSplitter.hasNext()) {
            return new Position(Float.parseFloat(simpleStringSplitter.next()), Float.parseFloat(simpleStringSplitter.next()));
        }
        throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Invalid Position string: ", string));
    }
}
