package android.text.style;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class ImageSpan extends DynamicDrawableSpan {
    private Uri mContentUri;
    private Context mContext;
    private Drawable mDrawable;
    private int mResourceId;
    private String mSource;

    @Deprecated
    public ImageSpan(Bitmap bitmap) {
        this((Context) null, bitmap, 0);
    }

    @Deprecated
    public ImageSpan(Bitmap bitmap, int i) {
        this((Context) null, bitmap, i);
    }

    public ImageSpan(Context context, Bitmap bitmap) {
        this(context, bitmap, 0);
    }

    public ImageSpan(Context context, Bitmap bitmap, int i) {
        super(i);
        BitmapDrawable bitmapDrawable;
        this.mContext = context;
        if (context != null) {
            bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        } else {
            bitmapDrawable = new BitmapDrawable(bitmap);
        }
        this.mDrawable = bitmapDrawable;
        int intrinsicWidth = bitmapDrawable.getIntrinsicWidth();
        int intrinsicHeight = this.mDrawable.getIntrinsicHeight();
        this.mDrawable.setBounds(0, 0, intrinsicWidth <= 0 ? 0 : intrinsicWidth, intrinsicHeight <= 0 ? 0 : intrinsicHeight);
    }

    public ImageSpan(Drawable drawable) {
        this(drawable, 0);
    }

    public ImageSpan(Drawable drawable, int i) {
        super(i);
        this.mDrawable = drawable;
    }

    public ImageSpan(Drawable drawable, String str) {
        this(drawable, str, 0);
    }

    public ImageSpan(Drawable drawable, String str, int i) {
        super(i);
        this.mDrawable = drawable;
        this.mSource = str;
    }

    public ImageSpan(Context context, Uri uri) {
        this(context, uri, 0);
    }

    public ImageSpan(Context context, Uri uri, int i) {
        super(i);
        this.mContext = context;
        this.mContentUri = uri;
        this.mSource = uri.toString();
    }

    public ImageSpan(Context context, int i) {
        this(context, i, 0);
    }

    public ImageSpan(Context context, int i, int i2) {
        super(i2);
        this.mContext = context;
        this.mResourceId = i;
    }

    @Override // android.text.style.DynamicDrawableSpan
    public Drawable getDrawable() {
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            return drawable;
        }
        Drawable drawable2 = null;
        if (this.mContentUri != null) {
            try {
                InputStream openInputStream = this.mContext.getContentResolver().openInputStream(this.mContentUri);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), BitmapFactory.decodeStream(openInputStream));
                try {
                    bitmapDrawable.setBounds(0, 0, bitmapDrawable.getIntrinsicWidth(), bitmapDrawable.getIntrinsicHeight());
                    openInputStream.close();
                    return bitmapDrawable;
                } catch (Exception e) {
                    e = e;
                    drawable2 = bitmapDrawable;
                    Log.e("ImageSpan", "Failed to loaded content " + this.mContentUri, e);
                    return drawable2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } else {
            try {
                drawable2 = this.mContext.getDrawable(this.mResourceId);
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                return drawable2;
            } catch (Exception unused) {
                Log.e("ImageSpan", "Unable to find resource: " + this.mResourceId);
            }
        }
        return drawable2;
    }

    public String getSource() {
        return this.mSource;
    }

    @Override // android.text.style.DynamicDrawableSpan
    public String toString() {
        return "ImageSpan{drawable=" + getDrawable() + ", source='" + getSource() + "', verticalAlignment=" + getVerticalAlignment() + '}';
    }
}
