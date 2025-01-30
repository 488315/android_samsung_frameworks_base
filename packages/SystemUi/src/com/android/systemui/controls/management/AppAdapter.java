package com.android.systemui.controls.management;

import android.R;
import android.content.ComponentName;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.util.PluralMessageFormaterKt;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppAdapter extends RecyclerView.Adapter {
    public final Set authorizedPanels;
    public final FavoritesRenderer favoritesRenderer;
    public final LayoutInflater layoutInflater;
    public List listOfServices;
    public final Function1 onAppSelected;
    public final Resources resources;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Holder extends RecyclerView.ViewHolder {
        public final FavoritesRenderer favRenderer;
        public final TextView favorites;
        public final ImageView icon;
        public final TextView title;
        public final View view;

        public Holder(View view, FavoritesRenderer favoritesRenderer) {
            super(view);
            this.favRenderer = favoritesRenderer;
            View view2 = this.itemView;
            this.view = view2;
            this.icon = (ImageView) view2.requireViewById(R.id.icon);
            this.title = (TextView) this.itemView.requireViewById(R.id.title);
            this.favorites = (TextView) this.itemView.requireViewById(com.android.systemui.R.id.favorites);
        }
    }

    public AppAdapter(Executor executor, Executor executor2, Lifecycle lifecycle, ControlsListingController controlsListingController, LayoutInflater layoutInflater, Function1 function1, FavoritesRenderer favoritesRenderer, Resources resources, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(executor, executor2, lifecycle, controlsListingController, layoutInflater, (i & 32) != 0 ? new Function1() { // from class: com.android.systemui.controls.management.AppAdapter.1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Unit.INSTANCE;
            }
        } : function1, favoritesRenderer, resources, (i & 256) != 0 ? EmptySet.INSTANCE : set);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.listOfServices.size();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0044  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        String str;
        Holder holder = (Holder) viewHolder;
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) this.listOfServices.get(i);
        holder.icon.setImageDrawable(controlsServiceInfo.loadIcon());
        holder.title.setText(controlsServiceInfo.loadLabel());
        if (controlsServiceInfo.panelActivity == null) {
            FavoritesRenderer favoritesRenderer = holder.favRenderer;
            int intValue = ((Number) favoritesRenderer.favoriteFunction.invoke(controlsServiceInfo.componentName)).intValue();
            if (intValue != 0) {
                str = PluralMessageFormaterKt.icuMessageFormat(favoritesRenderer.resources, com.android.systemui.R.string.controls_number_of_favorites, intValue);
                TextView textView = holder.favorites;
                textView.setText(str);
                textView.setVisibility(str != null ? 8 : 0);
                holder.view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.AppAdapter$onBindViewHolder$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AppAdapter appAdapter = AppAdapter.this;
                        appAdapter.onAppSelected.invoke(appAdapter.listOfServices.get(i));
                    }
                });
            }
        }
        str = null;
        TextView textView2 = holder.favorites;
        textView2.setText(str);
        textView2.setVisibility(str != null ? 8 : 0);
        holder.view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.AppAdapter$onBindViewHolder$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppAdapter appAdapter = AppAdapter.this;
                appAdapter.onAppSelected.invoke(appAdapter.listOfServices.get(i));
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new Holder(this.layoutInflater.inflate(com.android.systemui.R.layout.controls_app_item, (ViewGroup) recyclerView, false), this.favoritesRenderer);
    }

    public AppAdapter(final Executor executor, final Executor executor2, Lifecycle lifecycle, ControlsListingController controlsListingController, LayoutInflater layoutInflater, Function1 function1, FavoritesRenderer favoritesRenderer, Resources resources, Set<String> set) {
        this.layoutInflater = layoutInflater;
        this.onAppSelected = function1;
        this.favoritesRenderer = favoritesRenderer;
        this.resources = resources;
        this.authorizedPanels = set;
        this.listOfServices = EmptyList.INSTANCE;
        controlsListingController.observe(lifecycle, new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.management.AppAdapter$callback$1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public final void onServicesUpdated(final List list) {
                final AppAdapter appAdapter = this;
                final Executor executor3 = executor2;
                executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1
                    /* JADX WARN: Removed duplicated region for block: B:13:0x004e A[SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:17:0x0027 A[SYNTHETIC] */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        boolean z;
                        final Collator collator = Collator.getInstance(AppAdapter.this.resources.getConfiguration().getLocales().get(0));
                        Comparator comparator = new Comparator() { // from class: com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1$run$$inlined$compareBy$1
                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                return collator.compare(((ControlsServiceInfo) obj).loadLabel(), ((ControlsServiceInfo) obj2).loadLabel());
                            }
                        };
                        AppAdapter appAdapter2 = AppAdapter.this;
                        List list2 = list;
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : list2) {
                            ComponentName componentName = ((ControlsServiceInfo) obj).panelActivity;
                            if (componentName != null) {
                                if (CollectionsKt___CollectionsKt.contains(appAdapter2.authorizedPanels, componentName != null ? componentName.getPackageName() : null)) {
                                    z = false;
                                    if (!z) {
                                        arrayList.add(obj);
                                    }
                                }
                            }
                            z = true;
                            if (!z) {
                            }
                        }
                        appAdapter2.listOfServices = CollectionsKt___CollectionsKt.sortedWith(arrayList, comparator);
                        Executor executor4 = executor3;
                        final AppAdapter appAdapter3 = AppAdapter.this;
                        executor4.execute(new Runnable() { // from class: com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppAdapter.this.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });
    }
}
