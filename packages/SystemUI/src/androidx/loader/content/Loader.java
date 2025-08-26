package androidx.loader.content;

import android.content.Context;
import androidx.loader.app.LoaderManagerImpl;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public class Loader {
    public int mId;
    public LoaderManagerImpl.LoaderInfo mListener;
    public boolean mStarted = false;
    public boolean mAbandoned = false;
    public boolean mReset = true;

    public Loader(Context context) {
        context.getApplicationContext();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        Class<?> cls = getClass();
        sb.append(cls.getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(cls)));
        sb.append(" id=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.mId, "}", sb);
    }
}
