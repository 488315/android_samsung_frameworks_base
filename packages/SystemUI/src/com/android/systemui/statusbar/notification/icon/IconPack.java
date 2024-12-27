package com.android.systemui.statusbar.notification.icon;

import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.StatusBarIconView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class IconPack {
    public final StatusBarIconView mAodIcon;
    public StatusBarIcon mAppIconDescriptor;
    public final boolean mAreIconsAvailable;
    public boolean mIsImportantConversation;
    public StatusBarIcon mPeopleAvatarDescriptor;
    public final StatusBarIconView mShelfIcon;
    public StatusBarIcon mSmallIconDescriptor;
    public final StatusBarIconView mStatusBarIcon;

    private IconPack(boolean z, StatusBarIconView statusBarIconView, StatusBarIconView statusBarIconView2, StatusBarIconView statusBarIconView3, IconPack iconPack) {
        this.mAreIconsAvailable = z;
        this.mStatusBarIcon = statusBarIconView;
        this.mShelfIcon = statusBarIconView2;
        this.mAodIcon = statusBarIconView3;
        if (iconPack != null) {
            this.mIsImportantConversation = iconPack.mIsImportantConversation;
        }
    }

    public static IconPack buildEmptyPack(IconPack iconPack) {
        return new IconPack(false, null, null, null, iconPack);
    }

    public static IconPack buildPack(StatusBarIconView statusBarIconView, StatusBarIconView statusBarIconView2, StatusBarIconView statusBarIconView3, IconPack iconPack) {
        return new IconPack(true, statusBarIconView, statusBarIconView2, statusBarIconView3, iconPack);
    }
}
