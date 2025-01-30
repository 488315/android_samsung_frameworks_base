package com.samsung.android.knox.lockscreen;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.widget.ImageView;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.lockscreen.LSOItemContainer;
import com.samsung.android.knox.lockscreen.LSOItemText;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LockscreenOverlay {
    public static final int CUSTOM_LAYER = 2;
    public static final float DEFAULT_ALPHA_LEVEL = 1.0f;
    public static final int DEFAULT_LAYER = 1;
    public static final int EMERGENCY_PHONE_LAYER = 3;
    public static final int ERROR_BAD_STATE = -6;
    public static final int ERROR_FAILED = -4;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_ALLOWED = -1;
    public static final int ERROR_NOT_READY = -5;
    public static final int ERROR_NOT_SUPPORTED = -3;
    public static final int ERROR_PERMISSION_DENIED = -2;
    public static final int ERROR_UNKNOWN = -2000;
    public static final String TAG = "LSO_LockscreenOverlay";
    public static LockscreenOverlay gLSO;
    public static final Object mSync = new Object();
    public final Context mContext;
    public ContextInfo mContextInfo;
    public final LSOInterface mLSO;
    public IMiscPolicy mMiscService;

    public LockscreenOverlay(ContextInfo contextInfo, Context context) {
        this.mContext = context;
        this.mContextInfo = contextInfo;
        this.mLSO = LSOInterface.getInstance(contextInfo, context);
    }

    public static LockscreenOverlay createInstance(ContextInfo contextInfo, Context context) {
        return new LockscreenOverlay(contextInfo, context.getApplicationContext());
    }

    public static LSOItemData createLSOItem_EmergencyPhone(Context context, String str) {
        LSOItemContainer lSOItemContainer = new LSOItemContainer();
        lSOItemContainer.addItem(new LSOItemSpace(-1, LSOUtils.convertDipToPixel(context, 20)));
        LSOItemWidget lSOItemWidget = new LSOItemWidget();
        lSOItemWidget.setWidget("com.samsung.android.knox.lockscreen.EmergencyPhoneWidget");
        lSOItemWidget.setAttribute(LSOAttrConst.EPW_ATTR_PHONE_NUMBER, str);
        lSOItemWidget.setAttribute(LSOAttrConst.ATTR_ORIENTATION, Boolean.TRUE);
        lSOItemWidget.setAttribute(LSOAttrConst.EPW_ATTR_SHOW_DEFAULT_TEXT, Boolean.FALSE);
        lSOItemWidget.setDimension(LSOUtils.convertDipToPixel(context, 100), LSOUtils.convertDipToPixel(context, 200));
        lSOItemWidget.setGravity(51);
        lSOItemContainer.addItem(lSOItemWidget);
        lSOItemContainer.setAttribute(LSOAttrConst.ATTR_ALPHA, Float.valueOf(1.0f));
        lSOItemContainer.setGravity(51);
        return lSOItemContainer;
    }

    public static LSOItemData createLSOItem_StyleEnterprise(Context context, String str, String str2, String str3, String str4) {
        int rgb = Color.rgb(192, 199, IKnoxCustomManager.Stub.TRANSACTION_getVibrationIntensity);
        String resourceString = LSOUtils.getResourceString(context, R.string.stk_cc_ss_to_ss);
        String resourceString2 = LSOUtils.getResourceString(context, R.string.stk_cc_ss_to_ussd);
        String resourceString3 = LSOUtils.getResourceString(context, R.string.stk_cc_ss_to_dial_video);
        LSOItemContainer lSOItemContainer = new LSOItemContainer();
        lSOItemContainer.addItem(new LSOItemSpace(-1, 0, 10.0f));
        LSOItemContainer lSOItemContainer2 = new LSOItemContainer();
        LSOItemContainer lSOItemContainer3 = new LSOItemContainer();
        LSOItemText lSOItemText = new LSOItemText(resourceString);
        lSOItemText.setGravity(17);
        lSOItemContainer3.addItem(lSOItemText);
        if (str2 != null) {
            LSOItemImage lSOItemImage = new LSOItemImage(str2);
            lSOItemImage.setDimension(-1, -2);
            lSOItemImage.setAttribute(LSOAttrConst.ATTR_MAX_HEIGHT, Integer.valueOf(LSOUtils.convertDipToPixel(context, 75)));
            lSOItemContainer3.addItem(lSOItemImage);
        }
        LSOItemText lSOItemText2 = new LSOItemText(str);
        lSOItemText2.setTextSize(LSOItemText.LSOTextSize.LARGE);
        lSOItemText2.setTextStyle(1);
        lSOItemText2.setGravity(17);
        lSOItemText2.setAttribute(LSOAttrConst.ATTR_MAX_LINES, (Integer) 4);
        lSOItemContainer3.addItem(lSOItemText2);
        LSOItemText lSOItemText3 = new LSOItemText(resourceString2);
        lSOItemText3.setTextStyle(1);
        lSOItemText3.setGravity(17);
        lSOItemContainer3.addItem(lSOItemText3);
        lSOItemContainer3.setBgColor(rgb);
        lSOItemContainer3.setDimension(-1, -2);
        lSOItemContainer2.addItem(lSOItemContainer3);
        lSOItemContainer2.setGravity(17);
        lSOItemContainer2.setDimension(-1, 0, 44.0f);
        lSOItemContainer.addItem(lSOItemContainer2);
        lSOItemContainer.addItem(new LSOItemSpace(-1, 0, 10.0f));
        LSOItemContainer lSOItemContainer4 = new LSOItemContainer();
        LSOItemContainer lSOItemContainer5 = new LSOItemContainer();
        lSOItemContainer5.setOrientation(LSOItemContainer.ORIENTATION.HORIZONTAL);
        LSOItemText lSOItemText4 = new LSOItemText(resourceString3);
        lSOItemText4.setDimension(0, -2);
        lSOItemText4.setWeight(0.6f);
        LSOItemText.LSOTextSize lSOTextSize = LSOItemText.LSOTextSize.SMALL;
        lSOItemText4.setTextSize(lSOTextSize);
        lSOItemText4.setTextStyle(1);
        lSOItemText4.setGravity(17);
        lSOItemText4.setAttribute(LSOAttrConst.ATTR_MAX_LINES, (Integer) 4);
        lSOItemContainer5.addItem(lSOItemText4);
        LSOItemContainer lSOItemContainer6 = new LSOItemContainer();
        lSOItemContainer6.setDimension(0, -2);
        lSOItemContainer6.setWeight(1.0f);
        lSOItemContainer6.setGravity(17);
        LSOItemContainer lSOItemContainer7 = new LSOItemContainer();
        lSOItemContainer7.setDimension(-2, -2);
        lSOItemContainer7.setGravity(17);
        LSOItemText lSOItemText5 = new LSOItemText(str3);
        lSOItemText5.setTextSize(lSOTextSize);
        lSOItemText5.setTextStyle(1);
        lSOItemText5.setAttribute(LSOAttrConst.ATTR_MAX_LINES, (Integer) 3);
        lSOItemContainer7.addItem(lSOItemText5);
        LSOItemText lSOItemText6 = new LSOItemText(str4);
        lSOItemText6.setTextSize(lSOTextSize);
        lSOItemText6.setTextStyle(1);
        lSOItemText6.setAttribute(LSOAttrConst.ATTR_SINGLE_LINE, Boolean.TRUE);
        lSOItemContainer7.addItem(lSOItemText6);
        lSOItemContainer6.addItem(lSOItemContainer7);
        lSOItemContainer5.addItem(lSOItemContainer6);
        lSOItemContainer5.setDimension(-1, -2);
        lSOItemContainer5.setBgColor(rgb);
        lSOItemContainer5.setGravity(17);
        lSOItemContainer4.addItem(lSOItemContainer5);
        lSOItemContainer4.setGravity(48);
        lSOItemContainer4.setDimension(-1, 0, 30.0f);
        lSOItemContainer.addItem(lSOItemContainer4);
        lSOItemContainer.addItem(new LSOItemSpace(-1, 0, 6.0f));
        return lSOItemContainer;
    }

    public static LockscreenOverlay getInstance(Context context) {
        LockscreenOverlay lockscreenOverlay;
        synchronized (mSync) {
            if (gLSO == null && context != null) {
                gLSO = new LockscreenOverlay(new ContextInfo(Process.myUid()), context.getApplicationContext());
            }
            lockscreenOverlay = gLSO;
        }
        return lockscreenOverlay;
    }

    public static LSOEmergencyPhoneInfo parseLSOItem_EmergencyPhoneInfo(Context context, LSOItemData lSOItemData) {
        LSOItemWidget lSOItemWidget;
        LSOAttributeSet attrs;
        if (lSOItemData.getType() != 4) {
            return null;
        }
        LSOItemContainer lSOItemContainer = (LSOItemContainer) lSOItemData;
        int numItems = lSOItemContainer.getNumItems();
        int i = 0;
        while (true) {
            if (i >= numItems) {
                lSOItemWidget = null;
                break;
            }
            LSOItemData item = lSOItemContainer.getItem(i);
            if (item.getType() == 5) {
                lSOItemWidget = (LSOItemWidget) item;
                break;
            }
            i++;
        }
        if (lSOItemWidget == null || (attrs = lSOItemWidget.getAttrs()) == null) {
            return null;
        }
        LSOEmergencyPhoneInfo lSOEmergencyPhoneInfo = new LSOEmergencyPhoneInfo("");
        lSOEmergencyPhoneInfo.gravity = lSOItemWidget.gravity;
        if (attrs.containsKey(LSOAttrConst.EPW_ATTR_PHONE_NUMBER)) {
            lSOEmergencyPhoneInfo.phoneNumber = attrs.getAsString(LSOAttrConst.EPW_ATTR_PHONE_NUMBER);
        }
        if (attrs.containsKey(LSOAttrConst.ATTR_TEXT)) {
            lSOEmergencyPhoneInfo.text = attrs.getAsString(LSOAttrConst.ATTR_TEXT);
        }
        if (attrs.containsKey(LSOAttrConst.ATTR_IMAGE_SRC)) {
            lSOEmergencyPhoneInfo.icon = attrs.getAsString(LSOAttrConst.ATTR_IMAGE_SRC);
        }
        if (attrs.containsKey(LSOAttrConst.ATTR_TOP_POSITION)) {
            lSOEmergencyPhoneInfo.topPosition = attrs.getAsInteger(LSOAttrConst.ATTR_TOP_POSITION).intValue();
        }
        if (attrs.containsKey(LSOAttrConst.ATTR_BOTTOM_POSITION)) {
            lSOEmergencyPhoneInfo.bottomPosition = attrs.getAsInteger(LSOAttrConst.ATTR_BOTTOM_POSITION).intValue();
        }
        if (attrs.containsKey(LSOAttrConst.EPW_ATTR_SHOW_BG)) {
            lSOEmergencyPhoneInfo.showBackground = attrs.getAsBoolean(LSOAttrConst.EPW_ATTR_SHOW_BG).booleanValue();
        }
        if (attrs.containsKey(LSOAttrConst.EPW_ATTR_SHOW_DEFAULT_TEXT)) {
            lSOEmergencyPhoneInfo.showDefaultText = attrs.getAsBoolean(LSOAttrConst.EPW_ATTR_SHOW_DEFAULT_TEXT).booleanValue();
        }
        return lSOEmergencyPhoneInfo;
    }

    public final boolean canConfigure() {
        return this.mLSO.canConfigure(-1);
    }

    public final boolean changeLockScreenString(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.changeLockScreenString");
        if (getMiscService() == null) {
            return false;
        }
        try {
            return this.mMiscService.changeLockScreenString(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed changeLockScreenString", e);
            return false;
        }
    }

    public final int configure(String str, String str2, String str3, String str4) {
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.configure(String, String, String, String)");
        if (str == null || str3 == null || str.length() == 0 || str3.length() == 0) {
            throw new InvalidParameterException("Name and Address cannot be null");
        }
        if (str2 != null && str2.length() > 0 && (str2 = LSOUtils.copyFileToDataLocalDirectory(this.mContext, str2, "logo")) == null) {
            Log.e(TAG, "Failed to copy enterprise logo");
            return -4;
        }
        LSOItemData createLSOItem_StyleEnterprise = createLSOItem_StyleEnterprise(this.mContext, str, str2, str3, str4);
        if (createLSOItem_StyleEnterprise != null) {
            return setData(createLSOItem_StyleEnterprise);
        }
        return -2000;
    }

    public final float getAlpha() {
        Float valueOf = Float.valueOf(1.0f);
        LSOAttributeSet preferences = this.mLSO.getPreferences();
        if (preferences != null && preferences.containsKey(LSOAttrConst.ATTR_ALPHA)) {
            valueOf = preferences.getAsFloat(LSOAttrConst.ATTR_ALPHA);
        }
        if (valueOf == null) {
            return 1.0f;
        }
        return valueOf.floatValue();
    }

    public final String getCurrentLockScreenString() {
        if (getMiscService() == null) {
            return null;
        }
        try {
            return this.mMiscService.getCurrentLockScreenString(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed getCurrentLockScreenString!!!", e);
            return null;
        }
    }

    public final LSOItemData getData() {
        return this.mLSO.getData(1);
    }

    public final String getEmergencyPhone() {
        LSOItemData data = getData(3);
        if (data != null) {
            return data.getId();
        }
        return null;
    }

    public final LSOEmergencyPhoneInfo getEmergencyPhoneInfo() {
        LSOItemData data = getData(3);
        if (data != null) {
            return parseLSOItem_EmergencyPhoneInfo(this.mContext, data);
        }
        return null;
    }

    public final IMiscPolicy getMiscService() {
        if (this.mMiscService == null) {
            this.mMiscService = IMiscPolicy.Stub.asInterface(ServiceManager.getService("misc_policy"));
        }
        return this.mMiscService;
    }

    public final boolean isConfigured() {
        return this.mLSO.isConfigured(0);
    }

    public final void removeEmergencyPhone() {
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.removeEmergencyPhone");
        resetData(3);
    }

    public final void resetAll() {
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.resetAll");
        this.mLSO.resetData(0);
        resetWallpaper();
    }

    public final void resetData(int i) {
        this.mLSO.resetData(i);
    }

    public final void resetOverlay() {
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.resetOverlay");
        this.mLSO.resetData(1);
    }

    public final void resetWallpaper() {
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.resetWallpaper");
        this.mLSO.resetWallpaper();
    }

    public final int setAlpha(float f) {
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.setAlpha");
        if (f < 0.0f || f > 1.0f) {
            throw new InvalidParameterException("Alpha values must be in between 0 to 1");
        }
        LSOAttributeSet preferences = this.mLSO.getPreferences();
        if (preferences == null) {
            preferences = new LSOAttributeSet();
        }
        preferences.put(LSOAttrConst.ATTR_ALPHA, Float.valueOf(f));
        return this.mLSO.setPreferences(preferences);
    }

    public final int setData(LSOItemData lSOItemData) {
        if (lSOItemData != null) {
            lSOItemData.openFileDescriptor();
        }
        int data = this.mLSO.setData(lSOItemData, 1);
        if (lSOItemData != null) {
            lSOItemData.closeFileDescriptor();
        }
        LSOUtils.cleanDataLocalDirectory(this.mContext);
        return data;
    }

    public final int setEmergencyPhone(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.setEmergencyPhone");
        if (str == null || str.length() == 0) {
            throw new InvalidParameterException("Emergency/Support phone cannot be null");
        }
        LSOItemData createLSOItem_EmergencyPhone = createLSOItem_EmergencyPhone(this.mContext, str);
        createLSOItem_EmergencyPhone.setId(str);
        return setData(createLSOItem_EmergencyPhone, 3);
    }

    public final int setEmergencyPhoneInfo(LSOEmergencyPhoneInfo lSOEmergencyPhoneInfo) {
        String str;
        int i;
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.setEmergencyPhoneInfo");
        if (lSOEmergencyPhoneInfo == null || (str = lSOEmergencyPhoneInfo.phoneNumber) == null || str.length() == 0) {
            throw new InvalidParameterException("Emergency/Support phone cannot be null");
        }
        int i2 = lSOEmergencyPhoneInfo.topPosition;
        if (i2 < 0 || (i = lSOEmergencyPhoneInfo.bottomPosition) > 100 || i <= i2) {
            throw new InvalidParameterException("Invalid argument list");
        }
        String str2 = lSOEmergencyPhoneInfo.icon;
        if (str2 != null) {
            String copyFileToDataLocalDirectory = LSOUtils.copyFileToDataLocalDirectory(this.mContext, str2, "epw");
            lSOEmergencyPhoneInfo.icon = copyFileToDataLocalDirectory;
            if (copyFileToDataLocalDirectory == null) {
                Log.e(TAG, "Failed to copy icon");
                return -4;
            }
        }
        LSOItemData createLSOItem_EmergencyPhone = createLSOItem_EmergencyPhone(this.mContext, lSOEmergencyPhoneInfo);
        createLSOItem_EmergencyPhone.setId(lSOEmergencyPhoneInfo.phoneNumber);
        return setData(createLSOItem_EmergencyPhone, 3);
    }

    public final int setWallpaper(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.setWallpaper");
        if (str == null || str.length() == 0) {
            throw new InvalidParameterException("Wallpaper cannot be null");
        }
        String copyFileToDataLocalDirectory = LSOUtils.copyFileToDataLocalDirectory(this.mContext, str, "wp");
        if (copyFileToDataLocalDirectory == null) {
            Log.e(TAG, "Failed to copy wallaper");
            return -4;
        }
        try {
            ParcelFileDescriptor open = ParcelFileDescriptor.open(new File(copyFileToDataLocalDirectory), QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            int wallpaper = this.mLSO.setWallpaper(copyFileToDataLocalDirectory, open);
            if (open != null) {
                try {
                    open.close();
                } catch (IOException unused) {
                    Log.e(TAG, "Failed to close file descriptor");
                }
            }
            LSOUtils.cleanDataLocalDirectory(this.mContext);
            return wallpaper;
        } catch (FileNotFoundException unused2) {
            Log.w(TAG, "Error: file not found");
            return -4;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LSOEmergencyPhoneInfo {
        public int bottomPosition;
        public int gravity;
        public String icon;
        public String phoneNumber;
        public boolean showBackground;
        public boolean showDefaultText;
        public String text;
        public int topPosition;

        public LSOEmergencyPhoneInfo(String str) {
            init();
            this.phoneNumber = str;
        }

        public final void init() {
            this.phoneNumber = null;
            this.topPosition = 0;
            this.bottomPosition = 100;
            this.icon = null;
            this.gravity = 17;
            this.showBackground = true;
            this.showDefaultText = false;
            this.text = null;
        }

        public LSOEmergencyPhoneInfo(String str, String str2) {
            init();
            this.phoneNumber = str;
            this.icon = str2;
        }

        public LSOEmergencyPhoneInfo(String str, int i, String str2, int i2) {
            init();
            this.phoneNumber = str;
            this.topPosition = i;
            this.bottomPosition = i2;
            this.icon = str2;
        }
    }

    public final LSOItemData getData(int i) {
        return this.mLSO.getData(i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LSOImage {
        public int bottomPosition;
        public String filePath;
        public ImageView.ScaleType scaleType;
        public int topPosition;

        public LSOImage() {
            this.topPosition = 0;
            this.bottomPosition = 100;
            this.scaleType = ImageView.ScaleType.FIT_CENTER;
        }

        public LSOImage(String str) {
            this.topPosition = 0;
            this.bottomPosition = 100;
            this.filePath = str;
            this.scaleType = ImageView.ScaleType.FIT_CENTER;
        }

        public LSOImage(int i, String str, int i2) {
            this.topPosition = i;
            this.bottomPosition = i2;
            this.filePath = str;
            this.scaleType = ImageView.ScaleType.FIT_CENTER;
        }

        public LSOImage(int i, String str, int i2, ImageView.ScaleType scaleType) {
            this.topPosition = i;
            this.bottomPosition = i2;
            this.filePath = str;
            this.scaleType = scaleType;
        }
    }

    public final int setData(LSOItemData lSOItemData, int i) {
        if (lSOItemData != null) {
            lSOItemData.openFileDescriptor();
        }
        int data = this.mLSO.setData(lSOItemData, i);
        if (lSOItemData != null) {
            lSOItemData.closeFileDescriptor();
        }
        return data;
    }

    public static LockscreenOverlay getInstance(ContextInfo contextInfo, Context context) {
        LockscreenOverlay lockscreenOverlay;
        synchronized (mSync) {
            if (gLSO == null) {
                gLSO = new LockscreenOverlay(contextInfo, context.getApplicationContext());
            }
            lockscreenOverlay = gLSO;
        }
        return lockscreenOverlay;
    }

    public final int configure(LSOImage[] lSOImageArr) {
        LSOImage lSOImage;
        int i;
        int i2;
        EnterpriseLicenseManager.log(this.mContextInfo, "LockscreenOverlay.configure(LSOImage[])");
        if (lSOImageArr != null && lSOImageArr.length != 0) {
            int length = lSOImageArr.length - 1;
            LSOImage lSOImage2 = lSOImageArr[0];
            if (lSOImage2 != null && lSOImage2.topPosition >= 0 && (i = (lSOImage = lSOImageArr[length]).bottomPosition) <= 100 && i > lSOImage.topPosition) {
                int i3 = 0;
                while (i3 < length) {
                    LSOImage lSOImage3 = lSOImageArr[i3];
                    if (lSOImage3 != null && (i2 = lSOImage3.bottomPosition) > lSOImage3.topPosition) {
                        i3++;
                        if (i2 <= lSOImageArr[i3].topPosition) {
                        }
                    }
                    throw new InvalidParameterException("Invalid argument list - Item[i] top > bottom or Item[i+1] top < Item[i] bottom");
                }
                LSOItemContainer lSOItemContainer = new LSOItemContainer();
                lSOItemContainer.setDimension(-1, -1);
                int i4 = 0;
                for (int i5 = 0; i5 < lSOImageArr.length; i5++) {
                    if (i4 < lSOImageArr[i5].topPosition) {
                        lSOItemContainer.addItem(new LSOItemSpace(-1, 0, lSOImageArr[i5].topPosition - i4));
                    }
                    String copyFileToDataLocalDirectory = LSOUtils.copyFileToDataLocalDirectory(this.mContext, lSOImageArr[i5].filePath, "lso" + i5);
                    if (copyFileToDataLocalDirectory == null) {
                        Log.e(TAG, "Failed to copy images");
                        return -4;
                    }
                    LSOItemImage lSOItemImage = new LSOItemImage(copyFileToDataLocalDirectory);
                    LSOImage lSOImage4 = lSOImageArr[i5];
                    lSOItemImage.setDimension(-1, 0, lSOImage4.bottomPosition - lSOImage4.topPosition);
                    lSOItemImage.setScaleType(lSOImageArr[i5].scaleType);
                    lSOItemContainer.addItem(lSOItemImage);
                    i4 = lSOImageArr[i5].bottomPosition;
                }
                if (i4 < 100) {
                    lSOItemContainer.addItem(new LSOItemSpace(-1, 0, 100 - i4));
                }
                return setData(lSOItemContainer);
            }
            throw new InvalidParameterException("Invalid argument list - Item[0] top position is less than 0, Item[last_index] is greater than 100, or position of Item[0] > Item[last_index]");
        }
        throw new InvalidParameterException("Invalid argument list - List is empty");
    }

    public static LSOItemData createLSOItem_EmergencyPhone(Context context, LSOEmergencyPhoneInfo lSOEmergencyPhoneInfo) {
        LSOItemContainer lSOItemContainer = new LSOItemContainer();
        if (lSOEmergencyPhoneInfo.topPosition > 0) {
            lSOItemContainer.addItem(new LSOItemSpace(-1, 0, lSOEmergencyPhoneInfo.topPosition));
        }
        LSOItemWidget lSOItemWidget = new LSOItemWidget();
        lSOItemWidget.setWidget("com.samsung.android.knox.lockscreen.EmergencyPhoneWidget");
        lSOItemWidget.setAttribute(LSOAttrConst.EPW_ATTR_PHONE_NUMBER, lSOEmergencyPhoneInfo.phoneNumber);
        String str = lSOEmergencyPhoneInfo.text;
        if (str != null && str.length() > 0) {
            lSOItemWidget.setAttribute(LSOAttrConst.ATTR_TEXT, lSOEmergencyPhoneInfo.text);
            lSOItemWidget.setAttribute(LSOAttrConst.ATTR_MAX_LINES, (Integer) 3);
        } else {
            lSOItemWidget.setAttribute(LSOAttrConst.EPW_ATTR_SHOW_DEFAULT_TEXT, Boolean.valueOf(lSOEmergencyPhoneInfo.showDefaultText));
        }
        String str2 = lSOEmergencyPhoneInfo.icon;
        if (str2 != null && str2.length() > 0) {
            lSOItemWidget.setAttribute(LSOAttrConst.ATTR_IMAGE_SRC, lSOEmergencyPhoneInfo.icon);
        }
        lSOItemWidget.setAttribute(LSOAttrConst.ATTR_ORIENTATION, Boolean.TRUE);
        lSOItemWidget.setAttribute(LSOAttrConst.EPW_ATTR_SHOW_BG, Boolean.valueOf(lSOEmergencyPhoneInfo.showBackground));
        lSOItemWidget.setAttribute(LSOAttrConst.ATTR_TOP_POSITION, Integer.valueOf(lSOEmergencyPhoneInfo.topPosition));
        lSOItemWidget.setAttribute(LSOAttrConst.ATTR_BOTTOM_POSITION, Integer.valueOf(lSOEmergencyPhoneInfo.bottomPosition));
        lSOItemWidget.setGravity(lSOEmergencyPhoneInfo.gravity);
        lSOItemWidget.setDimension(-1, 0);
        lSOItemWidget.setWeight(lSOEmergencyPhoneInfo.bottomPosition - lSOEmergencyPhoneInfo.topPosition);
        lSOItemContainer.addItem(lSOItemWidget);
        if (lSOEmergencyPhoneInfo.bottomPosition < 100) {
            lSOItemContainer.addItem(new LSOItemSpace(-1, 0, 100 - lSOEmergencyPhoneInfo.bottomPosition));
        }
        lSOItemContainer.setAttribute(LSOAttrConst.ATTR_ALPHA, Float.valueOf(1.0f));
        lSOItemContainer.setGravity(51);
        return lSOItemContainer;
    }
}
