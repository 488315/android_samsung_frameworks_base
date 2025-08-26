package com.android.systemui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.android.systemui.res.R$styleable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public class AutoReinflateContainer extends FrameLayout {
    public static final Set SUPPORTED_CHANGES = Set.of(4, 512, Integer.MIN_VALUE, 4096, 1073741824);
    public final List mInflateListeners;
    public final Configuration mLastConfig;
    public final int mLayout;

    public AutoReinflateContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInflateListeners = new ArrayList();
        this.mLastConfig = new Configuration();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AutoReinflateContainer);
        if (!typedArrayObtainStyledAttributes.hasValue(0)) {
            throw new IllegalArgumentException("AutoReinflateContainer must contain a layout");
        }
        this.mLayout = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        inflateLayout();
    }

    public final void inflateLayout() {
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(this.mLayout, this);
        if (((ArrayList) this.mInflateListeners).size() <= 0) {
            return;
        }
        if (((ArrayList) this.mInflateListeners).get(0) != null) {
            throw new ClassCastException();
        }
        getChildAt(0);
        throw null;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        int iUpdateFrom = this.mLastConfig.updateFrom(configuration);
        Iterator it = SUPPORTED_CHANGES.iterator();
        while (it.hasNext()) {
            if ((((Integer) it.next()).intValue() & iUpdateFrom) != 0) {
                inflateLayout();
                return;
            }
        }
    }
}
