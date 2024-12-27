package com.android.systemui.controls.management.adapter;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageItemInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Prefs;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.adapter.SecAppAdapter;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecAppAdapter extends RecyclerView.Adapter {
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final BadgeProvider badgeProvider;
    public final Context context;
    public final ControlsUtil controlsUtil;
    public final SecFavoritesRenderer favoritesRenderer;
    public final boolean isOOBE;
    public final LayoutInflater layoutInflater;
    public List listOfServices;
    public final Function1 onAppSelected;
    public final SALogger saLogger;
    public final Function1 switchCallback;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SecHolder extends RecyclerView.ViewHolder {
        public final LinearLayout appInfoContainer;
        public final View badge;
        public final BadgeProvider badgeProvider;
        public final SecFavoritesRenderer favRenderer;
        public final TextView favorites;
        public final ImageView icon;
        public final boolean isOOBE;
        public final SwitchCompat onOff;
        public final LinearLayout onOffLayout;
        public final SALogger saLogger;
        public final Function1 switchCallback;
        public final View switchDivider;
        public final TextView title;

        public SecHolder(View view, SecFavoritesRenderer secFavoritesRenderer, Function1 function1, boolean z, SALogger sALogger, BadgeProvider badgeProvider) {
            super(view);
            this.favRenderer = secFavoritesRenderer;
            this.switchCallback = function1;
            this.isOOBE = z;
            this.saLogger = sALogger;
            this.badgeProvider = badgeProvider;
            this.icon = (ImageView) this.itemView.requireViewById(R.id.icon);
            this.title = (TextView) this.itemView.requireViewById(R.id.title);
            this.favorites = (TextView) this.itemView.requireViewById(com.android.systemui.R.id.favorites);
            this.onOff = (SwitchCompat) this.itemView.requireViewById(com.android.systemui.R.id.on_off_switch);
            this.onOffLayout = (LinearLayout) this.itemView.requireViewById(com.android.systemui.R.id.on_off_switch_layout);
            this.switchDivider = this.itemView.requireViewById(com.android.systemui.R.id.switch_divider);
            this.appInfoContainer = (LinearLayout) this.itemView.requireViewById(com.android.systemui.R.id.app_info_container);
            this.badge = this.itemView.requireViewById(com.android.systemui.R.id.badge);
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ SecAppAdapter(Executor executor, Executor executor2, Lifecycle lifecycle, ControlsListingController controlsListingController, LayoutInflater layoutInflater, Function1 function1, SecFavoritesRenderer secFavoritesRenderer, Context context, ControlsUtil controlsUtil, SALogger sALogger, BadgeProvider badgeProvider, AuthorizedPanelsRepository authorizedPanelsRepository, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(executor, executor2, lifecycle, controlsListingController, layoutInflater, (i & 32) != 0 ? new Function1() { // from class: com.android.systemui.controls.management.adapter.SecAppAdapter.1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Unit.INSTANCE;
            }
        } : function1, secFavoritesRenderer, context, controlsUtil, sALogger, badgeProvider, authorizedPanelsRepository, function12);
    }

    public static final void access$updateAuthorizedPanels(SecAppAdapter secAppAdapter, String str, boolean z) {
        Object obj;
        AuthorizedPanelsRepositoryImpl authorizedPanelsRepositoryImpl = (AuthorizedPanelsRepositoryImpl) secAppAdapter.authorizedPanelsRepository;
        Set<String> stringSet = authorizedPanelsRepositoryImpl.instantiateSharedPrefs(((UserTrackerImpl) authorizedPanelsRepositoryImpl.userTracker).getUserHandle()).getStringSet("authorized_panels", EmptySet.INSTANCE);
        Intrinsics.checkNotNull(stringSet);
        Iterator<T> it = stringSet.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (Intrinsics.areEqual((String) obj, str)) {
                    break;
                }
            }
        }
        String str2 = (String) obj;
        boolean z2 = false;
        if (str2 != null && str2.length() > 0) {
            z2 = true;
        }
        Log.d("SecAppAdapter", "updateAuthorizedPanels packageName = " + str + ", isAlreadyAdd = " + z2);
        if (z && !z2) {
            authorizedPanelsRepositoryImpl.addAuthorizedPanels(Collections.singleton(str));
            return;
        }
        if (z || !z2) {
            return;
        }
        Set singleton = Collections.singleton(str);
        SharedPreferences instantiateSharedPrefs = authorizedPanelsRepositoryImpl.instantiateSharedPrefs(((UserTrackerImpl) authorizedPanelsRepositoryImpl.userTracker).getUserHandle());
        Set<String> stringSet2 = instantiateSharedPrefs.getStringSet("authorized_panels", EmptySet.INSTANCE);
        Intrinsics.checkNotNull(stringSet2);
        instantiateSharedPrefs.edit().putStringSet("authorized_panels", SetsKt___SetsKt.minus((Set) stringSet2, (Iterable) singleton)).apply();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.listOfServices.size();
    }

    public final int getTotalFavoriteAndActiveAppCount() {
        List list = this.listOfServices;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
            ComponentName componentName = controlsServiceInfo.componentName;
            SecFavoritesRenderer secFavoritesRenderer = this.favoritesRenderer;
            if (((Number) secFavoritesRenderer.favoriteFunction.invoke(componentName)).intValue() > 0) {
                if (((Boolean) secFavoritesRenderer.getActiveFlag.invoke(controlsServiceInfo.componentName)).booleanValue()) {
                    arrayList.add(obj);
                }
            }
            if (controlsServiceInfo.panelActivity != null) {
                arrayList.add(obj);
            }
        }
        return arrayList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        String str;
        final SecHolder secHolder = (SecHolder) viewHolder;
        final ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) this.listOfServices.get(i);
        secHolder.icon.setImageDrawable(controlsServiceInfo.loadIcon());
        secHolder.title.setText(controlsServiceInfo.loadLabel());
        final boolean z = controlsServiceInfo.panelActivity != null;
        SecFavoritesRenderer secFavoritesRenderer = secHolder.favRenderer;
        if (z) {
            str = null;
        } else {
            int intValue = ((Number) secFavoritesRenderer.favoriteFunction.invoke(controlsServiceInfo.componentName)).intValue();
            if (intValue != 0) {
                str = secFavoritesRenderer.resources.getQuantityString(com.android.systemui.R.plurals.controls_item_number_of_favorites, intValue, Integer.valueOf(intValue));
                Intrinsics.checkNotNull(str);
            } else {
                str = secFavoritesRenderer.resources.getString(com.android.systemui.R.string.controls_no_items_favorites);
                Intrinsics.checkNotNull(str);
            }
        }
        secHolder.favorites.setText(str);
        secHolder.favorites.setVisibility(0);
        CharSequence loadLabel = controlsServiceInfo.loadLabel();
        SwitchCompat switchCompat = secHolder.onOff;
        switchCompat.setContentDescription(loadLabel);
        switchCompat.setEnabled(((Number) secFavoritesRenderer.favoriteFunction.invoke(controlsServiceInfo.componentName)).intValue() > 0 || z);
        boolean isEnabled = switchCompat.isEnabled();
        Function1 function1 = secFavoritesRenderer.getActiveFlag;
        switchCompat.setChecked(isEnabled && ((Boolean) function1.invoke(controlsServiceInfo.componentName)).booleanValue());
        Log.d("SecAppAdapter", "bindData isPanelType = " + z);
        final SecAppAdapter secAppAdapter = SecAppAdapter.this;
        if (z) {
            secHolder.switchDivider.setVisibility(8);
            int dimensionPixelSize = secAppAdapter.context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_provider_title_top_bottom_padding_for_panel);
            secHolder.appInfoContainer.setPaddingRelative(0, dimensionPixelSize, 0, dimensionPixelSize);
            secHolder.favorites.setVisibility(8);
        }
        if (!z) {
            if (((Number) secFavoritesRenderer.favoriteFunction.invoke(controlsServiceInfo.componentName)).intValue() == 0 && ((Boolean) function1.invoke(controlsServiceInfo.componentName)).booleanValue()) {
                secFavoritesRenderer.setActiveFlag.invoke(controlsServiceInfo.componentName, Boolean.FALSE);
            }
        }
        secHolder.onOffLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.adapter.SecAppAdapter$SecHolder$bindData$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (!SecAppAdapter.SecHolder.this.onOff.isEnabled()) {
                    SecAppAdapter.SecHolder.this.itemView.performClick();
                    return;
                }
                boolean z2 = !SecAppAdapter.SecHolder.this.onOff.isChecked();
                SecAppAdapter.SecHolder.this.onOff.setChecked(z2);
                if (z) {
                    SecAppAdapter.access$updateAuthorizedPanels(secAppAdapter, controlsServiceInfo.componentName.getPackageName(), z2);
                    SecAppAdapter.SecHolder.this.favRenderer.setActivePanelFlag.invoke(controlsServiceInfo.componentName, Boolean.valueOf(z2));
                } else {
                    SecAppAdapter.SecHolder.this.favRenderer.setActiveFlag.invoke(controlsServiceInfo.componentName, Boolean.valueOf(z2));
                }
                SecAppAdapter.SecHolder secHolder2 = SecAppAdapter.SecHolder.this;
                secHolder2.switchCallback.invoke(secHolder2.title.getText().toString());
                SecAppAdapter.SecHolder secHolder3 = SecAppAdapter.SecHolder.this;
                secHolder3.saLogger.sendEvent(secHolder3.isOOBE ? new SALogger.Event.ChooseAppOnOff(z2) : new SALogger.Event.ChooseAppOnOffOnManageApps(z2));
            }
        });
        ComponentName componentName = controlsServiceInfo.componentName;
        View view = secHolder.badge;
        BadgeProviderImpl badgeProviderImpl = (BadgeProviderImpl) secHolder.badgeProvider;
        if (badgeProviderImpl.badgeRequiredSet.contains(componentName.getPackageName())) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
        badgeProviderImpl.setDescription(controlsServiceInfo.componentName, secHolder.itemView, ((Object) secHolder.title.getText()) + ", " + ((Object) secHolder.favorites.getText()));
        secHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.adapter.SecAppAdapter$onBindViewHolder$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                String str2;
                if (((ControlsServiceInfo) SecAppAdapter.this.listOfServices.get(i)).panelActivity != null) {
                    SwitchCompat switchCompat2 = (SwitchCompat) secHolder.itemView.requireViewById(com.android.systemui.R.id.on_off_switch);
                    boolean z2 = !switchCompat2.isChecked();
                    switchCompat2.setChecked(z2);
                    SecAppAdapter secAppAdapter2 = SecAppAdapter.this;
                    SecAppAdapter.access$updateAuthorizedPanels(secAppAdapter2, ((ControlsServiceInfo) secAppAdapter2.listOfServices.get(i)).componentName.getPackageName(), z2);
                    SecAppAdapter secAppAdapter3 = SecAppAdapter.this;
                    secAppAdapter3.favoritesRenderer.setActivePanelFlag.invoke(((ControlsServiceInfo) secAppAdapter3.listOfServices.get(i)).componentName, Boolean.valueOf(z2));
                } else {
                    SecAppAdapter secAppAdapter4 = SecAppAdapter.this;
                    Function1 function12 = secAppAdapter4.onAppSelected;
                    ControlsServiceInfo controlsServiceInfo2 = (ControlsServiceInfo) secAppAdapter4.listOfServices.get(i);
                    ComponentName componentName2 = controlsServiceInfo2.componentName;
                    if (componentName2 != null) {
                        str2 = componentName2.flattenToString();
                    } else {
                        PackageItemInfo packageItemInfo = controlsServiceInfo2.packageItemInfo;
                        str2 = packageItemInfo != null ? packageItemInfo.packageName : null;
                    }
                    function12.invoke(ComponentName.unflattenFromString(str2));
                }
                SecAppAdapter secAppAdapter5 = SecAppAdapter.this;
                secAppAdapter5.saLogger.sendEvent(secAppAdapter5.isOOBE ? SALogger.Event.TapAppList.INSTANCE : SALogger.Event.TapAppListOnManageApps.INSTANCE);
            }
        });
        if (this.listOfServices.size() <= i + 1) {
            secHolder.itemView.requireViewById(com.android.systemui.R.id.list_divider).setVisibility(8);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        return new SecHolder(this.layoutInflater.inflate(com.android.systemui.R.layout.sec_controls_app_item, viewGroup, false), this.favoritesRenderer, this.switchCallback, this.isOOBE, this.saLogger, this.badgeProvider);
    }

    public SecAppAdapter(final Executor executor, final Executor executor2, Lifecycle lifecycle, ControlsListingController controlsListingController, LayoutInflater layoutInflater, Function1 function1, SecFavoritesRenderer secFavoritesRenderer, Context context, ControlsUtil controlsUtil, SALogger sALogger, BadgeProvider badgeProvider, AuthorizedPanelsRepository authorizedPanelsRepository, Function1 function12) {
        this.layoutInflater = layoutInflater;
        this.onAppSelected = function1;
        this.favoritesRenderer = secFavoritesRenderer;
        this.context = context;
        this.controlsUtil = controlsUtil;
        this.saLogger = sALogger;
        this.badgeProvider = badgeProvider;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
        this.switchCallback = function12;
        this.listOfServices = EmptyList.INSTANCE;
        controlsListingController.observe(lifecycle, new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.management.adapter.SecAppAdapter$callback$1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public final void onServicesUpdated(final List list) {
                Executor executor3 = executor;
                final SecAppAdapter secAppAdapter = this;
                final Executor executor4 = executor2;
                executor3.execute(new Runnable() { // from class: com.android.systemui.controls.management.adapter.SecAppAdapter$callback$1$onServicesUpdated$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecAppAdapter secAppAdapter2 = SecAppAdapter.this;
                        ControlsUtil controlsUtil2 = secAppAdapter2.controlsUtil;
                        Context context2 = secAppAdapter2.context;
                        List list2 = list;
                        controlsUtil2.getClass();
                        secAppAdapter2.listOfServices = ControlsUtil.getListOfServices(context2, list2);
                        ListPopupWindow$$ExternalSyntheticOutline0.m(SecAppAdapter.this.listOfServices.size(), "onServiceUpdated listOfServices = ", "SecAppAdapter");
                        Executor executor5 = executor4;
                        final SecAppAdapter secAppAdapter3 = SecAppAdapter.this;
                        executor5.execute(new Runnable() { // from class: com.android.systemui.controls.management.adapter.SecAppAdapter$callback$1$onServicesUpdated$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecAppAdapter.this.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });
        controlsUtil.getClass();
        this.isOOBE = !Prefs.get(context).getBoolean("ControlsOOBEManageAppsCompleted", false);
    }
}
