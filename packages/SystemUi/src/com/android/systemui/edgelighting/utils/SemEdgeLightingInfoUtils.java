package com.android.systemui.edgelighting.utils;

import android.os.Bundle;
import android.os.Debug;
import android.os.Parcelable;
import com.samsung.android.edge.SemEdgeLightingInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SemEdgeLightingInfoUtils {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final String EXTRA_KEY_SMALL_ICON = "smallIcon";

    public static int getInt(SemEdgeLightingInfo semEdgeLightingInfo, String str) {
        Bundle extra = semEdgeLightingInfo.getExtra();
        if (extra != null) {
            return extra.getInt(str, 0);
        }
        return 0;
    }

    public static String getText(SemEdgeLightingInfo semEdgeLightingInfo, String str) {
        CharSequence charSequence;
        Bundle extra = semEdgeLightingInfo.getExtra();
        if (extra == null || (charSequence = extra.getCharSequence(str)) == null) {
            return null;
        }
        return charSequence.toString().replaceAll("\\s", " ");
    }

    public static boolean isOnGoing(SemEdgeLightingInfo semEdgeLightingInfo) {
        return (semEdgeLightingInfo == null || (getInt(semEdgeLightingInfo, "flag") & 2) == 0) ? false : true;
    }

    public static String toString(SemEdgeLightingInfo semEdgeLightingInfo) {
        Parcelable parcelable;
        StringBuilder sb = new StringBuilder();
        sb.append("SemEdgeLightingInfo=" + semEdgeLightingInfo);
        if (DEBUG) {
            sb.append("flag=");
            sb.append(Integer.toHexString(getInt(semEdgeLightingInfo, "flag")));
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
            Bundle extra = semEdgeLightingInfo.getExtra();
            sb.append(extra != null ? extra.getLong("component_time", 0L) : 0L);
            sb.append(",intent=");
            Bundle extra2 = semEdgeLightingInfo.getExtra();
            if (extra2 == null || (parcelable = extra2.getParcelable("content_intent")) == null) {
                parcelable = null;
            }
            sb.append(parcelable);
        }
        return sb.toString();
    }
}
