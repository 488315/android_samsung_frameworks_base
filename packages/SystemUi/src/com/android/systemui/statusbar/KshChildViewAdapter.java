package com.android.systemui.statusbar;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyboardShortcutInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.statusbar.model.KshData;
import com.android.systemui.statusbar.model.KshDataUtils;
import com.android.systemui.statusbar.model.StringDrawableContainer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KshChildViewAdapter extends RecyclerView.Adapter {
    public List mData;
    public final Typeface mDefaultFont;
    public final LayoutInflater mInflater;
    public KshData mKshData;
    public final KshDataUtils mKshDataUtils;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iconView;
        public final TextView keywordView;
        public final LinearLayout shortcutKeysContainer;

        public ViewHolder(KshChildViewAdapter kshChildViewAdapter, View view) {
            super(view);
            this.iconView = (ImageView) view.findViewById(R.id.keyboard_shortcuts_icon);
            this.keywordView = (TextView) view.findViewById(R.id.keyboard_shortcuts_keyword);
            this.shortcutKeysContainer = (LinearLayout) view.findViewById(R.id.keyboard_shortcuts_item_container);
        }
    }

    public KshChildViewAdapter(Context context, LayoutInflater layoutInflater) {
        this.mInflater = layoutInflater;
        this.mKshDataUtils = new KshDataUtils(context);
        try {
            this.mDefaultFont = Typeface.createFromFile("/system/fonts/OneUISans-VF.ttf");
        } catch (Exception unused) {
            Log.e("KshChildViewAdapter", "/system/fonts/OneUISans-VF.ttf is not enabled");
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mData.size();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00c5, code lost:
    
        if (r15 == null) goto L40;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        KeyboardShortcutInfo keyboardShortcutInfo = (KeyboardShortcutInfo) this.mData.get(i);
        Icon icon = keyboardShortcutInfo.getIcon();
        if (icon != null) {
            ImageView imageView = viewHolder2.iconView;
            imageView.setImageIcon(icon);
            if (this.mKshData.mDefaultIcons.containsKey(icon)) {
                imageView.setBackgroundResource(R.drawable.ksh_no_default_app_background);
            }
            imageView.setVisibility(0);
        }
        viewHolder2.keywordView.setText(keyboardShortcutInfo.getLabel());
        LinearLayout linearLayout = viewHolder2.shortcutKeysContainer;
        linearLayout.removeAllViews();
        KshData kshData = this.mKshData;
        KshDataUtils kshDataUtils = this.mKshDataUtils;
        kshDataUtils.getClass();
        ArrayList arrayList = new ArrayList();
        int modifiers = keyboardShortcutInfo.getModifiers();
        ArrayList arrayList2 = null;
        if (modifiers != 0) {
            SparseArray sparseArray = kshData.mModifierNames;
            SparseArray sparseArray2 = kshData.mModifierDrawables;
            int i2 = 0;
            while (true) {
                int[] iArr = kshDataUtils.mModifierList;
                if (i2 >= iArr.length) {
                    break;
                }
                int i3 = iArr[i2];
                if ((modifiers & i3) != 0) {
                    arrayList.add(new StringDrawableContainer((String) sparseArray.get(i3), (Drawable) sparseArray2.get(i3), null));
                    modifiers &= ~i3;
                }
                i2++;
            }
            if (modifiers != 0) {
                arrayList = null;
            }
        }
        if (arrayList != null) {
            int keycode = keyboardShortcutInfo.getKeycode();
            Drawable drawable = (Drawable) kshData.mSpecialCharacterDrawables.get(keycode);
            String str = (String) kshData.mSpecialCharacterDrawableDescriptions.get(keycode);
            String valueOf = keyboardShortcutInfo.getBaseCharacter() > 0 ? String.valueOf(keyboardShortcutInfo.getBaseCharacter()) : (String) kshData.mSpecialCharacterNames.get(keycode);
            if (valueOf == null) {
                if (keycode != 0) {
                    char displayLabel = kshData.mKeyCharacterMap.getDisplayLabel(keycode);
                    if (displayLabel != 0) {
                        valueOf = String.valueOf(displayLabel);
                    } else {
                        char displayLabel2 = kshData.mBackupKeyCharacterMap.getDisplayLabel(keycode);
                        valueOf = displayLabel2 != 0 ? String.valueOf(displayLabel2) : null;
                    }
                }
                arrayList2 = arrayList;
            }
            arrayList.add(new StringDrawableContainer(valueOf, drawable, str));
            arrayList2 = arrayList;
        }
        if (arrayList2 == null) {
            Log.w("KshChildViewAdapter", "Keyboard Shortcut contains unsupported keys, skipping.");
            return;
        }
        for (int i4 = 0; i4 < arrayList2.size(); i4++) {
            StringDrawableContainer stringDrawableContainer = (StringDrawableContainer) arrayList2.get(i4);
            Drawable drawable2 = stringDrawableContainer.mDrawable;
            LayoutInflater layoutInflater = this.mInflater;
            if (drawable2 != null) {
                ImageView imageView2 = (ImageView) layoutInflater.inflate(R.layout.samsung_keyboard_shortcuts_key_icon_view, (ViewGroup) linearLayout, false);
                imageView2.setImageDrawable(stringDrawableContainer.mDrawable);
                imageView2.setContentDescription(stringDrawableContainer.mDrawableDescription);
                linearLayout.addView(imageView2);
            } else {
                String str2 = stringDrawableContainer.mString;
                if (str2 != null) {
                    TextView textView = (TextView) layoutInflater.inflate(R.layout.samsung_keyboard_shortcuts_key_view, (ViewGroup) linearLayout, false);
                    Typeface typeface = this.mDefaultFont;
                    if (typeface != null) {
                        textView.setTypeface(typeface);
                    }
                    textView.setText(str2);
                    linearLayout.addView(textView);
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new ViewHolder(this, this.mInflater.inflate(R.layout.samsung_keyboard_shortcut_item_view, (ViewGroup) recyclerView, false));
    }
}
