package androidx.picker.adapter.viewholder;

import android.view.View;
import android.view.ViewStub;
import androidx.picker.adapter.AbsAdapter;
import androidx.picker.features.composable.ComposableFrame;
import androidx.picker.features.composable.ComposableType;
import androidx.picker.features.composable.ComposableViewHolder;
import androidx.picker.model.viewdata.ViewData;
import com.android.systemui.R;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppListItemViewHolder extends PickerViewHolder {
    public final List composableItemViewHolderList;
    public final ComposableType composableType;

    public AppListItemViewHolder(View view, ComposableType composableType) {
        super(view);
        ComposableViewHolder composableViewHolder;
        this.composableType = composableType;
        View findViewById = view.findViewById(R.id.left_frame);
        Intrinsics.checkNotNull(findViewById);
        View findViewById2 = view.findViewById(R.id.icon_frame);
        Intrinsics.checkNotNull(findViewById2);
        View findViewById3 = view.findViewById(R.id.title_frame);
        Intrinsics.checkNotNull(findViewById3);
        View findViewById4 = view.findViewById(R.id.widget_frame);
        Intrinsics.checkNotNull(findViewById4);
        List<Pair> listOf = CollectionsKt__CollectionsKt.listOf(new Pair(composableType.getLeftFrame(), (ViewStub) findViewById), new Pair(composableType.getWidgetFrame(), (ViewStub) findViewById4), new Pair(composableType.getTitleFrame(), (ViewStub) findViewById3), new Pair(composableType.getIconFrame(), (ViewStub) findViewById2));
        ArrayList arrayList = new ArrayList();
        for (Pair pair : listOf) {
            ComposableFrame composableFrame = (ComposableFrame) pair.component1();
            ViewStub viewStub = (ViewStub) pair.component2();
            if (composableFrame == null) {
                composableViewHolder = null;
            } else {
                Constructor declaredConstructor = composableFrame.getViewHolderClass().getDeclaredConstructor(View.class);
                viewStub.setLayoutResource(composableFrame.getLayoutResId());
                composableViewHolder = (ComposableViewHolder) declaredConstructor.newInstance(viewStub.inflate());
            }
            if (composableViewHolder != null) {
                arrayList.add(composableViewHolder);
            }
        }
        this.composableItemViewHolderList = arrayList;
    }

    @Override // androidx.picker.adapter.viewholder.PickerViewHolder
    public final void bindAdapter(AbsAdapter absAdapter) {
        Iterator it = this.composableItemViewHolderList.iterator();
        while (it.hasNext()) {
            ((ComposableViewHolder) it.next()).bindAdapter(absAdapter);
        }
    }

    @Override // androidx.picker.adapter.viewholder.PickerViewHolder
    public final void bindData(ViewData viewData) {
        super.bindData(viewData);
        Iterator it = ((ArrayList) this.composableItemViewHolderList).iterator();
        while (it.hasNext()) {
            ComposableViewHolder composableViewHolder = (ComposableViewHolder) it.next();
            composableViewHolder.bindData(viewData);
            composableViewHolder.onBind(this.itemView);
        }
    }

    @Override // androidx.picker.adapter.viewholder.PickerViewHolder
    public final void onViewRecycled() {
        super.onViewRecycled();
        Iterator it = ((ArrayList) this.composableItemViewHolderList).iterator();
        while (it.hasNext()) {
            ((ComposableViewHolder) it.next()).onViewRecycled(this.itemView);
        }
    }
}
