package com.android.internal.globalactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;
import java.util.function.BooleanSupplier;

/* loaded from: classes5.dex */
public class ActionsAdapter extends BaseAdapter {
    private final Context mContext;
    private final BooleanSupplier mDeviceProvisioned;
    private final List<Action> mItems;
    private final BooleanSupplier mKeyguardShowing;

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public ActionsAdapter(Context context, List<Action> list, BooleanSupplier booleanSupplier, BooleanSupplier booleanSupplier2) {
        this.mContext = context;
        this.mItems = list;
        this.mDeviceProvisioned = booleanSupplier;
        this.mKeyguardShowing = booleanSupplier2;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        boolean asBoolean = this.mKeyguardShowing.getAsBoolean();
        boolean asBoolean2 = this.mDeviceProvisioned.getAsBoolean();
        int i = 0;
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            Action action = this.mItems.get(i2);
            if ((!asBoolean || action.showDuringKeyguard()) && (asBoolean2 || action.showBeforeProvisioning())) {
                i++;
            }
        }
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return getItem(i).isEnabled();
    }

    @Override // android.widget.Adapter
    public Action getItem(int i) {
        boolean asBoolean = this.mKeyguardShowing.getAsBoolean();
        boolean asBoolean2 = this.mDeviceProvisioned.getAsBoolean();
        int i2 = 0;
        for (int i3 = 0; i3 < this.mItems.size(); i3++) {
            Action action = this.mItems.get(i3);
            if ((!asBoolean || action.showDuringKeyguard()) && (asBoolean2 || action.showBeforeProvisioning())) {
                if (i2 == i) {
                    return action;
                }
                i2++;
            }
        }
        throw new IllegalArgumentException("position " + i + " out of range of showable actions, filtered count=" + getCount() + ", keyguardshowing=" + asBoolean + ", provisioned=" + asBoolean2);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Action item = getItem(i);
        Context context = this.mContext;
        return item.create(context, view, viewGroup, LayoutInflater.from(context));
    }
}
