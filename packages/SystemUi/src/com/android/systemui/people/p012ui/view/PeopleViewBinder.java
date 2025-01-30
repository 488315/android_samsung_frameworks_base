package com.android.systemui.people.p012ui.view;

import android.graphics.Outline;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.R;
import com.android.systemui.people.PeopleSpaceActivity$$ExternalSyntheticLambda0;
import com.android.systemui.people.PeopleSpaceTileView;
import com.android.systemui.people.p012ui.viewmodel.PeopleTileViewModel;
import com.android.systemui.people.p012ui.viewmodel.PeopleViewModel;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PeopleViewBinder {
    public static final PeopleViewBinder INSTANCE = new PeopleViewBinder();
    public static final PeopleViewBinder$ViewOutlineProvider$1 ViewOutlineProvider = new ViewOutlineProvider() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder$ViewOutlineProvider$1
        @Override // android.view.ViewOutlineProvider
        public final void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), view.getContext().getResources().getDimension(R.dimen.people_space_widget_radius));
        }
    };

    private PeopleViewBinder() {
    }

    public static final void bind(ViewGroup viewGroup, PeopleViewModel peopleViewModel, LifecycleOwner lifecycleOwner, PeopleSpaceActivity$$ExternalSyntheticLambda0 peopleSpaceActivity$$ExternalSyntheticLambda0) {
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new PeopleViewBinder$bind$1(lifecycleOwner, peopleViewModel, peopleSpaceActivity$$ExternalSyntheticLambda0, null), 3);
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new PeopleViewBinder$bind$2(lifecycleOwner, peopleViewModel, viewGroup, null), 3);
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new PeopleViewBinder$bind$3(lifecycleOwner, peopleViewModel, null), 3);
    }

    public static void setTileViews(View view, int i, int i2, List list, final Function1 function1) {
        ViewGroup viewGroup = (ViewGroup) view.requireViewById(i2);
        viewGroup.removeAllViews();
        viewGroup.setOutlineProvider(ViewOutlineProvider);
        LinearLayout linearLayout = (LinearLayout) view.requireViewById(i);
        if (list.isEmpty()) {
            linearLayout.setVisibility(8);
            return;
        }
        linearLayout.setVisibility(0);
        int i3 = 0;
        for (Object obj : list) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            final PeopleTileViewModel peopleTileViewModel = (PeopleTileViewModel) obj;
            PeopleSpaceTileView peopleSpaceTileView = new PeopleSpaceTileView(view.getContext(), viewGroup, peopleTileViewModel.key.mShortcutId, i3 == list.size() - 1);
            INSTANCE.getClass();
            try {
                peopleSpaceTileView.mNameView.setText(peopleTileViewModel.username);
                peopleSpaceTileView.mPersonIconView.setImageBitmap(peopleTileViewModel.icon);
                peopleSpaceTileView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder$bindTileView$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        Function1.this.invoke(peopleTileViewModel);
                    }
                });
            } catch (Exception e) {
                Log.e("PeopleViewBinder", "Couldn't retrieve shortcut information", e);
            }
            i3 = i4;
        }
    }
}
