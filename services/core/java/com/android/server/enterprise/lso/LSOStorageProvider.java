package com.android.server.enterprise.lso;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.samsung.android.knox.lockscreen.LSOAttributeSet;
import com.samsung.android.knox.lockscreen.LSOConstants;
import com.samsung.android.knox.lockscreen.LSOItemContainer;
import com.samsung.android.knox.lockscreen.LSOItemCreator;
import com.samsung.android.knox.lockscreen.LSOItemData;
import com.samsung.android.knox.lockscreen.LSOItemImage;
import com.samsung.android.knox.lockscreen.LSOItemText;
import com.samsung.android.knox.lockscreen.LSOItemWidget;
import java.security.InvalidParameterException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LSOStorageProvider extends EdmStorageProvider {
    public static final String[] tblColumns = {"Item_RowId", "Item_Type", "Item_Id", "Item_Width", "Item_Height", "Item_Weight", "Item_Bg_Color", "Item_Gravity", "Item_TxtColor_or_PollingInterval", "Item_Txt_or_ImgPath", "Item_TxtStyle_or_ScaleType", "Item_TxtSize", "Item_Url", "Item_Orientation", "Item_PackageName", "Item_Attributes", "Item_Layer", "Item_ParentId"};

    public final void deleteRecord(String[] strArr) {
        SQLiteDatabase writableDatabase = this.mEdmDbHelper.getWritableDatabase();
        Log.d("LSOStorageProvider", "Delete from ADMIN_REF where ");
        writableDatabase.delete("ADMIN_REF", "policyName=?", strArr);
    }

    public final int getAdminUid(String str) {
        int adminByField = getAdminByField("ADMIN_REF", "policyName", str);
        Log.d("LSOStorageProvider", str + " - Configured admin UID: " + adminByField);
        return adminByField;
    }

    public final long insertRecord(String str, ContentValues contentValues) {
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Insert record: ", str, " -- ");
        m.append(contentValues.toString());
        Log.d("LSOStorageProvider", m.toString());
        SQLiteDatabase writableDatabase = this.mEdmDbHelper.getWritableDatabase();
        long insert = writableDatabase.insert(str, null, contentValues);
        if (insert == -1) {
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, ": Failed to insert record - ");
            m2.append(contentValues.toString());
            Log.e("LSOStorageProvider", m2.toString());
            writableDatabase.close();
        }
        return insert;
    }

    public final void loadItemData(LSOItemData lSOItemData, Cursor cursor) {
        String string = cursor.getString(2);
        if (string != null) {
            lSOItemData.setId(string);
        }
        int i = cursor.getInt(3);
        if (i != -99) {
            lSOItemData.setWidth(i);
        }
        int i2 = cursor.getInt(4);
        if (i2 != -99) {
            lSOItemData.setHeight(i2);
        }
        float f = cursor.getFloat(5);
        if (f != FullScreenMagnificationGestureHandler.MAX_SCALE) {
            lSOItemData.setWeight(f);
        }
        int i3 = cursor.getInt(6);
        if (i3 != -1) {
            lSOItemData.setBgColor(i3);
        }
        if (lSOItemData.getType() == 1) {
            Log.d("LSOStorageProvider", "LoadItemData -- " + lSOItemData.toString());
            return;
        }
        int i4 = cursor.getInt(7);
        if (i4 != -1) {
            lSOItemData.setGravity(i4);
        }
        byte[] blob = cursor.getBlob(15);
        if (blob != null) {
            lSOItemData.setAttrs(LSOAttributeSet.fromByteArray(blob));
        }
        byte type = lSOItemData.getType();
        if (type == 2) {
            LSOItemText lSOItemText = (LSOItemText) lSOItemData;
            String string2 = cursor.getString(9);
            if (string2 != null && string2.length() != 0) {
                lSOItemText.setText(string2);
            }
            int i5 = cursor.getInt(8);
            if (i5 != -1) {
                lSOItemText.setTextColor(i5);
            }
            int i6 = cursor.getInt(10);
            if (i6 != -1) {
                lSOItemText.setTextStyle(i6);
            }
            float f2 = cursor.getFloat(11);
            if (f2 != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                lSOItemText.setTextSize(f2);
            }
            Log.d("LSOStorageProvider", "LoadTextData -- " + lSOItemText.toString());
            return;
        }
        if (type == 3) {
            LSOItemImage lSOItemImage = (LSOItemImage) lSOItemData;
            String string3 = cursor.getString(9);
            if (string3 != null && string3.length() != 0) {
                lSOItemImage.setImagePath(string3);
                int i7 = cursor.getInt(10);
                if (i7 != -1) {
                    lSOItemImage.setScaleType(i7);
                }
                int i8 = cursor.getInt(8);
                String string4 = cursor.getString(12);
                if (string4 != null && string4.length() != 0) {
                    lSOItemImage.setURL(string4, i8);
                }
            }
            Log.d("LSOStorageProvider", "LoadImageData -- " + lSOItemImage.toString());
            return;
        }
        if (type != 4) {
            if (type != 5) {
                return;
            }
            LSOItemWidget lSOItemWidget = (LSOItemWidget) lSOItemData;
            String string5 = cursor.getString(14);
            if (string5 != null && string5.length() != 0) {
                lSOItemWidget.setWidget(string5);
            }
            Log.d("LSOStorageProvider", "LoadWidgetData -- " + lSOItemWidget.toString());
            return;
        }
        LSOItemContainer lSOItemContainer = (LSOItemContainer) lSOItemData;
        String string6 = cursor.getString(9);
        int i9 = cursor.getInt(13);
        if (string6 != null && string6.length() != 0) {
            lSOItemContainer.setBGImage(string6);
        }
        if (i9 != 0) {
            lSOItemContainer.setOrientation(LSOItemContainer.ORIENTATION.HORIZONTAL);
        }
        Log.d("LSOStorageProvider", "LoadContainerData -- " + lSOItemContainer.toString());
        int i10 = cursor.getInt(0);
        ContentValues contentValues = new ContentValues();
        contentValues.put("Item_ParentId", Integer.valueOf(i10));
        Cursor cursor2 = getCursor("LOCKSCREEN_OVERLAY", tblColumns, contentValues);
        if (cursor2 == null) {
            return;
        }
        try {
            try {
                if (cursor2.getCount() != 0) {
                    while (cursor2.moveToNext()) {
                        LSOItemData createItem = LSOItemCreator.createItem((byte) cursor2.getInt(1));
                        if (createItem != null) {
                            loadItemData(createItem, cursor2);
                            lSOItemContainer.addItem(createItem);
                        }
                    }
                }
            } catch (SQLException e) {
                Log.e("LSOStorageProvider", "Exception occurred accessing Enterprise db " + e.getMessage());
            }
        } finally {
            cursor2.close();
        }
    }

    public final void resetOverlayData(int i) {
        int i2;
        wipeLayerData(i);
        String[] strArr = {"LOCKSCREEN_OVERLAY"};
        Cursor rawQuery = this.mEdmDbHelper.getWritableDatabase().rawQuery("SELECT COUNT(*) from LOCKSCREEN_OVERLAY", null);
        if (rawQuery == null) {
            i2 = 0;
        } else {
            rawQuery.moveToNext();
            i2 = rawQuery.getInt(0);
            rawQuery.close();
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "getCount(LOCKSCREEN_OVERLAY) - ", "LSOStorageProvider");
        }
        if (i2 == 0) {
            deleteRecord(strArr);
        }
        Log.d("LSOStorageProvider", "resetOverlayData() LOCKSCREEN_OVERLAY - resetted layer : " + LSOConstants.getLayerName(i));
    }

    public final void resetWallpaperData() {
        deleteRecord(new String[]{"LOCKSCREEN_WALLPAPER"});
        Log.d("LSOStorageProvider", "resetWallpaperData() LOCKSCREEN_WALLPAPER - resetted");
    }

    public final boolean setOverlayData(long j, LSOItemData lSOItemData, int i, LSOAttributeSet lSOAttributeSet) {
        if (lSOItemData == null || j == -1) {
            throw new InvalidParameterException("Parameter cannot be null");
        }
        int adminUid = getAdminUid("LOCKSCREEN_OVERLAY");
        wipeLayerData(i);
        boolean overlayData = setOverlayData(lSOItemData, i, 0L);
        if (!overlayData) {
            resetOverlayData(0);
        } else if (adminUid != j) {
            ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("policyName", "LOCKSCREEN_OVERLAY");
            m.put("adminUid", Long.valueOf(j));
            if (lSOAttributeSet != null) {
                m.put("accountObject", lSOAttributeSet.toByteArray());
            }
            overlayData = -1 != insertRecord("ADMIN_REF", m);
            if (!overlayData) {
                resetOverlayData(0);
            }
        }
        return overlayData;
    }

    public final boolean setOverlayData(LSOItemData lSOItemData, int i, long j) {
        if (lSOItemData == null) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        byte type = lSOItemData.getType();
        String[] strArr = tblColumns;
        int i2 = 16;
        contentValues.put(strArr[16], Integer.valueOf(i));
        contentValues.put(strArr[17], Long.valueOf(j));
        int i3 = 1;
        contentValues.put(strArr[1], Byte.valueOf(type));
        int[] updatedFields = lSOItemData.getUpdatedFields();
        if (updatedFields != null && updatedFields.length != 0) {
            int i4 = 0;
            boolean z = false;
            while (i4 < updatedFields.length && !z) {
                int i5 = updatedFields[i4];
                if (i5 == i3) {
                    contentValues.put(strArr[2], lSOItemData.getId());
                } else if (i5 == 2) {
                    contentValues.put(strArr[3], Integer.valueOf(lSOItemData.getWidth()));
                } else if (i5 == 4) {
                    contentValues.put(strArr[4], Integer.valueOf(lSOItemData.getHeight()));
                } else if (i5 == 8) {
                    contentValues.put(strArr[5], Float.valueOf(lSOItemData.getWeight()));
                } else if (i5 == i2) {
                    contentValues.put(strArr[6], Integer.valueOf(lSOItemData.getBgColor()));
                } else if (i5 == 32) {
                    contentValues.put(strArr[7], Integer.valueOf(lSOItemData.getGravity()));
                } else if (i5 == 64) {
                    contentValues.put(strArr[15], lSOItemData.getAttrs().toByteArray());
                } else if (type == 2) {
                    LSOItemText lSOItemText = (LSOItemText) lSOItemData;
                    if (i5 == 128) {
                        contentValues.put(strArr[9], lSOItemText.getText());
                    } else if (i5 == 256) {
                        contentValues.put(strArr[8], Integer.valueOf(lSOItemText.getTextColor()));
                    } else if (i5 == 512) {
                        contentValues.put(strArr[11], Float.valueOf(lSOItemText.getTextSizeAsFloat()));
                    } else if (i5 == 1024) {
                        contentValues.put(strArr[10], Integer.valueOf(lSOItemText.getTextStyle()));
                    }
                } else if (type == 3) {
                    LSOItemImage lSOItemImage = (LSOItemImage) lSOItemData;
                    if (i5 == 128) {
                        contentValues.put(strArr[9], lSOItemImage.getImagePath());
                    } else if (i5 == 256) {
                        contentValues.put(strArr[12], lSOItemImage.getUrl());
                        contentValues.put(strArr[8], Long.valueOf(lSOItemImage.getPollingInterval()));
                    } else if (i5 == 512) {
                        contentValues.put(strArr[10], Integer.valueOf(lSOItemImage.getScaleTypeAsInteger()));
                    }
                } else if (type == 4) {
                    LSOItemContainer lSOItemContainer = (LSOItemContainer) lSOItemData;
                    if (i5 == 128) {
                        contentValues.put(strArr[13], Integer.valueOf(lSOItemContainer.getOrientation() == LSOItemContainer.ORIENTATION.VERTICAL ? 0 : 1));
                    } else if (i5 == 256) {
                        contentValues.put(strArr[9], lSOItemContainer.getBGImagePath());
                    }
                } else if (type != 5) {
                    Log.e("LSOStorageProvider", "unknown LSOItem");
                    z = true;
                } else {
                    LSOItemWidget lSOItemWidget = (LSOItemWidget) lSOItemData;
                    if (i5 == 128) {
                        contentValues.put(strArr[14], lSOItemWidget.getWidget());
                    }
                }
                i4++;
                i2 = 16;
                i3 = 1;
            }
            if (z) {
                contentValues = null;
            }
        }
        if (contentValues == null) {
            Log.e("LSOStorageProvider", "Cannot construct content values");
            return false;
        }
        int i6 = 0;
        long insertRecord = insertRecord("LOCKSCREEN_OVERLAY", contentValues);
        if (insertRecord == -1) {
            return false;
        }
        if (lSOItemData.getType() != 4) {
            return true;
        }
        LSOItemContainer lSOItemContainer2 = (LSOItemContainer) lSOItemData;
        boolean z2 = true;
        while (i6 < lSOItemContainer2.getNumItems() && z2) {
            z2 = setOverlayData(lSOItemContainer2.getItem(i6), i, insertRecord);
            i6++;
        }
        if (!z2) {
            Log.e("LSOStorageProvider", "Failed to store LSOItemData for " + i6 + " with ParendId: " + insertRecord);
        }
        return z2;
    }

    public final void wipeLayerData(int i) {
        if (i == 0) {
            delete("LOCKSCREEN_OVERLAY", null);
        } else {
            deleteDataByFields("LOCKSCREEN_OVERLAY", new String[]{"Item_Layer"}, new String[]{String.valueOf(i)});
        }
        Log.d("LSOStorageProvider", "wipeLayerData() - LOCKSCREEN_OVERLAY - cleaned : " + LSOConstants.getLayerName(i));
    }
}
