package android.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

@Deprecated
/* loaded from: classes.dex */
public abstract class FragmentContainer {
    public abstract <T extends View> T onFindViewById(int i);

    public abstract boolean onHasView();

    public Fragment instantiate(Context context, String str, Bundle bundle) {
        return Fragment.instantiate(context, str, bundle);
    }
}
