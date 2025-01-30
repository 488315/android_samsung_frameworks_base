package androidx.core.graphics.drawable;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.versionedparcelable.CustomVersionedParcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class IconCompat extends CustomVersionedParcelable {
    public static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    public byte[] mData;
    public int mInt1;
    public int mInt2;
    public Object mObj1;
    public Parcelable mParcelable;
    public String mString1;
    public ColorStateList mTintList;
    public PorterDuff.Mode mTintMode;
    public String mTintModeStr;
    public int mType;

    public IconCompat() {
        this.mType = -1;
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.mTintMode = DEFAULT_TINT_MODE;
        this.mTintModeStr = null;
    }

    public static IconCompat createFromBundle(Bundle bundle) {
        int i = bundle.getInt("type");
        IconCompat iconCompat = new IconCompat(i);
        iconCompat.mInt1 = bundle.getInt("int1");
        iconCompat.mInt2 = bundle.getInt("int2");
        iconCompat.mString1 = bundle.getString("string1");
        if (bundle.containsKey("tint_list")) {
            iconCompat.mTintList = (ColorStateList) bundle.getParcelable("tint_list");
        }
        if (bundle.containsKey("tint_mode")) {
            iconCompat.mTintMode = PorterDuff.Mode.valueOf(bundle.getString("tint_mode"));
        }
        switch (i) {
            case -1:
            case 1:
            case 5:
                iconCompat.mObj1 = bundle.getParcelable("obj");
                return iconCompat;
            case 0:
            default:
                IconCompat$$ExternalSyntheticOutline0.m30m("Unknown type ", i, "IconCompat");
                return null;
            case 2:
            case 4:
            case 6:
                iconCompat.mObj1 = bundle.getString("obj");
                return iconCompat;
            case 3:
                iconCompat.mObj1 = bundle.getByteArray("obj");
                return iconCompat;
        }
    }

    public static IconCompat createFromIcon(Context context, Icon icon) {
        IconCompat iconCompat;
        icon.getClass();
        int type = icon.getType();
        if (type == 2) {
            String resPackage = icon.getResPackage();
            try {
                return createWithResource(icon.getResId(), getResources(context, resPackage), resPackage);
            } catch (Resources.NotFoundException unused) {
                throw new IllegalArgumentException("Icon resource cannot be found");
            }
        }
        if (type == 4) {
            Uri uri = icon.getUri();
            uri.getClass();
            String uri2 = uri.toString();
            uri2.getClass();
            iconCompat = new IconCompat(4);
            iconCompat.mObj1 = uri2;
        } else {
            if (type != 6) {
                IconCompat iconCompat2 = new IconCompat(-1);
                iconCompat2.mObj1 = icon;
                return iconCompat2;
            }
            Uri uri3 = icon.getUri();
            uri3.getClass();
            String uri4 = uri3.toString();
            uri4.getClass();
            iconCompat = new IconCompat(6);
            iconCompat.mObj1 = uri4;
        }
        return iconCompat;
    }

    public static IconCompat createWithBitmap(Bitmap bitmap) {
        bitmap.getClass();
        IconCompat iconCompat = new IconCompat(1);
        iconCompat.mObj1 = bitmap;
        return iconCompat;
    }

    public static IconCompat createWithResource(int i, Context context) {
        context.getClass();
        return createWithResource(i, context.getResources(), context.getPackageName());
    }

    public static Resources getResources(Context context, String str) {
        if ("android".equals(str)) {
            return Resources.getSystem();
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 8192);
            if (applicationInfo != null) {
                return packageManager.getResourcesForApplication(applicationInfo);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("IconCompat", String.format("Unable to find pkg=%s for icon", str), e);
            return null;
        }
    }

    public final void checkResource(Context context) {
        Object obj;
        if (this.mType != 2 || (obj = this.mObj1) == null) {
            return;
        }
        String str = (String) obj;
        if (str.contains(":")) {
            String str2 = str.split(":", -1)[1];
            String str3 = str2.split("/", -1)[0];
            String str4 = str2.split("/", -1)[1];
            String str5 = str.split(":", -1)[0];
            if ("0_resource_name_obfuscated".equals(str4)) {
                Log.i("IconCompat", "Found obfuscated resource, not trying to update resource id for it");
                return;
            }
            String resPackage = getResPackage();
            int identifier = getResources(context, resPackage).getIdentifier(str4, str3, str5);
            if (this.mInt1 != identifier) {
                Log.i("IconCompat", "Id has changed for " + resPackage + " " + str);
                this.mInt1 = identifier;
            }
        }
    }

    public final int getResId() {
        int i = this.mType;
        if (i == -1) {
            return ((Icon) this.mObj1).getResId();
        }
        if (i == 2) {
            return this.mInt1;
        }
        throw new IllegalStateException("called getResId() on " + this);
    }

    public final String getResPackage() {
        int i = this.mType;
        if (i == -1) {
            return ((Icon) this.mObj1).getResPackage();
        }
        if (i == 2) {
            String str = this.mString1;
            return (str == null || TextUtils.isEmpty(str)) ? ((String) this.mObj1).split(":", -1)[0] : this.mString1;
        }
        throw new IllegalStateException("called getResPackage() on " + this);
    }

    public final Uri getUri() {
        int i = this.mType;
        if (i == -1) {
            return ((Icon) this.mObj1).getUri();
        }
        if (i == 4 || i == 6) {
            return Uri.parse((String) this.mObj1);
        }
        throw new IllegalStateException("called getUri() on " + this);
    }

    public final Drawable loadDrawable(Context context) {
        checkResource(context);
        return toIcon$1().loadDrawable(context);
    }

    public final Bundle toBundle() {
        Bundle bundle = new Bundle();
        switch (this.mType) {
            case -1:
                bundle.putParcelable("obj", (Parcelable) this.mObj1);
                break;
            case 0:
            default:
                throw new IllegalArgumentException("Invalid icon");
            case 1:
            case 5:
                bundle.putParcelable("obj", (Bitmap) this.mObj1);
                break;
            case 2:
            case 4:
            case 6:
                bundle.putString("obj", (String) this.mObj1);
                break;
            case 3:
                bundle.putByteArray("obj", (byte[]) this.mObj1);
                break;
        }
        bundle.putInt("type", this.mType);
        bundle.putInt("int1", this.mInt1);
        bundle.putInt("int2", this.mInt2);
        bundle.putString("string1", this.mString1);
        ColorStateList colorStateList = this.mTintList;
        if (colorStateList != null) {
            bundle.putParcelable("tint_list", colorStateList);
        }
        PorterDuff.Mode mode = this.mTintMode;
        if (mode != DEFAULT_TINT_MODE) {
            bundle.putString("tint_mode", mode.name());
        }
        return bundle;
    }

    public final Icon toIcon$1() {
        Icon createWithBitmap;
        switch (this.mType) {
            case -1:
                return (Icon) this.mObj1;
            case 0:
            default:
                throw new IllegalArgumentException("Unknown type");
            case 1:
                createWithBitmap = Icon.createWithBitmap((Bitmap) this.mObj1);
                break;
            case 2:
                createWithBitmap = Icon.createWithResource(getResPackage(), this.mInt1);
                break;
            case 3:
                createWithBitmap = Icon.createWithData((byte[]) this.mObj1, this.mInt1, this.mInt2);
                break;
            case 4:
                createWithBitmap = Icon.createWithContentUri((String) this.mObj1);
                break;
            case 5:
                createWithBitmap = Icon.createWithAdaptiveBitmap((Bitmap) this.mObj1);
                break;
            case 6:
                createWithBitmap = Icon.createWithAdaptiveBitmapContentUri(getUri());
                break;
        }
        ColorStateList colorStateList = this.mTintList;
        if (colorStateList != null) {
            createWithBitmap.setTintList(colorStateList);
        }
        PorterDuff.Mode mode = this.mTintMode;
        if (mode == DEFAULT_TINT_MODE) {
            return createWithBitmap;
        }
        createWithBitmap.setTintMode(mode);
        return createWithBitmap;
    }

    public final String toString() {
        String str;
        if (this.mType == -1) {
            return String.valueOf(this.mObj1);
        }
        StringBuilder sb = new StringBuilder("Icon(typ=");
        switch (this.mType) {
            case 1:
                str = "BITMAP";
                break;
            case 2:
                str = "RESOURCE";
                break;
            case 3:
                str = "DATA";
                break;
            case 4:
                str = "URI";
                break;
            case 5:
                str = "BITMAP_MASKABLE";
                break;
            case 6:
                str = "URI_MASKABLE";
                break;
            default:
                str = "UNKNOWN";
                break;
        }
        sb.append(str);
        switch (this.mType) {
            case 1:
            case 5:
                sb.append(" size=");
                sb.append(((Bitmap) this.mObj1).getWidth());
                sb.append("x");
                sb.append(((Bitmap) this.mObj1).getHeight());
                break;
            case 2:
                sb.append(" pkg=");
                sb.append(this.mString1);
                sb.append(" id=");
                sb.append(String.format("0x%08x", Integer.valueOf(getResId())));
                break;
            case 3:
                sb.append(" len=");
                sb.append(this.mInt1);
                if (this.mInt2 != 0) {
                    sb.append(" off=");
                    sb.append(this.mInt2);
                    break;
                }
                break;
            case 4:
            case 6:
                sb.append(" uri=");
                sb.append(this.mObj1);
                break;
        }
        if (this.mTintList != null) {
            sb.append(" tint=");
            sb.append(this.mTintList);
        }
        if (this.mTintMode != DEFAULT_TINT_MODE) {
            sb.append(" mode=");
            sb.append(this.mTintMode);
        }
        sb.append(")");
        return sb.toString();
    }

    public static IconCompat createWithResource(int i, Resources resources, String str) {
        str.getClass();
        if (i != 0) {
            IconCompat iconCompat = new IconCompat(2);
            iconCompat.mInt1 = i;
            if (resources != null) {
                try {
                    iconCompat.mObj1 = resources.getResourceName(i);
                } catch (Resources.NotFoundException unused) {
                    throw new IllegalArgumentException("Icon resource cannot be found");
                }
            } else {
                iconCompat.mObj1 = str;
            }
            iconCompat.mString1 = str;
            return iconCompat;
        }
        throw new IllegalArgumentException("Drawable resource ID must not be 0");
    }

    public IconCompat(int i) {
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.mTintMode = DEFAULT_TINT_MODE;
        this.mTintModeStr = null;
        this.mType = i;
    }

    public static IconCompat createFromIcon(Icon icon) {
        icon.getClass();
        int type = icon.getType();
        if (type == 2) {
            return createWithResource(icon.getResId(), null, icon.getResPackage());
        }
        if (type == 4) {
            Uri uri = icon.getUri();
            uri.getClass();
            String uri2 = uri.toString();
            uri2.getClass();
            IconCompat iconCompat = new IconCompat(4);
            iconCompat.mObj1 = uri2;
            return iconCompat;
        }
        if (type != 6) {
            IconCompat iconCompat2 = new IconCompat(-1);
            iconCompat2.mObj1 = icon;
            return iconCompat2;
        }
        Uri uri3 = icon.getUri();
        uri3.getClass();
        String uri4 = uri3.toString();
        uri4.getClass();
        IconCompat iconCompat3 = new IconCompat(6);
        iconCompat3.mObj1 = uri4;
        return iconCompat3;
    }
}
