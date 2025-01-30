package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.IntentButtonProvider;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.statusbar.phone.ExpandableIndicator;
import com.android.systemui.tuner.LockscreenFragment;
import com.android.systemui.tuner.ShortcutParser;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class LockscreenFragment extends PreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList mTunables = new ArrayList();
    public TunerService mTunerService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ActivityButton implements IntentButtonProvider.IntentButton {
        public final IntentButtonProvider.IntentButton.IconState mIconState;
        public final Intent mIntent;

        public ActivityButton(Context context, ActivityInfo activityInfo) {
            this.mIntent = new Intent().setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
            IntentButtonProvider.IntentButton.IconState iconState = new IntentButtonProvider.IntentButton.IconState();
            this.mIconState = iconState;
            iconState.isVisible = true;
            iconState.drawable = activityInfo.loadIcon(context.getPackageManager()).mutate();
            iconState.contentDescription = activityInfo.loadLabel(context.getPackageManager());
            int applyDimension = (int) TypedValue.applyDimension(1, 32.0f, context.getResources().getDisplayMetrics());
            iconState.drawable = new ScalingDrawableWrapper(iconState.drawable, applyDimension / r6.getIntrinsicWidth());
            iconState.tint = false;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public final IntentButtonProvider.IntentButton.IconState getIcon() {
            return this.mIconState;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public final Intent getIntent() {
            return this.mIntent;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Adapter extends RecyclerView.Adapter {
        public final Consumer mCallback;
        public final ArrayList mItems = new ArrayList();

        public Adapter(Context context, Consumer<Item> consumer) {
            this.mCallback = consumer;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.mItems.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            final Holder holder = (Holder) viewHolder;
            Item item = (Item) this.mItems.get(i);
            holder.icon.setImageDrawable(item.getDrawable());
            holder.title.setText(item.getLabel());
            final int i2 = 0;
            holder.itemView.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.tuner.LockscreenFragment$Adapter$$ExternalSyntheticLambda0
                public final /* synthetic */ LockscreenFragment.Adapter f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i2) {
                        case 0:
                            LockscreenFragment.Adapter adapter = this.f$0;
                            adapter.mCallback.accept((LockscreenFragment.Item) adapter.mItems.get(holder.getBindingAdapterPosition()));
                            break;
                        default:
                            LockscreenFragment.Adapter adapter2 = this.f$0;
                            ((LockscreenFragment.Item) adapter2.mItems.get(holder.getBindingAdapterPosition())).toggleExpando(adapter2);
                            break;
                    }
                }
            });
            Boolean expando = item.getExpando();
            ExpandableIndicator expandableIndicator = holder.expand;
            if (expando == null) {
                expandableIndicator.setVisibility(8);
                return;
            }
            expandableIndicator.setVisibility(0);
            boolean booleanValue = expando.booleanValue();
            if (booleanValue != expandableIndicator.mExpanded) {
                expandableIndicator.mExpanded = booleanValue;
                boolean z = !booleanValue;
                boolean z2 = expandableIndicator.mIsDefaultDirection;
                int i3 = R.drawable.ic_volume_expand_animation;
                if (!z2 ? !z : z) {
                    i3 = R.drawable.ic_volume_collapse_animation;
                }
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) expandableIndicator.getContext().getDrawable(i3).getConstantState().newDrawable();
                expandableIndicator.setImageDrawable(animatedVectorDrawable);
                animatedVectorDrawable.forceAnimationOnUI();
                animatedVectorDrawable.start();
                expandableIndicator.setContentDescription(expandableIndicator.getContentDescription(booleanValue));
            }
            final int i4 = 1;
            expandableIndicator.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.tuner.LockscreenFragment$Adapter$$ExternalSyntheticLambda0
                public final /* synthetic */ LockscreenFragment.Adapter f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i4) {
                        case 0:
                            LockscreenFragment.Adapter adapter = this.f$0;
                            adapter.mCallback.accept((LockscreenFragment.Item) adapter.mItems.get(holder.getBindingAdapterPosition()));
                            break;
                        default:
                            LockscreenFragment.Adapter adapter2 = this.f$0;
                            ((LockscreenFragment.Item) adapter2.mItems.get(holder.getBindingAdapterPosition())).toggleExpando(adapter2);
                            break;
                    }
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            return new Holder(LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.tuner_shortcut_item, (ViewGroup) recyclerView, false));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class App extends Item {
        public final ArrayList mChildren;
        public final Context mContext;
        public boolean mExpanded;
        public final LauncherActivityInfo mInfo;

        public App(Context context, LauncherActivityInfo launcherActivityInfo) {
            super(0);
            this.mChildren = new ArrayList();
            this.mContext = context;
            this.mInfo = launcherActivityInfo;
            this.mExpanded = false;
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public final Drawable getDrawable() {
            return this.mInfo.getBadgedIcon(this.mContext.getResources().getConfiguration().densityDpi);
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public final Boolean getExpando() {
            if (this.mChildren.size() != 0) {
                return Boolean.valueOf(this.mExpanded);
            }
            return null;
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public final String getLabel() {
            return this.mInfo.getLabel().toString();
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public final void toggleExpando(final Adapter adapter) {
            boolean z = !this.mExpanded;
            this.mExpanded = z;
            ArrayList arrayList = this.mChildren;
            if (z) {
                arrayList.forEach(new Consumer() { // from class: com.android.systemui.tuner.LockscreenFragment$App$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        LockscreenFragment.App app = LockscreenFragment.App.this;
                        LockscreenFragment.Adapter adapter2 = adapter;
                        app.getClass();
                        ArrayList arrayList2 = adapter2.mItems;
                        int indexOf = arrayList2.indexOf(app) + 1;
                        arrayList2.add(indexOf, (LockscreenFragment.Item) obj);
                        adapter2.notifyItemInserted(indexOf);
                    }
                });
            } else {
                arrayList.forEach(new LockscreenFragment$$ExternalSyntheticLambda0(adapter, 1));
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Holder extends RecyclerView.ViewHolder {
        public final ExpandableIndicator expand;
        public final ImageView icon;
        public final TextView title;

        public Holder(View view) {
            super(view);
            this.icon = (ImageView) view.findViewById(android.R.id.icon);
            this.title = (TextView) view.findViewById(android.R.id.title);
            this.expand = (ExpandableIndicator) view.findViewById(R.id.expand);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class LockButtonFactory {
        public LockButtonFactory(Context context, String str) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ShortcutButton implements IntentButtonProvider.IntentButton {
        public final IntentButtonProvider.IntentButton.IconState mIconState;
        public final ShortcutParser.Shortcut mShortcut;

        public ShortcutButton(Context context, ShortcutParser.Shortcut shortcut) {
            this.mShortcut = shortcut;
            IntentButtonProvider.IntentButton.IconState iconState = new IntentButtonProvider.IntentButton.IconState();
            this.mIconState = iconState;
            iconState.isVisible = true;
            iconState.drawable = shortcut.icon.loadDrawable(context).mutate();
            iconState.contentDescription = shortcut.label;
            int applyDimension = (int) TypedValue.applyDimension(1, 32.0f, context.getResources().getDisplayMetrics());
            iconState.drawable = new ScalingDrawableWrapper(iconState.drawable, applyDimension / r4.getIntrinsicWidth());
            iconState.tint = false;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public final IntentButtonProvider.IntentButton.IconState getIcon() {
            return this.mIconState;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public final Intent getIntent() {
            return this.mShortcut.intent;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class StaticShortcut extends Item {
        public final Context mContext;
        public final ShortcutParser.Shortcut mShortcut;

        public StaticShortcut(Context context, ShortcutParser.Shortcut shortcut) {
            super(0);
            this.mContext = context;
            this.mShortcut = shortcut;
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public final Drawable getDrawable() {
            return this.mShortcut.icon.loadDrawable(this.mContext);
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public final Boolean getExpando() {
            return null;
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public final String getLabel() {
            return this.mShortcut.label;
        }
    }

    @Override // androidx.preference.PreferenceFragment
    public final void onCreatePreferences(String str) {
        this.mTunerService = (TunerService) Dependency.get(TunerService.class);
        new Handler();
        addPreferencesFromResource(R.xml.lockscreen_settings);
        setupGroup("sysui_keyguard_left", "sysui_keyguard_left_unlock");
        setupGroup("sysui_keyguard_right", "sysui_keyguard_right_unlock");
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mTunables.forEach(new LockscreenFragment$$ExternalSyntheticLambda0(this, 0));
    }

    public final void setupGroup(String str, String str2) {
        final Preference findPreference = findPreference(str);
        final SwitchPreference switchPreference = (SwitchPreference) findPreference(str2);
        TunerService.Tunable tunable = new TunerService.Tunable() { // from class: com.android.systemui.tuner.LockscreenFragment$$ExternalSyntheticLambda1
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str3, String str4) {
                ActivityInfo activityInfo;
                ShortcutParser.Shortcut shortcut;
                int i = LockscreenFragment.$r8$clinit;
                LockscreenFragment lockscreenFragment = LockscreenFragment.this;
                lockscreenFragment.getClass();
                switchPreference.setVisible(!TextUtils.isEmpty(str4));
                Preference preference = findPreference;
                if (str4 == null) {
                    preference.setSummary$1();
                    return;
                }
                if (!str4.contains("::")) {
                    if (!str4.contains("/")) {
                        preference.setSummary$1();
                        return;
                    }
                    try {
                        activityInfo = lockscreenFragment.getContext().getPackageManager().getActivityInfo(ComponentName.unflattenFromString(str4), 0);
                    } catch (PackageManager.NameNotFoundException unused) {
                        activityInfo = null;
                    }
                    preference.setSummary(activityInfo != null ? activityInfo.loadLabel(lockscreenFragment.getContext().getPackageManager()) : null);
                    return;
                }
                Context context = lockscreenFragment.getContext();
                String[] split = str4.split("::");
                try {
                    Iterator it = ((ArrayList) new ShortcutParser(context, new ComponentName(split[0], split[1])).getShortcuts()).iterator();
                    while (it.hasNext()) {
                        shortcut = (ShortcutParser.Shortcut) it.next();
                        if (shortcut.f363id.equals(split[2])) {
                            break;
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused2) {
                }
                shortcut = null;
                preference.setSummary(shortcut != null ? shortcut.label : null);
            }
        };
        this.mTunables.add(tunable);
        this.mTunerService.addTunable(tunable, str);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Item {
        private Item() {
        }

        public /* synthetic */ Item(int i) {
            this();
        }

        public abstract Drawable getDrawable();

        public abstract Boolean getExpando();

        public abstract String getLabel();

        public void toggleExpando(Adapter adapter) {
        }
    }
}
