package android.os;

import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes3.dex */
public class HWParamResultData extends HWParamData {
    private long time = 0;
    private int server = -1;
    private int interfaceType = -1;

    void readFromParcelLocked(Parcel in) {
        this.time = in.readLong();
        this.interfaceType = in.readInt();
        this.server = in.readInt();
        this.compID = in.readString();
        this.compVer = in.readString();
        this.compManufacture = in.readString();
        this.hitType = in.readString();
        this.feature = in.readString();
        this.HWRev = in.readString();
        this.IMEI = in.readString();
        this.UN = in.readString();
        this.logMaps = in.readString();
        this.envlogMaps = in.readString();
    }

    void writeToParcelLocked(Parcel out) {
        out.writeLong(this.time);
        out.writeInt(this.interfaceType);
        out.writeInt(this.server);
        out.writeString(this.compID);
        out.writeString(this.compVer);
        out.writeString(this.compManufacture);
        out.writeString(this.hitType);
        out.writeString(this.feature);
        out.writeString(this.HWRev);
        out.writeString(this.IMEI);
        out.writeString(this.UN);
        out.writeString(this.logMaps);
        out.writeString(this.envlogMaps);
    }

    public void setBasicParam(String compID, String compVer, String compManufacture, String hitType, String feature) {
        this.compID = compID;
        this.compVer = compVer;
        this.compManufacture = compManufacture;
        this.hitType = hitType;
        this.feature = feature;
    }

    public void setParam(String HWRev, String IMEI, String UN) {
        this.HWRev = HWRev;
        this.IMEI = IMEI;
        this.UN = UN;
    }

    public void setLog(String logMaps, String envlogMaps) {
        this.logMaps = logMaps;
        this.envlogMaps = envlogMaps;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setServer(int server, int interfaceType) {
        this.server = server;
        this.interfaceType = interfaceType;
    }

    public long getTime() {
        return this.time;
    }

    public Date getDate() {
        return new Date(this.time);
    }

    public int getServer() {
        return this.server;
    }

    public int getInterfaceType() {
        return this.interfaceType;
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        if (this.server == -1) {
            sb.append("N ");
        } else if (this.server == 0) {
            sb.append("D ");
        } else if (this.server == 1) {
            sb.append("C ");
        }
        if (this.interfaceType == -1) {
            sb.append("? ");
        } else if (this.interfaceType == 0) {
            sb.append("K ");
        } else if (this.interfaceType == 1) {
            sb.append("A ");
        } else if (this.interfaceType == 2) {
            sb.append("I ");
        }
        if (this.compID != null) {
            sb.append(" | ");
            sb.append(this.compID);
        }
        if (this.feature != null) {
            sb.append(" | ");
            sb.append(this.feature);
        }
        if (this.hitType != null) {
            sb.append(" | ");
            sb.append(this.hitType);
        }
        if (this.compVer != null) {
            sb.append(" | ");
            sb.append(this.compVer);
        }
        if (this.compManufacture != null) {
            sb.append(" | ");
            sb.append(this.compManufacture);
        }
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        if (this.time != 0) {
            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sb.append(DateFormat.format(getDate()));
        } else {
            sb.append("?????");
        }
        sb.append("\n");
        if (this.server == -1) {
            sb.append("N ");
        } else if (this.server == 0) {
            sb.append("D ");
        } else if (this.server == 1) {
            sb.append("C ");
        }
        if (this.interfaceType == -1) {
            sb.append("? ");
        } else if (this.interfaceType == 0) {
            sb.append("K ");
        } else if (this.interfaceType == 1) {
            sb.append("A ");
        } else if (this.interfaceType == 2) {
            sb.append("I ");
        }
        if (this.compID != null) {
            sb.append(" | ");
            sb.append(this.compID);
        }
        if (this.compVer != null) {
            sb.append(" | ");
            sb.append(this.compVer);
        }
        if (this.feature != null) {
            sb.append(" ");
            sb.append(this.feature);
        }
        if (this.hitType != null) {
            sb.append(" ");
            sb.append(this.hitType);
        }
        if (this.compVer != null) {
            sb.append(" | ");
            sb.append(this.compVer);
        }
        if (this.compManufacture != null) {
            sb.append(" | ");
            sb.append(this.compManufacture);
        }
        sb.append("\n");
        if (!this.logMaps.equals("")) {
            sb.append(" :");
            sb.append(this.logMaps);
        }
        sb.append("\n");
        if (!this.envlogMaps.equals("")) {
            sb.append(" :");
            sb.append(this.envlogMaps);
        }
        return sb.toString();
    }
}
