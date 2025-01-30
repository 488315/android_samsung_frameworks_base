package android.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.hardware.display.DisplayManager;
import android.hardware.input.IInputManager;
import android.hardware.input.InputManager;
import android.p009os.Binder;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.C4337R;
import com.android.internal.util.XmlUtils;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes4.dex */
public final class PointerIcon implements Parcelable {
    public static final int HOVERING_FLAG_ALWAYSSHOW = 1;
    public static final int HOVERING_PENSELECT_POINTER_01 = 20021;
    public static final int HOVERING_SCROLLICON_POINTER_01 = 20011;
    public static final int HOVERING_SCROLLICON_POINTER_02 = 20012;
    public static final int HOVERING_SCROLLICON_POINTER_03 = 20013;
    public static final int HOVERING_SCROLLICON_POINTER_04 = 20014;
    public static final int HOVERING_SCROLLICON_POINTER_05 = 20015;
    public static final int HOVERING_SCROLLICON_POINTER_06 = 20016;
    public static final int HOVERING_SCROLLICON_POINTER_07 = 20017;
    public static final int HOVERING_SCROLLICON_POINTER_08 = 20018;
    public static final int HOVERING_SPENICON_CURSOR = 20002;
    public static final int HOVERING_SPENICON_CUSTOM = 20000;
    public static final int HOVERING_SPENICON_DEFAULT = 20001;
    public static final int HOVERING_SPENICON_DEFAULT_CUSTOM = 20022;
    public static final int HOVERING_SPENICON_DISABLE_DEFAULT_CUSTOM = 20023;
    public static final int HOVERING_SPENICON_HIDE = 20019;
    public static final int HOVERING_SPENICON_HOVERPOPUP_DEFAULT = 20020;
    public static final int HOVERING_SPENICON_MORE = 20010;
    public static final int HOVERING_SPENICON_MOVE = 20005;
    public static final int HOVERING_SPENICON_RESIZE_01 = 20006;
    public static final int HOVERING_SPENICON_RESIZE_02 = 20007;
    public static final int HOVERING_SPENICON_RESIZE_03 = 20008;
    public static final int HOVERING_SPENICON_RESIZE_04 = 20009;
    public static final int HOVERING_SPENICON_SPLIT_01 = 20003;
    public static final int HOVERING_SPENICON_SPLIT_02 = 20004;
    public static final int MOUSEICON_CURSOR = 10102;
    public static final int MOUSEICON_CUSTOM = 10100;
    public static final int MOUSEICON_DEFAULT = 10101;
    public static final int MOUSEICON_DEFAULT_KNOX_DESKTOP = 10121;
    public static final int MOUSEICON_DEFAULT_KNOX_DESKTOP_LONG = 10126;
    public static final int MOUSEICON_DEFAULT_KNOX_DESKTOP_LONG_LARGE = 10127;
    public static final int MOUSEICON_DRAWING = 10120;
    public static final int MOUSEICON_MORE = 10110;
    public static final int MOUSEICON_MOVE = 10105;
    public static final int MOUSEICON_POINTER_01 = 10111;
    public static final int MOUSEICON_POINTER_02 = 10112;
    public static final int MOUSEICON_POINTER_03 = 10113;
    public static final int MOUSEICON_POINTER_04 = 10114;
    public static final int MOUSEICON_POINTER_05 = 10115;
    public static final int MOUSEICON_POINTER_06 = 10116;
    public static final int MOUSEICON_POINTER_07 = 10117;
    public static final int MOUSEICON_POINTER_08 = 10118;
    public static final int MOUSEICON_RESIZE_01 = 10106;
    public static final int MOUSEICON_RESIZE_01_KNOX_DESKTOP = 10122;
    public static final int MOUSEICON_RESIZE_02 = 10107;
    public static final int MOUSEICON_RESIZE_02_KNOX_DESKTOP = 10123;
    public static final int MOUSEICON_RESIZE_03 = 10108;
    public static final int MOUSEICON_RESIZE_03_KNOX_DESKTOP = 10124;
    public static final int MOUSEICON_RESIZE_04 = 10109;
    public static final int MOUSEICON_RESIZE_04_KNOX_DESKTOP = 10125;
    public static final int MOUSEICON_SPLIT_01 = 10103;
    public static final int MOUSEICON_SPLIT_02 = 10104;
    public static final int MOUSEICON_TRANSPARENT = 10119;
    public static final int SEM_TYPE_STYLUS_CURSOR = 20002;
    public static final int SEM_TYPE_STYLUS_DEFAULT = 20001;
    public static final int SEM_TYPE_STYLUS_HOVERPOPUP_DEFAULT = 20020;
    public static final int SEM_TYPE_STYLUS_MORE = 20010;
    public static final int SEM_TYPE_STYLUS_MOVE = 20005;
    public static final int SEM_TYPE_STYLUS_PEN_DIRECT_WRITING = 20024;
    public static final int SEM_TYPE_STYLUS_PEN_SELECT = 20021;
    public static final int SEM_TYPE_STYLUS_RESIZE_HEIGHT = 20007;
    public static final int SEM_TYPE_STYLUS_SCROLL_DOWN = 20015;
    public static final int SEM_TYPE_STYLUS_SCROLL_LEFT = 20017;
    public static final int SEM_TYPE_STYLUS_SCROLL_RIGHT = 20013;
    public static final int SEM_TYPE_STYLUS_SCROLL_UP = 20011;
    public static final int SEM_TYPE_STYLUS_SPLIT_HORIZONTAL = 20004;
    public static final int SEM_TYPE_STYLUS_TRANSPARENT = 20019;
    public static final int STYLE_ARROW_BIG = 999;
    public static final int STYLE_SPOT_HOVERING_SPEN = 20000;
    private static final String TAG = "PointerIcon";
    public static final int TYPE_ALIAS = 1010;
    public static final int TYPE_ALL_SCROLL = 1013;
    public static final int TYPE_ARROW = 1000;
    public static final int TYPE_CELL = 1006;
    public static final int TYPE_CONTEXT_MENU = 1001;
    public static final int TYPE_COPY = 1011;
    public static final int TYPE_CROSSHAIR = 1007;
    public static final int TYPE_CUSTOM = -1;
    public static final int TYPE_DEFAULT = 1000;
    public static final int TYPE_GRAB = 1020;
    public static final int TYPE_GRABBING = 1021;
    public static final int TYPE_HAND = 1002;
    public static final int TYPE_HANDWRITING = 1022;
    public static final int TYPE_HELP = 1003;
    public static final int TYPE_HORIZONTAL_DOUBLE_ARROW = 1014;
    public static final int TYPE_NOT_SPECIFIED = 1;
    public static final int TYPE_NO_DROP = 1012;
    public static final int TYPE_NULL = 0;
    private static final int TYPE_OEM_FIRST = 10000;
    public static final int TYPE_SPOT_ANCHOR = 2002;
    public static final int TYPE_SPOT_HOVER = 2000;
    public static final int TYPE_SPOT_TOUCH = 2001;
    public static final int TYPE_TEXT = 1008;
    public static final int TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW = 1017;
    public static final int TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW = 1016;
    public static final int TYPE_VERTICAL_DOUBLE_ARROW = 1015;
    public static final int TYPE_VERTICAL_TEXT = 1009;
    public static final int TYPE_WAIT = 1004;
    public static final int TYPE_ZOOM_IN = 1018;
    public static final int TYPE_ZOOM_OUT = 1019;
    private static DisplayManager.DisplayListener sDisplayListener;
    private static IInputManager sInputManagerService;
    private Bitmap mBitmap;
    private Bitmap[] mBitmapFrames;
    private int mDisplayIdForPointerIcon;
    private DisplayManager mDisplayManager;
    private int mDurationPerFrame;
    private float mHotSpotX;
    private float mHotSpotY;
    private int mPointerIconColor;
    private float mPointerIconSizeScale;
    private int mSystemIconResourceId;
    private int mType;
    private static final Object sStaticInitInput = new Object();
    private static final PointerIcon gNullIcon = new PointerIcon(0);
    private static final SparseArray<SparseArray<PointerIcon>> gSystemIconsByDisplay = new SparseArray<>();
    private static boolean sUseLargeIcons = false;
    public static boolean sDexMode = false;
    private static float sPointerIconSizeScale = 1.0f;
    private static int sPointerIconColor = 16777215;
    public static final Parcelable.Creator<PointerIcon> CREATOR = new Parcelable.Creator<PointerIcon>() { // from class: android.view.PointerIcon.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointerIcon createFromParcel(Parcel in) {
            try {
                int type = in.readInt();
                if (type == 0) {
                    return PointerIcon.getNullIcon();
                }
                int systemIconResourceId = in.readInt();
                if (systemIconResourceId != 0) {
                    PointerIcon icon = new PointerIcon(type);
                    icon.mSystemIconResourceId = systemIconResourceId;
                    return icon;
                }
                Bitmap bitmap = Bitmap.CREATOR.createFromParcel(in);
                float hotSpotX = in.readFloat();
                float hotSpotY = in.readFloat();
                if (type == 20000) {
                    return PointerIcon.createSpenIcon(bitmap, hotSpotX, hotSpotY);
                }
                if (type == -1) {
                    return PointerIcon.createDefaultIcon(bitmap, hotSpotX, hotSpotY, type);
                }
                return PointerIcon.create(bitmap, hotSpotX, hotSpotY);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return PointerIcon.getNullIcon();
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointerIcon[] newArray(int size) {
            return new PointerIcon[size];
        }
    };

    private PointerIcon(int type) {
        this.mDisplayIdForPointerIcon = 0;
        this.mPointerIconSizeScale = 1.0f;
        this.mPointerIconColor = 16777215;
        this.mType = type;
    }

    public static PointerIcon getNullIcon() {
        return gNullIcon;
    }

    public static PointerIcon getDefaultIcon(Context context) {
        return getSystemIcon(context, 1000);
    }

    public static PointerIcon getSystemIcon(Context context, int type) {
        TypedArray a;
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (type == 0) {
            return gNullIcon;
        }
        if (sDisplayListener == null) {
            registerDisplayListener(context);
        }
        int displayId = context.getDisplayId();
        SparseArray<SparseArray<PointerIcon>> sparseArray = gSystemIconsByDisplay;
        SparseArray<PointerIcon> systemIcons = sparseArray.get(displayId);
        if (systemIcons == null) {
            systemIcons = new SparseArray<>();
            sparseArray.put(displayId, systemIcons);
        }
        if (sUseLargeIcons && sDexMode) {
            switch (type) {
                case 10121:
                    type = 1000;
                    break;
                case 10122:
                    type = 1014;
                    break;
                case 10123:
                    type = 1015;
                    break;
                case 10124:
                    type = 1017;
                    break;
                case 10125:
                    type = 1016;
                    break;
                case 10126:
                    type = 10127;
                    break;
            }
        }
        PointerIcon icon = systemIcons.get(type);
        if (icon != null) {
            return icon;
        }
        int typeIndex = getSystemIconTypeIndex(type);
        if (typeIndex == -1) {
            if (type >= 20000) {
                typeIndex = getSystemIconTypeIndex(20001);
            } else if (type >= 10000) {
                typeIndex = getSystemIconTypeIndex(10121);
            } else {
                typeIndex = getSystemIconTypeIndex(1000);
            }
        }
        if (type >= 10000 || (CoreRune.FW_DIRECT_WRITING && type == 1022)) {
            a = context.obtainStyledAttributes(null, C4337R.styleable.DeviceDefault_Pointer, C4337R.attr.zzz_DeviceDefaultPointerStyle, 0);
        } else {
            int defStyleRes = sUseLargeIcons ? C4337R.style.LargePointer : C4337R.style.Pointer;
            a = context.obtainStyledAttributes(null, C4337R.styleable.Pointer, 0, defStyleRes);
        }
        int resourceId = a.getResourceId(typeIndex, -1);
        a.recycle();
        if (resourceId == -1) {
            Log.m102w(TAG, "Missing theme resources for pointer icon type " + type);
            return type == 1000 ? gNullIcon : getSystemIcon(context, 1000);
        }
        PointerIcon icon2 = new PointerIcon(type);
        if (((-16777216) & resourceId) == 16777216) {
            icon2.mSystemIconResourceId = resourceId;
        } else {
            icon2.loadResource(context, context.getResources(), resourceId);
        }
        systemIcons.append(type, icon2);
        return icon2;
    }

    public static void setCustomIcons(int color, float size) {
        sPointerIconColor = color;
        sPointerIconSizeScale = size;
        Log.m98i(TAG, "Changes PoinerIcons color=0x" + Integer.toHexString(sPointerIconColor) + " size=" + sPointerIconSizeScale);
        gSystemIconsByDisplay.clear();
    }

    private static Bitmap resizeBitmap(Bitmap bitmap, float scaleSize) {
        int width = (int) (bitmap.getWidth() * scaleSize);
        int height = (int) (bitmap.getHeight() * scaleSize);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        bitmap.recycle();
        return resizedBitmap;
    }

    public static void clearSystemIcons() {
        Log.m94d(TAG, "clearSystemIcons");
        gSystemIconsByDisplay.clear();
        InputManager.getInstance().setPointerIconType(10119);
    }

    public static void setUseLargeIcons(boolean use) {
        sUseLargeIcons = use;
        gSystemIconsByDisplay.clear();
    }

    public static PointerIcon create(Bitmap bitmap, float hotSpotX, float hotSpotY) {
        return createIcon(bitmap, hotSpotX, hotSpotY, -1);
    }

    private static PointerIcon createIcon(Bitmap bitmap, float hotSpotX, float hotSpotY, int type) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap must not be null");
        }
        float scaledHotSoptX = hotSpotX;
        float scaledHotSoptY = hotSpotY;
        if (!sUseLargeIcons && ((type == -1 || type == 10100) && sPointerIconSizeScale > 1.0f && !bitmap.isRecycled())) {
            float f = sPointerIconSizeScale;
            scaledHotSoptX = hotSpotX * f;
            scaledHotSoptY = hotSpotY * f;
            bitmap = resizeBitmap(bitmap, f);
        }
        validateHotSpot(bitmap, scaledHotSoptX, scaledHotSoptY);
        PointerIcon icon = new PointerIcon(type);
        icon.mBitmap = bitmap;
        icon.mHotSpotX = hotSpotX;
        icon.mHotSpotY = hotSpotY;
        return icon;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PointerIcon createSpenIcon(Bitmap bitmap, float hotSpotX, float hotSpotY) {
        return createIcon(bitmap, hotSpotX, hotSpotY, 20000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PointerIcon createDefaultIcon(Bitmap bitmap, float hotSpotX, float hotSpotY, int type) {
        return createIcon(bitmap, hotSpotX, hotSpotY, type);
    }

    public static PointerIcon load(Resources resources, int resourceId) {
        if (resources == null) {
            throw new IllegalArgumentException("resources must not be null");
        }
        PointerIcon icon = new PointerIcon(-1);
        icon.loadResource(null, resources, resourceId);
        return icon;
    }

    public PointerIcon load(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (this.mSystemIconResourceId == 0 || this.mBitmap != null) {
            return this;
        }
        try {
            if (getInputManagerService() != null) {
                this.mDisplayIdForPointerIcon = sInputManagerService.getDisplayIdForPointerIcon();
            }
            DisplayManager displayManager = new DisplayManager(context);
            this.mDisplayManager = displayManager;
            Display displayForPointerIcon = displayManager.getDisplay(this.mDisplayIdForPointerIcon);
            Context displayContext = context.createDisplayContext(displayForPointerIcon);
            PointerIcon result = new PointerIcon(this.mType);
            result.mSystemIconResourceId = this.mSystemIconResourceId;
            result.loadResource(displayContext, displayContext.getResources(), this.mSystemIconResourceId);
            return result;
        } catch (RemoteException e) {
            return null;
        }
    }

    public int getType() {
        return this.mType;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        try {
            out.writeInt(this.mType);
            if (this.mType != 0) {
                out.writeInt(this.mSystemIconResourceId);
                if (this.mSystemIconResourceId == 0) {
                    this.mBitmap.writeToParcel(out, flags);
                    out.writeFloat(this.mHotSpotX);
                    out.writeFloat(this.mHotSpotY);
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public boolean equals(Object other) {
        int i;
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof PointerIcon)) {
            return false;
        }
        PointerIcon otherIcon = (PointerIcon) other;
        if (this.mType != otherIcon.mType || (i = this.mSystemIconResourceId) != otherIcon.mSystemIconResourceId) {
            return false;
        }
        if (i != 0 || ((this.mBitmap == null || otherIcon.mBitmap.isRecycled() || this.mBitmap.sameAs(otherIcon.mBitmap)) && this.mHotSpotX == otherIcon.mHotSpotX && this.mHotSpotY == otherIcon.mHotSpotY)) {
            return true;
        }
        return false;
    }

    private Bitmap getBitmapFromDrawable(BitmapDrawable bitmapDrawable) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        float width = bitmapDrawable.getIntrinsicWidth();
        float height = bitmapDrawable.getIntrinsicHeight();
        float f = this.mPointerIconSizeScale;
        int scaledWidth = (int) (width * f);
        int scaledHeight = (int) (f * height);
        if (scaledWidth == bitmap.getWidth() && scaledHeight == bitmap.getHeight()) {
            return bitmap;
        }
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF dst = new RectF(0.0f, 0.0f, scaledWidth, scaledHeight);
        Bitmap scaled = Bitmap.createBitmap(scaledWidth, scaledHeight, bitmap.getConfig());
        Canvas canvas = new Canvas(scaled);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, src, dst, paint);
        return scaled;
    }

    private Bitmap getBitmapFromVectorDrawable(VectorDrawable vectorDrawable) {
        float width = vectorDrawable.getIntrinsicWidth();
        float height = vectorDrawable.getIntrinsicHeight();
        float f = this.mPointerIconSizeScale;
        int scaledWidth = (int) (width * f);
        int scaledHeight = (int) (f * height);
        Bitmap bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private void loadResource(Context context, Resources resources, int resourceId) {
        Drawable drawable;
        Bitmap bitmap;
        Drawable drawable2;
        XmlResourceParser parser = resources.getXml(resourceId);
        try {
            try {
                XmlUtils.beginDocument(parser, "pointer-icon");
                TypedArray a = resources.obtainAttributes(parser, C4337R.styleable.PointerIcon);
                int bitmapRes = a.getResourceId(0, 0);
                float hotSpotX = a.getDimension(1, 0.0f);
                float hotSpotY = a.getDimension(2, 0.0f);
                a.recycle();
                if (bitmapRes == 0) {
                    throw new IllegalArgumentException("<pointer-icon> is missing bitmap attribute.");
                }
                if (context == null) {
                    drawable = resources.getDrawable(bitmapRes, null);
                } else {
                    drawable = context.getDrawable(bitmapRes);
                }
                if (sUseLargeIcons || this.mType >= 20000) {
                    this.mPointerIconSizeScale = 1.0f;
                } else {
                    this.mPointerIconSizeScale = sPointerIconSizeScale;
                }
                if (drawable instanceof AnimationDrawable) {
                    AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
                    int frames = animationDrawable.getNumberOfFrames();
                    Drawable drawable3 = animationDrawable.getFrame(0);
                    if (frames != 1) {
                        this.mDurationPerFrame = animationDrawable.getDuration(0);
                        this.mBitmapFrames = new Bitmap[frames - 1];
                        int width = drawable3.getIntrinsicWidth();
                        int height = drawable3.getIntrinsicHeight();
                        for (int i = 1; i < frames; i++) {
                            Drawable drawableFrame = animationDrawable.getFrame(i);
                            if (!(drawableFrame instanceof BitmapDrawable)) {
                                throw new IllegalArgumentException("Frame of an animated pointer icon must refer to a bitmap drawable.");
                            }
                            if (drawableFrame.getIntrinsicWidth() != width || drawableFrame.getIntrinsicHeight() != height) {
                                throw new IllegalArgumentException("The bitmap size of " + i + "-th frame is different. All frames should have the exact same size and share the same hotspot.");
                            }
                            BitmapDrawable bitmapDrawableFrame = (BitmapDrawable) drawableFrame;
                            this.mBitmapFrames[i - 1] = getBitmapFromDrawable(bitmapDrawableFrame);
                        }
                        drawable2 = drawable3;
                    } else {
                        Log.m102w(TAG, "Animation icon with single frame -- simply treating the first frame as a normal bitmap icon.");
                        drawable2 = drawable3;
                    }
                    drawable = drawable2;
                }
                boolean changeColor = (sUseLargeIcons || sPointerIconColor == this.mPointerIconColor || this.mType >= 20000) ? false : true;
                if (drawable instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    Bitmap tempBitmap = getBitmapFromDrawable(bitmapDrawable);
                    if (changeColor) {
                        bitmap = tempBitmap.copy(Bitmap.Config.ARGB_8888, true);
                        this.mPointerIconColor = sPointerIconColor;
                        Paint paint = new Paint();
                        paint.setColorFilter(new PorterDuffColorFilter(this.mPointerIconColor, PorterDuff.Mode.MULTIPLY));
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                    } else {
                        bitmap = tempBitmap;
                    }
                } else if (drawable instanceof VectorDrawable) {
                    VectorDrawable vectorDrawable = (VectorDrawable) drawable;
                    if (changeColor) {
                        vectorDrawable.mutate();
                        int i2 = sPointerIconColor;
                        this.mPointerIconColor = i2;
                        if (i2 != -16777216) {
                            vectorDrawable.setPathFillColor("body", i2);
                            vectorDrawable.invalidateSelf();
                        }
                    }
                    bitmap = getBitmapFromVectorDrawable(vectorDrawable);
                } else {
                    throw new IllegalArgumentException("<pointer-icon> bitmap attribute must refer to a bitmap or vector drawable.");
                }
                float f = this.mPointerIconSizeScale;
                float scaledHotSoptX = hotSpotX * f;
                float scaledHotSoptY = f * hotSpotY;
                validateHotSpot(bitmap, scaledHotSoptX, scaledHotSoptY);
                this.mBitmap = bitmap;
                this.mHotSpotX = scaledHotSoptX;
                this.mHotSpotY = scaledHotSoptY;
            } catch (Exception ex) {
                throw new IllegalArgumentException("Exception parsing pointer icon resource.", ex);
            }
        } finally {
            parser.close();
        }
    }

    public String toString() {
        return "PointerIcon{type=" + typeToString(this.mType) + ", hotspotX=" + this.mHotSpotX + ", hotspotY=" + this.mHotSpotY + ", systemIconResourceId=" + this.mSystemIconResourceId + "}";
    }

    private static void validateHotSpot(Bitmap bitmap, float hotSpotX, float hotSpotY) {
        if (hotSpotX < 0.0f || hotSpotX >= bitmap.getWidth()) {
            throw new IllegalArgumentException("x hotspot lies outside of the bitmap area");
        }
        if (hotSpotY < 0.0f || hotSpotY >= bitmap.getHeight()) {
            throw new IllegalArgumentException("y hotspot lies outside of the bitmap area");
        }
    }

    private static int getSystemIconTypeIndex(int type) {
        switch (type) {
            case 1000:
                return 2;
            case 1001:
                return 4;
            case 1002:
                return 9;
            case 1003:
                return 11;
            case 1004:
                return 22;
            case 1006:
                return 3;
            case 1007:
                return 6;
            case 1008:
                return 17;
            case 1009:
                return 21;
            case 1010:
                return 0;
            case 1011:
                return 5;
            case 1012:
                return 13;
            case 1013:
                return 1;
            case 1014:
                return 12;
            case 1015:
                return 20;
            case 1016:
                return 19;
            case 1017:
                return 18;
            case 1018:
                return 23;
            case 1019:
                return 24;
            case 1020:
                return 7;
            case 1021:
                return 8;
            case 1022:
                return CoreRune.FW_DIRECT_WRITING ? 5 : 10;
            case 2000:
                return 15;
            case 2001:
                return 16;
            case 2002:
                return 14;
            case 10101:
                return 4;
            case 10102:
                return 0;
            case 10103:
                return 34;
            case MOUSEICON_SPLIT_02 /* 10104 */:
                return 35;
            case 10105:
                return 8;
            case 10106:
                return 14;
            case 10107:
                return 15;
            case MOUSEICON_RESIZE_03 /* 10108 */:
                return 16;
            case 10109:
                return 17;
            case MOUSEICON_MORE /* 10110 */:
                return 7;
            case 10111:
                return 26;
            case 10112:
                return 27;
            case 10113:
                return 28;
            case 10114:
                return 29;
            case 10115:
                return 30;
            case MOUSEICON_POINTER_06 /* 10116 */:
                return 31;
            case 10117:
                return 32;
            case 10118:
                return 33;
            case 10119:
                return 47;
            case 10120:
                return 6;
            case 10121:
                return 1;
            case 10122:
                return 10;
            case 10123:
                return 11;
            case 10124:
                return 12;
            case 10125:
                return 13;
            case 10126:
                return 2;
            case 10127:
                return 3;
            case 20001:
                return 36;
            case 20002:
                return 37;
            case 20003:
                return 45;
            case 20004:
                return 46;
            case 20005:
                return 40;
            case HOVERING_SPENICON_RESIZE_01 /* 20006 */:
                return 41;
            case 20007:
                return 42;
            case HOVERING_SPENICON_RESIZE_03 /* 20008 */:
                return 43;
            case HOVERING_SPENICON_RESIZE_04 /* 20009 */:
                return 44;
            case 20010:
                return 39;
            case 20011:
                return 18;
            case HOVERING_SCROLLICON_POINTER_02 /* 20012 */:
                return 19;
            case 20013:
                return 20;
            case HOVERING_SCROLLICON_POINTER_04 /* 20014 */:
                return 21;
            case 20015:
                return 22;
            case HOVERING_SCROLLICON_POINTER_06 /* 20016 */:
                return 23;
            case 20017:
                return 24;
            case HOVERING_SCROLLICON_POINTER_08 /* 20018 */:
                return 25;
            case 20019:
                return 38;
            case 20021:
                return 9;
            case SEM_TYPE_STYLUS_PEN_DIRECT_WRITING /* 20024 */:
                return 5;
            default:
                return -1;
        }
    }

    private static void registerDisplayListener(Context context) {
        sDisplayListener = new DisplayManager.DisplayListener() { // from class: android.view.PointerIcon.2
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int displayId) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int displayId) {
                PointerIcon.gSystemIconsByDisplay.remove(displayId);
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int displayId) {
                PointerIcon.gSystemIconsByDisplay.remove(displayId);
            }
        };
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        displayManager.registerDisplayListener(sDisplayListener, null);
    }

    public static void semSetDefaultPointerIcon(int toolType, PointerIcon icon) {
        setDefaultPointerIconInternal(toolType, icon, false);
    }

    public static void setDefaultPointerIconInternal(int toolType, PointerIcon icon, boolean forced) {
        try {
            if (getInputManagerService() == null) {
                Log.m94d(TAG, "setDefaultPointerIconInternal failed to get IMS");
                return;
            }
            int iconType = icon != null ? icon.mType : 0;
            Log.m94d(TAG, "setDefaultPointerIconInternal toolType : " + toolType + ", icon : " + icon + ", forced : " + forced + ", calling pid = " + Binder.getCallingPid());
            boolean isToolTypeStylus = toolType == 2;
            int defaultType = isToolTypeStylus ? 20001 : 1000;
            if (iconType == -1 && isToolTypeStylus) {
                icon.mType = 20000;
            }
            sInputManagerService.setDefaultPointerIcon(toolType, icon, forced);
            InputManager.getInstance().setPointerIconType(defaultType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static IInputManager getInputManagerService() {
        IInputManager iInputManager;
        synchronized (sStaticInitInput) {
            if (sInputManagerService == null) {
                sInputManagerService = IInputManager.Stub.asInterface(ServiceManager.getService("input"));
            }
            iInputManager = sInputManagerService;
        }
        return iInputManager;
    }

    void setType(int type) {
        this.mType = type;
    }

    public static String typeToString(int type) {
        switch (type) {
            case -1:
                return "CUSTOM";
            case 0:
                return "NULL";
            case 1:
                return "NOT_SPECIFIED";
            case 1000:
                return "ARROW";
            case 1001:
                return "CONTEXT_MENU";
            case 1002:
                return "HAND";
            case 1003:
                return "HELP";
            case 1004:
                return "WAIT";
            case 1006:
                return "CELL";
            case 1007:
                return "CROSSHAIR";
            case 1008:
                return "TEXT";
            case 1009:
                return "VERTICAL_TEXT";
            case 1010:
                return "ALIAS";
            case 1011:
                return "COPY";
            case 1012:
                return "NO_DROP";
            case 1013:
                return "ALL_SCROLL";
            case 1014:
                return "HORIZONTAL_DOUBLE_ARROW";
            case 1015:
                return "VERTICAL_DOUBLE_ARROW";
            case 1016:
                return "TOP_RIGHT_DIAGONAL_DOUBLE_ARROW";
            case 1017:
                return "TOP_LEFT_DIAGONAL_DOUBLE_ARROW";
            case 1018:
                return "ZOOM_IN";
            case 1019:
                return "ZOOM_OUT";
            case 1020:
                return "GRAB";
            case 1021:
                return "GRABBING";
            case 2000:
                return "SPOT_HOVER";
            case 2001:
                return "SPOT_TOUCH";
            case 2002:
                return "SPOT_ANCHOR";
            default:
                return Integer.toString(type);
        }
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public float getHotSpotX() {
        return this.mHotSpotX;
    }

    public float getHotSpotY() {
        return this.mHotSpotY;
    }

    public static void setDexMode(boolean enabled) {
        sDexMode = enabled;
    }

    private static final int hidden_SEM_TYPE_STYLUS_DEFAULT() {
        return 20001;
    }

    private static final int hidden_SEM_TYPE_STYLUS_MORE() {
        return 20010;
    }

    private static final int hidden_SEM_TYPE_STYLUS_PEN_SELECT() {
        return 20021;
    }

    private static final int hidden_SEM_TYPE_STYLUS_SCROLL_DOWN() {
        return 20015;
    }

    private static final int hidden_SEM_TYPE_STYLUS_SCROLL_LEFT() {
        return 20017;
    }

    private static final int hidden_SEM_TYPE_STYLUS_SCROLL_RIGHT() {
        return 20013;
    }

    private static final int hidden_SEM_TYPE_STYLUS_SCROLL_UP() {
        return 20011;
    }
}
