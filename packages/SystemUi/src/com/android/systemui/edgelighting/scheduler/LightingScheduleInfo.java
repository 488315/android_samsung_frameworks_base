package com.android.systemui.edgelighting.scheduler;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.Parcelable;
import android.util.Slog;
import com.android.systemui.edgelighting.utils.Utils;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LightingScheduleInfo {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public final Drawable mIcon;
    public final String mKey;
    public final SemEdgeLightingInfo mLightingInfo;
    public LightingLogicPolicy mLightingLogicPolicy;
    public ChainItemTitle mNotiTextPolicyChain;
    public final String mPackageName;
    public int mReason;
    public int mDuration = ImsProfile.DEFAULT_DEREG_TIMEOUT;
    public boolean mIsDirty = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ChainItemBodySubText extends NotiTextChain {
        public ChainItemBodySubText(LightingScheduleInfo lightingScheduleInfo) {
            super(lightingScheduleInfo);
        }

        @Override // com.android.systemui.edgelighting.scheduler.LightingScheduleInfo.NotiTextChain
        public final String getExtraKey() {
            return "subText";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ChainItemBodyText extends NotiTextChain {
        public ChainItemBodyText(LightingScheduleInfo lightingScheduleInfo) {
            super(lightingScheduleInfo);
        }

        @Override // com.android.systemui.edgelighting.scheduler.LightingScheduleInfo.NotiTextChain
        public final String getExtraKey() {
            return "text";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ChainItemTicker extends NotiTextChain {
        public ChainItemTicker(LightingScheduleInfo lightingScheduleInfo) {
            super(lightingScheduleInfo);
        }

        @Override // com.android.systemui.edgelighting.scheduler.LightingScheduleInfo.NotiTextChain
        public final String getExtraKey() {
            return "tickerText";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ChainItemTitle extends NotiTextChain {
        public ChainItemTitle(LightingScheduleInfo lightingScheduleInfo) {
            super(lightingScheduleInfo);
        }

        @Override // com.android.systemui.edgelighting.scheduler.LightingScheduleInfo.NotiTextChain
        public final String getExtraKey() {
            return "titleText";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LightingLogicPolicy {
        public boolean isNeedToKeepWhenLcdOff = false;
        public boolean isNeedLightOnWhenTurnOveredLcdOn = false;
        public boolean isNeedLightOnWhenTurnOveredLcdOff = false;
        public boolean isNeedLightOnWhenTurnRightedLcdOn = false;
        public boolean isNeedLightOnWhenTurnRightedLcdOff = false;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class NotiTextChain {
        public boolean mIsTextDirty;
        public NotiTextChain mNext;
        public final LightingScheduleInfo mThisScheduleInfo;
        public ArrayList mWith;

        public NotiTextChain(LightingScheduleInfo lightingScheduleInfo) {
            this.mThisScheduleInfo = lightingScheduleInfo;
        }

        public static String getExtraText(LightingScheduleInfo lightingScheduleInfo, String str) {
            CharSequence charSequence;
            Bundle extra = lightingScheduleInfo.mLightingInfo.getExtra();
            if (extra == null || (charSequence = extra.getCharSequence(str)) == null) {
                return null;
            }
            return charSequence.toString().replaceAll("\\s", " ");
        }

        public final void appendThisChainItemText(StringBuffer stringBuffer, NotiTextChain notiTextChain) {
            String extraText = getExtraText(this.mThisScheduleInfo, notiTextChain.getExtraKey());
            stringBuffer.append(notiTextChain.getExtraKey());
            stringBuffer.append(" [");
            stringBuffer.append(extraText);
            stringBuffer.append("] ");
            ArrayList arrayList = notiTextChain.mWith;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    NotiTextChain notiTextChain2 = (NotiTextChain) arrayList.get(i);
                    if (notiTextChain2 != null) {
                        stringBuffer.append("with:");
                        appendThisChainItemText(stringBuffer, notiTextChain2);
                    }
                }
            }
        }

        public final String[] getChainText() {
            String extraKey = getExtraKey();
            LightingScheduleInfo lightingScheduleInfo = this.mThisScheduleInfo;
            String extraText = getExtraText(lightingScheduleInfo, extraKey);
            if (extraText == null || extraText.isEmpty()) {
                if (this.mWith == null) {
                    NotiTextChain notiTextChain = this.mNext;
                    if (notiTextChain != null) {
                        return notiTextChain.getChainText();
                    }
                    return null;
                }
                StringBuffer stringBuffer = new StringBuffer();
                int size = this.mWith.size();
                for (int i = 0; i < size; i++) {
                    NotiTextChain notiTextChain2 = (NotiTextChain) this.mWith.get(i);
                    if (notiTextChain2 == null) {
                        boolean z = LightingScheduleInfo.DEBUG;
                        Slog.d("LightingScheduleInfo", "getChainText: invalid info at " + i);
                    } else {
                        String extraText2 = getExtraText(lightingScheduleInfo, notiTextChain2.getExtraKey());
                        if (extraText2 != null && !extraText2.isEmpty()) {
                            if (i > 0) {
                                stringBuffer.append(" ");
                            }
                            stringBuffer.append(extraText2);
                        }
                    }
                }
                String[] strArr = new String[2];
                strArr[0] = stringBuffer.toString();
                return strArr;
            }
            String[] strArr2 = new String[2];
            strArr2[0] = extraText;
            StringBuffer stringBuffer2 = new StringBuffer();
            ArrayList arrayList = this.mWith;
            if (arrayList != null) {
                int size2 = arrayList.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    NotiTextChain notiTextChain3 = (NotiTextChain) this.mWith.get(i2);
                    if (notiTextChain3 == null) {
                        boolean z2 = LightingScheduleInfo.DEBUG;
                        Slog.d("LightingScheduleInfo", "getChainText: invalid info at " + i2);
                    } else {
                        String extraText3 = getExtraText(lightingScheduleInfo, notiTextChain3.getExtraKey());
                        if (extraText3 != null && !extraText3.isEmpty()) {
                            if (i2 > 0) {
                                stringBuffer2.append(" ");
                            }
                            stringBuffer2.append(extraText3);
                        }
                    }
                }
                strArr2[1] = stringBuffer2.toString();
            }
            StringBuffer stringBuffer3 = new StringBuffer("getChainText: from ");
            stringBuffer3.append(getExtraKey());
            if (LightingScheduleInfo.DEBUG) {
                stringBuffer3.append(" [");
                stringBuffer3.append(extraText);
                stringBuffer3.append(" | ");
                stringBuffer3.append(stringBuffer2);
                stringBuffer3.append("]");
            }
            Slog.d("LightingScheduleInfo", stringBuffer3.toString());
            return strArr2;
        }

        public abstract String getExtraKey();

        public final boolean isTextDirty() {
            boolean z;
            ArrayList arrayList = this.mWith;
            if (arrayList != null) {
                int size = arrayList.size();
                z = false;
                for (int i = 0; i < size; i++) {
                    z |= ((NotiTextChain) this.mWith.get(i)).mIsTextDirty;
                }
            } else {
                z = false;
            }
            if (this.mIsTextDirty || z) {
                return true;
            }
            NotiTextChain notiTextChain = this.mNext;
            if (notiTextChain != null) {
                return notiTextChain.isTextDirty();
            }
            return false;
        }

        public final void mergeText(LightingScheduleInfo lightingScheduleInfo) {
            mergeTextChainItem(this, lightingScheduleInfo);
            ArrayList arrayList = this.mWith;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    mergeTextChainItem((NotiTextChain) this.mWith.get(i), lightingScheduleInfo);
                }
            }
            NotiTextChain notiTextChain = this.mNext;
            if (notiTextChain != null) {
                notiTextChain.mergeText(lightingScheduleInfo);
            }
        }

        public final void mergeTextChainItem(NotiTextChain notiTextChain, LightingScheduleInfo lightingScheduleInfo) {
            String extraText = getExtraText(lightingScheduleInfo, notiTextChain.getExtraKey());
            String extraKey = notiTextChain.getExtraKey();
            LightingScheduleInfo lightingScheduleInfo2 = this.mThisScheduleInfo;
            String extraText2 = getExtraText(lightingScheduleInfo2, extraKey);
            boolean z = true;
            boolean z2 = extraText == null || extraText.isEmpty();
            if (extraText2 != null && !extraText2.isEmpty()) {
                z = false;
            }
            notiTextChain.mIsTextDirty = !z;
            if (LightingScheduleInfo.DEBUG) {
                StringBuffer stringBuffer = new StringBuffer(notiTextChain.getExtraKey());
                stringBuffer.append("_mergeText: [");
                stringBuffer.append(extraText);
                stringBuffer.append("] --> [");
                stringBuffer.append(extraText2);
                stringBuffer.append("]");
                Slog.d("LightingScheduleInfo", stringBuffer.toString());
            } else {
                if (!z2) {
                    Slog.i("LightingScheduleInfo", " mergeTextChainItem :" + notiTextChain.getExtraKey() + ": prev : " + extraText.hashCode() + " = isDirty : " + notiTextChain.mIsTextDirty);
                }
                if (!z) {
                    Slog.i("LightingScheduleInfo", " mergeTextChainItem :" + notiTextChain.getExtraKey() + " this : " + extraText2.hashCode() + " = isDirty : " + notiTextChain.mIsTextDirty);
                }
            }
            boolean equals = extraText != null ? extraText.equals(extraText2) : false;
            if ((z2 || !z) && !equals) {
                return;
            }
            String extraKey2 = notiTextChain.getExtraKey();
            if (extraText != null) {
                SemEdgeLightingInfo semEdgeLightingInfo = lightingScheduleInfo2.mLightingInfo;
                Bundle extra = semEdgeLightingInfo.getExtra();
                if (extra == null) {
                    extra = new Bundle();
                }
                extra.putCharSequence(extraKey2, extraText);
                semEdgeLightingInfo.setExtra(extra);
            }
            notiTextChain.mIsTextDirty = false;
        }

        public final String toString() {
            if (!LightingScheduleInfo.DEBUG) {
                return super.toString();
            }
            StringBuffer stringBuffer = new StringBuffer("NotiTextChain: ");
            NotiTextChain notiTextChain = this;
            while (notiTextChain.mNext != null) {
                appendThisChainItemText(stringBuffer, notiTextChain);
                notiTextChain = notiTextChain.mNext;
            }
            appendThisChainItemText(stringBuffer, notiTextChain);
            return stringBuffer.toString();
        }
    }

    public LightingScheduleInfo(String str, String str2, SemEdgeLightingInfo semEdgeLightingInfo, Drawable drawable, int i, int i2) {
        this.mPackageName = str;
        this.mKey = str2;
        this.mLightingInfo = semEdgeLightingInfo;
        this.mReason = i;
        this.mIcon = drawable;
        ChainItemTitle chainItemTitle = new ChainItemTitle(this);
        ChainItemBodyText chainItemBodyText = new ChainItemBodyText(this);
        ChainItemBodySubText chainItemBodySubText = new ChainItemBodySubText(this);
        ChainItemTicker chainItemTicker = new ChainItemTicker(this);
        this.mNotiTextPolicyChain = chainItemTitle;
        if (chainItemTitle.mWith == null) {
            chainItemTitle.mWith = new ArrayList();
        }
        chainItemTitle.mWith.add(chainItemBodyText);
        chainItemTitle.mNext = chainItemTicker;
        chainItemTicker.mNext = chainItemBodySubText;
    }

    public final ArrayList getActionList() {
        ArrayList parcelableArrayList;
        Bundle extra = this.mLightingInfo.getExtra();
        if (extra == null || (parcelableArrayList = extra.getParcelableArrayList("noti_actions")) == null) {
            return null;
        }
        return parcelableArrayList;
    }

    public final PendingIntent getContentIntent() {
        Parcelable parcelable;
        Bundle extra = this.mLightingInfo.getExtra();
        if (extra == null || (parcelable = extra.getParcelable("content_intent")) == null || !(parcelable instanceof PendingIntent)) {
            return null;
        }
        return (PendingIntent) parcelable;
    }

    public final int getDuration() {
        return Utils.isLargeCoverFlipFolded() ? this.mDuration - 1000 : this.mDuration;
    }

    public final String[] getNotiText() {
        if (DEBUG) {
            Slog.d("LightingScheduleInfo", this.mNotiTextPolicyChain.toString());
        }
        return this.mNotiTextPolicyChain.getChainText();
    }

    public final int getNotificationID() {
        Bundle extra = this.mLightingInfo.getExtra();
        if (extra != null) {
            return extra.getInt("noti_id");
        }
        return 0;
    }

    public final String getNotificationKey() {
        Bundle extra;
        SemEdgeLightingInfo semEdgeLightingInfo = this.mLightingInfo;
        return (semEdgeLightingInfo == null || (extra = semEdgeLightingInfo.getExtra()) == null) ? this.mKey : extra.getString("noti_key");
    }

    public final String getNotificationTag() {
        Bundle extra = this.mLightingInfo.getExtra();
        if (extra != null) {
            return extra.getString("noti_tag");
        }
        return null;
    }

    public final int getUserId() {
        Bundle extra = this.mLightingInfo.getExtra();
        if (extra != null) {
            return extra.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
        }
        return 0;
    }

    public final int getVisibility() {
        Bundle extra = this.mLightingInfo.getExtra();
        if (extra != null) {
            return extra.getInt("noti_visiblity", -1000);
        }
        return -1000;
    }

    public final void setDuration(int i) {
        Slog.d("LightingScheduleInfo", "setDuration : duration=" + i);
        this.mDuration = i;
    }
}
