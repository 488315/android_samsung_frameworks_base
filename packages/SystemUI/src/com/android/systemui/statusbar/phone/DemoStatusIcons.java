package com.android.systemui.statusbar.phone;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.R;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.pipeline.mobile.ui.view.ModernStatusBarMobileView;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon$initializer$1;
import com.android.systemui.statusbar.pipeline.wifi.ui.view.ModernStatusBarWifiView;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DemoStatusIcons extends StatusIconContainer implements DemoMode, DarkIconDispatcher.DarkReceiver {
    public int mColor;
    public int mContrastColor;
    public boolean mDemoMode;
    public final int mIconSize;
    public final StatusBarLocation mLocation;
    public final MobileIconsViewModel mMobileIconsViewModel;
    public final ArrayList mModernMobileViews;
    public ModernStatusBarWifiView mModernWifiView;
    public final LinearLayout mStatusIcons;

    public DemoStatusIcons(LinearLayout linearLayout, MobileIconsViewModel mobileIconsViewModel, StatusBarLocation statusBarLocation, int i) {
        super(linearLayout.getContext());
        this.mModernMobileViews = new ArrayList();
        this.mStatusIcons = linearLayout;
        this.mIconSize = i;
        this.mColor = -301989889;
        this.mContrastColor = -16777216;
        this.mMobileIconsViewModel = mobileIconsViewModel;
        this.mLocation = statusBarLocation;
        if (linearLayout instanceof StatusIconContainer) {
            this.mShouldRestrictIcons = ((StatusIconContainer) linearLayout).mShouldRestrictIcons;
        } else {
            this.mShouldRestrictIcons = false;
        }
        setLayoutParams(linearLayout.getLayoutParams());
        setPadding(linearLayout.getPaddingLeft(), linearLayout.getPaddingTop(), linearLayout.getPaddingRight(), linearLayout.getPaddingBottom());
        setOrientation(linearLayout.getOrientation());
        setGravity(16);
        ViewGroup viewGroup = (ViewGroup) linearLayout.getParent();
        viewGroup.addView(this, viewGroup.indexOfChild(linearLayout));
    }

    public final void addBindableIcon(StatusBarIconHolder.BindableIconHolder bindableIconHolder) {
        addView(((DeviceBasedSatelliteBindableIcon$initializer$1) bindableIconHolder.initializer).createAndBind(((LinearLayout) this).mContext), new LinearLayout.LayoutParams(-2, this.mIconSize));
    }

    public final void addModernWifiView(LocationBasedWifiViewModel locationBasedWifiViewModel) {
        Log.d("DemoStatusIcons", "addModernDemoWifiView: ");
        ModernStatusBarWifiView constructAndBind = ModernStatusBarWifiView.constructAndBind(((LinearLayout) this).mContext, ImsProfile.PDN_WIFI, locationBasedWifiViewModel);
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            if (i >= getChildCount()) {
                break;
            }
            if (getChildAt(i) instanceof ModernStatusBarMobileView) {
                childCount = i;
                break;
            }
            i++;
        }
        this.mModernWifiView = constructAndBind;
        constructAndBind.setStaticDrawableColor(this.mColor, this.mContrastColor);
        addView(constructAndBind, childCount, new LinearLayout.LayoutParams(-2, this.mIconSize));
    }

    @Override // com.android.systemui.demomode.DemoMode
    public final List demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(IMSParameter.CALL.STATUS);
        return arrayList;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        String string = bundle.getString("volume");
        if (string != null) {
            updateSlot(string.equals("vibrate") ? R.drawable.stat_sys_ringer_vibrate : 0, "volume");
        }
        String string2 = bundle.getString("zen");
        if (string2 != null) {
            updateSlot(string2.equals("dnd") ? R.drawable.stat_sys_dnd : 0, "zen");
        }
        String string3 = bundle.getString("bluetooth");
        if (string3 != null) {
            updateSlot(string3.equals("connected") ? R.drawable.stat_sys_data_bluetooth_connected : 0, "bluetooth");
        }
        String string4 = bundle.getString("location");
        if (string4 != null) {
            updateSlot(string4.equals("show") ? PhoneStatusBarPolicy.LOCATION_STATUS_ICON_ID : 0, "location");
        }
        String string5 = bundle.getString("alarm");
        if (string5 != null) {
            updateSlot(string5.equals("show") ? R.drawable.stat_sys_alarm : 0, "alarm_clock");
        }
        String string6 = bundle.getString("tty");
        if (string6 != null) {
            updateSlot(string6.equals("show") ? R.drawable.stat_sys_tty_mode : 0, "tty");
        }
        String string7 = bundle.getString("mute");
        if (string7 != null) {
            updateSlot(string7.equals("show") ? android.R.drawable.stat_notify_call_mute : 0, "mute");
        }
        String string8 = bundle.getString("speakerphone");
        if (string8 != null) {
            updateSlot(string8.equals("show") ? android.R.drawable.stat_sys_speakerphone : 0, "speakerphone");
        }
        String string9 = bundle.getString("cast");
        if (string9 != null) {
            updateSlot(string9.equals("show") ? R.drawable.stat_sys_cast : 0, "cast");
        }
        String string10 = bundle.getString("hotspot");
        if (string10 != null) {
            updateSlot(string10.equals("show") ? R.drawable.stat_sys_hotspot : 0, "hotspot");
        }
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChangedWithContrast(ArrayList arrayList, int i, int i2) {
        setColor(i, i2);
        ModernStatusBarWifiView modernStatusBarWifiView = this.mModernWifiView;
        if (modernStatusBarWifiView != null) {
            modernStatusBarWifiView.onDarkChangedWithContrast(arrayList, i, i2);
        }
        Iterator it = this.mModernMobileViews.iterator();
        while (it.hasNext()) {
            ((ModernStatusBarMobileView) it.next()).onDarkChangedWithContrast(arrayList, i, i2);
        }
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        this.mDemoMode = false;
        this.mStatusIcons.setVisibility(0);
        this.mModernMobileViews.clear();
        setVisibility(8);
    }

    public final void setColor(int i, int i2) {
        this.mColor = i;
        this.mContrastColor = i2;
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            StatusIconDisplayable statusIconDisplayable = (StatusIconDisplayable) getChildAt(i3);
            statusIconDisplayable.setStaticDrawableColor(this.mColor, this.mContrastColor);
            statusIconDisplayable.setDecorColor(this.mColor);
        }
    }

    public final void updateSlot(int i, String str) {
        if (this.mDemoMode) {
            String packageName = ((LinearLayout) this).mContext.getPackageName();
            int i2 = 0;
            while (true) {
                if (i2 >= getChildCount()) {
                    i2 = -1;
                    break;
                }
                View childAt = getChildAt(i2);
                if (childAt instanceof StatusBarIconView) {
                    StatusBarIconView statusBarIconView = (StatusBarIconView) childAt;
                    if (str.equals(statusBarIconView.getTag())) {
                        if (i != 0) {
                            StatusBarIcon statusBarIcon = statusBarIconView.mIcon;
                            statusBarIcon.visible = true;
                            statusBarIcon.icon = Icon.createWithResource(statusBarIcon.icon.getResPackage(), i);
                            statusBarIconView.set(statusBarIcon);
                            statusBarIconView.updateDrawable(true);
                            return;
                        }
                    }
                }
                i2++;
            }
            if (i == 0) {
                if (i2 != -1) {
                    removeViewAt(i2);
                    return;
                }
                return;
            }
            StatusBarIcon statusBarIcon2 = new StatusBarIcon(packageName, UserHandle.SYSTEM, i, 0, 0, "Demo", StatusBarIcon.Type.SystemIcon);
            statusBarIcon2.visible = true;
            StatusBarIconView statusBarIconView2 = new StatusBarIconView(getContext(), str, null, false);
            statusBarIconView2.setTag(str);
            statusBarIconView2.set(statusBarIcon2);
            statusBarIconView2.setStaticDrawableColor(this.mColor);
            statusBarIconView2.setDecorColor(this.mColor);
            addView(statusBarIconView2, 0, new LinearLayout.LayoutParams(-2, this.mIconSize));
        }
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
    }
}
