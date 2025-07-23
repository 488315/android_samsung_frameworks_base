package android.os;

import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes3.dex */
public class HWParamResultData extends HWParamData {
    private long time = 0;
    private int server = -1;
    private int interfaceType = -1;

    void readFromParcelLocked(Parcel parcel) {
        this.time = parcel.readLong();
        this.interfaceType = parcel.readInt();
        this.server = parcel.readInt();
        this.compID = parcel.readString();
        this.compVer = parcel.readString();
        this.compManufacture = parcel.readString();
        this.hitType = parcel.readString();
        this.feature = parcel.readString();
        this.HWRev = parcel.readString();
        this.IMEI = parcel.readString();
        this.UN = parcel.readString();
        this.logMaps = parcel.readString();
        this.envlogMaps = parcel.readString();
    }

    void writeToParcelLocked(Parcel parcel) {
        parcel.writeLong(this.time);
        parcel.writeInt(this.interfaceType);
        parcel.writeInt(this.server);
        parcel.writeString(this.compID);
        parcel.writeString(this.compVer);
        parcel.writeString(this.compManufacture);
        parcel.writeString(this.hitType);
        parcel.writeString(this.feature);
        parcel.writeString(this.HWRev);
        parcel.writeString(this.IMEI);
        parcel.writeString(this.UN);
        parcel.writeString(this.logMaps);
        parcel.writeString(this.envlogMaps);
    }

    public void setBasicParam(String str, String str2, String str3, String str4, String str5) {
        this.compID = str;
        this.compVer = str2;
        this.compManufacture = str3;
        this.hitType = str4;
        this.feature = str5;
    }

    public void setParam(String str, String str2, String str3) {
        this.HWRev = str;
        this.IMEI = str2;
        this.UN = str3;
    }

    public void setLog(String str, String str2) {
        this.logMaps = str;
        this.envlogMaps = str2;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public void setServer(int i, int i2) {
        this.server = i;
        this.interfaceType = i2;
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
        int i = this.server;
        if (i == -1) {
            sb.append("N ");
        } else if (i == 0) {
            sb.append("D ");
        } else if (i == 1) {
            sb.append("C ");
        }
        int i2 = this.interfaceType;
        if (i2 == -1) {
            sb.append("? ");
        } else if (i2 == 0) {
            sb.append("K ");
        } else if (i2 == 1) {
            sb.append("A ");
        } else if (i2 == 2) {
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
            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getDate()));
        } else {
            sb.append("?????");
        }
        sb.append(ShaderAssembler.NEWLINE);
        int i = this.server;
        if (i == -1) {
            sb.append("N ");
        } else if (i == 0) {
            sb.append("D ");
        } else if (i == 1) {
            sb.append("C ");
        }
        int i2 = this.interfaceType;
        if (i2 == -1) {
            sb.append("? ");
        } else if (i2 == 0) {
            sb.append("K ");
        } else if (i2 == 1) {
            sb.append("A ");
        } else if (i2 == 2) {
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
        sb.append(ShaderAssembler.NEWLINE);
        if (!this.logMaps.equals("")) {
            sb.append(" :");
            sb.append(this.logMaps);
        }
        sb.append(ShaderAssembler.NEWLINE);
        if (!this.envlogMaps.equals("")) {
            sb.append(" :");
            sb.append(this.envlogMaps);
        }
        return sb.toString();
    }
}
