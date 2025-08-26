package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import androidx.appcompat.content.res.AppCompatResources;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ExpandButton extends Preference {
    public final long mId;

    public ExpandButton(Context context, List<Preference> list, long j) {
        super(context);
        this.mLayoutResId = R.layout.expand_button;
        setIcon(AppCompatResources.getDrawable(R.drawable.ic_arrow_down_24dp, this.mContext));
        this.mIconResId = R.drawable.ic_arrow_down_24dp;
        setTitle(R.string.expand_button_title);
        setOrder(999);
        ArrayList arrayList = new ArrayList();
        CharSequence string = null;
        for (Preference preference : list) {
            CharSequence title = preference.getTitle();
            boolean z = preference instanceof PreferenceGroup;
            if (z && !TextUtils.isEmpty(title)) {
                arrayList.add((PreferenceGroup) preference);
            }
            if (arrayList.contains(preference.mParentGroup)) {
                if (z) {
                    arrayList.add((PreferenceGroup) preference);
                }
            } else if (!TextUtils.isEmpty(title)) {
                string = string == null ? title : this.mContext.getString(R.string.summary_collapsed_preference_list, string, title);
            }
        }
        setSummary(string);
        this.mId = j + 1000000;
    }

    @Override // androidx.preference.Preference
    public final long getId() {
        return this.mId;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
    }
}
