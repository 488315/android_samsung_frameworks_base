package com.samsung.android.cover;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.internal.R;

public class CoverState implements Parcelable {
    public static final int COLOR_BLACK = 1;
    public static final int COLOR_BLUE = 5;
    public static final int COLOR_BLUSH_PINK = 8;
    public static final int COLOR_BRONZE = 14;
    public static final int COLOR_CARBON_METAL = 6;
    public static final int COLOR_CHARCOAL = 10;
    public static final int COLOR_CHARCOAL_GRAY = 10;
    public static final int COLOR_CLASSIC_WHITE = 2;
    public static final int COLOR_DEFAULT = 0;
    public static final int COLOR_GOLD = 7;
    public static final int COLOR_GRAYISH_BLUE = 9;
    public static final int COLOR_GREEN = 11;
    public static final int COLOR_INDIGO_BLUE = 5;
    public static final int COLOR_JET_BLACK = 1;
    public static final int COLOR_MAGENTA = 3;
    public static final int COLOR_MINT = 9;
    public static final int COLOR_MINT_BLUE = 9;
    public static final int COLOR_MUSTARD_YELLOW = 12;
    public static final int COLOR_NAVY = 4;
    public static final int COLOR_NFC_SMART_COVER = 255;
    public static final int COLOR_OATMEAL = 12;
    public static final int COLOR_OATMEAL_BEIGE = 12;
    public static final int COLOR_ORANGE = 13;
    public static final int COLOR_PEAKCOCK_GREEN = 11;
    public static final int COLOR_PEARL_WHITE = 2;
    public static final int COLOR_PINK = 8;
    public static final int COLOR_PLUM = 3;
    public static final int COLOR_PLUM_RED = 3;
    public static final int COLOR_ROSE_GOLD = 7;
    public static final int COLOR_SILVER = 6;
    public static final int COLOR_SOFT_PINK = 8;
    public static final int COLOR_WHITE = 2;
    public static final int COLOR_WILD_ORANGE = 13;
    public static final int COLOR_YELLOW = 12;
    public static final boolean COVER_ATTACHED = true;
    public static final boolean COVER_DETACHED = false;
    public static final Parcelable.Creator<CoverState> CREATOR =
            new Parcelable.Creator<
                    CoverState>() { // from class: com.samsung.android.cover.CoverState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CoverState createFromParcel(Parcel parcel) {
                    return new CoverState(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CoverState[] newArray(int size) {
                    return new CoverState[size];
                }
            };
    public static final int FOTA_MODE_NONE = 0;
    public static final int FRIENDS_TYPE_BACK_COVER = 1;
    public static final int FRIENDS_TYPE_FLIP_COVER = 2;
    public static final int FRIENDS_TYPE_NONE = 0;
    public static final int MODEL_DEFAULT = 0;
    public static final int MODEL_TB = 3;
    public static final int MODEL_TR = 2;
    public static final boolean SWITCH_STATE_COVER_CLOSE = false;
    public static final boolean SWITCH_STATE_COVER_OPEN = true;
    private static final String TAG = "CoverState";
    public static final int TYPE_ALCANTARA_COVER = 12;
    public static final int TYPE_BRAND_MONBLANC_COVER = 100;
    public static final int TYPE_CLEAR_CAMERA_VIEW_COVER = 17;
    public static final int TYPE_CLEAR_COVER = 8;
    public static final int TYPE_CLEAR_SIDE_VIEW_COVER = 15;
    public static final int TYPE_FLIP_COVER = 0;
    public static final int TYPE_GAMEPACK_COVER = 13;
    public static final int TYPE_HEALTH_COVER = 4;
    public static final int TYPE_KEYBOARD_KOR_COVER = 9;
    public static final int TYPE_KEYBOARD_US_COVER = 10;
    public static final int TYPE_LED_BACK_COVER = 14;
    public static final int TYPE_LED_COVER = 7;
    public static final int TYPE_MINI_SVIEW_WALLET_COVER = 16;
    public static final int TYPE_NEON_COVER = 11;
    public static final int TYPE_NFC_SMART_COVER = 255;
    public static final int TYPE_NONE = 2;
    public static final int TYPE_PALETTE_COVER = 18;
    public static final int TYPE_SVIEW_CHARGER_COVER = 3;
    public static final int TYPE_SVIEW_COVER = 1;
    public static final int TYPE_S_CHARGER_COVER = 5;
    public static final int TYPE_S_VIEW_WALLET_COVER = 6;
    public boolean attached;
    public int color;
    public boolean fakeCover;
    public int fotaMode;
    public int friendsType;
    public int heightPixel;
    private Rect mVisibleRect;
    public int model;
    public String serialNumber;
    public String smartCoverAppUri;
    public byte[] smartCoverCookie;
    public boolean switchState;
    public int type;
    public int widthPixel;

    public CoverState() {
        this.mVisibleRect = new Rect();
        this.switchState = true;
        this.type = 2;
        this.color = 0;
        this.widthPixel = 0;
        this.heightPixel = 0;
        this.attached = false;
        this.model = 0;
        this.serialNumber = null;
        this.smartCoverCookie = null;
        this.serialNumber = null;
        this.fakeCover = false;
        this.fotaMode = 0;
    }

    public CoverState(int defaultType, int defaultWidthPixel, int defaultHeightPixel) {
        this.mVisibleRect = new Rect();
        this.switchState = true;
        this.type = defaultType;
        this.color = 0;
        this.widthPixel = defaultWidthPixel;
        this.heightPixel = defaultHeightPixel;
        this.attached = false;
        this.model = 0;
        updateVisibleRect(this.type);
    }

    public CoverState(boolean switchState, int type, int color, int widthPixel, int heightPixel) {
        this.mVisibleRect = new Rect();
        this.switchState = switchState;
        this.type = type;
        this.color = color;
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        this.attached = false;
        this.model = 0;
        updateVisibleRect(type);
    }

    public CoverState(
            boolean switchState,
            int type,
            int color,
            int widthPixel,
            int heightPixel,
            boolean attached) {
        this.mVisibleRect = new Rect();
        this.switchState = switchState;
        this.type = type;
        this.color = color;
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        this.attached = attached;
        this.model = 0;
        updateVisibleRect(type);
    }

    public CoverState(
            boolean switchState,
            int type,
            int color,
            int widthPixel,
            int heightPixel,
            boolean attached,
            int model) {
        this.mVisibleRect = new Rect();
        this.switchState = switchState;
        this.type = type;
        this.color = color;
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        this.attached = attached;
        this.model = model;
        updateVisibleRect(type);
    }

    public CoverState(
            boolean switchState,
            int type,
            int color,
            boolean attached,
            int model,
            String installUri,
            byte[] vendorData,
            String serialNumber,
            boolean isFake) {
        this.mVisibleRect = new Rect();
        this.switchState = switchState;
        this.type = type;
        this.color = color;
        this.attached = attached;
        this.model = model;
        this.smartCoverAppUri = installUri;
        this.smartCoverCookie = vendorData;
        this.serialNumber = serialNumber;
        this.fakeCover = isFake;
        updateVisibleRect(type);
    }

    public CoverState(Parcel src) {
        this.mVisibleRect = new Rect();
        readFromParcel(src);
    }

    @Override // android.os.Parcelable
    @Deprecated
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    @Deprecated
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.switchState ? 1 : 0);
        parcel.writeInt(this.type);
        parcel.writeInt(this.color);
        parcel.writeInt(this.widthPixel);
        parcel.writeInt(this.heightPixel);
        parcel.writeInt(this.attached ? 1 : 0);
        parcel.writeInt(this.model);
        if (this.smartCoverAppUri == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(this.smartCoverAppUri);
        }
        if (this.smartCoverCookie == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(this.smartCoverCookie.length);
            parcel.writeByteArray(this.smartCoverCookie);
        }
        if (this.serialNumber == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(this.serialNumber);
        }
        parcel.writeInt(this.fakeCover ? 1 : 0);
        parcel.writeInt(this.fotaMode);
        parcel.writeInt(this.friendsType);
        parcel.writeParcelable(this.mVisibleRect, 0);
    }

    private void readFromParcel(Parcel src) {
        this.switchState = src.readInt() == 1;
        this.type = src.readInt();
        this.color = src.readInt();
        this.widthPixel = src.readInt();
        this.heightPixel = src.readInt();
        this.attached = src.readInt() == 1;
        this.model = src.readInt();
        if (src.readInt() == 1) {
            this.smartCoverAppUri = src.readString();
        }
        if (src.readInt() == 1) {
            int smartCoverCookieLength = src.readInt();
            this.smartCoverCookie = new byte[smartCoverCookieLength];
            src.readByteArray(this.smartCoverCookie);
        }
        int smartCoverCookieLength2 = src.readInt();
        if (smartCoverCookieLength2 == 1) {
            this.serialNumber = src.readString();
        }
        this.fakeCover = src.readInt() == 1;
        this.fotaMode = src.readInt();
        this.friendsType = src.readInt();
        this.mVisibleRect = (Rect) src.readParcelable(Rect.class.getClassLoader());
    }

    public String toString() {
        return String.format(
                "CoverState(switchState=%b type=%d color=%d widthPixel=%d heightPixel=%d model=%d"
                    + " attached=%b fotaMode=%d friendsType=%d VisibleRect=%s)",
                Boolean.valueOf(this.switchState),
                Integer.valueOf(this.type),
                Integer.valueOf(this.color),
                Integer.valueOf(this.widthPixel),
                Integer.valueOf(this.heightPixel),
                Integer.valueOf(this.model),
                Boolean.valueOf(this.attached),
                Integer.valueOf(this.fotaMode),
                Integer.valueOf(this.friendsType),
                this.mVisibleRect);
    }

    public void updateCoverState(int type, int color, int widthPixel, int heightPixel) {
        this.type = type;
        this.color = color;
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        updateVisibleRect(type);
    }

    public void updateCoverState(
            boolean switchState, int type, int color, int widthPixel, int heightPixel) {
        this.switchState = switchState;
        this.type = type;
        this.color = color;
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        updateVisibleRect(type);
    }

    public void updateCoverState(
            boolean switchState,
            int type,
            int color,
            int widthPixel,
            int heightPixel,
            boolean attached) {
        this.switchState = switchState;
        this.type = type;
        this.color = color;
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        this.attached = attached;
        updateVisibleRect(type);
    }

    public void updateCoverState(int type, int color, int widthPixel, int heightPixel, int model) {
        this.type = type;
        this.color = color;
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        this.model = model;
        updateVisibleRect(type);
    }

    public void updateCoverState(
            int type, int color, int widthPixel, int heightPixel, boolean attached, int model) {
        this.type = type;
        this.color = color;
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
        this.attached = attached;
        this.model = model;
        updateVisibleRect(type);
    }

    public void updateCoverWindowSize(int widthPixel, int heightPixel) {
        this.widthPixel = widthPixel;
        this.heightPixel = heightPixel;
    }

    public void copyFrom(CoverState o) {
        this.switchState = o.switchState;
        this.type = o.type;
        this.color = o.color;
        this.widthPixel = o.widthPixel;
        this.heightPixel = o.heightPixel;
        this.attached = o.attached;
        this.model = o.model;
        this.smartCoverAppUri = o.smartCoverAppUri;
        this.smartCoverCookie = o.smartCoverCookie;
        this.fakeCover = o.fakeCover;
        this.serialNumber = o.serialNumber;
        this.fotaMode = o.fotaMode;
        this.friendsType = o.friendsType;
        this.mVisibleRect = new Rect(o.mVisibleRect);
    }

    public boolean getSwitchState() {
        return this.switchState;
    }

    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
        updateVisibleRect(type);
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getWindowWidth() {
        return this.widthPixel;
    }

    public void setWindowWidth(int width) {
        this.widthPixel = width;
    }

    public int getWindowHeight() {
        return this.heightPixel;
    }

    public void setWindowHeight(int height) {
        this.heightPixel = height;
    }

    public boolean getAttachState() {
        return this.attached;
    }

    public void setAttachState(boolean attached) {
        this.attached = attached;
    }

    public int getModel() {
        return this.model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public byte[] getSmartCoverCookie() {
        return this.smartCoverCookie;
    }

    public void setSmartCoverCookie(byte[] extraData) {
        this.smartCoverCookie = extraData;
    }

    public String getSmartCoverAppUri() {
        return this.smartCoverAppUri;
    }

    public void setSmartCoverAppUri(String uri) {
        this.smartCoverAppUri = uri;
    }

    public boolean isFakeCover() {
        return this.fakeCover;
    }

    public void setFakeCover(boolean faked) {
        this.fakeCover = faked;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serial) {
        this.serialNumber = serial;
    }

    public int getFotaMode() {
        return this.fotaMode;
    }

    public void setFotaMode(int fotaMode) {
        this.fotaMode = fotaMode;
    }

    public int getFriendsType() {
        return this.friendsType;
    }

    public void setFriendsType(int friendsType) {
        this.friendsType = friendsType;
    }

    public Rect getVisibleRect() {
        return this.mVisibleRect;
    }

    public void setVisibleRect(Rect rect) {
        this.mVisibleRect = new Rect(rect);
    }

    public void updateVisibleRect(int type) {
        this.mVisibleRect.setEmpty();
        Resources resource = Resources.getSystem();
        if (resource != null) {
            if (type == 15 || type == 16 || type == 17) {
                this.mVisibleRect.left =
                        resource.getDimensionPixelSize(R.dimen.cover_clear_view_left);
                this.mVisibleRect.top =
                        resource.getDimensionPixelSize(R.dimen.cover_clear_view_top);
                this.mVisibleRect.right =
                        resource.getDimensionPixelSize(R.dimen.cover_clear_view_right);
                this.mVisibleRect.bottom =
                        resource.getDimensionPixelSize(R.dimen.cover_clear_view_bottom);
            }
            float defaultDensity = getDefaultDensity(resource);
            float currentDensity = resource.getDisplayMetrics().density;
            float ratio = defaultDensity / currentDensity;
            this.mVisibleRect.left = (int) (r4.left * ratio);
            this.mVisibleRect.top = (int) (r4.top * ratio);
            this.mVisibleRect.right = (int) (r4.right * ratio);
            this.mVisibleRect.bottom = (int) (r4.bottom * ratio);
            Log.d(
                    TAG,
                    "updateVisibility type= "
                            + type
                            + " defaultDensity= "
                            + defaultDensity
                            + " currentDensity= "
                            + currentDensity
                            + " ratio= "
                            + ratio
                            + " mVisibleRect left= "
                            + this.mVisibleRect.left
                            + " mVisibleRect top= "
                            + this.mVisibleRect.top
                            + " mVisibleRect right= "
                            + this.mVisibleRect.right
                            + " mVisibleRect bottom= "
                            + this.mVisibleRect.bottom);
        }
    }

    public float getDefaultDensity(Resources resource) {
        DisplayMetrics displayMetrics = resource.getDisplayMetrics();
        int width =
                displayMetrics.widthPixels < displayMetrics.heightPixels
                        ? displayMetrics.widthPixels
                        : displayMetrics.heightPixels;
        if (width >= 1440) {
            return 4.0f;
        }
        if (width > 720 && width <= 1080) {
            return 3.0f;
        }
        return 2.0f;
    }
}
