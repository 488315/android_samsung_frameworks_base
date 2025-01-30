package com.android.systemui.shade;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecExpandQSAtOnceController {
    public final Context mContext;
    public float mDisplayRatioOfDivider;
    public final BooleanSupplier mQsExpandSupplier;
    public int mDisplayWidthOfDivider = 0;
    public boolean mShouldCloseAtOnce = false;
    public final SettingsListener mSettingsListener = new SettingsListener(this, 0);
    public final SettingsHelper mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingsListener implements SettingsHelper.OnChangedCallback {
        public final Uri[] mSettingsValueList;

        public /* synthetic */ SettingsListener(SecExpandQSAtOnceController secExpandQSAtOnceController, int i) {
            this();
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (uri == null) {
                return;
            }
            boolean equals = uri.equals(Settings.Global.getUriFor("swipe_directly_to_quick_setting"));
            SecExpandQSAtOnceController secExpandQSAtOnceController = SecExpandQSAtOnceController.this;
            if (equals) {
                secExpandQSAtOnceController.printLogLine("onChanged(swipe_directly_to_quick_setting)");
                secExpandQSAtOnceController.updateResources();
            } else if (uri.equals(Settings.Global.getUriFor("swipe_directly_to_quick_setting_area"))) {
                secExpandQSAtOnceController.printLogLine("onChanged(swipe_directly_to_quick_setting_area)");
                secExpandQSAtOnceController.updateResources();
            } else if (uri.equals(Settings.Global.getUriFor("swipe_directly_to_quick_setting_position"))) {
                secExpandQSAtOnceController.printLogLine("onChanged(swipe_directly_to_quick_setting_position)");
            }
        }

        private SettingsListener() {
            this.mSettingsValueList = new Uri[]{Settings.Global.getUriFor("swipe_directly_to_quick_setting"), Settings.Global.getUriFor("swipe_directly_to_quick_setting_area"), Settings.Global.getUriFor("swipe_directly_to_quick_setting_position")};
        }
    }

    public SecExpandQSAtOnceController(Context context, BooleanSupplier booleanSupplier) {
        this.mContext = context;
        this.mQsExpandSupplier = booleanSupplier;
    }

    public final void printLogLine(String str) {
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, ", enabled:");
        SettingsHelper settingsHelper = this.mSettingsHelper;
        m2m.append(settingsHelper.isExpandQsAtOnceEnabled());
        m2m.append(", SidePosition:");
        SettingsHelper.ItemMap itemMap = settingsHelper.mItemLists;
        m2m.append(itemMap.get("swipe_directly_to_quick_setting_position").getStringValue());
        m2m.append(", Ratio:");
        m2m.append(this.mDisplayRatioOfDivider);
        m2m.append("(setting:");
        m2m.append(itemMap.get("swipe_directly_to_quick_setting_area").getIntValue());
        m2m.append("), Width:");
        RecyclerView$$ExternalSyntheticOutline0.m46m(m2m, this.mDisplayWidthOfDivider, "ExpandQSAtOnceController");
    }

    public final void updateResources() {
        printLogLine("updateResources(before)");
        SettingsHelper settingsHelper = this.mSettingsHelper;
        if (settingsHelper.isExpandQsAtOnceEnabled()) {
            int intValue = settingsHelper.mItemLists.get("swipe_directly_to_quick_setting_area").getIntValue();
            this.mDisplayRatioOfDivider = intValue >= 0 ? intValue * 0.01f : 0.8f;
            this.mDisplayWidthOfDivider = (int) (DeviceState.getDisplayWidth(this.mContext) * this.mDisplayRatioOfDivider);
            printLogLine("updateResources(after)");
        }
    }
}
