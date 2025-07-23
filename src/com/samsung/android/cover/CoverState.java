package com.samsung.android.cover;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.internal.R;

/* loaded from: classes6.dex */
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
    public static final Parcelable.Creator<CoverState> CREATOR = new Parcelable.Creator<CoverState>() { // from class: com.samsung.android.cover.CoverState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoverState createFromParcel(Parcel parcel) {
            return new CoverState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CoverState[] newArray(int i) {
            return new CoverState[i];
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

    @Override // android.os.Parcelable
    @Deprecated
    public int describeContents() {
        return 0;
    }

    public CoverState() {
        this.mVisibleRect = new Rect();
        this.switchState = true;
        this.type = 2;
        this.color = 0;
        this.widthPixel = 0;
        this.heightPixel = 0;
        this.attached = false;
        this.model = 0;
        this.smartCoverCookie = null;
        this.serialNumber = null;
        this.fakeCover = false;
        this.fotaMode = 0;
    }

    public CoverState(int i, int i2, int i3) {
        this.mVisibleRect = new Rect();
        this.switchState = true;
        this.type = i;
        this.color = 0;
        this.widthPixel = i2;
        this.heightPixel = i3;
        this.attached = false;
        this.model = 0;
        updateVisibleRect(i);
    }

    public CoverState(boolean z, int i, int i2, int i3, int i4) {
        this.mVisibleRect = new Rect();
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = false;
        this.model = 0;
        updateVisibleRect(i);
    }

    public CoverState(boolean z, int i, int i2, int i3, int i4, boolean z2) {
        this.mVisibleRect = new Rect();
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = z2;
        this.model = 0;
        updateVisibleRect(i);
    }

    public CoverState(boolean z, int i, int i2, int i3, int i4, boolean z2, int i5) {
        this.mVisibleRect = new Rect();
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = z2;
        this.model = i5;
        updateVisibleRect(i);
    }

    public CoverState(boolean z, int i, int i2, boolean z2, int i3, String str, byte[] bArr, String str2, boolean z3) {
        this.mVisibleRect = new Rect();
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.attached = z2;
        this.model = i3;
        this.smartCoverAppUri = str;
        this.smartCoverCookie = bArr;
        this.serialNumber = str2;
        this.fakeCover = z3;
        updateVisibleRect(i);
    }

    public CoverState(Parcel parcel) {
        this.mVisibleRect = new Rect();
        readFromParcel(parcel);
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

    private void readFromParcel(Parcel parcel) {
        this.switchState = parcel.readInt() == 1;
        this.type = parcel.readInt();
        this.color = parcel.readInt();
        this.widthPixel = parcel.readInt();
        this.heightPixel = parcel.readInt();
        this.attached = parcel.readInt() == 1;
        this.model = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.smartCoverAppUri = parcel.readString();
        }
        if (parcel.readInt() == 1) {
            byte[] bArr = new byte[parcel.readInt()];
            this.smartCoverCookie = bArr;
            parcel.readByteArray(bArr);
        }
        if (parcel.readInt() == 1) {
            this.serialNumber = parcel.readString();
        }
        this.fakeCover = parcel.readInt() == 1;
        this.fotaMode = parcel.readInt();
        this.friendsType = parcel.readInt();
        this.mVisibleRect = (Rect) parcel.readParcelable(Rect.class.getClassLoader());
    }

    public String toString() {
        return String.format("CoverState(switchState=%b type=%d color=%d widthPixel=%d heightPixel=%d model=%d attached=%b fotaMode=%d friendsType=%d VisibleRect=%s)", Boolean.valueOf(this.switchState), Integer.valueOf(this.type), Integer.valueOf(this.color), Integer.valueOf(this.widthPixel), Integer.valueOf(this.heightPixel), Integer.valueOf(this.model), Boolean.valueOf(this.attached), Integer.valueOf(this.fotaMode), Integer.valueOf(this.friendsType), this.mVisibleRect);
    }

    public void updateCoverState(int i, int i2, int i3, int i4) {
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        updateVisibleRect(i);
    }

    public void updateCoverState(boolean z, int i, int i2, int i3, int i4) {
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        updateVisibleRect(i);
    }

    public void updateCoverState(boolean z, int i, int i2, int i3, int i4, boolean z2) {
        this.switchState = z;
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = z2;
        updateVisibleRect(i);
    }

    public void updateCoverState(int i, int i2, int i3, int i4, int i5) {
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.model = i5;
        updateVisibleRect(i);
    }

    public void updateCoverState(int i, int i2, int i3, int i4, boolean z, int i5) {
        this.type = i;
        this.color = i2;
        this.widthPixel = i3;
        this.heightPixel = i4;
        this.attached = z;
        this.model = i5;
        updateVisibleRect(i);
    }

    public void updateCoverWindowSize(int i, int i2) {
        this.widthPixel = i;
        this.heightPixel = i2;
    }

    public void copyFrom(CoverState coverState) {
        this.switchState = coverState.switchState;
        this.type = coverState.type;
        this.color = coverState.color;
        this.widthPixel = coverState.widthPixel;
        this.heightPixel = coverState.heightPixel;
        this.attached = coverState.attached;
        this.model = coverState.model;
        this.smartCoverAppUri = coverState.smartCoverAppUri;
        this.smartCoverCookie = coverState.smartCoverCookie;
        this.fakeCover = coverState.fakeCover;
        this.serialNumber = coverState.serialNumber;
        this.fotaMode = coverState.fotaMode;
        this.friendsType = coverState.friendsType;
        this.mVisibleRect = new Rect(coverState.mVisibleRect);
    }

    public boolean getSwitchState() {
        return this.switchState;
    }

    public void setSwitchState(boolean z) {
        this.switchState = z;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
        updateVisibleRect(i);
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public int getWindowWidth() {
        return this.widthPixel;
    }

    public void setWindowWidth(int i) {
        this.widthPixel = i;
    }

    public int getWindowHeight() {
        return this.heightPixel;
    }

    public void setWindowHeight(int i) {
        this.heightPixel = i;
    }

    public boolean getAttachState() {
        return this.attached;
    }

    public void setAttachState(boolean z) {
        this.attached = z;
    }

    public int getModel() {
        return this.model;
    }

    public void setModel(int i) {
        this.model = i;
    }

    public byte[] getSmartCoverCookie() {
        return this.smartCoverCookie;
    }

    public void setSmartCoverCookie(byte[] bArr) {
        this.smartCoverCookie = bArr;
    }

    public String getSmartCoverAppUri() {
        return this.smartCoverAppUri;
    }

    public void setSmartCoverAppUri(String str) {
        this.smartCoverAppUri = str;
    }

    public boolean isFakeCover() {
        return this.fakeCover;
    }

    public void setFakeCover(boolean z) {
        this.fakeCover = z;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String str) {
        this.serialNumber = str;
    }

    public int getFotaMode() {
        return this.fotaMode;
    }

    public void setFotaMode(int i) {
        this.fotaMode = i;
    }

    public int getFriendsType() {
        return this.friendsType;
    }

    public void setFriendsType(int i) {
        this.friendsType = i;
    }

    public Rect getVisibleRect() {
        return this.mVisibleRect;
    }

    public void setVisibleRect(Rect rect) {
        this.mVisibleRect = new Rect(rect);
    }

    public void updateVisibleRect(int i) {
        this.mVisibleRect.setEmpty();
        Resources system = Resources.getSystem();
        if (system != null) {
            if (i == 15 || i == 16 || i == 17) {
                this.mVisibleRect.left = system.getDimensionPixelSize(R.dimen.cover_clear_view_left);
                this.mVisibleRect.top = system.getDimensionPixelSize(R.dimen.cover_clear_view_top);
                this.mVisibleRect.right = system.getDimensionPixelSize(R.dimen.cover_clear_view_right);
                this.mVisibleRect.bottom = system.getDimensionPixelSize(R.dimen.cover_clear_view_bottom);
            }
            float defaultDensity = getDefaultDensity(system);
            float f = system.getDisplayMetrics().density;
            float f2 = defaultDensity / f;
            this.mVisibleRect.left = (int) (r3.left * f2);
            this.mVisibleRect.top = (int) (r3.top * f2);
            this.mVisibleRect.right = (int) (r3.right * f2);
            this.mVisibleRect.bottom = (int) (r3.bottom * f2);
            Log.d(TAG, "updateVisibility type= " + i + " defaultDensity= " + defaultDensity + " currentDensity= " + f + " ratio= " + f2 + " mVisibleRect left= " + this.mVisibleRect.left + " mVisibleRect top= " + this.mVisibleRect.top + " mVisibleRect right= " + this.mVisibleRect.right + " mVisibleRect bottom= " + this.mVisibleRect.bottom);
        }
    }

    public float getDefaultDensity(Resources resources) {
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int i = displayMetrics.widthPixels < displayMetrics.heightPixels ? displayMetrics.widthPixels : displayMetrics.heightPixels;
        if (i >= 1440) {
            return 4.0f;
        }
        return (i <= 720 || i > 1080) ? 2.0f : 3.0f;
    }
}
