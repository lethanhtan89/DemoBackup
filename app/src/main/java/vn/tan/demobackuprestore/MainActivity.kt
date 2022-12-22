package vn.tan.demobackuprestore

import android.annotation.SuppressLint
import android.app.backup.BackupAgentHelper
import android.app.backup.BackupManager
import android.app.backup.SharedPreferencesBackupHelper
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

const val PREF = "pref_app"
const val PREF_NEWS = "pref_news"
const val KEY_USER = "key_user"
const val KEY_NEWS = "key_news"
const val PREFS_BACKUP_KEY = "user_setting"

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        demoPref()

        val androidID = getAndroidID()
    }

    private fun demoPref() {
        val pref = getSharedPreferences(PREF, MODE_PRIVATE)
        var edit = pref.edit()
        edit.putString(KEY_USER, Gson().toJson(User()))
        edit.commit()

        val prefNews = getSharedPreferences(PREF_NEWS, MODE_PRIVATE)
        var editNews = prefNews.edit()
        editNews.putString(KEY_NEWS, "Gson().toJson(User()")
        editNews.commit()
    }

    private fun setAndroidID(androidID: String?) {
        val pref = getSharedPreferences(PREFS_BACKUP_KEY, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("android_id", androidID)
        editor.commit()
    }

    private fun getAndroidID(): String? {
        val pref = getSharedPreferences(PREFS_BACKUP_KEY, MODE_PRIVATE)
        var androidID = pref.getString("android_id", "")
        if (TextUtils.isEmpty(androidID)) {
            androidID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

            //Change data
            setAndroidID(androidID)

            //request backup
            BackupManager(this).dataChanged()
        }
        return androidID
    }
}

//https://viblo.asia/p/backuprestore-sao-luuphuc-hoi-du-lieu-su-dung-key-value-pairs-with-android-backup-service-phan-1-oOVlYqwrl8W
class MyBackupAgent : BackupAgentHelper() {
    // Allocate a helper and add it to the backup agent
    override fun onCreate() {
        val helper = SharedPreferencesBackupHelper(this, PREFS)
        addHelper(PREFS_BACKUP_KEY, helper)
    }

    companion object {
        // The name of the SharedPreferences file
        const val PREFS = "user_preferences"

        // A key to uniquely identify the set of backup data
        const val PREFS_BACKUP_KEY = "user_setting"
    }
}

data class User(
    var id: Int = 10010,
    var name: String = "Nguyen Van A",
    var age: Int = 30,
    var address: String = "HCMCD"
)