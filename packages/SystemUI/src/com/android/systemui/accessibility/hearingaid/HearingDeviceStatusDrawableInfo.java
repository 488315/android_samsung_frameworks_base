package com.android.systemui.accessibility.hearingaid;

import android.R;

/* loaded from: classes.dex */
public final class HearingDeviceStatusDrawableInfo {
    public static final StatusDrawableInfo DRAWABLE_DEFAULT_INFO = new StatusDrawableInfo(R.drawable.ic_commit_search_api_material, 0, 0);
    public static final StatusDrawableInfo DRAWABLE_DISCONNECTED_INFO = new StatusDrawableInfo(R.drawable.ic_contact_picture, 0, R.string.no_recent_tasks);
    public static final StatusDrawableInfo DRAWABLE_CONNECTED_INFO = new StatusDrawableInfo(R.drawable.ic_commit_search_api_material, R.drawable.ic_commit_search_api_mtrl_alpha, R.string.no_permissions);
    public static final StatusDrawableInfo DRAWABLE_ACTIVE_INFO = new StatusDrawableInfo(R.drawable.ic_commit_search_api_material, R.drawable.ic_contact_picture_2, R.string.no_matches);

    public final class StatusDrawableInfo extends Record {
        public final int baseDrawableId;
        public final int indicatorDrawableId;
        public final int stateDescriptionId;

        public StatusDrawableInfo(int i, int i2, int i3) {
            this.baseDrawableId = i;
            this.indicatorDrawableId = i2;
            this.stateDescriptionId = i3;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            if (!(obj instanceof StatusDrawableInfo)) {
                return false;
            }
            StatusDrawableInfo statusDrawableInfo = (StatusDrawableInfo) obj;
            return this.baseDrawableId == statusDrawableInfo.baseDrawableId && this.indicatorDrawableId == statusDrawableInfo.indicatorDrawableId && this.stateDescriptionId == statusDrawableInfo.stateDescriptionId;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            int i = this.baseDrawableId;
            return (((i * 31) + this.indicatorDrawableId) * 31) + this.stateDescriptionId;
        }

        @Override // java.lang.Record
        public final String toString() {
            Object[] objArr = {Integer.valueOf(this.baseDrawableId), Integer.valueOf(this.indicatorDrawableId), Integer.valueOf(this.stateDescriptionId)};
            String[] strArrSplit = "baseDrawableId;indicatorDrawableId;stateDescriptionId".length() == 0 ? new String[0] : "baseDrawableId;indicatorDrawableId;stateDescriptionId".split(";");
            StringBuilder sb = new StringBuilder();
            sb.append(StatusDrawableInfo.class.getSimpleName());
            sb.append("[");
            for (int i = 0; i < strArrSplit.length; i++) {
                sb.append(strArrSplit[i]);
                sb.append("=");
                sb.append(objArr[i]);
                if (i != strArrSplit.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    private HearingDeviceStatusDrawableInfo() {
    }
}
