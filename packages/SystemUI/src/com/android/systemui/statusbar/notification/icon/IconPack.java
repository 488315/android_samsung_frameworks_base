package com.android.systemui.statusbar.notification.icon;

import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.statusbar.StatusBarIconView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class IconPack {
    public final StatusBarIconView mAodIcon;
    public final boolean mAreIconsAvailable;
    public boolean mIsImportantConversation;
    public StatusBarIcon mPeopleAvatarDescriptor;
    public final StatusBarIconView mShelfIcon;
    public StatusBarIcon mSmallIconDescriptor;
    public final StatusBarIconView mStatusBarChipIcon;
    public final StatusBarIconView mStatusBarIcon;

    private IconPack(boolean z, StatusBarIconView statusBarIconView, StatusBarIconView statusBarIconView2, StatusBarIconView statusBarIconView3, StatusBarIconView statusBarIconView4, IconPack iconPack) {
        this.mAreIconsAvailable = z;
        this.mStatusBarIcon = statusBarIconView;
        this.mStatusBarChipIcon = statusBarIconView2;
        this.mShelfIcon = statusBarIconView3;
        this.mAodIcon = statusBarIconView4;
        if (iconPack != null) {
            this.mIsImportantConversation = iconPack.mIsImportantConversation;
        }
    }

    public static IconPack buildEmptyPack(IconPack iconPack) {
        return new IconPack(false, null, null, null, null, iconPack);
    }

    public static IconPack buildPack(StatusBarIconView statusBarIconView, StatusBarIconView statusBarIconView2, StatusBarIconView statusBarIconView3, StatusBarIconView statusBarIconView4, IconPack iconPack) {
        return new IconPack(true, statusBarIconView, statusBarIconView2, statusBarIconView3, statusBarIconView4, iconPack);
    }
}
