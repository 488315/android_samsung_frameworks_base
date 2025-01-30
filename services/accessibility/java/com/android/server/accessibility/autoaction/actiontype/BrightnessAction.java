package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.Context;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.provider.Settings;
import com.android.internal.accessibility.util.AccessibilityUtils;

/* loaded from: classes.dex */
public class BrightnessAction extends CornerActionType {
    public Context mContext;
    public String mType;
    public int mUserId;

    public BrightnessAction(Context context, String str, int i) {
        this.mContext = context;
        this.mType = str;
        this.mUserId = i;
    }

    public static BrightnessAction createAction(Context context, String str, int i) {
        return new BrightnessAction(context, str, i);
    }

    public static int getStringResId(String str) {
        str.hashCode();
        if (str.equals("reduce_brightness")) {
            return R.string.adb_active_notification_message;
        }
        if (str.equals("increase_brightness")) {
            return R.string.activity_chooser_view_dialog_title_default;
        }
        throw new IllegalArgumentException("Wrong Brightness Action Type");
    }

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public void performCornerAction(int i) {
        boolean isFoldedLargeCoverScreen = AccessibilityUtils.isFoldedLargeCoverScreen();
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), isFoldedLargeCoverScreen ? "sub_screen_brightness" : "screen_brightness", 0, this.mUserId);
        String str = this.mType;
        str.hashCode();
        if (str.equals("reduce_brightness")) {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), isFoldedLargeCoverScreen ? "sub_screen_brightness" : "screen_brightness", Math.max(intForUser - 17, 0), this.mUserId);
        } else {
            if (str.equals("increase_brightness")) {
                Settings.System.putIntForUser(this.mContext.getContentResolver(), isFoldedLargeCoverScreen ? "sub_screen_brightness" : "screen_brightness", Math.min(intForUser + 17, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT), this.mUserId);
                return;
            }
            throw new IllegalArgumentException("Wrong Brightness Action Type");
        }
    }
}
