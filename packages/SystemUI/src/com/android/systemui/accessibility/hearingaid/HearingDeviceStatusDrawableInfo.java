package com.android.systemui.accessibility.hearingaid;

import android.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HearingDeviceStatusDrawableInfo {
    public static final StatusDrawableInfo DRAWABLE_DEFAULT_INFO = new StatusDrawableInfo(R.drawable.ic_commit_search_api_material, 0, 0);
    public static final StatusDrawableInfo DRAWABLE_DISCONNECTED_INFO = new StatusDrawableInfo(R.drawable.ic_contact_picture, 0, R.string.no_matches);
    public static final StatusDrawableInfo DRAWABLE_CONNECTED_INFO = new StatusDrawableInfo(R.drawable.ic_commit_search_api_material, R.drawable.ic_commit_search_api_mtrl_alpha, R.string.no_file_chosen);
    public static final StatusDrawableInfo DRAWABLE_ACTIVE_INFO = new StatusDrawableInfo(R.drawable.ic_commit_search_api_material, R.drawable.ic_contact_picture_2, R.string.noApplications);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            String[] split = "baseDrawableId;indicatorDrawableId;stateDescriptionId".length() == 0 ? new String[0] : "baseDrawableId;indicatorDrawableId;stateDescriptionId".split(";");
            StringBuilder sb = new StringBuilder();
            sb.append(StatusDrawableInfo.class.getSimpleName());
            sb.append("[");
            for (int i = 0; i < split.length; i++) {
                sb.append(split[i]);
                sb.append("=");
                sb.append(objArr[i]);
                if (i != split.length - 1) {
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
