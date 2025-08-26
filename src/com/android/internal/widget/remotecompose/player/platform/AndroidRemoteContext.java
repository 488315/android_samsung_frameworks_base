package com.android.internal.widget.remotecompose.player.platform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.TouchListener;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.operations.FloatExpression;
import com.android.internal.widget.remotecompose.core.operations.ShaderData;
import com.android.internal.widget.remotecompose.core.operations.utilities.ArrayAccess;
import com.android.internal.widget.remotecompose.core.operations.utilities.DataMap;
import com.android.internal.widget.remotecompose.core.types.LongConstant;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class AndroidRemoteContext extends RemoteContext {
    HashMap<String, VarName> mVarNameHashMap = new HashMap<>();

    public void useCanvas(Canvas canvas) {
        if (this.mPaintContext == null) {
            this.mPaintContext = new AndroidPaintContext(this, canvas);
        } else {
            this.mPaintContext.reset();
            ((AndroidPaintContext) this.mPaintContext).setCanvas(canvas);
        }
        this.mWidth = canvas.getWidth();
        this.mHeight = canvas.getHeight();
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadPathData(int i, float[] fArr) {
        this.mRemoteComposeState.putPathData(i, fArr);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public float[] getPathData(int i) {
        return this.mRemoteComposeState.getPathData(i);
    }

    static class VarName {
        int mId;
        String mName;
        int mType;

        VarName(String str, int i, int i2) {
            this.mName = str;
            this.mId = i;
            this.mType = i2;
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadVariableName(String str, int i, int i2) {
        this.mVarNameHashMap.put(str, new VarName(str, i, i2));
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void setNamedStringOverride(String str, String str2) {
        if (this.mVarNameHashMap.get(str) != null) {
            overrideText(this.mVarNameHashMap.get(str).mId, str2);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void clearNamedStringOverride(String str) {
        if (this.mVarNameHashMap.get(str) != null) {
            clearDataOverride(this.mVarNameHashMap.get(str).mId);
        }
        this.mVarNameHashMap.put(str, null);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void setNamedIntegerOverride(String str, int i) {
        if (this.mVarNameHashMap.get(str) != null) {
            overrideInt(this.mVarNameHashMap.get(str).mId, i);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void clearNamedIntegerOverride(String str) {
        if (this.mVarNameHashMap.get(str) != null) {
            clearIntegerOverride(this.mVarNameHashMap.get(str).mId);
        }
        this.mVarNameHashMap.put(str, null);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void setNamedFloatOverride(String str, float f) {
        if (this.mVarNameHashMap.get(str) != null) {
            overrideFloat(this.mVarNameHashMap.get(str).mId, f);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void clearNamedFloatOverride(String str) {
        if (this.mVarNameHashMap.get(str) != null) {
            clearFloatOverride(this.mVarNameHashMap.get(str).mId);
        }
        this.mVarNameHashMap.put(str, null);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void setNamedLong(String str, long j) {
        VarName varName = this.mVarNameHashMap.get(str);
        if (varName != null) {
            ((LongConstant) this.mRemoteComposeState.getObject(varName.mId)).setValue(j);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void setNamedDataOverride(String str, Object obj) {
        if (this.mVarNameHashMap.get(str) != null) {
            overrideData(this.mVarNameHashMap.get(str).mId, obj);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void clearNamedDataOverride(String str) {
        if (this.mVarNameHashMap.get(str) != null) {
            clearDataOverride(this.mVarNameHashMap.get(str).mId);
        }
        this.mVarNameHashMap.put(str, null);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void setNamedColorOverride(String str, int i) {
        this.mRemoteComposeState.overrideColor(this.mVarNameHashMap.get(str).mId, i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void addCollection(int i, ArrayAccess arrayAccess) {
        this.mRemoteComposeState.addCollection(i, arrayAccess);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void putDataMap(int i, DataMap dataMap) {
        this.mRemoteComposeState.putDataMap(i, dataMap);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public DataMap getDataMap(int i) {
        return this.mRemoteComposeState.getDataMap(i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void runAction(int i, String str) {
        this.mDocument.performClick(this, i, str);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void runNamedAction(int i, Object obj) {
        this.mDocument.runNamedAction(getText(i), obj);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadBitmap(int i, short s, short s2, int i2, int i3, byte[] bArr) {
        Bitmap bitmapCreateBitmap;
        if (this.mRemoteComposeState.containsId(i)) {
            return;
        }
        Bitmap bitmapDecodeByteArray = null;
        if (s != 0) {
            if (s == 1) {
                try {
                    bitmapDecodeByteArray = BitmapFactory.decodeStream(new URL(new String(bArr)).openStream());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            } else if (s == 2) {
                bitmapDecodeByteArray = BitmapFactory.decodeFile(new String(bArr));
            }
        } else if (s2 != 0) {
            if (s2 == 2) {
                bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
                int length = bArr.length / 4;
                int[] iArr = new int[length];
                for (int i4 = 0; i4 < length; i4++) {
                    iArr[i4] = bArr[i4] * 16843009;
                }
                bitmapCreateBitmap.setPixels(iArr, 0, i2, 0, 0, i2, i3);
            } else if (s2 == 3) {
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
                int length2 = bArr.length / 4;
                bitmapCreateBitmap = bitmapCreateBitmap2;
                int[] iArr2 = new int[length2];
                for (int i5 = 0; i5 < length2; i5++) {
                    int i6 = i5 * 4;
                    iArr2[i5] = bArr[i6 + 3] | (bArr[i6] << 24) | (bArr[i6 + 1] << 16) | (bArr[i6 + 2] << 8);
                }
                bitmapCreateBitmap.setPixels(iArr2, 0, i2, 0, 0, i2, i3);
            } else if (s2 == 4) {
                bitmapDecodeByteArray = decodePreferringAlpha8(bArr);
                if (!bitmapDecodeByteArray.getConfig().equals(Bitmap.Config.ALPHA_8)) {
                    bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeByteArray.getWidth(), bitmapDecodeByteArray.getHeight(), Bitmap.Config.ALPHA_8);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    Paint paint = new Paint();
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                    canvas.drawBitmap(bitmapDecodeByteArray, 0.0f, 0.0f, paint);
                    bitmapDecodeByteArray.recycle();
                }
            }
            bitmapDecodeByteArray = bitmapCreateBitmap;
        } else {
            bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        this.mRemoteComposeState.cacheData(i, bitmapDecodeByteArray);
    }

    private Bitmap decodePreferringAlpha8(byte[] bArr) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadText(int i, String str) {
        if (!this.mRemoteComposeState.containsId(i)) {
            this.mRemoteComposeState.cacheData(i, str);
        } else {
            this.mRemoteComposeState.updateData(i, str);
        }
    }

    public void overrideText(int i, String str) {
        this.mRemoteComposeState.overrideData(i, str);
    }

    public void overrideInt(int i, int i2) {
        this.mRemoteComposeState.overrideInteger(i, i2);
    }

    public void overrideData(int i, Object obj) {
        this.mRemoteComposeState.overrideData(i, obj);
    }

    public void clearDataOverride(int i) {
        this.mRemoteComposeState.clearDataOverride(i);
    }

    public void clearIntegerOverride(int i) {
        this.mRemoteComposeState.clearIntegerOverride(i);
    }

    public void clearFloatOverride(int i) {
        this.mRemoteComposeState.clearFloatOverride(i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public String getText(int i) {
        return (String) this.mRemoteComposeState.getFromId(i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadFloat(int i, float f) {
        this.mRemoteComposeState.updateFloat(i, f);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void overrideFloat(int i, float f) {
        this.mRemoteComposeState.overrideFloat(i, f);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadInteger(int i, int i2) {
        this.mRemoteComposeState.updateInteger(i, i2);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void overrideInteger(int i, int i2) {
        this.mRemoteComposeState.overrideInteger(i, i2);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void overrideText(int i, int i2) {
        overrideText(i, getText(i2));
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadColor(int i, int i2) {
        this.mRemoteComposeState.updateColor(i, i2);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadAnimatedFloat(int i, FloatExpression floatExpression) {
        this.mRemoteComposeState.cacheData(i, floatExpression);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadShader(int i, ShaderData shaderData) {
        this.mRemoteComposeState.cacheData(i, shaderData);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public float getFloat(int i) {
        return this.mRemoteComposeState.getFloat(i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void putObject(int i, Object obj) {
        this.mRemoteComposeState.updateObject(i, obj);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public Object getObject(int i) {
        return this.mRemoteComposeState.getObject(i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public int getInteger(int i) {
        return this.mRemoteComposeState.getInteger(i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public long getLong(int i) {
        return ((LongConstant) this.mRemoteComposeState.getObject(i)).getValue();
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public int getColor(int i) {
        return this.mRemoteComposeState.getColor(i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void listensTo(int i, VariableSupport variableSupport) {
        this.mRemoteComposeState.listenToVar(i, variableSupport);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public int updateOps() {
        return this.mRemoteComposeState.getOpsToUpdate(this);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public ShaderData getShader(int i) {
        return (ShaderData) this.mRemoteComposeState.getFromId(i);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void addTouchListener(TouchListener touchListener) {
        this.mDocument.addTouchListener(touchListener);
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void addClickArea(int i, int i2, float f, float f2, float f3, float f4, int i3) {
        this.mDocument.addClickArea(i, (String) this.mRemoteComposeState.getFromId(i2), f, f2, f3, f4, (String) this.mRemoteComposeState.getFromId(i3));
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void hapticEffect(int i) {
        this.mDocument.haptic(i);
    }
}
