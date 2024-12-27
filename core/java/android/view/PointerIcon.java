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
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.R;
import com.android.internal.util.XmlUtils;

import com.samsung.android.rune.CoreRune;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PointerIcon implements Parcelable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final float DEFAULT_POINTER_SCALE = 1.0f;
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
    public static final float LARGE_POINTER_SCALE = 2.5f;
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
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_BEGIN = 0;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_BLACK = 0;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_BLUE = 4;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_END = 4;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_GREEN = 1;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_PINK = 3;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_YELLOW = 2;
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
    private static IInputManager sInputManagerService;
    private Bitmap mBitmap;
    private Bitmap[] mBitmapFrames;
    private DisplayManager mDisplayManager;
    private boolean mDrawNativeDropShadow;
    private int mDurationPerFrame;
    private float mHotSpotX;
    private float mHotSpotY;
    private int mType;
    private static final Object sStaticInitInput = new Object();
    public static boolean sDexMode = false;
    private static float sPointerIconSizeScale = 1.0f;
    private static int sPointerIconColor = 16777215;
    private static final SparseArray<PointerIcon> SYSTEM_ICONS = new SparseArray<>();
    public static final Parcelable.Creator<PointerIcon> CREATOR =
            new Parcelable.Creator<PointerIcon>() { // from class: android.view.PointerIcon.1
                @Override // android.os.Parcelable.Creator
                public PointerIcon createFromParcel(Parcel in) {
                    PointerIcon icon;
                    try {
                        int type = in.readInt();
                        if (type != -1 && type != 20000) {
                            return PointerIcon.getSystemIcon(type);
                        }
                        if (type == 20000) {
                            icon =
                                    PointerIcon.createSpenIcon(
                                            Bitmap.CREATOR.createFromParcel(in),
                                            in.readFloat(),
                                            in.readFloat());
                        } else {
                            icon =
                                    PointerIcon.create(
                                            Bitmap.CREATOR.createFromParcel(in),
                                            in.readFloat(),
                                            in.readFloat());
                        }
                        icon.mDrawNativeDropShadow = in.readBoolean();
                        return icon;
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        return PointerIcon.getSystemIcon(0);
                    }
                }

                @Override // android.os.Parcelable.Creator
                public PointerIcon[] newArray(int size) {
                    return new PointerIcon[size];
                }
            };
    private int mDisplayIdForPointerIcon = 0;
    private float mPointerIconSizeScale = 1.0f;
    private int mPointerIconColor = 16777215;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PointerIconVectorStyleFill {}

    private PointerIcon(int type) {
        this.mType = type;
    }

    public static PointerIcon getSystemIcon(Context context, int type) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        return getSystemIcon(type);
    }

    public static PointerIcon getSystemIcon(int type) {
        if (type == -1) {
            throw new IllegalArgumentException("cannot get system icon for TYPE_CUSTOM");
        }
        if (sDexMode) {
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
        PointerIcon icon = SYSTEM_ICONS.get(type);
        if (icon == null) {
            PointerIcon icon2 = new PointerIcon(type);
            SYSTEM_ICONS.put(type, icon2);
            return icon2;
        }
        return icon;
    }

    public static PointerIcon getLoadedSystemIcon(
            Context context, int type, boolean useLargeIcons, float pointerScale) {
        TypedArray a;
        int defStyle;
        if (type == 1) {
            throw new IllegalStateException("Cannot load icon for type TYPE_NOT_SPECIFIED");
        }
        if (type == -1) {
            throw new IllegalArgumentException("Custom icons must be loaded when they're created");
        }
        int typeIndex = getSystemIconTypeIndex(type);
        if (typeIndex < 0) {
            if (type >= 20000) {
                typeIndex = getSystemIconTypeIndex(20001);
            } else if (type >= 10000) {
                typeIndex = getSystemIconTypeIndex(10121);
            } else {
                typeIndex = getSystemIconTypeIndex(1000);
            }
        }
        if (type >= 10000 || (CoreRune.DIRECT_WRITING && type == 1022)) {
            a =
                    context.obtainStyledAttributes(
                            null,
                            R.styleable.DeviceDefault_Pointer,
                            R.attr.zzz_DeviceDefaultPointerStyle,
                            0);
        } else {
            if (useLargeIcons) {
                defStyle = R.style.LargePointer;
            } else {
                defStyle = R.style.Pointer;
            }
            a = context.obtainStyledAttributes(null, R.styleable.Pointer, 0, defStyle);
        }
        int resourceId = a.getResourceId(typeIndex, -1);
        a.recycle();
        if (resourceId == -1) {
            Log.w(TAG, "Missing theme resources for pointer icon type " + type);
            if (type == 1000) {
                return getSystemIcon(0);
            }
            return getLoadedSystemIcon(context, 1000, useLargeIcons, pointerScale);
        }
        PointerIcon icon = new PointerIcon(type);
        icon.loadResource(context.getResources(), resourceId, context.getTheme(), pointerScale);
        return icon;
    }

    private boolean isLoaded() {
        return this.mBitmap != null
                && this.mHotSpotX >= 0.0f
                && this.mHotSpotX < ((float) this.mBitmap.getWidth())
                && this.mHotSpotY >= 0.0f
                && this.mHotSpotY < ((float) this.mBitmap.getHeight());
    }

    public static PointerIcon create(Bitmap bitmap, float hotSpotX, float hotSpotY) {
        return createIcon(bitmap, hotSpotX, hotSpotY, -1);
    }

    private static PointerIcon createIcon(Bitmap bitmap, float hotSpotX, float hotSpotY, int type) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap must not be null");
        }
        validateHotSpot(bitmap, hotSpotX, hotSpotY, false);
        if ((type == -1 || type == 10100) && sPointerIconSizeScale > 1.0f && !bitmap.isRecycled()) {
            float f = sPointerIconSizeScale * hotSpotX;
            float scaledHotSoptX = sPointerIconSizeScale;
            float f2 = scaledHotSoptX * hotSpotY;
            float scaledHotSoptY = sPointerIconSizeScale;
            bitmap = resizeBitmap(bitmap, scaledHotSoptY);
        }
        PointerIcon icon = new PointerIcon(-1);
        icon.mBitmap = bitmap;
        icon.mHotSpotX = hotSpotX;
        icon.mHotSpotY = hotSpotY;
        return icon;
    }

    public static PointerIcon load(Resources resources, int resourceId) {
        if (resources == null) {
            throw new IllegalArgumentException("resources must not be null");
        }
        PointerIcon icon = new PointerIcon(-1);
        icon.loadResource(resources, resourceId, null, 1.0f);
        return icon;
    }

    public int getType() {
        return this.mType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        try {
            out.writeInt(this.mType);
            if (this.mType != -1 && this.mType != 20000) {
                return;
            }
            if (!isLoaded()) {
                throw new IllegalStateException("Custom icon should be loaded upon creation");
            }
            this.mBitmap.writeToParcel(out, flags);
            out.writeFloat(this.mHotSpotX);
            out.writeFloat(this.mHotSpotY);
            out.writeBoolean(this.mDrawNativeDropShadow);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof PointerIcon)) {
            return false;
        }
        PointerIcon otherIcon = (PointerIcon) other;
        if (this.mType != otherIcon.mType) {
            return false;
        }
        if ((this.mBitmap == null
                        || otherIcon.mBitmap.isRecycled()
                        || this.mBitmap.sameAs(otherIcon.mBitmap))
                && this.mHotSpotX == otherIcon.mHotSpotX
                && this.mHotSpotY == otherIcon.mHotSpotY) {
            return true;
        }
        return false;
    }

    private Bitmap getBitmapFromDrawable(BitmapDrawable bitmapDrawable) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        float width = bitmapDrawable.getIntrinsicWidth();
        float height = bitmapDrawable.getIntrinsicHeight();
        int scaledWidth = (int) (this.mPointerIconSizeScale * width);
        int scaledHeight = (int) (this.mPointerIconSizeScale * height);
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

    private BitmapDrawable getBitmapDrawableFromVectorDrawable(
            Resources resources, VectorDrawable vectorDrawable, float pointerScale) {
        Bitmap bitmap =
                Bitmap.createBitmap(
                        resources.getDisplayMetrics(),
                        (int) (vectorDrawable.getIntrinsicWidth() * pointerScale),
                        (int) (vectorDrawable.getIntrinsicHeight() * pointerScale),
                        Bitmap.Config.ARGB_8888,
                        true);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return new BitmapDrawable(resources, bitmap);
    }

    private void loadResource(
            Resources resources, int resourceId, Resources.Theme theme, float pointerScale) {
        Bitmap bitmap;
        Drawable drawable;
        Drawable drawable2;
        Resources resources2 = resources;
        XmlResourceParser parser = resources.getXml(resourceId);
        try {
            try {
                XmlUtils.beginDocument(parser, "pointer-icon");
                TypedArray a = resources2.obtainAttributes(parser, R.styleable.PointerIcon);
                int bitmapRes = a.getResourceId(0, 0);
                float hotSpotX = a.getDimension(1, 0.0f) * pointerScale;
                float hotSpotY = a.getDimension(2, 0.0f) * pointerScale;
                a.recycle();
                if (bitmapRes == 0) {
                    throw new IllegalArgumentException(
                            "<pointer-icon> is missing bitmap attribute.");
                }
                Drawable drawable3 = resources2.getDrawable(bitmapRes, null);
                if (this.mType >= 20000) {
                    this.mPointerIconSizeScale = 1.0f;
                } else {
                    this.mPointerIconSizeScale = sPointerIconSizeScale;
                }
                if (drawable3 instanceof AnimationDrawable) {
                    AnimationDrawable animationDrawable = (AnimationDrawable) drawable3;
                    int frames = animationDrawable.getNumberOfFrames();
                    Drawable drawable4 = animationDrawable.getFrame(0);
                    if (frames != 1) {
                        this.mDurationPerFrame = animationDrawable.getDuration(0);
                        this.mBitmapFrames = new Bitmap[frames - 1];
                        int width = drawable4.getIntrinsicWidth();
                        int height = drawable4.getIntrinsicHeight();
                        boolean isVectorAnimation = drawable4 instanceof VectorDrawable;
                        this.mDrawNativeDropShadow = isVectorAnimation;
                        int i = 1;
                        while (i < frames) {
                            Drawable drawableFrame = animationDrawable.getFrame(i);
                            if (drawableFrame instanceof BitmapDrawable) {
                                drawable2 = drawable4;
                            } else {
                                if (!(drawableFrame instanceof VectorDrawable)) {
                                    throw new IllegalArgumentException(
                                            "Frame of an animated pointer icon must refer to a"
                                                + " bitmap drawable or vector drawable.");
                                }
                                drawable2 = drawable4;
                            }
                            if (isVectorAnimation != (drawableFrame instanceof VectorDrawable)) {
                                throw new IllegalArgumentException(
                                        "The drawable of the "
                                                + i
                                                + "-th frame is a different type from the others."
                                                + " All frames should be the same type.");
                            }
                            if (drawableFrame.getIntrinsicWidth() != width
                                    || drawableFrame.getIntrinsicHeight() != height) {
                                throw new IllegalArgumentException(
                                        "The bitmap size of "
                                                + i
                                                + "-th frame is different. All frames should have"
                                                + " the exact same size and share the same"
                                                + " hotspot.");
                            }
                            if (isVectorAnimation) {
                                drawableFrame =
                                        getBitmapDrawableFromVectorDrawable(
                                                resources2,
                                                (VectorDrawable) drawableFrame,
                                                pointerScale);
                            }
                            this.mBitmapFrames[i - 1] =
                                    getBitmapFromDrawable((BitmapDrawable) drawableFrame);
                            i++;
                            resources2 = resources;
                            drawable4 = drawable2;
                        }
                        drawable = drawable4;
                    } else {
                        Log.w(
                                TAG,
                                "Animation icon with single frame -- simply treating the first"
                                    + " frame as a normal bitmap icon.");
                        drawable = drawable4;
                    }
                    drawable3 = drawable;
                }
                boolean changeColor =
                        sPointerIconColor != this.mPointerIconColor && this.mType < 20000;
                if (drawable3 instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable3;
                    Bitmap tempBitmap = getBitmapFromDrawable(bitmapDrawable);
                    if (changeColor) {
                        bitmap = tempBitmap.copy(Bitmap.Config.ARGB_8888, true);
                        this.mPointerIconColor = sPointerIconColor;
                        Paint paint = new Paint();
                        paint.setColorFilter(
                                new PorterDuffColorFilter(
                                        this.mPointerIconColor, PorterDuff.Mode.MULTIPLY));
                        Canvas canvas = new Canvas(bitmap);
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                    } else {
                        bitmap = tempBitmap;
                    }
                } else if (drawable3 instanceof VectorDrawable) {
                    VectorDrawable vectorDrawable = (VectorDrawable) drawable3;
                    if (changeColor) {
                        vectorDrawable.mutate();
                        this.mPointerIconColor = sPointerIconColor;
                        if (this.mPointerIconColor != -16777216) {
                            vectorDrawable.setPathFillColor("body", this.mPointerIconColor);
                            vectorDrawable.invalidateSelf();
                        }
                    }
                    bitmap = getBitmapFromVectorDrawable(vectorDrawable);
                } else {
                    throw new IllegalArgumentException(
                            "<pointer-icon> bitmap attribute must refer to a bitmap or vector"
                                + " drawable.");
                }
                float scaledHotSoptX = this.mPointerIconSizeScale * hotSpotX;
                float scaledHotSoptY = this.mPointerIconSizeScale * hotSpotY;
                validateHotSpot(bitmap, scaledHotSoptX, scaledHotSoptY, true);
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
        return "PointerIcon{type="
                + typeToString(this.mType)
                + ", hotspotX="
                + this.mHotSpotX
                + ", hotspotY="
                + this.mHotSpotY
                + "}";
    }

    private static void validateHotSpot(
            Bitmap bitmap, float hotSpotX, float hotSpotY, boolean isScaled) {
        if (hotSpotX < 0.0f
                || (!isScaled
                        ? hotSpotX >= bitmap.getWidth()
                        : ((int) hotSpotX) > bitmap.getWidth())) {
            throw new IllegalArgumentException("x hotspot lies outside of the bitmap area");
        }
        if (hotSpotY >= 0.0f) {
            if (isScaled) {
                if (((int) hotSpotY) <= bitmap.getHeight()) {
                    return;
                }
            } else if (hotSpotY < bitmap.getHeight()) {
                return;
            }
        }
        throw new IllegalArgumentException("y hotspot lies outside of the bitmap area");
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
                return CoreRune.DIRECT_WRITING ? 5 : 10;
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
            case 10104:
                return 35;
            case 10105:
                return 8;
            case 10106:
                return 14;
            case 10107:
                return 15;
            case 10108:
                return 16;
            case 10109:
                return 17;
            case 10110:
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
            case 10116:
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
            case 20006:
                return 41;
            case 20007:
                return 42;
            case 20008:
                return 43;
            case 20009:
                return 44;
            case 20010:
                return 39;
            case 20011:
                return 18;
            case 20012:
                return 19;
            case 20013:
                return 20;
            case 20014:
                return 21;
            case 20015:
                return 22;
            case 20016:
                return 23;
            case 20017:
                return 24;
            case 20018:
                return 25;
            case 20019:
                return 38;
            case 20021:
                return 9;
            case 20024:
                return 5;
            default:
                return -1;
        }
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
            case 1022:
                return "HANDWRITING";
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

    public static int vectorFillStyleToResource(int fillStyle) {
        switch (fillStyle) {
            case 0:
            default:
                return R.style.PointerIconVectorStyleFillBlack;
            case 1:
                return R.style.PointerIconVectorStyleFillGreen;
            case 2:
                return R.style.PointerIconVectorStyleFillYellow;
            case 3:
                return R.style.PointerIconVectorStyleFillPink;
            case 4:
                return R.style.PointerIconVectorStyleFillBlue;
        }
    }

    public void setDrawNativeDropShadow(boolean drawNativeDropShadow) {
        this.mDrawNativeDropShadow = drawNativeDropShadow;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public static void setCustomIcons(int color, float size) {
        sPointerIconColor = color;
        sPointerIconSizeScale = size;
        Log.i(
                TAG,
                "Changes PoinerIcons color=0x"
                        + Integer.toHexString(sPointerIconColor)
                        + " size="
                        + sPointerIconSizeScale);
    }

    private static Bitmap resizeBitmap(Bitmap bitmap, float scaleSize) {
        int width = (int) (bitmap.getWidth() * scaleSize);
        int height = (int) (bitmap.getHeight() * scaleSize);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        bitmap.recycle();
        return resizedBitmap;
    }

    public static void clearSystemIcons() {
        Log.d(TAG, "clearSystemIcons");
        InputManager.getInstance().setPointerIconType(10119);
    }

    public static PointerIcon createSpenIcon(Bitmap bitmap, float hotSpotX, float hotSpotY) {
        return createIcon(bitmap, hotSpotX, hotSpotY, 20000);
    }

    private static PointerIcon createDefaultIcon(
            Bitmap bitmap, float hotSpotX, float hotSpotY, int type) {
        return createIcon(bitmap, hotSpotX, hotSpotY, type);
    }

    private Bitmap getBitmapFromVectorDrawable(VectorDrawable vectorDrawable) {
        float width = vectorDrawable.getIntrinsicWidth();
        float height = vectorDrawable.getIntrinsicHeight();
        int scaledWidth = (int) (this.mPointerIconSizeScale * width);
        int scaledHeight = (int) (this.mPointerIconSizeScale * height);
        Bitmap bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static void semSetDefaultPointerIcon(int toolType, PointerIcon icon) {
        setDefaultPointerIconInternal(toolType, icon, false);
    }

    public static void setDefaultPointerIconInternal(
            int toolType, PointerIcon icon, boolean forced) {
        try {
            if (getInputManagerService() == null) {
                Log.d(TAG, "setDefaultPointerIconInternal failed to get IMS");
                return;
            }
            int iconType = icon != null ? icon.mType : 0;
            Log.d(
                    TAG,
                    "setDefaultPointerIconInternal toolType : "
                            + toolType
                            + ", icon : "
                            + icon
                            + ", forced : "
                            + forced
                            + ", calling pid = "
                            + Binder.getCallingPid());
            boolean isToolTypeStylus = toolType == 2;
            if (isToolTypeStylus) {}
            if (iconType == -1 && isToolTypeStylus) {
                icon.mType = 20000;
            }
            sInputManagerService.setDefaultPointerIcon(toolType, icon, forced);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static IInputManager getInputManagerService() {
        IInputManager iInputManager;
        synchronized (sStaticInitInput) {
            if (sInputManagerService == null) {
                sInputManagerService =
                        IInputManager.Stub.asInterface(ServiceManager.getService("input"));
            }
            iInputManager = sInputManagerService;
        }
        return iInputManager;
    }

    void setType(int type) {
        this.mType = type;
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
