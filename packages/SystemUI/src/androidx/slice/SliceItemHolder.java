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

/* loaded from: classes.dex */
public class SliceItemHolder implements VersionedParcelable {
    public static final SliceProviderCompat.AnonymousClass2 sHandler = null;
    public static final Object sSerializeLock = null;
    public Bundle mBundle;
    public int mInt;
    public long mLong;
    public Parcelable mParcelable;
    public final SliceItemPool mPool;
    public String mStr;
    public VersionedParcelable mVersionedParcelable;

    public class SliceItemPool {
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
        String html;
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
                    html = Html.toHtml((Spanned) obj, 0);
                } else {
                    html = (String) obj;
                }
                this.mStr = html;
                break;
            case "image":
            case "slice":
                this.mVersionedParcelable = (VersionedParcelable) obj;
                break;
            case "input":
                this.mParcelable = (Parcelable) obj;
                break;
        }
        SliceProviderCompat.AnonymousClass2 anonymousClass2 = sHandler;
        if (anonymousClass2 != null) {
            anonymousClass2.handle(this);
        }
    }
}
