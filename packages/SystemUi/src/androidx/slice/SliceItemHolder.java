package androidx.slice;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import androidx.core.util.Pair;
import androidx.slice.compat.SliceProviderCompat;
import androidx.versionedparcelable.VersionedParcelable;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SliceItemHolder implements VersionedParcelable {
    public static SliceProviderCompat.C04942 sHandler;
    public static final Object sSerializeLock = new Object();
    public Bundle mBundle;
    public int mInt;
    public long mLong;
    public Parcelable mParcelable;
    public final SliceItemPool mPool;
    public String mStr;
    public VersionedParcelable mVersionedParcelable;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SliceItemPool {
        public final ArrayList mCached = new ArrayList();
    }

    public SliceItemHolder(SliceItemPool sliceItemPool) {
        this.mVersionedParcelable = null;
        this.mParcelable = null;
        this.mStr = null;
        this.mInt = 0;
        this.mLong = 0L;
        this.mBundle = null;
        this.mPool = sliceItemPool;
    }

    public SliceItemHolder(String str, Object obj, boolean z) {
        String str2;
        this.mVersionedParcelable = null;
        this.mParcelable = null;
        this.mStr = null;
        this.mInt = 0;
        this.mLong = 0L;
        this.mBundle = null;
        str.getClass();
        switch (str) {
            case "action":
                Pair pair = (Pair) obj;
                Object obj2 = pair.first;
                if (obj2 instanceof PendingIntent) {
                    this.mParcelable = (Parcelable) obj2;
                } else if (!z) {
                    throw new IllegalArgumentException("Cannot write callback to parcel");
                }
                this.mVersionedParcelable = (VersionedParcelable) pair.second;
                break;
            case "bundle":
                this.mBundle = (Bundle) obj;
                break;
            case "int":
                this.mInt = ((Integer) obj).intValue();
                break;
            case "long":
                this.mLong = ((Long) obj).longValue();
                break;
            case "text":
                if (obj instanceof Spanned) {
                    str2 = Html.toHtml((Spanned) obj, 0);
                } else {
                    str2 = (String) obj;
                }
                this.mStr = str2;
                break;
            case "image":
            case "slice":
                this.mVersionedParcelable = (VersionedParcelable) obj;
                break;
            case "input":
                this.mParcelable = (Parcelable) obj;
                break;
        }
        SliceProviderCompat.C04942 c04942 = sHandler;
        if (c04942 != null) {
            c04942.handle(this);
        }
    }
}
