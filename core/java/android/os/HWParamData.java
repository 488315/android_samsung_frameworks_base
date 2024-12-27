package android.os;

public class HWParamData {
    protected String compID = "";
    protected String compVer = "";
    protected String compManufacture = "";
    protected String hitType = "";
    protected String feature = "";
    protected String HWRev = "";
    protected String IMEI = "";
    protected String UN = "";
    protected String logMaps = "";
    protected String envlogMaps = "";

    public String getCompID() {
        return this.compID;
    }

    public String getCompVer() {
        return this.compVer;
    }

    public String getCompManufacture() {
        return this.compManufacture;
    }

    public String getHitType() {
        return this.hitType;
    }

    public String getFeature() {
        return this.feature;
    }

    public String getHWRev() {
        return this.HWRev;
    }

    public String getIMEI() {
        return this.IMEI;
    }

    public String getUN() {
        return this.UN;
    }

    public String getLogMaps() {
        return this.logMaps;
    }

    public String getEnvLogMaps() {
        return this.envlogMaps;
    }
}
