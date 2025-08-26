package android.inputmethodservice;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
/* loaded from: classes2.dex */
public class Keyboard {
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    public static final int EDGE_TOP = 4;
    private static final int GRID_HEIGHT = 5;
    private static final int GRID_SIZE = 50;
    private static final int GRID_WIDTH = 10;
    public static final int KEYCODE_ALT = -6;
    public static final int KEYCODE_CANCEL = -3;
    public static final int KEYCODE_DELETE = -5;
    public static final int KEYCODE_DONE = -4;
    public static final int KEYCODE_MODE_CHANGE = -2;
    public static final int KEYCODE_SHIFT = -1;
    private static float SEARCH_DISTANCE = 1.8f;
    static final String TAG = "Keyboard";
    private static final String TAG_KEY = "Key";
    private static final String TAG_KEYBOARD = "Keyboard";
    private static final String TAG_ROW = "Row";
    private int mCellHeight;
    private int mCellWidth;
    private int mDefaultHeight;
    private int mDefaultHorizontalGap;
    private int mDefaultVerticalGap;
    private int mDefaultWidth;
    private int mDisplayHeight;
    private int mDisplayWidth;
    private int[][] mGridNeighbors;
    private int mKeyHeight;
    private int mKeyWidth;
    private int mKeyboardMode;
    private List<Key> mKeys;
    private CharSequence mLabel;
    private List<Key> mModifierKeys;
    private int mProximityThreshold;
    private int[] mShiftKeyIndices;
    private Key[] mShiftKeys;
    private boolean mShifted;
    private int mTotalHeight;
    private int mTotalWidth;
    private ArrayList<Row> rows;

    public static class Row {
        public int defaultHeight;
        public int defaultHorizontalGap;
        public int defaultWidth;
        ArrayList<Key> mKeys = new ArrayList<>();
        public int mode;
        private Keyboard parent;
        public int rowEdgeFlags;
        public int verticalGap;

        public Row(Keyboard keyboard) {
            this.parent = keyboard;
        }

        public Row(Resources resources, Keyboard keyboard, XmlResourceParser xmlResourceParser) {
            this.parent = keyboard;
            TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R.styleable.Keyboard);
            this.defaultWidth = Keyboard.getDimensionOrFraction(typedArrayObtainAttributes, 0, keyboard.mDisplayWidth, keyboard.mDefaultWidth);
            this.defaultHeight = Keyboard.getDimensionOrFraction(typedArrayObtainAttributes, 1, keyboard.mDisplayHeight, keyboard.mDefaultHeight);
            this.defaultHorizontalGap = Keyboard.getDimensionOrFraction(typedArrayObtainAttributes, 2, keyboard.mDisplayWidth, keyboard.mDefaultHorizontalGap);
            this.verticalGap = Keyboard.getDimensionOrFraction(typedArrayObtainAttributes, 3, keyboard.mDisplayHeight, keyboard.mDefaultVerticalGap);
            typedArrayObtainAttributes.recycle();
            TypedArray typedArrayObtainAttributes2 = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R.styleable.Keyboard_Row);
            this.rowEdgeFlags = typedArrayObtainAttributes2.getInt(0, 0);
            this.mode = typedArrayObtainAttributes2.getResourceId(1, 0);
            typedArrayObtainAttributes2.recycle();
        }
    }

    public static class Key {
        public int[] codes;
        public int edgeFlags;
        public int gap;
        public int height;
        public Drawable icon;
        public Drawable iconPreview;
        private Keyboard keyboard;
        public CharSequence label;
        public boolean modifier;
        public boolean on;
        public CharSequence popupCharacters;
        public int popupResId;
        public boolean pressed;
        public boolean repeatable;
        public boolean sticky;
        public CharSequence text;
        public int width;
        public int x;
        public int y;
        private static final int[] KEY_STATE_NORMAL_ON = {16842911, 16842912};
        private static final int[] KEY_STATE_PRESSED_ON = {16842919, 16842911, 16842912};
        private static final int[] KEY_STATE_NORMAL_OFF = {16842911};
        private static final int[] KEY_STATE_PRESSED_OFF = {16842919, 16842911};
        private static final int[] KEY_STATE_NORMAL = new int[0];
        private static final int[] KEY_STATE_PRESSED = {16842919};

        public Key(Row row) {
            this.keyboard = row.parent;
            this.height = row.defaultHeight;
            this.width = row.defaultWidth;
            this.gap = row.defaultHorizontalGap;
            this.edgeFlags = row.rowEdgeFlags;
        }

        public Key(Resources resources, Row row, int i, int i2, XmlResourceParser xmlResourceParser) {
            this(row);
            this.x = i;
            this.y = i2;
            TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R.styleable.Keyboard);
            this.width = Keyboard.getDimensionOrFraction(typedArrayObtainAttributes, 0, this.keyboard.mDisplayWidth, row.defaultWidth);
            this.height = Keyboard.getDimensionOrFraction(typedArrayObtainAttributes, 1, this.keyboard.mDisplayHeight, row.defaultHeight);
            this.gap = Keyboard.getDimensionOrFraction(typedArrayObtainAttributes, 2, this.keyboard.mDisplayWidth, row.defaultHorizontalGap);
            typedArrayObtainAttributes.recycle();
            TypedArray typedArrayObtainAttributes2 = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R.styleable.Keyboard_Key);
            this.x += this.gap;
            TypedValue typedValue = new TypedValue();
            typedArrayObtainAttributes2.getValue(0, typedValue);
            if (typedValue.type == 16 || typedValue.type == 17) {
                this.codes = new int[]{typedValue.data};
            } else if (typedValue.type == 3) {
                this.codes = parseCSV(typedValue.string.toString());
            }
            Drawable drawable = typedArrayObtainAttributes2.getDrawable(7);
            this.iconPreview = drawable;
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.iconPreview.getIntrinsicHeight());
            }
            this.popupCharacters = typedArrayObtainAttributes2.getText(2);
            this.popupResId = typedArrayObtainAttributes2.getResourceId(1, 0);
            this.repeatable = typedArrayObtainAttributes2.getBoolean(6, false);
            this.modifier = typedArrayObtainAttributes2.getBoolean(4, false);
            this.sticky = typedArrayObtainAttributes2.getBoolean(5, false);
            int i3 = typedArrayObtainAttributes2.getInt(3, 0);
            this.edgeFlags = i3;
            this.edgeFlags = row.rowEdgeFlags | i3;
            Drawable drawable2 = typedArrayObtainAttributes2.getDrawable(10);
            this.icon = drawable2;
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.icon.getIntrinsicHeight());
            }
            this.label = typedArrayObtainAttributes2.getText(9);
            this.text = typedArrayObtainAttributes2.getText(8);
            if (this.codes == null && !TextUtils.isEmpty(this.label)) {
                this.codes = new int[]{this.label.charAt(0)};
            }
            typedArrayObtainAttributes2.recycle();
        }

        public void onPressed() {
            this.pressed = !this.pressed;
        }

        public void onReleased(boolean z) {
            this.pressed = !this.pressed;
            if (this.sticky && z) {
                this.on = !this.on;
            }
        }

        int[] parseCSV(String str) {
            int i;
            int i2 = 0;
            if (str.length() > 0) {
                i = 1;
                int iIndexOf = 0;
                while (true) {
                    iIndexOf = str.indexOf(",", iIndexOf + 1);
                    if (iIndexOf <= 0) {
                        break;
                    }
                    i++;
                }
            } else {
                i = 0;
            }
            int[] iArr = new int[i];
            StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
            while (stringTokenizer.hasMoreTokens()) {
                int i3 = i2 + 1;
                try {
                    iArr[i2] = Integer.parseInt(stringTokenizer.nextToken());
                } catch (NumberFormatException unused) {
                    Log.e("Keyboard", "Error parsing keycodes " + str);
                }
                i2 = i3;
            }
            return iArr;
        }

        public boolean isInside(int i, int i2) {
            int i3;
            int i4 = this.edgeFlags;
            boolean z = (i4 & 1) > 0;
            boolean z2 = (i4 & 2) > 0;
            boolean z3 = (i4 & 4) > 0;
            boolean z4 = (i4 & 8) > 0;
            int i5 = this.x;
            return (i >= i5 || (z && i <= this.width + i5)) && (i < this.width + i5 || (z2 && i >= i5)) && ((i2 >= (i3 = this.y) || (z3 && i2 <= this.height + i3)) && (i2 < this.height + i3 || (z4 && i2 >= i3)));
        }

        public int squaredDistanceFrom(int i, int i2) {
            int i3 = (this.x + (this.width / 2)) - i;
            int i4 = (this.y + (this.height / 2)) - i2;
            return (i3 * i3) + (i4 * i4);
        }

        public int[] getCurrentDrawableState() {
            int[] iArr = KEY_STATE_NORMAL;
            if (this.on) {
                if (this.pressed) {
                    return KEY_STATE_PRESSED_ON;
                }
                return KEY_STATE_NORMAL_ON;
            }
            if (!this.sticky) {
                return this.pressed ? KEY_STATE_PRESSED : iArr;
            }
            if (this.pressed) {
                return KEY_STATE_PRESSED_OFF;
            }
            return KEY_STATE_NORMAL_OFF;
        }
    }

    public Keyboard(Context context, int i) {
        this(context, i, 0);
    }

    public Keyboard(Context context, int i, int i2, int i3, int i4) {
        this.mShiftKeys = new Key[]{null, null};
        this.mShiftKeyIndices = new int[]{-1, -1};
        this.rows = new ArrayList<>();
        this.mDisplayWidth = i3;
        this.mDisplayHeight = i4;
        this.mDefaultHorizontalGap = 0;
        int i5 = i3 / 10;
        this.mDefaultWidth = i5;
        this.mDefaultVerticalGap = 0;
        this.mDefaultHeight = i5;
        this.mKeys = new ArrayList();
        this.mModifierKeys = new ArrayList();
        this.mKeyboardMode = i2;
        loadKeyboard(context, context.getResources().getXml(i));
    }

    public Keyboard(Context context, int i, int i2) {
        this.mShiftKeys = new Key[]{null, null};
        this.mShiftKeyIndices = new int[]{-1, -1};
        this.rows = new ArrayList<>();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mDisplayWidth = displayMetrics.widthPixels;
        this.mDisplayHeight = displayMetrics.heightPixels;
        this.mDefaultHorizontalGap = 0;
        int i3 = this.mDisplayWidth / 10;
        this.mDefaultWidth = i3;
        this.mDefaultVerticalGap = 0;
        this.mDefaultHeight = i3;
        this.mKeys = new ArrayList();
        this.mModifierKeys = new ArrayList();
        this.mKeyboardMode = i2;
        loadKeyboard(context, context.getResources().getXml(i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Keyboard(Context context, int i, CharSequence charSequence, int i2, int i3) {
        this(context, i);
        this.mTotalWidth = 0;
        Row row = new Row(this);
        row.defaultHeight = this.mDefaultHeight;
        row.defaultWidth = this.mDefaultWidth;
        row.defaultHorizontalGap = this.mDefaultHorizontalGap;
        row.verticalGap = this.mDefaultVerticalGap;
        row.rowEdgeFlags = 12;
        i2 = i2 == -1 ? Integer.MAX_VALUE : i2;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < charSequence.length(); i7++) {
            int iCharAt = charSequence.charAt(i7);
            if (i5 >= i2 || this.mDefaultWidth + i6 + i3 > this.mDisplayWidth) {
                i4 += this.mDefaultVerticalGap + this.mDefaultHeight;
                i5 = 0;
                i6 = 0;
            }
            Key key = new Key(row);
            key.x = i6;
            key.y = i4;
            key.label = String.valueOf((char) iCharAt);
            key.codes = new int[]{iCharAt};
            i5++;
            i6 += key.width + key.gap;
            this.mKeys.add(key);
            row.mKeys.add(key);
            if (i6 > this.mTotalWidth) {
                this.mTotalWidth = i6;
            }
        }
        this.mTotalHeight = i4 + this.mDefaultHeight;
        this.rows.add(row);
    }

    final void resize(int i, int i2) {
        int size = this.rows.size();
        for (int i3 = 0; i3 < size; i3++) {
            Row row = this.rows.get(i3);
            int size2 = row.mKeys.size();
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < size2; i6++) {
                Key key = row.mKeys.get(i6);
                if (i6 > 0) {
                    i4 += key.gap;
                }
                i5 += key.width;
            }
            if (i4 + i5 > i) {
                float f = (i - i4) / i5;
                int i7 = 0;
                for (int i8 = 0; i8 < size2; i8++) {
                    Key key2 = row.mKeys.get(i8);
                    key2.width = (int) (key2.width * f);
                    key2.x = i7;
                    i7 += key2.width + key2.gap;
                }
            }
        }
        this.mTotalWidth = i;
    }

    public List<Key> getKeys() {
        return this.mKeys;
    }

    public List<Key> getModifierKeys() {
        return this.mModifierKeys;
    }

    protected int getHorizontalGap() {
        return this.mDefaultHorizontalGap;
    }

    protected void setHorizontalGap(int i) {
        this.mDefaultHorizontalGap = i;
    }

    protected int getVerticalGap() {
        return this.mDefaultVerticalGap;
    }

    protected void setVerticalGap(int i) {
        this.mDefaultVerticalGap = i;
    }

    protected int getKeyHeight() {
        return this.mDefaultHeight;
    }

    protected void setKeyHeight(int i) {
        this.mDefaultHeight = i;
    }

    protected int getKeyWidth() {
        return this.mDefaultWidth;
    }

    protected void setKeyWidth(int i) {
        this.mDefaultWidth = i;
    }

    public int getHeight() {
        return this.mTotalHeight;
    }

    public int getMinWidth() {
        return this.mTotalWidth;
    }

    public boolean setShifted(boolean z) {
        for (Key key : this.mShiftKeys) {
            if (key != null) {
                key.on = z;
            }
        }
        if (this.mShifted == z) {
            return false;
        }
        this.mShifted = z;
        return true;
    }

    public boolean isShifted() {
        return this.mShifted;
    }

    public int[] getShiftKeyIndices() {
        return this.mShiftKeyIndices;
    }

    public int getShiftKeyIndex() {
        return this.mShiftKeyIndices[0];
    }

    private void computeNearestNeighbors() {
        this.mCellWidth = (getMinWidth() + 9) / 10;
        this.mCellHeight = (getHeight() + 4) / 5;
        this.mGridNeighbors = new int[50][];
        int[] iArr = new int[this.mKeys.size()];
        int i = this.mCellWidth * 10;
        int i2 = this.mCellHeight * 5;
        int i3 = 0;
        while (i3 < i) {
            int i4 = 0;
            while (i4 < i2) {
                int i5 = 0;
                for (int i6 = 0; i6 < this.mKeys.size(); i6++) {
                    Key key = this.mKeys.get(i6);
                    if (key.squaredDistanceFrom(i3, i4) < this.mProximityThreshold || key.squaredDistanceFrom((this.mCellWidth + i3) - 1, i4) < this.mProximityThreshold || key.squaredDistanceFrom((this.mCellWidth + i3) - 1, (this.mCellHeight + i4) - 1) < this.mProximityThreshold || key.squaredDistanceFrom(i3, (this.mCellHeight + i4) - 1) < this.mProximityThreshold) {
                        iArr[i5] = i6;
                        i5++;
                    }
                }
                int[] iArr2 = new int[i5];
                System.arraycopy(iArr, 0, iArr2, 0, i5);
                int[][] iArr3 = this.mGridNeighbors;
                int i7 = this.mCellHeight;
                iArr3[((i4 / i7) * 10) + (i3 / this.mCellWidth)] = iArr2;
                i4 += i7;
            }
            i3 += this.mCellWidth;
        }
    }

    public int[] getNearestKeys(int i, int i2) {
        int i3;
        if (this.mGridNeighbors == null) {
            computeNearestNeighbors();
        }
        if (i >= 0 && i < getMinWidth() && i2 >= 0 && i2 < getHeight() && (i3 = ((i2 / this.mCellHeight) * 10) + (i / this.mCellWidth)) < 50) {
            return this.mGridNeighbors[i3];
        }
        return new int[0];
    }

    protected Row createRowFromXml(Resources resources, XmlResourceParser xmlResourceParser) {
        return new Row(resources, this, xmlResourceParser);
    }

    protected Key createKeyFromXml(Resources resources, Row row, int i, int i2, XmlResourceParser xmlResourceParser) {
        return new Key(resources, row, i, i2, xmlResourceParser);
    }

    private void loadKeyboard(Context context, XmlResourceParser xmlResourceParser) {
        Keyboard keyboard;
        Exception exc;
        XmlResourceParser xmlResourceParser2;
        Resources resources = context.getResources();
        Key key = null;
        int i = 0;
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        Row row = null;
        while (true) {
            try {
                int next = xmlResourceParser.next();
                if (next == 1) {
                    break;
                }
                if (next == 2) {
                    String name = xmlResourceParser.getName();
                    if (TAG_ROW.equals(name)) {
                        try {
                            Row rowCreateRowFromXml = this.createRowFromXml(resources, xmlResourceParser);
                            this.rows.add(rowCreateRowFromXml);
                            if (rowCreateRowFromXml.mode == 0 || rowCreateRowFromXml.mode == this.mKeyboardMode) {
                                i = 0;
                                xmlResourceParser2 = xmlResourceParser;
                                row = rowCreateRowFromXml;
                                z2 = true;
                            } else {
                                this.skipToEndOfRow(xmlResourceParser);
                                i = 0;
                                z2 = false;
                                xmlResourceParser2 = xmlResourceParser;
                                row = rowCreateRowFromXml;
                            }
                            keyboard = this;
                        } catch (Exception e) {
                            exc = e;
                            keyboard = this;
                            Log.e("Keyboard", "Parse error:" + exc);
                            exc.printStackTrace();
                            keyboard.mTotalHeight = i2 - keyboard.mDefaultVerticalGap;
                        }
                    } else if (TAG_KEY.equals(name)) {
                        Keyboard keyboard2 = this;
                        xmlResourceParser2 = xmlResourceParser;
                        try {
                            Key keyCreateKeyFromXml = keyboard2.createKeyFromXml(resources, row, i, i2, xmlResourceParser2);
                            keyboard = keyboard2;
                            try {
                                keyboard.mKeys.add(keyCreateKeyFromXml);
                                if (keyCreateKeyFromXml.codes[0] == -1) {
                                    int i3 = 0;
                                    while (true) {
                                        Key[] keyArr = keyboard.mShiftKeys;
                                        if (i3 >= keyArr.length) {
                                            break;
                                        }
                                        if (keyArr[i3] == null) {
                                            keyArr[i3] = keyCreateKeyFromXml;
                                            keyboard.mShiftKeyIndices[i3] = keyboard.mKeys.size() - 1;
                                            break;
                                        }
                                        i3++;
                                    }
                                    keyboard.mModifierKeys.add(keyCreateKeyFromXml);
                                } else if (keyCreateKeyFromXml.codes[0] == -6) {
                                    keyboard.mModifierKeys.add(keyCreateKeyFromXml);
                                }
                                row.mKeys.add(keyCreateKeyFromXml);
                                key = keyCreateKeyFromXml;
                                z = true;
                            } catch (Exception e2) {
                                e = e2;
                                exc = e;
                                Log.e("Keyboard", "Parse error:" + exc);
                                exc.printStackTrace();
                                keyboard.mTotalHeight = i2 - keyboard.mDefaultVerticalGap;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            keyboard = keyboard2;
                        }
                    } else {
                        xmlResourceParser2 = xmlResourceParser;
                        keyboard = this;
                        if ("Keyboard".equals(name)) {
                            keyboard.parseKeyboardAttributes(resources, xmlResourceParser2);
                        }
                    }
                } else {
                    xmlResourceParser2 = xmlResourceParser;
                    keyboard = this;
                    if (next == 3) {
                        if (z) {
                            i += key.gap + key.width;
                            if (i > keyboard.mTotalWidth) {
                                keyboard.mTotalWidth = i;
                            }
                            z = false;
                        } else if (z2) {
                            i2 = i2 + row.verticalGap + row.defaultHeight;
                            z2 = false;
                        }
                    }
                }
                this = keyboard;
                xmlResourceParser = xmlResourceParser2;
            } catch (Exception e4) {
                e = e4;
                keyboard = this;
            }
        }
        keyboard = this;
        keyboard.mTotalHeight = i2 - keyboard.mDefaultVerticalGap;
    }

    private void skipToEndOfRow(XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlResourceParser.getName().equals(TAG_ROW)) {
                return;
            }
        }
    }

    private void parseKeyboardAttributes(Resources resources, XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlResourceParser), R.styleable.Keyboard);
        int i = this.mDisplayWidth;
        this.mDefaultWidth = getDimensionOrFraction(typedArrayObtainAttributes, 0, i, i / 10);
        this.mDefaultHeight = getDimensionOrFraction(typedArrayObtainAttributes, 1, this.mDisplayHeight, 50);
        this.mDefaultHorizontalGap = getDimensionOrFraction(typedArrayObtainAttributes, 2, this.mDisplayWidth, 0);
        this.mDefaultVerticalGap = getDimensionOrFraction(typedArrayObtainAttributes, 3, this.mDisplayHeight, 0);
        int i2 = (int) (this.mDefaultWidth * SEARCH_DISTANCE);
        this.mProximityThreshold = i2 * i2;
        typedArrayObtainAttributes.recycle();
    }

    static int getDimensionOrFraction(TypedArray typedArray, int i, int i2, int i3) {
        TypedValue typedValuePeekValue = typedArray.peekValue(i);
        if (typedValuePeekValue != null) {
            if (typedValuePeekValue.type == 5) {
                return typedArray.getDimensionPixelOffset(i, i3);
            }
            if (typedValuePeekValue.type == 6) {
                return Math.round(typedArray.getFraction(i, i2, i2, i3));
            }
        }
        return i3;
    }
}
