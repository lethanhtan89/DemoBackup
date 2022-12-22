# Backup


## Enable backup

Set value as follows to enable backup, we use android:allowBackup = "true|false"

```bash
<application
        android:allowBackup="true"
        android:fullBackupOnly="true"
        android:backupAgent=".MyBackupAgent"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
/>
```
## How to exclude backup data
Check at the MainActivity, There are 3 preferences applied:

```python
const val PREF = "pref_app"
const val PREF_NEWS = "pref_news"
const val PREFS_BACKUP_KEY = "user_setting"
```

It means it will be stored in phone storage when the app runs.
And in the data_extraction_rules file, I use 'exclude' to skip data backup

```python
<data-extraction-rules>
    <cloud-backup disableIfNoEncryptionCapabilities="false">
        <include domain="sharedpref" path="."/>
        <exclude domain="sharedpref" path="pref_app.xml"/>
        <exclude domain="sharedpref" path="user_setting.xml"/>
    </cloud-backup>
    <!--
    <device-transfer>
        <include .../>
        <exclude .../>
    </device-transfer>
    -->
</data-extraction-rules>
```

And the results after runs the command line 
```python
adb shell bmgr backupnow <PACKAGE> (adb shell bmgr backupnow vn.tan.demobackuprestore)
```
And There is only pref_news was backed up.
```python
2022-12-23 09:55:57.531 538-23013/? D/BackupManagerService: Calling doFullBackup() on vn.tan.demobackuprestore
2022-12-23 09:55:57.540 22744-22761/vn.tan.demobackuprestore I/FullBackup_native: measured [/data/data/vn.tan.demobackuprestore/shared_prefs] at 0
2022-12-23 09:55:57.540 22744-22761/vn.tan.demobackuprestore I/file_backup_helper:    Name: apps/vn.tan.demobackuprestore/sp/pref_news.xml
2022-12-23 09:55:57.541 22744-22761/vn.tan.demobackuprestore I/FullBackup_native: measured [/data/data/vn.tan.demobackuprestore/shared_prefs/pref_news.xml] at 1024
2022-12-23 09:55:57.558 22744-22744/vn.tan.demobackuprestore I/Process: Sending signal. PID: 22744 SIG: 9
```
