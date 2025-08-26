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

/* loaded from: classes4.dex */
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
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_END = 5;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_GREEN = 1;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_PINK = 3;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_PURPLE = 5;
    public static final int POINTER_ICON_VECTOR_STYLE_FILL_RED = 2;
    public static final int POINTER_ICON_VECTOR_STYLE_STROKE_BEGIN = 0;
    public static final int POINTER_ICON_VECTOR_STYLE_STROKE_BLACK = 1;
    public static final int POINTER_ICON_VECTOR_STYLE_STROKE_END = 2;
    public static final int POINTER_ICON_VECTOR_STYLE_STROKE_NONE = 2;
    public static final int POINTER_ICON_VECTOR_STYLE_STROKE_WHITE = 0;
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
    public static boolean sDexMode = false;
    private static IInputManager sInputManagerService = null;
    private static int sPointerIconColor = 16777215;
    private static float sPointerIconSizeScale = 1.0f;
    private Bitmap mBitmap;
    private Bitmap[] mBitmapFrames;
    private DisplayManager mDisplayManager;
    private boolean mDrawNativeDropShadow;
    private int mDurationPerFrame;
    private float mHotSpotX;
    private float mHotSpotY;
    private int mType;
    private static final Object sStaticInitInput = new Object();
    private static final SparseArray<PointerIcon> SYSTEM_ICONS = new SparseArray<>();
    public static final Parcelable.Creator<PointerIcon> CREATOR = new Parcelable.Creator<PointerIcon>() { // from class: android.view.PointerIcon.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointerIcon createFromParcel(Parcel parcel) {
            PointerIcon pointerIconCreate;
            try {
                int i = parcel.readInt();
                if (i != -1 && i != 20000) {
                    return PointerIcon.getSystemIcon(i);
                }
                if (i == 20000) {
                    pointerIconCreate = PointerIcon.createSpenIcon(Bitmap.CREATOR.createFromParcel(parcel), parcel.readFloat(), parcel.readFloat());
                } else {
                    pointerIconCreate = PointerIcon.create(Bitmap.CREATOR.createFromParcel(parcel), parcel.readFloat(), parcel.readFloat());
                }
                pointerIconCreate.mDrawNativeDropShadow = parcel.readBoolean();
                return pointerIconCreate;
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return PointerIcon.getSystemIcon(0);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointerIcon[] newArray(int i) {
            return new PointerIcon[i];
        }
    };
    private int mDisplayIdForPointerIcon = 0;
    private float mPointerIconSizeScale = 1.0f;
    private int mPointerIconColor = 16777215;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PointerIconVectorStyleFill {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PointerIconVectorStyleStroke {
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

    public static int vectorFillStyleToResource(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? R.style.PointerIconVectorStyleFillBlack : R.style.PointerIconVectorStyleFillPurple : R.style.PointerIconVectorStyleFillBlue : R.style.PointerIconVectorStyleFillPink : R.style.PointerIconVectorStyleFillRed : R.style.PointerIconVectorStyleFillGreen : R.style.PointerIconVectorStyleFillBlack;
    }

    public static int vectorStrokeStyleToResource(int i) {
        return i != 0 ? i != 1 ? i != 2 ? R.style.PointerIconVectorStyleStrokeWhite : R.style.PointerIconVectorStyleStrokeNone : R.style.PointerIconVectorStyleStrokeBlack : R.style.PointerIconVectorStyleStrokeWhite;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PointerIcon(int i) {
        this.mType = i;
    }

    public static PointerIcon getSystemIcon(Context context, int i) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        return getSystemIcon(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PointerIcon getSystemIcon(int i) {
        if (i == -1) {
            throw new IllegalArgumentException("cannot get system icon for TYPE_CUSTOM");
        }
        if (sDexMode) {
            switch (i) {
                case 10121:
                    i = 1000;
                    break;
                case 10122:
                    i = 1014;
                    break;
                case 10123:
                    i = 1015;
                    break;
                case 10124:
                    i = 1017;
                    break;
                case 10125:
                    i = 1016;
                    break;
                case 10126:
                    i = 10127;
                    break;
            }
        }
        SparseArray<PointerIcon> sparseArray = SYSTEM_ICONS;
        PointerIcon pointerIcon = sparseArray.get(i);
        if (pointerIcon != null) {
            return pointerIcon;
        }
        PointerIcon pointerIcon2 = new PointerIcon(i);
        sparseArray.put(i, pointerIcon2);
        return pointerIcon2;
    }

    public static PointerIcon getLoadedSystemIcon(Context context, int i, boolean z, float f) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes;
        if (i == 1) {
            throw new IllegalStateException("Cannot load icon for type TYPE_NOT_SPECIFIED");
        }
        if (i == -1) {
            throw new IllegalArgumentException("Custom icons must be loaded when they're created");
        }
        int systemIconTypeIndex = getSystemIconTypeIndex(i);
        if (systemIconTypeIndex < 0) {
            if (i >= 20000) {
                systemIconTypeIndex = getSystemIconTypeIndex(20001);
            } else if (i >= 10000) {
                systemIconTypeIndex = getSystemIconTypeIndex(10121);
            } else {
                systemIconTypeIndex = getSystemIconTypeIndex(1000);
            }
        }
        if (i >= 10000 || (CoreRune.DIRECT_WRITING && i == 1022)) {
            typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.DeviceDefault_Pointer, R.attr.zzz_DeviceDefaultPointerStyle, 0);
        } else {
            typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.Pointer, 0, z ? R.style.LargePointer : R.style.Pointer);
        }
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(systemIconTypeIndex, -1);
        typedArrayObtainStyledAttributes.recycle();
        if (resourceId == -1) {
            Log.w(TAG, "Missing theme resources for pointer icon type " + i);
            if (i == 1000) {
                return getSystemIcon(0);
            }
            return getLoadedSystemIcon(context, 1000, z, f);
        }
        PointerIcon pointerIcon = new PointerIcon(i);
        pointerIcon.loadResource(context.getResources(), resourceId, context.getTheme(), f);
        return pointerIcon;
    }

    private boolean isLoaded() {
        if (this.mBitmap == null) {
            return false;
        }
        float f = this.mHotSpotX;
        if (f < 0.0f || f >= r0.getWidth()) {
            return false;
        }
        float f2 = this.mHotSpotY;
        return f2 >= 0.0f && f2 < ((float) this.mBitmap.getHeight());
    }

    public static PointerIcon create(Bitmap bitmap, float f, float f2) {
        return createIcon(bitmap, f, f2, -1);
    }

    private static PointerIcon createIcon(Bitmap bitmap, float f, float f2, int i) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap must not be null");
        }
        validateHotSpot(bitmap, f, f2, false);
        if ((i == -1 || i == 10100) && sPointerIconSizeScale > 1.0f && !bitmap.isRecycled()) {
            bitmap = resizeBitmap(bitmap, sPointerIconSizeScale);
        }
        PointerIcon pointerIcon = new PointerIcon(-1);
        pointerIcon.mBitmap = bitmap;
        pointerIcon.mHotSpotX = f;
        pointerIcon.mHotSpotY = f2;
        return pointerIcon;
    }

    public static PointerIcon load(Resources resources, int i) throws Resources.NotFoundException {
        if (resources == null) {
            throw new IllegalArgumentException("resources must not be null");
        }
        PointerIcon pointerIcon = new PointerIcon(-1);
        pointerIcon.loadResource(resources, i, null, 1.0f);
        return pointerIcon;
    }

    public int getType() {
        return this.mType;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeInt(this.mType);
            int i2 = this.mType;
            if (i2 == -1 || i2 == 20000) {
                if (!isLoaded()) {
                    throw new IllegalStateException("Custom icon should be loaded upon creation");
                }
                this.mBitmap.writeToParcel(parcel, i);
                parcel.writeFloat(this.mHotSpotX);
                parcel.writeFloat(this.mHotSpotY);
                parcel.writeBoolean(this.mDrawNativeDropShadow);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof PointerIcon)) {
            PointerIcon pointerIcon = (PointerIcon) obj;
            if (this.mType != pointerIcon.mType) {
                return false;
            }
            if ((this.mBitmap == null || pointerIcon.mBitmap.isRecycled() || this.mBitmap.sameAs(pointerIcon.mBitmap)) && this.mHotSpotX == pointerIcon.mHotSpotX && this.mHotSpotY == pointerIcon.mHotSpotY) {
                return true;
            }
        }
        return false;
    }

    private Bitmap getBitmapFromDrawable(BitmapDrawable bitmapDrawable) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        float intrinsicWidth = bitmapDrawable.getIntrinsicWidth();
        float intrinsicHeight = bitmapDrawable.getIntrinsicHeight();
        float f = this.mPointerIconSizeScale;
        int i = (int) (intrinsicWidth * f);
        int i2 = (int) (intrinsicHeight * f);
        if (i == bitmap.getWidth() && i2 == bitmap.getHeight()) {
            return bitmap;
        }
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(0.0f, 0.0f, i, i2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, bitmap.getConfig());
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        return bitmapCreateBitmap;
    }

    private BitmapDrawable getBitmapDrawableFromVectorDrawable(Resources resources, VectorDrawable vectorDrawable, float f) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(resources.getDisplayMetrics(), (int) (vectorDrawable.getIntrinsicWidth() * f), (int) (vectorDrawable.getIntrinsicHeight() * f), Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return new BitmapDrawable(resources, bitmapCreateBitmap);
    }

    private void loadResource(Resources resources, int i, Resources.Theme theme, float f) throws Resources.NotFoundException {
        Bitmap bitmapFromVectorDrawable;
        XmlResourceParser xml = resources.getXml(i);
        try {
            try {
                XmlUtils.beginDocument(xml, "pointer-icon");
                TypedArray typedArrayObtainAttributes = resources.obtainAttributes(xml, R.styleable.PointerIcon);
                int resourceId = typedArrayObtainAttributes.getResourceId(0, 0);
                float dimension = (int) typedArrayObtainAttributes.getDimension(1, 0.0f);
                float dimension2 = (int) typedArrayObtainAttributes.getDimension(2, 0.0f);
                typedArrayObtainAttributes.recycle();
                if (resourceId == 0) {
                    throw new IllegalArgumentException("<pointer-icon> is missing bitmap attribute.");
                }
                Drawable drawable = resources.getDrawable(resourceId, null);
                if (this.mType >= 20000) {
                    this.mPointerIconSizeScale = 1.0f;
                } else {
                    this.mPointerIconSizeScale = sPointerIconSizeScale;
                }
                if (drawable instanceof AnimationDrawable) {
                    AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
                    int numberOfFrames = animationDrawable.getNumberOfFrames();
                    Drawable frame = animationDrawable.getFrame(0);
                    if (numberOfFrames == 1) {
                        Log.w(TAG, "Animation icon with single frame -- simply treating the first frame as a normal bitmap icon.");
                    } else {
                        this.mDurationPerFrame = animationDrawable.getDuration(0);
                        this.mBitmapFrames = new Bitmap[numberOfFrames - 1];
                        boolean z = frame instanceof VectorDrawable;
                        this.mDrawNativeDropShadow = z;
                        for (int i2 = 1; i2 < numberOfFrames; i2++) {
                            Drawable frame2 = animationDrawable.getFrame(i2);
                            if (!(frame2 instanceof BitmapDrawable) && !(frame2 instanceof VectorDrawable)) {
                                throw new IllegalArgumentException("Frame of an animated pointer icon must refer to a bitmap drawable or vector drawable.");
                            }
                            if (z != (frame2 instanceof VectorDrawable)) {
                                throw new IllegalArgumentException("The drawable of the " + i2 + "-th frame is a different type from the others. All frames should be the same type.");
                            }
                            if (z) {
                                frame2 = getBitmapDrawableFromVectorDrawable(resources, (VectorDrawable) frame2, f);
                            }
                            this.mBitmapFrames[i2 - 1] = getBitmapFromDrawable((BitmapDrawable) frame2);
                        }
                    }
                    drawable = frame;
                }
                boolean z2 = sPointerIconColor != this.mPointerIconColor && this.mType < 20000;
                if (drawable instanceof BitmapDrawable) {
                    bitmapFromVectorDrawable = getBitmapFromDrawable((BitmapDrawable) drawable);
                    if (z2) {
                        bitmapFromVectorDrawable = bitmapFromVectorDrawable.copy(Bitmap.Config.ARGB_8888, true);
                        this.mPointerIconColor = sPointerIconColor;
                        Paint paint = new Paint();
                        paint.setColorFilter(new PorterDuffColorFilter(this.mPointerIconColor, PorterDuff.Mode.MULTIPLY));
                        new Canvas(bitmapFromVectorDrawable).drawBitmap(bitmapFromVectorDrawable, 0.0f, 0.0f, paint);
                    }
                } else if (drawable instanceof VectorDrawable) {
                    VectorDrawable vectorDrawable = (VectorDrawable) drawable;
                    if (z2) {
                        vectorDrawable.mutate();
                        int i3 = sPointerIconColor;
                        this.mPointerIconColor = i3;
                        if (i3 != -16777216) {
                            vectorDrawable.setPathFillColor("body", i3);
                            vectorDrawable.invalidateSelf();
                        }
                    }
                    bitmapFromVectorDrawable = getBitmapFromVectorDrawable(vectorDrawable);
                } else {
                    throw new IllegalArgumentException("<pointer-icon> bitmap attribute must refer to a bitmap or vector drawable.");
                }
                float f2 = this.mPointerIconSizeScale;
                float f3 = dimension * f2;
                float f4 = f2 * dimension2;
                Log.i(TAG, "Load pointerIcon type " + this.mType + ", hotSpotX : " + dimension + ", hotSpotY : " + dimension2 + ", bitmap.getWidth() : " + bitmapFromVectorDrawable.getWidth() + ", bitmap.getHeight() : " + bitmapFromVectorDrawable.getHeight() + ", mPointerIconSizeScale : " + this.mPointerIconSizeScale);
                validateHotSpot(bitmapFromVectorDrawable, f3, f4, true);
                this.mBitmap = bitmapFromVectorDrawable;
                this.mHotSpotX = f3;
                this.mHotSpotY = f4;
            } catch (Exception e) {
                throw new IllegalArgumentException("Exception parsing pointer icon resource.", e);
            }
        } finally {
            xml.close();
        }
    }

    public String toString() {
        return "PointerIcon{type=" + typeToString(this.mType) + ", hotspotX=" + this.mHotSpotX + ", hotspotY=" + this.mHotSpotY + "}";
    }

    private static void validateHotSpot(Bitmap bitmap, float f, float f2, boolean z) {
        if (f < 0.0f || (!z ? f >= bitmap.getWidth() : ((int) f) > bitmap.getWidth())) {
            throw new IllegalArgumentException("x hotspot lies outside of the bitmap area");
        }
        if (f2 >= 0.0f) {
            if (z) {
                if (((int) f2) <= bitmap.getHeight()) {
                    return;
                }
            } else if (f2 < bitmap.getHeight()) {
                return;
            }
        }
        throw new IllegalArgumentException("y hotspot lies outside of the bitmap area");
    }

    private static int getSystemIconTypeIndex(int i) {
        if (i == 20021) {
            return 9;
        }
        if (i == 20024) {
            return 5;
        }
        switch (i) {
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
            default:
                switch (i) {
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
                    default:
                        switch (i) {
                            case 2000:
                                return 15;
                            case 2001:
                                return 16;
                            case 2002:
                                return 14;
                            default:
                                switch (i) {
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
                                    default:
                                        switch (i) {
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
                                            default:
                                                return -1;
                                        }
                                }
                        }
                }
        }
    }

    public static String typeToString(int i) {
        if (i == -1) {
            return "CUSTOM";
        }
        if (i == 0) {
            return "NULL";
        }
        if (i == 1) {
            return "NOT_SPECIFIED";
        }
        switch (i) {
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
            default:
                switch (i) {
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
                    default:
                        switch (i) {
                            case 2000:
                                return "SPOT_HOVER";
                            case 2001:
                                return "SPOT_TOUCH";
                            case 2002:
                                return "SPOT_ANCHOR";
                            default:
                                return Integer.toString(i);
                        }
                }
        }
    }

    public void setDrawNativeDropShadow(boolean z) {
        this.mDrawNativeDropShadow = z;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public static void setCustomIcons(int i, float f) {
        sPointerIconColor = i;
        sPointerIconSizeScale = f;
        Log.i(TAG, "Changes PoinerIcons color=0x" + Integer.toHexString(sPointerIconColor) + " size=" + sPointerIconSizeScale);
    }

    private static Bitmap resizeBitmap(Bitmap bitmap, float f) {
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * f), (int) (bitmap.getHeight() * f), true);
        bitmap.recycle();
        return bitmapCreateScaledBitmap;
    }

    public static void clearSystemIcons() {
        Log.d(TAG, "clearSystemIcons");
        InputManager.getInstance().setPointerIconType(10119);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PointerIcon createSpenIcon(Bitmap bitmap, float f, float f2) {
        return createIcon(bitmap, f, f2, 20000);
    }

    private static PointerIcon createDefaultIcon(Bitmap bitmap, float f, float f2, int i) {
        return createIcon(bitmap, f, f2, i);
    }

    private Bitmap getBitmapFromVectorDrawable(VectorDrawable vectorDrawable) {
        float intrinsicWidth = vectorDrawable.getIntrinsicWidth();
        float intrinsicHeight = vectorDrawable.getIntrinsicHeight();
        float f = this.mPointerIconSizeScale;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (intrinsicWidth * f), (int) (intrinsicHeight * f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static void semSetDefaultPointerIcon(int i, PointerIcon pointerIcon) {
        setDefaultPointerIconInternal(i, pointerIcon, false);
    }

    public static void setDefaultPointerIconInternal(int i, PointerIcon pointerIcon, boolean z) {
        try {
            if (getInputManagerService() == null) {
                Log.d(TAG, "setDefaultPointerIconInternal failed to get IMS");
                return;
            }
            int i2 = pointerIcon != null ? pointerIcon.mType : 0;
            Log.d(TAG, "setDefaultPointerIconInternal toolType : " + i + ", icon : " + pointerIcon + ", forced : " + z + ", calling pid = " + Binder.getCallingPid());
            boolean z2 = i == 2;
            if (i2 == -1 && z2) {
                pointerIcon.mType = 20000;
            }
            sInputManagerService.setDefaultPointerIcon(i, pointerIcon, z);
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

    void setType(int i) {
        this.mType = i;
    }

    public float getHotSpotX() {
        return this.mHotSpotX;
    }

    public float getHotSpotY() {
        return this.mHotSpotY;
    }

    public static void setDexMode(boolean z) {
        sDexMode = z;
    }
}
