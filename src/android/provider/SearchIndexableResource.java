package android.provider;

import android.annotation.SystemApi;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

@SystemApi
/* loaded from: classes3.dex */
public class SearchIndexableResource extends SearchIndexableData {
    public int xmlResId;

    public SearchIndexableResource(int i, int i2, String str, int i3) {
        this.rank = i;
        this.xmlResId = i2;
        this.className = str;
        this.iconResId = i3;
    }

    public SearchIndexableResource(Context context) {
        super(context);
    }

    @Override // android.provider.SearchIndexableData
    public String toString() {
        return "SearchIndexableResource[" + super.toString() + ", xmlResId: " + this.xmlResId + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
