package com.samsung.android.globalactions.presentation.viewmodel;

/* loaded from: classes6.dex */
public class ActionInfo {
    private String mName = "";
    private int mIconResId = -1;
    private ViewType mViewType = ViewType.CENTER_ICON_1P_VIEW;
    private String mLabel = "";
    private String mDescription = "";
    private int mViewIndex = -1;
    private String mStateLabel = "";

    public void setName(String str) {
        this.mName = str;
    }

    public String getName() {
        return this.mName;
    }

    public void setIcon(int i) {
        this.mIconResId = i;
    }

    public int getIcon() {
        return this.mIconResId;
    }

    public void setLabel(String str) {
        this.mLabel = str;
    }

    public String getLabel() {
        return this.mLabel;
    }

    public void setViewType(ViewType viewType) {
        this.mViewType = viewType;
    }

    public ViewType getViewType() {
        return this.mViewType;
    }

    public void setDescription(String str) {
        this.mDescription = str;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setViewIndex(int i) {
        this.mViewIndex = i;
    }

    public int getViewIndex() {
        return this.mViewIndex;
    }

    public void setStateLabel(String str) {
        this.mStateLabel = str;
    }

    public String getStateLabel() {
        return this.mStateLabel;
    }
}
