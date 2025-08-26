package android.graphics.drawable;

import android.app.IUriGrantsManager;
import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.graphics.RecordingCanvas;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.NtpTrustedTime;
import com.android.graphics.flags.Flags;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public final class Icon implements Parcelable {
    private static final boolean DEBUG = false;
    public static final int MIN_ASHMEM_ICON_SIZE = 131072;
    private static final String TAG = "Icon";
    public static final int TYPE_ADAPTIVE_BITMAP = 5;
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_RESOURCE = 2;
    public static final int TYPE_URI = 4;
    public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;
    private static final int VERSION_STREAM_SERIALIZER = 1;
    private BlendMode mBlendMode;
    private boolean mCachedAshmem;
    private float mInsetScale;
    private int mInt1;
    private int mInt2;
    private Object mObj1;
    private String mString1;
    private ColorStateList mTintList;
    private final int mType;
    private boolean mUseMonochrome;
    static final BlendMode DEFAULT_BLEND_MODE = Drawable.DEFAULT_BLEND_MODE;
    public static final Parcelable.Creator<Icon> CREATOR = new Parcelable.Creator<Icon>() { // from class: android.graphics.drawable.Icon.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Icon createFromParcel(Parcel parcel) {
            return new Icon(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Icon[] newArray(int i) {
            return new Icon[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface IconType {
    }

    public interface OnDrawableLoadedListener {
        void onDrawableLoaded(Drawable drawable);
    }

    public int getType() {
        return this.mType;
    }

    public Bitmap getBitmap() {
        int i = this.mType;
        if (i != 1 && i != 5) {
            throw new IllegalStateException("called getBitmap() on " + this);
        }
        return (Bitmap) this.mObj1;
    }

    private void setBitmap(Bitmap bitmap) {
        if (bitmap.isMutable()) {
            this.mObj1 = bitmap.copy(bitmap.getConfig(), false);
        } else {
            this.mObj1 = bitmap;
        }
        this.mCachedAshmem = false;
    }

    public int getDataLength() {
        int i;
        if (this.mType != 3) {
            throw new IllegalStateException("called getDataLength() on " + this);
        }
        synchronized (this) {
            i = this.mInt1;
        }
        return i;
    }

    public int getDataOffset() {
        int i;
        if (this.mType != 3) {
            throw new IllegalStateException("called getDataOffset() on " + this);
        }
        synchronized (this) {
            i = this.mInt2;
        }
        return i;
    }

    public byte[] getDataBytes() {
        byte[] bArr;
        if (this.mType != 3) {
            throw new IllegalStateException("called getDataBytes() on " + this);
        }
        synchronized (this) {
            bArr = (byte[]) this.mObj1;
        }
        return bArr;
    }

    public Resources getResources() {
        if (this.mType != 2) {
            throw new IllegalStateException("called getResources() on " + this);
        }
        return (Resources) this.mObj1;
    }

    public String getResPackage() {
        if (this.mType != 2) {
            throw new IllegalStateException("called getResPackage() on " + this);
        }
        return this.mString1;
    }

    public int getResId() {
        if (this.mType != 2) {
            throw new IllegalStateException("called getResId() on " + this);
        }
        return this.mInt1;
    }

    public String getUriString() {
        int i = this.mType;
        if (i != 4 && i != 6) {
            throw new IllegalStateException("called getUriString() on " + this);
        }
        return this.mString1;
    }

    public Uri getUri() {
        return Uri.parse(getUriString());
    }

    private static final String typeToString(int i) {
        switch (i) {
            case 1:
                return "BITMAP";
            case 2:
                return "RESOURCE";
            case 3:
                return "DATA";
            case 4:
                return "URI";
            case 5:
                return "BITMAP_MASKABLE";
            case 6:
                return "URI_MASKABLE";
            default:
                return "UNKNOWN";
        }
    }

    public void loadDrawableAsync(Context context, Message message) {
        if (message.getTarget() == null) {
            throw new IllegalArgumentException("callback message must have a target handler");
        }
        new LoadDrawableTask(context, message).runAsync();
    }

    public void loadDrawableAsync(Context context, OnDrawableLoadedListener onDrawableLoadedListener, Handler handler) {
        new LoadDrawableTask(context, handler, onDrawableLoadedListener).runAsync();
    }

    public Drawable loadDrawable(Context context) throws IOException {
        Drawable drawableLoadDrawableInner = loadDrawableInner(context);
        if (drawableLoadDrawableInner != null && hasTint()) {
            drawableLoadDrawableInner.mutate();
            drawableLoadDrawableInner.setTintList(this.mTintList);
            drawableLoadDrawableInner.setTintBlendMode(this.mBlendMode);
        }
        return this.mUseMonochrome ? crateMonochromeDrawable(drawableLoadDrawableInner, this.mInsetScale) : drawableLoadDrawableInner;
    }

    private static Drawable crateMonochromeDrawable(Drawable drawable, float f) {
        Drawable monochrome;
        return (!(drawable instanceof AdaptiveIconDrawable) || (monochrome = ((AdaptiveIconDrawable) drawable).getMonochrome()) == null) ? drawable : new InsetDrawable(monochrome, f);
    }

    private Bitmap fixMaxBitmapSize(Bitmap bitmap) {
        if (bitmap == null || bitmap.getByteCount() <= RecordingCanvas.MAX_BITMAP_SIZE) {
            return bitmap;
        }
        int rowBytes = RecordingCanvas.MAX_BITMAP_SIZE / (bitmap.getRowBytes() / bitmap.getWidth());
        float width = bitmap.getWidth() / bitmap.getHeight();
        int iSqrt = (int) Math.sqrt(rowBytes / width);
        return scaleDownIfNecessary(bitmap, (int) (iSqrt * width), iSqrt);
    }

    private Drawable fixMaxBitmapSize(Resources resources, Drawable drawable) {
        return drawable instanceof BitmapDrawable ? new BitmapDrawable(resources, fixMaxBitmapSize(((BitmapDrawable) drawable).getBitmap())) : drawable;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Drawable loadDrawableInner(Context context) throws IOException {
        InputStream uriInputStream;
        switch (this.mType) {
            case 1:
                return new BitmapDrawable(context.getResources(), fixMaxBitmapSize(getBitmap()));
            case 2:
                if (getResources() == null) {
                    String resPackage = getResPackage();
                    if (TextUtils.isEmpty(resPackage)) {
                        resPackage = context.getPackageName();
                    }
                    if ("android".equals(resPackage)) {
                        this.mObj1 = Resources.getSystem();
                    } else {
                        PackageManager packageManager = context.getPackageManager();
                        try {
                            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(resPackage, 9216);
                            if (applicationInfo != null) {
                                this.mObj1 = packageManager.getResourcesForApplication(applicationInfo);
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.e(TAG, String.format("Unable to find pkg=%s for icon %s", resPackage, this), e);
                        }
                    }
                    try {
                        return fixMaxBitmapSize(getResources(), getResources().getDrawable(getResId(), context.getTheme()));
                    } catch (RuntimeException e2) {
                        Log.e(TAG, String.format("Unable to load resource 0x%08x from pkg=%s", Integer.valueOf(this.getResId()), this.getResPackage()), e2);
                    }
                } else {
                    return fixMaxBitmapSize(getResources(), getResources().getDrawable(getResId(), context.getTheme()));
                }
                return null;
            case 3:
                return new BitmapDrawable(context.getResources(), fixMaxBitmapSize(BitmapFactory.decodeByteArray(getDataBytes(), getDataOffset(), getDataLength())));
            case 4:
                try {
                    uriInputStream = getUriInputStream(context);
                    if (uriInputStream == null) {
                        if (uriInputStream != null) {
                            uriInputStream.close();
                        }
                        return null;
                    }
                    try {
                        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(uriInputStream);
                        if (bitmapDecodeStream == null) {
                            Log.w(TAG, "Unable to decode image from URI: " + getUriString());
                            if (Flags.iconLoadDrawableReturnNullWhenUriDecodeFails()) {
                                if (uriInputStream != null) {
                                    uriInputStream.close();
                                }
                                return null;
                            }
                        }
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), fixMaxBitmapSize(bitmapDecodeStream));
                        if (uriInputStream != null) {
                            uriInputStream.close();
                        }
                        return bitmapDrawable;
                    } finally {
                        if (uriInputStream != null) {
                            try {
                                uriInputStream.close();
                            } catch (Throwable th) {
                                th.addSuppressed(th);
                            }
                        }
                    }
                } catch (IOException e3) {
                    throw new IllegalStateException(e3);
                }
            case 5:
                return new AdaptiveIconDrawable((Drawable) null, new BitmapDrawable(context.getResources(), fixMaxBitmapSize(getBitmap())));
            case 6:
                try {
                    uriInputStream = getUriInputStream(context);
                    if (uriInputStream == null) {
                        if (uriInputStream != null) {
                            uriInputStream.close();
                        }
                        return null;
                    }
                    try {
                        Bitmap bitmapDecodeStream2 = BitmapFactory.decodeStream(uriInputStream);
                        if (bitmapDecodeStream2 == null) {
                            Log.w(TAG, "Unable to decode image from URI: " + getUriString());
                            if (Flags.iconLoadDrawableReturnNullWhenUriDecodeFails()) {
                                if (uriInputStream != null) {
                                    uriInputStream.close();
                                }
                                return null;
                            }
                        }
                        AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable((Drawable) null, new BitmapDrawable(context.getResources(), fixMaxBitmapSize(bitmapDecodeStream2)));
                        if (uriInputStream != null) {
                            uriInputStream.close();
                        }
                        return adaptiveIconDrawable;
                    } finally {
                    }
                } catch (IOException e4) {
                    throw new IllegalStateException(e4);
                }
            default:
                return null;
        }
    }

    private InputStream getUriInputStream(Context context) {
        Uri uri = getUri();
        String scheme = uri.getScheme();
        if ("content".equals(scheme) || "file".equals(scheme)) {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (Exception e) {
                Log.w(TAG, "Unable to load image from URI: " + uri, e);
                return null;
            }
        }
        try {
            return new FileInputStream(new File(this.mString1));
        } catch (FileNotFoundException e2) {
            Log.w(TAG, "Unable to load image from path: " + uri, e2);
            return null;
        }
    }

    public Drawable loadDrawableAsUser(Context context, int i) {
        Context contextCreateContextAsUser;
        if (this.mType == 2) {
            String resPackage = getResPackage();
            if (TextUtils.isEmpty(resPackage)) {
                resPackage = context.getPackageName();
            }
            if (getResources() == null && !getResPackage().equals("android")) {
                if (context.getUserId() == i) {
                    contextCreateContextAsUser = context;
                } else {
                    contextCreateContextAsUser = context.createContextAsUser(UserHandle.of(i), (UserHandle.isSameApp(context.getApplicationInfo().uid, Process.myUid()) ? 1 : 0) | 4);
                }
                try {
                    this.mObj1 = contextCreateContextAsUser.getPackageManager().getResourcesForApplication(resPackage);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(TAG, String.format("Unable to find pkg=%s user=%d", getResPackage(), Integer.valueOf(i)), e);
                }
            }
        }
        return loadDrawable(context);
    }

    public Drawable loadDrawableCheckingUriGrant(Context context, IUriGrantsManager iUriGrantsManager, int i, String str) {
        if (getType() == 4 || getType() == 6) {
            try {
                iUriGrantsManager.checkGrantUriPermission_ignoreNonSystem(i, str, ContentProvider.getUriWithoutUserId(getUri()), 1, ContentProvider.getUserIdFromUri(getUri()));
            } catch (RemoteException | SecurityException e) {
                Log.e(TAG, "Failed to get URI permission for: " + getUri(), e);
                return null;
            }
        }
        return loadDrawable(context);
    }

    public void convertToAshmem() {
        int i = this.mType;
        if ((i == 1 || i == 5) && getBitmap().isMutable() && getBitmap().getAllocationByteCount() >= 131072) {
            setBitmap(getBitmap().asShared());
        }
        this.mCachedAshmem = true;
    }

    public void writeToStream(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeByte(this.mType);
        switch (this.mType) {
            case 1:
            case 5:
                getBitmap().compress(Bitmap.CompressFormat.PNG, 100, dataOutputStream);
                break;
            case 2:
                dataOutputStream.writeUTF(getResPackage());
                dataOutputStream.writeInt(getResId());
                break;
            case 3:
                dataOutputStream.writeInt(getDataLength());
                dataOutputStream.write(getDataBytes(), getDataOffset(), getDataLength());
                break;
            case 4:
            case 6:
                dataOutputStream.writeUTF(getUriString());
                break;
        }
    }

    private Icon(int i) {
        this.mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        this.mCachedAshmem = false;
        this.mUseMonochrome = false;
        this.mInsetScale = 0.0f;
        this.mType = i;
    }

    public static Icon createFromStream(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        if (dataInputStream.readInt() < 1) {
            return null;
        }
        switch (dataInputStream.readByte()) {
            case 1:
                return createWithBitmap(BitmapFactory.decodeStream(dataInputStream));
            case 2:
                return createWithResource(dataInputStream.readUTF(), dataInputStream.readInt());
            case 3:
                int i = dataInputStream.readInt();
                byte[] bArr = new byte[i];
                dataInputStream.read(bArr, 0, i);
                return createWithData(bArr, 0, i);
            case 4:
                return createWithContentUri(dataInputStream.readUTF());
            case 5:
                return createWithAdaptiveBitmap(BitmapFactory.decodeStream(dataInputStream));
            case 6:
                return createWithAdaptiveBitmapContentUri(dataInputStream.readUTF());
            default:
                return null;
        }
    }

    public boolean sameAs(Icon icon) {
        if (icon == this) {
            return true;
        }
        if (this.mType != icon.getType()) {
            return false;
        }
        switch (this.mType) {
            case 1:
            case 5:
                return getBitmap() == icon.getBitmap();
            case 2:
                return getResId() == icon.getResId() && Objects.equals(getResPackage(), icon.getResPackage()) && this.mUseMonochrome == icon.mUseMonochrome && this.mInsetScale == icon.mInsetScale;
            case 3:
                return getDataLength() == icon.getDataLength() && getDataOffset() == icon.getDataOffset() && Arrays.equals(getDataBytes(), icon.getDataBytes());
            case 4:
            case 6:
                return Objects.equals(getUriString(), icon.getUriString());
            default:
                return false;
        }
    }

    public static Icon createWithResource(Context context, int i) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        Icon icon = new Icon(2);
        icon.mInt1 = i;
        icon.mString1 = context.getPackageName();
        return icon;
    }

    public static Icon createWithResource(Resources resources, int i) {
        if (resources == null) {
            throw new IllegalArgumentException("Resource must not be null.");
        }
        Icon icon = new Icon(2);
        icon.mInt1 = i;
        icon.mString1 = resources.getResourcePackageName(i);
        return icon;
    }

    public static Icon createWithResource(String str, int i) {
        if (str == null) {
            throw new IllegalArgumentException("Resource package name must not be null.");
        }
        Icon icon = new Icon(2);
        icon.mInt1 = i;
        icon.mString1 = str;
        return icon;
    }

    public static Icon createWithResourceAdaptiveDrawable(String str, int i, boolean z, float f) {
        if (str == null) {
            throw new IllegalArgumentException("Resource package name must not be null.");
        }
        Icon icon = new Icon(2);
        icon.mInt1 = i;
        icon.mUseMonochrome = z;
        icon.mInsetScale = f;
        icon.mString1 = str;
        return icon;
    }

    public static Icon createWithBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        Icon icon = new Icon(1);
        icon.setBitmap(bitmap);
        return icon;
    }

    public static Icon createWithAdaptiveBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap must not be null.");
        }
        Icon icon = new Icon(5);
        icon.setBitmap(bitmap);
        return icon;
    }

    public static Icon createWithData(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
        Icon icon = new Icon(3);
        icon.mObj1 = bArr;
        icon.mInt1 = i2;
        icon.mInt2 = i;
        return icon;
    }

    public static Icon createWithContentUri(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        Icon icon = new Icon(4);
        icon.mString1 = str;
        return icon;
    }

    public static Icon createWithContentUri(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        return createWithContentUri(uri.toString());
    }

    public static Icon createWithAdaptiveBitmapContentUri(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        Icon icon = new Icon(6);
        icon.mString1 = str;
        return icon;
    }

    public static Icon createWithAdaptiveBitmapContentUri(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri must not be null.");
        }
        return createWithAdaptiveBitmapContentUri(uri.toString());
    }

    public Icon setTint(int i) {
        return setTintList(ColorStateList.valueOf(i));
    }

    public Icon setTintList(ColorStateList colorStateList) {
        this.mTintList = colorStateList;
        return this;
    }

    public ColorStateList getTintList() {
        return this.mTintList;
    }

    public Icon setTintMode(PorterDuff.Mode mode) {
        this.mBlendMode = BlendMode.fromValue(mode.nativeInt);
        return this;
    }

    public Icon setTintBlendMode(BlendMode blendMode) {
        this.mBlendMode = blendMode;
        return this;
    }

    public BlendMode getTintBlendMode() {
        return this.mBlendMode;
    }

    public boolean hasTint() {
        return (this.mTintList == null && this.mBlendMode == DEFAULT_BLEND_MODE) ? false : true;
    }

    public static Icon createWithFilePath(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Path must not be null.");
        }
        Icon icon = new Icon(4);
        icon.mString1 = str;
        return icon;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Icon(typ=");
        sb.append(typeToString(this.mType));
        switch (this.mType) {
            case 1:
            case 5:
                sb.append(" size=");
                sb.append(getBitmap().getWidth());
                sb.append("x");
                sb.append(getBitmap().getHeight());
                break;
            case 2:
                sb.append(" pkg=");
                sb.append(getResPackage());
                sb.append(" id=");
                sb.append(String.format("0x%08x", Integer.valueOf(getResId())));
                break;
            case 3:
                sb.append(" len=");
                sb.append(getDataLength());
                if (getDataOffset() != 0) {
                    sb.append(" off=");
                    sb.append(getDataOffset());
                    break;
                }
                break;
            case 4:
            case 6:
                sb.append(" uri=");
                sb.append(getUriString());
                break;
        }
        if (this.mTintList != null) {
            sb.append(" tint=");
            int[] colors = this.mTintList.getColors();
            int length = colors.length;
            String str = "";
            int i = 0;
            while (i < length) {
                sb.append(String.format("%s0x%08x", str, Integer.valueOf(colors[i])));
                i++;
                str = NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER;
            }
        }
        if (this.mBlendMode != DEFAULT_BLEND_MODE) {
            sb.append(" mode=");
            sb.append(this.mBlendMode);
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int i = this.mType;
        return (i == 1 || i == 5 || i == 3) ? 1 : 0;
    }

    private Icon(Parcel parcel) {
        this(parcel.readInt());
        switch (this.mType) {
            case 1:
            case 5:
                this.mObj1 = Bitmap.CREATOR.createFromParcel(parcel);
                break;
            case 2:
                String string = parcel.readString();
                int i = parcel.readInt();
                this.mString1 = string;
                this.mInt1 = i;
                this.mUseMonochrome = parcel.readBoolean();
                this.mInsetScale = parcel.readFloat();
                break;
            case 3:
                int i2 = parcel.readInt();
                byte[] blob = parcel.readBlob();
                if (i2 != blob.length) {
                    throw new RuntimeException("internal unparceling error: blob length (" + blob.length + ") != expected length (" + i2 + NavigationBarInflaterView.KEY_CODE_END);
                }
                this.mInt1 = i2;
                this.mObj1 = blob;
                break;
            case 4:
            case 6:
                this.mString1 = parcel.readString();
                break;
            default:
                throw new RuntimeException("invalid " + getClass().getSimpleName() + " type in parcel: " + this.mType);
        }
        if (parcel.readInt() == 1) {
            this.mTintList = ColorStateList.CREATOR.createFromParcel(parcel);
        }
        this.mBlendMode = BlendMode.fromValue(parcel.readInt());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        switch (this.mType) {
            case 1:
            case 5:
                if (!this.mCachedAshmem) {
                    this.mObj1 = ((Bitmap) this.mObj1).asShared();
                    this.mCachedAshmem = true;
                }
                getBitmap().writeToParcel(parcel, i);
                break;
            case 2:
                parcel.writeString(getResPackage());
                parcel.writeInt(getResId());
                parcel.writeBoolean(this.mUseMonochrome);
                parcel.writeFloat(this.mInsetScale);
                break;
            case 3:
                parcel.writeInt(getDataLength());
                parcel.writeBlob(getDataBytes(), getDataOffset(), getDataLength());
                break;
            case 4:
            case 6:
                parcel.writeString(getUriString());
                break;
        }
        if (this.mTintList == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mTintList.writeToParcel(parcel, i);
        }
        parcel.writeInt(BlendMode.toValue(this.mBlendMode));
    }

    public static Bitmap scaleDownIfNecessary(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= i && height <= i2) {
            return bitmap;
        }
        float f = width;
        float f2 = height;
        float fMin = Math.min(i / f, i2 / f2);
        return Bitmap.createScaledBitmap(bitmap, Math.max(1, (int) (f * fMin)), Math.max(1, (int) (fMin * f2)), true);
    }

    public void scaleDownIfNecessary(int i, int i2) {
        int i3 = this.mType;
        if (i3 == 1 || i3 == 5) {
            setBitmap(scaleDownIfNecessary(getBitmap(), i, i2));
        }
    }

    private class LoadDrawableTask implements Runnable {
        final Context mContext;
        final Message mMessage;

        public LoadDrawableTask(Context context, Handler handler, final OnDrawableLoadedListener onDrawableLoadedListener) {
            this.mContext = context;
            this.mMessage = Message.obtain(handler, new Runnable() { // from class: android.graphics.drawable.Icon.LoadDrawableTask.1
                @Override // java.lang.Runnable
                public void run() {
                    onDrawableLoadedListener.onDrawableLoaded((Drawable) LoadDrawableTask.this.mMessage.obj);
                }
            });
        }

        public LoadDrawableTask(Context context, Message message) {
            this.mContext = context;
            this.mMessage = message;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mMessage.obj = Icon.this.loadDrawable(this.mContext);
            this.mMessage.sendToTarget();
        }

        public void runAsync() {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(this);
        }
    }
}
