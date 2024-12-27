package vendor.samsung.frameworks.codecsolution;

import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class MediaStatisticsData {
    private static final String TAG = "CodecSolution_MediaStatisticsData";
    private Action mAction;
    private Category mCategory;
    private Label mLabel;
    private HashMap<String, Object> mMap = new HashMap<>();

    enum Action {
        NONE,
        INSTANTIATE;

        public static Action valueOf(int i) {
            Log.d(MediaStatisticsData.TAG, "Action.valueOf: " + i);
            for (Action action : values()) {
                if (i == action.ordinal()) {
                    return action;
                }
            }
            return NONE;
        }
    }

    enum Category {
        NONE,
        VENC,
        VDEC,
        AENC,
        ADEC;

        static Category valueOf(int i) {
            Log.d(MediaStatisticsData.TAG, "Category.valueOf: " + i);
            for (Category category : values()) {
                if (i == category.ordinal()) {
                    return category;
                }
            }
            return NONE;
        }
    }

    enum Label {
        NONE("none"),
        SEC_HW_H265("shh5"),
        SEC_HW_H264("shh4"),
        SEC_HW_H263("shh3"),
        SEC_HW_VC1("shv1"),
        SEC_HW_VP8("shv8"),
        SEC_HW_VP9("shv9"),
        SEC_HW_AV1("sha1"),
        SEC_HW_MPEG4("shm4"),
        SEC_HW_MPEG2("shm2"),
        SEC_HW_MP3("shp3"),
        SEC_HW_AAC("shac"),
        SEC_HW_FLAC("shfl"),
        SEC_SW_AAC("ssac"),
        SEC_SW_AMR("ssar"),
        SEC_SW_QCELP13("ssqc"),
        SEC_SW_EVRC("ssev"),
        SEC_SW_WMA("sswa"),
        SEC_SW_AMRWBPLUS("ssap"),
        SEC_SW_WMA10PRO("sswp"),
        SEC_SW_WMALOSSLESS("sswl"),
        SEC_SW_FLAC("ssfl"),
        SEC_SW_ALAC("ssal"),
        SEC_SW_APE("ssae"),
        SEC_SW_MULTIAAC("ssma"),
        SEC_SW_MP1("ssp1"),
        SEC_SW_MP2("ssp2"),
        SEC_SW_MP3("ssp3"),
        SEC_SW_ADPCM("ssam"),
        SEC_SW_H265("ssh5"),
        SEC_SW_H264("ssh4"),
        SEC_SW_H263("ssh3"),
        SEC_SW_WMV7("ssw7"),
        SEC_SW_WMV8("ssw8"),
        SEC_SW_VC1("ssv1"),
        SEC_SW_VP8("ssv8"),
        SEC_SW_MPEG4("ssm4"),
        SEC_SW_MP43("ss43"),
        GOOGLE_SW_AAC("gsac"),
        GOOGLE_SW_AMRNB("gsan"),
        GOOGLE_SW_AMRWB("gsaw"),
        GOOGLE_SW_G711ALAW("gsal"),
        GOOGLE_SW_G711MLAW("gsml"),
        GOOGLE_SW_MP3("gsp3"),
        GOOGLE_SW_VORBIS("gsvb"),
        GOOGLE_SW_OPUS("gsop"),
        GOOGLE_SW_RAW("gsrw"),
        GOOGLE_SW_FLAC("gsfl"),
        GOOGLE_SW_GSM("gsgm"),
        GOOGLE_SW_H265("gsh5"),
        GOOGLE_SW_H264("gsh4"),
        GOOGLE_SW_H263("gsh3"),
        GOOGLE_SW_VP8("gsv8"),
        GOOGLE_SW_VP9("gsv9"),
        GOOGLE_SW_MPEG4("gsm4"),
        DOLBY_SW_AC3("dba3"),
        DOLBY_SW_EAC3("dbe3"),
        DOLBY_SW_EAC3JOC("dbej"),
        DOLBY_SW_AC4("dba4"),
        ANDROID_SW_AV1("asa1"),
        ANDROID_SW_H265("ash5"),
        ANDROID_SW_H264("ash4"),
        ANDROID_SW_H263("ash3"),
        ANDROID_SW_VP8("asv8"),
        ANDROID_SW_VP9("asv9"),
        ANDROID_SW_MPEG4("asm4"),
        ANDROID_SW_AAC("asac"),
        ANDROID_SW_AMRNB("asan"),
        ANDROID_SW_AMRWB("asaw"),
        ANDROID_SW_G711ALAW("asal"),
        ANDROID_SW_G711MLAW("asml"),
        ANDROID_SW_MP3("asp3"),
        ANDROID_SW_VORBIS("asvb"),
        ANDROID_SW_OPUS("asop"),
        ANDROID_SW_RAW("asrw"),
        ANDROID_SW_FLAC("asfl"),
        ANDROID_SW_GSM("asgm"),
        ANDROID_SW_XAAC("asxc"),
        ENDL("endl");

        private int val;

        Label() {
            this.val = ordinal();
        }

        static Label valueOf(int i) {
            Log.d(MediaStatisticsData.TAG, String.format("Label.valueOf: 0x%x", Integer.valueOf(i)));
            for (Label label : values()) {
                if (i == label.val) {
                    return label;
                }
            }
            return NONE;
        }

        Label(String str) {
            this.val = MediaStatisticsData.getFourCCNumber(str);
        }
    }

    enum Type {
        NONE("none"),
        INT("vint"),
        LONG("vlng"),
        FLOAT("vflt"),
        STRING("vstr"),
        INTARRAY("aint"),
        LONGARRAY("alng"),
        ULONGARRAY("auln"),
        STRINGARRAY("astr");

        private int val;

        Type(String str) {
            this.val = MediaStatisticsData.getFourCCNumber(str);
        }

        @Override // java.lang.Enum
        public String toString() {
            return Integer.toString(this.val);
        }

        static Type valueOf(int i) {
            for (Type type : values()) {
                if (i == type.val) {
                    return type;
                }
            }
            return NONE;
        }
    }

    public MediaStatisticsData(String str) {
        Log.d(TAG, "MediaStatisticsData: " + str);
        unflatten(str);
        this.mCategory = Category.valueOf(((Integer) this.mMap.get("category")).intValue());
        this.mAction = Action.valueOf(((Integer) this.mMap.get("action")).intValue());
        this.mLabel = Label.valueOf(((Integer) this.mMap.get("label")).intValue());
        Log.d(TAG, "category: " + this.mCategory);
        Log.d(TAG, "action: " + this.mAction);
        Log.d(TAG, "label: " + this.mLabel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getFourCCNumber(String str) {
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i = (i << 8) | str.charAt(i2);
        }
        return i;
    }

    public String flatten() {
        StringBuilder sb = new StringBuilder();
        for (String str : this.mMap.keySet()) {
            Object obj = this.mMap.get(str);
            if (obj != null) {
                sb.append(str);
                sb.append("=");
                if (obj instanceof Integer) {
                    sb.append(((Integer) obj).toString());
                    sb.append("@" + Type.INT);
                } else if (obj instanceof Long) {
                    sb.append(((Long) obj).toString());
                    sb.append("@" + Type.LONG);
                } else if (obj instanceof Float) {
                    sb.append(((Float) obj).toString());
                    sb.append("@" + Type.FLOAT);
                } else if (obj instanceof String) {
                    sb.append((String) obj);
                    sb.append("@" + Type.STRING);
                } else if (obj instanceof List) {
                    sb.append("@a");
                } else {
                    Log.e(TAG, "Unknown type: " + obj);
                }
                sb.append(";");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public Object get(String str) {
        return this.mMap.get(str);
    }

    public String getAction() {
        return this.mAction.name();
    }

    public String getCategory() {
        return this.mCategory.name();
    }

    public float getFloat(String str) {
        return ((Float) this.mMap.get(str)).floatValue();
    }

    public int getInt(String str) {
        return ((Integer) this.mMap.get(str)).intValue();
    }

    public String getLabel() {
        return this.mLabel.name();
    }

    public List getList(String str) {
        return (List) this.mMap.get(str);
    }

    public long getLong(String str) {
        return ((Long) this.mMap.get(str)).longValue();
    }

    public String getString(String str) {
        return (String) this.mMap.get(str);
    }

    public void put(Map<? extends String, ?> map) {
        this.mMap.putAll(map);
    }

    public void set(String str, Object obj) {
        this.mMap.put(str, obj);
    }

    public void unflatten(String str) {
        this.mMap.clear();
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(';');
        simpleStringSplitter.setString(str);
        Iterator<String> it = simpleStringSplitter.iterator();
        while (it.hasNext()) {
            String next = it.next();
            int indexOf = next.indexOf("=");
            if (indexOf != -1) {
                String substring = next.substring(0, indexOf);
                int indexOf2 = next.indexOf("@");
                Type valueOf = Type.valueOf(Integer.parseInt(next.substring(indexOf2 + 1)));
                this.mMap.put(substring, valueOf == Type.INT ? Integer.valueOf(Integer.parseInt(next.substring(indexOf + 1, indexOf2))) : valueOf == Type.LONG ? Long.valueOf(Long.parseLong(next.substring(indexOf + 1, indexOf2))) : valueOf == Type.FLOAT ? Float.valueOf(Float.parseFloat(next.substring(indexOf + 1, indexOf2))) : valueOf == Type.STRING ? next.substring(indexOf + 1, indexOf2) : null);
            }
        }
    }
}
