package com.android.systemui.edgelighting.utils;

import android.os.Bundle;
import android.os.Debug;
import android.os.Parcelable;
import com.samsung.android.edge.SemEdgeLightingInfo;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SemEdgeLightingInfoUtils {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final String EXTRA_KEY_SMALL_ICON = "smallIcon";

    public static String getText(SemEdgeLightingInfo semEdgeLightingInfo, String str) {
        CharSequence charSequence;
        Bundle extra = semEdgeLightingInfo.getExtra();
        if (extra == null || (charSequence = extra.getCharSequence(str)) == null) {
            return null;
        }
        return charSequence.toString().replaceAll("\\s", " ");
    }

    public static boolean isOnGoing(SemEdgeLightingInfo semEdgeLightingInfo) {
        if (semEdgeLightingInfo == null) {
            return false;
        }
        Bundle extra = semEdgeLightingInfo.getExtra();
        return ((extra != null ? extra.getInt("flag", 0) : 0) & 2) != 0;
    }

    public static String toString(SemEdgeLightingInfo semEdgeLightingInfo) {
        Parcelable parcelable;
        StringBuilder sb = new StringBuilder();
        sb.append("SemEdgeLightingInfo=" + semEdgeLightingInfo);
        if (DEBUG) {
            sb.append("flag=");
            Bundle extra = semEdgeLightingInfo.getExtra();
            sb.append(Integer.toHexString(extra != null ? extra.getInt("flag", 0) : 0));
            sb.append("ticker=");
            sb.append(getText(semEdgeLightingInfo, "tickerText"));
            sb.append("title=");
            sb.append(getText(semEdgeLightingInfo, "titleText"));
            sb.append("text=");
            sb.append(getText(semEdgeLightingInfo, "text"));
            sb.append("sub=");
            sb.append(getText(semEdgeLightingInfo, "subText"));
            sb.append(",cn=");
            sb.append(getText(semEdgeLightingInfo, "component"));
            sb.append(",cn time=");
            Bundle extra2 = semEdgeLightingInfo.getExtra();
            sb.append(extra2 != null ? extra2.getLong("component_time", 0L) : 0L);
            sb.append(",intent=");
            Bundle extra3 = semEdgeLightingInfo.getExtra();
            Parcelable parcelable2 = null;
            if (extra3 != null && (parcelable = extra3.getParcelable("content_intent")) != null) {
                parcelable2 = parcelable;
            }
            sb.append(parcelable2);
        }
        return sb.toString();
    }
}
