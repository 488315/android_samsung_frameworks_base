package com.android.server.wm;

import android.os.Bundle;
import android.text.TextUtils;

import com.android.internal.util.FrameworkStatsLog;

import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TspGripCommand {
    public static final float MIN_REJECT_ZONE_RATIO;
    public final String mCommand;
    public TspStateController.DeviceSize mDeviceSize;
    public int mLandBottomGripWidth;
    public int mLandBottomRejectWidth;
    public int mLandEdgeZone;
    public int mLandTopGripWidth;
    public int mLandTopRejectWidth;
    public int mLandX1;
    public int mPortEdgeZone;
    public int mPortX1;
    public int mPortX2;
    public int mPortX3;
    public int mPortY1;
    public int mPortY2;

    static {
        MIN_REJECT_ZONE_RATIO = !CoreRune.IS_TABLET_DEVICE ? 0.0069f : 0.0035f;
    }

    public TspGripCommand(int i) {
        this.mCommand = null;
        reset();
        switch (i) {
            case 1:
                this.mCommand = "0.69%,2.22%,30%,1.39%,4.17%,4.17%,1.05%,1.05%,1.97%,1.97%";
                break;
            case 2:
                this.mCommand = "0.35%,0.35%,0,0,2.11%,0,0.22%,0.22%,1.32%,1.32%";
                break;
            case 3:
                this.mCommand = "0.35%,0.35%,0,0,2.11%,0,0.22%,0.22%,1.32%,1.32%";
                break;
            case 4:
                this.mCommand = "0.69%,1.39%,30%,1.39%,4.17%,4.17%,0.66%,0.66%,1.97%,1.97%";
                reset3rdParty();
                break;
            case 5:
            case 8:
                this.mCommand = "0.35%,0.35%,0,0,1.41%,0,0.22%,0.22%,0.88%,0.88%";
                reset3rdParty();
                break;
            case 6:
            case 9:
                this.mCommand = "0.35%,0.35%,0,0,1.41%,0,0.22%,0.22%,0.88%,0.88%";
                reset3rdParty();
                break;
            case 7:
                this.mCommand = "0.69%,0.69%,30%,1.39%,2.78%,4.17%,0.66%,0.66%,1.97%,1.97%";
                reset3rdParty();
                break;
            case 10:
                this.mCommand = "0.69%,1.39%,30%,1.39%,4.17%,2.55%,0.66%,0.66%,1.97%,1.97%";
                reset3rdParty();
                break;
        }
    }

    public static int convertToInt(int i, int i2, int i3, String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        String lowerCase = str.toLowerCase();
        int lastIndexOf = lowerCase.lastIndexOf("%");
        if (lastIndexOf >= 0) {
            return Math.round(
                    (Float.parseFloat(lowerCase.subSequence(0, lastIndexOf).toString()) / 100.0f)
                            * i2);
        }
        int lastIndexOf2 = lowerCase.lastIndexOf("px");
        if (lastIndexOf2 >= 0) {
            return (int)
                    ((Float.parseFloat(lowerCase.subSequence(0, lastIndexOf2).toString()) * i2)
                            / i);
        }
        int parseInt = Integer.parseInt(str);
        return i2 < i3 ? (parseInt * i2) / i3 : parseInt;
    }

    public static int getValueFromBundle(Bundle bundle, String str, int i, int i2, int i3, int i4) {
        int i5 = bundle.getInt(str, -1);
        if (i5 >= 0) {
            return i3 < i4 ? (i5 * i3) / i4 : i5;
        }
        String string = bundle.getString(str);
        return TextUtils.isEmpty(string) ? i : convertToInt(i2, i3, i4, string);
    }

    public final int convertToIntHeight(String str) {
        TspStateController.DeviceSize deviceSize = this.mDeviceSize;
        return convertToInt(deviceSize.height, deviceSize.initHeight, 2560, str);
    }

    public final int convertToIntLandscapeHeight(String str) {
        TspStateController.DeviceSize deviceSize = this.mDeviceSize;
        return convertToInt(deviceSize.height, deviceSize.initHeight, 1440, str);
    }

    public final int convertToIntWidth(String str) {
        TspStateController.DeviceSize deviceSize = this.mDeviceSize;
        return convertToInt(deviceSize.width, deviceSize.initWidth, 1440, str);
    }

    public final void parse(TspStateController.DeviceSize deviceSize, Bundle bundle) {
        if (bundle != null) {
            if (CoreRune.FW_TSP_DEADZONE_V3) {
                String string = bundle.getString("deadzone_v3", null);
                this.mDeviceSize = deviceSize;
                int i = deviceSize.initWidth;
                if (!TextUtils.isEmpty(string)) {
                    String[] split = string.split(",");
                    int length = split.length;
                    try {
                        String str = split[0];
                        this.mPortX1 = convertToIntWidth(str != null ? str.trim() : null);
                        if (length >= 3) {
                            String str2 = split[1];
                            this.mPortX2 = convertToIntWidth(str2 != null ? str2.trim() : null);
                            String str3 = split[2];
                            this.mPortY1 = convertToIntHeight(str3 != null ? str3.trim() : null);
                        } else {
                            this.mPortY1 = convertToIntHeight("100%");
                        }
                        if (length >= 5) {
                            String str4 = split[3];
                            this.mPortX3 = convertToIntWidth(str4 != null ? str4.trim() : null);
                            String str5 = split[4];
                            int convertToIntHeight =
                                    convertToIntHeight(str5 != null ? str5.trim() : null);
                            if (convertToIntHeight >= this.mPortY1) {
                                this.mPortY2 = convertToIntHeight;
                            }
                        } else {
                            this.mPortY2 = convertToIntHeight("100%");
                        }
                        if (length >= 6) {
                            String str6 = split[5];
                            this.mLandX1 = convertToIntWidth(str6 != null ? str6.trim() : null);
                        }
                        if (length >= 7) {
                            String str7 = split[6];
                            this.mPortEdgeZone =
                                    convertToIntWidth(str7 != null ? str7.trim() : null);
                        }
                        this.mLandEdgeZone = this.mPortEdgeZone;
                        if (length >= 8) {
                            String str8 = split[7];
                            this.mLandEdgeZone =
                                    convertToIntWidth(str8 != null ? str8.trim() : null);
                        }
                        if (length >= 9) {
                            String str9 = split[8];
                            this.mLandTopRejectWidth =
                                    convertToIntLandscapeHeight(str9 != null ? str9.trim() : null);
                        }
                        if (length >= 10) {
                            String str10 = split[9];
                            this.mLandBottomRejectWidth =
                                    convertToIntLandscapeHeight(
                                            str10 != null ? str10.trim() : null);
                        }
                        if (length >= 11) {
                            String str11 = split[10];
                            this.mLandTopGripWidth =
                                    convertToIntLandscapeHeight(
                                            str11 != null ? str11.trim() : null);
                        }
                        if (length >= 12) {
                            String str12 = split[11];
                            this.mLandBottomGripWidth =
                                    convertToIntLandscapeHeight(
                                            str12 != null ? str12.trim() : null);
                        }
                        setMinimumValue(i);
                        return;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            if (parse(deviceSize, bundle.getString("deadzone_v2", null), true)) {
                return;
            }
            int i2 = deviceSize.width;
            int i3 = deviceSize.height;
            int i4 = deviceSize.initWidth;
            int i5 = deviceSize.initHeight;
            int valueFromBundle = getValueFromBundle(bundle, "dead_zone_port_x1", -1, i2, i4, 1440);
            int valueFromBundle2 =
                    getValueFromBundle(bundle, "dead_zone_port_x2", -1, i2, i4, 1440);
            if (valueFromBundle != -1 && valueFromBundle2 == -1) {
                this.mPortX2 = valueFromBundle;
            } else if (valueFromBundle != -1) {
                this.mPortX1 = valueFromBundle;
            }
            if (valueFromBundle2 != -1) {
                this.mPortX2 = valueFromBundle2;
            }
            this.mPortY1 =
                    getValueFromBundle(bundle, "dead_zone_port_y1", this.mPortY1, i3, i5, 2560);
            int i6 = bundle.getInt("dead_zone_port_real_y1", -1);
            if (i6 != -1) {
                this.mPortY1 = (i5 * i6) / i3;
            }
            this.mLandX1 =
                    getValueFromBundle(bundle, "dead_zone_land_x1", this.mLandX1, i2, i4, 1440);
            int valueFromBundle3 =
                    getValueFromBundle(bundle, "edge_zone_width", this.mPortEdgeZone, i2, i4, 1440);
            this.mPortEdgeZone = valueFromBundle3;
            this.mLandEdgeZone = valueFromBundle3;
            this.mLandEdgeZone =
                    getValueFromBundle(bundle, "edge_zone_land", valueFromBundle3, i2, i4, 1440);
            this.mPortEdgeZone =
                    getValueFromBundle(bundle, "edge_zone_port", this.mPortEdgeZone, i2, i4, 1440);
            setMinimumValue(i4);
        }
    }

    public final boolean parse(TspStateController.DeviceSize deviceSize, String str, boolean z) {
        this.mDeviceSize = deviceSize;
        if (str == null) {
            str = this.mCommand;
        }
        int i = deviceSize.height;
        int i2 = deviceSize.initWidth;
        int i3 = deviceSize.initHeight;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] split = str.split(",");
        int length = split.length;
        try {
            if (length <= 1) {
                String str2 = split[0];
                this.mPortX2 = convertToIntWidth(str2 != null ? str2.trim() : null);
            } else {
                String str3 = split[0];
                this.mPortX1 = convertToIntWidth(str3 != null ? str3.trim() : null);
                if (length >= 3) {
                    String str4 = split[1];
                    String trim = str4 != null ? str4.trim() : null;
                    String str5 = split[2];
                    String trim2 = str5 != null ? str5.trim() : null;
                    this.mPortX2 = convertToIntWidth(trim);
                    this.mPortY1 = convertToIntHeight(trim2);
                }
                if (length >= 4) {
                    String str6 = split[3];
                    this.mLandX1 = convertToIntWidth(str6 != null ? str6.trim() : null);
                }
                if (length >= 5) {
                    String str7 = split[4];
                    this.mPortEdgeZone = convertToIntWidth(str7 != null ? str7.trim() : null);
                }
                this.mLandEdgeZone = this.mPortEdgeZone;
                if (length >= 6) {
                    String str8 = split[5];
                    this.mLandEdgeZone = convertToIntWidth(str8 != null ? str8.trim() : null);
                }
                if (length >= 7) {
                    String str9 = split[6];
                    this.mLandTopRejectWidth =
                            convertToIntLandscapeHeight(str9 != null ? str9.trim() : null);
                }
                if (length >= 8) {
                    String str10 = split[7];
                    this.mLandBottomRejectWidth =
                            convertToIntLandscapeHeight(str10 != null ? str10.trim() : null);
                }
                if (length >= 9) {
                    String str11 = split[8];
                    this.mLandTopGripWidth =
                            convertToIntLandscapeHeight(str11 != null ? str11.trim() : null);
                }
                if (length >= 10) {
                    String str12 = split[9];
                    this.mLandBottomGripWidth =
                            convertToIntLandscapeHeight(str12 != null ? str12.trim() : null);
                }
            }
            if (CoreRune.FW_TSP_DEADZONE_V3) {
                this.mPortX3 = this.mPortX2;
                this.mPortY2 = convertToInt(i, i3, 2560, "100%");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (z) {
            setMinimumValue(i2);
        }
        return true;
    }

    public final void reset() {
        this.mPortX1 = 10;
        this.mPortX2 = 32;
        this.mPortX3 = 32;
        this.mPortY1 = FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE;
        this.mPortY2 = 2560;
        this.mLandX1 = 10;
        this.mPortEdgeZone = 60;
        this.mLandEdgeZone = 60;
        this.mLandTopRejectWidth = 0;
        this.mLandBottomRejectWidth = 0;
        this.mLandTopGripWidth = 0;
        this.mLandBottomGripWidth = 0;
    }

    public final void reset3rdParty() {
        this.mPortX1 = 10;
        this.mPortX2 = 10;
        this.mPortX3 = 10;
        this.mPortY1 = 0;
        this.mPortY2 = 0;
        this.mLandX1 = 10;
        this.mPortEdgeZone = 40;
        this.mLandEdgeZone = 40;
    }

    public final void set(TspGripCommand tspGripCommand) {
        this.mPortX1 = tspGripCommand.mPortX1;
        this.mPortX2 = tspGripCommand.mPortX2;
        this.mPortY1 = tspGripCommand.mPortY1;
        this.mLandX1 = tspGripCommand.mLandX1;
        if (CoreRune.FW_TSP_DEADZONE_V3) {
            this.mPortX3 = tspGripCommand.mPortX3;
            this.mPortY2 = tspGripCommand.mPortY2;
        }
        this.mPortEdgeZone = tspGripCommand.mPortEdgeZone;
        this.mLandEdgeZone = tspGripCommand.mLandEdgeZone;
        this.mLandTopRejectWidth = tspGripCommand.mLandTopRejectWidth;
        this.mLandBottomRejectWidth = tspGripCommand.mLandBottomRejectWidth;
        this.mLandTopGripWidth = tspGripCommand.mLandTopGripWidth;
        this.mLandBottomGripWidth = tspGripCommand.mLandBottomGripWidth;
    }

    public final void setMinimumValue(int i) {
        int min = Math.min((int) (i * MIN_REJECT_ZONE_RATIO), 10);
        if (this.mPortX1 < min) {
            this.mPortX1 = min;
        }
        if (this.mPortX2 < min) {
            this.mPortX2 = min;
        }
    }

    public final String toString() {
        return "portX1="
                + this.mPortX1
                + ", portX2="
                + this.mPortX2
                + ", portX3="
                + this.mPortX3
                + ", portY1="
                + this.mPortY1
                + ", portY2="
                + this.mPortY2
                + ", landX1="
                + this.mLandX1
                + ", portEdge="
                + this.mPortEdgeZone
                + ", landEdge="
                + this.mLandEdgeZone
                + ", mLandTopRejectWidth="
                + this.mLandTopRejectWidth
                + ", mLandBottomRejectWidth="
                + this.mLandBottomRejectWidth
                + ", mLandTopGripWidth="
                + this.mLandTopGripWidth
                + ", mLandBottomGripWidth="
                + this.mLandBottomGripWidth;
    }
}
