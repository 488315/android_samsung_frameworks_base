package com.samsung.android.sdk.scs.ai.visual.c2pa;

import android.content.Context;
import android.util.MalformedJsonException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.samsung.android.sdk.scs.base.tasks.Task;
import com.samsung.android.sdk.scs.base.utils.Log;
import java.util.List;

/* loaded from: classes4.dex */
public class C2paClient {
    private static final String TAG = "C2paClient";
    private final C2paServiceExecutor mServiceExecutor;

    public C2paClient(Context context) {
        Log.d(TAG, "C2paClient");
        this.mServiceExecutor = new C2paServiceExecutor(context);
    }

    public static C2paManifestList fromJson(String str) throws MalformedJsonException {
        Gson gsonCreate = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).registerTypeAdapter(Data.class, new DataFieldDeserializer()).create();
        try {
            C2paManifestList c2paManifestList = (C2paManifestList) gsonCreate.fromJson(str, C2paManifestList.class);
            c2paManifestList.calculateValidation();
            List<String> manifestKeys = c2paManifestList.getManifestKeys();
            JsonObject jsonObject = (JsonObject) gsonCreate.fromJson(str, JsonObject.class);
            for (String str2 : manifestKeys) {
                if (c2paManifestList.getManifest(str2) != null) {
                    c2paManifestList.getManifest(str2).setAssertionsJsonArray(jsonObject.getAsJsonObject("manifests").getAsJsonObject(str2).getAsJsonArray("assertions"));
                }
            }
            return c2paManifestList;
        } catch (Exception e) {
            throw new MalformedJsonException("Not matched with C2PA Manifest Store : message - " + e.getMessage() + ", cause - " + e.getCause());
        }
    }

    public Task clearManifestsFromCache(String str) {
        Log.d(TAG, "clearManifestsFromCache()");
        C2paClientClearManifestsFromCacheRunnable c2paClientClearManifestsFromCacheRunnable = new C2paClientClearManifestsFromCacheRunnable(this.mServiceExecutor);
        c2paClientClearManifestsFromCacheRunnable.setFilePath(str);
        this.mServiceExecutor.execute(c2paClientClearManifestsFromCacheRunnable);
        return c2paClientClearManifestsFromCacheRunnable.getTask();
    }

    public Task embedManifestToFile(String str, String str2, String str3, List<String> list) {
        Log.d(TAG, "embedManifestToFile()");
        C2paClientEmbedManifestRunnable c2paClientEmbedManifestRunnable = new C2paClientEmbedManifestRunnable(this.mServiceExecutor);
        c2paClientEmbedManifestRunnable.setJson(str);
        c2paClientEmbedManifestRunnable.setTargetPath(str2);
        c2paClientEmbedManifestRunnable.setParentPath(str3);
        c2paClientEmbedManifestRunnable.setIngredientPaths(list);
        this.mServiceExecutor.execute(c2paClientEmbedManifestRunnable);
        return c2paClientEmbedManifestRunnable.getTask();
    }

    public Task getC2paService() {
        C2paGetServiceRunnable c2paGetServiceRunnable = new C2paGetServiceRunnable(this.mServiceExecutor);
        this.mServiceExecutor.execute(c2paGetServiceRunnable);
        return c2paGetServiceRunnable.getTask();
    }

    public Task getManifestsAsString(String str) {
        Log.d(TAG, "getManifestsAsString()");
        C2paClientGetManifestRunnable c2paClientGetManifestRunnable = new C2paClientGetManifestRunnable(this.mServiceExecutor);
        c2paClientGetManifestRunnable.setFilePath(str);
        this.mServiceExecutor.execute(c2paClientGetManifestRunnable);
        return c2paClientGetManifestRunnable.getTask();
    }

    public Task isC2paExist(String str) {
        Log.d(TAG, "isC2paExist()");
        C2paClientIsC2paExistRunnable c2paClientIsC2paExistRunnable = new C2paClientIsC2paExistRunnable(this.mServiceExecutor);
        c2paClientIsC2paExistRunnable.setParentPath(str);
        this.mServiceExecutor.execute(c2paClientIsC2paExistRunnable);
        return c2paClientIsC2paExistRunnable.getTask();
    }

    public void release() {
        Log.d(TAG, "release()");
        this.mServiceExecutor.deInit();
    }

    public Task saveManifestsToCache(String str) {
        Log.d(TAG, "saveManifestsToCache()");
        C2paClientSaveManifestsToCacheRunnable c2paClientSaveManifestsToCacheRunnable = new C2paClientSaveManifestsToCacheRunnable(this.mServiceExecutor);
        c2paClientSaveManifestsToCacheRunnable.setFilePath(str);
        this.mServiceExecutor.execute(c2paClientSaveManifestsToCacheRunnable);
        return c2paClientSaveManifestsToCacheRunnable.getTask();
    }

    public Task saveToCacheEmbedToFile(String str, String str2, String str3, List<String> list) {
        Log.d(TAG, "saveToCacheEmbedToFile()");
        C2paClientSaveToCacheEmbedToFileRunnable c2paClientSaveToCacheEmbedToFileRunnable = new C2paClientSaveToCacheEmbedToFileRunnable(this.mServiceExecutor);
        c2paClientSaveToCacheEmbedToFileRunnable.setJson(str);
        c2paClientSaveToCacheEmbedToFileRunnable.setTargetPath(str2);
        c2paClientSaveToCacheEmbedToFileRunnable.setParentPath(str3);
        c2paClientSaveToCacheEmbedToFileRunnable.setIngredientPaths(list);
        this.mServiceExecutor.execute(c2paClientSaveToCacheEmbedToFileRunnable);
        return c2paClientSaveToCacheEmbedToFileRunnable.getTask();
    }
}
