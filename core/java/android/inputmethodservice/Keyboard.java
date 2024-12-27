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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Deprecated
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

        public Row(Keyboard parent) {
            this.parent = parent;
        }

        public Row(Resources res, Keyboard parent, XmlResourceParser parser) {
            this.parent = parent;
            TypedArray a = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard);
            this.defaultWidth =
                    Keyboard.getDimensionOrFraction(
                            a, 0, parent.mDisplayWidth, parent.mDefaultWidth);
            this.defaultHeight =
                    Keyboard.getDimensionOrFraction(
                            a, 1, parent.mDisplayHeight, parent.mDefaultHeight);
            this.defaultHorizontalGap =
                    Keyboard.getDimensionOrFraction(
                            a, 2, parent.mDisplayWidth, parent.mDefaultHorizontalGap);
            this.verticalGap =
                    Keyboard.getDimensionOrFraction(
                            a, 3, parent.mDisplayHeight, parent.mDefaultVerticalGap);
            a.recycle();
            TypedArray a2 =
                    res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard_Row);
            this.rowEdgeFlags = a2.getInt(0, 0);
            this.mode = a2.getResourceId(1, 0);
            a2.recycle();
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

        public Key(Row parent) {
            this.keyboard = parent.parent;
            this.height = parent.defaultHeight;
            this.width = parent.defaultWidth;
            this.gap = parent.defaultHorizontalGap;
            this.edgeFlags = parent.rowEdgeFlags;
        }

        public Key(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
            this(parent);
            this.x = x;
            this.y = y;
            TypedArray a = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard);
            this.width =
                    Keyboard.getDimensionOrFraction(
                            a, 0, this.keyboard.mDisplayWidth, parent.defaultWidth);
            this.height =
                    Keyboard.getDimensionOrFraction(
                            a, 1, this.keyboard.mDisplayHeight, parent.defaultHeight);
            this.gap =
                    Keyboard.getDimensionOrFraction(
                            a, 2, this.keyboard.mDisplayWidth, parent.defaultHorizontalGap);
            a.recycle();
            TypedArray a2 =
                    res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard_Key);
            this.x += this.gap;
            TypedValue codesValue = new TypedValue();
            a2.getValue(0, codesValue);
            if (codesValue.type == 16 || codesValue.type == 17) {
                this.codes = new int[] {codesValue.data};
            } else if (codesValue.type == 3) {
                this.codes = parseCSV(codesValue.string.toString());
            }
            this.iconPreview = a2.getDrawable(7);
            if (this.iconPreview != null) {
                this.iconPreview.setBounds(
                        0,
                        0,
                        this.iconPreview.getIntrinsicWidth(),
                        this.iconPreview.getIntrinsicHeight());
            }
            this.popupCharacters = a2.getText(2);
            this.popupResId = a2.getResourceId(1, 0);
            this.repeatable = a2.getBoolean(6, false);
            this.modifier = a2.getBoolean(4, false);
            this.sticky = a2.getBoolean(5, false);
            this.edgeFlags = a2.getInt(3, 0);
            this.edgeFlags |= parent.rowEdgeFlags;
            this.icon = a2.getDrawable(10);
            if (this.icon != null) {
                this.icon.setBounds(
                        0, 0, this.icon.getIntrinsicWidth(), this.icon.getIntrinsicHeight());
            }
            this.label = a2.getText(9);
            this.text = a2.getText(8);
            if (this.codes == null && !TextUtils.isEmpty(this.label)) {
                this.codes = new int[] {this.label.charAt(0)};
            }
            a2.recycle();
        }

        public void onPressed() {
            this.pressed = !this.pressed;
        }

        public void onReleased(boolean inside) {
            this.pressed = !this.pressed;
            if (this.sticky && inside) {
                this.on = !this.on;
            }
        }

        int[] parseCSV(String value) {
            int indexOf;
            int count = 0;
            int lastIndex = 0;
            if (value.length() > 0) {
                do {
                    count++;
                    indexOf = value.indexOf(",", lastIndex + 1);
                    lastIndex = indexOf;
                } while (indexOf > 0);
            }
            int[] values = new int[count];
            int count2 = 0;
            StringTokenizer st = new StringTokenizer(value, ",");
            while (st.hasMoreTokens()) {
                int count3 = count2 + 1;
                try {
                    values[count2] = Integer.parseInt(st.nextToken());
                } catch (NumberFormatException e) {
                    Log.e("Keyboard", "Error parsing keycodes " + value);
                }
                count2 = count3;
            }
            return values;
        }

        public boolean isInside(int x, int y) {
            boolean leftEdge = (this.edgeFlags & 1) > 0;
            boolean rightEdge = (this.edgeFlags & 2) > 0;
            boolean topEdge = (this.edgeFlags & 4) > 0;
            boolean bottomEdge = (this.edgeFlags & 8) > 0;
            return (x >= this.x || (leftEdge && x <= this.x + this.width))
                    && (x < this.x + this.width || (rightEdge && x >= this.x))
                    && ((y >= this.y || (topEdge && y <= this.y + this.height))
                            && (y < this.y + this.height || (bottomEdge && y >= this.y)));
        }

        public int squaredDistanceFrom(int x, int y) {
            int xDist = (this.x + (this.width / 2)) - x;
            int yDist = (this.y + (this.height / 2)) - y;
            return (xDist * xDist) + (yDist * yDist);
        }

        public int[] getCurrentDrawableState() {
            int[] states = KEY_STATE_NORMAL;
            if (this.on) {
                if (this.pressed) {
                    int[] states2 = KEY_STATE_PRESSED_ON;
                    return states2;
                }
                int[] states3 = KEY_STATE_NORMAL_ON;
                return states3;
            }
            if (this.sticky) {
                if (this.pressed) {
                    int[] states4 = KEY_STATE_PRESSED_OFF;
                    return states4;
                }
                int[] states5 = KEY_STATE_NORMAL_OFF;
                return states5;
            }
            if (this.pressed) {
                int[] states6 = KEY_STATE_PRESSED;
                return states6;
            }
            return states;
        }
    }

    public Keyboard(Context context, int xmlLayoutResId) {
        this(context, xmlLayoutResId, 0);
    }

    public Keyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        this.mShiftKeys = new Key[] {null, null};
        this.mShiftKeyIndices = new int[] {-1, -1};
        this.rows = new ArrayList<>();
        this.mDisplayWidth = width;
        this.mDisplayHeight = height;
        this.mDefaultHorizontalGap = 0;
        this.mDefaultWidth = this.mDisplayWidth / 10;
        this.mDefaultVerticalGap = 0;
        this.mDefaultHeight = this.mDefaultWidth;
        this.mKeys = new ArrayList();
        this.mModifierKeys = new ArrayList();
        this.mKeyboardMode = modeId;
        loadKeyboard(context, context.getResources().getXml(xmlLayoutResId));
    }

    public Keyboard(Context context, int xmlLayoutResId, int modeId) {
        this.mShiftKeys = new Key[] {null, null};
        this.mShiftKeyIndices = new int[] {-1, -1};
        this.rows = new ArrayList<>();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        this.mDisplayWidth = dm.widthPixels;
        this.mDisplayHeight = dm.heightPixels;
        this.mDefaultHorizontalGap = 0;
        this.mDefaultWidth = this.mDisplayWidth / 10;
        this.mDefaultVerticalGap = 0;
        this.mDefaultHeight = this.mDefaultWidth;
        this.mKeys = new ArrayList();
        this.mModifierKeys = new ArrayList();
        this.mKeyboardMode = modeId;
        loadKeyboard(context, context.getResources().getXml(xmlLayoutResId));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Keyboard(
            Context context,
            int layoutTemplateResId,
            CharSequence characters,
            int columns,
            int horizontalPadding) {
        this(context, layoutTemplateResId);
        int x = 0;
        int y = 0;
        int column = 0;
        this.mTotalWidth = 0;
        Row row = new Row(this);
        row.defaultHeight = this.mDefaultHeight;
        row.defaultWidth = this.mDefaultWidth;
        row.defaultHorizontalGap = this.mDefaultHorizontalGap;
        row.verticalGap = this.mDefaultVerticalGap;
        row.rowEdgeFlags = 12;
        int maxColumns = columns == -1 ? Integer.MAX_VALUE : columns;
        for (int i = 0; i < characters.length(); i++) {
            int charAt = characters.charAt(i);
            if (column >= maxColumns
                    || this.mDefaultWidth + x + horizontalPadding > this.mDisplayWidth) {
                x = 0;
                y += this.mDefaultVerticalGap + this.mDefaultHeight;
                column = 0;
            }
            Key key = new Key(row);
            key.x = x;
            key.y = y;
            key.label = String.valueOf((char) charAt);
            key.codes = new int[] {charAt};
            column++;
            x += key.width + key.gap;
            this.mKeys.add(key);
            row.mKeys.add(key);
            if (x > this.mTotalWidth) {
                this.mTotalWidth = x;
            }
        }
        int i2 = this.mDefaultHeight;
        this.mTotalHeight = i2 + y;
        this.rows.add(row);
    }

    final void resize(int newWidth, int newHeight) {
        int numRows = this.rows.size();
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            Row row = this.rows.get(rowIndex);
            int numKeys = row.mKeys.size();
            int totalGap = 0;
            int totalWidth = 0;
            for (int keyIndex = 0; keyIndex < numKeys; keyIndex++) {
                Key key = row.mKeys.get(keyIndex);
                if (keyIndex > 0) {
                    totalGap += key.gap;
                }
                totalWidth += key.width;
            }
            int keyIndex2 = totalGap + totalWidth;
            if (keyIndex2 > newWidth) {
                int x = 0;
                float scaleFactor = (newWidth - totalGap) / totalWidth;
                for (int keyIndex3 = 0; keyIndex3 < numKeys; keyIndex3++) {
                    Key key2 = row.mKeys.get(keyIndex3);
                    key2.width = (int) (key2.width * scaleFactor);
                    key2.x = x;
                    x += key2.width + key2.gap;
                }
            }
        }
        this.mTotalWidth = newWidth;
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

    protected void setHorizontalGap(int gap) {
        this.mDefaultHorizontalGap = gap;
    }

    protected int getVerticalGap() {
        return this.mDefaultVerticalGap;
    }

    protected void setVerticalGap(int gap) {
        this.mDefaultVerticalGap = gap;
    }

    protected int getKeyHeight() {
        return this.mDefaultHeight;
    }

    protected void setKeyHeight(int height) {
        this.mDefaultHeight = height;
    }

    protected int getKeyWidth() {
        return this.mDefaultWidth;
    }

    protected void setKeyWidth(int width) {
        this.mDefaultWidth = width;
    }

    public int getHeight() {
        return this.mTotalHeight;
    }

    public int getMinWidth() {
        return this.mTotalWidth;
    }

    public boolean setShifted(boolean shiftState) {
        for (Key shiftKey : this.mShiftKeys) {
            if (shiftKey != null) {
                shiftKey.on = shiftState;
            }
        }
        if (this.mShifted == shiftState) {
            return false;
        }
        this.mShifted = shiftState;
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
        this.mCellWidth = ((getMinWidth() + 10) - 1) / 10;
        this.mCellHeight = ((getHeight() + 5) - 1) / 5;
        this.mGridNeighbors = new int[50][];
        int[] indices = new int[this.mKeys.size()];
        int gridWidth = this.mCellWidth * 10;
        int gridHeight = this.mCellHeight * 5;
        int x = 0;
        while (x < gridWidth) {
            int y = 0;
            while (y < gridHeight) {
                int count = 0;
                for (int i = 0; i < this.mKeys.size(); i++) {
                    Key key = this.mKeys.get(i);
                    if (key.squaredDistanceFrom(x, y) < this.mProximityThreshold
                            || key.squaredDistanceFrom((this.mCellWidth + x) - 1, y)
                                    < this.mProximityThreshold
                            || key.squaredDistanceFrom(
                                            (this.mCellWidth + x) - 1, (this.mCellHeight + y) - 1)
                                    < this.mProximityThreshold
                            || key.squaredDistanceFrom(x, (this.mCellHeight + y) - 1)
                                    < this.mProximityThreshold) {
                        indices[count] = i;
                        count++;
                    }
                }
                int[] cell = new int[count];
                System.arraycopy(indices, 0, cell, 0, count);
                this.mGridNeighbors[((y / this.mCellHeight) * 10) + (x / this.mCellWidth)] = cell;
                int count2 = this.mCellHeight;
                y += count2;
            }
            int y2 = this.mCellWidth;
            x += y2;
        }
    }

    public int[] getNearestKeys(int x, int y) {
        int index;
        if (this.mGridNeighbors == null) {
            computeNearestNeighbors();
        }
        if (x >= 0
                && x < getMinWidth()
                && y >= 0
                && y < getHeight()
                && (index = ((y / this.mCellHeight) * 10) + (x / this.mCellWidth)) < 50) {
            return this.mGridNeighbors[index];
        }
        return new int[0];
    }

    protected Row createRowFromXml(Resources res, XmlResourceParser parser) {
        return new Row(res, this, parser);
    }

    protected Key createKeyFromXml(
            Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        return new Key(res, parent, x, y, parser);
    }

    private void loadKeyboard(Context context, XmlResourceParser parser) {
        boolean leftMostKey;
        int row;
        int y;
        Key key;
        boolean leftMostKey2 = false;
        Resources res = context.getResources();
        int row2 = 0;
        int x = 0;
        int y2 = 0;
        boolean inRow = false;
        boolean inKey = false;
        Key key2 = null;
        Row currentRow = null;
        while (true) {
            try {
                int event = parser.next();
                if (event == 1) {
                    break;
                }
                if (event == 2) {
                    String tag = parser.getName();
                    if (TAG_ROW.equals(tag)) {
                        try {
                            currentRow = createRowFromXml(res, parser);
                            this.rows.add(currentRow);
                            boolean skipRow =
                                    (currentRow.mode == 0 || currentRow.mode == this.mKeyboardMode)
                                            ? false
                                            : true;
                            if (skipRow) {
                                try {
                                    skipToEndOfRow(parser);
                                    leftMostKey = leftMostKey2;
                                    x = 0;
                                    row = row2;
                                    y = y2;
                                    inRow = false;
                                } catch (Exception e) {
                                    e = e;
                                    Log.e("Keyboard", "Parse error:" + e);
                                    e.printStackTrace();
                                    this.mTotalHeight = y2 - this.mDefaultVerticalGap;
                                }
                            } else {
                                leftMostKey = leftMostKey2;
                                x = 0;
                                row = row2;
                                y = y2;
                                inRow = true;
                            }
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } else if (TAG_KEY.equals(tag)) {
                        leftMostKey = leftMostKey2;
                        row = row2;
                        Row currentRow2 = currentRow;
                        y = y2;
                        try {
                            key = createKeyFromXml(res, currentRow, x, y2, parser);
                        } catch (Exception e3) {
                            e = e3;
                            y2 = y;
                        }
                        try {
                            this.mKeys.add(key);
                            if (key.codes[0] == -1) {
                                int i = 0;
                                while (true) {
                                    if (i >= this.mShiftKeys.length) {
                                        break;
                                    }
                                    if (this.mShiftKeys[i] == null) {
                                        this.mShiftKeys[i] = key;
                                        this.mShiftKeyIndices[i] = this.mKeys.size() - 1;
                                        break;
                                    }
                                    i++;
                                }
                                this.mModifierKeys.add(key);
                            } else if (key.codes[0] == -6) {
                                this.mModifierKeys.add(key);
                            }
                            currentRow2.mKeys.add(key);
                            key2 = key;
                            currentRow = currentRow2;
                            inKey = true;
                        } catch (Exception e4) {
                            e = e4;
                            y2 = y;
                            Log.e("Keyboard", "Parse error:" + e);
                            e.printStackTrace();
                            this.mTotalHeight = y2 - this.mDefaultVerticalGap;
                        }
                    } else {
                        leftMostKey = leftMostKey2;
                        row = row2;
                        y = y2;
                        Row currentRow3 = currentRow;
                        Key key3 = key2;
                        try {
                            if ("Keyboard".equals(tag)) {
                                parseKeyboardAttributes(res, parser);
                            }
                            currentRow = currentRow3;
                            key2 = key3;
                        } catch (Exception e5) {
                            e = e5;
                            y2 = y;
                            Log.e("Keyboard", "Parse error:" + e);
                            e.printStackTrace();
                            this.mTotalHeight = y2 - this.mDefaultVerticalGap;
                        }
                    }
                    leftMostKey2 = leftMostKey;
                    row2 = row;
                    y2 = y;
                } else {
                    boolean leftMostKey3 = leftMostKey2;
                    int row3 = row2;
                    int y3 = y2;
                    Row currentRow4 = currentRow;
                    Key key4 = key2;
                    if (event == 3) {
                        if (inKey) {
                            inKey = false;
                            x += key4.gap + key4.width;
                            if (x > this.mTotalWidth) {
                                this.mTotalWidth = x;
                            }
                        } else if (inRow) {
                            inRow = false;
                            int y4 = y3 + currentRow4.verticalGap;
                            try {
                                int y5 = currentRow4.defaultHeight + y4;
                                int y6 = row3 + 1;
                                currentRow = currentRow4;
                                key2 = key4;
                                leftMostKey2 = leftMostKey3;
                                y2 = y5;
                                row2 = y6;
                            } catch (Exception e6) {
                                e = e6;
                                y2 = y4;
                                Log.e("Keyboard", "Parse error:" + e);
                                e.printStackTrace();
                                this.mTotalHeight = y2 - this.mDefaultVerticalGap;
                            }
                        }
                    }
                    currentRow = currentRow4;
                    key2 = key4;
                    leftMostKey2 = leftMostKey3;
                    row2 = row3;
                    y2 = y3;
                }
            } catch (Exception e7) {
                e = e7;
                y2 = y2;
            }
        }
        y2 = y2;
        this.mTotalHeight = y2 - this.mDefaultVerticalGap;
    }

    private void skipToEndOfRow(XmlResourceParser parser)
            throws XmlPullParserException, IOException {
        while (true) {
            int event = parser.next();
            if (event != 1) {
                if (event == 3 && parser.getName().equals(TAG_ROW)) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void parseKeyboardAttributes(Resources res, XmlResourceParser parser) {
        TypedArray a = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard);
        this.mDefaultWidth =
                getDimensionOrFraction(a, 0, this.mDisplayWidth, this.mDisplayWidth / 10);
        this.mDefaultHeight = getDimensionOrFraction(a, 1, this.mDisplayHeight, 50);
        this.mDefaultHorizontalGap = getDimensionOrFraction(a, 2, this.mDisplayWidth, 0);
        this.mDefaultVerticalGap = getDimensionOrFraction(a, 3, this.mDisplayHeight, 0);
        this.mProximityThreshold = (int) (this.mDefaultWidth * SEARCH_DISTANCE);
        this.mProximityThreshold *= this.mProximityThreshold;
        a.recycle();
    }

    static int getDimensionOrFraction(TypedArray a, int index, int base, int defValue) {
        TypedValue value = a.peekValue(index);
        if (value == null) {
            return defValue;
        }
        if (value.type == 5) {
            return a.getDimensionPixelOffset(index, defValue);
        }
        if (value.type == 6) {
            return Math.round(a.getFraction(index, base, base, defValue));
        }
        return defValue;
    }
}
