package com.samsung.android.wifi.intelligence.ins.data;

import android.hardware.gnss.GnssSignalType;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public final class OutageData implements Parcelable {
    private static final String TAG = "OutageData";
    private String L2TransitMode;
    private String RSSI;
    private String TxSpeed;
    private String ccaBusyTimeDiffMs;
    private String defaultUsed;
    private String latency;
    private String lqCallbackTriggered;
    private String lqComebackTriggered;
    private String outageCnt;
    private String radioOnTimeDiffMs;
    private String recoveryCnt;
    private String throughput;
    private final String timestamp;
    private String txCntDiff;
    private String txRatio;
    private String voiceCall;
    public static final Pattern REGEX_ONEUI_6_0_OUTAGE_LOG = Pattern.compile("(Link stat: )?(?<radioOnTimeDiffMs>\\d+) / (?<ccaBusyTimeDiffMs>\\d+) / (?<txRatio>[\\d.]+) \\((?<txCntDiff>\\d+)\\) / (?<TxSpeed>\\d+) / (?<RSSI>-[\\d.]+) \\|\\| (linkSpeedMbps )?(?<TreeNode1Value>[\\d.]+) : (?<Node1Variable>[\\d.]+) / ((successRate )?(?<TreeNode2Value>[\\d.]+) : (?<Node2Variable>[\\d.]+) / )?((ccaRo )?(?<TreeNode3Value>[\\d.]+) : (?<Node3Variable>[\\d.]+) / )?((RT QoS: )?(?<prediction>(false|true))) \\((?<decision>(false|true))\\) \\|\\| QoS: (?<latency>[\\d.]+) ms / (?<throughput>[\\d.]+) Mbps (\\((?<defaultUsed>[YN])\\) )?(/ (?<L2TransitMode>[Y]) / (?<voiceCall>[YN]) / )?/ (?<outageCnt>\\d) \\((?<lqCallbackTriggered>[YN01])\\)( / (?<recoveryCnt>\\d) (?<lqComebackTriggered>[YN]))?");
    public static final Pattern REGEX_ONEUI_6_1_OUTAGE_LOG = Pattern.compile("Link stat: (?<radioOnTimeDiffMs>\\d+) / (?<ccaBusyTimeDiffMs>\\d+) / (?<txRatio>[\\d.]+) \\((?<txCntDiff>\\d+)\\) / (?<TxSpeed>\\d+) / (?<RSSI>-[\\d.]+) \\|\\| ((?<TreeNode1Value>[\\d.]+) : (?<Node1Variable>[\\d.]+) / )?((?<TreeNode2Value>[\\d.]+) : (?<Node2Variable>[\\d.]+) / )?((?<TreeNode3Value>[\\d.]+) : (?<Node3Variable>[\\d.]+) / )?(?<prediction>[GB]) \\((?<decision>[GB])\\) \\|\\| (?<latency>[\\d.]+) ms / (?<throughput>[\\d.]+) Mbps / (?<defaultUsed>[YN]) \\|\\| (?<L2TransitMode>\\d) / (?<voiceCall>[YN]) / (?<outageCnt>\\d) (?<lqCallbackTriggered>[YN]) / (?<recoveryCnt>\\d) (?<lqComebackTriggered>[YN])");
    public static final Parcelable.Creator<OutageData> CREATOR = new Parcelable.Creator<OutageData>() { // from class: com.samsung.android.wifi.intelligence.ins.data.OutageData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OutageData createFromParcel(Parcel parcel) {
            return new OutageData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OutageData[] newArray(int i) {
            return new OutageData[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OutageData(String str) {
        this.radioOnTimeDiffMs = "-1";
        this.ccaBusyTimeDiffMs = "-1";
        this.txRatio = "-1";
        this.txCntDiff = "-1";
        this.TxSpeed = "-1";
        this.RSSI = "-1";
        this.latency = "-1";
        this.throughput = "-1";
        this.defaultUsed = "-1";
        this.L2TransitMode = "-1";
        this.voiceCall = "-1";
        this.outageCnt = "-1";
        this.lqCallbackTriggered = "-1";
        this.recoveryCnt = "-1";
        this.lqComebackTriggered = "-1";
        this.timestamp = str;
    }

    private OutageData(Parcel parcel) {
        this.radioOnTimeDiffMs = "-1";
        this.ccaBusyTimeDiffMs = "-1";
        this.txRatio = "-1";
        this.txCntDiff = "-1";
        this.TxSpeed = "-1";
        this.RSSI = "-1";
        this.latency = "-1";
        this.throughput = "-1";
        this.defaultUsed = "-1";
        this.L2TransitMode = "-1";
        this.voiceCall = "-1";
        this.outageCnt = "-1";
        this.lqCallbackTriggered = "-1";
        this.recoveryCnt = "-1";
        this.lqComebackTriggered = "-1";
        this.timestamp = parcel.readString();
        this.radioOnTimeDiffMs = parcel.readString();
        this.ccaBusyTimeDiffMs = parcel.readString();
        this.txRatio = parcel.readString();
        this.txCntDiff = parcel.readString();
        this.TxSpeed = parcel.readString();
        this.RSSI = parcel.readString();
        this.latency = parcel.readString();
        this.throughput = parcel.readString();
        this.defaultUsed = parcel.readString();
        this.L2TransitMode = parcel.readString();
        this.voiceCall = parcel.readString();
        this.outageCnt = parcel.readString();
        this.lqCallbackTriggered = parcel.readString();
        this.recoveryCnt = parcel.readString();
        this.lqComebackTriggered = parcel.readString();
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public String getLatency() {
        return this.latency;
    }

    public String getLqCallbackTriggered() {
        return this.lqCallbackTriggered;
    }

    public String getOutageCnt() {
        return this.outageCnt;
    }

    public void setLatency(double d) {
        this.latency = Double.toString(d);
    }

    public void setOutageCnt(int i) {
        this.outageCnt = Integer.toString(i);
    }

    public OutageData(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String str16) {
        this.timestamp = str;
        this.ccaBusyTimeDiffMs = str2;
        this.defaultUsed = str3;
        this.L2TransitMode = str4;
        this.latency = str5;
        this.lqCallbackTriggered = str6;
        this.lqComebackTriggered = str7;
        this.outageCnt = str8;
        this.radioOnTimeDiffMs = str9;
        this.recoveryCnt = str10;
        this.RSSI = str11;
        this.throughput = str12;
        this.txCntDiff = str13;
        this.txRatio = str14;
        this.TxSpeed = str15;
        this.voiceCall = str16;
    }

    public static String getCsvHeader() {
        return "outageTimestamp,radioOnTimeDiffMs,ccaBusyTimeDiffMs,txRatio,txCntDiff,TxSpeed,RSSI,latency,throughput,defaultUsed,L2TransitMode,voiceCall,outageCnt,lqCallbackTriggered,recoveryCnt,lqComebackTriggered\n";
    }

    public String toCsvString() {
        return this.timestamp + "," + this.radioOnTimeDiffMs + "," + this.ccaBusyTimeDiffMs + "," + this.txRatio + "," + this.txCntDiff + "," + this.TxSpeed + "," + this.RSSI + "," + this.latency + "," + this.throughput + "," + this.defaultUsed + "," + this.L2TransitMode + "," + this.voiceCall + "," + this.outageCnt + "," + this.lqCallbackTriggered + "," + this.recoveryCnt + "," + this.lqComebackTriggered + ShaderAssembler.NEWLINE;
    }

    public static OutageData fromLog(String str) {
        Matcher matcher = REGEX_ONEUI_6_0_OUTAGE_LOG.matcher(str);
        Matcher matcher2 = REGEX_ONEUI_6_1_OUTAGE_LOG.matcher(str);
        String str2 = new SimpleDateFormat("yyyyMMdd-HHmmss_SSS").format(new Date());
        if (matcher.matches()) {
            Log.v(TAG, "The Log string matches ONEUI_6_0_OUTAGE_LOG");
            return new OutageData(str2, (String) Optional.ofNullable(matcher.group("ccaBusyTimeDiffMs")).orElse("-1"), (String) Optional.ofNullable(matcher.group("defaultUsed")).orElse("-1"), (String) Optional.ofNullable(matcher.group("L2TransitMode")).orElse("-1"), (String) Optional.ofNullable(matcher.group("latency")).orElse("-1"), (String) Optional.ofNullable(matcher.group("lqCallbackTriggered")).orElse("-1"), (String) Optional.ofNullable(matcher.group("lqComebackTriggered")).orElse("-1"), (String) Optional.ofNullable(matcher.group("outageCnt")).orElse("-1"), (String) Optional.ofNullable(matcher.group("radioOnTimeDiffMs")).orElse("-1"), (String) Optional.ofNullable(matcher.group("recoveryCnt")).orElse("-1"), (String) Optional.ofNullable(matcher.group("RSSI")).orElse("-1"), (String) Optional.ofNullable(matcher.group("throughput")).orElse("-1"), (String) Optional.ofNullable(matcher.group("txCntDiff")).orElse("-1"), (String) Optional.ofNullable(matcher.group("txRatio")).orElse("-1"), (String) Optional.ofNullable(matcher.group("TxSpeed")).orElse("-1"), (String) Optional.ofNullable(matcher.group("voiceCall")).orElse("-1"));
        }
        if (matcher2.matches()) {
            Log.v(TAG, "The Log string matches ONEUI_6_1_OUTAGE_LOG");
            return new OutageData(str2, (String) Optional.ofNullable(matcher2.group("ccaBusyTimeDiffMs")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("defaultUsed")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("L2TransitMode")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("latency")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("lqCallbackTriggered")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("lqComebackTriggered")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("outageCnt")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("radioOnTimeDiffMs")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("recoveryCnt")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("RSSI")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("throughput")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("txCntDiff")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("txRatio")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("TxSpeed")).orElse("-1"), (String) Optional.ofNullable(matcher2.group("voiceCall")).orElse("-1"));
        }
        Log.v(TAG, "The Log string does not match any regex!");
        return new OutageData(str2);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.timestamp);
        parcel.writeString(this.radioOnTimeDiffMs);
        parcel.writeString(this.ccaBusyTimeDiffMs);
        parcel.writeString(this.txRatio);
        parcel.writeString(this.txCntDiff);
        parcel.writeString(this.TxSpeed);
        parcel.writeString(this.RSSI);
        parcel.writeString(this.latency);
        parcel.writeString(this.throughput);
        parcel.writeString(this.defaultUsed);
        parcel.writeString(this.L2TransitMode);
        parcel.writeString(this.voiceCall);
        parcel.writeString(this.outageCnt);
        parcel.writeString(this.lqCallbackTriggered);
        parcel.writeString(this.recoveryCnt);
        parcel.writeString(this.lqComebackTriggered);
    }

    public void setLqCallbackTriggered(boolean z) {
        this.lqCallbackTriggered = z ? GnssSignalType.CODE_TYPE_Y : GnssSignalType.CODE_TYPE_N;
    }
}
