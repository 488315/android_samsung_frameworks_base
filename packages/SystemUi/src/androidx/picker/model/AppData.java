package androidx.picker.model;

import android.graphics.drawable.Drawable;
import androidx.picker.model.appdata.CategoryAppData;
import androidx.picker.model.appdata.GroupAppData;
import java.util.List;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface AppData {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GridAppDataBuilder {
        private final AppInfo appInfo;
        private Drawable icon;
        private String label;
        private Drawable subIcon;
        private String subLabel;

        public GridAppDataBuilder(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        public final AppInfo getAppInfo() {
            return this.appInfo;
        }

        public final GridAppDataBuilder setIcon(Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public final GridAppDataBuilder setLabel(String str) {
            this.label = str;
            return this;
        }

        public final GridAppDataBuilder setSubIcon(Drawable drawable) {
            this.subIcon = drawable;
            return this;
        }

        public final GridAppDataBuilder setSubLabel(String str) {
            this.subLabel = str;
            return this;
        }

        public AppInfoData build() {
            return new AppInfoDataImpl(this.appInfo, 0, this.icon, this.subIcon, this.label, this.subLabel, null, null, false, false, false, 1984, null);
        }

        public GridAppDataBuilder(AppInfoData appInfoData) {
            this(appInfoData.getAppInfo());
            setIcon(appInfoData.getIcon());
            setSubIcon(appInfoData.getSubIcon());
            setLabel(appInfoData.getLabel());
            setSubLabel(appInfoData.getSubLabel());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GridCheckBoxAppDataBuilder {
        private final AppInfo appInfo;
        private boolean dimmed;
        private Drawable icon;
        private String label;
        private boolean selected;
        private Drawable subIcon;
        private String subLabel;

        public GridCheckBoxAppDataBuilder(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        public final AppInfo getAppInfo() {
            return this.appInfo;
        }

        public final GridCheckBoxAppDataBuilder setDimmed(boolean z) {
            this.dimmed = z;
            return this;
        }

        public final GridCheckBoxAppDataBuilder setIcon(Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public final GridCheckBoxAppDataBuilder setLabel(String str) {
            this.label = str;
            return this;
        }

        public final GridCheckBoxAppDataBuilder setSelected(boolean z) {
            this.selected = z;
            return this;
        }

        public final GridCheckBoxAppDataBuilder setSubIcon(Drawable drawable) {
            this.subIcon = drawable;
            return this;
        }

        public final GridCheckBoxAppDataBuilder setSubLabel(String str) {
            this.subLabel = str;
            return this;
        }

        public AppInfoData build() {
            return new AppInfoDataImpl(this.appInfo, 2, this.icon, this.subIcon, this.label, this.subLabel, null, null, this.selected, this.dimmed, false, 1216, null);
        }

        public GridCheckBoxAppDataBuilder(AppInfoData appInfoData) {
            this(appInfoData.getAppInfo());
            setIcon(appInfoData.getIcon());
            setSubIcon(appInfoData.getSubIcon());
            setLabel(appInfoData.getLabel());
            setSubLabel(appInfoData.getSubLabel());
            setSelected(appInfoData.getSelected());
            setDimmed(appInfoData.getDimmed());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GridRemoveAppDataBuilder {
        private final AppInfo appInfo;
        private boolean dimmed;
        private Drawable icon;
        private String label;
        private boolean selected;
        private Drawable subIcon;
        private String subLabel;

        public GridRemoveAppDataBuilder(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        public final AppInfo getAppInfo() {
            return this.appInfo;
        }

        public final GridRemoveAppDataBuilder setDimmed(boolean z) {
            this.dimmed = z;
            return this;
        }

        public final GridRemoveAppDataBuilder setIcon(Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public final GridRemoveAppDataBuilder setLabel(String str) {
            this.label = str;
            return this;
        }

        public final GridRemoveAppDataBuilder setSelected(boolean z) {
            this.selected = z;
            return this;
        }

        public final GridRemoveAppDataBuilder setSubIcon(Drawable drawable) {
            this.subIcon = drawable;
            return this;
        }

        public final GridRemoveAppDataBuilder setSubLabel(String str) {
            this.subLabel = str;
            return this;
        }

        public AppInfoData build() {
            return new AppInfoDataImpl(this.appInfo, 3, this.icon, this.subIcon, this.label, this.subLabel, null, null, this.selected, this.dimmed, false, 1216, null);
        }

        public GridRemoveAppDataBuilder(AppInfoData appInfoData) {
            this(appInfoData.getAppInfo());
            setIcon(appInfoData.getIcon());
            setSubIcon(appInfoData.getSubIcon());
            setLabel(appInfoData.getLabel());
            setSubLabel(appInfoData.getSubLabel());
            setSelected(appInfoData.getSelected());
            setDimmed(appInfoData.getDimmed());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListAppDataBuilder {
        private final AppInfo appInfo;
        private String extraLabel;
        private Drawable icon;
        private boolean isValueInSubLabel;
        private String label;
        private Drawable subIcon;
        private String subLabel;

        public ListAppDataBuilder(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        public static /* synthetic */ ListAppDataBuilder setSubLabel$default(ListAppDataBuilder listAppDataBuilder, String str, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return listAppDataBuilder.setSubLabel(str, z);
        }

        public final AppInfo getAppInfo() {
            return this.appInfo;
        }

        public final ListAppDataBuilder setExtraLabel(String str) {
            this.extraLabel = str;
            return this;
        }

        public final ListAppDataBuilder setIcon(Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public final ListAppDataBuilder setLabel(String str) {
            this.label = str;
            return this;
        }

        public final ListAppDataBuilder setSubIcon(Drawable drawable) {
            this.subIcon = drawable;
            return this;
        }

        public final ListAppDataBuilder setSubLabel(String str) {
            return setSubLabel$default(this, str, false, 2, null);
        }

        public AppInfoData build() {
            return new AppInfoDataImpl(this.appInfo, 0, this.icon, this.subIcon, this.label, this.subLabel, this.extraLabel, null, false, false, this.isValueInSubLabel, 896, null);
        }

        public final ListAppDataBuilder setSubLabel(String str, boolean z) {
            this.subLabel = str;
            this.isValueInSubLabel = z;
            return this;
        }

        public ListAppDataBuilder(AppInfoData appInfoData) {
            this(appInfoData.getAppInfo());
            setIcon(appInfoData.getIcon());
            setSubIcon(appInfoData.getSubIcon());
            setLabel(appInfoData.getLabel());
            setSubLabel(appInfoData.getSubLabel(), appInfoData.isValueInSubLabel());
            setExtraLabel(appInfoData.getExtraLabel());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListCheckBoxAppDataBuilder {
        private Drawable actionIcon;
        private final AppInfo appInfo;
        private boolean dimmed;
        private String extraLabel;
        private Drawable icon;
        private String label;
        private boolean selected;
        private Drawable subIcon;
        private String subLabel;

        public ListCheckBoxAppDataBuilder(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        public final AppInfo getAppInfo() {
            return this.appInfo;
        }

        public final ListCheckBoxAppDataBuilder setActionIcon(Drawable drawable) {
            this.actionIcon = drawable;
            return this;
        }

        public final ListCheckBoxAppDataBuilder setDimmed(boolean z) {
            this.dimmed = z;
            return this;
        }

        public final ListCheckBoxAppDataBuilder setExtraLabel(String str) {
            this.extraLabel = str;
            return this;
        }

        public final ListCheckBoxAppDataBuilder setIcon(Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public final ListCheckBoxAppDataBuilder setLabel(String str) {
            this.label = str;
            return this;
        }

        public final ListCheckBoxAppDataBuilder setSelected(boolean z) {
            this.selected = z;
            return this;
        }

        public final ListCheckBoxAppDataBuilder setSubIcon(Drawable drawable) {
            this.subIcon = drawable;
            return this;
        }

        public final ListCheckBoxAppDataBuilder setSubLabel(String str) {
            this.subLabel = str;
            return this;
        }

        public AppInfoData build() {
            return new AppInfoDataImpl(this.appInfo, 2, this.icon, this.subIcon, this.label, this.subLabel, this.extraLabel, this.actionIcon, this.selected, this.dimmed, false, 1024, null);
        }

        public ListCheckBoxAppDataBuilder(AppInfoData appInfoData) {
            this(appInfoData.getAppInfo());
            setIcon(appInfoData.getIcon());
            setSubIcon(appInfoData.getSubIcon());
            setLabel(appInfoData.getLabel());
            setSubLabel(appInfoData.getSubLabel());
            setExtraLabel(appInfoData.getExtraLabel());
            setActionIcon(appInfoData.getActionIcon());
            setSelected(appInfoData.getSelected());
            setDimmed(appInfoData.getDimmed());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListRadioButtonAppDataBuilder {
        private Drawable actionIcon;
        private final AppInfo appInfo;
        private boolean dimmed;
        private Drawable icon;
        private String label;
        private boolean selected;
        private Drawable subIcon;
        private String subLabel;

        public ListRadioButtonAppDataBuilder(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        public final AppInfo getAppInfo() {
            return this.appInfo;
        }

        public final ListRadioButtonAppDataBuilder setActionIcon(Drawable drawable) {
            this.actionIcon = drawable;
            return this;
        }

        public final ListRadioButtonAppDataBuilder setDimmed(boolean z) {
            this.dimmed = z;
            return this;
        }

        public final ListRadioButtonAppDataBuilder setIcon(Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public final ListRadioButtonAppDataBuilder setLabel(String str) {
            this.label = str;
            return this;
        }

        public final ListRadioButtonAppDataBuilder setSelected(boolean z) {
            this.selected = z;
            return this;
        }

        public final ListRadioButtonAppDataBuilder setSubIcon(Drawable drawable) {
            this.subIcon = drawable;
            return this;
        }

        public final ListRadioButtonAppDataBuilder setSubLabel(String str) {
            this.subLabel = str;
            return this;
        }

        public AppInfoData build() {
            return new AppInfoDataImpl(this.appInfo, 4, this.icon, this.subIcon, this.label, this.subLabel, null, this.actionIcon, this.selected, this.dimmed, false, 1088, null);
        }

        public ListRadioButtonAppDataBuilder(AppInfoData appInfoData) {
            this(appInfoData.getAppInfo());
            setIcon(appInfoData.getIcon());
            setSubIcon(appInfoData.getSubIcon());
            setLabel(appInfoData.getLabel());
            setSubLabel(appInfoData.getSubLabel());
            setActionIcon(appInfoData.getActionIcon());
            setSelected(appInfoData.getSelected());
            setDimmed(appInfoData.getDimmed());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListSwitchAppDataBuilder {
        private final AppInfo appInfo;
        private boolean dimmed;
        private Drawable icon;
        private boolean isValueInSubLabel;
        private String label;
        private boolean selected;
        private Drawable subIcon;
        private String subLabel;

        public ListSwitchAppDataBuilder(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        public static /* synthetic */ ListSwitchAppDataBuilder setSubLabel$default(ListSwitchAppDataBuilder listSwitchAppDataBuilder, String str, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return listSwitchAppDataBuilder.setSubLabel(str, z);
        }

        public final AppInfo getAppInfo() {
            return this.appInfo;
        }

        public final ListSwitchAppDataBuilder setDimmed(boolean z) {
            this.dimmed = z;
            return this;
        }

        public final ListSwitchAppDataBuilder setIcon(Drawable drawable) {
            this.icon = drawable;
            return this;
        }

        public final ListSwitchAppDataBuilder setLabel(String str) {
            this.label = str;
            return this;
        }

        public final ListSwitchAppDataBuilder setSelected(boolean z) {
            this.selected = z;
            return this;
        }

        public final ListSwitchAppDataBuilder setSubIcon(Drawable drawable) {
            this.subIcon = drawable;
            return this;
        }

        public final ListSwitchAppDataBuilder setSubLabel(String str) {
            return setSubLabel$default(this, str, false, 2, null);
        }

        public AppInfoData build() {
            return new AppInfoDataImpl(this.appInfo, 5, this.icon, this.subIcon, this.label, this.subLabel, null, null, this.selected, this.dimmed, this.isValueInSubLabel, 192, null);
        }

        public final ListSwitchAppDataBuilder setSubLabel(String str, boolean z) {
            this.subLabel = str;
            this.isValueInSubLabel = z;
            return this;
        }

        public ListSwitchAppDataBuilder(AppInfoData appInfoData) {
            this(appInfoData.getAppInfo());
            setIcon(appInfoData.getIcon());
            setSubIcon(appInfoData.getSubIcon());
            setLabel(appInfoData.getLabel());
            setSubLabel(appInfoData.getSubLabel(), appInfoData.isValueInSubLabel());
            setSelected(appInfoData.getSelected());
            setDimmed(appInfoData.getDimmed());
        }
    }

    AppInfo getAppInfo();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GroupAppDataBuilder {
        public final String key;
        public String label;
        public List mAppInfoDataList;

        public GroupAppDataBuilder(GroupAppData groupAppData) {
            this(groupAppData.appInfo.activityName);
            this.label = groupAppData.group;
            this.mAppInfoDataList = groupAppData.appDataList;
        }

        public GroupAppDataBuilder(String str) {
            this.key = str;
            this.mAppInfoDataList = EmptyList.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CategoryAppDataBuilder {
        public List appInfoDataList;
        public Drawable icon;
        public final String key;
        public String label;

        public CategoryAppDataBuilder(CategoryAppData categoryAppData) {
            this(categoryAppData.appInfo.activityName);
            this.icon = categoryAppData.icon;
            this.label = categoryAppData.label;
            this.appInfoDataList = categoryAppData.appInfoDataList;
        }

        public CategoryAppDataBuilder(String str) {
            this.key = str;
            this.appInfoDataList = EmptyList.INSTANCE;
        }
    }
}
