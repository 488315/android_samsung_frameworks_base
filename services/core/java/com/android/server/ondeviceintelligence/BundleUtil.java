package com.android.server.ondeviceintelligence;

import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.SharedMemory;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class BundleUtil {
    public static boolean canMarshall(Object obj) {
        return (obj instanceof byte[])
                || (obj instanceof PersistableBundle)
                || PersistableBundle.isValidType(obj);
    }

    public static void ensureValidBundle(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Request passed is expected to be non-null");
        }
        if (bundle.hasBinders() != 0) {
            throw new BadParcelableException("Bundle should not contain IBinder objects.");
        }
    }

    public static void sanitizeInferenceParams(Bundle bundle) {
        ensureValidBundle(bundle);
        if (bundle.hasFileDescriptors()) {
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj == null) {
                    bundle.putObject(str, null);
                } else if (!canMarshall(obj) && !(obj instanceof CursorWindow)) {
                    if (obj instanceof Bundle) {
                        sanitizeInferenceParams((Bundle) obj);
                    } else if (obj instanceof ParcelFileDescriptor) {
                        validatePfdReadOnly((ParcelFileDescriptor) obj);
                    } else if (obj instanceof SharedMemory) {
                        ((SharedMemory) obj).setProtect(OsConstants.PROT_READ);
                    } else if (obj instanceof Bitmap) {
                        validateBitmap((Bitmap) obj);
                    } else {
                        if (!(obj instanceof Parcelable[])) {
                            throw new BadParcelableException(
                                    "Unsupported Parcelable type encountered in the Bundle: "
                                            .concat(obj.getClass().getSimpleName()));
                        }
                        validateParcelableArray((Parcelable[]) obj);
                    }
                }
            }
        }
    }

    public static void sanitizeResponseParams(Bundle bundle) {
        ensureValidBundle(bundle);
        if (bundle.hasFileDescriptors()) {
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj == null) {
                    bundle.putObject(str, null);
                } else if (canMarshall(obj)) {
                    continue;
                } else if (obj instanceof Bundle) {
                    sanitizeResponseParams((Bundle) obj);
                } else if (obj instanceof ParcelFileDescriptor) {
                    validatePfdReadOnly((ParcelFileDescriptor) obj);
                } else if (obj instanceof Bitmap) {
                    validateBitmap((Bitmap) obj);
                } else {
                    if (!(obj instanceof Parcelable[])) {
                        throw new BadParcelableException(
                                "Unsupported Parcelable type encountered in the Bundle: "
                                        .concat(obj.getClass().getSimpleName()));
                    }
                    validateParcelableArray((Parcelable[]) obj);
                }
            }
        }
    }

    public static void sanitizeStateParams(Bundle bundle) {
        ensureValidBundle(bundle);
        if (bundle.hasFileDescriptors()) {
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj == null) {
                    bundle.putObject(str, null);
                } else if (canMarshall(obj)) {
                    continue;
                } else {
                    if (!(obj instanceof ParcelFileDescriptor)) {
                        throw new BadParcelableException(
                                "Unsupported Parcelable type encountered in the Bundle: "
                                        .concat(obj.getClass().getSimpleName()));
                    }
                    validatePfdReadOnly((ParcelFileDescriptor) obj);
                }
            }
        }
    }

    public static void tryCloseResource(Bundle bundle) {
        if (bundle == null || bundle.isEmpty() || !bundle.hasFileDescriptors()) {
            return;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            try {
                if (obj instanceof ParcelFileDescriptor) {
                    ((ParcelFileDescriptor) obj).close();
                } else if (obj instanceof CursorWindow) {
                    ((CursorWindow) obj).close();
                } else if (obj instanceof SharedMemory) {
                    ((SharedMemory) obj).close();
                }
            } catch (Exception e) {
                Log.e("BundleUtil", "Error closing resource with key: " + str, e);
            }
        }
    }

    public static void validateBitmap(Bitmap bitmap) {
        if (bitmap.isMutable()) {
            throw new BadParcelableException(
                    "Encountered a mutable Bitmap in the Bundle at key : " + bitmap);
        }
    }

    public static void validateParcelableArray(Parcelable[] parcelableArr) {
        int i = 0;
        if (parcelableArr.length > 0 && (parcelableArr[0] instanceof ParcelFileDescriptor)) {
            int length = parcelableArr.length;
            while (i < length) {
                validatePfdReadOnly((ParcelFileDescriptor) parcelableArr[i]);
                i++;
            }
            return;
        }
        if (parcelableArr.length <= 0 || !(parcelableArr[0] instanceof Bitmap)) {
            throw new BadParcelableException("Could not cast to any known parcelable array");
        }
        int length2 = parcelableArr.length;
        while (i < length2) {
            validateBitmap((Bitmap) parcelableArr[i]);
            i++;
        }
    }

    public static void validatePfdReadOnly(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) {
            return;
        }
        try {
            if ((Os.fcntlInt(parcelFileDescriptor.getFileDescriptor(), OsConstants.F_GETFL, 0)
                            & OsConstants.O_ACCMODE)
                    == OsConstants.O_RDONLY) {
            } else {
                throw new BadParcelableException(
                        "Bundle contains a parcel file descriptor which is not read-only.");
            }
        } catch (ErrnoException e) {
            throw new BadParcelableException("Invalid File descriptor passed in the Bundle.", e);
        }
    }
}
