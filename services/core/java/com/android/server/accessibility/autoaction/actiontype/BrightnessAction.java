package com.android.server.accessibility.autoaction.actiontype;

import android.content.ContentResolver;
import android.content.Context;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.provider.Settings;

import com.android.internal.accessibility.util.AccessibilityUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BrightnessAction extends CornerActionType {
    public Context mContext;
    public String mType;
    public int mUserId;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        boolean isFoldedLargeCoverScreen = AccessibilityUtils.isFoldedLargeCoverScreen();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String str = isFoldedLargeCoverScreen ? "sub_screen_brightness" : "screen_brightness";
        int i2 = this.mUserId;
        int intForUser = Settings.System.getIntForUser(contentResolver, str, 0, i2);
        String str2 = this.mType;
        str2.getClass();
        if (str2.equals("reduce_brightness")) {
            Settings.System.putIntForUser(
                    this.mContext.getContentResolver(),
                    isFoldedLargeCoverScreen ? "sub_screen_brightness" : "screen_brightness",
                    Math.max(intForUser - 17, 0),
                    i2);
        } else {
            if (!str2.equals("increase_brightness")) {
                throw new IllegalArgumentException("Wrong Brightness Action Type");
            }
            Settings.System.putIntForUser(
                    this.mContext.getContentResolver(),
                    isFoldedLargeCoverScreen ? "sub_screen_brightness" : "screen_brightness",
                    Math.min(
                            intForUser + 17,
                            IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
                    i2);
        }
    }
}
