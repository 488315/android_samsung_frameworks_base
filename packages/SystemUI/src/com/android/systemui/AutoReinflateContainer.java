package com.android.systemui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.systemui.res.R$styleable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class AutoReinflateContainer extends FrameLayout {
    public static final Set SUPPORTED_CHANGES = Set.of(4, 512, Integer.MIN_VALUE, 4096, 1073741824);
    public final List mInflateListeners;
    public final Configuration mLastConfig;
    public final int mLayout;

    public AutoReinflateContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInflateListeners = new ArrayList();
        this.mLastConfig = new Configuration();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AutoReinflateContainer);
        if (!obtainStyledAttributes.hasValue(0)) {
            throw new IllegalArgumentException("AutoReinflateContainer must contain a layout");
        }
        this.mLayout = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        inflateLayout();
    }

    public final void inflateLayout() {
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(this.mLayout, this);
        if (this.mInflateListeners.size() <= 0) {
            return;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mInflateListeners.get(0));
        getChildAt(0);
        throw null;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        int updateFrom = this.mLastConfig.updateFrom(configuration);
        Iterator it = SUPPORTED_CHANGES.iterator();
        while (it.hasNext()) {
            if ((((Integer) it.next()).intValue() & updateFrom) != 0) {
                inflateLayout();
                return;
            }
        }
    }
}
