package com.android.internal.app;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.ChooserTargetActionsDialogFragment;
import com.android.internal.app.ResolverListAdapter;
import com.android.internal.app.chooser.DisplayResolveInfo;
import com.android.internal.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes5.dex */
public class ChooserTargetActionsDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    public static final String INTENT_FILTER_KEY = "intent_filter";
    public static final String IS_SHORTCUT_PINNED_KEY = "is_shortcut_pinned";
    public static final String SHORTCUT_ID_KEY = "shortcut_id";
    public static final String SHORTCUT_TITLE_KEY = "shortcut_title";
    public static final String TARGET_INFOS_KEY = "target_infos";
    public static final String USER_HANDLE_KEY = "user_handle";
    protected IntentFilter mIntentFilter;
    protected boolean mIsShortcutPinned;
    protected String mShortcutId;
    protected String mShortcutTitle;
    protected ArrayList<DisplayResolveInfo> mTargetInfos = new ArrayList<>();
    protected UserHandle mUserHandle;

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            setStateFromBundle(bundle);
        } else {
            setStateFromBundle(getArguments());
        }
    }

    void setStateFromBundle(Bundle bundle) {
        this.mTargetInfos = (ArrayList) bundle.get(TARGET_INFOS_KEY);
        this.mUserHandle = (UserHandle) bundle.get("user_handle");
        this.mShortcutId = bundle.getString(SHORTCUT_ID_KEY);
        this.mShortcutTitle = bundle.getString(SHORTCUT_TITLE_KEY);
        this.mIsShortcutPinned = bundle.getBoolean(IS_SHORTCUT_PINNED_KEY);
        this.mIntentFilter = (IntentFilter) bundle.get("intent_filter");
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("user_handle", this.mUserHandle);
        bundle.putParcelableArrayList(TARGET_INFOS_KEY, this.mTargetInfos);
        bundle.putString(SHORTCUT_ID_KEY, this.mShortcutId);
        bundle.putBoolean(IS_SHORTCUT_PINNED_KEY, this.mIsShortcutPinned);
        bundle.putString(SHORTCUT_TITLE_KEY, this.mShortcutTitle);
        bundle.putParcelable("intent_filter", this.mIntentFilter);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (bundle != null) {
            setStateFromBundle(bundle);
        } else {
            setStateFromBundle(getArguments());
        }
        Optional.of(getDialog()).map(new Function() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Dialog) obj).getWindow();
            }
        }).ifPresent(new Consumer() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Window) obj).setBackgroundDrawable(new ColorDrawable(0));
            }
        });
        List list = (List) this.mTargetInfos.stream().map(new Function() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Pair lambda$onCreateView$1;
                lambda$onCreateView$1 = ChooserTargetActionsDialogFragment.this.lambda$onCreateView$1((DisplayResolveInfo) obj);
                return lambda$onCreateView$1;
            }
        }).collect(Collectors.toList());
        View inflate = layoutInflater.inflate(R.layout.chooser_dialog, viewGroup, false);
        TextView textView = (TextView) inflate.findViewById(16908310);
        ImageView imageView = (ImageView) inflate.findViewById(16908294);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.listContainer);
        ResolverListAdapter.ResolveInfoPresentationGetter providingAppPresentationGetter = getProvidingAppPresentationGetter();
        textView.lambda$setTextAsync$0(isShortcutTarget() ? this.mShortcutTitle : providingAppPresentationGetter.getLabel());
        imageView.lambda$setImageURIAsync$0(providingAppPresentationGetter.getIcon(this.mUserHandle));
        recyclerView.setAdapter(new VHAdapter(list));
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Pair lambda$onCreateView$1(DisplayResolveInfo displayResolveInfo) {
        return new Pair(getItemIcon(displayResolveInfo), getItemLabel(displayResolveInfo));
    }

    class VHAdapter extends RecyclerView.Adapter<VH> {
        List<Pair<Drawable, CharSequence>> mItems;

        VHAdapter(List<Pair<Drawable, CharSequence>> list) {
            this.mItems = list;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.widget.RecyclerView.Adapter
        public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
            return ChooserTargetActionsDialogFragment.this.new VH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chooser_dialog_item, viewGroup, false));
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public void onBindViewHolder(VH vh, int i) {
            vh.bind(this.mItems.get(i), i);
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mItems.size();
        }
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mLabel;

        VH(View view) {
            super(view);
            this.mLabel = (TextView) view.findViewById(R.id.text);
            this.mIcon = (ImageView) view.findViewById(16908294);
        }

        public void bind(Pair<Drawable, CharSequence> pair, final int i) {
            this.mLabel.lambda$setTextAsync$0(pair.second);
            if (pair.first == null) {
                this.mIcon.setVisibility(8);
            } else {
                this.mIcon.setVisibility(0);
                this.mIcon.lambda$setImageURIAsync$0(pair.first);
            }
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$VH$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChooserTargetActionsDialogFragment.VH.this.lambda$bind$0(i, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$bind$0(int i, View view) {
            ChooserTargetActionsDialogFragment chooserTargetActionsDialogFragment = ChooserTargetActionsDialogFragment.this;
            chooserTargetActionsDialogFragment.onClick(chooserTargetActionsDialogFragment.getDialog(), i);
        }
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (isShortcutTarget()) {
            toggleShortcutPinned(this.mTargetInfos.get(i).getResolvedComponentName());
        } else {
            pinComponent(this.mTargetInfos.get(i).getResolvedComponentName());
        }
        ((ChooserActivity) getActivity()).handlePackagesChanged();
        dismiss();
    }

    private void toggleShortcutPinned(ComponentName componentName) {
        if (this.mIntentFilter == null) {
            return;
        }
        List<String> pinnedShortcutsFromPackageAsUser = getPinnedShortcutsFromPackageAsUser(getContext(), this.mUserHandle, this.mIntentFilter, componentName.getPackageName());
        if (this.mIsShortcutPinned) {
            pinnedShortcutsFromPackageAsUser.remove(this.mShortcutId);
        } else {
            pinnedShortcutsFromPackageAsUser.add(this.mShortcutId);
        }
        ((LauncherApps) getContext().getSystemService(LauncherApps.class)).pinShortcuts(componentName.getPackageName(), pinnedShortcutsFromPackageAsUser, this.mUserHandle);
    }

    private static List<String> getPinnedShortcutsFromPackageAsUser(Context context, UserHandle userHandle, IntentFilter intentFilter, final String str) {
        return (List) ((ShortcutManager) context.createContextAsUser(userHandle, 0).getSystemService(ShortcutManager.class)).getShareTargets(intentFilter).stream().map(new Function() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ShortcutManager.ShareShortcutInfo) obj).getShortcutInfo();
            }
        }).filter(new Predicate() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ChooserTargetActionsDialogFragment.lambda$getPinnedShortcutsFromPackageAsUser$2(str, (ShortcutInfo) obj);
            }
        }).map(new Function() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ShortcutInfo) obj).getId();
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$getPinnedShortcutsFromPackageAsUser$2(String str, ShortcutInfo shortcutInfo) {
        return shortcutInfo.isPinned() && shortcutInfo.getPackage().equals(str);
    }

    private void pinComponent(ComponentName componentName) {
        SharedPreferences pinnedSharedPrefs = ChooserActivity.getPinnedSharedPrefs(getContext());
        String flattenToString = componentName.flattenToString();
        if (pinnedSharedPrefs.getBoolean(componentName.flattenToString(), false)) {
            pinnedSharedPrefs.edit().remove(flattenToString).apply();
        } else {
            pinnedSharedPrefs.edit().putBoolean(flattenToString, true).apply();
        }
    }

    private Drawable getPinIcon(boolean z) {
        if (z) {
            return getContext().getDrawable(R.drawable.ic_close);
        }
        return getContext().getDrawable(R.drawable.ic_chooser_pin_dialog);
    }

    private CharSequence getPinLabel(boolean z, CharSequence charSequence) {
        if (z) {
            return getResources().getString(R.string.unpin_specific_target, charSequence);
        }
        return getResources().getString(R.string.pin_specific_target, charSequence);
    }

    protected CharSequence getItemLabel(DisplayResolveInfo displayResolveInfo) {
        return getPinLabel(isPinned(displayResolveInfo), isShortcutTarget() ? this.mShortcutTitle : displayResolveInfo.getResolveInfo().loadLabel(getContext().getPackageManager()));
    }

    protected Drawable getItemIcon(DisplayResolveInfo displayResolveInfo) {
        return getPinIcon(isPinned(displayResolveInfo));
    }

    private ResolverListAdapter.ResolveInfoPresentationGetter getProvidingAppPresentationGetter() {
        return new ResolverListAdapter.ResolveInfoPresentationGetter(getContext(), ((ActivityManager) getContext().getSystemService("activity")).getLauncherLargeIconDensity(), this.mTargetInfos.get(0).getResolveInfo());
    }

    private boolean isPinned(DisplayResolveInfo displayResolveInfo) {
        return isShortcutTarget() ? this.mIsShortcutPinned : displayResolveInfo.isPinned();
    }

    private boolean isShortcutTarget() {
        return this.mShortcutId != null;
    }
}
