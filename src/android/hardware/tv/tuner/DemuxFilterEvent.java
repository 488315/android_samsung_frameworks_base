package android.hardware.tv.tuner;

import android.app.jank.AppJankStats;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DemuxFilterEvent implements Parcelable {
    public static final Parcelable.Creator<DemuxFilterEvent> CREATOR = new Parcelable.Creator<DemuxFilterEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterEvent createFromParcel(Parcel parcel) {
            return new DemuxFilterEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DemuxFilterEvent[] newArray(int i) {
            return new DemuxFilterEvent[i];
        }
    };
    public static final int download = 5;
    public static final int ipPayload = 6;
    public static final int media = 1;
    public static final int mmtpRecord = 4;
    public static final int monitorEvent = 8;
    public static final int pes = 2;
    public static final int section = 0;
    public static final int startId = 9;
    public static final int temi = 7;
    public static final int tsRecord = 3;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int download = 5;
        public static final int ipPayload = 6;
        public static final int media = 1;
        public static final int mmtpRecord = 4;
        public static final int monitorEvent = 8;
        public static final int pes = 2;
        public static final int section = 0;
        public static final int startId = 9;
        public static final int temi = 7;
        public static final int tsRecord = 3;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DemuxFilterEvent() {
        this._tag = 0;
        this._value = null;
    }

    private DemuxFilterEvent(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterEvent(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DemuxFilterEvent section(DemuxFilterSectionEvent demuxFilterSectionEvent) {
        return new DemuxFilterEvent(0, demuxFilterSectionEvent);
    }

    public DemuxFilterSectionEvent getSection() {
        _assertTag(0);
        return (DemuxFilterSectionEvent) this._value;
    }

    public void setSection(DemuxFilterSectionEvent demuxFilterSectionEvent) {
        _set(0, demuxFilterSectionEvent);
    }

    public static DemuxFilterEvent media(DemuxFilterMediaEvent demuxFilterMediaEvent) {
        return new DemuxFilterEvent(1, demuxFilterMediaEvent);
    }

    public DemuxFilterMediaEvent getMedia() {
        _assertTag(1);
        return (DemuxFilterMediaEvent) this._value;
    }

    public void setMedia(DemuxFilterMediaEvent demuxFilterMediaEvent) {
        _set(1, demuxFilterMediaEvent);
    }

    public static DemuxFilterEvent pes(DemuxFilterPesEvent demuxFilterPesEvent) {
        return new DemuxFilterEvent(2, demuxFilterPesEvent);
    }

    public DemuxFilterPesEvent getPes() {
        _assertTag(2);
        return (DemuxFilterPesEvent) this._value;
    }

    public void setPes(DemuxFilterPesEvent demuxFilterPesEvent) {
        _set(2, demuxFilterPesEvent);
    }

    public static DemuxFilterEvent tsRecord(DemuxFilterTsRecordEvent demuxFilterTsRecordEvent) {
        return new DemuxFilterEvent(3, demuxFilterTsRecordEvent);
    }

    public DemuxFilterTsRecordEvent getTsRecord() {
        _assertTag(3);
        return (DemuxFilterTsRecordEvent) this._value;
    }

    public void setTsRecord(DemuxFilterTsRecordEvent demuxFilterTsRecordEvent) {
        _set(3, demuxFilterTsRecordEvent);
    }

    public static DemuxFilterEvent mmtpRecord(DemuxFilterMmtpRecordEvent demuxFilterMmtpRecordEvent) {
        return new DemuxFilterEvent(4, demuxFilterMmtpRecordEvent);
    }

    public DemuxFilterMmtpRecordEvent getMmtpRecord() {
        _assertTag(4);
        return (DemuxFilterMmtpRecordEvent) this._value;
    }

    public void setMmtpRecord(DemuxFilterMmtpRecordEvent demuxFilterMmtpRecordEvent) {
        _set(4, demuxFilterMmtpRecordEvent);
    }

    public static DemuxFilterEvent download(DemuxFilterDownloadEvent demuxFilterDownloadEvent) {
        return new DemuxFilterEvent(5, demuxFilterDownloadEvent);
    }

    public DemuxFilterDownloadEvent getDownload() {
        _assertTag(5);
        return (DemuxFilterDownloadEvent) this._value;
    }

    public void setDownload(DemuxFilterDownloadEvent demuxFilterDownloadEvent) {
        _set(5, demuxFilterDownloadEvent);
    }

    public static DemuxFilterEvent ipPayload(DemuxFilterIpPayloadEvent demuxFilterIpPayloadEvent) {
        return new DemuxFilterEvent(6, demuxFilterIpPayloadEvent);
    }

    public DemuxFilterIpPayloadEvent getIpPayload() {
        _assertTag(6);
        return (DemuxFilterIpPayloadEvent) this._value;
    }

    public void setIpPayload(DemuxFilterIpPayloadEvent demuxFilterIpPayloadEvent) {
        _set(6, demuxFilterIpPayloadEvent);
    }

    public static DemuxFilterEvent temi(DemuxFilterTemiEvent demuxFilterTemiEvent) {
        return new DemuxFilterEvent(7, demuxFilterTemiEvent);
    }

    public DemuxFilterTemiEvent getTemi() {
        _assertTag(7);
        return (DemuxFilterTemiEvent) this._value;
    }

    public void setTemi(DemuxFilterTemiEvent demuxFilterTemiEvent) {
        _set(7, demuxFilterTemiEvent);
    }

    public static DemuxFilterEvent monitorEvent(DemuxFilterMonitorEvent demuxFilterMonitorEvent) {
        return new DemuxFilterEvent(8, demuxFilterMonitorEvent);
    }

    public DemuxFilterMonitorEvent getMonitorEvent() {
        _assertTag(8);
        return (DemuxFilterMonitorEvent) this._value;
    }

    public void setMonitorEvent(DemuxFilterMonitorEvent demuxFilterMonitorEvent) {
        _set(8, demuxFilterMonitorEvent);
    }

    public static DemuxFilterEvent startId(int i) {
        return new DemuxFilterEvent(9, Integer.valueOf(i));
    }

    public int getStartId() {
        _assertTag(9);
        return ((Integer) this._value).intValue();
    }

    public void setStartId(int i) {
        _set(9, Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeTypedObject(getSection(), i);
                break;
            case 1:
                parcel.writeTypedObject(getMedia(), i);
                break;
            case 2:
                parcel.writeTypedObject(getPes(), i);
                break;
            case 3:
                parcel.writeTypedObject(getTsRecord(), i);
                break;
            case 4:
                parcel.writeTypedObject(getMmtpRecord(), i);
                break;
            case 5:
                parcel.writeTypedObject(getDownload(), i);
                break;
            case 6:
                parcel.writeTypedObject(getIpPayload(), i);
                break;
            case 7:
                parcel.writeTypedObject(getTemi(), i);
                break;
            case 8:
                parcel.writeTypedObject(getMonitorEvent(), i);
                break;
            case 9:
                parcel.writeInt(getStartId());
                break;
        }
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (DemuxFilterSectionEvent) parcel.readTypedObject(DemuxFilterSectionEvent.CREATOR));
                return;
            case 1:
                _set(readInt, (DemuxFilterMediaEvent) parcel.readTypedObject(DemuxFilterMediaEvent.CREATOR));
                return;
            case 2:
                _set(readInt, (DemuxFilterPesEvent) parcel.readTypedObject(DemuxFilterPesEvent.CREATOR));
                return;
            case 3:
                _set(readInt, (DemuxFilterTsRecordEvent) parcel.readTypedObject(DemuxFilterTsRecordEvent.CREATOR));
                return;
            case 4:
                _set(readInt, (DemuxFilterMmtpRecordEvent) parcel.readTypedObject(DemuxFilterMmtpRecordEvent.CREATOR));
                return;
            case 5:
                _set(readInt, (DemuxFilterDownloadEvent) parcel.readTypedObject(DemuxFilterDownloadEvent.CREATOR));
                return;
            case 6:
                _set(readInt, (DemuxFilterIpPayloadEvent) parcel.readTypedObject(DemuxFilterIpPayloadEvent.CREATOR));
                return;
            case 7:
                _set(readInt, (DemuxFilterTemiEvent) parcel.readTypedObject(DemuxFilterTemiEvent.CREATOR));
                return;
            case 8:
                _set(readInt, (DemuxFilterMonitorEvent) parcel.readTypedObject(DemuxFilterMonitorEvent.CREATOR));
                return;
            case 9:
                _set(readInt, Integer.valueOf(parcel.readInt()));
                return;
            default:
                throw new IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return describeContents(getSection());
            case 1:
                return describeContents(getMedia());
            case 2:
                return describeContents(getPes());
            case 3:
                return describeContents(getTsRecord());
            case 4:
                return describeContents(getMmtpRecord());
            case 5:
                return describeContents(getDownload());
            case 6:
                return describeContents(getIpPayload());
            case 7:
                return describeContents(getTemi());
            case 8:
                return describeContents(getMonitorEvent());
            default:
                return 0;
        }
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        switch (i) {
            case 0:
                return "section";
            case 1:
                return AppJankStats.WIDGET_CATEGORY_MEDIA;
            case 2:
                return "pes";
            case 3:
                return "tsRecord";
            case 4:
                return "mmtpRecord";
            case 5:
                return Context.DOWNLOAD_SERVICE;
            case 6:
                return "ipPayload";
            case 7:
                return "temi";
            case 8:
                return "monitorEvent";
            case 9:
                return "startId";
            default:
                throw new IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
