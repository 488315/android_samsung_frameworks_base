package com.android.internal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/* loaded from: classes6.dex */
public class AccountViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<AccountElements> mData;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public AccountViewAdapter(Context context, List<AccountElements> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mData.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.mData.get(i);
    }

    public void updateData(List<AccountElements> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        AccountItemView accountItemView;
        if (view == null) {
            accountItemView = new AccountItemView(this.mContext);
        } else {
            accountItemView = (AccountItemView) view;
        }
        accountItemView.setViewItem((AccountElements) getItem(i));
        return accountItemView;
    }

    public static class AccountElements {
        private Drawable mDrawable;
        private int mIcon;
        private String mName;
        private String mNumber;

        public AccountElements(int i, String str, String str2) {
            this(i, null, str, str2);
        }

        public AccountElements(Drawable drawable, String str, String str2) {
            this(0, drawable, str, str2);
        }

        private AccountElements(int i, Drawable drawable, String str, String str2) {
            this.mIcon = i;
            this.mDrawable = drawable;
            this.mName = str;
            this.mNumber = str2;
        }

        public int getIcon() {
            return this.mIcon;
        }

        public String getName() {
            return this.mName;
        }

        public String getNumber() {
            return this.mNumber;
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }
    }
}
