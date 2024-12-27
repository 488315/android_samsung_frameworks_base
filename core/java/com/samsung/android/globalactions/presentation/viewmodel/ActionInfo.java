package com.samsung.android.globalactions.presentation.viewmodel;

public class ActionInfo {
    private String mName = "";
    private int mIconResId = -1;
    private ViewType mViewType = ViewType.CENTER_ICON_1P_VIEW;
    private String mLabel = "";
    private String mDescription = "";
    private int mViewIndex = -1;
    private String mStateLabel = "";

    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }

    public void setIcon(int resId) {
        this.mIconResId = resId;
    }

    public int getIcon() {
        return this.mIconResId;
    }

    public void setLabel(String label) {
        this.mLabel = label;
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

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setViewIndex(int index) {
        this.mViewIndex = index;
    }

    public int getViewIndex() {
        return this.mViewIndex;
    }

    public void setStateLabel(String stateLabel) {
        this.mStateLabel = stateLabel;
    }

    public String getStateLabel() {
        return this.mStateLabel;
    }
}
