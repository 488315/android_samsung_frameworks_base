package androidx.picker.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.picker.R$styleable;
import androidx.picker.adapter.AbsAdapter;
import androidx.picker.adapter.HeaderFooterAdapter;
import androidx.picker.adapter.ListAdapter;
import androidx.picker.common.log.LogTagHelperKt;
import androidx.picker.decorator.ListDividerItemDecoration;
import androidx.picker.decorator.ListSpacingItemDecoration;
import androidx.picker.decorator.RoundedCornerDecoration;
import androidx.picker.features.composable.ComposableStrategy;
import androidx.picker.features.composable.DefaultComposableStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.reflect.InvocationTargetException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslAppPickerListView extends SeslAppPickerView {
    public final ComposableStrategy mComposableStrategy;

    public SeslAppPickerListView(Context context) {
        this(context, null);
    }

    @Override // androidx.picker.widget.SeslAppPickerView
    public final AbsAdapter getAppPickerAdapter() {
        ListAdapter listAdapter = new ListAdapter(((SeslAppPickerView) this).mContext, this.mComposableStrategy);
        listAdapter.setHasStableIds(true);
        return listAdapter;
    }

    @Override // androidx.picker.widget.SeslAppPickerView
    public final RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(((SeslAppPickerView) this).mContext);
    }

    @Override // androidx.picker.widget.SeslAppPickerView, androidx.picker.common.log.LogTag
    public final String getLogTag() {
        return "SeslAppPickerListView";
    }

    @Override // androidx.picker.widget.SeslAppPickerView
    public final void setItemDecoration(int i, HeaderFooterAdapter headerFooterAdapter) {
        super.setItemDecoration(i, headerFooterAdapter);
        addItemDecoration(new ListSpacingItemDecoration(((SeslAppPickerView) this).mContext));
        addItemDecoration(new ListDividerItemDecoration(((SeslAppPickerView) this).mContext));
        addItemDecoration(new RoundedCornerDecoration(((SeslAppPickerView) this).mContext, headerFooterAdapter));
    }

    public SeslAppPickerListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x001e, code lost:
    
        if (r4 == null) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SeslAppPickerListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray typedArray;
        this.mViewType = 0;
        TypedArray typedArray2 = null;
        r1 = null;
        String str = null;
        try {
            typedArray = context.obtainStyledAttributes(attributeSet, R$styleable.SeslAppPickerListView, i, 0);
            try {
                try {
                    str = typedArray.getString(0);
                } catch (RuntimeException e) {
                    e = e;
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                th = th;
                typedArray2 = typedArray;
                if (typedArray2 != null) {
                    typedArray2.recycle();
                }
                throw th;
            }
        } catch (RuntimeException e2) {
            e = e2;
            typedArray = null;
        } catch (Throwable th2) {
            th = th2;
            if (typedArray2 != null) {
            }
            throw th;
        }
        typedArray.recycle();
        if (str == null) {
            try {
                str = DefaultComposableStrategy.class.getName();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | NullPointerException | InvocationTargetException e3) {
                LogTagHelperKt.info(this, "used DefaultComposableStrategy");
                String message = e3.getMessage();
                LogTagHelperKt.debug(this, message == null ? "Unknown error" : message);
                if (LogTagHelperKt.IS_DEBUG_DEVICE) {
                    e3.printStackTrace();
                }
                this.mComposableStrategy = new DefaultComposableStrategy();
            }
        }
        this.mComposableStrategy = (ComposableStrategy) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
        LogTagHelperKt.debug(this, "use ComposableStrategy: " + this.mComposableStrategy);
        initialize();
    }
}
