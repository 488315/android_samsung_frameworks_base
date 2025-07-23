package com.android.server.backup;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupHelperWithLogger;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncAdapterType;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.security.keystore.KeyProperties;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class AccountSyncSettingsBackupHelper extends BackupHelperWithLogger {
    private static final boolean DEBUG = false;
    private static final String JSON_FORMAT_ENCODING = "UTF-8";
    private static final String JSON_FORMAT_HEADER_KEY = "account_data";
    private static final int JSON_FORMAT_VERSION = 1;
    private static final String KEY_ACCOUNTS = "accounts";
    private static final String KEY_ACCOUNT_AUTHORITIES = "authorities";
    private static final String KEY_ACCOUNT_NAME = "name";
    private static final String KEY_ACCOUNT_TYPE = "type";
    private static final String KEY_AUTHORITY_NAME = "name";
    private static final String KEY_AUTHORITY_SYNC_ENABLED = "syncEnabled";
    private static final String KEY_AUTHORITY_SYNC_STATE = "syncState";
    private static final String KEY_MASTER_SYNC_ENABLED = "masterSyncEnabled";
    private static final String KEY_VERSION = "version";
    private static final int MD5_BYTE_SIZE = 16;
    private static final String STASH_FILE = "/backup/unadded_account_syncsettings.json";
    private static final int STATE_VERSION = 1;
    private static final int SYNC_REQUEST_LATCH_TIMEOUT_SECONDS = 1;
    private static final String TAG = "AccountSyncSettingsBackupHelper";
    private AccountManager mAccountManager;
    private Context mContext;
    private final int mUserId;

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
    }

    public AccountSyncSettingsBackupHelper(Context context, int i) {
        this.mContext = context;
        this.mAccountManager = AccountManager.get(context);
        this.mUserId = i;
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        try {
            byte[] bytes = serializeAccountSyncSettingsToJSON(this.mUserId).toString().getBytes("UTF-8");
            byte[] readOldMd5Checksum = readOldMd5Checksum(parcelFileDescriptor);
            byte[] generateMd5Checksum = generateMd5Checksum(bytes);
            if (Arrays.equals(readOldMd5Checksum, generateMd5Checksum)) {
                Log.i(TAG, "Old and new MD5 checksums match. Skipping backup.");
            } else {
                int length = bytes.length;
                backupDataOutput.writeEntityHeader(JSON_FORMAT_HEADER_KEY, length);
                backupDataOutput.writeEntityData(bytes, length);
                Log.i(TAG, "Backup successful.");
            }
            writeNewMd5Checksum(parcelFileDescriptor2, generateMd5Checksum);
        } catch (IOException | NoSuchAlgorithmException | JSONException e) {
            Log.e(TAG, "Couldn't backup account sync settings\n" + e);
        }
    }

    private JSONObject serializeAccountSyncSettingsToJSON(int i) throws JSONException {
        Account[] accountsAsUser = this.mAccountManager.getAccountsAsUser(i);
        SyncAdapterType[] syncAdapterTypesAsUser = ContentResolver.getSyncAdapterTypesAsUser(i);
        HashMap hashMap = new HashMap();
        for (SyncAdapterType syncAdapterType : syncAdapterTypesAsUser) {
            if (syncAdapterType.isUserVisible()) {
                if (!hashMap.containsKey(syncAdapterType.accountType)) {
                    hashMap.put(syncAdapterType.accountType, new ArrayList());
                }
                ((List) hashMap.get(syncAdapterType.accountType)).add(syncAdapterType.authority);
            }
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("version", 1);
        jSONObject.put(KEY_MASTER_SYNC_ENABLED, ContentResolver.getMasterSyncAutomaticallyAsUser(i));
        JSONArray jSONArray = new JSONArray();
        for (Account account : accountsAsUser) {
            List<String> list = (List) hashMap.get(account.type);
            if (list != null && !list.isEmpty()) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("name", account.name);
                jSONObject2.put("type", account.type);
                JSONArray jSONArray2 = new JSONArray();
                for (String str : list) {
                    int isSyncableAsUser = ContentResolver.getIsSyncableAsUser(account, str, i);
                    boolean syncAutomaticallyAsUser = ContentResolver.getSyncAutomaticallyAsUser(account, str, i);
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("name", str);
                    jSONObject3.put(KEY_AUTHORITY_SYNC_STATE, isSyncableAsUser);
                    jSONObject3.put(KEY_AUTHORITY_SYNC_ENABLED, syncAutomaticallyAsUser);
                    jSONArray2.put(jSONObject3);
                }
                jSONObject2.put("authorities", jSONArray2);
                jSONArray.put(jSONObject2);
            }
        }
        jSONObject.put("accounts", jSONArray);
        return jSONObject;
    }

    private byte[] readOldMd5Checksum(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(parcelFileDescriptor.getFileDescriptor()));
        byte[] bArr = new byte[16];
        try {
            int readInt = dataInputStream.readInt();
            if (readInt <= 1) {
                for (int i = 0; i < 16; i++) {
                    bArr[i] = dataInputStream.readByte();
                }
            } else {
                Log.i(TAG, "Backup state version is: " + readInt + " (support only up to version 1)");
            }
        } catch (EOFException unused) {
        }
        return bArr;
    }

    private void writeNewMd5Checksum(ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(parcelFileDescriptor.getFileDescriptor())));
        dataOutputStream.writeInt(1);
        dataOutputStream.write(bArr);
    }

    private byte[] generateMd5Checksum(byte[] bArr) throws NoSuchAlgorithmException {
        if (bArr == null) {
            return null;
        }
        return MessageDigest.getInstance(KeyProperties.DIGEST_MD5).digest(bArr);
    }

    @Override // android.app.backup.BackupHelperWithLogger, android.app.backup.BackupHelper
    public void restoreEntity(BackupDataInputStream backupDataInputStream) {
        byte[] bArr = new byte[backupDataInputStream.size()];
        try {
            backupDataInputStream.read(bArr);
            JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
            boolean z = jSONObject.getBoolean(KEY_MASTER_SYNC_ENABLED);
            JSONArray jSONArray = jSONObject.getJSONArray("accounts");
            if (ContentResolver.getMasterSyncAutomaticallyAsUser(this.mUserId)) {
                ContentResolver.setMasterSyncAutomaticallyAsUser(false, this.mUserId);
            }
            try {
                restoreFromJsonArray(jSONArray, this.mUserId);
                ContentResolver.setMasterSyncAutomaticallyAsUser(z, this.mUserId);
                Log.i(TAG, "Restore successful.");
            } catch (Throwable th) {
                ContentResolver.setMasterSyncAutomaticallyAsUser(z, this.mUserId);
                throw th;
            }
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Couldn't restore account sync settings\n" + e);
        }
    }

    private void restoreFromJsonArray(JSONArray jSONArray, int i) throws JSONException {
        Set<Account> accounts = getAccounts(i);
        JSONArray jSONArray2 = new JSONArray();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
            try {
                if (accounts.contains(new Account(jSONObject.getString("name"), jSONObject.getString("type")))) {
                    restoreExistingAccountSyncSettingsFromJSON(jSONObject, i);
                } else {
                    jSONArray2.put(jSONObject);
                }
            } catch (IllegalArgumentException unused) {
            }
        }
        if (jSONArray2.length() > 0) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(getStashFile(i));
                try {
                    new DataOutputStream(fileOutputStream).writeUTF(jSONArray2.toString());
                    fileOutputStream.close();
                    return;
                } finally {
                }
            } catch (IOException e) {
                Log.e(TAG, "unable to write the sync settings to the stash file", e);
                return;
            }
        }
        File stashFile = getStashFile(i);
        if (stashFile.exists()) {
            stashFile.delete();
        }
    }

    private void accountAddedInternal(int i) {
        try {
            FileInputStream fileInputStream = new FileInputStream(getStashFile(i));
            try {
                String readUTF = new DataInputStream(fileInputStream).readUTF();
                fileInputStream.close();
                try {
                    restoreFromJsonArray(new JSONArray(readUTF), i);
                } catch (JSONException e) {
                    Log.e(TAG, "there was an error with the stashed sync settings", e);
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException | IOException unused) {
        }
    }

    public static void accountAdded(Context context, int i) {
        new AccountSyncSettingsBackupHelper(context, i).accountAddedInternal(i);
    }

    private Set<Account> getAccounts(int i) {
        Account[] accountsAsUser = this.mAccountManager.getAccountsAsUser(i);
        HashSet hashSet = new HashSet();
        for (Account account : accountsAsUser) {
            hashSet.add(account);
        }
        return hashSet;
    }

    private void restoreExistingAccountSyncSettingsFromJSON(JSONObject jSONObject, int i) throws JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray("authorities");
        Account account = new Account(jSONObject.getString("name"), jSONObject.getString("type"));
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObject2 = (JSONObject) jSONArray.get(i2);
            String string = jSONObject2.getString("name");
            boolean z = jSONObject2.getBoolean(KEY_AUTHORITY_SYNC_ENABLED);
            int i3 = jSONObject2.getInt(KEY_AUTHORITY_SYNC_STATE);
            ContentResolver.setSyncAutomaticallyAsUser(account, string, z, i);
            if (!z) {
                ContentResolver.setIsSyncableAsUser(account, string, i3 == 0 ? 0 : 2, i);
            }
        }
    }

    private static File getStashFile(int i) {
        File dataSystemCeDirectory;
        if (i == 0) {
            dataSystemCeDirectory = Environment.getDataDirectory();
        } else {
            dataSystemCeDirectory = Environment.getDataSystemCeDirectory(i);
        }
        return new File(dataSystemCeDirectory, STASH_FILE);
    }
}
