package android.media;

import android.graphics.Rect;
import android.os.Parcel;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public final class TimedText {
    private static final int FIRST_PRIVATE_KEY = 101;
    private static final int FIRST_PUBLIC_KEY = 1;
    private static final int KEY_BACKGROUND_COLOR_RGBA = 3;
    private static final int KEY_DISPLAY_FLAGS = 1;
    private static final int KEY_END_CHAR = 104;
    private static final int KEY_FONT_ID = 105;
    private static final int KEY_FONT_SIZE = 106;
    private static final int KEY_GLOBAL_SETTING = 101;
    private static final int KEY_HIGHLIGHT_COLOR_RGBA = 4;
    private static final int KEY_LOCAL_SETTING = 102;
    private static final int KEY_SCROLL_DELAY = 5;
    private static final int KEY_START_CHAR = 103;
    private static final int KEY_START_TIME = 7;
    private static final int KEY_STRUCT_BLINKING_TEXT_LIST = 8;
    private static final int KEY_STRUCT_FONT_LIST = 9;
    private static final int KEY_STRUCT_HIGHLIGHT_LIST = 10;
    private static final int KEY_STRUCT_HYPER_TEXT_LIST = 11;
    private static final int KEY_STRUCT_JUSTIFICATION = 15;
    private static final int KEY_STRUCT_KARAOKE_LIST = 12;
    private static final int KEY_STRUCT_STYLE_LIST = 13;
    private static final int KEY_STRUCT_TEXT = 16;
    private static final int KEY_STRUCT_TEXT_POS = 14;
    private static final int KEY_STYLE_FLAGS = 2;
    private static final int KEY_TEXT_COLOR_RGBA = 107;
    private static final int KEY_WRAP_TEXT = 6;
    private static final int LAST_PRIVATE_KEY = 107;
    private static final int LAST_PUBLIC_KEY = 16;
    public static final int SEM_KEY_START_TIME = 3016;
    public static final int SEM_KEY_TEXT_INDEX = 3017;
    private static final String TAG = "TimedText";
    private int mBackgroundColorRGBA;
    private List<CharPos> mBlinkingPosList;
    private int mDisplayFlags;
    private List<Font> mFontList;
    private int mHighlightColorRGBA;
    private List<CharPos> mHighlightPosList;
    private List<HyperText> mHyperTextList;
    private Justification mJustification;
    private List<Karaoke> mKaraokeList;
    private final HashMap<Integer, Object> mKeyObjectMap;
    private Parcel mParcel;
    private int mScrollDelay;
    private List<Style> mStyleList;
    private Rect mTextBounds;
    private String mTextChars;
    private int mWrapText;

    private boolean isValidKey(int i) {
        if (i == 3017) {
            return true;
        }
        return (i >= 1 && i <= 16) || (i >= 101 && i <= 107);
    }

    public static final class CharPos {
        public final int endChar;
        public final int startChar;

        public CharPos(int i, int i2) {
            this.startChar = i;
            this.endChar = i2;
        }
    }

    public static final class Justification {
        public final int horizontalJustification;
        public final int verticalJustification;

        public Justification(int i, int i2) {
            this.horizontalJustification = i;
            this.verticalJustification = i2;
        }
    }

    public static final class Style {
        public final int colorRGBA;
        public final int endChar;
        public final int fontID;
        public final int fontSize;
        public final boolean isBold;
        public final boolean isItalic;
        public final boolean isUnderlined;
        public final int startChar;

        public Style(int i, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, int i5) {
            this.startChar = i;
            this.endChar = i2;
            this.fontID = i3;
            this.isBold = z;
            this.isItalic = z2;
            this.isUnderlined = z3;
            this.fontSize = i4;
            this.colorRGBA = i5;
        }
    }

    public static final class Font {
        public final int ID;
        public final String name;

        public Font(int i, String str) {
            this.ID = i;
            this.name = str;
        }
    }

    public static final class Karaoke {
        public final int endChar;
        public final int endTimeMs;
        public final int startChar;
        public final int startTimeMs;

        public Karaoke(int i, int i2, int i3, int i4) {
            this.startTimeMs = i;
            this.endTimeMs = i2;
            this.startChar = i3;
            this.endChar = i4;
        }
    }

    public static final class HyperText {
        public final String URL;
        public final String altString;
        public final int endChar;
        public final int startChar;

        public HyperText(int i, int i2, String str, String str2) {
            this.startChar = i;
            this.endChar = i2;
            this.URL = str;
            this.altString = str2;
        }
    }

    public TimedText(Parcel parcel) {
        this.mParcel = Parcel.obtain();
        HashMap<Integer, Object> map = new HashMap<>();
        this.mKeyObjectMap = map;
        this.mDisplayFlags = -1;
        this.mBackgroundColorRGBA = -1;
        this.mHighlightColorRGBA = -1;
        this.mScrollDelay = -1;
        this.mWrapText = -1;
        this.mBlinkingPosList = null;
        this.mHighlightPosList = null;
        this.mKaraokeList = null;
        this.mFontList = null;
        this.mStyleList = null;
        this.mHyperTextList = null;
        this.mTextBounds = null;
        this.mTextChars = null;
        byte[] bArrMarshall = parcel.marshall();
        this.mParcel.unmarshall(bArrMarshall, 0, bArrMarshall.length);
        if (parseParcel(this.mParcel)) {
            return;
        }
        map.clear();
        Log.w(TAG, "parseParcel() fails");
    }

    public TimedText(String str, Rect rect) {
        this.mParcel = Parcel.obtain();
        this.mKeyObjectMap = new HashMap<>();
        this.mDisplayFlags = -1;
        this.mBackgroundColorRGBA = -1;
        this.mHighlightColorRGBA = -1;
        this.mScrollDelay = -1;
        this.mWrapText = -1;
        this.mBlinkingPosList = null;
        this.mHighlightPosList = null;
        this.mKaraokeList = null;
        this.mFontList = null;
        this.mStyleList = null;
        this.mHyperTextList = null;
        this.mTextChars = str;
        this.mTextBounds = rect;
    }

    public String getText() {
        return this.mTextChars;
    }

    public Object semGetObject(int i) {
        if (i == 3016) {
            return getObject(7);
        }
        return getObject(i);
    }

    public Rect getBounds() {
        return this.mTextBounds;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private synchronized boolean parseParcel(Parcel parcel) {
        Object objValueOf;
        parcel.setDataPosition(0);
        if (parcel.dataAvail() == 0) {
            return false;
        }
        int i = parcel.readInt();
        if (i == 3017) {
            this.mKeyObjectMap.put(Integer.valueOf(i), Integer.valueOf(parcel.readInt()));
            i = parcel.readInt();
        }
        if (i == 102) {
            int i2 = parcel.readInt();
            if (i2 != 7) {
                return false;
            }
            this.mKeyObjectMap.put(Integer.valueOf(i2), Integer.valueOf(parcel.readInt()));
            if (parcel.readInt() != 16) {
                return false;
            }
            parcel.readInt();
            byte[] bArrCreateByteArray = parcel.createByteArray();
            if (bArrCreateByteArray == null || bArrCreateByteArray.length == 0) {
                this.mTextChars = null;
            } else {
                this.mTextChars = new String(bArrCreateByteArray);
            }
        } else if (i != 101) {
            Log.w(TAG, "Invalid timed text key found: " + i);
            return false;
        }
        while (parcel.dataAvail() > 0) {
            int i3 = parcel.readInt();
            if (!isValidKey(i3)) {
                Log.w(TAG, "Invalid timed text key found: " + i3);
                return false;
            }
            switch (i3) {
                case 1:
                    int i4 = parcel.readInt();
                    this.mDisplayFlags = i4;
                    objValueOf = Integer.valueOf(i4);
                    break;
                case 2:
                case 7:
                default:
                    objValueOf = null;
                    break;
                case 3:
                    int i5 = parcel.readInt();
                    this.mBackgroundColorRGBA = i5;
                    objValueOf = Integer.valueOf(i5);
                    break;
                case 4:
                    int i6 = parcel.readInt();
                    this.mHighlightColorRGBA = i6;
                    objValueOf = Integer.valueOf(i6);
                    break;
                case 5:
                    int i7 = parcel.readInt();
                    this.mScrollDelay = i7;
                    objValueOf = Integer.valueOf(i7);
                    break;
                case 6:
                    int i8 = parcel.readInt();
                    this.mWrapText = i8;
                    objValueOf = Integer.valueOf(i8);
                    break;
                case 8:
                    readBlinkingText(parcel);
                    objValueOf = this.mBlinkingPosList;
                    break;
                case 9:
                    readFont(parcel);
                    objValueOf = this.mFontList;
                    break;
                case 10:
                    readHighlight(parcel);
                    objValueOf = this.mHighlightPosList;
                    break;
                case 11:
                    readHyperText(parcel);
                    objValueOf = this.mHyperTextList;
                    break;
                case 12:
                    readKaraoke(parcel);
                    objValueOf = this.mKaraokeList;
                    break;
                case 13:
                    readStyle(parcel);
                    objValueOf = this.mStyleList;
                    break;
                case 14:
                    this.mTextBounds = new Rect(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    objValueOf = null;
                    break;
                case 15:
                    Justification justification = new Justification(parcel.readInt(), parcel.readInt());
                    this.mJustification = justification;
                    objValueOf = justification;
                    break;
            }
            if (objValueOf != null) {
                if (this.mKeyObjectMap.containsKey(Integer.valueOf(i3))) {
                    this.mKeyObjectMap.remove(Integer.valueOf(i3));
                }
                this.mKeyObjectMap.put(Integer.valueOf(i3), objValueOf);
            }
        }
        this.mParcel.recycle();
        return true;
    }

    private void readStyle(Parcel parcel) {
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        boolean z4 = false;
        while (!z4 && parcel.dataAvail() > 0) {
            int i6 = parcel.readInt();
            if (i6 != 2) {
                switch (i6) {
                    case 103:
                        i = parcel.readInt();
                        break;
                    case 104:
                        i2 = parcel.readInt();
                        break;
                    case 105:
                        i3 = parcel.readInt();
                        break;
                    case 106:
                        i4 = parcel.readInt();
                        break;
                    case 107:
                        i5 = parcel.readInt();
                        break;
                    default:
                        parcel.setDataPosition(parcel.dataPosition() - 4);
                        z4 = true;
                        break;
                }
            } else {
                int i7 = parcel.readInt();
                z = i7 % 2 == 1;
                z2 = i7 % 4 >= 2;
                z3 = i7 / 4 == 1;
            }
        }
        Style style = new Style(i, i2, i3, z, z2, z3, i4, i5);
        if (this.mStyleList == null) {
            this.mStyleList = new ArrayList();
        }
        this.mStyleList.add(style);
    }

    private void readFont(Parcel parcel) {
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            Font font = new Font(parcel.readInt(), new String(parcel.createByteArray(), 0, parcel.readInt()));
            if (this.mFontList == null) {
                this.mFontList = new ArrayList();
            }
            this.mFontList.add(font);
        }
    }

    private void readHighlight(Parcel parcel) {
        CharPos charPos = new CharPos(parcel.readInt(), parcel.readInt());
        if (this.mHighlightPosList == null) {
            this.mHighlightPosList = new ArrayList();
        }
        this.mHighlightPosList.add(charPos);
    }

    private void readKaraoke(Parcel parcel) {
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            Karaoke karaoke = new Karaoke(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            if (this.mKaraokeList == null) {
                this.mKaraokeList = new ArrayList();
            }
            this.mKaraokeList.add(karaoke);
        }
    }

    private void readHyperText(Parcel parcel) {
        HyperText hyperText = new HyperText(parcel.readInt(), parcel.readInt(), new String(parcel.createByteArray(), 0, parcel.readInt()), new String(parcel.createByteArray(), 0, parcel.readInt()));
        if (this.mHyperTextList == null) {
            this.mHyperTextList = new ArrayList();
        }
        this.mHyperTextList.add(hyperText);
    }

    private void readBlinkingText(Parcel parcel) {
        CharPos charPos = new CharPos(parcel.readInt(), parcel.readInt());
        if (this.mBlinkingPosList == null) {
            this.mBlinkingPosList = new ArrayList();
        }
        this.mBlinkingPosList.add(charPos);
    }

    private boolean containsKey(int i) {
        return isValidKey(i) && this.mKeyObjectMap.containsKey(Integer.valueOf(i));
    }

    private Set keySet() {
        return this.mKeyObjectMap.keySet();
    }

    private Object getObject(int i) {
        if (containsKey(i)) {
            return this.mKeyObjectMap.get(Integer.valueOf(i));
        }
        throw new IllegalArgumentException("Invalid key: " + i);
    }
}
