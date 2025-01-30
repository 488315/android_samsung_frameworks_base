package com.samsung.android.sdk.command.template;

import android.os.Bundle;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SingleChoiceTemplate extends CommandTemplate {
    public final String mCurrentActiveValue;
    public final List mEntries;
    public final ArrayList mEntryImageList;
    public final ArrayList mEntryPrimaryTitleList;
    public final ArrayList mEntrySecondaryTitleList;
    public final ArrayList mEntryValueList;

    public SingleChoiceTemplate(String str, List<Entry> list) {
        super("singlechoice");
        this.mEntryPrimaryTitleList = new ArrayList();
        this.mEntrySecondaryTitleList = new ArrayList();
        this.mEntryValueList = new ArrayList();
        this.mEntryImageList = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.mEntries = arrayList;
        this.mCurrentActiveValue = str;
        if (list == null || list.size() <= 0) {
            return;
        }
        arrayList.addAll(list);
        for (Entry entry : list) {
            String str2 = entry.mPrimaryTitle;
            if (!TextUtils.isEmpty(str2)) {
                this.mEntryPrimaryTitleList.add(str2);
            }
            String str3 = entry.mSecondaryTitle;
            if (!TextUtils.isEmpty(str3)) {
                this.mEntrySecondaryTitleList.add(str3);
            }
            int i = entry.mIconResId;
            if (i > 0) {
                this.mEntryImageList.add(Integer.valueOf(i));
            }
            String str4 = entry.mValue;
            if (!TextUtils.isEmpty(str4)) {
                this.mEntryValueList.add(str4);
            }
        }
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putString("key_current_active_mode_value", this.mCurrentActiveValue);
        dataBundle.putStringArrayList("key_entry_primary_title_list", this.mEntryPrimaryTitleList);
        dataBundle.putStringArrayList("key_entry_secondary_title_list", this.mEntrySecondaryTitleList);
        dataBundle.putStringArrayList("key_entry_value_list", this.mEntryValueList);
        dataBundle.putIntegerArrayList("key_entry_image_list", this.mEntryImageList);
        return dataBundle;
    }

    @Override // com.samsung.android.sdk.command.template.CommandTemplate
    public final int getTemplateType() {
        return 5;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Entry {
        public final int mIconResId;
        public final String mPrimaryTitle;
        public final String mSecondaryTitle;
        public final String mValue;

        public Entry(String str, String str2) {
            this.mPrimaryTitle = str;
            this.mValue = str2;
        }

        public Entry(String str, String str2, int i, String str3) {
            this.mPrimaryTitle = str;
            this.mSecondaryTitle = str2;
            this.mIconResId = i;
            this.mValue = str3;
        }
    }

    public SingleChoiceTemplate(Bundle bundle) {
        super(bundle);
        this.mEntryPrimaryTitleList = new ArrayList();
        this.mEntrySecondaryTitleList = new ArrayList();
        this.mEntryValueList = new ArrayList();
        this.mEntryImageList = new ArrayList();
        this.mEntries = new ArrayList();
        this.mCurrentActiveValue = bundle.getString("key_current_active_mode_value");
        ArrayList<String> stringArrayList = bundle.getStringArrayList("key_entry_primary_title_list");
        this.mEntryPrimaryTitleList = stringArrayList;
        this.mEntrySecondaryTitleList = bundle.getStringArrayList("key_entry_secondary_title_list");
        this.mEntryValueList = bundle.getStringArrayList("key_entry_value_list");
        this.mEntryImageList = bundle.getIntegerArrayList("key_entry_image_list");
        if (stringArrayList != null) {
            int i = 0;
            while (i < this.mEntryPrimaryTitleList.size()) {
                String str = (String) this.mEntryPrimaryTitleList.get(i);
                ArrayList arrayList = this.mEntrySecondaryTitleList;
                String str2 = null;
                String str3 = (arrayList == null || arrayList.size() <= i) ? null : (String) this.mEntrySecondaryTitleList.get(i);
                ArrayList arrayList2 = this.mEntryImageList;
                int intValue = (arrayList2 == null || arrayList2.size() <= i) ? 0 : ((Integer) this.mEntryImageList.get(i)).intValue();
                ArrayList arrayList3 = this.mEntryValueList;
                if (arrayList3 != null && arrayList3.size() > i) {
                    str2 = (String) this.mEntryValueList.get(i);
                }
                this.mEntries.add(new Entry(str, str3, intValue, str2));
                i++;
            }
        }
    }
}
