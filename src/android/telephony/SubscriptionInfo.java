package android.telephony;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.telephony.util.TelephonyUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public class SubscriptionInfo implements Parcelable {
    public static final Parcelable.Creator<SubscriptionInfo> CREATOR = new Parcelable.Creator<SubscriptionInfo>() { // from class: android.telephony.SubscriptionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubscriptionInfo createFromParcel(Parcel parcel) {
            return new Builder().setId(parcel.readInt()).setIccId(parcel.readString()).setSimSlotIndex(parcel.readInt()).setDisplayName(TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)).setCarrierName(TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)).setDisplayNameSource(parcel.readInt()).setIconTint(parcel.readInt()).setNumber(parcel.readString()).setDataRoaming(parcel.readInt()).setMcc(parcel.readString()).setMnc(parcel.readString()).setCountryIso(parcel.readString()).setEmbedded(parcel.readBoolean()).setNativeAccessRules((UiccAccessRule[]) parcel.createTypedArray(UiccAccessRule.CREATOR)).setCardString(parcel.readString()).setCardId(parcel.readInt()).setPortIndex(parcel.readInt()).setOpportunistic(parcel.readBoolean()).setGroupUuid(parcel.readString8()).setGroupDisabled(parcel.readBoolean()).setCarrierId(parcel.readInt()).setProfileClass(parcel.readInt()).setType(parcel.readInt()).setEhplmns(parcel.createStringArray()).setHplmns(parcel.createStringArray()).setGroupOwner(parcel.readString()).setCarrierConfigAccessRules((UiccAccessRule[]) parcel.createTypedArray(UiccAccessRule.CREATOR)).setUiccApplicationsEnabled(parcel.readBoolean()).setUsageSetting(parcel.readInt()).setOnlyNonTerrestrialNetwork(parcel.readBoolean()).setServiceCapabilities(SubscriptionManager.getServiceCapabilitiesSet(parcel.readInt())).setTransferStatus(parcel.readInt()).setSatelliteESOSSupported(parcel.readBoolean()).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SubscriptionInfo[] newArray(int i) {
            return new SubscriptionInfo[i];
        }
    };
    private static final int TEXT_SIZE = 16;
    private final boolean mAreUiccApplicationsEnabled;
    private final int mCardId;
    private final String mCardString;
    private final UiccAccessRule[] mCarrierConfigAccessRules;
    private final int mCarrierId;
    private final CharSequence mCarrierName;
    private final String mCountryIso;
    private final int mDataRoaming;
    private final CharSequence mDisplayName;
    private final int mDisplayNameSource;
    private final String[] mEhplmns;
    private final String mGroupOwner;
    private final ParcelUuid mGroupUuid;
    private final String[] mHplmns;
    private final String mIccId;
    private Bitmap mIconBitmap;
    private final int mIconTint;
    private final int mId;
    private final boolean mIsEmbedded;
    private final boolean mIsGroupDisabled;
    private final boolean mIsOnlyNonTerrestrialNetwork;
    private final boolean mIsOpportunistic;
    private final boolean mIsSatelliteESOSSupported;
    private final String mMcc;
    private final String mMnc;
    private final UiccAccessRule[] mNativeAccessRules;
    private final String mNumber;
    private final int mPortIndex;
    private final int mProfileClass;
    private final int mServiceCapabilities;
    private final int mSimSlotIndex;
    private final int mTransferStatus;
    private final int mType;
    private final int mUsageSetting;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public SubscriptionInfo(int i, String str, int i2, CharSequence charSequence, CharSequence charSequence2, int i3, int i4, String str2, int i5, Bitmap bitmap, String str3, String str4, String str5, boolean z, UiccAccessRule[] uiccAccessRuleArr, String str6) {
        this(i, str, i2, charSequence, charSequence2, i3, i4, str2, i5, bitmap, str3, str4, str5, z, uiccAccessRuleArr, str6, -1, false, null, false, -1, -1, 0, null, null, true);
    }

    @Deprecated
    public SubscriptionInfo(int i, String str, int i2, CharSequence charSequence, CharSequence charSequence2, int i3, int i4, String str2, int i5, Bitmap bitmap, String str3, String str4, String str5, boolean z, UiccAccessRule[] uiccAccessRuleArr, String str6, boolean z2, String str7, int i6, int i7) {
        this(i, str, i2, charSequence, charSequence2, i3, i4, str2, i5, bitmap, str3, str4, str5, z, uiccAccessRuleArr, str6, -1, z2, str7, false, i6, i7, 0, null, null, true);
    }

    @Deprecated
    public SubscriptionInfo(int i, String str, int i2, CharSequence charSequence, CharSequence charSequence2, int i3, int i4, String str2, int i5, Bitmap bitmap, String str3, String str4, String str5, boolean z, UiccAccessRule[] uiccAccessRuleArr, String str6, int i6, boolean z2, String str7, boolean z3, int i7, int i8, int i9, String str8, UiccAccessRule[] uiccAccessRuleArr2, boolean z4) {
        this(i, str, i2, charSequence, charSequence2, i3, i4, str2, i5, bitmap, str3, str4, str5, z, uiccAccessRuleArr, str6, i6, z2, str7, z3, i7, i8, i9, str8, uiccAccessRuleArr2, z4, 0);
    }

    @Deprecated
    public SubscriptionInfo(int i, String str, int i2, CharSequence charSequence, CharSequence charSequence2, int i3, int i4, String str2, int i5, Bitmap bitmap, String str3, String str4, String str5, boolean z, UiccAccessRule[] uiccAccessRuleArr, String str6, int i6, boolean z2, String str7, boolean z3, int i7, int i8, int i9, String str8, UiccAccessRule[] uiccAccessRuleArr2, boolean z4, int i10) {
        this(i, str, i2, charSequence, charSequence2, i3, i4, str2, i5, bitmap, str3, str4, str5, z, uiccAccessRuleArr, str6, i6, z2, str7, z3, i7, i8, i9, str8, uiccAccessRuleArr2, z4, i10, 0);
    }

    @Deprecated
    public SubscriptionInfo(int i, String str, int i2, CharSequence charSequence, CharSequence charSequence2, int i3, int i4, String str2, int i5, Bitmap bitmap, String str3, String str4, String str5, boolean z, UiccAccessRule[] uiccAccessRuleArr, String str6, int i6, boolean z2, String str7, boolean z3, int i7, int i8, int i9, String str8, UiccAccessRule[] uiccAccessRuleArr2, boolean z4, int i10, int i11) {
        this.mId = i;
        this.mIccId = str;
        this.mSimSlotIndex = i2;
        this.mDisplayName = charSequence;
        this.mCarrierName = charSequence2;
        this.mDisplayNameSource = i3;
        this.mIconTint = i4;
        this.mNumber = str2;
        this.mDataRoaming = i5;
        this.mIconBitmap = bitmap;
        this.mMcc = TextUtils.emptyIfNull(str3);
        this.mMnc = TextUtils.emptyIfNull(str4);
        this.mHplmns = null;
        this.mEhplmns = null;
        this.mCountryIso = TextUtils.emptyIfNull(str5);
        this.mIsEmbedded = z;
        this.mNativeAccessRules = uiccAccessRuleArr;
        this.mCardString = TextUtils.emptyIfNull(str6);
        this.mCardId = i6;
        this.mIsOpportunistic = z2;
        this.mGroupUuid = str7 != null ? ParcelUuid.fromString(str7) : null;
        this.mIsGroupDisabled = z3;
        this.mCarrierId = i7;
        this.mProfileClass = i8;
        this.mType = i9;
        this.mGroupOwner = TextUtils.emptyIfNull(str8);
        this.mCarrierConfigAccessRules = uiccAccessRuleArr2;
        this.mAreUiccApplicationsEnabled = z4;
        this.mPortIndex = i10;
        this.mUsageSetting = i11;
        this.mIsOnlyNonTerrestrialNetwork = false;
        this.mServiceCapabilities = 0;
        this.mTransferStatus = 0;
        this.mIsSatelliteESOSSupported = false;
    }

    private SubscriptionInfo(Builder builder) {
        this.mId = builder.mId;
        this.mIccId = builder.mIccId;
        this.mSimSlotIndex = builder.mSimSlotIndex;
        this.mDisplayName = builder.mDisplayName;
        this.mCarrierName = builder.mCarrierName;
        this.mDisplayNameSource = builder.mDisplayNameSource;
        this.mIconTint = builder.mIconTint;
        this.mNumber = builder.mNumber;
        this.mDataRoaming = builder.mDataRoaming;
        this.mIconBitmap = builder.mIconBitmap;
        this.mMcc = builder.mMcc;
        this.mMnc = builder.mMnc;
        this.mEhplmns = builder.mEhplmns;
        this.mHplmns = builder.mHplmns;
        this.mCountryIso = builder.mCountryIso;
        this.mIsEmbedded = builder.mIsEmbedded;
        this.mNativeAccessRules = builder.mNativeAccessRules;
        this.mCardString = builder.mCardString;
        this.mCardId = builder.mCardId;
        this.mIsOpportunistic = builder.mIsOpportunistic;
        this.mGroupUuid = builder.mGroupUuid;
        this.mIsGroupDisabled = builder.mIsGroupDisabled;
        this.mCarrierId = builder.mCarrierId;
        this.mProfileClass = builder.mProfileClass;
        this.mType = builder.mType;
        this.mGroupOwner = builder.mGroupOwner;
        this.mCarrierConfigAccessRules = builder.mCarrierConfigAccessRules;
        this.mAreUiccApplicationsEnabled = builder.mAreUiccApplicationsEnabled;
        this.mPortIndex = builder.mPortIndex;
        this.mUsageSetting = builder.mUsageSetting;
        this.mIsOnlyNonTerrestrialNetwork = builder.mIsOnlyNonTerrestrialNetwork;
        this.mServiceCapabilities = builder.mServiceCapabilities;
        this.mTransferStatus = builder.mTransferStatus;
        this.mIsSatelliteESOSSupported = builder.mIsSatelliteESOSSupported;
    }

    public int getSubscriptionId() {
        return this.mId;
    }

    public String getIccId() {
        return this.mIccId;
    }

    public int getSimSlotIndex() {
        return this.mSimSlotIndex;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public CharSequence getCarrierName() {
        return this.mCarrierName;
    }

    public int getDisplayNameSource() {
        return this.mDisplayNameSource;
    }

    public Bitmap createIconBitmap(Context context) {
        if (this.mIconBitmap == null) {
            this.mIconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_sim_card_multi_24px_clr);
        }
        int width = this.mIconBitmap.getWidth();
        int height = this.mIconBitmap.getHeight();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Bitmap createBitmap = Bitmap.createBitmap(displayMetrics, width, height, this.mIconBitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(this.mIconTint, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(this.mIconBitmap, 0.0f, 0.0f, paint);
        paint.setColorFilter(null);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT_FAMILY, 0));
        paint.setColor(-1);
        paint.setTextSize(displayMetrics.density * 16.0f);
        String formatSimple = TextUtils.formatSimple("%d", Integer.valueOf(this.mSimSlotIndex + 1));
        paint.getTextBounds(formatSimple, 0, 1, new Rect());
        canvas.drawText(formatSimple, (width / 2.0f) - r9.centerX(), (height / 2.0f) - r9.centerY(), paint);
        return createBitmap;
    }

    public int getIconTint() {
        return this.mIconTint;
    }

    @Deprecated
    public String getNumber() {
        return this.mNumber;
    }

    public int getDataRoaming() {
        return this.mDataRoaming;
    }

    @Deprecated
    public int getMcc() {
        try {
            if (TextUtils.isEmpty(this.mMcc)) {
                return 0;
            }
            return Integer.parseInt(this.mMcc);
        } catch (NumberFormatException unused) {
            Log.w("SubscriptionInfo", "MCC string is not a number: " + this.mMcc);
            return 0;
        }
    }

    @Deprecated
    public int getMnc() {
        try {
            if (TextUtils.isEmpty(this.mMnc)) {
                return 0;
            }
            return Integer.parseInt(this.mMnc);
        } catch (NumberFormatException unused) {
            Log.w("SubscriptionInfo", "MNC string is not a number: " + this.mMnc);
            return 0;
        }
    }

    public String getMccString() {
        return this.mMcc;
    }

    public String getMncString() {
        return this.mMnc;
    }

    public String getCountryIso() {
        return this.mCountryIso;
    }

    public boolean isEmbedded() {
        return this.mIsEmbedded;
    }

    public boolean isOpportunistic() {
        return this.mIsOpportunistic;
    }

    public boolean isActive() {
        return this.mSimSlotIndex >= 0 || this.mType == 1;
    }

    public ParcelUuid getGroupUuid() {
        return this.mGroupUuid;
    }

    public List<String> getEhplmns() {
        String[] strArr = this.mEhplmns;
        return Collections.unmodifiableList(strArr == null ? Collections.EMPTY_LIST : Arrays.asList(strArr));
    }

    public List<String> getHplmns() {
        String[] strArr = this.mHplmns;
        return Collections.unmodifiableList(strArr == null ? Collections.EMPTY_LIST : Arrays.asList(strArr));
    }

    public String getGroupOwner() {
        return this.mGroupOwner;
    }

    @SystemApi
    public int getProfileClass() {
        return this.mProfileClass;
    }

    public int semGetProfileClass() {
        return getProfileClass();
    }

    public int getSubscriptionType() {
        return this.mType;
    }

    @Deprecated
    public boolean canManageSubscription(Context context) {
        return canManageSubscription(context, context.getPackageName());
    }

    @Deprecated
    public boolean canManageSubscription(Context context, String str) {
        List<UiccAccessRule> accessRules = getAccessRules();
        if (accessRules == null) {
            return false;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 134217728);
            Iterator<UiccAccessRule> it = accessRules.iterator();
            while (it.hasNext()) {
                if (it.next().getCarrierPrivilegeStatus(packageInfo) == 1) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("SubscriptionInfo", "canManageSubscription: Unknown package: " + str, e);
            return false;
        }
    }

    @SystemApi
    public List<UiccAccessRule> getAccessRules() {
        ArrayList arrayList = new ArrayList();
        UiccAccessRule[] uiccAccessRuleArr = this.mNativeAccessRules;
        if (uiccAccessRuleArr != null) {
            arrayList.addAll(Arrays.asList(uiccAccessRuleArr));
        }
        UiccAccessRule[] uiccAccessRuleArr2 = this.mCarrierConfigAccessRules;
        if (uiccAccessRuleArr2 != null) {
            arrayList.addAll(Arrays.asList(uiccAccessRuleArr2));
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return Collections.unmodifiableList(arrayList);
    }

    public String getCardString() {
        return this.mCardString;
    }

    public int getCardId() {
        return this.mCardId;
    }

    public int getPortIndex() {
        return this.mPortIndex;
    }

    @SystemApi
    public boolean isGroupDisabled() {
        return this.mIsGroupDisabled;
    }

    @SystemApi
    public boolean areUiccApplicationsEnabled() {
        return this.mAreUiccApplicationsEnabled;
    }

    public boolean semAreUiccApplicationsEnabled() {
        return areUiccApplicationsEnabled();
    }

    public int getUsageSetting() {
        return this.mUsageSetting;
    }

    public boolean isOnlyNonTerrestrialNetwork() {
        return this.mIsOnlyNonTerrestrialNetwork;
    }

    public boolean isSatelliteESOSSupported() {
        return this.mIsSatelliteESOSSupported;
    }

    public Set<Integer> getServiceCapabilities() {
        return SubscriptionManager.getServiceCapabilitiesSet(this.mServiceCapabilities);
    }

    @SystemApi
    public int getTransferStatus() {
        return this.mTransferStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mIccId);
        parcel.writeInt(this.mSimSlotIndex);
        TextUtils.writeToParcel(this.mDisplayName, parcel, 0);
        TextUtils.writeToParcel(this.mCarrierName, parcel, 0);
        parcel.writeInt(this.mDisplayNameSource);
        parcel.writeInt(this.mIconTint);
        parcel.writeString(this.mNumber);
        parcel.writeInt(this.mDataRoaming);
        parcel.writeString(this.mMcc);
        parcel.writeString(this.mMnc);
        parcel.writeString(this.mCountryIso);
        parcel.writeBoolean(this.mIsEmbedded);
        parcel.writeTypedArray(this.mNativeAccessRules, i);
        parcel.writeString(this.mCardString);
        parcel.writeInt(this.mCardId);
        parcel.writeInt(this.mPortIndex);
        parcel.writeBoolean(this.mIsOpportunistic);
        ParcelUuid parcelUuid = this.mGroupUuid;
        parcel.writeString8(parcelUuid == null ? null : parcelUuid.toString());
        parcel.writeBoolean(this.mIsGroupDisabled);
        parcel.writeInt(this.mCarrierId);
        parcel.writeInt(this.mProfileClass);
        parcel.writeInt(this.mType);
        parcel.writeStringArray(this.mEhplmns);
        parcel.writeStringArray(this.mHplmns);
        parcel.writeString(this.mGroupOwner);
        parcel.writeTypedArray(this.mCarrierConfigAccessRules, i);
        parcel.writeBoolean(this.mAreUiccApplicationsEnabled);
        parcel.writeInt(this.mUsageSetting);
        parcel.writeBoolean(this.mIsOnlyNonTerrestrialNetwork);
        parcel.writeInt(this.mServiceCapabilities);
        parcel.writeInt(this.mTransferStatus);
        parcel.writeBoolean(this.mIsSatelliteESOSSupported);
    }

    public static String getPrintableId(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() <= 9 || TelephonyUtils.IS_DEBUGGABLE) {
            return str;
        }
        return str.substring(0, 9) + com.android.telephony.Rlog.pii(false, (Object) str.substring(9));
    }

    public String toString() {
        return "[SubscriptionInfo: id=" + this.mId + " iccId=" + getPrintableId(this.mIccId) + " simSlotIndex=" + this.mSimSlotIndex + " portIndex=" + this.mPortIndex + " isEmbedded=" + this.mIsEmbedded + " carrierId=" + this.mCarrierId + " displayName=" + ((Object) this.mDisplayName) + " carrierName=" + ((Object) this.mCarrierName) + " isOpportunistic=" + this.mIsOpportunistic + " groupUuid=" + this.mGroupUuid + " groupOwner=" + this.mGroupOwner + " isGroupDisabled=" + this.mIsGroupDisabled + " displayNameSource=" + SubscriptionManager.displayNameSourceToString(this.mDisplayNameSource) + " iconTint=" + this.mIconTint + " number=" + com.android.telephony.Rlog.pii(TelephonyUtils.IS_DEBUGGABLE, this.mNumber) + " dataRoaming=" + this.mDataRoaming + " mcc=" + this.mMcc + " mnc=" + this.mMnc + " ehplmns=" + Arrays.toString(this.mEhplmns) + " hplmns=" + Arrays.toString(this.mHplmns) + " cardString=" + getPrintableId(this.mCardString) + " cardId=" + this.mCardId + " nativeAccessRules=" + Arrays.toString(this.mNativeAccessRules) + " carrierConfigAccessRules=" + Arrays.toString(this.mCarrierConfigAccessRules) + " countryIso=" + this.mCountryIso + " profileClass=" + this.mProfileClass + " mType=" + SubscriptionManager.subscriptionTypeToString(this.mType) + " areUiccApplicationsEnabled=" + this.mAreUiccApplicationsEnabled + " usageSetting=" + SubscriptionManager.usageSettingToString(this.mUsageSetting) + " isOnlyNonTerrestrialNetwork=" + this.mIsOnlyNonTerrestrialNetwork + " serviceCapabilities=" + SubscriptionManager.getServiceCapabilitiesSet(this.mServiceCapabilities).toString() + " transferStatus=" + this.mTransferStatus + " isSatelliteESOSSupported=" + this.mIsSatelliteESOSSupported + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj;
            if (this.mId == subscriptionInfo.mId && this.mSimSlotIndex == subscriptionInfo.mSimSlotIndex && this.mDisplayNameSource == subscriptionInfo.mDisplayNameSource && this.mIconTint == subscriptionInfo.mIconTint && this.mDataRoaming == subscriptionInfo.mDataRoaming && this.mIsEmbedded == subscriptionInfo.mIsEmbedded && this.mIsOpportunistic == subscriptionInfo.mIsOpportunistic && this.mCarrierId == subscriptionInfo.mCarrierId && this.mProfileClass == subscriptionInfo.mProfileClass && this.mType == subscriptionInfo.mType && this.mAreUiccApplicationsEnabled == subscriptionInfo.mAreUiccApplicationsEnabled && this.mPortIndex == subscriptionInfo.mPortIndex && this.mUsageSetting == subscriptionInfo.mUsageSetting && this.mCardId == subscriptionInfo.mCardId && this.mIsGroupDisabled == subscriptionInfo.mIsGroupDisabled && this.mIccId.equals(subscriptionInfo.mIccId) && this.mDisplayName.equals(subscriptionInfo.mDisplayName) && this.mCarrierName.equals(subscriptionInfo.mCarrierName) && this.mNumber.equals(subscriptionInfo.mNumber) && Objects.equals(this.mMcc, subscriptionInfo.mMcc) && Objects.equals(this.mMnc, subscriptionInfo.mMnc) && Arrays.equals(this.mEhplmns, subscriptionInfo.mEhplmns) && Arrays.equals(this.mHplmns, subscriptionInfo.mHplmns) && this.mCardString.equals(subscriptionInfo.mCardString) && Arrays.equals(this.mNativeAccessRules, subscriptionInfo.mNativeAccessRules) && Arrays.equals(this.mCarrierConfigAccessRules, subscriptionInfo.mCarrierConfigAccessRules) && Objects.equals(this.mGroupUuid, subscriptionInfo.mGroupUuid) && this.mCountryIso.equals(subscriptionInfo.mCountryIso) && this.mGroupOwner.equals(subscriptionInfo.mGroupOwner) && this.mIsOnlyNonTerrestrialNetwork == subscriptionInfo.mIsOnlyNonTerrestrialNetwork && this.mServiceCapabilities == subscriptionInfo.mServiceCapabilities && this.mTransferStatus == subscriptionInfo.mTransferStatus && this.mIsSatelliteESOSSupported == subscriptionInfo.mIsSatelliteESOSSupported) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((((((Objects.hash(Integer.valueOf(this.mId), this.mIccId, Integer.valueOf(this.mSimSlotIndex), this.mDisplayName, this.mCarrierName, Integer.valueOf(this.mDisplayNameSource), Integer.valueOf(this.mIconTint), this.mNumber, Integer.valueOf(this.mDataRoaming), this.mMcc, this.mMnc, Boolean.valueOf(this.mIsEmbedded), this.mCardString, Boolean.valueOf(this.mIsOpportunistic), this.mGroupUuid, this.mCountryIso, Integer.valueOf(this.mCarrierId), Integer.valueOf(this.mProfileClass), Integer.valueOf(this.mType), this.mGroupOwner, Boolean.valueOf(this.mAreUiccApplicationsEnabled), Integer.valueOf(this.mPortIndex), Integer.valueOf(this.mUsageSetting), Integer.valueOf(this.mCardId), Boolean.valueOf(this.mIsGroupDisabled), Boolean.valueOf(this.mIsOnlyNonTerrestrialNetwork), Integer.valueOf(this.mServiceCapabilities), Integer.valueOf(this.mTransferStatus), Boolean.valueOf(this.mIsSatelliteESOSSupported)) * 31) + Arrays.hashCode(this.mEhplmns)) * 31) + Arrays.hashCode(this.mHplmns)) * 31) + Arrays.hashCode(this.mNativeAccessRules)) * 31) + Arrays.hashCode(this.mCarrierConfigAccessRules);
    }

    public static class Builder {
        private boolean mAreUiccApplicationsEnabled;
        private int mCardId;
        private String mCardString;
        private UiccAccessRule[] mCarrierConfigAccessRules;
        private int mCarrierId;
        private CharSequence mCarrierName;
        private String mCountryIso;
        private int mDataRoaming;
        private CharSequence mDisplayName;
        private int mDisplayNameSource;
        private String[] mEhplmns;
        private String mGroupOwner;
        private ParcelUuid mGroupUuid;
        private String[] mHplmns;
        private String mIccId;
        private Bitmap mIconBitmap;
        private int mIconTint;
        private int mId;
        private boolean mIsEmbedded;
        private boolean mIsGroupDisabled;
        private boolean mIsOnlyNonTerrestrialNetwork;
        private boolean mIsOpportunistic;
        private boolean mIsSatelliteESOSSupported;
        private String mMcc;
        private String mMnc;
        private UiccAccessRule[] mNativeAccessRules;
        private String mNumber;
        private int mPortIndex;
        private int mProfileClass;
        private int mServiceCapabilities;
        private int mSimSlotIndex;
        private int mTransferStatus;
        private int mType;
        private int mUsageSetting;

        public Builder() {
            this.mId = -1;
            this.mIccId = "";
            this.mSimSlotIndex = -1;
            this.mDisplayName = "";
            this.mCarrierName = "";
            this.mDisplayNameSource = -1;
            this.mIconTint = 0;
            this.mNumber = "";
            this.mDataRoaming = 0;
            this.mIconBitmap = null;
            this.mMcc = null;
            this.mMnc = null;
            this.mEhplmns = new String[0];
            this.mHplmns = new String[0];
            this.mCountryIso = "";
            this.mIsEmbedded = false;
            this.mNativeAccessRules = null;
            this.mCardString = "";
            this.mCardId = -2;
            this.mIsOpportunistic = false;
            this.mGroupUuid = null;
            this.mIsGroupDisabled = false;
            this.mCarrierId = -1;
            this.mProfileClass = -1;
            this.mType = 0;
            this.mGroupOwner = "";
            this.mCarrierConfigAccessRules = null;
            this.mAreUiccApplicationsEnabled = true;
            this.mPortIndex = -1;
            this.mUsageSetting = -1;
            this.mIsOnlyNonTerrestrialNetwork = false;
            this.mTransferStatus = 0;
            this.mServiceCapabilities = 0;
            this.mIsSatelliteESOSSupported = false;
        }

        public Builder(SubscriptionInfo subscriptionInfo) {
            this.mId = -1;
            this.mIccId = "";
            this.mSimSlotIndex = -1;
            this.mDisplayName = "";
            this.mCarrierName = "";
            this.mDisplayNameSource = -1;
            this.mIconTint = 0;
            this.mNumber = "";
            this.mDataRoaming = 0;
            this.mIconBitmap = null;
            this.mMcc = null;
            this.mMnc = null;
            this.mEhplmns = new String[0];
            this.mHplmns = new String[0];
            this.mCountryIso = "";
            this.mIsEmbedded = false;
            this.mNativeAccessRules = null;
            this.mCardString = "";
            this.mCardId = -2;
            this.mIsOpportunistic = false;
            this.mGroupUuid = null;
            this.mIsGroupDisabled = false;
            this.mCarrierId = -1;
            this.mProfileClass = -1;
            this.mType = 0;
            this.mGroupOwner = "";
            this.mCarrierConfigAccessRules = null;
            this.mAreUiccApplicationsEnabled = true;
            this.mPortIndex = -1;
            this.mUsageSetting = -1;
            this.mIsOnlyNonTerrestrialNetwork = false;
            this.mTransferStatus = 0;
            this.mServiceCapabilities = 0;
            this.mIsSatelliteESOSSupported = false;
            this.mId = subscriptionInfo.mId;
            this.mIccId = subscriptionInfo.mIccId;
            this.mSimSlotIndex = subscriptionInfo.mSimSlotIndex;
            this.mDisplayName = subscriptionInfo.mDisplayName;
            this.mCarrierName = subscriptionInfo.mCarrierName;
            this.mDisplayNameSource = subscriptionInfo.mDisplayNameSource;
            this.mIconTint = subscriptionInfo.mIconTint;
            this.mNumber = subscriptionInfo.mNumber;
            this.mDataRoaming = subscriptionInfo.mDataRoaming;
            this.mIconBitmap = subscriptionInfo.mIconBitmap;
            this.mMcc = subscriptionInfo.mMcc;
            this.mMnc = subscriptionInfo.mMnc;
            this.mEhplmns = subscriptionInfo.mEhplmns;
            this.mHplmns = subscriptionInfo.mHplmns;
            this.mCountryIso = subscriptionInfo.mCountryIso;
            this.mIsEmbedded = subscriptionInfo.mIsEmbedded;
            this.mNativeAccessRules = subscriptionInfo.mNativeAccessRules;
            this.mCardString = subscriptionInfo.mCardString;
            this.mCardId = subscriptionInfo.mCardId;
            this.mIsOpportunistic = subscriptionInfo.mIsOpportunistic;
            this.mGroupUuid = subscriptionInfo.mGroupUuid;
            this.mIsGroupDisabled = subscriptionInfo.mIsGroupDisabled;
            this.mCarrierId = subscriptionInfo.mCarrierId;
            this.mProfileClass = subscriptionInfo.mProfileClass;
            this.mType = subscriptionInfo.mType;
            this.mGroupOwner = subscriptionInfo.mGroupOwner;
            this.mCarrierConfigAccessRules = subscriptionInfo.mCarrierConfigAccessRules;
            this.mAreUiccApplicationsEnabled = subscriptionInfo.mAreUiccApplicationsEnabled;
            this.mPortIndex = subscriptionInfo.mPortIndex;
            this.mUsageSetting = subscriptionInfo.mUsageSetting;
            this.mIsOnlyNonTerrestrialNetwork = subscriptionInfo.mIsOnlyNonTerrestrialNetwork;
            this.mServiceCapabilities = subscriptionInfo.mServiceCapabilities;
            this.mTransferStatus = subscriptionInfo.mTransferStatus;
            this.mIsSatelliteESOSSupported = subscriptionInfo.mIsSatelliteESOSSupported;
        }

        public Builder setId(int i) {
            this.mId = i;
            return this;
        }

        public Builder setIccId(String str) {
            this.mIccId = TextUtils.emptyIfNull(str);
            return this;
        }

        public Builder setSimSlotIndex(int i) {
            this.mSimSlotIndex = i;
            return this;
        }

        public Builder setDisplayName(CharSequence charSequence) {
            if (charSequence == null) {
                charSequence = "";
            }
            this.mDisplayName = charSequence;
            return this;
        }

        public Builder setCarrierName(CharSequence charSequence) {
            if (charSequence == null) {
                charSequence = "";
            }
            this.mCarrierName = charSequence;
            return this;
        }

        public Builder setDisplayNameSource(int i) {
            this.mDisplayNameSource = i;
            return this;
        }

        public Builder setIconTint(int i) {
            this.mIconTint = i;
            return this;
        }

        public Builder setNumber(String str) {
            this.mNumber = TextUtils.emptyIfNull(str);
            return this;
        }

        public Builder setDataRoaming(int i) {
            this.mDataRoaming = i;
            return this;
        }

        public Builder setIcon(Bitmap bitmap) {
            this.mIconBitmap = bitmap;
            return this;
        }

        public Builder setMcc(String str) {
            this.mMcc = str;
            return this;
        }

        public Builder setMnc(String str) {
            this.mMnc = str;
            return this;
        }

        public Builder setEhplmns(String[] strArr) {
            if (strArr == null) {
                strArr = new String[0];
            }
            this.mEhplmns = strArr;
            return this;
        }

        public Builder setHplmns(String[] strArr) {
            if (strArr == null) {
                strArr = new String[0];
            }
            this.mHplmns = strArr;
            return this;
        }

        public Builder setCountryIso(String str) {
            this.mCountryIso = TextUtils.emptyIfNull(str);
            return this;
        }

        public Builder setEmbedded(boolean z) {
            this.mIsEmbedded = z;
            return this;
        }

        public Builder setNativeAccessRules(UiccAccessRule[] uiccAccessRuleArr) {
            this.mNativeAccessRules = uiccAccessRuleArr;
            return this;
        }

        public Builder setCardString(String str) {
            this.mCardString = TextUtils.emptyIfNull(str);
            return this;
        }

        public Builder setCardId(int i) {
            this.mCardId = i;
            return this;
        }

        public Builder setOpportunistic(boolean z) {
            this.mIsOpportunistic = z;
            return this;
        }

        public Builder setGroupUuid(String str) {
            this.mGroupUuid = TextUtils.isEmpty(str) ? null : ParcelUuid.fromString(str);
            return this;
        }

        public Builder setGroupDisabled(boolean z) {
            this.mIsGroupDisabled = z;
            return this;
        }

        public Builder setCarrierId(int i) {
            this.mCarrierId = i;
            return this;
        }

        public Builder setProfileClass(int i) {
            this.mProfileClass = i;
            return this;
        }

        public Builder setType(int i) {
            this.mType = i;
            return this;
        }

        public Builder setGroupOwner(String str) {
            this.mGroupOwner = TextUtils.emptyIfNull(str);
            return this;
        }

        public Builder setCarrierConfigAccessRules(UiccAccessRule[] uiccAccessRuleArr) {
            this.mCarrierConfigAccessRules = uiccAccessRuleArr;
            return this;
        }

        public Builder setUiccApplicationsEnabled(boolean z) {
            this.mAreUiccApplicationsEnabled = z;
            return this;
        }

        public Builder setPortIndex(int i) {
            this.mPortIndex = i;
            return this;
        }

        public Builder setUsageSetting(int i) {
            this.mUsageSetting = i;
            return this;
        }

        public Builder setOnlyNonTerrestrialNetwork(boolean z) {
            this.mIsOnlyNonTerrestrialNetwork = z;
            return this;
        }

        public Builder setServiceCapabilities(Set<Integer> set) {
            Iterator<Integer> it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                if (intValue < 1 || intValue > 3) {
                    throw new IllegalArgumentException("Invalid service capability value: " + intValue);
                }
                i |= SubscriptionManager.serviceCapabilityToBitmask(intValue);
            }
            this.mServiceCapabilities = i;
            return this;
        }

        public Builder setTransferStatus(int i) {
            this.mTransferStatus = i;
            return this;
        }

        public Builder setSatelliteESOSSupported(boolean z) {
            this.mIsSatelliteESOSSupported = z;
            return this;
        }

        public SubscriptionInfo build() {
            return new SubscriptionInfo(this);
        }
    }
}
